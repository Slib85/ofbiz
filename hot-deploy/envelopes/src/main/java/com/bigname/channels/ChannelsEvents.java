package com.bigname.channels;

import com.envelopes.product.ProductHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ChannelsEvents {
    public static final String module = ChannelsEvents.class.getName();

    public static String addChannelsQuantityOverride(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();

        boolean success = false;

        try {
            ChannelsHelper.addChannelsQuantityOverride(delegator, (String) context.get("productId"), ChannelsHelper.formatChannelsData((String) context.get("channelData")));
            success = true;
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError("Error trying add channels quantity override data: " + e.getMessage(), module);
            jsonResponse.put("error", "Error trying add channels quantity override data: " + e.getMessage());
        }

        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String updateChannelsQuantityOverride(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();

        boolean success = false;

        try {
            ChannelsHelper.updateChannelsQuantityOverride(delegator, (String) context.get("productId"), (String) context.get("channelName"), Long.valueOf((String) context.get("quantity")));
            success = true;
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError("Error trying update channels quantity override data: " + e.getMessage(), module);
            jsonResponse.put("error", "Error trying update channels quantity override data: " + e.getMessage());
        }

        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }
}
