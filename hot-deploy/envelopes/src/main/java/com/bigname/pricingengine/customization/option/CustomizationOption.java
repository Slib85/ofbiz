package com.bigname.pricingengine.customization.option;

import com.bigname.pricingengine.common.Config;
import com.bigname.pricingengine.customization.CustomizationType;

import java.util.List;

public interface CustomizationOption {
    List<?> getSides();
    Object getSide(int index);
    CustomizationType getCustomizationType();
    Config<String, Object> getConfig();

}
