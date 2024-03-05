package com.bigname.search.elasticsearch;

import java.util.*;

import com.bigname.search.elasticsearch.util.SearchUtil;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;

import com.google.gson.Gson;
import com.envelopes.util.EnvUtil;

/**
 * Created by shoab on 7/7/17.
 */
public class SearchTune {
    public static final String module = SearchTune.class.getName();

    private Delegator delegator = null;
    private String id = null;
    private Map<String, Object> context = null;
    private String tuneName =  null;
    private String pageTypeId = null;
    private String isQuery = null;
    private String containsQuery = null;
    private String conditionOperator = null;
    private List<String> promotedProductList = null;
    private List<String> demotedProductList = null;
    private List<String> removedProductList = null;
    private String webSiteId = null;
    private boolean enabled = false;
    private String error = null;

    /**
     * Search Tune Constructor
     * @param delegator
     * @param id
     * @param context
     */
    @SuppressWarnings("unchecked")
    public SearchTune(Delegator delegator, String id, Map<String, Object> context) {
        this.delegator = delegator;
        this.id = UtilValidate.isNotEmpty(id) ? id : null;
        this.context = context;
        this.tuneName = UtilValidate.isNotEmpty(context.get("tuneName")) ? (String) context.get("tuneName") : null;
        this.pageTypeId = UtilValidate.isNotEmpty(context.get("pageTypeId")) ? (String) context.get("pageTypeId") : null;
        this.conditionOperator = UtilValidate.isNotEmpty(context.get("conditionOperator")) ? (String) context.get("conditionOperator") : null;
        this.promotedProductList = UtilValidate.isNotEmpty(context.get("promotedProductList")) ? new Gson().fromJson((String) context.get("promotedProductList"), ArrayList.class) : null;
        this.demotedProductList = UtilValidate.isNotEmpty(context.get("demotedProductList")) ? new Gson().fromJson((String) context.get("demotedProductList"), ArrayList.class) : null;
        this.removedProductList = UtilValidate.isNotEmpty(context.get("removedProductList")) ? new Gson().fromJson((String) context.get("removedProductList"), ArrayList.class) : null;
        this.webSiteId = UtilValidate.isNotEmpty(context.get("webSiteId")) ? (String) context.get("webSiteId") : null;
        this.enabled = "Y".equalsIgnoreCase((String) context.get("enabled"));

        this.isQuery = UtilValidate.isNotEmpty(context.get("isQuery")) ? SearchTranslator.processQueryRules(this.delegator, (String) context.get("isQuery"), this.webSiteId, this.pageTypeId) : null;
        this.containsQuery = UtilValidate.isNotEmpty(context.get("containsQuery")) ? SearchTranslator.processQueryRules(this.delegator, (String) context.get("containsQuery"), this.webSiteId, this.pageTypeId) : null;
    }

    /**
     * Save the tune to the database
     * @return
     * @throws GenericEntityException
     */
    public GenericValue saveTune() throws GenericEntityException {
        GenericValue tune = null;
        String newId = this.makeId();

        if(this.id == null) {
            //NEW TUNE
            this.id = newId;
            if (this.exists()) {
                return null;
            }
            tune = this.delegator.makeValue("SearchTune", UtilMisc.toMap("id", this.id));
        } else if(this.id != null && this.id != newId) {
            //UPDATE TO A TUNE CRITERIA

            //remove tune before recreating
            tune = getTune();
            tune.remove();

            this.id = newId;
            tune = this.delegator.makeValue("SearchTune", UtilMisc.toMap("id", this.id));
        } else {
            //SAME TUNE, MODIFIED RESULTS
            tune = EntityQuery.use(this.delegator).from("SearchTune").where("id", this.id).queryOne();
        }

        tune.set("tuneName", this.tuneName);
        tune.set("pageTypeId", this.pageTypeId);
        tune.set("isQuery", this.isQuery);
        tune.set("containsQuery", this.containsQuery);
        tune.set("conditionOperator", this.conditionOperator);
        tune.set("promotedProductList", new Gson().toJson(this.promotedProductList));
        tune.set("demotedProductList", new Gson().toJson(this.demotedProductList));
        tune.set("removedProductList", new Gson().toJson(this.removedProductList));
        tune.set("conditionData", new Gson().toJson(SearchUtil.cleanConditions(this.context, this.webSiteId)));
        tune.set("webSiteId", this.webSiteId);
        tune.set("enabled", this.enabled ? "Y" : "N");
        this.delegator.createOrStore(tune);

        //clear search tune cache on any changes
        SearchData.invalidateCache();

        return tune;
    }

    /**
     * Make Unique Id for Tune
     * @return
     */
    private String makeId() {
        return EnvUtil.MD5(this.pageTypeId + this.isQuery + this.containsQuery + this.conditionOperator + new Gson().toJson(SearchUtil.cleanConditions(this.context, this.webSiteId)) + this.webSiteId);
    }

    /**
     * Check if tune exists
     * @return
     * @throws GenericEntityException
     */
    private boolean exists() throws GenericEntityException {
        return (getTune() != null) ? true : false;
    }

    /**
     * Check if tune exists
     * @return
     * @throws GenericEntityException
     */
    private GenericValue getTune() throws GenericEntityException {
        return EntityQuery.use(this.delegator).from("SearchTune").where("id", this.id).queryOne();
    }

    /**
     * Create Lookup ID for exact match criteria
     * @param pageTypeId
     * @param query
     * @param conditionOperator
     * @param context
     * @param webSiteId
     * @return
     */
    public static String makeExactMatchLookupId(String pageTypeId, String query, String conditionOperator, Map<String, Object> context, String webSiteId) {
        return EnvUtil.MD5(pageTypeId + SearchUtil.cleanQuery(query) + conditionOperator + new Gson().toJson(SearchUtil.cleanConditions(context, webSiteId)) + webSiteId);
    }
}