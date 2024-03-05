package com.bigname.pricingengine.common.impl;

import com.bigname.pricingengine.common.Config;

import java.util.*;

public class ConfigSupport<K, V> extends AbstractMapSupport<K, V> implements Config<K, V> {


    public ConfigSupport() {
        super();
    }

    @SuppressWarnings("unchecked")
    public ConfigSupport(Map<K, V> configMap) {
        configMap.forEach((k, vl) -> {
            if(Map.class.isInstance(vl)) {
                Config<K, V> child = new ConfigSupport<>((Map<K, V>)vl);
                put(k, (V)child);
            } else if(List.class.isInstance(vl) && !((List)vl).isEmpty() && Map.class.isInstance(((List)vl).get(0))) {
                final List<Config<K, V>> v1 = new ArrayList<>();
                ((List)vl).forEach(e -> v1.add(new ConfigSupport<K, V>((Map)e)));
                put(k, (V) v1);
            } else {
                put(k, vl);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> T get(K key, T defaultValue) {
        return get(key) != null && !get(key).equals("") ?  (T) get(key) : defaultValue;
    }

    @SuppressWarnings("unchecked")
    public Config<K,V> getAsConfig(K key) {
        Map<K, V> map = new HashMap<K, V>();
        map.put(key, (V)get(key));
        return new ConfigSupport<>(map);
    }
}
