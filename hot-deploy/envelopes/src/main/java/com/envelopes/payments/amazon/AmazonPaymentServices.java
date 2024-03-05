/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.payments.amazon;

import java.io.*;
import java.net.*;
import java.lang.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.text.SimpleDateFormat;

import org.apache.ofbiz.base.util.Debug;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.ofbiz.accounting.payment.PaymentGatewayServices;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.StringUtil;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import com.amazonservices.mws.offamazonpayments.*;
import com.amazonservices.mws.offamazonpayments.model.*;

import com.envelopes.util.*;

public class AmazonPaymentServices {
	public static final String module = AmazonPaymentServices.class.getName();

	/**
	 * Authorize Amazon Payments
	 */
	public static Map<String, Object> ccProcessor(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		GenericValue paymentPref = (GenericValue) context.get("orderPaymentPreference");
		GenericValue paymentMethod = (GenericValue) context.get("amazonPaymentMethod");
		String orderId = (String) context.get("orderId");
		BigDecimal processAmount = (BigDecimal) context.get("processAmount");
		String configString = (String) context.get("paymentConfig");

		if(configString == null) {
			configString = "envelopes.properties";
		}

		Map<String, Object> result = ServiceUtil.returnSuccess();
		result.put("processAmount", processAmount);

		AuthorizeResponse mwsResponse = null;
		try {
			mwsResponse = AmazonPaymentHelper.setAuthorizeResponse(paymentMethod.getString("amazonOrderId"), paymentPref.getString("paymentMethodId"), processAmount.toString());

			AuthorizeResult authorizeResult = mwsResponse.getAuthorizeResult();
			if(authorizeResult.isSetAuthorizationDetails()) {
				AuthorizationDetails authorizationDetails = authorizeResult.getAuthorizationDetails();
				result.put("authResult", Boolean.TRUE);
				result.put("authCode", authorizationDetails.getAmazonAuthorizationId());
				result.put("authRefNum", authorizationDetails.getAuthorizationReferenceId());
			} else {
				result.put("authRefNum", "ERROR_DURING_AUTH");
				result.put("authResult", Boolean.FALSE);
				result.put("resultDeclined", Boolean.TRUE);
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error processing Amazon payment. " + e + " : " + e.getMessage(), module);
		}

		return result;
	}

	public static Map<String, Object> ccCapture(DispatchContext dctx, Map<String, ? extends Object> context) {
		LocalDispatcher dispatcher = dctx.getDispatcher();
		Delegator delegator = dctx.getDelegator();
		GenericValue paymentPref = (GenericValue) context.get("orderPaymentPreference");
		GenericValue authTrans = (GenericValue) context.get("authTrans");
		BigDecimal amount = (BigDecimal) context.get("captureAmount");
		String configString = (String) context.get("paymentConfig");

		if(configString == null) {
			configString = "envelopes.properties";
		}

		if(authTrans == null) {
			authTrans = PaymentGatewayServices.getAuthTransaction(paymentPref);
		}

		if(authTrans == null) {
			return ServiceUtil.returnError("No authorization transaction found for the OrderPaymentPreference; cannot capture");
		}

		// check the response
		Map<String, Object> result = ServiceUtil.returnSuccess();
		result.put("captureAmount", amount);
		result.put("captureMessage", null);

		CaptureResponse mwsResponse = null;
		try {
			mwsResponse = AmazonPaymentHelper.setCaptureResponse(authTrans.getString("gatewayCode"), paymentPref.getString("paymentMethodId"), amount.toPlainString());

			CaptureResult captureResult = mwsResponse.getCaptureResult();
			if(captureResult.isSetCaptureDetails()) {
				CaptureDetails captureDetails = captureResult.getCaptureDetails();
				result.put("captureResult", Boolean.TRUE);
				result.put("captureCode", captureDetails.getAmazonCaptureId());
				result.put("captureRefNum", captureDetails.getCaptureReferenceId());
				dispatcher.runSync("changeOrderStatus", UtilMisc.toMap("orderId", paymentPref.getString("orderId"), "statusId", "ORDER_CREATED", "userLogin", EnvUtil.getSystemUser(delegator)));
			} else {
				result.put("captureResult", Boolean.FALSE);
				result.put("captureRefNum", "ERROR_DURING_CAPTURE");
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error processing Amazon payment. " + e + " : " + e.getMessage(), module);
		}
		return result;
	}

	public static Map<String, Object> ccRefund(DispatchContext dctx, Map<String, ? extends Object> context) {
		GenericValue paymentPref = (GenericValue) context.get("orderPaymentPreference");
		BigDecimal amount = (BigDecimal) context.get("refundAmount");
		String configString = (String) context.get("paymentConfig");

		if(configString == null) {
			configString = "envelopes.properties";
		}

		GenericValue captureTrans = PaymentGatewayServices.getCaptureTransaction(paymentPref);

		if(captureTrans == null) {
            return ServiceUtil.returnError("No capture transaction found for the OrderPaymentPreference; cannot refund");
        }

		// check the response
		Map<String, Object> result = ServiceUtil.returnSuccess();
		result.put("refundAmount", amount);

		RefundResponse mwsResponse = null;
		try {
			mwsResponse = AmazonPaymentHelper.setRefundResponse(paymentPref.getString("orderPaymentPreferenceId"), captureTrans.getString("gatewayCode"), amount.toString(), null);

			RefundResult refundResult = mwsResponse.getRefundResult();
			if(refundResult.isSetRefundDetails()) {
				RefundDetails refundDetails = refundResult.getRefundDetails();
				result.put("refundResult", Boolean.TRUE);
				result.put("refundCode", refundDetails.getRefundReferenceId());
				result.put("refundRefNum", refundDetails.getRefundReferenceId());
				result.put("refundMessage", refundDetails.getRefundStatus().getReasonDescription());
			} else {
				result.put("refundResult", Boolean.FALSE);
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error processing Amazon payment. " + e + " : " + e.getMessage(), module);
		}

		return result;
	}

	public static Map<String, Object> ccClose(DispatchContext dctx, Map<String, ? extends Object> context) {
		GenericValue paymentMethod = (GenericValue) context.get("amazonPaymentMethod");
		GenericValue orderHeader = (GenericValue) context.get("orderHeader");
		String amazonOrderId = (String) context.get("amazonOrderId");
		String reason = (String) context.get("reason");

		String amazonOrderReferenceId = null;
		if(UtilValidate.isNotEmpty(amazonOrderId)) {
			amazonOrderReferenceId = amazonOrderId;
		} else if(UtilValidate.isNotEmpty(paymentMethod)) {
			amazonOrderReferenceId = paymentMethod.getString("amazonOrderId");
		} else if(UtilValidate.isNotEmpty(orderHeader)) {
			amazonOrderReferenceId = orderHeader.getString("externalId");
		}

		// check the response
		Map<String, Object> result = ServiceUtil.returnSuccess();

		CloseOrderReferenceResponse mwsResponse = null;
		try {
			mwsResponse = AmazonPaymentHelper.setCloseOrderReferenceResponse(amazonOrderReferenceId, reason);
			if(!mwsResponse.isSetResponseMetadata()) {
				return ServiceUtil.returnError("Error closing amazon order: [" + amazonOrderReferenceId + "]");
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error processing Amazon payment. " + e + " : " + e.getMessage(), module);
			return ServiceUtil.returnError("Error closing amazon order: [" + amazonOrderReferenceId + "]");
		}

		return result;
	}

	public static Map<String, Object> ccVoid(DispatchContext dctx, Map<String, ? extends Object> context) {
		// check the response
		Map<String, Object> result = ServiceUtil.returnSuccess();
		result.put("releaseAmount", "amount");
		return result;
	}
}