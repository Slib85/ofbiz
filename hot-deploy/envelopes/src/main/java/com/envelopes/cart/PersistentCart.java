/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.cart;

import java.lang.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericDelegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.order.shoppingcart.CartItemModifyException;
import org.apache.ofbiz.order.shoppingcart.ShoppingCart;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartEvents;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartItem;
import org.apache.ofbiz.service.LocalDispatcher;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import com.envelopes.product.*;
import com.envelopes.util.*;


public class PersistentCart {
	public static final String module = PersistentCart.class.getName();
	public static final String COOKIE_NAME = "__SC_Data";

	private LocalDispatcher dispatcher = null;
	private Delegator delegator = null;
	protected GenericValue pCartGV = null;
	protected String id = null;
	protected String ipAddress = null;
	protected ShoppingCart cart = null;
	protected List<Map> data = null;
	protected String cartTransId = null;
	protected Timestamp lastUpdatedStamp = null;
	protected int cartLimit = 50;

	/**
	 * PersistentCart Constructor for ShoppingCart
	 * @param request
	 * @param response
	 * @param id
	 * @throws GenericEntityException
	 */
	public PersistentCart(HttpServletRequest request, HttpServletResponse response, String id) throws GenericEntityException {
		this.dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		this.delegator = (Delegator) request.getAttribute("delegator");
		this.cart = ShoppingCartEvents.getCartObject(request);
		this.data = CartHelper.getCartItems(this.cart);
		this.ipAddress = UtilValidate.isNotEmpty(request.getHeader("X-Forwarded-For")) ? request.getHeader("X-Forwarded-For") : request.getRemoteAddr();

		if(UtilValidate.isNotEmpty(id)) {
			this.id = id;
			this.pCartGV = delegator.findOne("PersistentCart", UtilMisc.toMap("id", this.id), false);
			if(this.pCartGV == null) {
				throw new IllegalStateException("The persistent cart does not exist.");
			}

			this.data = new Gson().fromJson(this.pCartGV.getString("data"), ArrayList.class);
		} else {
			//this.id = UUID.randomUUID().toString().toUpperCase();
			this.id = this.delegator.getNextSeqId("PersistentCart");
		}
	}

	/**
	 * PersistentCart Constructor for Orders
	 * @param request
	 * @param response
	 * @param tempOrder
	 * @param id
	 * @throws GenericEntityException
	 */
	public PersistentCart(HttpServletRequest request, HttpServletResponse response, boolean tempOrder, String id) throws GenericEntityException {
		this.dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		this.delegator = (Delegator) request.getAttribute("delegator");
		this.cart = ShoppingCartEvents.getCartObject(request);
		this.data = CartHelper.getCartItems(this.cart);

		if(UtilValidate.isNotEmpty(id)) {
			this.id = id;
			this.pCartGV = EntityUtil.getFirst(delegator.findByAnd("TemporaryOrder", UtilMisc.toMap("id", this.id), null, false));
			if (this.pCartGV == null) {
				throw new IllegalStateException("The persistent cart does not exist.");
			}

			this.data = new Gson().fromJson(this.pCartGV.getString("data"), ArrayList.class);
		} else {
			this.id = this.delegator.getNextSeqId("TemporaryOrder");
		}
	}

	/**
	 * PersistentCart Constructor from given data
	 * @param request
	 * @param response
	 * @param id
	 * @param items
	 * @throws GenericEntityException
	 */
	public PersistentCart(HttpServletRequest request, HttpServletResponse response, String id, List<Map> items) throws GenericEntityException {
		this.dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		this.delegator = (Delegator) request.getAttribute("delegator");
		this.cart = ShoppingCartEvents.getCartObject(request);
		this.data = items;
	}

	public void setCartTransId(String id) {
		this.cartTransId = id;
	}

	public void storeData(HttpServletRequest request) throws GenericEntityException {
		createGenericValue(request);
		delegator.create(this.pCartGV);
		this.pCartGV.refresh();
	}

	public void storeTempData(HttpServletRequest request) throws GenericEntityException {
		createTempGenericValue(request);
		delegator.create(this.pCartGV);
		this.pCartGV.refresh();
	}

