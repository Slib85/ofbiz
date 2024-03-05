package com.bigname.integration.cimpress.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequest;
import com.bigname.core.restful.client.request.ApiEndpoint;
import com.bigname.core.restful.client.request.ApiRequest;
import org.apache.commons.collections.map.HashedMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateItemStatusRequest extends AbstractApiRequest{
    private Map<String, Object> params = new HashMap<>();
    public UpdateItemStatusRequest(List<Map<String, String>> items) {
        params.put("notificationType", "ProductionStarted");
        params.put("items", items);
    }

    public Map<String, Object> getParams() {
        return params;
    }

    @Override
    public ApiEndpoint getEndpoint() {
        return CimpressEndpoint.UPDATE_ITEM_STATUS;
    }
}

