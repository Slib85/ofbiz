
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Tolerances complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Tolerances">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}QuantityTolerance" minOccurs="0"/>
 *         &lt;element ref="{}PriceTolerance" minOccurs="0"/>
 *         &lt;element ref="{}TimeTolerance" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Tolerances", propOrder = {
    "quantityTolerance",
    "priceTolerance",
    "timeTolerance"
})
public class Tolerances {

    @XmlElement(name = "QuantityTolerance")
    protected QuantityTolerance quantityTolerance;
    @XmlElement(name = "PriceTolerance")
    protected PriceTolerance priceTolerance;
    @XmlElement(name = "TimeTolerance")
    protected TimeTolerance timeTolerance;

    /**
     * Gets the value of the quantityTolerance property.
     * 
     * @return
     *     possible object is
     *     {@link QuantityTolerance }
     *     
     */
    public QuantityTolerance getQuantityTolerance() {
        return quantityTolerance;
    }

    /**
     * Sets the value of the quantityTolerance property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuantityTolerance }
     *     
     */
    public void setQuantityTolerance(QuantityTolerance value) {
        this.quantityTolerance = value;
    }

    /**
     * Gets the value of the priceTolerance property.
     * 
     * @return
     *     possible object is
     *     {@link PriceTolerance }
     *     
     */
    public PriceTolerance getPriceTolerance() {
        return priceTolerance;
    }

    /**
     * Sets the value of the priceTolerance property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceTolerance }
     *     
     */
    public void setPriceTolerance(PriceTolerance value) {
        this.priceTolerance = value;
    }

    /**
     * Gets the value of the timeTolerance property.
     * 
     * @return
     *     possible object is
     *     {@link TimeTolerance }
     *     
     */
    public TimeTolerance getTimeTolerance() {
        return timeTolerance;
    }

    /**
     * Sets the value of the timeTolerance property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeTolerance }
     *     
     */
    public void setTimeTolerance(TimeTolerance value) {
        this.timeTolerance = value;
    }

}
