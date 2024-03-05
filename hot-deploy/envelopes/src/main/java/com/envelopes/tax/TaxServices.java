/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.tax;

import java.math.BigDecimal;
import java.util.*;

import com.bigname.tax.AvalaraTax;
import com.envelopes.order.OrderHelper;
import com.envelopes.party.PartyHelper;
import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.order.order.OrderReadHelper;
import org.apache.ofbiz.security.Security;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import org.apache.ofbiz.order.order.OrderServices;

public class TaxServices {
	public static final String module = TaxServices.class.getName();

	/*
	 * Calculate the tax for a given list of products
	 */
	public static Map calcTax(DispatchContext dctx, Map<String, ? extends Object> context) {
		LocalDispatcher dispatcher = dctx.getDispatcher();
		Delegator delegator = dctx.getDelegator();
		BigDecimal totalTax = BigDecimal.ZERO;

		// Setup the return lists.
		List<GenericValue> orderAdjustments = new ArrayList<>();
		List<List<GenericValue>> itemAdjustments = new ArrayList<>();

		// Passed in data
		List<GenericValue> itemProductList = UtilGenerics.checkList(context.get("itemProductList"));
		List<BigDecimal> itemAmountList = UtilGenerics.checkList(context.get("itemAmountList"));
		List<BigDecimal> itemPriceList = UtilGenerics.checkList(context.get("itemPriceList"));
		List<BigDecimal> itemQuantityList = UtilGenerics.checkList(context.get("itemQuantityList"));
		List<BigDecimal> itemShippingList = UtilGenerics.checkList(context.get("itemShippingList"));
		BigDecimal orderShippingAmount = (BigDecimal) context.get("orderShippingAmount");
		BigDecimal orderPromotionsAmount = (BigDecimal) context.get("orderPromotionsAmount");
		GenericValue shippingAddress = (GenericValue) context.get("shippingAddress");
		GenericValue shipmentMethodType = (GenericValue) context.get("shipmentMethodType");
		String externalPartyId = (String) context.get("externalPartyId");

		if (UtilValidate.isEmpty(shippingAddress)) {
			return ServiceUtil.returnError("No shipping address available to calculate tax.");
		}

		if(TaxHelper.isAvalaraEnabled && UtilValidate.isNotEmpty(shippingAddress.getString("postalCode")) && UtilValidate.isNotEmpty(shippingAddress.getString("stateProvinceGeoId"))) {
			try {
				AvalaraTax avaTax = new AvalaraTax(dispatcher, delegator);

				//build item list
				List<Map<String, Object>> itemList = new ArrayList<>();
				for (int i = 0; i < itemProductList.size(); i++) {
					Map<String, Object> itemData = new HashMap<>();
					itemData.put("productId", itemProductList.get(i).getString("productId"));
					itemData.put("description", itemProductList.get(i).getString("productName"));
					itemData.put("taxCode", itemProductList.get(i).getString("taxCode"));
					itemData.put("price", itemAmountList.get(i));
					itemData.put("unitPrice", itemPriceList.get(i));
					itemData.put("quantity", itemQuantityList.get(i));
					itemData.put("shipping", itemShippingList.get(i));
					itemData.put("isProduct", true);
					itemData.put("isShipping", false);
					itemList.add(itemData);
				}

				if(UtilValidate.isNotEmpty(shipmentMethodType)) {
					Map<String, Object> shipData = new HashMap<>();
					shipData.put("productId", shipmentMethodType.getString("shipmentMethodTypeId"));
					shipData.put("description", shipmentMethodType.getString("description"));
					shipData.put("taxCode", shipmentMethodType.getString("taxCode"));
					shipData.put("price", orderShippingAmount);
					shipData.put("unitPrice", orderShippingAmount);
					shipData.put("quantity", BigDecimal.ONE);
					shipData.put("shipping", BigDecimal.ZERO);
					shipData.put("isProduct", false);
					shipData.put("isShipping", true);
					itemList.add(shipData);
				}

				avaTax.setCustomer(externalPartyId);
				avaTax.setItems(itemList);
				avaTax.setAddress(shippingAddress.getAllFields());
				avaTax.setDiscount(orderPromotionsAmount.abs());
				avaTax.sendTaxTransaction();

				totalTax = avaTax.getTotalTax();
			} catch (Exception e) {
				Debug.logError(e, "Error trying to get tax rate from Avalara.", module);
			}
		} else {
			// Get the tax rate
			BigDecimal taxRate = BigDecimal.ZERO;
			if(UtilValidate.isNotEmpty(shippingAddress.getString("postalCode"))) {
				try {
					taxRate = TaxHelper.getTaxRate(delegator, shippingAddress.getString("postalCode"));
				} catch(GenericEntityException e) {
					return ServiceUtil.returnError("No tax rate available to calculate tax.");
				}
			}

			// Get the item amount total
			BigDecimal taxableTotal = BigDecimal.ZERO;
			for (int i = 0; i < itemProductList.size(); i++) {
				taxableTotal = taxableTotal.add(itemAmountList.get(i));
			}

			// Get the shipping total
			if(UtilValidate.isNotEmpty(shippingAddress.get("stateProvinceGeoId")) && TaxHelper.STATE_WITH_TAXABLE_SHIPPING.contains(shippingAddress.getString("stateProvinceGeoId"))) {
				taxableTotal = (orderShippingAmount != null && orderShippingAmount.compareTo(BigDecimal.ZERO) > 0) ? taxableTotal.add(orderShippingAmount) : taxableTotal;
			}

			// Get the promotion total
			if (orderPromotionsAmount != null && orderPromotionsAmount.compareTo(BigDecimal.ZERO) != 0) {
				taxableTotal = taxableTotal.add(orderPromotionsAmount);
			}

			totalTax = TaxHelper.getTaxTotal(taxRate, taxableTotal);
		}

		if(totalTax.compareTo(BigDecimal.ZERO) > 0) {
			GenericValue taxAdjustment = delegator.makeValue("OrderAdjustment");
			taxAdjustment.set("orderAdjustmentTypeId", "SALES_TAX");
			taxAdjustment.set("amount", totalTax);
			orderAdjustments.add(taxAdjustment);
		}

		Map<String, Object> result = ServiceUtil.returnSuccess();
		result.put("orderAdjustments", orderAdjustments);
		result.put("itemAdjustments", itemAdjustments);

		return result;
	}

