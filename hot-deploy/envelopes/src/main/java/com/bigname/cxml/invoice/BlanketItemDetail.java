
package com.bigname.cxml.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BlanketItemDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BlanketItemDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Description" maxOccurs="unbounded"/>
 *         &lt;element ref="{}MaxAmount" minOccurs="0"/>
 *         &lt;element ref="{}MinAmount" minOccurs="0"/>
 *         &lt;element ref="{}MaxContractAmount" minOccurs="0"/>
 *         &lt;element ref="{}MaxAdhocAmount" minOccurs="0"/>
 *         &lt;element ref="{}MaxQuantity" minOccurs="0"/>
 *         &lt;element ref="{}MinQuantity" minOccurs="0"/>
 *         &lt;element ref="{}UnitPrice" minOccurs="0"/>
 *         &lt;element ref="{}UnitOfMeasure" minOccurs="0"/>
 *         &lt;element ref="{}PriceBasisQuantity" minOccurs="0"/>
 *         &lt;element ref="{}Classification" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BlanketItemDetail", propOrder = {
    "description",
    "maxAmount",
    "minAmount",
    "maxContractAmount",
    "maxAdhocAmount",
    "maxQuantity",
    "minQuantity",
    "unitPrice",
    "unitOfMeasure",
    "priceBasisQuantity",
    "classification",
    "extrinsic"
})
public class BlanketItemDetail {

    @XmlElement(name = "Description", required = true)
    protected List<Description> description;
    @XmlElement(name = "MaxAmount")
    protected MaxAmount maxAmount;
    @XmlElement(name = "MinAmount")
    protected MinAmount minAmount;
    @XmlElement(name = "MaxContractAmount")
    protected MaxContractAmount maxContractAmount;
    @XmlElement(name = "MaxAdhocAmount")
    protected MaxAdhocAmount maxAdhocAmount;
    @XmlElement(name = "MaxQuantity")
    protected MaxQuantity maxQuantity;
    @XmlElement(name = "MinQuantity")
    protected MinQuantity minQuantity;
    @XmlElement(name = "UnitPrice")
    protected UnitPrice unitPrice;
    @XmlElement(name = "UnitOfMeasure")
    protected UnitOfMeasure unitOfMeasure;
    @XmlElement(name = "PriceBasisQuantity")
    protected PriceBasisQuantity priceBasisQuantity;
    @XmlElement(name = "Classification")
    protected List<Classification> classification;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;

    /**
     * Gets the value of the description property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the description property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescription().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Description }
     * 
     * 
     */
    public List<Description> getDescription() {
        if (description == null) {
            description = new ArrayList<Description>();
        }
        return this.description;
    }

    /**
     * Gets the value of the maxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MaxAmount }
     *     
     */
    public MaxAmount getMaxAmount() {
        return maxAmount;
    }

    /**
     * Sets the value of the maxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaxAmount }
     *     
     */
    public void setMaxAmount(MaxAmount value) {
        this.maxAmount = value;
    }

    /**
     * Gets the value of the minAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MinAmount }
     *     
     */
    public MinAmount getMinAmount() {
        return minAmount;
    }

    /**
     * Sets the value of the minAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MinAmount }
     *     
     */
    public void setMinAmount(MinAmount value) {
        this.minAmount = value;
    }

    /**
     * Gets the value of the maxContractAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MaxContractAmount }
     *     
     */
    public MaxContractAmount getMaxContractAmount() {
        return maxContractAmount;
    }

    /**
     * Sets the value of the maxContractAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaxContractAmount }
     *     
     */
    public void setMaxContractAmount(MaxContractAmount value) {
        this.maxContractAmount = value;
    }

    /**
     * Gets the value of the maxAdhocAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MaxAdhocAmount }
     *     
     */
    public MaxAdhocAmount getMaxAdhocAmount() {
        return maxAdhocAmount;
    }

    /**
     * Sets the value of the maxAdhocAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaxAdhocAmount }
     *     
     */
    public void setMaxAdhocAmount(MaxAdhocAmount value) {
        this.maxAdhocAmount = value;
    }

    /**
     * Gets the value of the maxQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link MaxQuantity }
     *     
     */
    public MaxQuantity getMaxQuantity() {
        return maxQuantity;
    }

    /**
     * Sets the value of the maxQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaxQuantity }
     *     
     */
    public void setMaxQuantity(MaxQuantity value) {
        this.maxQuantity = value;
    }

    /**
     * Gets the value of the minQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link MinQuantity }
     *     
     */
    public MinQuantity getMinQuantity() {
        return minQuantity;
    }

    /**
     * Sets the value of the minQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link MinQuantity }
     *     
     */
    public void setMinQuantity(MinQuantity value) {
        this.minQuantity = value;
    }

    /**
     * Gets the value of the unitPrice property.
     * 
     * @return
     *     possible object is
     *     {@link UnitPrice }
     *     
     */
    public UnitPrice getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the value of the unitPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitPrice }
     *     
     */
    public void setUnitPrice(UnitPrice value) {
        this.unitPrice = value;
    }

    /**
     * Gets the value of the unitOfMeasure property.
     * 
     * @return
     *     possible object is
     *     {@link UnitOfMeasure }
     *     
     */
    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    /**
     * Sets the value of the unitOfMeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitOfMeasure }
     *     
     */
    public void setUnitOfMeasure(UnitOfMeasure value) {
        this.unitOfMeasure = value;
    }

    /**
     * Gets the value of the priceBasisQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link PriceBasisQuantity }
     *     
     */
    public PriceBasisQuantity getPriceBasisQuantity() {
        return priceBasisQuantity;
    }

    /**
     * Sets the value of the priceBasisQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceBasisQuantity }
     *     
     */
    public void setPriceBasisQuantity(PriceBasisQuantity value) {
        this.priceBasisQuantity = value;
    }

    /**
     * Gets the value of the classification property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the classification property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClassification().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Classification }
     * 
     * 
     */
    public List<Classification> getClassification() {
        if (classification == null) {
            classification = new ArrayList<Classification>();
        }
        return this.classification;
    }

    /**
     * Gets the value of the extrinsic property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extrinsic property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtrinsic().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Extrinsic }
     * 
     * 
     */
    public List<Extrinsic> getExtrinsic() {
        if (extrinsic == null) {
            extrinsic = new ArrayList<Extrinsic>();
        }
        return this.extrinsic;
    }

}
