
package com.bigname.cxml.fulfill;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ShipControl complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipControl">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}CarrierIdentifier" maxOccurs="unbounded"/>
 *         &lt;element ref="{}ShipmentIdentifier" maxOccurs="unbounded"/>
 *         &lt;element ref="{}PackageIdentification" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element ref="{}Route"/>
 *           &lt;element ref="{}TransportInformation"/>
 *         &lt;/choice>
 *         &lt;element ref="{}Contact" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Comments" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="startDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipControl", propOrder = {
    "carrierIdentifier",
    "shipmentIdentifier",
    "packageIdentification",
    "routeOrTransportInformation",
    "contact",
    "comments",
    "extrinsic"
})
public class ShipControl {

    @XmlElement(name = "CarrierIdentifier", required = true)
    protected List<CarrierIdentifier> carrierIdentifier;
    @XmlElement(name = "ShipmentIdentifier", required = true)
    protected List<ShipmentIdentifier> shipmentIdentifier;
    @XmlElement(name = "PackageIdentification")
    protected PackageIdentification packageIdentification;
    @XmlElements({
        @XmlElement(name = "Route", type = Route.class),
        @XmlElement(name = "TransportInformation", type = TransportInformation.class)
    })
    protected List<Object> routeOrTransportInformation;
    @XmlElement(name = "Contact")
    protected List<Contact> contact;
    @XmlElement(name = "Comments")
    protected List<Comments> comments;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(name = "startDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String startDate;

    /**
     * Gets the value of the carrierIdentifier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the carrierIdentifier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCarrierIdentifier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CarrierIdentifier }
     * 
     * 
     */
    public List<CarrierIdentifier> getCarrierIdentifier() {
        if (carrierIdentifier == null) {
            carrierIdentifier = new ArrayList<CarrierIdentifier>();
        }
        return this.carrierIdentifier;
    }

    /**
     * Gets the value of the shipmentIdentifier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the shipmentIdentifier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getShipmentIdentifier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ShipmentIdentifier }
     * 
     * 
     */
    public List<ShipmentIdentifier> getShipmentIdentifier() {
        if (shipmentIdentifier == null) {
            shipmentIdentifier = new ArrayList<ShipmentIdentifier>();
        }
        return this.shipmentIdentifier;
    }

    /**
     * Gets the value of the packageIdentification property.
     * 
     * @return
     *     possible object is
     *     {@link PackageIdentification }
     *     
     */
    public PackageIdentification getPackageIdentification() {
        return packageIdentification;
    }

    /**
     * Sets the value of the packageIdentification property.
     * 
     * @param value
     *     allowed object is
     *     {@link PackageIdentification }
     *     
     */
    public void setPackageIdentification(PackageIdentification value) {
        this.packageIdentification = value;
    }

    /**
     * Gets the value of the routeOrTransportInformation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the routeOrTransportInformation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRouteOrTransportInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Route }
     * {@link TransportInformation }
     * 
     * 
     */
    public List<Object> getRouteOrTransportInformation() {
        if (routeOrTransportInformation == null) {
            routeOrTransportInformation = new ArrayList<Object>();
        }
        return this.routeOrTransportInformation;
    }

    /**
     * Gets the value of the contact property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contact property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContact().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Contact }
     * 
     * 
     */
    public List<Contact> getContact() {
        if (contact == null) {
            contact = new ArrayList<Contact>();
        }
        return this.contact;
    }

    /**
     * Gets the value of the comments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the comments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Comments }
     * 
     * 
     */
    public List<Comments> getComments() {
        if (comments == null) {
            comments = new ArrayList<Comments>();
        }
        return this.comments;
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
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartDate(String value) {
        this.startDate = value;
    }

}
