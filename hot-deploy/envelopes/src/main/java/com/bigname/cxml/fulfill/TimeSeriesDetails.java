
package com.bigname.cxml.fulfill;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TimeSeriesDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TimeSeriesDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Period"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{}TimeSeriesQuantity"/>
 *           &lt;element ref="{}TimeSeriesValue"/>
 *           &lt;element ref="{}TimeSeriesAmount"/>
 *         &lt;/choice>
 *         &lt;element ref="{}UpsideQuantity" minOccurs="0"/>
 *         &lt;element ref="{}IdReference" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeSeriesDetails", propOrder = {
    "period",
    "timeSeriesQuantity",
    "timeSeriesValue",
    "timeSeriesAmount",
    "upsideQuantity",
    "idReference",
    "extrinsic"
})
public class TimeSeriesDetails {

    @XmlElement(name = "Period", required = true)
    protected Period period;
    @XmlElement(name = "TimeSeriesQuantity")
    protected TimeSeriesQuantity timeSeriesQuantity;
    @XmlElement(name = "TimeSeriesValue")
    protected TimeSeriesValue timeSeriesValue;
    @XmlElement(name = "TimeSeriesAmount")
    protected TimeSeriesAmount timeSeriesAmount;
    @XmlElement(name = "UpsideQuantity")
    protected UpsideQuantity upsideQuantity;
    @XmlElement(name = "IdReference")
    protected List<IdReference> idReference;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;

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
     * Gets the value of the timeSeriesQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link TimeSeriesQuantity }
     *     
     */
    public TimeSeriesQuantity getTimeSeriesQuantity() {
        return timeSeriesQuantity;
    }

    /**
     * Sets the value of the timeSeriesQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeSeriesQuantity }
     *     
     */
    public void setTimeSeriesQuantity(TimeSeriesQuantity value) {
        this.timeSeriesQuantity = value;
    }

    /**
     * Gets the value of the timeSeriesValue property.
     * 
     * @return
     *     possible object is
     *     {@link TimeSeriesValue }
     *     
     */
    public TimeSeriesValue getTimeSeriesValue() {
        return timeSeriesValue;
    }

    /**
     * Sets the value of the timeSeriesValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeSeriesValue }
     *     
     */
    public void setTimeSeriesValue(TimeSeriesValue value) {
        this.timeSeriesValue = value;
    }

    /**
     * Gets the value of the timeSeriesAmount property.
     * 
     * @return
     *     possible object is
     *     {@link TimeSeriesAmount }
     *     
     */
    public TimeSeriesAmount getTimeSeriesAmount() {
        return timeSeriesAmount;
    }

    /**
     * Sets the value of the timeSeriesAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeSeriesAmount }
     *     
     */
    public void setTimeSeriesAmount(TimeSeriesAmount value) {
        this.timeSeriesAmount = value;
    }

    /**
     * Gets the value of the upsideQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link UpsideQuantity }
     *     
     */
    public UpsideQuantity getUpsideQuantity() {
        return upsideQuantity;
    }

    /**
     * Sets the value of the upsideQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpsideQuantity }
     *     
     */
    public void setUpsideQuantity(UpsideQuantity value) {
        this.upsideQuantity = value;
    }

    /**
     * Gets the value of the idReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the idReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IdReference }
     * 
     * 
     */
    public List<IdReference> getIdReference() {
        if (idReference == null) {
            idReference = new ArrayList<IdReference>();
        }
        return this.idReference;
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

}
