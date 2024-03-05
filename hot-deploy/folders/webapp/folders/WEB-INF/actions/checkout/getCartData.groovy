import java.util.*;
import com.envelopes.order.*;
import com.envelopes.cart.*;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartEvents;

String module = "getCartData.groovy";

if(request.getAttribute("orderInfo") != null) {
    context.orderInfo = request.getAttribute("orderInfo");
} else {
    if(context.pageType.equals("cart") || context.pageType.equals("checkout")) {
        request.setAttribute("saveResponse", true);
        request.setAttribute("updatePersistentCart", true);
        CartEvents.getCart(request, response);
        context.orderInfo = request.getAttribute("savedResponse");
        context.crossSells = CartHelper.getCrossSellItems(request);
    } else {
        request.setAttribute("saveResponse", true);
        OrderEvents.getOrderDetails(request, response);
        Map orderDetails = request.getAttribute("savedResponse").get("orderData");
        orderDetails.put("openOrder", false);
        context.orderInfo = OrderHelper.buildOrderDetails(request, orderDetails);
    }
}

context.hasQuote = CartHelper.hasQuote(ShoppingCartEvents.getCartObject(request));
context.hasPrinted = CartHelper.hasPrinted(ShoppingCartEvents.getCartObject(request));
/*
import com.envelopes.cart.CartEvents;
import org.apache.ofbiz.base.util.UtilValidate;
import com.envelopes.bronto.BrontoUtil;
import com.envelopes.order.*;

String module = "getCartData.groovy";
request.setAttribute("saveResponse", true);
CartEvents.getCart(request, response);
context.pCart = request.getSession().getAttribute("PersistentCart");

if(CartEvents.isCartEmpty(request)) {
    context.emptyCart = true;
} else {
    context.emptyCart = false;
    context.orderInfo = request.getAttribute("savedResponse");
    BrontoUtil.sendCartData(request);
}

if(UtilValidate.isNotEmpty(request.getParameter("lastVisitedURL"))) {
    session.setAttribute("lastVisitedURL", request.getParameter("lastVisitedURL"));
    context.lastVisitedURL = request.getParameter("lastVisitedURL");
}
*/