package com.bigname.pricingengine.request;

import com.bigname.pricingengine.addon.option.AddonOption;
import com.bigname.pricingengine.common.Config;
import com.bigname.pricingengine.customization.option.CustomizationOption;

import java.util.List;

public interface PricingRequest {
    Config<String, Object> getContext();

    String getVendorSku();

    String getVendorId();

    String getColorGroup();

    String getColorName();

    String getPaperTexture();

    String getPaperWeight();

    String getColorCode();

    String getPreferredVendorId();

    List<Integer> getQuantities();

    List<CustomizationOption> getCustomizations();

    List<AddonOption> getAddons();

    int getCustomQuantity();

}
