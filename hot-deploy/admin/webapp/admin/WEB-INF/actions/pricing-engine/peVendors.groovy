import com.bigname.pricingengine.PricingEngineHelper;
import com.bigname.pricingengine.util.*;

String vendorId = request.getParameter("vendorId");
if(vendorId == null) {
    context.pageType = "Vendors";
    context.items = PricingEngineHelper.getVendors();
    context.breadcrumbs = BreadcrumbUtil.buildBreadcrumbs(BreadcrumbUtil.pricingEngineBreadcrumbBase, "Vendors", "").entrySet();
    context.title = "Pricing Engine - Vendors";
} else {
    context.vendorId = vendorId;
    context.pageType = "VendorDetails";
    context.vendor = PricingEngineHelper.getVendor(vendorId);
    context.vendorProducts = PricingEngineHelper.getVendorProducts(vendorId);
    context.breadcrumbs = BreadcrumbUtil.buildBreadcrumbs(BreadcrumbUtil.pricingEngineBreadcrumbBase, "Vendors", "/admin/control/peVendors", vendorId, "").entrySet();
    context.title = "Pricing Engine - Vendors";
}