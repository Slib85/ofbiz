
package com.bigname.cxml.cxml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QuoteRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QuoteRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}QuoteRequestHeader"/>
 *         &lt;element ref="{}QuoteItemOut" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuoteRequest", propOrder = {
    "quoteRequestHeader",
    "quoteItemOut"
})
public class QuoteRequest {

    @XmlElement(name = "QuoteRequestHeader", required = true)
    protected QuoteRequestHeader quoteRequestHeader;
    @XmlElement(name = "QuoteItemOut")
    protected List<QuoteItemOut> quoteItemOut;

    /**
     * Gets the value of the quoteRequestHeader property.
     * 
     * @return
     *     possible object is
     *     {@link QuoteRequestHeader }
     *     
     */
    public QuoteRequestHeader getQuoteRequestHeader() {
        return quoteRequestHeader;
    }

    /**
     * Sets the value of the quoteRequestHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuoteRequestHeader }
     *     
     */
    public void setQuoteRequestHeader(QuoteRequestHeader value) {
        this.quoteRequestHeader = value;
    }

    /**
     * Gets the value of the quoteItemOut property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the quoteItemOut property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQuoteItemOut().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QuoteItemOut }
     * 
     * 
     */
    public List<QuoteItemOut> getQuoteItemOut() {
        if (quoteItemOut == null) {
            quoteItemOut = new ArrayList<QuoteItemOut>();
        }
        return this.quoteItemOut;
    }

}
