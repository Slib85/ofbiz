
package com.bigname.cxml.fulfill;

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
 * <p>Java class for ItemOut complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemOut">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ItemID"/>
 *         &lt;element ref="{}Path" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{}ItemDetail"/>
 *           &lt;element ref="{}BlanketItemDetail"/>
 *         &lt;/choice>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{}SupplierID"/>
 *           &lt;element ref="{}SupplierList"/>
 *         &lt;/choice>
 *         &lt;element ref="{}ShipTo" minOccurs="0"/>
 *         &lt;element ref="{}Shipping" minOccurs="0"/>
 *         &lt;element ref="{}Tax" minOccurs="0"/>
 *         &lt;element ref="{}SpendDetail" minOccurs="0"/>
 *         &lt;element ref="{}Distribution" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Contact" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}TermsOfDelivery" minOccurs="0"/>
 *         &lt;element ref="{}Comments" minOccurs="0"/>
 *         &lt;element ref="{}Tolerances" minOccurs="0"/>
 *         &lt;element ref="{}ControlKeys" minOccurs="0"/>
 *         &lt;element ref="{}ScheduleLine" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{}MasterAgreementReference"/>
 *           &lt;element ref="{}MasterAgreementIDInfo"/>
 *         &lt;/choice>
 *         &lt;element ref="{}ItemOutIndustry" minOccurs="0"/>
 *         &lt;element ref="{}Packaging" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}ReleaseInfo" minOccurs="0"/>
 *         &lt;element ref="{}Batch" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="quantity" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="lineNumber" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="requisitionID" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="agreementItemNumber" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="requestedDeliveryDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="isAdHoc">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="parentLineNumber" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="itemType">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="item"/>
 *             &lt;enumeration value="lean"/>
 *             &lt;enumeration value="composite"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="requiresServiceEntry">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="confirmationDueDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="compositeItemType">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="itemLevel"/>
 *             &lt;enumeration value="groupLevel"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="itemClassification">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="service"/>
 *             &lt;enumeration value="material"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="itemCategory">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="text"/>
 *             &lt;enumeration value="thirdParty"/>
 *             &lt;enumeration value="consignment"/>
 *             &lt;enumeration value="subcontract"/>
 *             &lt;enumeration value="materialGroup"/>
 *             &lt;enumeration value="stockTransfer"/>
 *             &lt;enumeration value="materialUnknown"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="subcontractingType">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="regular"/>
 *             &lt;enumeration value="replacement"/>
 *             &lt;enumeration value="refurbWithChange"/>
 *             &lt;enumeration value="refurbWithoutChange"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="requestedShipmentDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="isReturn">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="returnAuthorizationNumber" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="isDeliveryCompleted">
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
@XmlType(name = "ItemOut", propOrder = {
    "itemID",
    "path",
    "itemDetail",
    "blanketItemDetail",
    "supplierID",
    "supplierList",
    "shipTo",
    "shipping",
    "tax",
    "spendDetail",
    "distribution",
    "contact",
    "termsOfDelivery",
    "comments",
    "tolerances",
    "controlKeys",
    "scheduleLine",
    "masterAgreementReference",
    "masterAgreementIDInfo",
    "itemOutIndustry",
    "packaging",
    "releaseInfo",
    "batch"
})
public class ItemOut {

