package com.bigname.integration.cimpress;

public enum ConfigKey {
    AUTH_ENDPOINT("cimpress.auth.endpoint"),
    FULFILLMENT_MANAGER_API_ENDPOINT("cimpress.fulfillment.manager.api.endpoint"),
    ORDER_MANAGER_API_ENDPOINT("cimpress.order.manager.api.endpoint"),
    CLIENT_ID("cimpress.client.id"),
    FULFILLER_ID("cimpress.fulfiller.id"),
    USER_NAME("cimpress.username"),
    PASSWORD("cimpress.password"),
    AUTO_ACCEPT("cimpress.order.import.auto.accept", true),
    NOTIFICATION_FETCH_SIZE("cimpress.order.import.notification.fetch.size", true),
    LOGGER_LEVEL("cimpress.order.import.logger.level", true),
    ALLOW_DUPLICATE("cimpress.order.import.allow.duplicate", true),
    ORDER_MODE("cimpress.order.import.mode"),
    PARTY_ID("cimpress.order.import.party.id"),
    NETSUITE_EXPORT("cimpress.order.import.netsuite.export"),
    SHIPPING_CHANGE_REPEAT_EMAIL("cimpress.order.import.shipping.change.email.repeat", true),
    ADDRESS_CHANGE_REPEAT_EMAIL("cimpress.order.import.address.change.email.repeat", true),
    CANCELLATION_REPEAT_EMAIL("cimpress.order.import.cancellation.email.repeat", true),
    NOTIFICATION_EMAIL_ADDRESS("cimpress.order.import.notification.email.address"),
    PRICE_CHECK("cimpress.order.import.product.price.check"),
    ORDER_NOTE("cimpress.order.header.note"),
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
