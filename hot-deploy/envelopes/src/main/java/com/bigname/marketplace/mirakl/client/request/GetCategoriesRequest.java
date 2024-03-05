package com.bigname.marketplace.mirakl.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequest;
import com.bigname.core.restful.client.request.ApiEndpoint;

/**
 * Created by Manu on 2/17/2017.
 */
public class GetCategoriesRequest extends AbstractApiRequest {
    @Override
    public ApiEndpoint getEndpoint() {
        return MiraklEndpoint.CA11;
    }
}
