/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.product.importer;

import com.envelopes.util.EnvUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.math.Fraction;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
/*CSV RELATED IMPORTS FROM SUPERCSV CLASS */
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.ParseChar;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ParseBigDecimal;
import org.supercsv.cellprocessor.Trim;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.exception.SuperCsvConstraintViolationException;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class ProductImporter {
    public static final String module = ProductImporter.class.getName();

    private Delegator delegator = null;
    private CellProcessor[] processors;
    private List<Map<String, Object>> products = new ArrayList<>();
    private GenericValue userLogin = null;
    private String entityException = null;
    private boolean success = false;

    public ProductImporter(Delegator delegator, HttpServletRequest request) {
        this.delegator = delegator;
        if(request != null) {
            this.userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
        } else {
            try {
                this.userLogin = EnvUtil.getSystemUser(this.delegator);
            } catch(Exception e) {
                Debug.logError(e, "Could not get system user.", module);
            }
        }
    }

    /**
     * Set the CSV Reader Headers
     * @param headers
     */
    public void setProcessors(String[] headers) {
        this.processors = ProductEnum.getProcessor(headers);
    }

    /**
     * If product data already exists, apply it
     * @param data
     */
    public void setProductData(List<Map<String, Object>> data) {
        this.products = data;
    }

    /**
     * Read the CSV file and get the product data to a List
     * @param fileLocation
     * @throws Exception
     */
    public void setProductData(String fileLocation) throws Exception {
        ICsvMapReader mapReader = null;
        try {
            if (UtilValidate.isNotEmpty(fileLocation)) {
                mapReader = new CsvMapReader(new FileReader(fileLocation), CsvPreference.STANDARD_PREFERENCE);

                //the header columns are used as the keys to the Map
                final String[] headers = mapReader.getHeader(true);

                setProcessors(headers);
                Map<String, Object> dataMap;

                while ((dataMap = mapReader.read(headers, this.processors)) != null) {
                    this.products.add(dataMap);
                }
            }
        } catch (Exception e) {
            Debug.logError(e, "Error: " + e.getMessage(), module);
            entityException = e.getMessage();
        } finally {
            try {
                if (mapReader != null) {
                    mapReader.close();
                }
            } catch (IOException e) {
                Debug.logError(e, "Error: " + e.getMessage(), module);
            }
        }
    }

    public List<Map<String, Object>> getProductData() {
        return this.products;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getError() {
        return this.entityException;
    }

    public void insertProduct(Map<String, Object> newProductData) {
        boolean beganTransaction = false;
        this.success = false;
        try {
            beganTransaction = TransactionUtil.begin();
            List<GenericValue> toBeStored = new ArrayList<>();

            //check for parent product
            String parentProductId = (String) newProductData.get(ProductEnum.getProductEnum("PARENT_CODE").name());
            if (EntityQuery.use(this.delegator).from("Product").where("productId", parentProductId).queryOne() == null) {
                toBeStored.add(createProductEntity(newProductData, true));
            }

            //add product
            toBeStored.add(createProductEntity(newProductData, false));
            toBeStored.add(createProductWebSiteEntity(newProductData));

            //add vendor product
            toBeStored.addAll(createVendorProductEntities(newProductData));

            //add product features
            toBeStored.addAll(createProductFeatureEntities(newProductData));

            //add product price
            toBeStored.addAll(createProductPriceEntities(newProductData, false));

            //add product category rollup
            toBeStored.addAll(createProductCategoryRollup(newProductData));
            toBeStored.addAll(createProductStyleRollup(newProductData));

            this.delegator.storeAll(toBeStored);
            this.success = true;
        } catch (GenericEntityException e) {
            try {
                TransactionUtil.rollback(beganTransaction, "Error saving product information", e);
                entityException = e.getMessage();
            } catch (GenericEntityException e2) {
                Debug.logError(e2, "Could not rollback transaction: " + e2.toString(), module);
            }
        } finally {
            try {
                TransactionUtil.commit(beganTransaction);
            } catch (GenericEntityException e) {
                Debug.logError(e, "Could not save new product information", module);
            }
        }
    }

    public void updateProduct(Map<String, Object> product) {
        boolean beganTransaction = false;
        this.success = false;
        try {
            beganTransaction = TransactionUtil.begin();
            List<GenericValue> toBeStored = new ArrayList<>();

            //add product price
            toBeStored.addAll(createProductPriceEntities(product, true));

            for(GenericValue price : toBeStored) {
                this.delegator.createOrStore(price);
            }

            //this.delegator.storeAll(toBeStored);
            this.success = true;
        } catch (GenericEntityException e) {
            try {
                TransactionUtil.rollback(beganTransaction, "Error saving product information", e);
                entityException = e.getMessage();
            } catch (GenericEntityException e2) {
                Debug.logError(e2, "Could not rollback transaction: " + e2.toString(), module);
            }
        } finally {
            try {
                TransactionUtil.commit(beganTransaction);
            } catch (GenericEntityException e) {
                Debug.logError(e, "Could not save new product information", module);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private GenericValue createProductEntity(Map<String, Object> newProductData, boolean isVirtual) throws GenericEntityException {
        GenericValue productGV = EntityQuery.use(this.delegator).from("Product").where("productId", newProductData.get("CODE")).queryOne();

        if (UtilValidate.isEmpty(productGV)) {
            productGV = this.delegator.makeValue("Product");
        }

        //Create a map of just the Product related information
        Map<String, Object> productMap = new HashMap<>();

        Iterator keysIter = newProductData.entrySet().iterator();
        while(keysIter.hasNext()) {
            Map.Entry<String, Object> pairs = (Map.Entry<String, Object>) keysIter.next();
            ProductEnum enumVal = ProductEnum.getProductEnum(pairs.getKey());
            if (enumVal != ProductEnum.UNKNOWN && enumVal.getType().equalsIgnoreCase("Product") && UtilValidate.isNotEmpty(pairs.getValue())) {
                //if STYLE column, there may be multiple values
                if(enumVal.name().equalsIgnoreCase("STYLE") && ((String) pairs.getValue()).contains(",")) {
                    String[] styles = ((String) pairs.getValue()).split(",");
                    productMap.put(enumVal.getField(), styles[0].trim());
                } else {
                    productMap.put(enumVal.getField(), pairs.getValue());
                }

                //certain fields need duplicate values in other columns
                //productName:internalName
                //productWeight:shippingWeight
                if(enumVal.getField().equalsIgnoreCase("productName")) {
                    productMap.put("internalName", pairs.getValue());
                } else if(enumVal.getField().equalsIgnoreCase("productWeight")) {
                    productMap.put("shippingWeight", pairs.getValue());
                }
            } else if (enumVal != ProductEnum.UNKNOWN && enumVal.getField().equalsIgnoreCase("SIZE") && UtilValidate.isNotEmpty(pairs.getValue())) {
                getWidthAndHeight(productMap, (String) pairs.getValue());
            }
        }

        //if virtual, make the productId the parent product id and null out parent product id
        if(isVirtual) {
            productMap.put("productId", productMap.remove("parentProductId"));
        }
        productMap.put("isVirtual", isVirtual ? "Y" : "N");
        productMap.put("isVariant", isVirtual ? "N" : "Y");

        //defaults
        productMap.put("includeInPromotions", "Y");
        newProductData.put("heightUomId", "LEN_in");
        newProductData.put("widthUomId", "LEN_in");
        newProductData.put("createdDate", UtilDateTime.nowTimestamp());
        newProductData.put("createdByUserLogin", this.userLogin.getString("userLoginId"));

        return EnvUtil.insertGenericValueData(this.delegator, productGV, productMap, false);
    }

    @SuppressWarnings("unchecked")
    private GenericValue createProductWebSiteEntity(Map<String, Object> product) throws GenericEntityException {
        GenericValue productWSGV = this.delegator.makeValue("ProductWebSite");

        productWSGV.put("productId", (String) product.get(ProductEnum.getProductEnum("CODE").name()));
        String webSites = (String) product.get(ProductEnum.getProductEnum("WEBSITE").name());
        if(UtilValidate.isNotEmpty(webSites)) {
            String[] webSiteIds = webSites.trim().split(",");
            for(String webSiteId : webSiteIds) {
                productWSGV.put(webSiteId.trim(), "Y");
            }
        } else {
            productWSGV.put("envelopes", "Y");
            productWSGV.put("ae", "Y");
        }

        return productWSGV;
    }

    @SuppressWarnings("unchecked")
    private List<GenericValue> createProductFeatureEntities(Map<String, Object> product) throws GenericEntityException {
        List<GenericValue> featureList = new ArrayList<>();

        LinkedHashMap<String, String> pocketStyle = new LinkedHashMap<>();

        //Create all features and features associated directly to products
        Iterator keysIter = product.entrySet().iterator();
        while (keysIter.hasNext()) {
            Map.Entry<String, Object> pairs = (Map.Entry<String, Object>) keysIter.next();
            ProductEnum enumVal = ProductEnum.getProductEnum(pairs.getKey());
            if (enumVal != ProductEnum.UNKNOWN && enumVal.getType().equalsIgnoreCase("ProductFeature") && enumVal.getFieldAppl() != null && UtilValidate.isNotEmpty(pairs.getValue())) {
                String value = ((String) pairs.getValue()).trim();
                if(enumVal.isMultiField()) {
                    String[] values = value.split("\\|");
                    for (String subValue : values) {
                        if (UtilValidate.isNotEmpty(subValue.trim())) {
                            subValue = subValue.trim();
                            GenericValue productFeature = addFeature(featureList, enumVal.getField(), subValue, true);
                            featureList.add(this.delegator.makeValue("ProductFeatureAppl", UtilMisc.toMap("productId", (String) product.get(ProductEnum.getProductEnum("CODE").name()), "productFeatureId", productFeature.getString("productFeatureId"), "productFeatureApplTypeId", enumVal.getFieldAppl() + "_FEATURE", "fromDate", UtilDateTime.nowTimestamp())));
                            if(enumVal.getField().equalsIgnoreCase("POCKET_STYLE")) {
                                pocketStyle.put(subValue, productFeature.getString("productFeatureId"));
                            }
                        }
                    }
                } else {
                    GenericValue productFeature = addFeature(featureList, enumVal.getField(), value);
                    featureList.add(this.delegator.makeValue("ProductFeatureAppl", UtilMisc.toMap("productId", (String) product.get(ProductEnum.getProductEnum("CODE").name()), "productFeatureId", productFeature.getString("productFeatureId"), "productFeatureApplTypeId", enumVal.getFieldAppl() + "_FEATURE", "fromDate", UtilDateTime.nowTimestamp())));
                }
            }
        }

        //Create all features associated to other features (subfeatures)
        keysIter = product.entrySet().iterator();
        while (keysIter.hasNext()) {
            Map.Entry<String, Object> pairs = (Map.Entry<String, Object>) keysIter.next();
            ProductEnum enumVal = ProductEnum.getProductEnum(pairs.getKey());
            if (enumVal != ProductEnum.UNKNOWN && enumVal.getType().equalsIgnoreCase("ProductFeature") && enumVal.getParent() != null && UtilValidate.isNotEmpty(pairs.getValue())) {
                String value = ((String) pairs.getValue()).trim();
                if(enumVal.isMultiField()) {
                    String[] values = value.split("\\|");
                    for(int i = 0; i < values.length; i++) {
                        if (UtilValidate.isNotEmpty(values[i].trim())) {
                            String subValue = values[i].trim();
                            if (subValue.contains(";")) {
                                String[] nestedValues = subValue.split(";");
                                for (String nestedValue : nestedValues) {
                                    if (UtilValidate.isNotEmpty(nestedValue.trim())) {
                                        nestedValue = nestedValue.trim();
                                        GenericValue productFeature = addFeature(featureList, enumVal.getField(), nestedValue, true);
                                        if(enumVal.getParent().equalsIgnoreCase("POCKET_STYLE") && UtilValidate.isNotEmpty(pocketStyle)) {
                                            featureList.add(this.delegator.makeValue("ProductFeatureAssoc", UtilMisc.toMap("productId", (String) product.get(ProductEnum.getProductEnum("CODE").name()), "productFeatureId", pocketStyle.get(pocketStyle.keySet().toArray()[i]), "productFeatureIdTo", productFeature.getString("productFeatureId"), "productAssocTypeId", "SUB_FEATURE")));
                                        }
                                    }
                                }
                            } else {
                                GenericValue productFeature = addFeature(featureList, enumVal.getField(), subValue, true);
                                if(enumVal.getParent().equalsIgnoreCase("POCKET_STYLE") && UtilValidate.isNotEmpty(pocketStyle)) {
                                    featureList.add(this.delegator.makeValue("ProductFeatureAssoc", UtilMisc.toMap("productId", (String) product.get(ProductEnum.getProductEnum("CODE").name()), "productFeatureId", pocketStyle.get(pocketStyle.keySet().toArray()[i]), "productFeatureIdTo", productFeature.getString("productFeatureId"), "productAssocTypeId", "SUB_FEATURE")));
                                }
                            }
                        }
                    }
                }
            }
        }

        //Create the vendorProductfeature assoc
        if(UtilValidate.isNotEmpty(pocketStyle)) {
            Object[] pocketKeys = pocketStyle.keySet().toArray();
            ProductEnum enumVal = ProductEnum.getProductEnum("VENDOR_SKU");
            String vendorSKUs = (String) product.get(ProductEnum.getProductEnum("VENDOR_SKU").name());

            if(enumVal.isMultiField()) {
                String[] values = vendorSKUs.split("\\|");
                for (int i = 0; i < values.length; i++) {
                    for (Object key : pocketKeys) {
                        Debug.logInfo(values[i] + ":" + pocketStyle.get(key.toString()), module);
                        //addVendorFeatureAppl(featureList, values[i], pocketStyle.get(key.toString()), true);
                    }
                }
            }
        }

        return featureList;
    }

    @SuppressWarnings("unchecked")
    private List<GenericValue> createVendorProductEntities(Map<String, Object> product) throws GenericEntityException {
        List<GenericValue> vendorProductList = new ArrayList<>();

        Iterator keysIter = product.entrySet().iterator();
        while (keysIter.hasNext()) {
            Map.Entry<String, Object> pairs = (Map.Entry<String, Object>) keysIter.next();
            ProductEnum enumVal = ProductEnum.getProductEnum(pairs.getKey());
            if (enumVal != ProductEnum.UNKNOWN && enumVal.getType().equalsIgnoreCase("VendorProduct") && UtilValidate.isNotEmpty(pairs.getValue())) {
                String value = ((String) pairs.getValue()).trim();
                if(enumVal.isMultiField()) {
                    String[] values = value.split("\\|");
                    for (String nestedValue : values) {
                        if (UtilValidate.isNotEmpty(nestedValue.trim())) {
                            nestedValue = nestedValue.trim();
                            vendorProductList.add(this.delegator.makeValue("VendorProduct", UtilMisc.toMap("productId", (String) product.get(ProductEnum.getProductEnum("CODE").name()), "productStoreGroupId", "_NA_", "vendorProductId", nestedValue, "vendorPartyId", (String) product.get(ProductEnum.getProductEnum("VENDOR").name()), "fromDate", UtilDateTime.nowTimestamp(), "pricingConfig", (String) product.get(ProductEnum.getProductEnum("PRICE").name()))));
                        }
                    }
                }
            }
        }

        return vendorProductList;
    }

    private GenericValue addFeature(List<GenericValue> featureList, String field, String value) throws GenericEntityException {
        return addFeature(featureList, field, value, false);
    }
    private GenericValue addFeature(List<GenericValue> featureList, String field, String value, boolean store) throws GenericEntityException {
        GenericValue productFeature = getProductFeature(field, value);
        if (productFeature == null) {
            productFeature = createProductFeature(field, value);
            if(store) {
                this.delegator.create(productFeature);
            } else {
                featureList.add(productFeature);
            }
        }

        return productFeature;
    }

    private GenericValue addVendorFeatureAppl(List<GenericValue> featureList, String vendorProductId, String featureId, boolean store) throws GenericEntityException {
        GenericValue productFeatureAppl = getVendorProductFeatureAppl(vendorProductId, featureId);
        if (productFeatureAppl == null) {
            productFeatureAppl = createVendorProductFeatureAppl(vendorProductId, featureId);
            if(store) {
                this.delegator.create(productFeatureAppl);
            } else {
                featureList.add(productFeatureAppl);
            }
        }

        return productFeatureAppl;
    }

    private List<GenericValue> createProductPriceEntities(Map<String, Object> product, boolean update) throws GenericEntityException {
        List<GenericValue> productPriceList = new ArrayList<>();

        //create price data
        String pricing = (String) product.get(ProductEnum.getProductEnum("PRICE").name());
        if (UtilValidate.isNotEmpty(pricing)) {
            String[] eachPrice = pricing.split(";");

            for (String price : eachPrice) {
                String[] qtyPrice = price.split(",");
                if (qtyPrice.length == 3) {
                    GenericValue priceGV = EntityQuery.use(this.delegator).from("ProductPrice").where("productId", product.get(ProductEnum.getProductEnum("CODE").name()), "quantity", Long.valueOf(qtyPrice[0].trim()), "colors", Long.valueOf(qtyPrice[1].trim())).queryFirst();
                    if (update && priceGV != null) {
                        priceGV.put("price", new BigDecimal(qtyPrice[2].trim()));
                        productPriceList.add(priceGV);
                    } else {
                        productPriceList.add(
                                EnvUtil.insertGenericValueData(this.delegator, this.delegator.makeValue(ProductEnum.PRICE.getType()), UtilMisc.<String, Object>toMap(
                                        "productId", product.get(ProductEnum.getProductEnum("CODE").name()),
                                        "createdDate", UtilDateTime.nowTimestamp(),
                                        "createdByUserLogin", this.userLogin.getString("userLoginId"),
                                        "fromDate", UtilDateTime.nowTimestamp(),
                                        "currencyUomId", "USD",
                                        "productStoreGroupId", "_NA_",
                                        "productPricePurposeId", "PURCHASE",
                                        "productPriceTypeId", "DEFAULT_PRICE",
                                        "quantity", qtyPrice[0].trim(),
                                        "colors", qtyPrice[1].trim(),
                                        "price", qtyPrice[2].trim()), true)
                        );
                    }
                }
            }
        }

        return productPriceList;
    }

    private List<GenericValue> createProductCategoryRollup(Map<String, Object> product) throws GenericEntityException {
        List<GenericValue> productCategoryList = new ArrayList<>();
        if (getProductCategory((String) product.get(ProductEnum.getProductEnum("CATEGORY").name()), "CATALOG_CATEGORY") == null) {
            productCategoryList.add(createProductCategoryRollup((String) product.get(ProductEnum.getProductEnum("CATEGORY").name()), (String) product.get(ProductEnum.getProductEnum("CODE").name())));
            productCategoryList.add(createProductCategoryRollup((String) product.get(ProductEnum.getProductEnum("CATEGORY").name()), (String) product.get(ProductEnum.getProductEnum("PARENT_CODE").name())));
        }

        return productCategoryList;
    }

    private List<GenericValue> createProductStyleRollup(Map<String, Object> product) throws GenericEntityException {
        List<GenericValue> productCategoryList = new ArrayList<>();
        if(((String) product.get(ProductEnum.getProductEnum("STYLE").name())).contains(",")) {
            String[] styles = ((String) product.get(ProductEnum.getProductEnum("STYLE").name())).split(",");
            for(int i = 0; i < styles.length; i++) {
                if (getProductCategory((String) product.get(ProductEnum.getProductEnum("STYLE").name()), "CATALOG_CATEGORY") == null) {
                    productCategoryList.add(createProductCategoryRollup(styles[i].trim(), (String) product.get(ProductEnum.getProductEnum("CODE").name())));
                }
            }

        }

        return productCategoryList;
    }

    /*
	 * Check if feature exists
	 */
    private GenericValue getProductFeature(String productFeatureTypeId, String description) throws GenericEntityException {
        return EntityQuery.use(this.delegator).from("ProductFeature").where("productFeatureCategoryId", "10000", "productFeatureTypeId", productFeatureTypeId, "description", description).queryFirst();
    }

    /*
	 * Create product feature
	 */
    private GenericValue createProductFeature(String productFeatureTypeId, String description) throws GenericEntityException {
        return this.delegator.makeValue("ProductFeature", UtilMisc.toMap("productFeatureId", this.delegator.getNextSeqId("ProductFeature"), "productFeatureCategoryId", "10000", "productFeatureTypeId", productFeatureTypeId, "description", description));
    }

    /*
     * Create Category Rollup
     */
    private GenericValue createProductCategoryRollup(String productCategoryId, String productId) {
        return this.delegator.makeValue("ProductCategoryMember", UtilMisc.toMap("productCategoryId", productCategoryId, "productId", productId, "fromDate", UtilDateTime.nowTimestamp()));
    }

    /*
	 * check if category exists
	 */
    private GenericValue getProductCategory(String productCategoryId, String productCategoryTypeId) throws GenericEntityException {
        return EntityQuery.use(this.delegator).from("ProductCategory").where("productCategoryId", productCategoryId, "productCategoryTypeId", productCategoryTypeId).queryFirst();
    }

    /*
	 * Check if feature exists
	 */
    private GenericValue getVendorProductFeatureAppl(String vendorProductId, String featureId) throws GenericEntityException {
        return EntityQuery.use(this.delegator).from("VendorProductFeatureAppl").where("vendorProductId", vendorProductId, "productFeatureId", featureId).queryFirst();
    }

    /*
	 * Create product feature
	 */
    private GenericValue createVendorProductFeatureAppl(String vendorProductId, String featureId) throws GenericEntityException {
        return this.delegator.makeValue("VendorProductFeatureAppl", UtilMisc.toMap("vendorProductId", vendorProductId, "productFeatureId", featureId, "productFeatureApplTypeId", "STANDARD_FEATURE", "fromDate", UtilDateTime.nowTimestamp()));
    }

    /*
	 * Get width and height
	 */
    private void getWidthAndHeight(Map<String, Object> productMap, String size) {
        BigDecimal width = null;
        BigDecimal height = null;
        BigDecimal depth = null;

        if(UtilValidate.isNotEmpty(size)) {
            size = size.toUpperCase();
            String[] sizeList = size.split("X");
            if(isFraction(sizeList[0])) {
                width = new BigDecimal(Fraction.getFraction(sizeList[0].trim()).doubleValue());
            } else {
                width = new BigDecimal(sizeList[0].trim().replaceAll("[^\\d.]", ""));
            }

            if(sizeList.length > 1) {
                if(isFraction(sizeList[1])) {
                    height = new BigDecimal(Fraction.getFraction(sizeList[1].trim()).doubleValue());
                } else {
                    height = new BigDecimal(sizeList[1].trim().replaceAll("[^\\d.]", ""));
                }
            }

            if(sizeList.length > 2) {
                if(isFraction(sizeList[1])) {
                    height = new BigDecimal(Fraction.getFraction(sizeList[2].trim()).doubleValue());
                } else {
                    height = new BigDecimal(sizeList[2].trim().replaceAll("[^\\d.]", ""));
                }
            }
        }

        productMap.put("productWidth", width);
        productMap.put("productHeight", height);
        productMap.put("productDepth", depth);
    }

    private boolean isFraction(String value) {
        if(UtilValidate.isNotEmpty(value) && value.trim().contains("/")) {
            return true;
        }

        return false;
    }
}