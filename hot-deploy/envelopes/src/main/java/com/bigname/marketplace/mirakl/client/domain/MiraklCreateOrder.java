package com.bigname.marketplace.mirakl.client.domain;

import java.util.List;

/**
 * Created by Manu on 3/27/2017.
 */
public class MiraklCreateOrder {
    private String commercialId;
    private MiraklOrderCustomer customer;
    private List<MiraklCreateOrderOffer> offers;
    private boolean scored;
    private String shippingZoneCode;
    private String paymentWorkFlow = "PAY_ON_DELIVERY";
    private String channelCode;

    public String getCommercialId() {
        return commercialId;
    }

    /**
     * Commercial ID of the order
     * Must be a composition of alphanumeric characters, hyphen and underscore
     */
    public void setCommercialId(String commercialId) {
        this.commercialId = commercialId;
    }

    public MiraklOrderCustomer getCustomer() {
        return customer;
    }

    /**
     * Customer of the order
     */
    public void setCustomer(MiraklOrderCustomer customer) {
        this.customer = customer;
    }

    public List<MiraklCreateOrderOffer> getOffers() {
        return offers;
    }

    /**
     * List of offers of the commercial order
     */
    public void setOffers(List<MiraklCreateOrderOffer> offers) {
        this.offers = offers;
    }

    public boolean getScored() {
        return scored;
    }

    /**
     * Is the customer is already scored or not?
     */
    public void setScored(boolean scored) {
        this.scored = scored;
    }

    public String getPaymentWorkFlow() {
        return paymentWorkFlow;
    }

    public void setPaymentWorkFlow(String paymentWorkFlow) {
        this.paymentWorkFlow = paymentWorkFlow;
    }

    public String getShippingZoneCode() {
        return shippingZoneCode;
    }

    /**
     * Shipping zone code of the commercial order
     */
    public void setShippingZoneCode(String shippingZoneCode) {
        this.shippingZoneCode = shippingZoneCode;
    }

    public String getChannelCode() {
        return channelCode;
    }

    /**
     * Channel code of the commercial order
     */
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MiraklCreateOrder that = (MiraklCreateOrder) o;

        if (scored != that.scored) {
            return false;
        }
        if (commercialId != null ? !commercialId.equals(that.commercialId) : that.commercialId != null) {
            return false;
        }
        if (customer != null ? !customer.equals(that.customer) : that.customer != null) {
            return false;
        }
        if (offers != null ? !offers.equals(that.offers) : that.offers != null) {
            return false;
        }
        if (shippingZoneCode != null ? !shippingZoneCode.equals(that.shippingZoneCode) : that.shippingZoneCode != null) {
            return false;
        }
        if (channelCode != null ? !channelCode.equals(that.channelCode) : that.channelCode != null) {
            return false;
        }
        return true;

    }

    @Override
    public int hashCode() {
        int result = commercialId != null ? commercialId.hashCode() : 0;
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + (offers != null ? offers.hashCode() : 0);
        result = 31 * result + (scored ? 1 : 0);
        result = 31 * result + (shippingZoneCode != null ? shippingZoneCode.hashCode() : 0);
        result = 31 * result + (channelCode != null ? channelCode.hashCode() : 0);
        return result;
    }

}
