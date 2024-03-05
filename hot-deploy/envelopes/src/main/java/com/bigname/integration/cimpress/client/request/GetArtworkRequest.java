package com.bigname.integration.cimpress.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequest;
import com.bigname.core.restful.client.request.ApiEndpoint;

import java.util.LinkedHashMap;
import java.util.Map;

public class GetArtworkRequest extends AbstractApiRequest {
    private String itemId;
    public GetArtworkRequest(String itemId) {
        this.itemId = itemId;
    }
    @Override
    public ApiEndpoint getEndpoint() {
        return CimpressEndpoint.ARTWORK;
    }

    public String getItemId() {
        return itemId;
    }

    @Override
    public Map<String, String> getRequestTemplates() {
        Map<String, String> requestTemplate = new LinkedHashMap<>();
        requestTemplate.put("itemId", itemId);
        return requestTemplate;
    }
}
