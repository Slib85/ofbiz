
package com.bigname.cxml.invoice;

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
 * <p>Java class for InvoiceDetailServiceItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceDetailServiceItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}InvoiceDetailServiceItemReference"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{}ServiceEntryItemReference"/>
 *           &lt;element ref="{}ServiceEntryItemIDInfo"/>
 *         &lt;/choice>
 *         &lt;element ref="{}SubtotalAmount"/>
 *         &lt;element ref="{}Period" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{}UnitRate"/>
 *           &lt;sequence>
 *             &lt;element ref="{}UnitOfMeasure"/>
 *             &lt;element ref="{}UnitPrice"/>
 *             &lt;element ref="{}PriceBasisQuantity" minOccurs="0"/>
 *           &lt;/sequence>
 *         &lt;/choice>
 *         &lt;element ref="{}Tax" minOccurs="0"/>
 *         &lt;element ref="{}GrossAmount" minOccurs="0"/>
 *         &lt;element ref="{}InvoiceDetailDiscount" minOccurs="0"/>
 *         &lt;element ref="{}InvoiceItemModifications" minOccurs="0"/>
 *         &lt;element ref="{}TotalCharges" minOccurs="0"/>
 *         &lt;element ref="{}TotalAllowances" minOccurs="0"/>
 *         &lt;element ref="{}TotalAmountWithoutTax" minOccurs="0"/>
 *         &lt;element ref="{}NetAmount" minOccurs="0"/>
 *         &lt;element ref="{}Distribution" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Comments" minOccurs="0"/>
 *         &lt;sequence minOccurs="0">
 *           &lt;element ref="{}InvoiceLaborDetail"/>
 *         &lt;/sequence>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="invoiceLineNumber" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="quantity" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="referenceDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="inspectionDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="parentInvoiceLineNumber" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="itemType">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="item"/>
 *             &lt;enumeration value="lean"/>
 *             &lt;enumeration value="composite"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="isAdHoc">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
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
@XmlType(name = "InvoiceDetailServiceItem", propOrder = {
    "invoiceDetailServiceItemReference",
    "serviceEntryItemReference",
    "serviceEntryItemIDInfo",
    "subtotalAmount",
    "period",
    "unitRate",
    "unitOfMeasure",
    "unitPrice",
    "priceBasisQuantity",
    "tax",
    "grossAmount",
    "invoiceDetailDiscount",
    "invoiceItemModifications",
    "totalCharges",
    "totalAllowances",
    "totalAmountWithoutTax",
    "netAmount",
    "distribution",
    "comments",
    "invoiceLaborDetail",
    "extrinsic"
})
public class InvoiceDetailServiceItem {

