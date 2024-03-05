
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
 * <p>Java class for SupplierSelector complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SupplierSelector">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}SupplierInvitation" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="matchingType">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="public"/>
 *             &lt;enumeration value="invitationOnly"/>
 *             &lt;enumeration value="approvedVendorOnly"/>
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
@XmlType(name = "SupplierSelector", propOrder = {
    "supplierInvitation"
})
public class SupplierSelector {

    @XmlElement(name = "SupplierInvitation")
    protected List<SupplierInvitation> supplierInvitation;
    @XmlAttribute(name = "matchingType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String matchingType;

    /**
     * Gets the value of the supplierInvitation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supplierInvitation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupplierInvitation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SupplierInvitation }
     * 
     * 
     */
    public List<SupplierInvitation> getSupplierInvitation() {
        if (supplierInvitation == null) {
            supplierInvitation = new ArrayList<SupplierInvitation>();
        }
        return this.supplierInvitation;
    }

    /**
     * Gets the value of the matchingType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatchingType() {
        return matchingType;
    }

    /**
     * Sets the value of the matchingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatchingType(String value) {
        this.matchingType = value;
    }

}
