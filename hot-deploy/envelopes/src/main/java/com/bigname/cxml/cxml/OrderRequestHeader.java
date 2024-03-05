
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
 * <p>Java class for OrderRequestHeader complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderRequestHeader">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Total"/>
 *         &lt;element ref="{}ShipTo" minOccurs="0"/>
 *         &lt;element ref="{}BillTo"/>
 *         &lt;element ref="{}LegalEntity" minOccurs="0"/>
 *         &lt;element ref="{}OrganizationalUnit" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Shipping" minOccurs="0"/>
 *         &lt;element ref="{}Tax" minOccurs="0"/>
 *         &lt;element ref="{}Payment" minOccurs="0"/>
 *         &lt;element ref="{}PaymentTerm" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Contact" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Comments" minOccurs="0"/>
 *         &lt;element ref="{}Followup" minOccurs="0"/>
 *         &lt;element ref="{}ControlKeys" minOccurs="0"/>
 *         &lt;element ref="{}DocumentReference" minOccurs="0"/>
 *         &lt;element ref="{}SupplierOrderInfo" minOccurs="0"/>
 *         &lt;element ref="{}TermsOfDelivery" minOccurs="0"/>
 *         &lt;element ref="{}DeliveryPeriod" minOccurs="0"/>
 *         &lt;element ref="{}IdReference" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}OrderRequestHeaderIndustry" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="orderID" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="orderDate" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="orderType" default="regular">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="blanket"/>
 *             &lt;enumeration value="regular"/>
 *             &lt;enumeration value="release"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="releaseRequired">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="type" default="new">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="new"/>
 *             &lt;enumeration value="delete"/>
 *             &lt;enumeration value="update"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="orderVersion" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="isInternalVersion">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="parentAgreementID" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="parentAgreementPayloadID" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="effectiveDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="expirationDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="agreementID" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="agreementPayloadID" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="requisitionID" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="shipComplete">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="pickUpDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="requestedDeliveryDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderRequestHeader", propOrder = {
    "total",
    "shipTo",
    "billTo",
    "legalEntity",
    "organizationalUnit",
    "shipping",
    "tax",
    "payment",
    "paymentTerm",
    "contact",
    "comments",
    "followup",
    "controlKeys",
    "documentReference",
    "supplierOrderInfo",
    "termsOfDelivery",
    "deliveryPeriod",
    "idReference",
    "orderRequestHeaderIndustry",
    "extrinsic"
})
public class OrderRequestHeader {