	public void updateData(HttpServletRequest request, boolean forceUpdate) throws GenericEntityException {
		if(forceUpdate || (isCartModified(request) && !isCartLocked(request))) {
			createGenericValue(request);
			delegator.store(this.pCartGV);
			this.pCartGV.refresh();
		}
	}

	/*
	 In order for a PersistentCart to be locked, the status must be locked, an agentId must be filled and the lockedTimeOut cannot have expired
	 */
	public boolean isCartLocked(HttpServletRequest request) {
		if(!"Y".equals(this.pCartGV.getString("locked")) || UtilValidate.isEmpty(this.pCartGV.getString("agentId"))) {
			return false;
		} else if(EnvUtil.addMinutesToTime(this.pCartGV.getTimestamp("lastUpdatedStamp"), 15).compareTo(UtilDateTime.nowTimestamp()) < 0) {
			return false;
		} else if("Y".equals(this.pCartGV.getString("locked")) && !request.getSession().getId().equals(this.pCartGV.getString("agentId"))) {
			return true;
		}

		return false;
	}

	public boolean isCartModified(HttpServletRequest request) {
		boolean modified = true;
		try {
			GenericValue pCartDB = delegator.findOne("PersistentCart", UtilMisc.toMap("id", this.id), false);
			List<Map> cartItems = CartHelper.getCartItems(ShoppingCartEvents.getCartObject(request));
			modified = !(UtilValidate.isEmpty(pCartDB) && UtilValidate.isEmpty(cartItems)) && !new Gson().fromJson(this.pCartGV.getString("data"), ArrayList.class).equals(new Gson().fromJson(new GsonBuilder().serializeNulls().create().toJson(CartHelper.getCartItems(cart)), ArrayList.class));
		} catch (GenericEntityException e) {
			Debug.log(e, module);
		}
		return modified;
	}

	public String getId() {
		return this.id;
	}

	public GenericValue getStoredData() {
		return this.pCartGV;
	}

	public void rebuildCart(HttpServletRequest request, HttpServletResponse response, boolean merge) throws GenericEntityException, CartItemModifyException {
		rebuildCart(request, response, merge, false);
		updateData(request, true);
	}

	public void rebuildCart(HttpServletRequest request, HttpServletResponse response, boolean merge, boolean ignoreLock) throws GenericEntityException, CartItemModifyException {
		if(UtilValidate.isNotEmpty(this.data) && (ignoreLock || (!ignoreLock && !isCartLocked(request)))) {
			if(!merge) {
				emptyCartItems(); //clear the cart first
			}

			int count = 0;
			for(int i = this.data.size() -1; i >= 0; i--) {
				if(count++ > EnvConstantsUtil.CART_LIMIT) {
					break;
				}
				buildCartItem(request, response, (Map) this.data.get(i));
			}
		}
	}

	public void setAgentData(HttpServletRequest request) throws GenericEntityException {
		this.pCartGV.set("agentId", (String) request.getSession().getId());
		this.pCartGV.set("locked", "Y");
		this.pCartGV.set("agentUserLoginId", (String) request.getParameter("agentUserLoginId"));
		this.delegator.store(this.pCartGV);
	}

	public void removeAgentData() throws GenericEntityException {
		this.pCartGV.set("agentId", null);
		this.pCartGV.set("locked", null);
		this.delegator.store(this.pCartGV);
	}

	public void setUserLoginData(HttpServletRequest request) throws GenericEntityException {
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		if(UtilValidate.isNotEmpty(userLogin)) {
			userLogin.set("cartId", this.id);
			this.delegator.store(userLogin);
		}
	}

	private void emptyCartItems() throws CartItemModifyException {
		for(ShoppingCartItem cartItem : this.cart.items()) {
			this.cart.removeCartItem(cartItem, this.dispatcher);
		}
	}

	private String convertToJSON() {
		this.data = CartHelper.getCartItems(this.cart); //rebuild from latest

		//limit # of items to store in database
		if(this.data.size() > this.cartLimit) {
			this.data = this.data.stream().limit(this.cartLimit).collect(Collectors.toList());
		}
		Gson gson = new GsonBuilder().serializeNulls().create();
		return gson.toJson(this.data);
	}

