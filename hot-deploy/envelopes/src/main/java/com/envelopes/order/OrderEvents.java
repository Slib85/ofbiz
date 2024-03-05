/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.order;

import java.io.FileReader;
import java.io.IOException;
import java.lang.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import com.envelopes.email.EmailHelper;
import com.envelopes.plating.PlateHelper;
import com.envelopes.quote.PurchaseOrderPDF;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;

import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.transaction.GenericTransactionException;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.entity.util.EntityListIterator;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.order.order.OrderReadHelper;
import org.apache.ofbiz.order.shoppingcart.*;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import org.apache.ofbiz.service.ServiceUtil;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.envelopes.shipping.*;
import com.envelopes.cart.CartHelper;
import com.envelopes.party.PartyHelper;
import com.envelopes.product.ProductHelper;
import com.envelopes.http.*;
import com.envelopes.util.*;

public class OrderEvents {
	public static final String module = OrderEvents.class.getName();
	public static final List<String> CANNED_MSGS;
	static {
		CANNED_MSGS = new ArrayList<String>();
		CANNED_MSGS.add("The artwork you uploaded is low resolution and probably wont print well. If you can, please upload a color separated, high resolution file. The file it was originally created in works best. (eps or pdf)");
		CANNED_MSGS.add("Art is a bit low resolution and could print crisper with a higher resolution file. The file it was originally created in works best. (eps or pdf)");
		CANNED_MSGS.add("The artwork you uploaded is low resolution and probably wont print well. If you can, please upload a color separated, high resolution file. The file it was originally created in works best. (eps or pdf)");
		CANNED_MSGS.add("By approving this proof, you are confirming all content and placement is correct. All will be printed as seen in proof.");
		CANNED_MSGS.add("We matched the color in your file as closely as possible. Please refer to a pantone solid uncoated swatch for an accurate representation of the final printed color. Colors viewed onscreen are not accurate as to what will print. If color is critical, please supply a pantone color or a printed sample to be color matched.");
		CANNED_MSGS.add("Your order was placed for one color, so your art has been converted to Grayscale. If you would like two color print, please reject the proof and specify in the rejection comments. We will adjust your order and send a new proof with new pricing. By approving the proof, you are approving all art as seen in the proof.");
	}

	/*
	 * Get order details for anon users
	 */
	public static String getOrderDetailsAnon(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		try {
			Map<String, Object> context = EnvUtil.getParameterMap(request);
			if (UtilValidate.isNotEmpty(context.get("emailAddress")) && UtilValidate.isNotEmpty(context.get("orderId"))) {
				OrderReadHelper orh = new OrderReadHelper(delegator, (String) context.get("orderId"));
				if(((String) context.get("emailAddress")).equalsIgnoreCase(orh.getOrderEmailString())) {
					request.setAttribute("isOwner", true);
					return getOrderDetails(request, response);
				}
			}
		} catch (Exception e) {
			Debug.logError(e, "No order found.", module);
		}

		return "error";
	}

	/**
     * Get the orderData for the given orderId
     */

