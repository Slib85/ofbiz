
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AdditionalAmounts complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AdditionalAmounts">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}TotalRetailAmount" minOccurs="0"/>
 *         &lt;element ref="{}InformationalAmount" minOccurs="0"/>
 *         &lt;element ref="{}GrossProgressPaymentAmount" minOccurs="0"/>
 *         &lt;element ref="{}TotalReturnableItemsDepositAmount" minOccurs="0"/>
 *         &lt;element ref="{}GoodsAndServiceAmount" minOccurs="0"/>
 *         &lt;element ref="{}ExactAmount" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdditionalAmounts", propOrder = {
    "totalRetailAmount",
    "informationalAmount",
    "grossProgressPaymentAmount",
    "totalReturnableItemsDepositAmount",
    "goodsAndServiceAmount",
    "exactAmount"
})
public class AdditionalAmounts {

    @XmlElement(name = "TotalRetailAmount")
    protected TotalRetailAmount totalRetailAmount;
    @XmlElement(name = "InformationalAmount")
    protected InformationalAmount informationalAmount;
    @XmlElement(name = "GrossProgressPaymentAmount")
    protected GrossProgressPaymentAmount grossProgressPaymentAmount;
    @XmlElement(name = "TotalReturnableItemsDepositAmount")
    protected TotalReturnableItemsDepositAmount totalReturnableItemsDepositAmount;
    @XmlElement(name = "GoodsAndServiceAmount")
    protected GoodsAndServiceAmount goodsAndServiceAmount;
    @XmlElement(name = "ExactAmount")
    protected ExactAmount exactAmount;

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
     * Gets the value of the informationalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link InformationalAmount }
     *     
     */
    public InformationalAmount getInformationalAmount() {
        return informationalAmount;
    }

    /**
     * Sets the value of the informationalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformationalAmount }
     *     
     */
    public void setInformationalAmount(InformationalAmount value) {
        this.informationalAmount = value;
    }

    /**
     * Gets the value of the grossProgressPaymentAmount property.
     * 
     * @return
     *     possible object is
     *     {@link GrossProgressPaymentAmount }
     *     
     */
    public GrossProgressPaymentAmount getGrossProgressPaymentAmount() {
        return grossProgressPaymentAmount;
    }

    /**
     * Sets the value of the grossProgressPaymentAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link GrossProgressPaymentAmount }
     *     
     */
    public void setGrossProgressPaymentAmount(GrossProgressPaymentAmount value) {
        this.grossProgressPaymentAmount = value;
    }

    /**
     * Gets the value of the totalReturnableItemsDepositAmount property.
     * 
     * @return
     *     possible object is
     *     {@link TotalReturnableItemsDepositAmount }
     *     
     */
    public TotalReturnableItemsDepositAmount getTotalReturnableItemsDepositAmount() {
        return totalReturnableItemsDepositAmount;
    }

    /**
     * Sets the value of the totalReturnableItemsDepositAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link TotalReturnableItemsDepositAmount }
     *     
     */
    public void setTotalReturnableItemsDepositAmount(TotalReturnableItemsDepositAmount value) {
        this.totalReturnableItemsDepositAmount = value;
    }

    /**
     * Gets the value of the goodsAndServiceAmount property.
     * 
     * @return
     *     possible object is
     *     {@link GoodsAndServiceAmount }
     *     
     */
    public GoodsAndServiceAmount getGoodsAndServiceAmount() {
        return goodsAndServiceAmount;
    }

    /**
     * Sets the value of the goodsAndServiceAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link GoodsAndServiceAmount }
     *     
     */
    public void setGoodsAndServiceAmount(GoodsAndServiceAmount value) {
        this.goodsAndServiceAmount = value;
    }

    /**
     * Gets the value of the exactAmount property.
     * 
     * @return
     *     possible object is
     *     {@link ExactAmount }
     *     
     */
    public ExactAmount getExactAmount() {
        return exactAmount;
    }

    /**
     * Sets the value of the exactAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExactAmount }
     *     
     */
    public void setExactAmount(ExactAmount value) {
        this.exactAmount = value;
    }

}
