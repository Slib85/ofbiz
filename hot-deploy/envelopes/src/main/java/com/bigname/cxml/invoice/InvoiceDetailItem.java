
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
 * <p>Java class for InvoiceDetailItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceDetailItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}UnitOfMeasure"/>
 *         &lt;element ref="{}UnitPrice"/>
 *         &lt;element ref="{}PriceBasisQuantity" minOccurs="0"/>
 *         &lt;element ref="{}InvoiceDetailItemReference"/>
 *         &lt;element ref="{}ReceiptLineItemReference" minOccurs="0"/>
 *         &lt;element ref="{}ShipNoticeLineItemReference" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{}ServiceEntryItemReference"/>
 *           &lt;element ref="{}ServiceEntryItemIDInfo"/>
 *         &lt;/choice>
 *         &lt;element ref="{}ProductMovementItemIDInfo" minOccurs="0"/>
 *         &lt;element ref="{}SubtotalAmount" minOccurs="0"/>
 *         &lt;element ref="{}Tax" minOccurs="0"/>
 *         &lt;element ref="{}InvoiceDetailLineSpecialHandling" minOccurs="0"/>
 *         &lt;element ref="{}InvoiceDetailLineShipping" minOccurs="0"/>
 *         &lt;element ref="{}ShipNoticeIDInfo" minOccurs="0"/>
 *         &lt;element ref="{}GrossAmount" minOccurs="0"/>
 *         &lt;element ref="{}InvoiceDetailDiscount" minOccurs="0"/>
 *         &lt;element ref="{}InvoiceItemModifications" minOccurs="0"/>
 *         &lt;element ref="{}TotalCharges" minOccurs="0"/>
 *         &lt;element ref="{}TotalAllowances" minOccurs="0"/>
 *         &lt;element ref="{}TotalAmountWithoutTax" minOccurs="0"/>
 *         &lt;element ref="{}NetAmount" minOccurs="0"/>
 *         &lt;element ref="{}Distribution" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Packaging" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}InvoiceDetailItemIndustry" minOccurs="0"/>
 *         &lt;element ref="{}Comments" minOccurs="0"/>
 *         &lt;element ref="{}CustomsInfo" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="invoiceLineNumber" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="quantity" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
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
 *       &lt;attribute name="compositeItemType">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="itemLevel"/>
 *             &lt;enumeration value="groupLevel"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="reason">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="return"/>
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
@XmlType(name = "InvoiceDetailItem", propOrder = {
    "unitOfMeasure",
    "unitPrice",
    "priceBasisQuantity",
    "invoiceDetailItemReference",
    "receiptLineItemReference",
    "shipNoticeLineItemReference",
    "serviceEntryItemReference",
    "serviceEntryItemIDInfo",
    "productMovementItemIDInfo",
    "subtotalAmount",
    "tax",
    "invoiceDetailLineSpecialHandling",
    "invoiceDetailLineShipping",
    "shipNoticeIDInfo",
    "grossAmount",
    "invoiceDetailDiscount",
    "invoiceItemModifications",
    "totalCharges",
    "totalAllowances",
    "totalAmountWithoutTax",
    "netAmount",
    "distribution",
    "packaging",
    "invoiceDetailItemIndustry",
    "comments",
    "customsInfo",
    "extrinsic"
})
public class InvoiceDetailItem {

