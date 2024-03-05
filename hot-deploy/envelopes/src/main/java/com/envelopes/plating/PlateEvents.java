/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.plating;

import java.io.*;
import java.net.*;
import java.lang.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.text.SimpleDateFormat;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.GeneralException;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilHttp;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntity;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityConditionList;
import org.apache.ofbiz.entity.condition.EntityExpr;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.transaction.GenericTransactionException;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.entity.util.EntityFindOptions;
import org.apache.ofbiz.entity.util.EntityListIterator;
import org.apache.ofbiz.entity.util.EntityTypeUtil;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileUploadException;

import org.w3c.dom.Document;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.envelopes.order.OrderHelper;
import com.envelopes.product.ProductHelper;
import com.envelopes.http.FileHelper;
import com.envelopes.util.*;

public class PlateEvents {
	public static final String module = PlateEvents.class.getName();

	/*
	 * Find the plate of a given order
	 */
	public static String getOrderPlate(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		boolean success = false;

		GenericValue orderItemArtwork = null;
		GenericValue orderItem = null;
		if(UtilValidate.isNotEmpty(context.get("order"))) {
			try {
				String orderId = ((String) context.get("order")).substring(0, ((String) context.get("order")).indexOf("_"));
				String orderItemSeqId = ((String) context.get("order")).substring(((String) context.get("order")).indexOf("_") + 1);
				orderItemArtwork = OrderHelper.getOrderItemArtwork(delegator, orderId, orderItemSeqId);
				orderItem = delegator.findOne("OrderItem", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), false);
				if(orderItemArtwork != null && UtilValidate.isNotEmpty(orderItemArtwork.getString("printPlateId"))) {
					jsonResponse.put("plateId", orderItemArtwork.getString("printPlateId"));
					jsonResponse.put("pressId", delegator.findOne("PrintPlate", UtilMisc.toMap("printPlateId", orderItemArtwork.getString("printPlateId")), false).getString("printPressId"));
					jsonResponse.put("orderId", orderId);
					jsonResponse.put("orderItemSeqId", orderItemSeqId);
					jsonResponse.put("isRemoveFromSchedule", orderItem.getString("isRemoveFromSchedule"));
					success = true;
				}
			} catch(GenericEntityException e) {
				EnvUtil.reportError(e);
				Debug.logError("Error trying to fetch order item artwork. " + e + " : " + e.getMessage(), module);
			}
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Process an XML sent from Switch
	 */
	public static String processPlateXML(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", true);

		if(EnvUtil.authorizedSwitchRequest((String) context.get("key"))) {
			boolean beganTransaction = false;
			try {
				beganTransaction = TransactionUtil.begin();
				try {
					List<Document> xmls = new ArrayList<Document>();
					Map<String, Object> fileData = FileHelper.uploadFile(request, null, true, true);
					if(UtilValidate.isNotEmpty(fileData) && UtilValidate.isNotEmpty(fileData.get("files"))) {
						List<Map> files = (List<Map>) fileData.get("files");
						for(Map file : files) {
							Iterator iter = file.entrySet().iterator();
							while(iter.hasNext()) {
								Map.Entry pairs = (Map.Entry) iter.next();
								if(((String) pairs.getKey()).equals("data") && UtilValidate.isNotEmpty((String) pairs.getValue())) {
									xmls.add(EnvUtil.stringToXML((String) pairs.getValue()));
								}
							}
						}
					}

					for(Document xml : xmls) {
						GenericValue printPlate = PlateHelper.createPrintPlate(delegator, dispatcher, xml);
					}
				} catch (FileUploadException e) {
					TransactionUtil.rollback(beganTransaction, "Error while trying to read uploaded plate file.", e);
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to read uploaded plate file. " + e + " : " + e.getMessage(), module);
					jsonResponse.put("success", false);
				} catch(GenericEntityException e) {
					TransactionUtil.rollback(beganTransaction, "Error while trying to read uploaded plate file.", e);
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to read uploaded order item file. " + e + " : " + e.getMessage(), module);
					jsonResponse.put("success", false);
				} catch(GenericServiceException e) {
					TransactionUtil.rollback(beganTransaction, "Error while trying to read uploaded plate file.", e);
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to read uploaded order item file. " + e + " : " + e.getMessage(), module);
					jsonResponse.put("success", false);
				} catch(Exception e) {
					TransactionUtil.rollback(beganTransaction, "Error while trying to read uploaded plate file.", e);
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to read uploaded order item file. " + e + " : " + e.getMessage(), module);
					jsonResponse.put("success", false);
				} finally {
					try {
						// only commit the transaction if we started one... this will throw an exception if it fails
						TransactionUtil.commit(beganTransaction);
					} catch(GenericEntityException e) {
						EnvUtil.reportError(e);
						Debug.logError(e, "Could not create new plate.", module);
					}
				}
			} catch(GenericTransactionException e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to save plate. " + e + " : " + e.getMessage(), module);
				jsonResponse.put("success", false);
			}
		} else {
			jsonResponse.put("success", false);
			jsonResponse.put("error", "Authorization failed.");
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Create a plate
	 */
	public static String createOrUpdatePlate(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", true);

		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try {
				GenericValue printPlate = PlateHelper.createOrUpdatePlate(delegator, dispatcher, (String) context.get("printPlateId"), context, userLogin);
			} catch (Exception e) {
				TransactionUtil.rollback(beganTransaction, "Error while trying to create plate file.", e);
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to create plate. " + e + " : " + e.getMessage(), module);
				jsonResponse.put("success", false);
			} finally {
				try {
					// only commit the transaction if we started one... this will throw an exception if it fails
					TransactionUtil.commit(beganTransaction);
				} catch(GenericEntityException e) {
					EnvUtil.reportError(e);
					Debug.logError(e, "Could not create new plate.", module);
				}
			}
		} catch(GenericTransactionException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to save plate. " + e + " : " + e.getMessage(), module);
			jsonResponse.put("success", false);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Create a plate
	 */
	public static String createOrUpdatePlateItem(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", true);
		context.put("changedByUserLoginId", userLogin.getString("userLoginId"));
		context.put("dueDate", Timestamp.valueOf((String) context.get("dueDate") + " 00:00:00.0"));

		try {
			OrderHelper.updateOrderItem(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"), context);
		} catch(GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to update order item. " + e + " : " + e.getMessage(), module);
			jsonResponse.put("success", false);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Cancel a plate
	 */
	public static String cancelPlate(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", true);

		if(UtilValidate.isNotEmpty(context.get("printPlateId"))) {
			boolean beganTransaction = false;
			try {
				beganTransaction = TransactionUtil.begin();
				try {
					PlateHelper.cancelPlate(delegator, dispatcher, (String) context.get("printPlateId"), userLogin);
				} catch (GenericEntityException e) {
					TransactionUtil.rollback(beganTransaction, "Error while trying to cancel plate file.", e);
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to cancel plate. " + e + " : " + e.getMessage(), module);
					jsonResponse.put("success", false);
				} catch (GenericServiceException e) {
					TransactionUtil.rollback(beganTransaction, "Error while trying to cancel plate file.", e);
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to cancel plate. " + e + " : " + e.getMessage(), module);
					jsonResponse.put("success", false);
				} finally {
					try {
						// only commit the transaction if we started one... this will throw an exception if it fails
						TransactionUtil.commit(beganTransaction);
					} catch(GenericEntityException e) {
						EnvUtil.reportError(e);
						Debug.logError(e, "Could not cancel plate.", module);
					}
				}
			} catch(GenericTransactionException e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to save plate. " + e + " : " + e.getMessage(), module);
				jsonResponse.put("success", false);
			}
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}
}