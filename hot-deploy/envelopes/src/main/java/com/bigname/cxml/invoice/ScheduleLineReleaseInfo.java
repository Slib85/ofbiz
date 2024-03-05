
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ScheduleLineReleaseInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ScheduleLineReleaseInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}UnitOfMeasure"/>
 *       &lt;/sequence>
 *       &lt;attribute name="commitmentCode" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="firm"/>
 *             &lt;enumeration value="forecast"/>
 *             &lt;enumeration value="tradeoff"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="cumulativeScheduledQuantity" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="receivedQuantity" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleLineReleaseInfo", propOrder = {
    "unitOfMeasure"
})
public class ScheduleLineReleaseInfo {

    @XmlElement(name = "UnitOfMeasure", required = true)
    protected UnitOfMeasure unitOfMeasure;
    @XmlAttribute(name = "commitmentCode", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String commitmentCode;
    @XmlAttribute(name = "cumulativeScheduledQuantity", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String cumulativeScheduledQuantity;
    @XmlAttribute(name = "receivedQuantity")
    @XmlSchemaType(name = "anySimpleType")
    protected String receivedQuantity;

    /**
     * Gets the value of the unitOfMeasure property.
     * 
     * @return
     *     possible object is
     *     {@link UnitOfMeasure }
     *     
     */
    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    /**
     * Sets the value of the unitOfMeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitOfMeasure }
     *     
     */
    public void setUnitOfMeasure(UnitOfMeasure value) {
        this.unitOfMeasure = value;
    }

    /**
     * Gets the value of the commitmentCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommitmentCode() {
        return commitmentCode;
    }

    /**
     * Sets the value of the commitmentCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommitmentCode(String value) {
        this.commitmentCode = value;
    }

    /**
     * Gets the value of the cumulativeScheduledQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCumulativeScheduledQuantity() {
        return cumulativeScheduledQuantity;
    }

    /**
     * Sets the value of the cumulativeScheduledQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCumulativeScheduledQuantity(String value) {
        this.cumulativeScheduledQuantity = value;
    }

    /**
     * Gets the value of the receivedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceivedQuantity() {
        return receivedQuantity;
    }

    /**
     * Sets the value of the receivedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceivedQuantity(String value) {
        this.receivedQuantity = value;
    }

}
