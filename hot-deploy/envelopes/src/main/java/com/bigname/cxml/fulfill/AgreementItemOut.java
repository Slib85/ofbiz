
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AgreementItemOut complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AgreementItemOut">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}MaxAmount" minOccurs="0"/>
 *         &lt;element ref="{}MinAmount" minOccurs="0"/>
 *         &lt;element ref="{}MaxReleaseAmount" minOccurs="0"/>
 *         &lt;element ref="{}MinReleaseAmount" minOccurs="0"/>
 *         &lt;element ref="{}ItemOut"/>
 *       &lt;/sequence>
 *       &lt;attribute name="maxQuantity" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="minQuantity" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="maxReleaseQuantity" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="minReleaseQuantity" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AgreementItemOut", propOrder = {
    "maxAmount",
    "minAmount",
    "maxReleaseAmount",
    "minReleaseAmount",
    "itemOut"
})
public class AgreementItemOut {

    @XmlElement(name = "MaxAmount")
    protected MaxAmount maxAmount;
    @XmlElement(name = "MinAmount")
    protected MinAmount minAmount;
    @XmlElement(name = "MaxReleaseAmount")
    protected MaxReleaseAmount maxReleaseAmount;
    @XmlElement(name = "MinReleaseAmount")
    protected MinReleaseAmount minReleaseAmount;
    @XmlElement(name = "ItemOut", required = true)
    protected ItemOut itemOut;
    @XmlAttribute(name = "maxQuantity")
    @XmlSchemaType(name = "anySimpleType")
    protected String maxQuantity;
    @XmlAttribute(name = "minQuantity")
    @XmlSchemaType(name = "anySimpleType")
    protected String minQuantity;
    @XmlAttribute(name = "maxReleaseQuantity")
    @XmlSchemaType(name = "anySimpleType")
    protected String maxReleaseQuantity;
    @XmlAttribute(name = "minReleaseQuantity")
    @XmlSchemaType(name = "anySimpleType")
    protected String minReleaseQuantity;

    /**
     * Gets the value of the maxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MaxAmount }
     *     
     */
    public MaxAmount getMaxAmount() {
        return maxAmount;
    }

    /**
     * Sets the value of the maxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaxAmount }
     *     
     */
    public void setMaxAmount(MaxAmount value) {
        this.maxAmount = value;
    }

    /**
     * Gets the value of the minAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MinAmount }
     *     
     */
    public MinAmount getMinAmount() {
        return minAmount;
    }

    /**
     * Sets the value of the minAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MinAmount }
     *     
     */
    public void setMinAmount(MinAmount value) {
        this.minAmount = value;
    }

    /**
     * Gets the value of the maxReleaseAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MaxReleaseAmount }
     *     
     */
    public MaxReleaseAmount getMaxReleaseAmount() {
        return maxReleaseAmount;
    }

    /**
     * Sets the value of the maxReleaseAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaxReleaseAmount }
     *     
     */
    public void setMaxReleaseAmount(MaxReleaseAmount value) {
        this.maxReleaseAmount = value;
    }

    /**
     * Gets the value of the minReleaseAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MinReleaseAmount }
     *     
     */
    public MinReleaseAmount getMinReleaseAmount() {
        return minReleaseAmount;
    }

    /**
     * Sets the value of the minReleaseAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MinReleaseAmount }
     *     
     */
    public void setMinReleaseAmount(MinReleaseAmount value) {
        this.minReleaseAmount = value;
    }

    /**
     * Gets the value of the itemOut property.
     * 
     * @return
     *     possible object is
     *     {@link ItemOut }
     *     
     */
    public ItemOut getItemOut() {
        return itemOut;
    }

    /**
     * Sets the value of the itemOut property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemOut }
     *     
     */
    public void setItemOut(ItemOut value) {
        this.itemOut = value;
    }

    /**
     * Gets the value of the maxQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxQuantity() {
        return maxQuantity;
    }

    /**
     * Sets the value of the maxQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxQuantity(String value) {
        this.maxQuantity = value;
    }

    /**
     * Gets the value of the minQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinQuantity() {
        return minQuantity;
    }

    /**
     * Sets the value of the minQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinQuantity(String value) {
        this.minQuantity = value;
    }

    /**
     * Gets the value of the maxReleaseQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxReleaseQuantity() {
        return maxReleaseQuantity;
    }

    /**
     * Sets the value of the maxReleaseQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxReleaseQuantity(String value) {
        this.maxReleaseQuantity = value;
    }

    /**
     * Gets the value of the minReleaseQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinReleaseQuantity() {
        return minReleaseQuantity;
    }

    /**
     * Sets the value of the minReleaseQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinReleaseQuantity(String value) {
        this.minReleaseQuantity = value;
    }

}
