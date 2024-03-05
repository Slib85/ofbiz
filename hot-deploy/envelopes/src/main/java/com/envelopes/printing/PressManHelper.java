package com.envelopes.printing;

import com.envelopes.http.HTTPHelper;
import com.envelopes.order.OrderHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import com.google.gson.Gson;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.jdbc.SQLProcessor;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Manu on 9/8/2016.
 */
public class PressManHelper {

    public static final String ARTWORK_PREVIEW_FILE_LOCATION = EnvConstantsUtil.OFBIZ_HOME + EnvConstantsUtil.UPLOAD_DIR + "/" + EnvConstantsUtil.ARTWORK_PREVIEW_FILE_DIR + "/";
    public static final String GOOD_TO_GO_BACKLOG_LOCATION = EnvConstantsUtil.OFBIZ_HOME + EnvConstantsUtil.UPLOAD_DIR + "/" + EnvConstantsUtil.GOOD_TO_GO_BACKLOG_DIR + "/";
    protected static final String module = PressManHelper.class.getName();
    public static final int OFFSET_QTY_THRESHOLD = 500;
    public static final String PRINT_JOB_ATTRIBUTES_ENDPOINT = "https://pps.envelopes.com/admin/control/getPrintJobData?key=b1a6fcad-20e3-4dc4-9347-d266fa012bee&";


    public static Map<String, Object> jobGoodToGo(Delegator delegator, JobGoodToGoRequest jobGoodToGoRequest) throws Exception {
        return addGoodToGoJobToMasterJobQueue(delegator, jobGoodToGoRequest);

    }

    private static Map getPrintJobAttributes(Delegator delegator, String jobId, boolean soaIntegration) throws Exception {
        if(soaIntegration) {
            String response = HTTPHelper.getURL(PRINT_JOB_ATTRIBUTES_ENDPOINT + "jobId=" + jobId, "GET", null, null, null, null, true, EnvConstantsUtil.RESPONSE_JSON, EnvConstantsUtil.RESPONSE_PLAIN);
            return new Gson().fromJson(response, Map.class);
        } else {
            return OrderHelper.getPrintJobAttributes(delegator, jobId);
        }
    }

