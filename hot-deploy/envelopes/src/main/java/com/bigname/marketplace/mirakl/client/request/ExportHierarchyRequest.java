package com.bigname.marketplace.mirakl.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequestWithFile;
import com.bigname.core.restful.client.request.ApiEndpoint;

import java.io.File;

/**
 * Created by Manu on 2/13/2017.
 */
public class ExportHierarchyRequest extends AbstractApiRequestWithFile {

    public ExportHierarchyRequest(File file) {
        setFile(file);
    }

    @Override
    public ApiEndpoint getEndpoint() {
        return MiraklEndpoint.H01;
    }
}
