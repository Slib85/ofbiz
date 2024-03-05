
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceEntrySummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceEntrySummary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}SubtotalAmount"/>
 *         &lt;element ref="{}Tax" minOccurs="0"/>
 *         &lt;element ref="{}SpecialHandlingAmount" minOccurs="0"/>
 *         &lt;element ref="{}ShippingAmount" minOccurs="0"/>
 *         &lt;element ref="{}GrossAmount" minOccurs="0"/>
 *         &lt;element ref="{}ServiceEntryDetailDiscount" minOccurs="0"/>
 *         &lt;element ref="{}ServiceEntryHeaderModifications" minOccurs="0"/>
 *         &lt;element ref="{}ServiceEntrySummaryLineItemModifications" minOccurs="0"/>
 *         &lt;element ref="{}TotalCharges" minOccurs="0"/>
 *         &lt;element ref="{}TotalAllowances" minOccurs="0"/>
 *         &lt;element ref="{}TotalAmountWithoutTax" minOccurs="0"/>
 *         &lt;element ref="{}NetAmount" minOccurs="0"/>
 *         &lt;element ref="{}DepositAmount" minOccurs="0"/>
 *         &lt;element ref="{}DueAmount" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceEntrySummary", propOrder = {
    "subtotalAmount",
    "tax",
    "specialHandlingAmount",
    "shippingAmount",
    "grossAmount",
    "serviceEntryDetailDiscount",
    "serviceEntryHeaderModifications",
    "serviceEntrySummaryLineItemModifications",
    "totalCharges",
    "totalAllowances",
    "totalAmountWithoutTax",
    "netAmount",
    "depositAmount",
    "dueAmount"
})
public class ServiceEntrySummary {

    @XmlElement(name = "SubtotalAmount", required = true)
    protected SubtotalAmount subtotalAmount;
    @XmlElement(name = "Tax")
    protected Tax tax;
    @XmlElement(name = "SpecialHandlingAmount")
    protected SpecialHandlingAmount specialHandlingAmount;
    @XmlElement(name = "ShippingAmount")
    protected ShippingAmount shippingAmount;
    @XmlElement(name = "GrossAmount")
    protected GrossAmount grossAmount;
    @XmlElement(name = "ServiceEntryDetailDiscount")
    protected ServiceEntryDetailDiscount serviceEntryDetailDiscount;
    @XmlElement(name = "ServiceEntryHeaderModifications")
    protected ServiceEntryHeaderModifications serviceEntryHeaderModifications;
    @XmlElement(name = "ServiceEntrySummaryLineItemModifications")
    protected ServiceEntrySummaryLineItemModifications serviceEntrySummaryLineItemModifications;
    @XmlElement(name = "TotalCharges")
    protected TotalCharges totalCharges;
    @XmlElement(name = "TotalAllowances")
    protected TotalAllowances totalAllowances;
    @XmlElement(name = "TotalAmountWithoutTax")
    protected TotalAmountWithoutTax totalAmountWithoutTax;
    @XmlElement(name = "NetAmount")
    protected NetAmount netAmount;
    @XmlElement(name = "DepositAmount")
    protected DepositAmount depositAmount;
    @XmlElement(name = "DueAmount")
    protected DueAmount dueAmount;

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
     * Gets the value of the specialHandlingAmount property.
     * 
     * @return
     *     possible object is
     *     {@link SpecialHandlingAmount }
     *     
     */
    public SpecialHandlingAmount getSpecialHandlingAmount() {
        return specialHandlingAmount;
    }

    /**
     * Sets the value of the specialHandlingAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpecialHandlingAmount }
     *     
     */
    public void setSpecialHandlingAmount(SpecialHandlingAmount value) {
        this.specialHandlingAmount = value;
    }

    /**
     * Gets the value of the shippingAmount property.
     * 
     * @return
     *     possible object is
     *     {@link ShippingAmount }
     *     
     */
    public ShippingAmount getShippingAmount() {
        return shippingAmount;
    }

    /**
     * Sets the value of the shippingAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShippingAmount }
     *     
     */
    public void setShippingAmount(ShippingAmount value) {
        this.shippingAmount = value;
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
     * Gets the value of the serviceEntryHeaderModifications property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEntryHeaderModifications }
     *     
     */
    public ServiceEntryHeaderModifications getServiceEntryHeaderModifications() {
        return serviceEntryHeaderModifications;
    }

    /**
     * Sets the value of the serviceEntryHeaderModifications property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEntryHeaderModifications }
     *     
     */
    public void setServiceEntryHeaderModifications(ServiceEntryHeaderModifications value) {
        this.serviceEntryHeaderModifications = value;
    }

    /**
     * Gets the value of the serviceEntrySummaryLineItemModifications property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEntrySummaryLineItemModifications }
     *     
     */
    public ServiceEntrySummaryLineItemModifications getServiceEntrySummaryLineItemModifications() {
        return serviceEntrySummaryLineItemModifications;
    }

    /**
     * Sets the value of the serviceEntrySummaryLineItemModifications property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEntrySummaryLineItemModifications }
     *     
     */
    public void setServiceEntrySummaryLineItemModifications(ServiceEntrySummaryLineItemModifications value) {
        this.serviceEntrySummaryLineItemModifications = value;
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
     * Gets the value of the depositAmount property.
     * 
     * @return
     *     possible object is
     *     {@link DepositAmount }
     *     
     */
    public DepositAmount getDepositAmount() {
        return depositAmount;
    }

    /**
     * Sets the value of the depositAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepositAmount }
     *     
     */
    public void setDepositAmount(DepositAmount value) {
        this.depositAmount = value;
    }

    /**
     * Gets the value of the dueAmount property.
     * 
     * @return
     *     possible object is
     *     {@link DueAmount }
     *     
     */
    public DueAmount getDueAmount() {
        return dueAmount;
    }

    /**
     * Sets the value of the dueAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link DueAmount }
     *     
     */
    public void setDueAmount(DueAmount value) {
        this.dueAmount = value;
    }

}
