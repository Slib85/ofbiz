package com.bigname.marketplace.mirakl.client;

import com.bigname.marketplace.mirakl.client.domain.*;
import com.bigname.marketplace.mirakl.client.request.*;

/**
 * Created by Manu on 1/30/2017.
 */
public interface MiraklApi {
    MiraklVersion getVersion();

    MiraklShops getShops();

    MiraklHierarchies getHierarchies();

    MiraklCategories getCategories();

    MiraklProductAttributes getProductAttributes();

    MiraklHierarchyExportTracking exportHierarchy(ExportHierarchyRequest request);

    MiraklProductAttributesExportTracking exportProductAttributes(ExportProductAttributesRequest request);

    MiraklProductsExportTracking exportProducts(ExportProductsRequest request);

    MiraklOffersExportTracking exportOffers(ExportOffersRequest request);

    MiraklCategoriesExportTracking exportCategories(ExportCategoriesRequest request);

    MiraklProductsOffers getProductsOffers(GetProductsOffersRequest request);

    MiraklCreatedOrders createOrder(CreateOrderRequest request);
}
