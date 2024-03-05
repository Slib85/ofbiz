/**
 * Created by Manu on 9/23/2014.
 */
import java.util.*;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartEvents;
import com.envelopes.party.PartyHelper;
import com.envelopes.cart.CartEvents;
import com.envelopes.order.CheckoutEvents;
import com.envelopes.bronto.BrontoUtil;
import com.envelopes.quote.QuoteHelper;
import com.envelopes.cart.CartHelper;
import com.envelopes.util.EnvUtil;
import com.bigname.payments.paypal.PayPalCheckoutHelper;

String module ="checkout.groovy";

context.paypalEnabled = PayPalCheckoutHelper.PAYPAL_ENABLED;

if(CartEvents.isCartEmpty(request)) {
    request.setAttribute("emptyCart", true);
} else {
    request.setAttribute("emptyCart", false);
    GenericValue loggedInUser = (GenericValue) request.getSession().getAttribute("userLogin");

    request.setAttribute("signedInUser", loggedInUser != null);
    context.freeOrder = CheckoutEvents.isFreeOrder(request);
    context.currentYear = Calendar.getInstance().get(Calendar.YEAR);

    Map savedResponse = request.getAttribute("savedResponse");

    if(loggedInUser != null && savedResponse == null) {
        request.setAttribute("isCheckOut", "yes");
        BrontoUtil.sendCartData(request);
    }

    if(request.getSession().getAttribute("parameterMap") != null) {
        context.parameterMap = request.getSession().getAttribute("parameterMap");
    } else {
        context.parameterMap = new HashMap();
    }

    if(savedResponse != null) {
        if(!savedResponse.get("errors").isEmpty()) {
            if(savedResponse.get("eventName").equals("login")) {
                context.checkoutError = ((Map)savedResponse.get("errors")).get("error");
                context.hasLoginError = "true";
                if(!savedResponse.get("parameterMap").isEmpty()) {
                    context.userName = ((Map)savedResponse.get("parameterMap")).get("USERNAME");
                }
            } else if(savedResponse.get("eventName").equals("checkout")) {
                context.checkoutError = ((Map)savedResponse.get("errors")).get("error");
            }

        }

        if(savedResponse.containsKey("messages") && !savedResponse.get("messages").isEmpty()) {
            context.checkoutMessage = ((Map)savedResponse.get("messages")).get("message");
        }
    }

    if(userLogin != null) {
        Map addressBook = PartyHelper.getAddressBook(request, response);
        if(addressBook != null && !addressBook.isEmpty()) {
            context.billingAddresses = addressBook.get(PartyHelper.BILLING_ADDRESS);
            context.shippingAddresses = addressBook.get(PartyHelper.SHIPPING_ADDRESS);
        }
    }

    //Additional Parameters needed for checkout
    if(loggedInUser != null) {
        context.userLoginId = loggedInUser.getString("userLoginId");
        context.partyId = loggedInUser.getString("partyId");

        //get party roles
        Map roles = dispatcher.runSync("getAEPartyRoles", [partyId : loggedInUser.getString("partyId"), userLogin : loggedInUser]);
        context.isNet30 = (UtilValidate.isNotEmpty(roles) && UtilValidate.isNotEmpty(roles.get("isNet30"))) ? ((Boolean) roles.get("isNet30")).booleanValue() : false;
    } else {
        if(CartHelper.hasQuote(ShoppingCartEvents.getCartObject(request))) {
            String partyId = QuoteHelper.getPartyIdFromQuote(delegator, ShoppingCartEvents.getCartObject(request), null);
            if (UtilValidate.isNotEmpty(partyId)) {
                Map roles = dispatcher.runSync("getAEPartyRoles", [partyId: partyId, userLogin: EnvUtil.getSystemUser(delegator)]);
                context.isNet30 = (UtilValidate.isNotEmpty(roles) && UtilValidate.isNotEmpty(roles.get("isNet30"))) ? ((Boolean) roles.get("isNet30")).booleanValue() : false;
            }
        }
    }

    context.orderInfo = CartEvents.getCart(request);
}