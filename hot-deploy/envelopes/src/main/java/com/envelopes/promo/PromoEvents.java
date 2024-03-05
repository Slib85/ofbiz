/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.promo;

import java.io.*;
import java.net.*;
import java.lang.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.text.SimpleDateFormat;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.GeneralException;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilHttp;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntity;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityConditionList;
import org.apache.ofbiz.entity.condition.EntityExpr;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.transaction.GenericTransactionException;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.entity.util.EntityFindOptions;
import org.apache.ofbiz.entity.util.EntityListIterator;
import org.apache.ofbiz.entity.util.EntityTypeUtil;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.order.shoppingcart.ShoppingCart;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartEvents;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartItem;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.envelopes.cart.CartHelper;
import com.envelopes.product.ProductHelper;
import com.envelopes.util.*;

public class PromoEvents {
	public static final String module = PromoEvents.class.getName();

	/*
	 * Create or Update Promo
	 */
	public static String createOrUpdateProductPromo(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		Map promoObj = (new Gson()).fromJson((String) context.get("data"), HashMap.class);

		boolean isSuccess = false;
		boolean transaction = false;

		try {
			transaction = TransactionUtil.begin();

			//create ProductPromo
			Map productPromoData = (Map) promoObj.remove("productPromo");
			productPromoData.put("createdByUserLogin", ((GenericValue) request.getSession().getAttribute("userLogin")).getString("userLoginId"));
			EnvUtil.convertMapValuesToString(productPromoData);
			GenericValue productPromo = PromoHelper.createOrUpdateProductPromo(delegator, productPromoData);

			List<Map> rules = (List<Map>) promoObj.remove("productPromoRule");
			for(Map rule : rules) {
				List<Map> conds = (List<Map>) rule.remove("conds");
				List<Map> actions = (List<Map>) rule.remove("actions");

				rule.put("productPromoId", productPromo.getString("productPromoId"));
				EnvUtil.convertMapValuesToString(rule);
				GenericValue productPromoRule = PromoHelper.createOrUpdateProductPromoRule(delegator, rule);

				for(Map cond : conds) {
					cond.put("productPromoId", productPromo.getString("productPromoId"));
					cond.put("productPromoRuleId", productPromoRule.getString("productPromoRuleId"));
					EnvUtil.convertMapValuesToString(cond);
					GenericValue productPromoCond = PromoHelper.createOrUpdateProductPromoCond(delegator, cond);
				}

				for(Map action : actions) {
					action.put("productPromoId", productPromo.getString("productPromoId"));
					action.put("productPromoRuleId", productPromoRule.getString("productPromoRuleId"));
					EnvUtil.convertMapValuesToString(action);
					GenericValue productPromoAction = PromoHelper.createOrUpdateProductPromoAction(delegator, action);
				}
			}

			Map productStorePromoApplData = (Map) promoObj.remove("productStorePromoAppl");
			productStorePromoApplData.put("productPromoId", productPromo.getString("productPromoId"));
			EnvUtil.convertMapValuesToString(productStorePromoApplData);
			GenericValue productStorePromoAppl = PromoHelper.createOrUpdateProductStorePromoAppl(delegator, productStorePromoApplData);

			List<Map> productPromoCodeData = (List<Map>) promoObj.remove("productPromoCodes");
			for(Map productPromoCode : productPromoCodeData) {
				productPromoCode.put("productPromoId", productPromo.getString("productPromoId"));
				EnvUtil.convertMapValuesToString(productPromoCode);
				PromoHelper.createProductPromoCode(delegator, productPromoCode);
			}

			isSuccess = true;
		} catch(GenericEntityException e) {
			isSuccess = false;
			try {
				TransactionUtil.rollback(transaction, "Error creating promo.", e);
			} catch (GenericTransactionException gte) {
				isSuccess = false;
			}
		} finally {
			if (!isSuccess) {
				try {
					TransactionUtil.rollback(transaction, "Error creating promo.", null);
				} catch (GenericTransactionException gte) {
					Debug.logError(gte, "Unable to rollback transaction", module);
				}
			} else {
				try {
					TransactionUtil.commit(transaction);
				} catch (GenericTransactionException gte) {
					Debug.logError(gte, "Unable to commit transaction", module);
				}
			}
		}

		jsonResponse.put("success", isSuccess);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}


	/*
	 * Get a list of all promos in cart
	 */
	public static String appliedPromos(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		jsonResponse.put("promos", cart.getProductPromoCodesEntered());
		jsonResponse.put("success", true);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Apply a promo given a promo code id
	 */
	public static String applyPromo(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);

		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		try {
			PromoHelper.validateCouponUsage(delegator, dispatcher, request.getSession(), cart, context, jsonResponse);
			//if the error result is empty, then promo was success, recalc tax
			if(UtilValidate.isEmpty((String) jsonResponse.get("error"))) {
				CartHelper.setTaxTotal(dispatcher, null, cart);
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to calculate tax. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", true);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Remove all promos
	 */
	public static String removeEnteredPromos(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		if(UtilValidate.isEmpty(context.get("productPromoCodeId"))) {
			PromoHelper.removeEnteredPromos(cart, dispatcher);
		} else {
			PromoHelper.removeEnteredPromos(cart, dispatcher, (String) context.get("productPromoCodeId"));
		}

		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		jsonResponse.put("success", true);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Apply a promo given a promo code id
	 */
	public static String removeAllPromos(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		cart.clearAllAdjustments();

		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		jsonResponse.put("success", true);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}
}