	private void createTempGenericValue(HttpServletRequest request) {
		this.pCartGV = delegator.makeValue("TemporaryOrder", UtilMisc.toMap(
				"id", this.id,
				"cartTransId", this.cartTransId,
				"data", convertToJSON()
		));
	}

	private void createGenericValue(HttpServletRequest request) {
		//this.cart = ShoppingCartEvents.getCartObject(request);
		this.pCartGV = delegator.makeValue("PersistentCart", UtilMisc.toMap(
				"id", this.id,
				//"partyId", cart.getPartyId(),
				//"userLoginId", UtilValidate.isNotEmpty(this.cart.getUserLogin()) ? this.cart.getUserLogin().getString("userLoginId") : null,
				"ipAddress", this.ipAddress,
				"data", convertToJSON()
		));
	}

	private void buildCartItem(HttpServletRequest request, HttpServletResponse response, Map item) throws GenericEntityException {
		Map<String, Object> envPriceCalcAttributes = new HashMap<>();
		Map<String, Object> envArtworkAttributes = new HashMap<>();
		Map<String, Object> envQuoteAttributes = new HashMap<>();
		Map<String, Object> envQuantityAttributes = new LinkedHashMap<>();

		buildPriceContext(envPriceCalcAttributes, (Map<String, Object>) item.get("envPriceCalcAttributes"));
		//iparcel fix/temp order fix
		envPriceCalcAttributes.put("id", item.get("productId"));

		buildArtworkContext(envArtworkAttributes, (Map<String, Object>) item.get("envArtworkAttributes"));
		buildQuoteContext(envQuoteAttributes, (Map<String, Object>) item.get("envQuoteAttributes"));
		buildQuantityContext(request, envQuantityAttributes, envPriceCalcAttributes);

		request.setAttribute("envPriceCalcAttributes", envPriceCalcAttributes);
		request.setAttribute("envArtworkAttributes", envArtworkAttributes);
		request.setAttribute("envQuoteAttributes", envQuoteAttributes);
		request.setAttribute("envQuantityAttributes", envQuantityAttributes);
		if(item.get("quantity") instanceof String) {
			request.setAttribute("quantity", item.get("quantity"));
		} else if(item.get("quantity") instanceof Double) {
			request.setAttribute("quantity", item.get("quantity").toString());
		} else {
			request.setAttribute("quantity", ((BigDecimal) item.get("quantity")).toPlainString());
		}
		if(UtilValidate.isNotEmpty(item.get("price"))) {
			if(item.get("price") instanceof String) {
				request.setAttribute("price", item.get("price"));
			} else if(item.get("price") instanceof Double) {
				request.setAttribute("price", item.get("price").toString());
			} else {
				request.setAttribute("price", ((BigDecimal) item.get("price")).toPlainString());
			}
		}
		if(UtilValidate.isNotEmpty(item.get("name"))) {
			request.setAttribute("name", (String) item.get("name"));
		}
		request.setAttribute("add_product_id", (String) item.get("productId"));
		request.setAttribute("itemComment",  (String) item.get("itemComment"));

		CartHelper.addToCart(request, response);

		//clear all shopping cart attributes for this instance of re-adding an item
		//to avoid issues on the next add to cart
		request.removeAttribute("envPriceCalcAttributes");
		request.removeAttribute("envArtworkAttributes");
		request.removeAttribute("envQuoteAttributes");
		request.removeAttribute("envQuantityAttributes");
		request.removeAttribute("quantity");
		request.removeAttribute("price");
		request.removeAttribute("name");
		request.removeAttribute("add_product_id");
		request.removeAttribute("itemComment");
	}

