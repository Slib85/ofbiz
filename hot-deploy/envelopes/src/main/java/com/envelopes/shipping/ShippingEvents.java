/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.shipping;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.order.shoppingcart.ShoppingCart;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartEvents;
import org.apache.ofbiz.order.shoppingcart.product.ProductPromoWorker;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;

import com.envelopes.cart.CartHelper;
import com.envelopes.order.OrderHelper;
import com.envelopes.party.PartyHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

public class ShippingEvents {
	public static final String module = ShippingEvents.class.getName();

	private static String SHOWDATES;

	static {
		try {
			SHOWDATES = UtilProperties.getPropertyValue("envelopes", "shipment.actualdates");
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error, could not open envelopes.properties.", module);
		}
	}

	/*
	 * This service is to get shipping rates for all available carriers without having to use any party tables
	 * Ofbiz default shipping stores data into tables in order to do calculation, not necessary, we are going to generate a map of the request params and use that instead of a GV of the postaladdress
	*/
	public static String getShippingRates(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> jsonResponse = EnvConstantsUtil.IS_UPS ? getShippingRates(request) : getFedExShippingRates(request);

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		if(UtilValidate.isNotEmpty(context.get("destroyCart")) && ((String) context.get("destroyCart")).equalsIgnoreCase("Y")) {
			ShoppingCartEvents.destroyCart(request, response);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static Map<String, Object> getShippingRates(HttpServletRequest request) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);

		Map<String, String> postalAddress = PartyHelper.getPostalAddressMap(request, true);
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		Map<GenericValue, BigDecimal> estimates = new HashMap<>();
		Map<String, Object> jsonResponse = new HashMap<>();
		List<Map> jsonArray = new ArrayList<>();

		String shipmentMethodTypeId = (String) context.get("shipmentMethodTypeId");

		//lets get the productStoreId from the cart if available, cart cannot be null!
		if(cart == null) {
			jsonResponse.put("success" , false);
			jsonResponse.put("error", "The cart is null while trying to get a shipping cost!");
		} else if(UtilValidate.isEmpty(postalAddress.get("postalCode")) && UtilValidate.isEmpty(cart.getShipTaxPostalAddress())) {
			jsonResponse.put("success" , false);
			jsonResponse.put("error", "The shipping postal code is empty while trying to get a shipping cost!");
		} else {
			CartHelper.setShipTaxPostalAddress(cart, postalAddress);
			if(UtilValidate.isNotEmpty(cart.getShipTaxPostalAddress())) {
				postalAddress = cart.getShipTaxPostalAddress();
			}

			int productionTime = CartHelper.getProductionTime(delegator, cart);
			BigDecimal shippableQuantity = BigDecimal.ZERO;
			BigDecimal shippableWeight = BigDecimal.ZERO;
			BigDecimal shippableTotal = BigDecimal.ZERO;
			List shippableItemInfo = null;
			List shippableItemSizes = null;

			String productStoreId = (UtilValidate.isNotEmpty(cart.getProductStoreId())) ? cart.getProductStoreId() : "10000";
			shippableQuantity = cart.getShippableQuantity(0);
			shippableWeight = cart.getShippableWeight(0);
			shippableTotal = cart.getShippableTotal(0);
			shippableItemInfo = cart.getShippableItemInfo(0);
			shippableItemSizes = cart.getShippableSizes(0);

			jsonResponse.put("shippableQuantity", shippableQuantity);
			jsonResponse.put("shippableWeight", shippableWeight);
			jsonResponse.put("shippableTotal", shippableTotal);
			jsonResponse.put("shippableItemInfo", shippableItemInfo);
			jsonResponse.put("shippableItemSizes", shippableItemSizes);
			jsonResponse.put("showGenericShipping", EnvConstantsUtil.SHOW_GENERIC_SHIP_NAMES);

			//if no shipment method was passed in get it from cart
			if(UtilValidate.isEmpty(shipmentMethodTypeId)) {
				shipmentMethodTypeId = cart.getShipmentMethodTypeId(0);
			}
			//if its still empty set the default
			if(UtilValidate.isEmpty(shipmentMethodTypeId)) {
				CartHelper.setCartDefaults(cart, request, null, null, null);
				shipmentMethodTypeId = cart.getShipmentMethodTypeId(0);
			}

			try {
				estimates = EnvConstantsUtil.IS_UPS ? ShippingHelper.getAllShippingEstimates(delegator, dispatcher, postalAddress, cart) : ShippingHelper.getAllFedExShippingEstimates(delegator, dispatcher, postalAddress, cart);
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to retrieve shipping methods/estimates. " + e + " : " + e.getMessage(), module);
			}
			try {
				if(estimates.size() == 0 && EnvConstantsUtil.IS_FEDEX) {
					estimates = ShippingHelper.getAllShippingEstimates(delegator, dispatcher, postalAddress, cart);
				}
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to retrieve shipping methods/estimates. " + e + " : " + e.getMessage(), module);
			}

			//sort the map
			if(estimates.size() > 0) {
				jsonResponse.put("success" , true);
			} else {
				jsonResponse.put("success" , false);
				jsonResponse.put("error", "No shipping estimates found.");
			}

			GenericValue baseShipping = null;
			boolean foundShipping = false;

			Iterator estimateIter = estimates.keySet().iterator();
			while(estimateIter.hasNext()) {
				GenericValue shipMethod = (GenericValue) estimateIter.next();
				Map<String, Object> shipMap = new HashMap<String, Object>();
				String estDeliveryFrame = null;
				if("true".equalsIgnoreCase(SHOWDATES) && UtilValidate.isNotEmpty(shipMethod.getLong("minTransitTime")) && UtilValidate.isNotEmpty(shipMethod.getLong("maxTransitTime"))) {
					estDeliveryFrame = EnvConstantsUtil.WMDSHORT.format(OrderHelper.getDueDate(productionTime + shipMethod.getLong("minTransitTime").intValue(), null));
					if(shipMethod.getLong("minTransitTime").intValue() != shipMethod.getLong("maxTransitTime").intValue()) {
						estDeliveryFrame = estDeliveryFrame + " - " + EnvConstantsUtil.WMDSHORT.format(OrderHelper.getDueDate(productionTime + shipMethod.getLong("maxTransitTime").intValue(), null));
					}

					if("NEXT_DAY_AIR".equals(shipMethod.getString("shipmentMethodTypeId")) || "NEXT_DAY_AM".equals(shipMethod.getString("shipmentMethodTypeId"))) {
						estDeliveryFrame = estDeliveryFrame + " Noon";
					}

					if("PICKUP".equals(shipMethod.getString("shipmentMethodTypeId"))) {
						estDeliveryFrame = "Ready for Pickup by " + estDeliveryFrame;
					} else {
						estDeliveryFrame = "Est. Delivery by " + estDeliveryFrame;
					}
					shipMap.put("genericDesc", shipMethod.getString("genericDescription").replaceAll("\\s\\(.*?\\d.*?\\)",""));
				} else {
					shipMap.put("genericDesc", shipMethod.getString("genericDescription"));
				}

				shipMap.put("method", shipMethod.getString("shipmentMethodTypeId"));
				shipMap.put("desc", shipMethod.getString("description"));
				shipMap.put("carrierPartyId", shipMethod.getString("partyId"));
				shipMap.put("estDeliveryDate", estDeliveryFrame);
				shipMap.put("cost", (BigDecimal) estimates.get(shipMethod));

				if(baseShipping == null && ((BigDecimal) estimates.get(shipMethod)).compareTo(BigDecimal.ZERO) > 0) {
					baseShipping = shipMethod;
				}

				//set the cart shipping if we find a matching value or assign the first one
				if(UtilValidate.isNotEmpty(shipmentMethodTypeId) && shipmentMethodTypeId.equals(shipMethod.getString("shipmentMethodTypeId"))) {
					CartHelper.setShipmentMethodTypeId(dispatcher, cart, 0, shipmentMethodTypeId, shipMethod.getString("partyId"));
					CartHelper.setShippingTotal(cart, 0, (BigDecimal) estimates.get(shipMethod));
					foundShipping = true;
				}
				jsonArray.add(shipMap);
			}

			//for PPS only, add FEDEX as an option when customers want to use it to ship on their own fedex account
			if(UtilValidate.isNotEmpty(context.get("showThirdParty")) && ((String) context.get("showThirdParty")).equalsIgnoreCase("Y")) {
				jsonArray.add(UtilMisc.toMap("method", "FEDEX_THIRD_PARTY", "desc", "Fedex 3rd Party", "genericDesc", "Fedex 3rd Party", "carrierPartyId", "FEDEX", "estDeliveryDate", null, "cost", BigDecimal.ZERO));
				jsonArray.add(UtilMisc.toMap("method", "UPS_THIRD_PARTY", "desc", "UPS 3rd Party", "genericDesc", "UPS 3rd Party", "carrierPartyId", "UPS", "estDeliveryDate", null, "cost", BigDecimal.ZERO));
			}

			if(!foundShipping && baseShipping != null) {
				shipmentMethodTypeId = baseShipping.getString("shipmentMethodTypeId");
				CartHelper.setShipmentMethodTypeId(dispatcher, cart, 0, baseShipping.getString("shipmentMethodTypeId"), baseShipping.getString("partyId"));
				CartHelper.setShippingTotal(cart, 0, (BigDecimal) estimates.get(baseShipping));
			} else if(!foundShipping) {
				//no shipping found, follow this order - sample else cheapest else pickup
				if(CartHelper.isAllowedFreeShipping(cart) || CartHelper.isOnlySample(cart) || CartHelper.isOnlySwatchBook(cart)) {
					shipmentMethodTypeId = "FIRST_CLASS";
					CartHelper.setShipmentMethodTypeId(dispatcher, cart, 0, "FIRST_CLASS", "USPS");
					CartHelper.setShippingTotal(cart, 0, BigDecimal.ZERO);
				} else if(estimates.size() > 0) {
					Map.Entry<GenericValue, BigDecimal> entry = estimates.entrySet().iterator().next();
					shipmentMethodTypeId = ((GenericValue) entry.getKey()).getString("shipmentMethodTypeId");
					CartHelper.setShipmentMethodTypeId(dispatcher, cart, 0, ((GenericValue) entry.getKey()).getString("shipmentMethodTypeId"), ((GenericValue) entry.getKey()).getString("partyId"));
					CartHelper.setShippingTotal(cart, 0, (BigDecimal) entry.getValue());
				} else {
					shipmentMethodTypeId = "PICKUP";
					CartHelper.setShipmentMethodTypeId(dispatcher, cart, 0, "PICKUP", "ENVELOPES");
					CartHelper.setShippingTotal(cart, 0, BigDecimal.ZERO);
				}
			}

			jsonResponse.put("shipmentMethodTypeId", shipmentMethodTypeId);

			//recalc the tax and other promotions because shipping has been updated
			try {
				if(cart.getProductPromoCodesEntered().size() > 0 && cart.getDoPromotions()) {
					ProductPromoWorker.doPromotions(cart, dispatcher);
				}
				CartHelper.setTaxTotal(dispatcher, postalAddress, cart);
			} catch (GenericServiceException e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to retrieve tax total. " + e + " : " + e.getMessage(), module);
			}
			jsonResponse.put("estimates", jsonArray);
		}
		return jsonResponse;
	}

