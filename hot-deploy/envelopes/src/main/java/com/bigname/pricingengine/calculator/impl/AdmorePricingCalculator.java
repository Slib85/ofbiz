package com.bigname.pricingengine.calculator.impl;

import com.bigname.pricingengine.addon.option.AddonOption;
import com.bigname.pricingengine.customization.CustomizationType;
import com.bigname.pricingengine.customization.option.CustomizationOption;
import com.bigname.pricingengine.customization.option.impl.Embossing;
import com.bigname.pricingengine.customization.option.impl.FoilStamping;
import com.bigname.pricingengine.customization.option.impl.OffsetPrinting;
import com.bigname.pricingengine.data.*;
import com.bigname.pricingengine.model.Vendor;
import com.bigname.pricingengine.request.PricingRequest;
import com.bigname.pricingengine.request.impl.DefaultPricingRequest;
import com.bigname.pricingengine.response.PricingResponse;
import com.bigname.pricingengine.util.EngineUtil;

import java.math.BigDecimal;
import java.util.*;


public class AdmorePricingCalculator extends PricingCalculatorSupport {

    public AdmorePricingCalculator(Vendor vendor, PricingNode pricingNode, PricingNode globalPricingNode, PricingRequest request, PricingResponse response) {
        super(vendor, pricingNode, globalPricingNode, request, response);
    }

    private class AdmorePricingRequest extends DefaultPricingRequest implements PricingRequest {
        AdmorePricingRequest(PricingRequest request) {
            super(request.getContext());
        }
    }

