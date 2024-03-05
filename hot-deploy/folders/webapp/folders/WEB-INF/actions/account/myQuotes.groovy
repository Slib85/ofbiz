import com.envelopes.quote.QuoteHelper;
import com.envelopes.party.PartyHelper;
String module = "myQuotes.groovy"

context.put("quoteList", QuoteHelper.getUserQuotes(delegator, request.getSession().getAttribute("userLogin")));
context.put("salesRepInfo", PartyHelper.getSalesRepInfo(delegator, request.getSession().getAttribute("userLogin").get("partyId")));