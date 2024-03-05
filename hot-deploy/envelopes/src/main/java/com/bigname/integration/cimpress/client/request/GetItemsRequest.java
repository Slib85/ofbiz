package com.bigname.integration.cimpress.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequest;
import com.bigname.core.restful.client.request.ApiEndpoint;
import com.bigname.integration.cimpress.CimpressHelper;

import java.util.HashMap;
import java.util.Map;
import static com.bigname.integration.cimpress.ConfigKey.*;

public class GetItemsRequest extends AbstractApiRequest {
    private Map<String, String> config = CimpressHelper.getCimpressConfig();
    @Override
    public ApiEndpoint getEndpoint() {
        return CimpressEndpoint.ITEMS;
    }

    @Override
    public Map<String, String> getQueryParams() {
        Map<String, String> params = new HashMap<>();
        params.put("fulfillerId", config.get(FULFILLER_ID.key()));
        params.put("status", "new");
        return params;
    }
}
