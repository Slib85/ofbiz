import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.webapp.website.WebSiteWorker;

import com.bigname.search.elasticsearch.enums.SearchPageType;
import com.bigname.search.elasticsearch.SearchBuilder;
import com.bigname.search.elasticsearch.SearchResult;
import com.envelopes.util.EnvUtil;

String module = "searchResults.groovy";

Map<String, Object> params = null;
if(UtilValidate.isNotEmpty(context.get("params"))) {
    params = context.get("params");
} else {
    params = EnvUtil.getParameterMap(request);
}

context.put("currentPage", UtilValidate.isNotEmpty(params.get("page")) ? params.get("page") : 0);

try {
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
} catch(Exception e) {
    EnvUtil.reportError(e);
}