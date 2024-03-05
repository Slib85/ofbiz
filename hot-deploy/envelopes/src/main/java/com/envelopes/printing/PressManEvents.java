package com.envelopes.printing;


import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.entity.Delegator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Manu on 9/8/2016.
 */
public class PressManEvents {
    protected static final String module = PressManEvents.class.getName();

    /**
     * Endpoint used to accept a good-to-go-job from switch
     * @param request
     * @param response
     * @return
     */
    public static String jobGoodToGo(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> jsonResponse = new HashMap<>();
        JobGoodToGoRequest jobGoodToGoRequest;
        boolean success = false;
        try {
            jobGoodToGoRequest = new JobGoodToGoRequest(request);
            result = PressManHelper.jobGoodToGo(delegator, jobGoodToGoRequest);
            success = true;
        }catch(Exception e) {
            Map<String, Object> context = EnvUtil.getParameterMap(request);
            result.put("error", e.getMessage());
            EnvUtil.reportError(e);
            Debug.logError(e, "An error occurred while placing the GoodToGoJob to the Master Job Queue: " + context.get("orderId") + "-" + context.get("orderItemSeqNum") + "-" +context.get("side") + ", due to:" + e.getMessage());
        }
        jsonResponse.put("data", result);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String getPresses(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> jsonResponse = new HashMap<>();
        boolean success = false;
        try {
            result = PressManHelper.getPresses(delegator);
            success = true;
        }catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "An error occurred while getting press data, due to:" + e.getMessage());
        }
        jsonResponse.put("data", result);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String getProductPrintAttributes(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> jsonResponse = new HashMap<>();
        boolean success = false;
        try {
            result = PressManHelper.getProductPrintAttributes(delegator);
            success = true;
        }catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "An error occurred while getting product print attributes, due to:" + e.getMessage());
        }
        jsonResponse.put("data", result);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }



}
