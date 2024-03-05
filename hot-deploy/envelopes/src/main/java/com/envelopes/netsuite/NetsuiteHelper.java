/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.netsuite;

import java.io.*;
import java.math.RoundingMode;
import java.net.*;
import java.lang.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import com.bigname.integration.listrak.ListrakHelper;
import com.bigname.quote.calculator.CalculatorHelper;
import com.envelopes.email.EmailHelper;
import com.envelopes.http.FileHelper;
import com.envelopes.iparcel.IParcelEvents;
import com.envelopes.party.PartyHelper;
import com.envelopes.scene7.Scene7Helper;
import com.envelopes.service.ServiceHelper;
import com.envelopes.tax.TaxHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.DelegatorFactory;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityConditionList;
import org.apache.ofbiz.entity.condition.EntityExpr;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.order.order.OrderReadHelper;
import org.apache.ofbiz.order.shoppingcart.ShoppingCart;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.envelopes.http.HTTPHelper;
import com.envelopes.order.OrderHelper;
import com.envelopes.product.ProductHelper;
import com.envelopes.shipping.ShippingHelper;
import com.envelopes.util.*;
import com.bigname.core.config.SiteConfig;

import static com.envelopes.util.EnvConstantsUtil.*;

public class NetsuiteHelper {
	public static final String module = NetsuiteHelper.class.getName();

	private static String STANDARDIZED_SHIPPING;
	private static String NLAUTH_ACCOUNT;
	private static String NLAUTH_EMAIL;
	private static String NLAUTH_SIGNATURE;
	private static String NLAUTH_ROLE;
	private static String NL_URL;
	private static String NL_CONSUMER_KEY;
	private static String NL_CONUMER_SECRET;
	private static String NL_APPID;
	private static String NL_TOKENID;
	private static String NL_TOKENSECRET;
	protected static Map<String, String> methodCall;
	private static Map<String, String> lineItemDiscounts;
	public static final Map<String, String> AeCountryMap;
	public static final Map<String, String> AeShipMethodMap;
	public static final Map<String, String> AeNonTaxShipMethodMap;

	static {
		try {
			STANDARDIZED_SHIPPING = UtilProperties.getPropertyValue("envelopes", "shipment.standardized");
			NLAUTH_ACCOUNT = UtilProperties.getPropertyValue("envelopes", (IS_PRODUCTION ? "" : "sb.") + "suiteTalk.accountId");
			NLAUTH_EMAIL = UtilProperties.getPropertyValue("envelopes", (IS_PRODUCTION ? "" : "sb.") + "suiteTalk.userName");
			NLAUTH_SIGNATURE = UtilProperties.getPropertyValue("envelopes", (IS_PRODUCTION ? "" : "sb.") + "suiteTalk.password");
			NLAUTH_ROLE = UtilProperties.getPropertyValue("envelopes", (IS_PRODUCTION ? "" : "sb.") + "suiteTalk.roleId");
			NL_URL = UtilProperties.getPropertyValue("envelopes", (IS_PRODUCTION ? "" : "sb.") + "suiteTalk.restlet.url");
			NL_CONSUMER_KEY = UtilProperties.getPropertyValue("envelopes", (IS_PRODUCTION ? "" : "sb.") + "suiteTalk.key");
			NL_CONUMER_SECRET = UtilProperties.getPropertyValue("envelopes", (IS_PRODUCTION ? "" : "sb.") + "suiteTalk.secret");
			NL_APPID = UtilProperties.getPropertyValue("envelopes", (IS_PRODUCTION ? "" : "sb.") + "suiteTalk.appid");
			NL_TOKENID = UtilProperties.getPropertyValue("envelopes", (IS_PRODUCTION ? "" : "sb.") + "suiteTalk.tokenid");
			NL_TOKENSECRET = UtilProperties.getPropertyValue("envelopes", (IS_PRODUCTION ? "" : "sb.") + "suiteTalk.tokensecret");
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error, could not open envelopes.properties.", module);
		}

		methodCall = new HashMap<>();
		methodCall.put("insertOrder", NL_URL + "?realm=" + NLAUTH_ACCOUNT + "&script=17&deploy=1");
		methodCall.put("insertCustomer", NL_URL + "?realm=" + NLAUTH_ACCOUNT + "&script=44&deploy=1");
		methodCall.put("getInventory", NL_URL + "?realm=" + NLAUTH_ACCOUNT + "&script=19&deploy=1");
		methodCall.put("getReturn", NL_URL + "?realm=" + NLAUTH_ACCOUNT + "&script=22&deploy=1");
		methodCall.put("getAllFulfillments", NL_URL + "?realm=" + NLAUTH_ACCOUNT + "&script=24&deploy=1");
		methodCall.put("updateOrder", NL_URL + "?realm=" + NLAUTH_ACCOUNT + "&script=26&deploy=1");
		methodCall.put("createPO", NL_URL + "?realm=" + NLAUTH_ACCOUNT + "&script=31&deploy=1");
		methodCall.put("getOrderItemPO", NL_URL + "?realm=" + NLAUTH_ACCOUNT + "&script=35&deploy=1");
		methodCall.put("findOrder", NL_URL + "?realm=" + NLAUTH_ACCOUNT + "&script=32&deploy=1");
		methodCall.put("createItemFulfillment", NL_URL + "?realm=" + NLAUTH_ACCOUNT + "&script=33&deploy=1");
		methodCall.put("createItemReceipt", NL_URL + "?realm=" + NLAUTH_ACCOUNT + "&script=54&deploy=1");
		methodCall.put("getPOPDF", NL_URL + "?realm=" + NLAUTH_ACCOUNT + "&script=34&deploy=1");
		methodCall.put("addLead", NL_URL + "?realm=" + NLAUTH_ACCOUNT + "&script=37&deploy=1");
		methodCall.put("createEstimate", NL_URL + "?realm=" + NLAUTH_ACCOUNT + "&script=38&deploy=1");
		methodCall.put("findPO", NL_URL + "?realm=" + NLAUTH_ACCOUNT + "&script=39&deploy=1");
		methodCall.put("getItemWorkOrder", NL_URL + "?realm=" + NLAUTH_ACCOUNT + "&script=42&deploy=1");
		methodCall.put("getChannelSales", NL_URL + "?realm=" + NLAUTH_ACCOUNT + "&script=101&deploy=1");
		methodCall.put("getWorkOrder", NL_URL + "?realm=" + NLAUTH_ACCOUNT + "&script=175&deploy=1");

		lineItemDiscounts = new HashMap<>();
		lineItemDiscounts.put("Trade Discount", "Trade Discount Promotion");
		lineItemDiscounts.put("Samples Coupon", "Samples Coupon");
		lineItemDiscounts.put("Non Profit Discount", "Non Profit Discount Promotion");
		lineItemDiscounts.put("Loyalty Discount", "Loyalty Discount");
		lineItemDiscounts.put("Trade Post Net Discount", "Postnet Discount");
		lineItemDiscounts.put("Trade Allegra Discount", "Allegra Discount");
		lineItemDiscounts.put("Nontaxable Discount", "Nontaxable Discount");

		AeCountryMap = new HashMap<>();
		AeCountryMap.put("United States", "US");
		AeCountryMap.put("Canada", "CA");
		AeCountryMap.put("Puerto Rico", "PR");
		AeCountryMap.put("Virgin Islands", "VI");
		AeCountryMap.put("Guam", "GU");
		AeCountryMap.put("Northern Marianas Islands", "MP");
		AeCountryMap.put("American Samoa", "AS");
		AeCountryMap.put("Argentina", "AR");
		AeCountryMap.put("Australia", "AU");
		AeCountryMap.put("Austria", "AT");
		AeCountryMap.put("Belgium", "BE");
		AeCountryMap.put("Brazil", "BR");
		AeCountryMap.put("China", "CN");
		AeCountryMap.put("Denmark", "DK");
		AeCountryMap.put("Dominican Republic", "DO");
		AeCountryMap.put("Ecuador", "EC");
		AeCountryMap.put("Finland", "FI");
		AeCountryMap.put("France", "FR");
		AeCountryMap.put("Germany", "DE");
		AeCountryMap.put("Greece", "GR");
		AeCountryMap.put("Greenland", "GL");
		AeCountryMap.put("Honduras", "HN");
		AeCountryMap.put("Hong Kong", "HK");
		AeCountryMap.put("Iceland", "IS");
		AeCountryMap.put("India", "IN");
		AeCountryMap.put("Indonesia", "ID");
		AeCountryMap.put("Ireland", "IE");
		AeCountryMap.put("Israel", "IL");
		AeCountryMap.put("Italy", "IT");
		AeCountryMap.put("Jamaica", "JM");
		AeCountryMap.put("Japan", "JP");
		AeCountryMap.put("Korea, Repulic of", "KR");
		AeCountryMap.put("Malaysia", "MY");
		AeCountryMap.put("Mexico", "MX");
		AeCountryMap.put("Netherlands", "NL");
		AeCountryMap.put("New Zealand", "NZ");
		AeCountryMap.put("Norway", "NO");
		AeCountryMap.put("Panama", "PA");
		AeCountryMap.put("Philippines", "PH");
		AeCountryMap.put("Poland", "PL");
		AeCountryMap.put("Portugal", "PT");
		AeCountryMap.put("Russia", "RU");
		AeCountryMap.put("Singapore", "SG");
		AeCountryMap.put("South Africa", "ZA");
		AeCountryMap.put("Spain", "ES");
		AeCountryMap.put("Sweden", "SE");
		AeCountryMap.put("Switzerland", "CH");
		AeCountryMap.put("United Kingdom", "GB");
		AeCountryMap.put("Taiwan", "TW");
		AeCountryMap.put("Thailand", "TH");
		AeCountryMap.put("Turkey", "TR");
		AeCountryMap.put("United Arab Emirates", "AE");
		AeCountryMap.put("Venezuela", "VE");

		AeShipMethodMap = new HashMap<>();
		AeShipMethodMap.put("UPS Ground", "4");
		AeShipMethodMap.put("Fedex 3rd Party", "1240");
		AeShipMethodMap.put("LTL Shipment", "1239");
		AeShipMethodMap.put("Local Messenger", "1238");
		AeShipMethodMap.put("Pickup (Amityville, NY)", "1280");
		AeShipMethodMap.put("UPS SurePost", "58212");
		AeShipMethodMap.put("UPS 3rd Party", "1281");
		AeShipMethodMap.put("UPS 3-Day Select", "5");
		AeShipMethodMap.put("UPS Next-Day Saver", "17485");
		AeShipMethodMap.put("UPS Next-Day Air", "7");
		AeShipMethodMap.put("UPS Second-Day Air", "6");
		AeShipMethodMap.put("UPS Standard (2-6 Bus. Days)", "2128");
		AeShipMethodMap.put("UPS Worldwide Expedited (3 Bus. Days)", "2129");
		AeShipMethodMap.put("UPS Worldwide Express (1-2 Bus. Days)", "2130");
		AeShipMethodMap.put("USPS First Class Mail", "1972");
		AeShipMethodMap.put("USPS Priority Mail (2-3 Days)", "1341");
		AeShipMethodMap.put("USPS Small Flat Rate", "17464");
		AeShipMethodMap.put("Vendor Truck", "1256");
		AeShipMethodMap.put("International Standard", "16473"); //we are setting international standard, it will be shipping Ground to Bongo

		try {
			updateShipMethodMap();
		} catch (Exception e) {
			Debug.logError("Unable to load the NetSuite Shipping Method Id, falling back to default", module);
			AeShipMethodMap.put("UPS Ground", IS_UPS ? "4" : "83152");
			AeShipMethodMap.put("Smart Post", "83157");
			AeShipMethodMap.put("Next Day", "83154");
			AeShipMethodMap.put("Next Day Early A.M.", "83153");
			AeShipMethodMap.put("Second Day", "83155");
			AeShipMethodMap.put("Third Day", "83156");
		}


		AeNonTaxShipMethodMap = new HashMap<>();
		AeNonTaxShipMethodMap.put("UPS Ground", "30102");
		AeNonTaxShipMethodMap.put("UPS 3-Day Select", "30101");
		AeNonTaxShipMethodMap.put("UPS Next-Day Saver", "30100");
		AeNonTaxShipMethodMap.put("UPS Next-Day Air", "30103");
		AeNonTaxShipMethodMap.put("UPS Second-Day Air", "30104");
		AeNonTaxShipMethodMap.put("USPS First Class Mail", "30105");
		AeNonTaxShipMethodMap.put("USPS Priority Mail (2-3 Days)", "30106");
		AeNonTaxShipMethodMap.put("USPS Small Flat Rate", "30107");
		AeNonTaxShipMethodMap.put("UPS SurePost", "81827");
	}

	/**
	 * Get all unshipped orders
	 */
	public static List<String> getUnShippedOrderIDs(Delegator delegator) throws GenericEntityException {
		List<GenericValue> orderList = delegator.findByAnd("OrderHeader", UtilMisc.toMap("statusId", "ORDER_CREATED"), UtilMisc.toList("createdStamp ASC"), false);
		if(UtilValidate.isNotEmpty(orderList)) {
			return EntityUtil.getFieldListFromEntityList(orderList, "orderId", true);
		}

		return null;
	}

	private static void updateShipMethodMap() throws Exception {
		Delegator delegator = DelegatorFactory.getDelegator("default");
		List<GenericValue> shippingMethods = delegator.findByAnd("ShippingCarrierMethod", UtilMisc.toMap("carrierId", "FEDEX"), null, true);
		for(GenericValue shippingMethod : shippingMethods) {
			String nsShippingMethodId = shippingMethod.getString("nsShippingMethodId");
			GenericValue shipmentMethodType = delegator.findOne("ShipmentMethodType", UtilMisc.toMap("shipmentMethodTypeId", shippingMethod.getString("shippingMethodId")), true);
			if("GROUND".equals(shippingMethod.getString("shippingMethodId"))) {
				AeShipMethodMap.put(shipmentMethodType.getString("description"), IS_UPS ? "4" : nsShippingMethodId);
			} else {
				AeShipMethodMap.put(shipmentMethodType.getString("description"), nsShippingMethodId);
			}
		}
	}

