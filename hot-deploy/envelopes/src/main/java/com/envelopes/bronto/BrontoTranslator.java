package com.envelopes.bronto;

import static com.envelopes.party.PartyHelper.*;

import com.envelopes.util.EnvConstantsUtil;

import com.envelopes.email.EmailHelper;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.entity.GenericValue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Manu on 2/4/2015.
 */
public class BrontoTranslator {

	//Bronto field Ids
	public static final BigDecimal CHECKOUT_SALE_PRICE = new BigDecimal(UtilProperties.getPropertyValue("envelopes", "envelopes.bronto.sale.price"));
	public static final String EMPTY_STRING				 = "";
	public static final String YES						 = "Y";
	public static final String NO						 = "N";
	public static final String COUNTRY_CODE_CANADA		 = "CAN";

	public Map<String, String> translate(Map<String, ? extends Object> data, boolean updateFlag, String webSiteId) {
		Map<BrontoDataKey, Object> _data = extractBrontoData(data);
		Map<String, String> brontoData = new HashMap<>();
		String value = EMPTY_STRING;

		if(UtilValidate.isNotEmpty(value = (String) data.get("mode"))) {
			brontoData.put("MODE", value);
		}

		if (UtilValidate.isNotEmpty(value = BrontoDataKey.EMAIL_SOURCE.getString(_data))) {
			if(!updateFlag) {
				brontoData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("EMAILSOURCE"), value);
			}
		}

