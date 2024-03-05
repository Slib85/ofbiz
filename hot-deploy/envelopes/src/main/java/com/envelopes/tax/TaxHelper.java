/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.tax;

import java.math.BigDecimal;
import java.util.*;

import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.order.order.OrderReadHelper;
import org.apache.ofbiz.order.shoppingcart.ShoppingCart;

import com.envelopes.util.*;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartItem;

public class TaxHelper {
	public static final String module = TaxHelper.class.getName();

	public static final List<String> STATE_TAXABLE = new ArrayList<>();
	public static final List<String> STATE_WITH_TAXABLE_SUBTOTAL = new ArrayList<>();
	public static final List<String> STATE_WITH_TAXABLE_SHIPPING = new ArrayList<>();
	public static final List<String> STATE_WITHOUT_TAXABLE_SHIPPING = new ArrayList<>();
	public static boolean isAvalaraEnabled = false;

	static {
		isAvalaraEnabled = UtilProperties.getPropertyAsBoolean("envelopes", "avalara.enabled", false);

		STATE_TAXABLE.add("AL");
		STATE_TAXABLE.add("CA");
		STATE_TAXABLE.add("FL");
		STATE_TAXABLE.add("IN");
		STATE_TAXABLE.add("KS");
		STATE_TAXABLE.add("KY");
		STATE_TAXABLE.add("MA");
		STATE_TAXABLE.add("MN");
		STATE_TAXABLE.add("NJ");
		STATE_TAXABLE.add("NY");
		STATE_TAXABLE.add("PA");
		STATE_TAXABLE.add("TN");
		STATE_TAXABLE.add("TX");
		STATE_TAXABLE.add("WA");
		STATE_TAXABLE.add("WI");

		STATE_WITH_TAXABLE_SUBTOTAL.add("AL");
		STATE_WITH_TAXABLE_SUBTOTAL.add("CA");
		STATE_WITH_TAXABLE_SUBTOTAL.add("FL");
		STATE_WITH_TAXABLE_SUBTOTAL.add("IN");
		STATE_WITH_TAXABLE_SUBTOTAL.add("KS");
		STATE_WITH_TAXABLE_SUBTOTAL.add("KY");
		STATE_WITH_TAXABLE_SUBTOTAL.add("MA");
		STATE_WITH_TAXABLE_SUBTOTAL.add("MN");
		STATE_WITH_TAXABLE_SUBTOTAL.add("NJ");
		STATE_WITH_TAXABLE_SUBTOTAL.add("NY");
		STATE_WITH_TAXABLE_SUBTOTAL.add("PA");
		STATE_WITH_TAXABLE_SUBTOTAL.add("TN");
		STATE_WITH_TAXABLE_SUBTOTAL.add("TX");
		STATE_WITH_TAXABLE_SUBTOTAL.add("WA");
		STATE_WITH_TAXABLE_SUBTOTAL.add("WI");

		STATE_WITH_TAXABLE_SHIPPING.add("IN");
		STATE_WITH_TAXABLE_SHIPPING.add("KS");
		STATE_WITH_TAXABLE_SHIPPING.add("KY");
		STATE_WITH_TAXABLE_SHIPPING.add("MN");
		STATE_WITH_TAXABLE_SHIPPING.add("NY");
		STATE_WITH_TAXABLE_SHIPPING.add("NJ");
		STATE_WITH_TAXABLE_SHIPPING.add("PA");
		STATE_WITH_TAXABLE_SHIPPING.add("TN");
		STATE_WITH_TAXABLE_SHIPPING.add("TX");
		STATE_WITH_TAXABLE_SHIPPING.add("WA");
		STATE_WITH_TAXABLE_SHIPPING.add("WI");

		STATE_WITHOUT_TAXABLE_SHIPPING.add("AL");
		STATE_WITHOUT_TAXABLE_SHIPPING.add("CA");
		STATE_WITHOUT_TAXABLE_SHIPPING.add("FL");
		STATE_WITHOUT_TAXABLE_SHIPPING.add("MA");
	}

