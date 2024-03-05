package com.bigname.marketplace.mirakl.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequestWithFile;
import com.bigname.core.restful.client.request.ApiEndpoint;

import java.io.File;

/**
 * Created by Manu on 2/16/2017.
 */
public class ExportProductsRequest extends AbstractApiRequestWithFile {

    public ExportProductsRequest(File file) {
        setFile(file);
    }

    @Override
    public ApiEndpoint getEndpoint() {
        return MiraklEndpoint.P21;
    }
}
