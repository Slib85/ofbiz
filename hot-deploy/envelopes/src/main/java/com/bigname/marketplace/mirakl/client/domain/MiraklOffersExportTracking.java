package com.bigname.marketplace.mirakl.client.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Manu on 3/7/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MiraklOffersExportTracking {
    @JsonProperty("import_id")
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

        MiraklOffersExportTracking that = (MiraklOffersExportTracking) o;

        return exportId == that.exportId;

    }

    @Override
    public int hashCode() {
        return exportId;
    }
}
