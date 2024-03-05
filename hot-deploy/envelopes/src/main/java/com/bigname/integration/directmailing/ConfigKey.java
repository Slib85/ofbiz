package com.bigname.integration.directmailing;

/**
 * Created by Manu on 9/11/2018.
 */
public enum ConfigKey {
    C2M_API_ENDPOINT("click2mail.api.endpoint"),
    C2M_USER_NAME("click2mail.api.username"),
    C2M_PASSWORD("click2mail.api.password"),
    BILLING_TYPE("click2mail.billing.type"),
    BILLING_NAME("click2mail.billing.name"),
    BILLING_ADDRESS1("click2mail.billing.address1"),
    BILLING_ADDRESS2("click2mail.billing.address2"),
    BILLING_CITY("click2mail.billing.city"),
    BILLING_STATE("click2mail.billing.state"),
    BILLING_ZIP("click2mail.billing.zip"),
    BILLING_CC_TYPE("click2mail.billing.cc.type"),
    BILLING_CC_NUMBER("click2mail.billing.cc.number"),
    BILLING_CC_MONTH("click2mail.billing.cc.month"),
    BILLING_CC_YEAR("click2mail.billing.cc.year"),
    BILLING_CC_CVV("click2mail.billing.cc.cvv"),
    UNKNOWN("", true);

    private String key;
    private boolean optional;

    ConfigKey(String key, boolean... optional) {
        this.key = key;
        if(optional != null && optional.length > 0) {
            this.optional = optional[0];
        }
    }

    public String key() {
        return this.key;
    }

    public boolean isOptional() {
        return optional;
    }

    public static ConfigKey find(String name) {
        try {
            return ConfigKey.valueOf(name);
        } catch (IllegalArgumentException e) {
            return ConfigKey.UNKNOWN;
        }
    }

}
