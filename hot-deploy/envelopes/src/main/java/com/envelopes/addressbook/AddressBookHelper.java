/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.addressbook;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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

import org.apache.ofbiz.base.crypto.HashCrypt;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.GeneralException;
import org.apache.ofbiz.base.util.StringUtil;
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
import org.apache.ofbiz.entity.model.DynamicViewEntity;
import org.apache.ofbiz.entity.model.ModelKeyMap;
import org.apache.ofbiz.entity.transaction.GenericTransactionException;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.entity.util.*;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.envelopes.util.*;

public class AddressBookHelper {
	public static final String module = AddressBookHelper.class.getName();

	/*
	 * Create a new empty addressbook
	 */
	public static String createAddressBook(HttpServletRequest request, Delegator delegator, String partyId) throws GenericEntityException {
		//if the partyId is "Company" we need to store a session variable
		//this variable will always be checked after login because we need to associate the uploaded artwork to the new partyid of the user
		if(request != null) {
			if(partyId.equals("Company")) {
				request.getSession().setAttribute("scene7PartyId", "Company");
			} else {
				request.getSession().removeAttribute("scene7PartyId");
			}
		}

		String addressGroupId = delegator.getNextSeqId("CustomerAddressGroup");
		Map<String, Object> addressBook = UtilMisc.<String, Object>toMap("customerAddressGroupId", addressGroupId);
		addressBook.put("name", "New Address Book (" + UtilDateTime.toDateTimeString(UtilDateTime.nowTimestamp()) + ")");
		addressBook.put("partyId", partyId);
		addressBook.put("sessionId", (request != null) ? request.getSession().getId() : "");
		addressBook.put("fromDate", UtilDateTime.nowTimestamp());

		GenericValue addressBookGV = delegator.makeValue("CustomerAddressGroup", addressBook);
		delegator.create(addressBookGV);

		return addressGroupId;
	}

	/*
	 * Update an addressbook
	 */
	public static boolean updateAddressBook(Delegator delegator, String addressGroupId, String value, String partyId, String sessionId) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(addressGroupId)) {
			GenericValue addressGroup = delegator.findOne("CustomerAddressGroup", UtilMisc.toMap("customerAddressGroupId", addressGroupId), false);
			if(addressGroup != null && EnvUtil.isOwner(partyId, sessionId, addressGroup, null, null)) {
				addressGroup.set("name", value);
				addressGroup.store();
				return true;
			}
		}

		return false;
	}

	/*
	 * Delete an addressbook
	 */
	public static boolean removeAddressBook(Delegator delegator, String addressGroupId, String partyId, String sessionId) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(addressGroupId)) {
			GenericValue addressGroup = delegator.findOne("CustomerAddressGroup", UtilMisc.toMap("customerAddressGroupId", addressGroupId), false);
			if(addressGroup != null && EnvUtil.isOwner(partyId, sessionId, addressGroup, null, null)) {
				addressGroup.set("thruDate", UtilDateTime.nowTimestamp());
				addressGroup.store();
				return true;
			}
		}

		return false;
	}

	/*
	 * Create a new empty address
	 */
	public static String addAddress(Delegator delegator, String addressGroupId, String partyId, String sessionId) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(addressGroupId)) {
			GenericValue addressGroup = delegator.findOne("CustomerAddressGroup", UtilMisc.toMap("customerAddressGroupId", addressGroupId), false);
			if(addressGroup != null && EnvUtil.isOwner(partyId, sessionId, addressGroup, null, null)) {
				String addressId = delegator.getNextSeqId("CustomerAddress");
				Map<String, Object> address = UtilMisc.<String, Object>toMap("customerAddressGroupId", addressGroupId, "customerAddressId", addressId);
				GenericValue addressGV = delegator.makeValue("CustomerAddress", address);
				delegator.create(addressGV);
				return addressId;
			}
		}

		return null;
	}

	/*
	 * Remove an address
	 */
	public static boolean removeAddress(Delegator delegator, String addressId, String partyId, String sessionId) throws GenericEntityException {
		boolean success = false;
		if(UtilValidate.isNotEmpty(addressId)) {
			GenericValue variableData = delegator.findOne("VariableData", UtilMisc.toMap("variableDataId", addressId), false);
			if(variableData != null) {
				List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
				conditionList.add(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId));
				conditionList.add(EntityCondition.makeCondition("variableDataGroupId", EntityOperator.EQUALS, variableData.get("variableDataGroupId")));

				DynamicViewEntity dve = new DynamicViewEntity();
				dve.addMemberEntity("ES", "EnvSession");
				dve.addAlias("ES", "partyId");
				dve.addAlias("ES", "sessionId");
				dve.addMemberEntity("VDG", "VariableDataGroup");
				dve.addAlias("VDG", "variableDataGroupId");
				dve.addAlias("VDG", "sessionId");
				dve.addViewLink("ES", "VDG", false, ModelKeyMap.makeKeyMapList("sessionId", "sessionId"));

				List<GenericValue> variableDataGroups = EntityQuery.use(delegator).select("variableDataGroupId", "partyId").from(dve).where(EntityCondition.makeCondition(conditionList)).queryList();

				if(UtilValidate.isNotEmpty(variableDataGroups) && variableDataGroups.get(0) != null && EnvUtil.isOwner(partyId, null, variableDataGroups.get(0), null, null)) {
					delegator.removeValue(variableData);
					success = true;
				}
			}
		}

		return success;
	}

	/*
	 * Update address entry
	 */
	public static boolean updateAddress(Delegator delegator, String addressId, String field, String value, String partyId, String sessionId) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(addressId)) {
			GenericValue address = delegator.findOne("CustomerAddress", UtilMisc.toMap("customerAddressId", addressId), false);
			if(address != null) {
				GenericValue addressGroup = delegator.findOne("CustomerAddressGroup", UtilMisc.toMap("customerAddressGroupId", address.getString("customerAddressGroupId")), false);
				if(addressGroup != null && EnvUtil.isOwner(partyId, sessionId, addressGroup, null, null)) {
					address.set(field, value);
					address.store();
					return true;
				}
			}
		}

		return false;
	}
}