	public static Map<String, Object> getFedExShippingRates(HttpServletRequest request) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);

		Map<String, String> postalAddress = PartyHelper.getPostalAddressMap(request, true);
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		Map<GenericValue, BigDecimal> estimates = new HashMap<>();
		Map<String, Object> jsonResponse = new HashMap<>();
		List<Map> jsonArray = new ArrayList<>();

		String shipmentMethodTypeId = (String) context.get("shipmentMethodTypeId");

		//lets get the productStoreId from the cart if available, cart cannot be null!
		if(cart == null) {
			jsonResponse.put("success" , false);
			jsonResponse.put("error", "The cart is null while trying to get a shipping cost!");
		} else if(UtilValidate.isEmpty(postalAddress.get("postalCode")) && UtilValidate.isEmpty(cart.getShipTaxPostalAddress())) {
			jsonResponse.put("success" , false);
			jsonResponse.put("error", "The shipping postal code is empty while trying to get a shipping cost!");
		} else {
			CartHelper.setShipTaxPostalAddress(cart, postalAddress);
			if(UtilValidate.isNotEmpty(cart.getShipTaxPostalAddress())) {
				postalAddress = cart.getShipTaxPostalAddress();
			}

			int productionTime = CartHelper.getProductionTime(delegator, cart);
			BigDecimal shippableQuantity = BigDecimal.ZERO;
			BigDecimal shippableWeight = BigDecimal.ZERO;
			BigDecimal shippableTotal = BigDecimal.ZERO;
			List shippableItemInfo = null;
			List shippableItemSizes = null;

			String productStoreId = (UtilValidate.isNotEmpty(cart.getProductStoreId())) ? cart.getProductStoreId() : "10000";
			shippableQuantity = cart.getShippableQuantity(0);
			shippableWeight = cart.getShippableWeight(0);
			shippableTotal = cart.getShippableTotal(0);
			shippableItemInfo = cart.getShippableItemInfo(0);
			shippableItemSizes = cart.getShippableSizes(0);

			jsonResponse.put("shippableQuantity", shippableQuantity);
			jsonResponse.put("shippableWeight", shippableWeight);
			jsonResponse.put("shippableTotal", shippableTotal);
			jsonResponse.put("shippableItemInfo", shippableItemInfo);
			jsonResponse.put("shippableItemSizes", shippableItemSizes);
			jsonResponse.put("showGenericShipping", EnvConstantsUtil.SHOW_GENERIC_SHIP_NAMES);

			//if no shipment method was passed in get it from cart
			if(UtilValidate.isEmpty(shipmentMethodTypeId)) {
				shipmentMethodTypeId = cart.getShipmentMethodTypeId(0);
			}
			//if its still empty set the default
			if(UtilValidate.isEmpty(shipmentMethodTypeId)) {
				CartHelper.setCartDefaults(cart, request, null, null, null);
				shipmentMethodTypeId = cart.getShipmentMethodTypeId(0);
			}

			try {
				estimates = ShippingHelper.getAllFedExShippingEstimates(delegator, dispatcher, postalAddress, cart);
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to retrieve shipping methods/estimates. " + e + " : " + e.getMessage(), module);
			}

			//sort the map
			if(estimates.size() > 0) {
				jsonResponse.put("success" , true);
			} else {
				jsonResponse.put("success" , false);
				jsonResponse.put("error", "No shipping estimates found.");
			}

			GenericValue baseShipping = null;
			boolean foundShipping = false;

			Iterator estimateIter = estimates.keySet().iterator();
			while(estimateIter.hasNext()) {
				GenericValue shipMethod = (GenericValue) estimateIter.next();
				Map<String, Object> shipMap = new HashMap<String, Object>();
				String estDeliveryFrame = null;
				if("true".equalsIgnoreCase(SHOWDATES) && UtilValidate.isNotEmpty(shipMethod.getLong("minTransitTime")) && UtilValidate.isNotEmpty(shipMethod.getLong("maxTransitTime"))) {
					estDeliveryFrame = EnvConstantsUtil.WMDSHORT.format(OrderHelper.getDueDate(productionTime + shipMethod.getLong("minTransitTime").intValue(), null));
					if(shipMethod.getLong("minTransitTime").intValue() != shipMethod.getLong("maxTransitTime").intValue()) {
						estDeliveryFrame = estDeliveryFrame + " - " + EnvConstantsUtil.WMDSHORT.format(OrderHelper.getDueDate(productionTime + shipMethod.getLong("maxTransitTime").intValue(), null));
					}

					if("NEXT_DAY_AM".equals(shipMethod.getString("shipmentMethodTypeId"))) {
						estDeliveryFrame = estDeliveryFrame + " Noon";
					}
					estDeliveryFrame = "Est. Delivery by " + estDeliveryFrame;
					shipMap.put("genericDesc", shipMethod.getString("genericDescription").replaceAll("\\s\\(.*?\\d.*?\\)",""));
				} else {
					shipMap.put("genericDesc", shipMethod.getString("genericDescription"));
				}

				shipMap.put("method", shipMethod.getString("shipmentMethodTypeId"));
				shipMap.put("desc", shipMethod.getString("description"));
				shipMap.put("carrierPartyId", shipMethod.getString("partyId"));
				shipMap.put("estDeliveryDate", estDeliveryFrame);
				shipMap.put("cost", (BigDecimal) estimates.get(shipMethod));

				if(baseShipping == null && ((BigDecimal) estimates.get(shipMethod)).compareTo(BigDecimal.ZERO) > 0) {
					baseShipping = shipMethod;
				}

				//set the cart shipping if we find a matching value or assign the first one
				if(UtilValidate.isNotEmpty(shipmentMethodTypeId) && shipmentMethodTypeId.equals(shipMethod.getString("shipmentMethodTypeId"))) {
					CartHelper.setShipmentMethodTypeId(dispatcher, cart, 0, shipmentMethodTypeId, shipMethod.getString("partyId"));
					CartHelper.setShippingTotal(cart, 0, (BigDecimal) estimates.get(shipMethod));
					foundShipping = true;
				}
				jsonArray.add(shipMap);
			}

			//for PPS only, add FEDEX as an option when customers want to use it to ship on their own fedex account
			if(UtilValidate.isNotEmpty(context.get("showThirdParty")) && ((String) context.get("showThirdParty")).equalsIgnoreCase("Y")) {
				jsonArray.add(UtilMisc.toMap("method", "FEDEX_THIRD_PARTY", "desc", "Fedex 3rd Party", "genericDesc", "Fedex 3rd Party", "carrierPartyId", "FEDEX", "estDeliveryDate", null, "cost", BigDecimal.ZERO));
				jsonArray.add(UtilMisc.toMap("method", "UPS_THIRD_PARTY", "desc", "UPS 3rd Party", "genericDesc", "UPS 3rd Party", "carrierPartyId", "UPS", "estDeliveryDate", null, "cost", BigDecimal.ZERO));
			}

			if(!foundShipping && baseShipping != null) {
				shipmentMethodTypeId = baseShipping.getString("shipmentMethodTypeId");
				CartHelper.setShipmentMethodTypeId(dispatcher, cart, 0, baseShipping.getString("shipmentMethodTypeId"), baseShipping.getString("partyId"));
				CartHelper.setShippingTotal(cart, 0, (BigDecimal) estimates.get(baseShipping));
			} else if(!foundShipping) {
				//no shipping found, follow this order - sample else cheapest else pickup
				if(CartHelper.isAllowedFreeShipping(cart) || CartHelper.isOnlySample(cart) || CartHelper.isOnlySwatchBook(cart)) {
					shipmentMethodTypeId = "FIRST_CLASS";
					CartHelper.setShipmentMethodTypeId(dispatcher, cart, 0, "FIRST_CLASS", "USPS");
					CartHelper.setShippingTotal(cart, 0, BigDecimal.ZERO);
				} else if(estimates.size() > 0) {
					Map.Entry<GenericValue, BigDecimal> entry = estimates.entrySet().iterator().next();
					shipmentMethodTypeId = ((GenericValue) entry.getKey()).getString("shipmentMethodTypeId");
					CartHelper.setShipmentMethodTypeId(dispatcher, cart, 0, ((GenericValue) entry.getKey()).getString("shipmentMethodTypeId"), ((GenericValue) entry.getKey()).getString("partyId"));
					CartHelper.setShippingTotal(cart, 0, (BigDecimal) entry.getValue());
				} else {
					shipmentMethodTypeId = "PICKUP";
					CartHelper.setShipmentMethodTypeId(dispatcher, cart, 0, "PICKUP", "ENVELOPES");
					CartHelper.setShippingTotal(cart, 0, BigDecimal.ZERO);
				}
			}

			jsonResponse.put("shipmentMethodTypeId", shipmentMethodTypeId);

			//recalc the tax and other promotions because shipping has been updated
			try {
				if(cart.getProductPromoCodesEntered().size() > 0 && cart.getDoPromotions()) {
					ProductPromoWorker.doPromotions(cart, dispatcher);
				}
				CartHelper.setTaxTotal(dispatcher, postalAddress, cart);
			} catch (GenericServiceException e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to retrieve tax total. " + e + " : " + e.getMessage(), module);
			}
			jsonResponse.put("estimates", jsonArray);
		}
		return jsonResponse;
	}

	public static String getTransitTime(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		boolean success = false;
		String postalCode = (String) context.get("postalCode");
		try {
			if(UtilValidate.isNotEmpty(postalCode)) {
				GenericValue postalRow = delegator.findOne("ZonePostalCodeLookup", UtilMisc.toMap("shipmentMethodTypeId", "GROUND", "postalCode", postalCode), true);
				if(postalRow == null && postalCode.length() > 3) {
					postalCode = postalCode.substring(0, 3);
					postalRow = delegator.findOne("ZonePostalCodeLookup", UtilMisc.toMap("shipmentMethodTypeId", "GROUND", "postalCode", postalCode), true);
					if(postalRow != null && UtilValidate.isNotEmpty(postalRow.get("transitTime")) && postalRow.getLong("transitTime") == 1) {
						jsonResponse.put("transitTime", 1);
						success = true;
					}
				}
			}
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve postal data. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}
}