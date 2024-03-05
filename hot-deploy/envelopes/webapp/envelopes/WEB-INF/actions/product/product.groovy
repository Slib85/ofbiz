import com.envelopes.product.ProductEvents;
import com.envelopes.product.ProductHelper;
import com.envelopes.scene7.Scene7Events;
import com.envelopes.util.EnvUtil;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.webapp.website.WebSiteWorker;

import com.envelopes.cart.CartHelper;
import com.envelopes.product.Product;

import com.bigname.search.elasticsearch.enums.SearchPageType;
import com.bigname.search.elasticsearch.SearchBuilder;
import com.bigname.search.elasticsearch.SearchResult;
import com.bigname.search.elasticsearch.util.SearchUtil;



String module = "product.groovy";

Map<String, Object> params = EnvUtil.getParameterMap(request);
String categoryId = (String) params.get("category_id");
context.put("secret", params.get("s3cr3t"));
try {
	String productId = (UtilValidate.isNotEmpty((String) params.get("product_id"))) ? (String) params.get("product_id") : null;
	String designId = (UtilValidate.isNotEmpty((String) params.get("designId"))) ? (String) params.get("designId") : null;
	String showUpload = (UtilValidate.isNotEmpty((String) params.get("showUpload"))) ? (String) params.get("showUpload") : null;

	context.showUpload = showUpload;

	if(productId == null && UtilValidate.isNotEmpty(designId)) {
		request.setAttribute("saveResponse", true);
		Scene7Events.getProductsForDesign(request, response);
		Map<String, Object> productsForDesign = (Map<String, Object>) request.getAttribute("savedResponse");

		if(UtilValidate.isNotEmpty(productsForDesign) && UtilValidate.isNotEmpty(productsForDesign.get("products")) && productsForDesign.get("products").size() > 0) {
			productId = productsForDesign.get("products").get(0).id;
		}

		context.templateType = (String) params.get("templateType");
	}

	if(productId != null) {
		context.FEATURES_TO_SHOW = ProductEvents.FEATURES_TO_SHOW;
		Product product = new Product(delegator, dispatcher, productId, request);
		if(product.isValid()) {
			if(product.isVirtual()) {
				product.getFirstActiveChild();
			}
			context.product = product;

			context.productSizeAndStyle = ProductHelper.getSortedProductColors(delegator, dispatcher, WebSiteWorker.getWebSiteId(request), (String) product.getProductType(), (String) product.getCategoryId(), (String) product.getSize());

			if(UtilValidate.isNotEmpty(designId)) {
				context.designId = designId;
				product.setDesigns((String) params.get("designId"));
				product.setBaseInkColorsForPrint();

				context.designName = (String) product.design.templateDescription;

				//load addon product
				if(product.getProductType() == "PAPER") {
					Scene7Events.getLowestPricedVariant(request, response);
					Map<String, Object> addOnProductsForDesign = (Map<String, Object>) request.getAttribute("savedResponse");
					if(addOnProductsForDesign.containsKey("productId")) {
						context.addOnProduct = (String) addOnProductsForDesign.get("productId");
					}
				}
			}
			context.crossSells = CartHelper.getCrossSellItems(request);

			if(UtilValidate.isNotEmpty(product.getProduct().getString("primaryProductCategoryId"))) {
				categoryId = product.getProduct().getString("primaryProductCategoryId");
			}

			List breadcrumbs = new ArrayList();
			GenericValue category = delegator.findOne("ProductCategory", UtilMisc.toMap("productCategoryId", categoryId), true);
			breadcrumbs.add(UtilMisc.toMap("name", category != null ? category.description : "", "link", "category/~category_id=" + (category != null ? category.productCategoryId : "")));

			if(UtilValidate.isNotEmpty(category) && UtilValidate.isNotEmpty(category.get("primaryParentCategoryId"))) {
				category = delegator.findOne("ProductCategory", UtilMisc.toMap("productCategoryId", category.getString("primaryParentCategoryId")), true);
				breadcrumbs.add(0, UtilMisc.toMap("name", category != null ? category.description : "", "link", "category/~category_id=" + (category != null ? category.productCategoryId : "")));
			}

			breadcrumbs.add(UtilMisc.toMap("name", product.getName(), "link", ""));

			context.breadcrumbs = breadcrumbs;
			request.getSession().setAttribute("productId", product.getId());
			request.getSession().setAttribute("reviews", product.getReviews());

			List<String> collectionsList = new ArrayList<String>();
			List<String> collectionOrderList = new ArrayList<String>();
			collectionOrderList.add("LUX");
			collectionOrderList.add("Metallics");
			collectionOrderList.add("Grocery Bag");
			collectionOrderList.add("Linen");
			collectionOrderList.add("White");
			collectionOrderList.add("Natural");
			collectionOrderList.add("Fashion");
			collectionOrderList.add("Translucents");
			collectionOrderList.add("Earthtones");
			collectionOrderList.add("Clear");
			collectionOrderList.add("Mirror");
			Iterator it = null;

   			for (int i = 11; i >= 0; i--) {
   				it = ProductHelper.getProductColors(delegator, dispatcher, product.getProduct()).get("filters").get("By Collection").get("choices").entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry)it.next();
					if (pair.getKey().equals(collectionOrderList[0]) && collectionOrderList.size() > 0 && i > 0) {
						collectionsList.add(pair.getKey());
						collectionOrderList.remove(0);
					}
					else if (i == 0) {
						if (!collectionsList.contains(pair.getKey())) {
							collectionsList.add(pair.getKey());
						}
					}
				}
			}

			String productSizeFacetName = null;
			String productStyleFacetName = null;
			String productColorFacetName = null;

			if (UtilValidate.isNotEmpty(product.getActualSize())) {
				productSizeFacetName = SearchUtil.cleanFacet((String) product.getActualSize(), false);
			}

			if (UtilValidate.isNotEmpty(product.getProduct().get("primaryProductCategoryId"))) {
				productStyleFacetName = SearchUtil.cleanFacet((String) product.getProduct().get("primaryProductCategoryId"), false);
			}

			if (UtilValidate.isNotEmpty(product.getColor()) && UtilValidate.isNotEmpty(product.getColorGroup())) {
				productColorFacetName = SearchUtil.cleanFacet((String) product.getColorGroup(), false) + "_" + SearchUtil.cleanFacet((String) product.getColor(), false);
			}

			if (product.showOutOfStockRecommendations()) {
				try {
					// Color Hits
					HashMap<String, String> searchParams = new HashMap<>();
					searchParams.put("af", "si:" + productSizeFacetName + " st:" + productStyleFacetName);

					SearchBuilder builder = new SearchBuilder(delegator, searchParams, WebSiteWorker.getWebSiteId(request), SearchPageType.SEARCH_PAGE);
					builder.executeSearch();

					SearchResult result = builder.getSearchResult();
					context.colorHits = result.getHits();


					// Style Hits
					searchParams = new HashMap<>();
					searchParams.put("af", "si:" + productSizeFacetName + " cog2:" + productColorFacetName);

					builder = new SearchBuilder(delegator, searchParams, WebSiteWorker.getWebSiteId(request), SearchPageType.SEARCH_PAGE);
					builder.executeSearch();

					result = builder.getSearchResult();
					context.styleHits = result.getHits();


					// Size Hits
					searchParams = new HashMap<>();
					searchParams.put("af", "st:" + productStyleFacetName + " cog2:" + productColorFacetName);

					builder = new SearchBuilder(delegator, searchParams, WebSiteWorker.getWebSiteId(request), SearchPageType.SEARCH_PAGE);
					builder.executeSearch();

					result = builder.getSearchResult();
					context.sizeHits = result.getHits();
				} catch (Exception e) {
					EnvUtil.reportError(e);
				}
			}

			context.collectionsList = collectionsList;
		}
	}

	// Get Swatchbook
	Product swatchbook = new Product(delegator, dispatcher, "LUX-SWATCHBOOK", request);
	context.put("swatchBookProduct", swatchbook);




/*
	// Do Elastic Search for Recommendations
	Map<String, Object> params = UtilValidate.isNotEmpty(context.get("params")) ? context.get("params") : EnvUtil.getParameterMap(request);

	try {
		SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request), SearchPageType.SEARCH_PAGE);
		builder.executeSearch();

		SearchResult result = builder.getSearchResult();
		context.pages = result.getTotalPages();
		context.totalHits = result.getTotalHits();
		context.hits = result.getHits();
		context.aggregations = result.getAggregations();
		context.filters = builder.getTranslator().getFilters();
		context.filterUrl = builder.getTranslator().getUrl();
		context.searchPageType = builder.getTranslator().getSearchPageType();
	} catch(Exception e) {
		EnvUtil.reportError(e);
	}*/
} catch (Exception e) {
	EnvUtil.reportError(e);
	Debug.logError("Error loading product: " + params.get("product_id"), module);
}