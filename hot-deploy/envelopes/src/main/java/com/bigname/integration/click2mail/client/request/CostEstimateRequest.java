package com.bigname.integration.click2mail.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequest;
import com.bigname.core.restful.client.request.ApiEndpoint;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Meenu on 16-04-2018.
 */
public class CostEstimateRequest extends AbstractApiRequest {

    Map<String, Object> params = new HashMap<>();
    public CostEstimateRequest(Map<String,  Object> params) {
        this.params = params;
    }

    @Override
    public ApiEndpoint getEndpoint() {
        return Click2MailEndpoint.COST_ESTIMATE;
    }

    @Override
    public Map<String, String> getQueryParams() {
        Map<String, String> formParams = super.getQueryParams();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            formParams.put(entry.getKey(), (String) entry.getValue());
        }
        return formParams;
    }
}
