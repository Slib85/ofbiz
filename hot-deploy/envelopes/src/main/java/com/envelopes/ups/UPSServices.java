/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.ups;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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

import com.lob.Util;
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

import com.envelopes.util.*;

public class UPSServices {
	public static final String module = UPSServices.class.getName();

	/**
	 * This service will return the time in transit for a specific zip code from 11701 location
	 */
	public static Map<String, Object> getTimeInTransit(DispatchContext dctx, Map<String, ? extends Object> context) {
		LocalDispatcher dispatcher = dctx.getDispatcher();
		Delegator delegator = dctx.getDelegator();

		Map<String, Object> result = ServiceUtil.returnSuccess();

		List<String> processedCodes = new ArrayList<>();
		try {
			int counter = 0;
			FileInputStream fstream = new FileInputStream(EnvConstantsUtil.OFBIZ_HOME + "hot-deploy/envelopes/data/zipList.txt"); //open zip list
			DataInputStream in = new DataInputStream(fstream); //Get the object of DataInputStream
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			String postalCodeToDo = (String) context.get("postalCode");
			String stateProvinceGeoIdToDo = (String) context.get("stateProvinceGeoId");

			while ((strLine = br.readLine()) != null) {
				String[] postalArray = strLine.split("\\t");
				List<String> postalList = Arrays.asList(postalArray);
				if (postalList.size() > 3) {
					if (UtilValidate.isNotEmpty(postalList.get(0).trim()) && UtilValidate.isNotEmpty(postalList.get(1).trim()) && UtilValidate.isNotEmpty(postalList.get(2).trim()) && UtilValidate.isNotEmpty(postalList.get(3).trim())) {
						String countryCode = postalList.get(0).trim();
						String postalCode = postalList.get(1).trim();
						String city = postalList.get(2).trim();
						String stateProvince = postalList.get(3).trim();

						//if postal code doesnt start with value passed in skip it
						if (UtilValidate.isNotEmpty(postalCodeToDo) && UtilValidate.isNotEmpty(postalCode) && !postalCode.startsWith(postalCodeToDo)) {
							continue;
						}

						//if state isnt what was passed in, skip it
						if (UtilValidate.isNotEmpty(stateProvinceGeoIdToDo) && UtilValidate.isNotEmpty(stateProvince) && !stateProvince.equalsIgnoreCase(stateProvinceGeoIdToDo)) {
							continue;
						}

						//if we see state as Puerto Rico, we will treat it as a country of its own and not US since UPS separates it
						if (stateProvince.equals("PR")) {
							countryCode = "PR";
							stateProvince = null;
						}

						try {
							GenericValue postalRow = null;
							if (countryCode.equals("US") || countryCode.equals("PR")) {
								postalRow = EntityUtil.getFirst(delegator.findByAnd("ZonePostalCodeLookup", UtilMisc.toMap("shipmentMethodTypeId", "GROUND", "postalCode", postalCode), null, false));
							} else {
								postalRow = EntityUtil.getFirst(delegator.findByAnd("ZonePostalCodeLookup", UtilMisc.toMap("shipmentMethodTypeId", "STANDARD", "postalCode", postalCode), null, false));
							}

							if (UtilValidate.isEmpty(postalRow) && postalCode.length() > 3) {
								if (countryCode.equals("US") || countryCode.equals("PR")) {
									postalRow = EntityUtil.getFirst(delegator.findByAnd("ZonePostalCodeLookup", UtilMisc.toMap("shipmentMethodTypeId", "GROUND", "postalCode", postalCode.substring(0, 3)), null, false));
								} else {
									postalRow = EntityUtil.getFirst(delegator.findByAnd("ZonePostalCodeLookup", UtilMisc.toMap("shipmentMethodTypeId", "STANDARD", "postalCode", postalCode.substring(0, 3)), null, false));
								}
							}

							List<Map> shipMethodAndTNT = null;
							if (UtilValidate.isNotEmpty(postalRow) && !processedCodes.contains(postalRow.getString("postalCode"))) {
								try {
									processedCodes.add(postalRow.getString("postalCode"));
									shipMethodAndTNT = UPSHelper.getTimeInTransit(postalCode, city, stateProvince, countryCode);
								} catch (Exception e) {
									//EnvUtil.reportError(e);
									//Debug.logError(e, "Error trying to look up transit time.", module);
								}

								counter++;
							}

							if (UtilValidate.isNotEmpty(shipMethodAndTNT)) {
								Iterator shipMethodAndTNTIter = shipMethodAndTNT.iterator();
								while (shipMethodAndTNTIter.hasNext()) {
									Map<String, String> summaryMap = (Map) shipMethodAndTNTIter.next();
									String serviceCode = (String) summaryMap.get("serviceCode");
									String serviceDesc = (String) summaryMap.get("serviceDesc");
									String inTransitTime = (String) summaryMap.get("inTransitTime");
									String etaDate = (String) summaryMap.get("etaDate");
									String etaTime = (String) summaryMap.get("etaTime");
									//update transitTime in ZonePostalCodeLookup
									//check if the serviceCode is something we use and is defined in UPSTransitCodeMap
									if (UPSHelper.UPS_SERVICE_DESC.containsKey(serviceDesc)) {
										GenericValue shipMethodAndPostalCode = delegator.findOne("ZonePostalCodeLookup", UtilMisc.toMap("shipmentMethodTypeId", UPSHelper.UPS_SERVICE_DESC.get(serviceDesc), "postalCode", postalRow.getString("postalCode")), false);
										if (UtilValidate.isEmpty(shipMethodAndPostalCode) && UPSHelper.UPS_SERVICE_MAP.containsKey(serviceCode)) {
											shipMethodAndPostalCode = delegator.findOne("ZonePostalCodeLookup", UtilMisc.toMap("shipmentMethodTypeId", UPSHelper.UPS_SERVICE_MAP.get(serviceCode), "postalCode", postalRow.getString("postalCode")), false);
										}
										if (UtilValidate.isNotEmpty(shipMethodAndPostalCode)) {
											Debug.logInfo("Storing transit times for : " + postalRow.getString("postalCode") + ":" + shipMethodAndPostalCode.getString("shipmentMethodTypeId"), module);
											shipMethodAndPostalCode.set("transitTime", (Long.valueOf(inTransitTime)).longValue());
											shipMethodAndPostalCode.store();
										}
									}

								}
							}
						} catch (GenericEntityException e) {
							//EnvUtil.reportError(e);
							Debug.logError(e, "Error trying to look up transit time.", module);
						}

						//pause the service every 100 records so that we dont get a request error for too many connections
						if ((counter % 100) == 0) {
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e) {
								//EnvUtil.reportError(e);
								Debug.logError("Issue while pausing.", module);
							}
						}
					}
				}
			}
			in.close(); //Close the input stream
		} catch(Exception e) {
			//EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to look up transit time.", module);
		}

		return ServiceUtil.returnSuccess();
	}


	/*
	 * Get attribute map from add to cart event for an item
	 */
	public static Map<String, Object> getEnvItemAttributes(Map<String, BigDecimal> adjustments) {
		Map<String, Object> envItemAttributes = new HashMap<String, Object>();
		for(Map.Entry<String, BigDecimal> adjustment : adjustments.entrySet()) {
			envItemAttributes.put(adjustment.getKey(), adjustment.getValue());
		}

		return envItemAttributes;
	}
}