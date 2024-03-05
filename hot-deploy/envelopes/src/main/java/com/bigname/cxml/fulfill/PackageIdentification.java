
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PackageIdentification complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PackageIdentification">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="rangeBegin" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="rangeEnd" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PackageIdentification")
public class PackageIdentification {

    @XmlAttribute(name = "rangeBegin", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String rangeBegin;
    @XmlAttribute(name = "rangeEnd", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String rangeEnd;

    /**
     * Gets the value of the rangeBegin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRangeBegin() {
        return rangeBegin;
    }

    /**
     * Sets the value of the rangeBegin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRangeBegin(String value) {
        this.rangeBegin = value;
    }

    /**
     * Gets the value of the rangeEnd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRangeEnd() {
        return rangeEnd;
    }

    /**
     * Sets the value of the rangeEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRangeEnd(String value) {
        this.rangeEnd = value;
    }

}
