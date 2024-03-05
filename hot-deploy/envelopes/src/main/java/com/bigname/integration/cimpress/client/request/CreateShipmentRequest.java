package com.bigname.integration.cimpress.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequest;
import com.bigname.core.restful.client.request.ApiEndpoint;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateShipmentRequest extends AbstractApiRequest {

    private Map<String, Object> params = new HashMap<>();
    public CreateShipmentRequest(Map<String, Object> bodyParams) {
        this.params = bodyParams;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    @Override
    public ApiEndpoint getEndpoint() {
        return CimpressEndpoint.CREATE_SHIPMENT;
    }
}
