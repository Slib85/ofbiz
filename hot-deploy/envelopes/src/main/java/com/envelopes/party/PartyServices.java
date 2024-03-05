/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.party;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import com.envelopes.util.*;

public class PartyServices {
	public static final String module = PartyServices.class.getName();

	/*
	 * Return role types for a given partyId
	 */
	public static Map<String, Object> getAEPartyRoles(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		Map result = ServiceUtil.returnSuccess();

		String partyId = (String) context.get("partyId");
		boolean isTrade = false;
		boolean isNonProfit = false;
		boolean isNonTaxable = false;
		boolean isTradePostNet = false;
		boolean isTradeAllegra = false;
		boolean isNet30 = false;

		try {
			List<GenericValue> partyRoles = EntityQuery.use(delegator).from("PartyRole").where("partyId", partyId).queryList();
			if(UtilValidate.isNotEmpty(partyRoles)) {
				Iterator partyRoleIter = partyRoles.iterator();
				while(partyRoleIter.hasNext()) {
					GenericValue partyRole = (GenericValue) partyRoleIter.next();
					if(UtilValidate.isNotEmpty(partyRole.getString("roleTypeId")) && partyRole.getString("roleTypeId").equalsIgnoreCase("NON_PROFIT")) {
						isNonProfit = true;
					} else if(UtilValidate.isNotEmpty(partyRole.getString("roleTypeId")) && partyRole.getString("roleTypeId").equalsIgnoreCase("NON_TAXABLE")) {
						isNonTaxable = true;
					} else if(UtilValidate.isNotEmpty(partyRole.getString("roleTypeId")) && partyRole.getString("roleTypeId").equalsIgnoreCase("WHOLESALER_PN")) {
						isTradePostNet = true;
					} else if(UtilValidate.isNotEmpty(partyRole.getString("roleTypeId")) && partyRole.getString("roleTypeId").equalsIgnoreCase("WHOLESALER")) {
						isTrade = true;
					} else if(UtilValidate.isNotEmpty(partyRole.getString("roleTypeId")) && partyRole.getString("roleTypeId").equalsIgnoreCase("WHOLESALER_ALGRA")) {
						isTradeAllegra = true;
					} else if(UtilValidate.isNotEmpty(partyRole.getString("roleTypeId")) && partyRole.getString("roleTypeId").equalsIgnoreCase("NET_30")) {
						isNet30 = true;
					}
				}
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error in getAEPartyRoles service for partyId: " + partyId, module);
		}

		result.put("isTrade", Boolean.valueOf(isTrade));
		result.put("isNonProfit", Boolean.valueOf(isNonProfit));
		result.put("isNonTaxable", Boolean.valueOf(isNonTaxable));
		result.put("isTradePostNet", Boolean.valueOf(isTradePostNet));
		result.put("isTradeAllegra", Boolean.valueOf(isTradeAllegra));
		result.put("isNet30", Boolean.valueOf(isNet30));

		return result;
	}

	/*
	 * Return role types for a given partyId
	 */
	public static Map<String, Object> checkPartyRole(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		LocalDispatcher dispatcher = dctx.getDispatcher();

		Map result = ServiceUtil.returnSuccess();

		String partyId = (String) context.get("partyId");
		String roleTypeId = (String) context.get("roleTypeId");

		try {
			if(UtilValidate.isNotEmpty(roleTypeId) && (roleTypeId.equals("WHOLESALER") || roleTypeId.equals("WHOLESALER_PN") || roleTypeId.equals("WHOLESALER_ALGRA"))) {
				GenericValue person = EntityQuery.use(delegator).from("Person").where("partyId", partyId).queryOne();
				if(person != null) {
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("listName", "Transactional");
					dataMap.put("messageName", "TradeApproval");
					dataMap.put("data", UtilMisc.toMap("RequestFirstName", (UtilValidate.isNotEmpty(person.getString("firstName"))) ? person.getString("firstName") : "Trade Member"));
					dataMap.put("email", PartyHelper.getPrimaryEmailAddress(delegator, partyId));
					dataMap.put("webSiteId", "envelopes");
					dispatcher.runAsync("sendListrakEmail", dataMap);
				}
			} else if(UtilValidate.isNotEmpty(roleTypeId) && (roleTypeId.equals("NON_PROFIT"))) {
				GenericValue person = EntityQuery.use(delegator).from("Person").where("partyId", partyId).queryOne();
				if(person != null) {
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("listName", "Transactional");
					dataMap.put("messageName", "NonProfitApproval");
					dataMap.put("data", UtilMisc.toMap("RequestFirstName", (UtilValidate.isNotEmpty(person.getString("firstName"))) ? person.getString("firstName") : "Non-Profit Member"));
					dataMap.put("email", PartyHelper.getPrimaryEmailAddress(delegator, partyId));
					dataMap.put("webSiteId", "envelopes");
					dispatcher.runAsync("sendListrakEmail", dataMap);
				}
			}
		} catch(Exception e) {
			EnvUtil.reportError(e);
		}

		return result;
	}

	/*
	 * Loyalty Points
	 */
	public static Map<String, Object> getOrderedSummaryInformation(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		Map result = ServiceUtil.returnSuccess();

		String partyId = (String) context.get("partyId");
		String statusId = (String) context.get("statusId");
		String roleTypeId = (String) context.get("roleTypeId");
		String orderTypeId = (String) context.get("orderTypeId");
		String webSiteId = (String) context.get("webSiteId");
		Integer monthsToInclude = (Integer) context.get("monthsToInclude");

		if(UtilValidate.isEmpty(orderTypeId)) {
			orderTypeId = "SALES_ORDER";
		}
		if(UtilValidate.isEmpty(roleTypeId)) {
			roleTypeId = "PLACING_CUSTOMER";
		}
		if(UtilValidate.isEmpty(statusId)) {
			statusId = "ORDER_SHIPPED";
		}
		if(UtilValidate.isEmpty(webSiteId)) {
			webSiteId = "envelopes";
		}
		if(UtilValidate.isEmpty(monthsToInclude)) {
			monthsToInclude = 12;
		}

		Timestamp fromDate = (Timestamp) context.get("fromDate"); //TODO actually use this
		Timestamp thruDate = (Timestamp) context.get("thruDate"); //TODO actually use this

		Long totalOrders = Long.valueOf("0");
		BigDecimal totalGrandAmount = BigDecimal.ZERO;
		BigDecimal totalSubRemainingAmount = BigDecimal.ZERO;
		String salesChannelEnumId = EnvUtil.getSalesChannelEnumId(webSiteId);

		try {
			GenericValue orderHeaderSummary = EntityQuery.use(delegator).from("OrderHeaderAndRoleSummary")
					.where(
							UtilMisc.toList(
									EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId),
									EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, statusId),
									EntityCondition.makeCondition("roleTypeId", EntityOperator.EQUALS, roleTypeId),
									EntityCondition.makeCondition("orderTypeId", EntityOperator.EQUALS, orderTypeId),
									EntityCondition.makeCondition("salesChannelEnumId", EntityOperator.EQUALS, salesChannelEnumId),
									EntityCondition.makeCondition("orderDate", EntityOperator.GREATER_THAN_EQUAL_TO, EnvUtil.getNMonthsBeforeOrAfterNow((monthsToInclude.intValue() * -1), true))
							)
					).queryFirst();
			if(UtilValidate.isNotEmpty(orderHeaderSummary)) {
				totalOrders = orderHeaderSummary.getLong("totalOrders");
				totalGrandAmount = orderHeaderSummary.getBigDecimal("totalGrandAmount");
				totalSubRemainingAmount = orderHeaderSummary.getBigDecimal("totalSubRemainingAmount");
			}

			List<GenericValue> loyaltyPoints =  EntityQuery.use(delegator).from("LoyaltyPoints")
																			.where(
																				UtilMisc.toList(
																					EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId),
																					EntityCondition.makeCondition("createdStamp", EntityOperator.GREATER_THAN_EQUAL_TO, EnvUtil.getNMonthsBeforeOrAfterNow((monthsToInclude.intValue() * -1), true))
																				)
																			).queryList();
			if(UtilValidate.isNotEmpty(loyaltyPoints)) {
				for(GenericValue points : loyaltyPoints) {
					totalSubRemainingAmount = totalSubRemainingAmount.add(BigDecimal.valueOf(points.getLong("points").longValue()));
				}
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error in getOrderedSummaryInformation service for partyId: " + partyId, module);
		}

		result.put("totalOrders", totalOrders);
		result.put("totalGrandAmount", totalGrandAmount);
		result.put("totalSubRemainingAmount", totalSubRemainingAmount.setScale(0, BigDecimal.ROUND_FLOOR));
		result.put("discountRate", PartyHelper.getLoyaltyDiscount(totalSubRemainingAmount));

		return result;
	}
}