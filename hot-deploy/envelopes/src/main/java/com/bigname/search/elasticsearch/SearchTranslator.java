package com.bigname.search.elasticsearch;

import java.util.*;

import com.bigname.search.elasticsearch.util.LegacyUtil;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;

import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.search.sort.SortOrder;
import com.google.gson.Gson;

import com.bigname.search.SearchHelper;
import com.bigname.search.elasticsearch.util.SearchUtil;
import com.bigname.search.elasticsearch.enums.SearchConditionOperator;
import com.bigname.search.elasticsearch.enums.SearchPageType;
import com.bigname.search.elasticsearch.enums.SearchSortType;
import com.envelopes.util.EnvUtil;

/**
 * Created by shoab on 5/12/17.
 */
public class SearchTranslator {
    public static final String module = SearchTranslator.class.getName();

    public static final int MAX_SIZE = 5000;

    private Map<String, Object> parameters = new HashMap<>();
    private Delegator delegator = null;

    /**
     * Hold the web site id requesting the page
     */
    protected String webSiteId = null;

    /**
     * Hold the page type the query is requested from
     */
    protected SearchPageType pageTypeId = SearchPageType.ANY_PAGE;

    /**
     * Hold the full searched query
     */
    protected String query = null;
    protected String originalQuery = null;

    /**
     * Hold the terms from the full searched query
     */
    protected String[] queryTerms = null;

    /**
     * Is a single term search or not
     */
    protected boolean isSingleTerm = false;

    /**
     * Is this a wild card search
     */
    protected boolean isWildCard = false;

    /**
     * Contains hyphen
     */
    protected boolean hasHyphen = false;

    /**
     * List of documents to search for specifically
     */
    protected List<String> documentIds = new ArrayList<>();

    /**
     * Hold all the filters being search. Used to apply the selected facet/filters to elastic
     */
    protected Map<Object, Object> filters = new LinkedHashMap<>();

    /**
     * Hold all the filters being search to compare to database.
     * Clean version to compare and determine if a corresponding banner/redirect/tune is available
     */
    protected Map<String, Object> searchTuningFilters = new TreeMap<>();

    /**
     * Hold all the filters being search to compare to database.
     * Clean version to output to UI
     */
    protected Map<String, List<Map<String, String>>> readableFilters = new TreeMap<>();

    /**
     * Sorting method for documents
     */
    protected List<SearchSortType> sort = new ArrayList<>();
    protected List<SortOrder> sortDirection = new ArrayList<>();

    /**
     * Hold full product list from a queried result.
     * This will have tuned data built in and will be passed to search instead of doing query
     */
    protected List<String> tunedProductList = new ArrayList<>();

    /**
     * Pagination related parameters
     */
    protected int size = 50;
    protected int page = 0;
    protected int from = 0;

    /**
     * Should elastic send back an explanation of the results
     */
    protected boolean explain = false;

    /**
     * If we want only aggregation data and want to ignore the search hits/results
     */
    boolean ignoreHits = false;

    /**
     * Default SearchTranslator Constructor
     * @param delegator
     * @param parameters
     * @param webSiteId
     */
    @Deprecated
    public SearchTranslator(Delegator delegator, Map<String, Object> parameters, String webSiteId) {
        this(delegator, parameters, webSiteId, SearchPageType.ANY_PAGE);
    }

    /**
     * Default SearchTranslator Constructor
     * @param delegator
     * @param parameters
     * @param webSiteId
     * @param searchPageType
     */
    public SearchTranslator(Delegator delegator, Map<String, Object> parameters, String webSiteId, SearchPageType searchPageType) {
        this.parameters = parameters;
        this.delegator = delegator;
        this.webSiteId = webSiteId;

        try {
            this.pageTypeId = searchPageType;
            this.setQueryString();
            this.getDocumentIds();
            this.setFilters();
            this.setSize();
            this.setPage();
            this.setSorting();
            this.setExplain();
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to fetch filters.", module);
        }
    }

    /**
     * Whether results should explain score
     */
    private void setExplain() {
        this.explain = "true".equals(SearchHelper.getUrlParameter(this.parameters.get("explain")));
    }

    /**
     * Specific documents to get data for
     */
    private void getDocumentIds() {
        String documentIds = SearchHelper.getUrlParameter(this.parameters.get("docids"));

        if(UtilValidate.isNotEmpty(documentIds)) {
            this.documentIds = Arrays.asList(documentIds.split(","));
        }
    }

