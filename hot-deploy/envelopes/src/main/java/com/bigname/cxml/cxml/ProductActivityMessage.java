
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
 * <p>Java class for ProductActivityMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProductActivityMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ProductActivityHeader"/>
 *         &lt;element ref="{}ProductActivityDetails" maxOccurs="unbounded"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="subcontractingIndicator">
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
@XmlType(name = "ProductActivityMessage", propOrder = {
    "productActivityHeader",
    "productActivityDetails",
    "extrinsic"
})
public class ProductActivityMessage {

    @XmlElement(name = "ProductActivityHeader", required = true)
    protected ProductActivityHeader productActivityHeader;
    @XmlElement(name = "ProductActivityDetails", required = true)
    protected List<ProductActivityDetails> productActivityDetails;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(name = "subcontractingIndicator")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String subcontractingIndicator;

    /**
     * Gets the value of the productActivityHeader property.
     * 
     * @return
     *     possible object is
     *     {@link ProductActivityHeader }
     *     
     */
    public ProductActivityHeader getProductActivityHeader() {
        return productActivityHeader;
    }

    /**
     * Sets the value of the productActivityHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductActivityHeader }
     *     
     */
    public void setProductActivityHeader(ProductActivityHeader value) {
        this.productActivityHeader = value;
    }

    /**
     * Gets the value of the productActivityDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the productActivityDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProductActivityDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductActivityDetails }
     * 
     * 
     */
    public List<ProductActivityDetails> getProductActivityDetails() {
        if (productActivityDetails == null) {
            productActivityDetails = new ArrayList<ProductActivityDetails>();
        }
        return this.productActivityDetails;
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
     * Gets the value of the subcontractingIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubcontractingIndicator() {
        return subcontractingIndicator;
    }

    /**
     * Sets the value of the subcontractingIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubcontractingIndicator(String value) {
        this.subcontractingIndicator = value;
    }

}
