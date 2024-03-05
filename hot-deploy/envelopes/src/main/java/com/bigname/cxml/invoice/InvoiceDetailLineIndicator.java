
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for InvoiceDetailLineIndicator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceDetailLineIndicator">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="isTaxInLine">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="isSpecialHandlingInLine">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="isShippingInLine">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="isDiscountInLine">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="isAccountingInLine">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="isPriceAdjustmentInLine">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
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
@XmlType(name = "InvoiceDetailLineIndicator")
public class InvoiceDetailLineIndicator {

    @XmlAttribute(name = "isTaxInLine")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isTaxInLine;
    @XmlAttribute(name = "isSpecialHandlingInLine")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isSpecialHandlingInLine;
    @XmlAttribute(name = "isShippingInLine")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isShippingInLine;
    @XmlAttribute(name = "isDiscountInLine")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isDiscountInLine;
    @XmlAttribute(name = "isAccountingInLine")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isAccountingInLine;
    @XmlAttribute(name = "isPriceAdjustmentInLine")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isPriceAdjustmentInLine;

    /**
     * Gets the value of the isTaxInLine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsTaxInLine() {
        return isTaxInLine;
    }

    /**
     * Sets the value of the isTaxInLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsTaxInLine(String value) {
        this.isTaxInLine = value;
    }

    /**
     * Gets the value of the isSpecialHandlingInLine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsSpecialHandlingInLine() {
        return isSpecialHandlingInLine;
    }

    /**
     * Sets the value of the isSpecialHandlingInLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsSpecialHandlingInLine(String value) {
        this.isSpecialHandlingInLine = value;
    }

    /**
     * Gets the value of the isShippingInLine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsShippingInLine() {
        return isShippingInLine;
    }

    /**
     * Sets the value of the isShippingInLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsShippingInLine(String value) {
        this.isShippingInLine = value;
    }

    /**
     * Gets the value of the isDiscountInLine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsDiscountInLine() {
        return isDiscountInLine;
    }

    /**
     * Sets the value of the isDiscountInLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsDiscountInLine(String value) {
        this.isDiscountInLine = value;
    }

    /**
     * Gets the value of the isAccountingInLine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsAccountingInLine() {
        return isAccountingInLine;
    }

    /**
     * Sets the value of the isAccountingInLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsAccountingInLine(String value) {
        this.isAccountingInLine = value;
    }

    /**
     * Gets the value of the isPriceAdjustmentInLine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsPriceAdjustmentInLine() {
        return isPriceAdjustmentInLine;
    }

    /**
     * Sets the value of the isPriceAdjustmentInLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsPriceAdjustmentInLine(String value) {
        this.isPriceAdjustmentInLine = value;
    }

}
