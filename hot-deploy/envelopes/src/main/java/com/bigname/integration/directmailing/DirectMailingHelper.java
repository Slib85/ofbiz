package com.bigname.integration.directmailing;

import com.bigname.integration.click2mail.client.domain.*;
import com.bigname.integration.click2mail.client.request.*;
import com.envelopes.cart.CartEvents;
import com.envelopes.cart.PersistentCart;
import com.envelopes.order.OrderHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.io.FilenameUtils;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.DelegatorFactory;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.jdbc.SQLProcessor;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.order.order.OrderReadHelper;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Manu on 5/2/2018.
 */
public class DirectMailingHelper {

    private static final Map<String, String> directMailingConfig = new HashMap<>();

    private static final Map<String, String> defaultDirectMailingConfig = new HashMap<>();
    static {
        defaultDirectMailingConfig.put(ConfigKey.C2M_API_ENDPOINT.key(), "https://rest.click2mail.com/molpro");
        defaultDirectMailingConfig.put(ConfigKey.C2M_USER_NAME.key(), "BIGNAME");
        defaultDirectMailingConfig.put(ConfigKey.C2M_PASSWORD.key(), "Envelopes5300");
        defaultDirectMailingConfig.put(ConfigKey.BILLING_TYPE.key(), "Invoice");
        defaultDirectMailingConfig.put(ConfigKey.BILLING_NAME.key(), "Manu Prasad");
        defaultDirectMailingConfig.put(ConfigKey.BILLING_ADDRESS1.key(), "5300 New Horizons Blvd");
        defaultDirectMailingConfig.put(ConfigKey.BILLING_ADDRESS2.key(), "");
        defaultDirectMailingConfig.put(ConfigKey.BILLING_CITY.key(), "Amityville");
        defaultDirectMailingConfig.put(ConfigKey.BILLING_STATE.key(), "New York");
        defaultDirectMailingConfig.put(ConfigKey.BILLING_ZIP.key(), "11701");
        defaultDirectMailingConfig.put(ConfigKey.BILLING_CC_TYPE.key(), "AE");
        defaultDirectMailingConfig.put(ConfigKey.BILLING_CC_NUMBER.key(), "370000000000002");
        defaultDirectMailingConfig.put(ConfigKey.BILLING_CC_MONTH.key(), "12");
        defaultDirectMailingConfig.put(ConfigKey.BILLING_CC_YEAR.key(), "23");
        defaultDirectMailingConfig.put(ConfigKey.BILLING_CC_CVV.key(), "123");
    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    private static Map<String, String> CACHED_DEFAULT_COST_ESTIMATE = new HashMap<>();

    public static final String module = DirectMailingHelper.class.getName();

    static void refreshDirectMailingConfig() {
        directMailingConfig.clear();
    }

    public static Map<String, String> getDirectMailingConfig(Delegator... delegator) {
        if(delegator == null || delegator.length == 0 || delegator[0] == null) {
            delegator = new Delegator[] {DelegatorFactory.getDelegator("default")};
        }
        if(directMailingConfig.isEmpty()) {
            List<GenericValue> configParams = null;
            try {
                configParams = EntityQuery.use(delegator[0]).from("DirectMailingConfig").cache(false).queryList();
            } catch (GenericEntityException e) {
                EnvUtil.reportError(e);
                Debug.logError("Error while getting DirectMailing Config from database, continuing with default config:  " + e, module);
            }
            if (UtilValidate.isNotEmpty(configParams)) {
                configParams.forEach(e -> directMailingConfig.put(e.getString("paramName"), e.getString("paramValue")));
            } else {
                directMailingConfig.putAll(defaultDirectMailingConfig);
            }
        }
        return UtilValidate.isEmpty(directMailingConfig) ? defaultDirectMailingConfig : directMailingConfig;
    }


    public static List<Map<String, Object>> getSavedJobs(HttpServletRequest request) throws GenericEntityException {
        List<Map<String, Object>> jobs = new ArrayList<>();

        String partyId = getPartyId(request);
        if(UtilValidate.isEmpty(partyId)) {
            return new ArrayList<>();
        }

        List<EntityCondition> conditionList = new ArrayList<>();
        conditionList.add(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId));
        conditionList.add(EntityCondition.makeCondition("thruDate", EntityOperator.EQUALS, null));


        Delegator delegator = (Delegator) request.getAttribute("delegator");
        List<GenericValue> storedJobs = EntityQuery.use(delegator).select("partyId", "jobNum", "productId", "data", "lastUpdatedStamp").from("DirectMailingJobData").where(conditionList).orderBy("createdStamp").queryList();
        for(GenericValue values : storedJobs) {
            Map<String, Object> data = new HashMap<>();
            String editUrl = "/envelopes/control/directMailingProduct?productId="+values.get("productId") + "&jobNumber=" + values.get("jobNum");
            data.put("partyId", values.get("partyId"));
            data.put("productId", values.get("productId"));
            data.put("jobNumber", values.get("jobNum"));
            data.put("data", values.get("data"));
            data.put("lastUpdatedStamp", dateFormat.format(values.get("lastUpdatedStamp")));
            data.put("editUrl", editUrl);
            jobs.add(data);
        }
        return jobs;
    }

