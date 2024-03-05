
package com.bigname.cxml.fulfill;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AirDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AirDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}TripType"/>
 *         &lt;element ref="{}AirLeg" maxOccurs="unbounded"/>
 *         &lt;element ref="{}AvailablePrice" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Penalty" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AirDetail", propOrder = {
    "tripType",
    "airLeg",
    "availablePrice",
    "penalty"
})
public class AirDetail {

    @XmlElement(name = "TripType", required = true)
    protected TripType tripType;
    @XmlElement(name = "AirLeg", required = true)
    protected List<AirLeg> airLeg;
    @XmlElement(name = "AvailablePrice")
    protected List<AvailablePrice> availablePrice;
    @XmlElement(name = "Penalty")
    protected Penalty penalty;

    /**
     * Gets the value of the tripType property.
     * 
     * @return
     *     possible object is
     *     {@link TripType }
     *     
     */
    public TripType getTripType() {
        return tripType;
    }

    /**
     * Sets the value of the tripType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TripType }
     *     
     */
    public void setTripType(TripType value) {
        this.tripType = value;
    }

    /**
     * Gets the value of the airLeg property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the airLeg property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAirLeg().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AirLeg }
     * 
     * 
     */
    public List<AirLeg> getAirLeg() {
        if (airLeg == null) {
            airLeg = new ArrayList<AirLeg>();
        }
        return this.airLeg;
    }

    /**
     * Gets the value of the availablePrice property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the availablePrice property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAvailablePrice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AvailablePrice }
     * 
     * 
     */
    public List<AvailablePrice> getAvailablePrice() {
        if (availablePrice == null) {
            availablePrice = new ArrayList<AvailablePrice>();
        }
        return this.availablePrice;
    }

    /**
     * Gets the value of the penalty property.
     * 
     * @return
     *     possible object is
     *     {@link Penalty }
     *     
     */
    public Penalty getPenalty() {
        return penalty;
    }

    /**
     * Sets the value of the penalty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Penalty }
     *     
     */
    public void setPenalty(Penalty value) {
        this.penalty = value;
    }

}
