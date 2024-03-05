package com.bigname.integration.click2mail.client.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "addressList")
public class AddressList {
    public AddressList(List address, String addressMappingId, String addressListName){
        this.address = address;
        this.addressMappingId = addressMappingId;
        this.addressListName = addressListName;
    }
    @JacksonXmlElementWrapper(localName = "addresses")
    public List address;
    public String addressMappingId;
    public String addressListName;
}
