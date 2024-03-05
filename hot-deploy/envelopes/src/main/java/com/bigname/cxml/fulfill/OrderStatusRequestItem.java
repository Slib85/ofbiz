
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for OrderStatusRequestItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderStatusRequestItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ItemReference"/>
 *         &lt;element ref="{}Comments" minOccurs="0"/>
 *         &lt;element ref="{}Priority" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="rescheduleRequest">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="in"/>
 *             &lt;enumeration value="out"/>
 *             &lt;enumeration value="cancel"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="rescheduleDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderStatusRequestItem", propOrder = {
    "itemReference",
    "comments",
    "priority"
})
public class OrderStatusRequestItem {

    @XmlElement(name = "ItemReference", required = true)
    protected ItemReference itemReference;
    @XmlElement(name = "Comments")
    protected Comments comments;
    @XmlElement(name = "Priority")
    protected Priority priority;
    @XmlAttribute(name = "rescheduleRequest")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String rescheduleRequest;
    @XmlAttribute(name = "rescheduleDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String rescheduleDate;

    /**
     * Gets the value of the itemReference property.
     * 
     * @return
     *     possible object is
     *     {@link ItemReference }
     *     
     */
    public ItemReference getItemReference() {
        return itemReference;
    }

    /**
     * Sets the value of the itemReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemReference }
     *     
     */
    public void setItemReference(ItemReference value) {
        this.itemReference = value;
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
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link Priority }
     *     
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Priority }
     *     
     */
    public void setPriority(Priority value) {
        this.priority = value;
    }

    /**
     * Gets the value of the rescheduleRequest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRescheduleRequest() {
        return rescheduleRequest;
    }

    /**
     * Sets the value of the rescheduleRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRescheduleRequest(String value) {
        this.rescheduleRequest = value;
    }

    /**
     * Gets the value of the rescheduleDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRescheduleDate() {
        return rescheduleDate;
    }

    /**
     * Sets the value of the rescheduleDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRescheduleDate(String value) {
        this.rescheduleDate = value;
    }

}
