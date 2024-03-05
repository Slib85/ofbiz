/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.iparcel;

import java.lang.*;
import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.envelopes.util.*;
import com.envelopes.party.PartyHelper;
import com.envelopes.cart.CartHelper;

import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.order.shoppingcart.ShoppingCart;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartEvents;
import org.apache.ofbiz.service.LocalDispatcher;

public class IPSetCheckoutRequest {
	public static final String module = IPSetCheckoutRequest.class.getName();

	protected Map<String, Object> requestObj = null;
	protected LocalDispatcher dispatcher = null;
	protected Delegator delegator = null;
	protected ShoppingCart cart = null;
	protected Map<String, Object> context = null;

	/**
	 * IPSetCheckoutRequest Constructor
	 * @param request
	 */
	public IPSetCheckoutRequest(HttpServletRequest request) {
		this.dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		this.delegator = (Delegator) request.getAttribute("delegator");
		this.cart = ShoppingCartEvents.getCartObject(request);
		this.context = EnvUtil.getParameterMap(request);

		this.requestObj = new HashMap<>();
	}

	public Map<String, Object> getRequest() {
		return this.requestObj;
	}

	public String getRequestAsJSON() {
		Gson gson = new GsonBuilder().serializeNulls().create();
		return gson.toJson(this.requestObj);
	}

	/**
	 * Set the actual request contents of IPSetCheckoutRequest constructor
	 * @param request
	 */
	protected void createRequest(HttpServletRequest request) {
		if(request != null && this.cart != null) {
			this.requestObj.put("key", IParcelEvents.API_KEY);
			this.requestObj.put("currency_code", IParcelEvents.CURRENCY);
			this.requestObj.put("cn", IParcelEvents.REDIRECT_MESSAGE);
			this.requestObj.put("no_note", IParcelEvents.SHOW_RED_MESSAGE);
			this.requestObj.put("image_url", IParcelEvents.LOGO);
			this.requestObj.put("return", IParcelEvents.buildURL(request, IParcelEvents.RETURN_URL));
			this.requestObj.put("shopping_url", IParcelEvents.SHOPPING_URL);
			this.requestObj.put("cancel_return", IParcelEvents.CANCEL_URL);

			try {
				createItemDetailsList(request);
				createAddressInfo(request);
				createPromoList(request);
				createDayPhone(request);
				createNightPhone(request);
			} catch (Exception e) {
				EnvUtil.reportError(e);
			}

		}
	}

	public void setTempOrderId(String id) {
		this.requestObj.put("custom", id);
	}

	/**
	 * Create the item level detail
	 * @param request
	 */
	private void createItemDetailsList(HttpServletRequest request) {
		List<Map> itemDetailsList = new ArrayList<>();

		List<Map> cartItems = CartHelper.getCartItems(this.cart);
		for(Map item : cartItems) {
			Map<String, Object> itemDetail = new HashMap<>();
			itemDetail.put("item_number", item.get("productId"));
			itemDetail.put("quantity", ((BigDecimal) item.get("quantity")).intValueExact());
			itemDetail.put("item_name", null);
			itemDetail.put("amount", item.get("totalPrice"));

			itemDetailsList.add(itemDetail);
		}

		this.requestObj.put("ItemDetailsList", itemDetailsList);
	}

	/**
	 * Create the address level detail
	 * @param request
	 */
	private void createAddressInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> addressInfo = new HashMap<>();

		addressInfo.put("Billing", createBilling(request));
		addressInfo.put("Shipping", createShipping(request));

