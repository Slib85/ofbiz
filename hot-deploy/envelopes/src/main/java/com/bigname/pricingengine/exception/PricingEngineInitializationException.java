package com.bigname.pricingengine.exception;

public class PricingEngineInitializationException extends RuntimeException {

    public PricingEngineInitializationException(Throwable cause) {
        super("An error occurred while initializing the Pricing Engine", cause);
    }

}
