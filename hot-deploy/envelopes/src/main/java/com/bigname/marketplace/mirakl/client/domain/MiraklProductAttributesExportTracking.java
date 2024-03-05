package com.bigname.marketplace.mirakl.client.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Manu on 2/14/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MiraklProductAttributesExportTracking {
    @JsonProperty("attributes_import_id")
    private int exportId;

    public int getExportId() {
        return exportId;
    }

    public void setExportId(int exportId) {
        this.exportId = exportId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MiraklProductAttributesExportTracking that = (MiraklProductAttributesExportTracking) o;

        return exportId == that.exportId;

    }

    @Override
    public int hashCode() {
        return exportId;
    }
}
