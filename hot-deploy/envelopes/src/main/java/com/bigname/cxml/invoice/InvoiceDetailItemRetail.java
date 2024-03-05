
package com.bigname.cxml.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceDetailItemRetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceDetailItemRetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}AdditionalPrices" minOccurs="0"/>
 *         &lt;element ref="{}TotalRetailAmount" minOccurs="0"/>
 *         &lt;element ref="{}ItemIndicator" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "InvoiceDetailItemRetail", propOrder = {
    "additionalPrices",
    "totalRetailAmount",
    "itemIndicator",
    "promotionDealID",
    "promotionVariantID"
})
public class InvoiceDetailItemRetail {

    @XmlElement(name = "AdditionalPrices")
    protected AdditionalPrices additionalPrices;
    @XmlElement(name = "TotalRetailAmount")
    protected TotalRetailAmount totalRetailAmount;
    @XmlElement(name = "ItemIndicator")
    protected List<ItemIndicator> itemIndicator;
    @XmlElement(name = "PromotionDealID")
    protected PromotionDealID promotionDealID;
    @XmlElement(name = "PromotionVariantID")
    protected PromotionVariantID promotionVariantID;

    /**
     * Gets the value of the additionalPrices property.
     * 
     * @return
     *     possible object is
     *     {@link AdditionalPrices }
     *     
     */
    public AdditionalPrices getAdditionalPrices() {
        return additionalPrices;
    }

    /**
     * Sets the value of the additionalPrices property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdditionalPrices }
     *     
     */
    public void setAdditionalPrices(AdditionalPrices value) {
        this.additionalPrices = value;
    }

    /**
     * Gets the value of the totalRetailAmount property.
     * 
     * @return
     *     possible object is
     *     {@link TotalRetailAmount }
     *     
     */
    public TotalRetailAmount getTotalRetailAmount() {
        return totalRetailAmount;
    }

    /**
     * Sets the value of the totalRetailAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link TotalRetailAmount }
     *     
     */
    public void setTotalRetailAmount(TotalRetailAmount value) {
        this.totalRetailAmount = value;
    }

    /**
     * Gets the value of the itemIndicator property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itemIndicator property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItemIndicator().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemIndicator }
     * 
     * 
     */
    public List<ItemIndicator> getItemIndicator() {
        if (itemIndicator == null) {
            itemIndicator = new ArrayList<ItemIndicator>();
        }
        return this.itemIndicator;
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
