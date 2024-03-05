package com.bigname.pricingengine.model;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private long productId;
    private List<VendorProduct> vendorProducts = new ArrayList<>();
    private Vendor preferredVendor;

    public Product(long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }

    public List<VendorProduct> getVendorProducts() {
        return vendorProducts;
    }

    public Vendor getPreferredVendor() {
        return preferredVendor;
    }

    public void setPreferredVendor(Vendor preferredVendor) {
        this.preferredVendor = preferredVendor;
    }
}
