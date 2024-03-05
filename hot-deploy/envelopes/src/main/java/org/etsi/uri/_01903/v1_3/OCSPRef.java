//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.10.04 at 05:24:23 PM EDT 
//


package org.etsi.uri._01903.v1_3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OCSPRef complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OCSPRef">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://uri.etsi.org/01903/v1.3.2#}OCSPIdentifier"/>
 *         &lt;element ref="{http://uri.etsi.org/01903/v1.3.2#}DigestAlgAndValue" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OCSPRef", namespace = "http://uri.etsi.org/01903/v1.3.2#", propOrder = {
    "ocspIdentifier",
    "digestAlgAndValue"
})
public class OCSPRef {

    @XmlElement(name = "OCSPIdentifier", namespace = "http://uri.etsi.org/01903/v1.3.2#", required = true)
    protected OCSPIdentifier ocspIdentifier;
    @XmlElement(name = "DigestAlgAndValue", namespace = "http://uri.etsi.org/01903/v1.3.2#")
    protected DigestAlgAndValue digestAlgAndValue;

    /**
     * Gets the value of the ocspIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link OCSPIdentifier }
     *     
     */
    public OCSPIdentifier getOCSPIdentifier() {
        return ocspIdentifier;
    }

    /**
     * Sets the value of the ocspIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link OCSPIdentifier }
     *     
     */
    public void setOCSPIdentifier(OCSPIdentifier value) {
        this.ocspIdentifier = value;
    }

    /**
     * Gets the value of the digestAlgAndValue property.
     * 
     * @return
     *     possible object is
     *     {@link DigestAlgAndValue }
     *     
     */
    public DigestAlgAndValue getDigestAlgAndValue() {
        return digestAlgAndValue;
    }

    /**
     * Sets the value of the digestAlgAndValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link DigestAlgAndValue }
     *     
     */
    public void setDigestAlgAndValue(DigestAlgAndValue value) {
        this.digestAlgAndValue = value;
    }

}
