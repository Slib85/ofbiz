/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.order;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.envelopes.addressbook.AddressBookEvents;
import com.envelopes.cxml.CXMLHelper;
import com.envelopes.http.FileHelper;
import com.envelopes.party.PartyHelper;
import com.envelopes.promo.PromoHelper;
import com.envelopes.quote.QuoteHelper;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.*;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.jdbc.SQLProcessor;
import org.apache.ofbiz.entity.model.DynamicViewEntity;
import org.apache.ofbiz.entity.model.ModelKeyMap;
import org.apache.ofbiz.entity.transaction.GenericTransactionException;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.entity.util.*;
import org.apache.ofbiz.order.order.OrderReadHelper;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.envelopes.product.ProductHelper;
import com.envelopes.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OrderHelper {
	public static final String module = OrderHelper.class.getName();

	private static Map<String, GenericValue> ORDER_ATTR_MAP;
	private static Map<String, List> ORDER_ATTR_OPTIONS_MAP;
	private static final List<String> COLOR_LIST = new ArrayList(Arrays.asList("0", "1", "2", "4"));
	private static final List<String> INDICATOR_LIST = new ArrayList(Arrays.asList("N", "Y"));
	private static final List<String> CUT_LIST = new ArrayList(Arrays.asList("0", "1", "2"));
	private static Map<String, String> STATUS_TYPES = new HashMap<String, String>();

	static {
		ORDER_ATTR_MAP = new LinkedHashMap<String, GenericValue>();

		ORDER_ATTR_OPTIONS_MAP = new HashMap<String, List>();
		ORDER_ATTR_OPTIONS_MAP.put("colorsFront", COLOR_LIST);
		ORDER_ATTR_OPTIONS_MAP.put("colorsBack", COLOR_LIST);
		ORDER_ATTR_OPTIONS_MAP.put("whiteInkFront", INDICATOR_LIST);
		ORDER_ATTR_OPTIONS_MAP.put("whiteInkBack", INDICATOR_LIST);
		ORDER_ATTR_OPTIONS_MAP.put("addresses", null);
		ORDER_ATTR_OPTIONS_MAP.put("isFolded", INDICATOR_LIST);
		ORDER_ATTR_OPTIONS_MAP.put("isFullBleed", INDICATOR_LIST);
		ORDER_ATTR_OPTIONS_MAP.put("cuts", CUT_LIST);
	}

	/*
	 * Order Map object to be be used mostly as JSON data
	 */
	public static Map<String, Object> getOrderData(Delegator delegator, String orderId, boolean simpleData) throws GenericEntityException {
		return getOrderData(delegator, orderId, null, simpleData);
	}
	public static Map<String, Object> getOrderData(Delegator delegator, String orderId, String orderItemSeqId, boolean simpleData) throws GenericEntityException {
		if(UtilValidate.isEmpty(orderId)) {
			return null;
		}

		OrderReadHelper orh = new OrderReadHelper(delegator, orderId);
		Map<String, Object> data = new HashMap<String, Object>();

		GenericValue shippingAddress = getShippingAddress(orh, delegator, orderId);

		data.put("orderHeader", orh.getOrderHeader());
		data.put("isRush", isRushOrder(orh));
		data.put("status", getOrderAndItemStatus(delegator, orderId, null));
		data.put("shippingAddress", shippingAddress);
		data.put("shippingTelecom", getTelecomNumber(orh, delegator, orderId, "PHONE_SHIPPING"));
		data.put("billingAddress", getBillingAddress(orh, delegator, orderId));
		data.put("billingTelecom", getTelecomNumber(orh, delegator, orderId, "PHONE_BILLING"));
		data.put("isBlindShipment", UtilValidate.isNotEmpty(shippingAddress) ? isBlindShipment(delegator, shippingAddress.getString("contactMechId")) : null);
		data.put("businessOrResidence", UtilValidate.isNotEmpty(shippingAddress) ? businessOrResidence(delegator, shippingAddress.getString("contactMechId")) : null);
		data.put("payments", getPaymentData(orh, delegator, orderId));
		data.put("items", getOrderItemData(orh, delegator, orderId, orderItemSeqId, simpleData));
		data.put("headerAdjustments", orh.getOrderHeaderAdjustments()); //order header adjustments
		data.put("adjustments", orh.getAdjustments()); //all adjustments
		data.put("email", orh.getOrderEmailString());
		data.put("person", orh.getBillToParty());
		data.put("notes", getOrderNotes(delegator, orderId));
		data.put("shipping", orh.getShippingMethod("00001", true));
		data.put("shipGroup", orh.getOrderItemShipGroup("00001"));
		data.put("outsourceable", isOutsourceable(orh, delegator, null));
		data.put("outsourced", isOutsourced(orh, delegator, null));
		data.put("pendingChange", isPendingChange(orh, delegator, null));
		data.put("isSyracuseable", isSyracuseable(delegator, orderId));

		// Get sales rep information if tied to user account.
		HashMap<String, String> salesRepData = new HashMap<>();
		salesRepData.put("email", null);
		salesRepData.put("name", null);
		salesRepData.put("phone", null);

		String partyId = ((GenericValue) data.get("person")).getString("partyId");

		try {
			if (UtilValidate.isNotEmpty(data.get("person")) && UtilValidate.isNotEmpty(((GenericValue) data.get("person")).getString("partyId"))) {
				GenericValue party = EntityQuery.use(delegator).from("Party").where("partyId", partyId).queryOne();

				if (UtilValidate.isNotEmpty(party)) {
					salesRepData.put("email", party.getString("salesRep"));

					if (UtilValidate.isNotEmpty(party.getString("salesRep"))) {
						GenericValue salesRepUserLogin = EntityQuery.use(delegator).from("UserLogin").where("userLoginId", party.getString("salesRep")).queryOne();

						if (UtilValidate.isNotEmpty(salesRepUserLogin)) {
							GenericValue salesRep = EntityQuery.use(delegator).from("Person").where("partyId", salesRepUserLogin.getString("partyId")).queryOne();
							salesRepData.put("name", salesRep.getString("firstName") + " " + salesRep.getString("lastName"));
							salesRepData.put("phone", (String) ((Map<String, Object>) ((Map<String, Object>) QuoteHelper.foldersAssignedToUsers).get((QuoteHelper.foldersAssignedToUsers.containsKey(party.getString("salesRep")) ? party.getString("salesRep") : "default"))).get("phoneNumber"));
						}
					}
				}
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to get Sales Rep from table Party for party id " + partyId + ".", module);
		}

		data.put("salesRep", salesRepData);

		return data;
	}

	public static GenericValue getOrderHeader(Delegator delegator, String orderId) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(orderId)) {
			return EntityQuery.use(delegator).from("OrderHeader").where("orderId", orderId).queryOne();
		}

		return null;
	}

	/*
	 * Generate map object of all order totals and order level adjustments
	 */
	public static List<Map> getOrderTotals(Delegator delegator, String orderId) throws GenericEntityException {
		if(UtilValidate.isEmpty(orderId)) {
			return null;
		}

		OrderReadHelper orh = new OrderReadHelper(delegator, orderId);
		List<Map> data = new ArrayList<Map>();
		List<Map> promos = new ArrayList<Map>();
		List<Map> manualFees = new ArrayList<Map>();
		List<Map> manualDiscounts = new ArrayList<Map>();

		BigDecimal tax = BigDecimal.ZERO;
		BigDecimal taxRate = BigDecimal.ZERO;
		BigDecimal shipping = BigDecimal.ZERO;

		//get subtotal
		data.add(UtilMisc.toMap("Subtotal", orh.getOrderItemsSubTotal()));

		//get tax rate based on shipping zip
		GenericValue shipTo = OrderHelper.getShippingAddress(orh, delegator, orderId);
		if(shipTo != null && UtilValidate.isNotEmpty(shipTo.getString("postalCode"))) {
			GenericValue zipSalesTax = EntityQuery.use(delegator).from("ZipSalesTaxLookup").where("zipCode", shipTo.getString("postalCode")).cache().queryFirst();
			if(zipSalesTax != null && UtilValidate.isNotEmpty(zipSalesTax.get("comboSalesTax"))) {
				taxRate = new BigDecimal(zipSalesTax.getString("comboSalesTax"));
			}
		}

		//get all adjustments
		List<GenericValue> adjustments = orh.getOrderHeaderAdjustments();
		if(UtilValidate.isNotEmpty(adjustments)) {
			Map<String, Object> discounts = new HashMap<>();

			for(GenericValue adjustment : adjustments) {
				BigDecimal discount = null;
				
				if(adjustment.getString("orderAdjustmentTypeId").equals("SALES_TAX")) {
					tax = adjustment.getBigDecimal("amount");
				} else if(adjustment.getString("orderAdjustmentTypeId").equals("SHIPPING_CHARGES")) {
					shipping = adjustment.getBigDecimal("amount");
					if ("PROMO_SHIP_CHARGE".equalsIgnoreCase(adjustment.getString("productPromoActionEnumId"))) {
						discount = adjustment.getBigDecimal("amount").setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);
					}
				} else if(adjustment.getString("orderAdjustmentTypeId").equals("PROMOTION_ADJUSTMENT")) {
					discount = adjustment.getBigDecimal("amount").setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);
				} else if(adjustment.getString("orderAdjustmentTypeId").equals("DISCOUNT_ADJUSTMENT")) {
					manualDiscounts.add(UtilMisc.toMap(adjustment.getString("description"), adjustment.getBigDecimal("amount")));
				} else if(adjustment.getString("orderAdjustmentTypeId").equals("FEE")) {
					manualFees.add(UtilMisc.toMap(adjustment.getString("description"), adjustment.getBigDecimal("amount")));
				}

				if (UtilValidate.isNotEmpty(discount)) {
					BigDecimal existingDiscount = BigDecimal.ZERO;

					if (UtilValidate.isNotEmpty(discounts.get(adjustment.get("description")))) {
						existingDiscount = new BigDecimal(String.valueOf(discounts.get(adjustment.get("description"))));
					}

					discounts.put((String) adjustment.get("description"), discount.add(existingDiscount));
				}
			}

			for (Map.Entry entrySet : discounts.entrySet()) {
				promos.add(UtilMisc.toMap(entrySet.getKey(), entrySet.getValue()));
			}
		}

		if(UtilValidate.isNotEmpty(promos)) {
			for(Map promo : promos) {
				Iterator iter = promo.entrySet().iterator();
				while(iter.hasNext()) {
					Map.Entry pairs = (Map.Entry) iter.next();
					data.add(UtilMisc.toMap((String) pairs.getKey(), pairs.getValue()));
				}
			}
		}
		if(UtilValidate.isNotEmpty(manualDiscounts)) {
			for(Map discount : manualDiscounts) {
				Iterator iter = discount.entrySet().iterator();
				while(iter.hasNext()) {
					Map.Entry pairs = (Map.Entry) iter.next();
					data.add(UtilMisc.toMap((String) pairs.getKey(), pairs.getValue()));
				}
			}
		}
		if(UtilValidate.isNotEmpty(manualFees)) {
			for(Map fee : manualFees) {
				Iterator iter = fee.entrySet().iterator();
				while(iter.hasNext()) {
					Map.Entry pairs = (Map.Entry) iter.next();
					data.add(UtilMisc.toMap((String) pairs.getKey(), pairs.getValue()));
				}
			}
		}

		data.add(UtilMisc.toMap("Tax", tax, "Rate", taxRate));
		data.add(UtilMisc.toMap("Shipping", shipping));
		data.add(UtilMisc.toMap("Refunds", getTotalRefundAmount(orh, delegator, orderId)));

		return data;
	}

	/*
	 * Get remaining refundable amount
	 */
	public static BigDecimal getRefundableAmount(Delegator delegator, String orderId) throws GenericEntityException {
		BigDecimal refundableAmount = BigDecimal.ZERO;
		List<Map> orderTotals = getOrderTotals(delegator, orderId);
		for(Map total : orderTotals) {
			Iterator totalsIter = total.keySet().iterator();
			while(totalsIter.hasNext()) {
				String key = (String) totalsIter.next();
				refundableAmount = refundableAmount.add((BigDecimal) total.get(key));
			}
		}

		return refundableAmount;
	}

	public static boolean isOwnerOfOrder(OrderReadHelper orh, Delegator delegator, String orderId, GenericValue userLogin) throws GenericEntityException {
		if(orh == null) {
			orh = new OrderReadHelper(delegator, orderId);
		}
		GenericValue person = orh.getBillToParty();
		if(UtilValidate.isNotEmpty(person) && UtilValidate.isNotEmpty(userLogin) && person.getString("partyId").equals(userLogin.getString("partyId"))) {
			return true;
		}

		return false;
	}

	/*
	 * Get shipping address
	 */
	public static GenericValue getShippingAddress(OrderReadHelper orh, Delegator delegator, String orderId) throws GenericEntityException {
		if(orh == null) {
			orh = new OrderReadHelper(delegator, orderId);
		}
		List<GenericValue> shippingAddresses = orh.getShippingLocations();
		if(UtilValidate.isNotEmpty(shippingAddresses)) {
			return shippingAddresses.get(0);
		}

		return null;
	}

	/**
	 * See if the address is part of US territories
	 * @param address
	 * @return
	 */
	public static boolean isUSTerritory(GenericValue address) {
		List<String> territories = new ArrayList<>();
		territories.add("AS");
		territories.add("FM");
		territories.add("GU");
		territories.add("MH");
		territories.add("MP");
		territories.add("PR");
		territories.add("PW");
		territories.add("UM");
		territories.add("VI");
		territories.add("AA");
		territories.add("AE");
		territories.add("AP");
		territories.add("PR");

		return address != null && territories.contains(address.getString("stateProvinceGeoId"));
	}

	/*
	 * Get blind shipment
	 */
	public static boolean isBlindShipment(Delegator delegator, String contactMechId) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(contactMechId)) {
			GenericValue isBS = delegator.findOne("ContactMechAttribute", UtilMisc.toMap("contactMechId", contactMechId, "attrName", "isBlindShipment"), false);
			if(isBS != null && isBS.getString("attrValue").equals("Y")) {
				return true;
			}
		}

		return false;
	}

	/*
	 * Get residential or buisness address
	 */
	public static boolean businessOrResidence(Delegator delegator, String contactMechId) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(contactMechId)) {
			GenericValue isBS = delegator.findOne("ContactMechAttribute", UtilMisc.toMap("contactMechId", contactMechId, "attrName", "businessOrResidence"), false);
			if(isBS != null && isBS.getString("attrValue").equals("Y")) {
				return true;
			}
		}

		return false;
	}

	/*
	 * Get billing address
	 */
	public static GenericValue getBillingAddress(OrderReadHelper orh, Delegator delegator, String orderId) throws GenericEntityException {
		if(orh == null) {
			orh = new OrderReadHelper(delegator, orderId);
		}
		List<GenericValue> billingAddresses = orh.getBillingLocations();
		if(UtilValidate.isNotEmpty(billingAddresses)) {
			return billingAddresses.get(0);
		}

		return null;
	}

	/*
	 * Get telecom number
	 */
	public static GenericValue getTelecomNumber(OrderReadHelper orh, Delegator delegator, String orderId, String contactMechPurposeTypeId) throws GenericEntityException {
		if(orh == null) {
			orh = new OrderReadHelper(delegator, orderId);
		}
		GenericValue partyContactMechPurpose = EntityUtil.getFirst(EntityUtil.filterByDate(delegator.findByAnd("PartyContactMechPurpose", UtilMisc.toMap("partyId", orh.getBillToParty().getString("partyId"), "contactMechPurposeTypeId", contactMechPurposeTypeId), UtilMisc.toList("createdStamp DESC"), false)));
		if(UtilValidate.isNotEmpty(partyContactMechPurpose)) {
			return delegator.findOne("TelecomNumber", UtilMisc.toMap("contactMechId", partyContactMechPurpose.getString("contactMechId")), false);
		}
		return null;
	}

	/*
	 * Get billing address
	 */
	public static List<Map> getPaymentData(OrderReadHelper orh, Delegator delegator, String orderId) throws GenericEntityException {
		if(orh == null) {
			orh = new OrderReadHelper(delegator, orderId);
		}

		List<Map> payments = new ArrayList<>();
		List<GenericValue> paymentPreferences = orh.getPaymentPreferences();
		if(UtilValidate.isNotEmpty(paymentPreferences)) {
			for(GenericValue paymentPreference : paymentPreferences) {
				Map<String, Object> paymentData = new HashMap<>();
				paymentData.put("paymentMethodTypeId", paymentPreference.getString("paymentMethodTypeId"));
				paymentData.put("maxAmount", paymentPreference.getString("maxAmount"));
				paymentData.put("orderPaymentPreferenceId", paymentPreference.getString("orderPaymentPreferenceId"));
				paymentData.put("paymentMethodTypeIdDesc", (delegator.findOne("PaymentMethodType", UtilMisc.toMap("paymentMethodTypeId", paymentPreference.getString("paymentMethodTypeId")), true)).getString("description"));
				paymentData.put("statusId", (delegator.findOne("StatusItem", UtilMisc.toMap("statusId", paymentPreference.getString("statusId")), true)).getString("description"));
				if(paymentPreference.getString("paymentMethodTypeId").equalsIgnoreCase("CREDIT_CARD")) {
					GenericValue creditCard = paymentPreference.getRelatedOne("CreditCard", false);
					paymentData.put("cardTypeId", creditCard.getString("cardType"));
					GenericValue cardType = null;
					paymentData.put("cardType", UtilValidate.isNotEmpty(cardType = delegator.findOne("Enumeration", UtilMisc.toMap("enumId", (creditCard.getString("cardType"))), true)) ? cardType.getString("description") : "");
					paymentData.put("cardNumber", formatCreditCard(creditCard));
					paymentData.put("cardNumberUnMasked", creditCard.getString("cardNumber"));
					paymentData.put("expireDate", creditCard.getString("expireDate"));
				} else if(paymentPreference.getString("paymentMethodTypeId").equalsIgnoreCase("EXT_AMAZON") || paymentPreference.getString("paymentMethodTypeId").equalsIgnoreCase("EXT_PAYPAL_CHECKOUT")) {
					paymentData.put("cardType", orh.getExternalId());
				} else if(paymentPreference.getString("paymentMethodTypeId").equalsIgnoreCase("PERSONAL_CHECK")) {
					paymentData.put("cardType", "CHECK");
				} else if(paymentPreference.getString("paymentMethodTypeId").equalsIgnoreCase("EXT_NET30")) {
					paymentData.put("cardType", "NET30");
				}
				List<GenericValue> gatewayResponses = delegator.findByAnd("PaymentGatewayResponse", UtilMisc.toMap("orderPaymentPreferenceId", paymentPreference.getString("orderPaymentPreferenceId")), UtilMisc.toList("transactionDate DESC"), false);
				List<Map> responseData = new ArrayList<>();
				Gson gson = new Gson();

				for(GenericValue gatewayResponse : gatewayResponses) {
					Map<String, String> requestMessage = gson.fromJson(gatewayResponse.getString("requestMsg"), HashMap.class);

					responseData.add(UtilMisc.toMap(
						"creditCardToken", (UtilValidate.isNotEmpty(requestMessage) ? requestMessage.get("creditCardToken") : null),
						"transactionId", (UtilValidate.isNotEmpty(requestMessage) ? requestMessage.get("transactionId") : null),
						"referenceNum", (UtilValidate.isNotEmpty(gatewayResponse) ? gatewayResponse.getString("referenceNum") : null),
						"gatewayCode", (UtilValidate.isNotEmpty(gatewayResponse) ? gatewayResponse.getString("gatewayCode") : null),
						"gatewayMessage", (UtilValidate.isNotEmpty(gatewayResponse) ? gatewayResponse.getString("gatewayMessage") : null),
						"gatewayFlag", (UtilValidate.isNotEmpty(gatewayResponse) ? gatewayResponse.getString("gatewayFlag") : null),
						"transactionDate", (UtilValidate.isNotEmpty(gatewayResponse) ? gatewayResponse.getTimestamp("transactionDate") : null),
						"cardType", (UtilValidate.isNotEmpty(requestMessage) ? requestMessage.get("cardType") : null)
					));
				}
				paymentData.put("responseData", responseData);
				//paymentData.put("refunds", getTotalRefundAmount(orh, delegator, orderId));
				payments.add(paymentData);
				break;
			}
		}

		return payments;
	}

	/*
	 * Get total refunded
	 */
	public static BigDecimal getTotalRefundAmount(OrderReadHelper orh, Delegator delegator, String orderId) throws GenericEntityException {
		if(orh == null) {
			orh = new OrderReadHelper(delegator, orderId);
		}

		BigDecimal totalRefunded = BigDecimal.ZERO;

		List<GenericValue> paymentPreferences = orh.getPaymentPreferences();
		if(UtilValidate.isNotEmpty(paymentPreferences)) {
			for (GenericValue paymentPreference : paymentPreferences) {
				List<GenericValue> responses = paymentPreference.getRelated("PaymentGatewayResponse", UtilMisc.toMap("transCodeEnumId", "PGT_REFUND"), null, false);
				for(GenericValue response : responses) {
					BigDecimal amount = response.getBigDecimal("amount");
					if(amount != null) {
						totalRefunded = totalRefunded.add(response.getBigDecimal("amount"));
					}
				}
			}
		}

		if(totalRefunded.compareTo(BigDecimal.ZERO) > 0) {
			totalRefunded = totalRefunded.multiply(new BigDecimal("-1"));
		}
		return totalRefunded;
	}

	/*
	 * Get the Order Item
	 */
	public static GenericValue getOrderItem(Delegator delegator, String orderId, String orderItemSeqId) throws GenericEntityException {
		GenericValue orderItem = EntityQuery.use(delegator).from("OrderItem").where("orderId", orderId, "orderItemSeqId", orderItemSeqId).queryOne();
		return orderItem;
	}

	/*
	 * Get the Order Items
	 */
	public static List<GenericValue> getOrderItems(OrderReadHelper orh, Delegator delegator, String orderId) throws GenericEntityException {
		if(orh == null) {
			orh = new OrderReadHelper(delegator, orderId);
		}

		List<GenericValue> orderItems = orh.getOrderItems();
		return orderItems;
	}

	/*
	 * Get Scene7 Order Items
	 */
	public static List<GenericValue> getS7OrderItemArtworks(Delegator delegator, String orderId) throws GenericEntityException {
		List<GenericValue> s7List = new ArrayList<GenericValue>();

		List<GenericValue> orderItems = delegator.findByAnd("OrderItemArtwork", UtilMisc.toMap("orderId", orderId), null, false);
		for(GenericValue orderItem : orderItems) {
			if(UtilValidate.isNotEmpty(orderItem.getString("scene7DesignId"))) {
				s7List.add(orderItem);
			}
		}

		return s7List;
	}

	/*
	 * Get the Order Item Artwork
	 */
	public static GenericValue getOrderItemArtwork(Delegator delegator, String orderId, String orderItemSeqId) throws GenericEntityException {
		GenericValue orderItemArtwork = null;

		if(UtilValidate.isNotEmpty(orderId) && UtilValidate.isNotEmpty(orderItemSeqId)) {
			orderItemArtwork = EntityUtil.getFirst(delegator.findByAnd("OrderItemArtwork", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), UtilMisc.toList("createdStamp DESC"), false));
		}

		return orderItemArtwork;
	}

	/*
	 * Get the Order Item Artwork Content
	 */
	public static List<GenericValue> getOrderItemContent(Delegator delegator, String orderId, String orderItemSeqId) throws GenericEntityException {
		return getOrderItemContent(delegator, orderId, orderItemSeqId, new ArrayList<>());
	}
	public static List<GenericValue> getOrderItemContent(Delegator delegator, String orderId, String orderItemSeqId, String contentPurposeEnumId) throws GenericEntityException {
		return getOrderItemContent(delegator, orderId, orderItemSeqId, UtilMisc.toList(contentPurposeEnumId));
	}
	public static List<GenericValue> getOrderItemContent(Delegator delegator, String orderId, String orderItemSeqId, List<String> contentPurposeEnumIdList) throws GenericEntityException {
		List<GenericValue> orderItemContent = new ArrayList<>();

		if(UtilValidate.isNotEmpty(orderId) && UtilValidate.isNotEmpty(orderItemSeqId)) {
			List<EntityCondition> conditionList = new ArrayList<>();
			conditionList.add(EntityCondition.makeCondition("orderId", EntityOperator.EQUALS, orderId));
			conditionList.add(EntityCondition.makeCondition("orderItemSeqId", EntityOperator.EQUALS, orderItemSeqId));
			if (UtilValidate.isNotEmpty(contentPurposeEnumIdList)) {
				conditionList.add(EntityCondition.makeCondition("contentPurposeEnumId", EntityOperator.IN, contentPurposeEnumIdList));
			}

			orderItemContent = EntityQuery.use(delegator).from("OrderItemContent").where(EntityCondition.makeCondition(conditionList, EntityOperator.AND)).filterByDate().orderBy("createdStamp DESC").queryList();
		}

		return orderItemContent;
	}

	/*
	 * Set the Order Item Artwork Content
	 */
	public static GenericValue setOrderItemContent(Delegator delegator, String orderId, String orderItemSeqId, String contentPurpose, Map<String, Object> file) throws GenericEntityException {
		if(UtilValidate.isEmpty(orderId) || UtilValidate.isEmpty(orderItemSeqId) || UtilValidate.isEmpty(contentPurpose)) {
			return null;
		}

		GenericValue orderItemContent = delegator.makeValue("OrderItemContent", UtilMisc.toMap("orderItemContentId", delegator.getNextSeqId("OrderItemContent")));
		orderItemContent.set("orderId", orderId);
		orderItemContent.set("orderItemSeqId", orderItemSeqId);
		orderItemContent.set("contentPurposeEnumId", contentPurpose);
		orderItemContent.set("contentPath", (String) file.get("path"));
		orderItemContent.set("contentName", (String) file.get("name"));
		orderItemContent.set("fromDate", UtilDateTime.nowTimestamp());
		delegator.create(orderItemContent);

		return orderItemContent;
	}

	/*
	 * Discontinue older artwork when new ones are uploaded
	 */
	public static void discOrderItemContent(Delegator delegator, String orderId, String orderItemSeqId, String contentPurpose) throws GenericEntityException {
		if(UtilValidate.isEmpty(orderId) || UtilValidate.isEmpty(orderItemSeqId) || UtilValidate.isEmpty(contentPurpose)) {
			return;
		}

		List<GenericValue> orderItemContents = delegator.findByAnd("OrderItemContent", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId, "contentPurposeEnumId", contentPurpose), null, false);
		for(GenericValue orderItemContent : orderItemContents) {
			orderItemContent.set("thruDate", UtilDateTime.nowTimestamp());
			orderItemContent.store();
		}
	}

	public static String[] getOrderItemContentPath(Delegator delegator, String orderId, String orderItemSeqId, String contentPurpose) throws GenericEntityException {
		if(UtilValidate.isEmpty(orderId) || UtilValidate.isEmpty(orderItemSeqId) || UtilValidate.isEmpty(contentPurpose)) {
			return new String[0];
		}

		List<GenericValue> orderItemContents = delegator.findByAnd("OrderItemContent", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId, "contentPurposeEnumId", contentPurpose, "thruDate", null), null, false);
		for(GenericValue orderItemContent : orderItemContents) {
			return new String[] {orderItemContent.getString("contentName"), orderItemContent.getString("contentPath")};
		}
		return new String[0];
	}

	/*
	 * Get the Order Item Attribute
	 */
	public static List<GenericValue> getOrderItemAttribute(Delegator delegator, String orderId, String orderItemSeqId) throws GenericEntityException {
		return getOrderItemAttribute(delegator, orderId, orderItemSeqId, null);
	}

	public static List<GenericValue> getOrderItemAttribute(Delegator delegator, String orderId, String orderItemSeqId, String attrName) throws GenericEntityException {
		List<GenericValue> orderItemAttribute = new ArrayList<GenericValue>();

		if(UtilValidate.isNotEmpty(orderId) && UtilValidate.isNotEmpty(orderItemSeqId)) {
			if(attrName != null) {
				orderItemAttribute = delegator.findByAnd("OrderItemAttribute", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId, "attrName", attrName), null, false);
			} else {
				orderItemAttribute = delegator.findByAnd("OrderItemAttribute", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), null, false);
			}
		}

		return orderItemAttribute;
	}

	/*
	 * Get the Order Item Attribute (cleaned version with just key value pair)
	 */
	public static Map<String, String> getOrderItemAttributeMap(Delegator delegator, String orderId, String orderItemSeqId) throws GenericEntityException {
		Map<String, String> attributes = new HashMap<String, String>();
		
		Iterator iter = getOrderItemAttributeMap(delegator, orderId, orderItemSeqId, false).entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry pairs = (Map.Entry) iter.next();
			attributes.put((String) pairs.getKey(), (String) pairs.getValue());
		}

		return attributes;
	}

	public static Map<String, Object> getOrderItemAttributeMap(Delegator delegator, String orderId, String orderItemSeqId, boolean includeDescription) throws GenericEntityException {
		Map<String, Object> attributes = new HashMap<String, Object>();
		if(UtilValidate.isNotEmpty(orderId) && UtilValidate.isNotEmpty(orderItemSeqId)) {
			
			if (includeDescription) {
				List<GenericValue> orderItemAttributes = EntityQuery.use(delegator).from("OrderItemAttributeAndProductAttribute").where("orderId", orderId, "orderItemSeqId", orderItemSeqId).queryList();
				for(GenericValue attr : orderItemAttributes) {
					Map<String, String> orderItemAttributeInfo = new HashMap<String, String>();
					orderItemAttributeInfo.put("value", attr.getString("attrValue"));
					orderItemAttributeInfo.put("description", attr.getString("attrDescription"));
					attributes.put(attr.getString("attrName"), orderItemAttributeInfo);
				}
			} else {
				List<GenericValue> orderItemAttributes = delegator.findByAnd("OrderItemAttribute", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), null, false);
				for(GenericValue attr : orderItemAttributes) {
					attributes.put(attr.getString("attrName"), attr.getString("attrValue"));
				}
			}
		}

		return attributes;
	}

	/*
	 * Get List of ink colors
	 */
	public static String getOrderItemInkList(GenericValue orderItemArtwork, boolean isFront) {
		if(orderItemArtwork == null) {
			return null;
		}

		StringBuilder inkList = new StringBuilder("");
		if(isFront) {
			if(UtilValidate.isNotEmpty(orderItemArtwork.getString("frontInkColor1"))) {
				if(UtilValidate.isNotEmpty(inkList.toString())) {
					inkList.append(", ");
				}
				inkList.append(orderItemArtwork.getString("frontInkColor1"));
			}
			if(UtilValidate.isNotEmpty(orderItemArtwork.getString("frontInkColor2"))) {
				if(UtilValidate.isNotEmpty(inkList.toString())) {
					inkList.append(", ");
				}
				inkList.append(orderItemArtwork.getString("frontInkColor2"));
			}
			if(UtilValidate.isNotEmpty(orderItemArtwork.getString("frontInkColor3"))) {
				if(UtilValidate.isNotEmpty(inkList.toString())) {
					inkList.append(", ");
				}
				inkList.append(orderItemArtwork.getString("frontInkColor3"));
			}
			if(UtilValidate.isNotEmpty(orderItemArtwork.getString("frontInkColor4"))) {
				if(UtilValidate.isNotEmpty(inkList.toString())) {
					inkList.append(", ");
				}
				inkList.append(orderItemArtwork.getString("frontInkColor4"));
			}
		} else {
			if(UtilValidate.isNotEmpty(orderItemArtwork.getString("backInkColor1"))) {
				if(UtilValidate.isNotEmpty(inkList.toString())) {
					inkList.append(", ");
				}
				inkList.append(orderItemArtwork.getString("backInkColor1"));
			}
			if(UtilValidate.isNotEmpty(orderItemArtwork.getString("backInkColor2"))) {
				if(UtilValidate.isNotEmpty(inkList.toString())) {
					inkList.append(", ");
				}
				inkList.append(orderItemArtwork.getString("backInkColor2"));
			}
			if(UtilValidate.isNotEmpty(orderItemArtwork.getString("backInkColor3"))) {
				if(UtilValidate.isNotEmpty(inkList.toString())) {
					inkList.append(", ");
				}
				inkList.append(orderItemArtwork.getString("backInkColor3"));
			}
			if(UtilValidate.isNotEmpty(orderItemArtwork.getString("backInkColor4"))) {
				if(UtilValidate.isNotEmpty(inkList.toString())) {
					inkList.append(", ");
				}
				inkList.append(orderItemArtwork.getString("backInkColor4"));
			}
		}

		if(UtilValidate.isEmpty(inkList.toString())) {
			inkList.append("N/A");
		}

		return inkList.toString();
	}

	/*
	 * Get List of ink colors
	 */
	public static String getOrderItemInkList(GenericValue orderItemArtwork) {
		if(orderItemArtwork == null) {
			return null;
		}

		StringBuilder inkList = new StringBuilder("");
		if(UtilValidate.isNotEmpty(orderItemArtwork.getString("frontInkColor1"))) {
			if(UtilValidate.isNotEmpty(inkList.toString())) {
				inkList.append(", ");
			}
			inkList.append(orderItemArtwork.getString("frontInkColor1"));
		}
		if(UtilValidate.isNotEmpty(orderItemArtwork.getString("frontInkColor2"))) {
			if(UtilValidate.isNotEmpty(inkList.toString())) {
				inkList.append(", ");
			}
			inkList.append(orderItemArtwork.getString("frontInkColor2"));
		}
		if(UtilValidate.isNotEmpty(orderItemArtwork.getString("frontInkColor3"))) {
			if(UtilValidate.isNotEmpty(inkList.toString())) {
				inkList.append(", ");
			}
			inkList.append(orderItemArtwork.getString("frontInkColor3"));
		}
		if(UtilValidate.isNotEmpty(orderItemArtwork.getString("frontInkColor4"))) {
			if(UtilValidate.isNotEmpty(inkList.toString())) {
				inkList.append(", ");
			}
			inkList.append(orderItemArtwork.getString("frontInkColor4"));
		}
		if(UtilValidate.isNotEmpty(orderItemArtwork.getString("backInkColor1"))) {
			if(UtilValidate.isNotEmpty(inkList.toString())) {
				inkList.append(", ");
			}
			inkList.append(orderItemArtwork.getString("backInkColor1"));
		}
		if(UtilValidate.isNotEmpty(orderItemArtwork.getString("backInkColor2"))) {
			if(UtilValidate.isNotEmpty(inkList.toString())) {
				inkList.append(", ");
			}
			inkList.append(orderItemArtwork.getString("backInkColor2"));
		}
		if(UtilValidate.isNotEmpty(orderItemArtwork.getString("backInkColor3"))) {
			if(UtilValidate.isNotEmpty(inkList.toString())) {
				inkList.append(", ");
			}
			inkList.append(orderItemArtwork.getString("backInkColor3"));
		}
		if(UtilValidate.isNotEmpty(orderItemArtwork.getString("backInkColor4"))) {
			if(UtilValidate.isNotEmpty(inkList.toString())) {
				inkList.append(", ");
			}
			inkList.append(orderItemArtwork.getString("backInkColor4"));
		}

		if(UtilValidate.isEmpty(inkList.toString())) {
			inkList.append("N/A");
		}

		return inkList.toString();
	}

	/*
	 * Get the Order Items and Related Artwork Data
	 */
	public static List<Map> getOrderItemData(OrderReadHelper orh, Delegator delegator, String orderId, String orderItemSeqId, boolean simpleData) throws GenericEntityException {
		if(orh == null) {
			orh = new OrderReadHelper(delegator, orderId);
		}

		List<Map> allItems = new ArrayList<Map>();
		List<GenericValue> orderItems = orh.getOrderItems();
		for(GenericValue orderItem : orderItems) {
			if(orderItemSeqId != null && !orderItemSeqId.equalsIgnoreCase(orderItem.getString("orderItemSeqId"))) {
				continue;
			}
			Map<String, Object> innerData = new HashMap<String, Object>();
			GenericValue product = delegator.findOne("Product", UtilMisc.toMap("productId", orderItem.getString("productId")), true);
			try {
				GenericValue warehouse = EntityQuery.use(delegator).from("ColorWarehouse").where("variantProductId", orderItem.getString("productId")).cache().queryFirst();
				if(UtilValidate.isNotEmpty(warehouse)) {
					innerData.put("hex", warehouse.get("colorHexCode"));
				}
			} catch (Exception e) {
				EnvUtil.reportError(e);
			}
			GenericValue artwork = getOrderItemArtwork(delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId"));
			innerData.put("item", orderItem);
			innerData.put("status", getOrderAndItemStatus(delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId")));
			innerData.put("product", product);
			innerData.put("artwork", artwork);
			innerData.put("isSample", isItemSample(orderItem, product));
			innerData.put("isCustom", ProductHelper.isCustomSku(product.getString("productId")));
			innerData.put("comments", getArtworkComment(delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId"), (artwork != null) ? artwork.getString("orderItemArtworkId") : null));
			innerData.put("content", getOrderItemContent(delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId")));
			innerData.put("attribute", (simpleData) ? getOrderItemAttributeMap(delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId")) : getOrderItemAttribute(delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId")));
			innerData.put("features", ProductHelper.getProductFeatures(delegator, product, null));
			innerData.put("adjustments", orh.getOrderItemAdjustments(orderItem));
			innerData.put("shipGroupAssoc", EntityUtil.getFirst(orh.getOrderItemShipGroupAssocs(orderItem)));
			innerData.put("autoOutsourceable", CXMLHelper.autoOutsourceable(delegator, null, null, orderItem));
			innerData.put("vendorOrder", delegator.findOne("VendorOrder", UtilMisc.toMap("orderId", orderItem.getString("orderId"), "orderItemSeqId", orderItem.getString("orderItemSeqId")), false));
			innerData.put("scene7DesignParentId", (UtilValidate.isNotEmpty(artwork) && UtilValidate.isNotEmpty(artwork.getString("scene7DesignId")) ? getDesignParentId(delegator, artwork.getString("scene7DesignId")) : null));
			if (UtilValidate.isNotEmpty(orderItem.getString("referenceQuoteItemSeqId"))) {
				innerData.put("quoteComments", (String) delegator.findOne("QcQuote", UtilMisc.toMap("quoteId", orderItem.getString("referenceQuoteItemSeqId")), true).get("internalComment"));
			}
			List<GenericValue> contents = delegator.findByAnd("OrderItemContent", UtilMisc.toMap("orderId", orderItem.getString("orderId"), "orderItemSeqId", orderItem.getString("orderItemSeqId"), "thruDate", null), null, false);
			List<Map<String, String>> contentsList = new ArrayList<>();
			for (GenericValue content : contents) {
				Map<String, String> contentMap = new HashMap<>();
				contentMap.put("contentPath", content.getString("contentPath"));
				if(UtilValidate.isNotEmpty(content.getString("contentName"))) {
					contentMap.put("contentName", content.getString("contentName"));
				} else {
					contentMap.put("contentName", content.getString("contentPath").substring(content.getString("contentPath").lastIndexOf("/") + 1));
				}
				contentMap.put("contentPurpose", content.getString("contentPurposeEnumId"));
				contentsList.add(contentMap);
			}
			innerData.put("contents", contentsList);
			allItems.add(innerData);
		}
		return allItems;
	}

	/*
	 * Format the credit card for display
	 */
	public static String formatCreditCard(GenericValue creditCard) {
		String cardNumber = creditCard.getString("cardNumber");
		if(cardNumber != null && cardNumber.length() > 4) {
			return "XXXX-XXXX-XXXX-" + (cardNumber.substring(cardNumber.length() - 4));
		}
		return "XXXX-XXXX-XXXX-XXXX";
	}

	/*
	 * Create OrderItem
	 * Key of Map should be field names of OrderItem table
	 */
	public static GenericValue createOrderItem(Delegator delegator, Map<String, Object> data) throws GenericEntityException {
		GenericValue orderItem = delegator.makeValue("OrderItem");
		Iterator iter = data.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry pairs = (Map.Entry) iter.next();
			orderItem.put((String) pairs.getKey(), pairs.getValue());
		}
		delegator.create(orderItem);
		return orderItem;
	}

	/*
	 * Update order item
	 */
	public static GenericValue updateOrderItem(Delegator delegator, String orderId, String orderItemSeqId, Map<String, ?> data) throws GenericEntityException {
		GenericValue orderItem = delegator.findOne("OrderItem", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), false);

		return updateOrderItem(delegator, orderItem, data);
	}

	/*
	 * Update order item
	 */
	public static GenericValue updateOrderItem(Delegator delegator, GenericValue orderItem, Map<String, ?> data) throws GenericEntityException {
		if(orderItem != null) {
			Iterator iter = data.entrySet().iterator();
			while(iter.hasNext()) {
				Map.Entry pairs = (Map.Entry) iter.next();
				if(orderItem.containsKey(pairs.getKey())) {
					orderItem.set((String) pairs.getKey(), pairs.getValue());
				}
			}
			orderItem.store();
		}

		return orderItem;
	}

	/*
	 * Create OrderItemArtwork
	 * Key of Map should be field names of OrderItemArtwork table
	 */
	public static GenericValue createOrderItemArtwork(Delegator delegator, Map<String, Object> data) throws GenericEntityException {
		GenericValue orderItemArtwork = delegator.makeValue("OrderItemArtwork");
		Iterator iter = data.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry pairs = (Map.Entry) iter.next();
			orderItemArtwork.put((String) pairs.getKey(), pairs.getValue());
		}
		delegator.create(orderItemArtwork);
		return orderItemArtwork;
	}

	/*
	 * Update order item artwork
	 */
	public static GenericValue updateOrderItemArtwork(Delegator delegator, String orderId, String orderItemSeqId, Map<String, Object> data) throws GenericEntityException {
		GenericValue orderItemArtwork = EntityUtil.getFirst(delegator.findByAnd("OrderItemArtwork", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), UtilMisc.toList("createdStamp DESC"), false));

		return updateOrderItemArtwork(delegator, orderItemArtwork, data);
	}

	/*
	 * Update order item artwork
	 */
	public static GenericValue updateOrderItemArtwork(Delegator delegator, GenericValue orderItemArtwork, Map<String, Object> data) throws GenericEntityException {
		if(orderItemArtwork != null) {
			Iterator iter = data.entrySet().iterator();
			while(iter.hasNext()) {
				Map.Entry pairs = (Map.Entry) iter.next();
				if(orderItemArtwork.containsKey(pairs.getKey())) {
					/*if(((String) pairs.getKey()).equals("assignedToUserLogin") && UtilValidate.isNotEmpty(orderItemArtwork.get("assignedToUserLogin")) && pairs.getValue() != null) {
						continue;
					}*/
					orderItemArtwork.set((String) pairs.getKey(), pairs.getValue());
				}
			}
			orderItemArtwork.store();
		}

		return orderItemArtwork;
	}

	/*
	 * Create OrderItemAttribute
	 * Key of Map should be field names of OrderItemAttribute table
	 */
	public static List<GenericValue> createOrderItemAttribute(Delegator delegator, Map<String, Object> data) throws GenericEntityException {
		List<GenericValue> attrList = new ArrayList<GenericValue>();
		Iterator iter = data.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry pairs = (Map.Entry) iter.next();
			GenericValue orderItemAttr = delegator.makeValue("OrderItemAttribute");
			orderItemAttr.put("attrName", (String) pairs.getKey());
			orderItemAttr.put("attrValue", (String) pairs.getValue());
			attrList.add(orderItemAttr);
		}
		delegator.storeAll(attrList);
		return attrList;
	}

	public static void resetOrderAttrMap(Delegator delegator) {
		ORDER_ATTR_MAP = new LinkedHashMap<String, GenericValue>();
		getOrderAttrMap(delegator);
	}

	public static Map<String, GenericValue> getOrderAttrMap(Delegator delegator) {
		if (UtilValidate.isEmpty(ORDER_ATTR_MAP)) {
			try {
				List<GenericValue> productAttributes = EntityQuery.use(delegator).from("ProductAttribute").queryList();

				for (GenericValue productAttribute : productAttributes) {
					ORDER_ATTR_MAP.put(productAttribute.getString("attrName"), productAttribute);
				}
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while setting order attribute map: " + e, module);
			}
		}

		return ORDER_ATTR_MAP;
	}

	/*
	 * Update order item attribute
	 */
	public static List<GenericValue> updateOrderItemAttribute(Delegator delegator, String orderId, String orderItemSeqId, Map<String, Object> data) throws GenericEntityException {
		List<GenericValue> orderItemAttrs = new ArrayList<GenericValue>();

		Iterator iter = data.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry pairs = (Map.Entry) iter.next();
			if(getOrderAttrMap(delegator).containsKey((String) pairs.getKey())) {
				GenericValue orderItemAttr = delegator.findOne("OrderItemAttribute", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId, "attrName", (String) pairs.getKey()), false);
				if(orderItemAttr != null) {
					if (UtilValidate.isEmpty(pairs.getValue())) {
						orderItemAttr.remove();
					} else {
						orderItemAttr.set("attrValue", (String) pairs.getValue());
						orderItemAttr.store();
						orderItemAttrs.add(orderItemAttr);
					}
				} else {
					if (UtilValidate.isNotEmpty((String) pairs.getValue()) && UtilValidate.isNotEmpty((String) data.get("productId"))) {
						if (getOrderAttrMap(delegator).containsKey((String) pairs.getKey()) && !(getOrderAttrMap(delegator).get((String) pairs.getKey())).get("attrValue").equals((String) pairs.getValue())) {
							// Override productId to always be CUSTOM-P.  This table is only used for folders CUSTOM-P product.
							ProductHelper.createProductAttribute(delegator, (String) pairs.getKey(), (String) pairs.getValue(), "CUSTOM-P");
						}
						orderItemAttrs.add(createOrderItemAttribute(delegator, orderId, orderItemSeqId, (String) pairs.getKey(), (String) pairs.getValue()));
					}
				}
			}
		}
		return orderItemAttrs;
	}

	/*
	 * Create OrderItemAttribute
	 */
	public static GenericValue createOrderItemAttribute(Delegator delegator, String orderId, String orderItemSeqId, String key, String value) throws GenericEntityException {
		GenericValue orderItemAttr = delegator.makeValue("OrderItemAttribute", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId, "attrName", key, "attrValue", value));
		delegator.create(orderItemAttr);
		return orderItemAttr;
	}

	/*
	 * Remove all order item attributes
	 */
	public static void removeAllOrderItemAttributes(Delegator delegator, String orderId, String orderItemSeqId) throws GenericEntityException {
		List<GenericValue> orderItemAttrs = delegator.findByAnd("OrderItemAttribute", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), null, false);
		if(UtilValidate.isNotEmpty(orderItemAttrs)) {
			delegator.removeAll(orderItemAttrs);
		}
	}

	/*
	 * Check if value is rush or not
	 */
	public static String isRush(String str) {
		if(UtilValidate.isNotEmpty(str) && str.equalsIgnoreCase("Rush")) {
			return "Y";
		}

		return "N";
	}

	public static boolean isRushOrder(OrderReadHelper orderReadHelper) {
		List<GenericValue> orderItems = orderReadHelper.getOrderItems();
		for(GenericValue orderItem : orderItems) {
			if(orderItem != null && "Y".equalsIgnoreCase(orderItem.getString("isRushProduction"))) {
				return true;
			}
		}
		return false;
	}

	public static boolean isRushOrder(List<GenericValue> orderItems) {
		for(GenericValue orderItem : orderItems) {
			if(orderItem != null && "Y".equalsIgnoreCase(orderItem.getString("isRushProduction"))) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Check if order item is rush
	 */
	public static boolean isRush(Delegator delegator, String orderId, String orderItemSeqId, GenericValue orderItem) throws GenericEntityException {
		if(orderItem == null) {
			orderItem = delegator.findOne("OrderItem", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), false);
		}

		return (orderItem != null && "Y".equalsIgnoreCase(orderItem.getString("isRushProduction")));
	}

	public static String getWebSiteId(Delegator delegator, String orderId) {
		try {
			return ((GenericValue)OrderHelper.getOrderData(delegator, orderId, true).get("orderHeader")).getString("webSiteId");
		} catch (GenericEntityException e) {
			return "";
		}
	}

	/*
	 * Build price context from order item to recalc pricing
	 */
	public static Map<String, Object> createPriceContextFromOrder(Delegator delegator, String orderId, String orderItemSeqId) throws GenericEntityException {
		Map<String, Object> priceContext = new HashMap<String, Object>();
		GenericValue orderHeader = delegator.findOne("OrderHeader", UtilMisc.toMap("orderId", orderId), false);
		if(orderHeader != null) {
			GenericValue orderItem = delegator.findOne("OrderItem", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), false);
			if(orderItem != null) {
				priceContext.put("product", delegator.findOne("Product", UtilMisc.toMap("productId", orderItem.getString("productId")), false));
				priceContext.put("id", orderItem.getString("productId"));
				priceContext.put("quantity", orderItem.getBigDecimal("quantity"));
				priceContext.put("isRush", ProductHelper.allowRushProduction(((UtilValidate.isNotEmpty(orderItem.getString("isRushProduction")) && orderItem.getString("isRushProduction").equalsIgnoreCase("Y")) ? true : false)));
				priceContext.put("partyId", (delegator.findOne("UserLogin", UtilMisc.toMap("userLoginId", orderHeader.getString("createdBy")), false).getString("partyId")));

				List<GenericValue> attrs = getOrderItemAttribute(delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId"));
				for(GenericValue attr : attrs) {
					if(attr.getString("attrName").equals("templateId") || attr.getString("attrName").equals("pricingRequest")) {
						priceContext.put(attr.getString("attrName"), attr.getString("attrValue"));
					} else if(attr.getString("attrName").equals("colorsFront") || attr.getString("attrName").equals("colorsBack") || attr.getString("attrName").equals("cuts") || attr.getString("attrName").equals("addresses")) {
						priceContext.put(attr.getString("attrName"), Integer.valueOf(attr.getString("attrValue")));
					} else if(attr.getString("attrName").equals("whiteInkFront") || attr.getString("attrName").equals("whiteInkBack") || attr.getString("attrName").equals("isFolded") || attr.getString("attrName").equals("isFullBleed")) {
						priceContext.put(attr.getString("attrName"), Boolean.valueOf(attr.getString("attrValue")));
					}
				}
			}
		}

		return priceContext;
	}

	/*
	 * Build artwork context from order item
	 */
	public static Map<String, Object> createArtworkContextFromOrder(Delegator delegator, String orderId, String orderItemSeqId) throws GenericEntityException {
		Map<String, Object> artworkContext = new HashMap<String, Object>();
		GenericValue orderItem = delegator.findOne("OrderItem", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), false);

		if(orderItem != null) {
			GenericValue orderItemArtwork = EntityUtil.getFirst(delegator.findByAnd("OrderItemArtwork", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), null, false));
			if(orderItemArtwork != null) {
				artworkContext.put("itemJobName", orderItemArtwork.getString("itemJobName"));
				artworkContext.put("itemInkColor", orderItemArtwork.getString("itemInkColor"));
				artworkContext.put("frontInkColor1", orderItemArtwork.getString("frontInkColor1"));
				artworkContext.put("frontInkColor2", orderItemArtwork.getString("frontInkColor2"));
				artworkContext.put("frontInkColor3", orderItemArtwork.getString("frontInkColor3"));
				artworkContext.put("frontInkColor4", orderItemArtwork.getString("frontInkColor4"));
				artworkContext.put("backInkColor1", orderItemArtwork.getString("backInkColor1"));
				artworkContext.put("backInkColor2", orderItemArtwork.getString("backInkColor2"));
				artworkContext.put("backInkColor3", orderItemArtwork.getString("backInkColor3"));
				artworkContext.put("backInkColor4", orderItemArtwork.getString("backInkColor4"));
				artworkContext.put("itemPrepressComments", orderItemArtwork.getString("itemPrepressComments"));
				artworkContext.put("itemPrintPosition", orderItemArtwork.getString("itemPrintPosition"));
				artworkContext.put("directMailJobId", orderItemArtwork.getString("directMailJobId"));
				artworkContext.put("scene7DesignId", orderItemArtwork.getString("scene7DesignId"));
				artworkContext.put("artworkSource", orderItem.getString("artworkSource"));
			}

			List<GenericValue> orderItemContents = delegator.findByAnd("OrderItemContent", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), null, false);
			if(UtilValidate.isNotEmpty(orderItemContents)) {
				List<Map> contentList = new ArrayList<Map>();
				for(GenericValue orderItemContent : orderItemContents) {
					if(UtilValidate.isEmpty(orderItemContent.get("thruDate"))) {
						contentList.add(UtilMisc.toMap("reOrderId", orderItem.getString("orderId"), "reOrderItemSeqId", orderItem.getString("orderItemSeqId"), "contentPath", orderItemContent.getString("contentPath"), "contentName", orderItemContent.getString("contentName"), "contentPurposeEnumId", orderItemContent.getString("contentPurposeEnumId")));
					}
				}
				artworkContext.put("content", contentList);
			}
		}

		return artworkContext;
	}

	/*
	 * Build artwork context from order item
	 */
	public static Map<String, Object> createQuoteContextFromOrder(Delegator delegator, String orderId, String orderItemSeqId) throws GenericEntityException {
		Map<String, Object> quoteContext = new HashMap<String, Object>();
		GenericValue orderItem = delegator.findOne("OrderItem", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), false);

		if(orderItem != null) {
			quoteContext.put("quoteId", orderItem.getString("referenceQuoteId"));
			quoteContext.put("quoteItemSeqId", orderItem.getString("referenceQuoteItemSeqId"));
		}

		return quoteContext;
	}

	/*
	 * Get order notes
	 */
	public static List<Map> getOrderNotes(Delegator delegator, String orderId) throws GenericEntityException {
		List<Map> orderNoteData = new ArrayList<Map>();
		List<GenericValue> noteData = delegator.findByAnd("OrderHeaderNoteView", UtilMisc.toMap("orderId", orderId), UtilMisc.toList("noteDateTime DESC"), false);
		for(GenericValue note : noteData) {
			GenericValue person = delegator.findOne("Person", UtilMisc.toMap("partyId", note.getString("noteParty")), false);
			orderNoteData.add(UtilMisc.toMap("name", (person.getString("firstName") + " " + person.getString("lastName")), "comment", note.getString("noteInfo"), "createdStamp", note.get("noteDateTime")));
		}

		return orderNoteData;
	}

	/*
	 * Get order notes
	 */
	public static List<Map> addOrderNotes(Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> context) throws GenericEntityException, GenericServiceException {
		if(UtilValidate.isNotEmpty(context.get("orderNote"))) {
			dispatcher.runSync("createOrderNote", UtilMisc.toMap("orderId", (String) context.get("orderId"), "internalNote", (String) context.get("internalNote"), "note", (String) context.get("orderNote"), "userLogin", (GenericValue) context.get("userLogin")));
		}

		return getOrderNotes(delegator, (String) context.get("orderId"));
	}

	/*
	 * Change order or item status
	 */
	public static GenericValue changeOrderAndItemStatus(Delegator delegator, String orderId, String orderItemSeqId) throws GenericEntityException {
		GenericValue rowToUpdate = null;
		boolean isItem = false;
		if(UtilValidate.isNotEmpty(orderItemSeqId)) {
			rowToUpdate = delegator.findOne("OrderHeader", UtilMisc.toMap("orderId", orderId), false);
		} else {
			isItem = true;
			rowToUpdate = delegator.findOne("OrderItem", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), false);
		}

		if(isItem) {

		}
		return rowToUpdate;
	}

	/*
	 * Create the status history
	 */
	public static void createStatusHistory(Delegator delegator, String statusId, String orderId, String orderItemSeqId, GenericValue userLogin) throws GenericEntityException {
		Map<String, Object> changeFields = new HashMap<String, Object>();
		changeFields.put("orderStatusId", delegator.getNextSeqId("OrderStatus"));
		changeFields.put("statusId", statusId);
		changeFields.put("orderId", orderId);
		changeFields.put("orderItemSeqId", orderItemSeqId);
		changeFields.put("statusDatetime", UtilDateTime.nowTimestamp());
		changeFields.put("statusUserLogin", userLogin.getString("userLoginId"));
		GenericValue orderStatus = delegator.makeValue("OrderStatus", changeFields);

		delegator.create(orderStatus);
	}

	/*
	 * Create order item artwork comments from prepress
	 */
	public static void createInternalArtworkComment(Delegator delegator, String type, String comment, GenericValue orderItemArtwork) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(comment) && UtilValidate.isNotEmpty(type)) {
			if (type.equals("internal")) {
				orderItemArtwork.set("itemPrepressComments", comment);
			} else if (type.equals("pressman")) {
				orderItemArtwork.set("itemPressmanComments", comment);
			} else {
				orderItemArtwork.set("itemCustomerComments", comment);
			}
			orderItemArtwork.store();
		}
	}

	/*
	 * Set pressman comments to add text "OUTSOURCE"
	 */
	public static void appendToOrderComments(Delegator delegator, String type, String comment, GenericValue orderItemArtwork) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(comment) && UtilValidate.isNotEmpty(type)) {
			if (type.equals("internal")) {
				orderItemArtwork.set("itemPrepressComments", (UtilValidate.isNotEmpty(orderItemArtwork.getString("itemPrepressComments"))) ? orderItemArtwork.getString("itemPrepressComments") + comment : comment);
			} else if (type.equals("pressman")) {
				orderItemArtwork.set("itemPressmanComments", (UtilValidate.isNotEmpty(orderItemArtwork.getString("itemPressmanComments"))) ? orderItemArtwork.getString("itemPressmanComments") + comment : comment);
			} else {
				orderItemArtwork.set("itemCustomerComments", (UtilValidate.isNotEmpty(orderItemArtwork.getString("itemCustomerComments"))) ? orderItemArtwork.getString("itemCustomerComments") + comment : comment);
			}
			orderItemArtwork.store();
		}
	}

	/*
	 * Create order item artwork comments from customer
	 */
	public static GenericValue createArtworkComment(Delegator delegator, Map<String, Object> context, GenericValue userLogin) throws GenericEntityException {
		GenericValue comment = null;
		if(UtilValidate.isEmpty(userLogin)) {
			return comment;
		}

		if(UtilValidate.isNotEmpty(context) && UtilValidate.isNotEmpty(context.get("orderItemArtworkId"))) {
			comment = delegator.makeValue("OrderItemArtworkComment", UtilMisc.toMap("orderItemArtworkCommentId", delegator.getNextSeqId("OrderItemArtworkComment")));
			Iterator iter = context.entrySet().iterator();
			while(iter.hasNext()) {
				Map.Entry pairs = (Map.Entry) iter.next();
				comment.set((String) pairs.getKey(), (String) pairs.getValue());
			}
		}
		comment.set("partyId", userLogin.getString("partyId"));
		/*if(!"Y".equalsIgnoreCase((String) context.get("internalNote"))) {
			comment.set("internalNote", "N");
		}*/

		delegator.create(comment);
		return comment;
	}

	/*
	 * Create order item artwork comments
	 */
	public static List<GenericValue> getArtworkComment(Delegator delegator, String orderId, String orderItemSeqId, String orderItemArtworkId) throws GenericEntityException {
		List<GenericValue> artworkComments = new ArrayList<GenericValue>();

		GenericValue orderItemArtwork = null;
		if(UtilValidate.isNotEmpty("orderItemArtworkId")) {
			orderItemArtwork = delegator.findOne("OrderItemArtwork", UtilMisc.toMap("orderItemArtworkId", orderItemArtworkId), false);
		} else if(UtilValidate.isNotEmpty(orderId) && UtilValidate.isNotEmpty(orderItemSeqId)) {
			orderItemArtwork = EntityUtil.getFirst(delegator.findByAnd("OrderItemArtwork", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), null, false));
		}

		if(orderItemArtwork != null) {
			artworkComments = delegator.findByAnd("OrderItemArtworkComment", UtilMisc.toMap("orderItemArtworkId", orderItemArtwork.getString("orderItemArtworkId")), UtilMisc.toList("createdStamp ASC"), false);
		}

		return artworkComments;
	}

	/*
	 * Update the shipping cost
	 */
	public static GenericValue updateShippingCost(Delegator delegator, String orderId, String orderAdjustmentId, BigDecimal amount, String carrierPartyId, String shipmentMethodTypeId) throws GenericEntityException {
		return updateShippingCost(delegator, null, orderId, orderAdjustmentId, amount, carrierPartyId, shipmentMethodTypeId);
	}

	public static GenericValue updateShippingCost(Delegator delegator, GenericValue userLogin, String orderId, String orderAdjustmentId, BigDecimal amount, String carrierPartyId, String shipmentMethodTypeId) throws GenericEntityException {
		GenericValue shipCost = null;
		if(UtilValidate.isEmpty(orderAdjustmentId) && UtilValidate.isNotEmpty(amount) && UtilValidate.isNotEmpty(orderId) && UtilValidate.isNotEmpty(carrierPartyId) && UtilValidate.isNotEmpty(shipmentMethodTypeId)) {
			shipCost = EntityUtil.getFirst(delegator.findByAnd("OrderAdjustment", UtilMisc.toMap("orderId", orderId, "orderAdjustmentTypeId", "SHIPPING_CHARGES"), null, false));
		} else if(UtilValidate.isNotEmpty(orderAdjustmentId) && UtilValidate.isNotEmpty(amount) && UtilValidate.isNotEmpty(orderId) && UtilValidate.isNotEmpty(carrierPartyId) && UtilValidate.isNotEmpty(shipmentMethodTypeId)) {
			shipCost = delegator.findOne("OrderAdjustment", UtilMisc.toMap("orderAdjustmentId", orderAdjustmentId), false);
		}

		if(shipCost != null) {
			shipCost.set("amount", amount);
			shipCost.store();
			updateOrderShipGroup(delegator, orderId, carrierPartyId, shipmentMethodTypeId);
			return shipCost;
		} else {
			addManualAdjustment(delegator, userLogin, orderId, amount, "SHIPPING_CHARGES", null);
		}

		return null;
	}

	public static GenericValue addManualAdjustment(Delegator delegator, GenericValue userLogin, String orderId, BigDecimal adjustment, String orderAdjustmentTypeId, String description) throws GenericEntityException {
		try {
			String orderAdjustmentId = delegator.getNextSeqId("OrderAdjustment");

			GenericValue orderAdjustment = delegator.makeValue("OrderAdjustment", UtilMisc.toMap("orderAdjustmentId", orderAdjustmentId));
			orderAdjustment.put("orderId", orderId);
			orderAdjustment.put("orderItemSeqId", "_NA_");
			orderAdjustment.put("shipGroupSeqId", "_NA_");
			orderAdjustment.put("orderAdjustmentTypeId", orderAdjustmentTypeId);
			orderAdjustment.put("description", description);
			orderAdjustment.put("amount", adjustment);
			orderAdjustment.put("createdDate", UtilDateTime.nowTimestamp());
			orderAdjustment.put("createdByUserLogin", (userLogin != null) ? userLogin.getString("userLoginId") : null);
			delegator.create(orderAdjustment);
			return orderAdjustment;
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to add manual adjustment to order:" + orderId, module);
		}
		return null;
	}

	/*
	 * Update ship group
	 */
	public static GenericValue updateOrderShipGroup(Delegator delegator, String orderId, String carrierPartyId, String shipmentMethodTypeId) throws GenericEntityException {
		GenericValue osg = delegator.findOne("OrderItemShipGroup", UtilMisc.toMap("orderId", orderId, "shipGroupSeqId", "00001"), false);
		if(osg != null) {
			osg.set("carrierPartyId", carrierPartyId);
			osg.set("shipmentMethodTypeId", shipmentMethodTypeId);
			osg.store();
		}
		return osg;
	}

	/*
	 * Update the tax total
	 */
	public static GenericValue updateTaxTotal(Delegator delegator, String orderId, String orderAdjustmentId, BigDecimal amount) throws GenericEntityException {
		GenericValue taxTotal = null;
		if(UtilValidate.isEmpty(orderAdjustmentId) && UtilValidate.isNotEmpty(amount) && UtilValidate.isNotEmpty(orderId)) {
			taxTotal = EntityUtil.getFirst(delegator.findByAnd("OrderAdjustment", UtilMisc.toMap("orderId", orderId, "orderAdjustmentTypeId", "SALES_TAX"), null, false));
		} else if(UtilValidate.isNotEmpty(orderAdjustmentId) && UtilValidate.isNotEmpty(amount) && UtilValidate.isNotEmpty(orderId)) {
			taxTotal = delegator.findOne("OrderAdjustment", UtilMisc.toMap("orderAdjustmentId", orderAdjustmentId), false);
		}

		if(taxTotal != null) {
			taxTotal.set("amount", amount);
			taxTotal.store();
			return taxTotal;
		}

		return null;
	}

	/*
	 * Get status history
	 */
	public static List<Map> getOrderAndItemStatus(Delegator delegator, String orderId, String orderItemSeqId) throws GenericEntityException {
		List<GenericValue> orderStatuses = new ArrayList<GenericValue>();

		if(UtilValidate.isNotEmpty(orderId)) {
			buildStatusMap(delegator);
			orderStatuses = delegator.findByAnd("OrderStatus", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId, "orderPaymentPreferenceId", null), UtilMisc.toList("createdStamp DESC"), false);
		}

		List<Map> statusList = new ArrayList<Map>();
		for(GenericValue status : orderStatuses) {
			statusList.add(UtilMisc.toMap("status", STATUS_TYPES.get(status.getString("statusId")), "statusDatetime", status.getTimestamp("statusDatetime"), "statusUserLogin", status.getString("statusUserLogin")));
		}

		return statusList;
	}

	/*
	 * Return the attribute list
	 */
	public static Map<String, GenericValue> getOrderItemAttributeList() {
		return ORDER_ATTR_MAP;
	}

	/*
	 * Return the attribute options
	 */
	public static Map<String, List> getOrderItemAttributeOptions() {
		return ORDER_ATTR_OPTIONS_MAP;
	}

	/*
	 * Create a list of all status options
	 */
	private static void buildStatusMap(Delegator delegator) throws GenericEntityException {
		Map<String, String> statusMap = new HashMap<String, String>();
		List<GenericValue> statusTypes = delegator.findAll("StatusItem", true);
		for(GenericValue status : statusTypes) {
			statusMap.put(status.getString("statusId"), status.getString("description"));
		}

		STATUS_TYPES.putAll(statusMap);
	}

	/*
	 * Get status description from status ID
	 */
	public static String getStatusDesc(Delegator delegator, String statusId) throws GenericEntityException {
		if(UtilValidate.isEmpty(STATUS_TYPES)) {
			buildStatusMap(delegator);
		}

		return STATUS_TYPES.get(statusId);
	}

	/*
	 * Method to recalc order totals if an order was updated
	 */
	public static void recalculateOrderTotal(Delegator delegator, String orderId) throws GenericEntityException {
		GenericValue orderHeader = null;
		if(UtilValidate.isNotEmpty(orderId)) {
			orderHeader = delegator.findOne("OrderHeader", UtilMisc.toMap("orderId", orderId), false);
			if(orderHeader == null) {
				return;
			}
		}

		orderHeader.set("grandTotal", (new OrderReadHelper(delegator, orderId)).getOrderGrandTotal());
		orderHeader.store();
	}

	/*
	 * Get a list of the orders given index size and position
	 * IMPORTANT - The calling method must wrap this call in a transaction
	 * IMPORTANT - The calling method must close the EntityListIterator in order to avoid memory leak
	 */
	public static EntityListIterator getOrderList(Delegator delegator, Integer startPosition, Integer maxRows, Map<String, Object> conditions) throws GenericEntityException, ParseException {
		EntityListIterator eli = null;
		List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
		EntityFindOptions efo = new EntityFindOptions();
		if(maxRows != null && maxRows.intValue() > 0) {
			efo.setMaxRows(maxRows);
		}

		if(UtilValidate.isNotEmpty(conditions)) {
			Iterator iter = conditions.entrySet().iterator();
			while(iter.hasNext()) {
				Map.Entry pairs = (Map.Entry) iter.next();
				if(pairs.getValue() instanceof List) {
					conditionList.add(EntityCondition.makeCondition((String) pairs.getKey(), EntityOperator.IN, (List) pairs.getValue()));
				} else if(((String) pairs.getValue()).contains(",")) {
					conditionList.add(EntityCondition.makeCondition((String) pairs.getKey(), EntityOperator.IN, Arrays.asList(((String) pairs.getValue()).split("\\s*,\\s*"))));
				} else if(((String) pairs.getKey()).equals("minDate")) {
					conditionList.add(EntityCondition.makeCondition("orderDate", EntityOperator.GREATER_THAN_EQUAL_TO, EnvUtil.convertStringToTimestamp((String) pairs.getValue())));
				} else if(((String) pairs.getKey()).equals("maxDate")) {
					conditionList.add(EntityCondition.makeCondition("orderDate", EntityOperator.LESS_THAN_EQUAL_TO, EnvUtil.convertStringToTimestamp((String) pairs.getValue())));
				} else {
					conditionList.add(EntityCondition.makeCondition((String) pairs.getKey(), EntityOperator.EQUALS, (String) pairs.getValue()));
				}
			}
		}

		if(UtilValidate.isEmpty(conditions)) {
			conditionList.add(EntityCondition.makeCondition("orderDate", EntityOperator.GREATER_THAN_EQUAL_TO, UtilDateTime.getDayStart(UtilDateTime.nowTimestamp())));
		}
		if(UtilValidate.isEmpty(conditions)) {
			conditionList.add(EntityCondition.makeCondition("orderDate", EntityOperator.LESS_THAN_EQUAL_TO, UtilDateTime.nowTimestamp()));
		}

		eli = delegator.find("EnvOrderHeaderAndItems", ((UtilValidate.isNotEmpty(conditionList)) ? EntityCondition.makeCondition(conditionList, EntityOperator.AND) : null), null, null, UtilMisc.toList("orderDate DESC"), efo);
		if(startPosition != null && startPosition >= 0) {
			boolean hasResults = eli.relative(startPosition.intValue());
		}

		return eli;
	}

	/*
	 * MAIN ADMIN SEARCH
	 */
	public static String adminSearch(Delegator delegator, String query) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(query)) {
			//check if its an order first
			GenericValue order = EntityQuery.use(delegator).from("OrderHeader").where("orderId", query.trim()).cache(false).queryOne();
			if(order == null) {
				//check if its an international order
				order = EntityQuery.use(delegator).from("OrderHeader").where("externalId", query.trim()).cache(false).queryFirst();
			}
			if(order != null) {
				return "viewOrder?orderId=" + order.getString("orderId");
			}

			//check if its a quote
			order = EntityQuery.use(delegator).from("CustomEnvelope").where("quoteId", query.trim()).cache(false).queryOne();
			if(order != null) {
				return (("folders".equalsIgnoreCase(order.getString("webSiteId"))) ? "folders" : "envelopes") + "QuoteView?quoteId=" + order.getString("quoteId");
			}
		}

		return null;
	}

	/*
	 * Update product price
	 */
	public static void updateProductPrice(Delegator delegator, LocalDispatcher dispatcher, String orderId, String orderItemSeqId) throws GenericEntityException, GenericServiceException {
		if(UtilValidate.isEmpty(orderId) || UtilValidate.isEmpty(orderId)) {
			return;
		}

		Map<String, Object> context = createPriceContextFromOrder(delegator, orderId, orderItemSeqId);
		Map<String, Object> priceResult = dispatcher.runSync("calculateProductPrice", context);
		if(UtilValidate.isNotEmpty(priceResult.get("basePrice"))) {
			GenericValue orderItem = delegator.findOne("OrderItem", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), false);
			orderItem.set("unitPrice", priceResult.get("basePrice"));
			orderItem.store();
		}
	}

	/*
	 * Get the next available order item sequence id
	 */
	public static String getNextSeqId(Delegator delegator, OrderReadHelper orh, String orderId) throws GenericEntityException, Exception {
		if(orh == null) {
			orh = new OrderReadHelper(delegator, orderId);
		}

		long nextSeqId = 2;
		List<GenericValue> orderItems = orh.getOrderItems();
		if(UtilValidate.isNotEmpty(orderItems)) {
			GenericValue lastItem = orderItems.get(orderItems.size() - 1);
			nextSeqId = Long.valueOf(EnvUtil.removeChar("0", lastItem.getString("orderItemSeqId"), true, false, false)).longValue() + 1;
		}

		return UtilFormatOut.formatPaddedNumber(nextSeqId, 5);
	}

	/*
	 * Check if its a printed order from a quote
	 */
	public static boolean isOrderQuote(List<GenericValue> orderItems) {
		if(UtilValidate.isNotEmpty(orderItems)) {
			for(GenericValue orderItem : orderItems) {
				if(UtilValidate.isNotEmpty(orderItem.getString("referenceQuoteId"))) {
					return true;
				}
			}
		}

		return false;
	}

	/*
	 * Check if item is a quote order
	 */
	public static boolean isItemQuote(Delegator delegator, String orderId, String orderItemSeqId, GenericValue orderItem) throws GenericEntityException {
		if(orderItem == null && UtilValidate.isNotEmpty(orderId) && UtilValidate.isNotEmpty(orderItemSeqId)) {
			orderItem = delegator.findOne("OrderItem", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), false);
			if(UtilValidate.isNotEmpty(orderItem)) {
				if(UtilValidate.isNotEmpty(orderItem.getString("referenceQuoteId"))) {
					return true;
				}
			}
		} else if(orderItem != null && UtilValidate.isNotEmpty(orderItem.getString("referenceQuoteId"))) {
			return true;
		}

		return false;
	}

	/*
	 * Check if item is printed
	 */
	public static boolean isItemPrinted(Delegator delegator, String orderId, String orderItemSeqId, GenericValue orderItem) throws GenericEntityException {
		if(orderItem == null && UtilValidate.isNotEmpty(orderId) && UtilValidate.isNotEmpty(orderItemSeqId)) {
			orderItem = delegator.findOne("OrderItem", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), false);
			if(UtilValidate.isNotEmpty(orderItem)) {
				if(UtilValidate.isNotEmpty(orderItem.getString("artworkSource"))) {
					return true;
				}
			}
		} else if(orderItem != null && UtilValidate.isNotEmpty(orderItem.getString("artworkSource"))) {
			return true;
		}

		return false;
	}

	/**
	 * Check if the order is upload and not scene7
	 * @param orderItems
	 * @return
	 */
	public static boolean isOrderUpload(List<GenericValue> orderItems) {
		if(UtilValidate.isNotEmpty(orderItems)) {
			for(GenericValue orderItem : orderItems) {
				if(UtilValidate.isNotEmpty(orderItem.getString("artworkSource")) && !orderItem.getString("artworkSource").equalsIgnoreCase("SCENE7_ART_ONLINE")) {
					return true;
				}
			}
		}

		return false;
	}

	/*
	 * Check if its a printed order
	 */
	public static boolean isOrderPrinted(List<GenericValue> orderItems) {
		if(UtilValidate.isNotEmpty(orderItems)) {
			for(GenericValue orderItem : orderItems) {
				if(UtilValidate.isNotEmpty(orderItem.getString("artworkSource"))) {
					return true;
				}
			}
		}

		return false;
	}
	public static boolean isOrderPrinted(Delegator delegator, String orderId) throws GenericEntityException {
		List<GenericValue> orderItems = delegator.findByAnd("OrderItem", UtilMisc.toMap("orderId", orderId), null, false);
		if(UtilValidate.isNotEmpty(orderItems)) {
			for(GenericValue orderItem : orderItems) {
				if(UtilValidate.isNotEmpty(orderItem.getString("artworkSource"))) {
					return true;
				}
			}
		}

		return false;
	}

	/*
	 * Check if its a printed order
	 */
	public static boolean isOrderScene7(List<GenericValue> orderItems) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(orderItems)) {
			for(GenericValue orderItem : orderItems) {
				if(UtilValidate.isNotEmpty(orderItem.getString("artworkSource")) && orderItem.getString("artworkSource").equalsIgnoreCase("SCENE7_ART_ONLINE")) {
					return true;
				}
			}
		}

		return false;
	}
	public static boolean isOrderScene7(Delegator delegator, String orderId) throws GenericEntityException {
		List<GenericValue> orderItems = delegator.findByAnd("OrderItem", UtilMisc.toMap("orderId", orderId), null, false);
		if(UtilValidate.isNotEmpty(orderItems)) {
			for(GenericValue orderItem : orderItems) {
				if(UtilValidate.isNotEmpty(orderItem.getString("artworkSource")) && orderItem.getString("artworkSource").equalsIgnoreCase("SCENE7_ART_ONLINE")) {
					return true;
				}
			}
		}

		return false;
	}

	/*
	 * Check if its a printed order
	 */
	public static boolean isItemScene7(Delegator delegator, String orderId, String orderItemSeqId, GenericValue orderItem) throws GenericEntityException {
		if(orderItem == null && UtilValidate.isNotEmpty(orderId) && UtilValidate.isNotEmpty(orderItemSeqId)) {
			orderItem = delegator.findOne("OrderItem", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), false);
			if(UtilValidate.isNotEmpty(orderItem)) {
				if(UtilValidate.isNotEmpty(orderItem.getString("artworkSource")) && orderItem.getString("artworkSource").equalsIgnoreCase("SCENE7_ART_ONLINE")) {
					return true;
				}
			}
		} else if(orderItem != null && UtilValidate.isNotEmpty(orderItem.getString("artworkSource")) && orderItem.getString("artworkSource").equalsIgnoreCase("SCENE7_ART_ONLINE")) {
			return true;
		}

		return false;
	}

	/*
	 * Check if order is on sale/clearance
	 */
	public static String orderClearanceOrSale(List<GenericValue> orderItems) throws GenericEntityException {
		String str = null;
		if(UtilValidate.isNotEmpty(orderItems)) {
			for(GenericValue orderItem : orderItems) {
				GenericValue product = orderItem.getRelatedOne("Product", true);
				if(product != null && UtilValidate.isNotEmpty(product.get("onSale"))) {
					str = "Sale";
				} else if(product != null && UtilValidate.isNotEmpty(product.get("onClearance"))) {
					str = "Clearance";
					break;
				}
			}
		}

		return str;
	}

	/*
	 * Get due date
	 */
	public static Timestamp getDueDate(Map<String, Integer> leadTime, boolean isRush, boolean itemScene7, boolean isPrinted, Timestamp startDateTime) {
		return getDueDate(getProductionTime(leadTime, isRush, itemScene7, isPrinted), startDateTime);
	}
	public static Timestamp getDueDate(int productionTime, Timestamp startDateTime) {
		return getDueDate(productionTime, startDateTime, true);
	}
	public static Timestamp getDueDate(int productionTime, Timestamp startDateTime, boolean useBusinessHours) {
		Calendar cal = Calendar.getInstance();

		if(UtilValidate.isNotEmpty(startDateTime)) {
			cal.setTimeInMillis(startDateTime.getTime());
		}

		//if the day is already a weekend, lets bypass
		if(EnvUtil.isNonWorkDay(new Timestamp(cal.getTimeInMillis()))) {
			while (EnvUtil.isNonWorkDay(new Timestamp(cal.getTimeInMillis()))) {
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
		} else if(cal.get(Calendar.HOUR_OF_DAY) >= 16 && useBusinessHours) {
			//if its passed 4pm, this current day is over so we need an additional day
			productionTime++;
		}

		for(int i = 0; i < productionTime; i++) {
			do {
				cal.add(Calendar.DAY_OF_MONTH, 1);
			} while (EnvUtil.isNonWorkDay(new Timestamp(cal.getTimeInMillis())));
		}

		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE,      cal.getActualMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND,      cal.getActualMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));

		return new Timestamp(cal.getTimeInMillis());
	}

	public static int getProductionTime(Map<String, Integer> leadTime, boolean isRush, boolean itemScene7, boolean isPrinted) {
		try {
			if (isRush) {
				return leadTime.get("leadTimeRushPrinted");
			}
			else if (itemScene7) {
				return leadTime.get("leadTimeScene7");
			}
			else if (isPrinted) {
				return leadTime.get("leadTimeStandardPrinted");
			}
			else {
				return leadTime.get("leadTimePlain");
			}
		}
		catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to get product time.", module);
			return 0;
		}
	}

	/*
		This version of buildOrderDetails accepts Delegator and will return a cleaner version of the order information from orderData.
	*/
	public static Map<String, Object> buildOrderDetails(Delegator delegator, Map<String, Object> orderData) {
		Map<String, Object> order = new HashMap<>();

		List<Map<String, Object>> lineItems = new ArrayList<>();
		Map<String, Object> lineItem = new HashMap<>();
		StringBuilder reorderInfo = new StringBuilder();

		boolean openOrder = UtilValidate.isNotEmpty(orderData.get("openOrder")) && (boolean)orderData.get("openOrder");
		if(openOrder) {

		} else {
			try {
 				GenericValue orderHeader = (GenericValue) orderData.get("orderHeader");
				GenericValue billingAddressData = (GenericValue) orderData.get("billingAddress");
				Map<String, String> billingAddress = new HashMap<>();
				billingAddress.put("name", billingAddressData.getString("toName"));
				billingAddress.put("address1", billingAddressData.getString("address1"));
				billingAddress.put("address2", billingAddressData.getString("address2"));
				billingAddress.put("company", billingAddressData.getString("companyName"));
				billingAddress.put("city", billingAddressData.getString("city"));
				billingAddress.put("stateProvince", billingAddressData.getString("stateProvinceGeoId"));
				billingAddress.put("postalCode", billingAddressData.getString("postalCode"));
				billingAddress.put("country", billingAddressData.getString("countryGeoId"));
				if(UtilValidate.isNotEmpty(billingAddressData.getString("toName"))) {
					String[] names = PartyHelper.splitAtFirstSpace(billingAddressData.getString("toName"));
					if(names.length > 1) {
						billingAddress.put("firstName", names[0]);
						billingAddress.put("lastName", names[1]);
					}
				}

				GenericValue shippingAddressData = (GenericValue) orderData.get("shippingAddress");
				Map<String, String> shippingAddress = new HashMap<>();
				shippingAddress.put("name", shippingAddressData.getString("toName"));
				shippingAddress.put("address1", shippingAddressData.getString("address1"));
				shippingAddress.put("address2", shippingAddressData.getString("address2"));
				shippingAddress.put("company", shippingAddressData.getString("companyName"));
				shippingAddress.put("city", shippingAddressData.getString("city"));
				shippingAddress.put("stateProvince", shippingAddressData.getString("stateProvinceGeoId"));
				shippingAddress.put("postalCode", shippingAddressData.getString("postalCode"));
				shippingAddress.put("country", shippingAddressData.getString("countryGeoId"));
				if(UtilValidate.isNotEmpty(shippingAddressData.getString("toName"))) {
					String[] names = PartyHelper.splitAtFirstSpace(shippingAddressData.getString("toName"));
					if(names.length > 1) {
						shippingAddress.put("firstName", names[0]);
						shippingAddress.put("lastName", names[1]);
					}
				}

				List<Map<String, Object>> paymentsData = (List<Map<String, Object>>) orderData.get("payments");
				Map<String, Object> paymentData = paymentsData.get(0);
				Map<String, String> paymentInfo = new HashMap<>();
				paymentInfo.put("paymentMethodTypeId", (String) paymentData.get("paymentMethodTypeId"));
				paymentInfo.put("paymentMethodTypeIdDesc", (String) paymentData.get("paymentMethodTypeIdDesc"));
				paymentInfo.put("cardNumber", (String) paymentData.get("cardNumber"));
				paymentInfo.put("cardType", (String) paymentData.get("cardType"));
				paymentInfo.put("expireDate", (String) paymentData.get("expireDate"));

				BigDecimal shippingTotal = BigDecimal.ZERO;
				BigDecimal taxTotal = BigDecimal.ZERO;
				BigDecimal totalShippingTotal = BigDecimal.ZERO;

				Map<String, Object> discounts = new LinkedHashMap<>();
				List<Map<String, Object>> manualDiscounts = new ArrayList<>();
				List<Map<String, Object>> manualFees = new ArrayList<>();
				List<GenericValue> adjustments = (List<GenericValue>) orderData.get("adjustments");
				BigDecimal totalDiscountAmount = BigDecimal.ZERO;

				for (GenericValue adjustment : adjustments) {
					BigDecimal discount = null;

					if (adjustment.getString("orderAdjustmentTypeId").equals("SHIPPING_CHARGES")) {
						shippingTotal = adjustment.getBigDecimal("amount").setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);
						if ("PROMO_SHIP_CHARGE".equalsIgnoreCase(adjustment.getString("productPromoActionEnumId"))) {
							discount = adjustment.getBigDecimal("amount").setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);
						}
					} else if (adjustment.getString("orderAdjustmentTypeId").equals("SALES_TAX")) {
						taxTotal = adjustment.getBigDecimal("amount").setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);
					} else if (adjustment.getString("orderAdjustmentTypeId").equals("PROMOTION_ADJUSTMENT")) {
						discount = adjustment.getBigDecimal("amount").setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);
					} else if (adjustment.getString("orderAdjustmentTypeId").equals("DISCOUNT_ADJUSTMENT")) {
						Map<String, Object> manualDiscount = new HashMap<>();
						manualDiscount.put((String) adjustment.get("description"), adjustment.getBigDecimal("amount").setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
						manualDiscounts.add(manualDiscount);
					} else if (adjustment.getString("orderAdjustmentTypeId").equals("FEE")) {
						Map<String, Object> manualFee = new HashMap<>();
						manualFee.put((String) adjustment.get("description"), adjustment.getBigDecimal("amount").setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
						manualFees.add(manualFee);
					}

					if (UtilValidate.isNotEmpty(discount)) {
						BigDecimal existingDiscount = BigDecimal.ZERO;

						if (UtilValidate.isNotEmpty(discounts.get(adjustment.get("description")))) {
							existingDiscount = new BigDecimal(String.valueOf(discounts.get(adjustment.get("description"))));
						}

						discounts.put((String) adjustment.get("description"), discount.add(existingDiscount));
						totalDiscountAmount = totalDiscountAmount.add(discount.add(existingDiscount));
					}
				}

				boolean hasAddressing = false;
				List<Map<String, Object>> itemsData = (List<Map<String, Object>>) orderData.get("items");
				boolean isReorder = false;
				for (Map<String, Object> itemData : itemsData) {
					GenericValue item = (GenericValue) itemData.get("item");
					GenericValue product = (GenericValue) itemData.get("product");
					GenericValue shipGroupAssoc = (GenericValue) itemData.get("shipGroupAssoc");
					List attributes = (List) itemData.get("attribute");
					GenericValue artwork = (GenericValue) itemData.get("artwork");
					Map<String, String> features = (Map<String, String>) itemData.get("features");

					List<String> printing = new ArrayList<>();
					boolean fullBleed = false;
					int addresses = 0;
					int colorsFront = 0;
					int colorsBack = 0;
					int cuts = 0;

					for (Object attributeObj : attributes) {
						GenericValue attribute = (GenericValue) attributeObj;
						if (attribute.getString("attrName").equals("addresses")) {
							addresses = NumberUtils.toInt(attribute.getString("attrValue"), 0);
							if(!hasAddressing && addresses > 0) {
								hasAddressing = true;
							}
						} else if (attribute.getString("attrName").equals("colorsFront")) {
							colorsFront = NumberUtils.toInt(attribute.getString("attrValue"), 0);
							if (colorsFront == 4) {
								printing.add("Full color front");
							} else if (colorsFront > 0) {
								printing.add(colorsFront + " color front");
							}
						} else if (attribute.getString("attrName").equals("colorsBack")) {
							colorsBack = NumberUtils.toInt(attribute.getString("attrValue"), 0);
							if (colorsBack == 4) {
								printing.add("Full color back");
							} else if (colorsBack > 0) {
								printing.add(colorsBack + " color back");
							}
						} else if (attribute.getString("attrName").equals("isFullBleed")) {
							fullBleed = attribute.getString("attrValue").equals("true");
							if (fullBleed) {
								printing.add("Full Bleed");
							}
						} else if (attribute.getString("attrName").equals("cuts")) {
							cuts = NumberUtils.toInt(attribute.getString("attrValue"), 0);
						}
					}

					lineItem = new HashMap<>();
					lineItem.put("productId", item.getString("productId"));
					lineItem.put("statusId", item.getString("statusId"));
					reorderInfo.append(reorderInfo.length() > 0 ? "^" : "").append(orderHeader.getString("orderId")).append("|").append(item.getString("orderItemSeqId"));
					lineItem.put("productName", ProductHelper.formatName(product.getString("productName")));
					lineItem.put("primaryProductCategoryId", product.getString("primaryProductCategoryId"));
					lineItem.put("productSize", features.get("SIZE"));
					lineItem.put("productColor", features.get("COLOR"));
					lineItem.put("quantity", item.getBigDecimal("quantity").intValue());
					lineItem.put("unitPrice", item.getBigDecimal("unitPrice").setScale(EnvConstantsUtil.ENV_SCALE_L));
					lineItem.put("totalPrice", item.getBigDecimal("unitPrice").multiply(item.getBigDecimal("quantity")).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
					lineItem.put("printing", printing);
					lineItem.put("addresses", addresses);
					lineItem.put("cuts", cuts);
					lineItem.put("colorsFront", colorsFront);
					lineItem.put("colorsBack", colorsBack);
					lineItem.put("dueDate", item.getTimestamp("dueDate"));
					lineItem.put("productionTime", (UtilValidate.isNotEmpty(item.getString("isRushProduction")) && item.getString("isRushProduction").equals("Y")) ? "Rush" : "Standard");
					lineItem.put("scene7DesignId", (artwork != null && UtilValidate.isNotEmpty(artwork.getString("scene7DesignId"))) ? artwork.getString("scene7DesignId") : null);
					lineItem.put("contents", itemData.get("contents"));
					lineItem.put("isPrinted", isItemPrinted(delegator, null, null, item));
					lineItem.put("hasSample", product.getString("hasSample"));
					lineItem.put("orderItemSeqId", item.getString("orderItemSeqId"));
					lineItem.put("upc", ProductHelper.getProductUPC(delegator, item.getString("productId")));
					lineItem.put("trackingNumber", UtilValidate.isNotEmpty(shipGroupAssoc) ? shipGroupAssoc.getString("trackingNumber") : null);
					lineItems.add(lineItem);
					if(UtilValidate.isNotEmpty(item.getString("referenceOrderId"))) {
						isReorder = true;
					}
				}
				String orderId = orderHeader.getString("orderId");

				OrderReadHelper orh = new OrderReadHelper(delegator, orderId);
				order.put("orderId", orderHeader.getString("orderId"));
				order.put("externalId", orderHeader.getString("externalId"));
				order.put("status", orderHeader.getString("statusId"));
				order.put("createdBy", orderHeader.getString("createdBy"));
				order.put("orderDate", orderHeader.get("orderDate"));
				order.put("orderDateS", orderHeader.getTimestamp("orderDate").getTime() / 1000L);
				order.put("billingAddress", billingAddress);
				order.put("shippingAddress", shippingAddress);
				order.put("paymentInfo", paymentInfo);
				order.put("lineItems", lineItems);
				order.put("subTotal", orh.getOrderItemsSubTotal());
				order.put("taxTotal", taxTotal);
				order.put("shipTotal", shippingTotal);
				order.put("shipTotalWithDiscount", PromoHelper.isShippingFree(adjustments) ? BigDecimal.ZERO : shippingTotal);
				order.put("discounts", discounts);
				order.put("manualFees", manualFees);
				order.put("manualDiscounts", manualDiscounts);
				order.put("grandTotal", orderHeader.getBigDecimal("grandTotal"));
				order.put("email", (String) orderData.get("email"));
				order.put("reorderInfo", reorderInfo.toString());
				order.put("isUpload", isOrderUpload(orh.getOrderItems()));
				order.put("isPrinted", isOrderPrinted(orh.getOrderItems()));
				order.put("hasAddressing", hasAddressing);
				order.put("isScene7", isOrderScene7(orh.getOrderItems()));
				order.put("clearanceOrSale", orderClearanceOrSale(orh.getOrderItems()));
				order.put("totalDiscountAmount", totalDiscountAmount);
				order.put("isReorder", isReorder);
				order.put("coupons", PromoHelper.getProductPromoUse(delegator, orderId));

				GenericValue shipGroup = (GenericValue) orderData.get("shipGroup");

				String trackingNumber;
				String trackingURL = UtilValidate.isNotEmpty(trackingNumber = (String)shipGroup.get("trackingNumber")) &&  UtilValidate.isNotEmpty(trackingURL = EnvUtil.getTrackingURL(trackingNumber))? trackingURL : "";
				order.put("trackingNumber", UtilValidate.isNotEmpty(trackingURL) ? trackingNumber : "");
				order.put("trackingURL", UtilValidate.isNotEmpty(trackingURL) ? trackingURL : "");

				order.put("notes", (List<Map>) orderData.get("notes"));
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "Caught Exception while retrieving order data.", module);
			}
		}

		return order;
	}

	/*
		This version of buildOrderDetails accepts an HttpServletRequest to get a cleaner version of the orderData while also clearing cart state data for cookieData
	*/
	public static Map<String, Object> buildOrderDetails(HttpServletRequest request, Map<String, Object> orderData) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> order = new HashMap<>();
		order = buildOrderDetails(delegator, orderData);

		try {
			Map<String, Object> cookieData = new HashMap<>();
			cookieData.put("JSESSIONID", UtilHttp.getSessionId(request));
			cookieData.put("itemCount", 0);
			cookieData.put("cartTotal", "empty");
			cookieData.put("cartSubtotal", "empty");
			cookieData.put("items", "");
			order.put("cartStateData", cookieData);
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Caught Exception while clearing cart data.", module);
		}

		return order;
	}

	public static List<Map<String, Object>> getOrderList(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue)request.getSession().getAttribute("userLogin");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		String partyId = userLogin.getString("partyId");
		String orderType = (String) request.getAttribute("orderType");
		boolean openOnly = orderType.equals("open");
		boolean all = orderType.equals("all");
		boolean returnOnly = orderType.equals("return");

		List<Map<String, Object>> orders = new ArrayList<>();

		DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT);

		List<EntityCondition> conditionList = new ArrayList<>();
		conditionList.add(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId));
		conditionList.add(EntityCondition.makeCondition("roleTypeId", EntityOperator.EQUALS, "PLACING_CUSTOMER"));
