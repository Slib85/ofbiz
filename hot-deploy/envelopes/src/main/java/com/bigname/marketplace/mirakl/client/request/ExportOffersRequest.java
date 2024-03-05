package com.bigname.marketplace.mirakl.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequestWithFile;
import com.bigname.core.restful.client.request.ApiEndpoint;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Manu on 3/7/2017.
 */
public class ExportOffersRequest extends AbstractApiRequestWithFile {

    private String storeId;

    public ExportOffersRequest(File file, String storeId) {
        this.storeId = storeId;
        setFile(file);
    }

    @Override
    public ApiEndpoint getEndpoint() {
        return MiraklEndpoint.OF01;
    }

    @Override
    public Map<String, String> getQueryParams() {
        Map<String, String> params = new HashMap<>();
        params.put("shop_id", storeId);
        return params;
    }

    @Override
    public Map<String, String> getFormParams() {
        Map<String, String> params = new HashMap<>();
        params.put("import_mode", "NORMAL");
        return params;
    }
}
