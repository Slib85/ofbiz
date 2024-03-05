import java.util.*;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import com.envelopes.email.*;
import com.envelopes.order.*;
import com.envelopes.bronto.BrontoUtil;

String module = "receipt.groovy";

String orderId = request.getParameter("orderId");
context.invalidSession = 'false';
if(UtilValidate.isNotEmpty(request.getParameter("orderId")) || UtilValidate.isNotEmpty((String) request.getAttribute("orderId"))) {
    request.setAttribute("saveResponse", true);
    request.setAttribute("newOrder", true);
    OrderEvents.getOrderDetails(request, response);
    Map orderDetails = request.getAttribute("savedResponse").get("orderData");
    orderDetails.put("openOrder", false);
    Map orderInfo = OrderHelper.buildOrderDetails(request, orderDetails);
	BrontoUtil.sendCheckoutData(request, orderInfo);
    context.orderInfo = orderInfo;
// Set the orderInfo object to the request, so that order-details.groovy can reuse it.
    request.setAttribute("orderInfo", context.orderInfo);
} else {
    context.invalidSession = 'true';
}



