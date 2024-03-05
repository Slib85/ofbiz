
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ShipNoticeItemRetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipNoticeItemRetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}BestBeforeDate" minOccurs="0"/>
 *         &lt;element ref="{}ExpiryDate" minOccurs="0"/>
 *         &lt;element ref="{}FreeGoodsQuantity" minOccurs="0"/>
 *         &lt;element ref="{}PromotionDealID" minOccurs="0"/>
 *         &lt;element ref="{}PromotionVariantID" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipNoticeItemRetail", propOrder = {
    "bestBeforeDate",
    "expiryDate",
    "freeGoodsQuantity",
    "promotionDealID",
    "promotionVariantID"
})
public class ShipNoticeItemRetail {

    @XmlElement(name = "BestBeforeDate")
    protected BestBeforeDate bestBeforeDate;
    @XmlElement(name = "ExpiryDate")
    protected ExpiryDate expiryDate;
    @XmlElement(name = "FreeGoodsQuantity")
    protected FreeGoodsQuantity freeGoodsQuantity;
    @XmlElement(name = "PromotionDealID")
    protected PromotionDealID promotionDealID;
    @XmlElement(name = "PromotionVariantID")
    protected PromotionVariantID promotionVariantID;

    /**
     * Gets the value of the bestBeforeDate property.
     * 
     * @return
     *     possible object is
     *     {@link BestBeforeDate }
     *     
     */
    public BestBeforeDate getBestBeforeDate() {
        return bestBeforeDate;
    }

    /**
     * Sets the value of the bestBeforeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BestBeforeDate }
     *     
     */
    public void setBestBeforeDate(BestBeforeDate value) {
        this.bestBeforeDate = value;
    }

    /**
     * Gets the value of the expiryDate property.
     * 
     * @return
     *     possible object is
     *     {@link ExpiryDate }
     *     
     */
    public ExpiryDate getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the value of the expiryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpiryDate }
     *     
     */
    public void setExpiryDate(ExpiryDate value) {
        this.expiryDate = value;
    }

    /**
     * Gets the value of the freeGoodsQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link FreeGoodsQuantity }
     *     
     */
    public FreeGoodsQuantity getFreeGoodsQuantity() {
        return freeGoodsQuantity;
    }

    /**
     * Sets the value of the freeGoodsQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link FreeGoodsQuantity }
     *     
     */
    public void setFreeGoodsQuantity(FreeGoodsQuantity value) {
        this.freeGoodsQuantity = value;
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

}