    /**
     * Set the query phrase to be used.
     * Empty or Asterisks means a "match_all" query will be used.
     */
    private void setQueryString() {
        String phrase = SearchHelper.getUrlParameter(this.parameters.get("w"));

        if((UtilValidate.isNotEmpty(phrase) && "*".equals(phrase)) || UtilValidate.isEmpty(phrase)) {
            this.isWildCard = true;
        } else if(UtilValidate.isNotEmpty(phrase) && !"*".equals(phrase)) {
            this.isSingleTerm = !(phrase.trim()).contains(" ");
            this.query = SearchUtil.cleanQuery(phrase);
            this.queryTerms = SearchUtil.splitTerms(this.query);
            this.hasHyphen = this.query.contains("-");

            //hold on to original query
            this.originalQuery = this.query;
        }

        //apply any rules to the processed terms/parameters
        SearchRules rule = new SearchRules(this);
    }

    public String getQueryString() {
        return this.query;
    }

    /**
     * Set the size of the hits to be returned
     */
    private void setSize() {
        if(this.ignoreHits) {
            this.size = 0;
        } else {
            String size = SearchHelper.getUrlParameter(this.parameters.get("cnt"));
            if (UtilValidate.isNotEmpty(size) && NumberUtils.isNumber(size)) {
                int sizeInt = Integer.valueOf(size);
                if (sizeInt % 50 == 0 && sizeInt <= MAX_SIZE) {
                    this.size = sizeInt;
                }
            }
        }
    }

    /**
     * Set the page from which hits should be returned
     */
    private void setPage() {
        String page = SearchHelper.getUrlParameter(this.parameters.get("page"));

        if(UtilValidate.isNotEmpty(page) && NumberUtils.isNumber(page)) {
            this.page = Integer.valueOf(page);
            this.from = this.size * this.page;
        }
    }

    /**
     * Set the sorting in which hits should be returned
     * NOTICE: the final query will call SearchBuilder.addFieldValueFactor() to add an additional boost based on sales ranking
     */
    private void setSorting() {
        String sort = SearchHelper.getUrlParameter(this.parameters.get("sort"));
        String direction = "asc";
        SearchSortType sst = null;

        if(UtilValidate.isNotEmpty(sort)) {
            //hardcoded override to support legacy search url
            sort = sort.replace("price", "baseprice");
            sort = sort.replace("SIZE_SMALL", "measurement");
            sort = sort.replace("sales", "salesrank");
            sort = sort.replace("date", "createdstamp");

            if(sort.contains(" ") || sort.contains("+")) {
                sort = sort.replace("+", " ");
                direction = UtilValidate.isNotEmpty(sort.substring(sort.indexOf(" ") + 1)) ? sort.substring(sort.indexOf(" ") + 1) : direction;
            } else {
                sort = sort + " ";
            }

            sst = SearchSortType.getValueOf(sort.substring(0, sort.indexOf(" ")));
            if(sst != null) {
                this.sort.add(sst);
                this.sortDirection.add(SortOrder.fromString((UtilValidate.isNotEmpty(direction) && (direction.equalsIgnoreCase("asc") || direction.equalsIgnoreCase("desc"))) ? direction : "asc"));

                //if its avg rating sort, we need to add the number of reviews to it as well
                if(sst == SearchSortType.AVGRATING) {
                    this.sort.add(SearchSortType.NUMOFREVIEWS);
                    this.sortDirection.add(SortOrder.DESC);
                }
            }
        }

        //if category search
        if(this.pageTypeId == SearchPageType.CATEGORY_PAGE) {
            //if no sorting is specified, default to size
            /*if(this.sort.size() == 0) {
                sst = SearchSortType.MEASUREMENT;
                this.sort.add(sst);
                this.sortDirection.add(SortOrder.ASC);
            }*/

            //if we are sorting by size, then we need to add score to sorting
            if(sst != null && sst == SearchSortType.MEASUREMENT) {
                this.sort.add(SearchSortType.SALESRANK);
                this.sortDirection.add(SortOrder.DESC);
            }
        }
    }

