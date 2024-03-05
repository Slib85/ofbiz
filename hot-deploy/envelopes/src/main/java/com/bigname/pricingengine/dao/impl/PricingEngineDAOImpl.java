package com.bigname.pricingengine.dao.impl;

import com.bigname.pricingengine.common.Context;
import com.bigname.pricingengine.common.impl.ContextSupport;
import com.bigname.pricingengine.dao.PricingEngineDAO;
import com.bigname.pricingengine.data.PricingAttribute;
import com.bigname.pricingengine.model.Vendor;
import com.bigname.pricingengine.util.EngineUtil;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.DelegatorFactory;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.jdbc.SQLProcessor;
import org.apache.ofbiz.entity.transaction.GenericTransactionException;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.entity.util.EntityQuery;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.*;

public class PricingEngineDAOImpl implements PricingEngineDAO {

    enum ProductAttribute {
        PRODUCT_ID("productId"),
        VENDOR_ID("vendorId"),
        PRICING_ATTRIBUTE(""),
        PRICING_ATTRIBUTE_ID("attributeId"),
        QUANTITY_BREAKS("qtyBreaks"),
        VOLUME_PRICING("values"),
        PRODUCT_NAME("productName"),
        SHORT_NAME("shortName"),
        DESCRIPTION("description");
        String fieldName;
        ProductAttribute(String fieldName) {
            this.fieldName = fieldName;
        }

        String getFieldName() {
            return fieldName;
        }

        static ProductAttribute[] PRODUCT_ATTRIBUTES = new ProductAttribute[] { PRODUCT_ID, VENDOR_ID, PRODUCT_NAME, SHORT_NAME, DESCRIPTION };
        static ProductAttribute[] PRICING_ATTRIBUTE_ATTRIBUTES = new ProductAttribute[] { PRICING_ATTRIBUTE_ID };
        static ProductAttribute[] PRICING_DETAIL_ATTRIBUTES = new ProductAttribute[] { PRODUCT_ID, VENDOR_ID, PRICING_ATTRIBUTE_ID, QUANTITY_BREAKS, VOLUME_PRICING };
        static Map<String, Object> getContext(Map<String, Object> data, ProductAttribute[] attributes ) {
            Map<String, Object> context = new HashMap<>();
            if(EngineUtil.isEmpty(attributes)) {
                for (ProductAttribute attribute : attributes) {
                    if(data.containsKey(attribute.name())) {
                        context.put(attribute.getFieldName(), data.get(attribute.name()));
                    }
                }
            }
            return context;
        }
    };

    @Override
    public Map<String, Vendor> getVendorsMap() throws Exception {
        Map<String, Vendor> vendors = new HashMap<>();
        Delegator delegator = DelegatorFactory.getDelegator("default");
        List<GenericValue> vendorGVs = EntityQuery.use(delegator).select("vendorId", "vendorName").from("PeVendor").queryList();
        vendorGVs.forEach(v -> vendors.put(v.getString("vendorId"), new Vendor(v.getString("vendorId"), v.getString("vendorName"))));
        return vendors;
    }

    @Override
    public Map<String, String> getAddons() throws Exception {
        Map<String, String> addons = new HashMap<>();
        Delegator delegator = DelegatorFactory.getDelegator("default");
        List<GenericValue> addonGVs = EntityQuery.use(delegator).select("addonId", "addonName").from("PeAddon").queryList();
        addonGVs.forEach(v -> addons.put(v.getString("addonId"), v.getString("addonName")));
        return addons;
    }

    @Override
    public List<Map<String, Object>> getVendors() throws Exception {
        List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
        conditionList.add(EntityCondition.makeCondition("vendorId", EntityOperator.NOT_EQUAL, "COMMON"));

        List<Map<String, Object>> vendors = new ArrayList<>();
        Delegator delegator = DelegatorFactory.getDelegator("default");
        List<GenericValue> vendorGVs = EntityQuery.use(delegator).from("PeVendor").where(EntityCondition.makeCondition(conditionList)).orderBy("vendorId").queryList();
        vendorGVs.forEach(v -> vendors.add(new HashMap<>(v)));
        return vendors;
    }

