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
    context.bannerData = translator.getBannerMatch();
    context.redirectData = translator.getRedirectMatch();
    context.filters = translator.getFilters();
    context.filterUrl = translator.getUrl();
    globalContext.translator = translator;

    String categoryId = (String) params.get("category_id");

    DocumentUtil documentUtil = new DocumentUtil(delegator, params, WebSiteWorker.getWebSiteId(request), SearchPageType.CATEGORY_PAGE, false);
    documentUtil.setPage((String) originalParams.get("page"));

    context.pages = documentUtil.getTotalPages();
    Map <String, Object> hits = documentUtil.getPaginatedDocuments();
    if("SHIPPING".equals(categoryId)) {
        hits.remove("CREDIT_CARD");
    } else if("BOOKS_PADS_AND_PLANNERS".equals(categoryId)) {
        hits.remove("PEN");
    } else if("REGULAR".equals(categoryId)) {
        hits.remove("3_MINI");
        hits.remove("MONARCH");
        hits.remove("6_BY_10_HALF_BOOKLET");
        hits.remove("6_BY_11_3_8_BOOKLET");
        hits.remove("10_SQUARE_FLAP_POST_IT");
        hits.remove("16_BANK_FLAP");
        hits.remove("3_6_REMITTANCE");
    }
    context.hits = hits;
    context.aggregations = SearchResult.getAggregations(documentUtil.getDelegator(), documentUtil.getAggregations());
    context.searchPageType = translator.getSearchPageType();

    ArrayList<String> productIdList = new ArrayList<>();
    for (Map.Entry<String, List<SearchHit>> entry : ((Map<String, List<SearchHit>>) context.get("hits")).entrySet()) {
        for(SearchHit hit : entry.getValue()) {
            productIdList.add(hit.getSourceAsMap().get("productid"));
        }
    }

    context.topReviewList = ProductReviewEvents.getTopReviewList(request, response, productIdList);


    if (UtilValidate.isNotEmpty(categoryId)) {
        GenericValue category = delegator.findOne("ProductCategory", UtilMisc.toMap("productCategoryId", categoryId), true);
        if(category != null) {
            if(UtilValidate.isNotEmpty(category.getString("description"))) {
                request.setAttribute("seoH1", category.getString("description"));
            }
            if(UtilValidate.isNotEmpty(category.getString("longDescription"))) {
                request.setAttribute("metaDescription", category.getString("longDescription"));
                request.setAttribute("pageDescription", category.getString("longDescription"));
            }
        }

        List staticCategoryHolidayProducts = new ArrayList();
        List staticCategoryProducts = new ArrayList();
        List staticCategoryDesigns = new ArrayList();

        switch (categoryId) {
            case "SHIPPING":
                context.hits
                break;
            case "REGULAR":
                Map staticCategoryDesignsMap = new HashMap();
                staticCategoryDesignsMap.put("productId", "43687");
                staticCategoryDesignsMap.put("designId", "14937");
                staticCategoryDesignsMap.put("name", "#10 - (4 1/8 x 9 1/2)");
                staticCategoryDesignsMap.put("type", "Return Address");
                staticCategoryDesignsMap.put("imageName", "/html/img/designs/thumbnails/14937_front.png");
                staticCategoryDesigns.add(staticCategoryDesignsMap);

                staticCategoryDesignsMap = new HashMap();
                staticCategoryDesignsMap.put("productId", "43703");
                staticCategoryDesignsMap.put("designId", "14948");
                staticCategoryDesignsMap.put("name", "#10 - (4 1/8 x 9 1/2)");
                staticCategoryDesignsMap.put("type", "Return Address");
                staticCategoryDesignsMap.put("imageName", "/html/img/designs/thumbnails/14948_front.png");
                staticCategoryDesigns.add(staticCategoryDesignsMap);

                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4260-15", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "5360-07", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "5360-06", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "EX4860-18", request));

                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "43703", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4860-GB", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4860-80W", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "WS-0078", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "43554", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "75746", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "17905", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "WS-0412", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4860-80W", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "43687", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "WS-2041", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "61597", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "65797", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "45146", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "5360-06", request));
                break;
            case "SQUARE_FLAP":
                Map staticCategoryDesignsMap = new HashMap();
                staticCategoryDesignsMap.put("productId", "4860-70W");
                staticCategoryDesignsMap.put("designId", "14944");
                staticCategoryDesignsMap.put("name", "#10 - (4 1/8 x 9 1/2)");
                staticCategoryDesignsMap.put("type", "Return Address");
                staticCategoryDesignsMap.put("imageName", "/html/img/designs/thumbnails/14944_front.png");
                staticCategoryDesigns.add(staticCategoryDesignsMap);

                staticCategoryDesignsMap = new HashMap();
                staticCategoryDesignsMap.put("productId", "72940");
                staticCategoryDesignsMap.put("designId", "15384");
                staticCategoryDesignsMap.put("name", "A7 - (5 1/4 x 7 1/4)");
                staticCategoryDesignsMap.put("type", "Return Address");
                staticCategoryDesignsMap.put("imageName", "/html/img/designs/thumbnails/15384_front.png");
                staticCategoryDesigns.add(staticCategoryDesignsMap);

                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "PHGC1-70WSG", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "EX4860-18", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "FLWHPHGC-01", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "FE4280-15", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "FLWH4880-03", request));

                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "11502a", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4860-80W", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4880-GB", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "72940", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "20578", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "5370-M09", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4895-WPP", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "5380-06", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4865-GB", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "FE4580-05", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "5370-MS02", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "5370-S01", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "72924", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4860-GB", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4872-W", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "72924", request));
                break;
            case "MINI_ENVELOPES":
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "LEVC-GBSG", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "EXLEVC-18H", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1801-24WMC", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1CO-80WSB", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "LUX-1801-18HH", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1CO-GBSG", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "LUX-1CO-18H", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1801-GBHE", request));

                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "PC1801PL", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "LEVC902", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "94623", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "LEVC-GB", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1CO-M09", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1COGB", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1CO-BLI", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1801-80N", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1801-CT", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1801-GB", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "LUX-1CO-22", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "GLASS-09", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "LUX-1CO-101", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "LEVC-S01", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1CO-CT", request));
                break;
            case "SQUARE":
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "FLWH8535-04", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "FLWH8535-01", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "FLWH8535-03", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "8535-15", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "8535-12", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "LUX-8525-103", request));

                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "8535-06", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "10928", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "8515-M07", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "10910", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "8525-80W", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "8525-GB", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "10902", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "8535-07", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "8515-50", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "10936", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "8545-07", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "8530-70W", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "8503-AO", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "10969", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "8635-W", request));
                break;
            case "CONTOUR_FLAP":
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "LUX-1880-18HH", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1880-06", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "CS1880-07", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "CS1880-06", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "CS1880-18", request));

                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1880-WPC", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1880-GB", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1880-07", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1870-WPC", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1870-GB", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "EX-1870-18", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1870-B", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1820-WPC", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1820-B", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1895-WPC", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1895-GB", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1895-07", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1855-07", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1855-06", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1855-30", request));
                break;
            case "WINDOW":
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4261-15", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "LUX-1590-18", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "FFW-912-18", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4892FFW-80W", request));

                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "43703", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1590", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "75761", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "45161", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "912CWIN", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "FFW-912", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "WS-3133", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "61597", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1590-GB", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "99966", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "WS-3270", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "INVDW", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "WS-2371", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4261-24IJ", request));
                break;
            case "INVITATION":
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "FLWH4880-03SB", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "LUX-4880-HFOILLINEDPACK", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "FLWH4880-04", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "FLWH4880-01", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "FLWH4880-03", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "FLNV4880-04", request));

                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "FE4580-05", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "8515-M09", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4880-GB", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4880-WPP", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "5380-06", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "20578", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "5370-MS02", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "72924", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "5395-07", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4870-GB", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "WINOUTER-SBW", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "72940", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4895-WPP", request));
                break;
            case "OPEN_END":
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1CO-80WSB", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1CO-GBSG", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "LUX-1CO-18H", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1CO-HOLI", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1CO-CHALK", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "LUX-1CO-L17", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1COJUP", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "LUX-1CO-18", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "LUX-1CO-26", request));

                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1590PS", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "75407", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "95447", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1COBLK", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4894-BLI", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1537", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "75423", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "EX4897-22", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1COGB", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1CO-CT", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1644-24IJ", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "10BS-GB", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "LUX-7CO-22", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "7716-BLI", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1801-CT", request));
                break;
            case "BOOKLET":
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "FFW-912-18", request));

                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "FE-4220-15", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "12310", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "F-6075-B", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1590B", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "LUXMLR-22", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4820-GB", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "F-4220-B", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "14554", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "5350-06", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "12328", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "E4895-00", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "EX4820-22", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4899-BLI", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "CC9x12", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "FFW-912", request));
                break;
            case "BUSINESS":
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4860-80WSG", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4260-15", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1COSIL", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1COGLD", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "LUX-1CO-18", request));
                staticCategoryHolidayProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "LUX-1CO-L17", request));

                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "43703", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "17889", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "1590", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4860-80W", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "WS-2041", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "75746", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "45161", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "FFW-912", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "EX4897-22", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "4860-32IJ", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "WS-0075", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "95447", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "WS-3133", request));
                staticCategoryProducts.add(new com.envelopes.product.Product(delegator, dispatcher, "LUX-1590-103", request));
                break;
            default:
                break;
        }

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
        if (UtilValidate.isNotEmpty(category) && UtilValidate.isNotEmpty(category.get("primaryParentCategoryId")) && !category.get("primaryParentCategoryId").equals("ENVELOPES")) {
            category = delegator.findOne("ProductCategory", UtilMisc.toMap("productCategoryId", category.getString("primaryParentCategoryId")), true);
            breadcrumb = new HashMap();
            breadcrumb.put("name", category != null ? category.description : "");
            breadcrumb.put("link", "category/~category_id=" + category.productCategoryId);
            breadcrumbs.add(0, breadcrumb);
        }
    }
    context.breadcrumbs = breadcrumbs;
} catch(Exception e) {
    Debug.logError(e, "Error trying to get envelopes category data.", module);
}