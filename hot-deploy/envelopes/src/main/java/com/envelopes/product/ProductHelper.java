/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.product;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.*;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.bigname.comparator.ListGenericValueSizeComparator;
import com.bigname.comparator.MapSequenceComparator;
import com.envelopes.http.FileHelper;
import org.apache.commons.io.FileUtils;
import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.base.util.cache.UtilCache;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.model.DynamicViewEntity;
import org.apache.ofbiz.entity.transaction.GenericTransactionException;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.entity.util.EntityFindOptions;
import org.apache.ofbiz.entity.util.EntityListIterator;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.order.shoppingcart.ShoppingCart;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;

import com.envelopes.order.OrderHelper;
import com.envelopes.util.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

public class ProductHelper {
	public static final String module = ProductHelper.class.getName();

	public static final Pattern cardSizePattern = Pattern.compile("\\s?\\(\\s*\\d+((\\s|-)*\\d+\\/\\d+|.\\d+)?((\\s*(?i)x\\s*)(\\d+)((\\s|-)*\\d+\\/\\d+|.\\d+)?)*(\\s*(?i)Closed)?\\s*\\)");
	public static final String freeSamples = UtilProperties.getPropertyValue("envelopes", "freeSamples");

	protected static Map<String, String> FEATURE_TYPE_ID_AND_DESC = new HashMap<String, String>();
	protected static final UtilCache<String, Map<String, Object>> productSizeAndStyle = UtilCache.createUtilCache("envelopes.product.styleandcategory", 0, 0);

	private static final List<String> productsWithNoRush;
	private static final List<String> colorsWithNoWhiteInk;
	private static final List<String> colorsForAutoWhiteInk;
	private static final List<String> reviewType;
	private static final Integer defaultLeadTimePlain = 0;
	private static final Integer defaultLeadTimeStandardPrinted = 3;
	private static final Integer defaultLeadTimeRushPrinted = 3;
	private static final Integer defaultLeadTimeScene7 = 3;
	private static final Map<String, String> inventoryLocation = new HashMap<>();
	private static final List<String> topBlankFolders = new ArrayList<>();

	private static Map<String, Object> sortedProductAttributesByProductId = new HashMap<>();
	private static List<Object> topBlankFoldersData = new ArrayList<>();

	static {
		productsWithNoRush = new ArrayList<String>();
		productsWithNoRush.add("ADD_TO_ME");

		colorsWithNoWhiteInk = new ArrayList<String>();
		colorsWithNoWhiteInk.add("Trendy Teal");
		colorsWithNoWhiteInk.add("Purple Power");
		colorsWithNoWhiteInk.add("Boutique Blue");
		colorsWithNoWhiteInk.add("Silversand");
		colorsWithNoWhiteInk.add("Hottie Pink");
		colorsWithNoWhiteInk.add("Black Satin");
		colorsWithNoWhiteInk.add("Glowing Green");

		colorsForAutoWhiteInk = new ArrayList<String>();
		colorsForAutoWhiteInk.add("Midnight Black");
		colorsForAutoWhiteInk.add("Black Linen");
		colorsForAutoWhiteInk.add("Black Satin");
		colorsForAutoWhiteInk.add("Chocolate");
		colorsForAutoWhiteInk.add("Dark Wash");
		colorsForAutoWhiteInk.add("Purple Power");
		colorsForAutoWhiteInk.add("Grass Print");
		colorsForAutoWhiteInk.add("Sunflower Print");
		colorsForAutoWhiteInk.add("Sand Print");
		colorsForAutoWhiteInk.add("Anthracite Metallic");
		colorsForAutoWhiteInk.add("Bronze Metallic");
		colorsForAutoWhiteInk.add("Sapphire Metallic");

		reviewType = new ArrayList<String>();
		reviewType.add("Describe Yourself");
		reviewType.add("Accounting Services");
		reviewType.add("Banking");
		reviewType.add("Bride");
		reviewType.add("Ecommerce");
		reviewType.add("Education");
		reviewType.add("Event Planner");
		reviewType.add("Florist");
		reviewType.add("Government");
		reviewType.add("Graphic Designer");
		reviewType.add("Groom");
		reviewType.add("Home Improvement Services");
		reviewType.add("Homemaker");
		reviewType.add("Legal Services");
		reviewType.add("Medical Services");
		reviewType.add("Non-Profit");
		reviewType.add("Photographer");
		reviewType.add("Printer");
		reviewType.add("Realtor");
		reviewType.add("Religious Institution");
		reviewType.add("Retail Sales");
		reviewType.add("Stationery Designer");
		reviewType.add("Student");
		reviewType.add("Teacher");
		reviewType.add("Web Designer");

		inventoryLocation.put("1", "A");
		inventoryLocation.put("2", "B");
		inventoryLocation.put("3", "Multi");

		topBlankFolders.add("PF-DBLI");
		topBlankFolders.add("SF-101-546-TANG");
		topBlankFolders.add("LF-118-SG12");
		topBlankFolders.add("CHEL-185-DDBLK100");
		topBlankFolders.add("PF-100WLI");
		topBlankFolders.add("WEL-DB100-GF");
		topBlankFolders.add("WEL-DDBLU100-GF");
		topBlankFolders.add("CHEL-185-DB100");
		topBlankFolders.add("MF-144-SG12");
		topBlankFolders.add("SF-101-DN12");
		topBlankFolders.add("MF-144-DDBLU100");
		topBlankFolders.add("LF-118-DDBLU100");
	}

	/*
	 * Check if product is virtual, if false then its variant
	 */
	public static boolean isVirtual(GenericValue product) {
		if(UtilValidate.isEmpty(product)) {
			return false;
		} else if(UtilValidate.isNotEmpty(product.getString("isVirtual")) && product.getString("isVirtual").equalsIgnoreCase("Y")) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Check if product GenericValue from String
	 */
	public static GenericValue getProduct(Delegator delegator, String productId) throws GenericEntityException {
		GenericValue product = null;
		if(UtilValidate.isNotEmpty(productId)) {
			product = EntityQuery.use(delegator).from("Product").where("productId", productId).cache().queryOne();
		}
		return product;
	}

	public static BigDecimal getBaseProductPrice(Delegator delegator, LocalDispatcher dispatcher, GenericValue product) {
		BigDecimal basePrice = BigDecimal.ZERO;

		try {
			TreeMap<Integer, Map> priceMap = (TreeMap<Integer, Map>)getProductPrice(null, delegator, dispatcher, product,0,null, 0, 0, false, false, false, 0, false, false, 0, BigDecimal.ZERO, null);
			if(UtilValidate.isNotEmpty(priceMap)) {
				basePrice = (BigDecimal) priceMap.get(priceMap.firstKey()).get("price");
			}
		} catch (Exception e) {
			Debug.logError(e, "Error trying to get the lowest price.", module);
		}

		return basePrice;
	}

	/*
	 * Get the product price of a product
	 */
	public static Map<Integer, Map> getProductPrice(ShoppingCart cart, Delegator delegator, LocalDispatcher dispatcher, GenericValue product, int quantity, String partyId, int colorsFront, int colorsBack, boolean isRush, boolean whiteInkFront, boolean whiteInkBack, int cuts, boolean isFolded, boolean isFullBleed, int addresses, BigDecimal customPrice, String templateId) throws GenericEntityException, GenericServiceException {
		if(UtilValidate.isEmpty(product)) {
			return null;
		}

		int colors = getLeastOrMostColors(true, colorsFront, colorsBack);
		boolean multipleSideSurcharge = doMultipleSideSurcharge(product.getString("productTypeId"), templateId);

		Map<Integer, Map> priceMap = new TreeMap<>();

		boolean ignoreProductPriceLookup = false;
		boolean isTrade = false; //check if the party id is a trade customer
		if(cart != null) {
			ignoreProductPriceLookup = cart.getIgnoreProductPriceLookup();
			isTrade = cart.getTradeStatus();
		} else {
			if(UtilValidate.isNotEmpty(partyId)) {
				Map getAEPartyRoles = dispatcher.runSync("getAEPartyRoles", UtilMisc.toMap("partyId", partyId));
				if(UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isTrade"))) {
					isTrade = ((Boolean) getAEPartyRoles.get("isTrade")).booleanValue();
				}
			}
		}

		//first line should always be a single sample for $1 if its not printed
		/*if(colors == 0) {
			priceMap.put(Integer.valueOf(1), applyAdjustmentsToPrice(delegator, dispatcher, product, 1, new BigDecimal("1"), partyId, colorsFront, colorsBack, isRush, whiteInkFront, whiteInkBack, cuts, isFolded, isFullBleed, addresses, isTrade, false, multipleSideSurcharge));
		}*/

		//now get prices in db
		int currentTry = 0;
		int startColor = colors;

		//if sample product, it can be 0 priced
		//if custom product, must be over 0
		if(isSampleSku(product.getString("productId")) || ((isCustomSku(product.getString("productId")) || isPricingEngineSku(delegator, product.getString("productId")) || ignoreProductPriceLookup) && customPrice.compareTo(BigDecimal.ZERO) > 0)) {
			priceMap.put(Integer.valueOf(quantity), applyAdjustmentsToPrice(delegator, dispatcher, product, quantity, customPrice, partyId, colorsFront, colorsBack, isRush, whiteInkFront, whiteInkBack, cuts, isFolded, isFullBleed, addresses, isTrade, true, multipleSideSurcharge));
		} else {
			List<GenericValue> priceList = null;
			while(currentTry < 3) {
				priceList = EntityQuery.use(delegator).from("ProductPrice").where("productId", product.getString("productId"), "productPriceTypeId", "DEFAULT_PRICE").orderBy(UtilMisc.toList("quantity ASC", "colors ASC")).cache().filterByDate().queryList();
				for(GenericValue price : priceList) {
					if(UtilValidate.isNotEmpty(price.getLong("quantity")) && ((UtilValidate.isEmpty(price.getLong("colors")) && colors == 0) || (UtilValidate.isNotEmpty(price.getLong("colors")) && colors == price.getLong("colors").intValue()))) {
						if(isFullBleed && price.getLong("quantity") < 1000) {
							continue;
						}
						priceMap.put(Integer.valueOf(price.getLong("quantity").intValue()), applyAdjustmentsToPrice(delegator, dispatcher, product, price.getLong("quantity").intValue(), price.getBigDecimal("price"), partyId, colorsFront, colorsBack, isRush, whiteInkFront, whiteInkBack, cuts, isFolded, isFullBleed, addresses, isTrade, false, multipleSideSurcharge));
					}
				}

				if(UtilValidate.isEmpty(priceMap)) {
					colors = getNextColor(startColor, colors);
				} else {
					break;
				}

				currentTry++;
			}

			//now get custom qty price
			int minQuantity = 1;
			if(UtilValidate.isNotEmpty(priceMap)){
				minQuantity = ((Integer) (priceMap.keySet().toArray()[0])).intValue();
			}

			if(isValidQuantity(minQuantity, quantity, colors, isFullBleed) && !priceMap.containsKey(Integer.valueOf(quantity))) {
				Map<String, BigDecimal> customPriceMap = applyAdjustmentsToPrice(delegator, dispatcher, product, quantity, getCustomProductPrice(priceMap, quantity, isTrade, hasSample(delegator, product, null), product.getString("productTypeId")), partyId, colorsFront, colorsBack, isRush, whiteInkFront, whiteInkBack, cuts, isFolded, isFullBleed, addresses, isTrade, false, multipleSideSurcharge);
				if(customPriceMap.get("price").compareTo(BigDecimal.ZERO) > 0) {
					priceMap.put(Integer.valueOf(quantity), customPriceMap);
				}
			}
		}

		return priceMap;
	}

	/*
	 * Get the original product price of a product
	 */
	public static Map<Integer, Map> getOriginalPrice(ShoppingCart cart, Delegator delegator, LocalDispatcher dispatcher, GenericValue product, int quantity, String partyId, int colorsFront, int colorsBack, boolean isRush, boolean whiteInkFront, boolean whiteInkBack, int cuts, boolean isFolded, boolean isFullBleed, int addresses, BigDecimal customPrice, String templateId) throws GenericEntityException, GenericServiceException {
		if(UtilValidate.isEmpty(product)) {
			return null;
		}

		int colors = getLeastOrMostColors(true, colorsFront, colorsBack);
		boolean multipleSideSurcharge = doMultipleSideSurcharge(product.getString("productTypeId"), templateId);

		Map<Integer, Map> priceMap = new TreeMap<>();

		boolean ignoreProductPriceLookup = false;
		boolean isTrade = false; //check if the party id is a trade customer
		if(cart != null) {
			ignoreProductPriceLookup = cart.getIgnoreProductPriceLookup();
			isTrade = cart.getTradeStatus();
		} else {
			if(UtilValidate.isNotEmpty(partyId)) {
				Map getAEPartyRoles = dispatcher.runSync("getAEPartyRoles", UtilMisc.toMap("partyId", partyId));
				if(UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isTrade"))) {
					isTrade = ((Boolean) getAEPartyRoles.get("isTrade")).booleanValue();
				}
			}
		}

		//first line should always be a single sample for $1 if its not printed
		/*if(colors == 0) {
			priceMap.put(Integer.valueOf(1), applyAdjustmentsToPrice(delegator, dispatcher, product, 1, new BigDecimal("1"), partyId, colorsFront, colorsBack, isRush, whiteInkFront, whiteInkBack, cuts, isFolded, isFullBleed, addresses, isTrade, false, multipleSideSurcharge));
		}*/

		//now get prices in db
		int currentTry = 0;
		int startColor = colors;

		if(isSampleSku(product.getString("productId")) || ((isCustomSku(product.getString("productId")) || isPricingEngineSku(delegator, product.getString("productId")) || ignoreProductPriceLookup) && customPrice.compareTo(BigDecimal.ZERO) > 0)) {
			priceMap.put(Integer.valueOf(quantity), applyAdjustmentsToPrice(delegator, dispatcher, product, quantity, customPrice, partyId, colorsFront, colorsBack, isRush, whiteInkFront, whiteInkBack, cuts, isFolded, isFullBleed, addresses, isTrade, true, multipleSideSurcharge));
		} else {
			List<GenericValue> priceList = null;
			while (currentTry < 3) {
				priceList = EntityQuery.use(delegator).from("ProductPrice").where("productId", product.getString("productId"), "productPriceTypeId", "DEFAULT_PRICE").orderBy(UtilMisc.toList("quantity ASC", "colors ASC")).cache().filterByDate().queryList();
				for (GenericValue price : priceList) {
					if (UtilValidate.isNotEmpty(price.getLong("quantity")) && ((UtilValidate.isEmpty(price.getLong("colors")) && colors == 0) || (UtilValidate.isNotEmpty(price.getLong("colors")) && colors == price.getLong("colors").intValue()))) {
						if (isFullBleed && price.getLong("quantity") < 1000) {
							continue;
						}
						priceMap.put(Integer.valueOf(price.getLong("quantity").intValue()), applyAdjustmentsToPrice(delegator, dispatcher, product, price.getLong("quantity").intValue(), price.getBigDecimal("originalPrice"), partyId, colorsFront, colorsBack, isRush, whiteInkFront, whiteInkBack, cuts, isFolded, isFullBleed, addresses, isTrade, false, multipleSideSurcharge));
					}
				}

				if (UtilValidate.isEmpty(priceMap)) {
					colors = getNextColor(startColor, colors);
				} else {
					break;
				}

				currentTry++;
			}

			//now get custom qty price
			int minQuantity = 1;
			if (UtilValidate.isNotEmpty(priceMap)) {
				minQuantity = ((Integer) (priceMap.keySet().toArray()[0])).intValue();
			}

			if (isValidQuantity(minQuantity, quantity, colors, isFullBleed) && !priceMap.containsKey(Integer.valueOf(quantity))) {
				Map<String, BigDecimal> customPriceMap = applyAdjustmentsToPrice(delegator, dispatcher, product, quantity, getCustomProductPrice(priceMap, quantity, isTrade, hasSample(delegator, product, null), product.getString("productTypeId")), partyId, colorsFront, colorsBack, isRush, whiteInkFront, whiteInkBack, cuts, isFolded, isFullBleed, addresses, isTrade, false, multipleSideSurcharge);
				if (customPriceMap.get("price").compareTo(BigDecimal.ZERO) > 0) {
					priceMap.put(Integer.valueOf(quantity), customPriceMap);
				}
			}
		}

		return priceMap;
	}

	/**
	 * Determine if we should surcharge for 2 sided printing.
	 * @param templateId
	 * @return
	 */
	public static boolean doMultipleSideSurcharge(String productTypeId, String templateId) {
		return !(UtilValidate.isNotEmpty(templateId) && !templateId.startsWith("8") && UtilValidate.isNotEmpty(productTypeId) && (productTypeId.equalsIgnoreCase("PAPER") || productTypeId.equalsIgnoreCase("CARDSTOCK")));
	}

	/*
	 * Validate Quantity
	 */
	public static boolean isValidQuantity(int minQuantity, int quantity, int colors, boolean isFullBleed) {
		if(quantity >= minQuantity && colors > 0) {
			return true;
		}

		if(((quantity > 0 && quantity <= 5) || (quantity >= minQuantity)) && colors == 0) {
			return true;
		}

		if(isFullBleed && quantity >= 1000) {
			return true;
		}

		return false;
	}

	/*
	 * Calculate custom quantity price
	 */
	public static BigDecimal getCustomProductPrice(Map<Integer, Map> priceMap, int quantity, boolean isTrade, boolean hasSample, String productTypeId) throws GenericServiceException {
		if(quantity == 0) {
			return BigDecimal.ZERO;
		} else if(quantity <= Integer.valueOf(EnvConstantsUtil.FREE_SAMPLES) && hasSample) {
			//if producttype is folders $3, else $1
			if(UtilValidate.isNotEmpty(productTypeId) && "FOLDER".equalsIgnoreCase(productTypeId)) {
				return (new BigDecimal(quantity)).multiply(new BigDecimal("3"));
			} else {
				return (new BigDecimal(quantity)).multiply(new BigDecimal("1"));
			}
		}

		BigDecimal perUnitCost = BigDecimal.ZERO;
		BigDecimal customPrice = BigDecimal.ZERO;

		int closestQtyFound = 0;
		Iterator priceIter = priceMap.keySet().iterator();
		while(priceIter.hasNext()) {
			Integer quantityKey = (Integer) priceIter.next();
			if(quantityKey.intValue() < quantity && quantityKey.intValue() >= closestQtyFound && UtilValidate.isNotEmpty((priceMap.get(quantityKey)).get("unadjustedPrice"))) {
				closestQtyFound = quantityKey.intValue();
				perUnitCost = ((BigDecimal) (priceMap.get(quantityKey)).get("unadjustedPrice")).divide((BigDecimal.valueOf(quantityKey.doubleValue())), EnvConstantsUtil.ENV_SCALE_L, EnvConstantsUtil.ENV_ROUNDING);
			}
		}

		if(perUnitCost.compareTo(BigDecimal.ZERO) > 0) {
			customPrice = BigDecimal.valueOf((Integer.valueOf(quantity)).doubleValue()).multiply(perUnitCost).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);
		}

		return customPrice;
	}

