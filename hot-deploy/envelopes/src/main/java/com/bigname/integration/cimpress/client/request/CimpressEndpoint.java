package com.bigname.integration.cimpress.client.request;

import com.bigname.core.restful.client.request.ApiEndpoint;

public enum CimpressEndpoint implements ApiEndpoint {
    ITEMS("items") ,
    NOTIFICATIONS("notifications"),
    ORDERS("orders"),
    ORDER_ITEMS("orders", "{orderId}", "items"),
    ORDER_DETAILS("orders", "{orderId}"),
    ITEM_DETAILS("items", "{itemId}"),
    ITEM_STATUS("items", "{itemId}", "status"),
    ITEM_PLAN("items", "{itemId}", "plan"),
    ARTWORK("items", "{itemId}", "artwork"),
    PREVIEW("items", "{itemId}", "preview"),
    UPDATE_ITEM_STATUS("notifications"),
    CREATE_SHIPMENT("shipments");
    private final String[] paths;

    CimpressEndpoint(String... paths) {
        this.paths = paths;
    }
    @Override
    public String[] getPaths() {
        return paths;
    }
}
