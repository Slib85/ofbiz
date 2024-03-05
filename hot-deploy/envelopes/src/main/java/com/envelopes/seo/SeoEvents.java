/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.seo;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.entity.util.EntityUtil;

import com.bigname.search.SearchHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.webapp.website.WebSiteWorker;

public class SeoEvents {
	public static final String module = SeoEvents.class.getName();

	/**
	 * Get the meta data for a specific request
	 * @param request
	 * @param response
	 * @throws GenericEntityException
	 */
	public static void getSEORule(HttpServletRequest request, HttpServletResponse response) throws GenericEntityException {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = parseSLIFacets(request);

		//set the data in the request
		getSEORule(request, delegator, context);
	}

	/**
	 * Get all the SEO rules to list
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getSEORules(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;

		try {
			List<GenericValue> rules = delegator.findList("SeoRule", null, null, UtilMisc.toList("currentView ASC", "sequenceNum ASC"), null, false);
			jsonResponse.put("rules", rules);
			success = true;
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve data. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String removeSEORule(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;

		try {
			if(UtilValidate.isNotEmpty(context.get("ruleId"))) {
				delegator.removeByAnd("SeoRule", UtilMisc.toMap("ruleId", (String) context.get("ruleId")));
				success = true;
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve data. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/**
	 * Save a SEO rule
	 * @param request
	 * @param response
	 * @return
	 */
	public static String setSEORule(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;

		try {
			GenericValue seoRule = null;
			if(UtilValidate.isNotEmpty(context.get("ruleId"))) {
				seoRule = delegator.findOne("SeoRule", UtilMisc.toMap("ruleId", (String) context.get("ruleId")), false);
			} else {
				String ruleId = delegator.getNextSeqId("SeoRule");
				seoRule = delegator.makeValue("SeoRule", UtilMisc.toMap("ruleId", ruleId));
				context.put("ruleId", ruleId);
			}

			//fix chars
			context.put("h1", EnvUtil.encodeStringToUTF8((String) context.get("h1")));
			context.put("h2", EnvUtil.encodeStringToUTF8((String) context.get("h2")));
			context.put("h3", EnvUtil.encodeStringToUTF8((String) context.get("h3")));
			context.put("metaTitle", EnvUtil.encodeStringToUTF8((String) context.get("metaTitle")));
			context.put("metaDescription", EnvUtil.encodeStringToUTF8((String) context.get("metaDescription")));
			context.put("metaKeywords", EnvUtil.encodeStringToUTF8((String) context.get("metaKeywords")));
			context.put("pageDescription", EnvUtil.encodeStringToUTF8((String) context.get("pageDescription")));
			context.put("altPageDescription", EnvUtil.encodeStringToUTF8((String) context.get("altPageDescription")));
			context.put("footerDescription", EnvUtil.encodeStringToUTF8((String) context.get("footerDescription")));

			seoRule = EnvUtil.insertGenericValueData(delegator, seoRule, context, true);
			delegator.createOrStore(seoRule);
			success = true;
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to save data. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/**
	 * Get the SEO rules for the request and set it in the request
	 * @param request
	 * @param delegator
	 * @param context
	 * @throws GenericEntityException
	 */
	protected static void getSEORule(HttpServletRequest request, Delegator delegator, Map<String, Object> context) throws GenericEntityException {
		//get website id
		String webSiteId = EnvUtil.validateContextValue(WebSiteWorker.getWebSiteId(request));

		//get current view
		String currentView = EnvUtil.validateContextValue((String) request.getAttribute("_CURRENT_VIEW_"));

		//get id
		String id = EnvUtil.validateContextValue((String) context.get("id"));

		//get product id
		String productId = EnvUtil.validateContextValue((String) context.get("product_id"));

		//get keyword
		String keyword = EnvUtil.validateContextValue((String) context.get("w"));

		//get category id
		String categoryId = null;
		if(UtilValidate.isEmpty(productId)) {
			categoryId = EnvUtil.validateContextValue((String) context.get("category_id"));
			if(UtilValidate.isNotEmpty(context.get("use"))) {
				categoryId = EnvUtil.validateContextValue((String) context.get("use"));
			}
		}

		//get style id
		String styleId = EnvUtil.validateContextValue((String) context.get("st"));

		//get color group id
		String colorGroupId = EnvUtil.validateContextValue((String) context.get("cog1"));

		//get color id
		String colorId = EnvUtil.validateContextValue((String) context.get("cog2"));

		//get collection id
		String collectionId = EnvUtil.validateContextValue((String) context.get("col"));

		//get sealing method id
		String sealingId = EnvUtil.validateContextValue((String) context.get("sm"));

		//get paper weight id
		String paperWeightId = EnvUtil.validateContextValue((String) context.get("pw"));

		//get finish id
		String finishId = EnvUtil.validateContextValue((String) context.get("finish"));

		//get rating id
		String ratingId = EnvUtil.validateContextValue((String) context.get("ra"));

		//get size id
		String sizeId = EnvUtil.validateContextValue((String) context.get("si"));

		//get customizable id
		String customizableId = EnvUtil.validateContextValue((String) context.get("customizable"));

		//get product id
		String productTypeId = EnvUtil.validateContextValue((String) context.get("prodtype"));

		String sort = EnvUtil.validateContextValue((String) context.get("sort"));
		String perPage = EnvUtil.validateContextValue((String) context.get("cnt"));
		String page = EnvUtil.validateContextValue((String) context.get("page"));

		Map<String, String> fieldMaps = UtilMisc.toMap(
				"currentView", currentView,
				"id", id,
				"productId", productId,
				"keyword", keyword,
				"categoryId", categoryId,
				"styleId", styleId,
				"colorGroupId", colorGroupId,
				"colorId", colorId,
				"collectionId", collectionId,
				"sealingId", sealingId,
				"paperWeightId", paperWeightId,
				"finishId", finishId,
				"ratingId", ratingId,
				"sizeId", sizeId,
				"customizableId", customizableId,
				"productTypeId", productTypeId
		);

		List<GenericValue> rules = delegator.findByAnd("SeoRule", fieldMaps, UtilMisc.toList("sequenceNum ASC"), true);
		GenericValue rule = null;

		if(webSiteId != null) {
			rule = EntityUtil.getFirst(EntityUtil.filterByAnd(rules, UtilMisc.toList(EntityCondition.makeCondition("webSiteId", EntityOperator.EQUALS, webSiteId))));
		}
		if(UtilValidate.isEmpty(rule)) {
			rule = EntityUtil.getFirst(rules);
		}

		String postFix = ((UtilValidate.isNotEmpty(page)) ? " | Page " + (Integer.valueOf(page) + 1) : "");

		if(UtilValidate.isNotEmpty(rule)) {
			if(UtilValidate.isNotEmpty(rule.getString("metaTitle"))) {
				request.setAttribute("metaTitle", rule.getString("metaTitle") + postFix);
			}

			if(UtilValidate.isNotEmpty(rule.getString("metaDescription"))) {
				request.setAttribute("metaDescription", rule.getString("metaDescription"));
			}

			if(UtilValidate.isNotEmpty(rule.getString("metaKeywords"))) {
				request.setAttribute("metaKeywords", rule.getString("metaKeywords"));
			}

			if(UtilValidate.isNotEmpty(rule.getString("pageDescription"))) {
				request.setAttribute("pageDescription", rule.getString("pageDescription"));
			}

			if(UtilValidate.isNotEmpty(rule.getString("altPageDescription"))) {
				request.setAttribute("altPageDescription", rule.getString("altPageDescription"));
			}

			if(UtilValidate.isNotEmpty(rule.getString("footerDescription"))) {
				request.setAttribute("footerDescription", rule.getString("footerDescription"));
			}

			if(UtilValidate.isNotEmpty(rule.getString("imageName"))) {
				request.setAttribute("seoImageName", rule.getString("imageName"));
			}

			if(UtilValidate.isNotEmpty(rule.getString("h1"))) {
				request.setAttribute("seoH1", rule.getString("h1"));
			}

			if(UtilValidate.isNotEmpty(rule.getString("h2"))) {
				request.setAttribute("seoH2", rule.getString("h2"));
			}

			if(UtilValidate.isNotEmpty(rule.getString("h3"))) {
				request.setAttribute("seoH3", rule.getString("h3"));
			}

			if(UtilValidate.isNotEmpty(rule.getString("h1Color"))) {
				request.setAttribute("seoH1Color", rule.getString("h1Color"));
			}

			if(UtilValidate.isNotEmpty(rule.getString("backgroundBannerColor"))) {
				request.setAttribute("bannerColor", rule.getString("backgroundBannerColor"));
			}
		}
	}

	protected static Map<String, Object> parseSLIFacets(HttpServletRequest request) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		//facets must remove lists
		SearchHelper.removeListValues(context);

		String appliedFacets = SearchHelper.getUrlParameter(context.get("af"));
		List<String> listAF = StringUtil.split(appliedFacets, " ");

		//remove wildcard and treat as null
		if(UtilValidate.isNotEmpty(context.get("w")) && "*".equalsIgnoreCase(SearchHelper.getUrlParameter(context.get("w")))) {
			context.remove("w");
		}

		if(UtilValidate.isNotEmpty(listAF)) {
			for(String facet: listAF) {
				String[] facetAndType = facet.split(":");
				//add use
				if(UtilValidate.isNotEmpty(facetAndType) && facetAndType[0].equalsIgnoreCase("use")) {
					context.put("use", (context.containsKey("use")) ? (String) context.get("use") + "," + facetAndType[1].trim() : facetAndType[1].trim());
				}
				//add style
				if(UtilValidate.isNotEmpty(facetAndType) && facetAndType[0].equalsIgnoreCase("st")) {
					context.put("st", (context.containsKey("st")) ? (String) context.get("st") + "," + facetAndType[1].trim() : facetAndType[1].trim());
				}
				//add color group
				if(UtilValidate.isNotEmpty(facetAndType) && facetAndType[0].equalsIgnoreCase("cog1")) {
					context.put("cog1", (context.containsKey("cog1")) ? (String) context.get("cog1") + "," + facetAndType[1].trim() : facetAndType[1].trim());
				}
				//add color
				if(UtilValidate.isNotEmpty(facetAndType) && facetAndType[0].equalsIgnoreCase("cog2")) {
					context.put("cog2", (context.containsKey("cog2")) ? (String) context.get("cog2") + "," + facetAndType[1].trim() : facetAndType[1].trim());
				}
				//add collection
				if(UtilValidate.isNotEmpty(facetAndType) && facetAndType[0].equalsIgnoreCase("col")) {
					context.put("col", (context.containsKey("col")) ? (String) context.get("col") + "," + facetAndType[1].trim() : facetAndType[1].trim());
				}
				//add size
				if(UtilValidate.isNotEmpty(facetAndType) && facetAndType[0].equalsIgnoreCase("si")) {
					context.put("si", (context.containsKey("si")) ? (String) context.get("si") + "," + facetAndType[1].trim() : facetAndType[1].trim());
				}
				//add sealing method
				if(UtilValidate.isNotEmpty(facetAndType) && facetAndType[0].equalsIgnoreCase("sm")) {
					context.put("sm", (context.containsKey("sm")) ? (String) context.get("sm") + "," + facetAndType[1].trim() : facetAndType[1].trim());
				}
				//add paper weight
				if(UtilValidate.isNotEmpty(facetAndType) && facetAndType[0].equalsIgnoreCase("pw")) {
					context.put("pw", (context.containsKey("pw")) ? (String) context.get("pw") + "," + facetAndType[1].trim() : facetAndType[1].trim());
				}
				//add finish
				if(UtilValidate.isNotEmpty(facetAndType) && facetAndType[0].equalsIgnoreCase("finish")) {
					context.put("finish", (context.containsKey("finish")) ? (String) context.get("finish") + "," + facetAndType[1].trim() : facetAndType[1].trim());
				}
				//add rating
				if(UtilValidate.isNotEmpty(facetAndType) && facetAndType[0].equalsIgnoreCase("ra")) {
					context.put("ra", (context.containsKey("ra")) ? (String) context.get("ra") + "," + facetAndType[1].trim() : facetAndType[1].trim());
				}
				//add product type
				if(UtilValidate.isNotEmpty(facetAndType) && facetAndType[0].equalsIgnoreCase("prodtype")) {
					context.put("prodtype", (context.containsKey("prodtype")) ? (String) context.get("prodtype") + "," + facetAndType[1].trim() : facetAndType[1].trim());
				}
				//add customizable
				if(UtilValidate.isNotEmpty(facetAndType) && facetAndType[0].equalsIgnoreCase("customizable")) {
					context.put("customizable", (context.containsKey("customizable")) ? (String) context.get("customizable") + "," + facetAndType[1].trim() : facetAndType[1].trim());
				}
			}
		}

		return context;
	}

	/**
	 * Get all the Rewrite rules to list
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getRewriteRules(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;

		try {
			List<GenericValue> rules = delegator.findList("RewriteRule", EntityCondition.makeCondition("manual", "Y"), null, UtilMisc.toList("webSiteId ASC", "fromUrl ASC"), null, false);
			jsonResponse.put("rules", rules);
			success = true;
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve data. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/**
	 * Save a Rewrite rule
	 * @param request
	 * @param response
	 * @return
	 */
	public static String setRewriteRule(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		context.put("timeStamp", UtilDateTime.nowTimestamp());

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;

		try {
			GenericValue rewriteRule = null;
			if(UtilValidate.isNotEmpty(context.get("rewriteTypeId")) && UtilValidate.isNotEmpty(context.get("fromUrl")) && UtilValidate.isNotEmpty(context.get("toUrl"))) {
				if("REWRITE".equalsIgnoreCase((String) context.get("rewriteTypeId")) && !((String) context.get("toUrl")).contains("/control/")) {
					jsonResponse.put("errorMessage", "Can't do rewrite to non ofbiz URL.");
				} else if("REDIRECT".equalsIgnoreCase((String) context.get("rewriteTypeId")) && UtilValidate.isEmpty(context.get("responseCode"))) {
					jsonResponse.put("errorMessage", "Response Code is required for Redirects.");
				} else {
					String ruleId = EnvUtil.MD5((String) context.get("fromUrl"));

					if(UtilValidate.isNotEmpty(context.get("ruleId")) && ruleId.equals(context.get("ruleId"))) {
						rewriteRule = EntityQuery.use(delegator).from("RewriteRule").where("ruleId", context.get("ruleId"), "webSiteId", (String) context.get("webSiteId")).queryOne();
					} else {
						rewriteRule = delegator.makeValue("RewriteRule", UtilMisc.toMap("ruleId", ruleId));
						context.put("ruleId", ruleId);

						if (UtilValidate.isNotEmpty(context.get("ruleId")) && !ruleId.equals(context.get("ruleId"))) {
							delegator.removeValue(EntityQuery.use(delegator).from("RewriteRule").where("ruleId", context.get("ruleId"), "webSiteId", (String) context.get("webSiteId")).queryOne());
						}
					}

					//#if the rule doesnt exist, or exist and is editable update it
					if(!doesRewriteRuleExist(delegator, (String) context.get("ruleId"), (String) context.get("webSiteId"))) {
						rewriteRule = EnvUtil.insertGenericValueData(delegator, rewriteRule, context, true);
						delegator.createOrStore(rewriteRule);
						success = true;
					}
				}
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			jsonResponse.put("errorMessage", e + " : " + e.getMessage());
			Debug.logError("Error while trying to save data. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String removeRewriteRule(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;

		try {
			if(UtilValidate.isNotEmpty(context.get("ruleId")) && UtilValidate.isNotEmpty(context.get("webSiteId"))) {
				delegator.removeByAnd("RewriteRule", UtilMisc.toMap("ruleId", (String) context.get("ruleId"), "webSiteId", (String) context.get("webSiteId")));
				success = true;
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve data. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/**
	 * Check if the rule already exist and does not have manual is null or N
	 * These rules are not editable
	 * @param ruleId
	 * @return
	 */
	public static boolean doesRewriteRuleExist(Delegator delegator, String ruleId, String webSiteId) throws GenericEntityException {
		List<EntityCondition> conditionList = new ArrayList<>();
		conditionList.add(EntityCondition.makeCondition("ruleId", EntityOperator.EQUALS, ruleId));
		conditionList.add(EntityCondition.makeCondition("webSiteId", EntityOperator.EQUALS, webSiteId));
		conditionList.add(EntityCondition.makeCondition(EntityCondition.makeCondition("manual", EntityOperator.EQUALS, null), EntityOperator.OR, EntityCondition.makeCondition("manual", EntityOperator.EQUALS, "N")));

		GenericValue rule = EntityUtil.getFirst(delegator.findList("RewriteRule", EntityCondition.makeCondition(conditionList, EntityOperator.AND), null, null, null, false));
		return UtilValidate.isNotEmpty(rule);
	}
}