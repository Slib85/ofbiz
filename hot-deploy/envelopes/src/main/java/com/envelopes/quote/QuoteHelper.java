/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.quote;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

import com.bigname.integration.listrak.ListrakHelper;
import com.bigname.quote.calculator.CalculatorHelper;
import com.envelopes.party.PartyHelper;
import com.envelopes.order.OrderHelper;
import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.model.DynamicViewEntity;
import org.apache.ofbiz.entity.model.ModelKeyMap;
import org.apache.ofbiz.entity.transaction.GenericTransactionException;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.order.shoppingcart.ShoppingCart;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartItem;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import javax.servlet.http.HttpServletRequest;

import com.envelopes.util.*;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityFindOptions;
import org.apache.ofbiz.entity.util.EntityListIterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

public class QuoteHelper {
	public static final String module = QuoteHelper.class.getName();

	public static final Map<String, Object> foldersAssignedToUsers;
	public static Map<String, Object> users = new LinkedHashMap<>();
	public static Map<String, Object> userInfo = new HashMap<>();
	public static Map<String, Boolean> userWebSites = new HashMap<>();
	
	static {
		/* ===============
			Lorraine
		   =============== */
		userWebSites = new HashMap<>();
		userWebSites.put("envelopes", true);
		userWebSites.put("folders", false);
		userInfo = new HashMap<>();
		userInfo.put("phoneNumber", "631-693-1063");
		userInfo.put("isSalesRep", true);
		userInfo.put("webSite", userWebSites);
		userInfo.put("viewableQueues", "self");
		users.put("lorraine@envelopes.com", userInfo);

		/* ===============
			Lee
		   =============== */
		userWebSites = new HashMap<>();
		userWebSites.put("envelopes", false);
		userWebSites.put("folders", true);
		userInfo = new HashMap<>();
		userInfo.put("phoneNumber", "800-296-4321 x1049");
		userInfo.put("isSalesRep", true);
		userInfo.put("webSite", userWebSites);
		userInfo.put("viewableQueues", "all");
		users.put("lee@folders.com", userInfo);

		/* ===============
			Vincent
		   =============== */
		userWebSites = new HashMap<>();
		userWebSites.put("envelopes", false);
		userWebSites.put("folders", true);
		userInfo = new HashMap<>();
		userInfo.put("phoneNumber", "631-693-4259");
		userInfo.put("isSalesRep", true);
		userInfo.put("webSite", userWebSites);
		userInfo.put("viewableQueues", "all");
		users.put("vincent@folders.com", userInfo);

		/* ===============
			Nick
		   =============== */
		userWebSites = new HashMap<>();
		userWebSites.put("envelopes", false);
		userWebSites.put("folders", true);
		userInfo = new HashMap<>();
		userInfo.put("phoneNumber", "631-693-4259");
		userInfo.put("isSalesRep", false);
		userInfo.put("webSite", userWebSites);
		userInfo.put("viewableQueues", "all");
		users.put("nick.brown@bigname.com", userInfo);

		/* ===============
			Folders Production
		   =============== */
		userWebSites = new HashMap<>();
		userWebSites.put("envelopes", false);
		userWebSites.put("folders", true);
		userInfo = new HashMap<>();
		userInfo.put("phoneNumber", "631-693-1076");
		userInfo.put("isSalesRep", false);
		userInfo.put("webSite", userWebSites);
		userInfo.put("viewableQueues", "all");
		users.put("production@folders.com", userInfo);

		/* ===============
			Craig
		   =============== */
		userWebSites = new HashMap<>();
		userWebSites.put("envelopes", true);
		userWebSites.put("folders", true);
		userInfo = new HashMap<>();
		userInfo.put("phoneNumber", "631-693-1010");
		userInfo.put("isSalesRep", false);
		userInfo.put("webSite", userWebSites);
		userInfo.put("viewableQueues", "all");
		users.put("craig@bigname.com", userInfo);

		/* ===============
			TaLia Davis
		   =============== */
		userWebSites = new HashMap<>();
		userWebSites.put("envelopes", true);
		userWebSites.put("folders", true);
		userInfo = new HashMap<>();
		userInfo.put("phoneNumber", "631-693-1010");
		userInfo.put("isSalesRep", false);
		userInfo.put("webSite", userWebSites);
		userInfo.put("viewableQueues", "all");
		users.put("talia.davis@bigname.com", userInfo);

		/* ===============
			Eric Santucci
		   =============== */
		userWebSites = new HashMap<>();
		userWebSites.put("envelopes", true);
		userWebSites.put("folders", true);
		userInfo = new HashMap<>();
		userInfo.put("phoneNumber", "631-693-1010");
		userInfo.put("isSalesRep", false);
		userInfo.put("webSite", userWebSites);
		userInfo.put("viewableQueues", "all");
		users.put("eric.santucci@bigname.com", userInfo);

		/* ===============
			Kristen
		   =============== */
		userWebSites = new HashMap<>();
		userWebSites.put("envelopes", true);
		userWebSites.put("folders", true);
		userInfo = new HashMap<>();
		userInfo.put("phoneNumber", "631-693-1010");
		userInfo.put("isSalesRep", false);
		userInfo.put("webSite", userWebSites);
		userInfo.put("viewableQueues", "all");
		users.put("kristen@bigname.com", userInfo);

		/* ===============
			Bonnie
		   =============== */
		userWebSites = new HashMap<>();
		userWebSites.put("envelopes", false);
		userWebSites.put("folders", true);
		userInfo = new HashMap<>();
		userInfo.put("phoneNumber", "631-693-1056");
		userInfo.put("isSalesRep", false);
		userInfo.put("webSite", userWebSites);
		userInfo.put("viewableQueues", "all");
		users.put("bonnie@envelopes.com", userInfo);
		users.put("bonnie.boynton@bigname.com", userInfo);

		/* ===============
			Mike
		   =============== */
		userWebSites = new HashMap<>();
		userWebSites.put("envelopes", true);
		userWebSites.put("folders", true);
		userInfo = new HashMap<>();
		userInfo.put("phoneNumber", "631-693-1010");
		userInfo.put("isSalesRep", false);
		userInfo.put("webSite", userWebSites);
		userInfo.put("viewableQueues", "all");
		users.put("mike@bigname.com", userInfo);

		/* ===============
			Default
		   =============== */
		userWebSites = new HashMap<>();
		userWebSites.put("envelopes", true);
		userWebSites.put("folders", true);
		userInfo = new HashMap<>();
		userInfo.put("phoneNumber", "800-296-4321");
		userInfo.put("isSalesRep", false);
		userInfo.put("webSite", userWebSites);
		userInfo.put("viewableQueues", "all");
		users.put("website", userInfo);

		foldersAssignedToUsers = Collections.unmodifiableMap(users);
	}

