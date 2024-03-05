/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.homepage;

import java.util.*;
import org.apache.ofbiz.entity.Delegator;
import com.envelopes.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomepageEvents {
	public static final String module = HomepageEvents.class.getName();

	public static String doHomepageImageInsertsOrUpdates(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator)request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", false);

		if (context.get("imageType").equals("default")) {
			jsonResponse.put("success", HomepageHelper.updateDefaultHomepageImageRule(delegator, context));
		}
		else if (context.get("imageType").equals("timed")) {
			jsonResponse.put("success", HomepageHelper.insertHomepageImageRule(delegator, context));
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}
}
