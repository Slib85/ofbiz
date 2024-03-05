package com.envelopes.printing;

import java.io.Serializable;

/**
 * Created by Manu on 8/16/2016.
 */
public interface Press extends Serializable {
	void setPressId(long pressId);
	long getPressId();
	String getPressName();
	String getPressCode();
	double getMaxWidth();
	double getMinWidth();
	double getMaxHeight();
	double getMinHeight();
	double getGripperSize();
	double getImageGap();
	int getNumberOfColors();
	boolean hasWhiteInkSupport();
}
