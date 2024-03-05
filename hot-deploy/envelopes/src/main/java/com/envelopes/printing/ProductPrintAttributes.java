package com.envelopes.printing;

/**
 * Created by Manu on 9/7/2016.
 */
public interface ProductPrintAttributes {
    String getParentProductId();

    String getProductName();

    String getProductType();

    double getHeight();

    double getWidth();

    double getOpenHeight();

    double getOpenWidth();

    Press getPreferredDigitalPress();

    Press getPreferredOffsetPress();

    boolean isNonPerfectingFlag();
}
