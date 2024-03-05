
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AdditionalPrices complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AdditionalPrices">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}UnitGrossPrice" minOccurs="0"/>
 *         &lt;element ref="{}InformationalPrice" minOccurs="0"/>
 *         &lt;element ref="{}InformationalPriceExclTax" minOccurs="0"/>
 *         &lt;element ref="{}UnitNetPriceCorrection" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdditionalPrices", propOrder = {
    "unitGrossPrice",
    "informationalPrice",
    "informationalPriceExclTax",
    "unitNetPriceCorrection"
})
public class AdditionalPrices {

    @XmlElement(name = "UnitGrossPrice")
    protected UnitGrossPrice unitGrossPrice;
    @XmlElement(name = "InformationalPrice")
    protected InformationalPrice informationalPrice;
    @XmlElement(name = "InformationalPriceExclTax")
    protected InformationalPriceExclTax informationalPriceExclTax;
    @XmlElement(name = "UnitNetPriceCorrection")
    protected UnitNetPriceCorrection unitNetPriceCorrection;

    /**
     * Gets the value of the unitGrossPrice property.
     * 
     * @return
     *     possible object is
     *     {@link UnitGrossPrice }
     *     
     */
    public UnitGrossPrice getUnitGrossPrice() {
        return unitGrossPrice;
    }

    /**
     * Sets the value of the unitGrossPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitGrossPrice }
     *     
     */
    public void setUnitGrossPrice(UnitGrossPrice value) {
        this.unitGrossPrice = value;
    }

    /**
     * Gets the value of the informationalPrice property.
     * 
     * @return
     *     possible object is
     *     {@link InformationalPrice }
     *     
     */
    public InformationalPrice getInformationalPrice() {
        return informationalPrice;
    }

    /**
     * Sets the value of the informationalPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformationalPrice }
     *     
     */
    public void setInformationalPrice(InformationalPrice value) {
        this.informationalPrice = value;
    }

    /**
     * Gets the value of the informationalPriceExclTax property.
     * 
     * @return
     *     possible object is
     *     {@link InformationalPriceExclTax }
     *     
     */
    public InformationalPriceExclTax getInformationalPriceExclTax() {
        return informationalPriceExclTax;
    }

    /**
     * Sets the value of the informationalPriceExclTax property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformationalPriceExclTax }
     *     
     */
    public void setInformationalPriceExclTax(InformationalPriceExclTax value) {
        this.informationalPriceExclTax = value;
    }

    /**
     * Gets the value of the unitNetPriceCorrection property.
     * 
     * @return
     *     possible object is
     *     {@link UnitNetPriceCorrection }
     *     
     */
    public UnitNetPriceCorrection getUnitNetPriceCorrection() {
        return unitNetPriceCorrection;
    }

    /**
     * Sets the value of the unitNetPriceCorrection property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitNetPriceCorrection }
     *     
     */
    public void setUnitNetPriceCorrection(UnitNetPriceCorrection value) {
        this.unitNetPriceCorrection = value;
    }

}
