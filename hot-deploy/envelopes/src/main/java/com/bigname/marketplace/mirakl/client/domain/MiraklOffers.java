package com.bigname.marketplace.mirakl.client.domain;

import java.util.List;

/**
 * Created by Manu on 2/23/2017.
 */
public class MiraklOffers {
    private List<MiraklOffer> offers;

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

        MiraklOffers that = (MiraklOffers) o;

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
