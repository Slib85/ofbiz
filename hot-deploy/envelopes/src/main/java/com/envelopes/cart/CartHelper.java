/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.cart;

import java.math.BigDecimal;
import java.util.*;

import com.envelopes.order.OrderHelper;
import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.order.shoppingcart.ShoppingCart;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartEvents;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartItem;
import org.apache.ofbiz.order.shoppingcart.product.ProductPromoWorker;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.webapp.website.WebSiteWorker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.envelopes.product.Product;
import com.envelopes.product.ProductHelper;
import com.envelopes.tax.TaxHelper;
import com.envelopes.util.*;

public class CartHelper {
	public static final String module = CartHelper.class.getName();
	protected static final Integer cartLimit = 50;

	/*
	 * Set the cart defaults
	 */
	public static void setCartDefaults(ShoppingCart cart, HttpServletRequest request, String orderType, String channelType, String productStoreId) {
		String webSiteId = "envelopes";
		if(request != null) {
			webSiteId = WebSiteWorker.getWebSiteId(request);
		}

		if(UtilValidate.isNotEmpty(orderType)) {
			cart.setOrderType(orderType);
		} else {
			cart.setOrderType("SALES_ORDER");
		}

		if(UtilValidate.isEmpty(cart.getShipmentMethodTypeId(0))) {
			setShipmentMethodTypeId((request != null) ? (LocalDispatcher) request.getAttribute("dispatcher") : null, cart, 0, "GROUND", EnvConstantsUtil.IS_UPS ? "UPS" : "FEDEX");
		}

		if(UtilValidate.isNotEmpty(channelType)) {
			cart.setChannelType(channelType);
		} else {
			cart.setChannelType(EnvUtil.getSalesChannelEnumId(webSiteId));
		}

		if(UtilValidate.isNotEmpty(productStoreId)) {
			cart.setProductStoreId(productStoreId);
		} else {
			cart.setProductStoreId("10000");
		}

		cart.setWebSiteId(webSiteId);
	}

