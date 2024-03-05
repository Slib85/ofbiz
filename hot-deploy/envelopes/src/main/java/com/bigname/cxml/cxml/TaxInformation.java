
package com.bigname.cxml.cxml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for TaxInformation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxInformation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}LegalName" minOccurs="0"/>
 *         &lt;element ref="{}TaxID" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="isExemptFromBackupWithholding">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="no"/>
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxInformation", propOrder = {
    "legalName",
    "taxID"
})
public class TaxInformation {

    @XmlElement(name = "LegalName")
    protected LegalName legalName;
    @XmlElement(name = "TaxID")
    protected List<TaxID> taxID;
    @XmlAttribute(name = "isExemptFromBackupWithholding")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isExemptFromBackupWithholding;

    /**
     * Gets the value of the legalName property.
     * 
     * @return
     *     possible object is
     *     {@link LegalName }
     *     
     */
    public LegalName getLegalName() {
        return legalName;
    }

    /**
     * Sets the value of the legalName property.
     * 
     * @param value
     *     allowed object is
     *     {@link LegalName }
     *     
     */
    public void setLegalName(LegalName value) {
        this.legalName = value;
    }

    /**
     * Gets the value of the taxID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the taxID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTaxID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TaxID }
     * 
     * 
     */
    public List<TaxID> getTaxID() {
        if (taxID == null) {
            taxID = new ArrayList<TaxID>();
        }
        return this.taxID;
    }

    /**
     * Gets the value of the isExemptFromBackupWithholding property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsExemptFromBackupWithholding() {
        return isExemptFromBackupWithholding;
    }

    /**
     * Sets the value of the isExemptFromBackupWithholding property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsExemptFromBackupWithholding(String value) {
        this.isExemptFromBackupWithholding = value;
    }

}
