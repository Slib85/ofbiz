package com.bigname.pricingengine.common;

import java.util.Map;

public interface Context<K,V> extends Map<K,V> {
    <T> T get(K key, T defaultValue);
    Context<K,V> getAsContext(K key);
}
