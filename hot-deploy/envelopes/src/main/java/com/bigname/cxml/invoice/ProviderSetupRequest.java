
package com.bigname.cxml.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProviderSetupRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProviderSetupRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}OriginatorCookie"/>
 *         &lt;sequence minOccurs="0">
 *           &lt;element ref="{}BrowserFormPost"/>
 *           &lt;element ref="{}Followup" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;element ref="{}SelectedService"/>
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
@XmlType(name = "ProviderSetupRequest", propOrder = {
    "originatorCookie",
    "browserFormPost",
    "followup",
    "selectedService",
    "extrinsic"
})
public class ProviderSetupRequest {

    @XmlElement(name = "OriginatorCookie", required = true)
    protected OriginatorCookie originatorCookie;
    @XmlElement(name = "BrowserFormPost")
    protected BrowserFormPost browserFormPost;
    @XmlElement(name = "Followup")
    protected Followup followup;
    @XmlElement(name = "SelectedService", required = true)
    protected SelectedService selectedService;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;

    /**
     * Gets the value of the originatorCookie property.
     * 
     * @return
     *     possible object is
     *     {@link OriginatorCookie }
     *     
     */
    public OriginatorCookie getOriginatorCookie() {
        return originatorCookie;
    }

    /**
     * Sets the value of the originatorCookie property.
     * 
     * @param value
     *     allowed object is
     *     {@link OriginatorCookie }
     *     
     */
    public void setOriginatorCookie(OriginatorCookie value) {
        this.originatorCookie = value;
    }

    /**
     * Gets the value of the browserFormPost property.
     * 
     * @return
     *     possible object is
     *     {@link BrowserFormPost }
     *     
     */
    public BrowserFormPost getBrowserFormPost() {
        return browserFormPost;
    }

    /**
     * Sets the value of the browserFormPost property.
     * 
     * @param value
     *     allowed object is
     *     {@link BrowserFormPost }
     *     
     */
    public void setBrowserFormPost(BrowserFormPost value) {
        this.browserFormPost = value;
    }

    /**
     * Gets the value of the followup property.
     * 
     * @return
     *     possible object is
     *     {@link Followup }
     *     
     */
    public Followup getFollowup() {
        return followup;
    }

    /**
     * Sets the value of the followup property.
     * 
     * @param value
     *     allowed object is
     *     {@link Followup }
     *     
     */
    public void setFollowup(Followup value) {
        this.followup = value;
    }

    /**
     * Gets the value of the selectedService property.
     * 
     * @return
     *     possible object is
     *     {@link SelectedService }
     *     
     */
    public SelectedService getSelectedService() {
        return selectedService;
    }

    /**
     * Sets the value of the selectedService property.
     * 
     * @param value
     *     allowed object is
     *     {@link SelectedService }
     *     
     */
    public void setSelectedService(SelectedService value) {
        this.selectedService = value;
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
