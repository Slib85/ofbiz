
package com.bigname.cxml.fulfill;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ReceiptOrder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReceiptOrder">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ReceiptOrderInfo"/>
 *         &lt;element ref="{}ReceiptItem" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="closeForReceiving">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReceiptOrder", propOrder = {
    "receiptOrderInfo",
    "receiptItem"
})
public class ReceiptOrder {

    @XmlElement(name = "ReceiptOrderInfo", required = true)
    protected ReceiptOrderInfo receiptOrderInfo;
    @XmlElement(name = "ReceiptItem", required = true)
    protected List<ReceiptItem> receiptItem;
    @XmlAttribute(name = "closeForReceiving")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String closeForReceiving;

    /**
     * Gets the value of the receiptOrderInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ReceiptOrderInfo }
     *     
     */
    public ReceiptOrderInfo getReceiptOrderInfo() {
        return receiptOrderInfo;
    }

    /**
     * Sets the value of the receiptOrderInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceiptOrderInfo }
     *     
     */
    public void setReceiptOrderInfo(ReceiptOrderInfo value) {
        this.receiptOrderInfo = value;
    }

    /**
     * Gets the value of the receiptItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the receiptItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReceiptItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReceiptItem }
     * 
     * 
     */
    public List<ReceiptItem> getReceiptItem() {
        if (receiptItem == null) {
            receiptItem = new ArrayList<ReceiptItem>();
        }
        return this.receiptItem;
    }

    /**
     * Gets the value of the closeForReceiving property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCloseForReceiving() {
        return closeForReceiving;
    }

    /**
     * Sets the value of the closeForReceiving property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCloseForReceiving(String value) {
        this.closeForReceiving = value;
    }

}