    /**
     * Process the filters that are in the url as part of the "af" parameter
     * Retrieve the id conversion value to pass back to elasticsearch
     */
    @SuppressWarnings("unchecked")
    private void setFilters() throws GenericEntityException {
        String appliedFilters = SearchHelper.getUrlParameter(this.parameters.get("af"));

        /**
         * Set facets for category page to support legacy category urls
         */
        String categoryId = SearchHelper.getUrlParameter(this.parameters.get("category_id")); //If a category page is selected and a category ID representing a DB ProductCategory entry value
        if(UtilValidate.isNotEmpty(categoryId)) {
            String cleanCategoryId = SearchUtil.cleanFacet(categoryId, false);
            // With the addition of Elastic Search, some breadcrumb links pointed to style (st) and not category (use) but uses categoryId filter.

            //if it is a style category, then append the parent category ("use") so that filters are appropriate
            GenericValue category = EntityQuery.use(this.delegator).from("ProductCategory").where("productCategoryId", categoryId).cache().queryOne();
            if(category != null && category.getString("primaryParentCategoryId") != null) {
                appliedFilters = (UtilValidate.isEmpty(appliedFilters)) ? "use:" + SearchUtil.cleanFacet(category.getString("primaryParentCategoryId"), false) : appliedFilters + " use:" + SearchUtil.cleanFacet(category.getString("primaryParentCategoryId"), false);
            }

            if (getFacet(this.delegator, "use", cleanCategoryId) != null) {
                appliedFilters = (UtilValidate.isEmpty(appliedFilters)) ? "use:" + cleanCategoryId : appliedFilters + " use:" + cleanCategoryId;
            }

            if (getFacet(this.delegator, "st", cleanCategoryId) != null) {
                appliedFilters = (UtilValidate.isEmpty(appliedFilters)) ? "st:" + cleanCategoryId : appliedFilters + " st:" + cleanCategoryId;
            }
        }

        if(UtilValidate.isNotEmpty(appliedFilters)) {
            String[] filters = appliedFilters.split(" ");
            for (String filter : filters) {
                String[] filterIdAndValue = filter.split(":");

                if(filterIdAndValue.length <= 1) {
                    continue;
                }

                String filterId = LegacyUtil.getNewFilterId(filterIdAndValue[0]);
                String filterValue = filterIdAndValue[1];

                GenericValue searchFilter = getFacet(this.delegator, filterId, filterValue);
                if (searchFilter != null) {
                    if (this.filters.containsKey(((Map<String, String>) SearchField.webSiteData.get(this.webSiteId).get("filterFieldMap")).get(filterId))) {
                        /*
                         * If the filter has already been added and another is found of the same filter id
                         * Create an array of the filter values and add the new value
                         */
                        if (this.filters.get(((Map<String, String>) SearchField.webSiteData.get(this.webSiteId).get("filterFieldMap")).get(filterId)) instanceof String) {
                            List<String> tempList = new ArrayList<>();
                            tempList.add((String) this.filters.get(((Map<String, String>) SearchField.webSiteData.get(this.webSiteId).get("filterFieldMap")).get(filterId)));
                            tempList.add(searchFilter.getString("facetName"));
                            this.filters.put(((Map<String, String>) SearchField.webSiteData.get(this.webSiteId).get("filterFieldMap")).get(filterId), tempList);

                            List<String> tempIdList = new ArrayList<>();
                            tempIdList.add((String) this.searchTuningFilters.get(filterId));
                            tempIdList.add(searchFilter.getString("facetId"));
                            this.searchTuningFilters.put(filterId, tempIdList);
                        } else if (this.filters.get(((Map<String, String>) SearchField.webSiteData.get(this.webSiteId).get("filterFieldMap")).get(filterId)) instanceof List) {
                            List<String> tempList = (List) this.filters.get(((Map<String, String>) SearchField.webSiteData.get(this.webSiteId).get("filterFieldMap")).get(filterId));
                            tempList.add(searchFilter.getString("facetName"));
                            this.filters.put(((Map<String, String>) SearchField.webSiteData.get(this.webSiteId).get("filterFieldMap")).get(filterId), tempList);

                            List<String> tempIdList = (List) this.searchTuningFilters.get(filterId);
                            tempIdList.add(searchFilter.getString("facetId"));
                            this.searchTuningFilters.put(filterId, tempIdList);
                        }

                        /*
                         * List of applied filters for readable UI
                         */
                        List<Map<String, String>> tempUIList = (List) this.readableFilters.get(filterId);
                        tempUIList.add(UtilMisc.toMap(searchFilter.getString("facetId"), searchFilter.getString("facetName")));
                        this.readableFilters.put(filterId, tempUIList);
                    } else {
                        this.filters.put(((Map<String, String>) SearchField.webSiteData.get(this.webSiteId).get("filterFieldMap")).get(filterId), searchFilter.getString("facetName"));
                        this.searchTuningFilters.put(filterId, searchFilter.getString("facetId"));
                        this.readableFilters.put(filterId, UtilMisc.toList(UtilMisc.toMap(searchFilter.getString("facetId"), searchFilter.getString("facetName"))));
                    }
                }
            }
        }
    }

