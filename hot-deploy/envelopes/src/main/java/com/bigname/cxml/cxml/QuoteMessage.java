
package com.bigname.cxml.cxml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QuoteMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QuoteMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}QuoteMessageHeader"/>
 *         &lt;element ref="{}QuoteItemIn" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuoteMessage", propOrder = {
    "quoteMessageHeader",
    "quoteItemIn"
})
public class QuoteMessage {

    @XmlElement(name = "QuoteMessageHeader", required = true)
    protected QuoteMessageHeader quoteMessageHeader;
    @XmlElement(name = "QuoteItemIn")
    protected List<QuoteItemIn> quoteItemIn;

    /**
     * Gets the value of the quoteMessageHeader property.
     * 
     * @return
     *     possible object is
     *     {@link QuoteMessageHeader }
     *     
     */
    public QuoteMessageHeader getQuoteMessageHeader() {
        return quoteMessageHeader;
    }

    /**
     * Sets the value of the quoteMessageHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuoteMessageHeader }
     *     
     */
    public void setQuoteMessageHeader(QuoteMessageHeader value) {
        this.quoteMessageHeader = value;
    }

    /**
     * Gets the value of the quoteItemIn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the quoteItemIn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQuoteItemIn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QuoteItemIn }
     * 
     * 
     */
    public List<QuoteItemIn> getQuoteItemIn() {
        if (quoteItemIn == null) {
            quoteItemIn = new ArrayList<QuoteItemIn>();
        }
        return this.quoteItemIn;
    }

}
