
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TaxExemption complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxExemption">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ExemptReason" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="exemptCode" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxExemption", propOrder = {
    "exemptReason"
})
public class TaxExemption {

    @XmlElement(name = "ExemptReason")
    protected ExemptReason exemptReason;
    @XmlAttribute(name = "exemptCode", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String exemptCode;

    /**
     * Gets the value of the exemptReason property.
     * 
     * @return
     *     possible object is
     *     {@link ExemptReason }
     *     
     */
    public ExemptReason getExemptReason() {
        return exemptReason;
    }

    /**
     * Sets the value of the exemptReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExemptReason }
     *     
     */
    public void setExemptReason(ExemptReason value) {
        this.exemptReason = value;
    }

    /**
     * Gets the value of the exemptCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExemptCode() {
        return exemptCode;
    }

    /**
     * Sets the value of the exemptCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExemptCode(String value) {
        this.exemptCode = value;
    }

}