	/*
	 * Get the tax rate
	 */
	public static BigDecimal getTaxRate(Delegator delegator, String postalCode) throws GenericEntityException {
		if(UtilValidate.isEmpty(postalCode)) {
			return BigDecimal.ZERO;
		}

		postalCode = cleanZipCode(postalCode);

		GenericValue tax = EntityUtil.getFirst(delegator.findByAnd("ZipSalesTaxLookup", UtilMisc.toMap("zipCode", postalCode), null, true));
		if(tax != null && UtilValidate.isNotEmpty(tax.get("comboSalesTax"))) {
			return tax.getBigDecimal("comboSalesTax");
		}

		return BigDecimal.ZERO;
	}

	/*
	 * Get total tax
	 */
	public static BigDecimal getTaxableTotal(ShoppingCart cart, Map<String, String> postalAddress) {
		BigDecimal taxableTotal = BigDecimal.ZERO;
		if(cart == null) {
			return taxableTotal;
		}

		if(UtilValidate.isNotEmpty(postalAddress.get("stateProvinceGeoId")) && STATE_WITH_TAXABLE_SUBTOTAL.contains(postalAddress.get("stateProvinceGeoId"))) {
			taxableTotal = taxableTotal.add(cart.getSubTotal()).add(cart.getOrderOtherAdjustmentTotal());
		}

		if(UtilValidate.isNotEmpty(postalAddress.get("stateProvinceGeoId")) && STATE_WITH_TAXABLE_SHIPPING.contains(postalAddress.get("stateProvinceGeoId"))) {
			taxableTotal = taxableTotal.add(cart.getTotalShipping());
		}

		/*
			if a free ship coupon was added to the cart and the ship zone
			does not charge tax on shipping, add the discount back to the taxable total
			REASON: the cart makes an adjustment to negate the shipping charge instead of
			zeroing out the shipping charge, for this reason, the shipping discount should
			be put back in to the taxable total or else the tax will be lower than it should
		 */
		if(UtilValidate.isNotEmpty(postalAddress.get("stateProvinceGeoId")) && STATE_WITHOUT_TAXABLE_SHIPPING.contains(postalAddress.get("stateProvinceGeoId"))) {
			List<GenericValue> filteredAdjs = OrderReadHelper.filterOrderAdjustments(cart.getAdjustments(), true, false, false, false, false);
			for (GenericValue orderAdjustment : filteredAdjs) {
				if("PROMOTION_ADJUSTMENT".equals(orderAdjustment.getString("orderAdjustmentTypeId")) && "PROMO_SHIP_CHARGE".equals(orderAdjustment.getString("productPromoActionEnumId"))) {
					taxableTotal = taxableTotal.add(orderAdjustment.getBigDecimal("amount").abs()); //add abs() because the adjustment is negative
				}
			}
		}

		return taxableTotal;
	}

