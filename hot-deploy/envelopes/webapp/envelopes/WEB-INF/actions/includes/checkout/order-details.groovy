import java.util.*;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartEvents;
import com.envelopes.product.Product;
import com.envelopes.order.*;
import com.envelopes.cart.*;

String module = "order-details.groovy";
if(request.getAttribute("orderInfo") != null) {
    context.orderInfo = request.getAttribute("orderInfo");
} else {
    if(context.pageType.equals("cart") || context.pageType.equals("checkout")) {
        request.setAttribute("saveResponse", true);
        request.setAttribute("updatePersistentCart", true);
        CartEvents.getCart(request, response);
        context.orderInfo = request.getAttribute("savedResponse");
        context.crossSells = CartHelper.getCrossSellItems(request);
        if(!CartHelper.hasSwatchBook(ShoppingCartEvents.getCartObject(request))) {
            context.swatchBook = new Product(delegator, dispatcher, "SWATCHBOOK", request)
        }
    } else {
        request.setAttribute("saveResponse", true);
        OrderEvents.getOrderDetails(request, response);
        Map orderDetails = request.getAttribute("savedResponse").get("orderData");
        orderDetails.put("openOrder", false);
        context.orderInfo = OrderHelper.buildOrderDetails(request, orderDetails);
    }
}