
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for InvoiceInstruction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceInstruction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}TemporaryPrice" minOccurs="0"/>
 *         &lt;element ref="{}Lower" minOccurs="0"/>
 *         &lt;element ref="{}Upper" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="value">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="isERS"/>
 *             &lt;enumeration value="allowed"/>
 *             &lt;enumeration value="isNotERS"/>
 *             &lt;enumeration value="notAllowed"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="verificationType">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="goodsReceipt"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="unitPriceEditable">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="no"/>
 *             &lt;enumeration value="yes"/>
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
@XmlType(name = "InvoiceInstruction", propOrder = {
    "temporaryPrice",
    "lower",
    "upper"
})
public class InvoiceInstruction {

    @XmlElement(name = "TemporaryPrice")
    protected TemporaryPrice temporaryPrice;
    @XmlElement(name = "Lower")
    protected Lower lower;
    @XmlElement(name = "Upper")
    protected Upper upper;
    @XmlAttribute(name = "value")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String value;
    @XmlAttribute(name = "verificationType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String verificationType;
    @XmlAttribute(name = "unitPriceEditable")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String unitPriceEditable;

    /**
     * Gets the value of the temporaryPrice property.
     * 
     * @return
     *     possible object is
     *     {@link TemporaryPrice }
     *     
     */
    public TemporaryPrice getTemporaryPrice() {
        return temporaryPrice;
    }

    /**
     * Sets the value of the temporaryPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link TemporaryPrice }
     *     
     */
    public void setTemporaryPrice(TemporaryPrice value) {
        this.temporaryPrice = value;
    }

    /**
     * Gets the value of the lower property.
     * 
     * @return
     *     possible object is
     *     {@link Lower }
     *     
     */
    public Lower getLower() {
        return lower;
    }

    /**
     * Sets the value of the lower property.
     * 
     * @param value
     *     allowed object is
     *     {@link Lower }
     *     
     */
    public void setLower(Lower value) {
        this.lower = value;
    }

    /**
     * Gets the value of the upper property.
     * 
     * @return
     *     possible object is
     *     {@link Upper }
     *     
     */
    public Upper getUpper() {
        return upper;
    }

    /**
     * Sets the value of the upper property.
     * 
     * @param value
     *     allowed object is
     *     {@link Upper }
     *     
     */
    public void setUpper(Upper value) {
        this.upper = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the verificationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVerificationType() {
        return verificationType;
    }

    /**
     * Sets the value of the verificationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVerificationType(String value) {
        this.verificationType = value;
    }

    /**
     * Gets the value of the unitPriceEditable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitPriceEditable() {
        return unitPriceEditable;
    }

    /**
     * Sets the value of the unitPriceEditable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitPriceEditable(String value) {
        this.unitPriceEditable = value;
    }

}
