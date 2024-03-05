package com.bigname.integration.cimpress.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequest;
import com.bigname.core.restful.client.request.ApiEndpoint;

import java.util.LinkedHashMap;
import java.util.Map;

public class GetItemDetailsRequest extends AbstractApiRequest {
    String itemId;
    public GetItemDetailsRequest(String itemId) {
        this.itemId = itemId;
    }
    @Override
    public ApiEndpoint getEndpoint() {
        return CimpressEndpoint.ITEM_DETAILS;
    }

    @Override
    public Map<String, String> getRequestTemplates() {
        Map<String, String> requestTemplate = new LinkedHashMap<>();
        requestTemplate.put("itemId", itemId);
        return requestTemplate;
    }
}
