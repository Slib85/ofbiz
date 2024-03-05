
package com.bigname.cxml.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}OrderRequestHeader"/>
 *         &lt;element ref="{}ItemOut" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderRequest", propOrder = {
    "orderRequestHeader",
    "itemOut"
})
public class OrderRequest {

    @XmlElement(name = "OrderRequestHeader", required = true)
    protected OrderRequestHeader orderRequestHeader;
    @XmlElement(name = "ItemOut", required = true)
    protected List<ItemOut> itemOut;

    /**
     * Gets the value of the orderRequestHeader property.
     * 
     * @return
     *     possible object is
     *     {@link OrderRequestHeader }
     *     
     */
    public OrderRequestHeader getOrderRequestHeader() {
        return orderRequestHeader;
    }

    /**
     * Sets the value of the orderRequestHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderRequestHeader }
     *     
     */
    public void setOrderRequestHeader(OrderRequestHeader value) {
        this.orderRequestHeader = value;
    }

    /**
     * Gets the value of the itemOut property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itemOut property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItemOut().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemOut }
     * 
     * 
     */
    public List<ItemOut> getItemOut() {
        if (itemOut == null) {
            itemOut = new ArrayList<ItemOut>();
        }
        return this.itemOut;
    }

}
