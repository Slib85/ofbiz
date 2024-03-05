//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.10.04 at 05:24:23 PM EDT 
//


package org.etsi.uri._01903.v1_3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CommitmentTypeIndication complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CommitmentTypeIndication">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://uri.etsi.org/01903/v1.3.2#}CommitmentTypeId"/>
 *         &lt;choice>
 *           &lt;element ref="{http://uri.etsi.org/01903/v1.3.2#}ObjectReference" maxOccurs="unbounded"/>
 *           &lt;element ref="{http://uri.etsi.org/01903/v1.3.2#}AllSignedDataObjects"/>
 *         &lt;/choice>
 *         &lt;element ref="{http://uri.etsi.org/01903/v1.3.2#}CommitmentTypeQualifiers" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommitmentTypeIndication", namespace = "http://uri.etsi.org/01903/v1.3.2#", propOrder = {
    "commitmentTypeId",
    "objectReference",
    "allSignedDataObjects",
    "commitmentTypeQualifiers"
})
public class CommitmentTypeIndication {

    @XmlElement(name = "CommitmentTypeId", namespace = "http://uri.etsi.org/01903/v1.3.2#", required = true)
    protected CommitmentTypeId commitmentTypeId;
    @XmlElement(name = "ObjectReference", namespace = "http://uri.etsi.org/01903/v1.3.2#")
    protected List<ObjectReference> objectReference;
    @XmlElement(name = "AllSignedDataObjects", namespace = "http://uri.etsi.org/01903/v1.3.2#")
    protected AllSignedDataObjects allSignedDataObjects;
    @XmlElement(name = "CommitmentTypeQualifiers", namespace = "http://uri.etsi.org/01903/v1.3.2#")
    protected CommitmentTypeQualifiers commitmentTypeQualifiers;

    /**
     * Gets the value of the commitmentTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link CommitmentTypeId }
     *     
     */
    public CommitmentTypeId getCommitmentTypeId() {
        return commitmentTypeId;
    }

    /**
     * Sets the value of the commitmentTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommitmentTypeId }
     *     
     */
    public void setCommitmentTypeId(CommitmentTypeId value) {
        this.commitmentTypeId = value;
    }

    /**
     * Gets the value of the objectReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the objectReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObjectReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ObjectReference }
     * 
     * 
     */
    public List<ObjectReference> getObjectReference() {
        if (objectReference == null) {
            objectReference = new ArrayList<ObjectReference>();
        }
        return this.objectReference;
    }

    /**
     * Gets the value of the allSignedDataObjects property.
     * 
     * @return
     *     possible object is
     *     {@link AllSignedDataObjects }
     *     
     */
    public AllSignedDataObjects getAllSignedDataObjects() {
        return allSignedDataObjects;
    }

    /**
     * Sets the value of the allSignedDataObjects property.
     * 
     * @param value
     *     allowed object is
     *     {@link AllSignedDataObjects }
     *     
     */
    public void setAllSignedDataObjects(AllSignedDataObjects value) {
        this.allSignedDataObjects = value;
    }

    /**
     * Gets the value of the commitmentTypeQualifiers property.
     * 
     * @return
     *     possible object is
     *     {@link CommitmentTypeQualifiers }
     *     
     */
    public CommitmentTypeQualifiers getCommitmentTypeQualifiers() {
        return commitmentTypeQualifiers;
    }

    /**
     * Sets the value of the commitmentTypeQualifiers property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommitmentTypeQualifiers }
     *     
     */
    public void setCommitmentTypeQualifiers(CommitmentTypeQualifiers value) {
        this.commitmentTypeQualifiers = value;
    }

}
