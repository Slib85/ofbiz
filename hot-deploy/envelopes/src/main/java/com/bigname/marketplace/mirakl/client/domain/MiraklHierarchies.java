package com.bigname.marketplace.mirakl.client.domain;

import java.util.List;

/**
 * Created by Manu on 2/11/2017.
 */
public class MiraklHierarchies {
    private List<MiraklHierarchy> hierarchies;

    public List<MiraklHierarchy> getHierarchies() {
        return hierarchies;
    }

    public void setHierarchies(List<MiraklHierarchy> hierarchies) {
        this.hierarchies = hierarchies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MiraklHierarchies that = (MiraklHierarchies) o;

        if (hierarchies != null ? !hierarchies.equals(that.hierarchies) : that.hierarchies != null) {
            return false;
        }
        return hierarchies.size() == that.hierarchies.size();

    }

    @Override
    public int hashCode() {
        int result = hierarchies != null ? hierarchies.hashCode() : 0;
        result = 31 * result + hierarchies.size();
        return result;
    }
}
