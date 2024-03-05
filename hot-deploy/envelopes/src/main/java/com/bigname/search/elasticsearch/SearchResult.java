package com.bigname.search.elasticsearch;

import java.util.*;
import java.lang.Math;

import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.InternalAggregations;
import org.elasticsearch.search.aggregations.bucket.filter.InternalFilter;
import org.elasticsearch.search.aggregations.bucket.filter.InternalFilters;
import org.elasticsearch.search.aggregations.bucket.global.InternalGlobal;
import org.elasticsearch.search.aggregations.bucket.terms.BucketUtil;
import org.elasticsearch.search.aggregations.bucket.terms.DoubleTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;

import com.bigname.comparator.MapGenericValueSizeComparator;

/**
 * Created by shoab on 6/2/17.
 */
public class SearchResult {
    public static final String module = SearchResult.class.getName();
    private Map<String, Map<GenericValue, Long>> aggregations = new HashMap<>();

    private SearchResponse response = null;
    private Aggregations aggs = null;
    private SearchTranslator translator = null;
    private Delegator delegator = null;

    public SearchResult(SearchResponse response, Aggregations aggs, SearchTranslator translator, Delegator delegator) {
        this.response = response;
        this.aggs = aggs;
        this.translator = translator;
        this.delegator = delegator;
    }

    /**
     * Return the list of results
     * @return
     */
    public SearchHit[] getHits() {
        return this.response.getHits().getHits();
    }

    /**
     * Total number of hits
     * @return
     */
    public long getTotalHits() {
        return this.response.getHits().getTotalHits();
    }

    /**
     * Total pages
     * @return
     */
    public long getTotalPages() {
        long pages = 0;
        if(this.response.getHits().getTotalHits() > 0) {
            pages = (long) Math.ceil(Double.valueOf(Long.valueOf(this.response.getHits().getTotalHits()).toString()) / Double.valueOf(Integer.valueOf(this.translator.size).toString()));
        }

        return pages;
    }

    /**
     * Build the aggregations for filtering
     * @return
     */
    public Map<String, Map<GenericValue, Long>> getAggregations() throws GenericEntityException {
        return getAggregations(this.delegator, (this.aggs != null) ? this.aggs : this.response.getAggregations());
    }

    public static Map<String, Map<GenericValue, Long>> getAggregations(Delegator delegator, Aggregations aggs) throws GenericEntityException {
        Map<String, Map<GenericValue, Long>> aggregations = new HashMap<>();
        Map<String, Aggregation> aggsMap = aggs.asMap();

        if(UtilValidate.isNotEmpty(aggsMap)) {
            Aggregation allAggs = aggsMap.get("all_aggregations");
            InternalAggregations internalAggs = ((InternalGlobal) allAggs).getAggregations();
            List<Aggregation> aggsList = internalAggs.asList();
            for(Aggregation aggFilter : aggsList) {
                InternalAggregations innerAggs = ((InternalFilter) aggFilter).getAggregations();
                List<Aggregation> innerAggsList = innerAggs.asList();
                for(Aggregation filters : innerAggsList) {
                    Map<GenericValue, Long> resultBuckets = null;
                    if(filters.getName().equalsIgnoreCase("si")) {
                        resultBuckets = new TreeMap<>(new MapGenericValueSizeComparator(true));
                    } else {
                        resultBuckets = new TreeMap<>();
                    }

                    if(filters instanceof InternalFilters) {
                        List<InternalFilters.InternalBucket> buckets = ((InternalFilters) filters).getBuckets();
                        OUTTERBUCKET: for(InternalFilters.InternalBucket bucket : buckets) {
                            List<Aggregation> bucketAgg = bucket.getAggregations().asList();
                            for(Aggregation innerBucket : bucketAgg) {
                                Iterator iter = null;
                                if(innerBucket instanceof DoubleTerms) {
                                    iter = ((DoubleTerms) innerBucket).getBuckets().iterator();
                                } else {
                                    iter = ((StringTerms) innerBucket).getBuckets().iterator();
                                }

                                boolean initial = resultBuckets.isEmpty();

                                Map<GenericValue, Long> tempBucket = new TreeMap<>();
                                while(iter.hasNext()) {
                                    Object data = iter.next();
                                    String facetName = BucketUtil.getKey(data);
                                    Long count = BucketUtil.getCount(data);

                                    /*
                                     * Lookup the facet in the database to get the facet id
                                     * NOTE: The CATCH ALL "Not Available" facet will be ignored because of this lookup, it does not exist in the database
                                     */
                                    GenericValue key = EntityQuery.use(delegator).from("SearchFacet").where("facetTypeId", filters.getName(), "facetName", facetName).cache().queryFirst();
                                    if(key != null) {
                                        tempBucket.put(key, count);

                                        if (initial) {
                                            resultBuckets.put(key, count);
                                        } else {
                                            if (resultBuckets.containsKey(key) && resultBuckets.get(key) > count) {
                                                resultBuckets.put(key, count);
                                            }
                                        }
                                    }
                                }

                                List<GenericValue> removableKeys = new ArrayList<>();
                                for (Map.Entry<GenericValue, Long> resultEntry : resultBuckets.entrySet()) {
                                    if(!tempBucket.containsKey(resultEntry.getKey())) {
                                        removableKeys.add(resultEntry.getKey());
                                    }
                                }

                                for(GenericValue key : removableKeys) {
                                    resultBuckets.remove(key);
                                }

                                if(resultBuckets.isEmpty()) {
                                    break OUTTERBUCKET;
                                }
                            }
                        }
                    } else if(filters instanceof DoubleTerms) {
                        Iterator iter = ((DoubleTerms) filters).getBuckets().iterator();
                        Map<GenericValue, Long> tempBucket = new TreeMap<>();
                        while(iter.hasNext()) {
                            Object data = iter.next();
                            String facetName = BucketUtil.getKey(data);
                            Long count = BucketUtil.getCount(data);

                            /*
                             * Lookup the facet in the database to get the facet id
                             * NOTE: The CATCH ALL "Not Available" facet will be ignored because of this lookup, it does not exist in the database
                             */
                            GenericValue key = EntityQuery.use(delegator).from("SearchFacet").where("facetTypeId", filters.getName(), "facetName", facetName).cache().queryFirst();
                            if(key != null) {
                                resultBuckets.put(key, count);
                            }
                        }
                    } else if(filters instanceof StringTerms) {
                        Iterator iter = ((StringTerms) filters).getBuckets().iterator();
                        Map<GenericValue, Long> tempBucket = new TreeMap<>();
                        while(iter.hasNext()) {
                            StringTerms.Bucket data = (StringTerms.Bucket) iter.next();
                            String facetName = (String) data.getKey();

                            /*
                             * Lookup the facet in the database to get the facet id
                             * NOTE: The CATCH ALL "Not Available" facet will be ignored because of this lookup, it does not exist in the database
                             */
                            GenericValue key = EntityQuery.use(delegator).from("SearchFacet").where("facetTypeId", filters.getName(), "facetName", facetName).cache().queryFirst();
                            if(key != null) {
                                Long count = data.getDocCount();
                                resultBuckets.put(key, count);
                            }
                        }
                    }

                    aggregations.put(filters.getName(), resultBuckets);
                }
            }
        }

        return aggregations;
    }
}
