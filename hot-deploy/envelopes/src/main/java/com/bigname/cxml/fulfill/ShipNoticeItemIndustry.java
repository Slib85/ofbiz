
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ShipNoticeItemIndustry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipNoticeItemIndustry">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ShipNoticeItemRetail" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipNoticeItemIndustry", propOrder = {
    "shipNoticeItemRetail"
})
public class ShipNoticeItemIndustry {

    @XmlElement(name = "ShipNoticeItemRetail")
    protected ShipNoticeItemRetail shipNoticeItemRetail;

    /**
     * Gets the value of the shipNoticeItemRetail property.
     * 
     * @return
     *     possible object is
     *     {@link ShipNoticeItemRetail }
     *     
     */
    public ShipNoticeItemRetail getShipNoticeItemRetail() {
        return shipNoticeItemRetail;
    }

    /**
     * Sets the value of the shipNoticeItemRetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipNoticeItemRetail }
     *     
     */
    public void setShipNoticeItemRetail(ShipNoticeItemRetail value) {
        this.shipNoticeItemRetail = value;
    }

}
