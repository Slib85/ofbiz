package com.bigname.integration.cimpress.client;

import com.bigname.integration.cimpress.client.request.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface CimpressApi {
    Map<String, Object> getItems();
    Map<String, Object> getNotifications(GetNotificationsRequest request);
    Map<String, Object> getOrderDetails(GetOrderDetailsRequest request);
    Map<String, Object> getItemDetails(GetItemDetailsRequest request);
    Boolean acceptNotification(String link);
    Map<String, Object> getItemStatus(GetItemStatusRequest request);
    Map<String, Object> getItemPlan(GetItemPlanRequest request);
    Map<String, String> getArtWork(GetArtworkRequest request);
    Map<String, String> getPreview(GetPreviewRequest request);
    Map<String, Object> getOrder(GetOrderRequest request);
    Map<String, Object> getOrderItems(GetOrderItemsRequest request);
    boolean updateItemStatus(UpdateItemStatusRequest request);
    boolean createShipment(CreateShipmentRequest request);
}
