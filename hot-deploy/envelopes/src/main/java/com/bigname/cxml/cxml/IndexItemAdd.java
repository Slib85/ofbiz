
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IndexItemAdd complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IndexItemAdd">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ItemID"/>
 *         &lt;element ref="{}ItemDetail"/>
 *         &lt;element ref="{}IndexItemDetail"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndexItemAdd", propOrder = {
    "itemID",
    "itemDetail",
    "indexItemDetail"
})
public class IndexItemAdd {

    @XmlElement(name = "ItemID", required = true)
    protected ItemID itemID;
    @XmlElement(name = "ItemDetail", required = true)
    protected ItemDetail itemDetail;
    @XmlElement(name = "IndexItemDetail", required = true)
    protected IndexItemDetail indexItemDetail;

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
     * Gets the value of the itemDetail property.
     * 
     * @return
     *     possible object is
     *     {@link ItemDetail }
     *     
     */
    public ItemDetail getItemDetail() {
        return itemDetail;
    }

    /**
     * Sets the value of the itemDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemDetail }
     *     
     */
    public void setItemDetail(ItemDetail value) {
        this.itemDetail = value;
    }

    /**
     * Gets the value of the indexItemDetail property.
     * 
     * @return
     *     possible object is
     *     {@link IndexItemDetail }
     *     
     */
    public IndexItemDetail getIndexItemDetail() {
        return indexItemDetail;
    }

    /**
     * Sets the value of the indexItemDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndexItemDetail }
     *     
     */
    public void setIndexItemDetail(IndexItemDetail value) {
        this.indexItemDetail = value;
    }

}
