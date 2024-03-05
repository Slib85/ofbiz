
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Modification complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Modification">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}OriginalPrice" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element ref="{}AdditionalDeduction"/>
 *           &lt;element ref="{}AdditionalCost"/>
 *         &lt;/choice>
 *         &lt;element ref="{}Tax" minOccurs="0"/>
 *         &lt;element ref="{}ModificationDetail" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="level" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Modification", propOrder = {
    "originalPrice",
    "additionalDeduction",
    "additionalCost",
    "tax",
    "modificationDetail"
})
public class Modification {

    @XmlElement(name = "OriginalPrice")
    protected OriginalPrice originalPrice;
    @XmlElement(name = "AdditionalDeduction")
    protected AdditionalDeduction additionalDeduction;
    @XmlElement(name = "AdditionalCost")
    protected AdditionalCost additionalCost;
    @XmlElement(name = "Tax")
    protected Tax tax;
    @XmlElement(name = "ModificationDetail")
    protected ModificationDetail modificationDetail;
    @XmlAttribute(name = "level")
    @XmlSchemaType(name = "anySimpleType")
    protected String level;

    /**
     * Gets the value of the originalPrice property.
     * 
     * @return
     *     possible object is
     *     {@link OriginalPrice }
     *     
     */
    public OriginalPrice getOriginalPrice() {
        return originalPrice;
    }

    /**
     * Sets the value of the originalPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link OriginalPrice }
     *     
     */
    public void setOriginalPrice(OriginalPrice value) {
        this.originalPrice = value;
    }

    /**
     * Gets the value of the additionalDeduction property.
     * 
     * @return
     *     possible object is
     *     {@link AdditionalDeduction }
     *     
     */
    public AdditionalDeduction getAdditionalDeduction() {
        return additionalDeduction;
    }

    /**
     * Sets the value of the additionalDeduction property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdditionalDeduction }
     *     
     */
    public void setAdditionalDeduction(AdditionalDeduction value) {
        this.additionalDeduction = value;
    }

    /**
     * Gets the value of the additionalCost property.
     * 
     * @return
     *     possible object is
     *     {@link AdditionalCost }
     *     
     */
    public AdditionalCost getAdditionalCost() {
        return additionalCost;
    }

    /**
     * Sets the value of the additionalCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdditionalCost }
     *     
     */
    public void setAdditionalCost(AdditionalCost value) {
        this.additionalCost = value;
    }

    /**
     * Gets the value of the tax property.
     * 
     * @return
     *     possible object is
     *     {@link Tax }
     *     
     */
    public Tax getTax() {
        return tax;
    }

    /**
     * Sets the value of the tax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Tax }
     *     
     */
    public void setTax(Tax value) {
        this.tax = value;
    }

    /**
     * Gets the value of the modificationDetail property.
     * 
     * @return
     *     possible object is
     *     {@link ModificationDetail }
     *     
     */
    public ModificationDetail getModificationDetail() {
        return modificationDetail;
    }

    /**
     * Sets the value of the modificationDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModificationDetail }
     *     
     */
    public void setModificationDetail(ModificationDetail value) {
        this.modificationDetail = value;
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
