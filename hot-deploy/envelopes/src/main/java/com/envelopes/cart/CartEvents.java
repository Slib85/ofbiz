/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.cart;

import java.io.*;
import java.net.*;
import java.lang.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import com.bigname.quote.calculator.CalculatorHelper;
import com.envelopes.bronto.BrontoUtil;
import com.envelopes.product.Product;
import com.envelopes.product.ProductEvents;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilHttp;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.order.shoppingcart.ShoppingCart;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartEvents;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartItem;
import org.apache.ofbiz.order.shoppingcart.product.ProductPromoWorker;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.envelopes.order.OrderHelper;
import com.envelopes.product.ProductHelper;
import com.envelopes.shipping.ShippingHelper;
import com.envelopes.util.*;

public class CartEvents {
	public static final String module = CartEvents.class.getName();
	public static final DecimalFormat PRICE_FORMAT = new DecimalFormat("##.##");
	enum ReorderType {
		REORDER("true"),
		REUSE("false"),
		PLAIN("undefined"),
		UNKNOWN("");
		private String inputValue;
		ReorderType(String inputValue) {
			this.inputValue = inputValue;
		}

		public String getInputValue() {
			return inputValue;
		}

		public static ReorderType getReorderType(String inputValue, Map<String, Object> envPriceCalcAttributes, String artworkComment) {
			ReorderType reorderType = UNKNOWN;
			for (ReorderType type : values()) {
				if(type.getInputValue().equalsIgnoreCase(inputValue)) {
					reorderType = type;
					break;
				}
			}
			if(reorderType == PLAIN) {
				int colorsFront = 0, colorsBack = 0;

				if(envPriceCalcAttributes.containsKey("colorsFront") && envPriceCalcAttributes.get("colorsFront") != null) {
					colorsFront = (Integer) envPriceCalcAttributes.get("colorsFront");
				}

				if(envPriceCalcAttributes.containsKey("colorsBack") && envPriceCalcAttributes.get("colorsBack") != null) {
					colorsBack = (Integer) envPriceCalcAttributes.get("colorsBack");
				}

				if(colorsFront + colorsBack > 0) {
					if(artworkComment != null && UtilValidate.isNotEmpty(artworkComment.trim())) {
						reorderType = REUSE;
					} else {
						reorderType = REORDER;
					}
				}
			}
			return reorderType;
		}
	}

	/*
	 * Get the product price list and/or value for custom quantity
	 * Returns a JSON Object
	 */

