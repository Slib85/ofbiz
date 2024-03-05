package com.bigname.marketplace.mirakl;

import com.bigname.marketplace.mirakl.client.MiraklApi;
import com.bigname.marketplace.mirakl.client.MiraklApiClient;
import com.bigname.marketplace.mirakl.client.domain.*;
import com.bigname.marketplace.mirakl.client.request.*;

import java.io.File;

/**
 * Created by Manu on 2/6/2017.
 */
public class MiraklUtil {


    public static MiraklShops getMiraklShops() {
        return getMiraklClient().getShops();
    }

    public static MiraklHierarchies getMiraklHierarchies() {
        return getMiraklClient().getHierarchies();
    }

    public static MiraklCategories getMiraklCategories() {
        return getMiraklClient().getCategories();
    }

    public static MiraklProductAttributes getMiraklProductAttributes() {
        return getMiraklClient().getProductAttributes();
    }

    public static MiraklHierarchyExportTracking exportHierarchy(File feed) {
        return getMiraklClient().exportHierarchy(new ExportHierarchyRequest(feed));
    }

    public static MiraklCategoriesExportTracking exportCategories(File feed) {
        return getMiraklClient().exportCategories(new ExportCategoriesRequest(feed));
    }

    public static MiraklProductAttributesExportTracking exportProductAttributes(File feed) {
        return getMiraklClient().exportProductAttributes(new ExportProductAttributesRequest(feed));
    }

    public static MiraklProductsExportTracking exportProducts(File feed) {
        return getMiraklClient().exportProducts(new ExportProductsRequest(feed));
    }

    public static MiraklOffersExportTracking exportOffers(File feed, String storeId) {
        return getMiraklStoreClient().exportOffers(new ExportOffersRequest(feed, storeId));
    }

    public static MiraklProductsOffers getProductsOffers(String[] productIds) {
        return getMiraklClient().getProductsOffers(new GetProductsOffersRequest(productIds));
    }

    public static MiraklCreatedOrders createOrder(CreateOrderRequest request) {
        return new MiraklApiClient().createOrder(request);
    }

    public static MiraklVersion getMiraklVersion() {
        return getMiraklClient().getVersion();
    }

    private static MiraklApi getMiraklClient() {
        return new MiraklApiClient();
    }

    private static MiraklApi getMiraklStoreClient() {
        return new MiraklApiClient(true);
    }


}
