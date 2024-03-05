
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
 * <p>Java class for InvoiceDetailOrderSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceDetailOrderSummary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}SubtotalAmount"/>
 *         &lt;element ref="{}Period" minOccurs="0"/>
 *         &lt;element ref="{}Tax" minOccurs="0"/>
 *         &lt;element ref="{}InvoiceDetailLineSpecialHandling" minOccurs="0"/>
 *         &lt;element ref="{}InvoiceDetailLineShipping" minOccurs="0"/>
 *         &lt;element ref="{}GrossAmount" minOccurs="0"/>
 *         &lt;element ref="{}InvoiceDetailDiscount" minOccurs="0"/>
 *         &lt;element ref="{}NetAmount" minOccurs="0"/>
 *         &lt;element ref="{}Comments" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="invoiceLineNumber" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="inspectionDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceDetailOrderSummary", propOrder = {
    "subtotalAmount",
    "period",
    "tax",
    "invoiceDetailLineSpecialHandling",
    "invoiceDetailLineShipping",
    "grossAmount",
    "invoiceDetailDiscount",
    "netAmount",
    "comments",
    "extrinsic"
})
public class InvoiceDetailOrderSummary {

    @XmlElement(name = "SubtotalAmount", required = true)
    protected SubtotalAmount subtotalAmount;
    @XmlElement(name = "Period")
    protected Period period;
    @XmlElement(name = "Tax")
    protected Tax tax;
    @XmlElement(name = "InvoiceDetailLineSpecialHandling")
    protected InvoiceDetailLineSpecialHandling invoiceDetailLineSpecialHandling;
    @XmlElement(name = "InvoiceDetailLineShipping")
    protected InvoiceDetailLineShipping invoiceDetailLineShipping;
    @XmlElement(name = "GrossAmount")
    protected GrossAmount grossAmount;
    @XmlElement(name = "InvoiceDetailDiscount")
    protected InvoiceDetailDiscount invoiceDetailDiscount;
    @XmlElement(name = "NetAmount")
    protected NetAmount netAmount;
    @XmlElement(name = "Comments")
    protected Comments comments;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(name = "invoiceLineNumber", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String invoiceLineNumber;
    @XmlAttribute(name = "inspectionDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String inspectionDate;

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

}
