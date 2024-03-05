/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.iparcel;

import java.io.*;
import java.net.*;
import java.lang.*;
import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.envelopes.util.*;
import com.envelopes.party.PartyHelper;
import com.envelopes.cart.CartHelper;

import com.mes.sdk.core.Http;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.order.shoppingcart.ShoppingCart;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartEvents;
import org.apache.ofbiz.service.LocalDispatcher;

public class IPCancelRequest {
	public static final String module = IPCancelRequest.class.getName();

	protected Map<String, Object> requestObj = null;
	protected LocalDispatcher dispatcher = null;
	protected Delegator delegator = null;
	protected Map<String, Object> context = null;

	/**
	 * IPSetCheckoutRequest Constructor
	 * @param request
	 */
	public IPCancelRequest(HttpServletRequest request) {
		this.dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		this.delegator = (Delegator) request.getAttribute("delegator");
		this.context = EnvUtil.getParameterMap(request);

		this.requestObj = new HashMap<>();
	}

	public Map<String, Object> getRequest() {
		return this.requestObj;
	}

	public String getRequestAsJSON() {
		Gson gson = new GsonBuilder().serializeNulls().create();
		return gson.toJson(this.requestObj);
	}

	/**
	 * Set the actual request contents of IPCancelRequest constructor
	 * @param request
	 */
	protected void createRequest(HttpServletRequest request) {
		if(request != null) {
			this.requestObj.put("key", IParcelEvents.API_KEY);
			this.requestObj.put("tx", this.context.get("tx"));
		}
	}

	protected void createRequest(String transactionId) {
		this.requestObj.put("key", IParcelEvents.API_KEY);
		this.requestObj.put("tx", transactionId);
	}
}