/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.cxml;

import java.lang.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.envelopes.netsuite.NetsuiteHelper;
import com.envelopes.order.OrderHelper;
import com.envelopes.ups.UPSHelper;
import com.envelopes.util.*;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

public class CXMLEvents {
	public static final String module = CXMLEvents.class.getName();

	/**
	 * Get the notification
	 * CAN ONLY HANDLE ONE LINE ITEM AT A TIME!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * @param request
	 * @param response
	 * @return
	 */
	public static String outsourceOrderRequest(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		context.put("userLogin", (GenericValue) request.getSession().getAttribute("userLogin"));

		Map<String, Object> jsonResponse = new HashMap<>();
		String result = CXMLHelper.outsourceOrderRequest(delegator, dispatcher, context);

		if(UtilValidate.isNotEmpty(result)) {
			jsonResponse.put("error", result);
		}
		jsonResponse.put("success", UtilValidate.isEmpty(result));

		//return EnvUtil.doResponse(request, response, null, xml, EnvConstantsUtil.RESPONSE_XML);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String cxmlShipNotice(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		String xmlResponse = "";

		CXMLShipNoticeRequest shipNotice = new CXMLShipNoticeRequest(request);
		shipNotice.getXML();
		shipNotice.getOrderItemAndTracking();
		try {
			boolean trackingSuccess = shipNotice.isXMLDataValid() && shipNotice.updateTracking();
			shipNotice.createResponseDoc(trackingSuccess);
			xmlResponse = shipNotice.getXMLString();
			//mark the item fulfilled in netsuite
			if(trackingSuccess) {
				Map<String, Object> fulfillmentResponse = createOrderItemFulfillment(delegator, dispatcher, shipNotice.getOrderId(), shipNotice.getOrderItemSeqId(), shipNotice.getTrackingNumber(), true);
			}
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to send cxml: " + xmlResponse, module);
		}

		return EnvUtil.doResponse(request, response, null, xmlResponse, EnvConstantsUtil.RESPONSE_XML);
	}

	public static String cxmlNavitorShipNotice(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		String xmlResponse = "";

		BaseCXMLShipNoticeRequest shipNoticeRequest = new BaseCXMLShipNoticeRequest(request).updateTracking();
		try {
			//mark the item fulfilled in netsuite
			if(shipNoticeRequest.isSuccess()) {
				createOrderItemFulfillment(delegator, dispatcher, shipNoticeRequest.getOrderId(), shipNoticeRequest.getOrderItemSeqId(), shipNoticeRequest.getTrackingNumber(), true);
			}
			xmlResponse = new BaseCXMLShipNoticeResponse(shipNoticeRequest.getOrderId(), shipNoticeRequest.getOrderItemSeqId(), shipNoticeRequest.isSuccess()).toString();
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to send cxml: " + xmlResponse, module);
		}

		return EnvUtil.doResponse(request, response, null, xmlResponse, EnvConstantsUtil.RESPONSE_XML);
	}

	public static String cxmlStatusNotice(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		String xmlResponse = "";

		boolean jobSuccess = false;
		CXMLStatusRequest statusRequest = new CXMLStatusRequest(request);
		statusRequest.getXML();
		try {
			if(statusRequest.isXMLDataValid()) {
				//check if its a job id update
				statusRequest.getJobId();
				if(UtilValidate.isNotEmpty(statusRequest.jobID)) {
					statusRequest.updateJobId();
					jobSuccess = true;
				}

				//check if its a stock notice update
				statusRequest.getStockIndicator();
				if(UtilValidate.isNotEmpty(statusRequest.stockIndicator)) {
					statusRequest.updateStockIndicator();
					jobSuccess = true;
				}

				//check if its a stock error update
				statusRequest.getStockError();
				if(UtilValidate.isNotEmpty(statusRequest.stockError)) {
					statusRequest.updateStockError();
					jobSuccess = true;
				}

				//check if its a status update
				statusRequest.getStatusCode();
				if(UtilValidate.isNotEmpty(statusRequest.statusCode)) {
					statusRequest.updateStatusCode();
					jobSuccess = true;
				}

				//send back response
				CXMLStatusResponse statusResponse = new CXMLStatusResponse(request);
				statusResponse.setOrderId(statusRequest.getOrderId());
				statusResponse.setOrderItemSeqId(statusRequest.getOrderItemSeqId());
				statusResponse.createResponseDoc(jobSuccess);
				xmlResponse = statusResponse.getXMLResponseString();
				//update netsuite order
				if(jobSuccess) {
					dispatcher.runSync("updateNetsuiteOrder", UtilMisc.toMap("orderId", statusRequest.getOrderId(), "userLogin", EnvUtil.getDelayedUser(delegator)));
				}
			}
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to send cxml: " + xmlResponse, module);
		}

		return EnvUtil.doResponse(request, response, null, xmlResponse, EnvConstantsUtil.RESPONSE_XML);
	}

