package com.bigname.integration.click2mail.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequest;
import com.bigname.core.restful.client.request.ApiEndpoint;
import com.bigname.integration.click2mail.client.domain.AddressList;

public class CreateAddressListRequest extends AbstractApiRequest {
    private AddressList addressList;

    public CreateAddressListRequest(AddressList addressList){
        setAddressList(addressList);
    }
    @Override
    public ApiEndpoint getEndpoint() {
        return Click2MailEndpoint.CREATE_ADDRESS_LIST;
    }

    public AddressList getAddressList() {
        return addressList;
    }

    public void setAddressList(AddressList addressList) {
        this.addressList = addressList;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddressList that = (AddressList) o;

        //return addressList != null ? addressList.equals(that.addressList) : that.addressList == null;
        return false;
    }

    @Override
    public int hashCode() {
        return addressList != null ? addressList.hashCode() : 0;
    }


}
