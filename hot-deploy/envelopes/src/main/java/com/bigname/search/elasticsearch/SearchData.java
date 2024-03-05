package com.bigname.search.elasticsearch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.DelegatorFactory;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.util.EntityQuery;
import com.google.gson.Gson;

import com.bigname.search.elasticsearch.enums.SearchConditionOperator;
import com.bigname.search.elasticsearch.enums.SearchPageType;
import com.envelopes.util.EnvUtil;

public class SearchData {
    public static final String module = SearchData.class.getName();

    private Delegator delegator = null;
    private Map<String, GenericValue> bannerData = new HashMap<>();
    private Map<String, GenericValue> redirectData = new HashMap<>();
    private Map<String, GenericValue> tuningData = new HashMap<>();

    private static SearchData searchData = null;
    private SearchData() {
        initialize();
    }

    public static SearchData getInstance() {
        if (searchData == null) {
            synchronized (SearchData.class) {
                if (searchData == null) {
                    searchData = new SearchData();
                }
            }
        }
        return searchData;
    }

    public static void invalidateCache() {
        searchData = null;
        getInstance();
    }

    private void initialize() {
        try {
            this.delegator = DelegatorFactory.getDelegator("default");
            this.processBannerData(bannerData, EntityQuery.use(this.delegator).from("SearchBanner").where(EntityCondition.makeCondition("enabled", EntityOperator.NOT_EQUAL, "N")).queryList());
            this.processRedirectData(redirectData, EntityQuery.use(this.delegator).from("SearchRedirect").where(EntityCondition.makeCondition("enabled", EntityOperator.NOT_EQUAL, "N")).queryList());
            this.processTuningData(tuningData, EntityQuery.use(this.delegator).from("SearchTune").where(EntityCondition.makeCondition("enabled", EntityOperator.NOT_EQUAL, "N")).queryList());
        } catch (GenericEntityException e) {
            Debug.logError(e, "Error trying to get search data.", module);
        }
    }

    public Map<String, GenericValue> getBannerData() {
        return this.bannerData;
    }

    public Map<String, GenericValue> getRedirectData() {
        return this.redirectData;
    }

    public Map<String, GenericValue> getTuningData() {
        return this.tuningData;
    }

    public static void main(String[] args) {
        getInstance();
    }