//		conditionList.add(EntityCondition.makeCondition("thruDate", EntityOperator.EQUALS, null));
//		conditionList.add(EntityCondition.makeCondition("contentPurposeEnumId", EntityOperator.IN, UtilMisc.toList("OIACPRP_FRONT", "OIACPRP_BACK")));

		if(returnOnly) {
			//TODO
		} else if(openOnly) {
			conditionList.add(EntityCondition.makeCondition("orderStatusId", EntityOperator.NOT_IN, Arrays.asList("ORDER_CANCELLED", "ORDER_SHIPPED")));
		}

		List<String> orderBy = new ArrayList<>();
		orderBy.add("orderDate");
		orderBy.add("orderItemSeqId");

		DynamicViewEntity dve = new DynamicViewEntity();

		dve.addMemberEntity("OH", "OrderHeader");
		dve.addAlias("OH", "orderId");
		dve.addAlias("OH", "orderDate");
		dve.addAlias("OH", "salesChannelEnumId");
		dve.addAlias("OH", "orderStatusId", "statusId", null, null, null, null);
		dve.addMemberEntity("ORL", "OrderRole");
		dve.addViewLink("OH", "ORL", true, ModelKeyMap.makeKeyMapList("orderId", "orderId"));
		dve.addAlias("ORL", "roleTypeId");
		dve.addAlias("ORL", "partyId");
		dve.addMemberEntity("OSI", "StatusItem");
		dve.addViewLink("OH", "OSI", true, ModelKeyMap.makeKeyMapList("statusId", "statusId"));
