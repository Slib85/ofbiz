
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RelationshipInformation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RelationshipInformation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}PaymentRelationshipInformation" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelationshipInformation", propOrder = {
    "paymentRelationshipInformation"
})
public class RelationshipInformation {

    @XmlElement(name = "PaymentRelationshipInformation")
    protected PaymentRelationshipInformation paymentRelationshipInformation;

    /**
     * Gets the value of the paymentRelationshipInformation property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentRelationshipInformation }
     *     
     */
    public PaymentRelationshipInformation getPaymentRelationshipInformation() {
        return paymentRelationshipInformation;
    }

    /**
     * Sets the value of the paymentRelationshipInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentRelationshipInformation }
     *     
     */
    public void setPaymentRelationshipInformation(PaymentRelationshipInformation value) {
        this.paymentRelationshipInformation = value;
    }

}
