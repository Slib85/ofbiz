package com.bigname.quote.calculator;

import com.envelopes.dialogtech.CallDetailReport;
import com.envelopes.party.PartyHelper;
import com.envelopes.printing.JobGoodToGoRequest;
import com.envelopes.product.ProductHelper;
import com.envelopes.quote.QuoteHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.jdbc.SQLProcessor;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.service.LocalDispatcher;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.quote.AlwaysQuoteMode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Manu on 6/1/2017.
 */
public class CalculatorHelper {
    public static final String module = CalculatorHelper.class.getName();
    public static final BigDecimal discountAmount = new BigDecimal(0).setScale(2, RoundingMode.FLOOR);

    public static List<GenericValue> getAllStyles(Delegator delegator) throws GenericEntityException {
        return EntityQuery.use(delegator).from("QcStyle").orderBy("styleGroupId", "styleName").queryList();
    }

    public static List<GenericValue> getAllStyleGroups(Delegator delegator) throws GenericEntityException {
        return EntityQuery.use(delegator).from("QcStyleGroup").orderBy("createdStamp DESC").queryList();
    }

    public static List<GenericValue> getAllStocks(Delegator delegator) throws GenericEntityException {
        return EntityQuery.use(delegator).from("QcStock").orderBy("createdStamp DESC").queryList();
    }

    public static List<GenericValue> getAllStockTypes(Delegator delegator) throws GenericEntityException {
        return EntityQuery.use(delegator).from("QcStockType").orderBy("stockTypeName ASC").queryList();
    }

    public static List<GenericValue> getAllMaterialTypes(Delegator delegator) throws GenericEntityException {
        return EntityQuery.use(delegator).from("QcMaterialType").orderBy("materialTypeName ASC").queryList();
    }

    public static List<GenericValue> getAllVendors(Delegator delegator) throws GenericEntityException {
        return EntityQuery.use(delegator).from("QcVendor").orderBy("vendorName ASC").queryList();
    }

    public static boolean isIdUnique(Delegator delegator, String id, String entityName) throws Exception {
        switch (entityName.toLowerCase()) {
            case "style":
                return UtilValidate.isEmpty(getStyle(delegator, id, true));
            case "stylegroup":
                return UtilValidate.isEmpty(getStyleGroup(delegator, id, true));
            case "stock":
                return UtilValidate.isEmpty(getStock(delegator, id, true));
            case "stocktype":
                return UtilValidate.isEmpty(getStockType(delegator, id, true));
            case "materialtype":
                return UtilValidate.isEmpty(getMaterialType(delegator, id, true));
            case "vendor":
                return UtilValidate.isEmpty(getVendor(delegator, id, true));
            default:
                return false;
        }
    }

    public static boolean hasItemUpcharge(Delegator delegator, String styleId) throws GenericEntityException {
        List<GenericValue> itemUpcharges = EntityQuery.use(delegator).from("QcStyleAttributeValue").where("styleId", styleId).queryList();
        return UtilValidate.isNotEmpty(itemUpcharges);
    }

    public static Map<String, Object> getStyle(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
        return getStyle(delegator, (String)context.get("id"), true);
    }

    public static Map<String, Object> getStyle(Delegator delegator, String styleId, boolean... ignoreNotFoundException) throws GenericEntityException {
        Map<String, Object> result = new HashMap<>();
        GenericValue style = EntityQuery.use(delegator).from("QcStyle").where(UtilMisc.toMap("styleId", styleId)).queryOne();
        if(UtilValidate.isNotEmpty(style)) {
            result.putAll(style);
            result.put("hasUpcharge", hasItemUpcharge(delegator, styleId));
        }

        if(!toBoolean(ignoreNotFoundException) && UtilValidate.isEmpty(style)) {
            throw new GenericEntityException("Style with the given id won't exists");
        }

        return result;
    }

    public static Map<String, Object> getStyleGroup(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
        return getStyleGroup(delegator, (String)context.get("id"), true);
    }

    public static Map<String, Object> getStyleGroup(Delegator delegator, String styleGroupId, boolean... ignoreNotFoundException) throws GenericEntityException {
        Map<String, Object> result = new HashMap<>();
        GenericValue styleGroup = EntityQuery.use(delegator).from("QcStyleGroup").where(UtilMisc.toMap("styleGroupId", styleGroupId)).queryOne();
        if(UtilValidate.isNotEmpty(styleGroup)) {
            result.putAll(styleGroup);
        }
        if(!toBoolean(ignoreNotFoundException) && UtilValidate.isEmpty(styleGroup)) {
            throw new GenericEntityException("Style Group with the given id won't exists");
        }
        return result;
    }

    public static Map<String, Object> getStock(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
        return getStock(delegator, (String)context.get("id"), true);
    }

    public static Map<String, Object> getStock(Delegator delegator, String stockId, boolean... ignoreNotFoundException) throws GenericEntityException {
        Map<String, Object> result = new HashMap<>();
        GenericValue stock = EntityQuery.use(delegator).from("QcStock").where(UtilMisc.toMap("stockId", stockId)).queryOne();
        if(UtilValidate.isNotEmpty(stock)) {
            result.putAll(stock);
        }
        if(!toBoolean(ignoreNotFoundException) && UtilValidate.isEmpty(stock)) {
            throw new GenericEntityException("Stock with the given id won't exists");
        }
        return result;
    }

    public static Map<String, Object> getStockType(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
        return getStockType(delegator, (String)context.get("id"), true);
    }

    public static Map<String, Object> getStockType(Delegator delegator, String stockTypeId, boolean... ignoreNotFoundException) throws GenericEntityException {
        Map<String, Object> result = new HashMap<>();
        GenericValue stockType = EntityQuery.use(delegator).from("QcStockType").where(UtilMisc.toMap("stockTypeId", stockTypeId)).queryOne();
        if(UtilValidate.isNotEmpty(stockType)) {
            result.putAll(stockType);
        }
        if(!toBoolean(ignoreNotFoundException) && UtilValidate.isEmpty(stockType)) {
            throw new GenericEntityException("Stock Type with the given id won't exists");
        }
        return result;
    }

    public static Map<String, Object> getMaterialType(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
        return getMaterialType(delegator, (String)context.get("id"), true);
    }

    public static Map<String, Object> getMaterialType(Delegator delegator, String materialTypeId, boolean... ignoreNotFoundException) throws GenericEntityException {
        Map<String, Object> result = new HashMap<>();
        GenericValue materialType = EntityQuery.use(delegator).from("QcMaterialType").where(UtilMisc.toMap("materialTypeId", materialTypeId)).queryOne();
        if(UtilValidate.isNotEmpty(materialType)) {
            result.putAll(materialType);
        }
        if(!toBoolean(ignoreNotFoundException) && UtilValidate.isEmpty(materialType)) {
            throw new GenericEntityException("Material Type with the given id won't exists");
        }
        return result;
    }

    public static Map<String, Object> getVendor(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
        return getVendor(delegator, (String)context.get("id"), true);
    }

    public static Map<String, Object> getVendor(Delegator delegator, String vendorId, boolean... ignoreNotFoundException) throws GenericEntityException {
        Map<String, Object> result = new HashMap<>();

        GenericValue vendor = EntityQuery.use(delegator).from("QcVendor").where(UtilMisc.toMap("vendorId", vendorId)).queryOne();
        if(UtilValidate.isNotEmpty(vendor)) {
            result.putAll(vendor);
        }
        if(!toBoolean(ignoreNotFoundException) && UtilValidate.isEmpty(vendor)) {
            throw new GenericEntityException("Vendor with the given id won't exists");
        }
        return result;
    }

