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
import java.util.Calendar;
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

import com.envelopes.order.OrderHelper;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.GeneralException;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilHttp;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntity;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.order.order.OrderReadHelper;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.amazonservices.mws.offamazonpayments.*;
import com.amazonservices.mws.offamazonpayments.model.*;

import com.envelopes.util.*;

public class AmazonPaymentEvents {
	public static final String module = AmazonPaymentEvents.class.getName();

	/*
	 * Notification endpoint
	 */
	public static String mwsIPN(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			InputStream inputStream = request.getInputStream();
			if(inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}

			//dispatcher.runSync("sendEmail", UtilMisc.toMap("email", "shoab@envelopes.com", "rawData", null, "data", UtilMisc.<String, String>toMap("subject", "AMAZON IPN", "request", stringBuilder.toString()), "messageType", "genericEmail"));
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to read amazon data", module);
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception ex) {
					EnvUtil.reportError(ex);
					Debug.logError(ex, "Error trying to read amazon data", module);
				}
			}
		}

		return "success";
	}

	public static String captureFunds(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		boolean success = false;

		try {
			Map<String, Object> result = dispatcher.runSync("captureOrderPayments", UtilMisc.toMap("orderId", (String) context.get("orderId"), "captureAmount", new BigDecimal((String) context.get("captureAmount")), "userLogin", (GenericValue) request.getSession().getAttribute("userLogin")));
			if(UtilValidate.isNotEmpty(result.get("captureResult")) && (Boolean) result.get("captureResult")) {
				success = true;
			}
		} catch (GenericServiceException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to capture funds.", module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String refundFunds(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		boolean success = false;

		try {
			GenericValue oPP = delegator.findOne("OrderPaymentPreference", UtilMisc.toMap("orderPaymentPreferenceId", (String) context.get("orderPaymentPreferenceId")), false);
			BigDecimal refundableAmount = OrderHelper.getRefundableAmount(delegator, oPP.getString("orderId"));

			if(new BigDecimal((String) context.get("refundAmount")).compareTo(refundableAmount) <= 0) {
				Map<String, Object> result = dispatcher.runSync("refundPayment", UtilMisc.toMap("orderPaymentPreference", oPP, "refundAmount", new BigDecimal((String) context.get("refundAmount")), "userLogin", (GenericValue) request.getSession().getAttribute("userLogin")));
				if(UtilValidate.isNotEmpty(result.get("responseMessage")) && "success".equalsIgnoreCase((String) result.get("responseMessage"))) {
					success = true;
				}
			} else {
				jsonResponse.put("error", "Refund amount exceeds remaining balance on order.");
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to capture funds.", module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}
}