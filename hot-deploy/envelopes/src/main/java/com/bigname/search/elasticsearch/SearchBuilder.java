package com.bigname.search.elasticsearch;

import java.util.*;
import java.util.concurrent.ExecutionException;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import com.google.gson.Gson;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.cache.clear.ClearIndicesCacheRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.index.query.functionscore.ScriptScoreFunctionBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.global.GlobalAggregationBuilder;
import org.elasticsearch.search.sort.SortBuilders;

import com.bigname.search.elasticsearch.client.SearchClient;
import com.bigname.search.elasticsearch.enums.SearchPageType;
import com.bigname.search.elasticsearch.util.DocumentUtil;
import com.bigname.search.elasticsearch.util.SearchUtil;

import com.envelopes.util.EnvUtil;


/**
 * Created by shoab on 5/9/17.
 */
public class SearchBuilder {
    public static final String module = SearchBuilder.class.getName();

    //parameters needed for client connections
    private static final String DEFAULT_AGG_NAME = "NotAvailable";

    //static data for building query
    private int maxRetries = 1;
    private static String INDEX = "bigname_search_alias";
    private static final String TYPE = "product";
    private static final Integer MAX_AGGREGATIONS = 500;
    private static final Integer SLOP_DISTANCE = 1;
    private static final Map<String, String> SEARCH_RESULT_OVERRIDES = new HashMap<>();

    static {
        try {
            //INDEX = UtilProperties.getPropertyValue("envelopes", "search.index.sandbox");
            INDEX = UtilProperties.getPropertyValue("envelopes", "search.index.production");
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError("Error, could not open envelopes.properties.", module);
        }
        try {
            String OVERRIDES = UtilProperties.getPropertyValue("envelopes", "search.result.overrides");
            if(UtilValidate.isNotEmpty(OVERRIDES)) {
                for(String override : OVERRIDES.split("\\|\\|")) {
                    String[] entry = override.trim().split("\\|");
                    SEARCH_RESULT_OVERRIDES.put(entry[0].trim().toLowerCase(), entry[1].trim());
                }
            }
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError("Error, could not open envelopes.properties.", module);
        }
    }

    private SearchRequestBuilder requestBuilder = null;
    private SearchResponse response = null;

    /**
     * Main query wrapper. Used to hold query and then use custom scoring function
     */
    private FunctionScoreQueryBuilder functionScoreQueryBuilder = null;

    /**
     * Main query. Has rules for what to search for and any filters that need to be applied
     */
    private BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

    /**
     * Aggregation builder to create filters/facets
     */
    private List<AggregationBuilder> aggregations = new ArrayList<>();

    private Delegator delegator = null;
    private Map<String, Object> healthData = new HashMap<>();
    private Map<String, Object> parameters = new HashMap<>();
    private SearchTranslator translator = null;
    private String webSiteId = null;
    private SearchPageType pageTypeId = SearchPageType.ANY_PAGE;
    private boolean mustMatchAll = true;
    private boolean bypassTuneCheck = false;
    private boolean getTunedResult = false;
    private boolean getSpecifiedDocuments = false;
    private Aggregations tunedAggregations = null;

    public SearchBuilder(Delegator delegator, Map<String, Object> parameters, String webSiteId) {
        this(delegator, parameters, webSiteId, SearchPageType.ANY_PAGE, false);
    }

    public SearchBuilder(Delegator delegator, Map<String, Object> parameters, String webSiteId, boolean bypassTuneCheck) {
        this(delegator, parameters, webSiteId, SearchPageType.ANY_PAGE, bypassTuneCheck);
    }

    public SearchBuilder(Delegator delegator, Map<String, Object> parameters, String webSiteId, SearchPageType searchPageType) {
        this(delegator, parameters, webSiteId, searchPageType, false);
    }

    public SearchBuilder(Delegator delegator, Map<String, Object> parameters, String webSiteId, SearchPageType searchPageType, boolean bypassTuneCheck) {
        this.delegator = delegator;
        String keyword = (String) parameters.get("w");
        if(UtilValidate.isNotEmpty(keyword) && SEARCH_RESULT_OVERRIDES.containsKey(keyword.toLowerCase())) {
            this.parameters = new HashMap<>();
            this.parameters.put("docids", SEARCH_RESULT_OVERRIDES.get(keyword.toLowerCase()));
        } else {
            this.parameters = parameters;
        }
        this.webSiteId = webSiteId;
        this.pageTypeId = searchPageType;
        this.bypassTuneCheck = bypassTuneCheck;

        this.translator = new SearchTranslator(this.delegator, this.parameters, this.webSiteId, this.pageTypeId);
    }

