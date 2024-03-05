package com.bigname.search.elasticsearch;

import com.bigname.search.elasticsearch.util.SearchUtil;
import com.envelopes.util.EnvUtil;
import com.google.gson.Gson;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;

import java.util.Map;

/**
 * Created by shoab on 7/7/17.
 */
public class SearchRedirect {
    public static final String module = SearchRedirect.class.getName();

    private Delegator delegator = null;
    private String id = null;
    private Map<String, Object> context = null;
    private String redirectName =  null;
    private String redirectUrl = null;
    private String pageTypeId = null;
    private String isQuery = null;
    private String containsQuery = null;
    private String conditionOperator = null;
    private String webSiteId = null;
    private boolean enabled = false;
    private String error = null;

    public SearchRedirect(Delegator delegator, String id, Map<String, Object> context) {
        this.delegator = delegator;
        this.id = UtilValidate.isNotEmpty(id) ? id : null;
        this.context = context;
        this.redirectName = UtilValidate.isNotEmpty(context.get("redirectName")) ? (String) context.get("redirectName") : null;
        this.redirectUrl = UtilValidate.isNotEmpty(context.get("redirectUrl")) ? (String) context.get("redirectUrl") : null;
        this.pageTypeId = UtilValidate.isNotEmpty(context.get("pageTypeId")) ? (String) context.get("pageTypeId") : null;
        this.conditionOperator = UtilValidate.isNotEmpty(context.get("conditionOperator")) ? (String) context.get("conditionOperator") : null;
        this.webSiteId = UtilValidate.isNotEmpty(context.get("webSiteId")) ? (String) context.get("webSiteId") : null;
        this.enabled = "Y".equalsIgnoreCase((String) context.get("enabled"));

        this.isQuery = UtilValidate.isNotEmpty(context.get("isQuery")) ? SearchTranslator.processQueryRules(this.delegator, (String) context.get("isQuery"), this.webSiteId, this.pageTypeId) : null;
        this.containsQuery = UtilValidate.isNotEmpty(context.get("containsQuery")) ? SearchTranslator.processQueryRules(this.delegator, (String) context.get("containsQuery"), this.webSiteId, this.pageTypeId) : null;
    }

    /**
     * Save the redirect to the database
     * @return
     * @throws GenericEntityException
     */
    public GenericValue saveRedirect() throws GenericEntityException {
        GenericValue redirect = null;
        String newId = this.makeId();

        if(this.id == null) {
            //NEW REDIRECT
            this.id = newId;
            if(this.exists()) {
                return null;
            }
            redirect = this.delegator.makeValue("SearchRedirect", UtilMisc.toMap("id", this.id));
        } else if(this.id != null && this.id != newId) {
            //UPDATE TO A REDIRECT CRITERIA

            //remove redirect before recreating
            redirect = getRedirect();
            redirect.remove();

            this.id = newId;
            redirect = this.delegator.makeValue("SearchRedirect", UtilMisc.toMap("id", this.id));
        } else {
            //SAME REDIRECT, MODIFIED RESULTS
            redirect = EntityQuery.use(this.delegator).from("SearchRedirect").where("id", this.id).queryOne();
        }

        redirect.set("redirectName", this.redirectName);
        redirect.set("redirectUrl", this.redirectUrl);
        redirect.set("pageTypeId", this.pageTypeId);
        redirect.set("isQuery", this.isQuery);
        redirect.set("containsQuery", this.containsQuery);
        redirect.set("conditionOperator", this.conditionOperator);
        redirect.set("conditionData", new Gson().toJson(SearchUtil.cleanConditions(this.context, this.webSiteId)));
        redirect.set("webSiteId", this.webSiteId);
        redirect.set("enabled", this.enabled ? "Y" : "N");
        this.delegator.createOrStore(redirect);

        return redirect;
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
        return (getRedirect() != null) ? true : false;
    }

    /**
     * Check if redirect exists
     * @return
     * @throws GenericEntityException
     */
    private GenericValue getRedirect() throws GenericEntityException {
        return EntityQuery.use(this.delegator).from("SearchRedirect").where("id", this.id).queryOne();
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