/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.seo;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.entity.util.EntityUtil;

import com.bigname.search.SearchHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.webapp.website.WebSiteWorker;

public class CanonicalEvents {
	public static final String module = CanonicalEvents.class.getName();

	public static String saveCanonicalRule(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;
		boolean transaction = false;

		try {
			transaction = TransactionUtil.begin();

			if (UtilValidate.isNotEmpty(context.get("ruleId"))) {
				CanonicalHelper.deleteCanonicalRule(delegator, (String) context.get("ruleId"));
			}

			CanonicalHelper.saveCanonicalRule(delegator, (String) context.get("webSiteId"), (String) context.get("url"), (String) context.get("canonicalUrl"));
			success = true;
		} catch (Exception e) {
			try {
				TransactionUtil.rollback(transaction, "Error while trying to create or update a Canonical Rule. ", e);
			} catch (GenericEntityException gee) {
				EnvUtil.reportError(gee);
			}

			EnvUtil.reportError(e);
		} finally {
			try {
				TransactionUtil.commit(transaction);
			} catch (GenericEntityException gee) {
				Debug.logError("Error while trying to create or update a Canonical Rule. ", module);
			}
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String deleteCanonicalRule(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;

		try {
			CanonicalHelper.deleteCanonicalRule(delegator, (String) context.get("ruleId"));
			success = true;
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to delete a Canonical Rule. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}
}