	public static String reorderItems(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		response.setHeader("Access-Control-Allow-Origin", "*");

		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		CartHelper.setCartDefaults(cart, request, null, null, null);

		//########### IF THIS IS A BRAND NEW CART (IE, NO ITEMS EXIST IN CART) REBUILD FROM PERSISTENT CART IF AVAILABLE
		PersistentCart.quickCheck(request, response, null, false, false);
		//###########

		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		boolean success = false;

		try {
			List<String> failedUUIDs = new ArrayList<>();
			if(UtilValidate.isNotEmpty(context.get("itemUUIDs"))) {
				StringTokenizer itemUUIDs = new StringTokenizer((String)context.get("itemUUIDs"), "^");
				while (itemUUIDs.hasMoreTokens() && cart.items().size() < CartHelper.cartLimit) {
					String itemUUID = itemUUIDs.nextToken();
					String[] UUIDTokens = itemUUID.split("\\|");
					String orderId = UUIDTokens[0];
					String orderItemSeqId = UUIDTokens[1];
					String proofApproved = UUIDTokens[2];
					String artworkComment = UUIDTokens.length == 4 ? UUIDTokens[3] : null;

					Map<String, Object> envPriceCalcAttributes = new HashMap<>();
					Map<String, Object> envArtworkAttributes = new HashMap<>();
					Map<String, Object> envQuoteAttributes = new HashMap<>();
					Map<String, Object> envQuantityAttributes = new LinkedHashMap<String, Object>();

					envPriceCalcAttributes = OrderHelper.createPriceContextFromOrder(delegator, orderId, orderItemSeqId);
					envArtworkAttributes = OrderHelper.createArtworkContextFromOrder(delegator, orderId, orderItemSeqId);
					envQuoteAttributes = OrderHelper.createQuoteContextFromOrder(delegator, orderId, orderItemSeqId);

					Map<String, Object> priceReCalcAttributes = new HashMap<>(envPriceCalcAttributes);
					boolean hasPricingRequest = UtilValidate.isNotEmpty(priceReCalcAttributes.get("pricingRequest"));
					priceReCalcAttributes.put("quantity", (BigDecimal) envPriceCalcAttributes.get("quantity"));
					priceReCalcAttributes.put("id", ((GenericValue) envPriceCalcAttributes.get("product")).getString("productId"));

					GenericValue orderItem = EntityQuery.use(delegator).from("OrderItem").where("orderId", orderId, "orderItemSeqId", orderItemSeqId).queryFirst();
					// If this is a folders quoted items reorder, use the last order item price and do not recalculate the price
					if(orderItem != null && "folders".equalsIgnoreCase(OrderHelper.getWebSiteId(delegator, orderId)) && OrderHelper.isItemPrinted(delegator, orderId, orderItemSeqId, orderItem) && !hasPricingRequest) {
						envPriceCalcAttributes.put("price", orderItem.getBigDecimal("unitPrice").multiply(orderItem.getBigDecimal("quantity")));
						priceReCalcAttributes.put("price", orderItem.getBigDecimal("unitPrice").multiply(orderItem.getBigDecimal("quantity")));
						context.put("price", orderItem.getBigDecimal("unitPrice").multiply(orderItem.getBigDecimal("quantity")));
					}
					Map<String, Object> quantityPriceList = ProductEvents.getProductPrice(request, priceReCalcAttributes);
					Map<Integer, Map<String, Object>> priceList = (Map) quantityPriceList.get("priceList");
					int reorderQuantity = ((BigDecimal) envPriceCalcAttributes.get("quantity")).intValue();
					for(Integer quantity : priceList.keySet()) {
						if(quantity == reorderQuantity) {
							context.put("price", ((BigDecimal) priceList.get(quantity).get("price")).toPlainString());
							envPriceCalcAttributes.put("price", priceList.get(quantity).get("price"));
							priceReCalcAttributes.put("price", priceList.get(quantity).get("price"));
						}
						envQuantityAttributes.put(quantity.toString(), priceList.get(quantity).get("price"));
					}
					ReorderType reorderType = ReorderType.getReorderType(proofApproved, envPriceCalcAttributes, artworkComment);

					switch (reorderType) {
						case REORDER:
							request.setAttribute("itemComment", "");
							envArtworkAttributes.put("artworkSource", "ART_REUSED");
							break;
						case REUSE:
							request.setAttribute("itemComment", artworkComment);
							envArtworkAttributes.put("artworkSource", "ART_REUSED");
							break;
						case PLAIN:
							request.removeAttribute("itemComment");
							// this part is to correct some bad data.
							// Due to an earlier bug in the reorder, few plain orders have
							// ART_REUSED value set for artworkSource attribute.
							// So if its a plain order and has artworkSource, reset it to null
							if(envArtworkAttributes.containsKey("artworkSource")) {
								envArtworkAttributes.put("artworkSource", null);
							}
							break;
						default:
							failedUUIDs.add(itemUUID);
							continue;
					}
					request.setAttribute("ADD_PRODUCT_ID", ((GenericValue) envPriceCalcAttributes.get("product")).getString("productId"));
					request.setAttribute("envPriceCalcAttributes", envPriceCalcAttributes);
					request.setAttribute("envArtworkAttributes", envArtworkAttributes);
					request.setAttribute("envQuoteAttributes", envQuoteAttributes);
					request.setAttribute("envQuantityAttributes", envQuantityAttributes);

					String result = CartHelper.addToCart(request, response);

					if(UtilValidate.isNotEmpty(result) && !result.equals("success")) {
						failedUUIDs.add(itemUUID);
					}

					//tax and shipping needs to recalc on every add to cart
					try {
						ShippingHelper.setShipMethodAndTotal(delegator, dispatcher, null, cart);
						CartHelper.setTaxTotal(dispatcher, null, cart);
					} catch (GenericEntityException e) {
						EnvUtil.reportError(e);
						Debug.logError("Error while trying to apply tax and ship values. " + e + " : " + e.getMessage(), module);
					} catch (GenericServiceException e) {
						EnvUtil.reportError(e);
						Debug.logError("Error while trying to apply tax and ship values. " + e + " : " + e.getMessage(), module);
					} catch (Exception e) {
						EnvUtil.reportError(e);
						Debug.logError("Error while trying to apply tax and ship values. " + e + " : " + e.getMessage(), module);
					}
				}

				if(failedUUIDs.isEmpty()) {
					success = true;
				}

				//########### UPDATE THE PERSISTENT CART
				PersistentCart.quickCheck(request, response, null, false, false);
				//###########

				List<Map> cartItemList = CartHelper.getCartItems(cart);
				List<GenericValue> cartPromoList = CartHelper.getCartAdjustments(cart);
				jsonResponse.put("cartItems", cartItemList);
				jsonResponse.put("cartPromos", cartPromoList);
				jsonResponse.put("shippingDetails", UtilMisc.toMap("shipmentMethodTypeId", cart.getShipmentMethodTypeId(0), "partyId", cart.getCarrierPartyId(0), "shippingTotal", cart.getItemShipGroupEstimate(0)));
				jsonResponse.put("subTotal", cart.getSubTotal().setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
				jsonResponse.put("taxTotal", cart.getTotalSalesTax().setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
				jsonResponse.put("shipTotal", cart.getTotalShipping().setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
				jsonResponse.put("grandTotal", cart.getDisplayGrandTotal().setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
				jsonResponse.put("success", success);
			}
		} catch (GenericEntityException e) {
			jsonResponse.put("success", false);
			jsonResponse.put("error", "There was an error trying to add your item to the cart.");
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to apply tax and ship values. " + e + " : " + e.getMessage(), module);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String addToCart(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		//response.setHeader("Access-Control-Allow-Origin", "*");

		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		CartHelper.setCartDefaults(cart, request, null, null, null);

		//########### IF THIS IS A BRAND NEW CART (IE, NO ITEMS EXIST IN CART) REBUILD FROM PERSISTENT CART IF AVAILABLE
		PersistentCart.quickCheck(request, response, null, false, false);
		//###########

		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		Map<String, Object> envPriceCalcAttributes = new HashMap<>();
		Map<String, Object> envArtworkAttributes = new HashMap<>();
		Map<String, Object> envQuoteAttributes = new HashMap<>();
		Map<String, Object> envQuantityAttributes = new LinkedHashMap<>();

		/**
		 * IMPORTANT: ADD ID VALUE AS ADDITIONAL VALUE INTO CONTEXT, REQUIRED FOR PERSISTENT CART
		 */
		context.put("id", (String) context.get("add_product_id"));

		/**
		 * If we are not adding a custom product, sample or direct mail, make sure to remove these optional parameters if they are present
		 * They are not needed for standard products
		 */
		if(!ProductHelper.isCustomSku((String) context.get("add_product_id")) && !ProductHelper.isSampleSku((String) context.get("add_product_id")) && !ProductHelper.isDirectMailSku((String) context.get("add_product_id"))) {
			context.remove("price");
			//context.remove("name");
			context.remove("weight");
		}

		try {
			//if the product is from a quote, get the price from the quote
			if(UtilValidate.isNotEmpty(context.get("quoteId")) && UtilValidate.isNotEmpty(context.get("quoteItemSeqId"))) {
				Map<String, Object> quoteData = CalculatorHelper.deserializedQuoteSummary(delegator, null, (String) context.get("quoteItemSeqId"));
				Map<Integer, Map<String, BigDecimal>> priceQuotes = (Map<Integer, Map<String, BigDecimal>>) quoteData.get("prices");
				Map<String, BigDecimal> price = priceQuotes.get(Integer.valueOf((String) context.get("quantity")));
				if(UtilValidate.isNotEmpty(price)) {
					context.put("price", price.get("total").toPlainString());
					context.put("name", (UtilValidate.isEmpty(quoteData.get("product")) || UtilValidate.isEmpty(quoteData.get("material"))) ? quoteData.get("productName") : ((GenericValue) quoteData.get("product")).getString("productId") + ": " + ((GenericValue) quoteData.get("product")).getString("productName") + " - " + quoteData.get("material"));
				}
			}

			if(UtilValidate.isNotEmpty(context.get("orderId")) && UtilValidate.isNotEmpty(context.get("orderItemSeqId"))) {
				envPriceCalcAttributes = OrderHelper.createPriceContextFromOrder(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"));
				envArtworkAttributes = OrderHelper.createArtworkContextFromOrder(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"));
				envQuoteAttributes = OrderHelper.createQuoteContextFromOrder(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"));
			} else {
				envPriceCalcAttributes = CartHelper.buildPriceContext(context);
				envArtworkAttributes = CartHelper.buildArtworkContext(context);
				envQuoteAttributes = CartHelper.buildQuoteContext(context);
			}

			Map<String, Object> priceReCalcAttributes = new HashMap<String, Object>(envPriceCalcAttributes);
			priceReCalcAttributes.put("quantity", (String) context.get("quantity"));
			priceReCalcAttributes.put("id", (String) context.get("add_product_id"));
			Map<String, Object> quantityPriceList = ProductEvents.getProductPrice(request, priceReCalcAttributes);
			Map<Integer, Map<String, Object>> priceList = (Map) quantityPriceList.get("priceList");
			for(Integer quantity : priceList.keySet()) {
				if(context.containsKey("pricingRequest") && quantity.toString().equals((String) context.get("quantity"))) {
					context.put("price", ((BigDecimal) priceList.get(quantity).get("price")).toPlainString());
					envPriceCalcAttributes = CartHelper.buildPriceContext(context); //rebuild to add price
				}

				envQuantityAttributes.put(quantity.toString(), priceList.get(quantity).get("price"));
			}

			request.setAttribute("envPriceCalcAttributes", envPriceCalcAttributes);
			request.setAttribute("envArtworkAttributes", envArtworkAttributes);
			request.setAttribute("envQuoteAttributes", envQuoteAttributes);
			request.setAttribute("envQuantityAttributes", envQuantityAttributes);

			//remove all other items in the cart with the same s7 id before adding these, this means its a re-edit
			if(UtilValidate.isNotEmpty(envArtworkAttributes.get("scene7ParentId"))) {
				List<ShoppingCartItem> cartLines = cart.items();
				Iterator cartIter = cartLines.listIterator();
				while(cartIter.hasNext()) {
					ShoppingCartItem sci = (ShoppingCartItem) cartIter.next();
					if(UtilValidate.isNotEmpty(sci.getAttribute("envArtworkAttributes")) &&	UtilValidate.isNotEmpty(((Map<String, Object>) sci.getAttribute("envArtworkAttributes")).get("scene7ParentId")) && ((String) ((Map<String, Object>) sci.getAttribute("envArtworkAttributes")).get("scene7ParentId")).equals((String) envArtworkAttributes.get("scene7ParentId"))) {
						if((UtilValidate.isNotEmpty(((Map<String, Object>) sci.getAttribute("envArtworkAttributes")).get("token")) && !((String) ((Map<String, Object>) sci.getAttribute("envArtworkAttributes")).get("token")).equals((String) envArtworkAttributes.get("token"))) || UtilValidate.isEmpty(((Map<String, Object>) sci.getAttribute("envArtworkAttributes")).get("token"))) {
							try {
								cart.removeCartItem((Integer.valueOf(cart.getItemIndex(sci))).intValue(), dispatcher);
							} catch (Exception e) {
								EnvUtil.reportError(e);
								Debug.logError("Error while trying to remove item from cart for re-edits. " + e + " : " + e.getMessage(), module);
							}
						}
					}
				}
			}

			String result = CartHelper.addToCart(request, response);

			if(UtilValidate.isNotEmpty(result) && result.equals("success")) {
				//tax and shipping needs to recalc on every add to cart
				try {
					ShippingHelper.setShipMethodAndTotal(delegator, dispatcher, null, cart);
					CartHelper.setTaxTotal(dispatcher, null, cart);
				} catch (Exception e) {
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to apply tax and ship values. " + e + " : " + e.getMessage(), module);
				}

				//########### UPDATE THE PERSISTENT CART
				PersistentCart.quickCheck(request, response, null, false, false);
				//###########

				List<Map> cartItemList = CartHelper.getCartItems(cart, dispatcher);
				List<GenericValue> cartPromoList = CartHelper.getCartAdjustments(cart);
				jsonResponse.put("cartItems", cartItemList);
				jsonResponse.put("cartPromos", cartPromoList);
				jsonResponse.put("shippingDetails", UtilMisc.toMap("shipmentMethodTypeId", cart.getShipmentMethodTypeId(0), "partyId", cart.getCarrierPartyId(0), "shippingTotal", cart.getItemShipGroupEstimate(0)));
				jsonResponse.put("subTotal", cart.getSubTotal().setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
				jsonResponse.put("taxTotal", cart.getTotalSalesTax().setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
				jsonResponse.put("shipTotal", cart.getTotalShipping().setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
				jsonResponse.put("grandTotal", cart.getDisplayGrandTotal().setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
				jsonResponse.put("success", true);
			} else {
				jsonResponse.put("success", false);
			}
		} catch (GenericEntityException e) {
			jsonResponse.put("success", false);
			jsonResponse.put("error", "There was an error trying to add your item to the cart.");
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to apply tax and ship values. " + e + " : " + e.getMessage(), module);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Remove an item from the cart
	 * Returns JSON Object
	 */
	public static String removeFromCart(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);

		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", true);

		if(UtilValidate.isNotEmpty(context.get("index"))) {
			try {
				cart.removeCartItem(Integer.valueOf((String) context.get("index")).intValue(), dispatcher);
				ShippingHelper.setShipMethodAndTotal(delegator, dispatcher, null, cart);
				CartHelper.setTaxTotal(dispatcher, null, cart);

				//########### UPDATE THE PERSISTENT CART
				PersistentCart.quickCheck(request, response, null, false, false);
				//###########

				BrontoUtil.sendCartData(request, true);
			} catch (Exception e) {
				jsonResponse.put("success", false);
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to remove item from cart. " + e + " : " + e.getMessage(), module);
			}
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Change the quantity of an item in the cart
	 */
	public static String updateCartItem(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", false);

		if((UtilValidate.isNotEmpty(context.get("quantity")) || UtilValidate.isNotEmpty(context.get("productionTime"))) && UtilValidate.isNotEmpty(context.get("index")) && cart != null) {
			ShoppingCartItem item = cart.findCartItem(Integer.valueOf((String) context.get("index")).intValue());

			try {
				Map<String, Object> buildPriceContext = CartHelper.rebuildPriceContext(context, cart, item); //updates reference, do not delete
				if(UtilValidate.isNotEmpty(context.get("productionTime"))) {
					String productionTime = (String) context.get("productionTime");
					if(productionTime.equalsIgnoreCase("Standard") || productionTime.equalsIgnoreCase("Rush")) {
						boolean isRush = productionTime.equalsIgnoreCase("Rush");
						if((Boolean) buildPriceContext.get("isRush") != isRush) {
							((Map)item.getAttribute("envPriceCalcAttributes")).put("isRush", isRush);
						}
						buildPriceContext.put("isRush", isRush);
					}
				}
				if(UtilValidate.isNotEmpty(context.get("quantity"))) {
					if(UtilValidate.isNotEmpty(context.get("isCustomQuantity"))) {
						item.setCustomQuantity(Boolean.valueOf((String) context.get("isCustomQuantity")));
					} else {
						item.setCustomQuantity(false);
					}
					item.setQuantity(new BigDecimal((String) context.get("quantity")), dispatcher, cart);
				}
				Map<String, Object> envQuantityAttributes = new LinkedHashMap<String, Object>();
				buildPriceContext.put("id", item.getProductId());
				buildPriceContext.put("quantity", context.get("quantity"));

				Map<String, Object> quantityPriceList = ProductEvents.getProductPrice(request, buildPriceContext);
				Map<Integer, Map<String, Object>> priceList = (Map) quantityPriceList.get("priceList");
				for(Integer quantity : priceList.keySet()) {
					envQuantityAttributes.put(quantity.toString(), priceList.get(quantity).get("price"));
				}

				item.setAttribute("envQuantityAttributes", envQuantityAttributes);
				item.updatePrice(dispatcher, cart);
				ShippingHelper.setShipMethodAndTotal(delegator, dispatcher, null, cart);
				CartHelper.setTaxTotal(dispatcher, null, cart);

				//########### UPDATE THE PERSISTENT CART
				PersistentCart.quickCheck(request, response, null, false, false);
				//###########

				BrontoUtil.sendCartData(request);
				jsonResponse.put("priceList", priceList);
				jsonResponse.put("success", true);
			} catch (Exception e) {
				jsonResponse.put("success", false);
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to update item in cart. " + e + " : " + e.getMessage(), module);
			}
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String isMiniCartValid(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();

		//session attributes
		Boolean isFromBronto = (Boolean) session.getAttribute("isFromBronto");
		if((UtilValidate.isEmpty(isFromBronto) || (UtilValidate.isNotEmpty(isFromBronto) && !isFromBronto)) && "bronto".equalsIgnoreCase(request.getParameter("utm_source"))) {
			session.setAttribute("isFromBronto", true);
		} else {
			session.setAttribute("isFromBronto", false);
		}

		String cartSessionId = request.getParameter("cartUUID");
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("success", true);
		jsonResponse.put("valid", UtilHttp.getSessionId(request).equals(cartSessionId));
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String loadCart(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		if(UtilValidate.isNotEmpty(context.get("id"))) {
			try {
				PersistentCart.quickCheck(request, response, (String) context.get("id"), UtilValidate.isNotEmpty((String) context.get("isAgent")) && "true".equalsIgnoreCase((String) context.get("isAgent")), UtilValidate.isNotEmpty((String) context.get("merge")) && "true".equalsIgnoreCase((String) context.get("merge")));
			} catch(Exception e) {
				Debug.logError(e, module);
			}
		}

		return "success";
	}

	public static String releaseCart(HttpServletRequest request, HttpServletResponse response) {
		PersistentCart pCart = (PersistentCart) request.getSession().getAttribute("PersistentCart");
		if(pCart != null) {
			try {
				pCart.cart.clear();
				pCart.removeAgentData();
				PersistentCart.destroy(request, response, null, false);
				PersistentCart.setCookie(request, response, true);
			} catch(Exception e) {
				Debug.logError(e, module);
			}
		}

		return "success";
	}

	/*
	 * Returns a JSON Object
	 */
	public static String getCart(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> jsonResponse = getCart(request);

		//########### CHECK CART INCASE IT NEEDS TO BE REBUILT
		PersistentCart.quickCheck(request, response, null, false, false);
		//###########

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static Map<String, Object> getCart(HttpServletRequest request) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		CartHelper.setCartDefaults(cart, request, null, null, null);
		Map<String, String> shippingPostalAddress = cart.getShipTaxPostalAddress();

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();

		List<Map> cartItemList = CartHelper.getCartItems(cart, dispatcher);
		List<GenericValue> cartPromoList = CartHelper.getCartAdjustments(cart);

		if(UtilValidate.isNotEmpty(context.get("miniCart")) && "true".equalsIgnoreCase((String) context.get("miniCart"))) {
			jsonResponse.put("cartItems", cartItemList);
			jsonResponse.put("cartPromos", cartPromoList);
			jsonResponse.put("shippingDetails", UtilMisc.toMap("shipmentMethodTypeId", cart.getShipmentMethodTypeId(0), "partyId", cart.getCarrierPartyId(0), "shippingTotal", cart.getItemShipGroupEstimate(0)));
			jsonResponse.put("subTotal", cart.getSubTotal().setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			jsonResponse.put("taxTotal", cart.getTotalSalesTax().setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			jsonResponse.put("shipTotal", cart.getTotalShipping().setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			jsonResponse.put("grandTotal", cart.getDisplayGrandTotal().setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
		} else {
			for (Map cartItem : cartItemList) {
				String productId = (String) cartItem.get("productId");

				Map<String, Object> quoteAttributes = cartItem.containsKey("envQuoteAttributes") ? (HashMap) cartItem.get("envQuoteAttributes") : new HashMap<>();
				Map<String, Object> artworkAttributes = cartItem.containsKey("envArtworkAttributes") ? (HashMap) cartItem.get("envArtworkAttributes") : new HashMap<>();
				Map<String, Object> priceCalcAttributes = cartItem.containsKey("envPriceCalcAttributes") ? (HashMap) cartItem.get("envPriceCalcAttributes") : new HashMap<>();
				//priceCalcAttributes.put("quantity", cartItem.get("quantity"));
				//priceCalcAttributes.put("id", cartItem.get("productId"));

				try {
					Product product = new Product(delegator, dispatcher, productId, cart);
					Map<String, String> productFeatures = product.getFeatures();

					String productionTime = "Standard";
					if (priceCalcAttributes.containsKey("isRush") && (Boolean) priceCalcAttributes.get("isRush")) {
						productionTime = "Rush";
					}

					int addresses = 0;
					if (priceCalcAttributes.containsKey("addresses") && priceCalcAttributes.get("addresses") != null) {
						addresses = (Integer) priceCalcAttributes.get("addresses");
					}

					int cuts = 0;
					if (priceCalcAttributes.containsKey("cuts") && priceCalcAttributes.get("cuts") != null) {
						cuts = (Integer) priceCalcAttributes.get("cuts");
					}

					boolean isFolded = false;
					if (priceCalcAttributes.containsKey("isFolded") && priceCalcAttributes.get("isFolded") != null) {
						isFolded = ((Boolean) priceCalcAttributes.get("isFolded")).booleanValue();
					}

					List<String> printing = new ArrayList<>();
					if (priceCalcAttributes.containsKey("isFullBleed") && (Boolean) priceCalcAttributes.get("isFullBleed")) {
						printing.add("Full Bleed");
					}

					if (priceCalcAttributes.containsKey("colorsFront") && priceCalcAttributes.get("colorsFront") != null) {
						int colorsFront = (Integer) priceCalcAttributes.get("colorsFront");
						if (colorsFront == 4) {
							printing.add("Full Color Front");
						} else if (colorsFront > 0) {
							printing.add(colorsFront + " Color Front");
						}
					}

					if (priceCalcAttributes.containsKey("colorsBack") && priceCalcAttributes.get("colorsBack") != null) {
						int colorsBack = (Integer) priceCalcAttributes.get("colorsBack");
						if (colorsBack == 4) {
							printing.add("Full Color Back");
						} else if (colorsBack > 0) {
							printing.add(colorsBack + " Color Back");
						}
					}

					List<String> files = new ArrayList<>();
					if (artworkAttributes.containsKey("content") && UtilValidate.isNotEmpty(artworkAttributes.get("content"))) {
						List<Map> contentList = (List<Map>) artworkAttributes.get("content");
						for (Map content : contentList) {
							files.add((String) content.get("contentName"));
						}
					}

					//if data exists for the pricing request, there is valuable print info in there
					String addOns = null;
					String pocketStyle = null;
					if (priceCalcAttributes.containsKey("pricingRequest") && priceCalcAttributes.get("pricingRequest") != null) {
						Map<String, Object> pricingRequest = CalculatorHelper.deserializedPriceSummary(delegator, (GenericValue) cartItem.get("product"), (String) priceCalcAttributes.get("pricingRequest"));
						if (pricingRequest.containsKey("offset")) {
							printing.clear();
							printing.add("Offset Printing");
						} else if (pricingRequest.containsKey("foil")) {
							printing.clear();
							printing.add("Foil Stamping");
						} else if (pricingRequest.containsKey("embossing")) {
							printing.clear();
							printing.add("Embossing");
						}

						if (pricingRequest.containsKey("addOnOptions")) {
							addOns = (String) pricingRequest.get("addOnOptions");
						}
					}

					cartItem.put("hasCustomQty", product.hasCustomQty());
					cartItem.put("productionTime", productionTime);
					cartItem.put("productName", ProductHelper.formatName(product.getName()));
					cartItem.put("productSize", productFeatures.get("SIZE"));
					cartItem.put("productColor", productFeatures.get("COLOR"));
					cartItem.put("addresses", addresses);
					cartItem.put("cuts", cuts);
					cartItem.put("isFolded", isFolded);
					cartItem.put("printing", printing);
					cartItem.put("addOns", addOns);
					cartItem.put("files", files);
					cartItem.put("productTypeId", product.getProduct().getString("productTypeId"));
					cartItem.put("leadTime", product.getLeadTime());
				} catch (GenericEntityException | GenericServiceException e) {
					Debug.logError(e, module);
				}
			}

			jsonResponse.put("lineItems", cartItemList);
			jsonResponse.put("cartPromos", cartPromoList);
			jsonResponse.put("shippingDetails", UtilMisc.toMap("shipmentMethodTypeId", cart.getShipmentMethodTypeId(0), "partyId", cart.getCarrierPartyId(0), "shippingTotal", cart.getItemShipGroupEstimate(0)));
			jsonResponse.put("subTotal", cart.getSubTotal().setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			jsonResponse.put("taxTotal", cart.getTotalSalesTax().setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			jsonResponse.put("shipTotal", cart.getTotalShipping().setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			jsonResponse.put("grandTotal", cart.getDisplayGrandTotal().setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			jsonResponse.put("discounts", getDiscounts(cartPromoList));
			jsonResponse.put("enteredPromos", cart.getProductPromoCodesEntered());
			jsonResponse.put("cartStateData", getCartCookieData(cart, request));

			if(UtilValidate.isNotEmpty(shippingPostalAddress)) { //TODO - validate the postal code
				try {
					Map<GenericValue, BigDecimal> estimates = ShippingHelper.getAllShippingEstimates(delegator, dispatcher, shippingPostalAddress, cart);
					List<Map> jsonArray = new ArrayList<>();
					Iterator estimateIter = estimates.keySet().iterator();
					while(estimateIter.hasNext()) {
						GenericValue shipMethod = (GenericValue) estimateIter.next();
						Map<String, Object> shipMap = new HashMap<String, Object>();
						shipMap.put("method", shipMethod.getString("shipmentMethodTypeId"));
						shipMap.put("desc", shipMethod.getString("description"));
						shipMap.put("genericDesc", shipMethod.getString("genericDescription"));
						shipMap.put("carrierPartyId", shipMethod.getString("partyId"));
						shipMap.put("cost", estimates.get(shipMethod));
						jsonArray.add(shipMap);
					}
					jsonResponse.put("shippingDetails", UtilMisc.toMap("shipmentMethodTypeId", cart.getShipmentMethodTypeId(0), "partyId", cart.getCarrierPartyId(0), "shippingTotal", cart.getItemShipGroupEstimate(0), "shippingMethods", jsonArray, "shippingPostalCode", shippingPostalAddress.get("postalCode"), "shippingCountryGeoId", shippingPostalAddress.get("countryGeoId"), "shipTo", shippingPostalAddress.get("shipTo")));
				} catch (Exception e) {
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to retrieve shipping methods/estimates. " + e + " : " + e.getMessage(), module);
					jsonResponse.put("shippingDetails", UtilMisc.toMap("shipmentMethodTypeId", cart.getShipmentMethodTypeId(0), "partyId", cart.getCarrierPartyId(0), "shippingTotal", cart.getItemShipGroupEstimate(0)));
				}
			}
		}

		return jsonResponse;
	}

	public static Map<String, Object> getCartCookieData(ShoppingCart cart, HttpServletRequest request) {
		Map<String, Object> cookieData = new HashMap<>();
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		cookieData.put("JSESSIONID", UtilHttp.getSessionId(request));
		StringBuilder itemStateData = new StringBuilder();
		List<Map> cartItemList = CartHelper.getCartItems(cart);
		for (Map cartItem : cartItemList) {
			String productId = (String) cartItem.get("productId");
			try {
				Product product = new Product(delegator, dispatcher, productId, cart);
				itemStateData.append(itemStateData.length() > 0 ? "|" : "").append(cartItem.get("productId")).append("|").append(ProductHelper.formatName(product.getName())).append("|").append(cartItem.get("totalPrice")).append("|").append(cartItem.get("quantity")).append("|").append((UtilValidate.isNotEmpty(((Map)cartItem.get("envArtworkAttributes")).get("scene7ParentId")) && UtilValidate.isNotEmpty(((Map)cartItem.get("envArtworkAttributes")).get("scene7DesignId"))) ? ((Map)cartItem.get("envArtworkAttributes")).get("scene7DesignId") : "");
			} catch (GenericEntityException|GenericServiceException e) {
				Debug.logError(e, module);
			}
		}
		cookieData.put("itemCount", cartItemList.size());
		cookieData.put("cartTotal", cartItemList.size() == 0 ? "empty" : cart.getDisplayGrandTotal().setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
		cookieData.put("cartSubtotal", cartItemList.size() == 0 ? "empty" : cart.getSubTotal().setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
		try {
			cookieData.put("cartItems", URLEncoder.encode(itemStateData.toString().replaceAll("%", "percent"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return cookieData;
	}

	public static Map<String, String> getDiscounts(List<GenericValue> cartPromoList) {
		Map<String, String> discounts = new LinkedHashMap<>();

		for (GenericValue promo : cartPromoList) {
			BigDecimal amount = new BigDecimal("0");

			if (UtilValidate.isNotEmpty(discounts.get(promo.getString("description")))) {
				amount = new BigDecimal(discounts.get(promo.getString("description")));
			}

			amount = amount.add(promo.getBigDecimal("amount"));
			discounts.put(promo.getString("description"), amount.setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING).toString());
		}

		return discounts;
	}

	/*
	 * Set the shipping method type id on the cart
	 */
	public static String setShipmentMethodTypeId(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		CartHelper.setShipmentMethodTypeId(dispatcher, cart, 0, (String) context.get("shipmentMethodTypeId"), (String) context.get("carrierPartyId"));

		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		jsonResponse.put("success", true);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Set the shipping total on the cart
	 */
	public static String setShippingTotal(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		CartHelper.setShippingTotal(cart, 0, new BigDecimal((String) context.get("shippingTotal")));

		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		jsonResponse.put("success", true);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Set the shipping method type and total
	 */
	public static String setShipping(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");

		Map<String, Object> context = EnvUtil.getParameterMap(request);

		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		CartHelper.setShippingTotal(cart, 0, new BigDecimal((String) context.get("shippingTotal")));
		CartHelper.setShipmentMethodTypeId(dispatcher, cart, 0, (String) context.get("shipmentMethodTypeId"), (String) context.get("carrierPartyId"));
		try {
			if(cart.getProductPromoCodesEntered().size() > 0) {
				ProductPromoWorker.doPromotions(cart, dispatcher);
			}
			CartHelper.setTaxTotal(dispatcher, null, cart);
		} catch(GenericServiceException e) {
			EnvUtil.reportError(e);
		}

		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		jsonResponse.put("success", true);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static boolean isCartEmpty(HttpServletRequest request) {
		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		return cart == null || cart.items() == null || cart.items().isEmpty();
	}

	public static String updateCartCookie(HttpServletRequest request, HttpServletResponse response) {
		//set persistent cart cookie
		PersistentCart.setCookie(request, response, false);

		request.setAttribute("success", true);
		return "success";
	}

	public static String deletePersistentCart(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;
		String responseMessage = "";

		try {
			CartHelper.deletePersistentCart(delegator, (String) context.get("cartId"));
			success = true;
		} catch (Exception e) {
			responseMessage = "Failed to delete cart with ID: " + context.get("cartId") + "\n\nError: " + e.getMessage();
		}

		jsonResponse.put("success", success);
		jsonResponse.put("responseMessage", responseMessage);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}
}
