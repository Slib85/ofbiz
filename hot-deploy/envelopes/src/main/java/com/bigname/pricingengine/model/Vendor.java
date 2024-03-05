package com.bigname.pricingengine.model;

import com.bigname.pricingengine.calculator.impl.DefaultPricingCalculator;
import com.bigname.pricingengine.calculator.PricingCalculator;
import com.bigname.pricingengine.calculator.impl.AdmorePricingCalculator;
import com.bigname.pricingengine.data.PricingNode;
import com.bigname.pricingengine.request.PricingRequest;
import com.bigname.pricingengine.response.PricingResponse;

public class Vendor {
    private String vendorId;
    private String vendorName;
    private PricingNode commonPricingNode;

    public Vendor(String vendorId, String vendorName) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.commonPricingNode = new PricingNode();

    }

    public String getVendorId() {
        return vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public PricingNode getCommonPricingNode() {
        return commonPricingNode;
    }

    private PricingCalculator getVendorCalculator(Vendor vendor, PricingNode pricingNode, PricingNode globalPricingNode, PricingRequest request, PricingResponse response) {
        switch (vendor.getVendorId()) {
            case "V_POCKET_FOLDERS_FAST":
            case "V_ADMORE":
                return new AdmorePricingCalculator(vendor, pricingNode, globalPricingNode, request, response);
            default:
                return new DefaultPricingCalculator(vendor, pricingNode, globalPricingNode, request, response);
        }
    }

    public PricingCalculator getPricingCalculator(PricingNode pricingNode, PricingNode globalPricingNode, PricingRequest request, PricingResponse response) {
        return getVendorCalculator(this, pricingNode, globalPricingNode, request, response);
    }
}
