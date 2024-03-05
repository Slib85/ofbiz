
package com.bigname.cxml.fulfill;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for TimeCard complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TimeCard">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}OrderInfo"/>
 *         &lt;element ref="{}Contractor"/>
 *         &lt;element ref="{}ReportedTime"/>
 *         &lt;element ref="{}SubmitterInfo"/>
 *         &lt;element ref="{}ApprovalInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Comments" minOccurs="0"/>
 *         &lt;element ref="{}DocumentReference" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" default="new">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="new"/>
 *             &lt;enumeration value="delete"/>
 *             &lt;enumeration value="update"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="status" default="submitted">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="denied"/>
 *             &lt;enumeration value="approved"/>
 *             &lt;enumeration value="submitted"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="timeCardID" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeCard", propOrder = {
    "orderInfo",
    "contractor",
    "reportedTime",
    "submitterInfo",
    "approvalInfo",
    "comments",
    "documentReference"
})
public class TimeCard {

    @XmlElement(name = "OrderInfo", required = true)
    protected OrderInfo orderInfo;
    @XmlElement(name = "Contractor", required = true)
    protected Contractor contractor;
    @XmlElement(name = "ReportedTime", required = true)
    protected ReportedTime reportedTime;
    @XmlElement(name = "SubmitterInfo", required = true)
    protected SubmitterInfo submitterInfo;
    @XmlElement(name = "ApprovalInfo")
    protected List<ApprovalInfo> approvalInfo;
    @XmlElement(name = "Comments")
    protected Comments comments;
    @XmlElement(name = "DocumentReference")
    protected DocumentReference documentReference;
    @XmlAttribute(name = "type")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String type;
    @XmlAttribute(name = "status")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String status;
    @XmlAttribute(name = "timeCardID", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String timeCardID;

    /**
     * Gets the value of the orderInfo property.
     * 
     * @return
     *     possible object is
     *     {@link OrderInfo }
     *     
     */
    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    /**
     * Sets the value of the orderInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderInfo }
     *     
     */
    public void setOrderInfo(OrderInfo value) {
        this.orderInfo = value;
    }

    /**
     * Gets the value of the contractor property.
     * 
     * @return
     *     possible object is
     *     {@link Contractor }
     *     
     */
    public Contractor getContractor() {
        return contractor;
    }

    /**
     * Sets the value of the contractor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Contractor }
     *     
     */
    public void setContractor(Contractor value) {
        this.contractor = value;
    }

    /**
     * Gets the value of the reportedTime property.
     * 
     * @return
     *     possible object is
     *     {@link ReportedTime }
     *     
     */
    public ReportedTime getReportedTime() {
        return reportedTime;
    }

    /**
     * Sets the value of the reportedTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReportedTime }
     *     
     */
    public void setReportedTime(ReportedTime value) {
        this.reportedTime = value;
    }

    /**
     * Gets the value of the submitterInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SubmitterInfo }
     *     
     */
    public SubmitterInfo getSubmitterInfo() {
        return submitterInfo;
    }

    /**
     * Sets the value of the submitterInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubmitterInfo }
     *     
     */
    public void setSubmitterInfo(SubmitterInfo value) {
        this.submitterInfo = value;
    }

    /**
     * Gets the value of the approvalInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the approvalInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getApprovalInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ApprovalInfo }
     * 
     * 
     */
    public List<ApprovalInfo> getApprovalInfo() {
        if (approvalInfo == null) {
            approvalInfo = new ArrayList<ApprovalInfo>();
        }
        return this.approvalInfo;
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
     * Gets the value of the documentReference property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentReference }
     *     
     */
    public DocumentReference getDocumentReference() {
        return documentReference;
    }

    /**
     * Sets the value of the documentReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentReference }
     *     
     */
    public void setDocumentReference(DocumentReference value) {
        this.documentReference = value;
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
        if (type == null) {
            return "new";
        } else {
            return type;
        }
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

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        if (status == null) {
            return "submitted";
        } else {
            return status;
        }
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the timeCardID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeCardID() {
        return timeCardID;
    }

    /**
     * Sets the value of the timeCardID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeCardID(String value) {
        this.timeCardID = value;
    }

}
