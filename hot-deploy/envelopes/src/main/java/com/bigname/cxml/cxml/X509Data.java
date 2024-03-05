
package com.bigname.cxml.cxml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for X509Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="X509Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element ref="{}X509IssuerSerial"/>
 *           &lt;element ref="{}X509SKI"/>
 *           &lt;element ref="{}X509SubjectName"/>
 *           &lt;element ref="{}X509Certificate"/>
 *         &lt;/choice>
 *         &lt;element ref="{}X509CRL"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "X509Data", propOrder = {
    "x509IssuerSerialOrX509SKIOrX509SubjectName",
    "x509CRL"
})
public class X509Data {

    @XmlElements({
        @XmlElement(name = "X509IssuerSerial", type = X509IssuerSerial.class),
        @XmlElement(name = "X509SKI", type = X509SKI.class),
        @XmlElement(name = "X509SubjectName", type = X509SubjectName.class),
        @XmlElement(name = "X509Certificate", type = X509Certificate.class)
    })
    protected List<Object> x509IssuerSerialOrX509SKIOrX509SubjectName;
    @XmlElement(name = "X509CRL")
    protected X509CRL x509CRL;

    /**
     * Gets the value of the x509IssuerSerialOrX509SKIOrX509SubjectName property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the x509IssuerSerialOrX509SKIOrX509SubjectName property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getX509IssuerSerialOrX509SKIOrX509SubjectName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link X509IssuerSerial }
     * {@link X509SKI }
     * {@link X509SubjectName }
     * {@link X509Certificate }
     * 
     * 
     */
    public List<Object> getX509IssuerSerialOrX509SKIOrX509SubjectName() {
        if (x509IssuerSerialOrX509SKIOrX509SubjectName == null) {
            x509IssuerSerialOrX509SKIOrX509SubjectName = new ArrayList<Object>();
        }
        return this.x509IssuerSerialOrX509SKIOrX509SubjectName;
    }

    /**
     * Gets the value of the x509CRL property.
     * 
     * @return
     *     possible object is
     *     {@link X509CRL }
     *     
     */
    public X509CRL getX509CRL() {
        return x509CRL;
    }

    /**
     * Sets the value of the x509CRL property.
     * 
     * @param value
     *     allowed object is
     *     {@link X509CRL }
     *     
     */
    public void setX509CRL(X509CRL value) {
        this.x509CRL = value;
    }

}
