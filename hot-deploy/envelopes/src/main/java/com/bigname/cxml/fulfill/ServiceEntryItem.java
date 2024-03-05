
package com.bigname.cxml.fulfill;

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
 * <p>Java class for ServiceEntryItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceEntryItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ItemReference"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{}MasterAgreementReference"/>
 *           &lt;element ref="{}MasterAgreementIDInfo"/>
 *         &lt;/choice>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{}UnitRate"/>
 *           &lt;sequence>
 *             &lt;element ref="{}UnitOfMeasure"/>
 *             &lt;element ref="{}UnitPrice"/>
 *             &lt;element ref="{}PriceBasisQuantity" minOccurs="0"/>
 *           &lt;/sequence>
 *         &lt;/choice>
 *         &lt;element ref="{}Period" minOccurs="0"/>
 *         &lt;element ref="{}SubtotalAmount" minOccurs="0"/>
 *         &lt;element ref="{}Tax" minOccurs="0"/>
 *         &lt;element ref="{}GrossAmount" minOccurs="0"/>
 *         &lt;element ref="{}ServiceEntryDetailLineSpecialHandling" minOccurs="0"/>
 *         &lt;element ref="{}ServiceEntryDetailLineShipping" minOccurs="0"/>
 *         &lt;element ref="{}ShipNoticeIDInfo" minOccurs="0"/>
 *         &lt;element ref="{}ServiceEntryDetailDiscount" minOccurs="0"/>
 *         &lt;element ref="{}ServiceEntryItemModifications" minOccurs="0"/>
 *         &lt;element ref="{}TotalCharges" minOccurs="0"/>
 *         &lt;element ref="{}TotalAllowances" minOccurs="0"/>
 *         &lt;element ref="{}TotalAmountWithoutTax" minOccurs="0"/>
 *         &lt;element ref="{}NetAmount" minOccurs="0"/>
 *         &lt;element ref="{}Distribution" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Comments" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="serviceLineNumber" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="quantity" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="referenceDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="inspectionDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="type">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="service"/>
 *             &lt;enumeration value="material"/>
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
@XmlType(name = "ServiceEntryItem", propOrder = {
    "itemReference",
    "masterAgreementReference",
    "masterAgreementIDInfo",
    "unitRate",
    "unitOfMeasure",
    "unitPrice",
    "priceBasisQuantity",
    "period",
    "subtotalAmount",
    "tax",
    "grossAmount",
    "serviceEntryDetailLineSpecialHandling",
    "serviceEntryDetailLineShipping",
    "shipNoticeIDInfo",
    "serviceEntryDetailDiscount",
    "serviceEntryItemModifications",
    "totalCharges",
    "totalAllowances",
    "totalAmountWithoutTax",
    "netAmount",
    "distribution",
    "comments",
    "extrinsic"
})
public class ServiceEntryItem {

