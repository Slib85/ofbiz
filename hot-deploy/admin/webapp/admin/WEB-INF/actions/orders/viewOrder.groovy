/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.order.order.OrderReadHelper;

import com.envelopes.util.*;
import com.envelopes.party.PartyHelper;
import com.envelopes.order.OrderHelper;
import com.envelopes.product.ProductHelper;

String module = "viewOrder.groovy";

String orderId = null;
if(UtilValidate.isNotEmpty(request.getParameter("orderId"))) {
	orderId = (String) request.getParameter("orderId");
} else if(UtilValidate.isNotEmpty((String) request.getAttribute("orderId"))) {
	orderId = (String) request.getAttribute("orderId");
}

Map<String, Object> orderData = new HashMap<String, Object>();
try {
	orderData = OrderHelper.getOrderData(delegator, orderId, true);
} catch (GenericEntityException e) {
	Debug.logError(e, "Error trying to get customer order data.", module);
}

context.orderReadHelper = new OrderReadHelper(orderData.get("orderHeader"));
context.order = orderData.get("orderHeader");
context.isRush = orderData.get("isRush");
context.status = orderData.get("status");
context.notes = orderData.get("notes");
context.shippingAddress = orderData.get("shippingAddress");
context.isBlindShipment = orderData.get("isBlindShipment");
context.shippingTelecom = orderData.get("shippingTelecom");
context.billingAddress = orderData.get("billingAddress");
context.billingTelecom = orderData.get("billingTelecom");
context.payments = orderData.get("payments");
context.items = orderData.get("items");
context.headerAdjustments = orderData.get("headerAdjustments");
context.adjustments = orderData.get("adjustments");
context.email = orderData.get("email");
context.person = orderData.get("person");
context.shipping = orderData.get("shipping");
context.shipGroup = orderData.get("shipGroup");
context.attrList = OrderHelper.getOrderItemAttributeList();
context.attrOptions = OrderHelper.getOrderItemAttributeOptions();
context.random = new Random().nextInt((4 - 1)  + 1) + 1;
context.outsourceable = orderData.get("outsourceable");
context.outsourced = orderData.get("outsourced");
context.pendingChange = orderData.get("pendingChange");
context.isSyracuseable = orderData.get("isSyracuseable");

try {
	context.externalUser = PartyHelper.getExternalUserLogin(delegator, context.person.getString("partyId"));
} catch (GenericEntityException e) {
	Debug.logError(e, "Error trying to get customer order data.", module);
}

serviceIn = [partyId : context.person.getString("partyId"), roleTypeId : "PLACING_CUSTOMER", orderTypeId : "SALES_ORDER", statusId : "ORDER_SHIPPED", "webSiteId" : EnvUtil.getWebsiteId(orderData.get("orderHeader").getString("salesChannelEnumId")), monthsToInclude : 12, userLogin : userLogin];
result = dispatcher.runSync("getOrderedSummaryInformation", serviceIn);
context.loyaltyPoints = result.totalSubRemainingAmount;

serviceIn = [partyId : context.person.getString("partyId"), userLogin : userLogin];
getAEPartyRoles = dispatcher.runSync("getAEPartyRoles", serviceIn);

boolean isTrade = (UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isTrade"))) ? ((Boolean) getAEPartyRoles.get("isTrade")).booleanValue() : false;
boolean isNonProfit = (UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isNonProfit"))) ? ((Boolean) getAEPartyRoles.get("isNonProfit")).booleanValue() : false;
boolean isNonTaxable = (UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isNonTaxable"))) ? ((Boolean) getAEPartyRoles.get("isNonTaxable")).booleanValue() : false;
boolean isTradePostNet = (UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isTradePostNet"))) ? ((Boolean) getAEPartyRoles.get("isTradePostNet")).booleanValue() : false;
boolean isTradeAllegra = (UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isTradeAllegra"))) ? ((Boolean) getAEPartyRoles.get("isTradeAllegra")).booleanValue() : false;
context.isWholesaler = isTrade || isTradePostNet || isTradeAllegra;
context.isNonProfit = isNonProfit;
context.isNonTaxable = isNonTaxable;

context.sortedProductAttributes = ProductHelper.getSortedProductAttributes(delegator, "CUSTOM-P");