package com.bigname.pricingengine.common;

import java.util.Map;

public interface Config<K,V> extends Map<K,V> {
    <T> T get(K key, T defaultValue);
    Config<K,V> getAsConfig(K key);
}
