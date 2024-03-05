/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.session;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.transaction.GenericTransactionException;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.service.LocalDispatcher;

import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;

public class SessionEvents {
	public static final String module = SessionEvents.class.getName();

	/**
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getSession(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;

		GenericValue session = null;
		try {
			session = SessionHelper.getSession(delegator, (String) context.get("sessionId"));
			success = true;
		} catch(GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while getting session data. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		jsonResponse.put("session", session);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getSessions(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;

		String partyId = null;
		if(UtilValidate.isEmpty(context.get("partyId"))) {
			GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
			if(UtilValidate.isNotEmpty(userLogin)) {
				partyId = userLogin.getString("partyId");
			}
		}

		List<GenericValue> sessions = null;
		try {
			sessions = SessionHelper.getSessions(delegator, partyId);
			success = true;
		} catch(GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while getting session data. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		jsonResponse.put("session", sessions);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}


}