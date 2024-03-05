
package com.bigname.cxml.fulfill;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesOrderRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesOrderRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}SalesOrderHeader"/>
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
@XmlType(name = "SalesOrderRequest", propOrder = {
    "salesOrderHeader",
    "itemIn"
})
public class SalesOrderRequest {

    @XmlElement(name = "SalesOrderHeader", required = true)
    protected SalesOrderHeader salesOrderHeader;
    @XmlElement(name = "ItemIn")
    protected List<ItemIn> itemIn;

    /**
     * Gets the value of the salesOrderHeader property.
     * 
     * @return
     *     possible object is
     *     {@link SalesOrderHeader }
     *     
     */
    public SalesOrderHeader getSalesOrderHeader() {
        return salesOrderHeader;
    }

    /**
     * Sets the value of the salesOrderHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesOrderHeader }
     *     
     */
    public void setSalesOrderHeader(SalesOrderHeader value) {
        this.salesOrderHeader = value;
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
