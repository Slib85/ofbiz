import com.envelopes.product.ProductEvents;
import com.envelopes.scene7.Scene7Events;

import java.lang.*;
import java.util.*;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.GenericValue;

import com.envelopes.cart.CartHelper;
import com.envelopes.product.Product;
import com.envelopes.util.*;

String module = "productComparison.groovy";

Map<String, Object> params = EnvUtil.getParameterMap(request);

List<Product> products = new ArrayList<>();
ArrayList<String> FEATURES_TO_SHOW = new ArrayList<>(ProductEvents.FEATURES_TO_SHOW);
FEATURES_TO_SHOW.remove("SHAPE");
FEATURES_TO_SHOW.remove("INNER_PANEL");
FEATURES_TO_SHOW.remove("INNER_POCKET");
FEATURES_TO_SHOW.remove("LABELS_PER_SHEET");
FEATURES_TO_SHOW.remove("TOP_MARGIN");
FEATURES_TO_SHOW.remove("BOTTOM_MARGIN");
FEATURES_TO_SHOW.remove("LEFT_MARGIN");
FEATURES_TO_SHOW.remove("RIGHT_MARGIN");
FEATURES_TO_SHOW.remove("CORNER_RADIUS");
FEATURES_TO_SHOW.remove("VERTICAL_SPACING");
FEATURES_TO_SHOW.remove("HORIZONTAL_SPACING");
FEATURES_TO_SHOW.remove("BACKSLITS");

if(UtilValidate.isNotEmpty(params.get("productIds"))) {

	try{
		String[] productIds = ((String) params.get("productIds")).split(",");
		for (String productId: productIds) {
			products.add(new Product(delegator, dispatcher, productId, request));
		}
	} catch(Exception e) {
		//
	}
}

context.products = products;
context.FEATURES_TO_SHOW = FEATURES_TO_SHOW;