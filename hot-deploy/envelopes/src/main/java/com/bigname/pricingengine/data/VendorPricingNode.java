package com.bigname.pricingengine.data;

import com.bigname.pricingengine.model.Vendor;
import com.bigname.pricingengine.request.PricingRequest;
import com.bigname.pricingengine.response.PricingResponse;


public class VendorPricingNode {

    private Vendor vendor;
    private PricingNode globalPricingNode;
    private PricingNode pricingNode;

    public VendorPricingNode(Vendor vendor, PricingNode pricingNode, PricingNode globalPricingNode) {
        this.vendor = vendor;
        this.pricingNode = pricingNode;
        this.globalPricingNode = globalPricingNode;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public PricingNode getPricingNode() {
        return pricingNode;
    }

    public PricingNode getGlobalPricingNode() {
        return globalPricingNode;
    }

    public void findPrice(PricingRequest request, PricingResponse response) {
        vendor.getPricingCalculator(pricingNode, globalPricingNode, request, response).calculate();
    }
}
