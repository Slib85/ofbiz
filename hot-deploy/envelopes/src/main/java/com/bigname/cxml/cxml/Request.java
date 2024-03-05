
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
 * <p>Java class for Request complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Request">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element ref="{}ProfileRequest"/>
 *           &lt;element ref="{}OrderRequest"/>
 *           &lt;element ref="{}MasterAgreementRequest"/>
 *           &lt;element ref="{}PurchaseRequisitionRequest"/>
 *           &lt;element ref="{}PunchOutSetupRequest"/>
 *           &lt;element ref="{}ProviderSetupRequest"/>
 *           &lt;element ref="{}StatusUpdateRequest"/>
 *           &lt;element ref="{}GetPendingRequest"/>
 *           &lt;element ref="{}SubscriptionListRequest"/>
 *           &lt;element ref="{}SubscriptionContentRequest"/>
 *           &lt;element ref="{}SupplierListRequest"/>
 *           &lt;element ref="{}SupplierDataRequest"/>
 *           &lt;element ref="{}SubscriptionStatusUpdateRequest"/>
 *           &lt;element ref="{}CopyRequest"/>
 *           &lt;element ref="{}CatalogUploadRequest"/>
 *           &lt;element ref="{}AuthRequest"/>
 *           &lt;element ref="{}DataRequest"/>
 *           &lt;element ref="{}OrganizationDataRequest"/>
 *           &lt;element ref="{}ApprovalRequest"/>
 *           &lt;element ref="{}QualityNotificationRequest"/>
 *           &lt;element ref="{}QualityInspectionRequest"/>
 *           &lt;element ref="{}QualityInspectionResultRequest"/>
 *           &lt;element ref="{}QualityInspectionDecisionRequest"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="deploymentMode" default="production">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="test"/>
 *             &lt;enumeration value="production"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Request", propOrder = {
    "profileRequest",
    "orderRequest",
    "masterAgreementRequest",
    "purchaseRequisitionRequest",
    "punchOutSetupRequest",
    "providerSetupRequest",
    "statusUpdateRequest",
    "getPendingRequest",
    "subscriptionListRequest",
    "subscriptionContentRequest",
    "supplierListRequest",
    "supplierDataRequest",
    "subscriptionStatusUpdateRequest",
    "copyRequest",
    "catalogUploadRequest",
    "authRequest",
    "dataRequest",
    "organizationDataRequest",
    "approvalRequest",
    "qualityNotificationRequest",
    "qualityInspectionRequest",
    "qualityInspectionResultRequest",
    "qualityInspectionDecisionRequest"
})
public class Request {

    @XmlElement(name = "ProfileRequest")
    protected ProfileRequest profileRequest;
    @XmlElement(name = "OrderRequest")
    protected OrderRequest orderRequest;
    @XmlElement(name = "MasterAgreementRequest")
    protected MasterAgreementRequest masterAgreementRequest;
    @XmlElement(name = "PurchaseRequisitionRequest")
    protected PurchaseRequisitionRequest purchaseRequisitionRequest;
    @XmlElement(name = "PunchOutSetupRequest")
    protected PunchOutSetupRequest punchOutSetupRequest;
    @XmlElement(name = "ProviderSetupRequest")
    protected ProviderSetupRequest providerSetupRequest;
    @XmlElement(name = "StatusUpdateRequest")
    protected StatusUpdateRequest statusUpdateRequest;
    @XmlElement(name = "GetPendingRequest")
    protected GetPendingRequest getPendingRequest;
    @XmlElement(name = "SubscriptionListRequest")
    protected SubscriptionListRequest subscriptionListRequest;
    @XmlElement(name = "SubscriptionContentRequest")
    protected SubscriptionContentRequest subscriptionContentRequest;
    @XmlElement(name = "SupplierListRequest")
    protected SupplierListRequest supplierListRequest;
    @XmlElement(name = "SupplierDataRequest")
    protected SupplierDataRequest supplierDataRequest;
    @XmlElement(name = "SubscriptionStatusUpdateRequest")
    protected SubscriptionStatusUpdateRequest subscriptionStatusUpdateRequest;
    @XmlElement(name = "CopyRequest")
    protected CopyRequest copyRequest;
    @XmlElement(name = "CatalogUploadRequest")
    protected CatalogUploadRequest catalogUploadRequest;
    @XmlElement(name = "AuthRequest")
    protected AuthRequest authRequest;
    @XmlElement(name = "DataRequest")
    protected DataRequest dataRequest;
    @XmlElement(name = "OrganizationDataRequest")
    protected OrganizationDataRequest organizationDataRequest;
    @XmlElement(name = "ApprovalRequest")
    protected ApprovalRequest approvalRequest;
    @XmlElement(name = "QualityNotificationRequest")
    protected QualityNotificationRequest qualityNotificationRequest;
    @XmlElement(name = "QualityInspectionRequest")
    protected QualityInspectionRequest qualityInspectionRequest;
    @XmlElement(name = "QualityInspectionResultRequest")
    protected QualityInspectionResultRequest qualityInspectionResultRequest;
    @XmlElement(name = "QualityInspectionDecisionRequest")
    protected QualityInspectionDecisionRequest qualityInspectionDecisionRequest;
    @XmlAttribute(name = "deploymentMode")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String deploymentMode;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the profileRequest property.
     * 
     * @return
     *     possible object is
     *     {@link ProfileRequest }
     *     
     */
    public ProfileRequest getProfileRequest() {
        return profileRequest;
    }

