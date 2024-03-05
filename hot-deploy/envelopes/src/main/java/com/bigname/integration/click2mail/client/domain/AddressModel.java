package com.bigname.integration.click2mail.client.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

public class AddressModel {
    public AddressModel(String firstName,
                   String lastName,
                   String organization,
                   String address1,
                   String address2,
                   String address3,
                   String city,
                   String state,
                   String zip,
                   String countryNonUS) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.organization = organization;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.countryNonUS = countryNonUS;
    }
    @JacksonXmlProperty(localName = "First_name")
    public String firstName;
    @JacksonXmlProperty(localName = "Last_name")
    public String lastName;
    @JacksonXmlProperty(localName = "Organization")
    public String organization;
    @JacksonXmlProperty(localName = "Address1")
    public String address1;
    @JacksonXmlProperty(localName = "Address2")
    public String address2;
    @JacksonXmlProperty(localName = "Address3")
    public String address3;
    @JacksonXmlProperty(localName = "City")
    public String city;
    @JacksonXmlProperty(localName = "State")
    public String state;
    @JacksonXmlProperty(localName = "Zip")
    public String zip;
    @JacksonXmlProperty(localName = "Country_non-US")
    public String countryNonUS;
}
