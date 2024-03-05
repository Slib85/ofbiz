
package com.bigname.cxml.cxml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QualityInspectionRequestDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QualityInspectionRequestDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}QualityInspectionCharacteristic" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QualityInspectionRequestDetail", propOrder = {
    "qualityInspectionCharacteristic"
})
public class QualityInspectionRequestDetail {

    @XmlElement(name = "QualityInspectionCharacteristic", required = true)
    protected List<QualityInspectionCharacteristic> qualityInspectionCharacteristic;

    /**
     * Gets the value of the qualityInspectionCharacteristic property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the qualityInspectionCharacteristic property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQualityInspectionCharacteristic().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QualityInspectionCharacteristic }
     * 
     * 
     */
    public List<QualityInspectionCharacteristic> getQualityInspectionCharacteristic() {
        if (qualityInspectionCharacteristic == null) {
            qualityInspectionCharacteristic = new ArrayList<QualityInspectionCharacteristic>();
        }
        return this.qualityInspectionCharacteristic;
    }

}
