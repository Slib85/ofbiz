package com.envelopes.refinements;

import com.envelopes.product.ProductHelper;
import com.envelopes.refinements.comparator.TemplateCategoryComparator;
import com.envelopes.refinements.comparator.TemplateSizeComparator;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.cache.UtilCache;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.Delegator;

import java.util.*;

/**
 * Created by Manu on 3/17/2015.
 */
public class TemplateManager {

	protected static final String module = ProductHelper.class.getName();

	protected static final UtilCache<String, Map<String, Set<Map<String, Object>>>> templatesCache = UtilCache.createUtilCache("envelopes.refinements.templates", 0, 0);

//	public static List<Map<String, Object>> findTemplates(String[] categoryIds, String[] sizeIds, Delegator delegator) { // Method signature needed to support category and size filters
	public static List<Map<String, Object>> findTemplates(Delegator delegator) {
		Map<String, Set<Map<String, Object>>> templates = 	getTemplates(delegator);
		List<Map<String, Object>> results = new ArrayList<>();
		for (Map.Entry<String, Set<Map<String, Object>>> entry : templates.entrySet()) {
			Map<String, Object> category = new HashMap<> ();
			category.put("category_id", entry.getKey());
			try {
				category.put("categoryName", delegator.findOne("ProductCategory", true, UtilMisc.toMap("productCategoryId", entry.getKey().replace("CAT:", ""))).getString("description"));
			} catch (GenericEntityException e) {
				category.put("categoryName", entry.getKey().replace("CAT:", ""));
			}
			category.put("categoryImage", entry.getKey().replace("CAT:", "").toLowerCase() + ".png");
			List<Map<String, Object>> templatesList = new ArrayList<>();
			templatesList.addAll(templates.get(entry.getKey()));
			Collections.sort(templatesList, new TemplateSizeComparator());
			category.put("templates", templatesList);
			results.add(category);
		}
		Collections.sort(results, new TemplateCategoryComparator());

		//Code needed to support filters

		/*if(categoryIds.length == 0) {
			for (Map.Entry<String, Set<Map<String, Object>>> entry : templates.entrySet()) {
				if(entry.getKey().startsWith("CAT:")) {
					List<Map<String, Object>> templatesList = new ArrayList<>();
					templatesList.addAll(templates.get(entry.getKey()));
					categoryResults.removeAll(templatesList);
					categoryResults.addAll(templatesList);
				}
			}
		} else {
			for (String categoryId : categoryIds) {
				List<Map<String, Object>> templatesList = new ArrayList<>();
				templatesList.addAll(templates.get("CAT:" + categoryId));
				categoryResults.removeAll(templatesList);
				categoryResults.addAll(templatesList);
			}
		}


		List<Map<String, Object>> sizeResults = new ArrayList<>();
		if(sizeIds.length == 0) {
			for (Map.Entry<String, Set<Map<String, Object>>> entry : templates.entrySet()) {
				if(entry.getKey().startsWith("SI:")) {
					List<Map<String, Object>> templatesList = new ArrayList<>();
					templatesList.addAll(templates.get(entry.getKey()));
					sizeResults.removeAll(templatesList);
					sizeResults.addAll(templatesList);
				}
			}
		} else {
			for (String sizeId : sizeIds) {
				List<Map<String, Object>> templatesList = new ArrayList<>();
				templatesList.addAll(templates.get("SI:" + sizeId));
				sizeResults.removeAll(templatesList);
				sizeResults.addAll(templatesList);
			}
		}

		categoryResults.retainAll(sizeResults);
*/
		return results;


	}

	public static Map<String, Set<Map<String, Object>>> getTemplates(Delegator delegator) {
		String cacheKey = "all-templates";
		if(!templatesCache.containsKey(cacheKey)) {
			try {
				templatesCache.put(cacheKey, ProductHelper.getDesignTemplates(delegator));
			} catch (GenericEntityException e) {
				Debug.logError(e, "Error occurred while populating the template cache" + " " + e + " : " + e.getMessage(), module);
				templatesCache.put(cacheKey, new HashMap<String, Set<Map<String, Object>>>());
			}
		}
		return templatesCache.get(cacheKey);
	}


}
