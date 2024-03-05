/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.cxml;

import com.bigname.pricingengine.PricingEngineHelper;
import com.bigname.pricingengine.util.EngineUtil;
import com.envelopes.netsuite.NetsuiteHelper;
import com.envelopes.order.OrderHelper;
import com.envelopes.product.ProductHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import com.google.gson.Gson;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.order.order.OrderReadHelper;
import org.apache.ofbiz.service.LocalDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class OutsourceRule {
	public static final String module = OutsourceRule.class.getName();

	protected Delegator delegator = null;
	protected LocalDispatcher dispatcher = null;

	protected GenericValue product = null; //The product data of the order item
	protected GenericValue productPrice = null; //Our closest ProductPrice data for the item
	protected OrderReadHelper orh = null; //OrderReadHelper object for easy data access
	protected GenericValue orderItem = null; //The orderItem that is being outsourced
	protected Map<String, String> orderItemAttribute = null; //The orderItemAttribute that is being outsourced
	protected GenericValue orderItemArtwork = null; //The orderItemArtwork data
	protected GenericValue orderItemPriceAttr = null; //The price map attribute for new Pricing Engine
	protected Map pricingMap = new HashMap(); //The price map for new Pricing Engine
	protected GenericValue shippingAddress = null; //The postalAddress data for shipping location
	protected String shippingMethod = null; //The shipping method used

	protected static Map<Integer, BigDecimal> variableDataCharge = new LinkedHashMap<>();
	protected static Map<Integer, BigDecimal> whiteInkCharge = new LinkedHashMap<>();
	protected static Map<Integer, BigDecimal> heavyInkCoverageCharge = new LinkedHashMap<>();
	protected static Map<Integer, BigDecimal> foldingCharge = new LinkedHashMap<>();
	protected static Map<Integer, BigDecimal> cuttingCharge = new LinkedHashMap<>();
	static {
		variableDataCharge.put(50, new BigDecimal("35.00"));
		variableDataCharge.put(100, new BigDecimal("45.00"));
		variableDataCharge.put(250, new BigDecimal("65.00"));
		variableDataCharge.put(500, new BigDecimal("75.00"));
		variableDataCharge.put(1000, new BigDecimal("100.00"));
		variableDataCharge.put(2000, new BigDecimal("160.00"));
		variableDataCharge.put(2500, new BigDecimal("175.00"));
		variableDataCharge.put(5000, new BigDecimal("325.00"));
		variableDataCharge.put(10000, new BigDecimal("600.00"));
		variableDataCharge.put(15000, new BigDecimal("750.00"));
		variableDataCharge.put(20000, new BigDecimal("900.00"));
		variableDataCharge.put(25000, new BigDecimal("1000.00"));
		variableDataCharge.put(50000, new BigDecimal("1750.00"));
		variableDataCharge.put(75000, new BigDecimal("2250.00"));
		variableDataCharge.put(100000, new BigDecimal("2500.00"));

		whiteInkCharge.put(50, new BigDecimal("17.50"));
		whiteInkCharge.put(100, new BigDecimal("27.50"));
		whiteInkCharge.put(250, new BigDecimal("57.50"));
		whiteInkCharge.put(500, new BigDecimal("102.50"));
		whiteInkCharge.put(1000, new BigDecimal("202.50"));
		whiteInkCharge.put(2000, new BigDecimal("405.00"));
		whiteInkCharge.put(2500, new BigDecimal("506.25"));
		whiteInkCharge.put(5000, new BigDecimal("1012.50"));
		whiteInkCharge.put(10000, new BigDecimal("2025.00"));
		whiteInkCharge.put(15000, new BigDecimal("3037.50"));
		whiteInkCharge.put(20000, new BigDecimal("4050.00"));
		whiteInkCharge.put(25000, new BigDecimal("5062.50"));
		whiteInkCharge.put(50000, new BigDecimal("10125.00"));
		whiteInkCharge.put(75000, new BigDecimal("15187.50"));
		whiteInkCharge.put(100000, new BigDecimal("20250.00"));


		heavyInkCoverageCharge.put(50, new BigDecimal("24.00"));
		heavyInkCoverageCharge.put(100, new BigDecimal("30.00"));
		heavyInkCoverageCharge.put(250, new BigDecimal("48.00"));
		heavyInkCoverageCharge.put(500, new BigDecimal("76.00"));
		heavyInkCoverageCharge.put(1000, new BigDecimal("110.00"));
		heavyInkCoverageCharge.put(2000, new BigDecimal("200.00"));
		heavyInkCoverageCharge.put(2500, new BigDecimal("225.00"));
		heavyInkCoverageCharge.put(5000, new BigDecimal("400.00"));
		heavyInkCoverageCharge.put(10000, new BigDecimal("700.00"));
		heavyInkCoverageCharge.put(15000, new BigDecimal("1050.00"));
		heavyInkCoverageCharge.put(20000, new BigDecimal("1400.00"));
		heavyInkCoverageCharge.put(25000, new BigDecimal("1750.00"));
		heavyInkCoverageCharge.put(50000, new BigDecimal("3500.00"));
		heavyInkCoverageCharge.put(75000, new BigDecimal("5250.00"));
		heavyInkCoverageCharge.put(100000, new BigDecimal("7000.00"));


		foldingCharge.put(50, new BigDecimal("10.00"));
		foldingCharge.put(100, new BigDecimal("12.00"));
		foldingCharge.put(250, new BigDecimal("13.00"));
		foldingCharge.put(500, new BigDecimal("14.00"));
		foldingCharge.put(1000, new BigDecimal("15.00"));
		foldingCharge.put(2000, new BigDecimal("30.00"));
		foldingCharge.put(2500, new BigDecimal("37.50"));
		foldingCharge.put(5000, new BigDecimal("75.00"));
		foldingCharge.put(10000, new BigDecimal("150.00"));
		foldingCharge.put(15000, new BigDecimal("225.00"));
		foldingCharge.put(20000, new BigDecimal("300.00"));
		foldingCharge.put(25000, new BigDecimal("375.00"));
		foldingCharge.put(50000, new BigDecimal("750.00"));
		foldingCharge.put(75000, new BigDecimal("1125.00"));
		foldingCharge.put(100000, new BigDecimal("1500.00"));


		cuttingCharge.put(50, new BigDecimal("12.00"));
		cuttingCharge.put(100, new BigDecimal("15.00"));
		cuttingCharge.put(250, new BigDecimal("17.00"));
		cuttingCharge.put(500, new BigDecimal("20.00"));
		cuttingCharge.put(1000, new BigDecimal("25.00"));
		cuttingCharge.put(2000, new BigDecimal("50.00"));
		cuttingCharge.put(2500, new BigDecimal("62.50"));
		cuttingCharge.put(5000, new BigDecimal("125.00"));
		cuttingCharge.put(10000, new BigDecimal("250.00"));
		cuttingCharge.put(15000, new BigDecimal("375.00"));
		cuttingCharge.put(20000, new BigDecimal("500.00"));
		cuttingCharge.put(25000, new BigDecimal("625.00"));
		cuttingCharge.put(50000, new BigDecimal("1250.00"));
		cuttingCharge.put(75000, new BigDecimal("1875.00"));
		cuttingCharge.put(100000, new BigDecimal("2500.00"));
	}

	protected int colorsFront = 0;
	protected boolean isFrontBlack = false;
	protected int colorsBack = 0;
	protected boolean isBackBlack = false;
	protected int maxColors = 0;
	protected BigDecimal itemRevenue = BigDecimal.ZERO;
	protected BigDecimal netItemRevenue = BigDecimal.ZERO;
	protected BigDecimal itemCost = BigDecimal.ZERO;
	protected BigDecimal itemPrintCost = BigDecimal.ZERO;
	protected BigDecimal itemMfgCost = BigDecimal.ZERO;
	protected BigDecimal shippingCost = BigDecimal.ZERO;

	public static Map<String, String> osIssue = new HashMap<>();
	public static Map<String, String> osStatus = new HashMap<>();

	static {
		osIssue.put("1", "Printed on Vendor Supplied Wrong Product");
		osIssue.put("2", "Quality / Print");
		osIssue.put("3", "Quality / Vendor Supplied Product");
		osIssue.put("4", "Vendor Shipped (Packed / Labeled)");
		osIssue.put("5", "Vendor Late on Job");
		osIssue.put("7", "Need Additional Stock / Vendor Print Error");
		osIssue.put("8", "Need Additional Stock / BNC Print File Issue");
		osIssue.put("9", "Need Additional Stock / BNC Picked Short");
		osIssue.put("10", "Need Additional Stock / Vendor Needs Make Ready");
		osIssue.put("11", "Need Additional Stock / Vendor Lost");
		osIssue.put("12", "Need Additional Stock / Vendor Reprint");
		osIssue.put("13", "BNC Picked Wrong Product");
		osIssue.put("14", "BNC Picked Damaged Stock");
		osIssue.put("15", "Need Additional Stock / Vendor Didn't Receive Stock");
		osIssue.put("16", "Ship Carrier Lost or Damaged Shipment");

		osStatus.put("1", "Waiting on Stock");
		osStatus.put("9", "Mod Request");
		osStatus.put("2", "Stock Received");
		osStatus.put("10", "Scheduling");
		osStatus.put("14", "Assigned to Press");
		osStatus.put("5", "On Press");
		osStatus.put("3", "Backorder");
		osStatus.put("6", "Printed/Pending Shipment");
		osStatus.put("7", "Shipped Complete");
		osStatus.put("8", "Shipped Partial");
	}

	public static String outsourceList(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;

		try {
			OutsourceRule oRule = new OutsourceRule(delegator, dispatcher, (String) context.get("orderId"), (String) context.get("orderItemSeqId"));
			jsonResponse.put("netItemRevenue", oRule.getNetItemRevenue());
			jsonResponse.put("grossProfit", oRule.getVendorAndPrice());
			success = true;
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to get outsource rule info. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success" , success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/**
	 * Constructor OutsourceRule
	 * @param delegator
	 * @param orderId
	 * @param orderItemSeqId
	 * @throws GenericEntityException
	 */
	public OutsourceRule(Delegator delegator, LocalDispatcher dispatcher, String orderId, String orderItemSeqId) throws Exception {
		this.delegator = delegator;
		this.dispatcher = dispatcher;

		if(UtilValidate.isNotEmpty(orderId)) {
			this.orh = new OrderReadHelper(this.delegator, orderId);
		}

		if(UtilValidate.isNotEmpty(orderItemSeqId)) {
			this.orderItem = this.orh.getOrderItem(orderItemSeqId);
			this.orderItemArtwork = OrderHelper.getOrderItemArtwork(this.delegator, this.orderItem.getString("orderId"), this.orderItem.getString("orderItemSeqId"));
			this.orderItemAttribute = OrderHelper.getOrderItemAttributeMap(this.delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId"));
			this.orderItemPriceAttr = EntityUtil.getFirst(OrderHelper.getOrderItemAttribute(this.delegator, this.orderItem.getString("orderId"), this.orderItem.getString("orderItemSeqId"), "pricingRequest"));
			this.shippingAddress = OrderHelper.getShippingAddress(this.orh, this.delegator, orderId);
			this.shippingMethod = this.orh.getShippingMethodType("00001");
			this.product = this.orderItem.getRelatedOne("Product", true);

			if(UtilValidate.isNotEmpty(this.orderItemPriceAttr)) {
				Map tempPricingMap = new Gson().fromJson(this.orderItemPriceAttr.getString("attrValue"), Map.class);
				if(!EngineUtil.isEmpty(tempPricingMap)) {
					try {
						this.pricingMap = PricingEngineHelper.getPricing(tempPricingMap);
					} catch(Exception e) {
						Debug.logError("No vendor pricing found for product : " + this.orderItem.getString("productId"), module);
					}
				}
			}

			setColors();
			setItemRevenue();
			setLocalPrice();
			setLocalCost();
			setLocalPrintCost();
			setLocalManufacturingCost();
			setShippingCost();
		}
	}

	/**
	 * Get the list of vendors and their prices for an order item
	 * @return
	 * @throws GenericEntityException
	 */
	public Map<String, Map<String, BigDecimal>> getVendorAndPrice() throws GenericEntityException {
		Map<String, Map<String, BigDecimal>> vendorPrices = new HashMap<>();

		GenericValue orderItemArtwork = OrderHelper.getOrderItemArtwork(this.delegator, this.orderItem.getString("orderId"), this.orderItem.getString("orderItemSeqId"));
		Set<String> inkColors = new HashSet<>();
		for(int i = 0, j = 0; i < 4 && j < 2; i ++) {
			String key = j == 0 ? "frontInkColor" + (i + 1) : "backInkColor" + (i + 1);
			String inkColor = orderItemArtwork.getString(key);
			if(UtilValidate.isNotEmpty(inkColor)) {
				inkColors.add(inkColor.trim());
				if(i == 3) {
					i = 0;
					j ++;
				}
			} else {
				i = 0;
				j ++;
				break;
			}
		}

		List<GenericValue> vendorProducts = getVendorProducts();
		for(GenericValue vendorProduct : vendorProducts) {
			GenericValue vendor = delegator.findOne("Vendor", UtilMisc.toMap("partyId", (String) vendorProduct.getString("vendorPartyId")), true);
			Map<String, Object> vendorRules = CXMLHelper.getVendorRules(vendor);
			List<String> supportedInkColors = (List<String>) vendorRules.get("inkColors");
			if(UtilValidate.isNotEmpty(supportedInkColors)) {
				if(!supportedInkColors.containsAll(inkColors)) {
					continue;
				}
			}
			//get the prices
			GenericValue vendorProductPrice = getVendorPrintPrice(vendorProduct.getString("vendorPartyId"), vendorProduct.getString("vendorProductId"));
			if(UtilValidate.isNotEmpty(vendorProductPrice)) {
				BigDecimal vendorPPM = vendorProductPrice.getBigDecimal("price").divide(new BigDecimal(vendorProductPrice.getLong("quantity").toString()), EnvConstantsUtil.ENV_SCALE_L, EnvConstantsUtil.ENV_ROUNDING);
				vendorPrices.put(vendorProduct.getString("vendorPartyId"), getGrossProfit(vendorProduct.getString("vendorPartyId"), vendorPPM));
			} else if(ProductHelper.isCustomSku(vendorProduct.getString("productId"))) {
				vendorPrices.put(vendorProduct.getString("vendorPartyId"), getGrossProfit(vendorProduct.getString("vendorPartyId"), BigDecimal.ZERO));
			}
		}

		if(UtilValidate.isNotEmpty(this.productPrice.getBigDecimal("cost")) && UtilValidate.isNotEmpty(this.productPrice.getBigDecimal("printCost"))) {
			BigDecimal localPPM = this.productPrice.getBigDecimal("cost").add(this.productPrice.getBigDecimal("printCost"));
			vendorPrices.put("Envelopes.com", getGrossProfit("Envelopes.com", localPPM));
		}

		return vendorPrices;
	}

	public BigDecimal getNetItemRevenue() {
		return this.netItemRevenue;
	}

	/**
	 * Get the gross profit of an order item
	 * @param partyId
	 * @param vendorPricePerM
	 * @return
	 */
	private Map<String, BigDecimal> getGrossProfit(String partyId, BigDecimal vendorPricePerM) {
		Map<String, BigDecimal> data = new HashMap<>();
		BigDecimal quantity = this.orderItem.getBigDecimal("quantity");
		BigDecimal cost = vendorPricePerM.multiply(quantity);
		int addresses = Integer.parseInt(orderItemAttribute.getOrDefault("addresses", "0"));
		boolean hasWhiteInk = "true".equalsIgnoreCase(this.orderItemAttribute.get("whiteInkFront")) || "true".equalsIgnoreCase(this.orderItemAttribute.get("whiteInkBack"));
		boolean hasHeavyInkCoverage = "true".equalsIgnoreCase(this.orderItemAttribute.get("isFullBleed"));
		boolean hasFolding = "true".equalsIgnoreCase(this.orderItemAttribute.get("isFolded"));
		boolean hasCuts = Integer.parseInt(orderItemAttribute.getOrDefault("cuts", "0")) > 0;
		cost = cost.add(getRushFee(partyId, cost));
		if("V_DUPLI".equals(partyId)) {
			if(addresses > 0) {
				cost = cost.add(getUpCharge(variableDataCharge, new BigDecimal(addresses)));
			}

			if(hasWhiteInk) {
				cost = cost.add(getUpCharge(whiteInkCharge, quantity));
			}

			if(hasHeavyInkCoverage) {
				cost = cost.add(getUpCharge(heavyInkCoverageCharge, quantity));
			}

			if(hasFolding) {
				cost = cost.add(getUpCharge(foldingCharge, quantity));
			}

			if(hasCuts) {
				cost = cost.add(getUpCharge(cuttingCharge, quantity));
			}
		}
		data.put("shipping", vendorShippingCost(partyId));
		data.put("cost", cost);

		return data;
	}

	private BigDecimal getUpCharge(Map<Integer, BigDecimal> rates, BigDecimal quantity) {
		List<Integer> volumeQuantities = new ArrayList<>(rates.keySet());
		Collections.sort(volumeQuantities);
		Integer quantityBreak = volumeQuantities.get(0);
		for(int i = 0; i < volumeQuantities.size(); i ++) {
			if(quantity.intValue() >= volumeQuantities.get(i)) {
				quantityBreak = volumeQuantities.get(i);
			} else {
				break;
			}
		}
		return rates.get(quantityBreak).divide(new BigDecimal(quantityBreak), EnvConstantsUtil.ENV_SCALE_L, EnvConstantsUtil.ENV_ROUNDING).multiply(quantity);
	}

	/**
	 * Get All the vendors and their matching products that we can outsource to
	 * @return
	 * @throws GenericEntityException
	 */
	private List<GenericValue> getVendorProducts() throws GenericEntityException {
		return EntityUtil.filterByDate(delegator.findByAnd("VendorProduct", UtilMisc.toMap("productId", this.orderItem.getString("productId")), null, true));
	}

	/**
	 * Get the price from the vendor based on the criteria
	 * @param vendorProductId
	 * @return
	 * @throws GenericEntityException
	 */
	private GenericValue getVendorPrintPrice(String partyId, String vendorProductId) throws GenericEntityException {
		GenericValue price = null;
		if(this.orderItemPriceAttr != null) {
			if(UtilValidate.isNotEmpty(this.pricingMap) && this.pricingMap.containsKey("VENDORS_DETAILED_PRICING")) {
				Map vendorPrice = (Map) ((Map) ((Map) (((Map) this.pricingMap.get("VENDORS_DETAILED_PRICING")).get((String) ((Map) pricingMap.get("REQUEST")).get("VENDOR_ID")))).get("details")).get("Total");
				price = this.delegator.makeValue("VendorProductPrice", UtilMisc.toMap(
						"partyId", (String) pricingMap.get("VENDOR_ID"),
						"vendorProductId", (String) pricingMap.get("VENDOR_SKU"),
						"price", new BigDecimal((String) vendorPrice.get(Integer.valueOf(this.orderItem.getBigDecimal("quantity").setScale(0, BigDecimal.ROUND_HALF_UP).toPlainString()))),
						"quantity", Long.valueOf(this.orderItem.getBigDecimal("quantity").longValue())
				));
			}
		} else {
			//Get the product price that is closest lower value to the quantity ordered and color rules
			List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
			conditionList.add(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId));
			conditionList.add(EntityCondition.makeCondition("vendorProductId", EntityOperator.EQUALS, vendorProductId));
			conditionList.add(EntityCondition.makeCondition("colorsFront", EntityOperator.EQUALS, Long.valueOf(this.colorsFront)));
			conditionList.add(EntityCondition.makeCondition("isFrontBlack", EntityOperator.EQUALS, (this.isFrontBlack && hasBlackInkRule(partyId)) ? "Y" : "N"));
			conditionList.add(EntityCondition.makeCondition("colorsBack", EntityOperator.EQUALS, Long.valueOf(this.colorsBack)));
			conditionList.add(EntityCondition.makeCondition("isBackBlack", EntityOperator.EQUALS, (this.isBackBlack && hasBlackInkRule(partyId)) ? "Y" : "N"));
			conditionList.add(EntityCondition.makeCondition("quantity", EntityOperator.LESS_THAN_EQUAL_TO, Long.valueOf(this.orderItem.getBigDecimal("quantity").longValue())));
			price = EntityUtil.getFirst(EntityUtil.filterByDate(this.delegator.findList("VendorProductPrice", EntityCondition.makeCondition(conditionList, EntityOperator.AND), null, UtilMisc.toList("quantity DESC"), null, false)));

			//if no price is found, reverse the color sides and try again
			if (UtilValidate.isEmpty(price)) {
				conditionList.clear();
				conditionList.add(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId));
				conditionList.add(EntityCondition.makeCondition("vendorProductId", EntityOperator.EQUALS, vendorProductId));
				conditionList.add(EntityCondition.makeCondition("colorsFront", EntityOperator.EQUALS, Long.valueOf(this.colorsBack)));
				conditionList.add(EntityCondition.makeCondition("isFrontBlack", EntityOperator.EQUALS, (this.isBackBlack && hasBlackInkRule(partyId)) ? "Y" : "N"));
				conditionList.add(EntityCondition.makeCondition("colorsBack", EntityOperator.EQUALS, Long.valueOf(this.colorsFront)));
				conditionList.add(EntityCondition.makeCondition("isBackBlack", EntityOperator.EQUALS, (this.isFrontBlack && hasBlackInkRule(partyId)) ? "Y" : "N"));
				conditionList.add(EntityCondition.makeCondition("quantity", EntityOperator.LESS_THAN_EQUAL_TO, Long.valueOf(this.orderItem.getBigDecimal("quantity").longValue())));
				price = EntityUtil.getFirst(EntityUtil.filterByDate(this.delegator.findList("VendorProductPrice", EntityCondition.makeCondition(conditionList, EntityOperator.AND), null, UtilMisc.toList("quantity DESC"), null, false)));
			}

			//if no price is found, get the smallest quantity based on the rules
			if (UtilValidate.isEmpty(price)) {
				conditionList.clear();
				conditionList.add(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId));
				conditionList.add(EntityCondition.makeCondition("vendorProductId", EntityOperator.EQUALS, vendorProductId));
				conditionList.add(EntityCondition.makeCondition("colorsFront", EntityOperator.EQUALS, Long.valueOf(this.colorsFront)));
				conditionList.add(EntityCondition.makeCondition("isFrontBlack", EntityOperator.EQUALS, (this.isFrontBlack && hasBlackInkRule(partyId)) ? "Y" : "N"));
				conditionList.add(EntityCondition.makeCondition("colorsBack", EntityOperator.EQUALS, Long.valueOf(this.colorsBack)));
				conditionList.add(EntityCondition.makeCondition("isBackBlack", EntityOperator.EQUALS, (this.isBackBlack && hasBlackInkRule(partyId)) ? "Y" : "N"));
				price = EntityUtil.getFirst(EntityUtil.filterByDate(this.delegator.findList("VendorProductPrice", EntityCondition.makeCondition(conditionList, EntityOperator.AND), null, UtilMisc.toList("quantity ASC"), null, false)));
			}

			//if no price is found, reverse the sides and get the smallest quantity based on the rules
			if (UtilValidate.isEmpty(price)) {
				conditionList.clear();
				conditionList.add(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId));
				conditionList.add(EntityCondition.makeCondition("vendorProductId", EntityOperator.EQUALS, vendorProductId));
				conditionList.add(EntityCondition.makeCondition("colorsFront", EntityOperator.EQUALS, Long.valueOf(this.colorsBack)));
				conditionList.add(EntityCondition.makeCondition("isFrontBlack", EntityOperator.EQUALS, (this.isBackBlack && hasBlackInkRule(partyId)) ? "Y" : "N"));
				conditionList.add(EntityCondition.makeCondition("colorsBack", EntityOperator.EQUALS, Long.valueOf(this.colorsFront)));
				conditionList.add(EntityCondition.makeCondition("isBackBlack", EntityOperator.EQUALS, (this.isFrontBlack && hasBlackInkRule(partyId)) ? "Y" : "N"));
				price = EntityUtil.getFirst(EntityUtil.filterByDate(this.delegator.findList("VendorProductPrice", EntityCondition.makeCondition(conditionList, EntityOperator.AND), null, UtilMisc.toList("quantity ASC"), null, false)));
			}
		}

		return price;
	}

	/**
	 * Get our selling price for the items closest quantity bracket
	 * @throws GenericEntityException
	 */
	private void setLocalPrice() throws GenericEntityException {
		if(this.orderItemPriceAttr != null) {
			if (UtilValidate.isNotEmpty(this.pricingMap) && this.pricingMap.containsKey("VENDORS_DETAILED_PRICING")) {
				Map vendorCost = (Map) ((Map) ((Map) (((Map) this.pricingMap.get("VENDORS_DETAILED_PRICING")).get((String) ((Map) pricingMap.get("REQUEST")).get("VENDOR_ID")))).get("details")).get("Cost");
				Map vendorPrice = (Map) ((Map) ((Map) (((Map) this.pricingMap.get("VENDORS_DETAILED_PRICING")).get((String) ((Map) pricingMap.get("REQUEST")).get("VENDOR_ID")))).get("details")).get("Total");
				this.productPrice = this.delegator.makeValue("ProductPrice", UtilMisc.toMap(
						"productId", (String) pricingMap.get("VENDOR_SKU"),
						"productPriceTypeId", "DEFAULT_PRICE",
						"productPricePurposeId", "PURCHASE",
						"currencyUomId", "USD",
						"productStoreGroupId", "_NA_",
						"fromDate", UtilDateTime.nowTimestamp(),
						"price", new BigDecimal((String) vendorPrice.get(Integer.valueOf(this.orderItem.getBigDecimal("quantity").setScale(0, BigDecimal.ROUND_HALF_UP).toPlainString()))),
						"printCost", new BigDecimal((String) vendorCost.get(Integer.valueOf(this.orderItem.getBigDecimal("quantity").setScale(0, BigDecimal.ROUND_HALF_UP).toPlainString()))).divide(this.orderItem.getBigDecimal("quantity"), 2, RoundingMode.HALF_UP),
						"quantity", Long.valueOf(this.orderItem.getBigDecimal("quantity").longValue())
				));
			}
		} else {
			List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
			conditionList.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, this.orderItem.getString("productId")));
			conditionList.add(EntityCondition.makeCondition("colors", EntityOperator.EQUALS, Long.valueOf(this.maxColors)));
			conditionList.add(EntityCondition.makeCondition("quantity", EntityOperator.LESS_THAN_EQUAL_TO, Long.valueOf(this.orderItem.getBigDecimal("quantity").longValue())));

			this.productPrice = EntityUtil.getFirst(this.delegator.findList("ProductPrice", EntityCondition.makeCondition(conditionList, EntityOperator.AND), null, UtilMisc.toList("quantity DESC"), null, false));
			if (this.productPrice == null) {
				Debug.logError("No internal pricing found for product : " + this.orderItem.getString("productId"), module);
			}
		}
	}

	/**
	 * Get our cost of the item
	 */
	private void setLocalCost() {
		if(UtilValidate.isNotEmpty(this.productPrice.getBigDecimal("cost"))) {
			this.itemCost = this.productPrice.getBigDecimal("cost").multiply(this.orderItem.getBigDecimal("quantity")).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);
		}
	}

	/**
	 * Get our print cost of the item
	 */
	private void setLocalPrintCost() {
		if(UtilValidate.isNotEmpty(this.productPrice.getBigDecimal("printCost"))) {
			this.itemPrintCost = this.productPrice.getBigDecimal("printCost").multiply(this.orderItem.getBigDecimal("quantity")).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);
		}
	}

	/**
	 * Get our manufacturing cost of the item
	 */
	private void setLocalManufacturingCost() {
		if(UtilValidate.isNotEmpty(this.productPrice.getBigDecimal("manufacturingCost"))) {
			this.itemMfgCost = this.productPrice.getBigDecimal("manufacturingCost").multiply(this.orderItem.getBigDecimal("quantity")).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);
		}
	}

	/**
	 * Get the total weight of the item
	 * @return
	 */
	private BigDecimal getTotalWeight() {
		return this.product.getBigDecimal("productWeight").multiply(this.orderItem.getBigDecimal("quantity")).setScale(EnvConstantsUtil.ENV_SCALE_L, EnvConstantsUtil.ENV_ROUNDING);
	}

	/**
	 * Get the carton weight of the item
	 * @return
	 */
	private BigDecimal getCartonWeight() {
		if(UtilValidate.isNotEmpty(this.product.getLong("cartonQty"))) {
			return this.product.getBigDecimal("productWeight").multiply(BigDecimal.valueOf(this.product.getLong("cartonQty"))).setScale(EnvConstantsUtil.ENV_SCALE_L, EnvConstantsUtil.ENV_ROUNDING);
		} else {
			return this.product.getBigDecimal("productWeight").multiply(new BigDecimal("100")).setScale(EnvConstantsUtil.ENV_SCALE_L, EnvConstantsUtil.ENV_ROUNDING);
		}
	}

	/**
	 * Return the number of cartons to ship
	 * If carton value is not available, default to 100
	 * @return
	 */
	private BigDecimal getTotalCartons() {
		if(UtilValidate.isNotEmpty(this.product.getLong("cartonQty"))) {
			return this.orderItem.getBigDecimal("quantity").divide(BigDecimal.valueOf(this.product.getLong("cartonQty")), EnvConstantsUtil.ENV_SCALE_L, EnvConstantsUtil.ENV_ROUNDING).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);
		} else {
			return this.orderItem.getBigDecimal("quantity").divide(new BigDecimal("100"), EnvConstantsUtil.ENV_SCALE_L, EnvConstantsUtil.ENV_ROUNDING).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);
		}
	}

	private void setShippingCost() throws Exception {
		BigDecimal totalCartons = getTotalCartons();
		BigDecimal totalWeight = getTotalWeight();
		BigDecimal cartonWeight = getCartonWeight();

		BigDecimal[] qR = totalCartons.divideAndRemainder(BigDecimal.ONE);

		//check if we can ship cheaper and faster
		Map<String, Object> shipMethod = NetsuiteHelper.getShipMethod(this.delegator, this.dispatcher, this.orh, this.shippingAddress, this.orh.getShippingTotal(), (List<GenericValue>) orh.getAdjustments());
		if(UtilValidate.isNotEmpty(shipMethod) && UtilValidate.isNotEmpty(shipMethod.get("shipMethodId"))) {
			this.shippingMethod = (String) shipMethod.get("shipMethodId");
		} else {
			this.shippingMethod = "GROUND";
		}

		//if totalCartons > 1 we have to do calculate weight individually per carton
		//else calculate on the total weight
		String zone = postalZone();
		if(UtilValidate.isNotEmpty(zone)) {
			List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
			if(totalCartons.compareTo(BigDecimal.ONE) > 0) {
				conditionList.add(EntityCondition.makeCondition("shipmentMethodTypeId", this.shippingMethod));
				conditionList.add(EntityCondition.makeCondition("zone", EntityOperator.EQUALS, zone));
				conditionList.add(EntityCondition.makeCondition("weight", EntityOperator.LESS_THAN_EQUAL_TO, cartonWeight.setScale(0, RoundingMode.CEILING)));
				GenericValue zoneCost = EntityUtil.getFirst(this.delegator.findList("ZoneCostLookup", EntityCondition.makeCondition(conditionList, EntityOperator.AND), null, UtilMisc.toList("weight DESC"), null, false));

				if(UtilValidate.isNotEmpty(zoneCost)) {
					this.shippingCost = zoneCost.getBigDecimal("averageCost").multiply(qR[0]).setScale(EnvConstantsUtil.ENV_SCALE_L, EnvConstantsUtil.ENV_ROUNDING);
				}

				if(qR[1].compareTo(BigDecimal.ZERO) > 0) {
					BigDecimal partialCartonWeight = qR[1].multiply(cartonWeight).setScale(0, RoundingMode.CEILING);
					conditionList.clear();
					conditionList.add(EntityCondition.makeCondition("shipmentMethodTypeId", this.shippingMethod));
					conditionList.add(EntityCondition.makeCondition("zone", EntityOperator.EQUALS, zone));
					conditionList.add(EntityCondition.makeCondition("weight", EntityOperator.LESS_THAN_EQUAL_TO, partialCartonWeight));
					zoneCost = EntityUtil.getFirst(this.delegator.findList("ZoneCostLookup", EntityCondition.makeCondition(conditionList, EntityOperator.AND), null, UtilMisc.toList("weight DESC"), null, false));

					if(UtilValidate.isNotEmpty(zoneCost)) {
						this.shippingCost = this.shippingCost.add(zoneCost.getBigDecimal("averageCost"));
					}
				}
			} else {
				conditionList.add(EntityCondition.makeCondition("shipmentMethodTypeId", this.shippingMethod));
				conditionList.add(EntityCondition.makeCondition("zone", EntityOperator.EQUALS, zone));
				conditionList.add(EntityCondition.makeCondition("weight", EntityOperator.LESS_THAN_EQUAL_TO, totalWeight.setScale(0, RoundingMode.CEILING)));
				GenericValue zoneCost = EntityUtil.getFirst(this.delegator.findList("ZoneCostLookup", EntityCondition.makeCondition(conditionList, EntityOperator.AND), null, UtilMisc.toList("weight DESC"), null, false));

				if(UtilValidate.isNotEmpty(zoneCost)) {
					this.shippingCost = this.shippingCost.add(zoneCost.getBigDecimal("averageCost"));
				}
			}
		}
	}

	/**
	 * Get the postalZone for the given shipment
	 * @return
	 * @throws GenericEntityException
	 */
	private String postalZone() throws GenericEntityException {
		String postalCode = this.shippingAddress.getString("postalCode");
		GenericValue postalRow = EntityUtil.getFirst(delegator.findByAnd("ZonePostalCodeLookup", UtilMisc.toMap("shipmentMethodTypeId", this.shippingMethod, "postalCode", postalCode), null, false));

		if(UtilValidate.isEmpty(postalRow)) {
			if(postalCode.length() > 3){
				postalCode = postalCode.substring(0, 3);
			}

			List<EntityCondition> conditions = new ArrayList<EntityCondition>();
			conditions.add(EntityCondition.makeCondition("postalCode", EntityOperator.LIKE, postalCode + "%"));
			postalRow = EntityUtil.getFirst(delegator.findList("ZonePostalCodeLookup", EntityCondition.makeCondition(conditions, EntityOperator.AND), null, null, null, true));
		}

		if(UtilValidate.isNotEmpty(postalRow)) {
			return postalRow.getString("zone");
		}

		return null;
	}

	/**
	 * Set the color profile for the order item
	 * @throws GenericEntityException
	 */
	private void setColors() throws GenericEntityException {
		Map<String, Integer> colors = OrderHelper.getColors(this.delegator, null, null, this.orderItem);
		this.colorsFront = colors.get("colorsFront");
		this.colorsBack = colors.get("colorsBack");
		this.maxColors = ProductHelper.getLeastOrMostColors(true, this.colorsFront, this.colorsBack);

		if(this.colorsFront == 1 && UtilValidate.isNotEmpty(this.orderItemArtwork.getString("frontInkColor1")) && "black".equalsIgnoreCase(this.orderItemArtwork.getString("frontInkColor1").trim())) {
			this.isFrontBlack = true;
		}

		if(this.colorsBack == 1 && UtilValidate.isNotEmpty(this.orderItemArtwork.getString("backInkColor1")) && "black".equalsIgnoreCase(this.orderItemArtwork.getString("backInkColor1").trim())) {
			this.isBackBlack = true;
		}
	}

	/**
	 * Certain vendors have rules for blank 1 color only, this will tell us if a vendor has this rule
	 * @param partyId
	 * @return
	 * @throws GenericEntityException
	 */
	private boolean hasBlackInkRule(String partyId) throws GenericEntityException {
		GenericValue vendor = this.delegator.findOne("Vendor", UtilMisc.toMap("partyId", partyId), false);
		if("Y".equalsIgnoreCase(vendor.getString("hasBlackInkRule"))) {
			return true;
		}

		return false;
	}

	/**
	 * Set the net revenue of the item based on the unit price, quantity, shipping fee and the discounts
	 * @throws GenericEntityException
	 */
	private void setItemRevenue() throws GenericEntityException {
		this.netItemRevenue = BigDecimal.ZERO;

		//String numOfItems = Integer.valueOf(this.orh.getOrderItems().size()).toString();
		BigDecimal totalQuantity = totalOrderQuantity(this.orh.getOrderItems());

		this.itemRevenue = this.orderItem.getBigDecimal("unitPrice").multiply(this.orderItem.getBigDecimal("quantity"));
		BigDecimal discounts = BigDecimal.ZERO;
		BigDecimal shippingTotal = BigDecimal.ZERO;

		//Get all the adjustments for shipping and promotions
		List<GenericValue> adjustments = this.orh.getAdjustments();
		for (GenericValue adjustment : adjustments) {
			if (adjustment.getString("orderAdjustmentTypeId").equals("SHIPPING_CHARGES")) {
				shippingTotal = shippingTotal.add(adjustment.getBigDecimal("amount"));
			} else if (adjustment.getString("orderAdjustmentTypeId").equals("PROMOTION_ADJUSTMENT") || adjustment.getString("orderAdjustmentTypeId").equals("DISCOUNT_ADJUSTMENT")) {
				discounts = discounts.add(adjustment.getBigDecimal("amount"));
			}
		}

		//get the weighted value of the shipping and discount for this item only based on the total quantity of the order
		discounts = discounts.divide(totalQuantity, EnvConstantsUtil.ENV_SCALE_L, EnvConstantsUtil.ENV_ROUNDING);
		discounts = discounts.multiply(this.orderItem.getBigDecimal("quantity"));

		shippingTotal = shippingTotal.divide(totalQuantity, EnvConstantsUtil.ENV_SCALE_L, EnvConstantsUtil.ENV_ROUNDING);
		shippingTotal = shippingTotal.multiply(this.orderItem.getBigDecimal("quantity"));

		this.netItemRevenue = this.netItemRevenue.add(this.itemRevenue).add(discounts).add(shippingTotal).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);
	}

	private BigDecimal vendorShippingCost(String partyId) {
		BigDecimal shippingCost = BigDecimal.ZERO;
		switch(partyId) {
			case "V_ARIZONA":
				shippingCost = this.itemRevenue.multiply(new BigDecimal("0.15")).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);
				break;
			default:
				shippingCost = this.shippingCost;
				break;
		}

		return shippingCost;
	}

	private BigDecimal getRushFee(String partyId, BigDecimal cost) {
		BigDecimal rushFee = BigDecimal.ZERO;

		try {
			if (OrderHelper.isRush(delegator, null, null, this.orderItem)) {
				switch (partyId) {
					case "V_DUPLI":
						rushFee = (this.orderItem.getBigDecimal("quantity").compareTo(new BigDecimal("1000")) < 0) ? new BigDecimal("10") : cost.multiply(new BigDecimal("0.10")).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);
						break;
					default:
						rushFee = rushFee;
						break;
				}
			}
		} catch(Exception e) {
			//
		}

		return rushFee;
	}

	private BigDecimal totalOrderQuantity(List<GenericValue> orderItems) {
		BigDecimal totalQty = BigDecimal.ZERO;
		for(GenericValue orderItem : orderItems) {
			totalQty = totalQty.add(orderItem.getBigDecimal("quantity"));
		}

		return totalQty;
	}

	/**
	 * Check order against rules for auto outsourcing a line item
	 * @param delegator
	 * @param dispatcher
	 * @param orh
	 * @param orderId
	 * @param orderItemSeqId
	 * @return
	 * @throws Exception
	 */
	public static void autoOutsource(Delegator delegator, LocalDispatcher dispatcher, OrderReadHelper orh, String orderId, String orderItemSeqId) throws Exception {
		if(orh == null) {
			orh = new OrderReadHelper(delegator, orderId);
		}
		List<GenericValue> orderItems = orh.getOrderItems();
		GenericValue orderItem = orh.getOrderItem(orderItemSeqId);
		GenericValue product = delegator.findOne("ProductOutsource", UtilMisc.toMap("productId", orderItem.getString("productId")), false);

		//disable cimpress auto-outsource
		if("2053030".equalsIgnoreCase(orh.getPlacingParty().getString("partyId"))) {
			return;
		} else if(UtilValidate.isEmpty(product) || (UtilValidate.isNotEmpty(product) && UtilValidate.isEmpty(product.getString("rules")))) {
			return;
		} else if(orderItem.getBigDecimal("quantity").compareTo(new BigDecimal(product.getLong("quantity").toString())) < 0) {
			return;
		}

		Map<String, Object> productRules = CXMLHelper.getProductRules(product);

		//build order item data used for outsourcing rules
		Map<String, Integer> colors = OrderHelper.getColors(delegator, null, null, orderItem);
		int colorsFront = colors.get("colorsFront");
		int colorsBack = colors.get("colorsBack");
		int maxColors = ProductHelper.getLeastOrMostColors(true, colorsFront, colorsBack);

		GenericValue orderItemArtwork = OrderHelper.getOrderItemArtwork(delegator, orderId, orderItemSeqId);
		Set<String> inkColors = new HashSet<>();
		for(int i = 0, j = 0; i < 4 && j < 2; i ++) {
			String key = j == 0 ? "frontInkColor" + (i + 1) : "backInkColor" + (i + 1);
			String inkColor = orderItemArtwork.getString(key);
			if(UtilValidate.isNotEmpty(inkColor)) {
				inkColors.add(inkColor.trim());
				if(i == 3) {
					i = 0;
					j ++;
				}
			} else {
				i = 0;
				j ++;
				break;
			}
		}

		Map<String, Object> orderData = new HashMap<>();
		orderData.put("singleItemOrder", (orderItems.size() == 1) ? "Y" : "N");
		orderData.put("shipping", orh.getShippingMethodDesc("00001"));
		orderData.put("productionTime", OrderHelper.isRush(delegator, null, null, orderItem) ? "Rush" : "Standard");
		orderData.put("inkColors", Integer.valueOf(maxColors).toString());
		orderData.put("state", OrderHelper.getShippingAddress(orh, delegator, orderId).getString("stateProvinceGeoId"));

		//get all the vendor related pricing margins
		OutsourceRule oRule = new OutsourceRule(delegator, dispatcher, orderId, orderItemSeqId);
		Map<String, Object> data = new HashMap<>();
		data.put("netItemRevenue", oRule.getNetItemRevenue());
		data.put("grossProfit", oRule.getVendorAndPrice());

		String allData = new Gson().toJson(data);

		Map<String, Map<String, BigDecimal>> vendorPL = (Map<String, Map<String, BigDecimal>>) data.get("grossProfit");
		Map<String, BigDecimal> envPL = vendorPL.remove("Envelopes.com");
		BigDecimal envelopeCost = (UtilValidate.isNotEmpty(envPL)) ? envPL.get("cost").add(envPL.get("shipping")) : null;

		if(envelopeCost == null && (!productRules.containsKey("stockSupplied") || !"Y".equalsIgnoreCase((String) productRules.get("stockSupplied")))) {
			return;
		}

		/*
		 * Rule 1 a - Check if the vendors returned in the margin list are available to ship to, if not remove
		 * Rule 1 b - Check if the vendor supports all inkColors, else remove the vendor if the they won't support all the ink colors for this item
		 */
		Iterator vendorIt = vendorPL.entrySet().iterator();
		while(vendorIt.hasNext()) {
			Map.Entry pair = (Map.Entry) vendorIt.next();
			GenericValue vendor = delegator.findOne("Vendor", UtilMisc.toMap("partyId", (String) pair.getKey()), false);
			Map<String, Object> vendorRules = CXMLHelper.getVendorRules(vendor);
			if(UtilValidate.isNotEmpty(vendorRules) && UtilValidate.isNotEmpty(vendorRules.get("states")) && !((ArrayList) vendorRules.get("states")).contains(orderData.get("state"))) {
				vendorIt.remove();
			} else if(UtilValidate.isEmpty(vendorRules) || (UtilValidate.isNotEmpty(vendorRules) && UtilValidate.isEmpty(vendorRules.get("states")))) {
				vendorIt.remove();
			} else if(vendorRules.containsKey("inkColors")) {
				List<String> supportedInkColors = (List<String>) vendorRules.get("inkColors");
				if(UtilValidate.isNotEmpty(supportedInkColors)) {
					if(!supportedInkColors.containsAll(inkColors)) {
						vendorIt.remove();
					}
				}
			}
		}

		/*
		 * Rule 2 - Check through product rules if the product is outsourceable via auto method
		 */
		boolean passedRules = true;
		if(UtilValidate.isNotEmpty(vendorPL)) {
			Iterator productIt = productRules.entrySet().iterator();
			while(productIt.hasNext()) {
				Map.Entry pair = (Map.Entry) productIt.next();
				String key = (String) pair.getKey();
				if(orderData.containsKey(key)) {
					if(key.equalsIgnoreCase("shipping") && !((List) pair.getValue()).contains(orderData.get(key))) {
						passedRules = false;
						break;
					} else if(key.equalsIgnoreCase("inkColors") && !((List) pair.getValue()).contains(orderData.get(key))) {
						passedRules = false;
						break;
					} else if(key.equalsIgnoreCase("productionTime") && !((List) pair.getValue()).contains(orderData.get(key))) {
						passedRules = false;
						break;
					} else if(key.equalsIgnoreCase("singleItemOrder") && !((String) orderData.get(key)).equalsIgnoreCase((String) pair.getValue())) {
						passedRules = false;
						break;
					}
				}
				if(key.equalsIgnoreCase("stockSupplied") && "Y".equalsIgnoreCase((String) pair.getValue())) {
					//check if stock supplied product is an option
					vendorIt = vendorPL.entrySet().iterator();
					while(vendorIt.hasNext()) {
						Map.Entry vendorPair = (Map.Entry) vendorIt.next();
						String vendor = (String) vendorPair.getKey();
						GenericValue vendorProduct = CXMLHelper.getVendorProduct(delegator, orderItem.getString("productId"), vendor);
						if(vendorProduct != null && !"Y".equalsIgnoreCase(vendorProduct.getString("stockSupplied"))) {
							vendorIt.remove();
						}
					}
				} else if(key.equalsIgnoreCase("profitMargin")) {
					//check if the profit margin is within range
					BigDecimal profitMargin = new BigDecimal((String) pair.getValue());
					vendorIt = vendorPL.entrySet().iterator();
					while(vendorIt.hasNext()) {
						Map.Entry vendorPair = (Map.Entry) vendorIt.next();
						Map<String, BigDecimal> margins = (Map<String, BigDecimal>) vendorPair.getValue();
						BigDecimal totalCost = margins.get("cost").add(margins.get("shipping"));
						//if the vendor total is more and not within the acceptable difference range, exclude it
						if(envelopeCost != null && totalCost.compareTo(envelopeCost) > 0 && profitMargin.compareTo(totalCost.subtract(envelopeCost)) < 0) {
							vendorIt.remove();
						}
					}
				}
			}

			//if we passed all the rules, look at what vendors are left and then choose the lowest cost vendor
			String vendor = null;
			BigDecimal cost = null;
			BigDecimal totalCost = null;
			if(passedRules && UtilValidate.isNotEmpty(vendorPL)) {
				vendorIt = vendorPL.entrySet().iterator();
				while(vendorIt.hasNext()) {
					Map.Entry vendorPair = (Map.Entry) vendorIt.next();
					Map<String, BigDecimal> margins = (Map<String, BigDecimal>) vendorPair.getValue();
					if(totalCost == null || totalCost.compareTo(margins.get("cost").add(margins.get("shipping"))) > 0) {
						vendor = (String) vendorPair.getKey();
						cost = margins.get("cost");
						totalCost = margins.get("cost").add(margins.get("shipping"));
					}
				}
			}

			//if any vendor is left, send the order to that vendor
			if(vendor != null) {
				String result = CXMLHelper.outsourceOrderRequest(delegator, dispatcher, UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId, "vendorPartyId", vendor, "cost", cost.toPlainString(), "data", allData, "userLogin", EnvUtil.getSystemUser(delegator)));
				String message = "Auto outsource for " + orderId + " - " + orderItemSeqId + " was " + ((UtilValidate.isEmpty(result)) ? "successful" : "failed. " + result);
				//dispatcher.runSync("sendEmail", UtilMisc.toMap("email", "shoab@envelopes.com", "rawData", null, "data", UtilMisc.<String, String>toMap("subject", "Vendor Outsource", "request", message), "messageType", "genericEmail", "webSiteId", "envelopes"));
				/*if(UtilValidate.isEmpty(result)) {
					dispatcher.runSync("changeOrderItemStatus", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId, "statusId", "ART_OUTSOURCED", "userLogin", EnvUtil.getSystemUser(delegator)));
				}*/
			}
		}
	}
}