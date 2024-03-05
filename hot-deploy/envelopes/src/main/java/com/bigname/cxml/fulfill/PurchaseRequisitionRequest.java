
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PurchaseRequisitionRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PurchaseRequisitionRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}PurchaseRequisition"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PurchaseRequisitionRequest", propOrder = {
    "purchaseRequisition"
})
public class PurchaseRequisitionRequest {

    @XmlElement(name = "PurchaseRequisition", required = true)
    protected PurchaseRequisition purchaseRequisition;

    /**
     * Gets the value of the purchaseRequisition property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseRequisition }
     *     
     */
    public PurchaseRequisition getPurchaseRequisition() {
        return purchaseRequisition;
    }

    /**
     * Sets the value of the purchaseRequisition property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseRequisition }
     *     
     */
    public void setPurchaseRequisition(PurchaseRequisition value) {
        this.purchaseRequisition = value;
    }

}
