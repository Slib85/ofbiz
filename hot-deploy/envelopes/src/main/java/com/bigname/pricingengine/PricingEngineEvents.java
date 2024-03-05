package com.bigname.pricingengine;

import com.bigname.pricingengine.data.PricingGrid;
import com.bigname.pricingengine.mock.RequestData;
import com.bigname.pricingengine.util.EngineUtil;
import com.bigname.quote.calculator.PricingCalculator;
import com.envelopes.http.FileHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import com.google.gson.Gson;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PricingEngineEvents {

    public static final String module = PricingEngineHelper.class.getName();

    public static String getPricing(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> output = new HashMap<>();
        Map<String, Object> jsonResponse = new HashMap<>();
        boolean success = false;
        try {
//            output = PricingEngineHelper.getPricing(RequestData.getMockRequest());
            String pricingRequest = request.getParameter("pricingRequest");
            if(!EngineUtil.isEmpty(pricingRequest)) {
                output = PricingEngineHelper.getPricing(new Gson().fromJson(request.getParameter("pricingRequest"), Map.class));
                success = true;
            }
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to get PricingData due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());
        }
        jsonResponse.put("output", output);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String saveProduct(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonResponse = new HashMap<>();
        Map<String, Object> output = new HashMap<>();
        try {
            output = PricingEngineHelper.saveProduct(EnvUtil.getParameterMap(request));
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error saving Product due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());

        }
        jsonResponse.putAll(output);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String clearCache(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonResponse = new HashMap<>();
        Map<String, Object> output = new HashMap<>();
        boolean success = false;
        try {
            PricingGrid.invalidateCache();
            PricingCalculator.invalidateCache();
            success = true;
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error invalidating cache due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());

        }
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String uploadProducts(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        try {
            Map<String, Object> fileData = FileHelper.uploadFile(request, EnvConstantsUtil.UPLOAD_DIR + "/" + EnvConstantsUtil.PRODUCT_UPLOAD_DIR, false, false);
            result = PricingEngineHelper.uploadProducts(fileData);
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error saving Product due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());

        }
        jsonResponse.putAll(result);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String saveVendor(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonResponse = new HashMap<>();
        Map<String, Object> output = new HashMap<>();
        try {
            output = PricingEngineHelper.saveVendor(EnvUtil.getParameterMap(request));
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error saving Vendor due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());

        }
        jsonResponse.putAll(output);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String saveAttribute(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonResponse = new HashMap<>();
        Map<String, Object> output = new HashMap<>();
        try {
            output = PricingEngineHelper.saveAttribute(EnvUtil.getParameterMap(request));
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error saving PricingAttribute due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());

        }
        jsonResponse.putAll(output);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String saveColorAndStock(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonResponse = new HashMap<>();
        Map<String, Object> output = new HashMap<>();
        try {
            output = PricingEngineHelper.saveColorAndStock(EnvUtil.getParameterMap(request));
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error saving ColorAndStock due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());

        }
        jsonResponse.putAll(output);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String savePricingAttributeValue(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonResponse = new HashMap<>();
        Map<String, Object> output = new HashMap<>();
        try {
            output = PricingEngineHelper.savePricingAttributeValue(EnvUtil.getParameterMap(request));
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error saving PricingAttributeValue due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());

        }
        jsonResponse.putAll(output);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

}
