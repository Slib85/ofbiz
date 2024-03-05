/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.transaction.GenericTransactionException;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.entity.util.EntityUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionHelper {
	public static final String module = SessionHelper.class.getName();
	public static final String COOKIE_NAME = "__SS_Data";

	/**
	 * Check if the session exist already
	 * @param delegator
	 * @param sessionId
	 * @return
	 * @throws GenericEntityException
	 */
	public static boolean doesSessionExist(Delegator delegator, String sessionId) throws GenericEntityException {
		List<GenericValue> sessions = delegator.findByAnd("EnvSession", UtilMisc.toMap("sessionId", sessionId), UtilMisc.toList("createdStamp DESC"), false);

		return UtilValidate.isNotEmpty(sessions);
	}

	/**
	 * Get the session
	 * @param delegator
	 * @param sessionId
	 * @return
	 * @throws GenericEntityException
	 */
	public static GenericValue getSession(Delegator delegator, String sessionId) throws GenericEntityException {
		return EntityUtil.getFirst(delegator.findByAnd("EnvSession", UtilMisc.toMap("sessionId", sessionId), UtilMisc.toList("createdStamp DESC"), false));
	}

	/**
	 * Get the sessions
	 * @param delegator
	 * @param partyId
	 * @return
	 * @throws GenericEntityException
	 */
	public static List<GenericValue> getSessions(Delegator delegator, String partyId) throws GenericEntityException {
		return delegator.findByAnd("EnvSession", UtilMisc.toMap("sessionId", partyId), UtilMisc.toList("createdStamp DESC"), false);
	}

	/**
	 * Store the session in the database
	 * @param delegator
	 * @param clientId
	 * @param partyId
	 * @param sessionId
	 * @return
	 * @throws GenericEntityException
	 */
	public static GenericValue setSession(Delegator delegator, String clientId, String partyId, String sessionId) throws GenericEntityException {
		GenericValue session = delegator.makeValue("EnvSession", UtilMisc.toMap("clientId", clientId, "partyId", partyId, "sessionId", sessionId));
		delegator.createOrStore(session);

		return session;
	}

	/**
	 * Save the session
	 * @param request
	 * @param response
	 * @return
	 */
	public static boolean setSession(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		String error = null;
		boolean success = false;

		//if party
		String partyId = null;
		if(UtilValidate.isEmpty(context.get("partyId"))) {
			GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
			if(UtilValidate.isNotEmpty(userLogin)) {
				partyId = userLogin.getString("partyId");
			}
		}

		String clientId = (UtilValidate.isNotEmpty(context.get("clientId"))) ? (String) context.get("clientId") : "envelopes";

		String sessionId = null;
		if(UtilValidate.isEmpty(context.get("sessionId"))) {
			if(request.getCookies() != null) {
				Map<String, Object> cookieData = new HashMap<>();
				for(Cookie cookie : request.getCookies()) {
					if(cookie.getName().equals(COOKIE_NAME)) {
						sessionId = cookie.getValue();
						break;
					}
				}
			}
		}

		try {
			//check if session exists and is not associated to
			if(UtilValidate.isEmpty(clientId) || UtilValidate.isEmpty(partyId) || UtilValidate.isEmpty(sessionId)) {
				error = "Missing sessionId value.";
			} else if(!SessionHelper.doesSessionExist(delegator, sessionId)) {
				boolean beganTransaction = false;
				try {
					beganTransaction = TransactionUtil.begin();
					try {
						setSession(delegator, clientId, partyId, UtilValidate.isEmpty(context.get("sessionId")) ? sessionId : (String) context.get("sessionId"));
					} catch (GenericEntityException e) {
						TransactionUtil.rollback(beganTransaction, "Error while set session data.", e);
						EnvUtil.reportError(e);
						Debug.logError("Error while set session data. " + e + " : " + e.getMessage(), module);
					} finally {
						try {
							TransactionUtil.commit(beganTransaction);
							success = true;
						} catch(GenericEntityException e) {
							TransactionUtil.rollback(beganTransaction, "Error while set session data.", e);
							EnvUtil.reportError(e);
							Debug.logError(e, "Error while set session data.", module);
						}
					}
				} catch(GenericTransactionException e) {
					EnvUtil.reportError(e);
					Debug.logError("Error while set session data. " + e + " : " + e.getMessage(), module);
				}
			} else {
				error = "This session has already been saved.";
				success = true;
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while set session data. " + e + " : " + e.getMessage(), module);
		}

		return success;
	}
}