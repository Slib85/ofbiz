package com.envelopes.refinements;

import com.envelopes.refinements.comparator.SortBy;
import com.envelopes.refinements.model.Facet;
import com.envelopes.refinements.model.Product;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Manu on 8/5/2014.
 */
public class RefinementsWidgetResponse {
    private Map<String, Facet> facetMap = new HashMap<>();
    private List<Product> products = new ArrayList<>();
    private List<List<Product>> paginatedProducts = new ArrayList<>();
    private Map<String, String> appliedFilters = new LinkedHashMap<>();
    private String afData = "";
    private static final int PAGE_SIZE = 40;
    private int numOfPages = 0;
    private int numOfItems = 0;
    private RefinementsUtil.RequestType requestType = RefinementsUtil.RequestType.CATEGORY;
    private String bannerName = "";
    private String bannerTitle = "";
	private String pageDescription = "";
	private String categoryId = "";
	private String jumpURL = "";
	private String itemUrlParameters = "";
	private String pageTitle = "";
    private boolean hasError = false;
    private String errorMessage = "";
	private List<Map<String, String>> collections = new ArrayList<>();
	private List<Map<String, String>> sizes = new ArrayList<>();
	private List<Map<String, Object>> colorGroups = new ArrayList<>();
	private List<Map<String, String>> colors = new ArrayList<>();

    public RefinementsWidgetResponse() { }

    public RefinementsWidgetResponse(boolean hasError, String errorMessage) {
        this.hasError = hasError;
        this.errorMessage = errorMessage;
    }

    public Map<String, Facet> getFacetMap() {
        return facetMap;
    }

    public void setFacetMap(Map<String, Facet> facetMap) {
        this.facetMap = facetMap;
    }

    public int getNumberOfPages() {
        if(requestType == RefinementsUtil.RequestType.SEARCH) {
            return numOfPages;
        } else {
			int pageSize = requestType == RefinementsUtil.RequestType.SIZE ? 50 : PAGE_SIZE;
            return products.size() / pageSize + (products.size() % pageSize == 0 ? 0 : 1);
        }
    }

    public List<Product> getProducts(int page, String sortBy) {
        return getPaginatedProducts(sortBy).get(page);
    }

    public List<List<Product>> getPaginatedProducts(String sortBy) {
        this.paginatedProducts.clear();
        List<Product> products = new ArrayList<>(this.products);
        if(SortBy.getSortBy(sortBy) == SortBy.DEFAULT_SORT) {
            Collections.sort(products, SortBy.getSortBy(sortBy).getComparator());
			int idx = 0;
			for (Product highPriorityProduct : highPriorityProducts) {
				products.add(idx ++, highPriorityProduct);
			}
            for (Product lowPriorityProduct : lowPriorityProducts) {
                products.add(lowPriorityProduct);
            }
        } else {
			for (Product highPriorityProduct : highPriorityProducts) {
				products.add(highPriorityProduct);
			}

            for (Product lowPriorityProduct : lowPriorityProducts) {
                products.add(lowPriorityProduct);
            }
            Collections.sort(products, SortBy.getSortBy(sortBy).getComparator());
        }

        List<Product> productsOnPage = new ArrayList<>();
		int pageSize = requestType == RefinementsUtil.RequestType.SIZE ? 50 : PAGE_SIZE;
        for(int i = 0; i < products.size(); i ++ ) {
            if(i % pageSize == 0 && i > 0) {
                paginatedProducts.add(productsOnPage);
                productsOnPage = new ArrayList<>();
            }
            productsOnPage.add(products.get(i));
        }
        if(!productsOnPage.isEmpty()) {
            paginatedProducts.add(productsOnPage);
        }

        return paginatedProducts;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        if(product.isValid()) {
            this.products.add(product);
        }
    }

    public Map<String, String> getAppliedFilters() {
        return appliedFilters;
    }

    public RefinementsUtil.RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RefinementsUtil.RequestType requestType) {
        this.requestType = requestType;
    }

    public void setAppliedFilters(Map<String, String> appliedFilters) {
        this.appliedFilters = appliedFilters;
    }

    public String getAfData() {
        return afData;
    }

    public void setAfData(String afData) {
        this.afData = afData;
    }

    public void setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
    }

    public int getNumOfItems() {
        return numOfItems;
    }

    public void setNumOfItems(int numOfItems) {
        this.numOfItems = numOfItems;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
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

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public List<Map<String, String>> getCollections() {
		return collections;
	}

	public void setCollections(List<Map<String, String>> collections) {
		this.collections = collections;
	}

	public List<Map<String, String>> getSizes() {
		return sizes;
	}

	public void setSizes(List<Map<String, String>> sizes) {
		this.sizes = sizes;
	}

	public List<Map<String, Object>> getColorGroups() {
		return colorGroups;
	}

	public void setColorGroups(List<Map<String, Object>> colorGroups) {

		this.colorGroups = sortColorsInColorGroups(colorGroups);
	}

	public List<Map<String, String>> getColors() {
		return colors;
	}

	public void setColors(List<Map<String, String>> colors) {
		this.colors = colors;
	}

	public void setBannerTitle(String bannerTitle) {
        this.bannerTitle = bannerTitle;
    }

	public void setPageDescription(String pageDescription) {
		this.pageDescription = pageDescription;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public List<Product> getHighPriorityProducts() {
		return highPriorityProducts;
	}

	public List<Product> getLowPriorityProducts() {
        return lowPriorityProducts;
    }

	public static List<String> getHighPriorityProductNames() {
		return highPriorityProductNames;
	}

	public static List<String> getLowPriorityProductNames() {
        return lowPriorityProductNames;
    }

	public String getJumpURL() {
		return jumpURL;
	}

	public void setJumpURL(String jumpURL) {
		this.jumpURL = jumpURL;
	}

	public String getItemUrlParameters() {
		return itemUrlParameters;
	}

	public void setItemUrlParameters(String itemUrlParameters) {
		this.itemUrlParameters = itemUrlParameters;
	}

	protected static List<Map<String, Object>> sortColorsInColorGroups(List<Map<String, Object>> colorGroups) {
		for (Map<String, Object> colorGroup : colorGroups) {
			List colors = (List)colorGroup.get("colors");
			Collections.sort(colors, new Comparator() {
				@Override
				public int compare(Object obj1, Object obj2) {
					Double numberOfProducts1 = (Double)((Map)obj1).get("count");
					Double numberOfProducts2 = (Double)((Map)obj2).get("count");
					return numberOfProducts2.compareTo(numberOfProducts1);
				}
			});
		}
		Collections.sort(colorGroups, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> colorGroup1, Map<String, Object> colorGroup2) {
				Integer numberOfColors1 = (Integer)colorGroup1.get("colorsCount");
				Integer numberOfColors2 = (Integer)colorGroup2.get("colorsCount");
				return numberOfColors2.compareTo(numberOfColors1);
			}
		});
		return colorGroups;
	}

	List<Product> lowPriorityProducts = new ArrayList<>();
    static List<String> lowPriorityProductNames = new ArrayList<>();
    static {
        lowPriorityProductNames.add("Currency Envelopes");
        lowPriorityProductNames.add("Currency Envelope Window");
        lowPriorityProductNames.add("Ticket Envelopes");

    }

	List<Product> highPriorityProducts = new ArrayList<>();
	static List<String> highPriorityProductNames = new ArrayList<>();
	static {
		highPriorityProductNames.add("#10 Regular Envelopes");
		highPriorityProductNames.add("#1 Coin Envelopes");
	}


}
