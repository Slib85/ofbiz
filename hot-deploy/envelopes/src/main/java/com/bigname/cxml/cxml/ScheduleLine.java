
package com.bigname.cxml.cxml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ScheduleLine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ScheduleLine">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}UnitOfMeasure"/>
 *         &lt;element ref="{}ScheduleLineReleaseInfo" minOccurs="0"/>
 *         &lt;element ref="{}SubcontractingComponent" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}ShipTo" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="quantity" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="requestedDeliveryDate" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="lineNumber" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="deliveryWindow" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="requestedShipmentDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="originalRequestedDeliveryDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleLine", propOrder = {
    "unitOfMeasure",
    "scheduleLineReleaseInfo",
    "subcontractingComponent",
    "shipTo",
    "extrinsic"
})
public class ScheduleLine {

    @XmlElement(name = "UnitOfMeasure", required = true)
    protected UnitOfMeasure unitOfMeasure;
    @XmlElement(name = "ScheduleLineReleaseInfo")
    protected ScheduleLineReleaseInfo scheduleLineReleaseInfo;
    @XmlElement(name = "SubcontractingComponent")
    protected List<SubcontractingComponent> subcontractingComponent;
    @XmlElement(name = "ShipTo")
    protected ShipTo shipTo;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(name = "quantity", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String quantity;
    @XmlAttribute(name = "requestedDeliveryDate", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String requestedDeliveryDate;
    @XmlAttribute(name = "lineNumber")
    @XmlSchemaType(name = "anySimpleType")
    protected String lineNumber;
    @XmlAttribute(name = "deliveryWindow")
    @XmlSchemaType(name = "anySimpleType")
    protected String deliveryWindow;
    @XmlAttribute(name = "requestedShipmentDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String requestedShipmentDate;
    @XmlAttribute(name = "originalRequestedDeliveryDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String originalRequestedDeliveryDate;

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
     * Gets the value of the scheduleLineReleaseInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ScheduleLineReleaseInfo }
     *     
     */
    public ScheduleLineReleaseInfo getScheduleLineReleaseInfo() {
        return scheduleLineReleaseInfo;
    }

    /**
     * Sets the value of the scheduleLineReleaseInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScheduleLineReleaseInfo }
     *     
     */
    public void setScheduleLineReleaseInfo(ScheduleLineReleaseInfo value) {
        this.scheduleLineReleaseInfo = value;
    }

    /**
     * Gets the value of the subcontractingComponent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subcontractingComponent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubcontractingComponent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubcontractingComponent }
     * 
     * 
     */
    public List<SubcontractingComponent> getSubcontractingComponent() {
        if (subcontractingComponent == null) {
            subcontractingComponent = new ArrayList<SubcontractingComponent>();
        }
        return this.subcontractingComponent;
    }

    /**
     * Gets the value of the shipTo property.
     * 
     * @return
     *     possible object is
     *     {@link ShipTo }
     *     
     */
    public ShipTo getShipTo() {
        return shipTo;
    }

    /**
     * Sets the value of the shipTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipTo }
     *     
     */
    public void setShipTo(ShipTo value) {
        this.shipTo = value;
    }

    /**
     * Gets the value of the extrinsic property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extrinsic property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtrinsic().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Extrinsic }
     * 
     * 
     */
    public List<Extrinsic> getExtrinsic() {
        if (extrinsic == null) {
            extrinsic = new ArrayList<Extrinsic>();
        }
        return this.extrinsic;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuantity(String value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the requestedDeliveryDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestedDeliveryDate() {
        return requestedDeliveryDate;
    }

    /**
     * Sets the value of the requestedDeliveryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestedDeliveryDate(String value) {
        this.requestedDeliveryDate = value;
    }

    /**
     * Gets the value of the lineNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLineNumber() {
        return lineNumber;
    }

    /**
     * Sets the value of the lineNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineNumber(String value) {
        this.lineNumber = value;
    }

    /**
     * Gets the value of the deliveryWindow property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryWindow() {
        return deliveryWindow;
    }

    /**
     * Sets the value of the deliveryWindow property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryWindow(String value) {
        this.deliveryWindow = value;
    }

    /**
     * Gets the value of the requestedShipmentDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestedShipmentDate() {
        return requestedShipmentDate;
    }

    /**
     * Sets the value of the requestedShipmentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestedShipmentDate(String value) {
        this.requestedShipmentDate = value;
    }

    /**
     * Gets the value of the originalRequestedDeliveryDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginalRequestedDeliveryDate() {
        return originalRequestedDeliveryDate;
    }

    /**
     * Sets the value of the originalRequestedDeliveryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginalRequestedDeliveryDate(String value) {
        this.originalRequestedDeliveryDate = value;
    }

}
