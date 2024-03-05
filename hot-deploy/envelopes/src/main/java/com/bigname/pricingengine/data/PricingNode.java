package com.bigname.pricingengine.data;

import java.util.HashMap;
import java.util.List;

public class PricingNode {
    private AttributeGroup attributeGroup;

    public PricingNode() {
        this.attributeGroup = new AttributeGroup();
    }

    public PricingNode(PricingAttribute pricingAttribute) {
        this.attributeGroup = new AttributeGroup(pricingAttribute);
    }

    public PricingNode(List<PricingAttribute> pricingAttributes) {
        this.attributeGroup = new AttributeGroup(pricingAttributes);
    }

    public void addAttribute(PricingAttribute pricingAttribute) {
        this.attributeGroup.addAttribute(pricingAttribute);
    }

    public void addAttributes(List<PricingAttribute> pricingAttributes) {
        this.attributeGroup.addAttributes(pricingAttributes);
    }

    public AttributeGroup getAttributeGroup() {
        return attributeGroup;
    }

    void swap(PricingNode node) {
        this.attributeGroup = node.getAttributeGroup();
    }
}
