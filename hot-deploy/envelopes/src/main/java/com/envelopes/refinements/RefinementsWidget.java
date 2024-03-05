package com.envelopes.refinements;

import com.envelopes.refinements.comparator.DesignSizeComparator;
import com.envelopes.refinements.comparator.ProductVariantRankComparator;
import com.envelopes.refinements.comparator.TemplateSizeComparator;
import com.envelopes.refinements.model.Filter;
import com.envelopes.refinements.model.Product;
import com.envelopes.refinements.model.ProductVariant;
import com.envelopes.refinements.model.SLIResponse;
import com.envelopes.http.HTTPHelper;
import com.envelopes.refinements.comparator.ParentIdComparator;
import com.envelopes.util.EnvConstantsUtil;
import com.google.gson.Gson;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;

/**
 * Created by Manu on 8/4/2014.
 */
public class RefinementsWidget {
	public static final String module = RefinementsWidget.class.getName();

	public static final String SLIServiceEndpoint = "http://envelopes.resultspage.com/search?p=Q&lbc=envelopes&ts=json-full";
	private int pageSize = 300;
	public static final int DEFAULT_SEARCH_PAGE_SIZE = 50;

	RefinementsWidgetResponse widgetResponse = new RefinementsWidgetResponse();

	private List<String> categoryFilters = new ArrayList<String>();
	private List<String> sizeFilters = new ArrayList<String>();
	private List<String> colorFilters = new ArrayList<String>();
	private List<String> styleFilters = new ArrayList<String>();

	private List<String> finishFilters = new ArrayList<String>();
	private List<String> ratingFilters = new ArrayList<String>();
	private List<String> collectionFilters = new ArrayList<String>();
	private List<String> weightFilters = new ArrayList<String>();
	private List<String> sealingFilters = new ArrayList<String>();
	private List<String> priceFilters = new ArrayList<String>();
	private List<String> industryFilters = new ArrayList<String>();
	private List<String> themeFilters = new ArrayList<String>();
	private List<String> productTypeFilters = new ArrayList<String>();

	private String keyword = null;
	private Map<Facet, List<String>> filterMap = new HashMap<>();
	private Map<String, Filter> filtersMap = new HashMap<>();
	private String appliedFacets = "";
	private int page = 0;
	private String sortBy = "";
	private Map<String, String> appliedFilters = new LinkedHashMap<>();
	private boolean group = true;
	private String itemUrlPrefix = "", itemUrlParameters = "";
	private Delegator delegator = null;

	private RefinementsUtil.RequestType requestType = RefinementsUtil.RequestType.CATEGORY;

	private List<Map<String, Object>> items;
	private Map<Facet, Map<String, String>> facets;

	public RefinementsWidget(Map<Facet, List<String>> filterMap, String itemUrlPrefix, String itemUrlParameters, Delegator delegator, RefinementsUtil.RequestType requestType) {
		this.filterMap = filterMap;
		this.itemUrlPrefix = itemUrlPrefix;
		this.itemUrlParameters = itemUrlParameters;
		this.delegator = delegator;
		this.requestType = requestType;
		this.appliedFacets = getAppliedFacets();
		this.widgetResponse.setRequestType(requestType);
		this.widgetResponse.setItemUrlParameters(itemUrlParameters);
		this.sortBy = this.sortBy != null ? sortBy : "sales";
		execute();
	}

