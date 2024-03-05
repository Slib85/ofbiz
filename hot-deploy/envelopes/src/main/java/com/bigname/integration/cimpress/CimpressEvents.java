package com.bigname.integration.cimpress;

import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.Debug;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CimpressEvents {

    public static final String module = CimpressEvents.class.getName();

    public static String refreshCimpressConfig(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonResponse = new HashMap<>();
        boolean success = false;
        try {
            CimpressHelper.refreshCimpressConfig();
            success = true;
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError("Error while refreshing Cimpress Config:  " + e, module);
        }

        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);

    }


}
