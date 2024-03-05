
package com.bigname.cxml.cxml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ItemOutIndustry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemOutIndustry">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ItemOutRetail" minOccurs="0"/>
 *         &lt;element ref="{}ReferenceDocumentInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Priority" minOccurs="0"/>
 *         &lt;element ref="{}QualityInfo" minOccurs="0"/>
 *         &lt;element ref="{}SerialNumberInfo" minOccurs="0"/>
 *         &lt;element ref="{}BatchInfo" minOccurs="0"/>
 *         &lt;element ref="{}AssetInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="planningType">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="ATO"/>
 *             &lt;enumeration value="CTO"/>
 *             &lt;enumeration value="MTO"/>
 *             &lt;enumeration value="MTS"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="requiresRealTimeConsumption">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="isHUMandatory">
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
@XmlType(name = "ItemOutIndustry", propOrder = {
    "itemOutRetail",
    "referenceDocumentInfo",
    "priority",
    "qualityInfo",
    "serialNumberInfo",
    "batchInfo",
    "assetInfo"
})
public class ItemOutIndustry {

    @XmlElement(name = "ItemOutRetail")
    protected ItemOutRetail itemOutRetail;
    @XmlElement(name = "ReferenceDocumentInfo")
    protected List<ReferenceDocumentInfo> referenceDocumentInfo;
    @XmlElement(name = "Priority")
    protected Priority priority;
    @XmlElement(name = "QualityInfo")
    protected QualityInfo qualityInfo;
    @XmlElement(name = "SerialNumberInfo")
    protected SerialNumberInfo serialNumberInfo;
    @XmlElement(name = "BatchInfo")
    protected BatchInfo batchInfo;
    @XmlElement(name = "AssetInfo")
    protected List<AssetInfo> assetInfo;
    @XmlAttribute(name = "planningType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String planningType;
    @XmlAttribute(name = "requiresRealTimeConsumption")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String requiresRealTimeConsumption;
    @XmlAttribute(name = "isHUMandatory")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isHUMandatory;

    /**
     * Gets the value of the itemOutRetail property.
     * 
     * @return
     *     possible object is
     *     {@link ItemOutRetail }
     *     
     */
    public ItemOutRetail getItemOutRetail() {
        return itemOutRetail;
    }

    /**
     * Sets the value of the itemOutRetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemOutRetail }
     *     
     */
    public void setItemOutRetail(ItemOutRetail value) {
        this.itemOutRetail = value;
    }

    /**
     * Gets the value of the referenceDocumentInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the referenceDocumentInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferenceDocumentInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReferenceDocumentInfo }
     * 
     * 
     */
    public List<ReferenceDocumentInfo> getReferenceDocumentInfo() {
        if (referenceDocumentInfo == null) {
            referenceDocumentInfo = new ArrayList<ReferenceDocumentInfo>();
        }
        return this.referenceDocumentInfo;
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
     * Gets the value of the qualityInfo property.
     * 
     * @return
     *     possible object is
     *     {@link QualityInfo }
     *     
     */
    public QualityInfo getQualityInfo() {
        return qualityInfo;
    }

    /**
     * Sets the value of the qualityInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link QualityInfo }
     *     
     */
    public void setQualityInfo(QualityInfo value) {
        this.qualityInfo = value;
    }

    /**
     * Gets the value of the serialNumberInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SerialNumberInfo }
     *     
     */
    public SerialNumberInfo getSerialNumberInfo() {
        return serialNumberInfo;
    }

    /**
     * Sets the value of the serialNumberInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SerialNumberInfo }
     *     
     */
    public void setSerialNumberInfo(SerialNumberInfo value) {
        this.serialNumberInfo = value;
    }

    /**
     * Gets the value of the batchInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BatchInfo }
     *     
     */
    public BatchInfo getBatchInfo() {
        return batchInfo;
    }

    /**
     * Sets the value of the batchInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BatchInfo }
     *     
     */
    public void setBatchInfo(BatchInfo value) {
        this.batchInfo = value;
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
     * Gets the value of the planningType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanningType() {
        return planningType;
    }

    /**
     * Sets the value of the planningType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanningType(String value) {
        this.planningType = value;
    }

    /**
     * Gets the value of the requiresRealTimeConsumption property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequiresRealTimeConsumption() {
        return requiresRealTimeConsumption;
    }

    /**
     * Sets the value of the requiresRealTimeConsumption property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequiresRealTimeConsumption(String value) {
        this.requiresRealTimeConsumption = value;
    }

    /**
     * Gets the value of the isHUMandatory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsHUMandatory() {
        return isHUMandatory;
    }

    /**
     * Sets the value of the isHUMandatory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsHUMandatory(String value) {
        this.isHUMandatory = value;
    }

}
