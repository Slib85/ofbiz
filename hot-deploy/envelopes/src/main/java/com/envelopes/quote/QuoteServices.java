package com.envelopes.quote;

import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.order.order.OrderReadHelper;
import org.apache.ofbiz.order.order.OrderServices;
import org.apache.ofbiz.security.Security;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;
import org.apache.ofbiz.service.GenericServiceException;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.sql.Timestamp;

/**
 * Created by shoab on 7/24/17.
 */
public class QuoteServices {
    public static final String module = QuoteServices.class.getName();
    public static final String resource = "OrderUiLabels";
    public static final String resource_error = "OrderErrorUiLabels";

    /** Service for changing the status on an order header */
    public static Map<String, Object> setQuoteStatus(DispatchContext ctx, Map<String, ? extends Object> context) {
        LocalDispatcher dispatcher = ctx.getDispatcher();
        Delegator delegator = ctx.getDelegator();

        GenericValue userLogin = (GenericValue) context.get("userLogin");
        String quoteId = (String) context.get("quoteId");
        String statusId = (String) context.get("statusId");
        String changeReason = (String) context.get("changeReason");

        Map<String, Object> successResult = ServiceUtil.returnSuccess();
        Locale locale = (Locale) context.get("locale");

        // check and make sure we have permission to change the quote
        Security security = ctx.getSecurity();
        boolean hasPermission = hasPermission(userLogin, "UPDATE", security);
        if (!hasPermission) {
            return ServiceUtil.returnError(UtilProperties.getMessage(resource_error, "OrderYouDoNotHavePermissionToChangeThisOrdersStatus", locale));
        }

        try {
            GenericValue customEnvelope = EntityQuery.use(delegator).from("CustomEnvelope").where("quoteId", quoteId).queryOne();

            if (customEnvelope == null) {
                return ServiceUtil.returnError(UtilProperties.getMessage(resource_error, "OrderErrorCouldNotChangeOrderStatusOrderCannotBeFound", locale));
            }
            // first save off the old status
            successResult.put("oldStatusId", customEnvelope.get("statusId"));

            if (Debug.verboseOn()) Debug.logVerbose("[QuoteServices.setOrderStatus] : From Status : " + customEnvelope.getString("statusId"), module);
            if (Debug.verboseOn()) Debug.logVerbose("[QuoteServices.setOrderStatus] : To Status : " + statusId, module);

            /*if (customEnvelope.getString("statusId").equals(statusId)) {
                Debug.logWarning(UtilProperties.getMessage(resource_error,
                        "OrderTriedToSetOrderStatusWithTheSameStatusIdforOrderWithId", UtilMisc.toMap("statusId", statusId, "orderId", quoteId), locale), module);
                return successResult;
            }*/

            // update the current status
            customEnvelope.set("statusId", statusId);

            // now create a status change
            GenericValue quoteStatus = delegator.makeValue("QuoteStatus");
            quoteStatus.put("quoteStatusId", delegator.getNextSeqId("QuoteStatus"));
            quoteStatus.put("statusId", statusId);
            quoteStatus.put("quoteId", quoteId);
            quoteStatus.put("statusDatetime", UtilDateTime.nowTimestamp());
            quoteStatus.put("statusUserLogin", userLogin.getString("userLoginId"));
            quoteStatus.put("changeReason", changeReason);

            customEnvelope.store();
            quoteStatus.create();
        } catch (GenericEntityException e) {
            return ServiceUtil.returnError(UtilProperties.getMessage(resource_error, "OrderErrorCouldNotChangeOrderStatus", locale) + e.getMessage() + ").");
        }

        successResult.put("quoteStatusId", statusId);
        return successResult;
    }

    public static Map<String, Object> cleanUpOutdatedQuotes(DispatchContext dctx, Map<String, ?> context) {
        Delegator delegator = dctx.getDelegator();
        LocalDispatcher dispatcher = dctx.getDispatcher();
        Boolean dryRun = (Boolean) context.get("dryRun");
        String webSiteId = (String) context.get("webSiteId");

        try {
            int daysToReject = UtilProperties.getPropertyAsInteger("envelopes", "envelopes.quote.ttl", 90);
            GenericValue userLogin = (GenericValue) context.get("userLogin");

            Timestamp now = UtilDateTime.nowTimestamp();
            Timestamp fromDate = UtilDateTime.addDaysToTimestamp(now, (-1 * daysToReject));
            List<EntityCondition> conditionList = UtilMisc.<EntityCondition>toList(
                    EntityCondition.makeCondition("createdDate", EntityOperator.LESS_THAN, fromDate),
                    EntityCondition.makeCondition("statusId", EntityOperator.NOT_IN, UtilMisc.toList("QUO_ORDERED", "QUO_REJECTED"))
            );
            if (UtilValidate.isNotEmpty(webSiteId)) {
                conditionList.add(EntityCondition.makeCondition("webSiteId", webSiteId));
            }
            List<GenericValue> customEnvelopes = EntityQuery.use(delegator).from("CustomEnvelope")
                    .where(EntityCondition.makeCondition(conditionList))
                    .queryList();
            for (GenericValue customEnvelope : customEnvelopes) {
                String quoteId = customEnvelope.getString("quoteId");
                Debug.logInfo("Quote " + quoteId + " is expired", module);
                if (!dryRun) {
                    dispatcher.runSync("changeQuoteStatus", UtilMisc.toMap(
                            "userLogin", userLogin, "quoteId", quoteId, "statusId", "QUO_REJECTED"
                    ));
                }
            }

        } catch (GenericEntityException | GenericServiceException e) {
            return ServiceUtil.returnError(e.getMessage());
        }

        return ServiceUtil.returnSuccess();
    }

    private static boolean hasPermission(GenericValue userLogin, String action, Security security) {
        boolean hasPermission = security.hasEntityPermission("ORDERMGR", "_" + action, userLogin);
        if (!hasPermission) {
            if (security.hasEntityPermission("ORDERMGR", "_SALES_" + action, userLogin)) {
                hasPermission = true;
            }
        }
        return hasPermission;
    }
}
