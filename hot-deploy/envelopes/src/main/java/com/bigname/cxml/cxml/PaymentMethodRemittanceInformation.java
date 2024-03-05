
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
 * <p>Java class for PaymentMethodRemittanceInformation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentMethodRemittanceInformation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Contact"/>
 *         &lt;element ref="{}PaymentMethod" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}PaymentMethodPartner" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="isDefault">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="preferredPaymentMethod">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="ach"/>
 *             &lt;enumeration value="cash"/>
 *             &lt;enumeration value="wire"/>
 *             &lt;enumeration value="check"/>
 *             &lt;enumeration value="draft"/>
 *             &lt;enumeration value="other"/>
 *             &lt;enumeration value="debitCard"/>
 *             &lt;enumeration value="creditCard"/>
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
@XmlType(name = "PaymentMethodRemittanceInformation", propOrder = {
    "contact",
    "paymentMethod",
    "paymentMethodPartner"
})
public class PaymentMethodRemittanceInformation {

    @XmlElement(name = "Contact", required = true)
    protected Contact contact;
    @XmlElement(name = "PaymentMethod")
    protected List<PaymentMethod> paymentMethod;
    @XmlElement(name = "PaymentMethodPartner")
    protected List<PaymentMethodPartner> paymentMethodPartner;
    @XmlAttribute(name = "isDefault")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isDefault;
    @XmlAttribute(name = "preferredPaymentMethod")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String preferredPaymentMethod;

    /**
     * Gets the value of the contact property.
     * 
     * @return
     *     possible object is
     *     {@link Contact }
     *     
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Sets the value of the contact property.
     * 
     * @param value
     *     allowed object is
     *     {@link Contact }
     *     
     */
    public void setContact(Contact value) {
        this.contact = value;
    }

    /**
     * Gets the value of the paymentMethod property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentMethod property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentMethod().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentMethod }
     * 
     * 
     */
    public List<PaymentMethod> getPaymentMethod() {
        if (paymentMethod == null) {
            paymentMethod = new ArrayList<PaymentMethod>();
        }
        return this.paymentMethod;
    }

    /**
     * Gets the value of the paymentMethodPartner property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentMethodPartner property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentMethodPartner().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentMethodPartner }
     * 
     * 
     */
    public List<PaymentMethodPartner> getPaymentMethodPartner() {
        if (paymentMethodPartner == null) {
            paymentMethodPartner = new ArrayList<PaymentMethodPartner>();
        }
        return this.paymentMethodPartner;
    }

    /**
     * Gets the value of the isDefault property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsDefault() {
        return isDefault;
    }

    /**
     * Sets the value of the isDefault property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsDefault(String value) {
        this.isDefault = value;
    }

    /**
     * Gets the value of the preferredPaymentMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferredPaymentMethod() {
        return preferredPaymentMethod;
    }

    /**
     * Sets the value of the preferredPaymentMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferredPaymentMethod(String value) {
        this.preferredPaymentMethod = value;
    }

}