    /**
     * Sets the value of the profileRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProfileRequest }
     *     
     */
    public void setProfileRequest(ProfileRequest value) {
        this.profileRequest = value;
    }

    /**
     * Gets the value of the orderRequest property.
     * 
     * @return
     *     possible object is
     *     {@link OrderRequest }
     *     
     */
    public OrderRequest getOrderRequest() {
        return orderRequest;
    }

    /**
     * Sets the value of the orderRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderRequest }
     *     
     */
    public void setOrderRequest(OrderRequest value) {
        this.orderRequest = value;
    }

    /**
     * Gets the value of the masterAgreementRequest property.
     * 
     * @return
     *     possible object is
     *     {@link MasterAgreementRequest }
     *     
     */
    public MasterAgreementRequest getMasterAgreementRequest() {
        return masterAgreementRequest;
    }

    /**
     * Sets the value of the masterAgreementRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link MasterAgreementRequest }
     *     
     */
    public void setMasterAgreementRequest(MasterAgreementRequest value) {
        this.masterAgreementRequest = value;
    }

    /**
     * Gets the value of the purchaseRequisitionRequest property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseRequisitionRequest }
     *     
     */
    public PurchaseRequisitionRequest getPurchaseRequisitionRequest() {
        return purchaseRequisitionRequest;
    }

    /**
     * Sets the value of the purchaseRequisitionRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseRequisitionRequest }
     *     
     */
    public void setPurchaseRequisitionRequest(PurchaseRequisitionRequest value) {
        this.purchaseRequisitionRequest = value;
    }

    /**
     * Gets the value of the punchOutSetupRequest property.
     * 
     * @return
     *     possible object is
     *     {@link PunchOutSetupRequest }
     *     
     */
    public PunchOutSetupRequest getPunchOutSetupRequest() {
        return punchOutSetupRequest;
    }

    /**
     * Sets the value of the punchOutSetupRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link PunchOutSetupRequest }
     *     
     */
    public void setPunchOutSetupRequest(PunchOutSetupRequest value) {
        this.punchOutSetupRequest = value;
    }

    /**
     * Gets the value of the providerSetupRequest property.
     * 
     * @return
     *     possible object is
     *     {@link ProviderSetupRequest }
     *     
     */
    public ProviderSetupRequest getProviderSetupRequest() {
        return providerSetupRequest;
    }

    /**
     * Sets the value of the providerSetupRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProviderSetupRequest }
     *     
     */
    public void setProviderSetupRequest(ProviderSetupRequest value) {
        this.providerSetupRequest = value;
    }

    /**
     * Gets the value of the statusUpdateRequest property.
     * 
     * @return
     *     possible object is
     *     {@link StatusUpdateRequest }
     *     
     */
    public StatusUpdateRequest getStatusUpdateRequest() {
        return statusUpdateRequest;
    }

