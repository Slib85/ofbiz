package com.bigname.marketplace.mirakl.client;

import com.bigname.core.restful.client.AbstractApiClient;
import com.bigname.core.restful.client.security.Credential;
import com.bigname.marketplace.mirakl.client.domain.*;
import com.bigname.marketplace.mirakl.client.request.*;
import org.apache.ofbiz.base.util.UtilProperties;

/**
 * Created by Manu on 2/7/2017.
 */
public class MiraklApiClient extends AbstractApiClient implements MiraklApi{

    private static final String FRONT_API_KEY = UtilProperties.getPropertyValue("envelopes", "mirakl.front.api.key");
    private static final String STORES_API_KEY = UtilProperties.getPropertyValue("envelopes", "mirakl.stores.api.key");
    private static final String ENV_URL = UtilProperties.getPropertyValue("envelopes", "mirakl.env.url");


    public MiraklApiClient() {
        super(ENV_URL, new Credential(FRONT_API_KEY));
    }

    public MiraklApiClient(boolean storeUser) {
        super(ENV_URL, new Credential(STORES_API_KEY));
    }

    public MiraklVersion getVersion() {
        return get(new GetVersionRequest(), MiraklVersion.class);
    }

    @Override
    public MiraklShops getShops() {
        return get(new GetShopsRequest(), MiraklShops.class);
    }

    @Override
    public MiraklHierarchies getHierarchies() {
        return get(new GetHierarchyRequest(), MiraklHierarchies.class);
    }

    @Override
    public MiraklCategories getCategories() {
        return get(new GetCategoriesRequest(), MiraklCategories.class);
    }

    @Override
    public MiraklProductAttributes getProductAttributes() {
        return get(new GetProductAttributesRequest(), MiraklProductAttributes.class);
    }

    @Override
    public MiraklHierarchyExportTracking exportHierarchy(ExportHierarchyRequest request) {
        return post(request, buildMultipart(request), MiraklHierarchyExportTracking.class);
    }

    @Override
    public MiraklProductAttributesExportTracking exportProductAttributes(ExportProductAttributesRequest request) {
        return post(request, buildMultipart(request), MiraklProductAttributesExportTracking.class);
    }

    @Override
    public MiraklProductsExportTracking exportProducts(ExportProductsRequest request) {
        return post(request, buildMultipart(request), MiraklProductsExportTracking.class);
    }

    @Override
    public MiraklOffersExportTracking exportOffers(ExportOffersRequest request) {
        /*

        multipart.bodyPart(new FormDataBodyPart("shop", request.getShopId()));
        multipart.bodyPart(new FormDataBodyPart("import_mode", request.getImportMode().name()));
        */

        return post(request, buildMultipart(request), MiraklOffersExportTracking.class);
    }

    @Override
    public MiraklCategoriesExportTracking exportCategories(ExportCategoriesRequest request) {
        return post(request, buildMultipart(request), MiraklCategoriesExportTracking.class);
    }

    @Override
    public MiraklProductsOffers getProductsOffers(GetProductsOffersRequest request) {
        return get(request, MiraklProductsOffers.class);
    }

    @Override
    public MiraklCreatedOrders createOrder(CreateOrderRequest request) {
        return post(request, toJsonString(request.getCreateOrder()), MiraklCreatedOrders.class);
    }
}
