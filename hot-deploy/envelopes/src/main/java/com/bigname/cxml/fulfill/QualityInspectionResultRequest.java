
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QualityInspectionResultRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QualityInspectionResultRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}QualityInspectionResultRequestHeader"/>
 *         &lt;element ref="{}QualityInspectionResultRequestDetail"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QualityInspectionResultRequest", propOrder = {
    "qualityInspectionResultRequestHeader",
    "qualityInspectionResultRequestDetail"
})
public class QualityInspectionResultRequest {

    @XmlElement(name = "QualityInspectionResultRequestHeader", required = true)
    protected QualityInspectionResultRequestHeader qualityInspectionResultRequestHeader;
    @XmlElement(name = "QualityInspectionResultRequestDetail", required = true)
    protected QualityInspectionResultRequestDetail qualityInspectionResultRequestDetail;

    /**
     * Gets the value of the qualityInspectionResultRequestHeader property.
     * 
     * @return
     *     possible object is
     *     {@link QualityInspectionResultRequestHeader }
     *     
     */
    public QualityInspectionResultRequestHeader getQualityInspectionResultRequestHeader() {
        return qualityInspectionResultRequestHeader;
    }

    /**
     * Sets the value of the qualityInspectionResultRequestHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link QualityInspectionResultRequestHeader }
     *     
     */
    public void setQualityInspectionResultRequestHeader(QualityInspectionResultRequestHeader value) {
        this.qualityInspectionResultRequestHeader = value;
    }

    /**
     * Gets the value of the qualityInspectionResultRequestDetail property.
     * 
     * @return
     *     possible object is
     *     {@link QualityInspectionResultRequestDetail }
     *     
     */
    public QualityInspectionResultRequestDetail getQualityInspectionResultRequestDetail() {
        return qualityInspectionResultRequestDetail;
    }

    /**
     * Sets the value of the qualityInspectionResultRequestDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link QualityInspectionResultRequestDetail }
     *     
     */
    public void setQualityInspectionResultRequestDetail(QualityInspectionResultRequestDetail value) {
        this.qualityInspectionResultRequestDetail = value;
    }

}
