/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.desk;

import java.io.*;
import java.net.*;
import java.lang.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.text.SimpleDateFormat;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.GeneralException;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilHttp;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntity;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityConditionList;
import org.apache.ofbiz.entity.condition.EntityExpr;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.transaction.GenericTransactionException;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.entity.util.EntityFindOptions;
import org.apache.ofbiz.entity.util.EntityListIterator;
import org.apache.ofbiz.entity.util.EntityTypeUtil;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.order.shoppingcart.ShoppingCart;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartEvents;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartHelper;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartItem;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;
import org.apache.ofbiz.shipment.shipment.ShipmentServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.envelopes.http.HTTPHelper;
import com.envelopes.util.*;

public class DeskEvents {
	public static final String module = DeskEvents.class.getName();

	private static String key;
	private static String secret;
	private static String token;
	private static String tokensecret;
	private static String url;
	private static String version;
	private static String useSecure;

	static {
		try{
			key = UtilProperties.getPropertyValue("envelopes", "desk.key");
			secret = UtilProperties.getPropertyValue("envelopes", "desk.secret");
			token = UtilProperties.getPropertyValue("envelopes", "desk.token");
			tokensecret = UtilProperties.getPropertyValue("envelopes", "desk.tokensecret");
			url = UtilProperties.getPropertyValue("envelopes", "desk.url");
			version = UtilProperties.getPropertyValue("envelopes", "desk.version");
			useSecure = UtilProperties.getPropertyValue("envelopes", "desk.useSecure");
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error, could not open: " + "envelopes.properties", module);
		}
	}

	/*
	 * Get case data from Desk.com
	 */
	public static String getCaseData(HttpServletRequest request, HttpServletResponse response) {
		boolean saveResponse = (UtilValidate.isEmpty(request.getAttribute("saveResponse"))) ? false : ((Boolean) request.getAttribute("saveResponse")).booleanValue();

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		response.setContentType(EnvConstantsUtil.RESPONSE_PLAIN);
		response.setCharacterEncoding(EnvConstantsUtil.ENV_CHAR_ENCODE);

		//context.put("subject", (String) context.get("orderId"));
		//context.put("case_custom_order_id", (String) context.get("orderId"));
		//context.put("description", (String) context.get("orderId"));

		Gson gson = new GsonBuilder().serializeNulls().create();

		Random r = new Random();
		int Low = 1001;
		int High = 9999;
		int R = r.nextInt(High-Low) + Low;

		Map<String, String> oAuthParameters = new HashMap<String, String>();
		oAuthParameters.put("oauth_version", "1.0");
		oAuthParameters.put("oauth_timestamp", UtilDateTime.nowAsString());
		oAuthParameters.put("oauth_nonce", UtilDateTime.nowAsString() + String.valueOf(R));
		oAuthParameters.put("oauth_signature_method", "PLAINTEXT");
		oAuthParameters.put("oauth_consumer_key", key);
		oAuthParameters.put("oauth_token", token);
		oAuthParameters.put("oauth_signature", secret + "&" + tokensecret);

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		String result = null;

		try {
			result = HTTPHelper.getURL(getUri("cases/search", context), "GET", oAuthParameters, null, null, null, false, EnvConstantsUtil.RESPONSE_JSON, EnvConstantsUtil.RESPONSE_PLAIN);
			//Map<String, Object> resultMap = gson.fromJson(result, Map.class);
		} catch(UnsupportedEncodingException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "There was an issue creating the URL", module);
		} catch(MalformedURLException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "There was an issue connecting to URL", module);
		} catch(IOException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "There was an issue connecting to URL", module);
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "There was an issue connecting to URL", module);
		}

		jsonResponse.put("result", gson.fromJson(result, HashMap.class));

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Build desk URI
	 */
	private static String getUri(String methodName, Map<String, Object> args) throws UnsupportedEncodingException {
		StringBuilder uri = new StringBuilder();

		uri.append("http");
		if(useSecure.equalsIgnoreCase("Y")) {
			uri.append("s");
		}

		uri.append("://").append(url).append("/").append(version).append("/").append(methodName);

		if(args != null) {
			uri.append("?");
			Iterator argsIter = args.entrySet().iterator();
			while (argsIter.hasNext()) {
				Map.Entry pairs = (Map.Entry)argsIter.next();
				uri.append(URLEncoder.encode((String) pairs.getKey(), EnvConstantsUtil.ENV_CHAR_ENCODE)).append("=").append(URLEncoder.encode((String) pairs.getValue(), EnvConstantsUtil.ENV_CHAR_ENCODE));
				if(argsIter.hasNext()) {
					uri.append("&");
				}
			}
		}
		return uri.toString();
	}
}