	private void buildPriceContext(Map<String, Object> envPriceCalcAttributes, Map<String, Object> tempPriceCalcAttributes) throws GenericEntityException {
		for(String key : tempPriceCalcAttributes.keySet()) {
			if(key.equals("partyId")) {
				envPriceCalcAttributes.put(key, (String) tempPriceCalcAttributes.get(key));
			} else if(key.equals("colorsFront") || key.equals("colorsBack") || key.equals("cuts") || key.equals("addresses")) {
				if(tempPriceCalcAttributes.get(key) instanceof Double) {
					envPriceCalcAttributes.put(key, Integer.valueOf(((Double) tempPriceCalcAttributes.get(key)).intValue()));
				} else {
					envPriceCalcAttributes.put(key, Integer.valueOf(tempPriceCalcAttributes.get(key).toString()));
				}
			} else if(key.equals("isRush") || key.equals("whiteInkFront") || key.equals("whiteInkBack") || key.equals("isFolded") || key.equals("isFullBleed")) {
				envPriceCalcAttributes.put(key, Boolean.valueOf(tempPriceCalcAttributes.get(key).toString()));
			} else if(key.equals("quantity") || key.equals("price") || key.equals("weight")) {
				envPriceCalcAttributes.put(key, new BigDecimal(tempPriceCalcAttributes.get(key).toString()));
			} else if(key.equals("product")) {
				envPriceCalcAttributes.put(key, delegator.findOne("Product", UtilMisc.toMap("productId", ((LinkedTreeMap) tempPriceCalcAttributes.get(key)).get("productId")), true));
			} else {
				envPriceCalcAttributes.put(key, tempPriceCalcAttributes.get(key));
			}
		}
	}

	private void buildArtworkContext(Map<String, Object> envArtworkAttributes, Map<String, Object> tempArtworkAttributes) {
		envArtworkAttributes.putAll(tempArtworkAttributes);
	}

	private void buildQuoteContext(Map<String, Object> envQuoteAttributes, Map<String, Object> tempQuoteAttributes) {
		envQuoteAttributes.putAll(tempQuoteAttributes);
	}

	private void buildQuantityContext(HttpServletRequest request, Map<String, Object> envQuantityAttributes, Map<String, Object> envPriceCalcAttributes) {
		Map<String, Object> quantityPriceList = ProductEvents.getProductPrice(request, envPriceCalcAttributes);
		Map<Integer, Map<String, Object>> priceList = (Map) quantityPriceList.get("priceList");
		for(Integer quantity : priceList.keySet()) {
			envQuantityAttributes.put(quantity.toString(), priceList.get(quantity).get("price"));
		}
	}

	protected void setLastUpdatedStamp(Timestamp lastUpdatedStamp) {
		this.lastUpdatedStamp = lastUpdatedStamp;
	}

	protected Timestamp getLastUpdatedStamp() {
		return this.lastUpdatedStamp;
	}

	/* STATIC FUNCTIONS */

	/**
	 * Load a PersistentCart from TemporaryOrder
	 * This is when a cart was saved in order to create a order via any API
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws GenericEntityException
	 * @throws CartItemModifyException
	 */
	public static PersistentCart load(HttpServletRequest request, HttpServletResponse response, String id) throws GenericEntityException, CartItemModifyException {
		//load from ID, assign to session
		PersistentCart pCart = new PersistentCart(request, response, true, id);
		pCart.rebuildCart(request, response, false, true);
		return pCart;
	}

	/**
	 * Load a PersistentCart from data passed in
	 * @param request
	 * @param response
	 * @param id
	 * @param items
	 * @return
	 * @throws GenericEntityException
	 * @throws CartItemModifyException
	 */
	public static PersistentCart load(HttpServletRequest request, HttpServletResponse response, String id, List<Map> items) throws GenericEntityException, CartItemModifyException {
		//load from data passed in, assign to session
		PersistentCart pCart = new PersistentCart(request, response, id, items);
		pCart.rebuildCart(request, response, false, true);
		return pCart;
	}

