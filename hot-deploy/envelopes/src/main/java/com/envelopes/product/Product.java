/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.product;

import java.lang.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.order.shoppingcart.ShoppingCart;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartEvents;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.envelopes.scene7.*;
import com.envelopes.util.*;
import org.apache.ofbiz.webapp.website.WebSiteWorker;

import javax.servlet.http.HttpServletRequest;

public class Product {
	public static final String module = Product.class.getName();

	private LocalDispatcher dispatcher = null;
	private Delegator delegator = null;
	private ShoppingCart cart = null;
	private String productId = null;
	private GenericValue product = null;
	private GenericValue warehouse = null;
	private GenericValue category = null;
	public Integer quantity = 0;
	public Integer colorsFront = 0;
	public Integer colorsBack = 0;
	public Boolean isRush = false;
	public Boolean whiteInkFront = false;
	public Boolean whiteInkBack = false;
	public Integer cuts = 0;
	public Boolean isFolded = false;
	public Boolean isFullBleed = false;
	public Integer addresses = 0;
	public BigDecimal price = BigDecimal.ZERO;
	public String templateId = null; //this is used to calculate pricing for a template so that we don't upcharge for front/back of a template
	public String partyId = null;
	public boolean hasPlainPrice = false;
	public boolean hasPrintPrice = false;
	public Integer minPrintColor = 0;
	public Integer maxPrintColor = 0;
	public String hex = null;
	public List<Map<String, Object>> designs = null;
	public GenericValue design = null;
	public GenericValue backDesign = null;
	private Map<String, String> features = null;
	private Map<Integer, Map> prices = null;
	private String pricesJSON = null;
	private Map<Integer, Map> originalPrices = null;
	private String originalPricesJSON = null;
	private List<Map<String, Object>> colors = null;
	private Map<String, Object> colorFilters = null;
	private List<Map<String, Object>> matchingProducts = null;
	private Map<String, Object> reviews = null;
	public Boolean hasAddressing = true;
	public Boolean hasVariableData = true;
	public String webSiteId = null;

	public Product(Delegator delegator, LocalDispatcher dispatcher, String productId, HttpServletRequest request) throws GenericEntityException, GenericServiceException {
		this(delegator, dispatcher, productId, ShoppingCartEvents.getCartObject(request));
		this.webSiteId = WebSiteWorker.getWebSiteId(request);
	}

	public Product(Delegator delegator, LocalDispatcher dispatcher, String productId, ShoppingCart cart) throws GenericEntityException, GenericServiceException {
		this.dispatcher = dispatcher;
		this.delegator = delegator;
		this.cart = cart;
		this.productId = productId;
		this.product = null;
		this.quantity = 0;
		this.colorsFront = 0;
		this.colorsBack = 0;
		this.isRush = false;
		this.whiteInkFront = false;
		this.whiteInkBack = false;
		this.cuts = 0;
		this.isFolded = false;
		this.isFullBleed = false;
		this.addresses = 0;
		this.price = BigDecimal.ZERO;
		this.templateId = null;
		this.partyId = null;
		this.hasPlainPrice = false;
		this.hasPrintPrice = false;
		this.minPrintColor = null;
		this.maxPrintColor = null;
		this.hex = null;
		this.design = null;
		this.backDesign = null;
		this.features = new HashMap<>();
		this.prices = new LinkedHashMap<>();
		this.pricesJSON = null;
		this.originalPrices = new LinkedHashMap<>();
		this.originalPricesJSON = null;
		this.colors = new ArrayList<>();
		this.colorFilters = new HashMap<>();
		this.matchingProducts = new ArrayList<>();
		this.reviews = new HashMap<>();

		//first set the actual product
		this.setProduct(productId);

		this.category = this.product != null && UtilValidate.isNotEmpty(this.getCategoryId()) ? EntityQuery.use(this.delegator).from("ProductCategory").where("productCategoryId", this.getCategoryId()).cache().queryOne() : null;
		this.warehouse = this.product != null ? EntityQuery.use(this.delegator).from("ColorWarehouse").where("variantProductId", this.productId).cache().queryFirst() : null;
		this.hasAddressing = this.product != null ? ProductHelper.hasAddressing(this.product) : false;
		this.hasVariableData = this.product != null ? ProductHelper.hasVariableData(this.product) : false;
	}