    private static Map addGoodToGoJobToMasterJobQueue(Delegator delegator, JobGoodToGoRequest jobGoodToGoRequest) throws Exception {
//        Map attributes = OrderHelper.getPrintJobAttributes(delegator, jobGoodToGoRequest.getJobId());

        Map attributes = getPrintJobAttributes(delegator, jobGoodToGoRequest.getJobId(), jobGoodToGoRequest.isStandaloneMode());
        if(jobGoodToGoRequest.isStandaloneMode()) {
            attributes = (Map) attributes.get("jobData");
        }
        GenericValue printJob = delegator.makeValue("PressManPrintJob", UtilMisc.toMap("orderId", jobGoodToGoRequest.getOrderId(), "orderItemSeqId", jobGoodToGoRequest.getOrderItemSeqId(), "side", jobGoodToGoRequest.getSide().getSideIndex()));
        long version = getVersion(delegator, jobGoodToGoRequest);
        printJob.put("version", version);
        printJob.put("isRushItem", attributes.get("RUSH_ITEM"));
        printJob.put("isRushJob", attributes.get("RUSH_ITEM"));
        printJob.put("itemDueDate", UtilDateTime.toTimestamp((String)attributes.get("ITEM_DUE_DATE")));
        printJob.put("jobDueDate", UtilDateTime.toTimestamp((String)attributes.get("ITEM_DUE_DATE")));
        printJob.put("heavyInkCoverage", attributes.get(false));
        long preferredPressId = getPreferredPressId(delegator, (String)attributes.get("PARENT_PRODUCT_ID"), getLongValue(attributes.get("ITEM_QTY")), false);
        printJob.put("preferredPressId", Long.toString(preferredPressId));
        printJob.put("assignedPressId", Long.toString(preferredPressId));
        printJob.put("internalJobId", attributes.get("ARTWORK_ID"));
        printJob.put("itemId", attributes.get("PRODUCT_ID"));
        printJob.put("parentProductId", attributes.get("PARENT_PRODUCT_ID"));
        printJob.put("itemQty", getLongValue(attributes.get("ITEM_QTY")));
        printJob.put("itemWidth", new BigDecimal((Double)attributes.get("ITEM_WIDTH")));
        printJob.put("itemHeight", new BigDecimal((Double)attributes.get("ITEM_HEIGHT")));
        if(UtilValidate.isNotEmpty(attributes.get("ART_WIDTH"))) {
            printJob.put("artWidth", new BigDecimal((Double) attributes.get("ART_WIDTH")));
        }
        printJob.put("artHeight", new BigDecimal(Double.toString(jobGoodToGoRequest.getArtHeight())));
        printJob.put("isScene7", "SCENE7_ART_ONLINE".equals(attributes.get("ARTWORK_SOURCE")));
        printJob.put("isVariableData", UtilValidate.isNotEmpty(attributes.get("NUM_OF_ADDRESSES")) && getIntegerValue(attributes.get("NUM_OF_ADDRESSES")) > 0);
        printJob.put("isTwoSided", isTwoSidedJob(attributes));
        printJob.put("isReprint", version > 0);
        printJob.put("reprintReason", "");
        List<GenericValue> colors = getInkColors(delegator, jobGoodToGoRequest.getColors());
        int colorIdx = 0;
        for(GenericValue color : colors) {
            colorIdx ++;
            printJob.put("inkColorId" + colorIdx, color.get("colorId"));
            printJob.put("inkColorCode" + colorIdx, UtilValidate.isEmpty(color.get("colorCodeOverride")) ? color.get("colorCode") : color.get("colorCodeOverride"));
        }
        printJob.put("comments", "");
        printJob.put("completedFlag", false);
        printJob = delegator.createOrStore(printJob);
        return printJob;
    }

    private static Long getLongValue(Object valueObject) {
        if(valueObject != null && valueObject instanceof Double) {
            return ((Double) valueObject).longValue();
        }
        return (Long) valueObject;
    }

    private static Integer getIntegerValue(Object valueObject) {
        if(valueObject != null && valueObject instanceof Double) {
            return ((Double) valueObject).intValue();
        }
        return (Integer) valueObject;
    }

