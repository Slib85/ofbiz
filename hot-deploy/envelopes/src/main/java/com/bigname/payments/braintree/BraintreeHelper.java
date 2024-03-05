package com.bigname.payments.braintree;

import com.braintreegateway.*;
import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class BraintreeHelper {
    public static final String module = BraintreeHelper.class.getName();

    private static boolean BRAINTREE_ENABLED;
    public static String BRAINTREE_ENVIRONMENT;
    private static String BRAINTREE_TOKEN;
    private static String BRAINTREE_MERCHANT_ID;
    private static String BRAINTREE_PUBLIC_KEY;
    private static String BRAINTREE_PRIVATE_KEY;
    private static String BRAINTREE_ACCOUNT_ID;
    public static BraintreeGateway GATEWAY;

    static {
        try{
            BRAINTREE_ENABLED = UtilProperties.getPropertyAsBoolean("envelopes", "braintree.enabled", true);
            BRAINTREE_ENVIRONMENT = UtilProperties.getPropertyValue("envelopes", "braintree.env");
            BRAINTREE_TOKEN = UtilProperties.getPropertyValue("envelopes", "braintree." + BRAINTREE_ENVIRONMENT + ".token");
            BRAINTREE_MERCHANT_ID = UtilProperties.getPropertyValue("envelopes", "braintree." + BRAINTREE_ENVIRONMENT + ".merchant.id");
            BRAINTREE_PUBLIC_KEY = UtilProperties.getPropertyValue("envelopes", "braintree." + BRAINTREE_ENVIRONMENT + ".public.key");
            BRAINTREE_PRIVATE_KEY = UtilProperties.getPropertyValue("envelopes", "braintree." + BRAINTREE_ENVIRONMENT + ".private.key");
            BRAINTREE_ACCOUNT_ID = UtilProperties.getPropertyValue("envelopes", "braintree." + BRAINTREE_ENVIRONMENT + ".account.id");

            GATEWAY = new BraintreeGateway(
                    (BRAINTREE_ENVIRONMENT.equals("sandbox")) ? Environment.SANDBOX : Environment.PRODUCTION,
                    BRAINTREE_MERCHANT_ID,
                    BRAINTREE_PUBLIC_KEY,
                    BRAINTREE_PRIVATE_KEY
            );
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError("Error, could not open: " + "envelopes.properties", module);
        }
    }

    // Use Live credentials WITH client token

    /**
     * Generate a new client token
     * @return
     */
    public static String getClientToken() {
        return getClientToken(null);
    }

    public static String getClientToken(String webSiteId) {
        ClientTokenRequest clientTokenRequest = new ClientTokenRequest().customerId(null).merchantAccountId(UtilValidate.isNotEmpty(webSiteId) ? webSiteId + "com_instant" : null);
        return GATEWAY.clientToken().generate(clientTokenRequest);
    }

    public static void createCustomerWithPaymentMethod(Map<String, Object> orderInfo, String nonce) {
        CustomerRequest request = new CustomerRequest()
                .firstName((String) ((HashMap) orderInfo.get("billingAddress")).get("firstName"))
                .lastName((String) ((HashMap) orderInfo.get("billingAddress")).get("lastName"))
                .email((String) orderInfo.get("email"))
                .paymentMethodNonce(nonce);

        Result<Customer> result = GATEWAY.customer().create(request);

        if (result.isSuccess()) {
            Customer customer = result.getTarget();
            customer.getId();
            customer.getPaymentMethods().get(0).getToken();
        } else {
            // Handle errors
        }
    }

    public static Map<String, String> preAuth(Map<String, Object> orderInfo, String paymentMethodTypeId, String nonce, String webSiteId) {
        Map<String, String> response = new HashMap<>();

        TransactionRequest request = new TransactionRequest()
                .amount(new BigDecimal(String.valueOf(orderInfo.get("grandTotal"))))
                .paymentMethodNonce(nonce)
                .orderId((String) orderInfo.get("orderId"))
                .merchantAccountId(webSiteId + "com_instant") // ENVELOPES, FOLDERS
                .customer()
                    .email((String) orderInfo.get("email"))
                    .done()
                .billingAddress()
                    .firstName((String) ((HashMap) orderInfo.get("billingAddress")).get("firstName"))
                    .lastName((String) ((HashMap) orderInfo.get("billingAddress")).get("lastName"))
                    .company((String) ((HashMap) orderInfo.get("billingAddress")).get("company"))
                    .streetAddress((String) ((HashMap) orderInfo.get("billingAddress")).get("address1"))
                    .extendedAddress((String) ((HashMap) orderInfo.get("billingAddress")).get("address2"))
                    .region((String) ((HashMap) orderInfo.get("billingAddress")).get("stateProvince"))
                    .postalCode((String) ((HashMap) orderInfo.get("billingAddress")).get("postalCode"))
                    .countryCodeAlpha3((String) ((HashMap) orderInfo.get("billingAddress")).get("country"))
                    .done()
                .shippingAddress()
                    .firstName((String) ((HashMap) orderInfo.get("shippingAddress")).get("firstName"))
                    .lastName((String) ((HashMap) orderInfo.get("shippingAddress")).get("lastName"))
                    .company((String) ((HashMap) orderInfo.get("shippingAddress")).get("company"))
                    .streetAddress((String) ((HashMap) orderInfo.get("shippingAddress")).get("address1"))
                    .extendedAddress((String) ((HashMap) orderInfo.get("shippingAddress")).get("address2"))
                    .region((String) ((HashMap) orderInfo.get("shippingAddress")).get("stateProvince"))
                    .postalCode((String) ((HashMap) orderInfo.get("shippingAddress")).get("postalCode"))
                    .countryCodeAlpha3((String) ((HashMap) orderInfo.get("shippingAddress")).get("country"))
                    .done()
                .options()
                    .submitForSettlement(false)
                    .storeInVaultOnSuccess(true)
                    .done();

        Result<Transaction> result = GATEWAY.transaction().sale(request);

        if (result.isSuccess()) {
            response.put("transactionId", result.getTarget().getId());
            response.put("creditCardToken", (paymentMethodTypeId.equals("CREDIT_CARD") ? result.getTarget().getCreditCard().getToken() : result.getTarget().getPayPalDetails().getImplicitlyVaultedPaymentMethodToken()));
            response.put("cardType", result.getTarget().getCreditCard().getCardType());
        } else {
            Debug.logError("Issue trying to authorize CC with nonce token (Braintree). " + result.getMessage(), module);
        }

        return response;
    }
}
