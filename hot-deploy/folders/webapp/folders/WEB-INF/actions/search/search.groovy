import org.apache.ofbiz.webapp.website.WebSiteWorker;
import org.apache.ofbiz.base.util.*;
import com.bigname.search.elasticsearch.enums.SearchPageType;
import com.bigname.search.elasticsearch.SearchTranslator;
import com.envelopes.util.EnvUtil;

String module = "search.groovy";

Map<String, Object> params = EnvUtil.getParameterMap(request);
context.put("searchString", params.get("w"));
context.put("test", "asdf");
List breadcrumbs = new ArrayList();

try {
    SearchTranslator translator = new SearchTranslator(delegator, params, WebSiteWorker.getWebSiteId(request), SearchPageType.SEARCH_PAGE);
    if(!translator.getPageTitle().equals("")) {
        globalContext.metaTitle = "Products in " + translator.getPageTitle() + " at Folders.com";
    }

    context.bannerData = translator.getBannerMatch();
    context.redirectData = translator.getRedirectMatch();
    context.filters = translator.getFilters();
    context.filterUrl = translator.getUrl();
    context.searchPageType = translator.getSearchPageType();

    Map breadcrumb = new HashMap();
    breadcrumb.put("name", "Search");
    breadcrumb.put("link", "");

    if (UtilValidate.isNotEmpty(context.get("searchString"))) {
        breadcrumb.put("link", "search");
        breadcrumbs.add(breadcrumb);
        
        breadcrumb = new HashMap();
        breadcrumb.put("name", context.get("searchString"));
        breadcrumb.put("link", "");
    }

    breadcrumbs.add(breadcrumb);
    context.put("breadcrumbs", breadcrumbs);
} catch(Exception e) {
    Debug.logError(e, "Error trying to load search.", module);
}