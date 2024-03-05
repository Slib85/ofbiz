package com.envelopes.refinements;

import com.envelopes.product.SizeMatrix;
import com.envelopes.product.SizeTuple;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.base.util.cache.UtilCache;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Manu on 8/5/2014.
 */
public class RefinementsUtil {

    private static final UtilCache<String, RefinementsWidgetResponse> refinementsCache = UtilCache.createUtilCache("envelopes.refinements.category", 0, 0);
	private static final UtilCache<String, SizeMatrix> sizeMatrixCache = UtilCache.createUtilCache("envelopes.refinements.size", 0, 0);
    public static enum RequestType {SEARCH, CATEGORY, SIZE, DESIGN};
    public static final String module = RefinementsUtil.class.getName();
    public static final String CATEGORY_ID = "category_id";
    public static final String STYLE_ID = "style_id";
    public static final String[] ADDITIONAL_PARAMETERS = new String[]{"utm_campaign"};
    public static final String AF_DATA = "af";
    public static final String PAGE = "page";
    public static final String SORT_BY = "sort";
    public static final String DELEGATOR = "delegator";
    public static final String SEARCH_WORD = "w";
	public static final String SIZE_FEATURE_ID = "SIZE_CODE";
	public static final String SIZE_WIDTH = "PRODUCT_WIDTH";
	public static final String SIZE_WIDTH_FRACTION = "PRODUCT_WIDTH_FRACTION";
	public static final String SIZE_HEIGHT = "PRODUCT_HEIGHT";
	public static final String SIZE_HEIGHT_FRACTION = "PRODUCT_HEIGHT_FRACTION";
	public static final String SIZE_FACET = "SIZE_FACET";
    public static final String PAGE_SIZE = "cnt";
    public static final String REQUEST_TYPE = "REQUEST_TYPE";
    protected static final String CATEGORY_REPLACE_PATTERN = "_|-";
    protected static String itemUrlPrefix = "";
    protected static String itemUrlParameters = "";

    public static RefinementsWidgetResponse getRefinementsResponse(HttpServletRequest request, String... args) {
        try {
            itemUrlPrefix = request.getRequestURL().toString().replace(request.getRequestURI(), "").replaceAll("(?:\\:80|\\:443|(\\:\\d+))(\\/|$)", "$1$2")/*.replaceAll("https:", "http:")*/;
            Map<String, Object> parametersMap = getRefinementParametersMap(request, args);
			itemUrlParameters = getItemUrlParameters(parametersMap);
            RequestType requestType = (RequestType) parametersMap.get(REQUEST_TYPE);
            if(requestType == RequestType.CATEGORY) {
                String categoryId = (String) parametersMap.get(CATEGORY_ID);
                String styleId = (String) parametersMap.get(STYLE_ID);
                String afData = (String) parametersMap.get(AF_DATA);
                Delegator delegator = (Delegator) parametersMap.get(DELEGATOR);
                if (afData != null && !afData.isEmpty()) {
                    return loadRefinementsResponse(afData, delegator);
                } else {
                    return getRefinementsResponse(categoryId, styleId, delegator);
                }
            } else if(requestType == RequestType.SEARCH) {
                return getRefinementsResponse(parametersMap);
            } else if(requestType == RequestType.SIZE) {
				Delegator delegator = (Delegator) parametersMap.get(DELEGATOR);
				String sizeFacet = (String) parametersMap.get(SIZE_FACET);
				return getRefinementsResponse(sizeFacet, delegator);
			} else if(requestType == RequestType.DESIGN) {
				Delegator delegator = (Delegator) parametersMap.get(DELEGATOR);
				return getDesigns(delegator);
			}
        } catch (GenericEntityException e) {
            EnvUtil.reportError(e);
            Debug.logError("Error while trying to retrieve refinements data. " + e + " : " + e.getMessage(), module);
        }
        return new RefinementsWidgetResponse(true, "An error occurred while fetching the refinements data");
    }