    /**
     * Execute the request to get the health of a cluster
     */
    public void executeHealth() {
        try {
            getClusterHealth();
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to fetch search.", module);
        }
    }

    /**
     * Execute the request to get the health of a cluster
     */
    public void executeCacheClear() {
        try {
            clearSearchCache();
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to fetch search.", module);
        }
    }

    /**
     * Execute a search request to retrieve results/hits
     */
    public void executeSearch() {
        try {
            /**
             * If documentids is passed, then the search will only give results for those documents
             */
            if(this.translator.documentIds.size() > 0) {
                this.getSpecifiedDocuments = true;
                this.translator.setTunedProductList(this.translator.documentIds);
                this.bypassTuneCheck = true;
                this.getTunedResult = true;
            }

            /**
             * Check to see if there is a matching tune
             */
            if (!this.bypassTuneCheck) {
                GenericValue tune = this.translator.getTuningMatch();
                /**
                 * If tune is found, then get the documents from the natural results
                 * And then apply the tuned products to the results
                 */
                if (tune != null) {
                    DocumentUtil getAllDocuments = new DocumentUtil(this.delegator, this.parameters, this.webSiteId, this.pageTypeId, true);
                    this.translator.setTunedProductList(getAllDocuments.getAllDocumentIds());
                    this.translator.sortTuneProductList(tune);
                    this.getTunedResult = true;
                    this.tunedAggregations = getAllDocuments.getAggregations();
                }
            }

            if (this.getTunedResult) {
                this.buildQuery();
                this.buildAggregations();
                this.buildRequest();
                this.addSorting();
            } else {
                this.buildQuery();
                this.addFilter();
                this.addFieldValueFactor(true, this.queryBuilder);
                this.buildAggregations();
                this.buildRequest();
                this.addSorting();
            }
            this.getResponse();
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to fetch search.", module);
        }
    }

    /**
     * Build bool query from given search phrase.
     */
    private void buildQuery() {
        //query documents based on passed query
        if(this.getTunedResult) {
            this.functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(this.buildIdsQuery(null), this.buildScriptScore(null)).boostMode(CombineFunction.REPLACE);
        } else {
            if (this.translator.isWildCard || this.translator.query == null) {
                //retrieve all documents when query is unavailable
                this.queryBuilder.must(QueryBuilders.matchAllQuery());
            } else {
                this.queryBuilder = this.queryHelper(this.queryBuilder, true);
            }
        }
    }

    /**
     * Build the query giving the parameters
     * @param bqb
     * @return
     */
    private BoolQueryBuilder queryHelper(BoolQueryBuilder bqb, boolean addPhrase) {
        if (this.translator.isWildCard || this.translator.query == null) {
            bqb.must(QueryBuilders.matchAllQuery());
        } else {
            /*
             * Build the query using only select fields we want to search.
             * This query allows to boost weight of certain fields to be higher than others
             *
             * PROS: allows specific fields to be queried and to boost certain fields to be higher than others
             *       this also respects the analyzers used for each field during index time
             * CONS: slightly more cpu intensive
             */
            if (this.mustMatchAll) {
                for (String queryTerm : this.translator.queryTerms) {
                    bqb.must(buildQueryString(queryTerm, MultiMatchQueryBuilder.Type.MOST_FIELDS));
                }
                //if its a phrase and we can find it together, this has extra weight
                if(addPhrase && (this.translator.queryTerms.length > 1 || this.translator.hasHyphen)) {
                    bqb.should(buildQueryString(this.translator.query, MultiMatchQueryBuilder.Type.PHRASE).slop(SLOP_DISTANCE).boost(20f));
                }
            } else {
                for (String queryTerm : this.translator.queryTerms) {
                    bqb.should(buildQueryString(queryTerm, MultiMatchQueryBuilder.Type.MOST_FIELDS));
                }
            }
        }

        return bqb;
    }

