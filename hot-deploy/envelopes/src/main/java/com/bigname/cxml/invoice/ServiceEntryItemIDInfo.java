
package com.bigname.cxml.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceEntryItemIDInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceEntryItemIDInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}IdReference" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="serviceLineNumber" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="serviceEntryID" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="serviceEntryDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceEntryItemIDInfo", propOrder = {
    "idReference"
})
public class ServiceEntryItemIDInfo {

    @XmlElement(name = "IdReference")
    protected List<IdReference> idReference;
    @XmlAttribute(name = "serviceLineNumber", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String serviceLineNumber;
    @XmlAttribute(name = "serviceEntryID", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String serviceEntryID;
    @XmlAttribute(name = "serviceEntryDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String serviceEntryDate;

    /**
     * Gets the value of the idReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the idReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IdReference }
     * 
     * 
     */
    public List<IdReference> getIdReference() {
        if (idReference == null) {
            idReference = new ArrayList<IdReference>();
        }
        return this.idReference;
    }

    /**
     * Gets the value of the serviceLineNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceLineNumber() {
        return serviceLineNumber;
    }

    /**
     * Sets the value of the serviceLineNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceLineNumber(String value) {
        this.serviceLineNumber = value;
    }

    /**
     * Gets the value of the serviceEntryID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceEntryID() {
        return serviceEntryID;
    }

    /**
     * Sets the value of the serviceEntryID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceEntryID(String value) {
        this.serviceEntryID = value;
    }

    /**
     * Gets the value of the serviceEntryDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceEntryDate() {
        return serviceEntryDate;
    }

    /**
     * Sets the value of the serviceEntryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceEntryDate(String value) {
        this.serviceEntryDate = value;
    }

}
