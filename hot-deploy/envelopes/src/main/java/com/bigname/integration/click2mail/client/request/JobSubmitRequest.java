package com.bigname.integration.click2mail.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequest;
import com.bigname.core.restful.client.request.ApiEndpoint;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JobSubmitRequest extends AbstractApiRequest {

   private Map<String, Object> params = new HashMap<>();
    private String jobId = null;

    public JobSubmitRequest(String jobId, Map<String, Object> params) {
        this.jobId = jobId;
        this.params = params;
    }

    @Override
    public ApiEndpoint getEndpoint() {
        return Click2MailEndpoint.JOB_SUBMIT;
    }

    @Override
    public Map<String, String> getFormParams() {
        Map<String, String> formParams = super.getQueryParams();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            formParams.put(entry.getKey(), (String) entry.getValue());
        }
        return formParams;
    }

    @Override
    public Map<String, String> getRequestTemplates() {
        Map<String, String> requestTemplate = new LinkedHashMap<>();
        if(this.jobId != null) {
            requestTemplate.put("jobId", this.jobId);
        }
        return requestTemplate;
    }
}
