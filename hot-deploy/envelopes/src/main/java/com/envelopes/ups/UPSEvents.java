/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.ups;

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

import org.apache.commons.io.IOUtils;
import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.envelopes.util.*;

public class UPSEvents {
	public static final String module = UPSEvents.class.getName();

	/*
	 * UPS Shipping Label
	 */
	public static String generateUPSLabel(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher)request.getAttribute("dispatcher");
		Delegator delegator = (Delegator)request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		response.setContentType(EnvConstantsUtil.RESPONSE_GIF);

		OutputStream output = null;

		Map<String, String> fromAddress = new HashMap<String, String>();
		fromAddress.put("toName", "Envelopes.com");
		fromAddress.put("attnName", "Farjana Hannan");
		fromAddress.put("address1", "83-30 118th St");
		fromAddress.put("address2", "Apt 6B");
		fromAddress.put("city", "Kew Gardens");
		fromAddress.put("postalCode", "11415");
		fromAddress.put("stateProvinceGeoId", "NY");
		fromAddress.put("countryGeoId", "US");
		fromAddress.put("phone", "5555555555");

		String base64Label = null;
		try {
			base64Label = UPSHelper.getUPSReturnShipLabel(fromAddress, null, null, null);
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to get label.", module);
		}

		try {
			output = response.getOutputStream();
			byte[] decodedImage = Base64.decodeBase64(base64Label);
			output.write(decodedImage);
			output.flush();
		} catch (IOException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error trying to display tracking label. " + e + " : " + e.getMessage(), module);
			return "error";
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error trying to display tracking label. " + e + " : " + e.getMessage(), module);
			return "error";
		} finally {
			if(output != null) {
				try {
					output.close();
				} catch (Exception fe) {
					return "error";
				}
			}
		}

		return "success";
	}

	/*
	 * UPS Shipping Label
	 */
	public static String getUPSTrackingData(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		jsonResponse = UPSHelper.getTrackingData((String) context.get("trackingNumber"));
		jsonResponse.put("success", UtilValidate.isNotEmpty(jsonResponse));
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}
}