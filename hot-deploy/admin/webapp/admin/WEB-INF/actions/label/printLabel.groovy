import org.apache.ofbiz.entity.*;
import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.base.util.UtilValidate;
import com.envelopes.label.*;

String productIdWithQty = request.getParameter("id");
if(UtilValidate.isNotEmpty(productIdWithQty)) {
    String productId = LabelPrintHelper.checkProductId(delegator, productIdWithQty.trim());
    if(UtilValidate.isNotEmpty(productId)) {
        if(LabelPrintHelper.getPackQty(productId, productIdWithQty) == -1) {
            context.productIdWithQty = productIdWithQty;
            context.printLabelError = "Invalid Quantity, please use a valid Quantity";
        } else {
            context.productIdWithQty = productIdWithQty;
            context.productId = productId;
            context.labelURL = "uploads/productLabels/" + LabelPrintHelper.getLabelData2(delegator, productIdWithQty, false, false).get(0).get("labelPath");
        }
    } else {
        context.productIdWithQty = productIdWithQty;
        context.printLabelError = "Invalid SKU, please use a valid SKU";
    }
} else {
    context.printLabelError = "Invalid SKU, please use a valid SKU";
}