    public static String deactivateJob(HttpServletRequest request, HttpServletResponse response) throws GenericEntityException {
        Map<String, Object> jsonResponse = new HashMap<>();
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        String jobNumber = (String) context.get("jobNumber");

        Delegator delegator = (Delegator) request.getAttribute("delegator");
        GenericValue jobDataGV = EntityQuery.use(delegator).from("DirectMailingJobData").where(UtilMisc.toMap("jobNum", jobNumber, "thruDate", null)).queryOne();
        if (UtilValidate.isNotEmpty(jobDataGV)) {

            jobDataGV.put("thruDate", UtilDateTime.nowTimestamp());
            delegator.createOrStore(jobDataGV);
        }
        jsonResponse.put("success", true);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static List<Map<String, Object>> getSavedDocuments(HttpServletRequest request) throws Exception {
        List<Map<String, Object>> documents = new ArrayList<>();
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        String partyId = getPartyId(request);
        if(UtilValidate.isEmpty(partyId) && UtilValidate.isEmpty(context.get("jobNumber"))) {
            return new ArrayList<>();
        }
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        List<EntityCondition> conditionList = new ArrayList<>();
        if(UtilValidate.isNotEmpty(partyId)) {
            conditionList.add(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId));
            conditionList.add(EntityCondition.makeCondition("thruDate", EntityOperator.EQUALS, null));
            conditionList.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, context.get("productId")));
            List<GenericValue> documentGVs = EntityQuery.use(delegator).from("DirectMailingContent").where(conditionList).orderBy("lastUpdatedStamp DESC").queryList();
            for (GenericValue documentGV : documentGVs) {
                Map<String, Object> documentAttributes = new HashMap<>();
    //            documentAttributes.put("directMailingContentId", documentGV.getString("directMailingContentId"));
                documentAttributes.put("documentId", documentGV.getString("documentId"));
                documentAttributes.put("contentPath", documentGV.getString("contentPath"));
                documentAttributes.put("contentName", documentGV.getString("contentName"));
                documentAttributes.put("lastUpdatedStamp", dateFormat.format(documentGV.get("lastUpdatedStamp")));
    //            documentAttributes.put("documentClass", documentGV.get("documentClass"));
                documents.add(documentAttributes);
            }
        } else {
            Map<String, Object> jobData = getJobData((Delegator)request.getAttribute("delegator"), (String)context.get("jobNumber"));
            Map<String, Object> documentAttributes = new HashMap<>();
            documentAttributes.put("documentId", jobData.get("documentId"));
            documentAttributes.put("contentPath", jobData.get("contentPath"));
            documentAttributes.put("contentName", jobData.get("contentName"));
            documentAttributes.put("lastUpdatedStamp", dateFormat.format(new Date()));
            documents.add(documentAttributes);
        }
        return documents;
    }

    public static void setCartId(HttpServletRequest request, HttpServletResponse response) throws GenericEntityException {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> context = EnvUtil.getParameterMap(request);

        request.setAttribute("saveResponse", true);
        CartEvents.getCart(request, response);
        PersistentCart pCart = (PersistentCart) request.getSession().getAttribute("PersistentCart");

        String cartId = pCart != null ? pCart.getId() : null;
        GenericValue jobData = EntityQuery.use(delegator).from("DirectMailingJobData").where("jobNum", context.get("jobNumber")).queryFirst();
        if(UtilValidate.isEmpty(jobData.get("cartId")) && UtilValidate.isNotEmpty(cartId)) {
            jobData.put("cartId", cartId);
            delegator.store(jobData);
        }
    }

    //COMPLETE
    public static String getPartyId(HttpServletRequest request) {
        GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
        return userLogin != null ? userLogin.getString("partyId") : "";
    }
    //COMPLETE
    public static Map<Long, Map<String, Object>> getOptions(Delegator delegator) throws GenericEntityException {
        Map<Long, Map<String, Object>> options = new HashMap<>();
        List<GenericValue> optionGVs = EntityQuery.use(delegator).select("optionId", "optionValue", "optionType").from("DirectMailingOption").queryList();
        optionGVs.forEach(e -> options.put(e.getLong("optionId"), new HashMap<>(e)));
        return options;
    }

    //COMPLETE
    public static String getOptionValue(Long optionId, Map<Long, Map<String, Object>> options) {
        return options.containsKey(optionId) ? (String) options.get(optionId).get("optionValue") : null;
    }

    //COMPLETE
    public static String getOptionType(Long optionId, Map<Long, Map<String, Object>> options) {
        return options.containsKey(optionId) ? (String) options.get(optionId).get("optionType") : null;
    }

    //COMPLETE
    public static Map<String, Object> getProductOptions(Delegator delegator, String productId) throws GenericEntityException {
        Map<String, Object> PRODUCT_OPTIONS = new LinkedHashMap<>();

        Map<Long, Map<String, Object>> options = getOptions(delegator);
        List<EntityCondition> conditionList1 = new ArrayList<>();
        conditionList1.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId));
        conditionList1.add(EntityCondition.makeCondition("level2OptionId", EntityOperator.EQUALS, null));
        List<GenericValue> levelOneOptions = EntityQuery.use(delegator).from("DirectMailingProductOption").where(conditionList1).orderBy("sequenceNum").queryList();
        levelOneOptions.forEach(e1 -> {
            Map<String, Object> LEVEL1_OPTION = new LinkedHashMap<>();
            LEVEL1_OPTION.put("INDEX", e1.getLong("sequenceNum"));
            long sequenceNum1 = e1.getLong("sequenceNum");
            List<EntityCondition> conditionList2 = new ArrayList<>();
            conditionList2.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId));
            conditionList2.add(EntityCondition.makeCondition("level3OptionId", EntityOperator.EQUALS, null));
            conditionList2.add(EntityCondition.makeCondition("level2OptionId", EntityOperator.NOT_EQUAL, null));
            conditionList2.add(EntityCondition.makeCondition("level1OptionId", EntityOperator.EQUALS, e1.getLong("level1OptionId")));
            List<GenericValue> levelTwoOptions = null;
            try {
                levelTwoOptions = EntityQuery.use(delegator).from("DirectMailingProductOption").where(conditionList2).orderBy("sequenceNum").queryList();
            } catch (GenericEntityException e) {
                e.printStackTrace();
            }
            levelTwoOptions.forEach(e2 -> {
                Map<String, Object> LEVEL2_OPTION = new LinkedHashMap<>();
                LEVEL2_OPTION.put("INDEX", e2.getLong("sequenceNum"));
                long sequenceNum2 = e2.getLong("sequenceNum");
                List<EntityCondition> conditionList3 = new ArrayList<>();
                conditionList3.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId));
                conditionList3.add(EntityCondition.makeCondition("level4OptionId", EntityOperator.EQUALS, null));
                conditionList3.add(EntityCondition.makeCondition("level3OptionId", EntityOperator.NOT_EQUAL, null));
                conditionList3.add(EntityCondition.makeCondition("level1OptionId", EntityOperator.EQUALS, e1.getLong("level1OptionId")));
                conditionList3.add(EntityCondition.makeCondition("level2OptionId", EntityOperator.EQUALS, e2.getLong("level2OptionId")));
                List<GenericValue> levelThreeOptions = null;
                try {
                    levelThreeOptions = EntityQuery.use(delegator).from("DirectMailingProductOption").where(conditionList3).orderBy("sequenceNum").queryList();
                } catch (GenericEntityException e) {
                    e.printStackTrace();
                }
                levelThreeOptions.forEach(e3 -> {
                    Map<String, Object> LEVEL3_OPTION = new LinkedHashMap<>();
                    long sequenceNum3 = e3.getLong("sequenceNum");
                    LEVEL3_OPTION.put("INDEX", e3.getLong("sequenceNum"));
                    List<EntityCondition> conditionList4 = new ArrayList<>();
                    conditionList4.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId));
                    conditionList4.add(EntityCondition.makeCondition("level4OptionId", EntityOperator.NOT_EQUAL, null));
                    conditionList4.add(EntityCondition.makeCondition("level1OptionId", EntityOperator.EQUALS, e1.getLong("level1OptionId")));
                    conditionList4.add(EntityCondition.makeCondition("level2OptionId", EntityOperator.EQUALS, e2.getLong("level2OptionId")));
                    conditionList4.add(EntityCondition.makeCondition("level3OptionId", EntityOperator.EQUALS, e3.getLong("level3OptionId")));
                    List<GenericValue> levelFourOptions = null;
                    try {
                        levelFourOptions = EntityQuery.use(delegator).from("DirectMailingProductOption").where(conditionList4).orderBy("sequenceNum").queryList();
                    } catch (GenericEntityException e) {
                        e.printStackTrace();
                    }
                    levelFourOptions.forEach(e4 -> {
                        Map<String, Object> LEVEL4_OPTION = new LinkedHashMap<>();
                        long sequenceNum4 = e4.getLong("sequenceNum");
                        LEVEL4_OPTION.put("INDEX", e4.getLong("sequenceNum"));
                        String optionType = getOptionType(e4.getLong("level4OptionId"), options);
                        if(LEVEL3_OPTION.containsKey(optionType)) {
                            ((Map<String, Object>) LEVEL3_OPTION.get(optionType)).put(getOptionValue(e4.getLong("level4OptionId"), options), LEVEL4_OPTION);
                        } else {
                            Map<String, Object> LEVEL4_OPTIONS = new LinkedHashMap<>();
                            if(sequenceNum4 > -1) {
                                LEVEL4_OPTIONS.put(getOptionValue(e4.getLong("level4OptionId"), options), LEVEL4_OPTION);
                                LEVEL3_OPTION.put(optionType, LEVEL4_OPTIONS);
                            } else {
                                LEVEL3_OPTION.put(optionType, e4.getString("optionValue"));
                            }

                        }
                    });
                    String optionType = getOptionType(e3.getLong("level3OptionId"), options);
                    if(LEVEL2_OPTION.containsKey(optionType)) {
                        ((Map<String, Object>) LEVEL2_OPTION.get(optionType)).put(getOptionValue(e3.getLong("level3OptionId"), options), LEVEL3_OPTION);
                    } else {
                        Map<String, Object> LEVEL3_OPTIONS = new LinkedHashMap<>();
                        if(sequenceNum3 > -1) {
                            LEVEL3_OPTIONS.put(getOptionValue(e3.getLong("level3OptionId"), options), LEVEL3_OPTION);
                            LEVEL2_OPTION.put(optionType, LEVEL3_OPTIONS);
                        } else {
                            LEVEL2_OPTION.put(optionType, e3.getString("optionValue"));
                        }
                    }
                });
                String optionType = getOptionType(e2.getLong("level2OptionId"), options);
                if(LEVEL1_OPTION.containsKey(optionType)) {
                    ((Map<String, Object>) LEVEL1_OPTION.get(optionType)).put(getOptionValue(e2.getLong("level2OptionId"), options), LEVEL2_OPTION);
                } else {
                    Map<String, Object> LEVEL2_OPTIONS = new LinkedHashMap<>();
                    if(sequenceNum2 > -1) {
                        LEVEL2_OPTIONS.put(getOptionValue(e2.getLong("level2OptionId"), options), LEVEL2_OPTION);
                        LEVEL1_OPTION.put(optionType, LEVEL2_OPTIONS);
                    } else {
                        LEVEL1_OPTION.put(optionType, e2.getString("optionValue"));
                    }
                }
            });
            String optionType = getOptionType(e1.getLong("level1OptionId"), options);
            if(PRODUCT_OPTIONS.containsKey(optionType)) {
                ((Map<String, Object>) PRODUCT_OPTIONS.get(optionType)).put(getOptionValue(e1.getLong("level1OptionId"), options), LEVEL1_OPTION);
            } else {
                Map<String, Object> LEVEL1_OPTIONS = new LinkedHashMap<>();
                if(sequenceNum1 > -1) {
                    LEVEL1_OPTIONS.put(getOptionValue(e1.getLong("level1OptionId"), options), LEVEL1_OPTION);
                    PRODUCT_OPTIONS.put(optionType, LEVEL1_OPTIONS);
                } else {
                    PRODUCT_OPTIONS.put(optionType, e1.getString("optionValue"));
                }
            }

        });

        return PRODUCT_OPTIONS;
    }

    //COMPLETE
    public static boolean clearDefaultCostEstimateCache() {
        CACHED_DEFAULT_COST_ESTIMATE.clear();
        return true;
    }

    //COMPLETE
    public static String getDefaultCostEstimate(String productId){
        if (!CACHED_DEFAULT_COST_ESTIMATE.containsKey(productId)) {
            String documentClass = "";
            String layout = "";
            String envelope = "";
            String paperType = "";
            String printOption = "";
            if (productId.equals("C2M-L85X11")) {
                documentClass = "Letter 8.5 x 11";
                layout = "Address on Separate Page";
                envelope = "Best Fit";
                paperType = "White 24#";
                printOption = "Printing One side";
            } else if (productId.equals("C2M-N425X55")) {
                documentClass = "Notecard 4.25 x 5.5";
                layout = "Notecard -Single Sided";
                envelope = "Address Printed On Envelope";
                paperType = "120# White Uncoated";
                printOption = "Printing both sides";
            } else {
                documentClass = "Note Card - Folded 4.25 x 5.5";
                layout = "Notecard - Folded";
                envelope = "Address Printed On Envelope";
                paperType = "120# White Uncoated";
                printOption = "Printing both sides";
            }
            Map<String, Object> formParams = new HashMap<>();
            formParams.put("documentClass", documentClass);
            formParams.put("layout", layout);
            formParams.put("productionTime", "Next Day");
            formParams.put("envelope", envelope);
            formParams.put("color", "Full Color");
            formParams.put("paperType", paperType);
            formParams.put("printOption", printOption);
            formParams.put("mailClass", "First Class");
            formParams.put("quantity", "50");
            formParams.put("numberOfPages", "1");
            CACHED_DEFAULT_COST_ESTIMATE.put(productId, getCostEstimate(formParams));

        }
        return CACHED_DEFAULT_COST_ESTIMATE.get(productId);
    }

    //TODO - Confirm
    public static String getCostEstimates(Map<String, Object> formParams) {
        Map<Integer, Map> priceList = new HashMap<>();
        String[] quantities = ((String) formParams.get("quantityList")).split(",");
        try {
            for(String newQuantity : quantities) {
                formParams.put("quantity", newQuantity);

                CostEstimate cost = DirectMailingUtil.getCostEstimate(new CostEstimateRequest(formParams));
                if (UtilValidate.isNotEmpty(cost) && cost.getStatus().equals("0")) {
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, String> productionCost = mapper.readValue(new Gson().toJson(cost.getProductionCost()), new TypeReference<Map<String,String>>(){});
                    Map<String, String> standardCost = mapper.readValue(new Gson().toJson(cost.getStandardCost()), new TypeReference<Map<String,String>>(){});
                    String totalCost = new BigDecimal(productionCost.get("subtotal")).add(new BigDecimal(standardCost.get("subtotal"))).toPlainString();
                    String quantity = productionCost.get("quantity");

                    /** calculate the effective cost by adding the margin price **/
                    priceList.put(Integer.valueOf(newQuantity), new Gson().fromJson(DirectMailingUtil.getEffectivePrice(new BigDecimal(totalCost), new BigDecimal(quantity), newQuantity), HashMap.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Gson().toJson(priceList);
    }

    public static String getCostEstimate(Map<String, Object> formParams) {
        String price = "";
        String originalQuantity = (String) formParams.get("quantity");
        try {
            CostEstimate cost = DirectMailingUtil.getCostEstimate(new CostEstimateRequest(formParams));
            if (UtilValidate.isNotEmpty(cost) && cost.getStatus().equals("0")) {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> productionCost = mapper.readValue(new Gson().toJson(cost.getProductionCost()), new TypeReference<Map<String,String>>(){});
                Map<String, String> standardCost = mapper.readValue(new Gson().toJson(cost.getStandardCost()), new TypeReference<Map<String,String>>(){});
                String totalCost = new BigDecimal(productionCost.get("subtotal")).add(new BigDecimal(standardCost.get("subtotal"))).toPlainString();
                String quantity = productionCost.get("quantity");

                /** calculate the effective cost by adding the margin price **/
                price = DirectMailingUtil.getEffectivePrice(new BigDecimal(totalCost), new BigDecimal(quantity), originalQuantity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return price;
    }

    @SuppressWarnings("unchecked")
    //public static Map<String, Object> getJobData(Delegator delegator, String jobNumber, String productId) throws GenericEntityException {
    public static Map<String, Object> getJobData(Delegator delegator, HttpServletRequest request) throws GenericEntityException {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        String partyId = getPartyId(request);
        String cartId = PersistentCart.getPersistentCartIdFromCookie(request, null);
        String jobNumber = (String) context.get("jobNumber");
        String productId = (String) context.get("productId");

        Map<String, Object> jobData               = new HashMap<>();
        Map<String, Object> productOptions        = getProductOptions(delegator, productId);
        Map<String, Object> layoutOptions         = (Map<String, Object>) productOptions.get("LAYOUT_OPTIONS");
        String layout                             = layoutOptions.keySet().toArray(new String[0])[0];
        Map<String, Object> layoutOption          = (Map<String, Object>)layoutOptions.get(layout);
        Map<String, Object> productionTimeOptions = (Map<String, Object>) layoutOption.get("PRODUCTION_TIME_OPTIONS");
        String productionTime                     = productionTimeOptions.keySet().toArray(new String[0])[0];
        Map<String, Object> productionTimeOption  = (Map<String, Object>)layoutOptions.get(productionTime);
        Map<String, Object> envelopeOptions       = (Map<String, Object>) layoutOption.get("ENVELOPE_OPTIONS");
        String envelope                           = envelopeOptions.keySet().toArray(new String[0])[0];
        Map<String, Object> envelopeOption        = (Map<String, Object>) envelopeOptions.get(envelope);
        Map<String, Object> printColorOptions     = (Map<String, Object>) envelopeOption.get("PRINT_COLOR_OPTIONS");
        String printColor                         = printColorOptions.keySet().toArray(new String[0])[0];
        Map<String, Object> printColorOption      = (Map<String, Object>) printColorOptions.get(printColor);
        Map<String, Object> paperColorOptions     = (Map<String, Object>) printColorOption.get("PAPER_COLOR_OPTIONS");
        String paperColor                         = paperColorOptions.keySet().toArray(new String[0])[0];
        Map<String, Object> printOptions          = (Map<String, Object>) envelopeOption.get("PRINT_OPTIONS");
        String printOption                        = printOptions.keySet().toArray(new String[0])[0];
        Map<String, Object> mailClassOptions      = (Map<String, Object>) envelopeOption.get("MAIL_CLASS_OPTIONS");
        String mailClassOption                    = mailClassOptions.keySet().toArray(new String[0])[0];

        if(UtilValidate.isEmpty(jobNumber)) {
            jobData.put("jobId",                        "0");
            jobData.put("jobNumber",                    "0");
            jobData.put("estimatedPrice",               getDefaultCostEstimate(productId));
            jobData.put("layout",                       layout);
            jobData.put("productionTime",               productionTime);
            jobData.put("envelope",                     envelope);
            jobData.put("color",                        printColor);
            jobData.put("paperType",                    paperColor);
            jobData.put("printOption",                  printOption);
            jobData.put("mailClass",                    mailClassOption);
            jobData.put("quantity",                     "50");
            jobData.put("numberOfPages",                "1");
            jobData.put("addressingData",               new HashMap<>());
            jobData.put("addressId",                    "0");
            jobData.put("documentId",                   "0");
            jobData.put("dataGroupId",                  "0");
            //jobData.put("addressListCreatedTimestamp",  "0");
            jobData.put("hasAddressModified",           false);
            jobData.put("fileUpload",                   new ArrayList<>());
            jobData.put("contentPath",                  "");
            jobData.put("contentName",                  "");
            jobData.put("proof",                        "");
            Map<String, String> returnAddress           = new HashMap<>();
            returnAddress.put("rtnName",                "");
            returnAddress.put("rtnOrganization",        "");
            returnAddress.put("rtnAddress1",            "");
            returnAddress.put("rtnAddress2",            "");
            returnAddress.put("rtnCity",                "");
            returnAddress.put("rtnState",               "");
            returnAddress.put("rtnZip",                 "");
            jobData.put("returnAddress",                returnAddress);

        } else if (UtilValidate.isEmpty(partyId) && UtilValidate.isEmpty(cartId) ) {

            jobData.put("errorCode", "501"); // Not a logged in user
            return jobData;

        } else {

            GenericValue savedJob = getSavedJobData(delegator, jobNumber, partyId, cartId);
            if (UtilValidate.isEmpty(savedJob)) {
                jobData.put("errorCode", "500"); // Invalid job number
                return jobData;
            }

            Map<String, Object> savedData = new Gson().fromJson(savedJob.getString("data"), Map.class);

            jobData.put("jobId",                        savedData.get("jobId"));
            jobData.put("jobNumber",                    savedJob.getString("jobNum"));
            jobData.put("layout",                       savedData.get("layout"));
            jobData.put("productionTime",               savedData.get("productionTime"));
            jobData.put("envelope",                     savedData.get("envelope"));
            jobData.put("color",                        savedData.get("color"));
            jobData.put("paperType",                    savedData.get("paperType"));
            jobData.put("printOption",                  savedData.get("printOption"));
            jobData.put("mailClass",                    savedData.get("mailClass"));
            jobData.put("quantity",                     savedData.get("quantity"));
            jobData.put("numberOfPages",                savedData.get("numberOfPages"));
            jobData.put("addressingData",               new HashMap<>());
            jobData.put("addressId",                    savedData.get("addressId"));
            jobData.put("documentId",                   savedData.get("documentId"));
            jobData.put("dataGroupId",                  savedData.get("dataGroupId"));
            //jobData.put("addressListCreatedTimestamp",  savedData.get("addressListCreatedTimestamp"));
            jobData.put("hasAddressModified",           savedData.get("hasAddressModified"));
            jobData.put("fileUpload",                   new ArrayList<>());
            jobData.put("contentPath",                  savedData.get("contentPath"));
            jobData.put("contentName",                  savedData.get("contentName"));
            jobData.put("proof",                        savedData.get("proof"));

            if(savedData.containsKey("estimatedPrice")) {
                jobData.put("estimatedPrice", new Gson().toJson(savedData.get("estimatedPrice")));
            } else {
                jobData.put("price", new Gson().toJson(savedData.get("price")));
            }

            Map<String, String> returnAddress = new HashMap<>();
            returnAddress.put("rtnName", (String) savedData.get("rtnName"));
            returnAddress.put("rtnOrganization", (String) savedData.get("rtnOrganization"));
            returnAddress.put("rtnAddress1", (String) savedData.get("rtnAddress1"));
            returnAddress.put("rtnAddress2", (String) savedData.get("rtnAddress2"));
            returnAddress.put("rtnCity", (String) savedData.get("rtnCity"));
            returnAddress.put("rtnState", (String) savedData.get("rtnState"));
            returnAddress.put("rtnZip", (String) savedData.get("rtnZip"));
            jobData.put("returnAddress", returnAddress);

        }
        return jobData;
    }

    private static boolean isValidJobIdentifiers(Delegator delegator, String jobNumber, String jobId, String partyId) throws Exception {

        /*if(UtilValidate.isNotEmpty(partyId) && !"0".equals(jobNumber)) {
            return EntityQuery.use(delegator).from("DirectMailingJobData").where("jobNum", jobNumber.trim(), "partyId", partyId, "thruDate", null).queryCount() == 1;
        } else {
            return true;
        }*/
        return true;
    }


    /**
     *
     * Scenario 1# jobNumber -   0, partyId -   '', jobId -   0, saveAndExit - F  // Clicking Next on Step 3 and user IS NOT logged in
     * Scenario 2# jobNumber -   0, partyId - ! '', jobId -   0, saveAndExit - F  // Clicking Next on Step 3 and user IS logged in
     * Scenario 3# jobNumber -   0, partyId - ! '', jobId -   0, saveAndExit - T  // Clicking Save & Exit on Step 1, 2 or 3 before clicking Next on Step 3 and user IS logged in
     * Scenario 4# jobNumber - ! 0, partyId - ! '', jobId -   0, saveAndExit - T  // Clicking Save & Exit on Step 1, 2 or 3 (Re-save) before clicking Next on Step 3 and user IS logged in
     * Scenario 5# jobNumber - ! 0, partyId - ! '', jobId - ! 0, saveAndExit - F  // After clicking Next on Step 3 and going back and clicking Next on Step 3 again and user IS logged in
     * Scenario 6# jobNumber - ! 0, partyId -   '', jobId - ! 0, saveAndExit - F  // After clicking Next on Step 3 and going back and clicking Next on Step 3 again and user IS NOT logged in
     * Scenario 7# jobNumber - ! 0, partyId - ! '', jobId - ! 0, saveAndExit - T  // After clicking Next on Step 3 and going back and clicking Save & Exit on Step 1, 2 or 3 and user IS logged in
     * Scenario 8# jobNumber - ! 0, partyId - ! '', jobId - ! 0, saveAndExit - T  // Clicking Save & Exit from Step 4 and user IS logged in
     * Scenario 9# jobNumber - ! 0, partyId -   '', jobId - ! 0, saveAndExit - T  // Clicking Save & Exit from Step 4 and user IS NOT logged in
     *
     *
     *
     * @param delegator
     * @param context
     * @return
     * @throws Exception
     */
   /* static Map<String, Object> saveJob(Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> context) throws Exception {
        boolean success            = false;
        Map<String, Object> result = new HashMap<>();
        String jobNumber           = (String) context.get("jobNumber");
        String jobId               = (String) context.get("jobId");
        String partyId             = (String) context.get("partyId");
        boolean saveAndExitMode    = "true".equals(context.get("saveAndExitMode"));

        if(isValidJobIdentifiers(delegator, jobNumber, jobId, partyId)) {

            GenericValue jobDataGV = EntityQuery.use(delegator).from("DirectMailingJobData").where(UtilMisc.toMap("jobNum", jobNumber)).queryOne();
            if (UtilValidate.isEmpty(jobDataGV)) {
                jobDataGV = delegator.makeValue("DirectMailingJobData", "jobNum", delegator.getNextSeqId("DirectMailingJobData"));
            }
            if (!saveAndExitMode) {
                jobDataGV.put("progressStatus", "");
            }
            delegator.createOrStore(jobDataGV);
            context.put("jobNumber", jobDataGV.get("jobNum"));
            result.put("jobNumber", jobDataGV.get("jobNum"));
            dispatcher.runAsync("saveJobDetails", context);
            success = true;
        } else {
            result.put("error", "Invalid JobNumber");
        }
        result.put("success", success);

        return result;
    }
*/
    static String getJobNumber(Delegator delegator, Map<String, Object> context) throws Exception {
        String jobNumber           = (String) context.get("jobNumber");
        GenericValue jobDataGV;
        if (UtilValidate.isEmpty(jobNumber) || "0".equals(jobNumber)) {
            jobDataGV = delegator.makeValue("DirectMailingJobData", "jobNum", delegator.getNextSeqId("DirectMailingJobData"));
            delegator.createOrStore(jobDataGV);
            jobNumber = jobDataGV.getString("jobNum");
        } else {
            jobDataGV = EntityQuery.use(delegator).from("DirectMailingJobData").where(UtilMisc.toMap("jobNum", jobNumber)).queryOne();
            jobDataGV.put("progressStatus", "");
            delegator.createOrStore(jobDataGV);
        }
        return jobNumber;
    }

    static Map<String, Object> saveJob(Delegator delegator, Map<String, Object> context) throws Exception {

        boolean success            = false;
        Map<String, Object> result = new HashMap<>();
        boolean saveAndExitMode    = "true".equals(context.get("saveAndExitMode"));
        String jobNumber           = (String) context.get("jobNumber");
        String jobId               = (String) context.get("jobId");
        String partyId             = (String) context.get("partyId");
        boolean hasAddressModified = "true".equals(context.get("hasAddressModified"));

        Map<String, Object> jobData        = new HashMap<>();
        Map<String, Object> estimatedPrice = new HashMap<>();
        Map<String, Object> actualPrice    = new HashMap<>();
        Map<String, Object> fileUpload     = new HashMap<>();
        Map<String, Object> oldJobData     = new HashMap<>();
        oldJobData.put("dataGroupId", "");

        GenericValue jobDataGV = EntityQuery.use(delegator).from("DirectMailingJobData").where(UtilMisc.toMap("jobNum", jobNumber)).queryOne();
        if (UtilValidate.isNotEmpty(jobDataGV.getString("data"))) {
            oldJobData = new Gson().fromJson(jobDataGV.getString("data"), Map.class);

        }

        jobData.put("documentClass", context.get("documentClass"));
        jobData.put("productId", context.get("productId"));
        jobData.put("jobId", context.get("jobId"));
        jobData.put("layout", context.get("layout"));
        jobData.put("productionTime", context.get("productionTime"));
        jobData.put("envelope", context.get("envelope"));
        jobData.put("color", context.get("color"));
        jobData.put("paperType", context.get("paperType"));
        jobData.put("printOption", context.get("printOption"));
        jobData.put("mailClass", context.get("mailClass"));
        jobData.put("documentId", context.get("documentId"));
        jobData.put("contentPath", context.get("contentPath"));
        jobData.put("contentName", context.get("contentName"));
        jobData.put("addressId", context.get("addressId"));
        jobData.put("dataGroupId", context.get("dataGroupId"));
        jobData.put("quantity", context.get("quantity"));
        jobData.put("numberOfPages", context.get("numberOfPages"));
        jobData.put("rtnName", context.get("returnAddress[rtnName]"));
        jobData.put("rtnOrganization", context.get("returnAddress[rtnOrganization]"));
        jobData.put("rtnAddress1", context.get("returnAddress[rtnAddress1]"));
        jobData.put("rtnAddress2", context.get("returnAddress[rtnAddress2]"));
        jobData.put("rtnCity", context.get("returnAddress[rtnCity]"));
        jobData.put("rtnState", context.get("returnAddress[rtnState]"));
        jobData.put("rtnZip", context.get("returnAddress[rtnZip]"));
        //jobData.put("addressListCreatedTimestamp", context.get("addressListCreatedTimestamp"));
        jobData.put("hasAddressModified", context.get("hasAddressModified"));
        jobData.put("proof", context.get("proof"));



        if (context.containsKey("estimatedPrice[totalPrice]")) {
            estimatedPrice.put("totalPrice", context.get("estimatedPrice[totalPrice]"));
            estimatedPrice.put("quantity", context.get("estimatedPrice[quantity]"));
            estimatedPrice.put("unitPrice", context.get("estimatedPrice[unitPrice]"));
            jobData.put("estimatedPrice", estimatedPrice);
        } else if (context.containsKey("price[totalPrice]")) {
            actualPrice.put("totalPrice", context.get("price[totalPrice]"));
            actualPrice.put("quantity", context.get("price[quantity]"));
            actualPrice.put("unitPrice", context.get("price[unitPrice]"));
            jobData.put("price", actualPrice);
        }


        if(context.containsKey("fileUpload[0][name]")) {
            fileUpload.put("name", context.get("fileUpload[0][name]"));
            fileUpload.put("path", context.get("fileUpload[0][path]"));
            List<Map<String, Object>> fileUploads = new ArrayList<>();
            fileUploads.add(fileUpload);
            jobData.put("fileUpload", fileUploads);
        }

        boolean hasJobModified = true;
        if(!saveAndExitMode) {
            hasJobModified = hasJobModified(jobData, oldJobData);
            if(("0".equals(jobData.get("jobId")) && "0".equals(jobData.get("documentId"))) || !fileUpload.isEmpty()) {
                Click2MailDocument document = uploadDocument((String) jobData.get("documentClass"), (String)fileUpload.get("path"));
                if(document.getId().equals("0")) {
                    result.put("error", "Document upload failed");
                    result.put("errorCode", 701);
                    result.put("document", document);
                    result.put("success", false);
                    jobDataGV.put("progressStatus", "ERROR_DOCUMENT_UPLOAD");
                    jobDataGV.put("resultData", new Gson().toJson(result));
                    delegator.createOrStore(jobDataGV);
                    TransactionUtil.commit();
                    return result;
                } else {
                    //Validate the max pages, if required on the server side
                    //Update jobDataGV progress_status column with UPLOAD_COMPLETE
                    jobDataGV.put("progressStatus", "DOCUMENT_UPLOAD_COMPLETE");
                    delegator.createOrStore(jobDataGV);
                    TransactionUtil.commit();
                    jobData.put("documentId", document.getId());
                    jobData.put("contentPath", fileUpload.get("path"));
                    jobData.put("contentName", fileUpload.get("name"));
                    jobData.put("fileUpload", new ArrayList<>());
                    hasJobModified = true;

                    Map<String, Object> ctx = new HashMap<>();

                    ctx.put("contentPath", fileUpload.get("path"));
                    ctx.put("contentName", fileUpload.get("name"));
                    ctx.put("productId", jobData.get("productId"));
                    ctx.put("documentClass", jobData.get("documentClass"));
                    ctx.put("documentId", document.getId());

                    saveDirectMailingDocument(delegator, partyId, ctx);
                }
            }
           /* boolean addressListModified = false;
            if (!"0".equals(jobData.get("addressId"))) {
                Double addressListCreatedTimestamp = Double.parseDouble(String.valueOf(oldJobData.get("addressListCreatedTimestamp")));
                addressListModified = hasAddressListModified(delegator, (String) jobData.get("dataGroupId"), (String) oldJobData.get("dataGroupId"), addressListCreatedTimestamp.longValue());
            }*/
            if("0".equals(jobData.get("addressId")) || hasAddressModified) {
                TimeUnit.SECONDS.sleep(2);
                if(!"0".equals(jobData.get("addressId"))) {
                    if (deleteAddressList((String) jobData.get("addressId"))){
                        jobData.put("addressId", "0");
                    }
                }
                Click2MailCreatedAddressList addressList = createOrUpdateAddressList(delegator, (String) jobData.get("dataGroupId"));
                if(UtilValidate.isEmpty(addressList) || addressList.getId().equals("0")) {
                    result.put("error", "Address List creation failed");
                    result.put("errorCode", 702);
                    result.put("addressList", addressList);
                    result.put("success", false);
                    jobDataGV.put("progressStatus", "ERROR_ADDRESS_LIST");
                    jobDataGV.put("resultData", new Gson().toJson(result));
                    delegator.createOrStore(jobDataGV);
                    TransactionUtil.commit();
                    return result;
                } else {
                    //Update jobDataGV progress_status column with ADDRESS_LIST_COMPLETE
                    jobDataGV.put("progressStatus", "ADDRESS_LIST_COMPLETE");
                    delegator.createOrStore(jobDataGV);
                    TransactionUtil.commit();
                    jobData.put("addressId", addressList.getId());
                    //jobData.put("addressListCreatedTimestamp", new Date().getTime());
                    hasJobModified = true;
                }
            }
            boolean calculateActualCostFlag = false;
            JobModel job = null;
            if("0".equals(jobId)) {
                job = createJob(jobData);
                if(job.getId().equals("0")) {
                    result.put("error", "Job creation failed");
                    result.put("errorCode", 710);
                    result.put("job", job);
                    result.put("success", false);
                    jobDataGV.put("progressStatus", "ERROR_JOB_CREATION");
                    jobDataGV.put("resultData", new Gson().toJson(result));
                    delegator.createOrStore(jobDataGV);
                    return result;
                } else {
                    //Update jobDataGV progress_status column with JOB_CREATED
                    TimeUnit.SECONDS.sleep(2);
                    jobDataGV.put("progressStatus", "JOB_CREATED");
                    delegator.createOrStore(jobDataGV);
                    jobData.put("jobId", job.getId());
                    calculateActualCostFlag = true;
                }

            } else if(hasJobModified) {
                job = updateJob(jobData);
                TimeUnit.SECONDS.sleep(2);
                jobDataGV.put("progressStatus", "JOB_CREATED");
                delegator.createOrStore(jobDataGV);
                TransactionUtil.commit();
                calculateActualCostFlag = true;
            }
            if(calculateActualCostFlag) {
                Map<String, String> price = new Gson().fromJson(getJobCost(job.getId(), (String) jobData.get("quantity")), Map.class);
                if(!price.isEmpty()) {
                    //Update jobDataGV progress_status column with PRICE_CALCULATED
                    jobDataGV.put("progressStatus", "PRICE_CALCULATED");
                    delegator.createOrStore(jobDataGV);
                    jobData.remove("estimatedPrice");
                    jobData.put("price", price);
                    TimeUnit.SECONDS.sleep(2);
                }
                JobModel proof = createProof((String) jobData.get("jobId"));
                if (UtilValidate.isNotEmpty(proof) && proof.getStatus().equals("0")) {
                    result.put("proof", proof.getStatusUrl());
                    jobData.put("proof", proof.getStatusUrl());
                }
            }
        } else if (!hasDocumentSaved(delegator, partyId, (String) jobData.get("documentId"))) {
            saveDirectMailingDocument(delegator, partyId, jobData);
        }

        jobDataGV.put("data", new Gson().toJson(jobData));
        if(saveAndExitMode) {
            jobDataGV.put("partyId", context.get("partyId"));
            jobDataGV.put("fromDate", UtilDateTime.nowTimestamp());
        }
        jobDataGV.put("productId", jobData.get("productId"));

        delegator.createOrStore(jobDataGV);

        result.put("jobId", jobData.get("jobId"));
        result.put("documentId", jobData.get("documentId"));
        result.put("contentPath", jobData.get("contentPath"));
        result.put("contentName", jobData.get("contentName"));
        result.put("addressId", jobData.get("addressId"));
        result.put("jobNumber", jobDataGV.get("jobNum"));
        result.put("partyId", context.get("partyId"));
        //result.put("addressListCreatedTimestamp", jobData.get("addressListCreatedTimestamp"));
        if(jobData.containsKey("estimatedPrice")) {
            result.put("estimatedPrice", jobData.get("estimatedPrice"));
        } else {
            result.put("price", jobData.get("price"));
        }
        success = true;

        if (success) {
            jobDataGV.put("progressStatus", "COMPLETED_SUCCESSFULLY");
            jobDataGV.put("resultData", new Gson().toJson(result));
            delegator.createOrStore(jobDataGV);
            TransactionUtil.commit();
        }
        result.put("success", success);
        return result;
    }

    static Map<String, Object> getSaveJobStatus(Delegator delegator, String jobNumber) throws GenericEntityException, IOException {
        boolean success = false;
        Map<String, Object> result = new HashMap<>();
        GenericValue jobDataGV = EntityQuery.use(delegator).from("DirectMailingJobData").where("jobNum", jobNumber).queryOne();
        if (UtilValidate.isNotEmpty(jobDataGV)) {
            success = true;
            result.put("progressStatus", UtilValidate.isNotEmpty(jobDataGV.get("progressStatus")) ? jobDataGV.get("progressStatus") : "");
            result.put("resultData", UtilValidate.isNotEmpty(jobDataGV.getString("resultData")) ? new Gson().fromJson(jobDataGV.getString("resultData"), Map.class) : "");
        }
        result.put("success", success);
        return result;
    }

    private static Map<String, Object> getJobData(Delegator delegator, String jobNumber) throws Exception {
        Map<String, Object> dataMap = new HashMap<>();
        if(UtilValidate.isEmpty(jobNumber)) {
            return new HashMap<>();
        }
       GenericValue jobDataGV = EntityQuery.use(delegator).from("DirectMailingJobData").where("jobNum", jobNumber).queryOne();
        if (UtilValidate.isNotEmpty(jobDataGV)) {
            dataMap = new Gson().fromJson(jobDataGV.getString("data"), Map.class);
        }
        return dataMap;
    }



    public static GenericValue getSavedJob(Delegator delegator, String jobNumber) throws GenericEntityException {

        if(UtilValidate.isEmpty(jobNumber)) {
            return null;
        }
        return EntityQuery.use(delegator).from("DirectMailingSavedJob").where("jobNum", jobNumber).queryOne();

    }

    public static GenericValue getSavedJobData(Delegator delegator, String jobNumber, String partyId, String cartId) throws GenericEntityException {
        if(UtilValidate.isNotEmpty(partyId)) {
            return EntityQuery.use(delegator).from("DirectMailingJobData").where(UtilMisc.toMap("jobNum", jobNumber, "partyId", partyId)).queryOne();
        } else {
            return EntityQuery.use(delegator).from("DirectMailingJobData").where(UtilMisc.toMap("jobNum", jobNumber, "cartId", cartId)).queryOne();
        }
    }

    static boolean hasAddressListModified(Delegator delegator, String dataGroupId, String oldDataGroupId, long click2MailDateTime) throws Exception {

        if(!dataGroupId.equals(oldDataGroupId)) {
            return true;
        }
        boolean isAddressUpdated = false;
        Date addressModifiedTime = getLastAddressModifiedTime(delegator, dataGroupId);
        if (UtilValidate.isNotEmpty(addressModifiedTime)) {
            if (addressModifiedTime.getTime() > (click2MailDateTime)) {
                isAddressUpdated = true;
            }
        }
        return isAddressUpdated;
    }

    private static Date getLastAddressModifiedTime(Delegator delegator, String dataGroupId) throws Exception {
        Date lastUpdatedDate = null;
        SQLProcessor sqlProcessor = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));
        String sqlCommand1 = "SELECT max(LAST_UPDATED_STAMP) AS LAST_UPDATED_STAMP FROM variable_data where VARIABLE_DATA_GROUP_ID = '" + dataGroupId +"'";
        try {
            ResultSet rs = sqlProcessor.executeQuery(sqlCommand1);
            if (rs != null) {
                while (rs.next()) {
                    //lastUpdatedDate = rs.getDate("LAST_UPDATED_STAMP");
                    lastUpdatedDate = new Date(rs.getTimestamp("LAST_UPDATED_STAMP").getTime());
                }
            }
        }finally {
            if(sqlProcessor != null) {
                sqlProcessor.close();
            }
        }
        return lastUpdatedDate;
    }

    static boolean hasJobModified(Map<String, Object> newData, Map<String, Object> oldData) {

        if (!newData.get("layout").equals(oldData.get("layout"))) {
            return true;
        }
        if (!newData.get("envelope").equals(oldData.get("envelope"))) {
            return true;
        }
        if (!newData.get("color").equals(oldData.get("color"))) {
            return true;
        }
        if (!newData.get("paperType").equals(oldData.get("paperType"))) {
            return true;
        }
        if (!newData.get("printOption").equals(oldData.get("printOption"))) {
            return true;
        }
        if (!newData.get("mailClass").equals(oldData.get("mailClass"))) {
            return true;
        }
        if (!newData.get("documentId").equals(oldData.get("documentId"))) {
            return true;
        }
        if (!newData.get("addressId").equals(oldData.get("addressId"))) {
            return true;
        }
        if (!newData.get("rtnName").equals(oldData.get("rtnName"))) {
            return true;
        }
        if (!newData.get("rtnOrganization").equals(oldData.get("rtnOrganization"))) {
            return true;
        }
        if (!newData.get("rtnAddress1").equals(oldData.get("rtnAddress1"))) {
            return true;
        }
        if (!newData.get("rtnAddress2").equals(oldData.get("rtnAddress2"))) {
            return true;
        }
        if (!newData.get("rtnCity").equals(oldData.get("rtnCity"))) {
            return true;
        }
        if (!newData.get("rtnState").equals(oldData.get("rtnState"))) {
            return true;
        }
        if (!newData.get("rtnZip").equals(oldData.get("rtnZip"))) {
            return true;
        }
        return false;
    }

    private static String getJobCost(String jobId, String quantity) {
        String price = "";
        try {
            Map<String, Object> formParams = new HashMap<>();
            //formParams.put("details", true);
            formParams.put("paymentType", URLEncoder.encode("Credit Card", "UTF-8"));
            JobCostModel cost = DirectMailingUtil.getJobCost(new JobCostRequest(UtilMisc.toMap("jobId", jobId), formParams));
            if (UtilValidate.isNotEmpty(cost) && cost.getStatus().equals("0")) {
                /** effective price is calculated by adding margin price **/
                price = DirectMailingUtil.getEffectivePrice(new BigDecimal(cost.getCost()), new BigDecimal(quantity), quantity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return price;
    }

    private static JobModel createJob(Map<String, Object> context) {
        Map<String, Object> map = new HashMap<>();
        map.put("documentClass", context.get("documentClass"));
        map.put("layout", context.get("layout"));
        map.put("productionTime", context.get("productionTime"));
        map.put("envelope", context.get("envelope"));
        map.put("color", context.get("color"));
        map.put("paperType", context.get("paperType"));
        map.put("printOption", context.get("printOption"));
        map.put("mailClass", context.get("mailClass"));
        map.put("documentId", context.get("documentId"));
        map.put("addressId", context.get("addressId"));
        map.put("rtnName", context.get("rtnName"));
        map.put("rtnOrganization", context.get("rtnOrganization"));
        map.put("rtnAddress1", context.get("rtnAddress1"));
        map.put("rtnAddress2", context.get("rtnAddress2"));
        map.put("rtnCity", context.get("rtnCity"));
        map.put("rtnState", context.get("rtnState"));
        map.put("rtnZip", context.get("rtnZip"));
        return DirectMailingUtil.createJob(new CreateJobRequest(map));
    }

    private static JobModel updateJob(Map<String, Object> context) {
        String jobId = (String) context.get("jobId");
        Map<String, Object> map = new HashMap<>();
        map.put("documentClass", context.get("documentClass"));
        map.put("layout", context.get("layout"));
        map.put("productionTime", context.get("productionTime"));
        map.put("envelope", context.get("envelope"));
        map.put("color", context.get("color"));
        map.put("paperType", context.get("paperType"));
        map.put("printOption", context.get("printOption"));
        map.put("mailClass", context.get("mailClass"));
        map.put("documentId", context.get("documentId"));
        map.put("addressId", context.get("addressId"));
        map.put("rtnName", context.get("rtnName"));
        map.put("rtnOrganization", context.get("rtnOrganization"));
        map.put("rtnAddress1", context.get("rtnAddress1"));
        map.put("rtnAddress2", context.get("rtnAddress2"));
        map.put("rtnCity", context.get("rtnCity"));
        map.put("rtnState", context.get("rtnState"));
        map.put("rtnZip", context.get("rtnZip"));
        return DirectMailingUtil.updateJob(new UpdateJobRequest(map, jobId));
    }

    private static Click2MailDocument  uploadDocument(String documentClass, String pathName) throws Exception {

        pathName = EnvConstantsUtil.OFBIZ_HOME + pathName;
        File file = new File(pathName);
        Map<String, Object> context = new HashMap<>();
        context.put("documentClass", documentClass);
        context.put("documentFormat", FilenameUtils.getExtension(file.getName()).toUpperCase());
        return DirectMailingUtil.uploadDocument(new UploadDocumentRequest(context, file));

    }

    private static JobModel createProof(String jobId) {
        return DirectMailingUtil.createJobProof(new CreateJobProofRequest(jobId));
    }

    private static void saveDirectMailingDocument(Delegator delegator, String partyId, Map<String, Object> context) throws Exception {

        if(UtilValidate.isNotEmpty(partyId)) {
            GenericValue directMailingContent = delegator.makeValue("DirectMailingContent", "directMailingContentId", delegator.getNextSeqId("DirectMailingContent"));
            directMailingContent.put("contentPurposeEnumId", "DMC_DOCUMENT");
            directMailingContent.put("contentPath", context.get("contentPath"));
            directMailingContent.put("contentName", context.get("contentName"));
            directMailingContent.put("productId", context.get("productId"));
            directMailingContent.put("documentClass", context.get("documentClass"));
            directMailingContent.put("documentId", context.get("documentId"));
            directMailingContent.put("partyId", partyId);
            directMailingContent.put("fromDate", UtilDateTime.nowTimestamp());

            delegator.createOrStore(directMailingContent);
        }
    }

    private static boolean hasDocumentSaved(Delegator delegator, String partyId, String documentId) throws Exception {
        boolean saved = true;
        GenericValue directMailingContent = EntityQuery.use(delegator).from("DirectMailingContent").where("documentId", documentId).queryFirst();
        if(!"0".equals(documentId) && UtilValidate.isEmpty(directMailingContent)) {
            saved = false;
        }
        return saved;
    }

    private static Click2MailCreatedAddressList createOrUpdateAddressList(Delegator delegator, String dataGroupId) throws Exception {
        List<Map<String, String>> addresses = getAddresses(delegator, dataGroupId);
        Click2MailCreatedAddressList address = null;
        if (UtilValidate.isNotEmpty(addresses)) {
            List addressList = new ArrayList();
            addresses.forEach(data -> {
                addressList.add(new AddressModel((data.get("firstName") != null ? data.get("firstName") : ""), (data.get("lastName") != null ? data.get("lastName") : ""),
                        (data.get("organization") != null ? data.get("organization") : ""), (data.get("address1") != null ? data.get("address1") : ""),
                        (data.get("address2") != null ? data.get("address2") : ""), (data.get("address3") != null ? data.get("address3") : ""),
                        (data.get("city") != null ? data.get("city") : ""), (data.get("state") != null ? data.get("state") : ""),
                        (data.get("zip") != null ? data.get("zip") : ""), (data.get("country") != null ? data.get("country") : "")));

            });
            address =  DirectMailingUtil.createAddressList(new CreateAddressListRequest(new AddressList(addressList, "2", "sample")));
        }
        return address;
    }

    private static boolean deleteAddressList(String addressListId)  {
        boolean success = false;
        try {
            AddressResponse response = DirectMailingUtil.deleteAddressList(new DeleteAddressListRequest(UtilMisc.toMap("addressListId", addressListId)));
            if(UtilValidate.isNotEmpty(response) && response.getStatus().equals("0")) {
                success = true;
            }
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
        return success;
    }

    private static List<Map<String, String>> getAddresses(Delegator delegator, String dataGroupId) throws Exception {
        List<String> headers = null;
        List<Map<String, String>> resultAddress = null;
        List<EntityCondition> conditionList = new ArrayList<>();
        if(UtilValidate.isNotEmpty(dataGroupId)) {
            conditionList.add(EntityCondition.makeCondition("variableDataGroupId", EntityOperator.EQUALS, dataGroupId));
        }
        GenericValue savedAddressGroupInfo = EntityQuery.use(delegator).select("variableDataGroupId", "attributes").from("VariableDataGroup").where(conditionList).queryOne();
        if(UtilValidate.isNotEmpty(savedAddressGroupInfo)) {
            String attributes = (String) savedAddressGroupInfo.get("attributes");
            headers = convertStringToList(attributes);
            List<GenericValue> addressDatas = EntityQuery.use(delegator).select("data").from("VariableData").where(conditionList).queryList();
            if(UtilValidate.isNotEmpty(addressDatas)) {
                resultAddress = new ArrayList<>();
                for(GenericValue addressValues : addressDatas) {
                    String address = (String) addressValues.get("data");
                    List<String> addressAsList = convertStringToList(address);
                    if(UtilValidate.isNotEmpty(addressAsList) && (addressAsList.size() == headers.size())) {
                        Iterator iterHeader = headers.iterator();
                        Iterator iterrAddress = addressAsList.iterator();
                        Map<String, String> addressValue = new HashMap<>();
                        while (iterHeader.hasNext()) {
                            String header = (String)iterHeader.next();
                            header = header.replace("\"", "");
                            switch (header) {
                                case "Name Line 1": {
                                    addressValue.put("firstName", ((String)iterrAddress.next()).trim());
                                    break;
                                }
                                case "organization": {
                                    addressValue.put("organization", ((String)iterrAddress.next()).trim());
                                    break;
                                }
                                case "Name Line 2": {
                                    addressValue.put("lastName", ((String)iterrAddress.next()).trim());
                                    break;
                                }
                                case "Address Line 1": {
                                    addressValue.put("address1", ((String)iterrAddress.next()).trim());
                                    break;
                                }
                                case "Address Line 2": {
                                    addressValue.put("address2", ((String)iterrAddress.next()).trim());
                                    break;
                                }
                                case "Address Line 3": {
                                    addressValue.put("address3", ((String)iterrAddress.next()).trim());
                                    break;
                                }
                                case "City": {
                                    addressValue.put("city", ((String)iterrAddress.next()).trim());
                                    break;
                                }
                                case "State": {
                                    addressValue.put("state", ((String)iterrAddress.next()).trim());
                                    break;
                                }
                                case "Zip": {
                                    addressValue.put("zip", ((String)iterrAddress.next()).trim());
                                    break;
                                }
                                case "Country": {
                                    addressValue.put("country", ((String)iterrAddress.next()).trim());
                                    break;
                                }
                            }
                        }
                        resultAddress.add(addressValue);
                    }
                }
            }
        }
        return resultAddress;
    }

    private static Map<String, Object> getPaymentOptions(Delegator delegator) {
        Map<String, String> directMailingConfig = getDirectMailingConfig(delegator);
        Map<String, Object> paymentOptions = new HashMap<>();
        paymentOptions.put("billingType", directMailingConfig.get(ConfigKey.BILLING_TYPE.key()));
        paymentOptions.put("billingName", directMailingConfig.get(ConfigKey.BILLING_NAME.key()));
        paymentOptions.put("billingAddress1", directMailingConfig.get(ConfigKey.BILLING_ADDRESS1.key()));
        paymentOptions.put("billingAddress2", directMailingConfig.get(ConfigKey.BILLING_ADDRESS2.key()));
        paymentOptions.put("billingCity", directMailingConfig.get(ConfigKey.BILLING_CITY.key()));
        paymentOptions.put("billingState", directMailingConfig.get(ConfigKey.BILLING_STATE.key()));
        paymentOptions.put("billingZip", directMailingConfig.get(ConfigKey.BILLING_ZIP.key()));
        if("Credit Card".equals(paymentOptions.get("billingType"))) {
            paymentOptions.put("billingCcType", directMailingConfig.get(ConfigKey.BILLING_CC_TYPE.key()));
            paymentOptions.put("billingNumber", directMailingConfig.get(ConfigKey.BILLING_CC_NUMBER.key()));
            paymentOptions.put("billingMonth", directMailingConfig.get(ConfigKey.BILLING_CC_MONTH.key()));
            paymentOptions.put("billingYear", directMailingConfig.get(ConfigKey.BILLING_CC_YEAR.key()));
            paymentOptions.put("billingCvv", directMailingConfig.get(ConfigKey.BILLING_CC_CVV.key()));
        }
        return paymentOptions;
    }

    public static boolean isDirectMailingOrder(Delegator delegator, String orderId) throws GenericEntityException {
        return UtilValidate.isNotEmpty(getDirectMailingArtworks(delegator, orderId, null));
    }
    public static boolean isDirectMailingOrderItem(Delegator delegator, String orderId, String orderItemSeqId) throws GenericEntityException {
        return UtilValidate.isNotEmpty(getDirectMailingArtworks(delegator, orderId, orderItemSeqId));
    }

    private static List<GenericValue> getDirectMailingArtworks(Delegator delegator, String orderId, String orderItemSeqId) throws GenericEntityException {
        List<GenericValue> artworks = new ArrayList<>();
        OrderReadHelper orh = new OrderReadHelper(delegator, orderId);
        for(GenericValue orderItem : orh.getOrderItems()) {
            if(orderItemSeqId != null && !"orderItemSeqId".equalsIgnoreCase(orderItem.getString("orderItemSeqId"))) {
                continue;
            }
            if("ITEM_CANCELLED".equalsIgnoreCase(orderItem.getString("statusId"))) {
                continue;
            }
            GenericValue artwork = OrderHelper.getOrderItemArtwork(delegator, orderId, orderItem.getString("orderItemSeqId"));
            if(UtilValidate.isNotEmpty(artwork) && UtilValidate.isNotEmpty(artwork.get("directMailJobId"))) {
                artworks.add(artwork);
            }
        }

        return artworks;
    }

    static Map<String, Object> submitJob(Delegator delegator, String orderId, String orderItemSeqId) throws Exception {
        Map<String, Object> result = new HashMap<>();
        for(GenericValue artwork : getDirectMailingArtworks(delegator, orderId, orderItemSeqId)) {
            String jobNumber = artwork.getString("directMailJobId");
            try {
                Map<String, Object> jobData = getJobData(delegator, jobNumber);
                if (UtilValidate.isNotEmpty(jobData) && UtilValidate.isNotEmpty(jobData.get("jobId"))) {
                    String jobId = (String) jobData.get("jobId");

                    GenericValue jobOrder = EntityQuery.use(delegator).from("DirectMailingJobOrder").where("jobNum", jobNumber).queryOne();
                    if (UtilValidate.isEmpty(jobOrder)) {
                        jobOrder = delegator.makeValue("DirectMailingJobOrder", "jobNum", jobNumber);
                        jobOrder.put("jobId", jobId);
                        jobOrder.put("orderId", orderId);
                        jobOrder.put("orderItemSeqId", artwork.getString("orderItemSeqId"));
                        jobOrder.put("status", "PENDING_SUBMIT");
                        jobOrder.put("createdDate", UtilDateTime.nowTimestamp());
                        jobOrder.create();

                        TransactionUtil.commit();

                        JobCostModel jobCostModel = DirectMailingUtil.submitJob(new JobSubmitRequest(jobId, getPaymentOptions(delegator)));
                        result.put("jobCostModel", jobCostModel);


                        jobOrder = EntityQuery.use(delegator).from("DirectMailingJobOrder").where("jobNum", jobNumber).queryOne();
                        if(UtilValidate.isNotEmpty(jobCostModel) && "0".equals(jobCostModel.getStatus())) {
                            jobOrder.put("status", "SUBMITTED");
                            jobOrder.put("submittedDate", UtilDateTime.nowTimestamp());
                        } else {
                            jobOrder.put("status", "SUBMIT_FAILED");
                        }
                        jobOrder.store();
                    }
                }
            } catch (Exception e) {
                EnvUtil.reportError(e);
                Debug.logError(e, "Error occurred while submitting the job to Click2Mail [Order ID : " + orderId + ", Item Seq Id : " + orderItemSeqId + "]", module);
            }
        }
        return result;
    }

    private static List<String> convertStringToList(String data) {
        List<String> dataArray = null;
        if(UtilValidate.isNotEmpty(data)) {
            String[] subData = data.replace("[", "").replace("]", "").split(",");
            dataArray = Arrays.asList(subData);
            List<String> dataArrayTrim = new ArrayList<>();
            if (UtilValidate.isNotEmpty(dataArray)) {
                for(String valueToTrim : dataArray) {
                    if(UtilValidate.isNotEmpty(valueToTrim)){
                        dataArrayTrim.add(valueToTrim.replace("\"", "").trim());
                    }
                }
                dataArray = dataArrayTrim;
            }
        }
        return dataArray;
    }

    public static String trimAddressArray(String data) {
        String dataResult = data;
        try {
            HashMap<String, Object> result = new Gson().fromJson(data, HashMap.class);
            if(UtilValidate.isNotEmpty(result)) {
                result.remove("addressingData");
                for (Map.Entry<String, Object> entry :result.entrySet()) {
                    if(entry.getValue() instanceof String) {
                        result.put(entry.getKey(), ((String)entry.getValue()).trim());
                    }else {
                        result.put(entry.getKey(), entry.getValue());
                    }
                }
                dataResult = new JSONObject(result).toString().trim();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return dataResult;
    }
}
