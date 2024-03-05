
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MinimumLimit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MinimumLimit">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ComparatorInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MinimumLimit", propOrder = {
    "comparatorInfo"
})
public class MinimumLimit {

    @XmlElement(name = "ComparatorInfo", required = true)
    protected ComparatorInfo comparatorInfo;

    /**
     * Gets the value of the comparatorInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ComparatorInfo }
     *     
     */
    public ComparatorInfo getComparatorInfo() {
        return comparatorInfo;
    }

    /**
     * Sets the value of the comparatorInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComparatorInfo }
     *     
     */
    public void setComparatorInfo(ComparatorInfo value) {
        this.comparatorInfo = value;
    }

}