    /**
     * Add the field value factor to improve rankings on certain criteria
     * @param updateGlobalFunctionScoreQuery
     * @param bqb
     * @return
     */
    private FunctionScoreQueryBuilder addFieldValueFactor(boolean updateGlobalFunctionScoreQuery, BoolQueryBuilder bqb) {
        if(updateGlobalFunctionScoreQuery) {
            this.functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(bqb, ScoreFunctionBuilders.fieldValueFactorFunction("salesrank"));
            return this.functionScoreQueryBuilder;
        } else {
            return QueryBuilders.functionScoreQuery(bqb, ScoreFunctionBuilders.fieldValueFactorFunction("salesrank"));
        }
    }

    /**
     * Add filters to the search criteria
     */
    private void addFilter() {
        Iterator filterIterator = this.translator.filters.entrySet().iterator();
        while (filterIterator.hasNext()) {
            Map.Entry pair = (Map.Entry) filterIterator.next();

            SearchField sfield = SearchField.getSearchField((String) pair.getKey());
            if(sfield.getIsReviewAggregation()) {
                if(pair.getValue() instanceof List) {
                    BoolQueryBuilder rangeQuery = QueryBuilders.boolQuery();
                    for(String value : (List<String>) pair.getValue()) {
                        rangeQuery.should(this.rangeQueryBuilder(sfield, value));
                    }
                    this.queryBuilder.filter(rangeQuery);
                } else {
                    this.queryBuilder.filter(this.rangeQueryBuilder(sfield, (String) pair.getValue()));
                }
            } else if (pair.getValue() instanceof Double) {
                this.queryBuilder.filter(QueryBuilders.termQuery((String) pair.getKey(), (Double) pair.getValue()));
            } else if (pair.getValue() instanceof Integer) {
                this.queryBuilder.filter(QueryBuilders.termQuery((String) pair.getKey(), (Integer) pair.getValue()));
            } else if (pair.getValue() instanceof String) {
                this.queryBuilder.filter(QueryBuilders.termQuery((String) pair.getKey(), (String) pair.getValue()));
            } else if (pair.getValue() instanceof List) {
                /*
                 * Note: The terms filter does a match using OR
                 * Inorder to do a filter on AND, build a bool query with MUST
                 * and add individual term queries and add to the filter
                 */
                this.queryBuilder.filter(QueryBuilders.termsQuery((String) pair.getKey(), (List) pair.getValue()));
            }
        }

        //if webSiteId is available add it as a filter
        this.addWebSiteFilter(this.queryBuilder);
    }

    /**
     * Add the websiteid filter to the query
     * @param query
     */
    private void addWebSiteFilter(BoolQueryBuilder query) {
        if(this.webSiteId != null) {
            query.filter(QueryBuilders.termsQuery(((Map<String, String>) SearchField.webSiteData.get(this.webSiteId).get("filterFieldMap")).get("websiteid"), this.webSiteId));
        }
    }

