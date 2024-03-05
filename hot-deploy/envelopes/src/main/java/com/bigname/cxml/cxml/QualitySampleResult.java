
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QualitySampleResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QualitySampleResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}PropertyValue" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="sampleID" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="unitValue" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="physicalSampleNumber" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QualitySampleResult", propOrder = {
    "propertyValue"
})
public class QualitySampleResult {

    @XmlElement(name = "PropertyValue")
    protected PropertyValue propertyValue;
    @XmlAttribute(name = "sampleID")
    @XmlSchemaType(name = "anySimpleType")
    protected String sampleID;
    @XmlAttribute(name = "unitValue")
    @XmlSchemaType(name = "anySimpleType")
    protected String unitValue;
    @XmlAttribute(name = "physicalSampleNumber")
    @XmlSchemaType(name = "anySimpleType")
    protected String physicalSampleNumber;

    /**
     * Gets the value of the propertyValue property.
     * 
     * @return
     *     possible object is
     *     {@link PropertyValue }
     *     
     */
    public PropertyValue getPropertyValue() {
        return propertyValue;
    }

    /**
     * Sets the value of the propertyValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertyValue }
     *     
     */
    public void setPropertyValue(PropertyValue value) {
        this.propertyValue = value;
    }

    /**
     * Gets the value of the sampleID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSampleID() {
        return sampleID;
    }

    /**
     * Sets the value of the sampleID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSampleID(String value) {
        this.sampleID = value;
    }

    /**
     * Gets the value of the unitValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitValue() {
        return unitValue;
    }

    /**
     * Sets the value of the unitValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitValue(String value) {
        this.unitValue = value;
    }

    /**
     * Gets the value of the physicalSampleNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhysicalSampleNumber() {
        return physicalSampleNumber;
    }

    /**
     * Sets the value of the physicalSampleNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhysicalSampleNumber(String value) {
        this.physicalSampleNumber = value;
    }

}
