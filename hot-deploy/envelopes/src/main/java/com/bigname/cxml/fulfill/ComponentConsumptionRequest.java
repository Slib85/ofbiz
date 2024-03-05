
package com.bigname.cxml.fulfill;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ComponentConsumptionRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ComponentConsumptionRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ComponentConsumptionHeader"/>
 *         &lt;element ref="{}ComponentConsumptionPortion" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComponentConsumptionRequest", propOrder = {
    "componentConsumptionHeader",
    "componentConsumptionPortion"
})
public class ComponentConsumptionRequest {

    @XmlElement(name = "ComponentConsumptionHeader", required = true)
    protected ComponentConsumptionHeader componentConsumptionHeader;
    @XmlElement(name = "ComponentConsumptionPortion")
    protected List<ComponentConsumptionPortion> componentConsumptionPortion;

    /**
     * Gets the value of the componentConsumptionHeader property.
     * 
     * @return
     *     possible object is
     *     {@link ComponentConsumptionHeader }
     *     
     */
    public ComponentConsumptionHeader getComponentConsumptionHeader() {
        return componentConsumptionHeader;
    }

    /**
     * Sets the value of the componentConsumptionHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComponentConsumptionHeader }
     *     
     */
    public void setComponentConsumptionHeader(ComponentConsumptionHeader value) {
        this.componentConsumptionHeader = value;
    }

    /**
     * Gets the value of the componentConsumptionPortion property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the componentConsumptionPortion property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComponentConsumptionPortion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComponentConsumptionPortion }
     * 
     * 
     */
    public List<ComponentConsumptionPortion> getComponentConsumptionPortion() {
        if (componentConsumptionPortion == null) {
            componentConsumptionPortion = new ArrayList<ComponentConsumptionPortion>();
        }
        return this.componentConsumptionPortion;
    }

}
