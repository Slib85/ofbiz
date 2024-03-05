
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceEntryItemReference complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceEntryItemReference">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}DocumentReference"/>
 *       &lt;/sequence>
 *       &lt;attribute name="serviceLineNumber" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="serviceEntryID" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="serviceEntryDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceEntryItemReference", propOrder = {
    "documentReference"
})
public class ServiceEntryItemReference {

    @XmlElement(name = "DocumentReference", required = true)
    protected DocumentReference documentReference;
    @XmlAttribute(name = "serviceLineNumber", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String serviceLineNumber;
    @XmlAttribute(name = "serviceEntryID")
    @XmlSchemaType(name = "anySimpleType")
    protected String serviceEntryID;
    @XmlAttribute(name = "serviceEntryDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String serviceEntryDate;

    /**
     * Gets the value of the documentReference property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentReference }
     *     
     */
    public DocumentReference getDocumentReference() {
        return documentReference;
    }

    /**
     * Sets the value of the documentReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentReference }
     *     
     */
    public void setDocumentReference(DocumentReference value) {
        this.documentReference = value;
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
