
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for InvoiceDetailHeaderIndicator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceDetailHeaderIndicator">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="isHeaderInvoice">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="isVatRecoverable">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="isPriceBasedLineLevelCreditMemo">
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
@XmlType(name = "InvoiceDetailHeaderIndicator")
public class InvoiceDetailHeaderIndicator {

    @XmlAttribute(name = "isHeaderInvoice")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isHeaderInvoice;
    @XmlAttribute(name = "isVatRecoverable")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isVatRecoverable;
    @XmlAttribute(name = "isPriceBasedLineLevelCreditMemo")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isPriceBasedLineLevelCreditMemo;

    /**
     * Gets the value of the isHeaderInvoice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsHeaderInvoice() {
        return isHeaderInvoice;
    }

    /**
     * Sets the value of the isHeaderInvoice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsHeaderInvoice(String value) {
        this.isHeaderInvoice = value;
    }

    /**
     * Gets the value of the isVatRecoverable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsVatRecoverable() {
        return isVatRecoverable;
    }

    /**
     * Sets the value of the isVatRecoverable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsVatRecoverable(String value) {
        this.isVatRecoverable = value;
    }

    /**
     * Gets the value of the isPriceBasedLineLevelCreditMemo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsPriceBasedLineLevelCreditMemo() {
        return isPriceBasedLineLevelCreditMemo;
    }

    /**
     * Sets the value of the isPriceBasedLineLevelCreditMemo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsPriceBasedLineLevelCreditMemo(String value) {
        this.isPriceBasedLineLevelCreditMemo = value;
    }

}
