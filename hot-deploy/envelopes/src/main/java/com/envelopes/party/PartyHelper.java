/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.party;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.security.SecureRandom;

import com.envelopes.tax.TaxHelper;
import org.apache.commons.lang.RandomStringUtils;

import org.apache.ofbiz.base.crypto.HashCrypt;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.GeneralException;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilHttp;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilProperties;
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
import org.apache.ofbiz.entity.util.*;
import org.apache.ofbiz.security.Security;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ModelService;
import org.apache.ofbiz.service.ServiceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import com.envelopes.shipping.ShippingHelper;
import com.envelopes.quote.QuoteHelper;
import com.envelopes.util.*;

public class PartyHelper {
	public static final String module = PartyHelper.class.getName();
	public static final String resourceWebapp = "SecurityextUiLabels";
	private static SecureRandom random = new SecureRandom();
	protected static Map<String, BigDecimal> LOYALTY_DISCOUNT = new HashMap<String, BigDecimal>();

	static {
		LOYALTY_DISCOUNT = new HashMap<String, BigDecimal>();
		LOYALTY_DISCOUNT.put("BRONZE", new BigDecimal("5"));
		LOYALTY_DISCOUNT.put("SILVER", new BigDecimal("7"));
		LOYALTY_DISCOUNT.put("GOLD", new BigDecimal("10"));
		LOYALTY_DISCOUNT.put("PLATINUM", new BigDecimal("15"));
	}

	/*
	 * Calculate loyalty discount
	 */
	public static BigDecimal getLoyaltyDiscount(BigDecimal points) {
		if(points.compareTo(new BigDecimal("5001")) >= 0 && points.compareTo(new BigDecimal("7500")) < 0) {
			return LOYALTY_DISCOUNT.get("BRONZE");
		} else if(points.compareTo(new BigDecimal("7501")) >= 0 && points.compareTo(new BigDecimal("10000")) <= 0) {
			return LOYALTY_DISCOUNT.get("SILVER");
		} else if(points.compareTo(new BigDecimal("10001")) >= 0 && points.compareTo(new BigDecimal("32000")) <= 0) {
			return LOYALTY_DISCOUNT.get("GOLD");
		} else if(points.compareTo(new BigDecimal("32001")) >= 0) {
			return LOYALTY_DISCOUNT.get("PLATINUM");
		} else {
			return BigDecimal.ZERO;
		}
	}

	/*
	 * Check to see if the email address has an account or previous history of any activity
	 */
	public static boolean isEmailActive(Delegator delegator, String email) throws GenericEntityException {
		if(UtilValidate.isEmpty(email)) {
			return false;
		}

		GenericValue userLogin = getUserLogin(delegator, email);
		if(userLogin == null) {
			//this lookup could be real slow
			//GenericValue contactMech = EntityUtil.getFirst(delegator.findByAnd("ContactMech", UtilMisc.toMap("contactMechTypeId", "EMAIL_ADDRESS", "INFO_STRING", email), null, false));
			//if(contactMech == null) {
				return false;
			//} else {
			//	return true;
			//}
		} else {
			return true;
		}
	}

	/**
	 * Get the party given an external id (netsuite id)
	 * @param delegator
	 * @param externalId
	 * @return
	 * @throws GenericEntityException
	 */
	public static GenericValue getPartyByExternalId(Delegator delegator, String externalId) throws GenericEntityException {
		if(UtilValidate.isEmpty(externalId)) {
			return null;
		}

		return EntityQuery.use(delegator).from("Party").where("externalId", externalId).queryFirst();
	}

	/*
	 * Create account
	 * Will return the UserLogin value
	 * If any table entries fail, rollback
	 */
	public static GenericValue createAccount(Delegator delegator, LocalDispatcher dispatcher, Map<String, ?> context) {
		boolean isSuccess = false;
		boolean transaction = false;
		String errorMsg = "Error trying to create an account.";
		GenericValue userLogin = null;

		try {
			userLogin = getUserLogin(delegator, (String) context.get("emailAddress"));

			if(userLogin == null) {
				transaction = TransactionUtil.begin();
				GenericValue party = createParty(delegator);
				GenericValue person = createPerson(delegator, party.getString("partyId"), (String) context.get("firstName"), (String) context.get("lastName"));
				GenericValue partyRole = createPartyRole(delegator, party.getString("partyId"), "CUSTOMER");
				GenericValue emailContactMech = createPartyContactMech(delegator, party.getString("partyId"), "EMAIL_ADDRESS", (String) context.get("emailAddress"));
							 createPartyContactMechPurpose(delegator, party.getString("partyId"), emailContactMech.getString("contactMechId"), "PRIMARY_EMAIL", false);
				userLogin = createUserLogin(delegator, party.getString("partyId"), (String) context.get("emailAddress"), (String) context.get("password"));
				isSuccess = true;
			} else {
				return userLogin;
			}
		} catch (GenericEntityException e) {
			isSuccess = false;
			EnvUtil.reportError(e);
			Debug.logError(e, errorMsg, module);
			try {
				TransactionUtil.rollback(transaction, errorMsg, e);
			} catch (GenericTransactionException gte) {
				EnvUtil.reportError(gte);
				Debug.logError(gte, "Unable to rollback transaction", module);
			}
		} finally {
			if (!isSuccess) {
				try {
					TransactionUtil.rollback(transaction, errorMsg, null);
				} catch (GenericTransactionException gte) {
					EnvUtil.reportError(gte);
					Debug.logError(gte, "Unable to rollback transaction", module);
				}
			} else {
				try {
					TransactionUtil.commit(transaction);
				} catch (GenericTransactionException gte) {
					EnvUtil.reportError(gte);
					Debug.logError(gte, "Unable to commit transaction", module);
				}
			}
		}

		return userLogin;
	}

	/*
	 * Create a Party entry
	 */
	public static GenericValue createParty(Delegator delegator) throws GenericEntityException {
		return createParty(delegator, null);
	}
	public static GenericValue createParty(Delegator delegator, String partyId) throws GenericEntityException {
		GenericValue party = delegator.makeValue("Party", UtilMisc.toMap("partyId", (UtilValidate.isEmpty(partyId)) ? delegator.getNextSeqId("Party") : partyId));
		party.put("partyTypeId", "PERSON");
		party.put("createdDate", UtilDateTime.nowTimestamp());
		party.put("lastModifiedDate", UtilDateTime.nowTimestamp());
		delegator.create(party);

		return party;
	}

	/*
	 * Create a Person entry
	 */
	public static GenericValue createPerson(Delegator delegator, String partyId, String firstName, String lastName) throws GenericEntityException {
		GenericValue person = delegator.makeValue("Person", UtilMisc.toMap("partyId", partyId));
		person.put("firstName", firstName);
		person.put("lastName", lastName);
		delegator.create(person);

		return person;
	}

	/*
	 * Create a PartyRole entry
	 */
	public static GenericValue createPartyRole(Delegator delegator, String partyId, String roleType) throws GenericEntityException {
		GenericValue partyRole = delegator.makeValue("PartyRole", UtilMisc.toMap("partyId", partyId));
		partyRole.put("roleTypeId", roleType);
		delegator.create(partyRole);

		return partyRole;
	}

	/*
	 * Create a PartyRole entry
	 */
	public static GenericValue removePartyRole(Delegator delegator, String partyId, String roleType) throws GenericEntityException {
		GenericValue partyRole = EntityQuery.use(delegator).from("PartyRole").where("partyId", partyId, "roleTypeId", roleType).queryOne();
		if(partyRole != null) {
			partyRole.remove();
		}

		return partyRole;
	}

	/*
	 * Create a ContactMech entry and associate it to Party via PartyContactMech
	 */
	public static GenericValue createPartyContactMech(Delegator delegator, String partyId, String contactMechTypeId, String infoString) throws GenericEntityException {
		String contactMechId = delegator.getNextSeqId("ContactMech");
		GenericValue contactMech = delegator.makeValue("ContactMech", UtilMisc.toMap("contactMechId", contactMechId));
		contactMech.put("contactMechTypeId", contactMechTypeId);
		contactMech.put("infoString", infoString);
		delegator.create(contactMech);

		GenericValue partyContactMech = delegator.makeValue("PartyContactMech", UtilMisc.toMap("contactMechId", contactMechId));
		partyContactMech.put("partyId", partyId);
		partyContactMech.put("fromDate", UtilDateTime.nowTimestamp());
		delegator.create(partyContactMech);

		return contactMech;
	}

