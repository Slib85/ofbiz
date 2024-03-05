
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
 * <p>Java class for QualityInspectionDecisionDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QualityInspectionDecisionDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element ref="{}QualityInspectionResultReference"/>
 *           &lt;element ref="{}QualityInspectionRequestReference"/>
 *           &lt;element ref="{}ShipNoticeReference"/>
 *           &lt;element ref="{}ReceiptReference"/>
 *         &lt;/choice>
 *         &lt;element ref="{}QualityInspectionLotStock"/>
 *         &lt;element ref="{}ValueGroup" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Description" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="decisionID" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="decisionDate" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="status">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="accepted"/>
 *             &lt;enumeration value="rejected"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="qualityScore" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="createdBy" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QualityInspectionDecisionDetail", propOrder = {
    "qualityInspectionResultReference",
    "qualityInspectionRequestReference",
    "shipNoticeReference",
    "receiptReference",
    "qualityInspectionLotStock",
    "valueGroup",
    "description",
    "extrinsic"
})
public class QualityInspectionDecisionDetail {

    @XmlElement(name = "QualityInspectionResultReference")
    protected QualityInspectionResultReference qualityInspectionResultReference;
    @XmlElement(name = "QualityInspectionRequestReference")
    protected QualityInspectionRequestReference qualityInspectionRequestReference;
    @XmlElement(name = "ShipNoticeReference")
    protected ShipNoticeReference shipNoticeReference;
    @XmlElement(name = "ReceiptReference")
    protected ReceiptReference receiptReference;
    @XmlElement(name = "QualityInspectionLotStock", required = true)
    protected QualityInspectionLotStock qualityInspectionLotStock;
    @XmlElement(name = "ValueGroup")
    protected List<ValueGroup> valueGroup;
    @XmlElement(name = "Description")
    protected Description description;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(name = "decisionID", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String decisionID;
    @XmlAttribute(name = "decisionDate", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String decisionDate;
    @XmlAttribute(name = "status")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String status;
    @XmlAttribute(name = "qualityScore")
    @XmlSchemaType(name = "anySimpleType")
    protected String qualityScore;
    @XmlAttribute(name = "createdBy")
    @XmlSchemaType(name = "anySimpleType")
    protected String createdBy;

    /**
     * Gets the value of the qualityInspectionResultReference property.
     * 
     * @return
     *     possible object is
     *     {@link QualityInspectionResultReference }
     *     
     */
    public QualityInspectionResultReference getQualityInspectionResultReference() {
        return qualityInspectionResultReference;
    }

    /**
     * Sets the value of the qualityInspectionResultReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link QualityInspectionResultReference }
     *     
     */
    public void setQualityInspectionResultReference(QualityInspectionResultReference value) {
        this.qualityInspectionResultReference = value;
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
     * Gets the value of the shipNoticeReference property.
     * 
     * @return
     *     possible object is
     *     {@link ShipNoticeReference }
     *     
     */
    public ShipNoticeReference getShipNoticeReference() {
        return shipNoticeReference;
    }

    /**
     * Sets the value of the shipNoticeReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipNoticeReference }
     *     
     */
    public void setShipNoticeReference(ShipNoticeReference value) {
        this.shipNoticeReference = value;
    }

    /**
     * Gets the value of the receiptReference property.
     * 
     * @return
     *     possible object is
     *     {@link ReceiptReference }
     *     
     */
    public ReceiptReference getReceiptReference() {
        return receiptReference;
    }

    /**
     * Sets the value of the receiptReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceiptReference }
     *     
     */
    public void setReceiptReference(ReceiptReference value) {
        this.receiptReference = value;
    }

    /**
     * Gets the value of the qualityInspectionLotStock property.
     * 
     * @return
     *     possible object is
     *     {@link QualityInspectionLotStock }
     *     
     */
    public QualityInspectionLotStock getQualityInspectionLotStock() {
        return qualityInspectionLotStock;
    }

    /**
     * Sets the value of the qualityInspectionLotStock property.
     * 
     * @param value
     *     allowed object is
     *     {@link QualityInspectionLotStock }
     *     
     */
    public void setQualityInspectionLotStock(QualityInspectionLotStock value) {
        this.qualityInspectionLotStock = value;
    }

    /**
     * Gets the value of the valueGroup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the valueGroup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValueGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ValueGroup }
     * 
     * 
     */
    public List<ValueGroup> getValueGroup() {
        if (valueGroup == null) {
            valueGroup = new ArrayList<ValueGroup>();
        }
        return this.valueGroup;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link Description }
     *     
     */
    public Description getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link Description }
     *     
     */
    public void setDescription(Description value) {
        this.description = value;
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
     * Gets the value of the decisionID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDecisionID() {
        return decisionID;
    }

    /**
     * Sets the value of the decisionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDecisionID(String value) {
        this.decisionID = value;
    }

    /**
     * Gets the value of the decisionDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDecisionDate() {
        return decisionDate;
    }

    /**
     * Sets the value of the decisionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDecisionDate(String value) {
        this.decisionDate = value;
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
        return status;
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
     * Gets the value of the qualityScore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQualityScore() {
        return qualityScore;
    }

    /**
     * Sets the value of the qualityScore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQualityScore(String value) {
        this.qualityScore = value;
    }

    /**
     * Gets the value of the createdBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the value of the createdBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatedBy(String value) {
        this.createdBy = value;
    }

}
