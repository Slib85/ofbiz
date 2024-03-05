
package com.bigname.cxml.fulfill;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TermsOfDelivery complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TermsOfDelivery">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}TermsOfDeliveryCode"/>
 *         &lt;element ref="{}ShippingPaymentMethod"/>
 *         &lt;element ref="{}TransportTerms" minOccurs="0"/>
 *         &lt;element ref="{}Address" minOccurs="0"/>
 *         &lt;element ref="{}Comments" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TermsOfDelivery", propOrder = {
    "termsOfDeliveryCode",
    "shippingPaymentMethod",
    "transportTerms",
    "address",
    "comments"
})
public class TermsOfDelivery {

    @XmlElement(name = "TermsOfDeliveryCode", required = true)
    protected TermsOfDeliveryCode termsOfDeliveryCode;
    @XmlElement(name = "ShippingPaymentMethod", required = true)
    protected ShippingPaymentMethod shippingPaymentMethod;
    @XmlElement(name = "TransportTerms")
    protected TransportTerms transportTerms;
    @XmlElement(name = "Address")
    protected Address address;
    @XmlElement(name = "Comments")
    protected List<Comments> comments;

    /**
     * Gets the value of the termsOfDeliveryCode property.
     * 
     * @return
     *     possible object is
     *     {@link TermsOfDeliveryCode }
     *     
     */
    public TermsOfDeliveryCode getTermsOfDeliveryCode() {
        return termsOfDeliveryCode;
    }

    /**
     * Sets the value of the termsOfDeliveryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link TermsOfDeliveryCode }
     *     
     */
    public void setTermsOfDeliveryCode(TermsOfDeliveryCode value) {
        this.termsOfDeliveryCode = value;
    }

    /**
     * Gets the value of the shippingPaymentMethod property.
     * 
     * @return
     *     possible object is
     *     {@link ShippingPaymentMethod }
     *     
     */
    public ShippingPaymentMethod getShippingPaymentMethod() {
        return shippingPaymentMethod;
    }

    /**
     * Sets the value of the shippingPaymentMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShippingPaymentMethod }
     *     
     */
    public void setShippingPaymentMethod(ShippingPaymentMethod value) {
        this.shippingPaymentMethod = value;
    }

    /**
     * Gets the value of the transportTerms property.
     * 
     * @return
     *     possible object is
     *     {@link TransportTerms }
     *     
     */
    public TransportTerms getTransportTerms() {
        return transportTerms;
    }

    /**
     * Sets the value of the transportTerms property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportTerms }
     *     
     */
    public void setTransportTerms(TransportTerms value) {
        this.transportTerms = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setAddress(Address value) {
        this.address = value;
    }

    /**
     * Gets the value of the comments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the comments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Comments }
     * 
     * 
     */
    public List<Comments> getComments() {
        if (comments == null) {
            comments = new ArrayList<Comments>();
        }
        return this.comments;
    }

}
