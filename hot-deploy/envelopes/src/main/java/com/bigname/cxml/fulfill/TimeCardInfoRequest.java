
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TimeCardInfoRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TimeCardInfoRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}TimeCard"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeCardInfoRequest", propOrder = {
    "timeCard"
})
public class TimeCardInfoRequest {

    @XmlElement(name = "TimeCard", required = true)
    protected TimeCard timeCard;

    /**
     * Gets the value of the timeCard property.
     * 
     * @return
     *     possible object is
     *     {@link TimeCard }
     *     
     */
    public TimeCard getTimeCard() {
        return timeCard;
    }

    /**
     * Sets the value of the timeCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeCard }
     *     
     */
    public void setTimeCard(TimeCard value) {
        this.timeCard = value;
    }

}
