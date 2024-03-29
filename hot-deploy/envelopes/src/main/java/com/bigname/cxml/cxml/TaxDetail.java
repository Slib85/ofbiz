
package com.bigname.cxml.cxml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for TaxDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}TaxableAmount" minOccurs="0"/>
 *         &lt;element ref="{}TaxAmount"/>
 *         &lt;element ref="{}TaxLocation" minOccurs="0"/>
 *         &lt;element ref="{}TaxAdjustmentAmount" minOccurs="0"/>
 *         &lt;element ref="{}Description" minOccurs="0"/>
 *         &lt;element ref="{}TriangularTransactionLawReference" minOccurs="0"/>
 *         &lt;element ref="{}TaxRegime" minOccurs="0"/>
 *         &lt;element ref="{}TaxExemption" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="taxedElement" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *       &lt;attribute name="purpose" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="category" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="percentageRate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="isVatRecoverable">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="taxPointDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="paymentDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="isTriangularTransaction">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="exemptDetail">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="exempt"/>
 *             &lt;enumeration value="zeroRated"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="isWithholdingTax">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="taxRateType" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="basePercentageRate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="isIncludedInPrice">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
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
@XmlType(name = "TaxDetail", propOrder = {
    "taxableAmount",
    "taxAmount",
    "taxLocation",
    "taxAdjustmentAmount",
    "description",
    "triangularTransactionLawReference",
    "taxRegime",
    "taxExemption",
    "extrinsic"
})
public class TaxDetail {

