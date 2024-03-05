/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.netsuite;


import com.bigname.integration.listrak.ListrakHelper;
import com.envelopes.cxml.CXMLHelper;
import com.envelopes.product.ProductHelper;
import com.envelopes.quote.PurchaseOrderPDF;
import com.envelopes.util.EnvUtil;
import com.google.gson.Gson;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.order.order.OrderReadHelper;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import java.util.*;

import com.envelopes.order.OrderHelper;

public class NetsuiteServices {
	public static final String module = NetsuiteServices.class.getName();

	/**
	 * Get tracking numbers from Netsuite and mark them in BOS
	 * @param dctx
	 * @param context
	 * @return
	 */
	public static Map<String, Object> getOrderTracking(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		LocalDispatcher dispatcher = dctx.getDispatcher();
		Map<String, Object> result = ServiceUtil.returnSuccess();

		try {
			//get tracking data from netsuite
			Map<String, Map<String, String>> orderAndTracking = NetsuiteHelper.getShippedOrdersFromNetsuite();

			if(UtilValidate.isNotEmpty(orderAndTracking)) {
				//insert tracking into database
				Map<String, List<String>> updatedTracking = OrderHelper.updateOrderTracking(delegator, orderAndTracking);

				//change item statuses to shipped
				Iterator updatedTrackingIter = updatedTracking.entrySet().iterator();
				while(updatedTrackingIter.hasNext()) {
					Map.Entry pairs = (Map.Entry) updatedTrackingIter.next();
					String orderId = (String) pairs.getKey();
					List<String> orderItemSeqIds = (List<String>) pairs.getValue();

					for(String orderItemSeqId : orderItemSeqIds) {
						OrderHelper.processItemShipStatusChange(delegator, dispatcher, orderId, orderItemSeqId, true, null);
					}
				}
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			return ServiceUtil.returnError(e.getMessage());
		}

		return result;
	}

	/**
	 * Get the product inventory levels from Netsuite
	 * @param dctx
	 * @param context
	 * @return
	 */
	public static Map<String, Object> getProductInventory(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		Map<String, Object> result = ServiceUtil.returnSuccess();

		try {
			Map<String, Map<String, Object>> productInventory = NetsuiteHelper.getInventoryFromNetsuite();
			if(UtilValidate.isNotEmpty(productInventory)) {
				Iterator dataIter = productInventory.entrySet().iterator();
				while(dataIter.hasNext()) {
					Map.Entry pairs = (Map.Entry) dataIter.next();
					try {
						ProductHelper.updateStockLevel(delegator, (String) pairs.getKey(), (Map<String, Object>) pairs.getValue());
					} catch (GenericEntityException e) {
						Debug.logError("Failed to update product: " + (String) pairs.getKey(), module);
					}
				}
			}
			ProductHelper.removeStockLevel(delegator, -10);
		} catch (Exception e) {
			EnvUtil.reportError(e);
			return ServiceUtil.returnError(e.getMessage());
		}

		return result;
	}

	/**
	 * Export a customer to Netsuite
	 * @param dctx
	 * @param context
	 * @return
	 */
	public static Map<String, Object> exportCustomer(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		LocalDispatcher dispatcher = dctx.getDispatcher();
		Map<String, Object> result = ServiceUtil.returnSuccess();

		String partyId = (String) context.get("partyId");
		String orderId = (String) context.get("orderId");
		String quoteId = (String) context.get("quoteId");

		StringBuilder emailMessage = new StringBuilder("");
		try {
			Map<String, String> response = NetsuiteHelper.exportCustomer(delegator, dispatcher, partyId, orderId, quoteId);
			if(UtilValidate.isNotEmpty(response)) {
				emailMessage.append("");
				Iterator entries = response.entrySet().iterator();
				while(entries.hasNext()) {
					Map.Entry entry = (Map.Entry) entries.next();
					emailMessage.append("" + (String) entry.getKey() + " : " + (String) entry.getValue() + "<br><br>");
				}
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
		}

		//TODO schedule email report
		if(UtilValidate.isNotEmpty(emailMessage.toString())) {
			emailMessage.insert(0, "<p>The following orders were attempted for export:</p><br>");
			//TODO send email
		}

		result.put("message", emailMessage.toString().replace("<p>","").replace("</p>","").replace("<br>","\n"));

		return result;
	}

	/**
	 * Export an order to Netsuite
	 * @param dctx
	 * @param context
	 * @return
	 */
	public static Map<String, Object> exportOrder(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		LocalDispatcher dispatcher = dctx.getDispatcher();
		Map<String, Object> result = ServiceUtil.returnSuccess();

		List<String> orderIds = (List<String>) context.get("orderIds");
		if(UtilValidate.isNotEmpty(orderIds) && orderIds.get(0).contains(",")) {
			orderIds = new ArrayList<String>(Arrays.asList(orderIds.get(0).split(",")));
		}
		boolean ignoreValidity = (UtilValidate.isEmpty(context.get("ignoreValidity"))) ? true : ((Boolean) context.get("ignoreValidity")).booleanValue();

		StringBuilder emailMessage = new StringBuilder("");
		for(String orderId : orderIds) {
			try {
				Map<String, String> response = NetsuiteHelper.exportOrder(delegator, dispatcher, orderId, ignoreValidity);
				if(UtilValidate.isNotEmpty(response)) {
					emailMessage.append("");
					Iterator entries = response.entrySet().iterator();
					while(entries.hasNext()) {
						Map.Entry entry = (Map.Entry) entries.next();
						emailMessage.append("" + (String) entry.getKey() + " : " + (String) entry.getValue() + "<br><br>");
					}
				}
			} catch (Exception e) {
				EnvUtil.reportError(e);
			}
		}

		//TODO schedule email report
		if(UtilValidate.isNotEmpty(emailMessage.toString())) {
			emailMessage.insert(0, "<p>The following orders were attempted for export:</p><br>");
			//TODO send email
		}

		result.put("message", emailMessage.toString().replace("<p>","").replace("</p>","").replace("<br>","\n"));

		return result;
	}

	/**
	 * Update an existin order in Netsuite
	 * @param dctx
	 * @param context
	 * @return
	 */
	public static Map<String, Object> updateOrder(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		LocalDispatcher dispatcher = dctx.getDispatcher();
		Map<String, Object> result = ServiceUtil.returnSuccess();

		String orderId = (String) context.get("orderId");

		try {
			//hack, any secas for this service (known by the user delayService) need to be scheduled to run on staggered schedule
			if(context.containsKey("userLogin") && "delayService".equals(((GenericValue) context.get("userLogin")).getString("userLoginId"))) {
				// Send ship email 1-7 minutes staggered
				Calendar cal = Calendar.getInstance();
				Random random = new Random();

				//stagger minutes
				int lowMin = 1;
				int highMin = 7;
				int randomMin = random.nextInt(highMin-lowMin) + lowMin;
				cal.add(Calendar.MINUTE, randomMin);

				//stagger seconds
				int lowSec = 1;
				int highSec = 60;
				int randomSec = random.nextInt(highSec-lowSec) + lowSec;
				cal.add(Calendar.SECOND, randomSec);

				dispatcher.schedule("updateNetsuiteOrder", UtilMisc.toMap("orderId", orderId, "userLogin", EnvUtil.getSystemUser(delegator)), cal.getTimeInMillis());
				return result;
			}

			Map<String, String> response = NetsuiteHelper.updateOrder(delegator, dispatcher, orderId);
			if(UtilValidate.isNotEmpty(response) && UtilValidate.isNotEmpty(response.get(orderId)) && !response.get(orderId).contains("Updated successfully")) {
				return ServiceUtil.returnError("Error updating order id: " + orderId + " : " + response);
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			return ServiceUtil.returnError(e.getMessage());
		}

		return result;
	}

	/**
	 * Create a purchase order in Netsuite
	 * @param dctx
	 * @param context
	 * @return
	 */
	public static Map<String, Object> createPurchaseOrder(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		LocalDispatcher dispatcher = dctx.getDispatcher();
		GenericValue userLogin = (GenericValue) context.get("userLogin");

		Map<String, Object> result = ServiceUtil.returnSuccess();

		String orderId = (String) context.get("orderId");
		String orderItemSeqId = (String) context.get("orderItemSeqId");

		try {
			Map<String, Object> data = new HashMap<>();
			GenericValue vendorOrder = EntityQuery.use(delegator).from("VendorOrder").where("orderId", orderId, "orderItemSeqId", orderItemSeqId).queryOne();
			if(vendorOrder != null && UtilValidate.isEmpty(vendorOrder.getString("purchaseOrderId"))) {
				Map<String, Object> vendorData = new Gson().fromJson(vendorOrder.getString("priceData"), HashMap.class);
				if(UtilValidate.isNotEmpty(vendorData) && UtilValidate.isNotEmpty(vendorData.get("grossProfit"))) {
					Map<String, Object> vendorCostData = (Map<String, Object>) ((Map<String, Object>) vendorData.get("grossProfit")).get(vendorOrder.getString("partyId"));
					if(UtilValidate.isNotEmpty(vendorCostData.get("cost"))) {
						data.put("cost", ((Object) vendorCostData.get("cost")).toString());
						data.put("orderId", orderId);
						data.put("orderItemSeqId", orderItemSeqId);
						data.put("comments", vendorCostData.get("comments"));
					}
				}

				OrderReadHelper orh = new OrderReadHelper(delegator, (String) context.get("orderId"));
				GenericValue vendor = EntityQuery.use(delegator).from("Vendor").where("partyId", vendorOrder.getString("partyId")).queryOne();
				data = CXMLHelper.getPOData(delegator, OrderHelper.getShippingAddress(orh, delegator, (String) context.get("orderId")), vendor, EntityQuery.use(delegator).from("OrderItem").where("orderId", orderId, "orderItemSeqId", orderItemSeqId).queryOne(), data);
				Map<String, Object> purchaseOrder = NetsuiteHelper.createPurchaseOrder(delegator, dispatcher, data);
				if (UtilValidate.isNotEmpty(purchaseOrder) && UtilValidate.isEmpty(purchaseOrder.get("purchaseOrderId"))) {
					purchaseOrder = NetsuiteHelper.getOrderItemPO(delegator, dispatcher, data);
				}
				if (UtilValidate.isNotEmpty(purchaseOrder) && UtilValidate.isNotEmpty((String) purchaseOrder.get("purchaseOrderId"))) {
					vendorOrder.put("purchaseOrderId", purchaseOrder.get("purchaseOrderId"));
					vendorOrder.store();

					PurchaseOrderPDF pdf = new PurchaseOrderPDF(delegator, UtilMisc.toMap("orderId", (String) context.get("orderId"), "orderItemSeqId", (String) context.get("orderItemSeqId"), "purchaseOrderId", purchaseOrder.get("purchaseOrderId")));
					pdf.savePDF();

					EnvUtil.convertMapValuesToString(data);

					dispatcher.runAsync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "OrderOutsource", "data", ListrakHelper.createOrderOutsourceData(data), "email", vendor.getString("email"), "webSiteId", EnvUtil.getWebsiteId(orh.getOrderHeader().getString("salesChannelEnumId"))));
					dispatcher.runAsync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "OrderOutsource", "data", ListrakHelper.createOrderOutsourceData(data), "email", "outsource@" + EnvUtil.getWebsiteId(orh.getOrderHeader().getString("salesChannelEnumId")) + ".com", "webSiteId", EnvUtil.getWebsiteId(orh.getOrderHeader().getString("salesChannelEnumId"))));

					//change status if sent successfully
					dispatcher.runSync("changeOrderItemStatus", UtilMisc.toMap("orderId", (String) context.get("orderId"), "orderItemSeqId", (String) context.get("orderItemSeqId"), "statusId", "SENT_PURCHASE_ORDER", "userLogin", userLogin));
				} else {
					return ServiceUtil.returnError("Error trying to create purchase order in netsuite for Order Number: " + orderId);
				}
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			return ServiceUtil.returnError(e.getMessage());
		}

		return result;
	}