	public void setProduct(String productId) throws GenericEntityException {
		this.productId = productId;
		this.product = EntityQuery.use(this.delegator).from("Product").where("productId", productId).cache().queryOne();

		//if this product is null, then chances are it was an old bundle product that we got rid of
		//we will do a look up in the relic table ProductAssoc and get the bundle and map it to its parent
		if(product == null) {
			GenericValue productByAssoc = EntityQuery.use(this.delegator).from("ProductAssoc").where("productIdTo", productId, "productAssocTypeId", "PRODUCT_BUNDLE").cache().queryFirst();

			if(productByAssoc != null) {
				this.product = EntityQuery.use(this.delegator).from("Product").where("productId", productByAssoc.getString("productId")).cache().queryOne();
				this.productId = productByAssoc.getString("productId");
			}
		}
		//if product is still null, then it is an invalid sku
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public void setFeatures() throws GenericEntityException {
		this.features = ProductHelper.getProductFeatures(this.delegator, product, null);
	}

	public void setColors() throws GenericEntityException, GenericServiceException{
		Map<String, Object> colorData = ProductHelper.getProductColors(this.delegator, product);
		this.colors = (List<Map<String, Object>>) colorData.get("colors");
		this.colorFilters = (Map<String, Object>) colorData.get("filters");
	}

	public void setPrices() throws GenericEntityException, GenericServiceException {
		this.prices = ProductHelper.getProductPrice(
													this.cart,
													this.delegator,
													this.dispatcher,
													this.product,
													this.quantity.intValue(),
													this.partyId,
													this.colorsFront.intValue(),
													this.colorsBack.intValue(),
													this.isRush.booleanValue(),
													this.whiteInkFront.booleanValue(),
													this.whiteInkBack.booleanValue(),
													this.cuts.intValue(),
													this.isFolded.booleanValue(),
													this.isFullBleed.booleanValue(),
													this.addresses.intValue(),
													this.price,
													this.templateId
												);
		Gson gson = new GsonBuilder().serializeNulls().create();
		this.pricesJSON = gson.toJson(this.prices);
	}

	public void setOriginalPrices() throws GenericEntityException, GenericServiceException {
		this.originalPrices = ProductHelper.getOriginalPrice(
				this.cart,
				this.delegator,
				this.dispatcher,
				this.product,
				this.quantity.intValue(),
				this.partyId,
				this.colorsFront.intValue(),
				this.colorsBack.intValue(),
				this.isRush.booleanValue(),
				this.whiteInkFront.booleanValue(),
				this.whiteInkBack.booleanValue(),
				this.cuts.intValue(),
				this.isFolded.booleanValue(),
				this.isFullBleed.booleanValue(),
				this.addresses.intValue(),
				this.price,
				this.templateId
		);
		Gson gson = new GsonBuilder().serializeNulls().create();
		this.originalPricesJSON = gson.toJson(this.originalPrices);
	}

	public void setMatchingProducts() throws GenericEntityException {
		List<GenericValue> matchingProducts = ProductHelper.getMatchingProducts(this.delegator, this.product);
		if(UtilValidate.isNotEmpty(matchingProducts)) {
			this.matchingProducts = ProductHelper.getCleanMatchingProductData(this.delegator, matchingProducts);
		}
	}

	public void setReviews() throws GenericEntityException {
		this.reviews = ProductHelper.getProductReviews(this.delegator, this.product, EnvUtil.getSalesChannelEnumId(webSiteId));
	}

	public void setDesigns() {
		this.designs = ProductHelper.getProductDesigns(this.delegator, this);
	}

	public List<Map<String, Object>> getDesigns() {
		if (UtilValidate.isEmpty(this.designs)) {
			setDesigns();
		}

		return this.designs;
	}

	@Deprecated
	public void setDesigns(String designId) throws GenericEntityException, SQLException {
		if(UtilValidate.isNotEmpty(designId)) {
			GenericValue design = EntityQuery.use(this.delegator).from("Scene7Template").where("scene7TemplateId", designId).cache().queryOne();
			if(design != null) {
				this.design = design;
				this.hasAddressing = (UtilValidate.isEmpty(design.getString("hasAddressing")) || "Y".equalsIgnoreCase(design.getString("hasAddressing"))) ? true : false;
				this.hasVariableData = (UtilValidate.isEmpty(design.getString("hasVariableData")) || "Y".equalsIgnoreCase(design.getString("hasVariableData"))) ? true : false;
				Map<String, String> otherSides = Scene7Helper.findOtherSideDesigns(delegator, designId, getParentId(), design);
				if(UtilValidate.isNotEmpty(otherSides) && UtilValidate.isNotEmpty(otherSides.get("back"))) {
					GenericValue backDesign = EntityQuery.use(this.delegator).from("Scene7Template").where("scene7TemplateId", otherSides.get("back")).cache().queryOne();
					if(backDesign != null) {
						this.backDesign = backDesign;
					}
				}
			}

		}
	}

	public void setBaseInkColorsForPrint() throws GenericEntityException {
		if(this.minPrintColor == null) {
			setMinAndMaxPrintColor();
		}
		if(this.design != null && UtilValidate.isNotEmpty(this.design.getLong("colors"))) {
			this.colorsFront = Integer.valueOf(this.design.getLong("colors").intValue());
			if(this.colorsFront < this.minPrintColor) {
				this.colorsFront = this.minPrintColor;
			} else if(this.colorsFront > this.maxPrintColor) {
				this.colorsFront = this.maxPrintColor;
			}
		}

		//TODO: rethink this, dont set yet
		//if(this.backDesign != null && UtilValidate.isNotEmpty(this.backDesign.getLong("colors"))) {
		//	this.colorsBack = Integer.valueOf(this.backDesign.getLong("colors").intValue());
		//	if(this.colorsBack < this.minPrintColor) {
		//		this.colorsBack = this.minPrintColor;
		//	} else if(this.colorsBack > this.maxPrintColor) {
		//		this.colorsBack = this.maxPrintColor;
		//	}
		//}
	}

	public void setMinAndMaxPrintColor() throws GenericEntityException {
		Map<String, Integer> colors = ProductHelper.getMinAndMaxPrintColor(this.delegator, this.product);
		this.minPrintColor = (Integer) colors.get("min");
		this.maxPrintColor = (Integer) colors.get("max");
	}

	public void setAllData() throws GenericEntityException, GenericServiceException {
		setFeatures();
		setPrices();
		setOriginalPrices();
		setColors();
		setMatchingProducts();
		setReviews();
		setMinAndMaxPrintColor();
		setHex();
	}

	public BigDecimal getBasePrice() {
		return ProductHelper.getBaseProductPrice(delegator, dispatcher, product);
	}

	public Map<String, Object> toMap() {
		Map<String, Object> productMap = new HashMap<String, Object>();
		productMap.put("product", this.product);
		productMap.put("quantity", this.quantity);
		productMap.put("colorsFront", this.colorsFront);
		productMap.put("colorsBack", this.colorsBack);
		productMap.put("isRush", this.isRush);
		productMap.put("whiteInkFront", this.whiteInkFront);
		productMap.put("whiteInkBack", this.whiteInkBack);
		productMap.put("cuts", this.cuts);
		productMap.put("isFolded", this.isFolded);
		productMap.put("isFullBleed", this.isFullBleed);
		productMap.put("addresses", this.addresses);
		productMap.put("price", this.price);
		productMap.put("templateId", this.templateId);
		productMap.put("partyId", this.partyId);
		productMap.put("features", this.features);
		productMap.put("hasPlainPrice", this.hasPlainPrice);
		productMap.put("hasPrintPrice", this.hasPrintPrice);
		productMap.put("minPrintColor", this.minPrintColor);
		productMap.put("maxPrintColor", this.maxPrintColor);
		productMap.put("design", this.design);
		productMap.put("hex", this.hex);
		productMap.put("backDesign", this.backDesign);
		productMap.put("prices", this.prices);
		productMap.put("originalPrices", this.originalPrices);
		productMap.put("colors", this.colors);
		productMap.put("colorFilters", this.colorFilters);
		productMap.put("matchingProducts", this.matchingProducts);
		productMap.put("reviews", this.reviews);
		productMap.put("hasAddressing", this.hasAddressing);
		productMap.put("hasVariableData", this.hasVariableData);

		return productMap;
	}

	public String toJSON() {
		Gson gson = new GsonBuilder().serializeNulls().create();
		return gson.toJson(toMap());
	}

	public GenericValue getProduct() throws GenericEntityException {
		return this.product;
	}

	public int getMinPrintColor() throws GenericEntityException {
		if(this.minPrintColor == null) {
			setMinAndMaxPrintColor();
		}
		return this.minPrintColor;
	}

	public int getMaxPrintColor() throws GenericEntityException {
		if(this.maxPrintColor == null) {
			setMinAndMaxPrintColor();
		}
		return this.maxPrintColor;
	}

	public int getColorsFront() throws GenericEntityException {
		return this.colorsFront;
	}

	public int getColorsBack() throws GenericEntityException {
		return this.colorsBack;
	}

	public boolean hasAddressingAbility() throws GenericEntityException {
		return this.hasAddressing.booleanValue();
	}

	public boolean hasVariableDataAbility() throws GenericEntityException {
		return this.hasVariableData.booleanValue();
	}

	public Map<String, Integer> getLeadTime() throws GenericEntityException {
		return ProductHelper.getLeadTime(this.product);
	}

	public Map<Integer, Map> getPrices() throws GenericEntityException, GenericServiceException {
		if(UtilValidate.isEmpty(this.prices)) {
			setPrices();
		}
		return this.prices;
	}

	public String getPricesJSON() throws GenericEntityException, GenericServiceException {
		if(UtilValidate.isEmpty(this.pricesJSON)) {
			setPrices();
		}
		return this.pricesJSON;
	}

	public Map<Integer, Map> getOriginalPrices() throws GenericEntityException, GenericServiceException {
		if(UtilValidate.isEmpty(this.originalPrices)) {
			setOriginalPrices();
		}
		return this.originalPrices;
	}

	public String getOriginalPricesJSON() throws GenericEntityException, GenericServiceException {
		if(UtilValidate.isEmpty(this.originalPricesJSON)) {
			setOriginalPrices();
		}
		return this.originalPricesJSON;
	}

	public List<Map<String, Object>> getColors() throws GenericEntityException, GenericServiceException {
		if(UtilValidate.isEmpty(this.colors)) {
			setColors();
		}
		return this.colors;
	}

	public void setHex() {
		this.hex = (this.warehouse != null && UtilValidate.isNotEmpty(this.warehouse.getString("colorHexCode"))) ? this.warehouse.getString("colorHexCode") : "ffffff";
	}

	public String getHex() {
		return (this.warehouse != null && UtilValidate.isNotEmpty(this.warehouse.getString("colorHexCode"))) ? this.warehouse.getString("colorHexCode") : "ffffff";
	}

	public Map<String, Object> getColorFilters() {
		return this.colorFilters;
	}

	public Map<String, String> getFeatures() throws GenericEntityException {
		if(UtilValidate.isEmpty(this.features)) {
			setFeatures();
		}
		return this.features;
	}

	public String getProductType() {
		return this.product.getString("productTypeId");
	}

	public Integer getMinColors() {
		return this.minPrintColor;
	}

	public Integer getMaxColors() {
		return this.maxPrintColor;
	}

	public String getId() {
		return this.product.getString("productId");
	}

	public String getParentId() {
		return this.product.getString("parentProductId");
	}

	public String getName() {
		return this.product.getString("productName");
	}

	public String getCategoryId() {
		return this.product.getString("primaryProductCategoryId");
	}

	public String getCategoryName() throws GenericEntityException {
		return category != null ? category.getString("categoryName") : null;
	}

	public String getParentCategoryId() throws GenericEntityException {
		return this.category != null ? category.getString("primaryParentCategoryId") : null;
	}

	public String getParentCategoryName() throws GenericEntityException {
		GenericValue category = EntityQuery.use(this.delegator).from("ProductCategory").where("productCategoryId", this.getParentCategoryId()).cache().queryOne();
		return category != null ? category.getString("description") : null;
	}

	public String getMetaTitle() {
		String metaTitle = this.product.getString("metaTitle");

		try {
			if (UtilValidate.isEmpty(metaTitle)) {
				metaTitle = this.getName().replace(" (", ", ").replace(")", ",") + " " + (UtilValidate.isNotEmpty(this.getWeight()) ? this.getWeight() : "") + " " + this.getColor();
			}
		} catch (Exception e) {
			Debug.logError("Error trying to set meta title of a product." + e + " : " + e.getMessage(), module);
		}

		return metaTitle;
	}

	public String getMetaDesc() {
		return this.product.getString("metaDescription");
	}

	public String getWeight() throws GenericEntityException {
		if(UtilValidate.isEmpty(this.features)) {
			getFeatures();
		}

		return this.features.get("PAPER_WEIGHT");
	}

	public String getColor() throws GenericEntityException {
		if(UtilValidate.isEmpty(this.features)) {
			getFeatures();
		}

		return this.features.get("COLOR");
	}

	public String getColorGroup() throws GenericEntityException {
		if(UtilValidate.isEmpty(this.features)) {
			getFeatures();
		}

		return this.features.get("COLOR_GROUP");
	}

	public String getCollection() throws GenericEntityException {
		if(UtilValidate.isEmpty(this.features)) {
			getFeatures();
		}

		return this.features.get("COLLECTION");
	}

	public String getSize() throws GenericEntityException {
		if(UtilValidate.isEmpty(this.features)) {
			getFeatures();
		}

		return this.features.get("SIZE_CODE");
	}

	public String getTexture() throws GenericEntityException {
		if(UtilValidate.isEmpty(this.features)) {
			getFeatures();
		}

		return this.features.get("PAPER_TEXTURE");
	}

	public String getPaperWeight() throws GenericEntityException {
		if(UtilValidate.isEmpty(this.features)) {
			getFeatures();
		}

		return this.features.get("PAPER_WEIGHT");
	}

	public String getActualSize() throws GenericEntityException {
		if(UtilValidate.isEmpty(this.features)) {
			getFeatures();
		}

		return this.features.get("SIZE");
	}

	public String getBrand() throws GenericEntityException {
		if(UtilValidate.isEmpty(this.features)) {
			getFeatures();
		}

		return this.features.get("BRAND");
	}

	public boolean onSale() throws GenericEntityException {
		if("Y".equals(this.product.getString("onSale"))) {
			return true;
		}

		return false;
	}

	public Long percentSavings() throws GenericEntityException {
		return (this.warehouse != null && UtilValidate.isNotEmpty(this.warehouse.getLong("percentSavings"))) ? this.warehouse.getLong("percentSavings") : 0;
	}

	public boolean onClearance() throws GenericEntityException {
		if("Y".equals(this.product.getString("onClearance"))) {
			return true;
		}

		return false;
	}

	public String getColorDescription() {
		return this.product.getString("colorDescription");
	}

	public String getDescription() {
		return this.product.getString("longDescription");
	}

	public String getTagLine() {
		return this.product.getString("tagLine");
	}

	public String getImage() {
		String imageName = this.product.getString("smallImageUrl");
		if(UtilValidate.isNotEmpty(imageName)) {
			imageName = imageName.replace("/images/products/small/","");
		}

		return imageName;
	}

	public boolean isPrintable(boolean checkTemplate) throws GenericEntityException {
		return ProductHelper.isPrintable(this.delegator, this.product, checkTemplate);
	}
	public boolean isPrintable() throws GenericEntityException {
		return ProductHelper.isPrintable(this.delegator, this.product);
	}

	public GenericValue getFrontDesign() {
		if(this.design != null) {
			return this.design;
		}

		return null;
	}

	public GenericValue getBackDesign() {
		if(this.backDesign != null) {
			return this.backDesign;
		}

		return null;
	}

	public String getFrontDesignId() {
		if(this.design != null) {
			return this.design.getString("scene7TemplateId");
		}

		return null;
	}

	public String getBackDesignId() {
		if(this.backDesign != null) {
			return this.backDesign.getString("scene7TemplateId");
		}

		return null;
	}

	public Integer getMaxImageSize() throws GenericEntityException {
		Integer maxImage = null;
		if(UtilValidate.isEmpty(this.features)) {
			getFeatures();
		}

		if(UtilValidate.isNotEmpty(this.features.get("IMAGE_SIZE")) && NumberUtils.isNumber(this.features.get("IMAGE_SIZE"))) {
			maxImage = NumberUtils.toInt(this.features.get("IMAGE_SIZE"));
		}

		return maxImage;
	}

	public boolean hasSample() throws GenericEntityException {
		return ProductHelper.hasSample(delegator, this.product, null);
	}

	public boolean hasCut() throws GenericEntityException {
		return ProductHelper.hasCut(this.delegator, this.product, null);
	}

	public boolean hasFold() throws GenericEntityException {
		return ProductHelper.hasFold(this.delegator, this.product, null);
	}

	public boolean hasRush() throws GenericEntityException {
		return ProductHelper.hasRush(this.product);
	}

	public boolean hasCustomQty() throws GenericEntityException {
		return ProductHelper.hasCustomQty(this.product);
	}

	public boolean hasWhiteInk() throws GenericEntityException {
		if (!UtilValidate.isEmpty(this.product.get("hasWhiteInk")) && this.product.getString("hasWhiteInk").equals("N")) {
			return false;
		}

		return true;
	}

	public boolean isPlain() {
		if(this.colorsFront > 0 || this.colorsBack > 0 || this.isFullBleed) {
			return false;
		}

		return true;
	}

	public boolean hasPeelAndPress() throws GenericEntityException {
		if (UtilValidate.isEmpty(this.features)) {
			this.setFeatures();
		}

		if (this.features.containsKey("SEALING_METHOD") && this.features.get("SEALING_METHOD").equals("Peel & Press™")) {
			return true;
		}

		return false;
	}

	public List<Map<String, Object>> getMatchingProducts() throws GenericEntityException {
		if(UtilValidate.isEmpty(this.matchingProducts)) {
			setMatchingProducts();
		}
		return this.matchingProducts;
	}

	public Map<String, Object> getReviews() throws GenericEntityException {
		if(UtilValidate.isEmpty(this.reviews)) {
			setReviews();
		}
		return this.reviews;
	}

	public List<GenericValue> getProductAssets() throws GenericEntityException {
		return this.getProductAssets("plain", null);
	}

	public List<GenericValue> getProductAssets(String type, String designId) throws GenericEntityException {
		return ProductHelper.getProductAssets(this.delegator, this.product.getString("productId"), type, designId);
	}

	public String getRating() throws GenericEntityException {
		return ProductHelper.getRating(this.delegator, this.product.getString("productId"), EnvUtil.getSalesChannelEnumId(webSiteId));
	}

	public String getPlainPriceDescription() {
		if(this.warehouse != null && UtilValidate.isNotEmpty(this.warehouse.getString("plainPriceDescription"))) {
			return this.warehouse.getString("plainPriceDescription");
		}

		return null;
	}

	public String getPrintPriceDescription() {
		if(this.warehouse != null && UtilValidate.isNotEmpty(this.warehouse.getString("printPriceDescription"))) {
			return this.warehouse.getString("printPriceDescription");
		}

		return null;
	}

	public List<GenericValue> getImprintMethods() throws GenericEntityException {
		return ProductHelper.getAllProductFeaturesByType(this.delegator, this.product, "IMPRINT_METHOD");
	}

	public boolean hasImprintMethod(String imprintMethod) throws GenericEntityException {
		List<GenericValue> imprintMethods = getImprintMethods();
		for(GenericValue method : imprintMethods) {
			if(method.getString("idCode").equalsIgnoreCase(imprintMethod)) {
				return true;
			}
		}

		return false;
	}

	public boolean allowTangStrips() throws GenericEntityException {
		if(UtilValidate.isEmpty(this.features)) {
			getFeatures();
		}

		return UtilValidate.isNotEmpty(this.features.get("ALLOW_TANG")) && "Y".equalsIgnoreCase(this.features.get("ALLOW_TANG"));
	}

	public boolean isValid() {
		if(this.product != null) {
			return true;
		}
		return false;
	}

	public boolean isActive() {
		if(UtilValidate.isNotEmpty(this.product.getTimestamp("salesDiscontinuationDate"))) {
			return false;
		}
		return true;
	}

	public boolean isVirtual() {
		if(UtilValidate.isNotEmpty(this.product.getString("isVirtual")) && this.product.getString("isVirtual").equals("Y")) {
			return true;
		}
		return false;
	}

	public void getFirstActiveChild() throws GenericEntityException {
		this.warehouse = EntityQuery.use(this.delegator).from("ColorWarehouse").where("virtualProductId", this.product.getString("productId")).orderBy("sequenceNum ASC").cache().queryFirst();

		if(this.warehouse != null) {
			setProduct(this.warehouse.getString("variantProductId"));
		}
	}

	public boolean isNew() {
		if(this.warehouse != null && UtilValidate.isNotEmpty(this.warehouse.getString("isNew")) && "Y".equalsIgnoreCase(this.warehouse.getString("isNew"))) {
			return true;
		}

		return false;
	}

	public String getUrl(boolean template) {
		if(template) {
			return "/product/~designId=" + getFrontDesignId();
		}

		return "/product/~category_id=" + ((UtilValidate.isNotEmpty(product.getString("primaryProductCategoryId"))) ? product.getString("primaryProductCategoryId") : "null") + "/~product_id=" + getId();
	}

	public Integer getLayerPosition() {
		if("434_634_LCARD".equalsIgnoreCase(this.product.getString("parentProductId")) || "618_618_LCARD".equalsIgnoreCase(this.product.getString("parentProductId"))) {
			return 1;
		} else if("414_614_LCARD".equalsIgnoreCase(this.product.getString("parentProductId")) || "558_558_CARD".equalsIgnoreCase(this.product.getString("parentProductId"))) {
			return 2;
		} else if("334_534_LCARD".equalsIgnoreCase(this.product.getString("parentProductId")) || "518_518_LCARD".equalsIgnoreCase(this.product.getString("parentProductId"))) {
			return 3;
		}

		return null;
	}

	public boolean isMailable() {
		return ProductHelper.isMailable(this.delegator, this.product, this.productId);
	}

	public boolean isSpecialQuantityOffer() {
		return ProductHelper.isSpecialQuantityOffer(this);
	}

	public boolean showOutOfStockRecommendations() throws GenericEntityException {
		return ProductHelper.showOutOfStockRecommendations(delegator, this.product, null);
	}

	public void clear() {
		//this.dispatcher = null; //WE DO NOT WANT TO CLEAR THESE OUT INCASE THERE ARE NOT AVAILABLE
		//this.delegator = null; //WE DO NOT WANT TO CLEAR THESE OUT INCASE THERE ARE NOT AVAILABLE
		this.productId = null;
		this.product = null;
		this.quantity = 0;
		this.colorsFront = 0;
		this.colorsBack = 0;
		this.isRush = false;
		this.whiteInkFront = false;
		this.whiteInkBack = false;
		this.cuts = 0;
		this.isFolded = false;
		this.isFullBleed = false;
		this.addresses = 0;
		this.price = BigDecimal.ZERO;
		this.templateId = null;
		this.partyId = null;
		this.features = new HashMap<String, String>();
		this.hasPlainPrice = false;
		this.hasPrintPrice = false;
		this.minPrintColor = null;
		this.maxPrintColor = null;
		this.hex = null;
		this.design = null;
		this.backDesign = null;
		this.prices = new LinkedHashMap<Integer, Map>();
		this.pricesJSON = null;
		this.originalPrices = new LinkedHashMap<Integer, Map>();
		this.originalPricesJSON = null;
		this.colors = new ArrayList<Map<String, Object>>();
		this.colorFilters = new HashMap<String, Object>();
		this.matchingProducts = new ArrayList<Map<String, Object>>();
		this.reviews = new HashMap<String, Object>();
		this.hasAddressing = true;
		this.hasVariableData = true;
		this.warehouse = null;
		this.category = null;
	}

	public static Product getProduct(String productId, HttpServletRequest request) throws GenericEntityException, GenericServiceException {
		return new Product((Delegator) request.getAttribute("delegator"), (LocalDispatcher) request.getAttribute("dispatcher"), productId, request);
	}
}