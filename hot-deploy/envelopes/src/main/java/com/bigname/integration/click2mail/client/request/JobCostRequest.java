package com.bigname.integration.click2mail.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequest;
import com.bigname.core.restful.client.request.ApiEndpoint;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JobCostRequest extends AbstractApiRequest {

    Map<String, Object> context = new HashMap<>();
    Map<String, Object> params = new HashMap<>();
    public JobCostRequest(Map<String, Object> context, Map<String, Object> params) {
        this.context = context;
        this.params = params;
    }

    @Override
    public ApiEndpoint getEndpoint() {
        return Click2MailEndpoint.JOB_COST;
    }

    @Override
    public Map<String, String> getQueryParams() {
        Map<String, String> formParams = super.getQueryParams();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            formParams.put(entry.getKey(), (String) entry.getValue());
        }
        return formParams;
    }

    @Override
    public Map<String, String> getRequestTemplates() {
        final Map<String, String> requestTemplate = new LinkedHashMap<>();
        context.forEach((k,v) -> requestTemplate.put(k,(String)v));
        return requestTemplate;
    }
}
