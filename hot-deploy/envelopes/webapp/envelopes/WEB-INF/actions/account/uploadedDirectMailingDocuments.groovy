String module = "uploadedDirectMailingDocuments.groovy"
import com.envelopes.order.OrderHelper;
import com.bigname.integration.click2mail.Click2MailHelper;
import com.envelopes.party.PartyHelper;

if(request.getSession().getAttribute("userLogin") != null) {
    context.orders = OrderHelper.getOrderItemContents(delegator, request.getSession().getAttribute("userLogin"));
}
context.documents = Click2MailHelper.getDirectMailingDocuments(request, response);
context.designs = PartyHelper.getSavedDesigns(request, response);