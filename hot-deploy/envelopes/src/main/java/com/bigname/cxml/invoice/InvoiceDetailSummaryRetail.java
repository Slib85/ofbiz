
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceDetailSummaryRetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceDetailSummaryRetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}AdditionalAmounts" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceDetailSummaryRetail", propOrder = {
    "additionalAmounts"
})
public class InvoiceDetailSummaryRetail {

    @XmlElement(name = "AdditionalAmounts")
    protected AdditionalAmounts additionalAmounts;

    /**
     * Gets the value of the additionalAmounts property.
     * 
     * @return
     *     possible object is
     *     {@link AdditionalAmounts }
     *     
     */
    public AdditionalAmounts getAdditionalAmounts() {
        return additionalAmounts;
    }

    /**
     * Sets the value of the additionalAmounts property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdditionalAmounts }
     *     
     */
    public void setAdditionalAmounts(AdditionalAmounts value) {
        this.additionalAmounts = value;
    }

}
