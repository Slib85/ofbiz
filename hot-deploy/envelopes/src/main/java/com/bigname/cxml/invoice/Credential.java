
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for Credential complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Credential">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Identity"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{}SharedSecret"/>
 *           &lt;element ref="{}DigitalSignature"/>
 *           &lt;element ref="{}CredentialMac"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="domain" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="type">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="marketplace"/>
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
@XmlType(name = "Credential", propOrder = {
    "identity",
    "sharedSecret",
    "digitalSignature",
    "credentialMac"
})
public class Credential {

    @XmlElement(name = "Identity", required = true)
    protected Identity identity;
    @XmlElement(name = "SharedSecret")
    protected SharedSecret sharedSecret;
    @XmlElement(name = "DigitalSignature")
    protected DigitalSignature digitalSignature;
    @XmlElement(name = "CredentialMac")
    protected CredentialMac credentialMac;
    @XmlAttribute(name = "domain", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String domain;
    @XmlAttribute(name = "type")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String type;

    /**
     * Gets the value of the identity property.
     * 
     * @return
     *     possible object is
     *     {@link Identity }
     *     
     */
    public Identity getIdentity() {
        return identity;
    }

    /**
     * Sets the value of the identity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Identity }
     *     
     */
    public void setIdentity(Identity value) {
        this.identity = value;
    }

    /**
     * Gets the value of the sharedSecret property.
     * 
     * @return
     *     possible object is
     *     {@link SharedSecret }
     *     
     */
    public SharedSecret getSharedSecret() {
        return sharedSecret;
    }

    /**
     * Sets the value of the sharedSecret property.
     * 
     * @param value
     *     allowed object is
     *     {@link SharedSecret }
     *     
     */
    public void setSharedSecret(SharedSecret value) {
        this.sharedSecret = value;
    }

    /**
     * Gets the value of the digitalSignature property.
     * 
     * @return
     *     possible object is
     *     {@link DigitalSignature }
     *     
     */
    public DigitalSignature getDigitalSignature() {
        return digitalSignature;
    }

    /**
     * Sets the value of the digitalSignature property.
     * 
     * @param value
     *     allowed object is
     *     {@link DigitalSignature }
     *     
     */
    public void setDigitalSignature(DigitalSignature value) {
        this.digitalSignature = value;
    }

    /**
     * Gets the value of the credentialMac property.
     * 
     * @return
     *     possible object is
     *     {@link CredentialMac }
     *     
     */
    public CredentialMac getCredentialMac() {
        return credentialMac;
    }

    /**
     * Sets the value of the credentialMac property.
     * 
     * @param value
     *     allowed object is
     *     {@link CredentialMac }
     *     
     */
    public void setCredentialMac(CredentialMac value) {
        this.credentialMac = value;
    }

    /**
     * Gets the value of the domain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Sets the value of the domain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomain(String value) {
        this.domain = value;
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
        return type;
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

}
