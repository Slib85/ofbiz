
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ShipNoticeReleaseInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipNoticeReleaseInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element ref="{}ShipNoticeReference"/>
 *           &lt;element ref="{}ShipNoticeIDInfo"/>
 *         &lt;/choice>
 *         &lt;element ref="{}UnitOfMeasure"/>
 *       &lt;/sequence>
 *       &lt;attribute name="receivedQuantity" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipNoticeReleaseInfo", propOrder = {
    "shipNoticeReference",
    "shipNoticeIDInfo",
    "unitOfMeasure"
})
public class ShipNoticeReleaseInfo {

    @XmlElement(name = "ShipNoticeReference")
    protected ShipNoticeReference shipNoticeReference;
    @XmlElement(name = "ShipNoticeIDInfo")
    protected ShipNoticeIDInfo shipNoticeIDInfo;
    @XmlElement(name = "UnitOfMeasure", required = true)
    protected UnitOfMeasure unitOfMeasure;
    @XmlAttribute(name = "receivedQuantity", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String receivedQuantity;

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

    /**
     * Gets the value of the unitOfMeasure property.
     * 
     * @return
     *     possible object is
     *     {@link UnitOfMeasure }
     *     
     */
    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    /**
     * Sets the value of the unitOfMeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitOfMeasure }
     *     
     */
    public void setUnitOfMeasure(UnitOfMeasure value) {
        this.unitOfMeasure = value;
    }

    /**
     * Gets the value of the receivedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceivedQuantity() {
        return receivedQuantity;
    }

    /**
     * Sets the value of the receivedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceivedQuantity(String value) {
        this.receivedQuantity = value;
    }

}
