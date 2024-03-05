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

import com.envelopes.http.HTTPHelper;
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

import com.envelopes.party.PartyHelper;
import com.envelopes.util.*;

public class SocialLoginHelper {
	public static final String module = SocialLoginHelper.class.getName();

	public static String API_DOMAIN;
	public static String API_ID;
	public static String API_KEY;
	public static String API_VERSION;

	static {
		try{
			API_DOMAIN = UtilProperties.getPropertyValue("envelopes", "janrain.domain");
			API_ID = UtilProperties.getPropertyValue("envelopes", "janrain.api.id");
			API_KEY = UtilProperties.getPropertyValue("envelopes", "janrain.api.key");
			API_VERSION = UtilProperties.getPropertyValue("envelopes", "janrain.version");
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error, could not open: " + "envelopes.properties", module);
		}
	}

	/*
	 * Do data check on user email to see if it exsts, if not create it, if so associate the user
	 */
	public static Map<String, GenericValue> getUserLogin(HttpServletRequest request, Map<String, String> context) throws GenericEntityException, GenericServiceException {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		return getUserLogin(delegator, dispatcher, context);
	}

	public static Map<String, GenericValue> getUserLogin(Delegator delegator, LocalDispatcher dispatcher, Map<String, String> context) throws GenericEntityException, GenericServiceException {
		Map<String, GenericValue> loginData = new HashMap<>();

		GenericValue userLogin = null;
		GenericValue externalUserLogin = getExternalLogin(delegator, context);
		if(UtilValidate.isEmpty(externalUserLogin)) {
			boolean isEmailActive = PartyHelper.isEmailActive(delegator, context.get("emailAddress"));
			if(isEmailActive) {
				userLogin = PartyHelper.getUserLogin(delegator, context.get("emailAddress"));
			} else {
				userLogin = PartyHelper.createAccount(delegator, dispatcher, context);
				dispatcher.runSync("addUserLoginToSecurityGroup", UtilMisc.toMap("userLogin", EnvUtil.getSystemUser(delegator), "userLoginId", context.get("emailAddress"), "groupId", "CONTENT_USER", "fromDate", UtilDateTime.nowTimestamp()));
				if(UtilValidate.isNotEmpty(context.get("emailAddress")) && (context.get("emailAddress").indexOf("@envelopes.com") != -1 || context.get("emailAddress").indexOf("@folders.com") != -1 || context.get("emailAddress").indexOf("@bigname.com") != -1)) {
					dispatcher.runSync("addUserLoginToSecurityGroup", UtilMisc.toMap("userLogin", EnvUtil.getSystemUser(delegator), "userLoginId", context.get("emailAddress"), "groupId", "ORDERADMIN", "fromDate", UtilDateTime.nowTimestamp()));
				}
			}

			if(UtilValidate.isNotEmpty(userLogin)) {
				externalUserLogin = createExternalLogin(delegator, userLogin, context);
			}
		} else {
			userLogin = PartyHelper.getUserLogin(delegator, context.get("emailAddress"));
		}

		loginData.put("userLogin", userLogin);
		loginData.put("externalUserLogin", externalUserLogin);

		return loginData;
	}

	/*
	 * Check if the user has logged in before with their external login
	 */
	public static GenericValue getExternalLogin(Delegator delegator, Map<String, String> context) throws GenericEntityException {
		GenericValue externalLogin = delegator.findOne("ExternalUserLogin", UtilMisc.toMap("id", context.get("emailAddress"), "provider", context.get("providerName")), false);
		return externalLogin;
	}

	/*
	 * Create the external entry
	 */
	public static GenericValue createExternalLogin(Delegator delegator, GenericValue userLogin, Map<String, String> context) throws GenericEntityException {
		GenericValue externalLogin = delegator.makeValue("ExternalUserLogin", UtilMisc.toMap("id", context.get("emailAddress"), "provider", context.get("providerName")));
		externalLogin.put("gender", context.get("gender"));
		externalLogin.put("photo", context.get("photo"));
		externalLogin.put("partyId", userLogin.getString("partyId"));
		externalLogin.put("identifier", context.get("identifier"));
		externalLogin.put("url", context.get("url"));
		externalLogin.put("enabled", "Y");

		delegator.create(externalLogin);
		return externalLogin;
	}

	/*
	 * Get the social context data from janrain
	 */
	public static Map<String, String> getAuthInfo(Map<String, String> args) throws Exception {
		Map<String, String> socialContext = new HashMap<String, String>();
		String result = null;

		result = HTTPHelper.getURL(SocialLoginEvents.getUri("auth_info", args), "GET", null, null, null, null, false, EnvConstantsUtil.RESPONSE_JSON, EnvConstantsUtil.RESPONSE_PLAIN);

		if(UtilValidate.isNotEmpty(result)) {
			HashMap<String, Object> jsonMap = new Gson().fromJson(result, HashMap.class);
			if(jsonMap.get("stat") != null && jsonMap.get("stat").equals("ok")) {
				Map<String, Object> jsonData = (Map<String, Object>) jsonMap.get("profile");
				if(UtilValidate.isNotEmpty(jsonData.get("verifiedEmail")) && jsonData.get("verifiedEmail") instanceof String && ((String) jsonData.get("verifiedEmail")).contains("@")) {
					socialContext.put("emailAddress", (String) jsonData.get("verifiedEmail"));
				} else if(UtilValidate.isNotEmpty(jsonData.get("email"))) {
					socialContext.put("emailAddress", (String) jsonData.get("email"));
				}

				if(UtilValidate.isNotEmpty(jsonData.get("identifier"))) {
					socialContext.put("identifier", (String) jsonData.get("identifier"));
				}
				if(UtilValidate.isNotEmpty(jsonData.get("providerName"))) {
					socialContext.put("providerName", (String) jsonData.get("providerName"));
				}
				if(UtilValidate.isNotEmpty(jsonData.get("url"))) {
					socialContext.put("url", (String) jsonData.get("url"));
				}
				if(UtilValidate.isNotEmpty(jsonData.get("photo"))) {
					socialContext.put("photo", (String) jsonData.get("photo"));
				}
				if(UtilValidate.isNotEmpty(jsonData.get("gender"))) {
					socialContext.put("gender", (String) jsonData.get("gender"));
				}
				if(UtilValidate.isNotEmpty(jsonData.get("name")) && UtilValidate.isNotEmpty(((Map) jsonData.get("name")).get("formatted"))) {
					String name = null;
					if(((Map) jsonData.get("name")).get("formatted") instanceof String) {
						name = (String) ((Map) jsonData.get("name")).get("formatted");
					} else if(((Map) jsonData.get("name")).get("formatted") instanceof Map) {
						name = (String) ((Map) ((Map) jsonData.get("name")).get("formatted")).get("formatted");
					}
					if (name.indexOf(" ") != -1) {
						socialContext.put("firstName", name.substring(0, name.indexOf(" ")));
						socialContext.put("lastName", name.substring(name.indexOf(" ") + 1));
					} else {
						socialContext.put("firstName", name);
					}
				}
			}
		}

		return socialContext;
	}
}