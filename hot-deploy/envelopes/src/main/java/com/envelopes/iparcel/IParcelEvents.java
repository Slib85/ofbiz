/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.iparcel;

import java.lang.*;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.envelopes.cart.PersistentCart;
import com.envelopes.http.HTTPHelper;
import com.envelopes.order.Order;

import com.envelopes.util.*;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.service.LocalDispatcher;

public class IParcelEvents {
	public static final String module = IParcelEvents.class.getName();

	public static String CUSTOMER_ID;
	public static String API_KEY;
	public static String SET_CHECKOUT_URL;
	public static String CART_URL;
	public static String CHECKOUT_DETAILS;
	public static String CANCEL_TRANSACTION;
	public static String CURRENCY = "USD";
	public static String REDIRECT_MESSAGE = "You have been redirected to UPS i-parcel to complete your international checkout.";
	public static String SHOW_RED_MESSAGE = "0";
	public static String LOGO = "https://www.envelopes.com/html/img/logo/logo.png";
	public static String RETURN_URL = "/iparcelNotification";
	public static String SHOPPING_URL = "https://www.envelopes.com/cart";
	public static String CANCEL_URL = "https://www.envelopes.com/cart";
	public static Map<String, ?> iPAddress = UtilMisc.toMap("contactMechId", "1", "toName", "Envelopes.com /i-parcel", "address1", "45 Fernwood Avenue", "address2", "Raritan Center", "city", "Edison", "stateProvinceGeoId", "NJ", "postalCode", "08837", "countryGeoId", "USA");
	public static String NETSUITE_EXT_ID = "9994";
	public static String NETSUITE_ID = "15149607";

	static {
		try{
			CUSTOMER_ID = UtilProperties.getPropertyValue("envelopes", "iparcel.id");
			API_KEY = UtilProperties.getPropertyValue("envelopes", "iparcel.api.key");
			SET_CHECKOUT_URL = UtilProperties.getPropertyValue("envelopes", "iparcel.set.checkout.url");
			CART_URL = UtilProperties.getPropertyValue("envelopes", "iparcel.cart.url");
			CHECKOUT_DETAILS = UtilProperties.getPropertyValue("envelopes", "iparcel.checkout.details");
			CANCEL_TRANSACTION = UtilProperties.getPropertyValue("envelopes", "iparcel.cancel.transaction");
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error, could not open: " + "envelopes.properties", module);
		}
	}

	/**
	 * Get the notification from iparcel for the successful response
	 * @param request
	 * @param response
	 * @return
	 */
	public static String iparcelNotification(HttpServletRequest request, HttpServletResponse response) {
		IPGetCheckoutDetails transactionDetail = new IPGetCheckoutDetails(request);

		try {
			transactionDetail.sendRequest();
		} catch(Exception e) {
			EnvUtil.reportError(e);
		}

		if(transactionDetail.isResponseValid()) {
			//create the order
			return createIParcelOrder(request, response, transactionDetail);
		}

		return "error"; //if order doesnt get created, return error
	}

