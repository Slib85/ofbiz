
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
 * <p>Java class for GetPendingRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetPendingRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}MessageType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="maxMessages" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="lastReceivedTimestamp" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetPendingRequest", propOrder = {
    "messageType"
})
public class GetPendingRequest {

    @XmlElement(name = "MessageType", required = true)
    protected List<MessageType> messageType;
    @XmlAttribute(name = "maxMessages")
    @XmlSchemaType(name = "anySimpleType")
    protected String maxMessages;
    @XmlAttribute(name = "lastReceivedTimestamp")
    @XmlSchemaType(name = "anySimpleType")
    protected String lastReceivedTimestamp;

    /**
     * Gets the value of the messageType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the messageType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMessageType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MessageType }
     * 
     * 
     */
    public List<MessageType> getMessageType() {
        if (messageType == null) {
            messageType = new ArrayList<MessageType>();
        }
        return this.messageType;
    }

    /**
     * Gets the value of the maxMessages property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxMessages() {
        return maxMessages;
    }

    /**
     * Sets the value of the maxMessages property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxMessages(String value) {
        this.maxMessages = value;
    }

    /**
     * Gets the value of the lastReceivedTimestamp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastReceivedTimestamp() {
        return lastReceivedTimestamp;
    }

    /**
     * Sets the value of the lastReceivedTimestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastReceivedTimestamp(String value) {
        this.lastReceivedTimestamp = value;
    }

}
