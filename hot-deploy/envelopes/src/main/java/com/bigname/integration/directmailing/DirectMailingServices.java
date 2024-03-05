package com.bigname.integration.directmailing;


import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.ServiceUtil;

import java.util.Map;

public class DirectMailingServices {
    public static final String module = DirectMailingServices.class.getName();

    public static Map<String, Object> submitJob(DispatchContext dctx, Map<String, ? extends Object> context) throws Exception {
        Delegator delegator = dctx.getDelegator();
        String orderId = (String) context.get("orderId");
        String orderItemSeqId = (String) context.get("orderItemSeqId");
        DirectMailingHelper.submitJob(delegator, orderId, orderItemSeqId);
        return ServiceUtil.returnSuccess();
    }
}
