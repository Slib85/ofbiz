package com.bigname.quote.calculator;

import java.math.BigDecimal;

/**
 * Created by Manu on 7/18/2017.
 */
public class AdmorePriceCalculator implements PriceCalculator {
    @Override
    public BigDecimal[] getPrice(int quantity, PricingAttribute pricingAttribute) {
        if(pricingAttribute.isEmpty()) {
            return PricingAttribute.ZERO_PRICE;
        } else {
            if(pricingAttribute.hasVolumePricing(quantity)) {
                return pricingAttribute.getPriceMap().get(quantity);
            } else {
                int[] matchingQtyBreaks = pricingAttribute.getMatchingQuantityBreaks(quantity);
                if(matchingQtyBreaks[0] == 0) {
                    return PricingAttribute.ZERO_PRICE;
                }
                if(matchingQtyBreaks.length == 1) {
                    return pricingAttribute.getPriceMap().get(matchingQtyBreaks[0]);
                } else {
                    int qtyBreak1 = matchingQtyBreaks[0];
                    int qtyBreak2 = matchingQtyBreaks[1];

                    BigDecimal price1 = pricingAttribute.getPriceMap().get(qtyBreak1) [1];
                    BigDecimal price2 = pricingAttribute.getPriceMap().get(qtyBreak2) [1];

                    BigDecimal unitPrice1 = pricingAttribute.getUnitPrice(price1, qtyBreak1);
                    BigDecimal unitPrice2 = price2.subtract(price1).divide(new BigDecimal(qtyBreak2 - qtyBreak1), 2, BigDecimal.ROUND_HALF_UP);

                    BigDecimal totalPrice = unitPrice1.multiply(new BigDecimal(qtyBreak1)).add(unitPrice2.multiply(new BigDecimal(quantity - qtyBreak1)));
                    BigDecimal unitPrice = pricingAttribute.getUnitPrice(totalPrice, quantity);
                    return new BigDecimal[] {unitPrice, totalPrice};
                }
            }
        }
    }
}
