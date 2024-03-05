package com.bigname.marketplace.mirakl.client.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.ofbiz.entity.GenericValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Manu on 2/14/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MiraklProductAttribute {
    public static final String ID = "attributeId";
    public static final String LABEL = "attributeLabel";
    public static final String REQUIRED = "required";
    public static final String TYPE = "type";

    @JsonIgnore
    private Map<String, Object> attribute;

    private String code;

    private String label;

    private boolean required;

    private String type;

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

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getAsMap(boolean withId) {
        if(attribute == null) {
            attribute = new HashMap<>();
            if(withId) {
                attribute.put(ID, getCode());
            }
            attribute.put(LABEL, getLabel());
            attribute.put(REQUIRED, isRequired());
            attribute.put(TYPE, getType());
        }
        return attribute;
    }

    public boolean equals(GenericValue attribute) {
        if(attribute == null) {
            return false;
        }

        return attribute.getString(ID).equals(this.getCode()) && attribute.getString(LABEL).equals(this.getLabel()) &&
                attribute.getBoolean(REQUIRED) == this.isRequired() && attribute.getString(TYPE).equals(this.getType());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MiraklProductAttribute)) return false;

        MiraklProductAttribute that = (MiraklProductAttribute) o;

        return getCode() != null ? getCode().equals(that.getCode()) : that.getCode() == null;

    }

    @Override
    public int hashCode() {
        return getCode() != null ? getCode().hashCode() : 0;
    }
}