    @XmlElement(name = "ItemReference", required = true)
    protected ItemReference itemReference;
    @XmlElement(name = "MasterAgreementReference")
    protected MasterAgreementReference masterAgreementReference;
    @XmlElement(name = "MasterAgreementIDInfo")
    protected MasterAgreementIDInfo masterAgreementIDInfo;
    @XmlElement(name = "UnitRate")
    protected UnitRate unitRate;
    @XmlElement(name = "UnitOfMeasure")
    protected UnitOfMeasure unitOfMeasure;
    @XmlElement(name = "UnitPrice")
    protected UnitPrice unitPrice;
    @XmlElement(name = "PriceBasisQuantity")
    protected PriceBasisQuantity priceBasisQuantity;
    @XmlElement(name = "Period")
    protected Period period;
    @XmlElement(name = "SubtotalAmount")
    protected SubtotalAmount subtotalAmount;
    @XmlElement(name = "Tax")
    protected Tax tax;
    @XmlElement(name = "GrossAmount")
    protected GrossAmount grossAmount;
    @XmlElement(name = "ServiceEntryDetailLineSpecialHandling")
    protected ServiceEntryDetailLineSpecialHandling serviceEntryDetailLineSpecialHandling;
    @XmlElement(name = "ServiceEntryDetailLineShipping")
    protected ServiceEntryDetailLineShipping serviceEntryDetailLineShipping;
    @XmlElement(name = "ShipNoticeIDInfo")
    protected ShipNoticeIDInfo shipNoticeIDInfo;
    @XmlElement(name = "ServiceEntryDetailDiscount")
    protected ServiceEntryDetailDiscount serviceEntryDetailDiscount;
    @XmlElement(name = "ServiceEntryItemModifications")
    protected ServiceEntryItemModifications serviceEntryItemModifications;
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
    protected List<Comments> comments;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(name = "serviceLineNumber", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String serviceLineNumber;
    @XmlAttribute(name = "quantity", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String quantity;
    @XmlAttribute(name = "referenceDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String referenceDate;
    @XmlAttribute(name = "inspectionDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String inspectionDate;
    @XmlAttribute(name = "type")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String type;
    @XmlAttribute(name = "isAdHoc")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isAdHoc;

    /**
     * Gets the value of the itemReference property.
     * 
     * @return
     *     possible object is
     *     {@link ItemReference }
     *     
     */
    public ItemReference getItemReference() {
        return itemReference;
    }

    /**
     * Sets the value of the itemReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemReference }
     *     
     */
    public void setItemReference(ItemReference value) {
        this.itemReference = value;
    }

    /**
     * Gets the value of the masterAgreementReference property.
     * 
     * @return
     *     possible object is
     *     {@link MasterAgreementReference }
     *     
     */
    public MasterAgreementReference getMasterAgreementReference() {
        return masterAgreementReference;
    }

    /**
     * Sets the value of the masterAgreementReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link MasterAgreementReference }
     *     
     */
    public void setMasterAgreementReference(MasterAgreementReference value) {
        this.masterAgreementReference = value;
    }

    /**
     * Gets the value of the masterAgreementIDInfo property.
     * 
     * @return
     *     possible object is
     *     {@link MasterAgreementIDInfo }
     *     
     */
    public MasterAgreementIDInfo getMasterAgreementIDInfo() {
        return masterAgreementIDInfo;
    }

    /**
     * Sets the value of the masterAgreementIDInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link MasterAgreementIDInfo }
     *     
     */
    public void setMasterAgreementIDInfo(MasterAgreementIDInfo value) {
        this.masterAgreementIDInfo = value;
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
     * Gets the value of the serviceEntryDetailLineSpecialHandling property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEntryDetailLineSpecialHandling }
     *     
     */
    public ServiceEntryDetailLineSpecialHandling getServiceEntryDetailLineSpecialHandling() {
        return serviceEntryDetailLineSpecialHandling;
    }

    /**
     * Sets the value of the serviceEntryDetailLineSpecialHandling property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEntryDetailLineSpecialHandling }
     *     
     */
    public void setServiceEntryDetailLineSpecialHandling(ServiceEntryDetailLineSpecialHandling value) {
        this.serviceEntryDetailLineSpecialHandling = value;
    }

    /**
     * Gets the value of the serviceEntryDetailLineShipping property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEntryDetailLineShipping }
     *     
     */
    public ServiceEntryDetailLineShipping getServiceEntryDetailLineShipping() {
        return serviceEntryDetailLineShipping;
    }

    /**
     * Sets the value of the serviceEntryDetailLineShipping property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEntryDetailLineShipping }
     *     
     */
    public void setServiceEntryDetailLineShipping(ServiceEntryDetailLineShipping value) {
        this.serviceEntryDetailLineShipping = value;
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
     * Gets the value of the serviceEntryDetailDiscount property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEntryDetailDiscount }
     *     
     */
    public ServiceEntryDetailDiscount getServiceEntryDetailDiscount() {
        return serviceEntryDetailDiscount;
    }

    /**
     * Sets the value of the serviceEntryDetailDiscount property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEntryDetailDiscount }
     *     
     */
    public void setServiceEntryDetailDiscount(ServiceEntryDetailDiscount value) {
        this.serviceEntryDetailDiscount = value;
    }

    /**
     * Gets the value of the serviceEntryItemModifications property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEntryItemModifications }
     *     
     */
    public ServiceEntryItemModifications getServiceEntryItemModifications() {
        return serviceEntryItemModifications;
    }

    /**
     * Sets the value of the serviceEntryItemModifications property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEntryItemModifications }
     *     
     */
    public void setServiceEntryItemModifications(ServiceEntryItemModifications value) {
        this.serviceEntryItemModifications = value;
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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the comments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Comments }
     * 
     * 
     */
    public List<Comments> getComments() {
        if (comments == null) {
            comments = new ArrayList<Comments>();
        }
        return this.comments;
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
     * Gets the value of the serviceLineNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceLineNumber() {
        return serviceLineNumber;
    }

    /**
     * Sets the value of the serviceLineNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceLineNumber(String value) {
        this.serviceLineNumber = value;
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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
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
