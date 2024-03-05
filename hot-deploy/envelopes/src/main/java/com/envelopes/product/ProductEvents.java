/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.product;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.RoundingMode;
import java.lang.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.List;

import com.bigname.pricingengine.PricingEngineHelper;
import com.envelopes.http.FileHelper;
import com.google.gson.Gson;
import com.lob.Util;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.transaction.GenericTransactionException;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.entity.util.*;
import org.apache.ofbiz.order.shoppingcart.ShoppingCart;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartEvents;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.envelopes.util.*;
import com.envelopes.party.PartyHelper;
import org.apache.ofbiz.webapp.website.WebSiteWorker;

public class ProductEvents {
	public static final String module = ProductEvents.class.getName();
	public static final List<String> FEATURES_TO_SHOW;
	protected static final List<String> TOP_RATED_PRODUCTS;

	static {
		FEATURES_TO_SHOW = new ArrayList<String>();
		FEATURES_TO_SHOW.add("COLOR");
		FEATURES_TO_SHOW.add("SIZE");
		FEATURES_TO_SHOW.add("PAPER_WEIGHT");
		FEATURES_TO_SHOW.add("SEALING_METHOD");
		FEATURES_TO_SHOW.add("RECYCLED_CONTENT");
		//FEATURES_TO_SHOW.add("COLLECTION");
		FEATURES_TO_SHOW.add("LASER");
		FEATURES_TO_SHOW.add("INKJET");
		FEATURES_TO_SHOW.add("METRIC_SIZE");
		FEATURES_TO_SHOW.add("WINDOW_SIZE");
		FEATURES_TO_SHOW.add("WINDOW_POSITION");
		FEATURES_TO_SHOW.add("AVAILABILITY");
		FEATURES_TO_SHOW.add("SHAPE");
		FEATURES_TO_SHOW.add("INNER_PANEL");
		FEATURES_TO_SHOW.add("INNER_POCKET");
		FEATURES_TO_SHOW.add("LABELS_PER_SHEET");
		FEATURES_TO_SHOW.add("TEXTURE");
		FEATURES_TO_SHOW.add("TOP_MARGIN");
		FEATURES_TO_SHOW.add("BOTTOM_MARGIN");
		FEATURES_TO_SHOW.add("LEFT_MARGIN");
		FEATURES_TO_SHOW.add("RIGHT_MARGIN");
		FEATURES_TO_SHOW.add("CORNER_RADIUS");
		FEATURES_TO_SHOW.add("VERTICAL_SPACING");
		FEATURES_TO_SHOW.add("HORIZONTAL_SPACING");
		FEATURES_TO_SHOW.add("BACKSLITS");
		//FEATURES_TO_SHOW.add("COMPARE_TO_BRAND");
		FEATURES_TO_SHOW.add("GRADE");
		FEATURES_TO_SHOW.add("BRAND");
		FEATURES_TO_SHOW.add("INNER_DIMENSION");
		FEATURES_TO_SHOW.add("MANUFACTURER_NUMBER");
		FEATURES_TO_SHOW.add("DIMENSION_CLOSED");
		FEATURES_TO_SHOW.add("DIMENSION_OPEN");
		FEATURES_TO_SHOW.add("NUMBER_OF_POCKETS");
		FEATURES_TO_SHOW.add("POCKET_SPEC");

		TOP_RATED_PRODUCTS = new ArrayList<>();
		TOP_RATED_PRODUCTS.add("72924");
		TOP_RATED_PRODUCTS.add("4880-GB");
		TOP_RATED_PRODUCTS.add("43703");
		TOP_RATED_PRODUCTS.add("4260-15");
		TOP_RATED_PRODUCTS.add("5865-01");
		TOP_RATED_PRODUCTS.add("17889");
		TOP_RATED_PRODUCTS.add("10902");
		TOP_RATED_PRODUCTS.add("81211-C-78");
		TOP_RATED_PRODUCTS.add("EX10-LEBA712PF");
		TOP_RATED_PRODUCTS.add("LUX-4872-101");
		TOP_RATED_PRODUCTS.add("LEVC-99");
	}

