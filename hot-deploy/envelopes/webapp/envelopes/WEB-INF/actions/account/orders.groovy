String module = "orders.groovy"
import com.envelopes.order.OrderHelper;

if(request.getSession().getAttribute("userLogin") != null) {
    request.setAttribute("orderType", context.orderType);
    context.orders = OrderHelper.getOrderList(request, response);
}

