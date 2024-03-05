package com.bigname.search.elasticsearch.util;

import org.apache.ofbiz.base.util.UtilMisc;

import java.util.HashMap;
import java.util.Map;

public class LegacyUtil {
    public static final String module = LegacyUtil.class.getName();

    public static Map<String, String> legacyFilters = new HashMap<>();
    public static Map<String, Map<String, String>> legacyFacets = new HashMap<>();

    static {
        //key: old filter id
        //value: new filter id
        legacyFilters.put("onsale", "sale");

        //key: filter id
        //value: map of old facet id to new facet id
        legacyFacets.put("sale", UtilMisc.toMap("onsale", "sale"));
    }

    /**
     * Convert legacy filter id to new filter id
     * @param filterId
     * @return
     */
    public static String getNewFilterId(String filterId) {
        if(legacyFilters.containsKey(filterId)) {
            return legacyFilters.get(filterId);
        }

        return filterId;
    }
}
