
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UnitPrice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UnitPrice">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Money"/>
 *         &lt;element ref="{}Modifications" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnitPrice", propOrder = {
    "money",
    "modifications"
})
public class UnitPrice {

    @XmlElement(name = "Money", required = true)
    protected Money money;
    @XmlElement(name = "Modifications")
    protected Modifications modifications;

    /**
     * Gets the value of the money property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getMoney() {
        return money;
    }

    /**
     * Sets the value of the money property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setMoney(Money value) {
        this.money = value;
    }

    /**
     * Gets the value of the modifications property.
     * 
     * @return
     *     possible object is
     *     {@link Modifications }
     *     
     */
    public Modifications getModifications() {
        return modifications;
    }

    /**
     * Sets the value of the modifications property.
     * 
     * @param value
     *     allowed object is
     *     {@link Modifications }
     *     
     */
    public void setModifications(Modifications value) {
        this.modifications = value;
    }

}