	public static String addZone(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> jsonResponse = new HashMap<>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		boolean success = false;

		if(UtilValidate.isNotEmpty(context.get("shipmentMethodTypeId"))
				&& UtilValidate.isNotEmpty(context.get("zone"))
				&& UtilValidate.isNotEmpty(context.get("weight"))
				&& UtilValidate.isNotEmpty(context.get("averageCost"))) {
			try {
				GenericValue zone = delegator.makeValue("ZoneCostLookup");
				zone = EnvUtil.insertGenericValueData(delegator, zone, context);

				delegator.createOrStore(zone);
				success = true;
			} catch (GenericEntityException e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "Error trying to add zone.", module);
			}
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String addVendorTracking(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> jsonResponse = new HashMap<>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		boolean success = false;

		if (UtilValidate.isNotEmpty(context.get("orderId")) && UtilValidate.isNotEmpty(context.get("orderItemSeqId")) && UtilValidate.isNotEmpty(context.get("trackingNumber")) && UtilValidate.isNotEmpty(context.get("packageWeight"))) {
			try {
				GenericValue vendorOrder = EntityQuery.use(delegator).from("VendorOrder").where("orderId", (String) context.get("orderId"), "orderItemSeqId", (String) context.get("orderItemSeqId")).queryOne();
				if(vendorOrder != null) {
					vendorOrder.put("trackingNumber", (String) context.get("trackingNumber"));
					if(NumberUtils.isNumber((String) context.get("packageWeight"))) {
						vendorOrder.put("packageWeight", new BigDecimal((String) context.get("packageWeight")));
					}
					vendorOrder.store();

					if(UtilValidate.isNotEmpty(context.get("doFulfillment")) && "Y".equalsIgnoreCase((String) context.get("doFulfillment"))) {
						Map<String, Object> itemFulfillment = createOrderItemFulfillment(delegator, dispatcher, (String) context.get("orderId"), (String) context.get("orderItemSeqId"), (String) context.get("trackingNumber"));
						//change status if sent successfully
						if(UtilValidate.isNotEmpty(itemFulfillment) && itemFulfillment.containsKey("itemFulfillmentId")) {
							GenericValue orderItem = EntityQuery.use(delegator).from("OrderItem").where("orderId", (String) context.get("orderId"), "orderItemSeqId", (String) context.get("orderItemSeqId")).queryOne();
							Map<String, Map<String, String>> orderAndTracking = new HashMap<>();
							orderAndTracking.put((String) context.get("orderId"), UtilMisc.<String, String>toMap((String) context.get("orderItemSeqId") + "|" + orderItem.getString("productId"), (String) context.get("trackingNumber")));
							Map<String, List<String>> updatedTracking = OrderHelper.updateOrderTracking(delegator, orderAndTracking);

							//change item statuses to shipped
							Iterator updatedTrackingIter = updatedTracking.entrySet().iterator();
							while(updatedTrackingIter.hasNext()) {
								Map.Entry pairs = (Map.Entry) updatedTrackingIter.next();
								String orderId = (String) pairs.getKey();
								List<String> orderItemSeqIds = (List<String>) pairs.getValue();

								for(String orderItemSeqId : orderItemSeqIds) {
									OrderHelper.processItemShipStatusChange(delegator, dispatcher, orderId, orderItemSeqId, false, userLogin);
								}
							}
						}
					}
				}
				success = true;
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "Error trying to update tracking for " + context.get("orderId"), module);
			}
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static Map<String, Object> createOrderItemFulfillment(Delegator delegator, LocalDispatcher dispatcher, String orderId, String orderItemSeqId, String trackingNumber) throws Exception {
		return createOrderItemFulfillment(delegator, dispatcher, orderId, orderItemSeqId, trackingNumber, false);
	}
	public static Map<String, Object> createOrderItemFulfillment(Delegator delegator, LocalDispatcher dispatcher, String orderId, String orderItemSeqId, String trackingNumber, boolean delay) throws Exception {
		Map<String, Object> data = new HashMap<>();
		data.put("orderId", orderId);
		data.put("orderItemSeqId", orderItemSeqId);
		data.put("lineItem", EnvUtil.removeChar("0", orderItemSeqId, true, false, false));
		data.put("trackingNumber", trackingNumber);
		data.put("packageWeight", (BigDecimal.ONE).toPlainString());
		data.put("tranDate", EnvConstantsUtil.MDY.format(UtilDateTime.nowTimestamp()));

		if(EnvUtil.isUPSTrackingNumber(trackingNumber)) {
			Map<String, Object> trackingData = UPSHelper.getTrackingData(trackingNumber);
			if(UtilValidate.isNotEmpty(trackingData)) {
				data.put("packageWeight", ((BigDecimal) trackingData.get("weight")).toPlainString());
				data.put("tranDate", (String) trackingData.get("pickupDate"));
				data.put("deliveryDate", (String) trackingData.get("deliveryDate"));
			}
		}

		//get associated vendor po id for netsuite
		GenericValue vendorOrder = EntityQuery.use(delegator).from("VendorOrder").where("orderId", orderId, "orderItemSeqId", orderItemSeqId).queryOne();
		if(vendorOrder != null) {
			data.put("purchaseOrderId", vendorOrder.getString("purchaseOrderId"));
		}

		if(delay) {
			data.put("userLogin", EnvUtil.getDelayedUser(delegator));
			dispatcher.runSync("createItemFulfillment", data);
			return ServiceUtil.returnSuccess();
		} else {
			return NetsuiteHelper.createOrderItemFulfillment(delegator, dispatcher, data);
		}
	}
}