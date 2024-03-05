package com.bigname.integration.click2mail.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequestWithFile;
import com.bigname.core.restful.client.request.ApiEndpoint;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UploadDocumentRequest extends AbstractApiRequestWithFile {

    private Map<String, Object> context = new HashMap<>();
    public UploadDocumentRequest(Map<String, Object> context, File file) {
        this.context = context;
        setFile(file);
    }
    @Override
    public ApiEndpoint getEndpoint() {
        return Click2MailEndpoint.UPLOAD_DOCUMENT;
    }

    @Override
    public File getFile() {
        return super.getFile();
    }

    @Override
    public void setFile(File file) {
        super.setFile(file);
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
