
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceDetailItemReferenceIndustry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceDetailItemReferenceIndustry">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}InvoiceDetailItemReferenceRetail" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceDetailItemReferenceIndustry", propOrder = {
    "invoiceDetailItemReferenceRetail"
})
public class InvoiceDetailItemReferenceIndustry {

    @XmlElement(name = "InvoiceDetailItemReferenceRetail")
    protected InvoiceDetailItemReferenceRetail invoiceDetailItemReferenceRetail;

    /**
     * Gets the value of the invoiceDetailItemReferenceRetail property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceDetailItemReferenceRetail }
     *     
     */
    public InvoiceDetailItemReferenceRetail getInvoiceDetailItemReferenceRetail() {
        return invoiceDetailItemReferenceRetail;
    }

    /**
     * Sets the value of the invoiceDetailItemReferenceRetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceDetailItemReferenceRetail }
     *     
     */
    public void setInvoiceDetailItemReferenceRetail(InvoiceDetailItemReferenceRetail value) {
        this.invoiceDetailItemReferenceRetail = value;
    }

}
