
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
 * <p>Java class for QualityNotificationActivity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QualityNotificationActivity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}QNCode" minOccurs="0"/>
 *         &lt;element ref="{}OwnerInfo" minOccurs="0"/>
 *         &lt;element ref="{}Description" minOccurs="0"/>
 *         &lt;element ref="{}Period" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="activityId" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="isCompleted">
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
@XmlType(name = "QualityNotificationActivity", propOrder = {
    "qnCode",
    "ownerInfo",
    "description",
    "period",
    "extrinsic"
})
public class QualityNotificationActivity {

    @XmlElement(name = "QNCode")
    protected QNCode qnCode;
    @XmlElement(name = "OwnerInfo")
    protected OwnerInfo ownerInfo;
    @XmlElement(name = "Description")
    protected Description description;
    @XmlElement(name = "Period")
    protected Period period;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(name = "activityId", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String activityId;
    @XmlAttribute(name = "isCompleted")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isCompleted;

    /**
     * Gets the value of the qnCode property.
     * 
     * @return
     *     possible object is
     *     {@link QNCode }
     *     
     */
    public QNCode getQNCode() {
        return qnCode;
    }

    /**
     * Sets the value of the qnCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link QNCode }
     *     
     */
    public void setQNCode(QNCode value) {
        this.qnCode = value;
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
     * Gets the value of the activityId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivityId() {
        return activityId;
    }

    /**
     * Sets the value of the activityId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivityId(String value) {
        this.activityId = value;
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

}
