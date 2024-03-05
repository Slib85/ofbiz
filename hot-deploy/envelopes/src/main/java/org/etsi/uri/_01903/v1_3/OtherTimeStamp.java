//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.10.04 at 05:24:23 PM EDT 
//


package org.etsi.uri._01903.v1_3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.w3._2000._09.xmldsig_.CanonicalizationMethod;


/**
 * <p>Java class for OtherTimeStamp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OtherTimeStamp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://uri.etsi.org/01903/v1.3.2#}ReferenceInfo" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}CanonicalizationMethod" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element ref="{http://uri.etsi.org/01903/v1.3.2#}EncapsulatedTimeStamp"/>
 *           &lt;element ref="{http://uri.etsi.org/01903/v1.3.2#}XMLTimeStamp"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OtherTimeStamp", namespace = "http://uri.etsi.org/01903/v1.3.2#", propOrder = {
    "referenceInfo",
    "canonicalizationMethod",
    "encapsulatedTimeStamp",
    "xmlTimeStamp"
})
public class OtherTimeStamp {

    @XmlElement(name = "ReferenceInfo", namespace = "http://uri.etsi.org/01903/v1.3.2#", required = true)
    protected List<ReferenceInfo> referenceInfo;
    @XmlElement(name = "CanonicalizationMethod", namespace = "http://www.w3.org/2000/09/xmldsig#")
    protected CanonicalizationMethod canonicalizationMethod;
    @XmlElement(name = "EncapsulatedTimeStamp", namespace = "http://uri.etsi.org/01903/v1.3.2#")
    protected EncapsulatedTimeStamp encapsulatedTimeStamp;
    @XmlElement(name = "XMLTimeStamp", namespace = "http://uri.etsi.org/01903/v1.3.2#")
    protected XMLTimeStamp xmlTimeStamp;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the referenceInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the referenceInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferenceInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReferenceInfo }
     * 
     * 
     */
    public List<ReferenceInfo> getReferenceInfo() {
        if (referenceInfo == null) {
            referenceInfo = new ArrayList<ReferenceInfo>();
        }
        return this.referenceInfo;
    }

    /**
     * Gets the value of the canonicalizationMethod property.
     * 
     * @return
     *     possible object is
     *     {@link CanonicalizationMethod }
     *     
     */
    public CanonicalizationMethod getCanonicalizationMethod() {
        return canonicalizationMethod;
    }

    /**
     * Sets the value of the canonicalizationMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link CanonicalizationMethod }
     *     
     */
    public void setCanonicalizationMethod(CanonicalizationMethod value) {
        this.canonicalizationMethod = value;
    }

    /**
     * Gets the value of the encapsulatedTimeStamp property.
     * 
     * @return
     *     possible object is
     *     {@link EncapsulatedTimeStamp }
     *     
     */
    public EncapsulatedTimeStamp getEncapsulatedTimeStamp() {
        return encapsulatedTimeStamp;
    }

    /**
     * Sets the value of the encapsulatedTimeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link EncapsulatedTimeStamp }
     *     
     */
    public void setEncapsulatedTimeStamp(EncapsulatedTimeStamp value) {
        this.encapsulatedTimeStamp = value;
    }

    /**
     * Gets the value of the xmlTimeStamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLTimeStamp }
     *     
     */
    public XMLTimeStamp getXMLTimeStamp() {
        return xmlTimeStamp;
    }

    /**
     * Sets the value of the xmlTimeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLTimeStamp }
     *     
     */
    public void setXMLTimeStamp(XMLTimeStamp value) {
        this.xmlTimeStamp = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}