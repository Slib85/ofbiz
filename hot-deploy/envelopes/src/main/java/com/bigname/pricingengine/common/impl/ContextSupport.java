package com.bigname.pricingengine.common.impl;

import com.bigname.pricingengine.common.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Manu on 2/7/2018.
 */
public class ContextSupport<K, V> extends AbstractMapSupport<K, V> implements Context<K, V> {


    public ContextSupport() {
        super();
    }

    @SuppressWarnings("unchecked")
    public ContextSupport(Map<K, V> configMap) {
        configMap.forEach((k, vl) -> {
            if(Map.class.isInstance(vl)) {
                Context<K, V> child = new ContextSupport<>((Map<K, V>)vl);
                put(k, (V)child);
            } else if(List.class.isInstance(vl) && !((List)vl).isEmpty() && Map.class.isInstance(((List)vl).get(0))) {
                final List<Context<K, V>> v1 = new ArrayList<>();
                ((List)vl).forEach(e -> v1.add(new ContextSupport<K, V>((Map)e)));
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
    public Context<K,V> getAsContext(K key) {
        Map<K, V> map = new HashMap<K, V>();
        map.put(key, (V)get(key));
        return new ContextSupport<>(map);
    }
}
