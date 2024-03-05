
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for ShipmentIdentifier complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipmentIdentifier">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="domain" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="trackingNumberDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="trackingURL" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipmentIdentifier", propOrder = {
    "content"
})
public class ShipmentIdentifier {

    @XmlValue
    protected String content;
    @XmlAttribute(name = "domain")
    @XmlSchemaType(name = "anySimpleType")
    protected String domain;
    @XmlAttribute(name = "trackingNumberDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String trackingNumberDate;
    @XmlAttribute(name = "trackingURL")
    @XmlSchemaType(name = "anySimpleType")
    protected String trackingURL;

    /**
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * Gets the value of the domain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Sets the value of the domain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomain(String value) {
        this.domain = value;
    }

    /**
     * Gets the value of the trackingNumberDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrackingNumberDate() {
        return trackingNumberDate;
    }

    /**
     * Sets the value of the trackingNumberDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrackingNumberDate(String value) {
        this.trackingNumberDate = value;
    }

    /**
     * Gets the value of the trackingURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrackingURL() {
        return trackingURL;
    }

    /**
     * Sets the value of the trackingURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrackingURL(String value) {
        this.trackingURL = value;
    }

}
