/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.payments.mes;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mes.sdk.core.Settings;
import com.mes.sdk.exception.MesRuntimeException;
import com.mes.sdk.gateway.CcData;
import com.mes.sdk.gateway.Gateway;
import com.mes.sdk.gateway.GatewayRequest;
import com.mes.sdk.gateway.GatewayRequest.TransactionType;
import com.mes.sdk.gateway.GatewayResponse;
import com.mes.sdk.gateway.GatewaySettings;

import com.envelopes.util.*;

public class MESHelper {
	public static final String module = MESHelper.class.getName();

	private static Boolean sandbox;
	private static String url;
	private static boolean verbose;
	private static int transTimeout;

	static {
		try {
			sandbox = UtilProperties.getPropertyAsBoolean("envelopes", "mes.sandbox", true);
			url = UtilProperties.getPropertyValue("envelopes", (sandbox) ? "mes.sanbox.url" : "mes.url");
			verbose = (UtilValidate.isNotEmpty(UtilProperties.getPropertyValue("envelopes", "mes.verbose"))) ? Boolean.valueOf(UtilProperties.getPropertyValue("envelopes", "mes.verbose")).booleanValue() : false;
			transTimeout = (UtilValidate.isNotEmpty(UtilProperties.getPropertyValue("envelopes", "mes.transTimeout"))) ? Integer.valueOf(UtilProperties.getPropertyValue("envelopes", "mes.transTimeout")).intValue() : 5000;
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error, could not open: " + "envelopes.properties", module);
		}
	}

	private static String getName(String salesChannelEnumId) {
		String name = null;
		switch (salesChannelEnumId) {
			case "ENV_SALES_CHANNEL":
				name = UtilProperties.getPropertyValue("envelopes", "mes.env.name");
				break;
			case "AE_SALES_CHANNEL":
				name = UtilProperties.getPropertyValue("envelopes", "mes.ae.name");
				break;
			case "FOLD_SALES_CHANNEL":
				name = UtilProperties.getPropertyValue("envelopes", "mes.folders.name");
				break;
			case "BNAME_SALES_CHANNEL":
				name = UtilProperties.getPropertyValue("envelopes", "mes.env.name");
				break;
			case "BAGS_SALES_CHANNEL":
				name = UtilProperties.getPropertyValue("envelopes", "mes.env.name");
				break;
			default:
				name = UtilProperties.getPropertyValue("envelopes", "mes.env.name");
				break;
		}

		return name;
	}

	private static Gateway getGateway(String salesChannelEnumId) {
		String profileId = null;
		String key = null;

		switch (salesChannelEnumId) {
			case "ENV_SALES_CHANNEL":
				profileId = UtilProperties.getPropertyValue("envelopes", "mes.env.profileId");
				key = UtilProperties.getPropertyValue("envelopes", "mes.env.key");
				break;
			case "AE_SALES_CHANNEL":
				profileId = UtilProperties.getPropertyValue("envelopes", "mes.ae.profileId");
				key = UtilProperties.getPropertyValue("envelopes", "mes.ae.key");
				break;
			case "FOLD_SALES_CHANNEL":
				profileId = UtilProperties.getPropertyValue("envelopes", "mes.folders.profileId");
				key = UtilProperties.getPropertyValue("envelopes", "mes.folders.key");
				break;
			case "BNAME_SALES_CHANNEL":
				profileId = UtilProperties.getPropertyValue("envelopes", "mes.env.profileId");
				key = UtilProperties.getPropertyValue("envelopes", "mes.env.key");
				break;
			case "BAGS_SALES_CHANNEL":
				profileId = UtilProperties.getPropertyValue("envelopes", "mes.env.profileId");
				key = UtilProperties.getPropertyValue("envelopes", "mes.env.key");
				break;
			default:
				profileId = UtilProperties.getPropertyValue("envelopes", "mes.env.profileId");
				key = UtilProperties.getPropertyValue("envelopes", "mes.env.key");
				break;
		}

		return new Gateway(new GatewaySettings()
				.credentials(profileId, key)
				.hostUrl(url)
				.method(Settings.Method.POST)
				.timeout(transTimeout)
				.verbose(verbose));
	}

	/*
	 * Transaction SALE
	 */
	public static GatewayResponse sale(Map<String, Object> parameters) throws MesRuntimeException {
		GatewayRequest sRequest = new GatewayRequest(TransactionType.SALE)
			.cardData(
				new CcData()
				.setCcNum((String) parameters.get("cardNumber"))
				.setExpDate((String) parameters.get("expireDate"))
				.setCvv((String) parameters.get("cvv"))
			)
			.amount((BigDecimal) parameters.get("amount"))
			.setParameter("invoice_number", (String) parameters.get("orderId"))
			.setParameter("client_reference_number", getName((String) parameters.get("salesChannelEnumId")));

			if(parameters.containsKey("address1")) {
				sRequest.setParameter("cardholder_street_address", (String) parameters.get("address1"));
			}
			if(parameters.containsKey("postalCode")) {
				sRequest.setParameter("cardholder_zip", (String) parameters.get("postalCode"));
			}

		return getGateway((String) parameters.get("salesChannelEnumId")).run(sRequest);
	}

