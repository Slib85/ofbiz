
package com.bigname.cxml.cxml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ContractRequestHeader complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContractRequestHeader">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}LegalEntity"/>
 *         &lt;element ref="{}OrganizationID"/>
 *         &lt;element ref="{}OrganizationalUnit" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}PaymentTerm" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}QuoteRequestReference" minOccurs="0"/>
 *         &lt;element ref="{}MaxAmount" minOccurs="0"/>
 *         &lt;element ref="{}MinAmount" minOccurs="0"/>
 *         &lt;element ref="{}MaxReleaseAmount" minOccurs="0"/>
 *         &lt;element ref="{}MinReleaseAmount" minOccurs="0"/>
 *         &lt;element ref="{}Contact" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Comments" minOccurs="0"/>
 *         &lt;element ref="{}DocumentInfo" minOccurs="0"/>
 *         &lt;element ref="{}ParentContractInfo" minOccurs="0"/>
 *         &lt;element ref="{}FollowUpDocument" minOccurs="0"/>
 *         &lt;element ref="{}TermsOfDelivery" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="contractID" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="type" default="value">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="value"/>
 *             &lt;enumeration value="quantity"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="createDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="agreementDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="effectiveDate" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="expirationDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}lang use="required""/>
 *       &lt;attribute name="operation" default="new">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="new"/>
 *             &lt;enumeration value="delete"/>
 *             &lt;enumeration value="update"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContractRequestHeader", propOrder = {
    "legalEntity",
    "organizationID",
    "organizationalUnit",
    "paymentTerm",
    "quoteRequestReference",
    "maxAmount",
    "minAmount",
    "maxReleaseAmount",
    "minReleaseAmount",
    "contact",
    "comments",
    "documentInfo",
    "parentContractInfo",
    "followUpDocument",
    "termsOfDelivery",
    "extrinsic"
})
public class ContractRequestHeader {

