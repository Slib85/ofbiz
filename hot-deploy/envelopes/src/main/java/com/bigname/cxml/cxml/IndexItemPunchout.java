
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IndexItemPunchout complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IndexItemPunchout">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ItemID"/>
 *         &lt;element ref="{}PunchoutDetail"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndexItemPunchout", propOrder = {
    "itemID",
    "punchoutDetail"
})
public class IndexItemPunchout {

    @XmlElement(name = "ItemID", required = true)
    protected ItemID itemID;
    @XmlElement(name = "PunchoutDetail", required = true)
    protected PunchoutDetail punchoutDetail;

    /**
     * Gets the value of the itemID property.
     * 
     * @return
     *     possible object is
     *     {@link ItemID }
     *     
     */
    public ItemID getItemID() {
        return itemID;
    }

    /**
     * Sets the value of the itemID property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemID }
     *     
     */
    public void setItemID(ItemID value) {
        this.itemID = value;
    }

    /**
     * Gets the value of the punchoutDetail property.
     * 
     * @return
     *     possible object is
     *     {@link PunchoutDetail }
     *     
     */
    public PunchoutDetail getPunchoutDetail() {
        return punchoutDetail;
    }

    /**
     * Sets the value of the punchoutDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link PunchoutDetail }
     *     
     */
    public void setPunchoutDetail(PunchoutDetail value) {
        this.punchoutDetail = value;
    }

}
