package com.bigname.pricingengine.calculator.impl;

import com.bigname.pricingengine.calculator.PricingCalculator;
import com.bigname.pricingengine.data.AttributeGroup;
import com.bigname.pricingengine.data.PricingAttribute;
import com.bigname.pricingengine.data.PricingGrid;
import com.bigname.pricingengine.data.PricingNode;
import com.bigname.pricingengine.model.Vendor;
import com.bigname.pricingengine.request.PricingRequest;
import com.bigname.pricingengine.response.PricingResponse;
import com.bigname.quote.calculator.CalculatorHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

abstract class PricingCalculatorSupport implements PricingCalculator {
    Vendor vendor;
    private AttributeGroup pricingAttributeGroup;
    private AttributeGroup globalAttributeGroup;
    private AttributeGroup commonAttributeGroup;
    PricingRequest request;
    PricingResponse response;
    private List<Integer> quantities;
    private final Map<String, PricingAttribute> applicableAttributes = new LinkedHashMap<>();
    private PricingAttribute emptyPricingAttribute = new PricingAttribute(0, "", new int[0], new BigDecimal[0]);

    PricingCalculatorSupport(Vendor vendor, PricingNode pricingNode, PricingNode globalPricingNode, PricingRequest request, PricingResponse response) {
        this.vendor = vendor;
        pricingAttributeGroup =  pricingNode.getAttributeGroup();
        commonAttributeGroup = vendor.getCommonPricingNode().getAttributeGroup();
        globalAttributeGroup = globalPricingNode.getAttributeGroup();
        this.request = request;
        quantities = request.getQuantities();
        this.response = response;
        if(quantities.isEmpty() && !pricingAttributeGroup.getPricingAttributes().isEmpty()) {
            quantities.addAll(pricingAttributeGroup.getAttribute(pricingAttributeGroup.getPricingAttributes().entrySet().stream().findFirst().get().getKey()).getQuantityBreaks());
        }
        if(request.getCustomQuantity() > 0) {
            quantities.add(request.getCustomQuantity());
            Collections.sort(quantities);
        }
    }

    void preCalculate() {

    }

    String getPricingAttributeIdForColor(String colorGroup, String colorName, String paperTexture, String paperWeight, String vendorId) {
        return PricingGrid.getInstance().getVendorStockPricingAttributeId(colorGroup, colorName, paperTexture, paperWeight, vendorId);
    }

    String getAddonName(String addonId) {
        return PricingGrid.getInstance().getAddonName(addonId);
    }

    boolean apply(String key, String attributeId) {
        if(pricingAttributeGroup.hasAttribute(attributeId)) {
            applicableAttributes.put(key, pricingAttributeGroup.getAttribute(attributeId));
            return true;
        } else if(commonAttributeGroup.hasAttribute(attributeId)) {
            applicableAttributes.put(key, commonAttributeGroup.getAttribute(attributeId));
            return true;
        } else if(globalAttributeGroup.hasAttribute(attributeId)) {
            applicableAttributes.put(key, globalAttributeGroup.getAttribute(attributeId));
            return true;
        }else {
            return false;
        }
    }

    PricingAttribute getAttribute(String attributeId) {
        if(pricingAttributeGroup.hasAttribute(attributeId)) {
            return pricingAttributeGroup.getAttribute(attributeId);
        } else if(commonAttributeGroup.hasAttribute(attributeId)) {
            return commonAttributeGroup.getAttribute(attributeId);
        } else if(globalAttributeGroup.hasAttribute(attributeId)) {
            return globalAttributeGroup.getAttribute(attributeId);
        } else {
            return emptyPricingAttribute;
        }
    }

