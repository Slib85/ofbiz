/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import com.bigname.integration.listrak.ListrakHelper;
import com.envelopes.cxml.OutsourceRule;
import com.envelopes.plating.PlateHelper;
import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.order.order.OrderReadHelper;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import com.envelopes.product.ProductHelper;
import com.envelopes.util.*;

public class OrderServices {
	public static final String module = OrderServices.class.getName();
	public static final String autoApproveAfterDays = UtilProperties.getPropertyValue("envelopes", "auto.approve");

	public static Map<String, Object> retryDeclinedOrders(DispatchContext dctx, Map<String, ? extends Object> context) {
		LocalDispatcher dispatcher = dctx.getDispatcher();
		Delegator delegator = dctx.getDelegator();

		Map<String, Object> result = ServiceUtil.returnSuccess();

		try {
			//RULES
			//PREVIOUS DAYS ORDERS ONLY
			//ORDER STATUS: ORDER_PENDING
			//PAYMENT METHOD: CREDIT_CARD
			//PAYMENT STATUS: DECLINED
			//EXPORTED: NOT EXPORTED TO NETSUITE
			List<EntityCondition> conditionList = new ArrayList<>();
			conditionList.add(EntityCondition.makeCondition("createdStamp", EntityOperator.GREATER_THAN_EQUAL_TO, EnvUtil.getNDaysBeforeOrAfterDate(UtilDateTime.nowTimestamp(), -1, true)));
			conditionList.add(EntityCondition.makeCondition("createdStamp", EntityOperator.LESS_THAN, EnvUtil.getNDaysBeforeOrAfterDate(UtilDateTime.nowTimestamp(), 0, true)));
			conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "ORDER_PENDING"));
			conditionList.add(EntityCondition.makeCondition("paymentMethodTypeId", EntityOperator.EQUALS, "CREDIT_CARD"));
			conditionList.add(EntityCondition.makeCondition("paymentStatusId", EntityOperator.EQUALS, "PAYMENT_DECLINED"));

