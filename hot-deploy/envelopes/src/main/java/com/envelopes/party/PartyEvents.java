/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.party;

import java.util.*;
import java.sql.Timestamp;

import com.bigname.integration.listrak.ListrakHelper;
import com.envelopes.email.EmailHelper;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.ofbiz.base.crypto.HashCrypt;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.transaction.GenericTransactionException;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.entity.util.*;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.webapp.website.WebSiteWorker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.envelopes.util.*;
import org.apache.ofbiz.webapp.control.LoginWorker;

public class PartyEvents {
	public static final String module = PartyEvents.class.getName();

	/*
	public static String importCustomers(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		Map<String, Map<String, String>> data = new HashMap<>();

		data.put("26218520", UtilMisc.toMap("emailAddress","sharon398@verizon.net","taxable","Yes","name","Melanie Cox"));
		data.put("25505361", UtilMisc.toMap("emailAddress","sharrionm@aol.com","taxable","Yes","name","Shay Enterprise Tax Services, Inc"));

		for(String netsuiteId : data.keySet()) {
			Map<String, String> customer = data.get(netsuiteId);
			try {
				Map<String, String> context = new HashMap<>();
				if(UtilValidate.isNotEmpty(customer.get("name"))) {
					String[] names = PartyHelper.splitAtFirstSpace(customer.get("name"));
					if (names.length > 1) {
						context.put("firstName", names[0]);
						context.put("lastName", names[1]);
					} else {
						context.put("firstName", customer.get("name"));
						context.put("lastName", "Customer");
					}
				} else {
					context.put("firstName", "New");
					context.put("lastName", "Customer");
				}
				context.put("emailAddress", customer.get("emailAddress"));

				GenericValue user = PartyHelper.createAccount(delegator, dispatcher, context);
				if(user != null) {
					GenericValue party = EntityQuery.use(delegator).from("Party").where("partyId", user.getString("partyId")).queryOne();
					party.put("externalId", netsuiteId);
					party.store();

					if(UtilValidate.isNotEmpty(customer.get("taxable")) && (customer.get("taxable").contains("N") || customer.get("taxable").contains("n")) ) {
						PartyHelper.removePartyRole(delegator, party.getString("partyId"), "NON_TAXABLE");
						PartyHelper.createPartyRole(delegator, party.getString("partyId"), "NON_TAXABLE");
					}
				}
			} catch (Exception e) {
				Debug.logError(e, "Error adding user: " + customer.get("emailAddress"), module);
			}
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}
	*/

	/*
	 * Get a full list of users
	*/
	public static String getUserList(HttpServletRequest request, HttpServletResponse response, boolean... associatedUsersOnly) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		ArrayList<Map> userList = new ArrayList<Map>();
		jsonResponse.put("success", true);

