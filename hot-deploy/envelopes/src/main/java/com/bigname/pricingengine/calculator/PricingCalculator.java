package com.bigname.pricingengine.calculator;

import com.bigname.pricingengine.data.PricingAttribute;

import java.math.BigDecimal;

public interface PricingCalculator {

    void calculate();

    BigDecimal[] getPrice(int quantity, PricingAttribute pricingAttribute);

    BigDecimal[] getSimplePrice(int quantity, PricingAttribute pricingAttribute);
}