	/*
	 * Determine the min color available for printing
	 */
	public static Map<String, Integer> getMinAndMaxPrintColor(Delegator delegator, GenericValue product) throws GenericEntityException {
		Map<String, Integer> colors = new HashMap<String, Integer>();
		colors.put("min", 0);
		colors.put("max", 0);

		if(product != null) {
			GenericValue warehouse = EntityQuery.use(delegator).from("ColorWarehouse").where("variantProductId", product.getString("productId")).cache().queryFirst();
			if(warehouse != null) {
				colors.put("min", Integer.valueOf(warehouse.getLong("minColors").intValue()));
				colors.put("max", Integer.valueOf(warehouse.getLong("maxColors").intValue()));
			} else {
				List<EntityCondition> productPriceCondList = new ArrayList<EntityCondition>();
				productPriceCondList.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, product.getString("productId")));
				productPriceCondList.add(EntityCondition.makeCondition("colors", EntityOperator.GREATER_THAN_EQUAL_TO, Long.valueOf("1")));
				EntityCondition productPriceCondition = EntityCondition.makeCondition(productPriceCondList, EntityOperator.AND);

				//cannot get cache value here, ofbiz ignores order by when looking in cache
				List<GenericValue> prices = EntityQuery.use(delegator).from("ProductPrice").where(productPriceCondition).orderBy("colors ASC").queryList();
				if(UtilValidate.isNotEmpty(prices)) {
					colors.put("min", Integer.valueOf(prices.get(0).getLong("colors").intValue()));
					colors.put("max", Integer.valueOf(prices.get(prices.size()-1).getLong("colors").intValue()));
				}
			}
		}

		return colors;
	}

	public static Map<String, BigDecimal> applyAdjustmentsToPrice(Delegator delegator, LocalDispatcher dispatcher, GenericValue product, int quantity, BigDecimal price, String partyId, int colorsFront, int colorsBack, boolean isRush, boolean whiteInkFront, boolean whiteInkBack, int cuts, boolean isFolded, boolean isFullBleed, int addresses, boolean isTrade, boolean customProductPrice, boolean multipleSideSurcharge) throws GenericEntityException, GenericServiceException {
		Map<String, BigDecimal> adjustmentDetails = new HashMap<String, BigDecimal>();

		if(UtilValidate.isEmpty(price) || price.compareTo(BigDecimal.ZERO) == 0 || quantity <= 1 || customProductPrice) {
			adjustmentDetails.put("multipleSide", BigDecimal.ZERO);
			adjustmentDetails.put("whiteInk", BigDecimal.ZERO);
			adjustmentDetails.put("cut", BigDecimal.ZERO);
			adjustmentDetails.put("folding", BigDecimal.ZERO);
			adjustmentDetails.put("fullBleed", BigDecimal.ZERO);
			adjustmentDetails.put("addressing", BigDecimal.ZERO);
			adjustmentDetails.put("rush", BigDecimal.ZERO);
			adjustmentDetails.put("unadjustedPrice", price);
			adjustmentDetails.put("price", price);
			return adjustmentDetails;
		}

		BigDecimal twoSidedPrintAdjustmentPrice = getAdjustmentFromMap(quantity, getTwoSidedPrintAdjustment(product, colorsFront, colorsBack, multipleSideSurcharge));
		BigDecimal whiteInkAdjustmentPrice = getAdjustmentFromMap(quantity, getWhiteInkAdjustment(delegator, product, colorsFront, colorsBack, whiteInkFront, whiteInkBack, quantity));
		BigDecimal cutAdjustmentPrice = getAdjustmentFromMap(quantity, getCuttingAdjustment(cuts));
		BigDecimal foldAdjustmentPrice = getAdjustmentFromMap(quantity, getFoldingAdjustment(isFolded));
		BigDecimal fullBleedAdjustmentPrice = getAdjustmentFromMap(quantity, getFullBleedAdjustment(isFullBleed));
		BigDecimal addressingAdjustmentPrice = getAdjustmentFromMap(addresses, getAddressAdjustment(addresses));
		BigDecimal rushAdjustmentPrice = (isRush) ? getRushAdjustment(dispatcher, product, getLeastOrMostColors(true, colorsFront, colorsBack), price, isTrade) : BigDecimal.ZERO;

		adjustmentDetails.put("multipleSide", twoSidedPrintAdjustmentPrice);
		adjustmentDetails.put("whiteInk", whiteInkAdjustmentPrice);
		adjustmentDetails.put("cut", cutAdjustmentPrice);
		adjustmentDetails.put("folding", foldAdjustmentPrice);
		adjustmentDetails.put("fullBleed", fullBleedAdjustmentPrice);
		adjustmentDetails.put("addressing", addressingAdjustmentPrice);
		adjustmentDetails.put("rush", rushAdjustmentPrice);
		adjustmentDetails.put("unadjustedPrice", price);
		adjustmentDetails.put("price", price.add(twoSidedPrintAdjustmentPrice).add(whiteInkAdjustmentPrice).add(cutAdjustmentPrice).add(foldAdjustmentPrice).add(fullBleedAdjustmentPrice).add(addressingAdjustmentPrice).add(rushAdjustmentPrice));

		return adjustmentDetails;
	}

	/*
	 * We charge an additional fee for printing both sides of the envelope. It is the
	 * same price whether we are printing the front OR the back, but only more if we are
	 * printing both sides. The rule is THE SIDE WITH THE MOST COLORS IS CONSIDERED THE
	 * FIRST SIDE, AND GETS THE PRICE FROM THE DATABASE. The second side, gets the price
	 * increase below.
	 */
	public static Map<Integer, BigDecimal> getTwoSidedPrintAdjustment(GenericValue product, int colorsFront, int colorsBack, boolean multipleSideSurcharge) {
		int colors = getLeastOrMostColors(false, colorsFront, colorsBack);
		boolean isEnvelope = isEnvelope(product);

		Map<Integer, BigDecimal> adjustmentMap = new HashMap<Integer, BigDecimal>();
		if(!multipleSideSurcharge) {
			adjustmentMap.put(50, BigDecimal.valueOf(0.0));
			adjustmentMap.put(100, BigDecimal.valueOf(0.0));
			adjustmentMap.put(250, BigDecimal.valueOf(0.0));
			adjustmentMap.put(500, BigDecimal.valueOf(0.0));
			adjustmentMap.put(1000, BigDecimal.valueOf(0.0));
			adjustmentMap.put(2000, BigDecimal.valueOf(0.0));
			adjustmentMap.put(5000, BigDecimal.valueOf(0.0));
			adjustmentMap.put(10000, BigDecimal.valueOf(0.0));
			adjustmentMap.put(20000, BigDecimal.valueOf(0.0));
			adjustmentMap.put(50000, BigDecimal.valueOf(0.0));
		} else if(!isEnvelope) {
			if(colors == 1) {
				adjustmentMap.put(50, BigDecimal.valueOf(30.0));
				adjustmentMap.put(100, BigDecimal.valueOf(40.0));
				adjustmentMap.put(250, BigDecimal.valueOf(44.0));
				adjustmentMap.put(500, BigDecimal.valueOf(50.0));
				adjustmentMap.put(1000, BigDecimal.valueOf(67.0));
				adjustmentMap.put(2000, BigDecimal.valueOf(89.0));
				adjustmentMap.put(5000, BigDecimal.valueOf(156.0));
				adjustmentMap.put(10000, BigDecimal.valueOf(227.0));
				adjustmentMap.put(20000, BigDecimal.valueOf(335.0));
				adjustmentMap.put(50000, BigDecimal.valueOf(670.0));
			} else if(colors == 2) {
				adjustmentMap.put(50, BigDecimal.valueOf(32.0));
				adjustmentMap.put(100, BigDecimal.valueOf(45.0));
				adjustmentMap.put(250, BigDecimal.valueOf(66.0));
				adjustmentMap.put(500, BigDecimal.valueOf(70.0));
				adjustmentMap.put(1000, BigDecimal.valueOf(100.0));
				adjustmentMap.put(2000, BigDecimal.valueOf(135.0));
				adjustmentMap.put(5000, BigDecimal.valueOf(224.0));
				adjustmentMap.put(10000, BigDecimal.valueOf(335.0));
				adjustmentMap.put(20000, BigDecimal.valueOf(500.0));
				adjustmentMap.put(50000, BigDecimal.valueOf(900.0));
			} else if(colors > 2) {
				adjustmentMap.put(50, BigDecimal.valueOf(42.0));
				adjustmentMap.put(100, BigDecimal.valueOf(59.0));
				adjustmentMap.put(250, BigDecimal.valueOf(96.0));
				adjustmentMap.put(500, BigDecimal.valueOf(270.0));
				adjustmentMap.put(1000, BigDecimal.valueOf(350.0));
				adjustmentMap.put(2000, BigDecimal.valueOf(435.0));
				adjustmentMap.put(5000, BigDecimal.valueOf(624.0));
				adjustmentMap.put(10000, BigDecimal.valueOf(835.0));
				adjustmentMap.put(20000, BigDecimal.valueOf(1100.0));
				adjustmentMap.put(50000, BigDecimal.valueOf(1600.0));
			}
		} else {
			if(colors == 1) {
				adjustmentMap.put(50, BigDecimal.valueOf(10.0));
				adjustmentMap.put(100, BigDecimal.valueOf(12.0));
				adjustmentMap.put(250, BigDecimal.valueOf(15.0));
				adjustmentMap.put(500, BigDecimal.valueOf(20.0));
				adjustmentMap.put(1000, BigDecimal.valueOf(30.0));
				adjustmentMap.put(2000, BigDecimal.valueOf(40.0));
				adjustmentMap.put(5000, BigDecimal.valueOf(70.0));
				adjustmentMap.put(10000, BigDecimal.valueOf(80.0));
				adjustmentMap.put(20000, BigDecimal.valueOf(100.0));
				adjustmentMap.put(50000, BigDecimal.valueOf(200.0));
			} else if(colors == 2) {
				adjustmentMap.put(50, BigDecimal.valueOf(15.0));
				adjustmentMap.put(100, BigDecimal.valueOf(17.0));
				adjustmentMap.put(250, BigDecimal.valueOf(20.0));
				adjustmentMap.put(500, BigDecimal.valueOf(30.0));
				adjustmentMap.put(1000, BigDecimal.valueOf(45.0));
				adjustmentMap.put(2000, BigDecimal.valueOf(60.0));
				adjustmentMap.put(5000, BigDecimal.valueOf(100.0));
				adjustmentMap.put(10000, BigDecimal.valueOf(120.0));
				adjustmentMap.put(20000, BigDecimal.valueOf(150.0));
				adjustmentMap.put(50000, BigDecimal.valueOf(300.0));
			} else if(colors > 2) {
				adjustmentMap.put(50, BigDecimal.valueOf(20.0));
				adjustmentMap.put(100, BigDecimal.valueOf(22.0));
				adjustmentMap.put(250, BigDecimal.valueOf(25.0));
				adjustmentMap.put(500, BigDecimal.valueOf(200.0));
				adjustmentMap.put(1000, BigDecimal.valueOf(250.0));
				adjustmentMap.put(2000, BigDecimal.valueOf(350.0));
				adjustmentMap.put(5000, BigDecimal.valueOf(450.0));
				adjustmentMap.put(10000, BigDecimal.valueOf(550.0));
				adjustmentMap.put(20000, BigDecimal.valueOf(650.0));
				adjustmentMap.put(50000, BigDecimal.valueOf(1200.0));
			}
		}

		return adjustmentMap;
	}

	/*
	 * Calculate White ink adjustment
	 */
	public static Map<Integer, BigDecimal> getAddressAdjustment(int addresses) {
		Map<Integer, BigDecimal> adjustmentMap = new HashMap<Integer, BigDecimal>();

		if(addresses > 0) {
			adjustmentMap.put(1, BigDecimal.valueOf(1.15).multiply(new BigDecimal(addresses)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(10, BigDecimal.valueOf(1.15).multiply(new BigDecimal(addresses)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(50, BigDecimal.valueOf(1.15).multiply(new BigDecimal(addresses)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(100, BigDecimal.valueOf(0.87).multiply(new BigDecimal(addresses)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(150, BigDecimal.valueOf(0.87).multiply(new BigDecimal(addresses)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(200, BigDecimal.valueOf(0.75).multiply(new BigDecimal(addresses)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(250, BigDecimal.valueOf(0.75).multiply(new BigDecimal(addresses)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(300, BigDecimal.valueOf(0.55).multiply(new BigDecimal(addresses)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(400, BigDecimal.valueOf(0.55).multiply(new BigDecimal(addresses)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(500, BigDecimal.valueOf(0.48).multiply(new BigDecimal(addresses)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(1000, BigDecimal.valueOf(0.38).multiply(new BigDecimal(addresses)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(2000, BigDecimal.valueOf(0.38).multiply(new BigDecimal(addresses)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(5000, BigDecimal.valueOf(0.38).multiply(new BigDecimal(addresses)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(10000, BigDecimal.valueOf(0.38).multiply(new BigDecimal(addresses)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(20000, BigDecimal.valueOf(0.38).multiply(new BigDecimal(addresses)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(50000, BigDecimal.valueOf(0.38).multiply(new BigDecimal(addresses)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
		}

		return adjustmentMap;
	}

	/**
	 * Calculate rush production adjustment -
	 * Rule is 25% premium with a $100 minimum for 1 or 2 color and $200 for 4 color
	 */
	public static BigDecimal getRushAdjustment(LocalDispatcher dispatcher, GenericValue product, int colors, BigDecimal price, boolean isTrade) throws GenericServiceException {
		BigDecimal adjustment = BigDecimal.ZERO;

		if(isTrade) {
			return adjustment;
		}

		//we have a list of products that should not get their rush markup adjusted if its less then $200
		boolean allowRushMarkup = (UtilValidate.isEmpty(product)) ? false : (!productsWithNoRush.contains(product.getString("productId")));

		if(allowRushMarkup) {
			if(price.compareTo(new BigDecimal("100.00")) < 0) {
				adjustment = price.setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);
			} else if(price.compareTo(new BigDecimal("100.00")) >= 0 && price.compareTo(new BigDecimal("400.00")) <= 0) {
				adjustment = price.multiply(new BigDecimal(".5")).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);
			} else if(price.compareTo(new BigDecimal("400.00")) > 0) {
				adjustment = price.multiply(new BigDecimal(".25")).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);
			}
		}

		return adjustment;
	}

	/*
	 * Calculate White ink adjustment
	 */
	public static Map<Integer, BigDecimal> getWhiteInkAdjustment(Delegator delegator, GenericValue product, int colorsFront, int colorsBack, boolean whiteInkFront, boolean whiteInkBack, int quantity) throws GenericEntityException {
		int numOfWhiteInkSides = 0;
		Map<Integer, BigDecimal> adjustmentMap = new HashMap<Integer, BigDecimal>();

		//check if its a certain color and digital quantity, if so, auto add whiteink to it
		Map<String, String> colorFeature = getProductFeatures(delegator, product, UtilMisc.toList("COLOR"));
		boolean hasWhiteInk = UtilValidate.isNotEmpty(product.getString("hasWhiteInk")) && product.getString("hasWhiteInk").equalsIgnoreCase("Y");

		if(UtilValidate.isNotEmpty(colorFeature) && colorsForAutoWhiteInk.contains(colorFeature.get("COLOR")) && quantity < 500 && hasWhiteInk) {
			if(colorsFront > 0) {
				whiteInkFront = true;
			}
			if(colorsBack > 0) {
				whiteInkBack = true;
			}
		}

		if(colorsFront > 0 && whiteInkFront) {
			numOfWhiteInkSides++;
		}
		if(colorsBack > 0 && whiteInkBack) {
			numOfWhiteInkSides++;
		}

		if(numOfWhiteInkSides == 1) {
			adjustmentMap.put(10, BigDecimal.valueOf(1.00).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(50, BigDecimal.valueOf(0.60).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(100, BigDecimal.valueOf(0.60).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(150, BigDecimal.valueOf(0.50).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(200, BigDecimal.valueOf(0.44).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(250, BigDecimal.valueOf(0.38).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(500, BigDecimal.valueOf(0.35).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(1000, BigDecimal.valueOf(0.30).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(2000, BigDecimal.valueOf(0.30).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(5000, BigDecimal.valueOf(0.30).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(10000, BigDecimal.valueOf(0.30).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(20000, BigDecimal.valueOf(0.30).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(50000, BigDecimal.valueOf(0.30).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
		} else if(numOfWhiteInkSides > 1) {
			adjustmentMap.put(10, BigDecimal.valueOf(2.00).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(50, BigDecimal.valueOf(1.20).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(100, BigDecimal.valueOf(0.60).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(150, BigDecimal.valueOf(0.60).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(200, BigDecimal.valueOf(0.88).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(250, BigDecimal.valueOf(0.76).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(500, BigDecimal.valueOf(0.70).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(1000, BigDecimal.valueOf(0.60).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(2000, BigDecimal.valueOf(0.60).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(5000, BigDecimal.valueOf(0.60).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(10000, BigDecimal.valueOf(0.60).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(20000, BigDecimal.valueOf(0.60).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
			adjustmentMap.put(50000, BigDecimal.valueOf(0.60).multiply(new BigDecimal(quantity)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING));
		}

		return adjustmentMap;
	}

	/*
	 * Calculate price adjustment for cutting.
	 */
	public static Map<Integer, BigDecimal> getCuttingAdjustment(int cuts) {
		Map<Integer, BigDecimal> adjustmentMap = new HashMap<Integer, BigDecimal>();

		if(cuts == 1) {
			adjustmentMap.put(50, BigDecimal.valueOf(20.0));
			adjustmentMap.put(100, BigDecimal.valueOf(20.0));
			adjustmentMap.put(250, BigDecimal.valueOf(20.0));
			adjustmentMap.put(500, BigDecimal.valueOf(20.0));
			adjustmentMap.put(1000, BigDecimal.valueOf(30.0));
			adjustmentMap.put(2000, BigDecimal.valueOf(40.0));
			adjustmentMap.put(5000, BigDecimal.valueOf(70.0));
			adjustmentMap.put(10000, BigDecimal.valueOf(120.0));
			adjustmentMap.put(20000, BigDecimal.valueOf(220.0));
			adjustmentMap.put(50000, BigDecimal.valueOf(520.0));
		} else if(cuts == 2) {
			adjustmentMap.put(50, BigDecimal.valueOf(40.0));
			adjustmentMap.put(100, BigDecimal.valueOf(40.0));
			adjustmentMap.put(250, BigDecimal.valueOf(40.0));
			adjustmentMap.put(500, BigDecimal.valueOf(40.0));
			adjustmentMap.put(1000, BigDecimal.valueOf(60.0));
			adjustmentMap.put(2000, BigDecimal.valueOf(80.0));
			adjustmentMap.put(5000, BigDecimal.valueOf(140.0));
			adjustmentMap.put(10000, BigDecimal.valueOf(240.0));
			adjustmentMap.put(20000, BigDecimal.valueOf(440.0));
			adjustmentMap.put(50000, BigDecimal.valueOf(1040.0));
		}

		return adjustmentMap;
	}

	/*
	 * Calculate price adjustment for folding.
	 */
	public static Map<Integer, BigDecimal> getFoldingAdjustment(boolean folded) {
		Map<Integer, BigDecimal> adjustmentMap = new HashMap<Integer, BigDecimal>();

		if(folded) {
			adjustmentMap.put(50, BigDecimal.valueOf(50.0));
			adjustmentMap.put(100, BigDecimal.valueOf(50.0));
			adjustmentMap.put(250, BigDecimal.valueOf(50.0));
			adjustmentMap.put(500, BigDecimal.valueOf(50.0));
			adjustmentMap.put(1000, BigDecimal.valueOf(75.0));
			adjustmentMap.put(2000, BigDecimal.valueOf(100.0));
			adjustmentMap.put(5000, BigDecimal.valueOf(175.0));
			adjustmentMap.put(10000, BigDecimal.valueOf(300.0));
			adjustmentMap.put(20000, BigDecimal.valueOf(550.0));
			adjustmentMap.put(50000, BigDecimal.valueOf(1300.0));
		}

		return adjustmentMap;
	}

	/*
	 * Calculate price adjustment for full bleed.
	 */
	public static Map<Integer, BigDecimal> getFullBleedAdjustment(boolean fullBleed) {
		Map<Integer, BigDecimal> adjustmentMap = new HashMap<Integer, BigDecimal>();

		if(fullBleed) {
			adjustmentMap.put(1000, BigDecimal.valueOf(300.0));
			adjustmentMap.put(2000, BigDecimal.valueOf(500.0));
			adjustmentMap.put(5000, BigDecimal.valueOf(700.0));
			adjustmentMap.put(10000, BigDecimal.valueOf(1000.0));
			adjustmentMap.put(20000, BigDecimal.valueOf(1500.0));
			adjustmentMap.put(50000, BigDecimal.valueOf(2500.0));
		}

		return adjustmentMap;
	}


	/*
	 * Find the side with least or most colors
	 * If the item is folded, front and back are the same side
	 */
	public static int getLeastOrMostColors(boolean mostColors, int colorsFront, int colorsBack) {
		int colors = 0;
		if(mostColors) {
			if(colorsFront > colorsBack) {
				colors = colorsFront;
			} else {
				colors = colorsBack;
			}
		} else {
			if(colorsFront > 0 && colorsFront <= colorsBack) {
				colors = colorsFront;
			} else if(colorsBack > 0 && colorsBack <= colorsFront) {
				colors = colorsBack;
			} else {
				colors = 0;
			}
		}

		return colors;
	}

	/*
	 * Return whether the product is an envelope, false if its a paper
	 */
	public static boolean isEnvelope(GenericValue product) {
		if(UtilValidate.isNotEmpty(product) && UtilValidate.isNotEmpty(product.getString("productTypeId")) && product.getString("productTypeId").equalsIgnoreCase("ENVELOPE")) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Get the side with the most colors
	 */
	public static boolean isProductFolded(Delegator delegator, GenericValue product) throws GenericEntityException {
		if(UtilValidate.isEmpty(product)) {
			return false;
		}

		boolean isProductInCategory = isProductInCategory(delegator, product, null, "FOLDED");
		if(isProductInCategory) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Return the product features of a product, can only have 1 feature per feature type
	 * @param delegator
	 * @param product
	 * @param productFeatureTypeIds
	 * @return
	 * @throws GenericEntityException
	 */
	public static Map<String, String> getProductFeatures(Delegator delegator, GenericValue product, List<String> productFeatureTypeIds) throws GenericEntityException {
		return getProductFeatures(delegator, product, productFeatureTypeIds, false);
	}

	/**
	 * Return the product features of a product, can only have 1 feature per feature type
	 * @param delegator
	 * @param product
	 * @param productFeatureTypeIds
	 * @param showFeatureTypeDesc
	 * @return
	 * @throws GenericEntityException
	 */
	public static Map<String, String> getProductFeatures(Delegator delegator, GenericValue product, List<String> productFeatureTypeIds, boolean showFeatureTypeDesc) throws GenericEntityException {
		if(UtilValidate.isEmpty(product)) {
			return null;
		}

		Map<String, String> productFeatures = new HashMap<String, String>();
		Map<String, String> sortedProductFeatures = new LinkedHashMap<String, String>();

		List<GenericValue> features = null;
		if(UtilValidate.isEmpty(productFeatureTypeIds)) {
			features = EntityQuery.use(delegator).from("ProductFeatureAndAppl").where("productId", product.getString("productId")).cache().queryList();
		} else {
			List<EntityCondition> productFeatureCondList = new ArrayList<EntityCondition>();
			productFeatureCondList.add(EntityCondition.makeCondition("productFeatureTypeId", EntityOperator.IN, productFeatureTypeIds));
			productFeatureCondList.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, product.getString("productId")));
			EntityCondition productFeatureCondition = EntityCondition.makeCondition(productFeatureCondList, EntityOperator.AND);
			features = EntityQuery.use(delegator).from("ProductFeatureAndAppl").where(productFeatureCondition).cache().queryList();
		}

		for(GenericValue feature : features) {
			if(feature.getString("productFeatureTypeId").equals("AVAILABILITY")) {
				Map<String, Integer> leadTime = getLeadTime(product);
				String plainAvailabilityInfo = feature.getString("description").replace(".","") + ".";
				String printedAvailabilityInfo = "";

				if (ProductHelper.isPrintable(delegator, product)) {
					printedAvailabilityInfo += "\nPrinted Items - Ship based on production time selected (additional " + (hasRush(product) ? leadTime.get("leadTimeRushPrinted").toString() + "-" + leadTime.get("leadTimeStandardPrinted").toString() : leadTime.get("leadTimeStandardPrinted").toString()) + " business days).";
				}

				if (UtilValidate.isNotEmpty(printedAvailabilityInfo)) {
					productFeatures.put(feature.getString("productFeatureTypeId"), "Plain Items - " + plainAvailabilityInfo + printedAvailabilityInfo);
				} else {
					productFeatures.put(feature.getString("productFeatureTypeId"), plainAvailabilityInfo);
				}
			} else {
				productFeatures.put(feature.getString("productFeatureTypeId"), feature.getString("description"));
			}
		}

		//now sort it
		if(UtilValidate.isNotEmpty(productFeatureTypeIds)) {
			for(String productFeatureTypeId : productFeatureTypeIds) {
				if(productFeatures.containsKey(productFeatureTypeId)) {
					sortedProductFeatures.put(showFeatureTypeDesc ? (EntityQuery.use(delegator).from("ProductFeatureType").where("productFeatureTypeId", productFeatureTypeId).cache().queryOne()).getString("description") : productFeatureTypeId, productFeatures.get(productFeatureTypeId));
				}
			}
			return sortedProductFeatures;
		}

		return productFeatures;
	}

	/**
	 * Get all feature for type, even if there are multiple
	 * @param delegator
	 * @param product
	 * @param productFeatureTypeId
	 * @return
	 * @throws GenericEntityException
	 */
	public static List<GenericValue> getAllProductFeaturesByType(Delegator delegator, GenericValue product, String productFeatureTypeId) throws GenericEntityException {
		if(UtilValidate.isEmpty(product)) {
			return null;
		}

		List<GenericValue> features = null;
		List<EntityCondition> productFeatureCondList = new ArrayList<>();
		productFeatureCondList.add(EntityCondition.makeCondition("productFeatureTypeId", EntityOperator.EQUALS, productFeatureTypeId));
		productFeatureCondList.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, product.getString("productId")));
		EntityCondition productFeatureCondition = EntityCondition.makeCondition(productFeatureCondList, EntityOperator.AND);
		features = EntityQuery.use(delegator).from("ProductFeatureAndAppl").where(productFeatureCondition).cache().queryList();

		return features;
	}

	public static Map<String, Object> getProductFeaturesByType(Delegator delegator, GenericValue product, String productFeatureTypeId) throws GenericEntityException {
		if(UtilValidate.isEmpty(product)) {
			return null;
		}

		Map<String, Object> productFeatures = new HashMap<>();

		List<GenericValue> features = null;
		List<EntityCondition> productFeatureCondList = new ArrayList<>();
		productFeatureCondList.add(EntityCondition.makeCondition("productFeatureTypeId", EntityOperator.EQUALS, productFeatureTypeId));
		productFeatureCondList.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, product.getString("productId")));
		EntityCondition productFeatureCondition = EntityCondition.makeCondition(productFeatureCondList, EntityOperator.AND);
		features = EntityQuery.use(delegator).from("ProductFeatureAndAppl").where(productFeatureCondition).cache().queryList();

		for(GenericValue feature : features) {
			List<GenericValue> assocFeatures = EntityQuery.use(delegator).from("ProductFeatureAndAssoc").where("productId", product.getString("productId"), "productFeatureIdFrom", feature.getString("productFeatureId")).orderBy(UtilMisc.toList("productFeatureTypeId ASC")).cache().queryList();

			Map<String, List<Map<String, String>>> assocs = new HashMap<>();
			if(UtilValidate.isNotEmpty(assocFeatures)) {
				String assocFeatureTypeId = null; //reset marker
				List<Map<String, String>> assocList = new ArrayList<>();
				for(GenericValue assocFeature : assocFeatures) {
					if(assocFeatureTypeId == null) {
						assocFeatureTypeId = assocFeature.getString("productFeatureTypeId");
					}

					if(!assocFeatureTypeId.equalsIgnoreCase(assocFeature.getString("productFeatureTypeId"))) {
						//reset
						assocs.put(assocFeatureTypeId, UtilMisc.<Map<String, String>>toList(assocList));
						assocFeatureTypeId = assocFeature.getString("productFeatureTypeId");
						assocList = new ArrayList<>();
					}

					Map<String, String> assocData = new HashMap<>();
					assocData.put("id", assocFeature.getString("productFeatureId"));
					assocData.put("desc", assocFeature.getString("description"));
					assocData.put("name", getFeatureDescByType(delegator, assocFeatureTypeId));
					assocList.add(assocData);
				}
				assocs.put(assocFeatureTypeId, UtilMisc.<Map<String, String>>toList(assocList));
			}

			productFeatures.put(feature.getString("productFeatureId"), UtilMisc.<String, String>toMap("type", feature.getString("productFeatureTypeId"), "desc", feature.getString("description"), "assocs", assocs));
		}

		return productFeatures;
	}

	/**
	 * Return the product features of a product
	 * @param delegator
	 * @param product
	 * @param productFeatureTypeIds
	 * @return
	 * @throws GenericEntityException
	 */
	public static List<String> getProductFeatureIds(Delegator delegator, GenericValue product, List<String> productFeatureTypeIds) throws GenericEntityException {
		if(UtilValidate.isEmpty(product)) {
			return null;
		}

		List<String> productFeatures = new ArrayList<String>();
		List<GenericValue> features = null;
		if(UtilValidate.isEmpty(productFeatureTypeIds)) {
			features = EntityQuery.use(delegator).from("ProductFeatureAndAppl").where("productId", product.getString("productId")).cache().queryList();
		} else {
			List<EntityCondition> productFeatureCondList = new ArrayList<EntityCondition>();
			productFeatureCondList.add(EntityCondition.makeCondition("productFeatureTypeId", EntityOperator.IN, productFeatureTypeIds));
			productFeatureCondList.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, product.getString("productId")));
			EntityCondition productFeatureCondition = EntityCondition.makeCondition(productFeatureCondList, EntityOperator.AND);
			features = EntityQuery.use(delegator).from("ProductFeatureAndAppl").where(productFeatureCondition).cache().queryList();
		}

		for(GenericValue feature : features) {
			productFeatures.add(feature.getString("productFeatureId"));
		}

		return productFeatures;
	}

	/**
	 * Does product have a feature
	 * @param delegator
	 * @param product
	 * @param productFeatureTypeId
	 * @return
	 * @throws GenericEntityException
	 */
	public static boolean doesProductHaveFeature(Delegator delegator, GenericValue product, String productFeatureTypeId) throws GenericEntityException {
		if(UtilValidate.isEmpty(product) || UtilValidate.isEmpty(productFeatureTypeId)) {
			return false;
		}

		List<GenericValue> features = EntityQuery.use(delegator).from("ProductFeatureAndAppl").where("productId", product.getString("productId"), "productFeatureTypeId", productFeatureTypeId).cache().queryList();
		return UtilValidate.isNotEmpty(features);
	}

	/**
	 * Delete a feature
	 * @param delegator
	 * @param product
	 * @param productFeatureTypeId
	 * @throws GenericEntityException
	 */
	public static void deleteFeatureAppl(Delegator delegator, GenericValue product, String productFeatureTypeId) throws GenericEntityException {
		List<GenericValue> productFeatureAndAppls = EntityQuery.use(delegator).from("ProductFeatureAndAppl").where("productId", product.getString("productId"), "productFeatureTypeId", productFeatureTypeId).queryList();
		for(GenericValue feature : productFeatureAndAppls) {
			List<GenericValue> productFeatureAppls = EntityQuery.use(delegator).from("ProductFeatureAppl").where("productId", product.getString("productId"), "productFeatureId", feature.getString("productFeatureId")).queryList();
			for(GenericValue feature2 : productFeatureAppls) {
				feature2.remove();
			}
		}
	}

	/**
	 * Create a feature
	 * @param delegator
	 * @param product
	 * @param productFeatureId
	 * @return
	 * @throws GenericEntityException
	 */
	public static GenericValue createFeatureAppl(Delegator delegator, GenericValue product, String productFeatureId) throws GenericEntityException {
		GenericValue feature = delegator.makeValue("ProductFeatureAppl", UtilMisc.toMap("productId", product.getString("productId")));
		feature.put("fromDate", UtilDateTime.nowTimestamp());
		feature.put("productFeatureId", productFeatureId);
		feature.put("productFeatureApplTypeId", "STANDARD_FEATURE");
		delegator.create(feature);

		return feature;
	}

	/**
	 * Get Category
	 * @param delegator
	 * @param categoryId
	 * @return
	 * @throws GenericEntityException
	 */
	public static GenericValue getProductCategory(Delegator delegator, String categoryId) throws GenericEntityException {
		if(UtilValidate.isEmpty(categoryId)) {
			return null;
		}

		return EntityQuery.use(delegator).from("ProductCategory").where("productCategoryId", categoryId).cache(true).queryOne();
	}

	/**
	 * Return the product categories of a product
	 * @param delegator
	 * @param product
	 * @return
	 * @throws GenericEntityException
	 */
	public static Map<String, String> getProductCategories(Delegator delegator, GenericValue product) throws GenericEntityException {
		if(UtilValidate.isEmpty(product)) {
			return null;
		}

		Map<String, String> productCategories = new HashMap<String, String>();
		List<GenericValue> categories = EntityQuery.use(delegator).from("ProductCategoryAndMember").where("productId", product.getString("productId")).cache().queryList();
		for(GenericValue category : categories) {
			productCategories.put(category.getString("productCategoryId"), category.getString("description"));
		}

		return productCategories;
	}

	/**
	 * Does product have belong to a category
	 * @param delegator
	 * @param product
	 * @param productCategoryId
	 * @param description
	 * @return
	 * @throws GenericEntityException
	 */
	public static boolean isProductInCategory(Delegator delegator, GenericValue product, String productCategoryId, String description) throws GenericEntityException {
		boolean result = false;

		if(UtilValidate.isEmpty(product)) {
			return result;
		}

		List<GenericValue> categories = null;
		if(UtilValidate.isNotEmpty(productCategoryId)) {
			categories = EntityQuery.use(delegator).from("ProductAndCategoryMember").where("productId", product.getString("productId"), "productCategoryId", productCategoryId).cache().queryList();
			return UtilValidate.isNotEmpty(categories);
		}


		if(UtilValidate.isNotEmpty(description)) {
			categories = EntityQuery.use(delegator).from("ProductAndCategoryMember").where("productId", product.getString("productId")).cache().queryList();
			for(GenericValue category : categories) {
				if(UtilValidate.isNotEmpty(category.getString("description")) && category.getString("description").toUpperCase().contains(description.toUpperCase())) {
					result = true;
				} else {
					result = false;
				}
			}
		}

		return result;
	}

	/**
	 * Get all products for a given product category
	 * The category to pass should be PRODUCT.PRIMARY_PRODUCT_CATEGORY_ID
	 * @param delegator
	 * @param productCategoryId
	 * @return
	 * @throws GenericEntityException
	 */
	public static List<GenericValue> getAllProductsInCategory(Delegator delegator, String webSiteId, String productTypeId, String productCategoryId) throws GenericEntityException {
		List<GenericValue> allSizes = new ArrayList<>();

		//get category for given product
		GenericValue category = EntityQuery.use(delegator).from("ProductCategory").where("productCategoryId", productCategoryId).cache().queryOne();
		if(category != null && UtilValidate.isNotEmpty(category.getString("primaryParentCategoryId"))) {
			//get all products in category
			List<GenericValue> allProductsInCategory = EntityQuery.use(delegator).from("ColorAndWebSite").where("primaryProductCategoryId", productCategoryId, "productTypeId", productTypeId, webSiteId, "Y").cache().queryList();

			if(UtilValidate.isNotEmpty(allProductsInCategory)) {
				//get all sizes from products
				List<String> sizes = EntityUtil.getFieldListFromEntityList(allProductsInCategory, "sizeDescription", true);

				//get detailed info about all the products in those sizes and their categories
				List<EntityCondition> conditions = new ArrayList<>();
				conditions.add(EntityCondition.makeCondition("sizeDescription", EntityOperator.IN, sizes));
				conditions.add(EntityCondition.makeCondition(webSiteId, EntityOperator.EQUALS, "Y"));
				conditions.add(EntityCondition.makeCondition("productTypeId", EntityOperator.EQUALS, productTypeId));
				allSizes = EntityQuery.use(delegator).from("ColorAndWebSite").where(EntityCondition.makeCondition(conditions, EntityOperator.AND)).cache().queryList();

				//remove any size that doesnt have at least 1 product in the original category
				List<String> sizesToRemove = new ArrayList<>();
				for(String size : sizes) {
					List<GenericValue> allProductsForSize = EntityUtil.filterByAnd(allSizes, UtilMisc.toMap("sizeDescription", size));
					List<String> productCategories = EntityUtil.getFieldListFromEntityList(allProductsForSize, "primaryProductCategoryId", true);
					if(!productCategories.contains(productCategoryId)) {
						sizesToRemove.add(size);
					}
				}

				if(sizesToRemove.size() > 0) {
					for(String sizeToRemove: sizesToRemove) {
						sizes.remove(sizeToRemove);
					}

					conditions.clear();
					conditions.add(EntityCondition.makeCondition("sizeDescription", EntityOperator.IN, sizes));
					conditions.add(EntityCondition.makeCondition(webSiteId, EntityOperator.EQUALS, "Y"));
					conditions.add(EntityCondition.makeCondition("productTypeId", EntityOperator.EQUALS, productTypeId));
					allSizes = EntityQuery.use(delegator).from("ColorAndWebSite").where(EntityCondition.makeCondition(conditions, EntityOperator.AND)).cache().queryList();
				}
			}
		}

		Collections.sort(allSizes, new ListGenericValueSizeComparator(true));
		return allSizes;
	}

	public static Map getProductAndStyle(Delegator delegator, LocalDispatcher dispatcher, List<GenericValue> products) throws GenericEntityException, GenericServiceException {
		Map styleNames = new HashMap();
		Map styles = new HashMap<>();
		Map sizes = new HashMap<>();
		List<String> sortedSizes = new ArrayList<>();

		for(GenericValue prod : products) {
			ShoppingCart cart = null;
			Product product = new Product(delegator, dispatcher, prod.getString("variantProductId"), cart);

			Map<String, Object> productMap = new HashMap<>();
			productMap.put("productId", product.getId());
			productMap.put("parentProductId", product.getParentId());
			productMap.put("productTypeId", product.getProductType());
			productMap.put("name", product.getName());
			productMap.put("category", product.getParentCategoryId());
			productMap.put("style", product.getCategoryId());
			productMap.put("size", product.getSize());
			productMap.put("actualSize", product.getActualSize());
			productMap.put("weight", product.getPaperWeight());
			productMap.put("colorGroup", product.getColorGroup());
			productMap.put("collection", product.getCollection());
			productMap.put("color", product.getColor());
			productMap.put("texture", product.getTexture());
			productMap.put("savings", product.percentSavings());
			productMap.put("rating", product.getRating());
			productMap.put("minColors", product.getMinPrintColor());
			productMap.put("maxColors", product.getMaxPrintColor());
			productMap.put("hasSample", product.hasSample());
			productMap.put("hasRush", product.hasRush());
			productMap.put("hasAddressing", product.hasAddressingAbility());
			productMap.put("hasWhiteInk", product.hasWhiteInk());
			productMap.put("hasCustomQty", product.hasCustomQty());
			productMap.put("hasPeelAndress", UtilValidate.isNotEmpty(prod.getString("sealingMethod")) && prod.getString("sealingMethod").contains("Peel"));
			productMap.put("brand", prod.getString("brandDescription"));
			productMap.put("plainDescription", prod.getString("plainPriceDescription"));
			productMap.put("printDescription", prod.getString("printPriceDescription"));
			productMap.put("hex", product.getHex());
			productMap.put("new", product.isNew());
			productMap.put("sale", product.onSale());
			productMap.put("clearance", product.onClearance());
			productMap.put("printable", product.isPrintable());
			productMap.put("metaTitle", product.getMetaTitle());
			productMap.put("isSpecialQuantityOffer", product.isSpecialQuantityOffer());
			productMap.put("sequenceNum", prod.getString("sequenceNum"));

			if(UtilValidate.isNotEmpty(product.getSize())) {
				if(!sortedSizes.contains(product.getSize())) {
					sortedSizes.add(product.getSize());
				}

				if(sizes.containsKey(product.getSize())) {
					Map tempCategory = (Map) sizes.get(product.getSize());
					if(UtilValidate.isNotEmpty(product.getCategoryId())) {
						if(tempCategory.containsKey(product.getCategoryId())) {
							Map skus = (Map) tempCategory.get(product.getCategoryId());
							if(!skus.containsKey(product.getId())) {
								skus.put(product.getId(), productMap);
							}
						} else {
							tempCategory.put(product.getCategoryId(), UtilMisc.toMap(product.getId(), productMap));
						}
					}
				} else {
					sizes.put(product.getSize(), UtilMisc.toMap(product.getCategoryId(), UtilMisc.toMap(product.getId(), productMap)));
				}
			}

			if(UtilValidate.isNotEmpty(product.getCategoryId())) {
				if(styles.containsKey(product.getCategoryId())) {
					Map tempSize = (Map) styles.get(product.getCategoryId());
					if(UtilValidate.isNotEmpty(product.getSize())) {
						if(tempSize.containsKey(product.getSize())) {
							Map skus = (Map) tempSize.get(product.getSize());
							if(!skus.containsKey(product.getId())) {
								skus.put(product.getId(), productMap);
							}
						} else {
							tempSize.put(product.getSize(), UtilMisc.toMap(product.getId(), productMap));
						}
					}
				} else {
					styles.put(product.getCategoryId(), UtilMisc.toMap(product.getSize(), UtilMisc.toMap(product.getId(), productMap)));
				}
			}

			if(!styleNames.containsKey(product.getCategoryId())) {
				styleNames.put(product.getCategoryId(), ProductHelper.getProductCategory(delegator, product.getCategoryId()).getString("categoryName"));
			}
		}

		return UtilMisc.toMap("BY_STYLE", styles, "BY_SIZE", sizes, "SORTED_SIZE_LIST", sortedSizes, "STYLE_LIST", styleNames);
	}
	private static Map<String, List<String>> colorOrderMap = new HashMap<>();
	static {
	    colorOrderMap.put("#10|REGULAR", Arrays.asList("43687","75746","32308","45146","75747","WS-2652","28726","WS-2960","10R-28W","75746-TAX","43687-RETSER","75747-RETSER","43687-INV","75747-INV","4260-24WSWLI","4260-24WAWLI","4260-70AWLI","4260-24WS","4260-24WAW","4260-70AW","4260-24WSW","4260-70SW","4260-24RBW","10R-24BW","10R-24UW","SPW10-80UW","SPW10-80PW","PC1903PL","PC1913PL","WS-2592","92908","WS-2956","10PIN-24W"));
	    colorOrderMap.put("#10|SQUARE_FLAP", Arrays.asList("4860-70W","4260-70SWLI","4860-80W","4860-32IJ","4860-WLI","4860-WPC","4860-SW","28725","26611-MI","FLWH4260-04","FLWH4260-03","FLWH4260-02","FLWH4260-01","4860-S02","5869-GL"));
	    colorOrderMap.put("A7|SQUARE_FLAP", Arrays.asList("72940","51867-MI","4880-WPP","20677","FE4580-05","4880-32IJ","4880-WPC","4880-SW","4880-SBW","4880-WLI","5380-S02","4880-WGV","4880-70SW","4880-70AW","4880-70WS","4880-70RBW","4880-70RBWLI","4880-70AWLI","4880-70SWLI","4880-80UW","4880-80BW","5380-08","5380-30","5880-GL","4880-WPCH"));
    }

	/**
	 * Get a sorted list of all the product color combos for a specific size and style for given product type and website
	 * @param delegator
	 * @param dispatcher
	 * @param webSiteId
	 * @param productTypeId
	 * @param productCategoryId
	 * @param size
	 * @return
	 */
	public static Map<String, Object> getSortedProductColors(Delegator delegator, LocalDispatcher dispatcher, String webSiteId, String productTypeId, String productCategoryId, String size) {
		Map<String, Object> data = null;
		TreeMap<String, Object> sortedData = null;
		if(UtilValidate.isNotEmpty(size)) {
			try {
				String md5Str = EnvUtil.MD5(webSiteId + productTypeId + productCategoryId);
				if(!productSizeAndStyle.containsKey(md5Str)) {
					productSizeAndStyle.put(md5Str, getProductAndStyle(delegator, dispatcher, getAllProductsInCategory(delegator, webSiteId, productTypeId, productCategoryId)));
				}
				data = new HashMap<>(productSizeAndStyle.get(md5Str));
				if (data.containsKey("BY_SIZE")) {
					Map tempData = (Map) data.get("BY_SIZE");
					if (tempData.containsKey(size)) {
						tempData = (Map) tempData.get(size);
						if (tempData.containsKey(productCategoryId)) {
							tempData = (Map) tempData.get(productCategoryId);

							sortedData = new TreeMap<>(new MapSequenceComparator(true, tempData));
							sortedData.putAll(tempData);
						}
					}
				}

				data.remove("BY_SIZE");
				data.remove("BY_STYLE");
				data.put("SORTED_COLORS", sortByColor(new LinkedHashMap<>(sortedData), size, productCategoryId));
			} catch (Exception e) {
				Debug.logError(e, "Error trying to fetch sorted product colors.", module);
			}
		}

		return data;
	}

	public static boolean saveDesignPreview(String imageData, String designId) {

		if(UtilValidate.isNotEmpty(imageData) && UtilValidate.isNotEmpty(designId)) {
			String imageDataBytes = imageData.substring(imageData.indexOf(",") + 1);

			try {
				InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(imageDataBytes.getBytes()));
				File imageFile = new File(EnvConstantsUtil.OFBIZ_HOME + "/" + EnvConstantsUtil.UPLOAD_DIR + "/" + EnvConstantsUtil.TEXEL_DESIGN_IMAGE_DIR + "/" + designId + ".png");
				imageFile.mkdirs();
				ImageIO.write(ImageIO.read(stream), "png", imageFile);
				return true;
			} catch (IOException e) {
				Debug.logError(e, "Error trying to save design image for designId:" + designId, module);
			}
		}
		return false;
	}
/*
	public static boolean saveDesignThumbnail(String imageData, String fileName) {
		if(UtilValidate.isNotEmpty(imageData) && UtilValidate.isNotEmpty(fileName)) {
			String imageDataBytes = imageData.substring(imageData.indexOf(",") + 1);

			try {
				InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(imageDataBytes.getBytes()));
				File imageFile = new File(EnvConstantsUtil.OFBIZ_HOME + "/hot-deploy/html/webapp/html/img/designs/thumbnails/" + fileName + ".png");
				imageFile.mkdirs();
				ImageIO.write(ImageIO.read(stream), "png", imageFile);
				return true;
			} catch (IOException e) {
				Debug.logError(e, "Error trying to save design image thumbnail to file:" + fileName, module);
			}
		}
		return false;
	}
*/
	public static boolean savePDFFile(String fileData, String fileName) throws Exception {

		//fileName
		//orderItemSeqId
		//orderId
		//contentPurposeEnumId
		//ignoreStatusChange
		if(UtilValidate.isNotEmpty(fileData) && UtilValidate.isNotEmpty(fileName)) {
			String fileUploadPath = FileHelper.getUploadPath(null);
			String extension = null;
			if(fileName.contains(".")) {
				extension = fileName.substring(fileName.lastIndexOf(".") + 1);
			}
			if(UtilValidate.isEmpty(extension)) {
				extension = null;
			}

			String fileDataBytes = fileData.substring(fileData.indexOf(",") + 1);
			OutputStream outStream = null;
			try {
//				InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(fileDataBytes.getBytes()));
//				File pdfFile = new File(EnvConstantsUtil.OFBIZ_HOME + "/" + EnvConstantsUtil.UPLOAD_DIR + "/" + EnvConstantsUtil.TEXEL_DESIGN_IMAGE_DIR + "/" + designId + ".pdf");
				File file = FileHelper.makeFile(null, fileUploadPath, extension);
				outStream = new FileOutputStream(file);
				outStream.write(Base64.getDecoder().decode(fileDataBytes.getBytes()));
				outStream.close();
				return true;
			} catch (IOException e) {
				Debug.logError(e, "Error trying to create file :" + fileName, module);
			} finally {
				if(outStream != null) {
					outStream.close();
				}
			}
		}
		return false;
	}

	private static Map<String, Object> sortByColor(Map<String, Object> data, String size, String productCategoryId) {
	    if(!colorOrderMap.containsKey(size + "|" + productCategoryId)) {
	        return data;
        }
	    Map<String, Object> sortedData = new LinkedHashMap<>();
	    List<String> orderedSKUs = colorOrderMap.get(size + "|" + productCategoryId);
	    for(String sku : orderedSKUs) {
	        if(data.containsKey(sku)) {
                sortedData.put(sku, data.get(sku));
            }
        }

        for(Map.Entry<String,Object> entry : data.entrySet()) {
            if(!sortedData.containsKey(entry.getKey())) {
                sortedData.put(entry.getKey(), entry.getValue());
            }
        }

	    return sortedData;
    }

	/**
	 * Select closest quantity adjustment
	 * @param quantity
	 * @param adjustmentMap
	 * @return
	 */
	public static BigDecimal getAdjustmentFromMap(int quantity, Map<Integer, BigDecimal> adjustmentMap) {
		BigDecimal adjustment = BigDecimal.ZERO;

		int closestQtyFound = 0;
		if(UtilValidate.isNotEmpty(adjustmentMap)) {
			Iterator adjustmentIter = adjustmentMap.keySet().iterator();
			while(adjustmentIter.hasNext()) {
				Integer quantityKey = (Integer) adjustmentIter.next();
				if(quantityKey.intValue() <= quantity && quantityKey.intValue() >= closestQtyFound) {
					closestQtyFound = quantityKey.intValue();
					adjustment = (BigDecimal) adjustmentMap.get(quantityKey);
				}
			}
		}

		return adjustment;
	}

	/**
	 * Get other colors/siblings of this product
	 * @param delegator
	 * @param product
	 * @return
	 * @throws GenericEntityException
	 */
	public static Map<String, Object> getProductColors(Delegator delegator, GenericValue product) throws GenericEntityException, GenericServiceException {
		return getProductColors(delegator, null, product);
	}

	public static Map<String, Object> getProductColors (Delegator delegator, LocalDispatcher dispatcher, GenericValue product) throws GenericEntityException, GenericServiceException {
		return getProductColors(delegator, dispatcher, product, null);
	}

	public static Map<String, Object> getProductColors (Delegator delegator, LocalDispatcher dispatcher, GenericValue product, String webSiteId) throws GenericEntityException, GenericServiceException {
		Map<String, Object> data = new HashMap<String, Object>();

		List<Map<String, Object>> productColors = new ArrayList<Map<String, Object>>();
		Map<String, String> colorGroup = new HashMap<String, String>();
		Map<String, String> collection = new HashMap<String, String>();
		Map<String, String> ecoChoice = new HashMap<String, String>();
		Map<String, Object> filters = new HashMap<String, Object>();

		List<GenericValue> colors = null;
		if(UtilValidate.isNotEmpty(product) && UtilValidate.isNotEmpty(product.getString("parentProductId")) && UtilValidate.isNotEmpty(product.getString("isVariant")) && product.getString("isVariant").equalsIgnoreCase("Y")) {
			colors = EntityQuery.use(delegator).from("ColorWarehouse").where("virtualProductId", product.getString("parentProductId")).orderBy("sequenceNum ASC").cache().queryList();
		} else {
			colors = EntityQuery.use(delegator).from("ColorWarehouse").where("virtualProductId", product.getString("productId")).orderBy("sequenceNum ASC").cache().queryList();
		}

		Map<String, Object> tempData = null;
		List<String> colorArray = null;
		List<String> collectionArray = null;
		List<String> ecoArray = null;

		for(GenericValue color: colors) {
			GenericValue webSiteData = getWebSiteData(delegator, color.getString("variantProductId"));

			if (UtilValidate.isEmpty(webSiteId) || (UtilValidate.isNotEmpty(webSiteData) && UtilValidate.isNotEmpty(webSiteData.getString(webSiteId)) && webSiteData.getString(webSiteId).equals("Y"))) {
				tempData = new HashMap<String, Object>();
				colorArray = new ArrayList<String>();
				collectionArray = new ArrayList<String>();
				ecoArray = new ArrayList<String>();
				if (dispatcher != null) {
					ShoppingCart cart = null;
					Product variantProduct = new Product(delegator, dispatcher, color.getString("variantProductId"), cart);
					tempData.put("imprintMethods", variantProduct.getImprintMethods());
					tempData.put("sizeCode", variantProduct.getSize());
				}
				tempData.put("productId", color.getString("variantProductId"));
				tempData.put("primaryProductCategoryId", color.getString("primaryProductCategoryId"));
				tempData.put("group", color.getString("colorGroupDescription"));
				tempData.put("desc", color.getString("colorDescription"));
				tempData.put("name", color.getString("productName"));
				tempData.put("rating", getRating(delegator, color.getString("variantProductId")));
				tempData.put("swatch", EnvUtil.toImageUrl(color.getString("colorDescription")) + ".jpg");
				tempData.put("recycled", color.getString("percentRecycled"));
				tempData.put("brand", color.getString("brandDescription"));
				tempData.put("weight", color.getString("paperWeightDescription"));
				tempData.put("texture", color.getString("paperTextureDescription"));
				tempData.put("coating", color.getString("coatingDescription"));
				tempData.put("type", color.getString("productTypeId"));
				tempData.put("hex", (UtilValidate.isNotEmpty(color.getString("colorHexCode"))) ? "#" + color.getString("colorHexCode") : "#FFFFFF");
				tempData.put("new", (UtilValidate.isNotEmpty(color.getString("isNew")) && color.getString("isNew").equals("Y")) ? true : false);
				tempData.put("sale", (UtilValidate.isNotEmpty(color.getString("onSale")) && color.getString("onSale").equals("Y")) ? true : false);
				tempData.put("clearance", (UtilValidate.isNotEmpty(color.getString("onClearance")) && color.getString("onClearance").equals("Y")) ? true : false);
				tempData.put("sealingMethod", color.getString("sealingMethod"));
				tempData.put("plainPrice", color.getString("plainPriceDescription"));
				tempData.put("printPrice", color.getString("printPriceDescription"));
				tempData.put("minColors", color.getLong("minColors"));
				tempData.put("maxColors", color.getLong("maxColors"));
				tempData.put("hasSample", !("N").equalsIgnoreCase(color.getString("hasSample")));
				tempData.put("hasWhiteInk", !("N").equalsIgnoreCase(color.getString("hasWhiteInk")));
				tempData.put("hasCustomQty", !("N").equalsIgnoreCase(color.getString("hasCustomQty")));
				tempData.put("isPrintable", (UtilValidate.isNotEmpty(color.getString("isPrintable")) && color.getString("isPrintable").equalsIgnoreCase("Y")) ? true : false);
				tempData.put("percentSavings", color.getLong("percentSavings"));

				if(UtilValidate.isNotEmpty(color.getString("isSfiCertified")) && color.getString("isSfiCertified").equals("Y")) {
					if(!ecoChoice.containsKey("SFI")) {
						ecoChoice.put("SFI", null);
					}
					tempData.put("isSfiCertified", true);
					ecoArray.add("SFI");
				} else {
					tempData.put("isSfiCertified", false);
				}

				if(UtilValidate.isNotEmpty(color.getString("percentRecycled")) && !ecoChoice.containsKey("Recycled")) {
					ecoChoice.put("Recycled", null);
					ecoArray.add("Recycled");
				}

				if(UtilValidate.isNotEmpty(color.getString("colorGroupDescription"))) {
					colorArray.add(color.getString("colorGroupDescription"));
					if(!colorGroup.containsKey(color.getString("colorGroupDescription"))) {
						colorGroup.put(color.getString("colorGroupDescription"), "color_" + color.getString("colorGroupDescription").toLowerCase().replace(" ","_") + ".gif");
					}
				}
				if(UtilValidate.isNotEmpty(color.getString("collectionDescription"))) {
					collectionArray.add(color.getString("collectionDescription"));
					if(!collection.containsKey(color.getString("collectionDescription"))) {
						collection.put(color.getString("collectionDescription"), "collection_" + color.getString("collectionDescription").toLowerCase().replace(" ","_") + ".gif");
					}
				}

				tempData.put("colorGroup", colorArray);
				tempData.put("colorGroupClasses", getColorGroupClasses(colorArray));
				tempData.put("collectionCategory", collectionArray);
				tempData.put("collectionClasses", getColorGroupClasses(collectionArray));
				tempData.put("ecoGroup", ecoArray);
				productColors.add(tempData);
			}
		}

		Map<String, Object> filterMap = new HashMap<String, Object>();
		filterMap.put("categoryType", "colorGroup");
		filterMap.put("type", "filter");
		filterMap.put("choices", colorGroup);
		filters.put("By Color", filterMap);
		filterMap= new HashMap<String, Object>();
		filterMap.put("categoryType", "collectionCategory");
		filterMap.put("type", "filter");
		filterMap.put("choices", collection);
		filters.put("By Collection", filterMap);
		filterMap= new HashMap<String, Object>();
		filterMap.put("categoryType", "ecoGroup");
		filterMap.put("type", "filter");
		filterMap.put("choices", ecoChoice);
		filters.put("Eco Choices", filterMap);

		data.put("colors", productColors);
		data.put("filters", filters);
		return data;
	}

	protected static String getColorGroupClasses(List<String> colorArray) {
		StringBuilder colorGroups = new StringBuilder();
		for (String colorGroup : colorArray) {
			colorGroups.append(" jqs-").append(colorGroup);
		}

		return colorGroups.toString();
	}

	/*
	 * This method is to get the opposite matching product for a given product at the product page
	 * Example, A paper will get the matching size envelope and vice versa
	 */
	public static List<GenericValue> getMatchingProducts(Delegator delegator, GenericValue product) throws GenericEntityException {
		List<GenericValue> matchingProductList = new ArrayList();

		if(product == null) {
			return null;
		}

		//get the color and size of the given product
		List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
		conditionList.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, product.getString("productId")));
		conditionList.add(EntityCondition.makeCondition("productFeatureTypeId", EntityOperator.IN, UtilMisc.toList("COLOR","COLOR_GROUP","SIZE","SIZE_CODE")));
		List<GenericValue> productFeatureAndApplList = EntityQuery.use(delegator).from("ProductFeatureAndAppl").where(EntityCondition.makeCondition(conditionList, EntityOperator.AND)).cache().queryList();
		if(UtilValidate.isEmpty(productFeatureAndApplList)) {
			return null;
		}

		String sizeFeatureId = null;
		String sizeCodeFeatureId = null;
		String colorGroupFeatureId = null;
		String colorFeatureId = null;

		Iterator productFeatureAndApplListIter = productFeatureAndApplList.iterator();
		while(productFeatureAndApplListIter.hasNext()) {
			GenericValue productFeature = (GenericValue) productFeatureAndApplListIter.next();
			if(productFeature.getString("productFeatureTypeId").equals("SIZE")) {
				sizeFeatureId = productFeature.getString("productFeatureId");
			} else if(productFeature.getString("productFeatureTypeId").equals("SIZE_CODE")) {
				sizeCodeFeatureId = productFeature.getString("productFeatureId");
			} else if(productFeature.getString("productFeatureTypeId").equals("COLOR_GROUP")) {
				colorGroupFeatureId = productFeature.getString("productFeatureId");
			} else if(productFeature.getString("productFeatureTypeId").equals("COLOR")) {
				colorFeatureId = productFeature.getString("productFeatureId");
			}
		}

		//get the matching active products that are in warehouse of opposite product type and same color and colorgroup
		conditionList.clear();
		conditionList.add(EntityCondition.makeCondition("colorGroupFeatureId", EntityOperator.IN, UtilMisc.toList(colorFeatureId, colorGroupFeatureId)));
		conditionList.add(EntityCondition.makeCondition("productTypeId", EntityOperator.NOT_EQUAL, product.getString("productTypeId")));
		matchingProductList = EntityQuery.use(delegator).from("ColorWarehouse").where(EntityCondition.makeCondition(conditionList, EntityOperator.AND)).cache().queryList();
		if(UtilValidate.isEmpty(matchingProductList)) {
			return null;
		}

		//remove anything that doesnt match the size_code
		List<GenericValue> paperProductList = new ArrayList<GenericValue>();

		Iterator colorWarehouseIter = matchingProductList.iterator();
		while(colorWarehouseIter.hasNext()) {
			GenericValue warehouseProduct = (GenericValue) colorWarehouseIter.next();

			//check if its in ITE, if its in there, lets remove it
			List<GenericValue> prodCategoryMemberITEList = EntityQuery.use(delegator).from("ProductCategoryMember").where("productId", warehouseProduct.getString("variantProductId"), "productCategoryId", "INSIDE_THE_ENVELOPE").queryList();
			if(UtilValidate.isNotEmpty(prodCategoryMemberITEList)) {
				colorWarehouseIter.remove();
				continue;
			}

			//remove the custom colorflap from the list
			if(UtilValidate.isNotEmpty(warehouseProduct.getString("colorDescription")) && warehouseProduct.getString("colorDescription").equals("Custom ColorFlap")) {
				colorWarehouseIter.remove();
				continue;
			}

			//check if it has the correct product size_code, if doesnt have it, remove it
			conditionList.clear();
			conditionList.add(EntityCondition.makeCondition("productFeatureId", EntityOperator.IN, UtilMisc.toList(sizeCodeFeatureId, "9030", "8607")));
			conditionList.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, warehouseProduct.getString("variantProductId")));
			GenericValue warehouseSizeCode = EntityQuery.use(delegator).from("ProductFeatureAndAppl").where(EntityCondition.makeCondition(conditionList, EntityOperator.AND)).cache().queryOne();

			if(UtilValidate.isEmpty(warehouseSizeCode)) {
				colorWarehouseIter.remove();
				continue;
			} else {
				//if its paper, we are going to remove them and then add them to the end
				if(UtilValidate.isNotEmpty(warehouseSizeCode.getString("productFeatureId")) && (warehouseSizeCode.getString("productFeatureId").equals("9030") || warehouseSizeCode.getString("productFeatureId").equals("8607"))) {
					paperProductList.add(warehouseProduct);
					colorWarehouseIter.remove();
					continue;
				}
			}
		}

		//add the paper back to the end of the list
		if(UtilValidate.isNotEmpty(paperProductList)) {
			matchingProductList.addAll(paperProductList);
		}

		return matchingProductList;
	}

	/*
	 * Get clean matching product data
	 */
	public static List<Map<String, Object>> getCleanMatchingProductData(Delegator delegator, List<GenericValue> matchingProducts) {
		List<Map<String, Object>> prodData = new ArrayList<Map<String, Object>>();

		Map<String, Object> tempData = null;
		for(GenericValue matchingProduct : matchingProducts) {
			tempData = new HashMap<String, Object>();
			GenericValue matchingProductGV = null;
			try {
				matchingProductGV = EntityQuery.use(delegator).from("Product").where("productId", matchingProduct.getString("variantProductId")).cache().queryOne();
			} catch (GenericEntityException e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
			}
			if(UtilValidate.isNotEmpty(matchingProductGV)) {
				String imageName = matchingProductGV.getString("smallImageUrl");
				if(UtilValidate.isNotEmpty(imageName)) {
					imageName = imageName.replace("/images/products/small/","");
				}
				tempData.put("productId", matchingProductGV.getString("productId"));
				tempData.put("productName", matchingProductGV.getString("productName"));
				tempData.put("color", matchingProduct.getString("colorDescription"));
				tempData.put("weight", matchingProduct.getString("paperWeightDescription"));
				tempData.put("image", imageName);
				tempData.put("plainPrice", matchingProductGV.getString("plainPriceDescription"));
				tempData.put("printPrice", matchingProductGV.getString("printPriceDescription"));
				tempData.put("sale", (UtilValidate.isNotEmpty(matchingProductGV.getString("onSale")) && matchingProductGV.getString("onSale").equals("Y")) ? true : false);
				tempData.put("isPrintable", (UtilValidate.isNotEmpty(matchingProductGV.getString("isPrintable")) && matchingProductGV.getString("isPrintable").equalsIgnoreCase("Y")) ? true : false);
			}
			prodData.add(tempData);
		}

		return prodData;
	}

	/**
	 * Update the stock level of a product
	 */
	public static void updateStockLevel(Delegator delegator, String productId, Map<String, Object> data) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(productId) && (EntityQuery.use(delegator).from("Product").where("productId", productId).cache().queryOne()) != null) {
			GenericValue newValue = delegator.makeValue("ProductStock", UtilMisc.toMap("productId", productId.trim(), "quantityOnHand", (Long) data.get("quantity"), "location", inventoryLocation.get(data.get("location")), "dropshipOnly", data.get("dropshipOnly"), "reorderMultiple", (Long) data.get("reorderMultiple")));
			delegator.createOrStore(newValue);
		}
	}

	/**
	 * Remove any stock level that didnt get updated within last N minutes, that means we didnt get a valid value from netsuite
	 */
	public static void removeStockLevel(Delegator delegator, int minutes) throws GenericEntityException {
		Timestamp staleTimestamp = EnvUtil.getNMinBeforeOrAfterNow(minutes);
		List<GenericValue> staleProductList = null;

		List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
		conditionList.add(EntityCondition.makeCondition("lastUpdatedStamp", EntityOperator.LESS_THAN_EQUAL_TO, staleTimestamp));
		conditionList.add(EntityCondition.makeCondition(UtilMisc.toList(
							EntityCondition.makeCondition("alwaysInstock", EntityOperator.EQUALS, null),
							EntityCondition.makeCondition("alwaysInstock", EntityOperator.EQUALS, "N")
							), EntityOperator.OR)
						);
		EntityCondition condition = EntityCondition.makeCondition(conditionList, EntityOperator.AND);
		staleProductList = EntityQuery.use(delegator).from("ProductStock").where(condition).queryList();

		if(UtilValidate.isNotEmpty(staleProductList)) {
			for(GenericValue productStock : staleProductList) {
				productStock.remove();
			}
		}
	}

	/*
	 * Check if enough stock is available for a product
	 * if last param is true, then reduce inventory by the quantity
	 */
	public static boolean hasInventory(Delegator delegator, String productId, Long quantity, boolean updateStock) throws GenericEntityException {
		boolean hasInventory = false;
		GenericValue stock = EntityQuery.use(delegator).from("ProductStock").where("productId", productId).queryOne();
		if(stock != null && UtilValidate.isNotEmpty(stock.getLong("quantityOnHand"))) {
			if(stock.getLong("quantityOnHand") > quantity || isCustomSku(productId) || "Y".equalsIgnoreCase(stock.getString("alwaysInstock"))) {
				hasInventory = true;
			}
			if(updateStock) {
				stock.set("quantityOnHand", stock.getLong("quantityOnHand") - quantity);
				stock.store();
			}
		}

		return hasInventory;
	}

	public static boolean allowBackorderEmail(Delegator delegator, String productId, Long quantityOrdered) throws GenericEntityException {
		GenericValue productStock = EntityQuery.use(delegator).from("ProductStock").where("productId", productId).queryOne();

		if (
			productStock != null &&
			(
				UtilValidate.isNotEmpty(productStock.getString("dropshipOnly")) && productStock.getString("dropshipOnly").equals("Y") ||
				UtilValidate.isNotEmpty(productStock.getLong("reorderMultiple")) && productStock.getLong("reorderMultiple").equals(quantityOrdered) ||
				UtilValidate.isNotEmpty(productStock.getString("ignoreBackorderEmail")) && productStock.getString("ignoreBackorderEmail").equals("Y")
			) ||
			hasCut(delegator, null, productId) ||
			hasFold(delegator, null, productId)
		) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Get a list of product feature descriptions for a given type
	 */
	public static List<String> getFeatureDescListByType(Delegator delegator, String productFeatureTypeId) throws GenericEntityException {
		List<GenericValue> featureList = EntityQuery.use(delegator).from("ProductFeature").where("productFeatureTypeId", productFeatureTypeId).cache().queryList();
		return EntityUtil.getFieldListFromEntityList(featureList, "description", true);
	}

	/*
	 * Load product feature type ids and their descriptiosn
	 */
	public static String getFeatureDescByType(Delegator delegator, String productFeatureTypeId) throws GenericEntityException {
		if (UtilValidate.isEmpty(FEATURE_TYPE_ID_AND_DESC)) {
			List<GenericValue> productFeatureTypes = EntityQuery.use(delegator).from("ProductFeatureType").cache().queryList();
			for (GenericValue featureType : productFeatureTypes) {
				FEATURE_TYPE_ID_AND_DESC.put(featureType.getString("productFeatureTypeId"), featureType.getString("description"));
			}
		}

		return FEATURE_TYPE_ID_AND_DESC.get(productFeatureTypeId);
	}

	protected static Map<String, String> getDesignTemplateFiles(Delegator delegator) {
		Map<String, String> designTemplateFiles = new LinkedHashMap<String, String>();
		String templateFolderPath = EnvConstantsUtil.OFBIZ_HOME + "/hot-deploy/html/webapp/html/files/templates/";
		templateFolderPath = FileHelper.cleanFolderPath(templateFolderPath);
		Iterator<File> templatesIterator = FileUtils.iterateFiles(new File(templateFolderPath), new String[] {"pdf"}, false);
		while(templatesIterator.hasNext()) {
			File template = templatesIterator.next();
			designTemplateFiles.put(template.getName(), "/html/files/templates/" + template.getName());
		}
		return designTemplateFiles;
	}

	public static Map<String, Set<Map<String, Object>>> getDesignTemplates(Delegator delegator) throws GenericEntityException {
		Map<String, String> templateFiles = getDesignTemplateFiles(delegator);
		Map<String, Set<Map<String, Object>>> designTemplates = new HashMap<>();
		DynamicViewEntity dve = new DynamicViewEntity();
		dve.addMemberEntity("CW", "ColorWarehouse");
		dve.addAlias("CW", "virtualProductId");
		dve.addAlias("CW", "variantProductId");
		dve.addAlias("CW", "primaryProductCategoryId");
		dve.addAlias("CW", "sizeDescription");
		dve.addAlias("CW", "variantCount", "variantProductId", null, null, null, "count");
		dve.setGroupBy(UtilMisc.<String>toList("virtualProductId"));

		List<GenericValue> templates = EntityQuery.use(delegator).select("virtualProductId", "variantProductId", "primaryProductCategoryId", "sizeDescription", "variantCount").from(dve).distinct().queryList();
		for (GenericValue _template : templates) {
			Map<String, Object> template = new HashMap<>();
			template.putAll(_template);
			if(templateFiles.containsKey(template.get("virtualProductId") + "_FRONT.pdf") || templateFiles.containsKey(template.get("virtualProductId") + "_BACK.pdf")) {
				template.put("FRONT_URL", templateFiles.get(template.get("virtualProductId") + "_FRONT.pdf"));
				template.put("BACK_URL", templateFiles.get(template.get("virtualProductId") + "_BACK.pdf"));
				template.put("SIZE_CODE", getSizeCode(delegator, _template.getString("virtualProductId")));
				template.put("PRODUCT_URL", "product/~category_id=" + _template.get("primaryProductCategoryId") + "/~product_id=" + _template.get("variantProductId"));
				String categoryKey = "", sizeKey = "";
				if(!designTemplates.containsKey(categoryKey = "CAT:" + template.get("primaryProductCategoryId"))) {
					designTemplates.put(categoryKey, new HashSet<Map<String, Object>>());
				}
			/*if(!designTemplates.containsKey(sizeKey = "SI:" + template.get("sizeDescription"))) {
				designTemplates.put(sizeKey, new HashSet<Map<String, Object>>());
			}*/

				designTemplates.get(categoryKey).add(template);
//				designTemplates.get(sizeKey).add(template);
			} else {
				continue;
			}
		}
		return designTemplates;
	}

	public static String getSizeCode(Delegator delegator, String productId) {
		String sizeCode = "";
		try {
			List<GenericValue> productFeatures = EntityQuery.use(delegator).from("ProductFeatureAndAppl").where("productId", productId, "productFeatureTypeId", "SIZE_CODE").cache().queryList();
			if(UtilValidate.isNotEmpty(productFeatures) && productFeatures.size() == 1) {
				if(UtilValidate.isNotEmpty(productFeatures.get(0).getString("description"))) {
					sizeCode = productFeatures.get(0).getString("description");
				}
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
		}
		return sizeCode;
	}

	public static String getSize(Delegator delegator, String productId) {
		String sizeCode = "";
		try {
			List<GenericValue> productFeatures = EntityQuery.use(delegator).from("ProductFeatureAndAppl").where("productId", productId, "productFeatureTypeId", "SIZE").cache().queryList();
			if(UtilValidate.isNotEmpty(productFeatures) && productFeatures.size() == 1) {
				if(UtilValidate.isNotEmpty(productFeatures.get(0).getString("description"))) {
					sizeCode = productFeatures.get(0).getString("description");
				}
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
		}
		return sizeCode;
	}

	/**
	 * Return a product tagline
	 * @param delegator
	 * @param productId
	 * @return
	 * @throws GenericEntityException
	 */
	public static String getTagline(Delegator delegator, String productId) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(productId)) {
			GenericValue product = getProduct(delegator, productId);
			if(product != null) {
				return product.getString("tagLine");
			}
		}

		return null;
	}

	/**
	 * Get product lead time
	 */
	public static Map<String, Integer> getLeadTime(GenericValue product) throws GenericEntityException {
		Map<String, Integer> leadTime = new HashMap<String, Integer>();

		leadTime.put("leadTimePlain", (UtilValidate.isNotEmpty(product.get("leadTimePlain")) ? Integer.valueOf(product.get("leadTimePlain").toString()) : defaultLeadTimePlain));
		leadTime.put("leadTimeStandardPrinted", (UtilValidate.isNotEmpty(product.get("leadTimeStandardPrinted")) ? Integer.valueOf(product.get("leadTimeStandardPrinted").toString()) : defaultLeadTimeStandardPrinted));
		leadTime.put("leadTimeRushPrinted", (UtilValidate.isNotEmpty(product.get("leadTimeRushPrinted")) ? Integer.valueOf(product.get("leadTimeRushPrinted").toString()) : defaultLeadTimeRushPrinted));
		leadTime.put("leadTimeScene7", defaultLeadTimeScene7);

		return leadTime;
	}
	public static Map<String, Integer> getLeadTime(Delegator delegator, String productId) throws GenericEntityException {
		GenericValue product = null;

		try {
			product = EntityQuery.use(delegator).from("Product").where("productId", productId).cache().queryOne();
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to get product lead time.", module);
		}

		return getLeadTime(product);
	}

	/**
	 * Get all reviews for a product
	 */
	public static Map<String, Object> getProductReviews(Delegator delegator, GenericValue product, String salesChannelEnumId) throws GenericEntityException {
		List<GenericValue> reviewList = null;
		if(UtilValidate.isEmpty(salesChannelEnumId)) {
			reviewList = EntityQuery.use(delegator).from("ProductReview").where("productId", product.getString("productId"), "statusId", "PRR_APPROVED").orderBy("createdStamp DESC").cache().queryList();
		} else {
			reviewList = EntityQuery.use(delegator).from("ProductReview").where("productId", product.getString("productId"), "statusId", "PRR_APPROVED", "salesChannelEnumId", salesChannelEnumId).orderBy("createdStamp DESC").cache().queryList();
		}

		Map<String, Integer> reviewerCount = new HashMap<>();
		List<Map<String, Object>> reviewListMap = new ArrayList<Map<String, Object>>();

		Map<String, Object> reviewMap = null;

		Integer other = 0;
		for(GenericValue review : reviewList) {
			reviewMap = new HashMap<String, Object>();
			reviewMap.put("nickName", review.getString("nickName"));
			reviewMap.put("productRating", review.getBigDecimal("productRating"));
			reviewMap.put("postedDateTime", review.getTimestamp("postedDateTime"));
			reviewMap.put("productReview", review.getString("productReview"));
			reviewMap.put("productQuality", review.getString("productQuality"));
			reviewMap.put("productQualityReason", review.getString("productQualityReason"));
			reviewMap.put("productUse", review.getString("productUse"));
			reviewMap.put("describeYourself", review.getString("describeYourself"));
			reviewMap.put("recommend", review.getString("recommend"));
			reviewMap.put("reviewResponse", review.getString("reviewResponse"));
			reviewMap.put("contentId", review.getString("contentId"));
			reviewMap.put("showContent", review.getString("showContent"));
			reviewMap.put("orderId", review.getString("orderId"));
			reviewListMap.add(reviewMap);

			if(reviewType.contains(review.getString("describeYourself"))) {
				reviewerCount.put(review.getString("describeYourself"), (reviewerCount.containsKey(review.getString("describeYourself"))) ? ((Integer) reviewerCount.get(review.getString("describeYourself"))) + 1 : 1);
			} else {
				other++;
			}
		}

		reviewerCount.put("Other", other);
		reviewerCount.put("All", reviewList.size());

		Map<String, Object> reviews = new HashMap<>();
		reviews.put("totals", reviewerCount);
		reviews.put("reviews", reviewListMap);

		return reviews;
	}

	public static String getRating(Delegator delegator, String productId) throws GenericEntityException {
		return getRating(delegator, productId, null);
	}

	public static String getRating(Delegator delegator, String productId, String salesChannelEnumId) throws GenericEntityException {
		GenericValue rating = null;

		if (UtilValidate.isEmpty(salesChannelEnumId)) {
			rating = EntityQuery.use(delegator).from("ProductAndReviewSummary").where("productId", productId, "statusId", "PRR_APPROVED").cache().queryFirst();
		} else {
			rating = EntityQuery.use(delegator).from("ProductAndReviewSummary").where("productId", productId, "statusId", "PRR_APPROVED", "salesChannelEnumId", salesChannelEnumId).cache().queryFirst();
		}

		if(rating != null) {
			return Float.toString(Math.round(rating.getBigDecimal("productRating").setScale(1, RoundingMode.HALF_UP).floatValue()/.5)/2F).replaceAll("\\.", "_");
		}

		return "0_0";
	}

	public static String formatName(String name) {
		Matcher matcher = cardSizePattern.matcher(name);
		return matcher.replaceAll("");
	}

	public static String addReview(Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> context) {
		try {
			GenericValue review = delegator.makeValue("ProductReview", UtilMisc.toMap("productReviewId", delegator.getNextSeqId("ProductReview")));
			review = EnvUtil.insertGenericValueData(delegator, review, context);
			delegator.create(review);
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to add a review.", module);
			return "error";
		}

		return "success";
	}

	public static boolean hasAddressing(GenericValue product) {
		if(product == null) {
			return false;
		}

		if("ENVELOPE".equalsIgnoreCase(product.getString("productTypeId"))) {
			if(UtilValidate.isNotEmpty(product.getBigDecimal("productWidth")) && UtilValidate.isNotEmpty(product.getBigDecimal("productHeight"))) {
				if(product.getBigDecimal("productWidth").compareTo(new BigDecimal("3.375")) < 0 || product.getBigDecimal("productHeight").compareTo(new BigDecimal("3.375")) < 0) {
					return false;
				} else {
					return true;
				}
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	public static boolean hasVariableData(GenericValue product) {
		if(product == null) {
			return false;
		}

		return !("N").equalsIgnoreCase(product.getString("hasVariableData"));
	}

	public static boolean hasCustomQty(GenericValue product) {
		if(product == null) {
			return false;
		}

		return !("N").equalsIgnoreCase(product.getString("hasCustomQty"));
	}

	public static boolean hasRush(GenericValue product) {
		if(product == null) {
			return false;
		}

		return "Y".equalsIgnoreCase(product.getString("hasRushProduction"));
	}

	public static boolean isPrintable(Delegator delegator, GenericValue product) throws GenericEntityException {
		return isPrintable(delegator, product, true);
	}
	public static boolean isPrintable(Delegator delegator, GenericValue product, boolean checkTemplate) throws GenericEntityException {
		if(product == null) {
			return false;
		}

		if("Y".equalsIgnoreCase(product.getString("isPrintable"))) {
			/*if(checkTemplate) {
				//even if it is printable, it must have an S7 template
				List<GenericValue> scene7ProdAssoc = EntityQuery.use(delegator).from("ProductDesignTemplate").where("productId", product.getString("productId")).cache().queryList();
				if (UtilValidate.isEmpty(scene7ProdAssoc)) {
					scene7ProdAssoc = EntityQuery.use(delegator).from("ProductDesignTemplate").where("productId", product.getString("parentProductId")).cache().queryList();
				}

				if (UtilValidate.isNotEmpty(scene7ProdAssoc)) {
					return true;
				}
			} else {*/
				return true;
			//}
		}

		return false;
	}



	public static int getNextColor(int startColor, int lastTriedColor) {
		if(startColor == 1 && lastTriedColor == 1) {
			return 2;
		} else if(startColor == 1 && lastTriedColor == 2) {
			return 4;
		} else if(startColor == 2 && lastTriedColor == 2) {
			return 4;
		} else if(startColor == 2 && lastTriedColor == 4) {
			return 1;
		} else if(startColor == 4 && lastTriedColor == 4) {
			return 2;
		} else if(startColor == 4 && lastTriedColor == 2) {
			return 1;
		} else if(startColor == 0 && lastTriedColor == 0) {
			return 4;
		} else {
			return 1;
		}
	}

	/*
		Get Product List by Size
	*/
	public static ArrayList<Map> getProductSizeList(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
		EntityListIterator eli = null;
		ArrayList<Map> productResults = new ArrayList<Map>();

		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try {
				EntityFindOptions efo = new EntityFindOptions();
				List<EntityCondition> conditionList = new ArrayList<EntityCondition>();

				conditionList.add(EntityCondition.makeCondition("productWidth", EntityOperator.EQUALS, new BigDecimal((String) context.get("width"))));
				conditionList.add(EntityCondition.makeCondition("productHeight", EntityOperator.EQUALS, new BigDecimal((String) context.get("height"))));
				conditionList.add(EntityCondition.makeCondition("productFeatureTypeId", EntityOperator.EQUALS, "COLOR"));
				conditionList.add(EntityCondition.makeCondition("productFeatureApplTypeId", EntityOperator.EQUALS, "STANDARD_FEATURE"));
				eli = EntityQuery.use(delegator).from("ProductListBySize").where((UtilValidate.isNotEmpty(conditionList)) ? EntityCondition.makeCondition(conditionList, EntityOperator.AND) : null).orderBy("productWidth ASC").queryIterator();

				GenericValue productData = null;
				while((productData = eli.next()) != null) {
					Map<String, Object> productInfo = new HashMap<String, Object>();
					productInfo.put("variantProductId", productData.get("variantProductId"));
					productInfo.put("virtualProductId", productData.get("virtualProductId"));
					productInfo.put("primaryProductCategoryId", productData.get("primaryProductCategoryId"));
					productInfo.put("productName", productData.get("productName"));
					productInfo.put("productWidth", productData.get("productWidth"));
					productInfo.put("productHeight", productData.get("productHeight"));
					productInfo.put("description", productData.get("description"));
					productResults.add(productInfo);
				}
			} catch (GenericEntityException e1) {
				TransactionUtil.rollback(beganTransaction, "Error looking up shipping and billing addresses", e1);
				EnvUtil.reportError(e1);
			} finally {
				if(eli != null) {
					try {
						eli.close();
						TransactionUtil.commit(beganTransaction);
					} catch (GenericEntityException e2) {
						EnvUtil.reportError(e2);
					}
				}
			}
		} catch (GenericTransactionException e3) {
			EnvUtil.reportError(e3);
		}
		return productResults;
	}

	/*
	 * Return relative url for a product based on proxy settings
	 */
	public static String getProductUrl(Delegator delegator, GenericValue product, String productId, String webSiteId, boolean fullPath) throws GenericEntityException, UnsupportedEncodingException {
		StringBuilder url = new StringBuilder();
		GenericValue webSite = EntityQuery.use(delegator).from("WebSite").where("webSiteId", webSiteId).cache().queryOne();

		if(UtilValidate.isEmpty(product) && UtilValidate.isEmpty(productId)) {
			return null;
		}

		url.append("/").append(webSiteId).append("/").append("control").append("/").append("product");
		if(product == null) {
			product = EntityQuery.use(delegator).from("Product").where("productId", productId).cache().queryOne();
			if(product != null) {
				if(UtilValidate.isNotEmpty(product.getString("primaryProductCategoryId"))) {
					url.append("/").append("~category_id!").append(product.getString("primaryProductCategoryId"));
				}
				url.append("/").append("~product_id!").append(product.getString("productId"));

				String proxiedUrl = UtilProperties.getPropertyValue(EnvUtil.getProxyFile(webSiteId), url.toString());
				if(UtilValidate.isNotEmpty(proxiedUrl)) {
					StringBuffer buf = new StringBuffer();
					proxiedUrl = proxiedUrl.substring(0, 1) + buf.append(URLEncoder.encode(proxiedUrl.substring(1, proxiedUrl.length()), "UTF-8")).toString();
					proxiedUrl = proxiedUrl.replaceAll("%2F", "/");

					url = new StringBuilder();
					if(fullPath) {
						url.append("https://").append(webSite.getString("cookieDomain"));
					}

					url.append(proxiedUrl);
				} else {
					return ((fullPath) ? "https://" + webSite.getString("cookieDomain") : "") + url.toString().replace("!", "=");
				}
			} else {
				return null;
			}
		}

		return url.toString();
	}

	/*
	 * Get all the new products within the last 2 months
	 */
	public static List<GenericValue> getNewArrivals(Delegator delegator, int numOfMonths) throws GenericEntityException {
		return getNewArrivals(delegator, numOfMonths, null);
	}

	public static List<GenericValue> getNewArrivals(Delegator delegator, int numOfMonths, ArrayList<String> webSiteIdList) throws GenericEntityException {
		List<EntityCondition> condition1 = new ArrayList<EntityCondition>();
		List<EntityCondition> condition2 = new ArrayList<EntityCondition>();
		List<EntityCondition> condition3 = new ArrayList<EntityCondition>();

		condition1.add(EntityCondition.makeCondition("createdStamp", EntityOperator.GREATER_THAN_EQUAL_TO, EnvUtil.getNMonthsBeforeOrAfterNow(numOfMonths, true)));

		if (UtilValidate.isNotEmpty(webSiteIdList)) {
			for (String webSiteId : webSiteIdList) {
				condition2.add(EntityCondition.makeCondition(webSiteId.toLowerCase(), EntityOperator.EQUALS, "Y"));
			}

			condition3.add(EntityCondition.makeCondition(condition2, EntityOperator.OR));
		}

		condition3.add(EntityCondition.makeCondition(condition1, EntityOperator.AND));
		return EntityQuery.use(delegator).from("ColorAndWebSite").where(condition3).orderBy("createdStamp DESC").cache().queryList();
	}

	/*
	 * Get whether the product allows for samples or not
	*/
	public static boolean hasSample(Delegator delegator, GenericValue product, String productId) throws GenericEntityException {
		if(product == null) {
			product = EntityQuery.use(delegator).from("Product").where("productId", productId).cache().queryOne();
		}

		if(product != null) {
			return !("N").equalsIgnoreCase(product.getString("hasSample"));
		}
		return true;
	}

	/*
	 * Get whether the product allows for cut or not
	*/
	public static boolean hasCut(Delegator delegator, GenericValue product, String productId) throws GenericEntityException {
		if(product == null) {
			product = EntityQuery.use(delegator).from("Product").where("productId", productId).cache().queryOne();
		}

		if(product != null) {
			return ("Y").equalsIgnoreCase(product.getString("hasCut"));
		}
		return false;
	}

	/*
	 * Get whether the product allows for fold or not
	*/
	public static boolean hasFold(Delegator delegator, GenericValue product, String productId) throws GenericEntityException {
		if(product == null) {
			product = EntityQuery.use(delegator).from("Product").where("productId", productId).cache().queryOne();
		}

		if(product != null) {
			return ("Y").equalsIgnoreCase(product.getString("hasFold"));
		}
		return false;
	}

	/*
	 * Get old school sku qty break
	 */
	public static String deprecatedSkuFormat(String productId, BigDecimal quantity) {
		String newSku = productId + "-" + quantity.stripTrailingZeros().toPlainString();
		if(quantity.compareTo(new BigDecimal("500")) > 0) {
			String perThousand = quantity.divide(new BigDecimal("1000")).stripTrailingZeros().toPlainString();
			newSku = productId + "-" + perThousand + "M";
		}

		return newSku;
	}

	/*
	 * Create a new product price entry
	 */
	public static GenericValue createProductPrice(Delegator delegator, String productId, Long quantity, Long colors, BigDecimal price) throws GenericEntityException {

		GenericValue productPrice = delegator.makeValue("ProductPrice", UtilMisc.toMap("productId", productId));
		productPrice.set("productPriceTypeId", "DEFAULT_PRICE");
		productPrice.set("productPricePurposeId", "PURCHASE");
		productPrice.set("currencyUomId", "USD");
		productPrice.set("productStoreGroupId", "_NA_");
		productPrice.set("fromDate", UtilDateTime.nowTimestamp());
		productPrice.set("quantity", quantity);
		productPrice.set("colors", colors);
		productPrice.set("price", price);

		delegator.create(productPrice);

		return productPrice;
	}

	public static String getProductUPC(Delegator delegator, String productId) throws GenericEntityException {
		List<EntityCondition> conditions = new ArrayList<>();
		conditions.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId));
		conditions.add(EntityCondition.makeCondition("upc", EntityOperator.NOT_EQUAL, null));
		GenericValue price = EntityQuery.use(delegator).from("ProductPrice").where(EntityCondition.makeCondition(conditions, EntityOperator.AND)).cache().queryFirst();

		if(price != null){
			return price.getString("upc");
		}

		return null;
	}

	/*
	 * Get whether the product allows for White Ink Printing
	*/
	public static boolean hasWhiteInk(String productColor) throws GenericEntityException {
		if (productColor != null && colorsWithNoWhiteInk.contains(productColor)) {
			return false;
		}

		return true;
	}

	public static void createProductAsset(Delegator delegator, String productId, String designId, String assetId, String assetType, String assetName, String assetThumbnail, String assetDescription, Long sortOrder, String isDefault) throws GenericEntityException {
		try {
			delegator.createOrStore(delegator.makeValue("ProductAssets", UtilMisc.toMap("assetId", (UtilValidate.isEmpty(assetId) ? delegator.getNextSeqId("ProductAssets") : assetId), "productId", productId, "designId", designId, "assetType", assetType, "assetName", assetName, "assetThumbnail", assetThumbnail, "assetDescription", assetDescription, "sortOrder", sortOrder, "assetDefault", isDefault)));
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to create or update asset. " + e + " : " + e.getMessage(), module);
		}
	}
	/*
	 * Remove Product Assets
	 */
	public static void removeProductAsset(Delegator delegator, String assetId) {
		try {
			delegator.removeValue(EntityQuery.use(delegator).from("ProductAssets").where("assetId", assetId).queryOne());
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to remove asset. " + e + " : " + e.getMessage(), module);
		}
	}

	/*
	 * Get a list of product assets
	 * DEPRECATED METHOD, USE OTHER
	 */
	public static List<GenericValue> getProductAssets(Delegator delegator, String productId) {
		return getProductAssets(delegator, productId, "plain", null);
	}

	/**
	 * Get the vendor product id given matching features
	 * @param delegator
	 * @param productFeatureId
	 * @return
	 * @throws GenericEntityException
	 */
	public static String getVendorProductId(Delegator delegator, String productId, String vendorPartyId, String productFeatureId, String productFeatureTypeId, String description) throws GenericEntityException {
		String vendorProductId = null;
		if (productId != null && vendorPartyId != null) {
			List<EntityCondition> conditons = new ArrayList<>();
			conditons.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId));
			conditons.add(EntityCondition.makeCondition("vendorPartyId", EntityOperator.EQUALS, vendorPartyId));
			if(UtilValidate.isNotEmpty(productFeatureId)) {
				conditons.add(EntityCondition.makeCondition("productFeatureId", EntityOperator.EQUALS, productFeatureId));
			}
			if(UtilValidate.isNotEmpty(productFeatureTypeId)) {
				conditons.add(EntityCondition.makeCondition("productFeatureTypeId", EntityOperator.EQUALS, productFeatureTypeId));
			}
			if(UtilValidate.isNotEmpty(description)) {
				conditons.add(EntityCondition.makeCondition("description", EntityOperator.EQUALS, description));
			}
			GenericValue vPFA = EntityQuery.use(delegator).from("VendorProductFeatureAndAppl").where(EntityCondition.makeCondition(conditons, EntityOperator.AND)).cache().queryFirst();
			if (vPFA != null) {
				vendorProductId = vPFA.getString("vendorProductId");
			}
		}

		return vendorProductId;
	}

	/*
		Get a list of product assets based on product type (plain/printed)
	 */
	public static List<GenericValue> getProductAssets(Delegator delegator, String productId, String type) {
		return getProductAssets(delegator, productId, type, null);
	}

	public static List<GenericValue> getProductAssets(Delegator delegator, String productId, String type, String designId) {
		if (UtilValidate.isEmpty(type)) {
			type = "plain";
		}

		List prodAssetConditions = new ArrayList();

		if ((type.toLowerCase()).equals("plain")) {
			prodAssetConditions.add(EntityCondition.makeCondition("assetType", EntityOperator.NOT_EQUAL, "printed"));
			prodAssetConditions.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId));
		} else if ((type.toLowerCase()).equals("printed") && UtilValidate.isNotEmpty(designId)) {
			prodAssetConditions.add(EntityCondition.makeCondition("assetType", EntityOperator.EQUALS, "printed"));
			prodAssetConditions.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId));
			prodAssetConditions.add(EntityCondition.makeCondition("designId", EntityOperator.EQUALS, designId));
		}

		try {
			return EntityQuery.use(delegator).from("ProductAssets").where(EntityCondition.makeCondition(prodAssetConditions, EntityOperator.AND)).orderBy("sortOrder ASC").cache().queryList();
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to obtain all product assets for product " + productId + ". " + e + " : " + e.getMessage(), module);
			return null;
		}
	}

	/*
	 * check if passed in value is custom product
	 */
	public static boolean isSampleSku(String productId) {
		return productId.equalsIgnoreCase(EnvConstantsUtil.SAMPLE_PRODUCT);
	}

	/*
	 * check if passed in value is custom product
	 */
	public static boolean isCustomSku(String productId) {
		return productId.equalsIgnoreCase(EnvConstantsUtil.CUSTOM_PRODUCT);
	}

	/*
	 * check if passed in value is direct mail product
	 */
	public static boolean isDirectMailSku(String productId) {
		return productId.startsWith(EnvConstantsUtil.DIRECT_MAIL_PRODUCT);
	}

	public static boolean isPricingEngineSku(Delegator delegator, String productId) throws GenericEntityException {
		List<GenericValue> prices = EntityQuery.use(delegator).from("ProductPrice").where("productId", productId, "productPriceTypeId", "DEFAULT_PRICE").orderBy(UtilMisc.toList("quantity ASC", "colors ASC")).cache().filterByDate().queryList();
		if(UtilValidate.isEmpty(prices)) {
			return true;
		}

		return  false;
	}

	/**
	 * Determine if a product is mailable given its dimensions
	 * @param delegator
	 * @param product
	 * @param productId
	 * @return
	 */
	public static boolean isMailable(Delegator delegator, GenericValue product, String productId) {
		try {
			if(product == null) {
				product = EntityQuery.use(delegator).from("Product").where("productId", productId).cache().queryOne();
			}
		} catch (Exception e) {
			//
		}

		List<GenericValue> products = new ArrayList<>();
		products.add(product);

		List<EntityCondition> condition1 = new ArrayList<EntityCondition>();
		condition1.add(EntityCondition.makeCondition("productHeight", EntityOperator.GREATER_THAN_EQUAL_TO, new BigDecimal("3.5")));
		condition1.add(EntityCondition.makeCondition("productWidth", EntityOperator.GREATER_THAN_EQUAL_TO, new BigDecimal("5")));

		List<EntityCondition> condition2 = new ArrayList<EntityCondition>();
		condition2.add(EntityCondition.makeCondition("productHeight", EntityOperator.GREATER_THAN_EQUAL_TO, new BigDecimal("5")));
		condition2.add(EntityCondition.makeCondition("productWidth", EntityOperator.GREATER_THAN_EQUAL_TO, new BigDecimal("3.5")));

		List<EntityCondition> condition3 = new ArrayList<EntityCondition>();
		condition3.add(EntityCondition.makeCondition(condition1, EntityOperator.AND));
		condition3.add(EntityCondition.makeCondition(condition2, EntityOperator.AND));

		products = EntityUtil.filterByOr(products, condition3);

		return UtilValidate.isNotEmpty(products);
	}

	public static GenericValue getMaterial(Delegator delegator, String materialId) throws GenericEntityException {
		return EntityQuery.use(delegator).from("Material").where("materialId", materialId).cache().queryFirst();
	}

	public static Map<String, Object> getSortedProductAttributes(Delegator delegator, String productId) {
		try {
			if (UtilValidate.isEmpty(sortedProductAttributesByProductId) && UtilValidate.isEmpty(sortedProductAttributesByProductId.get(productId))) {
				setSortedProductAttributes(delegator, productId);
			}

			return (UtilValidate.isNotEmpty(sortedProductAttributesByProductId.get(productId)) ? (Map<String, Object>) sortedProductAttributesByProductId.get(productId) : null);
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to get sorted product attributes.  Product ID: " + productId + ". " + e + " : " + e.getMessage(), module);
		}

		return null;
	}

	public static void setSortedProductAttributes(Delegator delegator, String productId) {
		EntityListIterator productAttributeList = null;

		try {
			Map<String, Object> sortedProductAttributes = new HashMap<>();
			productAttributeList = EntityQuery.use(delegator).from("ProductAttribute").where("productId", productId).queryIterator();

			GenericValue productAttribute = null;
			String attributeName = "";

			List<Object> productAttributesToBeStoredList = new ArrayList<>();
			HashMap<String, String> productAttributesToBeSorted = new HashMap<>();

			while ((productAttribute = productAttributeList.next()) != null) {
				productAttributesToBeSorted = new HashMap<>();

				if (!productAttribute.get("attrName").equals(attributeName)) {
					productAttributesToBeStoredList = new ArrayList<>();
				}

				productAttributesToBeSorted.put("attrName", (String) productAttribute.get("attrName"));
				productAttributesToBeSorted.put("attrValue", (String) productAttribute.get("attrValue"));
				productAttributesToBeSorted.put("attrType", (String) productAttribute.get("attrType"));
				productAttributesToBeSorted.put("attrDescription", (String) productAttribute.get("attrDescription"));
				productAttributesToBeStoredList.add(productAttributesToBeSorted);

				if (!productAttribute.get("attrName").equals(attributeName)) {
					sortedProductAttributes.put((String) productAttribute.get("attrName"), productAttributesToBeStoredList);
					attributeName = (String) productAttribute.get("attrName");
				}
			}

			sortedProductAttributesByProductId.put(productId, sortedProductAttributes);
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to set sorted product attributes.  Product ID: " + productId + ". " + e + " : " + e.getMessage(), module);
		} finally {
			if(productAttributeList != null) {
				try {
					productAttributeList.close();
				} catch (GenericEntityException e2) {
					EnvUtil.reportError(e2);
				}
			}
		}
	}

	public static void createProductAttribute(Delegator delegator, String attributeName, String attributeValue, String productId) {
		try {
			GenericValue newValue = delegator.makeValue("ProductAttribute", UtilMisc.toMap("productId", productId.trim(), "attrName", attributeName, "attrValue", attributeValue));
			delegator.createOrStore(newValue);
			OrderHelper.resetOrderAttrMap(delegator);
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("There was an issue while trying to add a new product attribute: " + e, module);
		}
	}

	public static void setTopBlankFoldersData(Delegator delegator, LocalDispatcher dispatcher, HttpServletRequest request) {
		try {
			for (String productId : topBlankFolders) {
				Product product = new Product(delegator, dispatcher, productId, request);

				if (UtilValidate.isNotEmpty(product)) {
					topBlankFoldersData.add(product);
				}
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("There was an issue getting the top blank folders data: " + e, module);
		}
	}

	public static List<Object> getTopBlankFoldersData(Delegator delegator, LocalDispatcher dispatcher, HttpServletRequest request) {
		if (UtilValidate.isEmpty(topBlankFoldersData)) {
			setTopBlankFoldersData(delegator, dispatcher, request);
		}

		return topBlankFoldersData;
	}

	public static String getVendorColor(Delegator delegator, Map<String, String> productFeatures) {
		String vendorColor = "";

		try {
			GenericValue colorVendorStockAssoc = EntityQuery.use(delegator).from("PeColorVendorStockAssoc").where(
				"colorGroup", (UtilValidate.isNotEmpty(productFeatures.get("Color Group")) ? productFeatures.get("Color Group") : ""),
				"colorName", (UtilValidate.isNotEmpty(productFeatures.get("Color")) ? productFeatures.get("Color") : ""),
				"paperTexture", (UtilValidate.isNotEmpty(productFeatures.get("Paper Texture")) ? productFeatures.get("Paper Texture") : "") + (UtilValidate.isNotEmpty(productFeatures.get("Coating")) ? " C" + productFeatures.get("Coating") + "S" : ""),
				"paperWeight", (UtilValidate.isNotEmpty(productFeatures.get("Paper Weight")) ? productFeatures.get("Paper Weight") : "")
			).cache().queryOne();
			vendorColor = colorVendorStockAssoc.getString("vendorStock");
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("There was an issue while trying to get vendor color: " + e, module);
		}

		return vendorColor;
	}

	public static void removeProductPrice(Delegator delegator, String productId, Long colors, Long quantity, BigDecimal price) throws GenericEntityException {
		List<GenericValue> productPrices = EntityQuery.use(delegator).from("ProductPrice").where("productId", productId, "colors", colors, "quantity", quantity, "price", price).queryList();
		if (productPrices.size() == 1) {
			delegator.removeValue(productPrices.get(0));
		}
	}

	public static void linkDesignsAndProducts(Delegator delegator) throws GenericEntityException {
		List<GenericValue> designs = EntityQuery.use(delegator).from("DesignTemplate").where("active", "Y").queryList();

		for(GenericValue design : designs) {
			if (UtilValidate.isNotEmpty(design.getString("legacyDesignId"))) {
				List<GenericValue> scene7Products = EntityQuery.use(delegator).from("Scene7ProdAssoc").where("scene7TemplateId", design.getString("legacyDesignId")).queryList();
				for (GenericValue scene7Product : scene7Products) {
					if (UtilValidate.isNotEmpty(design.getString("designTemplateId")) && UtilValidate.isNotEmpty(scene7Product.getString("productId"))) {
						GenericValue product = EntityQuery.use(delegator).from("Product").where("productId", scene7Product.getString("productId")).queryOne();
						if (UtilValidate.isNotEmpty(product)) {
							GenericValue productDesignTemplate = delegator.makeValue("ProductDesignTemplate", UtilMisc.toMap("productId", scene7Product.getString("productId"), "designTemplateId", design.getString("designTemplateId")));
							delegator.createOrStore(productDesignTemplate);
						}
					}
				}
			}
		}
	}

	public static List<Map<String, Object>> getProductDesigns(Delegator delegator, Product product) {
		return getProductDesigns(delegator, product.getId(), product.getParentId());
	}

	public static List<Map<String, Object>> getProductDesigns(Delegator delegator, String productId, String parentProductId) {
		List<Map<String, Object>> designs = new ArrayList<>();

		if (UtilValidate.isNotEmpty(productId)) {
			try {
				List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
				conditionList.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId));

				if (UtilValidate.isNotEmpty(parentProductId)) {
					conditionList.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, parentProductId));
				}

				List<GenericValue> designTemplateAndProductDesignTemplates = EntityQuery.use(delegator).from("DesignTemplateAndProductDesignTemplate").where(EntityCondition.makeCondition(conditionList, EntityOperator.OR)).cache().queryList();

				for(GenericValue designTemplateAndProductDesignTemplate : designTemplateAndProductDesignTemplates) {
					Map<String, Object> designInfo = new HashMap<>();

					designInfo.put("designTemplateId", designTemplateAndProductDesignTemplate.getString("designTemplateId"));
					designInfo.put("legacyDesignId", designTemplateAndProductDesignTemplate.getString("legacyDesignId"));
					designInfo.put("location", designTemplateAndProductDesignTemplate.getString("location"));
					designInfo.put("name", designTemplateAndProductDesignTemplate.getString("name"));
					designInfo.put("width", designTemplateAndProductDesignTemplate.getString("width"));
					designInfo.put("height", designTemplateAndProductDesignTemplate.getString("height"));
					designInfo.put("jsonData", designTemplateAndProductDesignTemplate.getString("jsonData"));
					//designInfo.put("fxgData", designTemplateAndProductDesignTemplate.getString("fxgData"));
					designInfo.put("active", designTemplateAndProductDesignTemplate.getString("active"));

					designs.add(designInfo);
				}
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError("Error trying to obtain product designs. " + e.getMessage(), module);
			}
		}

		return designs;
	}

	public static boolean generateProductExport() {
		return generateProductExport("");
	}

	public static boolean generateProductExport(String args) {
		Process process;

		try {
			process = Runtime.getRuntime().exec("perl " + EnvConstantsUtil.OFBIZ_HOME + "etc/product_export.pl" + (UtilValidate.isNotEmpty(args) ? " " + args : ""));
			process.waitFor();

			if (process.exitValue() == 0) {
				return true;
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Issue trying to run script to get product export. " + e.getMessage(), module);
		}

		return false;
	}

	public static Map<String, Object> getProductShippingWeightInfo(Delegator delegator, String productId) throws GenericEntityException {
		GenericValue productShippingWeightInfoResult = EntityQuery.use(delegator).from("Product").where("productId", productId).queryOne();

		Map<String, Object> productShippingWeightInfo = new HashMap<>();
		productShippingWeightInfo.put("productId", productShippingWeightInfoResult.get("productId"));
		productShippingWeightInfo.put("shippingWeight", productShippingWeightInfoResult.get("shippingWeight"));
		productShippingWeightInfo.put("productWeight", productShippingWeightInfoResult.get("productWeight"));
		productShippingWeightInfo.put("boxQty", productShippingWeightInfoResult.get("boxQty"));
		productShippingWeightInfo.put("cartonQty", productShippingWeightInfoResult.get("cartonQty"));

		return productShippingWeightInfo;
	}

	public static void addProductCategory(Delegator delegator, String productCategoryId, String productCategoryTypeId, String primaryParentCategoryId, String categoryName, String description, String longDescription) throws GenericEntityException {
		GenericValue newValue = delegator.makeValue("ProductCategory", UtilMisc.toMap("productCategoryId", productCategoryId, "primaryParentCategoryId", primaryParentCategoryId, "categoryName", categoryName, "description", description, "longDescription", longDescription));
		delegator.createOrStore(newValue);
	}

	public static List<Map<String, Object>> getProductCategories(Delegator delegator) throws GenericEntityException {
		List<Map<String,Object>> productCategories = new ArrayList<>();

		List<GenericValue> productCategoriesResponseList = EntityQuery.use(delegator).from("ProductCategory").queryList();

		for (GenericValue productCategoriesResponse : productCategoriesResponseList) {
			Map<String, Object> productCategoryData = new HashMap<>();

			productCategoryData.put("productCategoryId", productCategoriesResponse.get("productCategoryId"));
			productCategoryData.put("primaryParentCategoryId", productCategoriesResponse.get("primaryParentCategoryId"));
			productCategoryData.put("categoryName", productCategoriesResponse.get("categoryName"));
			productCategoryData.put("description", productCategoriesResponse.get("description"));
			productCategoryData.put("longDescription", productCategoriesResponse.get("longDescription"));

			productCategories.add(productCategoryData);
		}

		return productCategories;
	}

	public static boolean isSpecialQuantityOffer(Product product) {
		boolean isSpecialQuantityOffer = false;

		try {
			if (product.onSale() || product.onClearance()) {
				try {
					Iterator quantities = product.getPrices().keySet().iterator();
					String specialQuantityOfferProgress = "";

					while(quantities.hasNext()) {
						Integer quantity = (Integer) quantities.next();

						if (UtilValidate.isNotEmpty(((HashMap) ((TreeMap) product.getOriginalPrices()).get(quantity)).get("price")) && ((HashMap) ((TreeMap) product.getOriginalPrices()).get(quantity)).get("price") != ((HashMap) ((TreeMap) product.getPrices()).get(quantity)).get("price")) {
							if (specialQuantityOfferProgress.equals("equal")) {
								isSpecialQuantityOffer = true;
								break;
							}

							specialQuantityOfferProgress = "notEqual";
						} else {
							if (specialQuantityOfferProgress.equals("notEqual")) {
								isSpecialQuantityOffer = true;
								break;
							}

							specialQuantityOfferProgress = "equal";
						}
					}
				} catch (GenericServiceException gse) {
					Debug.log("Error trying to get product prices keyset: " + gse.getMessage(), module);
				}
			}
		} catch (GenericEntityException gee) {
			Debug.log("Error trying to see if product is special quantity offer: " + gee.getMessage(), module);
		}

		return isSpecialQuantityOffer;
	}

	public static List<GenericValue> getProductAddons(Delegator delegator, String productId) {
		List<GenericValue> productAddonList = null;

		try {
			productAddonList = EntityQuery.use(delegator).from("ProductAddon").where("productId", productId).cache(true).queryList();
		} catch (Exception e) {
			Debug.log("Error trying to retrieve product addons for product: " + productId + ". " + e.getMessage(), module);
		}

		return productAddonList;
	}

	public static List<GenericValue> getProductAddonQuantityThresholds(Delegator delegator, String productAddonId) {
		List<GenericValue> productAddonQuantityThresholdList = null;

		try {
			productAddonQuantityThresholdList = EntityQuery.use(delegator).from("ProductAddonQuantityThreshold").where("productAddonId", productAddonId).cache(true).queryList();
		} catch (Exception e) {
			Debug.log("Error trying to retrieve product addon quantity thresholds for product: " + productAddonId + ". " + e.getMessage(), module);
		}

		return productAddonQuantityThresholdList;
	}

	public static Map<String, Map<String, Object>> getProductAddonData(Delegator delegator, String productId) {
		Map<String, Map<String, Object>> productAddonDataMap = new HashMap<>();

		try {
			List<GenericValue> productAddonAndQuantityThresholdList = EntityQuery.use(delegator).from("ProductAddonAndQuantityThreshold").where("productId", productId).cache(true).queryList();

			for (GenericValue productAddonAndQuantityThreshold : productAddonAndQuantityThresholdList) {
				Map<String, Object> productAddonData = new HashMap<>();
				Map<String, Object> quantityThresholdData = new HashMap<>();

				productAddonData.put("name", productAddonAndQuantityThreshold.getString("name"));

				List<Map<String, Object>> productAddonQuantityThresholdDataList = new ArrayList<>();

				if (UtilValidate.isNotEmpty(productAddonDataMap.get(productAddonAndQuantityThreshold.getString("productAddonId"))) && UtilValidate.isNotEmpty(productAddonDataMap.get(productAddonAndQuantityThreshold.getString("productAddonId")).get("quantityThresholdData"))) {
					productAddonQuantityThresholdDataList = (ArrayList) productAddonDataMap.get(productAddonAndQuantityThreshold.getString("productAddonId")).get("quantityThresholdData");
				}

				quantityThresholdData.put("type", productAddonAndQuantityThreshold.getString("type"));
				quantityThresholdData.put("threshold", new BigDecimal(String.valueOf(productAddonAndQuantityThreshold.get("threshold"))));
				quantityThresholdData.put("quantity", new BigDecimal(String.valueOf(productAddonAndQuantityThreshold.getString("quantity"))));
				quantityThresholdData.put("price",  new BigDecimal(String.valueOf(productAddonAndQuantityThreshold.getString("price"))));
				productAddonQuantityThresholdDataList.add(quantityThresholdData);
				productAddonData.put("quantityThresholdData", productAddonQuantityThresholdDataList);
				productAddonDataMap.put(productAddonAndQuantityThreshold.getString("productAddonId"), productAddonData);
			}
		} catch (Exception e) {
			Debug.log("Error trying to retrieve product addon data for product: " + productId + ". " + e.getMessage(), module);
		}

		return productAddonDataMap;
	}

	public static GenericValue getWebSiteData(Delegator delegator, String productId) {
		GenericValue webSiteData = null;

		try {
			webSiteData = EntityQuery.use(delegator).from("ProductWebSite").where("productId", productId).cache().queryOne();
		} catch (Exception e) {
			Debug.log("There was an issue trying to get the website list for product, " + productId + ": " + e.getMessage(), module);
		}

		return webSiteData;
	}

	public static boolean allowRushProduction() {
		return allowRushProduction(false);
	}

	public static boolean allowRushProduction(boolean defaultValue) {
		return UtilProperties.getPropertyAsBoolean("envelopes", "envelopes.allowRushProduction", defaultValue);
	}

	public static boolean showOutOfStockRecommendations(Delegator delegator, GenericValue product, String productId) throws GenericEntityException {
		if(product == null) {
			product = EntityQuery.use(delegator).from("Product").where("productId", productId).cache().queryOne();
		}

		if(product != null) {
			return ("Y").equalsIgnoreCase(product.getString("showOutOfStockRecommendations"));
		}

		return false;
	}
}