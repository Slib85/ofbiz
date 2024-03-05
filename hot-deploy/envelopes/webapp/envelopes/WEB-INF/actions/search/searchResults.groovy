import org.apache.ofbiz.webapp.website.WebSiteWorker;
import org.apache.ofbiz.base.util.UtilValidate;

import com.bigname.search.elasticsearch.enums.SearchPageType;
import com.bigname.search.elasticsearch.SearchBuilder;
import com.bigname.search.elasticsearch.SearchResult;
import com.envelopes.util.EnvUtil;

String module = "searchResults.groovy";

Map<String, Object> params = UtilValidate.isNotEmpty(context.get("params")) ? context.get("params") : EnvUtil.getParameterMap(request);
context.currentPage = UtilValidate.isNotEmpty(params.get("page")) ? params.get("page") : 0;

try {
    if(context.get("pages") == null) {
        SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request), SearchPageType.SEARCH_PAGE);
        builder.executeSearch();

        SearchResult result = builder.getSearchResult();
        context.pages = result.getTotalPages();
        context.totalHits = result.getTotalHits();
        context.hits = result.getHits();
        context.aggregations = result.getAggregations();
        context.filters = builder.getTranslator().getFilters();
        context.filterUrl = builder.getTranslator().getUrl();
        context.searchPageType = builder.getTranslator().getSearchPageType();
    }
} catch(Exception e) {
    EnvUtil.reportError(e);
}