    @XmlElement(name = "UnitOfMeasure", required = true)
    protected UnitOfMeasure unitOfMeasure;
    @XmlElement(name = "UnitPrice", required = true)
    protected UnitPrice unitPrice;
    @XmlElement(name = "PriceBasisQuantity")
    protected PriceBasisQuantity priceBasisQuantity;
    @XmlElement(name = "InvoiceDetailItemReference", required = true)
    protected InvoiceDetailItemReference invoiceDetailItemReference;
    @XmlElement(name = "ReceiptLineItemReference")
    protected ReceiptLineItemReference receiptLineItemReference;
    @XmlElement(name = "ShipNoticeLineItemReference")
    protected ShipNoticeLineItemReference shipNoticeLineItemReference;
    @XmlElement(name = "ServiceEntryItemReference")
    protected ServiceEntryItemReference serviceEntryItemReference;
    @XmlElement(name = "ServiceEntryItemIDInfo")
    protected ServiceEntryItemIDInfo serviceEntryItemIDInfo;
    @XmlElement(name = "ProductMovementItemIDInfo")
    protected ProductMovementItemIDInfo productMovementItemIDInfo;
    @XmlElement(name = "SubtotalAmount")
    protected SubtotalAmount subtotalAmount;
    @XmlElement(name = "Tax")
    protected Tax tax;
    @XmlElement(name = "InvoiceDetailLineSpecialHandling")
    protected InvoiceDetailLineSpecialHandling invoiceDetailLineSpecialHandling;
    @XmlElement(name = "InvoiceDetailLineShipping")
    protected InvoiceDetailLineShipping invoiceDetailLineShipping;
    @XmlElement(name = "ShipNoticeIDInfo")
    protected ShipNoticeIDInfo shipNoticeIDInfo;
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
    @XmlElement(name = "Packaging")
    protected List<Packaging> packaging;
    @XmlElement(name = "InvoiceDetailItemIndustry")
    protected InvoiceDetailItemIndustry invoiceDetailItemIndustry;
    @XmlElement(name = "Comments")
    protected Comments comments;
    @XmlElement(name = "CustomsInfo")
    protected CustomsInfo customsInfo;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(name = "invoiceLineNumber", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String invoiceLineNumber;
    @XmlAttribute(name = "quantity", required = true)
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
    @XmlAttribute(name = "compositeItemType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String compositeItemType;
    @XmlAttribute(name = "reason")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String reason;
    @XmlAttribute(name = "isAdHoc")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isAdHoc;

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
     * Gets the value of the invoiceDetailItemReference property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceDetailItemReference }
     *     
     */
    public InvoiceDetailItemReference getInvoiceDetailItemReference() {
        return invoiceDetailItemReference;
    }

    /**
     * Sets the value of the invoiceDetailItemReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceDetailItemReference }
     *     
     */
    public void setInvoiceDetailItemReference(InvoiceDetailItemReference value) {
        this.invoiceDetailItemReference = value;
    }

    /**
     * Gets the value of the receiptLineItemReference property.
     * 
     * @return
     *     possible object is
     *     {@link ReceiptLineItemReference }
     *     
     */
    public ReceiptLineItemReference getReceiptLineItemReference() {
        return receiptLineItemReference;
    }

    /**
     * Sets the value of the receiptLineItemReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceiptLineItemReference }
     *     
     */
    public void setReceiptLineItemReference(ReceiptLineItemReference value) {
        this.receiptLineItemReference = value;
    }

    /**
     * Gets the value of the shipNoticeLineItemReference property.
     * 
     * @return
     *     possible object is
     *     {@link ShipNoticeLineItemReference }
     *     
     */
    public ShipNoticeLineItemReference getShipNoticeLineItemReference() {
        return shipNoticeLineItemReference;
    }

    /**
     * Sets the value of the shipNoticeLineItemReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipNoticeLineItemReference }
     *     
     */
    public void setShipNoticeLineItemReference(ShipNoticeLineItemReference value) {
        this.shipNoticeLineItemReference = value;
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
     * Gets the value of the productMovementItemIDInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ProductMovementItemIDInfo }
     *     
     */
    public ProductMovementItemIDInfo getProductMovementItemIDInfo() {
        return productMovementItemIDInfo;
    }

    /**
     * Sets the value of the productMovementItemIDInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductMovementItemIDInfo }
     *     
     */
    public void setProductMovementItemIDInfo(ProductMovementItemIDInfo value) {
        this.productMovementItemIDInfo = value;
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
     * Gets the value of the invoiceDetailLineSpecialHandling property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceDetailLineSpecialHandling }
     *     
     */
    public InvoiceDetailLineSpecialHandling getInvoiceDetailLineSpecialHandling() {
        return invoiceDetailLineSpecialHandling;
    }

    /**
     * Sets the value of the invoiceDetailLineSpecialHandling property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceDetailLineSpecialHandling }
     *     
     */
    public void setInvoiceDetailLineSpecialHandling(InvoiceDetailLineSpecialHandling value) {
        this.invoiceDetailLineSpecialHandling = value;
    }

    /**
     * Gets the value of the invoiceDetailLineShipping property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceDetailLineShipping }
     *     
     */
    public InvoiceDetailLineShipping getInvoiceDetailLineShipping() {
        return invoiceDetailLineShipping;
    }

    /**
     * Sets the value of the invoiceDetailLineShipping property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceDetailLineShipping }
     *     
     */
    public void setInvoiceDetailLineShipping(InvoiceDetailLineShipping value) {
        this.invoiceDetailLineShipping = value;
    }

    /**
     * Gets the value of the shipNoticeIDInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ShipNoticeIDInfo }
     *     
     */
    public ShipNoticeIDInfo getShipNoticeIDInfo() {
        return shipNoticeIDInfo;
    }

    /**
     * Sets the value of the shipNoticeIDInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipNoticeIDInfo }
     *     
     */
    public void setShipNoticeIDInfo(ShipNoticeIDInfo value) {
        this.shipNoticeIDInfo = value;
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
     * Gets the value of the packaging property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the packaging property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPackaging().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Packaging }
     * 
     * 
     */
    public List<Packaging> getPackaging() {
        if (packaging == null) {
            packaging = new ArrayList<Packaging>();
        }
        return this.packaging;
    }

    /**
     * Gets the value of the invoiceDetailItemIndustry property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceDetailItemIndustry }
     *     
     */
    public InvoiceDetailItemIndustry getInvoiceDetailItemIndustry() {
        return invoiceDetailItemIndustry;
    }

    /**
     * Sets the value of the invoiceDetailItemIndustry property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceDetailItemIndustry }
     *     
     */
    public void setInvoiceDetailItemIndustry(InvoiceDetailItemIndustry value) {
        this.invoiceDetailItemIndustry = value;
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
     * Gets the value of the customsInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CustomsInfo }
     *     
     */
    public CustomsInfo getCustomsInfo() {
        return customsInfo;
    }

    /**
     * Sets the value of the customsInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomsInfo }
     *     
     */
    public void setCustomsInfo(CustomsInfo value) {
        this.customsInfo = value;
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
     * Gets the value of the compositeItemType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompositeItemType() {
        return compositeItemType;
    }

    /**
     * Sets the value of the compositeItemType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompositeItemType(String value) {
        this.compositeItemType = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
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
