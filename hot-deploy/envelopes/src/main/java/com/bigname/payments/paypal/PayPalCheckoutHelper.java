package com.bigname.payments.paypal;

import com.bigname.payments.braintree.BraintreeHelper;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilProperties;

import java.math.BigDecimal;

public class PayPalCheckoutHelper {
    public static final String module = PayPalCheckoutHelper.class.getName();

    private static boolean PAYPAL_ENABLED;
    private static String PAYPAL_ENVIRONMENT;
    private static String PAYPAL_ACCOUNT_ID;
    private static String PAYPAL_CLIENT_ID;
    private static String PAYPAL_SECRET;
    public static String PAYPAL_AUTO_CAPTURE;

    static {
        try{
            PAYPAL_ENABLED = UtilProperties.getPropertyAsBoolean("envelopes", "paypal.checkout.enabled", true);
            PAYPAL_ENVIRONMENT = UtilProperties.getPropertyValue("envelopes", "paypal.checkout.env");

            PAYPAL_ACCOUNT_ID = UtilProperties.getPropertyValue("envelopes", "paypal.checkout." + PAYPAL_ENVIRONMENT + ".account");
            PAYPAL_CLIENT_ID = UtilProperties.getPropertyValue("envelopes", "paypal.checkout." + PAYPAL_ENVIRONMENT + ".clientid");
            PAYPAL_SECRET = UtilProperties.getPropertyValue("envelopes", "paypal.checkout." + PAYPAL_ENVIRONMENT + ".secret");
            PAYPAL_AUTO_CAPTURE = UtilProperties.getPropertyValue("envelopes", "paypal.checkout.autocapture");
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError("Error, could not open: " + "envelopes.properties", module);
        }
    }

    /**
     * Get the response object for an order auth
     * @param payPalOrderId
     * @param paymentMethodId
     * @param orderId
     * @param amount
     * @return
     */
    public static Result<Transaction> setAuthorizationRequest(String payPalOrderId, String paymentMethodId, String orderId, String amount) {
        TransactionRequest transactionRequest = new TransactionRequest()
                .amount(new BigDecimal(amount))
                .orderId(orderId + "-" + paymentMethodId)
                .paymentMethodNonce(payPalOrderId)
                .options()
                .submitForSettlement(false)
                .done();

        return BraintreeHelper.GATEWAY.transaction().sale(transactionRequest);
    }

    /**
     * Get the response object for an order capture
     * @param transactionId
     * @param amount
     * @return
     */
    public static Result<Transaction> setCaptureRequest(String transactionId, String amount) {
        return BraintreeHelper.GATEWAY.transaction().submitForSettlement(transactionId, new BigDecimal(amount));
    }

    /**
     * Get the response object for a order refund
     * @param transactionId
     * @param amount
     * @return
     */
    public static Result<Transaction> setRefundRequest(String transactionId, String amount) {
        return BraintreeHelper.GATEWAY.transaction().refund(transactionId, new BigDecimal(amount));
    }
}
