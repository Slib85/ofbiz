package com.bigname.pricingengine.model;

import java.util.ArrayList;
import java.util.List;

public class VendorProduct {
    private String uniqueVendorProductId;
    private String vendorProductId;
    private String vendorProductName;
    private Vendor vendor;
    private List<VendorProduct> competitorProducts = new ArrayList<>();

    public VendorProduct(String vendorProductId, String vendorProductName, Vendor vendor) {
        this.vendorProductId = vendorProductId;
        this.vendorProductName = vendorProductName;
        this.vendor = vendor;
        this.uniqueVendorProductId = vendor.getVendorId() + "|" + vendorProductId;
    }

    public String getUniqueVendorProductId() {
        return uniqueVendorProductId;
    }

    public String getVendorProductId() {
        return vendorProductId;
    }

    public String getVendorProductName() {
        return vendorProductName;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public List<VendorProduct> getCompetitorProducts() {
        return competitorProducts;
    }
}
