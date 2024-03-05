
package com.bigname.cxml.cxml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ContractItemIn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContractItemIn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}MaxAmount" minOccurs="0"/>
 *         &lt;element ref="{}MinAmount" minOccurs="0"/>
 *         &lt;element ref="{}MaxReleaseAmount" minOccurs="0"/>
 *         &lt;element ref="{}MinReleaseAmount" minOccurs="0"/>
 *         &lt;element ref="{}MaxQuantity" minOccurs="0"/>
 *         &lt;element ref="{}MinQuantity" minOccurs="0"/>
 *         &lt;element ref="{}MaxReleaseQuantity" minOccurs="0"/>
 *         &lt;element ref="{}MinReleaseQuantity" minOccurs="0"/>
 *         &lt;element ref="{}TermsOfDelivery" minOccurs="0"/>
 *         &lt;element ref="{}ItemIn"/>
 *         &lt;element ref="{}ReferenceDocumentInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Alternative" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="operation">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="new"/>
 *             &lt;enumeration value="delete"/>
 *             &lt;enumeration value="update"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="itemType">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="item"/>
 *             &lt;enumeration value="lean"/>
 *             &lt;enumeration value="composite"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="serviceLineType">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="blanket"/>
 *             &lt;enumeration value="standard"/>
 *             &lt;enumeration value="contingency"/>
 *             &lt;enumeration value="information"/>
 *             &lt;enumeration value="openquantity"/>
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
@XmlType(name = "ContractItemIn", propOrder = {
    "maxAmount",
    "minAmount",
    "maxReleaseAmount",
    "minReleaseAmount",
    "maxQuantity",
    "minQuantity",
    "maxReleaseQuantity",
    "minReleaseQuantity",
    "termsOfDelivery",
    "itemIn",
    "referenceDocumentInfo",
    "alternative"
})
public class ContractItemIn {

    @XmlElement(name = "MaxAmount")
    protected MaxAmount maxAmount;
    @XmlElement(name = "MinAmount")
    protected MinAmount minAmount;
    @XmlElement(name = "MaxReleaseAmount")
    protected MaxReleaseAmount maxReleaseAmount;
    @XmlElement(name = "MinReleaseAmount")
    protected MinReleaseAmount minReleaseAmount;
    @XmlElement(name = "MaxQuantity")
    protected MaxQuantity maxQuantity;
    @XmlElement(name = "MinQuantity")
    protected MinQuantity minQuantity;
    @XmlElement(name = "MaxReleaseQuantity")
    protected MaxReleaseQuantity maxReleaseQuantity;
    @XmlElement(name = "MinReleaseQuantity")
    protected MinReleaseQuantity minReleaseQuantity;
    @XmlElement(name = "TermsOfDelivery")
    protected TermsOfDelivery termsOfDelivery;
    @XmlElement(name = "ItemIn", required = true)
    protected ItemIn itemIn;
    @XmlElement(name = "ReferenceDocumentInfo")
    protected List<ReferenceDocumentInfo> referenceDocumentInfo;
    @XmlElement(name = "Alternative")
    protected Alternative alternative;
    @XmlAttribute(name = "operation")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String operation;
    @XmlAttribute(name = "itemType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String itemType;
    @XmlAttribute(name = "serviceLineType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String serviceLineType;

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
     * Gets the value of the maxQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link MaxQuantity }
     *     
     */
    public MaxQuantity getMaxQuantity() {
        return maxQuantity;
    }

    /**
     * Sets the value of the maxQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaxQuantity }
     *     
     */
    public void setMaxQuantity(MaxQuantity value) {
        this.maxQuantity = value;
    }

    /**
     * Gets the value of the minQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link MinQuantity }
     *     
     */
    public MinQuantity getMinQuantity() {
        return minQuantity;
    }

    /**
     * Sets the value of the minQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link MinQuantity }
     *     
     */
    public void setMinQuantity(MinQuantity value) {
        this.minQuantity = value;
    }

    /**
     * Gets the value of the maxReleaseQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link MaxReleaseQuantity }
     *     
     */
    public MaxReleaseQuantity getMaxReleaseQuantity() {
        return maxReleaseQuantity;
    }

    /**
     * Sets the value of the maxReleaseQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaxReleaseQuantity }
     *     
     */
    public void setMaxReleaseQuantity(MaxReleaseQuantity value) {
        this.maxReleaseQuantity = value;
    }

    /**
     * Gets the value of the minReleaseQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link MinReleaseQuantity }
     *     
     */
    public MinReleaseQuantity getMinReleaseQuantity() {
        return minReleaseQuantity;
    }

    /**
     * Sets the value of the minReleaseQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link MinReleaseQuantity }
     *     
     */
    public void setMinReleaseQuantity(MinReleaseQuantity value) {
        this.minReleaseQuantity = value;
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
     * Gets the value of the itemIn property.
     * 
     * @return
     *     possible object is
     *     {@link ItemIn }
     *     
     */
    public ItemIn getItemIn() {
        return itemIn;
    }

    /**
     * Sets the value of the itemIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemIn }
     *     
     */
    public void setItemIn(ItemIn value) {
        this.itemIn = value;
    }

    /**
     * Gets the value of the referenceDocumentInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the referenceDocumentInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferenceDocumentInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReferenceDocumentInfo }
     * 
     * 
     */
    public List<ReferenceDocumentInfo> getReferenceDocumentInfo() {
        if (referenceDocumentInfo == null) {
            referenceDocumentInfo = new ArrayList<ReferenceDocumentInfo>();
        }
        return this.referenceDocumentInfo;
    }

    /**
     * Gets the value of the alternative property.
     * 
     * @return
     *     possible object is
     *     {@link Alternative }
     *     
     */
    public Alternative getAlternative() {
        return alternative;
    }

    /**
     * Sets the value of the alternative property.
     * 
     * @param value
     *     allowed object is
     *     {@link Alternative }
     *     
     */
    public void setAlternative(Alternative value) {
        this.alternative = value;
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
        return operation;
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

    /**
     * Gets the value of the itemType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemType() {
        return itemType;
    }

    /**
     * Sets the value of the itemType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemType(String value) {
        this.itemType = value;
    }

    /**
     * Gets the value of the serviceLineType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceLineType() {
        return serviceLineType;
    }

    /**
     * Sets the value of the serviceLineType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceLineType(String value) {
        this.serviceLineType = value;
    }

}
