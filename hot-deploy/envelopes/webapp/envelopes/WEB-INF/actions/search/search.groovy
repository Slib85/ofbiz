import org.apache.ofbiz.webapp.website.WebSiteWorker;
import com.bigname.search.elasticsearch.enums.SearchPageType;
import com.bigname.search.elasticsearch.SearchTranslator;
import com.envelopes.util.EnvUtil;
                                                                                                                                                                                                                                                    
String module = "search.groovy";

Map<String, Object> params = EnvUtil.getParameterMap(request);
context.put("searchString", params.get("w"));

try {
    SearchTranslator translator = new SearchTranslator(delegator, params, WebSiteWorker.getWebSiteId(request), SearchPageType.SEARCH_PAGE);
    context.bannerData = translator.getBannerMatch();
    context.redirectData = translator.getRedirectMatch();
    context.filters = translator.getFilters();
    context.filterUrl = translator.getUrl();
    context.searchPageType = translator.getSearchPageType();
    globalContext.translator = translator;
} catch(Exception e) {
    EnvUtil.reportError(e);
}