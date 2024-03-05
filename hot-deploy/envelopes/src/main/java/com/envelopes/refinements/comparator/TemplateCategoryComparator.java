package com.envelopes.refinements.comparator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Manu on 3/18/2015.
 */
public class TemplateCategoryComparator implements Comparator<Map<String, Object>> {

	protected static Map<String, Integer> categoryIndex = new HashMap<>();
	static {
		categoryIndex.put("SQUARE_FLAP", 0);
		categoryIndex.put("CONTOUR_FLAP", 1);
		categoryIndex.put("REGULAR", 2);
		categoryIndex.put("WINDOW", 3);
		categoryIndex.put("SQUARE", 4);
		categoryIndex.put("OPEN_END", 5);
		categoryIndex.put("CLASP", 6);
		categoryIndex.put("BOOKLET", 7);
		categoryIndex.put("DOCUMENT", 8);
		categoryIndex.put("REMITTANCE", 9);
		categoryIndex.put("POINTED_FLAP", 10);
		categoryIndex.put("MINI_ENVELOPES", 11);
		categoryIndex.put("LINED", 12);
		categoryIndex.put("COLOR_SEAMS", 13);
		categoryIndex.put("PRINT_ERIORS", 14);
		categoryIndex.put("SPECIALTY_USE", 15);
		categoryIndex.put("COLOR_FLAPS", 16);
		categoryIndex.put("FOLDED_CARDS", 17);
		categoryIndex.put("NOTECARDS", 18);
	}

	@Override
	public int compare(Map<String, Object> o1, Map<String, Object> o2) {
		String categoryId1 = ((String)o1.get("category_id")).replace("CAT:", "");
		String categoryId2 = ((String)o2.get("category_id")).replace("CAT:", "");
		Integer index1 = categoryIndex.containsKey(categoryId1) ? categoryIndex.get(categoryId1) : 10000;
		Integer index2 = categoryIndex.containsKey(categoryId2) ? categoryIndex.get(categoryId2) : 10000;
		return index1.compareTo(index2);
	}
}
