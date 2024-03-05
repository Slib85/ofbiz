package com.envelopes.label;

import org.apache.ofbiz.base.util.UtilValidate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Manu on 8/2/2016.
 */
public class ProductLabel {
	protected String productId = "";
	protected String workOrderId = "";
	protected String labelQty = "";
	protected boolean defaultLabel = false;
	protected boolean miniLabel = false;
	protected boolean packLabel = false;
	protected boolean samplesFlag = false;
	protected boolean isWorkOrder = false;
	protected String labelPath = "";
	protected String labelPDFPath = "";
	protected String relativeLabelPath = "";
	protected String relativeLabelPDFPath = "";
	protected int labelCopies = 1;
	protected Map<String, String> attributes = new HashMap<>();

	protected long lastModified = 0;

	public ProductLabel(String productId, boolean miniLabel, String workOrderId) {
		this.productId = productId;
		this.miniLabel = miniLabel;
		if(!miniLabel) {
			this.workOrderId = workOrderId.trim();
			this.isWorkOrder = UtilValidate.isNotEmpty(workOrderId.trim());
		}
	}

	public String getProductId() {
		return productId;
	}

	public String getLabelQty() {
		return labelQty;
	}

	public void setLabelQty(String labelQty) {
		this.labelQty = labelQty;
	}

	public boolean isDefaultLabel() {
		return defaultLabel;
	}

	public void setDefaultLabel(boolean defaultLabel) {
		this.defaultLabel = defaultLabel;
	}

	public boolean isMiniLabel() {
		return miniLabel;
	}

	public boolean isPackLabel() {
		return packLabel;
	}

	public boolean isSamplesFlag() {
		return samplesFlag;
	}

	public void setSamplesFlag(boolean samplesFlag) {
		this.samplesFlag = samplesFlag;
	}

	public void setPackLabel(boolean packLabel) {
		this.packLabel = packLabel;
	}

	public String getLabelPath() {
		if(labelPath.isEmpty()) {
			if(miniLabel) {
				this.labelPath = LabelPrintHelper.MINI_LABEL_FILE_LOCATION + productId + (labelQty.isEmpty() ? "" : "-" + labelQty) + ".png";
				this.relativeLabelPath = "miniLabels/" + productId + (labelQty.isEmpty() ? "" : "-" + labelQty) + ".png";
			} else if(packLabel) {
				this.labelPath = LabelPrintHelper.PACK_LABEL_FILE_LOCATION + (isWorkOrder ? workOrderId : productId) + (labelQty.isEmpty() ? "" : "-" + labelQty) + ".png";
				this.relativeLabelPath = "packLabels/" + (isWorkOrder ? workOrderId : productId) + (labelQty.isEmpty() ? "" : "-" + labelQty) + ".png";
			} else {
				this.labelPath = LabelPrintHelper.LABEL_FILE_LOCATION + (isWorkOrder ? workOrderId : productId) + ".png";
				this.relativeLabelPath = "" + (isWorkOrder ? workOrderId : productId) + ".png";
			}
		}
		return labelPath;
	}

	public void setLabelPath(String labelPath) {
		this.labelPath = labelPath;
	}

	public String getLabelPDFPath() {
		if(labelPDFPath.isEmpty()) {
			if(miniLabel) {
				this.labelPDFPath = LabelPrintHelper.MINI_LABEL_FILE_LOCATION + productId + (labelQty.isEmpty() ? "" : "-" + labelQty) + ".pdf";
				this.relativeLabelPDFPath = "miniLabels/" + productId + (labelQty.isEmpty() ? "" : "-" + labelQty) + ".pdf";
			} else if(packLabel) {
				this.labelPDFPath = LabelPrintHelper.PACK_LABEL_FILE_LOCATION + (isWorkOrder ? workOrderId : productId) + (labelQty.isEmpty() ? "" : "-" + labelQty) + ".pdf";
				this.relativeLabelPDFPath = "packLabels/" + (isWorkOrder ? workOrderId : productId) + (labelQty.isEmpty() ? "" : "-" + labelQty) + ".pdf";
			} else {
				this.labelPDFPath = LabelPrintHelper.LABEL_FILE_LOCATION + (isWorkOrder ? workOrderId : productId)  + ".pdf";
				this.relativeLabelPDFPath = "" + (isWorkOrder ? workOrderId : productId) + ".pdf";
			}
		}
		return labelPDFPath;
	}

	public String getRelativeLabelPath() {
		if(relativeLabelPath.isEmpty()) {
			if(miniLabel) {
				this.relativeLabelPath = "miniLabels/" + productId + (labelQty.isEmpty() ? "" : "-" + labelQty) + ".png";
			} else if(packLabel) {
				this.relativeLabelPath = "packLabels/" + (isWorkOrder ? workOrderId : productId) + (labelQty.isEmpty() ? "" : "-" + labelQty) + ".png";
			} else {
				this.relativeLabelPath = "" + (isWorkOrder ? workOrderId : productId) + ".png";
			}
		}
		return relativeLabelPath;
	}

	public String getRelativeLabelPDFPath() {
		if(relativeLabelPDFPath.isEmpty()) {
			if(miniLabel) {
				this.relativeLabelPDFPath = "miniLabels/" + productId + (labelQty.isEmpty() ? "" : "-" + labelQty) + ".pdf";
			} else if(packLabel) {
				this.relativeLabelPDFPath = "packLabels/" + (isWorkOrder ? workOrderId : productId) + (labelQty.isEmpty() ? "" : "-" + labelQty) + ".pdf";
			} else {
				this.relativeLabelPDFPath = "" + (isWorkOrder ? workOrderId : productId) + ".pdf";
			}
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

	public String getProductIdWithQty() {
		return getProductId() + (!getLabelQty().isEmpty() ? "-" + getLabelQty() : "");
	}

	public String getWorkOrderIdWithQty() {
		return getWorkOrderId() + (!getLabelQty().isEmpty() ? "-" + getLabelQty() : "");
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public String getWorkOrderId() {
		return workOrderId;
	}

	public boolean isWorkOrder() {
		return isWorkOrder;
	}
}
