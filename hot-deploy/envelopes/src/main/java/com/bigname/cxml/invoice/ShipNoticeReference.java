
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ShipNoticeReference complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipNoticeReference">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}DocumentReference"/>
 *       &lt;/sequence>
 *       &lt;attribute name="shipNoticeID" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="shipNoticeDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipNoticeReference", propOrder = {
    "documentReference"
})
public class ShipNoticeReference {

    @XmlElement(name = "DocumentReference", required = true)
    protected DocumentReference documentReference;
    @XmlAttribute(name = "shipNoticeID")
    @XmlSchemaType(name = "anySimpleType")
    protected String shipNoticeID;
    @XmlAttribute(name = "shipNoticeDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String shipNoticeDate;

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
     * Gets the value of the shipNoticeID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipNoticeID() {
        return shipNoticeID;
    }

    /**
     * Sets the value of the shipNoticeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipNoticeID(String value) {
        this.shipNoticeID = value;
    }

    /**
     * Gets the value of the shipNoticeDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipNoticeDate() {
        return shipNoticeDate;
    }

    /**
     * Sets the value of the shipNoticeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipNoticeDate(String value) {
        this.shipNoticeDate = value;
    }

}
