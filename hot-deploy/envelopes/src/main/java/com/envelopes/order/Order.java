/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.order;

import java.lang.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.envelopes.cart.CartHelper;
import com.envelopes.cart.PersistentCart;
import com.envelopes.promo.PromoHelper;

import com.envelopes.util.*;

import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.order.shoppingcart.ShoppingCart;
import org.apache.ofbiz.order.shoppingcart.WebShoppingCart;
import org.apache.ofbiz.product.catalog.CatalogWorker;
import org.apache.ofbiz.product.store.ProductStoreWorker;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.webapp.website.WebSiteWorker;

public class Order {
	public static final String module = Order.class.getName();

	protected ShoppingCart cart = null;
	protected HttpServletRequest request = null;
	protected HttpServletResponse response = null;
	protected HttpSession session = null;

	public LocalDispatcher dispatcher = null;
	public Delegator delegator = null;
	public GenericValue productStore = null;
	public String catalogName = null;
	public String catalogId = null;
	public String webSiteId = null;
	public GenericValue webSite = null;
	public Locale locale = null;

	/**
	 * Create the order process
	 * @param request
	 * @param response
	 * @return
	 */
	public static String createOrder(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		String webSiteId = (String) request.getAttribute("_WEB_SITE_ID_");
		String externalId = (String) request.getAttribute("externalId");
		String tempOrderId = (String) request.getAttribute("tempOrderId");
		Map<String, String> tempShippingAddress = (Map<String, String>) request.getAttribute("tempShippingAddress");
		Map<String, String> tempBillingAddress = (Map<String, String>) request.getAttribute("tempBillingAddress");
		String tempShippingPhone = (String) request.getAttribute("tempShippingPhone");
		String tempBillingPhone = (String) request.getAttribute("tempBillingPhone");
		String tempPaymentMethodTypeId = (String) request.getAttribute("tempPaymentMethodTypeId");
		String tempEmail = (String) request.getAttribute("tempEmail");
		Map<String, Object> shipDetails = (Map<String, Object>) request.getAttribute("shipDetails");
		List<Map> tempCart = (List<Map>) request.getAttribute("tempCart");
		List promoDetails = (List) request.getAttribute("promoDetails");
		Timestamp orderDate = (Timestamp) request.getAttribute("orderDate");

		Order order = new Order(request, response);

		try {
			order.createNewSession();
			order.createNewCart();
			if(UtilValidate.isNotEmpty(tempOrderId)) {
				order.loadCart(tempOrderId);
			} else {
				order.loadCart(tempCart);
			}

			if(UtilValidate.isNotEmpty(orderDate)) {
				order.setOrderDate(orderDate);
			}
			order.setShipping((String) shipDetails.get("shipMethod"), (String) shipDetails.get("carrierPartyId"), (BigDecimal) shipDetails.get("shipCost"));
			order.setTaxTotal((BigDecimal) shipDetails.get("tax"));
			order.setDutyTotal((BigDecimal) shipDetails.get("duty"));
			order.applyPromos(promoDetails);

			request.setAttribute("_WEB_SITE_ID_", webSiteId);
			request.setAttribute("externalId", externalId);
			request.setAttribute("tempShippingAddress", tempShippingAddress);
			request.setAttribute("tempBillingAddress", tempBillingAddress);
			request.setAttribute("tempShippingPhone", tempShippingPhone);
			request.setAttribute("tempBillingPhone", tempBillingPhone);
			request.setAttribute("tempEmail", tempEmail);
			request.setAttribute("shipDetails", shipDetails);
			request.setAttribute("promoDetails", promoDetails);
			request.setAttribute("paymentMethodTypeId", tempPaymentMethodTypeId);

			return CheckoutEvents.createOrder(request, response);
		} catch (Exception e) {
			Debug.logError(e, "Error trying to create order.", module);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/**
	 * Empty Order constructor
	 */
	public Order(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	/**
	 * Create a new session for this request
	 */
	public void createNewSession() {
		this.request.getSession().invalidate();
		this.session = this.request.getSession();

		this.dispatcher = (LocalDispatcher) this.request.getAttribute("dispatcher");
		this.delegator = (Delegator) this.request.getAttribute("delegator");
		this.productStore = ProductStoreWorker.getProductStore(this.request);
		this.catalogName = CatalogWorker.getCatalogName(this.request);
		this.catalogId = CatalogWorker.getCurrentCatalogId(this.request);
		this.webSiteId = WebSiteWorker.getWebSiteId(this.request);
		this.webSite = WebSiteWorker.getWebSite(this.request);
		this.locale = UtilHttp.getLocale(this.request);
	}

	/**
	 * Create a new cart
	 */
	public void createNewCart() {
		this.cart = new WebShoppingCart(this.request, null, null);
		this.session.setAttribute("shoppingCart", this.cart);
		CartHelper.setCartDefaults(this.cart, this.request, null, null, null);
	}

	public void setOrderDate(Timestamp date) {
		this.cart.setOrderDate(date);
	}

	public void setOrderType(String orderTypeId) {
		if(UtilValidate.isEmpty(orderTypeId)) {
			this.cart.setOrderType("SALES_ORDER");
		} else {
			this.cart.setOrderType(orderTypeId);
		}
	}

	public void setChannelType(String salesChannelEnumId) {
		if(UtilValidate.isEmpty(salesChannelEnumId)) {
			this.cart.setChannelType("ENV_SALES_CHANNEL");
		} else {
			this.cart.setChannelType(salesChannelEnumId);
		}
	}

	/**
	 * Load a saved cart if available
	 * @param tempOrderId
	 */
	public void loadCart(String tempOrderId) throws Exception {
		if(UtilValidate.isNotEmpty(tempOrderId)) {
			PersistentCart.load(this.request, this.response, tempOrderId);
		}
	}

	/**
	 * Load a saved cart if available
	 * @param items
	 */
	public void loadCart(List<Map> items) throws Exception {
		if(UtilValidate.isNotEmpty(items)) {
			PersistentCart.load(this.request, this.response, null, items);
		}
	}

	/**
	 * Re enter all promos
	 * @param enteredPromos
	 * @throws Exception
	 */
	public void reprocessEnteredPromos(List<String> enteredPromos) throws Exception {
		//re-apply promotions
		Iterator<String> promoCodeIter = enteredPromos.iterator();
		while (promoCodeIter.hasNext()) {
			String productPromoCodeId = promoCodeIter.next();
			PromoHelper.validateCouponUsage(this.delegator, this.dispatcher, this.session, this.cart, UtilMisc.toMap("productPromoId", productPromoCodeId), new HashMap<String, Object>());
		}
	}

	/**
	 * Set the shipping
	 * @param shipmentMethodTypeId
	 * @param shipPartyId
	 * @param shippingTotal
	 */
	public void setShipping(String shipmentMethodTypeId, String shipPartyId, BigDecimal shippingTotal) {
		CartHelper.setShipmentMethodTypeId(this.dispatcher, this.cart, 0, shipmentMethodTypeId, shipPartyId);
		CartHelper.setShippingTotal(this.cart, 0, shippingTotal);
	}

	/**
	 * Set the tax
	 * @param amount
	 * @throws Exception
	 */
	public void setTaxTotal(BigDecimal amount) throws Exception {
		GenericValue salesTax = this.delegator.makeValue("OrderAdjustment", UtilMisc.toMap("orderAdjustmentTypeId", "FEE", "shipGroupSeqId", "00001", "description", "Tax", "amount", amount, "createdDate", UtilDateTime.nowTimestamp()));
		this.cart.addAdjustment(salesTax);
	}

	public void applyPromos(List promos) throws GenericEntityException, GenericServiceException {
		Iterator promoIter = promos.iterator();
		while(promoIter.hasNext()) {
			Map promo = (Map) promoIter.next();
			String productPromoCodeId = (String) promo.get("promo_code");
			if(UtilValidate.isNotEmpty(productPromoCodeId) && UtilValidate.isNotEmpty(getPromoPromoCode(this.delegator, productPromoCodeId))) {
				PromoHelper.validateCouponUsage(this.delegator, this.dispatcher, this.request.getSession(), this.cart, UtilMisc.<String, Object>toMap("productPromoCodeId", productPromoCodeId), new HashMap<String, Object>());
			}
		}
	}

	/**
	 * Set the Duty
	 * @param amount
	 * @throws Exception
	 */
	public void setDutyTotal(BigDecimal amount) throws Exception {
		GenericValue salesTax = this.delegator.makeValue("OrderAdjustment", UtilMisc.toMap("orderAdjustmentTypeId", "FEE", "shipGroupSeqId", "00001", "description", "Duty", "amount", amount, "createdDate", UtilDateTime.nowTimestamp()));
		this.cart.addAdjustment(salesTax);
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}

	public HttpServletResponse getResponse() {
		return this.response;
	}

	public ShoppingCart getCart() {
		return this.cart;
	}

	private GenericValue getPromoPromoCode(Delegator delegator, String productPromoCodeId) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(productPromoCodeId)) {
			return delegator.findOne("ProductPromoCode", UtilMisc.toMap("productPromoCodeId", productPromoCodeId), true);
		}

		return null;
	}
}