	/**
	 * Quick version of createOrLoad with setting of cookie value
	 * @param request
	 * @param response
	 * @param id
	 * @param agentLoaded
	 * @param merge
	 */
	public static void quickCheck(HttpServletRequest request, HttpServletResponse response, String id, boolean agentLoaded, boolean merge) {
		try {

			GenericDelegator delegator = (GenericDelegator) request.getAttribute("delegator");
			String validatedId = null;
			if (UtilValidate.isNotEmpty(id) &&
				delegator.findCountByCondition("PersistentCart", EntityCondition.makeCondition("id", EntityOperator.EQUALS, id), null, null) > 0) {
				validatedId = id;
			}
			PersistentCart.createOrLoad(request, response, validatedId, agentLoaded, merge);
			PersistentCart.setCookie(request, response, false);
		} catch (Exception pce) {
			Debug.logError(pce, module);
		}
	}

	/**
	 * Update the PersistentCart Data
	 * @param request
	 * @param response
	 * @param id
	 * @param agentLoaded
	 * @param merge
	 */
	public static void updateData(HttpServletRequest request, HttpServletResponse response, String id, boolean agentLoaded, boolean merge) {
		try {
			PersistentCart pCart = (PersistentCart) request.getSession().getAttribute("PersistentCart");
			if(pCart != null) {
				pCart.updateData(request, true);
				PersistentCart.setCookie(request, response, false);
			}
		} catch (Exception pce) {
			Debug.logError(pce, module);
		}
	}

	/**
	 * Create or Load a PersistentCart session
	 * @param request
	 * @param response
	 * @param id
	 * @param agentLoaded
	 * @param merge
	 * @return
	 * @throws GenericEntityException
	 * @throws CartItemModifyException
	 */
	public static PersistentCart createOrLoad(HttpServletRequest request, HttpServletResponse response, String id, boolean agentLoaded, boolean merge) throws GenericEntityException, CartItemModifyException {
		if(UtilValidate.isNotEmpty(id)) {
			//load from ID, assign to session
			PersistentCart pCart = new PersistentCart(request, response, id);
			pCart.rebuildCart(request, response, merge);
			if(agentLoaded) {
				pCart.setAgentData(request);
			}
			request.getSession().setAttribute("PersistentCart", pCart);
			return pCart;
		} else if(request.getSession().getAttribute("PersistentCart") != null) {
			//check session
			PersistentCart pCart = (PersistentCart) request.getSession().getAttribute("PersistentCart");

			//check if the pCart data was updated if it has, then reload
			pCart.pCartGV.refresh(); //refresh the GenericValue
			pCart.data = new Gson().fromJson(pCart.pCartGV.getString("data"), ArrayList.class);

			if(request.getCookies() != null) {
				Map<String, Object> cookieData = getCookie(request, null);
				if(UtilValidate.isNotEmpty(cookieData) && pCart.pCartGV.getTimestamp("lastUpdatedStamp").compareTo(Timestamp.valueOf((String) cookieData.get("date"))) > 0) {
					pCart.rebuildCart(request, response, false);
				}
			}

			pCart.updateData(request, false);
			return pCart;
		} else if(request.getCookies() != null) {
			//check cookie and load from it
			Map<String, Object> cookieData = getCookie(request, null);
			if(UtilValidate.isNotEmpty(cookieData)) {
				PersistentCart pCart = new PersistentCart(request, response, (String) cookieData.get("id"));
				if(pCart.pCartGV == null) {
					return null;
				} else {
					pCart.rebuildCart(request, response, true);
					request.getSession().setAttribute("PersistentCart", pCart);
					return pCart;
				}
			} else {
				return create(request, response);
			}
		} else if(!CartEvents.isCartEmpty(request)) {
			return create(request, response);
		}

		return null;
	}

	/**
	 * Create a new PersistentCart object
	 * @param request
	 * @param response
	 * @return
	 * @throws GenericEntityException
	 */
	public static PersistentCart create(HttpServletRequest request, HttpServletResponse response) throws GenericEntityException {
		//create new instance only if the cart is not empty
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		if(cart.items().size() > 0) {
			PersistentCart pCart = new PersistentCart(request, response, null);
			pCart.storeData(request);
			request.getSession().setAttribute("PersistentCart", pCart);
			return pCart;
		}
		return null;
	}

