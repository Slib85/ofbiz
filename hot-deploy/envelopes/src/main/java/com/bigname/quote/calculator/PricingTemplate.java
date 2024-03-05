package com.bigname.quote.calculator;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Manu on 5/26/2017.
 */
public class PricingTemplate {
    private String name;
    private List<String> vendorIds = new ArrayList<>();
    private int minQty = 250;
    private int maxQty = 10000;

    private Map<String, Map<String, PricingAttribute>> vendorsPricingAttributes = new HashMap<>();
    private Map<String, Map<String, Map<String, PricingAttribute>>> vendorsItemsPricingAttributes = new HashMap<>();

    private PricingAttribute emptyPricingAttribute = new PricingAttribute("0", "", new int[0], new BigDecimal[0], new ArrayList<>());

    public PricingTemplate(String name, String vendorId, List<PricingAttribute> pricingAttributes) {
        this.name = name;
        this.vendorIds.add(vendorId);
        int _minQty = pricingAttributes.isEmpty() ? 0 : pricingAttributes.get(0).getMinQuantity();
        this.minQty = _minQty > 0 ? _minQty : minQty;
        int _maxQty = pricingAttributes.isEmpty() ? 0 : pricingAttributes.get(0).getMaxQuantity();
        this.maxQty = _maxQty > 0 ? _maxQty : maxQty;
        Map<String, PricingAttribute> vendorPricingAttributes = new HashMap<>();

        Map<String, Map<String, PricingAttribute>> vendorItemPricingAttributes = new HashMap<>();

        for(PricingAttribute pricingAttribute : pricingAttributes) {
            if(pricingAttribute.getStyleIds().isEmpty()) {
                vendorPricingAttributes.put(pricingAttribute.getName(), pricingAttribute);
            } else {
                for(String styleId : pricingAttribute.getStyleIds()) {
                    if(vendorItemPricingAttributes.containsKey(styleId)) {
                        Map<String, PricingAttribute> itemPricingAttributes = vendorItemPricingAttributes.get(styleId);
                        itemPricingAttributes.put(pricingAttribute.getName(), pricingAttribute);
                        vendorItemPricingAttributes.put(styleId, itemPricingAttributes);
                    } else {
                        Map<String, PricingAttribute> itemPricingAttributes = new HashMap<>();
                        itemPricingAttributes.put(pricingAttribute.getName(), pricingAttribute);
                        vendorItemPricingAttributes.put(styleId, itemPricingAttributes);
                    }
                }
            }
        }

        this.vendorsPricingAttributes.put(vendorId, vendorPricingAttributes);
        this.vendorsItemsPricingAttributes.put(vendorId, vendorItemPricingAttributes);

    }

    public PricingAttribute getPricingAttribute(String name, String vendorId) {
        if(vendorsPricingAttributes.containsKey(vendorId)) {
            Map<String, PricingAttribute> vendorPricingAttributes = vendorsPricingAttributes.get(vendorId);
            return vendorPricingAttributes.getOrDefault(name, emptyPricingAttribute);
        }
        return emptyPricingAttribute;
    }

    public PricingAttribute getPricingAttribute(String styleId, String name, String vendorId) {
        if(vendorsItemsPricingAttributes.containsKey(vendorId)) {
            Map<String, Map<String, PricingAttribute>> vendorItemsPricingAttributes = vendorsItemsPricingAttributes.get(vendorId);
            if(vendorItemsPricingAttributes.containsKey(styleId)) {
                Map<String, PricingAttribute> vendorItemPricingAttributes = vendorItemsPricingAttributes.get(styleId);
                if(vendorItemPricingAttributes.containsKey(name)) {
                    return vendorItemPricingAttributes.getOrDefault(name, emptyPricingAttribute);
                }
            }
        }
        return emptyPricingAttribute;
    }

    public String getName() {
        return name;
    }

    public List<String> getVendorIds() {
        return vendorIds;
    }

    public int getMinQty() {
        return minQty;
    }

    public int getMaxQty() {
        return maxQty;
    }
}
