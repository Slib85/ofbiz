/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.seo;

import java.util.*;

import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;

import com.envelopes.util.EnvUtil;

public class CanonicalHelper {
	public static final String module = CanonicalHelper.class.getName();
	private static Map<String, String> canonicalUrls = null;

	private static void setCanonicalUrls(Delegator delegator) {
		List<GenericValue> activeCanonicalRules = getActiveCanonicalRules(delegator);
		canonicalUrls = new HashMap<String, String>();

		for(GenericValue activeCanonicalRule : activeCanonicalRules) {
			if (UtilValidate.isNotEmpty(activeCanonicalRule.getString("webSiteId")) && UtilValidate.isNotEmpty(activeCanonicalRule.getString("url")) && UtilValidate.isNotEmpty(activeCanonicalRule.getString("canonicalUrl"))) {
				canonicalUrls.put(activeCanonicalRule.getString("webSiteId") + "-" + activeCanonicalRule.getString("url"), activeCanonicalRule.getString("canonicalUrl"));
			}
		}
	}

	public static Map<String, String> getCanonicalUrls(Delegator delegator) {
		if (UtilValidate.isEmpty(canonicalUrls)) {
			setCanonicalUrls(delegator);
		}

		return canonicalUrls;
	}

	private static List<GenericValue> getActiveCanonicalRules(Delegator delegator) {
		List<GenericValue> canonicalRules = null;

		try {
			canonicalRules = EntityQuery.use(delegator).from("CanonicalRule").where("active", "Y").queryList();
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to get Canonical Rules: " + e + " : " + e.getMessage(), module);
		}

		return canonicalRules;
	}

	public static GenericValue getCanonicalRule(Delegator delegator, String ruleId) {
		GenericValue canonicalRule = null;

        try {
            if (UtilValidate.isNotEmpty(ruleId)) {
				canonicalRule = EntityQuery.use(delegator).from("CanonicalRule").where("ruleId", ruleId).queryOne();
            }
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to get Canonical Rule: " + e + " : " + e.getMessage(), module);
		}

		return canonicalRule;
	}

	public static List<GenericValue> getCanonicalRules(Delegator delegator) {
		List<GenericValue> canonicalRules = null;

		try {
			canonicalRules = EntityQuery.use(delegator).from("CanonicalRule").queryList();
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to get Canonical Rules: " + e + " : " + e.getMessage(), module);
		}

		return canonicalRules;
	}

	public static void deleteCanonicalRule(Delegator delegator, String ruleId) throws Exception {
		if (UtilValidate.isNotEmpty(ruleId)) {
			GenericValue rule = EntityQuery.use(delegator).from("CanonicalRule").where("ruleId", ruleId).queryOne();
			delegator.removeValue(rule);
		}
	}

	public static void saveCanonicalRule(Delegator delegator, String webSiteId, String url, String canonicalUrl) throws Exception {
		if (UtilValidate.isNotEmpty(webSiteId) && UtilValidate.isNotEmpty(url) && UtilValidate.isNotEmpty(canonicalUrl)) {
			String ruleId = EnvUtil.MD5(webSiteId + url + canonicalUrl);
			GenericValue newRule = delegator.makeValue("CanonicalRule", UtilMisc.toMap("ruleId", ruleId, "webSiteId", webSiteId, "url", url, "canonicalUrl", canonicalUrl, "active", "Y"));
			delegator.createOrStore(newRule);
		}
	}
}