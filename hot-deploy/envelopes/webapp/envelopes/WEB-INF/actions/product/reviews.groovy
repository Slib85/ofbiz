import org.apache.ofbiz.base.util.UtilValidate;
import com.envelopes.product.*;

import com.envelopes.util.*;

String module = "reviews.groovy";
String emailAddress = "", orderId = "";
Map<String, Object> params = EnvUtil.getParameterMap(request);

if(request.getSession().getAttribute("userLogin") != null) {
	context.emailAddress = userLogin.get("userLoginId");
} else if(UtilValidate.isNotEmpty(emailAddress = (String)params.get("emailAddr"))) {
    context.emailAddress = emailAddress;
}

if(UtilValidate.isNotEmpty(orderId = (String)params.get("orderId"))) {
    context.orderId = orderId;
}
String productId = "";
if(UtilValidate.isNotEmpty(productId = (String) params.get("product_id"))) {
    context.productId = productId;
	context.productReviews = ProductEvents.getReviews(request, productId);
	context.productRating = (String)params.get("product_rating");
} else {
	context.productId = request.getSession().getAttribute("productId");
	context.productReviews = request.getSession().getAttribute("reviews");
}
