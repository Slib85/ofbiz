
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PayableInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PayableInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{}PayableInvoiceInfo"/>
 *         &lt;element ref="{}PayableOrderInfo"/>
 *         &lt;element ref="{}PayableMasterAgreementInfo"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayableInfo", propOrder = {
    "payableInvoiceInfo",
    "payableOrderInfo",
    "payableMasterAgreementInfo"
})
public class PayableInfo {

    @XmlElement(name = "PayableInvoiceInfo")
    protected PayableInvoiceInfo payableInvoiceInfo;
    @XmlElement(name = "PayableOrderInfo")
    protected PayableOrderInfo payableOrderInfo;
    @XmlElement(name = "PayableMasterAgreementInfo")
    protected PayableMasterAgreementInfo payableMasterAgreementInfo;

    /**
     * Gets the value of the payableInvoiceInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PayableInvoiceInfo }
     *     
     */
    public PayableInvoiceInfo getPayableInvoiceInfo() {
        return payableInvoiceInfo;
    }

    /**
     * Sets the value of the payableInvoiceInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayableInvoiceInfo }
     *     
     */
    public void setPayableInvoiceInfo(PayableInvoiceInfo value) {
        this.payableInvoiceInfo = value;
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
