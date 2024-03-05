import java.util.*;
import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import com.envelopes.quote.*;

String module = "customEnvelopeList.groovy";

GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

request.setAttribute("saveResponse", true);
QuoteEvents.getCustomEnvelopeList(request, response);
Map<String, Object> customEnvelopeList = (Map<String, Object>) request.getAttribute("savedResponse");
context.customEnvelopeList = customEnvelopeList.get("data");
context.foldersAssignedToUsers = QuoteHelper.foldersAssignedToUsers;

boolean calcStats = ("folders".equalsIgnoreCase(request.getParameter("webSiteId"))) ? true : false;
if(UtilValidate.isNotEmpty(request.getParameter("quoteIds"))) {
    calcStats = false;
}
if(("folders").equalsIgnoreCase((String) request.getParameter("webSiteId")) && calcStats) {
    context.createdQuotes = QuoteHelper.createdQuotes(delegator, "folders", userLogin);
    context.quoteQueue = QuoteHelper.quoteQueue(delegator, "folders", userLogin);
    context.quoteScorecard = QuoteHelper.quoteScorecard(delegator, "folders", userLogin);
}
context.calcStats = calcStats;