
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransportInformation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransportInformation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Route" minOccurs="0"/>
 *         &lt;element ref="{}ShippingContractNumber" minOccurs="0"/>
 *         &lt;element ref="{}ShippingInstructions" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransportInformation", propOrder = {
    "route",
    "shippingContractNumber",
    "shippingInstructions"
})
public class TransportInformation {

    @XmlElement(name = "Route")
    protected Route route;
    @XmlElement(name = "ShippingContractNumber")
    protected ShippingContractNumber shippingContractNumber;
    @XmlElement(name = "ShippingInstructions")
    protected ShippingInstructions shippingInstructions;

    /**
     * Gets the value of the route property.
     * 
     * @return
     *     possible object is
     *     {@link Route }
     *     
     */
    public Route getRoute() {
        return route;
    }

    /**
     * Sets the value of the route property.
     * 
     * @param value
     *     allowed object is
     *     {@link Route }
     *     
     */
    public void setRoute(Route value) {
        this.route = value;
    }

    /**
     * Gets the value of the shippingContractNumber property.
     * 
     * @return
     *     possible object is
     *     {@link ShippingContractNumber }
     *     
     */
    public ShippingContractNumber getShippingContractNumber() {
        return shippingContractNumber;
    }

    /**
     * Sets the value of the shippingContractNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShippingContractNumber }
     *     
     */
    public void setShippingContractNumber(ShippingContractNumber value) {
        this.shippingContractNumber = value;
    }

    /**
     * Gets the value of the shippingInstructions property.
     * 
     * @return
     *     possible object is
     *     {@link ShippingInstructions }
     *     
     */
    public ShippingInstructions getShippingInstructions() {
        return shippingInstructions;
    }

    /**
     * Sets the value of the shippingInstructions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShippingInstructions }
     *     
     */
    public void setShippingInstructions(ShippingInstructions value) {
        this.shippingInstructions = value;
    }

}
