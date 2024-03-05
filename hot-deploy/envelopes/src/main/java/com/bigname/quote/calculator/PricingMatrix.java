package com.bigname.quote.calculator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Manu on 5/26/2017.
 */
public class PricingMatrix {
    private String name;
    private List<String> groupIds = new ArrayList<>();
    private Map<String, PricingTemplate> pricingTemplates = new LinkedHashMap<>();

    public PricingMatrix(String name, List<PricingTemplate> pricingTemplates) {
        this.name = name;
        for(PricingTemplate pricingTemplate : pricingTemplates) {
            this.pricingTemplates.put(pricingTemplate.getName(), pricingTemplate);
            this.groupIds.add(pricingTemplate.getName());
        }
    }

    public PricingTemplate getPricingTemplate(String name) {
        return pricingTemplates.get(name);
    }

    public String getName() {
        return name;
    }

    public List<String> getGroupIds() {
        return groupIds;
    }

}
