
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TelephoneNumber complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TelephoneNumber">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}CountryCode"/>
 *         &lt;element ref="{}AreaOrCityCode"/>
 *         &lt;element ref="{}Number"/>
 *         &lt;element ref="{}Extension" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TelephoneNumber", propOrder = {
    "countryCode",
    "areaOrCityCode",
    "number",
    "extension"
})
public class TelephoneNumber {

    @XmlElement(name = "CountryCode", required = true)
    protected CountryCode countryCode;
    @XmlElement(name = "AreaOrCityCode", required = true)
    protected AreaOrCityCode areaOrCityCode;
    @XmlElement(name = "Number", required = true)
    protected Number number;
    @XmlElement(name = "Extension")
    protected Extension extension;

    /**
     * Gets the value of the countryCode property.
     * 
     * @return
     *     possible object is
     *     {@link CountryCode }
     *     
     */
    public CountryCode getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the value of the countryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CountryCode }
     *     
     */
    public void setCountryCode(CountryCode value) {
        this.countryCode = value;
    }

    /**
     * Gets the value of the areaOrCityCode property.
     * 
     * @return
     *     possible object is
     *     {@link AreaOrCityCode }
     *     
     */
    public AreaOrCityCode getAreaOrCityCode() {
        return areaOrCityCode;
    }

    /**
     * Sets the value of the areaOrCityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link AreaOrCityCode }
     *     
     */
    public void setAreaOrCityCode(AreaOrCityCode value) {
        this.areaOrCityCode = value;
    }

    /**
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link Number }
     *     
     */
    public Number getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link Number }
     *     
     */
    public void setNumber(Number value) {
        this.number = value;
    }

    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link Extension }
     *     
     */
    public Extension getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link Extension }
     *     
     */
    public void setExtension(Extension value) {
        this.extension = value;
    }

}
