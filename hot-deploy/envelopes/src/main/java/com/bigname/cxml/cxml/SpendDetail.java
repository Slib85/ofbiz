
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SpendDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SpendDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{}TravelDetail"/>
 *         &lt;element ref="{}FeeDetail"/>
 *         &lt;element ref="{}LaborDetail"/>
 *         &lt;element ref="{}Extrinsic"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpendDetail", propOrder = {
    "travelDetail",
    "feeDetail",
    "laborDetail",
    "extrinsic"
})
public class SpendDetail {

    @XmlElement(name = "TravelDetail")
    protected TravelDetail travelDetail;
    @XmlElement(name = "FeeDetail")
    protected FeeDetail feeDetail;
    @XmlElement(name = "LaborDetail")
    protected LaborDetail laborDetail;
    @XmlElement(name = "Extrinsic")
    protected Extrinsic extrinsic;

    /**
     * Gets the value of the travelDetail property.
     * 
     * @return
     *     possible object is
     *     {@link TravelDetail }
     *     
     */
    public TravelDetail getTravelDetail() {
        return travelDetail;
    }

    /**
     * Sets the value of the travelDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link TravelDetail }
     *     
     */
    public void setTravelDetail(TravelDetail value) {
        this.travelDetail = value;
    }

    /**
     * Gets the value of the feeDetail property.
     * 
     * @return
     *     possible object is
     *     {@link FeeDetail }
     *     
     */
    public FeeDetail getFeeDetail() {
        return feeDetail;
    }

    /**
     * Sets the value of the feeDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link FeeDetail }
     *     
     */
    public void setFeeDetail(FeeDetail value) {
        this.feeDetail = value;
    }

    /**
     * Gets the value of the laborDetail property.
     * 
     * @return
     *     possible object is
     *     {@link LaborDetail }
     *     
     */
    public LaborDetail getLaborDetail() {
        return laborDetail;
    }

    /**
     * Sets the value of the laborDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link LaborDetail }
     *     
     */
    public void setLaborDetail(LaborDetail value) {
        this.laborDetail = value;
    }

    /**
     * Gets the value of the extrinsic property.
     * 
     * @return
     *     possible object is
     *     {@link Extrinsic }
     *     
     */
    public Extrinsic getExtrinsic() {
        return extrinsic;
    }

    /**
     * Sets the value of the extrinsic property.
     * 
     * @param value
     *     allowed object is
     *     {@link Extrinsic }
     *     
     */
    public void setExtrinsic(Extrinsic value) {
        this.extrinsic = value;
    }

}