    @Override
    public Map<String, Object> getVendor(String vendorId) throws Exception {
        Map<String, Object> vendor = new HashMap<>();
        Delegator delegator = DelegatorFactory.getDelegator("default");
        GenericValue vendorGV = EntityQuery.use(delegator).from("PeVendor").where(UtilMisc.toMap("vendorId", vendorId)).queryOne();
        if(!EngineUtil.isEmpty(vendorGV)) {
            vendor = new HashMap<>(vendorGV);
        }
        return vendor;
    }

    @Override
    public List<Map<String, Object>> getPricingAttributes() throws Exception {
        List<Map<String, Object>> attributes = new ArrayList<>();
        Delegator delegator = DelegatorFactory.getDelegator("default");
        List<GenericValue> attributeGVs = EntityQuery.use(delegator).from("PeAttribute").orderBy("sequenceNum", "attributeId").queryList();
        attributeGVs.forEach(v -> attributes.add(new HashMap<>(v)));
        return attributes;
    }

    @Override
    public Map<String, Object> getPricingAttribute(String attributeId) throws Exception {
        Map<String, Object> attribute = new HashMap<>();
        Delegator delegator = DelegatorFactory.getDelegator("default");
        GenericValue attributeGV = EntityQuery.use(delegator).from("PeAttribute").where(UtilMisc.toMap("attributeId", attributeId)).queryOne();
        if(!EngineUtil.isEmpty(attributeGV)) {
            attribute = new HashMap<>(attributeGV);
        }
        return attribute;
    }

    private Map<String, Object> getOrCreatePricingAttribute(String attributeId) throws Exception {
        Map<String, Object> attribute = getPricingAttribute(attributeId);

        if(EngineUtil.isEmpty(attribute)) {
            saveAttribute(UtilMisc.toMap("attributeId", attributeId));
            attribute = getPricingAttribute(attributeId);
        }

        return attribute;
    }

