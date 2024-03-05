package com.bigname.marketplace.mirakl.client.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ofbiz.base.util.UtilValidate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Manu on 2/17/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MiraklCategory {
    public static final String ID = "categoryId";
    public static final String LABEL = "categoryLabel";
    public static final String DESCRIPTION = "categoryDescription";
    public static final String PARENT_CATEGORY_ID = "parentCategoryId";
    public static final String LOGISTIC_CLASS = "logisticClass";
    public static final String LEVEL = "level";

    @JsonIgnore
    private Map<String, Object> category;

    @JsonProperty("category_code")
    private String code;

    @JsonProperty("category_label")
    private String label;

    private String description;

    private String logisticClass;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogisticClass() {
        return logisticClass;
    }

    public void setLogisticClass(String logisticClass) {
        this.logisticClass = logisticClass;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Map<String, Object> getAsMap(boolean withId) {
        if(category == null) {
            category = new HashMap<>();
            if(withId) {
                category.put(ID, getCode());
            }
            category.put(LABEL, getLabel());
            if(UtilValidate.isNotEmpty(getParentCode())) {
                category.put(PARENT_CATEGORY_ID, getParentCode());
            }
            category.put(LOGISTIC_CLASS, getLogisticClass());
            category.put(DESCRIPTION, getDescription());
        }
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MiraklCategory)) return false;

        MiraklCategory that = (MiraklCategory) o;

        return getCode() != null ? getCode().equals(that.getCode()) : that.getCode() == null;

    }

    @Override
    public int hashCode() {
        return getCode() != null ? getCode().hashCode() : 0;
    }
}
