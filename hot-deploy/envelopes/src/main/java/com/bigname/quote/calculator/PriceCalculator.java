package com.bigname.quote.calculator;

import java.math.BigDecimal;

/**
 * Created by Manu on 7/18/2017.
 */
public interface PriceCalculator {
    default BigDecimal[] getPrice(int quantity, PricingAttribute pricingAttribute) {
        if(pricingAttribute.isEmpty()) {
            return PricingAttribute.ZERO_PRICE;
        }
        if(pricingAttribute.hasVolumePricing(quantity)) {
            return pricingAttribute.getPriceMap().get(quantity);
        } else {
            int matchingQtyBreak = pricingAttribute.getMatchingQuantityBreak(quantity);
            BigDecimal unitPrice = pricingAttribute.getUnitPrice(pricingAttribute.getPriceMap().get(matchingQtyBreak)[1], matchingQtyBreak);
            BigDecimal totalPrice = unitPrice.multiply(new BigDecimal(quantity));
            return new BigDecimal[] {unitPrice, totalPrice};
        }
    }

    default BigDecimal[] getSimplePrice(int quantity, PricingAttribute pricingAttribute) {
        if(pricingAttribute.isEmpty()) {
            return PricingAttribute.ZERO_PRICE;
        }
        if(pricingAttribute.hasVolumePricing(quantity)) {
            return pricingAttribute.getPriceMap().get(quantity);
        } else {
            int matchingQtyBreak = pricingAttribute.getMatchingQuantityBreak(quantity);
            return pricingAttribute.getPriceMap().get(matchingQtyBreak);
        }
    }
}
