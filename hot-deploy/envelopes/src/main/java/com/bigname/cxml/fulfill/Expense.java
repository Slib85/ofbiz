
package com.bigname.cxml.fulfill;

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
 * <p>Java class for Expense complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Expense">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ExpenseAmount"/>
 *         &lt;element ref="{}Comments" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="expenseDate" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="expenseType" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="isNonBillable">
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
@XmlType(name = "Expense", propOrder = {
    "expenseAmount",
    "comments",
    "extrinsic"
})
public class Expense {

    @XmlElement(name = "ExpenseAmount", required = true)
    protected ExpenseAmount expenseAmount;
    @XmlElement(name = "Comments")
    protected List<Comments> comments;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(name = "expenseDate", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String expenseDate;
    @XmlAttribute(name = "expenseType", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String expenseType;
    @XmlAttribute(name = "isNonBillable")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isNonBillable;

    /**
     * Gets the value of the expenseAmount property.
     * 
     * @return
     *     possible object is
     *     {@link ExpenseAmount }
     *     
     */
    public ExpenseAmount getExpenseAmount() {
        return expenseAmount;
    }

    /**
     * Sets the value of the expenseAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpenseAmount }
     *     
     */
    public void setExpenseAmount(ExpenseAmount value) {
        this.expenseAmount = value;
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
     * Gets the value of the expenseDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpenseDate() {
        return expenseDate;
    }

    /**
     * Sets the value of the expenseDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpenseDate(String value) {
        this.expenseDate = value;
    }

    /**
     * Gets the value of the expenseType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpenseType() {
        return expenseType;
    }

    /**
     * Sets the value of the expenseType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpenseType(String value) {
        this.expenseType = value;
    }

    /**
     * Gets the value of the isNonBillable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsNonBillable() {
        return isNonBillable;
    }

    /**
     * Sets the value of the isNonBillable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsNonBillable(String value) {
        this.isNonBillable = value;
    }

}
