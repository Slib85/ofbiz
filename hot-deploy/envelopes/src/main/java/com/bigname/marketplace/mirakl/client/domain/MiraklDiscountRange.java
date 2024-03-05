package com.bigname.marketplace.mirakl.client.domain;

import java.math.BigDecimal;

/**
 * Created by Manu on 2/23/2017.
 */
public class MiraklDiscountRange {
    private BigDecimal price;
    private int quantityThreshold;

    public MiraklDiscountRange() {
    }

    public MiraklDiscountRange(BigDecimal price, int quantityThreshold) {
        this.price = price;
        this.quantityThreshold = quantityThreshold;
    }

    /**
     * The price that applies if the quantity threshold is reached
     */
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * The quantity threshold to determine if the discount applies
     */
    public int getQuantityThreshold() {
        return quantityThreshold;
    }

    public void setQuantityThreshold(int quantityThreshold) {
        this.quantityThreshold = quantityThreshold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MiraklDiscountRange that = (MiraklDiscountRange) o;

        if (quantityThreshold != that.quantityThreshold) {
            return false;
        }
        return price != null ? price.equals(that.price) : that.price == null;

    }

    @Override
    public int hashCode() {
        int result = price != null ? price.hashCode() : 0;
        result = 31 * result + quantityThreshold;
        return result;
    }
}
