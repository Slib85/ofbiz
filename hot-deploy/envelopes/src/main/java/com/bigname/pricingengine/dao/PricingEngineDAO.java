package com.bigname.pricingengine.dao;

import com.bigname.pricingengine.model.Vendor;
import org.apache.ofbiz.entity.Delegator;

import java.util.List;
import java.util.Map;

public interface PricingEngineDAO {
    Map<String, Vendor> getVendorsMap() throws Exception;

    Map<String, String> getAddons() throws Exception;

    List<Map<String, Object>> getVendors() throws Exception;

    Map<String, Object> getVendor(String vendorId) throws Exception;

    List<Map<String, Object>> getPricingAttributes() throws Exception;

    Map<String, Object> getPricingAttribute(String attributeId) throws Exception;

    Map<String, Object> uploadVendorProducts(Map<String, Object> contextMap) throws Exception;

    Map<String, Object> saveVendorProduct(Map<String, Object> contextMap) throws Exception;

    Map<String, Object> saveVendor(Map<String, Object> contextMap) throws Exception;

    Map<String, Object> saveAttribute(Map<String, Object> contextMap) throws Exception;

    Map<String, Object> saveColorAndStock(Map<String, Object> contextMap) throws Exception;

    Map<String, Object> saveAttributeValue(Map<String, Object> contextMap) throws Exception;

    List<Map<String, Object>> getVendorProducts(String... vendorId) throws Exception;

    Map<String, Object> getVendorProduct(String vendorProductId, String vendorId) throws Exception;

    Map<String, String> getVirtualProductMap() throws Exception;

    Map<String, String> getVendorStockToColorMap() throws Exception;

    List<Map<String, Object>> getColors() throws Exception;

    Map<String, Object> getColor(String colorCode, String vendorId) throws Exception;

    List<Map<String, String>> getPricingData() throws Exception;

    List<Map<String, Object>> getPricingDetails(String vendorProductId, String vendorId) throws Exception;

    List<Map<String, String>> getColorHierarchy(String vendorId) throws  Exception;
}
