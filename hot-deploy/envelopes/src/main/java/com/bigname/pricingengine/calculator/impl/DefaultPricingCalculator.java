package com.bigname.pricingengine.calculator.impl;

import com.bigname.pricingengine.calculator.PricingCalculator;
import com.bigname.pricingengine.data.PricingNode;
import com.bigname.pricingengine.model.Vendor;
import com.bigname.pricingengine.request.PricingRequest;
import com.bigname.pricingengine.response.PricingResponse;


public class DefaultPricingCalculator extends PricingCalculatorSupport implements PricingCalculator {

    public DefaultPricingCalculator(Vendor vendor, PricingNode pricingNode, PricingNode globalPricingNode, PricingRequest request, PricingResponse response) {
        super(vendor, pricingNode, globalPricingNode, request, response);
    }



    @Override
    public void calculate() {

    }


}
