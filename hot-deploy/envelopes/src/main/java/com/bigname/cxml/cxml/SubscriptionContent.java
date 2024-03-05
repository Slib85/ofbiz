
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SubscriptionContent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SubscriptionContent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{}CIFContent"/>
 *         &lt;element ref="{}Index"/>
 *         &lt;element ref="{}Contract"/>
 *       &lt;/choice>
 *       &lt;attribute name="filename" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubscriptionContent", propOrder = {
    "cifContent",
    "index",
    "contract"
})
public class SubscriptionContent {

    @XmlElement(name = "CIFContent")
    protected CIFContent cifContent;
    @XmlElement(name = "Index")
    protected Index index;
    @XmlElement(name = "Contract")
    protected Contract contract;
    @XmlAttribute(name = "filename")
    @XmlSchemaType(name = "anySimpleType")
    protected String filename;

    /**
     * Gets the value of the cifContent property.
     * 
     * @return
     *     possible object is
     *     {@link CIFContent }
     *     
     */
    public CIFContent getCIFContent() {
        return cifContent;
    }

    /**
     * Sets the value of the cifContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link CIFContent }
     *     
     */
    public void setCIFContent(CIFContent value) {
        this.cifContent = value;
    }

    /**
     * Gets the value of the index property.
     * 
     * @return
     *     possible object is
     *     {@link Index }
     *     
     */
    public Index getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     * @param value
     *     allowed object is
     *     {@link Index }
     *     
     */
    public void setIndex(Index value) {
        this.index = value;
    }

    /**
     * Gets the value of the contract property.
     * 
     * @return
     *     possible object is
     *     {@link Contract }
     *     
     */
    public Contract getContract() {
        return contract;
    }

    /**
     * Sets the value of the contract property.
     * 
     * @param value
     *     allowed object is
     *     {@link Contract }
     *     
     */
    public void setContract(Contract value) {
        this.contract = value;
    }

    /**
     * Gets the value of the filename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Sets the value of the filename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilename(String value) {
        this.filename = value;
    }

}
