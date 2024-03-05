package com.bigname.pricingengine.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PricingBlock {

    static final String COMMON_BLOCK_ID = "COMMON";

    private String productId;

    private Map<String, VendorPricingNode> vendorNodes = new HashMap<>();

    public PricingBlock(String productId, VendorPricingNode vendorPricingNode) {
        this.productId = productId;
        this.vendorNodes.put(vendorPricingNode.getVendor().getVendorId(), vendorPricingNode);
    }

    public PricingBlock(String productId, List<VendorPricingNode> vendorPricingNodes) {
        this.productId = productId;
        vendorPricingNodes.forEach(vp -> vendorNodes.put(vp.getVendor().getVendorId(), vp));
    }

    public String getProductId() {
        return productId;
    }

    public Map<String, VendorPricingNode> getVendorNodes() {
        return vendorNodes;
    }
}
