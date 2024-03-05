package com.bigname.search;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import com.bigname.search.elasticsearch.*;
import com.bigname.search.elasticsearch.enums.SearchPageType;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import com.google.gson.Gson;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;
import org.apache.ofbiz.webapp.website.WebSiteWorker;

/**
 * Created by shoabkhan on 5/6/17.
 */
public class SearchEvents {
    public static final String module = SearchEvents.class.getName();

    /**
     * Update field settings for search
     * @param request
     * @param response
     * @return
     */
    public static String saveSettings(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();

        boolean success = false;
        try {
            if(UtilValidate.isNotEmpty(context.get("data"))) {
               List<Map<String, Object>> data = new Gson().fromJson(request.getParameter("data"), ArrayList.class);
               for(Map<String, Object> fields : data) {
                   GenericValue field = SearchField.getField((String) fields.get("fieldName"), (String) context.get("webSiteId"));
                   field = EnvUtil.insertGenericValueData(delegator, field, fields);
                   field.set("changeByUserLoginId", ((GenericValue) request.getSession().getAttribute("userLogin")).getString("userLoginId"));
                   delegator.store(field);
               }
            }
            success = true;
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error saving search settings.", module);
        }

        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /**
     * Save or update a synonym entry
     * @param request
     * @param response
     * @return
     */
    public static String setSynonym(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();

        boolean success = false;

        String error = null;
        try {
            String id = UtilValidate.isNotEmpty(context.get("id")) ? (String) context.get("id") : null;
            String[] fromSynonyms = (UtilValidate.isNotEmpty(context.get("fromSynonyms"))) ? ((String) context.get("fromSynonyms")).trim().split("\\s*,\\s*") : null;
            String[] toSynonyms = (UtilValidate.isNotEmpty(context.get("toSynonyms"))) ? ((String) context.get("toSynonyms")).trim().split("\\s*,\\s*") : null;

            SearchSynonym synonym = new SearchSynonym((Delegator) request.getAttribute("delegator"), id, fromSynonyms, toSynonyms, (String) context.get("synonymTypeId"), (String) context.get("webSiteId"), (String) context.get("enabled"));
            if(synonym.isValid()) {
                synonym.saveSynonym();
                success = true;
                error = "Synonym conflict with existing rule in ID: " + id;
            } else {
                error = synonym.getError();
            }
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error saving synonym", module);
        }

        jsonResponse.put("error", error);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /**
     * Remove a synonym
     * @param request
     * @param response
     * @return
     */
    public static String removeSynonym(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();

        boolean success = false;
        try {
            if(UtilValidate.isNotEmpty(context.get("id"))) {
                GenericValue synonym = EntityQuery.use(delegator).from("SearchSynonym").where("id", (String) context.get("id")).queryOne();
                if(synonym != null) {
                    synonym.remove();
                    success = true;
                }
            }
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error removing synonym", module);
        }

        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /**
     * Generate the file of synonyms for elastic search
     * @param request
     * @param response
     * @return
     */
    public static String generateSynonymFile(HttpServletRequest request, HttpServletResponse response) {
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();

        boolean success = false;
        try {
            Map<String, Object> result = dispatcher.runSync("generateSynonymFile", UtilMisc.toMap("userLogin", (GenericValue) request.getSession().getAttribute("userLogin")));
            if(ServiceUtil.isSuccess(result)) {
                success = true;
            }
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error saving synonym", module);
        }

        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /**
     * Save or update a redirect entry
     * @param request
     * @param response
     * @return
     */
    public static String setRedirect(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();

        boolean success = false;

        String error = null;
        try {
            String id = UtilValidate.isNotEmpty(context.get("id")) ? (String) context.get("id") : null;

            SearchRedirect redirect = new SearchRedirect((Delegator) request.getAttribute("delegator"), id, context);
            if(redirect.saveRedirect() != null) {
                success = true;
            } else {
                error = "There is already a rule based on this criteria.";
            }
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error saving redirect", module);
        }

        jsonResponse.put("error", error);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /**
     * Remove a redirect
     * @param request
     * @param response
     * @return
     */
    public static String removeRedirect(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();

        boolean success = false;
        try {
            if(UtilValidate.isNotEmpty(context.get("id"))) {
                GenericValue redirect = EntityQuery.use(delegator).from("SearchRedirect").where("id", (String) context.get("id")).queryOne();
                if(redirect != null) {
                    redirect.remove();
                    success = true;
                }
            }
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error removing redirect", module);
        }

        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /**
     * Save or update a banner entry
     * @param request
     * @param response
     * @return
     */
    public static String setBanner(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();

        boolean success = false;

        String error = null;
        try {
            String id = UtilValidate.isNotEmpty(context.get("id")) ? (String) context.get("id") : null;

            SearchBanner banner = new SearchBanner((Delegator) request.getAttribute("delegator"), id, context);
            if(banner.saveBanner() != null) {
                success = true;
            } else {
                error = "There is already a rule based on this criteria.";
            }
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error saving banner", module);
        }

        jsonResponse.put("error", error);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /**
     * Remove a banner
     * @param request
     * @param response
     * @return
     */
    public static String removeBanner(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();

        boolean success = false;
        try {
            if(UtilValidate.isNotEmpty(context.get("id"))) {
                GenericValue banner = EntityQuery.use(delegator).from("SearchBanner").where("id", (String) context.get("id")).queryOne();
                if(banner != null) {
                    banner.remove();
                    success = true;
                }
            }
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error removing banner", module);
        }

        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /**
     * Save or update a tune entry
     * @param request
     * @param response
     * @return
     */
    public static String setTune(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();

        boolean success = false;

        String error = null;
        try {
            String id = UtilValidate.isNotEmpty(context.get("id")) ? (String) context.get("id") : null;

            SearchTune tune = new SearchTune((Delegator) request.getAttribute("delegator"), id, context);
            if(tune.saveTune() != null) {
                success = true;
            } else {
                error = "There is already a rule based on this criteria.";
            }
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error saving tune", module);
        }

        jsonResponse.put("error", error);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /**
     * Remove a tune
     * @param request
     * @param response
     * @return
     */
    public static String removeTune(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();

        boolean success = false;
        try {
            if(UtilValidate.isNotEmpty(context.get("id"))) {
                GenericValue tune = EntityQuery.use(delegator).from("SearchTune").where("id", (String) context.get("id")).queryOne();
                if(tune != null) {
                    tune.remove();
                    success = true;
                }
            }
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error removing tune", module);
        }

        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /**
     * Save or update a search landing page entry
     * @param request
     * @param response
     * @return
     */
    public static String setSearchPage(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();

        boolean success = false;

        String error = null;
        try {
            String id = UtilValidate.isNotEmpty(context.get("id")) ? (String) context.get("id") : null;

            SearchPage page = new SearchPage((Delegator) request.getAttribute("delegator"), id, context);
            if(page.savePage() != null) {
                success = true;
            } else {
                error = "There is already a page based on this criteria.";
            }
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error saving page", module);
        }

        jsonResponse.put("error", error);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /**
     * Remove a tune
     * @param request
     * @param response
     * @return
     */
    public static String removeSearchPage(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();

        boolean success = false;
        try {
            if(UtilValidate.isNotEmpty(context.get("id"))) {
                GenericValue page = EntityQuery.use(delegator).from("SearchPage").where("id", (String) context.get("id")).queryOne();
                if(page != null) {
                    page.remove();
                    success = true;
                }
            }
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error removing page", module);
        }

        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /**
     * Get search results
     * @param request
     * @param response
     * @return
     */
    public static String getSearchResult(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();

        boolean bypassTuneCheck = "Y".equalsIgnoreCase((String) context.get("bypassTuneCheck")) ? true : false;
        String webSiteId = context.containsKey("webSiteId") ? (String) context.get("webSiteId") : WebSiteWorker.getWebSiteId(request);
        SearchPageType spt = context.containsKey("searchPageType") ? SearchPageType.getValueOf((String) context.get("searchPageType")) : SearchPageType.ANY_PAGE;
        SearchBuilder builder = new SearchBuilder((Delegator) request.getAttribute("delegator"), context, webSiteId, spt, bypassTuneCheck);
        builder.executeSearch();

        try {
            SearchResult result = builder.getSearchResult();
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error analyzing results.", module);
        }

        jsonResponse.put("request", builder.getJSONRequest());
        jsonResponse.put("response", builder.getJSONResponse());
        jsonResponse.put("success", true);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /**
     * Get cluster health
     * @param request
     * @param response
     * @return
     */
    public static String getSearchHealth(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();

        SearchBuilder builder = new SearchBuilder((Delegator) request.getAttribute("delegator"), context, WebSiteWorker.getWebSiteId(request));
        builder.executeHealth();

        jsonResponse.put("health", builder.getHealthResponse());
        jsonResponse.put("success", true);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /**
     * Clear Cache for Search
     * @param request
     * @param response
     * @return
     */
    public static String clearSearchCache(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();

        //clear search cache
        SearchBuilder builder = new SearchBuilder((Delegator) request.getAttribute("delegator"), context, WebSiteWorker.getWebSiteId(request));
        builder.executeCacheClear();

        SearchField.clearCache(); //clear search field cache
        SearchData.invalidateCache(); //clear search tune cache

        jsonResponse.put("success", true);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }
}
