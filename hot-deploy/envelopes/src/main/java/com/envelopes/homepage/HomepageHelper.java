/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.homepage;

import java.util.*;
import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityConditionList;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.entity.util.EntityQuery;
import com.envelopes.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomepageHelper {
	public static final String module = HomepageHelper.class.getName();
	/*
	 * Get a list of product assets
	 */
	public static GenericValue getActiveHomepageImage(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator)request.getAttribute("delegator");
		GenericValue homepageImage = null;

		try {
			List<EntityCondition> homepageBannerCondList = new ArrayList<EntityCondition>();
			homepageBannerCondList.add(EntityCondition.makeCondition("fromDate", EntityOperator.LESS_THAN_EQUAL_TO, UtilDateTime.nowTimestamp()));
			homepageBannerCondList.add(EntityCondition.makeCondition("thruDate", EntityOperator.GREATER_THAN_EQUAL_TO, UtilDateTime.nowTimestamp()));
			homepageBannerCondList.add(EntityCondition.makeCondition("imageType", EntityOperator.EQUALS, "timed"));
			EntityCondition homepageBannerCondition = EntityCondition.makeCondition(homepageBannerCondList, EntityOperator.AND);

			homepageImage = EntityUtil.getFirst(delegator.findList("HomepageBanner", homepageBannerCondition, null, UtilMisc.toList("fromDate DESC"), null, false));

			if (UtilValidate.isEmpty(homepageImage)) {
				homepageImage = EntityUtil.getFirst(delegator.findByAnd("HomepageBanner", UtilMisc.toMap("imageType", "default"), null, false));
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to obtain Homepage Banner. " + e + " : " + e.getMessage(), module);
		}

		return homepageImage;
	}

	public static List<GenericValue> getTimedHomepageImages(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator)request.getAttribute("delegator");
		List<GenericValue> timedHomepageImages = null;

		try {
			timedHomepageImages = delegator.findByAnd("HomepageBanner", UtilMisc.toMap("imageType", "timed"), null, false);
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to obtain Timed Homepage Images from table Homepage Banner. " + e + " : " + e.getMessage(), module);
		}

		return timedHomepageImages;
	}

	public static boolean insertHomepageImageRule(Delegator delegator, Map<String, Object> context) {
		boolean response = false;

		try {
			GenericValue homepageBanner = delegator.makeValue("HomepageBanner", UtilMisc.toMap("bannerId", delegator.getNextSeqId("HomepageBanner")));
			homepageBanner = EnvUtil.insertGenericValueData(delegator, homepageBanner, context);
			delegator.create(homepageBanner);
			response = true;
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to add a row to HomepageBanner table.", module);
		}

		return response;
	}

	public static boolean updateDefaultHomepageImageRule(Delegator delegator, Map<String, Object> context) {
		boolean response = false;
        GenericValue defaultHomepageImage = null;

        try {
            defaultHomepageImage = EntityQuery.use(delegator).from("HomepageBanner").where("imageType", "default").queryOne();
        } catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error looking up default homepage image in Homepage Banner table.", module);
        }

        if (UtilValidate.isNotEmpty(defaultHomepageImage)) {
			defaultHomepageImage.set("foregroundImage", context.get("foregroundImage"));
			defaultHomepageImage.set("foregroundImageFormat", context.get("foregroundImageFormat"));
			defaultHomepageImage.set("foregroundImageLink", context.get("foregroundImageLink"));
			defaultHomepageImage.set("backgroundImage", context.get("backgroundImage"));
			defaultHomepageImage.set("mobileImage", context.get("mobileImage"));
			defaultHomepageImage.set("mobileImageFormat", context.get("mobileImageFormat"));
			defaultHomepageImage.set("mobileImageLink", context.get("mobileImageLink"));
			defaultHomepageImage.set("modalPopup", context.get("modalPopup"));
			try {
				delegator.store(defaultHomepageImage);
				response = true;
			} catch (GenericEntityException e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "Error updating default homepage image in Homepage Banner table.", module);
			}
		}
		else {
			response = insertHomepageImageRule(delegator, context);
		}

		return response;
	}
}
