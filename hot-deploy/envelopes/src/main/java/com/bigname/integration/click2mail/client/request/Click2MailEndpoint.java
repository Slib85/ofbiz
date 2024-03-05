package com.bigname.integration.click2mail.client.request;

import com.bigname.core.restful.client.request.ApiEndpoint;

public enum Click2MailEndpoint implements ApiEndpoint {
    CREATE_JOB("jobs"),
    UPLOAD_DOCUMENT("documents"),
    CREATE_ADDRESS_LIST("addressLists"),
    DELETE_ADDRESS("addressLists", "{addressListId}"),
    JOB_COST("jobs","{jobId}","cost"),
    UPDATE_JOB("jobs", "{jobId}", "update"),
    CREATE_JOB_PROOF("jobs", "{jobId}", "proof"),
    GET_PROOF("jobs", "{jobId}", "proof" , "{proofId}"),
    JOB_SUBMIT("jobs", "{jobId}", "submit"),
    JOB_CANCEL("jobs", "{jobId}", "cancel"),
    COST_ESTIMATE("costEstimate"),
    PRODUCT_OPTIONS("productOptions");

    private final String[] paths;

    Click2MailEndpoint(String... paths) {
        this.paths = paths;
    }
    @Override
    public String[] getPaths() {
        return paths;
    }

}