	public static HashMap<String, Object> getUserPrepressQueue(Delegator delegator, ArrayList<HashMap<String, Object>> prepressQueueList) {
		HashMap<String, Object> statusList = new HashMap<>();

		try {
			if (UtilValidate.isNotEmpty(prepressQueueList)) {
				HashMap<String, Object> offsetQueue = new HashMap<>(prepressQueueList.get(0));
				Iterator it = offsetQueue.entrySet().iterator();

				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();

					HashMap<String, Object> productionTimes = new HashMap<>((HashMap) pair.getValue());
					Iterator it2 = productionTimes.entrySet().iterator();
					while (it2.hasNext()) {
						Map.Entry pair2 = (Map.Entry) it2.next();
						for (int i = 0; i < ((ArrayList) pair2.getValue()).size(); i++) {
							GenericValue orderItemAndCustomEnvelope = EntityQuery.use(delegator).from("OrderItemArtwork").where("orderId", ((ArrayList) pair2.getValue()).get(i)).queryFirst();
							String assignedTo = "website";

							if (
								UtilValidate.isNotEmpty(orderItemAndCustomEnvelope) &&
								UtilValidate.isNotEmpty(orderItemAndCustomEnvelope.getString("assignedToUserLogin")) &&
								UtilValidate.isNotEmpty(foldersAssignedToUsers.get(orderItemAndCustomEnvelope.getString("assignedToUserLogin"))) &&
								(Boolean) ((HashMap) foldersAssignedToUsers.get(orderItemAndCustomEnvelope.getString("assignedToUserLogin"))).get("isSalesRep") &&
								(Boolean) ((HashMap) ((HashMap) foldersAssignedToUsers.get(orderItemAndCustomEnvelope.getString("assignedToUserLogin"))).get("webSite")).get("folders")
							) {
								assignedTo = orderItemAndCustomEnvelope.getString("assignedToUserLogin");
							}

							HashMap<String, ArrayList> statusInfo = new HashMap<>();
							ArrayList<String> orderIdList = new ArrayList<>();

							if (UtilValidate.isNotEmpty(statusList.get(pair.getKey()))) {
								statusInfo = (HashMap) statusList.get(pair.getKey());
							}

							if (UtilValidate.isNotEmpty(statusInfo.get(assignedTo))) {
								orderIdList = (ArrayList) statusInfo.get(assignedTo);
							}

							orderIdList.add((String) ((ArrayList) pair2.getValue()).get(i));
							statusInfo.put(assignedTo, orderIdList);
							statusList.put((String) pair.getKey(), statusInfo);
						}
						it2.remove();
					}

					it.remove();
				}
			}
		} catch (Exception e) {
			Debug.logError(e, "Error trying to get data from OrderItemAndCustomEnvelope", module);
		}

