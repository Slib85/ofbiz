
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
 * <p>Java class for ReceiptItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReceiptItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ReceiptItemReference"/>
 *         &lt;element ref="{}UnitRate"/>
 *         &lt;element ref="{}ReceivedAmount"/>
 *         &lt;element ref="{}AssetInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}DeliveryAddress" minOccurs="0"/>
 *         &lt;element ref="{}Comments" minOccurs="0"/>
 *         &lt;element ref="{}Batch" minOccurs="0"/>
 *         &lt;element ref="{}Classification" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="receiptLineNumber" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="quantity" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="type">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="received"/>
 *             &lt;enumeration value="returned"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="parentReceiptLineNumber" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
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
 *       &lt;attribute name="completedIndicator">
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
@XmlType(name = "ReceiptItem", propOrder = {
    "receiptItemReference",
    "unitRate",
    "receivedAmount",
    "assetInfo",
    "deliveryAddress",
    "comments",
    "batch",
    "classification",
    "extrinsic"
})
public class ReceiptItem {

    @XmlElement(name = "ReceiptItemReference", required = true)
    protected ReceiptItemReference receiptItemReference;
    @XmlElement(name = "UnitRate", required = true)
    protected UnitRate unitRate;
    @XmlElement(name = "ReceivedAmount", required = true)
    protected ReceivedAmount receivedAmount;
    @XmlElement(name = "AssetInfo")
    protected List<AssetInfo> assetInfo;
    @XmlElement(name = "DeliveryAddress")
    protected DeliveryAddress deliveryAddress;
    @XmlElement(name = "Comments")
    protected Comments comments;
    @XmlElement(name = "Batch")
    protected Batch batch;
    @XmlElement(name = "Classification")
    protected List<Classification> classification;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(name = "receiptLineNumber", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String receiptLineNumber;
    @XmlAttribute(name = "quantity", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String quantity;
    @XmlAttribute(name = "type")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String type;
    @XmlAttribute(name = "parentReceiptLineNumber")
    @XmlSchemaType(name = "anySimpleType")
    protected String parentReceiptLineNumber;
    @XmlAttribute(name = "itemType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String itemType;
    @XmlAttribute(name = "compositeItemType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String compositeItemType;
    @XmlAttribute(name = "completedIndicator")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String completedIndicator;

    /**
     * Gets the value of the receiptItemReference property.
     * 
     * @return
     *     possible object is
     *     {@link ReceiptItemReference }
     *     
     */
    public ReceiptItemReference getReceiptItemReference() {
        return receiptItemReference;
    }

    /**
     * Sets the value of the receiptItemReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceiptItemReference }
     *     
     */
    public void setReceiptItemReference(ReceiptItemReference value) {
        this.receiptItemReference = value;
    }

    /**
     * Gets the value of the unitRate property.
     * 
     * @return
     *     possible object is
     *     {@link UnitRate }
     *     
     */
    public UnitRate getUnitRate() {
        return unitRate;
    }

    /**
     * Sets the value of the unitRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitRate }
     *     
     */
    public void setUnitRate(UnitRate value) {
        this.unitRate = value;
    }

    /**
     * Gets the value of the receivedAmount property.
     * 
     * @return
     *     possible object is
     *     {@link ReceivedAmount }
     *     
     */
    public ReceivedAmount getReceivedAmount() {
        return receivedAmount;
    }

    /**
     * Sets the value of the receivedAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceivedAmount }
     *     
     */
    public void setReceivedAmount(ReceivedAmount value) {
        this.receivedAmount = value;
    }

    /**
     * Gets the value of the assetInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the assetInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAssetInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AssetInfo }
     * 
     * 
     */
    public List<AssetInfo> getAssetInfo() {
        if (assetInfo == null) {
            assetInfo = new ArrayList<AssetInfo>();
        }
        return this.assetInfo;
    }

    /**
     * Gets the value of the deliveryAddress property.
     * 
     * @return
     *     possible object is
     *     {@link DeliveryAddress }
     *     
     */
    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * Sets the value of the deliveryAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliveryAddress }
     *     
     */
    public void setDeliveryAddress(DeliveryAddress value) {
        this.deliveryAddress = value;
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
     * Gets the value of the classification property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the classification property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClassification().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Classification }
     * 
     * 
     */
    public List<Classification> getClassification() {
        if (classification == null) {
            classification = new ArrayList<Classification>();
        }
        return this.classification;
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
     * Gets the value of the receiptLineNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiptLineNumber() {
        return receiptLineNumber;
    }

    /**
     * Sets the value of the receiptLineNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiptLineNumber(String value) {
        this.receiptLineNumber = value;
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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
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
     * Gets the value of the parentReceiptLineNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentReceiptLineNumber() {
        return parentReceiptLineNumber;
    }

    /**
     * Sets the value of the parentReceiptLineNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentReceiptLineNumber(String value) {
        this.parentReceiptLineNumber = value;
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
     * Gets the value of the completedIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompletedIndicator() {
        return completedIndicator;
    }

    /**
     * Sets the value of the completedIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompletedIndicator(String value) {
        this.completedIndicator = value;
    }

}