    @XmlElement(name = "InvoiceDetailServiceItemReference", required = true)
    protected InvoiceDetailServiceItemReference invoiceDetailServiceItemReference;
    @XmlElement(name = "ServiceEntryItemReference")
    protected ServiceEntryItemReference serviceEntryItemReference;
    @XmlElement(name = "ServiceEntryItemIDInfo")
    protected ServiceEntryItemIDInfo serviceEntryItemIDInfo;
    @XmlElement(name = "SubtotalAmount", required = true)
    protected SubtotalAmount subtotalAmount;
    @XmlElement(name = "Period")
    protected Period period;
    @XmlElement(name = "UnitRate")
    protected UnitRate unitRate;
    @XmlElement(name = "UnitOfMeasure")
    protected UnitOfMeasure unitOfMeasure;
    @XmlElement(name = "UnitPrice")
    protected UnitPrice unitPrice;
    @XmlElement(name = "PriceBasisQuantity")
    protected PriceBasisQuantity priceBasisQuantity;
    @XmlElement(name = "Tax")
    protected Tax tax;
    @XmlElement(name = "GrossAmount")
    protected GrossAmount grossAmount;
    @XmlElement(name = "InvoiceDetailDiscount")
    protected InvoiceDetailDiscount invoiceDetailDiscount;
    @XmlElement(name = "InvoiceItemModifications")
    protected InvoiceItemModifications invoiceItemModifications;
    @XmlElement(name = "TotalCharges")
    protected TotalCharges totalCharges;
    @XmlElement(name = "TotalAllowances")
    protected TotalAllowances totalAllowances;
    @XmlElement(name = "TotalAmountWithoutTax")
    protected TotalAmountWithoutTax totalAmountWithoutTax;
    @XmlElement(name = "NetAmount")
    protected NetAmount netAmount;
    @XmlElement(name = "Distribution")
    protected List<Distribution> distribution;
    @XmlElement(name = "Comments")
    protected Comments comments;
    @XmlElement(name = "InvoiceLaborDetail")
    protected InvoiceLaborDetail invoiceLaborDetail;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(name = "invoiceLineNumber", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String invoiceLineNumber;
    @XmlAttribute(name = "quantity")
    @XmlSchemaType(name = "anySimpleType")
    protected String quantity;
    @XmlAttribute(name = "referenceDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String referenceDate;
    @XmlAttribute(name = "inspectionDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String inspectionDate;
    @XmlAttribute(name = "parentInvoiceLineNumber")
    @XmlSchemaType(name = "anySimpleType")
    protected String parentInvoiceLineNumber;
    @XmlAttribute(name = "itemType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String itemType;
    @XmlAttribute(name = "isAdHoc")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isAdHoc;

    /**
     * Gets the value of the invoiceDetailServiceItemReference property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceDetailServiceItemReference }
     *     
     */
    public InvoiceDetailServiceItemReference getInvoiceDetailServiceItemReference() {
        return invoiceDetailServiceItemReference;
    }

    /**
     * Sets the value of the invoiceDetailServiceItemReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceDetailServiceItemReference }
     *     
     */
    public void setInvoiceDetailServiceItemReference(InvoiceDetailServiceItemReference value) {
        this.invoiceDetailServiceItemReference = value;
    }

    /**
     * Gets the value of the serviceEntryItemReference property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEntryItemReference }
     *     
     */
    public ServiceEntryItemReference getServiceEntryItemReference() {
        return serviceEntryItemReference;
    }

    /**
     * Sets the value of the serviceEntryItemReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEntryItemReference }
     *     
     */
    public void setServiceEntryItemReference(ServiceEntryItemReference value) {
        this.serviceEntryItemReference = value;
    }

    /**
     * Gets the value of the serviceEntryItemIDInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEntryItemIDInfo }
     *     
     */
    public ServiceEntryItemIDInfo getServiceEntryItemIDInfo() {
        return serviceEntryItemIDInfo;
    }

    /**
     * Sets the value of the serviceEntryItemIDInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEntryItemIDInfo }
     *     
     */
    public void setServiceEntryItemIDInfo(ServiceEntryItemIDInfo value) {
        this.serviceEntryItemIDInfo = value;
    }

    /**
     * Gets the value of the subtotalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link SubtotalAmount }
     *     
     */
    public SubtotalAmount getSubtotalAmount() {
        return subtotalAmount;
    }

    /**
     * Sets the value of the subtotalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubtotalAmount }
     *     
     */
    public void setSubtotalAmount(SubtotalAmount value) {
        this.subtotalAmount = value;
    }

    /**
     * Gets the value of the period property.
     * 
     * @return
     *     possible object is
     *     {@link Period }
     *     
     */
    public Period getPeriod() {
        return period;
    }

    /**
     * Sets the value of the period property.
     * 
     * @param value
     *     allowed object is
     *     {@link Period }
     *     
     */
    public void setPeriod(Period value) {
        this.period = value;
    }

    /**
     * Gets the value of the unitRate property.
     * 
     * @return
     *     possible object is
     *     {@link UnitRate }
     *     
     */
    public UnitRate getUnitRate() {
        return unitRate;
    }

    /**
     * Sets the value of the unitRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitRate }
     *     
     */
    public void setUnitRate(UnitRate value) {
        this.unitRate = value;
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
     * Gets the value of the tax property.
     * 
     * @return
     *     possible object is
     *     {@link Tax }
     *     
     */
    public Tax getTax() {
        return tax;
    }

    /**
     * Sets the value of the tax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Tax }
     *     
     */
    public void setTax(Tax value) {
        this.tax = value;
    }

    /**
     * Gets the value of the grossAmount property.
     * 
     * @return
     *     possible object is
     *     {@link GrossAmount }
     *     
     */
    public GrossAmount getGrossAmount() {
        return grossAmount;
    }

    /**
     * Sets the value of the grossAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link GrossAmount }
     *     
     */
    public void setGrossAmount(GrossAmount value) {
        this.grossAmount = value;
    }

    /**
     * Gets the value of the invoiceDetailDiscount property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceDetailDiscount }
     *     
     */
    public InvoiceDetailDiscount getInvoiceDetailDiscount() {
        return invoiceDetailDiscount;
    }

    /**
     * Sets the value of the invoiceDetailDiscount property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceDetailDiscount }
     *     
     */
    public void setInvoiceDetailDiscount(InvoiceDetailDiscount value) {
        this.invoiceDetailDiscount = value;
    }

    /**
     * Gets the value of the invoiceItemModifications property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceItemModifications }
     *     
     */
    public InvoiceItemModifications getInvoiceItemModifications() {
        return invoiceItemModifications;
    }

    /**
     * Sets the value of the invoiceItemModifications property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceItemModifications }
     *     
     */
    public void setInvoiceItemModifications(InvoiceItemModifications value) {
        this.invoiceItemModifications = value;
    }

    /**
     * Gets the value of the totalCharges property.
     * 
     * @return
     *     possible object is
     *     {@link TotalCharges }
     *     
     */
    public TotalCharges getTotalCharges() {
        return totalCharges;
    }

    /**
     * Sets the value of the totalCharges property.
     * 
     * @param value
     *     allowed object is
     *     {@link TotalCharges }
     *     
     */
    public void setTotalCharges(TotalCharges value) {
        this.totalCharges = value;
    }

    /**
     * Gets the value of the totalAllowances property.
     * 
     * @return
     *     possible object is
     *     {@link TotalAllowances }
     *     
     */
    public TotalAllowances getTotalAllowances() {
        return totalAllowances;
    }

    /**
     * Sets the value of the totalAllowances property.
     * 
     * @param value
     *     allowed object is
     *     {@link TotalAllowances }
     *     
     */
    public void setTotalAllowances(TotalAllowances value) {
        this.totalAllowances = value;
    }

    /**
     * Gets the value of the totalAmountWithoutTax property.
     * 
     * @return
     *     possible object is
     *     {@link TotalAmountWithoutTax }
     *     
     */
    public TotalAmountWithoutTax getTotalAmountWithoutTax() {
        return totalAmountWithoutTax;
    }

    /**
     * Sets the value of the totalAmountWithoutTax property.
     * 
     * @param value
     *     allowed object is
     *     {@link TotalAmountWithoutTax }
     *     
     */
    public void setTotalAmountWithoutTax(TotalAmountWithoutTax value) {
        this.totalAmountWithoutTax = value;
    }

    /**
     * Gets the value of the netAmount property.
     * 
     * @return
     *     possible object is
     *     {@link NetAmount }
     *     
     */
    public NetAmount getNetAmount() {
        return netAmount;
    }

    /**
     * Sets the value of the netAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link NetAmount }
     *     
     */
    public void setNetAmount(NetAmount value) {
        this.netAmount = value;
    }

    /**
     * Gets the value of the distribution property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the distribution property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDistribution().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Distribution }
     * 
     * 
     */
    public List<Distribution> getDistribution() {
        if (distribution == null) {
            distribution = new ArrayList<Distribution>();
        }
        return this.distribution;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link Comments }
     *     
     */
    public Comments getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link Comments }
     *     
     */
    public void setComments(Comments value) {
        this.comments = value;
    }

    /**
     * Gets the value of the invoiceLaborDetail property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceLaborDetail }
     *     
     */
    public InvoiceLaborDetail getInvoiceLaborDetail() {
        return invoiceLaborDetail;
    }

    /**
     * Sets the value of the invoiceLaborDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceLaborDetail }
     *     
     */
    public void setInvoiceLaborDetail(InvoiceLaborDetail value) {
        this.invoiceLaborDetail = value;
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
     * Gets the value of the invoiceLineNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceLineNumber() {
        return invoiceLineNumber;
    }

    /**
     * Sets the value of the invoiceLineNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceLineNumber(String value) {
        this.invoiceLineNumber = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuantity(String value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the referenceDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceDate() {
        return referenceDate;
    }

    /**
     * Sets the value of the referenceDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceDate(String value) {
        this.referenceDate = value;
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
     * Gets the value of the parentInvoiceLineNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentInvoiceLineNumber() {
        return parentInvoiceLineNumber;
    }

    /**
     * Sets the value of the parentInvoiceLineNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentInvoiceLineNumber(String value) {
        this.parentInvoiceLineNumber = value;
    }

    /**
     * Gets the value of the itemType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemType() {
        return itemType;
    }

    /**
     * Sets the value of the itemType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemType(String value) {
        this.itemType = value;
    }

    /**
     * Gets the value of the isAdHoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsAdHoc() {
        return isAdHoc;
    }

    /**
     * Sets the value of the isAdHoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsAdHoc(String value) {
        this.isAdHoc = value;
    }

}
