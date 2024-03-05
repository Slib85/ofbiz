package com.envelopes.product;

import org.apache.commons.lang.math.Fraction;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Manu on 3/2/2015.
 */
public class SizeTuple {
	private String featureId;
	private String sizeOriginal = "";
	private String sizeFraction = "";
	private String sizeDecimal = "";
	private String facet;
	private String heightGroup;
	private String widthGroup;
	private String heightFraction = "";
	private String heightDecimal = "0";
	private String widthFraction = "";
	private String widthDecimal = "0";



	public static final Pattern p = Pattern.compile("((\\d+)((\\s|-|\\.)?(\\d+\\/?(\\d+)?))?)");

	private static final String module = SizeTuple.class.getName();

	public SizeTuple(String size, String featureId) {
		this.featureId = featureId;
		if(UtilValidate.isEmpty(size)) {
			sizeOriginal = "";
			Debug.logError("Unable to initialize SizeTuple due to empty size", module);
		} else {
			Matcher m = p.matcher(size);
			int cnt = 0;
			while(m.find()) {
				if(++cnt > 2) {
					break;
				}
				String heightOrWidth = m.group(0);
				String family = m.group(2);
				String fraction = "";
				String decimal = "";
				if(heightOrWidth.contains(".")) {
					Fraction _fraction = Fraction.getFraction(heightOrWidth);
					decimal = heightOrWidth;
					fraction = turnImproperFractionToMixedFraction(_fraction);
				} else if(heightOrWidth.contains("/")) {
					fraction = heightOrWidth;
					decimal = Double.toString(Fraction.getFraction(heightOrWidth).doubleValue());
				} else {
					decimal = fraction = heightOrWidth;
				}

				if(cnt == 1 && decimal.contains(".")) {
					widthFraction = fraction.substring(fraction.indexOf(" ") + 1);
					widthDecimal = decimal.substring(decimal.indexOf(".") + 1);
				} else if(cnt == 2 && decimal.contains(".")) {
					heightFraction = fraction.substring(fraction.indexOf(" ") + 1);
					heightDecimal = decimal.substring(decimal.indexOf(".") + 1);
				}

				sizeOriginal += sizeOriginal.isEmpty() ? heightOrWidth : " x " + heightOrWidth;
				sizeFraction += sizeFraction.isEmpty() ? fraction : " x " + fraction;
				sizeDecimal += sizeDecimal.isEmpty() ? decimal : " x " + decimal;
				facet = sizeOriginal.replaceAll("\\/|\\s|\\.", "");
				if(cnt == 1) {
					widthGroup = family;
				} else {
					heightGroup = family;
				}
			}
		}
	}

	public static String turnImproperFractionToMixedFraction(Fraction f) {
		int a = f.getNumerator() / f.getDenominator();
		int b = f.getNumerator() % f.getDenominator();
		return a != 0 ? (a + " " + b + "/" + f.getDenominator()) : (b + "/" + f.getDenominator());
	}

	public String getFeatureId() {
		return featureId;
	}

	public String getSizeOriginal() {
		return sizeOriginal;
	}

	public String getSizeFraction() {
		return sizeFraction;
	}

	public String getSizeDecimal() {
		return sizeDecimal;
	}

	public String getFacet() {
		return facet;
	}

	public String getHeightGroup() {
		return heightGroup;
	}

	public String getWidthGroup() {
		return widthGroup;
	}

	public String getHeightFraction() {
		return heightFraction;
	}

	public String getHeightDecimal() {
		return heightDecimal;
	}

	public String getWidthFraction() {
		return widthFraction;
	}

	public String getWidthDecimal() {
		return widthDecimal;
	}

	/*
	if(cnt == 2 && !map.containsKey(facet)) {
					System.out.print(_size);
					System.out.print(" - " + facet);
					System.out.print(" - " + _sizeF);
					System.out.print(" - " + _sizeD);
					System.out.print(" - " + wFamily);
					System.out.println(" - " + hFamily);
				}
	 */


}