	/**
	 * Destroy an PersistentCart object
	 * @param request
	 * @param response
	 * @param id
	 * @param delete
	 * @return
	 * @throws GenericEntityException
	 * @throws CartItemModifyException
	 */
	public static PersistentCart destroy(HttpServletRequest request, HttpServletResponse response, String id, boolean delete) throws GenericEntityException, CartItemModifyException {
		PersistentCart pCart = null;
		if(UtilValidate.isNotEmpty(id)) {
			pCart = createOrLoad(request, response, id, false, false);
		} else {
			pCart = (PersistentCart) request.getSession().getAttribute("PersistentCart");
		}
		request.getSession().removeAttribute("PersistentCart");
		if(delete && pCart != null) {
			GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
			if(userLogin != null) {
				userLogin.set("cartId", null);
				((Delegator) request.getAttribute("delegator")).store(userLogin);
			}
			((Delegator) request.getAttribute("delegator")).removeValue(pCart.pCartGV);
		}

		return null;
	}

	/**
	 * Get the cart id and date from cookie. Additionally if there is a date in the session and its future then cookie, override cookie date with session date instead.
	 * @param request
	 * @param response
	 * @return
	 */
	public static Map<String, Object> getCookie(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();

		Map<String, Object> cookieData = new HashMap<>();
		for(Cookie cookie : request.getCookies()) {
			if(cookie.getName().equals(COOKIE_NAME)) {
				cookieData = new Gson().fromJson(cookie.getValue(), HashMap.class);
				if(session != null) {
					PersistentCart pCart = (PersistentCart) request.getSession().getAttribute("PersistentCart");
					if(pCart != null && pCart.getLastUpdatedStamp() != null && pCart.getLastUpdatedStamp().compareTo(Timestamp.valueOf((String) cookieData.get("date"))) > 0) {
						cookieData.put("date", pCart.getLastUpdatedStamp().toString());
					}
				}
				break;
			}
		}

		return cookieData;
	}

	/**
	 * Set the cookie for end user. Additionally the date is also set on the session. This is required to check for updates between multiple calls to PCART before the page is loaded
	 * @param request
	 * @param response
	 * @param remove
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, boolean remove) {
		PersistentCart pCart = (PersistentCart) request.getSession().getAttribute("PersistentCart");
		HttpSession session = request.getSession();

		Map<String, Object> cookieData = new HashMap<>();
		if(pCart != null) {
			cookieData.put("id", pCart.id);
			cookieData.put("date", pCart.pCartGV.getTimestamp("lastUpdatedStamp").toString());
			pCart.setLastUpdatedStamp(pCart.pCartGV.getTimestamp("lastUpdatedStamp"));
		} else {
			cookieData.put("id", "_NA_");
			cookieData.put("date", "_NA_");
			remove = true;
		}

		Cookie pCartCookie = new Cookie(COOKIE_NAME, new Gson().toJson(cookieData));
		pCartCookie.setPath("/");
		pCartCookie.setHttpOnly(false);
		pCartCookie.setMaxAge( remove ? 0 : 60 * 60 * 24 * 30 );
		response.addCookie(pCartCookie);
	}

	/**
	 * Retrn the card id from cookie
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getPersistentCartIdFromCookie(HttpServletRequest request, HttpServletResponse response) {
		if(request.getCookies() != null) {
			Map<String, Object> cookieData = new HashMap<>();
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals(COOKIE_NAME)) {
					cookieData = new Gson().fromJson(cookie.getValue(), HashMap.class);
					return (String) cookieData.get("id");
				}
			}
		}

		return null;
	}

	/**
	 * Return the cart id from the session
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getPersistentCartIdFromSession(HttpServletRequest request, HttpServletResponse response) {
		PersistentCart pCart = (PersistentCart) request.getSession().getAttribute("PersistentCart");
		if(pCart != null) {
			return pCart.id;
		}

		return null;
	}

	/*
	 * Return java object of data
	 */
	public static List getDataAsObject(Delegator delegator, String cartId) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(cartId)) {
			GenericValue pCartGV = delegator.findOne("PersistentCart", UtilMisc.toMap("id", cartId), false);
			if(UtilValidate.isNotEmpty(pCartGV.getString("data"))) {
				return new Gson().fromJson(pCartGV.getString("data"), ArrayList.class);
			}
		}

		return null;
	}
}