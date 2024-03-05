String module = "designs.groovy";
import com.envelopes.refinements.*;
request.setAttribute("mode", "direct");
request.setAttribute("requestType", RefinementsUtil.RequestType.DESIGN);
context.widgetResponse = RefinementsUtil.getRefinementsResponse(request);