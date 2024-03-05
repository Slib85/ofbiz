String module = "uploadedFiles.groovy"
import com.envelopes.order.OrderHelper;

if(request.getSession().getAttribute("userLogin") != null) {
    context.orders = OrderHelper.getOrderItemContents(delegator, request.getSession().getAttribute("userLogin"));
}