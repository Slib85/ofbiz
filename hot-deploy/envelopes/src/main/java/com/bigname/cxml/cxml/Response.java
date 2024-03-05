
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Response">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Status"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{}ProfileResponse"/>
 *           &lt;element ref="{}PunchOutSetupResponse"/>
 *           &lt;element ref="{}ProviderSetupResponse"/>
 *           &lt;element ref="{}GetPendingResponse"/>
 *           &lt;element ref="{}SubscriptionListResponse"/>
 *           &lt;element ref="{}SubscriptionContentResponse"/>
 *           &lt;element ref="{}SupplierListResponse"/>
 *           &lt;element ref="{}SupplierDataResponse"/>
 *           &lt;element ref="{}AuthResponse"/>
 *           &lt;element ref="{}DataResponse"/>
 *           &lt;element ref="{}OrganizationDataResponse"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Response", propOrder = {
    "status",
    "profileResponse",
    "punchOutSetupResponse",
    "providerSetupResponse",
    "getPendingResponse",
    "subscriptionListResponse",
    "subscriptionContentResponse",
    "supplierListResponse",
    "supplierDataResponse",
    "authResponse",
    "dataResponse",
    "organizationDataResponse"
})
public class Response {

    @XmlElement(name = "Status", required = true)
    protected Status status;
    @XmlElement(name = "ProfileResponse")
    protected ProfileResponse profileResponse;
    @XmlElement(name = "PunchOutSetupResponse")
    protected PunchOutSetupResponse punchOutSetupResponse;
    @XmlElement(name = "ProviderSetupResponse")
    protected ProviderSetupResponse providerSetupResponse;
    @XmlElement(name = "GetPendingResponse")
    protected GetPendingResponse getPendingResponse;
    @XmlElement(name = "SubscriptionListResponse")
    protected SubscriptionListResponse subscriptionListResponse;
    @XmlElement(name = "SubscriptionContentResponse")
    protected SubscriptionContentResponse subscriptionContentResponse;
    @XmlElement(name = "SupplierListResponse")
    protected SupplierListResponse supplierListResponse;
    @XmlElement(name = "SupplierDataResponse")
    protected SupplierDataResponse supplierDataResponse;
    @XmlElement(name = "AuthResponse")
    protected AuthResponse authResponse;
    @XmlElement(name = "DataResponse")
    protected DataResponse dataResponse;
    @XmlElement(name = "OrganizationDataResponse")
    protected OrganizationDataResponse organizationDataResponse;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

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
     * Gets the value of the profileResponse property.
     * 
     * @return
     *     possible object is
     *     {@link ProfileResponse }
     *     
     */
    public ProfileResponse getProfileResponse() {
        return profileResponse;
    }

    /**
     * Sets the value of the profileResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProfileResponse }
     *     
     */
    public void setProfileResponse(ProfileResponse value) {
        this.profileResponse = value;
    }

    /**
     * Gets the value of the punchOutSetupResponse property.
     * 
     * @return
     *     possible object is
     *     {@link PunchOutSetupResponse }
     *     
     */
    public PunchOutSetupResponse getPunchOutSetupResponse() {
        return punchOutSetupResponse;
    }

    /**
     * Sets the value of the punchOutSetupResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link PunchOutSetupResponse }
     *     
     */
    public void setPunchOutSetupResponse(PunchOutSetupResponse value) {
        this.punchOutSetupResponse = value;
    }

    /**
     * Gets the value of the providerSetupResponse property.
     * 
     * @return
     *     possible object is
     *     {@link ProviderSetupResponse }
     *     
     */
    public ProviderSetupResponse getProviderSetupResponse() {
        return providerSetupResponse;
    }

    /**
     * Sets the value of the providerSetupResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProviderSetupResponse }
     *     
     */
    public void setProviderSetupResponse(ProviderSetupResponse value) {
        this.providerSetupResponse = value;
    }

    /**
     * Gets the value of the getPendingResponse property.
     * 
     * @return
     *     possible object is
     *     {@link GetPendingResponse }
     *     
     */
    public GetPendingResponse getGetPendingResponse() {
        return getPendingResponse;
    }

    /**
     * Sets the value of the getPendingResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetPendingResponse }
     *     
     */
    public void setGetPendingResponse(GetPendingResponse value) {
        this.getPendingResponse = value;
    }

    /**
     * Gets the value of the subscriptionListResponse property.
     * 
     * @return
     *     possible object is
     *     {@link SubscriptionListResponse }
     *     
     */
    public SubscriptionListResponse getSubscriptionListResponse() {
        return subscriptionListResponse;
    }

    /**
     * Sets the value of the subscriptionListResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubscriptionListResponse }
     *     
     */
    public void setSubscriptionListResponse(SubscriptionListResponse value) {
        this.subscriptionListResponse = value;
    }

    /**
     * Gets the value of the subscriptionContentResponse property.
     * 
     * @return
     *     possible object is
     *     {@link SubscriptionContentResponse }
     *     
     */
    public SubscriptionContentResponse getSubscriptionContentResponse() {
        return subscriptionContentResponse;
    }

    /**
     * Sets the value of the subscriptionContentResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubscriptionContentResponse }
     *     
     */
    public void setSubscriptionContentResponse(SubscriptionContentResponse value) {
        this.subscriptionContentResponse = value;
    }

    /**
     * Gets the value of the supplierListResponse property.
     * 
     * @return
     *     possible object is
     *     {@link SupplierListResponse }
     *     
     */
    public SupplierListResponse getSupplierListResponse() {
        return supplierListResponse;
    }

    /**
     * Sets the value of the supplierListResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplierListResponse }
     *     
     */
    public void setSupplierListResponse(SupplierListResponse value) {
        this.supplierListResponse = value;
    }

    /**
     * Gets the value of the supplierDataResponse property.
     * 
     * @return
     *     possible object is
     *     {@link SupplierDataResponse }
     *     
     */
    public SupplierDataResponse getSupplierDataResponse() {
        return supplierDataResponse;
    }

    /**
     * Sets the value of the supplierDataResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplierDataResponse }
     *     
     */
    public void setSupplierDataResponse(SupplierDataResponse value) {
        this.supplierDataResponse = value;
    }

    /**
     * Gets the value of the authResponse property.
     * 
     * @return
     *     possible object is
     *     {@link AuthResponse }
     *     
     */
    public AuthResponse getAuthResponse() {
        return authResponse;
    }

    /**
     * Sets the value of the authResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthResponse }
     *     
     */
    public void setAuthResponse(AuthResponse value) {
        this.authResponse = value;
    }

    /**
     * Gets the value of the dataResponse property.
     * 
     * @return
     *     possible object is
     *     {@link DataResponse }
     *     
     */
    public DataResponse getDataResponse() {
        return dataResponse;
    }

    /**
     * Sets the value of the dataResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataResponse }
     *     
     */
    public void setDataResponse(DataResponse value) {
        this.dataResponse = value;
    }

    /**
     * Gets the value of the organizationDataResponse property.
     * 
     * @return
     *     possible object is
     *     {@link OrganizationDataResponse }
     *     
     */
    public OrganizationDataResponse getOrganizationDataResponse() {
        return organizationDataResponse;
    }

    /**
     * Sets the value of the organizationDataResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganizationDataResponse }
     *     
     */
    public void setOrganizationDataResponse(OrganizationDataResponse value) {
        this.organizationDataResponse = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
