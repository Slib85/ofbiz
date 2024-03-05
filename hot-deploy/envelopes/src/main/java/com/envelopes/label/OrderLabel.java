package com.envelopes.label;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manu on 8/2/2016.
 */
public class OrderLabel {
	protected String orderId;
	protected boolean miniLabel;
	protected List<ProductLabel> labels = new ArrayList<>();

	public OrderLabel(String orderId, boolean miniLabel) {
		this.orderId = orderId;
		this.miniLabel = miniLabel;
	}

	public String getOrderId() {
		return orderId;
	}

	public boolean isMiniLabel() {
		return miniLabel;
	}

	public List<ProductLabel> getLabels() {
		return labels;
	}
}
