/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.quote;

import java.lang.*;
import java.util.*;
import java.text.SimpleDateFormat;

import com.bigname.integration.listrak.ListrakHelper;
import com.envelopes.email.EmailHelper;
import com.envelopes.netsuite.NetsuiteHelper;
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
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;
import org.apache.ofbiz.webapp.website.WebSiteWorker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.envelopes.party.PartyHelper;
import com.envelopes.util.*;

public class QuoteEvents {
	public static final String module = QuoteEvents.class.getName();

	public static String sendCompletedQuoteEmail(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		boolean success = false;
		String error = null;

		Map<String, Object> jsonResponse = new HashMap<>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		try {
			if(UtilValidate.isNotEmpty(context.get("quoteIds"))) {
				Map<String, Object> data = sendCompletedQuoteToNetsuite(delegator, dispatcher, context, (GenericValue) request.getSession().getAttribute("userLogin"));
				success = (Boolean) data.get("success");
				jsonResponse.put("response", data.get("response"));

				if(success) {
					//send email
					if ((Boolean) data.get("success")) {
						Map<String, Object> result = dispatcher.runSync("sendQuoteCompletedEmail", UtilMisc.toMap("quoteIds", context.get("quoteIds"), "webSiteId", "folders"));
						success = ServiceUtil.isSuccess(result);
						if (!success) {
							error = "Error sending email. Please try again.";
						}
					}

					Map<String, Object> statusResult = dispatcher.runSync("changeQuoteStatus", UtilMisc.toMap("quoteId", (String) context.get("quoteRequestId"), "statusId", "QUO_SENT_EMAIL", "changeReason", "Sent Quote(s): " + context.get("quoteIds"), "userLogin", (GenericValue) request.getSession().getAttribute("userLogin")));
				} else {
					error = (String) data.get("error");
				}
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to send email for quote completion", module);
			error = "Error sending email. Please try again.";
		}

		jsonResponse.put("error", error);
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String sendCompletedQuoteToNetsuite(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		return EnvUtil.doResponse(request, response, sendCompletedQuoteToNetsuite(delegator, dispatcher, context, (GenericValue) request.getSession().getAttribute("userLogin")), null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static Map<String, Object> sendCompletedQuoteToNetsuite(Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> context, GenericValue userLogin) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("success", false);

		try {
			data = NetsuiteHelper.createEstimate(delegator, dispatcher, NetsuiteHelper.getSalesEstimateMap(delegator, dispatcher, (String) context.get("quoteRequestId"), (String) context.get("quoteIds")));
		} catch (Exception netsuiteError) {
			EnvUtil.reportError(netsuiteError);
			Debug.logError(netsuiteError, "Error trying to create quote estimate in Netsuite.", module);
		} finally {
			if (!((Boolean) data.get("success"))) {
				data.put("error", "Error generating Netsuite estimate. Please try again.");
			} else {
				try {
					Map<String, Object> statusResult = dispatcher.runSync("changeQuoteStatus", UtilMisc.toMap("quoteId", (String) context.get("quoteRequestId"), "statusId", "QUO_SENT_NETSUITE", "changeReason", "Exported Quote(s): " + context.get("quoteIds"), "userLogin", userLogin));
				} catch (Exception quoteStatusError) {
					EnvUtil.reportError(quoteStatusError);
					Debug.logError(quoteStatusError, "Error trying up update quote status.", module);
				}
			}
		}

		return data;
	}

	/*
		Get Full List of Custom Orders
	*/
	public static String getCustomEnvelopeList(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		ArrayList<GenericValue> customEnvelopeList = new ArrayList<>();
		boolean success = false;
		if (UtilValidate.isNotEmpty(userLogin)) {
			EntityListIterator eli = null;
			GenericValue customEnvelopeData = null;
			boolean beganTransaction = false;
			try {
				beganTransaction = TransactionUtil.begin();
				try {
					eli = QuoteHelper.getCustomEnvelopeList(delegator, request);
					while((customEnvelopeData = eli.next()) != null) {
						customEnvelopeList.add(customEnvelopeData);
					}

					jsonResponse.put("data", customEnvelopeList);
					success = true;
				} catch(Exception e) {
					TransactionUtil.rollback(beganTransaction, "Error looking up shipping and billing addresses", e);
					Debug.logError("Error while trying to get custom envelope list. " + e + " : " + e.getMessage(), module);
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
		} else {
			jsonResponse.put("error", "You do not have sufficient privileges to update this order.");
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}


	/*
		Get a custom envelope info.
	*/
	public static String getCustomEnvelopeInfo(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;
		if (UtilValidate.isNotEmpty(userLogin)) {
			GenericValue customEnvelopeData = null;
			try {
				customEnvelopeData = QuoteHelper.getCustomEnvelopeInfo(delegator, request.getParameter("quoteId"));
				jsonResponse.put("data", customEnvelopeData);
				success = true;
			}
			catch(GenericEntityException e1) {
				Debug.logError("Error while trying to get custom envelope info. " + e1 + " : " + e1.getMessage(), module);
			}
		}
		else {
			jsonResponse.put("error", "You do not have sufficient privileges to update this order.");
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}
	
	/*
		Add Custom Order
	*/
	public static String addCustomEnvelopeOrder(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		boolean success = false;
		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(UtilDateTime.nowTimestamp());
		Map<String, Object> jsonResponse = new HashMap<>();

		try {
			context.put("emailAddress", context.get("userEmail"));
			if(!PartyHelper.isEmailActive(delegator, (String) context.get("userEmail"))) {
				GenericValue userLogin = PartyHelper.createAccount(delegator, dispatcher, context);
				context.put("partyId", userLogin.getString("partyId"));
			} else {
				GenericValue userLogin = PartyHelper.getUserLogin(delegator, (String) context.get("userEmail"));
				context.put("partyId", userLogin.getString("partyId"));
			}
			context.put("statusId", "QUO_CREATED");
			context.put("createdDate", timestamp);
			String quoteId = "";

			if(UtilValidate.isNotEmpty(quoteId = QuoteHelper.addCustomEnvelopeOrder(delegator, dispatcher, context))) {
				success = true;
				jsonResponse.put("quoteId", quoteId);
				
				Map<String, Object> messageData = new HashMap<>();
				messageData = (HashMap) ListrakHelper.createCustomQuoteEmailData(context, quoteId);

				try {
					dispatcher.runAsync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "QuoteConfirmation", "data", messageData, "email", context.get("emailAddress"), "webSiteId", WebSiteWorker.getWebSiteId(request)));
					dispatcher.runAsync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "QuoteConfirmation", "data", messageData, "email", "transaction@" + WebSiteWorker.getWebSiteId(request) + ".com", "webSiteId", WebSiteWorker.getWebSiteId(request)));
				} catch (GenericServiceException e) {
					Debug.logError(e, "Error sending quote confirmation email", module);
				}
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
		}

		jsonResponse.put("success" , success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String updateCustomOrderAssignedTo(HttpServletRequest request, HttpServletResponse response) {
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        boolean success = false;
        Map<String, Object> jsonResponse = new HashMap<>();
        Map<String, Object> context = EnvUtil.getParameterMap(request);

        try {
            QuoteHelper.updateCustomOrder(delegator, dispatcher, context);
			QuoteHelper.changeQuoteStatus(dispatcher, (String) context.get("quoteId"), "QUO_ASSIGNED", "Assigned to: " + context.get("assignedTo"), (GenericValue) request.getSession().getAttribute("userLogin"));
            Map<String, Object> emailData = QuoteHelper.getCustomEnvelopeInfo(delegator, (String) context.get("quoteId")).getAllFields();

            for (Map.Entry<String, Object> entry : emailData.entrySet()) {
                if (UtilValidate.isNotEmpty(entry.getValue()) && !(entry.getValue() instanceof String)) {
                    entry.setValue(entry.getValue().toString());
                }
            }

            emailData.put("additonalInfo", emailData.get("comment"));
            emailData.put("email", emailData.get("userEmail"));
            emailData.put("productStyle", emailData.get("productId"));
            emailData.put("productQuantity", emailData.get("quantity"));

            if (UtilValidate.isNotEmpty(context.get("assignedTo")) && !((String) context.get("assignedTo")).equals("none")) {
            	PartyHelper.setSalesRep(delegator, (String) emailData.get("partyId") , null, (String) context.get("assignedTo"), false);

                dispatcher.runAsync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "QuoteAssignment", "data", ListrakHelper.convertQuoteAssignmentData(emailData), "email", (String) context.get("assignedTo"), "webSiteId", context.get("websiteId")));
            }
            success = true;
        } catch (Exception e) {
            EnvUtil.reportError(e);
        }

        jsonResponse.put("success" , success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

	/*
     * Quote Request Email
     */
	public static String foldersQuoteRequest(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		boolean success = false;
		Map<String, Object> jsonResponse = new HashMap<>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(UtilDateTime.nowTimestamp());

		try {
			context.put("emailAddress", (String) context.get("email"));
			context.put("userEmail", (String) context.get("email"));
			context.put("productId", (String) context.get("productStyle"));

			if(UtilValidate.isNotEmpty(context.get("productQuantity"))) {
				String quantity = ((String) context.get("productQuantity")).contains(",") ? ((String) context.get("productQuantity")).substring(0, ((String) context.get("productQuantity")).indexOf(",")) : (String) context.get("productQuantity");
				context.put("quantity", quantity);
			}
			
			if(!PartyHelper.isEmailActive(delegator, (String) context.get("email"))) {
				GenericValue userLogin = PartyHelper.createAccount(delegator, dispatcher, context);
				GenericValue person = userLogin.getRelatedOne("Person", false);
				context.put("partyId", userLogin.getString("partyId"));

				//New Customer, then add as Lead
				Map<String, Object> data = NetsuiteHelper.getPerson(userLogin, person);
				data.put("phone", (String) context.get("phone"));
				data.put("companyName", (String) context.get("companyName"));
				try {
					NetsuiteHelper.createLead(delegator, dispatcher, data);
				} catch (Exception e1) {
					EnvUtil.reportError(e1);
				}
			} else {
				GenericValue userLogin = PartyHelper.getUserLogin(delegator, (String) context.get("userEmail"));
				context.put("partyId", userLogin.getString("partyId"));
			}

			context.put("statusId", "QUO_CREATED");
			context.put("createdDate", timestamp);
			String quoteId = "";

			//TODO: for the meantime folders quote data will all be summarized inside the comments for the quote table

			StringBuilder folderComments = new StringBuilder("");

			if(UtilValidate.isNotEmpty(context.get("comment"))) {
				folderComments.append(context.get("comment"));
			} else {
				if(UtilValidate.isNotEmpty(context.get("paperColor"))) {
					folderComments.append("Paper Color: ").append((String) context.get("paperColor"));
				}
				if(UtilValidate.isNotEmpty(context.get("outsideColor"))) {
					folderComments.append("Outside Color: ").append((String) context.get("outsideColor")).append(UtilValidate.isNotEmpty(folderComments.toString()) ? "," : "");
				}
				if(UtilValidate.isNotEmpty(context.get("insideColor"))) {
					folderComments.append("Inside Color: ").append((String) context.get("insideColor")).append(UtilValidate.isNotEmpty(folderComments.toString()) ? "," : "");
				}
				if(UtilValidate.isNotEmpty(context.get("foilColor"))) {
					folderComments.append("Foil Color: ").append((String) context.get("foilColor")).append(UtilValidate.isNotEmpty(folderComments.toString()) ? "," : "");
				}
				if(UtilValidate.isNotEmpty(context.get("productQuantity"))) {
					folderComments.append("Quantities: ").append((String) context.get("productQuantity"));
				}
				if(UtilValidate.isNotEmpty(context.get("additonalInfo"))) {
					folderComments.append("Additional Info: ").append((String) context.get("additonalInfo"));
				}
			}

			context.put("comment", folderComments.toString());
			if(UtilValidate.isNotEmpty(quoteId = QuoteHelper.addCustomEnvelopeOrder(delegator, dispatcher, context))) {
				success = true;
				jsonResponse.put("quoteId", quoteId);
				context.put("quoteId", quoteId);
				try {
					if(!"emailThisQuote".equalsIgnoreCase((String) context.get("source"))) {
						dispatcher.runAsync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "QuoteRequest", "data", ListrakHelper.createQuoteRequestData(context), "email", context.get("email"), "webSiteId", WebSiteWorker.getWebSiteId(request)));
					} else {
						//TODO hardcoded, assign to erica for all "quotes" that are from website calculated quotes
						QuoteHelper.changeQuoteStatus(dispatcher, (String) context.get("quoteId"), "QUO_ASSIGNED", "Assigned to: production@folders.com", EnvUtil.getSystemUser(delegator));
						PartyHelper.setSalesRep(delegator, (String) context.get("partyId"), null, "production@folders.com", false);
					}
				} catch (GenericServiceException e) {
					Debug.logError(e, "Error sending quote confirmation email", module);
				}
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

    /**
     * Update Custom Order
     */
	public static String updateCustomOrder(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		boolean success = false;
		Map<String, Object> jsonResponse = new HashMap<>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		try {
			QuoteHelper.updateCustomOrder(delegator, dispatcher, context);
			success = true;
		} catch (Exception e) {
			EnvUtil.reportError(e);
		}

		jsonResponse.put("success" , success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/**
	 * Update Custom Order
	 */
	public static String changeQuoteStatus(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		boolean success = false;
		Map<String, Object> jsonResponse = new HashMap<>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		try {
			Map<String, Object> result = dispatcher.runSync("changeQuoteStatus", UtilMisc.toMap("quoteId", (String) context.get("quoteId"), "statusId", (String) context.get("statusId"), "userLogin", (GenericValue) request.getSession().getAttribute("userLogin")));
			if(ServiceUtil.isSuccess(result)) {
				success = true;
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
		}

		jsonResponse.put("success" , success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String addQuoteNote(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> jsonResponse = new HashMap<>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		jsonResponse.put("success", QuoteHelper.addQuoteNote(delegator, context, ((GenericValue) request.getSession().getAttribute("userLogin")).getString("partyId")));
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String generateQuotePDF(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> jsonResponse = new HashMap<>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		boolean success = QuoteHelper.generateQuotePDF(delegator, (String) context.get("quoteId"));

		jsonResponse.put("success" , success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String discontinueChildQuote(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> jsonResponse = new HashMap<>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		jsonResponse.put("success" , QuoteHelper.discontinueChildQuote(delegator, (String) context.get("quoteId")));
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}
	
	public static String getUserQuotes(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", false);

		if(UtilValidate.isNotEmpty(userLogin)) {
			try {
				jsonResponse.put("data", QuoteHelper.getUserQuotes(delegator, userLogin));
				jsonResponse.put("success", true);
			} catch(GenericEntityException e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to obtain user quotes. " + e + " : " + e.getMessage(), module);
			}
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String setDriftQuote(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		jsonResponse.put("success", false);

		if (UtilValidate.isNotEmpty(context.get("isDrift")) && UtilValidate.isNotEmpty(context.get("quoteId"))) {
			try {
				QuoteHelper.setDriftQuote(delegator, (String) context.get("quoteId"), (String) context.get("isDrift"));
				jsonResponse.put("success", true);
			} catch(GenericEntityException e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to set drift quote. " + e + " : " + e.getMessage(), module);
			}
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String emailQuote(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		jsonResponse.put("success", false);

		try {
			if (UtilValidate.isNotEmpty(context.get("UserEmail"))) {
				String messageType = (UtilValidate.isNotEmpty(context.get("messageType")) ? (String) context.get("messageType") : "EmailQuote");

				dispatcher.runAsync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", messageType, "data", context, "email", (String) context.get("UserEmail"), "webSiteId", (UtilValidate.isNotEmpty(context.get("WebSiteId")) ? (String) context.get("WebSiteId") : "envelopes")));
				jsonResponse.put("success", true);
			}
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error trying to send envelopes quote data. " + e + " : " + e.getMessage(), module);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}
}