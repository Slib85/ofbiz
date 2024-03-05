
package com.bigname.cxml.fulfill;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ShipNoticeRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipNoticeRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ShipNoticeHeader"/>
 *         &lt;element ref="{}ShipControl" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}ShipNoticePortion" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipNoticeRequest", propOrder = {
    "shipNoticeHeader",
    "shipControl",
    "shipNoticePortion"
})
public class ShipNoticeRequest {

    @XmlElement(name = "ShipNoticeHeader", required = true)
    protected ShipNoticeHeader shipNoticeHeader;
    @XmlElement(name = "ShipControl")
    protected List<ShipControl> shipControl;
    @XmlElement(name = "ShipNoticePortion")
    protected List<ShipNoticePortion> shipNoticePortion;

    /**
     * Gets the value of the shipNoticeHeader property.
     * 
     * @return
     *     possible object is
     *     {@link ShipNoticeHeader }
     *     
     */
    public ShipNoticeHeader getShipNoticeHeader() {
        return shipNoticeHeader;
    }

    /**
     * Sets the value of the shipNoticeHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipNoticeHeader }
     *     
     */
    public void setShipNoticeHeader(ShipNoticeHeader value) {
        this.shipNoticeHeader = value;
    }

    /**
     * Gets the value of the shipControl property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the shipControl property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getShipControl().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ShipControl }
     * 
     * 
     */
    public List<ShipControl> getShipControl() {
        if (shipControl == null) {
            shipControl = new ArrayList<ShipControl>();
        }
        return this.shipControl;
    }

    /**
     * Gets the value of the shipNoticePortion property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the shipNoticePortion property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getShipNoticePortion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ShipNoticePortion }
     * 
     * 
     */
    public List<ShipNoticePortion> getShipNoticePortion() {
        if (shipNoticePortion == null) {
            shipNoticePortion = new ArrayList<ShipNoticePortion>();
        }
        return this.shipNoticePortion;
    }

}
