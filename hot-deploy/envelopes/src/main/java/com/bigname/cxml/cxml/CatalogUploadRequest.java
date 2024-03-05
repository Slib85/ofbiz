
package com.bigname.cxml.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for CatalogUploadRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CatalogUploadRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}CatalogName"/>
 *         &lt;element ref="{}Description"/>
 *         &lt;element ref="{}Attachment"/>
 *         &lt;element ref="{}Commodities" minOccurs="0"/>
 *         &lt;element ref="{}AutoPublish" minOccurs="0"/>
 *         &lt;element ref="{}Notification"/>
 *       &lt;/sequence>
 *       &lt;attribute name="operation" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="new"/>
 *             &lt;enumeration value="update"/>
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
@XmlType(name = "CatalogUploadRequest", propOrder = {
    "catalogName",
    "description",
    "attachment",
    "commodities",
    "autoPublish",
    "notification"
})
public class CatalogUploadRequest {

    @XmlElement(name = "CatalogName", required = true)
    protected CatalogName catalogName;
    @XmlElement(name = "Description", required = true)
    protected Description description;
    @XmlElement(name = "Attachment", required = true)
    protected Attachment attachment;
    @XmlElement(name = "Commodities")
    protected Commodities commodities;
    @XmlElement(name = "AutoPublish")
    protected AutoPublish autoPublish;
    @XmlElement(name = "Notification", required = true)
    protected Notification notification;
    @XmlAttribute(name = "operation", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String operation;

    /**
     * Gets the value of the catalogName property.
     * 
     * @return
     *     possible object is
     *     {@link CatalogName }
     *     
     */
    public CatalogName getCatalogName() {
        return catalogName;
    }

    /**
     * Sets the value of the catalogName property.
     * 
     * @param value
     *     allowed object is
     *     {@link CatalogName }
     *     
     */
    public void setCatalogName(CatalogName value) {
        this.catalogName = value;
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
     * Gets the value of the attachment property.
     * 
     * @return
     *     possible object is
     *     {@link Attachment }
     *     
     */
    public Attachment getAttachment() {
        return attachment;
    }

    /**
     * Sets the value of the attachment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Attachment }
     *     
     */
    public void setAttachment(Attachment value) {
        this.attachment = value;
    }

    /**
     * Gets the value of the commodities property.
     * 
     * @return
     *     possible object is
     *     {@link Commodities }
     *     
     */
    public Commodities getCommodities() {
        return commodities;
    }

    /**
     * Sets the value of the commodities property.
     * 
     * @param value
     *     allowed object is
     *     {@link Commodities }
     *     
     */
    public void setCommodities(Commodities value) {
        this.commodities = value;
    }

    /**
     * Gets the value of the autoPublish property.
     * 
     * @return
     *     possible object is
     *     {@link AutoPublish }
     *     
     */
    public AutoPublish getAutoPublish() {
        return autoPublish;
    }

    /**
     * Sets the value of the autoPublish property.
     * 
     * @param value
     *     allowed object is
     *     {@link AutoPublish }
     *     
     */
    public void setAutoPublish(AutoPublish value) {
        this.autoPublish = value;
    }

    /**
     * Gets the value of the notification property.
     * 
     * @return
     *     possible object is
     *     {@link Notification }
     *     
     */
    public Notification getNotification() {
        return notification;
    }

    /**
     * Sets the value of the notification property.
     * 
     * @param value
     *     allowed object is
     *     {@link Notification }
     *     
     */
    public void setNotification(Notification value) {
        this.notification = value;
    }

    /**
     * Gets the value of the operation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Sets the value of the operation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperation(String value) {
        this.operation = value;
    }

}
