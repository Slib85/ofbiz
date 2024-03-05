import java.util.*;
import java.sql.Timestamp;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;

import com.envelopes.netsuite.NetsuiteHelper;
import com.envelopes.order.OrderHelper;
import com.envelopes.util.*;

String module = "workOrder.groovy";

String id = request.getParameter("id");
Map<String, Object> params = EnvUtil.getParameterMap(request);

if("Y".equalsIgnoreCase((String) params.get("update"))) {
    context.put("workOrder", NetsuiteHelper.getWorkOrder(delegator, dispatcher, params));
} else if(UtilValidate.isNotEmpty(id)) {
    context.put("workOrder", NetsuiteHelper.getWorkOrder(delegator, dispatcher, UtilMisc.toMap("id", id)));
} else {
    context.put("workOrder", UtilMisc.toMap("id", "_NA_"));
}

context.put("staff", OrderHelper.getWorkOrderEmployeeList(delegator, true));

Map<String, String> issues = new LinkedHashMap<>();
issues.put("1", "Backordered");
issues.put("2", "Offcuts");
issues.put("3", "Short Pulled");
context.put("issues", issues);