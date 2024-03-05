
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
 * <p>Java class for QualityNotificationRequestItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QualityNotificationRequestItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}QNCode" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}OwnerInfo" minOccurs="0"/>
 *         &lt;element ref="{}Description" minOccurs="0"/>
 *         &lt;element ref="{}Period" minOccurs="0"/>
 *         &lt;element ref="{}AdditionalQNInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}QualityNotificationTask" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}QualityNotificationActivity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}QualityNotificationCause" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}AssetInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="defectId" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="defectCount" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="isCompleted">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="completedDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
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
@XmlType(name = "QualityNotificationRequestItem", propOrder = {
    "qnCode",
    "ownerInfo",
    "description",
    "period",
    "additionalQNInfo",
    "qualityNotificationTask",
    "qualityNotificationActivity",
    "qualityNotificationCause",
    "assetInfo",
    "extrinsic"
})
public class QualityNotificationRequestItem {

    @XmlElement(name = "QNCode")
    protected List<QNCode> qnCode;
    @XmlElement(name = "OwnerInfo")
    protected OwnerInfo ownerInfo;
    @XmlElement(name = "Description")
    protected Description description;
    @XmlElement(name = "Period")
    protected Period period;
    @XmlElement(name = "AdditionalQNInfo")
    protected List<AdditionalQNInfo> additionalQNInfo;
    @XmlElement(name = "QualityNotificationTask")
    protected List<QualityNotificationTask> qualityNotificationTask;
    @XmlElement(name = "QualityNotificationActivity")
    protected List<QualityNotificationActivity> qualityNotificationActivity;
    @XmlElement(name = "QualityNotificationCause")
    protected List<QualityNotificationCause> qualityNotificationCause;
    @XmlElement(name = "AssetInfo")
    protected List<AssetInfo> assetInfo;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(name = "defectId", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String defectId;
    @XmlAttribute(name = "defectCount")
    @XmlSchemaType(name = "anySimpleType")
    protected String defectCount;
    @XmlAttribute(name = "isCompleted")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isCompleted;
    @XmlAttribute(name = "completedDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String completedDate;
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
     * Gets the value of the ownerInfo property.
     * 
     * @return
     *     possible object is
     *     {@link OwnerInfo }
     *     
     */
    public OwnerInfo getOwnerInfo() {
        return ownerInfo;
    }

    /**
     * Sets the value of the ownerInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link OwnerInfo }
     *     
     */
    public void setOwnerInfo(OwnerInfo value) {
        this.ownerInfo = value;
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
     * Gets the value of the period property.
     * 
     * @return
     *     possible object is
     *     {@link Period }
     *     
     */
    public Period getPeriod() {
        return period;
    }

    /**
     * Sets the value of the period property.
     * 
     * @param value
     *     allowed object is
     *     {@link Period }
     *     
     */
    public void setPeriod(Period value) {
        this.period = value;
    }

    /**
     * Gets the value of the additionalQNInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the additionalQNInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdditionalQNInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdditionalQNInfo }
     * 
     * 
     */
    public List<AdditionalQNInfo> getAdditionalQNInfo() {
        if (additionalQNInfo == null) {
            additionalQNInfo = new ArrayList<AdditionalQNInfo>();
        }
        return this.additionalQNInfo;
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
     * Gets the value of the qualityNotificationCause property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the qualityNotificationCause property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQualityNotificationCause().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QualityNotificationCause }
     * 
     * 
     */
    public List<QualityNotificationCause> getQualityNotificationCause() {
        if (qualityNotificationCause == null) {
            qualityNotificationCause = new ArrayList<QualityNotificationCause>();
        }
        return this.qualityNotificationCause;
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
     * Gets the value of the defectId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefectId() {
        return defectId;
    }

    /**
     * Sets the value of the defectId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefectId(String value) {
        this.defectId = value;
    }

    /**
     * Gets the value of the defectCount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefectCount() {
        return defectCount;
    }

    /**
     * Sets the value of the defectCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefectCount(String value) {
        this.defectCount = value;
    }

    /**
     * Gets the value of the isCompleted property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsCompleted() {
        return isCompleted;
    }

    /**
     * Sets the value of the isCompleted property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsCompleted(String value) {
        this.isCompleted = value;
    }

    /**
     * Gets the value of the completedDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompletedDate() {
        return completedDate;
    }

    /**
     * Sets the value of the completedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompletedDate(String value) {
        this.completedDate = value;
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