	/**
	 * Check if order is exportable
	 */
	public static boolean isExportable(Delegator delegator, GenericValue order, boolean ignoreValidity) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(order) && !ignoreValidity) {
			//check if it has a shipping value
			Map<String, Object> filters = new HashMap<String, Object>();
			filters.put("orderId", order.getString("orderId"));
			filters.put("orderItemSeqId", null);
			filters.put("orderAdjustmentTypeId", "SHIPPING_CHARGES");
			filters.put("amount", BigDecimal.ZERO);
			filters.put("productPromoId", null); //shipping could be 0 if a promo was used
			GenericValue orderAdj = EntityUtil.getFirst(delegator.findByAnd("OrderAdjustment", filters, null, false));
			if(orderAdj != null) {
				return false;
			}

			//check if its pending
			if(order.getString("statusId").equals("ORDER_PENDING")) {
				return false;
			}

			//now check if it has a valid payment
			filters.clear();
			filters.put("orderId", order.getString("orderId"));
			filters.put("statusId", "PAYMENT_NOT_AUTH");
			GenericValue orderPayment = EntityUtil.getFirst(delegator.findByAnd("OrderPaymentPreference", filters, UtilMisc.toList("createdStamp DESC"), false));
			if(orderPayment != null) {
				return false;
			}
		}

		return true;
	}

	public static Map<String, Object> findChannelOrder(String orderId) throws Exception {
		Map<String, Object> orderData;
		Map<String, Object> data = new HashMap<>();
		data.put("orderId", orderId);
		String result = HTTPHelper.getURL(NetsuiteHelper.methodCall.get("findOrder"), getMethodType(NetsuiteHelper.methodCall.get("findOrder")), getOAuthParams("findOrder"), null, data, null, true, RESPONSE_JSON, RESPONSE_JSON);
		try {
			orderData = new Gson().fromJson(result, Map.class);
		} catch(Exception ignore) {
			orderData = new HashMap<>();
		}
		return orderData;
	}

	/**
	 * Create a PO in Netsuite
	 */
	public static Map<String, Object> createPurchaseOrder(Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> data) throws Exception {
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;
		int counter = 0;

		if(UtilValidate.isNotEmpty(data)) {
			do {
				Debug.logInfo("PO Output: " + new Gson().toJson(data), module);
				String response = HTTPHelper.getURL(methodCall.get("createPO"), getMethodType(methodCall.get("createPO")), getOAuthParams("createPO"), null, data, null, true, RESPONSE_JSON, RESPONSE_JSON);
				if(response.startsWith("{")) {
					HashMap<String, Object> orderResponse = new Gson().fromJson(response, HashMap.class);
					if(orderResponse != null) {
						if(orderResponse.containsKey("purchaseOrderId") && UtilValidate.isNotEmpty(orderResponse.get("purchaseOrderId"))) {
							jsonResponse.put("purchaseOrderId", (String) orderResponse.get("purchaseorder"));
							success = true;
						}
					}
				}
				counter++;
			} while(!success && counter < 3);
		}

		jsonResponse.put("success", success);
		return jsonResponse;
	}

	/**
	 * Create a Lead in Netsuite
	 */
	public static Map<String, Object> createLead(Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> data) throws GenericEntityException, GenericServiceException, UnsupportedEncodingException, MalformedURLException, IOException, NumberFormatException, Exception {
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;

		if(UtilValidate.isNotEmpty(data)) {
			Debug.logInfo("Lead Output: " + data, module);
			String response = HTTPHelper.getURL(methodCall.get("addLead"), getMethodType(methodCall.get("addLead")), getOAuthParams("addLead"), null, data, null, true, RESPONSE_JSON, RESPONSE_JSON);
			if(response.startsWith("{")) {
				HashMap<String, Object> orderResponse = new Gson().fromJson(response, HashMap.class);
				if(orderResponse != null) {
					if(orderResponse.containsKey("id")) {
						jsonResponse.put("id", (String) orderResponse.get("id"));
						success = true;
					}
				}
			}
		}

		jsonResponse.put("success", success);
		return jsonResponse;
	}

	/**
	 * Get channel sales data
	 */
	public static Map<String, Object> getChannelSalesReport(Delegator delegator, LocalDispatcher dispatcher) throws Exception {
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;
		int counter = 0;

		do {
			String response = HTTPHelper.getURL(methodCall.get("getChannelSales"), getMethodType(methodCall.get("getChannelSales")), getOAuthParams("getChannelSales"), null, UtilMisc.toMap("get", "data"), null, true, RESPONSE_JSON, RESPONSE_JSON);
			if(response.startsWith("[")) {
				List<Map> reportResponse = new Gson().fromJson(response, ArrayList.class);
				if(reportResponse != null) {
					jsonResponse.put("data", reportResponse);
					success = true;
				}
			}
			counter++;
		} while(!success && counter < 3);

		jsonResponse.put("success", success);
		return jsonResponse;
	}

	public static Map<String, Object> getOrderItemPO(Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> data) throws GenericEntityException, GenericServiceException, UnsupportedEncodingException, MalformedURLException, IOException, NumberFormatException, Exception {
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;

		if(UtilValidate.isNotEmpty(data)) {
			String response = HTTPHelper.getURL(methodCall.get("getOrderItemPO"), getMethodType(methodCall.get("getOrderItemPO")), getOAuthParams("getOrderItemPO"), null, data, null, true, RESPONSE_JSON, RESPONSE_JSON);
			if(response.startsWith("{")) {
				HashMap<String, Object> orderResponse = new Gson().fromJson(response, HashMap.class);
				if(orderResponse != null) {
					if(orderResponse.containsKey("purchaseorder")) {
						jsonResponse.put("purchaseOrderId", (String) orderResponse.get("purchaseorder"));
						success = true;
					}
				}
			}
		}

		jsonResponse.put("success", success);
		return jsonResponse;
	}

	/**
	 * Get work order data
	 * @param delegator
	 * @param dispatcher
	 * @param data
	 * @return
	 * @throws GenericEntityException
	 * @throws GenericServiceException
	 * @throws UnsupportedEncodingException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public static Map<String, Object> getWorkOrder(Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> data) throws GenericEntityException, GenericServiceException, UnsupportedEncodingException, MalformedURLException, IOException, NumberFormatException, Exception {
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;

		if(UtilValidate.isNotEmpty(data)) {
			if("Y".equalsIgnoreCase((String) data.get("update"))) {
				if(UtilValidate.isNotEmpty(data.get("pulledBy"))) {
					data.put("pulledDate", MDYTA.format(UtilDateTime.nowTimestamp()));
					if(data.get("pulledBy") instanceof List) {
						data.put("pulledBy", String.join(",", (List) data.get("pulledBy")));
					}
				}
                if(UtilValidate.isNotEmpty(data.get("cutBy"))) {
                    data.put("cutDate", MDYTA.format(UtilDateTime.nowTimestamp()));
					/*if(data.get("cutBy") instanceof List) {
						data.put("cutBy", String.join(",", (List) data.get("cutBy")));
					}*/
                }
			}

			String response = HTTPHelper.getURL(methodCall.get("getWorkOrder"), getMethodType(methodCall.get("getWorkOrder")), getOAuthParams("getWorkOrder"), null, data, null, true, RESPONSE_JSON, RESPONSE_JSON);
			if(response.startsWith("{")) {
				response = response.replaceAll("\\\\u0005", ",");
				HashMap<String, Object> orderResponse = new Gson().fromJson(response, HashMap.class);
				if(orderResponse != null && orderResponse.containsKey("id")) {
					jsonResponse = orderResponse;
					success = true;
				}
			}
		}

		jsonResponse.put("success", success);
		return jsonResponse;
	}

	public static Map<String, Object> findPO(Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> data) throws Exception {
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;

		if(UtilValidate.isNotEmpty(data)) {
			String response = HTTPHelper.getURL(methodCall.get("findPO"), getMethodType(methodCall.get("findPO")), getOAuthParams("findPO"), null, data, null, true, RESPONSE_JSON, RESPONSE_JSON);
			if(response.startsWith("{")) {
				HashMap<String, Object> orderResponse = new Gson().fromJson(response, HashMap.class);
				if(orderResponse != null) {
					jsonResponse = orderResponse;
					success = true;
				}
			}
		}

		jsonResponse.put("success", success);
		return jsonResponse;
	}

	/**
	 * Get PO PDF and return filepath to location
	 */
	public static Map<String, Object> getPOPDF(Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> data) throws Exception {
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;

		if(UtilValidate.isNotEmpty(data)) {
			String response = HTTPHelper.getURL(methodCall.get("getPOPDF"), getMethodType(methodCall.get("getPOPDF")), getOAuthParams("getPOPDF"), null, data, null, true, RESPONSE_JSON, RESPONSE_JSON);
			if(response.startsWith("{")) {
				HashMap<String, Object> orderResponse = new Gson().fromJson(response, HashMap.class);
				if(orderResponse != null) {
					if(orderResponse.containsKey("base64")) {
						jsonResponse = FileHelper.saveBase64File((String) orderResponse.get("base64"), (String) data.get("purchaseOrderId"), "pdf", null);
					}
				}
			}
		}

		return jsonResponse;
	}

	public static Map<String, Object> createOrderItemFulfillment(Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> data) throws GenericEntityException, GenericServiceException, UnsupportedEncodingException, MalformedURLException, IOException, NumberFormatException, Exception {
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;
		int counter = 0;

		if(UtilValidate.isNotEmpty(data)) {
			do {
				String response = HTTPHelper.getURL(methodCall.get("createItemFulfillment"), getMethodType(methodCall.get("createItemFulfillment")), getOAuthParams("createItemFulfillment"), null, data, null, true, RESPONSE_JSON, RESPONSE_JSON);
				if (response.startsWith("{")) {
					HashMap<String, Object> orderResponse = new Gson().fromJson(response, HashMap.class);
					if (orderResponse != null) {
						if (orderResponse.containsKey("itemFulfillmentId") && UtilValidate.isNotEmpty(orderResponse.get("itemFulfillmentId"))) {
							jsonResponse.put("itemFulfillmentId", (String) orderResponse.get("itemFulfillmentId"));
							success = true;
						}
					}
				}
				counter++;
			} while(!success && counter < 3);
		}

		jsonResponse.put("success", success);
		return jsonResponse;
	}

	/**
	 * Update a given order to netsuite
	 */
	public static Map<String, String> updateOrder(Delegator delegator, LocalDispatcher dispatcher, String orderId) throws Exception {
		Map<String, String> orderResponse = new HashMap<>();
		boolean success = false;
		int counter = 0;

		Map<String, Object> orderData = OrderHelper.getOrderData(delegator, orderId, true);
		GenericValue order = EntityQuery.use(delegator).from("OrderHeader").where("orderId", orderId).queryOne();
		if(order != null && UtilValidate.isNotEmpty(order.getString("exportedDate"))) {
			Map<String, Object> orderJSON = createOrderMap(delegator, dispatcher, orderData, true);
			Gson gson = new GsonBuilder().serializeNulls().create();

			if (UtilValidate.isNotEmpty(orderJSON)) {
				do {
					String response = HTTPHelper.getURL(methodCall.get("updateOrder"), getMethodType(methodCall.get("updateOrder")), getOAuthParams("updateOrder"), null, orderJSON, null, true, RESPONSE_JSON, RESPONSE_JSON);
					if (response.startsWith("{")) {
						HashMap<String, Object> jsonResponse = new Gson().fromJson(response, HashMap.class);
						if (jsonResponse != null) {
							if (jsonResponse.containsKey("orderId") && UtilValidate.isNotEmpty(jsonResponse.get("orderId"))) {
								orderResponse.put(orderId, "Updated successfully: " + new GsonBuilder().serializeNulls().create().toJson(orderJSON));
								success = true;
							} else if (jsonResponse.containsKey("error")) {
								orderResponse.put(orderId, ((Map<String, Object>) jsonResponse.get("error")).get("message") + ", JSON Request: " + new GsonBuilder().serializeNulls().create().toJson(orderJSON));
							} else {
								orderResponse.put(orderId, "Export failed, please review.");
								Debug.logInfo("orderJSON: " + gson.toJson(orderJSON), module);
							}
						}
					} else {
						orderResponse.put(orderId, response);
						Debug.logInfo("orderJSON: " + gson.toJson(orderJSON), module);
					}
					counter++;
				} while(!success && counter < 3);
			} else {
				orderResponse.put(orderId, "Export failed, does not meet criteria to export (maybe pending).");
				Debug.logInfo("orderJSON: " + gson.toJson(orderJSON), module);
			}
		}

		return orderResponse;
	}

	/**
	 * Export a given customer to netsuite
	 */
	public static Map<String, String> exportCustomer(Delegator delegator, LocalDispatcher dispatcher, String partyId, String orderId, String quoteId) throws GenericEntityException, GenericServiceException, UnsupportedEncodingException, MalformedURLException, IOException, NumberFormatException, Exception {
		Map<String, String> customerResponse = new HashMap<>();

		Map<String, Object> json = new HashMap<>();
		if(orderId != null) {
			Map<String, Object> orderData = OrderHelper.getOrderData(delegator, orderId, true);
			json = createOrderMap(delegator, dispatcher, orderData, true);
			json = (Map<String, Object>) json.get("customer");
		} else if(quoteId != null) {

		} else {
			GenericValue person = PartyHelper.getPerson(delegator, partyId);
			json.put("partyId", partyId);
			json.put("firstname", person.getString("firstName"));
			json.put("lastname", person.getString("lastName"));

			Map<String, Object> getAEPartyRoles = dispatcher.runSync("getAEPartyRoles", UtilMisc.toMap("partyId", partyId));
			boolean isTrade = (UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isTrade"))) ? ((Boolean) getAEPartyRoles.get("isTrade")).booleanValue() : false;
			boolean isNonProfit = (UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isNonProfit"))) ? ((Boolean) getAEPartyRoles.get("isNonProfit")).booleanValue() : false;
			boolean isNonTaxable = (UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isNonTaxable"))) ? ((Boolean) getAEPartyRoles.get("isNonTaxable")).booleanValue() : false;
			boolean isTradePostNet = (UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isTradePostNet"))) ? ((Boolean) getAEPartyRoles.get("isTradePostNet")).booleanValue() : false;
			boolean isTradeAllegra = (UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isTradeAllegra"))) ? ((Boolean) getAEPartyRoles.get("isTradeAllegra")).booleanValue() : false;

			json.put("isTrade", (isTrade || isTradePostNet || isTradeAllegra));
			json.put("isNonProfit", isNonProfit);
			json.put("isNonTaxable", isNonTaxable);
		}

		String response = HTTPHelper.getURL(methodCall.get("insertCustomer"), getMethodType(methodCall.get("insertCustomer")), getOAuthParams("insertCustomer"), null, json, null, true, RESPONSE_JSON, RESPONSE_JSON);
		if(response != null && response.startsWith("{")) {
			HashMap<String, Object> jsonResponse = new Gson().fromJson(response, HashMap.class);
			if(jsonResponse != null) {
				if(jsonResponse.containsKey("externalid") && UtilValidate.isNotEmpty(jsonResponse.get("externalid"))) {
					GenericValue party = PartyHelper.getParty(delegator, partyId);
					party.put("externalId", (String) jsonResponse.get("externalid"));
					party.store();
					customerResponse.put(partyId, response + ", JSON Request: " + new GsonBuilder().serializeNulls().create().toJson(json));
				} else {
					customerResponse.put(partyId, response);
				}
			}
		}

		return customerResponse;
	}

	/**
	 * Export a given order to netsuite
	 */
	public static Map<String, String> exportOrder(Delegator delegator, LocalDispatcher dispatcher, String orderId, boolean ignoreValidity) throws GenericEntityException, GenericServiceException, UnsupportedEncodingException, MalformedURLException, IOException, NumberFormatException, Exception {
		Map<String, String> orderResponse = new HashMap<>();

		Map<String, Object> orderData = OrderHelper.getOrderData(delegator, orderId, true);
		GenericValue order = (GenericValue) orderData.get("orderHeader");

		Map<String, Object> orderJSON = createOrderMap(delegator, dispatcher, orderData, ignoreValidity);

		Gson gson = new GsonBuilder().serializeNulls().create();
		Debug.logInfo("orderJSON: " + gson.toJson(orderJSON), module);

		if(UtilValidate.isNotEmpty(orderJSON)) {
			String response = HTTPHelper.getURL(methodCall.get("insertOrder"), getMethodType(methodCall.get("insertOrder")), getOAuthParams("insertOrder"), null, orderJSON, null, true, RESPONSE_JSON,  RESPONSE_JSON);

			//garble credit card number because this is being sent to email for logging
			if(orderJSON.containsKey("payment") && UtilValidate.isNotEmpty(orderJSON.get("payment")) && ((Map<String, Object>) orderJSON.get("payment")).containsKey("ccnumber")) {
				((Map<String, Object>) orderJSON.get("payment")).put("ccnumber", "XXXX-XXXX-XXXX-XXXX");
			}

			if(response.startsWith("{")) {
				HashMap<String, Object> jsonResponse = new Gson().fromJson(response, HashMap.class);
				if(jsonResponse != null) {
					if(jsonResponse.containsKey("orderId")) {
						orderResponse.put(orderId, "Exported successfully: " + new GsonBuilder().serializeNulls().create().toJson(orderJSON));
						order.put("exportedDate", UtilDateTime.nowTimestamp());
						order.store();
					} else if(jsonResponse.containsKey("error")) {
						orderResponse.put(orderId, response + ", JSON Request: " + new GsonBuilder().serializeNulls().create().toJson(orderJSON));
						if(((Map<String, Object>) jsonResponse.get("error")).get("message").equals("This record already exists") && UtilValidate.isEmpty(order.getString("exportedDate"))) {
							order.put("exportedDate", UtilDateTime.nowTimestamp());
							order.store();
						}
					} else {
						orderResponse.put(orderId, response);
					}
				}
			} else if(response.contains("CC_PROCESSOR_ERROR")) {
				if(!"ORDER_PENDING".equalsIgnoreCase(order.getString("statusId"))) {
					try {
						Map<String, String> emailData = ListrakHelper.createOrderPendingEmailData(delegator, orderData, orderId);
						dispatcher.runAsync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "OrderPending", "data", emailData, "email", orderData.get("email"), "webSiteId", EnvUtil.getWebsiteId(order.getString("salesChannelEnumId"))));
						dispatcher.runAsync("changeOrderStatus", UtilMisc.toMap("orderId", orderId, "statusId", "ORDER_PENDING", "userLogin", EnvUtil.getSystemUser(delegator)));
					} catch (GenericServiceException e) {
						EnvUtil.reportError(e);
						Debug.logError(e, "Error setting pending status" + " " + e + " : " + e.getMessage(), module);
					}
				}
				if(UtilValidate.isEmpty(order.getString("exportedDate"))) {
					order.put("exportedDate", UtilDateTime.nowTimestamp());
					order.store();
				}
				orderResponse.put(orderId, "Export failed to charge card. Deposit not created.");
			} else {
				orderResponse.put(orderId, response);
				Debug.logInfo("orderJSON: " + gson.toJson(orderJSON), module);
			}
		} else {
			orderResponse.put(orderId, "Export failed, does not meet criteria to export (maybe pending).");
		}

		return orderResponse;
	}

	/*
	 * Build order json
	 */
	public static Map<String, Object> createOrderMap(Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> orderData, boolean ignoreValidity) throws GenericEntityException, GenericServiceException, Exception {
		Map<String, Object> orderJSON = new HashMap<String, Object>();
		GenericValue order = (GenericValue) orderData.get("orderHeader");
		if(order != null && isExportable(delegator, order, ignoreValidity)) {
			//Map<String, Object> orderDetails = OrderHelper.buildOrderDetails(null, orderData);
			OrderReadHelper orh = new OrderReadHelper(order);
			GenericValue orderHeader = (GenericValue) orderData.get("orderHeader");
			String webSiteId = EnvUtil.getWebsiteId(orderHeader.getString("salesChannelEnumId"));
			SiteConfig siteconfig = SITE_CONFIGS.get(webSiteId);

			List<GenericValue> orderHeaderAdjustments = (List<GenericValue>) orh.getOrderHeaderAdjustments();
			List<GenericValue> orderAdjustments = (List<GenericValue>) orh.getAdjustments();

			Map<String, String> taxData = getTaxRate(delegator, (GenericValue) orderData.get("shippingAddress"));
			Map<String, Object> shipData = getShipMethod(delegator, dispatcher, orh, (GenericValue) orderData.get("shippingAddress"), orh.getShippingTotal(), orderAdjustments);
			//Map<String, Object> discountData = getDiscountTotal(delegator, orderHeaderAdjustments);

			Map<String, Object> getAEPartyRoles = dispatcher.runSync("getAEPartyRoles", UtilMisc.toMap("partyId", (orh.getPlacingParty()).getString("partyId")));
			boolean isOrderPrinted = OrderHelper.isOrderPrinted(orh.getOrderItems());
			boolean isOrderQuote = OrderHelper.isOrderQuote(orh.getOrderItems());
			boolean isTrade = (UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isTrade"))) ? ((Boolean) getAEPartyRoles.get("isTrade")).booleanValue() : false;
			boolean isNonProfit = (UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isNonProfit"))) ? ((Boolean) getAEPartyRoles.get("isNonProfit")).booleanValue() : false;
			boolean isNonTaxable = (UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isNonTaxable"))) ? ((Boolean) getAEPartyRoles.get("isNonTaxable")).booleanValue() : false;
			boolean isTradePostNet = (UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isTradePostNet"))) ? ((Boolean) getAEPartyRoles.get("isTradePostNet")).booleanValue() : false;
			boolean isTradeAllegra = (UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isTradeAllegra"))) ? ((Boolean) getAEPartyRoles.get("isTradeAllegra")).booleanValue() : false;
			boolean isInternationalOrder = OrderHelper.isInternationalOrder(orh, delegator, null);

			orderJSON.put("tranDate", MDY.format(order.getTimestamp("orderDate")));
			orderJSON.put("tranid", order.getString("orderId"));
			orderJSON.put("externalid", order.getString("orderId"));
			orderJSON.put("isTrade", (isTrade || isTradePostNet || isTradeAllegra) ? true : false);
			orderJSON.put("isNonProfit", (isNonProfit) ? true : false);
			orderJSON.put("department", siteconfig.NETSUITE_SETTINGS.get("department"));

			//if cimpress order, populate the order source field
			if("2162652".equalsIgnoreCase(orh.getPlacingParty().getString("partyId"))) {
				orderJSON.put("order_source", "11");
			}

			orderJSON.put("totalcostestimate", 0);
			orderJSON.put("shipcomplete", true);
			orderJSON.put("getauth", false);
			orderJSON.put("custbody_blind_shipment", UtilValidate.isNotEmpty((GenericValue) orderData.get("shippingAddress")) ? OrderHelper.isBlindShipment(delegator, ((GenericValue) orderData.get("shippingAddress")).getString("contactMechId")) : false);

			orderJSON.put("custbodyae_admin_url", "https://os.bigname.com/admin/control/viewOrder?orderId=" + order.getString("orderId"));

			orderJSON.put("creditcardprocessor", (String) siteconfig.PAYMENT_SETTINGS.get("mesId"));
			orderJSON.put("custbody_brand", (String) siteconfig.NETSUITE_SETTINGS.get("brand"));
			if(OrderHelper.isOrderOnlySample(orh.getOrderItems())) {
				orderJSON.put("customform", (String) siteconfig.NETSUITE_SETTINGS.get("formSample"));
			} else if(isOrderQuote) {
				orderJSON.put("customform", (String) siteconfig.NETSUITE_SETTINGS.get("formQuote"));
			} else if(isOrderPrinted) {
				orderJSON.put("customform", (String) siteconfig.NETSUITE_SETTINGS.get("formPrint"));
			} else {
				orderJSON.put("customform", (String) siteconfig.NETSUITE_SETTINGS.get("formPlain"));
			}

			//orderJSON.put("discountcode", (String) discountData.get("codes"));
			//orderJSON.put("discountitem", (String) discountData.get("promoNetsuiteId"));
			//orderJSON.put("discounttotal", (UtilValidate.isNotEmpty(discountData.get("discountTotal"))) ? ((BigDecimal) discountData.get("discountTotal")).doubleValue() : 0.0);

			orderJSON.put("discountcode", null);
			orderJSON.put("discountitem", null);
			orderJSON.put("discounttotal", 0);
			orderJSON.put("istaxable", !isNonTaxable);
			orderJSON.put("taxrate", taxData.get("taxRate"));
			orderJSON.put("taxitem", (UtilValidate.isNotEmpty(taxData.get("taxItem"))) ? Integer.valueOf(taxData.get("taxItem")) : "");
			orderJSON.put("taxtotal", orh.getTaxTotal());
			orderJSON.put("custbody_rush_production", (UtilValidate.isNotEmpty(order.getString("isRushOrder")) && order.getString("isRushOrder").equals("Y")) ? true : false);
			orderJSON.put("custbody_loyalty_points", getLoyaltyPoints(dispatcher, (orh.getPlacingParty()).getString("partyId"), 12));
			orderJSON.put("salesrep", (EntityQuery.use(delegator).from("Party").where("partyId", (orh.getPlacingParty()).getString("partyId")).queryOne()).getString("salesRep"));

			if(UtilValidate.isNotEmpty(orderHeader.getString("externalId"))) {
				orderJSON.put("custbody_amazon_order_id", orderHeader.getString("externalId"));
				orderJSON.put("otherrefnum", orderHeader.getString("externalId"));
			}

			orderJSON.put("custbody_printed_or_plain", (isOrderPrinted) ? "Printed" : "Plain");
			orderJSON.put("custbody_address_type", ((Boolean) orderData.get("businessOrResidence")) ? "Business" : "Residence");
			orderJSON.put("shipdate", MDY.format(order.getTimestamp("orderDate")));

			Map<String, Object> paymentData = getPaymentData(delegator, orh, orderData);
			if(paymentData != null && "folders".equalsIgnoreCase(webSiteId) && isOrderPrinted) {
				paymentData.put("chargeit", true);
			}
			orderJSON.put("payment", paymentData);

			if(UtilValidate.isNotEmpty(orderData.get("closed"))) {
				orderJSON.put("closed", orderData.get("closed"));
			}
			orderJSON.put("shipnote", (isInternationalOrder) ? "iparcel" : shipData.get("shipNote"));
			orderJSON.put("shipmethod", (isInternationalOrder) ? AeShipMethodMap.get("UPS Ground") : shipData.get("shipMethod"));
			orderJSON.put("shippingcost", (isInternationalOrder) ? new Double("0.00") : shipData.get("shipCost"));
			orderJSON.put("custbody_customer_ship_via", shipData.get("custbody_customer_ship_via"));
			orderJSON.put("custbody_actual_ship_cost", shipData.get("custbody_actual_ship_cost"));

			orderJSON.put("custbody_comments", getOrderComments((List<Map>) orderData.get("notes")));
			orderJSON.put("getauth", false);

			List<Map> orderItems = getOrderItems(delegator, (List<Map>) orderData.get("items"), orh);

			//check if order item has a quote reference in it
			boolean hasQuote = false;
			boolean customSiteOrder = false;
			for(Map orderItem : orderItems) {
				if(UtilValidate.isNotEmpty(orderItem.get("quoteId"))) {
					orderJSON.put("quoteId", orderItem.get("quoteId"));
					GenericValue customQuoteInfo = EntityQuery.use(delegator).from("CustomEnvelope").where("quoteId", orderItem.get("quoteId")).queryOne();
					orderJSON.put("gclid", customQuoteInfo.get("gclid"));
					orderJSON.put("gsource", customQuoteInfo.get("source"));
					hasQuote = true;
					break;
				}
			}

			//if its a custom order placed on site without quote
			if(isOrderPrinted && !hasQuote) {
				customSiteOrder = true;
			}

			//if its international order, lets modify it for fees
			if(isInternationalOrder) {
				orderItems = getInternationalFees(orderItems);
			}

			List<Map> adjustments = getAdjustments(delegator, orderAdjustments, orh, false);
			if(UtilValidate.isNotEmpty(adjustments)) {
				orderItems.addAll(adjustments);
			}

			orderJSON.put("customSiteOrder", customSiteOrder);
			orderJSON.put("items", orderItems); //items
			orderJSON.put("total", order.getBigDecimal("grandTotal")); //totals
			orderJSON.put("terms", getTerms(orh, ("folders".equalsIgnoreCase(webSiteId) && isOrderQuote)));

			Map<String, Object> customer = getCustomerData(delegator, orderData, isInternationalOrder, (orh.getPlacingParty()).getString("partyId"));
			customer.put("partyId", (OrderHelper.isInternationalOrder(orh, delegator, null)) ? IParcelEvents.NETSUITE_EXT_ID : orh.getPlacingParty().getString("partyId"));
			customer.put("externalId", (OrderHelper.isInternationalOrder(orh, delegator, null)) ? IParcelEvents.NETSUITE_ID : PartyHelper.getParty(delegator, orh.getPlacingParty().getString("partyId")).getString("externalId"));
			customer.put("isTrade", (isTrade || isTradePostNet || isTradeAllegra));
			customer.put("isNonProfit", isNonProfit);
			customer.put("isNonTaxable", isNonTaxable);
			customer.put("email", orh.getOrderEmailString());

			GenericValue phone = OrderHelper.getTelecomNumber(orh, delegator, order.getString("orderId"), "PHONE_BILLING");
			customer.put("phone", (UtilValidate.isNotEmpty(phone)) ? phone.getString("contactNumber") : null);
			orderJSON.put("customer", customer);

			for(Map orderItem: orderItems) {
				if(((String) orderItem.get("sku")).contains("SWATCHBOOK")) {
					customer.put("swatchbook", true);
					customer.put("swatchbookDate", MDY.format(order.getTimestamp("orderDate")));
					break;
				}
			}
		}

		return orderJSON;
	}

	public static Map<String, Object> createEstimate(Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> data) throws GenericEntityException, GenericServiceException, UnsupportedEncodingException, MalformedURLException, IOException, NumberFormatException, Exception {
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;

		if(UtilValidate.isNotEmpty(data)) {
			String response = HTTPHelper.getURL(methodCall.get("createEstimate"), getMethodType(methodCall.get("createEstimate")), getOAuthParams("createEstimate"), null, data, null, true, RESPONSE_JSON, RESPONSE_JSON);
			jsonResponse.put("response", response);
			if(response.startsWith("{")) {
				HashMap<String, Object> orderResponse = new Gson().fromJson(response, HashMap.class);
				if(orderResponse != null) {
					if(orderResponse.containsKey("estimateId")) {
						jsonResponse.put("estimateId", (String) orderResponse.get("estimateId"));
						success = true;
					}
				}
			}
		}

		jsonResponse.put("success", success);
		return jsonResponse;
	}

	public static Map<String, Object> getSalesEstimateMap(Delegator delegator, LocalDispatcher dispatcher, String quoteRequestId, String quoteIds) throws Exception {
		Map<String, Object> estimateJSON = new HashMap<String, Object>();

		GenericValue quoteData = EntityQuery.use(delegator).from("CustomEnvelopeAndContactInfo").where("quoteId", quoteRequestId).queryFirst();
		SiteConfig siteconfig = SITE_CONFIGS.get(quoteData.getString("webSiteId"));

		Map<String, Object> getAEPartyRoles = dispatcher.runSync("getAEPartyRoles", UtilMisc.toMap("partyId", quoteData.getString("partyId")));
		boolean isTrade = (UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isTrade"))) ? ((Boolean) getAEPartyRoles.get("isTrade")).booleanValue() : false;
		boolean isNonProfit = (UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isNonProfit"))) ? ((Boolean) getAEPartyRoles.get("isNonProfit")).booleanValue() : false;
		boolean isNonTaxable = (UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isNonTaxable"))) ? ((Boolean) getAEPartyRoles.get("isNonTaxable")).booleanValue() : false;
		boolean isTradePostNet = (UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isTradePostNet"))) ? ((Boolean) getAEPartyRoles.get("isTradePostNet")).booleanValue() : false;
		boolean isTradeAllegra = (UtilValidate.isNotEmpty(getAEPartyRoles) && UtilValidate.isNotEmpty(getAEPartyRoles.get("isTradeAllegra"))) ? ((Boolean) getAEPartyRoles.get("isTradeAllegra")).booleanValue() : false;

		estimateJSON.put("tranDate", MDY.format(quoteData.getTimestamp("createdDate")));
		estimateJSON.put("tranid", quoteRequestId.toUpperCase());
		estimateJSON.put("externalid", quoteRequestId.toUpperCase());
		estimateJSON.put("isTrade", (isTrade || isTradePostNet || isTradeAllegra));
		estimateJSON.put("isNonProfit", isNonProfit);
		estimateJSON.put("department", siteconfig.NETSUITE_SETTINGS.get("department"));
		estimateJSON.put("gclid", quoteData.getString("gclid"));
		estimateJSON.put("gsource", quoteData.get("source"));

		estimateJSON.put("custbodyae_admin_url", "https://os.bigname.com/admin/control/foldersQuoteView?quoteId=" + quoteRequestId);

		estimateJSON.put("custbody_brand", (String) siteconfig.NETSUITE_SETTINGS.get("brand"));
		estimateJSON.put("customform", (String) siteconfig.NETSUITE_SETTINGS.get("estimate"));

		GenericValue userLogin = EntityQuery.use(delegator).from("UserLogin").where("userLoginId", quoteData.getString("userEmail")).queryFirst();
		Map<String, Object> customer = getPerson(userLogin, userLogin.getRelatedOne("Person", true));
		customer.put("partyId", quoteData.getString("partyId"));
		customer.put("isTrade", (isTrade || isTradePostNet || isTradeAllegra));
		customer.put("isNonProfit", isNonProfit);
		customer.put("isNonTaxable", isNonTaxable);
		customer.put("email", quoteData.getString("userEmail"));

		Map<String, Object> billingAddress = getAddress(delegator, quoteData, false);
		Map<String, Object> shippingAddress = getAddress(delegator, quoteData, true);
		Map<String, String> taxData = getTaxRate(delegator, shippingAddress);

		estimateJSON.put("shipmethod", getShippingMethod((String) quoteData.get("stateProvinceGeoId"), "UPS Ground"));
		estimateJSON.put("shippingcost", 0);

		billingAddress.put("firstname", quoteData.getString("firstName"));
		billingAddress.put("lastname", quoteData.getString("lastName"));
		shippingAddress.put("firstname", quoteData.getString("firstName"));
		shippingAddress.put("lastname", quoteData.getString("lastName"));

		customer.put("billing", billingAddress);
		customer.put("shipping", shippingAddress);

		customer.put("phone", quoteData.getString("phone"));
		estimateJSON.put("customer", customer);

		//get estimates
		List<Map<String, Object>> items = new ArrayList<>();
		List<GenericValue> quotes = EntityQuery.use(delegator).from("QcQuote").where(EntityCondition.makeCondition("quoteId", EntityOperator.IN, new ArrayList<String>(Arrays.asList(quoteIds.split(","))))).orderBy("createdStamp ASC").queryList();
		for(GenericValue quote : quotes) {
			Map quoteSummary = CalculatorHelper.deserializedQuoteSummary(delegator, quote, null);
			estimateJSON.put("salesrep", (String) quoteSummary.get("salesEmail"));
			Map<Integer, Map<String, BigDecimal>> quantities = (Map<Integer, Map<String, BigDecimal>>) quoteSummary.get("prices");

			Iterator iter = quantities.entrySet().iterator();
			while(iter.hasNext()) {
				Map.Entry pair = (Map.Entry) iter.next();
				Map<String, Object> data = new HashMap<>();
				data.put("sku", (String) quoteSummary.get("netsuiteSKU"));
				data.put("name", (UtilValidate.isNotEmpty(quoteSummary.get("product"))) ? ((GenericValue) quoteSummary.get("product")).getString("productName") + " - " + quoteSummary.get("material") : (String) quoteSummary.get("productName"));
				data.put("printOptions", (String) quoteSummary.get("printOptions"));
				data.put("addOnOptions", (String) quoteSummary.get("addOnOptions"));
				data.put("quantity", pair.getKey());
				data.put("amount", ((Map<String, BigDecimal>) pair.getValue()).get("total"));
				data.put("rate", ((Map<String, BigDecimal>) pair.getValue()).get("each"));
				data.put("seqId", quote.getString("quoteId"));
				items.add(data);
			}
		}

		estimateJSON.put("items", items);
		estimateJSON.put("istaxable", !isNonTaxable);
		estimateJSON.put("taxrate", taxData.get("taxRate"));
		estimateJSON.put("taxitem", (UtilValidate.isNotEmpty(taxData.get("taxItem"))) ? Integer.valueOf(taxData.get("taxItem")) : "");
		estimateJSON.put("taxtotal", null);

		Debug.logError("estimateJSON: " + (new GsonBuilder().serializeNulls().create()).toJson(estimateJSON), module);
		return estimateJSON;
	}

	/**
	 * Get all shipped orders for today
	 */
	public static Map<String, Map<String, String>> getShippedOrdersFromNetsuite() throws Exception {
		Map<String, Map<String, String>> orderAndTracking = new HashMap<>();
		String response = HTTPHelper.getURL(methodCall.get("getAllFulfillments"), getMethodType(methodCall.get("getAllFulfillments")), getOAuthParams("getAllFulfillments"), null, UtilMisc.<String, Object>toMap("data", "{}"), null, false, RESPONSE_JSON,  RESPONSE_JSON);
		if(UtilValidate.isNotEmpty(response)) {
			Debug.logError("Fulfillments##: " + response, module);
			ArrayList<Object> jsonList = new Gson().fromJson(response, ArrayList.class);
			for(int i = 0; i < jsonList.size(); i++) {
				Map orderData = (Map) jsonList.get(i);
				String nsOrderId = (String) orderData.get("orderid");
				String nsProductId = (String) orderData.get("productid");
				String nsSequenceNumber = UtilValidate.isNotEmpty(orderData.get("sequencenum")) ? UtilFormatOut.formatPaddedNumber(Long.valueOf((String) orderData.get("sequencenum")), 5) : "M" + i;
				String nsTrackingNumber = (String) orderData.get("tracking");

				if(UtilValidate.isNotEmpty(nsTrackingNumber)) {
					String[] trackingNumberList = nsTrackingNumber.split("<BR>");
					nsTrackingNumber = trackingNumberList[0];
				} else {
					nsTrackingNumber = null; //shipped without tracking number
				}

				if(orderAndTracking.containsKey(nsOrderId)) {
					Map<String, String> tempData = orderAndTracking.get(nsOrderId);
					tempData.put(nsSequenceNumber + "|" + nsProductId, nsTrackingNumber);
					orderAndTracking.put(nsOrderId, tempData);
				} else {
					orderAndTracking.put(nsOrderId, UtilMisc.<String, String>toMap(nsSequenceNumber + "|" + nsProductId, nsTrackingNumber));
				}
			}
		}

		return orderAndTracking;
	}

	/**
	 * Get all shipped orders for today
	 */
	public static Map<String, String> getItemWorkOrder() throws UnsupportedEncodingException, MalformedURLException, IOException, Exception {
		Map<String, String> workOrderList = new HashMap<>();
		String response = HTTPHelper.getURL(methodCall.get("getItemWorkOrder"), getMethodType(methodCall.get("getItemWorkOrder")), getOAuthParams("getItemWorkOrder"), null, UtilMisc.<String, Object>toMap("data", "{}"), null, false, RESPONSE_JSON,  RESPONSE_JSON);
		if(UtilValidate.isNotEmpty(response)) {
			ArrayList<Object> jsonList = new Gson().fromJson(response, ArrayList.class);
			for(int i = 0; i < jsonList.size(); i++) {
				Map orderData = (Map) jsonList.get(i);
				String nsProductId = (String) orderData.get("productid");
				String nsWorkOrderId = (String) orderData.get("workorderid");

				if(UtilValidate.isNotEmpty(nsWorkOrderId) && UtilValidate.isNotEmpty(nsWorkOrderId)) {
					workOrderList.put(nsProductId, nsWorkOrderId);
				}
			}
		}

		return workOrderList;
	}

	/**
	 * Helper method to return the product Id corresponding to the given netsuite workOrderId
	 * TODO: Need to optimize the NetSuite endpoint, just to do a single WO lookup.
	 */
	public static String getWorkOrderItem(String workOrderId) throws UnsupportedEncodingException, MalformedURLException, IOException, Exception {
		Map<String, String> workOrderList = new HashMap<>();
		String response = HTTPHelper.getURL(methodCall.get("getItemWorkOrder"), getMethodType(methodCall.get("getItemWorkOrder")), getOAuthParams("getItemWorkOrder"), null, UtilMisc.<String, Object>toMap("data", "{}"), null, false, RESPONSE_JSON,  RESPONSE_JSON);
		if(UtilValidate.isNotEmpty(response)) {
			ArrayList<Object> jsonList = new Gson().fromJson(response, ArrayList.class);
			for(int i = 0; i < jsonList.size(); i++) {
				Map orderData = (Map) jsonList.get(i);
				String nsProductId = (String) orderData.get("productid");
				String nsWorkOrderId = (String) orderData.get("workorderid");

				if(UtilValidate.isNotEmpty(nsWorkOrderId) && UtilValidate.isNotEmpty(nsWorkOrderId) && nsWorkOrderId.equals(workOrderId)) {
					return nsProductId;
				}
			}
		}

		return "";
	}

	/**
	 * Get inventory from netsuite
	 */
	public static Map<String, Map<String, Object>> getInventoryFromNetsuite() throws UnsupportedEncodingException, MalformedURLException, IOException, NumberFormatException, Exception {
		Map<String, Map<String, Object>> productInventory = new HashMap<>();
		String response = HTTPHelper.getURL(methodCall.get("getInventory"), getMethodType(methodCall.get("getInventory")), getOAuthParams("getInventory"), null, UtilMisc.<String, Object>toMap("data", "{}"), null, false, RESPONSE_JSON,  RESPONSE_JSON);
		if(UtilValidate.isNotEmpty(response)) {
			ArrayList<Object> jsonList = new Gson().fromJson(response, ArrayList.class);
			for(int i = 0; i < jsonList.size(); i++) {
				Map productData = (Map) jsonList.get(i);
				String productId = ((String) productData.get("itemid")).replaceAll(".*\\s+\\:+\\s+(.*)$", "$1");

				String quantity = "0";
				if(UtilValidate.isNotEmpty((String) productData.get("qty")) && NumberUtils.isNumber((String) productData.get("qty")) && ((String) productData.get("qty")).contains(".")) {
					quantity = ((String) productData.get("qty")).substring(0, ((String) productData.get("qty")).indexOf("."));
					if(UtilValidate.isEmpty(quantity)) {
						quantity = "0";
					}
				} else if(UtilValidate.isNotEmpty((String) productData.get("qty")) && NumberUtils.isNumber((String) productData.get("qty"))) {
					quantity = (String) productData.get("qty");
				} else {
					quantity = "0";
				}

				String location = "1";
				if(UtilValidate.isNotEmpty((String) productData.get("location"))) {
					location = (String) productData.get("location");
				} else {
					location = "2";
				}

				String dropshipOnly = (UtilValidate.isNotEmpty(productData.get("dropshipOnly")) && productData.get("dropshipOnly").equals("T") ? "Y" : "N");
				Long reorderMultiple = (UtilValidate.isNotEmpty(productData.get("reorderMultiple")) ? Long.valueOf((String) productData.get("reorderMultiple")) : null);

				productInventory.put(productId, UtilMisc.<String, Object>toMap("quantity", Long.valueOf(quantity), "location", location, "dropshipOnly", dropshipOnly, "reorderMultiple", reorderMultiple));
			}
		}

		return productInventory;
	}

	/**
	 * Returns TRUE if the method requires a POST method
	 *
	 * @param  method
	 * @return boolean
	 */
	protected static String getMethodType(String method) {
		if(method.equals(NL_URL + "/rest/roles")) {
			return "GET";
		}

		return "POST";
	}

	/**
	 * Netsuite Authorization header
	 */
	protected static String getAuthString() {
		StringBuilder authString = new StringBuilder();
		authString.append("NLAuth ")
					.append("nlauth_account").append("=").append(NLAUTH_ACCOUNT).append(", ")
					.append("nlauth_email").append("=").append(NLAUTH_EMAIL).append(", ")
					.append("nlauth_signature").append("=").append(NLAUTH_SIGNATURE).append(", ")
					.append("nlauth_role").append("=").append(NLAUTH_ROLE);
		return authString.toString();
	}

	/**
	* method takes a shipping address GenericValue and updates the address1 and address2
	**/
	private static Map formatShippingAddressForUPS(GenericValue shippingAddress) {
		Map<String, String> newAddressLine1And2 = new HashMap<>();

		String newAddress1 = null;
		String newAddress2 = null;

		if(UtilValidate.isNotEmpty(shippingAddress)) {
			//we need to make sure that address1 IS NOT empty and that address2 IS empty
			if(UtilValidate.isNotEmpty(shippingAddress.get("address1")) && UtilValidate.isEmpty(shippingAddress.get("address2"))) {
				String address1 = shippingAddress.getString("address1").toLowerCase();
				if(address1.contains(" ste")) {
					//for this we have to make sure its not followed by a letter
					String tempRemainingAddress = (address1.substring(address1.lastIndexOf(" ste"))).trim();
					if(tempRemainingAddress.length() > 3) {
						//we are now getting the 4th char, if its not a letter, lets separate
						tempRemainingAddress = tempRemainingAddress.substring(3,4);
						if (!tempRemainingAddress.matches("^[a-z]")) {
							newAddress1 = (shippingAddress.getString("address1")).substring(0, address1.lastIndexOf(" ste"));
							newAddress2 = (shippingAddress.getString("address1")).substring(address1.lastIndexOf(" ste"));
						}
					}
				} else if(address1.contains(" suite")) {
					newAddress1 = (shippingAddress.getString("address1")).substring(0, address1.lastIndexOf(" suite"));
					newAddress2 = (shippingAddress.getString("address1")).substring(address1.lastIndexOf(" suite"));
				} else if(address1.contains(" space")) {
					newAddress1 = (shippingAddress.getString("address1")).substring(0, address1.lastIndexOf(" space"));
					newAddress2 = (shippingAddress.getString("address1")).substring(address1.lastIndexOf(" space"));
				} else if(address1.contains(" apt")) {
					newAddress1 = (shippingAddress.getString("address1")).substring(0, address1.lastIndexOf(" apt"));
					newAddress2 = (shippingAddress.getString("address1")).substring(address1.lastIndexOf(" apt"));
				} else if(address1.contains("#") && !address1.contains("box")) { //contains # sign but is not a pobox address
					newAddress1 = (shippingAddress.getString("address1")).substring(0, address1.lastIndexOf("#"));
					newAddress2 = (shippingAddress.getString("address1")).substring(address1.lastIndexOf("#"));
				}
			}
		}

		if(UtilValidate.isNotEmpty(newAddress1) && UtilValidate.isNotEmpty(newAddress2)) {
			newAddressLine1And2.put("newAddress1", newAddress1.trim());
			newAddressLine1And2.put("newAddress2", newAddress2.trim());
		}
		return newAddressLine1And2;
	}

	private static Map<String, Object> getCustomerData(Delegator delegator, Map<String, Object> orderData, boolean isInternationalOrder, String partyId) throws GenericEntityException {
		Map<String, Object> customerData = new HashMap<>();

		GenericValue billingAddr = (GenericValue) orderData.get("billingAddress");
		Map<String, Object> billingAddress = getAddress(delegator, billingAddr, false);

		String firstName = null;
		String lastName = null;
		if(UtilValidate.isNotEmpty(billingAddr.getString("toName")) && billingAddr.getString("toName").contains(" ")) {
			firstName = billingAddr.getString("toName").substring(0, billingAddr.getString("toName").indexOf(" "));
			lastName = billingAddr.getString("toName").substring(billingAddr.getString("toName").indexOf(" ")+1);
		}

		customerData.put("webSiteId", EnvUtil.getWebsiteId(((GenericValue) orderData.get("orderHeader")).getString("salesChannelEnumId")));
		customerData.put("firstname", firstName);
		customerData.put("lastname", lastName);
		customerData.put("companyName", UtilValidate.isNotEmpty(billingAddr.get("companyName")) ? billingAddr.getString("companyName") : null);
		customerData.put("resellerId", (PartyHelper.getParty(delegator, partyId)).getString("resellerId"));

		billingAddress.put("firstname", firstName);
		billingAddress.put("lastname", lastName);
		billingAddress.put("companyName", UtilValidate.isNotEmpty(billingAddr.get("companyName")) ? billingAddr.getString("companyName") : null);
		customerData.put("billing", billingAddress);

		GenericValue shippingAddr = (GenericValue) orderData.get("shippingAddress");
		Map<String, Object> shippingAddress = getAddress(delegator, shippingAddr, true);

		if(isInternationalOrder) {
			shippingAddress = getAddress(delegator, delegator.makeValue("PostalAddress", IParcelEvents.iPAddress), true);
			firstName = "Envelopes.com";
			lastName = "i-parcel";
		} else {
			firstName = null;
			lastName = null;
			if(UtilValidate.isNotEmpty(shippingAddr.getString("toName")) && shippingAddr.getString("toName").contains(" ")) {
				firstName = shippingAddr.getString("toName").substring(0, shippingAddr.getString("toName").indexOf(" "));
				lastName = shippingAddr.getString("toName").substring(shippingAddr.getString("toName").indexOf(" ")+1);
			} else if(UtilValidate.isNotEmpty(shippingAddr.getString("toName"))) {
				firstName = shippingAddr.getString("toName");
			}

			shippingAddress.put("companyName", UtilValidate.isNotEmpty(shippingAddr.get("companyName")) ? shippingAddr.getString("companyName") : null);
		}

		shippingAddress.put("firstname", firstName);
		shippingAddress.put("lastname", lastName);
		customerData.put("shipping", shippingAddress);

		return customerData;
	}

	public static Map<String, Object> getPerson(GenericValue userLogin, GenericValue person) {
		Map<String, Object> data = new HashMap<>();
		data.put("firstname", person.getString("firstName"));
		data.put("lastname", person.getString("lastName"));
		data.put("email", userLogin.getString("userLoginId"));
		data.put("partyId", userLogin.getString("partyId"));

		return data;
	}

	private static Map<String, Object> getAddress(Delegator delegator, GenericValue addressGV, boolean isShippingAddress) throws GenericEntityException {
		Map<String, Object> address = new HashMap<>();

		Map<String, String> formatShippingAddressForUPS = new HashMap<>();
		if(isShippingAddress) {
			formatShippingAddressForUPS = formatShippingAddressForUPS(addressGV);
		}

		if(UtilValidate.isNotEmpty(formatShippingAddressForUPS) && UtilValidate.isNotEmpty(formatShippingAddressForUPS.get("newAddress1")) && UtilValidate.isNotEmpty(formatShippingAddressForUPS.get("newAddress2"))) {
			address.put("address1", formatShippingAddressForUPS.get("newAddress1"));
			address.put("address2", formatShippingAddressForUPS.get("newAddress2"));
			address.put("city", addressGV.getString("city"));
		} else {
			address.put("address1", addressGV.get("address1"));
			address.put("address2", addressGV.get("address2"));
			address.put("city", addressGV.getString("city"));
		}

		String stateProvinceGeoCode = null;
		String stateProvinceGeoId = addressGV.getString("stateProvinceGeoId");
		if(UtilValidate.isNotEmpty(stateProvinceGeoId)) {
			GenericValue gvSProvinceGeoId = delegator.findOne("Geo", UtilMisc.toMap("geoId", stateProvinceGeoId), true);
			if(UtilValidate.isNotEmpty(gvSProvinceGeoId)) {
				stateProvinceGeoCode = gvSProvinceGeoId.getString("geoCode");
			}
		}

		String countryGeoId = addressGV.getString("countryGeoId");
		if (UtilValidate.isEmpty(countryGeoId)) {
			if(OrderHelper.isUSTerritory(addressGV)) {
				countryGeoId = addressGV.getString("stateProvinceGeoId");
			} else if(ShippingHelper.isCanadianPostalCode(delegator, addressGV.getString("postalCode"))) {
				countryGeoId = "CAN";
			} else {
				countryGeoId = "USA";
			}
		} else if(OrderHelper.isUSTerritory(addressGV)) {
			countryGeoId = addressGV.getString("stateProvinceGeoId");
		}

		String countryGeoName = null;
		if(UtilValidate.isNotEmpty(countryGeoId)) {
			GenericValue gvSCountryGeoId = delegator.findOne("Geo", UtilMisc.toMap("geoId", countryGeoId), true);
			if(UtilValidate.isNotEmpty(gvSCountryGeoId)) {
				countryGeoName = gvSCountryGeoId.getString("geoName");
			}
		}

		if(stateProvinceGeoCode != null) {
			address.put("state", stateProvinceGeoCode);
		} else {
			address.put("state", stateProvinceGeoId);
		}
		address.put("zip", addressGV.getString("postalCode"));

		if(UtilValidate.isNotEmpty(countryGeoName)) {
			address.put("country", AeCountryMap.get(countryGeoName));
		} else {
			if(UtilValidate.isNotEmpty(countryGeoId)) {
				address.put("country", AeCountryMap.get(countryGeoId));
			} else {
				address.put("country", "US");
			}
		}

		return address;
	}

	private static Map<String, Object> getPaymentData(Delegator delegator, OrderReadHelper orh, Map<String, Object> orderData) throws GenericEntityException {
		Map<String, Object> payment = new HashMap<>();

		List<Map> paymentData = OrderHelper.getPaymentData(orh, delegator, orh.getOrderId());
		if(UtilValidate.isNotEmpty(paymentData)) {
			GenericValue billingAddr = (GenericValue) orderData.get("billingAddress");
			Map<String, Object> paymentInfo = paymentData.get(0);
			if("CREDIT_CARD".equals((String) paymentInfo.get("paymentMethodTypeId"))) {
				payment.put("ccname", billingAddr.get("toName"));
				payment.put("ccstreet", billingAddr.get("address1"));
				payment.put("cczipcode", billingAddr.get("postalCode"));
				payment.put("ccexpiredate", (String) paymentInfo.get("expireDate"));
				payment.put("ccnumber", (String) paymentInfo.get("cardNumberUnMasked"));

				List<Map> responses = (List<Map>) paymentInfo.get("responseData");
				if(UtilValidate.isNotEmpty(responses)) {
					Map responseData = responses.get(0);
					payment.put("referenceNumber", (String) responseData.get("referenceNum"));
					payment.put("ccsecuritycode", (String) responseData.get("gatewayCode"));
					payment.put("authCode", (String) responseData.get("gatewayCode"));
					payment.put("ccapproved", "Authorized".equalsIgnoreCase((String) paymentInfo.get("statusId")) || "Settled".equalsIgnoreCase((String) paymentInfo.get("statusId")));
					payment.put("creditCardToken", (UtilValidate.isNotEmpty(responseData.get("creditCardToken")) ? responseData.get("creditCardToken") : null));
					payment.put("transactionId", (UtilValidate.isNotEmpty(responseData.get("transactionId")) ? responseData.get("transactionId") : null));
					payment.put("paymentmethod", netsuitePaymentMethod((String) responseData.get("cardType")));
				}

				if (UtilValidate.isEmpty(responses) || UtilValidate.isEmpty(payment.get("paymentmethod"))) {
					payment.put("paymentmethod", netsuitePaymentMethod((String) paymentInfo.get("cardTypeId")));
				}
			} else if("PERSONAL_CHECK".equals((String) paymentInfo.get("paymentMethodTypeId")) || "EXT_OFFLINE".equals((String) paymentInfo.get("paymentMethodTypeId")) || "EXT_IPARCEL".equals((String) paymentInfo.get("paymentMethodTypeId")) || "EXT_NET30".equals((String) paymentInfo.get("paymentMethodTypeId"))) {
				return null;
			} else if("EXT_CLOSED".equals((String) paymentInfo.get("paymentMethodTypeId"))) {
				orderData.put("closed", true);
				return null;
			} else if("EXT_AMAZON".equals((String) paymentInfo.get("paymentMethodTypeId")) || "EXT_PAYPAL_CHECKOUT".equals((String) paymentInfo.get("paymentMethodTypeId"))) {
				payment.put("ccname", billingAddr.get("toName"));
				payment.put("ccstreet", billingAddr.get("address1"));
				payment.put("cczipcode", billingAddr.get("postalCode"));
				payment.put("ccnumber", "4111111111111111");
				payment.put("ccexpiredate", "01/2030");
				payment.put("paymentmethod", netsuitePaymentMethod((String) paymentInfo.get("paymentMethodTypeId")));

				List<Map> responses = (List<Map>) paymentInfo.get("responseData");
				if(UtilValidate.isNotEmpty(responses)) {
					Map responseData = responses.get(0);
					payment.put("creditCardToken", (UtilValidate.isNotEmpty(responseData.get("creditCardToken")) ? responseData.get("creditCardToken") : null));
					payment.put("transactionId", (UtilValidate.isNotEmpty(responseData.get("transactionId")) ? responseData.get("transactionId") : null));
				}
			}
		}

		return payment;
	}

	/**
	 * Translation for payment method in netsuite
	 * @param paymentMethodTypeId
	 * @return
	 */
	private static String netsuitePaymentMethod(String paymentMethodTypeId) {
		if("CCT_VISA".equals(paymentMethodTypeId)) {
			return "5";
		} else if("CCT_AMERICANEXPRESS".equals(paymentMethodTypeId)) {
			return "6";
		} else if("CCT_MASTERCARD".equals(paymentMethodTypeId)) {
			return "4";
		} else if("CCT_DISCOVER".equals(paymentMethodTypeId)) {
			return "3";
		} else if("EXT_AMAZON".equals(paymentMethodTypeId)) {
			return "11";
		} else if("EXT_PAYPAL_CHECKOUT".equals(paymentMethodTypeId)) {
			return "9";
		} else if("Visa".equals(paymentMethodTypeId)) {
			return "14";
		} else if("American Express".equals(paymentMethodTypeId)) {
			return "18";
		} else if("Discover".equals(paymentMethodTypeId)) {
			return "15";
		} else if("JCB".equals(paymentMethodTypeId)) {
			return "16";
		} else if("MasterCard".equals(paymentMethodTypeId)) {
			return "17";
		}

		return null;
	}

	/**
	 * Get the tax rates
	 * @param delegator
	 * @param shippingAddress
	 * @return
	 * @throws GenericEntityException
	 * @throws Exception
	 */
	private static Map<String, String> getTaxRate(Delegator delegator, GenericValue shippingAddress) throws Exception {
		return getTaxRate(delegator, shippingAddress.getAllFields());
	}
	private static Map<String, String> getTaxRate(Delegator delegator, Map<String, Object> shippingAddress) throws Exception {
		Map<String, String> taxData = new HashMap<>();
		if(UtilValidate.isNotEmpty(shippingAddress)) {
			String shipZip = (String) shippingAddress.get("postalCode");
			if(shipZip == null) {
				shipZip = (String) shippingAddress.get("zip");
			}
			if(shipZip.contains("-")) {
				shipZip = shipZip.substring(0, shipZip.indexOf("-"));
			}

			List<GenericValue> zipSalesTaxLookupList = delegator.findByAnd("ZipSalesTaxLookup", UtilMisc.toMap("zipCode", shipZip), null, true);
			if(UtilValidate.isNotEmpty(zipSalesTaxLookupList)) {
				GenericValue taxInfo = EntityUtil.getFirst(zipSalesTaxLookupList);
				String taxGroupId = taxInfo.getString("groupId");
				String taxRateString = taxInfo.getString("comboSalesTax");
				BigDecimal tr = new BigDecimal(taxRateString);
				tr = tr.multiply(new BigDecimal("100"));
				taxRateString = tr.toString();
				taxData.put("taxRate", taxRateString);
				taxData.put("taxItem", taxGroupId);
			} else {
				taxData.put("taxRate", "0.0");
				taxData.put("taxItem", UtilProperties.getPropertyValue("envelopes", "suiteTalk.taxCode.Not_Taxable"));
			}
		}

		return taxData;
	}

	/**
	 * Get the list of order items
	 * @param delegator
	 * @param items
	 * @param orh
	 * @return
	 * @throws GenericEntityException
	 * @throws Exception
	 */
	private static List<Map> getOrderItems(Delegator delegator, List<Map> items, OrderReadHelper orh) throws GenericEntityException, Exception {
		List<Map> itemList = new ArrayList<Map>();
		Integer linerQuantity = 0;

		for(Iterator i = items.iterator(); i.hasNext();) {
			Map item = (Map) i.next();
			GenericValue product = (GenericValue) item.get("product");
			GenericValue orderItem = (GenericValue) item.get("item");
			List<GenericValue> orderItemAttrs = OrderHelper.getOrderItemAttribute(delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId"));
			String pricingRequest = getPricingRequest(orderItemAttrs);

			Map<String, Object> itemData = new HashMap<>();
			StringBuilder name = new StringBuilder((product.getString("productId").equalsIgnoreCase(CUSTOM_PRODUCT)) || (product.getString("productId").equalsIgnoreCase(SAMPLE_PRODUCT)) ? orderItem.getString("itemDescription") : product.getString("internalName"));

			Map<String, String> colorMap = ProductHelper.getProductFeatures(delegator, product, UtilMisc.<String>toList("COLOR"), true);
			if(UtilValidate.isNotEmpty(colorMap)) {
				name.append(" - ").append(colorMap.get(colorMap.keySet().toArray()[0]));
			}

			String artworkSource = null;
			BigDecimal numberOfAddresses = new BigDecimal(Integer.toString(OrderHelper.getNumberOfAddresses(orderItemAttrs)));
			if(OrderHelper.isItemPrinted(delegator, null, null, orderItem)) {
				name.append(getColorCount(orderItemAttrs));
				artworkSource = orderItem.getString("artworkSource");
			}

			String s7TemplateId = null;
			String plateId = null;
			String pressman = null;
			BigDecimal approvalDuration = null;
			BigDecimal jobDuration = null;
			if(OrderHelper.isItemPrinted(delegator, null, null, orderItem)) {
				GenericValue artwork = OrderHelper.getOrderItemArtwork(delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId"));
				if(artwork != null) {
					plateId = artwork.getString("printPlateId");
					pressman = artwork.getString("pressMan");

					if(UtilValidate.isNotEmpty(artwork.get("jobStartTime")) && UtilValidate.isNotEmpty(artwork.get("jobFinishTime"))) {
						jobDuration = new BigDecimal(Long.valueOf(EnvUtil.getMinutesBetweenDates(artwork.getTimestamp("jobStartTime"), artwork.getTimestamp("jobFinishTime"))).toString());
					}

					if(UtilValidate.isNotEmpty(artwork.get("approvalStartTime")) && UtilValidate.isNotEmpty(artwork.get("approvalFinishTime"))) {
						approvalDuration = new BigDecimal(Long.valueOf(EnvUtil.getMinutesBetweenDates(artwork.getTimestamp("approvalStartTime"), artwork.getTimestamp("approvalFinishTime"))).toString());
					}

					if(UtilValidate.isNotEmpty(artwork.getString("scene7DesignId"))) {
						GenericValue s7Design = Scene7Helper.getScene7Design(delegator, artwork.getString("scene7DesignId"));
						if(UtilValidate.isNotEmpty(s7Design.getString("data"))) {
							s7TemplateId = Scene7Helper.getScene7TemplateId(new Gson().fromJson(s7Design.getString("data"), HashMap.class));
						}
					}
				}
			}

			if(!OrderHelper.isItemSample(orderItem, product)) {
				name.append(" - ").append(EnvUtil.convertBigDecimal(orderItem.getBigDecimal("quantity"), true));
				name.append(("ENVELOPE".equalsIgnoreCase(product.getString("productTypeId"))) ? " Envelopes" : "");
			}

			String outsourceVendor = null;
			String jobId = null;
			String stockIndicator = null;
			String stockError = null;
			String stockErrorDate = null;
			String statusCode = null;
			String outsourcedBy = null;
			Boolean outsourced = null;
			Timestamp outsourcedDate = null;
			Boolean syracused = null;
			Timestamp syracusedDate = null;

			if("ART_SYRACUSE".equalsIgnoreCase(orderItem.getString("statusId"))) {
				GenericValue oiStatus = EntityQuery.use(delegator).from("OrderStatus").where("orderId", orderItem.getString("orderId"), "orderItemSeqId", orderItem.getString("orderItemSeqId"), "statusId", "ART_SYRACUSE").orderBy("statusDatetime DESC").queryFirst();
				if(oiStatus != null) {
					syracused = true;
					syracusedDate = oiStatus.getTimestamp("createdStamp");
				}
			}
			if("ART_OUTSOURCED".equalsIgnoreCase(orderItem.getString("statusId"))) {
				GenericValue vendorOrder = EntityQuery.use(delegator).from("VendorOrder").where("orderId", orderItem.getString("orderId"), "orderItemSeqId", orderItem.getString("orderItemSeqId")).queryOne();
				GenericValue oiStatus = EntityQuery.use(delegator).from("OrderStatus").where("orderId", orderItem.getString("orderId"), "orderItemSeqId", orderItem.getString("orderItemSeqId"), "statusId", "ART_OUTSOURCED").orderBy("statusDatetime DESC").queryFirst();
				if(vendorOrder != null) {
					outsourceVendor = vendorOrder.getString("partyId");
					jobId = vendorOrder.getString("jobId");
					stockIndicator = vendorOrder.getString("stockIndicator");
					stockError = vendorOrder.getString("errorCode");
					stockErrorDate = MDYTA.format(vendorOrder.getTimestamp("lastUpdatedStamp"));
					statusCode = vendorOrder.getString("statusCode");
				}
				if(oiStatus != null && UtilValidate.isNotEmpty(oiStatus.getString("statusUserLogin"))) {
					outsourced = true;
					outsourcedDate = oiStatus.getTimestamp("createdStamp");
					outsourcedBy = oiStatus.getString("statusUserLogin");
				}
			}

			String prodDescription = name.toString();
			String webSiteId = EnvUtil.getWebsiteId(orh.getOrderHeader().getString("salesChannelEnumId"));
			//if("folders".equalsIgnoreCase(webSiteId) && "FOLDER".equalsIgnoreCase(product.getString("productTypeId"))) {
			//	itemData.put("sku", orderItem.getString("productId"));
			//} else {
				itemData.put("sku", OrderHelper.isItemSample(orderItem, product) ? "SAMPLE" : orderItem.getString("productId"));
				prodDescription = OrderHelper.isItemSample(orderItem, product) ? prodDescription + ", " + orderItem.getString("productId") : prodDescription;
			//}

			if(UtilValidate.isNotEmpty(orderItem.getString("referenceQuoteItemSeqId"))) {
				Map<String, Object> quoteData = CalculatorHelper.deserializedQuoteSummary(delegator, null, orderItem.getString("referenceQuoteItemSeqId"));
				prodDescription = (UtilValidate.isNotEmpty(quoteData.get("product"))) ? ((GenericValue) quoteData.get("product")).getString("productName") + " - " + quoteData.get("material") : (String) quoteData.get("productName");
				itemData.put("name", prodDescription);
				itemData.put("printOptions", quoteData.get("printOptions"));
				itemData.put("addOnOptions", quoteData.get("addOnOptions"));
				itemData.put("sku", quoteData.get("netsuiteSKU"));
			} else if(UtilValidate.isNotEmpty(pricingRequest)) {
				Map priceSummary = CalculatorHelper.deserializedPriceSummary(delegator, product, pricingRequest);
				prodDescription = orderItem.getString("itemDescription");
				itemData.put("printOptions", priceSummary.get("printOptions"));
				itemData.put("addOnOptions", priceSummary.get("addOnOptions"));
				itemData.put("sku", priceSummary.get("netsuiteSKU"));
			}

			itemData.put("name", prodDescription);
			itemData.put("seqId", EnvUtil.removeChar("0", orderItem.getString("orderItemSeqId"), true, false, false));
			itemData.put("quantity", orderItem.getBigDecimal("quantity"));
			itemData.put("amount", OrderReadHelper.getOrderItemSubTotal(orderItem, null)); //this is item total without any adjustments, adjustments are added later at line item level for netsuite
			itemData.put("isRush", OrderHelper.isRush(delegator, null, null, orderItem));
			itemData.put("s7TemplateId", s7TemplateId);
			itemData.put("artworkSource", artworkSource);
			itemData.put("plateId", plateId);
			itemData.put("outsourced", outsourced);
			itemData.put("pressMan", pressman);
			itemData.put("addresses", numberOfAddresses);
			itemData.put("jobDuration", jobDuration);
			itemData.put("approvalDuration", approvalDuration);
			itemData.put("outsourceVendor", outsourceVendor);
			itemData.put("outsourcedBy", outsourcedBy);
			itemData.put("outsourcedDate", outsourcedDate != null ? MDYTA.format(outsourcedDate) : null);
			itemData.put("syracused", syracused);
			itemData.put("syracusedDate", syracusedDate != null ? MDYTA.format(syracusedDate) : null);
			itemData.put("jobId", jobId);
			itemData.put("stockIndicator", stockIndicator);
			itemData.put("stockError", stockError);
			itemData.put("stockErrorDate", stockErrorDate);
			itemData.put("statusCode", statusCode);
			itemData.put("quoteId", orderItem.getString("referenceQuoteId"));
			itemData.put("quoteItemSeqId", orderItem.getString("referenceQuoteItemSeqId"));
			itemData.put("isPrinted", OrderHelper.isItemPrinted(delegator, null, null, orderItem));

			Timestamp approvalDate = OrderHelper.getStatusChangeTimeForOrderItem(delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId"), "ART_PROOF_APPROVED");
			itemData.put("approvalDate", approvalDate != null ? MDYTA.format(approvalDate) : null);

			Timestamp printDate = OrderHelper.getStatusChangeTimeForOrderItem(delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId"), "ART_PRINTED");
			itemData.put("printDate", printDate != null ? MDYTA.format(printDate) : null);

			itemData.put("dueDate", UtilValidate.isNotEmpty(orderItem.getTimestamp("dueDate")) ? MDYTA.format(orderItem.getTimestamp("dueDate")) : null);
			itemList.add(itemData);
		}

		for (Map<String, Object> itemAddon : OrderHelper.getItemAddonList(delegator, items)) {
			itemList.add(itemAddon);
		}

		return itemList;
	}

	/**
	 * Here we will modify the list of items and upcharge them with iparcel fees
	 * Then we will add another (service) item to decrease the total iparcel fees
	 * @param orderItems
	 */
	private static List<Map> getInternationalFees(List<Map> orderItems) {
		List<Map> newOrderItemList = new ArrayList<>();
		Iterator<Map> orderItemIter = orderItems.iterator();

		while(orderItemIter.hasNext()) {
			Map item = orderItemIter.next();
			BigDecimal amount = (BigDecimal) item.get("amount");
			BigDecimal upcharge = amount.multiply(IPARCEL_FEE.add(IPARCEL_ADMIN_FEE)).setScale(ENV_SCALE_P, ENV_ROUNDING);
			newOrderItemList.add(item);

			Map<String, Object> totalServiceDiscount = new HashMap<>();
			totalServiceDiscount.put("name", "iParcel Fees");
			totalServiceDiscount.put("sku", "iParcel Fees");
			totalServiceDiscount.put("quantity", "1");
			totalServiceDiscount.put("amount", upcharge.multiply(new BigDecimal("-1")).setScale(ENV_SCALE_P, ENV_ROUNDING));
			newOrderItemList.add(totalServiceDiscount);
		}

		return newOrderItemList;
	}

	/**
	 * Get any promotion level discounts
	 * @param delegator
	 * @param orderAdjustments
	 * @param orh
	 * @param onlyFreeShip
	 * @return
	 * @throws GenericEntityException
	 */
	private static List<Map> getAdjustments(Delegator delegator, List<GenericValue> orderAdjustments, OrderReadHelper orh, boolean onlyFreeShip) throws GenericEntityException {
		// trade discount and sample coupons
		List<Map> promoList = new ArrayList<Map>();
		for(GenericValue adjustment : orderAdjustments) {
			String adjustmentTypeId = adjustment.getString("orderAdjustmentTypeId");
			String promoActionEnumId = adjustment.getString("productPromoActionEnumId");
			if("PROMOTION_ADJUSTMENT".equals(adjustmentTypeId)) {
				GenericValue promotion = adjustment.getRelatedOne("ProductPromo", true);
				if(promotion.getString("promoName").equals("Nontaxable Discount")) { //ignore this, this gets set as a flag in netsuite
					continue;
				}

				if((onlyFreeShip && !"PROMO_SHIP_CHARGE".equalsIgnoreCase(promoActionEnumId)) || (!onlyFreeShip && "PROMO_SHIP_CHARGE".equalsIgnoreCase(promoActionEnumId))) {
					continue;
				}

				BigDecimal promoAmount = orh.getOrderAdjustmentTotal(adjustment);
				Map<String, Object> adjData = new HashMap<>();
				adjData.put("sku", promotion.getString("promoName"));
				adjData.put("name", promotion.getString("promoName"));
				adjData.put("quantity", 0);
				adjData.put("amount", promoAmount);
				adjData.put("rate", promoAmount);
				promoList.add(adjData);
			}
		}
		return promoList;
	}

	private static String getPricingRequest(List<GenericValue> orderItemAttrs) {
		if(UtilValidate.isNotEmpty(orderItemAttrs)) {
			for(GenericValue orderItemAttr : orderItemAttrs) {
				if("pricingRequest".equals(orderItemAttr.getString("attrName"))) {
					return orderItemAttr.getString("attrValue");
				}
			}
		}

		return null;
	}

	/*
	 * Get print color
	 */
	private static String getColorCount(List<GenericValue> orderItemAttrs) {
		String colorsFront = "0";
		String colorsBack = "0";
		String isFullBleed = "false";
		if(UtilValidate.isNotEmpty(orderItemAttrs)) {
			for(GenericValue orderItemAttr : orderItemAttrs) {
				if("colorsFront".equals(orderItemAttr.getString("attrName"))) {
					colorsFront = orderItemAttr.getString("attrValue");
				} else if("colorsBack".equals(orderItemAttr.getString("attrName"))) {
					colorsBack = orderItemAttr.getString("attrValue");
				} else if("isFullBleed".equals(orderItemAttr.getString("attrName"))) {
					isFullBleed = orderItemAttr.getString("attrValue");
				}
			}
		}

		if("true".equalsIgnoreCase(isFullBleed)) {
			return "Full Bleed";
		}

		if(Integer.valueOf(colorsFront) > Integer.valueOf(colorsBack)) {
			return " - Printed " + colorsFront + " Color";
		} else {
			return " - Printed " + colorsBack + " Color";
		}
	}

	private static String getOrderComments(List<Map> notes) {
		StringBuilder commentsText = new StringBuilder();

		for(Iterator i = notes.iterator(); i.hasNext();) {
			Map note = (Map) i.next();
			commentsText.append("By: " + (String) note.get("name") + "\n");
			commentsText.append("At: " + MDYT.format((Timestamp) note.get("createdStamp")) + "\n");
			commentsText.append((String) note.get("comment") + "\n\n");
		}

		return commentsText.toString().trim();
	}

	private static Integer getTerms(OrderReadHelper orh, boolean isCustomQuoteFoldersOrder) throws GenericEntityException {
		GenericValue iparcelPayment = EntityUtil.getFirst(EntityUtil.filterByAnd(orh.getPaymentPreferences(), UtilMisc.toMap("paymentMethodTypeId", "EXT_IPARCEL")));
		GenericValue offLinePayment = EntityUtil.getFirst(EntityUtil.filterByAnd(orh.getPaymentPreferences(), UtilMisc.toMap("paymentMethodTypeId", "EXT_OFFLINE")));
		GenericValue checkPayment = EntityUtil.getFirst(EntityUtil.filterByAnd(orh.getPaymentPreferences(), UtilMisc.toMap("paymentMethodTypeId", "PERSONAL_CHECK")));
		GenericValue net30Payment = EntityUtil.getFirst(EntityUtil.filterByAnd(orh.getPaymentPreferences(), UtilMisc.toMap("paymentMethodTypeId", "EXT_NET30")));
		GenericValue closedPayment = EntityUtil.getFirst(EntityUtil.filterByAnd(orh.getPaymentPreferences(), UtilMisc.toMap("paymentMethodTypeId", "EXT_CLOSED")));
		if(UtilValidate.isNotEmpty(iparcelPayment) || UtilValidate.isNotEmpty(offLinePayment)) {
			return 28;
		} else if (UtilValidate.isNotEmpty(net30Payment) || UtilValidate.isNotEmpty(checkPayment)) {
			return 2;
		} else if (isCustomQuoteFoldersOrder) {
			return 30;
		} else {
			return null;
		}
	}

	private static String getLoyaltyPoints(LocalDispatcher dispatcher, String partyId, Integer totalMonths) throws GenericServiceException {
		String points = "0";

		Map<String, Object> serviceContext = new HashMap<String, Object>();
		serviceContext.put("partyId", partyId);
		serviceContext.put("monthsToInclude", Integer.valueOf("12"));
		serviceContext.put("orderTypeId", "SALES_ORDER");
		serviceContext.put("statusId", "ORDER_COMPLETED");
		serviceContext.put("roleTypeId", "PLACING_CUSTOMER");
		Map<String, Object> result = dispatcher.runSync("getOrderedSummaryInformation", serviceContext);

		if(UtilValidate.isNotEmpty(result) && UtilValidate.isNotEmpty(result.get("currentPoints"))) {
			points = ((BigDecimal) result.get("totalGrandAmount")).toString();
		}
		return points;
	}

	/**
	 * Get the shipment method for the order, figure out discounted ship rates if we ship it faster
	 * @param delegator
	 * @param dispatcher
	 * @param orh
	 * @param shippingAddress
	 * @param shippingAmount
	 * @param orderAdjustments
	 * @return
	 * @throws GenericEntityException
	 * @throws GenericServiceException
	 * @throws Exception
	 */
	public static Map<String, Object> getShipMethod(Delegator delegator, LocalDispatcher dispatcher, OrderReadHelper orh, GenericValue shippingAddress, BigDecimal shippingAmount, List<GenericValue> orderAdjustments) throws GenericEntityException, GenericServiceException, Exception {
		Map<String, Object> shipMethod = new HashMap<>();

		String postalCode = shippingAddress.getString("postalCode");
		GenericValue shipmentMethodType = null;
		String shipmentMethodTypeId = null;
		String shipmentMethodDesc = null;
		String shipmentMethodGenericDesc = null;
		String shipGroupSeqId = null;

		String costEffectiveShippingMethodDesc = null;
		BigDecimal costEffectiveShippingCost = null;
		String costEffectiveShipmentMethodTypeId = null;
		String costEffectiveShippingNote = null;
		boolean calcStandardizedShipping = "true".equalsIgnoreCase(STANDARDIZED_SHIPPING);

		List<GenericValue> orderItemShipGroupList = delegator.findByAnd("OrderItemShipGroup", UtilMisc.toMap("orderId", orh.getOrderId()), null, false);

		if(UtilValidate.isNotEmpty(orderItemShipGroupList)) {
			Iterator orderShipGroupIter = orderItemShipGroupList.iterator();
			if (orderShipGroupIter != null && orderShipGroupIter.hasNext()) {
				GenericValue orderItemShipGroup = (GenericValue) orderShipGroupIter.next();
				shipmentMethodTypeId = orderItemShipGroup.getString("shipmentMethodTypeId");
				shipGroupSeqId = orderItemShipGroup.getString("shipGroupSeqId");
				shipmentMethodType = delegator.findOne("ShipmentMethodType", UtilMisc.toMap("shipmentMethodTypeId", shipmentMethodTypeId), true);
			}
		}

		if(shipmentMethodType != null) {
			shipmentMethodDesc = shipmentMethodType.getString("description");
			shipmentMethodGenericDesc = shipmentMethodType.getString("genericDescription");
		}

		shipMethod.put("shipNote", (!"GROUND".equalsIgnoreCase(shipmentMethodTypeId) && !"STANDARD".equalsIgnoreCase(shipmentMethodTypeId)) ? "EXPEDITED" : "");

		//now in order to see which shipping method we should actually ship the package in order to get it to the customer in the time frame
		//they wanted and the most cost effective way we need to see if its a USA or Canada order first
		boolean isCANOrder = false;
		boolean isUSAOrder = false;
		if(UtilValidate.isNotEmpty(shippingAddress.getString("countryGeoId"))) {
			if((shippingAddress.getString("countryGeoId")).equals("USA")) {
				isUSAOrder = true;
			} else if ((shippingAddress.getString("countryGeoId")).equals("CAN")) {
				isCANOrder = true;
			}
		}

		//if the description and genericDescription of a ship method is the same, that means we are not showing a generic option
		//if no generic option is shown, we will store the selected method as the actual shipping
		if(UtilValidate.isNotEmpty(shipmentMethodDesc) && UtilValidate.isNotEmpty(shipmentMethodGenericDesc) && shipmentMethodDesc.equals(shipmentMethodGenericDesc)) {
			calcStandardizedShipping = false;
		}
		//if its ground or standard, then customer chose the most cost effective method already
		if(("STANDARD").equalsIgnoreCase(shipmentMethodTypeId) || "GROUND".equalsIgnoreCase(shipmentMethodTypeId) || "FIRST_CLASS".equalsIgnoreCase(shipmentMethodTypeId) || "SMALL_FLAT_RATE".equalsIgnoreCase(shipmentMethodTypeId) || "PRIORITY".equalsIgnoreCase(shipmentMethodTypeId) || "NEXT_DAY_AIR".equalsIgnoreCase(shipmentMethodTypeId)) {
			calcStandardizedShipping = false;
		}

		//now if its a canada or usa order, lets see if we can send it via a more cost effective method (if they already havent selected the most cost effective)
		if(calcStandardizedShipping && isUSAOrder && UtilValidate.isNotEmpty(postalCode)) {
			//lets get the number of days the customer chose based on the generic description of the shipping method
			Map<String, String> shippingTimeInDays = shippingTimeInDays(shipmentMethodGenericDesc);
			String minDaysStr = shippingTimeInDays.get("min"); //this should never be null, if it is, then something is wrong with the genericDescription of ShipmentMethodType
			String maxDaysStr = shippingTimeInDays.get("max");
			if(UtilValidate.isEmpty(maxDaysStr)) {
				maxDaysStr = minDaysStr;
			}
			if(UtilValidate.isNotEmpty(minDaysStr) && UtilValidate.isNotEmpty(maxDaysStr)) {
				Long maxDays = Long.valueOf(maxDaysStr);
				List postalRow = null;
				//find a list of all shipping methods that are not of the same type that was selected and are faster or equal in transit speed
				List searchConditionList = UtilMisc.toList(
						new EntityExpr("postalCode", EntityOperator.EQUALS, postalCode),
						new EntityExpr("shipmentMethodTypeId", EntityOperator.NOT_EQUAL, shipmentMethodType.getString("shipmentMethodTypeId")),
						new EntityExpr("transitTime", EntityOperator.NOT_EQUAL, null),
						new EntityExpr("transitTime", EntityOperator.LESS_THAN_EQUAL_TO, maxDays));
				EntityConditionList searchCondition = new EntityConditionList(searchConditionList, EntityOperator.AND);
				postalRow = delegator.findList("ZonePostalCodeLookup", searchCondition, null, null, null, true);

				if(UtilValidate.isEmpty(postalRow)) {
					//if it is empty, lets try to find it based on the first 3 chars
					if(postalCode.length() > 3) {
						postalCode = postalCode.substring(0, 3);
						searchConditionList = UtilMisc.toList(
								new EntityExpr("postalCode", EntityOperator.EQUALS, postalCode),
								new EntityExpr("shipmentMethodTypeId", EntityOperator.NOT_EQUAL, shipmentMethodType.getString("shipmentMethodTypeId")),
								new EntityExpr("transitTime", EntityOperator.NOT_EQUAL, null),
								new EntityExpr("transitTime", EntityOperator.LESS_THAN_EQUAL_TO, maxDays));
						searchCondition = new EntityConditionList(searchConditionList, EntityOperator.AND);
						postalRow = delegator.findList("ZonePostalCodeLookup", searchCondition, null, null, null, false);
					}
				}

				//now lets send the faster transit list and find the ones that is the cheapest
				if(UtilValidate.isNotEmpty(postalRow)) {
					Map<String, String> postalAddress = UtilMisc.<String, String>toMap("address1", shippingAddress.getString("address1"), "address2", shippingAddress.getString("address2"), "city", shippingAddress.getString("city"), "stateProvinceGeoId", shippingAddress.getString("stateProvinceGeoId"), "countryGeoId", shippingAddress.getString("countryGeoId"), "postalCode", shippingAddress.getString("postalCode"));
					List shipmentMethodTypeIds = EntityUtil.getFieldListFromEntityList(postalRow, "shipmentMethodTypeId", true);
					Map getCostEffectiveShipMethod = getCostEffectiveShipMethod(delegator, dispatcher, orh, shipGroupSeqId, shipmentMethodTypeIds, postalAddress, isCANOrder, isUSAOrder);
					if(UtilValidate.isNotEmpty(getCostEffectiveShipMethod)) {
						costEffectiveShippingMethodDesc = (String) getCostEffectiveShipMethod.get("shipmentMethodType");
						costEffectiveShippingCost = (BigDecimal) getCostEffectiveShipMethod.get("shippingTotal");
						costEffectiveShipmentMethodTypeId = (String) getCostEffectiveShipMethod.get("shipmentMethodTypeId");
					}
				}
			}

			//if all values are present and the cost effective shipping amount is less then the customer selected shipping amount, lets use it
			if(UtilValidate.isNotEmpty(costEffectiveShippingMethodDesc) && UtilValidate.isNotEmpty(costEffectiveShippingCost) && costEffectiveShippingCost.doubleValue() >= 0 && costEffectiveShippingCost.doubleValue() < shippingAmount.doubleValue()) {
				shipMethod.put("custbody_actual_ship_cost", new Double(costEffectiveShippingCost.doubleValue()));
				shipMethod.put("shipMethod", getShippingMethod(shippingAddress.getString("stateProvinceGeoId"), costEffectiveShippingMethodDesc));
				shipMethod.put("shipMethodDesc", costEffectiveShippingMethodDesc);
				shipMethod.put("shipMethodId", costEffectiveShipmentMethodTypeId);
			}
		}

		//if we didnt find a cost effective method or its not available use user selected method
		if(!shipMethod.containsKey("shipMethod")) {
			//if the costEffectiveShippingCost cannot be calculated or should not be used, lets use the option the customer chose
			if("Free shipping via USPS".equalsIgnoreCase(shipmentMethodDesc)) {
				shipmentMethodDesc = "USPS First Class Mail";
				shippingAmount = BigDecimal.ZERO;
			}
			shipMethod.put("custbody_actual_ship_cost", new Double(shippingAmount.doubleValue()));
			shipMethod.put("shipMethod", getShippingMethod(shippingAddress.getString("stateProvinceGeoId"), shipmentMethodDesc));
			shipMethod.put("shipMethodDesc", shipmentMethodDesc);
			shipMethod.put("shipMethodId", shipmentMethodTypeId);
		}

		//if we can send it USPS Parcel do so, but only change for ground shipments
		if(!calcStandardizedShipping && isUSAOrder && "GROUND".equalsIgnoreCase(shipmentMethodTypeId) && isFirstClassMailable(delegator, orh.getOrderItems(), shipmentMethodTypeId)) {
			shipMethod.put("custbody_actual_ship_cost", BigDecimal.ZERO);
			shipMethod.put("shipMethod", getShippingMethod(shippingAddress.getString("stateProvinceGeoId"), "USPS First Class Mail"));
			shipMethod.put("shipMethodDesc", "USPS First Class Mail");
			shipMethod.put("shipNote", "USPS FCM PARCEL");
			shipMethod.put("shipMethodId", shipmentMethodTypeId);
		}

		//store the chosen shipping type as separate data in custom field
		if(shipmentMethodType != null) {
			shipMethod.put("custbody_customer_ship_via", shipmentMethodGenericDesc);
		}

		List<Map> shipAdjustments = getAdjustments(delegator, orderAdjustments, orh, true);
		if(UtilValidate.isNotEmpty(shipAdjustments)) {
			for(Map shipAdjustment : shipAdjustments) {
				shippingAmount = shippingAmount.add((BigDecimal) shipAdjustment.get("amount"));
			}
		}

		shipMethod.put("shipCost", new Double(shippingAmount.doubleValue()));

		return shipMethod;
	}

	/**
	* method that gets the most cost effective shipping method available for an order
	**/
	private static Map<String, Object> getCostEffectiveShipMethod(Delegator delegator, LocalDispatcher dispatcher, OrderReadHelper orh, String shipGroupSeqId, List shipmentMethodTypeIds, Map<String, String> postalAddress, boolean isCANOrder, boolean isUSAOrder) throws GenericEntityException, GenericServiceException, Exception {
		Map<String, Object> costEffectiveShipMethod = new HashMap<String, Object>();

		String newShipMethodTypeId = null;
		BigDecimal newShippingTotal = null;

		ArrayList<String> availableShipMethods = new ArrayList<String>();
		if(UtilValidate.isEmpty(shipGroupSeqId)) {
			shipGroupSeqId = "00001";
		}

		if(isCANOrder) {
			availableShipMethods.add("STANDARD");
			availableShipMethods.add("WORLDWIDE_EXPR");
			availableShipMethods.add("WORLDWIDE_EXPTD");
		} else if(isUSAOrder) {
			//availableShipMethods.add("PRIORITY");
			availableShipMethods.add("GROUND");
			availableShipMethods.add("THREE_DAY_SELECT");
			availableShipMethods.add("SECOND_DAY_AIR");
			availableShipMethods.add("NEXT_DAY_SAVER");
			//availableShipMethods.add("NEXT_DAY_AIR");
		}

		//lets loop through the available shipping methods that we can try, then get the lowest value
		if(UtilValidate.isNotEmpty(shipmentMethodTypeIds)) {
			Map<String, Object> outMap = dispatcher.runSync("loadCartFromOrder", UtilMisc.<String, Object>toMap("orderId", orh.getOrderId(), "skipProductChecks", Boolean.TRUE, "useOrderPrice", Boolean.TRUE, "userLogin", EnvUtil.getSystemUser(delegator)));
			ShoppingCart cart = (ShoppingCart) outMap.get("shoppingCart");
			Map<GenericValue, BigDecimal> shipEstimateMap = ShippingHelper.getAllShippingEstimates(delegator, dispatcher, postalAddress, cart);

			Iterator shipMethodIter = shipmentMethodTypeIds.iterator();
			while(shipMethodIter.hasNext()) {
				String shipmentMethodTypeId = (String) shipMethodIter.next();
				if(availableShipMethods.contains(shipmentMethodTypeId)) {
					//now lets get the shipping costs
					Iterator estimateIter = shipEstimateMap.keySet().iterator();
					while(estimateIter.hasNext()) {
						GenericValue shipMethod = (GenericValue) estimateIter.next();
						if(shipMethod.getString("shipmentMethodTypeId").equals(shipmentMethodTypeId)) {
							BigDecimal retrievedShipTotal = (BigDecimal) shipEstimateMap.get(shipMethod);
							if(retrievedShipTotal != null && retrievedShipTotal.compareTo(BigDecimal.ZERO) > 0) {
								if(newShippingTotal == null) {
									newShippingTotal = retrievedShipTotal;
									newShipMethodTypeId = shipmentMethodTypeId;
								} else if(newShippingTotal.compareTo(retrievedShipTotal) > 0) {
									newShippingTotal = retrievedShipTotal;
									newShipMethodTypeId = shipmentMethodTypeId;
								}
							}
						}
					}
				}
			}
		}

		if(newShipMethodTypeId != null && newShippingTotal != null) {
			GenericValue newShipMethodType = delegator.findOne("ShipmentMethodType", UtilMisc.toMap("shipmentMethodTypeId", newShipMethodTypeId), true);
			if(UtilValidate.isNotEmpty(newShipMethodType) && UtilValidate.isNotEmpty(newShipMethodType.getString("description"))) {
				costEffectiveShipMethod.put("shipmentMethodType", newShipMethodType.getString("description"));
				costEffectiveShipMethod.put("shipmentMethodTypeId", newShipMethodTypeId);
				costEffectiveShipMethod.put("shippingTotal", newShippingTotal);
			}
		}

		return costEffectiveShipMethod;
	}

	/**
	* method gets the min/max days from a shipping description based on values inside parentheses and hyphen separated
	**/
	private static Map<String, String> shippingTimeInDays(String genericShippingDescription) {
		Map<String, String> dayRange = new HashMap<String, String>();

		int startIndex = genericShippingDescription.indexOf("(");
		int endIndex = genericShippingDescription.indexOf(")");
		String timeRange = genericShippingDescription.substring(startIndex, endIndex);

		//now lets remove anything thats not a number or hyphen
		timeRange = timeRange.replaceAll("[^0-9-]", "");

		//now lets see if there is a hyphen, if so lets split and get min/max days
		if(timeRange.contains("-")) {
			String[] dayArray = timeRange.split("-");

			if(dayArray.length > 0) {
				String minDay = dayArray[0];
				if(UtilValidate.isNotEmpty(minDay)) {
					dayRange.put("min", minDay);
				}
				if(dayArray.length > 1) {
					String maxDay = dayArray[1];
					if(UtilValidate.isNotEmpty(maxDay)) {
						dayRange.put("max", maxDay);
					}
				}
			}
		} else {
			dayRange.put("min", timeRange);
		}

		return dayRange;
	}

	private static Map<String, Object> getDiscountTotal(Delegator delegator, List<GenericValue> orderHeaderAdjustments) throws GenericEntityException {
		Map<String, Object> promoMap = new HashMap<String, Object>();
		String promoString = "";
		String promoNetsuiteId = "";
		Double promoTotal = 0.0;
		for(GenericValue adjustment : orderHeaderAdjustments) {
			String adjustmentTypeId = adjustment.getString("orderAdjustmentTypeId");
			if("PROMOTION_ADJUSTMENT".equals(adjustmentTypeId)) {
				GenericValue promotion = adjustment.getRelatedOne("ProductPromo", true);
				if(UtilValidate.isNotEmpty(promotion) && UtilValidate.isNotEmpty(promotion.getString("promoName")) && !lineItemDiscounts.containsKey(promotion.getString("promoName"))) {
					if(promoString != "") {
						promoString += ", ";
					}
					if(UtilValidate.isNotEmpty(promotion.getString("netsuiteId"))) {
						promoNetsuiteId = promotion.getString("netsuiteId");
					}
					promoString += promotion.getString("promoName");
					promoTotal += adjustment.getDouble("amount").doubleValue();
				}
			}
		}

		if(UtilValidate.isNotEmpty(promoString)) {
			BigDecimal promoTotalBigVal = new BigDecimal(promoTotal);
			promoTotalBigVal = promoTotalBigVal.setScale(3, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
			promoMap.put("codes", promoString);
			promoMap.put("netsuiteId", promoNetsuiteId);
			promoMap.put("discountTotal", promoTotalBigVal);
		}

		return promoMap;
	}

	/**
	 * Check if we can make this mailable via first class
	 * @param delegator
	 * @param orderItems
	 * @param shipmentMethodTypeId
	 * @return
	 * @throws GenericEntityException
	 */
	private static boolean isFirstClassMailable(Delegator delegator, List<GenericValue> orderItems, String shipmentMethodTypeId) throws GenericEntityException {
		if(("PICKUP").equalsIgnoreCase(shipmentMethodTypeId) || "NEXT_DAY_AIR".equalsIgnoreCase(shipmentMethodTypeId) || "NEXT_DAY_SAVER".equalsIgnoreCase(shipmentMethodTypeId)) {
			return false;
		}

		boolean isFirstClassMailable = false;
		int totalCount = 0;

		BigDecimal maxFirstClassHeight = new BigDecimal("5.25");
		BigDecimal maxFirstClassWidth = new BigDecimal("7.25");

		BigDecimal maxFirstClassHeight2 = new BigDecimal("6.00");
		BigDecimal maxFirstClassWidth2 = new BigDecimal("6.00");

		BigDecimal maxHalfFirstClassHeight = new BigDecimal("4.3125");
		BigDecimal maxHalfFirstClassWidth = new BigDecimal("2.6875");

		for(GenericValue orderItem : orderItems) {
			totalCount++;
			GenericValue product = orderItem.getRelatedOne("Product", true);
			if(UtilValidate.isNotEmpty(product)) {
				if(!"ENVELOPE".equalsIgnoreCase(product.getString("productTypeId"))) {
					isFirstClassMailable = false;
					break;
				} else if(UtilValidate.isNotEmpty(product.getBigDecimal("productHeight")) && UtilValidate.isNotEmpty(product.getBigDecimal("productWidth"))) {
					if (orderItem.getBigDecimal("quantity").compareTo(new BigDecimal("5")) > 0 && orderItem.getBigDecimal("quantity").compareTo(new BigDecimal("50")) <= 0 && ((product.getBigDecimal("productHeight").compareTo(maxFirstClassHeight) <= 0 && product.getBigDecimal("productWidth").compareTo(maxFirstClassWidth) <= 0) || (product.getBigDecimal("productHeight").compareTo(maxFirstClassWidth) <= 0 && product.getBigDecimal("productWidth").compareTo(maxFirstClassHeight) <= 0))) {
						isFirstClassMailable = true;
					} else if (orderItem.getBigDecimal("quantity").compareTo(new BigDecimal("5")) > 0 && orderItem.getBigDecimal("quantity").compareTo(new BigDecimal("50")) <= 0 && ((product.getBigDecimal("productHeight").compareTo(maxFirstClassHeight2) <= 0 && product.getBigDecimal("productWidth").compareTo(maxFirstClassWidth2) <= 0) || (product.getBigDecimal("productHeight").compareTo(maxFirstClassWidth2) <= 0 && product.getBigDecimal("productWidth").compareTo(maxFirstClassHeight2) <= 0))) {
						isFirstClassMailable = true;
					} else if (orderItem.getBigDecimal("quantity").compareTo(new BigDecimal("5")) > 0 && orderItem.getBigDecimal("quantity").compareTo(new BigDecimal("100")) <= 0 && ((product.getBigDecimal("productHeight").compareTo(maxHalfFirstClassHeight) <= 0 && product.getBigDecimal("productWidth").compareTo(maxHalfFirstClassWidth) <= 0) || (product.getBigDecimal("productHeight").compareTo(maxHalfFirstClassWidth) <= 0 && product.getBigDecimal("productWidth").compareTo(maxHalfFirstClassHeight) <= 0))) {
						isFirstClassMailable = true;
					}
				}
			}
		}

		return (isFirstClassMailable && totalCount == 1);
	}

	private static String getShippingMethod(String stateProvinceGeoId, String shipMethod) {
		if(!TaxHelper.isAvalaraEnabled) {
			if (UtilValidate.isNotEmpty(stateProvinceGeoId) && TaxHelper.STATE_TAXABLE.contains(stateProvinceGeoId) && !TaxHelper.STATE_WITH_TAXABLE_SHIPPING.contains(stateProvinceGeoId)) {
				return UtilValidate.isNotEmpty(AeNonTaxShipMethodMap.get(shipMethod)) ? AeNonTaxShipMethodMap.get(shipMethod) : AeShipMethodMap.get(shipMethod);
			}
		}

		return AeShipMethodMap.get(shipMethod);
	}

	private static Map<String, String> getOAuthParams(String method) {
		Random r = new Random();
		int Low = 1001;
		int High = 9999;
		int R = r.nextInt(High-Low) + Low;

		String timestamp = UtilDateTime.nowAsString();
		String modTimestamp = new BigDecimal(timestamp).divide(new BigDecimal("1000"), BigDecimal.ROUND_HALF_UP).toPlainString();
		String nonce = timestamp + String.valueOf(R);

		Map<String, String> oAuthParameters = new HashMap<>();
		oAuthParameters.put("realm", NLAUTH_ACCOUNT);
		oAuthParameters.put("oauth_consumer_key", NL_CONSUMER_KEY);
		oAuthParameters.put("oauth_token", NL_TOKENID);
		oAuthParameters.put("oauth_nonce", nonce);
		oAuthParameters.put("oauth_timestamp", modTimestamp);
		oAuthParameters.put("oauth_signature_method", "HMAC-SHA256");
		oAuthParameters.put("oauth_version", "1.0");
		oAuthParameters.put("oauth_signature", getOAuthSignature(method, nonce, modTimestamp));

		return oAuthParameters;
	}

	private static String getOAuthSignature(String method, String nonce, String timestamp) {
		StringBuilder str = new StringBuilder();
		String scriptId = StringUtils.substringBetween(methodCall.get(method), "script=", "&deploy");
		String deployId = methodCall.get(method).substring(methodCall.get(method).indexOf("&deploy=") + 8);

		str.append("deploy=" + deployId)
			.append("&oauth_consumer_key=" + NL_CONSUMER_KEY)
			.append("&oauth_nonce=" + nonce)
			.append("&oauth_signature_method=" + "HMAC-SHA256")
			.append("&oauth_timestamp=" + timestamp)
			.append("&oauth_token=" + NL_TOKENID)
			.append("&oauth_version=" + "1.0")
			.append("&realm=" + NLAUTH_ACCOUNT)
			.append("&script=" + scriptId);

		String completeData = getMethodType(method) + "&" + EnvUtil.encodeURIComponent(NL_URL) + "&" + EnvUtil.encodeURIComponent(str.toString());
		String encodedSigData = EnvUtil.encodeURIComponent(NL_CONUMER_SECRET) + "&" + EnvUtil.encodeURIComponent(NL_TOKENSECRET);
		return EnvUtil.generateHmacSHA256Signature(completeData, encodedSigData);
	}
}
