String module = "account.groovy"

import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.webapp.website.WebSiteWorker;

context.signedInUser = request.getSession().getAttribute("userLogin") != null;
if(request.getSession().getAttribute("userLogin") != null) {
    context.emailAddress = userLogin.get("userLoginId");
    loyaltyDataIn = [partyId: userLogin.getString("partyId"), roleTypeId: "PLACING_CUSTOMER", orderTypeId: "SALES_ORDER", statusId: "ORDER_SHIPPED", "webSiteId" : WebSiteWorker.getWebSiteId(request), monthsToInclude: 12, userLogin: userLogin];
    loyaltyResult = dispatcher.runSync("getOrderedSummaryInformation", loyaltyDataIn);
    context.loyaltyPoints = loyaltyResult.totalSubRemainingAmount;
    context.discountRate = loyaltyResult.discountRate;

    tradeDataIn = [partyId: userLogin.getString("partyId")];
    tradeResult = dispatcher.runSync("getAEPartyRoles", tradeDataIn);
    if (UtilValidate.isNotEmpty(tradeResult) && UtilValidate.isNotEmpty(tradeResult.get("isTrade"))) {
        context.isTrade = ((Boolean) tradeResult.get("isTrade")).booleanValue();
    }
} else if(UtilValidate.isNotEmpty(request.getParameter("utm_source"))) {
    context.externalReferrer = 'email';
}