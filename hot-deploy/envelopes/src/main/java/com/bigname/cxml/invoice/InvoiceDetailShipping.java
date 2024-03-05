
package com.bigname.cxml.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceDetailShipping complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceDetailShipping">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Contact" maxOccurs="unbounded" minOccurs="2"/>
 *         &lt;sequence minOccurs="0">
 *           &lt;element ref="{}CarrierIdentifier" maxOccurs="unbounded"/>
 *           &lt;element ref="{}ShipmentIdentifier" maxOccurs="unbounded"/>
 *         &lt;/sequence>
 *         &lt;element ref="{}DocumentReference" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="shippingDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceDetailShipping", propOrder = {
    "contact",
    "carrierIdentifier",
    "shipmentIdentifier",
    "documentReference"
})
public class InvoiceDetailShipping {

    @XmlElement(name = "Contact", required = true)
    protected List<Contact> contact;
    @XmlElement(name = "CarrierIdentifier")
    protected List<CarrierIdentifier> carrierIdentifier;
    @XmlElement(name = "ShipmentIdentifier")
    protected List<ShipmentIdentifier> shipmentIdentifier;
    @XmlElement(name = "DocumentReference")
    protected DocumentReference documentReference;
    @XmlAttribute(name = "shippingDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String shippingDate;

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
     * Gets the value of the documentReference property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentReference }
     *     
     */
    public DocumentReference getDocumentReference() {
        return documentReference;
    }

    /**
     * Sets the value of the documentReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentReference }
     *     
     */
    public void setDocumentReference(DocumentReference value) {
        this.documentReference = value;
    }

    /**
     * Gets the value of the shippingDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShippingDate() {
        return shippingDate;
    }

    /**
     * Sets the value of the shippingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShippingDate(String value) {
        this.shippingDate = value;
    }

}
