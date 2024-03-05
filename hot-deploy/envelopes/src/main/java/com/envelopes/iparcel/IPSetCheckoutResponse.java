/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.iparcel;

import java.lang.*;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import com.envelopes.util.*;
import org.apache.ofbiz.base.util.UtilValidate;

public class IPSetCheckoutResponse {
	public static final String module = IPSetCheckoutResponse.class.getName();

	protected HashMap<String, Object> responseObj = null;

	/**
	 * Empty IPSetCheckoutResponse constructor
	 */
	public IPSetCheckoutResponse() {
		this.responseObj = new HashMap<>();
	}

	/**
	 * IPSetCheckoutResponse constructor
	 * @param response
	 */
	public IPSetCheckoutResponse(String response) {
		setResponse(response);
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
	 * Get the transaction id
	 * @return
	 */
	public String getTransactionId() {
		if(UtilValidate.isNotEmpty(this.responseObj)) {
			return (String) this.responseObj.get("tx");
		}

		return null;
	}

	/**
	 * See if the response is valid
	 * @return
	 */
	public boolean isResponseValid() {
		if(UtilValidate.isNotEmpty(this.responseObj) && UtilValidate.isNotEmpty(this.responseObj.get("tx"))) {
			return true;
		}

		return false;
	}

	public String getIParcelCheckoutPage() {
		if(isResponseValid()) {
			return IParcelEvents.CART_URL + "?key= + " + IParcelEvents.API_KEY + "&tx=" + getTransactionId();
		}

		return null;
	}
}