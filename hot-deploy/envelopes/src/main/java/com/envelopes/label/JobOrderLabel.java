package com.envelopes.label;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manu on 8/1/2017.
 */
public class JobOrderLabel {
    protected String jobNumber;
    protected List<FolderSampleLabel> labels = new ArrayList<>();

    public JobOrderLabel(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public List<FolderSampleLabel> getLabels() {
        return labels;
    }
}
