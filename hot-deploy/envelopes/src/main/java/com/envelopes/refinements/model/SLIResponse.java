package com.envelopes.refinements.model;

import com.envelopes.common.HashMapSupport;
import com.envelopes.refinements.comparator.ColorGroupComparator;
import com.envelopes.refinements.comparator.SizeFacetComparator;
import com.envelopes.refinements.comparator.SizeFilterComparator;
import com.google.gson.internal.LinkedTreeMap;
import org.apache.ofbiz.base.util.UtilValidate;

import java.util.*;

/**
 * Created by Manu on 8/5/2014.
 */
public class SLIResponse<K, V> extends HashMapSupport<K, V> {

    public enum Attribute {
        TITLE("title"), PARENT_ID("parent_id"), SIZE("size"), PRICE("price"), QUANTITY("base_quantity"), IMAGE_URL("image_url"), ITEM_URL("url"), UNFRIENDLY_ITEM_URL("unfriendlyUrl"), PRINTABLE("printable"), NEW("new"), CLEARANCE("onClearance"),  COLOR_GROUP("color_group"), COLOR("color"), RANK("rank"), SALES_RANK("salesRank"), ON_SALE("onSale"),PERCENTAGE_SAVINGS("percentSavings"), RATING("rating"), PRODUCT_ID("product_id"), UNKNOWN("");
        private String sliAttribute;
        private Attribute(String sliAttribute) {
            this.sliAttribute = sliAttribute;
        }

        public String getSliAttribute() {
            return sliAttribute;
        }
    }

    public Map getResultMetaData() {
        return (Map)get("result_meta");
    }

    private Map<String, Filter> filterMap = new HashMap<>();

    private Map<String, Facet> facetMap = new HashMap<>();

    private String banner = null;

	private String jumpURL = null;

	private List<Map<String, Object>> colorGroups = new ArrayList<>();

	private List<Map<String, Object>> collections = new ArrayList<>();

	private List<Map<String, Object>> sizes = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getSliFacets() {
        return (List<Map<String, Object>>)get("facets");
    }

    public Map<String, Facet> getFacetMap() {
        if(facetMap.isEmpty()) {
            List<Map<String, Object>> sliFacets = getSliFacets();
            for (Map<String, Object> sliFacet : sliFacets) {
                Facet facet = new Facet(sliFacet);
                facetMap.put(facet.getFacetId(), facet);
            }
        }
        return facetMap;
    }

    public Map<String, Filter> getFilterMap() {
        if(filterMap.isEmpty()) {
            for (String key : getFacetMap().keySet()) {
                Facet facet = getFacetMap().get(key);
                for (Filter filter : facet.getFilters()) {
                    filterMap.put(filter.getFilterId(), filter);
                }
            }
        }
        return filterMap;
    }

    public Map getPages() {
        return (Map)get("pages");
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getResults() {
        return (List<Map<String, Object>>)get("results");
    }

    public Map getCurrentPage() {
        return (Map)getPages().get("current");
    }

    protected static int getIntValue(Object wrapperObject) {
        if(wrapperObject instanceof Double) {
            return ((Double) wrapperObject).intValue();
        }
        return 0;
    }

    public int getTotalNumberOfItems() {
        return getIntValue(getResultMetaData().get("total"));
    }

    public int getTotalNumberOfPages() {
        return getIntValue(getPages().get("total"));
    }

    public int getCurrentPageNumber() {
        return getIntValue(getCurrentPage().get("name"));
    }

    public boolean hasMorePages() {
        return getCurrentPageNumber() < getTotalNumberOfPages();
    }

    public boolean isFirstPage() {
        return getCurrentPageNumber() == 1;
    }

    public String getBanner() {
        if(banner == null) {
            banner = "";
			Map merch = getMerchandise();
			if(merch != null && !merch.isEmpty()) {
				Map banners = (Map) merch.get("banners");
				if(banners != null && !banners.isEmpty()) {
					banner = (String)banners.get(banners.keySet().toArray()[0]);
				}
			}
        }
        return banner;
    }

	public String getJumpURL() {
		if(jumpURL == null) {
			jumpURL = "";
			Map merch = getMerchandise();
			if(merch != null && !merch.isEmpty() && merch.containsKey("jumpurl")) {
				jumpURL = (String) merch.get("jumpurl");
			}
		}
		return jumpURL;
	}

	public Map getMerchandise() {
		if(this.containsKey("merch")) {
			return (Map)get("merch");
		} else {
			return new HashMap();
		}
	}

	/*public Map<String, Object> getColors() {
		if(colors.isEmpty()) {
			if(UtilValidate.isNotEmpty(getSliFacets())) {
				colors = getSliFacets().get(0);
			}
		}
		return colors;
	}*/

	public List<Map<String, Object>> getCollections() {
		if(collections.isEmpty()) {
			if(UtilValidate.isNotEmpty(getSliFacets())) {
				collections = (List<Map<String, Object>>)getSliFacets().get(0).get("values");
			}
		}
		return collections;
	}

	public List<Map<String, Object>> getSizes() {
		if(sizes.isEmpty()) {
			if(UtilValidate.isNotEmpty(getSliFacets())) {
				sizes = (List<Map<String, Object>>)getSliFacets().get(0).get("values");
			}
			Collections.sort(sizes, new SizeFacetComparator());
		}
		return sizes;
	}

	public List<Map<String, Object>> getColorGroups() {
		if(colorGroups.isEmpty()) {
			if(UtilValidate.isNotEmpty(getSliFacets())) {
				colorGroups = (List<Map<String, Object>>)getSliFacets().get(0).get("values");
				List<Map<String, String>> colors = (List<Map<String, String>>)getSliFacets().get(1).get("values");
				addColorGroupColors(colors, colorGroups);
			}
		}
		return colorGroups;
	}

	public void addColorGroupColors(List<Map<String, String>> colors, List<Map<String, Object>> colorGroups) {
		Collections.sort(colors, new ColorGroupComparator());
		String previousColorGroupName = "";
		Map<String, List<Map<String, String>>> colorGroupMap = new HashMap<>();
		List<Map<String, String>> colorGroupColors = new ArrayList<>();

		for(Map<String, String> color : colors) {
			String colorGroupName = ColorGroupComparator.getColorGroupName(color.get("id"));
			if(colorGroupName.equals(previousColorGroupName)) {
				colorGroupColors.add(color);
			} else {
				if(!colorGroupColors.isEmpty()) {
					colorGroupMap.put(previousColorGroupName, colorGroupColors);
					colorGroupColors = new ArrayList<>();
				}
				previousColorGroupName = colorGroupName;
				colorGroupColors.add(color);
			}
		}
		if(!colorGroupColors.isEmpty()) {
			colorGroupMap.put(previousColorGroupName, colorGroupColors);
		}

		for (Map<String, Object> colorGroup : colorGroups) {
			colorGroupColors = colorGroupMap.get(colorGroup.get("id"));
			colorGroup.put("colors", colorGroupColors);
			colorGroup.put("colorsCount", colorGroupColors.size());
		}

	}



}