    /**
     * Get all the aggregations (facets) that need to be created
     */
    private void buildAggregations() {
        //if filters are applied, global aggregations need to be created
        GlobalAggregationBuilder globalAggregation = AggregationBuilders.global("all_aggregations");

        //if query string is available, create the query string as main filter
        FilterAggregationBuilder queryAggregation = null;
        if(this.getTunedResult) {
            queryAggregation = AggregationBuilders.filter("queryFilter", QueryBuilders.functionScoreQuery(this.buildIdsQuery(null), this.buildScriptScore(null)).boostMode(CombineFunction.REPLACE));
        } else {
            BoolQueryBuilder filterQueryBuilder = this.queryHelper(QueryBuilders.boolQuery(), false);
            this.addWebSiteFilter(filterQueryBuilder);
            queryAggregation = AggregationBuilders.filter("queryFilter", this.addFieldValueFactor(false, filterQueryBuilder));
        }

        globalAggregation.subAggregation(queryAggregation);

        for (SearchField aggregations : (List<SearchField>) SearchField.webSiteData.get(this.webSiteId).get("aggregationsList")) {
            /*
             * Apply all the term filters towards the aggregation only
             * when the filters being applied are not of the type of the current aggregation
             * Example: If current aggregation being created is of "producttype"
             * then apply all filters except "producttype"
             */

            //loop through all filters
            List<QueryBuilder> queryFilter = new ArrayList<>();
            Iterator filterIterator = this.translator.filters.entrySet().iterator();
            while (filterIterator.hasNext()) {
                Map.Entry pair = (Map.Entry) filterIterator.next();

                //skip filter if its the same as the aggregation
                if (aggregations.getFieldName().equalsIgnoreCase((String) pair.getKey()) || aggregations.getFilterField().equalsIgnoreCase((String) pair.getKey())) {
                    continue;
                }

                SearchField sfield = SearchField.getSearchField((String) pair.getKey());
                if(sfield.getIsReviewAggregation()) {
                    if(pair.getValue() instanceof List) {
                        BoolQueryBuilder rangeQuery = QueryBuilders.boolQuery();
                        for(String value : (List<String>) pair.getValue()) {
                            rangeQuery.should(this.rangeQueryBuilder(sfield, value));
                        }
                        queryFilter.add(rangeQuery);
                    } else {
                        queryFilter.add(this.rangeQueryBuilder(sfield, (String) pair.getValue()));
                    }
                } else if (pair.getValue() instanceof Double) {
                    queryFilter.add(QueryBuilders.termQuery((String) pair.getKey(), (Double) pair.getValue()));
                } else if (pair.getValue() instanceof Integer) {
                    queryFilter.add(QueryBuilders.termQuery((String) pair.getKey(), (Integer) pair.getValue()));
                } else if (pair.getValue() instanceof String) {
                    queryFilter.add(QueryBuilders.termQuery((String) pair.getKey(), (String) pair.getValue()));
                } else if (pair.getValue() instanceof List) {
                    /*
                     * Note: The terms filter does a match using OR
                     * Inorder to do a filter on AND, build a bool query with MUST
                     * and add individual term queries and add to the filter
                     */
                    queryFilter.add(QueryBuilders.termsQuery((String) pair.getKey(), (List) pair.getValue()));
                }
            }

            if(queryFilter.isEmpty()) {
                queryAggregation.subAggregation(
                    AggregationBuilders.terms(aggregations.getFilterId()).field(aggregations.getFilterField()).size(MAX_AGGREGATIONS).missing(aggregations.getIsStringAggregation() ? DEFAULT_AGG_NAME : 0)
                );
            } else {
                //build the must filter query for aggregations
                BoolQueryBuilder boolFilter = QueryBuilders.boolQuery();
                for(QueryBuilder qb : queryFilter) {
                    boolFilter.must(qb);
                }
                queryAggregation.subAggregation(
                    AggregationBuilders.filters(aggregations.getFilterId(), boolFilter).subAggregation(
                        AggregationBuilders.terms(aggregations.getFilterId()).field(aggregations.getFilterField()).size(MAX_AGGREGATIONS).missing(aggregations.getIsStringAggregation() ? DEFAULT_AGG_NAME : 0)
                    )
                );
            }
        }

        this.aggregations.add(globalAggregation);
    }

    /**
     * Build a range query given a number (as string)
     * @param sfield
     * @param range
     * @return
     */
    private RangeQueryBuilder rangeQueryBuilder(SearchField sfield, String range) {
        RangeQueryBuilder rqB = QueryBuilders.rangeQuery(sfield.getFilterField());
        rqB.gte(Double.valueOf(range));
        rqB.lt(SearchUtil.getUpperBoundForRatingsFilter(Double.valueOf(range)));

        return rqB;
    }

    /**
     * Build the actual request to fetch results/hits
     */
    private void buildRequest() {
        this.requestBuilder = SearchClient.getTransportClient().prepareSearch().setIndices(INDEX).setTypes(TYPE).setQuery(this.functionScoreQueryBuilder).setSize(this.translator.size).setFrom(this.translator.from);

        if(this.translator.explain) {
            this.requestBuilder.setExplain(this.translator.explain);
        }

        /*
         * The Aggregations for a request only takes one at a time
         */
        for(AggregationBuilder aggregationBuilder : aggregations) {
            this.requestBuilder.addAggregation(aggregationBuilder);
        }
    }

    /**
     * Set the sorting order of the documents
     */
    private void addSorting() {
        if(this.translator.sort.size() > 0) {
            for(int i = 0; i < this.translator.sort.size(); i++) {
                this.requestBuilder.addSort(this.translator.sort.get(i).toString(), this.translator.sortDirection.get(i));
            }
        } else {
            //default sort order
            this.requestBuilder.addSort(SortBuilders.scoreSort());
            /*this.requestBuilder.addSort("views", SortOrder.DESC);
            this.requestBuilder.addSort("conversionrate", SortOrder.DESC);
            this.requestBuilder.addSort("revenue", SortOrder.DESC);
            this.requestBuilder.addSort("quantitypurchased", SortOrder.DESC);*/
        }
    }

