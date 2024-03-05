
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceDetailHeaderOrder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceDetailHeaderOrder">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}InvoiceDetailOrderInfo"/>
 *         &lt;element ref="{}InvoiceDetailOrderSummary"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceDetailHeaderOrder", propOrder = {
    "invoiceDetailOrderInfo",
    "invoiceDetailOrderSummary"
})
public class InvoiceDetailHeaderOrder {

    @XmlElement(name = "InvoiceDetailOrderInfo", required = true)
    protected InvoiceDetailOrderInfo invoiceDetailOrderInfo;
    @XmlElement(name = "InvoiceDetailOrderSummary", required = true)
    protected InvoiceDetailOrderSummary invoiceDetailOrderSummary;

    /**
     * Gets the value of the invoiceDetailOrderInfo property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceDetailOrderInfo }
     *     
     */
    public InvoiceDetailOrderInfo getInvoiceDetailOrderInfo() {
        return invoiceDetailOrderInfo;
    }

    /**
     * Sets the value of the invoiceDetailOrderInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceDetailOrderInfo }
     *     
     */
    public void setInvoiceDetailOrderInfo(InvoiceDetailOrderInfo value) {
        this.invoiceDetailOrderInfo = value;
    }

    /**
     * Gets the value of the invoiceDetailOrderSummary property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceDetailOrderSummary }
     *     
     */
    public InvoiceDetailOrderSummary getInvoiceDetailOrderSummary() {
        return invoiceDetailOrderSummary;
    }

    /**
     * Sets the value of the invoiceDetailOrderSummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceDetailOrderSummary }
     *     
     */
    public void setInvoiceDetailOrderSummary(InvoiceDetailOrderSummary value) {
        this.invoiceDetailOrderSummary = value;
    }

}
