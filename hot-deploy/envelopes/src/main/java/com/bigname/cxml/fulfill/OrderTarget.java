
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderTarget complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderTarget">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{}Phone"/>
 *         &lt;element ref="{}Email"/>
 *         &lt;element ref="{}Fax"/>
 *         &lt;element ref="{}URL"/>
 *         &lt;element ref="{}OtherOrderTarget"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderTarget", propOrder = {
    "phone",
    "email",
    "fax",
    "url",
    "otherOrderTarget"
})
public class OrderTarget {

    @XmlElement(name = "Phone")
    protected Phone phone;
    @XmlElement(name = "Email")
    protected Email email;
    @XmlElement(name = "Fax")
    protected Fax fax;
    @XmlElement(name = "URL")
    protected URL url;
    @XmlElement(name = "OtherOrderTarget")
    protected OtherOrderTarget otherOrderTarget;

    /**
     * Gets the value of the phone property.
     * 
     * @return
     *     possible object is
     *     {@link Phone }
     *     
     */
    public Phone getPhone() {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     * 
     * @param value
     *     allowed object is
     *     {@link Phone }
     *     
     */
    public void setPhone(Phone value) {
        this.phone = value;
    }

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
     * Gets the value of the fax property.
     * 
     * @return
     *     possible object is
     *     {@link Fax }
     *     
     */
    public Fax getFax() {
        return fax;
    }

    /**
     * Sets the value of the fax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Fax }
     *     
     */
    public void setFax(Fax value) {
        this.fax = value;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link URL }
     *     
     */
    public URL getURL() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link URL }
     *     
     */
    public void setURL(URL value) {
        this.url = value;
    }

    /**
     * Gets the value of the otherOrderTarget property.
     * 
     * @return
     *     possible object is
     *     {@link OtherOrderTarget }
     *     
     */
    public OtherOrderTarget getOtherOrderTarget() {
        return otherOrderTarget;
    }

    /**
     * Sets the value of the otherOrderTarget property.
     * 
     * @param value
     *     allowed object is
     *     {@link OtherOrderTarget }
     *     
     */
    public void setOtherOrderTarget(OtherOrderTarget value) {
        this.otherOrderTarget = value;
    }

}
