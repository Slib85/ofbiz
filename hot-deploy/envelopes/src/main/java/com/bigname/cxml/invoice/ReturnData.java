
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReturnData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReturnData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ReturnValue"/>
 *         &lt;element ref="{}Name"/>
 *       &lt;/sequence>
 *       &lt;attribute name="nameValue" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReturnData", propOrder = {
    "returnValue",
    "name"
})
public class ReturnData {

    @XmlElement(name = "ReturnValue", required = true)
    protected ReturnValue returnValue;
    @XmlElement(name = "Name", required = true)
    protected Name name;
    @XmlAttribute(name = "name")
    @XmlSchemaType(name = "anySimpleType")
    protected String nameValue;

    /**
     * Gets the value of the returnValue property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnValue }
     *     
     */
    public ReturnValue getReturnValue() {
        return returnValue;
    }

    /**
     * Sets the value of the returnValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnValue }
     *     
     */
    public void setReturnValue(ReturnValue value) {
        this.returnValue = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link Name }
     *     
     */
    public Name getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link Name }
     *     
     */
    public void setName(Name value) {
        this.name = value;
    }

    /**
     * Gets the value of the nameValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameValue() {
        return nameValue;
    }

    /**
     * Sets the value of the nameValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameValue(String value) {
        this.nameValue = value;
    }

}
