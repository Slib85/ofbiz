
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Notification complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Notification">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Email" minOccurs="0"/>
 *         &lt;element ref="{}URLPost" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Notification", propOrder = {
    "email",
    "urlPost"
})
public class Notification {

    @XmlElement(name = "Email")
    protected Email email;
    @XmlElement(name = "URLPost")
    protected URLPost urlPost;

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link Email }
     *     
     */
    public Email getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link Email }
     *     
     */
    public void setEmail(Email value) {
        this.email = value;
    }

    /**
     * Gets the value of the urlPost property.
     * 
     * @return
     *     possible object is
     *     {@link URLPost }
     *     
     */
    public URLPost getURLPost() {
        return urlPost;
    }

    /**
     * Sets the value of the urlPost property.
     * 
     * @param value
     *     allowed object is
     *     {@link URLPost }
     *     
     */
    public void setURLPost(URLPost value) {
        this.urlPost = value;
    }

}