    @Override
    public void calculate() {
        preCalculate();
        AdmorePricingRequest _request = new AdmorePricingRequest(request);

        final List<CustomizationOption> customizations = Collections.unmodifiableList(_request.getCustomizations());
        final List<AddonOption> addons = _request.getAddons();
        final boolean hasOffSetPrinting = customizations.stream().filter(e -> e.getCustomizationType() == CustomizationType.OFFSET_PRINTING).count() > 0;
        final boolean hasFoilStamping = customizations.stream().filter(e -> e.getCustomizationType() == CustomizationType.FOIL_STAMPING).count() > 0;
        final boolean hasEmbossing = customizations.stream().filter(e -> e.getCustomizationType() == CustomizationType.EMBOSSING).count() > 0;

        //Plain Price
        apply("Base Price", "PLAIN");

        //Item Upcharge
        apply("Upcharge", "UPCHARGE");

        //Stock Upcharge
        String colorPricingAttributeId = super.getPricingAttributeIdForColor(_request.getColorGroup(), _request.getColorName(), _request.getPaperTexture(), _request.getPaperWeight(), vendor.getVendorId());
        if(!EngineUtil.isEmpty(colorPricingAttributeId)) {
            apply("Stock Upcharge",colorPricingAttributeId);
        }

        customizations.forEach(e -> {
            switch (e.getCustomizationType()) {
                case OFFSET_PRINTING:
                    OffsetPrinting offsetPrinting = (OffsetPrinting)e;
                    offsetPrinting.getSides().forEach(s -> {
                        if(s.getIndex() == 0) {
                            if(s.getMethod() == OffsetPrinting.Method.PMS) {
                                apply("PMS Offset Printing - Base Price", "PRINT_1C");
                            } else if(s.getMethod() == OffsetPrinting.Method.FOUR_COLOR) {
                                apply("Four Color Offset Printing - Base Price", "PRINT_4C");
                            }
                            for (int i = 0; i < (s.getMethod() == OffsetPrinting.Method.PMS ? s.getNumberOfInks() - 1 : s.getNumberOfInks()); i++) {
                                apply("Side 1 - Additional Ink Add-on Price - " + (i + 1), "ADDITIONAL_COLOR_PER_SIDE");
                            }
                            if(s.isHeavyInkCoverage()) {
                                apply("Side 1 - Heavy Coverage Add-on Price", "HEAVY_COVERAGE_PER_SIDE");
                            }
                            for (int i = 0; i < s.getColorWashes(); i++) {
                                apply("Side 1 - Color Wash Add-on Price - " + (i + 1), s.isHeavyInkCoverage() ? "COLOR_WASH_WITH_HEAVY_COVERAGE" : "COLOR_WASH");
                            }
                            for (int i = 0; i < s.getPlateChanges(); i++) {
                                apply("Side 1 - Plate Change Add-on Price - " + (i + 1), "PLATE_CHANGE");
                            }
                        } else if(s.getIndex() == 1) {
                            if(s.getMethod() == OffsetPrinting.Method.FOUR_COLOR) {
                                if(offsetPrinting.getSides().get(0).getMethod() == OffsetPrinting.Method.FOUR_COLOR) {
                                    apply("Four Color Process - 2nd Side Price", "PRINT_4C_2ND_SIDE");
                                } else {
                                    apply("Four Color Offset Printing - Base Price", "PRINT_4C");
                                }
                            }
                            for (int i = 0; i < (offsetPrinting.getSides().get(0).getMethod() == OffsetPrinting.Method.FOUR_COLOR && s.getMethod() == OffsetPrinting.Method.PMS ? s.getNumberOfInks() - 1 : s.getNumberOfInks()); i++) {
                                apply("Side 2 - Additional Ink Add-on Price - " + (i + 1), "ADDITIONAL_COLOR_PER_SIDE");
                            }
                            if(s.isHeavyInkCoverage()) {
                                apply("Side 2 - Heavy Coverage Add-on Price", "HEAVY_COVERAGE_PER_SIDE");
                            }
                            for (int i = 0; i < s.getColorWashes(); i++) {
                                apply("Side 2 - Color Wash Add-on Price - " + (i + 1), s.isHeavyInkCoverage() ? "COLOR_WASH_WITH_HEAVY_COVERAGE" : "COLOR_WASH");
                            }
                            for (int i = 0; i < s.getPlateChanges(); i++) {
                                apply("Side 2 - Plate Change Add-on Price - " + (i + 1), "PLATE_CHANGE");
                            }
                        }
                    });
                    break;

                case FOIL_STAMPING:
                    FoilStamping foilStamping = (FoilStamping) e;
                    foilStamping.getSide(0).getRuns().forEach(r -> {
                        if(hasOffSetPrinting || r.getIndex() > 0) {
                            apply("Foil Stamping - Add-on Price" + (r.getIndex() + 1), "FOIL_STAMP_RUN_ADD_ON");
                        } else {
                            apply("Foil Stamping - Base Price", "FOIL_STAMP_BASE");
                        }

                        r.getImages().forEach(i -> {
                            if(i.getIndex() > 0) {
                                apply("Foil Stamping - RUN " + (r.getIndex() + 1) + " - Additional Image Add-on Price" + i.getIndex(), "FOIL_STAMP_IMAGE_ADD_ON");
                            }
                        });
                    });
                    break;

                case EMBOSSING:
                    Embossing embossing = (Embossing) e;
                    embossing.getSides().forEach(s -> s.getRuns().forEach(r -> {
                        if(hasOffSetPrinting || hasFoilStamping || r.getIndex() > 0 || s.getIndex() > 0) {
                            apply("Side " + (s.getIndex() + 1) + " Embossing Add-on Price - RUN" + (r.getIndex() + 1), "EMBOSS_RUN_ADD_ON");
                        } else {
                            apply("Embossing - Base Price", "EMBOSS_BASE");
                        }
                        r.getImages().forEach(i -> {
                            if(i.getIndex() > 0) {
                                apply("Side " + (s.getIndex() + 1) + " Embossing - RUN" + (r.getIndex() + 1) + " - Additional Image Add-on Price" + i.getIndex(), "EMBOSS_IMAGE_ADD_ON");
                            }
                        });
                    }));
                    break;
            }
        });

        addons.forEach(e -> e.getSides().forEach(s -> s.getAddons().forEach(a -> {
            if (e.getType().equalsIgnoreCase("COATINGS")) {
                apply(getAddonName(a.getId()) + " - Addon - Side " + s.getIndex() + 1, a.getId());
            } else {
                apply(getAddonName(a.getId()) + " - Addon " + s.getIndex() + 1, a.getId());
            }
        })));

        //Image discount
        if (applyImageDiscount(customizations)) {
            apply("Foil Stamping Image Discount", "DISCOUNT_15_SQ_INCH");
        }

        postCalculate();

    }

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
                    BigDecimal unitPrice = pricingAttribute.getUnitPrice(pricingAttribute.getPriceMap().get(matchingQtyBreaks[0])[1], matchingQtyBreaks[0]);
                    BigDecimal totalPrice = unitPrice.multiply(new BigDecimal(quantity));
                    return new BigDecimal[] {unitPrice, totalPrice};
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

    private boolean applyImageDiscount(List<CustomizationOption> customizationOptions) {
        for(int i = 0; i < customizationOptions.size(); i ++) {
            if(customizationOptions.get(i).getCustomizationType() == CustomizationType.FOIL_STAMPING) {
                FoilStamping foilStamping = (FoilStamping) customizationOptions.get(i);
                for (FoilStamping.Side.Run run : foilStamping.getSide(0).getRuns()) {
                    for (FoilStamping.Side.Run.Image image : run.getImages()) {
                        if(image.getArea().equals("15")) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
