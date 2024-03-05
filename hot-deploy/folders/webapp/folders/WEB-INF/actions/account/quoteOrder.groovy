import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.entity.GenericValue;

import com.bigname.quote.calculator.CalculatorHelper;
import com.envelopes.util.EnvUtil;

Map<String, Object> params = EnvUtil.getParameterMap(request);

String[] quotes = ((String) params.get("quoteIds")).split(",");

Map<String, Object> quoteData = new HashMap<>();
for(String quote : quotes) {
    quoteData.put(quote, CalculatorHelper.deserializedQuoteSummary(delegator, null, quote));
}

GenericValue quoteRequest = EntityQuery.use(delegator).from("CustomEnvelopeAndContactInfo").where("quoteId", (String) params.get("quoteRequestId")).queryFirst();;

context.quoteRequest = quoteRequest;
context.quotes = quoteData;