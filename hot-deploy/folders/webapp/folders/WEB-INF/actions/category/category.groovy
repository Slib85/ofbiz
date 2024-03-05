import java.util.*;

import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.webapp.website.WebSiteWorker;
import org.apache.ofbiz.entity.GenericValue;

import org.elasticsearch.search.SearchHit;

import com.bigname.search.elasticsearch.enums.SearchPageType;
import com.bigname.search.elasticsearch.SearchResult;
import com.bigname.search.elasticsearch.SearchTranslator;
import com.bigname.search.elasticsearch.util.DocumentUtil;
import com.envelopes.product.ProductReviewEvents;
import com.envelopes.util.EnvUtil;
                                                                                                                                                                                                                                                    
String module = "categoryNew.groovy";

try {
    Map<String, Object> originalParams = EnvUtil.getParameterMap(request);
    Map<String, Object> params = EnvUtil.getParameterMap(request);
    context.put("currentPage", UtilValidate.isNotEmpty(originalParams.get("page")) ? originalParams.get("page") : 0);
    context.put("searchString", params.get("w"));

    SearchTranslator translator = new SearchTranslator(delegator, originalParams, WebSiteWorker.getWebSiteId(request), SearchPageType.CATEGORY_PAGE);
    if(!translator.getPageTitle().equals("")) {
        globalContext.metaTitle = "Products in " + translator.getPageTitle() + " at Folders.com";
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

    ArrayList<String> productIdList = new ArrayList<>();
    for (Map.Entry<String, List<SearchHit>> entry : ((Map<String, List<SearchHit>>) context.get("hits")).entrySet()) {
        for(SearchHit hit : entry.getValue()) {
            productIdList.add(hit.getSourceAsMap().get("productid"));
        }
    }

    context.topReviewList = ProductReviewEvents.getTopReviewList(request, response, productIdList);

    String categoryId = (String) params.get("category_id");
    if (UtilValidate.isNotEmpty(categoryId)) {
        GenericValue category = delegator.findOne("ProductCategory", UtilMisc.toMap("productCategoryId", categoryId), true);
        if(category != null) {
            if(UtilValidate.isNotEmpty(category.getString("description"))) {
                request.setAttribute("seoH1", category.getString("description"));
            }
            if(UtilValidate.isNotEmpty(category.getString("longDescription"))) {
                request.setAttribute("pageDescription", category.getString("longDescription"));
            }
        }

        List staticCategoryHolidayProducts = new ArrayList();
        List staticCategoryProducts = new ArrayList();
        List staticCategoryDesigns = new ArrayList();

        context.staticCategoryHolidayProducts = staticCategoryHolidayProducts;
        context.staticCategoryProducts = staticCategoryProducts;
        context.staticCategoryDesigns = staticCategoryDesigns;
    }
    
    List breadcrumbs = new ArrayList();
    if(UtilValidate.isNotEmpty(categoryId)) {
        GenericValue category = delegator.findOne("ProductCategory", UtilMisc.toMap("productCategoryId", categoryId), true);
        Map breadcrumb = new HashMap();
        breadcrumb.put("name", category != null ? category.description : "");
        breadcrumb.put("link", "");
        breadcrumbs.add(breadcrumb);
        if (UtilValidate.isNotEmpty(category.get("primaryParentCategoryId")) && !category.get("primaryParentCategoryId").equals("ENVELOPES")) {
            category = delegator.findOne("ProductCategory", UtilMisc.toMap("productCategoryId", category.getString("primaryParentCategoryId")), true);
            breadcrumb = new HashMap();
            breadcrumb.put("name", category != null ? category.description : "");
            breadcrumb.put("link", "category/~category_id=" + category.productCategoryId);
            breadcrumbs.add(0, breadcrumb);
        }
    }
    context.breadcrumbs = breadcrumbs;
} catch(Exception e) {
    Debug.logError(e, "Error trying to get folders category data.", module);
}