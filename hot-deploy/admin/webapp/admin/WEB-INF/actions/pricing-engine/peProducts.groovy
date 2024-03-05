import com.bigname.pricingengine.PricingEngineHelper;
import com.bigname.pricingengine.util.*;
import org.apache.ofbiz.base.util.*;
String productId = request.getParameter("productId");
String vendorId = request.getParameter("vendorId");
if(productId == null) {
    context.pageType = "Products";
    context.items = PricingEngineHelper.getVendorProducts();
    context.breadcrumbs = BreadcrumbUtil.buildBreadcrumbs(BreadcrumbUtil.pricingEngineBreadcrumbBase, "Products", "").entrySet();
    context.title = "Pricing Engine - Products";
} else {
    context.productId = productId;
    context.vendorId = vendorId;
    context.pageType = "ProductDetails";
    context.product = PricingEngineHelper.getVendorProduct(productId, vendorId);
    if(!UtilValidate.isEmpty(BreadcrumbUtil.getReferrerParameter(request, "vendorId"))) {
        context.vendor = PricingEngineHelper.getVendor(vendorId);
        context.breadcrumbs = BreadcrumbUtil.buildBreadcrumbs(BreadcrumbUtil.pricingEngineBreadcrumbBase, "Vendors", "/admin/control/peVendors", context.vendor.get("vendorName"), "/admin/control/peVendors?vendorId=" + vendorId, "Products", "/admin/control/peVendors?vendorId=" + vendorId, productId, "").entrySet();
        context.title = "Pricing Engine - Product [" + context.vendor.get("vendorName") + "]";
    } else {
        context.breadcrumbs = BreadcrumbUtil.buildBreadcrumbs(BreadcrumbUtil.pricingEngineBreadcrumbBase, "Products", "/admin/control/peProducts", productId, "").entrySet();
        context.title = "Pricing Engine - Product";
    }
    context.pricingDetails = PricingEngineHelper.getPricingDetails(productId, vendorId);
    context.pricingAttributes = PricingEngineHelper.getPricingAttributes();
    context.vendors = PricingEngineHelper.getVendors();

    /*
    Merging different quantity breaks to  generate a consolidated one
    For eg: 250, 500, 1000 AND 250, 500, 750, 1000, 2000 will be consolidated to 250, 500, 750, 1000, 2000
     */
    TreeSet<Integer> generalQuantityBreak = new TreeSet<>();
    if (context.pricingDetails != null && context.pricingDetails.size() > 0) {
        for (Map<String, Object> pricingDetail : context.pricingDetails) {
            List<String> quantityBreakList = pricingDetail.get("quantityBreak");
            for (String quantity : quantityBreakList) {
                generalQuantityBreak.add(Integer.parseInt(quantity));
            }
        }
        for (Map<String, Object> pricingDetail : context.pricingDetails) {
            List<String> quantityBreakList = pricingDetail.get("quantityBreak");
            List<String> volumePriceList = pricingDetail.get("volumePrice");
            List<String> resultingQuantityBreak = new ArrayList<>(quantityBreakList);
            List<String> resultingVolumeBreak = Collections.synchronizedList(new ArrayList<>(volumePriceList));
            iterator = generalQuantityBreak.iterator();
            for (int i = 0; i < generalQuantityBreak.size(); ++i) {
                String generalQuantity = iterator.next().toString();
                if (!quantityBreakList.contains(generalQuantity)) {
                    if(i > 0 && i < resultingVolumeBreak.size()) {
                        resultingVolumeBreak.add(i, "");
                        resultingQuantityBreak.add(i, generalQuantity);
                    }else if(i < resultingVolumeBreak.size()){
                        resultingVolumeBreak.add(i, "");
                        resultingQuantityBreak.add(i, generalQuantity);
                    }else {
                        resultingVolumeBreak.add("");
                        resultingQuantityBreak.add(generalQuantity);
                    }
                }
            }
            pricingDetail.put("quantityBreak", resultingQuantityBreak);
            pricingDetail.put("volumePrice", resultingVolumeBreak);
        }
        context.generalQuantityBreak = generalQuantityBreak;
    }
}