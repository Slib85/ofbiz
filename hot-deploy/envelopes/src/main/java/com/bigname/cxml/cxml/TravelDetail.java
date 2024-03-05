
package com.bigname.cxml.cxml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TravelDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TravelDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element ref="{}AirDetail"/>
 *           &lt;element ref="{}CarRentalDetail"/>
 *           &lt;element ref="{}HotelDetail"/>
 *           &lt;element ref="{}RailDetail"/>
 *         &lt;/choice>
 *         &lt;element ref="{}PolicyViolation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Comments" minOccurs="0"/>
 *         &lt;element ref="{}TermsAndConditions" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="confirmationNumber" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="pnrLocator" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="quoteExpirationTime" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TravelDetail", propOrder = {
    "airDetail",
    "carRentalDetail",
    "hotelDetail",
    "railDetail",
    "policyViolation",
    "comments",
    "termsAndConditions"
})
public class TravelDetail {

    @XmlElement(name = "AirDetail")
    protected AirDetail airDetail;
    @XmlElement(name = "CarRentalDetail")
    protected CarRentalDetail carRentalDetail;
    @XmlElement(name = "HotelDetail")
    protected HotelDetail hotelDetail;
    @XmlElement(name = "RailDetail")
    protected RailDetail railDetail;
    @XmlElement(name = "PolicyViolation")
    protected List<PolicyViolation> policyViolation;
    @XmlElement(name = "Comments")
    protected Comments comments;
    @XmlElement(name = "TermsAndConditions")
    protected TermsAndConditions termsAndConditions;
    @XmlAttribute(name = "confirmationNumber", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String confirmationNumber;
    @XmlAttribute(name = "pnrLocator")
    @XmlSchemaType(name = "anySimpleType")
    protected String pnrLocator;
    @XmlAttribute(name = "quoteExpirationTime")
    @XmlSchemaType(name = "anySimpleType")
    protected String quoteExpirationTime;

    /**
     * Gets the value of the airDetail property.
     * 
     * @return
     *     possible object is
     *     {@link AirDetail }
     *     
     */
    public AirDetail getAirDetail() {
        return airDetail;
    }

    /**
     * Sets the value of the airDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link AirDetail }
     *     
     */
    public void setAirDetail(AirDetail value) {
        this.airDetail = value;
    }

    /**
     * Gets the value of the carRentalDetail property.
     * 
     * @return
     *     possible object is
     *     {@link CarRentalDetail }
     *     
     */
    public CarRentalDetail getCarRentalDetail() {
        return carRentalDetail;
    }

    /**
     * Sets the value of the carRentalDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link CarRentalDetail }
     *     
     */
    public void setCarRentalDetail(CarRentalDetail value) {
        this.carRentalDetail = value;
    }

    /**
     * Gets the value of the hotelDetail property.
     * 
     * @return
     *     possible object is
     *     {@link HotelDetail }
     *     
     */
    public HotelDetail getHotelDetail() {
        return hotelDetail;
    }

    /**
     * Sets the value of the hotelDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link HotelDetail }
     *     
     */
    public void setHotelDetail(HotelDetail value) {
        this.hotelDetail = value;
    }

    /**
     * Gets the value of the railDetail property.
     * 
     * @return
     *     possible object is
     *     {@link RailDetail }
     *     
     */
    public RailDetail getRailDetail() {
        return railDetail;
    }

    /**
     * Sets the value of the railDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link RailDetail }
     *     
     */
    public void setRailDetail(RailDetail value) {
        this.railDetail = value;
    }

    /**
     * Gets the value of the policyViolation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the policyViolation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPolicyViolation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PolicyViolation }
     * 
     * 
     */
    public List<PolicyViolation> getPolicyViolation() {
        if (policyViolation == null) {
            policyViolation = new ArrayList<PolicyViolation>();
        }
        return this.policyViolation;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link Comments }
     *     
     */
    public Comments getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link Comments }
     *     
     */
    public void setComments(Comments value) {
        this.comments = value;
    }

    /**
     * Gets the value of the termsAndConditions property.
     * 
     * @return
     *     possible object is
     *     {@link TermsAndConditions }
     *     
     */
    public TermsAndConditions getTermsAndConditions() {
        return termsAndConditions;
    }

    /**
     * Sets the value of the termsAndConditions property.
     * 
     * @param value
     *     allowed object is
     *     {@link TermsAndConditions }
     *     
     */
    public void setTermsAndConditions(TermsAndConditions value) {
        this.termsAndConditions = value;
    }

    /**
     * Gets the value of the confirmationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    /**
     * Sets the value of the confirmationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfirmationNumber(String value) {
        this.confirmationNumber = value;
    }

    /**
     * Gets the value of the pnrLocator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPnrLocator() {
        return pnrLocator;
    }

    /**
     * Sets the value of the pnrLocator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPnrLocator(String value) {
        this.pnrLocator = value;
    }

    /**
     * Gets the value of the quoteExpirationTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuoteExpirationTime() {
        return quoteExpirationTime;
    }

    /**
     * Sets the value of the quoteExpirationTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuoteExpirationTime(String value) {
        this.quoteExpirationTime = value;
    }

}
