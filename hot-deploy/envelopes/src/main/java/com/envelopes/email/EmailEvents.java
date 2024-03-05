/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.email;

import java.lang.*;
import java.util.HashMap;
import java.util.Map;

import com.bigname.integration.listrak.ListrakHelper;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.webapp.website.WebSiteWorker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.envelopes.util.*;

public class EmailEvents {
	public static final String module = EmailEvents.class.getName();

	/*
	 * Subscribe a member
	 */
	public static String addOrUpdateContact(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		boolean success = false;
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			success = EmailHelper.addOrUpdateContact((String) context.get("email"), context, WebSiteWorker.getWebSiteId(request));
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "There was an error while trying to addOrUpdateContact.", module);
		}

		result.put("success", success);

		return EnvUtil.doResponse(request, response, result, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Email self a design
	 */
	public static void emailDesign(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");

		Map<String, String> data = new HashMap<String, String>();

		data.put("designId", (String) context.get("designId"));

		try {
			//dispatcher.runAsync("sendEmail", UtilMisc.toMap("email", (String) context.get("email"), "rawData", null, "data", data, "messageType", "emailDesign", "webSiteId", WebSiteWorker.getWebSiteId(request)));
		}
		catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to send a design email. Design ID: " + (String) context.get("designId") + ", Email: " + (String) context.get("email"), module);
		}
	}

	/*
	 * Sample Request Email
	 */
	public static String samplesRequest(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");

		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;

		try {
			dispatcher.runAsync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "SampleRequest", "data", ListrakHelper.createSampleRequestData(context), "email", (String) context.get("email"), "webSiteId", WebSiteWorker.getWebSiteId(request)));
			dispatcher.runAsync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "SampleRequest", "data", ListrakHelper.createSampleRequestData(context), "email", "transaction@" + WebSiteWorker.getWebSiteId(request) + ".com", "webSiteId", WebSiteWorker.getWebSiteId(request)));
			success = true;
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to send a sample request Email. Email: " + (String) context.get("email"), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Custom Sample Pack Email
	 */
	public static String customSamplePack(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");

		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;

		try {
			dispatcher.runAsync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "SamplePackRequest", "data", ListrakHelper.createSamplePackRequestData(context), "email", (String) context.get("email"), "webSiteId", WebSiteWorker.getWebSiteId(request)));
			dispatcher.runAsync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "SamplePackRequest", "data", ListrakHelper.createSamplePackRequestData(context), "email", "transaction@" + WebSiteWorker.getWebSiteId(request) + ".com", "webSiteId", WebSiteWorker.getWebSiteId(request)));
			success = true;
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to send a custom sample pack Email. Email: " + (String) context.get("email"), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Email This Quote
	 */
	public static String emailThisQuote(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");

		HashMap<String, String> emailData = new HashMap<>();

		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;

		try {
			emailData.put("QuoteBodyHTML", ListrakHelper.buildEmailThisQuoteHTML(context, true));

			if (UtilValidate.isNotEmpty(emailData.get("QuoteBodyHTML"))) {
				dispatcher.runAsync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "EmailQuote", "data", emailData, "email", context.get("email"), "webSiteId", WebSiteWorker.getWebSiteId(request)));
				success = true;
			} else {
				Debug.logError("Email This Quote email not sent.  Either desktop or mobile content could not be created and were empty.", module);
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to send an Email This Quote email: " + (String) context.get("email"), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String commerceConciergeSubmission(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");

		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;

		try {
			//dispatcher.runAsync("sendEmail", UtilMisc.toMap("email", (String) context.get("email"), "rawData", null, "data", context, "messageType", "commerceConciergeSubmission", "webSiteId", WebSiteWorker.getWebSiteId(request)));
			//dispatcher.runAsync("sendEmail", UtilMisc.toMap("email", "concierge@bigname.com", "rawData", null, "data", context, "messageType", "commerceConciergeSubmission", "webSiteId", WebSiteWorker.getWebSiteId(request)));
			success = true;
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to send a commerce concierge submission Email. Email: " + (String) context.get("email"), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

}