/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.order.shoppingcart.ShoppingCart;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import com.envelopes.util.*;


public class ProductServices {
	public static final String module = ProductServices.class.getName();

	/**
	 * This service will return a single price of a product including all adjustments if passed, this method is used to overload the existing calculateProductPrice method built into OfBiz
	 */
	public static Map<String, Object> calculateProductPrice(DispatchContext dctx, Map<String, ? extends Object> context) {
		LocalDispatcher dispatcher = dctx.getDispatcher();
		Delegator delegator = dctx.getDelegator();

		Map<String, Object> result = ServiceUtil.returnSuccess();

		ShoppingCart cart = (ShoppingCart) context.get("cart");
		GenericValue product = (GenericValue) context.get("product");
		BigDecimal quantity = (UtilValidate.isNotEmpty(context.get("quantity"))) ? (BigDecimal) context.get("quantity") : BigDecimal.ZERO;
		String partyId = (String) context.get("partyId");
		Integer colorsFront = (UtilValidate.isNotEmpty(context.get("colorsFront"))) ? (Integer) context.get("colorsFront") : Integer.valueOf(0);
		Integer colorsBack = (UtilValidate.isNotEmpty(context.get("colorsBack"))) ? (Integer) context.get("colorsBack") : Integer.valueOf(0);
		Boolean isRush = (UtilValidate.isNotEmpty(context.get("isRush"))) ? (Boolean) context.get("isRush") : false;
		Boolean whiteInkFront = (UtilValidate.isNotEmpty(context.get("whiteInkFront"))) ? (Boolean) context.get("whiteInkFront") : false;
		Boolean whiteInkBack = (UtilValidate.isNotEmpty(context.get("whiteInkBack"))) ? (Boolean) context.get("whiteInkBack") : false;
		Integer cuts = (UtilValidate.isNotEmpty(context.get("cuts"))) ? (Integer) context.get("cuts") : Integer.valueOf("0");
		Boolean isFolded = (UtilValidate.isNotEmpty(context.get("isFolded"))) ? (Boolean) context.get("isFolded") : false;
		Boolean isFullBleed = (UtilValidate.isNotEmpty(context.get("isFullBleed"))) ? (Boolean) context.get("isFullBleed") : false;
		Integer addresses = (UtilValidate.isNotEmpty(context.get("addresses"))) ? (Integer) context.get("addresses") : Integer.valueOf(0);
		BigDecimal customPrice = (UtilValidate.isNotEmpty(context.get("price"))) ? (BigDecimal) context.get("price") : BigDecimal.ZERO;
		String templateId = (String) context.get("templateId");

		Map<Integer, Map> priceMap = null;

		try {
			priceMap = ProductHelper.getProductPrice(cart, delegator, dispatcher, product, quantity.intValue(), partyId, colorsFront.intValue(), colorsBack.intValue(), isRush.booleanValue(), whiteInkFront.booleanValue(), whiteInkBack.booleanValue(), cuts.intValue(), isFolded.booleanValue(), isFullBleed.booleanValue(), addresses.intValue(), customPrice, templateId);
		} catch(GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to look up price for product: " + product, module);
			return ServiceUtil.returnError(e.getMessage());
		} catch(GenericServiceException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to look up price for product: " + product, module);
			return ServiceUtil.returnError(e.getMessage());
		}

		if(UtilValidate.isNotEmpty(priceMap) && priceMap.containsKey(Integer.valueOf(quantity.intValue()))) {
			BigDecimal perUnitPrice = ((BigDecimal) ((Map) priceMap.get(Integer.valueOf(quantity.intValue()))).get("price")).divide(quantity, EnvConstantsUtil.ENV_SCALE_L, EnvConstantsUtil.ENV_ROUNDING);
			result.put("basePrice", perUnitPrice); //this is the per single item value
			result.put("price", perUnitPrice); //this is the per single item value
			result.put("defaultPrice", perUnitPrice); //this is the per single item value
			result.put("validPriceFound", Boolean.valueOf("true"));
			result.put("envPriceResultAttributes", (Map) getEnvItemAttributes((Map) priceMap.get(Integer.valueOf(quantity.intValue()))));
		} else {
			result.put("basePrice", BigDecimal.ZERO);
			result.put("price", BigDecimal.ZERO);
			result.put("defaultPrice", BigDecimal.ZERO);
			result.put("validPriceFound", Boolean.valueOf("true"));
			result.put("envPriceResultAttributes", null);
		}

		result.put("isSale", Boolean.valueOf("false"));
		result.put("currencyUsed", "USD");
		result.put("orderItemPriceInfos", new ArrayList());

		return result;
	}


	/*
	 * Get attribute map from add to cart event for an item
	 */
	public static Map<String, Object> getEnvItemAttributes(Map<String, BigDecimal> adjustments) {
		Map<String, Object> envItemAttributes = new HashMap<String, Object>();
		for(Map.Entry<String, BigDecimal> adjustment : adjustments.entrySet()) {
			envItemAttributes.put(adjustment.getKey(), adjustment.getValue());
		}

		return envItemAttributes;
	}
}