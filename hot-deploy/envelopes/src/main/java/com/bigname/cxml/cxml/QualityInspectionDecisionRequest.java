
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QualityInspectionDecisionRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QualityInspectionDecisionRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}QualityInspectionDecisionDetail"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QualityInspectionDecisionRequest", propOrder = {
    "qualityInspectionDecisionDetail"
})
public class QualityInspectionDecisionRequest {

    @XmlElement(name = "QualityInspectionDecisionDetail", required = true)
    protected QualityInspectionDecisionDetail qualityInspectionDecisionDetail;

    /**
     * Gets the value of the qualityInspectionDecisionDetail property.
     * 
     * @return
     *     possible object is
     *     {@link QualityInspectionDecisionDetail }
     *     
     */
    public QualityInspectionDecisionDetail getQualityInspectionDecisionDetail() {
        return qualityInspectionDecisionDetail;
    }

    /**
     * Sets the value of the qualityInspectionDecisionDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link QualityInspectionDecisionDetail }
     *     
     */
    public void setQualityInspectionDecisionDetail(QualityInspectionDecisionDetail value) {
        this.qualityInspectionDecisionDetail = value;
    }

}
