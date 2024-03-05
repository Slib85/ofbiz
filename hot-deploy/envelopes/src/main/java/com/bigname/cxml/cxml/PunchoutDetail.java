
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
 * <p>Java class for PunchoutDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PunchoutDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Description" maxOccurs="unbounded"/>
 *         &lt;element ref="{}URL"/>
 *         &lt;element ref="{}Classification" maxOccurs="unbounded"/>
 *         &lt;element ref="{}UnitPrice" minOccurs="0"/>
 *         &lt;element ref="{}UnitOfMeasure" minOccurs="0"/>
 *         &lt;element ref="{}PriceBasisQuantity" minOccurs="0"/>
 *         &lt;element ref="{}ManufacturerName" minOccurs="0"/>
 *         &lt;element ref="{}ManufacturerPartID" minOccurs="0"/>
 *         &lt;element ref="{}LeadTime" minOccurs="0"/>
 *         &lt;element ref="{}ExpirationDate" minOccurs="0"/>
 *         &lt;element ref="{}EffectiveDate" minOccurs="0"/>
 *         &lt;element ref="{}SearchGroupData" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}TerritoryAvailable" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="punchoutLevel" default="store">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="aisle"/>
 *             &lt;enumeration value="shelf"/>
 *             &lt;enumeration value="store"/>
 *             &lt;enumeration value="product"/>
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
@XmlType(name = "PunchoutDetail", propOrder = {
    "description",
    "url",
    "classification",
    "unitPrice",
    "unitOfMeasure",
    "priceBasisQuantity",
    "manufacturerName",
    "manufacturerPartID",
    "leadTime",
    "expirationDate",
    "effectiveDate",
    "searchGroupData",
    "territoryAvailable",
    "extrinsic"
})
public class PunchoutDetail {

    @XmlElement(name = "Description", required = true)
    protected List<Description> description;
    @XmlElement(name = "URL", required = true)
    protected URL url;
    @XmlElement(name = "Classification", required = true)
    protected List<Classification> classification;
    @XmlElement(name = "UnitPrice")
    protected UnitPrice unitPrice;
    @XmlElement(name = "UnitOfMeasure")
    protected UnitOfMeasure unitOfMeasure;
    @XmlElement(name = "PriceBasisQuantity")
    protected PriceBasisQuantity priceBasisQuantity;
    @XmlElement(name = "ManufacturerName")
    protected ManufacturerName manufacturerName;
    @XmlElement(name = "ManufacturerPartID")
    protected ManufacturerPartID manufacturerPartID;
    @XmlElement(name = "LeadTime")
    protected LeadTime leadTime;
    @XmlElement(name = "ExpirationDate")
    protected ExpirationDate expirationDate;
    @XmlElement(name = "EffectiveDate")
    protected EffectiveDate effectiveDate;
    @XmlElement(name = "SearchGroupData")
    protected List<SearchGroupData> searchGroupData;
    @XmlElement(name = "TerritoryAvailable")
    protected List<TerritoryAvailable> territoryAvailable;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(name = "punchoutLevel")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String punchoutLevel;

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
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link URL }
     *     
     */
    public URL getURL() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link URL }
     *     
     */
    public void setURL(URL value) {
        this.url = value;
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
     * Gets the value of the leadTime property.
     * 
     * @return
     *     possible object is
     *     {@link LeadTime }
     *     
     */
    public LeadTime getLeadTime() {
        return leadTime;
    }

    /**
     * Sets the value of the leadTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link LeadTime }
     *     
     */
    public void setLeadTime(LeadTime value) {
        this.leadTime = value;
    }

    /**
     * Gets the value of the expirationDate property.
     * 
     * @return
     *     possible object is
     *     {@link ExpirationDate }
     *     
     */
    public ExpirationDate getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the value of the expirationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpirationDate }
     *     
     */
    public void setExpirationDate(ExpirationDate value) {
        this.expirationDate = value;
    }

    /**
     * Gets the value of the effectiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link EffectiveDate }
     *     
     */
    public EffectiveDate getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the value of the effectiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link EffectiveDate }
     *     
     */
    public void setEffectiveDate(EffectiveDate value) {
        this.effectiveDate = value;
    }

    /**
     * Gets the value of the searchGroupData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the searchGroupData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSearchGroupData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SearchGroupData }
     * 
     * 
     */
    public List<SearchGroupData> getSearchGroupData() {
        if (searchGroupData == null) {
            searchGroupData = new ArrayList<SearchGroupData>();
        }
        return this.searchGroupData;
    }

    /**
     * Gets the value of the territoryAvailable property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the territoryAvailable property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTerritoryAvailable().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TerritoryAvailable }
     * 
     * 
     */
    public List<TerritoryAvailable> getTerritoryAvailable() {
        if (territoryAvailable == null) {
            territoryAvailable = new ArrayList<TerritoryAvailable>();
        }
        return this.territoryAvailable;
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

    /**
     * Gets the value of the punchoutLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPunchoutLevel() {
        if (punchoutLevel == null) {
            return "store";
        } else {
            return punchoutLevel;
        }
    }

    /**
     * Sets the value of the punchoutLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPunchoutLevel(String value) {
        this.punchoutLevel = value;
    }

}
