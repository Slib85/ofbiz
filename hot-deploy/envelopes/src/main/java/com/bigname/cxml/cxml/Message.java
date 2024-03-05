
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
 * <p>Java class for Message complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Message">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Status" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element ref="{}PunchOutOrderMessage"/>
 *           &lt;element ref="{}ProviderDoneMessage"/>
 *           &lt;element ref="{}SubscriptionChangeMessage"/>
 *           &lt;element ref="{}DataAvailableMessage"/>
 *           &lt;element ref="{}SupplierChangeMessage"/>
 *           &lt;element ref="{}OrganizationChangeMessage"/>
 *           &lt;element ref="{}ProductActivityMessage"/>
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
 *       &lt;attribute name="inReplyTo" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Message", propOrder = {
    "status",
    "punchOutOrderMessage",
    "providerDoneMessage",
    "subscriptionChangeMessage",
    "dataAvailableMessage",
    "supplierChangeMessage",
    "organizationChangeMessage",
    "productActivityMessage"
})
public class Message {

    @XmlElement(name = "Status")
    protected Status status;
    @XmlElement(name = "PunchOutOrderMessage")
    protected PunchOutOrderMessage punchOutOrderMessage;
    @XmlElement(name = "ProviderDoneMessage")
    protected ProviderDoneMessage providerDoneMessage;
    @XmlElement(name = "SubscriptionChangeMessage")
    protected SubscriptionChangeMessage subscriptionChangeMessage;
    @XmlElement(name = "DataAvailableMessage")
    protected DataAvailableMessage dataAvailableMessage;
    @XmlElement(name = "SupplierChangeMessage")
    protected SupplierChangeMessage supplierChangeMessage;
    @XmlElement(name = "OrganizationChangeMessage")
    protected OrganizationChangeMessage organizationChangeMessage;
    @XmlElement(name = "ProductActivityMessage")
    protected ProductActivityMessage productActivityMessage;
    @XmlAttribute(name = "deploymentMode")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String deploymentMode;
    @XmlAttribute(name = "inReplyTo")
    @XmlSchemaType(name = "anySimpleType")
    protected String inReplyTo;
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
     * Gets the value of the punchOutOrderMessage property.
     * 
     * @return
     *     possible object is
     *     {@link PunchOutOrderMessage }
     *     
     */
    public PunchOutOrderMessage getPunchOutOrderMessage() {
        return punchOutOrderMessage;
    }

    /**
     * Sets the value of the punchOutOrderMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link PunchOutOrderMessage }
     *     
     */
    public void setPunchOutOrderMessage(PunchOutOrderMessage value) {
        this.punchOutOrderMessage = value;
    }

    /**
     * Gets the value of the providerDoneMessage property.
     * 
     * @return
     *     possible object is
     *     {@link ProviderDoneMessage }
     *     
     */
    public ProviderDoneMessage getProviderDoneMessage() {
        return providerDoneMessage;
    }

    /**
     * Sets the value of the providerDoneMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProviderDoneMessage }
     *     
     */
    public void setProviderDoneMessage(ProviderDoneMessage value) {
        this.providerDoneMessage = value;
    }

    /**
     * Gets the value of the subscriptionChangeMessage property.
     * 
     * @return
     *     possible object is
     *     {@link SubscriptionChangeMessage }
     *     
     */
    public SubscriptionChangeMessage getSubscriptionChangeMessage() {
        return subscriptionChangeMessage;
    }

    /**
     * Sets the value of the subscriptionChangeMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubscriptionChangeMessage }
     *     
     */
    public void setSubscriptionChangeMessage(SubscriptionChangeMessage value) {
        this.subscriptionChangeMessage = value;
    }

    /**
     * Gets the value of the dataAvailableMessage property.
     * 
     * @return
     *     possible object is
     *     {@link DataAvailableMessage }
     *     
     */
    public DataAvailableMessage getDataAvailableMessage() {
        return dataAvailableMessage;
    }

    /**
     * Sets the value of the dataAvailableMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataAvailableMessage }
     *     
     */
    public void setDataAvailableMessage(DataAvailableMessage value) {
        this.dataAvailableMessage = value;
    }

    /**
     * Gets the value of the supplierChangeMessage property.
     * 
     * @return
     *     possible object is
     *     {@link SupplierChangeMessage }
     *     
     */
    public SupplierChangeMessage getSupplierChangeMessage() {
        return supplierChangeMessage;
    }

    /**
     * Sets the value of the supplierChangeMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplierChangeMessage }
     *     
     */
    public void setSupplierChangeMessage(SupplierChangeMessage value) {
        this.supplierChangeMessage = value;
    }

    /**
     * Gets the value of the organizationChangeMessage property.
     * 
     * @return
     *     possible object is
     *     {@link OrganizationChangeMessage }
     *     
     */
    public OrganizationChangeMessage getOrganizationChangeMessage() {
        return organizationChangeMessage;
    }

    /**
     * Sets the value of the organizationChangeMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganizationChangeMessage }
     *     
     */
    public void setOrganizationChangeMessage(OrganizationChangeMessage value) {
        this.organizationChangeMessage = value;
    }

    /**
     * Gets the value of the productActivityMessage property.
     * 
     * @return
     *     possible object is
     *     {@link ProductActivityMessage }
     *     
     */
    public ProductActivityMessage getProductActivityMessage() {
        return productActivityMessage;
    }

    /**
     * Sets the value of the productActivityMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductActivityMessage }
     *     
     */
    public void setProductActivityMessage(ProductActivityMessage value) {
        this.productActivityMessage = value;
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
     * Gets the value of the inReplyTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInReplyTo() {
        return inReplyTo;
    }

    /**
     * Sets the value of the inReplyTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInReplyTo(String value) {
        this.inReplyTo = value;
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
