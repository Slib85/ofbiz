
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceDetailSummaryIndustry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceDetailSummaryIndustry">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}InvoiceDetailSummaryRetail" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceDetailSummaryIndustry", propOrder = {
    "invoiceDetailSummaryRetail"
})
public class InvoiceDetailSummaryIndustry {

    @XmlElement(name = "InvoiceDetailSummaryRetail")
    protected InvoiceDetailSummaryRetail invoiceDetailSummaryRetail;

    /**
     * Gets the value of the invoiceDetailSummaryRetail property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceDetailSummaryRetail }
     *     
     */
    public InvoiceDetailSummaryRetail getInvoiceDetailSummaryRetail() {
        return invoiceDetailSummaryRetail;
    }

    /**
     * Sets the value of the invoiceDetailSummaryRetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceDetailSummaryRetail }
     *     
     */
    public void setInvoiceDetailSummaryRetail(InvoiceDetailSummaryRetail value) {
        this.invoiceDetailSummaryRetail = value;
    }

}
