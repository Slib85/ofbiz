package com.bigname.pricingengine.exception;

public class PricingEngineException extends Exception {
    public PricingEngineException(Throwable cause) {
        super("An error occurred while getting the pricing data from Pricing Engine", cause);
    }
}
