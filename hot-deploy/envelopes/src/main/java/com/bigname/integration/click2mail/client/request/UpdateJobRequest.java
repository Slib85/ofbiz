package com.bigname.integration.click2mail.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequest;
import com.bigname.core.restful.client.request.ApiEndpoint;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class UpdateJobRequest extends AbstractApiRequest{
    private Map<String, Object> context = new HashMap<>();
    private String jobId;
    public UpdateJobRequest(Map<String, Object> context, String jobId) {
        this.context = context;
        this.jobId = jobId;
    }
    @Override
    public Map<String, String> getFormParams() {
        Map<String, String> formParams = super.getFormParams();
        for (Map.Entry<String, Object> entry : context.entrySet()) {
            formParams.put(entry.getKey(), (String) entry.getValue());
        }
        return formParams;
    }
    @Override
    public Map<String, String> getRequestTemplates() {
        Map<String, String> requestTemplate = new LinkedHashMap<>();
        requestTemplate.put("jobId", this.jobId);
        return requestTemplate;
    }

    @Override
    public ApiEndpoint getEndpoint() {
        return Click2MailEndpoint.UPDATE_JOB;
    }
}
