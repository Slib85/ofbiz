
package com.bigname.cxml.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceDetailItemReferenceRetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceDetailItemReferenceRetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}EANID" minOccurs="0"/>
 *         &lt;element ref="{}EuropeanWasteCatalogID" minOccurs="0"/>
 *         &lt;element ref="{}Characteristic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceDetailItemReferenceRetail", propOrder = {
    "eanid",
    "europeanWasteCatalogID",
    "characteristic"
})
public class InvoiceDetailItemReferenceRetail {

    @XmlElement(name = "EANID")
    protected EANID eanid;
    @XmlElement(name = "EuropeanWasteCatalogID")
    protected EuropeanWasteCatalogID europeanWasteCatalogID;
    @XmlElement(name = "Characteristic")
    protected List<Characteristic> characteristic;

    /**
     * Gets the value of the eanid property.
     * 
     * @return
     *     possible object is
     *     {@link EANID }
     *     
     */
    public EANID getEANID() {
        return eanid;
    }

    /**
     * Sets the value of the eanid property.
     * 
     * @param value
     *     allowed object is
     *     {@link EANID }
     *     
     */
    public void setEANID(EANID value) {
        this.eanid = value;
    }

    /**
     * Gets the value of the europeanWasteCatalogID property.
     * 
     * @return
     *     possible object is
     *     {@link EuropeanWasteCatalogID }
     *     
     */
    public EuropeanWasteCatalogID getEuropeanWasteCatalogID() {
        return europeanWasteCatalogID;
    }

    /**
     * Sets the value of the europeanWasteCatalogID property.
     * 
     * @param value
     *     allowed object is
     *     {@link EuropeanWasteCatalogID }
     *     
     */
    public void setEuropeanWasteCatalogID(EuropeanWasteCatalogID value) {
        this.europeanWasteCatalogID = value;
    }

    /**
     * Gets the value of the characteristic property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the characteristic property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCharacteristic().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Characteristic }
     * 
     * 
     */
    public List<Characteristic> getCharacteristic() {
        if (characteristic == null) {
            characteristic = new ArrayList<Characteristic>();
        }
        return this.characteristic;
    }

}
