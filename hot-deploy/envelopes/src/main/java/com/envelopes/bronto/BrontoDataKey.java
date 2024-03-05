package com.envelopes.bronto;

import java.util.Map;

/**
 * Created by Manu on 2/4/2015.
 */
public enum BrontoDataKey {
	FIRST_NAME,
	LAST_NAME,
	COMPANY_NAME,
	ADDRESS_LINE1,
	ADDRESS_LINE2,
	CITY,
	POSTAL_CODE,
	PHONE,
	STATE,
	COUNTRY,
	CUSTOMER_TYPE,
	PARTY_ID,
	TRADE_DISCOUNT,
	NON_PROFIT_TRADE_DISCOUNT,
	LOYALTY_DISCOUNT,
	PROOF_STATUS,
	LAST_PURCHASE_DATE {
		@Override
		public String getString(Map<BrontoDataKey, Object> data) {
			return data.containsKey(this) ? data.get(this).toString() : null;
		}
	},
	EMAIL_SOURCE,
	TRIGGER_EMAIL;
	public String getString(Map<BrontoDataKey, Object> data) {
		return data.containsKey(this) ? (String) data.get(this) : null;
	}
}
