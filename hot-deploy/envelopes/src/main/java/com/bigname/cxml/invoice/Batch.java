
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
 * <p>Java class for Batch complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Batch">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}BuyerBatchID" minOccurs="0"/>
 *         &lt;element ref="{}SupplierBatchID" minOccurs="0"/>
 *         &lt;element ref="{}PropertyValuation" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="productionDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="expirationDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="inspectionDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="shelfLife" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="originCountryCode" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="batchQuantity" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Batch", propOrder = {
    "buyerBatchID",
    "supplierBatchID",
    "propertyValuation"
})
public class Batch {

    @XmlElement(name = "BuyerBatchID")
    protected BuyerBatchID buyerBatchID;
    @XmlElement(name = "SupplierBatchID")
    protected SupplierBatchID supplierBatchID;
    @XmlElement(name = "PropertyValuation")
    protected List<PropertyValuation> propertyValuation;
    @XmlAttribute(name = "productionDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String productionDate;
    @XmlAttribute(name = "expirationDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String expirationDate;
    @XmlAttribute(name = "inspectionDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String inspectionDate;
    @XmlAttribute(name = "shelfLife")
    @XmlSchemaType(name = "anySimpleType")
    protected String shelfLife;
    @XmlAttribute(name = "originCountryCode")
    @XmlSchemaType(name = "anySimpleType")
    protected String originCountryCode;
    @XmlAttribute(name = "batchQuantity")
    @XmlSchemaType(name = "anySimpleType")
    protected String batchQuantity;

    /**
     * Gets the value of the buyerBatchID property.
     * 
     * @return
     *     possible object is
     *     {@link BuyerBatchID }
     *     
     */
    public BuyerBatchID getBuyerBatchID() {
        return buyerBatchID;
    }

    /**
     * Sets the value of the buyerBatchID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BuyerBatchID }
     *     
     */
    public void setBuyerBatchID(BuyerBatchID value) {
        this.buyerBatchID = value;
    }

    /**
     * Gets the value of the supplierBatchID property.
     * 
     * @return
     *     possible object is
     *     {@link SupplierBatchID }
     *     
     */
    public SupplierBatchID getSupplierBatchID() {
        return supplierBatchID;
    }

    /**
     * Sets the value of the supplierBatchID property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplierBatchID }
     *     
     */
    public void setSupplierBatchID(SupplierBatchID value) {
        this.supplierBatchID = value;
    }

    /**
     * Gets the value of the propertyValuation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the propertyValuation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPropertyValuation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PropertyValuation }
     * 
     * 
     */
    public List<PropertyValuation> getPropertyValuation() {
        if (propertyValuation == null) {
            propertyValuation = new ArrayList<PropertyValuation>();
        }
        return this.propertyValuation;
    }

    /**
     * Gets the value of the productionDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductionDate() {
        return productionDate;
    }

    /**
     * Sets the value of the productionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductionDate(String value) {
        this.productionDate = value;
    }

    /**
     * Gets the value of the expirationDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the value of the expirationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpirationDate(String value) {
        this.expirationDate = value;
    }

    /**
     * Gets the value of the inspectionDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInspectionDate() {
        return inspectionDate;
    }

    /**
     * Sets the value of the inspectionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInspectionDate(String value) {
        this.inspectionDate = value;
    }

    /**
     * Gets the value of the shelfLife property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShelfLife() {
        return shelfLife;
    }

    /**
     * Sets the value of the shelfLife property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShelfLife(String value) {
        this.shelfLife = value;
    }

    /**
     * Gets the value of the originCountryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginCountryCode() {
        return originCountryCode;
    }

    /**
     * Sets the value of the originCountryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginCountryCode(String value) {
        this.originCountryCode = value;
    }

    /**
     * Gets the value of the batchQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBatchQuantity() {
        return batchQuantity;
    }

    /**
     * Sets the value of the batchQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBatchQuantity(String value) {
        this.batchQuantity = value;
    }

}
