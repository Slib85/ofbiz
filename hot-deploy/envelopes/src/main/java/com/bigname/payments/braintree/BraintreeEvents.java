package com.bigname.payments.braintree;

import com.envelopes.order.OrderHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import com.google.gson.Gson;
import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class BraintreeEvents {
    public static final String module = BraintreeEvents.class.getName();

    public static String preAuth(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        Map<String, Object> jsonResponse = new HashMap<>();
        boolean success = false;
        String responseMessage = "";

        try {
            Map<String, Object> orderInfo = OrderHelper.buildOrderDetails(request, OrderHelper.getOrderData(delegator, (String) context.get("orderId"), false));

            Map<String, String> braintreeResponse = BraintreeHelper.preAuth(orderInfo, (String) context.get("paymentMethodTypeId"), (String) context.get("nonceToken"), (String) context.get("webSiteId"));

            // TODO: Clean up
            GenericValue orderPaymentPreference = delegator.findByAnd("OrderPaymentPreference", UtilMisc.toMap("orderId", (String) context.get("orderId")), null, false).get(0);
            orderPaymentPreference.put("paymentMethodTypeId", context.get("paymentMethodTypeId"));

            GenericValue orderHeader = delegator.findByAnd("OrderHeader", UtilMisc.toMap("orderId", (String) context.get("orderId")), null, false).get(0);

            if (UtilValidate.isNotEmpty(braintreeResponse.get("transactionId")) && UtilValidate.isNotEmpty(braintreeResponse.get("creditCardToken"))) {
                orderPaymentPreference.put("statusId", "PAYMENT_AUTHORIZED");

                orderHeader.put("statusId", "ORDER_CREATED");

                success = true;
                responseMessage = "Success!";
            } else {
                orderPaymentPreference.put("statusId", "PAYMENT_DECLINED");

                orderHeader.put("statusId", "ORDER_PENDING");

                success = false;
                responseMessage = "Payment Declined";
            }

            delegator.store(orderPaymentPreference);
            delegator.store(orderHeader);

            if (UtilValidate.isNotEmpty(braintreeResponse.get("creditCardToken")) && UtilValidate.isNotEmpty(braintreeResponse.get("transactionId"))) {
                Gson gson = new Gson();

                braintreeResponse.put("orderPaymentPreferenceId", (String) orderPaymentPreference.get("orderPaymentPreferenceId"));
                braintreeResponse.put("orderId", (String) context.get("orderId"));

                GenericValue paymentGatewayResponse = delegator.findByAnd("PaymentGatewayResponse", UtilMisc.toMap("orderPaymentPreferenceId", orderPaymentPreference.get("orderPaymentPreferenceId")), null, false).get(0);

                if (UtilValidate.isEmpty(paymentGatewayResponse)) {
                    paymentGatewayResponse = delegator.makeValue("PaymentGatewayResponse");
                    paymentGatewayResponse.put("paymentGatewayResponseId", delegator.getNextSeqId("PaymentGatewayResponse"));
                }

                paymentGatewayResponse.put("paymentServiceTypeEnumId", "PRDS_PAY_AUTH");
                paymentGatewayResponse.put("orderPaymentPreferenceId", orderPaymentPreference.get("orderPaymentPreferenceId"));
                paymentGatewayResponse.put("paymentMethodTypeId", context.get("paymentMethodTypeId"));
                paymentGatewayResponse.put("paymentMethodId", orderPaymentPreference.get("paymentMethodId"));
                paymentGatewayResponse.put("transCodeEnumId", "PGT_AUTHORIZE");
                paymentGatewayResponse.put("amount", new BigDecimal(String.valueOf(orderInfo.get("grandTotal"))));
                paymentGatewayResponse.put("currencyUomId", "USD");
                paymentGatewayResponse.put("gatewayAvsResult", "N");
                paymentGatewayResponse.put("gatewayCvResult", "N");
                paymentGatewayResponse.put("transactionDate", UtilDateTime.nowTimestamp());
                paymentGatewayResponse.put("requestMsg", gson.toJson(braintreeResponse));

                delegator.createOrStore(paymentGatewayResponse);
            }
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError("Error trying to pre-authorize braintree nonce token payment. " + context.toString(), module);
        }

        jsonResponse.put("success", success);
        jsonResponse.put("responseMessage", responseMessage);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }
}
