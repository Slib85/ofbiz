import java.util.*;

import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.webapp.website.WebSiteWorker;

import com.bigname.search.elasticsearch.enums.SearchPageType;
import com.bigname.search.elasticsearch.SearchTranslator;
import com.bigname.search.elasticsearch.util.DocumentUtil;
import com.bigname.search.elasticsearch.SearchResult;
import com.envelopes.util.EnvUtil;
                                              
String module = "shopBySize.groovy";

try {
    Map<String, Object> originalParams = EnvUtil.getParameterMap(request);
    context.put("currentPage", UtilValidate.isNotEmpty(originalParams.get("page")) ? originalParams.get("page") : 0);

    Map<String, Object> params = EnvUtil.getParameterMap(request);
    context.put("searchString", params.get("w"));

    SearchTranslator translator = new SearchTranslator(delegator, params, WebSiteWorker.getWebSiteId(request), SearchPageType.CATEGORY_PAGE);
    if(!translator.getPageTitle().equals("")) {
        globalContext.metaTitle = "Products in " + translator.getPageTitle() + " at Envelopes.com";
    }

    context.bannerData = translator.getBannerMatch();
    context.redirectData = translator.getRedirectMatch();
    context.filters = translator.getFilters();
    context.filterUrl = translator.getUrl();

    DocumentUtil documentUtil = new DocumentUtil(delegator, params, WebSiteWorker.getWebSiteId(request), SearchPageType.CATEGORY_PAGE, false);
    documentUtil.setPage((String) originalParams.get("page"));

    context.pages = documentUtil.getTotalPages();
    context.hits = documentUtil.getPaginatedDocuments();
    context.aggregations = SearchResult.getAggregations(documentUtil.getDelegator(), documentUtil.getAggregations());
    context.searchPageType = translator.getSearchPageType();
    context.hideFilters = true;
} catch(Exception e) {
    Debug.logError(e, "Error trying to get category data for shop by size.", module);
}

List breadcrumbs = new ArrayList();
Map breadcrumb = new HashMap();
breadcrumb.put("name" , "Shop By Size");
breadcrumb.put("link", "shopBySize");
breadcrumbs.add(breadcrumb);
context.breadcrumbs = breadcrumbs;

Map staticSizeMap = new LinkedHashMap();
staticSizeMap.put("#10 (4 1/8 x 9 1/2)", "418x912");
staticSizeMap.put("A7 (5 1/4 x 7 1/4)", "514x714");
staticSizeMap.put("A2 (4 3/8 x 5 3/4)", "438x534");
staticSizeMap.put("A6 (4 3/4 x 6 1/2)", "434x612");
staticSizeMap.put("A1 (3 5/8 x 5 1/8)", "358x518");
staticSizeMap.put("9 x 12", "9x12");
staticSizeMap.put("6 x 9", "6x9");
staticSizeMap.put("A9 (5 3/4 x 8 3/4)", "534x834");
staticSizeMap.put("6 3/4 (3 5/8 x 6 1/2)", "358x612");
staticSizeMap.put("#1 (2 1/4 x 3 1/2)", "214x312");
staticSizeMap.put("#17 (2 11/16 x 3 11/16)", "21116x31116");
staticSizeMap.put("#9 (3 7/8 x 8 7/8)", "378x878");
context.staticSizeMap = staticSizeMap;

List staticCategoryProducts = new ArrayList();
staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4561-01", request));
staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1590", request));
staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4880-GB", request));
staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "72940", request));
staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "R0265", request));
staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4880-WPP", request));
staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "5380-06", request));
staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "8515-M09", request));
staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "LUX-4880-113", request));
staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4880-WLI", request));
staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "WS-2040", request));
staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "5380-08", request));
staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "5370-MS02", request));
context.staticCategoryProducts = staticCategoryProducts;