    /**
     * Create the multi match query for a search string
     * @return
     */
    private MultiMatchQueryBuilder buildQueryString(String queryTerm, MultiMatchQueryBuilder.Type type) {
        return QueryBuilders.multiMatchQuery(queryTerm).fields(((Map<String, Float>) SearchField.webSiteData.get(webSiteId).get("searchableFilterMap")))
                .type(type);
    }

    /**
     * Create query to search for specific skus
     * @param productIds
     * @return
     */
    private IdsQueryBuilder buildIdsQuery(String[] productIds) {
        productIds = UtilValidate.isNotEmpty(productIds) ? productIds : this.translator.tunedProductList.stream().toArray(String[]::new);
        return QueryBuilders.idsQuery().addIds(productIds).types(TYPE);
    }

    /**
     * Create script score for specific skus
     * @param productIds
     * @return
     */
    private ScriptScoreFunctionBuilder buildScriptScore(String[] productIds) {
        productIds = UtilValidate.isNotEmpty(productIds) ? productIds : this.translator.tunedProductList.stream().toArray(String[]::new);
        List<String> idList = Arrays.asList(productIds); //need to convert to List inorder to use indexOf function in script
        return ScoreFunctionBuilders.scriptFunction(new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG, "return -params.ids.indexOf(doc['productid.keyword'].value);", UtilMisc.toMap("ids", idList)));
    }

    /**
     * Get the Response for a build request
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public SearchResponse getResponse() throws InterruptedException, ExecutionException {
        if(this.response != null) {
            return this.response;
        }

        this.response = this.requestBuilder.execute().actionGet();

        //if we get 0 results, we can re-execute with OR
        if(this.maxRetries > 0 && this.response.getHits().getTotalHits() == 0 && !this.translator.isSingleTerm) {
            this.maxRetries--;
            this.mustMatchAll = false;
            this.clearQuery();
            this.executeSearch();
        }

        return this.response;
    }

    /**
     * Get tuned aggregations
     * @return
     */
    public Aggregations getTunedAggregations() {
        return this.tunedAggregations;
    }

    /**
     * Get SearchResult to process the response
     * @return
     */
    public SearchResult getSearchResult() {
        if(this.response == null) {
            throw new NullPointerException("No response available, search may not have been executed.");
        }
        return new SearchResult(this.response, this.tunedAggregations, this.translator, this.delegator);
    }

    /**
     * Get JSON/Map of the request sent
     * @return
     */
    public Map getJSONRequest() {
        return new Gson().fromJson(this.requestBuilder.toString(), HashMap.class);
    }

    /**
     * Get the JSON/Map of the response received
     * @return
     */
    public Map getJSONResponse() {
        return new Gson().fromJson(this.response.toString(), HashMap.class);
    }

    /**
     * Get the HEALTH of the cluster
     */
    private void getClusterHealth() {
        ClusterHealthResponse healths = SearchClient.getTransportClient().admin().cluster().prepareHealth().get();
        healthData.put("clusterName", healths.getClusterName());
        healthData.put("numberOfDataNodes", healths.getNumberOfDataNodes());
        healthData.put("numberOfNodes", healths.getNumberOfNodes());
        for (ClusterIndexHealth health : healths.getIndices().values()) {
            Map<String, Object> indexData = new HashMap<>();
            indexData.put("numberOfShards", health.getNumberOfShards());
            indexData.put("numberOfReplicas", health.getNumberOfReplicas());
            indexData.put("status", health.getStatus());
            healthData.put(health.getIndex(), indexData);
        }
    }

    /**
     * Return the health of the cluster
     * @return
     */
    public Map<String, Object> getHealthResponse() {
        return this.healthData;
    }

    public SearchTranslator getTranslator() {
        return this.translator;
    }

    /**
     * Clear the search cache
     */
    public void clearSearchCache() {
        List<String> indices = new ArrayList<>();
        indices.add(INDEX);
        SearchClient.getTransportClient().admin().indices().clearCache(new ClearIndicesCacheRequest((String[]) indices.toArray(new String[indices.size()]))).actionGet();
    }

    /**
     * Reset query object
     */
    private void clearQuery() {
        this.requestBuilder = null;
        this.response = null;
        this.queryBuilder = QueryBuilders.boolQuery();
        this.aggregations = new ArrayList<>();
    }
}