	public RefinementsWidget(String keyword, String afData, int page, int pageSize, String sortBy, String itemUrlPrefix, String itemUrlParameters) {
		try {
			this.keyword = URLEncoder.encode(keyword, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace(); // TODO Proper logging
		}
		this.itemUrlPrefix = itemUrlPrefix;
		this.itemUrlParameters = itemUrlParameters;
		this.requestType = RefinementsUtil.RequestType.SEARCH;
		this.widgetResponse.setRequestType(requestType);
		this.widgetResponse.setItemUrlParameters(itemUrlParameters);
		this.page = page;
		this.pageSize = pageSize > 0 ? pageSize : DEFAULT_SEARCH_PAGE_SIZE;
		this.sortBy = sortBy != null ? sortBy : "";
		this.group = false;
		this.appliedFacets = afData != null ? afData : "";
		this.filterMap = getFilterMap();
		execute();
	}

	public RefinementsWidget(String afData, String itemUrlPrefix, String itemUrlParameters,  Delegator delegator) {
		this.appliedFacets = afData;
		this.itemUrlPrefix = itemUrlPrefix;
		this.itemUrlParameters = itemUrlParameters;
		this.widgetResponse.setItemUrlParameters(itemUrlParameters);
		this.delegator     = delegator;
		this.filterMap     = getFilterMap();

		execute();
	}

	public RefinementsWidget() {}

	public void execute() {
		fetchItems(this.page);
		if(UtilValidate.isEmpty(widgetResponse.getJumpURL())) {
			gatherItems();
		}
		widgetResponse.setAfData(getAppliedFacets().replaceAll(":", "-")); //Freemarker is encoding the : inside the <script> tag. So escaping with a - replace. This will get replaced back to : in refinements-widget.ftl
	}

	protected BannerInfo getCategoryBannerInfo() {
		String bannerName = "";
		String bannerTitle = "";
		String pageDescription = "";
		String categoryId = "";
		if(UtilValidate.isNotEmpty(getFilterMap().get(Facet.STYLE))/* && getFilterMap().get(Facet.STYLE).size() == 1*/) {
			bannerName = getFilterMap().get(Facet.STYLE).get(0);
		} else if(UtilValidate.isNotEmpty(getFilterMap().get(Facet.CATEGORY))/* && getFilterMap().get(Facet.CATEGORY).size() == 1*/) {
			bannerName = getFilterMap().get(Facet.CATEGORY).get(0);
		}
		try {
			GenericValue category = delegator.findOne("ProductCategory", UtilMisc.toMap("productCategoryId", RefinementsUtil.categoryLookup.get(bannerName)), true);
			if(UtilValidate.isNotEmpty(category)) {
				bannerTitle = category.getString("description");
				pageDescription = category.getString("longDescription");
				categoryId = category.getString("productCategoryId");
			}
		} catch (GenericEntityException e) {
			e.printStackTrace(); //TODO proper logging
		}
		return new BannerInfo(bannerName, bannerTitle, pageDescription, categoryId);
	}

	public RefinementsWidgetResponse getResponse() {
		return this.widgetResponse;
	}

	public void load(String appliedFacets) {
		this.appliedFacets = appliedFacets;
	}

	public String getAppliedFacets() {
		if(appliedFacets.isEmpty()) {
			StringBuilder appliedFacets = new StringBuilder(requestType == RefinementsUtil.RequestType.SEARCH? "" : requestType == RefinementsUtil.RequestType.DESIGN ? "prodtype:designs" : "prodtype:products ");
			if(this.requestType == RefinementsUtil.RequestType.SIZE) {
				appliedFacets.append("type:envelope ");
			}
			if (!filterMap.isEmpty()) {
				for (Facet facet : Facet.values()) {
					if (filterMap.containsKey(facet)) {
						List<String> filters = getFilterMap().get(facet);
						for (String filter : filters) {
							if (!UtilValidate.isEmpty(filter)) {
								appliedFacets.append(facet.getSliFacet()).append(":").append(filter).append(" ");
							}
						}
					}
				}
			}
			this.appliedFacets = appliedFacets.toString().trim();
		}
		return this.appliedFacets;
	}

	public Map<Facet, List<String>> getFilterMap() {
		if(!appliedFacets.isEmpty() && filterMap.isEmpty()) {
			StringTokenizer filterTokenizer = new StringTokenizer(getAppliedFacets(), " ");
			while(filterTokenizer.hasMoreTokens()) {
				String appliedFacet = filterTokenizer.nextToken();
				if(!appliedFacet.isEmpty() && appliedFacet.contains(":")) {
					String[] filterTokens = appliedFacet.split(":");
					switch(Facet.getFacet(filterTokens[0])) {
						case CATEGORY:
							categoryFilters.add(filterTokens[1]);
							break;

						case STYLE:
							styleFilters.add(filterTokens[1]);
							break;

						case SIZE:
							sizeFilters.add(filterTokens[1]);
							break;

						case COLOR:
							colorFilters.add(filterTokens[1]);
							break;

						case FINISH:
							finishFilters.add(filterTokens[1]);
							break;

						case RATING:
							ratingFilters.add(filterTokens[1]);
							break;

						case COLLECTION:
							collectionFilters.add(filterTokens[1]);
							break;

						case PAPER_WEIGHT:
							weightFilters.add(filterTokens[1]);
							break;

						case SEALING_METHOD:
							sealingFilters.add(filterTokens[1]);
							break;

						case PRICE_RANGE:
							priceFilters.add(filterTokens[1]);
							break;

						case INDUSTRY:
							industryFilters.add(filterTokens[1]);
							break;

						case THEME:
							themeFilters.add(filterTokens[1]);
							break;

						case PRODUCT_TYPE:
							productTypeFilters.add(filterTokens[1]);
							break;
					}
				}
			}
			filterMap.put(Facet.CATEGORY, categoryFilters);
			filterMap.put(Facet.SIZE, sizeFilters);
			filterMap.put(Facet.COLOR, colorFilters);
			filterMap.put(Facet.STYLE, styleFilters);
			filterMap.put(Facet.FINISH, finishFilters);
			filterMap.put(Facet.RATING, ratingFilters);
			filterMap.put(Facet.COLLECTION, collectionFilters);
			filterMap.put(Facet.PAPER_WEIGHT, weightFilters);
			filterMap.put(Facet.SEALING_METHOD, sealingFilters);
			filterMap.put(Facet.PRICE_RANGE, priceFilters);
			filterMap.put(Facet.INDUSTRY, industryFilters);
			filterMap.put(Facet.THEME, themeFilters);
			filterMap.put(Facet.PRODUCT_TYPE, productTypeFilters);
		}
		return this.filterMap;
	}

	protected Map<String, String> getAppliedFilters(Map<String, Filter> filtersMap) {
		if(appliedFilters.isEmpty()) {
			StringTokenizer filterTokenizer = new StringTokenizer(getAppliedFacets(), " ");
			while(filterTokenizer.hasMoreTokens()) {
				String appliedFacet = filterTokenizer.nextToken();
				if (!appliedFacet.isEmpty() && appliedFacet.contains(":")) {
					String[] filterTokens = appliedFacet.split(":");
					if(requestType == RefinementsUtil.RequestType.CATEGORY && Facet.getFacet(filterTokens[0]) == Facet.PRODUCT_TYPE) { //Skip ProductType facet in CATEGORY page
						continue;
					}
					if(filtersMap.containsKey(filterTokens[1]) && Facet.getFacet(filterTokens[0]) != Facet.UNKNOWN) {
						appliedFilters.put(filtersMap.get(filterTokens[1]).getFilterName(), Facet.getFacet(filterTokens[0]).name() + "~" + filterTokens[1]);
					} else if(getFiltersMap().containsKey(filterTokens[1]) && Facet.getFacet(filterTokens[0]) != Facet.UNKNOWN) {
						appliedFilters.put(getFiltersMap().get(filterTokens[1]).getFilterName(), Facet.getFacet(filterTokens[0]).name() + "~" + filterTokens[1]);
					}
				}
			}
		}
		widgetResponse.setPageTitle(getPageTitle());
		return appliedFilters;
	}

	protected String getPageTitle() {
		StringBuilder title = new StringBuilder("");
		for (Map.Entry<String, String> appliedFiltersEntry : appliedFilters.entrySet()) {
			title.append((title.length() > 0 ? ", " : "") + appliedFiltersEntry.getKey());
		}
		return title.toString();
	}

	protected String getAppliedFacetsParameter(boolean... encodeFlag) {
		String appliedFacets = getAppliedFacets();
		if(encodeFlag != null && encodeFlag.length == 1 && encodeFlag[0]) {
			try {
				appliedFacets = URLEncoder.encode(appliedFacets, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace(); // TODO Proper logging
			}
		}
		return getAppliedFacets().isEmpty() ? "" : "&af=" + appliedFacets;
	}
	protected void fetchItems(int... _pageOffset) {
		SLIResponse sliJSON;
		String result = "";
		int pageOffset = _pageOffset != null && _pageOffset.length == 1 ? _pageOffset[0] : 0;
		try {
			result = HTTPHelper.getURL(SLIServiceEndpoint + ((UtilValidate.isNotEmpty(this.keyword)) ? "&w=" + this.keyword : "&w=*") + "&cnt=" + pageSize + "&srt=" + (pageOffset * pageSize) + getAppliedFacetsParameter(true) + (UtilValidate.isNotEmpty(this.sortBy) ? "&isort=" + sortBy.replaceAll("\\s", "+") : ""), "GET", null, null, null, null, true, EnvConstantsUtil.RESPONSE_JSON, EnvConstantsUtil.RESPONSE_PLAIN);
			System.out.println(((UtilValidate.isNotEmpty(this.keyword)) ? "&w=" + this.keyword : "&w=*") + "&cnt=" + pageSize + "&srt=" + (pageOffset * pageSize) + getAppliedFacetsParameter(true) + (UtilValidate.isNotEmpty(this.sortBy) ? "&isort=" + sortBy : ""));
			sliJSON = new Gson().fromJson(result, SLIResponse.class);
			if(UtilValidate.isNotEmpty(sliJSON.getJumpURL())) {
				widgetResponse.setJumpURL(sliJSON.getJumpURL());
				return;
			}
			if(items == null) {
				items = new ArrayList<>(sliJSON.getTotalNumberOfItems());
			}
			if(UtilValidate.isNotEmpty(sliJSON.getResults())) {
				items.addAll(sliJSON.getResults());
				if(sliJSON.isFirstPage() || requestType == RefinementsUtil.RequestType.SEARCH) {
					if(sliJSON.isFirstPage() && requestType == RefinementsUtil.RequestType.CATEGORY) {
						BannerInfo bannerInfo = getCategoryBannerInfo();
						widgetResponse.setBannerName(bannerInfo.getBannerName());
						widgetResponse.setBannerTitle(bannerInfo.getBannerTitle());
						widgetResponse.setPageDescription(bannerInfo.getPageDescription());
						widgetResponse.setCategoryId(bannerInfo.getCategoryId());
					}
					if(sliJSON.isFirstPage()) {
						String bannerName = "";
						if(requestType == RefinementsUtil.RequestType.SEARCH) {
							widgetResponse.setBannerName(getBannerName(sliJSON.getBanner()));
						} else if(requestType == RefinementsUtil.RequestType.CATEGORY && UtilValidate.isNotEmpty(bannerName = getBannerName(sliJSON.getBanner()))) {
							widgetResponse.setBannerName(bannerName);
							widgetResponse.setBannerTitle(virtualCategories.get(bannerName));
						}
					}
					if(requestType == RefinementsUtil.RequestType.SEARCH) {
						widgetResponse.setNumOfPages(sliJSON.getTotalNumberOfPages());
						widgetResponse.setNumOfItems(sliJSON.getTotalNumberOfItems());
					}
					widgetResponse.setFacetMap(sliJSON.getFacetMap());
					widgetResponse.setAppliedFilters(getAppliedFilters(sliJSON.getFilterMap()));
				}
				if((requestType == RefinementsUtil.RequestType.CATEGORY || requestType == RefinementsUtil.RequestType.SIZE || requestType == RefinementsUtil.RequestType.DESIGN) && sliJSON.hasMorePages() && this.group) {
					fetchItems(++pageOffset);
				}
			} else {
				widgetResponse.setFacetMap(sliJSON.getFacetMap());
				widgetResponse.setAppliedFilters(getAppliedFilters(sliJSON.getFilterMap()));
			}
		} catch (Exception e) {
			e.printStackTrace();//TODO - Proper logging
		}
	}

	/*public RefinementsWidget fetchColors() {
		SLIResponse sliJSON;
		String result = "";
		try {
			result = HTTPHelper.getURL(SLIServiceEndpoint + "&w=*&cnt=0&srt=1&facetControl=cog2(count=all),*(count=0)", "GET", null, null, null, null, true, EnvConstantsUtil.RESPONSE_JSON, EnvConstantsUtil.RESPONSE_PLAIN);
			System.out.println(SLIServiceEndpoint + "&w=*&cnt=0&srt=1&facetControl=cog2(count=all),*(count=0)");
			sliJSON = new Gson().fromJson(result, SLIResponse.class);
			widgetResponse.setColors((List)sliJSON.getColors().get("values"));
		} catch (Exception e) {
			e.printStackTrace();//TODO - Proper logging
		}
		return this;
	}*/

	public RefinementsWidget fetchColorGroups() {
		SLIResponse sliJSON;
		String result = "";
		try {
			result = HTTPHelper.getURL(SLIServiceEndpoint + "&w=*&cnt=0&srt=1&facetControl=cog1(count=all),cog2(count=all),*(count=0)", "GET", null, null, null, null, true, EnvConstantsUtil.RESPONSE_JSON, EnvConstantsUtil.RESPONSE_PLAIN);
			System.out.println(SLIServiceEndpoint + "&w=*&cnt=0&srt=1&facetControl=cog1(count=all),*(count=0)");
			sliJSON = new Gson().fromJson(result, SLIResponse.class);
			widgetResponse.setColorGroups((List) sliJSON.getColorGroups());
		} catch (Exception e) {
			e.printStackTrace();//TODO - Proper logging
		}
		return this;
	}

	public RefinementsWidget fetchCollections() {
		SLIResponse sliJSON;
		String result = "";
		try {
			result = HTTPHelper.getURL(SLIServiceEndpoint + "&w=*&cnt=0&srt=1&facetControl=col(count=all),*(count=0)", "GET", null, null, null, null, true, EnvConstantsUtil.RESPONSE_JSON, EnvConstantsUtil.RESPONSE_PLAIN);
			System.out.println(SLIServiceEndpoint + "&w=*&cnt=0&srt=1&facetControl=co1(count=all)");
			sliJSON = new Gson().fromJson(result, SLIResponse.class);
			widgetResponse.setCollections((List) sliJSON.getCollections());
		} catch (Exception e) {
			e.printStackTrace();//TODO - Proper logging
		}
		return this;
	}

	public RefinementsWidget fetchSizes() {
		SLIResponse sliJSON;
		String result = "";
		try {
			result = HTTPHelper.getURL(SLIServiceEndpoint + "&w=*&cnt=0&srt=1&facetControl=si(count=all),*(count=0)", "GET", null, null, null, null, true, EnvConstantsUtil.RESPONSE_JSON, EnvConstantsUtil.RESPONSE_PLAIN);
			System.out.println(SLIServiceEndpoint + "&w=*&cnt=0&srt=1&facetControl=si(count=all)");
			sliJSON = new Gson().fromJson(result, SLIResponse.class);
			widgetResponse.setSizes((List) sliJSON.getSizes());
		} catch (Exception e) {
			e.printStackTrace();//TODO - Proper logging
		}
		return this;
	}

	protected Map<String, Filter> getFiltersMap() {
		if(filtersMap.isEmpty()) {
			SLIResponse sliJSON;
			String result = "";
			try {
				result = HTTPHelper.getURL(SLIServiceEndpoint + "&w=*&cnt=0&srt=1&" + Facet.getFacetControl(), "GET", null, null, null, null, true, EnvConstantsUtil.RESPONSE_JSON, EnvConstantsUtil.RESPONSE_PLAIN);
				System.out.println(SLIServiceEndpoint + "&w=*&cnt=0&srt=1&" + Facet.getFacetControl());
				sliJSON = new Gson().fromJson(result, SLIResponse.class);
				this.filtersMap =  sliJSON.getFilterMap();
			} catch (Exception e) {
				e.printStackTrace();//TODO - Proper logging
			}
		}
		return this.filtersMap;
	}


	protected static String getBannerName(String banner) {
		Matcher m = Pattern.compile(".*?\\/?(((\\w|-)+)\\.(?:jpg|png|gif|jpeg))").matcher(banner);
		if(m.find()) {
			return m.group(2);
		}
		return "";
	}

	protected void gatherItems() {

		String productId = "";
		String productSize = "";
		Product product = new Product();
		if(this.group) {
			if(requestType == RefinementsUtil.RequestType.DESIGN) {
				Collections.sort(items, new DesignSizeComparator());
				for (Map<String, Object> item : items) {
					ProductVariant productVariant = new ProductVariant(item, itemUrlPrefix, itemUrlParameters);
					String productVariantSize = productVariant.getProductVariantSize() != null ? productVariant.getProductVariantSize() : "UNKNOWN";
					if (!productVariantSize.equals("UNKNOWN")) {
						if (!productSize.equalsIgnoreCase(productVariantSize)) {
							if (!product.getProductVariants().isEmpty()) {
								if (RefinementsWidgetResponse.getHighPriorityProductNames().contains(product.getProductName())) {
									Collections.sort(product.getProductVariants(), new ProductVariantRankComparator()); //Sort the variants before adding the product to the products collection
									product.setDefaultProductVariant(product.getProductVariants().get(0));//Set the top rated variant as the default variant
//									widgetResponse.getHighPriorityProducts().add(RefinementsWidgetResponse.getHighPriorityProductNames().indexOf(product.getProductName()), product);
									widgetResponse.getHighPriorityProducts().add(product);
								} else if (RefinementsWidgetResponse.getLowPriorityProductNames().contains(product.getProductName())) {
									Collections.sort(product.getProductVariants(), new ProductVariantRankComparator()); //Sort the variants before adding the product to the products collection
									product.setDefaultProductVariant(product.getProductVariants().get(0));//Set the top rated variant as the default variant
//									widgetResponse.getLowPriorityProducts().add(RefinementsWidgetResponse.getLowPriorityProductNames().indexOf(product.getProductName()), product);
									widgetResponse.getLowPriorityProducts().add(product);
								} else {
									Collections.sort(product.getProductVariants(), new ProductVariantRankComparator()); //Sort the variants before adding the product to the products collection
									product.setDefaultProductVariant(product.getProductVariants().get(0));//Set the top rated variant as the default variant
									widgetResponse.addProduct(product);
								}
								product = new Product();
							}
							product.setDefaultProductVariant(productVariant);
						}
						product.addProductVariant(productVariant);
						productSize = productVariantSize;
					}
				}
				if (!product.getProductVariants().isEmpty()) {
					if (RefinementsWidgetResponse.getHighPriorityProductNames().contains(product.getProductName())) {
						Collections.sort(product.getProductVariants(), new ProductVariantRankComparator()); //Sort the variants before adding the product to the products collection
						product.setDefaultProductVariant(product.getProductVariants().get(0));//Set the top rated variant as the default variant
//						widgetResponse.getHighPriorityProducts().add(RefinementsWidgetResponse.getHighPriorityProductNames().indexOf(product.getProductName()), product);
						widgetResponse.getHighPriorityProducts().add(product);
					} else if (RefinementsWidgetResponse.getLowPriorityProductNames().contains(product.getProductName())) {
						Collections.sort(product.getProductVariants(), new ProductVariantRankComparator()); //Sort the variants before adding the product to the products collection
						product.setDefaultProductVariant(product.getProductVariants().get(0));//Set the top rated variant as the default variant
						//widgetResponse.getLowPriorityProducts().add(RefinementsWidgetResponse.getLowPriorityProductNames().indexOf(product.getProductName()), product);
						widgetResponse.getLowPriorityProducts().add(product);
					} else {
						Collections.sort(product.getProductVariants(), new ProductVariantRankComparator()); //Sort the variants before adding the product to the products collection
						product.setDefaultProductVariant(product.getProductVariants().get(0));//Set the top rated variant as the default variant
						widgetResponse.addProduct(product);
					}
				}
			} else {
				Collections.sort(items, new ParentIdComparator());
				for (Map<String, Object> item : items) {
					ProductVariant productVariant = new ProductVariant(item, itemUrlPrefix, itemUrlParameters);
					String productVariantId = productVariant.getProductId() != null ? productVariant.getProductId() : "UNKNOWN";
					if (!productVariantId.equals("UNKNOWN")) {
						if (!productId.equalsIgnoreCase(productVariantId)) {
							if (!product.getProductVariants().isEmpty()) {
								if (RefinementsWidgetResponse.getHighPriorityProductNames().contains(product.getProductName())) {
									Collections.sort(product.getProductVariants(), new ProductVariantRankComparator()); //Sort the variants before adding the product to the products collection
									product.setDefaultProductVariant(product.getProductVariants().get(0));//Set the top rated variant as the default variant
									//widgetResponse.getHighPriorityProducts().add(RefinementsWidgetResponse.getHighPriorityProductNames().indexOf(product.getProductName()), product);
									widgetResponse.getHighPriorityProducts().add(product);
								} else if (RefinementsWidgetResponse.getLowPriorityProductNames().contains(product.getProductName())) {
									Collections.sort(product.getProductVariants(), new ProductVariantRankComparator()); //Sort the variants before adding the product to the products collection
									product.setDefaultProductVariant(product.getProductVariants().get(0));//Set the top rated variant as the default variant
//									widgetResponse.getLowPriorityProducts().add(RefinementsWidgetResponse.getLowPriorityProductNames().indexOf(product.getProductName()), product);
									widgetResponse.getLowPriorityProducts().add(product);
								} else {
									Collections.sort(product.getProductVariants(), new ProductVariantRankComparator()); //Sort the variants before adding the product to the products collection
									product.setDefaultProductVariant(product.getProductVariants().get(0));//Set the top rated variant as the default variant
									widgetResponse.addProduct(product);
								}
								product = new Product();
							}
							product.setDefaultProductVariant(productVariant);
						}
						product.addProductVariant(productVariant);
						productId = productVariantId;
					}
				}
				if (!product.getProductVariants().isEmpty()) {
					if (RefinementsWidgetResponse.getHighPriorityProductNames().contains(product.getProductName())) {
						Collections.sort(product.getProductVariants(), new ProductVariantRankComparator()); //Sort the variants before adding the product to the products collection
						product.setDefaultProductVariant(product.getProductVariants().get(0));//Set the top rated variant as the default variant
//						widgetResponse.getHighPriorityProducts().add(RefinementsWidgetResponse.getHighPriorityProductNames().indexOf(product.getProductName()), product);
						widgetResponse.getHighPriorityProducts().add(product);
					} else if (RefinementsWidgetResponse.getLowPriorityProductNames().contains(product.getProductName())) {
						Collections.sort(product.getProductVariants(), new ProductVariantRankComparator()); //Sort the variants before adding the product to the products collection
						product.setDefaultProductVariant(product.getProductVariants().get(0));//Set the top rated variant as the default variant
//						widgetResponse.getLowPriorityProducts().add(RefinementsWidgetResponse.getLowPriorityProductNames().indexOf(product.getProductName()), product);
						widgetResponse.getLowPriorityProducts().add(product);
					} else {
						Collections.sort(product.getProductVariants(), new ProductVariantRankComparator()); //Sort the variants before adding the product to the products collection
						product.setDefaultProductVariant(product.getProductVariants().get(0));//Set the top rated variant as the default variant
						widgetResponse.addProduct(product);
					}
				}
			}

		} else {
			for (Map<String, Object> item : items) {
				ProductVariant productVariant = new ProductVariant(item, itemUrlPrefix, itemUrlParameters);
				product.setDefaultProductVariant(productVariant);
				product.addProductVariant(productVariant);
				widgetResponse.addProduct(product);
				product = new Product();
			}
		}
	}



	public enum Facet {
		CATEGORY("use"), STYLE("st"), SIZE("si"), COLOR("cog1"), FINISH("finish"), EXACT_COLOR("cog2"), RATING("ra"), COLLECTION("col"), PAPER_WEIGHT("pw"), SEALING_METHOD("sm"), PRICE_RANGE("pb"), INDUSTRY("industry"), THEME("theme"),PRODUCT_TYPE("prodtype"), UNKNOWN("");

		private String sliFacet;

		private Facet(String sliFacet) {
			this.sliFacet = sliFacet;
		}

		public String getSliFacet() {
			return sliFacet;
		}

		public static Facet getFacet(String sliFacet) {
			for (Facet facet : values()) {
				if(facet.getSliFacet().equals(sliFacet)) {
					return facet;
				}
			}
			return UNKNOWN;
		}

		public static String getFacetControl() {
			StringBuilder facetControl = new StringBuilder("facetControl=");
			for (Facet facet : values()) {
				facetControl.append(facet.getSliFacet() + "(count=all),");
			}
			facetControl.append("*(count=0)");
			return facetControl.toString();
		}
	}

	public static Map<String, String> virtualCategories = new HashMap<>();
	static {
		virtualCategories.put("paper_cardstock_category", "Paper & Cardstock");
	}

	class BannerInfo {
		private String bannerName;
		private String bannerTitle;
		private String pageDescription;
		private String categoryId;

		BannerInfo(String bannerName, String bannerTitle, String pageDescription, String categoryId) {
			this.bannerName = bannerName;
			this.bannerTitle = bannerTitle;
			this.pageDescription = pageDescription;
			this.categoryId = categoryId;
		}

		public String getBannerName() {
			return bannerName;
		}

		public String getBannerTitle() {
			return bannerTitle;
		}

		public String getPageDescription() {
			return pageDescription;
		}

		public String getCategoryId() {
			return categoryId;
		}
	}
}
