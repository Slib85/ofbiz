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
 * Created by shoab on 11/7/17.
 */
public class SearchBanner {
    public static final String module = SearchBanner.class.getName();

    private Delegator delegator = null;
    private String id = null;
    private Map<String, Object> context = null;
    private String bannerName =  null;
    private String banner = null;
    private String bannerBackground = null;
    private String bannerLeft = null;
    private String bannerRight = null;
    private String pageTypeId = null;
    private String positionTypeId = null;
    private String isQuery = null;
    private String containsQuery = null;
    private String conditionOperator = null;
    private String webSiteId = null;
    private boolean enabled = false;
    private String error = null;

    public SearchBanner(Delegator delegator, String id, Map<String, Object> context) {
        this.delegator = delegator;
        this.id = UtilValidate.isNotEmpty(id) ? id : null;
        this.context = context;
        this.bannerName = UtilValidate.isNotEmpty(context.get("bannerName")) ? (String) context.get("bannerName") : null;
        this.banner = UtilValidate.isNotEmpty(context.get("banner")) ? (String) context.get("banner") : null;
        this.bannerBackground = UtilValidate.isNotEmpty(context.get("bannerBackground")) ? (String) context.get("bannerBackground") : null;
        this.bannerLeft = UtilValidate.isNotEmpty(context.get("bannerLeft")) ? (String) context.get("bannerLeft") : null;
        this.bannerRight = UtilValidate.isNotEmpty(context.get("bannerRight")) ? (String) context.get("bannerRight") : null;
        this.pageTypeId = UtilValidate.isNotEmpty(context.get("pageTypeId")) ? (String) context.get("pageTypeId") : null;
        this.positionTypeId = UtilValidate.isNotEmpty(context.get("positionTypeId")) ? (String) context.get("positionTypeId") : null;
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
    public GenericValue saveBanner() throws GenericEntityException {
        GenericValue banner = null;
        String newId = this.makeId();

        if(this.id == null && this.positionTypeId != null) {
            this.id = newId;
            if(this.exists()) {
                return null;
            }
            banner = this.delegator.makeValue("SearchBanner", UtilMisc.toMap("id", this.id, "positionTypeId", this.positionTypeId));
        } else if(this.id != null && this.id != newId && this.positionTypeId != null) {
            //UPDATE TO A BANNER CRITERIA

            //remove banner before recreating
            banner = getBanner();
            banner.remove();

            this.id = newId;
            banner = this.delegator.makeValue("SearchBanner", UtilMisc.toMap("id", this.id, "positionTypeId", this.positionTypeId));
        } else {
            //SAME BANNER, MODIFIED RESULTS
            banner = EntityQuery.use(this.delegator).from("SearchBanner").where("id", this.id, "positionTypeId", this.positionTypeId).queryOne();
        }

        banner.set("bannerName", this.bannerName);
        banner.set("banner", this.banner);
        banner.set("bannerBackground", this.bannerBackground);
        banner.set("bannerLeft", this.bannerLeft);
        banner.set("bannerRight", this.bannerRight);
        banner.set("pageTypeId", this.pageTypeId);
        banner.set("isQuery", this.isQuery);
        banner.set("containsQuery", this.containsQuery);
        banner.set("conditionOperator", this.conditionOperator);
        banner.set("conditionData", new Gson().toJson(SearchUtil.cleanConditions(this.context, this.webSiteId)));
        banner.set("webSiteId", this.webSiteId);
        banner.set("enabled", this.enabled ? "Y" : "N");
        this.delegator.createOrStore(banner);

        return banner;
    }

    /**
     * Make Unique Id for Tune
     * @return
     */
    private String makeId() {
        return EnvUtil.MD5(this.pageTypeId + this.positionTypeId + this.isQuery + this.containsQuery + this.conditionOperator + new Gson().toJson(SearchUtil.cleanConditions(this.context, this.webSiteId)) + this.webSiteId);
    }

    /**
     * Check if tune exists
     * @return
     * @throws GenericEntityException
     */
    private boolean exists() throws GenericEntityException {
        return (getBanner() != null) ? true : false;
    }

    /**
     * Check if banner exists
     * @return
     * @throws GenericEntityException
     */
    private GenericValue getBanner() throws GenericEntityException {
        return EntityQuery.use(this.delegator).from("SearchBanner").where("id", this.id).queryOne();
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
    public static String makeExactMatchLookupId(String pageTypeId, String positionTypeId, String query, String conditionOperator, Map<String, Object> context, String webSiteId) {
        return EnvUtil.MD5(pageTypeId + positionTypeId + SearchUtil.cleanQuery(query) + conditionOperator + new Gson().toJson(SearchUtil.cleanConditions(context, webSiteId)) + webSiteId);
    }
}