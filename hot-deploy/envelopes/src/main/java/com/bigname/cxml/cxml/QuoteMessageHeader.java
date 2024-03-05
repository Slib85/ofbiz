
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
 * <p>Java class for QuoteMessageHeader complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QuoteMessageHeader">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}OrganizationID"/>
 *         &lt;element ref="{}Total"/>
 *         &lt;element ref="{}ShipTo" minOccurs="0"/>
 *         &lt;element ref="{}Contact" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}QuoteRequestReference" minOccurs="0"/>
 *         &lt;element ref="{}Comments" minOccurs="0"/>
 *         &lt;element ref="{}QuoteHeaderInfo" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="award"/>
 *             &lt;enumeration value="final"/>
 *             &lt;enumeration value="accept"/>
 *             &lt;enumeration value="reject"/>
 *             &lt;enumeration value="update"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="quoteID" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="quoteDate" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="currency" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}lang use="required""/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuoteMessageHeader", propOrder = {
    "organizationID",
    "total",
    "shipTo",
    "contact",
    "quoteRequestReference",
    "comments",
    "quoteHeaderInfo",
    "extrinsic"
})
public class QuoteMessageHeader {

    @XmlElement(name = "OrganizationID", required = true)
    protected OrganizationID organizationID;
    @XmlElement(name = "Total", required = true)
    protected Total total;
    @XmlElement(name = "ShipTo")
    protected ShipTo shipTo;
    @XmlElement(name = "Contact")
    protected List<Contact> contact;
    @XmlElement(name = "QuoteRequestReference")
    protected QuoteRequestReference quoteRequestReference;
    @XmlElement(name = "Comments")
    protected Comments comments;
    @XmlElement(name = "QuoteHeaderInfo")
    protected QuoteHeaderInfo quoteHeaderInfo;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(name = "type", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String type;
    @XmlAttribute(name = "quoteID", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String quoteID;
    @XmlAttribute(name = "quoteDate", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String quoteDate;
    @XmlAttribute(name = "currency", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String currency;
    @XmlAttribute(name = "lang", namespace = "http://www.w3.org/XML/1998/namespace", required = true)
    protected String lang;

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
     * Gets the value of the total property.
     * 
     * @return
     *     possible object is
     *     {@link Total }
     *     
     */
    public Total getTotal() {
        return total;
    }

    /**
     * Sets the value of the total property.
     * 
     * @param value
     *     allowed object is
     *     {@link Total }
     *     
     */
    public void setTotal(Total value) {
        this.total = value;
    }

    /**
     * Gets the value of the shipTo property.
     * 
     * @return
     *     possible object is
     *     {@link ShipTo }
     *     
     */
    public ShipTo getShipTo() {
        return shipTo;
    }

    /**
     * Sets the value of the shipTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipTo }
     *     
     */
    public void setShipTo(ShipTo value) {
        this.shipTo = value;
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
     * Gets the value of the quoteHeaderInfo property.
     * 
     * @return
     *     possible object is
     *     {@link QuoteHeaderInfo }
     *     
     */
    public QuoteHeaderInfo getQuoteHeaderInfo() {
        return quoteHeaderInfo;
    }

    /**
     * Sets the value of the quoteHeaderInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuoteHeaderInfo }
     *     
     */
    public void setQuoteHeaderInfo(QuoteHeaderInfo value) {
        this.quoteHeaderInfo = value;
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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
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
     * Gets the value of the quoteID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuoteID() {
        return quoteID;
    }

    /**
     * Sets the value of the quoteID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuoteID(String value) {
        this.quoteID = value;
    }

    /**
     * Gets the value of the quoteDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuoteDate() {
        return quoteDate;
    }

    /**
     * Sets the value of the quoteDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuoteDate(String value) {
        this.quoteDate = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
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

}
