/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.iparcel;

import java.lang.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.envelopes.http.HTTPHelper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import com.envelopes.util.*;

import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityUtil;

public class IPGetCheckoutDetails {
	public static final String module = IPGetCheckoutDetails.class.getName();

	protected Map<String, Object> requestObj = null;
	protected HashMap<String, Object> responseObj = null;
	protected Delegator delegator = null;
	protected Map<String, Object> context = null;

	/**
	 * IPGetCheckoutDetails constructor
	 */
	public IPGetCheckoutDetails(HttpServletRequest request)  {
		this.delegator = (Delegator) request.getAttribute("delegator");
		this.context = EnvUtil.getParameterMap(request);

		this.requestObj = new HashMap<>();
		this.requestObj.put("key", IParcelEvents.API_KEY);
		this.requestObj.put("tx", (String) context.get("tx"));
	}

	/**
	 * Send the request and get the response
	 * @throws Exception
	 */
	public void sendRequest() throws Exception {
		String result = HTTPHelper.getURL(IParcelEvents.CHECKOUT_DETAILS, "POST", null, null, getRequest(), null, false, EnvConstantsUtil.RESPONSE_JSON, EnvConstantsUtil.RESPONSE_JSON);
		setResponse(result);
	}

	/**
	 * Return the request object
	 * @return
	 */
	public Map<String, Object> getRequest() {
		return this.requestObj;
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
		if(UtilValidate.isNotEmpty(this.responseObj) && UtilValidate.isNotEmpty(this.responseObj.get("trackingnumber"))) {
			return true;
		}

		return false;
	}

	/**
	 * Get the transaction id
	 * @return
	 */
	public String getTransactionId() {
		if(!isResponseValid()) {
			return null;
		}

		return (String) this.requestObj.get("tx");
	}

	/**
	 * Is transaction a success
	 * @return
	 */
	public boolean isSuccess() {
		return "SUCCESS".equalsIgnoreCase((String) this.responseObj.get("status"));
	}

	/**
	 * Get the used currency code
	 * @return
	 */
	public String getCurrency() {
		return (String) this.responseObj.get("page_currency");
	}

	/**
	 * Get the tracking number
	 * @return
	 */
	public String getTrackingNumber() {
		if(!isResponseValid()) {
			return null;
		}

		return (String) this.responseObj.get("trackingNumber");
	}

	/**
	 * Get the shipping address map
	 * @return
	 */
	public Map<String, String> getShippingAddress() throws GenericEntityException {
		Map<String, String> address = new HashMap<>();

		Map<String, Object> shippingAddress = (Map<String, Object>) ((Map<String, Object>) this.responseObj.get("AddressInfo")).get("Shipping");
		address.put("shipping_firstName", (String) shippingAddress.get("shipping_first_name"));
		address.put("shipping_lastName", (String) shippingAddress.get("shipping_last_name"));
		address.put("shipping_address1", (String) shippingAddress.get("shipping_address1"));
		address.put("shipping_address2", (String) shippingAddress.get("shipping_address2"));
		address.put("shipping_city", (String) shippingAddress.get("shipping_city"));
		address.put("shipping_postalCode", (String) shippingAddress.get("shipping_zip"));
		address.put("shipping_stateProvinceGeoId", (String) shippingAddress.get("shipping_state"));
		address.put("shipping_countryGeoId", getGeoCodeFromGeoId((String) shippingAddress.get("shipping_country")));

		return address;
	}

	/**
	 * Get the billing address map
	 * @return
	 */
	public Map<String, String> getBillingAddress() throws GenericEntityException {
		Map<String, String> address = new HashMap<>();

		Map<String, Object> billingAddress = (Map<String, Object>) ((Map<String, Object>) this.responseObj.get("AddressInfo")).get("Billing");
		address.put("billing_firstName", (String) billingAddress.get("first_name"));
		address.put("billing_lastName", (String) billingAddress.get("last_name"));
		address.put("billing_address1", (String) billingAddress.get("address1"));
		address.put("billing_address2", (String) billingAddress.get("address2"));
		address.put("billing_city", (String) billingAddress.get("city"));
		address.put("billing_postalCode", (String) billingAddress.get("zip"));
		address.put("billing_stateProvinceGeoId", (String) billingAddress.get("state"));
		address.put("billing_countryGeoId", getGeoCodeFromGeoId((String) billingAddress.get("country")));

		return address;
	}

	/**
	 * Get the day time phone
	 * @return
	 */
	public String getDayPhone() {
		return ((String) this.responseObj.get("day_phone_a")) + ((String) this.responseObj.get("day_phone_b"));
	}

	/**
	 * Get the night time phone
	 * @return
	 */
	public String getNightPhone() {
		return ((String) this.responseObj.get("night_phone_a")) + ((String) this.responseObj.get("night_phone_b"));
	}

	/**
	 * Get the shipping cost
	 * @return
	 */
	public BigDecimal getShippingCost() {
		if(this.responseObj.get("shipping_cost") instanceof String) {
			return new BigDecimal((String) this.responseObj.get("shipping_cost"));
		} else if(this.responseObj.get("shipping_cost") instanceof Double) {
			return new BigDecimal(((Double) this.responseObj.get("shipping_cost")).toString());
		}

		return BigDecimal.ZERO;
	}

	/**
	 * Get the duty tax
	 * @return
	 */
	public BigDecimal getDuty() {
		if(this.responseObj.get("duty") instanceof String) {
			return new BigDecimal((String) this.responseObj.get("duty"));
		} else if(this.responseObj.get("duty") instanceof Double) {
			return new BigDecimal(((Double) this.responseObj.get("duty")).toString());
		}

		return BigDecimal.ZERO;
	}

	/**
	 * Get the tax
	 * @return
	 */
	public BigDecimal getTax() {
		if(this.responseObj.get("tax") instanceof String) {
			return new BigDecimal((String) this.responseObj.get("tax"));
		} else if(this.responseObj.get("tax") instanceof Double) {
			return new BigDecimal(((Double) this.responseObj.get("tax")).toString());
		}

		return BigDecimal.ZERO;
	}

	public String getEmail() {
		Map<String, Object> shippingAddress = (Map<String, Object>) ((Map<String, Object>) this.responseObj.get("AddressInfo")).get("Shipping");
		return (String) shippingAddress.get("shipping_email");
	}

	public List getPromoList() {
		return (List) this.responseObj.get("PromoList");
	}

	/**
	 * Get the temporary order id from the custom field
	 * @return
	 */
	public String getTempOrderId() {
		return (String) this.responseObj.get("custom");
	}

	/**
	 * Get GeoCode
	 * @param geoCode
	 * @return
	 * @throws GenericEntityException
	 */
	private String getGeoCodeFromGeoId(String geoCode) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(geoCode)) {
			GenericValue geo = EntityUtil.getFirst(this.delegator.findByAnd("Geo", UtilMisc.toMap("geoCode", geoCode), null, true));
			if(UtilValidate.isNotEmpty(geo)) {
				return geo.getString("geoId");
			}
		}

		return null;
	}
}