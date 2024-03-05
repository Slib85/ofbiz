
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceEntryRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceEntryRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ServiceEntryRequestHeader"/>
 *         &lt;element ref="{}ServiceEntryOrder"/>
 *         &lt;element ref="{}ServiceEntrySummary"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceEntryRequest", propOrder = {
    "serviceEntryRequestHeader",
    "serviceEntryOrder",
    "serviceEntrySummary"
})
public class ServiceEntryRequest {

    @XmlElement(name = "ServiceEntryRequestHeader", required = true)
    protected ServiceEntryRequestHeader serviceEntryRequestHeader;
    @XmlElement(name = "ServiceEntryOrder", required = true)
    protected ServiceEntryOrder serviceEntryOrder;
    @XmlElement(name = "ServiceEntrySummary", required = true)
    protected ServiceEntrySummary serviceEntrySummary;

    /**
     * Gets the value of the serviceEntryRequestHeader property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEntryRequestHeader }
     *     
     */
    public ServiceEntryRequestHeader getServiceEntryRequestHeader() {
        return serviceEntryRequestHeader;
    }

    /**
     * Sets the value of the serviceEntryRequestHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEntryRequestHeader }
     *     
     */
    public void setServiceEntryRequestHeader(ServiceEntryRequestHeader value) {
        this.serviceEntryRequestHeader = value;
    }

    /**
     * Gets the value of the serviceEntryOrder property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEntryOrder }
     *     
     */
    public ServiceEntryOrder getServiceEntryOrder() {
        return serviceEntryOrder;
    }

    /**
     * Sets the value of the serviceEntryOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEntryOrder }
     *     
     */
    public void setServiceEntryOrder(ServiceEntryOrder value) {
        this.serviceEntryOrder = value;
    }

    /**
     * Gets the value of the serviceEntrySummary property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEntrySummary }
     *     
     */
    public ServiceEntrySummary getServiceEntrySummary() {
        return serviceEntrySummary;
    }

    /**
     * Sets the value of the serviceEntrySummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEntrySummary }
     *     
     */
    public void setServiceEntrySummary(ServiceEntrySummary value) {
        this.serviceEntrySummary = value;
    }

}