	/*
	 * Get list of cart items
	 */
	public static List<Map> getCartItems(ShoppingCart cart, boolean stringify) {
		return getCartItems(cart, null, stringify);
	}
	public static List<Map> getCartItems(ShoppingCart cart, LocalDispatcher dispatcher) {
		return getCartItems(cart, dispatcher, false);
	}
	public static List<Map> getCartItems(ShoppingCart cart) {
		return getCartItems(cart, null, false);
	}
	public static List<Map> getCartItems(ShoppingCart cart, LocalDispatcher dispatcher, boolean stringify) {
		if(cart == null) {
			return null;
		}

		List<Map> cartItemList = new ArrayList<>();

		List<ShoppingCartItem> cartLines = cart.items();
		for(ShoppingCartItem sci : cartLines) {
			Map<String, Object> cartItemMap = new HashMap<String, Object>();
			cartItemMap.put("index", Integer.valueOf(cart.getItemIndex(sci)));
			cartItemMap.put("isPromo", sci.getIsPromo());
			cartItemMap.put("productId", sci.getProductId());
			cartItemMap.put("product", sci.getProduct());
			cartItemMap.put("primaryProductCategoryId", ((GenericValue) sci.getProduct()).getString("primaryProductCategoryId"));
			cartItemMap.put("orderItemTypeId", sci.getItemType());
			cartItemMap.put("quantity", stringify ? sci.getQuantity().toPlainString() : sci.getQuantity());
			cartItemMap.put("isCustomQuantity", sci.isCustomQuantity());
			cartItemMap.put("unitPrice", sci.getDisplayPrice().setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			cartItemMap.put("totalPrice", sci.getItemSubTotal().setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			cartItemMap.put("envArtworkAttributes", sci.getAttribute("envArtworkAttributes"));
			cartItemMap.put("envQuoteAttributes", sci.getAttribute("envQuoteAttributes"));
			cartItemMap.put("envPriceCalcAttributes", sci.getAttribute("envPriceCalcAttributes"));
			cartItemMap.put("envPriceResultAttributes", sci.getAttribute("envPriceResultAttributes"));
			cartItemMap.put("envQuantityAttributes", sci.getAttribute("envQuantityAttributes"));
			if(dispatcher != null) {
				cartItemMap.put("itemDescription", sci.getName(dispatcher));
			}
			if(UtilValidate.isNotEmpty(sci.getItemComment())) {
				cartItemMap.put("itemComment", sci.getItemComment());
			}
			cartItemList.add(cartItemMap);
		}

		return cartItemList;
	}

	/*
	 Get recommended products
	 */
	public static List<Product> getCrossSellItems(HttpServletRequest request) throws GenericEntityException {
		Delegator delegator = (Delegator)request.getAttribute("delegator");
		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);

		Map<String, Object> context = EnvUtil.getParameterMap(request);

		List<Product> products = new ArrayList<>();

		try {
			String lastProdId = null;

			if(UtilValidate.isNotEmpty(context.get("product_id"))) {
				lastProdId = (String) context.get("product_id");
			} else {
				List<ShoppingCartItem> cartLines = cart.items();
				for(ShoppingCartItem sci : cartLines) {
					lastProdId = sci.getProductId();
					break;
				}
			}

			if(UtilValidate.isNotEmpty(lastProdId)) {
				GenericValue productRec = delegator.findOne("ProductRecommendation", UtilMisc.toMap("productId", lastProdId), true);
				if(productRec != null) {
					for(int i = 1; i <= 10; i++) {
						if(UtilValidate.isNotEmpty(productRec.getString("recommendation" + i))) {
							products.add(new Product(delegator, (LocalDispatcher) request.getAttribute("dispatcher"), productRec.getString("recommendation" + i), request));
						}
					}
				}
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while getting the cross sells. " + e + " : " + e.getMessage(), module);
		} catch (GenericServiceException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while getting the cross sells. " + e + " : " + e.getMessage(), module);
		}

		return products;
	}

	/*
	 * Get list of cart items
	 */
	public static List<GenericValue> getCartAdjustments(ShoppingCart cart) {
		if(cart == null) {
			return null;
		}

		List<GenericValue> cartPromoList = new ArrayList<GenericValue>();

		List<GenericValue> cartAdjustments = cart.getAdjustments();
		if(cartAdjustments != null) {
			for(GenericValue checkOrderAdjustment : cartAdjustments) {
				cartPromoList.add(checkOrderAdjustment);
			}
		}

		return cartPromoList;
	}

	/*
	 * Set the cart shipping method type
	 */
	public static void setShipmentMethodTypeId(LocalDispatcher dispatcher, ShoppingCart cart, int shipGroupIndex, String shipmentMethodTypeId, String carrierPartyId) {
		cart.setShipmentMethodTypeId(shipGroupIndex, shipmentMethodTypeId);
		cart.setCarrierPartyId(shipGroupIndex, carrierPartyId);

		//we have to redo promos everytime incase they have a promo ship charge adjustment
		if(dispatcher != null && cart.getDoPromotions()) {
			ProductPromoWorker.doPromotions(cart, dispatcher);
		}
	}

	/*
	 * Set the cart shipping total
	 */
	public static void setShippingTotal(ShoppingCart cart, int shipGroupIndex, BigDecimal shippingTotal) {
		if(shippingTotal == null) {
			shippingTotal = BigDecimal.ZERO;
		}
		cart.setItemShipGroupEstimate(shippingTotal, shipGroupIndex);
	}

	/*
	 * Get a quantity of a cart item
	 */
	public static BigDecimal getCartItemQuantity(ShoppingCartItem cartItem) {
		if(cartItem == null) {
			return null;
		}

		return cartItem.getQuantity();
	}

	/*
	 * Set tax total
	 */
	public static void setTaxTotal(LocalDispatcher dispatcher, Map<String, String> postalAddress, ShoppingCart cart) throws GenericServiceException {
		if(UtilValidate.isEmpty(postalAddress) && UtilValidate.isNotEmpty(cart.getShipTaxPostalAddress())) {
			postalAddress = cart.getShipTaxPostalAddress();
		}

		if(UtilValidate.isNotEmpty(postalAddress)) {
			int shipGroups = cart.getShipGroupSize();
			for(int i = 0; i < shipGroups; i++) {
				ShoppingCart.CartShipInfo csi = cart.getShipInfo(i);
				csi.clearAllTaxInfo();
				csi.shipTaxAdj.clear();

				if(!cart.getNonTaxableStatus()) {
					Map<String, Object> context = TaxHelper.makeTaxContext(i, cart, cart.getDelegator().makeValidValue("PostalAddress", postalAddress));
					Map<String, Object> taxReturn = dispatcher.runSync("calcTax", context);
					if(UtilValidate.isNotEmpty(taxReturn) && UtilValidate.isNotEmpty(taxReturn.get("orderAdjustments"))) {
						csi.shipTaxAdj.addAll((List<GenericValue>) taxReturn.get("orderAdjustments"));
					}
				}
			}
		}
	}

	/*
	 * Set the postalCode in the cart, this is an added variable for ENV to be used when new items are added to the cart
	 */
	public static void setShipTaxPostalAddress(ShoppingCart cart, Map<String, String> shipTaxPostalAddress) {
		if(cart != null && UtilValidate.isNotEmpty(shipTaxPostalAddress) && UtilValidate.isNotEmpty(shipTaxPostalAddress.get("postalCode"))) {
			cart.setShipTaxPostalAddress(shipTaxPostalAddress);
		}
	}

	/*
	 * Does cart contain free shipping only items
	 * This method will eventually replace IS ONLY SWATCHBOOK and IS ONLY SAMPLE where it is being used to check if free shipping should be applied.
	 * The other methods conflict with one another.
	 */
	public static boolean isAllowedFreeShipping(ShoppingCart cart) {
		boolean isAllowedFreeShipping = false;

		List<ShoppingCartItem> cartLines = cart.items();
		for (ShoppingCartItem sci : cartLines) {
			if(!sci.getProductId().toLowerCase().contains("swatchbook") && !((UtilValidate.isEmpty(sci.getProduct().getString("hasSample")) || "Y".equalsIgnoreCase(sci.getProduct().getString("hasSample"))) && sci.getQuantity().compareTo(new BigDecimal("5")) <= 0 && !sci.getProductId().contains("SWATCHBOOK") || EnvConstantsUtil.SAMPLE_PRODUCT.equalsIgnoreCase(sci.getProduct().getString("productId")))) {
				isAllowedFreeShipping = false;
				break;
			} else {
				isAllowedFreeShipping = true;
			}
		}
		return isAllowedFreeShipping;
	}

	/*
	 * Does cart only contain swatchbook
	 */
	public static boolean isOnlySwatchBook(ShoppingCart cart) {
		boolean isOnlySwatchBook = false;

		List<ShoppingCartItem> cartLines = cart.items();
		for (ShoppingCartItem sci : cartLines) {
			if(!sci.getProductId().toLowerCase().contains("swatchbook")) {
				isOnlySwatchBook = false;
				break;
			} else {
				isOnlySwatchBook = true;
			}
		}

		return isOnlySwatchBook;
	}

	/*
	 * Does cart contain swatchbook
	 */
	public static boolean hasSwatchBook(ShoppingCart cart) {
		boolean hasSwatchBook = false;

		List<ShoppingCartItem> cartLines = cart.items();
		for (ShoppingCartItem sci : cartLines) {
			if(sci.getProductId().toLowerCase().contains("swatchbook")) {
				hasSwatchBook = true;
				break;
			}
		}

		return hasSwatchBook;
	}

	/*
	 * Does the cart only contain samples
	 */
	public static boolean isOnlySample(ShoppingCart cart) {
		boolean isOnlySample = false;

		List<ShoppingCartItem> cartLines = cart.items();
		for (ShoppingCartItem sci : cartLines) {
			if((UtilValidate.isEmpty(sci.getProduct().getString("hasSample")) || "Y".equalsIgnoreCase(sci.getProduct().getString("hasSample"))) && sci.getQuantity().compareTo(new BigDecimal("5")) <= 0 && !sci.getProductId().contains("SWATCHBOOK")) {
				isOnlySample = true;
			} else if(EnvConstantsUtil.SAMPLE_PRODUCT.equalsIgnoreCase(sci.getProduct().getString("productId"))) {
				isOnlySample = true;
			} else {
				isOnlySample = false;
				break;
			}
		}

		return isOnlySample;
	}

	/*
	 * Does cart have quotes being ordered
	 */
	public static boolean hasQuote(ShoppingCart cart) {
		boolean hasQuote = false;

		List<ShoppingCartItem> cartLines = cart.items();
		for (ShoppingCartItem sci : cartLines) {
			if(UtilValidate.isNotEmpty(sci.getAttribute("envQuoteAttributes"))) {
				Map<String, Object> envQuoteAttributes = (Map<String, Object>) sci.getAttribute("envQuoteAttributes");
				hasQuote = UtilValidate.isNotEmpty(envQuoteAttributes.get("quoteId"));
				break;
			}
		}

		return hasQuote;
	}

	/**
	 * Return if the item is a quote
	 * @param item
	 * @return
	 */
	public static boolean isQuote(ShoppingCartItem item) {
		boolean isQuote = false;

		if(UtilValidate.isNotEmpty(item.getAttribute("envQuoteAttributes"))) {
			Map<String, Object> envQuoteAttributes = (Map<String, Object>) item.getAttribute("envQuoteAttributes");
			isQuote = UtilValidate.isNotEmpty(envQuoteAttributes.get("quoteId"));
		}

		return isQuote;
	}

	/*
	 * Does cart have printed items being ordered
	 */
	public static boolean hasPrinted(ShoppingCart cart) {
		boolean hasPrinted = false;

		List<ShoppingCartItem> cartLines = cart.items();
		for (ShoppingCartItem sci : cartLines) {
			if(UtilValidate.isNotEmpty(sci.getAttribute("envPriceCalcAttributes"))) {
				Map<String, Object> envPriceCalcAttributes = (Map<String, Object>) sci.getAttribute("envPriceCalcAttributes");
				if(UtilValidate.isNotEmpty(envPriceCalcAttributes) && ((envPriceCalcAttributes.containsKey("colorsFront") && UtilValidate.isNotEmpty(envPriceCalcAttributes.get("colorsFront")) && (Integer) envPriceCalcAttributes.get("colorsFront") > 0) || (envPriceCalcAttributes.containsKey("colorsBack") && UtilValidate.isNotEmpty(envPriceCalcAttributes.get("colorsBack")) && (Integer) envPriceCalcAttributes.get("colorsBack") > 0))) {
					hasPrinted = true;
					break;
				}
			}
		}

		return hasPrinted;
	}

	/**
	 * Return if the item is printed
	 * @param item
	 * @return
	 */
	public static boolean isItemPrinted(ShoppingCartItem item) {
		boolean isItemPrinted = false;

		Map<String, Object> priceCalcAttributes = (UtilValidate.isNotEmpty(item.getAttribute("envPriceCalcAttributes"))) ? (HashMap) item.getAttribute("envPriceCalcAttributes") : new HashMap<>();

		if(UtilValidate.isNotEmpty(priceCalcAttributes) && ((priceCalcAttributes.containsKey("colorsFront") && UtilValidate.isNotEmpty(priceCalcAttributes.get("colorsFront")) && (Integer) priceCalcAttributes.get("colorsFront") > 0) || (priceCalcAttributes.containsKey("colorsBack") && UtilValidate.isNotEmpty(priceCalcAttributes.get("colorsBack")) && (Integer) priceCalcAttributes.get("colorsBack") > 0))) {
			isItemPrinted = true;
		}

		return isItemPrinted;
	}

	/**
	 * Return product type of item
	 * @param item
	 * @return
	 */
	public static String getProductTypeId(ShoppingCartItem item) {
		GenericValue product = item.getProduct();
		return product.getString("productTypeId");
	}

	/**
	 * Return if an item ships for free
	 * @param item
	 * @return
	 */
	public static boolean isFreeShipItem(ShoppingCartItem item) {
		//if its a printed folders product, it ships free
		boolean shipQuoteForFree = UtilProperties.getPropertyAsBoolean("envelopes", "shipment.quotes.free.ship", false).booleanValue();

		if(shipQuoteForFree) {
			GenericValue product = item.getProduct();
			if ("FOLDER".equalsIgnoreCase(product.getString("productTypeId"))) {
				Map<String, Object> priceCalcAttributes = (UtilValidate.isNotEmpty(item.getAttribute("envPriceCalcAttributes"))) ? (HashMap) item.getAttribute("envPriceCalcAttributes") : new HashMap<>();
				if (UtilValidate.isNotEmpty(priceCalcAttributes) && ((priceCalcAttributes.containsKey("colorsFront") && UtilValidate.isNotEmpty(priceCalcAttributes.get("colorsFront")) && (Integer) priceCalcAttributes.get("colorsFront") > 0) || (priceCalcAttributes.containsKey("colorsBack") && UtilValidate.isNotEmpty(priceCalcAttributes.get("colorsBack")) && (Integer) priceCalcAttributes.get("colorsBack") > 0))) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean hasFreeShipItem(ShoppingCart cart) {
		List cartItems = cart.items();
		Iterator cartItemIter = cartItems.iterator();
		while(cartItemIter.hasNext()) {
			ShoppingCartItem cartItem = (ShoppingCartItem) cartItemIter.next();
			if(isFreeShipItem(cartItem) || isQuote(cartItem)) {
				return true;
			}
		}

		return false;
	}
	/*
	 * Build price param map
	 */
	public static Map<String, Object> buildPriceContext(Map<String, Object> context) {
		Map<String, Object> params = new HashMap<String, Object>();
		for(Map.Entry<String, Object> param : context.entrySet()) {
			if(UtilValidate.isNotEmpty(param.getValue())) {
				if(param.getKey().equals("id") || param.getKey().equals("partyId") || param.getKey().equals("name") || param.getKey().equals("templateId") || param.getKey().equals("pricingRequest")) {
					params.put((String)param.getKey(), (String) param.getValue());
				} else if(param.getKey().equals("colorsFront") || param.getKey().equals("colorsBack") || param.getKey().equals("cuts") || param.getKey().equals("addresses")) {
					params.put((String)param.getKey(), Integer.valueOf((String) param.getValue()));
				} else if(param.getKey().equals("isRush") || param.getKey().equals("whiteInkFront") || param.getKey().equals("whiteInkBack") || param.getKey().equals("isFolded") || param.getKey().equals("isFullBleed")) {
					params.put((String)param.getKey(), Boolean.valueOf((String) param.getValue()));
				} else if(param.getKey().equals("quantity") || param.getKey().equals("price") || param.getKey().equals("weight")) {
					params.put((String)param.getKey(), new BigDecimal((String) param.getValue()));
				}
			}
		}

		return params;
	}

	/*
	 * Build item artwork param map
	 */
	public static Map<String, Object> buildArtworkContext(Map<String, Object> context) {
		Map<String, Object> params = new HashMap<String, Object>();
		for(Map.Entry<String, Object> param : context.entrySet()) {
			if(param.getKey().equals("itemJobName") || param.getKey().equals("itemInkColor") || param.getKey().equals("itemPrintPosition") || param.getKey().equals("scene7DesignId") || param.getKey().equals("scene7ParentId") || param.getKey().equals("directMailJobId") || param.getKey().equals("isProduct") || param.getKey().equals("designId") || param.getKey().equals("artworkSource") || param.getKey().equals("token")) {
				params.put((String)param.getKey(), (String) param.getValue());
			} else if(param.getKey().equals("fileName[]")) {
				List<Map> content = new ArrayList<Map>();
				if(param.getValue() instanceof LinkedList) {
					List fileNames          = (LinkedList) param.getValue();
					List filePaths          = (LinkedList) context.get("filePath[]");
					List fileOrderIds       = (LinkedList) context.get("fileOrder[]");
					List fileOrderItemSeqId = (LinkedList) context.get("fileOrderItem[]");
					for(int i = 0; i < fileNames.size(); i++) {
						Map fileData = new HashMap();
						fileData.put("contentName", fileNames.get(i));
						fileData.put("contentPath", filePaths.get(i));
						fileData.put("reOrderId", UtilValidate.isNotEmpty(fileOrderIds.get(i)) ? fileOrderIds.get(i) : null);
						fileData.put("reOrderItemSeqId", UtilValidate.isNotEmpty(fileOrderItemSeqId.get(i)) ? fileOrderItemSeqId.get(i) : null);
						fileData.put("contentPurposeEnumId", "OIACPRP_FILE");
						content.add(fileData);
					}
				} else if(param.getValue() instanceof String) {
					Map fileData = new HashMap();
					fileData.put("contentName", (String) param.getValue());
					fileData.put("contentPath", (String) context.get("filePath[]"));
					fileData.put("reOrderId", (String) context.get("fileOrder[]"));
					fileData.put("reOrderItemSeqId", (String) context.get("fileOrderItem[]"));
					fileData.put("contentPurposeEnumId", "OIACPRP_FILE");
					content.add(fileData);
				}

				params.put("content", content);
			}
		}

		return params;
	}

	/*
	 * Build item quote param map
	 */
	public static Map<String, Object> buildQuoteContext(Map<String, Object> context) {
		Map<String, Object> params = new HashMap<String, Object>();
		for(Map.Entry<String, Object> param : context.entrySet()) {
			if(param.getKey().equals("quoteId") || param.getKey().equals("quoteItemSeqId")) {
				params.put((String)param.getKey(), (String) param.getValue());
			}
		}

		return params;
	}

	/*
	 * Build price update context for updating a cart item
	 */
	public static Map<String, Object> rebuildPriceContext(Map<String, Object> context, ShoppingCart cart, ShoppingCartItem item) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> envPriceCalcAttributes = (Map<String, Object>) item.getAttribute("envPriceCalcAttributes");

		//error check
		if(envPriceCalcAttributes == null) {
			envPriceCalcAttributes = new HashMap<>();
		}

		//check the existing attributes and create a new map of them
		for(Map.Entry<String, Object> param : envPriceCalcAttributes.entrySet()) {
			if(param.getKey().equals("partyId") || param.getKey().equals("name") || param.getKey().equals("templateId") || param.getKey().equals("pricingRequest")) {
				params.put((String)param.getKey(), (String) param.getValue());
			} else if(param.getKey().equals("colorsFront") || param.getKey().equals("colorsBack") || param.getKey().equals("cuts") || param.getKey().equals("addresses")) {
				params.put((String)param.getKey(), (Integer) param.getValue());
			} else if(param.getKey().equals("isRush") || param.getKey().equals("whiteInkFront") || param.getKey().equals("whiteInkBack") || param.getKey().equals("isFolded") || param.getKey().equals("isFullBleed")) {
				params.put((String)param.getKey(), (Boolean) param.getValue());
			} else if(param.getKey().equals("price") || param.getKey().equals("weight")) {
				params.put((String)param.getKey(), (BigDecimal) param.getValue());
			}
		}

		//check the new attributes passed in and replace the existing ones
		for(Map.Entry<String, Object> param : context.entrySet()) {
			if(UtilValidate.isNotEmpty(param.getValue())) {
				if(param.getKey().equals("partyId") || param.getKey().equals("name") || param.getKey().equals("templateId") || param.getKey().equals("pricingRequest")) {
					params.put((String)param.getKey(), (String) param.getValue());
				} else if(param.getKey().equals("colorsFront") || param.getKey().equals("colorsBack") || param.getKey().equals("cuts") || param.getKey().equals("addresses")) {
					params.put((String)param.getKey(), Integer.valueOf((String) param.getValue()));
				} else if(param.getKey().equals("isRush") || param.getKey().equals("whiteInkFront") || param.getKey().equals("whiteInkBack") || param.getKey().equals("isFolded") || param.getKey().equals("isFullBleed")) {
					params.put((String)param.getKey(), Boolean.valueOf((String) param.getValue()));
				} else if(param.getKey().equals("price") || param.getKey().equals("weight")) {
					params.put((String)param.getKey(), new BigDecimal((String) param.getValue()));
				}
			}
		}

		//clear existing item map and replace with the newly built one
		envPriceCalcAttributes.clear();
		for(Map.Entry<String, Object> param : params.entrySet()) {
			if(param.getKey().equals("partyId") || param.getKey().equals("name") || param.getKey().equals("templateId") || param.getKey().equals("pricingRequest")) {
				envPriceCalcAttributes.put((String)param.getKey(), (String) param.getValue());
			} else if(param.getKey().equals("colorsFront") || param.getKey().equals("colorsBack") || param.getKey().equals("cuts") || param.getKey().equals("addresses")) {
				envPriceCalcAttributes.put((String)param.getKey(), (Integer) param.getValue());
			} else if(param.getKey().equals("isRush") || param.getKey().equals("whiteInkFront") || param.getKey().equals("whiteInkBack") || param.getKey().equals("isFolded") || param.getKey().equals("isFullBleed")) {
				envPriceCalcAttributes.put((String)param.getKey(), (Boolean) param.getValue());
			} else if(param.getKey().equals("price") || param.getKey().equals("weight")) {
				envPriceCalcAttributes.put((String)param.getKey(), (BigDecimal) param.getValue());
			}
		}

		item.setAttribute("envPriceCalcAttributes", envPriceCalcAttributes);
		return params;
	}

	/*
	 * Check if the cart has a printed item and rush or standard or no printed items
	 * TODO: this can probably be sped up by storing in cart object
	 */
	public static int getProductionTime(Delegator delegator, ShoppingCart cart) {
		int days = 0;

		List<Map> cartItemList = CartHelper.getCartItems(cart);
		for(Map cartItem : cartItemList) {

			boolean isPrinted = false;
			boolean isRush = false;
			boolean isScene7 = false;
			Map<String, Integer> leadTime = null;

			Map<String, Object> priceCalcAttributes = cartItem.containsKey("envPriceCalcAttributes") ? (HashMap)cartItem.get("envPriceCalcAttributes") : new HashMap<>();
			Map<String, Object> artworkAttributes = cartItem.containsKey("envArtworkAttributes") ? (HashMap)cartItem.get("envArtworkAttributes") : new HashMap<>();
			Map<String, Object> quoteAttributes = cartItem.containsKey("envQuoteAttributes") ? (HashMap)cartItem.get("envQuoteAttributes") : new HashMap<>();

			if(UtilValidate.isNotEmpty(priceCalcAttributes) && ((priceCalcAttributes.containsKey("colorsFront") && UtilValidate.isNotEmpty(priceCalcAttributes.get("colorsFront")) && (Integer) priceCalcAttributes.get("colorsFront") > 0) || (priceCalcAttributes.containsKey("colorsBack") && UtilValidate.isNotEmpty(priceCalcAttributes.get("colorsBack")) && (Integer) priceCalcAttributes.get("colorsBack") > 0))) {
				isPrinted = true;
			}

			if(isPrinted && UtilValidate.isNotEmpty(priceCalcAttributes) && priceCalcAttributes.containsKey("isRush") && (Boolean) priceCalcAttributes.get("isRush")) {
				isRush = true;
			}

			if(isPrinted && UtilValidate.isNotEmpty(artworkAttributes) && artworkAttributes.containsKey("scene7DesignId") && UtilValidate.isNotEmpty(artworkAttributes.get("scene7DesignId"))) {
				isScene7 = true;
			}

			try {
				leadTime = ProductHelper.getLeadTime(delegator, (String) cartItem.get("productId"));
			}
			catch (GenericEntityException e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to get lead time in cart helper getProductionTime method." + e + " : " + e.getMessage(), module);
			}

			int productionTime = OrderHelper.getProductionTime(leadTime, isRush, isScene7, isPrinted);

			if(productionTime > days) {
				days = productionTime;
			}
		}

		return days;
	}

	public static void deletePersistentCart(Delegator delegator, String cartId) throws GenericEntityException {
		delegator.removeByAnd("PersistentCart", UtilMisc.toMap("id", cartId));
	}

	public static String addToCart(HttpServletRequest request, HttpServletResponse response) {
		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		List<ShoppingCartItem> cartLines = cart.items();

		/* May not need this, as during a rebuild...it rebuilds the cart.
		for (int i = 0; i < cartLines.size(); i++) {
			if (i >= cartLimit) {
				cart.items().remove(i);
				i--;
			}
		}
		*/

		if (cartLines.size() < cartLimit) {
			return ShoppingCartEvents.addToCart(request, response);
		} else {
			return "success";
		}
	}
}