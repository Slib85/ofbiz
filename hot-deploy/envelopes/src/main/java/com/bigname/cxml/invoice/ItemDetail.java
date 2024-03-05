
package com.bigname.cxml.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ItemDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}UnitPrice"/>
 *         &lt;element ref="{}Description" maxOccurs="unbounded"/>
 *         &lt;element ref="{}OverallLimit" minOccurs="0"/>
 *         &lt;element ref="{}ExpectedLimit" minOccurs="0"/>
 *         &lt;element ref="{}UnitOfMeasure"/>
 *         &lt;element ref="{}PriceBasisQuantity" minOccurs="0"/>
 *         &lt;element ref="{}Classification" maxOccurs="unbounded"/>
 *         &lt;element ref="{}ManufacturerPartID" minOccurs="0"/>
 *         &lt;element ref="{}ManufacturerName" minOccurs="0"/>
 *         &lt;element ref="{}URL" minOccurs="0"/>
 *         &lt;element ref="{}LeadTime" minOccurs="0"/>
 *         &lt;element ref="{}Dimension" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}ItemDetailIndustry" minOccurs="0"/>
 *         &lt;element ref="{}AttachmentReference" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}PlannedAcceptanceDays" minOccurs="0"/>
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
@XmlType(name = "ItemDetail", propOrder = {
    "unitPrice",
    "description",
    "overallLimit",
    "expectedLimit",
    "unitOfMeasure",
    "priceBasisQuantity",
    "classification",
    "manufacturerPartID",
    "manufacturerName",
    "url",
    "leadTime",
    "dimension",
    "itemDetailIndustry",
    "attachmentReference",
    "plannedAcceptanceDays",
    "extrinsic"
})
public class ItemDetail {

    @XmlElement(name = "UnitPrice", required = true)
    protected UnitPrice unitPrice;
    @XmlElement(name = "Description", required = true)
    protected List<Description> description;
    @XmlElement(name = "OverallLimit")
    protected OverallLimit overallLimit;
    @XmlElement(name = "ExpectedLimit")
    protected ExpectedLimit expectedLimit;
    @XmlElement(name = "UnitOfMeasure", required = true)
    protected UnitOfMeasure unitOfMeasure;
    @XmlElement(name = "PriceBasisQuantity")
    protected PriceBasisQuantity priceBasisQuantity;
    @XmlElement(name = "Classification", required = true)
    protected List<Classification> classification;
    @XmlElement(name = "ManufacturerPartID")
    protected ManufacturerPartID manufacturerPartID;
    @XmlElement(name = "ManufacturerName")
    protected ManufacturerName manufacturerName;
    @XmlElement(name = "URL")
    protected URL url;
    @XmlElement(name = "LeadTime")
    protected LeadTime leadTime;
    @XmlElement(name = "Dimension")
    protected List<Dimension> dimension;
    @XmlElement(name = "ItemDetailIndustry")
    protected ItemDetailIndustry itemDetailIndustry;
    @XmlElement(name = "AttachmentReference")
    protected List<AttachmentReference> attachmentReference;
    @XmlElement(name = "PlannedAcceptanceDays")
    protected PlannedAcceptanceDays plannedAcceptanceDays;
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
     * Gets the value of the overallLimit property.
     * 
     * @return
     *     possible object is
     *     {@link OverallLimit }
     *     
     */
    public OverallLimit getOverallLimit() {
        return overallLimit;
    }

    /**
     * Sets the value of the overallLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link OverallLimit }
     *     
     */
    public void setOverallLimit(OverallLimit value) {
        this.overallLimit = value;
    }

    /**
     * Gets the value of the expectedLimit property.
     * 
     * @return
     *     possible object is
     *     {@link ExpectedLimit }
     *     
     */
    public ExpectedLimit getExpectedLimit() {
        return expectedLimit;
    }

    /**
     * Sets the value of the expectedLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpectedLimit }
     *     
     */
    public void setExpectedLimit(ExpectedLimit value) {
        this.expectedLimit = value;
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
     * Gets the value of the attachmentReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attachmentReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttachmentReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttachmentReference }
     * 
     * 
     */
    public List<AttachmentReference> getAttachmentReference() {
        if (attachmentReference == null) {
            attachmentReference = new ArrayList<AttachmentReference>();
        }
        return this.attachmentReference;
    }

    /**
     * Gets the value of the plannedAcceptanceDays property.
     * 
     * @return
     *     possible object is
     *     {@link PlannedAcceptanceDays }
     *     
     */
    public PlannedAcceptanceDays getPlannedAcceptanceDays() {
        return plannedAcceptanceDays;
    }

    /**
     * Sets the value of the plannedAcceptanceDays property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlannedAcceptanceDays }
     *     
     */
    public void setPlannedAcceptanceDays(PlannedAcceptanceDays value) {
        this.plannedAcceptanceDays = value;
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
