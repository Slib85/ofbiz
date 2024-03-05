import com.envelopes.order.OrderHelper
import com.envelopes.util.EnvUtil
import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.*;
import javolution.util.*;
import org.apache.ofbiz.entity.condition.*;
import org.apache.ofbiz.entity.util.*;
import org.apache.ofbiz.entity.model.*
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.model.DynamicViewEntity;
import org.apache.ofbiz.entity.model.ModelKeyMap;
import org.apache.ofbiz.entity.util.EntityListIterator;

import java.sql.Timestamp;

String module = "reAuth.groovy";

String goBack = request.getParameter("goBack");
if(goBack == null) {
	goBack = "0";
}

Calendar cal = Calendar.getInstance();
int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

Timestamp getVisaDaysPriorStart = null;
Timestamp getVisaDaysPriorEnd = EnvUtil.getNDaysBeforeOrAfterNow(7, false);
if(dayOfWeek == 2) {
	getVisaDaysPriorStart = EnvUtil.getNDaysBeforeOrAfterNow(-(9+(Integer.parseInt(goBack))), true);
} else {
	getVisaDaysPriorStart = EnvUtil.getNDaysBeforeOrAfterNow(-(7+(Integer.parseInt(goBack))), true);
}

List orderList = new ArrayList();
List orderIdList = new ArrayList();

DynamicViewEntity dve = new DynamicViewEntity();
EntityFindOptions efo = new EntityFindOptions();

dve.addMemberEntity("OH", "OrderHeader");
dve.addMemberEntity("OS", "OrderStatus");
dve.addMemberEntity("OPP", "OrderPaymentPreference");
dve.addMemberEntity("CC", "CreditCard");

dve.addAlias("OH", "orderId", "orderId", null, null, null, null);
dve.addAlias("OH", "orderDate", "orderDate", null, null, null, null);
dve.addAlias("OH", "statusId", "statusId", null, null, null, null);
dve.addAlias("OS", "osStatusId", "statusId", null, null, null, null);
dve.addAlias("OS", "statusDatetime", "statusDatetime", null, null, null, null);
dve.addAlias("CC", "cardType", "cardType", null, null, null, null);
dve.addAlias("CC", "createdStamp", "createdStamp", null, null, null, null);

dve.addViewLink("OH", "OS", Boolean.FALSE, ModelKeyMap.makeKeyMapList("orderId"));
dve.addViewLink("OH", "OPP", Boolean.FALSE, ModelKeyMap.makeKeyMapList("orderId"));
dve.addViewLink("OPP", "CC", Boolean.FALSE, ModelKeyMap.makeKeyMapList("paymentMethodId"));

List<String> fieldsToSelect = UtilMisc.toList("orderId", "orderDate", "statusDatetime", "statusId", "cardType", "createdStamp");
fieldsToSelect.add("osStatusId");

List<String> orderByList = UtilMisc.toList("statusDatetime DESC");

List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
conditionList.add(EntityCondition.makeCondition("createdStamp", EntityOperator.NOT_EQUAL, null));
conditionList.add(EntityCondition.makeCondition("cardType", EntityOperator.EQUALS, "CCT_VISA"));
conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "ORDER_CREATED"));
conditionList.add(EntityCondition.makeCondition("osStatusId", EntityOperator.EQUALS, "ORDER_CREATED"));
conditionList.add(EntityCondition.makeCondition("statusDatetime", EntityOperator.GREATER_THAN, getVisaDaysPriorStart));
conditionList.add(EntityCondition.makeCondition("statusDatetime", EntityOperator.LESS_THAN, getVisaDaysPriorEnd));

EntityListIterator eli = null;
try {
	eli = delegator.findListIteratorByCondition(dve, EntityCondition.makeCondition(conditionList, EntityOperator.AND), null, fieldsToSelect, orderByList, efo);
	GenericValue orderData = null;
	while(((orderData = (GenericValue) eli.next()) != null)) {
		String orderId = orderData.getString("orderId");

		//now lets check if the order is all plain
		List<GenericValue> orderItemList = delegator.findByAnd("OrderItem", UtilMisc.toMap("orderId", orderId), null, false);
		boolean isOrderPrinted = OrderHelper.isOrderPrinted(orderItemList);

		Map<String, Object> orderInfo = new HashMap<>();
		orderInfo.put("orderId", orderId);
		orderInfo.put("statusDatetime", orderData.getTimestamp("statusDatetime"));
		if(isOrderPrinted) {
			orderInfo.put("plainOrPrinted", "Printed");
		} else {
			orderInfo.put("plainOrPrinted", "Plain");
		}
		if(!(orderIdList.contains(orderId))) {
			orderList.add(orderInfo);
			orderIdList.add(orderId);
		}
	}
} catch(GenericEntityException e) {
	Debug.logError(e, "Error retrieving order data from reAuth.groovy");
} finally {
	if(eli != null) {
		try {
			eli.close();
		} catch(GenericEntityException e) {
			Debug.logError("ERROR : Could not close entity list iterator while retrieving order data from reAuth.groovy. Message : " + e.getMessage(), "");
		}
	}
}

