
package com.bigname.cxml.cxml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StatusUpdateRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StatusUpdateRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}DocumentReference" minOccurs="0"/>
 *         &lt;element ref="{}Status"/>
 *         &lt;sequence minOccurs="0">
 *           &lt;choice>
 *             &lt;element ref="{}PaymentStatus"/>
 *             &lt;element ref="{}SourcingStatus"/>
 *             &lt;element ref="{}InvoiceStatus"/>
 *             &lt;element ref="{}DocumentStatus"/>
 *             &lt;element ref="{}IntegrationStatus"/>
 *           &lt;/choice>
 *         &lt;/sequence>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatusUpdateRequest", propOrder = {
    "documentReference",
    "status",
    "paymentStatus",
    "sourcingStatus",
    "invoiceStatus",
    "documentStatus",
    "integrationStatus",
    "extrinsic"
})
public class StatusUpdateRequest {

    @XmlElement(name = "DocumentReference")
    protected DocumentReference documentReference;
    @XmlElement(name = "Status", required = true)
    protected Status status;
    @XmlElement(name = "PaymentStatus")
    protected PaymentStatus paymentStatus;
    @XmlElement(name = "SourcingStatus")
    protected SourcingStatus sourcingStatus;
    @XmlElement(name = "InvoiceStatus")
    protected InvoiceStatus invoiceStatus;
    @XmlElement(name = "DocumentStatus")
    protected DocumentStatus documentStatus;
    @XmlElement(name = "IntegrationStatus")
    protected IntegrationStatus integrationStatus;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;

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
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link Status }
     *     
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Status }
     *     
     */
    public void setStatus(Status value) {
        this.status = value;
    }

    /**
     * Gets the value of the paymentStatus property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentStatus }
     *     
     */
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * Sets the value of the paymentStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentStatus }
     *     
     */
    public void setPaymentStatus(PaymentStatus value) {
        this.paymentStatus = value;
    }

    /**
     * Gets the value of the sourcingStatus property.
     * 
     * @return
     *     possible object is
     *     {@link SourcingStatus }
     *     
     */
    public SourcingStatus getSourcingStatus() {
        return sourcingStatus;
    }

    /**
     * Sets the value of the sourcingStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link SourcingStatus }
     *     
     */
    public void setSourcingStatus(SourcingStatus value) {
        this.sourcingStatus = value;
    }

    /**
     * Gets the value of the invoiceStatus property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceStatus }
     *     
     */
    public InvoiceStatus getInvoiceStatus() {
        return invoiceStatus;
    }

    /**
     * Sets the value of the invoiceStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceStatus }
     *     
     */
    public void setInvoiceStatus(InvoiceStatus value) {
        this.invoiceStatus = value;
    }

    /**
     * Gets the value of the documentStatus property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentStatus }
     *     
     */
    public DocumentStatus getDocumentStatus() {
        return documentStatus;
    }

    /**
     * Sets the value of the documentStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentStatus }
     *     
     */
    public void setDocumentStatus(DocumentStatus value) {
        this.documentStatus = value;
    }

    /**
     * Gets the value of the integrationStatus property.
     * 
     * @return
     *     possible object is
     *     {@link IntegrationStatus }
     *     
     */
    public IntegrationStatus getIntegrationStatus() {
        return integrationStatus;
    }

    /**
     * Sets the value of the integrationStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntegrationStatus }
     *     
     */
    public void setIntegrationStatus(IntegrationStatus value) {
        this.integrationStatus = value;
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

}
