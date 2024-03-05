import com.bigname.quote.calculator.*;
import java.util.*;

context.items = CalculatorHelper.getAllVendors(delegator);
context.itemsCount = context.items.size
String vendorId = request.getParameter("vendorId");
if(vendorId != null) {
    String styleGroupId = request.getParameter("groupId");
    Map<String, Object> vendor = CalculatorHelper.getVendor(delegator, vendorId);
    if(vendor != null) {
        context.pageType = "VendorStyleGroups";
        context.styleGroups = CalculatorHelper.getAllVendorStyleGroups(delegator, vendorId);
        context.vendorPricingDetails = CalculatorHelper.getVendorPricingDetails(delegator, vendorId);
        context.vendorId = vendorId;
        if(styleGroupId != null) {
            context.pageType = "VendorPricingDetails";
        }
    }
}