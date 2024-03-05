
package com.bigname.cxml.cxml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConsignmentMovement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConsignmentMovement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ProductMovementItemIDInfo"/>
 *         &lt;element ref="{}InvoiceItemIDInfo" minOccurs="0"/>
 *         &lt;element ref="{}ReferenceDocumentInfo" minOccurs="0"/>
 *         &lt;element ref="{}MovementQuantity"/>
 *         &lt;element ref="{}SubtotalAmount"/>
 *         &lt;element ref="{}UnitPrice" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsignmentMovement", propOrder = {
    "productMovementItemIDInfo",
    "invoiceItemIDInfo",
    "referenceDocumentInfo",
    "movementQuantity",
    "subtotalAmount",
    "unitPrice",
    "extrinsic"
})
public class ConsignmentMovement {

    @XmlElement(name = "ProductMovementItemIDInfo", required = true)
    protected ProductMovementItemIDInfo productMovementItemIDInfo;
    @XmlElement(name = "InvoiceItemIDInfo")
    protected InvoiceItemIDInfo invoiceItemIDInfo;
    @XmlElement(name = "ReferenceDocumentInfo")
    protected ReferenceDocumentInfo referenceDocumentInfo;
    @XmlElement(name = "MovementQuantity", required = true)
    protected MovementQuantity movementQuantity;
    @XmlElement(name = "SubtotalAmount", required = true)
    protected SubtotalAmount subtotalAmount;
    @XmlElement(name = "UnitPrice")
    protected UnitPrice unitPrice;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;

    /**
     * Gets the value of the productMovementItemIDInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ProductMovementItemIDInfo }
     *     
     */
    public ProductMovementItemIDInfo getProductMovementItemIDInfo() {
        return productMovementItemIDInfo;
    }

    /**
     * Sets the value of the productMovementItemIDInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductMovementItemIDInfo }
     *     
     */
    public void setProductMovementItemIDInfo(ProductMovementItemIDInfo value) {
        this.productMovementItemIDInfo = value;
    }

    /**
     * Gets the value of the invoiceItemIDInfo property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceItemIDInfo }
     *     
     */
    public InvoiceItemIDInfo getInvoiceItemIDInfo() {
        return invoiceItemIDInfo;
    }

    /**
     * Sets the value of the invoiceItemIDInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceItemIDInfo }
     *     
     */
    public void setInvoiceItemIDInfo(InvoiceItemIDInfo value) {
        this.invoiceItemIDInfo = value;
    }

    /**
     * Gets the value of the referenceDocumentInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceDocumentInfo }
     *     
     */
    public ReferenceDocumentInfo getReferenceDocumentInfo() {
        return referenceDocumentInfo;
    }

    /**
     * Sets the value of the referenceDocumentInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceDocumentInfo }
     *     
     */
    public void setReferenceDocumentInfo(ReferenceDocumentInfo value) {
        this.referenceDocumentInfo = value;
    }

    /**
     * Gets the value of the movementQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link MovementQuantity }
     *     
     */
    public MovementQuantity getMovementQuantity() {
        return movementQuantity;
    }

    /**
     * Sets the value of the movementQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link MovementQuantity }
     *     
     */
    public void setMovementQuantity(MovementQuantity value) {
        this.movementQuantity = value;
    }

    /**
     * Gets the value of the subtotalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link SubtotalAmount }
     *     
     */
    public SubtotalAmount getSubtotalAmount() {
        return subtotalAmount;
    }

    /**
     * Sets the value of the subtotalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubtotalAmount }
     *     
     */
    public void setSubtotalAmount(SubtotalAmount value) {
        this.subtotalAmount = value;
    }

    /**
     * Gets the value of the unitPrice property.
     * 
     * @return
     *     possible object is
     *     {@link UnitPrice }
     *     
     */
    public UnitPrice getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the value of the unitPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitPrice }
     *     
     */
    public void setUnitPrice(UnitPrice value) {
        this.unitPrice = value;
    }

    /**
     * Gets the value of the extrinsic property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extrinsic property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtrinsic().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Extrinsic }
     * 
     * 
     */
    public List<Extrinsic> getExtrinsic() {
        if (extrinsic == null) {
            extrinsic = new ArrayList<Extrinsic>();
        }
        return this.extrinsic;
    }

}
