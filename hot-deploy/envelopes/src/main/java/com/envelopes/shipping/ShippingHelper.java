/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.shipping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.common.geo.GeoWorker;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.order.shoppingcart.ShoppingCart;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartItem;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;

import com.envelopes.cart.CartHelper;
import com.envelopes.product.ProductHelper;
import com.envelopes.ups.UPSHelper;
import com.envelopes.util.*;

public class ShippingHelper {
	public static final String module = ShippingHelper.class.getName();
	private static final BigDecimal fedexWeightLimit = new BigDecimal(150);
	private static final ArrayList<String> zebraPackPrintingSkuList = new ArrayList<>();
	private static final ArrayList<String> zebraPackShippingList = new ArrayList<>();
	private static final BigDecimal groundFlatRateFeeCondition = new BigDecimal("20");
	private static final BigDecimal groundFlatRateFee = new BigDecimal("7.95");

	static {
		zebraPackPrintingSkuList.add("LUX-KBBM-0");
		zebraPackPrintingSkuList.add("LUX-KWBM-0");
		zebraPackPrintingSkuList.add("LUX-KGBM-0");
		zebraPackPrintingSkuList.add("LUX-KWBM-2");
		zebraPackPrintingSkuList.add("LUX-KBBM-3");
		zebraPackPrintingSkuList.add("LUX-KWBM-4");
		zebraPackPrintingSkuList.add("LUX-KWBM-5");
		zebraPackPrintingSkuList.add("LUX-KWBM-6");
		zebraPackPrintingSkuList.add("LUX-KWBM-7");

		zebraPackShippingList.add("GROUND");
		zebraPackShippingList.add("HOME_DELIVERY");
		zebraPackShippingList.add("FEDEX_INTR_GROUND");
	}



	// Apply shipping limitation for Zebra Pack.  They only allow Standard Shipping.
	private static Map<GenericValue, BigDecimal> checkZebraPackShipping(Map<GenericValue, BigDecimal> allShippingEstimates, ShoppingCart cart) {
		try {
			for (ShoppingCartItem cartItem : cart) {
				if (zebraPackPrintingSkuList.contains(cartItem.getProductId()) &&
						UtilValidate.isNotEmpty(cartItem.getAttributes()) && UtilValidate.isNotEmpty(cartItem.getAttributes().get("envPriceCalcAttributes")) &&
						((Integer) ((HashMap) (cartItem.getAttributes().get("envPriceCalcAttributes"))).get("colorsFront") >= 1 ||
						(Integer) ((HashMap) (cartItem.getAttributes().get("envPriceCalcAttributes"))).get("colorsBack") >= 1)) {
					// Remove all but ground and home delivery.
					Iterator allShippingEstimatesIter = allShippingEstimates.keySet().iterator();
					List<GenericValue> listOfShipMethodsToRemove = new ArrayList<>();

					while (allShippingEstimatesIter.hasNext()) {
						GenericValue shipMethod = (GenericValue) allShippingEstimatesIter.next();

						if (!(zebraPackShippingList.contains(shipMethod.get("shipmentMethodTypeId")))) {
							listOfShipMethodsToRemove.add(shipMethod);
						}
					}

					for (GenericValue shipMethod : listOfShipMethodsToRemove) {
						allShippingEstimates.remove(shipMethod);
					}
				}
			}
		} catch (Exception e) {
			Debug.log("Error trying to remove shipping methods for Zebra Pack. " + e.getMessage(), module);
		}

		return allShippingEstimates;
	}
	/*
	 * Get the shipping rate for selected shipment method type
	 */
	public static void setShipMethodAndTotal(Delegator delegator, LocalDispatcher dispatcher, Map<String, String> postalAddress, ShoppingCart cart) throws GenericEntityException, GenericServiceException, Exception {
		if(UtilValidate.isEmpty(postalAddress) && UtilValidate.isNotEmpty(cart.getShipTaxPostalAddress())) {
			postalAddress = cart.getShipTaxPostalAddress();
		}

		if(UtilValidate.isNotEmpty(postalAddress)) {
			Map<GenericValue, BigDecimal> estimates = EnvConstantsUtil.IS_UPS ? getAllShippingEstimates(delegator, dispatcher, postalAddress, cart) : getAllFedExShippingEstimates(delegator, dispatcher, postalAddress, cart);
			String shipmentMethodTypeId = cart.getShipmentMethodTypeId(0);

			if(estimates.size() > 0) {
				Iterator estimateIter = estimates.keySet().iterator();
				while(estimateIter.hasNext()) {
					GenericValue shipMethod = (GenericValue) estimateIter.next();
					//while looping through lets set the cart shipping if we find a matching value or assign the first one
					if(UtilValidate.isNotEmpty(shipmentMethodTypeId) && shipmentMethodTypeId.equals(shipMethod.getString("shipmentMethodTypeId"))) {
						CartHelper.setShipmentMethodTypeId(dispatcher, cart, 0, shipmentMethodTypeId, shipMethod.getString("partyId"));
						CartHelper.setShippingTotal(cart, 0, (BigDecimal) estimates.get(shipMethod));
					}
				}
			}
		}
	}

	private static List<GenericValue> filteredShippingMethods(List shippingMethods, boolean isOnlySample, boolean isOnlySwatchBook) {
		List<GenericValue> finalShippingMethods = new LinkedList(shippingMethods);
		Iterator shippingMethodsIter = shippingMethods.iterator();
		while(shippingMethodsIter.hasNext()) {
			GenericValue shippingMethod = (GenericValue) shippingMethodsIter.next();
			String shipmentMethodTypeId = shippingMethod.getString("shippingMethodId");

			if((isOnlySample || isOnlySwatchBook) && !shipmentMethodTypeId.equals("FIRST_CLASS") && !shipmentMethodTypeId.equals("PICKUP")) {
				finalShippingMethods.remove(shippingMethod);
				continue;
			}
		}

		return finalShippingMethods;
	}

	public static Map<GenericValue, BigDecimal> getAllFedExShippingEstimates(Delegator delegator, LocalDispatcher dispatcher, Map<String, String> postalAddress, ShoppingCart cart) throws Exception {
		BigDecimal maxCartonWeight = new BigDecimal("150");

		Map<GenericValue, BigDecimal> estimates;

		if(cart == null) {
			return null;
		}

		boolean isOnlySample;
		boolean isOnlySwatchBook;

		if (CartHelper.isAllowedFreeShipping(cart)) {
			isOnlySample = true;
			isOnlySwatchBook = true;
		} else {
			isOnlySample = CartHelper.isOnlySample(cart);
			isOnlySwatchBook = CartHelper.isOnlySwatchBook(cart);
		}


		List splitInfo = getSplitPackageInfo(delegator, cart, maxCartonWeight);

		List<GenericValue> shippingMethods = delegator.findByAnd("ShippingCarrierMethod", null, null, true);

		boolean isFlatRatable = false;
		if(UtilValidate.isNotEmpty(postalAddress.get("stateProvinceGeoId")) && !(postalAddress.get("stateProvinceGeoId")).equals("__BLANK__")) {
			List shippableItemInfo = cart.getShippableItemInfo(0);
			Map isUSPSSmallFlatRatable = null;
			Map uspsSmallFlatRateMap = new HashMap();
			uspsSmallFlatRateMap.put("stateProvinceGeoId", postalAddress.get("stateProvinceGeoId"));
			uspsSmallFlatRateMap.put("shippableItemInfo", shippableItemInfo);
			isUSPSSmallFlatRatable = dispatcher.runSync("isUSPSSmallFlatRatable", uspsSmallFlatRateMap);
			if(UtilValidate.isNotEmpty(isUSPSSmallFlatRatable) && isUSPSSmallFlatRatable.containsKey("isFlatRatable")) {
				isFlatRatable = ((Boolean)isUSPSSmallFlatRatable.get("isFlatRatable")).booleanValue();

			}
		}
		if(!isFlatRatable) {
			shippingMethods = EntityUtil.filterByCondition(shippingMethods, EntityCondition.makeCondition("shippingMethodId", EntityOperator.NOT_EQUAL, "SMALL_FLAT_RATE"));
		}

		estimates = getFedExEstimates(delegator, splitInfo, filteredShippingMethods(shippingMethods, isOnlySample, isOnlySwatchBook), cart, postalAddress, isOnlySample, isOnlySwatchBook);

		if(estimates.size() > 1) {
			estimates = sortShipMapByValues((HashMap) estimates);
		}

		return estimates;
	}

