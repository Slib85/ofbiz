
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Upper complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Upper">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Tolerances"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Upper", propOrder = {
    "tolerances"
})
public class Upper {

    @XmlElement(name = "Tolerances", required = true)
    protected Tolerances tolerances;

    /**
     * Gets the value of the tolerances property.
     * 
     * @return
     *     possible object is
     *     {@link Tolerances }
     *     
     */
    public Tolerances getTolerances() {
        return tolerances;
    }

    /**
     * Sets the value of the tolerances property.
     * 
     * @param value
     *     allowed object is
     *     {@link Tolerances }
     *     
     */
    public void setTolerances(Tolerances value) {
        this.tolerances = value;
    }

}
