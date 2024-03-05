import com.bigname.pricingengine.dao.*;
import com.bigname.pricingengine.*;
import com.google.gson.Gson;

String vendorId = request.getParameter("vendorId");
if(vendorId == null ) {
    vendorId = "V_ADMORE";
}
context.vendorId = vendorId;
context.vendors = PricingEngineHelper.getVendors();
context.vendorProducts = PricingEngineDataManager.getVendorProducts(vendorId);
Map<String, Object>colorHierarchy = PricingEngineHelper.createColorHierarchy(vendorId);
context.colorJSON = new Gson().toJson(colorHierarchy);
context.colorHierarchy = colorHierarchy;
