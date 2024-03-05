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
 * <p>Java class for SignaturePolicyIdentifier complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SignaturePolicyIdentifier">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{http://uri.etsi.org/01903/v1.3.2#}SignaturePolicyId"/>
 *         &lt;element ref="{http://uri.etsi.org/01903/v1.3.2#}SignaturePolicyImplied"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignaturePolicyIdentifier", namespace = "http://uri.etsi.org/01903/v1.3.2#", propOrder = {
    "signaturePolicyId",
    "signaturePolicyImplied"
})
public class SignaturePolicyIdentifier {

    @XmlElement(name = "SignaturePolicyId", namespace = "http://uri.etsi.org/01903/v1.3.2#")
    protected SignaturePolicyId signaturePolicyId;
    @XmlElement(name = "SignaturePolicyImplied", namespace = "http://uri.etsi.org/01903/v1.3.2#")
    protected SignaturePolicyImplied signaturePolicyImplied;

    /**
     * Gets the value of the signaturePolicyId property.
     * 
     * @return
     *     possible object is
     *     {@link SignaturePolicyId }
     *     
     */
    public SignaturePolicyId getSignaturePolicyId() {
        return signaturePolicyId;
    }

    /**
     * Sets the value of the signaturePolicyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignaturePolicyId }
     *     
     */
    public void setSignaturePolicyId(SignaturePolicyId value) {
        this.signaturePolicyId = value;
    }

    /**
     * Gets the value of the signaturePolicyImplied property.
     * 
     * @return
     *     possible object is
     *     {@link SignaturePolicyImplied }
     *     
     */
    public SignaturePolicyImplied getSignaturePolicyImplied() {
        return signaturePolicyImplied;
    }

    /**
     * Sets the value of the signaturePolicyImplied property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignaturePolicyImplied }
     *     
     */
    public void setSignaturePolicyImplied(SignaturePolicyImplied value) {
        this.signaturePolicyImplied = value;
    }

}
