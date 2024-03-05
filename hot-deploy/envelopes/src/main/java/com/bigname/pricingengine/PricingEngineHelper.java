package com.bigname.pricingengine;

import com.bigname.pricingengine.dao.PricingEngineDataManager;
import com.bigname.pricingengine.exception.PricingEngineException;
import com.bigname.pricingengine.request.impl.DefaultPricingRequest;
import org.apache.ofbiz.entity.Delegator;

import java.util.*;

public class PricingEngineHelper {

    public static Map<String, Object> getPricing(Map<String, Object> context) throws PricingEngineException {
        return PricingEngine.getPricing(new DefaultPricingRequest(context)).getResponse();
    }

    public static List<Map<String, Object>> getVendorProducts(String... vendorId) throws Exception {
        return PricingEngineDataManager.getVendorProducts(vendorId);
    }

    public static Map<String, Object> getVendorProduct(String vendorProductId, String vendorId) throws Exception {
        return PricingEngineDataManager.getVendorProduct(vendorProductId, vendorId);
    }

    public static List<Map<String, Object>> getPricingDetails(String vendorProductId, String vendorId) throws Exception {
        return PricingEngineDataManager.getPricingDetails(vendorProductId, vendorId);
    }

    public static Map<String, Object> uploadProducts(Map<String, Object> fileData) throws Exception {
        return PricingEngineDataManager.uploadProducts(fileData);
    }

    public static Map<String, Object> saveProduct(Map<String, Object> contextMap) throws Exception {
        return PricingEngineDataManager.saveVendorProduct(contextMap);
    }

    public static Map<String, Object> saveVendor(Map<String, Object> contextMap) throws Exception {
        return PricingEngineDataManager.saveVendor(contextMap);
    }

    public static Map<String, Object> saveAttribute(Map<String, Object> contextMap) throws Exception {
        return PricingEngineDataManager.saveAttribute(contextMap);
    }

    public static Map<String, Object> saveColorAndStock(Map<String, Object> contextMap) throws Exception {
        return PricingEngineDataManager.saveColorAndStock(contextMap);
    }

    public static Map<String, Object> savePricingAttributeValue(Map<String, Object> contextMap) throws Exception {
        return PricingEngineDataManager.savePricingAttributeValue(contextMap);
    }

    public static List<Map<String, Object>> getPricingAttributes() throws Exception {
        return PricingEngineDataManager.getPricingAttributes();
    }

    public static Map<String, Object> getPricingAttribute(String attributeId) throws Exception {
        return PricingEngineDataManager.getPricingAttribute(attributeId);
    }

    public static List<Map<String, Object>> getVendors() throws Exception {
        return PricingEngineDataManager.getVendors();
    }

    public static Map<String, Object> getVendor(String vendorId) throws Exception {
        return PricingEngineDataManager.getVendor(vendorId);
    }

    public static List<Map<String, Object>> getColors() throws Exception {
        return PricingEngineDataManager.getColors();
    }

    public static Map<String, Object> getColor(String colorCode, String vendorId) throws Exception {
        return PricingEngineDataManager.getColor(colorCode, vendorId);
    }

    public static Map<String, Object> createColorHierarchy(String vendorId) {

        Map<String, Object> hierarchyJSON = new HashMap<>();

        try {
            List<Map<String, String>> colorData = PricingEngineDataManager.getColorHierarchy(vendorId);
            System.err.println("colorData = " + colorData);
            if (colorData == null || colorData.isEmpty()) {
                return hierarchyJSON;
            }

            Map<String, Object> colorGroup = new LinkedHashMap<>();
            Map<String, Object> colorName = new LinkedHashMap<>();
            Map<String, Object> texture = new LinkedHashMap<>();
            Map<String, Object> weight = new LinkedHashMap<>();

            List<Map<String, Object>> colorGroups = new LinkedList<>();
            List<Map<String, Object>> colorNames = new LinkedList<>();
            List<Map<String, Object>> textures = new LinkedList<>();
            List<Map<String, Object>> weights = new LinkedList<>();

            String previousColorGroupValue = "";
            String previousColorNameValue = "";
            String previousPaperTextureValue = "";

            for (Map<String, String> colorRow : colorData) {

                String currentColorGroupValue = colorRow.get("colorGroup");
                String currentColorNameValue = colorRow.get("colorName");
                String currentPaperTextureValue = colorRow.get("paperTexture");
                if (currentPaperTextureValue.isEmpty()) {
                    currentPaperTextureValue = "EMPTY";
                }
                String currentPaperWeightValue = colorRow.get("paperWeight");
                if (currentPaperWeightValue.isEmpty()) {
                    currentPaperWeightValue = "EMPTY";
                }

                if (previousColorGroupValue.isEmpty()) {
                    colorGroup.put("colorNames", colorNames);
                    colorName.put("paperTextures", textures);
                    texture.put("paperWeights", weights);
                }

                boolean parentReset = false;

                if (!previousColorGroupValue.isEmpty() && !currentColorGroupValue.equals(previousColorGroupValue)) {
                    weights = new ArrayList<>();

                    texture = new LinkedHashMap<>();
                    texture.put("paperWeights", weights);

                    textures = new ArrayList<>();

                    colorName = new LinkedHashMap<>();
                    colorName.put("paperTextures", textures);

                    colorNames = new ArrayList<>();

                    colorGroup = new LinkedHashMap<>();
                    colorGroup.put("colorNames", colorNames);

                    parentReset = true;
                }

                if (!parentReset && !previousColorNameValue.isEmpty() && !currentColorNameValue.equals(previousColorNameValue)) {
                    weights = new ArrayList<>();

                    texture = new LinkedHashMap<>();
                    texture.put("paperWeights", weights);

                    textures = new ArrayList<>();

                    colorName = new LinkedHashMap<>();
                    colorName.put("paperTextures", textures);

                    parentReset = true;
                }

                if (!parentReset && !previousPaperTextureValue.isEmpty() && !currentPaperTextureValue.equals(previousPaperTextureValue)) {
                    weights = new ArrayList<>();

                    texture = new LinkedHashMap<>();
                    texture.put("paperWeights", weights);

                    parentReset = true;
                }

                weight = new LinkedHashMap<>();

                if (colorGroup.size() == 1) {
                    colorGroup.put("value", currentColorGroupValue);
                    colorGroup.put("text", currentColorGroupValue);
                    colorGroups.add(colorGroup);
                }

                if (colorName.size() == 1) {
                    colorName.put("value", currentColorNameValue);
                    colorName.put("text", currentColorNameValue);
                    colorNames.add(colorName);
                }

                if (texture.size() == 1) {
                    texture.put("value", currentPaperTextureValue);
                    texture.put("text", currentPaperTextureValue);
                    textures.add(texture);
                }

                if (weight.size() == 0) {
                    weight.put("value", currentPaperWeightValue);
                    weight.put("text", currentPaperWeightValue);
                    weights.add(weight);
                }

                previousColorGroupValue = currentColorGroupValue;
                previousColorNameValue = currentColorNameValue;
                previousPaperTextureValue = currentPaperTextureValue;

            }

            hierarchyJSON.put("colorGroups", colorGroups);


        } catch (Exception e) {
            e.printStackTrace();
        }


        return hierarchyJSON;
    }

}
