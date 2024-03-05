
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderStatusRequestIDInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderStatusRequestIDInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="orderStatusRequestID" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="orderStatusRequestDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderStatusRequestIDInfo")
public class OrderStatusRequestIDInfo {

    @XmlAttribute(name = "orderStatusRequestID", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String orderStatusRequestID;
    @XmlAttribute(name = "orderStatusRequestDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String orderStatusRequestDate;

    /**
     * Gets the value of the orderStatusRequestID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderStatusRequestID() {
        return orderStatusRequestID;
    }

    /**
     * Sets the value of the orderStatusRequestID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderStatusRequestID(String value) {
        this.orderStatusRequestID = value;
    }

    /**
     * Gets the value of the orderStatusRequestDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderStatusRequestDate() {
        return orderStatusRequestDate;
    }

    /**
     * Sets the value of the orderStatusRequestDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderStatusRequestDate(String value) {
        this.orderStatusRequestDate = value;
    }

}
