package com.bigname.quote.calculator;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Manu on 5/25/2017.
 */
public class PricingAttribute {

    private String id;
    private String name;
    private List<String> styleIds;
    private Map<Integer, BigDecimal[]> priceMap = new LinkedHashMap<>();

    public static BigDecimal[] ZERO_PRICE = new BigDecimal[] {BigDecimal.ZERO, BigDecimal.ZERO};

    private List<Integer> quantityBreaks;

    public PricingAttribute(String id, String name, int[] quantityBreaks, BigDecimal[] prices, List<String> styleIds) {
        if(name.isEmpty() && quantityBreaks.length == 0) {
            return;
        }
        if(prices.length != quantityBreaks.length || prices.length < 1 ) {
            throw new RuntimeException("Initialization exception: Invalid quantityBreaks or prices for Pricing Attribute '" + name + "'");
        }
        this.id = id;
        this.name = name;
        this.styleIds = styleIds;
        for(int i = 0; i < quantityBreaks.length; i ++) {
            priceMap.put(quantityBreaks[i], new BigDecimal[]{getUnitPrice(prices[i], quantityBreaks[i]), prices[i]});
        }

        this.quantityBreaks = IntStream.of(quantityBreaks).boxed().collect(Collectors.toList());
        Collections.sort(this.quantityBreaks);
    }

    public BigDecimal[] getPrice(int quantity, String vendorId) {
        if(isEmpty()) {
            return ZERO_PRICE;
        }
        return getPriceCalculator(vendorId).getPrice(quantity, this);

    }

    public BigDecimal[] getSimplePrice(int quantity, String vendorId) {
        if(isEmpty()) {
            return ZERO_PRICE;
        }
        return getPriceCalculator(vendorId).getSimplePrice(quantity, this);

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

    private PriceCalculator getPriceCalculator(String vendorId) {
        if(vendorId.equals("ADMORE")) {
            return new AdmorePriceCalculator() {};
        } else {
            return new PriceCalculator() {};
        }
    }

    public BigDecimal getUnitPrice(BigDecimal volumePrice, int qtyBreak) {
        if(qtyBreak <= 0) {
            return BigDecimal.ZERO;
        }

        return volumePrice.divide(new BigDecimal(qtyBreak), 3, BigDecimal.ROUND_HALF_UP);
    }

    public Map<Integer, BigDecimal[]> getPriceMap() {
        return priceMap;
    }

    public boolean hasVolumePricing(int quantity) {
        return priceMap.keySet().contains(quantity);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getStyleIds() {
        return styleIds;
    }

    public boolean isEmpty() {
        return this.id == "0" || this.name == null || this.name.isEmpty() || quantityBreaks.isEmpty() || priceMap.isEmpty();
    }

    public int getMinQuantity() {
        return this.quantityBreaks.get(0);
    }

    public int getMaxQuantity() {
        return this.quantityBreaks.get(this.quantityBreaks.size() - 1);
    }
}
