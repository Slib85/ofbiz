
package com.bigname.cxml.fulfill;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderStatusRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderStatusRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}OrderStatusRequestHeader"/>
 *         &lt;element ref="{}OrderStatusRequestItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderStatusRequest", propOrder = {
    "orderStatusRequestHeader",
    "orderStatusRequestItem"
})
public class OrderStatusRequest {

    @XmlElement(name = "OrderStatusRequestHeader", required = true)
    protected OrderStatusRequestHeader orderStatusRequestHeader;
    @XmlElement(name = "OrderStatusRequestItem")
    protected List<OrderStatusRequestItem> orderStatusRequestItem;

    /**
     * Gets the value of the orderStatusRequestHeader property.
     * 
     * @return
     *     possible object is
     *     {@link OrderStatusRequestHeader }
     *     
     */
    public OrderStatusRequestHeader getOrderStatusRequestHeader() {
        return orderStatusRequestHeader;
    }

    /**
     * Sets the value of the orderStatusRequestHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderStatusRequestHeader }
     *     
     */
    public void setOrderStatusRequestHeader(OrderStatusRequestHeader value) {
        this.orderStatusRequestHeader = value;
    }

    /**
     * Gets the value of the orderStatusRequestItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderStatusRequestItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderStatusRequestItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderStatusRequestItem }
     * 
     * 
     */
    public List<OrderStatusRequestItem> getOrderStatusRequestItem() {
        if (orderStatusRequestItem == null) {
            orderStatusRequestItem = new ArrayList<OrderStatusRequestItem>();
        }
        return this.orderStatusRequestItem;
    }

}
