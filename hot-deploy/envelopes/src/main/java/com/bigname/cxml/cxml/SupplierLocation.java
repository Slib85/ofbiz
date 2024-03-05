
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SupplierLocation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SupplierLocation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Address"/>
 *         &lt;element ref="{}OrderMethods"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplierLocation", propOrder = {
    "address",
    "orderMethods"
})
public class SupplierLocation {

    @XmlElement(name = "Address", required = true)
    protected Address address;
    @XmlElement(name = "OrderMethods", required = true)
    protected OrderMethods orderMethods;

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
     * Gets the value of the orderMethods property.
     * 
     * @return
     *     possible object is
     *     {@link OrderMethods }
     *     
     */
    public OrderMethods getOrderMethods() {
        return orderMethods;
    }

    /**
     * Sets the value of the orderMethods property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderMethods }
     *     
     */
    public void setOrderMethods(OrderMethods value) {
        this.orderMethods = value;
    }

}
