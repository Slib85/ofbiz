
package com.bigname.cxml.cxml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContractRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContractRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ContractRequestHeader"/>
 *         &lt;element ref="{}ContractItemIn" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContractRequest", propOrder = {
    "contractRequestHeader",
    "contractItemIn"
})
public class ContractRequest {

    @XmlElement(name = "ContractRequestHeader", required = true)
    protected ContractRequestHeader contractRequestHeader;
    @XmlElement(name = "ContractItemIn", required = true)
    protected List<ContractItemIn> contractItemIn;

    /**
     * Gets the value of the contractRequestHeader property.
     * 
     * @return
     *     possible object is
     *     {@link ContractRequestHeader }
     *     
     */
    public ContractRequestHeader getContractRequestHeader() {
        return contractRequestHeader;
    }

    /**
     * Sets the value of the contractRequestHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractRequestHeader }
     *     
     */
    public void setContractRequestHeader(ContractRequestHeader value) {
        this.contractRequestHeader = value;
    }

    /**
     * Gets the value of the contractItemIn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contractItemIn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContractItemIn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContractItemIn }
     * 
     * 
     */
    public List<ContractItemIn> getContractItemIn() {
        if (contractItemIn == null) {
            contractItemIn = new ArrayList<ContractItemIn>();
        }
        return this.contractItemIn;
    }

}
