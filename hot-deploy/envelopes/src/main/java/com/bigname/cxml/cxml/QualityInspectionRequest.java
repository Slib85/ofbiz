
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QualityInspectionRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QualityInspectionRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}QualityInspectionRequestHeader"/>
 *         &lt;element ref="{}QualityInspectionRequestDetail"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QualityInspectionRequest", propOrder = {
    "qualityInspectionRequestHeader",
    "qualityInspectionRequestDetail"
})
public class QualityInspectionRequest {

    @XmlElement(name = "QualityInspectionRequestHeader", required = true)
    protected QualityInspectionRequestHeader qualityInspectionRequestHeader;
    @XmlElement(name = "QualityInspectionRequestDetail", required = true)
    protected QualityInspectionRequestDetail qualityInspectionRequestDetail;

    /**
     * Gets the value of the qualityInspectionRequestHeader property.
     * 
     * @return
     *     possible object is
     *     {@link QualityInspectionRequestHeader }
     *     
     */
    public QualityInspectionRequestHeader getQualityInspectionRequestHeader() {
        return qualityInspectionRequestHeader;
    }

    /**
     * Sets the value of the qualityInspectionRequestHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link QualityInspectionRequestHeader }
     *     
     */
    public void setQualityInspectionRequestHeader(QualityInspectionRequestHeader value) {
        this.qualityInspectionRequestHeader = value;
    }

    /**
     * Gets the value of the qualityInspectionRequestDetail property.
     * 
     * @return
     *     possible object is
     *     {@link QualityInspectionRequestDetail }
     *     
     */
    public QualityInspectionRequestDetail getQualityInspectionRequestDetail() {
        return qualityInspectionRequestDetail;
    }

    /**
     * Sets the value of the qualityInspectionRequestDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link QualityInspectionRequestDetail }
     *     
     */
    public void setQualityInspectionRequestDetail(QualityInspectionRequestDetail value) {
        this.qualityInspectionRequestDetail = value;
    }

}