	/*
	 * Get the product
	 * Returns a JSON Object
	 */
	public static String getProduct(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		String productId = (String) context.get("id");
		if(UtilValidate.isNotEmpty(productId)) {
			try {
				Product product = new Product(delegator, dispatcher, productId, ShoppingCartEvents.getCartObject(request));
				if(product.isValid()) {
					product.setAllData();
					jsonResponse.put("success", true);
					jsonResponse.put("product", product.toMap());
				}
			} catch(GenericEntityException e) {
				jsonResponse.put("success", false);
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
			} catch(GenericServiceException e) {
				jsonResponse.put("success", false);
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
			}

		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}
	/**
	 * Retun JSON representation of size, style and products based off of website, product type id and style
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getProductSizeAndStyle(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;

		try {
			String md5Str = EnvUtil.MD5(WebSiteWorker.getWebSiteId(request) + (String) context.get("productTypeId") + (String) context.get("productCategoryId"));
			if(!ProductHelper.productSizeAndStyle.containsKey(md5Str)) {
				List<GenericValue> allProducts = ProductHelper.getAllProductsInCategory(delegator, WebSiteWorker.getWebSiteId(request), (String) context.get("productTypeId"), (String) context.get("productCategoryId"));
				if(UtilValidate.isNotEmpty(allProducts.size())) {
					ProductHelper.productSizeAndStyle.put(md5Str, ProductHelper.getProductAndStyle(delegator, dispatcher, allProducts));
				}
			}

			jsonResponse.put("data", ProductHelper.productSizeAndStyle.get(md5Str));
			success = true;
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve product, size, style data. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get the product price list and/or value for custom quantity
	 * Returns a JSON Object
	 */
	public static String getProductPrice(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = getProductPrice(request, context);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

    public static Map<String, Object> getProductPrice(HttpServletRequest request, Map<String, Object> context) {
		Map<String, Object> jsonResponse = new HashMap<>();
		String pricingRequest = request.getParameter("pricingRequest");

		if(UtilValidate.isEmpty(pricingRequest)) {
			pricingRequest = (String) context.get("pricingRequest");
		}


		if(UtilValidate.isEmpty(pricingRequest)) {
			LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
			Delegator delegator = (Delegator) request.getAttribute("delegator");

			String partyId         = (String) context.get("partyId");
			Integer quantity       = (UtilValidate.isNotEmpty(context.get("quantity")))      ? context.get("quantity")      instanceof String ? Integer.valueOf((String) context.get("quantity"))      : ((BigDecimal) context.get("quantity")).intValue()       : Integer.valueOf(0);
			Integer colorsFront    = (UtilValidate.isNotEmpty(context.get("colorsFront")))   ? context.get("colorsFront")   instanceof String ? Integer.valueOf((String) context.get("colorsFront"))   : (Integer)     context.get("colorsFront")                : Integer.valueOf(0);
			Integer colorsBack     = (UtilValidate.isNotEmpty(context.get("colorsBack")))    ? context.get("colorsBack")    instanceof String ? Integer.valueOf((String) context.get("colorsBack"))    : (Integer)      context.get("colorsBack")                : Integer.valueOf(0);
			Boolean isRush         = (UtilValidate.isNotEmpty(context.get("isRush")))        ? context.get("isRush")        instanceof String ? Boolean.valueOf((String) context.get("isRush"))        : (Boolean)     context.get("isRush")                     : false;
			Boolean whiteInkFront  = (UtilValidate.isNotEmpty(context.get("whiteInkFront"))) ? context.get("whiteInkFront") instanceof String ? Boolean.valueOf((String) context.get("whiteInkFront")) : (Boolean)     context.get("whiteInkFront")              : false;
			Boolean whiteInkBack   = (UtilValidate.isNotEmpty(context.get("whiteInkBack")))  ? context.get("whiteInkBack")  instanceof String ? Boolean.valueOf((String) context.get("whiteInkBack"))  : (Boolean)     context.get("whiteInkBack")               : false;
			Integer cuts           = (UtilValidate.isNotEmpty(context.get("cuts")))          ? context.get("cuts")          instanceof String ? Integer.valueOf((String) context.get("cuts"))          : (Integer)      context.get("cuts")                      : Integer.valueOf("0");
			Boolean isFolded       = (UtilValidate.isNotEmpty(context.get("isFolded")))      ? context.get("isFolded")      instanceof String ? Boolean.valueOf((String) context.get("isFolded"))      : (Boolean)     context.get("isFolded")                   : false;
			Boolean isFullBleed    = (UtilValidate.isNotEmpty(context.get("isFullBleed")))   ? context.get("isFullBleed")   instanceof String ? Boolean.valueOf((String) context.get("isFullBleed"))   : (Boolean)     context.get("isFullBleed")                : false;
			Integer addresses      = (UtilValidate.isNotEmpty(context.get("addresses")))     ? context.get("addresses")     instanceof String ? Integer.valueOf((String) context.get("addresses"))     : (Integer)     context.get("addresses")                  : Integer.valueOf(0);
			BigDecimal customPrice = (UtilValidate.isNotEmpty(context.get("price")))         ? context.get("price")         instanceof String ? new BigDecimal((String) context.get("price"))          : (BigDecimal)  context.get("price")                      : BigDecimal.ZERO;
			String templateId      = (String) context.get("templateId");

			Map<Integer, Map> productPrice = new LinkedHashMap<Integer, Map>();
			try {
				/**
				 * ---------------------------- WARNING -----------------------------------
				 * This is a endpoint for entire site, we will not pass in cart object here because this endpoint is cached and should not be tailored to any specific user
				 **/
				GenericValue product = ProductHelper.getProduct(delegator, (String) context.get("id"));
				productPrice = ProductHelper.getProductPrice(null, delegator, dispatcher, product, quantity, partyId, colorsFront, colorsBack, isRush, whiteInkFront, whiteInkBack, cuts, isFolded, isFullBleed, addresses, customPrice, templateId);
			} catch (GenericEntityException | GenericServiceException e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
			}

			if (UtilValidate.isEmpty(productPrice)) {
				jsonResponse.put("success", false);
			} else {
				jsonResponse.put("success", true);
			}
			jsonResponse.put("priceList", productPrice);
		} else {
			try {
				Map<Integer, Map> productPrice = new TreeMap<>();
				Map<String, Object> output = PricingEngineHelper.getPricing(new Gson().fromJson(pricingRequest, Map.class));
				if(UtilValidate.isNotEmpty(output) && UtilValidate.isNotEmpty(output.get("VENDORS_SIMPLE_PRICING"))) {
					Map<String, Object> vendorPricing = (LinkedHashMap<String, Object>) output.get("VENDORS_SIMPLE_PRICING");
					Map<Integer, String> qtyAndPrice = (LinkedHashMap<Integer, String>) vendorPricing.get(vendorPricing.keySet().toArray()[0]);
					for (Map.Entry<Integer, String> entry : qtyAndPrice.entrySet()) {
						productPrice.put(entry.getKey(), UtilMisc.toMap("price", new BigDecimal(entry.getValue()), "unadjustedPrice", new BigDecimal(entry.getValue())));
					}
					jsonResponse.put("priceList", productPrice);
				}
			} catch(Exception e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "Error trying to get PricingData due to:" + e.getMessage(), module);
				jsonResponse.put("success", false);
			}
		}

        return jsonResponse;
    }

	/*
	 * Get the product price list and/or value for custom quantity
	 * Returns a JSON Object
	 */
	public static String getOriginalPrice(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = getOriginalPrice(request, context);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static Map<String, Object> getOriginalPrice(HttpServletRequest request, Map<String, Object> context) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		String partyId         = (String) context.get("partyId");
		Integer quantity       = (UtilValidate.isNotEmpty(context.get("quantity")))      ? context.get("quantity")      instanceof String ? Integer.valueOf((String) context.get("quantity"))      : ((BigDecimal) context.get("quantity")).intValue()       : Integer.valueOf(0);
		Integer colorsFront    = (UtilValidate.isNotEmpty(context.get("colorsFront")))   ? context.get("colorsFront")   instanceof String ? Integer.valueOf((String) context.get("colorsFront"))   : (Integer)     context.get("colorsFront")                : Integer.valueOf(0);
		Integer colorsBack     = (UtilValidate.isNotEmpty(context.get("colorsBack")))    ? context.get("colorsBack")    instanceof String ? Integer.valueOf((String) context.get("colorsBack"))    : (Integer)      context.get("colorsBack")                : Integer.valueOf(0);
		Boolean isRush         = (UtilValidate.isNotEmpty(context.get("isRush")))        ? context.get("isRush")        instanceof String ? Boolean.valueOf((String) context.get("isRush"))        : (Boolean)     context.get("isRush")                     : false;
		Boolean whiteInkFront  = (UtilValidate.isNotEmpty(context.get("whiteInkFront"))) ? context.get("whiteInkFront") instanceof String ? Boolean.valueOf((String) context.get("whiteInkFront")) : (Boolean)     context.get("whiteInkFront")              : false;
		Boolean whiteInkBack   = (UtilValidate.isNotEmpty(context.get("whiteInkBack")))  ? context.get("whiteInkBack")  instanceof String ? Boolean.valueOf((String) context.get("whiteInkBack"))  : (Boolean)     context.get("whiteInkBack")               : false;
		Integer cuts           = (UtilValidate.isNotEmpty(context.get("cuts")))          ? context.get("cuts")          instanceof String ? Integer.valueOf((String) context.get("cuts"))          : (Integer)      context.get("cuts")                      : Integer.valueOf("0");
		Boolean isFolded       = (UtilValidate.isNotEmpty(context.get("isFolded")))      ? context.get("isFolded")      instanceof String ? Boolean.valueOf((String) context.get("isFolded"))      : (Boolean)     context.get("isFolded")                   : false;
		Boolean isFullBleed    = (UtilValidate.isNotEmpty(context.get("isFullBleed")))   ? context.get("isFullBleed")   instanceof String ? Boolean.valueOf((String) context.get("isFullBleed"))   : (Boolean)     context.get("isFullBleed")                : false;
		Integer addresses      = (UtilValidate.isNotEmpty(context.get("addresses")))     ? context.get("addresses")     instanceof String ? Integer.valueOf((String) context.get("addresses"))     : (Integer)     context.get("addresses")                  : Integer.valueOf(0);
		BigDecimal customPrice = (UtilValidate.isNotEmpty(context.get("price")))         ? context.get("price")         instanceof String ? new BigDecimal((String) context.get("price"))          : (BigDecimal)  context.get("price")                      : BigDecimal.ZERO;
		String templateId      = (String) context.get("templateId");

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		Map<Integer, Map> productPrice = new LinkedHashMap<Integer, Map>();
		try {
			/**
			 * ---------------------------- WARNING -----------------------------------
			 * This is a endpoint for entire site, we will not pass in cart object here because this endpoint is cached and should not be tailored to any specific user
			 **/
			GenericValue product = ProductHelper.getProduct(delegator, (String) context.get("id"));
			productPrice = ProductHelper.getOriginalPrice(null, delegator, dispatcher, product, quantity, partyId, colorsFront, colorsBack, isRush, whiteInkFront, whiteInkBack, cuts, isFolded, isFullBleed, addresses, customPrice, templateId);
		} catch(GenericEntityException|GenericServiceException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		}

		if(UtilValidate.isEmpty(productPrice)) {
			jsonResponse.put("success", false);
		} else {
			jsonResponse.put("success", true);
		}
		jsonResponse.put("priceList", productPrice);

		return jsonResponse;
	}

	/*
	 * Determine the min color available for printing
	 */
	public static String getMinAndMaxPrintColor(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator)request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);