    @XmlElement(name = "TaxableAmount")
    protected TaxableAmount taxableAmount;
    @XmlElement(name = "TaxAmount", required = true)
    protected TaxAmount taxAmount;
    @XmlElement(name = "TaxLocation")
    protected TaxLocation taxLocation;
    @XmlElement(name = "TaxAdjustmentAmount")
    protected TaxAdjustmentAmount taxAdjustmentAmount;
    @XmlElement(name = "Description")
    protected Description description;
    @XmlElement(name = "TriangularTransactionLawReference")
    protected TriangularTransactionLawReference triangularTransactionLawReference;
    @XmlElement(name = "TaxRegime")
    protected TaxRegime taxRegime;
    @XmlElement(name = "TaxExemption")
    protected TaxExemption taxExemption;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(name = "taxedElement")
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object taxedElement;
    @XmlAttribute(name = "purpose")
    @XmlSchemaType(name = "anySimpleType")
    protected String purpose;
    @XmlAttribute(name = "category", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String category;
    @XmlAttribute(name = "percentageRate")
    @XmlSchemaType(name = "anySimpleType")
    protected String percentageRate;
    @XmlAttribute(name = "isVatRecoverable")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isVatRecoverable;
    @XmlAttribute(name = "taxPointDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String taxPointDate;
    @XmlAttribute(name = "paymentDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String paymentDate;
    @XmlAttribute(name = "isTriangularTransaction")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isTriangularTransaction;
    @XmlAttribute(name = "exemptDetail")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String exemptDetail;
    @XmlAttribute(name = "isWithholdingTax")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isWithholdingTax;
    @XmlAttribute(name = "taxRateType")
    @XmlSchemaType(name = "anySimpleType")
    protected String taxRateType;
    @XmlAttribute(name = "basePercentageRate")
    @XmlSchemaType(name = "anySimpleType")
    protected String basePercentageRate;
    @XmlAttribute(name = "isIncludedInPrice")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isIncludedInPrice;

    /**
     * Gets the value of the taxableAmount property.
     * 
     * @return
     *     possible object is
     *     {@link TaxableAmount }
     *     
     */
    public TaxableAmount getTaxableAmount() {
        return taxableAmount;
    }

    /**
     * Sets the value of the taxableAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxableAmount }
     *     
     */
    public void setTaxableAmount(TaxableAmount value) {
        this.taxableAmount = value;
    }

    /**
     * Gets the value of the taxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link TaxAmount }
     *     
     */
    public TaxAmount getTaxAmount() {
        return taxAmount;
    }

    /**
     * Sets the value of the taxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxAmount }
     *     
     */
    public void setTaxAmount(TaxAmount value) {
        this.taxAmount = value;
    }

    /**
     * Gets the value of the taxLocation property.
     * 
     * @return
     *     possible object is
     *     {@link TaxLocation }
     *     
     */
    public TaxLocation getTaxLocation() {
        return taxLocation;
    }

    /**
     * Sets the value of the taxLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxLocation }
     *     
     */
    public void setTaxLocation(TaxLocation value) {
        this.taxLocation = value;
    }

    /**
     * Gets the value of the taxAdjustmentAmount property.
     * 
     * @return
     *     possible object is
     *     {@link TaxAdjustmentAmount }
     *     
     */
    public TaxAdjustmentAmount getTaxAdjustmentAmount() {
        return taxAdjustmentAmount;
    }

    /**
     * Sets the value of the taxAdjustmentAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxAdjustmentAmount }
     *     
     */
    public void setTaxAdjustmentAmount(TaxAdjustmentAmount value) {
        this.taxAdjustmentAmount = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link Description }
     *     
     */
    public Description getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link Description }
     *     
     */
    public void setDescription(Description value) {
        this.description = value;
    }

    /**
     * Gets the value of the triangularTransactionLawReference property.
     * 
     * @return
     *     possible object is
     *     {@link TriangularTransactionLawReference }
     *     
     */
    public TriangularTransactionLawReference getTriangularTransactionLawReference() {
        return triangularTransactionLawReference;
    }

    /**
     * Sets the value of the triangularTransactionLawReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link TriangularTransactionLawReference }
     *     
     */
    public void setTriangularTransactionLawReference(TriangularTransactionLawReference value) {
        this.triangularTransactionLawReference = value;
    }

    /**
     * Gets the value of the taxRegime property.
     * 
     * @return
     *     possible object is
     *     {@link TaxRegime }
     *     
     */
    public TaxRegime getTaxRegime() {
        return taxRegime;
    }

    /**
     * Sets the value of the taxRegime property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxRegime }
     *     
     */
    public void setTaxRegime(TaxRegime value) {
        this.taxRegime = value;
    }

    /**
     * Gets the value of the taxExemption property.
     * 
     * @return
     *     possible object is
     *     {@link TaxExemption }
     *     
     */
    public TaxExemption getTaxExemption() {
        return taxExemption;
    }

    /**
     * Sets the value of the taxExemption property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxExemption }
     *     
     */
    public void setTaxExemption(TaxExemption value) {
        this.taxExemption = value;
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
     * Gets the value of the taxedElement property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getTaxedElement() {
        return taxedElement;
    }

    /**
     * Sets the value of the taxedElement property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setTaxedElement(Object value) {
        this.taxedElement = value;
    }

    /**
     * Gets the value of the purpose property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Sets the value of the purpose property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurpose(String value) {
        this.purpose = value;
    }

    /**
     * Gets the value of the category property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategory(String value) {
        this.category = value;
    }

    /**
     * Gets the value of the percentageRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPercentageRate() {
        return percentageRate;
    }

    /**
     * Sets the value of the percentageRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPercentageRate(String value) {
        this.percentageRate = value;
    }

    /**
     * Gets the value of the isVatRecoverable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsVatRecoverable() {
        return isVatRecoverable;
    }

    /**
     * Sets the value of the isVatRecoverable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsVatRecoverable(String value) {
        this.isVatRecoverable = value;
    }

    /**
     * Gets the value of the taxPointDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxPointDate() {
        return taxPointDate;
    }

    /**
     * Sets the value of the taxPointDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxPointDate(String value) {
        this.taxPointDate = value;
    }

    /**
     * Gets the value of the paymentDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentDate() {
        return paymentDate;
    }

    /**
     * Sets the value of the paymentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentDate(String value) {
        this.paymentDate = value;
    }

    /**
     * Gets the value of the isTriangularTransaction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsTriangularTransaction() {
        return isTriangularTransaction;
    }

    /**
     * Sets the value of the isTriangularTransaction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsTriangularTransaction(String value) {
        this.isTriangularTransaction = value;
    }

    /**
     * Gets the value of the exemptDetail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExemptDetail() {
        return exemptDetail;
    }

    /**
     * Sets the value of the exemptDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExemptDetail(String value) {
        this.exemptDetail = value;
    }

    /**
     * Gets the value of the isWithholdingTax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsWithholdingTax() {
        return isWithholdingTax;
    }

    /**
     * Sets the value of the isWithholdingTax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsWithholdingTax(String value) {
        this.isWithholdingTax = value;
    }

    /**
     * Gets the value of the taxRateType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxRateType() {
        return taxRateType;
    }

    /**
     * Sets the value of the taxRateType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxRateType(String value) {
        this.taxRateType = value;
    }

    /**
     * Gets the value of the basePercentageRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBasePercentageRate() {
        return basePercentageRate;
    }

    /**
     * Sets the value of the basePercentageRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBasePercentageRate(String value) {
        this.basePercentageRate = value;
    }

    /**
     * Gets the value of the isIncludedInPrice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsIncludedInPrice() {
        return isIncludedInPrice;
    }

    /**
     * Sets the value of the isIncludedInPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsIncludedInPrice(String value) {
        this.isIncludedInPrice = value;
    }

}
