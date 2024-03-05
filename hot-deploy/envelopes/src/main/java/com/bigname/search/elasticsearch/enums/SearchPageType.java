package com.bigname.search.elasticsearch.enums;

import java.util.HashMap;
import java.util.Map;

public enum SearchPageType {
    SEARCH_PAGE("SEARCH_PAGE"),
    CATEGORY_PAGE("CATEGORY_PAGE"),
    ANY_PAGE("ANY_PAGE");

    private final String type;
    private static Map map = new HashMap<>();

    SearchPageType(final String type) {
        this.type = type;
    }

    static {
        for (SearchPageType searchPageType : SearchPageType.values()) {
            map.put(searchPageType.type, searchPageType);
        }
    }

    @Override
    public String toString() {
        return type;
    }

    public static SearchPageType getValueOf(String type) {
        SearchPageType searchpageType = (SearchPageType) map.get(type);
        if (searchpageType == null) {
            return null;
        }

        return searchpageType;
    }
}