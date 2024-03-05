/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.plating;

import java.lang.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;

import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.model.DynamicViewEntity;
import org.apache.ofbiz.entity.model.ModelKeyMap;
import org.apache.ofbiz.entity.transaction.GenericTransactionException;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.entity.util.EntityListIterator;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.envelopes.order.OrderHelper;
import com.envelopes.util.*;

public class PlateHelper {
	public static final String module = PlateHelper.class.getName();
	private static final SimpleDateFormat mdy = new SimpleDateFormat("MM/dd/yyyy");
	private static final SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	public static Map<String, String> PRESS_IDS;

	static {
		PRESS_IDS = new HashMap<String, String>();
		PRESS_IDS.put("1 Color","1C");
		PRESS_IDS.put("2 Color","2C");
		PRESS_IDS.put("4 Color","4C");
		PRESS_IDS.put("Paper","P");
		PRESS_IDS.put("Super Jet #1","SJ1");
		PRESS_IDS.put("Super Jet #2","SJ2");
		PRESS_IDS.put("Super Jet #3","SJ3");
		PRESS_IDS.put("iJet","iJet");
		PRESS_IDS.put("MGI","MGI");
		PRESS_IDS.put("OKIW","OKIW");
		PRESS_IDS.put("OKIC","OKIC");
		PRESS_IDS.put("HP Indigo","HPI");
		PRESS_IDS.put("Mem4C","Mem4C");
		PRESS_IDS.put("Digital","D");
		PRESS_IDS.put("Syracuse","SYR");
	}

	/*
	 * Create a plate. MUST wrap this in a TRANSACTION when called
	 */
	public static GenericValue createPrintPlate(Delegator delegator, LocalDispatcher dispatcher, Document xml) throws GenericEntityException, GenericServiceException, Exception {
		GenericValue printPlate = null;

		String printPlateId = xml.getElementsByTagName("myPlateID").item(0).getTextContent();
		String press = xml.getElementsByTagName("myPress").item(0).getTextContent();

		if(UtilValidate.isNotEmpty(printPlateId) && UtilValidate.isNotEmpty(press)) {
			printPlate = createPrintPlate(delegator, printPlateId, press);

			Timestamp dueDate = UtilDateTime.nowTimestamp();

			for(int i = 1; i <= 48; i++) {
				String artworkName = xml.getElementsByTagName("myProduct" + i).item(0).getTextContent();
				if(artworkName != null && !artworkName.equals("undefined")) {
					String orderId = artworkName.substring(0, artworkName.indexOf("_"));
					String orderItemSeqId = artworkName.substring(artworkName.indexOf("_")+1, artworkName.indexOf("_")+6);
					GenericValue artwork = setArtworkPlate(delegator, orderId, orderItemSeqId, printPlateId);
					GenericValue orderItem = delegator.findOne("OrderItem", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), false);
					if(UtilValidate.isNotEmpty(artwork) && UtilValidate.isNotEmpty(orderItem)) {
						if(UtilValidate.isNotEmpty(orderItem.getTimestamp("dueDate"))) {
							if(i == 1 || dueDate.after(orderItem.getTimestamp("dueDate"))) {
								dueDate = orderItem.getTimestamp("dueDate");
							}
						}
						dispatcher.runSync("changeOrderItemStatus", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId, "statusId", "PP_ASSIGNED_TO_PLATE", "userLogin", EnvUtil.getSystemUser(delegator)));
					}
				}
			}

