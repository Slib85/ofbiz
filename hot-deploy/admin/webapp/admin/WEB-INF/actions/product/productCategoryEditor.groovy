import org.apache.ofbiz.base.util.Debug;

import com.envelopes.product.ProductHelper;
import com.envelopes.util.*;

try {
    context.put("productCategoryList", ProductHelper.getProductCategories(delegator));
} catch (Exception e) {
    EnvUtil.reportError(e);
    Debug.logError("Error trying to add a new product category. " + e + " : " + e.getMessage(), module);
}