	public static String getSizeDescription(HttpServletRequest request) {
		try {
			Map<String, Object> parametersMap = getRefinementParametersMap(request);
			String sizeFacet = (String) parametersMap.get(SIZE_FACET);
			Delegator delegator = (Delegator) parametersMap.get(DELEGATOR);
			SizeMatrix sizeMatrix = getSizeMatrix(delegator);
			if(UtilValidate.isNotEmpty(sizeFacet)) {
				return sizeMatrix.getSizeDescription(sizeFacet);
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve refinements data. " + e + " : " + e.getMessage(), module);
		}
		return "";
	}

	public static RefinementsWidgetResponse getColors(String parentColor) {
		String cacheKey = parentColor + "-colors-" + itemUrlPrefix;
		if(!refinementsCache.containsKey(cacheKey)) {
			RefinementsWidgetResponse response = new RefinementsWidget().fetchColorGroups().getResponse();
			for (Map<String, Object> colorGroup : response.getColorGroups()) {
				if(colorGroup.get("id").equals(parentColor)) {
					response.setColors((List<Map<String, String>>)colorGroup.get("colors"));
					refinementsCache.put(cacheKey, response);
					break;
				}
			}
		}
		return refinementsCache.get(cacheKey);
	}

	public static RefinementsWidgetResponse getColorGroups() {
		String cacheKey = "color-groups-" + itemUrlPrefix;
		if(!refinementsCache.containsKey(cacheKey)) {
			RefinementsWidgetResponse response = new RefinementsWidget().fetchColorGroups().getResponse();
			refinementsCache.put(cacheKey, response);
		}
		return refinementsCache.get(cacheKey);
	}

	public static RefinementsWidgetResponse getCollections() {
		String cacheKey = "collections-" + itemUrlPrefix;
		if(!refinementsCache.containsKey(cacheKey)) {
			RefinementsWidgetResponse response = new RefinementsWidget().fetchCollections().getResponse();
			refinementsCache.put(cacheKey, response);
		}
		return refinementsCache.get(cacheKey);
	}

	public static RefinementsWidgetResponse getSizes() {
		String cacheKey = "sizes-" + itemUrlPrefix;
		if(!refinementsCache.containsKey(cacheKey)) {
			RefinementsWidgetResponse response = new RefinementsWidget().fetchSizes().getResponse();
			refinementsCache.put(cacheKey, response);
		}
		return refinementsCache.get(cacheKey);
	}

	public static RefinementsWidgetResponse getDesigns(Delegator delegator) {
		String cacheKey = "designs-" + itemUrlPrefix;
		if(!refinementsCache.containsKey(cacheKey)) {
			RefinementsWidgetResponse response = new RefinementsWidget(new HashMap<RefinementsWidget.Facet, List<String>>(), itemUrlPrefix, itemUrlParameters, delegator, RequestType.DESIGN).getResponse();
			refinementsCache.put(cacheKey, response);
		}
		return refinementsCache.get(cacheKey);
	}

    protected static RefinementsWidgetResponse getRefinementsResponse(Map<String, Object> parametersMap) {
        String keyword = (String) parametersMap.get(SEARCH_WORD);
        String afData = (String) parametersMap.get(AF_DATA);
        int page = (int) parametersMap.get(PAGE);
        int pageSize = (int) parametersMap.get(PAGE_SIZE);
        String sortBy = (String) parametersMap.get(SORT_BY);
        return new RefinementsWidget(keyword, afData, page, pageSize, sortBy, itemUrlPrefix, itemUrlParameters).getResponse();
    }

	public static SizeMatrix getSizeMatrix(Delegator delegator) {
		if(!sizeMatrixCache.containsKey("sizes")) {
			try {
				sizeMatrixCache.put("sizes", new SizeMatrix(delegator));
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to instantiate sizeMatrix. " + e + " : " + e.getMessage(), module);
			}
		}

		return sizeMatrixCache.get("sizes");
	}

    public static String getSliResponse(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        try {
            request.setAttribute("requestType", RequestType.CATEGORY);
            RefinementsWidgetResponse widgetResponse = getRefinementsResponse(request);
            Map<String, Object> parametersMap = getRefinementParametersMap(request);
            int page = (int) parametersMap.get(PAGE);
            String sortBy = (String) parametersMap.get(SORT_BY);
            jsonResponse.put("products", widgetResponse.getProducts(page, sortBy));
            jsonResponse.put("pages", widgetResponse.getNumberOfPages());
            jsonResponse.put("thisPage", page);
            jsonResponse.put("facets", widgetResponse.getFacetMap());
            jsonResponse.put("filters", widgetResponse.getAppliedFilters());
            jsonResponse.put("description", widgetResponse.getBannerTitle());
            jsonResponse.put("imageFile", widgetResponse.getBannerName());
            jsonResponse.put("success", true);
        } catch (Exception e) {
            jsonResponse.put("success", false);
            EnvUtil.reportError(e);
            Debug.logError("Error while trying to retrieve SLI data. " + e + " : " + e.getMessage(), module);
        }
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    protected static RefinementsWidgetResponse getRefinementsResponse(String categoryId, String styleId, Delegator delegator) {
        String cacheKey = getCacheKey(categoryId, styleId) + "-" + itemUrlPrefix;
        if(!refinementsCache.containsKey(cacheKey)) {
            Map<RefinementsWidget.Facet, List<String>> filtersMap = new HashMap<>();
            if(categoryId != null) {
                filtersMap.put(RefinementsWidget.Facet.CATEGORY, Arrays.asList(categoryId));
            }
            if(styleId != null) {
                filtersMap.put(RefinementsWidget.Facet.STYLE, Arrays.asList(styleId));
            }
            RefinementsWidgetResponse response = new RefinementsWidget(filtersMap, itemUrlPrefix, itemUrlParameters, delegator, RequestType.CATEGORY).getResponse();
            refinementsCache.put(cacheKey, response);
        }
        return refinementsCache.get(cacheKey);
    }

	protected static RefinementsWidgetResponse getRefinementsResponse(String sizeId, Delegator delegator) {
		String cacheKey = getCacheKeyForSize(sizeId) + "-" + itemUrlPrefix;
		if(!refinementsCache.containsKey(cacheKey)) {
			Map<RefinementsWidget.Facet, List<String>> filtersMap = new HashMap<>();
			//TODO -if size Id is null, so no items found for the given size
			if(UtilValidate.isNotEmpty(sizeId)) {
				filtersMap.put(RefinementsWidget.Facet.SIZE, Arrays.asList(sizeId));
			}
			RefinementsWidgetResponse response = new RefinementsWidget(filtersMap, itemUrlPrefix, itemUrlParameters, delegator, RequestType.SIZE).getResponse();
			refinementsCache.put(cacheKey, response);
		}
		return refinementsCache.get(cacheKey);
	}

    protected static RefinementsWidgetResponse loadRefinementsResponse(String afData, Delegator delegator) {
        String cacheKey = getCacheKey(afData) + "-" + itemUrlPrefix;
        if(!refinementsCache.containsKey(cacheKey)) {
            refinementsCache.put(cacheKey, new RefinementsWidget(afData, itemUrlPrefix, itemUrlParameters, delegator).getResponse());
        }
        return refinementsCache.get(cacheKey);
    }

    public static String getCacheKey(String categoryId, String styleId) {
        String cacheKey = UtilValidate.isEmpty(categoryId) && UtilValidate.isEmpty(styleId) ? "use:all" : !UtilValidate.isEmpty(categoryId) ? RefinementsWidget.Facet.CATEGORY.getSliFacet() + ":" + categoryId : "";
        if(!UtilValidate.isEmpty(styleId)) {
            cacheKey += " "  + RefinementsWidget.Facet.STYLE.getSliFacet() + ":" + styleId;
        }
        return cacheKey;
    }

	public static String getCacheKeyForSize(String sizeId) {
		String cacheKey = UtilValidate.isEmpty(sizeId) ? "si:all" : "si:" + sizeId;

		return cacheKey;
	}

    public static String getCacheKey(String afData) {
        return afData == null ? "" : afData.replaceAll("prodtype:products", "").trim();
    }

    protected static Map<String, Object> getRefinementParametersMap(HttpServletRequest request, String... args) throws GenericEntityException {
        Map<String, Object> params = EnvUtil.getParameterMap(request);
        RequestType requestType = (RequestType) request.getAttribute("requestType");
        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put(REQUEST_TYPE, requestType);
        if(requestType == RequestType.CATEGORY) {
            String categoryId = args != null && args.length >= 1 ? args[0] : cleanParam((String) params.get(CATEGORY_ID));
            String styleId = args != null && args.length == 2 ? args[1] : cleanParam((String) params.get(STYLE_ID));
            String requestMode = (String) request.getAttribute("mode");
            Delegator delegator = (Delegator) request.getAttribute("delegator");
            parametersMap.put(DELEGATOR, delegator);
            if (requestMode != null && requestMode.equals("direct") && categoryId != null) {
                categoryId = convertToFacet(categoryId);
                if (categoryLookup.containsKey(categoryId)) {
                    GenericValue category = delegator.findOne("ProductCategory", UtilMisc.toMap("productCategoryId", categoryLookup.get(categoryId)), true);
                    if (category != null) {
                        if (!category.getString("primaryParentCategoryId").equals("ENVELOPES") && !category.getString("primaryParentCategoryId").equals("AE")) {
                            styleId = categoryId;
                            categoryId = convertToFacet(category.getString("primaryParentCategoryId"));
                        }
                    }
                }
            } else {
                if (categoryId != null || styleId != null) {
                    categoryId = categoryId != null ? convertToFacet(categoryId) : null;
                    styleId = styleId != null ? convertToFacet(styleId) : null;
                }
            }
			parametersMap.putAll(getAdditionalParameters(params));
            parametersMap.put(CATEGORY_ID, categoryId);
            parametersMap.put(STYLE_ID, styleId);
        } else if(requestType == RequestType.SEARCH) {
			if(params.get(SEARCH_WORD) == null) {
				parametersMap.put(SEARCH_WORD, request.getAttribute(SEARCH_WORD));
			} else {
				parametersMap.put(SEARCH_WORD, cleanParam((String) params.get(SEARCH_WORD)));
			}
			parametersMap.putAll(getAdditionalParameters(params));
            parametersMap.put(PAGE_SIZE, NumberUtils.toInt(cleanParam((String) params.get(PAGE_SIZE))));
        } else if(requestType == RequestType.SIZE) {
			String value = "";
			Delegator delegator = (Delegator) request.getAttribute("delegator");
			parametersMap.put(DELEGATOR, delegator);
			if(UtilValidate.isNotEmpty(value = cleanParam((String)params.get(SIZE_FEATURE_ID)))) {
				parametersMap.put(SIZE_FACET, getSizeMatrix(delegator).findSize(value.replaceAll("pft_", "")).getFacet());
			} else if(UtilValidate.isNotEmpty(value = cleanParam((String) params.get(SIZE_FACET)))) {
				parametersMap.put(SIZE_FACET, value);
			} else if(UtilValidate.isNotEmpty(cleanParam((String)params.get(SIZE_WIDTH))) || UtilValidate.isNotEmpty(cleanParam((String)params.get(SIZE_HEIGHT)))) {
				SizeTuple sizeTuple = getSizeMatrix(delegator).findSize(cleanParam((String)params.get(SIZE_WIDTH)), cleanParam((String)params.get(SIZE_WIDTH_FRACTION)), cleanParam((String)params.get(SIZE_HEIGHT)), cleanParam((String)params.get(SIZE_HEIGHT_FRACTION)));
				parametersMap.put(SIZE_FACET, sizeTuple != null ? sizeTuple.getFacet() : null);
			} else {
				parametersMap.put(SIZE_FACET, "");
			}

		}
		if(cleanParam((String)params.get(AF_DATA)) == null) {
			parametersMap.put(AF_DATA, cleanParam((String)request.getAttribute(AF_DATA)));
		} else {
			parametersMap.put(AF_DATA, cleanParam((String) params.get(AF_DATA)));
		}
        parametersMap.put(PAGE, NumberUtils.toInt(cleanParam((String) params.get(PAGE))));
        parametersMap.put(SORT_BY, cleanParam((String) params.get(SORT_BY)));
        return parametersMap;
    }

	protected static Map<String, Object> getAdditionalParameters(Map<String, Object> context) {
		Map<String, Object> additionalParameters = new HashMap<>();
		for(int i = 0; i < ADDITIONAL_PARAMETERS.length; i ++) {
			if(UtilValidate.isNotEmpty(context.get(ADDITIONAL_PARAMETERS[i]))) {
				additionalParameters.put(ADDITIONAL_PARAMETERS[i], context.get(ADDITIONAL_PARAMETERS[i]));
			}
		}
		return additionalParameters;
	}

	protected static String getItemUrlParameters(Map<String, Object> parameterMap) {
		String itemUrlParameters = "";
		/* Hard coded rules to add custom parameters to product page*/
		String campaignId = "";
		if(UtilValidate.isNotEmpty(campaignId = (String) parameterMap.get("utm_campaign"))) {
			if(campaignId.equalsIgnoreCase("2016 Glitter Sample Version")) {
				itemUrlParameters = "ss=true";
			}
		}
		return itemUrlParameters;
	}

	protected static String cleanParam(String param) {
		if(UtilValidate.isNotEmpty(param)) {
			return param.replaceAll("\\?$", "");
		} else {
			return param;
		}
	}

	public static List<Map<String, Object>> getDesignTemplates(HttpServletRequest request) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		return TemplateManager.findTemplates(delegator);

	}

    protected static String convertToFacet(String categoryId) {
        return categoryId.replaceAll(CATEGORY_REPLACE_PATTERN, "").toLowerCase();
    }

    public static final Map<String, List<String>> categoryStyleMap = new LinkedHashMap<>();
    static {
        List<String> businessStyles = new ArrayList<>();
        businessStyles.add("regular");
        businessStyles.add("window");
        businessStyles.add("openend");
        businessStyles.add("booklet");
        businessStyles.add("document");
        businessStyles.add("clasp");
        businessStyles.add("remittance");
        businessStyles.add("specialtyuse");
        categoryStyleMap.put("business", businessStyles);

        List<String> invitationStyles = new ArrayList<>();
        invitationStyles.add("squareflap");
        invitationStyles.add("contourflap");
        invitationStyles.add("square");
        invitationStyles.add("pointedflap");
        invitationStyles.add("colorflaps");
        invitationStyles.add("colorseams");
        invitationStyles.add("printeriors");
        invitationStyles.add("minienvelopes");
        invitationStyles.add("layercards");
        invitationStyles.add("gatefold");
        invitationStyles.add("zfold");
        invitationStyles.add("lined");
        invitationStyles.add("petals");
        invitationStyles.add("pockets");
        invitationStyles.add("foldedtags");
        categoryStyleMap.put("invitation", invitationStyles);

        List<String> shippingStyles = new ArrayList<>();
        shippingStyles.add("bubblelined");
        shippingStyles.add("expansion");
        shippingStyles.add("paperboardmailers");
        shippingStyles.add("plasticmailers");
        shippingStyles.add("tyvek");
        shippingStyles.add("boxes");
        categoryStyleMap.put("shipping", shippingStyles);


        List<String> paperAndMoreStyles = new ArrayList<>();
        paperAndMoreStyles.add("notecards");
        paperAndMoreStyles.add("paper");
        paperAndMoreStyles.add("labels");
        paperAndMoreStyles.add("cardstock");
        paperAndMoreStyles.add("folders");
        paperAndMoreStyles.add("paperandmore");
        paperAndMoreStyles.add("foldedcards");
        paperAndMoreStyles.add("bordercards");
        categoryStyleMap.put("paperandmore", paperAndMoreStyles);
    }

    public static final Map<String, String> categoryLookup = new LinkedHashMap<>();
    static {
        categoryLookup.put("invitation",        "INVITATION");
        categoryLookup.put("business",          "BUSINESS");
        categoryLookup.put("paperandmore",      "PAPER_AND_MORE");
        categoryLookup.put("shipping",          "SHIPPING");
        categoryLookup.put("squareflap",        "SQUARE_FLAP");
        categoryLookup.put("square",            "SQUARE");
        categoryLookup.put("layercards",        "LAYER_CARDS");
        categoryLookup.put("contourflap",       "CONTOUR_FLAP");
        categoryLookup.put("pointedflap",       "POINTED_FLAP");
        categoryLookup.put("gatefold",          "GATEFOLD");
        categoryLookup.put("zfold",             "Z-FOLD");
        categoryLookup.put("lined",             "LINED");
        categoryLookup.put("petals",            "PETALS");
        categoryLookup.put("pockets",           "POCKETS");
        categoryLookup.put("colorseams",        "COLOR_SEAMS");
        categoryLookup.put("colorflaps",        "COLOR_FLAPS");
        categoryLookup.put("foldedtags",        "TAGS");
        categoryLookup.put("tags",              "TAGS");
        categoryLookup.put("printeriors",       "PRINT_ERIORS");
        categoryLookup.put("regular",           "REGULAR");
        categoryLookup.put("booklet",           "BOOKLET");
        categoryLookup.put("openend",           "OPEN_END");
        categoryLookup.put("window",            "WINDOW");
        categoryLookup.put("clasp",             "CLASP");
        categoryLookup.put("remittance",        "REMITTANCE");
        categoryLookup.put("document",          "DOCUMENT");
        categoryLookup.put("specialtyuse",      "SPECIALTY_USE");
        categoryLookup.put("flatcards",         "NOTECARDS");
        categoryLookup.put("notecards",         "NOTECARDS");
        categoryLookup.put("paper",             "PAPER");
        categoryLookup.put("labels",            "LABELS");
        categoryLookup.put("cardstock",         "CARDSTOCK");
        categoryLookup.put("folders",           "FOLDERS");
        categoryLookup.put("bubblelined",       "BUBBLE_LINED");
        categoryLookup.put("expansion",         "EXPANSION");
        categoryLookup.put("paperboardmailers", "PAPERBOARD_MAILERS");
        categoryLookup.put("plasticmailers",    "PLASTIC_MAILERS");
        categoryLookup.put("tyvek",             "TYVEK");
        categoryLookup.put("minienvelopes",     "MINI_ENVELOPES");
        categoryLookup.put("foldedcards",       "FOLDED_CARDS");
        categoryLookup.put("bordercards",       "BORDERCARDS");
        categoryLookup.put("box",       		"BOX");
        categoryLookup.put("stamps",       		"STAMPS");
        categoryLookup.put("innerouter",   		"INNER_OUTER");

    }

	public static final Map<String, String> colorGroupsCategoryMap = new HashMap<>();
	static {
		colorGroupsCategoryMap.put("AIRMAIL"            ,  "airmail-colors");
		colorGroupsCategoryMap.put("BLACK"              ,  "black-colors");
		colorGroupsCategoryMap.put("BLUE"               ,  "blue-colors");
		colorGroupsCategoryMap.put("BROWN"              ,  "brown-colors");
		colorGroupsCategoryMap.put("CLEAR"              ,  "clear-colors");
		colorGroupsCategoryMap.put("CRYSTAL"            ,  "crystal-colors");
		colorGroupsCategoryMap.put("GOLD"               ,  "gold-colors");
		colorGroupsCategoryMap.put("GRAY"               ,  "gray-colors");
		colorGroupsCategoryMap.put("GREEN"              ,  "green-colors");
		colorGroupsCategoryMap.put("IVORY"              ,  "ivory-colors");
		colorGroupsCategoryMap.put("NATURAL"            ,  "natural-colors");
		colorGroupsCategoryMap.put("ORANGE"             ,  "orange-colors");
		colorGroupsCategoryMap.put("PINK"               ,  "pink-colors");
		colorGroupsCategoryMap.put("PURPLE"             ,  "purple-colors");
		colorGroupsCategoryMap.put("QUARTZ"             ,  "quartz-colors");
		colorGroupsCategoryMap.put("RED"                ,  "red-colors");
		colorGroupsCategoryMap.put("SILVER"             ,  "silver-colors");
		colorGroupsCategoryMap.put("TAN"                ,  "tan-colors");
		colorGroupsCategoryMap.put("TEAL"               ,  "teal-colors");
		colorGroupsCategoryMap.put("WHITE"              ,  "white-colors");
		colorGroupsCategoryMap.put("YELLOW"             ,  "yellow-colors");
		colorGroupsCategoryMap.put("GROCERYBAG"         ,  "grocerybag-colors");
		colorGroupsCategoryMap.put("TAUPE"         	    ,  "taupe-colors");
		colorGroupsCategoryMap.put("NAVY"         	    ,  "navy-colors");
		colorGroupsCategoryMap.put("STONE"         	    ,  "stone-colors");
		colorGroupsCategoryMap.put("VRYSTAL"         	,  "vrystal-colors");
	}

	public static final Map<String, String> collectionsCategoryMap = new HashMap<>();
	static {
		collectionsCategoryMap.put("BLACKS"             ,  "black-collections");
		collectionsCategoryMap.put("BRIGHTS"            ,  "brights-collections");
		collectionsCategoryMap.put("CLEARS"             ,  "clear-collections");
		collectionsCategoryMap.put("COLOR_LININGS"      ,  "color-linings-collections");
		collectionsCategoryMap.put("COLORFLAPS"         ,  "colorflaps-collections");
		collectionsCategoryMap.put("COLORSEAMS"         ,  "colorseams-collections");
		collectionsCategoryMap.put("COTTON"             ,  "cotton-collections");
		collectionsCategoryMap.put("DENIMS"             ,  "denim-collections");
		collectionsCategoryMap.put("EARTHTONES"         ,  "earthtones-collections");
		collectionsCategoryMap.put("EXCLUSIVES"         ,  "exclusives-collections");
		collectionsCategoryMap.put("FASHIONS"           ,  "fashion-collections");
		collectionsCategoryMap.put("GROCERY_BAG"        ,  "grocery-bag-collections");
		collectionsCategoryMap.put("IVORIES"            ,  "ivory-collections");
		collectionsCategoryMap.put("KRAFTS"             ,  "kraft-collections");
		collectionsCategoryMap.put("LINENS"             ,  "linen-collections");
		collectionsCategoryMap.put("LUX"                ,  "lux-collections");
		collectionsCategoryMap.put("METALLICS"          ,  "metallics-collections");
		collectionsCategoryMap.put("MIRRORS"            ,  "mirror-collections");
		collectionsCategoryMap.put("NATURALS"           ,  "natural-collections");
		collectionsCategoryMap.put("PARCHMENTS"         ,  "parchments-collections");
		collectionsCategoryMap.put("PASTELS"            ,  "pastels-collections");
		collectionsCategoryMap.put("PRINTERIORS"        ,  "printeriors-collections");
		collectionsCategoryMap.put("PRINTS"             ,  "prints-collections");
		collectionsCategoryMap.put("PRIORITY_EXPRESS"   ,  "express-collections");
		collectionsCategoryMap.put("TRANSLUCENTS"       ,  "translucents-collections");
		collectionsCategoryMap.put("WHITES"             ,  "white-collections");
	}

	public static final Map<String, Object> sizeCombinationMap = new HashMap<>();
	static {
		sizeCombinationMap.put("01", new String[]{"", "1/4", "1/2", "5/8", "11/16", });
	}

	public static final Map<String, String> productFeatureToSizeMap = new HashMap<>();
	static {
		productFeatureToSizeMap.put("8378",  "1 11/16 x 2 3/4");
		productFeatureToSizeMap.put("8630",  "1 11/16 x 2 3/4");
		productFeatureToSizeMap.put("9104",  "1 11/16 x 2 3/4");
		productFeatureToSizeMap.put("8333",  "1 x 2.65");
		productFeatureToSizeMap.put("8429",  "1.25 x 1.25 ");
		productFeatureToSizeMap.put("8513",  "1.25 x 1.25");
		productFeatureToSizeMap.put("12120", "1.5 x 1.5");
		productFeatureToSizeMap.put("12110", "1.625");
		productFeatureToSizeMap.put("8343",  "1.75 x 4.25");
		productFeatureToSizeMap.put("8373",  "2 1/2 x 4 1/4");
		productFeatureToSizeMap.put("9105",  "2 1/2 x 4 1/4");
		productFeatureToSizeMap.put("8415",  "2 1/4 x 3 1/2");
		productFeatureToSizeMap.put("8632",  "2 1/4 x 3 1/2");
		productFeatureToSizeMap.put("9106",  "2 1/4 x 3 1/2");
		productFeatureToSizeMap.put("8356",  "2 1/8 x 3 5/8");
		productFeatureToSizeMap.put("9107",  "2 1/8 x 3 5/8");
		productFeatureToSizeMap.put("8342",  "2 11/16 x 3 11/16");
		productFeatureToSizeMap.put("9108",  "2 11/16 x 3 11/16");
		productFeatureToSizeMap.put("12182", "2 3/8 x 3 1/2");
		productFeatureToSizeMap.put("8276",  "2 7/8 x 5 1/4");
		productFeatureToSizeMap.put("8633",  "2 7/8 x 5 1/4");
		productFeatureToSizeMap.put("9109",  "2 7/8 x 5 1/4");
		productFeatureToSizeMap.put("12040", "2 7/8 x 6 1/2");
		productFeatureToSizeMap.put("8383",  "2 9/16 x 3 9/16");
		productFeatureToSizeMap.put("9110",  "2 9/16 x 3 9/16");
		productFeatureToSizeMap.put("11775", "2 x 2");
		productFeatureToSizeMap.put("8208",  "2 x 3 1/2");
		productFeatureToSizeMap.put("9111",  "2 x 3 1/2");
		productFeatureToSizeMap.put("12083", "2.5 x 1.375");
		productFeatureToSizeMap.put("12072", "2.625 x 1");
		productFeatureToSizeMap.put("8232",  "3 1/2 x 4 7/8");
		productFeatureToSizeMap.put("9112",  "3 1/2 x 4 7/8");
		productFeatureToSizeMap.put("8218",  "3 1/2 x 6");
		productFeatureToSizeMap.put("9113",  "3 1/2 x 6");
		productFeatureToSizeMap.put("8260",  "3 1/2 x 6 1/2");
		productFeatureToSizeMap.put("8634",  "3 1/2 x 6 1/2");
		productFeatureToSizeMap.put("9114",  "3 1/2 x 6 1/2");
		productFeatureToSizeMap.put("8294",  "3 1/3 Round");
		productFeatureToSizeMap.put("12156", "3 1/4 x 3 1/4");
		productFeatureToSizeMap.put("8198",  "3 1/8 x 5 1/2");
		productFeatureToSizeMap.put("8635",  "3 1/8 x 5 1/2");
		productFeatureToSizeMap.put("9115",  "3 1/8 x 5 1/2");
		productFeatureToSizeMap.put("11839", "3 3/4 x 5 3/4");
		productFeatureToSizeMap.put("8055",  "3 3/4 x 6 3/4");
		productFeatureToSizeMap.put("9116",  "3 3/4 x 6 3/4");
		productFeatureToSizeMap.put("11785", "3 3/5 x 8 1/2");
		productFeatureToSizeMap.put("12185", "3 3/8 x 3 1/2");
		productFeatureToSizeMap.put("8249",  "3 3/8 x 6");
		productFeatureToSizeMap.put("8636",  "3 3/8 x 6");
		productFeatureToSizeMap.put("9117",  "3 3/8 x 6");
		productFeatureToSizeMap.put("8178",  "3 5/8 x 5 1/8");
		productFeatureToSizeMap.put("9118",  "3 5/8 x 5 1/8");
		productFeatureToSizeMap.put("8093",  "3 5/8 x 6 1/2");
		productFeatureToSizeMap.put("9119",  "3 5/8 x 6 1/2");
		productFeatureToSizeMap.put("8282",  "3 5/8 x 8 3/4");
		productFeatureToSizeMap.put("9120",  "3 5/8 x 8 3/4");
		productFeatureToSizeMap.put("8156",  "3 5/8 x 8 5/8");
		productFeatureToSizeMap.put("9121",  "3 5/8 x 8 5/8");
		productFeatureToSizeMap.put("8246",  "3 7/8 x 7 1/2");
		productFeatureToSizeMap.put("9122",  "3 7/8 x 7 1/2");
		productFeatureToSizeMap.put("12319", "3 7/8 x 8 1/2");
		productFeatureToSizeMap.put("8099",  "3 7/8 x 8 7/8");
		productFeatureToSizeMap.put("9123",  "3 7/8 x 8 7/8");
		productFeatureToSizeMap.put("11778", "3 x 3");
		productFeatureToSizeMap.put("8172",  "3 x 4 1/2");
		productFeatureToSizeMap.put("8637",  "3 x 4 1/2");
		productFeatureToSizeMap.put("9124",  "3 x 4 1/2");
		productFeatureToSizeMap.put("8307",  "3 x 4 7/8");
		productFeatureToSizeMap.put("8638",  "3 x 4 7/8");
		productFeatureToSizeMap.put("9125",  "3 x 4 7/8");
		productFeatureToSizeMap.put("8450",  "3.5 x 4.25");
		productFeatureToSizeMap.put("8288",  "3.875 x 9.25");
		productFeatureToSizeMap.put("8128",  "4 1/2 x 10 3/8");
		productFeatureToSizeMap.put("9126",  "4 1/2 x 10 3/8");
		productFeatureToSizeMap.put("12155", "4 1/2 x 9 1/2");
		productFeatureToSizeMap.put("8248",  "4 1/4 x 5 1/2");
		productFeatureToSizeMap.put("9127",  "4 1/4 x 5 1/2");
		productFeatureToSizeMap.put("11767", "4 1/4 x 6");
		productFeatureToSizeMap.put("8124",  "4 1/4 x 6 1/2");
		productFeatureToSizeMap.put("9128",  "4 1/4 x 6 1/2");
		productFeatureToSizeMap.put("8639",  "4 1/4 x 6 1/4");
		productFeatureToSizeMap.put("9129",  "4 1/4 x 6 1/4");
		productFeatureToSizeMap.put("8640",  "4 1/4 x 8 7/8");
		productFeatureToSizeMap.put("9130",  "4 1/4 x 8 7/8");
		productFeatureToSizeMap.put("8109",  "4 1/8 x 5 1/2");
		productFeatureToSizeMap.put("9131",  "4 1/8 x 5 1/2");
		productFeatureToSizeMap.put("12241", "4 1/8 x 8");
		productFeatureToSizeMap.put("8050",  "4 1/8 x 9 1/2");
		productFeatureToSizeMap.put("8529",  "4 1/8 x 9 1/2");
		productFeatureToSizeMap.put("9132",  "4 1/8 x 9 1/2");
		productFeatureToSizeMap.put("8641",  "4 1/8 x 9 1/8");
		productFeatureToSizeMap.put("9133",  "4 1/8 x 9 1/8");
		productFeatureToSizeMap.put("12028", "4 15/16 x 5");
		productFeatureToSizeMap.put("8537",  "4 3/16 x 9 1/2");
		productFeatureToSizeMap.put("8223",  "4 3/4 x 11");
		productFeatureToSizeMap.put("9134",  "4 3/4 x 11");
		productFeatureToSizeMap.put("11882", "4 3/4 x 4 3/4");
		productFeatureToSizeMap.put("8058",  "4 3/4 x 6 1/2");
		productFeatureToSizeMap.put("9135",  "4 3/4 x 6 1/2");
		productFeatureToSizeMap.put("11834", "4 3/4 x 6 3/4");
		productFeatureToSizeMap.put("8538",  "4 3/4 x 6 5/16");
		productFeatureToSizeMap.put("8459",  "4 3/4 x 7 1/4");
		productFeatureToSizeMap.put("9136",  "4 3/4 x 7 1/4");
		productFeatureToSizeMap.put("12187", "4 3/8 x 3 1/2");
		productFeatureToSizeMap.put("8043",  "4 3/8 x 5 3/4");
		productFeatureToSizeMap.put("9137",  "4 3/8 x 5 3/4");
		productFeatureToSizeMap.put("9138",  "4 3/8 x 8 1/4");
		productFeatureToSizeMap.put("10000", "4 3/8 x 8 1/4");
		productFeatureToSizeMap.put("8003",  "4 5/8 x 6 1/4");
		productFeatureToSizeMap.put("9139",  "4 5/8 x 6 1/4");
		productFeatureToSizeMap.put("8323",  "4 5/8 x 6 3/4");
		productFeatureToSizeMap.put("9140",  "4 5/8 x 6 3/4");
		productFeatureToSizeMap.put("8280",  "4 5/8 x 8 7/8");
		productFeatureToSizeMap.put("8374",  "4 7/8 x 5");
		productFeatureToSizeMap.put("10043", "4 7/8 x 6 7/8");
		productFeatureToSizeMap.put("11746", "4 7/8 x 7");
		productFeatureToSizeMap.put("12130", "4 x 1");
		productFeatureToSizeMap.put("12100", "4 x 2");
		productFeatureToSizeMap.put("12136", "4 x 3.33");
		productFeatureToSizeMap.put("11781", "4 x 4");
		productFeatureToSizeMap.put("8206",  "4 x 5 1/4");
		productFeatureToSizeMap.put("8357",  "4 x 6");
		productFeatureToSizeMap.put("10305", "4 x 8");
		productFeatureToSizeMap.put("8483",  "4 x 9.25");
		productFeatureToSizeMap.put("8477",  "4.5 x 5.5");
		productFeatureToSizeMap.put("8063",  "5 1/2 x 5 1/2");
		productFeatureToSizeMap.put("9141",  "5 1/2 x 5 1/2");
		productFeatureToSizeMap.put("8159",  "5 1/2 x 7 1/2");
		productFeatureToSizeMap.put("9142",  "5 1/2 x 7 1/2");
		productFeatureToSizeMap.put("9143",  "5 1/2 x 7 3/4");
		productFeatureToSizeMap.put("8642",  "5 1/2 x 8 1/2");
		productFeatureToSizeMap.put("9144",  "5 1/2 x 8 1/2");
		productFeatureToSizeMap.put("8412",  "5 1/2 x 8 1/2 2up");
		productFeatureToSizeMap.put("8047",  "5 1/2 x 8 1/8");
		productFeatureToSizeMap.put("9145",  "5 1/2 x 8 1/8");
		productFeatureToSizeMap.put("11831", "5 1/4 x 5 1/4");
		productFeatureToSizeMap.put("8534",  "5 1/4 x 7 1/2");
		productFeatureToSizeMap.put("9146",  "5 1/4 x 7 1/2");
		productFeatureToSizeMap.put("8095",  "5 1/4 x 7 1/4");
		productFeatureToSizeMap.put("9147",  "5 1/4 x 7 1/4");
		productFeatureToSizeMap.put("11848", "5 1/8 x 5 1/8");
		productFeatureToSizeMap.put("8154",  "5 1/8 x 7");
		productFeatureToSizeMap.put("9148",  "5 1/8 x 7");
		productFeatureToSizeMap.put("10061", "5 1/8 x 8 1/2");
		productFeatureToSizeMap.put("11828", "5 3/4 x 5 3/4");
		productFeatureToSizeMap.put("8643",  "5 3/4 x 8");
		productFeatureToSizeMap.put("9149",  "5 3/4 x 8");
		productFeatureToSizeMap.put("8012",  "5 3/4 x 8 3/4");
		productFeatureToSizeMap.put("9150",  "5 3/4 x 8 3/4");
		productFeatureToSizeMap.put("8284",  "5 3/4 x 8 7/8");
		productFeatureToSizeMap.put("9151",  "5 3/4 x 8 7/8");
		productFeatureToSizeMap.put("8393",  "5 3/4 x 9 1/4");
		productFeatureToSizeMap.put("9152",  "5 3/4 x 9 1/4");
		productFeatureToSizeMap.put("12189", "5 3/8 x 3 1/2");
		productFeatureToSizeMap.put("11845", "5 5/8 x 5 5/8");
		productFeatureToSizeMap.put("12164", "5 5/8 x 9");
		productFeatureToSizeMap.put("10306", "5 x 10");
		productFeatureToSizeMap.put("11995", "5 x 11");
		productFeatureToSizeMap.put("8318",  "5 x 11 1/2");
		productFeatureToSizeMap.put("9153",  "5 x 11 1/2");
		productFeatureToSizeMap.put("11921", "5 x 11 x 2");
		productFeatureToSizeMap.put("12031", "5 x 4 7/8");
		productFeatureToSizeMap.put("8096",  "5 x 5");
		productFeatureToSizeMap.put("9154",  "5 x 5");
		productFeatureToSizeMap.put("10037", "5 x 7");
		productFeatureToSizeMap.put("8023",  "5 x 7 1/2");
		productFeatureToSizeMap.put("9155",  "5 x 7 1/2");
		productFeatureToSizeMap.put("8072",  "5.5 x 7.75");
		productFeatureToSizeMap.put("1230",  "6 1/2 x 10 1/2");
		productFeatureToSizeMap.put("8115",  "6 1/2 x 6 1/2");
		productFeatureToSizeMap.put("9156",  "6 1/2 x 6 1/2");
		productFeatureToSizeMap.put("8245",  "6 1/2 x 9 1/2");
		productFeatureToSizeMap.put("9157",  "6 1/2 x 9 1/2");
		productFeatureToSizeMap.put("12249", "6 1/4 x 2 5/8");
		productFeatureToSizeMap.put("10039", "6 1/4 x 6 1/4");
		productFeatureToSizeMap.put("11735", "6 1/4 x 9 1/4");
		productFeatureToSizeMap.put("11842", "6 1/8 x 6 1/8");
		productFeatureToSizeMap.put("8465",  "6 3/4 x 9 1/4");
		productFeatureToSizeMap.put("9158",  "6 3/4 x 9 1/4");
		productFeatureToSizeMap.put("12191", "6 3/8 x 3 1/2");
		productFeatureToSizeMap.put("11743", "6 5/8 x 10");
		productFeatureToSizeMap.put("10307", "6 x 10");
		productFeatureToSizeMap.put("1218",  "6 x 10 1/2");
		productFeatureToSizeMap.put("11772", "6 x 11");
		productFeatureToSizeMap.put("12315", "6 x 11 1/2");
		productFeatureToSizeMap.put("8066",  "6 x 6");
		productFeatureToSizeMap.put("9159",  "6 x 6");
		productFeatureToSizeMap.put("1216",  "6 x 6 1/2");
		productFeatureToSizeMap.put("8487",  "6 x 8");
		productFeatureToSizeMap.put("9160",  "6 x 8");
		productFeatureToSizeMap.put("8644",  "6 x 8 1/4");
		productFeatureToSizeMap.put("9161",  "6 x 8 1/4");
		productFeatureToSizeMap.put("8426",  "6 x 8 x 1");
		productFeatureToSizeMap.put("8084",  "6 x 9");
		productFeatureToSizeMap.put("9162",  "6 x 9");
		productFeatureToSizeMap.put("8070",  "6 x 9 1/2");
		productFeatureToSizeMap.put("9163",  "6 x 9 1/2");
		productFeatureToSizeMap.put("8089",  "7 1/2 x 10 1/2");
		productFeatureToSizeMap.put("9164",  "7 1/2 x 10 1/2");
		productFeatureToSizeMap.put("8175",  "7 1/2 x 7 1/2");
		productFeatureToSizeMap.put("9165",  "7 1/2 x 7 1/2");
		productFeatureToSizeMap.put("8432",  "7 1/4 11 1/4");
		productFeatureToSizeMap.put("10308", "7 1/4 x 10 1/2");
		productFeatureToSizeMap.put("8645",  "7 1/4 x 11 1/4");
		productFeatureToSizeMap.put("9166",  "7 1/4 x 11 1/4");
		productFeatureToSizeMap.put("10309", "7 1/4 x 12");
		productFeatureToSizeMap.put("12213", "7 1/4 x 5 1/4");
		productFeatureToSizeMap.put("10310", "7 1/4 x 8");
		productFeatureToSizeMap.put("8180",  "7 x 10");
		productFeatureToSizeMap.put("9167",  "7 x 10");
		productFeatureToSizeMap.put("8194",  "7 x 7");
		productFeatureToSizeMap.put("9168",  "7 x 7");
		productFeatureToSizeMap.put("8402",  "7 x 9");
		productFeatureToSizeMap.put("9169",  "7 x 9");
		productFeatureToSizeMap.put("10311", "8 1/2 x 10 1/2");
		productFeatureToSizeMap.put("8130",  "8 1/2 x 11");
		productFeatureToSizeMap.put("9170",  "8 1/2 x 11");
		productFeatureToSizeMap.put("8395",  "8 1/2 x 11 1/4");
		productFeatureToSizeMap.put("9171",  "8 1/2 x 11 1/4");
		productFeatureToSizeMap.put("10312", "8 1/2 x 12");
		productFeatureToSizeMap.put("10313", "8 1/2 x 13");
		productFeatureToSizeMap.put("8469",  "8 1/2 x 13 3/4");
		productFeatureToSizeMap.put("9172",  "8 1/2 x 13 3/4");
		productFeatureToSizeMap.put("10314", "8 1/2 x 14 1/2");
		productFeatureToSizeMap.put("8068",  "8 1/2 x 8 1/2");
		productFeatureToSizeMap.put("9173",  "8 1/2 x 8 1/2");
		productFeatureToSizeMap.put("8274",  "8 3/4 x 11 1/2");
		productFeatureToSizeMap.put("9174",  "8 3/4 x 11 1/2");
		productFeatureToSizeMap.put("8331",  "8 3/4 x 11 1/4");
		productFeatureToSizeMap.put("9175",  "8 3/4 x 11 1/4");
		productFeatureToSizeMap.put("12245", "8 x 3");
		productFeatureToSizeMap.put("8032",  "8 x 8");
		productFeatureToSizeMap.put("9176",  "8 x 8");
		productFeatureToSizeMap.put("12095", "8.5 x 1.5");
		productFeatureToSizeMap.put("8304",  "8.5 x 11");
		productFeatureToSizeMap.put("8258",  "8.5 x 5.5");
		productFeatureToSizeMap.put("8121",  "9 1/2 x 12 1/2");
		productFeatureToSizeMap.put("9177",  "9 1/2 x 12 1/2");
		productFeatureToSizeMap.put("8531",  "9 1/2 x 12 3/4");
		productFeatureToSizeMap.put("8113",  "9 1/2 x 12 5/8");
		productFeatureToSizeMap.put("9178",  "9 1/2 x 12 5/8");
		productFeatureToSizeMap.put("10315", "9 1/2 x 13");
		productFeatureToSizeMap.put("8350",  "9 1/2 x 13 3/4");
		productFeatureToSizeMap.put("9179",  "9 1/2 x 13 3/4");
		productFeatureToSizeMap.put("10316", "9 1/2 x 14 1/2");
		productFeatureToSizeMap.put("10317", "9 1/2 x 14 1/2 ");
		productFeatureToSizeMap.put("8118",  "9 1/2 x 9 1/2");
		productFeatureToSizeMap.put("9180",  "9 1/2 x 9 1/2");
		productFeatureToSizeMap.put("8404",  "9 3/4 x 12 1/4");
		productFeatureToSizeMap.put("9181",  "9 3/4 x 12 1/4");
		productFeatureToSizeMap.put("8351",  "9 x 11 1/2");
		productFeatureToSizeMap.put("9182",  "9 x 11 1/2");
		productFeatureToSizeMap.put("8444",  "9 x 11 1/2 x 1 1/2");
		productFeatureToSizeMap.put("8037",  "9 x 12");
		productFeatureToSizeMap.put("9183",  "9 x 12");
		productFeatureToSizeMap.put("11923", "9 x 12 x 1");
		productFeatureToSizeMap.put("11925", "9 x 12 x 2");
		productFeatureToSizeMap.put("11935", "9 x 12 x 3");
		productFeatureToSizeMap.put("11930", "9 x 12 x 4");
		productFeatureToSizeMap.put("8302",  "9 x 9");
		productFeatureToSizeMap.put("9184",  "9 x 9");
		productFeatureToSizeMap.put("10298", "10 1/2 x 14");
		productFeatureToSizeMap.put("8339",  "10 1/2 x 15 1/4");
		productFeatureToSizeMap.put("9185",  "10 1/2 x 15 1/4");
		productFeatureToSizeMap.put("10299", "10 1/2 x 16");
		productFeatureToSizeMap.put("8018",  "10 x 13");
		productFeatureToSizeMap.put("9186",  "10 x 13");
		productFeatureToSizeMap.put("11912", "10 x 13 x 1");
		productFeatureToSizeMap.put("11915", "10 x 13 x 1 1/2");
		productFeatureToSizeMap.put("11917", "10 x 13 x 2");
		productFeatureToSizeMap.put("11933", "10 x 13 x 4");
		productFeatureToSizeMap.put("8631",  "10 x 15");
		productFeatureToSizeMap.put("9187",  "10 x 15");
		productFeatureToSizeMap.put("11919", "10 x 15 x 2");
		productFeatureToSizeMap.put("8136",  "11 1/2 x 14 1/2");
		productFeatureToSizeMap.put("9188",  "11 1/2 x 14 1/2");
		productFeatureToSizeMap.put("8468",  "11 x 13 1/2");
		productFeatureToSizeMap.put("9189",  "11 x 13 1/2");
		productFeatureToSizeMap.put("8269",  "11 x 17");
		productFeatureToSizeMap.put("9190",  "11 x 17");
		productFeatureToSizeMap.put("12310", "12 1/2 x 12 1/2");
		productFeatureToSizeMap.put("10300", "12 1/2 x 15");
		productFeatureToSizeMap.put("8361",  "12 1/2 x 18 1/2");
		productFeatureToSizeMap.put("9191",  "12 1/2 x 18 1/2");
		productFeatureToSizeMap.put("8237",  "12 1/2 x 18 1/4");
		productFeatureToSizeMap.put("9192",  "12 1/2 x 18 1/4");
		productFeatureToSizeMap.put("10301", "12 1/2 x 19");
		productFeatureToSizeMap.put("10302", "12 1/2 x 19 ");
		productFeatureToSizeMap.put("1220",  "12 3/4 X 10 1/2");
		productFeatureToSizeMap.put("8163",  "12 3/4 x 15");
		productFeatureToSizeMap.put("9193",  "12 3/4 x 15");
		productFeatureToSizeMap.put("8482",  "12 3/4 x 15 x 1 1/2");
		productFeatureToSizeMap.put("11999", "12 x 15");
		productFeatureToSizeMap.put("8211",  "12 x 15 1/2");
		productFeatureToSizeMap.put("9194",  "12 x 15 1/2");
		productFeatureToSizeMap.put("1222",  "12 X 17");
		productFeatureToSizeMap.put("12002", "12 x 18");
		productFeatureToSizeMap.put("8321",  "13 x 17");
		productFeatureToSizeMap.put("9195",  "13 x 17");
		productFeatureToSizeMap.put("8472",  "13 x 18 x 1 1/2");
		productFeatureToSizeMap.put("8306",  "13 x 19");
		productFeatureToSizeMap.put("9196",  "13 x 19");
		productFeatureToSizeMap.put("12020", "14 1/2 x 17 1/2");
		productFeatureToSizeMap.put("8319",  "14 1/2 x 19");
		productFeatureToSizeMap.put("9197",  "14 1/2 x 19");
		productFeatureToSizeMap.put("10303", "14 1/4 x 18 1/2");
		productFeatureToSizeMap.put("8278",  "14 1/4 x 19 1/4");
		productFeatureToSizeMap.put("9198",  "14 1/4 x 19 1/4");
		productFeatureToSizeMap.put("10304", "14 1/4 x 20");
		productFeatureToSizeMap.put("8354",  "14 x 18");
		productFeatureToSizeMap.put("9199",  "14 x 18");
		productFeatureToSizeMap.put("8261",  "15 x 18");
		productFeatureToSizeMap.put("9200",  "15 x 18");
		productFeatureToSizeMap.put("8216",  "15 x 20");
		productFeatureToSizeMap.put("9201",  "15 x 20");
		productFeatureToSizeMap.put("8320",  "16 x 20");
		productFeatureToSizeMap.put("9202",  "16 x 20");
		productFeatureToSizeMap.put("8312",  "17 x 22");
		productFeatureToSizeMap.put("9203",  "17 x 22");
		productFeatureToSizeMap.put("8104",  "18 x 23");
		productFeatureToSizeMap.put("9204",  "18 x 23");
		productFeatureToSizeMap.put("8398",  "19 x 24");
		productFeatureToSizeMap.put("9205",  "19 x 24");
		productFeatureToSizeMap.put("8153",  "19 x 26");
		productFeatureToSizeMap.put("9206",  "19 x 26");
		productFeatureToSizeMap.put("8174",  "22 x 27");
		productFeatureToSizeMap.put("9207",  "22 x 27");
		productFeatureToSizeMap.put("8296",  "24 x 24");
		productFeatureToSizeMap.put("9208",  "24 x 24");
		productFeatureToSizeMap.put("8253",  "24 x 30");
		productFeatureToSizeMap.put("9209",  "24 x 30");
		productFeatureToSizeMap.put("8332",  "24 x 36");
		productFeatureToSizeMap.put("9210",  "24 x 36");
	}



    public static String loadCacheRecords(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        List<String> succeededFacets = new ArrayList<>();
        List<String> failedFacets = new ArrayList<>();
        List<String> facets = new ArrayList<>(categoryLookup.keySet());
        facets.add("*");
        if(refinementsCache.isEmpty() || request.getParameter("fullBuild") != null) {
            request.setAttribute("requestType", RequestType.CATEGORY);
            for (String categoryFacet : categoryStyleMap.keySet()) {
                boolean succeeded = loadCacheRecord(request, categoryFacet, null);
                if(succeeded) {
                    succeededFacets.add(categoryFacet);
                } else {
                    failedFacets.add(categoryFacet);
                }
                for (String styleFacet : categoryStyleMap.get(categoryFacet)) {
                    succeeded = loadCacheRecord(request, categoryFacet, styleFacet);
                    if(succeeded) {
                        succeededFacets.add(categoryFacet + "/" + styleFacet);
                    } else {
                        failedFacets.add(categoryFacet + "/" + styleFacet);
                    }

                    succeeded = loadCacheRecord(request, null, styleFacet);
                    if(succeeded) {
                        succeededFacets.add(styleFacet);
                    } else {
                        failedFacets.add(styleFacet);
                    }
                }
            }
            boolean succeeded = loadCacheRecord(request, null, null);
            if(succeeded) {
                succeededFacets.add("*");
            } else {
                failedFacets.add("*");
            }

            jsonResponse.put("status", refinementsCache.size() + " new records loaded");
            jsonResponse.put("failedFacets", failedFacets);
            jsonResponse.put("succeededFacets", succeededFacets);
        } else {
            jsonResponse.put("status", "Cache records are already loaded, 0 new records loaded");
        }

        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    protected static boolean loadCacheRecord(HttpServletRequest request, String categoryId, String styleId) {
        boolean result = false;
        RefinementsWidgetResponse widgetResponse = getRefinementsResponse(request, categoryId, styleId);

        if(widgetResponse != null) {
            result = true;
        }
        return result;
    }

	public static boolean isCollection(String name) {
		return collectionsCategoryMap.containsKey(name);
	}

	public static boolean isColorGroup(String name) {
		return colorGroupsCategoryMap.containsKey(name);
	}

	public static String getSliFacetFromCategoryId(String categoryId) {
		return cFMap.containsKey(categoryId) ? cFMap.get(categoryId) : "";
	}

	public static String getDefaultSorting(String keywords, String afData) {
		String defaultSort = "sales";
		if(UtilValidate.isNotEmpty(keywords)) {
			keywords = keywords.trim().toLowerCase();
			for (String bestSellerOverrideKeyWord : bestSellerOverrideKeyWords) {
				if(keywords.contains(bestSellerOverrideKeyWord)) {
					defaultSort = "";
					break;
				}
			}
		}
		if(!defaultSort.isEmpty() && UtilValidate.isNotEmpty(afData)) {
			afData = afData.trim().toLowerCase();
			for (String _afData : bestSellerOverrideAfData) {
				if(afData.contains(_afData)) {
					defaultSort = "";
					break;
				}
			}

		}
		return defaultSort;
	}

	public static List<String> bestSellerOverrideKeyWords = new ArrayList<>();
	static {
		bestSellerOverrideKeyWords.add("square envelopes");
		bestSellerOverrideKeyWords.add("wedding envelopes");
		bestSellerOverrideKeyWords.add("lined");
		bestSellerOverrideKeyWords.add("cards");
		bestSellerOverrideKeyWords.add("liner");
		bestSellerOverrideKeyWords.add("rfid");
		bestSellerOverrideKeyWords.add("rfid");
	}

	public static final List<String> bestSellerOverrideAfData = new ArrayList<>();
	static {
		bestSellerOverrideAfData.add("sale:onsale");
	}

	public static Map<String, String> cFMap = new HashMap<>();
	static {

		cFMap.put("8024","prints_airmail");
		cFMap.put("8025","black_midnightblack");
		cFMap.put("8308","black_blacksatin");
//		cFMap.put("8330","black_eagleheadblack");  //
		cFMap.put("8425","black_blackflap");
		cFMap.put("8489","black_blackseam");
		cFMap.put("8507","black_blackwredluxlining");
		cFMap.put("8515","black_blackwsilverluxlining");
		cFMap.put("8533","black_blackwgoldluxlining");
		cFMap.put("8574","black_blacklinen");
		cFMap.put("8026","blue_aquamarinemetallic");
		cFMap.put("8027","blue_babyblue");
		cFMap.put("8028","blue_blueparchment");
		cFMap.put("8029","blue_boardwalkblue");
		cFMap.put("8030","blue_brightblue");
//		cFMap.put("8031","blue_brightteal"); //
		cFMap.put("8032","blue_pastelblue");
		cFMap.put("8033","blue_sapphiremetallic");
		cFMap.put("8034","blue_slate");
//		cFMap.put("8035","blue_surftranslucent");    //
		cFMap.put("8036","blue_vistametallic");
		cFMap.put("8303","blue_teal");
		cFMap.put("8309","blue_boutiqueblue");
		cFMap.put("8310","blue_darkwash");
		cFMap.put("8311","blue_lightwash");
//		cFMap.put("8331","blue_nextdayexpress");  //
		cFMap.put("8332","blue_expressletterblue");
//		cFMap.put("8333","blue_priorityexpressletter");//
		cFMap.put("8409","blue_pool");
		cFMap.put("8411","blue_navy");
		cFMap.put("8423","blue_poolflap");
		cFMap.put("8445","blue_bluegingham");
		cFMap.put("8453","blue_seastripes");
		cFMap.put("8461","blue_bluepolkadots");
		cFMap.put("8467","blue_bluelines");
		cFMap.put("8473","blue_poolseam");
		cFMap.put("8494","blue_navyflap");
		cFMap.put("8519","blue_navywsilverluxlining");
		cFMap.put("8535","blue_navywgoldluxlining");
		cFMap.put("8037","brown_24lbbrownkraft");
		cFMap.put("8038","brown_28lbbrownkraft");
		cFMap.put("8039","brown_bronzemetallic");
//		cFMap.put("8040","brown_brownkraftwpeelampsealreg");   //
		cFMap.put("8041","brown_chocolate");
		cFMap.put("8042","brown_coppermetallic");
//		cFMap.put("8043","brown_fiberkraft");   //
		cFMap.put("8044","brown_grocerybag");
		cFMap.put("8045","brown_tan");
		cFMap.put("8046","brown_tobacco");
		cFMap.put("8539","brown_40lbbrownkraft");
		cFMap.put("8047","clear_cleartranslucent");
		cFMap.put("8048","clear_crystalclear");
//		cFMap.put("8049","clear_iridescenttranslucent");   //
		cFMap.put("8565","clear_clearmatte");
		cFMap.put("8401","crystal_crystalmetallic");
		cFMap.put("8050","gold_brightgold");
		cFMap.put("8051","gold_goldmetallic");
		cFMap.put("8052","gold_goldparchment");
//		cFMap.put("8053","gold_goldtranslucent");  //
		cFMap.put("8054","gold_goldenrod");
//		cFMap.put("8055","gold_sunflower"); //
		cFMap.put("8431","gold_goldflap");
		cFMap.put("8475","gold_goldseam");
		cFMap.put("8521","gold_snowflakes");
		cFMap.put("8551","gold_goldbow");
		cFMap.put("8553","gold_golddamask");
		cFMap.put("8566","gold_goldfoil");
		cFMap.put("8056","gray_anthracitemetallic");
		cFMap.put("8057","gray_graykraft");
//		cFMap.put("8058","gray_graykraftwpeelampsealreg");    //
		cFMap.put("8059","gray_grayparchment");
		cFMap.put("8060","gray_pastelgray");
		cFMap.put("8061","gray_smoke");
		cFMap.put("8427","gray_smokeflap");
		cFMap.put("8491","gray_smokeseam");
		cFMap.put("8517","gray_smokewsilverluxlining");
		cFMap.put("8541","gray_32lbgraykraft");
		cFMap.put("8062","green_brightgreen");
		cFMap.put("8063","green_chartreusetranslucent");
		cFMap.put("8064","green_electricgreen");
		cFMap.put("8065","green_emeraldmetallic");
		cFMap.put("8066","green_fairwaymetallic");
		cFMap.put("8067","green_greenparchment");
		cFMap.put("8068","green_lagoonmetallic");
		cFMap.put("8069","green_leaftranslucent");
		cFMap.put("8070","green_moss");
		cFMap.put("8071","green_olive");
		cFMap.put("8072","green_pastelgreen");
		cFMap.put("8073","green_racinggreen");
		cFMap.put("8074","green_splitpea");
		cFMap.put("8300","green_avocado");
		cFMap.put("8321","green_glowinggreen");
		cFMap.put("8407","green_limelight");
		cFMap.put("8451","green_greengingham");
		cFMap.put("8487","green_avocadoseam");
		cFMap.put("8555","green_christmastrees");
		cFMap.put("8558","green_fluorescentgreen");
		cFMap.put("8594","green_holidaygreen");
		cFMap.put("8607","green_wasabi");
		cFMap.put("8075","natural_ivory");
		cFMap.put("8076","natural_cream");
		cFMap.put("8077","natural_creamparchment");
		cFMap.put("8078","natural_natural");
		cFMap.put("8079","natural_naturallinen");
		cFMap.put("8081","natural_quartzmetallic");
		cFMap.put("8082","natural_springochretranslucent");
		cFMap.put("8083","natural_stone");
		cFMap.put("8264","natural_natural100recycled");
		cFMap.put("8405","natural_naturalwhite100cotton");
		cFMap.put("8437","natural_naturalflap");
		cFMap.put("8505","natural_naturalwgoldluxlining");
		cFMap.put("8509","natural_naturalwredluxlining");
		cFMap.put("8511","natural_naturalwblackluxlining");
		cFMap.put("8573","natural_70lbnatural");
		cFMap.put("8585","natural_nude");
		cFMap.put("8084","orange_brightorange");
		cFMap.put("8085","orange_flamemetallic");
		cFMap.put("8086","orange_mandarin");
		cFMap.put("8087","orange_ochre");
//		cFMap.put("8088","orange_orangetranslucent");   //
		cFMap.put("8302","orange_rust");
		cFMap.put("8439","orange_mandarinflap");
		cFMap.put("8481","orange_mandarinseam");
		cFMap.put("8559","orange_fluorescentorange");
		cFMap.put("8562","orange_pastelorange");
		cFMap.put("8579","orange_tangerine");
		cFMap.put("8089","pink_azaleametallic");
//		cFMap.put("8090","pink_blushtranslucent");   //
		cFMap.put("8091","pink_brightfuchsia");
		cFMap.put("8092","pink_candypink");
		cFMap.put("8093","pink_electricpink");
		cFMap.put("8094","pink_magenta");
//		cFMap.put("8095","pink_magentatranslucent");   //
		cFMap.put("8096","pink_pastelpink");
		cFMap.put("8097","pink_pinkparchment");
		cFMap.put("8098","pink_rosequartzmetallic");
		cFMap.put("8324","pink_hottiepink");
//		cFMap.put("8435","pink_fuchsiaflap"); //
		cFMap.put("8441","pink_candypinkflap");
		cFMap.put("8447","pink_pinkgingham");
//		cFMap.put("8455","pink_fruitystripes");      //
		cFMap.put("8459","pink_flowergarden");
		cFMap.put("8463","pink_pinkpolkadots");
		cFMap.put("8479","pink_fuchsiaseam");
		cFMap.put("8483","pink_candypinkseam");
		cFMap.put("8560","pink_fluorescentpink");
		cFMap.put("8618","pink_rosequartz");
		cFMap.put("8099","purple_amethystmetallic");
//		cFMap.put("8100","purple_brightpurple");  //
		cFMap.put("8101","purple_deeppurple");
		cFMap.put("8102","purple_orchid");
		cFMap.put("8103","purple_punchmetallic");
		cFMap.put("8325","purple_purplepower");
		cFMap.put("8413","purple_vintageplum");
		cFMap.put("8415","purple_mulberry");
		cFMap.put("8417","purple_wisteria");
		cFMap.put("8583","purple_lilac");
		cFMap.put("8104","red_holidayred");
		cFMap.put("8105","red_jupitermetallic");
		cFMap.put("8106","red_redtranslucent");
		cFMap.put("8107","red_rubyred");
		cFMap.put("8108","red_terracotta");
		cFMap.put("8109","red_wine");
		cFMap.put("8301","red_garnet");
//		cFMap.put("8334","red_rushpriorityexpress");     //
		cFMap.put("8433","red_rubyredflap");
		cFMap.put("8457","red_americana");
//		cFMap.put("8465","red_redlines");//
		cFMap.put("8477","red_rubyredseam");
		cFMap.put("8523","red_ornaments");
		cFMap.put("8525","red_poinsettia");
		cFMap.put("8527","red_christmaslights");
		cFMap.put("8110","silver_platinumtranslucent");
		cFMap.put("8111","silver_silvermetallic");
		cFMap.put("8326","silver_mirror");
		cFMap.put("8327","silver_privatemirror");
		cFMap.put("8328","silver_silversand");
		cFMap.put("8429","silver_silverflap");
		cFMap.put("8485","silver_silverseam");
		cFMap.put("8513","silver_silverwblackluxlining");
		cFMap.put("8567","silver_silverfoil");
		cFMap.put("8329","teal_trendyteal");
		cFMap.put("8577","teal_seafoam");
		cFMap.put("8112","white_14lbtyvek");
		cFMap.put("8113","white_24lbbrightwhite");
		cFMap.put("8114","white_28lbbrightwhite");
		cFMap.put("8115","white_70lbbrightwhite");
		cFMap.put("8116","white_80lbbrightwhite");
		cFMap.put("8117","white_birchtranslucent");
//		cFMap.put("8118","white_darkbluelining");    //
		cFMap.put("8119","white_glossywhite");
//		cFMap.put("8120","white_goldfoillining");  //
//		cFMap.put("8121","white_goldlining");  //
//		cFMap.put("8122","white_maroonlining");  //
//		cFMap.put("8123","white_pearllining"); //
		cFMap.put("8124","white_pinklining");
//		cFMap.put("8125","white_purplelining"); //
//		cFMap.put("8127","white_redfoillining");    //
//		cFMap.put("8128","white_redlining");    //
//		cFMap.put("8129","white_silverfoillining");      //
//		cFMap.put("8130","white_silverlining");      //
		cFMap.put("8131","white_whitebubble");
		cFMap.put("8132","white_whitelinen");
		cFMap.put("8133","white_whitepaperboard");
		cFMap.put("8134","white_whiteplastic");
		cFMap.put("8135","white_white30recycled");
//		cFMap.put("8136","white_whitewpeelampsealreg");  //
		cFMap.put("8137","white_whitewsecuritytint");
		cFMap.put("8263","white_white100recycled");
		cFMap.put("8403","white_brightwhite100cotton");
		cFMap.put("8443","white_customcolorflap");
		cFMap.put("8469","white_customcolor");
		cFMap.put("8493","white_60lbwhitewpeelpress");
		cFMap.put("8497","white_whitewredluxlining");
		cFMap.put("8499","white_whitewblackluxlining");
		cFMap.put("8501","white_whitewsilverluxlining");
		cFMap.put("8503","white_whitewgoldluxlining");
		cFMap.put("8543","white_14lbtyvekfirstclass");
		cFMap.put("8545","white_11lbtyvek");
		cFMap.put("8547","white_14lbtyvekwzipstick");
		cFMap.put("8549","white_13lbtyvek");
		cFMap.put("8557","white_70lbwhite");
		cFMap.put("8564","white_white");
		cFMap.put("8569","white_ticketsenclosed");
		cFMap.put("8571","white_24lbwhite");
		cFMap.put("8138","yellow_brightcanary");
//		cFMap.put("8139","yellow_brightlemon");   //
		cFMap.put("8140","yellow_electricyellow");
		cFMap.put("8141","yellow_lemonade");
		cFMap.put("8142","yellow_pastelcanary");
		cFMap.put("8449","yellow_yellowgingham");
		cFMap.put("8561","yellow_fluorescentyellow");
		cFMap.put("8563","yellow_pastelyellow");
		cFMap.put("8591","yellow_citrus");



		cFMap.put("BLACKS"             ,  "black");
		cFMap.put("BRIGHTS"            ,  "brights");
		cFMap.put("CLEARS"             ,  "clear");
		cFMap.put("COLOR_LININGS"      ,  "colorlinings");
		cFMap.put("COLORFLAPS"         ,  "colorflaps");
		cFMap.put("COLORSEAMS"         ,  "colorseams");
		cFMap.put("COTTON"             ,  "cotton");
		cFMap.put("DENIMS"             ,  "denim");
		cFMap.put("EARTHTONES"         ,  "earthtones");
		cFMap.put("EXCLUSIVES"         ,  "exclusive");
		cFMap.put("FASHIONS"           ,  "fashion");
		cFMap.put("GROCERY_BAG", "grocerybag");
		cFMap.put("IVORIES"            ,  "natural");   //TODO - need to verify this
		cFMap.put("KRAFTS"             ,  "kraft");
		cFMap.put("LINENS"             ,  "linen");
		cFMap.put("LUX"                ,  "lux");
		cFMap.put("METALLICS"          ,  "metallics");
		cFMap.put("MIRRORS"            ,  "mirror");
		cFMap.put("NATURALS"           ,  "natural");
		cFMap.put("PARCHMENTS"         ,  "parchments");
		cFMap.put("PASTELS"            ,  "pastels");
		cFMap.put("PRINTERIORS"        ,  "printeriors");
		cFMap.put("PRINTS"             ,  "prints");
		cFMap.put("PRIORITY_EXPRESS"   ,  "");
		cFMap.put("TRANSLUCENTS"       ,  "translucents");
		cFMap.put("WHITES"             ,  "white");
		
		cFMap.put("8143","black_midnightblack");
		cFMap.put("8144","blue_brightblue");
		cFMap.put("8145","yellow_brightcanary");
		cFMap.put("8146","pink_brightfuchsia");
		cFMap.put("8147","gold_brightgold");
		cFMap.put("8148","green_brightgreen");
//		cFMap.put("8149","yellow_brightlemon");
		cFMap.put("8150","orange_brightorange");
//		cFMap.put("8151","purple_brightpurple");
		cFMap.put("8152","red_holidayred");
//		cFMap.put("8153","blue_brightteal");
		cFMap.put("8154","green_electricgreen");
		cFMap.put("8155","pink_electricpink");
		cFMap.put("8156","yellow_electricyellow");
		cFMap.put("8157","clear_crystalclear");
//		cFMap.put("8158","white_darkbluelining");
//		cFMap.put("8159","white_goldfoillining");
//		cFMap.put("8160","white_goldlining");
//		cFMap.put("8161","white_maroonlining");
//		cFMap.put("8162","white_pearllining");
		cFMap.put("8163","white_pinklining");
//		cFMap.put("8164","white_purplelining");
//		cFMap.put("8165","white_redfoillining");
//		cFMap.put("8166","white_redlining");
//		cFMap.put("8167","white_silverfoillining");
//		cFMap.put("8168","white_silverlining");
		cFMap.put("8169","whitelining");//
		cFMap.put("8496","white_whitewredluxlining");
		cFMap.put("8498","white_whitewblackluxlining");
		cFMap.put("8500","white_whitewsilverluxlining");
		cFMap.put("8502","white_whitewgoldluxlining");
		cFMap.put("8504","natural_naturalwgoldluxlining");
		cFMap.put("8506","black_blackwredluxlining");
		cFMap.put("8508","natural_naturalwredluxlining");
		cFMap.put("8510","natural_naturalwblackluxlining");
		cFMap.put("8512","silver_silverwblackluxlining");
		cFMap.put("8514","black_blackwsilverluxlining");
		cFMap.put("8516","gray_smokewsilverluxlining");
		cFMap.put("8518","blue_navywsilverluxlining");
		cFMap.put("8532","black_blackwgoldluxlining");
		cFMap.put("8534","blue_navywgoldluxlining");
		cFMap.put("8422","blue_poolflap");
		cFMap.put("8424","black_blackflap");
		cFMap.put("8426","gray_smokeflap");
		cFMap.put("8428","silver_silverflap");
		cFMap.put("8430","gold_goldflap");
		cFMap.put("8432","red_rubyredflap");
//		cFMap.put("8434","pink_fuchsiaflap");
		cFMap.put("8436","natural_naturalflap");
		cFMap.put("8438","orange_mandarinflap");
		cFMap.put("8440","pink_candypinkflap");
		cFMap.put("8442","white_customcolorflap");
		cFMap.put("8495","blue_navyflap");
		cFMap.put("8520","gold_snowflakes");
		cFMap.put("8522","red_ornaments");
		cFMap.put("8524","red_poinsettia");
		cFMap.put("8526","red_christmaslights");
		cFMap.put("8609","chevronflap");    //
		cFMap.put("8472","blue_poolseam");
		cFMap.put("8474","gold_goldseam");
		cFMap.put("8476","red_rubyredseam");
		cFMap.put("8478","pink_fuchsiaseam");
		cFMap.put("8480","orange_mandarinseam");
		cFMap.put("8482","pink_candypinkseam");
		cFMap.put("8484","silver_silverseam");
		cFMap.put("8486","green_avocadoseam");
		cFMap.put("8488","black_blackseam");
		cFMap.put("8490","gray_smokeseam");
		cFMap.put("8402","white_brightwhite100cotton");
		cFMap.put("8404","natural_naturalwhite100cotton");
		cFMap.put("8312","blue_darkwash");
		cFMap.put("8313","blue_lightwash");
//		cFMap.put("8170","brown_fiberkraft");
		cFMap.put("8171","green_moss");
		cFMap.put("8172","orange_ochre");
		cFMap.put("8173","green_olive");
		cFMap.put("8174","blue_slate");
		cFMap.put("8175","natural_stone");
		cFMap.put("8176","red_terracotta");
		cFMap.put("8177","brown_tobacco");
		cFMap.put("8178","blue_babyblue");
		cFMap.put("8179","blue_boardwalkblue");
		cFMap.put("8180","pink_candypink");
		cFMap.put("8181","brown_chocolate");
		cFMap.put("8182","purple_deeppurple");
		cFMap.put("8183","yellow_lemonade");
		cFMap.put("8184","pink_magenta");
		cFMap.put("8185","orange_mandarin");
		cFMap.put("8186","green_racinggreen");
		cFMap.put("8187","red_rubyred");
		cFMap.put("8189","green_splitpea");
//		cFMap.put("8190","gold_sunflower");
		cFMap.put("8191","red_wine");
		cFMap.put("8304","green_avocado");
		cFMap.put("8305","red_garnet");
		cFMap.put("8306","orange_rust");
		cFMap.put("8307","blue_teal");
		cFMap.put("8406","green_limelight");
		cFMap.put("8408","blue_pool");
		cFMap.put("8410","blue_navy");
		cFMap.put("8414","purple_mulberry");
		cFMap.put("8416","purple_wisteria");
		cFMap.put("8314","black_blacksatin");
		cFMap.put("8315","blue_boutiqueblue");
		cFMap.put("8316","green_glowinggreen");
		cFMap.put("8317","pink_hottiepink");
		cFMap.put("8318","purple_purplepower");
		cFMap.put("8319","silver_silversand");
		cFMap.put("8320","teal_trendyteal");
		cFMap.put("8192","brown_grocerybag");
		cFMap.put("8193","natural_ivory");
		cFMap.put("8194","brown_24lbbrownkraft");
		cFMap.put("8195","brown_28lbbrownkraft");
//		cFMap.put("8196","brown_brownkraftwpeelampsealreg");
		cFMap.put("8197","gray_graykraft");
//		cFMap.put("8198","gray_graykraftwpeelampsealreg");
		cFMap.put("8538","brown_40lbbrownkraft");
		cFMap.put("8540","gray_32lbgraykraft");
		cFMap.put("8199","natural_naturallinen");
		cFMap.put("8200","white_whitelinen");
		cFMap.put("8340","black_blacklinen");
		cFMap.put("8412","purple_vintageplum");
		cFMap.put("8576","teal_seafoam");
		cFMap.put("8578","orange_tangerine");
		cFMap.put("8582","purple_lilac");
		cFMap.put("8584","natural_nude");
		cFMap.put("8590","yellow_citrus");
		cFMap.put("8593","green_holidaygreen");
		cFMap.put("8601","gray_smoke");
		cFMap.put("8606","green_wasabi");
		cFMap.put("8201","purple_amethystmetallic");
		cFMap.put("8202","gray_anthracitemetallic");
		cFMap.put("8203","blue_aquamarinemetallic");
		cFMap.put("8204","pink_azaleametallic");
		cFMap.put("8205","brown_bronzemetallic");
		cFMap.put("8206","brown_coppermetallic");
		cFMap.put("8207","green_emeraldmetallic");
		cFMap.put("8208","green_fairwaymetallic");
		cFMap.put("8209","orange_flamemetallic");
		cFMap.put("8210","gold_goldmetallic");
		cFMap.put("8211","red_jupitermetallic");
		cFMap.put("8212","green_lagoonmetallic");
		cFMap.put("8213","purple_punchmetallic");
		cFMap.put("8214","natural_quartzmetallic");
		cFMap.put("8215","pink_rosequartzmetallic");
		cFMap.put("8216","blue_sapphiremetallic");
		cFMap.put("8217","silver_silvermetallic");
		cFMap.put("8218","blue_vistametallic");
		cFMap.put("8400","crystal_crystalmetallic");
		cFMap.put("8617","pink_rosequartz");
		cFMap.put("8322","silver_mirror");
		cFMap.put("8323","silver_privatemirror");
		cFMap.put("8219","natural_natural");
		cFMap.put("8266","natural_natural100recycled");
		cFMap.put("8572","natural_70lbnatural");
		cFMap.put("8221","blue_blueparchment");
		cFMap.put("8222","natural_creamparchment");
		cFMap.put("8223","gold_goldparchment");
		cFMap.put("8224","gray_grayparchment");
		cFMap.put("8225","green_greenparchment");
		cFMap.put("8226","pink_pinkparchment");
		cFMap.put("8227","natural_cream");
		cFMap.put("8228","gold_goldenrod");
		cFMap.put("8230","purple_orchid");
		cFMap.put("8231","blue_pastelblue");
		cFMap.put("8232","yellow_pastelcanary");
		cFMap.put("8233","gray_pastelgray");
		cFMap.put("8234","green_pastelgreen");
		cFMap.put("8235","pink_pastelpink");
		cFMap.put("8236","brown_tan");
		cFMap.put("8444","blue_bluegingham");
		cFMap.put("8446","pink_pinkgingham");
		cFMap.put("8448","yellow_yellowgingham");
		cFMap.put("8450","green_greengingham");
		cFMap.put("8452","blue_seastripes");
//		cFMap.put("8454","pink_fruitystripes");
		cFMap.put("8456","red_americana");
		cFMap.put("8458","pink_flowergarden");
		cFMap.put("8460","blue_bluepolkadots");
		cFMap.put("8462","pink_pinkpolkadots");
//		cFMap.put("8464","red_redlines");
		cFMap.put("8466","blue_bluelines");
		cFMap.put("8468","white_customcolor");
		cFMap.put("8418","prints_grassprint");
		cFMap.put("8419","prints_sunflowerprint");
		cFMap.put("8420","prints_sandprint");
		cFMap.put("8335","blue_expressletterblue");
//		cFMap.put("8336","blue_nextdayexpress");
//		cFMap.put("8337","blue_priorityexpressletter	");
//		cFMap.put("8338","red_rushpriorityexpress");  //
//		cFMap.put("8339","black_eagleheadblack");   //
		cFMap.put("8237","white_birchtranslucent");
//		cFMap.put("8238","pink_blushtranslucent");   //
		cFMap.put("8239","green_chartreusetranslucent");
		cFMap.put("8240","clear_cleartranslucent");
//		cFMap.put("8241","gold_goldtranslucent");
//		cFMap.put("8242","clear_iridescenttranslucent"); //
		cFMap.put("8243","green_leaftranslucent");
//		cFMap.put("8244","pink_magentatranslucent"); //
//		cFMap.put("8245","orange_orangetranslucent");
		cFMap.put("8246","silver_platinumtranslucent");
		cFMap.put("8247","red_redtranslucent");
		cFMap.put("8248","natural_springochretranslucent");
//		cFMap.put("8249","blue_surftranslucent");
		cFMap.put("8250","white_14lbtyvek");
		cFMap.put("8251","white_24lbbrightwhite");
		cFMap.put("8252","white_28lbbrightwhite");
		cFMap.put("8253","white_70lbbrightwhite");
		cFMap.put("8254","white_80lbbrightwhite");
		cFMap.put("8255","prints_airmail");
		cFMap.put("8256","white_glossywhite");
		cFMap.put("8257","white_whitebubble");
		cFMap.put("8258","white_whitepaperboard");
		cFMap.put("8259","white_whiteplastic");
		cFMap.put("8260","white_white30recycled");
//		cFMap.put("8261","white_whitewpeelampsealreg");
		cFMap.put("8262","white_whitewsecuritytint");
		cFMap.put("8265","white_white100recycled");
		cFMap.put("8470","white_18lbtyvek");
		cFMap.put("8492","white_60lbwhitewpeelpress");
		cFMap.put("8542","white_14lbtyvekfirstclass");
		cFMap.put("8544","white_11lbtyvek");
		cFMap.put("8546","white_14lbtyvekwzipstick");
		cFMap.put("8548","white_13lbtyvek");
		cFMap.put("8550","gold_goldbow");
		cFMap.put("8552","gold_golddamask");
		cFMap.put("8554","green_christmastrees");
		cFMap.put("8556","white_70lbwhite");
		cFMap.put("8568","white_ticketsenclosed");
		cFMap.put("8570","white_24lbwhite");
	}

}
