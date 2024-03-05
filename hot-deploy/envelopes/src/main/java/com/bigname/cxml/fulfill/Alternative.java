
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for Alternative complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Alternative">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="alternativeType" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="basicLine"/>
 *             &lt;enumeration value="noAlternative"/>
 *             &lt;enumeration value="alternativeLine"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="basicLineNumber" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Alternative")
public class Alternative {

    @XmlAttribute(name = "alternativeType", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String alternativeType;
    @XmlAttribute(name = "basicLineNumber")
    @XmlSchemaType(name = "anySimpleType")
    protected String basicLineNumber;

    /**
     * Gets the value of the alternativeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlternativeType() {
        return alternativeType;
    }

    /**
     * Sets the value of the alternativeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlternativeType(String value) {
        this.alternativeType = value;
    }

    /**
     * Gets the value of the basicLineNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBasicLineNumber() {
        return basicLineNumber;
    }

    /**
     * Sets the value of the basicLineNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBasicLineNumber(String value) {
        this.basicLineNumber = value;
    }

}
