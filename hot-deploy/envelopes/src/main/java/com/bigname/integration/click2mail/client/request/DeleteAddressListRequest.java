package com.bigname.integration.click2mail.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequest;
import com.bigname.core.restful.client.request.ApiEndpoint;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DeleteAddressListRequest extends AbstractApiRequest {
    Map<String, Object> context = new HashMap<>();

    public DeleteAddressListRequest(Map<String, Object> context) {
        this.context = context;
    }

    @Override
    public ApiEndpoint getEndpoint() {
        return Click2MailEndpoint.DELETE_ADDRESS;
    }

    @Override
    public Map<String, String> getRequestTemplates() {
        final Map<String, String> requestTemplate = new LinkedHashMap<>();
        context.forEach((k,v) -> requestTemplate.put(k,(String)v));
        return requestTemplate;
    }
}
