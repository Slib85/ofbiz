package com.bigname.pricingengine;

import com.bigname.pricingengine.data.PricingBlock;
import com.bigname.pricingengine.data.PricingGrid;
import com.bigname.pricingengine.data.VendorPricingNode;
import com.bigname.pricingengine.exception.PricingEngineException;
import com.bigname.pricingengine.request.PricingRequest;
import com.bigname.pricingengine.response.PricingResponse;
import com.bigname.pricingengine.util.EngineUtil;

import java.util.Map;


public abstract class PricingEngine {

    public static PricingResponse getPricing(PricingRequest request) throws PricingEngineException {
        try {
            PricingGrid pricingGrid = PricingGrid.getInstance();
            String sku = pricingGrid.getVirtualProductId(request.getVendorId(), request.getVendorSku());
            PricingBlock skuBlock = pricingGrid.getPricingBlock(sku);
            PricingResponse response = new PricingResponse(request);
            if(!EngineUtil.isEmpty(skuBlock)) {
                Map<String, VendorPricingNode> vendorNodes = skuBlock.getVendorNodes();
                for(VendorPricingNode vendorNode : vendorNodes.values()) {
                    vendorNode.findPrice(request, response);
                }
            }
            return response;
        } catch (Exception e) {
            throw new PricingEngineException(e);
        }
    }


}
