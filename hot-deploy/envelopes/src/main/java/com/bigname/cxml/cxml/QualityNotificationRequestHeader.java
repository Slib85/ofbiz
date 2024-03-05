
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
 * <p>Java class for QualityNotificationRequestHeader complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QualityNotificationRequestHeader">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}DocumentReference" minOccurs="0"/>
 *         &lt;element ref="{}QualityInspectionRequestReference" minOccurs="0"/>
 *         &lt;element ref="{}QNCode" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}ShipTo" minOccurs="0"/>
 *         &lt;element ref="{}BillTo" minOccurs="0"/>
 *         &lt;element ref="{}Shipping" minOccurs="0"/>
 *         &lt;element ref="{}Contact" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}QNNotes" maxOccurs="unbounded"/>
 *         &lt;element ref="{}Priority"/>
 *         &lt;element ref="{}RequestedProcessingPeriod" minOccurs="0"/>
 *         &lt;element ref="{}MalfunctionPeriod" minOccurs="0"/>
 *         &lt;element ref="{}ReferenceDocumentInfo" minOccurs="0"/>
 *         &lt;element ref="{}ItemInfo"/>
 *         &lt;element ref="{}Batch" minOccurs="0"/>
 *         &lt;element ref="{}ComplainQuantity" minOccurs="0"/>
 *         &lt;element ref="{}ReturnQuantity" minOccurs="0"/>
 *         &lt;element ref="{}QualityNotificationTask" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}QualityNotificationActivity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}AssetInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="requestID" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="externalRequestID" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="requestDate" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="requestVersion" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="operation" default="new">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="new"/>
 *             &lt;enumeration value="update"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="status" default="new">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="new"/>
 *             &lt;enumeration value="draft"/>
 *             &lt;enumeration value="closed"/>
 *             &lt;enumeration value="canceled"/>
 *             &lt;enumeration value="completed"/>
 *             &lt;enumeration value="postponed"/>
 *             &lt;enumeration value="in-process"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="discoveryDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="serialNumber" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="returnDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="returnAuthorizationNumber" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="itemCategory">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="subcontract"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="minimumRequiredTasks" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="minimumRequiredActivities" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="minimumRequiredCauses" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QualityNotificationRequestHeader", propOrder = {
    "documentReference",
    "qualityInspectionRequestReference",
    "qnCode",
    "shipTo",
    "billTo",
    "shipping",
    "contact",
    "qnNotes",
    "priority",
    "requestedProcessingPeriod",
    "malfunctionPeriod",
    "referenceDocumentInfo",
    "itemInfo",
    "batch",
    "complainQuantity",
    "returnQuantity",
    "qualityNotificationTask",
    "qualityNotificationActivity",
    "assetInfo",
    "extrinsic"
})
public class QualityNotificationRequestHeader {

