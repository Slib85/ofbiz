package com.bigname.search.elasticsearch.util;

import java.util.*;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang.NumberUtils;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;

import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregations;

import com.bigname.search.elasticsearch.SearchBuilder;
import com.bigname.search.elasticsearch.SearchResult;
import com.bigname.search.elasticsearch.SearchTranslator;
import com.bigname.search.elasticsearch.enums.SearchPageType;

public class DocumentUtil {
    public static final String module = DocumentUtil.class.getName();

    /**
     * Hold full document id list from a queried result.
     */
    private List<String> documentIds = new ArrayList<>();

    /**
     * Hold full document hit list from a queried result.
     */
    private Map<String, List<SearchHit>> documents = new LinkedHashMap<>();

    private SearchBuilder builder = null;

    private Delegator delegator = null;
    protected int size = 30;
    protected int page = 0;

    /**
     * Quick helper class to get an entire list of document hit or just the document ids
     * @param delegator
     * @param parameters
     * @param webSiteId
     * @param searchPageType
     * @param bypassTuneCheck
     */
    public DocumentUtil(Delegator delegator, Map<String, Object> parameters, String webSiteId, SearchPageType searchPageType, boolean bypassTuneCheck) {
        this.delegator = delegator;
        this.setDefaults(parameters);

        this.builder = new SearchBuilder(delegator, parameters, webSiteId, searchPageType, bypassTuneCheck);
        this.builder.executeSearch();

        SearchResult result = this.builder.getSearchResult();
        long totalPages = result.getTotalPages();

        for(SearchHit hit : result.getHits()) {
            this.documentIds.add(hit.getId());
            if(searchPageType == SearchPageType.CATEGORY_PAGE) {
                if (this.documents.containsKey(hit.getSourceAsMap().get("parentid"))) {
                    this.documents.get(hit.getSourceAsMap().get("parentid")).add(hit);
                } else {
                    this.documents.put((String) hit.getSourceAsMap().get("parentid"), UtilMisc.toList(hit));
                }
            }
        }

        while (totalPages > 1) {
            parameters.put("page", String.valueOf(totalPages - 1));
            this.builder = new SearchBuilder(delegator, parameters, webSiteId, searchPageType, bypassTuneCheck);
            this.builder.executeSearch();

            result = this.builder.getSearchResult();

            for(SearchHit hit : result.getHits()) {
                this.documentIds.add(hit.getId());
                if(searchPageType == SearchPageType.CATEGORY_PAGE) {
                    if (this.documents.containsKey(hit.getSourceAsMap().get("parentid"))) {
                        this.documents.get(hit.getSourceAsMap().get("parentid")).add(hit);
                    } else {
                        this.documents.put((String) hit.getSourceAsMap().get("parentid"), UtilMisc.toList(hit));
                    }
                }
            }

            totalPages--;
        }
    }

    /**
     * Set defaults, this overrides any parameters passed in through context
     * So that it doesn't interfere with SearchTranslator
     * @param parameters
     */
    private void setDefaults(Map<String, Object> parameters) {
        parameters.put("cnt", String.valueOf(SearchTranslator.MAX_SIZE));
        parameters.put("page", "0");
    }

    /**
     * Set the page when requested paginated data
     * @param page
     */
    public void setPage(int page) {
        this.page = page;
    }
    public void setPage(String page) {
        if(UtilValidate.isNotEmpty(page) && NumberUtils.isNumber(page)) {
            this.page = Integer.valueOf(page);
        }
    }

    /**
     * Return all document ids
     * @return
     */
    public List<String> getAllDocumentIds() {
        return this.documentIds;
    }

    /**
     * Return all document hits
     * @return
     */
    public Map<String, List<SearchHit>> getAllDocuments() {
        return this.documents;
    }

    /**
     * Get paginated subset of document data
     * @return
     */
    public Map<String, List<SearchHit>> getPaginatedDocuments() {
        Map<String, List<SearchHit>> subDocuments = new LinkedHashMap<>();

        if(this.documents.size() > 0) {
            int startIndex = this.page == 0 ? 0 : this.page * this.size;
            int endIndex = this.page == 0 ? this.size : (this.page * this.size) + this.size;

            Set<String> keys = this.documents.keySet();
            try {
                if(startIndex <= keys.size()) {
                    if(endIndex > keys.size()) {
                        endIndex = keys.size();
                    }
                    List<String> subList = new ArrayList<>(keys).subList(startIndex, endIndex);
                    for (String key : subList) {
                        subDocuments.put(key, this.documents.get(key));
                    }
                }
            } catch (Exception e) {
                Debug.logError(e, "Error trying to get paginated sublist.", module);
            }
        }

        return subDocuments;
    }

    /**
     * Get the total # of pages of hits
     * @return
     */
    public long getTotalPages() {
        long pages = 0;
        if(this.documents.size() > 0) {
            pages = (long) Math.ceil(Double.valueOf(Long.valueOf(this.documents.size()).toString()) / Double.valueOf(Integer.valueOf(this.size).toString()));
        }

        return pages;
    }

    /**
     * Get the aggregations of a tuned search
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public Aggregations getAggregations() throws InterruptedException, ExecutionException {
        return this.builder.getResponse().getAggregations();
    }

    public Delegator getDelegator() {
        return this.delegator;
    }

    public SearchTranslator getTranslator() {
        return this.builder.getTranslator();
    }
}
