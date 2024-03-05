import java.util.*;
import com.envelopes.order.*;
import com.envelopes.cart.*;

String module = "vertical-order-details.groovy";
if(request.getAttribute("orderInfo") != null) {
    context.orderInfo = request.getAttribute("orderInfo");
} else {
	request.setAttribute("saveResponse", true);
	CartEvents.getCart(request, response);
	context.orderInfo = request.getAttribute("savedResponse");
}
