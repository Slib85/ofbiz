package com.bigname.pricingengine.addon.option.impl;

import com.bigname.pricingengine.addon.option.AddonOption;
import com.bigname.pricingengine.common.Config;

import java.util.ArrayList;
import java.util.List;

public class AddonOptionImpl implements AddonOption {
    private String type;
    private List<Side> sides = new ArrayList<>();
    private Config<String, Object> config;

    public AddonOptionImpl(Config<String, Object> addonsConfig) {
        this.config = addonsConfig;
        this.type = addonsConfig.get("ADDON_TYPE", "");
        int index1 = -1;
        for (List<String> side : addonsConfig.get("SIDES", new ArrayList<List<String>>())) {
            sides.add(new Side(side, ++ index1));
        }
    }

    @Override
    public List<Side> getSides() {
        return sides;
    }

    @Override
    public Config<String, Object> getConfig() {
        return config;
    }

    @Override
    public String getType() {
        return type;
    }

    public class Side {
        private List<Addon> addons = new ArrayList<>();
        private int index = -1;
        public Side(List<String> sideConfig, int index) {
            this.index = index;
            int index2 = -1;
            for (String addon : sideConfig) {
                addons.add(new Addon(addon, ++ index2));
            }
        }

        public List<Addon> getAddons() {
            return addons;
        }

        public Config<String, Object> getConfig() {
            return config;
        }

        public int getIndex() {
            return index;
        }

        public class Addon {
            private String id;
            private int index = -1;
            public Addon(String id, int index) {
                this.id = id;
                this.index = index;
            }

            public String getId() {
                return id;
            }

            public int getIndex() {
                return index;
            }
        }
    }
}
