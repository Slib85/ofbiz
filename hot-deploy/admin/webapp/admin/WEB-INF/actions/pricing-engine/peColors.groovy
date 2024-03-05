import com.bigname.pricingengine.PricingEngineHelper;
import com.bigname.pricingengine.util.*;
import org.apache.ofbiz.base.util.*;

context.pageType = "Colors";
context.items = PricingEngineHelper.getColors();
context.breadcrumbs = BreadcrumbUtil.buildBreadcrumbs(BreadcrumbUtil.pricingEngineBreadcrumbBase, "Colors", "").entrySet();
context.title = "Pricing Engine - Colors & Stocks";
context.pricingAttributes = PricingEngineHelper.getPricingAttributes();
context.vendors = PricingEngineHelper.getVendors();