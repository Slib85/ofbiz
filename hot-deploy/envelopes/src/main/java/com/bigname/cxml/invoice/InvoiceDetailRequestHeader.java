
package com.bigname.cxml.invoice;

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
 * <p>Java class for InvoiceDetailRequestHeader complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceDetailRequestHeader">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}InvoiceDetailHeaderIndicator"/>
 *         &lt;element ref="{}InvoiceDetailLineIndicator"/>
 *         &lt;element ref="{}InvoicePartner" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}DocumentReference" minOccurs="0"/>
 *         &lt;element ref="{}InvoiceIDInfo" minOccurs="0"/>
 *         &lt;element ref="{}PaymentProposalIDInfo" minOccurs="0"/>
 *         &lt;element ref="{}InvoiceDetailShipping" minOccurs="0"/>
 *         &lt;element ref="{}ShipNoticeIDInfo" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element ref="{}InvoiceDetailPaymentTerm" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}PaymentTerm" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;element ref="{}Period" minOccurs="0"/>
 *         &lt;element ref="{}Comments" minOccurs="0"/>
 *         &lt;element ref="{}IdReference" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="invoiceID" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="isInformationOnly">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="purpose" default="standard">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="standard"/>
 *             &lt;enumeration value="debitMemo"/>
 *             &lt;enumeration value="creditMemo"/>
 *             &lt;enumeration value="lineLevelDebitMemo"/>
 *             &lt;enumeration value="lineLevelCreditMemo"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="operation" default="new">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="new"/>
 *             &lt;enumeration value="delete"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="invoiceDate" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="invoiceOrigin">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="buyer"/>
 *             &lt;enumeration value="supplier"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="isERS">
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
@XmlType(name = "InvoiceDetailRequestHeader", propOrder = {
    "invoiceDetailHeaderIndicator",
    "invoiceDetailLineIndicator",
    "invoicePartner",
    "documentReference",
    "invoiceIDInfo",
    "paymentProposalIDInfo",
    "invoiceDetailShipping",
    "shipNoticeIDInfo",
    "invoiceDetailPaymentTerm",
    "paymentTerm",
    "period",
    "comments",
    "idReference",
    "extrinsic"
})
public class InvoiceDetailRequestHeader {

