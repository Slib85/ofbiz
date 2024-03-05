/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.janrain;

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

import com.envelopes.login.EnvLoginEvents;
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
import org.apache.ofbiz.webapp.control.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.envelopes.http.HTTPHelper;
import com.envelopes.util.*;

public class SocialLoginEvents {
	public static final String module = SocialLoginEvents.class.getName();

	/*
	 * Get the access token after login and store in session
	 */
	public static String getAccessToken(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String token = request.getParameter("token");
		session.setAttribute("token", token);

		getAuthInfo(request, response);

		return "success";
	}

	/*
	 * Get the authorization info for user
	 */
	public static String getAuthInfo(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);

		String result = null;

		String token = (String) session.getAttribute("token");

		Map<String, String> args = new HashMap<String, String>();
		args.put("apiKey", SocialLoginHelper.API_KEY);
		args.put("token", token);

		Map<String, String> socialContext = new HashMap<>();
		try {
			socialContext = SocialLoginHelper.getAuthInfo(args);
		} catch(Exception e) {
			EnvUtil.reportError(e);
		}

		return "success";
	}

	/*
	 * Get the app settings for JanRain Engage
	 */
	public static String getAppSettings(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		response.setContentType(EnvConstantsUtil.RESPONSE_PLAIN);
		response.setCharacterEncoding(EnvConstantsUtil.ENV_CHAR_ENCODE);

		PrintWriter output = null;
		Gson gson = new GsonBuilder().serializeNulls().create();

		String result = null;

		Map<String, String> args = new HashMap<String, String>();
		args.put("apiKey", SocialLoginHelper.API_KEY);

		try {
			result = HTTPHelper.getURL(getUri("get_app_settings", args), "GET", null, null, null, null, false, EnvConstantsUtil.RESPONSE_JSON, EnvConstantsUtil.RESPONSE_PLAIN);
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "There was an issue connecting to URL", module);
		}

		try {
			output = response.getWriter();
			output.print(result);
			output.flush();
		} catch (IOException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
			return "error";
		} finally {
			if(output != null) {
				try {
					output.close();
				} catch (Exception fe) {
					return "error";
				}
			}
		}

		return "success";
	}

	/*
	 * Get the available configured providers for JanRain Engage
	 */
	public static String getAvailableProviders(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		response.setContentType(EnvConstantsUtil.RESPONSE_PLAIN);
		response.setCharacterEncoding(EnvConstantsUtil.ENV_CHAR_ENCODE);

		PrintWriter output = null;
		Gson gson = new GsonBuilder().serializeNulls().create();

		String result = null;

		try {
			result = HTTPHelper.getURL(getUri("get_available_providers", null), "GET", null, null, null, null, false, EnvConstantsUtil.RESPONSE_JSON, EnvConstantsUtil.RESPONSE_PLAIN);
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "There was an issue connecting to URL", module);
		}

		try {
			output = response.getWriter();
			output.print(result);
			output.flush();
		} catch (IOException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
			return "error";
		} finally {
			if(output != null) {
				try {
					output.close();
				} catch (Exception fe) {
					return "error";
				}
			}
		}

		return "success";
	}

	/*
	 * Build URI
	 */
	protected static String getUri(String methodName, Map<String, String> args) throws UnsupportedEncodingException {
		StringBuilder uri = new StringBuilder(SocialLoginHelper.API_DOMAIN);
		uri.append("api/").append(SocialLoginHelper.API_VERSION).append("/").append(methodName);

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