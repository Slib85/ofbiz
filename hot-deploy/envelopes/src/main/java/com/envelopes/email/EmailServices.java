/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.email;

import java.lang.*;
import java.util.*;

import com.bigname.integration.listrak.ListrakHelper;
import com.envelopes.order.OrderHelper;
import com.envelopes.order.OrderServices;
import org.apache.cxf.service.Service;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.entity.util.EntityUtilProperties;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import com.envelopes.util.*;

public class EmailServices {
	public static final String module = EmailServices.class.getName();

	public static Map<String, Object> sendEmail(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();

		if(!isBrontoActive()) {
			return result;
		}

		String redirectAddress = EntityUtilProperties.getPropertyValue("general.properties", "mail.notifications.redirectTo", delegator);
		if(UtilValidate.isEmpty(redirectAddress)) {
			redirectAddress = (String) context.get("email");
		}

		try {
			EmailHelper.sendEmail((Map) context.get("rawData"), (Map) context.get("data"), redirectAddress, (String) context.get("messageType"), true, (String) context.get("webSiteId"));
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "There was an error while trying to send email to: " + (String) context.get("email"), module);
		}

		return result;
	}

	public static Map<String, Object> sendSMS(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();

		if(!isBrontoActive()) {
			return result;
		}

		String redirectAddress = EntityUtilProperties.getPropertyValue("general.properties", "mail.notifications.redirectTo", delegator);
		if(UtilValidate.isEmpty(redirectAddress)) {
			redirectAddress = (String) context.get("email");
		}

		try {
			EmailHelper.sendSMS((Map) context.get("rawData"), (Map) context.get("data"), redirectAddress, (String) context.get("messageType"), (String) context.get("webSiteId"));
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "There was an error while trying to send SMS to: " + (String) context.get("email"), module);
		}

		return result;
	}

	/**
	 * Get all orders that are artwork ready for review
	 * If they status is in between the day it was set and the day before it becomes auto approved
	 * Send a reminder email
	 * @param dctx
	 * @param context
	 * @return
	 */
	public static Map<String, Object> sendProofReadyReminder(DispatchContext dctx, Map<String, ?> context) {
		Delegator delegator = dctx.getDelegator();
		LocalDispatcher dispatcher = dctx.getDispatcher();
		Map<String, Object> result = ServiceUtil.returnSuccess();

		//look up orders all orders that are in proof ready for review
		try {
			List<String> orderIds = new ArrayList<>();
			List<GenericValue> orderItems = OrderHelper.getAllProofRreadyOrderItems(delegator, false);
			for(GenericValue orderItem : orderItems) {
				GenericValue orderStatus = EntityUtil.getFirst(delegator.findByAnd("OrderStatus", UtilMisc.toMap("orderId", orderItem.getString("orderId"), "orderItemSeqId", orderItem.getString("orderItemSeqId"), "statusId", orderItem.getString("itemStatusId")), UtilMisc.toList("statusDatetime DESC"), false));
				long days = 0;
				try {
					//must not be on the day the status was set
					//must not be on the day the order will automatically go to print
					if(!orderIds.contains(orderItem.getString("orderId"))) {
						days = EnvUtil.getDaysBetweenDates(orderStatus.getTimestamp("statusDatetime"), UtilDateTime.nowTimestamp(), true, true);
						if (days > 1 && days < (Long.valueOf(OrderServices.autoApproveAfterDays) - 1)) {
							Map<String, String> emailTemplateData = ListrakHelper.createProofReadyEmailData(delegator, null, orderItem.getString("orderId"));
							String email = emailTemplateData.get("EmailAddress");
							try {
								ListrakHelper.sendEmail("Transactional", "ProofReadyReminder", emailTemplateData, email, EnvUtil.getWebsiteId(orderItem.getString("salesChannelEnumId")));
								//System.out.println(orderItem.getString("orderId"));
							} catch (Exception e) {
								EnvUtil.reportError(e);
								Debug.logError(e, "There was an error while trying to send Proof Ready Reminder email to: " + email, module);
							}
						}
					}
					orderIds.add(orderItem.getString("orderId"));
				} catch(Exception e1) {
					EnvUtil.reportError(e1);
					Debug.logError(e1, "There was an error while trying to send Proof Ready Reminder.", module);
				}
			}
		} catch(Exception e2) {
			EnvUtil.reportError(e2);
			Debug.logError(e2, "There was an error while trying to send Proof Ready Reminder.", module);
		}

		return result;
	}

	public static Map<String, Object> sendProofReadyEmail(DispatchContext dctx, Map<String, ?> context) {
		Delegator delegator = dctx.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();
		String orderId = (String)context.get("orderId");
		String webSiteId = (String) context.get("webSiteId");

		try {
			//check if website id is available
			if(UtilValidate.isEmpty(webSiteId)) {
				webSiteId = EnvUtil.getWebsiteId(EntityQuery.use(delegator).from("OrderHeader").where("orderId", orderId).queryOne().getString("salesChannelEnumId"));
			}

			Map<String, String> emailTemplateData = ListrakHelper.createProofReadyEmailData(delegator, null, orderId);
			String email = emailTemplateData.get("EmailAddress");
			try {
				ListrakHelper.sendEmail("Transactional", "ProofReady", emailTemplateData, email, webSiteId);
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "There was an error while trying to send Proof Ready email to: " + email, module);
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "There was an error while trying to get order data for order with id: " + orderId, module);
		}
		return result;
	}

	public static Map<String, Object> sendProofReadySMS(DispatchContext dctx, Map<String, ?> context) {
		Delegator delegator = dctx.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();
		String orderId = (String)context.get("orderId");
		String webSiteId = (String) context.get("webSiteId");

		if(!isBrontoActive()) {
			return result;
		}

		try {
			//check if website id is available
			if(UtilValidate.isEmpty(webSiteId)) {
				webSiteId = EnvUtil.getWebsiteId(EntityQuery.use(delegator).from("OrderHeader").where("orderId", orderId).queryOne().getString("salesChannelEnumId"));
			}

			Map<String, String> smsTemplateData = EmailHelper.createProofReadySMSData(delegator, null, orderId);
			String email = smsTemplateData.get("email");
			try {
				EmailHelper.sendSMS(null, UtilMisc.<String, String>toMap("Order_Number", smsTemplateData.get("Order_Number")), email, "proofUploaded", webSiteId);
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "There was an error while trying to send Proof Ready SMS to: " + email, module);
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "There was an error while trying to get order data for order with id: " + orderId, module);
		}
		return result;
	}

	public static Map<String, Object> sendNeedNewArtworkEmail(DispatchContext dctx, Map<String, ?> context) {
		Delegator delegator = dctx.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();
		String orderId = (String)context.get("orderId");
		String orderItemSeqId = (String)context.get("orderItemSeqId");
		String webSiteId = (String) context.get("webSiteId");

		if(!isBrontoActive()) {
			return result;
		}

		try {
			//check if website id is available
			if(UtilValidate.isEmpty(webSiteId)) {
				webSiteId = EnvUtil.getWebsiteId(EntityQuery.use(delegator).from("OrderHeader").where("orderId", orderId).queryOne().getString("salesChannelEnumId"));
			}

			Map<String, String> emailTemplateData = EmailHelper.createNeedNewArtworkEmailData(delegator, null, orderId, orderItemSeqId);
			String email = emailTemplateData.get("email");
			try {
				EmailHelper.sendEmail(null, emailTemplateData, email, "needNewArtwork", true, webSiteId);
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "There was an error while trying to send Need New Artwork email to: " + email, module);
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "There was an error while trying to get order data for order with id: " + orderId, module);
		}
		return result;
	}

	/**
	 * Send shipment email on a line item basis
	 * This will look at the lastUpdatedStamp to find all items where tracking was updated within a set interval and group them as a shipment
	 * @param dctx
	 * @param context
	 * @return
	 */
	public static Map<String, Object> sendItemShippedEmail(DispatchContext dctx, Map<String, ?> context) {
		Delegator delegator = dctx.getDelegator();
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		String orderId = (String) context.get("orderId");
		String orderItemSeqId = (String) context.get("orderItemSeqId");
		String webSiteId = (String) context.get("webSiteId");

		Map<String, Object> result = ServiceUtil.returnSuccess();

		try {
			//check if website id is available
			if(UtilValidate.isEmpty(webSiteId)) {
				webSiteId = EnvUtil.getWebsiteId(EntityQuery.use(delegator).from("OrderHeader").where("orderId", orderId).queryOne().getString("salesChannelEnumId"));
			}

			Map<String, String> emailTemplateData = ListrakHelper.createItemShippedEmailData(delegator, null, orderId, orderItemSeqId, webSiteId);

			//no tracking found, dont send email
			if(!Boolean.valueOf(emailTemplateData.get("foundTrackingNumber"))) {
				return result;
			}

			//String email = emailTemplateData.get("email");
			String email = emailTemplateData.get("EmailAddress");
			try {
				if(OrderHelper.isOrderPickUp(null, delegator, orderId)) {
					EmailHelper.sendEmail(null, emailTemplateData, email, "orderPickup", true, webSiteId);
				} else {
					//EmailHelper.sendEmail(null, emailTemplateData, email, "itemShipped", true, webSiteId);
					ListrakHelper.sendEmail("Transactional", "ItemShippingConfirmation", emailTemplateData, email, webSiteId);
				}
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "There was an error while trying to send Order Shipped email to: " + email, module);
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "There was an error while trying to get order data for order with id: " + orderId, module);
		}
		return result;
	}

	/**
	 * Send shipment email on an order basis
	 * @param dctx
	 * @param context
	 * @return
	 */
	public static Map<String, Object> sendOrderShippedEmail(DispatchContext dctx, Map<String, ?> context) {
		Delegator delegator = dctx.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();
		String orderId = (String)context.get("orderId");
		String webSiteId = (String) context.get("webSiteId");

		if(!isBrontoActive()) {
			return result;
		}

		try {
			//check if website id is available
			if(UtilValidate.isEmpty(webSiteId)) {
				webSiteId = EnvUtil.getWebsiteId(EntityQuery.use(delegator).from("OrderHeader").where("orderId", orderId).queryOne().getString("salesChannelEnumId"));
			}

			Map<String, String> emailTemplateData = EmailHelper.createOrderShippedEmailData(delegator, null, orderId);
			String email = emailTemplateData.get("email");
			try {
				if(OrderHelper.isOrderPickUp(null, delegator, orderId)) {
					EmailHelper.sendEmail(null, emailTemplateData, email, "orderPickup", true, webSiteId);
				} else {
					EmailHelper.sendEmail(null, emailTemplateData, email, "orderShipped", true, webSiteId);
				}
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "There was an error while trying to send Order Shipped email to: " + email, module);
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "There was an error while trying to get order data for order with id: " + orderId, module);
		}
		return result;
	}

	public static Map<String, Object> sendOrderShippedSMS(DispatchContext dctx, Map<String, ?> context) {
		Delegator delegator = dctx.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();
		String orderId = (String)context.get("orderId");
		String webSiteId = (String) context.get("webSiteId");

		if(!isBrontoActive()) {
			return result;
		}

		try {
			//check if website id is available
			if(UtilValidate.isEmpty(webSiteId)) {
				webSiteId = EnvUtil.getWebsiteId(EntityQuery.use(delegator).from("OrderHeader").where("orderId", orderId).queryOne().getString("salesChannelEnumId"));
			}

			Map<String, String> smsTemplateData = EmailHelper.createOrderShippedSMSData(delegator, null, orderId);
			String email = smsTemplateData.get("email");
			try {
				EmailHelper.sendSMS(null, UtilMisc.<String, String>toMap("Order_Number", smsTemplateData.get("Order_Number"), "Tracking_Number", smsTemplateData.get("trackingNumber"), "Ship_Type", OrderHelper.isOrderPickUp(null, delegator, orderId) ? "Is Ready For Pick Up" : "Has Shipped"), email, "orderShipped", webSiteId);
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "There was an error while trying to send Order Shipped SMS to: " + email, module);
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "There was an error while trying to get order data for order with id: " + orderId, module);
		}
		return result;
	}

	public static Map<String, Object> updateContactProofStatus(DispatchContext dctx, Map<String, ?> context) {
		Delegator delegator = dctx.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();
		String orderId = (String)context.get("orderId");
		String webSiteId = (String) context.get("webSiteId");

		if(!isBrontoActive()) {
			return result;
		}

		try {
			//check if website id is available
			if(UtilValidate.isEmpty(webSiteId)) {
				webSiteId = EnvUtil.getWebsiteId(EntityQuery.use(delegator).from("OrderHeader").where("orderId", orderId).queryOne().getString("salesChannelEnumId"));
			}

			boolean needsApproval = false;
			Map<String, Object> orderData = OrderHelper.getOrderData(delegator, orderId, true);
			List<Map> items = (List<Map>) orderData.get("items");
			for(Map item : items) {
				GenericValue orderItem = (GenericValue) item.get("item");
				if("ART_READY_FOR_REVIEW".equalsIgnoreCase(orderItem.getString("statusId"))) {
					needsApproval = true;
					break;
				}
			}
			String email = (String)orderData.get("email");
			try {
				EmailHelper.addOrUpdateContact(email, UtilMisc.<String, Object>toMap("proofStatus", (needsApproval) ? "UNAPPROVED" : "APPROVED"), webSiteId);
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "There was an error while trying to send Proof Ready email to: " + email, module);
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "There was an error while trying to get order data for order with id: " + orderId, module);
		}
		return result;
	}

	public static Map<String, Object> addOrUpdateContact(DispatchContext dctx, Map<String, ?> context) {
		Delegator delegator = dctx.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();

		String webSiteId = (UtilValidate.isEmpty(context.get("webSiteId"))) ? "envelopes" : (String) context.get("webSiteId");
		Boolean translated = (Boolean) context.get("translated");
		if(translated == null) {
			translated = Boolean.FALSE;
		}

		if(!isBrontoActive()) {
			return result;
		}

		try {
			EmailHelper.addOrUpdateContact((String) context.get("email"), (Map) context.get("rawData"), translated.booleanValue(), webSiteId);
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "There was an error while trying to send email to: " + (String) context.get("email"), module);
		}

		return result;
	}

	public static Map<String, Object> sendReorderEmail(DispatchContext dctx, Map<String, ?> context) {
		LocalDispatcher dispatcher = (LocalDispatcher) dctx.getDispatcher();
		Delegator delegator = dctx.getDelegator();

		Map<String, Object> result = ServiceUtil.returnSuccess();

		Integer numberOfDays = (Integer) context.get("numberOfDays");
		int startDays = 0;
		int endDays = 0;

		if(!isBrontoActive()) {
			return result;
		}

		if(UtilValidate.isEmpty(numberOfDays)) {
			numberOfDays = -90;
		}

		startDays = numberOfDays.intValue();
		endDays = numberOfDays.intValue() + 1;

		try {
			List<EntityCondition> conditionList = new ArrayList<>();
			conditionList.add(EntityCondition.makeCondition("orderDate", EntityOperator.GREATER_THAN_EQUAL_TO, EnvUtil.getNDaysBeforeOrAfterNow(startDays, true)));
			conditionList.add(EntityCondition.makeCondition("orderDate", EntityOperator.LESS_THAN_EQUAL_TO, EnvUtil.getNDaysBeforeOrAfterNow(endDays, true)));
			conditionList.add(EntityCondition.makeCondition("salesChannelEnumId", EntityOperator.IN, UtilMisc.toList("AE_SALES_CHANNEL", "ENV_SALES_CHANNEL")));
			conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "ORDER_SHIPPED"));
			List<GenericValue> orderHeaders = delegator.findList("OrderHeader", EntityCondition.makeCondition(conditionList), null, null, null, false);

			if(UtilValidate.isNotEmpty(orderHeaders)) {
				for(GenericValue orderHeader : orderHeaders) {
					Map<String, String> reorderData = EmailHelper.createReorderEmailData(delegator, null, orderHeader.getString("orderId"));

					if(UtilValidate.isEmpty(reorderData.get("itemReminderItem"))) {
						continue;
					}

					String email = reorderData.get("email");
					String messageType = reorderData.get("messageType");

					// Send ship email 1-59 minutes staggered
					Random random = new Random();
					int Low = 1;
					int High = 59;
					int randomInt = random.nextInt(High-Low) + Low;

					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.MINUTE, randomInt);
					long startTime = cal.getTimeInMillis();

					try {
						//dispatcher.schedule("sendEmail", UtilMisc.toMap("email", email, "rawData", null, "data", reorderData, "messageType", messageType, "webSiteId", EnvUtil.getWebsiteId(orderHeader.getString("salesChannelEnumId")), "userLogin", EnvUtil.getSystemUser(delegator)), startTime);
					} catch (Exception e) {
						EnvUtil.reportError(e);
						Debug.logError(e, "There was an error while trying to send Order Shipped email to: " + email, module);
					}
				}
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error while trying to send re-order emails", module);
		}

		return result;
	}

	/**
	 * Send email out stating the order is in production
	 * This will only go out if the previous status is ART_READY_FOR_REVIEW and the new status is ART_PROOF_APPROVED
	 * @param dctx
	 * @param context
	 * @return
	 */
	public static Map<String, Object> sendProductionEmail(DispatchContext dctx, Map<String, ?> context) {
		Delegator delegator = dctx.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();

		String orderId = (String) context.get("orderId");
		String orderItemSeqId = (String) context.get("orderItemSeqId");
		String webSiteId = (String) context.get("webSiteId");

		try {
			//check if website id is available
			if(UtilValidate.isEmpty(webSiteId)) {
				webSiteId = EnvUtil.getWebsiteId(EntityQuery.use(delegator).from("OrderHeader").where("orderId", orderId).queryOne().getString("salesChannelEnumId"));
			}

			//check if the previous status was ART_READY_FOR_REVIEW
			List<GenericValue> previousStatus = delegator.findByAnd("OrderStatus", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), UtilMisc.toList("statusDatetime DESC"), false);

			//below we loop through them from newest to oldest, reason is because this service runs after the status has already changed to ART_PROOF_APPROVED,
			//so we need to get the previous status right before to check if its ART_READY_FOR_REVIEW
			GenericValue lastStatus = null;
			int count = 0;
			for(GenericValue status : previousStatus) {
				if(count == 1 && "ART_READY_FOR_REVIEW".equalsIgnoreCase(status.getString("statusId"))) {
					lastStatus = status;
					break;
				}
				count++;
			}

			if(UtilValidate.isNotEmpty(lastStatus) && "ART_READY_FOR_REVIEW".equalsIgnoreCase(lastStatus.getString("statusId"))) {
				Map<String, Object> orderData = OrderHelper.getOrderData(delegator, orderId, true);
				String email = (String) orderData.get("email");

				try {
					Map<String, String> data = ListrakHelper.createProductionEmailData(delegator, orderData, orderId, orderItemSeqId);
					ListrakHelper.sendEmail("Transactional", "Production", data, email, webSiteId);
				} catch(Exception e) {
					EnvUtil.reportError(e);
					Debug.logError(e, "There was an error while trying to send email to: " + email, module);
				}
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "There was an error while trying to get order data for order with id: " + orderId, module);
		}
		return result;
	}

	public static Map<String, Object> sendQuoteCompletedEmail(DispatchContext dctx, Map<String, ?> context) {
		Delegator delegator = dctx.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();

		String quoteIds = (String) context.get("quoteIds");
		String[] quoteIdsList = quoteIds.split(",");
		String webSiteId = (String) context.get("webSiteId");

		try {
			Map<String, String> data = ListrakHelper.createQuoteCompletedEmailData(delegator, quoteIdsList);
			ListrakHelper.sendEmail("Transactional", "QuoteCompleted", data, data.get("EmailAddress"), webSiteId);
			ListrakHelper.sendEmail("Transactional", "QuoteCompleted", data, data.get("SalesEmail"), webSiteId);
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "There was an error trying to send email for quotes id: " + quoteIds, module);
		}
		return result;
	}

	public static Map<String, Object> sendTradeAnniversaryEmails(DispatchContext dctx, Map<String, ?> context) {
		Delegator delegator = dctx.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();

		String emailBeingWorkedOn = null;

		try {
			List<Map<String, String>> anniversaryList = ListrakHelper.getTodaysTradeAnniversaryEmails(delegator);
			for (Map<String, String> anniversaryData : anniversaryList) {
				ListrakHelper.sendEmail("Transactional", "TradeAnniversary", anniversaryData, anniversaryData.get("EmailAddress"), "envelopes");
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "There was an issue sending trade anniversary email to: " + emailBeingWorkedOn, module);
			result = ServiceUtil.returnError("There was an issue sending trade anniversary email to: " + emailBeingWorkedOn);
		}
		return result;
	}

	/**
	 * Check if we should run bronto or return and exit
	 */
	protected static boolean isBrontoActive() {
		if(!("active").equalsIgnoreCase(EmailHelper.BRONTO_STATUS)) {
			return false;
		}

		return true;
	}
}