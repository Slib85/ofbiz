package com.bigname.marketplace.mirakl.client.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.GenericValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Manu on 2/2/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MiraklHierarchy {

    public static final String ID = "categoryId";
    public static final String LABEL = "categoryLabel";
    public static final String PARENT_CATEGORY_ID = "parentCategoryId";
    public static final String LEVEL = "level";

    @JsonIgnore
    private Map<String, Object> hierarchy;

    private String code;

    private String label;

    private long level;

    private String parentCode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Map<String, Object> getAsMap(boolean withId) {
        if(hierarchy == null) {
            hierarchy = new HashMap<>();
            if(withId) {
                hierarchy.put(ID, getCode());
            }
            hierarchy.put(LABEL, getLabel());
            if(UtilValidate.isNotEmpty(getParentCode())) {
                hierarchy.put(PARENT_CATEGORY_ID, getParentCode());
            }
            hierarchy.put(LEVEL, getLevel());
        }
        return hierarchy;
    }

    public boolean equals(GenericValue hierarchy) {
        if(hierarchy == null) {
            return false;
        }

        if(hierarchy.getString(ID).equals(this.getCode()) && hierarchy.getString(LABEL).equals(this.getLabel())) {
            String parentCategoryId = "";
            long level = 0;
            if(UtilValidate.isNotEmpty(hierarchy.get(PARENT_CATEGORY_ID))) {
                parentCategoryId = hierarchy.getString(PARENT_CATEGORY_ID);
            }
            if(hierarchy.get(LEVEL) != null) {
                try {
                    level = hierarchy.getLong(LEVEL);
                } catch (Exception ignore) { }
            }
            if(parentCategoryId.equals(this.getParentCode()) && level == this.getLevel()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MiraklHierarchy)) return false;

        MiraklHierarchy that = (MiraklHierarchy) o;

        return getCode() != null ? getCode().equals(that.getCode()) : that.getCode() == null;

    }

    @Override
    public int hashCode() {
        return getCode() != null ? getCode().hashCode() : 0;
    }
}
