package com.envelopes.printing;

import java.io.Serializable;

/**
 * Created by Manu on 8/16/2016.
 */
public interface InkColor extends Serializable {
    void setColorId(long colorId);
    long getColorId();
    String getColorCode();
    String getColorCodeOverride();
    String getColorName();
    boolean isCompareByColorCodeOverride();
    void setCompareByColorCodeOverride(boolean compareByColorCodeOverride);
}
