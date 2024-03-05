package com.bigname.search.elasticsearch.enums;

public enum SearchConditionOperator {
    OR("OR"),
    AND("AND");

    private final String type;

    SearchConditionOperator(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