context.put("orderList", orderList);


Timestamp getVisaDaysPriorStart2 = null;
Timestamp getVisaDaysPriorEnd2 = EnvUtil.getNDaysBeforeOrAfterNow(-14, false);
if(dayOfWeek == 2) {
	getVisaDaysPriorStart2 = EnvUtil.getNDaysBeforeOrAfterNow(-(16+(Integer.parseInt(goBack))), true);
} else {
	getVisaDaysPriorStart2 = EnvUtil.getNDaysBeforeOrAfterNow(-(14+(Integer.parseInt(goBack))), true);
}

List orderList2 = new ArrayList();
List orderIdList2 = new ArrayList();

DynamicViewEntity dve2 = new DynamicViewEntity();
EntityFindOptions efo2 = new EntityFindOptions();

dve2.addMemberEntity("OH", "OrderHeader");
dve2.addMemberEntity("OS", "OrderStatus");
dve2.addMemberEntity("OPP", "OrderPaymentPreference");
dve2.addMemberEntity("CC", "CreditCard");

dve2.addAlias("OH", "orderId", "orderId", null, null, null, null);
dve2.addAlias("OH", "orderDate", "orderDate", null, null, null, null);
dve2.addAlias("OH", "statusId", "statusId", null, null, null, null);
dve2.addAlias("OS", "osStatusId", "statusId", null, null, null, null);
dve2.addAlias("OS", "statusDatetime", "statusDatetime", null, null, null, null);
dve2.addAlias("CC", "cardType", "cardType", null, null, null, null);
dve2.addAlias("CC", "createdStamp", "createdStamp", null, null, null, null);

dve2.addViewLink("OH", "OS", Boolean.FALSE, ModelKeyMap.makeKeyMapList("orderId"));
dve2.addViewLink("OH", "OPP", Boolean.FALSE, ModelKeyMap.makeKeyMapList("orderId"));
dve2.addViewLink("OPP", "CC", Boolean.FALSE, ModelKeyMap.makeKeyMapList("paymentMethodId"));

List<String> fieldsToSelect2 = UtilMisc.toList("orderId", "orderDate", "statusDatetime", "statusId", "cardType", "createdStamp");
fieldsToSelect2.add("osStatusId");

List<String> orderByList2 = UtilMisc.toList("statusDatetime DESC");

List<EntityCondition> conditionList2 = new ArrayList<EntityCondition>();
conditionList2.add(EntityCondition.makeCondition("createdStamp", EntityOperator.NOT_EQUAL, null));
conditionList2.add(EntityCondition.makeCondition("cardType", EntityOperator.EQUALS, "CCT_VISA"));
conditionList2.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "ORDER_CREATED"));
conditionList2.add(EntityCondition.makeCondition("osStatusId", EntityOperator.EQUALS, "ORDER_CREATED"));
conditionList2.add(EntityCondition.makeCondition("statusDatetime", EntityOperator.GREATER_THAN, getVisaDaysPriorStart2));
conditionList2.add(EntityCondition.makeCondition("statusDatetime", EntityOperator.LESS_THAN, getVisaDaysPriorEnd2));

EntityListIterator eli2 = null;
try {
	eli2 = delegator.findListIteratorByCondition(dve2, EntityCondition.makeCondition(conditionList2, EntityOperator.AND), null, fieldsToSelect2, orderByList2, efo2);
	GenericValue orderData = null;
	while(((orderData = (GenericValue) eli2.next()) != null)) {
		String orderId = orderData.getString("orderId");

		//now lets check if the order is all plain
		List<GenericValue> orderItemList = delegator.findByAnd("OrderItem", UtilMisc.toMap("orderId", orderId), null, false);
		boolean isOrderPrinted = OrderHelper.isOrderPrinted(orderItemList);

		Map<String, Object> orderInfo = new HashMap<>();
		orderInfo.put("orderId", orderId);
		orderInfo.put("statusDatetime", orderData.getTimestamp("statusDatetime"));
		if(isOrderPrinted) {
			orderInfo.put("plainOrPrinted", "Printed");
		} else {
			orderInfo.put("plainOrPrinted", "Plain");
		}
		if(!(orderIdList2.contains(orderId))) {
			orderList2.add(orderInfo);
			orderIdList2.add(orderId);
		}
	}
} catch(GenericEntityException e) {
	Debug.logError(e, "Error retrieving order data from reAuth.groovy");
} finally {
	if(eli2 != null) {
		try {
			eli2.close();
		} catch(GenericEntityException e) {
			Debug.logError("ERROR : Could not close entity list iterator while retrieving order data from reAuth.groovy. Message : " + e.getMessage(), "");
		}
	}
}

context.put("orderList2", orderList2);