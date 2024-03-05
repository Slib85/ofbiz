
package com.bigname.cxml.cxml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AuthRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuthRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Credential" maxOccurs="unbounded"/>
 *         &lt;element ref="{}X509Data" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthRequest", propOrder = {
    "credential",
    "x509Data"
})
public class AuthRequest {

    @XmlElement(name = "Credential", required = true)
    protected List<Credential> credential;
    @XmlElement(name = "X509Data")
    protected X509Data x509Data;

    /**
     * Gets the value of the credential property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the credential property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCredential().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Credential }
     * 
     * 
     */
    public List<Credential> getCredential() {
        if (credential == null) {
            credential = new ArrayList<Credential>();
        }
        return this.credential;
    }

    /**
     * Gets the value of the x509Data property.
     * 
     * @return
     *     possible object is
     *     {@link X509Data }
     *     
     */
    public X509Data getX509Data() {
        return x509Data;
    }

    /**
     * Sets the value of the x509Data property.
     * 
     * @param value
     *     allowed object is
     *     {@link X509Data }
     *     
     */
    public void setX509Data(X509Data value) {
        this.x509Data = value;
    }

}