	/**
	 * Create or update order item fulfillments
	 * @param dctx
	 * @param context
	 * @return
	 */
	public static Map<String, Object> createItemFulfillment(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();
		LocalDispatcher dispatcher = dctx.getDispatcher();
		Map<String, Object> result = ServiceUtil.returnSuccess();

		String orderId = (String) context.get("orderId");

		try {
			//hack, any secas for this service (known by the user delayService) need to be scheduled to run on staggered schedule
			Map<String, Object> data = UtilMisc.toMap(
					"orderId", orderId,
					"orderItemSeqId", context.get("orderItemSeqId"),
					"lineItem", context.get("lineItem"),
					"trackingNumber", context.get("trackingNumber"),
					"packageWeight", context.get("packageWeight"),
					"tranDate", context.get("tranDate"),
					"deliveryDate", context.get("deliveryDate"),
					"purchaseOrderId", context.get("purchaseOrderId"),
					"userLogin", EnvUtil.getSystemUser(delegator)
			);
			if(context.containsKey("userLogin") && "delayService".equals(((GenericValue) context.get("userLogin")).getString("userLoginId"))) {
				// Send fulfillment 1-7 minutes staggered
				Calendar cal = Calendar.getInstance();
				Random random = new Random();

				//stagger minutes
				int lowMin = 1;
				int highMin = 7;
				int randomMin = random.nextInt(highMin-lowMin) + lowMin;
				cal.add(Calendar.MINUTE, randomMin);

				//stagger seconds
				int lowSec = 1;
				int highSec = 60;
				int randomSec = random.nextInt(highSec-lowSec) + lowSec;
				cal.add(Calendar.SECOND, randomSec);

				dispatcher.schedule("createItemFulfillment", data, cal.getTimeInMillis());
				return result;
			}

			data.remove("userLogin");
			Map<String, Object> response = NetsuiteHelper.createOrderItemFulfillment(delegator, dispatcher, data);
			if(UtilValidate.isNotEmpty(response) && !((Boolean) response.get("success"))) {
				return ServiceUtil.returnError("Error creating fulfillment for order id: " + orderId + " : " + response);
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			return ServiceUtil.returnError(e.getMessage());
		}

		return result;
	}
}
