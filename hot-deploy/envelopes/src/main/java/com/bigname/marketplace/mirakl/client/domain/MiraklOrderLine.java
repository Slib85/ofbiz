package com.bigname.marketplace.mirakl.client.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.math.BigDecimal;

/**
 * Created by Manu on 3/27/2017.
 */
@JsonIgnoreProperties({"shipping_price_unit", "shipping_price_additional_unit"})
public class MiraklOrderLine {
    @JsonProperty("order_line_id")
    private String id;

    @JsonProperty("order_line_index")
    private int index;

    @JsonUnwrapped
    private MiraklOrderLineHistory history;

    private int quantity;
    private String priceAdditionalInfo;
    private BigDecimal price;
    private BigDecimal shippingPrice;
    private BigDecimal totalPrice;

    @JsonUnwrapped
    @JsonProperty("state")
    private MiraklOrderLineStatus status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public MiraklOrderLineHistory getHistory() {
        return history;
    }

    public void setHistory(MiraklOrderLineHistory history) {
        this.history = history;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPriceAdditionalInfo() {
        return priceAdditionalInfo;
    }

    public void setPriceAdditionalInfo(String priceAdditionalInfo) {
        this.priceAdditionalInfo = priceAdditionalInfo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(BigDecimal shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public MiraklOrderLineStatus getStatus() {
        return status;
    }

    public void setStatus(MiraklOrderLineStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MiraklOrderLine that = (MiraklOrderLine) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
