
package com.bigname.cxml.fulfill;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ShipNoticePortion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipNoticePortion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}OrderReference"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{}MasterAgreementReference"/>
 *           &lt;element ref="{}MasterAgreementIDInfo"/>
 *         &lt;/choice>
 *         &lt;element ref="{}Contact" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Comments" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}ShipNoticeItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}ReferenceDocumentInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipNoticePortion", propOrder = {
    "orderReference",
    "masterAgreementReference",
    "masterAgreementIDInfo",
    "contact",
    "comments",
    "extrinsic",
    "shipNoticeItem",
    "referenceDocumentInfo"
})
public class ShipNoticePortion {

    @XmlElement(name = "OrderReference", required = true)
    protected OrderReference orderReference;
    @XmlElement(name = "MasterAgreementReference")
    protected MasterAgreementReference masterAgreementReference;
    @XmlElement(name = "MasterAgreementIDInfo")
    protected MasterAgreementIDInfo masterAgreementIDInfo;
    @XmlElement(name = "Contact")
    protected List<Contact> contact;
    @XmlElement(name = "Comments")
    protected List<Comments> comments;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlElement(name = "ShipNoticeItem")
    protected List<ShipNoticeItem> shipNoticeItem;
    @XmlElement(name = "ReferenceDocumentInfo")
    protected List<ReferenceDocumentInfo> referenceDocumentInfo;

    /**
     * Gets the value of the orderReference property.
     * 
     * @return
     *     possible object is
     *     {@link OrderReference }
     *     
     */
    public OrderReference getOrderReference() {
        return orderReference;
    }

    /**
     * Sets the value of the orderReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderReference }
     *     
     */
    public void setOrderReference(OrderReference value) {
        this.orderReference = value;
    }

    /**
     * Gets the value of the masterAgreementReference property.
     * 
     * @return
     *     possible object is
     *     {@link MasterAgreementReference }
     *     
     */
    public MasterAgreementReference getMasterAgreementReference() {
        return masterAgreementReference;
    }

    /**
     * Sets the value of the masterAgreementReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link MasterAgreementReference }
     *     
     */
    public void setMasterAgreementReference(MasterAgreementReference value) {
        this.masterAgreementReference = value;
    }

    /**
     * Gets the value of the masterAgreementIDInfo property.
     * 
     * @return
     *     possible object is
     *     {@link MasterAgreementIDInfo }
     *     
     */
    public MasterAgreementIDInfo getMasterAgreementIDInfo() {
        return masterAgreementIDInfo;
    }

    /**
     * Sets the value of the masterAgreementIDInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link MasterAgreementIDInfo }
     *     
     */
    public void setMasterAgreementIDInfo(MasterAgreementIDInfo value) {
        this.masterAgreementIDInfo = value;
    }

    /**
     * Gets the value of the contact property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contact property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContact().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Contact }
     * 
     * 
     */
    public List<Contact> getContact() {
        if (contact == null) {
            contact = new ArrayList<Contact>();
        }
        return this.contact;
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
     * Gets the value of the shipNoticeItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the shipNoticeItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getShipNoticeItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ShipNoticeItem }
     * 
     * 
     */
    public List<ShipNoticeItem> getShipNoticeItem() {
        if (shipNoticeItem == null) {
            shipNoticeItem = new ArrayList<ShipNoticeItem>();
        }
        return this.shipNoticeItem;
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

}
