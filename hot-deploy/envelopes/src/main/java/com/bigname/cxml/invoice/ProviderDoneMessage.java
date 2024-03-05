
package com.bigname.cxml.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProviderDoneMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProviderDoneMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}OriginatorCookie"/>
 *         &lt;element ref="{}ReturnData" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProviderDoneMessage", propOrder = {
    "originatorCookie",
    "returnData"
})
public class ProviderDoneMessage {

    @XmlElement(name = "OriginatorCookie", required = true)
    protected OriginatorCookie originatorCookie;
    @XmlElement(name = "ReturnData")
    protected List<ReturnData> returnData;

    /**
     * Gets the value of the originatorCookie property.
     * 
     * @return
     *     possible object is
     *     {@link OriginatorCookie }
     *     
     */
    public OriginatorCookie getOriginatorCookie() {
        return originatorCookie;
    }

    /**
     * Sets the value of the originatorCookie property.
     * 
     * @param value
     *     allowed object is
     *     {@link OriginatorCookie }
     *     
     */
    public void setOriginatorCookie(OriginatorCookie value) {
        this.originatorCookie = value;
    }

    /**
     * Gets the value of the returnData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the returnData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReturnData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReturnData }
     * 
     * 
     */
    public List<ReturnData> getReturnData() {
        if (returnData == null) {
            returnData = new ArrayList<ReturnData>();
        }
        return this.returnData;
    }

}