			printPlate.put("dueDate", dueDate);
			delegator.createOrStore(printPlate);
		}

		return printPlate;
	}

	public static GenericValue createPrintPlate(Delegator delegator, String printPlateId, String press) throws GenericEntityException {
		GenericValue printPlate = delegator.makeValue("PrintPlate");
		printPlate.put("printPlateId", UtilValidate.isEmpty(printPlateId) ? PRESS_IDS.get(press) + "-" + delegator.getNextSeqId("PrintPlate") : printPlateId);
		printPlate.put("printPressId", PRESS_IDS.get(press));
		printPlate.put("schedulePrintPressId", PRESS_IDS.get(press));
		printPlate.put("statusId", "PP_LAYOUT_COMPLETE");
		delegator.createOrStore(printPlate);

		return printPlate;
	}

	/*
	 * Create a Print Card
	 */
	public static GenericValue createPrintCard(Delegator delegator, String str) throws GenericEntityException, Exception {
		GenericValue printCard = delegator.makeValue("PrintCard", UtilMisc.toMap("printCardId", delegator.getNextSeqId("PrintCard")));
		Document xml = EnvUtil.stringToXML(str);

		return printCard;
	}

	/*
	 * Update orderItemArtwork with the print plate id
	 */
	public static GenericValue setArtworkPlate(Delegator delegator, String orderId, String orderItemSeqId, String printPlateId) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(orderId) && UtilValidate.isNotEmpty(orderItemSeqId) && UtilValidate.isNotEmpty(printPlateId)) {
			GenericValue orderItemArtwork = EntityUtil.getFirst(delegator.findByAnd("OrderItemArtwork", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), null, false));
			if(orderItemArtwork != null) {
				orderItemArtwork.set("printPlateId", printPlateId);
				orderItemArtwork.store();
				return orderItemArtwork;
			}
		}

		return null;
	}

	/*
	 * Cancel a Print Card
	 */
	public static GenericValue cancelPlate(Delegator delegator, LocalDispatcher dispatcher, String plateId, GenericValue userLogin) throws GenericEntityException, GenericServiceException {
		GenericValue printPlate = null;
		if(userLogin != null && UtilValidate.isNotEmpty(plateId)) {
			printPlate = delegator.findOne("PrintPlate", UtilMisc.toMap("printPlateId", plateId), false);
			printPlate.set("statusId", "PP_CANCELED");
			printPlate.store();

			List<GenericValue> orderItemArtworkList = delegator.findByAnd("OrderItemArtwork", UtilMisc.toMap("printPlateId", plateId), null, false);

			// update orderItemArtwork record
			if(orderItemArtworkList != null && orderItemArtworkList.size() > 0){
				for(GenericValue orderItemArtwork : orderItemArtworkList) {
					orderItemArtwork.set("printPlateId", null);
					orderItemArtwork.store();

					//reset order item status
					GenericValue orderItem = PlateHelper.getOrderItem(delegator, orderItemArtwork);
					if(orderItem.getEntityName().equals("OrderItem")) {
						dispatcher.runSync("changeOrderItemStatus", UtilMisc.toMap("orderId", orderItem.getString("orderId"), "orderItemSeqId", orderItem.getString("orderItemSeqId"), "statusId", "ART_GOOD_TO_GO", "userLogin", userLogin));
					} else {
						orderItem.set("statusId", "ART_GOOD_TO_GO");
						orderItem.store();
					}
				}
			}
		}

		return printPlate;
	}

	/*
	 * Get order item or print card related to a plate
	 */
	public static GenericValue getOrderItem(Delegator delegator, GenericValue orderItemArtwork) throws GenericEntityException {
		if (UtilValidate.isNotEmpty(orderItemArtwork.getString("orderItemSeqId"))) {
			return delegator.findOne("OrderItem", UtilMisc.toMap("orderId", orderItemArtwork.get("orderId"), "orderItemSeqId", orderItemArtwork.get("orderItemSeqId")), false);
		} else {
			return EntityUtil.getFirst(delegator.findByAnd("PrintCard", UtilMisc.toMap("orderItemArtworkId", orderItemArtwork.getString("orderItemArtworkId")), null, false));
		}
	}

	public static String startJobTime(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		boolean success = false;

		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try {
				if(UtilValidate.isNotEmpty(context.get("approval"))) {
					OrderHelper.updateOrderItemArtwork(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"), UtilMisc.toMap((UtilValidate.isNotEmpty(context.get("finish")) && "Y".equalsIgnoreCase((String) context.get("finish"))) ? "approvalFinishTime" : "approvalStartTime", UtilDateTime.nowTimestamp(), "pressMan", ((GenericValue) request.getSession().getAttribute("userLogin")).getString("userLoginId")));
				} else {
					OrderHelper.updateOrderItemArtwork(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"), UtilMisc.toMap("jobStartTime", UtilDateTime.nowTimestamp(), "pressMan", ((GenericValue) request.getSession().getAttribute("userLogin")).getString("userLoginId")));
				}
				success = true;
			} catch (Exception e1) {
				TransactionUtil.rollback(beganTransaction, "Error removing item from schedule.", e1);
				EnvUtil.reportError(e1);
			}
		} catch (GenericTransactionException e3) {
			EnvUtil.reportError(e3);
		} finally {
			try {
				TransactionUtil.commit(beganTransaction);
			} catch (GenericEntityException e2) {
				EnvUtil.reportError(e2);
			}
		}

		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("success", success);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String removeFromSchedule(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> parameterMap = EnvUtil.getParameterMap(request);
		boolean success = false;

		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try {
				OrderHelper.updateOrderItemArtwork(delegator, (String) parameterMap.get("orderId"), (String) parameterMap.get("orderItemSeqId"), UtilMisc.toMap("jobFinishTime", UtilDateTime.nowTimestamp(), "pressMan", ((GenericValue) request.getSession().getAttribute("userLogin")).getString("userLoginId")));
				GenericValue orderItem = delegator.findOne("OrderItem", UtilMisc.toMap("orderId", (String) parameterMap.get("orderId"), "orderItemSeqId", (String) parameterMap.get("orderItemSeqId")), true);
				if(orderItem != null) {
					orderItem.set("isRemoveFromSchedule", "Y");
					delegator.store(orderItem);

					dispatcher.runSync("changeOrderItemStatus", UtilMisc.toMap("orderId", (String) parameterMap.get("orderId"), "orderItemSeqId", (String) parameterMap.get("orderItemSeqId"), "statusId", "ART_PRINTED", "userLogin", userLogin == null ? EnvUtil.getSystemUser(delegator) : userLogin));
					if(UtilValidate.isNotEmpty(parameterMap.get("timestamp"))) {
						GenericValue orderStatusChange = OrderHelper.updateOrderStatusTime(delegator, (String) parameterMap.get("orderId"), (String) parameterMap.get("orderItemSeqId"), "ART_PRINTED", EnvUtil.convertStringToTimestamp((String) parameterMap.get("timestamp") + " 00:00:00.0"));
					}
					success = true;
				}
			} catch (Exception e1) {
				TransactionUtil.rollback(beganTransaction, "Error removing item from schedule.", e1);
				EnvUtil.reportError(e1);
			}
		} catch (GenericTransactionException e3) {
			EnvUtil.reportError(e3);
		} finally {
			try {
				TransactionUtil.commit(beganTransaction);
			} catch (GenericEntityException e2) {
				EnvUtil.reportError(e2);
			}
		}

		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("success", success);


		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String addToSchedule(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> parameterMap = EnvUtil.getParameterMap(request);
		boolean success = false;

		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try {
				GenericValue orderItem = delegator.findOne("OrderItem", UtilMisc.toMap("orderId", (String) parameterMap.get("orderId"), "orderItemSeqId", (String) parameterMap.get("orderItemSeqId")), true);
				if(orderItem != null) {
					orderItem.set("isRemoveFromSchedule", null);
					delegator.store(orderItem);

					dispatcher.runSync("changeOrderItemStatus", UtilMisc.toMap("orderId", (String) parameterMap.get("orderId"), "orderItemSeqId", (String) parameterMap.get("orderItemSeqId"), "statusId", "PP_ASSIGNED_TO_PLATE", "userLogin", userLogin == null ? EnvUtil.getSystemUser(delegator) : userLogin));
					success = true;
				}
			} catch (Exception e1) {
				TransactionUtil.rollback(beganTransaction, "Error adding item to schedule.", e1);
				EnvUtil.reportError(e1);
			}
		} catch (GenericTransactionException e3) {
			EnvUtil.reportError(e3);
		} finally {
			try {
				TransactionUtil.commit(beganTransaction);
			} catch (GenericEntityException e2) {
				EnvUtil.reportError(e2);
			}
		}

		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("success", success);


		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static List<List<Map<String, Object>>> getOpenPlates(Delegator delegator, GenericValue userLogin, String printPressId) throws GenericEntityException {
		return getOpenPlates(delegator, userLogin, printPressId, null, null);
	}
	public static List<List<Map<String, Object>>> getOpenPlates(Delegator delegator, GenericValue userLogin, String printPressId, String orderId, String orderItemSeqId) throws GenericEntityException {
		List<List<Map<String, Object>>> openPlates = new ArrayList<>();
		if (UtilValidate.isEmpty(userLogin)) {
			return null;
		}

		List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
		conditionList.add(EntityCondition.makeCondition("isRemoveFromSchedule", EntityOperator.EQUALS, null));
		conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.IN, Arrays.asList("PP_LAYOUT_COMPLETE")));
		conditionList.add(EntityCondition.makeCondition("productFeatureTypeId", EntityOperator.EQUALS, "COLOR"));
		conditionList.add(EntityCondition.makeCondition("shipGroupSeqId", EntityOperator.EQUALS, "00001"));
		if(printPressId != null) {
			conditionList.add(EntityCondition.makeCondition("printPressId", EntityOperator.EQUALS, printPressId));
		}
		if(orderId != null && orderItemSeqId != null) {
			conditionList.add(EntityCondition.makeCondition("orderId", EntityOperator.EQUALS, orderId));
			conditionList.add(EntityCondition.makeCondition("orderItemSeqId", EntityOperator.EQUALS, orderItemSeqId));
		}

		List<String> orderBy = new ArrayList<>();
		orderBy.add("dueDate");
		orderBy.add("printPlateId");
		//orderBy.add("orderId");
		//orderBy.add("orderItemSeqId");

		DynamicViewEntity dve = new DynamicViewEntity();
		dve.addMemberEntity("OI", "OrderItem");
		dve.addAlias("OI", "orderId");
		dve.addAlias("OI", "orderItemSeqId");
		dve.addAlias("OI", "quantity");
		dve.addAlias("OI", "productId");
		dve.addAlias("OI", "itemDescription");
		dve.addAlias("OI", "isRemoveFromSchedule");
		dve.addAlias("OI", "isRushProduction");
		dve.addAlias("OI", "itemDueDate", "dueDate", null, null, null, null);
		dve.addAlias("OI", "stockPicked");
		dve.addAlias("OI", "savePrintSample");
		dve.addMemberEntity("P", "Product");
		dve.addViewLink("OI", "P", false, ModelKeyMap.makeKeyMapList("productId", "productId"));
		dve.addMemberEntity("PFA", "ProductFeatureAppl");
		dve.addAlias("PFA", "productFeatureId");
		dve.addViewLink("P", "PFA", false, ModelKeyMap.makeKeyMapList("productId", "productId"));
		dve.addMemberEntity("PF", "ProductFeature");
		dve.addAlias("PF", "productFeatureTypeId");
		dve.addAlias("PF", "colorDescription", "description", null, null, null, null);
		dve.addViewLink("PFA", "PF", false, ModelKeyMap.makeKeyMapList("productFeatureId", "productFeatureId"));
		dve.addMemberEntity("OIA", "OrderItemArtwork");
		dve.addAlias("OIA", "printPlateId");
		dve.addAlias("OIA", "frontInkColor1");
		dve.addAlias("OIA", "frontInkColor2");
		dve.addAlias("OIA", "frontInkColor3");
		dve.addAlias("OIA", "frontInkColor4");
		dve.addAlias("OIA", "backInkColor1");
		dve.addAlias("OIA", "backInkColor2");
		dve.addAlias("OIA", "backInkColor3");
		dve.addAlias("OIA", "backInkColor4");
		dve.addAlias("OIA", "itemPrintPosition");
		dve.addAlias("OIA", "itemCustomerComments");
		dve.addAlias("OIA", "itemPrepressComments");
		dve.addAlias("OIA", "itemPressmanComments");
		dve.addAlias("OIA", "inkPicked");
		dve.addViewLink("OI", "OIA", false, ModelKeyMap.makeKeyMapList("orderId", "orderId", "orderItemSeqId", "orderItemSeqId"));
		dve.addMemberEntity("PP", "PrintPlate");
		dve.addAlias("PP", "dueDate");
		dve.addAlias("PP", "statusId");
		dve.addAlias("PP", "printPressId");
		dve.addViewLink("OIA", "PP", false, ModelKeyMap.makeKeyMapList("printPlateId", "printPlateId"));
		dve.addMemberEntity("OISG", "OrderItemShipGroup");
		dve.addAlias("OISG", "shipGroupSeqId");
		dve.addViewLink("OI", "OISG", false, ModelKeyMap.makeKeyMapList("orderId", "orderId"));
		dve.addMemberEntity("SMT", "ShipmentMethodType");
		dve.addAlias("SMT", "shipmentMethodTypeId");
		dve.addAlias("SMT", "genericDescription");
		dve.addViewLink("OISG", "SMT", false, ModelKeyMap.makeKeyMapList("shipmentMethodTypeId", "shipmentMethodTypeId"));

		EntityListIterator eli = null;
		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try {
				eli = delegator.findListIteratorByCondition(dve, EntityCondition.makeCondition(conditionList), null, null, orderBy, null);
				GenericValue plateGV = null;
				String plateId = "";
				List<Map<String, Object>> plate = new ArrayList<>();
				while ((plateGV = eli.next()) != null) {
					String currentPlateId = plateGV.getString("printPlateId");
					Map<String, Object> orderItem = new HashMap<>();
					orderItem.put("plateNumber", currentPlateId);
					orderItem.put("orderNumber", plateGV.getString("orderId"));
					orderItem.put("orderItemSeqId", plateGV.getString("orderItemSeqId"));
					orderItem.put("quantity", plateGV.getBigDecimal("quantity"));
					orderItem.put("sku", plateGV.getString("productId"));
					orderItem.put("ink", getInkDetails(plateGV));
					orderItem.put("savePrintSample", plateGV.getString("savePrintSample"));
					String itemDescription = plateGV.getString("itemDescription");
					String colorDescription = plateGV.getString("colorDescription");
					orderItem.put("item", UtilValidate.isNotEmpty(itemDescription) && !itemDescription.contains(colorDescription) ? itemDescription + " - " + colorDescription : itemDescription);
					orderItem.put("customerComments", plateGV.getString("itemCustomerComments"));
					orderItem.put("prepressComments", plateGV.getString("itemPrepressComments"));
					orderItem.put("pressmanComments", plateGV.getString("itemPressmanComments"));
					orderItem.put("position", plateGV.getString("itemPrintPosition"));
					orderItem.put("name", plateGV.getString("orderId") + "_" + plateGV.getString("orderItemSeqId"));
					orderItem.put("dueDate", UtilValidate.isNotEmpty(plateGV.getTimestamp("dueDate")) ? mdy.format(plateGV.getTimestamp("dueDate")) : "");
					orderItem.put("productionTime", plateGV.getString("isRushProduction"));
					orderItem.put("itemDueDate", UtilValidate.isNotEmpty(plateGV.getTimestamp("itemDueDate")) ? mdy.format(plateGV.getTimestamp("itemDueDate")) : "");
					orderItem.put("shipping", plateGV.getString("genericDescription"));
					orderItem.put("content", OrderHelper.getOrderItemContent(delegator, plateGV.getString("orderId"), plateGV.getString("orderItemSeqId")));
					orderItem.put("timeWarning", (UtilValidate.isNotEmpty(plateGV.getTimestamp("dueDate")) && UtilValidate.isNotEmpty(plateGV.getTimestamp("itemDueDate")) && plateGV.getTimestamp("dueDate").compareTo(plateGV.getTimestamp("itemDueDate")) < 0) ? "Y" : "N");
					orderItem.put("stockPicked", plateGV.getString("stockPicked"));
					orderItem.put("inkPicked", plateGV.getString("inkPicked"));

					if(!plateGV.getString("printPlateId").equals(plateId) && !plate.isEmpty()) {
						openPlates.add(plate);
						plate = new ArrayList<>();
					}

					plateId = currentPlateId;
					plate.add(orderItem);
				}

				if(!plate.isEmpty()) {
					openPlates.add(plate);
				}
			} catch (GenericEntityException e1) {
				TransactionUtil.rollback(beganTransaction, "Error looking up open plates.", e1);
				EnvUtil.reportError(e1);
			} finally {
				if (eli != null) {
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
		/*Collections.sort(openPlates, new Comparator<List<Map<String, Object>>>() {
			@Override
			public int compare(List<Map<String, Object>> o1, List<Map<String, Object>> o2) {
			try {
				return (mdy.parse((String)o1.get(0).get("itemDueDate"))).compareTo(mdy.parse((String)o2.get(0).get("itemDueDate")));
			} catch (ParseException e) {
				EnvUtil.reportError(e);
				return 0;
			}
			}
		});*/

		return openPlates;
	}

	public static List<List<Map<String, Object>>> getPrintedJobs(Delegator delegator, GenericValue userLogin, String printPressId, int minutes) throws GenericEntityException {
		List<List<Map<String, Object>>> openPlates = new ArrayList<>();
		if (UtilValidate.isEmpty(userLogin)) {
			return null;
		}

		List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
		conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.IN, Arrays.asList("ART_PRINTED")));
		conditionList.add(EntityCondition.makeCondition("productFeatureTypeId", EntityOperator.EQUALS, "COLOR"));
		conditionList.add(EntityCondition.makeCondition("shipGroupSeqId", EntityOperator.EQUALS, "00001"));
		conditionList.add(EntityCondition.makeCondition("lastUpdatedStamp", EntityOperator.GREATER_THAN_EQUAL_TO, EnvUtil.getNMinBeforeOrAfterNow(minutes)));
		if(printPressId != null) {
			conditionList.add(EntityCondition.makeCondition("printPressId", EntityOperator.EQUALS, printPressId));
		}

		List<String> orderBy = new ArrayList<>();
		orderBy.add("dueDate");
		orderBy.add("printPlateId");
		//orderBy.add("orderId");
		//orderBy.add("orderItemSeqId");

		DynamicViewEntity dve = new DynamicViewEntity();
		dve.addMemberEntity("OI", "OrderItem");
		dve.addAlias("OI", "orderId");
		dve.addAlias("OI", "statusId");
		dve.addAlias("OI", "orderItemSeqId");
		dve.addAlias("OI", "quantity");
		dve.addAlias("OI", "productId");
		dve.addAlias("OI", "itemDescription");
		dve.addAlias("OI", "isRemoveFromSchedule");
		dve.addAlias("OI", "isRushProduction");
		dve.addAlias("OI", "itemDueDate", "dueDate", null, null, null, null);
		dve.addAlias("OI", "lastUpdatedStamp");
		dve.addAlias("OI", "savePrintSample");
		dve.addMemberEntity("P", "Product");
		dve.addViewLink("OI", "P", false, ModelKeyMap.makeKeyMapList("productId", "productId"));
		dve.addMemberEntity("PFA", "ProductFeatureAppl");
		dve.addAlias("PFA", "productFeatureId");
		dve.addViewLink("P", "PFA", false, ModelKeyMap.makeKeyMapList("productId", "productId"));
		dve.addMemberEntity("PF", "ProductFeature");
		dve.addAlias("PF", "productFeatureTypeId");
		dve.addAlias("PF", "colorDescription", "description", null, null, null, null);
		dve.addViewLink("PFA", "PF", false, ModelKeyMap.makeKeyMapList("productFeatureId", "productFeatureId"));
		dve.addMemberEntity("OIA", "OrderItemArtwork");
		dve.addAlias("OIA", "printPlateId");
		dve.addAlias("OIA", "frontInkColor1");
		dve.addAlias("OIA", "frontInkColor2");
		dve.addAlias("OIA", "frontInkColor3");
		dve.addAlias("OIA", "frontInkColor4");
		dve.addAlias("OIA", "backInkColor1");
		dve.addAlias("OIA", "backInkColor2");
		dve.addAlias("OIA", "backInkColor3");
		dve.addAlias("OIA", "backInkColor4");
		dve.addAlias("OIA", "itemPrintPosition");
		dve.addAlias("OIA", "itemCustomerComments");
		dve.addAlias("OIA", "itemPrepressComments");
		dve.addAlias("OIA", "itemPressmanComments");
		dve.addViewLink("OI", "OIA", false, ModelKeyMap.makeKeyMapList("orderId", "orderId", "orderItemSeqId", "orderItemSeqId"));
		dve.addMemberEntity("PP", "PrintPlate");
		dve.addAlias("PP", "dueDate");
		dve.addAlias("PP", "printPressId");
		dve.addViewLink("OIA", "PP", false, ModelKeyMap.makeKeyMapList("printPlateId", "printPlateId"));
		dve.addMemberEntity("OISG", "OrderItemShipGroup");
		dve.addAlias("OISG", "shipGroupSeqId");
		dve.addViewLink("OI", "OISG", false, ModelKeyMap.makeKeyMapList("orderId", "orderId"));
		dve.addMemberEntity("SMT", "ShipmentMethodType");
		dve.addAlias("SMT", "shipmentMethodTypeId");
		dve.addAlias("SMT", "genericDescription");
		dve.addViewLink("OISG", "SMT", false, ModelKeyMap.makeKeyMapList("shipmentMethodTypeId", "shipmentMethodTypeId"));

		EntityListIterator eli = null;
		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try {
				eli = delegator.findListIteratorByCondition(dve, EntityCondition.makeCondition(conditionList), null, null, orderBy, null);
				GenericValue plateGV = null;
				String plateId = "";
				List<Map<String, Object>> plate = new ArrayList<>();
				while ((plateGV = eli.next()) != null) {
					String currentPlateId = plateGV.getString("printPlateId");
					Map<String, Object> orderItem = new HashMap<>();
					orderItem.put("plateNumber", currentPlateId);
					orderItem.put("orderNumber", plateGV.getString("orderId"));
					orderItem.put("orderItemSeqId", plateGV.getString("orderItemSeqId"));
					orderItem.put("quantity", plateGV.getBigDecimal("quantity"));
					orderItem.put("sku", plateGV.getString("productId"));
					orderItem.put("ink", getInkDetails(plateGV));
					orderItem.put("savePrintSample", plateGV.getString("savePrintSample"));
					String itemDescription = plateGV.getString("itemDescription");
					String colorDescription = plateGV.getString("colorDescription");
					orderItem.put("item", UtilValidate.isNotEmpty(itemDescription) && !itemDescription.contains(colorDescription) ? itemDescription + " - " + colorDescription : itemDescription);
					orderItem.put("customerComments", plateGV.getString("itemCustomerComments"));
					orderItem.put("prepressComments", plateGV.getString("itemPrepressComments"));
					orderItem.put("pressmanComments", plateGV.getString("itemPressmanComments"));
					orderItem.put("position", plateGV.getString("itemPrintPosition"));
					orderItem.put("name", plateGV.getString("orderId") + "_" + plateGV.getString("orderItemSeqId"));
					orderItem.put("dueDate", UtilValidate.isNotEmpty(plateGV.getTimestamp("dueDate")) ? mdy.format(plateGV.getTimestamp("dueDate")) : "");
					orderItem.put("productionTime", plateGV.getString("isRushProduction"));
					orderItem.put("itemDueDate", UtilValidate.isNotEmpty(plateGV.getTimestamp("itemDueDate")) ? mdy.format(plateGV.getTimestamp("itemDueDate")) : "");
					orderItem.put("shipping", plateGV.getString("genericDescription"));
					orderItem.put("content", OrderHelper.getOrderItemContent(delegator, plateGV.getString("orderId"), plateGV.getString("orderItemSeqId")));
					orderItem.put("timeWarning", (UtilValidate.isNotEmpty(plateGV.getTimestamp("dueDate")) && UtilValidate.isNotEmpty(plateGV.getTimestamp("itemDueDate")) && plateGV.getTimestamp("dueDate").compareTo(plateGV.getTimestamp("itemDueDate")) < 0) ? "Y" : "N");

					if(!plateGV.getString("printPlateId").equals(plateId) && !plate.isEmpty()) {
						openPlates.add(plate);
						plate = new ArrayList<>();
					}

					plateId = currentPlateId;
					plate.add(orderItem);
				}

				if(!plate.isEmpty()) {
					openPlates.add(plate);
				}
			} catch (GenericEntityException e1) {
				TransactionUtil.rollback(beganTransaction, "Error looking up open plates.", e1);
				EnvUtil.reportError(e1);
			} finally {
				if (eli != null) {
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
		/*Collections.sort(openPlates, new Comparator<List<Map<String, Object>>>() {
			@Override
			public int compare(List<Map<String, Object>> o1, List<Map<String, Object>> o2) {
			try {
				return (mdy.parse((String)o1.get(0).get("itemDueDate"))).compareTo(mdy.parse((String)o2.get(0).get("itemDueDate")));
			} catch (ParseException e) {
				EnvUtil.reportError(e);
				return 0;
			}
			}
		});*/

		return openPlates;
	}

	public static List<Map<String, Object>> getPlateDetails(Delegator delegator, GenericValue userLogin, String plateId) throws GenericEntityException {
		if (UtilValidate.isEmpty(userLogin) || UtilValidate.isEmpty(plateId)) {
			return null;
		}

		List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
		conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.IN, Arrays.asList("PP_LAYOUT_COMPLETE")));
		conditionList.add(EntityCondition.makeCondition("printPlateId", EntityOperator.EQUALS, plateId));
		conditionList.add(EntityCondition.makeCondition("productFeatureTypeId", EntityOperator.EQUALS, "COLOR"));

		List<String> orderBy = new ArrayList<>();
		orderBy.add("orderId");
		orderBy.add("orderItemSeqId");

		DynamicViewEntity dve = new DynamicViewEntity();
		dve.addMemberEntity("OI", "OrderItem");
		dve.addAlias("OI", "orderId");
		dve.addAlias("OI", "orderItemSeqId");
		dve.addAlias("OI", "quantity");
		dve.addAlias("OI", "productId");
		dve.addAlias("OI", "itemDescription");
		dve.addAlias("OI", "isRemoveFromSchedule");
		dve.addAlias("OI", "createdStamp");
		dve.addAlias("OI", "isRushProduction");
		dve.addAlias("OI", "itemDueDate", "dueDate", null, null, null, null);
		dve.addMemberEntity("P", "Product");
		dve.addAlias("P", "binLocation");
		dve.addViewLink("OI", "P", false, ModelKeyMap.makeKeyMapList("productId", "productId"));
		dve.addMemberEntity("PFA", "ProductFeatureAppl");
		dve.addAlias("PFA", "productFeatureId");
		dve.addViewLink("P", "PFA", false, ModelKeyMap.makeKeyMapList("productId", "productId"));
		dve.addMemberEntity("PF", "ProductFeature");
		dve.addAlias("PF", "productFeatureTypeId");
		dve.addAlias("PF", "colorDescription", "description", null, null, null, null);
		dve.addViewLink("PFA", "PF", false, ModelKeyMap.makeKeyMapList("productFeatureId", "productFeatureId"));
		dve.addMemberEntity("OIA", "OrderItemArtwork");
		dve.addAlias("OIA", "printPlateId");
		dve.addAlias("OIA", "frontInkColor1");
		dve.addAlias("OIA", "frontInkColor2");
		dve.addAlias("OIA", "frontInkColor3");
		dve.addAlias("OIA", "frontInkColor4");
		dve.addAlias("OIA", "backInkColor1");
		dve.addAlias("OIA", "backInkColor2");
		dve.addAlias("OIA", "backInkColor3");
		dve.addAlias("OIA", "backInkColor4");
		dve.addAlias("OIA", "itemPrintPosition");
		dve.addAlias("OIA", "itemCustomerComments");
		dve.addAlias("OIA", "itemPrepressComments");
		dve.addAlias("OIA", "itemPressmanComments");
		dve.addViewLink("OI", "OIA", false, ModelKeyMap.makeKeyMapList("orderId", "orderId", "orderItemSeqId", "orderItemSeqId"));
		dve.addMemberEntity("PP", "PrintPlate");
		dve.addAlias("PP", "dueDate");
		dve.addAlias("PP", "statusId");
		dve.addAlias("PP", "printPressId");
		dve.addAlias("PP", "comment");
		dve.addViewLink("OIA", "PP", false, ModelKeyMap.makeKeyMapList("printPlateId", "printPlateId"));
		List<Map<String, Object>> plate = new ArrayList<>();
		EntityListIterator eli = null;
		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try {
				eli = delegator.findListIteratorByCondition(dve, EntityCondition.makeCondition(conditionList, EntityOperator.AND), null, null, orderBy, null);
				GenericValue plateGV = null;

				while ((plateGV = eli.next()) != null) {
					Map<String, Object> orderItem = new HashMap<>();
					orderItem.put("plateNumber", plateGV.getString("printPlateId"));
					orderItem.put("orderNumber", plateGV.getString("orderId"));
					orderItem.put("orderDate", UtilValidate.isNotEmpty(plateGV.getTimestamp("createdStamp")) ? mdy.format(plateGV.getTimestamp("createdStamp")) : "");
					orderItem.put("rushProduction", UtilValidate.isNotEmpty(plateGV.getString("isRushProduction")) && plateGV.getString("isRushProduction").equals("Y"));
					orderItem.put("orderItemSeqId", plateGV.getString("orderItemSeqId"));
					orderItem.put("quantity", plateGV.getBigDecimal("quantity"));
					orderItem.put("sku", plateGV.getString("productId"));
					orderItem.put("bin", plateGV.getString("binLocation"));
					orderItem.put("ink", getInkDetails(plateGV));
					orderItem.put("inkList", getInkList(plateGV));
					String itemDescription = plateGV.getString("itemDescription");
					String colorDescription = plateGV.getString("colorDescription");
					orderItem.put("item", UtilValidate.isNotEmpty(itemDescription) && !itemDescription.contains(colorDescription) ? itemDescription + " - " + colorDescription : itemDescription);
					orderItem.put("customerComments", plateGV.getString("itemCustomerComments"));
					orderItem.put("prepressComments", plateGV.getString("itemPrepressComments"));
					orderItem.put("pressmanComments", plateGV.getString("itemPressmanComments"));
					orderItem.put("position", plateGV.getString("itemPrintPosition"));
					orderItem.put("name", plateGV.getString("orderId") + "_" + plateGV.getString("orderItemSeqId"));
					orderItem.put("pressNum", plateGV.getString("printPressId"));
					orderItem.put("dueDate", UtilValidate.isNotEmpty(plateGV.getTimestamp("dueDate")) ? mdy.format(plateGV.getTimestamp("dueDate")) : "");
					orderItem.put("dueDateYMD", UtilValidate.isNotEmpty(plateGV.getTimestamp("dueDate")) ? ymd.format(plateGV.getTimestamp("dueDate")) : "");
					orderItem.put("itemDueDate", UtilValidate.isNotEmpty(plateGV.getTimestamp("itemDueDate")) ? mdy.format(plateGV.getTimestamp("itemDueDate")) : "");
					orderItem.put("itemDueDateYMD", UtilValidate.isNotEmpty(plateGV.getTimestamp("itemDueDate")) ? ymd.format(plateGV.getTimestamp("itemDueDate")) : "");
					orderItem.put("statusId", plateGV.getString("statusId"));
					orderItem.put("comment", plateGV.getString("comment"));
					plate.add(orderItem);
				}
			} catch (GenericEntityException e1) {
				TransactionUtil.rollback(beganTransaction, "Error looking up shipping and billing addresses", e1);
				EnvUtil.reportError(e1);
			} finally {
				if (eli != null) {
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
		return plate;
	}

	public static GenericValue createOrUpdatePlate(Delegator delegator, LocalDispatcher dispatcher, String printPlateId, Map<String, Object> context, GenericValue userLogin) throws GenericEntityException, Exception {
		GenericValue printPlate = null;
		if(UtilValidate.isEmpty(printPlateId)) {
			printPlateId = getNextSeqPlateId(delegator, (String) context.get("printPressId"));
			printPlate = delegator.makeValue("PrintPlate", UtilMisc.toMap("printPlateId", printPlateId));
			printPlate.put("printPressId", (String) context.get("printPressId"));

			if(UtilValidate.isNotEmpty(context.get("schedulePrintPressId"))) {
				printPlate.put("schedulePrintPressId", (String) context.get("schedulePrintPressId"));
			} else {
				printPlate.put("schedulePrintPressId", (String) context.get("printPressId"));
			}

			printPlate.put("dueDate", Timestamp.valueOf((String) context.get("dueDate") + " 00:00:00.0"));
			printPlate.put("comment", (String) context.get("comment"));
			printPlate.put("statusId", (String) context.get("statusId"));
			printPlate.create();
		} else {
			printPlate = delegator.findOne("PrintPlate", UtilMisc.toMap("printPlateId", printPlateId), false);
			printPlate = EnvUtil.insertGenericValueData(delegator, printPlate, context);

			/*
			// when switching to a different press, cancel the old one and create a new plate.
			if (!printPlate.getString("printPressId").equals((String) context.get("printPressId"))) {
				cancelPlate(delegator, dispatcher, (String) context.get("printPlateId"), userLogin);
				printPlateId = getNextSeqPlateId(delegator, (String) context.get("printPressId"));
				printPlate = delegator.makeValue("PrintPlate", UtilMisc.toMap("printPlateId", printPlateId, "printPressId", (String) context.get("printPressId")));
				printPlate.create();
			}

			printPlate.put("printPressId", (String) context.get("printPressId"));
			printPlate.put("dueDate", Timestamp.valueOf((String) context.get("dueDate") + " 00:00:00.0"));
			printPlate.put("comment", (String) context.get("comment"));
			printPlate.put("statusId", (String) context.get("statusId"));
			*/
			printPlate.store();
		}

		return printPlate;
	}

	public static List<Map<String, Object>> pressmanStats(HttpServletRequest request) throws GenericEntityException, ParseException  {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		if(UtilValidate.isEmpty(context.get("start"))) {
			context.put("start", UtilDateTime.getDayStart(UtilDateTime.nowTimestamp()));
		} else {
			context.put("start", EnvUtil.convertStringToTimestamp((String) context.get("start") + " 00:00:00.0"));
		}
		if(UtilValidate.isEmpty(context.get("end"))) {
			context.put("end", UtilDateTime.getDayEnd(UtilDateTime.nowTimestamp()));
		} else {
			context.put("end", EnvUtil.convertStringToTimestamp((String) context.get("end") + " 23:59:00.0"));
		}

		List<Map<String, Object>> pressManList = new ArrayList<>();

		DynamicViewEntity dve = new DynamicViewEntity();
		dve.addMemberEntity("OI", "OrderItem");
		dve.addAlias("OI", "orderId");
		dve.addAlias("OI", "orderItemSeqId");
		dve.addAlias("OI", "quantity");
		dve.addMemberEntity("OIA", "OrderItemArtwork");
		dve.addViewLink("OI", "OIA", false, ModelKeyMap.makeKeyMapList("orderId", "orderId", "orderItemSeqId", "orderItemSeqId"));
		dve.addAlias("OIA", "frontInkColor1");
		dve.addAlias("OIA", "frontInkColor2");
		dve.addAlias("OIA", "frontInkColor3");
		dve.addAlias("OIA", "frontInkColor4");
		dve.addAlias("OIA", "backInkColor1");
		dve.addAlias("OIA", "backInkColor2");
		dve.addAlias("OIA", "backInkColor3");
		dve.addAlias("OIA", "backInkColor4");
		dve.addAlias("OIA", "pressMan");
		dve.addAlias("OIA", "completionTime");
		dve.addAlias("OIA", "approvalTime");
		dve.addAlias("OIA", "jobStartTime");
		dve.addAlias("OIA", "jobFinishTime");
		dve.addAlias("OIA", "approvalStartTime");
		dve.addAlias("OIA", "approvalFinishTime");
		dve.addMemberEntity("OS", "OrderStatus");
		dve.addViewLink("OIA", "OS", false, ModelKeyMap.makeKeyMapList("orderId", "orderId", "orderItemSeqId", "orderItemSeqId"));
		dve.addAlias("OS", "statusId");
		dve.addAlias("OS", "printDate", "statusDatetime", null, null, null, null);

		List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
		conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "ART_PRINTED"));
		conditionList.add(EntityCondition.makeCondition("pressMan", EntityOperator.NOT_EQUAL, null));
		conditionList.add(EntityCondition.makeCondition("printDate", EntityOperator.GREATER_THAN_EQUAL_TO, (Timestamp) context.get("start")));
		conditionList.add(EntityCondition.makeCondition("printDate", EntityOperator.LESS_THAN_EQUAL_TO, (Timestamp) context.get("end")));

		List<String> orderBy = new ArrayList<>();
		orderBy.add("pressMan");

		EntityListIterator eli = null;
		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try {
				long numOfDays = EnvUtil.getDaysBetweenDates((Timestamp) context.get("start"), (Timestamp) context.get("end")) + 1; //getDaysBetweenDates returns 0 if its the same day, so for our purpose, we need to treat this as 1 for same day
				List<String> processedOrders = new ArrayList<>();
				Map<String, Object> pressMan = new LinkedHashMap<>();
				String previousPressMan = "";
				List<String> inkColors = new ArrayList<>();
				List<Long> time = new ArrayList<>();
				eli = delegator.findListIteratorByCondition(dve, EntityCondition.makeCondition(conditionList), null, null, orderBy, null);
				GenericValue orderGV = null;
				while((orderGV = eli.next()) != null) {
					if(!previousPressMan.equals("") && !previousPressMan.equals(orderGV.getString("pressMan"))) {
						pressMan.put("totalTime", EnvUtil.averageTime(time, numOfDays));
						pressMan.put("inkColors", inkColors.size());
						pressManList.add(pressMan);

						//clear for reuse
						pressMan = new LinkedHashMap<>();
						time = new ArrayList<>();
						inkColors = new ArrayList<>();
					}

					if(!processedOrders.contains(orderGV.getString("orderId") + "_" + orderGV.getString("orderItemSeqId"))) {
						//pressMan name
						pressMan.put("pressMan", orderGV.getString("pressMan"));

						//quantity
						if(pressMan.containsKey("quantity")) {
							pressMan.put("quantity", ((BigDecimal) pressMan.get("quantity")).add(orderGV.getBigDecimal("quantity")));
						} else {
							pressMan.put("quantity", orderGV.getBigDecimal("quantity"));
						}

						//line items
						if(pressMan.containsKey("orderItems")) {
							pressMan.put("orderItems", ((BigDecimal) pressMan.get("orderItems")).add(BigDecimal.ONE));
						} else {
							pressMan.put("orderItems", BigDecimal.ONE);
						}

						//ink colors
						if(UtilValidate.isNotEmpty(orderGV.getString("frontInkColor1")) && !inkColors.contains(orderGV.getString("frontInkColor1"))) {
							inkColors.add(orderGV.getString("frontInkColor1"));
						}
						if(UtilValidate.isNotEmpty(orderGV.getString("frontInkColor2")) && !inkColors.contains(orderGV.getString("frontInkColor2"))) {
							inkColors.add(orderGV.getString("frontInkColor2"));
						}
						if(UtilValidate.isNotEmpty(orderGV.getString("frontInkColor3")) && !inkColors.contains(orderGV.getString("frontInkColor3"))) {
							inkColors.add(orderGV.getString("frontInkColor3"));
						}
						if(UtilValidate.isNotEmpty(orderGV.getString("frontInkColor4")) && !inkColors.contains(orderGV.getString("frontInkColor4"))) {
							inkColors.add(orderGV.getString("frontInkColor4"));
						}
						if(UtilValidate.isNotEmpty(orderGV.getString("backInkColor1")) && !inkColors.contains(orderGV.getString("backInkColor1"))) {
							inkColors.add(orderGV.getString("backInkColor1"));
						}
						if(UtilValidate.isNotEmpty(orderGV.getString("backInkColor2")) && !inkColors.contains(orderGV.getString("backInkColor2"))) {
							inkColors.add(orderGV.getString("backInkColor2"));
						}
						if(UtilValidate.isNotEmpty(orderGV.getString("backInkColor3")) && !inkColors.contains(orderGV.getString("backInkColor3"))) {
							inkColors.add(orderGV.getString("backInkColor3"));
						}
						if(UtilValidate.isNotEmpty(orderGV.getString("backInkColor4")) && !inkColors.contains(orderGV.getString("backInkColor4"))) {
							inkColors.add(orderGV.getString("backInkColor4"));
						}

						//total time
						if(UtilValidate.isNotEmpty(orderGV.getTimestamp("jobStartTime")) && UtilValidate.isNotEmpty(orderGV.getTimestamp("jobFinishTime"))) {
							time.add(EnvUtil.getTimeBetweenDates(orderGV.getTimestamp("jobStartTime"), orderGV.getTimestamp("jobFinishTime")));
						}
						if(UtilValidate.isNotEmpty(orderGV.getTimestamp("approvalStartTime")) && UtilValidate.isNotEmpty(orderGV.getTimestamp("approvalFinishTime"))) {
							time.add(EnvUtil.getTimeBetweenDates(orderGV.getTimestamp("approvalStartTime"), orderGV.getTimestamp("approvalFinishTime")));
						}
					}

					previousPressMan = orderGV.getString("pressMan");
					processedOrders.add(orderGV.getString("orderId") + "_" + orderGV.getString("orderItemSeqId"));
				}

				if(UtilValidate.isNotEmpty(pressMan)) {
					pressMan.put("totalTime", EnvUtil.averageTime(time, numOfDays));
					pressMan.put("inkColors", inkColors.size());
					pressManList.add(pressMan);
				}
			} catch (Exception e1) {
				TransactionUtil.rollback(beganTransaction, "Error looking pressman stats", e1);
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

		return pressManList;
	}

	protected static String getInkDetails(GenericValue genericValue) {
		StringBuilder inkDetails = new StringBuilder();
		if(UtilValidate.isNotEmpty(genericValue.getString("frontInkColor1"))) {
		   inkDetails.append("Front:").append(genericValue.getString("frontInkColor1"));
			if(UtilValidate.isNotEmpty(genericValue.getString("frontInkColor4"))) {
				inkDetails.append(", ").append(genericValue.getString("frontInkColor2")).append(", ").append(genericValue.getString("frontInkColor3")).append(" & ").append(genericValue.getString("frontInkColor4"));
			} else if(UtilValidate.isNotEmpty(genericValue.getString("frontInkColor3"))) {
				inkDetails.append(", ").append(genericValue.getString("frontInkColor2")).append(" & ").append(genericValue.getString("frontInkColor3"));
			} else if(UtilValidate.isNotEmpty(genericValue.getString("frontInkColor2"))) {
				inkDetails.append(" & ").append(genericValue.getString("frontInkColor2"));
			}
			inkDetails.append("; ");
		}

		if(UtilValidate.isNotEmpty(genericValue.getString("backInkColor1"))) {
			inkDetails.append("Back:").append(genericValue.getString("backInkColor1"));
			if(UtilValidate.isNotEmpty(genericValue.getString("backInkColor4"))) {
				inkDetails.append(", ").append(genericValue.getString("backInkColor2")).append(", ").append(genericValue.getString("backInkColor3")).append(" & ").append(genericValue.getString("backInkColor4"));
			} else if(UtilValidate.isNotEmpty(genericValue.getString("backInkColor3"))) {
				inkDetails.append(", ").append(genericValue.getString("backInkColor2")).append(" & ").append(genericValue.getString("backInkColor3"));
			} else if(UtilValidate.isNotEmpty(genericValue.getString("backInkColor2"))) {
				inkDetails.append(" & ").append(genericValue.getString("backInkColor2"));
			}
		}

		return inkDetails.toString();
	}

	protected static List<String> getInkList(GenericValue genericValue) {
		List<String> inks = new ArrayList<>();
		if (UtilValidate.isNotEmpty(genericValue.getString("frontInkColor1")) && !inks.contains(genericValue.getString("frontInkColor1"))) {
			inks.add(genericValue.getString("frontInkColor1"));
			if (UtilValidate.isNotEmpty(genericValue.getString("frontInkColor2")) && !inks.contains(genericValue.getString("frontInkColor2"))) {
				inks.add(genericValue.getString("frontInkColor2"));
				if (UtilValidate.isNotEmpty(genericValue.getString("frontInkColor3")) && !inks.contains(genericValue.getString("frontInkColor3"))) {
					inks.add(genericValue.getString("frontInkColor3"));
					if (UtilValidate.isNotEmpty(genericValue.getString("frontInkColor4")) && !inks.contains(genericValue.getString("frontInkColor4"))) {
						inks.add(genericValue.getString("frontInkColor4"));
					}
				}
			}
		}
		if (UtilValidate.isNotEmpty(genericValue.getString("backInkColor1")) && !inks.contains(genericValue.getString("backInkColor1"))) {
			inks.add(genericValue.getString("backInkColor1"));
			if (UtilValidate.isNotEmpty(genericValue.getString("backInkColor2")) && !inks.contains(genericValue.getString("backInkColor2"))) {
				inks.add(genericValue.getString("backInkColor2"));
				if (UtilValidate.isNotEmpty(genericValue.getString("backInkColor3")) && !inks.contains(genericValue.getString("backInkColor3"))) {
					inks.add(genericValue.getString("backInkColor3"));
					if (UtilValidate.isNotEmpty(genericValue.getString("backInkColor4")) && !inks.contains(genericValue.getString("backInkColor4"))) {
						inks.add(genericValue.getString("backInkColor4"));
					}
				}
			}
		}

		return inks;
	}

	protected static String getNextSeqPlateId(Delegator delegator, String printPressId) throws Exception {
		String printPlateId = "";
		GenericValue printPress = delegator.findOne("PrintPress", UtilMisc.toMap("printPressId", printPressId), false);
		if(printPress != null){
			// generate print plate id
			Long plateSeqId = printPress.getLong("plateSequence");
			String s = String.format("%04d", plateSeqId);
			printPlateId = printPressId+"-"+s;

			// update plateSequence in PrintPress
			Long nextPlateSeq = plateSeqId + 1;
			printPress.put("plateSequence", nextPlateSeq);
			printPress.store();
		}
		return printPlateId;
    }

    public static Map<String, String> getPressList() {
    	return PRESS_IDS;
	}
}