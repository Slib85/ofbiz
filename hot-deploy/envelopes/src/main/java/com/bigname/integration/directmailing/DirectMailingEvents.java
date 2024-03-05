package com.bigname.integration.directmailing;

import com.bigname.integration.click2mail.client.request.GetProofRequest;
import com.envelopes.http.FileHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Manu on 5/2/2018.
 */
public class DirectMailingEvents {
    public static final String module = DirectMailingEvents.class.getName();
    
    public static String getCostEstimate(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();

        Map<String, Object> formParams = new HashMap<>();
        formParams.put("documentClass", context.get("documentClass"));
        formParams.put("layout", context.get("layout"));
        formParams.put("productionTime", context.get("productionTime"));
        formParams.put("envelope", context.get("envelope"));
        formParams.put("color", context.get("color"));
        formParams.put("paperType", context.get("paperType"));
        formParams.put("printOption", context.get("printOption"));
        formParams.put("mailClass", context.get("mailClass"));
        formParams.put("quantity", context.get("quantity"));
        formParams.put("quantityList", context.get("quantityList"));
        formParams.put("numberOfPages", context.get("numberOfPages"));

        String price;
        boolean success = false;
        try {
            price = (UtilValidate.isNotEmpty(formParams.get("quantityList"))) ? DirectMailingHelper.getCostEstimates(formParams) : DirectMailingHelper.getCostEstimate(formParams);
            success = true;
            jsonResponse.put("price", price);
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonResponse.put("success", success);

        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String getJobNumber(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        context.put("partyId", DirectMailingHelper.getPartyId(request));
        Map<String, Object> jsonResponse = new HashMap<>();
        boolean success = false;
        try {

            jsonResponse.put("jobNumber", DirectMailingHelper.getJobNumber(delegator, context));
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String saveJob(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        context.put("partyId", DirectMailingHelper.getPartyId(request));
        Map<String, Object> jsonResponse = new HashMap<>();
        boolean success = false;
        try {

            jsonResponse.put("result", DirectMailingHelper.saveJob(delegator, context));
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String getSaveJobStatus(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> context = EnvUtil.getParameterMap(request);;
        Map<String, Object> jsonResponse = new HashMap<>();
        boolean success = false;
        try {
            jsonResponse.put("result", DirectMailingHelper.getSaveJobStatus(delegator, (String) context.get("jobNumber")));
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String getLoggedInPartyId(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("partyId", DirectMailingHelper.getPartyId(request));
        jsonResponse.put("success", true);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static void getProof(HttpServletRequest request, HttpServletResponse response) {
        Boolean success = false;
        Map<String, Object> jsonResponse = new HashMap<>();
        Map<String, Object> context = EnvUtil.getParameterMap(request);;
        DirectMailingUtil.getProof(new GetProofRequest((String) context.get("jobId"), (String)context.get("proofId")), response);
        /*if (success == null) success = false;
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);*/
    }

    public static String uploadDocumentFile(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        try {
            jsonResponse = FileHelper.uploadFile(request, EnvConstantsUtil.UPLOAD_DIR + "/" + EnvConstantsUtil.DIRECT_MAILING_DOC_UPLOAD_DIR, false, false);
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError("Error while uploading direct mailing document file. " + e + " : " + e.getMessage(), module);
        }

        if (UtilValidate.isEmpty(jsonResponse)) {
            jsonResponse.put("error", "Could not upload files, please try again.");
            jsonResponse.put("success", false);
        }

        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String getSavedDocuments(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean success = false;
        try {
            jsonResponse.put("documents", DirectMailingHelper.getSavedDocuments(request));
            success = true;
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError("Error while getting saved documents . " + e + " : " + e.getMessage(), module);
        }

        jsonResponse.put("success", success);

        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String setCartId(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean success = false;
        try {
            DirectMailingHelper.setCartId(request, response);
            success = true;
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError("Error while setting cart Id . " + e + " : " + e.getMessage(), module);
        }

        jsonResponse.put("success", success);

        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

}