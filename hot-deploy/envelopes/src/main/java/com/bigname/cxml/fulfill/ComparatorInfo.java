
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ComparatorInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ComparatorInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="comparatorType" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="less"/>
 *             &lt;enumeration value="greater"/>
 *             &lt;enumeration value="lessOrEqual"/>
 *             &lt;enumeration value="greaterOrEqual"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="comparatorValue" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComparatorInfo")
public class ComparatorInfo {

    @XmlAttribute(name = "comparatorType", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String comparatorType;
    @XmlAttribute(name = "comparatorValue", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String comparatorValue;

    /**
     * Gets the value of the comparatorType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComparatorType() {
        return comparatorType;
    }

    /**
     * Sets the value of the comparatorType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComparatorType(String value) {
        this.comparatorType = value;
    }

    /**
     * Gets the value of the comparatorValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComparatorValue() {
        return comparatorValue;
    }

    /**
     * Sets the value of the comparatorValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComparatorValue(String value) {
        this.comparatorValue = value;
    }

}
