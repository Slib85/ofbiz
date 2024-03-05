
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderMethod complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderMethod">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}OrderTarget"/>
 *         &lt;element ref="{}OrderProtocol" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderMethod", propOrder = {
    "orderTarget",
    "orderProtocol"
})
public class OrderMethod {

    @XmlElement(name = "OrderTarget", required = true)
    protected OrderTarget orderTarget;
    @XmlElement(name = "OrderProtocol")
    protected OrderProtocol orderProtocol;

    /**
     * Gets the value of the orderTarget property.
     * 
     * @return
     *     possible object is
     *     {@link OrderTarget }
     *     
     */
    public OrderTarget getOrderTarget() {
        return orderTarget;
    }

    /**
     * Sets the value of the orderTarget property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderTarget }
     *     
     */
    public void setOrderTarget(OrderTarget value) {
        this.orderTarget = value;
    }

    /**
     * Gets the value of the orderProtocol property.
     * 
     * @return
     *     possible object is
     *     {@link OrderProtocol }
     *     
     */
    public OrderProtocol getOrderProtocol() {
        return orderProtocol;
    }

    /**
     * Sets the value of the orderProtocol property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderProtocol }
     *     
     */
    public void setOrderProtocol(OrderProtocol value) {
        this.orderProtocol = value;
    }

}