    public static Map<String,Object> saveStyle(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
        Map<String, Object> result = new HashMap<>();
        boolean updateMode = true;
        String styleId = (String)context.get("styleId");
        if(styleId != null) {
            styleId = styleId.toUpperCase();
        }
        if(UtilValidate.isEmpty(styleId) || !styleId.replaceAll("^[A-Z0-9-]+$", "").isEmpty()) {
            throw new GenericEntityException("Invalid Style Id: " + styleId + ", use letters, numbers and hyphens only");
        }
        GenericValue style = EntityQuery.use(delegator).from("QcStyle").where(UtilMisc.toMap("styleId", styleId)).queryOne();

        if(UtilValidate.isEmpty(style)) {
            updateMode = false;
            style = delegator.makeValue("QcStyle", "styleId", styleId);
        }

        style.put("styleName", context.get("name"));
        style.put("styleShortName", context.get("shortName"));
        if(UtilValidate.isNotEmpty(context.get("styleGroupId"))) {
            style.put("styleGroupId", context.get("styleGroupId"));
        }
        style.put("styleDescription", context.get("description"));
        if(UtilValidate.isNotEmpty(context.get("activeFlag"))) {
            style.put("activeFlag", converttoIndicator((String) context.get("activeFlag"), "N"));
        }
        result.put("updateMode", updateMode);
        result.put("id", styleId);
        result.putAll(delegator.createOrStore(style));
        return result;
    }

    public static Map<String,Object> saveStyleGroup(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
        Map<String, Object> result = new HashMap<>();
        boolean updateMode = true;
        String styleGroupId = (String)context.get("styleGroupId");
        if(styleGroupId != null) {
            styleGroupId = styleGroupId.toUpperCase();
        }
        if(UtilValidate.isEmpty(styleGroupId) || !styleGroupId.replaceAll("^[A-Z0-9_]+$", "").isEmpty()) {
            throw new GenericEntityException("Invalid Style Group Id: " + styleGroupId + ", use letters, numbers and hyphens only");
        }
        GenericValue styleGroup = EntityQuery.use(delegator).from("QcStyleGroup").where(UtilMisc.toMap("styleGroupId", styleGroupId)).queryOne();

        if(UtilValidate.isEmpty(styleGroup)) {
            updateMode = false;
            styleGroup = delegator.makeValue("QcStyleGroup", "styleGroupId", styleGroupId);
        }

        styleGroup.put("styleGroupName", context.get("name"));
        if(UtilValidate.isNotEmpty(context.get("activeFlag"))) {
            styleGroup.put("activeFlag", converttoIndicator((String) context.get("activeFlag"), "N"));
        }
        result.put("updateMode", updateMode);
        result.put("id", styleGroupId);
        result.putAll(delegator.createOrStore(styleGroup));
        if(!updateMode) {
            try {
                assignPricingAttributes(delegator, styleGroupId);
            } catch (Exception e) {
                throw new GenericEntityException(e);
            }
        }
        return result;
    }

    public static Map<String,Object> saveStock(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
        Map<String, Object> result = new HashMap<>();
        boolean updateMode = true;
        String stockId = (String)context.get("stockId");
        if(stockId != null) {
            stockId = stockId.toUpperCase();
        }
        if(UtilValidate.isEmpty(stockId) || !stockId.replaceAll("^[A-Z0-9]+$", "").isEmpty()) {
            throw new GenericEntityException("Invalid Stock Id: " + stockId + ", use letters and numbers only");
        }
        GenericValue stock = EntityQuery.use(delegator).from("QcStock").where(UtilMisc.toMap("stockId", stockId)).queryOne();

        if(UtilValidate.isEmpty(stock)) {
            updateMode = false;
            stock = delegator.makeValue("QcStock", "stockId", stockId);
        }

        stock.put("stockName", context.get("name"));
        stock.put("stockTypeId", context.get("stockTypeId"));
        stock.put("stockDescription", context.get("description"));
        if(UtilValidate.isNotEmpty(context.get("activeFlag"))) {
            stock.put("activeFlag", converttoIndicator((String) context.get("activeFlag"), "N"));
        }
        result.put("updateMode", updateMode);
        result.put("id", stockId);
        result.putAll(delegator.createOrStore(stock));
        return result;
    }

    public static Map<String,Object> saveStockType(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
        Map<String, Object> result = new HashMap<>();
        boolean updateMode = true;
        String stockTypeId = (String)context.get("stockTypeId");
        if(stockTypeId != null) {
            stockTypeId = stockTypeId.toUpperCase();
        }
        if(UtilValidate.isEmpty(stockTypeId) || !stockTypeId.replaceAll("^[A-Z0-9_]+$", "").isEmpty()) {
            throw new GenericEntityException("Invalid Stock Type Id: " + stockTypeId + ", use letters, numbers and underscores only");
        }
        GenericValue stockType = EntityQuery.use(delegator).from("QcStockType").where(UtilMisc.toMap("stockTypeId", stockTypeId)).queryOne();

        if(UtilValidate.isEmpty(stockType)) {
            updateMode = false;
            stockType = delegator.makeValue("QcStockType", "stockTypeId", stockTypeId);
        }

        stockType.put("stockTypeName", context.get("name"));
        stockType.put("materialTypeId", context.get("materialTypeId"));
        stockType.put("stockTypeDescription", context.get("description"));
        if(UtilValidate.isNotEmpty(context.get("activeFlag"))) {
            stockType.put("activeFlag", converttoIndicator((String) context.get("activeFlag"), "N"));
        }
        result.put("updateMode", updateMode);
        result.put("id", stockTypeId);
        result.putAll(delegator.createOrStore(stockType));
        return result;
    }

    public static Map<String,Object> saveMaterialType(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
        Map<String, Object> result = new HashMap<>();
        boolean updateMode = true;
        String materialTypeId = (String)context.get("materialTypeId");
        if(materialTypeId != null) {
            materialTypeId = materialTypeId.toUpperCase();
        }
        if(UtilValidate.isEmpty(materialTypeId) || !materialTypeId.replaceAll("^[A-Z0-9_]+$", "").isEmpty()) {
            throw new GenericEntityException("Invalid Material Type Id: " + materialTypeId + ", use letters, numbers and underscores only");
        }
        GenericValue materialType = EntityQuery.use(delegator).from("QcMaterialType").where(UtilMisc.toMap("materialTypeId", materialTypeId)).queryOne();

        if(UtilValidate.isEmpty(materialType)) {
            updateMode = false;
            materialType = delegator.makeValue("QcMaterialType", "materialTypeId", materialTypeId);
        }

        materialType.put("materialTypeName", context.get("name"));
        materialType.put("materialTypeDescription", context.get("description"));
        if(UtilValidate.isNotEmpty(context.get("activeFlag"))) {
            materialType.put("activeFlag", converttoIndicator((String) context.get("activeFlag"), "N"));
        }
        result.put("updateMode", updateMode);
        result.put("id", materialTypeId);
        result.putAll(delegator.createOrStore(materialType));
        return result;
    }

    public static Map<String,Object> saveVendor(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
        Map<String, Object> result = new HashMap<>();
        boolean updateMode = true;
        String vendorId = (String)context.get("vendorId");
        if(vendorId != null) {
            vendorId = vendorId.toUpperCase();
        }
        if(UtilValidate.isEmpty(vendorId) || !vendorId.replaceAll("^[A-Z0-9_]+$", "").isEmpty()) {
            throw new GenericEntityException("Invalid Vendor Id: " + vendorId + ", use letters, numbers and underscores only");
        }
        GenericValue vendor = EntityQuery.use(delegator).from("QcVendor").where(UtilMisc.toMap("vendorId", vendorId)).queryOne();

        if(UtilValidate.isEmpty(vendor)) {
            updateMode = false;
            vendor = delegator.makeValue("QcVendor", "vendorId", vendorId);
        }

        vendor.put("vendorName", context.get("name"));
        if(UtilValidate.isNotEmpty(context.get("activeFlag"))) {
            vendor.put("activeFlag", converttoIndicator((String) context.get("activeFlag"), "N"));
        }
        result.put("updateMode", updateMode);
        result.put("id", vendorId);
        result.putAll(delegator.createOrStore(vendor));
        return result;
    }