		return statusList;
	}

	/*
		Get a list of the created custom envelopes (made to order)
	*/
	public static EntityListIterator getCustomEnvelopeList(Delegator delegator, HttpServletRequest request) throws GenericEntityException, ParseException {
		EntityListIterator eli = null;
		EntityFindOptions efo = new EntityFindOptions();
		List<EntityCondition> conditionList = new ArrayList<>();

		boolean selectAll = true;
		if(UtilValidate.isNotEmpty(request.getParameter("webSiteId"))) {
			conditionList.add(EntityCondition.makeCondition("webSiteId", EntityOperator.EQUALS, request.getParameter("webSiteId")));
		}

		if(UtilValidate.isNotEmpty(request.getParameter("minDate"))) {
			String minDate = request.getParameter("minDate") + " 00:00:00.0";
			conditionList.add(EntityCondition.makeCondition("createdDate", EntityOperator.GREATER_THAN_EQUAL_TO, EnvUtil.convertStringToTimestamp(minDate)));
			selectAll = false;
		}

		if(UtilValidate.isNotEmpty(request.getParameter("maxDate"))) {
			String maxDate = request.getParameter("maxDate") + " 23:59:59.0";
			conditionList.add(EntityCondition.makeCondition("createdDate", EntityOperator.LESS_THAN_EQUAL_TO, EnvUtil.convertStringToTimestamp(maxDate)));
			selectAll = false;
		}

		if(UtilValidate.isNotEmpty(request.getParameter("statusId"))) {
			conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, request.getParameter("statusId")));
			selectAll = false;
		}

		if(UtilValidate.isNotEmpty(request.getParameter("assignedTo"))) {
			conditionList.add(EntityCondition.makeCondition("assignedTo", EntityOperator.EQUALS, request.getParameter("assignedTo")));
			selectAll = false;
		}

		if(UtilValidate.isNotEmpty(request.getParameter("quoteIds"))) {
			conditionList.add(EntityCondition.makeCondition("quoteId", EntityOperator.IN, Arrays.asList(request.getParameter("quoteIds").split("\\s*,\\s*"))));
			selectAll = false;
		}

		if(UtilValidate.isNotEmpty(request.getParameter("w"))) {
			String searchTerm = request.getParameter("w");
			List<EntityCondition> keyConditionList = new ArrayList<>();
			keyConditionList.add(EntityCondition.makeCondition("quoteId", EntityOperator.LIKE, "%" + searchTerm + "%"));
			keyConditionList.add(EntityCondition.makeCondition("firstName", EntityOperator.LIKE, "%" + searchTerm + "%"));
			keyConditionList.add(EntityCondition.makeCondition("lastName", EntityOperator.LIKE, "%" + searchTerm + "%"));
			keyConditionList.add(EntityCondition.makeCondition("address1", EntityOperator.LIKE, "%" + searchTerm + "%"));
			keyConditionList.add(EntityCondition.makeCondition("city", EntityOperator.LIKE, "%" + searchTerm + "%"));
			keyConditionList.add(EntityCondition.makeCondition("stateProvinceGeoId", EntityOperator.LIKE, "%" + searchTerm + "%"));
			keyConditionList.add(EntityCondition.makeCondition("postalCode", EntityOperator.LIKE, "%" + searchTerm + "%"));
			keyConditionList.add(EntityCondition.makeCondition("phone", EntityOperator.LIKE, "%" + searchTerm + "%"));
			keyConditionList.add(EntityCondition.makeCondition("userEmail", EntityOperator.LIKE, "%" + searchTerm + "%"));
			keyConditionList.add(EntityCondition.makeCondition("companyName", EntityOperator.LIKE, "%" + searchTerm + "%"));
			conditionList.add(EntityCondition.makeCondition(keyConditionList, EntityOperator.OR));
			selectAll = false;
		}

		//if no conditions are passed, get all quotes for last 3 days
		if(selectAll) {
			//conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "QUO_CREATED"));
			conditionList.add(EntityCondition.makeCondition("createdStamp", EntityOperator.GREATER_THAN_EQUAL_TO, EnvUtil.getNDaysBeforeOrAfterNow(-3, true)));
		}

		conditionList.add(EntityCondition.makeCondition("userEmail", EntityOperator.NOT_EQUAL, null));
		eli = delegator.find("CustomEnvelopeAndContact", ((UtilValidate.isNotEmpty(conditionList)) ? EntityCondition.makeCondition(conditionList, EntityOperator.AND) : null), null, null, UtilMisc.toList("createdDate DESC"), efo);

		return eli;
	}

	/*
		Get a custom envelope info.
	*/
	public static GenericValue getCustomEnvelopeInfo(Delegator delegator, String quoteId) throws GenericEntityException {
		return delegator.findOne("CustomEnvelopeAndContactInfo", UtilMisc.toMap("quoteId", quoteId), false);
	}

	/*
		Add custom envelope order
	*/
	public static String addCustomEnvelopeOrder(Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> context) {
		String id = null;

		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try {
				id = "Q" + delegator.getNextSeqId("CustomEnvelope");
				String windowId = delegator.getNextSeqId("CustomEnvelopeWindow");
				List<GenericValue> listToBeStored = new LinkedList<GenericValue>();

				// custom_envelope data
				GenericValue customEnvelopeOrder = delegator.makeValue("CustomEnvelope", UtilMisc.toMap("quoteId", id));
				customEnvelopeOrder = EnvUtil.insertGenericValueData(delegator, customEnvelopeOrder, context);
				listToBeStored.add(customEnvelopeOrder);

				// custom_envelope_contact data
				GenericValue customEnvelopeOrderContact = delegator.makeValue("CustomEnvelopeContact", UtilMisc.toMap("customEnvelopeQuoteId", id));
				customEnvelopeOrderContact = EnvUtil.insertGenericValueData(delegator, customEnvelopeOrderContact, context);
				listToBeStored.add(customEnvelopeOrderContact);

				// custom_envelope_contact data
				GenericValue customEnvelopeOrderWindow = delegator.makeValue("CustomEnvelopeWindow", UtilMisc.toMap("envelopeWindowId", windowId, "quoteId", id));
				customEnvelopeOrderWindow = EnvUtil.insertGenericValueData(delegator, customEnvelopeOrderWindow, UtilMisc.toMap("comment", context.get("windowComment")));
				listToBeStored.add(customEnvelopeOrderWindow);

				delegator.storeAll(listToBeStored);

				//check the party, if there is a salesrep, assign it
				if(UtilValidate.isNotEmpty(context.get("partyId"))) {
					GenericValue partyId = EntityQuery.use(delegator).from("Party").where("partyId", (String) context.get("partyId")).queryOne();
					if(partyId != null) {
						String assignedTo = null;
						if (UtilValidate.isNotEmpty(context.get("assignedTo"))) {
							assignedTo = (String) context.get("assignedTo");
						} else if (UtilValidate.isNotEmpty(partyId.getString("salesRep"))) {
							assignedTo = partyId.getString("salesRep");
						}

						if(UtilValidate.isNotEmpty(assignedTo)) {
							changeQuoteStatus(dispatcher, id, "QUO_ASSIGNED", "Assigned to: " + assignedTo, EnvUtil.getSystemUser(delegator));
							customEnvelopeOrder.put("assignedTo", assignedTo);
							customEnvelopeOrder.store();

							try {
								Map<String, Object> emailData = QuoteHelper.getCustomEnvelopeInfo(delegator, id).getAllFields();
								emailData.put("additonalInfo", emailData.get("comment"));
								emailData.put("email", emailData.get("userEmail"));
								emailData.put("productStyle", emailData.get("productId"));
								emailData.put("productQuantity", emailData.get("quantity"));
								dispatcher.runAsync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "QuoteAssignment", "data", ListrakHelper.convertQuoteAssignmentData(emailData), "email", assignedTo, "webSiteId", (UtilValidate.isNotEmpty(emailData.get("webSiteId")) ? (String) emailData.get("webSiteId") : "folders")));
							} catch (Exception emailE) {
								EnvUtil.reportError(emailE);
								Debug.logError(emailE, "Error trying to send email to sales rep.", module);
							}
						}
					}
				}
			} catch (Exception e) {
				TransactionUtil.rollback(beganTransaction, "Error trying to add a new custom order.", e);
				EnvUtil.reportError(e);
				Debug.logError(e, "Error trying to add a new custom order.", module);
				id = null;
			} finally {
				TransactionUtil.commit(beganTransaction);
			}
		} catch (GenericTransactionException genericTransactionError) {
			EnvUtil.reportError(genericTransactionError);
		}

		return id;
	}

	public static void updateCustomOrder(Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> context) {
		try {
			GenericValue quoteInfo = EntityQuery.use(delegator).from("CustomEnvelope").where("quoteId", context.get("quoteId")).queryOne();

			if (quoteInfo != null) {
				quoteInfo = EnvUtil.insertGenericValueData(delegator, quoteInfo, context);
				quoteInfo.store();
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to update CustomOrder.", module);
		}
	}

	/**
	 * Update the quote customer info
	 * @param delegator
	 * @param context
	 * @return (String) the quote id that was updated
	 */
	public static String updateCustomerContact(Delegator delegator, Map<String, Object> context) {
		try {
			GenericValue quote = EntityQuery.use(delegator).from("CustomEnvelope").where("quoteId", context.get("quoteRequestId")).queryOne();
			GenericValue contact = EntityQuery.use(delegator).from("CustomEnvelopeContact").where("customEnvelopeQuoteId", context.get("quoteRequestId")).queryOne();

			String partyId = contact.getString("partyId");
			String storedEmail = contact.getString("userEmail");
			String contextEmail = (String) context.get("userEmail");
			if (partyId != null && UtilValidate.isNotEmpty(storedEmail) && UtilValidate.isNotEmpty(contextEmail) && !storedEmail.equalsIgnoreCase(contextEmail)) {
				context.put("userEmail", contextEmail.trim());
				GenericValue userLogin = PartyHelper.createUserLogin(delegator, partyId, contextEmail.trim(), null);
			}

			if (quote != null) {
				quote = EnvUtil.insertGenericValueData(delegator, quote, context);
				quote.store();
			}
			if (contact != null) {
				contact = EnvUtil.insertGenericValueData(delegator, contact, context);
				contact.store();
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to update CustomOrder.", module);
		}

		return (String) context.get("quoteRequestId");
	}

	public static boolean addQuoteNote(Delegator delegator, Map<String, Object> context, String partyId) {
		boolean success = false;
		boolean beganTransaction = false;

		try {
			beganTransaction = TransactionUtil.begin();
			try {
				String noteId = delegator.getNextSeqId("NoteData");
				delegator.create(delegator.makeValue("NoteData", UtilMisc.toMap("noteId", noteId, "noteInfo", context.get("noteInfo"), "noteDateTime", UtilDateTime.nowTimestamp(), "noteParty", partyId)));
				delegator.create(delegator.makeValue("QuoteNote", UtilMisc.toMap("quoteId", context.get("quoteId"), "noteId", noteId)));
				success = true;
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "Error trying to add quote note.", module);
			} finally {
				TransactionUtil.commit(beganTransaction);
			}
		} catch (GenericTransactionException genericTransactionError) {
			EnvUtil.reportError(genericTransactionError);
		}

		return success;
	}

	public static List<GenericValue> getQuoteNoteDataList(Delegator delegator, String quoteId) {
		List<GenericValue> quoteNoteDataList = new ArrayList<>();

		try {
			quoteNoteDataList = EntityQuery.use(delegator).from("QuoteNoteData").where("quoteId", quoteId).queryList();
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to get quote note list.");
		}

		return quoteNoteDataList;
	}

	/**
	 * Get a list of all stats by assignment
	 * @param delegator
	 * @return
	 * @throws GenericEntityException
	 */
	public static Map<String, List<String>> createdQuotes(Delegator delegator, String webSiteId, GenericValue userLogin) throws GenericEntityException {
		Map<String, List<String>> stats = new HashMap<>();

		EntityListIterator eli = null;
		try {
			List<EntityCondition> conditionList = new ArrayList<>();
			conditionList.add(EntityCondition.makeCondition("quoteId", EntityOperator.NOT_EQUAL, null));
			if(UtilValidate.isNotEmpty(webSiteId)) {
				List<EntityCondition> siteCondition = new ArrayList<>();
				String[] webSiteIds = webSiteId.split(",");
				for(String webSite : webSiteIds) {
					siteCondition.add(EntityCondition.makeCondition("webSiteId", webSite));
				}
				conditionList.add(EntityCondition.makeCondition(siteCondition, EntityOperator.OR));
			}

			DynamicViewEntity dve = new DynamicViewEntity();
			dve.addMemberEntity("CE", "CustomEnvelope");
			dve.addAlias("CE", "quoteId");
			dve.addAlias("CE", "webSiteId");
			dve.addAlias("CE", "createdDate");
			dve.addAlias("CE", "assignedTo");
			dve.addMemberEntity("QS", "QuoteStatus");
			dve.addAlias("QS", "statusId");
			dve.addAlias("QS", "statusDatetime");
			dve.addViewLink("CE", "QS", true, ModelKeyMap.makeKeyMapList("quoteId", "quoteId"));

			eli = delegator.findListIteratorByCondition(dve, EntityCondition.makeCondition(conditionList, EntityOperator.AND), null, null, UtilMisc.toList("assignedTo ASC"), null);

			List<String> allQuotes = new ArrayList<>(); //all quotes for salesrep
			List<String> unQuotedQuotes = new ArrayList<>(); //all quotes that have not been quoted
			List<GenericValue> quoteGroup = new ArrayList<>(); //holds all quote statuses for a specific quote

			String currentQuoteId = null;
			String currentAssignedTo = null;

			GenericValue quoteAndStatus = null;
			while ((quoteAndStatus = eli.next()) != null) {
				String assignedTo = (UtilValidate.isEmpty(quoteAndStatus.getString("assignedTo"))) ? "Unassigned" : quoteAndStatus.getString("assignedTo");

				if(currentQuoteId == null) {
					currentQuoteId = quoteAndStatus.getString("quoteId");
				}

				if(currentAssignedTo == null) {
					currentAssignedTo = assignedTo;
				}

				//do stat accumulation
				if(!currentQuoteId.equalsIgnoreCase(quoteAndStatus.getString("quoteId"))) {
					//quoted and unquoted accumulation

					List<EntityCondition> processedQuotes = new ArrayList<>();
					processedQuotes.add(EntityCondition.makeCondition("statusId", EntityOperator.IN, UtilMisc.toList("QUO_REJECTED", "QUO_APPROVED", "QUO_ALTERNATE_QUO", "QUO_ALTERNATE_ORDR")));
					GenericValue quote = EntityUtil.getFirst(EntityUtil.filterByAnd(quoteGroup, processedQuotes));
					if(quote == null) {
						unQuotedQuotes.add(EntityUtil.getFirst(quoteGroup).getString("quoteId"));
					}
					//reset
					quoteGroup.clear();
					currentQuoteId = quoteAndStatus.getString("quoteId");
				}

				//do stat calculation
				if(!currentAssignedTo.equalsIgnoreCase(assignedTo)) {
					//unquoted percent
					stats.put(currentAssignedTo, unQuotedQuotes);

					//reset
					currentAssignedTo = assignedTo;
					allQuotes.clear();
					unQuotedQuotes = new ArrayList<>();
				}

				if (!allQuotes.contains(quoteAndStatus.getString("quoteId"))) {
					allQuotes.add(quoteAndStatus.getString("quoteId"));
				}
				quoteGroup.add(quoteAndStatus);
			}
		} catch(Exception e1) {
			EnvUtil.reportError(e1);
		} finally {
			if(eli != null) {
				try {
					eli.close();
				} catch (GenericEntityException e2) {
					EnvUtil.reportError(e2);
				}
			}
		}

		return stats;
	}

	/**
	 * Get the quote assignment data for assigned, quoted, rejected, open quotes
	 * @param delegator
	 * @param webSiteId
	 * @param userLogin
	 * @return
	 * @throws GenericEntityException
	 */
	public static Map<String, Map> quoteQueue(Delegator delegator, String webSiteId, GenericValue userLogin) throws GenericEntityException {
		Map<String, Map> data = new HashMap<>();

		//all quote requests in last 30 days
		Timestamp nowTimestamp = UtilDateTime.nowTimestamp();
		Timestamp nowStartstamp = UtilDateTime.getDayStart(nowTimestamp);
		Timestamp yesterdayStartstamp = EnvUtil.getNDaysBeforeOrAfterNow(-1, true);

		//get incoming quote stats
		Map<String, List<String>> incomingQuotesMTD = new HashMap<>();
		Map<String, List<String>> incomingQuotesT = new HashMap<>();
		Map<String, List<String>> incomingQuotesY = new HashMap<>();

		List<EntityCondition> conditions = new ArrayList<>();
		conditions.add(EntityCondition.makeCondition("createdDate", EntityOperator.GREATER_THAN_EQUAL_TO, UtilDateTime.getMonthStart(nowTimestamp)));
		if(UtilValidate.isNotEmpty(webSiteId)) {
			List<EntityCondition> siteCondition = new ArrayList<>();
			String[] webSiteIds = webSiteId.split(",");
			for(String webSite : webSiteIds) {
				siteCondition.add(EntityCondition.makeCondition("webSiteId", webSite));
			}
			conditions.add(EntityCondition.makeCondition(siteCondition, EntityOperator.OR));
		}

		EntityListIterator eli = null;
		try {
			eli = delegator.find("CustomEnvelope", EntityCondition.makeCondition(conditions, EntityOperator.AND), null, null, UtilMisc.toList("createdDate ASC"), null);
			GenericValue quoteRequest = null;
			while((quoteRequest = eli.next()) != null) {
				String assignedTo = (UtilValidate.isEmpty(quoteRequest.getString("assignedTo"))) ? "Unassigned" : quoteRequest.getString("assignedTo");
				Timestamp quoteStartstamp = UtilDateTime.getDayStart(quoteRequest.getTimestamp("createdDate"));
				if(nowStartstamp.compareTo(quoteStartstamp) == 0) {
					//today
					List<String> quotes = (incomingQuotesT.containsKey(assignedTo)) ? incomingQuotesT.get(assignedTo) : new ArrayList<>();
					quotes.add(quoteRequest.getString("quoteId"));
					incomingQuotesT.put(assignedTo, quotes);
				} else if(yesterdayStartstamp.compareTo(quoteStartstamp) == 0) {
					//yesterday
					List<String> quotes = (incomingQuotesY.containsKey(assignedTo)) ? incomingQuotesY.get(assignedTo) : new ArrayList<>();
					quotes.add(quoteRequest.getString("quoteId"));
					incomingQuotesY.put(assignedTo, quotes);
				}

				//mtd
				List<String> quotes = (incomingQuotesMTD.containsKey(assignedTo)) ? incomingQuotesMTD.get(assignedTo) : new ArrayList<>();
				quotes.add(quoteRequest.getString("quoteId"));
				incomingQuotesMTD.put(assignedTo, quotes);
			}
		} catch(GenericEntityException e) {
			EnvUtil.reportError(e);
		} finally {
			if(eli != null) {
				eli.close();
			}
		}

		data.put("incomingQuotesMTD", incomingQuotesMTD);
		data.put("incomingQuotesT", incomingQuotesT);
		data.put("incomingQuotesY", incomingQuotesY);

		//get sent quote stats
		Map<String, List<String>> sentQuotesMTD = new HashMap<>();
		Map<String, List<String>> sentQuotesT = new HashMap<>();
		Map<String, List<String>> sentQuotesY = new HashMap<>();

		conditions.clear();
		conditions.add(EntityCondition.makeCondition("statusDatetime", EntityOperator.GREATER_THAN_EQUAL_TO, UtilDateTime.getMonthStart(nowTimestamp)));
		conditions.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "QUO_SENT_EMAIL"));

		eli = null;
		try {
			eli = delegator.find("QuoteStatus", EntityCondition.makeCondition(conditions, EntityOperator.AND), null, null, UtilMisc.toList("statusDatetime ASC"), null);
			GenericValue quoteStatus = null;
			List<String> usedQuotes = new ArrayList<>();
			while((quoteStatus = eli.next()) != null) {
				String assignedTo = (UtilValidate.isEmpty(quoteStatus.getString("statusUserLogin"))) ? "Unassigned" : quoteStatus.getString("statusUserLogin");
				Timestamp quoteStartstamp = UtilDateTime.getDayStart(quoteStatus.getTimestamp("statusDatetime"));
				if(!usedQuotes.contains(quoteStatus.getString("quoteId"))) {
					if (nowStartstamp.compareTo(quoteStartstamp) == 0) {
						//today
						List<String> quotes = (sentQuotesT.containsKey(assignedTo)) ? sentQuotesT.get(assignedTo) : new ArrayList<>();
						quotes.add(quoteStatus.getString("quoteId"));
						sentQuotesT.put(assignedTo, quotes);
					} else if (yesterdayStartstamp.compareTo(quoteStartstamp) == 0) {
						//yesterday
						List<String> quotes = (sentQuotesY.containsKey(assignedTo)) ? sentQuotesY.get(assignedTo) : new ArrayList<>();
						quotes.add(quoteStatus.getString("quoteId"));
						sentQuotesY.put(assignedTo, quotes);
					}

					//mtd
					List<String> quotes = (sentQuotesMTD.containsKey(assignedTo)) ? sentQuotesMTD.get(assignedTo) : new ArrayList<>();
					quotes.add(quoteStatus.getString("quoteId"));
					sentQuotesMTD.put(assignedTo, quotes);
					usedQuotes.add(quoteStatus.getString("quoteId"));
				}
			}
		} catch(GenericEntityException e) {
			EnvUtil.reportError(e);
		} finally {
			if(eli != null) {
				eli.close();
			}
		}

		data.put("sentQuotesMTD", sentQuotesMTD);
		data.put("sentQuotesT", sentQuotesT);
		data.put("sentQuotesY", sentQuotesY);

		//get open quotes
		Map<String, List<String>> openQuotes = new HashMap<>();

		conditions.clear();
		conditions.add(EntityCondition.makeCondition("statusId", EntityOperator.NOT_IN,
						UtilMisc.<String>toList("QUO_ORDERED", "QUO_REJECTED")));
		if(UtilValidate.isNotEmpty(webSiteId)) {
			List<EntityCondition> siteCondition = new ArrayList<>();
			String[] webSiteIds = webSiteId.split(",");
			for(String webSite : webSiteIds) {
				siteCondition.add(EntityCondition.makeCondition("webSiteId", webSite));
			}
			conditions.add(EntityCondition.makeCondition(siteCondition, EntityOperator.OR));
		}

		eli = null;
		try {
			eli = delegator.find("CustomEnvelope", EntityCondition.makeCondition(conditions, EntityOperator.AND), null, null, UtilMisc.toList("createdDate ASC"), null);
			GenericValue quoteRequest = null;
			while((quoteRequest = eli.next()) != null) {
				String assignedTo = (UtilValidate.isEmpty(quoteRequest.getString("assignedTo"))) ? "Unassigned" : quoteRequest.getString("assignedTo");
				List<String> quotes = (openQuotes.containsKey(assignedTo)) ? openQuotes.get(assignedTo) : new ArrayList<>();
				quotes.add(quoteRequest.getString("quoteId"));
				openQuotes.put(assignedTo, quotes);
			}
		} catch(GenericEntityException e) {
			EnvUtil.reportError(e);
		} finally {
			if(eli != null) {
				eli.close();
			}
		}

		data.put("openQuotes", openQuotes);

		//get rejected quotes
		Map<String, List<String>> rejectedQuotes = new HashMap<>();

		conditions.clear();
		conditions.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "QUO_REJECTED"));
		conditions.add(EntityCondition.makeCondition("createdDate", EntityOperator.GREATER_THAN_EQUAL_TO, UtilDateTime.getMonthStart(nowTimestamp)));
		if(UtilValidate.isNotEmpty(webSiteId)) {
			List<EntityCondition> siteCondition = new ArrayList<>();
			String[] webSiteIds = webSiteId.split(",");
			for(String webSite : webSiteIds) {
				siteCondition.add(EntityCondition.makeCondition("webSiteId", webSite));
			}
			conditions.add(EntityCondition.makeCondition(siteCondition, EntityOperator.OR));
		}

		eli = null;
		try {
			eli = delegator.find("CustomEnvelope", EntityCondition.makeCondition(conditions, EntityOperator.AND), null, null, UtilMisc.toList("createdDate ASC"), null);
			GenericValue quoteRequest = null;
			while((quoteRequest = eli.next()) != null) {
				String assignedTo = (UtilValidate.isEmpty(quoteRequest.getString("assignedTo"))) ? "Unassigned" : quoteRequest.getString("assignedTo");
				List<String> quotes = (rejectedQuotes.containsKey(assignedTo)) ? rejectedQuotes.get(assignedTo) : new ArrayList<>();
				quotes.add(quoteRequest.getString("quoteId"));
				rejectedQuotes.put(assignedTo, quotes);
			}
		} catch(GenericEntityException e) {
			EnvUtil.reportError(e);
		} finally {
			if(eli != null) {
				eli.close();
			}
		}

		data.put("rejectedQuotes", rejectedQuotes);

		return data;
	}

	/**
	 * Get scores for each salesrep and their quotes
	 * @param delegator
	 * @param webSiteId
	 * @param userLogin
	 * @return
	 * @throws GenericEntityException
	 */
	public static Map<String, Map> quoteScorecard(Delegator delegator, String webSiteId, GenericValue userLogin) throws GenericEntityException {
		Map<String, Map> data = new HashMap<>();

		Map<String, BigDecimal> unQuotedPercentage = new HashMap<>();
		Map<String, BigDecimal> requestToQuotePercentage = new HashMap<>();
		Map<String, BigDecimal> quoteToOrderPercentage = new HashMap<>();
		Map<String, BigDecimal> rejectedPercentage = new HashMap<>();
		Map<String, String> timeToQuote = new HashMap<>();
		Map<String, BigDecimal> avgOrderAmount = new HashMap<>();

		List<EntityCondition> conditions = new ArrayList<>();
		conditions.add(EntityCondition.makeCondition("statusDatetime", EntityOperator.GREATER_THAN_EQUAL_TO, EnvUtil.getNDaysBeforeOrAfterNow(-30, true)));
		if(UtilValidate.isNotEmpty(webSiteId)) {
			List<EntityCondition> siteCondition = new ArrayList<>();
			String[] webSiteIds = webSiteId.split(",");
			for(String webSite : webSiteIds) {
				siteCondition.add(EntityCondition.makeCondition("webSiteId", webSite));
			}
			conditions.add(EntityCondition.makeCondition(siteCondition, EntityOperator.OR));
		}

		DynamicViewEntity dve = new DynamicViewEntity();
		dve.addMemberEntity("CE", "CustomEnvelope");
		dve.addAlias("CE", "quoteId");
		dve.addAlias("CE", "webSiteId");
		dve.addAlias("CE", "createdDate");
		dve.addAlias("CE", "assignedTo");
		dve.addMemberEntity("QS", "QuoteStatus");
		dve.addAlias("QS", "statusId");
		dve.addAlias("QS", "statusDatetime");
		dve.addViewLink("CE", "QS", true, ModelKeyMap.makeKeyMapList("quoteId", "quoteId"));

		EntityListIterator eli = null;
		try {
			eli = delegator.findListIteratorByCondition(dve, EntityCondition.makeCondition(conditions, EntityOperator.AND), null, null, UtilMisc.toList("assignedTo ASC", "quoteId ASC", "statusDatetime ASC"), null);
			String currentQuoteId = null;
			String currentAssignedTo = null;

			List<String> allQuotes = new ArrayList<>(); //all quotes for salesrep
			List<GenericValue> unQuotedQuotes = new ArrayList<>(); //all quotes that have not been quoted
			List<GenericValue> quotedQuotes = new ArrayList<>(); //all quotes that have been quoted
			List<GenericValue> orderedQuotes = new ArrayList<>(); //all quotes that have been ordered
			List<GenericValue> rejectedQuotes = new ArrayList<>(); //all quotes that have been rejected

			List<GenericValue> quoteGroup = new ArrayList<>(); //holds all quote statuses for a specific quote

			GenericValue quoteAndStatus = null;
			while((quoteAndStatus = eli.next()) != null) {
				String assignedTo = (UtilValidate.isEmpty(quoteAndStatus.getString("assignedTo"))) ? "Unassigned" : quoteAndStatus.getString("assignedTo");

				if(currentQuoteId == null) {
					currentQuoteId = quoteAndStatus.getString("quoteId");
				}

				if(currentAssignedTo == null) {
					currentAssignedTo = assignedTo;
				}

				//do stat accumulation
				if(!currentQuoteId.equalsIgnoreCase(quoteAndStatus.getString("quoteId"))) {
					//quoted and unquoted accumulation
					GenericValue quote = EntityUtil.getFirst(EntityUtil.filterByAnd(quoteGroup, UtilMisc.toMap("statusId", "QUO_APPROVED")));
					if(quote == null) {
						unQuotedQuotes.add(EntityUtil.getFirst(quoteGroup));
					} else {
						quotedQuotes.add(quote);
					}

					//ordered accumulation
					GenericValue ordered = EntityUtil.getFirst(EntityUtil.filterByAnd(quoteGroup, UtilMisc.toMap("statusId", "QUO_ORDERED")));
					if(ordered != null) {
						orderedQuotes.add(ordered);
					}

					//rejected accumulation
					GenericValue rejected = EntityUtil.getFirst(EntityUtil.filterByAnd(quoteGroup, UtilMisc.toMap("statusId", "QUO_REJECTED")));
					if(rejected != null) {
						rejectedQuotes.add(rejected);
					}

					//reset
					quoteGroup.clear();
					currentQuoteId = quoteAndStatus.getString("quoteId");
				}

				//do stat calculation
				if(!currentAssignedTo.equalsIgnoreCase(assignedTo)) {
					//unquoted percent
					BigDecimal unquotedPercent = BigDecimal.ZERO;
					if(unQuotedQuotes.size() > 0) {
						unquotedPercent = new BigDecimal((double) unQuotedQuotes.size() / (double) allQuotes.size());
					}
					unQuotedPercentage.put(currentAssignedTo, unquotedPercent);

					//quoted percent
					BigDecimal quotedPercent = BigDecimal.ZERO;
					if(quotedQuotes.size() > 0) {
						quotedPercent = new BigDecimal((double) quotedQuotes.size() / (double) allQuotes.size());
					}
					requestToQuotePercentage.put(currentAssignedTo, quotedPercent);

					//ordered percent
					BigDecimal orderedPercent = BigDecimal.ZERO;
					if(orderedQuotes.size() > 0) {
						orderedPercent = new BigDecimal((double) orderedQuotes.size() / (double) allQuotes.size());
					}
					quoteToOrderPercentage.put(currentAssignedTo, orderedPercent);

					//rejected percent
					BigDecimal rejectedPercent = BigDecimal.ZERO;
					if(rejectedQuotes.size() > 0) {
						rejectedPercent = new BigDecimal((double) rejectedQuotes.size() / (double) allQuotes.size());
					}
					rejectedPercentage.put(currentAssignedTo, rejectedPercent);

					//avg time to quote
					long totalMilliseconds = 0;
					for(GenericValue quotedQuote : quotedQuotes) {
						Timestamp createdDate = quotedQuote.getTimestamp("createdDate");
						Timestamp quotedDate = quotedQuote.getTimestamp("statusDatetime");
						totalMilliseconds += EnvUtil.getMillisecondsBetweenDates(createdDate, quotedDate);
					}
					timeToQuote.put(currentAssignedTo, (totalMilliseconds > 0) ? EnvUtil.formatTime(totalMilliseconds / (long) quotedQuotes.size()) : "00:00:00");

					//avg order amount
					List<BigDecimal> orderTotals = new ArrayList<>();
					BigDecimal sumOfTotals = BigDecimal.ZERO;
					for(GenericValue orderedQuote : orderedQuotes) {
						List<EntityCondition> qcConditions = new ArrayList<>();
						qcConditions.add(EntityCondition.makeCondition("quoteRequestId", EntityOperator.EQUALS, orderedQuote.getString("quoteId")));
						qcConditions.add(EntityCondition.makeCondition("orderId", EntityOperator.NOT_EQUAL, null));
						List<GenericValue> qcQuotes = EntityQuery.use(delegator).from("QcQuote").where(EntityCondition.makeCondition(qcConditions, EntityOperator.AND)).queryList();
						List<String> orderIds = EntityUtil.getFieldListFromEntityList(qcQuotes, "orderId", true);

						for(String orderId : orderIds) {
							BigDecimal grandTotal = (EntityQuery.use(delegator).from("OrderHeader").where("orderId", orderId).queryOne()).getBigDecimal("grandTotal");
							orderTotals.add(grandTotal);
							sumOfTotals = sumOfTotals.add(grandTotal);
						}
					}
					avgOrderAmount.put(currentAssignedTo, (orderTotals.size() > 0) ? sumOfTotals.divide(new BigDecimal(orderTotals.size()), EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING) : BigDecimal.ZERO);

					//reset
					currentAssignedTo = assignedTo;
					allQuotes.clear();
					unQuotedQuotes.clear();
					quotedQuotes.clear();
					orderedQuotes.clear();
					rejectedQuotes.clear();
				}

				if (!allQuotes.contains(quoteAndStatus.getString("quoteId"))) {
					allQuotes.add(quoteAndStatus.getString("quoteId"));
				}
				quoteGroup.add(quoteAndStatus);
			}

			//below is a dup of the code inside the loop because we need to calculate on the last iteration of the loop
			//do stat accumulation
			//quoted and unquoted accumulation
			GenericValue quote = EntityUtil.getFirst(EntityUtil.filterByAnd(quoteGroup, UtilMisc.toMap("statusId", "QUO_APPROVED")));
			if(quote == null) {
				unQuotedQuotes.add(EntityUtil.getFirst(quoteGroup));
			} else {
				quotedQuotes.add(quote);
			}

			//ordered accumulation
			GenericValue ordered = EntityUtil.getFirst(EntityUtil.filterByAnd(quoteGroup, UtilMisc.toMap("statusId", "QUO_ORDERED")));
			if(ordered != null) {
				orderedQuotes.add(ordered);
			}

			//unquoted percent
			BigDecimal unquotedPercent = BigDecimal.ZERO;
			if(unQuotedQuotes.size() > 0 && allQuotes.size() > 0) {
				unquotedPercent = new BigDecimal((double) unQuotedQuotes.size() / (double) allQuotes.size());
			}
			unQuotedPercentage.put(currentAssignedTo, unquotedPercent);

			//quoted percent
			BigDecimal quotedPercent = BigDecimal.ZERO;
			if(quotedQuotes.size() > 0) {
				quotedPercent = new BigDecimal((double) quotedQuotes.size() / (double) allQuotes.size());
			}
			requestToQuotePercentage.put(currentAssignedTo, quotedPercent);

			//ordered percent
			BigDecimal orderedPercent = BigDecimal.ZERO;
			if(orderedQuotes.size() > 0) {
				orderedPercent = new BigDecimal((double) orderedQuotes.size() / (double) allQuotes.size());
			}
			quoteToOrderPercentage.put(currentAssignedTo, orderedPercent);

			//rejected percent
			BigDecimal rejectedPercent = BigDecimal.ZERO;
			if(rejectedQuotes.size() > 0) {
				rejectedPercent = new BigDecimal((double) rejectedQuotes.size() / (double) allQuotes.size());
			}
			rejectedPercentage.put(currentAssignedTo, rejectedPercent);

			//avg time to quote
			long totalMilliseconds = 0;
			for(GenericValue quotedQuote : quotedQuotes) {
				Timestamp createdDate = quotedQuote.getTimestamp("createdDate");
				Timestamp quotedDate = quotedQuote.getTimestamp("statusDatetime");
				totalMilliseconds += EnvUtil.getMillisecondsBetweenDates(createdDate, quotedDate);
			}
			timeToQuote.put(currentAssignedTo, (totalMilliseconds > 0) ? EnvUtil.formatTime(totalMilliseconds / (long) quotedQuotes.size()) : "00:00:00");

			//avg order amount
			List<BigDecimal> orderTotals = new ArrayList<>();
			BigDecimal sumOfTotals = BigDecimal.ZERO;
			for(GenericValue orderedQuote : orderedQuotes) {
				List<EntityCondition> qcConditions = new ArrayList<>();
				qcConditions.add(EntityCondition.makeCondition("quoteRequestId", EntityOperator.EQUALS, orderedQuote.getString("quoteId")));
				qcConditions.add(EntityCondition.makeCondition("orderId", EntityOperator.NOT_EQUAL, null));
				List<GenericValue> qcQuotes = EntityQuery.use(delegator).from("QcQuote").where(EntityCondition.makeCondition(qcConditions, EntityOperator.AND)).queryList();
				List<String> orderIds = EntityUtil.getFieldListFromEntityList(qcQuotes, "orderId", true);

				for(String orderId : orderIds) {
					BigDecimal grandTotal = (EntityQuery.use(delegator).from("OrderHeader").where("orderId", orderId).queryOne()).getBigDecimal("grandTotal");
					orderTotals.add(grandTotal);
					sumOfTotals = sumOfTotals.add(grandTotal);
				}
			}
			avgOrderAmount.put(currentAssignedTo, (orderTotals.size() > 0) ? sumOfTotals.divide(new BigDecimal(orderTotals.size()), EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING) : BigDecimal.ZERO);
		} catch(GenericEntityException e) {
			EnvUtil.reportError(e);
		} finally {
			if(eli != null) {
				eli.close();
			}
		}

		data.put("unQuotedPercentage", unQuotedPercentage);
		data.put("requestToQuotePercentage", requestToQuotePercentage);
		data.put("quoteToOrderPercentage", quoteToOrderPercentage);
		data.put("rejectedPercentage", rejectedPercentage);
		data.put("timeToQuote", timeToQuote);
		data.put("avgOrderAmount", avgOrderAmount);

		return data;
	}

	public static boolean generateQuotePDF(Delegator delegator, String quoteId) {
		boolean success = false;
		try {
			QuotePDF pdf = new QuotePDF(CalculatorHelper.deserializedQuoteSummary(delegator, null, quoteId));
			pdf.savePDF();
			success = true;
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to generate pdf for: " + quoteId, module);
		}

		return success;
	}

	public static boolean discontinueChildQuote(Delegator delegator, String quoteId) {
		boolean success = false;
		try {
			GenericValue quoteInfo = EntityQuery.use(delegator).from("QcQuote").where("quoteId", quoteId).queryOne();

			if (quoteInfo != null) {
				quoteInfo.put("thruDate", UtilDateTime.nowTimestamp());
				quoteInfo.store();
			}
			success = true;
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to discontinue quote: " + quoteId, module);
		}

		return success;
	}

	/**
	 * Get the Party Id from a quote
	 * @param delegator
	 * @param cart
	 * @param quoteId
	 * @return
	 */
	public static String getPartyIdFromQuote(Delegator delegator, ShoppingCart cart, String quoteId) {
		if(cart != null) {
			List<ShoppingCartItem> cartLines = cart.items();
			for(ShoppingCartItem sci : cartLines) {
				Map<String, Object> envQuoteAttributes = (Map<String, Object>) sci.getAttribute("envQuoteAttributes");
				if(UtilValidate.isNotEmpty(envQuoteAttributes) && UtilValidate.isNotEmpty(envQuoteAttributes.get("quoteId"))) {
					quoteId = (String) envQuoteAttributes.get("quoteId");
				}
				break;
			}
		}

		if(quoteId != null) {
			try {
				GenericValue quoteContact = EntityQuery.use(delegator).from("CustomEnvelopeContact").where("customEnvelopeQuoteId", quoteId).queryOne();
				if(quoteContact != null && UtilValidate.isNotEmpty(quoteContact.getString("partyId"))) {
					return quoteContact.getString("partyId");
				}
			} catch (Exception e) {
				//
			}
		}
		return null;
	}

	public static ArrayList getUserQuotes(Delegator delegator, GenericValue userLogin) throws GenericEntityException {
		List<GenericValue> quoteDataList = EntityQuery.use(delegator).from("CustomEnvelopeAndQCQuote").where("userEmail", userLogin.getString("userLoginId")).orderBy("createdStamp DESC").cache().queryList();
		ArrayList<Map<String,Object>> quoteList = new ArrayList<Map<String,Object>>();

		if(quoteDataList != null) {
			for(GenericValue quoteData : quoteDataList) {
				Map<String, Object> quoteDataMap = new HashMap<String, Object>();
				quoteDataMap.put("quoteId", quoteData.getString("quoteId"));
				quoteDataMap.put("productId", quoteData.getString("productId"));
				quoteDataMap.put("quoteRequestId", quoteData.getString("quoteRequestId"));
				quoteDataMap.put("comment", quoteData.getString("comment"));
				quoteDataMap.put("production", quoteData.getString("production"));
				quoteDataMap.put("statusId", quoteData.getString("statusId"));
				quoteDataMap.put("userEmail", quoteData.getString("userEmail"));
				quoteDataMap.put("firstName", quoteData.getString("firstName"));
				quoteDataMap.put("lastName", quoteData.getString("lastName"));
				quoteDataMap.put("companyName", quoteData.getString("companyName"));
				quoteDataMap.put("address1", quoteData.getString("address1"));
				quoteDataMap.put("address2", quoteData.getString("address2"));
				quoteDataMap.put("city", quoteData.getString("city"));
				quoteDataMap.put("stateProvinceGeoId", quoteData.getString("stateProvinceGeoId"));
				quoteDataMap.put("postalCode", quoteData.getString("postalCode"));
				quoteDataMap.put("phone", quoteData.getString("phone"));
				quoteDataMap.put("countryGeoId", quoteData.getString("countryGeoId"));
				quoteDataMap.put("internalComment", quoteData.getString("internalComment"));
				quoteDataMap.put("comment", quoteData.getString("comment"));
				quoteDataMap.put("createdStamp", quoteData.getTimestamp("createdStamp"));

				if (UtilValidate.isNotEmpty(quoteData.getString("orderId")) && UtilValidate.isNotEmpty(quoteData.getString("orderItemSeqId"))) {
					GenericValue orderItemProofArtwork = EntityUtil.getFirst(OrderHelper.getOrderItemContent(delegator, quoteData.getString("orderId"), quoteData.getString("orderItemSeqId"), "OIACPRP_PDF"));
					if (UtilValidate.isNotEmpty(orderItemProofArtwork)) {
						quoteDataMap.put("proofPDFContentPath", orderItemProofArtwork.get("contentPath"));
						quoteDataMap.put("proofPDFContentName", orderItemProofArtwork.get("contentName"));
					}
				}

				Gson gson = new GsonBuilder().serializeNulls().create();
				quoteDataMap.put("pricingRequest", gson.fromJson(quoteData.getString("pricingRequest"), HashMap.class));
				quoteDataMap.put("pricingResponse", gson.fromJson(quoteData.getString("pricingResponse"), HashMap.class));
				quoteList.add(quoteDataMap);
			}
		}

		return quoteList;
	}

	/**
	 * Change status of a quote
	 * @param dispatcher
	 * @param quoteId
	 * @param statusId
	 * @param reason
	 * @param userLogin
	 * @return
	 * @throws GenericServiceException
	 */
	public static Map<String, Object> changeQuoteStatus(LocalDispatcher dispatcher, String quoteId, String statusId, String reason, GenericValue userLogin) throws GenericServiceException {
		return dispatcher.runSync("changeQuoteStatus", UtilMisc.toMap("quoteId", quoteId, "statusId", statusId, "changeReason", reason, "userLogin", userLogin));
	}

	public static void setDriftQuote(Delegator delegator, String quoteId, String isDrift) throws GenericEntityException {
		try {
			GenericValue quoteInfo = EntityQuery.use(delegator).from("CustomEnvelope").where("quoteId", quoteId).queryOne();

			if (UtilValidate.isNotEmpty(isDrift)) {
				quoteInfo.put("isDrift", (isDrift.equals("true") ? "Y" : "N"));
				quoteInfo.store();
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to update custom envelope - isDrift column.", module);
		}
	}
}