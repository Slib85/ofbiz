package com.envelopes.bronto;

import com.envelopes.cart.CartEvents;
import com.envelopes.cart.PersistentCart;
import com.envelopes.email.EmailHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.webapp.website.WebSiteWorker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Manu on 3/26/2015.
 */
public class BrontoUtil {

	/**
	 * Helper method to send cart data to Bronto for abandoned cart marketing emails. This method will try to get the email address
	 * from the user login first. If not a logged in user, it will look in the request parameter. If an email address is found, then
	 * this method will extract the cart data and send it to Bronto. Otherwise, this method will ignore the request.
	 *
	 * This method can be called from the service side or can be invoked from the client side using AJAX.
	 *
	 * NOTE: this method will only reset the cart data on order creation and when an item is removed from the cart only, not on loading empty shopping cart page.
	 * <pre>
	 * Use cases when cart data is sent to Bronto:
	 *
	 * 1 - When the user logs in using the login layer or from the checkout page. (Server side call from <code>com.envelopes.login.EnvLoginEvents.loginUser(HttpServletRequest request, HttpServletResponse response)</code> )
	 * 2 - When shopping cart page is loaded (Server side call from cart.groovy)
	 * 3 - When checkout page is loaded (Server side call from checkout.groovy)
	 * 4 - When shopping cart is modified (Server side call from <code>com.envelopes.cart.CartEvents.updateCartItem(HttpServletRequest request, HttpServletResponse response)</code>)
	 * 5 - When item is removed from shopping cart (Server side call from <code>com.envelopes.cart.CartEvents.removeFromCart(HttpServletRequest request, HttpServletResponse response)</code>)
	 * 6 - When a guest user enters the email address on check out page (Client side call from checkout.js on blur of email address field)
	 * 7 - Sending empty cart info after order is placed (Server side call from <code>com.envelopes.order.CheckoutEvents.createOrder(HttpServletRequest request, HttpServletResponse response)</code>)
	 *;
	 * </pre>
	 * @param request - The HttpServletRequest object
	 */
	public static boolean sendCartData(HttpServletRequest request, boolean ... processEmptyCart) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		String webSiteId = WebSiteWorker.getWebSiteId(request);

		String status = UtilProperties.getPropertyValue("envelopes", "envelopes.bronto.status");
		if(UtilValidate.isEmpty(status) || !status.equalsIgnoreCase("active")) {
			return true;
		}

		try {
			GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
			Map<String, Object> context = EnvUtil.getParameterMap(request);
			String emailSource = context.containsKey("emailSource") ? (String) context.get("emailSource") : null;
			String emailAddress = "";
			if((userLogin != null && UtilValidate.isNotEmpty(emailAddress = userLogin.getString("userLoginId"))) || UtilValidate.isNotEmpty(emailAddress = (String)context.get("emailAddress"))) {
				Map<String, Object> shoppingCart = CartEvents.getCart(request);
				List<Map> lineItems = shoppingCart.containsKey("lineItems") ? (List<Map>)shoppingCart.get("lineItems") : new ArrayList<Map>();
				if(!lineItems.isEmpty() || (processEmptyCart != null && processEmptyCart.length == 1 && processEmptyCart[0])) {
					Map<String, String> cartData = new BrontoTranslator().getCartData(shoppingCart, WebSiteWorker.getWebSiteId(request));
					String cartId = PersistentCart.getPersistentCartIdFromCookie(request, null);
					if (EmailHelper.isNewContact(emailAddress, WebSiteWorker.getWebSiteId(request)) && UtilValidate.isNotEmpty(emailSource) && emailSource.equalsIgnoreCase("checkout")) {
						cartData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("EMAILSOURCE"), emailSource);
					}
					if(lineItems.isEmpty()) {
						cartData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("ITEMINCART"), "");
					} else {
						cartData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("ITEMINCART"), "Yes");
					}
					if(UtilValidate.isNotEmpty(request.getAttribute("isCheckOut"))) {
						cartData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("INCHECKOUT"), "Yes");
					} else {
						cartData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("INCHECKOUT"), "");
					}

					if(processEmptyCart != null && processEmptyCart.length == 1 && processEmptyCart[0]) {
						cartData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("CARTID"), "");
					} else if(UtilValidate.isNotEmpty(cartId)) {
						cartData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("CARTID"), cartId);
					}

					try {
						dispatcher.runAsync("addOrUpdateContact", UtilMisc.toMap("email", emailAddress, "rawData", cartData, "translated", Boolean.TRUE, "webSiteId", WebSiteWorker.getWebSiteId(request)));
					} catch (Exception sE) {
						sE.printStackTrace();
					}
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String sendCartData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("success", sendCartData(request));
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static boolean sendCheckoutData(HttpServletRequest request, Map<String, Object> orderInfo) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");

		String status = UtilProperties.getPropertyValue("envelopes", "envelopes.bronto.status");
		if(UtilValidate.isEmpty(status) || !status.equalsIgnoreCase("active")) {
			return true;
		}

		try {
			GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
			Map<String, Object> context = EnvUtil.getParameterMap(request);
			String emailSource = context.containsKey("emailSource") ? (String) context.get("emailSource") : null;
			String emailAddress = "";
			if((userLogin != null && UtilValidate.isNotEmpty(emailAddress = userLogin.getString("userLoginId"))) || UtilValidate.isNotEmpty(emailAddress = (String)context.get("emailAddress"))) {
				Map <String, String> checkoutData = new BrontoTranslator().getCheckoutData(orderInfo, WebSiteWorker.getWebSiteId(request));

				try {
					dispatcher.runAsync("addOrUpdateContact", UtilMisc.toMap("email", emailAddress, "rawData", checkoutData, "translated", Boolean.TRUE, "webSiteId", WebSiteWorker.getWebSiteId(request)));
				} catch (Exception sE) {
					sE.printStackTrace();
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}