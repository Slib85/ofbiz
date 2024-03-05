
package com.bigname.cxml.fulfill;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Packaging complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Packaging">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;sequence>
 *             &lt;element ref="{}PackagingCode" maxOccurs="unbounded"/>
 *             &lt;element ref="{}Dimension" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;/sequence>
 *           &lt;element ref="{}Dimension" maxOccurs="unbounded"/>
 *         &lt;/choice>
 *         &lt;element ref="{}Description" minOccurs="0"/>
 *         &lt;element ref="{}PackagingLevelCode" minOccurs="0"/>
 *         &lt;element ref="{}PackageTypeCodeIdentifierCode" minOccurs="0"/>
 *         &lt;element ref="{}ShippingContainerSerialCode" minOccurs="0"/>
 *         &lt;element ref="{}ShippingContainerSerialCodeReference" minOccurs="0"/>
 *         &lt;element ref="{}PackageID" minOccurs="0"/>
 *         &lt;element ref="{}ShippingMark" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}OrderedQuantity" minOccurs="0"/>
 *         &lt;element ref="{}DispatchQuantity" minOccurs="0"/>
 *         &lt;element ref="{}FreeGoodsQuantity" minOccurs="0"/>
 *         &lt;element ref="{}QuantityVarianceNote" minOccurs="0"/>
 *         &lt;element ref="{}BestBeforeDate" minOccurs="0"/>
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
@XmlType(name = "Packaging", propOrder = {
    "content"
})
public class Packaging {

    @XmlElementRefs({
        @XmlElementRef(name = "PackagingCode", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Description", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "PackageID", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "OrderedQuantity", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Dimension", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Extrinsic", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ShippingMark", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "PackageTypeCodeIdentifierCode", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "FreeGoodsQuantity", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ShippingContainerSerialCode", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "BestBeforeDate", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "PackagingLevelCode", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ShippingContainerSerialCodeReference", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "QuantityVarianceNote", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "DispatchQuantity", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> content;

    /**
     * Gets the rest of the content model. 
     * 
     * <p>
     * You are getting this "catch-all" property because of the following reason: 
     * The field name "Dimension" is used by two different parts of a schema. See: 
     * line 2480 of file:/C:/DevStudio/Projects/CXML/CXML-1.2.0.29/src/main/resources/Fulfill3.xsd
     * line 2478 of file:/C:/DevStudio/Projects/CXML/CXML-1.2.0.29/src/main/resources/Fulfill3.xsd
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
     * {@link JAXBElement }{@code <}{@link PackagingCode }{@code >}
     * {@link JAXBElement }{@code <}{@link Description }{@code >}
     * {@link JAXBElement }{@code <}{@link PackageID }{@code >}
     * {@link JAXBElement }{@code <}{@link OrderedQuantity }{@code >}
     * {@link JAXBElement }{@code <}{@link Dimension }{@code >}
     * {@link JAXBElement }{@code <}{@link Extrinsic }{@code >}
     * {@link JAXBElement }{@code <}{@link ShippingMark }{@code >}
     * {@link JAXBElement }{@code <}{@link PackageTypeCodeIdentifierCode }{@code >}
     * {@link JAXBElement }{@code <}{@link FreeGoodsQuantity }{@code >}
     * {@link JAXBElement }{@code <}{@link ShippingContainerSerialCode }{@code >}
     * {@link JAXBElement }{@code <}{@link BestBeforeDate }{@code >}
     * {@link JAXBElement }{@code <}{@link PackagingLevelCode }{@code >}
     * {@link JAXBElement }{@code <}{@link ShippingContainerSerialCodeReference }{@code >}
     * {@link JAXBElement }{@code <}{@link QuantityVarianceNote }{@code >}
     * {@link JAXBElement }{@code <}{@link DispatchQuantity }{@code >}
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
