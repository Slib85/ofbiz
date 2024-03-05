
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PayableMasterAgreementInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PayableMasterAgreementInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{}MasterAgreementReference"/>
 *         &lt;element ref="{}MasterAgreementIDInfo"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayableMasterAgreementInfo", propOrder = {
    "masterAgreementReference",
    "masterAgreementIDInfo"
})
public class PayableMasterAgreementInfo {

    @XmlElement(name = "MasterAgreementReference")
    protected MasterAgreementReference masterAgreementReference;
    @XmlElement(name = "MasterAgreementIDInfo")
    protected MasterAgreementIDInfo masterAgreementIDInfo;

    /**
     * Gets the value of the masterAgreementReference property.
     * 
     * @return
     *     possible object is
     *     {@link MasterAgreementReference }
     *     
     */
    public MasterAgreementReference getMasterAgreementReference() {
        return masterAgreementReference;
    }

    /**
     * Sets the value of the masterAgreementReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link MasterAgreementReference }
     *     
     */
    public void setMasterAgreementReference(MasterAgreementReference value) {
        this.masterAgreementReference = value;
    }

    /**
     * Gets the value of the masterAgreementIDInfo property.
     * 
     * @return
     *     possible object is
     *     {@link MasterAgreementIDInfo }
     *     
     */
    public MasterAgreementIDInfo getMasterAgreementIDInfo() {
        return masterAgreementIDInfo;
    }

    /**
     * Sets the value of the masterAgreementIDInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link MasterAgreementIDInfo }
     *     
     */
    public void setMasterAgreementIDInfo(MasterAgreementIDInfo value) {
        this.masterAgreementIDInfo = value;
    }

}
