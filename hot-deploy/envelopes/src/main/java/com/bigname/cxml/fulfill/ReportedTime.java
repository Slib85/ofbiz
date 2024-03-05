
package com.bigname.cxml.fulfill;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReportedTime complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReportedTime">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Period"/>
 *         &lt;element ref="{}TimeCardTimeInterval" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Expense" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReportedTime", propOrder = {
    "period",
    "timeCardTimeInterval",
    "expense"
})
public class ReportedTime {

    @XmlElement(name = "Period", required = true)
    protected Period period;
    @XmlElement(name = "TimeCardTimeInterval")
    protected List<TimeCardTimeInterval> timeCardTimeInterval;
    @XmlElement(name = "Expense")
    protected List<Expense> expense;

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
     * Gets the value of the timeCardTimeInterval property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the timeCardTimeInterval property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTimeCardTimeInterval().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TimeCardTimeInterval }
     * 
     * 
     */
    public List<TimeCardTimeInterval> getTimeCardTimeInterval() {
        if (timeCardTimeInterval == null) {
            timeCardTimeInterval = new ArrayList<TimeCardTimeInterval>();
        }
        return this.timeCardTimeInterval;
    }

    /**
     * Gets the value of the expense property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the expense property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExpense().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Expense }
     * 
     * 
     */
    public List<Expense> getExpense() {
        if (expense == null) {
            expense = new ArrayList<Expense>();
        }
        return this.expense;
    }

}
