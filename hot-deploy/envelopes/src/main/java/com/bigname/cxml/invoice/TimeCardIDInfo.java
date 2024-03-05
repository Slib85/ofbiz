
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TimeCardIDInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TimeCardIDInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="timeCardID" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeCardIDInfo")
public class TimeCardIDInfo {

    @XmlAttribute(name = "timeCardID", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String timeCardID;

    /**
     * Gets the value of the timeCardID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeCardID() {
        return timeCardID;
    }

    /**
     * Sets the value of the timeCardID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeCardID(String value) {
        this.timeCardID = value;
    }

}
