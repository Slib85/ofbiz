import com.bigname.integration.cimpress.CimpressHelper;
import java.util.*;

String module = "cimpressOrders.groovy";

Map<String, Object> orders                = CimpressHelper.getOpenCimpressOrders(delegator);
List<Map<String, Object>> openOrders      = (List<Map<String, Object>>)orders.get("openOrders");
List<Map<String, Object>> cancelledOrders = (List<Map<String, Object>>)orders.get("cancelledOrders");
List<Map<String, Object>> changedOrders   = (List<Map<String, Object>>)orders.get("changedOrders");
List<Map<String, Object>> pendingOrders   = (List<Map<String, Object>>)orders.get("pendingOrders");



context.put("openOrders", openOrders);
context.put("cancelledOrders", cancelledOrders);
context.put("changedOrders", changedOrders);
context.put("pendingOrders", pendingOrders);