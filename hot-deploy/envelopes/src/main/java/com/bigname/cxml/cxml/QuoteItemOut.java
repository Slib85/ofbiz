
package com.bigname.cxml.cxml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for QuoteItemOut complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QuoteItemOut">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ItemID" minOccurs="0"/>
 *         &lt;element ref="{}ItemDetail"/>
 *         &lt;element ref="{}ShipTo" minOccurs="0"/>
 *         &lt;element ref="{}Shipping" minOccurs="0"/>
 *         &lt;element ref="{}Tax" minOccurs="0"/>
 *         &lt;element ref="{}SpendDetail" minOccurs="0"/>
 *         &lt;element ref="{}Total" minOccurs="0"/>
 *         &lt;element ref="{}TermsOfDelivery" minOccurs="0"/>
 *         &lt;element ref="{}ReferenceDocumentInfo" minOccurs="0"/>
 *         &lt;element ref="{}Contact" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Comments" minOccurs="0"/>
 *         &lt;element ref="{}Alternative" minOccurs="0"/>
 *         &lt;element ref="{}SupplierSelector" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="quantity" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="lineNumber" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="parentLineNumber" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="requestedDeliveryDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="itemClassification">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="service"/>
 *             &lt;enumeration value="material"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="itemType">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="item"/>
 *             &lt;enumeration value="lean"/>
 *             &lt;enumeration value="composite"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="serviceLineType">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="blanket"/>
 *             &lt;enumeration value="standard"/>
 *             &lt;enumeration value="contingency"/>
 *             &lt;enumeration value="information"/>
 *             &lt;enumeration value="openquantity"/>
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
@XmlType(name = "QuoteItemOut", propOrder = {
    "itemID",
    "itemDetail",
    "shipTo",
    "shipping",
    "tax",
    "spendDetail",
    "total",
    "termsOfDelivery",
    "referenceDocumentInfo",
    "contact",
    "comments",
    "alternative",
    "supplierSelector"
})
public class QuoteItemOut {

