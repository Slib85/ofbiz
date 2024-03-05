package com.bigname.marketplace.mirakl.client.domain;

import java.util.List;

/**
 * Created by Manu on 2/14/2017.
 */
public class MiraklProductAttributes {
    private List<MiraklProductAttribute> attributes;

    public List<MiraklProductAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<MiraklProductAttribute> attributes) {
        this.attributes = attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MiraklProductAttributes that = (MiraklProductAttributes) o;

        if (attributes != null ? !attributes.equals(that.attributes) : that.attributes != null) {
            return false;
        }
        return attributes.size() == that.attributes.size();

    }

    @Override
    public int hashCode() {
        int result = attributes != null ? attributes.hashCode() : 0;
        result = 31 * result + attributes.size();
        return result;
    }
}
