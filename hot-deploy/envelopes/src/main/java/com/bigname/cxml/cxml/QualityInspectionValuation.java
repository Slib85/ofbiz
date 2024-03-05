
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
 * <p>Java class for QualityInspectionValuation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QualityInspectionValuation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}QualitySampleResult" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}ValueGroup" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Description" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="valuationID" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="characteristicID" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="operationNumber" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="workCenter" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="meanValue" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="aboveTolerance" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="belowTolerance" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="inspectedQuantity" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="nonConformance" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="deviation" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="variance" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="numberOfDefects" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="serialNumber" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="inspectionDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="isAdHoc">
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
@XmlType(name = "QualityInspectionValuation", propOrder = {
    "qualitySampleResult",
    "valueGroup",
    "description",
    "extrinsic"
})
public class QualityInspectionValuation {

    @XmlElement(name = "QualitySampleResult")
    protected List<QualitySampleResult> qualitySampleResult;
    @XmlElement(name = "ValueGroup")
    protected List<ValueGroup> valueGroup;
    @XmlElement(name = "Description")
    protected Description description;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(name = "valuationID", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String valuationID;
    @XmlAttribute(name = "characteristicID", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String characteristicID;
    @XmlAttribute(name = "operationNumber", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String operationNumber;
    @XmlAttribute(name = "workCenter")
    @XmlSchemaType(name = "anySimpleType")
    protected String workCenter;
    @XmlAttribute(name = "meanValue")
    @XmlSchemaType(name = "anySimpleType")
    protected String meanValue;
    @XmlAttribute(name = "aboveTolerance")
    @XmlSchemaType(name = "anySimpleType")
    protected String aboveTolerance;
    @XmlAttribute(name = "belowTolerance")
    @XmlSchemaType(name = "anySimpleType")
    protected String belowTolerance;
    @XmlAttribute(name = "inspectedQuantity")
    @XmlSchemaType(name = "anySimpleType")
    protected String inspectedQuantity;
    @XmlAttribute(name = "nonConformance")
    @XmlSchemaType(name = "anySimpleType")
    protected String nonConformance;
    @XmlAttribute(name = "deviation")
    @XmlSchemaType(name = "anySimpleType")
    protected String deviation;
    @XmlAttribute(name = "variance")
    @XmlSchemaType(name = "anySimpleType")
    protected String variance;
    @XmlAttribute(name = "numberOfDefects")
    @XmlSchemaType(name = "anySimpleType")
    protected String numberOfDefects;
    @XmlAttribute(name = "serialNumber")
    @XmlSchemaType(name = "anySimpleType")
    protected String serialNumber;
    @XmlAttribute(name = "inspectionDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String inspectionDate;
    @XmlAttribute(name = "isAdHoc")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isAdHoc;

    /**
     * Gets the value of the qualitySampleResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the qualitySampleResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQualitySampleResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QualitySampleResult }
     * 
     * 
     */
    public List<QualitySampleResult> getQualitySampleResult() {
        if (qualitySampleResult == null) {
            qualitySampleResult = new ArrayList<QualitySampleResult>();
        }
        return this.qualitySampleResult;
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
     * Gets the value of the valuationID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValuationID() {
        return valuationID;
    }

    /**
     * Sets the value of the valuationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValuationID(String value) {
        this.valuationID = value;
    }

    /**
     * Gets the value of the characteristicID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCharacteristicID() {
        return characteristicID;
    }

    /**
     * Sets the value of the characteristicID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCharacteristicID(String value) {
        this.characteristicID = value;
    }

    /**
     * Gets the value of the operationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationNumber() {
        return operationNumber;
    }

    /**
     * Sets the value of the operationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationNumber(String value) {
        this.operationNumber = value;
    }

    /**
     * Gets the value of the workCenter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkCenter() {
        return workCenter;
    }

    /**
     * Sets the value of the workCenter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkCenter(String value) {
        this.workCenter = value;
    }

    /**
     * Gets the value of the meanValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeanValue() {
        return meanValue;
    }

    /**
     * Sets the value of the meanValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeanValue(String value) {
        this.meanValue = value;
    }

    /**
     * Gets the value of the aboveTolerance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAboveTolerance() {
        return aboveTolerance;
    }

    /**
     * Sets the value of the aboveTolerance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAboveTolerance(String value) {
        this.aboveTolerance = value;
    }

    /**
     * Gets the value of the belowTolerance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBelowTolerance() {
        return belowTolerance;
    }

    /**
     * Sets the value of the belowTolerance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBelowTolerance(String value) {
        this.belowTolerance = value;
    }

    /**
     * Gets the value of the inspectedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInspectedQuantity() {
        return inspectedQuantity;
    }

    /**
     * Sets the value of the inspectedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInspectedQuantity(String value) {
        this.inspectedQuantity = value;
    }

    /**
     * Gets the value of the nonConformance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNonConformance() {
        return nonConformance;
    }

    /**
     * Sets the value of the nonConformance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNonConformance(String value) {
        this.nonConformance = value;
    }

    /**
     * Gets the value of the deviation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviation() {
        return deviation;
    }

    /**
     * Sets the value of the deviation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviation(String value) {
        this.deviation = value;
    }

    /**
     * Gets the value of the variance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVariance() {
        return variance;
    }

    /**
     * Sets the value of the variance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVariance(String value) {
        this.variance = value;
    }

    /**
     * Gets the value of the numberOfDefects property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberOfDefects() {
        return numberOfDefects;
    }

    /**
     * Sets the value of the numberOfDefects property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberOfDefects(String value) {
        this.numberOfDefects = value;
    }

    /**
     * Gets the value of the serialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the value of the serialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialNumber(String value) {
        this.serialNumber = value;
    }

    /**
     * Gets the value of the inspectionDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInspectionDate() {
        return inspectionDate;
    }

    /**
     * Sets the value of the inspectionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInspectionDate(String value) {
        this.inspectionDate = value;
    }

    /**
     * Gets the value of the isAdHoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsAdHoc() {
        return isAdHoc;
    }

    /**
     * Sets the value of the isAdHoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsAdHoc(String value) {
        this.isAdHoc = value;
    }

}
