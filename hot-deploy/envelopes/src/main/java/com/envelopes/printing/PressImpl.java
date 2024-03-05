package com.envelopes.printing;

import java.util.Objects;

/**
 * Created by Manu on 8/16/2016.
 */
public class PressImpl implements Press {

	private long pressId;
	private String pressName;
	private String pressCode;
	private double maxWidth;
	private double minWidth;
	private double maxHeight;
	private double minHeight;
	private double gripperSize;
	private double imageGap;
	private int numberOfColors;
	private boolean whiteInkSupport;

	public PressImpl(String pressName, String pressCode, double maxWidth, double minWidth, double maxHeight, double minHeight, double gripperSize, double imageGap, int numberOfColors, boolean whiteInkSupport) {
		this.pressName = pressName;
		this.pressCode = pressCode;
		this.maxWidth = maxWidth;
		this.minWidth = minWidth;
		this.maxHeight = maxHeight;
		this.minHeight = minHeight;
		this.gripperSize = gripperSize;
		this.imageGap = imageGap;

		this.numberOfColors = numberOfColors;
		this.whiteInkSupport = whiteInkSupport;
	}

	@Override
	public long getPressId() {
		return pressId;
	}

	@Override
	public void setPressId(long pressId) {
		if(pressId == Long.MIN_VALUE) {
			this.pressId = pressId;
		}
	}

	@Override
	public String getPressName() {
		return pressName;
	}

	@Override
	public String getPressCode() {
		return pressCode;
	}

	@Override
	public double getMaxWidth() {
		return maxWidth;
	}

	@Override
	public double getMinWidth() {
		return minWidth;
	}

	public double getMaxHeight() {
		return maxHeight;
	}

	public double getMinHeight() {
		return minHeight;
	}

	public double getGripperSize() {
		return gripperSize;
	}

	public double getImageGap() {
		return imageGap;
	}

	@Override
	public int getNumberOfColors() {
		return numberOfColors;
	}

	@Override
	public boolean hasWhiteInkSupport() {
		return whiteInkSupport;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PressImpl)) return false;
		PressImpl press = (PressImpl) o;
		return Objects.equals(getPressCode(), press.getPressCode());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPressCode());
	}
}
