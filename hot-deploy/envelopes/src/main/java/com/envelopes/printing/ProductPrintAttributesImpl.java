package com.envelopes.printing;

/**
 * Created by Manu on 9/7/2016.
 */
public class ProductPrintAttributesImpl implements ProductPrintAttributes {
    private String parentProductId;
    private String productName;
    private String productType;
    private double height;
    private double width;
    private double openHeight;
    private double openWidth;
    private Press preferredDigitalPress;
    private Press preferredOffsetPress;
    private boolean nonPerfectingFlag;

    public ProductPrintAttributesImpl(String parentProductId, String productName, String productType, double height, double width, double openHeight, double openWidth, Press preferredDigitalPress, Press preferredOffsetPress, boolean nonPerfectingFlag) {
        this.parentProductId = parentProductId;
        this.productName = productName;
        this.productType = productType;
        this.height = height;
        this.width = width;
        this.openHeight = openHeight;
        this.openWidth = openWidth;
        this.preferredDigitalPress = preferredDigitalPress;
        this.preferredOffsetPress = preferredOffsetPress;
        this.nonPerfectingFlag = nonPerfectingFlag;
    }

    @Override
    public String getParentProductId() {
        return parentProductId;
    }

    @Override
    public String getProductName() {
        return productName;
    }

    @Override
    public String getProductType() {
        return productType;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getOpenHeight() {
        return openHeight;
    }

    @Override
    public double getOpenWidth() {
        return openWidth;
    }

    @Override
    public Press getPreferredDigitalPress() {
        return preferredDigitalPress;
    }

    @Override
    public Press getPreferredOffsetPress() {
        return preferredOffsetPress;
    }

    @Override
    public boolean isNonPerfectingFlag() {
        return nonPerfectingFlag;
    }
}
