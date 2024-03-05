package com.bigname.integration.click2mail.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequest;
import com.bigname.core.restful.client.request.ApiEndpoint;

import java.util.LinkedHashMap;
import java.util.Map;

public class CreateJobProofRequest extends AbstractApiRequest{
    private String jobId;
    public CreateJobProofRequest (String jobId) {
        this.jobId = jobId;
    }
    @Override
    public Map<String, String> getRequestTemplates() {
        Map<String, String> requestTemplate = new LinkedHashMap<>();
        requestTemplate.put("jobId", this.jobId);
        return requestTemplate;
    }

    @Override
    public ApiEndpoint getEndpoint() {
        return Click2MailEndpoint.CREATE_JOB_PROOF;
    }
}
