package com.bigname.search.elasticsearch.enums;

import java.util.HashMap;
import java.util.Map;

public enum SearchSortType {
    AVGRATING("avgrating"),
    BASEPRICE("baseprice"),
    CREATEDSTAMP("createdstamp"),
    MEASUREMENT("measurement"),
    NUMOFREVIEWS("numofreviews"),
    PARENTID("parentid"),
    PRICE("price"),
    SALESRANK("salesrank"),
    SCORE("_score");

    private final String type;

    protected static final Map map = new HashMap<>();

    SearchSortType(final String type) {
        this.type = type;
    }

    static {
        for (SearchSortType searchSortType : SearchSortType.values()) {
            map.put(searchSortType.type, searchSortType);
        }
    }

    @Override
    public String toString() {
        return type;
    }

    public static SearchSortType getValueOf(String type) {
        SearchSortType searchSortType = (SearchSortType) map.get(type);
        return searchSortType;
    }
}