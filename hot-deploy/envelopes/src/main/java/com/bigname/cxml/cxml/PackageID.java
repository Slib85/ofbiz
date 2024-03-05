
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PackageID complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PackageID">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}GlobalIndividualAssetID" minOccurs="0"/>
 *         &lt;element ref="{}ReturnablePackageID" minOccurs="0"/>
 *         &lt;element ref="{}PackageTrackingID" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PackageID", propOrder = {
    "globalIndividualAssetID",
    "returnablePackageID",
    "packageTrackingID"
})
public class PackageID {

    @XmlElement(name = "GlobalIndividualAssetID")
    protected GlobalIndividualAssetID globalIndividualAssetID;
    @XmlElement(name = "ReturnablePackageID")
    protected ReturnablePackageID returnablePackageID;
    @XmlElement(name = "PackageTrackingID")
    protected PackageTrackingID packageTrackingID;

    /**
     * Gets the value of the globalIndividualAssetID property.
     * 
     * @return
     *     possible object is
     *     {@link GlobalIndividualAssetID }
     *     
     */
    public GlobalIndividualAssetID getGlobalIndividualAssetID() {
        return globalIndividualAssetID;
    }

    /**
     * Sets the value of the globalIndividualAssetID property.
     * 
     * @param value
     *     allowed object is
     *     {@link GlobalIndividualAssetID }
     *     
     */
    public void setGlobalIndividualAssetID(GlobalIndividualAssetID value) {
        this.globalIndividualAssetID = value;
    }

    /**
     * Gets the value of the returnablePackageID property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnablePackageID }
     *     
     */
    public ReturnablePackageID getReturnablePackageID() {
        return returnablePackageID;
    }

    /**
     * Sets the value of the returnablePackageID property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnablePackageID }
     *     
     */
    public void setReturnablePackageID(ReturnablePackageID value) {
        this.returnablePackageID = value;
    }

    /**
     * Gets the value of the packageTrackingID property.
     * 
     * @return
     *     possible object is
     *     {@link PackageTrackingID }
     *     
     */
    public PackageTrackingID getPackageTrackingID() {
        return packageTrackingID;
    }

    /**
     * Sets the value of the packageTrackingID property.
     * 
     * @param value
     *     allowed object is
     *     {@link PackageTrackingID }
     *     
     */
    public void setPackageTrackingID(PackageTrackingID value) {
        this.packageTrackingID = value;
    }

}
