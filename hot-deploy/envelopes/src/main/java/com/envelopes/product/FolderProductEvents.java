package com.envelopes.product;

import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mike on 6/26/2017.
 */
public class FolderProductEvents {
    public static final String module = FolderProductEvents.class.getName();

    public static String getProductMaterials(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        jsonResponse.put("success", true);
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> folderStyleMaterialLists = new HashMap<>();

        try {
            folderStyleMaterialLists.put("basicAndPremium", FolderProductHelper.getProductMaterials(delegator, (String) context.get("productId"), Boolean.TRUE));
            folderStyleMaterialLists.put("nonBasicAndPremium", FolderProductHelper.getProductMaterials(delegator, (String) context.get("productId"), Boolean.FALSE));
            jsonResponse.put("data", folderStyleMaterialLists);
        }
        catch(GenericEntityException e) {
            jsonResponse.put("success", false);
            EnvUtil.reportError(e);
            Debug.logError("Error while trying to get product materials. [Product ID: " + context.get("productId") + "] - " + e + " : " + e.getMessage(), module);
        }

        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String getFolderStyleList(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("success", true);
        Map<String, Object> context = EnvUtil.getParameterMap(request);

        try {
            jsonResponse.put("data", FolderProductHelper.getFolderStyleList(delegator, (String) context.get("categoryId")));
        }
        catch(GenericEntityException e) {
            jsonResponse.put("success", false);
            EnvUtil.reportError(e);
            Debug.logError("Error while trying to get product style list. [Category ID: " + context.get("categoryId") + "] - " + e + " : " + e.getMessage(), module);
        }

        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }
}