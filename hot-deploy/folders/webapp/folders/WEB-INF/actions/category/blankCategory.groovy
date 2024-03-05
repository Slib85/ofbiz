import java.util.*;
import com.folders.category.*;

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
                                                                                                                                                                                                                                                    
String module = "blankCategory.groovy";

List breadcrumbs = new ArrayList();


String categoryId = request.getParameter("category_id");
if(StaticCategories.blankCategories.containsKey(categoryId)) {
    Map<String, Object> s_category = StaticCategories.blankCategories.get(request.getParameter("category_id"));
    breadcrumbs.add(UtilMisc.toMap("name", s_category.get("seoH1")));
    context.breadcrumbs = breadcrumbs;
    context.subCategories = s_category.get("subCategories");
}