		String productId = (String) context.get("id");

		Map<String, Integer> colors = null;
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", true);
		try {
			GenericValue product = EntityQuery.use(delegator).from("Product").where("productId", productId).cache().queryOne();
			colors = ProductHelper.getMinAndMaxPrintColor(delegator, product);
		} catch (GenericEntityException e) {
			jsonResponse.put("success", false);
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("colors", colors);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get the product name of a given sku
	 */
	public static String getProductName(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator)request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);

		String productId = (String) context.get("id");

		GenericValue product = null;
		Map<String, String> size = null;
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		try {
			product = EntityQuery.use(delegator).from("Product").where("productId", productId).cache().queryOne();
			size = ProductHelper.getProductFeatures(delegator, product, UtilMisc.toList("SIZE_CODE"));
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		}

		if(UtilValidate.isNotEmpty(product)) {
			jsonResponse.put("success", true);
			jsonResponse.put("productId", product.getString("productId"));
			jsonResponse.put("productName", product.getString("productName"));
			jsonResponse.put("tagLine", product.getString("tagLine"));
			jsonResponse.put("size", size.get("SIZE_CODE"));
		} else {
			jsonResponse.put("success", false);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get the product name of a given sku
	 */
	public static String getProductDesc(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator)request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);

		String productId = (String) context.get("id");

		GenericValue product = null;
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		try {
			product = EntityQuery.use(delegator).from("Product").where("productId", productId).cache().queryOne();
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		}

		if(UtilValidate.isNotEmpty(product)) {
			jsonResponse.put("success", true);
			jsonResponse.put("productId", product.getString("productId"));
			jsonResponse.put("desc", product.getString("longDescription"));
			jsonResponse.put("colorDesc", product.getString("colorDescription"));
		} else {
			jsonResponse.put("success", false);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get the product name of a given sku
	 */
	public static String getProductImage(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator)request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);

		String productId = (String) context.get("id");

		GenericValue product = null;
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		try {
			product = EntityQuery.use(delegator).from("Product").where("productId", productId).cache().queryOne();
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		}

		if(UtilValidate.isNotEmpty(product)) {
			jsonResponse.put("success", true);
			jsonResponse.put("productId", product.getString("productId"));
			String imageName = product.getString("smallImageUrl");
			if(UtilValidate.isNotEmpty(imageName)) {
				imageName = imageName.replace("/images/products/small/","");
			}
			jsonResponse.put("image", imageName);
		} else {
			jsonResponse.put("success", false);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get the product name of a given sku
	 */
	public static String getProductReviews(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator)request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);

		String productId = (String) context.get("id");

		GenericValue product = null;
		Map<String, Object> reviews = new HashMap<String, Object>();
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		try {
			product = EntityQuery.use(delegator).from("Product").where("productId", productId).cache().queryOne();
			if(product != null) {
				reviews = ProductHelper.getProductReviews(delegator, product, null);
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		}

		if(UtilValidate.isNotEmpty(product)) {
			jsonResponse.put("success", true);
			jsonResponse.put("totals", reviews.get("totals"));
			jsonResponse.put("reviews", reviews.get("reviews"));
		} else {
			jsonResponse.put("success", false);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

    public static Map<String, Object> getReviews(HttpServletRequest request, String productId) {
        Delegator delegator = (Delegator)request.getAttribute("delegator");
		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
        Map<String, Object> context = EnvUtil.getParameterMap(request);

        GenericValue product = null;
        Map<String, Object> reviews = new HashMap<String, Object>();
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        try {
            product = EntityQuery.use(delegator).from("Product").where("productId", productId).cache().queryOne();
            if(product != null) {
                reviews = ProductHelper.getProductReviews(delegator, product, EnvUtil.getSalesChannelEnumId(cart.getWebSiteId()));
            }
        } catch (GenericEntityException e) {
            EnvUtil.reportError(e);
            Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
        }
        return reviews;
    }

	public static List<Map<String, Object>> getDynamicTopRatedProducts(HttpServletRequest request, HttpServletResponse response) throws GenericEntityException {
		Delegator delegator = (Delegator)request.getAttribute("delegator");

		List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
		conditionList.add(EntityCondition.makeCondition("productReviewId", EntityOperator.GREATER_THAN_EQUAL_TO, 25l));
		EntityCondition condition = EntityCondition.makeCondition(conditionList, EntityOperator.AND);
		List<String> parentProductIds = new ArrayList<>();
		List<Map<String, Object>> products = new ArrayList<>();
		String webSiteId = WebSiteWorker.getWebSiteId(request);
		if(UtilValidate.isEmpty(webSiteId)) {
			webSiteId = "envelopes";
		}

		List<GenericValue> topRatedProducts = EntityQuery.use(delegator).select("productId", "parentProductId", "statusId", "salesChannelEnumId", "productReviewId", "productRating").from("ProductAndReviewSummary").having(condition).where(UtilMisc.toMap("statusId", "PRR_APPROVED", "salesChannelEnumId", EnvUtil.getSalesChannelEnumId(webSiteId))).orderBy("-productReviewId ").distinct().cache().queryList();
		for (GenericValue topRatedProduct : topRatedProducts) {
			if(!parentProductIds.contains(topRatedProduct.getString("parentProductId"))) {
				parentProductIds.add(topRatedProduct.getString("parentProductId"));
				Map<String, Object> product = new HashMap(EntityQuery.use(delegator).from("Product").where("productId", topRatedProduct.getString("productId")).cache().queryOne());
				product.put("numberOfReviews", topRatedProduct.getLong("productReviewId"));
				product.put("ratings", Float.toString(Math.round(topRatedProduct.getBigDecimal("productRating").setScale(1, RoundingMode.HALF_UP).floatValue()/.5)/2F).replaceAll("\\.", "_"));
				GenericValue color = EntityQuery.use(delegator).from("ColorWarehouse").where("variantProductId", topRatedProduct.getString("productId")).cache().queryFirst();
				product.put("colorDescription", color != null ? color.getString("colorDescription") : "");
				products.add(product);
			}
			if(products.size() >= 10) {
				break;
			}
		}

		return products;
	}

	public static List<Map<String, Object>> getStaticTopRatedProducts(HttpServletRequest request, HttpServletResponse response) throws GenericEntityException {
		Delegator delegator = (Delegator)request.getAttribute("delegator");
		String webSiteId = WebSiteWorker.getWebSiteId(request);
		if(UtilValidate.isEmpty(webSiteId)) {
			webSiteId = "envelopes";
		}

		List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
		conditionList.add(EntityCondition.makeCondition("productId", EntityOperator.IN, TOP_RATED_PRODUCTS));
		conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "PRR_APPROVED"));
		conditionList.add(EntityCondition.makeCondition("salesChannelEnumId", EntityOperator.EQUALS, EnvUtil.getSalesChannelEnumId(webSiteId)));
		EntityCondition condition = EntityCondition.makeCondition(conditionList, EntityOperator.AND);
		List<Map<String, Object>> products = new ArrayList<>();


		List<GenericValue> topRatedProducts = EntityQuery.use(delegator).select("productId", "parentProductId", "statusId", "salesChannelEnumId", "productReviewId", "productRating").from("ProductAndReviewSummary").where(condition).orderBy("-productReviewId ").distinct().cache().queryList();
		for (GenericValue topRatedProduct : topRatedProducts) {
			Map<String, Object> product = new HashMap(EntityQuery.use(delegator).from("Product").where("productId", topRatedProduct.getString("productId")).cache().queryOne());
			product.put("numberOfReviews", topRatedProduct.getLong("productReviewId"));
			product.put("ratings", Float.toString(Math.round(topRatedProduct.getBigDecimal("productRating").setScale(1, RoundingMode.HALF_UP).floatValue()/.5)/2F).replaceAll("\\.", "_"));
			GenericValue color = EntityQuery.use(delegator).from("ColorWarehouse").where("variantProductId", topRatedProduct.getString("productId")).cache().queryFirst();
			product.put("colorDescription", color != null ? color.getString("colorDescription") : "");
			products.add(product);
			if(products.size() >= 6) {
				break;
			}
		}

		return products;
	}

