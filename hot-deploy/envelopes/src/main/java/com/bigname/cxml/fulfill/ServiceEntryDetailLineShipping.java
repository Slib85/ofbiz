
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceEntryDetailLineShipping complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceEntryDetailLineShipping">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ServiceEntryDetailShipping"/>
 *         &lt;element ref="{}Money"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceEntryDetailLineShipping", propOrder = {
    "serviceEntryDetailShipping",
    "money"
})
public class ServiceEntryDetailLineShipping {

    @XmlElement(name = "ServiceEntryDetailShipping", required = true)
    protected ServiceEntryDetailShipping serviceEntryDetailShipping;
    @XmlElement(name = "Money", required = true)
    protected Money money;

    /**
     * Gets the value of the serviceEntryDetailShipping property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEntryDetailShipping }
     *     
     */
    public ServiceEntryDetailShipping getServiceEntryDetailShipping() {
        return serviceEntryDetailShipping;
    }

    /**
     * Sets the value of the serviceEntryDetailShipping property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEntryDetailShipping }
     *     
     */
    public void setServiceEntryDetailShipping(ServiceEntryDetailShipping value) {
        this.serviceEntryDetailShipping = value;
    }

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

}
