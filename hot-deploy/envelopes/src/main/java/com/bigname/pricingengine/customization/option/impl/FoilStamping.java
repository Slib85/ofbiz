package com.bigname.pricingengine.customization.option.impl;

import com.bigname.pricingengine.common.Config;
import com.bigname.pricingengine.customization.CustomizationType;
import com.bigname.pricingengine.customization.option.CustomizationOption;

import java.util.ArrayList;
import java.util.List;

public class FoilStamping implements CustomizationOption {

    private List<Side> sides = new ArrayList<>();
    private Config<String, Object> config;
    private CustomizationType customizationType = CustomizationType.FOIL_STAMPING;

    public FoilStamping(Config<String, Object> foilStampingConfig) {
        this.config = foilStampingConfig;
        sides.add(new Side(foilStampingConfig.getAsConfig("RUNS"), 1));
    }
    @Override
    public List<Side> getSides() {
        return sides;
    }

    @Override
    public Side getSide(int index) {
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
        return o != null && o instanceof FoilStamping;

    }

    @Override
    public int hashCode() {
        return 0;
    }

    public class Side {
        private List<Run> runs = new ArrayList<>();
        private Config<String, Object> config;
        private int index = -1;
        public Side(Config<String, Object> sideConfig, int index) {
            this.config = sideConfig;
            this.index = index;
            int index1 = -1;
            for (Config<String, Object> config : sideConfig.get("RUNS", new ArrayList<Config<String, Object>>())) {
                runs.add(new Run(config, ++index1));

            }
        }

        public List<Run> getRuns() {
            return runs;
        }

        public int getIndex() {
            return index;
        }

        public Config<String, Object> getConfig() {
            return config;
        }
        public class Run {

            private List<Image> images = new ArrayList<>();
            private int index = -1;
            private Config<String, Object> config;

            public Run(Config<String, Object> runConfig, int index) {
                this.config = runConfig;
                int index2 = -1;
                for (String config : runConfig.get("IMAGES", new ArrayList<String>())) {
                    images.add(new Image(config, ++index2));
                    this.index = index;
                }

            }

            public List<Image> getImages() {
                return images;
            }

            public int getIndex() {
                return index;
            }

            public Config<String, Object> getConfig() {
                return config;
            }

            public class Image {
                private String area;
                private int index = -1;

                public Image(String area, int index) {
                    this.area = area;
                    this.index = index;
                }

                public String getArea() {
                    return area;
                }

                public int getIndex() {
                    return index;
                }
            }
        }
    }
}
