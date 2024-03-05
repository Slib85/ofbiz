import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.base.util.UtilValidate;

import com.envelopes.plating.PlateHelper;

String module = "plateSchedule.groovy";

Delegator delegator = (Delegator) request.getAttribute("delegator");
GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

String printPressId = null;
if(UtilValidate.isNotEmpty(request.getParameter("printPressId"))) {
	printPressId = request.getParameter("printPressId")
}

context.plates = PlateHelper.getOpenPlates(delegator, userLogin, printPressId, (UtilValidate.isNotEmpty(request.getParameter("orderId"))) ? request.getParameter("orderId") : null, (UtilValidate.isNotEmpty(request.getParameter("orderItemSeqId"))) ? request.getParameter("orderItemSeqId") : null);
context.completedPlates = PlateHelper.getPrintedJobs(delegator, userLogin, printPressId, -120);

//hold the contents of the cumulative stock
Map<String, Map> stockMap = new HashMap<>();
Map<String, BigDecimal> stock = new HashMap<>();

//hold the contents of what orders are in the stock
Map<String, Map<String>> stockOrders = new HashMap<>();
Map<String, String> orders = new HashMap<>();

//hold the contents of what orders have been marked picked
Map<String, Map<String>> stockPickedOrders = new HashMap<>();
Map<String, String> pickedOrders = new HashMap<>();

Map<String, String> stockName = new HashMap<>();
String date = "";
for(List plate : context.plates) {
	for(Map orderItem : plate) {
		stockName.put((String) orderItem.get("sku"), (String) orderItem.get("item"));
		String plateDueDate = (String) orderItem.get("dueDate");

		if(!date.equals("") && !plateDueDate.equals(date)) {
			stockMap.put(date, stock);
			stock = new HashMap<>();

			stockOrders.put(date, orders);
			orders = new HashMap<>();

			stockPickedOrders.put(date, pickedOrders);
			pickedOrders = new HashMap<>();
		}

		if(stock.containsKey((String) orderItem.get("sku"))) {
			stock.put((String) orderItem.get("sku"), ((BigDecimal) stock.get((String) orderItem.get("sku"))).add((BigDecimal) orderItem.get("quantity")));
			orders.put((String) orderItem.get("sku"), orders.get(orderItem.get("sku")) + "," + (String) orderItem.get("orderNumber") + "_" + (String) orderItem.get("orderItemSeqId"));
		} else {
			stock.put((String) orderItem.get("sku"), (BigDecimal) orderItem.get("quantity"));
			orders.put((String) orderItem.get("sku"), (String) orderItem.get("orderNumber") + "_" + (String) orderItem.get("orderItemSeqId"));
		}

		if("Y".equalsIgnoreCase((String) orderItem.get("stockPicked"))) {
			if(pickedOrders.containsKey((String) orderItem.get("sku"))) {
				pickedOrders.put((String) orderItem.get("sku"), pickedOrders.get(orderItem.get("sku")) + "," + (String) orderItem.get("orderNumber") + "_" + (String) orderItem.get("orderItemSeqId"));
			} else {
				pickedOrders.put((String) orderItem.get("sku"), (String) orderItem.get("orderNumber") + "_" + (String) orderItem.get("orderItemSeqId"));
			}
		}

		date = plateDueDate;
	}

	if(UtilValidate.isNotEmpty(stock)) {
		stockMap.put(date, stock);
	}
	if(UtilValidate.isNotEmpty(orders)) {
		stockOrders.put(date, orders);
	}
	if(UtilValidate.isNotEmpty(pickedOrders)) {
		stockPickedOrders.put(date, pickedOrders);
	}
}

context.stockMap = stockMap;
context.stockName = stockName;
context.stockOrders = stockOrders;
context.stockPickedOrders = stockPickedOrders;