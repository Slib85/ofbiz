
package com.bigname.cxml.fulfill;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QualityInspectionResultRequestDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QualityInspectionResultRequestDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}QualityInspectionValuation" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QualityInspectionResultRequestDetail", propOrder = {
    "qualityInspectionValuation"
})
public class QualityInspectionResultRequestDetail {

    @XmlElement(name = "QualityInspectionValuation", required = true)
    protected List<QualityInspectionValuation> qualityInspectionValuation;

    /**
     * Gets the value of the qualityInspectionValuation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the qualityInspectionValuation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQualityInspectionValuation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QualityInspectionValuation }
     * 
     * 
     */
    public List<QualityInspectionValuation> getQualityInspectionValuation() {
        if (qualityInspectionValuation == null) {
            qualityInspectionValuation = new ArrayList<QualityInspectionValuation>();
        }
        return this.qualityInspectionValuation;
    }

}