		this.requestObj.put("AddressInfo", addressInfo);
	}

	/**
	 * Create the billing level detail
	 * @param request
	 * @return Map
	 */
	private Map<String, Object> createBilling(HttpServletRequest request) throws Exception {
		Map<String, String> billingAddress = PartyHelper.getPostalAddressMap(request, false);
		Map<String, Object> billing = new HashMap<>();
		billing.put("email", this.context.get("emailAddress"));
		billing.put("first_name", billingAddress.get("firstName"));
		billing.put("last_name", billingAddress.get("lastName"));
		billing.put("address1", billingAddress.get("address1"));
		billing.put("address2", billingAddress.get("address2"));
		billing.put("city", billingAddress.get("city"));
		billing.put("state", billingAddress.get("stateProvinceGeoId"));
		billing.put("zip", billingAddress.get("postalCode"));
		billing.put("country", getGeoCodeFromGeoId((Delegator) request.getAttribute("delegator"), billingAddress.get("countryGeoId")));

		return billing;
	}

	/**
	 * Create the shipping level detail
	 * @param request
	 * @return Map
	 */
	private Map<String, Object> createShipping(HttpServletRequest request) throws Exception {
		Map<String, String> shippingAddress = PartyHelper.getPostalAddressMap(request, true);
		Map<String, Object> shipping = new HashMap<>();
		shipping.put("shipping_email", this.context.get("emailAddress"));
		shipping.put("shipping_first_name", shippingAddress.get("firstName"));
		shipping.put("shipping_last_name", shippingAddress.get("lastName"));
		shipping.put("shipping_address1", shippingAddress.get("address1"));
		shipping.put("shipping_address2", shippingAddress.get("address2"));
		shipping.put("shipping_city", shippingAddress.get("city"));
		shipping.put("shipping_state", shippingAddress.get("stateProvinceGeoId"));
		shipping.put("shipping_zip", shippingAddress.get("postalCode"));
		shipping.put("shipping_country", getGeoCodeFromGeoId((Delegator) request.getAttribute("delegator"), shippingAddress.get("countryGeoId")));

		return shipping;
	}

	/**
	 * Create the promo list
	 * @param request
	 */
	private void createPromoList(HttpServletRequest request) throws GenericEntityException {
		List<Map> promoList = new ArrayList<>();

		BigDecimal totalDiscount = BigDecimal.ZERO;

		Map<String, String> promoCodes = new HashMap<>();

		Set<String> enteredPromos = this.cart.getProductPromoCodesEntered();
		for(String promoCodeId : enteredPromos) {
			promoCodes.put(getPromoPromoIdFromCode((Delegator) request.getAttribute("delegator"), promoCodeId), promoCodeId);
		}

		List<GenericValue> cartPromoList = CartHelper.getCartAdjustments(this.cart);
		for (GenericValue promo : cartPromoList) {
			Map<String, Object> promoData = new HashMap<>();
			promoData.put("promo_code", (UtilValidate.isNotEmpty(promoCodes.get(promo.getString("productPromoId")))) ? promoCodes.get(promo.getString("productPromoId")) : promo.getString("productPromoId"));
			promoData.put("promo_description", promo.get("description"));

			promoList.add(promoData);
			totalDiscount = totalDiscount.add(((BigDecimal) promo.get("amount")).abs());
		}

		this.requestObj.put("PromoList", promoList);
		this.requestObj.put("discount_amount_cart", totalDiscount);
	}

	/**
	 * Create the billing phone
	 * @param request
	 */
	private void createDayPhone(HttpServletRequest request) {
		Map<String, String> billingPhone = PartyHelper.getTelecomMap(request, false);

		this.requestObj.put("day_phone_a", (UtilValidate.isNotEmpty(billingPhone.get("contactNumber")) && (billingPhone.get("contactNumber")).length() >= 3) ? billingPhone.get("contactNumber").substring(0,3) : "000");
		this.requestObj.put("day_phone_b", (UtilValidate.isNotEmpty(billingPhone.get("contactNumber")) && (billingPhone.get("contactNumber")).length() > 3) ? billingPhone.get("contactNumber").substring(3) : billingPhone.get("contactNumber"));
	}

	/**
	 * Create the shipping phone
	 * @param request
	 */
	private void createNightPhone(HttpServletRequest request) {
		Map<String, String> shippingPhone = PartyHelper.getTelecomMap(request, true);

		this.requestObj.put("night_phone_a", (UtilValidate.isNotEmpty(shippingPhone.get("contactNumber")) && (shippingPhone.get("contactNumber")).length() >= 3) ? shippingPhone.get("contactNumber").substring(0,3) : "000");
		this.requestObj.put("night_phone_b", (UtilValidate.isNotEmpty(shippingPhone.get("contactNumber")) && (shippingPhone.get("contactNumber")).length() > 3) ? shippingPhone.get("contactNumber").substring(3) : shippingPhone.get("contactNumber"));
	}

	private String getGeoCodeFromGeoId(Delegator delegator, String geoId) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(geoId)) {
			GenericValue geo = delegator.findOne("Geo", UtilMisc.toMap("geoId", geoId), true);
			if(UtilValidate.isNotEmpty(geo)) {
				return geo.getString("geoCode");
			}
		}

		return null;
	}

	private String getPromoPromoIdFromCode(Delegator delegator, String productPromoCodeId) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(productPromoCodeId)) {
			GenericValue productPromoCode = delegator.findOne("ProductPromoCode", UtilMisc.toMap("productPromoCodeId", productPromoCodeId), true);
			if(productPromoCode != null) {
				return productPromoCode.getString("productPromoId");
			}
		}

		return null;
	}
}