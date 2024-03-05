
package com.bigname.cxml.fulfill;

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
 * <p>Java class for ReplenishmentTimeSeries complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReplenishmentTimeSeries">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}TimeSeriesDetails" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="custom"/>
 *             &lt;enumeration value="shipment"/>
 *             &lt;enumeration value="firmReceipt"/>
 *             &lt;enumeration value="purchaseOrder"/>
 *             &lt;enumeration value="plannedReceipt"/>
 *             &lt;enumeration value="projectedStock"/>
 *             &lt;enumeration value="supplierForecast"/>
 *             &lt;enumeration value="capacityShiftRuns"/>
 *             &lt;enumeration value="manufacturingOrder"/>
 *             &lt;enumeration value="capacityConfirmation"/>
 *             &lt;enumeration value="forecastConfirmation"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="customType" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReplenishmentTimeSeries", propOrder = {
    "timeSeriesDetails"
})
public class ReplenishmentTimeSeries {

    @XmlElement(name = "TimeSeriesDetails", required = true)
    protected List<TimeSeriesDetails> timeSeriesDetails;
    @XmlAttribute(name = "type", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String type;
    @XmlAttribute(name = "customType")
    @XmlSchemaType(name = "anySimpleType")
    protected String customType;

    /**
     * Gets the value of the timeSeriesDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the timeSeriesDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTimeSeriesDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TimeSeriesDetails }
     * 
     * 
     */
    public List<TimeSeriesDetails> getTimeSeriesDetails() {
        if (timeSeriesDetails == null) {
            timeSeriesDetails = new ArrayList<TimeSeriesDetails>();
        }
        return this.timeSeriesDetails;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the customType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomType() {
        return customType;
    }

    /**
     * Sets the value of the customType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomType(String value) {
        this.customType = value;
    }

}
