package com.bigname.marketplace.mirakl.client.domain;

/**
 * Created by Manu on 2/24/2017.
 */
public class MiraklCustomerShippingAddress extends AbstractCustomerAddress {

    private String additionalInfo;

    private String internalAdditionalInfo;

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * Configure addtional information of the customer shipping address
     */
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getInternalAdditionalInfo() {
        return internalAdditionalInfo;
    }

    /**
     * Configure internal addtional information of the customer shipping address<br/>
     * This information cannot be seen by the shop
     */
    public void setInternalAdditionalInfo(String internalAdditionalInfo) {
        this.internalAdditionalInfo = internalAdditionalInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        MiraklCustomerShippingAddress that = (MiraklCustomerShippingAddress) o;

        boolean equals = internalAdditionalInfo != null ? internalAdditionalInfo.equals(that.internalAdditionalInfo) : that.internalAdditionalInfo == null;

        return equals && (additionalInfo != null ? additionalInfo.equals(that.additionalInfo) : that.additionalInfo == null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (additionalInfo != null ? additionalInfo.hashCode() : 0);
        result = 31 * result + (internalAdditionalInfo != null ? internalAdditionalInfo.hashCode() : 0);
        return result;
    }

}
