package com.bigname.marketplace.mirakl.client.domain;

import java.util.List;

/**
 * Created by Manu on 2/24/2017.
 */
public class MiraklProductsOffers {

    private List<MiraklProductOffers> products;

    public List<MiraklProductOffers> getProducts() {
        return products;
    }

    public void setProducts(List<MiraklProductOffers> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MiraklProductsOffers that = (MiraklProductsOffers) o;

        if (products != null ? !products.equals(that.products) : that.products != null) {
            return false;
        }
        return products.size() == that.products.size();

    }

    @Override
    public int hashCode() {
        int result = products != null ? products.hashCode() : 0;
        result = 31 * result + products.size();
        return result;
    }

}
