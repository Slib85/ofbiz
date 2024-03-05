
package com.bigname.cxml.fulfill;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResourceID complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResourceID">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}SupplierResourceID"/>
 *         &lt;element ref="{}ResourceType" minOccurs="0"/>
 *         &lt;element ref="{}InputItemID" minOccurs="0"/>
 *         &lt;element ref="{}OutputItemID" minOccurs="0"/>
 *         &lt;element ref="{}IdReference" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResourceID", propOrder = {
    "supplierResourceID",
    "resourceType",
    "inputItemID",
    "outputItemID",
    "idReference"
})
public class ResourceID {

    @XmlElement(name = "SupplierResourceID", required = true)
    protected SupplierResourceID supplierResourceID;
    @XmlElement(name = "ResourceType")
    protected ResourceType resourceType;
    @XmlElement(name = "InputItemID")
    protected InputItemID inputItemID;
    @XmlElement(name = "OutputItemID")
    protected OutputItemID outputItemID;
    @XmlElement(name = "IdReference")
    protected List<IdReference> idReference;

    /**
     * Gets the value of the supplierResourceID property.
     * 
     * @return
     *     possible object is
     *     {@link SupplierResourceID }
     *     
     */
    public SupplierResourceID getSupplierResourceID() {
        return supplierResourceID;
    }

    /**
     * Sets the value of the supplierResourceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplierResourceID }
     *     
     */
    public void setSupplierResourceID(SupplierResourceID value) {
        this.supplierResourceID = value;
    }

    /**
     * Gets the value of the resourceType property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceType }
     *     
     */
    public ResourceType getResourceType() {
        return resourceType;
    }

    /**
     * Sets the value of the resourceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceType }
     *     
     */
    public void setResourceType(ResourceType value) {
        this.resourceType = value;
    }

    /**
     * Gets the value of the inputItemID property.
     * 
     * @return
     *     possible object is
     *     {@link InputItemID }
     *     
     */
    public InputItemID getInputItemID() {
        return inputItemID;
    }

    /**
     * Sets the value of the inputItemID property.
     * 
     * @param value
     *     allowed object is
     *     {@link InputItemID }
     *     
     */
    public void setInputItemID(InputItemID value) {
        this.inputItemID = value;
    }

    /**
     * Gets the value of the outputItemID property.
     * 
     * @return
     *     possible object is
     *     {@link OutputItemID }
     *     
     */
    public OutputItemID getOutputItemID() {
        return outputItemID;
    }

    /**
     * Sets the value of the outputItemID property.
     * 
     * @param value
     *     allowed object is
     *     {@link OutputItemID }
     *     
     */
    public void setOutputItemID(OutputItemID value) {
        this.outputItemID = value;
    }

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

}
