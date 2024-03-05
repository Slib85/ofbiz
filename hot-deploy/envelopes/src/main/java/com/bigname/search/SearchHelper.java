package com.bigname.search;

import org.elasticsearch.search.SearchHit;
import org.apache.ofbiz.base.util.UtilValidate;
import java.util.*;

/**
 * Created by shoabkhan on 5/6/17.
 */
public class SearchHelper {
    public static final String module = SearchHelper.class.getName();

    public static Map<String, Object> createCategoryMap(ArrayList<SearchHit> searchData) {
        Map<String, Object> sortedDataSet = new HashMap<>();
        
        for (SearchHit data : searchData) {
            Map<String, Object> source = data.getSourceAsMap();
            String productId = (String) source.get("productid");
            String style = ((ArrayList<String>) source.get("productstyle")).get(0);
            String size = (String) source.get("size");
            String name = ((String) source.get("name")).replaceAll("(?:\\s+\\(" + size + "\\)$|^" + size + "\\s+)", "");
            
            Map<String, Object> nameAndSizeMap = (UtilValidate.isNotEmpty((Map<String, Object>) sortedDataSet.get(style)) && UtilValidate.isNotEmpty(((Map<String, Object>) sortedDataSet.get(style)).get(size)) ? (Map<String, Object>) ((Map<String, Object>) sortedDataSet.get(style)).get(size) : new HashMap<String, Object>());
            nameAndSizeMap.put(productId, source);
            Map<String, Object> styleMap = (UtilValidate.isNotEmpty((Map<String, Object>) sortedDataSet.get(style)) ? (Map<String, Object>) sortedDataSet.get(style) : new HashMap<String, Object>());
            styleMap.put(size, nameAndSizeMap);
            sortedDataSet.put(style, styleMap);
        }

        return sortedDataSet;
    }

    /**
     * Get the param value from the url
     * Used incase the same param value exists multiple times in the url
     * @param param
     * @return
     */
    public static String getUrlParameter(Object param) {
        return (param instanceof List) ? (String) ((List) param).get(0) : (String) param;
    }

    /**
     * Remove lists from map values
     * @param context
     * @return
     */
    public static Map<String, Object> removeListValues(Map<String, Object> context) {
        for (Map.Entry<String, Object> entry : context.entrySet()) {
            if(entry.getValue() instanceof List) {
                entry.setValue((String) ((List) entry.getValue()).get(0));
            }
        }

        return context;
    }
}