
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PunchOutSetupResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PunchOutSetupResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}StartPage"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PunchOutSetupResponse", propOrder = {
    "startPage"
})
public class PunchOutSetupResponse {

    @XmlElement(name = "StartPage", required = true)
    protected StartPage startPage;

    /**
     * Gets the value of the startPage property.
     * 
     * @return
     *     possible object is
     *     {@link StartPage }
     *     
     */
    public StartPage getStartPage() {
        return startPage;
    }

    /**
     * Sets the value of the startPage property.
     * 
     * @param value
     *     allowed object is
     *     {@link StartPage }
     *     
     */
    public void setStartPage(StartPage value) {
        this.startPage = value;
    }

}
