package com.bigname.pricingengine.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeGroup {
    private Map<String, PricingAttribute> pricingAttributes = new HashMap<>();

    public AttributeGroup() {

    }

    public AttributeGroup(PricingAttribute pricingAttribute) {
        this.pricingAttributes.put(pricingAttribute.getAttributeId(), pricingAttribute);
    }

    public AttributeGroup(List<PricingAttribute> attributes) {
        attributes.forEach(a -> pricingAttributes.put(a.getAttributeId(), a));
    }

    public void addAttribute(PricingAttribute attribute) {
        pricingAttributes.put(attribute.getAttributeId(), attribute);
    }

    public void addAttributes(List<PricingAttribute> attributes) {
        attributes.forEach(a -> pricingAttributes.put(a.getAttributeId(), a));
    }

    public Map<String, PricingAttribute> getPricingAttributes() {
        return pricingAttributes;
    }

    public PricingAttribute getAttribute(String attributeId) {
        return pricingAttributes.getOrDefault(attributeId, PricingAttribute.EMPTY_ATTRIBUTE);
    }

    public boolean hasAttribute(String attributeId) {
        return pricingAttributes.containsKey(attributeId);
    }
}
