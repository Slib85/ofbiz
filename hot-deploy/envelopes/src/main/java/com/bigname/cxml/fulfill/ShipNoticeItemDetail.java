
package com.bigname.cxml.fulfill;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ShipNoticeItemDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipNoticeItemDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}UnitPrice" minOccurs="0"/>
 *         &lt;element ref="{}Description" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}UnitOfMeasure" minOccurs="0"/>
 *         &lt;element ref="{}PriceBasisQuantity" minOccurs="0"/>
 *         &lt;element ref="{}Classification" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}ManufacturerPartID" minOccurs="0"/>
 *         &lt;element ref="{}ManufacturerName" minOccurs="0"/>
 *         &lt;element ref="{}Dimension" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}ItemDetailIndustry" minOccurs="0"/>
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
@XmlType(name = "ShipNoticeItemDetail", propOrder = {
    "unitPrice",
    "description",
    "unitOfMeasure",
    "priceBasisQuantity",
    "classification",
    "manufacturerPartID",
    "manufacturerName",
    "dimension",
    "itemDetailIndustry",
    "extrinsic"
})
public class ShipNoticeItemDetail {

    @XmlElement(name = "UnitPrice")
    protected UnitPrice unitPrice;
    @XmlElement(name = "Description")
    protected List<Description> description;
    @XmlElement(name = "UnitOfMeasure")
    protected UnitOfMeasure unitOfMeasure;
    @XmlElement(name = "PriceBasisQuantity")
    protected PriceBasisQuantity priceBasisQuantity;
    @XmlElement(name = "Classification")
    protected List<Classification> classification;
    @XmlElement(name = "ManufacturerPartID")
    protected ManufacturerPartID manufacturerPartID;
    @XmlElement(name = "ManufacturerName")
    protected ManufacturerName manufacturerName;
    @XmlElement(name = "Dimension")
    protected List<Dimension> dimension;
    @XmlElement(name = "ItemDetailIndustry")
    protected ItemDetailIndustry itemDetailIndustry;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;

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
     * Gets the value of the manufacturerPartID property.
     * 
     * @return
     *     possible object is
     *     {@link ManufacturerPartID }
     *     
     */
    public ManufacturerPartID getManufacturerPartID() {
        return manufacturerPartID;
    }

    /**
     * Sets the value of the manufacturerPartID property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManufacturerPartID }
     *     
     */
    public void setManufacturerPartID(ManufacturerPartID value) {
        this.manufacturerPartID = value;
    }

    /**
     * Gets the value of the manufacturerName property.
     * 
     * @return
     *     possible object is
     *     {@link ManufacturerName }
     *     
     */
    public ManufacturerName getManufacturerName() {
        return manufacturerName;
    }

    /**
     * Sets the value of the manufacturerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManufacturerName }
     *     
     */
    public void setManufacturerName(ManufacturerName value) {
        this.manufacturerName = value;
    }

    /**
     * Gets the value of the dimension property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dimension property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDimension().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Dimension }
     * 
     * 
     */
    public List<Dimension> getDimension() {
        if (dimension == null) {
            dimension = new ArrayList<Dimension>();
        }
        return this.dimension;
    }

    /**
     * Gets the value of the itemDetailIndustry property.
     * 
     * @return
     *     possible object is
     *     {@link ItemDetailIndustry }
     *     
     */
    public ItemDetailIndustry getItemDetailIndustry() {
        return itemDetailIndustry;
    }

    /**
     * Sets the value of the itemDetailIndustry property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemDetailIndustry }
     *     
     */
    public void setItemDetailIndustry(ItemDetailIndustry value) {
        this.itemDetailIndustry = value;
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
