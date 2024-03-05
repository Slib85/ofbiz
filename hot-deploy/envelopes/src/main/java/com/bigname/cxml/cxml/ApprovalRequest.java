
package com.bigname.cxml.cxml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApprovalRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApprovalRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ApprovalRequestHeader"/>
 *         &lt;choice>
 *           &lt;element ref="{}AcceptanceItem" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}ApprovalItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApprovalRequest", propOrder = {
    "approvalRequestHeader",
    "acceptanceItem",
    "approvalItem"
})
public class ApprovalRequest {

    @XmlElement(name = "ApprovalRequestHeader", required = true)
    protected ApprovalRequestHeader approvalRequestHeader;
    @XmlElement(name = "AcceptanceItem")
    protected List<AcceptanceItem> acceptanceItem;
    @XmlElement(name = "ApprovalItem")
    protected List<ApprovalItem> approvalItem;

    /**
     * Gets the value of the approvalRequestHeader property.
     * 
     * @return
     *     possible object is
     *     {@link ApprovalRequestHeader }
     *     
     */
    public ApprovalRequestHeader getApprovalRequestHeader() {
        return approvalRequestHeader;
    }

    /**
     * Sets the value of the approvalRequestHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApprovalRequestHeader }
     *     
     */
    public void setApprovalRequestHeader(ApprovalRequestHeader value) {
        this.approvalRequestHeader = value;
    }

    /**
     * Gets the value of the acceptanceItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the acceptanceItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAcceptanceItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AcceptanceItem }
     * 
     * 
     */
    public List<AcceptanceItem> getAcceptanceItem() {
        if (acceptanceItem == null) {
            acceptanceItem = new ArrayList<AcceptanceItem>();
        }
        return this.acceptanceItem;
    }

    /**
     * Gets the value of the approvalItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the approvalItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getApprovalItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ApprovalItem }
     * 
     * 
     */
    public List<ApprovalItem> getApprovalItem() {
        if (approvalItem == null) {
            approvalItem = new ArrayList<ApprovalItem>();
        }
        return this.approvalItem;
    }

}
