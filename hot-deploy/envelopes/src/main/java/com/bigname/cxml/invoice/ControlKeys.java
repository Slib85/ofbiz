
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ControlKeys complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ControlKeys">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}OCInstruction" minOccurs="0"/>
 *         &lt;element ref="{}ASNInstruction" minOccurs="0"/>
 *         &lt;element ref="{}InvoiceInstruction" minOccurs="0"/>
 *         &lt;element ref="{}SESInstruction" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ControlKeys", propOrder = {
    "ocInstruction",
    "asnInstruction",
    "invoiceInstruction",
    "sesInstruction"
})
public class ControlKeys {

    @XmlElement(name = "OCInstruction")
    protected OCInstruction ocInstruction;
    @XmlElement(name = "ASNInstruction")
    protected ASNInstruction asnInstruction;
    @XmlElement(name = "InvoiceInstruction")
    protected InvoiceInstruction invoiceInstruction;
    @XmlElement(name = "SESInstruction")
    protected SESInstruction sesInstruction;

    /**
     * Gets the value of the ocInstruction property.
     * 
     * @return
     *     possible object is
     *     {@link OCInstruction }
     *     
     */
    public OCInstruction getOCInstruction() {
        return ocInstruction;
    }

    /**
     * Sets the value of the ocInstruction property.
     * 
     * @param value
     *     allowed object is
     *     {@link OCInstruction }
     *     
     */
    public void setOCInstruction(OCInstruction value) {
        this.ocInstruction = value;
    }

    /**
     * Gets the value of the asnInstruction property.
     * 
     * @return
     *     possible object is
     *     {@link ASNInstruction }
     *     
     */
    public ASNInstruction getASNInstruction() {
        return asnInstruction;
    }

    /**
     * Sets the value of the asnInstruction property.
     * 
     * @param value
     *     allowed object is
     *     {@link ASNInstruction }
     *     
     */
    public void setASNInstruction(ASNInstruction value) {
        this.asnInstruction = value;
    }

    /**
     * Gets the value of the invoiceInstruction property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceInstruction }
     *     
     */
    public InvoiceInstruction getInvoiceInstruction() {
        return invoiceInstruction;
    }

    /**
     * Sets the value of the invoiceInstruction property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceInstruction }
     *     
     */
    public void setInvoiceInstruction(InvoiceInstruction value) {
        this.invoiceInstruction = value;
    }

    /**
     * Gets the value of the sesInstruction property.
     * 
     * @return
     *     possible object is
     *     {@link SESInstruction }
     *     
     */
    public SESInstruction getSESInstruction() {
        return sesInstruction;
    }

    /**
     * Sets the value of the sesInstruction property.
     * 
     * @param value
     *     allowed object is
     *     {@link SESInstruction }
     *     
     */
    public void setSESInstruction(SESInstruction value) {
        this.sesInstruction = value;
    }

}
