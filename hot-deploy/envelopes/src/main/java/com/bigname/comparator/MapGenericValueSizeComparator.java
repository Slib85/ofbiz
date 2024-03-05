package com.bigname.comparator;

import com.bigname.search.elasticsearch.util.SearchUtil;
import org.apache.ofbiz.entity.GenericValue;

import java.util.Comparator;
import java.util.Map;

public class MapGenericValueSizeComparator implements Comparator<GenericValue> {
    public static final String module = MapGenericValueSizeComparator.class.getName();

    private static final int MAX_DIMENSIONS = 3; //2 dimensional or 3 dimensional products
    private boolean ascOrder = true;
    private Object objectType = Map.class;

    /**
     * Sort ascending or descending order
     * @param ascending
     */
    public MapGenericValueSizeComparator(boolean ascending) {
        this.ascOrder = ascending;
    }

    @Override
    public int compare(GenericValue gv1, GenericValue gv2) {
        int result = -1;
        String size1 = "";
        String size2 = "";

        if(gv1 != null && "SearchFacet".equalsIgnoreCase(gv1.getEntityName())) {
            size1 = (gv1 == null) ? "" : (gv1.get("facetName") == null) ? "" : gv1.getString("facetName");
            size2 = (gv2 == null) ? "" : (gv2.get("facetName") == null) ? "" : gv2.getString("facetName");
        }

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
