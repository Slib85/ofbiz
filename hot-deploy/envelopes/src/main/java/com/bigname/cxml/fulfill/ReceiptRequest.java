
package com.bigname.cxml.fulfill;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReceiptRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReceiptRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ReceiptRequestHeader"/>
 *         &lt;element ref="{}ReceiptOrder" maxOccurs="unbounded"/>
 *         &lt;element ref="{}Total"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReceiptRequest", propOrder = {
    "receiptRequestHeader",
    "receiptOrder",
    "total"
})
public class ReceiptRequest {

    @XmlElement(name = "ReceiptRequestHeader", required = true)
    protected ReceiptRequestHeader receiptRequestHeader;
    @XmlElement(name = "ReceiptOrder", required = true)
    protected List<ReceiptOrder> receiptOrder;
    @XmlElement(name = "Total", required = true)
    protected Total total;

    /**
     * Gets the value of the receiptRequestHeader property.
     * 
     * @return
     *     possible object is
     *     {@link ReceiptRequestHeader }
     *     
     */
    public ReceiptRequestHeader getReceiptRequestHeader() {
        return receiptRequestHeader;
    }

    /**
     * Sets the value of the receiptRequestHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceiptRequestHeader }
     *     
     */
    public void setReceiptRequestHeader(ReceiptRequestHeader value) {
        this.receiptRequestHeader = value;
    }

    /**
     * Gets the value of the receiptOrder property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the receiptOrder property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReceiptOrder().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReceiptOrder }
     * 
     * 
     */
    public List<ReceiptOrder> getReceiptOrder() {
        if (receiptOrder == null) {
            receiptOrder = new ArrayList<ReceiptOrder>();
        }
        return this.receiptOrder;
    }

    /**
     * Gets the value of the total property.
     * 
     * @return
     *     possible object is
     *     {@link Total }
     *     
     */
    public Total getTotal() {
        return total;
    }

    /**
     * Sets the value of the total property.
     * 
     * @param value
     *     allowed object is
     *     {@link Total }
     *     
     */
    public void setTotal(Total value) {
        this.total = value;
    }

}
