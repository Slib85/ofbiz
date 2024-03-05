import java.util.*;
import java.sql.Timestamp;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;

import com.envelopes.order.OrderHelper;
import com.envelopes.util.*;

String module = "workOrderAdmin.groovy";

context.put("workOrderEmployeeList", OrderHelper.getWorkOrderEmployeeList(delegator, true));