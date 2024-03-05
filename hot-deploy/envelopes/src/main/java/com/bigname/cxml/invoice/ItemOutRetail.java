
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ItemOutRetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemOutRetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}PromotionVariantID" minOccurs="0"/>
 *         &lt;element ref="{}PromotionDealID" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemOutRetail", propOrder = {
    "promotionVariantID",
    "promotionDealID"
})
public class ItemOutRetail {

    @XmlElement(name = "PromotionVariantID")
    protected PromotionVariantID promotionVariantID;
    @XmlElement(name = "PromotionDealID")
    protected PromotionDealID promotionDealID;

    /**
     * Gets the value of the promotionVariantID property.
     * 
     * @return
     *     possible object is
     *     {@link PromotionVariantID }
     *     
     */
    public PromotionVariantID getPromotionVariantID() {
        return promotionVariantID;
    }

    /**
     * Sets the value of the promotionVariantID property.
     * 
     * @param value
     *     allowed object is
     *     {@link PromotionVariantID }
     *     
     */
    public void setPromotionVariantID(PromotionVariantID value) {
        this.promotionVariantID = value;
    }

    /**
     * Gets the value of the promotionDealID property.
     * 
     * @return
     *     possible object is
     *     {@link PromotionDealID }
     *     
     */
    public PromotionDealID getPromotionDealID() {
        return promotionDealID;
    }

    /**
     * Sets the value of the promotionDealID property.
     * 
     * @param value
     *     allowed object is
     *     {@link PromotionDealID }
     *     
     */
    public void setPromotionDealID(PromotionDealID value) {
        this.promotionDealID = value;
    }

}
