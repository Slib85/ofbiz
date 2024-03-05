package com.envelopes.refinements.comparator;

import com.envelopes.refinements.model.SLIResponse;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by Manu on 8/5/2014.
 */
public class ParentIdComparator implements Comparator<Map<String, Object>> {
    @Override
    public int compare(Map<String, Object> item1, Map<String, Object> item2) {
        return getValidatedParentId(item1).compareTo(getValidatedParentId(item2));
    }

    protected static String getValidatedParentId(Map<String, Object> item) {
        String parentId = null;
        if(item.containsKey(SLIResponse.Attribute.PARENT_ID.getSliAttribute())) {
            parentId = (String)item.get(SLIResponse.Attribute.PARENT_ID.getSliAttribute());
        }
        if(parentId == null || parentId.equalsIgnoreCase("null")) {
            parentId = "UNKNOWN";
        }
        return parentId;
    }
}
