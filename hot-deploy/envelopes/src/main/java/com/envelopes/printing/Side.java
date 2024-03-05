package com.envelopes.printing;

/**
 * Created by Manu on 9/9/2016.
 */
public enum Side {
    FRONT(0), BACK(1), UNKNOWN(-1);

    private long sideIndex = -1;
    Side(long sideIndex) {
        this.sideIndex = sideIndex;
    }
    public static Side getSide(String sideStr) {
        for (Side side : values()) {
            if(side.name().equals(sideStr)) {
                return side;
            }
        }
        return UNKNOWN;
    }

    public long getSideIndex() {
        return this.sideIndex;
    }
}
