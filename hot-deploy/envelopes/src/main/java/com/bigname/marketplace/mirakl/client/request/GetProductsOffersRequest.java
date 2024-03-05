package com.bigname.marketplace.mirakl.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequest;
import com.bigname.core.restful.client.request.ApiEndpoint;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Manu on 2/24/2017.
 */
public class GetProductsOffersRequest extends AbstractApiRequest {

    private String[] productIds;

    public GetProductsOffersRequest(String[] productIds) {
        this.productIds = productIds;
    }

    public String[] getProductIds() {
        return productIds;
    }

    @Override
    public ApiEndpoint getEndpoint() {
        return MiraklEndpoint.P11;
    }

    @Override
    public Map<String, String> getQueryParams() {
        StringBuilder productIds = new StringBuilder();
        for(String productId : this.productIds) {
            if(productIds.length() != 0) {
                productIds.append(",");
            }
            productIds.append(productId);
        }
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("product_ids", productIds.toString());
        return queryParams;
    }
}
