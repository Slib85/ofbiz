import com.bigname.quote.calculator.*;
import java.util.*;

context.items = CalculatorHelper.getAllStyleGroups(delegator);
context.itemsCount = context.items.size
String styleGroupId = request.getParameter("styleGroupId");
if(styleGroupId != null) {
    Map<String, Object> styleGroup = CalculatorHelper.getStyleGroup(delegator, styleGroupId);
    if(styleGroup != null) {
        context.pageType = "PricingAttributes";
        context.styleGroupId = styleGroupId;
        context.styleGroup = styleGroup;
        context.pricingAttributes = CalculatorHelper.getAllStyleGroupPricingAttributes(delegator, styleGroupId);
    }
}