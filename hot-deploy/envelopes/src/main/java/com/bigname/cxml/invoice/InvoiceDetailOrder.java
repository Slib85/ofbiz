
package com.bigname.cxml.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceDetailOrder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceDetailOrder">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}InvoiceDetailOrderInfo"/>
 *         &lt;element ref="{}InvoiceDetailReceiptInfo" minOccurs="0"/>
 *         &lt;element ref="{}InvoiceDetailShipNoticeInfo" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element ref="{}InvoiceDetailItem"/>
 *           &lt;element ref="{}InvoiceDetailServiceItem"/>
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
@XmlType(name = "InvoiceDetailOrder", propOrder = {
    "invoiceDetailOrderInfo",
    "invoiceDetailReceiptInfo",
    "invoiceDetailShipNoticeInfo",
    "invoiceDetailItemOrInvoiceDetailServiceItem"
})
public class InvoiceDetailOrder {

    @XmlElement(name = "InvoiceDetailOrderInfo", required = true)
    protected InvoiceDetailOrderInfo invoiceDetailOrderInfo;
    @XmlElement(name = "InvoiceDetailReceiptInfo")
    protected InvoiceDetailReceiptInfo invoiceDetailReceiptInfo;
    @XmlElement(name = "InvoiceDetailShipNoticeInfo")
    protected InvoiceDetailShipNoticeInfo invoiceDetailShipNoticeInfo;
    @XmlElements({
        @XmlElement(name = "InvoiceDetailItem", type = InvoiceDetailItem.class),
        @XmlElement(name = "InvoiceDetailServiceItem", type = InvoiceDetailServiceItem.class)
    })
    protected List<Object> invoiceDetailItemOrInvoiceDetailServiceItem;

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
     * Gets the value of the invoiceDetailReceiptInfo property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceDetailReceiptInfo }
     *     
     */
    public InvoiceDetailReceiptInfo getInvoiceDetailReceiptInfo() {
        return invoiceDetailReceiptInfo;
    }

    /**
     * Sets the value of the invoiceDetailReceiptInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceDetailReceiptInfo }
     *     
     */
    public void setInvoiceDetailReceiptInfo(InvoiceDetailReceiptInfo value) {
        this.invoiceDetailReceiptInfo = value;
    }

    /**
     * Gets the value of the invoiceDetailShipNoticeInfo property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceDetailShipNoticeInfo }
     *     
     */
    public InvoiceDetailShipNoticeInfo getInvoiceDetailShipNoticeInfo() {
        return invoiceDetailShipNoticeInfo;
    }

    /**
     * Sets the value of the invoiceDetailShipNoticeInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceDetailShipNoticeInfo }
     *     
     */
    public void setInvoiceDetailShipNoticeInfo(InvoiceDetailShipNoticeInfo value) {
        this.invoiceDetailShipNoticeInfo = value;
    }

    /**
     * Gets the value of the invoiceDetailItemOrInvoiceDetailServiceItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the invoiceDetailItemOrInvoiceDetailServiceItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvoiceDetailItemOrInvoiceDetailServiceItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InvoiceDetailItem }
     * {@link InvoiceDetailServiceItem }
     * 
     * 
     */
    public List<Object> getInvoiceDetailItemOrInvoiceDetailServiceItem() {
        if (invoiceDetailItemOrInvoiceDetailServiceItem == null) {
            invoiceDetailItemOrInvoiceDetailServiceItem = new ArrayList<Object>();
        }
        return this.invoiceDetailItemOrInvoiceDetailServiceItem;
    }

}