		if(UtilValidate.isNotEmpty(userLogin)) {
			boolean beganTransaction = false;
			boolean associatedUsersOnlyFlag = associatedUsersOnly != null && associatedUsersOnly.length == 1 && associatedUsersOnly[0];
			EntityListIterator eli = null;
			GenericValue userData = null;
			try {
				try {
					beganTransaction = TransactionUtil.begin();

					List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
					if(associatedUsersOnlyFlag) {
						conditionList.add(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, userLogin.getString("partyId")));
						conditionList.add(EntityCondition.makeCondition("userLoginId", EntityOperator.NOT_LIKE, "%ANONYMOUS%"));
					}
					EntityFindOptions efo = new EntityFindOptions();

					eli = delegator.find("UserLogin", associatedUsersOnlyFlag ? EntityCondition.makeCondition(conditionList) : null, null, null, null, efo);

					String currentOrderId = "";
					Map<String, Object> userInfo = null;
					while((userData = eli.next()) != null) {
						userInfo = new HashMap<String, Object>();
						userInfo.put("userLoginId", userData.get("userLoginId"));
						userInfo.put("currentPassword", userData.get("currentPassword"));
						userInfo.put("passwordHint", userData.get("passwordHint"));
						userInfo.put("partyId", userData.get("partyId"));
						userInfo.put("createdStamp", userData.get("createdStamp"));
						userInfo.put("active", userData.get("enabled") != null && userData.get("enabled").equals("Y"));
						userList.add(userInfo);
					}
				} catch(GenericEntityException e) {
					jsonResponse.put("success", false);
					TransactionUtil.rollback(beganTransaction, "Error while trying to get order data.", e);
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to get order data. " + e + " : " + e.getMessage(), module);
				} catch(Exception e) {
					jsonResponse.put("success", false);
					TransactionUtil.rollback(beganTransaction, "Error while trying to get order data.", e);
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to get order data. " + e + " : " + e.getMessage(), module);
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
		}

		jsonResponse.put("data", userList);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String doContactFormSubmission(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;
/*
		try {
			//dispatcher.runAsync("sendEmail", UtilMisc.toMap("email", "bulk@envelopes.com", "rawData", null, "data", EmailHelper.createDoContactEmailData(context), "messageType", "bulkContact", "webSiteId", WebSiteWorker.getWebSiteId(request)));
			success = true;
		} catch (GenericServiceException e) {

			EnvUtil.reportError(e);
			Debug.logError("Error while sending contact form submission email. " + e + " : " + e.getMessage(), module);
		}
*/
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Sign up trade user
	 */
	public static String tradeRequest(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", true);

		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try {
				boolean hasAccount = PartyHelper.isEmailActive(delegator, (String) context.get("emailAddress"));

				GenericValue userLogin = null;
				GenericValue billingContactMech = null;
				GenericValue billingTelecomMech = null;

				Map<String, String> data = new HashMap<String, String>();
				data.put("firstName", (String) context.get("billing_firstName"));
				data.put("lastName", (String) context.get("billing_lastName"));
				data.put("emailAddress", (String) context.get("emailAddress"));
				Map<String, String> billingAddress = PartyHelper.getPostalAddressMap(request, false);
				Map<String, String> billingPhone = PartyHelper.getTelecomMap(request, false);

				if(!hasAccount) {
					userLogin = PartyHelper.createAccount(delegator, dispatcher, data);
				} else {
					userLogin = PartyHelper.getUserLogin(delegator, (String) context.get("emailAddress"));
					billingContactMech = PartyHelper.getMatchedPostalAddress(delegator, billingAddress, userLogin.getString("partyId"), "BILLING_LOCATION");
					billingTelecomMech = PartyHelper.getMatchedTelecomNumber(delegator, billingPhone, userLogin.getString("partyId"), "PHONE_BILLING");
				}

				if(UtilValidate.isEmpty(billingContactMech)) {
					PartyHelper.createPostalAddress(delegator, userLogin.getString("partyId"), billingAddress, "BILLING_LOCATION");
				}

				if(UtilValidate.isEmpty(billingTelecomMech)) {
					PartyHelper.createTelecomNumber(delegator, userLogin.getString("partyId"), billingPhone, "PHONE_BILLING");
				}

				//send email to approval team and customer
				dispatcher.runAsync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "TradeRequest", "data", ListrakHelper.createTradeRequestEmailData(context), "email", (String) context.get("emailAddress"), "webSiteId", WebSiteWorker.getWebSiteId(request)));
				dispatcher.runAsync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "TradeRequest", "data", ListrakHelper.createTradeRequestEmailData(context), "email", "trade@" + WebSiteWorker.getWebSiteId(request) + ".com", "webSiteId", WebSiteWorker.getWebSiteId(request)));
			} catch(Exception e) {
				jsonResponse.put("success", false);
				TransactionUtil.rollback(beganTransaction, "Error creating trade account", e);
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to save order. " + e + " : " + e.getMessage(), module);
			} finally {
				try {
					// only commit the transaction if we started one... this will throw an exception if it fails
					TransactionUtil.commit(beganTransaction);
				} catch(GenericEntityException e) {
					jsonResponse.put("success", false);
					EnvUtil.reportError(e);
					Debug.logError(e, "Could not create trade account.", module);
				}
			}
		} catch(GenericTransactionException e) {
			jsonResponse.put("success", false);
			EnvUtil.reportError(e);
			Debug.logError("Error while trying create trade account. " + e + " : " + e.getMessage(), module);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Sign up non-profit user
	 */
	public static String nonProfitRequest(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("success", true);

		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try {
				boolean hasAccount = PartyHelper.isEmailActive(delegator, (String) context.get("emailAddress"));

				GenericValue userLogin = null;
				GenericValue billingContactMech = null;
				GenericValue billingTelecomMech = null;

				Map<String, String> data = new HashMap<String, String>();
				data.put("firstName", (String) context.get("billing_firstName"));
				data.put("lastName", (String) context.get("billing_lastName"));
				data.put("emailAddress", (String) context.get("emailAddress"));
				Map<String, String> billingAddress = PartyHelper.getPostalAddressMap(request, false);
				Map<String, String> billingPhone = PartyHelper.getTelecomMap(request, false);

				if(!hasAccount) {
					userLogin = PartyHelper.createAccount(delegator, dispatcher, data);
				} else {
					userLogin = PartyHelper.getUserLogin(delegator, (String) context.get("emailAddress"));
					billingContactMech = PartyHelper.getMatchedPostalAddress(delegator, billingAddress, userLogin.getString("partyId"), "BILLING_LOCATION");
					billingTelecomMech = PartyHelper.getMatchedTelecomNumber(delegator, billingPhone, userLogin.getString("partyId"), "PHONE_BILLING");
				}

				if(UtilValidate.isEmpty(billingContactMech)) {
					PartyHelper.createPostalAddress(delegator, userLogin.getString("partyId"), billingAddress, "BILLING_LOCATION");
				}

				if(UtilValidate.isEmpty(billingTelecomMech)) {
					PartyHelper.createTelecomNumber(delegator, userLogin.getString("partyId"), billingPhone, "PHONE_BILLING");
				}

				//send email to approval team and customer
				dispatcher.runAsync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "NonProfitRequest", "data", ListrakHelper.createNonProfitRequestEmailData(context), "email", (String) context.get("emailAddress"), "webSiteId", WebSiteWorker.getWebSiteId(request)));
				dispatcher.runAsync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "NonProfitRequest", "data", ListrakHelper.createNonProfitRequestEmailData(context), "email", "trade@" + WebSiteWorker.getWebSiteId(request) + ".com", "webSiteId", WebSiteWorker.getWebSiteId(request)));
			} catch(Exception e) {
				jsonResponse.put("success", false);
				TransactionUtil.rollback(beganTransaction, "Error creating trade account", e);
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to save order. " + e + " : " + e.getMessage(), module);
			} finally {
				try {
					// only commit the transaction if we started one... this will throw an exception if it fails
					TransactionUtil.commit(beganTransaction);
				} catch(GenericEntityException e) {
					jsonResponse.put("success", false);
					EnvUtil.reportError(e);
					Debug.logError(e, "Could not create trade account.", module);
				}
			}
		} catch(GenericTransactionException e) {
			jsonResponse.put("success", false);
			EnvUtil.reportError(e);
			Debug.logError("Error while trying create trade account. " + e + " : " + e.getMessage(), module);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String requestPasswordReset(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		String website = (UtilValidate.isNotEmpty(context.get("website")) ? (String) context.get("website") : "envelopes");
		boolean success = false;
		String errorMsg = null;

		GenericValue userLogin = null;
		if(UtilValidate.isNotEmpty(context.get("userLoginId"))) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, 60);

			Date resetExpireDate = cal.getTime();
			Timestamp resetExpireTimestamp = new Timestamp(resetExpireDate.getTime());

			String resetToken = PartyHelper.generateSecurityToken();
			try {
				userLogin = delegator.findOne("UserLogin", UtilMisc.toMap("userLoginId", (String) context.get("userLoginId")), false);

				if(userLogin != null) {
					userLogin.set("resetToken", resetToken);
					userLogin.set("resetExpireDate", resetExpireTimestamp);
					userLogin.store();
					userLogin.refresh();

					Map<String, String> data = new HashMap<>();
					data.put("ResetMessage", "Please click the link below inorder to reset your password.");
					data.put("ResetSubMessage", "<a title=\"Website\" href=\"https://www." + website + ".com/" + website + "/control/resetPassword?resetToken=" + resetToken + "&userName=" + (String) context.get("userLoginId") + "\">Click Here to Reset Your Password</a>");
					dispatcher.runAsync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "ForgotPassword", "data", data, "email",  (String) context.get("userLoginId"), "webSiteId", WebSiteWorker.getWebSiteId(request)));

					success = true;
				} else {
					errorMsg = "Invalid email.";
				}
			} catch(GenericEntityException e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "Could not process password reset request.", module);
			} catch(GenericServiceException e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "Could not process password reset request.", module);
			}
		}

		jsonResponse.put("success", success);
		jsonResponse.put("error", errorMsg);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String updatePassword(HttpServletRequest request, HttpServletResponse response) {
		String EC_TOKEN_EXPIRED = "token_expired";
		String EC_USER_NOT_FOUND = "user_not_found";
		String EC_PASSWORD_EMPTY = "password_empty";
		String EC_CONFIRM_PASSWORD_MISMATCH = "confirm_password_mismatch";
		String EC_ERROR_RESETTING_PASSWORD = "error_resetting_password";
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;


		GenericValue userLogin = null;
		if(UtilValidate.isNotEmpty(request.getParameter("USERNAME"))) {
			try {
				userLogin = delegator.findOne("UserLogin", UtilMisc.toMap("userLoginId", request.getParameter("USERNAME")), false);
			} catch(GenericEntityException e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "Error calling updatePassword service", module);
			}
		}
		String newPassword = "";
		if(userLogin != null && UtilValidate.isNotEmpty(request.getParameter("resetToken")) && UtilValidate.isNotEmpty(userLogin.get("resetExpireDate"))) {
			if (request.getParameter("resetToken").equals(userLogin.getString("resetToken")) && userLogin.getTimestamp("resetExpireDate").compareTo(UtilDateTime.nowTimestamp()) > 0) {
				if(UtilValidate.isNotEmpty(newPassword = request.getParameter("PASSWORD"))) {
					if(newPassword.equals(request.getParameter("PASSWORD_CONFIRM"))) {
						try {
							PartyHelper.resetPassword(userLogin, newPassword);
							success = true;
						} catch (GenericEntityException e) {
							success = false;
							jsonResponse.put("error", EC_ERROR_RESETTING_PASSWORD);
							EnvUtil.reportError(e);
							Debug.logError(e, "Could not process password reset request.", module);
						}
					} else {
						jsonResponse.put("error", EC_CONFIRM_PASSWORD_MISMATCH);
					}
				} else {
					jsonResponse.put("error", EC_PASSWORD_EMPTY);
				}
			} else {
				jsonResponse.put("error", EC_TOKEN_EXPIRED);
			}
		} else if(userLogin != null && UtilValidate.isNotEmpty(request.getParameter("CURRENT_PASSWORD")) && UtilValidate.isNotEmpty(request.getParameter("PASSWORD")) && UtilValidate.isNotEmpty(userLogin.get("PASSWORD_CONFIRM"))) {
			jsonResponse = PartyHelper.updatePassword(request, userLogin, jsonResponse, false);
		} else {
			jsonResponse.put("error", userLogin == null ? EC_USER_NOT_FOUND : EC_ERROR_RESETTING_PASSWORD);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/**
	 * WARNING: This is for updating users through admin
	 * @param request
	 * @param response
	 * @return
	 */
	public static String updateUser(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		boolean success = false;
		Map<String, Object> jsonResponse = new HashMap<>();

		String emailAddress = (UtilValidate.isNotEmpty(request.getParameter("email")) ? request.getParameter("email").replace(" ", "") : null);
		String password = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("verifyPassword");
		String enabled = request.getParameter("active");

		try {
			GenericValue user = delegator.findOne("UserLogin", UtilMisc.toMap("userLoginId", emailAddress), false);
			if (UtilValidate.isNotEmpty(password) && password.equals(confirmPassword)) {
				user.set("currentPassword", HashCrypt.cryptUTF8(EnvUtil.getHashType(), null, password));
			}

			user.set("enabled", enabled);
			if("Y".equalsIgnoreCase(enabled)) {
				user.set("successiveFailedLogins", 0L);
			}
			user.store();
			user.refresh();

			success = true;
		} catch(GenericEntityException e) {
			EnvUtil.reportError(e);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/**
	 * WARNING: This is for adding users through admin
	 * @param request
	 * @param response
	 * @return
	 */
	public static String addUser(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		boolean success = false;
		Map<String, Object> jsonResponse = new HashMap<>();

		String emailAddress = (UtilValidate.isNotEmpty(request.getParameter("email")) ? request.getParameter("email").replace(" ", "") : null);
		String errorMessage = "";
		try {
			errorMessage = checkDuplicateUser(emailAddress, "", false, delegator);
			if(UtilValidate.isEmpty(errorMessage)) {
				PartyHelper.createUserLogin(delegator, request.getParameter("partyId"), emailAddress, RandomStringUtils.randomAlphanumeric(EnvConstantsUtil.PASSWORD_LENGTH));
				success = true;
			}
		} catch(GenericEntityException e) {
			EnvUtil.reportError(e);
		}

		jsonResponse.put("success", success);
		jsonResponse.put("errorMessage", errorMessage);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String saveUser(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		boolean success = false;
		Map<String, Object> jsonResponse = new HashMap<>();
		if(userLogin != null) {
			String partyId = userLogin.getString("partyId");
			String emailAddress = (UtilValidate.isNotEmpty(request.getParameter("email")) ? request.getParameter("email").replace(" ", "") : null);
			String password = request.getParameter("password");
			String confirmPassword = request.getParameter("confirmPassword");
			String enabled = request.getParameter("active");
			String updateFlag = request.getParameter("updateFlag");
			enabled = UtilValidate.isNotEmpty(enabled) && enabled.equals("true") ? "Y" : "N";
			boolean updateMode = UtilValidate.isNotEmpty(updateFlag);
			String duplicateUserMessage = checkDuplicateUser(emailAddress, partyId, updateMode, delegator);
			if(UtilValidate.isEmpty(duplicateUserMessage)) {
				if (updateMode) {
					if (UtilValidate.isNotEmpty(emailAddress)) {
						try {
							boolean toggleActiveFlag = false;
							GenericValue user = delegator.findOne("UserLogin", UtilMisc.toMap("userLoginId", emailAddress), false);
							if (UtilValidate.isNotEmpty(password) && password.equals(confirmPassword)) {
								user.set("currentPassword", HashCrypt.cryptUTF8(EnvUtil.getHashType(), null, password));
							} else {
								user.set("enabled", enabled);
								toggleActiveFlag = true;
							}
							user.store();
							user.refresh();

							//if the user password that is changed is the same as the logged in user, lets update the session
							if(userLogin.getString("userLoginId").equalsIgnoreCase(emailAddress)) {
								//replace the session GenericValue with the updated GenericValue
								request.getSession().setAttribute("userLogin", user);
							}

							jsonResponse.put("userLoginId", emailAddress);
							jsonResponse.put("active", enabled.equals("Y"));
							jsonResponse.put("message", "User has been successfully " + (toggleActiveFlag ? enabled.equals("Y") ? "enabled" : "disabled": "updated"));
							success = true;
						} catch (GenericEntityException e) {
							EnvUtil.reportError(e);
							Debug.logError(e, "Could not update the user.", module);
							jsonResponse.put("errorMessage", "An error occurred while updating the user");
						}
					}
				} else {
					if (UtilValidate.isNotEmpty(emailAddress) && UtilValidate.isNotEmpty(password) && password.equals(confirmPassword)) {
						try {
							GenericValue user = delegator.makeValue("UserLogin");
							user.put("userLoginId", emailAddress);
							user.put("partyId", partyId);
							user.put("currentPassword", HashCrypt.cryptUTF8(EnvUtil.getHashType(), null, password));
							user.put("enabled", "Y");
							user.create();
							jsonResponse.put("userLoginId", emailAddress);
							jsonResponse.put("active", true);
							jsonResponse.put("message", "User has been successfully added");
							success = true;
						} catch (GenericEntityException e) {
							EnvUtil.reportError(e);
							Debug.logError(e, "Could not create the user.", module);
							jsonResponse.put("errorMessage", "An error occurred while adding the user");
						}
					}
				}
			} else {
				jsonResponse.put("errorMessage", duplicateUserMessage);
			}
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	protected static String checkDuplicateUser(String userLoginId, String partyId, boolean updateMode, Delegator delegator) {
		EntityListIterator eli = null;
		boolean beganTransaction = false;
		String errorMessage = "";
		try {
			try {
				beganTransaction = TransactionUtil.begin();

				List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
				conditionList.add(EntityCondition.makeCondition("userLoginId", EntityOperator.EQUALS, userLoginId));
				if(updateMode) {
					conditionList.add(EntityCondition.makeCondition("partyId", EntityOperator.NOT_EQUAL, partyId));
				}
				EntityFindOptions efo = new EntityFindOptions();

				eli = delegator.find("UserLogin", EntityCondition.makeCondition(conditionList), null, null, null, efo);

				GenericValue userData = null;
				if((userData = eli.next()) != null) {
					if(partyId.equals(userData.getString("partyId"))) {
						errorMessage = "The user with the given email address already exists";
					} else {
						errorMessage = "The given email address is associated with a different user";
					}
				}
			} catch(GenericEntityException e) {
				if(UtilValidate.isEmpty(errorMessage)) {
					errorMessage = "An error occurred while adding the new user";
				}
				TransactionUtil.rollback(beganTransaction, "Error while trying to get user data.", e);
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to get user data. " + e + " : " + e.getMessage(), module);
			} finally {
				try {
					if(eli != null) {
						eli.close();
						TransactionUtil.commit(beganTransaction);
					}
				} catch(GenericEntityException e) {
					if(UtilValidate.isEmpty(errorMessage)) {
						errorMessage = "An error occurred while adding the new user";
					}
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to get user data and closing list. " + e + " : " + e.getMessage(), module);
				}
			}
		} catch (GenericTransactionException e) {
			if(UtilValidate.isEmpty(errorMessage)) {
				errorMessage = "An error occurred while adding the new user";
			}
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to get user data. " + e + " : " + e.getMessage(), module);
		}
		return errorMessage;
	}

	public static String updateUserLoginIdOrPassword(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		boolean success = false;
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		if(userLogin != null) {
			String partyId = userLogin.getString("partyId");
			String currentEmailAddress = userLogin.getString("userLoginId");
			String newEmailAddress = request.getParameter("email");
			String currentPassword = request.getParameter("currentPassword");
			String newPassword = request.getParameter("password");
			String confirmNewPassword = request.getParameter("confirmPassword");
			String validationMessage = validateUserLoginIdOrPasswordChangeRequest(currentEmailAddress, newEmailAddress, partyId, currentPassword, newPassword, confirmNewPassword, delegator, request);
			if(UtilValidate.isEmpty(validationMessage)) {
				if(newEmailAddress.equalsIgnoreCase(currentEmailAddress)) {
					// Password change only
					try {
						//set the new password to the GenericValue
						userLogin.set("currentPassword", HashCrypt.cryptUTF8(EnvUtil.getHashType(), null, newPassword));
						userLogin.store();
						userLogin.refresh();

						//replace the session GenericValue with the updated GenericValue
						request.getSession().setAttribute("userLogin", userLogin);

						jsonResponse.put("message", "Password has been successfully updated");
						success = true;
					} catch (GenericEntityException e) {
						EnvUtil.reportError(e);
						Debug.logError(e, "Could not create the user.", module);
						jsonResponse.put("errorMessage", "An error occurred while changing the email address and password");
					}
				} else {
					// User Login Id  and password change
				}
			} else {
				jsonResponse.put("errorMessage", validationMessage);
			}
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String addLoyaltyPoints(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		boolean success = false;
		Map<String, Object> jsonResponse = new HashMap<>();
		if(userLogin != null) {
			try {
				GenericValue points = delegator.makeValue("LoyaltyPoints");
				points.put("loyaltyPointsId", delegator.getNextSeqId("LoyaltyPoints"));
				points.put("partyId", (String) context.get("partyId"));
				points.put("createdByUserLogin", userLogin.getString("userLoginId"));
				points.put("createdDate", UtilDateTime.nowTimestamp());
				points.put("points", Long.valueOf((String) context.get("points")));
				points.create();
				success = true;
			} catch (GenericEntityException e) {
				EnvUtil.reportError(e);
			}
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String updatePerson(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		boolean success = false;
		Map<String, Object> jsonResponse = new HashMap<>();
		try {
			PartyHelper.updatePerson(delegator, (String) context.get("partyId"), context);
			success = true;
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	protected static String validateUserLoginIdOrPasswordChangeRequest(String userLoginId, String newUserLoginId, String partyId, String currentPassword, String newPassword, String confirmNewPassword, Delegator delegator, HttpServletRequest request) {
		EntityListIterator eli = null;
		boolean beganTransaction = false;
		String errorMessage = "";
		try {
			try {
				beganTransaction = TransactionUtil.begin();

				List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
				conditionList.add(EntityCondition.makeCondition("userLoginId", EntityOperator.EQUALS, userLoginId));
				EntityFindOptions efo = new EntityFindOptions();

				eli = delegator.find("UserLogin", EntityCondition.makeCondition(conditionList), null, null, null, efo);

				GenericValue userData = null;
				String password = "";
				if((userData = eli.next()) != null) {
					if(UtilValidate.isEmpty(newUserLoginId)) {
						errorMessage = "Email address is required";
					} else if(UtilValidate.isEmpty(currentPassword)) {
						errorMessage = "Current Password is required";
					} else if(UtilValidate.isEmpty(newPassword)) {
						errorMessage = "New Password is required";
					} else if (UtilValidate.isEmpty(confirmNewPassword)) {
						errorMessage = "Confirm Password is required";
					} else if(!newPassword.equals(confirmNewPassword)) {
						errorMessage = "New Password and Confirm Password should match";
					} else if(!(password = LoginWorker.checkPassword(request, userLoginId, currentPassword)).equals("VALID")) {
						errorMessage = password.equals("ERROR") ? "An error occurred while verifying the Current Password" : "Incorrect Current password";
					} else if(!userLoginId.equals(newUserLoginId)) {
						//TODO - Additional validations for changing the Primary email address.
					}
				}
			} catch(GenericEntityException e) {
				if(UtilValidate.isEmpty(errorMessage)) {
					errorMessage = "An error occurred while adding the new user";
				}
				TransactionUtil.rollback(beganTransaction, "Error while trying to get user data.", e);
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to get user data. " + e + " : " + e.getMessage(), module);
			} finally {
				try {
					if(eli != null) {
						eli.close();
						TransactionUtil.commit(beganTransaction);
					}
				} catch(GenericEntityException e) {
					if(UtilValidate.isEmpty(errorMessage)) {
						errorMessage = "An error occurred while adding the new user";
					}
					EnvUtil.reportError(e);
					Debug.logError("Error while trying to get user data and closing list. " + e + " : " + e.getMessage(), module);
				}
			}
		} catch (GenericTransactionException e) {
			if(UtilValidate.isEmpty(errorMessage)) {
				errorMessage = "An error occurred while adding the new user";
			}
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to get user data. " + e + " : " + e.getMessage(), module);
		}
		return errorMessage;
	}

	/**
	 * WARNING : Add roles for party through admin
	 * @param request
	 * @param response
	 * @return
	 */
	public static String addOrRemovePartyRole(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		boolean success = false;
		Map<String, Object> jsonResponse = new HashMap<>();
		if(UtilValidate.isNotEmpty(context.get("partyId")) && UtilValidate.isNotEmpty(context.get("roleTypeId"))) {
			try {
				GenericValue role = null;
				if("Y".equalsIgnoreCase((String) context.get("remove"))) {
					role = EntityQuery.use(delegator).from("PartyRole").where("partyId", (String) context.get("partyId"), "roleTypeId", (String) context.get("roleTypeId")).queryOne();
					role.remove();
				} else {
					role = delegator.makeValue("PartyRole");
					role.put("partyId", (String) context.get("partyId"));
					role.put("roleTypeId", (String) context.get("roleTypeId"));
					role.create();
				}
				success = true;

				dispatcher.runAsync("exportCustomer", UtilMisc.toMap("partyId", context.get("partyId"), "orderId", null, "quoteId", null, "userLogin", (GenericValue) request.getSession().getAttribute("userLogin")));
			} catch (Exception e) {
				EnvUtil.reportError(e);
			}
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String updateSalesRep(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		boolean success = false;
		Map<String, Object> jsonResponse = new HashMap<>();
		if(UtilValidate.isNotEmpty(context.get("partyId")) && UtilValidate.isNotEmpty(context.get("salesRep"))) {
			try {
				GenericValue party = PartyHelper.setSalesRep(delegator, (String) context.get("partyId"), null, (String) context.get("salesRep"), true);
				success = true;
			} catch (GenericEntityException e) {
				EnvUtil.reportError(e);
			}
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}
}