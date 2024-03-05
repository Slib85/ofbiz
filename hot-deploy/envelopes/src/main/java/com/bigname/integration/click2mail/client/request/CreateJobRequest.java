package com.bigname.integration.click2mail.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequest;
import com.bigname.core.restful.client.request.ApiEndpoint;

import java.util.HashMap;
import java.util.Map;

public class CreateJobRequest extends AbstractApiRequest {

    private Map<String, Object> context = new HashMap<>();
    public CreateJobRequest(Map<String, Object> context) {
        this.context = context;
    }

    @Override
    public ApiEndpoint getEndpoint() {
        return Click2MailEndpoint.CREATE_JOB;
    }

    @Override
    public Map<String, String> getFormParams() {
        Map<String, String> formParams = super.getFormParams();
            for (Map.Entry<String, Object> entry : context.entrySet()) {
                formParams.put(entry.getKey(), (String) entry.getValue());
            }
        return formParams;
    }
}
