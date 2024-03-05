
package com.bigname.cxml.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TermsOfTransport complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TermsOfTransport">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}SealID" minOccurs="0"/>
 *         &lt;element ref="{}SealingPartyCode" minOccurs="0"/>
 *         &lt;element ref="{}EquipmentIdentificationCode" minOccurs="0"/>
 *         &lt;element ref="{}TransportTerms" minOccurs="0"/>
 *         &lt;element ref="{}Dimension" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "TermsOfTransport", propOrder = {
    "sealID",
    "sealingPartyCode",
    "equipmentIdentificationCode",
    "transportTerms",
    "dimension",
    "extrinsic"
})
public class TermsOfTransport {

    @XmlElement(name = "SealID")
    protected SealID sealID;
    @XmlElement(name = "SealingPartyCode")
    protected SealingPartyCode sealingPartyCode;
    @XmlElement(name = "EquipmentIdentificationCode")
    protected EquipmentIdentificationCode equipmentIdentificationCode;
    @XmlElement(name = "TransportTerms")
    protected TransportTerms transportTerms;
    @XmlElement(name = "Dimension")
    protected List<Dimension> dimension;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;

    /**
     * Gets the value of the sealID property.
     * 
     * @return
     *     possible object is
     *     {@link SealID }
     *     
     */
    public SealID getSealID() {
        return sealID;
    }

    /**
     * Sets the value of the sealID property.
     * 
     * @param value
     *     allowed object is
     *     {@link SealID }
     *     
     */
    public void setSealID(SealID value) {
        this.sealID = value;
    }

    /**
     * Gets the value of the sealingPartyCode property.
     * 
     * @return
     *     possible object is
     *     {@link SealingPartyCode }
     *     
     */
    public SealingPartyCode getSealingPartyCode() {
        return sealingPartyCode;
    }

    /**
     * Sets the value of the sealingPartyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link SealingPartyCode }
     *     
     */
    public void setSealingPartyCode(SealingPartyCode value) {
        this.sealingPartyCode = value;
    }

    /**
     * Gets the value of the equipmentIdentificationCode property.
     * 
     * @return
     *     possible object is
     *     {@link EquipmentIdentificationCode }
     *     
     */
    public EquipmentIdentificationCode getEquipmentIdentificationCode() {
        return equipmentIdentificationCode;
    }

    /**
     * Sets the value of the equipmentIdentificationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link EquipmentIdentificationCode }
     *     
     */
    public void setEquipmentIdentificationCode(EquipmentIdentificationCode value) {
        this.equipmentIdentificationCode = value;
    }

    /**
     * Gets the value of the transportTerms property.
     * 
     * @return
     *     possible object is
     *     {@link TransportTerms }
     *     
     */
    public TransportTerms getTransportTerms() {
        return transportTerms;
    }

    /**
     * Sets the value of the transportTerms property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportTerms }
     *     
     */
    public void setTransportTerms(TransportTerms value) {
        this.transportTerms = value;
    }

    /**
     * Gets the value of the dimension property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dimension property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDimension().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Dimension }
     * 
     * 
     */
    public List<Dimension> getDimension() {
        if (dimension == null) {
            dimension = new ArrayList<Dimension>();
        }
        return this.dimension;
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
