
package com.bigname.cxml.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceDetailLineShipping complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceDetailLineShipping">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}InvoiceDetailShipping"/>
 *         &lt;element ref="{}Money"/>
 *         &lt;element ref="{}Distribution" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceDetailLineShipping", propOrder = {
    "invoiceDetailShipping",
    "money",
    "distribution"
})
public class InvoiceDetailLineShipping {

    @XmlElement(name = "InvoiceDetailShipping", required = true)
    protected InvoiceDetailShipping invoiceDetailShipping;
    @XmlElement(name = "Money", required = true)
    protected Money money;
    @XmlElement(name = "Distribution")
    protected List<Distribution> distribution;

    /**
     * Gets the value of the invoiceDetailShipping property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceDetailShipping }
     *     
     */
    public InvoiceDetailShipping getInvoiceDetailShipping() {
        return invoiceDetailShipping;
    }

    /**
     * Sets the value of the invoiceDetailShipping property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceDetailShipping }
     *     
     */
    public void setInvoiceDetailShipping(InvoiceDetailShipping value) {
        this.invoiceDetailShipping = value;
    }

    /**
     * Gets the value of the money property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getMoney() {
        return money;
    }

    /**
     * Sets the value of the money property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setMoney(Money value) {
        this.money = value;
    }

    /**
     * Gets the value of the distribution property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the distribution property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDistribution().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Distribution }
     * 
     * 
     */
    public List<Distribution> getDistribution() {
        if (distribution == null) {
            distribution = new ArrayList<Distribution>();
        }
        return this.distribution;
    }

}