    @Override
    public Map<String, Object> uploadVendorProducts(Map<String, Object> fileData) throws Exception {
        Map<String, Object> result = new HashMap<>();

        if (UtilValidate.isNotEmpty(fileData) && UtilValidate.isNotEmpty(fileData.get("files"))) {
            List<Map> files = (List<Map>) fileData.get("files");
            String fileLocation = "";
            for (Map file : files) {
                Iterator iterator = file.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry pairs = (Map.Entry) iterator.next();
                    if ("path".equalsIgnoreCase((String) pairs.getKey())) {
                        fileLocation = EnvConstantsUtil.OFBIZ_HOME + (pairs.getValue());
                        break;
                    }
                }
            }

            if(!EngineUtil.isEmpty(fileLocation)) {
                Map<String, Object> productsData = groupDataByVendorProduct(EngineUtil.parseDataFile(fileLocation));
                if(!EngineUtil.isEmpty(productsData)) {
                    return createProducts(productsData);
                }

            }
        }
        result.put("success", false);
        return result;
    }


    private Map<String, Object> createProducts(Map<String, Object> productsData) {

        Delegator delegator = DelegatorFactory.getDelegator("default");
        Map<String, Object> result = new HashMap<>();
        boolean success = false;
        boolean beganTransaction = false;
        try {
            beganTransaction = TransactionUtil.begin();
            try {
                for (Map.Entry<String, Object> entry : productsData.entrySet()) {
                    Context<String, Object> productContext = new ContextSupport<>((Map<String, Object>)entry.getValue());
                    String productId = productContext.get(ProductAttribute.PRODUCT_ID.name(), "");
                    String vendorId = productContext.get(ProductAttribute.VENDOR_ID.name(), "");
                    List<Map<String, Object>> pricingAttributes = productContext.get(ProductAttribute.PRICING_ATTRIBUTE.name(), new ArrayList<>());

                    if (!EngineUtil.isEmpty(productId, true) && !EngineUtil.isEmpty(vendorId, true)) {
                        Map<String, Object> product = getVendorProduct(productId, vendorId);

                        if(EngineUtil.isEmpty(product)) { // Create if product won't exist

                            // Vendor Product Creation
                            GenericValue productGV = delegator.makeValue("PeVendorProduct", "vendorId", vendorId, "vendorProductId", productId, "productName", productId);
                            delegator.create(productGV);

                            // Create Pricing Attribute Values and Pricing Attribute (if required)
                            for (Map<String, Object> pricingAttribute : pricingAttributes) {
                                Context<String, Object> attributeContext = new ContextSupport<>(pricingAttribute);
                                String attributeId = attributeContext.get(ProductAttribute.PRICING_ATTRIBUTE_ID.name(), "");

                                if (!EngineUtil.isEmpty(attributeId, true)) {
                                    Map<String, Object> attribute = getPricingAttribute(attributeId);

                                    if(EngineUtil.isEmpty(attribute)) { // Create the Pricing Attribute, if it won't exists
                                        GenericValue attributeGV = delegator.makeValue("PeAttribute", "attributeId", attributeId);
                                        delegator.create(attributeGV);
                                    }

                                    // Save the attribute Values
                                    String[] values = EngineUtil.validateQuantityBreakValues(attributeContext.get(ProductAttribute.QUANTITY_BREAKS.name(), ""), attributeContext.get(ProductAttribute.VOLUME_PRICING.name(), ""));
                                    if(!EngineUtil.isEmpty(values)) {
                                        GenericValue attributeValueGV = delegator.makeValue("PeAttributeValue", "attributeValueId", delegator.getNextSeqId("PeAttributeValue"));
                                        attributeValueGV.put("vendorId", vendorId);
                                        attributeValueGV.put("vendorProductId", productId);
                                        attributeValueGV.put("attributeId", attributeId);
                                        attributeValueGV.put("quantityBreak", values[0]);
                                        attributeValueGV.put("volumePrice", values[1]);
                                        delegator.create(attributeValueGV);
                                    }
                                }
                            }

                        } else{ // Skip this product, since it already exists
                            continue;
                        }
                    }
                    success = true;
                }
            } catch (Exception e1) {
                success = false;
                TransactionUtil.rollback(beganTransaction, "Error creating vendor products", e1);
                EnvUtil.reportError(e1);
            } finally {
                    try {
                        TransactionUtil.commit(beganTransaction);
                    } catch (GenericEntityException e2) {
                        EnvUtil.reportError(e2);
                    }

            }
        } catch (GenericTransactionException e3) {
            EnvUtil.reportError(e3);
            success = false;
        }
        result.put("success", success);
        return result;
    }

    @Override
    public Map<String, Object> saveVendorProduct(Map<String, Object> contextMap) throws Exception {
        Context<String, Object> context = new ContextSupport<>(contextMap);
        boolean success = false;
        Map<String, Object> result = new HashMap<>();

        Delegator delegator = DelegatorFactory.getDelegator("default");

        String productId = context.get("productId", "");
        String vendorId = context.get("vendorId", "");
        String productName = context.get("productName", "");
        String shortName = context.get("shortName", "");
        String description = context.get("description", "");
        if (!EngineUtil.isEmpty(productId) && !EngineUtil.isEmpty(vendorId)) {
            boolean updateMode = true;
            GenericValue productGV = EntityQuery.use(delegator).from("PeVendorProduct").where(UtilMisc.toMap("vendorId", vendorId, "vendorProductId", productId)).queryOne();
            if (EngineUtil.isEmpty(productGV)) {
                updateMode = false;
                productGV = delegator.makeValue("PeVendorProduct", "vendorId", vendorId, "vendorProductId", productId);
            }
            productGV.put("productName", productName);
            productGV.put("shortName", shortName);
            productGV.put("description", description);
            delegator.createOrStore(productGV);
            success = true;
            result.put("mode", updateMode ? "MODIFY" : "NEW");
        }
        result.put("success", success);

        return result;
    }

    @Override
    public Map<String, Object> saveVendor(Map<String, Object> contextMap) throws Exception {
        Context<String, Object> context = new ContextSupport<>(contextMap);
        boolean success = false;
        Map<String, Object> result = new HashMap<>();

        Delegator delegator = DelegatorFactory.getDelegator("default");

        String vendorId = context.get("vendorId", "");
        String vendorName = context.get("vendorName", "");
        if (!EngineUtil.isEmpty(vendorId)) {
            boolean updateMode = true;
            GenericValue vendorGV = EntityQuery.use(delegator).from("PeVendor").where(UtilMisc.toMap("vendorId", vendorId)).queryOne();
            if (EngineUtil.isEmpty(vendorGV)) {
                updateMode = false;
                vendorGV = delegator.makeValue("PeVendor", "vendorId", vendorId);
            }
            vendorGV.put("vendorName", vendorName);
            delegator.createOrStore(vendorGV);
            success = true;
            result.put("mode", updateMode ? "MODIFY" : "NEW");
        }
        result.put("success", success);

        return result;
    }

    @Override
    public Map<String, Object> saveAttribute(Map<String, Object> contextMap) throws Exception {
        Context<String, Object> context = new ContextSupport<>(contextMap);
        boolean success = false;
        Map<String, Object> result = new HashMap<>();

        Delegator delegator = DelegatorFactory.getDelegator("default");

        String attributeId = context.get("attributeId", "");
        String attributeName = context.get("attributeName", "");
        String attributeLabel = context.get("attributeLabel", "");
        String attributeDescription = context.get("attributeDescription", "");
        if (!EngineUtil.isEmpty(attributeId)) {
            boolean updateMode = true;
            GenericValue attributeGV = EntityQuery.use(delegator).from("PeAttribute").where(UtilMisc.toMap("attributeId", attributeId)).queryOne();
            if (EngineUtil.isEmpty(attributeGV)) {
                updateMode = false;
                attributeGV = delegator.makeValue("PeAttribute", "attributeId", attributeId);
            }
            attributeGV.put("attributeName", attributeName);
            attributeGV.put("attributeLabel", attributeLabel);
            attributeGV.put("attributeDescription", attributeDescription);
            delegator.createOrStore(attributeGV);
            success = true;
            result.put("mode", updateMode ? "MODIFY" : "NEW");
        }
        result.put("success", success);

        return result;
    }

    @Override
    public Map<String, Object> saveColorAndStock(Map<String, Object> contextMap) throws Exception {
        Context<String, Object> context = new ContextSupport<>(contextMap);
        boolean success = false;
        Map<String, Object> result = new HashMap<>();

        Delegator delegator = DelegatorFactory.getDelegator("default");

        String colorCode = context.get("colorCode", "");
        String vendorId = context.get("vendorId", "");
        String colorGroup = context.get("colorGroup", "");
        String colorName = context.get("colorName", "");
        String paperTexture = context.get("paperTexture", "");
        String paperWeight = context.get("paperWeight", "");
        String vendorStockId = context.get("vendorStockId", "");
        String vendorStock = context.get("vendorStock", "");
        String attributeId = context.get("attributeId", "");
        if (!EngineUtil.isEmpty(colorCode) && !EngineUtil.isEmpty(vendorId)) {
            boolean updateMode = true;
            GenericValue colorAndStockGV = EntityQuery.use(delegator).from("PeColorVendorStockAssoc").where(UtilMisc.toMap("colorCode", colorCode, "vendorId", vendorId)).queryOne();
            if (EngineUtil.isEmpty(colorAndStockGV)) {
                updateMode = false;
                colorAndStockGV = delegator.makeValue("PeColorVendorStockAssoc", "colorCode", colorCode, "vendorId", vendorId);
            }
            colorAndStockGV.put("colorGroup", colorGroup);
            colorAndStockGV.put("colorName", colorName);
            colorAndStockGV.put("paperTexture", paperTexture);
            colorAndStockGV.put("paperWeight", paperWeight);
            colorAndStockGV.put("vendorStockId", vendorStockId);
            colorAndStockGV.put("vendorStock", vendorStock);
            colorAndStockGV.put("pricingAttributeId", attributeId);
            delegator.createOrStore(colorAndStockGV);
            success = true;
            result.put("mode", updateMode ? "MODIFY" : "NEW");
        }
        result.put("success", success);

        return result;
    }

    @Override
    public Map<String, Object> saveAttributeValue(Map<String, Object> contextMap) throws Exception {
        Context<String, Object> context = new ContextSupport<>(contextMap);
        String[] values = EngineUtil.validateQuantityBreakValues(context.get("qtyBreaks", ""), context.get("values", ""));
        boolean success = false;
        Map<String, Object> result = new HashMap<>();
        if(!EngineUtil.isEmpty(values)) {
            Delegator delegator = DelegatorFactory.getDelegator("default");
            String productId = context.get("productId", "");
            String vendorId = context.get("vendorId", "");
            String attributeId = context.get("attributeId", "");


            if (!EngineUtil.isEmpty(productId) && !EngineUtil.isEmpty(vendorId) && !EngineUtil.isEmpty(attributeId)) {
                boolean updateMode = true;
                GenericValue attributeValueGV = EntityQuery.use(delegator).from("PeAttributeValue").where(UtilMisc.toMap("vendorId", vendorId, "vendorProductId", productId, "attributeId", attributeId)).queryOne();
                if (EngineUtil.isEmpty(attributeValueGV)) {
                    updateMode = false;
                    attributeValueGV = delegator.makeValue("PeAttributeValue", "attributeValueId", delegator.getNextSeqId("PeAttributeValue"));
                    attributeValueGV.put("vendorId", vendorId);
                    attributeValueGV.put("vendorProductId", productId);
                    attributeValueGV.put("attributeId", attributeId);
                }
                attributeValueGV.put("quantityBreak", values[0]);
                attributeValueGV.put("volumePrice", values[1]);
                delegator.createOrStore(attributeValueGV);
                success = true;
                result.put("mode", updateMode ? "MODIFY" : "NEW");
            }

        }

        result.put("success", success);

        return result;
    }

    @Override
    public List<Map<String, Object>> getVendorProducts(String... vendorId) throws Exception {
        List<Map<String, Object>> vendorProducts = new ArrayList<>();
        Delegator delegator = DelegatorFactory.getDelegator("default");

        List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
        if(!EngineUtil.isEmpty(vendorId) && !EngineUtil.isEmpty(vendorId[0])) {
            conditionList.add(EntityCondition.makeCondition("vendorId", EntityOperator.EQUALS, vendorId[0]));
        }

        List<GenericValue> vendorProductGVs = EntityQuery.use(delegator).select("vendorProductId", "vendorId", "productName", "shortName", "description").from("PeVendorProduct").where(EntityCondition.makeCondition(conditionList)).orderBy("vendorId", "vendorProductId").queryList();
        vendorProductGVs.forEach(v -> vendorProducts.add(new HashMap<>(v)));
        return vendorProducts;
    }

    @Override
    public Map<String, Object> getVendorProduct(String vendorProductId, String vendorId) throws Exception {
        Map<String, Object> vendorProduct = new HashMap<>();
        Delegator delegator = DelegatorFactory.getDelegator("default");
        List<GenericValue> vendorProductGVs = EntityQuery.use(delegator).select("vendorProductId", "vendorId", "productName", "shortName", "description").from("PeVendorProduct").where(UtilMisc.toMap("vendorProductId", vendorProductId, "vendorId", vendorId)).queryList();
        if(!EngineUtil.isEmpty(vendorProductGVs)) {
            vendorProduct = new HashMap<>(vendorProductGVs.get(0));
        }
        return vendorProduct;
    }

    @Override
    public Map<String, String> getVirtualProductMap() throws Exception {
        Map<String, String> virtualProductMap = new HashMap<>();
        Delegator delegator = DelegatorFactory.getDelegator("default");
        List<GenericValue> relatedVendorProductGVs = EntityQuery.use(delegator).select("vendorProductId", "vendorId", "virtualProductId").from("PeRelatedVendorProduct").queryList();
        relatedVendorProductGVs.forEach(v -> virtualProductMap.put(v.getString("vendorId") + "|" + v.getString("vendorProductId"), v.getString("virtualProductId")));
        return virtualProductMap;
    }

    @Override
    public Map<String, String> getVendorStockToColorMap() throws Exception {
        Map<String, String> vendorStockToColorMap = new HashMap<>();
        Delegator delegator = DelegatorFactory.getDelegator("default");
        List<GenericValue> vendorStockColorGVs = EntityQuery.use(delegator).select("vendorId", "colorGroup", "colorName", "paperTexture",  "paperWeight", "pricingAttributeId").from("PeColorVendorStockAssoc").queryList();
        vendorStockColorGVs.forEach(v -> vendorStockToColorMap.put(v.getString("colorGroup") + "|" + v.getString("colorName")   + "|" + (EngineUtil.isEmpty(v.get("paperTexture")) ? "" : v.getString("paperTexture")) + "|" + v.getString("paperWeight") + "|" + v.getString("vendorId") , v.getString("pricingAttributeId")));
        return vendorStockToColorMap;
    }

    @Override
    public List<Map<String, Object>> getColors() throws Exception {
        List<Map<String, Object>> colors = new ArrayList<>();
        Delegator delegator = DelegatorFactory.getDelegator("default");
        List<GenericValue> colorGVs = EntityQuery.use(delegator).from("PeColorVendorStockAssoc").orderBy("vendorId", "colorGroup", "colorName", "paperTexture", "paperWeight").queryList();
        colorGVs.forEach(v -> colors.add(new HashMap<>(v)));
        return colors;
    }

    @Override
    public Map<String, Object> getColor(String colorCode, String vendorId) throws Exception {
        Map<String, Object> color = new HashMap<>();
        Delegator delegator = DelegatorFactory.getDelegator("default");
        GenericValue colorGV = EntityQuery.use(delegator).from("PeColorVendorStockAssoc").where("vendorId", vendorId, "colorCode", colorCode).queryOne();
        if(!EngineUtil.isEmpty(colorGV)) {
            color = new HashMap<>(colorGV);
        }
        return color;
    }

    @Override
    public List<Map<String, String>> getPricingData() throws Exception {
        List<Map<String, String>> pricingAttributeValues = new ArrayList<>();
        Delegator delegator = DelegatorFactory.getDelegator("default");
        List<GenericValue> attributeValueGVs = EntityQuery.use(delegator).select("attributeValueId", "vendorId", "vendorProductId", "attributeId", "quantityBreak", "volumePrice").from("PeAttributeValue").queryList();
        attributeValueGVs.forEach(v -> {
            Map<String, String> pricingAttributeValue = new HashMap<>();
            pricingAttributeValue.put("attributeValueId", v.getString("attributeValueId"));
            pricingAttributeValue.put("vendorId", v.getString("vendorId"));
            pricingAttributeValue.put("vendorProductId", v.getString("vendorProductId"));
            pricingAttributeValue.put("attributeId", v.getString("attributeId"));
            pricingAttributeValue.put("quantityBreak", v.getString("quantityBreak"));
            pricingAttributeValue.put("volumePrice", v.getString("volumePrice"));
            pricingAttributeValues.add(pricingAttributeValue);
        });
        return pricingAttributeValues;
    }

    @Override
    public List<Map<String, Object>> getPricingDetails(String vendorProductId, String vendorId) throws Exception {
        Delegator delegator = DelegatorFactory.getDelegator("default");
        SQLProcessor sqlProcessor = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));
        List<Map<String, Object>> priceDetails = new ArrayList<>();
        String sqlCommand1 = "SELECT A.ATTRIBUTE_NAME, A.ATTRIBUTE_LABEL, B.ATTRIBUTE_ID, B.ATTRIBUTE_VALUE_ID, B.QUANTITY_BREAK, B.VOLUME_PRICE FROM PE_ATTRIBUTE A INNER JOIN PE_ATTRIBUTE_VALUE B ON A.ATTRIBUTE_ID = B.ATTRIBUTE_ID WHERE B.VENDOR_ID='" + vendorId + "' AND B.VENDOR_PRODUCT_ID='" + vendorProductId+ "' ORDER BY A.SEQUENCE_NUM, A.ATTRIBUTE_ID";
        try {
            ResultSet rs = sqlProcessor.executeQuery(sqlCommand1);
            if (rs != null) {

                while (rs.next()) {
                    Map<String, Object> priceDetail = new HashMap<>();
                    priceDetail.put("attributeName", rs.getString("ATTRIBUTE_NAME"));
                    priceDetail.put("attributeLabel", rs.getString("ATTRIBUTE_LABEL"));
                    priceDetail.put("attributeId", rs.getString("ATTRIBUTE_ID"));
                    priceDetail.put("attributeValueId", rs.getString("ATTRIBUTE_VALUE_ID"));
                    priceDetail.put("quantityBreak", Arrays.asList(rs.getString("QUANTITY_BREAK").split("\\|")));
                    List<String> _volumePrice = Arrays.asList(rs.getString("VOLUME_PRICE").split("\\|"));
                    List<String> volumePrice = new ArrayList<>();
                    _volumePrice.forEach(p -> {
                        BigDecimal price = new BigDecimal(p);
                        price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
                        volumePrice.add(price.toString());
                    });
                    priceDetail.put("volumePrice", volumePrice);

                    priceDetails.add(priceDetail);

                }
            }
        } finally {
            if(sqlProcessor != null) {
                sqlProcessor.close();
            }
        }

        return priceDetails;
    }

    private static Map<String, Object> groupDataByVendorProduct(List<Map<String, String>> data) throws Exception {
        Map<String, Object> groupedData = new LinkedHashMap<>();
        if(data == null || data.isEmpty()) {
            return groupedData;
        }
        String previousKey = "";
        List<Map<String, Object>> pricingAttributesData = new ArrayList<>();
        for (Map<String, String> e : data) {
            String currentKey = e.get("VENDOR_ID") + "|" + e.get("PRODUCT_ID");
            if(!previousKey.isEmpty() && !currentKey.equals(previousKey)) {
                Map<String, Object> vendorProductMap = new LinkedHashMap<>();
                vendorProductMap.put("VENDOR_ID", previousKey.split("\\|")[0]);
                vendorProductMap.put("PRODUCT_ID", previousKey.split("\\|")[1]);
                vendorProductMap.put("PRICING_ATTRIBUTE", pricingAttributesData);
                groupedData.put(previousKey, vendorProductMap);
                pricingAttributesData = new ArrayList<>();
            }
            Map<String, Object> pricingAttributeMap = new LinkedHashMap<>();
            pricingAttributeMap.put("PRICING_ATTRIBUTE_ID", e.get("PRICING_ATTRIBUTE_ID"));
            pricingAttributeMap.put("PRODUCT_ID", e.get("PRODUCT_ID"));
            pricingAttributeMap.put("VENDOR_ID", e.get("VENDOR_ID"));
            pricingAttributeMap.put("QUANTITY_BREAKS", e.get("QUANTITY_BREAKS"));
            pricingAttributeMap.put("VOLUME_PRICING", e.get("VOLUME_PRICING"));
            pricingAttributesData.add(pricingAttributeMap);
            previousKey = currentKey;
        };
        Map<String, Object> vendorProductMap = new LinkedHashMap<>();
        vendorProductMap.put("VENDOR_ID", previousKey.split("\\|")[0]);
        vendorProductMap.put("PRODUCT_ID", previousKey.split("\\|")[1]);
        vendorProductMap.put("PRICING_ATTRIBUTE", pricingAttributesData);
        groupedData.put(previousKey, vendorProductMap);
        return groupedData;
    }

    @Override
    public List<Map<String, String>> getColorHierarchy(String vendorId) throws Exception {
        List<Map<String, String>> colorVendorStocks = new LinkedList<>();
        List<String> orders = new LinkedList<>();
        Delegator delegator = DelegatorFactory.getDelegator("default");

        orders.add("colorGroup");
        orders.add("colorName");
        orders.add("paperTexture");
        orders.add("paperWeight");
        List<GenericValue> colorHierarchy = EntityQuery.use(delegator).select("colorGroup", "colorName", "paperTexture", "paperWeight").from("PeColorVendorStockAssoc").where(UtilMisc.toMap("vendorId", vendorId)).orderBy(orders).queryList();
        System.err.println("Color hierarchy size = "+colorHierarchy.size());
        colorHierarchy.stream().forEach(raw -> {
            Map<String, String> colorVendorData = new HashMap<>();
            colorVendorData.put("colorGroup", raw.getString("colorGroup"));
            colorVendorData.put("colorName", raw.getString("colorName"));
            colorVendorData.put("paperTexture", raw.getString("paperTexture"));
            colorVendorData.put("paperWeight", raw.getString("paperWeight"));
            colorVendorStocks.add(colorVendorData);
        });
        System.err.println("colorVendorStocks size = "+colorVendorStocks.size());
        return  colorVendorStocks;
    }

}
