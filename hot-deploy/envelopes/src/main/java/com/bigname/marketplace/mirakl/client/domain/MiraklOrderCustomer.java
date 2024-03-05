package com.bigname.marketplace.mirakl.client.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Locale;

/**
 * Created by Manu on 2/24/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MiraklOrderCustomer {
    @JsonProperty("customer_id")
    private String id;
    private String civility;
    private String firstname;
    private String lastname;
    private Locale locale;
    private String email;
    private MiraklCustomerShippingAddress shippingAddress;
    private MiraklCustomerBillingAddress billingAddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCivility() {
        return civility;
    }

    public void setCivility(String civility) {
        this.civility = civility;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MiraklCustomerShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(MiraklCustomerShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public MiraklCustomerBillingAddress getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(MiraklCustomerBillingAddress billingAddress) {
        this.billingAddress = billingAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MiraklOrderCustomer that = (MiraklOrderCustomer) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