	@SuppressWarnings("unchecked")
	public static Map<GenericValue, BigDecimal> getFedExEstimates(Delegator delegator, List splitInfo, List shippingMethods, ShoppingCart cart, Map<String, String> postalAddress, boolean isOnlySample, boolean isOnlySwatchBook) throws GenericEntityException, Exception {
		boolean hasFreeShipItem = CartHelper.hasFreeShipItem(cart);
		String freeShipMethod = UtilProperties.getPropertyValue("envelopes", "shipment.quotes.ship.method");

		//certain locations do not allow for free shipping
		if(UtilValidate.isNotEmpty(postalAddress.get("stateProvinceGeoId")) && EnvConstantsUtil.FREE_SHIP_PROV_EXCL.contains(postalAddress.get("stateProvinceGeoId"))) {
			hasFreeShipItem = false;
			freeShipMethod = "_NA_";
		} else if(UtilValidate.isNotEmpty(postalAddress.get("countryGeoId")) && EnvConstantsUtil.FREE_SHIP_COUNTRY_EXCL.contains(postalAddress.get("countryGeoId"))) {
			hasFreeShipItem = false;
			freeShipMethod = "_NA_";
		}

		Map<GenericValue, BigDecimal> allShippingEstimates = new HashMap<>();

		//get the zone
		List<GenericValue> shippingZones = getFedExShippingZones(delegator, postalAddress.get("postalCode"));

		//loop through the available shipping methods and get each cost
		if(UtilValidate.isNotEmpty(shippingMethods) && UtilValidate.isNotEmpty(splitInfo)) {
			Iterator shipMethodIter = shippingMethods.iterator();
			while(shipMethodIter.hasNext()) {
				GenericValue shippingMethod = (GenericValue) shipMethodIter.next();
				String shippingMethodId = shippingMethod.getString("shippingMethodId");

				BigDecimal shipMethodTotalCost = BigDecimal.ZERO;
				BigDecimal totalWeight = BigDecimal.ZERO;
				BigDecimal totalPartialWeight = BigDecimal.ZERO;
				List<BigDecimal> packages = new ArrayList<>();

				Iterator splitInfoIter = splitInfo.iterator();
				while(splitInfoIter.hasNext()) {
					Map<String, BigDecimal> cartInfoList = (HashMap<String, BigDecimal>) splitInfoIter.next();

					BigDecimal originalWeight = cartInfoList.get("originalWeight");
					BigDecimal overrideShipCost = cartInfoList.get("overrideShipCost");

					if(freeShipMethod.contains(shippingMethodId) && overrideShipCost.compareTo(BigDecimal.ZERO) == 0) {
						//do not tally up partial/total weight for free item because its free and no need to do any calculation on it
					} else {
						totalWeight = totalWeight.add(originalWeight);
					}
				}

				shipMethodTotalCost = shipMethodTotalCost.add(getFedExShippingEstimate(delegator, shippingMethod, shippingZones, totalWeight, new BigDecimal(1)));
				GenericValue shipmentMethod = delegator.findByAnd("ProductStoreShipmentMethView", UtilMisc.toMap("productStoreId", "10000", "shipmentMethodTypeId", delegator.findOne("ShippingMethod", UtilMisc.toMap("shippingMethodId", shippingMethod.getString("shippingMethodId")), true).getString("shipmentMethodTypeId")), UtilMisc.toList("sequenceNumber"), true).get(0);

				if (allShippingEstimates.containsKey(shipmentMethod)) {
					allShippingEstimates.put(shipmentMethod, (allShippingEstimates.get(shipmentMethod)).add((shipMethodTotalCost)));
				} else {
					allShippingEstimates.put(shipmentMethod, (shipMethodTotalCost));
				}
			}
		}

		//a list of all zero shipping methods
		List zeroEstimatesList = new ArrayList();

		Iterator estimateIter = allShippingEstimates.keySet().iterator();
		while(estimateIter.hasNext()) {
			GenericValue shipMethod = (GenericValue) estimateIter.next();
			BigDecimal value = allShippingEstimates.get(shipMethod);
			if((value == null || value.compareTo(BigDecimal.ZERO) == 0)) {
				if((isOnlySample || isOnlySwatchBook) && shipMethod.getString("shipmentMethodTypeId").equals("FIRST_CLASS")) {
					//if its only sample or swatch book we will keep it
				} else if(hasFreeShipItem && freeShipMethod.contains(shipMethod.getString("shipmentMethodTypeId"))) {
					//if its a quote and service is free ship we will keep it
				} else {
					zeroEstimatesList.add(shipMethod);
				}
			}
		}


		Iterator zeroEstIter = zeroEstimatesList.iterator();
		while(zeroEstIter.hasNext()) {
			GenericValue est = (GenericValue) zeroEstIter.next();
			allShippingEstimates.remove(est);
		}


		//now lets remove any duplicate shipping methods that would show up if showGenericShipping is set to Y
		if(EnvConstantsUtil.SHOW_GENERIC_SHIP_NAMES) {

			boolean has3Day = false;
			boolean has2Day = false;
			GenericValue threeDayGV = null;
			GenericValue twoDayGV = null;

			boolean hasGround = false;
			BigDecimal groundFee = BigDecimal.ZERO;
			GenericValue groundGV = null;
			boolean hasSmallFlatRate = false;
			BigDecimal smallFlatRateFee = BigDecimal.ZERO;
			GenericValue smallFlatRateGV = null;
			boolean hasSmartPost = false;
			BigDecimal smartPostFee = BigDecimal.ZERO;
			GenericValue smartPostGV = null;
			boolean hasHomeDelivery = false;
			BigDecimal homeDeliveryFee = BigDecimal.ZERO;
			GenericValue homeDeliveryGV = null;

			estimateIter = allShippingEstimates.keySet().iterator();
			while(estimateIter.hasNext()) {
				GenericValue est = (GenericValue) estimateIter.next();
				String estMethod = est.getString("shipmentMethodTypeId");

				if(estMethod.equals("THREE_DAY")) {
					has3Day = true;
					threeDayGV = est;
				} else if(estMethod.equals("TWO_DAY")) {
					has2Day = true;
					twoDayGV = est;
				} else if(estMethod.equals("GROUND")) {
					hasGround = true;
					groundGV = est;

					if (cart.getSubTotal().compareTo(groundFlatRateFeeCondition) == -1) {
						allShippingEstimates.put(est, groundFlatRateFee);
					}

					groundFee = (BigDecimal) allShippingEstimates.get(est);
				} else if(estMethod.equals("SMART_POST")) {
					hasSmartPost = true;
					smartPostGV = est;
					smartPostFee = (BigDecimal) allShippingEstimates.get(est);
				} else if(estMethod.equals("SMALL_FLAT_RATE")) {
					hasSmallFlatRate = true;
					smallFlatRateGV = est;
					smallFlatRateFee = (BigDecimal) allShippingEstimates.get(est);
				} else if(estMethod.equals("HOME_DELIVERY")) {
					hasHomeDelivery = true;
					homeDeliveryGV = est;

					if (cart.getSubTotal().compareTo(groundFlatRateFeeCondition) == -1) {
						allShippingEstimates.put(est, groundFlatRateFee);
					}
					
					smallFlatRateFee = (BigDecimal) allShippingEstimates.get(est);
				}
			}

			if (UtilValidate.isNotEmpty(postalAddress.get("shipTo")) && postalAddress.get("shipTo").equals("RESIDENTIAL_LOCATION") && hasHomeDelivery) {
				allShippingEstimates.remove(groundGV);
			} else {
				allShippingEstimates.remove(homeDeliveryGV);
			}

			if(has3Day && has2Day) {
				//if both 3day and 2day are available, lets remove 2day
				Debug.logInfo("Removing FEDEX TWO_DAY from list because FEDEX THREE_DAY is available", module);
				allShippingEstimates.remove(twoDayGV);
			}

			// Craig mentioned that we will remove smart post for now until we fully understand the rules for smart post.
			if (hasSmartPost) {
				allShippingEstimates.remove(smartPostGV);
			}

			//if both small flat rate and ground are available, lets remove one or the other
			/*
			boolean removedGround = false;
			if(hasSmallFlatRate && hasGround) {
				if(smallFlatRateFee == null) {
					smallFlatRateFee = BigDecimal.ZERO;
				}
				if(groundFee == null) {
					groundFee = BigDecimal.ZERO;
				}
				if(groundFee.compareTo(BigDecimal.ZERO) <= 0 && smallFlatRateFee.compareTo(BigDecimal.ZERO) <= 0) {
					Debug.log("Removing USPS Small Flat Rate from list because both are 0", module);
					allShippingEstimates.remove(smallFlatRateGV);
				} else if(groundFee.compareTo(BigDecimal.ZERO) > 0 && smallFlatRateFee.compareTo(BigDecimal.ZERO) > 0) {
					Debug.log("Removing FEDEX Ground from list because both have values", module);
					allShippingEstimates.remove(groundGV);
					removedGround = true;
				} else if(groundFee.compareTo(BigDecimal.ZERO) > 0 && smallFlatRateFee.compareTo(BigDecimal.ZERO) <= 0) {
					Debug.log("Removing USPS Small Flat Rate from list because it is 0", module);
					allShippingEstimates.remove(smallFlatRateGV);
				} else if(groundFee.compareTo(BigDecimal.ZERO) <= 0 && smallFlatRateFee.compareTo(BigDecimal.ZERO) > 0) {
					Debug.log("Removing FEDEX Ground from list because it is 0", module);
					allShippingEstimates.remove(groundGV);
					removedGround = true;
				}
			}

			//ground & smartPost rules
			if(removedGround) {
				allShippingEstimates.remove(smartPostGV); //if ground has been removed prior, remove smartPost as well
			} else if(hasSmartPost) {
				allShippingEstimates.remove(groundGV); //if both ground and surepost are available, remove ground
			}
			*/
		}

		return checkZebraPackShipping(allShippingEstimates, cart);
	}

