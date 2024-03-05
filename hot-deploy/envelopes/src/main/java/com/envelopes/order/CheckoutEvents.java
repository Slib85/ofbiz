/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.order;

import java.lang.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bigname.integration.directmailing.DirectMailingHelper;
import com.bigname.integration.listrak.ListrakHelper;
import com.bigname.payments.braintree.BraintreeHelper;
import com.bigname.payments.paypal.PayPalCheckoutHelper;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.envelopes.addressbook.AddressBookEvents;
import com.envelopes.bronto.BrontoUtil;
import com.envelopes.cart.PersistentCart;
import com.envelopes.email.EmailHelper;
import com.envelopes.service.ServiceHelper;
import com.google.api.client.googleapis.notifications.json.gson.GsonNotificationCallback;
import com.google.gson.Gson;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;

import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.transaction.GenericTransactionException;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.order.shoppingcart.*;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.webapp.website.WebSiteWorker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.envelopes.shipping.*;
import com.envelopes.cart.CartHelper;
import com.envelopes.login.EnvLoginEvents;
import com.envelopes.party.PartyHelper;
import com.envelopes.product.ProductHelper;
import com.envelopes.promo.PromoHelper;
import com.envelopes.payments.amazon.*;
import com.envelopes.util.*;

import com.amazonservices.mws.offamazonpayments.model.*;

public class CheckoutEvents {
	public static final String module = CheckoutEvents.class.getName();

