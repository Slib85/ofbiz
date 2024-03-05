import com.envelopes.refinements.*;
import org.apache.ofbiz.base.util.UtilValidate;
import com.envelopes.util.EnvUtil;
import com.envelopes.refinements.*;

String module = "collections.groovy";
String collectionsName = EnvUtil.getParameterMap(request).get("category_id");
if(UtilValidate.isEmpty(collectionsName)) {
    context.widgetResponse = RefinementsUtil.getCollections();
    context.mode = "collections";
} else if(RefinementsUtil.isCollection(collectionsName)) {
    String facetName = RefinementsUtil.getSliFacetFromCategoryId(collectionsName);
    context.facet = facetName;
    context.mode = "products";
    request.setAttribute("w", "*");
    request.setAttribute"af", "col:" + facetName;
    request.setAttribute("requestType", RefinementsUtil.RequestType.SEARCH);
    context.widgetResponse = RefinementsUtil.getRefinementsResponse(request);
} else {
    String facetName = RefinementsUtil.getSliFacetFromCategoryId(collectionsName);
    context.facet = facetName;
    context.mode = "products";
    request.setAttribute("w", "*");
    request.setAttribute"af", "cog2:" + facetName;
    request.setAttribute("requestType", RefinementsUtil.RequestType.SEARCH);
    context.widgetResponse = RefinementsUtil.getRefinementsResponse(request);
}

List breadcrumbs = new ArrayList();
Map breadcrumb = new HashMap();
breadcrumb.put("name", "Shop by Collection");
breadcrumb.put("link", "");
breadcrumbs.add(breadcrumb);
context.breadcrumbs = breadcrumbs;
