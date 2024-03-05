import java.util.*;

import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.entity.util.EntityQuery;

import com.envelopes.util.*;
import com.envelopes.quote.*;
import com.envelopes.party.PartyHelper;
import com.envelopes.product.ProductHelper;

String module = "viewCustomEnvelope.groovy";

request.setAttribute("saveResponse", true);
QuoteEvents.getCustomEnvelopeInfo(request, response);
Map<String, Object> customEnvelopeInfo = (Map<String, Object>) request.getAttribute("savedResponse");
context.put("customEnvelopeInfo", customEnvelopeInfo.get("data"));
context.put("foldersAssignedToUsers", QuoteHelper.foldersAssignedToUsers);

Map roles = dispatcher.runSync("getAEPartyRoles", [partyId : customEnvelopeInfo.get("data").getString("partyId"), userLogin : EnvUtil.getSystemUser(delegator)]);
boolean isTrade = (UtilValidate.isNotEmpty(roles) && UtilValidate.isNotEmpty(roles.get("isTrade"))) ? ((Boolean) roles.get("isTrade")).booleanValue() : false;
boolean isNonProfit = (UtilValidate.isNotEmpty(roles) && UtilValidate.isNotEmpty(roles.get("isNonProfit"))) ? ((Boolean) roles.get("isNonProfit")).booleanValue() : false;
boolean isNonTaxable = (UtilValidate.isNotEmpty(roles) && UtilValidate.isNotEmpty(roles.get("isNonTaxable"))) ? ((Boolean) roles.get("isNonTaxable")).booleanValue() : false;
boolean isTradePostNet = (UtilValidate.isNotEmpty(roles) && UtilValidate.isNotEmpty(roles.get("isTradePostNet"))) ? ((Boolean) roles.get("isTradePostNet")).booleanValue() : false;
boolean isTradeAllegra = (UtilValidate.isNotEmpty(roles) && UtilValidate.isNotEmpty(roles.get("isTradeAllegra"))) ? ((Boolean) roles.get("isTradeAllegra")).booleanValue() : false;
boolean isNet30 = (UtilValidate.isNotEmpty(roles) && UtilValidate.isNotEmpty(roles.get("isNet30"))) ? ((Boolean) roles.get("isNet30")).booleanValue() : false;

context.isWholesaler = isTrade;
context.isTradeAllegra = isTradeAllegra;
context.isTradePostNet = isTradePostNet;
context.isNonProfit = isNonProfit;
context.isNonTaxable = isNonTaxable;
context.isNet30 = isNet30;
context.partyGV = PartyHelper.getParty(delegator, customEnvelopeInfo.get("data").getString("partyId"));

if(UtilValidate.isNotEmpty(request.getParameter("quoteId"))) {
    context.put("quoteNoteDataList", QuoteHelper.getQuoteNoteDataList(delegator, request.getParameter("quoteId")));
    context.quotes = EntityUtil.filterByDate(EntityQuery.use(delegator).from("QcQuote").where("quoteRequestId", request.getParameter("quoteId")).queryList());
    context.put("quoteStatusList", EntityQuery.use(delegator).from("QuoteStatus").where("quoteId", request.getParameter("quoteId")).orderBy("statusDatetime DESC").queryList());
    if (UtilValidate.isNotEmpty(customEnvelopeInfo.get("data")) && UtilValidate.isNotEmpty(customEnvelopeInfo.get("data").get("productId"))) {
        context.put("product", ProductHelper.getProduct(delegator, customEnvelopeInfo.get("data").get("productId")));
    }
}