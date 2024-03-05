package com.envelopes.product;

import com.envelopes.refinements.comparator.GenericNumericComparator;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Manu on 3/2/2015.
 */
public class SizeMatrix {
	private  Map<String, SizeTuple> sizeFeatureMap = new HashMap<>();
	private  Map<String, SizeTuple> sizeDecimalMap = new HashMap<>();
	private  Map<String, SizeTuple> sizeFractionMap = new HashMap<>();
	private  Map<String, String> facetSizeMap = new HashMap<>();
	private Map<Integer, List<List<String>>> heightFamilyFraction = new TreeMap<>();
	private Map<Integer, List<List<String>>> heightFamilyDecimal = new TreeMap<>();
	private Map<Integer, List<List<String>>> widthFamilyFraction = new TreeMap<>();
	private Map<Integer, List<List<String>>> widthFamilyDecimal = new TreeMap<>();

	public SizeMatrix(Delegator delegator) throws Exception {
		init(findSizes(delegator));
	}

	public void init(List<GenericValue> sizes) {
		for (GenericValue size : sizes) {
			SizeTuple sizeTuple = new SizeTuple(size.getString("sizeDescription"), size.getString("sizeFeatureId"));
			if(UtilValidate.isNotEmpty(sizeTuple.getSizeOriginal())) {
				facetSizeMap.put(sizeTuple.getFacet(), sizeTuple.getSizeOriginal());
				sizeFeatureMap.put(sizeTuple.getFeatureId(), sizeTuple);
				sizeDecimalMap.put(sizeTuple.getSizeDecimal(), sizeTuple);
				sizeFractionMap.put(sizeTuple.getSizeFraction(), sizeTuple);
				addFamilyValues(sizeTuple);
			}
		}
		sortFamilyAndValues();
	}

	public void addFamilyValues(SizeTuple sizeTuple) {
		int widthGroup = 0, heightGroup = 0;
		if((sizeTuple.getWidthGroup()) != null && (sizeTuple.getHeightGroup()) != null) {
			widthGroup  = NumberUtils.createInteger(sizeTuple.getWidthGroup());
			heightGroup = NumberUtils.createInteger(sizeTuple.getHeightGroup());
			if (!widthFamilyFraction.containsKey(widthGroup)) {
				widthFamilyFraction.put(widthGroup, new ArrayList<List<String>>());
				widthFamilyFraction.get(widthGroup).add(new ArrayList<String>());
				widthFamilyFraction.get(widthGroup).add(new ArrayList<String>());
				widthFamilyFraction.get(widthGroup).add(new ArrayList<String>());
				widthFamilyDecimal.put(widthGroup, new ArrayList<List<String>>());
				widthFamilyDecimal.get(widthGroup).add(new ArrayList<String>());
				widthFamilyDecimal.get(widthGroup).add(new ArrayList<String>());
				widthFamilyDecimal.get(widthGroup).add(new ArrayList<String>());
			}
			if (!heightFamilyFraction.containsKey(heightGroup)) {
				heightFamilyFraction.put(heightGroup, new ArrayList<List<String>>());
				heightFamilyFraction.get(heightGroup).add(new ArrayList<String>());
				heightFamilyFraction.get(heightGroup).add(new ArrayList<String>());
				heightFamilyFraction.get(heightGroup).add(new ArrayList<String>());
				heightFamilyDecimal.put(heightGroup, new ArrayList<List<String>>());
				heightFamilyDecimal.get(heightGroup).add(new ArrayList<String>());
				heightFamilyDecimal.get(heightGroup).add(new ArrayList<String>());
				heightFamilyDecimal.get(heightGroup).add(new ArrayList<String>());
			}

			List<List<String>> valueList;
			String value;
			if(!(valueList = widthFamilyFraction.get(widthGroup)).get(0).contains(value = sizeTuple.getWidthFraction())) {
				valueList.get(0).add(value);
			}
			if(!valueList.get(1).contains(value = sizeTuple.getHeightGroup())) {
				valueList.get(1).add(value);
			}

			if(!valueList.get(2).contains(value = sizeTuple.getHeightFraction())) {
				valueList.get(2).add(value);
			}

			if(!(valueList = widthFamilyDecimal.get(widthGroup)).get(0).contains(value = sizeTuple.getWidthDecimal())) {
				valueList.get(0).add(value);
			}
			if(!valueList.get(1).contains(value = sizeTuple.getHeightGroup())) {
				valueList.get(1).add(value);
			}

			if(!valueList.get(2).contains(value = sizeTuple.getHeightDecimal())) {
				valueList.get(2).add(value);
			}

			if(!(valueList = heightFamilyFraction.get(heightGroup)).get(0).contains(value = sizeTuple.getHeightFraction())) {
				valueList.get(0).add(value);
			}
			if(!valueList.get(1).contains(value = sizeTuple.getWidthGroup())) {
				valueList.get(1).add(value);
			}

			if(!valueList.get(2).contains(value = sizeTuple.getWidthFraction())) {
				valueList.get(2).add(value);
			}

			if(!(valueList = heightFamilyDecimal.get(heightGroup)).get(0).contains(value = sizeTuple.getHeightDecimal())) {
				valueList.get(0).add(value);
			}
			if(!valueList.get(1).contains(value = sizeTuple.getWidthGroup())) {
				valueList.get(1).add(value);
			}

			if(!valueList.get(2).contains(value = sizeTuple.getWidthDecimal())) {
				valueList.get(2).add(value);
			}
		}
	}

