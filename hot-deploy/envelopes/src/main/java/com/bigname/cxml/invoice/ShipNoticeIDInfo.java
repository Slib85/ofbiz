
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
 * <p>Java class for ShipNoticeIDInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipNoticeIDInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}IdReference" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="shipNoticeID" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="shipNoticeDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipNoticeIDInfo", propOrder = {
    "idReference"
})
public class ShipNoticeIDInfo {

    @XmlElement(name = "IdReference")
    protected List<IdReference> idReference;
    @XmlAttribute(name = "shipNoticeID", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String shipNoticeID;
    @XmlAttribute(name = "shipNoticeDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String shipNoticeDate;

    /**
     * Gets the value of the idReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the idReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IdReference }
     * 
     * 
     */
    public List<IdReference> getIdReference() {
        if (idReference == null) {
            idReference = new ArrayList<IdReference>();
        }
        return this.idReference;
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
