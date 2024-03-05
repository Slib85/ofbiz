/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.netsuite;

import java.lang.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.envelopes.cxml.CXMLStatusRequest;
import com.envelopes.cxml.CXMLStatusResponse;
import com.envelopes.http.HTTPHelper;
import com.envelopes.order.OrderHelper;
import com.envelopes.party.PartyEvents;
import com.envelopes.party.PartyHelper;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.envelopes.util.*;

public class NetsuiteEvents {
	public static final String module = NetsuiteEvents.class.getName();

	/**
	 * Export order to netsuite
	 * @param request
	 * @param response
	 * @return
	 */
	public static String exportOrder(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		boolean success = true;
		try {
			Map <String, Object> exportOrder = dispatcher.runSync("exportOrder", UtilMisc.toMap("orderIds", UtilMisc.toList((String) context.get("orderId")), "ignoreValidity", Boolean.valueOf((String) context.get("ignoreValidity")), "userLogin", userLogin));
			if(ServiceUtil.isError(exportOrder)) {
				success = false;
			}
			jsonResponse.put("message", exportOrder.get("message"));
		} catch(GenericServiceException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to export order. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/**
	 * Get an item PO
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getPO(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		boolean success = false;
		try {
			jsonResponse = NetsuiteHelper.findPO(delegator, dispatcher, UtilMisc.<String, Object>toMap("purchaseOrderId", context.get("id")));
			success = true;
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to export order. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/**
	 * Get an channel reports
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getChannelSalesReport(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		boolean success = false;
		try {
			jsonResponse = NetsuiteHelper.getChannelSalesReport(delegator, dispatcher);
			success = true;
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to get channel data. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/**
	 * Get all open work orders
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getItemWorkOrder(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();

		boolean success = false;
		try {
			Map<String, String> workOrderList = NetsuiteHelper.getItemWorkOrder();
			if(UtilValidate.isNotEmpty(context.get("workOrderId"))) {
				jsonResponse.put((String) context.get("workOrderId"), getKeyByValue(workOrderList, (String) context.get("workOrderId")));
			} else if(UtilValidate.isNotEmpty(context.get("productId"))) {
				jsonResponse.put((String) context.get("productId"), workOrderList.get((String) context.get("productId")));
			}
			success = true;
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to export order. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
		for (Map.Entry<T, E> entry : map.entrySet()) {
			if (Objects.equals(value, entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	public static String createCustomerFromNetsuite(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();

		Debug.logInfo("Netsuite customer data: " + context, module);

		boolean success = false;
		if(UtilValidate.isNotEmpty(context)) {
			try {
				//check if the netsuite id exists in external id field
				GenericValue party = PartyHelper.getPartyByExternalId(delegator, (String) context.get("netsuiteId"));

				//get the email/look up the email
				if (!PartyHelper.isEmailActive(delegator, (String) context.get("emailAddress")) && party == null) {
					//if it doesnt exist, create a new customer
					GenericValue userLogin = PartyHelper.createAccount(delegator, dispatcher, context);
					if (userLogin != null) {
						//add netsuite id
						party = userLogin.getRelatedOne("Party", false);
						party.set("externalId", (String) context.get("netsuiteId"));
						party.store();

						//if customer is non taxable
						if ("F".equalsIgnoreCase((String) context.get("taxable"))) {
							PartyHelper.createPartyRole(delegator, party.getString("partyId"), "NON_TAXABLE");
						}

						//if customer is trade
						if ("T".equalsIgnoreCase((String) context.get("trade"))) {
							PartyHelper.createPartyRole(delegator, party.getString("partyId"), "WHOLESALER");
						}

						//if customer is non profit
						if ("T".equalsIgnoreCase((String) context.get("nonprofit"))) {
							PartyHelper.createPartyRole(delegator, party.getString("partyId"), "NON_PROFIT");
						}

						//update netsuite with party id
						jsonResponse.put("partyId", party.getString("partyId"));
						jsonResponse.put("existing", false);
						success = true;
					}
				} else {
					GenericValue userLogin = null;
					if (party != null) {
						userLogin = EntityQuery.use(delegator).from("UserLogin").where("partyId", party.getString("partyId")).queryFirst();
					} else {
						userLogin = PartyHelper.getUserLogin(delegator, (String) context.get("emailAddress"));
						party = userLogin.getRelatedOne("Party", false);
					}

					Map<String, Object> getAEPartyRoles = dispatcher.runSync("getAEPartyRoles", UtilMisc.toMap("partyId", party.getString("partyId")));
					boolean isTrade = (UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isTrade"))) ? ((Boolean) getAEPartyRoles.get("isTrade")).booleanValue() : false;
					boolean isNonProfit = (UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isNonProfit"))) ? ((Boolean) getAEPartyRoles.get("isNonProfit")).booleanValue() : false;
					boolean isNonTaxable = (UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isNonTaxable"))) ? ((Boolean) getAEPartyRoles.get("isNonTaxable")).booleanValue() : false;

					jsonResponse.put("partyId", party.getString("partyId"));
					jsonResponse.put("isTrade", isTrade);
					jsonResponse.put("isNonProfit", isNonProfit);
					jsonResponse.put("isNonTaxable", isNonTaxable);
					jsonResponse.put("existing", true);
					success = true;
				}
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to create customer. " + e + " : " + e.getMessage(), module);
			}
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String updateFulfillmentFromNetsuite(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();

		boolean success = false;
		if(UtilValidate.isNotEmpty(context.get("orderId")) && UtilValidate.isNotEmpty(context.get("orderItemSeqId"))) {
			context.put("orderItemSeqId", EnvUtil.formatOrderItemSeqNumber((String) context.get("orderItemSeqId")));

			try {
				GenericValue vendorOrder = EntityQuery.use(delegator).from("VendorOrder").where("orderId", (String) context.get("orderId"), "orderItemSeqId", (String) context.get("orderItemSeqId")).queryOne();
				if(vendorOrder != null) {
					vendorOrder.set("stockLocation", (String) context.get("name"));
					vendorOrder.store();
					success = true;

					try {
						//send the vendor the status request update
						//currently only CXML vendors are supported
						CXMLStatusRequest statusRequest = new CXMLStatusRequest(request);
						statusRequest.setVendorOrder();
						if(statusRequest.getVendor() != null && "Y".equalsIgnoreCase(statusRequest.getVendor().getString("notifyOnFulfillment")) && UtilValidate.isNotEmpty(statusRequest.getVendor().getString("cxmlStatusEndpoint"))) {
							statusRequest.createRequestDoc();
							String result = HTTPHelper.getURL(statusRequest.getVendor().getString("cxmlStatusEndpoint"), "POST", null, null, UtilMisc.toMap("key", statusRequest.getXMLRequestString()), null, false, EnvConstantsUtil.RESPONSE_XML, EnvConstantsUtil.RESPONSE_XML);

							//process the response
							CXMLStatusResponse statusResponse = new CXMLStatusResponse(result);
							if (statusResponse.isSuccess()) {
								//
							} else {
								//
							}
						}
					} catch(Exception req) {
						EnvUtil.reportError(req);
						Debug.logError("Error while trying to send cxml status request. " + req + " : " + req.getMessage(), module);
					}
				}
			} catch(Exception e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to save fulfillment stock location. " + e + " : " + e.getMessage(), module);
			}
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String getNetsuiteJSON(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();

		boolean success = false;
		if(UtilValidate.isNotEmpty(context.get("orderId"))) {
			try {
				Map<String, Object> orderData = OrderHelper.getOrderData(delegator, (String) context.get("orderId"), true);
				jsonResponse.put("orderMap", NetsuiteHelper.createOrderMap(delegator, dispatcher, orderData, true));
			} catch(Exception e) {
				EnvUtil.reportError(e);
			}
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}
}