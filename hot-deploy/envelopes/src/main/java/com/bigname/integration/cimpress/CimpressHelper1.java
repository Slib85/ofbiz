package com.bigname.integration.cimpress;

import com.bigname.core.restful.client.domain.JWT;
import com.google.gson.Gson;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.DelegatorFactory;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @deprecated
 */
public class CimpressHelper1 {

    /*public static Map<String, Map<String, Object>> parseItemNotifications(Map<String, Object> itemNotificationsResponse, Delegator delegator) {

        Map<String, Map<String, Object>> notifications = new HashMap<>();
        List<Map<String, Object>> listOfNotifications = (List<Map<String, Object>>) itemNotificationsResponse.get("notifications");

        for (Map<String, Object> notification : listOfNotifications){

            Map<String, Object> notificationMap = new HashMap<>();

            String notificationId = (String) notification.get("notificationId");
            Map<String, Object> order = (Map<String, Object>) notification.get("order");
            String orderId = (String) order.get("orderId");
           // if (orderId.equals("FZE1HA507A")) {
            List<Map<String, Object>> listOfItems = (List<Map<String, Object>>) notification.get("items");
            Map<String, Object> orderDetails = getOrderItemDetails(orderId, listOfItems, delegator);

            notificationMap.put("notificationId", notificationId);
            notificationMap.put("orderId", orderId);
            notificationMap.put("orderDetails", orderDetails);
            if (UtilValidate.isNotEmpty(orderDetails)) {
                Map<String, Object> notificationLinks = (Map<String, Object>) notification.get("links");
                Map<String, Object> accept = (Map<String, Object>) notificationLinks.get("accept");
                String acceptLink = (String) accept.get("href");
                notificationMap.put("acceptNotificationLink", acceptLink);
                if (notificationId.equals("028432a8-79ee-46e0-93de-26264f93752f")) {
                    CimpressEvents.acceptNotification(acceptLink);
                }
            }
            notifications.put(notificationId, notificationMap);
            //}
        }
        System.out.println("final notifications :" + notifications);
        return notifications;
    }

    public static Map<String, Object> getOrderItemDetails(String orderId, List<Map<String, Object>> listOfItems, Delegator delegator) {

        Map<String, Object> orderMap = new HashMap<>();
        try {

            Map<String, Object> orderDetails = CimpressEvents.getOrderDetails(orderId);
            orderMap.put("orderId", orderId);
            orderMap.put("createdDate", orderDetails.get("createdDate"));
            orderMap.put("promisedArrivalDate", orderDetails.get("promisedArrivalDate"));
            orderMap.put("destinationAddress", orderDetails.get("destinationAddress"));
            Map<String, Map<String, Object>> items = new HashMap<>();
            for (Map<String, Object> item : listOfItems) {
                Map<String, Object> itemMap = new HashMap<>();
                String itemId = (String) item.get("itemId");
                Map<String, Object> itemDetails = CimpressEvents.getItemDetails(itemId);

                if (UtilValidate.isNotEmpty(itemDetails)) {
                    Map<String, Object> plan = CimpressEvents.getItemPlan(itemId);
                    if (UtilValidate.isNotEmpty(plan)) {
                        Map<String, Object> production = (Map<String, Object>) plan.get("production");
                        Map<String, Object> shipping = (Map<String, Object>) plan.get("shipping");
                        Map<String, Object> carrierService = (Map<String, Object>) shipping.get("expectedCarrierService");
                        itemMap.put("expectedProductionStartTime", production.get("expectedProductionStartTime"));
                        itemMap.put("expectedShipTime", shipping.get("expectedShipTime"));
                        itemMap.put("expectedCarrierService", carrierService.get("name"));
                    }
                    List<Map<String, Object>> deliveryDetails = (List<Map<String, Object>>) itemDetails.get("deliveryDetails");
                    itemMap.put("itemId", itemId);
                    itemMap.put("product", itemDetails.get("product"));
                    itemMap.put("orderedQuantity", itemDetails.get("orderedQuantity"));
                    for (Map<String, Object> map : deliveryDetails) {
                        if (map.containsKey("type")) {
                            itemMap.put("deliveryType", map.get("type"));
                            break;
                        }
                    }
                }
                items.put(itemId, itemMap);
            }
            orderMap.put("items", items);
            if (saveOrderItemDetails(orderMap, delegator)){
                return orderMap;
            }
            return new HashMap<>();
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    public static boolean saveOrderItemDetails(Map<String, Object> orderDetails,Delegator delegator) throws GenericEntityException, ParseException {
        boolean success = false;
        String orderId = (String) orderDetails.get("orderId");
        Map<String, Object> destinationAddress = (Map<String, Object>) orderDetails.get("destinationAddress");

        //Delegator delegator = DelegatorFactory.getDelegator("default");
        GenericValue order = EntityQuery.use(delegator).from("CimpressOrder").where(UtilMisc.toMap("orderId", orderId)).queryOne();
        if (UtilValidate.isEmpty(order)) {
            order = delegator.makeValue("CimpressOrder", "orderId", orderId);
        }
        SimpleDateFormat utcFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        utcFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date createdDate = utcFormatter.parse((String) orderDetails.get("createdDate"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date promisedArrivalDate = formatter.parse((String) orderDetails.get("promisedArrivalDate"));

        order.set("createdDate", new Timestamp(createdDate.getTime()));
        order.set("promisedArrivalDate", new Timestamp(promisedArrivalDate.getTime()));
        order.set("destinationAddress", new Gson().toJson(destinationAddress));
        delegator.createOrStore(order);

        Map<String, Map<String, Object>> items = new HashMap<>();
        if (UtilValidate.isEmpty(orderDetails.get("items"))) {
            return false;
        } else {
            items = (Map<String, Map<String, Object>>) orderDetails.get("items");
            for (String key : items.keySet()) {
                Map<String, Object> item = items.get(key);
                try {
                    saveItemDetails(item, orderId, delegator);
                } catch (Exception e) {
                    return false;
                }
            }
        }
        success = true;
        return success;
    }

    public static boolean saveItemDetails(Map<String, Object> itemDetails, String orderId, Delegator delegator) throws GenericEntityException, ParseException {
        boolean success = false;
        String itemId = (String) itemDetails.get("itemId");
        Map<String, Object> product = (Map<String, Object>) itemDetails.get("product");
        List<Map<String, Object>> attributes = (List<Map<String, Object>>) product.get("attributes");
        GenericValue item = EntityQuery.use(delegator).from("CimpressOrderItem").where(UtilMisc.toMap("itemId", itemId)).queryOne();
        if (UtilValidate.isEmpty(item)) {
            item = delegator.makeValue("CimpressOrderItem", "itemId", itemId);
        }
        SimpleDateFormat utcFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        utcFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date expectedProductionStartTime = utcFormatter.parse((String) itemDetails.get("expectedProductionStartTime"));
        Date expectedShipTime = utcFormatter.parse((String) itemDetails.get("expectedShipTime"));

        item.set("orderId", orderId);
        item.set("orderedQuantity", itemDetails.get("orderedQuantity").toString());
        item.set("deliveryType", itemDetails.get("deliveryType"));
        item.set("productSku", product.get("sku"));
        item.set("productName", product.get("name"));
        item.set("productDescription", product.get("description"));
        item.set("bignameSku", attributes.get(0).get("value"));
        item.set("expectedProductionStartTime", new Timestamp(expectedProductionStartTime.getTime()));
        item.set("expectedShipTime", new Timestamp(expectedShipTime.getTime()));
        item.set("expectedCarrierService", itemDetails.get("expectedCarrierService"));
        item.set("status", "New");
        delegator.createOrStore(item);
        success = true;
        return success;
    }

    public static boolean saveArtwork(Map<String, String> contentDetails) throws GenericEntityException {
        boolean success = false;
        Delegator delegator = DelegatorFactory.getDelegator("default");
        String itemId = (String) contentDetails.get("itemId");
        String orderId = (String) contentDetails.get("orderId");
        String artworkName = (String) contentDetails.get("fileName");
        String artworkPath = (String) contentDetails.get("filePath");

        GenericValue item = EntityQuery.use(delegator).from("CimpressArtwork").where(UtilMisc.toMap("itemId", itemId)).queryOne();
        if (UtilValidate.isEmpty(item)) {
            item = delegator.makeValue("CimpressArtwork", "itemId", itemId);
        }
        item.set("orderId", orderId);
        item.set("artworkName", artworkName);
        item.set("artworkPath", artworkPath);

        delegator.createOrStore(item);
        success = true;
        return success;
    }*/
}
