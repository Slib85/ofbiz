package com.bigname.integration.cimpress.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequest;
import com.bigname.core.restful.client.request.ApiEndpoint;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Manu on 6/16/2018.
 */
public class GetOrderItemsRequest extends AbstractApiRequest {
    private String orderId;
    private String baseEndpoint;

    public GetOrderItemsRequest(String orderId, String baseEndpoint) {
        this.orderId = orderId;
        this.baseEndpoint = baseEndpoint;
    }

    @Override
    public ApiEndpoint getEndpoint() {
        return CimpressEndpoint.ORDER_ITEMS;
    }

    @Override
    public String getBaseEndpoint() {
        return baseEndpoint;
    }

    @Override
    public Map<String, String> getRequestTemplates() {
        Map<String, String> requestTemplate = new LinkedHashMap<>();
        requestTemplate.put("orderId", orderId);
        return requestTemplate;
    }
}