	/*
	 * Get the product name of a given sku
	 */
	public static String getProductFeatures(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator)request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		String productId = (String) context.get("id");
		String featureTypeId = (String) context.get("featureTypeId");
		boolean success = false;
		GenericValue product = null;
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		Map<String, String> features = new HashMap<String, String>();
		try {
			product = EntityQuery.use(delegator).from("Product").where("productId", productId).cache().queryOne();
			if(UtilValidate.isNotEmpty(product)) {
				features = ProductHelper.getProductFeatures(delegator, product, (UtilValidate.isNotEmpty(featureTypeId) ? UtilMisc.toList(featureTypeId) : FEATURES_TO_SHOW), true);
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		}

		if(UtilValidate.isNotEmpty(product)) {
			success = true;
			jsonResponse.put("productId", product.getString("productId"));
			jsonResponse.put("features", features);
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get the all the colors for a virtual product
	 */
	public static String getProductColors(HttpServletRequest request, HttpServletResponse response) throws GenericServiceException {
		Delegator delegator = (Delegator)request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);

		String productId = (String) request.getAttribute("id");
		if(UtilValidate.isEmpty(productId)) {
			productId = (String) context.get("id");
		}

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		Map<String, Object> colorData = new HashMap<String, Object>();;

		try {
			GenericValue product = EntityQuery.use(delegator).from("Product").where("productId", productId).cache().queryOne();
			colorData = ProductHelper.getProductColors(delegator, product);
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		}

		if(UtilValidate.isNotEmpty(colorData)) {
			jsonResponse.put("success", true);
			jsonResponse.put("productId", productId);
			jsonResponse.put("colors", colorData.get("colors"));
			jsonResponse.put("filters", colorData.get("filters"));
		} else {
			jsonResponse.put("success", false);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get the matching products for the given
	 */
	public static String getMatchingProducts(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator)request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);

		String productId = (String) context.get("id");

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		List<Map<String, Object>> prodData = new ArrayList<Map<String, Object>>();
		List<GenericValue> matchingProducts = null;
		try {
			GenericValue product = EntityQuery.use(delegator).from("Product").where("productId", productId).cache().queryOne();
			if(product != null) {
				matchingProducts = ProductHelper.getMatchingProducts(delegator, product);
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		}

		if(UtilValidate.isNotEmpty(matchingProducts)) {
			prodData = ProductHelper.getCleanMatchingProductData(delegator, matchingProducts);
			jsonResponse.put("products", prodData);
			jsonResponse.put("productId", productId);
			jsonResponse.put("success", true);
		} else {
			jsonResponse.put("success", false);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get a list of recycled products
	 */
	public static String getRecycledProdCatList(HttpServletRequest request, HttpServletResponse response, List<String> recycledProductPrimaryParentCategoryIds, List<String> recycledProductProductCategoryIds) {
		Delegator delegator = (Delegator)request.getAttribute("delegator");

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		List<GenericValue> prodCatList = null;
		List<Map> prodCatData = new ArrayList<Map>();

		try {
			List<EntityCondition> recycledProductCondList = new ArrayList<EntityCondition>();

			recycledProductCondList.add(
				EntityCondition.makeCondition(
					EntityCondition.makeCondition("primaryParentCategoryId", EntityOperator.IN, recycledProductPrimaryParentCategoryIds), EntityOperator.OR,
					EntityCondition.makeCondition("productCategoryId", EntityOperator.IN, recycledProductProductCategoryIds)
				)
			);

			recycledProductCondList.add(EntityCondition.makeCondition("categoryName", EntityOperator.NOT_EQUAL, null));
			EntityCondition recycledProductCondition = EntityCondition.makeCondition(recycledProductCondList, EntityOperator.AND);
			prodCatList = EntityQuery.use(delegator).from("ProductCategory").where(recycledProductCondition).queryList();
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		}

		if(UtilValidate.isNotEmpty(prodCatList)) {
			jsonResponse.put("success", true);
			for(GenericValue prodCat : prodCatList) {
				Map<String, Object> prodCatInfo = new HashMap<String, Object>();
				prodCatInfo.put("productCategoryId", prodCat.getString("productCategoryId") == null ? "" : prodCat.getString("productCategoryId"));
				prodCatInfo.put("linkOneImageUrl", prodCat.getString("linkOneImageUrl") == null ? "" : prodCat.getString("linkOneImageUrl"));
				prodCatInfo.put("categoryName", prodCat.getString("categoryName"));
				prodCatData.add(prodCatInfo);
			}
			jsonResponse.put("categories", prodCatData);
		} else {
			jsonResponse.put("success", false);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String addUpdateVendor(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		boolean success = false;

		try {
			if(UtilValidate.isNotEmpty(context.get("partyId"))) {
				//check if party exists
				GenericValue party = EntityQuery.use(delegator).from("Party").where("partyId", (String) context.get("partyId")).queryOne();

				if(party == null) {
					PartyHelper.createParty(delegator, (String) context.get("partyId"));
				}
				GenericValue vendor = delegator.makeValue("Vendor");

				//check states
				Map<String, Object> data = new HashMap<>();
				LinkedList<String> states = new LinkedList<>();
				if(context.get("states") instanceof String) {
					states.add((String) context.get("states"));
				} else {
					states = (LinkedList) context.get("states");
				}
				data.put("states", states);
				context.put("rules", new Gson().toJson(data));

				vendor = EnvUtil.insertGenericValueData(delegator, vendor, context);

				delegator.createOrStore(vendor);
				success = true;
			}
		} catch (GenericEntityException e) {
			//
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String addVendorQuantity(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Timestamp nowTime = UtilDateTime.nowTimestamp();
		boolean success = false;

		try {
			if(UtilValidate.isNotEmpty(context.get("vendorProductId"))
					&& UtilValidate.isNotEmpty(context.get("partyId"))
					&& UtilValidate.isNotEmpty(context.get("quantity"))
					&& UtilValidate.isNotEmpty(context.get("price"))
					&& UtilValidate.isNotEmpty(context.get("colorsFront"))
					&& UtilValidate.isNotEmpty(context.get("isFrontBlack"))
					&& UtilValidate.isNotEmpty(context.get("colorsBack"))
					&& UtilValidate.isNotEmpty(context.get("isBackBlack"))) {

				context.put("fromDate", nowTime);
				GenericValue price = delegator.makeValue("VendorProductPrice");
				price = EnvUtil.insertGenericValueData(delegator, price, context);

				delegator.create(price);
				success = true;
			}
		} catch (GenericEntityException e) {
			//
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String addVendorProduct(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Timestamp nowTime = UtilDateTime.nowTimestamp();
		boolean success = false;

		try {
			if(UtilValidate.isNotEmpty(context.get("vendorPartyId"))
					&& UtilValidate.isNotEmpty(context.get("productId"))
					&& UtilValidate.isNotEmpty(context.get("vendorProductId"))) {

				context.put("fromDate", nowTime);
				GenericValue product = delegator.makeValue("VendorProduct");
				product = EnvUtil.insertGenericValueData(delegator, product, context);
				delegator.createOrStore(product);

				//create the entry to allow this product to be outsourceable
				GenericValue productOutsource = EntityQuery.use(delegator).from("ProductOutsource").where("productId", (String) context.get("productId")).cache().queryOne();
				String quantity = UtilValidate.isNotEmpty((String) context.get("quantity")) ? (String) context.get("quantity") : "500";

				if(productOutsource == null) {
					productOutsource = delegator.makeValue("ProductOutsource", UtilMisc.toMap("productId", (String) context.get("productId"), "quantity", Long.valueOf(quantity), "colors", Long.valueOf("1"), "outsourceable", "Y"));
					delegator.createOrStore(productOutsource);
				}

				success = true;
			}
		} catch (GenericEntityException e) {
			//
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String updateVendorProduct(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Timestamp nowTime = UtilDateTime.nowTimestamp();

		if(UtilValidate.isNotEmpty((String) context.get("id")) && UtilValidate.isNotEmpty((String) context.get("vendorProductId"))) {
			boolean beganTransaction = false;
			try {
				beganTransaction = TransactionUtil.begin();
				GenericValue vendorProduct = EntityQuery.use(delegator).from("VendorProduct").where("productId", (String) context.get("id"), "vendorPartyId", (String) context.get("partyId"), "productStoreGroupId", "_NA_").queryOne();
				vendorProduct.set("stockSupplied", UtilValidate.isEmpty(context.get("stockSupplied")) ? "N" : "Y");
				vendorProduct.store();

				//if new vendorProductId, update that before all other updates
				String vendorProductId = (String) context.get("vendorProductId");
				String newVendorProductId = (String) context.get("newVendorProductId");
				if (UtilValidate.isNotEmpty(vendorProductId) && UtilValidate.isNotEmpty(newVendorProductId) && !newVendorProductId.equalsIgnoreCase(vendorProductId)) {
					//update VendorProductPrice
					List<GenericValue> vNewPrices = new ArrayList<>();
					List<GenericValue> vPrices = EntityQuery.use(delegator).from("VendorProductPrice").where("partyId", (String) context.get("partyId"), "vendorProductId", vendorProductId).queryList();
					for (GenericValue vPrice : vPrices) {
						GenericValue temp = delegator.makeValidValue("VendorProductPrice", vPrice.getAllFields());
						temp.set("vendorProductId", newVendorProductId);
						vNewPrices.add(temp);
					}
					delegator.removeAll(vPrices);

					//update VendorProduct
					if (vendorProduct != null) {
						GenericValue newVendorProduct = delegator.makeValidValue("VendorProduct", vendorProduct.getAllFields());
						newVendorProduct.set("vendorProductId", newVendorProductId);

						vendorProduct.remove();
						newVendorProduct.create();
					}
					delegator.storeAll(vNewPrices);
					vendorProductId = newVendorProductId;
				}

				//update general product data
				GenericValue product = EntityQuery.use(delegator).from("Product").where("productId", (String) context.get("id")).queryOne();
				product.set("boxQty", (UtilValidate.isNotEmpty(context.get("boxQty")) && NumberUtils.isNumber((String) context.get("boxQty"))) ? Long.parseLong((String) context.get("boxQty")) : null);
				product.set("cartonQty", (UtilValidate.isNotEmpty(context.get("cartonQty")) && NumberUtils.isNumber((String) context.get("cartonQty"))) ? Long.parseLong((String) context.get("cartonQty")) : null);
				product.set("productWeight", (EnvUtil.validateContextValue((String) context.get("weight")) == null ? null : new BigDecimal((String) context.get("weight"))));
				product.set("shippingWeight", (EnvUtil.validateContextValue((String) context.get("weight")) == null ? null : new BigDecimal((String) context.get("weight"))));
				product.store();

				//update vendor product price data
				Iterator contextIter = context.entrySet().iterator();
				while (contextIter.hasNext()) {
					Map.Entry pairs = (Map.Entry) contextIter.next();

					String priceRow = (String) pairs.getKey();
					if (!priceRow.startsWith("pp_") && !priceRow.startsWith("ppd_") && !priceRow.startsWith("ppdel_")) {
						continue;
					}

					//update product price data
					if (priceRow.startsWith("pp_")) {
						priceRow = priceRow.replace("pp_", "");
						String[] priceData = priceRow.split("_");

						List<GenericValue> productPriceList = EntityQuery.use(delegator).from("VendorProductPrice").where("partyId", (String) context.get("partyId"), "vendorProductId", vendorProductId, "quantity", Long.valueOf(priceData[0]), "colorsFront", Long.valueOf(priceData[1]), "colorsBack", Long.valueOf(priceData[2]), "isFrontBlack", priceData[3], "isBackBlack", priceData[4]).queryList();
						for (GenericValue productPrice : productPriceList) {
							productPrice.set("price", new BigDecimal((String) pairs.getValue()));
							productPrice.store();
						}
					}

					if (priceRow.startsWith("ppd_")) {
						priceRow = priceRow.replace("ppd_", "");
						String[] priceData = priceRow.split("_");

						List<GenericValue> productPriceList = EntityQuery.use(delegator).from("VendorProductPrice").where("partyId", (String) context.get("partyId"), "vendorProductId", vendorProductId, "quantity", Long.valueOf(priceData[0]), "colorsFront", Long.valueOf(priceData[1]), "colorsBack", Long.valueOf(priceData[2]), "isFrontBlack", priceData[3], "isBackBlack", priceData[4]).queryList();
						for (GenericValue productPrice : productPriceList) {
							productPrice.set("thruDate", nowTime);
							productPrice.store();
						}
					}

					if (priceRow.startsWith("ppdel_")) {
						priceRow = priceRow.replace("ppdel_", "");
						String[] priceData = priceRow.split("_");

						List<GenericValue> productPriceList = EntityQuery.use(delegator).from("VendorProductPrice").where("partyId", (String) context.get("partyId"), "vendorProductId", vendorProductId, "quantity", Long.valueOf(priceData[0]), "colorsFront", Long.valueOf(priceData[1]), "colorsBack", Long.valueOf(priceData[2]), "isFrontBlack", priceData[3], "isBackBlack", priceData[4]).queryList();
						for (GenericValue productPrice : productPriceList) {
							delegator.removeValue(productPrice);
						}
					}
				}
			} catch (Exception e) {
				try {
					TransactionUtil.rollback(beganTransaction, "Error while trying to create product data.", e);
				} catch (GenericEntityException gee) {
					EnvUtil.reportError(gee);
				}

				EnvUtil.reportError(e);
			} finally {
				try {
					TransactionUtil.commit(beganTransaction);
				} catch (GenericEntityException gee) {
					Debug.logError("Error while trying to create product data", module);
				}
			}
		}

		return "success";
	}

	public static String addUpdateProductOutsource(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		boolean success = false;

		try {
			if(UtilValidate.isNotEmpty(context.get("productId"))) {
				GenericValue productOS = EntityQuery.use(delegator).from("ProductOutsource").where("productId", (String) context.get("productId")).queryOne();

				//check states
				context.remove("productId");
				context.put("rules", new Gson().toJson(context));
				productOS = EnvUtil.insertGenericValueData(delegator, productOS, context);
				delegator.createOrStore(productOS);
				success = true;
			}
		} catch (GenericEntityException e) {
			//
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Update a product and its related features/prices/etc
	 */
	public static String addProduct(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		boolean success = false;

		String productId = (String) context.get("productId");
		if(UtilValidate.isNotEmpty(productId)) {
			try {
				boolean beganTransaction = TransactionUtil.begin();
				try {
					//update general product data
					GenericValue product = EntityQuery.use(delegator).from("Product").where("productId", productId).queryOne();
					if(product == null) {
						product = delegator.makeValue("Product", UtilMisc.toMap("productId", productId));
						product.set("createdDate", UtilDateTime.nowTimestamp());
						product.set("createdByUserLogin", userLogin.getString("userLoginId"));
						delegator.create(product);
					}

					TransactionUtil.commit(beganTransaction);
					success = true;
				} catch (GenericEntityException e) {
					TransactionUtil.rollback(beganTransaction, "Error while trying to create product data.", e);
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to create product data. " + e + " : " + e.getMessage(), module);
				}
			} catch (GenericTransactionException e) {
				Debug.logError(e, "Error while trying to create product data.", module);
			}
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Update a product and its related features/prices/etc
	 */
	public static String updateProduct(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Timestamp nowTime = UtilDateTime.nowTimestamp();

		String productId = (String) context.get("id");
		if(UtilValidate.isNotEmpty(productId)) {
			try {
				boolean beganTransaction = TransactionUtil.begin();
				try {
					//update general product data
					GenericValue product = EntityQuery.use(delegator).from("Product").where("productId", productId).queryOne();
					product.set("lastModifiedByUserLogin", userLogin.getString("userLoginId"));
					product.set("productName", EnvUtil.validateContextValue((String) context.get("productName")));
					product.set("internalName", EnvUtil.validateContextValue((String) context.get("productName")));
					String longDescription = (String) context.get("longDescription");
					String colorDescription = (String) context.get("colorDescription");
					String metaDescription = (String) context.get("metaDescription");

					/*
					try {
						if(UtilValidate.isNotEmpty(longDescription)) {
							longDescription = new String(longDescription.getBytes("ISO-8859-1"), "UTF-8");
						}

						if(UtilValidate.isNotEmpty(colorDescription)) {
							colorDescription = new String(colorDescription.getBytes("ISO-8859-1"), "UTF-8");
						}

						if(UtilValidate.isNotEmpty(metaDescription)) {
							metaDescription = new String(metaDescription.getBytes("ISO-8859-1"), "UTF-8");
						}
					} catch (UnsupportedEncodingException e) {
						Debug.logError("An error occurred while encoding the longDescription, colorDescription and metaDescription for product with id " + productId, module);
					}
					*/

					product.set("comments", EnvUtil.validateContextValue((String) context.get("comments")));
					product.set("metaTitle", EnvUtil.validateContextValue((String) context.get("metaTitle")));
					product.set("metaDescription", EnvUtil.validateContextValue(metaDescription));
					product.set("longDescription", EnvUtil.validateContextValue(longDescription));
					product.set("colorDescription", EnvUtil.validateContextValue(colorDescription));
					product.set("tagLine", EnvUtil.validateContextValue((String) context.get("tagLine")));
					product.set("hasRushProduction", EnvUtil.validateContextValue((String) context.get("hasRushProduction")));
					product.set("isPrintable", EnvUtil.validateContextValue((String) context.get("isPrintable")));
					product.set("hasWhiteInk", (EnvUtil.validateContextValue((String) context.get("hasWhiteInk")) == null ? "N" : "Y"));
					product.set("hasSample", (EnvUtil.validateContextValue((String) context.get("hasSample")) == null ? "N" : "Y"));
					product.set("hasCut", (EnvUtil.validateContextValue((String) context.get("hasCut")) == null ? "N" : "Y"));
					product.set("hasFold", (EnvUtil.validateContextValue((String) context.get("hasFold")) == null ? "N" : "Y"));
					product.set("hasVariableData", (EnvUtil.validateContextValue((String) context.get("hasVariableData")) == null ? "N" : "Y"));
					product.set("hasCustomQty", (EnvUtil.validateContextValue((String) context.get("hasCustomQty")) == null ? "Y" : "N"));
					product.set("showOutOfStockRecommendations", (EnvUtil.validateContextValue((String) context.get("showOutOfStockRecommendations")) == null ? "N" : "Y"));
					product.set("binLocation", EnvUtil.validateContextValue((String) context.get("binLocation")));
					product.set("leadTimePlain", Long.parseLong((String) context.get("leadTimePlain")));
					product.set("leadTimeStandardPrinted", Long.parseLong((String) context.get("leadTimeStandardPrinted")));
					product.set("leadTimeRushPrinted", Long.parseLong((String) context.get("leadTimeRushPrinted")));
					product.set("boxQty", (UtilValidate.isNotEmpty(context.get("boxQty"))) ? Long.parseLong((String) context.get("boxQty")) : null);
					product.set("cartonQty", (UtilValidate.isNotEmpty(context.get("cartonQty"))) ? Long.parseLong((String) context.get("cartonQty")) : null);
					product.set("productTypeId", EnvUtil.validateContextValue((String) context.get("productTypeId")));
					product.set("productHeight", (EnvUtil.validateContextValue((String) context.get("productHeight")) == null ? null : new BigDecimal((String) context.get("productHeight"))));
					product.set("productWidth", (EnvUtil.validateContextValue((String) context.get("productWidth")) == null ? null : new BigDecimal((String) context.get("productWidth"))));
					product.set("heightUomId", "LEN_in");
					product.set("widthUomId", "LEN_in");
					product.set("productWeight", (EnvUtil.validateContextValue((String) context.get("weight")) == null ? null : new BigDecimal((String) context.get("weight"))));
					product.set("shippingWeight", (EnvUtil.validateContextValue((String) context.get("weight")) == null ? null : new BigDecimal((String) context.get("weight"))));
					product.set("parentProductId", EnvUtil.validateContextValue((String) context.get("parentProductId")));
					product.set("primaryProductCategoryId", EnvUtil.validateContextValue((String) context.get("primaryProductCategoryId")));
					product.set("isVirtual", "N");
					product.set("isVariant", "Y");

					if(("Y").equalsIgnoreCase((String) context.get("discontinued"))) {
						product.set("salesDiscontinuationDate", nowTime);
					} else {
						product.set("salesDiscontinuationDate", null);
						product.set("createdDate", nowTime);
					}

					//set sale/clearance data
					//ONLY FOR PLAIN PRODUCT PRICING ONLY!!!!!!!!!!!!!!!!!!!!!!!!
					boolean updatePrice = true;
					if(UtilValidate.isNotEmpty(context.get("percentSavings"))) {
						if("NONE".equalsIgnoreCase((String) context.get("percentSavings"))) {
							if(UtilValidate.isNotEmpty(product.getString("onSale")) || UtilValidate.isNotEmpty(product.getString("onClearance"))) {
								product.set("onSale", null);
								product.set("onClearance", null);

								List<GenericValue> productPriceList = EntityQuery.use(delegator).from("ProductPrice").where("productId", productId, "colors", Long.valueOf("0")).queryList();
								for(GenericValue productPrice : productPriceList) {
									if(UtilValidate.isNotEmpty(productPrice.getBigDecimal("originalPrice"))) {
										productPrice.set("price", productPrice.getBigDecimal("originalPrice"));
										productPrice.set("originalPrice", null);
										productPrice.store();
									}
								}

								updatePrice = false;
							}
						} else {
							BigDecimal percentOff = new BigDecimal("." + (String) context.get("percentSavings"));
							if(percentOff.compareTo(new BigDecimal("0.50")) >= 0) {
								product.set("onClearance", "Y");
								product.set("onSale", null);
							} else if(percentOff.compareTo(new BigDecimal("0.50")) < 0) {
								product.set("onSale", "Y");
								product.set("onClearance", null);
							}

							percentOff = BigDecimal.ONE.subtract(percentOff);

							List<GenericValue> productPriceList = EntityQuery.use(delegator).from("ProductPrice").where("productId", productId, "colors", Long.valueOf("0")).queryList();
							for(GenericValue productPrice : productPriceList) {
								if(UtilValidate.isNotEmpty(productPrice.getBigDecimal("originalPrice"))) {
									productPrice.set("price", productPrice.getBigDecimal("originalPrice").multiply(percentOff).setScale(2, EnvConstantsUtil.ENV_ROUNDING));
									productPrice.store();
								} else {
									productPrice.set("originalPrice", productPrice.getBigDecimal("price"));
									productPrice.set("price", productPrice.getBigDecimal("price").multiply(percentOff).setScale(2, EnvConstantsUtil.ENV_ROUNDING));
									productPrice.store();
								}
							}

							updatePrice = false;
						}
					}

					product.store();

					List<String> discItems = new ArrayList<>();

					Iterator contextIter = context.entrySet().iterator();

					BigDecimal cost = null;
					BigDecimal manufacturingCost = null;

					while(contextIter.hasNext()) {
						Map.Entry pairs = (Map.Entry) contextIter.next();

						String featureTypeId = (String) pairs.getKey();
						if(!featureTypeId.startsWith("pf_")
								&& !featureTypeId.startsWith("pp_")
								&& !featureTypeId.startsWith("sdd_")
								&& !featureTypeId.startsWith("pc_")
								&& !featureTypeId.startsWith("ppc_")
								&& !featureTypeId.startsWith("pmc_")
								&& !featureTypeId.startsWith("upc_")
						) {
							continue;
						}

						//error handling for multiple prices in log output
						if(featureTypeId.startsWith("pp_") &&  pairs.getValue() instanceof LinkedList) {
							Debug.logError("Error, multiple prices detected: " + featureTypeId + ":" + pairs.getValue(), module);
						}
						String featureId = (String) pairs.getValue();

						//update product feature data
						if(featureTypeId.startsWith("pf_")) {
							ProductHelper.deleteFeatureAppl(delegator, product, featureTypeId.replace("pf_",""));
							if(UtilValidate.isNotEmpty(featureId)) {
								ProductHelper.createFeatureAppl(delegator, product, featureId);
							}
						}

						//update product price data
						if(updatePrice && featureTypeId.startsWith("pp_")) {
							String qty = featureTypeId.substring(featureTypeId.indexOf("_")+1, featureTypeId.lastIndexOf("_"));
							String colors = featureTypeId.substring(featureTypeId.lastIndexOf("_")+1);
							List<GenericValue> productPriceList = EntityQuery.use(delegator).from("ProductPrice").where("productId", productId, "colors", Long.valueOf(colors), "quantity", Long.valueOf(qty)).queryList();
							for(GenericValue productPrice : productPriceList) {
								productPrice.set("price", new BigDecimal((String) pairs.getValue()));
								productPrice.store();
							}
						}

						if(featureTypeId.startsWith("pc_") && UtilValidate.isNotEmpty(pairs.getValue())) {
							cost = new BigDecimal((String) pairs.getValue());
						}
						if(featureTypeId.startsWith("ppc_") && UtilValidate.isNotEmpty(pairs.getValue())) {
							String qty = featureTypeId.substring(featureTypeId.indexOf("_")+1, featureTypeId.lastIndexOf("_"));
							String colors = featureTypeId.substring(featureTypeId.lastIndexOf("_")+1);
							List<GenericValue> productPriceList = EntityQuery.use(delegator).from("ProductPrice").where("productId", productId, "colors", Long.valueOf(colors), "quantity", Long.valueOf(qty)).queryList();
							for(GenericValue productPrice : productPriceList) {
								productPrice.set("printCost", new BigDecimal((String) pairs.getValue()));
								productPrice.store();
							}
						}
						if(featureTypeId.startsWith("pmc_") && UtilValidate.isNotEmpty(pairs.getValue())) {
							manufacturingCost = new BigDecimal((String) pairs.getValue());
						}

						if(featureTypeId.startsWith("upc_") && UtilValidate.isNotEmpty(pairs.getValue())) {
							String qty = featureTypeId.substring(featureTypeId.indexOf("_")+1, featureTypeId.lastIndexOf("_"));
							List<GenericValue> productPriceList = EntityQuery.use(delegator).from("ProductPrice").where("productId", productId, "quantity", Long.valueOf(qty)).queryList();
							for(GenericValue productPrice : productPriceList) {
								productPrice.set("upc", (String) pairs.getValue());
								productPrice.store();
							}
						}

						if(featureTypeId.startsWith("sdd_")) {
							String qty = featureTypeId.substring(featureTypeId.indexOf("_")+1, featureTypeId.lastIndexOf("_"));
							String colors = featureTypeId.substring(featureTypeId.lastIndexOf("_")+1);
							List<GenericValue> productPriceList = EntityQuery.use(delegator).from("ProductPrice").where("productId", productId, "colors", Long.valueOf(colors), "quantity", Long.valueOf(qty)).queryList();
							for(GenericValue productPrice : productPriceList) {
								productPrice.set("thruDate", UtilDateTime.nowTimestamp());
								productPrice.store();
								discItems.add(qty + "_" + colors);
							}
						}
					}

					//finally update cost/print/mfg cost for an item
					if(UtilValidate.isNotEmpty(cost) || UtilValidate.isNotEmpty(manufacturingCost)) {
						List<GenericValue> productPriceList = EntityQuery.use(delegator).from("ProductPrice").where("productId", productId).queryList();
						for(GenericValue productPrice : productPriceList) {
							productPrice.set("cost", cost);
							productPrice.set("manufacturingCost", manufacturingCost);
							productPrice.store();
						}
					}

					//update non discontinued items
					boolean hasDefaultPricing = false;
					List<GenericValue> productPriceList = EntityQuery.use(delegator).from("ProductPrice").where("productId", productId).queryList();
					if(UtilValidate.isNotEmpty(productPriceList)) {
						hasDefaultPricing = true;
					}

					Iterator ppIter = productPriceList.iterator();
					while(ppIter.hasNext()) {
						GenericValue pp = (GenericValue) ppIter.next();
						if(discItems.contains(pp.getLong("quantity").toString() + "_" + pp.getLong("colors").toString())) {
							ppIter.remove();
						}
					}

					if(hasDefaultPricing) {
						boolean hasPrinted = false;
						for (GenericValue productPrice : productPriceList) {
							if (!hasPrinted && productPrice.getLong("colors") > 0) {
								hasPrinted = true;
							}

							productPrice.set("thruDate", null);
							productPrice.store();
						}
						product.set("isPrintable", hasPrinted ? "Y" : "N");
						product.store();
					}

                    //Remove Assets
					String[] removedAssetList = ((String) context.get("removedAssets")).split(",");
					for(String removedAsset : removedAssetList) {
						if (UtilValidate.isNotEmpty(removedAsset)) {
							ProductHelper.removeProductAsset(delegator, removedAsset);
						}
					}

                    //Add Assets
                    for(long i = 0; i < Long.parseLong((String) context.get("totalAssetCount")); i++) {
                        ProductHelper.createProductAsset(delegator, productId, (String) context.get("asset" + i + "-designId"), (String) context.get("asset" + i + "-id"), (String) context.get("asset" + i + "-type"), (String) context.get("asset" + i + "-name"), (String) context.get("asset" + i + "-thumbnail"), (String) context.get("asset" + i + "-description"), i, (UtilValidate.isNotEmpty(context.get("asset" + i + "-default")) ? "Y" : "N"));
                    }

					GenericValue productWebsite = delegator.makeValue("ProductWebSite", UtilMisc.toMap("productId", productId, "ae", (UtilValidate.isNotEmpty(context.get("envelopesWebsite")) ? "Y" : "N"), "envelopes", (UtilValidate.isNotEmpty(context.get("envelopesWebsite")) ? "Y" : "N"), "folders", (UtilValidate.isNotEmpty(context.get("foldersWebsite")) ? "Y" : "N")));
					delegator.createOrStore(productWebsite);

					TransactionUtil.commit(beganTransaction);
				} catch (GenericEntityException e) {
					TransactionUtil.rollback(beganTransaction, "Error while trying to update product data.", e);
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to update product data. " + e + " : " + e.getMessage(), module);
				}
			} catch (GenericTransactionException e) {
				Debug.logError(e, "Error while trying to update product data.", module);
			}
		}

		return "success";
	}

	/*
		ADD REVIEW
	*/
	public static String addReview(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		boolean success = false;
		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(UtilDateTime.nowTimestamp());
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		// check if user login exists, if it does then use it, otherwise use anonymous.
		try {
			context.put("postedAnonymous", (PartyHelper.isEmailActive(delegator, (String) context.get("userLoginId")) ? "N" : "Y"));
			context.put("userLoginId", (PartyHelper.isEmailActive(delegator, (String) context.get("userLoginId")) ? context.get("userLoginId") : "anonymous"));
			context.put("statusId", "PRR_PENDING");
			context.put("postedDateTime", timestamp);
			context.put("productStoreId", "10000");
			if (ProductHelper.addReview(delegator, dispatcher, context) == "success") {
				success = true;
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
		}
		jsonResponse.put("success" , success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
		Get Product List by Size
	*/
	public static String getProductSizeList(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		boolean success = false;
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		try {
			jsonResponse.put("data", ProductHelper.getProductSizeList(delegator, context));
			success = true;
		}
		catch(GenericEntityException e) {
			Debug.logError("Error while trying to get custom envelope info. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get inventory
	 */
	public static String getInventory(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		boolean success = false;
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		try {
			jsonResponse.put("hasInventory", ProductHelper.hasInventory(delegator, (String) context.get("id"), Long.valueOf("1"), false));
			success = true;
		}catch(GenericEntityException e) {
			Debug.logError("Error while trying to get custom envelope info. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Create new product Price
	 */
	public static String createProductPrice(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;

		if(UtilValidate.isEmpty(context.get("productId")) || UtilValidate.isEmpty(context.get("quantity")) || UtilValidate.isEmpty(context.get("colors")) || UtilValidate.isEmpty(context.get("price"))) {
			return null;
		}

		try {
			ProductHelper.createProductPrice(delegator, (String) context.get("productId"), Long.valueOf((String) context.get("quantity")),  Long.valueOf((String) context.get("colors")),  new BigDecimal((String) context.get("price")));
			success = true;
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to add product price. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get Product Assets
	 */
	public static String getProductAssets(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		String productId = (String) context.get("productId");

		boolean success = false;

		try {
			jsonResponse.put("productAssets", ProductHelper.getProductAssets(delegator, productId));
			success = true;
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to obtain all product assets for product " + productId + ". " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String saveDesignPreview(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();
		String designId = context.get("designId") + "_" + context.get("side");
		boolean success = false;
		try {
			success = ProductHelper.saveDesignPreview((String) context.get("base64"), designId);
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while saving the design image " + designId + ". " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	public static String saveDesignThumbnail(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();
		String fileName = (String) context.get("fileName");
		boolean success = false;
		try {
			success = ProductHelper.saveDesignThumbnail((String) context.get("base64"), fileName);
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while saving the design image thumbnail " + fileName + ". " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}
	*/

	/*public static String saveArtworkFile(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();
		String fileName = context.get("designId") + "_" + context.get("side");
		boolean success = false;
		try {
			jsonResponse = FileHelper.saveFileFromStream(request, "test12345.pdf", null);
			success = true;
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while saving the pdf file " + fileName + ". " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}*/

	public static String getBase64ImageData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;
		try {
			jsonResponse.put("base64", FileHelper.convertToBase64(request));
			if (UtilValidate.isNotEmpty(context.get("index"))) {
				jsonResponse.put("index", context.get("index"));
			}
			
			success = true;
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while getting base64 encoded image data for : " + request.getParameter("url")+ ". " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);


	}



	public static String getVendorProductId(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		String productId = (String) context.get("productId");
		String vendorPartyId = (String) context.get("vendorPartyId");
		String productFeatureId = (String) context.get("productFeatureId");
		String productFeatureTypeId = (String) context.get("productFeatureTypeId");
		String description = (String) context.get("description");

		boolean success = false;

		try {
			jsonResponse.put("vendorProductId", ProductHelper.getVendorProductId(delegator, productId, vendorPartyId, productFeatureId, productFeatureTypeId, description));
			success = true;
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to obtain all product sku for feature " + productFeatureId + ". " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String removeProductPrice(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();

		boolean success = false;

		try {
			ProductHelper.removeProductPrice(delegator, (String) context.get("productId"), Long.parseLong((String) context.get("colors")), Long.parseLong((String) context.get("quantity")), new BigDecimal((String) context.get("price")));
			success = true;
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error trying to remove product price for product" + context.get("productId") + ". " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String linkDesignsAndProducts(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> jsonResponse = new HashMap<>();

		boolean success = false;

		try {
			ProductHelper.linkDesignsAndProducts(delegator);
			success = true;
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error trying to link legacy designs and products to the new design. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String getProductDesigns(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();

		boolean success = false;

		try {
			jsonResponse.put("data", ProductHelper.getProductDesigns(delegator, (String) context.get("productId"), (String) context.get("parentProductId")));
			success = true;
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error trying to obtain product designs with productId and parentProductId. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String generateProductExport(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> jsonResponse = new HashMap<>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		boolean success = false;

		try {
			success = ProductHelper.generateProductExport((UtilValidate.isNotEmpty(context.get("args")) ? (String) context.get("args") : ""));
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error trying to obtain product export. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String addProductCategory(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();

		boolean success = false;

		try {
			if (UtilValidate.isNotEmpty(context.get("productCategoryId")) && UtilValidate.isNotEmpty(context.get("primaryParentCategoryId")) &&
				UtilValidate.isNotEmpty(context.get("categoryName")) && UtilValidate.isNotEmpty(context.get("description")) && UtilValidate.isNotEmpty(context.get("longDescription"))) {
				ProductHelper.addProductCategory(delegator, (String) context.get("productCategoryId"), "CATALOG_CATEGORY", (String) context.get("primaryParentCategoryId"), (String) context.get("categoryName"), (String) context.get("description"), (String) context.get("longDescription"));
				success = true;
			} else {
				jsonResponse.put("error", "Not all required fields have been entered.");
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error trying to add a new product category. " + e + " : " + e.getMessage(), module);
			jsonResponse.put("error", "Error trying to add a new product category. " + e + " : " + e.getMessage());
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}
}