    /**
     * Return all selected filters
     * @return
     */
    public Map<String, List<Map<String, String>>> getFilters() {
        return this.readableFilters;
    }

    /**
     * Create a param list to append to a url for all filters available
     * @return
     */
    public String getUrl() {
        boolean hasParam = false;
        StringBuilder url = new StringBuilder("");

        //add search term
        if(!this.isWildCard) {
            url.append("w=").append(this.query);
            hasParam = true;
        }

        //add filters
        if(UtilValidate.isNotEmpty(this.readableFilters)) {
            boolean hasFilter = false;
            url.append(hasParam ? "&" : "").append("af=");
            for (Map.Entry<String, List<Map<String, String>>> params : this.readableFilters.entrySet()) {
                List<Map<String, String>> values = params.getValue();
                for (Map<String, String> value : values) {
                    for (Map.Entry<String, String> filter : value.entrySet()) {
                        url.append(hasFilter ? " " : "").append(params.getKey()).append(":").append(filter.getKey());
                        hasFilter = true;
                    }
                }
            }

            hasParam = true;
        }

        //add sorting
        if(UtilValidate.isNotEmpty(SearchHelper.getUrlParameter(this.parameters.get("sort")))) {
            url.append(hasParam ? "&" : "").append("sort=").append(SearchHelper.getUrlParameter(this.parameters.get("sort")));
            hasParam = true;
        }

        //add hits per page
        if(UtilValidate.isNotEmpty(SearchHelper.getUrlParameter(this.parameters.get("cnt")))) {
            url.append(hasParam ? "&" : "").append("cnt=").append(Integer.valueOf(this.size).toString());
            hasParam = true;
        }

        //add page
        if(UtilValidate.isNotEmpty(SearchHelper.getUrlParameter(this.parameters.get("page")))) {
            url.append(hasParam ? "&" : "").append("page=").append(Integer.valueOf(this.page).toString());
        }


        return url.toString().equals("?") ? null : url.toString();
    }

    /**
     * Set the web site id requesting the search
     * @param webSiteId
     */
    protected void setWebSiteId(String webSiteId) {
        this.webSiteId = webSiteId;
    }

    /**
     * Set value to only get aggregations
     * @param aggsOnly
     */
    public void setAggsOnly(boolean aggsOnly) {
        this.ignoreHits = aggsOnly;
    }

    /**
     * Get a banner search query match from database
     * @return
     */
    public GenericValue getBannerMatch() {
        //todo TOP banner position is hardcoded, make dynamic
        SearchData sd = SearchData.getInstance();
        String queryId = SearchBanner.makeExactMatchLookupId(this.pageTypeId.toString(), "POSITION_TOP", this.query, SearchConditionOperator.AND.toString(), this.searchTuningFilters, this.webSiteId);

        if(sd.getBannerData().containsKey(queryId)) { //check for exact match
            return sd.getBannerData().get(queryId);
        } else { //check for partial match
            GenericValue bestMatch = this.processMatchCriteria(sd.getBannerData());

            if(bestMatch != null) {
                return bestMatch;
            }
        }

        return null;
    }

    /**
     * Get a redirect search query match from database
     * @return
     */
    protected GenericValue getRedirectMatch() {
        SearchData sd = SearchData.getInstance();
        String queryId = SearchRedirect.makeExactMatchLookupId(this.pageTypeId.toString(), this.query, SearchConditionOperator.AND.toString(), this.searchTuningFilters, this.webSiteId);

        if(sd.getRedirectData().containsKey(queryId)) { //check for exact match
            return sd.getRedirectData().get(queryId);
        } else { //check for partial match
            GenericValue bestMatch = this.processMatchCriteria(sd.getRedirectData());

            if(bestMatch != null) {
                return bestMatch;
            }
        }

        return null;
    }

