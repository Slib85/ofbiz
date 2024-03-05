
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QualityInspectionRequestReference complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QualityInspectionRequestReference">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}DocumentReference" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="inspectionID" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="inspectionDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QualityInspectionRequestReference", propOrder = {
    "documentReference"
})
public class QualityInspectionRequestReference {

    @XmlElement(name = "DocumentReference")
    protected DocumentReference documentReference;
    @XmlAttribute(name = "inspectionID")
    @XmlSchemaType(name = "anySimpleType")
    protected String inspectionID;
    @XmlAttribute(name = "inspectionDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String inspectionDate;

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
     * Gets the value of the inspectionID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInspectionID() {
        return inspectionID;
    }

    /**
     * Sets the value of the inspectionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInspectionID(String value) {
        this.inspectionID = value;
    }

    /**
     * Gets the value of the inspectionDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInspectionDate() {
        return inspectionDate;
    }

    /**
     * Sets the value of the inspectionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInspectionDate(String value) {
        this.inspectionDate = value;
    }

}