    /**
     * Sets the value of the statusUpdateRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusUpdateRequest }
     *     
     */
    public void setStatusUpdateRequest(StatusUpdateRequest value) {
        this.statusUpdateRequest = value;
    }

    /**
     * Gets the value of the getPendingRequest property.
     * 
     * @return
     *     possible object is
     *     {@link GetPendingRequest }
     *     
     */
    public GetPendingRequest getGetPendingRequest() {
        return getPendingRequest;
    }

    /**
     * Sets the value of the getPendingRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetPendingRequest }
     *     
     */
    public void setGetPendingRequest(GetPendingRequest value) {
        this.getPendingRequest = value;
    }

    /**
     * Gets the value of the subscriptionListRequest property.
     * 
     * @return
     *     possible object is
     *     {@link SubscriptionListRequest }
     *     
     */
    public SubscriptionListRequest getSubscriptionListRequest() {
        return subscriptionListRequest;
    }

    /**
     * Sets the value of the subscriptionListRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubscriptionListRequest }
     *     
     */
    public void setSubscriptionListRequest(SubscriptionListRequest value) {
        this.subscriptionListRequest = value;
    }

    /**
     * Gets the value of the subscriptionContentRequest property.
     * 
     * @return
     *     possible object is
     *     {@link SubscriptionContentRequest }
     *     
     */
    public SubscriptionContentRequest getSubscriptionContentRequest() {
        return subscriptionContentRequest;
    }

    /**
     * Sets the value of the subscriptionContentRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubscriptionContentRequest }
     *     
     */
    public void setSubscriptionContentRequest(SubscriptionContentRequest value) {
        this.subscriptionContentRequest = value;
    }

    /**
     * Gets the value of the supplierListRequest property.
     * 
     * @return
     *     possible object is
     *     {@link SupplierListRequest }
     *     
     */
    public SupplierListRequest getSupplierListRequest() {
        return supplierListRequest;
    }

    /**
     * Sets the value of the supplierListRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplierListRequest }
     *     
     */
    public void setSupplierListRequest(SupplierListRequest value) {
        this.supplierListRequest = value;
    }

    /**
     * Gets the value of the supplierDataRequest property.
     * 
     * @return
     *     possible object is
     *     {@link SupplierDataRequest }
     *     
     */
    public SupplierDataRequest getSupplierDataRequest() {
        return supplierDataRequest;
    }

    /**
     * Sets the value of the supplierDataRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplierDataRequest }
     *     
     */
    public void setSupplierDataRequest(SupplierDataRequest value) {
        this.supplierDataRequest = value;
    }

    /**
     * Gets the value of the subscriptionStatusUpdateRequest property.
     * 
     * @return
     *     possible object is
     *     {@link SubscriptionStatusUpdateRequest }
     *     
     */
    public SubscriptionStatusUpdateRequest getSubscriptionStatusUpdateRequest() {
        return subscriptionStatusUpdateRequest;
    }

    /**
     * Sets the value of the subscriptionStatusUpdateRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubscriptionStatusUpdateRequest }
     *     
     */
    public void setSubscriptionStatusUpdateRequest(SubscriptionStatusUpdateRequest value) {
        this.subscriptionStatusUpdateRequest = value;
    }

    /**
     * Gets the value of the copyRequest property.
     * 
     * @return
     *     possible object is
     *     {@link CopyRequest }
     *     
     */
    public CopyRequest getCopyRequest() {
        return copyRequest;
    }

    /**
     * Sets the value of the copyRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link CopyRequest }
     *     
     */
    public void setCopyRequest(CopyRequest value) {
        this.copyRequest = value;
    }

    /**
     * Gets the value of the catalogUploadRequest property.
     * 
     * @return
     *     possible object is
     *     {@link CatalogUploadRequest }
     *     
     */
    public CatalogUploadRequest getCatalogUploadRequest() {
        return catalogUploadRequest;
    }

    /**
     * Sets the value of the catalogUploadRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link CatalogUploadRequest }
     *     
     */
    public void setCatalogUploadRequest(CatalogUploadRequest value) {
        this.catalogUploadRequest = value;
    }

