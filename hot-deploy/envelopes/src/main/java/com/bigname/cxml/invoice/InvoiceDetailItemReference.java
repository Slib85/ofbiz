
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
 * <p>Java class for InvoiceDetailItemReference complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceDetailItemReference">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ItemID" minOccurs="0"/>
 *         &lt;element ref="{}Description" minOccurs="0"/>
 *         &lt;element ref="{}Classification" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;sequence minOccurs="0">
 *           &lt;element ref="{}ManufacturerPartID"/>
 *           &lt;element ref="{}ManufacturerName"/>
 *         &lt;/sequence>
 *         &lt;element ref="{}Country" minOccurs="0"/>
 *         &lt;element ref="{}SerialNumber" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}SupplierBatchID" minOccurs="0"/>
 *         &lt;element ref="{}InvoiceDetailItemReferenceIndustry" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="lineNumber" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="serialNumber1" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceDetailItemReference", propOrder = {
    "itemID",
    "description",
    "classification",
    "manufacturerPartID",
    "manufacturerName",
    "country",
    "serialNumber",
    "supplierBatchID",
    "invoiceDetailItemReferenceIndustry"
})
public class InvoiceDetailItemReference {

    @XmlElement(name = "ItemID")
    protected ItemID itemID;
    @XmlElement(name = "Description")
    protected Description description;
    @XmlElement(name = "Classification")
    protected List<Classification> classification;
    @XmlElement(name = "ManufacturerPartID")
    protected ManufacturerPartID manufacturerPartID;
    @XmlElement(name = "ManufacturerName")
    protected ManufacturerName manufacturerName;
    @XmlElement(name = "Country")
    protected Country country;
    @XmlElement(name = "SerialNumber")
    protected List<SerialNumber> serialNumber;
    @XmlElement(name = "SupplierBatchID")
    protected SupplierBatchID supplierBatchID;
    @XmlElement(name = "InvoiceDetailItemReferenceIndustry")
    protected InvoiceDetailItemReferenceIndustry invoiceDetailItemReferenceIndustry;
    @XmlAttribute(name = "lineNumber", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String lineNumber;
    @XmlAttribute(name = "serialNumber")
    @XmlSchemaType(name = "anySimpleType")
    protected String serialNumberValue;

    /**
     * Gets the value of the itemID property.
     * 
     * @return
     *     possible object is
     *     {@link ItemID }
     *     
     */
    public ItemID getItemID() {
        return itemID;
    }

    /**
     * Sets the value of the itemID property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemID }
     *     
     */
    public void setItemID(ItemID value) {
        this.itemID = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link Description }
     *     
     */
    public Description getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link Description }
     *     
     */
    public void setDescription(Description value) {
        this.description = value;
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
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link Country }
     *     
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link Country }
     *     
     */
    public void setCountry(Country value) {
        this.country = value;
    }

    /**
     * Gets the value of the serialNumber property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serialNumber property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSerialNumber().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SerialNumber }
     * 
     * 
     */
    public List<SerialNumber> getSerialNumber() {
        if (serialNumber == null) {
            serialNumber = new ArrayList<SerialNumber>();
        }
        return this.serialNumber;
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
     * Gets the value of the invoiceDetailItemReferenceIndustry property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceDetailItemReferenceIndustry }
     *     
     */
    public InvoiceDetailItemReferenceIndustry getInvoiceDetailItemReferenceIndustry() {
        return invoiceDetailItemReferenceIndustry;
    }

    /**
     * Sets the value of the invoiceDetailItemReferenceIndustry property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceDetailItemReferenceIndustry }
     *     
     */
    public void setInvoiceDetailItemReferenceIndustry(InvoiceDetailItemReferenceIndustry value) {
        this.invoiceDetailItemReferenceIndustry = value;
    }

    /**
     * Gets the value of the lineNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLineNumber() {
        return lineNumber;
    }

    /**
     * Sets the value of the lineNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineNumber(String value) {
        this.lineNumber = value;
    }

    /**
     * Gets the value of the serialNumberValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialNumberValue() {
        return serialNumberValue;
    }

    /**
     * Sets the value of the serialNumberValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialNumberValue(String value) {
        this.serialNumberValue = value;
    }

}
