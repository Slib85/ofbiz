
package com.bigname.cxml.cxml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesReport complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesReport">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Period"/>
 *         &lt;element ref="{}SalesQuantity"/>
 *         &lt;element ref="{}ReturnQuantity" minOccurs="0"/>
 *         &lt;element ref="{}Total" minOccurs="0"/>
 *         &lt;element ref="{}PromotionVariantID" minOccurs="0"/>
 *         &lt;element ref="{}Comments" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="salesDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="lineNumber" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesReport", propOrder = {
    "period",
    "salesQuantity",
    "returnQuantity",
    "total",
    "promotionVariantID",
    "comments"
})
public class SalesReport {

    @XmlElement(name = "Period", required = true)
    protected Period period;
    @XmlElement(name = "SalesQuantity", required = true)
    protected SalesQuantity salesQuantity;
    @XmlElement(name = "ReturnQuantity")
    protected ReturnQuantity returnQuantity;
    @XmlElement(name = "Total")
    protected Total total;
    @XmlElement(name = "PromotionVariantID")
    protected PromotionVariantID promotionVariantID;
    @XmlElement(name = "Comments")
    protected List<Comments> comments;
    @XmlAttribute(name = "salesDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String salesDate;
    @XmlAttribute(name = "lineNumber", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String lineNumber;

    /**
     * Gets the value of the period property.
     * 
     * @return
     *     possible object is
     *     {@link Period }
     *     
     */
    public Period getPeriod() {
        return period;
    }

    /**
     * Sets the value of the period property.
     * 
     * @param value
     *     allowed object is
     *     {@link Period }
     *     
     */
    public void setPeriod(Period value) {
        this.period = value;
    }

    /**
     * Gets the value of the salesQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link SalesQuantity }
     *     
     */
    public SalesQuantity getSalesQuantity() {
        return salesQuantity;
    }

    /**
     * Sets the value of the salesQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesQuantity }
     *     
     */
    public void setSalesQuantity(SalesQuantity value) {
        this.salesQuantity = value;
    }

    /**
     * Gets the value of the returnQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnQuantity }
     *     
     */
    public ReturnQuantity getReturnQuantity() {
        return returnQuantity;
    }

    /**
     * Sets the value of the returnQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnQuantity }
     *     
     */
    public void setReturnQuantity(ReturnQuantity value) {
        this.returnQuantity = value;
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
     * Gets the value of the promotionVariantID property.
     * 
     * @return
     *     possible object is
     *     {@link PromotionVariantID }
     *     
     */
    public PromotionVariantID getPromotionVariantID() {
        return promotionVariantID;
    }

    /**
     * Sets the value of the promotionVariantID property.
     * 
     * @param value
     *     allowed object is
     *     {@link PromotionVariantID }
     *     
     */
    public void setPromotionVariantID(PromotionVariantID value) {
        this.promotionVariantID = value;
    }

    /**
     * Gets the value of the comments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the comments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Comments }
     * 
     * 
     */
    public List<Comments> getComments() {
        if (comments == null) {
            comments = new ArrayList<Comments>();
        }
        return this.comments;
    }

    /**
     * Gets the value of the salesDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesDate() {
        return salesDate;
    }

    /**
     * Sets the value of the salesDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesDate(String value) {
        this.salesDate = value;
    }

    /**
     * Gets the value of the lineNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLineNumber() {
        return lineNumber;
    }

    /**
     * Sets the value of the lineNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineNumber(String value) {
        this.lineNumber = value;
    }

}
