package com.envelopes.refinements.comparator;

import com.envelopes.refinements.model.Filter;
import org.apache.commons.lang.math.Fraction;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Manu on 2/23/2015.
 */
public class SizeFacetComparator implements Comparator<Map<String, Object>>{

	private static final int MAX_SIZE_TOKENS = 3;
	private static final Pattern NUMBER_PATTERN = Pattern.compile("[\\d\\s\\/\\.-]*");

	@Override
	public int compare(Map<String, Object> f1, Map<String, Object> f2) {
		String size1 = (String)f1.get("name");
		if(size1 == null) {
			size1 = "";
		}

		String size2 = (String)f2.get("name");
		if(size2 == null) {
			size2 = "";
		}

		String[] size1TokensString = size1.split("(?i)x");

		String[] size2TokensString = size2.split("(?i)x");

		Float[] size1Tokens = new Float[MAX_SIZE_TOKENS];
		Float[] size2Tokens = new Float[MAX_SIZE_TOKENS];

		for(int i = 0; i < MAX_SIZE_TOKENS; i ++) {
			if(i < size1TokensString.length) {
				size1Tokens[i] = convertToFloat(size1TokensString[i]);
			} else {
				size1Tokens[i] = 0f;
			}

			if(i < size2TokensString.length) {
				size2Tokens[i] = convertToFloat(size2TokensString[i]);
			} else {
				size2Tokens[i] = 0f;
			}

			if(size1Tokens[i].compareTo(size2Tokens[i]) != 0) {
				return size1Tokens[i].compareTo(size2Tokens[i]);

			}
		}
		return 0;
	}

	public static Float convertToFloat(String number) {
		number = number.trim().replaceAll("(\\s|-)+", " ").replaceAll("(\\s\\/\\s?)|(\\s?\\/\\s)", "/");
		Matcher matcher = NUMBER_PATTERN.matcher(number);
		if(matcher.matches()) {
			return Fraction.getFraction(number).floatValue();
		} else {
			return 0f;
		}
	}
}