    void postCalculate() {
        Map<String, Object> pricingDetails = new LinkedHashMap<>();
        List<BigDecimal> total = new ArrayList<>();
        List<BigDecimal> cost = new ArrayList<>();
        for(int i = 0; i < quantities.size(); i ++) {
            total.add(BigDecimal.ZERO);
        }

        List<String> quantityDetails = new ArrayList<>();
        quantities.forEach(e -> quantityDetails.add(e.toString()));
        pricingDetails.put("Quantities", quantityDetails);

        applicableAttributes.forEach((k,v) -> {
            Map<Integer, String> pricingDetail = new LinkedHashMap<>();
            for(int i = 0; i < quantities.size(); i++) {
                BigDecimal quantityBreakPrice = v.getPrice(this, quantities.get(i))[1];
                quantityBreakPrice = quantityBreakPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
                total.set(i, total.get(i).add(quantityBreakPrice));
                pricingDetail.put(quantities.get(i), quantityBreakPrice.toString());
            }
            pricingDetails.put(k, pricingDetail);
        });

        cost.addAll(total);

        if (!total.isEmpty() && total.get(0).doubleValue() > 0) {
            Map<Integer, String> pricingDetail = new LinkedHashMap<>();
            for (int i = 0; i < quantities.size(); i++) {
                if (total.get(i).doubleValue() > 0) {
                    BigDecimal markupPercentage = getAttribute("MARKUP_PERCENTAGE").getSimplePrice(this, quantities.get(i))[1];
                    BigDecimal markupPrice = total.get(i).multiply(markupPercentage).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
                    markupPrice = markupPrice.setScale(2, BigDecimal.ROUND_HALF_UP);

                    total.set(i, total.get(i).add(markupPrice));
                    pricingDetail.put(quantities.get(i), markupPrice.toString());
                }
            }
            pricingDetails.put("Gross Profit Percentage Markup", pricingDetail);

            pricingDetail = new LinkedHashMap<>();
            for (int i = 0; i < quantities.size(); i++) {
                if (total.get(i).doubleValue() > 0) {
                    BigDecimal markupDollar = getAttribute("MARKUP_DOLLAR").getSimplePrice(this, quantities.get(i))[1];
                    markupDollar = markupDollar.setScale(2, BigDecimal.ROUND_HALF_UP);

                    total.set(i, total.get(i).add(markupDollar));
                    pricingDetail.put(quantities.get(i), markupDollar.toString());
                }
            }
            pricingDetails.put("Gross Profit Dollar Markup", pricingDetail);

            pricingDetail = new LinkedHashMap<>();
            for (int i = 0; i < quantities.size(); i++) {
                if (total.get(i).doubleValue() > 0) {
                    BigDecimal discountPercentage = getAttribute("DISCOUNT_PERCENTAGE").getSimplePrice(this, quantities.get(i))[1];
                    BigDecimal discountPrice = total.get(i).multiply(discountPercentage).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
                    discountPrice = discountPrice.setScale(2, BigDecimal.ROUND_HALF_UP);

                    total.set(i, total.get(i).add(discountPrice));
                    pricingDetail.put(quantities.get(i), discountPrice.toString());
                }
            }
            pricingDetails.put("Percentage Discount", pricingDetail);

            pricingDetail = new LinkedHashMap<>();
            for (int i = 0; i < quantities.size(); i++) {
                if (total.get(i).doubleValue() > 0) {
                    BigDecimal discountDollar = getAttribute("DISCOUNT_DOLLAR").getSimplePrice(this, quantities.get(i))[1];
                    discountDollar = discountDollar.setScale(2, BigDecimal.ROUND_HALF_UP);

                    total.set(i, total.get(i).add(discountDollar));
                    pricingDetail.put(quantities.get(i), discountDollar.toString());
                }
            }
            pricingDetails.put("Dollar Discount", pricingDetail);

        }

        Map<Integer, String> totalDetails = new LinkedHashMap<>();
        Map<Integer, String> costDetails = new LinkedHashMap<>();
        Map<Integer, String> unitDetails = new LinkedHashMap<>();
        for (int i = 0; i < total.size(); i++) {
            BigDecimal unitPrice = total.get(i).divide(new BigDecimal(quantities.get(i)), 2, BigDecimal.ROUND_HALF_UP);
            unitDetails.put(quantities.get(i), unitPrice.toString());
            totalDetails.put(quantities.get(i), unitPrice.multiply(new BigDecimal(quantities.get(i))).toString());
            costDetails.put(quantities.get(i), cost.get(i).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        }
        pricingDetails.put("Cost", costDetails);
        pricingDetails.put("Total", totalDetails);
        pricingDetails.put("Each", unitDetails);
        Map<String, Object> vendorPricing = new LinkedHashMap<>();
        vendorPricing.put("details", pricingDetails);
        response.setVendorDetailedPricing(vendor.getVendorId(), vendorPricing);


        response.setVendorSimplePricing(vendor.getVendorId(), applyDiscount((Map<Integer, String>)pricingDetails.get("Total")));
    }

    PricingRequest getPricingRequest() {
        return request;
    }

    public BigDecimal[] getPrice(int quantity, PricingAttribute pricingAttribute) {
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

    public BigDecimal[] getSimplePrice(int quantity, PricingAttribute pricingAttribute) {
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

    private Map<Integer, String> applyDiscount(Map<Integer, String> pricing) {
        for (Map.Entry<Integer, String> entry : pricing.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();

            pricing.put(key, new BigDecimal(value).multiply(new BigDecimal(1).subtract(CalculatorHelper.discountAmount)).setScale(2, RoundingMode.CEILING).toString());
        }

        return pricing;
    }
}
