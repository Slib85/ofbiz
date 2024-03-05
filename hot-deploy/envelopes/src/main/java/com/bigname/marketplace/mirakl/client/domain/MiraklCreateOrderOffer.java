package com.bigname.marketplace.mirakl.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Manu on 3/27/2017.
 */
public class MiraklCreateOrderOffer {
    @JsonProperty("offer_id")
    private String id;
    @JsonProperty("offer_price")
    private BigDecimal priceUnit;
    private int quantity;
    private BigDecimal price;
    private BigDecimal shippingPrice;
    private String shippingTypeCode;
    private String orderLineId;
    private List<String> orderLineAdditionalFields;
//    private List<MiraklOrderTaxAmount> taxes;
//    private List<MiraklOrderTaxAmount> shippingTaxes;
//    private MiraklIsoCurrencyCode currencyIsoCode;
//    private Integer leadtimeToShip;

    public String getId() {
        return id;
    }

    /**
     * Offer ID
     */
    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getPriceUnit() {
        return priceUnit;
    }

    /**
     * Unit price of the offer
     */
    public void setPriceUnit(BigDecimal priceUnit) {
        this.priceUnit = priceUnit;
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * Quantity ordered of the offer
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Price without shipping price = ({@link #priceUnit} * {@link #quantity})
     */
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getShippingPrice() {
        return shippingPrice;
    }

    /**
     * Shipping price of the line
     */
    public void setShippingPrice(BigDecimal shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public String getShippingTypeCode() {
        return shippingTypeCode;
    }

    /**
     * Code of the shipping type used
     */
    public void setShippingTypeCode(String shippingTypeCode) {
        this.shippingTypeCode = shippingTypeCode;
    }

    public String getOrderLineId() {
        return orderLineId;
    }

    /**
     * (Optional) Force the value of the order line ID. If nothing specified the ID will be generated based on the commercial ID
     */
    public void setOrderLineId(String orderLineId) {
        this.orderLineId = orderLineId;
    }

    public List<String> getOrderLineAdditionalFields() {
        return orderLineAdditionalFields;
    }

    public void setOrderLineAdditionalFields(List<String> orderLineAdditionalFields) {
        this.orderLineAdditionalFields = orderLineAdditionalFields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MiraklCreateOrderOffer that = (MiraklCreateOrderOffer) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
