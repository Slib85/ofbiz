
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceTimeCardDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceTimeCardDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element ref="{}TimeCardReference"/>
 *           &lt;element ref="{}TimeCardIDInfo"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceTimeCardDetail", propOrder = {
    "timeCardReference",
    "timeCardIDInfo"
})
public class InvoiceTimeCardDetail {

    @XmlElement(name = "TimeCardReference")
    protected TimeCardReference timeCardReference;
    @XmlElement(name = "TimeCardIDInfo")
    protected TimeCardIDInfo timeCardIDInfo;

    /**
     * Gets the value of the timeCardReference property.
     * 
     * @return
     *     possible object is
     *     {@link TimeCardReference }
     *     
     */
    public TimeCardReference getTimeCardReference() {
        return timeCardReference;
    }

    /**
     * Sets the value of the timeCardReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeCardReference }
     *     
     */
    public void setTimeCardReference(TimeCardReference value) {
        this.timeCardReference = value;
    }

    /**
     * Gets the value of the timeCardIDInfo property.
     * 
     * @return
     *     possible object is
     *     {@link TimeCardIDInfo }
     *     
     */
    public TimeCardIDInfo getTimeCardIDInfo() {
        return timeCardIDInfo;
    }

    /**
     * Sets the value of the timeCardIDInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeCardIDInfo }
     *     
     */
    public void setTimeCardIDInfo(TimeCardIDInfo value) {
        this.timeCardIDInfo = value;
    }

}