    private static long getVersion(Delegator delegator, JobGoodToGoRequest jobGoodToGoRequest) throws Exception {
        SQLProcessor sqlProcessor = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));
        long version = 0;
        String sqlCommand1 =
                "SELECT  " +
                "    CASE " +
                "        WHEN MAX(version) IS NULL THEN 0 " +
                "        ELSE MAX(version) " +
                "    END AS version " +
                "FROM " +
                "    press_man_print_job " +
                "WHERE " +
                "    order_id = '" + jobGoodToGoRequest.getOrderId() + "' && order_item_seq_id = '" + jobGoodToGoRequest.getOrderItemSeqId() + "' " +
                "        AND side = '" + jobGoodToGoRequest.getSide().getSideIndex() + "'";

        try {
            ResultSet rs = sqlProcessor.executeQuery(sqlCommand1);
            if (rs != null) {
                if (rs.next()) {
                    version = rs.getLong("version");
                }
            }
        } finally {
            if(sqlProcessor != null) {
                sqlProcessor.close();
            }
        }
        return version;
    }

    private static long getPreferredPressId(Delegator delegator, String parentProductId, long qty, boolean heavyInkCoverage) throws Exception {
        SQLProcessor sqlProcessor = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));
        long preferredPressId = -1;
        String sqlCommand1 =
                "SELECT  " +
                "    offset_press_id, digital_press_id " +
                "FROM " +
                "    press_man_product_attributes " +
                "WHERE " +
                "    parent_product_id = '" + parentProductId + "'";

        try {
            ResultSet rs = sqlProcessor.executeQuery(sqlCommand1);
            if (rs != null) {
                if (rs.next()) {
                    String offsetPressId = rs.getString("offset_press_id");
                    String digitalPressId = rs.getString("digital_press_id");
                    if(qty >= OFFSET_QTY_THRESHOLD || NumberUtils.toInt(digitalPressId) == 0) {
                        preferredPressId = NumberUtils.toInt(offsetPressId);
                    } else {
                        preferredPressId = NumberUtils.toInt(digitalPressId);
                    }
                }
            }
        } finally {
            if(sqlProcessor != null) {
                sqlProcessor.close();
            }
        }
        return preferredPressId;
    }

    private static boolean isTwoSidedJob(Map printJobAttributes) {
        return (UtilValidate.isNotEmpty(printJobAttributes.get("FRONT_COLOR1")) || UtilValidate.isNotEmpty(printJobAttributes.get("FRONT_COLOR2")) || UtilValidate.isNotEmpty(printJobAttributes.get("FRONT_COLOR3")) || UtilValidate.isNotEmpty(printJobAttributes.get("FRONT_COLOR4")))
                    && (UtilValidate.isNotEmpty(printJobAttributes.get("BACK_COLOR1")) || UtilValidate.isNotEmpty(printJobAttributes.get("BACK_COLOR2")) || UtilValidate.isNotEmpty(printJobAttributes.get("BACK_COLOR3")) || UtilValidate.isNotEmpty(printJobAttributes.get("BACK_COLOR4")));
    }

    private static List<GenericValue> getInkColors(Delegator delegator, List<String> colorCodes) throws Exception {

        List<GenericValue> inkColors = new ArrayList<>();
        List<GenericValue> colors = delegator.findList("PressManInkColor", EntityCondition.makeCondition("colorCode", EntityOperator.IN, colorCodes), null, null, null, true);
        colorCodeLabel:
        for (String colorCode : colorCodes) {
            for (GenericValue color : colors) {
                if(color.getString("colorCode").equalsIgnoreCase(colorCode)) {
                    inkColors.add(color);
                    continue colorCodeLabel;
                }
            }
            GenericValue color = delegator.makeValue("PressManInkColor", UtilMisc.toMap("colorId", delegator.getNextSeqId("PressManInkColor")));
            color.put("colorCode", colorCode);
            color.put("colorName", colorCode);
            color.create();
            inkColors.add(color);
        }

        return inkColors;
    }

    public static void addGoodToGoJobToBacklog(JobGoodToGoRequest jobGoodToGoRequest) {

    }

    public static List<Map<String, Object>> getJobsForPress(Delegator delegator, Map<String, Object> context) {
        String pressCode      = getValue(context, "pressCode", "", String.class);
        String itemDueDate    = getValue(context, "itemDueDate", "", String.class);
        List<Map<String, Object>> result = new ArrayList<>();
        //TODO FETCH JOBS FOR THE GIVEN PRESS CODE THAT ARE DUE ON OR BEFORE THE GIVEN DUE DATE

        return result;
    }

    public static List<Map<String, Object>> getPlateForPress(Delegator delegator, Map<String, Object> context) {
        String pressCode      = getValue(context, "pressCode", "", String.class);
        String itemDueDate    = getValue(context, "itemDueDate", "", String.class);
        List<Map<String, Object>> result = new ArrayList<>();
        //TODO CREATE AN OPTIMIZED PLATE FOR THE GIVEN PRESS CODE THAT ARE DUE ON OR BEFORE THE GIVEN DUE DATE

        return result;
    }

    public static Map<String, Object> removeJobFromPlate(Delegator delegator, Map<String, Object> context) {
        String orderId      = getValue(context, "orderId", "", String.class);
        String temSeqNum    = getValue(context, "orderItemSeqNum", "", String.class);
        String side         = getValue(context, "side", "0", String.class);
        Map<String, Object> result = new HashMap<>();
        //TODO

        return result;
    }

    public static Map<String, Object> addJobToPlate(Delegator delegator, Map<String, Object> context) {
        String orderId      = getValue(context, "orderId", "", String.class);
        String temSeqNum    = getValue(context, "orderItemSeqNum", "", String.class);
        String side         = getValue(context, "side", "0", String.class);
        Map<String, Object> result = new HashMap<>();
        //TODO

        return result;
    }

    public static Map<String, Object> swapJobsBetweenPlates(Delegator delegator, Map<String, Object> context) {
        String orderId      = getValue(context, "orderId", "", String.class);
        String temSeqNum    = getValue(context, "orderItemSeqNum", "", String.class);
        String side         = getValue(context, "side", "0", String.class);
        Map<String, Object> result = new HashMap<>();
        //TODO

        return result;
    }

    public static Map<String, Object> rejectPlate(Delegator delegator, Map<String, Object> context) {
        String orderId      = getValue(context, "orderId", "", String.class);
        String temSeqNum    = getValue(context, "orderItemSeqNum", "", String.class);
        String side         = getValue(context, "side", "0", String.class);
        Map<String, Object> result = new HashMap<>();
        //TODO

        return result;
    }

    public static Map<String, Object> lockPlate(Delegator delegator, Map<String, Object> context) {
        String orderId      = getValue(context, "orderId", "", String.class);
        String temSeqNum    = getValue(context, "orderItemSeqNum", "", String.class);
        String side         = getValue(context, "side", "0", String.class);
        Map<String, Object> result = new HashMap<>();
        //TODO

        return result;
    }

    public static Map<String, Object> createPlate(Delegator delegator, Map<String, Object> context) {
        String orderId      = getValue(context, "orderId", "", String.class);
        String temSeqNum    = getValue(context, "orderItemSeqNum", "", String.class);
        String side         = getValue(context, "side", "0", String.class);
        Map<String, Object> result = new HashMap<>();
        //TODO

        return result;
    }

    protected static <T> T getValue(Map<String, Object> context, String name, T defaultValue, Class<T> typeClass) {
        return context.containsKey(name) ? (T)context.get(name) : defaultValue;
    }

    public static List<Map<String, Object>> getPresses(Delegator delegator) throws Exception {
        List<Map<String, Object>> pressList = new ArrayList<>();
        List<GenericValue> presses = delegator.findList("PressManPress", EntityCondition.makeCondition("pressCode", EntityOperator.NOT_EQUAL, "NONE"), null, null, null, false);
        for(GenericValue press : presses) {
            Map<String, Object> pressMap = new HashMap<>();
            pressMap.put("pressId", press.get("pressId"));
            pressMap.put("pressCode", press.get("pressCode"));
            pressMap.put("pressName", press.get("pressName"));
            pressMap.put("isActive", press.get("isActive"));
            pressList.add(pressMap);
        }
        return pressList;
    }

    public static List<Map<String, Object>> getProductPrintAttributes(Delegator delegator) throws Exception {
        List<Map<String, Object>> productPrintAttributesList = new ArrayList<>();
        List<GenericValue> attributesList = delegator.findList("PressManProductAttributes", null, null, null, null, false);
        for(GenericValue attributes: attributesList) {
            Map<String, Object> productPrintAttributes = new HashMap<>();
            productPrintAttributes.put("parentProductId", attributes.get("parentProductId"));
            productPrintAttributes.put("productName", attributes.get("productName"));
            productPrintAttributes.put("height", attributes.get("height"));
            productPrintAttributes.put("width", attributes.get("width"));
            productPrintAttributes.put("offsetPressId", attributes.get("offsetPressId"));
            productPrintAttributes.put("digitalPressId", attributes.get("digitalPressId"));
            productPrintAttributes.put("nonPerfectingFlag", attributes.get("nonPerfectingFlag"));
            productPrintAttributes.put("isActive", attributes.get("isActive"));
            productPrintAttributesList.add(productPrintAttributes);
        }

        return productPrintAttributesList;
    }
}
