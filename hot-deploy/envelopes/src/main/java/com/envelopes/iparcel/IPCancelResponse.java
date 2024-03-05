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

import com.google.gson.JsonSyntaxException;
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

public class IPCancelResponse {
	public static final String module = IPCancelResponse.class.getName();

	protected Map<String, Object> responseObj = null;

	/**
	 * IPCancelResponse Constructor
	 */
	public IPCancelResponse() {
		this.responseObj = new HashMap<>();
	}

	public Map<String, Object> getRequest() {
		return this.responseObj;
	}

	public String getRequestAsJSON() {
		Gson gson = new GsonBuilder().serializeNulls().create();
		return gson.toJson(this.responseObj);
	}

	/**
	 * Set the response value
	 * @param response
	 */
	public void setResponse(String response) {
		try {
			if(UtilValidate.isNotEmpty(response)) {
				this.responseObj = new Gson().fromJson(response, HashMap.class);
			}
		} catch(JsonSyntaxException e) {
			EnvUtil.reportError(e);
			this.responseObj = null;
		}
	}

	/**
	 * See if the response is valid
	 * @return
	 */
	public boolean isResponseValid() {
		if(UtilValidate.isNotEmpty(this.responseObj) && UtilValidate.isNotEmpty(this.responseObj.get("status"))) {
			return true;
		}

		return false;
	}

	/**
	 * See if the response is valid
	 * @return
	 */
	public boolean isSuccess() {
		if(isResponseValid()) {
			return "OK".equalsIgnoreCase((String) this.responseObj.get("status"));
		}

		return false;
	}
}