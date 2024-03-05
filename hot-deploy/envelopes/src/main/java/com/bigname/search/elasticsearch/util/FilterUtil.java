package com.bigname.search.elasticsearch.util;

import com.bigname.search.elasticsearch.SearchBuilder;
import com.bigname.search.elasticsearch.SearchResult;
import com.bigname.search.elasticsearch.enums.SearchPageType;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FilterUtil {
    public static final String module = FilterUtil.class.getName();

    private Map<String, Map<GenericValue, Long>> filterAndFacets = new HashMap<>();
    private SearchBuilder builder = null;

    /**
     * Constructor
     * @param delegator
     * @param webSiteId
     */
    public FilterUtil(Delegator delegator, String webSiteId) {
        this(delegator, new HashMap<>(), webSiteId, SearchPageType.ANY_PAGE, true);
    }

    /**
     * Constructor
     * @param delegator
     * @param parameters
     * @param webSiteId
     */
    public FilterUtil(Delegator delegator,  Map<String, Object> parameters, String webSiteId) {
        this(delegator, parameters, webSiteId, SearchPageType.ANY_PAGE, true);
    }

    /**
     * Constructor
     * @param delegator
     * @param parameters
     * @param webSiteId
     * @param searchPageType
     * @param bypassTuneCheck
     */
    public FilterUtil(Delegator delegator, Map<String, Object> parameters, String webSiteId, SearchPageType searchPageType, boolean bypassTuneCheck) {
        this.builder = new SearchBuilder(delegator, parameters, webSiteId, searchPageType, bypassTuneCheck);
        this.builder.getTranslator().setAggsOnly(true);
        this.builder.executeSearch();

        SearchResult result = this.builder.getSearchResult();
        try {
            filterAndFacets = result.getAggregations();
        } catch (GenericEntityException e) {

        }
    }

    /**
     * Get all the filters and their facets
     * @return
     */
    public Map<String, Map<GenericValue, Long>> getFilterAndFacets() {
        return this.filterAndFacets;
    }

    /**
     * Get a specific filter and its facets
     * @param filterId
     * @return
     */
    public Map<GenericValue, Long> getFacets(String filterId) {
        return this.filterAndFacets.get(filterId);
    }

    /**
     * Sort the filter map by the values (total number of hits)
     * @param data
     * @return
     */
    public static Map<GenericValue, Long> sortedFilter(Map<GenericValue, Long> data) {
        return data.entrySet().stream().sorted((e1,e2)->e2.getValue().compareTo(e1.getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2)->e1, LinkedHashMap::new));
    }

}