	/*
	 * Transaction PREAUTH
	 */
	public static GatewayResponse preAuth(Map<String, Object> parameters) throws MesRuntimeException {
		GatewayRequest sRequest = new GatewayRequest(TransactionType.PREAUTH)
			.cardData(
				new CcData()
				.setCcNum((String) parameters.get("cardNumber"))
				.setExpDate((String) parameters.get("expireDate"))
				.setCvv((String) parameters.get("cvv"))
			)
			.amount((BigDecimal) parameters.get("amount"))
			.setParameter("invoice_number", (String) parameters.get("orderId"))
			.setParameter("client_reference_number", getName((String) parameters.get("salesChannelEnumId")));

			if(parameters.containsKey("address1")) {
				sRequest.setParameter("cardholder_street_address", (String) parameters.get("address1"));
			}
			if(parameters.containsKey("postalCode")) {
				sRequest.setParameter("cardholder_zip", (String) parameters.get("postalCode"));
			}

		return getGateway((String) parameters.get("salesChannelEnumId")).run(sRequest);
	}

	/*
	 * Transaction REAUTH
	 */
	public static GatewayResponse reAuth(Map<String, Object> parameters) throws MesRuntimeException {
		GatewayRequest sRequest = new GatewayRequest(TransactionType.REAUTH)
			.amount((BigDecimal) parameters.get("amount"))
			.setParameter("transaction_id", (String) parameters.get("refNum"))
			.setParameter("invoice_number", (String) parameters.get("orderId"))
			.setParameter("client_reference_number", getName((String) parameters.get("salesChannelEnumId")));

		return getGateway((String) parameters.get("salesChannelEnumId")).run(sRequest);
	}

	/*
	 * Transaction SETTLE
	 */
	public static GatewayResponse settle(Map<String, Object> parameters) throws MesRuntimeException {
		GatewayRequest sRequest = new GatewayRequest(TransactionType.SETTLE)
			.amount((BigDecimal) parameters.get("amount"))
			.setParameter("transaction_id", (String) parameters.get("refNum"))
			.setParameter("invoice_number", (String) parameters.get("orderId"))
			.setParameter("client_reference_number", getName((String) parameters.get("salesChannelEnumId")));

		return getGateway((String) parameters.get("salesChannelEnumId")).run(sRequest);
	}

	/*
	 * Transaction VOID
	 */
	public static GatewayResponse voidAuth(Map<String, Object> parameters) throws MesRuntimeException {
		GatewayRequest sRequest = new GatewayRequest(TransactionType.VOID)
			.amount((BigDecimal) parameters.get("amount"))
			.setParameter("transaction_id", (String) parameters.get("refNum"))
			.setParameter("invoice_number", (String) parameters.get("orderId"))
			.setParameter("client_reference_number", getName((String) parameters.get("salesChannelEnumId")));

		return getGateway((String) parameters.get("salesChannelEnumId")).run(sRequest);
	}

	/*
	 * Transaction REFUND
	 */
	public static GatewayResponse refund(Map<String, Object> parameters) throws MesRuntimeException {
		GatewayRequest sRequest = new GatewayRequest(TransactionType.REFUND)
			.amount((BigDecimal) parameters.get("amount"))
			.setParameter("transaction_id", (String) parameters.get("refNum"))
			.setParameter("invoice_number", (String) parameters.get("orderId"))
			.setParameter("client_reference_number", getName((String) parameters.get("salesChannelEnumId")));

		return getGateway((String) parameters.get("salesChannelEnumId")).run(sRequest);
	}

	public static String testRequest(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("cardNumber", request.getParameter("cardNumber"));
		parameters.put("expireDate", "05/15");
		parameters.put("cvv", "123");
		parameters.put("orderId", request.getParameter("orderId"));
		parameters.put("amount", new BigDecimal("0.03"));
		parameters.put("address1", "5300 New Horizons Blvd");
		parameters.put("postalCode", "11701");

		try {
			GatewayResponse gResponse = sale(parameters);
			Debug.logInfo("gResponse: " + gResponse, module);
		} catch (MesRuntimeException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error processing MES payment. " + e + " : " + e.getMessage(), module);
			return "error";
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error processing MES payment. " + e + " : " + e.getMessage(), module);
			return "error";
		}

		return "success";
	}
}