	/*
	 * Create an order from form submission
	 */
	public static String createOrder(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		request.setAttribute("saveResponse", true);
		HttpSession session = request.getSession();
		boolean success = false;

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		context = getContextFromRequestAttribute(context, request);

		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		String errorMessage = null;
		String orderId = null;
		Map<String, String> errors = new HashMap<>();
		Map<String,Object> orderData = null;
		String tempPassword = null;
		Map orderInfo = null;
		String paymentMethodId = null;

		boolean isTrade = false;
		boolean isNonProfit = false;
		boolean hasQuote = false;
		boolean doAuth = false;
		boolean doCapture = false;
		boolean isOrderPrinted = false;
		boolean sendEmail = (UtilValidate.isNotEmpty(context.get("sendEmail"))) ? Boolean.parseBoolean((String) context.get("sendEmail")) : true;
		boolean setPending = false;
		GenericValue userLogin = null;
		String webSiteId = (UtilValidate.isNotEmpty(context.get("_WEB_SITE_ID_"))) ? (String) context.get("_WEB_SITE_ID_") : WebSiteWorker.getWebSiteId(request);
		String paymentMethodTypeId = (String) context.get("paymentMethodTypeId");

		//schedule order for export
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 2);
		long jobStartTime = cal.getTimeInMillis();

		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			boolean removeUserFromSession = false;
			try {
				boolean isEmailActive = false;

				//if this is from an internal PPS order creation, we need to read passed in email as userlogin
				if(UtilValidate.isNotEmpty(context.get("emailAddress")) && UtilValidate.isNotEmpty(context.get("ppsOrder")) && "Y".equalsIgnoreCase((String) context.get("ppsOrder"))) {
					//currently nothing needs to be done here
				} else {
					userLogin = (GenericValue) session.getAttribute("userLogin");
				}

				if(userLogin != null) {
					isEmailActive = true;
				}

				if(UtilValidate.isEmpty(context.get("emailAddress"))) {
					//Adding default email address to capture all orders with a missing email address. This is happening when abide fails to validate the missing email on the client side.
					context.put("emailAddress", "orders@envelopes.com");
					/*Exception e;
					EnvUtil.reportError(e = new Exception("Email Address is required for placing an order."));
					throw e;*/
				}

				if(!isEmailActive) {
					isEmailActive = PartyHelper.isEmailActive(delegator, (String) context.get("emailAddress"));
					if(isEmailActive) {
						userLogin = PartyHelper.getUserLogin(delegator, (String) context.get("emailAddress"));
						removeUserFromSession = true;
						//LoginWorker.doBasicLogin(PartyHelper.getUserLogin(delegator, (String) context.get("emailAddress")), request);
						//LoginWorker.autoLoginSet(request, response);
					}
				}

				//no user found, make one
				if(!isEmailActive) {
					tempPassword = RandomStringUtils.randomAlphanumeric(EnvConstantsUtil.PASSWORD_LENGTH);
					Map<String, String> data = new HashMap<String, String>();
					data.put("firstName", (String) context.get("shipping_firstName"));
					data.put("lastName", (String) context.get("shipping_lastName"));
					data.put("emailAddress", (String) context.get("emailAddress"));
					data.put("password", tempPassword);
					userLogin = PartyHelper.createAccount(delegator, dispatcher, data);
					if(userLogin == null) {
						Exception e;
						EnvUtil.reportError(e = new Exception("Error occurred while creating account."));
						throw e;
					}

					GenericValue emailContactMech = PartyHelper.getMatchedEmailAddress(delegator, (String) context.get("emailAddress"), userLogin.getString("partyId"), "EMAIL_ADDRESS");
					cart.setOrderEmailContactMechId(emailContactMech.getString("contactMechId")); //set the email address to the cart
					//LoginWorker.doBasicLogin(PartyHelper.getUserLogin(delegator, (partyString) context.get("emailAddress")), request);
					//LoginWorker.autoLoginSet(request, response);
				} else if(UtilValidate.isNotEmpty((String) context.get("oldEmailAddress")) && !((String) context.get("emailAddress")).equalsIgnoreCase((String) context.get("oldEmailAddress"))) {
					//check if the email exists
					GenericValue emailContactMech = PartyHelper.getMatchedEmailAddress(delegator, (String) context.get("emailAddress"), userLogin.getString("partyId"), "EMAIL_ADDRESS");
					if(emailContactMech == null) {
						emailContactMech = PartyHelper.createPartyContactMech(delegator, userLogin.getString("partyId"), "EMAIL_ADDRESS", (String) context.get("emailAddress"));
					}
					PartyHelper.createPartyContactMechPurpose(delegator, userLogin.getString("partyId"), emailContactMech.getString("contactMechId"), "PRIMARY_EMAIL", true);

					cart.setOrderEmailContactMechId(emailContactMech.getString("contactMechId")); //set the email address to the cart
				} else {
					GenericValue emailContactMech = PartyHelper.getMatchedEmailAddress(delegator, (String) context.get("emailAddress"), userLogin.getString("partyId"), "EMAIL_ADDRESS");
					if(emailContactMech == null) {
						emailContactMech = PartyHelper.createPartyContactMech(delegator, userLogin.getString("partyId"), "EMAIL_ADDRESS", (String) context.get("emailAddress"));
					}
					cart.setOrderEmailContactMechId(emailContactMech.getString("contactMechId")); //set the email address to the cart
				}

				Map<String, String> shippingAddress = PartyHelper.getPostalAddressMap(request, context, true);
				Map<String, String> billingAddress = PartyHelper.getPostalAddressMap(request, context, false);
				Map<String, String> shippingPhone = PartyHelper.getTelecomMap(request, context, true);
				Map<String, String> billingPhone = PartyHelper.getTelecomMap(request, context, false);
				Map<String, String> creditCard = PartyHelper.getCreditCardMap(request, billingAddress);

				GenericValue shippingContactMech = null;
				GenericValue shippingTelecomMech = null;
				GenericValue billingContactMech = null;
				GenericValue billingTelecomMech = null;
				GenericValue billingCreditCard = null;

				if(isEmailActive) {
					//check if the addresses already exist
					shippingContactMech = PartyHelper.getMatchedPostalAddress(delegator, shippingAddress, userLogin.getString("partyId"), "SHIPPING_LOCATION");
					shippingTelecomMech = PartyHelper.getMatchedTelecomNumber(delegator, shippingPhone, userLogin.getString("partyId"), "PHONE_SHIPPING");
					billingContactMech = PartyHelper.getMatchedPostalAddress(delegator, billingAddress, userLogin.getString("partyId"), "BILLING_LOCATION");
					billingTelecomMech = PartyHelper.getMatchedTelecomNumber(delegator, billingPhone, userLogin.getString("partyId"), "PHONE_BILLING");
					//billingCreditCard = PartyHelper.getMatchedCreditCard(delegator, creditCard, (UtilValidate.isNotEmpty(billingContactMech.get("contactMechId"))) ? (String) billingContactMech.get("contactMechId") : null); //this will always fail because CC value is stored with encryption
				}

				if(UtilValidate.isEmpty(shippingContactMech)) {
					shippingContactMech = PartyHelper.createPostalAddress(delegator, userLogin.getString("partyId"), shippingAddress, "SHIPPING_LOCATION");
				}
				if(UtilValidate.isEmpty(billingContactMech)) {
					if(!EnvUtil.areMapsEqual(shippingAddress, billingAddress)) {
						billingContactMech = PartyHelper.createPostalAddress(delegator, userLogin.getString("partyId"), billingAddress, "BILLING_LOCATION");
					} else {
						billingContactMech = shippingContactMech;
						PartyHelper.createPartyContactMechPurpose(delegator, userLogin.getString("partyId"), billingContactMech.getString("contactMechId"), "BILLING_LOCATION", false);
					}
				}

				if(UtilValidate.isEmpty(shippingTelecomMech)) {
					shippingTelecomMech = PartyHelper.createTelecomNumber(delegator, userLogin.getString("partyId"), shippingPhone, "PHONE_SHIPPING");
				}
				if(UtilValidate.isEmpty(billingTelecomMech)) {
					if(!EnvUtil.areMapsEqual(shippingPhone, billingPhone)) {
						billingTelecomMech = PartyHelper.createTelecomNumber(delegator, userLogin.getString("partyId"), billingPhone, "PHONE_BILLING");
					} else {
						billingTelecomMech = shippingTelecomMech;
						PartyHelper.createPartyContactMechPurpose(delegator, userLogin.getString("partyId"), billingTelecomMech.getString("contactMechId"), "PHONE_BILLING", false);
					}
				}

				if(UtilValidate.isNotEmpty(shippingContactMech)) {
					PartyHelper.createPartyContactMechAttribute(delegator, shippingContactMech.getString("contactMechId"), "isBlindShipment", UtilValidate.isNotEmpty(context.get("no_price_info")) ? "Y" : "N");

					if(UtilValidate.isNotEmpty(context.get("ship_to"))) {
						PartyHelper.createPartyContactMechAttribute(delegator, shippingContactMech.getString("contactMechId"), "businessOrResidence", (String) context.get("ship_to"));
					}

					if(UtilValidate.isNotEmpty(shippingTelecomMech)) {
						PartyHelper.createPartyContactMechAttribute(delegator, shippingContactMech.getString("contactMechId"), "telecomNumber", shippingTelecomMech.getString("contactMechId"));
					}
				}

				if(UtilValidate.isNotEmpty(billingContactMech) && UtilValidate.isNotEmpty(billingTelecomMech)) {
					PartyHelper.createPartyContactMechAttribute(delegator, billingContactMech.getString("contactMechId"), "telecomNumber", billingTelecomMech.getString("contactMechId"));
				}

				cart.setUserLogin(userLogin, dispatcher); //always reset the cart userLogin from the session or created one
				cart.setShippingContactMechId(0, shippingContactMech.getString("contactMechId"));

				//promos may have re-calced
				CartHelper.setTaxTotal(dispatcher, shippingAddress, cart);

				if(UtilValidate.isNotEmpty(context.get("correspondingPoId"))) {
					cart.setPoNumber((String) context.get("correspondingPoId"));
				}

				if(UtilValidate.isNotEmpty(context.get("resellerId"))) {
					PartyHelper.setResellerId(delegator, userLogin.getString("partyId"), (String) context.get("resellerId"));
				}

				//set the commment for order level if any
				if(UtilValidate.isNotEmpty(context.get("orderNote"))) {
					cart.addOrderNote((String) context.get("orderNote"));
				}

				//if the cart does not have a ship cost, get the cheapest non zero shipping and add it
				if(!foundShipping(cart)) {
					addShippingToCart(delegator, dispatcher, shippingAddress, cart);
				}

				boolean isOrderFree = isOrderFree(cart);
				if(UtilValidate.isNotEmpty(context.get("externalId"))) {
					cart.setExternalId((String) context.get("externalId"));
				}

				if(UtilValidate.isNotEmpty(context.get("externalIdPP")) && paymentMethodTypeId.equalsIgnoreCase("EXT_PAYPAL_CHECKOUT")) {
					cart.setExternalId((String) context.get("externalIdPP"));
				}

				if(isOrderFree || (paymentMethodTypeId).equalsIgnoreCase("EXT_OFFLINE") || (paymentMethodTypeId).equalsIgnoreCase("EXT_IPARCEL")) {
					if(isOrderFree) {
						context.put("paymentMethodTypeId", "EXT_OFFLINE");
						paymentMethodTypeId = "EXT_OFFLINE";
					}
					cart.clearPayments();
					paymentMethodTypeId = (UtilValidate.isEmpty(paymentMethodTypeId)) ? "EXT_OFFLINE" : paymentMethodTypeId;
					GenericValue payment = PartyHelper.createOfflinePayment(delegator, (UtilValidate.isNotEmpty(billingContactMech.get("contactMechId"))) ? (String) billingContactMech.get("contactMechId") : null, userLogin.getString("partyId"), paymentMethodTypeId, null);
					cart.addPayment((UtilValidate.isEmpty(payment) && UtilValidate.isEmpty(payment.get("paymentMethodId"))) ? null : payment.getString("paymentMethodId"));
				} else if((paymentMethodTypeId).equalsIgnoreCase("EXT_AMAZON")) {
					cart.clearPayments();
					GenericValue payment = PartyHelper.createAmazonPayment(delegator, (UtilValidate.isNotEmpty(billingContactMech.get("contactMechId"))) ? (String) billingContactMech.get("contactMechId") : null, userLogin.getString("partyId"), "EXT_AMAZON", (String) context.get("externalId"));
					cart.addPayment((UtilValidate.isEmpty(payment) && UtilValidate.isEmpty(payment.get("paymentMethodId"))) ? null : payment.getString("paymentMethodId"));
					doAuth = true;
					if (UtilValidate.isNotEmpty(AmazonPaymentHelper.MWS_AUTO_CAPTURE) && AmazonPaymentHelper.MWS_AUTO_CAPTURE.equalsIgnoreCase("Y")) {
						doCapture = true;
					}
				} else if((paymentMethodTypeId).equalsIgnoreCase("PERSONAL_CHECK")) {
					cart.clearPayments();
					GenericValue payment = PartyHelper.createOfflinePayment(delegator, (UtilValidate.isNotEmpty(billingContactMech.get("contactMechId"))) ? (String) billingContactMech.get("contactMechId") : null, userLogin.getString("partyId"), paymentMethodTypeId, (String) context.get("checkNumber"));
					cart.addPayment((UtilValidate.isEmpty(payment) && UtilValidate.isEmpty(payment.get("paymentMethodId"))) ? null : payment.getString("paymentMethodId"));
				} else if((paymentMethodTypeId).equalsIgnoreCase("EXT_NET30") || (paymentMethodTypeId).equalsIgnoreCase("EXT_CLOSED")) {
					cart.clearPayments();
					GenericValue payment = PartyHelper.createOfflinePayment(delegator, (UtilValidate.isNotEmpty(billingContactMech.get("contactMechId"))) ? (String) billingContactMech.get("contactMechId") : null, userLogin.getString("partyId"), paymentMethodTypeId, null);
					cart.addPayment((UtilValidate.isEmpty(payment) && UtilValidate.isEmpty(payment.get("paymentMethodId"))) ? null : payment.getString("paymentMethodId"));
				} else if (paymentMethodTypeId.equalsIgnoreCase("CREDIT_CARD") && UtilValidate.isNotEmpty(context.get("NONCE_TOKEN"))){
					creditCard.put("cardNumber", "4111111111111111");
					creditCard.put("expireDate", "12/2028");
					billingCreditCard = PartyHelper.createCreditCard(delegator, creditCard, (UtilValidate.isNotEmpty(billingContactMech.get("contactMechId"))) ? (String) billingContactMech.get("contactMechId") : null, userLogin.getString("partyId"), "CREDIT_CARD");
					cart.clearPayments();
					cart.addPayment(billingCreditCard.getString("paymentMethodId"));
					paymentMethodId = billingCreditCard.getString("paymentMethodId");

					//set the security code
					ShoppingCart.CartPaymentInfo cpi = cart.getPaymentInfo(billingCreditCard.getString("paymentMethodId"), null, null, null, true);
					cpi.securityCode = (String) request.getParameter("billToCardSecurityCode");
					doAuth = true;
/*
					paymentMethod = delegator.makeValue("PaymentMethod", UtilMisc.toMap("paymentMethodId", delegator.getNextSeqId("PaymentMethod"), "paymentMethodTypeId", paymentMethodTypeId));
					paymentMethod.put("partyId", userLogin.getString("partyId"));
					paymentMethod.put("description", paymentMethodTypeId);
					paymentMethod.put("fromDate", UtilDateTime.nowTimestamp());
					delegator.create(paymentMethod);
					doAuth = true;*/
				} else if((paymentMethodTypeId).equalsIgnoreCase("EXT_PAYPAL_CHECKOUT") && (UtilValidate.isNotEmpty(context.get("NONCE_TOKEN")) || "folders".equalsIgnoreCase(webSiteId))) {
					cart.clearPayments();
					GenericValue payment = PartyHelper.createPayPalPayment(delegator, (UtilValidate.isNotEmpty(billingContactMech.get("contactMechId"))) ? (String) billingContactMech.get("contactMechId") : null, userLogin.getString("partyId"), "EXT_PAYPAL_CHECKOUT", (String) context.get("externalIdPP"));
					cart.addPayment((UtilValidate.isEmpty(payment) && UtilValidate.isEmpty(payment.get("paymentMethodId"))) ? null : payment.getString("paymentMethodId"));
					doAuth = true;
					if(UtilValidate.isNotEmpty(PayPalCheckoutHelper.PAYPAL_AUTO_CAPTURE) && PayPalCheckoutHelper.PAYPAL_AUTO_CAPTURE.equalsIgnoreCase("Y")) {
						doCapture = true;
					}
				} else if (paymentMethodTypeId.equalsIgnoreCase("CREDIT_CARD") && UtilValidate.isEmpty(billingCreditCard)) {
					billingCreditCard = PartyHelper.createCreditCard(delegator, creditCard, (UtilValidate.isNotEmpty(billingContactMech.get("contactMechId"))) ? (String) billingContactMech.get("contactMechId") : null, userLogin.getString("partyId"), "CREDIT_CARD");
					cart.clearPayments();
					cart.addPayment(billingCreditCard.getString("paymentMethodId"));

					//set the security code
					ShoppingCart.CartPaymentInfo cpi = cart.getPaymentInfo(billingCreditCard.getString("paymentMethodId"), null, null, null, true);
					cpi.securityCode = (String) request.getParameter("billToCardSecurityCode");
					doAuth = true;
				}

				if("INTRNL_STD".equalsIgnoreCase(cart.getShipmentMethodTypeId())) {
					setPending = true;
				}

				//MANUAL PASS OF WEBSITE ID, USED WHEN CREATING ORDERS THROUGH ADMIN
				if(UtilValidate.isNotEmpty(context.get("_WEB_SITE_ID_"))) {
					request.setAttribute("_WEB_SITE_ID_", (String) context.get("_WEB_SITE_ID_"));
					cart.setWebSiteId(webSiteId);

					if(UtilValidate.isEmpty(context.get("_SALES_CHANNEL_ENUM_ID"))) {
						context.put("_SALES_CHANNEL_ENUM_ID", EnvUtil.getSalesChannelEnumId(webSiteId));
					}
				}
				if(UtilValidate.isNotEmpty(context.get("_SALES_CHANNEL_ENUM_ID"))) {
					request.setAttribute("_SALES_CHANNEL_ENUM_ID", (String) context.get("_SALES_CHANNEL_ENUM_ID"));
					cart.setChannelType((String) context.get("_SALES_CHANNEL_ENUM_ID"));
				}
				String result = CheckOutEvents.createOrder(request, response);

				if(UtilValidate.isNotEmpty((String) request.getAttribute("orderId"))) {
					orderId = (String) request.getAttribute("orderId");
					Set productPromoCodes = cart.getProductPromoCodesEntered();
					Iterator productPromoCodesIterator = productPromoCodes.iterator();
					while (productPromoCodesIterator.hasNext()) {
						String productPromoCodeId = (String) productPromoCodesIterator.next();
						if (PromoHelper.isSampleCoupon(delegator, productPromoCodeId)) {
							PromoHelper.discProductPromoCode(delegator, productPromoCodeId);
						}
					}

					orderData = OrderHelper.getOrderData(delegator, orderId, false);
					jsonResponse.put("orderData", orderData);

					//get roles
					isTrade = cart.getTradeStatus();
					isNonProfit = cart.getNonProfitStatus();

					boolean sendNeedArtworkEmail = false;
					List<Map> itemList = (List<Map>) orderData.get("items");
					if (UtilValidate.isNotEmpty(itemList)) {
						for (Map item : itemList) {
							if(ProductHelper.hasInventory(delegator, ((GenericValue) item.get("item")).getString("productId"), ((GenericValue) item.get("item")).getBigDecimal("quantity").longValue(), true)) {
								boolean catchStatusUpdate = OrderHelper.updateStatusForPrintItem(dispatcher, delegator, (GenericValue) item.get("item"), webSiteId);
								if (catchStatusUpdate) {
									sendNeedArtworkEmail = true;
								}
							} else {
								if(!OrderHelper.isItemSample((GenericValue) item.get("item"), null)) {
									dispatcher.runAsync("changeOrderItemStatus", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", ((GenericValue) item.get("item")).getString("orderItemSeqId"), "statusId", "ITEM_BACKORDERED", "userLogin", EnvUtil.getSystemUser(delegator)));
								}
							}

							//if the customer is trade, update orderitem and set isRush to Y
							isOrderPrinted = (OrderHelper.isItemPrinted(delegator, null, null, (GenericValue) item.get("item"))) || isOrderPrinted;
							if(isTrade && OrderHelper.isItemPrinted(delegator, null, null, (GenericValue) item.get("item")) && ProductHelper.allowRushProduction()) {
								OrderHelper.updateOrderItem(delegator, (GenericValue) item.get("item"), UtilMisc.<String, String>toMap("isRushProduction", "Y"));
							}

							//if this is a quote item, set the required data for the quote and update status
							hasQuote = updateQuoteForOrder(dispatcher, delegator, (GenericValue) item.get("item"));
						}
						if (sendNeedArtworkEmail) {
							dispatcher.runAsync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "UploadArtworkLater", "data", UtilMisc.toMap("OrderNumber", orderId), "email", orderData.get("email"), "webSiteId", webSiteId));
						}
					}

					//if the order is check, or not free and offline payment (like iparcel) then set it to pending
					if((paymentMethodTypeId).equalsIgnoreCase("PERSONAL_CHECK") || (paymentMethodTypeId).equalsIgnoreCase("EXT_IPARCEL") || (paymentMethodTypeId).equalsIgnoreCase("EXT_CLOSED")) {
						setPending = true;
					}

					if(request.getSession().getAttribute("scene7PartyId") != null) {
						EnvLoginEvents.updatePartyForScene7UserContent(request, response);
					}

					List<GenericValue> s7Items = OrderHelper.getS7OrderItemArtworks(delegator, orderId);
					if(UtilValidate.isNotEmpty(s7Items)) {
						for(GenericValue s7Item : s7Items) {
							Map<String, Object> s7Map = new HashMap<String, Object>();
							s7Map.put("orderId", orderId);
							s7Map.put("orderItemSeqId", s7Item.getString("orderItemSeqId"));
							s7Map.put("scene7DesignId", s7Item.getString("scene7DesignId"));
							s7Map.put("userLogin", userLogin);
							s7Map.put("previewFiles", Boolean.TRUE);
							s7Map.put("proofFile", Boolean.FALSE);
							s7Map.put("printFiles", Boolean.FALSE);
							dispatcher.runAsync("generateScene7Files", s7Map);

							s7Map.put("previewFiles", Boolean.FALSE);
							s7Map.put("proofFile", Boolean.TRUE);
							s7Map.put("printFiles", Boolean.FALSE);
							dispatcher.runAsync("generateScene7Files", s7Map);

							s7Map.put("previewFiles", Boolean.FALSE);
							s7Map.put("proofFile", Boolean.FALSE);
							s7Map.put("printFiles", Boolean.TRUE);
							dispatcher.runAsync("generateScene7Files", s7Map);
						}
					}

					dispatcher.schedule("exportOrder", UtilMisc.toMap("orderIds", UtilMisc.toList(orderId), "ignoreValidity", Boolean.FALSE, "userLogin", userLogin), jobStartTime);
					dispatcher.runAsync("exportCustomer", UtilMisc.toMap("partyId", userLogin.getString("partyId"), "orderId", orderId, "quoteId", null, "userLogin", userLogin));
					dispatcher.runAsync("generateArtworkFiles", UtilMisc.toMap("orderId", orderId,"userLogin", userLogin));
					cart.clear();
				}
				errorMessage = (String) request.getAttribute("_ERROR_MESSAGE_");
				success = true;
				//Since the order has been placed successfully, remove the parameterMap from the session
				request.getSession().removeAttribute("parameterMap");
			} catch(Exception e) {
				TransactionUtil.rollback(beganTransaction, "Error creating order", e);
				errorMessage = (String) request.getAttribute("_ERROR_MESSAGE_");
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to save order. " + e + " : " + e.getMessage(), module);
			} finally {
				try {
					if(removeUserFromSession) {
						cart.removeUserLogin();
						session.removeAttribute("userLogin");
					}
					// only commit the transaction if we started one... this will throw an exception if it fails
					TransactionUtil.commit(beganTransaction);
				} catch(GenericEntityException e) {
					Debug.logError(e, "Could not create new order.", module);
					success = false;
				}
			}
		} catch(GenericTransactionException e) {
			errorMessage = (String) request.getAttribute("_ERROR_MESSAGE_");
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to save order. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		if(!success) { // This is required to show checkout page with user entered data and error message, in case of errors
			//Store the user inputs from the checkout page in the http session, so that they won't be re-entered in case of checkout errors
			request.getSession().setAttribute("parameterMap", context);
			errors.put("error", "An error occurred while placing the order");
		} else {
			if(UtilValidate.isNotEmpty(orderId)) {
				BrontoUtil.sendCartData(request, true);
				//send the order confirmation email, temporary password email and export order XML data to Switch
				try {
					orderInfo = OrderHelper.buildOrderDetails(request, orderData);
					orderData.put("isTrade", isTrade? "Y" : "N");
					orderData.put("isNonProfit", isNonProfit? "Y" : "N");
					orderData.put("password", tempPassword);
					String _errorMessage = "";

					try {
						if(sendEmail) {
							//send email to customer to send addressbook
							//
							boolean sendNeedAddressBookEmail = false;
							List<Map<String, Object>> lineItems = (List<Map<String, Object>>) orderInfo.get("lineItems");
							for(Map<String, Object> lineItem : lineItems) {
								//if has addresses and scene7 but no addressbook then send email, if has addressing but no scene7 then send email
								if((Integer) lineItem.get("addresses") > 0 && UtilValidate.isNotEmpty(lineItem.get("scene7DesignId"))) {
									String addressBookId = AddressBookEvents.getAddressBookId(delegator, (String) lineItem.get("scene7DesignId"), "0");
									if(UtilValidate.isEmpty(addressBookId)) {
										sendNeedAddressBookEmail = true;
									}
								} else if((Integer) lineItem.get("addresses") > 0 && UtilValidate.isEmpty(lineItem.get("scene7DesignId"))) {
									sendNeedAddressBookEmail = true;
								}
							}

							if(sendNeedAddressBookEmail) {
								Map<String, String> addressBookEmailData = EmailHelper.createEmailAddressBookData(delegator, dispatcher, orderData, orderId, webSiteId);
								//ServiceHelper.runAsync(dispatcher, "sendEmail", UtilMisc.toMap("email", orderInfo.get("email"), "rawData", orderData, "data", addressBookEmailData, "messageType", "requestAddressBook", "webSiteId", webSiteId), _errorMessage, true);
							}

							_errorMessage = "Error sending order confirmation email for order with id " + orderId;
							//Map<String, String> emailData = EmailHelper.createOrderConfirmationEmailData(delegator, dispatcher, orderData, orderId, webSiteId);
							Map<String, String> emailData = ListrakHelper.createOrderConfirmationEmailData(delegator, dispatcher, orderData, orderId, webSiteId);


							if (UtilValidate.isNotEmpty(emailData.get("quoteAssignedToEmails"))) {
								String[] quoteAssignedToEmails = emailData.get("quoteAssignedToEmails").split(",");

								for (String quoteAssignedToEmail : quoteAssignedToEmails) {
									ServiceHelper.runAsync(dispatcher, "sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "QuoteAssignment", "data", emailData, "email", quoteAssignedToEmail, "webSiteId", webSiteId), _errorMessage, true);
								}
							}

							ServiceHelper.runAsync(dispatcher, "sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "OrderConfirmation", "data", emailData, "email", orderInfo.get("email"), "webSiteId", webSiteId), _errorMessage, true);
						}
					} catch (GenericEntityException e) {
						EnvUtil.reportError(e);
						Debug.logError(e, _errorMessage + " " + e + " : " + e.getMessage(), module);
					}

					if(sendEmail && tempPassword != null) {
						_errorMessage = "Error sending Temporary Password email for order with id " + orderId;
						try {
							Map<String, String> emailData = ListrakHelper.createPasswordEmailData(delegator, orderData, orderId);
							ServiceHelper.runAsync(dispatcher, "sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "TemporaryPassword", "data", emailData, "email", orderInfo.get("email"), "webSiteId", webSiteId), _errorMessage, true);
						} catch (GenericEntityException e) {
							EnvUtil.reportError(e);
							Debug.logError(e, _errorMessage + " " + e + " : " + e.getMessage(), module);
						}
					}

					//update amazon order with ofbiz order data including order id and total
					if(paymentMethodTypeId.equalsIgnoreCase("EXT_AMAZON")) {
						SetOrderReferenceDetailsResponse mwsResponse = AmazonPaymentHelper.setOrderReferenceDetailsResponse((String) orderInfo.get("externalId"), ((BigDecimal) orderInfo.get("grandTotal")).toString(), orderId, (String) orderInfo.get("orderNotes"));
						if(mwsResponse != null) {
							SetOrderReferenceDetailsResult setOrderReferenceDetailsResult = mwsResponse.getSetOrderReferenceDetailsResult();
							if(setOrderReferenceDetailsResult.isSetOrderReferenceDetails()) {
								ConfirmOrderReferenceResponse mwsResponseConfirm = AmazonPaymentHelper.setConfirmOrderReferenceResponse((String) orderInfo.get("externalId"));
								if(mwsResponseConfirm.isSetResponseMetadata()) {
									//TODO
								}
							}
						}
					}

					//export to switch
					_errorMessage = "Error exporting OrderXML to switch for order with id " + orderId;
					ServiceHelper.schedule(dispatcher, "exportOrderXML", UtilMisc.toMap("orderId", orderId, "sequence", Integer.valueOf(1), "userLogin", EnvUtil.getSystemUser(delegator)), jobStartTime, _errorMessage, true);

					//remove persistent data
					PersistentCart.destroy(request, response, null, true);
					PersistentCart.setCookie(request, response, true);
				} catch (Exception e) {
					Debug.logError(e, "Error sending email order confirmation.", module);
				}
			}

			//if its a folders custom quote order, auth needs to happen in netsuite
			if("CREDIT_CARD".equalsIgnoreCase(paymentMethodTypeId) && "folders".equalsIgnoreCase(webSiteId) && (isOrderPrinted || hasQuote)) {
				Debug.logInfo("Printed Quote Folders Order, No Auth or Capture.", module);
				doAuth = false;
				doCapture = false;
			}

			if(doAuth && UtilValidate.isNotEmpty(context.get("NONCE_TOKEN"))) {
				try {
					Map<String, String> braintreeResponse = BraintreeHelper.preAuth(orderInfo, paymentMethodTypeId, (String) context.get("NONCE_TOKEN"), webSiteId);

					GenericValue orderPaymentPreference = delegator.findByAnd("OrderPaymentPreference", UtilMisc.toMap("orderId", orderId), null, false).get(0);
					orderPaymentPreference.put("paymentMethodTypeId", paymentMethodTypeId);

					if (UtilValidate.isNotEmpty(braintreeResponse.get("transactionId")) && UtilValidate.isNotEmpty(braintreeResponse.get("creditCardToken"))) {
						orderPaymentPreference.put("statusId", "PAYMENT_AUTHORIZED");
					} else {
						orderPaymentPreference.put("statusId", "PAYMENT_DECLINED");

						setPending = true;
					}

					delegator.store(orderPaymentPreference);

					if (UtilValidate.isNotEmpty(braintreeResponse.get("creditCardToken")) && UtilValidate.isNotEmpty(braintreeResponse.get("transactionId"))) {
						Gson gson = new Gson();

						braintreeResponse.put("orderPaymentPreferenceId", (String) orderPaymentPreference.get("orderPaymentPreferenceId"));
						braintreeResponse.put("orderId", orderId);

						GenericValue paymentGatewayResponse = delegator.makeValue("PaymentGatewayResponse");
						paymentGatewayResponse.put("paymentGatewayResponseId", delegator.getNextSeqId("PaymentGatewayResponse"));
						paymentGatewayResponse.put("paymentServiceTypeEnumId", "PRDS_PAY_AUTH");
						paymentGatewayResponse.put("orderPaymentPreferenceId", orderPaymentPreference.get("orderPaymentPreferenceId"));
						paymentGatewayResponse.put("paymentMethodTypeId", "CREDIT_CARD");
						paymentGatewayResponse.put("paymentMethodId", paymentMethodId);
						paymentGatewayResponse.put("transCodeEnumId", "PGT_AUTHORIZE");
						paymentGatewayResponse.put("amount", new BigDecimal(String.valueOf(orderInfo.get("grandTotal"))));
						paymentGatewayResponse.put("currencyUomId", "USD");
						paymentGatewayResponse.put("gatewayAvsResult", "N");
						paymentGatewayResponse.put("gatewayCvResult", "N");
						//paymentGatewayResponse.put("gatewayMessage", "Exact Match");
						paymentGatewayResponse.put("transactionDate", UtilDateTime.nowTimestamp());
						paymentGatewayResponse.put("requestMsg", gson.toJson(braintreeResponse));
						delegator.create(paymentGatewayResponse);
					}
				} catch (GenericEntityException gee) {
					EnvUtil.reportError(gee);
					Debug.logError(gee, "Issue trying to create Order Payment Preference for CC Billing. " + gee + " : " + gee.getMessage(), module);
				}
			} else if (doAuth) {
				String _errorMessage = "";
				_errorMessage = "Error authorizing the credit card payment for order with id " + orderId;
				Map<String, Object> authResult = ServiceHelper.runSync(dispatcher, "authOrderPayments", UtilMisc.toMap("orderId", orderId, "reAuth", false, "userLogin", userLogin), _errorMessage, true);
				//set order to pending if declined or auth service failed
				if(authResult == null || authResult.isEmpty() || "FAILED".equals((String) authResult.get("processResult"))) {
					_errorMessage = "Error changing order status to ORDER_PENDING for order with id " + orderId;
					setPending = true;

					try {
						if(sendEmail) {
							_errorMessage = "Error sending order pending email for order with id " + orderId;
							Map<String, String> emailData = ListrakHelper.createOrderPendingEmailData(delegator, orderData, orderId);
							ServiceHelper.runAsync(dispatcher, "sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "OrderPending", "data", emailData, "email", orderData.get("email"), "webSiteId", webSiteId), _errorMessage, true);
						}
					} catch (GenericEntityException e) {
						EnvUtil.reportError(e);
						Debug.logError(e, _errorMessage + " " + e + " : " + e.getMessage(), module);
					}
				} else {
					//successful auth, change status for c2m orders
					try {
						if(DirectMailingHelper.isDirectMailingOrder(delegator, orderId)) {
							_errorMessage = "Error submitting direct mailing job to Click2Mail for order with id " + orderId;

							List<Map> itemList = (List<Map>) orderData.get("items");
							for(Map<String, Object> lineItem : itemList) {
								if(DirectMailingHelper.isDirectMailingOrderItem(delegator, orderId, ((GenericValue) lineItem.get("item")).getString("orderItemSeqId"))) {
									ServiceHelper.runAsync(dispatcher, "changeOrderItemStatus", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", ((GenericValue) lineItem.get("item")).getString("orderItemSeqId"), "statusId", "ART_DIRECTMAIL", "userLogin", EnvUtil.getSystemUser(delegator)), _errorMessage, true);
								}
							}
						}
					} catch (Exception dme) {
						EnvUtil.reportError(dme);
						Debug.logError(dme, _errorMessage + " " + dme + " : " + dme.getMessage(), module);
					}
				}

				//if its amazon we will always send to pending after auth
				if(paymentMethodTypeId.equalsIgnoreCase("EXT_AMAZON") || paymentMethodTypeId.equalsIgnoreCase("EXT_PAYPAL_CHECKOUT")) {
					setPending = true;
				}
			}

			if(doCapture) {
				String _errorMessage = "Error capturing the credit card payment for order with id " + orderId;
				try {
					Calendar cal2 = Calendar.getInstance();
					cal2.add(Calendar.MINUTE, 1);
					long captureTime = cal2.getTimeInMillis();
					ServiceHelper.schedule(dispatcher, "captureOrderPayments", UtilMisc.toMap("orderId", orderId, "captureAmount", (BigDecimal) orderInfo.get("grandTotal"), "userLogin", EnvUtil.getSystemUser(delegator)), captureTime, _errorMessage, true);
				} catch (GenericEntityException e) {
					EnvUtil.reportError(e);
					Debug.logError(e, _errorMessage + " " + e + " : " + e.getMessage(), module);
				}
			}

			if(setPending) {
				ServiceHelper.runAsync(dispatcher, "changeOrderStatus", UtilMisc.toMap("orderId", orderId, "statusId", "ORDER_PENDING", "userLogin", userLogin), "", true);
			}
		}
		jsonResponse.put("errors", errors);
		jsonResponse.put("error", errorMessage);
		jsonResponse.put("eventName", "checkout");
		jsonResponse.put("orderId", orderId);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static boolean isFreeOrder(HttpServletRequest request) {
		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		return isOrderFree(cart);
	}

	/*
	 * Is cart zero
	 */
	private static boolean isOrderFree(ShoppingCart cart) {
		return (cart.getGrandTotal().compareTo(BigDecimal.ZERO) == 0);
	}

	/*
	 * Does order have shipping
	 */
	private static boolean foundShipping(ShoppingCart cart) {
		boolean hasFreeShipItem = CartHelper.hasFreeShipItem(cart);

		if("FIRST_CLASS".equalsIgnoreCase(cart.getShipmentMethodTypeId()) || "PICKUP".equalsIgnoreCase(cart.getShipmentMethodTypeId())) {
			return true;
		} else if(cart.getTotalShipping().compareTo(BigDecimal.ZERO) == 0 && !hasFreeShipItem) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Add shipping to cart if its not available
	 */
	private static void addShippingToCart(Delegator delegator, LocalDispatcher dispatcher, Map<String, String> postalAddress, ShoppingCart cart) throws GenericEntityException, GenericServiceException, Exception {
		GenericValue foundShipMethod = null;
		BigDecimal shipCost = BigDecimal.ZERO;

		Map<GenericValue, BigDecimal> estimates = ShippingHelper.getAllShippingEstimates(delegator, dispatcher, postalAddress, cart);
		if(UtilValidate.isNotEmpty(estimates)) {
			Iterator estimateIter = estimates.keySet().iterator();
			while(estimateIter.hasNext()) {
				GenericValue shipMethod = (GenericValue) estimateIter.next();
				if(shipCost.compareTo(BigDecimal.ZERO) == 0 || (((BigDecimal) estimates.get(shipMethod)).compareTo(BigDecimal.ZERO) > 0 && ((BigDecimal) estimates.get(shipMethod)).compareTo(shipCost) < 0)) {
					shipCost = (BigDecimal) estimates.get(shipMethod);
					foundShipMethod = shipMethod;
				}
			}
		}

		//set the cart shipping if we find a matching value or assign the first one
		if(foundShipMethod != null) {
			cart.setShipTaxPostalAddress(postalAddress);
			CartHelper.setShipmentMethodTypeId(dispatcher, cart, 0, foundShipMethod.getString("shipmentMethodTypeId"), foundShipMethod.getString("partyId"));
			CartHelper.setShippingTotal(cart, 0, shipCost);
			CartHelper.setTaxTotal(dispatcher, null, cart);
		}
	}

	/**
	 * Get the context from the request attributes
	 * This is used when we create orders without using user inputed data in checkout.ftl
	 * @param context
	 * @param request
	 * @return
	 */
	private static Map<String, Object> getContextFromRequestAttribute(Map<String, Object> context, HttpServletRequest request) {
		//if this is not a request from checkout.ftl ignore and exit
		if(UtilValidate.isEmpty(request.getAttribute("tempOrderId")) && UtilValidate.isEmpty(request.getAttribute("tempCart"))) {
			return context;
		}

		Map<String, Object> newContext = new HashMap<>();

		Map<String, String> tempShippingAddress = (Map<String, String>) request.getAttribute("tempShippingAddress");
		Map<String, String> tempBillingAddress = (Map<String, String>) request.getAttribute("tempBillingAddress");
		String tempShippingPhone = (String) request.getAttribute("tempShippingPhone");
		String tempBillingPhone = (String) request.getAttribute("tempBillingPhone");
		String tempPaymentMethodTypeId = (String) request.getAttribute("tempPaymentMethodTypeId");
		String tempEmail = (String) request.getAttribute("tempEmail");
		String externalId = (String) request.getAttribute("externalId");
		String webSiteId = (String) request.getAttribute("_WEB_SITE_ID_");

		if(UtilValidate.isNotEmpty(tempShippingAddress)) {
			Iterator addressIter = tempShippingAddress.keySet().iterator();
			while (addressIter.hasNext()) {
				String key = (String) addressIter.next();
				newContext.put(key, tempShippingAddress.get(key));
			}
		}

		if(UtilValidate.isNotEmpty(tempBillingAddress)) {
			Iterator addressIter = tempBillingAddress.keySet().iterator();
			while (addressIter.hasNext()) {
				String key = (String) addressIter.next();
				newContext.put(key, tempBillingAddress.get(key));
			}
		}

		newContext.put("shipping_contactNumber", tempShippingPhone);
		newContext.put("billing_contactNumber", tempBillingPhone);
		newContext.put("emailAddress", tempEmail);

		newContext.put("paymentMethodTypeId", tempPaymentMethodTypeId);
		newContext.put("externalId", externalId);
		newContext.put("_WEB_SITE_ID_", webSiteId);

		return newContext;
	}

	/**
	 * If an item has a quote associated to it, update the quote and say its ordered
	 * @param dispatcher
	 * @param delegator
	 * @param orderItem
	 * @throws GenericEntityException
	 * @throws GenericServiceException
	 */
	private static boolean updateQuoteForOrder(LocalDispatcher dispatcher, Delegator delegator, GenericValue orderItem) throws GenericEntityException, GenericServiceException {
		boolean success = false;

		if(UtilValidate.isNotEmpty(orderItem.getString("referenceQuoteId")) && UtilValidate.isNotEmpty(orderItem.getString("referenceQuoteItemSeqId"))) {
			GenericValue quoteItem = EntityQuery.use(delegator).from("QcQuote").where("quoteId", orderItem.getString("referenceQuoteItemSeqId"), "quoteRequestId", orderItem.getString("referenceQuoteId")).queryFirst();
			if(quoteItem != null) {
				quoteItem.set("orderId", orderItem.getString("orderId"));
				quoteItem.set("orderItemSeqId", orderItem.getString("orderItemSeqId"));
				quoteItem.store();

				//update the status of the quote to ordered
				dispatcher.runAsync("changeQuoteStatus", UtilMisc.toMap("quoteId", orderItem.getString("referenceQuoteId"), "statusId", "QUO_ORDERED", "changeReason", "Created Order: " + orderItem.getString("orderId"), "userLogin", EnvUtil.getSystemUser(delegator)));
				success = true;
			}
		}

		return success;
	}
}
