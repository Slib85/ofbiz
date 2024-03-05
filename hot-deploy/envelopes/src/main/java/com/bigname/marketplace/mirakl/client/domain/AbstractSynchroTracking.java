package com.bigname.marketplace.mirakl.client.domain;

/**
 * Created by Manu on 2/13/2017.
 */
public class AbstractSynchroTracking {
    private String synchroId;

    public String getSynchroId() {
        return synchroId;
    }

    public void setSynchroId(String synchroId) {
        this.synchroId = synchroId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AbstractSynchroTracking that = (AbstractSynchroTracking) o;

        return synchroId != null ? synchroId.equals(that.synchroId) : that.synchroId == null;

    }

    @Override
    public int hashCode() {
        return synchroId != null ? synchroId.hashCode() : 0;
    }
}
