package com.envelopes.refinements.model;

import com.envelopes.common.HashMapSupport;

import java.util.Map;

/**
 * Created by Manu on 8/6/2014.
 */
public class Filter extends HashMapSupport {
    private String filterId;
    private String filterName;

    public Filter(Map<String, Object> sliFilter) {
        super(sliFilter);
        this.filterId = (String)get("id");
        this.filterName = (String)get("name");
    }

    public String getFilterId() {
        return filterId;
    }

    public String getFilterName() {
        return filterName;
    }
}