    @XmlElement(name = "InvoiceDetailHeaderIndicator", required = true)
    protected InvoiceDetailHeaderIndicator invoiceDetailHeaderIndicator;
    @XmlElement(name = "InvoiceDetailLineIndicator", required = true)
    protected InvoiceDetailLineIndicator invoiceDetailLineIndicator;
    @XmlElement(name = "InvoicePartner")
    protected List<InvoicePartner> invoicePartner;
    @XmlElement(name = "DocumentReference")
    protected DocumentReference documentReference;
    @XmlElement(name = "InvoiceIDInfo")
    protected InvoiceIDInfo invoiceIDInfo;
    @XmlElement(name = "PaymentProposalIDInfo")
    protected PaymentProposalIDInfo paymentProposalIDInfo;
    @XmlElement(name = "InvoiceDetailShipping")
    protected InvoiceDetailShipping invoiceDetailShipping;
    @XmlElement(name = "ShipNoticeIDInfo")
    protected ShipNoticeIDInfo shipNoticeIDInfo;
    @XmlElement(name = "InvoiceDetailPaymentTerm")
    protected List<InvoiceDetailPaymentTerm> invoiceDetailPaymentTerm;
    @XmlElement(name = "PaymentTerm")
    protected List<PaymentTerm> paymentTerm;
    @XmlElement(name = "Period")
    protected Period period;
    @XmlElement(name = "Comments")
    protected Comments comments;
    @XmlElement(name = "IdReference")
    protected List<IdReference> idReference;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(name = "invoiceID", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String invoiceID;
    @XmlAttribute(name = "isInformationOnly")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isInformationOnly;
    @XmlAttribute(name = "purpose")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String purpose;
    @XmlAttribute(name = "operation")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String operation;
    @XmlAttribute(name = "invoiceDate", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String invoiceDate;
    @XmlAttribute(name = "invoiceOrigin")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String invoiceOrigin;
    @XmlAttribute(name = "isERS")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isERS;

    /**
     * Gets the value of the invoiceDetailHeaderIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceDetailHeaderIndicator }
     *     
     */
    public InvoiceDetailHeaderIndicator getInvoiceDetailHeaderIndicator() {
        return invoiceDetailHeaderIndicator;
    }

    /**
     * Sets the value of the invoiceDetailHeaderIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceDetailHeaderIndicator }
     *     
     */
    public void setInvoiceDetailHeaderIndicator(InvoiceDetailHeaderIndicator value) {
        this.invoiceDetailHeaderIndicator = value;
    }

    /**
     * Gets the value of the invoiceDetailLineIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceDetailLineIndicator }
     *     
     */
    public InvoiceDetailLineIndicator getInvoiceDetailLineIndicator() {
        return invoiceDetailLineIndicator;
    }

    /**
     * Sets the value of the invoiceDetailLineIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceDetailLineIndicator }
     *     
     */
    public void setInvoiceDetailLineIndicator(InvoiceDetailLineIndicator value) {
        this.invoiceDetailLineIndicator = value;
    }

    /**
     * Gets the value of the invoicePartner property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the invoicePartner property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvoicePartner().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InvoicePartner }
     * 
     * 
     */
    public List<InvoicePartner> getInvoicePartner() {
        if (invoicePartner == null) {
            invoicePartner = new ArrayList<InvoicePartner>();
        }
        return this.invoicePartner;
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
     * Gets the value of the paymentProposalIDInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentProposalIDInfo }
     *     
     */
    public PaymentProposalIDInfo getPaymentProposalIDInfo() {
        return paymentProposalIDInfo;
    }

    /**
     * Sets the value of the paymentProposalIDInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentProposalIDInfo }
     *     
     */
    public void setPaymentProposalIDInfo(PaymentProposalIDInfo value) {
        this.paymentProposalIDInfo = value;
    }

    /**
     * Gets the value of the invoiceDetailShipping property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceDetailShipping }
     *     
     */
    public InvoiceDetailShipping getInvoiceDetailShipping() {
        return invoiceDetailShipping;
    }

    /**
     * Sets the value of the invoiceDetailShipping property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceDetailShipping }
     *     
     */
    public void setInvoiceDetailShipping(InvoiceDetailShipping value) {
        this.invoiceDetailShipping = value;
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
     * Gets the value of the invoiceDetailPaymentTerm property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the invoiceDetailPaymentTerm property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvoiceDetailPaymentTerm().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InvoiceDetailPaymentTerm }
     * 
     * 
     */
    public List<InvoiceDetailPaymentTerm> getInvoiceDetailPaymentTerm() {
        if (invoiceDetailPaymentTerm == null) {
            invoiceDetailPaymentTerm = new ArrayList<InvoiceDetailPaymentTerm>();
        }
        return this.invoiceDetailPaymentTerm;
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
     * Gets the value of the invoiceID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceID() {
        return invoiceID;
    }

    /**
     * Sets the value of the invoiceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceID(String value) {
        this.invoiceID = value;
    }

    /**
     * Gets the value of the isInformationOnly property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsInformationOnly() {
        return isInformationOnly;
    }

    /**
     * Sets the value of the isInformationOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsInformationOnly(String value) {
        this.isInformationOnly = value;
    }

    /**
     * Gets the value of the purpose property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurpose() {
        if (purpose == null) {
            return "standard";
        } else {
            return purpose;
        }
    }

    /**
     * Sets the value of the purpose property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurpose(String value) {
        this.purpose = value;
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
     * Gets the value of the invoiceDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * Sets the value of the invoiceDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceDate(String value) {
        this.invoiceDate = value;
    }

    /**
     * Gets the value of the invoiceOrigin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceOrigin() {
        return invoiceOrigin;
    }

    /**
     * Sets the value of the invoiceOrigin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceOrigin(String value) {
        this.invoiceOrigin = value;
    }

    /**
     * Gets the value of the isERS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsERS() {
        return isERS;
    }

    /**
     * Sets the value of the isERS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsERS(String value) {
        this.isERS = value;
    }

}
