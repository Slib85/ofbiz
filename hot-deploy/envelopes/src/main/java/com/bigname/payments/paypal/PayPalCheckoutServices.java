package com.bigname.payments.paypal;

import com.braintreegateway.Transaction;
import com.braintreegateway.Result;
import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.accounting.payment.PaymentGatewayServices;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import java.math.BigDecimal;
import java.util.Map;

public class PayPalCheckoutServices {
    public static final String module = PayPalCheckoutServices.class.getName();

    /**
     * Authorize the paypal amount
     * @param dctx
     * @param context
     * @return
     */
    public static Map<String, Object> ccProcessor(DispatchContext dctx, Map<String, ? extends Object> context) {
        Delegator delegator = dctx.getDelegator();
        GenericValue paymentPref = (GenericValue) context.get("orderPaymentPreference");
        GenericValue paymentMethod = (GenericValue) context.get("payPalPaymentMethod");
        String orderId = (String) context.get("orderId");
        BigDecimal processAmount = (BigDecimal) context.get("processAmount");
        String configString = (String) context.get("paymentConfig");

        if (configString == null) {
            configString = "envelopes.properties";
        }

        Map<String, Object> result = ServiceUtil.returnSuccess();
        result.put("processAmount", processAmount);

        Result<Transaction> transactionResult = null;
        try {
            transactionResult = PayPalCheckoutHelper.setAuthorizationRequest(paymentMethod.getString("payPalOrderId"), paymentPref.getString("paymentMethodId"), paymentPref.getString("orderId"), processAmount.toPlainString());
            if (transactionResult.isSuccess()) {
                result.put("authResult", Boolean.TRUE);
                result.put("authCode", transactionResult.getTarget().getPayPalDetails().getAuthorizationId());
                result.put("authRefNum", transactionResult.getTarget().getId());
            } else {
                result.put("authRefNum", "ERROR_DURING_AUTH");
                result.put("authResult", Boolean.FALSE);
                result.put("resultDeclined", Boolean.TRUE);
            }
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError("Error processing PayPal payment. " + e + " : " + e.getMessage(), module);
        }

        return result;
    }

    /**
     * Capture the paypal payment amount
     * @param dctx
     * @param context
     * @return
     */
    public static Map<String, Object> ccCapture(DispatchContext dctx, Map<String, ? extends Object> context) {
        LocalDispatcher dispatcher = dctx.getDispatcher();
        Delegator delegator = dctx.getDelegator();
        GenericValue paymentPref = (GenericValue) context.get("orderPaymentPreference");
        GenericValue authTrans = (GenericValue) context.get("authTrans");
        BigDecimal amount = (BigDecimal) context.get("captureAmount");
        String configString = (String) context.get("paymentConfig");

        if (configString == null) {
            configString = "envelopes.properties";
        }

        if (authTrans == null) {
            authTrans = PaymentGatewayServices.getAuthTransaction(paymentPref);
        }

        if (authTrans == null) {
            return ServiceUtil.returnError("No authorization transaction found for the OrderPaymentPreference; cannot capture");
        }

        // check the response
        Map<String, Object> result = ServiceUtil.returnSuccess();
        result.put("captureAmount", amount);
        result.put("captureMessage", null);

        Result<Transaction> transactionResult = null;
        try {
            transactionResult = PayPalCheckoutHelper.setCaptureRequest(authTrans.getString("referenceNum"), amount.toPlainString());
            if (transactionResult.isSuccess()) {
                result.put("captureResult", Boolean.TRUE);
                result.put("captureCode", transactionResult.getTarget().getPayPalDetails().getCaptureId());
                result.put("captureRefNum", transactionResult.getTarget().getId());
                dispatcher.runSync("changeOrderStatus", UtilMisc.toMap("orderId", paymentPref.getString("orderId"), "statusId", "ORDER_CREATED", "userLogin", EnvUtil.getSystemUser(delegator)));
            } else {
                result.put("captureResult", Boolean.FALSE);
                result.put("captureRefNum", "ERROR_DURING_CAPTURE");
            }
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError("Error processing PayPal payment. " + e + " : " + e.getMessage(), module);
        }

        return result;
    }

    /**
     * Refund a paypal transaction
     * @param dctx
     * @param context
     * @return
     */
    public static Map<String, Object> ccRefund(DispatchContext dctx, Map<String, ? extends Object> context) {
        GenericValue paymentPref = (GenericValue) context.get("orderPaymentPreference");
        BigDecimal amount = (BigDecimal) context.get("refundAmount");
        String configString = (String) context.get("paymentConfig");

        if (configString == null) {
            configString = "envelopes.properties";
        }

        GenericValue captureTrans = PaymentGatewayServices.getCaptureTransaction(paymentPref);

        if(captureTrans == null) {
            return ServiceUtil.returnError("No capture transaction found for the OrderPaymentPreference; cannot refund");
        }

        // check the response
        Map<String, Object> result = ServiceUtil.returnSuccess();
        result.put("refundAmount", amount);

        Result<Transaction> transactionResult = null;
        try {
            transactionResult = PayPalCheckoutHelper.setRefundRequest(captureTrans.getString("referenceNum"), amount.toPlainString());
            if (transactionResult.isSuccess()) {
                result.put("refundResult", Boolean.TRUE);
                result.put("refundCode", transactionResult.getTarget().getPayPalDetails().getRefundId());
                result.put("refundRefNum", transactionResult.getTarget().getId());
                result.put("refundMessage", "Refund for $" + amount.toPlainString());
            } else {
                result.put("refundResult", Boolean.FALSE);
            }
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError("Error processing PayPal payment. " + e + " : " + e.getMessage(), module);
        }

        return result;
    }

    /**
     * Voide the paypal payment amount
     * @param dctx
     * @param context
     * @return
     */
    public static Map<String, Object> ccVoid(DispatchContext dctx, Map<String, ? extends Object> context) {
        // check the response
        Map<String, Object> result = ServiceUtil.returnSuccess();
        result.put("releaseAmount", "amount");
        return result;
    }
}
