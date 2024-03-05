
package com.bigname.cxml.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for SubcontractingComponent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SubcontractingComponent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ComponentID"/>
 *         &lt;element ref="{}UnitOfMeasure"/>
 *         &lt;element ref="{}Description" minOccurs="0"/>
 *         &lt;element ref="{}Product" minOccurs="0"/>
 *         &lt;element ref="{}ProductRevisionID" minOccurs="0"/>
 *         &lt;element ref="{}Batch" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="quantity" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="requirementDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="materialProvisionIndicator">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="regular"/>
 *             &lt;enumeration value="reworkTo"/>
 *             &lt;enumeration value="reworkFrom"/>
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
@XmlType(name = "SubcontractingComponent", propOrder = {
    "componentID",
    "unitOfMeasure",
    "description",
    "product",
    "productRevisionID",
    "batch"
})
public class SubcontractingComponent {

    @XmlElement(name = "ComponentID", required = true)
    protected ComponentID componentID;
    @XmlElement(name = "UnitOfMeasure", required = true)
    protected UnitOfMeasure unitOfMeasure;
    @XmlElement(name = "Description")
    protected Description description;
    @XmlElement(name = "Product")
    protected Product product;
    @XmlElement(name = "ProductRevisionID")
    protected ProductRevisionID productRevisionID;
    @XmlElement(name = "Batch")
    protected Batch batch;
    @XmlAttribute(name = "quantity", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String quantity;
    @XmlAttribute(name = "requirementDate")
    @XmlSchemaType(name = "anySimpleType")
    protected String requirementDate;
    @XmlAttribute(name = "materialProvisionIndicator")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String materialProvisionIndicator;

    /**
     * Gets the value of the componentID property.
     * 
     * @return
     *     possible object is
     *     {@link ComponentID }
     *     
     */
    public ComponentID getComponentID() {
        return componentID;
    }

    /**
     * Sets the value of the componentID property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComponentID }
     *     
     */
    public void setComponentID(ComponentID value) {
        this.componentID = value;
    }

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
     * Gets the value of the product property.
     * 
     * @return
     *     possible object is
     *     {@link Product }
     *     
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the value of the product property.
     * 
     * @param value
     *     allowed object is
     *     {@link Product }
     *     
     */
    public void setProduct(Product value) {
        this.product = value;
    }

    /**
     * Gets the value of the productRevisionID property.
     * 
     * @return
     *     possible object is
     *     {@link ProductRevisionID }
     *     
     */
    public ProductRevisionID getProductRevisionID() {
        return productRevisionID;
    }

    /**
     * Sets the value of the productRevisionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductRevisionID }
     *     
     */
    public void setProductRevisionID(ProductRevisionID value) {
        this.productRevisionID = value;
    }

    /**
     * Gets the value of the batch property.
     * 
     * @return
     *     possible object is
     *     {@link Batch }
     *     
     */
    public Batch getBatch() {
        return batch;
    }

    /**
     * Sets the value of the batch property.
     * 
     * @param value
     *     allowed object is
     *     {@link Batch }
     *     
     */
    public void setBatch(Batch value) {
        this.batch = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuantity(String value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the requirementDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequirementDate() {
        return requirementDate;
    }

    /**
     * Sets the value of the requirementDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequirementDate(String value) {
        this.requirementDate = value;
    }

    /**
     * Gets the value of the materialProvisionIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialProvisionIndicator() {
        return materialProvisionIndicator;
    }

    /**
     * Sets the value of the materialProvisionIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialProvisionIndicator(String value) {
        this.materialProvisionIndicator = value;
    }

}
