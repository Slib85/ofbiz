import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.webapp.website.WebSiteWorker;

import com.bigname.search.elasticsearch.enums.SearchPageType;
import com.bigname.search.elasticsearch.SearchResult;
import com.bigname.search.elasticsearch.SearchTranslator;
import com.bigname.search.elasticsearch.util.DocumentUtil;
import com.envelopes.util.EnvUtil

String module = "categoryResults.groovy";

try {
    if(context.get("pages") == null) {
        Map<String, Object> originalParams = EnvUtil.getParameterMap(request);
        Map<String, Object> params = EnvUtil.getParameterMap(request);
        context.currentPage = UtilValidate.isNotEmpty(originalParams.get("page")) ? originalParams.get("page") : 0;

        SearchTranslator translator = new SearchTranslator(delegator, originalParams, WebSiteWorker.getWebSiteId(request), SearchPageType.CATEGORY_PAGE);
        context.filters = translator.getFilters();
        context.filterUrl = translator.getUrl();

        DocumentUtil documentUtil = new DocumentUtil(delegator, params, WebSiteWorker.getWebSiteId(request), SearchPageType.CATEGORY_PAGE, false);
        documentUtil.setPage((String) originalParams.get("page"));

        context.pages = documentUtil.getTotalPages();
        context.hits = documentUtil.getPaginatedDocuments();
        context.aggregations = SearchResult.getAggregations(documentUtil.getDelegator(), documentUtil.getAggregations());
        context.searchPageType = documentUtil.getTranslator().getSearchPageType();
    }
} catch(Exception e) {
    Debug.logError(e, "Error trying to get envelopes category data.", module);
}