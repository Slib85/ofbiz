
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for IntegrationStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IntegrationStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}IntegrationMessage" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="documentStatus" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="deliveryReady"/>
 *             &lt;enumeration value="customerFailed"/>
 *             &lt;enumeration value="deliveryFailed"/>
 *             &lt;enumeration value="deliveryDelayed"/>
 *             &lt;enumeration value="customerReceived"/>
 *             &lt;enumeration value="customerConfirmed"/>
 *             &lt;enumeration value="deliverySuccessful"/>
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
@XmlType(name = "IntegrationStatus", propOrder = {
    "integrationMessage"
})
public class IntegrationStatus {

    @XmlElement(name = "IntegrationMessage")
    protected IntegrationMessage integrationMessage;
    @XmlAttribute(name = "documentStatus", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String documentStatus;

    /**
     * Gets the value of the integrationMessage property.
     * 
     * @return
     *     possible object is
     *     {@link IntegrationMessage }
     *     
     */
    public IntegrationMessage getIntegrationMessage() {
        return integrationMessage;
    }

    /**
     * Sets the value of the integrationMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntegrationMessage }
     *     
     */
    public void setIntegrationMessage(IntegrationMessage value) {
        this.integrationMessage = value;
    }

    /**
     * Gets the value of the documentStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentStatus() {
        return documentStatus;
    }

    /**
     * Sets the value of the documentStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentStatus(String value) {
        this.documentStatus = value;
    }

}
