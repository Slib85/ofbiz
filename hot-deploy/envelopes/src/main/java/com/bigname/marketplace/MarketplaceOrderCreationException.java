package com.bigname.marketplace;

/**
 * Created by Manu on 3/21/2017.
 */
public class MarketplaceOrderCreationException extends RuntimeException {
    public MarketplaceOrderCreationException() {
        super();
    }

    public MarketplaceOrderCreationException(String message) {
        super(message);
    }

    public MarketplaceOrderCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MarketplaceOrderCreationException(Throwable cause) {
        super(cause);
    }

    protected MarketplaceOrderCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
