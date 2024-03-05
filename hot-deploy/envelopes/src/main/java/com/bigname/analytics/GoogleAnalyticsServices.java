package com.bigname.analytics;

import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.ServiceUtil;

import java.util.Map;

public class GoogleAnalyticsServices {
    public static final String module = GoogleAnalyticsServices.class.getName();

    /**
     * Get product stat from analytics
     * @param dctx
     * @param context
     * @return
     */
    public static Map<String, Object> getProductStatReport(DispatchContext dctx, Map<String, ? extends Object> context) {
        Delegator delegator = dctx.getDelegator();

        Map<String, Object> result = ServiceUtil.returnSuccess();

        try {
            GoogleAnalyticsHelper.getProductStatReport(delegator);
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError("Error processing analytics product stats. " + e + " : " + e.getMessage(), module);
        }

        return result;
    }
}
