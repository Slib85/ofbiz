
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ItemDetailIndustry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemDetailIndustry">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ItemDetailRetail" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="isConfigurableMaterial">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemDetailIndustry", propOrder = {
    "itemDetailRetail"
})
public class ItemDetailIndustry {

    @XmlElement(name = "ItemDetailRetail")
    protected ItemDetailRetail itemDetailRetail;
    @XmlAttribute(name = "isConfigurableMaterial")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isConfigurableMaterial;

    /**
     * Gets the value of the itemDetailRetail property.
     * 
     * @return
     *     possible object is
     *     {@link ItemDetailRetail }
     *     
     */
    public ItemDetailRetail getItemDetailRetail() {
        return itemDetailRetail;
    }

    /**
     * Sets the value of the itemDetailRetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemDetailRetail }
     *     
     */
    public void setItemDetailRetail(ItemDetailRetail value) {
        this.itemDetailRetail = value;
    }

    /**
     * Gets the value of the isConfigurableMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsConfigurableMaterial() {
        return isConfigurableMaterial;
    }

    /**
     * Sets the value of the isConfigurableMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsConfigurableMaterial(String value) {
        this.isConfigurableMaterial = value;
    }

}