	/**
	 * Get the notification from iparcel to get updates for an order payment status
	 * @param request
	 * @param response
	 * @return
	 */
	public static String iparcelPaymentStatus(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();

		//business: api key
		//status: SUCCESS or FAILED
		//reference_number: usually our order number
		//trackingnumber: iparcel order number

		boolean success = false;
		if(API_KEY.equalsIgnoreCase((String) context.get("business")) && "SUCCESS".equalsIgnoreCase((String) context.get("status"))) {
			//look up order and set that status to created so it can move along
			String orderId = (String) context.get("reference_number");
			String iparcelOrderNumber = (String) context.get("trackingnumber");

			//lookup the order via external id
			try {
				GenericValue order = EntityUtil.getFirst(delegator.findByAnd("OrderHeader", UtilMisc.toMap("externalId", iparcelOrderNumber), null, false));
				if(order != null) {
					dispatcher.runSync("changeOrderStatus", UtilMisc.toMap("orderId", order.getString("orderId"), "statusId", "ORDER_CREATED", "userLogin", EnvUtil.getSystemUser(delegator)));
					success = true;
				}
			} catch(Exception e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "Error trying to find iparcel order: " + iparcelOrderNumber, module);
			}
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String createIParcelOrder(HttpServletRequest request, HttpServletResponse response, IPGetCheckoutDetails transactionDetail) {
		try {
			request.setAttribute("externalId", transactionDetail.getTransactionId());
			request.setAttribute("tempOrderId", transactionDetail.getTempOrderId());
			request.setAttribute("tempShippingAddress", transactionDetail.getShippingAddress());
			request.setAttribute("tempBillingAddress", transactionDetail.getBillingAddress());
			request.setAttribute("tempShippingPhone", transactionDetail.getNightPhone());
			request.setAttribute("tempBillingPhone", transactionDetail.getDayPhone());
			request.setAttribute("tempPaymentMethodTypeId", "EXT_IPARCEL");
			request.setAttribute("tempEmail", transactionDetail.getEmail());
			request.setAttribute("shipDetails", UtilMisc.<String, Object>toMap("shipMethod", "INTRNL_STD", "carrierPartyId", "INTERNATIONAL", "shipCost", transactionDetail.getShippingCost(), "duty", transactionDetail.getDuty(), "tax", transactionDetail.getTax()));
			request.setAttribute("promoDetails", transactionDetail.getPromoList());

			return Order.createOrder(request, response);
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to create order", module);
		}

		return "error";
	}

	/**
	 * Send the cart to iparcel.
	 * @param request
	 * @param response
	 * @return
	 */
	public static String iparcelCheckoutRequest(HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		Map<String, Object> jsonResponse = new HashMap<>();

		try {
			//create and save the cart that we are passing off first
			PersistentCart pCart = new PersistentCart(request, response, true, null);

			IPSetCheckoutRequest ipRequest = new IPSetCheckoutRequest(request);
			ipRequest.createRequest(request);
			ipRequest.setTempOrderId(pCart.getId());

			IPSetCheckoutResponse ipResponse = new IPSetCheckoutResponse();
			ipResponse.setResponse(HTTPHelper.getURL(SET_CHECKOUT_URL, "POST", null, null, ipRequest.getRequest(), null, false, EnvConstantsUtil.RESPONSE_JSON, EnvConstantsUtil.RESPONSE_JSON));

			if(ipResponse.isResponseValid()) {
				jsonResponse.put("transactionId", ipResponse.getTransactionId());
				jsonResponse.put("url", CART_URL + "?key=" + API_KEY + "&tx=" + ipResponse.getTransactionId());

				pCart.setCartTransId(ipResponse.getTransactionId());
				pCart.storeTempData(request);

				success = true;
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/**
	 * Cancel an iparcel order
	 * @param request
	 * @param response
	 * @return
	 */
	public static String iparcelCancelRequest(HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		Map<String, Object> jsonResponse = new HashMap<>();

		try {
			IPCancelRequest ipRequest = new IPCancelRequest(request);
			ipRequest.createRequest("");

			IPCancelResponse ipResponse = new IPCancelResponse();
			ipResponse.setResponse(HTTPHelper.getURL(CANCEL_TRANSACTION, "POST", null, null, ipRequest.getRequest(), null, false, EnvConstantsUtil.RESPONSE_JSON, EnvConstantsUtil.RESPONSE_JSON));

			if(ipResponse.isResponseValid() && ipResponse.isSuccess()) {
				//
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String buildURL(HttpServletRequest request, String path) {
		StringBuilder url = new StringBuilder();
		if(request.isSecure()) {
			url.append("https://");
		} else {
			url.append("http://");
		}

		url.append(request.getServerName());

		if(request.isSecure() && request.getServerPort() != 443) {
			url.append(":8443");
		} else if(!request.isSecure() && request.getServerPort() != 80) {
			url.append(":8080");
		}

		url.append(request.getAttribute("_CONTROL_PATH_")).append(path);

		return url.toString();
	}
}