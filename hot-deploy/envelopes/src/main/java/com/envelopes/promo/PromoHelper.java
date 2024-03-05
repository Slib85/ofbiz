/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.promo;

import java.lang.*;
import java.math.BigDecimal;
import java.util.*;

import com.envelopes.cart.CartHelper;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.order.shoppingcart.ShoppingCart;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;

import com.envelopes.util.*;

public class PromoHelper {
	public static final String module = PromoHelper.class.getName();

	/*
	 * Manage product promotion applications, we do not stack promos unless its certain types
	 */
	public static String checkAndApplyPromo(LocalDispatcher dispatcher, ShoppingCart cart, String productPromoCodeId) {
		if(cart == null) {
			return null;
		}

		return cart.addProductPromoCode(productPromoCodeId, dispatcher);
	}

	/*
	 * Discontinue a product promo code
	 */
	public static void discProductPromoCode(Delegator delegator, String productPromoCodeId) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(productPromoCodeId)) {
			GenericValue productPromoCode = delegator.findOne("ProductPromoCode", UtilMisc.toMap("productPromoCodeId", productPromoCodeId), false);
			if(productPromoCode != null) {
				productPromoCode.set("thruDate", UtilDateTime.nowTimestamp());
				productPromoCode.store();

				//check if there any other product promo codes still active, if not lets disc the whole promo, except if its a sample
				if(!isSampleCoupon(delegator, productPromoCodeId)) {
					if(anyActiveProductPromoCodeLeft(delegator, productPromoCode.getString("productPromoId"))) {
						discProductPromo(delegator, productPromoCode.getString("productPromoId"), null);
					}
				}
			}
		}
	}

	/*
	 * Discontinue a productPromo
	 */
	public static void discProductPromo(Delegator delegator, String productPromoId, String productStoreId) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(productPromoId)) {
			List<GenericValue> productStorePromoAppls = (UtilValidate.isEmpty(productStoreId)) ? delegator.findByAnd("ProductStorePromoAppl", UtilMisc.toMap("productPromoId", productPromoId), null, false) : delegator.findByAnd("ProductStorePromoAppl", UtilMisc.toMap("productPromoId", productPromoId, "productStoreId", productStoreId), null, false);
			if(UtilValidate.isNotEmpty(productStorePromoAppls)) {
				for(GenericValue productStorePromoAppl : productStorePromoAppls) {
					productStorePromoAppl.set("thruDate", UtilDateTime.nowTimestamp());
					productStorePromoAppl.store();
				}
			}
		}
	}

	/*
	 * Check if all codes are disc, if so, return true
	 */
	public static boolean anyActiveProductPromoCodeLeft(Delegator delegator, String productPromoId) throws GenericEntityException {
		if(UtilValidate.isEmpty(productPromoId)) {
			return true;
		}

		List<EntityCondition> conditionList1 = new ArrayList<EntityCondition>();
		conditionList1.add(EntityCondition.makeCondition("thruDate",  EntityOperator.EQUALS, null));
		conditionList1.add(EntityCondition.makeCondition("thruDate",  EntityOperator.LESS_THAN_EQUAL_TO, UtilDateTime.nowTimestamp()));

		List<EntityCondition> conditionList2 = new ArrayList<EntityCondition>();
		conditionList2.add(EntityCondition.makeCondition("productPromoId",  EntityOperator.EQUALS, productPromoId));
		conditionList2.add(EntityCondition.makeCondition(conditionList1, EntityOperator.OR));

		EntityCondition condition = EntityCondition.makeCondition(conditionList2, EntityOperator.AND);

		List<GenericValue> activePromos = delegator.findList("ProductPromoCode", condition, null, null, null, false);
		if(UtilValidate.isEmpty(activePromos)) {
			return false;
		}

		return true;
	}

	/*
	 * Check if code is a sample
	 */
	public static boolean isSampleCoupon(Delegator delegator, String productPromoCodeId) throws GenericEntityException {
		if(UtilValidate.isEmpty(productPromoCodeId)) {
			return false;
		}

		GenericValue productPromoCode = delegator.findOne("ProductPromoCode", UtilMisc.toMap("productPromoCodeId", productPromoCodeId), false);
		if(productPromoCode != null) {
			GenericValue productPromo = productPromoCode.getRelatedOne("ProductPromo", false);
			if(UtilValidate.isNotEmpty(productPromo.get("isSample")) && productPromo.getString("isSample").equalsIgnoreCase("Y")) {
				return true;
			}
		}

		return false;
	}

	/*
	 * Generate a random coupon code for sample orders
	 */
	public static String generateRandomPromoCode(Delegator delegator) throws GenericEntityException {
		GenericValue productPromoCode = null;
		String promoCode = null;

		do {
			promoCode = RandomStringUtils.randomAlphanumeric(EnvConstantsUtil.PROMO_CODE_LENGTH).toUpperCase();
			productPromoCode = delegator.findOne("ProductPromoCode", UtilMisc.toMap("productPromoCodeId", promoCode), false);
		} while (productPromoCode != null);

		return promoCode;
	}

	/*
	 * Associate a sample coupon to appropriate sample promo, if none is found, apply to $1 sample
	 */
	public static void createSampleCoupon(Delegator delegator, String productPromoCodeId, int sampleAmount) throws GenericEntityException {
		GenericValue productPromoCode = delegator.makeValue("ProductPromoCode", UtilMisc.toMap("productPromoCodeId", productPromoCodeId));
		if(EnvConstantsUtil.SAMPLE_COUPONS.containsKey(Integer.valueOf(sampleAmount))) {
			productPromoCode.put("productPromoId", EnvConstantsUtil.SAMPLE_COUPONS.get(Integer.valueOf(sampleAmount)));
		} else {
			productPromoCode.put("productPromoId", EnvConstantsUtil.SAMPLE_COUPONS.get(Integer.valueOf(1)));
		}
		productPromoCode.put("fromDate", UtilDateTime.nowTimestamp());
		productPromoCode.put("thruDate", EnvUtil.getNDaysBeforeOrAfterNow(EnvConstantsUtil.SAMPLE_EXPIRE_DAYS, false));

		delegator.create(productPromoCode);
	}

	/**
	 * Check if cart already has a promo
	 */
	public static boolean doesCartHavePromo(ShoppingCart cart) {
		if(!(cart.getProductPromoCodesEntered()).isEmpty()) {
			return true;
		}

		return false;
	}

	/**
	 * Remove any entered promos
	 */
	public static void removeEnteredPromos(ShoppingCart cart, LocalDispatcher dispatcher) {
		cart.removeProductPromoCode(null, dispatcher);
	}

	/**
	 * Remove given promo code
	 */
	public static void removeEnteredPromos(ShoppingCart cart, LocalDispatcher dispatcher, String productPromoCodeId) {
		cart.removeProductPromoCode(productPromoCodeId, dispatcher);
	}

	/**
	 * See if a coupon can be used
	 */
	public static void validateCouponUsage(Delegator delegator, LocalDispatcher dispatcher, HttpSession session, ShoppingCart cart, Map<String, Object> context, Map<String, Object> jsonResponse) throws GenericEntityException, GenericServiceException {
		String result = checkAndApplyPromo(dispatcher, cart, (String) context.get("productPromoCodeId"));
		jsonResponse.put("error", result);
	}

	/*
	 * Create Product Promo
	 */
	public static GenericValue createOrUpdateProductPromo(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
		//check if it exists already
		GenericValue productPromo = (UtilValidate.isNotEmpty(context.get("productPromoId"))) ? delegator.findOne("ProductPromo", UtilMisc.toMap("productPromoId", (String) context.get("productPromoId")), false) : delegator.makeValue("ProductPromo", UtilMisc.toMap("productPromoId", delegator.getNextSeqId("ProductPromo")));
		productPromo = EnvUtil.insertGenericValueData(delegator, productPromo, context);
		delegator.createOrStore(productPromo);

		return productPromo;
	}

	/*
	 * Create Product Promo Rule
	 */
	public static GenericValue createOrUpdateProductPromoRule(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
		//check if it exists already
		GenericValue productPromoRule = delegator.findOne("ProductPromoRule", UtilMisc.toMap("productPromoId", (String) context.get("productPromoId"), "productPromoRuleId", (String) context.get("productPromoRuleId")), false);
		if(productPromoRule == null) {
			productPromoRule = delegator.makeValue("ProductPromoRule", UtilMisc.toMap("productPromoId", (String) context.get("productPromoId"), "productPromoRuleId", (String) context.get("productPromoRuleId")));
		}

		productPromoRule = EnvUtil.insertGenericValueData(delegator, productPromoRule, context);
		delegator.createOrStore(productPromoRule);

		return productPromoRule;
	}

	/*
	 * Create Product Promo Condition
	 */
	public static GenericValue createOrUpdateProductPromoCond(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
		//check if it exists already
		GenericValue productPromoCond = delegator.findOne("ProductPromoCond", UtilMisc.toMap("productPromoId", (String) context.get("productPromoId"), "productPromoRuleId", (String) context.get("productPromoRuleId"), "productPromoCondSeqId", (String) context.get("productPromoCondSeqId")), false);
		if(productPromoCond == null) {
			productPromoCond = delegator.makeValue("ProductPromoCond", UtilMisc.toMap("productPromoId", (String) context.get("productPromoId"), "productPromoRuleId", (String) context.get("productPromoRuleId"), "productPromoCondSeqId", (String) context.get("productPromoCondSeqId")));
		}

		productPromoCond = EnvUtil.insertGenericValueData(delegator, productPromoCond, context);
		delegator.createOrStore(productPromoCond);

		return productPromoCond;
	}

	/*
	 * Create Product Promo Action
	 */
	public static GenericValue createOrUpdateProductPromoAction(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
		//check if it exists already
		GenericValue productPromoAction = delegator.findOne("ProductPromoAction", UtilMisc.toMap("productPromoId", (String) context.get("productPromoId"), "productPromoRuleId", (String) context.get("productPromoRuleId"), "productPromoActionSeqId", (String) context.get("productPromoActionSeqId")), false);
		if(productPromoAction == null) {
			productPromoAction = delegator.makeValue("ProductPromoAction", UtilMisc.toMap("productPromoId", (String) context.get("productPromoId"), "productPromoRuleId", (String) context.get("productPromoRuleId"), "productPromoActionSeqId", (String) context.get("productPromoActionSeqId")));
		}

		productPromoAction = EnvUtil.insertGenericValueData(delegator, productPromoAction, context);
		delegator.createOrStore(productPromoAction);

		return productPromoAction;
	}

	/*
	 * Create Product Promo Store Appl
	 */
	public static GenericValue createOrUpdateProductStorePromoAppl(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
		//check if it exists already
		GenericValue productStorePromoAppl = EntityUtil.getFirst(delegator.findByAnd("ProductStorePromoAppl", UtilMisc.toMap("productPromoId", (String) context.get("productPromoId")), null, false));
		if(productStorePromoAppl == null) {
			productStorePromoAppl = delegator.makeValue("ProductStorePromoAppl", UtilMisc.toMap("productStoreId", "10000"));
		}

		productStorePromoAppl = EnvUtil.insertGenericValueData(delegator, productStorePromoAppl, context);
		delegator.createOrStore(productStorePromoAppl);

		return productStorePromoAppl;
	}

	/*
	 * Create Product Promo Code
	 */
	public static GenericValue createProductPromoCode(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
		//check if it exists already
		GenericValue productPromoCode = null;
		if(UtilValidate.isNotEmpty(context.get("productPromoCodeId"))) {
			productPromoCode = delegator.findOne("ProductPromoCode", UtilMisc.toMap("productPromoCodeId", (String) context.get("productPromoCodeId")), false);
		}

		if(productPromoCode == null) {
			productPromoCode = delegator.makeValue("ProductPromoCode", UtilMisc.toMap("productPromoCodeId", (String) context.get("productPromoCodeId")));
		}

		productPromoCode = EnvUtil.insertGenericValueData(delegator, productPromoCode, context);
		delegator.createOrStore(productPromoCode);

		return productPromoCode;
	}

	/*
	 * Check if the cart has a free shipping promo, if so we will show free shipping message
	 */
	public static boolean isShippingFree(ShoppingCart cart) {
		List<GenericValue> adjustments = CartHelper.getCartAdjustments(cart);
		for(GenericValue adjustment : adjustments) {
			if("PROMO_SHIP_CHARGE".equalsIgnoreCase(adjustment.getString("productPromoActionEnumId"))) {
				BigDecimal amount = adjustment.getBigDecimal("amount").abs();
				if(amount.compareTo(cart.getTotalShipping()) >= 0) {
					return true;
				}
			}
		}

		return false;
	}

	/*
	* Check if the cart has a free shipping promo, if so we will show free shipping message
	*/
	public static boolean isShippingFree(List<GenericValue> adjustments) {
		BigDecimal shippingTotal = BigDecimal.ZERO;
		BigDecimal shippingDiscount = BigDecimal.ZERO;

		for (GenericValue adjustment : adjustments) {
			if ((adjustment.getString("orderAdjustmentTypeId").equals("PROMOTION_ADJUSTMENT") || adjustment.getString("orderAdjustmentTypeId").equals("SHIPPING_CHARGES")) && "PROMO_SHIP_CHARGE".equalsIgnoreCase(adjustment.getString("productPromoActionEnumId"))) {
				shippingTotal = adjustment.getBigDecimal("amount");
			} else if (adjustment.getString("orderAdjustmentTypeId").equals("SHIPPING_CHARGES")) {
				shippingDiscount = adjustment.getBigDecimal("amount");
			}
		}

		return (shippingTotal.compareTo(shippingDiscount.abs()) == 0);
	}

	/*
	 * Get the discount value for the free shipping promo
	 */
	public static BigDecimal getShippingDiscount(ShoppingCart cart, boolean getAbsolute) {
		BigDecimal shippingDiscount = BigDecimal.ZERO;
		List<GenericValue> adjustments = CartHelper.getCartAdjustments(cart);
		for(GenericValue adjustment : adjustments) {
			if("PROMO_SHIP_CHARGE".equalsIgnoreCase(adjustment.getString("productPromoActionEnumId"))) {
				shippingDiscount = adjustment.getBigDecimal("amount");
			}
		}

		return (getAbsolute) ? shippingDiscount.setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING).abs() : shippingDiscount.setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);
	}

	/*
	 * Get the discounts used for an order
	 */
	public static String getProductPromoUse(Delegator delegator, String orderId) throws GenericEntityException {
		StringBuilder couponsList = new StringBuilder("");
		List<GenericValue> promoCodes = delegator.findByAnd("ProductPromoUse", UtilMisc.toMap("orderId", orderId), null, false);
		for(GenericValue use : promoCodes) {
			if(UtilValidate.isNotEmpty(use.getString("productPromoCodeId"))) {
				couponsList.append((UtilValidate.isNotEmpty(couponsList.toString())) ? "," : "" ).append(use.getString("productPromoCodeId"));
			}

		}

		return couponsList.toString();
	}
}