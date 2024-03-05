import com.envelopes.cart.CartEvents;
import org.apache.ofbiz.base.util.UtilValidate;
import com.envelopes.bronto.BrontoUtil;

String module = "cart.groovy";
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