
package com.bigname.cxml.invoice;

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
 *           &lt;element ref="{}InvoiceDetailRequest"/>
 *           &lt;element ref="{}ProviderSetupRequest"/>
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
    "invoiceDetailRequest",
    "providerSetupRequest"
})
public class Request {

    @XmlElement(name = "InvoiceDetailRequest")
    protected InvoiceDetailRequest invoiceDetailRequest;
    @XmlElement(name = "ProviderSetupRequest")
    protected ProviderSetupRequest providerSetupRequest;
    @XmlAttribute(name = "deploymentMode")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String deploymentMode;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the invoiceDetailRequest property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceDetailRequest }
     *     
     */
    public InvoiceDetailRequest getInvoiceDetailRequest() {
        return invoiceDetailRequest;
    }

    /**
     * Sets the value of the invoiceDetailRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceDetailRequest }
     *     
     */
    public void setInvoiceDetailRequest(InvoiceDetailRequest value) {
        this.invoiceDetailRequest = value;
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
