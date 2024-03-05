import java.util.Map;
import java.util.HashMap;

import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.GenericValue;

import com.bigname.search.elasticsearch.SearchTranslator;
import com.bigname.search.elasticsearch.util.FilterUtil;
import com.bigname.search.elasticsearch.util.SearchUtil;
import com.envelopes.util.EnvUtil;

String module = "shopByColor.groovy";
Map<String, Object> params = EnvUtil.getParameterMap(request);
String colorGroupName = params.get("category_id");

List breadcrumbs = new ArrayList();

try {
    Map breadcrumb = new HashMap();
    breadcrumb.put("name", "Shop By Color" );

    if(UtilValidate.isEmpty(colorGroupName)) {
        breadcrumb.put("link", "");
        breadcrumbs.add(breadcrumb);

        FilterUtil fl = new FilterUtil(delegator, globalContext.webSiteId);
        context.colorGroups = fl.getFacets("cog1");
        context.mode = "colorGroup";
    } else {
        breadcrumb.put("link", "shopByColor");
        breadcrumbs.add(breadcrumb);

        if(colorGroupName.contains("_")) {
            String parentGroup = colorGroupName.substring(0, colorGroupName.indexOf("_"));
            params.remove("category_id");
            params.put("af", "cog2:" + SearchUtil.cleanFacet(colorGroupName, true));
            context.params = params;

            GenericValue facet = SearchTranslator.getFacet(delegator, "cog2", SearchUtil.cleanFacet(colorGroupName, true));
            if(facet != null) {
                colorGroupName = facet.getString("facetName");
            }

            breadcrumb = new HashMap();
            breadcrumb.put("name" , parentGroup);
            breadcrumb.put("link", "shopByColor/~category_id=" + parentGroup);
            breadcrumbs.add(breadcrumb);
            breadcrumb = new HashMap();
            breadcrumb.put("name" , colorGroupName);
            breadcrumb.put("link", "");
            breadcrumbs.add(breadcrumb);

            context.colorGroupName = colorGroupName;
            context.mode = "products";
        } else {
            breadcrumb = new HashMap();
            breadcrumb.put("name" , colorGroupName);
            breadcrumb.put("link", "");
            breadcrumbs.add(breadcrumb);

            GenericValue category = delegator.findOne("ProductCategory", UtilMisc.toMap("productCategoryId", colorGroupName), true);
            if (category != null) {
                if (UtilValidate.isNotEmpty(category.getString("description"))) {
                    request.setAttribute("seoH1", category.getString("description"));
                }
                if (UtilValidate.isNotEmpty(category.getString("longDescription"))) {
                    request.setAttribute("pageDesc", category.getString("longDescription"));
                }
            }

            params.put("af", "cog1:" + SearchUtil.cleanFacet(colorGroupName, false));
            FilterUtil fl = new FilterUtil(delegator, params, globalContext.webSiteId);
            context.colors = FilterUtil.sortedFilter(fl.getFacets("cog2"));
            
            context.mode = "color";
            context.colorGroupName = colorGroupName;
        }
    }
} catch(Exception e) {
    Debug.logError(e, "Error trying to load colors.", module);
}

context.breadcrumbs = breadcrumbs;