
package com.bigname.cxml.cxml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValueGroup complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValueGroup">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}IdReference" minOccurs="0"/>
 *         &lt;element ref="{}ParentID" minOccurs="0"/>
 *         &lt;element ref="{}PropertyValue" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValueGroup", propOrder = {
    "idReference",
    "parentID",
    "propertyValue"
})
public class ValueGroup {

    @XmlElement(name = "IdReference")
    protected IdReference idReference;
    @XmlElement(name = "ParentID")
    protected ParentID parentID;
    @XmlElement(name = "PropertyValue")
    protected List<PropertyValue> propertyValue;

    /**
     * Gets the value of the idReference property.
     * 
     * @return
     *     possible object is
     *     {@link IdReference }
     *     
     */
    public IdReference getIdReference() {
        return idReference;
    }

    /**
     * Sets the value of the idReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdReference }
     *     
     */
    public void setIdReference(IdReference value) {
        this.idReference = value;
    }

    /**
     * Gets the value of the parentID property.
     * 
     * @return
     *     possible object is
     *     {@link ParentID }
     *     
     */
    public ParentID getParentID() {
        return parentID;
    }

    /**
     * Sets the value of the parentID property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParentID }
     *     
     */
    public void setParentID(ParentID value) {
        this.parentID = value;
    }

    /**
     * Gets the value of the propertyValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the propertyValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPropertyValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PropertyValue }
     * 
     * 
     */
    public List<PropertyValue> getPropertyValue() {
        if (propertyValue == null) {
            propertyValue = new ArrayList<PropertyValue>();
        }
        return this.propertyValue;
    }

}
