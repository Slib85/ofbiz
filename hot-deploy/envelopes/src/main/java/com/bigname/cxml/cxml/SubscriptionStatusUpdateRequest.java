
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SubscriptionStatusUpdateRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SubscriptionStatusUpdateRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}InternalID"/>
 *         &lt;element ref="{}SubscriptionVersion" minOccurs="0"/>
 *         &lt;element ref="{}SubscriptionStatus"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubscriptionStatusUpdateRequest", propOrder = {
    "internalID",
    "subscriptionVersion",
    "subscriptionStatus"
})
public class SubscriptionStatusUpdateRequest {

    @XmlElement(name = "InternalID", required = true)
    protected InternalID internalID;
    @XmlElement(name = "SubscriptionVersion")
    protected SubscriptionVersion subscriptionVersion;
    @XmlElement(name = "SubscriptionStatus", required = true)
    protected SubscriptionStatus subscriptionStatus;

    /**
     * Gets the value of the internalID property.
     * 
     * @return
     *     possible object is
     *     {@link InternalID }
     *     
     */
    public InternalID getInternalID() {
        return internalID;
    }

    /**
     * Sets the value of the internalID property.
     * 
     * @param value
     *     allowed object is
     *     {@link InternalID }
     *     
     */
    public void setInternalID(InternalID value) {
        this.internalID = value;
    }

    /**
     * Gets the value of the subscriptionVersion property.
     * 
     * @return
     *     possible object is
     *     {@link SubscriptionVersion }
     *     
     */
    public SubscriptionVersion getSubscriptionVersion() {
        return subscriptionVersion;
    }

    /**
     * Sets the value of the subscriptionVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubscriptionVersion }
     *     
     */
    public void setSubscriptionVersion(SubscriptionVersion value) {
        this.subscriptionVersion = value;
    }

    /**
     * Gets the value of the subscriptionStatus property.
     * 
     * @return
     *     possible object is
     *     {@link SubscriptionStatus }
     *     
     */
    public SubscriptionStatus getSubscriptionStatus() {
        return subscriptionStatus;
    }

    /**
     * Sets the value of the subscriptionStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubscriptionStatus }
     *     
     */
    public void setSubscriptionStatus(SubscriptionStatus value) {
        this.subscriptionStatus = value;
    }

}
