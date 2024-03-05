package com.bigname.integration.cimpress.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequest;
import com.bigname.core.restful.client.request.ApiEndpoint;

import java.util.LinkedHashMap;
import java.util.Map;

public class GetOrderDetailsRequest extends AbstractApiRequest{
    private String orderId;
    public GetOrderDetailsRequest(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public ApiEndpoint getEndpoint() {
        return CimpressEndpoint.ORDER_DETAILS;
    }

    @Override
    public Map<String, String> getRequestTemplates() {
        Map<String, String> requestTemplate = new LinkedHashMap<>();
        requestTemplate.put("orderId", orderId);
        return requestTemplate;
    }

    public String getOrderId() {
        return orderId;
    }
}
