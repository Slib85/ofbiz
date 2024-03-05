package com.envelopes.label;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Manu on 8/1/2017.
 */
public class FolderSampleLabel {
    protected String jobNumber = "";
    protected String labelPath = "";
    protected String labelPDFPath = "";
    protected String relativeLabelPath = "";
    protected String relativeLabelPDFPath = "";
    protected int labelCopies = 1;
    protected Map<String, String> attributes = new HashMap<>();

    protected long lastModified = 0;

    public FolderSampleLabel(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public String getLabelPath() {
        if(labelPath.isEmpty()) {
            this.labelPath = LabelPrintHelper.FOLDER_SAMPLE_LABEL_FILE_LOCATION + jobNumber + ".png";
            this.relativeLabelPath = "folderSampleLabels/" + jobNumber + ".png";
        }
        return labelPath;
    }

    public void setLabelPath(String labelPath) {
        this.labelPath = labelPath;
    }

    public String getLabelPDFPath() {
        if(labelPDFPath.isEmpty()) {
            this.labelPDFPath = LabelPrintHelper.FOLDER_SAMPLE_LABEL_FILE_LOCATION + jobNumber + ".pdf";
            this.relativeLabelPDFPath = "folderSampleLabels/" + jobNumber + ".pdf";
        }
        return labelPDFPath;
    }

    public String getRelativeLabelPath() {
        if(relativeLabelPath.isEmpty()) {
            this.relativeLabelPath = "folderSampleLabels/" + jobNumber + ".png";
        }
        return relativeLabelPath;
    }

    public String getRelativeLabelPDFPath() {
        if(relativeLabelPDFPath.isEmpty()) {
            this.relativeLabelPDFPath = "folderSampleLabels/" + jobNumber + ".pdf";
        }
        return relativeLabelPDFPath;
    }

    public void setLabelPDFPath(String labelPDFPath) {
        this.labelPDFPath = labelPDFPath;
    }

    public int getLabelCopies() {
        return labelCopies;
    }

    public void setLabelCopies(int labelCopies) {
        this.labelCopies = labelCopies;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