    @XmlElement(name = "DocumentReference")
    protected DocumentReference documentReference;
    @XmlElement(name = "QualityInspectionRequestReference")
    protected QualityInspectionRequestReference qualityInspectionRequestReference;
    @XmlElement(name = "QNCode")
    protected List<QNCode> qnCode;
    @XmlElement(name = "ShipTo")
    protected ShipTo shipTo;
    @XmlElement(name = "BillTo")
    protected BillTo billTo;
    @XmlElement(name = "Shipping")
    protected Shipping shipping;
    @XmlElement(name = "Contact")
    protected List<Contact> contact;
    @XmlElement(name = "QNNotes", required = true)
    protected List<QNNotes> qnNotes;
    @XmlElement(name = "Priority", required = true)
    protected Priority priority;
    @XmlElement(name = "RequestedProcessingPeriod")
    protected RequestedProcessingPeriod requestedProcessingPeriod;
    @XmlElement(name = "MalfunctionPeriod")
    protected MalfunctionPeriod malfunctionPeriod;
    @XmlElement(name = "ReferenceDocumentInfo")
    protected ReferenceDocumentInfo referenceDocumentInfo;
    @XmlElement(name = "ItemInfo", required = true)
    protected ItemInfo itemInfo;
    @XmlElement(name = "Batch")
    protected Batch batch;
    @XmlElement(name = "ComplainQuantity")
    protected ComplainQuantity complainQuantity;
    @XmlElement(name = "ReturnQuantity")
    protected ReturnQuantity returnQuantity;
    @XmlElement(name = "QualityNotificationTask")
    protected List<QualityNotificationTask> qualityNotificationTask;
    @XmlElement(name = "QualityNotificationActivity")
    protected List<QualityNotificationActivity> qualityNotificationActivity;
    @XmlElement(name = "AssetInfo")
    protected List<AssetInfo> assetInfo;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(name = "requestID", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String requestID;
    @XmlAttribute(name = "externalRequestID")
    @XmlSchemaType(name = "anySimpleType")
    protected String externalRequestID;
    @XmlAttribute(name = "requestDate", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String requestDate;
    @XmlAttribute(name = "requestVersion")
    @XmlSchemaType(name = "anySimpleType")
    protected String requestVersion;
    @XmlAttribute(name = "operation")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String operation;
    @XmlAttribute(name = "status")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String status;
    @XmlAttribute(name = "discoveryDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String discoveryDate;
    @XmlAttribute(name = "serialNumber")
    @XmlSchemaType(name = "anySimpleType")
    protected String serialNumber;
    @XmlAttribute(name = "returnDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String returnDate;
    @XmlAttribute(name = "returnAuthorizationNumber")
    @XmlSchemaType(name = "anySimpleType")
    protected String returnAuthorizationNumber;
    @XmlAttribute(name = "itemCategory")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String itemCategory;
    @XmlAttribute(name = "minimumRequiredTasks")
    @XmlSchemaType(name = "anySimpleType")
    protected String minimumRequiredTasks;
    @XmlAttribute(name = "minimumRequiredActivities")
    @XmlSchemaType(name = "anySimpleType")
    protected String minimumRequiredActivities;
    @XmlAttribute(name = "minimumRequiredCauses")
    @XmlSchemaType(name = "anySimpleType")
    protected String minimumRequiredCauses;

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
     * Gets the value of the qualityInspectionRequestReference property.
     * 
     * @return
     *     possible object is
     *     {@link QualityInspectionRequestReference }
     *     
     */
    public QualityInspectionRequestReference getQualityInspectionRequestReference() {
        return qualityInspectionRequestReference;
    }

    /**
     * Sets the value of the qualityInspectionRequestReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link QualityInspectionRequestReference }
     *     
     */
    public void setQualityInspectionRequestReference(QualityInspectionRequestReference value) {
        this.qualityInspectionRequestReference = value;
    }

    /**
     * Gets the value of the qnCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the qnCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQNCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QNCode }
     * 
     * 
     */
    public List<QNCode> getQNCode() {
        if (qnCode == null) {
            qnCode = new ArrayList<QNCode>();
        }
        return this.qnCode;
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
     * Gets the value of the qnNotes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the qnNotes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQNNotes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QNNotes }
     * 
     * 
     */
    public List<QNNotes> getQNNotes() {
        if (qnNotes == null) {
            qnNotes = new ArrayList<QNNotes>();
        }
        return this.qnNotes;
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link Priority }
     *     
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Priority }
     *     
     */
    public void setPriority(Priority value) {
        this.priority = value;
    }

    /**
     * Gets the value of the requestedProcessingPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link RequestedProcessingPeriod }
     *     
     */
    public RequestedProcessingPeriod getRequestedProcessingPeriod() {
        return requestedProcessingPeriod;
    }

    /**
     * Sets the value of the requestedProcessingPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestedProcessingPeriod }
     *     
     */
    public void setRequestedProcessingPeriod(RequestedProcessingPeriod value) {
        this.requestedProcessingPeriod = value;
    }

    /**
     * Gets the value of the malfunctionPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link MalfunctionPeriod }
     *     
     */
    public MalfunctionPeriod getMalfunctionPeriod() {
        return malfunctionPeriod;
    }

    /**
     * Sets the value of the malfunctionPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link MalfunctionPeriod }
     *     
     */
    public void setMalfunctionPeriod(MalfunctionPeriod value) {
        this.malfunctionPeriod = value;
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
     * Gets the value of the itemInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ItemInfo }
     *     
     */
    public ItemInfo getItemInfo() {
        return itemInfo;
    }

    /**
     * Sets the value of the itemInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemInfo }
     *     
     */
    public void setItemInfo(ItemInfo value) {
        this.itemInfo = value;
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
     * Gets the value of the complainQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link ComplainQuantity }
     *     
     */
    public ComplainQuantity getComplainQuantity() {
        return complainQuantity;
    }

    /**
     * Sets the value of the complainQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplainQuantity }
     *     
     */
    public void setComplainQuantity(ComplainQuantity value) {
        this.complainQuantity = value;
    }

    /**
     * Gets the value of the returnQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnQuantity }
     *     
     */
    public ReturnQuantity getReturnQuantity() {
        return returnQuantity;
    }

    /**
     * Sets the value of the returnQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnQuantity }
     *     
     */
    public void setReturnQuantity(ReturnQuantity value) {
        this.returnQuantity = value;
    }

    /**
     * Gets the value of the qualityNotificationTask property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the qualityNotificationTask property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQualityNotificationTask().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QualityNotificationTask }
     * 
     * 
     */
    public List<QualityNotificationTask> getQualityNotificationTask() {
        if (qualityNotificationTask == null) {
            qualityNotificationTask = new ArrayList<QualityNotificationTask>();
        }
        return this.qualityNotificationTask;
    }

    /**
     * Gets the value of the qualityNotificationActivity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the qualityNotificationActivity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQualityNotificationActivity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QualityNotificationActivity }
     * 
     * 
     */
    public List<QualityNotificationActivity> getQualityNotificationActivity() {
        if (qualityNotificationActivity == null) {
            qualityNotificationActivity = new ArrayList<QualityNotificationActivity>();
        }
        return this.qualityNotificationActivity;
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
     * Gets the value of the requestID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestID() {
        return requestID;
    }

    /**
     * Sets the value of the requestID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestID(String value) {
        this.requestID = value;
    }

    /**
     * Gets the value of the externalRequestID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalRequestID() {
        return externalRequestID;
    }

    /**
     * Sets the value of the externalRequestID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalRequestID(String value) {
        this.externalRequestID = value;
    }

    /**
     * Gets the value of the requestDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestDate() {
        return requestDate;
    }

    /**
     * Sets the value of the requestDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestDate(String value) {
        this.requestDate = value;
    }

    /**
     * Gets the value of the requestVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestVersion() {
        return requestVersion;
    }

    /**
     * Sets the value of the requestVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestVersion(String value) {
        this.requestVersion = value;
    }

    /**
     * Gets the value of the operation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperation() {
        if (operation == null) {
            return "new";
        } else {
            return operation;
        }
    }

    /**
     * Sets the value of the operation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperation(String value) {
        this.operation = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        if (status == null) {
            return "new";
        } else {
            return status;
        }
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the discoveryDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscoveryDate() {
        return discoveryDate;
    }

    /**
     * Sets the value of the discoveryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscoveryDate(String value) {
        this.discoveryDate = value;
    }

    /**
     * Gets the value of the serialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the value of the serialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialNumber(String value) {
        this.serialNumber = value;
    }

    /**
     * Gets the value of the returnDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the value of the returnDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnDate(String value) {
        this.returnDate = value;
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
     * Gets the value of the minimumRequiredTasks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinimumRequiredTasks() {
        return minimumRequiredTasks;
    }

    /**
     * Sets the value of the minimumRequiredTasks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinimumRequiredTasks(String value) {
        this.minimumRequiredTasks = value;
    }

    /**
     * Gets the value of the minimumRequiredActivities property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinimumRequiredActivities() {
        return minimumRequiredActivities;
    }

    /**
     * Sets the value of the minimumRequiredActivities property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinimumRequiredActivities(String value) {
        this.minimumRequiredActivities = value;
    }

    /**
     * Gets the value of the minimumRequiredCauses property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinimumRequiredCauses() {
        return minimumRequiredCauses;
    }

    /**
     * Sets the value of the minimumRequiredCauses property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinimumRequiredCauses(String value) {
        this.minimumRequiredCauses = value;
    }

}
