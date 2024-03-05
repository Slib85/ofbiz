
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ForecastDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ForecastDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="cumulativeStartDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="cumulativeEndDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ForecastDetails")
public class ForecastDetails {

    @XmlAttribute(name = "cumulativeStartDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String cumulativeStartDate;
    @XmlAttribute(name = "cumulativeEndDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String cumulativeEndDate;

    /**
     * Gets the value of the cumulativeStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCumulativeStartDate() {
        return cumulativeStartDate;
    }

    /**
     * Sets the value of the cumulativeStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCumulativeStartDate(String value) {
        this.cumulativeStartDate = value;
    }

    /**
     * Gets the value of the cumulativeEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCumulativeEndDate() {
        return cumulativeEndDate;
    }

    /**
     * Sets the value of the cumulativeEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCumulativeEndDate(String value) {
        this.cumulativeEndDate = value;
    }

}
