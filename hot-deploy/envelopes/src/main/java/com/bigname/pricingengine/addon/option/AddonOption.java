package com.bigname.pricingengine.addon.option;

import com.bigname.pricingengine.addon.option.impl.AddonOptionImpl;
import com.bigname.pricingengine.common.Config;

import java.util.List;
public interface AddonOption {
    List<AddonOptionImpl.Side> getSides();
    Config<String, Object> getConfig();

    String getType();
}