    /**
     * Gets the value of the authRequest property.
     * 
     * @return
     *     possible object is
     *     {@link AuthRequest }
     *     
     */
    public AuthRequest getAuthRequest() {
        return authRequest;
    }

    /**
     * Sets the value of the authRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthRequest }
     *     
     */
    public void setAuthRequest(AuthRequest value) {
        this.authRequest = value;
    }

    /**
     * Gets the value of the dataRequest property.
     * 
     * @return
     *     possible object is
     *     {@link DataRequest }
     *     
     */
    public DataRequest getDataRequest() {
        return dataRequest;
    }

    /**
     * Sets the value of the dataRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataRequest }
     *     
     */
    public void setDataRequest(DataRequest value) {
        this.dataRequest = value;
    }

    /**
     * Gets the value of the organizationDataRequest property.
     * 
     * @return
     *     possible object is
     *     {@link OrganizationDataRequest }
     *     
     */
    public OrganizationDataRequest getOrganizationDataRequest() {
        return organizationDataRequest;
    }

    /**
     * Sets the value of the organizationDataRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganizationDataRequest }
     *     
     */
    public void setOrganizationDataRequest(OrganizationDataRequest value) {
        this.organizationDataRequest = value;
    }

    /**
     * Gets the value of the approvalRequest property.
     * 
     * @return
     *     possible object is
     *     {@link ApprovalRequest }
     *     
     */
    public ApprovalRequest getApprovalRequest() {
        return approvalRequest;
    }

    /**
     * Sets the value of the approvalRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApprovalRequest }
     *     
     */
    public void setApprovalRequest(ApprovalRequest value) {
        this.approvalRequest = value;
    }

    /**
     * Gets the value of the qualityNotificationRequest property.
     * 
     * @return
     *     possible object is
     *     {@link QualityNotificationRequest }
     *     
     */
    public QualityNotificationRequest getQualityNotificationRequest() {
        return qualityNotificationRequest;
    }

    /**
     * Sets the value of the qualityNotificationRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link QualityNotificationRequest }
     *     
     */
    public void setQualityNotificationRequest(QualityNotificationRequest value) {
        this.qualityNotificationRequest = value;
    }

    /**
     * Gets the value of the qualityInspectionRequest property.
     * 
     * @return
     *     possible object is
     *     {@link QualityInspectionRequest }
     *     
     */
    public QualityInspectionRequest getQualityInspectionRequest() {
        return qualityInspectionRequest;
    }

    /**
     * Sets the value of the qualityInspectionRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link QualityInspectionRequest }
     *     
     */
    public void setQualityInspectionRequest(QualityInspectionRequest value) {
        this.qualityInspectionRequest = value;
    }

    /**
     * Gets the value of the qualityInspectionResultRequest property.
     * 
     * @return
     *     possible object is
     *     {@link QualityInspectionResultRequest }
     *     
     */
    public QualityInspectionResultRequest getQualityInspectionResultRequest() {
        return qualityInspectionResultRequest;
    }

    /**
     * Sets the value of the qualityInspectionResultRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link QualityInspectionResultRequest }
     *     
     */
    public void setQualityInspectionResultRequest(QualityInspectionResultRequest value) {
        this.qualityInspectionResultRequest = value;
    }

    /**
     * Gets the value of the qualityInspectionDecisionRequest property.
     * 
     * @return
     *     possible object is
     *     {@link QualityInspectionDecisionRequest }
     *     
     */
    public QualityInspectionDecisionRequest getQualityInspectionDecisionRequest() {
        return qualityInspectionDecisionRequest;
    }

    /**
     * Sets the value of the qualityInspectionDecisionRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link QualityInspectionDecisionRequest }
     *     
     */
    public void setQualityInspectionDecisionRequest(QualityInspectionDecisionRequest value) {
        this.qualityInspectionDecisionRequest = value;
    }

    /**
     * Gets the value of the deploymentMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeploymentMode() {
        if (deploymentMode == null) {
            return "production";
        } else {
            return deploymentMode;
        }
    }

    /**
     * Sets the value of the deploymentMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeploymentMode(String value) {
        this.deploymentMode = value;
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
