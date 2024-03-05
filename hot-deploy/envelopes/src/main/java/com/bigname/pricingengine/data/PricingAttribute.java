package com.bigname.pricingengine.data;

import com.bigname.pricingengine.calculator.PricingCalculator;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class PricingAttribute {
    private long id;
    private String attributeId;
    private Map<Integer, BigDecimal[]> priceMap = new LinkedHashMap<>();
    public static BigDecimal[] ZERO_PRICE = new BigDecimal[] {BigDecimal.ZERO, BigDecimal.ZERO};

    public static PricingAttribute EMPTY_ATTRIBUTE = new PricingAttribute(0, "",  new int[0], new BigDecimal[0]);

    private List<Integer> quantityBreaks;

    public PricingAttribute(long id, String attributeId, int[] quantityBreaks, BigDecimal[] prices) {
        if(attributeId.isEmpty() && quantityBreaks.length == 0) {
            return;
        }
        if(prices.length != quantityBreaks.length || prices.length < 1 ) {
            throw new RuntimeException("Initialization exception: Invalid quantityBreaks or prices for Pricing Attribute '" + attributeId + "'");
        }
        this.id = id;
        this.attributeId = attributeId;
        for(int i = 0; i < quantityBreaks.length; i ++) {
            priceMap.put(quantityBreaks[i], new BigDecimal[]{getUnitPrice(prices[i], quantityBreaks[i]), prices[i]});
        }

        this.quantityBreaks = IntStream.of(quantityBreaks).boxed().collect(Collectors.toList());
        Collections.sort(this.quantityBreaks);
    }

    public String getAttributeId() {
        return attributeId;
    }

    public BigDecimal getUnitPrice(BigDecimal volumePrice, int qtyBreak) {
        if(qtyBreak <= 0) {
            return BigDecimal.ZERO;
        }

        return volumePrice.divide(new BigDecimal(qtyBreak), 3, BigDecimal.ROUND_HALF_UP);
    }

    public long getId() {
        return id;
    }

    public Map<Integer, BigDecimal[]> getPriceMap() {
        return priceMap;
    }

    public List<Integer> getQuantityBreaks() {
        return quantityBreaks;
    }

    public boolean hasVolumePricing(int quantity) {
        return priceMap.keySet().contains(quantity);
    }

    public int getMatchingQuantityBreak(int quantity) {
        if(priceMap.keySet().contains(quantity)) {
            return quantity;
        } else {
            int matchingQuantityBreak = 0;
            for(int quantityBreak : quantityBreaks) {
                if(quantityBreak <= quantity) {
                    matchingQuantityBreak = quantityBreak;
                } else if(quantityBreak > quantity) {
                    if(matchingQuantityBreak == 0) {
                        matchingQuantityBreak = quantityBreak;
                    }
                    break;
                }
            }
            return matchingQuantityBreak;
        }
    }

    public int[] getMatchingQuantityBreaks(int quantity) {
        if(priceMap.keySet().contains(quantity)) {
            return new int[] {quantity};
        } else {
            int matchingQuantityBreak1 = 0;
            int matchingQuantityBreak2 = 0;
            for (int i = 0; i < quantityBreaks.size(); i ++) {
                int quantityBreak = quantityBreaks.get(i);
                if(quantityBreak <= quantity) {
                    matchingQuantityBreak1 = quantityBreak;
                    if(i < quantityBreaks.size() - 1) {
                        matchingQuantityBreak2 = quantityBreaks.get(i + 1);
                    } else {
                        matchingQuantityBreak2 = 0;
                    }
                } else if(quantityBreak > quantity) {
                    if(matchingQuantityBreak1 == 0) {
                        matchingQuantityBreak1 = quantityBreak;
                        if(i < quantityBreaks.size() - 1) {
                            matchingQuantityBreak2 = quantityBreaks.get(i + 1);
                        } else {
                            matchingQuantityBreak2 = 0;
                        }
                        break;
                    }
                }
            }
            if(matchingQuantityBreak2 > 0) {
                return new int[] {matchingQuantityBreak1, matchingQuantityBreak2};
            } else {
                return new int[] {matchingQuantityBreak1};
            }
        }
    }

    public BigDecimal[] getPrice(PricingCalculator calculator, int quantity) {
        if(isEmpty()) {
            return ZERO_PRICE;
        }
        return calculator.getPrice(quantity, this);

    }

    public BigDecimal[] getSimplePrice(PricingCalculator calculator, int quantity) {
        if(isEmpty()) {
            return ZERO_PRICE;
        }
        return calculator.getSimplePrice(quantity, this);

    }

    public boolean isEmpty() {
        return this.id == 0 || this.attributeId == null || this.attributeId.isEmpty() || quantityBreaks.isEmpty() || priceMap.isEmpty();
    }
}
