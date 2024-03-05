package com.bigname.pricingengine.dao;

import com.bigname.pricingengine.dao.impl.PricingEngineDAOImpl;
import com.bigname.pricingengine.model.Vendor;
import org.apache.ofbiz.entity.Delegator;

import java.util.List;
import java.util.Map;

public abstract class PricingEngineDataManager {

    private static PricingEngineDAO dao = new PricingEngineDAOImpl();
    public static Map<String, Vendor> getVendorsMap() throws Exception {
        return dao.getVendorsMap();
    }

    public static Map<String, String> getAddons() throws Exception {
        return dao.getAddons();
    }

    public static List<Map<String, Object>> getVendors() throws Exception {
        return dao.getVendors();
    }
    public static Map<String, Object> getVendor(String vendorId) throws Exception {
        return dao.getVendor(vendorId);
    }

    public static List<Map<String, Object>> getPricingAttributes() throws Exception {
        return dao.getPricingAttributes();
    }
    public static Map<String, Object> getPricingAttribute(String attributeId) throws Exception {
        return dao.getPricingAttribute(attributeId);
    }

    public static List<Map<String, Object>> getVendorProducts(String... vendorId) throws Exception {
        return dao.getVendorProducts(vendorId);
    }

    public static Map<String, Object> getVendorProduct(String vendorProductId, String vendorId) throws Exception {
        return dao.getVendorProduct(vendorProductId, vendorId);
    }

    public static List<Map<String, Object>> getColors() throws Exception {
        return dao.getColors();
    }

    public static Map<String, Object> getColor(String colorCode, String vendorId) throws Exception {
        return dao.getVendorProduct(colorCode, vendorId);
    }

    public static Map<String, String> getVirtualProductMap() throws Exception {
        return dao.getVirtualProductMap();
    }

    public static Map<String, String> getVendorStockToColorMap() throws Exception {
        return dao.getVendorStockToColorMap();
    }

    public static List<Map<String, String>> getPricingData() throws Exception {
        return dao.getPricingData();
    }

    public static List<Map<String, Object>> getPricingDetails(String vendorProductId, String vendorId) throws Exception {
        return dao.getPricingDetails(vendorProductId, vendorId);
    }

    public static Map<String, Object> uploadProducts(Map<String, Object> fileData) throws Exception {
        return dao.uploadVendorProducts(fileData);
    }

    public static Map<String, Object> saveVendorProduct(Map<String, Object> contextMap) throws Exception {
        return dao.saveVendorProduct(contextMap);
    }

    public static Map<String, Object> saveVendor(Map<String, Object> contextMap) throws Exception {
        return dao.saveVendor(contextMap);
    }

    public static Map<String, Object> saveAttribute(Map<String, Object> contextMap) throws Exception {
        return dao.saveAttribute(contextMap);
    }

    public static Map<String, Object> saveColorAndStock(Map<String, Object> contextMap) throws Exception {
        return dao.saveColorAndStock(contextMap);
    }

    public static Map<String, Object> savePricingAttributeValue(Map<String, Object> contextMap) throws Exception {
        return dao.saveAttributeValue(contextMap);
    }

    public static List<Map<String,String>> getColorHierarchy(String vendorId) throws Exception {
        return dao.getColorHierarchy(vendorId);
    }
}
