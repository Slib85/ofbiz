package com.bigname.search.comparator;

import com.bigname.search.elasticsearch.util.SearchUtil;
import org.elasticsearch.search.SearchHit;

import java.util.Comparator;

public class SizeSortComparator implements Comparator<SearchHit> {
    public static final String module = SizeSortComparator.class.getName();

    private static final int MAX_DIMENSIONS = 3; //2 dimensional or 3 dimensional products
    private boolean ascOrder = true;

    /**
     * Sort ascending or descending order
     * @param ascending
     */
    public SizeSortComparator(boolean ascending) {
        this.ascOrder = ascending;
    }

    @Override
    public int compare(SearchHit hit1, SearchHit hit2) {
        String size1 = (hit1 == null) ? "" : (hit1.getSourceAsMap().get("size") == null) ? "" : (String) hit1.getSourceAsMap().get("size");
        String size2 = (hit2 == null) ? "" : (hit2.getSourceAsMap().get("size") == null) ? "" : (String) hit2.getSourceAsMap().get("size");

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
        return -1;
    }
}
