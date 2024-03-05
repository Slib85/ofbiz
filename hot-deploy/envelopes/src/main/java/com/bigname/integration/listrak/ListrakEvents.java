package com.bigname.integration.listrak;

import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.webapp.website.WebSiteWorker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ListrakEvents {
	public static final String module = ListrakEvents.class.getName();

    public static String reportData(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();
        Map<String, String> emailData = new HashMap<>();

        boolean success = false;

        try {
            if (UtilValidate.isNotEmpty(context.get("allDataJSONString"))) {
                emailData.put("allDataJSONString", (String) context.get("allDataJSONString"));
            }

            ListrakHelper.sendEmail("Transactional", "ReportData", emailData, "mike@bigname.com", WebSiteWorker.getWebSiteId(request));
            success = true;
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError("Error trying to report data to mike@bigname.com: " + e.getMessage(), module);
        }

        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }
}