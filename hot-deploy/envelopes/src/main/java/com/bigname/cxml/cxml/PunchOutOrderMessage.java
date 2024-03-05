
package com.bigname.cxml.cxml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PunchOutOrderMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PunchOutOrderMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}BuyerCookie"/>
 *         &lt;element ref="{}PunchOutOrderMessageHeader"/>
 *         &lt;element ref="{}ItemIn" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PunchOutOrderMessage", propOrder = {
    "buyerCookie",
    "punchOutOrderMessageHeader",
    "itemIn"
})
public class PunchOutOrderMessage {

    @XmlElement(name = "BuyerCookie", required = true)
    protected BuyerCookie buyerCookie;
    @XmlElement(name = "PunchOutOrderMessageHeader", required = true)
    protected PunchOutOrderMessageHeader punchOutOrderMessageHeader;
    @XmlElement(name = "ItemIn")
    protected List<ItemIn> itemIn;

    /**
     * Gets the value of the buyerCookie property.
     * 
     * @return
     *     possible object is
     *     {@link BuyerCookie }
     *     
     */
    public BuyerCookie getBuyerCookie() {
        return buyerCookie;
    }

    /**
     * Sets the value of the buyerCookie property.
     * 
     * @param value
     *     allowed object is
     *     {@link BuyerCookie }
     *     
     */
    public void setBuyerCookie(BuyerCookie value) {
        this.buyerCookie = value;
    }

    /**
     * Gets the value of the punchOutOrderMessageHeader property.
     * 
     * @return
     *     possible object is
     *     {@link PunchOutOrderMessageHeader }
     *     
     */
    public PunchOutOrderMessageHeader getPunchOutOrderMessageHeader() {
        return punchOutOrderMessageHeader;
    }

    /**
     * Sets the value of the punchOutOrderMessageHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link PunchOutOrderMessageHeader }
     *     
     */
    public void setPunchOutOrderMessageHeader(PunchOutOrderMessageHeader value) {
        this.punchOutOrderMessageHeader = value;
    }

    /**
     * Gets the value of the itemIn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itemIn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItemIn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemIn }
     * 
     * 
     */
    public List<ItemIn> getItemIn() {
        if (itemIn == null) {
            itemIn = new ArrayList<ItemIn>();
        }
        return this.itemIn;
    }

}
