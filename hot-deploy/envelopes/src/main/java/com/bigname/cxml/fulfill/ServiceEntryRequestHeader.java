
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
 * <p>Java class for ServiceEntryRequestHeader complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceEntryRequestHeader">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}PartnerContact" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{}DocumentReference"/>
 *           &lt;element ref="{}DocumentInfo"/>
 *         &lt;/choice>
 *         &lt;element ref="{}ServiceEntryDetailLineIndicator" minOccurs="0"/>
 *         &lt;element ref="{}ServiceEntryDetailShipping" minOccurs="0"/>
 *         &lt;element ref="{}ShipNoticeIDInfo" minOccurs="0"/>
 *         &lt;element ref="{}PaymentTerm" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Period" minOccurs="0"/>
 *         &lt;element ref="{}Comments" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}IdReference" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="serviceEntryDate" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="serviceEntryID" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="supplierReferenceNumber" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="operation" default="new">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="new"/>
 *             &lt;enumeration value="delete"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="isFinal">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
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
@XmlType(name = "ServiceEntryRequestHeader", propOrder = {
    "partnerContact",
    "documentReference",
    "documentInfo",
    "serviceEntryDetailLineIndicator",
    "serviceEntryDetailShipping",
    "shipNoticeIDInfo",
    "paymentTerm",
    "period",
    "comments",
    "idReference",
    "extrinsic"
})
public class ServiceEntryRequestHeader {

    @XmlElement(name = "PartnerContact")
    protected List<PartnerContact> partnerContact;
    @XmlElement(name = "DocumentReference")
    protected DocumentReference documentReference;
    @XmlElement(name = "DocumentInfo")
    protected DocumentInfo documentInfo;
    @XmlElement(name = "ServiceEntryDetailLineIndicator")
    protected ServiceEntryDetailLineIndicator serviceEntryDetailLineIndicator;
    @XmlElement(name = "ServiceEntryDetailShipping")
    protected ServiceEntryDetailShipping serviceEntryDetailShipping;
    @XmlElement(name = "ShipNoticeIDInfo")
    protected ShipNoticeIDInfo shipNoticeIDInfo;
    @XmlElement(name = "PaymentTerm")
    protected List<PaymentTerm> paymentTerm;
    @XmlElement(name = "Period")
    protected Period period;
    @XmlElement(name = "Comments")
    protected List<Comments> comments;
    @XmlElement(name = "IdReference")
    protected List<IdReference> idReference;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(name = "serviceEntryDate", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String serviceEntryDate;
    @XmlAttribute(name = "serviceEntryID", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String serviceEntryID;
    @XmlAttribute(name = "supplierReferenceNumber")
    @XmlSchemaType(name = "anySimpleType")
    protected String supplierReferenceNumber;
    @XmlAttribute(name = "operation")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String operation;
    @XmlAttribute(name = "isFinal")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isFinal;

    /**
     * Gets the value of the partnerContact property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the partnerContact property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPartnerContact().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PartnerContact }
     * 
     * 
     */
    public List<PartnerContact> getPartnerContact() {
        if (partnerContact == null) {
            partnerContact = new ArrayList<PartnerContact>();
        }
        return this.partnerContact;
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
     * Gets the value of the documentInfo property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentInfo }
     *     
     */
    public DocumentInfo getDocumentInfo() {
        return documentInfo;
    }

    /**
     * Sets the value of the documentInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentInfo }
     *     
     */
    public void setDocumentInfo(DocumentInfo value) {
        this.documentInfo = value;
    }

    /**
     * Gets the value of the serviceEntryDetailLineIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEntryDetailLineIndicator }
     *     
     */
    public ServiceEntryDetailLineIndicator getServiceEntryDetailLineIndicator() {
        return serviceEntryDetailLineIndicator;
    }

    /**
     * Sets the value of the serviceEntryDetailLineIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEntryDetailLineIndicator }
     *     
     */
    public void setServiceEntryDetailLineIndicator(ServiceEntryDetailLineIndicator value) {
        this.serviceEntryDetailLineIndicator = value;
    }

    /**
     * Gets the value of the serviceEntryDetailShipping property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEntryDetailShipping }
     *     
     */
    public ServiceEntryDetailShipping getServiceEntryDetailShipping() {
        return serviceEntryDetailShipping;
    }

    /**
     * Sets the value of the serviceEntryDetailShipping property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEntryDetailShipping }
     *     
     */
    public void setServiceEntryDetailShipping(ServiceEntryDetailShipping value) {
        this.serviceEntryDetailShipping = value;
    }

    /**
     * Gets the value of the shipNoticeIDInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ShipNoticeIDInfo }
     *     
     */
    public ShipNoticeIDInfo getShipNoticeIDInfo() {
        return shipNoticeIDInfo;
    }

    /**
     * Sets the value of the shipNoticeIDInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipNoticeIDInfo }
     *     
     */
    public void setShipNoticeIDInfo(ShipNoticeIDInfo value) {
        this.shipNoticeIDInfo = value;
    }

    /**
     * Gets the value of the paymentTerm property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentTerm property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentTerm().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentTerm }
     * 
     * 
     */
    public List<PaymentTerm> getPaymentTerm() {
        if (paymentTerm == null) {
            paymentTerm = new ArrayList<PaymentTerm>();
        }
        return this.paymentTerm;
    }

    /**
     * Gets the value of the period property.
     * 
     * @return
     *     possible object is
     *     {@link Period }
     *     
     */
    public Period getPeriod() {
        return period;
    }

    /**
     * Sets the value of the period property.
     * 
     * @param value
     *     allowed object is
     *     {@link Period }
     *     
     */
    public void setPeriod(Period value) {
        this.period = value;
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
     * Gets the value of the idReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the idReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IdReference }
     * 
     * 
     */
    public List<IdReference> getIdReference() {
        if (idReference == null) {
            idReference = new ArrayList<IdReference>();
        }
        return this.idReference;
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
     * Gets the value of the serviceEntryDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceEntryDate() {
        return serviceEntryDate;
    }

    /**
     * Sets the value of the serviceEntryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceEntryDate(String value) {
        this.serviceEntryDate = value;
    }

    /**
     * Gets the value of the serviceEntryID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceEntryID() {
        return serviceEntryID;
    }

    /**
     * Sets the value of the serviceEntryID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceEntryID(String value) {
        this.serviceEntryID = value;
    }

    /**
     * Gets the value of the supplierReferenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplierReferenceNumber() {
        return supplierReferenceNumber;
    }

    /**
     * Sets the value of the supplierReferenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplierReferenceNumber(String value) {
        this.supplierReferenceNumber = value;
    }

    /**
     * Gets the value of the operation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperation() {
        if (operation == null) {
            return "new";
        } else {
            return operation;
        }
    }

    /**
     * Sets the value of the operation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperation(String value) {
        this.operation = value;
    }

    /**
     * Gets the value of the isFinal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsFinal() {
        return isFinal;
    }

    /**
     * Sets the value of the isFinal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsFinal(String value) {
        this.isFinal = value;
    }

}