    @XmlElement(name = "LegalEntity", required = true)
    protected LegalEntity legalEntity;
    @XmlElement(name = "OrganizationID", required = true)
    protected OrganizationID organizationID;
    @XmlElement(name = "OrganizationalUnit")
    protected List<OrganizationalUnit> organizationalUnit;
    @XmlElement(name = "PaymentTerm")
    protected List<PaymentTerm> paymentTerm;
    @XmlElement(name = "QuoteRequestReference")
    protected QuoteRequestReference quoteRequestReference;
    @XmlElement(name = "MaxAmount")
    protected MaxAmount maxAmount;
    @XmlElement(name = "MinAmount")
    protected MinAmount minAmount;
    @XmlElement(name = "MaxReleaseAmount")
    protected MaxReleaseAmount maxReleaseAmount;
    @XmlElement(name = "MinReleaseAmount")
    protected MinReleaseAmount minReleaseAmount;
    @XmlElement(name = "Contact")
    protected List<Contact> contact;
    @XmlElement(name = "Comments")
    protected Comments comments;
    @XmlElement(name = "DocumentInfo")
    protected DocumentInfo documentInfo;
    @XmlElement(name = "ParentContractInfo")
    protected ParentContractInfo parentContractInfo;
    @XmlElement(name = "FollowUpDocument")
    protected FollowUpDocument followUpDocument;
    @XmlElement(name = "TermsOfDelivery")
    protected TermsOfDelivery termsOfDelivery;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(name = "contractID", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String contractID;
    @XmlAttribute(name = "type")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String type;
    @XmlAttribute(name = "createDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String createDate;
    @XmlAttribute(name = "agreementDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String agreementDate;
    @XmlAttribute(name = "effectiveDate", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String effectiveDate;
    @XmlAttribute(name = "expirationDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String expirationDate;
    @XmlAttribute(name = "lang", namespace = "http://www.w3.org/XML/1998/namespace", required = true)
    protected String lang;
    @XmlAttribute(name = "operation")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String operation;

    /**
     * Gets the value of the legalEntity property.
     * 
     * @return
     *     possible object is
     *     {@link LegalEntity }
     *     
     */
    public LegalEntity getLegalEntity() {
        return legalEntity;
    }

    /**
     * Sets the value of the legalEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link LegalEntity }
     *     
     */
    public void setLegalEntity(LegalEntity value) {
        this.legalEntity = value;
    }

    /**
     * Gets the value of the organizationID property.
     * 
     * @return
     *     possible object is
     *     {@link OrganizationID }
     *     
     */
    public OrganizationID getOrganizationID() {
        return organizationID;
    }

    /**
     * Sets the value of the organizationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganizationID }
     *     
     */
    public void setOrganizationID(OrganizationID value) {
        this.organizationID = value;
    }

    /**
     * Gets the value of the organizationalUnit property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the organizationalUnit property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrganizationalUnit().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrganizationalUnit }
     * 
     * 
     */
    public List<OrganizationalUnit> getOrganizationalUnit() {
        if (organizationalUnit == null) {
            organizationalUnit = new ArrayList<OrganizationalUnit>();
        }
        return this.organizationalUnit;
    }

    /**
     * Gets the value of the paymentTerm property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentTerm property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentTerm().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentTerm }
     * 
     * 
     */
    public List<PaymentTerm> getPaymentTerm() {
        if (paymentTerm == null) {
            paymentTerm = new ArrayList<PaymentTerm>();
        }
        return this.paymentTerm;
    }

    /**
     * Gets the value of the quoteRequestReference property.
     * 
     * @return
     *     possible object is
     *     {@link QuoteRequestReference }
     *     
     */
    public QuoteRequestReference getQuoteRequestReference() {
        return quoteRequestReference;
    }

    /**
     * Sets the value of the quoteRequestReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuoteRequestReference }
     *     
     */
    public void setQuoteRequestReference(QuoteRequestReference value) {
        this.quoteRequestReference = value;
    }

    /**
     * Gets the value of the maxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MaxAmount }
     *     
     */
    public MaxAmount getMaxAmount() {
        return maxAmount;
    }

    /**
     * Sets the value of the maxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaxAmount }
     *     
     */
    public void setMaxAmount(MaxAmount value) {
        this.maxAmount = value;
    }

    /**
     * Gets the value of the minAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MinAmount }
     *     
     */
    public MinAmount getMinAmount() {
        return minAmount;
    }

    /**
     * Sets the value of the minAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MinAmount }
     *     
     */
    public void setMinAmount(MinAmount value) {
        this.minAmount = value;
    }

    /**
     * Gets the value of the maxReleaseAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MaxReleaseAmount }
     *     
     */
    public MaxReleaseAmount getMaxReleaseAmount() {
        return maxReleaseAmount;
    }

    /**
     * Sets the value of the maxReleaseAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaxReleaseAmount }
     *     
     */
    public void setMaxReleaseAmount(MaxReleaseAmount value) {
        this.maxReleaseAmount = value;
    }

    /**
     * Gets the value of the minReleaseAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MinReleaseAmount }
     *     
     */
    public MinReleaseAmount getMinReleaseAmount() {
        return minReleaseAmount;
    }

    /**
     * Sets the value of the minReleaseAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MinReleaseAmount }
     *     
     */
    public void setMinReleaseAmount(MinReleaseAmount value) {
        this.minReleaseAmount = value;
    }

    /**
     * Gets the value of the contact property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contact property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContact().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Contact }
     * 
     * 
     */
    public List<Contact> getContact() {
        if (contact == null) {
            contact = new ArrayList<Contact>();
        }
        return this.contact;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link Comments }
     *     
     */
    public Comments getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link Comments }
     *     
     */
    public void setComments(Comments value) {
        this.comments = value;
    }

    /**
     * Gets the value of the documentInfo property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentInfo }
     *     
     */
    public DocumentInfo getDocumentInfo() {
        return documentInfo;
    }

    /**
     * Sets the value of the documentInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentInfo }
     *     
     */
    public void setDocumentInfo(DocumentInfo value) {
        this.documentInfo = value;
    }

    /**
     * Gets the value of the parentContractInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ParentContractInfo }
     *     
     */
    public ParentContractInfo getParentContractInfo() {
        return parentContractInfo;
    }

    /**
     * Sets the value of the parentContractInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParentContractInfo }
     *     
     */
    public void setParentContractInfo(ParentContractInfo value) {
        this.parentContractInfo = value;
    }

    /**
     * Gets the value of the followUpDocument property.
     * 
     * @return
     *     possible object is
     *     {@link FollowUpDocument }
     *     
     */
    public FollowUpDocument getFollowUpDocument() {
        return followUpDocument;
    }

    /**
     * Sets the value of the followUpDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link FollowUpDocument }
     *     
     */
    public void setFollowUpDocument(FollowUpDocument value) {
        this.followUpDocument = value;
    }

    /**
     * Gets the value of the termsOfDelivery property.
     * 
     * @return
     *     possible object is
     *     {@link TermsOfDelivery }
     *     
     */
    public TermsOfDelivery getTermsOfDelivery() {
        return termsOfDelivery;
    }

    /**
     * Sets the value of the termsOfDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link TermsOfDelivery }
     *     
     */
    public void setTermsOfDelivery(TermsOfDelivery value) {
        this.termsOfDelivery = value;
    }

    /**
     * Gets the value of the extrinsic property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extrinsic property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtrinsic().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Extrinsic }
     * 
     * 
     */
    public List<Extrinsic> getExtrinsic() {
        if (extrinsic == null) {
            extrinsic = new ArrayList<Extrinsic>();
        }
        return this.extrinsic;
    }

    /**
     * Gets the value of the contractID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractID() {
        return contractID;
    }

    /**
     * Sets the value of the contractID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractID(String value) {
        this.contractID = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        if (type == null) {
            return "value";
        } else {
            return type;
        }
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the createDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * Sets the value of the createDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateDate(String value) {
        this.createDate = value;
    }

    /**
     * Gets the value of the agreementDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreementDate() {
        return agreementDate;
    }

    /**
     * Sets the value of the agreementDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreementDate(String value) {
        this.agreementDate = value;
    }

    /**
     * Gets the value of the effectiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the value of the effectiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffectiveDate(String value) {
        this.effectiveDate = value;
    }

    /**
     * Gets the value of the expirationDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the value of the expirationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpirationDate(String value) {
        this.expirationDate = value;
    }

    /**
     * Gets the value of the lang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the value of the lang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLang(String value) {
        this.lang = value;
    }

    /**
     * Gets the value of the operation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperation() {
        if (operation == null) {
            return "new";
        } else {
            return operation;
        }
    }

    /**
     * Sets the value of the operation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperation(String value) {
        this.operation = value;
    }

}