    @XmlElement(name = "ItemID", required = true)
    protected ItemID itemID;
    @XmlElement(name = "Path")
    protected Path path;
    @XmlElement(name = "ItemDetail")
    protected ItemDetail itemDetail;
    @XmlElement(name = "BlanketItemDetail")
    protected BlanketItemDetail blanketItemDetail;
    @XmlElement(name = "SupplierID")
    protected SupplierID supplierID;
    @XmlElement(name = "SupplierList")
    protected SupplierList supplierList;
    @XmlElement(name = "ShipTo")
    protected ShipTo shipTo;
    @XmlElement(name = "Shipping")
    protected Shipping shipping;
    @XmlElement(name = "Tax")
    protected Tax tax;
    @XmlElement(name = "SpendDetail")
    protected SpendDetail spendDetail;
    @XmlElement(name = "Distribution")
    protected List<Distribution> distribution;
    @XmlElement(name = "Contact")
    protected List<Contact> contact;
    @XmlElement(name = "TermsOfDelivery")
    protected TermsOfDelivery termsOfDelivery;
    @XmlElement(name = "Comments")
    protected Comments comments;
    @XmlElement(name = "Tolerances")
    protected Tolerances tolerances;
    @XmlElement(name = "ControlKeys")
    protected ControlKeys controlKeys;
    @XmlElement(name = "ScheduleLine")
    protected List<ScheduleLine> scheduleLine;
    @XmlElement(name = "MasterAgreementReference")
    protected MasterAgreementReference masterAgreementReference;
    @XmlElement(name = "MasterAgreementIDInfo")
    protected MasterAgreementIDInfo masterAgreementIDInfo;
    @XmlElement(name = "ItemOutIndustry")
    protected ItemOutIndustry itemOutIndustry;
    @XmlElement(name = "Packaging")
    protected List<Packaging> packaging;
    @XmlElement(name = "ReleaseInfo")
    protected ReleaseInfo releaseInfo;
    @XmlElement(name = "Batch")
    protected Batch batch;
    @XmlAttribute(name = "quantity", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String quantity;
    @XmlAttribute(name = "lineNumber")
    @XmlSchemaType(name = "anySimpleType")
    protected String lineNumber;
    @XmlAttribute(name = "requisitionID")
    @XmlSchemaType(name = "anySimpleType")
    protected String requisitionID;
    @XmlAttribute(name = "agreementItemNumber")
    @XmlSchemaType(name = "anySimpleType")
    protected String agreementItemNumber;
    @XmlAttribute(name = "requestedDeliveryDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String requestedDeliveryDate;
    @XmlAttribute(name = "isAdHoc")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isAdHoc;
    @XmlAttribute(name = "parentLineNumber")
    @XmlSchemaType(name = "anySimpleType")
    protected String parentLineNumber;
    @XmlAttribute(name = "itemType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String itemType;
    @XmlAttribute(name = "requiresServiceEntry")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String requiresServiceEntry;
    @XmlAttribute(name = "confirmationDueDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String confirmationDueDate;
    @XmlAttribute(name = "compositeItemType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String compositeItemType;
    @XmlAttribute(name = "itemClassification")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String itemClassification;
    @XmlAttribute(name = "itemCategory")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String itemCategory;
    @XmlAttribute(name = "subcontractingType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String subcontractingType;
    @XmlAttribute(name = "requestedShipmentDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String requestedShipmentDate;
    @XmlAttribute(name = "isReturn")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isReturn;
    @XmlAttribute(name = "returnAuthorizationNumber")
    @XmlSchemaType(name = "anySimpleType")
    protected String returnAuthorizationNumber;
    @XmlAttribute(name = "isDeliveryCompleted")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isDeliveryCompleted;

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
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link Path }
     *     
     */
    public Path getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * 
     * @param value
     *     allowed object is
     *     {@link Path }
     *     
     */
    public void setPath(Path value) {
        this.path = value;
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
     * Gets the value of the blanketItemDetail property.
     * 
     * @return
     *     possible object is
     *     {@link BlanketItemDetail }
     *     
     */
    public BlanketItemDetail getBlanketItemDetail() {
        return blanketItemDetail;
    }

    /**
     * Sets the value of the blanketItemDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link BlanketItemDetail }
     *     
     */
    public void setBlanketItemDetail(BlanketItemDetail value) {
        this.blanketItemDetail = value;
    }

    /**
     * Gets the value of the supplierID property.
     * 
     * @return
     *     possible object is
     *     {@link SupplierID }
     *     
     */
    public SupplierID getSupplierID() {
        return supplierID;
    }

    /**
     * Sets the value of the supplierID property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplierID }
     *     
     */
    public void setSupplierID(SupplierID value) {
        this.supplierID = value;
    }

    /**
     * Gets the value of the supplierList property.
     * 
     * @return
     *     possible object is
     *     {@link SupplierList }
     *     
     */
    public SupplierList getSupplierList() {
        return supplierList;
    }

    /**
     * Sets the value of the supplierList property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplierList }
     *     
     */
    public void setSupplierList(SupplierList value) {
        this.supplierList = value;
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
     * Gets the value of the distribution property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the distribution property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDistribution().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Distribution }
     * 
     * 
     */
    public List<Distribution> getDistribution() {
        if (distribution == null) {
            distribution = new ArrayList<Distribution>();
        }
        return this.distribution;
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
     * Gets the value of the tolerances property.
     * 
     * @return
     *     possible object is
     *     {@link Tolerances }
     *     
     */
    public Tolerances getTolerances() {
        return tolerances;
    }

    /**
     * Sets the value of the tolerances property.
     * 
     * @param value
     *     allowed object is
     *     {@link Tolerances }
     *     
     */
    public void setTolerances(Tolerances value) {
        this.tolerances = value;
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
     * Gets the value of the scheduleLine property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the scheduleLine property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScheduleLine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ScheduleLine }
     * 
     * 
     */
    public List<ScheduleLine> getScheduleLine() {
        if (scheduleLine == null) {
            scheduleLine = new ArrayList<ScheduleLine>();
        }
        return this.scheduleLine;
    }

    /**
     * Gets the value of the masterAgreementReference property.
     * 
     * @return
     *     possible object is
     *     {@link MasterAgreementReference }
     *     
     */
    public MasterAgreementReference getMasterAgreementReference() {
        return masterAgreementReference;
    }

    /**
     * Sets the value of the masterAgreementReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link MasterAgreementReference }
     *     
     */
    public void setMasterAgreementReference(MasterAgreementReference value) {
        this.masterAgreementReference = value;
    }

    /**
     * Gets the value of the masterAgreementIDInfo property.
     * 
     * @return
     *     possible object is
     *     {@link MasterAgreementIDInfo }
     *     
     */
    public MasterAgreementIDInfo getMasterAgreementIDInfo() {
        return masterAgreementIDInfo;
    }

    /**
     * Sets the value of the masterAgreementIDInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link MasterAgreementIDInfo }
     *     
     */
    public void setMasterAgreementIDInfo(MasterAgreementIDInfo value) {
        this.masterAgreementIDInfo = value;
    }

    /**
     * Gets the value of the itemOutIndustry property.
     * 
     * @return
     *     possible object is
     *     {@link ItemOutIndustry }
     *     
     */
    public ItemOutIndustry getItemOutIndustry() {
        return itemOutIndustry;
    }

    /**
     * Sets the value of the itemOutIndustry property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemOutIndustry }
     *     
     */
    public void setItemOutIndustry(ItemOutIndustry value) {
        this.itemOutIndustry = value;
    }

    /**
     * Gets the value of the packaging property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the packaging property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPackaging().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Packaging }
     * 
     * 
     */
    public List<Packaging> getPackaging() {
        if (packaging == null) {
            packaging = new ArrayList<Packaging>();
        }
        return this.packaging;
    }

    /**
     * Gets the value of the releaseInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ReleaseInfo }
     *     
     */
    public ReleaseInfo getReleaseInfo() {
        return releaseInfo;
    }

    /**
     * Sets the value of the releaseInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReleaseInfo }
     *     
     */
    public void setReleaseInfo(ReleaseInfo value) {
        this.releaseInfo = value;
    }

    /**
     * Gets the value of the batch property.
     * 
     * @return
     *     possible object is
     *     {@link Batch }
     *     
     */
    public Batch getBatch() {
        return batch;
    }

    /**
     * Sets the value of the batch property.
     * 
     * @param value
     *     allowed object is
     *     {@link Batch }
     *     
     */
    public void setBatch(Batch value) {
        this.batch = value;
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
     * Gets the value of the agreementItemNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreementItemNumber() {
        return agreementItemNumber;
    }

    /**
     * Sets the value of the agreementItemNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreementItemNumber(String value) {
        this.agreementItemNumber = value;
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
     * Gets the value of the isAdHoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsAdHoc() {
        return isAdHoc;
    }

    /**
     * Sets the value of the isAdHoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsAdHoc(String value) {
        this.isAdHoc = value;
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
     * Gets the value of the requiresServiceEntry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequiresServiceEntry() {
        return requiresServiceEntry;
    }

    /**
     * Sets the value of the requiresServiceEntry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequiresServiceEntry(String value) {
        this.requiresServiceEntry = value;
    }

    /**
     * Gets the value of the confirmationDueDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfirmationDueDate() {
        return confirmationDueDate;
    }

    /**
     * Sets the value of the confirmationDueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfirmationDueDate(String value) {
        this.confirmationDueDate = value;
    }

    /**
     * Gets the value of the compositeItemType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompositeItemType() {
        return compositeItemType;
    }

    /**
     * Sets the value of the compositeItemType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompositeItemType(String value) {
        this.compositeItemType = value;
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
     * Gets the value of the itemCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemCategory() {
        return itemCategory;
    }

    /**
     * Sets the value of the itemCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemCategory(String value) {
        this.itemCategory = value;
    }

    /**
     * Gets the value of the subcontractingType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubcontractingType() {
        return subcontractingType;
    }

    /**
     * Sets the value of the subcontractingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubcontractingType(String value) {
        this.subcontractingType = value;
    }

    /**
     * Gets the value of the requestedShipmentDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestedShipmentDate() {
        return requestedShipmentDate;
    }

    /**
     * Sets the value of the requestedShipmentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestedShipmentDate(String value) {
        this.requestedShipmentDate = value;
    }

    /**
     * Gets the value of the isReturn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsReturn() {
        return isReturn;
    }

    /**
     * Sets the value of the isReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsReturn(String value) {
        this.isReturn = value;
    }

    /**
     * Gets the value of the returnAuthorizationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnAuthorizationNumber() {
        return returnAuthorizationNumber;
    }

    /**
     * Sets the value of the returnAuthorizationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnAuthorizationNumber(String value) {
        this.returnAuthorizationNumber = value;
    }

    /**
     * Gets the value of the isDeliveryCompleted property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsDeliveryCompleted() {
        return isDeliveryCompleted;
    }

    /**
     * Sets the value of the isDeliveryCompleted property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsDeliveryCompleted(String value) {
        this.isDeliveryCompleted = value;
    }

}
