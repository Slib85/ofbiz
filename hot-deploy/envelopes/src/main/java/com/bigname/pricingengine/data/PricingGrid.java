package com.bigname.pricingengine.data;

import com.bigname.pricingengine.dao.PricingEngineDataManager;
import com.bigname.pricingengine.exception.PricingEngineInitializationException;
import com.bigname.pricingengine.model.Vendor;
import com.bigname.pricingengine.util.EngineUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bigname.pricingengine.data.PricingBlock.*;

public class PricingGrid {

    private Map<String, PricingBlock> blocks = new HashMap<>();

    private Map<String, Vendor> vendors;

    private Map<String, String> virtualProductMap;

    private Map<String, String> addons;

    private Map<String, String> vendorStockColors;

    private static PricingGrid instance;

    private PricingGrid() {
        initialize();
    }

    public static PricingGrid getInstance() {
        if(instance == null) {
            instance = new PricingGrid();
        }
        return instance;
    }

    public static void invalidateCache() {
        instance = null;
        getInstance();
    }

    private void initialize() throws PricingEngineInitializationException{
        try {
            List<Map<String, String>> pricingData = PricingEngineDataManager.getPricingData();
            PricingNode globalNode = new PricingNode();
            for(Map<String, String> attributeMap : pricingData) {
                String vendorId = attributeMap.get("vendorId");
                String vendorProductId = attributeMap.get("vendorProductId");
                String productId = EngineUtil.isEmpty(vendorId) || EngineUtil.isEmpty(vendorProductId)? COMMON_BLOCK_ID : getVirtualProductId(vendorId, vendorProductId).toUpperCase();

                PricingAttribute pricingAttribute = new PricingAttribute(Long.parseLong(attributeMap.get("attributeValueId")), attributeMap.get("attributeId"), EngineUtil.toIntArray(attributeMap.get("quantityBreak")), EngineUtil.toBigDecimalArray(attributeMap.get("volumePrice")));

                if(EngineUtil.isEmpty(vendorId)) {
                    vendorId = COMMON_BLOCK_ID;
                }

                PricingBlock block = blocks.get(productId);
                if(block == null) {
                    PricingNode node = new PricingNode(pricingAttribute);
                    VendorPricingNode vendorNode = new VendorPricingNode(getVendor(vendorId), node, globalNode);
                    block = new PricingBlock(productId, vendorNode);
                    blocks.put(productId, block);
                } else {
                    VendorPricingNode vendorNode = block.getVendorNodes().get(vendorId);
                    if(vendorNode == null) {
                        PricingNode node = new PricingNode(pricingAttribute);
                        vendorNode = new VendorPricingNode(getVendor(vendorId), node, globalNode);
                        block.getVendorNodes().put(vendorId, vendorNode);
                    } else {
                        PricingNode node = vendorNode.getPricingNode();
                        node.getAttributeGroup().addAttribute(pricingAttribute);
                    }
                }
            }
            if(blocks.containsKey(COMMON_BLOCK_ID)) {
                PricingBlock commonBlock = blocks.get(COMMON_BLOCK_ID);
                globalNode.swap(commonBlock.getVendorNodes().get(COMMON_BLOCK_ID).getPricingNode());

                commonBlock.getVendorNodes().forEach((k,v) -> {
                    if(!k.equalsIgnoreCase(COMMON_BLOCK_ID)) {
                        v.getVendor().getCommonPricingNode().swap(v.getPricingNode());
                    }
                });
                blocks.remove(COMMON_BLOCK_ID);
            }
        } catch (Exception e) {
            throw new PricingEngineInitializationException(e);
        }
    }


    private Map<String, Vendor> getVendors() throws Exception {
        if(vendors == null) {
            vendors = PricingEngineDataManager.getVendorsMap();
        }
        return vendors;
    }

    private Map<String, String> getAddons() throws Exception {
        if(addons == null) {
            addons = PricingEngineDataManager.getAddons();
        }
        return addons;
    }

    private Map<String, String> getVirtualProductMap() throws Exception {
        if(virtualProductMap == null) {
            virtualProductMap = PricingEngineDataManager.getVirtualProductMap();
        }
        return virtualProductMap;
    }

    private Map<String, String> getVendorStockToColorMap() throws Exception {
        if(vendorStockColors == null) {
            vendorStockColors = PricingEngineDataManager.getVendorStockToColorMap();
        }
        return vendorStockColors;
    }

    private Vendor getVendor(String vendorId) throws Exception {
        return getVendors().get(vendorId);
    }

    public String getVirtualProductId(String vendorId, String vendorProductId) throws Exception {
        return getVirtualProductMap().get((vendorId + "|" + vendorProductId).toUpperCase());
    }

    public String getAddonName(String addonId) {
        try {
            return getAddons().getOrDefault(addonId, addonId);
        } catch (Exception e) {
            return addonId;
        }
    }

    public String getVendorStockPricingAttributeId(String colorGroup, String colorName, String paperTexture, String paperWeight, String vendorId) {
        try {
            if("EMPTY".equals(paperTexture)) {
                paperTexture = "";
            }
            return getVendorStockToColorMap().getOrDefault(colorGroup + "|" + colorName + "|" + paperTexture + "|" + paperWeight + "|" + vendorId, "");
        } catch (Exception e) {
            return "";
        }
    }

    public PricingBlock getPricingBlock(String name) {
        return blocks.get(name);
    }

    public static void main(String[] args) {
        getInstance();
    }


}
