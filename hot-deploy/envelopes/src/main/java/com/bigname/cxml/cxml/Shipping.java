
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Shipping complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Shipping">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Money"/>
 *         &lt;element ref="{}Description"/>
 *         &lt;element ref="{}Modifications" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="trackingDomain" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="trackingId" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="tracking" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Shipping", propOrder = {
    "money",
    "description",
    "modifications"
})
public class Shipping {

    @XmlElement(name = "Money", required = true)
    protected Money money;
    @XmlElement(name = "Description", required = true)
    protected Description description;
    @XmlElement(name = "Modifications")
    protected Modifications modifications;
    @XmlAttribute(name = "trackingDomain")
    @XmlSchemaType(name = "anySimpleType")
    protected String trackingDomain;
    @XmlAttribute(name = "trackingId")
    @XmlSchemaType(name = "anySimpleType")
    protected String trackingId;
    @XmlAttribute(name = "tracking")
    @XmlSchemaType(name = "anySimpleType")
    protected String tracking;

    /**
     * Gets the value of the money property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getMoney() {
        return money;
    }

    /**
     * Sets the value of the money property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setMoney(Money value) {
        this.money = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link Description }
     *     
     */
    public Description getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link Description }
     *     
     */
    public void setDescription(Description value) {
        this.description = value;
    }

    /**
     * Gets the value of the modifications property.
     * 
     * @return
     *     possible object is
     *     {@link Modifications }
     *     
     */
    public Modifications getModifications() {
        return modifications;
    }

    /**
     * Sets the value of the modifications property.
     * 
     * @param value
     *     allowed object is
     *     {@link Modifications }
     *     
     */
    public void setModifications(Modifications value) {
        this.modifications = value;
    }

    /**
     * Gets the value of the trackingDomain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrackingDomain() {
        return trackingDomain;
    }

    /**
     * Sets the value of the trackingDomain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrackingDomain(String value) {
        this.trackingDomain = value;
    }

    /**
     * Gets the value of the trackingId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrackingId() {
        return trackingId;
    }

    /**
     * Sets the value of the trackingId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrackingId(String value) {
        this.trackingId = value;
    }

    /**
     * Gets the value of the tracking property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTracking() {
        return tracking;
    }

    /**
     * Sets the value of the tracking property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTracking(String value) {
        this.tracking = value;
    }

}
