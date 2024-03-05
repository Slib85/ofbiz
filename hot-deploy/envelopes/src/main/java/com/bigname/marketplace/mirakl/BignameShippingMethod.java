package com.bigname.marketplace.mirakl;

/**
 * Created by Manu on 2/27/2017.
 */
public enum BignameShippingMethod {

    NEXT_DAY_AIR("NEXT_DAY_AIR", "US"),
    NEXT_DAY_SAVER("NEXT_DAY_SAVER", "US"),
    SECOND_DAY_AIR("SECOND_DAY_AIR", "US"),
    THREE_DAY_SELECT("THREE_DAY_SELECT", "US"),
    PRIORITY("PRIORITY", "US"),
    STANDARD("STANDARD", "US"),
    GROUND("GROUND", "US"),
    SMALL_FLAT_RATE("SMALL_FLAT_RATE", "US"),
    FIRST_CLASS("FIRST_CLASS", "US"),
    WORLDWIDE_EXPR("INTRNL_EXPRESS", "INTRNL"),
    WORLDWIDE_EXPTD("INTRNL_PREMIUM", "INTRNL"),
    INTRNL_STD("INTRNL_STANDARD", "INTRNL"),
    UNKNOWN("", "");

    private String miraklShippingMethod = "";
    private String shippingZone = "US";

    BignameShippingMethod(String miraklShippingMethod, String shippingZone) {
        this.miraklShippingMethod = miraklShippingMethod;
        this.shippingZone = shippingZone;
    }

    public String getMiraklShippingMethod() {
        return this.miraklShippingMethod;
    }

    public String getMiraklShippingZone() {
        return this.shippingZone;
    }

    public static String getMiraklShippingMethod(String bignameShippingMethodId) {
        try {
            return valueOf(bignameShippingMethodId).getMiraklShippingMethod();
        } catch (IllegalArgumentException ignore) {
            return UNKNOWN.getMiraklShippingMethod();
        }
    }

    public static String getMiraklShippingZone(String bignameShippingMethodId) {
        try {
            return valueOf(bignameShippingMethodId).getMiraklShippingZone();
        } catch (IllegalArgumentException ignore) {
            return UNKNOWN.getMiraklShippingZone();
        }
    }
}
