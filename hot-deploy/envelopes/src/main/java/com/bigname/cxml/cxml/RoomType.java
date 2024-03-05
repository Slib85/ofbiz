
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
 * <p>Java class for RoomType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RoomType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Description" minOccurs="0"/>
 *         &lt;element ref="{}Amenities" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="smoking" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="no"/>
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="numberOfBed" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="bedType">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="full"/>
 *             &lt;enumeration value="king"/>
 *             &lt;enumeration value="other"/>
 *             &lt;enumeration value="queen"/>
 *             &lt;enumeration value="double"/>
 *             &lt;enumeration value="single"/>
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
@XmlType(name = "RoomType", propOrder = {
    "description",
    "amenities"
})
public class RoomType {

    @XmlElement(name = "Description")
    protected Description description;
    @XmlElement(name = "Amenities")
    protected List<Amenities> amenities;
    @XmlAttribute(name = "smoking", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String smoking;
    @XmlAttribute(name = "numberOfBed")
    @XmlSchemaType(name = "anySimpleType")
    protected String numberOfBed;
    @XmlAttribute(name = "bedType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String bedType;

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
     * Gets the value of the amenities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the amenities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAmenities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Amenities }
     * 
     * 
     */
    public List<Amenities> getAmenities() {
        if (amenities == null) {
            amenities = new ArrayList<Amenities>();
        }
        return this.amenities;
    }

    /**
     * Gets the value of the smoking property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmoking() {
        return smoking;
    }

    /**
     * Sets the value of the smoking property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmoking(String value) {
        this.smoking = value;
    }

    /**
     * Gets the value of the numberOfBed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberOfBed() {
        return numberOfBed;
    }

    /**
     * Sets the value of the numberOfBed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberOfBed(String value) {
        this.numberOfBed = value;
    }

    /**
     * Gets the value of the bedType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBedType() {
        return bedType;
    }

    /**
     * Sets the value of the bedType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBedType(String value) {
        this.bedType = value;
    }

}
