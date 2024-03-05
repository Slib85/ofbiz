
package com.bigname.cxml.fulfill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for Request complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Request">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element ref="{}ConfirmationRequest"/>
 *           &lt;element ref="{}ShipNoticeRequest"/>
 *           &lt;element ref="{}ServiceEntryRequest"/>
 *           &lt;element ref="{}CopyRequest"/>
 *           &lt;element ref="{}SalesOrderRequest"/>
 *           &lt;element ref="{}TimeCardInfoRequest"/>
 *           &lt;element ref="{}ReceiptRequest"/>
 *           &lt;element ref="{}OrderStatusRequest"/>
 *           &lt;element ref="{}TimeCardRequest"/>
 *           &lt;element ref="{}ComponentConsumptionRequest"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="deploymentMode" default="production">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="test"/>
 *             &lt;enumeration value="production"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Request", propOrder = {
    "confirmationRequest",
    "shipNoticeRequest",
    "serviceEntryRequest",
    "copyRequest",
    "salesOrderRequest",
    "timeCardInfoRequest",
    "receiptRequest",
    "orderStatusRequest",
    "timeCardRequest",
    "componentConsumptionRequest"
})
public class Request {

    @XmlElement(name = "ConfirmationRequest")
    protected ConfirmationRequest confirmationRequest;
    @XmlElement(name = "ShipNoticeRequest")
    protected ShipNoticeRequest shipNoticeRequest;
    @XmlElement(name = "ServiceEntryRequest")
    protected ServiceEntryRequest serviceEntryRequest;
    @XmlElement(name = "CopyRequest")
    protected CopyRequest copyRequest;
    @XmlElement(name = "SalesOrderRequest")
    protected SalesOrderRequest salesOrderRequest;
    @XmlElement(name = "TimeCardInfoRequest")
    protected TimeCardInfoRequest timeCardInfoRequest;
    @XmlElement(name = "ReceiptRequest")
    protected ReceiptRequest receiptRequest;
    @XmlElement(name = "OrderStatusRequest")
    protected OrderStatusRequest orderStatusRequest;
    @XmlElement(name = "TimeCardRequest")
    protected TimeCardRequest timeCardRequest;
    @XmlElement(name = "ComponentConsumptionRequest")
    protected ComponentConsumptionRequest componentConsumptionRequest;
    @XmlAttribute(name = "deploymentMode")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String deploymentMode;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the confirmationRequest property.
     * 
     * @return
     *     possible object is
     *     {@link ConfirmationRequest }
     *     
     */
    public ConfirmationRequest getConfirmationRequest() {
        return confirmationRequest;
    }

    /**
     * Sets the value of the confirmationRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConfirmationRequest }
     *     
     */
    public void setConfirmationRequest(ConfirmationRequest value) {
        this.confirmationRequest = value;
    }

    /**
     * Gets the value of the shipNoticeRequest property.
     * 
     * @return
     *     possible object is
     *     {@link ShipNoticeRequest }
     *     
     */
    public ShipNoticeRequest getShipNoticeRequest() {
        return shipNoticeRequest;
    }

    /**
     * Sets the value of the shipNoticeRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipNoticeRequest }
     *     
     */
    public void setShipNoticeRequest(ShipNoticeRequest value) {
        this.shipNoticeRequest = value;
    }

    /**
     * Gets the value of the serviceEntryRequest property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEntryRequest }
     *     
     */
    public ServiceEntryRequest getServiceEntryRequest() {
        return serviceEntryRequest;
    }

    /**
     * Sets the value of the serviceEntryRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEntryRequest }
     *     
     */
    public void setServiceEntryRequest(ServiceEntryRequest value) {
        this.serviceEntryRequest = value;
    }

    /**
     * Gets the value of the copyRequest property.
     * 
     * @return
     *     possible object is
     *     {@link CopyRequest }
     *     
     */
    public CopyRequest getCopyRequest() {
        return copyRequest;
    }

    /**
     * Sets the value of the copyRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link CopyRequest }
     *     
     */
    public void setCopyRequest(CopyRequest value) {
        this.copyRequest = value;
    }

    /**
     * Gets the value of the salesOrderRequest property.
     * 
     * @return
     *     possible object is
     *     {@link SalesOrderRequest }
     *     
     */
    public SalesOrderRequest getSalesOrderRequest() {
        return salesOrderRequest;
    }

    /**
     * Sets the value of the salesOrderRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesOrderRequest }
     *     
     */
    public void setSalesOrderRequest(SalesOrderRequest value) {
        this.salesOrderRequest = value;
    }

    /**
     * Gets the value of the timeCardInfoRequest property.
     * 
     * @return
     *     possible object is
     *     {@link TimeCardInfoRequest }
     *     
     */
    public TimeCardInfoRequest getTimeCardInfoRequest() {
        return timeCardInfoRequest;
    }

    /**
     * Sets the value of the timeCardInfoRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeCardInfoRequest }
     *     
     */
    public void setTimeCardInfoRequest(TimeCardInfoRequest value) {
        this.timeCardInfoRequest = value;
    }

    /**
     * Gets the value of the receiptRequest property.
     * 
     * @return
     *     possible object is
     *     {@link ReceiptRequest }
     *     
     */
    public ReceiptRequest getReceiptRequest() {
        return receiptRequest;
    }

    /**
     * Sets the value of the receiptRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceiptRequest }
     *     
     */
    public void setReceiptRequest(ReceiptRequest value) {
        this.receiptRequest = value;
    }

    /**
     * Gets the value of the orderStatusRequest property.
     * 
     * @return
     *     possible object is
     *     {@link OrderStatusRequest }
     *     
     */
    public OrderStatusRequest getOrderStatusRequest() {
        return orderStatusRequest;
    }

    /**
     * Sets the value of the orderStatusRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderStatusRequest }
     *     
     */
    public void setOrderStatusRequest(OrderStatusRequest value) {
        this.orderStatusRequest = value;
    }

    /**
     * Gets the value of the timeCardRequest property.
     * 
     * @return
     *     possible object is
     *     {@link TimeCardRequest }
     *     
     */
    public TimeCardRequest getTimeCardRequest() {
        return timeCardRequest;
    }

    /**
     * Sets the value of the timeCardRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeCardRequest }
     *     
     */
    public void setTimeCardRequest(TimeCardRequest value) {
        this.timeCardRequest = value;
    }

    /**
     * Gets the value of the componentConsumptionRequest property.
     * 
     * @return
     *     possible object is
     *     {@link ComponentConsumptionRequest }
     *     
     */
    public ComponentConsumptionRequest getComponentConsumptionRequest() {
        return componentConsumptionRequest;
    }

    /**
     * Sets the value of the componentConsumptionRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComponentConsumptionRequest }
     *     
     */
    public void setComponentConsumptionRequest(ComponentConsumptionRequest value) {
        this.componentConsumptionRequest = value;
    }

    /**
     * Gets the value of the deploymentMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeploymentMode() {
        if (deploymentMode == null) {
            return "production";
        } else {
            return deploymentMode;
        }
    }

    /**
     * Sets the value of the deploymentMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeploymentMode(String value) {
        this.deploymentMode = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