    /**
     * Get a tuned search query match from database
     * @return
     */
    protected GenericValue getTuningMatch() {
        SearchData sd = SearchData.getInstance();
        String queryId = SearchTune.makeExactMatchLookupId(this.pageTypeId.toString(), this.query, SearchConditionOperator.AND.toString(), this.searchTuningFilters, this.webSiteId);

        if(sd.getTuningData().containsKey(queryId)) { //check for exact match
            return sd.getTuningData().get(queryId);
        } else { //check for partial match
            GenericValue bestMatch = this.processMatchCriteria(sd.getTuningData());

            if(bestMatch != null) {
                return bestMatch;
            }
        }

        return null;
    }

    /**
     * Get matched data
     * @param data
     * @return
     */
    @SuppressWarnings("unchecked")
    private GenericValue processMatchCriteria(Map<String, GenericValue> data) {
        int bestMatchScore = 0;
        GenericValue bestMatchData = null;
        for(Map.Entry<String, GenericValue> entry : data.entrySet()) {
            GenericValue gv = entry.getValue();

            //if the value being tested for is not for the current site, skip
            if(!this.webSiteId.equalsIgnoreCase(gv.getString("webSiteId"))) {
                continue;
            }

            boolean andOperator = gv.getString("conditionOperator").equals(SearchConditionOperator.AND.toString());
            boolean isQuery = UtilValidate.isNotEmpty(gv.getString("isQuery"));
            boolean containsQuery = UtilValidate.isNotEmpty(gv.getString("containsQuery"));

            //if its exact match data entry and is a "is" query or is an exact match without any query, break out because we are only trying to find partial matches
            if((isQuery && andOperator) || (!isQuery && !containsQuery && andOperator)) {
                continue;
            }

            int phraseScore = 0;
            int filterScore = 0;

            if(containsQuery && andOperator) { //if its a contains query with AND operator
                if (!this.isWildCard) {
                    if (SearchData.hasPartialPhrase(gv, this.query)) {
                        phraseScore++;
                    }
                }

                //check if filters are exact
                if(UtilValidate.isNotEmpty(gv.getString("conditionData")) && gv.getString("conditionData").equalsIgnoreCase(new Gson().toJson(SearchUtil.cleanConditions(this.searchTuningFilters, this.webSiteId)))) {
                    filterScore++;
                }

                //if either phrase is missing or filter is missing, we didnt find a match
                if(phraseScore == 0 || filterScore == 0) {
                    continue;
                }
            } else { //its a OR operator
                //check phrase
                if (!this.isWildCard) {
                    if (SearchData.hasExactPhrase(gv, this.query)) {
                        phraseScore++;
                    } else if (SearchData.hasPartialPhrase(gv, this.query)) {
                        phraseScore++;
                    }
                }
                //check filters
                Map<String, Object> filters = new Gson().fromJson(gv.getString("conditionData"), TreeMap.class);
                for (Map.Entry<String, Object> filter : filters.entrySet()) {
                    if (this.searchTuningFilters.containsKey(filter.getKey())) {
                        if (filter.getValue() instanceof List) {
                            List filterValueList = (List) filter.getValue();
                            for (Object filterValue : filterValueList) {
                                if (this.searchTuningFilters.get(filter.getKey()) instanceof List && ((List) this.searchTuningFilters.get(filter.getKey())).contains(filterValue)) {
                                    filterScore++;
                                } else if (this.searchTuningFilters.get(filter.getKey()).equals(filterValue)) {
                                    filterScore++;
                                }
                            }
                        } else {
                            if (this.searchTuningFilters.get(filter.getKey()) instanceof List && ((List) this.searchTuningFilters.get(filter.getKey())).contains(filter.getValue())) {
                                filterScore++;
                            } else if (this.searchTuningFilters.get(filter.getKey()).equals(filter.getValue())) {
                                filterScore++;
                            }
                        }
                    }
                }
            }

            int currentScore = filterScore + phraseScore;
            if(currentScore > bestMatchScore) {
                bestMatchData = gv;
                bestMatchScore = currentScore;
            }
        }

        return bestMatchData;
    }

    /**
     * Set the tuned products
     * @param productList
     */
    protected void setTunedProductList(List<String> productList) {
        this.tunedProductList = productList;
    }

