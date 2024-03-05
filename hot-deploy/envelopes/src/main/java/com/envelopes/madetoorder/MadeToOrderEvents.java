/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.madetoorder;

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
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.envelopes.party.PartyHelper;
import com.envelopes.util.*;

public class MadeToOrderEvents {
	public static final String module = MadeToOrderEvents.class.getName();

	/*
	 * Get the product price list and/or value for custom quantity
	 * Returns a JSON Object
	 */
	public static String createCustomEnvelope(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher)request.getAttribute("dispatcher");
		Delegator delegator = (Delegator)request.getAttribute("delegator");
		HttpSession session = request.getSession();

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		boolean success = false;
		String error = null;

		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try {
				boolean isEmailActive = false;

				GenericValue userLogin = (GenericValue) session.getAttribute("userLogin");
				if(userLogin != null) {
					isEmailActive = true;
				}

				if(!isEmailActive) {
					isEmailActive = PartyHelper.isEmailActive(delegator, (String) context.get("emailAddress"));
					if(isEmailActive) {
						userLogin = PartyHelper.getUserLogin(delegator, (String) context.get("emailAddress"));
					}
				}

				if(!isEmailActive) {
					Map<String, String> data = new HashMap<String, String>();
					data.put("firstName", (String) context.get("billing_firstName"));
					data.put("lastName", (String) context.get("billing_lastName"));
					data.put("emailAddress", (String) context.get("emailAddress"));
					userLogin = PartyHelper.createAccount(delegator, dispatcher, data);
				} else if(UtilValidate.isNotEmpty((String) context.get("emailAddress"))) {
					//check if the email exists
					GenericValue emailContactMech = PartyHelper.getMatchedEmailAddress(delegator, (String) context.get("emailAddress"), userLogin.getString("partyId"), "EMAIL_ADDRESS");
					if(emailContactMech == null) {
						emailContactMech = PartyHelper.createPartyContactMech(delegator, userLogin.getString("partyId"), "EMAIL_ADDRESS", (String) context.get("emailAddress"));
					}
					PartyHelper.createPartyContactMechPurpose(delegator, userLogin.getString("partyId"), emailContactMech.getString("contactMechId"), "PRIMARY_EMAIL", true);
				}

			} catch(Exception e) {
				TransactionUtil.rollback(beganTransaction, "Error creating order", e);
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to save order. " + e + " : " + e.getMessage(), module);
			} finally {
				try {
					// only commit the transaction if we started one... this will throw an exception if it fails
					TransactionUtil.commit(beganTransaction);
				} catch(GenericEntityException e) {
					Debug.logError(e, "Could not create new order.", module);
					success = false;
				}
			}
		} catch(GenericTransactionException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to save order. " + e + " : " + e.getMessage(), module);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}
}