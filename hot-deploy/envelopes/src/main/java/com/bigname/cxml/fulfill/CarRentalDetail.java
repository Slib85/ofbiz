
package com.bigname.cxml.fulfill;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CarRentalDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CarRentalDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Vendor"/>
 *         &lt;element ref="{}CarRentalPickup"/>
 *         &lt;element ref="{}CarRentalDropoff"/>
 *         &lt;element ref="{}BookingClassCode" minOccurs="0"/>
 *         &lt;element ref="{}CarRentalFee" maxOccurs="unbounded"/>
 *         &lt;element ref="{}LimitedMileage" minOccurs="0"/>
 *         &lt;element ref="{}AvailablePrice" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="travelSegment" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="pickupTime" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="dropoffTime" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CarRentalDetail", propOrder = {
    "vendor",
    "carRentalPickup",
    "carRentalDropoff",
    "bookingClassCode",
    "carRentalFee",
    "limitedMileage",
    "availablePrice"
})
public class CarRentalDetail {

    @XmlElement(name = "Vendor", required = true)
    protected Vendor vendor;
    @XmlElement(name = "CarRentalPickup", required = true)
    protected CarRentalPickup carRentalPickup;
    @XmlElement(name = "CarRentalDropoff", required = true)
    protected CarRentalDropoff carRentalDropoff;
    @XmlElement(name = "BookingClassCode")
    protected BookingClassCode bookingClassCode;
    @XmlElement(name = "CarRentalFee", required = true)
    protected List<CarRentalFee> carRentalFee;
    @XmlElement(name = "LimitedMileage")
    protected LimitedMileage limitedMileage;
    @XmlElement(name = "AvailablePrice")
    protected List<AvailablePrice> availablePrice;
    @XmlAttribute(name = "travelSegment", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String travelSegment;
    @XmlAttribute(name = "pickupTime", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String pickupTime;
    @XmlAttribute(name = "dropoffTime", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String dropoffTime;

    /**
     * Gets the value of the vendor property.
     * 
     * @return
     *     possible object is
     *     {@link Vendor }
     *     
     */
    public Vendor getVendor() {
        return vendor;
    }

    /**
     * Sets the value of the vendor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Vendor }
     *     
     */
    public void setVendor(Vendor value) {
        this.vendor = value;
    }

    /**
     * Gets the value of the carRentalPickup property.
     * 
     * @return
     *     possible object is
     *     {@link CarRentalPickup }
     *     
     */
    public CarRentalPickup getCarRentalPickup() {
        return carRentalPickup;
    }

    /**
     * Sets the value of the carRentalPickup property.
     * 
     * @param value
     *     allowed object is
     *     {@link CarRentalPickup }
     *     
     */
    public void setCarRentalPickup(CarRentalPickup value) {
        this.carRentalPickup = value;
    }

    /**
     * Gets the value of the carRentalDropoff property.
     * 
     * @return
     *     possible object is
     *     {@link CarRentalDropoff }
     *     
     */
    public CarRentalDropoff getCarRentalDropoff() {
        return carRentalDropoff;
    }

    /**
     * Sets the value of the carRentalDropoff property.
     * 
     * @param value
     *     allowed object is
     *     {@link CarRentalDropoff }
     *     
     */
    public void setCarRentalDropoff(CarRentalDropoff value) {
        this.carRentalDropoff = value;
    }

    /**
     * Gets the value of the bookingClassCode property.
     * 
     * @return
     *     possible object is
     *     {@link BookingClassCode }
     *     
     */
    public BookingClassCode getBookingClassCode() {
        return bookingClassCode;
    }

    /**
     * Sets the value of the bookingClassCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookingClassCode }
     *     
     */
    public void setBookingClassCode(BookingClassCode value) {
        this.bookingClassCode = value;
    }

    /**
     * Gets the value of the carRentalFee property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the carRentalFee property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCarRentalFee().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CarRentalFee }
     * 
     * 
     */
    public List<CarRentalFee> getCarRentalFee() {
        if (carRentalFee == null) {
            carRentalFee = new ArrayList<CarRentalFee>();
        }
        return this.carRentalFee;
    }

    /**
     * Gets the value of the limitedMileage property.
     * 
     * @return
     *     possible object is
     *     {@link LimitedMileage }
     *     
     */
    public LimitedMileage getLimitedMileage() {
        return limitedMileage;
    }

    /**
     * Sets the value of the limitedMileage property.
     * 
     * @param value
     *     allowed object is
     *     {@link LimitedMileage }
     *     
     */
    public void setLimitedMileage(LimitedMileage value) {
        this.limitedMileage = value;
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
     * Gets the value of the travelSegment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTravelSegment() {
        return travelSegment;
    }

    /**
     * Sets the value of the travelSegment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTravelSegment(String value) {
        this.travelSegment = value;
    }

    /**
     * Gets the value of the pickupTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPickupTime() {
        return pickupTime;
    }

    /**
     * Sets the value of the pickupTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPickupTime(String value) {
        this.pickupTime = value;
    }

    /**
     * Gets the value of the dropoffTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDropoffTime() {
        return dropoffTime;
    }

    /**
     * Sets the value of the dropoffTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDropoffTime(String value) {
        this.dropoffTime = value;
    }

}
