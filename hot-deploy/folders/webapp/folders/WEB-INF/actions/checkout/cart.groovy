import com.envelopes.cart.*;
import org.apache.ofbiz.base.util.UtilValidate;
import com.envelopes.bronto.BrontoUtil;
import com.envelopes.order.CheckoutEvents;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartEvents;

String module = "cart.groovy";
request.setAttribute("saveResponse", true);
context.put("isCartEmpty", CartEvents.isCartEmpty(request));
// This is required for bronto...
if(!CartEvents.isCartEmpty(request)) {
    context.orderInfo = request.getAttribute("savedResponse");
    BrontoUtil.sendCartData(request);
}