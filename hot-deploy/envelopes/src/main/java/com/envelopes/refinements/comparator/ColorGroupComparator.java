package com.envelopes.refinements.comparator;

import java.util.Comparator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Manu on 1/19/2015.
 */
public class ColorGroupComparator implements Comparator<Map<String, String>> {
	private static Pattern p = Pattern.compile("(.*)?_");

	@Override
	public int compare(Map<String, String> o1, Map<String, String> o2) {
		return getColorGroupName(o1.get("id")).compareTo(getColorGroupName(o2.get("id")));
	}

	public static String getColorGroupName(String colorFacetName) {
		Matcher m = p.matcher(colorFacetName);
		if(m.find()) {
			return m.group(1);
		} else {
			return "";
		}
	}
}