	/**
	 * Get the total tax based on tax rate and taxable amount
	 * @param taxRate
	 * @param taxableTotal
	 * @return BigDecimal
	 */
	public static BigDecimal getTaxTotal(BigDecimal taxRate, BigDecimal taxableTotal) {
		return taxRate.multiply(taxableTotal).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);
	}

	/**
	 * Clean up zip code for only 5 digits
	 * @param postalCode
	 * @return String
	 */
	public static String cleanZipCode(String postalCode) {
		if(UtilValidate.isNotEmpty(postalCode) && postalCode.length() > 5) {
			postalCode = postalCode.substring(0, 5);
		}

		return postalCode;
	}

	/**
	 * Create tax context for calc service
	 * @param shipGroup
	 * @param cart
	 * @param shipAddress
	 * @return Map
	 */
	public static Map<String, Object> makeTaxContext(int shipGroup, ShoppingCart cart, GenericValue shipAddress) {
		ShoppingCart.CartShipInfo csi = cart.getShipInfo(shipGroup);
		int totalItems = csi.shipItemInfo.size();

		List<GenericValue> product = new ArrayList<>(totalItems);
		List<BigDecimal> amount = new ArrayList<>(totalItems);
		List<BigDecimal> price = new ArrayList<>(totalItems);
		List<BigDecimal> quantity = new ArrayList<>(totalItems);
		List<BigDecimal> shipAmt = new ArrayList<>(totalItems);

		Iterator<ShoppingCartItem> it = csi.shipItemInfo.keySet().iterator();
		for (int i = 0; i < totalItems; i++) {
			ShoppingCartItem cartItem = it.next();
			product.add(i, cartItem.getProduct());
			amount.add(i, cartItem.getItemSubTotal(cartItem.getQuantity()));
			price.add(i, cartItem.getBasePrice());
			quantity.add(i, cartItem.getQuantity());
			shipAmt.add(i, BigDecimal.ZERO);
		}

		// Adjustments
		List<GenericValue> allAdjustments = cart.getAdjustments();

		// Total promotions except shipping
		BigDecimal orderPromoAmt = calcOrderPromoAdjustments(allAdjustments, "PROMOTION_ADJUSTMENT", null, false);
		orderPromoAmt = orderPromoAmt.add(calcOrderPromoAdjustments(allAdjustments, "DISCOUNT_ADJUSTMENT", null, false));
		orderPromoAmt = orderPromoAmt.add(calcOrderPromoAdjustments(allAdjustments, "FEE", null, false));

		// Total shipping promotions only
		BigDecimal orderShipPromoAmt = calcOrderPromoAdjustments(allAdjustments, "SHIPPING_CHARGES", "PROMO_SHIP_CHARGE", true);
		BigDecimal shipAmount = csi.shipEstimate.add(orderShipPromoAmt);

		Map<String, Object> serviceContext = UtilMisc.<String, Object>toMap("productStoreId", cart.getProductStoreId());
		serviceContext.put("payToPartyId", cart.getBillFromVendorPartyId());
		serviceContext.put("billToPartyId", cart.getBillToCustomerPartyId());
		serviceContext.put("itemProductList", product);
		serviceContext.put("itemAmountList", amount);
		serviceContext.put("itemPriceList", price);
		serviceContext.put("itemQuantityList", quantity);
		serviceContext.put("itemShippingList", shipAmt);
		serviceContext.put("orderShippingAmount", shipAmount); // Shipping total minus any shipping discount
		serviceContext.put("shippingAddress", shipAddress);
		serviceContext.put("orderPromotionsAmount", orderPromoAmt); // All promotion discounts except shipping discounts
		serviceContext.put("shipmentMethodType", cart.getShipmentMethodType(0));
		serviceContext.put("externalPartyId", cart.getExternalPartyId());

		return serviceContext;
	}

	/**
	 * Get all the promotions except Shipping Discounts.
	 * Shipping Discounts will be deducted straight from the shipping total instead
	 * @param allOrderAdjustments
	 * @param includeShippingDiscounts
	 * @return BigDecimal
	 */
	public static BigDecimal calcOrderPromoAdjustments(List<GenericValue> allOrderAdjustments, String orderAdjustmentTypeId, String productPromoActionEnumId, boolean includeShippingDiscounts) {
		BigDecimal promoAdjTotal = BigDecimal.ZERO;

		Map<String, Object> filters = new HashMap<>();
		if(UtilValidate.isNotEmpty(orderAdjustmentTypeId)) {
			filters.put("orderAdjustmentTypeId", orderAdjustmentTypeId);
		}
		if(UtilValidate.isNotEmpty(productPromoActionEnumId)) {
			filters.put("productPromoActionEnumId", productPromoActionEnumId);
		}

		List<GenericValue> promoAdjustments = EntityUtil.filterByAnd(allOrderAdjustments, filters);
		if (UtilValidate.isNotEmpty(promoAdjustments)) {
			Iterator<GenericValue> promoAdjIter = promoAdjustments.iterator();
			while (promoAdjIter.hasNext()) {
				GenericValue promoAdjustment = promoAdjIter.next();
				if (promoAdjustment != null) {
					if("PROMO_SHIP_CHARGE".equals(promoAdjustment.getString("productPromoActionEnumId")) && !includeShippingDiscounts) {
						continue;
					}
					BigDecimal amount = promoAdjustment.getBigDecimal("amount").setScale(OrderReadHelper.taxCalcScale, OrderReadHelper.taxRounding);
					promoAdjTotal = promoAdjTotal.add(amount);
				}
			}
		}
		return promoAdjTotal.setScale(OrderReadHelper.scale, OrderReadHelper.rounding);
	}
}