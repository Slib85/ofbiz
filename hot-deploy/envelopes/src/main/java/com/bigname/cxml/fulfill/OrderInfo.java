
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{}OrderReference"/>
 *         &lt;element ref="{}OrderIDInfo"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderInfo", propOrder = {
    "orderReference",
    "orderIDInfo"
})
public class OrderInfo {

    @XmlElement(name = "OrderReference")
    protected OrderReference orderReference;
    @XmlElement(name = "OrderIDInfo")
    protected OrderIDInfo orderIDInfo;

    /**
     * Gets the value of the orderReference property.
     * 
     * @return
     *     possible object is
     *     {@link OrderReference }
     *     
     */
    public OrderReference getOrderReference() {
        return orderReference;
    }

    /**
     * Sets the value of the orderReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderReference }
     *     
     */
    public void setOrderReference(OrderReference value) {
        this.orderReference = value;
    }

    /**
     * Gets the value of the orderIDInfo property.
     * 
     * @return
     *     possible object is
     *     {@link OrderIDInfo }
     *     
     */
    public OrderIDInfo getOrderIDInfo() {
        return orderIDInfo;
    }

    /**
     * Sets the value of the orderIDInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderIDInfo }
     *     
     */
    public void setOrderIDInfo(OrderIDInfo value) {
        this.orderIDInfo = value;
    }

}
