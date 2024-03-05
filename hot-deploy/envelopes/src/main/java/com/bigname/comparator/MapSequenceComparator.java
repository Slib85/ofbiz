package com.bigname.comparator;

import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.commons.lang.math.NumberUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Given a map of string, object
 * resort on the object and key sequenceNum
 */
public class MapSequenceComparator implements Comparator<String> {
    public static final String module = MapSequenceComparator.class.getName();

    Map<String, Object> map = new HashMap<>();
    private boolean ascOrder = true;

    /**
     * Sort ascending or descending order
     * @param ascending
     */
    public MapSequenceComparator(boolean ascending, Map<String, Object> map){
        this.ascOrder = ascending;
        this.map = map;
    }

    @Override
    public int compare(String str1, String str2) {
        String sequence1 = ((String) ((Map) this.map.get(str1)).get("sequenceNum"));
        sequence1 = UtilValidate.isNotEmpty(sequence1) ? sequence1 : "5000000";

        String sequence2 = ((String) ((Map) this.map.get(str2)).get("sequenceNum"));
        sequence2 = UtilValidate.isNotEmpty(sequence2) ? sequence2 : "5000000";

        if(NumberUtils.toInt(sequence1) >= (NumberUtils.toInt(sequence2))) {
            return this.ascOrder ? 1 : -1;
        } else {
            return this.ascOrder ? -1 : 1;
        }
    }
}