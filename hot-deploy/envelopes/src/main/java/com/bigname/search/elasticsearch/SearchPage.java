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

/**
 * Created by shoab on 7/7/17.
 */
public class SearchPage {
    public static final String module = SearchPage.class.getName();

    private Delegator delegator = null;
    private String id = null;
    private Map<String, Object> context = null;
    private String pageName =  null;
    private String pageTypeId = null;
    private String isQuery = null;
    private String containsQuery = null;
    private String conditionOperator = null;
    private List<String> promotedProductList = null;
    private List<String> demotedProductList = null;
    private List<String> removedProductList = null;
    private boolean ignoreCondition = false;
    private String webSiteId = null;
    private boolean enabled = false;
    private String error = null;

    /**
     * Search Tune Constructor
     * @param delegator
     * @param id
     * @param context
     */
    public SearchPage(Delegator delegator, String id, Map<String, Object> context) {
        this.delegator = delegator;
        this.id = UtilValidate.isNotEmpty(id) ? id : null;
        this.context = context;
        this.pageName = UtilValidate.isNotEmpty(context.get("pageName")) ? (String) context.get("pageName") : null;
        this.pageTypeId = UtilValidate.isNotEmpty(context.get("pageTypeId")) ? (String) context.get("pageTypeId") : null;
        this.conditionOperator = UtilValidate.isNotEmpty(context.get("conditionOperator")) ? (String) context.get("conditionOperator") : null;
        this.promotedProductList = UtilValidate.isNotEmpty(context.get("promotedProductList")) ? new Gson().fromJson((String) context.get("promotedProductList"), ArrayList.class) : null;
        this.demotedProductList = UtilValidate.isNotEmpty(context.get("demotedProductList")) ? new Gson().fromJson((String) context.get("demotedProductList"), ArrayList.class) : null;
        this.removedProductList = UtilValidate.isNotEmpty(context.get("removedProductList")) ? new Gson().fromJson((String) context.get("removedProductList"), ArrayList.class) : null;
        this.ignoreCondition = "Y".equalsIgnoreCase((String) context.get("ignoreCondition"));
        this.webSiteId = UtilValidate.isNotEmpty(context.get("webSiteId")) ? (String) context.get("webSiteId") : null;
        this.enabled = "Y".equalsIgnoreCase((String) context.get("enabled"));

        this.isQuery = UtilValidate.isNotEmpty(context.get("isQuery")) ? SearchTranslator.processQueryRules(this.delegator, (String) context.get("isQuery"), this.webSiteId, this.pageTypeId) : null;
        this.containsQuery = UtilValidate.isNotEmpty(context.get("containsQuery")) ? SearchTranslator.processQueryRules(this.delegator, (String) context.get("containsQuery"), this.webSiteId, this.pageTypeId) : null;
    }

    /**
     * Save the page to the database
     * @return
     * @throws GenericEntityException
     */
    public GenericValue savePage() throws GenericEntityException {
        GenericValue page = null;
        if(this.id == null) {
            this.id = this.makeId();
            page = this.delegator.makeValue("SearchPage", UtilMisc.toMap("id", this.id));
        } else {
            page = EntityQuery.use(this.delegator).from("SearchPage").where("id", this.id).queryOne();
        }

        page.set("pageName", this.pageName);
        page.set("pageTypeId", this.pageTypeId);
        page.set("isQuery", this.isQuery);
        page.set("containsQuery", this.containsQuery);
        page.set("conditionOperator", this.conditionOperator);
        page.set("promotedProductList", new Gson().toJson(this.promotedProductList));
        page.set("demotedProductList", new Gson().toJson(this.demotedProductList));
        page.set("removedProductList", new Gson().toJson(this.removedProductList));
        page.set("conditionData", new Gson().toJson(SearchUtil.cleanConditions(this.context, this.webSiteId)));
        page.set("ignoreCondition", this.ignoreCondition ? "Y" : "N");
        page.set("webSiteId", this.webSiteId);
        page.set("enabled", this.enabled ? "Y" : "N");
        this.delegator.createOrStore(page);

        //clear search tune cache on any changes
        //SearchData.invalidateCache();

        return page;
    }

    /**
     * Make Unique Id for Tune
     * @return
     */
    private String makeId() {
        return this.delegator.getNextSeqId("SearchPage");
    }

    /**
     * Check if tune exists
     * @return
     * @throws GenericEntityException
     */
    private boolean exists() throws GenericEntityException {
        return (getSearchPage() != null) ? true : false;
    }

    /**
     * Check if tune exists
     * @return
     * @throws GenericEntityException
     */
    private GenericValue getSearchPage() throws GenericEntityException {
        return EntityQuery.use(this.delegator).from("SearchPage").where("id", this.id).queryOne();
    }
}