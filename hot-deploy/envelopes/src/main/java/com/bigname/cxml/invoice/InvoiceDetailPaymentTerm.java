
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceDetailPaymentTerm complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceDetailPaymentTerm">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="payInNumberOfDays" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="percentageRate" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceDetailPaymentTerm")
public class InvoiceDetailPaymentTerm {

    @XmlAttribute(name = "payInNumberOfDays", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String payInNumberOfDays;
    @XmlAttribute(name = "percentageRate", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String percentageRate;

    /**
     * Gets the value of the payInNumberOfDays property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayInNumberOfDays() {
        return payInNumberOfDays;
    }

    /**
     * Sets the value of the payInNumberOfDays property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayInNumberOfDays(String value) {
        this.payInNumberOfDays = value;
    }

    /**
     * Gets the value of the percentageRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPercentageRate() {
        return percentageRate;
    }

    /**
     * Sets the value of the percentageRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPercentageRate(String value) {
        this.percentageRate = value;
    }

}
