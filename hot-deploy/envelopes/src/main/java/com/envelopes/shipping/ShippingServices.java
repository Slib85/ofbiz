/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.shipping;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
import java.util.HashSet;
import java.util.TreeSet;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.GeneralException;
import org.apache.ofbiz.base.util.UtilDateTime;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.envelopes.util.*;

public class ShippingServices {
	public static final String module = ShippingServices.class.getName();

	public static final Set<String> validFlatRateStates;
	static {
		validFlatRateStates = new HashSet<String>();
		validFlatRateStates.add("AK");
		validFlatRateStates.add("HI");
		validFlatRateStates.add("WA");
		validFlatRateStates.add("OR");
		validFlatRateStates.add("CA");
		validFlatRateStates.add("AZ");
		validFlatRateStates.add("NV");
		validFlatRateStates.add("ID");
		validFlatRateStates.add("MT");
		validFlatRateStates.add("WY");
		validFlatRateStates.add("UT");
		validFlatRateStates.add("CO");
		validFlatRateStates.add("NM");
		validFlatRateStates.add("ND");
		validFlatRateStates.add("SD");
		validFlatRateStates.add("NE");
		validFlatRateStates.add("OK");
		validFlatRateStates.add("TX");
		validFlatRateStates.add("MS");
	}

	/*
	 * Service to see if item is USPSable
	 */
	public static Map isUSPSSmallFlatRatable (DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();

		BigDecimal maxFlatRateHeight = new BigDecimal("8.625");
		BigDecimal maxFlatRateWidth = new BigDecimal("5.375");

		BigDecimal maxHalfFlatRateHeight = new BigDecimal("4.3125");
		BigDecimal maxHalfFlatRateWidth = new BigDecimal("2.6875");

		List shippableItemInfo = (List) context.get("shippableItemInfo");
		String stateProvinceGeoId = (String) context.get("stateProvinceGeoId");
		boolean isUSPSFlatRatable = false; //if at anytime we get a value of false while looping through the cart item list, we want to break out of the code

		Map result = ServiceUtil.returnSuccess();

		//if the passed stateProviceGeoId doesnt match the accepted set, we do not want to offer this service
		if(UtilValidate.isNotEmpty(stateProvinceGeoId)) {
			if(!validFlatRateStates.contains(stateProvinceGeoId)) {
				//Debug.log("Not a valid State: " + stateProvinceGeoId, module);
				result.put("isFlatRatable", isUSPSFlatRatable); //setting default value of false
				return result;
			}
		}

		int totalCartItems = 0;
		//loop through list to get productIds
		Iterator litr = shippableItemInfo.iterator();
		while (litr.hasNext()) {
			totalCartItems++;
			Map itemMap = (Map) litr.next();
			String productId = (String) itemMap.get("productId");
			BigDecimal qty = (BigDecimal) itemMap.get("quantity");

			if(UtilValidate.isEmpty(qty)) {
				qty = BigDecimal.ZERO;
			}

			try {
				GenericValue product = delegator.findOne("Product", UtilMisc.toMap("productId", productId), true);

				if(UtilValidate.isNotEmpty(product) && UtilValidate.isNotEmpty(product.getBigDecimal("productHeight")) && UtilValidate.isNotEmpty(product.getBigDecimal("productWidth"))) {
					if(qty.compareTo(BigDecimal.ZERO) > 0 && qty.compareTo(new BigDecimal("50")) <= 0 && product.getBigDecimal("productHeight").compareTo(maxFlatRateHeight) <= 0 && product.getBigDecimal("productWidth").compareTo(maxFlatRateWidth) <= 0) {
						//if the qty is less than 50, we will try to fit to edge of box
						isUSPSFlatRatable = true;
						//Debug.log("IN QTY CHECK " + qty, module);
					} else if(qty.compareTo(new BigDecimal("50")) >= 0 && qty.compareTo(new BigDecimal("100")) <= 0 && product.getBigDecimal("productHeight").compareTo(maxHalfFlatRateHeight) <= 0 && product.getBigDecimal("productWidth").compareTo(maxHalfFlatRateWidth) <= 0) {
						//if the qty is between 50-100, we can split it in half and try to fit it in box
						isUSPSFlatRatable = true;
						//Debug.log("IN QTY CHECK " + qty, module);
					} else {
						isUSPSFlatRatable = false;
						break;
					}

					//check if this product is part of the FASHION collection if so, we need to ignore it, they are too thick
					GenericValue fashionGV = EntityUtil.getFirst(delegator.findByAnd("ProductCategoryMember", UtilMisc.toMap("productId", productId, "productCategoryId", "FASHIONS"), null, true));
					if(UtilValidate.isNotEmpty(fashionGV)) {
						isUSPSFlatRatable = false;
						break;
					}

					//list of variants that according to size will fit, but in reality will not
					if(UtilValidate.isNotEmpty(product.getString("productName"))) {
						String productName = product.getString("productName").toLowerCase();
						if(productName.contains("remittance")) {
							isUSPSFlatRatable = false;
							break;
						} else if(productName.contains("2-way")) {
							isUSPSFlatRatable = false;
							break;
						} else if(productName.contains("folded")) {
							isUSPSFlatRatable = false;
							break;
						} else if(productName.contains("petal")) {
							isUSPSFlatRatable = false;
							break;
						} else if(productName.contains("pocket")) {
							isUSPSFlatRatable = false;
							break;
						}
					}

					//list of colors that wont fit
					GenericValue prodColor = EntityUtil.getFirst(delegator.findByAnd("ProductFeatureAndAppl", UtilMisc.toMap("productId", productId, "productFeatureTypeId", "COLOR"), null, true));
					if(UtilValidate.isNotEmpty(prodColor) && UtilValidate.isNotEmpty(prodColor.getString("description"))) {
						String color = prodColor.getString("description");
						if(color.equals("Crystal Clear")) {
							isUSPSFlatRatable = false;
							break;
						}
					}
				}
			} catch (GenericEntityException e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "There was an issue getting data for this product", module);
				return ServiceUtil.returnError(e.getMessage());
			}
		}

		if(totalCartItems > 1) {
			isUSPSFlatRatable = false;
		}

		result.put("isFlatRatable", isUSPSFlatRatable);
		return result;
	}
}