	private static GenericValue getWeightBreak(Delegator delegator, BigDecimal weight) throws GenericEntityException {
		List<EntityCondition> conditions = new ArrayList<>();
		conditions.add(EntityCondition.makeCondition("quantityBreakTypeId", EntityOperator.EQUALS, "SHIP_WEIGHT"));
		conditions.add(EntityCondition.makeCondition("thruQuantity", EntityOperator.GREATER_THAN_EQUAL_TO, weight));
		conditions.add(EntityCondition.makeCondition("fromQuantity", EntityOperator.LESS_THAN_EQUAL_TO, weight));
		EntityCondition condition = EntityCondition.makeCondition(conditions, EntityOperator.AND);
		return EntityUtil.getFirst(delegator.findList("QuantityBreak", condition, null, null, null, true));
	}

	private static BigDecimal getShippingRate(Delegator delegator, GenericValue shippingMethod, List<GenericValue> shippingZones, GenericValue weightBreak) throws GenericEntityException {
		GenericValue shippingRate = null;
		BigDecimal ratePerUnit = BigDecimal.ZERO;

		List<GenericValue> filteredZones = EntityUtil.filterByAnd(shippingZones, UtilMisc.toMap("shippingMethodId", shippingMethod.getString("shippingMethodId")));

		if(UtilValidate.isNotEmpty(weightBreak) && UtilValidate.isNotEmpty(filteredZones)) {
			List<EntityCondition> conditions = new ArrayList<>();
			conditions = new ArrayList<>();
			conditions.add(EntityCondition.makeCondition("weightBreakId", EntityOperator.EQUALS, weightBreak.getString("quantityBreakId")));
			conditions.add(EntityCondition.makeCondition("zone", EntityOperator.IN, EntityUtil.getFieldListFromEntityList(filteredZones, "zone", true)));
			conditions.add(EntityCondition.makeCondition("carrierId", EntityOperator.EQUALS, "FEDEX"));
			conditions.add(EntityCondition.makeCondition("shippingMethodId", EntityOperator.EQUALS, shippingMethod.getString("shippingMethodId")));
			shippingRate = EntityUtil.getFirst(delegator.findList("ShippingCarrierRateLookup", EntityCondition.makeCondition(conditions, EntityOperator.AND), null, null, null, true));
		}

		if (UtilValidate.isNotEmpty(shippingRate) && UtilValidate.isNotEmpty(shippingRate.get("ratePerUnit"))) {
			ratePerUnit = shippingRate.getBigDecimal("ratePerUnit");
		}

		return ratePerUnit;
	}

	public static BigDecimal getFedExShippingEstimate(Delegator delegator, GenericValue shippingMethod, List<GenericValue> shippingZones, BigDecimal weight, BigDecimal cartons) throws GenericEntityException {
		BigDecimal shipMethodCost = new BigDecimal(0);

		List<EntityCondition> conditions = new ArrayList<>();
		BigDecimal additionalShipMethodCost = new BigDecimal(0);
		// Obtain shipping rates for floor(weight / 150) * rate of fedexWeightLimit
		if (weight.compareTo(fedexWeightLimit) >= 0) {
			GenericValue weightBreak = getWeightBreak(delegator, fedexWeightLimit);
			shipMethodCost = shipMethodCost.add(new BigDecimal("1").multiply(getShippingRate(delegator, shippingMethod, shippingZones, weightBreak)).multiply(weight.divide(fedexWeightLimit, 0, RoundingMode.DOWN)));
		}

		GenericValue weightBreak = getWeightBreak(delegator, weight.remainder(new BigDecimal(150)).setScale(0, RoundingMode.CEILING));
		shipMethodCost = shipMethodCost.add(new BigDecimal("1").multiply(getShippingRate(delegator, shippingMethod, shippingZones, weightBreak)).multiply(cartons).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));

