
package com.bigname.cxml.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceDetailOrderInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceDetailOrderInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;sequence>
 *           &lt;element ref="{}OrderReference"/>
 *           &lt;choice minOccurs="0">
 *             &lt;element ref="{}MasterAgreementReference"/>
 *             &lt;element ref="{}MasterAgreementIDInfo"/>
 *           &lt;/choice>
 *           &lt;element ref="{}SupplierOrderInfo" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element ref="{}MasterAgreementReference"/>
 *           &lt;element ref="{}OrderIDInfo" minOccurs="0"/>
 *           &lt;element ref="{}SupplierOrderInfo" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element ref="{}MasterAgreementIDInfo"/>
 *           &lt;element ref="{}OrderIDInfo" minOccurs="0"/>
 *           &lt;element ref="{}SupplierOrderInfo" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element ref="{}OrderIDInfo"/>
 *           &lt;element ref="{}SupplierOrderInfo" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;element ref="{}SupplierOrderInfo"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceDetailOrderInfo", propOrder = {
    "content"
})
public class InvoiceDetailOrderInfo {

    @XmlElementRefs({
        @XmlElementRef(name = "MasterAgreementReference", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "MasterAgreementIDInfo", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "OrderIDInfo", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "OrderReference", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "SupplierOrderInfo", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> content;

    /**
     * Gets the rest of the content model. 
     * 
     * <p>
     * You are getting this "catch-all" property because of the following reason: 
     * The field name "MasterAgreementReference" is used by two different parts of a schema. See: 
     * line 3120 of file:/C:/DevStudio/Projects/CXML/CXML-1.2.0.29/src/main/resources/InvoiceDetail3.xsd
     * line 3114 of file:/C:/DevStudio/Projects/CXML/CXML-1.2.0.29/src/main/resources/InvoiceDetail3.xsd
     * <p>
     * To get rid of this property, apply a property customization to one 
     * of both of the following declarations to change their names: 
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link MasterAgreementReference }{@code >}
     * {@link JAXBElement }{@code <}{@link MasterAgreementIDInfo }{@code >}
     * {@link JAXBElement }{@code <}{@link OrderReference }{@code >}
     * {@link JAXBElement }{@code <}{@link SupplierOrderInfo }{@code >}
     * {@link JAXBElement }{@code <}{@link OrderIDInfo }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getContent() {
        if (content == null) {
            content = new ArrayList<JAXBElement<?>>();
        }
        return this.content;
    }

}
