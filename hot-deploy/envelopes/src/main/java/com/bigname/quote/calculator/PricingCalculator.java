package com.bigname.quote.calculator;


import org.apache.commons.lang.math.NumberUtils;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.DelegatorFactory;
import org.apache.ofbiz.entity.GenericValue;

import static com.bigname.quote.calculator.CalculatorHelper.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Manu on 5/26/2017.
 */
public class PricingCalculator {

    private PricingMatrix pricingMatrix;

    private static PricingCalculator instance;

    private PricingCalculator(Delegator delegator) {
        try {
            pricingMatrix = createPricingMatrix(delegator);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PricingCalculator getInstance(Delegator delegator) {
        if(instance == null) {
            instance = new PricingCalculator(delegator);
        }
        return instance;
    }

    public static void invalidateCache() {
        instance = null;
        getInstance(DelegatorFactory.getDelegator("default"));
    }

    public PricingMatrix getPricingMatrix() {
        return pricingMatrix;
    }

    private static Map<String, String> messageMap = new HashMap<>();
    static {
        messageMap.put("SIDE1.PMS_OFFSET.BASE", "PMS Offset Printing - Base Price");
        messageMap.put("SIDE1.FOUR_CP.BASE", "Four Color Process - Base Price");
    }

    private static DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    public static final String delim = " - ";

    public static final Map<String, String> addonsMap = new HashMap<>();
    static {
        addonsMap.put("AQUEOUS_CHOICE", "Aqueous Coating - Choice");
        addonsMap.put("AQUEOUS_GLOSS", "Aqueous Coating - Gloss");
        addonsMap.put("AQUEOUS_MATTE", "Aqueous Coating - Matte");
        addonsMap.put("AQUEOUS_SATIN", "Aqueous Coating - Satin");
        addonsMap.put("AQUEOUS_SOFT_TOUCH", "Aqueous Coating - Soft Touch");
        addonsMap.put("LAMINATION_GLOSS", "Film Lamination - Gloss");
        addonsMap.put("LAMINATION_MATTE", "Film Lamination - Matte");
        addonsMap.put("LAMINATION_SOFT_TOUCH", "Film Lamination - Soft Touch");
        addonsMap.put("SPOT_LAMINATION", "Spot Lamination");
        addonsMap.put("SPOT_UV", "Spot U/V");
        addonsMap.put("SPOT_STRIKE_MATTE", "Spot Strike-thru - Matte");
        addonsMap.put("UV_COATING_GLOSS", "U/V Flood Coating - Gloss");
    }

    public Map<String, Object> getPricingResponse(Delegator delegator, Map<String, Object> pricingRequest, String createdBy) throws Exception {
        StringBuilder details = new StringBuilder();
        Map<String, Object> pricingResponse = new HashMap<>();
        pricingResponse.put("valid", false);
        pricingResponse.put("createdDate", dateFormat.format(new Date()));
        pricingResponse.put("createdBy", UtilValidate.isEmpty(createdBy) ? "Anonymous" : createdBy);
        String changedParam = (String)pricingRequest.get("CHANGED_PARAM");
        if(changedParam == null) {
            changedParam = "";
        }
        String styleId = pricingRequest.containsKey("STYLE") ? (String)pricingRequest.get("STYLE") : null;
        String styleGroupId = pricingRequest.containsKey("STYLE_GROUP") ? (String)pricingRequest.get("STYLE_GROUP") : null;
        String vendorId = pricingRequest.containsKey("VENDOR") ? (String)pricingRequest.get("VENDOR") : null;
        String stockTypeId = pricingRequest.containsKey("STOCK_TYPE") ? (String)pricingRequest.get("STOCK_TYPE") : null;
        String stockId = pricingRequest.containsKey("STOCK") ? (String)pricingRequest.get("STOCK") : null;
        String customStyle = null, customStyleGroup = null, customVendor = null, customStockType = null, customStock = null;

        if(styleId == null) {
            customStyle =  (String)pricingRequest.get("CUSTOM_STYLE");
        }

        if(styleGroupId == null) {
            customStyleGroup =  (String)pricingRequest.get("CUSTOM_STYLE_GROUP");
        }

        if(vendorId == null) {
            customVendor =  (String)pricingRequest.get("CUSTOM_VENDOR");
        }

        if(stockTypeId == null) {
            customStockType =  (String)pricingRequest.get("CUSTOM_STOCK_TYPE");
        }

        if(stockId == null) {
            customStock =  (String)pricingRequest.get("CUSTOM_STOCK");
        }

        boolean customQuoteRequest = styleId == null || styleGroupId == null || vendorId == null || stockTypeId == null || stockId == null;

        PricingMatrix pricingMatrix = getPricingMatrix();

        if(customStyle == null && customStyleGroup == null) {
            //If STYLE is selected or deselected
            if (changedParam.equals("STYLE")) {

                List<String> groupIds = new ArrayList<>();
                //If STYLE is selected
                if (UtilValidate.isNotEmpty(styleId)) {
                    //Get the STYLE object for the selected STYLE_ID
                    Map<String, Object> style = getStyle(delegator, UtilMisc.toMap("id", styleId));

                    //Add the GROUP_ID of the selected STYLE to the list of GROUP_IDs. When a STYLE is selected, the GROUP_IDs list will contain only one GROUP_ID, which is the GROUP_ID of the selected STYLE
                    groupIds.add((String) style.get("styleGroupId"));

                    //STYLE_GROUP is not selected
                    if (UtilValidate.isEmpty(styleGroupId)) {
                        //Since we only have one STYLE_GROUP, select that single STYLE_GROUP by default
                        styleGroupId = (String) style.get("styleGroupId");

                        //Add the selected STYLE_GROUP_ID to the response, needed to select the STYLE_GROUP drop down on the front-end
                        pricingResponse.put("STYLE_GROUPS_VALUE", styleGroupId);

                        //Set the changedParam for STYLE_GROUP, since we need to show only STYLES corresponding to this defaultly selected STYLE_GROUPS only
                        changedParam = "STYLE_GROUP";
                    }
                }
                //STYLE is deselected
                else {
                    //Add all the available GROUP_IDS, so that we can show all the STYLES corresponding to this GROUP_IDS
                    groupIds = pricingMatrix.getGroupIds();
                }

                List<GenericValue> groups = getStyleGroups(delegator, groupIds);
                Map<String, String> _groups = new LinkedHashMap<>();
                groups.forEach(group -> _groups.put((String) group.get("styleGroupId"), (String) group.get("styleGroupName")));
                pricingResponse.put("STYLE_GROUPS", _groups);
            }

            if (changedParam.equals("STYLE_GROUP")) {
                List<GenericValue> styles;
                if (UtilValidate.isNotEmpty(styleGroupId)) {
                    styles = getStyles(delegator, UtilMisc.toList(styleGroupId));

                } else {
                    styles = getStyles(delegator, pricingMatrix.getGroupIds());

                }

                Map<String, String> _styles = new LinkedHashMap<>();
                styles.forEach(style -> _styles.put((String) style.get("styleId"), style.get("styleId") + " - " + style.get("styleDescription")));
                pricingResponse.put("STYLES", _styles);
                pricingResponse.put("STYLES_VALUE", styleId);

                if (UtilValidate.isEmpty(styleGroupId)) {
                    pricingResponse.put("VENDORS", new LinkedHashMap<>());
                    vendorId = "";
                } else {
                    List<GenericValue> vendors = getVendors(delegator, pricingMatrix.getPricingTemplate(styleGroupId).getVendorIds());
                    Map<String, String> _vendors = new LinkedHashMap<>();
                    vendors.forEach(vendor -> _vendors.put((String) vendor.get("vendorId"), (String) vendor.get("vendorName")));
                    pricingResponse.put("VENDORS", _vendors);
                    if (vendors.size() == 1) {
                        vendorId = vendors.get(0).getString("vendorId");
                    }
                    if (UtilValidate.isNotEmpty(vendorId)) {
                        pricingResponse.put("VENDORS_VALUE", vendorId);
                    }
                }
            }
        } else {
            if(changedParam.equals("CUSTOM_STYLE")) {
                if(styleGroupId != null) {
                    List<GenericValue> groups = getStyleGroups(delegator, pricingMatrix.getGroupIds());
                    Map<String, String> _groups = new LinkedHashMap<>();
                    groups.forEach(group -> _groups.put((String) group.get("styleGroupId"), (String) group.get("styleGroupName")));
                    pricingResponse.put("STYLE_GROUPS", _groups);
                    if(!styleGroupId.isEmpty()) {
                        pricingResponse.put("STYLE_GROUPS_VALUE", styleGroupId);
                    }
                }
                if(vendorId != null) {
                    Map<String, String> _vendors = new LinkedHashMap<>();
                    getAllVendors(delegator).forEach(vendor -> _vendors.put((String) vendor.get("vendorId"), (String) vendor.get("vendorName")));
                    pricingResponse.put("VENDORS", _vendors);
                    if(!vendorId.isEmpty()) {
                        pricingResponse.put("VENDORS_VALUE", vendorId);
                    }
                }

            } else if(changedParam.equals("CUSTOM_STYLE_GROUP") || changedParam.equals("STYLE")) {
                if(styleId != null) {
                    pricingResponse.put("STYLES", new HashMap<>());
                }

                if(vendorId != null) {
                    Map<String, String> _vendors = new LinkedHashMap<>();
                    getAllVendors(delegator).forEach(vendor -> _vendors.put((String) vendor.get("vendorId"), (String) vendor.get("vendorName")));
                    pricingResponse.put("VENDORS", _vendors);
                    if(!vendorId.isEmpty()) {
                        pricingResponse.put("VENDORS_VALUE", vendorId);
                    }
                }
            }
        }

        if(customStock == null && customStockType == null) {
            if (changedParam.equals("STOCK")) {

                List<String> typeIds = new ArrayList<>();
                //If STOCK is selected
                if (UtilValidate.isNotEmpty(stockId)) {
                    //Get the STOCK object for the selected STOCK_ID
                    Map<String, Object> stock = getStock(delegator, UtilMisc.toMap("id", stockId));

                    //Add the TYPE_ID of the selected STOCK to the list of TYPE_IDs. When a STOCK is selected, the TYPE_IDs list will contain only one TYPE_ID, which is the TYPE_ID of the selected TYPE
                    typeIds.add((String) stock.get("stockTypeId"));

                    //STOCK_TYPE is not selected
                    if (UtilValidate.isEmpty(stockTypeId)) {
                        //Since we only have one STOCK_TYPE, select that single STOCK_TYPE by default
                        stockTypeId = (String) stock.get("stockTypeId");

                        //Add the selected STOCK_TYPE_ID to the response, needed to select the STOCK_TYPE drop down on the front-end
                        pricingResponse.put("STOCK_TYPES_VALUE", stockTypeId);

                        //Set the changedParam for STOCK_TYPE, since we need to show only STOCKS corresponding to this defaultly selected STOCK_TYPE only
                        changedParam = "STOCK_TYPE";
                    }
                }
                //STOCK is deselected
                else {
                    //Add all the available TYPE_IDS, so that we can show all the STOCKS corresponding to this TYPE_IDs
                    getAllStockTypes(delegator).forEach(st -> typeIds.add((String) st.get("stockTypeId")));
                }

                List<GenericValue> types = getStockTypes(delegator, typeIds);
                Map<String, String> _types = new LinkedHashMap<>();
                types.forEach(type -> _types.put((String) type.get("stockTypeId"), (String) type.get("stockTypeName")));
                pricingResponse.put("STOCK_TYPES", _types);
            }

            if (changedParam.equals("STOCK_TYPE")) {
                List<GenericValue> stocks;
                if (UtilValidate.isNotEmpty(stockTypeId)) {
                    stocks = getStocks(delegator, UtilMisc.toList(stockTypeId));

                } else {
                    List<String> typeIds = new ArrayList<>();
                    getAllStockTypes(delegator).forEach(st -> typeIds.add((String) st.get("stockTypeId")));
                    stocks = getStocks(delegator, typeIds);

                }

                Map<String, String> _stocks = new LinkedHashMap<>();
                stocks.forEach(stock -> _stocks.put((String) stock.get("stockId"), (String) stock.get("stockName")));
                pricingResponse.put("STOCKS", _stocks);
                pricingResponse.put("STOCKS_VALUE", stockId);

            }
        } else {
            if(changedParam.equals("CUSTOM_STOCK")) {
                if(stockTypeId != null) {
                    List<String> typeIds = new ArrayList<>();
                    getAllStockTypes(delegator).forEach(st -> typeIds.add((String) st.get("stockTypeId")));
                    List<GenericValue> types = getStockTypes(delegator, typeIds);
                    Map<String, String> _types = new LinkedHashMap<>();
                    types.forEach(type -> _types.put((String) type.get("stockTypeId"), (String) type.get("stockTypeName")));
                    pricingResponse.put("STOCK_TYPES", _types);
                    if(!stockTypeId.isEmpty()) {
                        pricingResponse.put("STOCK_TYPES_VALUE", stockTypeId);
                    }
                }
            } else if(changedParam.equals("CUSTOM_STOCK_TYPE") || changedParam.equals("STOCK")) {
                if(stockId != null) {
                    pricingResponse.put("STOCKS", new HashMap<>());
                }
            }
        }


        Map<String, PricingAttribute> applicableAttributes = new LinkedHashMap<>();
        List<Integer> qtyBreaks = getRequiredQtyBreaks(pricingRequest);
        PricingTemplate pricingTemplate = pricingMatrix.getPricingTemplate(customQuoteRequest ? "CUSTOM" : styleGroupId);
        PricingTemplate commonTemplate = pricingMatrix.getPricingTemplate("COMMON");
        if(UtilValidate.isNotEmpty(styleId) && UtilValidate.isNotEmpty(styleGroupId) && UtilValidate.isNotEmpty(stockId) && UtilValidate.isNotEmpty(stockTypeId)) {
            Map<String, Object> style = CalculatorHelper.getStyle(delegator, UtilMisc.toMap("id", styleId));
            Map<String, Object> styleGroup = CalculatorHelper.getStyleGroup(delegator, UtilMisc.toMap("id", styleGroupId));
            Map<String, Object> stock = CalculatorHelper.getStock(delegator, UtilMisc.toMap("id", stockId));
            Map<String, Object> stockType = CalculatorHelper.getStockType(delegator, UtilMisc.toMap("id", stockTypeId));
            details.append(styleId).append (delim).append(style.get("styleDescription")).append(delim).append(styleGroup.get("styleGroupName")).append(delim).append(stock.get("stockName")).append(delim).append(stockType.get("stockTypeName"));
        } else {
            if(UtilValidate.isNotEmpty(styleId) || UtilValidate.isNotEmpty(customStyle)) {
                if (styleId == null) {
                    details.append(customStyle);
                } else {
                    Map<String, Object> style = CalculatorHelper.getStyle(delegator, UtilMisc.toMap("id", styleId));
                    details.append(styleId).append(delim).append(style.get("styleDescription"));
                }
            }

            if(UtilValidate.isNotEmpty(styleGroupId) || UtilValidate.isNotEmpty(customStyleGroup)) {
                if (styleGroupId == null) {
                    details.append(delim).append(customStyleGroup);
                } else {
                    Map<String, Object> styleGroup = CalculatorHelper.getStyleGroup(delegator, UtilMisc.toMap("id", styleGroupId));
                    details.append(delim).append(styleGroup.get("styleGroupName"));
                }
            }
            if(UtilValidate.isNotEmpty(stockId) || UtilValidate.isNotEmpty(customStock)) {
                if (stockId == null) {
                    details.append(delim).append(customStock);
                } else {
                    Map<String, Object> stock = CalculatorHelper.getStock(delegator, UtilMisc.toMap("id", stockId));
                    details.append(delim).append(stock.get("stockName"));
                }
            }
            if(UtilValidate.isNotEmpty(stockTypeId) || UtilValidate.isNotEmpty(customStockType)) {
                if (stockTypeId == null) {
                    details.append(delim).append(customStockType);
                } else {
                    Map<String, Object> stockType = CalculatorHelper.getStockType(delegator, UtilMisc.toMap("id", stockTypeId));
                    details.append(delim).append(stockType.get("stockTypeName"));
                }
            }
        }

        if(UtilValidate.isNotEmpty(stockTypeId) && !customQuoteRequest) {
            applicableAttributes.put(getStockTypes(delegator, UtilMisc.toList(stockTypeId)).get(0).getString("stockTypeName") + " - Add-on Price", pricingTemplate.getPricingAttribute(stockTypeId, vendorId));
        }
        PricingAttribute grossMarkupPercentage = commonTemplate.getPricingAttribute("DEFAULT_MARKUP_PERCENTAGE", vendorId);
        PricingAttribute grossMarkupDollar = commonTemplate.getPricingAttribute("DEFAULT_MARKUP_DOLLAR", vendorId);


        List<Map<String, Object>> printOptions = (List<Map<String, Object>>) pricingRequest.get("PRINT_OPTIONS");

        boolean hasPrinting = false;
        boolean hasFoilStamping = false;
        boolean hasEmbossing = false;
        boolean applyImageDiscount = false;
        for(Map<String, Object> printOption : printOptions) {
            switch ((String)printOption.get("PRINT_OPTION_NAME")) {
                case "OFFSET":
                    hasPrinting = true;
                    List<Map<String, Object>> sides = (List<Map<String, Object>>) printOption.get("SIDES");

                    if(sides.size() > 0) {
                        Map<String, Object> side1 = sides.get(0);
                        String printMethod = (String)side1.get("PRINT_METHOD");
                        int numOfInks = Integer.parseInt((String)side1.get("NUMBER_OF_INKS"));
                        int colorWashes = Integer.parseInt((String)side1.get("COLOR_WASHES"));
                        int plateChanges = Integer.parseInt((String)side1.get("PLATE_CHANGES"));
                        boolean heavyInkCoverage = side1.get("HEAVY_COVERAGE").equals("Y");

                        if(printMethod.equals("PMS_OFFSET")) {
                            details.append(delim).append("PMS Offset Printing on Side 1");
                            if(!customQuoteRequest) {
                                applicableAttributes.put("PMS Offset - Base Price", pricingTemplate.getPricingAttribute("PRINT_1C", vendorId));
                            }
                            numOfInks --;
                        } else if(printMethod.equals("FOUR_CP")) {
                            details.append(delim).append("Four Color Printing on Side 1");
                            if(!customQuoteRequest) {
                                applicableAttributes.put("Four Color Process - Base Price", pricingTemplate.getPricingAttribute("PRINT_4C", vendorId));
                            }
                        }
                        if(numOfInks + 1 > 0) {
                            details.append(delim).append((numOfInks + 1) + " Ink Color(s) on Side 1");
                        }
                        if(!customQuoteRequest) {
                            for (int i = 0; i < numOfInks; i++) {
                                applicableAttributes.put("Side 1 - Additional Ink Add-on Price - " + (i + 1), pricingTemplate.getPricingAttribute("ADDITIONAL_COLOR_PER_SIDE", vendorId));
                            }
                        }
                        if(colorWashes > 0) {
                            details.append(delim).append(colorWashes + " Color Washes on Side 1");
                        }
                        if(!customQuoteRequest) {
                            for (int i = 0; i < colorWashes; i++) {
                                applicableAttributes.put("Side 1 - Color Wash Add-on Price - " + (i + 1), commonTemplate.getPricingAttribute(heavyInkCoverage ? "COLOR_WASH_WITH_HEAVY_COVERAGE" : "COLOR_WASH", vendorId));
                            }
                        }
                        if(plateChanges > 0) {
                            details.append(delim).append(plateChanges + " Plate Change(s) on Side 1");
                        }
                        if(!customQuoteRequest) {
                            for (int i = 0; i < plateChanges; i++) {
                                applicableAttributes.put("Side 1 - Plate Change Add-on Price - " + (i + 1), commonTemplate.getPricingAttribute("PLATE_CHANGE", vendorId));
                            }
                        }
                        if(heavyInkCoverage) {
                            details.append(delim).append("Heavy Ink Coverage on Side 1");
                            if(!customQuoteRequest) {
                                applicableAttributes.put("Side 1 - Heavy Coverage Add-on Price ", pricingTemplate.getPricingAttribute("HEAVY_COVERAGE_PER_SIDE", vendorId));
                            }
                        }
                    }

                    if(sides.size() > 1) {
                        Map<String, Object> side2 = sides.get(1);
                        String printMethod = (String)side2.get("PRINT_METHOD");
                        int numOfInks = Integer.parseInt((String)side2.get("NUMBER_OF_INKS"));
                        int colorWashes = Integer.parseInt((String)side2.get("COLOR_WASHES"));
                        int plateChanges = Integer.parseInt((String)side2.get("PLATE_CHANGES"));
                        boolean heavyInkCoverage = side2.get("HEAVY_COVERAGE").equals("Y");

                        if(printMethod.equals("PMS_OFFSET")) {
                            details.append(delim).append("PMS Offset Printing on Side 2");
                            //all ink colors on side 2 are considered as additional color
                        } else if(printMethod.equals("FOUR_CP")) {
                            details.append(delim).append("Four Color Printing on Side 1");
                            if(!customQuoteRequest) {
                                applicableAttributes.put("Four Color Process - 2nd Side Price", pricingTemplate.getPricingAttribute("PRINT_4C_2ND_SIDE", vendorId));
                            }
                        }
                        if(numOfInks + 1 > 0) {
                            details.append(delim).append(numOfInks + " Ink Color(s) on Side 2");
                        }
                        if(!customQuoteRequest) {
                            for (int i = 0; i < numOfInks; i++) {
                                applicableAttributes.put("Side 2 - Additional Ink Add-on Price - " + (i + 1), pricingTemplate.getPricingAttribute("ADDITIONAL_COLOR_PER_SIDE", vendorId));
                            }
                        }
                        if(colorWashes > 0) {
                            details.append(delim).append(colorWashes + " Color Washes on Side 2");
                        }
                        if(!customQuoteRequest) {
                            for (int i = 0; i < colorWashes; i++) {
                                applicableAttributes.put("Side 2 - Color Wash Add-on Price - " + (i + 1), pricingTemplate.getPricingAttribute("ADDITIONAL_COLOR_PER_SIDE", vendorId));
                            }
                        }
                        if(plateChanges > 0) {
                            details.append(delim).append(plateChanges + " Plate Change(s) on Side 2");
                        }
                        if(!customQuoteRequest) {
                            for (int i = 0; i < plateChanges; i++) {
                                applicableAttributes.put("Side 2 - Plate Change Add-on Price - " + (i + 1), pricingTemplate.getPricingAttribute("ADDITIONAL_COLOR_PER_SIDE", vendorId));
                            }
                        }
                        if(heavyInkCoverage) {
                            details.append(delim).append("Heavy Ink Coverage on Side 2");
                            if(!customQuoteRequest) {
                                applicableAttributes.put("Side 2 - Heavy Coverage Add-on Price ", pricingTemplate.getPricingAttribute("HEAVY_COVERAGE_PER_SIDE", vendorId));
                            }
                        }
                    }
                    break;

                case "FOIL_STAMPING":
                    hasFoilStamping = true;
                    List<Map<String, Object>> foilRuns = (List<Map<String, Object>>) printOption.get("RUNS");
                    details.append(delim).append("Foil Stamping");
                    if(!customQuoteRequest) {
                        for (int i = 0; i < foilRuns.size(); i++) {
                            if (hasPrinting || i > 0) {
                                applicableAttributes.put("Foil Stamping - Add-on Price" + (i + 1), commonTemplate.getPricingAttribute("FOIL_STAMP_RUN_ADD_ON", vendorId));
                            } else {
                                applicableAttributes.put("Foil Stamping - Base Price", pricingTemplate.getPricingAttribute("FOIL_STAMP_BASE", vendorId));
                            }

                            Map<String, Object> run = foilRuns.get(i);
                            List<String> images = (List<String>) run.get("IMAGES");
                            for (int j = 0; j < images.size(); j++) {
                                if (i == 0 && !hasPrinting && !applyImageDiscount && images.get(j).equals("15")) {
                                    applyImageDiscount = true;
                                }
                                if (j == 0) {
                                    continue;
                                }
                                applicableAttributes.put("Foil Stamping - RUN " + (i + 1) + " - Additional Image Add-on Price" + j, commonTemplate.getPricingAttribute("FOIL_STAMP_IMAGE_ADD_ON", vendorId));
                            }
                        }
                    }
                    break;

                case "EMBOSSING":
                    hasEmbossing = true;
                    applyImageDiscount = false;
                    List<Map<String, Object>> embossSides = (List<Map<String, Object>>) printOption.get("SIDES");
                    details.append(delim).append("Embossing").append(delim).append(embossSides.size() == 1 ? "One Side" : "Two Sides");
                    if(!customQuoteRequest) {
                        for (int k = 0; k < embossSides.size(); k++) {
                            List<Map<String, Object>> embossRuns = (List<Map<String, Object>>) embossSides.get(k).get("RUNS");
                            for (int i = 0; i < embossRuns.size(); i++) {
                                if (hasPrinting || hasFoilStamping || i > 0 || k > 0) {
                                    applicableAttributes.put("Side " + (k + 1) + " Embossing Add-on Price - RUN" + (i + 1), commonTemplate.getPricingAttribute("EMBOSS_RUN_ADD_ON", vendorId));
                                } else {
                                    applicableAttributes.put("Embossing - Base Price", pricingTemplate.getPricingAttribute("EMBOSS_BASE", vendorId));
                                }
                                Map<String, Object> run = embossRuns.get(i);
                                List<String> images = (List<String>) run.get("IMAGES");
                                for (int j = 0; j < images.size(); j++) {
                                    if (j == 0) {
                                        continue;
                                    }
                                    applicableAttributes.put("Side " + (k + 1) + " Embossing - RUN" + (i + 1) + " - Additional Image Add-on Price" + j, commonTemplate.getPricingAttribute("EMBOSS_IMAGE_ADD_ON", vendorId));
                                }
                            }
                        }
                    }
                    break;
            }
        }

        List<String[]> pricingDetails = new ArrayList<>();

        int numOfQtyBreaks = qtyBreaks.size();
        BigDecimal[] totals = new BigDecimal[numOfQtyBreaks];
        BigDecimal[] subTotal = new BigDecimal[numOfQtyBreaks];
        for (int i = 0; i < numOfQtyBreaks; i++) {
            totals[i] = BigDecimal.ZERO;
        }

        String[] qtyBreakDetails = new String[numOfQtyBreaks + 1];
        qtyBreakDetails[0] = "Quantity";
        int idx = 1;
        for(Integer qty : qtyBreaks) {
            qtyBreakDetails[idx++] = qty.toString();
        }
        pricingDetails.add(0, qtyBreakDetails);

        if(hasPrinting || hasFoilStamping || hasEmbossing || customQuoteRequest) {

            //Item upcharge
            if (!customQuoteRequest && !pricingTemplate.getPricingAttribute(styleId, "ITEM_UPCHARGE", vendorId).isEmpty()) {
                applicableAttributes.put(styleId + " - Upcharge", pricingTemplate.getPricingAttribute(styleId, "ITEM_UPCHARGE", vendorId));
            }

            //Image discount
            if (!customQuoteRequest && applyImageDiscount) {
                applicableAttributes.put("Foil Stamping Image Discount", pricingTemplate.getPricingAttribute("DISCOUNT_15_SQ_INCH", vendorId));
            }

            //Addons
            List<Map<String, Object>> addons = (List<Map<String, Object>>) pricingRequest.get("ADDONS");
            List<String> customAddons = (List<String>) pricingRequest.get("CUSTOM_ADDONS");
            if (addons != null && !addons.isEmpty()) {
                for (int i = 0; i < addons.size(); i++) {
                    Map<String, Object> addon = addons.get(i);
                    switch ((String) addon.get("ADDON_NAME")) {
                        case "COATING":
                            List<String> coatingAddons = (List<String>) addon.get("SIDES");
                            for (int j = 0; j < coatingAddons.size(); j++) {
                                String coatingAddon = coatingAddons.get(i);
                                applicableAttributes.put(coatingAddon + " - Addon - Side " + (j + 1), pricingTemplate.getPricingAttribute(coatingAddon, vendorId));
                                details.append(delim).append(coatingAddon).append(" on Side " + (j + 1));
                            }
                            break;
                    }
                }
            } else if(customAddons != null && !customAddons.isEmpty()) {
                for(int i = 0; i < customAddons.size(); i ++) {
                    details.append(delim).append(addonsMap.containsKey(customAddons.get(i)) ? addonsMap.get(customAddons.get(i)) : customAddons.get(i));
                }
            }



            for (Map.Entry<String, PricingAttribute> attributeEntry : applicableAttributes.entrySet()) {
                String key = attributeEntry.getKey();
                PricingAttribute pricingAttribute = attributeEntry.getValue();
                String[] pricingDetail = new String[numOfQtyBreaks + 1];
                String detailDescription = key;
                pricingDetail[0] = detailDescription;
                for (int i = 0; i < numOfQtyBreaks; i++) {
                    BigDecimal qtyBreakPrice = pricingAttribute.getPrice(qtyBreaks.get(i), vendorId)[1];
                    qtyBreakPrice = qtyBreakPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
                    totals[i] = totals[i].add(qtyBreakPrice);
                    pricingDetail[i + 1] = qtyBreakPrice.toString();
                }
                pricingDetails.add(pricingDetail);
            }

            if(customQuoteRequest) {
                BigDecimal[] customVendorCost = getCustomVendorCost(pricingRequest, numOfQtyBreaks);
                String[] pricingDetail = new String[numOfQtyBreaks + 1];
                pricingDetail[0] = "Custom Cost";
                for(int i = 0; i < numOfQtyBreaks; i ++) {
                    totals[i] = totals[i].add(customVendorCost[i]);
                    pricingDetail[i + 1] = customVendorCost[i].toString();
                }
                pricingDetails.add(pricingDetail);
            }

            for (int i = 0; i < numOfQtyBreaks; i++) {
                subTotal[i] = totals[i];
            }
            if (totals[0].doubleValue() > 0) {
                String[] grossProfitPercentageDetails = new String[numOfQtyBreaks + 1];
                grossProfitPercentageDetails[0] = "Gross Profit Markup Percentage";
                for (int i = 0; i < numOfQtyBreaks; i++) {
                    if(totals[i].doubleValue() > 0) {
                        BigDecimal markupPercentage = grossMarkupPercentage.getSimplePrice(qtyBreaks.get(i), "")[1];
                        BigDecimal markupPrice = totals[i].multiply(markupPercentage).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
                        markupPrice = markupPrice.setScale(2, BigDecimal.ROUND_HALF_UP);

                        totals[i] = totals[i].add(markupPrice);
                        grossProfitPercentageDetails[i + 1] = markupPrice.toString();
                    }
                }
                pricingDetails.add(grossProfitPercentageDetails);

                String[] grossProfitDollarDetails = new String[numOfQtyBreaks + 1];
                grossProfitDollarDetails[0] = "Gross Profit Markup Dollar";
                for (int i = 0; i < numOfQtyBreaks; i++) {
                    if(totals[i].doubleValue() > 0) {
                        BigDecimal markupDollar = grossMarkupDollar.getSimplePrice(qtyBreaks.get(i), "")[1];
                        markupDollar = markupDollar.setScale(2, BigDecimal.ROUND_HALF_UP);

                        totals[i] = totals[i].add(markupDollar);
                        grossProfitDollarDetails[i + 1] = markupDollar.toString();
                    }
                }
                pricingDetails.add(grossProfitDollarDetails);

                Map<String, List<String>> customMarkupsAndDiscountsMap = getCustomMarksupsAndDiscounts(pricingRequest);

                if (customMarkupsAndDiscountsMap.containsKey("artChargeHours")) {
                    List<String> hours = customMarkupsAndDiscountsMap.get("artChargeHours");
                    String[] artChargeMarkupPercentageDetails = new String[numOfQtyBreaks + 1];
                    artChargeMarkupPercentageDetails[0] = "Art Charges Markup";
                    for (int i = 0; i < numOfQtyBreaks; i++) {
                        BigDecimal hour = new BigDecimal(hours.get(i));
                        BigDecimal artCharge = new BigDecimal(75).multiply(hour);
                        artCharge = artCharge.setScale(2, BigDecimal.ROUND_HALF_UP);
                        totals[i] = totals[i].add(artCharge);
                        artChargeMarkupPercentageDetails[i + 1] = artCharge.toString();
                    }
                    pricingDetails.add(artChargeMarkupPercentageDetails);
                }

                if (customMarkupsAndDiscountsMap.containsKey("markupPercentage")) {
                    List<String> markupPercentages = customMarkupsAndDiscountsMap.get("markupPercentage");
                    String[] customMarkupPercentageDetails = new String[numOfQtyBreaks + 1];
                    customMarkupPercentageDetails[0] = "Custom Percentage Markup";
                    for (int i = 0; i < numOfQtyBreaks; i++) {
                        BigDecimal markupPercentage = new BigDecimal(markupPercentages.get(i));
                        BigDecimal markupPrice = totals[i].multiply(markupPercentage).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
                        markupPrice = markupPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
                        totals[i] = totals[i].add(markupPrice);
                        customMarkupPercentageDetails[i + 1] = markupPrice.toString();
                    }
                    pricingDetails.add(customMarkupPercentageDetails);
                }

                if (customMarkupsAndDiscountsMap.containsKey("markupDollar")) {
                    List<String> markupDollars = customMarkupsAndDiscountsMap.get("markupDollar");
                    String[] customMarkupDollareDetails = new String[numOfQtyBreaks + 1];
                    customMarkupDollareDetails[0] = "Custom Dollar Markup";
                    for (int i = 0; i < numOfQtyBreaks; i++) {
                        BigDecimal markupDollar = new BigDecimal(markupDollars.get(i));
                        markupDollar = markupDollar.setScale(2, BigDecimal.ROUND_HALF_UP);
                        totals[i] = totals[i].add(markupDollar);
                        customMarkupDollareDetails[i + 1] = markupDollar.toString();
                    }
                    pricingDetails.add(customMarkupDollareDetails);
                }

                if (customMarkupsAndDiscountsMap.containsKey("discountPercentage")) {
                    List<String> discountPercentages = customMarkupsAndDiscountsMap.get("discountPercentage");
                    String[] customDiscountPercentageDetails = new String[numOfQtyBreaks + 1];
                    customDiscountPercentageDetails[0] = "Custom Percentage Discount";
                    for (int i = 0; i < numOfQtyBreaks; i++) {
                        BigDecimal discountPercentage = new BigDecimal(discountPercentages.get(i));
                        BigDecimal discountPrice = totals[i].multiply(discountPercentage).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(-1));
                        discountPrice = discountPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
                        totals[i] = totals[i].add(discountPrice);
                        customDiscountPercentageDetails[i + 1] = discountPrice.toString();
                    }
                    pricingDetails.add(customDiscountPercentageDetails);
                }

                if (customMarkupsAndDiscountsMap.containsKey("discountDollar")) {
                    List<String> discountDollars = customMarkupsAndDiscountsMap.get("discountDollar");
                    String[] customDiscountDollareDetails = new String[numOfQtyBreaks + 1];
                    customDiscountDollareDetails[0] = "Custom Dollar Discount";
                    for (int i = 0; i < numOfQtyBreaks; i++) {
                        BigDecimal discountDollar = new BigDecimal(discountDollars.get(i)).multiply(new BigDecimal(-1));
                        discountDollar = discountDollar.setScale(2, BigDecimal.ROUND_HALF_UP);
                        totals[i] = totals[i].add(discountDollar);
                        customDiscountDollareDetails[i + 1] = discountDollar.toString();
                    }
                    pricingDetails.add(customDiscountDollareDetails);
                }
                pricingResponse.put("valid", true);
            }
        }
        String[] totalDetails = new String[numOfQtyBreaks + 1];
        String[] unitDetails = new String[numOfQtyBreaks + 1];
        totalDetails[0] = "Total";
        unitDetails[0] = "Each";
        for (int i = 0; i < totals.length; i++) {
            BigDecimal unitPrice = totals[i].multiply(new BigDecimal(1).subtract(CalculatorHelper.discountAmount)).divide(new BigDecimal(qtyBreaks.get(i)), 2, BigDecimal.ROUND_HALF_UP);
            unitDetails[i + 1] = unitPrice.toString();
            totalDetails[i + 1] = unitPrice.multiply(new BigDecimal(qtyBreaks.get(i))).toString();
        }
        pricingDetails.add(totalDetails);
        pricingDetails.add(unitDetails);
        pricingResponse.put("pricingDetails", pricingDetails);
        pricingResponse.put("details", details.toString());
        return pricingResponse;
    }

    private Map<String, List<String>> getCustomMarksupsAndDiscounts(Map<String, Object> pricingRequest) {
        Map<String, List<String>> customMarkupsAndDiscountsMap = new HashMap<>();
        List<Map<String, Object>> customMarkupsAndDiscounts = (List<Map<String, Object>>) pricingRequest.get("MARKUPS_AND_DISCOUNTS");
        customMarkupsAndDiscounts.forEach(cmad -> {
            String type = (String) cmad.get("MARKUP_DISCOUNT_TYPE");
            List<Double> values = (List<Double>)cmad.get("MARKUP_DISCOUNT_VALUE");
            List<String> strValues = new ArrayList<>();
            values.forEach(v -> {
                if (v != null && v.intValue() > 0) {
                    strValues.add(v.toString());
                } else {
                    strValues.add("0.0");
                }
            });
            customMarkupsAndDiscountsMap.put(type, strValues);
        });
        return customMarkupsAndDiscountsMap;
    }

    private List<Integer> getRequiredQtyBreaks(Map<String, Object> pricingRequest) {
        List<Double> _quantities = (List<Double>)pricingRequest.get("QUANTITIES");
        List<Integer> quantities = new ArrayList<>();
        _quantities.forEach(q -> {
            if (q != null && q.intValue() > 0) {
                quantities.add(q.intValue());
            }
        });
        return quantities;
    }
    private BigDecimal[] getCustomVendorCost(Map<String, Object> pricingRequest, int numOfQtyBreaks) {
        BigDecimal[] customVendorCost = new BigDecimal[numOfQtyBreaks];
        if(pricingRequest.containsKey("CUSTOM_VENDOR_COST")) {
            List<Double> vendorCosts = (List<Double>) pricingRequest.get("CUSTOM_VENDOR_COST");
            for(int i = 0; i < numOfQtyBreaks; i ++) {
                customVendorCost[i] = new BigDecimal(Double.toString(vendorCosts.get(i)));
            }
        }

        return customVendorCost;
    }

    public static final String[] STOCK_TYPE = {"STOCK_TYPE", ""};
}