    /**
     * Sort the tuned product ids and remove and reinsert in correct positions
     * @param tune
     */
    protected void sortTuneProductList(GenericValue tune) {
        if(tune != null) {
            List<String> promotedProductList = UtilValidate.isNotEmpty(tune.getString("promotedProductList")) ? new Gson().fromJson(tune.getString("promotedProductList"), ArrayList.class) : new ArrayList<>();
            List<String> demotedProductList = UtilValidate.isNotEmpty(tune.getString("demotedProductList")) ? new Gson().fromJson(tune.getString("demotedProductList"), ArrayList.class) : new ArrayList<>();
            List<String> removedProductList = UtilValidate.isNotEmpty(tune.getString("removedProductList")) ? new Gson().fromJson(tune.getString("removedProductList"), ArrayList.class) : new ArrayList<>();

            /**
             * Remove any products not wanted in list
             */
            if(removedProductList.size() > 0) {
                for(int i = 0; i < removedProductList.size(); i++) {
                    this.tunedProductList.remove(removedProductList.get(i));
                }
            }

            /**
             * Take all promoted products, remove them and then reinsert them in the position they need to be in
             */
            if(promotedProductList.size() > 0) {
                for(int i = 0; i < promotedProductList.size(); i++) {
                    if(UtilValidate.isEmpty(promotedProductList.get(i))) {
                        continue;
                    }
                    this.tunedProductList.remove(promotedProductList.get(i));
                    if(this.tunedProductList.size() > i-1) {
                        this.tunedProductList.add(i, promotedProductList.get(i));
                    } else {
                        this.tunedProductList.add(promotedProductList.get(i));
                    }
                }
            }

            /**
             * Take all demoted products, remove them and then reinsert them at the end of the products
             */
            if(demotedProductList.size() > 0) {
                for(int i = 0; i < demotedProductList.size(); i++) {
                    this.tunedProductList.remove(demotedProductList.get(i));
                    this.tunedProductList.add(demotedProductList.get(i));
                }
            }
        }
    }

    /**
     * Generate formatted string of selected filters
     * @return
     */
    public String getPageTitle() {
        StringBuilder title = new StringBuilder("");

        if(this.query != null) {
            title.append("Search Results for ").append(this.query);
        } else {
            Iterator filterIterator = this.filters.entrySet().iterator();
            while (filterIterator.hasNext()) {
                Map.Entry pair = (Map.Entry) filterIterator.next();
                title.append((title.length() > 0 ? ", " : "") + ((pair.getValue() instanceof List) ? String.join(", ", (List) pair.getValue()) : pair.getValue()));
            }
        }

        return title.toString();
    }

    /**
     * Check if a style category is present
     * @return
     */
    public boolean hasStyle() {
        return this.searchTuningFilters.containsKey("st");
    }

    /**
     * Check if a use category is present
     * @return
     */
    public boolean hasUse() {
        return this.searchTuningFilters.containsKey("use");
    }

    /**
     * Get all applied filters
     * @return
     */
    public Map<String, Object> getSearchTuningFilters() {
        return this.searchTuningFilters;
    }

    /**
     * Return DB value for a facet/filter
     * @param delegator
     * @param filterId
     * @param filterValue
     * @return
     * @throws GenericEntityException
     */
    public static GenericValue getFacet(Delegator delegator, String filterId, String filterValue) throws GenericEntityException {
        return EntityQuery.use(delegator).from("SearchFacet").where("facetTypeId", filterId, "facetId", filterValue).cache().queryOne();
    }

    /**
     * Return the search page type
     * @return
     */
    public String getSearchPageType() {
        return this.pageTypeId.name();
    }

    /**
     * Return cleaned and processed query term
     * @param delegator
     * @param str
     * @param webSiteId
     * @param pageTypeId
     * @return
     */
    public static String processQueryRules(Delegator delegator, String str, String webSiteId, String pageTypeId) {
        StringBuilder sb = new StringBuilder("");
        if(UtilValidate.isNotEmpty(str)) {
            String[] queryTerms = str.split(",");
            for(String query : queryTerms) {
                SearchTranslator translator = new SearchTranslator(delegator, UtilMisc.toMap("w", query), webSiteId, SearchPageType.getValueOf(pageTypeId));
                sb.append(sb.toString().equalsIgnoreCase("") ? "" : "," ).append(translator.getQueryString());
            }
        }

        return str != null ? sb.toString() : null;
    }
}