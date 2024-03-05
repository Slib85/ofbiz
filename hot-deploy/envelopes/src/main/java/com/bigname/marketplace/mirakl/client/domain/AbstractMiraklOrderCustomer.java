package com.bigname.marketplace.mirakl.client.domain;

/**
 * Created by Manu on 3/27/2017.
 */
public class AbstractMiraklOrderCustomer extends AbstractMiraklCustomer {

    private MiraklCustomerBillingAddress billingAddress;

    public MiraklCustomerBillingAddress getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(MiraklCustomerBillingAddress billingAddress) {
        this.billingAddress = billingAddress;
    }

}