			List<GenericValue> declinedOrders = EntityQuery.use(delegator).from("OrderAndPaymentPreference").where(EntityCondition.makeCondition(conditionList, EntityOperator.AND)).queryList();
			for(GenericValue order : declinedOrders) {
				if(UtilValidate.isEmpty(order.getTimestamp("exportedDate"))) {
					try {
						// get the last failed auth attempt
						GenericValue paymentPref = EntityQuery.use(delegator).from("OrderPaymentPreference").where("orderId", order.getString("orderId"), "statusId", "PAYMENT_DECLINED").orderBy("createdStamp DESC").queryFirst();
						if(paymentPref != null) {
							Map<String, Object> authResult = dispatcher.runSync("authOrderPaymentPreference", UtilMisc.toMap("orderPaymentPreferenceId", paymentPref.getString("orderPaymentPreferenceId"), "userLogin", EnvUtil.getSystemUser(delegator)));
							if(authResult != null && !authResult.isEmpty() && (Boolean) authResult.get("finished")) {
								Calendar cal = Calendar.getInstance();
								cal.add(Calendar.MINUTE, 2);
								long jobStartTime = cal.getTimeInMillis();

								Debug.logInfo("Successfully re-authorized " + order.getString("orderId") + ".", module);
								dispatcher.runSync("changeOrderStatus", UtilMisc.toMap("orderId", order.getString("orderId"), "statusId", "ORDER_CREATED", "userLogin", EnvUtil.getSystemUser(delegator)));

								Map<String,Object> orderData = OrderHelper.getOrderData(delegator, order.getString("orderId"), false);
								Map<String, String> emailData = ListrakHelper.createOrderPendingEmailData(delegator, orderData, order.getString("orderId"));

								dispatcher.runAsync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "PaymentAuthorized", "data", emailData, "email", orderData.get("email"), "webSiteId", EnvUtil.getWebsiteId(order.getString("salesChannelEnumId"))));
								dispatcher.schedule("exportOrder", UtilMisc.toMap("orderIds", UtilMisc.toList(order.getString("orderId")), "ignoreValidity", Boolean.FALSE, "userLogin", EnvUtil.getSystemUser(delegator)), jobStartTime);
							} else {
								Debug.logInfo("Unsuccessfully tried to re-authorized " + order.getString("orderId") + ".", module);
							}
						}
					} catch (GenericServiceException gse) {
						EnvUtil.reportError(gse);
					}
				}
			}
		} catch(GenericEntityException gee) {
			EnvUtil.reportError(gee);
			return ServiceUtil.returnError(gee.getMessage());
		}

		return result;
	}

	/**
	 * Export order xml data for switch
	 * @param dctx
	 * @param context
	 * @return
	 */
	public static Map<String, Object> exportOrderXML(DispatchContext dctx, Map<String, ? extends Object> context) {
		LocalDispatcher dispatcher = dctx.getDispatcher();
		Delegator delegator = dctx.getDelegator();

		Map<String, Object> result = ServiceUtil.returnSuccess();

		try {
			OrderHelper.exportOrderForSwitch(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"), (Integer) context.get("sequence"));
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to export order file. " + e + " : " + e.getMessage(), module);
			return ServiceUtil.returnError(e.getMessage());
		} catch (Exception e) {
			EnvUtil.reportError(e);
			return ServiceUtil.returnError(e.getMessage());
		}

		return result;
	}

	/**
	 * Update due date for an order
	 */
	public static Map<String, Object> updateDueDate(DispatchContext dctx, Map<String, ? extends Object> context) {
		LocalDispatcher dispatcher = dctx.getDispatcher();
		Delegator delegator = dctx.getDelegator();

		Map<String, Object> result = ServiceUtil.returnSuccess();

		Timestamp dueDate = UtilDateTime.nowTimestamp();
		try {
			//an order due date is based on the due date of the last item updated, unless dueDate value is given
			if((context.containsKey("dueDate") && UtilValidate.isEmpty(context.get("dueDate"))) || "ART_READY_FOR_REVIEW".equalsIgnoreCase((String) context.get("statusId"))) {
				OrderHelper.updateOrderItem(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"), UtilMisc.<String, Object>toMap("dueDate", null));
			} else if(UtilValidate.isNotEmpty(context.get("dueDate"))) {
				dueDate = Timestamp.valueOf((String) context.get("dueDate") + " 00:00:00.0");
				OrderHelper.updateOrderItem(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"), UtilMisc.<String, Object>toMap("dueDate", dueDate));
			} else {
				List<GenericValue> orderItems = OrderHelper.getOrderItems(null, delegator, (String) context.get("orderId"));

				if(UtilValidate.isNotEmpty(context.get("orderItemSeqId"))) {
					GenericValue orderItem = delegator.findOne("OrderItem", UtilMisc.toMap("orderId", (String) context.get("orderId"), "orderItemSeqId", (String) context.get("orderItemSeqId")), false);
					boolean isReorder = UtilValidate.isNotEmpty(orderItem.getString("referenceOrderId"));
					boolean hasChanges = OrderHelper.reOrderWithChanges(delegator, orderItem);
					dueDate = OrderHelper.getDueDate(ProductHelper.getLeadTime(delegator, (String) orderItem.get("productId")), OrderHelper.isRushOrder(orderItems), OrderHelper.isItemScene7(null, null, null, orderItem), true, (isReorder && !hasChanges) ? EntityUtil.getFirst(delegator.findByAnd("OrderStatus", UtilMisc.toMap("orderId", orderItem.getString("orderId"), "orderItemSeqId", orderItem.getString("orderItemSeqId"), "statusId", "ITEM_CREATED"), UtilMisc.toList("statusDatetime DESC"), false)).getTimestamp("createdStamp") : null);
				}

				//update all other printed items with the same due date
				for(GenericValue orderItem : orderItems) {
					if(OrderHelper.isItemPrinted(delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId"), orderItem)) {
						OrderHelper.updateOrderItem(delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId"), UtilMisc.<String, Object>toMap("dueDate", dueDate));
					}
				}
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to export order file. " + e + " : " + e.getMessage(), module);
			return ServiceUtil.returnError(e.getMessage());
		} catch (Exception e) {
			EnvUtil.reportError(e);
			return ServiceUtil.returnError(e.getMessage());
		}

		return result;
	}

	/**
	 * Replace credit card date with dummy date after order is processed
	 */
	public static Map<String, Object> resetCreditCard(DispatchContext dctx, Map<String, ? extends Object> context) {
		LocalDispatcher dispatcher = dctx.getDispatcher();
		Delegator delegator = dctx.getDelegator();

		Map<String, Object> result = ServiceUtil.returnSuccess();

		try {
			if(UtilValidate.isNotEmpty(context.get("orderId"))) {
				OrderReadHelper orh = new OrderReadHelper(delegator, (String) context.get("orderId"));
				List<GenericValue> paymentPreferences = orh.getPaymentPreferences();
				if(UtilValidate.isNotEmpty(paymentPreferences)) {
					for (GenericValue paymentPreference : paymentPreferences) {
						if(paymentPreference.getString("paymentMethodTypeId").equalsIgnoreCase("CREDIT_CARD")) {
							GenericValue creditCard = paymentPreference.getRelatedOne("CreditCard", false);
							creditCard.set("cardNumber", "4111111111111111");
							creditCard.store();
						}
					}
				}
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to export order file. " + e + " : " + e.getMessage(), module);
			return ServiceUtil.returnError(e.getMessage());
		} catch (Exception e) {
			EnvUtil.reportError(e);
			return ServiceUtil.returnError(e.getMessage());
		}

		return result;
	}

	/**
	 * If can auto outsource, send it out and update the order item to outsourced and outsourceable
	 */
	public static Map<String, Object> autoOutsource(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		LocalDispatcher dispatcher = dctx.getDispatcher();

		Map<String, Object> result = ServiceUtil.returnSuccess();

		try {
			if(UtilValidate.isNotEmpty(context.get("orderId")) && UtilValidate.isNotEmpty(context.get("orderItemSeqId"))) {
				//only autoOutsource if we can outsource and its not set for a netsuite change update
				if(OrderHelper.canOutsource(null, delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId")) && !OrderHelper.isPendingChange(null, delegator, (String) context.get("orderId"))) {
					OutsourceRule.autoOutsource(delegator, dispatcher, null, (String) context.get("orderId"), (String) context.get("orderItemSeqId"));
					OrderHelper.setOutsourceable(delegator, null, null, (String) context.get("orderId"), (String) context.get("orderItemSeqId"), "Y");
				}
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to set outsource. " + e + " : " + e.getMessage(), module);
			return ServiceUtil.returnError(e.getMessage());
		} catch (Exception e) {
			EnvUtil.reportError(e);
			return ServiceUtil.returnError(e.getMessage());
		}

		return result;
	}

	public static Map<String, Object> autoApprove(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		LocalDispatcher dispatcher = dctx.getDispatcher();

		Map<String, Object> result = ServiceUtil.returnSuccess();

		//get all orders in the ART_READY_FOR_REVIEW
		try {
			List<GenericValue> orderItems = OrderHelper.getAllProofRreadyOrderItems(delegator, true);
			for(GenericValue orderItem : orderItems) {
				//check if this order has been in queue for more than 5 days waiting for approvals
				GenericValue orderStatus = EntityUtil.getFirst(delegator.findByAnd("OrderStatus", UtilMisc.toMap("orderId", orderItem.getString("orderId"), "orderItemSeqId", orderItem.getString("orderItemSeqId"), "statusId", orderItem.getString("itemStatusId")), UtilMisc.toList("statusDatetime DESC"), false));
				long days = 0;
				try {
					days = EnvUtil.getDaysBetweenDates(orderStatus.getTimestamp("statusDatetime"), UtilDateTime.nowTimestamp(), true, true);
					if(days > Long.valueOf(autoApproveAfterDays)) {
						// Change status in staggared times
						Random random = new Random();
						int Low = 1;
						int High = 10;
						int randomInt = random.nextInt(High-Low) + Low;

						Calendar cal = Calendar.getInstance();
						cal.add(Calendar.MINUTE, randomInt);
						long startTime = cal.getTimeInMillis();

						dispatcher.schedule("changeOrderItemStatus", UtilMisc.toMap("orderId", orderItem.getString("orderId"), "orderItemSeqId", orderItem.getString("orderItemSeqId"), "statusId", "ART_PROOF_APPROVED", "userLogin", EnvUtil.getSystemUser(delegator)), startTime); //send sample coupon
					}
				} catch(Exception e) {
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to get approval data. " + e + " : " + e.getMessage(), module);
				}
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to get approval data. " + e + " : " + e.getMessage(), module);
			return ServiceUtil.returnError(e.getMessage());
		} catch (Exception e) {
			EnvUtil.reportError(e);
			return ServiceUtil.returnError(e.getMessage());
		}

		return result;
	}

	/**
	 * Update the order id with the correct prefix
	 * @param dctx
	 * @param context
	 * @return
	 */
	public static Map<String, Object> setOrderIdPrefix(DispatchContext dctx, Map<String, ? extends Object> context) {
		LocalDispatcher dispatcher = dctx.getDispatcher();
		Delegator delegator = dctx.getDelegator();

		Map<String, Object> result = ServiceUtil.returnSuccess();

		String orderId = EnvUtil.getOrderIdPrefix((String) context.get("orderId"), (String) context.get("webSiteId"));
		result.put("orderId", orderId);

		return result;
	}

	/**
	 * Check if all items on an order is shipped and set order level status accordingly
	 * @param dctx
	 * @param context
	 * @return
	 */
	public static Map<String, Object> changeOrderShippedStatus(DispatchContext dctx, Map<String, ? extends Object> context) {
		LocalDispatcher dispatcher = dctx.getDispatcher();
		Delegator delegator = dctx.getDelegator();

		Map<String, Object> result = ServiceUtil.returnSuccess();

		try {
			GenericValue orderHeader = OrderHelper.getOrderHeader(delegator, (String) context.get("orderId"));

			if(!"ORDER_SHIPPED".equalsIgnoreCase(orderHeader.getString("statusId"))) {
				List<GenericValue> orderItems = OrderHelper.getOrderItems(null, delegator, (String) context.get("orderId"));

				int shippedItems = 0;
				for (GenericValue orderItem : orderItems) {
					if ("ITEM_SHIPPED".equalsIgnoreCase(orderItem.getString("statusId"))) {
						shippedItems++;
					}
				}

				if(shippedItems == orderItems.size()) {
					dispatcher.runSync("changeOrderStatus", UtilMisc.toMap("orderId", orderHeader.getString("orderId"), "statusId", "ORDER_SHIPPED", "userLogin", context.get("userLogin")));

					OrderReadHelper orh = new OrderReadHelper(delegator, orderHeader.getString("orderId"));

					//if international order, TODO
					if(OrderHelper.isInternationalOrder(orh, delegator, null)) {
						//
					}

					//if amazon, we need to close the "payment"
					if(OrderHelper.isAmazonOrder(orh, delegator, null)) {
						dispatcher.runAsync("amazonCCClose", UtilMisc.toMap("orderHeader", orh.getOrderHeader(), "amazonOrderId", orh.getOrderHeader().getString("externalId"), "reason", "Order Fulfilled."));
					}

					//send coupon email if applicable
					Map getAEPartyRoles = dispatcher.runSync("getAEPartyRoles", UtilMisc.toMap("partyId", orh.getPlacingParty().getString("partyId")));
					boolean isTrade = (Boolean) getAEPartyRoles.get("isTrade");
					if(!isTrade) {
						Map<String, Object> couponData = OrderHelper.orderSampleData(delegator, delegator.findByAnd("OrderItem", UtilMisc.toMap("orderId", orderHeader.getString("orderId")), null, false), false);
						if(UtilValidate.isNotEmpty(couponData.get("sampleTotal")) && ((BigDecimal) couponData.get("sampleTotal")).compareTo(BigDecimal.ZERO) > 0) {
							dispatcher.runAsync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "SampleCoupon", "data", ListrakHelper.createSampleEmailData(delegator, null, orderHeader.getString("orderId")), "email", orh.getOrderEmailString(), "webSiteId", EnvUtil.getWebsiteId(orderHeader.getString("salesChannelEnumId"))));
						}
					}

					if (!OrderHelper.isOrderOnlySample(orderItems) && !OrderHelper.isOrderOnlyCustomProducts(orderItems)) {
						//send order review email 2 weeks (14 days)
						Calendar cal = Calendar.getInstance();
						cal.add(Calendar.DATE, 14);
						//dispatcher.schedule("sendEmail", UtilMisc.toMap("email", orh.getOrderEmailString(), "rawData", null, "data", EmailHelper.createReviewEmailData(delegator, orderHeader.getString("orderId")), "messageType", "yourOrderReview", "webSiteId", EnvUtil.getWebsiteId(orh.getOrderHeader().getString("salesChannelEnumId"))), cal.getTimeInMillis());
					}
				} else if(shippedItems > 0 && shippedItems < orderItems.size() && !"ORDER_PARTIALLY_FULFILLED".equalsIgnoreCase(orderHeader.getString("statusId"))) {
					dispatcher.runSync("changeOrderStatus", UtilMisc.toMap("orderId", orderHeader.getString("orderId"), "statusId", "ORDER_PARTIALLY_FULFILLED", "userLogin", context.get("userLogin")));
				}
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			return ServiceUtil.returnError(e.getMessage());
		}

		return result;
	}

	/**
	 * Rules to see if an item is printable in syrcase warehouse
	 */
	public static Map<String, Object> isPrintableInSyracuse(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		LocalDispatcher dispatcher = dctx.getDispatcher();

		Map<String, Object> result = ServiceUtil.returnSuccess();

		try {
			if(UtilValidate.isNotEmpty(context.get("orderId")) && UtilValidate.isNotEmpty(context.get("orderItemSeqId"))) {
				//check if the item can be printed in syracuse
				if(OrderHelper.syracuseCompatible(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"))) {
					OrderHelper.updateOrderItemFlag(delegator, UtilMisc.toMap(
							"orderId", context.get("orderId"),
							"orderItemSeqId", context.get("orderItemSeqId"),
							"printableSyracuse", "Y"
					));
				}
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to set item flag. " + e + " : " + e.getMessage(), module);
			return ServiceUtil.returnError(e.getMessage());
		} catch (Exception e) {
			EnvUtil.reportError(e);
			return ServiceUtil.returnError(e.getMessage());
		}

		return result;
	}

	/**
	 * Set a plate id
	 * Currently only for automatic plate numbers for Syr orders
	 */
	public static Map<String, Object> setPlateId(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		LocalDispatcher dispatcher = dctx.getDispatcher();

		Map<String, Object> result = ServiceUtil.returnSuccess();

		try {
			if(UtilValidate.isNotEmpty(context.get("orderId")) && UtilValidate.isNotEmpty(context.get("orderItemSeqId"))) {
				if("ART_SYRACUSE".equalsIgnoreCase(OrderHelper.getOrderItem(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId")).getString("statusId"))) {
					String printPlateId = "SYR-" + EnvConstantsUtil.COMPACT_MDY.format(UtilDateTime.nowTimestamp()) + "-" + EnvConstantsUtil.COMPACT_TIME.format(UtilDateTime.nowTimestamp());
					PlateHelper.createPrintPlate(delegator, printPlateId, "Syracuse");
					OrderHelper.updateOrderItemArtwork(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"), UtilMisc.toMap("printPlateId", printPlateId));
				}
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to set item flag. " + e + " : " + e.getMessage(), module);
			return ServiceUtil.returnError(e.getMessage());
		} catch (Exception e) {
			EnvUtil.reportError(e);
			return ServiceUtil.returnError(e.getMessage());
		}

		return result;
	}

	public static Map<String, Object> generateArtworkFiles(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();
		try {
			if(UtilValidate.isNotEmpty(context.get("orderId"))) {
				OrderHelper.generateArtworkFiles(delegator, context);
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to generate artwork files. " + e + " : " + e.getMessage(), module);
			return ServiceUtil.returnError(e.getMessage());
		}

		return result;
	}

	public static Map<String, Object> sendBackorderEmail(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		LocalDispatcher dispatcher = dctx.getDispatcher();

		Map<String, Object> result = ServiceUtil.returnSuccess();

		try {
			Map<String, Object> orderData = OrderHelper.getOrderData(delegator, (String) context.get("orderId"), false);
			String webSiteId = EnvUtil.getWebsiteId((String) ((GenericValue) orderData.get("orderHeader")).get("salesChannelEnumId"));

			// This was requested for envelopes only for now.  We might bring this to other folders and more...
			if (webSiteId.equals("envelopes")) {
				List<Map> items = (List<Map>) orderData.get("items");
				GenericValue orderItem = null;

				for (Map item : items) {
					orderItem = (GenericValue) item.get("item");

					if (((String) context.get("orderItemSeqId")).equalsIgnoreCase(orderItem.getString("orderItemSeqId"))) {
						break;
					}

					orderItem = null;
				}

				if (ProductHelper.allowBackorderEmail(delegator, orderItem.getString("productId"), orderItem.getBigDecimal("quantity").longValue()) && !OrderHelper.isItemPrinted(delegator, null, null, (GenericValue) orderItem)) {
					Map<String, String> emailData = ListrakHelper.createBackorderEmailData(orderData, orderItem);
					dispatcher.runAsync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "BackorderItem", "data", emailData, "email", orderData.get("email"), "webSiteId", webSiteId));
				}
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error trying to send Backorder Email. " + e + " : " + e.getMessage(), module);
			return ServiceUtil.returnError(e.getMessage());
		}

		return result;
	}
}