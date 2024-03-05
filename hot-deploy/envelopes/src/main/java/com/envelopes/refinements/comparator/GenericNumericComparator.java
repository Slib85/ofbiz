package com.envelopes.refinements.comparator;

import org.apache.commons.lang.math.Fraction;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ofbiz.base.util.UtilValidate;

import java.util.Comparator;

/**
 * Created by Manu on 3/5/2015.
 */
public class GenericNumericComparator implements Comparator<String> {
	@Override
	public int compare(String v1, String v2) {
		String value1 = UtilValidate.isEmpty(v1) ? "0" : v1.contains("/") ? Double.toString(Fraction.getFraction(v1).doubleValue()) : v1;
		String value2 = UtilValidate.isEmpty(v2) ? "0" : v2.contains("/") ? Double.toString(Fraction.getFraction(v2).doubleValue()) : v2;
		return new Double(value1).compareTo(new Double(value2));
	}
}
