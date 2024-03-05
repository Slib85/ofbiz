
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AdditionalDeduction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AdditionalDeduction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{}DeductionAmount"/>
 *         &lt;element ref="{}DeductionPercent"/>
 *         &lt;element ref="{}DeductedPrice"/>
 *       &lt;/choice>
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdditionalDeduction", propOrder = {
    "deductionAmount",
    "deductionPercent",
    "deductedPrice"
})
public class AdditionalDeduction {

    @XmlElement(name = "DeductionAmount")
    protected DeductionAmount deductionAmount;
    @XmlElement(name = "DeductionPercent")
    protected DeductionPercent deductionPercent;
    @XmlElement(name = "DeductedPrice")
    protected DeductedPrice deductedPrice;
    @XmlAttribute(name = "type")
    @XmlSchemaType(name = "anySimpleType")
    protected String type;

    /**
     * Gets the value of the deductionAmount property.
     * 
     * @return
     *     possible object is
     *     {@link DeductionAmount }
     *     
     */
    public DeductionAmount getDeductionAmount() {
        return deductionAmount;
    }

    /**
     * Sets the value of the deductionAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeductionAmount }
     *     
     */
    public void setDeductionAmount(DeductionAmount value) {
        this.deductionAmount = value;
    }

    /**
     * Gets the value of the deductionPercent property.
     * 
     * @return
     *     possible object is
     *     {@link DeductionPercent }
     *     
     */
    public DeductionPercent getDeductionPercent() {
        return deductionPercent;
    }

    /**
     * Sets the value of the deductionPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeductionPercent }
     *     
     */
    public void setDeductionPercent(DeductionPercent value) {
        this.deductionPercent = value;
    }

    /**
     * Gets the value of the deductedPrice property.
     * 
     * @return
     *     possible object is
     *     {@link DeductedPrice }
     *     
     */
    public DeductedPrice getDeductedPrice() {
        return deductedPrice;
    }

    /**
     * Sets the value of the deductedPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeductedPrice }
     *     
     */
    public void setDeductedPrice(DeductedPrice value) {
        this.deductedPrice = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
