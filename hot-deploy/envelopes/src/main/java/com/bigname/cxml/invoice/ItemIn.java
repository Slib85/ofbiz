
package com.bigname.cxml.invoice;

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
 * <p>Java class for ItemIn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemIn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ItemID"/>
 *         &lt;element ref="{}Path" minOccurs="0"/>
 *         &lt;element ref="{}ItemDetail"/>
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
 *         &lt;element ref="{}Comments" minOccurs="0"/>
 *         &lt;element ref="{}ScheduleLine" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}BillTo" minOccurs="0"/>
 *         &lt;element ref="{}Batch" minOccurs="0"/>
 *         &lt;element ref="{}DateInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="quantity" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="openQuantity" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="promisedQuantity" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="lineNumber" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
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
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemIn", propOrder = {
    "itemID",
    "path",
    "itemDetail",
    "supplierID",
    "supplierList",
    "shipTo",
    "shipping",
    "tax",
    "spendDetail",
    "distribution",
    "contact",
    "comments",
    "scheduleLine",
    "billTo",
    "batch",
    "dateInfo",
    "extrinsic"
})
public class ItemIn {

    @XmlElement(name = "ItemID", required = true)
    protected ItemID itemID;
    @XmlElement(name = "Path")
    protected Path path;
    @XmlElement(name = "ItemDetail", required = true)
    protected ItemDetail itemDetail;
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
    @XmlElement(name = "Comments")
    protected Comments comments;
    @XmlElement(name = "ScheduleLine")
    protected List<ScheduleLine> scheduleLine;
    @XmlElement(name = "BillTo")
    protected BillTo billTo;
    @XmlElement(name = "Batch")
    protected Batch batch;
    @XmlElement(name = "DateInfo")
    protected List<DateInfo> dateInfo;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(name = "quantity", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String quantity;
    @XmlAttribute(name = "openQuantity")
    @XmlSchemaType(name = "anySimpleType")
    protected String openQuantity;
    @XmlAttribute(name = "promisedQuantity")
    @XmlSchemaType(name = "anySimpleType")
    protected String promisedQuantity;
    @XmlAttribute(name = "lineNumber")
    @XmlSchemaType(name = "anySimpleType")
    protected String lineNumber;
    @XmlAttribute(name = "parentLineNumber")
    @XmlSchemaType(name = "anySimpleType")
    protected String parentLineNumber;
    @XmlAttribute(name = "itemType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String itemType;
    @XmlAttribute(name = "compositeItemType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String compositeItemType;
    @XmlAttribute(name = "itemClassification")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String itemClassification;
    @XmlAttribute(name = "itemCategory")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String itemCategory;

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
     * Gets the value of the dateInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dateInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDateInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DateInfo }
     * 
     * 
     */
    public List<DateInfo> getDateInfo() {
        if (dateInfo == null) {
            dateInfo = new ArrayList<DateInfo>();
        }
        return this.dateInfo;
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
     * Gets the value of the openQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOpenQuantity() {
        return openQuantity;
    }

    /**
     * Sets the value of the openQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOpenQuantity(String value) {
        this.openQuantity = value;
    }

    /**
     * Gets the value of the promisedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromisedQuantity() {
        return promisedQuantity;
    }

    /**
     * Sets the value of the promisedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromisedQuantity(String value) {
        this.promisedQuantity = value;
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

}
