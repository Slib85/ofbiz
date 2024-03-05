
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExpectedResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExpectedResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}MinimumLimit" minOccurs="0"/>
 *         &lt;element ref="{}MaximumLimit" minOccurs="0"/>
 *         &lt;element ref="{}PropertyValue" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="targetValue" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="valuePrecision" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="qualitativeValue" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExpectedResult", propOrder = {
    "minimumLimit",
    "maximumLimit",
    "propertyValue"
})
public class ExpectedResult {

    @XmlElement(name = "MinimumLimit")
    protected MinimumLimit minimumLimit;
    @XmlElement(name = "MaximumLimit")
    protected MaximumLimit maximumLimit;
    @XmlElement(name = "PropertyValue")
    protected PropertyValue propertyValue;
    @XmlAttribute(name = "targetValue")
    @XmlSchemaType(name = "anySimpleType")
    protected String targetValue;
    @XmlAttribute(name = "valuePrecision")
    @XmlSchemaType(name = "anySimpleType")
    protected String valuePrecision;
    @XmlAttribute(name = "qualitativeValue")
    @XmlSchemaType(name = "anySimpleType")
    protected String qualitativeValue;

    /**
     * Gets the value of the minimumLimit property.
     * 
     * @return
     *     possible object is
     *     {@link MinimumLimit }
     *     
     */
    public MinimumLimit getMinimumLimit() {
        return minimumLimit;
    }

    /**
     * Sets the value of the minimumLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link MinimumLimit }
     *     
     */
    public void setMinimumLimit(MinimumLimit value) {
        this.minimumLimit = value;
    }

    /**
     * Gets the value of the maximumLimit property.
     * 
     * @return
     *     possible object is
     *     {@link MaximumLimit }
     *     
     */
    public MaximumLimit getMaximumLimit() {
        return maximumLimit;
    }

    /**
     * Sets the value of the maximumLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaximumLimit }
     *     
     */
    public void setMaximumLimit(MaximumLimit value) {
        this.maximumLimit = value;
    }

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
     * Gets the value of the targetValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetValue() {
        return targetValue;
    }

    /**
     * Sets the value of the targetValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetValue(String value) {
        this.targetValue = value;
    }

    /**
     * Gets the value of the valuePrecision property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValuePrecision() {
        return valuePrecision;
    }

    /**
     * Sets the value of the valuePrecision property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValuePrecision(String value) {
        this.valuePrecision = value;
    }

    /**
     * Gets the value of the qualitativeValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQualitativeValue() {
        return qualitativeValue;
    }

    /**
     * Sets the value of the qualitativeValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQualitativeValue(String value) {
        this.qualitativeValue = value;
    }

}
