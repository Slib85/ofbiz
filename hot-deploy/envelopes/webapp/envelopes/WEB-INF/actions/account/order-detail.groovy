import java.util.*;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import com.envelopes.order.*;

String module = "order-detail.groovy"

context.orderId = request.getParameter("orderId");

request.setAttribute("saveResponse", true);
if(request.getAttribute("_CURRENT_VIEW_") == "orderStatusInfo") {
	OrderEvents.getOrderDetailsAnon(request, response);
} else {
	OrderEvents.getOrderDetails(request, response);
}
if(UtilValidate.isNotEmpty(request.getAttribute("savedResponse"))) {
	Map orderDetails = request.getAttribute("savedResponse").get("orderData");
	orderDetails.put("openOrder", false);
	context.orderInfo = OrderHelper.buildOrderDetails(request, orderDetails);

	// Set the orderInfo object to the request, so that order-details.groovy can reuse it.
	request.setAttribute("orderInfo", context.orderInfo);
}