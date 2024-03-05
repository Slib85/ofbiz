
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Contractor complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Contractor">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ContractorIdentifier"/>
 *         &lt;element ref="{}Contact"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Contractor", propOrder = {
    "contractorIdentifier",
    "contact"
})
public class Contractor {

    @XmlElement(name = "ContractorIdentifier", required = true)
    protected ContractorIdentifier contractorIdentifier;
    @XmlElement(name = "Contact", required = true)
    protected Contact contact;

    /**
     * Gets the value of the contractorIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link ContractorIdentifier }
     *     
     */
    public ContractorIdentifier getContractorIdentifier() {
        return contractorIdentifier;
    }

    /**
     * Sets the value of the contractorIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractorIdentifier }
     *     
     */
    public void setContractorIdentifier(ContractorIdentifier value) {
        this.contractorIdentifier = value;
    }

    /**
     * Gets the value of the contact property.
     * 
     * @return
     *     possible object is
     *     {@link Contact }
     *     
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Sets the value of the contact property.
     * 
     * @param value
     *     allowed object is
     *     {@link Contact }
     *     
     */
    public void setContact(Contact value) {
        this.contact = value;
    }

}
