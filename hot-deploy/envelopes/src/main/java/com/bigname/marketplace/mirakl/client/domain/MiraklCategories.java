package com.bigname.marketplace.mirakl.client.domain;

import java.util.List;

/**
 * Created by Manu on 2/17/2017.
 */
public class MiraklCategories {
    private List<MiraklCategory> categories;

    public List<MiraklCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<MiraklCategory> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MiraklCategories that = (MiraklCategories) o;

        if (categories != null ? !categories.equals(that.categories) : that.categories != null) {
            return false;
        }
        return categories.size() == that.categories.size();

    }

    @Override
    public int hashCode() {
        int result = categories != null ? categories.hashCode() : 0;
        result = 31 * result + categories.size();
        return result;
    }
}
