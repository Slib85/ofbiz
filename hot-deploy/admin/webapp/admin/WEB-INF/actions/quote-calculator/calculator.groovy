import com.bigname.quote.calculator.*;

import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.*;
import org.apache.ofbiz.entity.util.EntityQuery;

PricingCalculator pricingCalculator = PricingCalculator.getInstance(delegator);

context.styles = CalculatorHelper.getStyles(delegator, pricingCalculator.getPricingMatrix().getGroupIds());
context.styleGroups = CalculatorHelper.getStyleGroups(delegator, pricingCalculator.getPricingMatrix().getGroupIds());
context.stocks = CalculatorHelper.getStocks(delegator, CalculatorHelper.getAllStockTypeIds(delegator));
context.stockTypes = CalculatorHelper.getStockTypes(delegator, CalculatorHelper.getAllStockTypeIds(delegator));
context.materialTypes = CalculatorHelper.getAllMaterialTypes(delegator);

if(UtilValidate.isNotEmpty(request.getParameter("quoteRequestId"))) {
    GenericValue quoteRequest = EntityQuery.use(delegator).from("CustomEnvelopeContact").where("customEnvelopeQuoteId", request.getParameter("quoteRequestId")).queryOne();
    context.quoteRequest = quoteRequest
    context.rules = UtilMisc.toMap("states", UtilMisc.toList(quoteRequest.getString("stateProvinceGeoId")));
}

if(UtilValidate.isNotEmpty(request.getParameter("quoteId"))) {
    GenericValue quote = CalculatorHelper.getQuote(delegator, UtilMisc.toMap("quoteId", request.getParameter("quoteId")));
    context.pricingRequest = quote.getString("pricingRequest");
    context.pricingResponse = quote.getString("pricingResponse");
    context.comment = quote.getString("comment");
    context.internalComment = quote.getString("internalComment");
    context.production = quote.getString("production");
    context.quoteId = request.getParameter("quoteId");
}