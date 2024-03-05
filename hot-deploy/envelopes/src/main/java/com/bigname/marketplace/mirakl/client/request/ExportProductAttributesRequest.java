package com.bigname.marketplace.mirakl.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequestWithFile;
import com.bigname.core.restful.client.request.ApiEndpoint;

import java.io.File;

/**
 * Created by Manu on 2/14/2017.
 */
public class ExportProductAttributesRequest extends AbstractApiRequestWithFile {

    public ExportProductAttributesRequest(File file) {
        setFile(file);
    }

    @Override
    public ApiEndpoint getEndpoint() {
        return MiraklEndpoint.PM01;
    }
}