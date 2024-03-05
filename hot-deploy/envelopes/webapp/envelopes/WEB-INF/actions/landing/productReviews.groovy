import org.apache.ofbiz.base.util.UtilValidate;
import com.envelopes.product.*;

import com.envelopes.util.*;

String module = "productReviews.groovy";
Map<String, Object> params = EnvUtil.getParameterMap(request);

String productId = "";
if(UtilValidate.isNotEmpty(productId = (String) params.get("product_id"))) {
    context.productId = productId;
	context.productReviews = ProductEvents.getReviews(request, productId);
	context.productRating = (String)params.get("product_rating");
} else {
	context.productId = request.getSession().getAttribute("productId");
	context.productReviews = ProductReviewEvents.getTopReviewList(request, response, productId);
	context.productRating = (String)params.get("product_rating");
}
