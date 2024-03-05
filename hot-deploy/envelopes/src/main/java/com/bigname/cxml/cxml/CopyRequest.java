
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for CopyRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CopyRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{}cXMLAttachment"/>
 *         &lt;element ref="{}cXML"/>
 *       &lt;/choice>
 *       &lt;attribute name="processingMode">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="copy"/>
 *             &lt;enumeration value="info"/>
 *             &lt;enumeration value="process"/>
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
@XmlType(name = "CopyRequest", propOrder = {
    "cxmlAttachment",
    "cxml"
})
public class CopyRequest {

    @XmlElement(name = "cXMLAttachment")
    protected CXMLAttachment cxmlAttachment;
    @XmlElement(name = "cXML")
    protected CXML cxml;
    @XmlAttribute(name = "processingMode")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String processingMode;

    /**
     * Gets the value of the cxmlAttachment property.
     * 
     * @return
     *     possible object is
     *     {@link CXMLAttachment }
     *     
     */
    public CXMLAttachment getCXMLAttachment() {
        return cxmlAttachment;
    }

    /**
     * Sets the value of the cxmlAttachment property.
     * 
     * @param value
     *     allowed object is
     *     {@link CXMLAttachment }
     *     
     */
    public void setCXMLAttachment(CXMLAttachment value) {
        this.cxmlAttachment = value;
    }

    /**
     * Gets the value of the cxml property.
     * 
     * @return
     *     possible object is
     *     {@link CXML }
     *     
     */
    public CXML getCXML() {
        return cxml;
    }

    /**
     * Sets the value of the cxml property.
     * 
     * @param value
     *     allowed object is
     *     {@link CXML }
     *     
     */
    public void setCXML(CXML value) {
        this.cxml = value;
    }

    /**
     * Gets the value of the processingMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessingMode() {
        return processingMode;
    }

    /**
     * Sets the value of the processingMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessingMode(String value) {
        this.processingMode = value;
    }

}
