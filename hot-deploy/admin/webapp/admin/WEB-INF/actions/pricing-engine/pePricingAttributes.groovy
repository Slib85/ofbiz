import com.bigname.pricingengine.PricingEngineHelper;
import com.bigname.pricingengine.util.*;


String attributeId = request.getParameter("attributeId");
if(attributeId == null) {
    context.pageType = "Attributes";
    context.items = PricingEngineHelper.getPricingAttributes();
    context.breadcrumbs = BreadcrumbUtil.buildBreadcrumbs(BreadcrumbUtil.pricingEngineBreadcrumbBase, "Pricing Attributes", "").entrySet();
    context.title = "Pricing Engine - Pricing Attributes";
} else {
    context.attributeId = attributeId;
    context.pageType = "AttributeDetails";
    context.attribute = PricingEngineHelper.getPricingAttribute(attributeId);
    context.breadcrumbs = BreadcrumbUtil.buildBreadcrumbs(BreadcrumbUtil.pricingEngineBreadcrumbBase, "Pricing Attributes", "/admin/control/pePricingAttributes", attributeId, "").entrySet();
    context.title = "Pricing Engine - Pricing Attribute";
}
