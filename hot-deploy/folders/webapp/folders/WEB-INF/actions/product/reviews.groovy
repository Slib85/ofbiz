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

String productId = UtilValidate.isNotEmpty((String) params.get("productId")) ? (String) params.get("productId") : (String) params.get("product_id");
String variantId = (String) params.get("variantId");
Product product = null;

if (UtilValidate.isNotEmpty(productId)) {
    Map<String, Object> foldersProduct = FoldersProduct.getProductData(productId);

    if (UtilValidate.isEmpty(foldersProduct)) {
        product = new Product(delegator, dispatcher, productId, request);
        variantId = productId;
        context.plain = "y";
        if (UtilValidate.isNotEmpty(product)) {
            productId = FoldersProduct.getProductDataByParentId(product.getParentId())
            foldersProduct = FoldersProduct.getProductData(productId);
        }
    }

    context.productId = productId;
    context.foldersProduct = foldersProduct;

    if(FoldersProduct.parentProduct.containsKey(productId)) {
        product = new Product(delegator, dispatcher, (UtilValidate.isNotEmpty(variantId) ? variantId : FoldersProduct.parentProduct.get(productId)), request);

        if(product.isValid()) {
            if (product.isVirtual()) {
                product.getFirstActiveChild();
            }
            context.product = product;
        }
    }
}

if (UtilValidate.isNotEmpty(product)) {
    if(UtilValidate.isNotEmpty(orderId = (String)params.get("orderId"))) {
        context.orderId = orderId;
    }

    context.productId = product.getId();
    context.productReviews = ProductEvents.getReviews(request, product.getId());
    context.productRating = (String)params.get("product_rating");
    context.product = product;
}