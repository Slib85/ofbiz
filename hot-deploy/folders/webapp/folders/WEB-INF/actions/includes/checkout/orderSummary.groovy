import com.envelopes.cart.CartEvents;
import com.envelopes.order.OrderEvents;
import com.envelopes.order.OrderHelper;
import org.apache.ofbiz.base.util.UtilValidate;

String module = "orderSummary.groovy";

request.setAttribute("saveResponse", true);
if(context.get("currentView").equalsIgnoreCase("checkout")) {
    CartEvents.getCart(request, response);
    context.pCart = request.getSession().getAttribute("PersistentCart");

    if(CartEvents.isCartEmpty(request)) {
        context.emptyCart = true;
    } else {
        context.emptyCart = false;
        context.orderInfo = request.getAttribute("savedResponse");
    }
} else {
    context.orderId = request.getParameter("orderId");
    if(context.get("currentView").equalsIgnoreCase("orderStatusInfo")) {
        OrderEvents.getOrderDetailsAnon(request, response);
    } else {
        OrderEvents.getOrderDetails(request, response);
    }
    if(UtilValidate.isNotEmpty(request.getAttribute("savedResponse"))) {
        Map orderDetails = request.getAttribute("savedResponse").get("orderData");
        orderDetails.put("openOrder", false);
        context.orderInfo = OrderHelper.buildOrderDetails(request, orderDetails);
        request.setAttribute("orderInfo", context.orderInfo);
    }
}