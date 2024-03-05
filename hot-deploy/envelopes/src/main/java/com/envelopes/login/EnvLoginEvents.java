/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.login;

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
import java.util.Properties;

import com.envelopes.bronto.BrontoUtil;
import com.envelopes.cart.PersistentCart;
import com.envelopes.janrain.SocialLoginHelper;
import com.envelopes.session.SessionEvents;
import com.envelopes.session.SessionHelper;
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
import org.apache.ofbiz.entity.util.EntityFindOptions;
import org.apache.ofbiz.entity.util.EntityListIterator;
import org.apache.ofbiz.entity.util.EntityTypeUtil;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.order.shoppingcart.ShoppingCart;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartEvents;
import org.apache.ofbiz.securityext.login.LoginEvents;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;
import org.apache.ofbiz.webapp.control.LoginWorker;
import org.apache.ofbiz.webapp.control.RequestHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.envelopes.party.PartyHelper;
import com.envelopes.util.*;

public class EnvLoginEvents {
	public static final String module = EnvLoginEvents.class.getName();

	//get the user login
	public static String getUserLogin(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", true);

		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		if(userLogin == null) {
			jsonResponse.put("userLogin", null);
		} else {
			jsonResponse.put("userLogin", userLogin.getString("userLoginId"));
			jsonResponse.put("partyId", userLogin.getString("partyId"));
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String isUserLoggedIn(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", false);
		/*if(Boolean.valueOf(request.getParameter("reset"))) {
			request.getSession().removeAttribute("loginError");
		}*/
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		if(userLogin != null) {
			jsonResponse.put("partyId", userLogin.getString("partyId"));
			jsonResponse.put("userLogin", userLogin.getString("userLoginId"));
			GenericValue userAttributes = (GenericValue) request.getSession().getAttribute("person");
			jsonResponse.put("firstName", UtilValidate.isNotEmpty(userAttributes.containsKey("firstName")) ? userAttributes.getString("firstName") : "");
			jsonResponse.put("lastName", UtilValidate.isNotEmpty(userAttributes.containsKey("lastName")) ? userAttributes.getString("lastName") : "");
			jsonResponse.put("success", true);
		}/* else if(UtilValidate.isNotEmpty(request.getSession().getAttribute("loginError"))) {
			jsonResponse.put("loginError", true);
		}*/

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	//login a user
	public static String loginUser(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		Map<String, String> errors = new HashMap<>();
		Map<String, String> messages = new HashMap<>();
		Map<String, Object> parameters = new HashMap<>();
		boolean success = false;
		String username = null;
		String password = null;
		if(!request.getMethod().equalsIgnoreCase("POST")) {
			errors.put("genericError", "There was an error trying to log you in. Please try again.");
			errors.put("error", "User data was sent using a non secure method.");
		} else {
			username = request.getParameter("USERNAME");
			password = request.getParameter("PASSWORD");
			if(UtilValidate.isEmpty(username)) {
				username = (String) request.getAttribute("USERNAME");
			}
			if(UtilValidate.isEmpty(password)) {
				password = (String) request.getAttribute("PASSWORD");
			}

			//these errors should only be thrown when there is no token
			if(UtilValidate.isEmpty(request.getParameter("token"))) {
				if(UtilValidate.isEmpty(username)) {
					errors.put("USERNAME", "E-mail address can't be blank.");
					errors.put("error", "Invalid e-mail address or password. Please try again.");
				}
				if(UtilValidate.isEmpty(password)) {
					errors.put("PASSWORD", "Password can't be blank.");
					errors.put("error", "Invalid e-mail address or password. Please try again.");
				}
			}

			parameters.put("USERNAME", username);

			if((UtilValidate.isNotEmpty(username) && UtilValidate.isNotEmpty(password)) || UtilValidate.isNotEmpty(request.getParameter("token"))) {
				if(LoginEvents.storeLogin(request, response).equals("success")) {
					GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
					try {
						userLogin.refresh();
						//replace the session GenericValue with the updated GenericValue
						request.getSession().setAttribute("userLogin", userLogin);
					} catch (Exception e) {
						EnvUtil.reportError(e);
						Debug.logError(e, "Error refreshing userLogin value", module);
					}
					doPostLoginUpdates(request, response, messages, userLogin.getString("userLoginId"));
					success = true;
				} else {
					String errorMessage = (String)request.getAttribute("_ERROR_MESSAGE_");
					boolean accountDisabled = false;

					if(UtilValidate.isNotEmpty(errorMessage) && errorMessage.contains("disabled")) {
						accountDisabled = true;
					}
					if(accountDisabled) {
						errors.put("error", "Your account has been disabled after multiple invalid login attempts and will be re-enabled after 5 minutes.");
					} else {
						errors.put("error", "Invalid e-mail address or password. Please try again.");
					}
				}
			}
		}

		jsonResponse.put("messages", messages);
		jsonResponse.put("errors", errors);
		jsonResponse.put("parameterMap", parameters);
		jsonResponse.put("eventName", "login");
		jsonResponse.put("success", success);

		setCookie(response, Boolean.toString(success));
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String closeLoginLayer(HttpServletRequest request, HttpServletResponse response) {
		setCookie(response, "close");

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", true);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	//register a user
	public static String registerUser(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		StringBuilder errors = new StringBuilder();
		jsonResponse.put("success", true);

		boolean isEmailActive = false;
		GenericValue userLogin = null;

		try {
			isEmailActive = PartyHelper.isEmailActive(delegator, (String) request.getParameter("USERNAME"));
			if(isEmailActive) {
				errors.append("A user already exists with this email address.");
			}

			//no user found, make one
			if(!isEmailActive) {
				Map<String, String> data = new HashMap<String, String>();
				data.put("firstName", "Please");
				data.put("lastName", "Update");
				data.put("emailAddress", (String) request.getParameter("USERNAME"));
				data.put("password", (String) request.getParameter("PASSWORD"));
				userLogin = PartyHelper.createAccount(delegator, dispatcher, data);
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
		}

		//after registration log them in
		if(userLogin != null) {
			LoginWorker.doBasicLogin(userLogin, request);
			LoginWorker.autoLoginSet(request, response);
		}

		if(UtilValidate.isNotEmpty(errors.toString())) {
			jsonResponse.put("success", false);
			jsonResponse.put("error", errors.toString());
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/**
	 * An HTTP WebEvent handler that logs out a userLogin by clearing the session.
	 *
	 * @param request The HTTP request object for the current request.
	 * @param response The HTTP response object for the current request.
	 * @return Return a boolean which specifies whether or not the calling request
	 *        should generate its own content. This allows an event to override the default content.
	 */
	public static String logout(HttpServletRequest request, HttpServletResponse response) {
		// run the before-logout events
		RequestHandler rh = RequestHandler.getRequestHandler(request.getSession().getServletContext());
		rh.runBeforeLogoutEvents(request, response);


		// invalidate the security group list cache
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		LoginWorker.doBasicLogout(userLogin, request, response);

		if (request.getAttribute("_AUTO_LOGIN_LOGOUT_") == null) {
			return LoginWorker.autoLoginCheck(request, response);
		}

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", true);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Helper to do post login changes and updates
	 */
	public static void doPostLoginUpdates(HttpServletRequest request, HttpServletResponse response, Map<String, String> messages, String username) {
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		if(userLogin != null) {
			LoginWorker.doBasicLogin(userLogin, request);
			LoginWorker.autoLoginSet(request, response);
		}
		messages.put("message", "You have successfully logged into your account as '" + username + "'.");
		if(request.getSession().getAttribute("scene7PartyId") != null) {
			updatePartyForScene7UserContent(request, response);
		}

		//set new session related stuff
		SessionHelper.setSession(request, response);

		if(UtilValidate.isNotEmpty(userLogin.getString("cartId"))) {
			PersistentCart.quickCheck(request, response, userLogin.getString("cartId"), false, true);
		} else if(request.getSession().getAttribute("PersistentCart") != null) {
			try {
				((PersistentCart) request.getSession().getAttribute("PersistentCart")).setUserLoginData(request);
			} catch (Exception e) {
				EnvUtil.reportError(e);
			}
		}

		BrontoUtil.sendCartData(request);
	}

	/*
	 * Helper to set login cookie
	 */
	public static void setCookie(HttpServletResponse response, String value) {
		Cookie cookie = new Cookie("__ES_ll", value);
		cookie.setPath("/");
		cookie.setHttpOnly(false);
		cookie.setMaxAge(60 * 60 * 24 * 30);
		response.addCookie(cookie);
	}

	/*
	 * Set the Scene7UserContent to the correct partyId
	 */
	public static void updatePartyForScene7UserContent(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);

		try {
			//check for saved designs
			List<GenericValue> scene7DesignList = delegator.findByAnd("Scene7Design", UtilMisc.toMap("sessionId", request.getSession().getId()), null, false);
			for(GenericValue scene7Design : scene7DesignList) {
				try {
					scene7Design.set("partyId", cart.getPartyId());
					scene7Design.set("sessionId", null);
					scene7Design.store();
				} catch(GenericEntityException e) {
					Debug.logError(e, "Error trying to update the partyId for Scene7Design", module);
				}
			}

			//check the user uploaded content
			List<GenericValue> scene7UserContentList = delegator.findByAnd("Scene7UserContent", UtilMisc.toMap("sessionId", request.getSession().getId()), null, false);
			for(GenericValue scene7UserContent : scene7UserContentList) {
				try {
					scene7UserContent.set("partyId", cart.getPartyId());
					scene7UserContent.set("sessionId", null);
					scene7UserContent.store();
				} catch(GenericEntityException e) {
					Debug.logError(e, "Error trying to update the partyId for Scene7UserContent", module);
				}
			}

			//check the addressing options
			List<GenericValue> scene7AddressList = delegator.findByAnd("CustomerAddressGroup", UtilMisc.toMap("sessionId", request.getSession().getId()), null, false);
			for(GenericValue scene7AddressGroup : scene7AddressList) {
				try {
					scene7AddressGroup.set("partyId", cart.getPartyId());
					scene7AddressGroup.set("sessionId", null);
					scene7AddressGroup.store();
				} catch(GenericEntityException e) {
					Debug.logError(e, "Error trying to update the partyId for CustomerAddressGroup", module);
				}
			}
		} catch(GenericEntityException e) {
			Debug.logError(e, "Error retreiving list of Scene7 UserContent and Address data.", module);
		}
	}
}