    public static Map<String, Object> getAssignedAndAvailableVendorStyleGroups(Delegator delegator, String vendorId) throws Exception {
        SQLProcessor sqlProcessor = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));
        Map<String, String> availableStyleGroups = new LinkedHashMap<>();
        Map<String, String> assignedStyleGroups = new LinkedHashMap<>();
        String sqlCommand1 = "SELECT STYLE_GROUP_ID, STYLE_GROUP_NAME FROM qc_style_group where STYLE_GROUP_ID not in (select STYLE_GROUP_ID from qc_vendor_style_group_assoc where vendor_id=?) and ACTIVE_FLAG='Y' order by STYLE_GROUP_NAME";
        try (PreparedStatement statement = sqlProcessor.getConnection().prepareStatement(sqlCommand1)) {
            statement.setString(1, vendorId);
            ResultSet rs = statement.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    availableStyleGroups.put(rs.getString("STYLE_GROUP_ID"), rs.getString("STYLE_GROUP_NAME"));
                }
            }
        } finally {
            if(sqlProcessor != null) {
                sqlProcessor.close();
            }
        }
        List<GenericValue> vendorStyleGroups = EntityQuery.use(delegator).from("QcVendorStyleGroup").where(UtilMisc.toMap("vendorId", vendorId)).orderBy("sequenceNum ASC").queryList();
        vendorStyleGroups.forEach(v -> assignedStyleGroups.put(v.getString("styleGroupId"), v.getString("styleGroupName")));

        Map<String, Object> result = new HashMap<>();
        result.put("available", availableStyleGroups);
        result.put("assigned", assignedStyleGroups);
        return result;
    }

    public static Map<String, Object> getAssignedAndAvailablePricingAttributes(Delegator delegator, String styleGroupId) throws Exception {
        SQLProcessor sqlProcessor = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));
        Map<String, String> availableAttributes = new LinkedHashMap<>();
        Map<String, String> assignedAttributes = new LinkedHashMap<>();
        String sqlCommand1 = "SELECT ATTRIBUTE_ID, ATTRIBUTE_NAME, ATTRIBUTE_LABEL FROM QC_ATTRIBUTE WHERE ATTRIBUTE_TYPE_ID IN ('BASE_PRICE', 'CATEGORY_ADD_ON_PRICE') AND ATTRIBUTE_ID NOT IN (SELECT ATTRIBUTE_ID FROM QC_STYLE_GROUP_ATTRIBUTE_ASSOC WHERE STYLE_GROUP_ID=?) AND ACTIVE_FLAG='Y' ORDER BY ATTRIBUTE_NAME;";
        try (PreparedStatement statement = sqlProcessor.getConnection().prepareStatement(sqlCommand1)) {
            statement.setString(1, styleGroupId);
            ResultSet rs = statement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    availableAttributes.put(rs.getString("ATTRIBUTE_ID"), UtilValidate.isNotEmpty(rs.getString("ATTRIBUTE_LABEL")) ? rs.getString("ATTRIBUTE_LABEL") : rs.getString("ATTRIBUTE_NAME"));
                }
            }
        } finally {
            if(sqlProcessor != null) {
                sqlProcessor.close();
            }
        }
        List<GenericValue> styleGroupAttributes = EntityQuery.use(delegator).from("QcStyleGroupAttribute").where(UtilMisc.toMap("styleGroupId", styleGroupId)).orderBy("sequenceNum ASC").queryList();
        styleGroupAttributes.forEach(e -> assignedAttributes.put(e.getString("attributeId"), e.getString("attributeName")));

        Map<String, Object> result = new HashMap<>();
        result.put("available", availableAttributes);
        result.put("assigned", assignedAttributes);
        return result;
    }

    public static Map<String, Object> getVendorPricingDetails(Delegator delegator, String vendorId) throws Exception {
        SQLProcessor sqlProcessor = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));
        Map<String, Object> vendorPricingDetails = new LinkedHashMap<>();
        String sqlCommand1 = "SELECT A.ATTRIBUTE_NAME, A.ATTRIBUTE_LABEL, B.ATTRIBUTE_ID, B.STYLE_GROUP_ID, B.ATTRIBUTE_VALUE_ID, B.QUANTITY_BREAK, B.VOLUME_PRICE FROM QC_ATTRIBUTE A INNER JOIN QC_ATTRIBUTE_VALUE B ON A.ATTRIBUTE_ID = B.ATTRIBUTE_ID WHERE B.VENDOR_ID=? ORDER BY B.STYLE_GROUP_ID, A.SEQUENCE_NUM";
        try (PreparedStatement statement = sqlProcessor.getConnection().prepareStatement(sqlCommand1)) {
            statement.setString(1, vendorId);
            ResultSet rs = statement.executeQuery();
            String lastStyleGroupId = "";
            if (rs != null) {
                List<Map<String, Object>> styleGroupPriceDetails = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> priceDetail = new HashMap<>();
                    String styleGroupId = rs.getString("STYLE_GROUP_ID");
                    if(styleGroupId == null) {
                        styleGroupId = "COMMON";
                    }

                    if(!lastStyleGroupId.isEmpty() && !styleGroupId.equals(lastStyleGroupId)) {
                        vendorPricingDetails.put(lastStyleGroupId, styleGroupPriceDetails);
                        styleGroupPriceDetails = new ArrayList<>();
                    }
                    lastStyleGroupId = styleGroupId;
                    priceDetail.put("attributeName", rs.getString("ATTRIBUTE_NAME"));
                    priceDetail.put("attributeLabel", rs.getString("ATTRIBUTE_LABEL"));
                    priceDetail.put("attributeId", rs.getString("ATTRIBUTE_ID"));
                    priceDetail.put("attributeValueId", rs.getString("ATTRIBUTE_VALUE_ID"));
                    priceDetail.put("quantityBreak", Arrays.asList(rs.getString("QUANTITY_BREAK").split("\\|")));
                    List<String> _volumePrice = Arrays.asList(rs.getString("VOLUME_PRICE").split("\\|"));
                    List<String> volumePrice = new ArrayList<>();
                    _volumePrice.forEach(p -> volumePrice.add(Integer.toString(new BigDecimal(p).intValue())));
                    priceDetail.put("volumePrice", volumePrice);

                    styleGroupPriceDetails.add(priceDetail);

                }
                if(!styleGroupPriceDetails.isEmpty()) {
                    vendorPricingDetails.put(lastStyleGroupId, styleGroupPriceDetails);
                }
            }
        } finally {
            if(sqlProcessor != null) {
                sqlProcessor.close();
            }
        }

        return vendorPricingDetails;
    }

    public static List<GenericValue> getAllVendorStyleGroups(Delegator delegator, String vendorId) throws GenericEntityException {
        return EntityQuery.use(delegator).from("QcVendorStyleGroup").where(UtilMisc.toMap("vendorId", vendorId)).orderBy("sequenceNum ASC").queryList();
    }

    public static List<GenericValue> getAllStyleGroupPricingAttributes(Delegator delegator, String styleGroupId) throws GenericEntityException {
//        try {
//            createPricingDataFeed(delegator, "ADMORE",styleGroupId);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return EntityQuery.use(delegator).from("QcStyleGroupAttribute").where(UtilMisc.toMap("styleGroupId", styleGroupId)).orderBy("sequenceNum ASC").queryList();
    }

    public static void addOrRemoveVendorStyleGroups(Delegator delegator, String vendorId, String[] styleGroupIds, boolean removeFlag) throws GenericEntityException {
        for(String groupId : styleGroupIds) {
            if (removeFlag) {
                GenericValue vendorStyleGroup = EntityQuery.use(delegator).from("QcVendorStyleGroupAssoc").where("vendorId", vendorId, "styleGroupId", groupId).queryOne();
                if(vendorStyleGroup != null) {
                    vendorStyleGroup.remove();
                }
            } else {
                GenericValue styleGroup = EntityQuery.use(delegator).from("QcStyleGroup").where("styleGroupId", groupId).queryOne();
                if(styleGroup != null) {
                    GenericValue vendorStyleGroup = delegator.makeValue("QcVendorStyleGroupAssoc", "vendorId", vendorId, "styleGroupId", groupId);
                    vendorStyleGroup.put("sequenceNum", 0L);
                    delegator.createOrStore(vendorStyleGroup);
                }
            }
        }
    }

    public static void addOrRemoveStyleGroupPricingAttributes(Delegator delegator, String styleGroupId, String[] attributeIds, boolean removeFlag) throws GenericEntityException {
        for(String attributeId : attributeIds) {
            if (removeFlag) {
                GenericValue styleGroupAttribute = EntityQuery.use(delegator).from("QcStyleGroupAttributeAssoc").where("styleGroupId", styleGroupId, "attributeId", attributeId).queryOne();
                if(styleGroupAttribute != null) {
                    styleGroupAttribute.remove();
                }
            } else {
                GenericValue attribute = EntityQuery.use(delegator).from("QcAttribute").where("attributeId", attributeId).queryOne();
                if(attribute != null) {
                    GenericValue styleGroupAttribute = delegator.makeValue("QcStyleGroupAttributeAssoc", "styleGroupId", styleGroupId, "attributeId", attributeId);
                    styleGroupAttribute.put("sequenceNum", 0L);
                    delegator.createOrStore(styleGroupAttribute);
                }
            }
        }
    }

    private static void assignPricingAttributes(Delegator delegator, String styleGroupId) throws Exception {
        SQLProcessor sqlProcessor = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));
        SQLProcessor sqlProcessor1 = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"), sqlProcessor.getConnection());
        String sqlCommand1 = "INSERT INTO QC_STYLE_GROUP_ATTRIBUTE_ASSOC (STYLE_GROUP_ID, ATTRIBUTE_ID, SEQUENCE_NUM) SELECT ?, ATTRIBUTE_ID, 0 FROM QC_ATTRIBUTE WHERE ATTRIBUTE_TYPE_ID IN ('BASE_PRICE', 'CATEGORY_ADD_ON_PRICE')";
        try (PreparedStatement statement = sqlProcessor.getConnection().prepareStatement(sqlCommand1)) {
            statement.setString(1, styleGroupId);
            statement.executeQuery();
        } finally {
            if(sqlProcessor1 != null) {
                sqlProcessor1.close();
            }
            if(sqlProcessor != null) {
                sqlProcessor.close();
            }
        }
    }

    private static int[] toIntArray(String value) {
        String[] valueArray = value.split("\\|");
        int[] intArray = new int[valueArray.length];
        for(int i = 0; i < valueArray.length; i ++) {
            intArray[i] = Integer.parseInt(valueArray[i]);
        }
        return intArray;
    }

    private static BigDecimal[] toBigDecimalArray(String value) {
        if(UtilValidate.isEmpty(value)) {
            return new BigDecimal[0];
        }
        String[] valueArray = value.split("\\|");
        BigDecimal[] bigDecimalArray = new BigDecimal[valueArray.length];
        for(int i = 0; i < valueArray.length; i ++) {
            bigDecimalArray[i] = new BigDecimal(valueArray[i]);
        }
        return bigDecimalArray;
    }



    public static PricingMatrix createPricingMatrix(Delegator delegator) throws Exception {

        List<GenericValue> attributeValues = EntityQuery.use(delegator).select("vendorId", "styleGroupId", "attributeId", "attributeValueId", "quantityBreak", "volumePrice").from("QcAttributeValue").orderBy("vendorId", "styleGroupId").queryList();
        Map<String, List<String>> itemAttributeValues = getItemAttributeValues(delegator);

        List<PricingTemplate> pricingTemplates = new ArrayList<>();
        pricingTemplates.add(new PricingTemplate("CUSTOM", "", new ArrayList<>()));

        List<PricingAttribute> pricingAttributes = new ArrayList<>();
        boolean initial = true;
        String lastVendorId = "";
        String lastGroupId = "";
        for(GenericValue attributeValue : attributeValues){
            String vendorId = attributeValue.getString("vendorId");
            String groupId = attributeValue.getString("styleGroupId");
            if(UtilValidate.isEmpty(groupId)) {
                groupId = "COMMON";
            }
            List<String> styleIds = new ArrayList<>();
            if(itemAttributeValues.containsKey(attributeValue.getString("attributeValueId"))) {
                styleIds = itemAttributeValues.get(attributeValue.getString("attributeValueId"));
            }
            PricingAttribute pricingAttribute = new PricingAttribute(attributeValue.getString("attributeValueId"), attributeValue.getString("attributeId"), toIntArray(attributeValue.getString("quantityBreak")), toBigDecimalArray(attributeValue.getString("volumePrice")), styleIds);
            if(!initial && (!vendorId.equals(lastVendorId) || !groupId.equals(lastGroupId))) {
                pricingTemplates.add(new PricingTemplate(lastGroupId, lastVendorId, pricingAttributes));
                pricingAttributes = new ArrayList<>();
            }
            pricingAttributes.add(pricingAttribute);
            lastVendorId = vendorId;
            lastGroupId = groupId;
            initial = false;
        }
        if(!pricingAttributes.isEmpty()) {
            pricingTemplates.add(new PricingTemplate(lastGroupId, lastVendorId, pricingAttributes));
        }

        return new PricingMatrix("FOLDERS", pricingTemplates);
    }

    public static Map<String, List<String>> getItemAttributeValues(Delegator delegator) throws Exception {
        Map<String, List<String>> itemAttributeValues = new HashMap<>();

        List<GenericValue> attributeValues = EntityQuery.use(delegator).select("styleId", "attributeValueId").from("QcStyleAttributeValue").orderBy("attributeValueId").queryList();
        for(GenericValue av : attributeValues) {
            List<String> styles = new ArrayList<>();
            if(itemAttributeValues.containsKey(av.getString("attributeValueId"))) {
                styles.addAll(itemAttributeValues.get(av.getString("attributeValueId")));
            }
            styles.add(av.getString("styleId"));
            itemAttributeValues.put(av.getString("attributeValueId"), styles);
        }
        return itemAttributeValues;
    }

    public static List<GenericValue> getStyles(Delegator delegator, List<String> groupIds) throws GenericEntityException {
        return EntityQuery.use(delegator).select("styleId", "styleDescription").from("QcStyle").where(EntityCondition.makeCondition("styleGroupId", EntityOperator.IN, groupIds)).orderBy("styleId").cache().queryList();
    }

    public static List<GenericValue> getStyleGroups(Delegator delegator, List<String> groupIds) throws GenericEntityException {
        return EntityQuery.use(delegator).select("styleGroupId", "styleGroupName").from("QcStyleGroup").where(EntityCondition.makeCondition("styleGroupId", EntityOperator.IN, groupIds)).orderBy("styleGroupName").cache().queryList();
    }

    public static List<GenericValue> getVendors(Delegator delegator, List<String> vendorIds) throws GenericEntityException {
        return EntityQuery.use(delegator).select("vendorId", "vendorName").from("QcVendor").where(EntityCondition.makeCondition("vendorId", EntityOperator.IN, vendorIds)).orderBy("vendorName").cache().queryList();
    }

    public static List<GenericValue> getStocks(Delegator delegator, List<String> typeIds) throws GenericEntityException {
        return EntityQuery.use(delegator).select("stockId", "stockName").from("QcStock").where(EntityCondition.makeCondition("stockTypeId", EntityOperator.IN, typeIds)).orderBy("stockName").cache().queryList();
    }

    public static List<GenericValue> getStockTypes(Delegator delegator, List<String> typeIds) throws GenericEntityException {
        return EntityQuery.use(delegator).select("stockTypeId", "stockTypeName").from("QcStockType").where(EntityCondition.makeCondition("stockTypeId", EntityOperator.IN, typeIds)).orderBy("stockTypeName").cache().queryList();
    }

    public static List<String> getAllStockTypeIds(Delegator delegator) throws GenericEntityException {
        List<String> typeIds = new ArrayList<>();
        getAllStockTypes(delegator).forEach(st -> typeIds.add(st.getString("stockTypeId")));
        return typeIds;
    }

    private static Timestamp convertFromToDateToTimestamp(String value, boolean toDate) {
        if(!value.contains(" ")) {
            value = value + ((!toDate) ? (" 00:00:00.0") : (" 23:59:59.0"));
        }
        return Timestamp.valueOf(value);
    }

    private static String converttoIndicator(String value, String defaultValue) {
        if(UtilValidate.isNotEmpty(value) && value.trim().equalsIgnoreCase("Y")) {
            return "Y";
        } else if(UtilValidate.isNotEmpty(defaultValue) && (defaultValue.trim().equalsIgnoreCase("Y") || defaultValue.trim().equalsIgnoreCase("N"))){
            return defaultValue.toUpperCase();
        } else {
            return "N";
        }
    }

    public static Map<String, Object> getPricingResponse(LocalDispatcher dispatcher, Delegator delegator, Map<String, Object> context) throws Exception {
        PricingCalculator pricingCalculator = PricingCalculator.getInstance(delegator);
        Map<String, Object> pricingResponse = pricingCalculator.getPricingResponse(delegator, new Gson().fromJson((String) context.get("state"), Map.class), ((GenericValue) context.get("userLogin")).getString("userLoginId"));
        if(new Boolean((String)context.get("generate"))) {
            String pricingRequestJSON = (String) context.get("state");
            String pricingResponseJSON = new Gson().toJson(pricingResponse);
            if(!(boolean)pricingResponse.get("valid")) {
                throw new Exception("Unable to generate the Quote, invalid pricing response");
            }
            context.put("pricingRequest", pricingRequestJSON);
            context.put("pricingResponse", pricingResponseJSON);
            GenericValue quote = saveQuote(delegator, context);
            pricingResponse.put("quoteId", quote.getString("quoteId"));

            try {
                GenericValue quoteRequest = EntityQuery.use(delegator).from("CustomEnvelope").where("quoteId", context.get("quoteRequestId")).queryOne();
                if(quoteRequest != null) {
                    //if there is an additionalPhone, try and get gclid
                    if(UtilValidate.isEmpty(quoteRequest.getString("gclid"))) {
                        try {
                            Map<String, Object> requestData = new Gson().fromJson(pricingRequestJSON, HashMap.class);
                            if(UtilValidate.isNotEmpty(requestData) && UtilValidate.isNotEmpty(requestData.get("CUSTOMER_ADDRESS"))) {
                                Map<String, Object> customer = (Map<String, Object>) requestData.get("CUSTOMER_ADDRESS");
                                if(UtilValidate.isNotEmpty(customer) && UtilValidate.isNotEmpty(customer.get("additionalPhone"))) {
                                    CallDetailReport dialogReport = new CallDetailReport(delegator, ((String) customer.get("additionalPhone")).replaceAll("[^\\d]", ""), EnvUtil.getNDaysBeforeOrAfterNow(-5, true), UtilDateTime.nowTimestamp());
                                    dialogReport.process();
                                    if(UtilValidate.isNotEmpty(dialogReport.getGCLID())) {
                                        quoteRequest.put("gclid", dialogReport.getGCLID());
                                        quoteRequest.store();
                                    }
                                    if(UtilValidate.isNotEmpty(dialogReport.getSource())) {
                                        quoteRequest.put("source", dialogReport.getSource());
                                        quoteRequest.store();
                                    }
                                }
                            }
                        } catch(Exception cdrE) {
                            EnvUtil.reportError(cdrE);
                        }
                    }

                    //if quote is unassigned, then assign the user that generated the quote
                    if(UtilValidate.isEmpty(quoteRequest.getString("assignedTo"))) {
                        Map<String, Object> statusResult = dispatcher.runSync("changeQuoteStatus", UtilMisc.toMap("quoteId", context.get("quoteRequestId"), "statusId", "QUO_ASSIGNED", "changeReason", "Assigned to: " + ((GenericValue) context.get("userLogin")).getString("userLoginId"), "userLogin", EnvUtil.getSystemUser(delegator)));
                        quoteRequest.put("assignedTo", ((GenericValue) context.get("userLogin")).getString("userLoginId"));
                        quoteRequest.store();
                    }
                }

                Map<String, Object> statusResult = dispatcher.runSync("changeQuoteStatus", UtilMisc.toMap("quoteId", context.get("quoteRequestId"), "statusId", "QUO_APPROVED", "changeReason", "Created Quote: " + quote.getString("quoteId"), "userLogin", (GenericValue) context.get("userLogin")));
                boolean quotePDFSuccess = QuoteHelper.generateQuotePDF(delegator, quote.getString("quoteId"));
            } catch (Exception e) {
                EnvUtil.reportError(e);
                Debug.logError(e, "Error changing status", module);
            }
        }
        return pricingResponse;
    }

    public static Map<String, Object> getMinMaxQuantities(Delegator delegator, Map<String, Object> context) throws Exception {
        PricingCalculator pricingCalculator = PricingCalculator.getInstance(delegator);
        PricingTemplate pricingTemplate = pricingCalculator.getPricingMatrix().getPricingTemplate((String)context.get("styleGroupId"));
        Map<String, Object> response = new HashMap<>();
        response.put("MIN_QTY", pricingTemplate.getMinQty());
        response.put("MAX_QTY", pricingTemplate.getMaxQty());
        return response;
    }

    private static GenericValue saveQuote(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
        String pricingRequest = (String) context.get("pricingRequest");
        String pricingResponse = (String) context.get("pricingResponse");
        String quoteId = delegator.getNextSeqId("QcQuote");
        String quoteRequestId = (String) context.get("quoteRequestId");
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        String comment = (String) context.get("comment");
        String internalComment = (String) context.get("internalComment");
        String production = (String) context.get("production");

        GenericValue quote = delegator.makeValue("QcQuote", "quoteId", quoteId);
        quote.put("quoteRequestId", quoteRequestId);
        quote.put("pricingRequest", pricingRequest);
        quote.put("pricingResponse", pricingResponse);
        quote.put("createdBy", userLogin.getString("userLoginId"));
        quote.put("comment", UtilValidate.isEmpty(comment) ? null : comment);
        quote.put("internalComment", UtilValidate.isEmpty(internalComment) ? null : internalComment);
        quote.put("production", UtilValidate.isEmpty(production) ? null : production);
        quote.put("fromDate", UtilDateTime.nowTimestamp());
        delegator.createOrStore(quote);

        return quote;
    }

    public static GenericValue getQuote(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
        return EntityQuery.use(delegator).from("QcQuote").where("quoteId", (String) context.get("quoteId")).queryOne();
    }

    /**
     * Deserialize price request and get useful data
     * @param delegator
     * @param priceJson
     * @return
     * @throws GenericEntityException
     */
    public static Map<String, Object> deserializedPriceSummary(Delegator delegator, GenericValue product, String priceJson) throws GenericEntityException {
        Map<String, Object> priceData = new HashMap<>();

        if(UtilValidate.isNotEmpty(priceJson)) {
            Map pricingRequest = deserializePricingRequest(priceJson);
            priceData.put("vendor", pricingRequest.get("VENDOR_ID"));
            priceData.put("product", product);
            priceData.put("printOptions", printOptionsSummary((List<Map<String, Object>>) pricingRequest.get("CUSTOM_OPTIONS"), priceData, "CUSTOM"));
            priceData.put("addOnOptions", addOnOptionsSummary((List) pricingRequest.get("ADDONS_OPTIONS"), priceData, false));
            priceData.put("printSummary", printOptions(priceData));

            String netsuiteSKU = product.getString("productId").replaceAll("^.*(\\-.*?\\-.*?)$", (String) pricingRequest.get("VENDOR_SKU") + "$1");
            priceData.put("netsuiteSKU", netsuiteSKU);
        }

        return priceData;
    }

    /**
     * Get the quote data necessary for a summary to be sent to the inquirer
     * @param delegator
     * @param quote
     * @param quoteId
     * @return
     * @throws GenericEntityException
     */
    public static Map<String, Object> deserializedQuoteSummary(Delegator delegator, GenericValue quote, String quoteId) throws GenericEntityException {
        Map<String, Object> quoteData = new HashMap<>();
        if(quote == null) {
            quote = getQuote(delegator, UtilMisc.toMap("quoteId", quoteId));
        }

        Map pricingRequest = deserializePricingRequest(quote.getString("pricingRequest"));
        Map pricingResponse = deserializePricingResponse(quote.getString("pricingResponse"));

        boolean isCustomStyle = UtilValidate.isNotEmpty(pricingRequest.get("CUSTOM_STYLE"));
        GenericValue product = (UtilValidate.isNotEmpty(pricingRequest.get("CUSTOM_STYLE"))) ? null : ProductHelper.getProduct(delegator, (String) pricingRequest.get("STYLE"));
        GenericValue material = (UtilValidate.isNotEmpty(pricingRequest.get("CUSTOM_STOCK"))) ? null : ProductHelper.getMaterial(delegator, (String) pricingRequest.get("STOCK"));

        GenericValue assignedTo = EntityQuery.use(delegator).from("CustomEnvelope").where("quoteId", (String) quote.get("quoteRequestId")).queryOne();
        GenericValue userLogin = null;

        if (UtilValidate.isNotEmpty(assignedTo) && UtilValidate.isNotEmpty(assignedTo.get("assignedTo"))) {
            userLogin = EntityQuery.use(delegator).from("UserLogin").where("userLoginId", (String) assignedTo.get("assignedTo")).queryFirst();
        }

        if (UtilValidate.isEmpty(userLogin)) {
            userLogin = EntityQuery.use(delegator).from("UserLogin").where("userLoginId", quote.getString("createdBy")).queryFirst();
        }

        GenericValue salesPerson = EntityQuery.use(delegator).from("Person").where("partyId", userLogin.getString("partyId")).queryOne();
        String salesEmail = userLogin.getString("userLoginId");
        String salesNumber = (String) ((Map<String, Object>) ((Map<String, Object>) QuoteHelper.foldersAssignedToUsers).get((QuoteHelper.foldersAssignedToUsers.containsKey(salesEmail) ? salesEmail : "default"))).get("phoneNumber");

        quoteData.put("isCustomStyle", isCustomStyle);
        quoteData.put("quoteRequestId", quote.getString("quoteRequestId"));
        quoteData.put("quoteId", quote.getString("quoteId"));
        quoteData.put("customer", QuoteHelper.getCustomEnvelopeInfo(delegator, quote.getString("quoteRequestId")));
        quoteData.put("vendor", (UtilValidate.isNotEmpty(pricingRequest.get("CUSTOM_VENDOR"))) ? pricingRequest.get("CUSTOM_VENDOR") : pricingRequest.get("VENDOR"));
        quoteData.put("product", product);
        if(product == null) {
            quoteData.put("productName", isCustomStyle ? (String) pricingRequest.get("CUSTOM_STYLE") : (String) pricingRequest.get("STYLE"));
        }
        quoteData.put("netsuiteSKU", (product == null || material == null) ? "CUSTOM-P" : product.getString("productId") + "-" + material.getString("customColorNumber") + "-C");
        quoteData.put("material", (material == null) ? (String) pricingRequest.get("CUSTOM_STOCK") : material.getString("description"));
        quoteData.put("createdStamp", quote.getTimestamp("createdStamp"));
        quoteData.put("createdBy", quote.getString("createdBy"));
        quoteData.put("salesPerson", salesPerson.getString("firstName") + " " + salesPerson.getString("lastName"));
        quoteData.put("printOptions", printOptionsSummary((List<Map<String, Object>>) pricingRequest.get("PRINT_OPTIONS"), quoteData, "PRINT"));
        quoteData.put("addOnOptions", addOnOptionsSummary((UtilValidate.isNotEmpty(pricingRequest.get("CUSTOM_ADDONS"))) ? (List) pricingRequest.get("CUSTOM_ADDONS") : (List) pricingRequest.get("ADDONS"), quoteData, true));
        quoteData.put("printSummary", printOptions(quoteData));
        quoteData.put("comment", quote.getString("comment"));
        quoteData.put("internalComment", quote.getString("internalComment"));
        quoteData.put("production", quote.getString("production"));
        quoteData.put("customSKU", createCustomSKU(quoteData));
        quoteData.put("salesEmail", salesEmail);
        quoteData.put("salesNumber", salesNumber);

        if (UtilValidate.isNotEmpty(pricingRequest.get("ADDONS"))) {
            for (Object option : (List) pricingRequest.get("ADDONS")) {
                quoteData.put((String) ((Map) option).get("ADDON_NAME"), ((Map) option).get("SIDES"));
            }
        }


        Map<Integer, Map<String, BigDecimal>> priceQuotes = new LinkedHashMap<>();
        List pricingDetails = (ArrayList) pricingResponse.get("pricingDetails");

        List<String> quantities = (ArrayList) pricingDetails.get(0);
        List<String> totals = (ArrayList) pricingDetails.get(pricingDetails.size() - 2);
        List<String> perEach = (ArrayList) pricingDetails.get(pricingDetails.size() - 1);

        int count = 0;
        for(String quantity : quantities) {
            if(StringUtils.isNumeric(quantity)) {
                priceQuotes.put(new Integer(quantity), UtilMisc.<String, BigDecimal>toMap("total", new BigDecimal(totals.get(count)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING), "each", new BigDecimal(perEach.get(count)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING)));
            }
            count++;
        }

        quoteData.put("prices", priceQuotes);

        HashMap<String, Object> properlyFormattedPricingDetails = new HashMap<>();

        for (int x = 1; x < ((ArrayList<String>) pricingDetails.get(0)).size(); x++) {
            HashMap<String, String> pricingDetailList = new HashMap<>();

            for (int y = 1; y < pricingDetails.size(); y++) {
                pricingDetailList.put(((ArrayList<String>) pricingDetails.get(y)).get(0), ((ArrayList<String>) pricingDetails.get(y)).get(x));
            }

            properlyFormattedPricingDetails.put(((ArrayList<String>) pricingDetails.get(0)).get(x), pricingDetailList);
        }

        quoteData.put("pricingDetails", properlyFormattedPricingDetails);

        return quoteData;
    }

    public static String createCustomSKU(Map<String, Object> quoteData) {
        if(UtilValidate.isNotEmpty(quoteData.get("product"))) {
            return "CUSTOM-P";
        }
        if(UtilValidate.isNotEmpty(quoteData.get("offset")) && (Boolean) quoteData.get("offset")) {
            if(UtilValidate.isNotEmpty(quoteData.get("isPMS")) && (Boolean) quoteData.get("isPMS")) {
                return quoteData.get("customSKU") + "-O";
            } else {
                return quoteData.get("customSKU") + "-4";
            }
        }
        if(UtilValidate.isNotEmpty(quoteData.get("foil")) && (Boolean) quoteData.get("foil")) {
            return quoteData.get("customSKU") + "-F";
        }
        if(UtilValidate.isNotEmpty(quoteData.get("embossing")) && (Boolean) quoteData.get("embossing")) {
            return quoteData.get("customSKU") + "-E";
        }

        return null;
    }

    /**
     * Return a string representation of all the options and update the quoteData with necessary values
     * This method also adds data back to the map for other purposes
     * @param printOptions
     * @return
     */
    public static String printOptionsSummary(List<Map<String, Object>> printOptions, Map<String, Object> quoteData, String prefix) {
        StringBuilder data = new StringBuilder();

        int colorsFront = 1;
        int colorsBack = 0;

        for(Map<String, Object> option : printOptions) {
            if(((String) option.get(prefix + "_OPTION_NAME")).equalsIgnoreCase("OFFSET")) {
                boolean twoSided = false;
                quoteData.put("offset", true);

                List<String> offsetNote = new ArrayList<>();
                List<Map<String, Object>> sides = (List<Map<String, Object>>) option.get("SIDES");
                if(sides.size() == 2) {
                    twoSided = true;
                }

                for(int i = 0; i < sides.size(); i++) {
                    boolean hasFrontPrinting = false;
                    boolean hasBackPrinting = false;
                    boolean is4Color = false;
                    boolean isPMS = false;

                    data.append("Offset Printing Side").append(" " + (i + 1) + ":").append("\n");
                    StringBuilder str = new StringBuilder();

                    //get front/back ink color info
                    for(Map.Entry<String, Object> entry : sides.get(i).entrySet()) {
                        if(entry.getKey().equalsIgnoreCase("NUMBER_OF_INKS")) {
                            if((i + 1) == 1) {
                                if(entry.getValue() instanceof Double) {
                                    colorsFront = ((Double) entry.getValue()).intValue();
                                } else {
                                    colorsFront = Integer.valueOf((String) entry.getValue());
                                }
                            } else {
                                if(entry.getValue() instanceof Double) {
                                    colorsBack = ((Double) entry.getValue()).intValue();
                                } else {
                                    colorsBack = Integer.valueOf((String) entry.getValue());
                                }
                            }
                        }
                    }

                    for(Map.Entry<String, Object> entry : sides.get(i).entrySet()) {
                        if(entry.getKey().equalsIgnoreCase("PRINT_METHOD") && (((String) entry.getValue()).equalsIgnoreCase("PMS_OFFSET") || ((String) entry.getValue()).equalsIgnoreCase("PMS"))) {
                            isPMS = true;
                            quoteData.put("isPMS", isPMS);
                            int pmsColors = 0;
                            if(i + 1 == 1) {
                                pmsColors = colorsFront;
                                hasFrontPrinting = true;
                            } else {
                                pmsColors = colorsBack;
                                hasBackPrinting = true;
                            }

                            str.append("Offset printed on Side " + (i + 1) + " - " + pmsColors + " PMS Color" + (pmsColors > 1 ? "s" : ""));
                        }
                        if(entry.getKey().equalsIgnoreCase("PRINT_METHOD") && (((String) entry.getValue()).equalsIgnoreCase("FOUR_CP") || ((String) entry.getValue()).equalsIgnoreCase("FOUR_COLOR"))) {
                            is4Color = true;
                            quoteData.put("is4Color", is4Color);
                            int pmsColors = 0;
                            if(i + 1 == 1) {
                                pmsColors = colorsFront;
                                hasFrontPrinting = true;
                            } else {
                                pmsColors = colorsBack;
                                hasBackPrinting = true;
                            }

                            str.append("Offset printed on Side " + (i + 1) + " - 4 Color Process (CMYK)" + (pmsColors > 0 ? " and " + pmsColors + " PMS Color" + (pmsColors > 1 ? "s" : "") : ""));
                        }
                        if(entry.getKey().equalsIgnoreCase("HEAVY_COVERAGE") && ((String) entry.getValue()).equalsIgnoreCase("Y")) {
                            str.append(" with Heavy Coverage");
                        }
                        if(entry.getValue() instanceof Double) {
                            data.append(snakeCaseToWords(entry.getKey()) + ": " + (snakeCaseToWords(((Double) entry.getValue()).toString()))).append("\n");
                        } else {
                            data.append(snakeCaseToWords(entry.getKey()) + ": " + (snakeCaseToWords((String) entry.getValue()))).append("\n");
                        }
                    }


                    if(is4Color && hasFrontPrinting) {
                        colorsFront = 4;
                    }
                    if(is4Color && hasBackPrinting) {
                        colorsBack = 4;
                    }

                    offsetNote.add(str.toString());
                }

                data.append("\n");
                quoteData.put("offsetNote", String.join("\n", offsetNote));
            } else if(((String) option.get(prefix + "_OPTION_NAME")).equalsIgnoreCase("FOIL_STAMPING")) {
                quoteData.put("foil", true);
                quoteData.put("foilColor", option.containsKey(prefix + "_OPTION_COLOR") ? (String) option.get(prefix + "_OPTION_COLOR") : null);

                StringBuilder str = new StringBuilder();
                List<Map<String, Object>> runs = (List<Map<String, Object>>) option.get("RUNS");
                int totalImages = 0;
                for (int i = 0; i < runs.size(); i++) {
                    data.append("Foil Stamping Run").append(" " + (i + 1) + ":").append("\n");
                    str.append("Foil color run #" +  (i + 1) + ":").append("\n");
                    for (Map.Entry<String, Object> entry : runs.get(i).entrySet()) {
                        StringBuilder mergedStr = new StringBuilder();
                        List<String> imgs = (List<String>) entry.getValue();
                        for(int j = 0; j < imgs.size(); j++) {
                            mergedStr.append(UtilValidate.isEmpty(mergedStr.toString()) ? "" : ", ").append(imgs.get(j) + " Sq In.");
                            str.append("Image #" + (j + 1) + " is " + imgs.get(j) + " square inches.").append("\n");
                            totalImages++;
                        }
                        data.append(snakeCaseToWords(entry.getKey()) + ": " +  mergedStr.toString()).append("\n");
                    }
                }

                data.append("\n");
                quoteData.put("foilNote", "This job includes " + totalImages + " images in " + runs.size() + " foil color run(s).\n" + str.toString());
                quoteData.put("totalFoilImages", totalImages);
            } else if(((String) option.get(prefix + "_OPTION_NAME")).equalsIgnoreCase("EMBOSSING")) {
                quoteData.put("embossing", true);

                StringBuilder str = new StringBuilder();
                List<Map<String, Object>> sides = (List<Map<String, Object>>) option.get("SIDES");
                int totalImages = 0;
                int totalRuns = 0;
                for(int i = 0; i < sides.size(); i++) {
                    for(Map.Entry<String, Object> entry : sides.get(i).entrySet()) {
                        List<Map<String, Object>> runs = (List<Map<String, Object>>) entry.getValue();
                        totalRuns = runs.size();
                        for (int j = 0; j < runs.size(); j++) {
                            data.append("Embossing Side").append(" " + (i + 1)).append(" Run " + (j + 1) + ":").append("\n");
                            str.append("Embossing side " + (i + 1) + ", run #" +  (j + 1) + ":").append("\n");
                            for (Map.Entry<String, Object> entry2 : runs.get(j).entrySet()) {
                                StringBuilder mergedStr = new StringBuilder();
                                List<String> imgs = (List<String>) entry2.getValue();
                                for(int k = 0; k < imgs.size(); k++) {
                                    mergedStr.append(UtilValidate.isEmpty(mergedStr.toString()) ? "" : ", ").append(imgs.get(k) + " Sq In.");
                                    str.append("Image #" + (k + 1) + " is " + imgs.get(k) + " square inches.").append("\n");
                                    totalImages++;
                                }
                                data.append(snakeCaseToWords(entry2.getKey()) + ": " +  mergedStr.toString()).append("\n");
                            }
                        }
                    }
                }

                data.append("\n");
                quoteData.put("embossingNote", "This job includes " + totalImages + " images in " + totalRuns + " embossing run(s).\n" + str.toString());
                quoteData.put("totalEmbossingImages", totalImages);
            }
        }

        quoteData.put("colorsFront", colorsFront);
        quoteData.put("colorsBack", colorsBack);

        return data.toString();
    }

    /**
     * Return a string representation of all the options
     * @param addOnOptions
     * @return
     */
    public static String addOnOptionsSummary(List addOnOptions, Map<String, Object> quoteData, boolean isQuote) {
        StringBuilder data = new StringBuilder();

        if (UtilValidate.isNotEmpty(addOnOptions)) {
            for (Object option : addOnOptions) {
                if(isQuote) {
                    if (option instanceof String) {
                        data.append("Addon:").append(" " + option).append("\n");
                    } else if (option instanceof Map) {
                        if (((String) ((Map) option).get("ADDON_NAME")).equalsIgnoreCase("COATING")) {
                            List<String> sides = (List<String>) ((Map) option).get("SIDES");
                            for (int i = 0; i < sides.size(); i++) {
                                data.append("Coating Side ").append((i + 1) + ":").append(" " + WordUtils.capitalizeFully(sides.get(i), new char[]{'_',' '}).replaceAll("_", " ")).append("\n");
                            }
                        }
                    }
                } else {
                    if (option instanceof String) {
                        data.append("Addon:").append(" " + option).append("\n");
                    } else if (option instanceof Map) {
                        if (((String) ((Map) option).get("ADDON_TYPE")).equalsIgnoreCase("ATTACHMENTS")) {
                            List<List> sides = (List<List>) ((Map) option).get("SIDES");
                            for (int i = 0; i < sides.size(); i++) {
                                List addonList = (List) sides.get(i);
                                for (int j = 0; j < addonList.size(); j++) {
                                    data.append("Attachment Side ").append((i + 1) + ":").append(" " + WordUtils.capitalizeFully((String) addonList.get(j), new char[]{'_',' '}).replaceAll("_", " ")).append("\n");
                                    Map addonNotes = (Map) ((Map) option).get("ADDON_NOTES");
                                    data.append("Attachment Details: ").append(WordUtils.capitalizeFully((String) addonNotes.get("ATTACHMENT_TYPE"), new char[]{'_',' '}).replaceAll("_", " ")).append("\n");
                                }
                            }
                        } else if (((String) ((Map) option).get("ADDON_TYPE")).equalsIgnoreCase("COATINGS")) {
                            List<List> sides = (List<List>) ((Map) option).get("SIDES");
                            for (int i = 0; i < sides.size(); i++) {
                                List addonList = (List) sides.get(i);
                                for (int j = 0; j < addonList.size(); j++) {
                                    data.append("Coating Side ").append((i + 1) + ":").append(" " + WordUtils.capitalizeFully((String) addonList.get(j), new char[]{'_',' '}).replaceAll("_", " ")).append("\n");
                                }
                            }
                        }
                    }
                }
            }
        }

        return data.toString();
    }

    public static Map deserializePricingRequest(String pricingRequest) {
        return new Gson().fromJson(pricingRequest, HashMap.class);
    }

    public static Map deserializePricingResponse(String pricingResponse) {
        return new Gson().fromJson(pricingResponse, HashMap.class);
    }

    private static String snakeCaseToWords(String snake) {
        StringBuilder phrase = new StringBuilder();
        if(UtilValidate.isNotEmpty(snake)) {
            String[] words = snake.split("_");
            for(String word : words) {
                phrase.append(UtilValidate.isEmpty(phrase.toString()) ? "" : " ").append(StringUtils.capitalize(StringUtils.lowerCase(word)));
            }
        }

        return phrase.toString();
    }

    public static String printOptions(Map<String, Object> data) {
        List<String> options = new ArrayList<>();
        if(UtilValidate.isNotEmpty(data.get("offset")) && (Boolean) data.get("offset")) {
            options.add("Offset Printing");
        }
        if(UtilValidate.isNotEmpty(data.get("foil")) && (Boolean) data.get("foil")) {
            options.add("Foil");
        }
        if(UtilValidate.isNotEmpty(data.get("embossing")) && (Boolean) data.get("embossing")) {
            options.add("Embossing");
        }

        return String.join(", ", options);
    }

    public static Map<String, Object> getFeedData(Delegator delegator, String vendorId, String styleGroupId) throws Exception {

        Map<String, Object> feedData = new LinkedHashMap<>();
        List<String> pricingAttributes = getDefaultPricingAttributes(delegator);
        String[] defaultQtyBreaks = getDefaultQuantityBreaks();
        Map<String, Object> pricingData = getPricingData(delegator, vendorId, styleGroupId);
        if(pricingData.isEmpty()) {
            feedData.put("QTY_BREAK", Arrays.asList(defaultQtyBreaks));
            pricingAttributes.forEach(e -> {
                feedData.put(e, new ArrayList<>());
            });
        } else {
            feedData.put("QTY_BREAK", pricingData.get("quantityBreak"));
            pricingAttributes.forEach(e -> {
                if(pricingData.containsKey(e)) {
                    feedData.put(e, ((Map)pricingData.get(e)).get("volumePrice"));
                }
            });
        }
        return feedData;
    }

    public static List<String> getDefaultPricingAttributes(Delegator delegator) throws Exception {
        SQLProcessor sqlProcessor = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));
        String sqlCommand = "SELECT ATTRIBUTE_ID, SEQUENCE_NUM FROM QC_ATTRIBUTE WHERE ATTRIBUTE_TYPE_ID IN ('BASE_PRICE', 'CATEGORY_ADD_ON_PRICE') ORDER BY SEQUENCE_NUM";
        List<String> pricingAttributes = new ArrayList<>();
        try {

            ResultSet rs = sqlProcessor.executeQuery(sqlCommand);
            if (rs != null) {
                while (rs.next()) {
                    pricingAttributes.add(rs.getString("ATTRIBUTE_ID"));
                }
            }
        } finally {
            if(sqlProcessor != null) {
                sqlProcessor.close();
            }
        }
        return pricingAttributes;
    }

    private static Map<String, Object> getPricingData(Delegator delegator, String vendorId, String styleGroupId) throws Exception {
        SQLProcessor sqlProcessor = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));
        String sqlCommand = "SELECT ATTRIBUTE_ID, QUANTITY_BREAK, VOLUME_PRICE FROM QC_ATTRIBUTE_VALUE WHERE VENDOR_ID=? AND STYLE_GROUP_ID=?";
        Map<String, Object> pricingData = new HashMap<>();
        try (PreparedStatement statement = sqlProcessor.getConnection().prepareStatement(sqlCommand)) {
            statement.setString(1, vendorId);
            statement.setString(2, styleGroupId);
            ResultSet rs = statement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Map<String, Object> rowData = new HashMap<>();
                    List<String> _volumePrice = Arrays.asList(rs.getString("VOLUME_PRICE").split("\\|"));
                    List<String> volumePrice = new ArrayList<>();
                    _volumePrice.forEach(p -> volumePrice.add(Integer.toString(new BigDecimal(p).intValue())));
                    rowData.put("volumePrice", volumePrice);
                    pricingData.put(rs.getString("ATTRIBUTE_ID"), rowData);
                    if(!pricingData.containsKey("quantityBreak")) {
                        pricingData.put("quantityBreak", Arrays.asList(rs.getString("QUANTITY_BREAK").split("\\|")));
                    }
                }
            }
        } finally {
            if(sqlProcessor != null) {
                sqlProcessor.close();
            }
        }
        return pricingData;
    }

    public static String[] getDefaultQuantityBreaks() {
        return new String[] {"250"," 500"," 750"," 1000"," 1500"," 2000"," 2500"," 3000"," 4000"," 5000"," 7500"," 10000"};
    }

    public static File createPricingDataFeed(Delegator delegator, String vendorId, String styleGroupId) throws Exception {

//        Set<String> excludedCategoryIds = new HashSet<>(Arrays.asList(excludedCategories));
        File feed = new File("C:/tmp/" + vendorId + "-" + styleGroupId + "-feed-" + System.currentTimeMillis() + ".csv");
        ICsvListWriter listWriter = null;
        try {

            CsvPreference preference = new CsvPreference.Builder('"', ';', "\n")
                    .useQuoteMode(new AlwaysQuoteMode()).build();

            listWriter = new CsvListWriter(new FileWriter(feed), preference);

            final ICsvListWriter $listWriter = listWriter;

            Map<String, Object> feedData = getFeedData(delegator, vendorId, styleGroupId);

            List<String> qtyBreak = (List<String>)feedData.get("QTY_BREAK");

            List<String> qtyBreakHeader = new ArrayList<>(qtyBreak);
            qtyBreakHeader.add(0, "QTY_BREAK");

            listWriter.writeHeader(qtyBreakHeader.toArray(new String[0]));

            final CellProcessor[] processors = new CellProcessor[qtyBreak.size() + 1];
            for(int i = 0; i < processors.length; i++) {
                processors[i] = new org.supercsv.cellprocessor.Optional();
            }

            feedData.forEach((k, v) -> {
                try {
                    List<String> rowData = new ArrayList<>((List<String>)v);
                    rowData.add(0, k);
                    $listWriter.write(rowData, processors);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } finally {
            if (listWriter != null) {
                listWriter.close();
            }
        }

        return feed;
    }

    public static boolean toBoolean(boolean... varArg) {
        if(varArg != null && varArg.length > 0) {
            return varArg[0];
        }
        return false;
    }
}