package com.envelopes.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Manu on 8/6/2014.
 */
public class HashMapSupport<K, V> extends HashMap<K, V> implements MapSupport<K, V> {
    public HashMapSupport(int initialCapacity, float loadFactor) { super(initialCapacity, loadFactor);}
    public HashMapSupport(int initialCapacity) { super(initialCapacity);}
    public HashMapSupport() { super();}
    public HashMapSupport(Map<? extends K, ? extends V> map) {super(map);}
}
