
package com.bigname.cxml.fulfill;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QualityNotificationRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QualityNotificationRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}QualityNotificationRequestHeader"/>
 *         &lt;element ref="{}QualityNotificationRequestItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QualityNotificationRequest", propOrder = {
    "qualityNotificationRequestHeader",
    "qualityNotificationRequestItem"
})
public class QualityNotificationRequest {

    @XmlElement(name = "QualityNotificationRequestHeader", required = true)
    protected QualityNotificationRequestHeader qualityNotificationRequestHeader;
    @XmlElement(name = "QualityNotificationRequestItem")
    protected List<QualityNotificationRequestItem> qualityNotificationRequestItem;

    /**
     * Gets the value of the qualityNotificationRequestHeader property.
     * 
     * @return
     *     possible object is
     *     {@link QualityNotificationRequestHeader }
     *     
     */
    public QualityNotificationRequestHeader getQualityNotificationRequestHeader() {
        return qualityNotificationRequestHeader;
    }

    /**
     * Sets the value of the qualityNotificationRequestHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link QualityNotificationRequestHeader }
     *     
     */
    public void setQualityNotificationRequestHeader(QualityNotificationRequestHeader value) {
        this.qualityNotificationRequestHeader = value;
    }

    /**
     * Gets the value of the qualityNotificationRequestItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the qualityNotificationRequestItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQualityNotificationRequestItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QualityNotificationRequestItem }
     * 
     * 
     */
    public List<QualityNotificationRequestItem> getQualityNotificationRequestItem() {
        if (qualityNotificationRequestItem == null) {
            qualityNotificationRequestItem = new ArrayList<QualityNotificationRequestItem>();
        }
        return this.qualityNotificationRequestItem;
    }

}
