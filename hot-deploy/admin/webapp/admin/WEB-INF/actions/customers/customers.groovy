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

import java.util.HashMap;

import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.*;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.util.*;
import org.apache.ofbiz.entity.condition.*;

import com.envelopes.order.OrderHelper;
import com.envelopes.quote.QuoteHelper;

import com.envelopes.util.*;

String searchQuery = request.getParameter("id");
String searchAddress = request.getParameter("searchAddress");
boolean isPostalAddress = false;
if(UtilValidate.isNotEmpty(searchQuery)) {
    //search for the user
    List<GenericValue> customers = null;
    if(searchQuery.startsWith("ID:")) {
        customers = from("UserLogin").where("partyId", searchQuery.replace("ID:","")).orderBy("createdStamp DESC").maxRows(1).queryList();
    } else {
        if(UtilValidate.isEmpty(searchAddress)) {
            customers = from("UserLogin").where(EntityCondition.makeCondition("userLoginId", EntityOperator.LIKE, "%" + searchQuery + "%")).queryList();
        } else {
            List<EntityCondition> conditions = new ArrayList<>();
            conditions.add(EntityCondition.makeCondition("toName", EntityOperator.LIKE, "%" + searchQuery + "%"));
            conditions.add(EntityCondition.makeCondition("companyName", EntityOperator.LIKE, "%" + searchQuery + "%"));
            EntityListIterator eli = null;
            try {
                eli = delegator.find("PartyAndPostalAddress", EntityCondition.makeCondition(conditions, EntityOperator.OR), null, null, UtilMisc.toList("fromDate DESC"), new EntityFindOptions());
                GenericValue address = null;
                List<String> partyIds = new ArrayList<>();
                customers = new ArrayList<>();
                while ((address = eli.next()) != null) {
                    if(!partyIds.contains(address.getString("partyId"))) {
                        customers.add(address);
                    }
                    partyIds.add(address.getString("partyId"));
                }
                isPostalAddress = true;
            } catch(Exception e) {
                Debug.logError(e, "Error trying to search", module);
            } finally {
                if(eli != null) {
                    try {
                        eli.close();
                    } catch (GenericEntityException e2) {
                        Debug.logError(e2, "Error trying to search", module);
                    }
                }
            }
        }
    }

    if(UtilValidate.isNotEmpty(customers)) {
        if(customers.size() == 1 && !isPostalAddress) {
            GenericValue customer = customers.get(0);
            context.customer = customer;

            //get all the users for this party so we can pull all orders
            GenericValue person = from("Person").where("partyId", customer.getString("partyId")).queryOne();
            GenericValue party = from("Party").where("partyId", customer.getString("partyId")).queryOne();
            List<GenericValue> userLogins = from("UserLogin").where("partyId", customer.getString("partyId")).queryList();
            List<GenericValue> externalLogins = from("ExternalUserLogin").where("partyId", customer.getString("partyId")).queryList();
            List<GenericValue> orders = OrderHelper.getOrderHeadersForParty(delegator, customer.getString("partyId"));
            List<String> orderChannels = EntityUtil.getFieldListFromEntityList(orders, "salesChannelEnumId", true);
            List<GenericValue> quotes = from("CustomEnvelopeAndContact").where(EntityCondition.makeCondition("partyId", customer.getString("partyId"))).orderBy("createdDate DESC").queryList();
            List<GenericValue> loyaltyPoints = from("LoyaltyPoints").where(EntityCondition.makeCondition(UtilMisc.toList(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, customer.getString("partyId")), EntityCondition.makeCondition("createdStamp", EntityOperator.GREATER_THAN_EQUAL_TO, EnvUtil.getNMonthsBeforeOrAfterNow(-12, true))), EntityOperator.AND)).orderBy("createdStamp DESC").queryList();
            GenericValue getLastBillingAddress = EntityUtil.getFirst(EntityUtil.filterByDate(from("PartyContactDetailByPurpose").where("contactMechPurposeTypeId", "BILLING_LOCATION", "purposeThruDate", null, "partyId", customer.getString("partyId")).queryList()));

            //get total points
            Map allPoints = new HashMap();
            for(String orderChannel : orderChannels) {
                allPoints.put(EnvUtil.getWebsiteId(orderChannel), dispatcher.runSync("getOrderedSummaryInformation", [partyId : customer.getString("partyId"), roleTypeId : "PLACING_CUSTOMER", orderTypeId : "SALES_ORDER", statusId : "ORDER_SHIPPED", "webSiteId" : EnvUtil.getWebsiteId(orderChannel), monthsToInclude : 12, userLogin : EnvUtil.getSystemUser(delegator)]));
            }

            //get party roles
            Map roles = dispatcher.runSync("getAEPartyRoles", [partyId : customer.getString("partyId"), userLogin : customer]);
            boolean isTrade = (UtilValidate.isNotEmpty(roles) && UtilValidate.isNotEmpty(roles.get("isTrade"))) ? ((Boolean) roles.get("isTrade")).booleanValue() : false;
            boolean isNonProfit = (UtilValidate.isNotEmpty(roles) && UtilValidate.isNotEmpty(roles.get("isNonProfit"))) ? ((Boolean) roles.get("isNonProfit")).booleanValue() : false;
            boolean isNonTaxable = (UtilValidate.isNotEmpty(roles) && UtilValidate.isNotEmpty(roles.get("isNonTaxable"))) ? ((Boolean) roles.get("isNonTaxable")).booleanValue() : false;
            boolean isTradePostNet = (UtilValidate.isNotEmpty(roles) && UtilValidate.isNotEmpty(roles.get("isTradePostNet"))) ? ((Boolean) roles.get("isTradePostNet")).booleanValue() : false;
            boolean isTradeAllegra = (UtilValidate.isNotEmpty(roles) && UtilValidate.isNotEmpty(roles.get("isTradeAllegra"))) ? ((Boolean) roles.get("isTradeAllegra")).booleanValue() : false;
            boolean isNet30 = (UtilValidate.isNotEmpty(roles) && UtilValidate.isNotEmpty(roles.get("isNet30"))) ? ((Boolean) roles.get("isNet30")).booleanValue() : false;

            context.person = person;
            context.party = party;
            context.userLogins = userLogins;
            context.externalLogins = externalLogins;
            context.orders = orders;
            context.quotes = quotes;
            context.loyaltyPoints = loyaltyPoints;
            context.allPoints = allPoints;
            context.isWholesaler = isTrade;
            context.isTradeAllegra = isTradeAllegra;
            context.isTradePostNet = isTradePostNet;
            context.isNonProfit = isNonProfit;
            context.isNonTaxable = isNonTaxable;
            context.isNet30 = isNet30;
            context.foldersAssignedToUsers = QuoteHelper.foldersAssignedToUsers;
            context.lastBillingAddress = getLastBillingAddress;
        } else {
            context.customers = customers;
        }
    }
} else {
    List<GenericValue> customers = from("UserLogin").where(EntityCondition.makeCondition("createdStamp", EntityOperator.GREATER_THAN_EQUAL_TO, EnvUtil.getNDaysBeforeOrAfterNow(-1, true))).queryList();
    context.customers = customers;
}
context.isPostalAddress = isPostalAddress;