    public static String getOrderDetails(HttpServletRequest request, HttpServletResponse response) {
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, String> errors = new HashMap<>();
		boolean success = false;

        String orderId = request.getParameter("orderId");
		boolean newOrder = false;
        if(UtilValidate.isEmpty(orderId)) {
            orderId = (String) request.getAttribute("orderId");
        }
		if(UtilValidate.isEmpty(request.getParameter("newOrder")) && UtilValidate.isNotEmpty(request.getAttribute("newOrder"))) {
			newOrder = ((Boolean) request.getAttribute("newOrder")).booleanValue();
		}

        if(UtilValidate.isEmpty(orderId)) {
            orderId = (String) ((Map)request.getAttribute("savedResponse")).get("orderId");
        }

        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        if(UtilValidate.isEmpty(orderId)) {
            errors.put("error", "The order id is empty and is required to get the order details.");
        } else {
            try {
				//see if the order belongs to the user
				Map<String, Object> orderData = new HashMap<>();
				boolean isOwner = (UtilValidate.isNotEmpty(request.getAttribute("isOwner"))) ? (Boolean) request.getAttribute("isOwner") : false;
				if(OrderHelper.isOwnerOfOrder(null, delegator, orderId, userLogin) || newOrder || isOwner) {
					orderData = OrderHelper.getOrderData(delegator, orderId, false);
					orderData.put("openOrder", false);
					success = true;
				} else {
					errors.put("error", "The order could not be found.");
				}

				jsonResponse.put("orderData", orderData);
            } catch (GenericEntityException e) {
                EnvUtil.reportError(e);
                Debug.logError("Error while trying to get order data for order with id '" + orderId + "'. " + e + " : " + e.getMessage(), module);
                errors.put("error", "An error occurred while getting the order details for Order Id : " + orderId);
            }
        }
        jsonResponse.put("errors", errors);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

	/*
	 * Get Data from Switch to update proof
	 */
	public static String processSwitchWorker(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		boolean success = false;
		jsonResponse.put("success", true);

		if(EnvUtil.authorizedSwitchRequest((String) context.get("key"))) {
			boolean beganTransaction = false;
			try {
				beganTransaction = TransactionUtil.begin();
				try {
					jsonResponse = FileHelper.uploadFile(request, null, false, false);

					if(UtilValidate.isNotEmpty(jsonResponse) && UtilValidate.isNotEmpty(jsonResponse.get("files"))) {
						List<Map<String, Object>> files = (List<Map<String, Object>>) jsonResponse.get("files");
						for(Map<String, Object> file : files) {
							String fileName = (String) file.get("name");
							boolean noKeyline = fileName.toUpperCase().contains("_NOKEY.PDF");
							boolean whiteInk = fileName.toUpperCase().contains("_WHITE.PDF");
							if(fileName.toUpperCase().contains("_NOKEY.PDF")) {
								noKeyline = true;
								fileName = fileName.replaceAll("(?i)_NOKEY\\.PDF", ".pdf");
							}
							if(fileName.toUpperCase().contains("_WHITE.PDF")) {
								whiteInk = true;
								fileName = fileName.replaceAll("(?i)_WHITE\\.PDF", ".pdf");
							}
							if(fileName.indexOf("_") == -1) {
								continue; //file is formatted incorrectly
							}
							String orderId = fileName.substring(0, fileName.indexOf("_"));
							String orderItemSeqId = null;
							String side = null;
							if(fileName.contains("-")) {
								orderItemSeqId = fileName.substring(fileName.indexOf("_") + 1, fileName.indexOf("-"));
								if(fileName.indexOf("-") != fileName.lastIndexOf("-")) {
									side = fileName.substring(fileName.lastIndexOf("-") + 1, fileName.lastIndexOf("."));
								}
							} else {
								orderItemSeqId = fileName.substring(fileName.indexOf("_") + 1, fileName.indexOf("."));
							}

							//get # of sides for this order
							if(side == null) {
								Map<String, String> attr = OrderHelper.getOrderItemAttributeMap(delegator, orderId, orderItemSeqId);
								if (attr.containsKey("colorsFront") && Integer.valueOf(attr.get("colorsFront")) > 0) {
									side = "FRONT";
								} else if (attr.containsKey("colorsBack") && Integer.valueOf(attr.get("colorsBack")) > 0) {
									side = "BACK";
								}
							}

							if(!noKeyline && !whiteInk) {
								OrderHelper.discOrderItemContent(delegator, orderId, orderItemSeqId, "OIACPRP_WORKER_" + side);
								OrderHelper.discOrderItemContent(delegator, orderId, orderItemSeqId, "OIACPRP_WORKER_NOKEY_" + side);
								OrderHelper.discOrderItemContent(delegator, orderId, orderItemSeqId, "OIACPRP_WORKER_WHITE_" + side);

								OrderHelper.setOrderItemContent(delegator, orderId, orderItemSeqId, "OIACPRP_WORKER_" + side, file);
							} else if(noKeyline) {
								OrderHelper.discOrderItemContent(delegator, orderId, orderItemSeqId, "OIACPRP_WORKER_NOKEY_" + side);
								OrderHelper.setOrderItemContent(delegator, orderId, orderItemSeqId, "OIACPRP_WORKER_NOKEY_" + side, file);
							} else if(whiteInk){
								OrderHelper.discOrderItemContent(delegator, orderId, orderItemSeqId, "OIACPRP_WORKER_WHITE_" + side);
								OrderHelper.setOrderItemContent(delegator, orderId, orderItemSeqId, "OIACPRP_WORKER_WHITE_" + side, file);
							}
							//Map<String, Object> result = dispatcher.runSync("changeOrderItemStatus", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId, "statusId", "ART_READY_FOR_REVIEW", "userLogin", EnvUtil.getSystemUser(delegator)));
						}
					}
				} catch (FileUploadException e) {
					TransactionUtil.rollback(beganTransaction, "Error while trying to read uploaded order item file.", e);
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to read uploaded order item file. " + e + " : " + e.getMessage(), module);
				} catch (Exception e) {
					TransactionUtil.rollback(beganTransaction, "Error while trying to read uploaded order item file.", e);
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to read uploaded order item file. " + e + " : " + e.getMessage(), module);
				} finally {
					try {
						// only commit the transaction if we started one... this will throw an exception if it fails
						TransactionUtil.commit(beganTransaction);
						success = true;
					} catch(GenericEntityException e) {
						EnvUtil.reportError(e);
						Debug.logError(e, "Could not create new plate.", module);
					}
				}
			} catch(GenericTransactionException e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to read uploaded order item file. " + e + " : " + e.getMessage(), module);
			}
		} else {
			jsonResponse.put("error", "Authorization failed.");
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get Data from Switch to update proof
	 */
	public static String processSwitchProof(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;

		if(EnvUtil.authorizedSwitchRequest((String) context.get("key"))) {
			boolean beganTransaction = false;
			try {
				beganTransaction = TransactionUtil.begin();
				try {
					Map<String, Object> fileData = FileHelper.uploadFile(request, null, false, false);

					if(UtilValidate.isNotEmpty(fileData) && UtilValidate.isNotEmpty(fileData.get("files"))) {
						List<Map<String, Object>> files = (List<Map<String, Object>>) fileData.get("files");
						for(Map<String, Object> file : files) {
							String fileName = (String) file.get("name");
							String orderId = (UtilValidate.isNotEmpty(context.get("orderId"))) ? (String) context.get("orderId") : fileName.substring(0, fileName.indexOf("_"));
							String orderItemSeqId = (UtilValidate.isNotEmpty(context.get("orderItemSeqId"))) ? (String) context.get("orderItemSeqId") : fileName.substring(fileName.indexOf("_") + 1, fileName.indexOf("."));
							OrderHelper.discOrderItemContent(delegator, orderId, orderItemSeqId, "OIACPRP_PDF");
							OrderHelper.setOrderItemContent(delegator, orderId, orderItemSeqId, "OIACPRP_PDF", file);

							GenericValue orderItem = delegator.findOne("OrderItem", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), false);
							boolean reuse = (orderItem != null && UtilValidate.isNotEmpty(orderItem.getString("referenceOrderId")));

							if(orderItem != null && reuse && ("ART_REORDER".equalsIgnoreCase(orderItem.getString("statusId")) || "ART_OUTSOURCED".equalsIgnoreCase(orderItem.getString("statusId")))) {
								//do nothing
							} else if(orderItem != null) {
								String statusId = (UtilValidate.isNotEmpty(context.get("statusId"))) ? (String) context.get("statusId") : "ART_READY_FOR_REVIEW";
								Map<String, Object> result = dispatcher.runSync("changeOrderItemStatus", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId, "statusId", statusId, "userLogin", EnvUtil.getSystemUser(delegator)));
							}
						}
					}
					success = true;
				} catch (FileUploadException e) {
					TransactionUtil.rollback(beganTransaction, "Error while trying to read uploaded order item file.", e);
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to read uploaded order item file. " + e + " : " + e.getMessage(), module);
				} catch (Exception e) {
					TransactionUtil.rollback(beganTransaction, "Error while trying to read uploaded order item file.", e);
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to read uploaded order item file. " + e + " : " + e.getMessage(), module);
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
				Debug.logError("Error while trying to read uploaded order item file. " + e + " : " + e.getMessage(), module);
			}
		} else {
			jsonResponse.put("error", "Authorization failed.");
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get Data from Switch to update order items
	 */
	public static String processSwitchOrderItemData(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", true);

		if(EnvUtil.authorizedSwitchRequest((String) context.get("key"))) {
			boolean beganTransaction = false;
			try {
				beganTransaction = TransactionUtil.begin();
				List<Document> xmls = new ArrayList<Document>();
				try {
					Map<String, Object> fileData = FileHelper.uploadFile(request, null, false, true);

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

					if(UtilValidate.isNotEmpty(xmls)) {
						for(Document xml : xmls) {
							String orderId = DOMUtil.getStringValue("orderNumber", xml);
							String orderItemSeqId = DOMUtil.getStringValue("sequenceID", xml);
							if(UtilValidate.isNotEmpty(orderId) && UtilValidate.isNotEmpty(orderItemSeqId)) {
								Map<String, Object> orderItemData = new HashMap<String, Object>();
								//orderItemData.put("orderId", xml.getElementsByTagName("orderNumber").item(0).getTextContent());
								//orderItemData.put("orderItemSeqId", xml.getElementsByTagName("sequenceID").item(0).getTextContent());
								orderItemData.put("quantity", DOMUtil.getBigDecimalValue("quantity", xml));
								orderItemData.put("isRushProduction", DOMUtil.getBooleanValue("productionType", xml));

								orderItemData.put("itemPrintPosition", DOMUtil.getStringValue("printPosition", xml));
								orderItemData.put("frontInkColor1", DOMUtil.getStringValue("frontInkColor1", xml));
								orderItemData.put("frontInkColor2", DOMUtil.getStringValue("frontInkColor2", xml));
								orderItemData.put("frontInkColor3", DOMUtil.getStringValue("frontInkColor3", xml));
								orderItemData.put("frontInkColor4", DOMUtil.getStringValue("frontInkColor4", xml));
								orderItemData.put("backInkColor1", DOMUtil.getStringValue("backInkColor1", xml));
								orderItemData.put("backInkColor2", DOMUtil.getStringValue("backInkColor2", xml));
								orderItemData.put("backInkColor3", DOMUtil.getStringValue("backInkColor3", xml));
								orderItemData.put("backInkColor4", DOMUtil.getStringValue("backInkColor4", xml));

								orderItemData.put("isFolded", DOMUtil.getStringValue("folding", xml, "false"));
								orderItemData.put("isFullBleed", DOMUtil.getStringValue("fullBleed", xml, "false"));
								orderItemData.put("cuts", DOMUtil.getStringValue("cuts", xml, "0"));
								orderItemData.put("colorsFront", DOMUtil.getStringValue("totalInksFront", xml));
								orderItemData.put("colorsBack", DOMUtil.getStringValue("totalInksBack", xml));

								StringBuilder fullCommentsToCustomer = new StringBuilder();
								for(int i = 1; i <= 30; i++) {
									NodeList commentsToCustomerList = xml.getElementsByTagName("commentsToCustomer" + i);
									if(commentsToCustomerList.getLength() > 0) {
										String commentsToCustomer = commentsToCustomerList.item(0).getTextContent();
										if(commentsToCustomer != null && !commentsToCustomer.equals("undefined")) {
											fullCommentsToCustomer.append(commentsToCustomer + " ");
										}
									}
								}

								StringBuilder fullCommentsToPrintProduction = new StringBuilder();
								for(int i = 1; i <= 30; i++) {
									NodeList commentsToPrintProductionList = xml.getElementsByTagName("commentsToPrintProduction" + i);
									if(commentsToPrintProductionList.getLength() > 0) {
										String commentsToPrintProduction = commentsToPrintProductionList.item(0).getTextContent();
										if(commentsToPrintProduction != null && !commentsToPrintProduction.equals("undefined")) {
											fullCommentsToPrintProduction.append(commentsToPrintProduction + " ");
										}
									}
								}

								StringBuilder fullKickBackComment = new StringBuilder();
								for(int i = 1; i <= 30; i++) {
									NodeList kickBackCommentList = xml.getElementsByTagName("kickbackComment" + i);
									if(kickBackCommentList.getLength() > 0) {
										String kickBackComment = kickBackCommentList.item(0).getTextContent();
										if(kickBackComment != null && !kickBackComment.equals("undefined")) {
											fullKickBackComment.append(kickBackComment + " ");
										}
									}
								}

								String fixedAndReplaced = null;
								NodeList fixedAndReplacedList = xml.getElementsByTagName("fixedAndReplaced");
								if(fixedAndReplacedList.getLength() > 0) {
									fixedAndReplaced = fixedAndReplacedList.item(0).getTextContent();
								}

								String fixedAndReplacedNotes = null;
								NodeList fixedAndReplacedNoteList = xml.getElementsByTagName("fixedAndReplacedNotes");
								if(fixedAndReplacedNoteList.getLength() > 0) {
									fixedAndReplacedNotes = fixedAndReplacedNoteList.item(0).getTextContent();
								}

								if(UtilValidate.isNotEmpty(fullKickBackComment.toString())) {
									fullCommentsToPrintProduction.append("\n" + "Kickback - " + fullKickBackComment);
								}
								if(UtilValidate.isNotEmpty(fixedAndReplaced)) {
									fullCommentsToPrintProduction.append("\n" + "Fixed and Replaced - " + fixedAndReplaced);
								}
								if(UtilValidate.isNotEmpty(fixedAndReplacedNotes)) {
									fullCommentsToPrintProduction.append("\n" + "Notes - " + fixedAndReplacedNotes);
								}

								if(UtilValidate.isNotEmpty(fullCommentsToCustomer.toString())) {
									orderItemData.put("itemCustomerComments", fullCommentsToCustomer.toString());
								}
								if(UtilValidate.isNotEmpty(fullCommentsToPrintProduction.toString())) {
									orderItemData.put("itemPrepressComments", fullCommentsToPrintProduction.toString());
								}

								//OrderHelper.updateOrderItem(delegator, orderId, orderItemSeqId, orderItemData);
								OrderHelper.updateOrderItemArtwork(delegator, orderId, orderItemSeqId, orderItemData);
								OrderHelper.updateOrderItemAttribute(delegator, orderId, orderItemSeqId, orderItemData);

							}
						}
					}
				} catch (FileUploadException e) {
					TransactionUtil.rollback(beganTransaction, "Error while trying to read uploaded order item file.", e);
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to read uploaded order item file. " + e + " : " + e.getMessage(), module);
					jsonResponse.put("success", false);
				} catch (Exception e) {
					TransactionUtil.rollback(beganTransaction, "Error while trying to read uploaded order item file.", e);
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
				Debug.logError("Error while trying to read uploaded order item file. " + e + " : " + e.getMessage(), module);
				jsonResponse.put("success", false);
			}
		} else {
			jsonResponse.put("success", false);
			jsonResponse.put("error", "Authorization failed.");
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Update the order item artwork row
	 */
	public static String setOrderItemArtwork(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", true);

		if(UtilValidate.isNotEmpty(userLogin)) {
			try {
				if("true".equalsIgnoreCase((String) context.get("removeUserAssignment"))) {
					context.put("assignedToUserLogin", null);
				} else {
					if (UtilValidate.isNotEmpty(context.get("assignToUser"))) {
						context.put("assignedToUserLogin", context.get("assignToUser"));
					} else {
						context.put("assignedToUserLogin", userLogin.getString("userLoginId"));
					}
					context.put("lastModifiedByUserLogin", userLogin.getString("userLoginId"));
					context.put("lastModifiedDate", UtilDateTime.nowTimestamp());
				}

				context.remove("removeUserAssignment"); //dont need this value

				//if we have print plate id, check if its just the press id and not a plate id, if its just press id, generate a new plate id
				if(UtilValidate.isNotEmpty(context.get("printPlateId"))) {
					Map<String, String> pressList = PlateHelper.getPressList();
					if(pressList.containsKey((String) context.get("printPlateId"))) {
						context.put("printPlateId", PlateHelper.createPrintPlate(delegator, null, (String) context.get("printPlateId")).getString("printPlateId"));
					}
				}

				OrderHelper.updateOrderItemArtwork(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"), context);
			} catch (GenericEntityException e) {
				jsonResponse.put("success", false);
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to update order item artwork. " + e + " : " + e.getMessage(), module);
			}
		} else {
			jsonResponse.put("success", false);
			jsonResponse.put("error", "You do not have sufficient privileges to update this order.");
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Update the order item attribute data
	 */
	public static String setOrderItemAttribute(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", true);

		if(UtilValidate.isNotEmpty(userLogin)) {
			try {
				String updatePrice = (String) context.remove("updatePrice");
				context.put("assignedToUserLogin", userLogin.getString("userLoginId"));
				context.put("lastModifiedByUserLogin", userLogin.getString("userLoginId"));
				context.put("lastModifiedDate", UtilDateTime.nowTimestamp());
				OrderHelper.updateOrderItemArtwork(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"), context);

				String orderId = (String) context.remove("orderId");
				String orderItemSeqId = (String) context.remove("orderItemSeqId");
				String isRushProduction = (String) context.remove("isRushProduction");
				String lockScene7Design = (String) context.remove("lockScene7Design");
				OrderHelper.updateOrderItemAttribute(delegator, orderId, orderItemSeqId, context);

				if(UtilValidate.isNotEmpty(isRushProduction)) {
					OrderHelper.updateOrderItem(delegator, orderId, orderItemSeqId, UtilMisc.toMap("isRushProduction", (isRushProduction.equalsIgnoreCase("Y")) ? "Y" : "N"));
				}

				if(UtilValidate.isNotEmpty(lockScene7Design)) {
					OrderHelper.updateOrderItem(delegator, orderId, orderItemSeqId, UtilMisc.toMap("lockScene7Design", (lockScene7Design.equalsIgnoreCase("Y")) ? "Y" : "N"));
				}

				if(UtilValidate.isEmpty(updatePrice) || (UtilValidate.isNotEmpty(updatePrice) && "true".equalsIgnoreCase(updatePrice))) {
					GenericValue orderHeader = OrderHelper.getOrderHeader(delegator, orderId);
					if(orderHeader != null && !"FOLD_SALES_CHANNEL".equalsIgnoreCase(orderHeader.getString("salesChannelEnumId"))) {
						OrderHelper.updateProductPrice(delegator, dispatcher, orderId, orderItemSeqId);

						request.setAttribute("saveResponse", true);
						String responseMap = updateOrderTotals(request, response);
					} else {
						//todo ADD FOLDERS PRODUCT PRICING
					}
				}
			} catch (GenericEntityException e) {
				jsonResponse.put("success", false);
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to update order item attribute. " + e + " : " + e.getMessage(), module);
			} catch (GenericServiceException e) {
				jsonResponse.put("success", false);
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to update order item attribute. " + e + " : " + e.getMessage(), module);
			}
		} else {
			jsonResponse.put("success", false);
			jsonResponse.put("error", "You do not have sufficient privileges to update this order.");
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get shipping rates for items in an order
	 */
	public static String getShippingRates(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", true);

		if(UtilValidate.isNotEmpty(context.get("orderId")) && UtilValidate.isNotEmpty(context.get("shipping_postalCode"))) {
			String loadCart = ShoppingCartEvents.loadCartFromOrder(request, response);
			if(!loadCart.equals("error")) {
				ShippingEvents.getShippingRates(request, response);
			}
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get shipping rates for items in an order
	 */
	public static String setShippingRate(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", true);

		if(UtilValidate.isNotEmpty(userLogin) && UtilValidate.isNotEmpty(context.get("orderId")) && UtilValidate.isNotEmpty(context.get("amount")) && UtilValidate.isNotEmpty(context.get("carrierPartyId")) && UtilValidate.isNotEmpty(context.get("shipmentMethodTypeId"))) {
			try {
				GenericValue adj = OrderHelper.updateShippingCost(delegator, userLogin, (String) context.get("orderId"), null, new BigDecimal((String) context.get("amount")), (String) context.get("carrierPartyId"), (String) context.get("shipmentMethodTypeId"));
				if(adj == null) {
					jsonResponse.put("success", false);
				} else {
					String responseMap = updateOrderTotals(request, response);
				}
			} catch(GenericEntityException e) {
				jsonResponse.put("success", false);
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to update order adjustment. " + e + " : " + e.getMessage(), module);
			} catch(Exception e) {
				jsonResponse.put("success", false);
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to update order adjustment. " + e + " : " + e.getMessage(), module);
			}
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String applyManualAdjustments(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;
		String adjustment = "";
		if(UtilValidate.isNotEmpty(userLogin) && UtilValidate.isNotEmpty(context.get("orderId")) && UtilValidate.isNotEmpty(adjustment = (String)context.get("adjustment"))) {
			boolean beganTransaction = false;
			try {
				try {
					beganTransaction = TransactionUtil.begin();
					String orderAdjustmentTypeId = adjustment.startsWith("-") ? "DISCOUNT_ADJUSTMENT" : "FEE";
					GenericValue adj = OrderHelper.addManualAdjustment(delegator, userLogin, (String) context.get("orderId"), new BigDecimal((String) context.get("adjustment")), orderAdjustmentTypeId, (String) context.get("description"));

					//now update all order totals
					request.setAttribute("saveResponse", true);
					String responseMap = updateOrderTotals(request, response);

					success = true;
				} catch (Exception e) {
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to add a manual order adjustment. " + e + " : " + e.getMessage(), module);
					TransactionUtil.rollback(beganTransaction, "Error while trying to add a manual order adjustment.", e);

				} finally {
					TransactionUtil.commit(beganTransaction);
				}
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to add a manual order adjustment. " + e + " : " + e.getMessage(), module);
			}
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get a full list of orders for logged in user
	 */
	public static String getUserOrderSummary(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;

		if(userLogin != null) {
			try {
				request.setAttribute("orderType", "all");
				Map<String, Object> serviceIn = UtilMisc.<String, Object>toMap("partyId", userLogin.getString("partyId"),
						"roleTypeId", "PLACING_CUSTOMER",
						"orderTypeId", "SALES_ORDER",
						"statusId", "ORDER_SHIPPED",
						"webSiteId", ShoppingCartEvents.getCartObject(request).getWebSiteId(),
						"monthsToInclude", Integer.valueOf(12),
						"userLogin", userLogin);

				Map<String, Object> result = dispatcher.runSync("getOrderedSummaryInformation", serviceIn);
				if (ServiceUtil.isError(result)) {
					//
				} else {
					jsonResponse.put("loyaltyPoints", (BigDecimal) result.get("totalSubRemainingAmount"));
					jsonResponse.put("discountRate", (BigDecimal) result.get("discountRate"));
				}

				List<Map<String, Object>> orderList = OrderHelper.getOrderList(request, response);
				jsonResponse.put("orders", orderList);
			} catch (Exception e) {
				//
			}
			success = true;
		} else {
			success = false;
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get a full list of orders
	 */
	public static String getOrderList(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		ArrayList<Map> orderList = new ArrayList<Map>();
		jsonResponse.put("success", true);

		//datatables.net adds params that need to be ignored
		context.remove("_");

		if(UtilValidate.isNotEmpty(userLogin)) {
			boolean beganTransaction = false;

			EntityListIterator eli = null;
			GenericValue orderData = null;
			String currentOrderId = "";
			try {
				try {
					beganTransaction = TransactionUtil.begin();
					eli = OrderHelper.getOrderList(delegator, (UtilValidate.isNotEmpty(context.get("startPosition"))) ? Integer.valueOf((String) context.get("startPosition")): null, (UtilValidate.isNotEmpty(context.get("maxRows"))) ? Integer.valueOf((String) context.get("maxRows")): null, context);

					Map<String, Object> orderHeader = null;
					ArrayList<Map> orderItems = null;
					boolean isRush = false;
					boolean isPrinted = false;
					boolean isScene7 = false;
					boolean hasAddressing = false;
					while((orderData = eli.next()) != null) {
						if(!currentOrderId.equals(orderData.getString("orderId"))) {
							if(UtilValidate.isNotEmpty(currentOrderId)) {
								orderHeader.put("priority", isRush ? "Rush" : "Standard");
								orderHeader.put("isPrinted", isPrinted ? "Printed" : "Plain");
								orderHeader.put("isScene7", isScene7 ? "Y" : "N");
								orderHeader.put("hasAddressing", hasAddressing ? "Y" : "N");
								isRush = false;
								isPrinted = false;
								isScene7 = false;
								hasAddressing = false;
								applyOrderList(orderHeader, orderItems, orderList);
							}

							orderHeader = new HashMap<String, Object>();
							orderItems = new ArrayList<Map>();

							OrderReadHelper orh = new OrderReadHelper(delegator, orderData.getString("orderId"));
							GenericValue person = orh.getBillToParty();

							orderHeader.put("orderId", orderData.get("orderId"));
							orderHeader.put("orderDate", orderData.get("orderDate"));
							orderHeader.put("exportedDate", orderData.get("exportedDate"));
							orderHeader.put("statusId", orderData.get("orderStatusId"));
							orderHeader.put("grandTotal", orderData.get("grandTotal"));
							orderHeader.put("email", orh.getOrderEmailString());
							orderHeader.put("billToName", (UtilValidate.isNotEmpty(person)) ? person.getString("firstName") + " " + person.getString("lastName"): null);
							orderHeader.put("partyId", person.getString("partyId"));
							orderHeader.put("shippingMethod", orh.getGenericShippingMethod("00001"));
							orderHeader.put("salesChannelEnumId", orderData.get("salesChannelEnumId"));

							Map<String, Object> orderItem = new HashMap<String, Object>();
							orderItem.put("orderItemSeqId", orderData.get("orderItemSeqId"));
							orderItem.put("productId", orderData.get("productId"));
							orderItem.put("quantity", orderData.get("quantity"));
							orderItem.put("cancelQuantity", orderData.get("cancelQuantity"));
							orderItem.put("unitPrice", orderData.get("unitPrice"));
							orderItem.put("itemDescription", orderData.get("itemDescription"));
							orderItem.put("statusId", orderData.get("itemStatusId"));
							orderItem.put("isRushProduction", orderData.get("isRushProduction"));
							orderItem.put("artworkSource", orderData.get("artworkSource"));

							isRush = isRush || (UtilValidate.isNotEmpty(orderData.get("isRushProduction")) && orderData.getString("isRushProduction").equalsIgnoreCase("Y"));
							isPrinted = isPrinted || UtilValidate.isNotEmpty(orderData.getString("artworkSource"));
							isScene7 = isScene7 || ("SCENE7_ART_ONLINE".equalsIgnoreCase(orderData.getString("artworkSource")));
							if("SCENE7_ART_ONLINE".equalsIgnoreCase(orderData.getString("artworkSource"))) {
								Map<String, String> itemAttr = OrderHelper.getOrderItemAttributeMap(delegator, orderData.getString("orderId"), orderData.getString("orderItemSeqId"));
								hasAddressing = hasAddressing || (UtilValidate.isNotEmpty(itemAttr.get("addresses")) && !"0".equals(itemAttr.get("addresses")));
							}

							orderItems.add(orderItem);
						} else {
							Map<String, Object> orderItem = new HashMap<String, Object>();
							orderItem.put("orderItemSeqId", orderData.get("orderItemSeqId"));
							orderItem.put("productId", orderData.get("productId"));
							orderItem.put("quantity", orderData.get("quantity"));
							orderItem.put("cancelQuantity", orderData.get("cancelQuantity"));
							orderItem.put("unitPrice", orderData.get("unitPrice"));
							orderItem.put("itemDescription", orderData.get("itemDescription"));
							orderItem.put("statusId", orderData.get("itemStatusId"));
							orderItem.put("isRushProduction", orderData.get("isRushProduction"));
							orderItem.put("artworkSource", orderData.get("artworkSource"));

							isRush = isRush || (UtilValidate.isNotEmpty(orderData.get("isRushProduction")) && orderData.getString("isRushProduction").equalsIgnoreCase("Y"));
							isPrinted = isPrinted || UtilValidate.isNotEmpty(orderData.getString("artworkSource"));
							isScene7 = isScene7 || ("SCENE7_ART_ONLINE".equalsIgnoreCase(orderData.getString("artworkSource")));
							if("SCENE7_ART_ONLINE".equalsIgnoreCase(orderData.getString("artworkSource"))) {
								Map<String, String> itemAttr = OrderHelper.getOrderItemAttributeMap(delegator, orderData.getString("orderId"), orderData.getString("orderItemSeqId"));
								hasAddressing = hasAddressing || (UtilValidate.isNotEmpty(itemAttr.get("addresses")) && !"0".equals(itemAttr.get("addresses")));
							}

							orderItems.add(orderItem);
						}

						currentOrderId = (String) orderData.get("orderId");
					}
					if(UtilValidate.isNotEmpty(currentOrderId)) {
						orderHeader.put("priority", isRush ? "Rush" : "Standard");
						orderHeader.put("isPrinted", isPrinted ? "Printed" : "Plain");
						orderHeader.put("isScene7", isScene7 ? "Y" : "N");
						orderHeader.put("hasAddressing", hasAddressing ? "Y" : "N");
						applyOrderList(orderHeader, orderItems, orderList);
					}
				} catch(GenericEntityException e) {
					jsonResponse.put("success", false);
					TransactionUtil.rollback(beganTransaction, "Error while trying to get order data. [" + orderData.get("orderId") + "]", e);
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to get order data. [" + orderData.get("orderId") + "]" + e + " : " + e.getMessage(), module);
				} catch(NullPointerException e) {
					jsonResponse.put("success", false);
					TransactionUtil.rollback(beganTransaction, "Error while trying to get order data. [" + orderData.get("orderId") + "]", e);
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to get order data. [" + orderData.get("orderId") + "]" + e + " : " + e.getMessage(), module);
				} catch(Exception e) {
					jsonResponse.put("success", false);
					TransactionUtil.rollback(beganTransaction, "Error while trying to get order data. [" + orderData.get("orderId") + "]", e);
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to get order data. [" + orderData.get("orderId") + "]" + e + " : " + e.getMessage(), module);
				} finally {
					try {
						if(eli != null) {
							eli.close();
							TransactionUtil.commit(beganTransaction);
						}
					} catch(GenericEntityException e) {
						jsonResponse.put("success", false);
						EnvUtil.reportError(e);
						Debug.logError("Error while trying to get order data and closing list. " + e + " : " + e.getMessage(), module);
					}
				}
			} catch (GenericTransactionException e) {
				jsonResponse.put("success", false);
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to get order data. " + e + " : " + e.getMessage(), module);
			}
			jsonResponse.put("data", orderList);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get a full list of orders
	 */
	public static String adminSearch(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", true);
		jsonResponse.put("url", null);

		if(UtilValidate.isNotEmpty(userLogin)) {
			try {
				jsonResponse.put("url", OrderHelper.adminSearch(delegator, (String) context.get("query")));
			} catch(GenericEntityException e) {
				jsonResponse.put("success", false);
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to get order data. " + e + " : " + e.getMessage(), module);
			}
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Set comments
	 */
	public static String setArtworkInternalComment(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;

		if(UtilValidate.isNotEmpty(userLogin) && UtilValidate.isNotEmpty(context.get("orderItemArtworkId")) && UtilValidate.isNotEmpty(context.get("type")) && UtilValidate.isNotEmpty(context.get("comment"))) {
			try {
				GenericValue orderItemArtwork = delegator.findOne("OrderItemArtwork", UtilMisc.toMap("orderItemArtworkId", (String) context.get("orderItemArtworkId")), false);
				if(orderItemArtwork != null) {
					OrderHelper.createInternalArtworkComment(delegator, (String) context.get("type"), (String) context.get("comment"), orderItemArtwork);
					success = true;
				}
			} catch(GenericEntityException e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to save order comment. " + e + " : " + e.getMessage(), module);
			}
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Set comments
	 */
	public static String setArtworkComment(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", false);

		if(UtilValidate.isNotEmpty(userLogin)) {
			try {
				OrderHelper.createArtworkComment(delegator, context, userLogin);
				jsonResponse.put("success", true);
			} catch(GenericEntityException e) {
				jsonResponse.put("success", false);
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to save order comment. " + e + " : " + e.getMessage(), module);
			}
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Upload files for order artwork
	 */
	public static String uploadArtwork(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator)request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		if(("OIACPRP_WORKER").equals((String) context.get("contentPurposeEnumId"))) {
			return processSwitchWorker(request, response);
		}

		try {
			jsonResponse = FileHelper.uploadFile(request, null, false, false);

			if(UtilValidate.isNotEmpty(jsonResponse.get("files"))) {
				List<Map<String, Object>> files = (List<Map<String, Object>>) jsonResponse.get("files");
				for(Map<String, Object> file : files) {
					if(!("OIACPRP_FILE").equals((String) context.get("contentPurposeEnumId")) && !("OIACPRP_INT_FILE").equals((String) context.get("contentPurposeEnumId"))) {
						OrderHelper.discOrderItemContent(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"), (String) context.get("contentPurposeEnumId"));
					}
					OrderHelper.setOrderItemContent(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"), (String) context.get("contentPurposeEnumId"), file);
					GenericValue orderItem = delegator.findOne("OrderItem", UtilMisc.toMap("orderId", (String) context.get("orderId"), "orderItemSeqId", (String) context.get("orderItemSeqId")), false);
					boolean reuse = (orderItem != null && UtilValidate.isNotEmpty(orderItem.getString("referenceOrderId")));

					if (UtilValidate.isEmpty(context.get("ignoreStatusChange")) || !context.get("ignoreStatusChange").equals("1")) {
						if (orderItem != null && reuse && "ART_PROOF_APPROVED".equalsIgnoreCase(orderItem.getString("statusId")) && ("OIACPRP_PDF").equals((String) context.get("contentPurposeEnumId"))) {
							//do nothing
						} else if (orderItem != null && ("OIACPRP_PDF").equals((String) context.get("contentPurposeEnumId"))) {
							//if we are uploading proof, change status and send email
							Map<String, Object> result = dispatcher.runSync("changeOrderItemStatus", UtilMisc.toMap("orderId", (String) context.get("orderId"), "orderItemSeqId", (String) context.get("orderItemSeqId"), "statusId", "ART_READY_FOR_REVIEW", "userLogin", userLogin));
						} else if (orderItem != null && "OIACPRP_FILE".equals((String) context.get("contentPurposeEnumId")) && "ART_NOT_RECEIVED".equals(orderItem.getString("statusId"))) {
							Map<String, Object> result = dispatcher.runSync("changeOrderItemStatus", UtilMisc.toMap("orderId", (String) context.get("orderId"), "orderItemSeqId", (String) context.get("orderItemSeqId"), "statusId", "ART_PENDING_PROOF", "userLogin", userLogin));
						}
					}
				}
			}
		} catch(FileUploadException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		} catch(GenericEntityException e) {
			jsonResponse.clear();
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		}

		if(UtilValidate.isEmpty(jsonResponse)) {
			jsonResponse.put("error", "Could not upload files, please try again.");
			jsonResponse.put("success", false);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String generateArtwork(HttpServletRequest request, HttpServletResponse response) {

		//OIACPRP_SC7_FRNT_PDF
		//OIACPRP_SC7_BACK_PDF
		//OIACPRP_PDF
		//OIACPRP_FRONT -- img
		//OIACPRP_BACK  -- img

		Delegator delegator = (Delegator)request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();
		String orderId = (String) context.get("orderId");
		String orderItemSeqId = (String) context.get("orderItemSeqId");
		String contentPurposeEnumId = (String) context.get("contentPurposeEnumId");
		String fileName = orderId + "_" + orderItemSeqId;
		switch(contentPurposeEnumId) {
			case "OIACPRP_SC7_FRNT_PDF":
				fileName += "_FRONT.pdf";
				break;
			case "OIACPRP_SC7_BACK_PDF":
				fileName += "_BACK.pdf";
				break;
			case "OIACPRP_PDF":
				fileName += "_PROOF.pdf";
				break;
			case "OIACPRP_FRONT":
				fileName += ".png";
				break;
			case "OIACPRP_BACK":
				fileName += ".png";
				break;
			default:
				fileName = "";


		}
		boolean success = false;
		if(UtilValidate.isNotEmpty(fileName) && UtilValidate.isNotEmpty(orderId) && UtilValidate.isNotEmpty(orderItemSeqId)) {
			try {
				Map<String, Object> file = FileHelper.saveFileFromStream(request, fileName, null);
				if((boolean)file.get("success")) {
					OrderHelper.discOrderItemContent(delegator, orderId, orderItemSeqId, contentPurposeEnumId);
					OrderHelper.setOrderItemContent(delegator, orderId, orderItemSeqId, contentPurposeEnumId, file);
					jsonResponse.putAll(file);
					success = true;
				}
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while saving the pdf file " + fileName + ". " + e + " : " + e.getMessage(), module);
			}
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String discontinueOrderItemContent(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;
		try {
			String orderId = (String) context.get("orderId");
			String orderItemSeqId = (String) context.get("orderItemSeqId");
			String contentPurpose = (String) context.get("contentPurpose");
			if(UtilValidate.isNotEmpty(orderId) && UtilValidate.isNotEmpty(orderItemSeqId) && UtilValidate.isNotEmpty(contentPurpose)) {
				OrderHelper.discOrderItemContent(delegator, orderId, orderItemSeqId, contentPurpose);
				success = true;
			}
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while discontinuing order item content." + e + " : " + e.getMessage(), module);
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String getOrderItemContent(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;
		try {
			String orderId = (String) context.get("orderId");
			String orderItemSeqId = (String) context.get("orderItemSeqId");
			String contentPurpose = (String) context.get("contentPurpose");
			if(UtilValidate.isNotEmpty(orderId) && UtilValidate.isNotEmpty(orderItemSeqId) && UtilValidate.isNotEmpty(contentPurpose)) {
				jsonResponse.put("content", OrderHelper.getOrderItemContentPath(delegator, orderId, orderItemSeqId, contentPurpose));
				success = true;
			}
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while getting order item content." + e + " : " + e.getMessage(), module);
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Add additional item to an order
	 */
	public static String addItemToOrder(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", true);

		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try {
				context.remove("_");

				Map<String, Object> priceContext = new HashMap<String, Object>();
				GenericValue product = ProductHelper.getProduct(delegator, (String) context.get("productId"));
				priceContext.put("product", product);
				priceContext.put("quantity", new BigDecimal((String) context.get("quantity")));

				Map<String, Object> orderItemContext = new HashMap<String, Object>();
				Map<String, Object> priceResult = dispatcher.runSync("calculateProductPrice", priceContext);
				if(UtilValidate.isNotEmpty(priceResult.get("basePrice"))) {
					orderItemContext.put("unitPrice", (BigDecimal) priceResult.get("basePrice"));
					String orderItemSeqId = OrderHelper.getNextSeqId(delegator, null, (String) context.get("orderId"));
					orderItemContext.put("orderId", (String) context.get("orderId"));
					orderItemContext.put("orderItemSeqId", orderItemSeqId);
					orderItemContext.put("orderItemTypeId", "PRODUCT_ORDER_ITEM");
					orderItemContext.put("productId", (String) context.get("productId"));
					orderItemContext.put("quantity", new BigDecimal((String) context.get("quantity")));
					orderItemContext.put("prodCatalogId", "AECatalog");
					orderItemContext.put("isPromo", "N");
					orderItemContext.put("selectedAmount", BigDecimal.ZERO);
					orderItemContext.put("unitListPrice", BigDecimal.ZERO);
					orderItemContext.put("isModifiedPrice", "N");
					orderItemContext.put("itemDescription", product.getString("productName"));
					orderItemContext.put("statusId", "ITEM_CREATED"); //TODO: Create OrderStatus entry

					jsonResponse.put("orderItemSeqId", EnvUtil.removeChar("0", orderItemSeqId, true, false, false));
					jsonResponse.put("statusId", "ITEM_CREATED");
					jsonResponse.put("unitPrice", (BigDecimal) priceResult.get("basePrice"));

					OrderHelper.createOrderItem(delegator, orderItemContext);
					//OrderHelper.createOrderItemArtwork(delegator, context);
					//OrderHelper.createOrderItemAttribute(delegator, context);

					//now update all order totals
					request.setAttribute("saveResponse", true);
					String responseMap = updateOrderTotals(request, response);
				} else {
					jsonResponse.put("success", false);
					jsonResponse.put("error", "Error calculating product price.");
				}
			} catch(GenericEntityException e) {
				TransactionUtil.rollback(beganTransaction, "Error while trying to retrieve json data.", e);
				jsonResponse.clear();
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
			} catch(GenericServiceException e) {
				TransactionUtil.rollback(beganTransaction, "Error while trying to retrieve json data.", e);
				jsonResponse.clear();
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
			} catch(Exception e) {
				TransactionUtil.rollback(beganTransaction, "Error while trying to retrieve json data.", e);
				jsonResponse.clear();
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
			} finally {
				try {
					// only commit the transaction if we started one... this will throw an exception if it fails
					TransactionUtil.commit(beganTransaction);
				} catch(GenericEntityException e) {
					jsonResponse.put("success", false);
					Debug.logError(e, "Could not create new order item.", module);
				}
			}
		} catch(GenericTransactionException e) {
			jsonResponse.put("success", false);
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to save order item. " + e + " : " + e.getMessage(), module);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Add additional item to an order
	 */
	public static String updateItemInOrder(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", true);

		boolean isPrinted = "true".equals(((String) context.get("isPrinted")));

		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try {
				context.remove("_");

				String updatePrice = (String) context.remove("updatePrice");
				if(UtilValidate.isNotEmpty(context.get("orderId")) && UtilValidate.isNotEmpty(context.get("orderItemSeqId"))) {
					GenericValue orderItem = delegator.findOne("OrderItem", UtilMisc.toMap("orderId", (String) context.get("orderId"), "orderItemSeqId", (String) context.get("orderItemSeqId")), false);
					Map<String, Object> orderItemContext = new HashMap<>();

					if(isPrinted && !OrderHelper.isItemPrinted(delegator, null, null, orderItem)) {
						//check if the item was at some point printed
						GenericValue orderItemArtwork = EntityUtil.getFirst(delegator.findByAnd("OrderItemArtwork", UtilMisc.toMap("orderId", (String) context.get("orderId"), "orderItemSeqId", (String) context.get("orderItemSeqId")), UtilMisc.toList("createdStamp DESC"), false));

						Map<String, Object> oIA = new HashMap<>();
						oIA.put("orderItemArtworkId", (orderItemArtwork != null) ? orderItemArtwork.getString("orderItemArtworkId") : delegator.getNextSeqId("OrderItemArtwork"));
						oIA.put("orderId", (String) context.get("orderId"));
						oIA.put("orderItemSeqId", (String) context.get("orderItemSeqId"));

						if(orderItemArtwork != null) {
							OrderHelper.updateOrderItemArtwork(delegator, orderItemArtwork, oIA);
						} else {
							OrderHelper.createOrderItemArtwork(delegator, oIA);
						}

						OrderHelper.updateOrderItemAttribute(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"), UtilMisc.toMap("colorsFront", "1"));

						orderItemContext.put("artworkSource", "ART_UPLOADED_LATER");

						Map<String, Object> result = dispatcher.runSync("changeOrderItemStatus", UtilMisc.toMap("orderId", (String) context.get("orderId"), "orderItemSeqId", (String) context.get("orderItemSeqId"), "statusId", "ART_NOT_RECEIVED", "userLogin", (GenericValue) request.getSession().getAttribute("userLogin")));
					} else if(!isPrinted && OrderHelper.isItemPrinted(delegator, null, null, orderItem)) {
						OrderHelper.removeAllOrderItemAttributes(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"));

						orderItemContext.put("artworkSource", null);

						Map<String, Object> result = dispatcher.runSync("changeOrderItemStatus", UtilMisc.toMap("orderId", (String) context.get("orderId"), "orderItemSeqId", (String) context.get("orderItemSeqId"), "statusId", "ITEM_CREATED", "userLogin", (GenericValue) request.getSession().getAttribute("userLogin")));
					}

					if(UtilValidate.isNotEmpty(context.get("quantity"))) {
						orderItem.refresh();
						Map<String, Object> priceContext = OrderHelper.createPriceContextFromOrder(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"));
						GenericValue product = ProductHelper.getProduct(delegator, (String) context.get("productId"));
						priceContext.put("product", product);
						priceContext.put("quantity", new BigDecimal((String) context.get("quantity")));

						Map<String, Object> priceResult = dispatcher.runSync("calculateProductPrice", priceContext);
						if(UtilValidate.isNotEmpty(priceResult.get("basePrice"))) {
							orderItemContext.put("productId", product.getString("productId"));
							orderItemContext.put("itemDescription", product.getString("productName"));
							orderItemContext.put("unitPrice", (UtilValidate.isEmpty(updatePrice) || (UtilValidate.isNotEmpty(updatePrice) && "true".equalsIgnoreCase(updatePrice))) ? (BigDecimal) priceResult.get("basePrice") : orderItem.getBigDecimal("unitPrice"));
							orderItemContext.put("quantity", new BigDecimal((String) context.get("quantity")));

							jsonResponse.put("orderItemSeqId", EnvUtil.removeChar("0", (String) context.get("orderItemSeqId"), true, false, false));
							jsonResponse.put("unitPrice", (UtilValidate.isEmpty(updatePrice) || (UtilValidate.isNotEmpty(updatePrice) && "true".equalsIgnoreCase(updatePrice))) ? (BigDecimal) priceResult.get("basePrice") : orderItem.getBigDecimal("unitPrice"));
						} else {
							jsonResponse.put("success", false);
							jsonResponse.put("error", "Error calculating product price.");
						}
					}

					OrderHelper.updateOrderItem(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"), orderItemContext);

					//now update all order totals
					request.setAttribute("saveResponse", true);
					String responseMap = updateOrderTotals(request, response);
				}
			} catch(GenericEntityException e) {
				TransactionUtil.rollback(beganTransaction, "Error while trying to retrieve json data.", e);
				jsonResponse.clear();
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
			} catch(GenericServiceException e) {
				TransactionUtil.rollback(beganTransaction, "Error while trying to retrieve json data.", e);
				jsonResponse.clear();
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
			} catch(Exception e) {
				TransactionUtil.rollback(beganTransaction, "Error while trying to retrieve json data.", e);
				jsonResponse.clear();
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
			} finally {
				try {
					// only commit the transaction if we started one... this will throw an exception if it fails
					TransactionUtil.commit(beganTransaction);
				} catch(GenericEntityException e) {
					jsonResponse.put("success", false);
					Debug.logError(e, "Could not create new order item.", module);
				}
			}
		} catch(GenericTransactionException e) {
			jsonResponse.put("success", false);
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to save order item. " + e + " : " + e.getMessage(), module);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Update orderItem outsource data
	 * This is a temp function, will update all order items
	 */
	public static String updateOrderItemOutSourceData(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;

		try {
			if(UtilValidate.isNotEmpty(context.get("orderId"))) {
				List<GenericValue> orderItems = delegator.findByAnd("OrderItem", UtilMisc.toMap("orderId", (String) context.remove("orderId")), null, false);
				OrderHelper.setOutsourceable(delegator, orderItems, null, null, null, (String) context.get("outsourceable"));
			}
			success = true;
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to save order item. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Update orderItem change data
	 * This is a temp function, will update all order items
	 */
	public static String updateOrderItemPendingChangeData(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;

		try {
			if(UtilValidate.isNotEmpty(context.get("orderId"))) {
				List<GenericValue> orderItems = delegator.findByAnd("OrderItem", UtilMisc.toMap("orderId", (String) context.remove("orderId")), null, false);
				for(GenericValue orderItem: orderItems) {
					if(OrderHelper.isItemPrinted(delegator, null, null, orderItem)) {
						OrderHelper.updateOrderItem(delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId"), context);
					}
				}
			}
			success = true;
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to save order item. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Update orderItem flag data
	 * This is a temp function, will update all order items
	 */
	public static String updateOrderItemSyracuseable(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;

		try {
			if(UtilValidate.isNotEmpty(context.get("orderId"))) {
				List<GenericValue> orderItems = delegator.findByAnd("OrderItem", UtilMisc.toMap("orderId", (String) context.remove("orderId")), null, false);
				for(GenericValue orderItem: orderItems) {
					if(OrderHelper.isItemPrinted(delegator, null, null, orderItem)) {
						OrderHelper.updateOrderItemFlag(delegator, UtilMisc.toMap(
								"orderId", orderItem.getString("orderId"),
								"orderItemSeqId", orderItem.getString("orderItemSeqId"),
								"printableSyracuse", context.get("printableSyracuse")
						));
					}
				}
			}
			success = true;
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to save order item flag. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Update orderItem change data
	 * This is a temp function, will update all order items
	 */
	public static String updateOrderBlindShipData(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;

		try {
			if(UtilValidate.isNotEmpty(context.get("orderId"))) {
				OrderReadHelper orh = new OrderReadHelper(delegator, (String) context.get("orderId"));
				GenericValue shippingLocation = OrderHelper.getShippingAddress(orh, delegator, (String) context.get("orderId"));
				if(shippingLocation != null) {
					PartyHelper.createPartyContactMechAttribute(delegator, shippingLocation.getString("contactMechId"), "isBlindShipment", (String) context.get("isBlindShipment"));
				}
			}
			success = true;
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to save order item. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Update orderItem
	 */
	public static String setOrderItem(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;

		try {
			OrderHelper.updateOrderItem(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"), context);
			success = true;
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to save order item. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Update tax/shipping/grand total of an order
	 * This will create a temp cart object in the request and will be destroyed at the end
	 */
	public static String updateOrderTotals(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", true);

		try {
			if(UtilValidate.isNotEmpty(context.get("orderId"))) {
				String loadCart = ShoppingCartEvents.loadCartFromOrder(request, response);
				if(!loadCart.equals("error")) {
					OrderReadHelper orh = new OrderReadHelper(delegator, (String) context.get("orderId"));
					ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
					request.setAttribute("saveResponse", true);

					GenericValue shippingAddress = OrderHelper.getShippingAddress(orh, delegator, (String) context.get("orderId"));
					request.setAttribute("postalCode", shippingAddress.getString("postalCode"));

					ShippingEvents.getShippingRates(request, response);
					Map<String, Object> shippingRates = (Map<String, Object>) request.getAttribute("savedResponse");

					String shipmentMethodTypeId = orh.getShippingMethodType("00001");

					if(UtilValidate.isNotEmpty(shipmentMethodTypeId)) {
						List<Map> estimates = (List<Map>) shippingRates.get("estimates");
						Iterator estIter = estimates.iterator();
						while(estIter.hasNext()) {
							Map<String, Object> shipMethod = (Map<String, Object>) estIter.next();
							if(((String) shipMethod.get("method")).equals(shipmentMethodTypeId)) {
								GenericValue adj = OrderHelper.updateShippingCost(delegator, (String) context.get("orderId"), null, (BigDecimal) shipMethod.get("cost"), (String) shipMethod.get("carrierPartyId"), (String) shipMethod.get("method"));
								CartHelper.setShippingTotal(cart, 0, (BigDecimal) shipMethod.get("cost"));
							}
						}
					}

					//set tax
					CartHelper.setTaxTotal(dispatcher, null, cart);
					OrderHelper.updateTaxTotal(delegator, (String) context.get("orderId"), null, cart.getTotalSalesTax());

					//set grand total
					OrderHelper.recalculateOrderTotal(delegator, (String) context.get("orderId"));

					//destroy cart
					ShoppingCartEvents.destroyCart(request, response);

					//remove the save option so it can be outputted to screen
					request.removeAttribute("saveResponse");
				}
			}
		} catch (GenericEntityException e) {
			jsonResponse.put("success", false);
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to save order item. " + e + " : " + e.getMessage(), module);
		} catch (GenericServiceException e) {
			jsonResponse.put("success", false);
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to save order item. " + e + " : " + e.getMessage(), module);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String changeOrderPayment(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;
		String orderId = (String) context.get("orderId");

		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try{
				if (UtilValidate.isNotEmpty(orderId)) {
					OrderReadHelper orh = new OrderReadHelper(delegator, orderId);
					List<GenericValue> orderPPs = orh.getOrderPayments();
					for(GenericValue orderPP : orderPPs) {
						orderPP.set("thruDate", UtilDateTime.nowTimestamp());
						orderPP.store();
					}

					GenericValue newPP = OrderHelper.createPaymentMethod(delegator, (String) context.get("paymentMethodType"), orh.getPlacingParty().getString("partyId"));
					if("CREDIT_CARD".equals((String) context.get("paymentMethodType"))) {
						GenericValue billingAddress = OrderHelper.getBillingAddress(orh, delegator, orderId);
						String[] name = billingAddress.getString("toName").split("\\s+");
						Map<String, String> ccData = new HashMap<>();
						ccData.put("paymentMethodId", newPP.getString("paymentMethodId"));
						ccData.put("cardNumber", (String) context.get("cardNumber"));
						ccData.put("expireDate", (String) context.get("expireDate"));
						ccData.put("firstNameOnCard", UtilValidate.isNotEmpty(name[0]) ? name[0] : "");
						ccData.put("lastNameOnCard", UtilValidate.isNotEmpty(name[1]) ? name[1] : "");
						ccData.put("contactMechId", billingAddress.getString("contactMechId"));

						GenericValue creditCard = OrderHelper.createCreditCard(delegator, ccData);

						Map<String, Object> oppData = new HashMap<>();
						oppData.put("orderId", orderId);
						oppData.put("paymentMethodTypeId", (String) context.get("paymentMethodType"));
						oppData.put("paymentMethodId", newPP.getString("paymentMethodId"));
						oppData.put("statusId", "PAYMENT_NOT_AUTH");
						oppData.put("presentFlag", "N");
						oppData.put("swipedFlag", "N");
						oppData.put("overflowFlag", "N");
						oppData.put("maxAmount", orh.getOrderGrandTotal());
						oppData.put("securityCode", (String) context.get("cvv"));
						oppData.put("createdDate", UtilDateTime.nowTimestamp());
						oppData.put("createdByUserLogin", userLogin.getString("userLoginId"));
						GenericValue oPP = OrderHelper.createOrderPaymentPreference(delegator, oppData);
					}

					Map<String, Object> authResult = dispatcher.runSync("authOrderPayments", UtilMisc.toMap("orderId", orderId, "reAuth", false, "userLogin", userLogin));
					success = true;
				}
			} catch(Exception e) {
				TransactionUtil.rollback(beganTransaction, "Error updating payment method in order:" + orderId, e);
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to update shipping address in order:" + orderId + e + " : " + e.getMessage(), module);
			} finally {
				try {
					TransactionUtil.commit(beganTransaction);
				} catch(GenericEntityException e) {
					Debug.logError(e, "Could not update shipping address.", module);
					success = false;
				}
			}
		} catch(GenericTransactionException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to update shipping address  in order:" + orderId  + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String updateShippingAddress(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;
		String orderId = (String)context.get("orderId");
		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try{
				if (UtilValidate.isNotEmpty(orderId)) {
					OrderReadHelper orh = new OrderReadHelper(delegator, orderId);
					String partyId = orh.getPlacingParty().getString("partyId");
					GenericValue shippingContactMech = PartyHelper.createPostalAddress(delegator, partyId, PartyHelper.getAddressData(request), "SHIPPING_LOCATION");
					updateOrderContactMech(delegator, orderId, shippingContactMech.getString("contactMechId"), "SHIPPING_LOCATION");
					success = true;
				}
			} catch(Exception e) {
				TransactionUtil.rollback(beganTransaction, "Error updating shipping address in order:" + orderId, e);
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to update shipping address in order:" + orderId + e + " : " + e.getMessage(), module);
			} finally {
				try {
					TransactionUtil.commit(beganTransaction);
				} catch(GenericEntityException e) {
					Debug.logError(e, "Could not update shipping address.", module);
					success = false;
				}
			}
		} catch(GenericTransactionException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to update shipping address  in order:" + orderId  + e + " : " + e.getMessage(), module);
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String updateBillingAddress(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;
		String orderId = (String)context.get("orderId");
		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try{
				if (UtilValidate.isNotEmpty(orderId)) {
					OrderReadHelper orh = new OrderReadHelper(delegator, orderId);
					String partyId = orh.getPlacingParty().getString("partyId");
					String paymentMethodId = orh.getPaymentPreferences().get(0).getString("paymentMethodId");
					Map addressMap = PartyHelper.getAddressData(request);
					GenericValue billingContactMech = PartyHelper.createPostalAddress(delegator, partyId, addressMap, "BILLING_LOCATION");
					updateOrderContactMech(delegator, orderId, billingContactMech.getString("contactMechId"), "BILLING_LOCATION");
					if(OrderHelper.isCreditCardOrder(orh, delegator, orderId)) {
						updateCreditCard(delegator, paymentMethodId, UtilMisc.toMap("contactMechId", billingContactMech.getString("contactMechId"), "firstNameOnCard", addressMap.get("firstName"), "lastNameOnCard", addressMap.get("lastName")));
					}
					success = true;
				}
			} catch(Exception e) {
				TransactionUtil.rollback(beganTransaction, "Error updating billing address in order:" + orderId, e);
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to update billing address in order:" + orderId + e + " : " + e.getMessage(), module);
			} finally {
				try {
					TransactionUtil.commit(beganTransaction);
				} catch(GenericEntityException e) {
					Debug.logError(e, "Could not update billing address.", module);
					success = false;
				}
			}
		} catch(GenericTransactionException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to update billing address  in order:" + orderId  + e + " : " + e.getMessage(), module);
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String updatePhone(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;
		String orderId = (String) context.get("orderId");
		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try{
				if(UtilValidate.isNotEmpty(orderId)) {
					PartyHelper.updateTelecomNumber(delegator, (String) context.get("contactMechId"), UtilMisc.<String, String>toMap("contactNumber", (String) context.get("contactNumber")));
					success = true;
				}
			} catch(Exception e) {
				TransactionUtil.rollback(beganTransaction, "Error updating phone in order:" + orderId, e);
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to update phone in order:" + orderId + e + " : " + e.getMessage(), module);
			} finally {
				try {
					TransactionUtil.commit(beganTransaction);
				} catch(GenericEntityException e) {
					Debug.logError(e, "Could not update phone.", module);
					success = false;
				}
			}
		} catch(GenericTransactionException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to update phone in order:" + orderId  + e + " : " + e.getMessage(), module);
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String updateEmailAddress(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;
		String orderId = (String)context.get("orderId");
		String email = (String)context.get("email");
		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try{
				if (UtilValidate.isNotEmpty(orderId)) {
					OrderReadHelper orh = new OrderReadHelper(delegator, orderId);
					String partyId = orh.getPlacingParty().getString("partyId");
					GenericValue emailContactMech = PartyHelper.getMatchedEmailAddress(delegator, email, partyId, "EMAIL_ADDRESS");
					if(emailContactMech == null) {
						emailContactMech = PartyHelper.createPartyContactMech(delegator, partyId, "EMAIL_ADDRESS", email);
					}
					updateOrderContactMech(delegator, orderId, emailContactMech.getString("contactMechId"), "ORDER_EMAIL");
					success = true;
				}
			} catch(Exception e) {
				TransactionUtil.rollback(beganTransaction, "Error updating email address in order:" + orderId, e);
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to update email address in order:" + orderId + e + " : " + e.getMessage(), module);
			} finally {
				try {
					TransactionUtil.commit(beganTransaction);
				} catch(GenericEntityException e) {
					Debug.logError(e, "Could not update email address.", module);
					success = false;
				}
			}
		} catch(GenericTransactionException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to update email address  in order:" + orderId  + e + " : " + e.getMessage(), module);
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String updatePayment(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;
		String orderId = (String)context.get("orderId");
		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try{
				if (UtilValidate.isNotEmpty(orderId)) {
					OrderReadHelper orh = new OrderReadHelper(delegator, orderId);
					String partyId = orh.getPlacingParty().getString("partyId");
					//TODO
					success = true;
				}
			} catch(Exception e) {
				TransactionUtil.rollback(beganTransaction, "Error updating payment in order:" + orderId, e);
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to update payment in order:" + orderId + e + " : " + e.getMessage(), module);
			} finally {
				try {
					TransactionUtil.commit(beganTransaction);
				} catch(GenericEntityException e) {
					Debug.logError(e, "Could not update billing address.", module);
					success = false;
				}
			}
		} catch(GenericTransactionException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to update billing address  in order:" + orderId  + e + " : " + e.getMessage(), module);
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	protected static void updateOrderContactMech(Delegator delegator, String orderId, String contactMechId, String contactMechPurposeTypeId) throws GenericEntityException {
		List<GenericValue> orderContactMechs = delegator.findByAnd("OrderContactMech", UtilMisc.toMap("orderId", orderId, "contactMechPurposeTypeId", contactMechPurposeTypeId), null, false);
		delegator.removeAll(orderContactMechs);
		GenericValue orderContactMech = delegator.makeValue("OrderContactMech", UtilMisc.toMap("orderId", orderId, "contactMechId", contactMechId, "contactMechPurposeTypeId", contactMechPurposeTypeId));
		delegator.create(orderContactMech);
	}

	protected static void updateCreditCard(Delegator delegator, String paymentMethodId, Map attributes) throws GenericEntityException {
		GenericValue payment = delegator.findOne("CreditCard", UtilMisc.toMap("paymentMethodId", paymentMethodId), false);
		payment.putAll(attributes);
		delegator.store(payment);
	}


	/*
	 * Get a list of the order totals and order level adjustments
	 */
	public static String getOrderTotals(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", true);

		try {
			if(UtilValidate.isNotEmpty(context.get("orderId"))) {
				jsonResponse.put("data", OrderHelper.getOrderTotals(delegator, (String) context.get("orderId")));
			}
		} catch (GenericEntityException e) {
			jsonResponse.put("success", false);
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to save order item. " + e + " : " + e.getMessage(), module);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	private static void applyOrderList(Map<String, Object> orderHeader, ArrayList<Map> orderItems, ArrayList<Map> orderList) {
		orderHeader.put("orderItems", orderItems);
		orderList.add(orderHeader);
	}

	/*
	 * Get due date
	 */
	public static String getDueDate(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		jsonResponse.put("success", true);
		Map<String, Integer> leadTime = null;
		Boolean isRush = null;

		try {
			leadTime = ProductHelper.getLeadTime(delegator, (String) context.get("productId"));
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to get the product lead time from getDueDate method. " + e + " : " + e.getMessage(), module);
		}

		if(UtilValidate.isNotEmpty(context.get("isRush"))) {
			isRush = Boolean.valueOf((String) context.get("isRush"));
		} else if(UtilValidate.isNotEmpty(request.getAttribute("isRush"))) {
			isRush = ((Boolean) request.getAttribute("isRush"));
		}

		jsonResponse.put("leadTime", leadTime);

		if(UtilValidate.isEmpty(isRush)) {
			//standard date
			jsonResponse.put("standardDate", EnvConstantsUtil.WMD.format(new Date(OrderHelper.getDueDate(leadTime.get("leadTimeStandardPrinted"), null).getTime())));

			//rush date
			jsonResponse.put("rushDate", EnvConstantsUtil.WMD.format(new Date(OrderHelper.getDueDate(leadTime.get("leadTimeRushPrinted"), null).getTime())));
		} else {
			jsonResponse.put("date", EnvConstantsUtil.WMD.format(new Date(OrderHelper.getDueDate(leadTime, isRush, false, true, null).getTime())));
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Set due date
	 */
	public static String setDueDate(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;

		try {
			dispatcher.runSync("updateDueDate", UtilMisc.toMap("orderId", context.get("orderId"), "orderItemSeqId", context.get("orderItemSeqId"), "dueDate", context.get("dueDate"), "userLogin", (GenericValue) request.getSession().getAttribute("userLogin")));
			success = true;
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to set due date. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get orders and their files for a given user
	 */
	public static String getOrderAndContent(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		boolean success = false;
		if(request.getSession().getAttribute("userLogin") != null) {
			try {
				jsonResponse.put("orderAndContent", OrderHelper.getOrderItemContents(delegator, (GenericValue) request.getSession().getAttribute("userLogin"), (String) context.get("contentEnumTypeId")));
				success = true;
			} catch(GenericEntityException e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to get user order and content. " + e + " : " + e.getMessage(), module);
			}
		} else {
			success = false;
			jsonResponse.put("error", "You are not logged in, please log in.");
		}

		jsonResponse.put("success", success);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get the print options for an order item
	 */
	public static String getOrderPrintOptions(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		boolean success = false;
		if(UtilValidate.isNotEmpty((String) context.get("orderId")) && UtilValidate.isNotEmpty((String) context.get("orderItemSeqId"))) {
			try {
				jsonResponse.put("printOptions", OrderHelper.getOrderItemAttributeMap(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId")));
				success = true;
			} catch(GenericEntityException e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to get order item attributes. " + e + " : " + e.getMessage(), module);
			}
		}

		jsonResponse.put("success", success);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Add order Note
	 */
	public static String addOrderNote(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		context.put("userLogin", (GenericValue) request.getSession().getAttribute("userLogin"));
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		boolean success = false;

		try {
			jsonResponse.put("notes", OrderHelper.addOrderNotes(delegator, dispatcher, context));
			success = true;
		} catch(GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to set order note. " + e + " : " + e.getMessage(), module);
		} catch(GenericServiceException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to set order note. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get customers artwork for files when they do upload later
	 */
	public static String approveOrRejectProof(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		try {
			jsonResponse = OrderHelper.approveOrRejectProof(request, dispatcher, delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"), (("true".equals((String) context.get("approved"))) ? "ART_PROOF_APPROVED" : "ART_PROOF_REJECTED"), jsonResponse);
//			if ((Boolean) jsonResponse.get("success") && (context.get("addressing").equals("true") || context.get("scene7").equals("true")) && context.get("upload").equals("false") && ("false".equals((String) context.get("approved")))) {
//				jsonResponse = OrderHelper.approveOrRejectProof(request, dispatcher, delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"), "ART_READY_FOR_REVIEW", jsonResponse);
//			}

			if (UtilValidate.isNotEmpty((String) context.get("message"))) {
				OrderHelper.createArtworkComment(delegator, UtilMisc.toMap("orderItemArtworkId", (String) context.get("orderItemArtworkId"), "message", (String) context.get("message"), "typeEnumId", (("true".equals((String) context.get("approved"))) ? "OIAC_APPROVEPROOF" : "OIAC_REJECTPROOF")), (GenericValue) request.getSession().getAttribute("userLogin"));
			}
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to update order item status. " + e + " : " + e.getMessage(), module);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Order queue requests
	 */
	public static String getOrderQueue(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		boolean success = false;

		Map<String, Object> jsonResponse = new HashMap<>();

		try {
			Map<String, List> queues = OrderHelper.orderListQueue(dispatcher, delegator);
			jsonResponse.put("queues", queues);
			success = true;
		} catch(GenericEntityException e) {
			EnvUtil.reportError(e);
		}

		jsonResponse.put("success" , success);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Order queue requests
	 */
	public static String getPrepressQueue(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		boolean success = false;

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();

		try {
			List<Map> queues = OrderHelper.prepressQueue(delegator, (String) context.get("webSiteId"));
			jsonResponse.put("queues", queues);
			success = true;
		} catch(GenericEntityException e) {
			EnvUtil.reportError(e);
		}

		jsonResponse.put("success", success);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Order list for pre press
	 */
	public static String prepressOrderAndItemList(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		boolean success = false;

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();

		try {
			List<Map> orders = OrderHelper.prepressOrderAndItemList(delegator, context);
			jsonResponse.put("orders", orders);
			success = true;
		} catch(GenericEntityException e) {
			EnvUtil.reportError(e);
		}

		jsonResponse.put("success" , success);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Upload PDF and save to db
	 */
	public static String uploadPrintOrderFile(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		boolean success = false;

		try {
			jsonResponse = FileHelper.uploadFile(request, null, false, false);
		} catch(FileUploadException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		}
		if(UtilValidate.isEmpty(jsonResponse)) {
			jsonResponse.put("error", "Could not upload files, please try again.");
		}
		else {
			try {
				Map<String, Object> file = (HashMap) ((ArrayList) jsonResponse.get("files")).get(0);
				OrderHelper.setOrderItemContent(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"), "OIACPRP_FILE", file);
				success = true;
			}
			catch (GenericEntityException e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to store Uploaded Files to DB - " + e + " : " + e.getMessage(), module);
			}
		}

		jsonResponse.put("success", success);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Check of picked stock status
	 */
	public static String pickOrderItemStock(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		boolean success = false;

		if(UtilValidate.isNotEmpty(context.get("orders"))) {
			List<String> orderList = Arrays.asList(((String) context.get("orders")).split(","));

			try {
				boolean beganTransaction = TransactionUtil.begin();

				try {
					for(String order : orderList) {
						OrderHelper.updateStockPickStatus(delegator, order.substring(0, order.indexOf("_")), order.substring(order.indexOf("_") + 1), "true".equalsIgnoreCase((String) context.get("picked")));
					}

					TransactionUtil.commit(beganTransaction);
					success = true;
				} catch (GenericEntityException e1) {
					EnvUtil.reportError(e1);
					Debug.logError(e1, "Error trying to update pick status for order item", module);
					TransactionUtil.rollback(beganTransaction, "Error trying to update pick status for order item", e1);
				}
			} catch (GenericTransactionException e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "Error trying to update pick status for order item", module);
			}
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Check of picked stock status
	 */
	public static String pickInk(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		boolean success = false;

		if(UtilValidate.isNotEmpty(context.get("order"))) {
			List<String> orderList = Arrays.asList(((String) context.get("order")).split(","));

			try {
				boolean beganTransaction = TransactionUtil.begin();

				try {
					String order = (String) context.get("order");
					OrderHelper.updateInkPickStatus(delegator, order.substring(0, order.indexOf("_")), order.substring(order.indexOf("_") + 1), "true".equalsIgnoreCase((String) context.get("picked")));

					TransactionUtil.commit(beganTransaction);
					success = true;
				} catch (GenericEntityException e1) {
					EnvUtil.reportError(e1);
					Debug.logError(e1, "Error trying to update pick status for order item", module);
					TransactionUtil.rollback(beganTransaction, "Error trying to update pick status for order item", e1);
				}
			} catch (GenericTransactionException e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "Error trying to update pick status for order item", module);
			}
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String getPrintJobData(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;

		if(EnvUtil.authorizedRESTRequest((String) context.get("key"))) {
			try {
				jsonResponse.put("jobData", OrderHelper.getPrintJobAttributes(delegator, (String) context.get("jobId")));
				success = true;
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "Error trying to get Print Job data for JobId:" + (String) context.get("jobId"), module);
				jsonResponse.put("error", e.getMessage());
			}
		} else {
			jsonResponse.put("error", "Authorization failed.");
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String getPrintJobStatus(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;

		if(EnvUtil.authorizedRESTRequest((String) context.get("key"))) {
			try {
				jsonResponse.put("jobStatus", OrderHelper.getPrintJobStatus(delegator, ((String)context.get("jobIds")).split("__")));
				success = true;
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "Error trying to get Print Job status for JobId(s):" + ((String) context.get("jobIds")).replaceAll("__", ", "), module);
				jsonResponse.put("error", e.getMessage());
			}
		} else {
			jsonResponse.put("error", "Authorization failed.");
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String sendOrderConfirmation(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;

		try {
			Map<String,Object> orderData = OrderHelper.getOrderData(delegator, (String) context.get("orderId"), false);
			Map<String, Object> orderInfo = OrderHelper.buildOrderDetails(request, orderData);
			Map<String, String> emailData = EmailHelper.createOrderConfirmationEmailData(delegator, dispatcher, orderData, (String) context.get("orderId"), EnvUtil.getWebsiteId(((GenericValue) orderData.get("orderHeader")).getString("salesChannelEnumId")));
			if(OrderHelper.isOrderEnvelopeOnly(delegator, (String) context.get("orderId"))) {
				//dispatcher.runSync("sendEmail", UtilMisc.toMap("email", orderInfo.get("email"), "rawData", orderData, "data", emailData, "messageType", "orderConfirmation2", "webSiteId", EnvUtil.getWebsiteId(((GenericValue) orderData.get("orderHeader")).getString("salesChannelEnumId"))));
			} else {
				//dispatcher.runSync("sendEmail", UtilMisc.toMap("email", orderInfo.get("email"), "rawData", orderData, "data", emailData, "messageType", "orderConfirmation", "webSiteId", EnvUtil.getWebsiteId(((GenericValue) orderData.get("orderHeader")).getString("salesChannelEnumId"))));
			}
			success = true;
		} catch (Exception e) {
			EnvUtil.reportError(e);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String sendBusinessReviewEmail(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;

		try {
			OrderReadHelper orh = null;
			if(UtilValidate.isNotEmpty(context.get("orderId"))) {
				orh = new OrderReadHelper(delegator, (String) context.get("orderId"));
			} else if(UtilValidate.isNotEmpty(context.get("reviewId"))) {
				GenericValue review = EntityQuery.use(delegator).from("ProductReview").where("productReviewId", (String) context.get("reviewId")).queryFirst();
				if (review != null && UtilValidate.isNotEmpty(review.getString("orderId"))) {
					orh = new OrderReadHelper(delegator, (String) review.get("orderId"));
				}
			}

			if(orh != null) {
				dispatcher.runSync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "ReviewBusiness", "data", new HashMap(), "email", orh.getOrderEmailString(), "webSiteId", EnvUtil.getWebsiteId(orh.getOrderHeader().getString("salesChannelEnumId"))));
				success = true;
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/**
	 * Import bulk orders via excel sheet
	 * @param request
	 * @param response
	 * @return
	 */
	public static String bulkImportOrders(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;

		CellProcessor[] processors = new CellProcessor[] {
			new Optional(new Trim()), //Customer
			new NotNull(new Trim()),  //Order ID
			new Optional(new Trim()), //Bill To Name
			new Optional(new Trim()), //Bill To Contact Name
			new Optional(new Trim()), //Bill To Street 1
			new Optional(new Trim()), //Bill To Street 2
			new Optional(new Trim()), //Bill To Street 3
			new Optional(new Trim()), //Bill To City
			new Optional(new Trim()), //Bill To State/Province
			new Optional(new Trim()), //Bill To ZIP/Postal Code
			new Optional(new Trim()), //Bill To Phone
			new Optional(new Trim()), //Email_Address
			new Optional(new Trim()), //Owner
			new Optional(new Trim()), //Ship To Name
			new Optional(new Trim()), //Ship To Contact Name
			new Optional(new Trim()), //Ship To Street 1
			new Optional(new Trim()), //Ship To Street 2
			new Optional(new Trim()), //Ship To Street 3
			new Optional(new Trim()), //Ship To City
			new Optional(new Trim()), //Ship To State/Province
			new Optional(new Trim()), //Ship To ZIP/Postal Code
			new Optional(new Trim()), //Ship To Phone
			new Optional(new Trim()), //Email_address_1
			new Optional(new Trim()), //Shipping Address 2 Name
			new Optional(new Trim()), //Shipping Address 2 Contact
			new Optional(new Trim()), //Shipping Address 2 Street 1
			new Optional(new Trim()), //Shipping Address 2 Street 2
			new Optional(new Trim()), //Shipping Address 2 City
			new Optional(new Trim()), //Shipping Address 2 State
			new Optional(new Trim()), //Shipping Address 2 Zip
			new Optional(new Trim()), //Phone
			new Optional(new Trim()), //E-Mail
			new Optional(new Trim()), //Style
			new Optional(new Trim()), //Folder Style Description
			new Optional(new Trim()), //Quantity Ordered
			new Optional(new Trim()), //Total
			new Optional(new Trim()), //Paper
			new Optional(new Trim()), //Print Method
			new Optional(new Trim()), //Right Pocket Option
			new Optional(new Trim()), //RP Slit Position
			new Optional(new Trim()), //Left Pocket Option
			new Optional(new Trim()), //LP Slit Position
			new Optional(new Trim()), //Center Pocket Option
			new Optional(new Trim()), //Center Slit Position
			new Optional(new Trim()), //Pocket Slit Instructions
			new Optional(new Trim()), //Outside 4 Color Process
			new Optional(new Trim()), //Coating
			new Optional(new Trim()), //Outside Color 1
			new Optional(new Trim()), //Outside Color 2
			new Optional(new Trim()), //Outside Color 3
			new Optional(new Trim()), //Inside 4 Color Process
			new Optional(new Trim()), //Inside Coating
			new Optional(new Trim()), //Inside Color 1
			new Optional(new Trim()), //Inside Color 2
			new Optional(new Trim()), //Total Images
			new Optional(new Trim()), //Images Foil Run 1
			new Optional(new Trim()), //Run 1 Image Color
			new Optional(new Trim()), //Images Foil Run 2
			new Optional(new Trim()), //Run 2 Image Color
			new Optional(new Trim()), //Images Foil Run 3
			new Optional(new Trim()), //Run 3 Image Color
			new Optional(new Trim()), //Images Emboss Run 1
			new Optional(new Trim()), //Images Emboss Run 2
			new Optional(new Trim()), //Screen Inks
			new Optional(new Trim()), //Vendor
			new Optional(new Trim()), //Vendor Job / Order #
			new Optional(new Trim()), //Vendor Base Price
			new Optional(new Trim()), //Vendor Invoice Total
			new Optional(new Trim()), //Vendor Invoice/Order#
			new Optional(new Trim()), //Vendor actual Ship Date
			new Optional(new Trim()), //Status
		};

		String fileLocation = null;
		ICsvMapReader mapReader = null;
		StringBuilder errorMsg = new StringBuilder();

		try {
			Map<String, Object> fileData = FileHelper.uploadFile(request, EnvConstantsUtil.UPLOAD_DIR + "/" + EnvConstantsUtil.PRODUCT_UPLOAD_DIR, false, false);
			if (UtilValidate.isNotEmpty(fileData) && UtilValidate.isNotEmpty(fileData.get("files"))) {
				List<Map> files = (List<Map>) fileData.get("files");
				for (Map file : files) {
					Iterator iter = file.entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry pairs = (Map.Entry) iter.next();
						if ("path".equalsIgnoreCase((String) pairs.getKey())) {
							fileLocation = EnvConstantsUtil.OFBIZ_HOME + ((String) pairs.getValue());
							break;
						}
					}
				}
			}

			if (UtilValidate.isNotEmpty(fileLocation)) {
				Map<String, Map<String, Object>> orderData = new HashMap<>();
				mapReader = new CsvMapReader(new FileReader(fileLocation), CsvPreference.STANDARD_PREFERENCE);

				//the header columns are used as the keys to the Map
				String[] headers = mapReader.getHeader(true);
				Map<String, Object> dataMap;
				while ((dataMap = mapReader.read(headers, processors)) != null) {
					String orderId = (String) dataMap.get("Order ID");
					if(UtilValidate.isEmpty(orderId)) {
						continue;
					}

					List<Map<String, Object>> products = new ArrayList<>();
					if(orderData.containsKey(orderId)) {
						products = (List<Map<String, Object>>) ((Map<String, Object>) orderData.get(orderId)).get("tempCart");
					}

					Map<String, String> tempShippingAddress = new HashMap<>();
					String[] shipName = ((String) dataMap.get("Ship To Contact Name")).split(" ");
					tempShippingAddress.put("shipping_firstName", (UtilValidate.isNotEmpty(shipName[0])) ? shipName[0] : "");
					tempShippingAddress.put("shipping_lastName", (shipName.length > 1 && UtilValidate.isNotEmpty(shipName[1])) ? shipName[1] : "");
					tempShippingAddress.put("shipping_address1", (String) dataMap.get("Ship To Street 1"));
					tempShippingAddress.put("shipping_address2", (String) dataMap.get("Ship To Street 2"));
					tempShippingAddress.put("shipping_city", (String) dataMap.get("Ship To City"));
					tempShippingAddress.put("shipping_postalCode", (String) dataMap.get("Ship To ZIP/Postal Code"));
					tempShippingAddress.put("shipping_stateProvinceGeoId", (String) dataMap.get("Ship To State/Province"));
					tempShippingAddress.put("shipping_countryGeoId", "USA");

					Map<String, String> tempBillingAddress = new HashMap<>();
					String[] billName = ((String) dataMap.get("Bill To Contact Name")).split(" ");
					tempBillingAddress.put("billing_firstName", (UtilValidate.isNotEmpty(billName[0])) ? billName[0] : "");
					tempBillingAddress.put("billing_lastName", (billName.length > 1 && UtilValidate.isNotEmpty(billName[1])) ? billName[1] : "");
					tempBillingAddress.put("billing_address1", (String) dataMap.get("Bill To Street 1"));
					tempBillingAddress.put("billing_address2", (String) dataMap.get("Bill To Street 2"));
					tempBillingAddress.put("billing_city", (String) dataMap.get("Bill To City"));
					tempBillingAddress.put("billing_postalCode", (String) dataMap.get("Bill To ZIP/Postal Code"));
					tempBillingAddress.put("billing_stateProvinceGeoId", (String) dataMap.get("Bill To State/Province"));
					tempBillingAddress.put("billing_countryGeoId", "USA");

					BigDecimal quantity = new BigDecimal((String) dataMap.get("Quantity Ordered"));
					BigDecimal itemTotal = new BigDecimal(((String) dataMap.get("Total")).replaceAll("\\$", "").replaceAll(",", ""));

					Map<String, Object> productData = new HashMap<>();
					productData.put("unitPrice", quantity.divide(itemTotal, EnvConstantsUtil.ENV_SCALE_P, EnvConstantsUtil.ENV_ROUNDING));
					productData.put("quantity", quantity);
					productData.put("productId", "CUSTOM-P");
					productData.put("totalPrice", itemTotal);
					productData.put("price", itemTotal.toPlainString());
					productData.put("index", 0);
					productData.put("envPriceCalcAttributes", UtilMisc.toMap("quantity", quantity, "id", "CUSTOM-P", "name", getProductName(dataMap)));
					productData.put("envQuoteAttributes", new HashMap());
					productData.put("isPromo", false);
					productData.put("envPriceResultAttributes", UtilMisc.toMap("price", itemTotal));
					productData.put("envArtworkAttributes", UtilMisc.toMap("artworkSource", "ART_UPLOADED", "isProduct", true));
					productData.put("orderItemTypeId", "PRODUCT_ORDER_ITEM");
					productData.put("name", getProductName(dataMap));
					products.add(productData);

					Map<String, Object> data = new HashMap<>();
					data.put("tempShippingAddress", tempShippingAddress);
					data.put("tempBillingAddress", tempBillingAddress);
					data.put("tempPaymentMethodTypeId", "EXT_CLOSED");
					data.put("tempShippingPhone", (String) dataMap.get("Ship To Phone"));
					data.put("tempBillingPhone", (String) dataMap.get("Bill To Phone"));
					data.put("tempEmail", (String) dataMap.get("Email_Address"));
					data.put("externalId", orderId);
					data.put("shipDetails", UtilMisc.<String, Object>toMap("shipMethod", "UPS_GROUND", "carrierPartyId", "UPS", "shipCost", BigDecimal.ZERO, "duty", BigDecimal.ZERO, "tax", BigDecimal.ZERO));
					data.put("tempCart", products);
					data.put("_WEB_SITE_ID_", "folders");
					data.put("promoDetails", new ArrayList());

					Timestamp orderDate = UtilDateTime.nowTimestamp();
					if(UtilValidate.isNotEmpty((String) dataMap.get("Vendor actual Ship Date"))) {
						try {
							Date initDate = EnvConstantsUtil.MDY.parse((String) dataMap.get("Vendor actual Ship Date"));
							SimpleDateFormat formatter = EnvConstantsUtil.timeStamp;
							orderDate = Timestamp.valueOf(formatter.format(initDate));
						} catch (Exception dateException) {
							//
						}
					}

					data.put("orderDate", orderDate);
					orderData.put(orderId, data);
				}

				//loop through items
				Iterator orderIter = orderData.entrySet().iterator();
				while(orderIter.hasNext()) {
					Map.Entry pair = (Map.Entry) orderIter.next();
					Map<String, Object> data = (Map<String, Object>) pair.getValue();
					Iterator dataIter = data.entrySet().iterator();
					while(dataIter.hasNext()) {
						Map.Entry dataPair = (Map.Entry) dataIter.next();
						request.setAttribute((String) dataPair.getKey(), dataPair.getValue());
					}
					Order.createOrder(request, response);
					System.out.println("Completed Order: " + (String) pair.getKey());
				}
			}

			success = true;
		} catch (Exception e) {
			Debug.logError(e, "Error: " + e.getMessage(), module);
		} finally {
			try {
				if (mapReader != null) {
					mapReader.close();
				}
			} catch (IOException e) {
				Debug.logError(e, "Error: " + e.getMessage(), module);
			}
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/**
	 * Get the product Name from the data
	 * This is for the bulk import function above
	 * @param dataMap
	 */
	private static String getProductName(Map<String, Object> dataMap) {
		StringBuilder name = new StringBuilder();
		if(UtilValidate.isNotEmpty(dataMap.get("Folder Style Description"))) {
			name.append((String) dataMap.get("Folder Style Description"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Paper"))) {
			name.append(" ").append((String) dataMap.get("Paper"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Print Method"))) {
			name.append("\nPrint Method: ").append((String) dataMap.get("Print Method"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Right Pocket Option"))) {
			name.append("\nRight Pocket Option: ").append((String) dataMap.get("Right Pocket Option"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("RP Slit Position"))) {
			name.append("\nRP Slit Position: ").append((String) dataMap.get("RP Slit Position"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Left Pocket Option"))) {
			name.append("\nLeft Pocket Option: ").append((String) dataMap.get("Left Pocket Option"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("LP Slit Position"))) {
			name.append("\nLP Slit Position: ").append((String) dataMap.get("LP Slit Position"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Center Pocket Option"))) {
			name.append("\nCenter Pocket Option: ").append((String) dataMap.get("Center Pocket Option"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Center Slit Position"))) {
			name.append("\nCenter Slit Position: ").append((String) dataMap.get("Center Slit Position"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Pocket Slit Instructions"))) {
			name.append("\nPocket Slit Instructions: ").append((String) dataMap.get("Pocket Slit Instructions"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Outside 4 Color Process"))) {
			name.append("\nOutside 4 Color Process: ").append((String) dataMap.get("Outside 4 Color Process"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Coating"))) {
			name.append("\nCoating: ").append((String) dataMap.get("Coating"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Outside Color 1"))) {
			name.append("\nOutside Color 1: ").append((String) dataMap.get("Outside Color 1"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Outside Color 2"))) {
			name.append("\nOutside Color 2: ").append((String) dataMap.get("Outside Color 2"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Outside Color 3"))) {
			name.append("\nOutside Color 3: ").append((String) dataMap.get("Outside Color 3"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Inside 4 Color Process"))) {
			name.append("\nInside 4 Color Process: ").append((String) dataMap.get("Inside 4 Color Process"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Inside Coating"))) {
			name.append("\nInside Coating: ").append((String) dataMap.get("Inside Coating"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Inside Color 1"))) {
			name.append("\nInside Color 1: ").append((String) dataMap.get("Inside Color 1"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Inside Color 2"))) {
			name.append("\nInside Color 2: ").append((String) dataMap.get("Inside Color 2"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Total Images"))) {
			name.append("\nTotal Images: ").append((String) dataMap.get("Total Images"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Images Foil Run 1"))) {
			name.append("\nImages Foil Run 1: ").append((String) dataMap.get("Images Foil Run 1"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Run 1 Image Color"))) {
			name.append("\nRun 1 Image Color: ").append((String) dataMap.get("Run 1 Image Color"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Images Foil Run 2"))) {
			name.append("\nImages Foil Run 2: ").append((String) dataMap.get("Images Foil Run 2"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Run 2 Image Color"))) {
			name.append("\nRun 2 Image Color: ").append((String) dataMap.get("Run 2 Image Color"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Images Foil Run 3"))) {
			name.append("\nImages Foil Run 3: ").append((String) dataMap.get("Images Foil Run 3"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Run 3 Image Color"))) {
			name.append("\nRun 3 Image Color: ").append((String) dataMap.get("Run 3 Image Color"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Images Emboss Run 1"))) {
			name.append("\nImages Emboss Run 1: ").append((String) dataMap.get("Images Emboss Run 1"));
		}
		if(UtilValidate.isNotEmpty(dataMap.get("Images Emboss Run 2"))) {
			name.append("\nImages Emboss Run 2: ").append((String) dataMap.get("Images Emboss Run 2"));
		}
		return name.toString();
	}

	public static String generatePurchaseOrderPDF(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> jsonResponse = new HashMap<>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		boolean success = false;

		try {
			PurchaseOrderPDF pdf = new PurchaseOrderPDF(delegator, UtilMisc.toMap("orderId", (String) context.get("orderId"), "orderItemSeqId", (String) context.get("orderItemSeqId"), "purchaseOrderId", (String) context.get("purchaseOrderId")));
			pdf.savePDF();
			success = pdf.isSuccess();
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error: " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String updateLineItemDescription(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> jsonResponse = new HashMap<>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		
		boolean success = false;
		try {
			OrderHelper.updateOrderItem(delegator, (String) context.get("orderId"), (String) context.get("orderItemSequenceId"), context);
			success = true;
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to update order line item description. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String getUserQuotes(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", false);

		if(UtilValidate.isNotEmpty(userLogin)) {
			try {
				jsonResponse.put("data", OrderHelper.getUserQuotes(delegator, userLogin));
				jsonResponse.put("success", true);
			} catch(GenericEntityException e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to obtain user quotes. " + e + " : " + e.getMessage(), module);
			}
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String updatePrepressQueueFilter(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> jsonResponse = new HashMap<>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		boolean success = false;
		try {
			OrderHelper.updatePrepressQueueFilter(delegator, userLogin, (String) context.get("queueName"), (String) context.get("statusId"), (String) context.get("hidden"));
			success = true;
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error trying to update prepress queue filter. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String updateVendorOrder(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> jsonResponse = new HashMap<>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		boolean success = false;
		try {
			OrderHelper.updateVendorOrder(delegator, context);
			success = true;
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error trying to update vendor order. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String addWorkOrderEmployee(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();

		boolean success = false;

		try {
			if (UtilValidate.isNotEmpty(context.get("emailAddress")) && UtilValidate.isNotEmpty(context.get("netsuiteId"))) {
				OrderHelper.addWorkOrderEmployee(delegator, (String) context.get("emailAddress"), (String) context.get("netsuiteId"));
				success = true;
			} else {
				jsonResponse.put("error", "Not all required fields have been entered.");
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error trying to add a new row. " + e + " : " + e.getMessage(), module);
			jsonResponse.put("error", "Error trying to add a new row. " + e + " : " + e.getMessage());
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String removeWorkOrderEmployee(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();

		boolean success = false;

		try {
			if (UtilValidate.isNotEmpty(context.get("emailAddress"))) {
				OrderHelper.removeWorkOrderEmployee(delegator, (String) context.get("emailAddress"));
				success = true;
			} else {
				jsonResponse.put("error", "Not all required fields have been entered.");
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error trying to remove a row. " + e + " : " + e.getMessage(), module);
			jsonResponse.put("error", "Error trying to remove a row. " + e + " : " + e.getMessage());
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}
}