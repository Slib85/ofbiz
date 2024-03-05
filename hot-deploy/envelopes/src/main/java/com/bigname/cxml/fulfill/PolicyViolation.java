
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for PolicyViolation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PolicyViolation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Description"/>
 *         &lt;element ref="{}PolicyViolationJustification"/>
 *         &lt;element ref="{}Comments" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="level" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="warning"/>
 *             &lt;enumeration value="violation"/>
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
@XmlType(name = "PolicyViolation", propOrder = {
    "description",
    "policyViolationJustification",
    "comments"
})
public class PolicyViolation {

    @XmlElement(name = "Description", required = true)
    protected Description description;
    @XmlElement(name = "PolicyViolationJustification", required = true)
    protected PolicyViolationJustification policyViolationJustification;
    @XmlElement(name = "Comments")
    protected Comments comments;
    @XmlAttribute(name = "level", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String level;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link Description }
     *     
     */
    public Description getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link Description }
     *     
     */
    public void setDescription(Description value) {
        this.description = value;
    }

    /**
     * Gets the value of the policyViolationJustification property.
     * 
     * @return
     *     possible object is
     *     {@link PolicyViolationJustification }
     *     
     */
    public PolicyViolationJustification getPolicyViolationJustification() {
        return policyViolationJustification;
    }

    /**
     * Sets the value of the policyViolationJustification property.
     * 
     * @param value
     *     allowed object is
     *     {@link PolicyViolationJustification }
     *     
     */
    public void setPolicyViolationJustification(PolicyViolationJustification value) {
        this.policyViolationJustification = value;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link Comments }
     *     
     */
    public Comments getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link Comments }
     *     
     */
    public void setComments(Comments value) {
        this.comments = value;
    }

    /**
     * Gets the value of the level property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLevel() {
        return level;
    }

    /**
     * Sets the value of the level property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLevel(String value) {
        this.level = value;
    }

}
