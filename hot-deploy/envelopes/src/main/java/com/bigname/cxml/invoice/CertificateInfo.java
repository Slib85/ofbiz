
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for CertificateInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CertificateInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}IdReference"/>
 *       &lt;/sequence>
 *       &lt;attribute name="isRequired">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
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
@XmlType(name = "CertificateInfo", propOrder = {
    "idReference"
})
public class CertificateInfo {

    @XmlElement(name = "IdReference", required = true)
    protected IdReference idReference;
    @XmlAttribute(name = "isRequired")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isRequired;

    /**
     * Gets the value of the idReference property.
     * 
     * @return
     *     possible object is
     *     {@link IdReference }
     *     
     */
    public IdReference getIdReference() {
        return idReference;
    }

    /**
     * Sets the value of the idReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdReference }
     *     
     */
    public void setIdReference(IdReference value) {
        this.idReference = value;
    }

    /**
     * Gets the value of the isRequired property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsRequired() {
        return isRequired;
    }

    /**
     * Sets the value of the isRequired property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsRequired(String value) {
        this.isRequired = value;
    }

}