		if(UtilValidate.isNotEmpty(value = BrontoDataKey.FIRST_NAME.getString(_data))) {
			brontoData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("FIRST_NAME"), value);
			brontoData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("FIRSTNAME"), value);
		}

		if(UtilValidate.isNotEmpty(value = BrontoDataKey.LAST_NAME.getString(_data))) {
			brontoData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("LAST_NAME"), value);
			brontoData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("LASTNAME"), value);
		}

		if(UtilValidate.isNotEmpty(value = BrontoDataKey.COMPANY_NAME.getString(_data))) {
			brontoData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("COMPANYNAME"), value);
		}
		if (UtilValidate.isNotEmpty(value = BrontoDataKey.ADDRESS_LINE1.getString(_data))) {
			brontoData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("ADDRESSLINE1"), value);
		}

		if (UtilValidate.isNotEmpty(value = BrontoDataKey.ADDRESS_LINE2.getString(_data))) {
			brontoData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("ADDRESSLINE2"), value);
		}

		if (UtilValidate.isNotEmpty(value = BrontoDataKey.CITY.getString(_data))) {
			brontoData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("CITY"), value);
		}

		if (UtilValidate.isNotEmpty(value = BrontoDataKey.POSTAL_CODE.getString(_data))) {
			brontoData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("POSTAL_CODE"), value);
			brontoData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("ZIP"), value);
		}

		if (UtilValidate.isNotEmpty(value = BrontoDataKey.PHONE.getString(_data))) {
			brontoData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("PHONE_HOME"), value);
		}

		if (UtilValidate.isNotEmpty(value = BrontoDataKey.STATE.getString(_data))) {
			brontoData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("STATE_ABBREV"), value);
		}

		if (UtilValidate.isNotEmpty(value = BrontoDataKey.COUNTRY.getString(_data))) {
			brontoData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("CANADIAN"), value.equalsIgnoreCase(COUNTRY_CODE_CANADA) ? YES : NO);
		}

		if (UtilValidate.isNotEmpty(value = BrontoDataKey.CUSTOMER_TYPE.getString(_data))) {
			brontoData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("CUSTOMERTYPE"), value);
		}

		if (UtilValidate.isNotEmpty(value = BrontoDataKey.PARTY_ID.getString(_data))) {
			brontoData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("PARTYID"), value);
		}

		if (UtilValidate.isNotEmpty(value = BrontoDataKey.TRADE_DISCOUNT.getString(_data))) {
			brontoData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("TRADEDISCOUNT"), value);
		}

		if(UtilValidate.isNotEmpty(value = BrontoDataKey.PROOF_STATUS.getString(_data))) {
			brontoData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("PROOF_STATUS"), value);
		}

		if (UtilValidate.isNotEmpty(value = BrontoDataKey.LOYALTY_DISCOUNT.getString(_data))) {
			brontoData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("LOYALTYDISCOUNT"), value);
		}

		if (UtilValidate.isNotEmpty(value = BrontoDataKey.LAST_PURCHASE_DATE.getString(_data))) {
			brontoData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("LASTPURCHASEDATE"), value);
		}

		if (UtilValidate.isNotEmpty(value = BrontoDataKey.TRIGGER_EMAIL.getString(_data))) {
			brontoData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("TRIGGER_EMAIL"), value);
		}
		/*if(isCheckout) {
			brontoData.putAll(getResetData());
		}*/

		return brontoData;
	}

	public Map<String, String> getCartData(Map<String, ? extends Object> _cartData, String webSiteId) {
		Map<String, String> cartData = new HashMap<>();
		List lineItems = (List) _cartData.get("lineItems");
		for(int i = 0; i < 5; i ++) {
			if(i < lineItems.size()) {
				Map lineItem = (Map)lineItems.get(i);
				String productId = (String)lineItem.get("productId");
				String itemName = (String)lineItem.get("productName");
				String imageURL = "";
				Map<String, String> envArtworkAttributes = (Map<String, String>)lineItem.get("envArtworkAttributes");

				if(!envArtworkAttributes.isEmpty() && envArtworkAttributes.containsKey("scene7DesignId")) {
					imageURL = "www.envelopes.com/envelopes/control/scene7Image.png?id=" + (String)envArtworkAttributes.get("scene7DesignId") + "&setWidth=100";
				} else {
					imageURL = "//actionenvelope.scene7.com/is/image/ActionEnvelope/" + productId + "?wid=100&fmt=png-alpha";
				}
				int quantity = ((BigDecimal)lineItem.get("quantity")).intValue();
				if(quantity <= 5) {
					itemName = "Samples of " + itemName;
				}
				String price = ((BigDecimal)lineItem.get("totalPrice")).toString();

				cartData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_CART_FIELDS.get("CARTPRODIMAGE" + (i + 1)), imageURL);
				cartData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_CART_FIELDS.get("CARTPRODNAME"  + (i + 1)), itemName);
				cartData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_CART_FIELDS.get("CARTPRODPRICE" + (i + 1)), price);
				cartData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_CART_FIELDS.get("CARTPRODQTY"   + (i + 1)), Integer.toString(quantity));
				cartData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_CART_FIELDS.get("CARTPRODSKU"   + (i + 1)), productId);
			} else {
				cartData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_CART_FIELDS.get("CARTPRODIMAGE" + (i + 1)), EMPTY_STRING);
				cartData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_CART_FIELDS.get("CARTPRODNAME"  + (i + 1)), EMPTY_STRING);
				cartData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_CART_FIELDS.get("CARTPRODPRICE" + (i + 1)), EMPTY_STRING);
				cartData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_CART_FIELDS.get("CARTPRODQTY"   + (i + 1)), EMPTY_STRING);
				cartData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_CART_FIELDS.get("CARTPRODSKU"   + (i + 1)), EMPTY_STRING);
			}
		}
		return cartData;
	}

	public Map<String, String> getCheckoutData(Map<String, Object> orderInfo, String webSiteId) {
		Map<String, String> checkoutData = new HashMap<>();
		Map<String, Object> lineItem = ((List<Map>) orderInfo.get("lineItems")).get(((List<Map>) orderInfo.get("lineItems")).size() - 1);

		BigDecimal totalPrice = (BigDecimal) lineItem.get("totalPrice");
		checkoutData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_CHECKOUT_FIELDS.get("LASTITEMPURCHASEIMAGE"), (UtilValidate.isNotEmpty(lineItem.get("scene7DesignId")) ? "https://www.envelopes.com/envelopes/control/scene7Image.png?id=" + ((String) lineItem.get("scene7DesignId")) : "https://actionenvelope.scene7.com/is/image/ActionEnvelope/" + ((String) lineItem.get("productId"))));
		checkoutData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_CHECKOUT_FIELDS.get("LASTITEMPURCHASETITLE"), (String) lineItem.get("productName"));
		checkoutData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_CHECKOUT_FIELDS.get("LASTITEMPURCHASESIZECOLOR"),  "(" + ((String) lineItem.get("productSize")) + ") - " + ((String) lineItem.get("productColor")));
		checkoutData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_CHECKOUT_FIELDS.get("LASTITEMPURCHASEPRICE"),  (totalPrice).toPlainString());
		checkoutData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_CHECKOUT_FIELDS.get("LASTITEMPURCHASESALEPRICE"), totalPrice.multiply(CHECKOUT_SALE_PRICE).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING).toPlainString());
		if (UtilValidate.isNotEmpty(lineItem.get("primaryProductCategoryId"))) {
			checkoutData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("INTERESTEDCATEGORY"), ((String) lineItem.get("primaryProductCategoryId")).replace(" ", "_").toLowerCase());
		}
		if (UtilValidate.isNotEmpty(lineItem.get("productColor"))) {
			checkoutData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("INTERESTEDCOLOR"), ((String) lineItem.get("productColor")).replace(" ", "_").toLowerCase());
		}
		if (UtilValidate.isNotEmpty(lineItem.get("productSize"))) {
			checkoutData.put(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FIELDS.get("INTERESTEDSIZE"), ((String) lineItem.get("productSize")).replace(" ", "_").toLowerCase());
		}

		return checkoutData;
	}

	protected Map<BrontoDataKey, Object> extractBrontoData(Map<String, ? extends Object> data) {
		Map<BrontoDataKey, Object> brontoData = new HashMap<>();
		String emailSource = "";
		if(UtilValidate.isNotEmpty(emailSource = (String) data.get("emailSource"))) {
			brontoData.put(BrontoDataKey.EMAIL_SOURCE, emailSource);
		} else {
			brontoData.put(BrontoDataKey.EMAIL_SOURCE, "Footer");
		}

		String triggerEmail = null;
		if(UtilValidate.isNotEmpty(triggerEmail = (String) data.get("triggerEmail"))) {
			brontoData.put(BrontoDataKey.TRIGGER_EMAIL, triggerEmail);
		}

		GenericValue personData = null;
		if(UtilValidate.isNotEmpty(personData = (GenericValue)data.get("person"))) {
			brontoData.put(BrontoDataKey.PARTY_ID, personData.getString("partyId"));
		}

		String proofStatus = "";
		if (UtilValidate.isNotEmpty(proofStatus = (String)data.get("proofStatus"))) {
			brontoData.put(BrontoDataKey.PROOF_STATUS, proofStatus);
		}

		if(data.containsKey(TO_NAME)) {
			String[] names = new String[0];
			String toName = EMPTY_STRING;
			if(UtilValidate.isNotEmpty(toName = (String)data.get(TO_NAME))) {
				names = splitAtFirstSpace(toName);
			}
			if(names.length == 2) {
				brontoData.put(BrontoDataKey.FIRST_NAME, names[0]);
				brontoData.put(BrontoDataKey.LAST_NAME, names[1]);
			} else if(names.length == 1) {
				brontoData.put(BrontoDataKey.FIRST_NAME, names[0]);
				brontoData.put(BrontoDataKey.LAST_NAME, EMPTY_STRING);
			} else {
				brontoData.put(BrontoDataKey.FIRST_NAME, EMPTY_STRING);
				brontoData.put(BrontoDataKey.LAST_NAME, EMPTY_STRING);
			}
		}

		if(data.containsKey(COMPANY_NAME)) {
			String companyName = EMPTY_STRING;
			if (UtilValidate.isNotEmpty(companyName = (String)data.get("companyName"))) {
				brontoData.put(BrontoDataKey.COMPANY_NAME, companyName);
			}
		}

		GenericValue shippingAddressData = null;
		if(UtilValidate.isNotEmpty(shippingAddressData = (GenericValue)data.get("shippingAddress"))) {
			String[] names = new String[0];
			String toName = EMPTY_STRING;
			if(UtilValidate.isNotEmpty(toName = shippingAddressData.getString(TO_NAME))) {
				names = splitAtFirstSpace(toName);
			}
			if(names.length == 2) {
				brontoData.put(BrontoDataKey.FIRST_NAME, names[0]);
				brontoData.put(BrontoDataKey.LAST_NAME, names[1]);
			} else if(names.length == 1) {
				brontoData.put(BrontoDataKey.FIRST_NAME, names[0]);
				brontoData.put(BrontoDataKey.LAST_NAME, EMPTY_STRING);
			} else {
				brontoData.put(BrontoDataKey.FIRST_NAME, EMPTY_STRING);
				brontoData.put(BrontoDataKey.LAST_NAME, EMPTY_STRING);
			}
			brontoData.put(BrontoDataKey.ADDRESS_LINE1, shippingAddressData.getString("address1"));
			String addressLine2 = EMPTY_STRING;
			if (UtilValidate.isNotEmpty(addressLine2 = shippingAddressData.getString("address2"))) {
				brontoData.put(BrontoDataKey.ADDRESS_LINE2, addressLine2);
			}
			String companyName = EMPTY_STRING;
			if (UtilValidate.isNotEmpty(companyName = shippingAddressData.getString("companyName"))) {
				brontoData.put(BrontoDataKey.COMPANY_NAME, companyName);
			}
			brontoData.put(BrontoDataKey.CITY, shippingAddressData.getString("city"));
			brontoData.put(BrontoDataKey.STATE, shippingAddressData.getString("stateProvinceGeoId"));
			brontoData.put(BrontoDataKey.COUNTRY, shippingAddressData.getString("countryGeoId"));
			brontoData.put(BrontoDataKey.POSTAL_CODE, shippingAddressData.getString("postalCode"));
		}

		GenericValue shippingTelecom = null;
		if(UtilValidate.isNotEmpty(shippingTelecom = (GenericValue) data.get("shippingTelecom"))) {
			brontoData.put(BrontoDataKey.PHONE, shippingTelecom.getString("contactNumber"));
		}

		GenericValue orderHeaderData = null;
		if(UtilValidate.isNotEmpty(orderHeaderData = (GenericValue) data.get("orderHeader"))) {
			brontoData.put(BrontoDataKey.LAST_PURCHASE_DATE, orderHeaderData.getTimestamp("createdStamp"));
			brontoData.put(BrontoDataKey.EMAIL_SOURCE, "Checkout");
		}

		String isTrade = EMPTY_STRING;
		if(UtilValidate.isNotEmpty(isTrade = (String) data.get("isTrade"))) {
			brontoData.put(BrontoDataKey.TRADE_DISCOUNT, isTrade);
		}

		String isNonProfit = EMPTY_STRING;
		if(UtilValidate.isNotEmpty(isNonProfit = (String) data.get("isNonProfit"))) {
			brontoData.put(BrontoDataKey.NON_PROFIT_TRADE_DISCOUNT, isNonProfit);
		}

		String customerType = EMPTY_STRING;
		if(UtilValidate.isNotEmpty(customerType = (String) data.get("customerType"))) {
			brontoData.put(BrontoDataKey.CUSTOMER_TYPE, customerType);
		}
		return brontoData;
	}
}