	public void sortFamilyAndValues() {
		/*List<String> familyKeys = Arrays.asList(widthFamilyFraction.keySet().toArray(new String[0]));
		Collections.sort(familyKeys, new GenericNumericComparator());
		for (String familyKey : familyKeys) {
			widthFamilyFraction.get(familyKey);
		}*/
		for (Integer key : widthFamilyFraction.keySet()) {
			Collections.sort(widthFamilyFraction.get(key).get(0), new GenericNumericComparator());
			Collections.sort(widthFamilyFraction.get(key).get(1), new GenericNumericComparator());
			Collections.sort(widthFamilyFraction.get(key).get(2), new GenericNumericComparator());
		}

		for (Integer key : widthFamilyDecimal.keySet()) {
			Collections.sort(widthFamilyDecimal.get(key).get(0), new GenericNumericComparator());
			Collections.sort(widthFamilyDecimal.get(key).get(1), new GenericNumericComparator());
			Collections.sort(widthFamilyDecimal.get(key).get(2), new GenericNumericComparator());
		}

		for (Integer key : heightFamilyFraction.keySet()) {
			Collections.sort(heightFamilyFraction.get(key).get(0), new GenericNumericComparator());
			Collections.sort(heightFamilyFraction.get(key).get(1), new GenericNumericComparator());
			Collections.sort(heightFamilyFraction.get(key).get(2), new GenericNumericComparator());
		}

		for (Integer key : heightFamilyDecimal.keySet()) {
			Collections.sort(heightFamilyDecimal.get(key).get(0), new GenericNumericComparator());
			Collections.sort(heightFamilyDecimal.get(key).get(1), new GenericNumericComparator());
			Collections.sort(heightFamilyDecimal.get(key).get(2), new GenericNumericComparator());
		}


	}

	public SizeTuple findSize(String featureId) {
		return sizeFeatureMap.containsKey(featureId) ? sizeFeatureMap.get(featureId) : null;
	}

	public SizeTuple findSize(String width, String widthFraction, String height, String heightFraction) {
		String size = getSize(width, widthFraction, height, heightFraction);
		if(sizeFractionMap.containsKey(size)) {
			return sizeFractionMap.get(size);
		} else if(sizeDecimalMap.containsKey(size)) {
			return sizeDecimalMap.get(size);
		} else {
			return null;
		}
	}

	public List<GenericValue> findSizes(Delegator delegator) throws GenericEntityException {
		return EntityQuery.use(delegator).select("sizeDescription", "sizeFeatureId").from("ColorWarehouse").where("productTypeId", "ENVELOPE").orderBy("sizeDescription").distinct().queryList();
	}

	public static String getSize(String width, String widthFraction, String height, String heightFraction) {
		width = NumberUtils.createInteger(width).toString();
		String size = width;
		if(UtilValidate.isNotEmpty(widthFraction)) {
			if (widthFraction.contains("/")) {
				size += " " + widthFraction;
			} else {
				size = "." + widthFraction;
			}
		}
		if(UtilValidate.isNotEmpty(height)) {
			height = NumberUtils.createInteger(height).toString();
			size += " x " + height;
			if(UtilValidate.isNotEmpty(heightFraction)) {
				if (heightFraction.contains("/")) {
					size += " " + heightFraction;
				} else {
					size += "." + heightFraction;
				}
			}
		}

		return size;
	}

	public String getSizeDescription(String sizeFacet) {
		if(facetSizeMap.containsKey(sizeFacet)) {
			return facetSizeMap.get(sizeFacet);
		} else {
			return sizeFacet;
		}
	}
}
