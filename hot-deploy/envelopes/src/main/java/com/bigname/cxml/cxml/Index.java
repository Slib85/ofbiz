
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
 * <p>Java class for Index complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Index">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}SupplierID" maxOccurs="unbounded"/>
 *         &lt;element ref="{}Comments" minOccurs="0"/>
 *         &lt;element ref="{}SearchGroup" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}IndexItem" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="loadmode">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="Full"/>
 *             &lt;enumeration value="Incremental"/>
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
@XmlType(name = "Index", propOrder = {
    "supplierID",
    "comments",
    "searchGroup",
    "indexItem"
})
public class Index {

    @XmlElement(name = "SupplierID", required = true)
    protected List<SupplierID> supplierID;
    @XmlElement(name = "Comments")
    protected Comments comments;
    @XmlElement(name = "SearchGroup")
    protected List<SearchGroup> searchGroup;
    @XmlElement(name = "IndexItem", required = true)
    protected List<IndexItem> indexItem;
    @XmlAttribute(name = "loadmode")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String loadmode;

    /**
     * Gets the value of the supplierID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supplierID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupplierID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SupplierID }
     * 
     * 
     */
    public List<SupplierID> getSupplierID() {
        if (supplierID == null) {
            supplierID = new ArrayList<SupplierID>();
        }
        return this.supplierID;
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
     * Gets the value of the searchGroup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the searchGroup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSearchGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SearchGroup }
     * 
     * 
     */
    public List<SearchGroup> getSearchGroup() {
        if (searchGroup == null) {
            searchGroup = new ArrayList<SearchGroup>();
        }
        return this.searchGroup;
    }

    /**
     * Gets the value of the indexItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the indexItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndexItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IndexItem }
     * 
     * 
     */
    public List<IndexItem> getIndexItem() {
        if (indexItem == null) {
            indexItem = new ArrayList<IndexItem>();
        }
        return this.indexItem;
    }

    /**
     * Gets the value of the loadmode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoadmode() {
        return loadmode;
    }

    /**
     * Sets the value of the loadmode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoadmode(String value) {
        this.loadmode = value;
    }

}
