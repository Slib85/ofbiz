
package com.bigname.cxml.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RailDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RailDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}TripType"/>
 *         &lt;element ref="{}RailLeg" maxOccurs="unbounded"/>
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
@XmlType(name = "RailDetail", propOrder = {
    "tripType",
    "railLeg",
    "availablePrice",
    "penalty"
})
public class RailDetail {

    @XmlElement(name = "TripType", required = true)
    protected TripType tripType;
    @XmlElement(name = "RailLeg", required = true)
    protected List<RailLeg> railLeg;
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
     * Gets the value of the railLeg property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the railLeg property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRailLeg().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RailLeg }
     * 
     * 
     */
    public List<RailLeg> getRailLeg() {
        if (railLeg == null) {
            railLeg = new ArrayList<RailLeg>();
        }
        return this.railLeg;
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