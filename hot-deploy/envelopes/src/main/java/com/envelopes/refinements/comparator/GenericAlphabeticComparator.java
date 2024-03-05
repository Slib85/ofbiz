package com.envelopes.refinements.comparator;

import com.envelopes.refinements.model.Filter;
import org.apache.ofbiz.base.util.UtilValidate;

import java.util.Comparator;

/**
 * Created by Manu on 2/10/2015.
 */
public class GenericAlphabeticComparator implements Comparator<Filter> {
	@Override
	public int compare(Filter f1, Filter f2) {
		String value1 = UtilValidate.isNotEmpty(value1 = f1.getFilterName()) ? value1 : "";
		String value2 = UtilValidate.isNotEmpty(value2 = f2.getFilterName()) ? value2 : "";
		return value1.compareTo(value2);
	}
}
