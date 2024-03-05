
package com.bigname.cxml.cxml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PurchaseRequisition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PurchaseRequisition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}PurchaseRequisitionHeader"/>
 *         &lt;element ref="{}ItemIn" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PurchaseRequisition", propOrder = {
    "purchaseRequisitionHeader",
    "itemIn"
})
public class PurchaseRequisition {

    @XmlElement(name = "PurchaseRequisitionHeader", required = true)
    protected PurchaseRequisitionHeader purchaseRequisitionHeader;
    @XmlElement(name = "ItemIn")
    protected List<ItemIn> itemIn;

    /**
     * Gets the value of the purchaseRequisitionHeader property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseRequisitionHeader }
     *     
     */
    public PurchaseRequisitionHeader getPurchaseRequisitionHeader() {
        return purchaseRequisitionHeader;
    }

    /**
     * Sets the value of the purchaseRequisitionHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseRequisitionHeader }
     *     
     */
    public void setPurchaseRequisitionHeader(PurchaseRequisitionHeader value) {
        this.purchaseRequisitionHeader = value;
    }

    /**
     * Gets the value of the itemIn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itemIn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItemIn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemIn }
     * 
     * 
     */
    public List<ItemIn> getItemIn() {
        if (itemIn == null) {
            itemIn = new ArrayList<ItemIn>();
        }
        return this.itemIn;
    }

}