	/** Service for checking and re-calc the tax amount */
	public static Map<String, Object> recalcOrderTax(DispatchContext ctx, Map<String, ? extends Object> context) {
		LocalDispatcher dispatcher = ctx.getDispatcher();
		Delegator delegator = ctx.getDelegator();
		String orderId = (String) context.get("orderId");
		String orderItemSeqId = (String) context.get("orderItemSeqId");
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Locale locale = (Locale) context.get("locale");

		// check and make sure we have permission to change the order
		Security security = ctx.getSecurity();
		boolean hasPermission = OrderServices.hasPermission(orderId, userLogin, "UPDATE", security, delegator);
		if (!hasPermission) {
			return ServiceUtil.returnError(UtilProperties.getMessage("OrderUiLabels", "OrderYouDoNotHavePermissionToChangeThisOrdersStatus",locale));
		}

		// get the order header
		GenericValue orderHeader = null;
		try {
			orderHeader = EntityQuery.use(delegator).from("OrderHeader").where("orderId", orderId).queryOne();
		} catch (GenericEntityException e) {
			return ServiceUtil.returnError(UtilProperties.getMessage("OrderUiLabels", "OrderErrorCannotGetOrderHeaderEntity",locale) + e.getMessage());
		}

		if (orderHeader == null) {
			return ServiceUtil.returnError(UtilProperties.getMessage("OrderUiLabels", "OrderErrorNoValidOrderHeaderFoundForOrderId", UtilMisc.toMap("orderId",orderId), locale));
		}

		// Retrieve the order tax adjustments
		List<GenericValue> orderTaxAdjustments = null;
		try {
			orderTaxAdjustments = EntityQuery.use(delegator).from("OrderAdjustment").where("orderId", orderId, "orderAdjustmentTypeId", "SALES_TAX").queryList();
		} catch (GenericEntityException e) {
			Debug.logError(e, "Unable to retrieve SALES_TAX adjustments for order : " + orderId, module);
			return ServiceUtil.returnError(UtilProperties.getMessage("OrderUiLabels", "OrderUnableToRetrieveSalesTaxAdjustments",locale));
		}

		// Accumulate the total existing tax adjustment
		BigDecimal totalExistingOrderTax = BigDecimal.ZERO;
		for (GenericValue orderTaxAdjustment : orderTaxAdjustments) {
			if (orderTaxAdjustment.get("amount") != null) {
				totalExistingOrderTax = totalExistingOrderTax.add(orderTaxAdjustment.getBigDecimal("amount").setScale(OrderServices.taxDecimals, OrderServices.taxRounding));
			}
		}

		// Recalculate the taxes for the order
		BigDecimal totalNewOrderTax = BigDecimal.ZERO;
		OrderReadHelper orh = new OrderReadHelper(orderHeader);
		List<GenericValue> shipGroups = orh.getOrderItemShipGroups();
		if (shipGroups != null) {
			for (GenericValue shipGroup : shipGroups) {
				String shipGroupSeqId = shipGroup.getString("shipGroupSeqId");

				List<GenericValue> validOrderItems = orh.getValidOrderItems(shipGroupSeqId);
				if (validOrderItems != null) {
					// prepare the inital lists
					List<GenericValue> products = new ArrayList<GenericValue>(validOrderItems.size());
					List<BigDecimal> amounts = new ArrayList<BigDecimal>(validOrderItems.size());
					List<BigDecimal> shipAmts = new ArrayList<BigDecimal>(validOrderItems.size());
					List<BigDecimal> itPrices = new ArrayList<BigDecimal>(validOrderItems.size());
					List<BigDecimal> itQuantities = new ArrayList<BigDecimal>(validOrderItems.size());

					// adjustments and total
					List<GenericValue> allAdjustments = orh.getAdjustments();
					List<GenericValue> orderHeaderAdjustments = OrderReadHelper.getOrderHeaderAdjustments(allAdjustments, shipGroupSeqId);
					BigDecimal orderSubTotal = OrderReadHelper.getOrderItemsSubTotal(validOrderItems, allAdjustments);

					// shipping amount
					BigDecimal orderShipping = OrderReadHelper.calcOrderAdjustments(orderHeaderAdjustments, orderSubTotal, false, false, true);
					BigDecimal orderShipPromoAmt = TaxHelper.calcOrderPromoAdjustments(allAdjustments, "PROMOTION_ADJUSTMENT", "PROMO_SHIP_CHARGE", true);
					orderShipping = orderShipping.add(orderShipPromoAmt);

					//promotions amount
					BigDecimal orderPromotions = TaxHelper.calcOrderPromoAdjustments(allAdjustments, "PROMOTION_ADJUSTMENT", null, false); //customer applied
					orderPromotions = orderPromotions.add(TaxHelper.calcOrderPromoAdjustments(allAdjustments, "DISCOUNT_ADJUSTMENT", null, false)); //manual applied
					orderPromotions = orderPromotions.add(TaxHelper.calcOrderPromoAdjustments(allAdjustments, "FEE", null, false)); //manual applied

					// build up the list of tax calc service parameters
					for (int i = 0; i < validOrderItems.size(); i++) {
						GenericValue orderItem = validOrderItems.get(i);
						String productId = orderItem.getString("productId");
						try {
							products.add(i, EntityQuery.use(delegator).from("Product").where("productId", productId).queryOne());  // get the product entity
							amounts.add(i, OrderReadHelper.getOrderItemSubTotal(orderItem, allAdjustments, true, false)); // get the item amount
							shipAmts.add(i, OrderReadHelper.getOrderItemAdjustmentsTotal(orderItem, allAdjustments, false, false, true)); // get the shipping amount
							itPrices.add(i, orderItem.getBigDecimal("unitPrice"));
							itQuantities.add(i, orderItem.getBigDecimal("quantity"));
						} catch (GenericEntityException e) {
							Debug.logError(e, "Cannot read order item entity : " + orderItem, module);
							return ServiceUtil.returnError(UtilProperties.getMessage("OrderUiLabels", "OrderCannotReadTheOrderItemEntity",locale));
						}
					}

					GenericValue shippingAddress = null;
					try {
						shippingAddress = OrderHelper.getShippingAddress(orh, delegator, orderId);
					} catch (GenericEntityException shipE) {
						Debug.logError(shipE, "Cannot get shipping address for Order : " + orderId, module);
					}

					// if shippingAddress is still null then don't calculate tax; it may be an situation where no tax is applicable, or the data is bad and we don't have a way to find an address to check tax for
					if (shippingAddress == null) {
						Debug.logWarning("Not calculating tax for Order [" + orderId + "] because there is no shippingAddress, and no address on the origin facility [" +  orderHeader.getString("originFacilityId") + "]", module);
						continue;
					}

					// invoke the calcTax service
					Map<String, Object> serviceResult = null;
					try {
						// prepare the service context
						Map<String, Object> serviceContext = UtilMisc.<String, Object>toMap("productStoreId", orh.getProductStoreId(), "itemProductList", products, "itemAmountList", amounts, "itemShippingList", shipAmts, "itemPriceList", itPrices, "itemQuantityList", itQuantities, "orderShippingAmount", orderShipping);
						serviceContext.put("shippingAddress", shippingAddress);
						serviceContext.put("orderPromotionsAmount", orderPromotions);
						serviceContext.put("shipmentMethodType", orh.getShipmentMethodType("00001"));
						serviceContext.put("externalPartyId", PartyHelper.getParty(delegator, orh.getBillToParty().getString("partyId")).getString("externalId"));
						if (orh.getBillToParty() != null) serviceContext.put("billToPartyId", orh.getBillToParty().getString("partyId"));
						if (orh.getBillFromParty() != null) serviceContext.put("payToPartyId", orh.getBillFromParty().getString("partyId"));

						serviceResult = dispatcher.runSync("calcTax", serviceContext);
					} catch (Exception e) {
						Debug.logError(e, module);
						return ServiceUtil.returnError(UtilProperties.getMessage("OrderUiLabels", "OrderProblemOccurredInTaxService",locale));
					}

					if (ServiceUtil.isError(serviceResult)) {
						return ServiceUtil.returnError(ServiceUtil.getErrorMessage(serviceResult));
					}

					// the adjustments (returned in order) from the tax service
					List<GenericValue> orderAdj = UtilGenerics.checkList(serviceResult.get("orderAdjustments"));
					List<List<GenericValue>> itemAdj = UtilGenerics.checkList(serviceResult.get("itemAdjustments"));

					// Accumulate the new tax total from the recalculated header adjustments
					if (UtilValidate.isNotEmpty(orderAdj)) {
						for (GenericValue oa : orderAdj) {
							if (oa.get("amount") != null) {
								totalNewOrderTax = totalNewOrderTax.add(oa.getBigDecimal("amount").setScale(OrderServices.taxDecimals, OrderReadHelper.taxRounding));
							}
						}
					}

					// Accumulate the new tax total from the recalculated item adjustments
					if (UtilValidate.isNotEmpty(itemAdj)) {
						for (int i = 0; i < itemAdj.size(); i++) {
							List<GenericValue> itemAdjustments = itemAdj.get(i);
							for (GenericValue ia : itemAdjustments) {
								if (ia.get("amount") != null) {
									totalNewOrderTax = totalNewOrderTax.add(ia.getBigDecimal("amount").setScale(OrderServices.taxDecimals, OrderReadHelper.taxRounding));
								}
							}
						}
					}
				}
			}

			// If the total has changed, create an OrderAdjustment to reflect the fact
			if (totalNewOrderTax.compareTo(totalExistingOrderTax) != 0) {
				Map<String, Object> createOrderAdjContext = new HashMap<>();
				createOrderAdjContext.put("orderAdjustmentTypeId", "SALES_TAX");
				createOrderAdjContext.put("orderId", orderId);
				createOrderAdjContext.put("shipGroupSeqId", "_NA_");
				createOrderAdjContext.put("description", "Tax adjustment due to order change");
				createOrderAdjContext.put("amount", totalNewOrderTax);
				createOrderAdjContext.put("userLogin", userLogin);
				createOrderAdjContext.put("orderItemSeqId", "_NA_");

				Map<String, Object> createOrderAdjResponse = null;
				try {
					//remove old adjustments
					delegator.removeAll(orderTaxAdjustments);
					createOrderAdjResponse = dispatcher.runSync("createOrderAdjustment", createOrderAdjContext);
				} catch (Exception e) {
					String createOrderAdjErrMsg = UtilProperties.getMessage("OrderUiLabels", "OrderErrorCallingCreateOrderAdjustmentService", locale);
					Debug.logError(createOrderAdjErrMsg, module);
					return ServiceUtil.returnError(createOrderAdjErrMsg);
				}
				if (ServiceUtil.isError(createOrderAdjResponse)) {
					Debug.logError(ServiceUtil.getErrorMessage(createOrderAdjResponse), module);
					return ServiceUtil.returnError(ServiceUtil.getErrorMessage(createOrderAdjResponse));
				}
			}
		}

		return ServiceUtil.returnSuccess();
	}
}