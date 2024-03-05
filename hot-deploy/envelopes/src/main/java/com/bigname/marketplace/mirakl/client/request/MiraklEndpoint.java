package com.bigname.marketplace.mirakl.client.request;

import com.bigname.core.restful.client.request.ApiEndpoint;

/**
 * Created by Manu on 1/30/2017.
 */
public enum MiraklEndpoint implements ApiEndpoint{

    CA01("categories", "synchros"),
    CA11("categories"),

    H01("hierarchies", "imports"),
    H11("hierarchies"),

    OF01("offers", "imports"),

    OR01("orders"),

    P11("products", "offers"),

    PM01("products", "attributes", "imports"),
    PM11("products", "attributes"),
    P21("products", "synchros"),

    S20("shops"),

    V01("version");



    private final String[] paths;

    MiraklEndpoint(String... paths) {
        this.paths = paths;
    }
    @Override
    public String[] getPaths() {
        return paths;
    }
}
