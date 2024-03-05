package com.bigname.pricingengine.request.impl;

import com.bigname.pricingengine.addon.option.AddonOption;
import com.bigname.pricingengine.addon.option.impl.AddonOptionImpl;
import com.bigname.pricingengine.common.Config;
import com.bigname.pricingengine.common.impl.ConfigSupport;
import com.bigname.pricingengine.customization.option.CustomizationOption;
import com.bigname.pricingengine.customization.option.impl.Embossing;
import com.bigname.pricingengine.customization.option.impl.FoilStamping;
import com.bigname.pricingengine.customization.option.impl.OffsetPrinting;
import com.bigname.pricingengine.request.PricingRequest;
import com.bigname.pricingengine.response.PricingResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DefaultPricingRequest implements PricingRequest {

    protected Config<String, Object> context;
    protected String vendorSku;
    protected String vendorId;
    protected String colorGroup;
    protected String colorName;
    protected String paperTexture;
    protected String paperWeight;
    protected String colorCode;
    protected String preferredVendorId;
    protected List<CustomizationOption> customizations = new ArrayList<>();
    protected List<AddonOption> addons = new ArrayList<>();
    protected int customQuantity;
    protected List<Integer> quantities = new ArrayList<>();
    protected PricingResponse.Type responseType = PricingResponse.Type.DEFAULT;


    public DefaultPricingRequest(Map<String, Object> pricingRequestMap) {
        this(new ConfigSupport<>(pricingRequestMap));
    }

    protected DefaultPricingRequest(Config<String, Object> context) {
        this.context = context;
        vendorSku = this.context.get("VENDOR_SKU", "");
        vendorId = this.context.get("VENDOR_ID", "");
        preferredVendorId = this.context.get("PREFERRED_VENDOR_ID", "");
        colorGroup = this.context.get("COLOR_GROUP", "");
        colorName = this.context.get("COLOR_NAME", "");
        paperTexture = this.context.get("PAPER_TEXTURE", "");
        paperWeight = this.context.get("PAPER_WEIGHT", "");
        colorCode = this.context.get("COLOR_CODE", "");
        customQuantity = this.context.get("CUSTOM_QUANTITY", 0.0).intValue();
        if(this.context.containsKey("QUANTITIES")) {
            this.context.get("QUANTITIES", new ArrayList<Double>()).forEach(e -> quantities.add(e.intValue()));
            Collections.sort(quantities);
        }
        customizations = Collections.unmodifiableList(getSelectedCustomizationOptions());
        addons = Collections.unmodifiableList(getSelectedAddons());
    }

    protected List<CustomizationOption> getSelectedCustomizationOptions() {
        List<CustomizationOption> customizationOptions = new ArrayList<>();
        context.get("CUSTOM_OPTIONS", new ArrayList<Config<String, Object>>()).forEach(e -> {
            switch(e.get("CUSTOM_OPTION_NAME").toString()) {
                case "OFFSET" :
                    customizationOptions.add(new OffsetPrinting(e));
                    break;
                case "FOIL_STAMPING":
                    customizationOptions.add(new FoilStamping(e));
                    break;
                case "EMBOSSING":
                    customizationOptions.add(new Embossing(e));
                    break;
            }
        });
        return customizationOptions;
    }

    protected List<AddonOption> getSelectedAddons() {
        List<AddonOption> addonOptions = new ArrayList<>();
        context.get("ADDONS_OPTIONS", new ArrayList<Config<String, Object>>()).forEach(e -> addonOptions.add(new AddonOptionImpl(e)));
        return  addonOptions;
    }

    @Override
    public Config<String, Object> getContext() {
        return context;
    }

    @Override
    public String getVendorSku() {
        return vendorSku;
    }

    @Override
    public String getVendorId() {
        return vendorId;
    }

    @Override
    public String getColorGroup() {
        return colorGroup;
    }

    @Override
    public String getColorName() {
        return colorName;
    }

    @Override
    public String getPaperTexture() {
        return paperTexture;
    }

    @Override
    public String getPaperWeight() {
        return paperWeight;
    }

    @Override
    public String getColorCode() {
        return colorCode;
    }

    @Override
    public String getPreferredVendorId() {
        return preferredVendorId;
    }

    @Override
    public int getCustomQuantity() {
        return customQuantity;
    }

    @Override
    public List<Integer> getQuantities() {
        return quantities;
    }

    public PricingResponse.Type getResponseType() {
        return responseType;
    }

    @Override
    public List<CustomizationOption> getCustomizations() {
        return customizations;
    }

    @Override
    public List<AddonOption> getAddons() {
        return addons;
    }
}
