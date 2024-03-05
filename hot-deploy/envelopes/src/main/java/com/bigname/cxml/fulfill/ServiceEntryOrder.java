
package com.bigname.cxml.fulfill;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceEntryOrder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceEntryOrder">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ServiceEntryOrderInfo"/>
 *         &lt;element ref="{}ServiceEntryItem" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceEntryOrder", propOrder = {
    "serviceEntryOrderInfo",
    "serviceEntryItem"
})
public class ServiceEntryOrder {

    @XmlElement(name = "ServiceEntryOrderInfo", required = true)
    protected ServiceEntryOrderInfo serviceEntryOrderInfo;
    @XmlElement(name = "ServiceEntryItem", required = true)
    protected List<ServiceEntryItem> serviceEntryItem;

    /**
     * Gets the value of the serviceEntryOrderInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEntryOrderInfo }
     *     
     */
    public ServiceEntryOrderInfo getServiceEntryOrderInfo() {
        return serviceEntryOrderInfo;
    }

    /**
     * Sets the value of the serviceEntryOrderInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEntryOrderInfo }
     *     
     */
    public void setServiceEntryOrderInfo(ServiceEntryOrderInfo value) {
        this.serviceEntryOrderInfo = value;
    }

    /**
     * Gets the value of the serviceEntryItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serviceEntryItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServiceEntryItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServiceEntryItem }
     * 
     * 
     */
    public List<ServiceEntryItem> getServiceEntryItem() {
        if (serviceEntryItem == null) {
            serviceEntryItem = new ArrayList<ServiceEntryItem>();
        }
        return this.serviceEntryItem;
    }

}