    /**
     * Get banner matches for product searches
     * @param data
     * @param list
     */
    @SuppressWarnings("unchecked")
    private void processBannerData(Map<String, GenericValue> data, List<GenericValue> list) {
        if(UtilValidate.isNotEmpty(list)) {
            for(GenericValue entry : list) {
                if(entry.getString("pageTypeId").equals(SearchPageType.SEARCH_PAGE.toString()) || entry.getString("pageTypeId").equals(SearchPageType.CATEGORY_PAGE.toString())) {
                    //SEARCH OR CATEGORY PAGE MATCH
                    SearchPageType pageType = SearchPageType.getValueOf(entry.getString("pageTypeId"));
                    if(entry.getString("conditionOperator").equals(SearchConditionOperator.AND.toString())) {
                        //AND MATCH
                        if(UtilValidate.isNotEmpty(entry.getString("isQuery"))) {
                            //HAS A SEARCH PHRASE
                            String[] queries = entry.getString("isQuery").split(",");
                            for (String query : queries) {
                                data.put(SearchBanner.makeExactMatchLookupId(pageType.toString(), entry.getString("positionTypeId"), query, SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                            }
                        } else if(UtilValidate.isNotEmpty(entry.getString("containsQuery"))) {
                            //HAS CONTAINS PHRASE (REALLY A PLACE HOLDER, CONTAINS QUERY ARE CREATED VIA SCORE SEE SearchTranslator CLASS)
                            data.put(SearchBanner.makeExactMatchLookupId(pageType.toString(), entry.getString("positionTypeId"), "CQ-" + entry.getString("containsQuery"), SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                        } else {
                            //DOES NOT HAVE SEARCH PHRASE
                            data.put(SearchBanner.makeExactMatchLookupId(pageType.toString(), entry.getString("positionTypeId"), null, SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                        }
                    } else {
                        //OR MATCH
                        data.put(EnvUtil.MD5(entry.toString()), entry);
                    }
                } else if(entry.getString("pageTypeId").equals(SearchPageType.ANY_PAGE.toString())) {
                    //ANY PAGE MATCH
                    if(entry.getString("conditionOperator").equals(SearchConditionOperator.AND.toString())) {
                        //AND MATCH
                        if(UtilValidate.isNotEmpty(entry.getString("isQuery"))) {
                            //HAS A SEARCH PHRASE
                            String[] queries = entry.getString("isQuery").split(",");
                            for(String query : queries) {
                                data.put(SearchBanner.makeExactMatchLookupId(SearchPageType.CATEGORY_PAGE.toString(), entry.getString("positionTypeId"), query, SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                                data.put(SearchBanner.makeExactMatchLookupId(SearchPageType.SEARCH_PAGE.toString(), entry.getString("positionTypeId"), query, SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                            }
                        } else if(UtilValidate.isNotEmpty(entry.getString("containsQuery"))) {
                            //HAS CONTAINS PHRASE (REALLY A PLACE HOLDER, CONTAINS QUERY ARE CREATED VIA SCORE SEE SearchTranslator CLASS)
                            data.put(SearchBanner.makeExactMatchLookupId(SearchPageType.CATEGORY_PAGE.toString(), entry.getString("positionTypeId"), "CQ-" + entry.getString("containsQuery"), SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                            data.put(SearchBanner.makeExactMatchLookupId(SearchPageType.SEARCH_PAGE.toString(), entry.getString("positionTypeId"), "CQ-" + entry.getString("containsQuery"), SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                        } else {
                            //DOES NOT HAVE SEARCH PHRASE
                            data.put(SearchBanner.makeExactMatchLookupId(SearchPageType.CATEGORY_PAGE.toString(), entry.getString("positionTypeId"), null, SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                            data.put(SearchBanner.makeExactMatchLookupId(SearchPageType.SEARCH_PAGE.toString(), entry.getString("positionTypeId"), null, SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                        }
                    } else {
                        //OR MATCH
                        GenericValue categoryEntry = this.delegator.makeValue("SearchBanner", entry.getAllFields());
                        categoryEntry.set("pageTypeId", SearchPageType.CATEGORY_PAGE.toString());
                        data.put(EnvUtil.MD5(categoryEntry.toString()), categoryEntry);

                        GenericValue searchEntry = this.delegator.makeValue("SearchBanner", entry.getAllFields());
                        searchEntry.set("pageTypeId", SearchPageType.SEARCH_PAGE.toString());
                        data.put(EnvUtil.MD5(searchEntry.toString()), searchEntry);
                    }
                }
            }
        }
    }

    /**
     * Get redirect matches for product searches
     * @param data
     * @param list
     */
    @SuppressWarnings("unchecked")
    private void processRedirectData(Map<String, GenericValue> data, List<GenericValue> list) {
        if(UtilValidate.isNotEmpty(list)) {
            for(GenericValue entry : list) {
                if(entry.getString("pageTypeId").equals(SearchPageType.SEARCH_PAGE.toString()) || entry.getString("pageTypeId").equals(SearchPageType.CATEGORY_PAGE.toString())) {
                    //SEARCH OR CATEGORY PAGE MATCH
                    SearchPageType pageType = SearchPageType.getValueOf(entry.getString("pageTypeId"));
                    if(entry.getString("conditionOperator").equals(SearchConditionOperator.AND.toString())) {
                        //AND MATCH
                        if(UtilValidate.isNotEmpty(entry.getString("isQuery"))) {
                            //HAS A SEARCH PHRASE
                            String[] queries = entry.getString("isQuery").split(",");
                            for(String query : queries) {
                                data.put(SearchRedirect.makeExactMatchLookupId(pageType.toString(), query, SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                            }
                        } else if(UtilValidate.isNotEmpty(entry.getString("containsQuery"))) {
                            //HAS CONTAINS PHRASE (REALLY A PLACE HOLDER, CONTAINS QUERY ARE CREATED VIA SCORE SEE SearchTranslator CLASS)
                            data.put(SearchRedirect.makeExactMatchLookupId(pageType.toString(), "CQ-" + entry.getString("containsQuery"), SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                        } else {
                            //DOES NOT HAVE SEARCH PHRASE
                            data.put(SearchRedirect.makeExactMatchLookupId(pageType.toString(), null, SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                        }
                    } else {
                        //OR MATCH
                        data.put(EnvUtil.MD5(entry.toString()), entry);
                    }
                } else if(entry.getString("pageTypeId").equals(SearchPageType.ANY_PAGE.toString())) {
                    //ANY PAGE MATCH
                    if(entry.getString("conditionOperator").equals(SearchConditionOperator.AND.toString())) {
                        //AND MATCH
                        if(UtilValidate.isNotEmpty(entry.getString("isQuery"))) {
                            //HAS A SEARCH PHRASE
                            String[] queries = entry.getString("isQuery").split(",");
                            for(String query : queries) {
                                data.put(SearchRedirect.makeExactMatchLookupId(SearchPageType.CATEGORY_PAGE.toString(), query, SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                                data.put(SearchRedirect.makeExactMatchLookupId(SearchPageType.SEARCH_PAGE.toString(), query, SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                            }
                        } else if(UtilValidate.isNotEmpty(entry.getString("containsQuery"))) {
                            //HAS CONTAINS PHRASE (REALLY A PLACE HOLDER, CONTAINS QUERY ARE CREATED VIA SCORE SEE SearchTranslator CLASS)
                            data.put(SearchRedirect.makeExactMatchLookupId(SearchPageType.CATEGORY_PAGE.toString(), "CQ-" + entry.getString("containsQuery"), SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                            data.put(SearchRedirect.makeExactMatchLookupId(SearchPageType.SEARCH_PAGE.toString(), "CQ-" + entry.getString("containsQuery"), SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                        } else {
                            //DOES NOT HAVE SEARCH PHRASE
                            data.put(SearchRedirect.makeExactMatchLookupId(SearchPageType.CATEGORY_PAGE.toString(), null, SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                            data.put(SearchRedirect.makeExactMatchLookupId(SearchPageType.SEARCH_PAGE.toString(), null, SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                        }
                    } else {
                        //OR MATCH
                        GenericValue categoryEntry = this.delegator.makeValue("SearchRedirect", entry.getAllFields());
                        categoryEntry.set("pageTypeId", SearchPageType.CATEGORY_PAGE.toString());
                        data.put(EnvUtil.MD5(categoryEntry.toString()), categoryEntry);

                        GenericValue searchEntry = this.delegator.makeValue("SearchRedirect", entry.getAllFields());
                        searchEntry.set("pageTypeId", SearchPageType.SEARCH_PAGE.toString());
                        data.put(EnvUtil.MD5(searchEntry.toString()), searchEntry);
                    }
                }
            }
        }
    }

    /**
     * Get tuning matches for product searches
     * @param data
     * @param list
     */
    @SuppressWarnings("unchecked")
    private void processTuningData(Map<String, GenericValue> data, List<GenericValue> list) {
        if(UtilValidate.isNotEmpty(list)) {
            for(GenericValue entry : list) {
                if(entry.getString("pageTypeId").equals(SearchPageType.SEARCH_PAGE.toString()) || entry.getString("pageTypeId").equals(SearchPageType.CATEGORY_PAGE.toString())) {
                    //SEARCH OR CATEGORY PAGE MATCH
                    SearchPageType pageType = SearchPageType.getValueOf(entry.getString("pageTypeId"));
                    if(entry.getString("conditionOperator").equals(SearchConditionOperator.AND.toString())) {
                        //AND MATCH
                        if(UtilValidate.isNotEmpty(entry.getString("isQuery"))) {
                            //HAS A SEARCH PHRASE
                            String[] queries = entry.getString("isQuery").split(",");
                            for(String query : queries) {
                                data.put(SearchTune.makeExactMatchLookupId(pageType.toString(), query, SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                            }
                        } else if(UtilValidate.isNotEmpty(entry.getString("containsQuery"))) {
                            //HAS CONTAINS PHRASE (REALLY A PLACE HOLDER, CONTAINS QUERY ARE CREATED VIA SCORE SEE SearchTranslator CLASS)
                            data.put(SearchTune.makeExactMatchLookupId(pageType.toString(), "CQ-" + entry.getString("containsQuery"), SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                        } else {
                            //DOES NOT HAVE SEARCH PHRASE
                            data.put(SearchTune.makeExactMatchLookupId(pageType.toString(), null, SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                        }
                    } else {
                        //OR MATCH
                        data.put(EnvUtil.MD5(entry.toString()), entry);
                    }
                } else if(entry.getString("pageTypeId").equals(SearchPageType.ANY_PAGE.toString())) {
                    //ANY PAGE MATCH
                    if(entry.getString("conditionOperator").equals(SearchConditionOperator.AND.toString())) {
                        //AND MATCH
                        if(UtilValidate.isNotEmpty(entry.getString("isQuery"))) {
                            //HAS A SEARCH PHRASE
                            String[] queries = entry.getString("isQuery").split(",");
                            for(String query : queries) {
                                data.put(SearchTune.makeExactMatchLookupId(SearchPageType.CATEGORY_PAGE.toString(), query, SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                                data.put(SearchTune.makeExactMatchLookupId(SearchPageType.SEARCH_PAGE.toString(), query, SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                            }
                        } else if(UtilValidate.isNotEmpty(entry.getString("containsQuery"))) {
                            //HAS CONTAINS PHRASE (REALLY A PLACE HOLDER, CONTAINS QUERY ARE CREATED VIA SCORE SEE SearchTranslator CLASS)
                            data.put(SearchTune.makeExactMatchLookupId(SearchPageType.CATEGORY_PAGE.toString(), "CQ-" + entry.getString("containsQuery"), SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                            data.put(SearchTune.makeExactMatchLookupId(SearchPageType.SEARCH_PAGE.toString(), "CQ-" + entry.getString("containsQuery"), SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                        } else {
                            //DOES NOT HAVE SEARCH PHRASE
                            data.put(SearchTune.makeExactMatchLookupId(SearchPageType.CATEGORY_PAGE.toString(), null, SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                            data.put(SearchTune.makeExactMatchLookupId(SearchPageType.SEARCH_PAGE.toString(), null, SearchConditionOperator.AND.toString(), new Gson().fromJson(entry.getString("conditionData"), TreeMap.class), entry.getString("webSiteId")), entry);
                        }
                    } else {
                        //OR MATCH
                        GenericValue categoryEntry = this.delegator.makeValue("SearchTune", entry.getAllFields());
                        categoryEntry.set("pageTypeId", SearchPageType.CATEGORY_PAGE.toString());
                        data.put(EnvUtil.MD5(categoryEntry.toString()), categoryEntry);

                        GenericValue searchEntry = this.delegator.makeValue("SearchTune", entry.getAllFields());
                        searchEntry.set("pageTypeId", SearchPageType.SEARCH_PAGE.toString());
                        data.put(EnvUtil.MD5(searchEntry.toString()), searchEntry);
                    }
                }
            }
        }
    }

    /**
     * Check if there are multiple exact match phrases
     * @param data
     * @return
     */
    private static boolean hasMultipleExactPhrases(GenericValue data) {
        return (UtilValidate.isNotEmpty(data.getString("isQuery")) && data.getString("isQuery").contains(","));
    }

    /**
     * Check if there are multiple partial match phrases
     * @param data
     * @return
     */
    private static boolean hasMultiplePartialPhrases(GenericValue data) {
        return (UtilValidate.isNotEmpty(data.getString("containsQuery")) && data.getString("containsQuery").contains(","));
    }

    /**
     * Check if entry contains the exact phrase being searched for
     * @param data
     * @param searchPhrase
     * @return
     */
    protected static boolean hasExactPhrase(GenericValue data, String searchPhrase) {
        if(hasMultipleExactPhrases(data)) {
            String[] phrases = data.getString("isQuery").split(",");
            for(String phrase : phrases) {
                if(UtilValidate.isNotEmpty(phrase) && phrase.equalsIgnoreCase(searchPhrase)) {
                    return true;
                }
            }
        } else {
            return (UtilValidate.isNotEmpty(data.getString("isQuery")) && (data.getString("isQuery")).equalsIgnoreCase(searchPhrase));
        }

        return false;
    }

    /**
     * Check if the search phrase is a partial match to the entry
     * @param data
     * @param searchPhrase
     * @return
     */
    protected static boolean hasPartialPhrase(GenericValue data, String searchPhrase) {
        return (UtilValidate.isNotEmpty(data.getString("containsQuery")) && searchPhrase.contains(data.getString("containsQuery")));
    }
}