    @XmlElement(name = "Total", required = true)
    protected Total total;
    @XmlElement(name = "ShipTo")
    protected ShipTo shipTo;
    @XmlElement(name = "BillTo", required = true)
    protected BillTo billTo;
    @XmlElement(name = "LegalEntity")
    protected LegalEntity legalEntity;
    @XmlElement(name = "OrganizationalUnit")
    protected List<OrganizationalUnit> organizationalUnit;
    @XmlElement(name = "Shipping")
    protected Shipping shipping;
    @XmlElement(name = "Tax")
    protected Tax tax;
    @XmlElement(name = "Payment")
    protected Payment payment;
    @XmlElement(name = "PaymentTerm")
    protected List<PaymentTerm> paymentTerm;
    @XmlElement(name = "Contact")
    protected List<Contact> contact;
    @XmlElement(name = "Comments")
    protected Comments comments;
    @XmlElement(name = "Followup")
    protected Followup followup;
    @XmlElement(name = "ControlKeys")
    protected ControlKeys controlKeys;
    @XmlElement(name = "DocumentReference")
    protected DocumentReference documentReference;
    @XmlElement(name = "SupplierOrderInfo")
    protected SupplierOrderInfo supplierOrderInfo;
    @XmlElement(name = "TermsOfDelivery")
    protected TermsOfDelivery termsOfDelivery;
    @XmlElement(name = "DeliveryPeriod")
    protected DeliveryPeriod deliveryPeriod;
    @XmlElement(name = "IdReference")
    protected List<IdReference> idReference;
    @XmlElement(name = "OrderRequestHeaderIndustry")
    protected OrderRequestHeaderIndustry orderRequestHeaderIndustry;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(name = "orderID", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String orderID;
    @XmlAttribute(name = "orderDate", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String orderDate;
    @XmlAttribute(name = "orderType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String orderType;
    @XmlAttribute(name = "releaseRequired")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String releaseRequired;
    @XmlAttribute(name = "type")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String type;
    @XmlAttribute(name = "orderVersion")
    @XmlSchemaType(name = "anySimpleType")
    protected String orderVersion;
    @XmlAttribute(name = "isInternalVersion")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isInternalVersion;
    @XmlAttribute(name = "parentAgreementID")
    @XmlSchemaType(name = "anySimpleType")
    protected String parentAgreementID;
    @XmlAttribute(name = "parentAgreementPayloadID")
    @XmlSchemaType(name = "anySimpleType")
    protected String parentAgreementPayloadID;
    @XmlAttribute(name = "effectiveDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String effectiveDate;
    @XmlAttribute(name = "expirationDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String expirationDate;
    @XmlAttribute(name = "agreementID")
    @XmlSchemaType(name = "anySimpleType")
    protected String agreementID;
    @XmlAttribute(name = "agreementPayloadID")
    @XmlSchemaType(name = "anySimpleType")
    protected String agreementPayloadID;
    @XmlAttribute(name = "requisitionID")
    @XmlSchemaType(name = "anySimpleType")
    protected String requisitionID;
    @XmlAttribute(name = "shipComplete")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String shipComplete;
    @XmlAttribute(name = "pickUpDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String pickUpDate;
    @XmlAttribute(name = "requestedDeliveryDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String requestedDeliveryDate;

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
     * Gets the value of the billTo property.
     * 
     * @return
     *     possible object is
     *     {@link BillTo }
     *     
     */
    public BillTo getBillTo() {
        return billTo;
    }

    /**
     * Sets the value of the billTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillTo }
     *     
     */
    public void setBillTo(BillTo value) {
        this.billTo = value;
    }

    /**
     * Gets the value of the legalEntity property.
     * 
     * @return
     *     possible object is
     *     {@link LegalEntity }
     *     
     */
    public LegalEntity getLegalEntity() {
        return legalEntity;
    }

    /**
     * Sets the value of the legalEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link LegalEntity }
     *     
     */
    public void setLegalEntity(LegalEntity value) {
        this.legalEntity = value;
    }

    /**
     * Gets the value of the organizationalUnit property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the organizationalUnit property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrganizationalUnit().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrganizationalUnit }
     * 
     * 
     */
    public List<OrganizationalUnit> getOrganizationalUnit() {
        if (organizationalUnit == null) {
            organizationalUnit = new ArrayList<OrganizationalUnit>();
        }
        return this.organizationalUnit;
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
     * Gets the value of the payment property.
     * 
     * @return
     *     possible object is
     *     {@link Payment }
     *     
     */
    public Payment getPayment() {
        return payment;
    }

    /**
     * Sets the value of the payment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Payment }
     *     
     */
    public void setPayment(Payment value) {
        this.payment = value;
    }

    /**
     * Gets the value of the paymentTerm property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentTerm property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentTerm().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentTerm }
     * 
     * 
     */
    public List<PaymentTerm> getPaymentTerm() {
        if (paymentTerm == null) {
            paymentTerm = new ArrayList<PaymentTerm>();
        }
        return this.paymentTerm;
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
     * Gets the value of the followup property.
     * 
     * @return
     *     possible object is
     *     {@link Followup }
     *     
     */
    public Followup getFollowup() {
        return followup;
    }

    /**
     * Sets the value of the followup property.
     * 
     * @param value
     *     allowed object is
     *     {@link Followup }
     *     
     */
    public void setFollowup(Followup value) {
        this.followup = value;
    }

    /**
     * Gets the value of the controlKeys property.
     * 
     * @return
     *     possible object is
     *     {@link ControlKeys }
     *     
     */
    public ControlKeys getControlKeys() {
        return controlKeys;
    }

    /**
     * Sets the value of the controlKeys property.
     * 
     * @param value
     *     allowed object is
     *     {@link ControlKeys }
     *     
     */
    public void setControlKeys(ControlKeys value) {
        this.controlKeys = value;
    }

    /**
     * Gets the value of the documentReference property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentReference }
     *     
     */
    public DocumentReference getDocumentReference() {
        return documentReference;
    }

    /**
     * Sets the value of the documentReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentReference }
     *     
     */
    public void setDocumentReference(DocumentReference value) {
        this.documentReference = value;
    }

    /**
     * Gets the value of the supplierOrderInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SupplierOrderInfo }
     *     
     */
    public SupplierOrderInfo getSupplierOrderInfo() {
        return supplierOrderInfo;
    }

    /**
     * Sets the value of the supplierOrderInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplierOrderInfo }
     *     
     */
    public void setSupplierOrderInfo(SupplierOrderInfo value) {
        this.supplierOrderInfo = value;
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
     * Gets the value of the deliveryPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link DeliveryPeriod }
     *     
     */
    public DeliveryPeriod getDeliveryPeriod() {
        return deliveryPeriod;
    }

    /**
     * Sets the value of the deliveryPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliveryPeriod }
     *     
     */
    public void setDeliveryPeriod(DeliveryPeriod value) {
        this.deliveryPeriod = value;
    }

    /**
     * Gets the value of the idReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the idReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IdReference }
     * 
     * 
     */
    public List<IdReference> getIdReference() {
        if (idReference == null) {
            idReference = new ArrayList<IdReference>();
        }
        return this.idReference;
    }

    /**
     * Gets the value of the orderRequestHeaderIndustry property.
     * 
     * @return
     *     possible object is
     *     {@link OrderRequestHeaderIndustry }
     *     
     */
    public OrderRequestHeaderIndustry getOrderRequestHeaderIndustry() {
        return orderRequestHeaderIndustry;
    }

    /**
     * Sets the value of the orderRequestHeaderIndustry property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderRequestHeaderIndustry }
     *     
     */
    public void setOrderRequestHeaderIndustry(OrderRequestHeaderIndustry value) {
        this.orderRequestHeaderIndustry = value;
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

    /**
     * Gets the value of the orderID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderID() {
        return orderID;
    }

    /**
     * Sets the value of the orderID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderID(String value) {
        this.orderID = value;
    }

    /**
     * Gets the value of the orderDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the value of the orderDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderDate(String value) {
        this.orderDate = value;
    }

    /**
     * Gets the value of the orderType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderType() {
        if (orderType == null) {
            return "regular";
        } else {
            return orderType;
        }
    }

    /**
     * Sets the value of the orderType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderType(String value) {
        this.orderType = value;
    }

    /**
     * Gets the value of the releaseRequired property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReleaseRequired() {
        return releaseRequired;
    }

    /**
     * Sets the value of the releaseRequired property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReleaseRequired(String value) {
        this.releaseRequired = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        if (type == null) {
            return "new";
        } else {
            return type;
        }
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the orderVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderVersion() {
        return orderVersion;
    }

    /**
     * Sets the value of the orderVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderVersion(String value) {
        this.orderVersion = value;
    }

    /**
     * Gets the value of the isInternalVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsInternalVersion() {
        return isInternalVersion;
    }

    /**
     * Sets the value of the isInternalVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsInternalVersion(String value) {
        this.isInternalVersion = value;
    }

    /**
     * Gets the value of the parentAgreementID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentAgreementID() {
        return parentAgreementID;
    }

    /**
     * Sets the value of the parentAgreementID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentAgreementID(String value) {
        this.parentAgreementID = value;
    }

    /**
     * Gets the value of the parentAgreementPayloadID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentAgreementPayloadID() {
        return parentAgreementPayloadID;
    }

    /**
     * Sets the value of the parentAgreementPayloadID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentAgreementPayloadID(String value) {
        this.parentAgreementPayloadID = value;
    }

    /**
     * Gets the value of the effectiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the value of the effectiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffectiveDate(String value) {
        this.effectiveDate = value;
    }

    /**
     * Gets the value of the expirationDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the value of the expirationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpirationDate(String value) {
        this.expirationDate = value;
    }

    /**
     * Gets the value of the agreementID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreementID() {
        return agreementID;
    }

    /**
     * Sets the value of the agreementID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreementID(String value) {
        this.agreementID = value;
    }

    /**
     * Gets the value of the agreementPayloadID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreementPayloadID() {
        return agreementPayloadID;
    }

    /**
     * Sets the value of the agreementPayloadID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreementPayloadID(String value) {
        this.agreementPayloadID = value;
    }

    /**
     * Gets the value of the requisitionID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequisitionID() {
        return requisitionID;
    }

    /**
     * Sets the value of the requisitionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequisitionID(String value) {
        this.requisitionID = value;
    }

    /**
     * Gets the value of the shipComplete property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipComplete() {
        return shipComplete;
    }

    /**
     * Sets the value of the shipComplete property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipComplete(String value) {
        this.shipComplete = value;
    }

    /**
     * Gets the value of the pickUpDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPickUpDate() {
        return pickUpDate;
    }

    /**
     * Sets the value of the pickUpDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPickUpDate(String value) {
        this.pickUpDate = value;
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

}
