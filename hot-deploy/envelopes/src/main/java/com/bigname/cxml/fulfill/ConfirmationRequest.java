
package com.bigname.cxml.fulfill;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConfirmationRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConfirmationRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ConfirmationHeader"/>
 *         &lt;element ref="{}OrderReference"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element ref="{}OrderStatusRequestReference"/>
 *           &lt;element ref="{}OrderStatusRequestIDInfo"/>
 *         &lt;/choice>
 *         &lt;element ref="{}ConfirmationItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfirmationRequest", propOrder = {
    "confirmationHeader",
    "orderReference",
    "orderStatusRequestReferenceOrOrderStatusRequestIDInfo",
    "confirmationItem"
})
public class ConfirmationRequest {

    @XmlElement(name = "ConfirmationHeader", required = true)
    protected ConfirmationHeader confirmationHeader;
    @XmlElement(name = "OrderReference", required = true)
    protected OrderReference orderReference;
    @XmlElements({
        @XmlElement(name = "OrderStatusRequestReference", type = OrderStatusRequestReference.class),
        @XmlElement(name = "OrderStatusRequestIDInfo", type = OrderStatusRequestIDInfo.class)
    })
    protected List<Object> orderStatusRequestReferenceOrOrderStatusRequestIDInfo;
    @XmlElement(name = "ConfirmationItem")
    protected List<ConfirmationItem> confirmationItem;

    /**
     * Gets the value of the confirmationHeader property.
     * 
     * @return
     *     possible object is
     *     {@link ConfirmationHeader }
     *     
     */
    public ConfirmationHeader getConfirmationHeader() {
        return confirmationHeader;
    }

    /**
     * Sets the value of the confirmationHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConfirmationHeader }
     *     
     */
    public void setConfirmationHeader(ConfirmationHeader value) {
        this.confirmationHeader = value;
    }

    /**
     * Gets the value of the orderReference property.
     * 
     * @return
     *     possible object is
     *     {@link OrderReference }
     *     
     */
    public OrderReference getOrderReference() {
        return orderReference;
    }

    /**
     * Sets the value of the orderReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderReference }
     *     
     */
    public void setOrderReference(OrderReference value) {
        this.orderReference = value;
    }

    /**
     * Gets the value of the orderStatusRequestReferenceOrOrderStatusRequestIDInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderStatusRequestReferenceOrOrderStatusRequestIDInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderStatusRequestReferenceOrOrderStatusRequestIDInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderStatusRequestReference }
     * {@link OrderStatusRequestIDInfo }
     * 
     * 
     */
    public List<Object> getOrderStatusRequestReferenceOrOrderStatusRequestIDInfo() {
        if (orderStatusRequestReferenceOrOrderStatusRequestIDInfo == null) {
            orderStatusRequestReferenceOrOrderStatusRequestIDInfo = new ArrayList<Object>();
        }
        return this.orderStatusRequestReferenceOrOrderStatusRequestIDInfo;
    }

    /**
     * Gets the value of the confirmationItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the confirmationItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConfirmationItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConfirmationItem }
     * 
     * 
     */
    public List<ConfirmationItem> getConfirmationItem() {
        if (confirmationItem == null) {
            confirmationItem = new ArrayList<ConfirmationItem>();
        }
        return this.confirmationItem;
    }

}