    @XmlElement(name = "ItemID")
    protected ItemID itemID;
    @XmlElement(name = "ItemDetail", required = true)
    protected ItemDetail itemDetail;
    @XmlElement(name = "ShipTo")
    protected ShipTo shipTo;
    @XmlElement(name = "Shipping")
    protected Shipping shipping;
    @XmlElement(name = "Tax")
    protected Tax tax;
    @XmlElement(name = "SpendDetail")
    protected SpendDetail spendDetail;
    @XmlElement(name = "Total")
    protected Total total;
    @XmlElement(name = "TermsOfDelivery")
    protected TermsOfDelivery termsOfDelivery;
    @XmlElement(name = "ReferenceDocumentInfo")
    protected ReferenceDocumentInfo referenceDocumentInfo;
    @XmlElement(name = "Contact")
    protected List<Contact> contact;
    @XmlElement(name = "Comments")
    protected Comments comments;
    @XmlElement(name = "Alternative")
    protected Alternative alternative;
    @XmlElement(name = "SupplierSelector")
    protected SupplierSelector supplierSelector;
    @XmlAttribute(name = "quantity", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String quantity;
    @XmlAttribute(name = "lineNumber")
    @XmlSchemaType(name = "anySimpleType")
    protected String lineNumber;
    @XmlAttribute(name = "parentLineNumber")
    @XmlSchemaType(name = "anySimpleType")
    protected String parentLineNumber;
    @XmlAttribute(name = "requestedDeliveryDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String requestedDeliveryDate;
    @XmlAttribute(name = "itemClassification")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String itemClassification;
    @XmlAttribute(name = "itemType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String itemType;
    @XmlAttribute(name = "serviceLineType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String serviceLineType;

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
     * Gets the value of the shipTo property.
     * 
     * @return
     *     possible object is
     *     {@link ShipTo }
     *     
     */
    public ShipTo getShipTo() {
        return shipTo;
    }

    /**
     * Sets the value of the shipTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipTo }
     *     
     */
    public void setShipTo(ShipTo value) {
        this.shipTo = value;
    }

    /**
     * Gets the value of the shipping property.
     * 
     * @return
     *     possible object is
     *     {@link Shipping }
     *     
     */
    public Shipping getShipping() {
        return shipping;
    }

    /**
     * Sets the value of the shipping property.
     * 
     * @param value
     *     allowed object is
     *     {@link Shipping }
     *     
     */
    public void setShipping(Shipping value) {
        this.shipping = value;
    }

    /**
     * Gets the value of the tax property.
     * 
     * @return
     *     possible object is
     *     {@link Tax }
     *     
     */
    public Tax getTax() {
        return tax;
    }

    /**
     * Sets the value of the tax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Tax }
     *     
     */
    public void setTax(Tax value) {
        this.tax = value;
    }

    /**
     * Gets the value of the spendDetail property.
     * 
     * @return
     *     possible object is
     *     {@link SpendDetail }
     *     
     */
    public SpendDetail getSpendDetail() {
        return spendDetail;
    }

    /**
     * Sets the value of the spendDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpendDetail }
     *     
     */
    public void setSpendDetail(SpendDetail value) {
        this.spendDetail = value;
    }

    /**
     * Gets the value of the total property.
     * 
     * @return
     *     possible object is
     *     {@link Total }
     *     
     */
    public Total getTotal() {
        return total;
    }

    /**
     * Sets the value of the total property.
     * 
     * @param value
     *     allowed object is
     *     {@link Total }
     *     
     */
    public void setTotal(Total value) {
        this.total = value;
    }

    /**
     * Gets the value of the termsOfDelivery property.
     * 
     * @return
     *     possible object is
     *     {@link TermsOfDelivery }
     *     
     */
    public TermsOfDelivery getTermsOfDelivery() {
        return termsOfDelivery;
    }

    /**
     * Sets the value of the termsOfDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link TermsOfDelivery }
     *     
     */
    public void setTermsOfDelivery(TermsOfDelivery value) {
        this.termsOfDelivery = value;
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
     * Gets the value of the contact property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contact property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContact().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Contact }
     * 
     * 
     */
    public List<Contact> getContact() {
        if (contact == null) {
            contact = new ArrayList<Contact>();
        }
        return this.contact;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link Comments }
     *     
     */
    public Comments getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link Comments }
     *     
     */
    public void setComments(Comments value) {
        this.comments = value;
    }

    /**
     * Gets the value of the alternative property.
     * 
     * @return
     *     possible object is
     *     {@link Alternative }
     *     
     */
    public Alternative getAlternative() {
        return alternative;
    }

    /**
     * Sets the value of the alternative property.
     * 
     * @param value
     *     allowed object is
     *     {@link Alternative }
     *     
     */
    public void setAlternative(Alternative value) {
        this.alternative = value;
    }

    /**
     * Gets the value of the supplierSelector property.
     * 
     * @return
     *     possible object is
     *     {@link SupplierSelector }
     *     
     */
    public SupplierSelector getSupplierSelector() {
        return supplierSelector;
    }

    /**
     * Sets the value of the supplierSelector property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplierSelector }
     *     
     */
    public void setSupplierSelector(SupplierSelector value) {
        this.supplierSelector = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuantity(String value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the lineNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLineNumber() {
        return lineNumber;
    }

    /**
     * Sets the value of the lineNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineNumber(String value) {
        this.lineNumber = value;
    }

    /**
     * Gets the value of the parentLineNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentLineNumber() {
        return parentLineNumber;
    }

    /**
     * Sets the value of the parentLineNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentLineNumber(String value) {
        this.parentLineNumber = value;
    }

    /**
     * Gets the value of the requestedDeliveryDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestedDeliveryDate() {
        return requestedDeliveryDate;
    }

    /**
     * Sets the value of the requestedDeliveryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestedDeliveryDate(String value) {
        this.requestedDeliveryDate = value;
    }

    /**
     * Gets the value of the itemClassification property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemClassification() {
        return itemClassification;
    }

    /**
     * Sets the value of the itemClassification property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemClassification(String value) {
        this.itemClassification = value;
    }

    /**
     * Gets the value of the itemType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemType() {
        return itemType;
    }

    /**
     * Sets the value of the itemType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemType(String value) {
        this.itemType = value;
    }

    /**
     * Gets the value of the serviceLineType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceLineType() {
        return serviceLineType;
    }

    /**
     * Sets the value of the serviceLineType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceLineType(String value) {
        this.serviceLineType = value;
    }

}
