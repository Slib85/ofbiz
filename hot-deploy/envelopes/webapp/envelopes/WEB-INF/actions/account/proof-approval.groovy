import java.util.*;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.GenericValue;

import com.envelopes.order.OrderHelper;

String module ="proof-approval.groovy";

List<GenericValue> orderItemsForUser = OrderHelper.orderItemsForUser(delegator, request.getSession().getAttribute("userLogin"), "ART_READY_FOR_REVIEW");
context.orderItemsForUser = orderItemsForUser;