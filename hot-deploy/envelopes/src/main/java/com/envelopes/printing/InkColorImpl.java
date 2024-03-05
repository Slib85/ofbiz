package com.envelopes.printing;

import java.util.Objects;

/**
 * Created by Manu on 9/2/2016.
 */
public class InkColorImpl implements InkColor {

    private long colorId;
    private String colorCode;
    private String colorCodeOverride;
    private String colorName;
    private boolean compareByColorCodeOverride;

    public InkColorImpl(String colorCode, String colorCodeOverride, String colorName) {
        this.colorCode = colorCode;
        this.colorCodeOverride = colorCodeOverride;
        this.colorName = colorName;
    }

    public long getColorId() {
        return colorId;
    }

    public void setColorId(long colorId) {
        if(colorId == Long.MIN_VALUE) {
            this.colorId = colorId;
        }
    }

    public String getColorCode() {
        return colorCode;
    }

    public String getColorCodeOverride() {
        return colorCodeOverride;
    }

    public String getColorName() {
        return colorName;
    }

    public boolean isCompareByColorCodeOverride() {
        return compareByColorCodeOverride;
    }

    public void setCompareByColorCodeOverride(boolean compareByColorCodeOverride) {
        this.compareByColorCodeOverride = compareByColorCodeOverride;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InkColorImpl)) return false;
        InkColorImpl inkColor = (InkColorImpl) o;
        if(isCompareByColorCodeOverride()) {
            String comparingColorCode1 = getColorCodeOverride().isEmpty() ? getColorCode() : getColorCodeOverride();
            String comparingColorCode2 = inkColor.getColorCodeOverride().isEmpty() ? inkColor.getColorCode() : inkColor.getColorCodeOverride();
            return comparingColorCode1.equals(comparingColorCode2);
        } else {
            return Objects.equals(getColorCode(), inkColor.getColorCode());
        }

    }

    @Override
    public int hashCode() {
        return Objects.hash(getColorCode());
    }
}
