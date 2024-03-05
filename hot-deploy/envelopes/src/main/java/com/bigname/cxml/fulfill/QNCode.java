
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QNCode complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QNCode">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="domain" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="codeGroup" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="codeGroupDescription" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="code" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="codeDescription" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QNCode")
public class QNCode {

    @XmlAttribute(name = "domain", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String domain;
    @XmlAttribute(name = "codeGroup")
    @XmlSchemaType(name = "anySimpleType")
    protected String codeGroup;
    @XmlAttribute(name = "codeGroupDescription")
    @XmlSchemaType(name = "anySimpleType")
    protected String codeGroupDescription;
    @XmlAttribute(name = "code", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String code;
    @XmlAttribute(name = "codeDescription", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String codeDescription;

    /**
     * Gets the value of the domain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Sets the value of the domain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomain(String value) {
        this.domain = value;
    }

    /**
     * Gets the value of the codeGroup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeGroup() {
        return codeGroup;
    }

    /**
     * Sets the value of the codeGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeGroup(String value) {
        this.codeGroup = value;
    }

    /**
     * Gets the value of the codeGroupDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeGroupDescription() {
        return codeGroupDescription;
    }

    /**
     * Sets the value of the codeGroupDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeGroupDescription(String value) {
        this.codeGroupDescription = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the codeDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeDescription() {
        return codeDescription;
    }

    /**
     * Sets the value of the codeDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeDescription(String value) {
        this.codeDescription = value;
    }

}
