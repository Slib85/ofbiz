package com.bigname.integration.cimpress.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequest;
import com.bigname.core.restful.client.request.ApiEndpoint;

import java.util.HashMap;
import java.util.Map;

public class GetOrderRequest extends AbstractApiRequest {
    private String merchantId;
    private String merchantOrderId;
    private String baseEndpoint;

    public GetOrderRequest(String merchantId, String merchantOrderId, String baseEndpoint) {
        this.merchantId = merchantId;
        this.merchantOrderId = merchantOrderId;
        this.baseEndpoint = baseEndpoint;
    }

    @Override
    public ApiEndpoint getEndpoint() {
        return CimpressEndpoint.ORDERS;
    }

    @Override
    public String getBaseEndpoint() {
        return baseEndpoint;
    }

    @Override
    public Map<String, String> getQueryParams() {
        Map<String, String> params = new HashMap<>();
        params.put("merchantId", merchantId);
        params.put("merchantOrderId", merchantOrderId);
        return params;
    }
}
