
package com.bigname.cxml.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ReleaseInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReleaseInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}UnitOfMeasure"/>
 *         &lt;element ref="{}ShipNoticeReleaseInfo" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="releaseType" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="jit"/>
 *             &lt;enumeration value="forecast"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="cumulativeReceivedQuantity" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="releaseNumber" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="productionGoAheadEndDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="materialGoAheadEndDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReleaseInfo", propOrder = {
    "unitOfMeasure",
    "shipNoticeReleaseInfo",
    "extrinsic"
})
public class ReleaseInfo {

    @XmlElement(name = "UnitOfMeasure", required = true)
    protected UnitOfMeasure unitOfMeasure;
    @XmlElement(name = "ShipNoticeReleaseInfo")
    protected ShipNoticeReleaseInfo shipNoticeReleaseInfo;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(name = "releaseType", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String releaseType;
    @XmlAttribute(name = "cumulativeReceivedQuantity", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String cumulativeReceivedQuantity;
    @XmlAttribute(name = "releaseNumber")
    @XmlSchemaType(name = "anySimpleType")
    protected String releaseNumber;
    @XmlAttribute(name = "productionGoAheadEndDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String productionGoAheadEndDate;
    @XmlAttribute(name = "materialGoAheadEndDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String materialGoAheadEndDate;

    /**
     * Gets the value of the unitOfMeasure property.
     * 
     * @return
     *     possible object is
     *     {@link UnitOfMeasure }
     *     
     */
    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    /**
     * Sets the value of the unitOfMeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitOfMeasure }
     *     
     */
    public void setUnitOfMeasure(UnitOfMeasure value) {
        this.unitOfMeasure = value;
    }

    /**
     * Gets the value of the shipNoticeReleaseInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ShipNoticeReleaseInfo }
     *     
     */
    public ShipNoticeReleaseInfo getShipNoticeReleaseInfo() {
        return shipNoticeReleaseInfo;
    }

    /**
     * Sets the value of the shipNoticeReleaseInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipNoticeReleaseInfo }
     *     
     */
    public void setShipNoticeReleaseInfo(ShipNoticeReleaseInfo value) {
        this.shipNoticeReleaseInfo = value;
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
     * Gets the value of the releaseType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReleaseType() {
        return releaseType;
    }

    /**
     * Sets the value of the releaseType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReleaseType(String value) {
        this.releaseType = value;
    }

    /**
     * Gets the value of the cumulativeReceivedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCumulativeReceivedQuantity() {
        return cumulativeReceivedQuantity;
    }

    /**
     * Sets the value of the cumulativeReceivedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCumulativeReceivedQuantity(String value) {
        this.cumulativeReceivedQuantity = value;
    }

    /**
     * Gets the value of the releaseNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReleaseNumber() {
        return releaseNumber;
    }

    /**
     * Sets the value of the releaseNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReleaseNumber(String value) {
        this.releaseNumber = value;
    }

    /**
     * Gets the value of the productionGoAheadEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductionGoAheadEndDate() {
        return productionGoAheadEndDate;
    }

    /**
     * Sets the value of the productionGoAheadEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductionGoAheadEndDate(String value) {
        this.productionGoAheadEndDate = value;
    }

    /**
     * Gets the value of the materialGoAheadEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialGoAheadEndDate() {
        return materialGoAheadEndDate;
    }

    /**
     * Sets the value of the materialGoAheadEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialGoAheadEndDate(String value) {
        this.materialGoAheadEndDate = value;
    }

}
