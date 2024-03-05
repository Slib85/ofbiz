package com.bigname.marketplace;

import com.bigname.marketplace.mirakl.MiraklUtil;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Manu on 2/6/2017.
 */
public class MarketplaceEvents {

    public static final String module = MarketplaceEvents.class.getName();

    public static String syncSellers(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean success = false;
        try {
            MarketplaceHelper.syncMarketplaceSellers(delegator, MiraklUtil.getMiraklShops());
            jsonResponse.put("sellers", MarketplaceHelper.getAllSellers(delegator));
            success = true;
        } catch (GenericEntityException e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error occurred while syncing Mirakl Shops .", module);
        }

        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String syncHierarchies(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> jsonResponse = new HashMap<>();
        boolean success = false;
        try {
            MarketplaceHelper.syncMarketplaceHierarchies(delegator, MiraklUtil.getMiraklHierarchies());
            jsonResponse.put("hierarchies", MarketplaceHelper.getMiraklHierarchies(delegator));
            success = true;
        } catch (GenericEntityException e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error occurred while syncing Mirakl Hierarchies .", module);
        }

        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String syncCategories(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> jsonResponse = new HashMap<>();
        boolean success = false;
        try {
            MarketplaceHelper.syncMarketplaceCategories(delegator, MiraklUtil.getMiraklCategories());
            jsonResponse.put("categories", MarketplaceHelper.getMiraklCategories(delegator));
            success = true;
        } catch (GenericEntityException e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error occurred while syncing Mirakl Categories .", module);
        }

        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String syncProductAttributes(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> jsonResponse = new HashMap<>();
        boolean success = false;
        try {
            MarketplaceHelper.syncMarketplaceProductAttributes(delegator, MiraklUtil.getMiraklProductAttributes());
            jsonResponse.put("attributes", MarketplaceHelper.getAllProductAttributes(delegator));
            success = true;
        } catch (GenericEntityException e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error occurred while syncing Mirakl Product Attributes .", module);
        }

        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String exportHierarchiesToMirakl(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> jsonResponse = new HashMap<>();
        boolean success = false;
        try {
            int exportId = MiraklUtil.exportHierarchy(MarketplaceHelper.createHierarchyFeed(delegator, new String[0])).getExportId();
            success = exportId > 0;
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error occurred while Exporting Hierarchies to Mirakl .", module);
        }
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String exportCategoriesToMirakl(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> jsonResponse = new HashMap<>();
        boolean success = false;
        try {
            int exportId = NumberUtils.toInt(MiraklUtil.exportCategories(MarketplaceHelper.createCategoryFeed(delegator, new String[0])).getSynchroId());
            success = exportId > 0;
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error occurred while Exporting Categories to Mirakl .", module);
        }
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String exportProductAttributesToMirakl(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> jsonResponse = new HashMap<>();
        boolean success = false;
        try {
            int exportId = MiraklUtil.exportProductAttributes(MarketplaceHelper.createProductAttributesFeed((delegator))).getExportId();
            success = exportId > 0;
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error occurred while Exporting Product Attributes to Mirakl .", module);
        }
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String getSellers(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean success = false;
        try {
            jsonResponse.put("sellers", MarketplaceHelper.getAllSellers(delegator));
            success = true;
        } catch (GenericEntityException e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error occurred while getting all Marketplace Sellers .", module);
        }

        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);

    }

    public static String holdSellerProducts(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean success = false;
        try {
            MarketplaceHelper.holdProducts(delegator, request.getParameter("productIds"));
            success = true;
        } catch (GenericEntityException e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error occurred while holding seller products .", module);
        }

        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);

    }

    public static String importedSellerProducts(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean success = false;
        try {
            MarketplaceHelper.importedProducts(delegator, request.getParameter("productIds"));
            success = true;
        } catch (GenericEntityException e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error occurred while importing seller products .", module);
        }

        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);

    }

    public static String publishSellerProducts(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        List<String> messages = new ArrayList<>();
        boolean success = false;
        String productIds =  request.getParameter("productIds");
        try {
            File productFeed = MarketplaceHelper.createProductFeed(delegator, productIds);
            Map<String, File> offerFeeds = MarketplaceHelper.createOfferFeeds(delegator, productIds);
            if(productFeed == null) {
                throw new Exception("Unable to generate product feed");
            } else if(UtilValidate.isEmpty(offerFeeds)) {
                throw new Exception("Unable to generate offer feed(s)");
            }
            int exportId = NumberUtils.toInt(MiraklUtil.exportProducts(productFeed).getSynchroId());
            success = exportId > 0;
            if(success) {
                MarketplaceHelper.publishedProducts(delegator, productIds);
                messages.add("Published Products");

                for (Map.Entry<String, File> entry : offerFeeds.entrySet()) {
                    exportId = MiraklUtil.exportOffers(entry.getValue(), entry.getKey()).getExportId();
                    success = exportId > 0;
                    if(success) {
                        messages.add("Published Offers for store " + entry.getKey());
                    } else {
                        messages.add("Error occurred while publishing Offers for store " + entry.getKey());
                    }
                }
            } else {
                messages.add("Error occurred while publishing Products");
            }
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error occurred while publishing seller products .", module);
            messages.add("Error occurred while publishing Products");
        }

        jsonResponse.put("success", success);
        jsonResponse.put("messages", messages);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);

    }




}
