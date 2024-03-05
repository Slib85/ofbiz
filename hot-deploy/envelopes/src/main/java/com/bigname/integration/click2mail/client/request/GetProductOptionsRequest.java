package com.bigname.integration.click2mail.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequest;
import com.bigname.core.restful.client.request.ApiEndpoint;

import java.util.Map;

/**
 * Created by Meenu on 19-04-2018.
 */
public class GetProductOptionsRequest extends AbstractApiRequest {
    private String documentClass;
    public GetProductOptionsRequest(String documentClass) {
        this.documentClass = documentClass;
    }
    @Override
    public ApiEndpoint getEndpoint() {
        return Click2MailEndpoint.PRODUCT_OPTIONS;
    }

    @Override
    public Map<String, String> getQueryParams() {
        Map<String, String> formParams = super.getQueryParams();
        formParams.put("documentClass", documentClass);
        return formParams;
    }
}
