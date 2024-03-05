import java.math.BigDecimal;
import java.lang.*;

import org.apache.ofbiz.base.util.UtilValidate;

import com.envelopes.product.ProductHelper;
import com.envelopes.shipping.ShippingHelper;
import com.envelopes.util.EnvUtil;



String module = "shippingRates.groovy";

try {
    Map<String, Object> params = EnvUtil.getParameterMap(request);
    
    if (UtilValidate.isNotEmpty(params.get("productId")) && UtilValidate.isNotEmpty(params.get("productQuantity") && UtilValidate.isNotEmpty(params.get("zipCode")))) {
        productShippingWeightInfo = ProductHelper.getProductShippingWeightInfo(delegator, params.get("productId"));
        
        BigDecimal productQuantity = new BigDecimal(params.get("productQuantity"));
        BigDecimal cartonQty = Math.floor(productQuantity / productShippingWeightInfo.get("cartonQty"));

        totalShippingWeight = Math.ceil(productShippingWeightInfo.get("shippingWeight") * productQuantity / cartonQty);

        context.put("cartonQty", cartonQty);
        context.put("shippingMethods", ShippingHelper.getShippingMethods(delegator, params.get("zipCode"), totalShippingWeight));
    }
} catch (Exception e) {
    EnvUtil.reportError(e);
    Debug.logError("An error occured while trying to obtain shipping methods for (Zip Code: " + params.get("zipCode") + ") and (Product ID: " + params.get("productId") + ") and (Product Quantity: " + params.get("productQuantity") + ") ERROR: " + e.getMessage(), module);
}