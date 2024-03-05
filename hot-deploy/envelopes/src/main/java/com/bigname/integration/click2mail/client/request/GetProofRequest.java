package com.bigname.integration.click2mail.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequest;
import com.bigname.core.restful.client.request.ApiEndpoint;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Meenu on 21-06-2018.
 */
public class GetProofRequest extends AbstractApiRequest {
    private String jobId;
    private String proofId;
    public GetProofRequest (String jobId, String proofId) {
        this.jobId = jobId;
        this.proofId = proofId;
    }
    @Override
    public Map<String, String> getRequestTemplates() {
        Map<String, String> requestTemplate = new LinkedHashMap<>();
        requestTemplate.put("jobId", this.jobId);
        requestTemplate.put("proofId", this.proofId);
        return requestTemplate;
    }

    @Override
    public ApiEndpoint getEndpoint() {
        return Click2MailEndpoint.GET_PROOF;
    }
}
