
package com.bigname.cxml.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PropertyValuation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PropertyValuation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}PropertyReference" minOccurs="0"/>
 *         &lt;element ref="{}ValueGroup" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PropertyValuation", propOrder = {
    "propertyReference",
    "valueGroup"
})
public class PropertyValuation {

    @XmlElement(name = "PropertyReference")
    protected PropertyReference propertyReference;
    @XmlElement(name = "ValueGroup")
    protected List<ValueGroup> valueGroup;

    /**
     * Gets the value of the propertyReference property.
     * 
     * @return
     *     possible object is
     *     {@link PropertyReference }
     *     
     */
    public PropertyReference getPropertyReference() {
        return propertyReference;
    }

    /**
     * Sets the value of the propertyReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertyReference }
     *     
     */
    public void setPropertyReference(PropertyReference value) {
        this.propertyReference = value;
    }

    /**
     * Gets the value of the valueGroup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the valueGroup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValueGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ValueGroup }
     * 
     * 
     */
    public List<ValueGroup> getValueGroup() {
        if (valueGroup == null) {
            valueGroup = new ArrayList<ValueGroup>();
        }
        return this.valueGroup;
    }

}
