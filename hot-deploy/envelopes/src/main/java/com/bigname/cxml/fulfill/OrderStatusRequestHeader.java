
package com.bigname.cxml.fulfill;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderStatusRequestHeader complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderStatusRequestHeader">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element ref="{}OrderReference"/>
 *           &lt;element ref="{}OrderIDInfo"/>
 *         &lt;/choice>
 *         &lt;element ref="{}Contact" maxOccurs="unbounded"/>
 *         &lt;element ref="{}Comments" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="orderStatusRequestID" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="orderStatusRequestDate" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderStatusRequestHeader", propOrder = {
    "orderReference",
    "orderIDInfo",
    "contact",
    "comments",
    "extrinsic"
})
public class OrderStatusRequestHeader {

    @XmlElement(name = "OrderReference")
    protected OrderReference orderReference;
    @XmlElement(name = "OrderIDInfo")
    protected OrderIDInfo orderIDInfo;
    @XmlElement(name = "Contact", required = true)
    protected List<Contact> contact;
    @XmlElement(name = "Comments")
    protected Comments comments;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(name = "orderStatusRequestID", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String orderStatusRequestID;
    @XmlAttribute(name = "orderStatusRequestDate", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String orderStatusRequestDate;

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
     * Gets the value of the orderIDInfo property.
     * 
     * @return
     *     possible object is
     *     {@link OrderIDInfo }
     *     
     */
    public OrderIDInfo getOrderIDInfo() {
        return orderIDInfo;
    }

    /**
     * Sets the value of the orderIDInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderIDInfo }
     *     
     */
    public void setOrderIDInfo(OrderIDInfo value) {
        this.orderIDInfo = value;
    }

    /**
     * Gets the value of the contact property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contact property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContact().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Contact }
     * 
     * 
     */
    public List<Contact> getContact() {
        if (contact == null) {
            contact = new ArrayList<Contact>();
        }
        return this.contact;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link Comments }
     *     
     */
    public Comments getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link Comments }
     *     
     */
    public void setComments(Comments value) {
        this.comments = value;
    }

    /**
     * Gets the value of the extrinsic property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extrinsic property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtrinsic().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Extrinsic }
     * 
     * 
     */
    public List<Extrinsic> getExtrinsic() {
        if (extrinsic == null) {
            extrinsic = new ArrayList<Extrinsic>();
        }
        return this.extrinsic;
    }

    /**
     * Gets the value of the orderStatusRequestID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderStatusRequestID() {
        return orderStatusRequestID;
    }

    /**
     * Sets the value of the orderStatusRequestID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderStatusRequestID(String value) {
        this.orderStatusRequestID = value;
    }

    /**
     * Gets the value of the orderStatusRequestDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderStatusRequestDate() {
        return orderStatusRequestDate;
    }

    /**
     * Sets the value of the orderStatusRequestDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderStatusRequestDate(String value) {
        this.orderStatusRequestDate = value;
    }

}
