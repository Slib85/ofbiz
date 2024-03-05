package com.envelopes.refinements.model;

import com.envelopes.common.HashMapSupport;
import com.envelopes.refinements.comparator.GenericAlphabeticComparator;
import com.envelopes.refinements.comparator.SizeFilterComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Manu on 8/6/2014.
 */
public class Facet extends HashMapSupport {
    private String facetId;
    private String facetName;
    private List<Filter> filters = new ArrayList<>();

    public Facet(Map sliFacet) {
        super(sliFacet);
        this.facetId = (String) get("id");
        this.facetName = (String) get("name");
        List<Map<String, Object>> sliFilters = (List<Map<String, Object>>)get("values");
        for (Map<String, Object> sliFilter : sliFilters) {
            this.filters.add(new Filter(sliFilter));
        }
		if(this.facetId.equals("si")) {
			Collections.sort(this.filters, new SizeFilterComparator());
		} else if(this.facetId.equals("st") || this.facetId.equals("cog1") || this.facetId.equals("cog2")
				|| this.facetId.equals("finish") || this.facetId.equals("col") || this.facetId.equals("sm")
				|| this.facetId.equals("industry") || this.facetId.equals("theme") || this.facetId.equals("prodType")) {
			Collections.sort(this.filters, new GenericAlphabeticComparator());
		}
    }

    public String getFacetId() {
        return facetId;
    }

    public String getFacetName() {
        return facetName;
    }

    public List<Filter> getFilters() {
        return filters;
    }
}