		return shipMethodCost;
	}

	/*
	 * Get final List of shipping methods given address and cart
	 */
	public static Map<GenericValue, BigDecimal> getAllShippingEstimates(Delegator delegator, LocalDispatcher dispatcher, Map<String, String> postalAddress, ShoppingCart cart) throws GenericEntityException, GenericServiceException, Exception {
		BigDecimal maxCartonWeight = new BigDecimal(UtilProperties.getPropertyValue("envelopes", "shipment.ups.max.estimate.weight", "70"));

		Map<GenericValue, BigDecimal> estimates = new HashMap<>();
		BigDecimal shippableTotal = BigDecimal.ZERO;
		List shippableItemInfo = null;
		List shippableItemSizes = null;

		if(cart == null) {
			return null;
		}

		String productStoreId = (UtilValidate.isNotEmpty(cart.getProductStoreId())) ? cart.getProductStoreId() : "10000";
		shippableTotal = cart.getShippableTotal(0);
		shippableItemInfo = cart.getShippableItemInfo(0);
		shippableItemSizes = cart.getShippableSizes(0);

		boolean isOnlySample;
		boolean isOnlySwatchBook;

		if (CartHelper.isAllowedFreeShipping(cart)) {
			isOnlySample = true;
			isOnlySwatchBook = true;
		} else {
			isOnlySample = CartHelper.isOnlySample(cart);
			isOnlySwatchBook = CartHelper.isOnlySwatchBook(cart);
		}


		List splitInfo = getSplitPackageInfo(delegator, cart, maxCartonWeight);

		List<GenericValue> shippingMethods = getAvailableShippingMethods(delegator, dispatcher, splitInfo, productStoreId, postalAddress, shippableItemInfo, shippableItemSizes, shippableTotal, isOnlySample, isOnlySwatchBook);
		estimates = getEstimates(delegator, splitInfo, shippingMethods, cart, postalAddress, isOnlySample, isOnlySwatchBook);

		if(estimates.size() > 1) {
			estimates = sortShipMapByValues((HashMap) estimates);
		}

		return estimates;
	}

	/*
	 * Get a list of all the available shipping methods
	 */
	public static List<GenericValue> getAvailableShippingMethods(Delegator delegator, LocalDispatcher dispatcher, List splitInfo, String productStoreId, Map<String, String> postalAddress, List shippableItemInfo, List shippableItemSizes, BigDecimal shippableTotal, boolean isOnlySample, boolean isOnlySwatchBook) throws GenericEntityException, GenericServiceException {
		if(UtilValidate.isNotEmpty(productStoreId)) {
			productStoreId = "10000"; //if the id is unavailable we are defaulting to the env website
		}

		List<GenericValue> shippingMethods = delegator.findByAnd("ProductStoreShipmentMethView", UtilMisc.toMap("productStoreId", productStoreId), UtilMisc.toList("sequenceNumber"), true);

		//create a new cloned list of the returned results
		List<GenericValue> finalShippingMethods = new LinkedList(shippingMethods);

		//iterate throught the returned results and remove any invalid rows from the cloned list
		if(UtilValidate.isNotEmpty(shippingMethods)) {
			boolean isUspsAddress = isUspsAddress(postalAddress);
			boolean isPriorityMailable = isPriorityMailable(postalAddress);
			boolean isCanadianPostalCode = isCanadianPostalCode(postalAddress.get("countryGeoId"));
			boolean isUSTerritory = isUSTerritory(postalAddress.get("postalCode"));
			boolean isInternationalAddress = isInternationalAddress(postalAddress);
			boolean isSurePostable = isSurePostable(postalAddress, splitInfo);
			boolean isPickupAddress = isPickupAddress(postalAddress, shippableItemInfo);

			Iterator shippingMethodsIter = shippingMethods.iterator();
			while(shippingMethodsIter.hasNext()) {
				GenericValue shippingMethod = (GenericValue) shippingMethodsIter.next();
				String shipmentMethodTypeId = shippingMethod.getString("shipmentMethodTypeId");

				if((!EnvConstantsUtil.SHOW_INT_SHIP_METHODS && shipmentMethodTypeId.equals("INTRNL_STD")) || (EnvConstantsUtil.SHOW_INT_SHIP_METHODS && !isInternationalAddress && shipmentMethodTypeId.equals("INTRNL_STD"))) {
					finalShippingMethods.remove(shippingMethod);
					continue;
				}

				if(EnvConstantsUtil.SHOW_INT_SHIP_METHODS && isInternationalAddress && !"INTRNL_STD".equalsIgnoreCase(shipmentMethodTypeId)) {
					finalShippingMethods.remove(shippingMethod);
					continue;
				}

				if(!isSurePostable && shipmentMethodTypeId.equals("SURE_POST")) {
					finalShippingMethods.remove(shippingMethod);
					continue;
				}

				if(!isOnlySample && !isOnlySwatchBook && shipmentMethodTypeId.equals("FIRST_CLASS")) {
					finalShippingMethods.remove(shippingMethod);
					continue;
				}

				if(!isPickupAddress && shipmentMethodTypeId.equals("PICKUP")) {
					finalShippingMethods.remove(shippingMethod);
					continue;
				}

				if((isOnlySample || isOnlySwatchBook) && !shipmentMethodTypeId.equals("FIRST_CLASS") && !shipmentMethodTypeId.equals("PICKUP")) {
					finalShippingMethods.remove(shippingMethod);
					continue;
				}

				// check USPS address
				String allowUspsAddr = shippingMethod.getString("allowUspsAddr");
				String requireUspsAddr = shippingMethod.getString("requireUspsAddr");

				if("N".equals(allowUspsAddr) && isUspsAddress) {
					finalShippingMethods.remove(shippingMethod);
					continue;
				}

				if("Y".equals(requireUspsAddr) && (!isUspsAddress && !isPriorityMailable)) {
					finalShippingMethods.remove(shippingMethod);
					continue;
				}

				// check the items excluded from shipping
				String includeFreeShipping = shippingMethod.getString("includeNoChargeItems");
				if(includeFreeShipping != null && "N".equalsIgnoreCase(includeFreeShipping)) {
					if((shippableItemSizes == null || shippableItemSizes.size() == 0) && shippableTotal.compareTo(BigDecimal.ZERO) == 0) {
						finalShippingMethods.remove(shippingMethod);
						continue;
					}
				}

				// check the geos
				String includeGeoId = shippingMethod.getString("includeGeoId");
				String excludeGeoId = shippingMethod.getString("excludeGeoId");
				if((includeGeoId != null && includeGeoId.length() > 0) || (excludeGeoId != null && excludeGeoId.length() > 0)) {
					if (postalAddress == null) {
						finalShippingMethods.remove(shippingMethod);
						continue;
					}
				}
				if(includeGeoId != null && includeGeoId.length() > 0) {
					List includeGeoGroup = GeoWorker.expandGeoGroup(includeGeoId, delegator);
					if (!GeoWorker.containsGeo(includeGeoGroup, postalAddress.get("countryGeoId"), delegator) && !GeoWorker.containsGeo(includeGeoGroup, postalAddress.get("stateProvinceGeoId"), delegator) && !GeoWorker.containsGeo(includeGeoGroup, postalAddress.get("postalCodeGeoId"), delegator)) {
						finalShippingMethods.remove(shippingMethod);
						continue;
					}
				}
				if(excludeGeoId != null && excludeGeoId.length() > 0) {
					List excludeGeoGroup = GeoWorker.expandGeoGroup(excludeGeoId, delegator);
					if (GeoWorker.containsGeo(excludeGeoGroup, postalAddress.get("countryGeoId"), delegator) || GeoWorker.containsGeo(excludeGeoGroup, postalAddress.get("stateProvinceGeoId"), delegator) ||  GeoWorker.containsGeo(excludeGeoGroup, postalAddress.get("postalCodeGeoId"), delegator)) {
						finalShippingMethods.remove(shippingMethod);
						continue;
					}
				}

				//check canada or us territory
				/*if(!isCanadianPostalCode && !isUSTerritory && includeGeoId != null && includeGeoId.equals("CAN")) {
					finalShippingMethods.remove(shippingMethod);
					continue;
				}*/

				//check if its small flat ratable, if the shipmentMethodTypeId = SMALL_FLAT_RATE & the state is of a select list, lets get
				if(shipmentMethodTypeId.equals("SMALL_FLAT_RATE") && UtilValidate.isNotEmpty(postalAddress.get("stateProvinceGeoId")) && !(postalAddress.get("stateProvinceGeoId")).equals("__BLANK__")) {
					Map isUSPSSmallFlatRatable = null;
					Map uspsSmallFlatRateMap = new HashMap();
					uspsSmallFlatRateMap.put("stateProvinceGeoId", postalAddress.get("stateProvinceGeoId"));
					uspsSmallFlatRateMap.put("shippableItemInfo", shippableItemInfo);
					isUSPSSmallFlatRatable = dispatcher.runSync("isUSPSSmallFlatRatable", uspsSmallFlatRateMap);
					if(UtilValidate.isNotEmpty(isUSPSSmallFlatRatable) && isUSPSSmallFlatRatable.containsKey("isFlatRatable")) {
						boolean isFlatRatable = ((Boolean)isUSPSSmallFlatRatable.get("isFlatRatable")).booleanValue();
						if(!isFlatRatable && shipmentMethodTypeId.equals("SMALL_FLAT_RATE")) {
							finalShippingMethods.remove(shippingMethod);
							continue;
						}
					}
				} else if(shipmentMethodTypeId.equals("SMALL_FLAT_RATE") && UtilValidate.isEmpty(postalAddress.get("stateProvinceGeoId"))) {
					finalShippingMethods.remove(shippingMethod);
					continue;
				}
			}
		}

		return finalShippingMethods;
	}

	/*
	 * Get all the shipping methods and cost
	 */
	@SuppressWarnings("unchecked")
	public static Map<GenericValue, BigDecimal> getEstimates(Delegator delegator, List splitInfo, List shippingMethods, ShoppingCart cart, Map<String, String> postalAddress, boolean isOnlySample, boolean isOnlySwatchBook) throws GenericEntityException, Exception {
		boolean hasFreeShipItem = CartHelper.hasFreeShipItem(cart);
		String freeShipMethod = UtilProperties.getPropertyValue("envelopes", "shipment.quotes.ship.method");

		//certain locations do not allow for free shipping
		if(UtilValidate.isNotEmpty(postalAddress.get("stateProvinceGeoId")) && EnvConstantsUtil.FREE_SHIP_PROV_EXCL.contains(postalAddress.get("stateProvinceGeoId"))) {
			hasFreeShipItem = false;
			freeShipMethod = "_NA_";
		} else if(UtilValidate.isNotEmpty(postalAddress.get("countryGeoId")) && EnvConstantsUtil.FREE_SHIP_COUNTRY_EXCL.contains(postalAddress.get("countryGeoId"))) {
			hasFreeShipItem = false;
			freeShipMethod = "_NA_";
		}

		boolean getUPSRates = UtilProperties.getPropertyAsBoolean("envelopes", "shipment.get.real.time", false);
		Map<GenericValue, BigDecimal> allShippingEstimates = new HashMap<>();

		//get the zone
		List<GenericValue> shippingZones = getShippingZones(delegator, postalAddress.get("postalCode"));

		Map<String, BigDecimal> upsRates = new HashMap<>(); //map to hold ups rates if using ups
		boolean tryUPSRates = true;

		//loop through the available shipping methods and get each cost
		if(UtilValidate.isNotEmpty(shippingMethods) && UtilValidate.isNotEmpty(splitInfo)) {
			Iterator shipMethodIter = shippingMethods.iterator();
			while(shipMethodIter.hasNext()) {
				GenericValue shippingMethod = (GenericValue) shipMethodIter.next();
				String shipmentMethodTypeId = shippingMethod.getString("shipmentMethodTypeId");

				BigDecimal shipMethodTotalCost = BigDecimal.ZERO;
				BigDecimal totalWeight = BigDecimal.ZERO;
				BigDecimal totalPartialWeight = BigDecimal.ZERO;
				List<BigDecimal> packages = new ArrayList<>();

				//SMALL_FLAT_RATE is always a flat rate fee, no need to calculate
				if(shipmentMethodTypeId.equals("SMALL_FLAT_RATE")) {
					String uspsSmallFlatRate = UtilProperties.getPropertyValue("envelopes", "shipment.usps.smallflatrate.value");
					BigDecimal flatRate = new BigDecimal(uspsSmallFlatRate);
					shipMethodTotalCost = flatRate;
				} else if(shipmentMethodTypeId.equals("PICKUP") || shipmentMethodTypeId.equals("FIRST_CLASS")) {
					shipMethodTotalCost = BigDecimal.ZERO;
				} else {
					//we get rates for US Territories from UPS directly
					if(!isInternationalAddress(postalAddress) && ((UtilValidate.isNotEmpty(postalAddress.get("stateProvinceGeoId")) && EnvConstantsUtil.US_TERRITORIES.contains(postalAddress.get("stateProvinceGeoId"))) || (UtilValidate.isNotEmpty(postalAddress.get("postalCode")) && isUSTerritory(postalAddress.get("postalCode"))))) {
						getUPSRates = true;
					}

					Iterator splitInfoIter = splitInfo.iterator();
					while(splitInfoIter.hasNext()) {
						Map<String, BigDecimal> cartInfoList = (HashMap<String, BigDecimal>) splitInfoIter.next();

						BigDecimal wholePieces = cartInfoList.get("wholePieces");
						BigDecimal wholeWeight = cartInfoList.get("wholeWeight");
						BigDecimal partialWeight = cartInfoList.get("partialWeight");
						BigDecimal overrideShipCost = cartInfoList.get("overrideShipCost");

						if(freeShipMethod.contains(shipmentMethodTypeId) && overrideShipCost.compareTo(BigDecimal.ZERO) == 0) {
							//do not tally up partial/total weight for free item because its free and no need to do any calculation on it
						} else {
							totalWeight = totalWeight.add(wholeWeight).add(partialWeight);
							totalPartialWeight = totalPartialWeight.add(partialWeight);
						}

						//get the weight break for the whole pieces if there are any
						if(wholePieces.compareTo(BigDecimal.ZERO) > 0) {
							if(getUPSRates) {
								for(int i = 1; i <= wholePieces.intValue(); i++) {
									packages.add(wholeWeight);
								}
							} else {
								shipMethodTotalCost = (freeShipMethod.contains(shipmentMethodTypeId) && overrideShipCost.compareTo(BigDecimal.ZERO) == 0) ? BigDecimal.ZERO : shipMethodTotalCost.add(getShippingEstimate(delegator, shippingMethod, shippingZones, wholeWeight, wholePieces));
							}
						}
					}

					//get the weightbreak and shipmentcostestimate for the partial weight left over
					if(totalPartialWeight.compareTo(BigDecimal.ZERO) > 0) {
						if(getUPSRates) {
							packages.add(totalPartialWeight);
						} else {
							shipMethodTotalCost = shipMethodTotalCost.add(getShippingEstimate(delegator, shippingMethod, shippingZones, totalPartialWeight, new BigDecimal(1)));
						}
					}

					if(getUPSRates) {
						if (UtilValidate.isEmpty(upsRates) && tryUPSRates) {
							upsRates = UPSHelper.getRates(postalAddress, packages, shippingMethod);
							tryUPSRates = false;
						}
						BigDecimal rate = upsRates.get(shippingMethod.getString("shipmentMethodTypeId"));
						shipMethodTotalCost = rate == null ? BigDecimal.ZERO : rate;
					}
				}

				//get any associated surchages (do not do surcharges for real time ups rates
				if(!getUPSRates) {
					if (shipMethodTotalCost.compareTo(BigDecimal.ZERO) > 0) {
						shipMethodTotalCost = getShippingSurcharges(shippingMethod, postalAddress, shipMethodTotalCost, totalWeight);
					}
				}
				if (allShippingEstimates.containsKey(shippingMethod)) {
					allShippingEstimates.put(shippingMethod, (allShippingEstimates.get(shippingMethod)).add((shipMethodTotalCost)));
				} else {
					allShippingEstimates.put(shippingMethod, (shipMethodTotalCost));
				}
			}
		}

		//a list of all zero shipping methods
		ArrayList zeroEstimatesList = new ArrayList();

		//check to see if both NEXT_DAY_SAVER and NEXT_DAY_AIR are available, if so, NEXT_DAY_AIR gets a surcharge
		boolean hasAirSaver = false;
		boolean hasAir = false;
		BigDecimal airSaverCost = BigDecimal.ZERO;
		BigDecimal airCost = BigDecimal.ZERO;
		GenericValue nextDayAirSaver = null;
		GenericValue nextDayAir = null;

		Iterator estimateIter = allShippingEstimates.keySet().iterator();
		while(estimateIter.hasNext()) {
			GenericValue shipMethod = (GenericValue) estimateIter.next();
			BigDecimal value = allShippingEstimates.get(shipMethod);
			if((value == null || value.compareTo(BigDecimal.ZERO) == 0) && !(shipMethod.getString("shipmentMethodTypeId")).equals("PICKUP")) {
				if((isOnlySample || isOnlySwatchBook) && shipMethod.getString("shipmentMethodTypeId").equals("FIRST_CLASS")) {
					//if its only sample or swatchbook we will keep it
				} else if(hasFreeShipItem && freeShipMethod.contains(shipMethod.getString("shipmentMethodTypeId"))) {
					//if its a quote and service is ground/surepost we will keep it
				} else {
					zeroEstimatesList.add(shipMethod);
				}
			}
			if(shipMethod.getString("shipmentMethodTypeId").equals("NEXT_DAY_SAVER") && value.compareTo(BigDecimal.ZERO) > 0) {
				hasAirSaver = true;
				nextDayAirSaver = shipMethod;
				airSaverCost = value;
			} else if(shipMethod.getString("shipmentMethodTypeId").equals("NEXT_DAY_AIR") && value.compareTo(BigDecimal.ZERO) > 0) {
				hasAir = true;
				nextDayAir = shipMethod;
				airCost = value;
			}
		}

		//this surcharge is done here because we need to find out if both ship air and air saver have values
		if(hasAirSaver && hasAir) {
			String airSurcharge = UtilProperties.getPropertyValue("envelopes", "shipment.ups.air.surcharge.value");
			if(airSurcharge != null) {
				airCost = airCost.add(new BigDecimal(airSurcharge));
			}
			allShippingEstimates.put(nextDayAir, airCost);
		}

		//we want to remove zero estimates only if there are other valid ones
		if(zeroEstimatesList.size() < allShippingEstimates.size()) {
			Iterator zeroEstIter = zeroEstimatesList.iterator();
			while(zeroEstIter.hasNext()) {
				GenericValue est = (GenericValue) zeroEstIter.next();
				allShippingEstimates.remove(est);
			}
		}

		//now lets remove any duplicate shipping methods that would show up if showGenericShipping is set to Y
		if(EnvConstantsUtil.SHOW_GENERIC_SHIP_NAMES) {
			//boolean values to determine if the list of shipping methods have both 2day and 3day because we only want to show 3 day if both are available
			boolean has3Day = false;
			boolean has2Day = false;
			GenericValue threeDayGV = null;
			GenericValue twoDayGV = null;

			//boolean values to determine if the list of shipping methods have usps small flat rate and ups ground because we only want to show usps flat rate if both are available
			boolean hasSmallFlatRate = false;
			BigDecimal smallFlatRateFee = BigDecimal.ZERO;
			boolean hasGround = false;
			BigDecimal groundFee = BigDecimal.ZERO;
			boolean hasSurePost = false;
			BigDecimal surepostFee = BigDecimal.ZERO;
			GenericValue smallFlatRateGV = null;
			GenericValue upsGroundGV = null;
			GenericValue surepostGV = null;

			estimateIter = allShippingEstimates.keySet().iterator();
			while(estimateIter.hasNext()) {
				GenericValue est = (GenericValue) estimateIter.next();
				String estMethod = est.getString("shipmentMethodTypeId");

				if(estMethod.equals("THREE_DAY_SELECT")) {
					has3Day = true;
					threeDayGV = est;
				} else if(estMethod.equals("SECOND_DAY_AIR")) {
					has2Day = true;
					twoDayGV = est;
				} else if(estMethod.equals("GROUND")) {
					hasGround = true;
					upsGroundGV = est;
					groundFee = (BigDecimal) allShippingEstimates.get(est);
				} else if(estMethod.equals("SURE_POST")) {
					hasSurePost = true;
					surepostGV = est;
					surepostFee = (BigDecimal) allShippingEstimates.get(est);
				} else if(estMethod.equals("SMALL_FLAT_RATE")) {
					hasSmallFlatRate = true;
					smallFlatRateGV = est;
					smallFlatRateFee = (BigDecimal) allShippingEstimates.get(est);
				}
			}

			//now lets remove the dups
			if(has3Day && has2Day) {
				//if both 3day and 2day are available, lets remove 2day
				//Debug.logInfo("Removing UPS Second-Day Air from list because UPS 3-Day Select is available", module);
				allShippingEstimates.remove(twoDayGV);

				//if both 3day and 2day are available, lets remove 3day
				//Debug.logInfo("Removing UPS Three-Day Air from list because UPS 3-Day Select is available", module);
				//allShippingEstimates.remove(threeDayGV);
			}
			//if both small flat rate and ground are available, lets remove one or the other
			boolean removedGround = false;
			boolean removedSmallFlatRate = false;
			if(hasSmallFlatRate && hasGround) {
				if(smallFlatRateFee == null) {
					smallFlatRateFee = BigDecimal.ZERO;
				}
				if(groundFee == null) {
					groundFee = BigDecimal.ZERO;
				}
				if(groundFee.compareTo(BigDecimal.ZERO) <= 0 && smallFlatRateFee.compareTo(BigDecimal.ZERO) <= 0) {
					Debug.log("Removing USPS Small Flat Rate from list because both are 0", module);
					allShippingEstimates.remove(smallFlatRateGV);
					removedSmallFlatRate = true;
				} else if(groundFee.compareTo(BigDecimal.ZERO) > 0 && smallFlatRateFee.compareTo(BigDecimal.ZERO) > 0) {
					Debug.log("Removing UPS Ground from list because both have values", module);
					allShippingEstimates.remove(upsGroundGV);
					removedGround = true;
				} else if(groundFee.compareTo(BigDecimal.ZERO) > 0 && smallFlatRateFee.compareTo(BigDecimal.ZERO) <= 0) {
					Debug.log("Removing USPS Small Flat Rate from list because it is 0", module);
					allShippingEstimates.remove(smallFlatRateGV);
					removedSmallFlatRate = true;
				} else if(groundFee.compareTo(BigDecimal.ZERO) <= 0 && smallFlatRateFee.compareTo(BigDecimal.ZERO) > 0) {
					Debug.log("Removing UPS Ground from list because it is 0", module);
					allShippingEstimates.remove(upsGroundGV);
					removedGround = true;
				}
			}

			//ground & surepost rules
			if(removedGround) {
				allShippingEstimates.remove(surepostGV); //if ground has been removed prior, remove surepost as well
			} else if(!removedGround && hasSurePost) {
				allShippingEstimates.remove(upsGroundGV); //if both ground and surepost are available, remove ground
			}
		}

		return allShippingEstimates;
	}

	/*
	 * Get the shipping cost for a given shipping method
	 */
	public static BigDecimal getShippingEstimate(Delegator delegator, GenericValue shippingMethod, List<GenericValue> shippingZones, BigDecimal weight, BigDecimal cartons) throws GenericEntityException {
		BigDecimal shipMethodCost = BigDecimal.ZERO;

		List<GenericValue> filteredZones = EntityUtil.filterByAnd(shippingZones, UtilMisc.toMap("shipmentMethodTypeId", shippingMethod.getString("shipmentMethodTypeId")));

		List<EntityCondition> conditions = new ArrayList<>();
		conditions.add(EntityCondition.makeCondition("quantityBreakTypeId", EntityOperator.EQUALS, "SHIP_WEIGHT"));
		conditions.add(EntityCondition.makeCondition("thruQuantity", EntityOperator.GREATER_THAN_EQUAL_TO, weight));
		conditions.add(EntityCondition.makeCondition("fromQuantity", EntityOperator.LESS_THAN_EQUAL_TO, weight));
		EntityCondition condition = EntityCondition.makeCondition(conditions, EntityOperator.AND);
		GenericValue weightBreak = EntityUtil.getFirst(delegator.findList("QuantityBreak", condition, null, null, null, true));

		GenericValue shipmentEstimate = null;
		if(UtilValidate.isNotEmpty(weightBreak) && UtilValidate.isNotEmpty(filteredZones)) {
			List<GenericValue> shipmentEstimates = delegator.findByAnd("ShipmentCostEstimate", UtilMisc.toMap("weightBreakId", weightBreak.getString("quantityBreakId")), null, true);
			//get only the estimates for the zones we want, within the shipmentMethodTypeId and partyId
			List<EntityCondition> filterExprs = new ArrayList<>();
			filterExprs.add(EntityCondition.makeCondition("zone", EntityOperator.IN, EntityUtil.getFieldListFromEntityList(filteredZones, "zone", true)));
			filterExprs.add(EntityCondition.makeCondition("shipmentMethodTypeId", EntityOperator.EQUALS, shippingMethod.getString("shipmentMethodTypeId")));
			filterExprs.add(EntityCondition.makeCondition("carrierPartyId", EntityOperator.EQUALS, shippingMethod.getString("partyId")));
			filterExprs.add(EntityCondition.makeCondition("carrierRoleTypeId", EntityOperator.EQUALS, "CARRIER"));
			shipmentEstimate = EntityUtil.getFirst(EntityUtil.filterByAnd(shipmentEstimates, filterExprs));
		} else {
			//Debug.logError("Could not get ShipmentCostEstimage because QuantityBreak was empty for: " + weight, module);
		}

		//get the cost per pound based on the shipmentcostestimate and then add it up only for the whole cartons
		if(UtilValidate.isNotEmpty(shipmentEstimate) && UtilValidate.isNotEmpty(shipmentEstimate.get("weightUnitPrice"))) {
			shipMethodCost = (weight.compareTo(new BigDecimal("1")) < 0) ? new BigDecimal("1").multiply(shipmentEstimate.getBigDecimal("weightUnitPrice")).multiply(cartons).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING)
					: weight.multiply(shipmentEstimate.getBigDecimal("weightUnitPrice")).multiply(cartons).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);
		} else {
			//Debug.logInfo("No ShipmentCostEstimate was found after filtering!", module);
		}

		return shipMethodCost;
	}

	/*
	 * Get all the shipping surcharges
	 */
	public static BigDecimal getShippingSurcharges(GenericValue shippingMethod, Map<String, String> postalAddress, BigDecimal shipMethodTotalCost, BigDecimal totalWeight) {
		String shipmentMethodTypeId = shippingMethod.getString("shipmentMethodTypeId");

		boolean addSurcharge = true;
		if(shipmentMethodTypeId.equals("SMALL_FLAT_RATE")) {
			addSurcharge = false;
		} else if(shipmentMethodTypeId.equals("INTRNL_STD")) {
			addSurcharge = false;
		} else if(shipmentMethodTypeId.equals("PICKUP")) {
			addSurcharge = false;
		} else if(shipmentMethodTypeId.equals("FIRST_CLASS")) {
			addSurcharge = false;
		}

		if(addSurcharge) {
			String fuelSurcharge = null;
			//if(shipmentMethodTypeId.equals("GROUND") || shipmentMethodTypeId.equals("THREE_DAY_SELECT") || shipmentMethodTypeId.equals("SECOND_DAY_AIR") ){
			if(shipmentMethodTypeId.equals("GROUND") || shipmentMethodTypeId.equals("SURE_POST")) {
				fuelSurcharge = UtilProperties.getPropertyValue("envelopes", "shipment.ups.fuel.surcharge.ground.percent");
			} else {
				fuelSurcharge = UtilProperties.getPropertyValue("envelopes", "shipment.ups.fuel.surcharge.air.percent");
			}

			if(fuelSurcharge != null){
				shipMethodTotalCost = shipMethodTotalCost.add(shipMethodTotalCost.multiply(new BigDecimal(fuelSurcharge)).divide(new BigDecimal(100), EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			}

			String addnlSurcPermissibleWt = UtilProperties.getPropertyValue("envelopes", "shipment.ups.additional.handling.permissible.weight");
			if(UtilValidate.isNotEmpty(addnlSurcPermissibleWt) && totalWeight.compareTo(new BigDecimal(addnlSurcPermissibleWt)) > 0) {
				String addnHandSurcharge = UtilProperties.getPropertyValue("envelopes", "shipment.ups.additional.handling.surcharge");
				if(UtilValidate.isNotEmpty(addnHandSurcharge)) {
					shipMethodTotalCost = shipMethodTotalCost.add(new BigDecimal(addnHandSurcharge));
				}
			}

			//add the shipment.ups.additional.handling.surcharge.percent.canada to the estimate
			if((UtilValidate.isNotEmpty(postalAddress.get("countryGeoId")) && postalAddress.get("countryGeoId").equals("CAN"))) {
				String addnHandCanSurcharge = UtilProperties.getPropertyValue("envelopes", "shipment.ups.additional.handling.surcharge.percent.canada");
				if(UtilValidate.isNotEmpty(addnHandCanSurcharge)) {
					shipMethodTotalCost = shipMethodTotalCost.add(shipMethodTotalCost.multiply(new BigDecimal(addnHandCanSurcharge)).divide(new BigDecimal(100), EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
				}
			}

			String surcharge = UtilProperties.getPropertyValue("envelopes", "shipment.default.surcharge.value");
			if(surcharge != null) {
				shipMethodTotalCost = shipMethodTotalCost.add(new BigDecimal(surcharge));
			}

			//2018-03-27, ADDED 5% OVERALL UPCHARGE TO GROUND AND 3DAY
			if(shipmentMethodTypeId.equals("GROUND") || shipmentMethodTypeId.equals("SURE_POST") || shipmentMethodTypeId.equals("THREE_DAY_SELECT")) {
				shipMethodTotalCost = shipMethodTotalCost.add(shipMethodTotalCost.multiply(new BigDecimal("5")).divide(new BigDecimal(100), EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			}

		}

		return shipMethodTotalCost;
	}

	/*
	 * loop through the cart, get the item, its number of packages based on the carton qty
	 */
	public static List getSplitPackageInfo(Delegator delegator, ShoppingCart cart, BigDecimal maxCartonWeight) throws GenericEntityException {
		List cartInfoList = new ArrayList();

		List cartItems = cart.items();
		Iterator cartItemIter = cartItems.iterator();
		while(cartItemIter.hasNext()) {
			ShoppingCartItem cartItem = (ShoppingCartItem) cartItemIter.next();

			boolean isFreeShipItemOrQuote = CartHelper.isFreeShipItem(cartItem) || CartHelper.isQuote(cartItem);
			cartInfoList.add(generateWeightMap(delegator, cartItem, maxCartonWeight, null, null, isFreeShipItemOrQuote));
		}

		return cartInfoList;
	}

	/*
	 * Generate a map of all the item weights
	 */
	public static Map<String, BigDecimal> generateWeightMap(Delegator delegator, ShoppingCartItem cartItem, BigDecimal maxCartonWeight, BigDecimal quantityFinal, BigDecimal weightFinal, boolean ignoreWeight) throws GenericEntityException {
		GenericValue product = cartItem.getProduct();
		Map itemInfo = cartItem.getItemProductInfo();


		//carton qty
		BigDecimal cartonQty = BigDecimal.ZERO;
		if(UtilValidate.isNotEmpty(product.get("cartonQty"))) {
			cartonQty = new BigDecimal(product.getLong("cartonQty").longValue());
		} else {
			Map<String, String> cartonQtyValue = ProductHelper.getProductFeatures(delegator, product, UtilMisc.toList("CARTON_QUANTITY"));
			if(UtilValidate.isNotEmpty(cartonQtyValue)) {
				cartonQty = new BigDecimal((String) cartonQtyValue.get("CARTON_QUANTITY"));
				//itemInfo.put("cartonQty", cartonQty);
			}
		}

		//cart qty
		BigDecimal quantity = CartHelper.getCartItemQuantity(cartItem);
		BigDecimal originalWeight = ((BigDecimal) itemInfo.get("weight")).multiply(quantity);
		BigDecimal weightPerUnit = (BigDecimal) itemInfo.get("weight");
		BigDecimal wholePieces = BigDecimal.ZERO;
		BigDecimal wholeWeight = BigDecimal.ZERO;
		BigDecimal partialPieces = BigDecimal.ZERO;
		BigDecimal partialWeight = BigDecimal.ZERO;

		if (cartonQty.compareTo(BigDecimal.ZERO) > 0 && quantity.compareTo(cartonQty) > 0) {
			wholePieces = BigDecimal.valueOf(Math.floor(quantity.doubleValue() / cartonQty.doubleValue()));
			partialPieces = quantity.remainder(cartonQty);
			if (partialPieces.compareTo(BigDecimal.ZERO) == 0) {
				partialWeight = partialPieces.multiply(weightPerUnit);
				wholeWeight = originalWeight.subtract(partialWeight);
				wholeWeight = BigDecimal.valueOf(Math.ceil(wholeWeight.doubleValue() / wholePieces.doubleValue()));
			} else {
				wholeWeight = BigDecimal.valueOf(Math.ceil(originalWeight.doubleValue() / wholePieces.doubleValue()));
			}
		} else if (cartonQty.compareTo(BigDecimal.ZERO) == 0 && originalWeight.compareTo(maxCartonWeight) > 0) {
			wholePieces = BigDecimal.valueOf(Math.floor(originalWeight.doubleValue() / maxCartonWeight.doubleValue()));
			partialPieces = originalWeight.remainder(maxCartonWeight);
			if (partialPieces.compareTo(BigDecimal.ZERO) == 0) {
				partialWeight = partialPieces.multiply(weightPerUnit);
				wholeWeight = originalWeight.subtract(partialWeight);
				wholeWeight = BigDecimal.valueOf(Math.ceil(wholeWeight.doubleValue() / wholePieces.doubleValue()));
			} else {
				wholeWeight = BigDecimal.valueOf(Math.ceil(originalWeight.doubleValue() / wholePieces.doubleValue()));
			}
		} else {
			partialPieces = quantity;
			partialWeight = originalWeight;
		}

		Map<String, BigDecimal> itemData = new HashMap<String, BigDecimal>();
		itemData.put("quantity", quantity);
		itemData.put("originalWeight", originalWeight);
		itemData.put("weightPerUnit", weightPerUnit);
		itemData.put("wholePieces", wholePieces);
		itemData.put("wholeWeight", wholeWeight);
		itemData.put("partialPieces", partialPieces);
		itemData.put("partialWeight", partialWeight);
		itemData.put("cartonQty", cartonQty);
		itemData.put("overrideShipCost", ignoreWeight ? BigDecimal.ZERO : BigDecimal.ONE);

		return itemData;
	}

	/*
	 * Check to see if address is international address
	 */
	public static boolean isInternationalAddress(Map<String, String> postalAddress) {
		if(UtilValidate.isNotEmpty(postalAddress) && UtilValidate.isNotEmpty(postalAddress.get("countryGeoId"))) {
			if(UtilValidate.isNotEmpty(postalAddress.get("countryGeoId")) && !"USA".equalsIgnoreCase(postalAddress.get("countryGeoId")) && !"CAN".equalsIgnoreCase(postalAddress.get("countryGeoId"))) {
				return true;
			}
		}

		return false;
	}

	/*
	 * Check to see if address is USPS address
	 */
	public static boolean isUspsAddress(Map<String, String> postalAddress) {
		if (postalAddress == null) {
			// null postal address is not a USPS address
			return false;
		}

		// get and clean the address strings
		String addr1 = postalAddress.get("address1");
		String addr2 = postalAddress.get("address2");
		String city = postalAddress.get("city");
		String countryGeoId = postalAddress.get("countryGeoId");

		if(UtilValidate.isNotEmpty(countryGeoId) && !countryGeoId.equals("USA")) {
			return false;
		}

		// get the matching string from general.properties, this is checking if the address is a PO BOX
		String matcher = UtilProperties.getPropertyValue("general.properties", "usps.address.match");
		if (UtilValidate.isNotEmpty(matcher)) {
			if (addr1 != null && addr1.toLowerCase().matches(matcher)) {
				return true;
			}
			if (addr2 != null && addr2.toLowerCase().matches(matcher)) {
				return true;
			}
		}

		//now lets see if the city is a APO, FPO, DPO, if so then it is also considered USPS
		if(UtilValidate.isNotEmpty(city)) {
			city = city.trim().toLowerCase();
			if(city.endsWith("apo") || city.endsWith("fpo") || city.endsWith("dpo")) {
				return true;
			}
		}

		return false;
	}

	/*
	 * Check to see if the address is priority mailable
	 */
	public static boolean isPriorityMailable(Map<String, String> postalAddress) {
		if (postalAddress == null) {
			// null postal address is not a USPS address
			return false;
		}

		// get and clean the address strings
		String stateProvinceGeoId = postalAddress.get("stateProvinceGeoId");
		String validGeoIDs = UtilProperties.getPropertyValue("envelopes.properties", "shipment.priority.mailable");

		//now lets see if the states are withing certain params
		if(UtilValidate.isNotEmpty(stateProvinceGeoId) && validGeoIDs.contains(stateProvinceGeoId)) {
			return true;
		}

		return false;
	}

	/*
	 * Check to see if the address is priority mailable
	 */
	public static boolean isSurePostable(Map<String, String> postalAddress, List splitInfo) {
		if (UtilValidate.isEmpty(postalAddress) || (UtilValidate.isNotEmpty(postalAddress) && UtilValidate.isEmpty(postalAddress.get("countryGeoId")))) {
			return false;
		}

		BigDecimal totalWeight = getTotalCartWeight(splitInfo);
		if (totalWeight.compareTo(new BigDecimal("9")) >= 0) {
			return false;
		}

		//now lets see if the states are withing certain params
		if ("RESIDENTIAL_LOCATION".equalsIgnoreCase(postalAddress.get("shipTo")) && "USA".equalsIgnoreCase(postalAddress.get("countryGeoId"))) {
			return true;
		}

		return false;
	}

	public static boolean isSmartPostable(Map<String, String> postalAddress, List splitInfo) {
		if (UtilValidate.isEmpty(postalAddress) || (UtilValidate.isNotEmpty(postalAddress) && UtilValidate.isEmpty(postalAddress.get("countryGeoId")))) {
			return false;
		}

		BigDecimal totalWeight = getTotalCartWeight(splitInfo);
		if (totalWeight.compareTo(new BigDecimal("70")) > 0) {
			return false;
		}

		//now lets see if the states are withing certain params
		if ("RESIDENTIAL_LOCATION".equalsIgnoreCase(postalAddress.get("shipTo")) && "USA".equalsIgnoreCase(postalAddress.get("countryGeoId"))) {
			return true;
		}

		return false;
	}

	public static boolean isPickupAddress(Map<String, String> postalAddress, List shippableItemInfo) {
		if (postalAddress == null) {
			return false;
		}

		// get and clean the address strings
		String stateProvinceGeoId = postalAddress.get("stateProvinceGeoId");
		String validGeoIDs = UtilProperties.getPropertyValue("envelopes.properties", "shipment.pickup.state");
		String validLocationIDs = UtilProperties.getPropertyValue("envelopes.properties", "shipment.pickup.location");

		//now see if the states are withing certain params
		if(UtilValidate.isNotEmpty(stateProvinceGeoId) && validGeoIDs.contains(stateProvinceGeoId)) {
			//now check if all items are in valid location
			for (int i = 0; i < shippableItemInfo.size(); i++) {
				Map<String, Object> info = (Map<String, Object>) shippableItemInfo.get(i);
				if(UtilValidate.isNotEmpty(info.get("location")) && !validLocationIDs.contains((String) info.get("location"))) {
					return false;
				}
			}

			return true;
		}

		return false;
	}

	//this is the same as sortHashMapByValues inside StringUtil.java but adding it here because its been modified to relate to Shipping only
	//this method is to ONLY be used for the shipping map for ShippingEstimateWrapper!!!
	public static LinkedHashMap sortShipMapByValues(HashMap passedMap) {
		List mapKeys = new ArrayList(passedMap.keySet());
		List mapValues = new ArrayList(passedMap.values());

		Collections.sort(mapValues);
		Collections.sort(mapKeys);

		boolean foundValidValue = false;

		LinkedHashMap sortedMap = new LinkedHashMap();

		Iterator valueIt = mapValues.iterator();

		//first loop is for all greater then 0 values
		while (valueIt.hasNext()) {
			Object val = valueIt.next();
			BigDecimal valDb = (BigDecimal)val;
			if(valDb == null) {
				valDb = BigDecimal.ZERO;
			}
			if(valDb.compareTo(BigDecimal.ZERO) > 0) {
				Iterator keyIt = mapKeys.iterator();

				while (keyIt.hasNext()) {
					Object key = keyIt.next();
					String comp1 = passedMap.get(key).toString();
					String comp2 = val.toString();

					if (comp1.equals(comp2)){
						passedMap.remove(key);
						mapKeys.remove(key);
						sortedMap.put(key, (BigDecimal)val);
						foundValidValue = true;
						break;
					}
				}
			}
		}

		//second loop is for all 0 or less values only if there are valid values
		if(foundValidValue) {
			valueIt = mapValues.iterator();
			while (valueIt.hasNext()) {
				Object val = valueIt.next();
				BigDecimal valDb = (BigDecimal)val;
				if(valDb == null) {
					valDb = BigDecimal.ZERO;
				}
				if(valDb.compareTo(BigDecimal.ZERO) <= 0) {
					Iterator keyIt = mapKeys.iterator();

					while (keyIt.hasNext()) {
						Object key = keyIt.next();
						String comp1 = passedMap.get(key).toString();
						String comp2 = val.toString();

						if (comp1.equals(comp2)){
							passedMap.remove(key);
							mapKeys.remove(key);
							sortedMap.put(key, (BigDecimal)val);
							break;
						}
					}
				}
			}
		} else {
			//this else statement is when all values are zero if it is, then it never made it inside any of the loops above
			//therefor we will order it based on the sequenceNumber inside the mapKeys
			Map shipMethodAndSequence = new HashMap();
			Iterator mapKeysIter = mapKeys.iterator();
			while (mapKeysIter.hasNext()) {
				GenericValue keyGV = (GenericValue) mapKeysIter.next();
				BigDecimal sequenceNumber = BigDecimal.ZERO;
				if(UtilValidate.isNotEmpty(keyGV.get("sequenceNumber"))) {
					sequenceNumber = new BigDecimal(((Long)keyGV.get("sequenceNumber")).doubleValue());
				}
				shipMethodAndSequence.put(keyGV, sequenceNumber);
			}
			//sort by sequenceNumber
			shipMethodAndSequence = sortHashMapByValues((HashMap)shipMethodAndSequence);

			//now loop through this and create the new sortedMap
			Iterator shipMethodAndSequenceIter = shipMethodAndSequence.entrySet().iterator();
			while (shipMethodAndSequenceIter.hasNext()) {
				Map.Entry pairs = (Map.Entry)shipMethodAndSequenceIter.next();
				Object pairKey = pairs.getKey();
				if(passedMap.containsKey(pairKey)) {
					sortedMap.put(pairKey, (BigDecimal)passedMap.get(pairKey));
					passedMap.remove(pairKey);
				}
			}
		}

		return sortedMap;
	}

	public static LinkedHashMap sortHashMapByValues(HashMap passedMap) {
		List mapKeys = new ArrayList(passedMap.keySet());
		List mapValues = new ArrayList(passedMap.values());
		Collections.sort(mapValues);
		Collections.sort(mapKeys);

		LinkedHashMap sortedMap = new LinkedHashMap();

		Iterator valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			Object val = valueIt.next();
			Iterator keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				Object key = keyIt.next();
				String comp1 = passedMap.get(key).toString();
				String comp2 = val.toString();

				if (comp1.equals(comp2)){
					passedMap.remove(key);
					mapKeys.remove(key);
					sortedMap.put(key, (BigDecimal)val);
					break;
				}
			}
		}
		return sortedMap;
	}

	/*
	 * Check if postal code is Canadian
	 */
	public static boolean isCanadianPostalCode(String countryGeoId) {
		return UtilValidate.isNotEmpty(countryGeoId) && "CAN".equalsIgnoreCase(countryGeoId);
	}
	public static boolean isCanadianPostalCode(Delegator delegator, String postalCode) throws GenericEntityException {
		if(UtilValidate.isEmpty(postalCode)) {
			return false;
		}

		postalCode = (postalCode.length() > 3) ? postalCode.substring(0, 3) : postalCode;
		if(EnvConstantsUtil.US_TERRITORIES_ZIP.contains(postalCode)) {
			return false;
		}

		List<GenericValue> shippingZones = getShippingZones(delegator, postalCode);
		if(UtilValidate.isEmpty(shippingZones)){
			return false;
		}

		Iterator zonesIter = shippingZones.iterator();

		while (zonesIter.hasNext()) {
			GenericValue zone = (GenericValue) zonesIter.next();

			String shipMethTypeId = zone.getString("shipmentMethodTypeId");
			if (!shipMethTypeId.equals("STANDARD") && !shipMethTypeId.equals("WORLDWIDE_EXPTD") && !shipMethTypeId.equals("WORLDWIDE_EXPR")) {
				return false;
			}
		}
		return true;
	}

	/*
	 * Check if postal code is Canadian
	 */
	public static boolean isUSTerritory(String postalCode) {
		if(UtilValidate.isEmpty(postalCode)) {
			return false;
		}

		postalCode = (postalCode.length() > 3) ? postalCode.substring(0, 3) : postalCode;
		if(!EnvConstantsUtil.US_TERRITORIES_ZIP.contains(postalCode)) {
			return false;
		}

		return true;
	}
	public static String getUSTerritoryGeoId(String postalCode) {
		String geoId = null;
		if(UtilValidate.isEmpty(postalCode)) {
			return null;
		}

		postalCode = (postalCode.length() > 3) ? postalCode.substring(0, 3) : postalCode;
		if(EnvConstantsUtil.US_TERRITORIES_ZIP.contains(postalCode)) {
			switch (postalCode) {
				case "006":
					geoId = "PR";
					break;
				case "007":
					geoId = "PR";
					break;
				case "008":
					geoId = "VI";
					break;
				case "009":
					geoId = "PR";
					break;
				case "340":
					geoId = "AA";
					break;
				case "090":
					geoId = "AE";
					break;
				case "091":
					geoId = "AE";
					break;
				case "092":
					geoId = "AE";
					break;
				case "093":
					geoId = "AE";
					break;
				case "094":
					geoId = "AE";
					break;
				case "095":
					geoId = "AE";
					break;
				case "096":
					geoId = "AE";
					break;
				case "097":
					geoId = "AE";
					break;
				case "098":
					geoId = "AE";
					break;
				case "099":
					geoId = "AE";
					break;
				case "962":
					geoId = "AP";
					break;
				case "963":
					geoId = "AP";
					break;
				case "964":
					geoId = "AP";
					break;
				case "965":
					geoId = "AP";
					break;
				case "966":
					geoId = "AP";
					break;
				case "969":
					geoId = "GU";
					break;
				default:
					geoId = null;
					break;
			}
		}

		return geoId;
	}

	/*
	 * Get a list of all the shipping zones
	 */
	public static List<GenericValue> getShippingZones(Delegator delegator, String postalCode) throws GenericEntityException {
		List<GenericValue> shippingZones = new ArrayList();

		if(UtilValidate.isEmpty(postalCode)) {
			return shippingZones;
		}

		postalCode = (postalCode.length() > 3) ? postalCode.substring(0, 3) : postalCode;
		List<EntityCondition> conditions = new ArrayList<EntityCondition>();
		conditions.add(EntityCondition.makeCondition("postalCode", EntityOperator.LIKE, postalCode + "%"));
		shippingZones = delegator.findList("ZonePostalCodeLookup", EntityCondition.makeCondition(conditions, EntityOperator.AND), null, null, null, true);

		return shippingZones;
	}

	public static List<GenericValue> getFedExShippingZones(Delegator delegator, String postalCode) throws GenericEntityException {
		List<GenericValue> shippingZones = new ArrayList();
		if(UtilValidate.isEmpty(postalCode)) {
			return shippingZones;
		}
		postalCode = (postalCode.length() > 3) ? postalCode.substring(0, 3) : postalCode;
		List<EntityCondition> conditions = new ArrayList<EntityCondition>();
		conditions.add(EntityCondition.makeCondition("carrierId", EntityOperator.EQUALS, "FEDEX"));
		conditions.add(EntityCondition.makeCondition("zipCode", EntityOperator.LIKE, postalCode + "%"));
		shippingZones = delegator.findList("ShippingCarrierZoneLookup", EntityCondition.makeCondition(conditions, EntityOperator.AND), null, null, null, true);

		return shippingZones;
	}

	public static BigDecimal getTotalCartWeight(List splitInfo) {
		BigDecimal totalWeight = BigDecimal.ZERO;
		Iterator splitInfoIter = splitInfo.iterator();
		while(splitInfoIter.hasNext()) {
			Map<String, BigDecimal> cartInfoList = (HashMap<String, BigDecimal>) splitInfoIter.next();

			BigDecimal wholePieces = cartInfoList.get("wholePieces");
			BigDecimal wholeWeight = cartInfoList.get("wholeWeight");
			BigDecimal partialWeight = cartInfoList.get("partialWeight");

			totalWeight = totalWeight.add((wholePieces.compareTo(BigDecimal.ZERO) > 0) ? wholeWeight.multiply(wholePieces) : wholeWeight).add(partialWeight);
		}

		return totalWeight;
	}

	public static List<Map<String, Object>> getShippingMethods(Delegator delegator, String zipCode, BigDecimal totalProductWeight) throws GenericEntityException {
		List<EntityCondition> conditions = new ArrayList<>();
		conditions.add(EntityCondition.makeCondition("zipCode", EntityOperator.EQUALS, zipCode.substring(0,3)));
		conditions.add(EntityCondition.makeCondition("fromQuantity", EntityOperator.GREATER_THAN_EQUAL_TO, totalProductWeight));
		conditions.add(EntityCondition.makeCondition("thruQuantity", EntityOperator.LESS_THAN, totalProductWeight.add(new BigDecimal(1))));
		List<GenericValue> shippingMethodsResultList = EntityQuery.use(delegator).from("ShippingCarrierRateZoneLookupByQuantity").where(EntityCondition.makeCondition(conditions, EntityOperator.AND)).queryList();

		List<Map<String, Object>> shippingMethodsList = new ArrayList<>();

		for(GenericValue shippingMethodsResult : shippingMethodsResultList) {
			Map<String, Object> shippingMethodsMap = new HashMap<>();

			shippingMethodsMap.put("carrierId", shippingMethodsResult.get("carrierId"));
			shippingMethodsMap.put("zone", shippingMethodsResult.get("zone"));
			shippingMethodsMap.put("shippingMethodId", shippingMethodsResult.get("shippingMethodId"));
			shippingMethodsMap.put("ratePerUnit", shippingMethodsResult.get("ratePerUnit"));

			shippingMethodsList.add(shippingMethodsMap);
		}

		return shippingMethodsList;
	}
}