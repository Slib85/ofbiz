package com.bigname.integration.cimpress;

import com.bigname.integration.cimpress.client.CimpressApi;
import com.bigname.integration.cimpress.client.CimpressApiClient;
import com.bigname.integration.cimpress.client.request.*;
import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;

import java.util.*;

public class CimpressUtil {

    public static final String module = CimpressUtil.class.getName();
    public static Map<String, Object> getItems() {
        return getCimpressClient().getItems();
    }
    static Map<String, Object> getNotifications(GetNotificationsRequest request) {
        return getCimpressClient().getNotifications(request);
    }
    static Map<String, Object> getOrderDetails(GetOrderDetailsRequest request) {
        Map<String, Object> orderDetails = getCimpressClient().getOrderDetails(request);
        Map<String, Object> merchantInformation;
        Map<String, Object> transferPrice = new HashMap<>();
        try {
            if(UtilValidate.isNotEmpty(orderDetails) && UtilValidate.isNotEmpty(merchantInformation = (Map<String, Object>)orderDetails.get("merchantInformation"))) {
                String orderManagerApiEndpoint = CimpressHelper.getCimpressConfig().get(ConfigKey.ORDER_MANAGER_API_ENDPOINT.key());
                String merchantId = (String) merchantInformation.get("id");
                String merchantOrderId = (String) merchantInformation.get("orderId");
                Map<String, Object> order = getOrder(new GetOrderRequest(merchantId, merchantOrderId, orderManagerApiEndpoint));
                String orderId = (String)((List<Map<String,Object>>)order.get("orders")).get(0).get("orderId");
                Map<String, Object> orderItems = getCimpressClient().getOrderItems(new GetOrderItemsRequest(orderId, orderManagerApiEndpoint));
                List<Map<String, Object>> items;
                if(UtilValidate.isNotEmpty(orderItems) && UtilValidate.isNotEmpty(items = (List<Map<String, Object>>)orderItems.get("items"))) {
                    items.forEach(e -> transferPrice.put((String)e.get("shortItemId"), e.get("transferPrice")));
                    orderDetails.put("transferPrice", transferPrice);
                }
            }
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError("Error while getting transfer price for cimpress order [ Order ID: " + request.getOrderId() + "] " + e, module);
        }
        return orderDetails;
    }
    static Map<String, Object> getItemDetails(GetItemDetailsRequest request) {
        return getCimpressClient().getItemDetails(request);
    }
    static Boolean acceptNotification(String link) {
        return getCimpressClient().acceptNotification(link);
    }

    static Map<String, Object> getOrder(GetOrderRequest request) {
        return getCimpressClient().getOrder(request);
    }

    @SuppressWarnings("unchecked")
    static Map<String, Object> getItemStatus(GetItemStatusRequest request) {
        Map<String, Object> statusDetails = new HashMap<>();
        Map<String, Object> response = getCimpressClient().getItemStatus(request);
        if (UtilValidate.isNotEmpty(response) && UtilValidate.isNotEmpty(response.get("statusDetails"))) {
            statusDetails = (Map<String, Object>) response.get("statusDetails");
        }
        return statusDetails;
    }

    static Map<String, Object> getItemPlan(GetItemPlanRequest request) {
        return getCimpressClient().getItemPlan(request);
    }

    static Map<String, String> getArtWork(GetArtworkRequest request) {
        return getCimpressClient().getArtWork(request);
    }

    static Map<String, String> getPreview(GetPreviewRequest request) {
        return getCimpressClient().getPreview(request);
    }

    static boolean setProductionStarted(UpdateItemStatusRequest request) {
        return getCimpressClient().updateItemStatus(request);
    }

    static boolean createShipment(CreateShipmentRequest request) {
        return getCimpressClient().createShipment(request);
    }

    private static CimpressApi getCimpressClient() {
        return new CimpressApiClient(CimpressHelper.getCimpressConfig());
    }
}
