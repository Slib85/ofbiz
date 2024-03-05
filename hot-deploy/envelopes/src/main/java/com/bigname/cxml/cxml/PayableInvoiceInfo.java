
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PayableInvoiceInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PayableInvoiceInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element ref="{}InvoiceReference"/>
 *           &lt;element ref="{}InvoiceIDInfo"/>
 *         &lt;/choice>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{}PayableOrderInfo"/>
 *           &lt;element ref="{}PayableMasterAgreementInfo"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayableInvoiceInfo", propOrder = {
    "invoiceReference",
    "invoiceIDInfo",
    "payableOrderInfo",
    "payableMasterAgreementInfo"
})
public class PayableInvoiceInfo {

    @XmlElement(name = "InvoiceReference")
    protected InvoiceReference invoiceReference;
    @XmlElement(name = "InvoiceIDInfo")
    protected InvoiceIDInfo invoiceIDInfo;
    @XmlElement(name = "PayableOrderInfo")
    protected PayableOrderInfo payableOrderInfo;
    @XmlElement(name = "PayableMasterAgreementInfo")
    protected PayableMasterAgreementInfo payableMasterAgreementInfo;

    /**
     * Gets the value of the invoiceReference property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceReference }
     *     
     */
    public InvoiceReference getInvoiceReference() {
        return invoiceReference;
    }

    /**
     * Sets the value of the invoiceReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceReference }
     *     
     */
    public void setInvoiceReference(InvoiceReference value) {
        this.invoiceReference = value;
    }

    /**
     * Gets the value of the invoiceIDInfo property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceIDInfo }
     *     
     */
    public InvoiceIDInfo getInvoiceIDInfo() {
        return invoiceIDInfo;
    }

    /**
     * Sets the value of the invoiceIDInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceIDInfo }
     *     
     */
    public void setInvoiceIDInfo(InvoiceIDInfo value) {
        this.invoiceIDInfo = value;
    }

    /**
     * Gets the value of the payableOrderInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PayableOrderInfo }
     *     
     */
    public PayableOrderInfo getPayableOrderInfo() {
        return payableOrderInfo;
    }

    /**
     * Sets the value of the payableOrderInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayableOrderInfo }
     *     
     */
    public void setPayableOrderInfo(PayableOrderInfo value) {
        this.payableOrderInfo = value;
    }

    /**
     * Gets the value of the payableMasterAgreementInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PayableMasterAgreementInfo }
     *     
     */
    public PayableMasterAgreementInfo getPayableMasterAgreementInfo() {
        return payableMasterAgreementInfo;
    }

    /**
     * Sets the value of the payableMasterAgreementInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayableMasterAgreementInfo }
     *     
     */
    public void setPayableMasterAgreementInfo(PayableMasterAgreementInfo value) {
        this.payableMasterAgreementInfo = value;
    }

}
