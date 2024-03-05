package com.bigname.payments.paypal;

import com.envelopes.order.OrderHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.service.LocalDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class PayPalCheckoutEvents {
    public static final String module = PayPalCheckoutEvents.class.getName();

    /**
     * Paypal IPN
     * @param request
     * @param response
     * @return
     */
    public static String paypalCheckoutIPN(HttpServletRequest request, HttpServletResponse response) {
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if(inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }

            //dispatcher.runSync("sendEmail", UtilMisc.toMap("email", "shoab@envelopes.com", "rawData", null, "data", UtilMisc.<String, String>toMap("subject", "PAYPAL IPN", "request", stringBuilder.toString()), "messageType", "genericEmail"));
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to read amazon data", module);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception ex) {
                    EnvUtil.reportError(ex);
                    Debug.logError(ex, "Error trying to read paypal data", module);
                }
            }
        }

        return "success";
    }

    /**
     * Refund request for PayPal
     * @param request
     * @param response
     * @return
     */
    public static String refundFunds(HttpServletRequest request, HttpServletResponse response) {
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<String, Object>();

        boolean success = false;

        try {
            GenericValue oPP = delegator.findOne("OrderPaymentPreference", UtilMisc.toMap("orderPaymentPreferenceId", (String) context.get("orderPaymentPreferenceId")), false);
            BigDecimal refundableAmount = OrderHelper.getRefundableAmount(delegator, oPP.getString("orderId"));

            if(new BigDecimal((String) context.get("refundAmount")).compareTo(refundableAmount) <= 0) {
                Map<String, Object> result = dispatcher.runSync("refundPayment", UtilMisc.toMap("orderPaymentPreference", oPP, "refundAmount", new BigDecimal((String) context.get("refundAmount")), "userLogin", (GenericValue) request.getSession().getAttribute("userLogin")));
                if(UtilValidate.isNotEmpty(result.get("responseMessage")) && "success".equalsIgnoreCase((String) result.get("responseMessage"))) {
                    success = true;
                }
            } else {
                jsonResponse.put("error", "Refund amount exceeds remaining balance on order.");
            }
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to capture funds.", module);
        }

        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }
}
