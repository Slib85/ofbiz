
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QualityInspectionLotStock complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QualityInspectionLotStock">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}UnrestrictedUseQuantity" minOccurs="0"/>
 *         &lt;element ref="{}ScrapQuantity" minOccurs="0"/>
 *         &lt;element ref="{}SampleUsageQuantity" minOccurs="0"/>
 *         &lt;element ref="{}BlockedQuantity" minOccurs="0"/>
 *         &lt;element ref="{}NewMaterialQuantity" minOccurs="0"/>
 *         &lt;element ref="{}ReserveQuantity" minOccurs="0"/>
 *         &lt;element ref="{}ReturnQuantity" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QualityInspectionLotStock", propOrder = {
    "unrestrictedUseQuantity",
    "scrapQuantity",
    "sampleUsageQuantity",
    "blockedQuantity",
    "newMaterialQuantity",
    "reserveQuantity",
    "returnQuantity"
})
public class QualityInspectionLotStock {

    @XmlElement(name = "UnrestrictedUseQuantity")
    protected UnrestrictedUseQuantity unrestrictedUseQuantity;
    @XmlElement(name = "ScrapQuantity")
    protected ScrapQuantity scrapQuantity;
    @XmlElement(name = "SampleUsageQuantity")
    protected SampleUsageQuantity sampleUsageQuantity;
    @XmlElement(name = "BlockedQuantity")
    protected BlockedQuantity blockedQuantity;
    @XmlElement(name = "NewMaterialQuantity")
    protected NewMaterialQuantity newMaterialQuantity;
    @XmlElement(name = "ReserveQuantity")
    protected ReserveQuantity reserveQuantity;
    @XmlElement(name = "ReturnQuantity")
    protected ReturnQuantity returnQuantity;

    /**
     * Gets the value of the unrestrictedUseQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link UnrestrictedUseQuantity }
     *     
     */
    public UnrestrictedUseQuantity getUnrestrictedUseQuantity() {
        return unrestrictedUseQuantity;
    }

    /**
     * Sets the value of the unrestrictedUseQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnrestrictedUseQuantity }
     *     
     */
    public void setUnrestrictedUseQuantity(UnrestrictedUseQuantity value) {
        this.unrestrictedUseQuantity = value;
    }

    /**
     * Gets the value of the scrapQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link ScrapQuantity }
     *     
     */
    public ScrapQuantity getScrapQuantity() {
        return scrapQuantity;
    }

    /**
     * Sets the value of the scrapQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScrapQuantity }
     *     
     */
    public void setScrapQuantity(ScrapQuantity value) {
        this.scrapQuantity = value;
    }

    /**
     * Gets the value of the sampleUsageQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link SampleUsageQuantity }
     *     
     */
    public SampleUsageQuantity getSampleUsageQuantity() {
        return sampleUsageQuantity;
    }

    /**
     * Sets the value of the sampleUsageQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link SampleUsageQuantity }
     *     
     */
    public void setSampleUsageQuantity(SampleUsageQuantity value) {
        this.sampleUsageQuantity = value;
    }

    /**
     * Gets the value of the blockedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link BlockedQuantity }
     *     
     */
    public BlockedQuantity getBlockedQuantity() {
        return blockedQuantity;
    }

    /**
     * Sets the value of the blockedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BlockedQuantity }
     *     
     */
    public void setBlockedQuantity(BlockedQuantity value) {
        this.blockedQuantity = value;
    }

    /**
     * Gets the value of the newMaterialQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link NewMaterialQuantity }
     *     
     */
    public NewMaterialQuantity getNewMaterialQuantity() {
        return newMaterialQuantity;
    }

    /**
     * Sets the value of the newMaterialQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link NewMaterialQuantity }
     *     
     */
    public void setNewMaterialQuantity(NewMaterialQuantity value) {
        this.newMaterialQuantity = value;
    }

    /**
     * Gets the value of the reserveQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link ReserveQuantity }
     *     
     */
    public ReserveQuantity getReserveQuantity() {
        return reserveQuantity;
    }

    /**
     * Sets the value of the reserveQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReserveQuantity }
     *     
     */
    public void setReserveQuantity(ReserveQuantity value) {
        this.reserveQuantity = value;
    }

    /**
     * Gets the value of the returnQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnQuantity }
     *     
     */
    public ReturnQuantity getReturnQuantity() {
        return returnQuantity;
    }

    /**
     * Sets the value of the returnQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnQuantity }
     *     
     */
    public void setReturnQuantity(ReturnQuantity value) {
        this.returnQuantity = value;
    }

}
