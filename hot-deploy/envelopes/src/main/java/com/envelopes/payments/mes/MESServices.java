/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.payments.mes;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.envelopes.order.OrderHelper;
import org.apache.ofbiz.accounting.payment.PaymentGatewayServices;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.ServiceUtil;

import com.mes.sdk.exception.MesRuntimeException;
import com.mes.sdk.gateway.GatewayResponse;

import com.envelopes.util.*;

public class MESServices {
	public static final String module = MESServices.class.getName();

	/**
	 * Authorize credit card payment service. Service wrapper around Merchant e-Solutions API.
	 * @param dctx Service Engine DispatchContext.
	 * @param context Map context of parameters.
	 * @return Response map, including RESPMSG, and RESULT keys.
	 */
	public static Map<String, Object> ccSale(DispatchContext dctx, Map<String, ? extends Object> context) {
		GenericValue paymentPref = (GenericValue) context.get("orderPaymentPreference");
		String orderId = (String) context.get("orderId");
		String salesChannelEnumId = (String) context.get("salesChannelEnumId");
		String cvv2 = (String) context.get("cardSecurityCode");
		BigDecimal processAmount = (BigDecimal) context.get("processAmount");
		GenericValue party = (GenericValue) context.get("billToParty");
		GenericValue cc = (GenericValue) context.get("creditCard");
		GenericValue ps = (GenericValue) context.get("billingAddress");
		String configString = (String) context.get("paymentConfig");

		if(configString == null) {
			configString = "envelopes.properties";
		}

		boolean isReAuth = false;

		String postalCode = ps.getString("postalCode");
		if(postalCode.contains("-")) {
			postalCode = postalCode.substring(0, postalCode.indexOf("-"));
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("cardNumber", cc.getString("cardNumber"));
		parameters.put("expireDate", cc.getString("expireDate"));
		parameters.put("cvv", cvv2);
		parameters.put("orderId", orderId);
		parameters.put("amount", processAmount);
		parameters.put("address1", ps.getString("address1"));
		parameters.put("postalCode", postalCode);
		parameters.put("salesChannelEnumId", salesChannelEnumId);

		//reauths should not be use to reauth transaction after expiration, MES reauth is meant to be used when a card is declined

		GatewayResponse gResponse = null;
		try {
			gResponse = MESHelper.sale(parameters);
		} catch (MesRuntimeException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error processing MES payment. " + e + " : " + e.getMessage(), module);
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error processing MES payment. " + e + " : " + e.getMessage(), module);
		}

		Map<String, Object> result = ServiceUtil.returnSuccess();
		parseAuthResponse(gResponse, result, configString, isReAuth);
		result.put("processAmount", processAmount);
		return result;
	}

	/**
	 * Authorize credit card payment service. Service wrapper around Merchant e-Solutions API.
	 * @param dctx Service Engine DispatchContext.
	 * @param context Map context of parameters.
	 * @return Response map, including RESPMSG, and RESULT keys.
	 */
	public static Map<String, Object> ccProcessor(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		GenericValue paymentPref = (GenericValue) context.get("orderPaymentPreference");
		String orderId = (String) context.get("orderId");
		String salesChannelEnumId = (String) context.get("salesChannelEnumId");
		String cvv2 = (String) context.get("cardSecurityCode");
		BigDecimal processAmount = (BigDecimal) context.get("processAmount");
		GenericValue party = (GenericValue) context.get("billToParty");
		GenericValue cc = (GenericValue) context.get("creditCard");
		GenericValue ps = (GenericValue) context.get("billingAddress");
		String configString = (String) context.get("paymentConfig");

		if(UtilValidate.isEmpty(salesChannelEnumId)) {
			try {
				salesChannelEnumId = EntityQuery.use(delegator).from("OrderHeader").where("orderId", paymentPref.getString("orderId")).queryOne().getString("salesChannelEnumId");
			} catch(Exception e) {
				EnvUtil.reportError(e);
			}
		}

		if(configString == null) {
			configString = "envelopes.properties";
		}

		boolean isReAuth = false;

		String postalCode = ps.getString("postalCode");
		if(postalCode.contains("-")) {
			postalCode = postalCode.substring(0, postalCode.indexOf("-"));
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("cardNumber", cc.getString("cardNumber"));
		parameters.put("expireDate", cc.getString("expireDate"));
		parameters.put("cvv", cvv2);
		parameters.put("orderId", orderId);
		parameters.put("amount", processAmount);
		parameters.put("address1", ps.getString("address1"));
		parameters.put("postalCode", postalCode);
		parameters.put("salesChannelEnumId", salesChannelEnumId);

		//reauths should not be use to reauth transaction after expiration, MES reauth is meant to be used when a card is declined
		Map<String, String> saveRequest = new HashMap<>();
		saveRequest.put("orderId", orderId);
		saveRequest.put("orderPaymentPreferenceId", paymentPref.getString("orderPaymentPreferenceId"));
		saveRequest.put("cardNumber", OrderHelper.formatCreditCard(cc));

		GatewayResponse gResponse = null;
		try {
			gResponse = MESHelper.preAuth(parameters);
		} catch (MesRuntimeException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error processing MES payment. " + e + " : " + e.getMessage(), module);
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error processing MES payment. " + e + " : " + e.getMessage(), module);
		}

		Map<String, Object> result = ServiceUtil.returnSuccess();
		parseAuthResponse(gResponse, result, configString, isReAuth);
		result.put("processAmount", processAmount);
		result.put("requestMsg", EnvUtil.convertMapToString(saveRequest));
		return result;
	}

	public static Map<String, Object> ccCapture(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		String salesChannelEnumId = (String) context.get("salesChannelEnumId");
		GenericValue paymentPref = (GenericValue) context.get("orderPaymentPreference");
		GenericValue authTrans = (GenericValue) context.get("authTrans");
		BigDecimal amount = (BigDecimal) context.get("captureAmount");
		String configString = (String) context.get("paymentConfig");

		if(UtilValidate.isEmpty(salesChannelEnumId)) {
			try {
				salesChannelEnumId = EntityQuery.use(delegator).from("OrderHeader").where("orderId", paymentPref.getString("orderId")).queryOne().getString("salesChannelEnumId");
			} catch(Exception e) {
				EnvUtil.reportError(e);
			}
		}

		if(configString == null) {
			configString = "envelopes.properties";
		}

		if(authTrans == null){
			authTrans = PaymentGatewayServices.getAuthTransaction(paymentPref);
		}

		if(authTrans == null) {
			return ServiceUtil.returnError("No authorization transaction found for the OrderPaymentPreference; cannot capture");
		}

		// auth ref number
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("refNum", authTrans.getString("referenceNum"));
		parameters.put("orderId", paymentPref.getString("orderId"));
		parameters.put("amount", amount);
		parameters.put("salesChannelEnumId", salesChannelEnumId);

		GatewayResponse gResponse = null;
		try {
			gResponse = MESHelper.settle(parameters);
		} catch (MesRuntimeException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error processing MES payment. " + e + " : " + e.getMessage(), module);
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error processing MES payment. " + e + " : " + e.getMessage(), module);
		}

		// check the response
		Map<String, Object> result = ServiceUtil.returnSuccess();
		parseCaptureResponse(gResponse, result, configString);
		result.put("captureAmount", amount);
		return result;
	}

	public static Map<String, Object> ccRefund(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		String salesChannelEnumId = (String) context.get("salesChannelEnumId");
		GenericValue paymentPref = (GenericValue) context.get("orderPaymentPreference");
		BigDecimal amount = (BigDecimal) context.get("refundAmount");
		String configString = (String) context.get("paymentConfig");

		if(UtilValidate.isEmpty(salesChannelEnumId)) {
			try {
				salesChannelEnumId = EntityQuery.use(delegator).from("OrderHeader").where("orderId", paymentPref.getString("orderId")).queryOne().getString("salesChannelEnumId");
			} catch(Exception e) {
				EnvUtil.reportError(e);
			}
		}

		if(configString == null) {
			configString = "envelopes.properties";
		}

		GenericValue captureTrans = PaymentGatewayServices.getCaptureTransaction(paymentPref);

		if(captureTrans == null) {
			return ServiceUtil.returnError("No capture transaction found for the OrderPaymentPreference; cannot refund");
		}

		// auth ref number
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("refNum", captureTrans.getString("referenceNum"));
		parameters.put("orderId", paymentPref.getString("orderId"));
		parameters.put("amount", amount);
		parameters.put("salesChannelEnumId", salesChannelEnumId);

		GatewayResponse gResponse = null;
		try {
			gResponse = MESHelper.refund(parameters);
		} catch (MesRuntimeException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error processing MES payment. " + e + " : " + e.getMessage(), module);
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error processing MES payment. " + e + " : " + e.getMessage(), module);
		}

		// check the response
		Map<String, Object> result = ServiceUtil.returnSuccess();
		parseRefundResponse(gResponse, result, configString);
		result.put("refundAmount", amount);
		return result;
	}

	public static Map<String, Object> ccVoid(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		String salesChannelEnumId = (String) context.get("salesChannelEnumId");
		GenericValue paymentPref = (GenericValue) context.get("orderPaymentPreference");
		GenericValue authTrans = (GenericValue) context.get("authTrans");
		BigDecimal amount = (BigDecimal) context.get("releaseAmount");
		String configString = (String) context.get("paymentConfig");

		if(UtilValidate.isEmpty(salesChannelEnumId)) {
			try {
				salesChannelEnumId = EntityQuery.use(delegator).from("OrderHeader").where("orderId", paymentPref.getString("orderId")).queryOne().getString("salesChannelEnumId");
			} catch(Exception e) {
				EnvUtil.reportError(e);
			}
		}

		if(configString == null) {
			configString = "envelopes.properties";
		}

		if(authTrans == null){
			authTrans = PaymentGatewayServices.getAuthTransaction(paymentPref);
		}

		if(authTrans == null) {
			return ServiceUtil.returnError("No authorization transaction found for the OrderPaymentPreference; cannot void");
		}

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("refNum", authTrans.getString("referenceNum"));
		parameters.put("orderId", paymentPref.getString("orderId"));
		parameters.put("amount", amount);
		parameters.put("salesChannelEnumId", salesChannelEnumId);

		GatewayResponse gResponse = null;
		try {
			gResponse = MESHelper.voidAuth(parameters);
		} catch (MesRuntimeException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error processing MES payment. " + e + " : " + e.getMessage(), module);
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error processing MES payment. " + e + " : " + e.getMessage(), module);
		}

		// check the response
		Map<String, Object> result = ServiceUtil.returnSuccess();
		parseVoidResponse(gResponse, result, configString);
		result.put("releaseAmount", amount);
		return result;
	}

	private static void parseAuthResponse(GatewayResponse gResponse, Map<String, Object> result, String resource, boolean isReAuth) {
		//Debug.logInfo("MeS response string: " + gResponse.toString(), module);

		if(UtilValidate.isEmpty(gResponse)) {
			result.put("authResult", Boolean.FALSE);
			result.put("resultDeclined", Boolean.TRUE);
			result.put("authRefNum", "");
			result.put("authMessage", "Invalid Credit Card Number");
		} else {
			if("000".equals(gResponse.getErrorCode()) && gResponse.isApproved()) {
				result.put("authResult", Boolean.TRUE);
				result.put("authCode", gResponse.getResponseValue("auth_code"));
			} else {
				result.put("authResult", Boolean.FALSE);
				// now check certain special conditions and report back through the generic params
				if("051".equals(gResponse.getErrorCode())) {
					result.put("resultNsf", Boolean.TRUE);
				} else if("014".equals(gResponse.getErrorCode()) || "015".equals(gResponse.getErrorCode())) {
					result.put("resultBadCardNumber", Boolean.TRUE);
				} else if("054".equals(gResponse.getErrorCode())) {
					result.put("resultBadExpire", Boolean.TRUE);
				} else {
					result.put("resultDeclined", Boolean.TRUE);
				}
			}

			result.put("cvCode", gResponse.getResponseValue("cvv2_result"));
			result.put("avsCode", gResponse.getResponseValue("avs_result"));
			result.put("authRefNum", gResponse.getTransactionId());
			result.put("authMessage", gResponse.getAuthResponse());
		}
	}

	private static void parseCaptureResponse(GatewayResponse gResponse, Map<String, Object> result, String resource) {
		//Debug.logInfo("MeS response string: " + gResponse.toString(), module);

		if(UtilValidate.isEmpty(gResponse)) {
			result.put("captureResult", Boolean.FALSE);
		} else {
			if("000".equals(gResponse.getErrorCode()) && gResponse.isApproved()) {
				result.put("captureResult", Boolean.TRUE);
			} else {
				result.put("captureResult", Boolean.FALSE);
			}
			result.put("captureCode", gResponse.getResponseValue("auth_code"));
			result.put("captureRefNum", gResponse.getTransactionId());
			result.put("captureMessage", gResponse.getAuthResponse());
		}
	}

	private static void parseRefundResponse(GatewayResponse gResponse, Map<String, Object> result, String resource) {
		//Debug.logInfo("MeS response string: " + gResponse.toString(), module);

		if(UtilValidate.isEmpty(gResponse)) {
			result.put("refundResult", Boolean.FALSE);
		} else {
			if("000".equals(gResponse.getErrorCode()) && gResponse.isApproved()) {
				result.put("refundResult", Boolean.TRUE);
			} else {
				result.put("refundResult", Boolean.FALSE);
			}
			result.put("refundCode", gResponse.getResponseValue("auth_code"));
			result.put("refundRefNum", gResponse.getTransactionId());
			result.put("refundMessage", gResponse.getAuthResponse());
		}
	}

	private static void parseVoidResponse(GatewayResponse gResponse, Map<String, Object> result, String resource) {
		//Debug.logInfo("MeS response string: " + gResponse.toString(), module);

		if(UtilValidate.isEmpty(gResponse)) {
			result.put("releaseResult", Boolean.FALSE);
		} else {
			if("000".equals(gResponse.getErrorCode()) && gResponse.isApproved()) {
				result.put("releaseResult", Boolean.TRUE);
			} else {
				result.put("releaseResult", Boolean.FALSE);
			}
			result.put("releaseCode", gResponse.getResponseValue("auth_code"));
			result.put("releaseRefNum", gResponse.getTransactionId());
			result.put("releaseMessage", gResponse.getAuthResponse());
		}
	}
}