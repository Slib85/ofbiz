
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
 * <p>Java class for InvoiceStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}InvoiceIDInfo" minOccurs="0"/>
 *         &lt;element ref="{}PartialAmount" minOccurs="0"/>
 *         &lt;element ref="{}Comments" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="paid"/>
 *             &lt;enumeration value="paying"/>
 *             &lt;enumeration value="canceled"/>
 *             &lt;enumeration value="rejected"/>
 *             &lt;enumeration value="processing"/>
 *             &lt;enumeration value="reconciled"/>
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
@XmlType(name = "InvoiceStatus", propOrder = {
    "invoiceIDInfo",
    "partialAmount",
    "comments"
})
public class InvoiceStatus {

    @XmlElement(name = "InvoiceIDInfo")
    protected InvoiceIDInfo invoiceIDInfo;
    @XmlElement(name = "PartialAmount")
    protected PartialAmount partialAmount;
    @XmlElement(name = "Comments")
    protected List<Comments> comments;
    @XmlAttribute(name = "type", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String type;

    /**
     * Gets the value of the invoiceIDInfo property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceIDInfo }
     *     
     */
    public InvoiceIDInfo getInvoiceIDInfo() {
        return invoiceIDInfo;
    }

    /**
     * Sets the value of the invoiceIDInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceIDInfo }
     *     
     */
    public void setInvoiceIDInfo(InvoiceIDInfo value) {
        this.invoiceIDInfo = value;
    }

    /**
     * Gets the value of the partialAmount property.
     * 
     * @return
     *     possible object is
     *     {@link PartialAmount }
     *     
     */
    public PartialAmount getPartialAmount() {
        return partialAmount;
    }

    /**
     * Sets the value of the partialAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartialAmount }
     *     
     */
    public void setPartialAmount(PartialAmount value) {
        this.partialAmount = value;
    }

    /**
     * Gets the value of the comments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the comments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Comments }
     * 
     * 
     */
    public List<Comments> getComments() {
        if (comments == null) {
            comments = new ArrayList<Comments>();
        }
        return this.comments;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
