package com.bigname.comparator;

import com.bigname.search.elasticsearch.util.SearchUtil;
import org.apache.ofbiz.entity.GenericValue;

import java.util.Comparator;
import java.util.Map;

public class ListStringSizeComparator implements Comparator<String> {
    public static final String module = ListStringSizeComparator.class.getName();

    private static final int MAX_DIMENSIONS = 3; //2 dimensional or 3 dimensional products
    private boolean ascOrder = true;
    private Object objectType = Map.class;

    /**
     * Sort ascending or descending order
     * @param ascending
     */
    public ListStringSizeComparator(boolean ascending) {
        this.ascOrder = ascending;
    }

    @Override
    public int compare(String size1, String size2) {
        int result = 0;
        size1 = (size1 == null) ? "" : size1;
        size2 = (size2 == null) ? "" : size2;

        String[] size1DimensionList = size1.split("(?i)x");
        String[] size2DimensionList = size2.split("(?i)x");
        Float[] size1DimensionListF = new Float[MAX_DIMENSIONS];
        Float[] size2DimensionListF = new Float[MAX_DIMENSIONS];

        for(int i = 0; i < MAX_DIMENSIONS; i++) {
            size1DimensionListF[i] = (i < size1DimensionList.length) ? SearchUtil.convertToFloat(size1DimensionList[i]) : 0f;
            size2DimensionListF[i] = (i < size2DimensionList.length) ? SearchUtil.convertToFloat(size2DimensionList[i]) : 0f;

            if(size1DimensionListF[i].compareTo(size2DimensionListF[i]) != 0) {
                return this.ascOrder ? size1DimensionListF[i].compareTo(size2DimensionListF[i]) : size1DimensionListF[i].compareTo(size2DimensionListF[i]) * -1;
            }
        }

        return result;
    }
}
