package com.bigname.pricingengine.response;

import com.bigname.pricingengine.common.Config;
import com.bigname.pricingengine.common.impl.ConfigSupport;
import com.bigname.pricingengine.request.PricingRequest;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class PricingResponse {
    public enum Type {DEFAULT, WITH_REUEST, QUANTITY_BREAKS_ONLY, DETAILED}

    public enum Attribute {REQUEST, SIMPLE_PRICING, DETAILED_PRICING, VENDORS_SIMPLE_PRICING, VENDORS_DETAILED_PRICING}

    private Config<Attribute, Object> response = new ConfigSupport<>();

    public PricingResponse(PricingRequest request) {
        response.put(Attribute.REQUEST, request.getContext());
        response.put(Attribute.VENDORS_SIMPLE_PRICING, new LinkedHashMap<>());
        response.put(Attribute.VENDORS_DETAILED_PRICING, new LinkedHashMap<>());
    }

    public void setVendorDetailedPricing(String vendorId, Map<String, Object> detailedPricing) {
        response.get(Attribute.VENDORS_DETAILED_PRICING, new LinkedHashMap<String, Object>()).put(vendorId, detailedPricing);
    }

    public void setVendorSimplePricing(String vendorId, Map<Integer, String> simplePricing) {
        response.get(Attribute.VENDORS_SIMPLE_PRICING, new LinkedHashMap<String, Object>()).put(vendorId, simplePricing);
    }

    public Map<String, Object> getResponse() {
        Map<String, Object> response = new LinkedHashMap<>();
        this.response.forEach((k,v) -> response.put(k.name(), v));
        return response;
    }
}