//		dve.addAlias("OSI", "orderStatusId", "statusId", null, null, null, null);
		dve.addAlias("OSI", "orderStatus", "description", null, null, null, null);
		dve.addMemberEntity("OI", "OrderItem");
		dve.addViewLink("OH", "OI", true, ModelKeyMap.makeKeyMapList("orderId", "orderId"));
		dve.addAlias("OI", "orderItemSeqId");
		dve.addAlias("OI", "productId");
		dve.addAlias("OI", "itemDescription");
		dve.addAlias("OI", "quantity");
		dve.addAlias("OI", "unitPrice");
		dve.addAlias("OI", "artworkSource");
		dve.addAlias("OI", "orderItemStatusId", "statusId", null, null, null, null);
		dve.addMemberEntity("OISI", "StatusItem");
		dve.addViewLink("OI", "OISI", true, ModelKeyMap.makeKeyMapList("statusId", "statusId"));
		dve.addAlias("OISI", "orderItemStatusId", "statusId", null, null, null, null);
		dve.addAlias("OISI", "orderItemStatus", "description", null, null, null, null);
//		dve.addMemberEntity("OIC", "OrderItemContent");
//		dve.addViewLink("OI", "OIC", true, ModelKeyMap.makeKeyMapList("orderId", "orderId", "orderItemSeqId", "orderItemSeqId"));
//		dve.addAlias("OIC", "contentPath");
//		dve.addAlias("OIC", "contentName");
//		dve.addAlias("OIC", "thruDate");
//		dve.addAlias("OIC", "contentPurposeEnumId");

		EntityListIterator eli = null;
		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try {
				eli = delegator.findListIteratorByCondition(dve, EntityCondition.makeCondition(conditionList), null, null, orderBy, null);
				GenericValue orderGV = null;
				String previousOrderId = "";
				Map<String, Object> order = new LinkedHashMap<>();
				List<Map<String, Object>> items = new ArrayList<>();
				while((orderGV = eli.next()) != null) {
					Map<String, Object> item = new HashMap<>();
					item.put("orderItemSeqId", orderGV.getString("orderItemSeqId"));
					item.put("productId", orderGV.getString("productId"));
					item.put("itemDescription", orderGV.getString("itemDescription"));
					item.put("itemStatus", orderGV.getString("orderItemStatus"));
					item.put("quantity", orderGV.getBigDecimal("quantity"));
					item.put("unitPrice", orderGV.getBigDecimal("unitPrice"));
					item.put("artworkSource", orderGV.getString("artworkSource"));
					GenericValue artwork = getOrderItemArtwork(delegator, orderGV.getString("orderId"), orderGV.getString("orderItemSeqId"));
					item.put("scene7DesignId", artwork != null ? artwork.getString("scene7DesignId") : null);
					List<GenericValue> contents = delegator.findByAnd("OrderItemContent", UtilMisc.toMap("orderId", orderGV.getString("orderId"), "orderItemSeqId", orderGV.getString("orderItemSeqId"), "thruDate", null), null, false);
					List<Map<String, String>> contentsList = new ArrayList<>();
					for (GenericValue content : contents) {
						Map<String, String> contentMap = new HashMap<>();
						contentMap.put("contentPath", content.getString("contentPath"));
						if(UtilValidate.isNotEmpty(content.getString("contentName"))) {
							contentMap.put("contentName", content.getString("contentName"));
						} else {
							if(UtilValidate.isNotEmpty(content.getString("contentPath"))) {
								contentMap.put("contentName", content.getString("contentPath").substring(content.getString("contentPath").lastIndexOf("/") + 1));
							} else {
								contentMap.put("contentName", "_NA_");
							}
						}
						contentMap.put("contentPurpose", content.getString("contentPurposeEnumId"));
						contentsList.add(contentMap);
					}
					item.put("contents", contentsList);
					item.put("orderItemStatusId", orderGV.getString("orderItemStatusId"));

					if(!previousOrderId.equals(orderGV.getString("orderId"))) {
						if(!items.isEmpty()) {
							order.put("items", items);
							items = new ArrayList<>();

							orders.add(order);
							order = new LinkedHashMap<>();
						}
						order.put("orderId", orderGV.getString("orderId"));
						order.put("orderDate", dateFormatter.format(orderGV.get("orderDate")));
						order.put("orderStatus", orderGV.getString("orderStatus"));
						order.put("salesChannel", orderGV.get("salesChannelEnumId") != null && orderGV.getString("salesChannelEnumId").equals("FOLD_SALES_CHANNEL") ? "FOLDERS" : "ENVELOPES");
						order.put("webSiteId", EnvUtil.getWebsiteId(orderGV.getString("salesChannelEnumId")));
					}
					items.add(item);
					previousOrderId = orderGV.getString("orderId");
				}
				if(!items.isEmpty()) {
					order.put("items", items);
					orders.add(order);
				}
			} catch (GenericEntityException e1) {
				TransactionUtil.rollback(beganTransaction, "Error looking up shipping and billing addresses", e1);
				EnvUtil.reportError(e1);
			} finally {
				if(eli != null) {
					try {
						eli.close();
						TransactionUtil.commit(beganTransaction);
					} catch (GenericEntityException e2) {
						EnvUtil.reportError(e2);
					}
				}
			}
		} catch (GenericTransactionException e3) {
			EnvUtil.reportError(e3);
		}
		return orders;
	}

	/*
	 * Get all orders for a user
	 */
	public static List<GenericValue> ordersForUser(Delegator delegator, GenericValue userLogin, String statusId) throws GenericEntityException {
		if(userLogin == null) {
			return null;
		}

		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("createdBy", userLogin.getString("userLoginId"));
		if(UtilValidate.isNotEmpty(statusId)) {
			fields.put("statusId", statusId);
		}

		List<GenericValue> orderHeaders = delegator.findByAnd("OrderHeader", fields, UtilMisc.toList("createdStamp DESC"), false);
		return orderHeaders;
	}

	/*
	 * Get Parent Design Id
	 */
	public static String getDesignParentId(Delegator delegator, String designId) throws GenericEntityException {
		try {
			return delegator.findOne("Scene7Design", UtilMisc.toMap("scene7DesignId", designId), false).getString("parentId");
		}
		catch (GenericEntityException e) {
			EnvUtil.reportError(e);
		}

		return null;
	}

	/*
	 * Get all items that need proof approval
	 */
	public static List<Map<String, Object>> orderItemsForUser(Delegator delegator, GenericValue userLogin, String... statusId) throws GenericEntityException {
		if(userLogin == null) {
			return null;
		}

		List<EntityCondition> conditionList = new ArrayList<>();
		conditionList.add(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, userLogin.getString("partyId")));
		conditionList.add(EntityCondition.makeCondition("roleTypeId", EntityOperator.EQUALS, "PLACING_CUSTOMER"));
		if(UtilValidate.isNotEmpty(statusId)) {
			conditionList.add(EntityCondition.makeCondition("itemStatusId", EntityOperator.IN, Arrays.asList(statusId)));
		}

		List<GenericValue> orderHeaderAndItems = delegator.findList("EnvOrderHeaderRoleAndItems", EntityCondition.makeCondition(conditionList), null, null, null, false);
		List<Map<String, Object>> orderItems = new ArrayList<>();
		for (GenericValue orderHeaderAndItem : orderHeaderAndItems) {
			Map<String, Object> orderItem = orderHeaderAndItem.getAllFields();
			int numberOfAddresses = 0;
			String orderId = "0";
			String lockScene7Design = "N";
			String orderItemSeqId = "0000";
			String addressBookId = "";
			String scene7DesignParentId = "";
			String scene7DesignId = "";
			Boolean hasUpload = false;
			GenericValue orderItemArtwork = null;
			GenericValue orderItemContent = null;

			try {
				orderId = orderHeaderAndItem.getString("orderId");
				orderItemSeqId = orderHeaderAndItem.getString("orderItemSeqId");
				lockScene7Design = orderHeaderAndItem.getString("lockScene7Design");
				numberOfAddresses = getNumberOfAddresses(delegator, orderId, orderItemSeqId);
				hasUpload = UtilValidate.isNotEmpty(EntityUtil.filterByDate(delegator.findByAnd("OrderItemContent", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId, "contentPurposeEnumId", "OIACPRP_FILE"), null, false)));
				orderItemArtwork = getOrderItemArtwork(delegator, orderId, orderItemSeqId);
				orderItemContent = EntityUtil.getFirst(getOrderItemContent(delegator, orderId, orderItemSeqId, "OIACPRP_PDF"));

				if(orderHeaderAndItem.getString("artworkSource").equalsIgnoreCase("SCENE7_ART_ONLINE")) {
					Map<String, Object> artworkAttributes = createArtworkContextFromOrder(delegator, orderId, orderItemSeqId);
					scene7DesignId = (String) artworkAttributes.get("scene7DesignId");
					scene7DesignParentId = getDesignParentId(delegator, scene7DesignId);
					addressBookId = AddressBookEvents.getAddressBookId(delegator, scene7DesignId, "0");
				}
			} catch (Exception ignore) {
				Debug.logError("Error occurred while finding order item details for orderId:" + orderId + ", orderItemSeqId:" + orderItemSeqId, module);
				EnvUtil.reportError(ignore);
			}
			orderItem.put("numberOfAddresses", numberOfAddresses);
			orderItem.put("addressBookId", addressBookId);
			orderItem.put("scene7DesignId", scene7DesignId);
			orderItem.put("scene7DesignParentId", scene7DesignParentId);
			orderItem.put("hasUpload", hasUpload);
			orderItem.put("lockScene7Design", lockScene7Design);
			orderItem.put("orderItemArtwork", orderItemArtwork);
			orderItem.put("orderItemContent", orderItemContent);
			orderItems.add(orderItem);
		}

		return orderItems;
	}

	/*
	 * Get all content for a userde
	 */
	public static final Pattern defaultContentNamePattern = Pattern.compile(".*?\\/?(\\w*)\\..*");
	public static List<Map<String, Object>> getOrderItemContents(Delegator delegator, GenericValue userLogin, String... contentPurposeEnumId) throws GenericEntityException {
		List<Map<String, Object>> orders = new ArrayList<>();
		String partyId = userLogin.getString("partyId");

		String contentPurposeEnumTypeId = contentPurposeEnumId != null && contentPurposeEnumId.length == 1 && UtilValidate.isNotEmpty(contentPurposeEnumId[0]) ? contentPurposeEnumId[0] : "OIACPRP_FILE";
		List<EntityCondition> conditionList = new ArrayList<>();
		conditionList.add(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId));
		//conditionList.add(EntityCondition.makeCondition("userLoginId", EntityOperator.NOT_LIKE, "%ANONYMOUS%"));
		conditionList.add(EntityCondition.makeCondition("contentPurposeEnumId", EntityOperator.EQUALS, contentPurposeEnumTypeId));
		conditionList.add(EntityCondition.makeCondition("contentThruDate", EntityOperator.EQUALS, null));

		List<String> orderBy = new ArrayList<>();
		orderBy.add("createdStamp DESC");

		DynamicViewEntity dve = new DynamicViewEntity();
		dve.addMemberEntity("UL", "UserLogin");
		dve.addAlias("UL", "userLoginId");
		dve.addAlias("UL", "partyId");

		dve.addMemberEntity("OH", "OrderHeader");
		dve.addViewLink("UL", "OH", false, ModelKeyMap.makeKeyMapList("userLoginId", "createdBy"));
		dve.addAlias("OH", "orderId");
		dve.addAlias("OH", "createdStamp");

		dve.addMemberEntity("OI", "OrderItem");
		dve.addViewLink("OH", "OI", false, ModelKeyMap.makeKeyMapList("orderId", "orderId"));
		dve.addAlias("OI", "itemDescription");

		dve.addMemberEntity("OIC", "OrderItemContent");
		dve.addViewLink("OI", "OIC", true, ModelKeyMap.makeKeyMapList("orderId", "orderId", "orderItemSeqId", "orderItemSeqId"));
		dve.addAlias("OIC", "orderItemContentId");
		dve.addAlias("OIC", "contentPurposeEnumId");
		dve.addAlias("OIC", "contentThruDate", "thruDate", null, null, null, null);
		dve.addAlias("OIC", "orderItemContentOrderId", "orderId", null, null, null, null);
		dve.addAlias("OIC", "orderItemContentOrderItemSeqId", "orderItemSeqId", null, null, null, null);
		dve.addAlias("OIC", "contentPath");
		dve.addAlias("OIC", "contentName");

		dve.addMemberEntity("OIA", "OrderItemArtwork");
		dve.addViewLink("OI", "OIA", false, ModelKeyMap.makeKeyMapList("orderId", "orderId", "orderItemSeqId", "orderItemSeqId"));
		dve.addAlias("OIA", "itemJobName");

		EntityListIterator eli = null;
		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try {
				eli = delegator.findListIteratorByCondition(dve, EntityCondition.makeCondition(conditionList), null, null, orderBy, null);
				GenericValue orderGV = null;
				String previousOrderId = "";
				Map<String, Object> order = new LinkedHashMap<>();
				List<Map<String, Object>> orderItemContents = new ArrayList<>();
				while((orderGV = eli.next()) != null) {
					Map<String, Object> orderItemContent = new HashMap<>();
					orderItemContent.put("orderItemContentId", orderGV.getString("orderItemContentId"));
					orderItemContent.put("orderId", orderGV.getString("orderItemContentOrderId"));
					orderItemContent.put("orderItemSeqId", orderGV.getString("orderItemContentOrderItemSeqId"));
					orderItemContent.put("contentPath", orderGV.getString("contentPath"));
					orderItemContent.put("itemDescription", orderGV.getString("itemDescription"));
					orderItemContent.put("itemJobName", orderGV.getString("itemJobName"));
					orderItemContent.put("createdStamp", orderGV.getTimestamp("createdStamp"));
					String contentName = orderGV.getString("contentName");
					if(UtilValidate.isEmpty(contentName)) {
						Matcher m = defaultContentNamePattern.matcher(orderGV.getString("contentPath"));
						if(m.find()) {
							contentName = m.group(1);
						} else {
							contentName = "_NA_";
						}
					}
					orderItemContent.put("contentName", contentName);
					if(!previousOrderId.equals(orderGV.getString("orderItemContentOrderId"))) {
						if(!orderItemContents.isEmpty()) {
							order.put("orderItemContents", orderItemContents);
							orderItemContents = new ArrayList<>();

							orders.add(order);
							order = new LinkedHashMap<>();
						}
						order.put("orderId", orderGV.getString("orderId"));

					}
					orderItemContents.add(orderItemContent);
					previousOrderId = orderGV.getString("orderId");
				}
				if(!orderItemContents.isEmpty()) {
					order.put("orderItemContents", orderItemContents);
					orders.add(order);
				}
			} catch (GenericEntityException e1) {
				TransactionUtil.rollback(beganTransaction, "Error looking up shipping and billing addresses", e1);
				EnvUtil.reportError(e1);
			} finally {
				if(eli != null) {
					try {
						eli.close();
						TransactionUtil.commit(beganTransaction);
					} catch (GenericEntityException e2) {
						EnvUtil.reportError(e2);
					}
				}
			}
		} catch (GenericTransactionException e3) {
			EnvUtil.reportError(e3);
		}
		return  orders;
	}

	/*
	 * Approve proof
	 */
	public static Map<String, Object> approveOrRejectProof(HttpServletRequest request, LocalDispatcher dispatcher, Delegator delegator, String orderId, String orderItemSeqId, String statusId, Map<String, Object> jsonResponse) throws GenericEntityException, GenericServiceException {
		boolean success = false;
		GenericValue userLogin = null;
		if(request != null) {
			userLogin = (GenericValue)request.getSession().getAttribute("userLogin");
			if(userLogin == null) {
				jsonResponse.put("error", "Must be logged in to update order status.");
			}
		} else {
			userLogin = EnvUtil.getSystemUser(delegator);
		}

		Map<String, Object> result = null;
		if(UtilValidate.isNotEmpty(orderId) && UtilValidate.isNotEmpty(orderItemSeqId) && UtilValidate.isNotEmpty(statusId)) {
			result = dispatcher.runSync("changeOrderItemStatus", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId, "statusId", statusId, "userLogin", userLogin));
		} else {
			jsonResponse.put("error", "No order id or order item given to update status.");
		}

		if(ServiceUtil.isError(result)) {
			jsonResponse.put("error", "Error updating status.");
		} else {
			success = true;
		}

		jsonResponse.put("success", success);
		return jsonResponse;
	}

	private static String getParentProductId(Delegator delegator, String orderId, String orderItemSeqId) {
		try {
			DynamicViewEntity dve = new DynamicViewEntity();
			dve.addMemberEntity("OI", "OrderItem");
			dve.addAlias("OI", "orderId");
			dve.addAlias("OI", "orderItemSeqId");
			dve.addAlias("OI", "productId");
			dve.addMemberEntity("P", "Product");
			dve.addAlias("P", "productId");
			dve.addAlias("P", "parentProductId");
			dve.addViewLink("OI", "P", false, ModelKeyMap.makeKeyMapList("productId", "productId"));

			List<EntityCondition> conditions = new ArrayList<EntityCondition>();
			conditions.add(EntityCondition.makeCondition("orderId", EntityOperator.EQUALS, orderId));
			conditions.add(EntityCondition.makeCondition("orderItemSeqId", EntityOperator.EQUALS, orderItemSeqId));

			GenericValue orderItem = EntityQuery.use(delegator).select("parentProductId").from(dve).where(EntityCondition.makeCondition(conditions, EntityOperator.AND)).cache().queryOne();
			if(UtilValidate.isNotEmpty(orderItem) && UtilValidate.isNotEmpty(orderItem.get("parentProductId"))) {
				return orderItem.getString("parentProductId");
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
		}

		return "";
	}

	/*
	 * Export Order to Switch
	 */
	public static void exportOrderForSwitch(Delegator delegator, String orderId, String orderItemSeqId, Integer sequence) throws GenericEntityException, Exception {
		GenericValue order = delegator.findOne("OrderHeader", UtilMisc.toMap("orderId", orderId), false);

		if(order != null) {
			boolean isOrderPrinted = isOrderPrinted(delegator, orderId);
			if(isOrderPrinted) {
				Map<String, Object> orderData = getOrderData(delegator, orderId, true);

				for(Map item : (List<Map>) orderData.get("items")) {
					GenericValue orderItem = (GenericValue) item.get("item");

					if(UtilValidate.isNotEmpty(orderItemSeqId) && !orderItem.getString("orderItemSeqId").equals(orderItemSeqId)) {
						continue;
					}

					boolean isItemPrinted = isItemPrinted(delegator, null, null, orderItem);
					if(!isItemPrinted) {
						continue;
					}

					GenericValue product = (GenericValue) item.get("product");
					GenericValue orderItemArtwork = (GenericValue) item.get("artwork");
					List<GenericValue> orderItemContentList = getOrderItemContent(delegator, orderId, orderItem.getString("orderItemSeqId"), "OIACPRP_FILE");
					Map<String, String> orderItemAttribute = (Map<String, String>) item.get("attribute");

					Map<String, String> srcFiles = new HashMap<>();
					String baseSwitchPath = FileHelper.cleanFolderPath(EnvConstantsUtil.OFBIZ_HOME + EnvConstantsUtil.UPLOAD_DIR + "/" + EnvConstantsUtil.SWITCH_UPLOAD_DIR);

					String zipFile = baseSwitchPath + order.getString("orderId") + "_" + orderItem.getString("orderItemSeqId") + "_" + orderItem.getString("statusId") + ".zip";
					String xmlFileName = order.getString("orderId") + "_" + orderItem.getString("orderItemSeqId") + ".xml";
					String xmlFile = baseSwitchPath + xmlFileName;

					Integer colorsFront = Integer.valueOf(UtilValidate.isNotEmpty(orderItemAttribute.get("colorsFront")) ? orderItemAttribute.get("colorsFront") : "0");
					Integer colorsBack = Integer.valueOf(UtilValidate.isNotEmpty(orderItemAttribute.get("colorsBack")) ? orderItemAttribute.get("colorsBack") : "0");
					Integer maxColors = Integer.valueOf(ProductHelper.getLeastOrMostColors(true, colorsFront.intValue(), colorsBack.intValue()));
					Integer cuts = Integer.valueOf(UtilValidate.isNotEmpty(orderItemAttribute.get("cuts")) ? orderItemAttribute.get("cuts") : "0");
					Boolean isFolded =  "true".equals(orderItemAttribute.get("isFolded"));
					Boolean isFullBleed = "true".equals(orderItemAttribute.get("isFullBleed"));
					Boolean hasAddressing = (UtilValidate.isNotEmpty(orderItemAttribute.get("addresses")) && !"0".equals(orderItemAttribute.get("addresses"))) ? true : false;

					String productName = product.getString("productName");
					if(productName != null) {
						if(productName.contains("(")) {
							productName = productName.substring(0, productName.indexOf("(")).trim();
						}
						productName = productName.replace("Envelopes","");
						productName = productName.replace("Envelope","");
						productName = productName.trim();
					} else {
						productName = "";
					}

					Map<String, String> productFeatures = ProductHelper.getProductFeatures(delegator, product, null, true);

					try {
						DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
						DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

						// order root elements
						Document doc = docBuilder.newDocument();
						Element rootElement = doc.createElement("order");
						doc.appendChild(rootElement);

						// orderId element
						Element orderIdElement = doc.createElement("orderId");
						orderIdElement.appendChild(doc.createTextNode(orderId));
						rootElement.appendChild(orderIdElement);

						// entry Number element
						Element entryNumberElement = doc.createElement("xmlEntryNumber");
						entryNumberElement.appendChild(doc.createTextNode(sequence != null ? sequence.toString() : "0"));
						rootElement.appendChild(entryNumberElement);

						// orderStatus element
						Element orderStatus = doc.createElement("orderStatus");
						orderStatus.appendChild(doc.createTextNode(order.getString("statusId")));
						rootElement.appendChild(orderStatus);

						// orderDate element
						Element orderDate = doc.createElement("orderDate");
						orderDate.appendChild(doc.createTextNode(order.getTimestamp("orderDate").toString().replace(":","_")));
						rootElement.appendChild(orderDate);

						// appendChild element
						Element userName = doc.createElement("userName");
						userName.appendChild(doc.createTextNode(order.getString("createdBy")));
						rootElement.appendChild(userName);

						// email element
						Element email = doc.createElement("email");
						email.appendChild(doc.createTextNode((String) orderData.get("email")));
						rootElement.appendChild(email);

						// numberOfOrderItems element
						Element numberOfOrderItems = doc.createElement("numberOfOrderItems");
						numberOfOrderItems.appendChild(doc.createTextNode(Integer.toString(((List) orderData.get("items")).size())));
						rootElement.appendChild(numberOfOrderItems);

						// orderItem element
						Element orderItemsElement = doc.createElement("orderItems");
						rootElement.appendChild(orderItemsElement);

						// orderItemSeqId element
						Element itemSeqId = doc.createElement("itemSeqId");
						itemSeqId.appendChild(doc.createTextNode(orderItem.getString("orderItemSeqId")));
						orderItemsElement.appendChild(itemSeqId);

						GenericValue getOrderStatus = EntityUtil.getFirst(delegator.findByAnd("OrderStatus", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItem.getString("orderItemSeqId"), "statusId", orderItem.getString("statusId")), UtilMisc.toList("statusDatetime DESC"), false));
						// seqIdDate element
						if(getOrderStatus != null) {
							Element seqIdDate = doc.createElement("seqIdDate");
							seqIdDate.appendChild(doc.createTextNode(getOrderStatus.getTimestamp("statusDatetime").toString().replace(":","_")));
							orderItemsElement.appendChild(seqIdDate);
						} else {
							Element seqIdDate = doc.createElement("seqIdDate");
							seqIdDate.appendChild(doc.createTextNode(orderItem.getTimestamp("lastUpdatedStamp").toString().replace(":","_")));
							orderItemsElement.appendChild(seqIdDate);
						}


						// productId element
						Element productIdElement = doc.createElement("productId");
						productIdElement.appendChild(doc.createTextNode(orderItem.getString("productId")));
						orderItemsElement.appendChild(productIdElement);

						// productId element
						Element parentProductIdElement = doc.createElement("parentProductId");
						parentProductIdElement.appendChild(doc.createTextNode((ProductHelper.isCustomSku(orderItem.getString("productId"))) ? orderItem.getString("productId") : EnvUtil.convertToString(product.getString("parentProductId"))));
						orderItemsElement.appendChild(parentProductIdElement);

						// kind element
						Element kind = doc.createElement("kind");
						kind.appendChild(doc.createTextNode(productName));
						orderItemsElement.appendChild(kind);

						// size element
						Element sizeElement = doc.createElement("size");
						sizeElement.appendChild(doc.createTextNode((UtilValidate.isEmpty(productFeatures.get("SIZE"))) ? "NA" : EnvUtil.convertToString((String) productFeatures.get("SIZE"))));
						orderItemsElement.appendChild(sizeElement);

						// pageHeight element
						Element pageHeight = doc.createElement("pageHeight");
						pageHeight.appendChild(doc.createTextNode((UtilValidate.isEmpty(product.getBigDecimal("productHeight"))) ? "NA" : EnvUtil.convertToString(product.getBigDecimal("productHeight")))); //height is really width
						orderItemsElement.appendChild(pageHeight);

						// pageWidth element
						Element pageWidth = doc.createElement("pageWidth");
						pageWidth.appendChild(doc.createTextNode((UtilValidate.isEmpty(product.getBigDecimal("productWidth"))) ? "NA" : EnvUtil.convertToString(product.getBigDecimal("productWidth")))); //width is really height
						orderItemsElement.appendChild(pageWidth);

						// color element
						Element color = doc.createElement("color");
						color.appendChild(doc.createTextNode(EnvUtil.convertToString((String) productFeatures.get("COLOR"))));
						orderItemsElement.appendChild(color);

						// quantity element
						Element quantity = doc.createElement("quantity");
						quantity.appendChild(doc.createTextNode(EnvUtil.convertToString(orderItem.getBigDecimal("quantity"))));
						orderItemsElement.appendChild(quantity);

						// quantity element
						Element status = doc.createElement("status");
						status.appendChild(doc.createTextNode(orderItem.getString("statusId")));
						orderItemsElement.appendChild(status);

						// inkFront element
						Element inkFront = doc.createElement("inkFront");
						inkFront.appendChild(doc.createTextNode(EnvUtil.convertToString(colorsFront)));
						orderItemsElement.appendChild(inkFront);

						// inkBack element
						Element inkBack = doc.createElement("inkBack");
						inkBack.appendChild(doc.createTextNode(EnvUtil.convertToString(colorsBack)));
						orderItemsElement.appendChild(inkBack);

						// totalInkColors element
						Element totalInkColorsFront = doc.createElement("totalInkColorsFront");
						totalInkColorsFront.appendChild(doc.createTextNode(EnvUtil.convertToString(colorsFront)));
						orderItemsElement.appendChild(totalInkColorsFront);

						// frontInkColor element
						Element frontInkColor1 = doc.createElement("frontInkColor1");
						frontInkColor1.appendChild(doc.createTextNode(EnvUtil.convertToString(orderItemArtwork.getString("frontInkColor1"))));
						orderItemsElement.appendChild(frontInkColor1);

						Element frontInkColor2 = doc.createElement("frontInkColor2");
						frontInkColor2.appendChild(doc.createTextNode(EnvUtil.convertToString(orderItemArtwork.getString("frontInkColor2"))));
						orderItemsElement.appendChild(frontInkColor2);

						Element frontInkColor3 = doc.createElement("frontInkColor3");
						frontInkColor3.appendChild(doc.createTextNode(EnvUtil.convertToString(orderItemArtwork.getString("frontInkColor3"))));
						orderItemsElement.appendChild(frontInkColor3);

						Element frontInkColor4 = doc.createElement("frontInkColor4");
						frontInkColor4.appendChild(doc.createTextNode(EnvUtil.convertToString(orderItemArtwork.getString("frontInkColor4"))));
						orderItemsElement.appendChild(frontInkColor4);

						// totalInkColors element
						Element totalInkColorsBack = doc.createElement("totalInkColorsBack");
						totalInkColorsBack.appendChild(doc.createTextNode(EnvUtil.convertToString(colorsBack)));
						orderItemsElement.appendChild(totalInkColorsBack);

						// backInkColor element
						Element backInkColor1 = doc.createElement("backInkColor1");
						backInkColor1.appendChild(doc.createTextNode(EnvUtil.convertToString(orderItemArtwork.getString("backInkColor1"))));
						orderItemsElement.appendChild(backInkColor1);

						Element backInkColor2 = doc.createElement("backInkColor2");
						backInkColor2.appendChild(doc.createTextNode(EnvUtil.convertToString(orderItemArtwork.getString("backInkColor2"))));
						orderItemsElement.appendChild(backInkColor2);

						Element backInkColor3 = doc.createElement("backInkColor3");
						backInkColor3.appendChild(doc.createTextNode(EnvUtil.convertToString(orderItemArtwork.getString("backInkColor3"))));
						orderItemsElement.appendChild(backInkColor3);

						Element backInkColor4 = doc.createElement("backInkColor4");
						backInkColor4.appendChild(doc.createTextNode(EnvUtil.convertToString(orderItemArtwork.getString("backInkColor4"))));
						orderItemsElement.appendChild(backInkColor4);

						Element cutsEl = doc.createElement("cuts");
						cutsEl.appendChild(doc.createTextNode(EnvUtil.convertToString(cuts)));
						orderItemsElement.appendChild(cutsEl);

						Element foldingEl = doc.createElement("folding");
						foldingEl.appendChild(doc.createTextNode(EnvUtil.convertToString(isFolded)));
						orderItemsElement.appendChild(foldingEl);

						Element addressingEl = doc.createElement("vdp");
						addressingEl.appendChild(doc.createTextNode(EnvUtil.convertToString(hasAddressing)));
						orderItemsElement.appendChild(addressingEl);

						Element colorMatchedEl = doc.createElement("colorMatched");
						colorMatchedEl.appendChild(doc.createTextNode(EnvUtil.convertToString(UtilValidate.isEmpty(orderItemArtwork.getString("inkMatched")) ? "N" : orderItemArtwork.getString("inkMatched"))));
						orderItemsElement.appendChild(colorMatchedEl);

						// productionTime element
						Element productionTime = doc.createElement("productionTime");
						if(UtilValidate.isNotEmpty(orderItem.getString("isRushProduction")) && orderItem.getString("isRushProduction").equals("Y")) {
							productionTime.appendChild(doc.createTextNode("RUSH"));
						} else {
							productionTime.appendChild(doc.createTextNode("STANDARD"));
						}
						orderItemsElement.appendChild(productionTime);

						Element dueDate = doc.createElement("dueDate");
						if(UtilValidate.isNotEmpty(orderItem.getTimestamp("dueDate"))){
							dueDate.appendChild(doc.createTextNode(orderItem.getTimestamp("dueDate").toString().replace(":","_")));
						} else {
							dueDate.appendChild(doc.createTextNode(""));
						}
						orderItemsElement.appendChild(dueDate);

						// printPosition element
						Element printPosition = doc.createElement("printPosition");
						printPosition.appendChild(doc.createTextNode(EnvUtil.convertToString(orderItemArtwork.getString("itemPrintPosition"))));
						orderItemsElement.appendChild(printPosition);

						// inkColor element
						Element inkColor = doc.createElement("inkColor");
						inkColor.appendChild(doc.createTextNode(EnvUtil.convertToString(orderItemArtwork.getString("itemInkColor"))));
						orderItemsElement.appendChild(inkColor);

						// totalFiles element
						Element totalFiles = doc.createElement("totalFiles");
						totalFiles.appendChild(doc.createTextNode(EnvUtil.convertToString(orderItemContentList.size())));
						orderItemsElement.appendChild(totalFiles);

						// inkColor element
						Element artSource = doc.createElement("artSource");
						artSource.appendChild(doc.createTextNode(EnvUtil.convertToString(orderItem.getString("artworkSource"))));
						orderItemsElement.appendChild(artSource);

						List<GenericValue> orderItemArtworkComments = delegator.findByAnd("OrderItemArtworkComment", UtilMisc.toMap("orderItemArtworkId", orderItemArtwork.getString("orderItemArtworkId")), UtilMisc.toList("createdStamp ASC"), false);

						// comments element
						Element commentsElement = doc.createElement("comments");
						orderItemsElement.appendChild(commentsElement);

						StringBuilder originalComments = new StringBuilder();
						StringBuilder rejectionComments = new StringBuilder();

						//get first comment from customer if exists
						if(UtilValidate.isNotEmpty(orderItem.getString("comments"))) {
							originalComments.append(orderItem.getString("comments"));
							if(!originalComments.toString().endsWith(".")) {
								originalComments.append(".");
							}
						}
						for(GenericValue orderItemArtworkComment : orderItemArtworkComments) {
							if(UtilValidate.isNotEmpty(orderItemArtworkComment.getString("typeEnumId")) && orderItemArtworkComment.getString("typeEnumId").equals("OIAC_INSTRUCTION")) {
								originalComments.append(UtilValidate.isNotEmpty(orderItemArtworkComment.getString("message")) ? orderItemArtworkComment.getString("message") : "");
							} else if(UtilValidate.isNotEmpty(orderItemArtworkComment.getString("typeEnumId")) && orderItemArtworkComment.getString("typeEnumId").equals("OIAC_REJECTPROOF")) {
								rejectionComments.append(UtilValidate.isNotEmpty(orderItemArtworkComment.getString("message")) ? orderItemArtworkComment.getString("message") : "");
							}
						}

						if(UtilValidate.isNotEmpty(originalComments.toString())) {
							int commentCount = 1;
							Element prepressComments = doc.createElement("customerComments");
							commentsElement.appendChild(prepressComments);

							String[] sentences = originalComments.toString().split("\\.");
							for(int s = 0; s < sentences.length; s++) {
								if(UtilValidate.isNotEmpty(sentences[s].trim())) {
									Element comment = doc.createElement("comment" + commentCount);
									comment.appendChild(doc.createTextNode(sentences[s].trim() + "."));
									prepressComments.appendChild(comment);
									commentCount++;
								}
							}
						}

						if(UtilValidate.isNotEmpty(rejectionComments.toString())) {
							int commentCount = 1;
							Element prepressComments = doc.createElement("rejectionComments");
							commentsElement.appendChild(prepressComments);

							String[] sentences = rejectionComments.toString().split("\\.");
							for(int s = 0; s < sentences.length; s++) {
								if(UtilValidate.isNotEmpty(sentences[s].trim())) {
									Element comment = doc.createElement("comment" + commentCount);
									comment.appendChild(doc.createTextNode(sentences[s].trim() + "."));
									prepressComments.appendChild(comment);
									commentCount++;
								}
							}
						}

						if(UtilValidate.isNotEmpty(orderItemArtwork.getString("itemPrepressComments"))) {
							int commentCount = 1;
							Element prepressComments = doc.createElement("prePressComments");
							commentsElement.appendChild(prepressComments);

							String[] sentences = orderItemArtwork.getString("itemPrepressComments").split("\\.");
							for(int s = 0; s < sentences.length; s++) {
								if(UtilValidate.isNotEmpty(sentences[s].trim())) {
									Element comment = doc.createElement("comment" + commentCount);
									comment.appendChild(doc.createTextNode(sentences[s].trim() + "."));
									prepressComments.appendChild(comment);
									commentCount++;
								}
							}
						}

						if(UtilValidate.isNotEmpty(orderItemArtwork.getString("itemCustomerComments"))) {
							int commentCount = 1;
							Element prepressComments = doc.createElement("prePressToCustomer");
							commentsElement.appendChild(prepressComments);

							String[] sentences = orderItemArtwork.getString("itemCustomerComments").split("\\.");
							for(int s = 0; s < sentences.length; s++) {
								if(UtilValidate.isNotEmpty(sentences[s].trim())) {
									Element comment = doc.createElement("comment" + commentCount);
									comment.appendChild(doc.createTextNode(sentences[s].trim() + "."));
									prepressComments.appendChild(comment);
									commentCount++;
								}
							}
						}

						if(commentsElement.getChildNodes().getLength() == 0) {
							commentsElement.appendChild(doc.createTextNode(""));
						}

						// files element
						Element filesElement = doc.createElement("files");
						orderItemsElement.appendChild(filesElement);

						/*boolean artReused = false;
						boolean parentSkuMatched = false;

						if(UtilValidate.isNotEmpty(orderItem.getString("artworkSource")) && orderItem.getString("artworkSource").equals("ART_REUSED")) {
							artReused = true;
							Element reuse = doc.createElement("reuse");
							Element reuseFile = doc.createElement("reuseFilename");
							if(UtilValidate.isNotEmpty(orderItem.getString("referenceOrderId")) && UtilValidate.isNotEmpty(orderItem.getString("referenceOrderItemSeqId"))) {
								parentSkuMatched = getParentProductId(delegator, orderItem.getString("referenceOrderId"), orderItem.getString("referenceOrderItemSeqId")).equals(product.getString("parentProductId"));
								reuse.appendChild(doc.createTextNode(orderItem.getString("referenceOrderId")));
								reuseFile.appendChild(doc.createTextNode(orderItem.getString("referenceOrderId") + "_" + orderItem.getString("referenceOrderItemSeqId") + ".pdf"));
							} else {
								reuse.appendChild(doc.createTextNode(""));
								reuseFile.appendChild(doc.createTextNode(""));
							}
							filesElement.appendChild(reuse);
							filesElement.appendChild(reuseFile);
						}*/

						int counter = 1;
						for(GenericValue orderItemContent : orderItemContentList) {
							String contentName = UtilValidate.isNotEmpty(orderItemContent.get("contentName")) ? orderItemContent.getString("contentName") : "_NA_";
							if(orderItem.getString("statusId").equals("ART_PROOF_REJECTED")) {
								if(orderItemContent.getTimestamp("fromDate").equals(getOrderStatus.getTimestamp("statusDatetime")) || orderItemContent.getTimestamp("fromDate").after(getOrderStatus.getTimestamp("statusDatetime"))) {
									Element fileNumber = doc.createElement("newfileNumber");
									fileNumber.appendChild(doc.createTextNode(Integer.toString(counter)));
									filesElement.appendChild(fileNumber);

									// fileName element
									Element fileName = doc.createElement("newfileName");
									fileName.appendChild(doc.createTextNode(contentName));
									filesElement.appendChild(fileName);

									String fileExt = "";
									if(contentName.lastIndexOf(".") != -1) {
										fileExt = orderItemContent.getString("contentName").substring(orderItemContent.getString("contentName").lastIndexOf(".") + 1);
									}

									// fileName element
									Element extention = doc.createElement("extention");
									extention.appendChild(doc.createTextNode(fileExt));
									filesElement.appendChild(extention);

									//now lets try to ftp the file
									String fullArtworkPath = EnvConstantsUtil.OFBIZ_HOME + orderItemContent.getString("contentPath");
									srcFiles.put(contentName, fullArtworkPath);

									counter++;
								}
							} else {
								Element fileNumber = doc.createElement("fileNumber");
								fileNumber.appendChild(doc.createTextNode(Integer.toString(counter)));
								filesElement.appendChild(fileNumber);

								// fileName element
								Element fileName = doc.createElement("fileName");
								fileName.appendChild(doc.createTextNode(contentName));
								filesElement.appendChild(fileName);

								String fileExt = "";
								if(contentName.lastIndexOf(".") != -1) {
									fileExt = orderItemContent.getString("contentName").substring(orderItemContent.getString("contentName").lastIndexOf(".") + 1);
								}

								// fileName element
								Element extention = doc.createElement("extention");
								extention.appendChild(doc.createTextNode(fileExt));
								filesElement.appendChild(extention);

								//now lets try to ftp the file
								String fullArtworkPath = EnvConstantsUtil.OFBIZ_HOME + orderItemContent.getString("contentPath");
								srcFiles.put(contentName, fullArtworkPath);

								counter++;
							}
						}

						if(UtilValidate.isNotEmpty(orderItem.getString("artworkSource")) && orderItem.getString("artworkSource").equals("SCENE7_ART_ONLINE")) {
							GenericValue s7Front = EntityUtil.getFirst(getOrderItemContent(delegator, orderId, orderItem.getString("orderItemSeqId"), "OIACPRP_SC7_FRNT_PDF"));
							GenericValue s7Back = EntityUtil.getFirst(getOrderItemContent(delegator, orderId, orderItem.getString("orderItemSeqId"), "OIACPRP_SC7_BACK_PDF"));
							if(UtilValidate.isNotEmpty(s7Front)) {
								Element fileNumber = doc.createElement("fileNumber");
								fileNumber.appendChild(doc.createTextNode(Integer.toString(counter)));
								filesElement.appendChild(fileNumber);

								// fileName element
								Element fileName = doc.createElement("fileName");
								fileName.appendChild(doc.createTextNode(EnvUtil.convertToString(s7Front.getString("contentName"))));
								filesElement.appendChild(fileName);

								String fileExt = "";
								if(UtilValidate.isNotEmpty(s7Front.getString("contentName")) && s7Front.getString("contentName").lastIndexOf(".") != -1) {
									fileExt = s7Front.getString("contentName").substring(s7Front.getString("contentName").lastIndexOf(".") + 1);
								}

								// fileName element
								Element extention = doc.createElement("extention");
								extention.appendChild(doc.createTextNode(EnvUtil.convertToString(fileExt)));
								filesElement.appendChild(extention);
								// fileName element
								Element fileUrl = doc.createElement("fileUrlFront");
								String urlToStream = "https://www.envelopes.com/envelopes/control/downloadFile?filePath=" + s7Front.getString("contentPath") + "&fileName=" + ((UtilValidate.isNotEmpty(s7Front.getString("contentName"))) ? s7Front.getString("contentName") : "_NA_") + "&downLoad=Y";
								fileUrl.appendChild(doc.createTextNode(EnvUtil.convertToString(urlToStream)));
								filesElement.appendChild(fileUrl);
								counter++;
							}
							if(UtilValidate.isNotEmpty(s7Back)) {
								Element fileNumber = doc.createElement("fileNumber");
								fileNumber.appendChild(doc.createTextNode(Integer.toString(counter)));
								filesElement.appendChild(fileNumber);

								// fileName element
								Element fileName = doc.createElement("fileName");
								fileName.appendChild(doc.createTextNode(EnvUtil.convertToString(s7Back.getString("contentName"))));
								filesElement.appendChild(fileName);

								String fileExt = "";
								if(UtilValidate.isNotEmpty(s7Back.getString("contentName")) && s7Back.getString("contentName").lastIndexOf(".") != -1) {
									fileExt = s7Back.getString("contentName").substring(s7Back.getString("contentName").lastIndexOf(".") + 1);
								}

								// fileName element
								Element extention = doc.createElement("extention");
								extention.appendChild(doc.createTextNode(EnvUtil.convertToString(fileExt)));
								filesElement.appendChild(extention);
								// fileName element
								Element fileUrl = doc.createElement("fileUrlBack");
								String urlToStream = "https://www.envelopes.com/envelopes/control/downloadFile?filePath=" + s7Back.getString("contentPath") + "&fileName=" + ((UtilValidate.isNotEmpty(s7Back.getString("contentName"))) ? s7Back.getString("contentName") : "_NA_") + "&downLoad=Y";
								fileUrl.appendChild(doc.createTextNode(EnvUtil.convertToString(urlToStream)));
								filesElement.appendChild(fileUrl);
							}
						}

						if(UtilValidate.isNotEmpty(orderItem.getString("artworkSource")) && orderItem.getString("artworkSource").equals("ART_REUSED")) {
							Element reuse = doc.createElement("reuse");
							Element reuseFile = doc.createElement("reuseFilename");
							if(UtilValidate.isNotEmpty(orderItem.getString("referenceOrderId")) && UtilValidate.isNotEmpty(orderItem.getString("referenceOrderItemSeqId"))) {
								reuse.appendChild(doc.createTextNode(orderItem.getString("referenceOrderId")));

								GenericValue reuseContent = EntityUtil.getFirst(EntityUtil.filterByDate(delegator.findByAnd("OrderItemContent", UtilMisc.toMap("orderId", orderItem.getString("referenceOrderId"), "orderItemSeqId", orderItem.getString("referenceOrderItemSeqId"), "contentPurposeEnumId", "OIACPRP_PDF"), UtilMisc.toList("createdStamp DESC"), false)));
								if(UtilValidate.isNotEmpty(reuseContent) && UtilValidate.isNotEmpty(reuseContent.getString("contentName"))) {
									reuseFile.appendChild(doc.createTextNode(reuseContent.getString("contentName")));
								} else {
									reuseFile.appendChild(doc.createTextNode(orderItem.getString("referenceOrderId") + "_" + orderItem.getString("referenceOrderItemSeqId") + ".pdf"));
								}
							} else {
								reuse.appendChild(doc.createTextNode(""));
								reuseFile.appendChild(doc.createTextNode(""));
							}
							filesElement.appendChild(reuse);
							filesElement.appendChild(reuseFile);
						}

						counter = 1;

						String frontTemplateName = "";
						String frontTemplate = FileHelper.doesFileExist("/hot-deploy/html/webapp/html/files/templates/", orderItem.getString("productId") + "_FRONT.pdf");
						if(UtilValidate.isNotEmpty(frontTemplate)) {
							frontTemplateName = orderItem.getString("productId") + "_FRONT.pdf";
						} else {
							frontTemplate = FileHelper.doesFileExist("/hot-deploy/html/webapp/html/files/templates/", product.getString("parentProductId") + "_FRONT.pdf");
							if(UtilValidate.isNotEmpty(frontTemplate)) {
								frontTemplateName = product.getString("parentProductId") + "_FRONT.pdf";
							}
						}

						String backTemplateName = "";
						String backTemplate = FileHelper.doesFileExist("/hot-deploy/html/webapp/html/files/templates/", orderItem.getString("productId") + "_BACK.pdf");
						if(UtilValidate.isNotEmpty(backTemplate)) {
							backTemplateName = orderItem.getString("productId") + "_BACK.pdf";
						} else {
							backTemplate = FileHelper.doesFileExist("/hot-deploy/html/webapp/html/files/templates/", product.getString("parentProductId") + "_BACK.pdf");
							if(UtilValidate.isNotEmpty(backTemplate)) {
								backTemplateName = product.getString("parentProductId") + "_BACK.pdf";
							}
						}

						// template files
						if(colorsFront > 0 && UtilValidate.isNotEmpty(frontTemplateName)) {
							Element templateNumber = doc.createElement("templateNumber");
							templateNumber.appendChild(doc.createTextNode(EnvUtil.convertToString(Integer.toString(counter))));
							filesElement.appendChild(templateNumber);

							// templateNumber element
							Element templateName = doc.createElement("templateName");
							templateName.appendChild(doc.createTextNode(EnvUtil.convertToString(frontTemplateName)));
							filesElement.appendChild(templateName);

							srcFiles.put(frontTemplateName, frontTemplate);

							counter++;
						}

						if(colorsBack > 0 && UtilValidate.isNotEmpty(backTemplateName)) {
							// templateNumber element
							Element templateNumber = doc.createElement("templateNumber");
							templateNumber.appendChild(doc.createTextNode(EnvUtil.convertToString(Integer.toString(counter))));
							filesElement.appendChild(templateNumber);

							// templateNumber element
							Element templateName = doc.createElement("templateName");
							templateName.appendChild(doc.createTextNode(EnvUtil.convertToString(backTemplateName)));
							filesElement.appendChild(templateName);

							srcFiles.put(backTemplateName, backTemplate);

						}

						//store data
						TransformerFactory tf = TransformerFactory.newInstance();
						Transformer transformer = tf.newTransformer();

						//save to file
						//Debug.logError("fullFilePath: " + fullFilePath, module);
						DOMSource source = new DOMSource(doc);
						StreamResult result = new StreamResult(new File(xmlFile));
						transformer.transform(source, result);

						srcFiles.put(xmlFileName, xmlFile);

						Debug.logError("ABOUT TO ZIP FILES: " + srcFiles, module);
						if(UtilValidate.isNotEmpty(srcFiles)) {
							ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
							byte[] buf = new byte[1024];

							Iterator iter = srcFiles.entrySet().iterator();
							while(iter.hasNext()) {
								Map.Entry pairs = (Map.Entry) iter.next();
								File srcFile = new File((String) pairs.getValue());
								FileInputStream in = null;
								try {
									in = new FileInputStream(srcFile);
									out.putNextEntry(new ZipEntry((String) pairs.getKey()));

									int len;
									while ((len = in.read(buf)) > 0) {
										out.write(buf, 0, len);
									}
								} finally {
									out.closeEntry();
									if(in != null) {
										in.close();
									}
								}
							}

							out.close();
						}

						//set zip file permissions
						File createdZipFile = new File(zipFile);
						createdZipFile.setExecutable(true, false);
						createdZipFile.setReadable(true, false);
						createdZipFile.setWritable(true, false);

						//delete xml file
						try {
							File file = new File(xmlFile);
							if(file.exists()) {
								if(file.delete()){
									Debug.logError("File [" + xmlFileName + "] deleted.", module);
								} else {
									Debug.logError("File [" + xmlFileName + "] not deleted.", module);
								}
							}
						} catch (SecurityException e) {
							Debug.logError(e, "Caught SecurityException while trying to delete file: " + xmlFileName, module);
						} catch (Exception e){
							Debug.logError(e, "Caught Exception while trying to delete file: " + xmlFileName, module);
						}
					} catch(Exception e) {
						EnvUtil.reportError(e);
					}
				}
			}
		}
	}

	/*
		Get a list of the old created custom envelopes (made to order)
	*/
	public static EntityListIterator getOldCustomEnvelopeList(Delegator delegator) throws GenericEntityException {
		EntityListIterator eli = null;
		EntityFindOptions efo = new EntityFindOptions();
		List<EntityCondition> conditionList = new ArrayList<EntityCondition>();

		conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "QUO_CREATED"));
		conditionList.add(EntityCondition.makeCondition("userEmail", EntityOperator.NOT_EQUAL, null));
		eli = delegator.find("OldCustomEnvelopeAndContact", ((UtilValidate.isNotEmpty(conditionList)) ? EntityCondition.makeCondition(conditionList, EntityOperator.AND) : null), null, null, UtilMisc.toList("createdDate DESC"), efo);

		return eli;
	}

	/*
	 * Is item a sample
	 */
	public static boolean isItemSample(Delegator delegator, String productId, BigDecimal quantity, GenericValue product) throws GenericEntityException {
		if(product == null) {
			product = ProductHelper.getProduct(delegator, productId);
		}

		if(quantity.compareTo(new BigDecimal("5")) <= 0 && !("N").equalsIgnoreCase(product.getString("hasSample"))) {
			return true;
		} else if(EnvConstantsUtil.SAMPLE_PRODUCT.equalsIgnoreCase(productId)) {
			return true;
		}

		return false;
	}
	public static boolean isItemSample(GenericValue orderItem, GenericValue product) throws GenericEntityException {
 		return isItemSample(orderItem.getDelegator(), orderItem.getString("productId"), orderItem.getBigDecimal("quantity"), product);
	}

	/*
	 * Is an order only samples
	 */
	public static boolean isOrderOnlySample(List<GenericValue> orderItems) throws GenericEntityException {
		boolean isOrderOnlySample = true;

		if(UtilValidate.isNotEmpty(orderItems)) {
			for (GenericValue orderItem : orderItems) {
				GenericValue product = ProductHelper.getProduct(orderItem.getDelegator(), orderItem.getString("productId"));
				if ((orderItem.getBigDecimal("quantity").compareTo(new BigDecimal("5")) <= 0 && !("N").equalsIgnoreCase(product.getString("hasSample"))) || EnvConstantsUtil.SAMPLE_PRODUCT.equalsIgnoreCase(product.getString("productId"))) {
					isOrderOnlySample = true;
					continue;
				} else {
					isOrderOnlySample = false;
					break;
				}
			}
		}

		return isOrderOnlySample;
	}

	public static boolean isOrderOnlyCustomProducts(List<GenericValue> orderItems) {
		if(UtilValidate.isNotEmpty(orderItems)) {
			for (GenericValue orderItem : orderItems) {
				if (!"CUSTOM-P".equalsIgnoreCase(orderItem.getString("productId"))) {
					return false;
				}
			}
		}
		return true;
	}

	/*
	 * Check if order has sample
	 */
	public static Map<String, Object> orderSampleData(Delegator delegator, List<GenericValue> orderItems, boolean createCoupon) throws GenericEntityException {
		Map<String, Object> sampleData = new HashMap<>();
		BigDecimal sampleTotal = BigDecimal.ZERO;
		if(UtilValidate.isNotEmpty(orderItems)) {
			for(GenericValue orderItem : orderItems) {
				if(isItemSample(orderItem, null)) {
					sampleData.put("hasSample", true);
					sampleTotal = sampleTotal.add(orderItem.getBigDecimal("unitPrice").multiply(orderItem.getBigDecimal("quantity")));
				}
			}
			sampleData.put("sampleTotal", sampleTotal);
			sampleData.put("promotionAmount", (sampleTotal.compareTo(new BigDecimal("5")) > 0) ? new BigDecimal("5") : sampleTotal);
		} else {
			sampleData.put("hasSample", false);
			sampleData.put("sampleTotal", sampleTotal);
		}

		if(createCoupon) {
			String sampleCoupon = PromoHelper.generateRandomPromoCode(delegator);
			PromoHelper.createSampleCoupon(delegator, sampleCoupon, (sampleTotal.compareTo(new BigDecimal("5")) > 0) ? 5 : sampleTotal.intValue());
			sampleData.put("sampleCoupon", sampleCoupon);
			sampleData.put("expireDate", EnvUtil.getNDaysBeforeOrAfterNow(EnvConstantsUtil.SAMPLE_EXPIRE_DAYS, false));
		}

		return sampleData;
	}

	/*
	 * Change status for item if its printed
	 */

	public static boolean updateStatusForPrintItem(LocalDispatcher dispatcher, Delegator delegator, GenericValue orderItem) throws GenericEntityException, GenericServiceException {
		return updateStatusForPrintItem(dispatcher, delegator, orderItem, null);
	}
	public static boolean updateStatusForPrintItem(LocalDispatcher dispatcher, Delegator delegator, GenericValue orderItem, String webSiteId) throws GenericEntityException, GenericServiceException {
		if(isItemPrinted(delegator, null, null, orderItem)) {
			boolean isReorder = UtilValidate.isNotEmpty(orderItem.getString("referenceOrderId"));
			boolean hasChanges = reOrderWithChanges(delegator, orderItem);

			if("ART_NOT_RECEIVED".equalsIgnoreCase(orderItem.getString("artworkSource")) || "ART_UPLOADED_LATER".equalsIgnoreCase(orderItem.getString("artworkSource"))) {
				dispatcher.runAsync("changeOrderItemStatus", UtilMisc.toMap("orderId", orderItem.getString("orderId"), "orderItemSeqId", orderItem.getString("orderItemSeqId"), "statusId", "ART_NOT_RECEIVED", "userLogin", EnvUtil.getSystemUser(delegator)));
				return true;
			} else if(isReorder && !hasChanges) {
				dispatcher.runAsync("changeOrderItemStatus", UtilMisc.toMap("orderId", orderItem.getString("orderId"), "orderItemSeqId", orderItem.getString("orderItemSeqId"), "statusId", "ART_REORDER", "userLogin", EnvUtil.getSystemUser(delegator)));
				updateOrderItemArtwork(delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId"), UtilMisc.toMap("assignedToUserLogin", "Switch", "lastModifiedByUserLogin", "Switch", "lastModifiedDate", UtilDateTime.nowTimestamp()));
			} else if(!"folders".equalsIgnoreCase(webSiteId) && !isReorder && ("SCENE7_ART_ONLINE".equalsIgnoreCase(orderItem.getString("artworkSource")) || "ART_DESIGNED_ONLINE".equalsIgnoreCase(orderItem.getString("artworkSource")))) {
				dispatcher.runAsync("changeOrderItemStatus", UtilMisc.toMap("orderId", orderItem.getString("orderId"), "orderItemSeqId", orderItem.getString("orderItemSeqId"), "statusId", "ART_PROOF_APPROVED", "userLogin", EnvUtil.getSystemUser(delegator)));
			} else {
				dispatcher.runAsync("changeOrderItemStatus", UtilMisc.toMap("orderId", orderItem.getString("orderId"), "orderItemSeqId", orderItem.getString("orderItemSeqId"), "statusId", "ART_PENDING_PROOF", "userLogin", EnvUtil.getSystemUser(delegator)));
			}
		}

		return false;
	}

	public static boolean reOrderWithChanges(Delegator delegator, GenericValue orderItem) throws GenericEntityException, GenericServiceException {
		boolean hasChanges = false;
		String orderedFromOderId = orderItem.getString("referenceOrderId");
		String orderedFromOderItemSeqId = orderItem.getString("referenceOrderItemSeqId");

		if(UtilValidate.isNotEmpty(orderItem.get("comments"))) {
			hasChanges = true;
		} else if(UtilValidate.isNotEmpty(orderedFromOderId) && UtilValidate.isNotEmpty(orderedFromOderItemSeqId)) {
			GenericValue reOrderItem = delegator.findOne("OrderItem", UtilMisc.toMap("orderId", orderedFromOderId, "orderItemSeqId", orderedFromOderItemSeqId), false);
			if(UtilValidate.isNotEmpty(reOrderItem)) {
				GenericValue product = orderItem.getRelatedOne("Product", true);
				GenericValue reOrderProduct = reOrderItem.getRelatedOne("Product", true);
				if(UtilValidate.isNotEmpty(product) && UtilValidate.isNotEmpty(reOrderProduct) && UtilValidate.isNotEmpty(product.getString("parentProductId")) && UtilValidate.isNotEmpty(reOrderProduct.getString("parentProductId")) && product.getString("parentProductId").equalsIgnoreCase(reOrderProduct.getString("parentProductId"))) {
					hasChanges = false;
				} else {
					hasChanges = true;
				}
			}
		}

		return hasChanges;
	}

	/*
	 * Prepress level queues
	 */
	public static List<Map> prepressQueue(Delegator delegator, String webSiteId) throws GenericEntityException {
		Pattern foldersPattern = Pattern.compile("(?:^|,)folders(?:,|$)");
		Matcher foldersMatcher = foldersPattern.matcher(webSiteId);


		List<String> statusListType = new ArrayList<>();
		statusListType.add("ART_PENDING_PROOF");
		statusListType.add("Scene7");                  //manual list
		statusListType.add("Variable");                //manual list
		statusListType.add("ART_NOT_RECEIVED");
		statusListType.add("ITEM_BACKORDERED");
		statusListType.add("ART_PENDING");
		statusListType.add("ART_PROD_CHECK");
		statusListType.add("ART_PROOF_APPROVED");
		statusListType.add("ART_PROOF_REJECTED");
		statusListType.add("ART_REORDER");
		statusListType.add("ART_READY_FOR_REVIEW");
		statusListType.add("ART_RDY_FOR_INTRNL_REVIEW");
		statusListType.add("PP_ASSIGNED_TO_PLATE");
		statusListType.add("SENT_PURCHASE_ORDER");

		//if (foldersMatcher.find()) {
			statusListType.add("ART_GOOD_TO_PROOF");
			statusListType.add("ART_GOOD_TO_GO");
			statusListType.add("ITEM_SHIPPED");
			statusListType.add("ART_PRINTED");
			statusListType.add("Outsourceable");           //manual list
			statusListType.add("ART_OUTSOURCED");
			statusListType.add("Syracuseable");           //manual list
			statusListType.add("ART_SYRACUSE");
			statusListType.add("Vendor");                  //manual list
		//}

		List<String> proofReadyKeys = new ArrayList<>();
		proofReadyKeys.add("Rush");
		proofReadyKeys.add("Sent Today");
		proofReadyKeys.add("Sent Yesterday");
		proofReadyKeys.add("Sent 2-5 Days Ago");
		proofReadyKeys.add("Sent 5-30 Days Ago");
		proofReadyKeys.add("Sent 30+ Days Ago");

		List<String> fullStatusList = new ArrayList<>();

		fullStatusList.add("ART_PROOF_APPROVED");  //manual list

		//if (foldersMatcher.find()) {
			fullStatusList.add("ART_GOOD_TO_GO");      //manual list
		//}

		Map<String, Map<String, List<String>>> offSetQueue = new LinkedHashMap<>();
		Map<String, Map<String, List<String>>> digitalQueue = new LinkedHashMap<>();
		Map<String, List<String>> proofReady = new LinkedHashMap<>();
		Map<String, List<String>> fullStatus = new LinkedHashMap<>();

		for(int i = 0; i<statusListType.size(); i++) {
			if(!"ART_READY_FOR_REVIEW".equals(statusListType.get(i))) {
				digitalQueue.put(statusListType.get(i), UtilMisc.<String, List<String>>toMap("Standard", new ArrayList<String>(), "Rush", new ArrayList<String>()));
				if (!"Variable".equals(statusListType.get(i))) {
					offSetQueue.put(statusListType.get(i), UtilMisc.<String, List<String>>toMap("Standard", new ArrayList<String>(), "Rush", new ArrayList<String>()));
				}
			}
		}

		for(int i = 0; i<proofReadyKeys.size(); i++) {
			proofReady.put(proofReadyKeys.get(i), new ArrayList<String>());
		}

		for(int i = 0; i<fullStatusList.size(); i++) {
			fullStatus.put(fullStatusList.get(i), new ArrayList<String>());
		}

		DynamicViewEntity dve = new DynamicViewEntity();
		dve.addMemberEntity("OH", "OrderHeader");
		dve.addAlias("OH", "salesChannelEnumId");
		dve.addAlias("OH", "orderId");
		dve.addAlias("OH", "orderDate");
		dve.addAlias("OH", "orderStatusId", "statusId", null, null, null, null);
		dve.addMemberEntity("OI", "OrderItem");
		dve.addAlias("OI", "itemStatusId", "statusId", null, null, null, null);
		dve.addAlias("OI", "orderItemSeqId");
		dve.addAlias("OI", "productId");
		dve.addAlias("OI", "itemDescription");
		dve.addAlias("OI", "dueDate");
		dve.addAlias("OI", "quantity");
		dve.addAlias("OI", "isRushProduction");
		dve.addAlias("OI", "artworkSource");
		dve.addAlias("OI", "outsourceable");
		dve.addAlias("OI", "outsource");
		dve.addMemberEntity("OIA", "OrderItemArtwork");
		dve.addAlias("OIA", "assignedToUserLogin");
		dve.addMemberEntity("VO", "VendorOrder");
		dve.addAlias("VO", "partyId");
		dve.addMemberEntity("OIF", "OrderItemFlag");
		dve.addAlias("OIF", "printableSyracuse");
		dve.addViewLink("OH", "OI", true, ModelKeyMap.makeKeyMapList("orderId", "orderId"));
		dve.addViewLink("OI", "OIA", true, ModelKeyMap.makeKeyMapList("orderId", "orderId", "orderItemSeqId", "orderItemSeqId"));
		dve.addViewLink("OI", "VO", true, ModelKeyMap.makeKeyMapList("orderId", "orderId", "orderItemSeqId", "orderItemSeqId"));
		dve.addViewLink("OI", "OIF", true, ModelKeyMap.makeKeyMapList("orderId", "orderId", "orderItemSeqId", "orderItemSeqId"));

		List<EntityCondition> conditions = new ArrayList<>();
		if(UtilValidate.isEmpty(webSiteId) || !"folders".equalsIgnoreCase(webSiteId)) {
			conditions.add(EntityCondition.makeCondition("orderStatusId", EntityOperator.NOT_IN, UtilMisc.toList("ORDER_PENDING", "ORDER_CANCELLED")));
		}
		conditions.add(EntityCondition.makeCondition("itemStatusId", EntityOperator.IN, statusListType));
		conditions.add(EntityCondition.makeCondition("artworkSource", EntityOperator.NOT_EQUAL, null));
		conditions.add(EntityCondition.makeCondition("orderDate", EntityOperator.GREATER_THAN_EQUAL_TO, EnvUtil.getNDaysBeforeOrAfterNow(-60, true)));

		if(UtilValidate.isNotEmpty(webSiteId)) {
			List<EntityCondition> siteCondition = new ArrayList<>();
			String[] webSiteIds = webSiteId.split(",");
			for(String webSite : webSiteIds) {
				siteCondition.add(EntityCondition.makeCondition("salesChannelEnumId", EntityOperator.EQUALS, EnvUtil.getSalesChannelEnumId(webSite)));
			}
			conditions.add(EntityCondition.makeCondition(siteCondition, EntityOperator.OR));
		}

		EntityListIterator eli = null;
		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try {
				eli = delegator.findListIteratorByCondition(dve, EntityCondition.makeCondition(conditions, EntityOperator.AND), null, null, null, null);
				GenericValue order = null;
				while((order = eli.next()) != null) {
					//manual full list
					if("ART_GOOD_TO_GO".equalsIgnoreCase(order.getString("itemStatusId")) || "ART_PROOF_APPROVED".equalsIgnoreCase(order.getString("itemStatusId"))) {
						fullStatus.get(order.getString("itemStatusId")).add(order.getString("orderId"));
					}

					if("ART_READY_FOR_REVIEW".equalsIgnoreCase(order.getString("itemStatusId"))) {
						//get the status date
						GenericValue statusDate = EntityUtil.getFirst(delegator.findByAnd("OrderStatus", UtilMisc.toMap("statusId", "ART_READY_FOR_REVIEW", "orderId", order.getString("orderId"), "orderItemSeqId", order.getString("orderItemSeqId")), UtilMisc.toList("createdStamp DESC"), false));
						long days = 0;
						try {
							days = EnvUtil.getDaysBetweenDates(statusDate.getTimestamp("createdStamp"), UtilDateTime.nowTimestamp(), true);
							if ("Y".equals(order.getString("isRushProduction"))) {
								proofReady.get("Rush").add(order.getString("orderId"));
							}
							if(days == 0) {
								proofReady.get("Sent Today").add(order.getString("orderId"));
							} else if(days == 1) {
								proofReady.get("Sent Yesterday").add(order.getString("orderId"));
							} else if(days >= 2 && days <= 5) {
								proofReady.get("Sent 2-5 Days Ago").add(order.getString("orderId"));
							} else if(days >= 6 && days <= 30) {
								proofReady.get("Sent 5-30 Days Ago").add(order.getString("orderId"));
							} else if(days > 30) {
								proofReady.get("Sent 30+ Days Ago").add(order.getString("orderId"));
							}
						} catch(Exception e) {
							//todo
						}
					} else {
						Map<String, String> itemAttr = getOrderItemAttributeMap(delegator, order.getString("orderId"), order.getString("orderItemSeqId"));
						boolean hasAddressing = (UtilValidate.isNotEmpty(itemAttr.get("addresses")) && !"0".equals(itemAttr.get("addresses")));

						if(order.getBigDecimal("quantity").compareTo(new BigDecimal("500")) < 0 && (UtilValidate.isEmpty(webSiteId) || (UtilValidate.isNotEmpty(webSiteId) && !webSiteId.contains("folders")))) {
							//digital queue
							if(UtilValidate.isNotEmpty(order.getString("partyId"))) {
								if ("Y".equals(order.getString("isRushProduction"))) {
									digitalQueue.get("Vendor").get("Rush").add(order.getString("orderId"));
								} else {
									digitalQueue.get("Vendor").get("Standard").add(order.getString("orderId"));
								}
							}
							if("Y".equals(order.getString("outsourceable")) && !"ART_OUTSOURCED".equals(order.getString("itemStatusId"))) {
								if ("Y".equals(order.getString("isRushProduction"))) {
									digitalQueue.get("Outsourceable").get("Rush").add(order.getString("orderId"));
								} else {
									digitalQueue.get("Outsourceable").get("Standard").add(order.getString("orderId"));
								}
							}
							if("Y".equals(order.getString("printableSyracuse")) && !"ART_SYRACUSE".equals(order.getString("itemStatusId"))) {
								if ("Y".equals(order.getString("isRushProduction"))) {
									digitalQueue.get("Syracuseable").get("Rush").add(order.getString("orderId"));
								} else {
									digitalQueue.get("Syracuseable").get("Standard").add(order.getString("orderId"));
								}
							}
							if("ART_PRINTED".equalsIgnoreCase(order.getString("itemStatusId"))) {
								if ("Y".equals(order.getString("isRushProduction"))) {
									digitalQueue.get(order.getString("itemStatusId")).get("Rush").add(order.getString("orderId"));
								} else {
									digitalQueue.get(order.getString("itemStatusId")).get("Standard").add(order.getString("orderId"));
								}
							} else if(!"ART_OUTSOURCED".equals(order.getString("itemStatusId")) && !"ITEM_BACKORDERED".equals(order.getString("itemStatusId")) && !"ART_PROOF_REJECTED".equals(order.getString("itemStatusId")) && "SCENE7_ART_ONLINE".equals(order.getString("artworkSource")) && UtilValidate.isEmpty(order.getString("assignedToUserLogin"))) {
								if("Y".equals(order.getString("isRushProduction"))) {
									if (hasAddressing) {
										digitalQueue.get("Variable").get("Rush").add(order.getString("orderId"));
									} else {
										digitalQueue.get("Scene7").get("Rush").add(order.getString("orderId"));
									}
								} else {
									if (hasAddressing) {
										digitalQueue.get("Variable").get("Standard").add(order.getString("orderId"));
									} else {
										digitalQueue.get("Scene7").get("Standard").add(order.getString("orderId"));
									}
								}
							} else {
								if ("Y".equals(order.getString("isRushProduction"))) {
									digitalQueue.get(order.getString("itemStatusId")).get("Rush").add(order.getString("orderId"));
								} else {
									digitalQueue.get(order.getString("itemStatusId")).get("Standard").add(order.getString("orderId"));
								}
							}
						} else {
							//offset queue
							if(UtilValidate.isNotEmpty(order.getString("partyId"))) {
								if ("Y".equals(order.getString("isRushProduction"))) {
									offSetQueue.get("Vendor").get("Rush").add(order.getString("orderId"));
								} else {
									offSetQueue.get("Vendor").get("Standard").add(order.getString("orderId"));
								}
							}
							if("Y".equals(order.getString("outsourceable")) && !"ART_OUTSOURCED".equals(order.getString("itemStatusId"))) {
								if ("Y".equals(order.getString("isRushProduction"))) {
									offSetQueue.get("Outsourceable").get("Rush").add(order.getString("orderId"));
								} else {
									offSetQueue.get("Outsourceable").get("Standard").add(order.getString("orderId"));
								}
							}
							if("Y".equals(order.getString("printableSyracuse")) && !"ART_SYRACUSE".equals(order.getString("itemStatusId"))) {
								if ("Y".equals(order.getString("isRushProduction"))) {
									offSetQueue.get("Syracuseable").get("Rush").add(order.getString("orderId"));
								} else {
									offSetQueue.get("Syracuseable").get("Standard").add(order.getString("orderId"));
								}
							}
							if("ART_PRINTED".equalsIgnoreCase(order.getString("itemStatusId"))) {
								if ("Y".equals(order.getString("isRushProduction"))) {
									offSetQueue.get(order.getString("itemStatusId")).get("Rush").add(order.getString("orderId"));
								} else {
									offSetQueue.get(order.getString("itemStatusId")).get("Standard").add(order.getString("orderId"));
								}
							} else if(!"ART_OUTSOURCED".equals(order.getString("itemStatusId")) && !"ITEM_BACKORDERED".equals(order.getString("itemStatusId")) && !"ART_PROOF_REJECTED".equals(order.getString("itemStatusId")) && "SCENE7_ART_ONLINE".equals(order.getString("artworkSource")) && UtilValidate.isEmpty(order.getString("assignedToUserLogin"))) {
								if ("Y".equals(order.getString("isRushProduction"))) {
									if (hasAddressing) {
										digitalQueue.get("Variable").get("Rush").add(order.getString("orderId"));
									} else {
										offSetQueue.get("Scene7").get("Rush").add(order.getString("orderId"));
									}
								} else {
									if (hasAddressing) {
										digitalQueue.get("Variable").get("Standard").add(order.getString("orderId"));
									} else {
										offSetQueue.get("Scene7").get("Standard").add(order.getString("orderId"));
									}
								}
							} else {
								if ("Y".equals(order.getString("isRushProduction"))) {
									offSetQueue.get(order.getString("itemStatusId")).get("Rush").add(order.getString("orderId"));
								} else {
									offSetQueue.get(order.getString("itemStatusId")).get("Standard").add(order.getString("orderId"));
								}
							}
						}
					}
				}
			} catch (GenericEntityException e1) {
				TransactionUtil.rollback(beganTransaction, "Error looking up prepress order list addresses", e1);
				EnvUtil.reportError(e1);
			} finally {
				if(eli != null) {
					try {
						eli.close();
						TransactionUtil.commit(beganTransaction);
					} catch (GenericEntityException e2) {
						EnvUtil.reportError(e2);
					}
				}
			}
		} catch (GenericTransactionException e3) {
			EnvUtil.reportError(e3);
		}

		List<Map> queues = new ArrayList<>();
		queues.add(offSetQueue);
		queues.add(digitalQueue);
		queues.add(proofReady);
		queues.add(fullStatus);

		return queues;
	}

	/*
	 * Order and Item List view for prepress
	 */
	public static List<Map> prepressOrderAndItemList(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
		String[] orderIds = ((String) context.get("orderIds")).split("\\s*,\\s*");

		List<Map> orders = new ArrayList<>();

		DynamicViewEntity dve = new DynamicViewEntity();
		dve.addMemberEntity("OH", "OrderHeader");
		dve.addAlias("OH", "salesChannelEnumId");
		dve.addAlias("OH", "orderId");
		dve.addAlias("OH", "orderDate");
		dve.addAlias("OH", "createdBy");
		dve.addMemberEntity("OI", "OrderItem");
		dve.addAlias("OI", "statusId");
		dve.addAlias("OI", "orderItemSeqId");
		dve.addAlias("OI", "productId");
		dve.addAlias("OI", "itemDescription");
		dve.addAlias("OI", "dueDate");
		dve.addAlias("OI", "quantity");
		dve.addAlias("OI", "isRushProduction");
		dve.addAlias("OI", "artworkSource");
		dve.addAlias("OI", "outsource");
		dve.addAlias("OI", "outsourceable");
		dve.addAlias("OI", "pendingChange");
		dve.addMemberEntity("OIA", "OrderItemArtwork");
		dve.addAlias("OIA", "assignedToUserLogin");
		dve.addViewLink("OH", "OI", true, ModelKeyMap.makeKeyMapList("orderId", "orderId"));
		dve.addViewLink("OI", "OIA", true, ModelKeyMap.makeKeyMapList("orderId", "orderId", "orderItemSeqId", "orderItemSeqId"));
		dve.addMemberEntity("P", "Product");
		dve.addViewLink("OI", "P", false, ModelKeyMap.makeKeyMapList("productId", "productId"));
		dve.addMemberEntity("PFA", "ProductFeatureAppl");
		dve.addAlias("PFA", "productFeatureId");
		dve.addViewLink("P", "PFA", false, ModelKeyMap.makeKeyMapList("productId", "productId"));
		dve.addMemberEntity("PF", "ProductFeature");
		dve.addAlias("PF", "productFeatureTypeId");
		dve.addAlias("PF", "colorDescription", "description", null, null, null, null);
		dve.addViewLink("PFA", "PF", false, ModelKeyMap.makeKeyMapList("productFeatureId", "productFeatureId"));
		dve.addMemberEntity("VO", "VendorOrder");
		dve.addAlias("VO", "partyId");
		dve.addViewLink("OI", "VO", true, ModelKeyMap.makeKeyMapList("orderId", "orderId", "orderItemSeqId", "orderItemSeqId"));
		dve.addMemberEntity("OISG", "OrderItemShipGroup");
		dve.addAlias("OISG", "shipmentMethodTypeId");
		dve.addViewLink("OI", "OISG", true, ModelKeyMap.makeKeyMapList("orderId", "orderId"));
		dve.addMemberEntity("OISGA", "OrderItemShipGroupAssoc");
		dve.addAlias("OISGA", "trackingNumber");
		dve.addViewLink("OI", "OISGA", true, ModelKeyMap.makeKeyMapList("orderId", "orderId", "orderItemSeqId", "orderItemSeqId"));
		dve.addMemberEntity("OIF", "OrderItemFlag");
		dve.addAlias("OIF", "printableSyracuse");
		dve.addViewLink("OI", "OIF", true, ModelKeyMap.makeKeyMapList("orderId", "orderId", "orderItemSeqId", "orderItemSeqId"));

		List<EntityCondition> conditions = new ArrayList<>();
		conditions.add(EntityCondition.makeCondition("orderId", EntityOperator.IN, Arrays.asList(orderIds)));
		conditions.add(EntityCondition.makeCondition("productFeatureTypeId", EntityOperator.EQUALS, "COLOR"));

		String webSiteId = (String) context.get("webSiteId");
		if(UtilValidate.isNotEmpty(webSiteId)) {
			List<EntityCondition> siteCondition = new ArrayList<>();
			String[] webSiteIds = webSiteId.split(",");
			for(String webSite : webSiteIds) {
				siteCondition.add(EntityCondition.makeCondition("salesChannelEnumId", EntityOperator.EQUALS, EnvUtil.getSalesChannelEnumId(webSite)));
			}
			conditions.add(EntityCondition.makeCondition(siteCondition, EntityOperator.OR));
		}

		if(UtilValidate.isNotEmpty(context.get("statusId"))) {
			if("Variable".equalsIgnoreCase((String) context.get("statusId"))) {
				//TODO
			} else if("Scene7".equalsIgnoreCase((String) context.get("statusId"))) {
				//TODO
			} else if("Outsourceable".equalsIgnoreCase((String) context.get("statusId"))) {
				//TODO
			} else if("Syracuseable".equalsIgnoreCase((String) context.get("statusId"))) {
				//TODO
			} else if("Vendor".equalsIgnoreCase((String) context.get("statusId"))) {
				//TODO
			} else {
				conditions.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, (String) context.get("statusId")));
			}
		}

		EntityListIterator eli = null;
		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try {
				eli = delegator.findListIteratorByCondition(dve, EntityCondition.makeCondition(conditions, EntityOperator.AND), null, null, UtilMisc.toList("orderDate DESC"), null);
				GenericValue order = null;

				while((order = eli.next()) != null) {
					Map<String, String> itemAttr = getOrderItemAttributeMap(delegator, order.getString("orderId"), order.getString("orderItemSeqId"));
					boolean hasAddressing = (UtilValidate.isNotEmpty(itemAttr.get("addresses")) && !"0".equals(itemAttr.get("addresses")));
					String itemDescription = order.getString("itemDescription");
					String colorDescription = order.getString("colorDescription");
					order.put("itemDescription", UtilValidate.isNotEmpty(itemDescription) && !itemDescription.contains(colorDescription) ? itemDescription + " - " + colorDescription : itemDescription);
					Map<String, Object> orderGV = new HashMap<>();
					orderGV.putAll(order);
					orderGV.put("colorsFront", (String) itemAttr.get("colorsFront"));
					orderGV.put("colorsBack", (String) itemAttr.get("colorsBack"));
					orderGV.put("cimpressOrder", ("cimpress@bigname.com".equalsIgnoreCase(order.getString("createdBy"))) ? "Yes" : "No");

					GenericValue shippingAddress = getShippingAddress(null, delegator, order.getString("orderId"));
					orderGV.put("shippingState", (UtilValidate.isNotEmpty(shippingAddress)) ? shippingAddress.getString("stateProvinceGeoId") : null);

					List<GenericValue> orderItemContent = getOrderItemContent(delegator, order.getString("orderId"), order.getString("orderItemSeqId"), UtilMisc.toList("OIACPRP_WORKER_FRONT", "OIACPRP_WORKER_BACK", "OIACPRP_WORKER"));
					orderGV.put("workerDate", (UtilValidate.isNotEmpty(orderItemContent) ? orderItemContent.get(0).getTimestamp("createdStamp") : null));

					Timestamp approvalDate = OrderHelper.getStatusChangeTimeForOrderItem(delegator, order.getString("orderId"), order.getString("orderItemSeqId"), "ART_PROOF_APPROVED");
					if(approvalDate != null) {
						orderGV.put("approvalDate", approvalDate);
					}

					if("Variable".equalsIgnoreCase((String) context.get("statusId")) && hasAddressing) {
						orderGV.put("SCENE7", "YES");
						orderGV.put("VARIABLE", "YES");
					} else if("SCENE7_ART_ONLINE".equalsIgnoreCase(order.getString("artworkSource"))) {
						orderGV.put("SCENE7", "YES");
					}

					if("ART_PROOF_APPROVED".equalsIgnoreCase(order.getString("statusId"))) {
						List<GenericValue> totalItemsInOrder = delegator.findList("EnvOrderHeaderAndItems", EntityCondition.makeCondition(UtilMisc.toList(EntityCondition.makeCondition("orderId", EntityOperator.EQUALS, order.getString("orderId")), EntityCondition.makeCondition("artworkSource", EntityOperator.NOT_EQUAL, null)), EntityOperator.AND), null, null, null, false);
						List<GenericValue> totalPrintedItemsInOrder = delegator.findByAnd("EnvOrderHeaderAndItems", UtilMisc.toMap("orderId", order.getString("orderId"), "itemStatusId", "ART_PROOF_APPROVED"), null, false);
						orderGV.put("totalItemsInOrder", totalItemsInOrder.size());
						orderGV.put("totalProofApprovedItemsInOrder", totalPrintedItemsInOrder.size());
					}

					if("ART_PRINTED".equalsIgnoreCase(order.getString("statusId"))) {
						List<GenericValue> totalItemsInOrder = delegator.findList("EnvOrderHeaderAndItems", EntityCondition.makeCondition(UtilMisc.toList(EntityCondition.makeCondition("orderId", EntityOperator.EQUALS, order.getString("orderId")), EntityCondition.makeCondition("artworkSource", EntityOperator.NOT_EQUAL, null)), EntityOperator.AND), null, null, null, false);
						List<GenericValue> totalPrintedItemsInOrder = delegator.findByAnd("EnvOrderHeaderAndItems", UtilMisc.toMap("orderId", order.getString("orderId"), "itemStatusId", "ART_PRINTED"), null, false);
						orderGV.put("totalItemsInOrder", totalItemsInOrder.size());
						orderGV.put("totalPrintedItemsInOrder", totalPrintedItemsInOrder.size());
					}

					if("DIGITAL".equalsIgnoreCase((String) context.get("queue"))) { //digital queue
						if(hasAddressing || order.getBigDecimal("quantity").compareTo(new BigDecimal("500")) < 0) {
							if("Vendor".equals((String) context.get("statusId")) && UtilValidate.isNotEmpty(order.getString("partyId"))) {
								if ("Y".equalsIgnoreCase((String) context.get("isRush")) && "Y".equals(order.getString("isRushProduction"))) {
									orders.add(orderGV);
								} else {
									orders.add(orderGV);
								}
							}
							if("Outsourceable".equals((String) context.get("statusId"))) {
								if ("Y".equalsIgnoreCase((String) context.get("isRush")) && "Y".equals(order.getString("isRushProduction"))) {
									orders.add(orderGV);
								} else {
									orders.add(orderGV);
								}
							} else if("Syracuseable".equals((String) context.get("statusId"))) {
								if ("Y".equalsIgnoreCase((String) context.get("isRush")) && "Y".equals(order.getString("isRushProduction"))) {
									orders.add(orderGV);
								} else {
									orders.add(orderGV);
								}
							} else if (!"ART_OUTSOURCED".equals(order.getString("statusId")) && !"ITEM_BACKORDERED".equals(order.getString("statusId")) && !"ART_PROOF_REJECTED".equals(order.getString("statusId")) && "SCENE7_ART_ONLINE".equals(order.getString("artworkSource")) && UtilValidate.isEmpty(order.getString("assignedToUserLogin"))) {
								if ("Y".equalsIgnoreCase((String) context.get("isRush")) && "Y".equals(order.getString("isRushProduction"))) {
									if (hasAddressing && "Variable".equalsIgnoreCase((String) context.get("statusId"))) {
										orders.add(orderGV);
									} else if ("Scene7".equalsIgnoreCase((String) context.get("statusId"))) {
										orders.add(orderGV);
									}
								} else {
									if (hasAddressing && "Variable".equalsIgnoreCase((String) context.get("statusId"))) {
										orders.add(orderGV);
									} else if ("Scene7".equalsIgnoreCase((String) context.get("statusId"))) {
										orders.add(orderGV);
									}
								}
							} else {
								if ("Y".equalsIgnoreCase((String) context.get("isRush")) && "Y".equals(order.getString("isRushProduction"))) {
									if (order.getString("statusId").equalsIgnoreCase((String) context.get("statusId"))) {
										orders.add(orderGV);
									}
								} else {
									if (order.getString("statusId").equalsIgnoreCase((String) context.get("statusId"))) {
										orders.add(orderGV);
									}
								}
							}
						}
					} else if("OFFSET".equalsIgnoreCase((String) context.get("queue"))) { //offset queue
						if(order.getBigDecimal("quantity").compareTo(new BigDecimal("500")) >= 0 || (UtilValidate.isNotEmpty(webSiteId) && webSiteId.contains("folders"))) {
							if("Vendor".equals((String) context.get("statusId")) && UtilValidate.isNotEmpty(order.getString("partyId"))) {
								if ("Y".equalsIgnoreCase((String) context.get("isRush")) && "Y".equals(order.getString("isRushProduction"))) {
									orders.add(orderGV);
								} else {
									orders.add(orderGV);
								}
							}
							if("Outsourceable".equals((String) context.get("statusId"))) {
								if ("Y".equalsIgnoreCase((String) context.get("isRush")) && "Y".equals(order.getString("isRushProduction"))) {
									orders.add(orderGV);
								} else {
									orders.add(orderGV);
								}
							} else if("Syracuseable".equals((String) context.get("statusId"))) {
								if ("Y".equalsIgnoreCase((String) context.get("isRush")) && "Y".equals(order.getString("isRushProduction"))) {
									orders.add(orderGV);
								} else {
									orders.add(orderGV);
								}
							} else if(!"ART_OUTSOURCED".equals(order.getString("statusId")) && !"ITEM_BACKORDERED".equals(order.getString("statusId")) && !"ART_PROOF_REJECTED".equals(order.getString("statusId")) && "SCENE7_ART_ONLINE".equals(order.getString("artworkSource")) && UtilValidate.isEmpty(order.getString("assignedToUserLogin"))) {
								if("Y".equalsIgnoreCase((String) context.get("isRush")) && "Y".equals(order.getString("isRushProduction"))) {
									if("Scene7".equalsIgnoreCase((String) context.get("statusId"))) {
										orders.add(orderGV);
									}
								} else {
									if("Scene7".equalsIgnoreCase((String) context.get("statusId"))) {
										orders.add(orderGV);
									}
								}
							} else {
								if("Y".equalsIgnoreCase((String) context.get("isRush")) && "Y".equals(order.getString("isRushProduction"))) {
									if(order.getString("statusId").equalsIgnoreCase((String) context.get("statusId"))) {
										orders.add(orderGV);
									}
								} else {
									if(order.getString("statusId").equalsIgnoreCase((String) context.get("statusId"))) {
										orders.add(orderGV);
									}
								}
							}
						}
					} else {
						orders.add(orderGV);
					}
				}
			} catch (GenericEntityException e1) {
				TransactionUtil.rollback(beganTransaction, "Error looking up prepress order list addresses", e1);
				EnvUtil.reportError(e1);
			} finally {
				if(eli != null) {
					try {
						eli.close();
						TransactionUtil.commit(beganTransaction);
					} catch (GenericEntityException e2) {
						EnvUtil.reportError(e2);
					}
				}
			}
		} catch (GenericTransactionException e3) {
			EnvUtil.reportError(e3);
		}

		return orders;
	}

	/*
	 * Create order level queues
	 */
	public static Map<String, List> orderListQueue(LocalDispatcher dispatcher, Delegator delegator) throws GenericEntityException {
		Map<String, List> queues = new HashMap<>();

		List<GenericValue> ordersToExport = null;
		List<GenericValue> declinedOrders = null;
		List<GenericValue> nonCapturedOrders = null;
		List<GenericValue> checkOrders = null;
		List<GenericValue> pendingOrders = null;
		List<GenericValue> internationalOrders = null;

		List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
		EntityCondition conditions = null;

		List<String> notReadyForExport = new ArrayList<>();

		//get declined orders
		conditionList.clear();
		conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "ORDER_PENDING"));
		//conditionList.add(EntityCondition.makeCondition("exportedDate", EntityOperator.EQUALS, null));
		conditionList.add(EntityCondition.makeCondition("paymentMethodTypeId", EntityOperator.EQUALS, "CREDIT_CARD"));
		conditions = EntityCondition.makeCondition(conditionList, EntityOperator.AND);
		declinedOrders = delegator.findList("OrderAndPaymentPreference", conditions, null, null, null, false);
		List<String> declinedList = EntityUtil.getFieldListFromEntityList(declinedOrders, "orderId", true);
		queues.put("declinedOrders", declinedList);
		notReadyForExport.addAll(declinedList);

		//get failed-capture orders
		conditionList.clear();
		conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.NOT_IN, UtilMisc.toList("ORDER_CANCELLED", "ORDER_SHIPPED")));
		//conditionList.add(EntityCondition.makeCondition("exportedDate", EntityOperator.EQUALS, null));
		conditionList.add(EntityCondition.makeCondition("paymentMethodTypeId", EntityOperator.IN, UtilMisc.toList("EXT_AMAZON")));
		conditionList.add(EntityCondition.makeCondition("paymentStatusId", EntityOperator.NOT_IN, UtilMisc.toList("PAYMENT_SETTLED", "PAYMENT_REFUNDED")));
		conditions = EntityCondition.makeCondition(conditionList, EntityOperator.AND);
		nonCapturedOrders = delegator.findList("OrderAndPaymentPreference", conditions, null, null, null, false);
		List<String> nonCapturedList = EntityUtil.getFieldListFromEntityList(nonCapturedOrders, "orderId", true);
		queues.put("nonCapturedOrders", nonCapturedList);
		notReadyForExport.addAll(nonCapturedList);

		//get check orders
		conditionList.clear();
		conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "ORDER_PENDING"));
		//conditionList.add(EntityCondition.makeCondition("exportedDate", EntityOperator.EQUALS, null));
		conditionList.add(EntityCondition.makeCondition("paymentMethodTypeId", EntityOperator.EQUALS, "PERSONAL_CHECK"));
		conditions = EntityCondition.makeCondition(conditionList, EntityOperator.AND);
		checkOrders = delegator.findList("OrderAndPaymentPreference", conditions, null, null, null, false);
		List<String> checkList = EntityUtil.getFieldListFromEntityList(checkOrders, "orderId", true);
		queues.put("checkOrders", checkList);
		notReadyForExport.addAll(checkList);

		//get declined orders
		conditionList.clear();
		conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "ORDER_PENDING"));
		conditions = EntityCondition.makeCondition(conditionList, EntityOperator.AND);
		pendingOrders = delegator.findList("OrderHeader", conditions, null, null, null, false);
		List<String> pendingList = EntityUtil.getFieldListFromEntityList(pendingOrders, "orderId", true);
		queues.put("pendingOrders", pendingList);
		notReadyForExport.addAll(pendingList);

		//get orders waiting to be exported queue
		conditionList.clear();
		conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "ORDER_CREATED"));
		conditionList.add(EntityCondition.makeCondition("exportedDate", EntityOperator.EQUALS, null));
		conditions = EntityCondition.makeCondition(conditionList, EntityOperator.AND);
		ordersToExport = delegator.findList("OrderHeader", conditions, null, null, null, false);
		List<String> exportList = EntityUtil.getFieldListFromEntityList(ordersToExport, "orderId", true);
		exportList.removeAll(notReadyForExport);
		queues.put("ordersToExport", exportList);

		//get international orders
		conditionList.clear();
		conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "ORDER_PENDING"));
		conditionList.add(EntityCondition.makeCondition("exportedDate", EntityOperator.EQUALS, null));
		conditionList.add(EntityCondition.makeCondition("shipmentMethodTypeId", EntityOperator.EQUALS, "INTRNL_STD"));
		conditions = EntityCondition.makeCondition(conditionList, EntityOperator.AND);
		internationalOrders = delegator.findList("OrderAndAdjustmentAndShipGroup", conditions, null, null, null, false);
		List<String> internationalList = EntityUtil.getFieldListFromEntityList(internationalOrders, "orderId", true);
		queues.put("internationalOrders", internationalList);

		return queues;
	}

	/*
	 * Create new Payment
	 */
	public static GenericValue createPaymentMethod(Delegator delegator, String paymentMethodTypeId, String partyId) throws GenericEntityException {
		GenericValue paymentMethod = delegator.makeValue("PaymentMethod", UtilMisc.toMap("paymentMethodId", delegator.getNextSeqId("PaymentMethod")));
		paymentMethod.set("partyId", partyId);
		paymentMethod.set("paymentMethodTypeId", paymentMethodTypeId);
		paymentMethod.set("description", paymentMethodTypeId);
		paymentMethod.set("fromDate", UtilDateTime.nowTimestamp());
		delegator.create(paymentMethod);

		return paymentMethod;
	}

	/*
	 * Create new Credit Card
	 */
	public static GenericValue createCreditCard(Delegator delegator, Map<String, String> data) throws GenericEntityException {
		GenericValue creditCard = delegator.makeValue("CreditCard", data);

		String cardType = "CCT_VISA";
		if(data.get("cardNumber").matches("^4[0-9]{12}(?:[0-9]{3})?$")) {
			cardType = "CCT_VISA";
		} else if(data.get("cardNumber").matches("^5[1-5][0-9]{14}")) {
			cardType = "CCT_MASTERCARD";
		} else if(data.get("cardNumber").matches("^3[47][0-9]{13}$")) {
			cardType = "CCT_AMERICANEXPRESS";
		} else if(data.get("cardNumber").matches("^6(?:011|5[0-9]{2})[0-9]{12}$")) {
			cardType = "CCT_DISCOVER";
		}
		creditCard.set("cardType", cardType);
		delegator.create(creditCard);

		return creditCard;
	}

	/*
	 * Create new Credit Card
	 */
	public static GenericValue createOrderPaymentPreference(Delegator delegator, Map<String, Object> data) throws GenericEntityException {
		GenericValue oPP = delegator.makeValue("OrderPaymentPreference", UtilMisc.toMap("orderPaymentPreferenceId", delegator.getNextSeqId("OrderPaymentPreference")));
		Iterator iter = data.entrySet().iterator();

		while(iter.hasNext()) {
			Map.Entry pairs = (Map.Entry) iter.next();
			oPP.set((String) pairs.getKey(), pairs.getValue());
		}
		delegator.create(oPP);

		return oPP;
	}

	/*
	 * Get the latest timestamp for a given status
	 */
	public static Timestamp getStatusChangeTimeForOrderItem(Delegator delegator, String orderId, String orderItemSeqId, String statusId) throws GenericEntityException {
		GenericValue statusItem = EntityUtil.getFirst(delegator.findByAnd("OrderStatus", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId, "statusId", statusId), UtilMisc.toList("createdStamp DESC"), false));
		if(statusItem != null) {
			return statusItem.getTimestamp("statusDatetime");
		}

		return null;
	}

	/*
	 * Get the latest timestamp for a given status
	 */
	public static GenericValue updateOrderStatusTime(Delegator delegator, String orderId, String orderItemSeqId, String statusId, Timestamp timestamp) throws GenericEntityException {
		GenericValue statusItem = EntityUtil.getFirst(delegator.findByAnd("OrderStatus", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId, "statusId", statusId), UtilMisc.toList("createdStamp DESC"), false));
		if(statusItem != null) {
			statusItem.put("statusDatetime", timestamp);
			statusItem.store();
		}

		return statusItem;
	}

	/*
	 * Check if the order is an amazon order
	 */
	public static boolean isAmazonOrder(OrderReadHelper orh, Delegator delegator, String orderId) throws GenericEntityException {
		if(orh == null) {
			orh = new OrderReadHelper(delegator, orderId);
		}

		List<GenericValue> paymentPreferences = orh.getPaymentPreferences();
		if(UtilValidate.isNotEmpty(paymentPreferences)) {
			for(GenericValue paymentPreference : paymentPreferences) {
				if("EXT_AMAZON".equalsIgnoreCase(paymentPreference.getString("paymentMethodTypeId"))) {
					return true;
				}
			}
		}

		return false;
	}

	/*
	 * Check if the order is an paypal order
	 */
	public static boolean isPayPalOrder(OrderReadHelper orh, Delegator delegator, String orderId) throws GenericEntityException {
		if(orh == null) {
			orh = new OrderReadHelper(delegator, orderId);
		}

		List<GenericValue> paymentPreferences = orh.getPaymentPreferences();
		if(UtilValidate.isNotEmpty(paymentPreferences)) {
			for(GenericValue paymentPreference : paymentPreferences) {
				if("EXT_PAYPAL_CHECKOUT".equalsIgnoreCase(paymentPreference.getString("paymentMethodTypeId"))) {
					return true;
				}
			}
		}

		return false;
	}

	/*
	 * Check if the order is an creditcard order
	 */
	public static boolean isCreditCardOrder(OrderReadHelper orh, Delegator delegator, String orderId) throws GenericEntityException {
		if(orh == null) {
			orh = new OrderReadHelper(delegator, orderId);
		}

		List<GenericValue> paymentPreferences = orh.getPaymentPreferences();
		if(UtilValidate.isNotEmpty(paymentPreferences)) {
			for(GenericValue paymentPreference : paymentPreferences) {
				if("CREDIT_CARD".equalsIgnoreCase(paymentPreference.getString("paymentMethodTypeId"))) {
					return true;
				}
			}
		}

		return false;
	}

	/*
	 * Check if order contains only envelopes
	 */
	public static boolean isOrderEnvelopeOnly(Delegator delegator, String orderId) throws GenericEntityException {
		List<GenericValue> orderItems = delegator.findByAnd("OrderItem", UtilMisc.toMap("orderId", orderId), null, false);
		if(UtilValidate.isNotEmpty(orderItems)) {
			for(GenericValue orderItem : orderItems) {
				GenericValue product = delegator.findOne("Product", UtilMisc.toMap("productId", orderItem.getString("productId")), true);
				if(!"ENVELOPE".equalsIgnoreCase(product.getString("productTypeId"))) {
					return false;
				}
			}
		}

		return true;
	}

	/*
	 * Check if order is set for pickup
	 */
	public static boolean isOrderPickUp(OrderReadHelper orh, Delegator delegator, String orderId) throws GenericEntityException {
		if(orh == null) {
			orh = new OrderReadHelper(delegator, orderId);
		}

		return "PICKUP".equals(orh.getShippingMethodType("00001"));
	}

	/*
	 * Check if order is set for pickup
	 */
	public static boolean isInternationalOrder(OrderReadHelper orh, Delegator delegator, String orderId) throws GenericEntityException {
		if(orh == null) {
			orh = new OrderReadHelper(delegator, orderId);
		}

		return "INTRNL_STD".equals(orh.getShippingMethodType("00001"));
	}
	public static boolean isInternationalOrder(String shipmentMethodTypeId) {
		return "INTRNL_STD".equalsIgnoreCase(shipmentMethodTypeId);
	}

	/*
	 * Check if the order item is outsourceable
	 */
	public static boolean isOutsourceable(Delegator delegator, String orderId, String orderItemSeqId, GenericValue orderItem) throws GenericEntityException {
		if(orderItem == null) {
			orderItem = delegator.findOne("OrderItem", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), false);
		}

		if(orderItem != null && "Y".equalsIgnoreCase(orderItem.getString("outsourceable"))) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Check if the order is outsourceable based on the orderitem value
	 */
	public static boolean isOutsourceable(OrderReadHelper orh, Delegator delegator, String orderId) throws GenericEntityException {
		if(orh == null) {
			orh = new OrderReadHelper(delegator, orderId);
		}

		List<GenericValue> orderItems = orh.getOrderItems();
		for(GenericValue orderItem : orderItems) {
			if("Y".equalsIgnoreCase(orderItem.getString("outsourceable"))) {
				return true;
			}
		}

		return false;
	}

	/*
	 * Check if the order is outsourced
	 * Outsource is at the line item level, but currently prepress treats it at the order level
	 */
	public static String isOutsourced(OrderReadHelper orh, Delegator delegator, String orderId) throws GenericEntityException {
		if (orh == null) {
			orh = new OrderReadHelper(delegator, orderId);
		}

		List<GenericValue> orderItems = orh.getOrderItems();
		for (GenericValue orderItem : orderItems) {
			if (UtilValidate.isNotEmpty(orderItem.getString("outsource"))) {
				return orderItem.getString("outsource");
			}
		}

		return null;
	}

	/*
	 * Determine if outsourcable based on auto outsource list
	 */
	public static boolean canOutsource(OrderReadHelper orh, Delegator delegator, String orderId, String orderItemSeqId) throws GenericEntityException {
		if (orh == null) {
			orh = new OrderReadHelper(delegator, orderId);
		}

		List<GenericValue> orderItems = orh.getOrderItems();
		for (GenericValue orderItem : orderItems) {
			if(UtilValidate.isNotEmpty(orderItemSeqId) && !orderItemSeqId.equalsIgnoreCase(orderItem.getString("orderItemSeqId"))) {
				continue;
			}

			Map<String, String> itemAttr = getOrderItemAttributeMap(delegator, orderId, orderItemSeqId);
			boolean hasAddressing = (UtilValidate.isNotEmpty(itemAttr.get("addresses")) && !"0".equals(itemAttr.get("addresses")));
			if(hasAddressing) {
				return false;
			}

			GenericValue shipAddress = getShippingAddress(orh, delegator, orderId);
			if(!"USA".equalsIgnoreCase(shipAddress.getString("countryGeoId")) && !"CAN".equalsIgnoreCase(shipAddress.getString("countryGeoId"))) {
				return false;
			}

			Map<String, Integer> colors = getColors(delegator, null, null, orderItem);
			int colorsFront = colors.get("colorsFront");
			int colorsBack = colors.get("colorsBack");
			int mostColors = ProductHelper.getLeastOrMostColors(true, colorsFront, colorsBack);

			if(mostColors == 4 && orderItem.getBigDecimal("quantity").compareTo(new BigDecimal("500")) >= 0) {
				return true;
			}

			GenericValue prodOutsource = delegator.findOne("ProductOutsource", UtilMisc.toMap("productId", orderItem.getString("productId")), true);
			if (UtilValidate.isNotEmpty(prodOutsource)) {
				if(UtilValidate.isEmpty(prodOutsource.get("quantity")) || UtilValidate.isEmpty(prodOutsource.get("colors"))) {
					continue;
				}
				if("Y".equalsIgnoreCase(prodOutsource.getString("outsourceable")) && orderItem.getBigDecimal("quantity").intValue() >= prodOutsource.getLong("quantity").intValue() && mostColors >= prodOutsource.getLong("colors").intValue()) {
					return true;
				}
			}
		}

		return false;
	}

	public static Map<String, Integer> getColors(Delegator delegator, String orderId, String orderItemSeqId, GenericValue orderItem) throws GenericEntityException {
		if(UtilValidate.isEmpty(orderItem) && UtilValidate.isNotEmpty(orderId) && UtilValidate.isNotEmpty(orderItemSeqId)) {
			orderItem = delegator.findOne("OrderItem", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), false);
		}

		Map<String, Integer> colors = new HashMap<>();
		int colorsFront = 0;
		int colorsBack = 0;

		Map<String, String> attribute = getOrderItemAttributeMap(delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId"));

		if (attribute.containsKey("colorsFront")) {
			colorsFront = NumberUtils.toInt(attribute.get("colorsFront"), 0);
		}
		if (attribute.containsKey("colorsBack")) {
			colorsBack = NumberUtils.toInt(attribute.get("colorsBack"), 0);
		}

		colors.put("colorsFront", colorsFront);
		colors.put("colorsBack", colorsBack);

		return colors;
	}

	/*
	 * Set order item as outsourceable
	 */
	public static void setOutsourceable(Delegator delegator, List<GenericValue> orderItems, GenericValue orderItem, String orderId, String orderItemSeqId, String outsource) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(orderItem)) {
			OrderHelper.updateOrderItem(delegator, orderItem, UtilMisc.toMap("outsourceable", outsource));
		} else if (UtilValidate.isNotEmpty(orderId) && UtilValidate.isNotEmpty(orderItemSeqId)) {
			orderItem = delegator.findOne("OrderItem", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), false);
			OrderHelper.updateOrderItem(delegator, orderItem, UtilMisc.toMap("outsourceable", outsource));
		} else if(UtilValidate.isNotEmpty(orderId) || UtilValidate.isNotEmpty(orderItems)) {
			if(UtilValidate.isEmpty(orderItems) && UtilValidate.isNotEmpty(orderId)) {
				orderItems = delegator.findByAnd("OrderItem", UtilMisc.toMap("orderId", orderId), null, false);
			}
			for(GenericValue _orderItem: orderItems) {
				if(OrderHelper.isItemPrinted(delegator, null, null, _orderItem)) {
					OrderHelper.updateOrderItem(delegator, _orderItem, UtilMisc.toMap("outsourceable", outsource));
				}
			}
		}
	}

	/*
	 * Check if the order is pending netsuite update
	 * Outsource is at the line item level, but currently prepress treats it at the order level
	 */
	public static boolean isPendingChange(OrderReadHelper orh, Delegator delegator, String orderId) throws GenericEntityException {
		if(orh == null) {
			orh = new OrderReadHelper(delegator, orderId);
		}

		List<GenericValue> orderItems = orh.getOrderItems();
		for(GenericValue orderItem : orderItems) {
			if("Y".equalsIgnoreCase(orderItem.getString("pendingChange"))) {
				return true;
			}
		}

		return false;
	}


	/*
	 * Check if the order is syracuseable
	 */
	public static boolean isSyracuseable(Delegator delegator, String orderId) throws GenericEntityException {
		List<GenericValue> orderItems = EntityQuery.use(delegator).from("OrderItemFlag").where("orderId", orderId).queryList();
		for(GenericValue orderItem : orderItems) {
			if("Y".equalsIgnoreCase(orderItem.getString("printableSyracuse"))) {
				return true;
			}
		}

		return false;
	}

	/*
	 * Set picked status for order item artwork
	 */
	public static GenericValue updateStockPickStatus(Delegator delegator, String orderId, String orderItemSeqId, boolean picked) throws GenericEntityException {
		return updateOrderItem(delegator, orderId, orderItemSeqId, UtilMisc.toMap("stockPicked", picked ? "Y" : null));
	}

	/*
	 * Set picked status for order item ink
	 */
	public static GenericValue updateInkPickStatus(Delegator delegator, String orderId, String orderItemSeqId, boolean picked) throws GenericEntityException {
		return updateOrderItemArtwork(delegator, orderId, orderItemSeqId, UtilMisc.toMap("inkPicked", picked ? "Y" : null));
	}

	public static int getNumberOfAddresses(List<GenericValue> attributes) throws GenericEntityException {
		for(GenericValue attr : attributes) {
			if("addresses".equalsIgnoreCase(attr.getString("attrName")) && UtilValidate.isNotEmpty(attr.getString("attrValue"))) {
				return Integer.valueOf(attr.getString("attrValue"));
			}
		}
		return 0;
	}

	public static int getNumberOfAddresses(Delegator delegator, String orderId, String orderItemSeqId) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(orderId) && UtilValidate.isNotEmpty(orderItemSeqId)) {
			Map<String, String> attributes = getOrderItemAttributeMap(delegator, orderId, orderItemSeqId);
			Iterator attrIter = attributes.keySet().iterator();
			while(attrIter.hasNext()) {
				String key = (String) attrIter.next();
				if("addresses".equalsIgnoreCase(key)) {
					return Integer.valueOf(attributes.get(key));
				}
			}
		}

		return 0;
	}

	protected static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	public static Map<String, Object> getPrintJobAttributes(Delegator delegator, String jobId) throws GenericEntityException, SQLException {
		Map<String, Object> attributes = new LinkedHashMap<>();
		String[] idTokens = jobId.split("-");
		SQLProcessor sqlProcessor = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));

		String sqlCommand1 =
				"SELECT  " +
						"	 oi.order_id, " +
						"	 oi.order_item_seq_id, " +
						"	 oi.status_id, " +
						"    oi.product_id, " +
						"    cw.virtual_product_id as parent_product_id, " +
						"    oia.order_item_artwork_id, " +
						"    oi.artwork_source, " +
						"    oia.front_ink_color1, " +
						"    oia.front_ink_color2, " +
						"    oia.front_ink_color3, " +
						"    oia.front_ink_color4, " +
						"    oia.back_ink_color1, " +
						"    oia.back_ink_color2, " +
						"    oia.back_ink_color3, " +
						"    oia.back_ink_color4, " +
						"    oiat.attr_value AS num_of_addresses, " +
						"    oi.is_rush_production, " +
						"    oi.due_date, " +
						"    oi.quantity, " +
						"    cw.product_width, " +
						"    cw.product_height " +
						"FROM " +
						"    order_item oi " +
						"        INNER JOIN " +
						"    order_item_artwork oia ON oi.order_id = oia.order_id " +
						"        AND oi.order_item_seq_id = oia.order_item_seq_id " +
						"        INNER JOIN " +
						"    color_warehouse cw ON cw.variant_product_id = oi.product_id " +
						"        LEFT OUTER JOIN " +
						"    order_item_attribute oiat ON oiat.order_id = oi.order_id " +
						"        AND oiat.order_item_seq_id = oi.order_item_seq_id " +
						"        AND oiat.attr_name = 'addresses' " +
						"WHERE " +
						"    oi.order_id = '" + idTokens[0] + "' and oi.order_item_seq_id = '" + idTokens[1] + "'";

		try {
			ResultSet rs = sqlProcessor.executeQuery(sqlCommand1);
			if (rs != null) {
				if(rs.next()) {
					String stringVal;
					int intVal;
					long longVal;
					Timestamp dateVal;
					double doubleVal;
					attributes.put("ORDER_ID", 				UtilValidate.isNotEmpty(stringVal = rs.getString   ("order_id")) 			    ? stringVal : "");
					attributes.put("ORDER_ITEM_SEQ_ID",		UtilValidate.isNotEmpty(stringVal = rs.getString   ("order_item_seq_id")) 	    ? stringVal : "");
					attributes.put("STATUS_ID", 	 		UtilValidate.isNotEmpty(stringVal = rs.getString   ("status_id")) 			    ? stringVal : "");
					attributes.put("PRODUCT_ID", 	 		UtilValidate.isNotEmpty(stringVal = rs.getString   ("product_id")) 			    ? stringVal : "");
					attributes.put("PARENT_PRODUCT_ID", 	UtilValidate.isNotEmpty(stringVal = rs.getString   ("parent_product_id")) 		? stringVal : "");
					attributes.put("ARTWORK_ID",		 	UtilValidate.isNotEmpty(stringVal = rs.getString   ("order_item_artwork_id")) 	? stringVal : "");
					attributes.put("ARTWORK_SOURCE",		UtilValidate.isNotEmpty(stringVal = rs.getString   ("artwork_source")) 		    ? stringVal : "");
					attributes.put("FRONT_COLOR1",		 	UtilValidate.isNotEmpty(stringVal = rs.getString   ("front_ink_color1")) 		? stringVal : "");
					attributes.put("FRONT_COLOR2",		 	UtilValidate.isNotEmpty(stringVal = rs.getString   ("front_ink_color2")) 		? stringVal : "");
					attributes.put("FRONT_COLOR3",		 	UtilValidate.isNotEmpty(stringVal = rs.getString   ("front_ink_color3")) 		? stringVal : "");
					attributes.put("FRONT_COLOR4",		 	UtilValidate.isNotEmpty(stringVal = rs.getString   ("front_ink_color4")) 		? stringVal : "");
					attributes.put("BACK_COLOR1",		 	UtilValidate.isNotEmpty(stringVal = rs.getString   ("back_ink_color1")) 		? stringVal : "");
					attributes.put("BACK_COLOR2",		 	UtilValidate.isNotEmpty(stringVal = rs.getString   ("back_ink_color2")) 		? stringVal : "");
					attributes.put("BACK_COLOR3",		 	UtilValidate.isNotEmpty(stringVal = rs.getString   ("back_ink_color3")) 		? stringVal : "");
					attributes.put("BACK_COLOR4",		 	UtilValidate.isNotEmpty(stringVal = rs.getString   ("back_ink_color4")) 		? stringVal : "");
					attributes.put("NUM_OF_ADDRESSES",	 	UtilValidate.isNotEmpty(intVal    = rs.getInt      ("num_of_addresses")) 		? intVal    : 0);
					attributes.put("ITEM_DUE_DATE",			UtilValidate.isNotEmpty(dateVal   = rs.getTimestamp("due_date")) 				? sdf.format(dateVal)   : null);
					attributes.put("ITEM_QTY",	 			UtilValidate.isNotEmpty(longVal   = rs.getLong      ("quantity")) 				? longVal    : 0);
					attributes.put("ITEM_WIDTH",	 		UtilValidate.isNotEmpty(doubleVal = rs.getDouble   ("product_width")) 			? doubleVal : 0.0);
					attributes.put("ITEM_HEIGHT",	 		UtilValidate.isNotEmpty(doubleVal = rs.getDouble   ("product_height")) 		    ? doubleVal : 0.0);
					attributes.put("RUSH_ITEM",	 			UtilValidate.isNotEmpty(stringVal = rs.getString   ("is_rush_production")) && stringVal.equalsIgnoreCase("Y"));
				}
			}
		} finally {
			if(sqlProcessor != null) {
                sqlProcessor.close();
            }
		}
		return attributes;
	}

	/**
	 * Get print job status
	 * @param delegator
	 * @param jobIds
	 * @return
	 * @throws GenericEntityException
	 */
	public static Map<String, String> getPrintJobStatus(Delegator delegator, String[] jobIds) throws GenericEntityException {
		Map<String, String> jobStatus = new LinkedHashMap<>();
		List<EntityCondition> conditionList = new ArrayList<>();

		for (String jobId : jobIds) {
			List<EntityCondition> subConditionList = new ArrayList<>();
			String[] idTokens = jobId.split("-");
			subConditionList.add(EntityCondition.makeCondition("orderId", EntityOperator.EQUALS, idTokens[0]));
			subConditionList.add(EntityCondition.makeCondition("orderItemSeqId", EntityOperator.EQUALS, idTokens[1]));
			conditionList.add(EntityCondition.makeCondition(subConditionList, EntityOperator.AND));
		}

		List<GenericValue> jobStatusList = delegator.findList("OrderItem", EntityCondition.makeCondition(conditionList, EntityOperator.OR), UtilMisc.toSet("orderId", "orderItemSeqId", "statusId"), null, null, false);
		for (GenericValue genericValue : jobStatusList) {
			jobStatus.put(genericValue.get("orderId") + "-" + genericValue.get("orderItemSeqId"), (String)genericValue.get("statusId"));
		}

		return jobStatus;
	}

	/**
	 * Get all order headers for a given party id
	 * @param delegator
	 * @param partyId
	 * @return
	 * @throws GenericEntityException
	 */
	public static List<GenericValue> getOrderHeadersForParty(Delegator delegator, String partyId) throws GenericEntityException {
		List<String> orderIds = getOrderIdsForParty(delegator, partyId);
		if(UtilValidate.isNotEmpty(orderIds)) {
			return EntityQuery.use(delegator).from("OrderHeader").where(EntityCondition.makeCondition("orderId", EntityOperator.IN, orderIds)).orderBy("orderDate DESC").queryList();
		}

		return new ArrayList<>();
	}

	/**
	 * Get all order ids for a given party id
	 * @param delegator
	 * @param partyId
	 * @return
	 * @throws GenericEntityException
	 */
	public static List<String> getOrderIdsForParty(Delegator delegator, String partyId) throws GenericEntityException {
		List<GenericValue> orderRoles = EntityQuery.use(delegator).from("OrderRole").where("partyId", partyId, "roleTypeId", "PLACING_CUSTOMER").queryList();
		if(UtilValidate.isNotEmpty(orderRoles)) {
			return EntityUtil.getFieldListFromEntityList(orderRoles, "orderId", true);
		}

		return new ArrayList<>();
	}

	/**
	 * Update order tracking data into database
	 * Return a map of order ids and a list of corresponding order item seq ids
	 * @param delegator
	 * @param data
	 * @throws GenericEntityException
	 * @return Map
	 */
	public static Map<String, List<String>> updateOrderTracking(Delegator delegator, Map<String, Map<String, String>> data) throws GenericEntityException {
		Map<String, List<String>> updatedTracking = new HashMap<>();
		if(UtilValidate.isNotEmpty(data)) {
			Iterator dataIter = data.entrySet().iterator();
			while(dataIter.hasNext()) {
				Map.Entry pairs = (Map.Entry) dataIter.next();
				String orderId = (String) pairs.getKey();

				//if empty orderId, skip
				if(UtilValidate.isEmpty(orderId)) {
					continue;
				}

				GenericValue oISG = EntityQuery.use(delegator).from("OrderItemShipGroup").where("orderId", (String) pairs.getKey(), "shipGroupSeqId", "00001").queryFirst();
				if(oISG != null) {
					Map<String, String> trackingData = (Map<String, String>) pairs.getValue();
					Iterator trackingDataIter = trackingData.entrySet().iterator();
					while (trackingDataIter.hasNext()) {
						Map.Entry sequencePairs = (Map.Entry) trackingDataIter.next();
						String[] key = ((String) sequencePairs.getKey()).split("\\|");
						String orderItemSeqId = key[0];
						String productId = key[1];
						String trackingNumber = (String) sequencePairs.getValue();

						//if item is already shipped, skip
						GenericValue orderItem = EntityQuery.use(delegator).from("OrderItem").where("orderId", orderId, "orderItemSeqId", orderItemSeqId).queryOne();
						if(orderItem != null && "ITEM_SHIPPED".equalsIgnoreCase(orderItem.getString("statusId"))) {
							continue;
						}

						//if item already has tracking, skip
						//used for legacy system where order was marked shipped even if a single item was shipped
						//because all tracking from oISG was transferred to oISGA
						GenericValue oISGA = EntityQuery.use(delegator).from("OrderItemShipGroupAssoc").where("orderId", orderId, "orderItemSeqId", orderItemSeqId, "shipGroupSeqId", "00001").queryOne();
						if(oISGA != null && UtilValidate.isNotEmpty(oISGA.getString("trackingNumber"))) {
							continue;
						}

						if (oISGA != null) {
							try {
								//if a ship group assoc is found, enter tracking to individual item
								oISGA.set("trackingNumber", (OrderHelper.isInternationalOrder(oISG.getString("shipmentMethodTypeId"))) ? EntityQuery.use(delegator).from("OrderHeader").where("orderId", orderId).queryOne().getString("externalId") : trackingNumber);
								oISGA.store();

								if (updatedTracking.containsKey(orderId)) {
									List<String> tempData = updatedTracking.get(orderId);
									if (!tempData.contains(orderItemSeqId)) {
										tempData.add(orderItemSeqId);
										updatedTracking.put(orderId, tempData);
									}
								} else {
									updatedTracking.put(orderId, UtilMisc.<String>toList(orderItemSeqId));
								}
							} catch (Exception eI) {
								EnvUtil.reportError(eI);
								Debug.logError(eI, "Error trying to update tracking when oISGA is available for: " + orderId + " : " + orderItemSeqId, module);
							}
						} else {
							try {
								//if a ship group assoc is not found, enter tracking by checking the product id match
								List<GenericValue> allShipGroupAssocs = EntityQuery.use(delegator).from("OrderItemShipGroupAssoc").where("orderId", orderId, "trackingNumber", null).queryList();
								for (GenericValue shipGroupAssoc : allShipGroupAssocs) {
									GenericValue orderItemLU = EntityQuery.use(delegator).from("OrderItem").where("orderId", orderId, "orderItemSeqId", shipGroupAssoc.getString("orderItemSeqId")).queryOne();
									if (orderItemLU != null && productId.equalsIgnoreCase(orderItemLU.getString("productId"))) {
										shipGroupAssoc.set("trackingNumber", (OrderHelper.isInternationalOrder(oISG.getString("shipmentMethodTypeId"))) ? EntityQuery.use(delegator).from("OrderHeader").where("orderId", orderId).queryOne().getString("externalId") : trackingNumber);
										shipGroupAssoc.store();

										if (updatedTracking.containsKey(orderId)) {
											List<String> tempData = updatedTracking.get(orderId);
											if (!tempData.contains(orderItemLU.getString("orderItemSeqId"))) {
												tempData.add(orderItemLU.getString("orderItemSeqId"));
												updatedTracking.put(orderId, tempData);
											}
										} else {
											updatedTracking.put(orderId, UtilMisc.<String>toList(orderItemLU.getString("orderItemSeqId")));
										}
									}
								}
							} catch(Exception eE) {
								EnvUtil.reportError(eE);
								Debug.logError(eE, "Error trying to update tracking when oISGA is not available for: " + orderId + " : " + orderItemSeqId, module);
							}
						}
					}
				}
			}
		}

		return updatedTracking;
	}

	/**
	 * Schedule the item shipment status change
	 * @param delegator
	 * @param dispatcher
	 * @param orderId
	 * @param orderItemSeqId
	 * @param stagger
	 * @param userLogin
	 * @throws GenericEntityException
	 * @throws GenericServiceException
	 */
	public static void processItemShipStatusChange(Delegator delegator, LocalDispatcher dispatcher, String orderId, String orderItemSeqId, boolean stagger, GenericValue userLogin) throws GenericEntityException, GenericServiceException {
		if(userLogin == null) {
			userLogin = EnvUtil.getSystemUser(delegator);
		}

		Debug.logInfo("Setting shipped status for: " + orderId + " : " + orderItemSeqId, module);
		if(stagger) {
			//Set item status 1-7 minutes staggered
			Random random = new Random();
			int Low = 1;
			int High = EnvConstantsUtil.MIN_TO_STAGGER_SHIP_STATUS;
			int randomInt = random.nextInt(High - Low) + Low;

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, randomInt);
			dispatcher.schedule("changeOrderItemStatus", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId, "statusId", "ITEM_SHIPPED", "userLogin", userLogin), cal.getTimeInMillis());
		} else {
			dispatcher.runSync("changeOrderItemStatus", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId, "statusId", "ITEM_SHIPPED", "userLogin", userLogin));
		}
	}

	public static ArrayList getUserQuotes(Delegator delegator, GenericValue userLogin) throws GenericEntityException {
		List<GenericValue> quoteDataList = EntityQuery.use(delegator).from("CustomEnvelopeAndQCQuote").where("userEmail", userLogin.getString("userLoginId")).orderBy("createdStamp ASC").cache().queryList();
		ArrayList<Map<String,Object>> quoteList = new ArrayList<Map<String,Object>>();

		if(quoteDataList != null) {
			for(GenericValue quoteData : quoteDataList) {
				Map<String, Object> quoteDataMap = new HashMap<String, Object>();
				quoteDataMap.put("quoteId", quoteData.getString("quoteId"));
				quoteDataMap.put("productId", quoteData.getString("productId"));
				quoteDataMap.put("quoteRequestId", quoteData.getString("quoteRequestId"));
				quoteDataMap.put("comment", quoteData.getString("comment"));
				quoteDataMap.put("production", quoteData.getString("production"));
				quoteDataMap.put("statusId", quoteData.getString("statusId"));
				quoteDataMap.put("userEmail", quoteData.getString("userEmail"));
				quoteDataMap.put("firstName", quoteData.getString("firstName"));
				quoteDataMap.put("lastName", quoteData.getString("lastName"));
				quoteDataMap.put("companyName", quoteData.getString("companyName"));
				quoteDataMap.put("address1", quoteData.getString("address1"));
				quoteDataMap.put("address2", quoteData.getString("address2"));
				quoteDataMap.put("city", quoteData.getString("city"));
				quoteDataMap.put("stateProvinceGeoId", quoteData.getString("stateProvinceGeoId"));
				quoteDataMap.put("postalCode", quoteData.getString("postalCode"));
				quoteDataMap.put("phone", quoteData.getString("phone"));
				quoteDataMap.put("countryGeoId", quoteData.getString("countryGeoId"));
				quoteDataMap.put("internalComment", quoteData.getString("internalComment"));
				quoteDataMap.put("comment", quoteData.getString("comment"));
				quoteDataMap.put("createdStamp", quoteData.getTimestamp("createdStamp"));

				Gson gson = new GsonBuilder().serializeNulls().create();
				quoteDataMap.put("pricingRequest", gson.fromJson(quoteData.getString("pricingRequest"), HashMap.class));
				quoteDataMap.put("pricingResponse", gson.fromJson(quoteData.getString("pricingResponse"), HashMap.class));
				quoteList.add(quoteDataMap);
			}
		}

		return quoteList;
	}

	/**
	 * Insert order item flags
	 * @param delegator
	 * @param context
	 * @throws GenericEntityException
	 */
	public static void updateOrderItemFlag(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
		GenericValue data = delegator.makeValue("OrderItemFlag");
		data = EnvUtil.insertGenericValueData(delegator, data, context, true);
		delegator.createOrStore(data);
	}

	/**
	 * Temporary function to see if an item is exportable to syracuse warehouse
	 * @param delegator
	 * @param orderId
	 * @param orderItemSeqId
	 * @return
	 */
	public static boolean syracuseCompatible(Delegator delegator, String orderId, String orderItemSeqId) throws GenericEntityException {
		GenericValue orderItem = EntityQuery.use(delegator).from("OrderItem").where("orderId", orderId, "orderItemSeqId", orderItemSeqId).queryOne();

		//must be 250 or less quantity
		if(orderItem.getBigDecimal("quantity").compareTo(new BigDecimal("250")) <= 0) {
			GenericValue product = EntityQuery.use(delegator).from("Product").where("productId", orderItem.getString("productId")).cache(true).queryOne();
			GenericValue productStock = EntityQuery.use(delegator).from("ProductStock").where("productId", orderItem.getString("productId")).cache(true).queryOne();

			//must be warehouse b item
			if(productStock != null && "B".equalsIgnoreCase(productStock.getString("location"))) {
				Map<String, String> features = ProductHelper.getProductFeatures(delegator, product, null);
				String color = features.get("COLOR");
				if(UtilValidate.isNotEmpty(color)) {
					color = color.toLowerCase();
					//must not be tyvek or metallic
					if(!color.contains("metallic") && !color.contains("tyvek")) {
						//must be >=3 1/8 x 5 1/2 && <= 9 x12
						BigDecimal minWidth = new BigDecimal("3.125");
						BigDecimal maxWidth = new BigDecimal("9");

						if(UtilValidate.isNotEmpty(product.getBigDecimal("productWidth")) && UtilValidate.isNotEmpty(product.getBigDecimal("productHeight"))) {
							if((product.getBigDecimal("productWidth").compareTo(minWidth) >= 0 && product.getBigDecimal("productWidth").compareTo(maxWidth) <= 0)
									|| (product.getBigDecimal("productHeight").compareTo(minWidth) >= 0 && product.getBigDecimal("productHeight").compareTo(maxWidth) <= 0)
								) {

								//must not be 9" or larger and window
								if((product.getBigDecimal("productWidth").compareTo(new BigDecimal("9")) == 0 || product.getBigDecimal("productHeight").compareTo(new BigDecimal("9")) == 0) && product.getString("productName").toLowerCase().contains("window")) {
									return false;
								}

								return true;
							}
						}
					}
				}
			}
		}

		return false;
	}
	
	public static void updatePrepressQueueFilter(Delegator delegator, GenericValue userLogin, String queueName, String statusId, String hidden) throws GenericEntityException {
		GenericValue filter = EntityQuery.use(delegator).from("PrepressQueueFilter").where("userLoginId", userLogin.getString("userLoginId"), "queueName", queueName, "statusId", statusId).queryOne();
		
		if (filter != null) {
			filter.set("hidden", hidden);
			filter.store();
		} else {
			filter = delegator.makeValue("PrepressQueueFilter");
			filter.put("userLoginId", userLogin.getString("userLoginId"));;
			filter.put("queueName", queueName);
			filter.put("statusId", statusId);
			filter.put("hidden", hidden);
			delegator.create(filter);
		}
	}

	public static Map<String, Object> getPrepressQueueFilterList(Delegator delegator, GenericValue userLogin) throws GenericEntityException {
		List<GenericValue> prepressQueueFilterList = EntityQuery.use(delegator).from("PrepressQueueFilter").where("userLoginId", userLogin.getString("userLoginId")).queryList();

		Map<String, Object> data = new HashMap<>();
		
		for (GenericValue prepressQueueFilter : prepressQueueFilterList) {
			Map<String, String> statusIdMap = new HashMap<>();
			if (UtilValidate.isNotEmpty(data.get(prepressQueueFilter.getString("queueName")))) {
				statusIdMap = (HashMap<String, String>) data.get(prepressQueueFilter.getString("queueName"));
			}
			statusIdMap.put(prepressQueueFilter.getString("statusId"), prepressQueueFilter.getString("hidden"));
			data.put(prepressQueueFilter.getString("queueName"), statusIdMap);
/*
			if (UtilValidate.isEmpty(data.get(prepressQueueFilter.getString("queueName")))) {
				ArrayList<String> myArray = new ArrayList<>();
				data.put(prepressQueueFilter.getString("queueName"), myArray);
			}
			
			ArrayList<String> myArray = (ArrayList) data.get(prepressQueueFilter.getString("queueName"));
			myArray.add(prepressQueueFilter.getString("statusId"));
			data.put(prepressQueueFilter.getString("queueName"), myArray);*/
		}

		return data;
	}

	public static List<GenericValue> getAllProofRreadyOrderItems(Delegator delegator, boolean evalPendingChange) throws GenericEntityException, ParseException {
		List<EntityCondition> conditionList = new ArrayList<>();
		if(evalPendingChange) {
			conditionList.add(EntityCondition.makeCondition(UtilMisc.toList(EntityCondition.makeCondition("pendingChange", EntityOperator.EQUALS, null), EntityCondition.makeCondition("pendingChange", EntityOperator.EQUALS, "N")), EntityOperator.OR));
		}
		conditionList.add(EntityCondition.makeCondition("orderStatusId", EntityOperator.NOT_EQUAL, "ORDER_CANCELLED"));
		conditionList.add(EntityCondition.makeCondition("itemStatusId", EntityOperator.EQUALS, "ART_READY_FOR_REVIEW"));
		conditionList.add(EntityCondition.makeCondition("orderDate", EntityOperator.GREATER_THAN_EQUAL_TO, EnvUtil.convertStringToTimestamp("2016-01-01 00:00:00.000"))); //failsafe to not pull real old orders
		conditionList.add(EntityCondition.makeCondition("salesChannelEnumId", EntityOperator.NOT_EQUAL, "FOLD_SALES_CHANNEL")); //TODO fix this, hardcoded
		return EntityQuery.use(delegator).from("EnvOrderHeaderAndItems").where(EntityCondition.makeCondition(conditionList, EntityOperator.AND)).queryList();
	}

	public static void updateVendorOrder(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
		GenericValue vendorOrder = delegator.findOne("VendorOrder", UtilMisc.toMap("orderId", (String) context.get("orderId"), "orderItemSeqId", context.get("orderItemSeqId")), false);

		if (UtilValidate.isNotEmpty(vendorOrder)) {
			Iterator it = ((Map<String, Object>) context).entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				vendorOrder.put((String) pair.getKey(), (String) pair.getValue());
				it.remove();
			}

			vendorOrder.store();
		}
	}

	public static void generateArtworkFiles(Delegator delegator, Map<String, ? extends Object> context) throws Exception {
		String orderId = (String) context.get("orderId");
		Map<String, Object> orderData = OrderHelper.getOrderData(delegator, orderId, false);
		if (UtilValidate.isNotEmpty(orderData)) {
			Map<String, Object> orderInfo = OrderHelper.buildOrderDetails(delegator, orderData);
			for (Map<String, Object> lineItem : (List<Map<String, Object>>)orderInfo.get("lineItems")) {
				if((boolean)lineItem.get("isPrinted")) {
					String s7DesignId = (String) lineItem.get("scene7DesignId");
					if(UtilValidate.isNotEmpty(s7DesignId)) {
						GenericValue scene7Design = delegator.findOne("Scene7Design", UtilMisc.toMap("scene7DesignId", s7DesignId), false);
						if(scene7Design != null) {
							Map<String, Object> jsonMap = new Gson().fromJson(scene7Design.getString("data"), HashMap.class);
							if(jsonMap.containsKey("imagePathList")) {
								List<String> customerArtFiles = (List<String>)jsonMap.get("imagePathList");
								for (String customerArtFile : customerArtFiles) {
									String extension = customerArtFile.contains(".") ? customerArtFile.substring(customerArtFile.indexOf(".")) : "";
									OrderHelper.setOrderItemContent(delegator, (String) context.get("orderId"), (String) lineItem.get("orderItemSeqId"), "OIACPRP_FILE", UtilMisc.toMap("name", "Customer File" + extension, "path", customerArtFile.replace('\\', '/').replaceAll(EnvConstantsUtil.OFBIZ_HOME, "")));
								}
							}
						}
					}
					String orderItemSeqId = (String) lineItem.get("orderItemSeqId");
					for (String side : Arrays.asList("FRONT", "BACK")) {
						File imageFile = new File(EnvConstantsUtil.OFBIZ_HOME + "/" + EnvConstantsUtil.UPLOAD_DIR + "/" + EnvConstantsUtil.TEXEL_DESIGN_IMAGE_DIR + "/" + s7DesignId + "_" + side.toLowerCase() + ".png");
						if(imageFile.exists()) {
							String fileName = orderId + "_" + orderItemSeqId;
							if(UtilValidate.isNotEmpty(imageFile.getName()) && imageFile.getName().contains(".")) {
								fileName += imageFile.getName().substring(imageFile.getName().lastIndexOf(".")).trim();
							}
							Map<String, Object> file = FileHelper.createFileFromFile(imageFile, fileName);
							if((boolean)file.get("success")) {
								OrderHelper.discOrderItemContent(delegator, orderId, orderItemSeqId, "OIACPRP_" + side);
								OrderHelper.setOrderItemContent(delegator, orderId, orderItemSeqId, "OIACPRP_" + side, file);
							}
						}
					}
				}
			}
		}
	}

	public static List<Map<String, Object>> getItemAddonList(Delegator delegator, List<Map> items) {
		List<Map<String, Object>> itemAddonList = new ArrayList<>();
		Map<String, BigDecimal> itemTotalQuantityData = new HashMap<>();

		try {
			for (Iterator i = items.iterator(); i.hasNext(); ) {
				Map item = (Map) i.next();

				GenericValue orderItem = (GenericValue) item.get("item");
				String productId = orderItem.getString("productId");
				BigDecimal quantity = orderItem.getBigDecimal("quantity");

				if (UtilValidate.isNotEmpty(itemTotalQuantityData.get(productId))) {
					itemTotalQuantityData.put(productId, itemTotalQuantityData.get(productId).add(quantity));
				} else {
					itemTotalQuantityData.put(productId, quantity);
				}
			}
		} catch (Exception e) {
			Debug.log("There was an issue trying to obtain all product id's and their quantities as a whole. " + e.getMessage(), module);
		}

		try {
			for (Map.Entry<String, BigDecimal> itemTotalQuantityEntry : itemTotalQuantityData.entrySet()) {
				String productId = itemTotalQuantityEntry.getKey();
				BigDecimal quantity = itemTotalQuantityEntry.getValue();

				Map<String, Map<String, Object>> productAddonList = ProductHelper.getProductAddonData(delegator, productId);

				for (Map.Entry productAddonEntry : productAddonList.entrySet()) {
					String sku = String.valueOf(productAddonEntry.getKey());
					Map<String, Object> productAddon = (HashMap) productAddonEntry.getValue();
					String name = String.valueOf(productAddon.get("name"));
					List<Map<String, Object>> quantityThresholdDataList = (ArrayList) productAddon.get("quantityThresholdData");
					BigDecimal addonQuantity = BigDecimal.ZERO;
					BigDecimal amount = BigDecimal.ZERO;

					for (Map<String, Object> quantityThresholdData : quantityThresholdDataList) {
						String type = String.valueOf(quantityThresholdData.get("type"));

						if (type.equals("interval")) {
							addonQuantity = addonQuantity.add(quantity.divide(new BigDecimal(String.valueOf(quantityThresholdData.get("threshold"))), 0, RoundingMode.CEILING).multiply(new BigDecimal(String.valueOf(quantityThresholdData.get("quantity")))));
							amount = amount.add(quantity.divide(new BigDecimal(String.valueOf(quantityThresholdData.get("threshold"))), 0, RoundingMode.CEILING).multiply(new BigDecimal(String.valueOf(quantityThresholdData.get("price")))));
						} else if (type.equals("threshold")) {
							if (quantity.compareTo(new BigDecimal(String.valueOf(quantityThresholdData.get("threshold")))) >= 0) {
								addonQuantity = addonQuantity.add(new BigDecimal(String.valueOf(quantityThresholdData.get("quantity"))));
								amount = amount.add(new BigDecimal(String.valueOf(quantityThresholdData.get("price"))));
							}
						}
					}

					itemAddonList.add(UtilMisc.toMap("sku", sku, "name", name, "quantity", addonQuantity, "amount", amount.setScale(EnvConstantsUtil.ENV_SCALE_P, EnvConstantsUtil.ENV_ROUNDING)));
				}
			}
		} catch (Exception e) {
			Debug.log("There was an issue trying to generate the new item addon list. " + e.getMessage(), module);
		}

		return itemAddonList;
	}

	public static List<GenericValue> getWorkOrderEmployeeList(Delegator delegator, boolean useCache) {
		List<GenericValue> workOrderEmployeeList = null;

		try {
			workOrderEmployeeList = delegator.findAll("WorkOrderEmployee", useCache);
		} catch (GenericEntityException gee) {
			Debug.log("There was an issue trying to get all work order employees. " + gee.getMessage(), module);
			EnvUtil.reportError(gee);
		}

		return workOrderEmployeeList;
	}

	public static void addWorkOrderEmployee(Delegator delegator, String emailAddress, String netsuiteId) throws GenericEntityException {
		GenericValue newValue = delegator.makeValue("WorkOrderEmployee", UtilMisc.toMap("emailAddress", emailAddress, "netsuiteId", netsuiteId));
		delegator.createOrStore(newValue);
	}

	public static void removeWorkOrderEmployee(Delegator delegator, String emailAddress) throws GenericEntityException {
		GenericValue orderItemAttr = delegator.findOne("WorkOrderEmployee", UtilMisc.toMap("emailAddress", emailAddress), false);
		orderItemAttr.remove();
	}
}
