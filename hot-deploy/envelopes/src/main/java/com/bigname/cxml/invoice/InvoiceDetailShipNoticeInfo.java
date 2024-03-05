
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceDetailShipNoticeInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceDetailShipNoticeInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{}ShipNoticeReference"/>
 *         &lt;element ref="{}ShipNoticeIDInfo"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceDetailShipNoticeInfo", propOrder = {
    "shipNoticeReference",
    "shipNoticeIDInfo"
})
public class InvoiceDetailShipNoticeInfo {

    @XmlElement(name = "ShipNoticeReference")
    protected ShipNoticeReference shipNoticeReference;
    @XmlElement(name = "ShipNoticeIDInfo")
    protected ShipNoticeIDInfo shipNoticeIDInfo;

    /**
     * Gets the value of the shipNoticeReference property.
     * 
     * @return
     *     possible object is
     *     {@link ShipNoticeReference }
     *     
     */
    public ShipNoticeReference getShipNoticeReference() {
        return shipNoticeReference;
    }

    /**
     * Sets the value of the shipNoticeReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipNoticeReference }
     *     
     */
    public void setShipNoticeReference(ShipNoticeReference value) {
        this.shipNoticeReference = value;
    }

    /**
     * Gets the value of the shipNoticeIDInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ShipNoticeIDInfo }
     *     
     */
    public ShipNoticeIDInfo getShipNoticeIDInfo() {
        return shipNoticeIDInfo;
    }

    /**
     * Sets the value of the shipNoticeIDInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipNoticeIDInfo }
     *     
     */
    public void setShipNoticeIDInfo(ShipNoticeIDInfo value) {
        this.shipNoticeIDInfo = value;
    }

}
