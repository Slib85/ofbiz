
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for X509IssuerSerial complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="X509IssuerSerial">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}X509IssuerName"/>
 *         &lt;element ref="{}X509SerialNumber"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "X509IssuerSerial", propOrder = {
    "x509IssuerName",
    "x509SerialNumber"
})
public class X509IssuerSerial {

    @XmlElement(name = "X509IssuerName", required = true)
    protected X509IssuerName x509IssuerName;
    @XmlElement(name = "X509SerialNumber", required = true)
    protected X509SerialNumber x509SerialNumber;

    /**
     * Gets the value of the x509IssuerName property.
     * 
     * @return
     *     possible object is
     *     {@link X509IssuerName }
     *     
     */
    public X509IssuerName getX509IssuerName() {
        return x509IssuerName;
    }

    /**
     * Sets the value of the x509IssuerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link X509IssuerName }
     *     
     */
    public void setX509IssuerName(X509IssuerName value) {
        this.x509IssuerName = value;
    }

    /**
     * Gets the value of the x509SerialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link X509SerialNumber }
     *     
     */
    public X509SerialNumber getX509SerialNumber() {
        return x509SerialNumber;
    }

    /**
     * Sets the value of the x509SerialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link X509SerialNumber }
     *     
     */
    public void setX509SerialNumber(X509SerialNumber value) {
        this.x509SerialNumber = value;
    }

}
