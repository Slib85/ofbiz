package com.bigname.integration.cimpress.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequest;
import com.bigname.core.restful.client.request.ApiEndpoint;
import com.bigname.integration.cimpress.CimpressHelper;
import org.apache.ofbiz.base.util.UtilValidate;

import java.util.HashMap;
import java.util.Map;
import static com.bigname.integration.cimpress.ConfigKey.*;

public class GetNotificationsRequest extends AbstractApiRequest {
    private String type;
    private Map<String, String> config = CimpressHelper.getCimpressConfig();
    public GetNotificationsRequest(String type) {
        this.type = type;
    }
    @Override
    public ApiEndpoint getEndpoint() {
        return  CimpressEndpoint.NOTIFICATIONS;
    }

    @Override
    public Map<String, String> getQueryParams() {
        Map<String, String> params = new HashMap<>();
        params.put("fulfillerId", config.get(FULFILLER_ID.key()));
        params.put("status", "new");
        params.put("type", type);
        params.put("limit", UtilValidate.isEmpty(config.get(NOTIFICATION_FETCH_SIZE.key())) ? "10" : config.get(NOTIFICATION_FETCH_SIZE.key()));
        return params;
    }
}
