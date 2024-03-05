
package com.bigname.cxml.cxml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for CorporateInformation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CorporateInformation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}AnnualRevenue" minOccurs="0"/>
 *         &lt;element ref="{}StateOfIncorporation" minOccurs="0"/>
 *         &lt;element ref="{}OwnershipType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="yearFounded" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="numberOfEmployees" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="organizationType" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CorporateInformation", propOrder = {
    "annualRevenue",
    "stateOfIncorporation",
    "ownershipType"
})
public class CorporateInformation {

    @XmlElement(name = "AnnualRevenue")
    protected AnnualRevenue annualRevenue;
    @XmlElement(name = "StateOfIncorporation")
    protected StateOfIncorporation stateOfIncorporation;
    @XmlElement(name = "OwnershipType")
    protected List<OwnershipType> ownershipType;
    @XmlAttribute(name = "yearFounded")
    @XmlSchemaType(name = "anySimpleType")
    protected String yearFounded;
    @XmlAttribute(name = "numberOfEmployees")
    @XmlSchemaType(name = "anySimpleType")
    protected String numberOfEmployees;
    @XmlAttribute(name = "organizationType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String organizationType;

    /**
     * Gets the value of the annualRevenue property.
     * 
     * @return
     *     possible object is
     *     {@link AnnualRevenue }
     *     
     */
    public AnnualRevenue getAnnualRevenue() {
        return annualRevenue;
    }

    /**
     * Sets the value of the annualRevenue property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnnualRevenue }
     *     
     */
    public void setAnnualRevenue(AnnualRevenue value) {
        this.annualRevenue = value;
    }

    /**
     * Gets the value of the stateOfIncorporation property.
     * 
     * @return
     *     possible object is
     *     {@link StateOfIncorporation }
     *     
     */
    public StateOfIncorporation getStateOfIncorporation() {
        return stateOfIncorporation;
    }

    /**
     * Sets the value of the stateOfIncorporation property.
     * 
     * @param value
     *     allowed object is
     *     {@link StateOfIncorporation }
     *     
     */
    public void setStateOfIncorporation(StateOfIncorporation value) {
        this.stateOfIncorporation = value;
    }

    /**
     * Gets the value of the ownershipType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ownershipType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOwnershipType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OwnershipType }
     * 
     * 
     */
    public List<OwnershipType> getOwnershipType() {
        if (ownershipType == null) {
            ownershipType = new ArrayList<OwnershipType>();
        }
        return this.ownershipType;
    }

    /**
     * Gets the value of the yearFounded property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYearFounded() {
        return yearFounded;
    }

    /**
     * Sets the value of the yearFounded property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYearFounded(String value) {
        this.yearFounded = value;
    }

    /**
     * Gets the value of the numberOfEmployees property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberOfEmployees() {
        return numberOfEmployees;
    }

    /**
     * Sets the value of the numberOfEmployees property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberOfEmployees(String value) {
        this.numberOfEmployees = value;
    }

    /**
     * Gets the value of the organizationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationType() {
        return organizationType;
    }

    /**
     * Sets the value of the organizationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationType(String value) {
        this.organizationType = value;
    }

}