	public static void removePartyContactMechPurpose(Delegator delegator, String contactMechId, String contactMechPurposeTypeId) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(contactMechPurposeTypeId)) {
			Map<String, String> context = new HashMap<String, String>();
			context.put("contactMechId", contactMechId);
			context.put("contactMechPurposeTypeId", contactMechPurposeTypeId);
			GenericValue partyContactMechPurpose = EntityUtil.getFirst(EntityUtil.filterByDate(delegator.findByAnd("PartyContactMechPurpose", context, null, false)));
			partyContactMechPurpose.put("thruDate", UtilDateTime.nowTimestamp());
			delegator.store(partyContactMechPurpose);
		} else {
			//TODO
		}
	}

	/*
	 * Create a PartyRole entry
	 */
	public static GenericValue createUserLogin(Delegator delegator, String partyId, String emailAddress, String password) throws GenericEntityException {
		GenericValue userLogin = delegator.makeValue("UserLogin", UtilMisc.toMap("userLoginId", emailAddress));
		userLogin.put("partyId", partyId);
		userLogin.put("enabled", "Y");
		userLogin.put("currentPassword", HashCrypt.cryptUTF8(EnvUtil.getHashType(), null, (UtilValidate.isNotEmpty(password)) ? password : RandomStringUtils.randomAlphanumeric(EnvConstantsUtil.PASSWORD_LENGTH)));
		delegator.create(userLogin);

		return userLogin;
	}

	/*
	 * Get a UserLogin value
	 */
	public static GenericValue getUserLogin(Delegator delegator, String userLoginId) throws GenericEntityException {
		GenericValue userLogin = null;
		if(UtilValidate.isNotEmpty(userLoginId)) {
			userLogin = delegator.findOne("UserLogin", UtilMisc.toMap("userLoginId", userLoginId), false);
		}

		return userLogin;
	}

	/*
	 * Check if a postal address already exists for the specified party
	 */
	public static GenericValue getMatchedPostalAddress(Delegator delegator, Map<String, String> postalAddress, String partyId, String contactMechPurposeTypeId) throws GenericEntityException {
		Map<String, String> context = new HashMap<String, String>(postalAddress);
		context.put("partyId", partyId);
		context.put("purposeThruDate", null);
		context.put("toName", ((UtilValidate.isNotEmpty(context.get("firstName"))) ? context.get("firstName") : "") + ((UtilValidate.isNotEmpty(context.get("lastName"))) ? " " + context.get("lastName") : ""));
		context.put("contactMechPurposeTypeId", contactMechPurposeTypeId);
		context.remove("firstName");
		context.remove("lastName");
		context.remove("shipTo");
		GenericValue address = EntityUtil.getFirst(EntityUtil.filterByDate(delegator.findByAnd("PartyContactDetailByPurpose", context, null, false)));
		if(address == null) {
			context.remove("purposeThruDate");
			address = EntityUtil.getFirst(EntityUtil.filterByDate(delegator.findByAnd("PartyContactDetailByPurpose", context, null, false)));
		}
		return address;
	}

	public static GenericValue getMatchedPostalAddress(Delegator delegator, String contactMechId, String partyId, String contactMechPurposeTypeId) throws GenericEntityException {
		Map<String, String> context = new HashMap<String, String>();
		context.put("contactMechId", contactMechId);
		context.put("partyId", partyId);
		context.put("contactMechPurposeTypeId", contactMechPurposeTypeId);
		GenericValue address = EntityUtil.getFirst(EntityUtil.filterByDate(delegator.findByAnd("PartyContactDetailByPurpose", context, null, false)));
		return address;
	}

	public static GenericValue getMatchedTelecomNumber(Delegator delegator, String contactMechId, String partyId, String contactMechPurposeTypeId) throws GenericEntityException {
		GenericValue telecomMechAttribute = delegator.findOne("ContactMechAttribute", UtilMisc.toMap("contactMechId", contactMechId, "attrName", "telecomNumber"), false);
		if(UtilValidate.isNotEmpty(telecomMechAttribute)) {
			Map<String, String> context = new HashMap<String, String>();
			context.put("contactMechId", telecomMechAttribute.getString("attrValue"));
			context.put("partyId", partyId);
			context.put("contactMechPurposeTypeId", contactMechPurposeTypeId);
			GenericValue telecomNumber = EntityUtil.getFirst(EntityUtil.filterByDate(delegator.findByAnd("PartyContactMechPurpose", context, null, false)));
			return telecomNumber;
		}
		return null;
	}

	/*
	 * Get the postal address from the request
	 */
	public static Map getPostalAddressMap(HttpServletRequest request, boolean isShipAddress) {
		return getPostalAddressMap(request, null, isShipAddress);
	}
	public static Map getPostalAddressMap(HttpServletRequest request, Map<String, Object> context, boolean isShipAddress) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, String> postalAddress = new HashMap<String, String>();

		String prefix = (isShipAddress) ? "shipping_" : "billing_";
		postalAddress.put("shipTo", (UtilValidate.isNotEmpty(request.getParameter("ship_to"))) ? request.getParameter("ship_to") : null);
		postalAddress.put("firstName", (UtilValidate.isNotEmpty(request.getParameter(prefix + "firstName"))) ? request.getParameter(prefix + "firstName") : null);
		postalAddress.put("lastName", (UtilValidate.isNotEmpty(request.getParameter(prefix + "lastName"))) ? request.getParameter(prefix + "lastName") : null);
		postalAddress.put("companyName", (UtilValidate.isNotEmpty(request.getParameter(prefix + "companyName"))) ? request.getParameter(prefix + "companyName") : null);
		postalAddress.put("address1", (UtilValidate.isNotEmpty(request.getParameter(prefix + "address1"))) ? request.getParameter(prefix + "address1") : null);
		postalAddress.put("address2", (UtilValidate.isNotEmpty(request.getParameter(prefix + "address2"))) ? request.getParameter(prefix + "address2") : null);
		postalAddress.put("city", (UtilValidate.isNotEmpty(request.getParameter(prefix + "city"))) ? request.getParameter(prefix + "city") : null);
		postalAddress.put("stateProvinceGeoId", (UtilValidate.isNotEmpty(request.getParameter(prefix + "stateProvinceGeoId"))) ? request.getParameter(prefix + "stateProvinceGeoId") : null);
		String postalCode = (String) request.getAttribute("postalCode");
		if(UtilValidate.isEmpty(postalCode)) {
			postalCode = (UtilValidate.isNotEmpty(request.getParameter(prefix + "postalCode"))) ? request.getParameter(prefix + "postalCode") : null;
		}
		postalAddress.put("postalCode", postalCode);
		postalAddress.put("countryGeoId", (UtilValidate.isNotEmpty(request.getParameter(prefix + "countryGeoId"))) ? request.getParameter(prefix + "countryGeoId") : "USA");

		if(UtilValidate.isNotEmpty(context)) {
			postalAddress.put("firstName", (UtilValidate.isNotEmpty(context.get(prefix + "firstName"))) ? (String) context.get(prefix + "firstName") : null);
			postalAddress.put("lastName", (UtilValidate.isNotEmpty(context.get(prefix + "lastName"))) ? (String) context.get(prefix + "lastName") : null);
			postalAddress.put("companyName", (UtilValidate.isNotEmpty(context.get(prefix + "companyName"))) ? (String) context.get(prefix + "companyName") : null);
			postalAddress.put("address1", (UtilValidate.isNotEmpty(context.get(prefix + "address1"))) ? (String) context.get(prefix + "address1") : null);
			postalAddress.put("address2", (UtilValidate.isNotEmpty(context.get(prefix + "address2"))) ? (String) context.get(prefix + "address2") : null);
			postalAddress.put("city", (UtilValidate.isNotEmpty(context.get(prefix + "city"))) ? (String) context.get(prefix + "city") : null);
			postalAddress.put("stateProvinceGeoId", (UtilValidate.isNotEmpty(context.get(prefix + "stateProvinceGeoId"))) ? (String) context.get(prefix + "stateProvinceGeoId") : null);
			postalCode = (String) request.getAttribute("postalCode");
			if(UtilValidate.isEmpty(postalCode)) {
				postalCode = (UtilValidate.isNotEmpty(context.get(prefix + "postalCode"))) ? (String) context.get(prefix + "postalCode") : null;
			}
			postalAddress.put("postalCode", postalCode);
			postalAddress.put("countryGeoId", (UtilValidate.isNotEmpty(context.get(prefix + "countryGeoId"))) ? (String) context.get(prefix + "countryGeoId") : "USA");
		}

		try {
			if(UtilValidate.isNotEmpty(postalAddress.get("countryGeoId")) && postalAddress.get("countryGeoId").equals("USA") && UtilValidate.isNotEmpty(postalAddress.get("postalCode")) && UtilValidate.isEmpty(postalAddress.get("stateProvinceGeoId"))) {
				List zipStatesList = delegator.findByAnd("ZipSalesTaxLookup", UtilMisc.toMap("zipCode", TaxHelper.cleanZipCode(postalAddress.get("postalCode"))), null, true);
				if(UtilValidate.isNotEmpty(zipStatesList)) {
					GenericValue zipStateValue = (GenericValue) zipStatesList.get(0);
					postalAddress.put("stateProvinceGeoId", zipStateValue.getString("stateCode"));
				}
			}
			/*if(UtilValidate.isNotEmpty(postalAddress.get("postalCode"))) {
				boolean isCanadianPostalCode = ShippingHelper.isCanadianPostalCode(delegator, postalAddress.get("postalCode"));
				if(isCanadianPostalCode) {
					postalAddress.put("countryGeoId", "CAN");
				}
			}*/
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, module);
		}

		return postalAddress;
	}

	public static Map getAddressData(HttpServletRequest request) {
		Map<String, String> addressData = new HashMap<String, String>();
		addressData.put("firstName", (UtilValidate.isNotEmpty(request.getParameter("firstName"))) ? request.getParameter("firstName") : null);
		addressData.put("lastName", (UtilValidate.isNotEmpty(request.getParameter("lastName"))) ? request.getParameter("lastName") : null);
		if(addressData.get("firstName") == null) {
			String name = "";
			if(UtilValidate.isNotEmpty(name = request.getParameter("toName"))) {
				String[] names = splitAtFirstSpace(name);
				if(names.length > 1) {
					addressData.put("firstName", names[0]);
					addressData.put("lastName", names[1]);
				}
			}
		}
		addressData.put("companyName", (UtilValidate.isNotEmpty(request.getParameter("companyName"))) ? request.getParameter("companyName") : null);
		addressData.put("address1", (UtilValidate.isNotEmpty(request.getParameter("address1"))) ? request.getParameter("address1") : null);
		addressData.put("address2", (UtilValidate.isNotEmpty(request.getParameter("address2"))) ? request.getParameter("address2") : null);
		addressData.put("city", (UtilValidate.isNotEmpty(request.getParameter("city"))) ? request.getParameter("city") : null);
		addressData.put("stateProvinceGeoId", (UtilValidate.isNotEmpty(request.getParameter("stateProvinceGeoId"))) ? request.getParameter("stateProvinceGeoId") : null);
		addressData.put("postalCode", (UtilValidate.isNotEmpty(request.getParameter("postalCode"))) ? request.getParameter("postalCode") : null);
		addressData.put("countryGeoId", (UtilValidate.isNotEmpty(request.getParameter("countryGeoId"))) ? request.getParameter("countryGeoId") : "USA");
		return addressData;
	}

	public static Map getAddressingAddressData(HttpServletRequest request) {
		Map<String, String> addressData = new HashMap<String, String>();
		addressData.put("name", (UtilValidate.isNotEmpty(request.getParameter("name"))) ? request.getParameter("name") : null);
		addressData.put("name2", (UtilValidate.isNotEmpty(request.getParameter("name2"))) ? request.getParameter("name2") : null);
		addressData.put("address1", (UtilValidate.isNotEmpty(request.getParameter("address1"))) ? request.getParameter("address1") : null);
		addressData.put("address2", (UtilValidate.isNotEmpty(request.getParameter("address2"))) ? request.getParameter("address2") : null);
		addressData.put("city", (UtilValidate.isNotEmpty(request.getParameter("city"))) ? request.getParameter("city") : null);
		addressData.put("state", (UtilValidate.isNotEmpty(request.getParameter("state"))) ? request.getParameter("state") : null);
		addressData.put("zip", (UtilValidate.isNotEmpty(request.getParameter("zip"))) ? request.getParameter("zip") : null);
		addressData.put("country", (UtilValidate.isNotEmpty(request.getParameter("country"))) ? request.getParameter("country") : null);
		if(UtilValidate.isNotEmpty(request.getParameter("customerAddressId"))) {
			addressData.put("variableDataId", request.getParameter("customerAddressId"));
		}
		addressData.put("variableDataGroupId", request.getParameter("customerAddressGroupId"));
		return addressData;
	}

	public static Map<String, String> saveAddressingData(HttpServletRequest request) throws GenericEntityException {
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		boolean success = false;
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		String partyId = userLogin.getString("partyId");
		Map<String, String> address = PartyHelper.getAddressingAddressData(request);
		String mode = address.containsKey("variableDataId") ? "edit" : "add";

		List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
		conditionList.add(EntityCondition.makeCondition(PARTY_ID, EntityOperator.EQUALS, partyId));
		conditionList.add(EntityCondition.makeCondition("variableDataGroupId", EntityOperator.EQUALS, address.get("variableDataGroupId")));

		DynamicViewEntity dve = new DynamicViewEntity();
		dve.addMemberEntity("ES", "EnvSession");
		dve.addAlias("ES", PARTY_ID);
		dve.addAlias("ES", "sessionId");
		dve.addMemberEntity("VDG", "VariableDataGroup");
		dve.addAlias("VDG", "variableDataGroupId");
		dve.addAlias("VDG", "sessionId");
		dve.addViewLink("ES", "VDG", false, ModelKeyMap.makeKeyMapList("sessionId", "sessionId"));

		List<GenericValue> variableDataGroups = EntityQuery.use(delegator).select("variableDataGroupId", "partyId").from(dve).where(EntityCondition.makeCondition(conditionList)).queryList();
		if(UtilValidate.isNotEmpty(variableDataGroups) && variableDataGroups.get(0) != null && EnvUtil.isOwner(partyId, null, variableDataGroups.get(0), (Security) request.getAttribute("security"), userLogin)) {
			if (mode.equals("add")) {
				String variableDataId = delegator.getNextSeqId("VariableData");
				GenericValue variableDataGV = delegator.makeValue("VariableData", UtilMisc.toMap("variableDataId", variableDataId, "variableDataGroupId", address.get("variableDataGroupId"), "sequenceNum", 0L, "data", concatenateAddressAttributes(address)));
				variableDataGV.create();
				address.put("customerAddressGroupId", address.get("variableDataGroupId"));
				address.put("customerAddressId", variableDataId);
				success = true;
			} else {
				GenericValue variableDataGV = 	delegator.findOne("VariableData", UtilMisc.toMap("variableDataId", address.get("variableDataId")), false);
				variableDataGV.put("data", concatenateAddressAttributes(address));
				variableDataGV.store();
				address.put("customerAddressGroupId", address.get("variableDataGroupId"));
				address.put("customerAddressId", variableDataGV.getString("variableDataId"));
				success = true;
			}
		}

		return success ? address : new HashMap<String, String>();
	}

	protected static String concatenateAddressAttributes(Map<String, String> address) {
		String[] addressAttributes = new String[8];
		addressAttributes[0] = address.get("name");
		addressAttributes[1] = address.get("name2");
		addressAttributes[2] = address.get("address1");
		addressAttributes[3] = address.get("address2");
		addressAttributes[4] = address.get("city");
		addressAttributes[5] = address.get("state");
		addressAttributes[6] = address.get("zip");
		addressAttributes[7] = address.get("country");

		return new Gson().toJson(addressAttributes);
	}

	public static Map getPhoneData(HttpServletRequest request) {
		Map<String, String> phoneData = new HashMap<String, String>();
		phoneData.put("contactNumber", (UtilValidate.isNotEmpty(request.getParameter("contactNumber"))) ? request.getParameter("contactNumber") : null);
		return phoneData;
	}

	public static Map getAddressMetadata(HttpServletRequest request) {
		Map<String, String> metadata = new HashMap<String, String>();
		metadata.put("contactMechPurposeTypeId", (UtilValidate.isNotEmpty(request.getParameter("contactMechPurposeTypeId"))) ? request.getParameter("contactMechPurposeTypeId") : SHIPPING_LOCATION);
		metadata.put("contactMechId", (UtilValidate.isNotEmpty(request.getParameter("contactMechId"))) ? request.getParameter("contactMechId") : null);
		metadata.put("defaultShipping", (UtilValidate.isNotEmpty(request.getParameter("defaultShipping"))) ? request.getParameter("defaultShipping") : null);
		metadata.put("defaultBilling", (UtilValidate.isNotEmpty(request.getParameter("defaultBilling"))) ? request.getParameter("defaultBilling") : null);
		return metadata;
	}

	/*
	 * Check if a phone number already exists for the specified party
	 */
	public static GenericValue getMatchedTelecomNumber(Delegator delegator, Map<String, String> telecomNumber, String partyId, String contactMechPurposeTypeId) throws GenericEntityException {
		Map<String, String> context = new HashMap<String, String>(telecomNumber);
		context.put("partyId", partyId);
		context.put("contactMechPurposeTypeId", contactMechPurposeTypeId);
		GenericValue number = EntityUtil.getFirst(delegator.findByAnd("PartyContactDetailByPurpose", context, null, false));

		return number;
	}

	/*
	 * Get the phone number from the request
	 */
	public static Map getTelecomMap(HttpServletRequest request, boolean isShipNumber) {
		return getTelecomMap(request, null, isShipNumber);
	}
	public static Map getTelecomMap(HttpServletRequest request, Map<String, Object> context, boolean isShipNumber) {
		Map<String, String> telecomNumber = new HashMap<String, String>();

		String prefix = (isShipNumber) ? "shipping_" : "billing_";
		telecomNumber.put("countryCode", (UtilValidate.isNotEmpty(request.getParameter(prefix + "countryCode"))) ? request.getParameter(prefix + "countryCode") : null);
		telecomNumber.put("areaCode", (UtilValidate.isNotEmpty(request.getParameter(prefix + "areaCode"))) ? request.getParameter(prefix + "areaCode") : null);
		telecomNumber.put("contactNumber", (UtilValidate.isNotEmpty(request.getParameter(prefix + "contactNumber"))) ? request.getParameter(prefix + "contactNumber") : null);

		if(UtilValidate.isNotEmpty(context)) {
			telecomNumber.put("countryCode", (UtilValidate.isNotEmpty(context.get(prefix + "countryCode"))) ? (String) context.get(prefix + "countryCode") : null);
			telecomNumber.put("areaCode", (UtilValidate.isNotEmpty(context.get(prefix + "areaCode"))) ? (String) context.get(prefix + "areaCode") : null);
			telecomNumber.put("contactNumber", (UtilValidate.isNotEmpty(context.get(prefix + "contactNumber"))) ? (String) context.get(prefix + "contactNumber") : null);
		}

		return telecomNumber;
	}

	/*
	 * Get matched email address
	 */
	public static GenericValue getMatchedEmailAddress(Delegator delegator, String emailAddress, String partyId, String contactMechTypeId) throws GeneralException {
		Map<String, String> context = new HashMap<String, String>();
		context.put("partyId", partyId);
		context.put("contactMechTypeId", contactMechTypeId);
		List<GenericValue> emailList = delegator.findByAnd("PartyContactDetailByPurpose", context, null, false);
		for(GenericValue email : emailList) {
			if(email.getString("infoString").equalsIgnoreCase(emailAddress)) {
				return email;
			}
		}

		return null;
	}

	public static List<GenericValue> getAssociatedUserLogins(Delegator delegator, String partyId) throws GenericEntityException {
		return delegator.findByAnd("UserLogin", UtilMisc.toMap("partyId", partyId), null, true);
	}

	public static List<String> getAssociatedUserLoginIds(Delegator delegator, String userLoginId) throws GenericEntityException {
		List<String> userLoginIds = new ArrayList<>();
		try {
			String partyId = delegator.findOne("UserLogin", UtilMisc.toMap("userLoginId", userLoginId), true).getString("partyId");
			List<GenericValue> userLogins = getAssociatedUserLogins(delegator, partyId);
			for (GenericValue userLogin : userLogins) {
				userLoginIds.add(userLogin.getString("userLoginId"));
			}
		} catch (Exception e) {
			Debug.logError("Unable to find associated user login ids for the email address " + userLoginId, module);
			userLoginIds.add(userLoginId);
		}
		return userLoginIds;
	}

	/*
	 * Get primary email address
	 */
	public static String getPrimaryEmailAddress(Delegator delegator, String partyId) throws GeneralException {
		GenericValue email = EntityUtil.getFirst(EntityUtil.filterByDate(delegator.findByAnd("PartyContactDetailByPurpose", UtilMisc.toMap("partyId", partyId, "contactMechPurposeTypeId", "PRIMARY_EMAIL", "contactMechTypeId", "EMAIL_ADDRESS"), null, false)));
		return email.getString("infoString");
	}

	/*
	 * Verify billing and shipping
	 */
	public static boolean isValidAddressData(Map<String, String> postalAddress) {
		boolean isValidAddress = true;
		if(UtilValidate.isEmpty(postalAddress.get("address1"))) {
			isValidAddress = false;
		} else if(UtilValidate.isEmpty(postalAddress.get("city"))) {
			isValidAddress = false;
		} else if(UtilValidate.isEmpty(postalAddress.get("stateProvinceGeoId"))) {
			isValidAddress = false;
		} else if(UtilValidate.isEmpty(postalAddress.get("postalCode"))) {
			isValidAddress = false;
		} else if(UtilValidate.isEmpty(postalAddress.get("countryGeoId"))) {
			isValidAddress = false;
		}

		return isValidAddress;
	}

	/*
	 * Create Postal Address
	 */
	public static GenericValue createPostalAddress(Delegator delegator, String partyId, Map<String, String> postalAddress, String contactMechPurposeTypeId) throws GenericEntityException {
		GenericValue contactMech = createPartyContactMech(delegator, partyId, "POSTAL_ADDRESS", null);
		GenericValue address = delegator.makeValue("PostalAddress", UtilMisc.toMap("contactMechId", contactMech.getString("contactMechId")));

		String firstName = null;
		String lastName = null;

		Iterator postalAddressIter = postalAddress.entrySet().iterator();
		while(postalAddressIter.hasNext()) {
			Map.Entry pairs = (Map.Entry) postalAddressIter.next();
			if(((String) pairs.getKey()).equals("firstName")) {
				firstName = (String) pairs.getValue();
			} else if(((String) pairs.getKey()).equals("lastName")) {
				lastName = (String) pairs.getValue();
			} else if(((String) pairs.getKey()).equals("shipTo")) {
				//this field is used for shipping validation, must be removed
			} else {
				address.put((String) pairs.getKey(), (String) pairs.getValue());
			}
		}

		address.put("toName", ((UtilValidate.isNotEmpty(firstName)) ? firstName : "") + ((UtilValidate.isNotEmpty(lastName)) ? " " + lastName : ""));
		delegator.create(address);

		createPartyContactMechPurpose(delegator, partyId, contactMech.getString("contactMechId"), contactMechPurposeTypeId, false);

		return address;
	}

	public static GenericValue updatePostalAddress(Delegator delegator, String contactMechId, Map<String, String> postalAddress) throws GenericEntityException {
		GenericValue address = delegator.makeValue("PostalAddress", UtilMisc.toMap("contactMechId", contactMechId));

		String firstName = null;
		String lastName = null;

		Iterator postalAddressIter = postalAddress.entrySet().iterator();
		while(postalAddressIter.hasNext()) {
			Map.Entry pairs = (Map.Entry) postalAddressIter.next();
			if(((String) pairs.getKey()).equals("firstName")) {
				firstName = (String) pairs.getValue();
			} else if(((String) pairs.getKey()).equals("lastName")) {
				lastName = (String) pairs.getValue();
			} else if(((String) pairs.getKey()).equals("shipTo")) {
				//this field is used for shipping validation, must be removed
			} else {
				address.put((String) pairs.getKey(), (String) pairs.getValue());
			}
		}

		address.put("toName", ((UtilValidate.isNotEmpty(firstName)) ? firstName : "") + ((UtilValidate.isNotEmpty(lastName)) ? " " + lastName : ""));
		delegator.store(address);

		return address;
	}

	/*
	 * Create Telecom Number
	 */
	public static GenericValue createTelecomNumber(Delegator delegator, String partyId, Map<String, String> telcomNumber, String contactMechPurposeTypeId) throws GenericEntityException {
		GenericValue contactMech = createPartyContactMech(delegator, partyId, "TELECOM_NUMBER", null);
		GenericValue number = delegator.makeValue("TelecomNumber", UtilMisc.toMap("contactMechId", contactMech.getString("contactMechId")));

		Iterator telcomNumberIter = telcomNumber.entrySet().iterator();
		while(telcomNumberIter.hasNext()) {
			Map.Entry pairs = (Map.Entry) telcomNumberIter.next();
			number.put((String) pairs.getKey(), (String) pairs.getValue());
		}

		delegator.create(number);

		createPartyContactMechPurpose(delegator, partyId, contactMech.getString("contactMechId"), contactMechPurposeTypeId, false);

		return number;
	}

	public static GenericValue createTelecomNumber(Delegator delegator, String partyId, String contactMechId, Map<String, String> telcomNumber, String contactMechPurposeTypeId) throws GenericEntityException {
		GenericValue telecomMech = createTelecomNumber(delegator, partyId, telcomNumber, contactMechPurposeTypeId);
		if(UtilValidate.isNotEmpty(telecomMech)) {
			PartyHelper.createPartyContactMechAttribute(delegator, contactMechId, "telecomNumber", telecomMech.getString("contactMechId"));
		}
		return telecomMech;
	}

	public static GenericValue updateTelecomNumber(Delegator delegator, String contactMechId, Map<String, String> telecomNumber) throws GenericEntityException {
		GenericValue number = delegator.makeValue("TelecomNumber", UtilMisc.toMap("contactMechId", contactMechId));

		Iterator telecomNumberIter = telecomNumber.entrySet().iterator();
		while(telecomNumberIter.hasNext()) {
			Map.Entry pairs = (Map.Entry) telecomNumberIter.next();
			number.put((String) pairs.getKey(), (String) pairs.getValue());
		}

		delegator.store(number);

		return number;
	}

	public static List<GenericValue> getTelecomNumber(Delegator delegator, String partyId) throws GenericEntityException {
		return EntityQuery.use(delegator).from("PartyAndTelecomNumber").where("partyId", partyId).orderBy("fromDate DESC").filterByDate().queryList();
	}

	public static GenericValue getPartyDefaultContactMechs(Delegator delegator, String partyId) throws GenericEntityException {
		return delegator.findOne("PartyProfileDefault", UtilMisc.toMap("partyId", partyId, "productStoreId", EnvConstantsUtil.ENV_PROD_STORE), true);
	}

	/*
	 * Create the party profile default contact mechs
	 */
	public static void createPartyDefaultContactMechs(Delegator delegator, String partyId, String contactMechId,  ContactMechPurposeType contactMechPurposeType, String productStoreId) throws GenericEntityException {
			GenericValue partyProfileDefault = delegator.makeValue("PartyProfileDefault", UtilMisc.toMap("partyId", partyId));
			partyProfileDefault.put("productStoreId", (UtilValidate.isNotEmpty(productStoreId)) ? productStoreId : EnvConstantsUtil.ENV_PROD_STORE);
			switch(contactMechPurposeType) {
				case SHIPPING_LOCATION_ONLY:
					partyProfileDefault.put("defaultBillAddr", "");
					partyProfileDefault.put("defaultShipAddr", contactMechId);
					break;
				case SHIPPING_LOCATION:
					partyProfileDefault.put("defaultShipAddr", contactMechId);
					break;
				case BILLING_LOCATION_ONLY:
					partyProfileDefault.put("defaultShipAddr", "");
					partyProfileDefault.put("defaultBillAddr", contactMechId);
					break;
				case BILLING_LOCATION:
					partyProfileDefault.put("defaultBillAddr", contactMechId);
					break;
				case SHIPPING_AND_BILLING_LOCATION:
					partyProfileDefault.put("defaultShipAddr", contactMechId);
					partyProfileDefault.put("defaultBillAddr", contactMechId);
					break;
			}
			delegator.createOrStore(partyProfileDefault);
	}

	/*
	 * Create a contact mech purpose types
	 */
	public static void createPartyContactMechPurpose(Delegator delegator, String partyId, String contactMechId, String contactMechPurposeTypeId, boolean removeOldPurpose) throws GenericEntityException {
		if(removeOldPurpose) {
			discPartyContactMechPurpose(delegator, partyId, contactMechPurposeTypeId); //remove existing defaults
		}

		GenericValue contactMechPurpose = delegator.makeValue("PartyContactMechPurpose", UtilMisc.toMap("partyId", partyId, "contactMechId", contactMechId, "contactMechPurposeTypeId", contactMechPurposeTypeId));
		contactMechPurpose.put("fromDate", UtilDateTime.nowTimestamp());
		delegator.create(contactMechPurpose);
	}

	/*
	 * Remove old purpose types
	 */
	public static void discPartyContactMechPurpose(Delegator delegator, String partyId, String contactMechPurposeTypeId) throws GenericEntityException {
		List<GenericValue> partyCMPs = EntityUtil.filterByDate(delegator.findByAnd("PartyContactMechPurpose", UtilMisc.toMap("partyId", partyId, "contactMechPurposeTypeId", contactMechPurposeTypeId), null, false));
		Iterator iter = partyCMPs.iterator();
		while(iter.hasNext()) {
			GenericValue partyCMP = (GenericValue) iter.next();
			partyCMP.set("thruDate", UtilDateTime.nowTimestamp());
			partyCMP.store();
			//delegator.removeValue(partyCMP);
		}
	}

	public static void discPartyContactMechPurpose(Delegator delegator, String partyId, String contactMechId, String contactMechPurposeTypeId) throws GenericEntityException {
		List<GenericValue> partyCMPs = EntityUtil.filterByDate(delegator.findByAnd("PartyContactMechPurpose", UtilMisc.toMap("partyId", partyId, "contactMechId", contactMechId, "contactMechPurposeTypeId", contactMechPurposeTypeId), null, false));
		Iterator iter = partyCMPs.iterator();
		while(iter.hasNext()) {
			GenericValue partyCMP = (GenericValue) iter.next();
			partyCMP.set("thruDate", UtilDateTime.nowTimestamp());
			partyCMP.store();
		}
	}

	/*
	 * Create contact mech attributes
	 */
	public static void createPartyContactMechAttribute(Delegator delegator, String contactMechId, String name, String value) throws GenericEntityException {
		GenericValue contactMechAttr = delegator.makeValue("ContactMechAttribute", UtilMisc.toMap("contactMechId", contactMechId, "attrName", name, "attrValue", value));
		delegator.createOrStore(contactMechAttr);
	}

	/*
	 * Update contact mech attributes
	 */
	public static void updatePartyContactMechAttribute(Delegator delegator, String contactMechId, String name, String value) throws GenericEntityException {
		GenericValue contactMechAttr = EntityQuery.use(delegator).from("ContactMechAttribute").where("contactMechId", contactMechId, "attrName", name).queryOne();
		if(contactMechAttr != null) {
			contactMechAttr.set("attrValue", value);
			contactMechAttr.store();
		}
	}

	/*
	 * Get the credit card details
	 */
	public static Map<String, String> getCreditCardMap(HttpServletRequest request, Map<String, String> billingAddress) {
		Map<String, String> context = new HashMap<String, String>();
		String cardNumber = UtilValidate.isNotEmpty(request.getParameter("cardNumber")) ? ((String) request.getParameter("cardNumber")).replaceAll("[^\\d]", "") : null;
		context.put("cardType", (String) request.getParameter("cardType"));
		context.put("cardNumber", cardNumber);
		context.put("expireDate", (String) request.getParameter("expMonth") + "/" + (String) request.getParameter("expYear"));
		context.put("companyNameOnCard", billingAddress.get("companyName"));
		context.put("firstNameOnCard", billingAddress.get("firstName"));
		context.put("lastNameOnCard", billingAddress.get("lastName"));

		return context;
	}

	/*
	 * Check if the credit card exists
	 */
	public static GenericValue getMatchedCreditCard(Delegator delegator, Map<String, String> creditCard, String contactMechId) throws GenericEntityException {
		if(UtilValidate.isEmpty(contactMechId)) {
			return null;
		}
		Map<String, String> context = new HashMap<String, String>(creditCard);
		context.put("contactMechId", contactMechId);
		GenericValue card = EntityUtil.getFirst(delegator.findByAnd("CreditCard", context, null, false));

		return card;
	}

	/*
	 * Create a check payment entry
	 */
	public static GenericValue createOfflinePayment(Delegator delegator, String contactMechId, String partyId, String paymentMethodTypeId, String checkNumber) throws GenericEntityException {
		GenericValue paymentMethod = createPaymentMethod(delegator, partyId, paymentMethodTypeId);
		GenericValue ccGV = delegator.makeValue("OfflinePaymentMethod", UtilMisc.toMap("paymentMethodId", paymentMethod.getString("paymentMethodId"), "contactMechId", contactMechId, "checkNumber", checkNumber));
		delegator.create(ccGV);

		return ccGV;
	}

	/*
	 * Create an amazon payment entry
	 */
	public static GenericValue createAmazonPayment(Delegator delegator, String contactMechId, String partyId, String paymentMethodTypeId, String amazonOrderId) throws GenericEntityException {
		GenericValue paymentMethod = createPaymentMethod(delegator, partyId, paymentMethodTypeId);
		GenericValue ccGV = delegator.makeValue("AmazonPaymentMethod", UtilMisc.toMap("paymentMethodId", paymentMethod.getString("paymentMethodId"), "contactMechId", contactMechId, "amazonOrderId", amazonOrderId));
		delegator.create(ccGV);

		return ccGV;
	}

	/*
	 * Create an paypal payment entry
	 */
	public static GenericValue createPayPalPayment(Delegator delegator, String contactMechId, String partyId, String paymentMethodTypeId, String payPalOrderId) throws GenericEntityException {
		GenericValue paymentMethod = createPaymentMethod(delegator, partyId, paymentMethodTypeId);
		GenericValue ccGV = delegator.makeValue("PayPalCheckoutPaymentMethod", UtilMisc.toMap("paymentMethodId", paymentMethod.getString("paymentMethodId"), "contactMechId", contactMechId, "payPalOrderId", payPalOrderId));
		delegator.create(ccGV);

		return ccGV;
	}

	/*
	 * Create a credit card entry
	 */
	public static GenericValue createCreditCard(Delegator delegator, Map<String, String> creditCard, String contactMechId, String partyId, String paymentMethodTypeId) throws GenericEntityException {
		GenericValue paymentMethod = createPaymentMethod(delegator, partyId, paymentMethodTypeId);
		GenericValue ccGV = delegator.makeValue("CreditCard", UtilMisc.toMap("paymentMethodId", paymentMethod.getString("paymentMethodId"), "contactMechId", contactMechId));

		Iterator creditCardIter = creditCard.entrySet().iterator();
		while(creditCardIter.hasNext()) {
			Map.Entry pairs = (Map.Entry) creditCardIter.next();
			ccGV.put((String) pairs.getKey(), (String) pairs.getValue());
		}

		delegator.create(ccGV);

		return ccGV;
	}

	/*
	 * Create a payment method entry
	 */
	public static GenericValue createPaymentMethod(Delegator delegator, String partyId, String paymentMethodTypeId) throws GenericEntityException {
		GenericValue paymentMethod = delegator.makeValue("PaymentMethod", UtilMisc.toMap("paymentMethodId", delegator.getNextSeqId("PaymentMethod"), "paymentMethodTypeId", paymentMethodTypeId));
		paymentMethod.put("partyId", partyId);
		paymentMethod.put("description", paymentMethodTypeId);
		paymentMethod.put("fromDate", UtilDateTime.nowTimestamp());

		delegator.create(paymentMethod);

		return paymentMethod;
	}

	public static final String CONTACT_MECH_PURPOSE_TYPE_ID = "contactMechPurposeTypeId";
	public static final String PARTY_ID                     = "partyId";
	public static final String THRU_DATE                    = "thruDate";
	public static final String TO_NAME                      = "toName";
	public static final String FIRST_NAME                   = "firstName";
	public static final String LAST_NAME                    = "lastName";
	public static final String COMPANY_NAME                 = "companyName";
	public static final String ADDRESS1                     = "address1";
	public static final String ADDRESS2                     = "address2";
	public static final String ADDRESS3                     = "address3";
	public static final String CITY                         = "city";
	public static final String STATE_PROVINCE               = "stateProvinceGeoId";
	public static final String COUNTRY                      = "countryGeoId";
	public static final String POSTAL_CODE                  = "postalCode";
	public static final String BLIND_SHIPMENT               = "isBlindShipment";
	public static final String BUSINESS_OR_RESIDENCE        = "businessOrResidence";
	public static final String CONTACT_MECH_ID              = "contactMechId";
	public static final String CONTACT_NUMBER               = "contactNumber";
	public static final String EMAIL_ADDRESS                = "infoString";
	public static final String DEFAULT_BILLING              = "defaultBilling";
	public static final String DEFAULT_SHIPPING             = "defaultShipping";
	public static final String DEFAULTS                     = "defaults";

	public static final String BILLING_ADDRESS = "billingAddress";
	public static final String SHIPPING_ADDRESS = "shippingAddress";

	public static final String SHIPPING_LOCATION = "SHIPPING_LOCATION";
	public static final String BILLING_LOCATION = "BILLING_LOCATION";
	public static final String SHIPPING_AND_BILLING_LOCATION = "SHIPPING_AND_BILLING_LOCATION";
	public static final String PRIMARY_EMAIL = "PRIMARY_EMAIL";

	public static final String RESIDENTIAL_LOCATION = "RESIDENTIAL_LOCATION";
	public static final String BUSINESS_LOCATION = "BUSINESS_LOCATION";

	public static final String EMPTY_STRING ="";
	public static final String ATTRIBUTE_NAME = "attrName";
	public static final String ATTRIBUTE_VALUE = "attrValue";
	public static final String NO = "N";
	public static final String JSON_DATA = "jsonData";

	public static Map<String, Map<String, Map<String, String>>> getAddressBook(HttpServletRequest request, HttpServletResponse response) throws GenericEntityException {
		Map<String, Map<String, Map<String, String>>> addressBook = new LinkedHashMap<>();
		Map<String, Map<String, String>> billingAddresses = new LinkedHashMap<>();
		Map<String, Map<String, String>> shippingAddresses = new LinkedHashMap<>();

		String primaryEmailAddress = EMPTY_STRING;

		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue)request.getSession().getAttribute("userLogin");
		if(UtilValidate.isEmpty(userLogin)){
			return null;
		}
		String partyId = userLogin.getString(PARTY_ID);

		List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
		conditionList.add(EntityCondition.makeCondition(PARTY_ID, EntityOperator.EQUALS, partyId));
		conditionList.add(EntityCondition.makeCondition("PCMPThruDate", EntityOperator.EQUALS, null));
		conditionList.add(EntityCondition.makeCondition("PCMThruDate", EntityOperator.EQUALS, null));
		conditionList.add(EntityCondition.makeCondition(CONTACT_MECH_PURPOSE_TYPE_ID, EntityOperator.IN, Arrays.asList(BILLING_LOCATION, SHIPPING_LOCATION, PRIMARY_EMAIL)));

		List<String> orderBy = new ArrayList<>();
		orderBy.add(TO_NAME);
		orderBy.add(ADDRESS1);

		DynamicViewEntity dve = new DynamicViewEntity();
		dve.addMemberEntity("PCMP", "PartyContactMechPurpose");
		dve.addAlias("PCMP", PARTY_ID);
		dve.addAlias("PCMP", "PCMPThruDate", THRU_DATE, null, null, null, null);
		dve.addAlias("PCMP", CONTACT_MECH_PURPOSE_TYPE_ID);
		dve.addMemberEntity("PCM", "PartyContactMech");
		dve.addViewLink("PCMP", "PCM", true, ModelKeyMap.makeKeyMapList(CONTACT_MECH_ID, CONTACT_MECH_ID));
		dve.addAlias("PCM", "PCMThruDate", THRU_DATE, null, null, null, null);
		dve.addMemberEntity("CM", "ContactMech");
		dve.addAlias("CM", EMAIL_ADDRESS);
		dve.addViewLink("PCMP", "CM", true, ModelKeyMap.makeKeyMapList(CONTACT_MECH_ID, CONTACT_MECH_ID));
		dve.addMemberEntity("PA", "PostalAddress");
		dve.addViewLink("PCMP", "PA", true, ModelKeyMap.makeKeyMapList(CONTACT_MECH_ID, CONTACT_MECH_ID));
		dve.addAliasAll("PA", "", null);

		EntityListIterator eli = null;
		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try {
				eli = delegator.findListIteratorByCondition(dve, EntityCondition.makeCondition(conditionList), null, null, orderBy, null);
				GenericValue contact = null;
				while((contact = eli.next()) != null) {
					if(contact.getString(CONTACT_MECH_PURPOSE_TYPE_ID).equals(PRIMARY_EMAIL)) { // Since we are sorting by FirstName and Address1, PRIMARY_EMAIL will always comes first
						primaryEmailAddress = contact.getString(EMAIL_ADDRESS);
					} else {
						List attributes = delegator.findByAnd("ContactMechAttribute", UtilMisc.toMap(CONTACT_MECH_ID, contact.getString(CONTACT_MECH_ID)), null, true);
						String[] names = splitAtFirstSpace(contact.getString(TO_NAME));
						Map<String, String> addressMap = new HashMap<>();
						addressMap.put(PARTY_ID, contact.getString(PARTY_ID));
						addressMap.put(CONTACT_MECH_ID, contact.getString(CONTACT_MECH_ID));
						addressMap.put(FIRST_NAME, names[0]);
						addressMap.put(LAST_NAME, names[1]);
						addressMap.put(COMPANY_NAME, UtilValidate.isEmpty(contact.getString(COMPANY_NAME)) ? EMPTY_STRING : contact.getString(COMPANY_NAME));
						addressMap.put(ADDRESS1, UtilValidate.isEmpty(contact.getString(ADDRESS1)) ? EMPTY_STRING : contact.getString(ADDRESS1));
						addressMap.put(ADDRESS2, UtilValidate.isEmpty(contact.getString(ADDRESS2)) ? EMPTY_STRING : contact.getString(ADDRESS2));
						//                        addressMap.put(ADDRESS3, UtilValidate.isEmpty(contact.getString(ADDRESS3)) ? EMPTY_STRING : contact.getString(ADDRESS3));
						addressMap.put(CITY, UtilValidate.isEmpty(contact.getString(CITY)) ? EMPTY_STRING : contact.getString(CITY));
						addressMap.put(STATE_PROVINCE, UtilValidate.isEmpty(contact.getString(STATE_PROVINCE)) ? EMPTY_STRING : contact.getString(STATE_PROVINCE));
						addressMap.put(POSTAL_CODE, UtilValidate.isEmpty(contact.getString(POSTAL_CODE)) ? EMPTY_STRING : contact.getString(POSTAL_CODE));
						addressMap.put(COUNTRY, UtilValidate.isEmpty(contact.getString(COUNTRY)) ? EMPTY_STRING : contact.getString(COUNTRY));
						addressMap.put(EMAIL_ADDRESS, primaryEmailAddress);
						for (Object attribute : attributes) {
							GenericValue attr = (GenericValue) attribute;
							if (attr.getString(ATTRIBUTE_NAME).equals("telecomNumber")) {
								GenericValue telecomNumber = delegator.findOne("TelecomNumber", UtilMisc.toMap(CONTACT_MECH_ID, attr.getString(ATTRIBUTE_VALUE)), true);
								String phoneNumber = telecomNumber != null ? telecomNumber.getString(CONTACT_NUMBER) : EMPTY_STRING;
								addressMap.put(CONTACT_NUMBER, UtilValidate.isEmpty(phoneNumber) ? EMPTY_STRING : phoneNumber);
							} else if (attr.getString(ATTRIBUTE_NAME).equals(BUSINESS_OR_RESIDENCE)) {
								addressMap.put(BUSINESS_OR_RESIDENCE, attr.getString(ATTRIBUTE_VALUE));
							} else if (attr.getString(ATTRIBUTE_NAME).equals(BLIND_SHIPMENT)) {
								addressMap.put(BLIND_SHIPMENT, attr.getString(ATTRIBUTE_VALUE));
							}
						}

						if (!addressMap.containsKey(CONTACT_NUMBER)) {
							addressMap.put(CONTACT_NUMBER, EMPTY_STRING);
						}


						if (contact.getString(CONTACT_MECH_PURPOSE_TYPE_ID).equals(BILLING_LOCATION)) {
							Gson gson = new Gson();
							String addressJSON = gson.toJson(addressMap);
							addressMap.put(JSON_DATA, addressJSON);
							billingAddresses.put(addressMap.get(CONTACT_MECH_ID), addressMap);
						} else if (contact.getString(CONTACT_MECH_PURPOSE_TYPE_ID).equals(SHIPPING_LOCATION)) {
							if (!addressMap.containsKey(BUSINESS_OR_RESIDENCE)) {
								addressMap.put(BUSINESS_OR_RESIDENCE, BUSINESS_LOCATION);
							}

							if (!addressMap.containsKey(BLIND_SHIPMENT)) {
								addressMap.put(BLIND_SHIPMENT, NO);
							}
							Gson gson = new Gson();
							String addressJSON = gson.toJson(addressMap);
							addressMap.put(JSON_DATA, addressJSON);
							shippingAddresses.put(addressMap.get(CONTACT_MECH_ID), addressMap);
						}
					}
				}
				addressBook.put(SHIPPING_ADDRESS, shippingAddresses);
				addressBook.put(BILLING_ADDRESS, billingAddresses);
			} catch (GenericEntityException e1) {
				TransactionUtil.rollback(beganTransaction, "Error looking up shipping and billing addresses", e1);
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
		return addressBook;
	}

	public enum ContactMechPurposeType {SHIPPING_LOCATION, SHIPPING_LOCATION_ONLY, BILLING_LOCATION, BILLING_LOCATION_ONLY, SHIPPING_AND_BILLING_LOCATION}

	public static String removeAddress(HttpServletRequest request, HttpServletResponse response) throws GenericEntityException {
		boolean success = false;
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		String contactMechId = request.getParameter("contactMechId");
		if(UtilValidate.isNotEmpty(contactMechId)) {
			Delegator delegator = (Delegator) request.getAttribute("delegator");
			GenericValue partyDefaultContactMechs = PartyHelper.getPartyDefaultContactMechs(delegator, userLogin.getString("partyId"));
			String defaultShippingContactMechId = UtilValidate.isNotEmpty(partyDefaultContactMechs) ? partyDefaultContactMechs.getString("defaultShipAddr") : "";
			String defaultBillingContactMechId = UtilValidate.isNotEmpty(partyDefaultContactMechs) ? partyDefaultContactMechs.getString("defaultBillAddr") : "";
			boolean isDefaultShipping = contactMechId.equals(defaultShippingContactMechId);
			boolean isDefaultBilling = contactMechId.equals(defaultBillingContactMechId);

			ContactMechPurposeType profileDefaultContactMechPurposeType = null;

			if(isDefaultShipping && isDefaultBilling) {
				profileDefaultContactMechPurposeType = ContactMechPurposeType.SHIPPING_AND_BILLING_LOCATION;
			} else if(isDefaultShipping) {
				profileDefaultContactMechPurposeType = ContactMechPurposeType.SHIPPING_LOCATION;
			} else if(isDefaultBilling) {
				profileDefaultContactMechPurposeType = ContactMechPurposeType.BILLING_LOCATION;
			}

			if(profileDefaultContactMechPurposeType != null) {
				PartyHelper.createPartyDefaultContactMechs(delegator, userLogin.getString("partyId"), "", profileDefaultContactMechPurposeType, null);
			}

			List<GenericValue> partyCMPs = EntityUtil.filterByDate(delegator.findByAnd("PartyContactMechPurpose", UtilMisc.toMap("partyId", userLogin.getString("partyId"), "contactMechId", contactMechId), null, false));
			Iterator iter = partyCMPs.iterator();
			int counter = 0;
			while(iter.hasNext()) {
				GenericValue partyCMP = (GenericValue) iter.next();
				partyCMP.set("thruDate", UtilDateTime.nowTimestamp());
				partyCMP.store();
				counter ++;
			}
			if(counter > 0) {
				success = true;
			}
		}
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("success", success);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}
	private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	public static List<Map<String, Object>> getSavedDesigns(HttpServletRequest request, HttpServletResponse response) throws GenericEntityException {
		List<Map<String, Object>> designs = new ArrayList<>();
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		String partyId = userLogin != null ? userLogin.getString("partyId") : "";
		if(UtilValidate.isEmpty(partyId)) {
			return new ArrayList<>();
		}

		Delegator delegator = (Delegator) request.getAttribute("delegator");

		List<EntityCondition> conditionList = new ArrayList<>();
		conditionList.add(EntityCondition.makeCondition(PARTY_ID, EntityOperator.EQUALS, partyId));
		conditionList.add(EntityCondition.makeCondition("parentId", EntityOperator.EQUALS, null));
		conditionList.add(EntityCondition.makeCondition("data", EntityOperator.NOT_EQUAL, null));
		conditionList.add(EntityCondition.makeCondition("inactive", EntityOperator.EQUALS, null));

		List<GenericValue> designGVs =  EntityQuery.use(delegator).select("scene7DesignId", "data", "lastUpdatedStamp").from("Scene7Design").where(conditionList).orderBy("createdStamp").queryList();
		for (GenericValue designGV : designGVs) {
			try {
				Map<String, Object> designAttributes = new HashMap<>();
				designAttributes.put("projectId", designGV.getString("scene7DesignId"));
				designAttributes.put("parentId", designGV.getString("scene7DesignId"));
				designAttributes.put("lastUpdatedStamp", sdf.format(designGV.get("lastUpdatedStamp")));
				Map<String, Object> dataGson = new Gson().fromJson(designGV.getString("data"), HashMap.class);
				Map<String, Object> settings  = (Map<String, Object>) dataGson.get("settings");
				designAttributes.put("productType", UtilValidate.isEmpty(settings.get("productType")) ? "product" : settings.get("productType"));
				List products = (List)settings.get("product");
				if(UtilValidate.isEmpty(products)) {
					continue;
				}

				String productId = (UtilValidate.isNotEmpty(((Map) products.get(0)).get("sku"))) ? (String) ((Map) products.get(0)).get("sku") : (String) ((Map) products.get(0)).get("productId");
				GenericValue product = delegator.findOne("Product", UtilMisc.toMap("productId", productId), true);

				designAttributes.put("sku", productId);
				designAttributes.put("scene7DesignId", ((Map)products.get(0)).get("scene7DesignId"));
				designAttributes.put("designId", ((Map)products.get(0)).get("designId"));
				designAttributes.put("categoryId", (UtilValidate.isNotEmpty(product) && UtilValidate.isNotEmpty(product.getString("primaryProductCategoryId"))) ? product.getString("primaryProductCategoryId") : null);
				designs.add(designAttributes);
			} catch (Exception e) {
				//todo
			}
		}

		return designs;
	}

	public static String deactivateDesign(HttpServletRequest request, HttpServletResponse response) throws GenericEntityException {
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		String parentId = (String) context.get("scene7DesignId");

		Delegator delegator = (Delegator) request.getAttribute("delegator");

		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		String partyId = userLogin != null ? userLogin.getString("partyId") : "";

		List<GenericValue> designs = delegator.findByAnd("Scene7Design", UtilMisc.toMap("scene7DesignId", parentId), null, false);
		if(UtilValidate.isNotEmpty(designs) && designs.size() > 0) {
			GenericValue design = designs.get(0);
			if (UtilValidate.isNotEmpty(design) && design.getString("partyId").equals(partyId)) {
				design.put("inactive", "Y");
				design.store();
				success = true;
			}
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String saveAddress(HttpServletRequest request, HttpServletResponse response) throws GenericEntityException { //TODO - Exception Handling

		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		boolean success = false;

		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		if(userLogin != null) {
			Delegator delegator = (Delegator) request.getAttribute("delegator");
			Map<String, String> address = PartyHelper.getAddressData(request);
			Map<String, String> phone = PartyHelper.getPhoneData(request);
			Map<String, String> addressMetadata = PartyHelper.getAddressMetadata(request);
			String partyId = userLogin.getString("partyId");
			String contactMechId = addressMetadata.get("contactMechId");
			String mode = UtilValidate.isNotEmpty(addressMetadata.get("contactMechId")) ? "edit" : "add";

			ContactMechPurposeType contactMechPurposeType = ContactMechPurposeType.valueOf(addressMetadata.get("contactMechPurposeTypeId"));

			boolean isDefaultShipping = addressMetadata.get("defaultShipping") != null && addressMetadata.get("defaultShipping").equals("true");
			boolean isDefaultBilling = addressMetadata.get("defaultBilling") != null && addressMetadata.get("defaultBilling").equals("true");

			GenericValue partyDefaultContactMechs = PartyHelper.getPartyDefaultContactMechs(delegator, userLogin.getString("partyId"));
			String defaultShippingContactMechId = UtilValidate.isNotEmpty(partyDefaultContactMechs) ? partyDefaultContactMechs.getString("defaultShipAddr") : "";
			String defaultBillingContactMechId = UtilValidate.isNotEmpty(partyDefaultContactMechs) ? partyDefaultContactMechs.getString("defaultBillAddr") : "";
			boolean isShippingAlreadyDefault = false, isBillingAlreadyDefault = false;

			//ContactMechPurposeType for setting the address as default Shipping address or default Billing address or both
			ContactMechPurposeType profileDefaultContactMechPurposeType = null;
			String profileDefaultContactMechId = null;

			GenericValue shippingContactMech = null;
			GenericValue billingContactMech = null;

			GenericValue shippingTelecomMech = null;
			GenericValue billingTelecomMech = null;

			if(mode.equals("add")) {
				shippingContactMech = PartyHelper.getMatchedPostalAddress(delegator, address, userLogin.getString("partyId"), SHIPPING_LOCATION);
				billingContactMech = PartyHelper.getMatchedPostalAddress(delegator, address, userLogin.getString("partyId"), BILLING_LOCATION);
				if(UtilValidate.isNotEmpty(shippingContactMech)) {
					shippingTelecomMech = PartyHelper.getMatchedTelecomNumber(delegator, shippingContactMech.getString("contactMechId"), userLogin.getString("partyId"), "PHONE_SHIPPING");
				}
				if(UtilValidate.isNotEmpty(billingContactMech)) {
					billingTelecomMech = PartyHelper.getMatchedTelecomNumber(delegator, billingContactMech.getString("contactMechId"), userLogin.getString("partyId"), "PHONE_BILLING");
				}
			} else {
				shippingContactMech = PartyHelper.getMatchedPostalAddress(delegator, contactMechId, userLogin.getString("partyId"), SHIPPING_LOCATION);
				billingContactMech = PartyHelper.getMatchedPostalAddress(delegator, contactMechId, userLogin.getString("partyId"), BILLING_LOCATION);
				shippingTelecomMech = PartyHelper.getMatchedTelecomNumber(delegator, contactMechId, userLogin.getString("partyId"), "PHONE_SHIPPING");
				billingTelecomMech = PartyHelper.getMatchedTelecomNumber(delegator, contactMechId, userLogin.getString("partyId"), "PHONE_BILLING");
			}

			if(UtilValidate.isNotEmpty(shippingContactMech)) {
				isShippingAlreadyDefault = shippingContactMech.getString("contactMechId").equals(defaultShippingContactMechId);
			}

			if(UtilValidate.isNotEmpty(billingContactMech)) {
				isBillingAlreadyDefault = billingContactMech.getString("contactMechId").equals(defaultBillingContactMechId);
			}
			switch(contactMechPurposeType) {
				/**
				 * The user selected to save the given address as both Shipping and Billing address. When the same address is marked as both Shipping and Billing,
				 * only one Postal Address will be created in the postal_address table. The same address will then be marked as both Shipping and Billing address
				 * in the party_contact_mech_purpose table. The same approach is used for the Telecom Number associated with the address. One Telecom Number will be
				 * created in the telecom_number table and the same number will ten be marked as Shipping and Billing number in the party_contact_mech_purpose table.
				 * If the given address exists as a shipping and billing address, then we will update the existing address with the given parameters. If the given
				 * address exists as either a shipping address or billing address, then we will update the existing Shipping or Billing address and will then mark
				 * them as Billing and Shipping address in party_contact_mech_purpose table. If the existing address has an associate Telecom Number, that will also
				 * be updated with the given number and will be then marked as Shipping and Billing number in party_contact_mech_purpose table. If an address is
				 * stored as a Shipping and Billing address, then this address will show up in both Shipping and Billing address books during checkout.
				 */
				case SHIPPING_AND_BILLING_LOCATION:
					/**
					 * No address exists, so create a new Postal address and mark it as both Shipping and Billing address in party_contact_mech_purpose table.
					 * This scenario will occur only when a new (non-duplicate) address is added to the address book. Since no postal address exists, there won't
					 * be any Telecom Number either. So create a new Telecom Number and mark it as both Shipping and Billing address in party_contact_mech_purpose
					 * table as well.
					 */
					if(UtilValidate.isEmpty(shippingContactMech) && UtilValidate.isEmpty(billingContactMech)) {
						//Create a new Postal Address and mark it as SHIPPING_LOCATION in party_contact_mech_purpose table
						shippingContactMech = PartyHelper.createPostalAddress(delegator, partyId, address, "SHIPPING_LOCATION");

						//Mark the newly created postal address as BILLING_LOCATION as well in party_contact_mech_purpose table.
						PartyHelper.createPartyContactMechPurpose(delegator, partyId, shippingContactMech.getString("contactMechId"), "BILLING_LOCATION", false);
						billingContactMech = shippingContactMech;

						//Create a new Telecom Number and mark it as PHONE_SHIPPING in party_contact_mech_purpose table
						shippingTelecomMech = PartyHelper.createTelecomNumber(delegator, userLogin.getString("partyId"), shippingContactMech.getString("contactMechId"), phone, "PHONE_SHIPPING");

						//Mark the newly createdTelecom Number as PHONE_BILLING as well in party_contact_mech_purpose table
						PartyHelper.createPartyContactMechPurpose(delegator, userLogin.getString("partyId"), shippingTelecomMech.getString("contactMechId"), "PHONE_BILLING", false);
						billingTelecomMech = shippingTelecomMech;
					}
					/**
					 * A Postal address exists and is marked as Shipping address in party_contact_mech_purpose table. Update the existing address(only in edit mode) and mark it as Billing address as well
					 * in the party_contact_mech_purpose table. If the existing Shipping address has an associated Telecom Number, update the existing number and mark the existing Telecom Number as
					 * Billing Number in party_contact_mech_purpose table. If the existing Shipping address doesn't have an associated Telecom Number, and given address has a telephone number, create a
					 * new Telecom number and mark it as Shipping and Billing Number in party_contact_mech_purpose table.
					 */
					else if(UtilValidate.isEmpty(billingContactMech)) {
						/**
						 * If we are in edit mode, update the existing Postal Address.
						 */
						if(mode.equals("edit")) {
							PartyHelper.updatePostalAddress(delegator, contactMechId, address);
						}

						//If the existing Postal address is not marked as Shipping Address, mark it as Shipping Address
						if(shippingContactMech.get("purposeThruDate") != null) {
							PartyHelper.createPartyContactMechPurpose(delegator, partyId, shippingContactMech.getString("contactMechId"), "SHIPPING_LOCATION", false);
						}

						// If there is no Shipping Telecom Number and the given address has a Telecom Number, create a new Telecom Number and mark it as Shipping Number
						if(UtilValidate.isEmpty(shippingTelecomMech) && UtilValidate.isNotEmpty(phone)){
							shippingTelecomMech = PartyHelper.createTelecomNumber(delegator, userLogin.getString("partyId"), shippingContactMech.getString("contactMechId"), phone, "PHONE_SHIPPING");
						}

						//Mark the existing Postal address as Billing address
						PartyHelper.createPartyContactMechPurpose(delegator, partyId, shippingContactMech.getString("contactMechId"), "BILLING_LOCATION", false);
						billingContactMech = shippingContactMech;

						//If there is a Shipping Number, update the number and mark it as Billing Number
						if(UtilValidate.isNotEmpty(shippingTelecomMech)) {
							PartyHelper.updateTelecomNumber(delegator, shippingTelecomMech.getString("contactMechId"), phone);
							PartyHelper.createPartyContactMechPurpose(delegator, userLogin.getString("partyId"), shippingTelecomMech.getString("contactMechId"), "PHONE_BILLING", false);
							billingTelecomMech = shippingTelecomMech;
						}
					}
					/**
					 * A Postal address exists and is marked as Billing address in party_contact_mech_purpose table. Update the existing address(only in edit mode) and mark it as Shipping address as well
					 * in the party_contact_mech_purpose table. If the existing Billing address has an associated Telecom Number, update the existing number and mark the existing Telecom Number as
					 * Shipping Number in party_contact_mech_purpose table. If the existing Billing address doesn't have an associated Telecom Number, and given address has a telephone number, create a
					 * new Telecom number and mark it as Shipping and Billing Number in party_contact_mech_purpose table.
					 */
					else if(UtilValidate.isEmpty(shippingContactMech)) {
						/**
						 * If we are in edit mode, update the existing Postal Address.
						 */
						if (mode.equals("edit")) {
							// Update the Postal Address
							PartyHelper.updatePostalAddress(delegator, contactMechId, address);
						}

						//If the existing Postal address is not marked as Billing Address, mark it as Billing Address
						if(billingContactMech.get("purposeThruDate") != null) {
							PartyHelper.createPartyContactMechPurpose(delegator, partyId, billingContactMech.getString("contactMechId"), "BILLING_LOCATION", false);
						}

						// If there is no Billing Telecom Number and the given address has a Telecom Number, create a new Telecom Number and mark it as Billing Number
						if (UtilValidate.isEmpty(billingTelecomMech) && UtilValidate.isNotEmpty(phone)) {
							billingTelecomMech = PartyHelper.createTelecomNumber(delegator, userLogin.getString("partyId"), billingContactMech.getString("contactMechId"), phone, "PHONE_BILLING");
						}

						//Mark the existing Postal address as Shipping address
						PartyHelper.createPartyContactMechPurpose(delegator, partyId, billingContactMech.getString("contactMechId"), "SHIPPING_LOCATION", false);
						shippingContactMech = billingContactMech;

						//If there is a Billing Number, update the number and mark it as Shipping Number
						if (UtilValidate.isNotEmpty(billingTelecomMech)) {
							PartyHelper.updateTelecomNumber(delegator, billingTelecomMech.getString("contactMechId"), phone);
							PartyHelper.createPartyContactMechPurpose(delegator, userLogin.getString("partyId"), billingTelecomMech.getString("contactMechId"), "PHONE_SHIPPING", false);
							shippingTelecomMech = billingTelecomMech;
						}
					}
					/**
					 * A Postal address exists and is marked as Shipping and Billing address in party_contact_mech_purpose table. Update the existing address(only in edit mode). If the existing Shipping
					 * and Billing addresses have an associated Telecom Number, update the existing number. If only one of the Shipping or Billing address has an associated Telecom Number, update the
					 * existing number and mark the existing Telecom Number as both Shipping and Billing number. If none of the Shipping or Billing address has an associated Telecom Number, and the
					 * given address has a phone number, create a new Telecom Number and mark it as both Shipping and Billing number.
					 */
					else {

						/**
						 * If we are in edit mode, update the existing Postal Address. Also update the telecom number, if exists, as well.
						 */
						if(mode.equals("edit")) {
							// Update the Postal Address
							PartyHelper.updatePostalAddress(delegator, contactMechId, address);
						}

						//If the existing Postal address is not marked as Shipping Address, mark it as Shipping Address
						if(shippingContactMech.get("purposeThruDate") != null) {
							PartyHelper.createPartyContactMechPurpose(delegator, partyId, shippingContactMech.getString("contactMechId"), "SHIPPING_LOCATION", false);
						}

						//If the existing Postal address is not marked as Billing Address, mark it as Billing Address
						if(billingContactMech.get("purposeThruDate") != null) {
							PartyHelper.createPartyContactMechPurpose(delegator, partyId, billingContactMech.getString("contactMechId"), "BILLING_LOCATION", false);
						}

						// Update the Telecom Number, if any
						if(UtilValidate.isNotEmpty(shippingTelecomMech) || UtilValidate.isNotEmpty(billingTelecomMech)) {
							String existingTelecomContactMechId = "";
							String requiredMechPurposeType = "";
							if(UtilValidate.isEmpty(shippingTelecomMech)) {
								existingTelecomContactMechId = billingTelecomMech.getString("contactMechId");
								requiredMechPurposeType = "PHONE_SHIPPING";
							} else if(UtilValidate.isEmpty(billingTelecomMech)) {
								existingTelecomContactMechId = shippingTelecomMech.getString("contactMechId");
								requiredMechPurposeType = "PHONE_BILLING";
							} else {
								existingTelecomContactMechId = shippingTelecomMech.getString("contactMechId");
							}
							// Update the Telecom Number
							PartyHelper.updateTelecomNumber(delegator, existingTelecomContactMechId, phone);

							// If the Telecom Number is marked as only a Shipping Number or a Billing Number, mark the existing number as Shipping and Billing number.
							if(UtilValidate.isNotEmpty(requiredMechPurposeType)) {
								PartyHelper.createPartyContactMechPurpose(delegator, userLogin.getString("partyId"), existingTelecomContactMechId, requiredMechPurposeType, false);
							}
						}

						/**
						 * If the existing Postal address has no Telecom Number and the given address has a Phone Number, create a Telecom Number and mark it as Shipping and Billing number.
						 */
						if(UtilValidate.isEmpty(shippingTelecomMech) && UtilValidate.isEmpty(billingTelecomMech) && UtilValidate.isNotEmpty(phone)){
							// Create a Shipping Telecom Number
							shippingTelecomMech = PartyHelper.createTelecomNumber(delegator, userLogin.getString("partyId"), shippingContactMech.getString("contactMechId"), phone, "PHONE_SHIPPING");
							//Mark the created Shipping Number as Billing Number as well
							PartyHelper.createPartyContactMechPurpose(delegator, userLogin.getString("partyId"), shippingTelecomMech.getString("contactMechId"), "PHONE_BILLING", false);
						}
					}

					if(isDefaultShipping && isDefaultBilling) {
						profileDefaultContactMechPurposeType = ContactMechPurposeType.SHIPPING_AND_BILLING_LOCATION;
					} else if(isDefaultShipping) {
						profileDefaultContactMechPurposeType = isBillingAlreadyDefault ? ContactMechPurposeType.SHIPPING_LOCATION_ONLY : ContactMechPurposeType.SHIPPING_LOCATION;
					} else if(isDefaultBilling) {
						profileDefaultContactMechPurposeType = isShippingAlreadyDefault ? ContactMechPurposeType.BILLING_LOCATION_ONLY : ContactMechPurposeType.BILLING_LOCATION;
					}
					profileDefaultContactMechId = billingContactMech.getString("contactMechId");
					success = true;
					break;

				/**
				 * The user selected to save the given address as both Billing address. When the same address is marked as both Shipping and Billing,
				 * only one Postal Address will be created in the postal_address table. The same address will then be marked as both Shipping and Billing address
				 * in the party_contact_mech_purpose table. When an existing Shipping or Billing address is removed, we won't delete any records in the backend DB,
				 * instead the thru_date will be set to current date in the party_contact_mech_purpose table. The same approach is used for the Telecom Number
				 * associated with the address as well. In order to discard a Telecom Number, we will set the thru_date to current date in party_contact_mech_purpose
				 * table.
				 */
				case BILLING_LOCATION:
					/**
					 * No address exists, so create a new Postal address and mark it as both Billing address in party_contact_mech_purpose table.
					 * This scenario will occur only when a new (non-duplicate) address is added to the address book. Since no postal address exists, there won't
					 * be any Telecom Number either. So create a new Telecom Number and mark it as Billing Number in party_contact_mech_purpose
					 * table as well.
					 */
					if(UtilValidate.isEmpty(shippingContactMech) && UtilValidate.isEmpty(billingContactMech)) {
						//Create a new Postal Address and mark it as BILLING_LOCATION in party_contact_mech_purpose table
						billingContactMech = PartyHelper.createPostalAddress(delegator, partyId, address, "BILLING_LOCATION");

						//Create a new Telecom Number and mark it as PHONE_BILLING in party_contact_mech_purpose table
						billingTelecomMech = PartyHelper.createTelecomNumber(delegator, userLogin.getString("partyId"), billingContactMech.getString("contactMechId"), phone, "PHONE_BILLING");

					}
					/**
					 * A Postal address exists and is marked as Shipping address in party_contact_mech_purpose table. Update the existing address(only in edit mode) and mark
					 * the existing Postal address as Billing address in the party_contact_mech_purpose table and also (In edit mode) discard the Shipping address by setting a thru_date in
					 * party_contact_mech_purpose table.If the existing Shipping address has an associated Telecom Number, update the number and mark the existing Telecom Number as Billing Number
					 * in party_contact_mech_purpose table and (in edit mode) discard the Shipping Number by setting a thru_date party_contact_mech_purpose table. If the existing Shipping
					 * address doesn't have an associated Telecom Number, and the given address has a phone number, create a new Telecom number and mark it as Billing Number
					 *  in party_contact_mech_purpose table.
					 */
					else if(UtilValidate.isEmpty(billingContactMech)) {
						/**
						 * If we are in edit mode, update the existing Postal Address.
						 */
						if(mode.equals("edit")) {
							//Update the Postal Address
							PartyHelper.updatePostalAddress(delegator, contactMechId, address);

							//Discard the existing Shipping Address
							PartyHelper.discPartyContactMechPurpose(delegator, userLogin.getString("partyId"), shippingContactMech.getString("contactMechId"), "SHIPPING_LOCATION");
							shippingContactMech = null;

							// Discard the shipping number if exists
							if(UtilValidate.isNotEmpty(shippingTelecomMech)) {
								//Discard the existing Shipping Number
								PartyHelper.discPartyContactMechPurpose(delegator, userLogin.getString("partyId"), shippingTelecomMech.getString("contactMechId"), "PHONE_SHIPPING");
								shippingTelecomMech = null;
							}
						}

						//Mark the existing Postal address as Billing address
						PartyHelper.createPartyContactMechPurpose(delegator, partyId, shippingContactMech.getString("contactMechId"), "BILLING_LOCATION", false);
						billingContactMech = shippingContactMech;

						// If there is no Shipping Telecom Number and the given address has a Telecom Number, create a new Telecom Number and mark it as Billing Number
						if(UtilValidate.isEmpty(shippingTelecomMech) && UtilValidate.isNotEmpty(phone)){
							billingTelecomMech = PartyHelper.createTelecomNumber(delegator, userLogin.getString("partyId"), billingContactMech.getString("contactMechId"), phone, "PHONE_BILLING");
						}

						//If there is a Shipping Number, update the number and mark it as Billing Number
						else if(UtilValidate.isNotEmpty(shippingTelecomMech)) {
							PartyHelper.updateTelecomNumber(delegator, shippingTelecomMech.getString("contactMechId"), phone);
							PartyHelper.createPartyContactMechPurpose(delegator, userLogin.getString("partyId"), shippingTelecomMech.getString("contactMechId"), "PHONE_BILLING", false);
							billingTelecomMech = shippingTelecomMech;
						}
					}
					/**
					 * A Postal address exists and is marked as Billing address in party_contact_mech_purpose table. Update the existing address(only in edit mode). If the existing Billing
					 * address has an associated Telecom Number, update the Telecom Number. If the existing Billing address doesn't have an associated Telecom Number, and the given address
					 * has a phone number, create a new Telecom number and mark it as Billing Number in party_contact_mech_purpose table.
					 */
					else if(UtilValidate.isEmpty(shippingContactMech)) {
						/**
						 * If we are in edit mode, update the existing Postal Address.
						 */
						if(mode.equals("edit")) {
							//Update the Postal Address
							PartyHelper.updatePostalAddress(delegator, contactMechId, address);
						}

						//If the existing Postal address is not marked as Billing Address, mark it as Billing Address
						if(billingContactMech.get("purposeThruDate") != null) {
							PartyHelper.createPartyContactMechPurpose(delegator, partyId, billingContactMech.getString("contactMechId"), "BILLING_LOCATION", false);
						}

						// If there is no Billing Telecom Number and the given address has a Telecom Number, create a new Telecom Number and mark it as Billing Number
						if(UtilValidate.isEmpty(billingTelecomMech) && UtilValidate.isNotEmpty(phone)){
							billingTelecomMech = PartyHelper.createTelecomNumber(delegator, userLogin.getString("partyId"), billingContactMech.getString("contactMechId"), phone, "PHONE_BILLING");
						}

						//If there is a Billing Number, update the number
						else if(UtilValidate.isNotEmpty(billingTelecomMech)) {
							PartyHelper.updateTelecomNumber(delegator, billingTelecomMech.getString("contactMechId"), phone);
						}
					}
					/**
					 * A Postal address exists and is marked as Shipping and Billing address in party_contact_mech_purpose table. Update the existing address(only in edit mode). If the existing Shipping
					 * and Billing addresses have an associated Telecom Number, update the existing number. If only one of the Shipping or Billing address has an associated Telecom Number, update the
					 * existing number and mark the existing Telecom Number as both Shipping and Billing number. If none of the Shipping or Billing address has an associated Telecom Number, and the
					 * given address has a phone number, create a new Telecom Number and mark it as both Shipping and Billing number. In edit mode, discard the Shipping Address and Shipping Telecom Number, if exists
					 */
					else {

						/**
						 * If we are in edit mode, update the existing Postal Address.
						 */
						if (mode.equals("edit")) {
							//Update the Postal Address
							PartyHelper.updatePostalAddress(delegator, contactMechId, address);

							//Discard the existing Shipping Address
							PartyHelper.discPartyContactMechPurpose(delegator, userLogin.getString("partyId"), shippingContactMech.getString("contactMechId"), "SHIPPING_LOCATION");
							shippingContactMech = null;

							// Discard the shipping number if exists
							if (UtilValidate.isNotEmpty(shippingTelecomMech)) {
								//Discard the existing Shipping Number
								PartyHelper.discPartyContactMechPurpose(delegator, userLogin.getString("partyId"), shippingTelecomMech.getString("contactMechId"), "PHONE_SHIPPING");
								shippingTelecomMech = null;
							}
						}

						//If the existing Postal address is not marked as Billing Address, mark it as Billing Address
						if(billingContactMech.get("purposeThruDate") != null) {
							PartyHelper.createPartyContactMechPurpose(delegator, partyId, billingContactMech.getString("contactMechId"), "BILLING_LOCATION", false);
						}


						// If there is no Shipping and Billing Telecom Number and the given address has a Telecom Number, create a new Telecom Number and mark it as Shipping and Billing Number
						if (UtilValidate.isEmpty(shippingTelecomMech) && UtilValidate.isEmpty(billingTelecomMech) && UtilValidate.isNotEmpty(phone)) {
							billingTelecomMech = PartyHelper.createTelecomNumber(delegator, userLogin.getString("partyId"), billingContactMech.getString("contactMechId"), phone, "PHONE_BILLING");
							PartyHelper.createPartyContactMechPurpose(delegator, userLogin.getString("partyId"), billingTelecomMech.getString("contactMechId"), "PHONE_SHIPPING", false);
							shippingTelecomMech = billingTelecomMech;
						}

						//If there is a Shipping Number and no Billing number, update the number and mark it as Billing Number
						else if (UtilValidate.isNotEmpty(shippingTelecomMech)  && UtilValidate.isEmpty(billingTelecomMech)) {
							PartyHelper.updateTelecomNumber(delegator, shippingTelecomMech.getString("contactMechId"), phone);
							PartyHelper.createPartyContactMechPurpose(delegator, userLogin.getString("partyId"), shippingTelecomMech.getString("contactMechId"), "PHONE_BILLING", false);
							billingTelecomMech = shippingTelecomMech;
						}

						//If there is a Billing Number and no Shipping number, update the number and mark it as Shipping Number
						else if (UtilValidate.isNotEmpty(billingTelecomMech)  && UtilValidate.isEmpty(shippingTelecomMech)) {
							PartyHelper.updateTelecomNumber(delegator, billingTelecomMech.getString("contactMechId"), phone);
							PartyHelper.createPartyContactMechPurpose(delegator, userLogin.getString("partyId"), billingTelecomMech.getString("contactMechId"), "PHONE_SHIPPING", false);
							shippingTelecomMech = billingTelecomMech;
						}
					}
					if(isDefaultBilling) {
						profileDefaultContactMechPurposeType = isShippingAlreadyDefault ? ContactMechPurposeType.BILLING_LOCATION_ONLY : ContactMechPurposeType.BILLING_LOCATION;
						profileDefaultContactMechId = billingContactMech.getString("contactMechId");
					}
					success = true;
					break;

				/**
				 * The user selected to save the given address as both Shipping address. When the same address is marked as both Shipping and Billing,
				 * only one Postal Address will be created in the postal_address table. The same address will then be marked as both Shipping and Billing address
				 * in the party_contact_mech_purpose table. When an existing Shipping or Billing address is removed, we won't delete any records in the backend DB,
				 * instead the thru_date will be set to current date in the party_contact_mech_purpose table. The same approach is used for the Telecom Number
				 * associated with the address as well. In order to discard a Telecom Number, we will set the thru_date to current date in party_contact_mech_purpose
				 * table.
				 */
				case SHIPPING_LOCATION:
					/**
					 * No address exists, so create a new Postal address and mark it as both Shipping address in party_contact_mech_purpose table.
					 * This scenario will occur only when a new (non-duplicate) address is added to the address book. Since no postal address exists, there won't
					 * be any Telecom Number either. So create a new Telecom Number and mark it as Shipping Number in party_contact_mech_purpose
					 * table as well.
					 */
					if(UtilValidate.isEmpty(shippingContactMech) && UtilValidate.isEmpty(billingContactMech)) {
						//Create a new Postal Address and mark it as SHIPPING_LOCATION in party_contact_mech_purpose table
						shippingContactMech = PartyHelper.createPostalAddress(delegator, partyId, address, "SHIPPING_LOCATION");

						//Create a new Telecom Number and mark it as PHONE_SHIPPING in party_contact_mech_purpose table
						shippingTelecomMech = PartyHelper.createTelecomNumber(delegator, userLogin.getString("partyId"), shippingContactMech.getString("contactMechId"), phone, "PHONE_SHIPPING");

					}
					/**
					 * A Postal address exists and is marked as Billing address in party_contact_mech_purpose table. Update the existing address(only in edit mode) and mark
					 * the existing Postal address as Shipping address in the party_contact_mech_purpose table and also (In edit mode) discard the Billing address by setting a thru_date in
					 * party_contact_mech_purpose table.If the existing Billing address has an associated Telecom Number, update the number and mark the existing Telecom Number as Shipping Number
					 * in party_contact_mech_purpose table and (in edit mode) discard the Billing Number by setting a thru_date party_contact_mech_purpose table. If the existing Billing
					 * address doesn't have an associated Telecom Number, and the given address has a phone number, create a new Telecom number and mark it as Shipping Number
					 *  in party_contact_mech_purpose table.
					 */
					else if(UtilValidate.isEmpty(shippingContactMech)) {
						/**
						 * If we are in edit mode, update the existing Postal Address.
						 */
						if(mode.equals("edit")) {
							//Update the Postal Address
							PartyHelper.updatePostalAddress(delegator, contactMechId, address);

							//Discard the existing Billing Address
							PartyHelper.discPartyContactMechPurpose(delegator, userLogin.getString("partyId"), billingContactMech.getString("contactMechId"), "BILLING_LOCATION");
							billingContactMech = null;

							// Discard the Billing number if exists
							if(UtilValidate.isNotEmpty(billingTelecomMech)) {
								//Discard the existing Billing Number
								PartyHelper.discPartyContactMechPurpose(delegator, userLogin.getString("partyId"), billingTelecomMech.getString("contactMechId"), "PHONE_BILLING");
								billingTelecomMech = null;
							}
						}

						//Mark the existing Postal address as Shipping address
						PartyHelper.createPartyContactMechPurpose(delegator, partyId, billingContactMech.getString("contactMechId"), "SHIPPING_LOCATION", false);
						shippingContactMech = billingContactMech;

						// If there is no Billing Telecom Number and the given address has a Telecom Number, create a new Telecom Number and mark it as Shipping Number
						if(UtilValidate.isEmpty(billingTelecomMech) && UtilValidate.isNotEmpty(phone)){
							shippingTelecomMech = PartyHelper.createTelecomNumber(delegator, userLogin.getString("partyId"), shippingContactMech.getString("contactMechId"), phone, "PHONE_SHIPPING");
						}

						//If there is a Billing Number, update the number and mark it as Shipping Number
						else if(UtilValidate.isNotEmpty(billingTelecomMech)) {
							PartyHelper.updateTelecomNumber(delegator, billingTelecomMech.getString("contactMechId"), phone);
							PartyHelper.createPartyContactMechPurpose(delegator, userLogin.getString("partyId"), billingTelecomMech.getString("contactMechId"), "PHONE_SHIPPING", false);
							shippingTelecomMech = billingTelecomMech;
						}
					}
					/**
					 * A Postal address exists and is marked as Shipping address in party_contact_mech_purpose table. Update the existing address(only in edit mode). If the existing Shipping
					 * address has an associated Telecom Number, update the Telecom Number. If the existing Shipping address doesn't have an associated Telecom Number, and the given address
					 * has a phone number, create a new Telecom number and mark it as Shipping Number in party_contact_mech_purpose table.
					 */
					else if(UtilValidate.isEmpty(billingContactMech)) {
						/**
						 * If we are in edit mode, update the existing Postal Address.
						 */
						if(mode.equals("edit")) {
							//Update the Postal Address
							PartyHelper.updatePostalAddress(delegator, contactMechId, address);
						}

						//If the existing Postal address is not marked as Shipping Address, mark it as Shipping Address
						if(shippingContactMech.get("purposeThruDate") != null) {
							PartyHelper.createPartyContactMechPurpose(delegator, partyId, shippingContactMech.getString("contactMechId"), "SHIPPING_LOCATION", false);
						}

						// If there is no Shipping Telecom Number and the given address has a Telecom Number, create a new Telecom Number and mark it as Shipping Number
						if(UtilValidate.isEmpty(shippingTelecomMech) && UtilValidate.isNotEmpty(phone)){
							shippingTelecomMech = PartyHelper.createTelecomNumber(delegator, userLogin.getString("partyId"), shippingContactMech.getString("contactMechId"), phone, "PHONE_SHIPPING");
						}

						//If there is a Shipping Number, update the number
						else if(UtilValidate.isNotEmpty(shippingTelecomMech)) {
							PartyHelper.updateTelecomNumber(delegator, shippingTelecomMech.getString("contactMechId"), phone);
						}
					}
					/**
					 * A Postal address exists and is marked as Shipping and Billing address in party_contact_mech_purpose table. Update the existing address(only in edit mode). If the existing Shipping
					 * and Billing addresses have an associated Telecom Number, update the existing number. If only one of the Shipping or Billing address has an associated Telecom Number, update the
					 * existing number and mark the existing Telecom Number as both Shipping and Billing number. If none of the Shipping or Billing address has an associated Telecom Number, and the
					 * given address has a phone number, create a new Telecom Number and mark it as both Shipping and Billing number. In edit mode, discard the Billing Address and Billing Telecom Number, if exists
					 */
					else {

						/**
						 * If we are in edit mode, update the existing Postal Address.
						 */
						if (mode.equals("edit")) {
							//Update the Postal Address
							PartyHelper.updatePostalAddress(delegator, contactMechId, address);

							//Discard the existing Billing Address
							PartyHelper.discPartyContactMechPurpose(delegator, userLogin.getString("partyId"), billingContactMech.getString("contactMechId"), "BILLING_LOCATION");

							// Discard the Billing number if exists
							if (UtilValidate.isNotEmpty(billingTelecomMech)) {
								//Discard the existing Billing Number
								PartyHelper.discPartyContactMechPurpose(delegator, userLogin.getString("partyId"), billingTelecomMech.getString("contactMechId"), "PHONE_BILLING");
							}
						}

						//If the existing Postal address is not marked as Shipping Address, mark it as Shipping Address
						if(shippingContactMech.get("purposeThruDate") != null) {
							PartyHelper.createPartyContactMechPurpose(delegator, partyId, shippingContactMech.getString("contactMechId"), "SHIPPING_LOCATION", false);
						}


						// If there is no Shipping and Billing Telecom Number and the given address has a Telecom Number, create a new Telecom Number and mark it as Shipping and Billing Number
						if (UtilValidate.isEmpty(shippingTelecomMech) && UtilValidate.isEmpty(billingTelecomMech) && UtilValidate.isNotEmpty(phone)) {
							billingTelecomMech = PartyHelper.createTelecomNumber(delegator, userLogin.getString("partyId"), billingContactMech.getString("contactMechId"), phone, "PHONE_BILLING");
							PartyHelper.createPartyContactMechPurpose(delegator, userLogin.getString("partyId"), billingTelecomMech.getString("contactMechId"), "PHONE_SHIPPING", false);
							shippingTelecomMech = billingTelecomMech;
						}

						//If there is a Shipping Number and no Billing number, update the number and mark it as Billing Number
						else if (UtilValidate.isNotEmpty(shippingTelecomMech)  && UtilValidate.isEmpty(billingTelecomMech)) {
							PartyHelper.updateTelecomNumber(delegator, shippingTelecomMech.getString("contactMechId"), phone);
							PartyHelper.createPartyContactMechPurpose(delegator, userLogin.getString("partyId"), shippingTelecomMech.getString("contactMechId"), "PHONE_BILLING", false);
							billingTelecomMech = shippingTelecomMech;
						}

						//If there is a Billing Number and no Shipping number, update the number and mark it as Shipping Number
						else if (UtilValidate.isNotEmpty(billingTelecomMech)  && UtilValidate.isEmpty(shippingTelecomMech)) {
							PartyHelper.updateTelecomNumber(delegator, billingTelecomMech.getString("contactMechId"), phone);
							PartyHelper.createPartyContactMechPurpose(delegator, userLogin.getString("partyId"), billingTelecomMech.getString("contactMechId"), "PHONE_SHIPPING", false);
							shippingTelecomMech = billingTelecomMech;
						}
					}

					if(isDefaultShipping) {
						profileDefaultContactMechPurposeType = isBillingAlreadyDefault ? ContactMechPurposeType.SHIPPING_LOCATION_ONLY : ContactMechPurposeType.SHIPPING_LOCATION;
						profileDefaultContactMechId = shippingContactMech.getString("contactMechId");
					}
					success = true;
					break;
			}

			if(profileDefaultContactMechPurposeType != null) {
				createPartyDefaultContactMechs(delegator, userLogin.getString("partyId"), profileDefaultContactMechId, profileDefaultContactMechPurposeType, null);
				addressMetadata.put("defaults", isDefaultShipping && isDefaultBilling ? "default-shipping default-billing" : isDefaultShipping ? "default-shipping" : isDefaultBilling ? "default-billing" : "");
			} else if(isBillingAlreadyDefault || isShippingAlreadyDefault) {
				createPartyDefaultContactMechs(delegator, userLogin.getString("partyId"), "", ContactMechPurposeType.SHIPPING_AND_BILLING_LOCATION, null);
				addressMetadata.put("defaults", "none");
			}


			if(UtilValidate.isNotEmpty(shippingContactMech)) {
				addressMetadata.put("contactMechId", shippingContactMech.getString("contactMechId"));
			} else if(UtilValidate.isNotEmpty(billingContactMech)) {
				addressMetadata.put("contactMechId", billingContactMech.getString("contactMechId"));
			}
			Map<String, String> addressData = new HashMap<>();
			addressData.putAll(address);
			addressData.putAll(phone);
			addressData.putAll(addressMetadata);
			addressData.put("jsonData", new Gson().toJson(addressData));
			jsonResponse.put("address", addressData);
		}
		jsonResponse.put("success", success);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static List<GenericValue> getAddressBooks(HttpServletRequest request, HttpServletResponse response) throws GenericEntityException {
		List<GenericValue> addressBooks = new ArrayList<>();
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue)request.getSession().getAttribute("userLogin");
		if(UtilValidate.isEmpty(userLogin)){
			return new ArrayList<>();
		}
		EntityListIterator eli = null;
		try {
			GenericValue party = userLogin.getRelatedOne("Party", true);
			String partyId = party.getString(PARTY_ID);

			List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
			conditionList.add(EntityCondition.makeCondition(PARTY_ID, EntityOperator.EQUALS, partyId));
			conditionList.add(EntityCondition.makeCondition(THRU_DATE, EntityOperator.EQUALS, null));
			conditionList.add(EntityCondition.makeCondition("name", EntityOperator.NOT_EQUAL, null));

			DynamicViewEntity dve = new DynamicViewEntity();
			dve.addMemberEntity("ES", "EnvSession");
			dve.addAlias("ES", PARTY_ID);
			dve.addAlias("ES", "sessionId");
			dve.addMemberEntity("VDG", "VariableDataGroup");
			dve.addAlias("VDG", "name");
			dve.addAlias("VDG", "variableDataGroupId");
			dve.addAlias("VDG", "createdStamp");
			dve.addAlias("VDG", "sessionId");
			dve.addAlias("VDG", THRU_DATE);
			dve.addViewLink("ES", "VDG", false, ModelKeyMap.makeKeyMapList("sessionId", "sessionId"));
			dve.addMemberEntity("VD", "VariableData");
			dve.addAlias("VD", "variableDataCount", "variableDataId", null, null, null, "count");
			dve.setGroupBy(UtilMisc.<String>toList("variableDataGroupId"));
			dve.addViewLink("VDG", "VD", false, ModelKeyMap.makeKeyMapList("variableDataGroupId", "variableDataGroupId"));

			eli = EntityQuery.use(delegator).select("variableDataGroupId", "name", "createdStamp", "variableDataCount").from(dve).where(EntityCondition.makeCondition(conditionList)).queryIterator();
			GenericValue addressBook = null;
			while((addressBook = eli.next()) != null) {
				addressBooks.add(addressBook);
			}
		} catch(GenericEntityException e) {
			Debug.logError(e, module);
		} finally {
			if(eli != null) {
				eli.close();
			}
		}

		return addressBooks;
	}

	public static Map<String, Object> getAddressBookWithAddresses(HttpServletRequest request, HttpServletResponse response) throws GenericEntityException {
		Map<String, Object> addressBook = new HashMap<>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue)request.getSession().getAttribute("userLogin");
		String variableDataGroupId = (String) context.get("variableDataGroupId");
		if(UtilValidate.isEmpty(userLogin) || UtilValidate.isEmpty(variableDataGroupId)){
			return new HashMap<>();
		}
		try {
			GenericValue party = userLogin.getRelatedOne("Party", true);
			String partyId = party.getString(PARTY_ID);

			List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
			conditionList.add(EntityCondition.makeCondition(PARTY_ID, EntityOperator.EQUALS, partyId));
			conditionList.add(EntityCondition.makeCondition("variableDataGroupId", EntityOperator.EQUALS, variableDataGroupId));
			conditionList.add(EntityCondition.makeCondition(THRU_DATE, EntityOperator.EQUALS, null));

			DynamicViewEntity dve = new DynamicViewEntity();
			dve.addMemberEntity("ES", "EnvSession");
			dve.addAlias("ES", PARTY_ID);
			dve.addAlias("ES", "sessionId");
			dve.addMemberEntity("VDG", "VariableDataGroup");
			dve.addAlias("VDG", "name");
			dve.addAlias("VDG", "variableDataGroupId");
			dve.addAlias("VDG", "attributes");
			dve.addAlias("VDG", "sessionId");
			dve.addAlias("VDG", THRU_DATE);
			dve.addViewLink("ES", "VDG", false, ModelKeyMap.makeKeyMapList("sessionId", "sessionId"));
			dve.addMemberEntity("VD", "VariableData");
			dve.addAlias("VD", "variableDataCount", "variableDataId", null, null, null, "count");
			dve.setGroupBy(UtilMisc.<String>toList("variableDataGroupId"));
			dve.addViewLink("VDG", "VD", false, ModelKeyMap.makeKeyMapList("variableDataGroupId", "variableDataGroupId"));

			List<GenericValue> addressBookGV = EntityQuery.use(delegator).select("variableDataGroupId", "name", "attributes").from(dve).where(EntityCondition.makeCondition(conditionList)).queryList();
			if(UtilValidate.isEmpty(addressBookGV)) {
				return new HashMap<>();
			} else {
				String[] attributes = new Gson().fromJson((String)addressBookGV.get(0).get("attributes"), String[].class);

				addressBook.put("customerAddressGroupId", addressBookGV.get(0).getString("variableDataGroupId"));
				addressBook.put("name", addressBookGV.get(0).getString("name"));
				addressBook.put("attributes", attributes);

				List<Map<String, Object>> _addresses = new ArrayList<>();
				List<GenericValue> addresses = EntityQuery.use(delegator).select("variableDataId", "variableDataGroupId", "data").from("VariableData").where(UtilMisc.toMap("variableDataGroupId", variableDataGroupId)).orderBy("data").queryList();
				for (GenericValue addressGV : addresses) {
					Map<String, Object> address = new HashMap<>();
					address.put("customerAddressId", addressGV.get("variableDataId"));
					address.put("customerAddressGroupId", addressGV.get("variableDataGroupId"));
					String[] data = new Gson().fromJson((String)addressGV.get("data"), String[].class);
					for(int i = 0; i < data.length; i ++) {
						if(attributes[i].equals("Name Line 1")) {
							address.put("name", data[i]);
						} else if(attributes[i].equals("Name Line 2")) {
							address.put("name2", data[i]);
						} else if(attributes[i].equals("Address Line 1")) {
							address.put("address1", data[i]);
						} else if(attributes[i].equals("Address Line 2")) {
							address.put("address2", data[i]);
						} else if(attributes[i].equals("City")) {
							address.put("city", data[i]);
						} else if(attributes[i].equals("State")) {
							address.put("state", data[i]);
						} else if(attributes[i].equals("Zip")) {
							address.put("zip", data[i]);
						} else if(attributes[i].equals("Country")) {
							address.put("country", data[i]);
						}

					}
					Map<String, Object> _address = new HashMap<>(address);
					_address.put("jsonData", new Gson().toJson(address));
					_addresses.add(_address);
				}

				addressBook.put("addresses", _addresses);
			}
		} catch(GenericEntityException e) {
			Debug.logError(e, module);
		}

		return addressBook;
	}

	public static Map<String, Map<String, String>> getAddresses(HttpServletRequest request, HttpServletResponse response) throws GenericEntityException {
		Map<String, Map<String, String>> addresses = new LinkedHashMap<>();
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue)request.getSession().getAttribute("userLogin");
		if(UtilValidate.isEmpty(userLogin)){
			return null;
		}
		GenericValue party = userLogin.getRelatedOne("Party", true);
		String partyId = party.getString(PARTY_ID);

		List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
		conditionList.add(EntityCondition.makeCondition(PARTY_ID, EntityOperator.EQUALS, partyId));
		conditionList.add(EntityCondition.makeCondition("PCMPThruDate", EntityOperator.EQUALS, null));
		conditionList.add(EntityCondition.makeCondition("PCMThruDate", EntityOperator.EQUALS, null));
		conditionList.add(EntityCondition.makeCondition(CONTACT_MECH_PURPOSE_TYPE_ID, EntityOperator.IN, Arrays.asList(BILLING_LOCATION, SHIPPING_LOCATION)));

		List<String> orderBy = new ArrayList<>();
		orderBy.add(TO_NAME);
		orderBy.add(ADDRESS1);

		DynamicViewEntity dve = new DynamicViewEntity();
		dve.addMemberEntity("PCMP", "PartyContactMechPurpose");
		dve.addAlias("PCMP", PARTY_ID);
		dve.addAlias("PCMP", CONTACT_MECH_PURPOSE_TYPE_ID);
		dve.addAlias("PCMP", "PCMPThruDate", THRU_DATE, null, null, null, null);
		dve.addMemberEntity("PPD", "PartyProfileDefault");
		dve.addAlias("PPD", "defaultShipAddr");
		dve.addAlias("PPD", "defaultBillAddr");
		dve.addViewLink("PCMP", "PPD", true, ModelKeyMap.makeKeyMapList(PARTY_ID, PARTY_ID));
		dve.addMemberEntity("PCM", "PartyContactMech");
		dve.addViewLink("PCMP", "PCM", true, ModelKeyMap.makeKeyMapList(CONTACT_MECH_ID, CONTACT_MECH_ID));
		dve.addAlias("PCM", "PCMThruDate", THRU_DATE, null, null, null, null);
		dve.addMemberEntity("PA", "PostalAddress");
		dve.addViewLink("PCMP", "PA", true, ModelKeyMap.makeKeyMapList(CONTACT_MECH_ID, CONTACT_MECH_ID));
		dve.addAliasAll("PA", "", null);

		EntityListIterator eli = null;
		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try {
				eli = delegator.findListIteratorByCondition(dve, EntityCondition.makeCondition(conditionList), null, null, orderBy, null);
				GenericValue contact = null;
				while((contact = eli.next()) != null) {
					String contactMechId = contact.getString(CONTACT_MECH_ID);
					List attributes = delegator.findByAnd("ContactMechAttribute", UtilMisc.toMap(ATTRIBUTE_NAME, "telecomNumber", CONTACT_MECH_ID, contact.getString(CONTACT_MECH_ID)), null, true);
					String[] names = splitAtFirstSpace(contact.getString(TO_NAME));
					Map<String, String> addressMap = new HashMap<>();
					addressMap.put(CONTACT_MECH_ID, contactMechId);
					addressMap.put(CONTACT_MECH_PURPOSE_TYPE_ID, contact.getString(CONTACT_MECH_PURPOSE_TYPE_ID));
					if(contact.getString(CONTACT_MECH_PURPOSE_TYPE_ID).equals(SHIPPING_LOCATION)) {
						boolean defaultShipping = UtilValidate.isNotEmpty(contact.getString("defaultShipAddr")) && contact.getString("defaultShipAddr").equals(contact.getString(CONTACT_MECH_ID));
						addressMap.put(DEFAULT_SHIPPING, Boolean.toString(defaultShipping));
						addressMap.put(DEFAULT_BILLING, "false");
						addressMap.put(DEFAULTS, defaultShipping ? "default-shipping" : "");
					} else if(contact.getString(CONTACT_MECH_PURPOSE_TYPE_ID).equals(BILLING_LOCATION)) {
						boolean defaultBilling = UtilValidate.isNotEmpty(contact.getString("defaultBillAddr")) && contact.getString("defaultBillAddr").equals(contact.getString(CONTACT_MECH_ID));
						addressMap.put(DEFAULT_BILLING, Boolean.toString(defaultBilling));
						addressMap.put(DEFAULT_SHIPPING, "false");
						addressMap.put(DEFAULTS, defaultBilling ? "default-billing" : "");
					}
					addressMap.put(FIRST_NAME, names[0]);
					addressMap.put(LAST_NAME, names[1]);
					addressMap.put(COMPANY_NAME, UtilValidate.isEmpty(contact.getString(COMPANY_NAME)) ? EMPTY_STRING : contact.getString(COMPANY_NAME));
					addressMap.put(ADDRESS1, UtilValidate.isEmpty(contact.getString(ADDRESS1)) ? EMPTY_STRING : contact.getString(ADDRESS1));
					addressMap.put(ADDRESS2, UtilValidate.isEmpty(contact.getString(ADDRESS2)) ? EMPTY_STRING : contact.getString(ADDRESS2));
//                    addressMap.put(ADDRESS3, UtilValidate.isEmpty(contact.getString(ADDRESS3)) ? EMPTY_STRING : contact.getString(ADDRESS3));
					addressMap.put(CITY, UtilValidate.isEmpty(contact.getString(CITY)) ? EMPTY_STRING : contact.getString(CITY));
					addressMap.put(STATE_PROVINCE, UtilValidate.isEmpty(contact.getString(STATE_PROVINCE)) ? EMPTY_STRING : contact.getString(STATE_PROVINCE));
					addressMap.put(POSTAL_CODE, UtilValidate.isEmpty(contact.getString(POSTAL_CODE)) ? EMPTY_STRING : contact.getString(POSTAL_CODE));
					addressMap.put(COUNTRY, UtilValidate.isEmpty(contact.getString(COUNTRY)) ? EMPTY_STRING : contact.getString(COUNTRY));

					if(attributes != null && attributes.size() > 0) {
						GenericValue telecomNumber = delegator.findOne("TelecomNumber", UtilMisc.toMap(CONTACT_MECH_ID, ((GenericValue) attributes.get(0)).getString(ATTRIBUTE_VALUE)), true);
						String phoneNumber = telecomNumber != null ? telecomNumber.getString(CONTACT_NUMBER) : EMPTY_STRING;
						addressMap.put(CONTACT_NUMBER, UtilValidate.isEmpty(phoneNumber) ? EMPTY_STRING : phoneNumber);
					}

					if (!addressMap.containsKey(CONTACT_NUMBER)) {
						addressMap.put(CONTACT_NUMBER, EMPTY_STRING);
					}
					/**
					 * If the same address is mapped as both SHIPPING_LOCATION and BILLING_LOCATION, treat it as a single address with a virtual CONTACT_MECH_PURPOSE_TYPE_ID 'SHIPPING_AND_BILLING_LOCATION'.
					 */
					if(addresses.containsKey(contactMechId)) {
						String existingContactMechPurposeTypeId = addresses.get(contactMechId).get(CONTACT_MECH_PURPOSE_TYPE_ID);
						String contactMechPurposeTypeId = addressMap.get(CONTACT_MECH_PURPOSE_TYPE_ID);
						if(!existingContactMechPurposeTypeId.equals(SHIPPING_AND_BILLING_LOCATION) && !existingContactMechPurposeTypeId.equals(contactMechPurposeTypeId)) {
							addressMap.put(CONTACT_MECH_PURPOSE_TYPE_ID, ContactMechPurposeType.SHIPPING_AND_BILLING_LOCATION.name());

							boolean defaultShipping = UtilValidate.isNotEmpty(contact.getString("defaultShipAddr")) && contact.getString("defaultShipAddr").equals(contact.getString(CONTACT_MECH_ID));
							addressMap.put(DEFAULT_SHIPPING, Boolean.toString(defaultShipping));

							boolean defaultBilling = UtilValidate.isNotEmpty(contact.getString("defaultBillAddr")) && contact.getString("defaultBillAddr").equals(contact.getString(CONTACT_MECH_ID));
							addressMap.put(DEFAULT_BILLING, Boolean.toString(defaultBilling));

							addressMap.put(DEFAULTS, defaultShipping && defaultBilling ? "default-shipping default-billing" : defaultShipping ? "default-shipping" : defaultBilling ? "default-billing" : "");

							addressMap.put("jsonData", new Gson().toJson(addressMap));
							addresses.put(contactMechId, addressMap);
						}
					} else {
						addressMap.put("jsonData", new Gson().toJson(addressMap));
						addresses.put(contactMechId, addressMap);
					}

				}
			} catch (GenericEntityException e1) {
				TransactionUtil.rollback(beganTransaction, "Error looking up shipping and billing addresses", e1);
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
		return addresses;
	}

	public static String[] splitAtFirstSpace(String string) {
		if(string == null) {
			string = EMPTY_STRING;
		} else {
			string = string.trim();
		}
		int index = string.indexOf(" ");
		if(index == -1) {
			return new String[] { string, EMPTY_STRING };
		} else {
			return new String[] {string.substring(0, index), string.substring(index + 1)};
		}
	}

	/*
	 * Get a full list of users
	*/
	public static String getUserList(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		ArrayList<Map> userList = new ArrayList<Map>();
		jsonResponse.put("success", true);

		if(UtilValidate.isNotEmpty(userLogin)) {
			boolean beganTransaction = false;

			EntityListIterator eli = null;
			GenericValue userData = null;
			try {
				try {
					beganTransaction = TransactionUtil.begin();

					List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
					EntityFindOptions efo = new EntityFindOptions();

					eli = delegator.find("UserLogin", null, null, null, null, efo);

					String currentOrderId = "";
					Map<String, Object> userInfo = null;
					while((userData = eli.next()) != null) {
						userInfo = new HashMap<String, Object>();
						userInfo.put("userLoginId", userData.get("userLoginId"));
						userInfo.put("currentPassword", userData.get("currentPassword"));
						userInfo.put("passwordHint", userData.get("passwordHint"));
						userInfo.put("partyId", userData.get("partyId"));
						userInfo.put("createdStamp", userData.get("createdStamp"));
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

	protected static String generateSecurityToken() {
		return new BigInteger(130, random).toString(32);
	}

	protected static void resetPassword(GenericValue userLogin, String newPassword) throws GenericEntityException {
		if(userLogin != null) {
			userLogin.put("currentPassword", HashCrypt.cryptUTF8(EnvUtil.getHashType(), null, newPassword));
			userLogin.set("passwordHint", "Forgot Password Reset");
			userLogin.set("enabled", "Y");
			userLogin.set("successiveFailedLogins", 0L);
			userLogin.store();
			userLogin.refresh();
		}
	}

	protected static Map<String, Object> updatePassword(HttpServletRequest request, GenericValue userLogin, Map<String, Object> jsonResponse, boolean byPassAuth) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> resultPasswordChange = null;
		try {
			Map<String, Object> inMap = UtilMisc.<String, Object>toMap("login.username", request.getParameter("USERNAME"), "login.password", request.getParameter("PASSWORD"), "locale", UtilHttp.getLocale(request));
			inMap.put("userLoginId", request.getParameter("USERNAME"));
			inMap.put("currentPassword", request.getParameter("CURRENT_PASSWORD"));
			inMap.put("newPassword", request.getParameter("PASSWORD"));
			inMap.put("newPasswordVerify", request.getParameter("PASSWORD_CONFIRM"));
			inMap.put("userLogin", (byPassAuth) ? EnvUtil.getSystemUser(delegator) : userLogin);
			resultPasswordChange = dispatcher.runSync("updatePassword", inMap);
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error calling updatePassword service", module);
			Map<String, String> messageMap = UtilMisc.toMap("errorMessage", e.getMessage());
			String errMsg = UtilProperties.getMessage(resourceWebapp, "loginevents.following_error_occurred_during_login", messageMap, UtilHttp.getLocale(request));
			jsonResponse.put("error", errMsg);
			jsonResponse.put("success", false);
		}

		if(ServiceUtil.isError(resultPasswordChange)) {
			String errorMessage = (String) resultPasswordChange.get(ModelService.ERROR_MESSAGE);
			if(UtilValidate.isNotEmpty(errorMessage)) {
				Map<String, String> messageMap = UtilMisc.toMap("errorMessage", errorMessage);
				String errMsg = UtilProperties.getMessage(resourceWebapp, "loginevents.following_error_occurred_during_login", messageMap, UtilHttp.getLocale(request));
				jsonResponse.put("error", errMsg);
				jsonResponse.put("success", false);
			}
		} else {
			try {
				userLogin.refresh();
			} catch (GenericEntityException e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "Error refreshing userLogin value", module);
				Map<String, String> messageMap = UtilMisc.toMap("errorMessage", e.getMessage());
				String errMsg = UtilProperties.getMessage(resourceWebapp, "loginevents.following_error_occurred_during_login", messageMap, UtilHttp.getLocale(request));
				jsonResponse.put("error", errMsg);
				jsonResponse.put("success", false);
			}
		}

		return jsonResponse;
	}

	/*
	 * Get external login for party
	 */
	public static List<GenericValue> getExternalUserLogin(Delegator delegator, String partyId) throws GenericEntityException {
		if(UtilValidate.isEmpty(partyId)) {
			return null;
		}

		return delegator.findByAnd("ExternalUserLogin", UtilMisc.toMap("partyId", partyId), null, false);
	}

	public static void setResellerId(Delegator delegator, String partyId, String resellerId) throws GenericEntityException {
		GenericValue party = getParty(delegator, partyId);
		party.set("resellerId", resellerId);
		party.store();
	}

	public static GenericValue getParty(Delegator delegator, String partyId) throws GenericEntityException {
		return EntityQuery.use(delegator).from("Party").where("partyId", partyId).queryOne();
	}
	public static GenericValue getPerson(Delegator delegator, String partyId) throws GenericEntityException {
		return EntityQuery.use(delegator).from("Person").where("partyId", partyId).queryOne();
	}

	public static GenericValue updatePerson(Delegator delegator, String partyId, Map<String, Object> data) throws GenericEntityException {
		GenericValue person = getPerson(delegator, partyId);
		person = EnvUtil.insertGenericValueData(delegator, person, data);
		delegator.store(person);

		return person;
	}

	public static GenericValue setSalesRep(Delegator delegator, String partyId, GenericValue party, String salesRep, boolean update) throws GenericEntityException {
		if(party == null) {
			party = getParty(delegator, partyId);
		}
		if(update || UtilValidate.isEmpty(party.getString("salesRep"))) {
			party = EnvUtil.insertGenericValueData(delegator, party, UtilMisc.<String, Object>toMap("salesRep", salesRep));
			delegator.store(party);
		}

		return party;
	}

	public static Map<String, Object> getSalesRepInfo(Delegator delegator, String partyId) throws GenericEntityException {
		Map<String, Object> salesRepInfo = new HashMap<>();
		
		GenericValue party = EntityQuery.use(delegator).from("Party").where("partyId", partyId).queryOne();
		String salesRepEmail = UtilValidate.isNotEmpty(party.getString("salesRep")) ? party.getString("salesRep") : "production@folders.com";

		salesRepInfo.put("email", salesRepEmail);
		salesRepInfo.put("userInfo", (Map<String, Object>) QuoteHelper.users.get(salesRepEmail));

		return salesRepInfo;
	}
}