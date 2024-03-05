package com.bigname.marketplace.mirakl.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Manu on 2/24/2017.
 */
public class MiraklProductOffers {

    @JsonProperty("product_sku")
    private String productId;

    private List<MiraklOffer> offers;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<MiraklOffer> getOffers() {
        return offers;
    }

    public void setOffers(List<MiraklOffer> offers) {
        this.offers = offers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MiraklProductOffers that = (MiraklProductOffers) o;

        if (offers != null ? !offers.equals(that.offers) : that.offers != null) {
            return false;
        }
        return offers.size() == that.offers.size();

    }

    @Override
    public int hashCode() {
        int result = offers != null ? offers.hashCode() : 0;
        result = 31 * result + offers.size();
        return result;
    }

}
