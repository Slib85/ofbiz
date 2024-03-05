package com.bigname.pricingengine.customization.option.impl;

import com.bigname.pricingengine.common.Config;
import com.bigname.pricingengine.customization.CustomizationType;
import com.bigname.pricingengine.customization.option.CustomizationOption;

import java.util.ArrayList;
import java.util.List;

import static com.bigname.pricingengine.customization.option.impl.OffsetPrinting.Method.PMS;


public class OffsetPrinting implements CustomizationOption {

    public enum Method {PMS, FOUR_COLOR}
    private List<Side> sides = new ArrayList<>();
    private Config<String, Object> config;
    private CustomizationType customizationType = CustomizationType.OFFSET_PRINTING;
    public OffsetPrinting(Config<String, Object> offsetConfig) {
        this.config = offsetConfig;
        int index = -1;
        for (Config<String, Object> config : offsetConfig.get("SIDES", new ArrayList<Config<String, Object>>())) {
            String method = config.get("PRINT_METHOD", "");
            int numberOfInks = config.get("NUMBER_OF_INKS", 0.0).intValue();
            boolean heavyInkCoverage = config.get("HEAVY_COVERAGE", "").equalsIgnoreCase("Y");
            int colorWashes = config.get("COLOR_WASHES", 0.0).intValue();
            int plateChanges = config.get("PLATE_CHANGES", 0.0).intValue();
            sides.add(new Side(method, numberOfInks, heavyInkCoverage, colorWashes, plateChanges, ++index));
        }


    }

    @Override
    public List<Side> getSides() {
        return sides;
    }

    @Override
    public Object getSide(int index) {
        return sides.size() > index ? sides.get(index) : null;
    }

    @Override
    public CustomizationType getCustomizationType() {
        return customizationType;
    }

    @Override
    public Config<String, Object> getConfig() {
        return config;
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof OffsetPrinting;

    }

    @Override
    public int hashCode() {
        return 0;
    }

    public class Side {
        private Method method = PMS;
        private  int numberOfInks = 0;
        private boolean heavyInkCoverage;
        private int colorWashes = 0;
        private int plateChanges = 0;
        private int index = -1;

        public Method getMethod() {
            return method;
        }

        public int getNumberOfInks() {
            return numberOfInks;
        }

        public boolean isHeavyInkCoverage() {
            return heavyInkCoverage;
        }

        public int getColorWashes() {
            return colorWashes;
        }

        public int getPlateChanges() {
            return plateChanges;
        }

        public int getIndex() {
            return index;
        }

        private Side(String method, int numberOfInks, boolean heavyInkCoverage, int colorWashes, int plateChanges, int index) {
            this.method = Method.valueOf(method);
            this.numberOfInks = numberOfInks;
            this.heavyInkCoverage = heavyInkCoverage;
            this.colorWashes = colorWashes;
            this.plateChanges = plateChanges;
            this.index = index;
        }
    }

}
