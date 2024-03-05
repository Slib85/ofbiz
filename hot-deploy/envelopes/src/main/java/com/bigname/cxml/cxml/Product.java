
package com.bigname.cxml.cxml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Product complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Product">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}SupplierPartID" minOccurs="0"/>
 *         &lt;element ref="{}SupplierPartAuxiliaryID" minOccurs="0"/>
 *         &lt;element ref="{}BuyerPartID" minOccurs="0"/>
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
@XmlType(name = "Product", propOrder = {
    "supplierPartID",
    "supplierPartAuxiliaryID",
    "buyerPartID",
    "idReference"
})
public class Product {

    @XmlElement(name = "SupplierPartID")
    protected SupplierPartID supplierPartID;
    @XmlElement(name = "SupplierPartAuxiliaryID")
    protected SupplierPartAuxiliaryID supplierPartAuxiliaryID;
    @XmlElement(name = "BuyerPartID")
    protected BuyerPartID buyerPartID;
    @XmlElement(name = "IdReference")
    protected List<IdReference> idReference;

    /**
     * Gets the value of the supplierPartID property.
     * 
     * @return
     *     possible object is
     *     {@link SupplierPartID }
     *     
     */
    public SupplierPartID getSupplierPartID() {
        return supplierPartID;
    }

    /**
     * Sets the value of the supplierPartID property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplierPartID }
     *     
     */
    public void setSupplierPartID(SupplierPartID value) {
        this.supplierPartID = value;
    }

    /**
     * Gets the value of the supplierPartAuxiliaryID property.
     * 
     * @return
     *     possible object is
     *     {@link SupplierPartAuxiliaryID }
     *     
     */
    public SupplierPartAuxiliaryID getSupplierPartAuxiliaryID() {
        return supplierPartAuxiliaryID;
    }

    /**
     * Sets the value of the supplierPartAuxiliaryID property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplierPartAuxiliaryID }
     *     
     */
    public void setSupplierPartAuxiliaryID(SupplierPartAuxiliaryID value) {
        this.supplierPartAuxiliaryID = value;
    }

    /**
     * Gets the value of the buyerPartID property.
     * 
     * @return
     *     possible object is
     *     {@link BuyerPartID }
     *     
     */
    public BuyerPartID getBuyerPartID() {
        return buyerPartID;
    }

    /**
     * Sets the value of the buyerPartID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BuyerPartID }
     *     
     */
    public void setBuyerPartID(BuyerPartID value) {
        this.buyerPartID = value;
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
