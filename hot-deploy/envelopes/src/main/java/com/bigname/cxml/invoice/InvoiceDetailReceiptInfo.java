
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceDetailReceiptInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceDetailReceiptInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{}ReceiptReference"/>
 *         &lt;element ref="{}ReceiptIDInfo"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceDetailReceiptInfo", propOrder = {
    "receiptReference",
    "receiptIDInfo"
})
public class InvoiceDetailReceiptInfo {

    @XmlElement(name = "ReceiptReference")
    protected ReceiptReference receiptReference;
    @XmlElement(name = "ReceiptIDInfo")
    protected ReceiptIDInfo receiptIDInfo;

    /**
     * Gets the value of the receiptReference property.
     * 
     * @return
     *     possible object is
     *     {@link ReceiptReference }
     *     
     */
    public ReceiptReference getReceiptReference() {
        return receiptReference;
    }

    /**
     * Sets the value of the receiptReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceiptReference }
     *     
     */
    public void setReceiptReference(ReceiptReference value) {
        this.receiptReference = value;
    }

    /**
     * Gets the value of the receiptIDInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ReceiptIDInfo }
     *     
     */
    public ReceiptIDInfo getReceiptIDInfo() {
        return receiptIDInfo;
    }

    /**
     * Sets the value of the receiptIDInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceiptIDInfo }
     *     
     */
    public void setReceiptIDInfo(ReceiptIDInfo value) {
        this.receiptIDInfo = value;
    }

}
