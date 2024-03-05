
package com.bigname.cxml.fulfill;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProductReplenishmentMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProductReplenishmentMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ProductReplenishmentHeader"/>
 *         &lt;element ref="{}ProductReplenishmentDetails" maxOccurs="unbounded"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductReplenishmentMessage", propOrder = {
    "productReplenishmentHeader",
    "productReplenishmentDetails",
    "extrinsic"
})
public class ProductReplenishmentMessage {

    @XmlElement(name = "ProductReplenishmentHeader", required = true)
    protected ProductReplenishmentHeader productReplenishmentHeader;
    @XmlElement(name = "ProductReplenishmentDetails", required = true)
    protected List<ProductReplenishmentDetails> productReplenishmentDetails;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;

    /**
     * Gets the value of the productReplenishmentHeader property.
     * 
     * @return
     *     possible object is
     *     {@link ProductReplenishmentHeader }
     *     
     */
    public ProductReplenishmentHeader getProductReplenishmentHeader() {
        return productReplenishmentHeader;
    }

    /**
     * Sets the value of the productReplenishmentHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductReplenishmentHeader }
     *     
     */
    public void setProductReplenishmentHeader(ProductReplenishmentHeader value) {
        this.productReplenishmentHeader = value;
    }

    /**
     * Gets the value of the productReplenishmentDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the productReplenishmentDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProductReplenishmentDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductReplenishmentDetails }
     * 
     * 
     */
    public List<ProductReplenishmentDetails> getProductReplenishmentDetails() {
        if (productReplenishmentDetails == null) {
            productReplenishmentDetails = new ArrayList<ProductReplenishmentDetails>();
        }
        return this.productReplenishmentDetails;
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

}
