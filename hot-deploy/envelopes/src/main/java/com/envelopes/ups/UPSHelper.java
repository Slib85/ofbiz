/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.ups;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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


import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.GenericValue;

import com.ups.www.wsdl.xoltws.track.v2_0.TrackServiceStub;
import com.ups.www.wsdl.xoltws.track.v2_0.TrackServiceStub.*;
import com.ups.www.wsdl.xoltws.ship.v1_0.ShipServiceStub;
import com.ups.www.wsdl.xoltws.ship.v1_0.ShipServiceStub.*;
import com.ups.www.wsdl.xoltws.tnt.v1_0.TimeInTransitServiceStub;
import com.ups.www.wsdl.xoltws.tnt.v1_0.TimeInTransitServiceStub.*;
import com.ups.www.wsdl.xoltws.rate.v1_1.RateServiceStub;
import com.ups.www.wsdl.xoltws.rate.v1_1.RateServiceStub.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.envelopes.util.*;
import com.envelopes.shipping.ShippingHelper;

public class UPSHelper {
	public static final String module = UPSHelper.class.getName();

	public static String UPS_ACCOUNT;
	public static String ACCESS_KEY;
	public static String USER_NAME;
	public static String PASS_WORD;
	public static String SHIP_URL;
	public static String TNT_URL;
	public static String RATE_URL;
	public static String TRACK_URL;

	public static final Map<String, String> UPS_SERVICE_TYPE;
	public static final Map<String, String> UPS_SERVICE_MAP;
	public static final Map<String, String> UPS_SERVICE_MAP_FOR_RATES;
	public static final Map<String, String> UPS_SERVICE_DESC;
	public static final Map<String, String> UPS_SERVICE_ID;
	public static final Map<String, String> UPS_PACKAGE_TYPE;
	public static final Map<String, String> SHIPPER_ADDRESS;

	static {
		try {
			UPS_ACCOUNT = UtilProperties.getPropertyValue("envelopes", "ups.accountnumber");
			ACCESS_KEY = UtilProperties.getPropertyValue("envelopes", "ups.accesskey");
			USER_NAME = UtilProperties.getPropertyValue("envelopes", "ups.username");
			PASS_WORD = UtilProperties.getPropertyValue("envelopes", "ups.password");
			SHIP_URL = UtilProperties.getPropertyValue("envelopes", "ups.ship.url");
			TNT_URL = UtilProperties.getPropertyValue("envelopes", "ups.tnt.url");
			RATE_URL = UtilProperties.getPropertyValue("envelopes", "ups.rate.url");
			TRACK_URL = UtilProperties.getPropertyValue("envelopes", "ups.track.url");
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error, could not open: " + "envelopes.properties", module);
		}

		UPS_SERVICE_TYPE = new HashMap<String, String>();
		UPS_SERVICE_TYPE.put("nextDayAir", "01");
		UPS_SERVICE_TYPE.put("2ndDayAir", "02");
		UPS_SERVICE_TYPE.put("ground", "03");
		UPS_SERVICE_TYPE.put("express", "07");
		UPS_SERVICE_TYPE.put("expedited", "08");
		UPS_SERVICE_TYPE.put("upsStandard", "11");
		UPS_SERVICE_TYPE.put("3DaySelect", "12");
		UPS_SERVICE_TYPE.put("nextDayAirSaver", "13");
		UPS_SERVICE_TYPE.put("nextDayAirEarlyAM", "14");
		UPS_SERVICE_TYPE.put("expressPlus", "54");
		UPS_SERVICE_TYPE.put("2ndDayAirAM", "59");
		UPS_SERVICE_TYPE.put("upsSaver", "65");
		UPS_SERVICE_TYPE.put("upsTodayStandard", "82");
		UPS_SERVICE_TYPE.put("upsTodayDedicatedCourier", "83");
		UPS_SERVICE_TYPE.put("upsTodayIntercity", "84");
		UPS_SERVICE_TYPE.put("upsTodayExpress", "85");
		UPS_SERVICE_TYPE.put("upsTodayExpressSaver", "86");
		UPS_SERVICE_TYPE.put("upsWorldwideExpressFreight", "96");

		UPS_SERVICE_MAP = new HashMap<String, String>();
		UPS_SERVICE_MAP.put("GND", "GROUND");
		UPS_SERVICE_MAP.put("03", "GROUND");
		UPS_SERVICE_MAP.put("3", "GROUND");
		UPS_SERVICE_MAP.put("G", "GROUND");
		UPS_SERVICE_MAP.put("1DA", "NEXT_DAY_AIR");
		UPS_SERVICE_MAP.put("01", "NEXT_DAY_AIR");
		UPS_SERVICE_MAP.put("1", "NEXT_DAY_AIR");
		UPS_SERVICE_MAP.put("1DP", "NEXT_DAY_SAVER");
		UPS_SERVICE_MAP.put("2DA", "SECOND_DAY_AIR");
		UPS_SERVICE_MAP.put("02", "SECOND_DAY_AIR");
		UPS_SERVICE_MAP.put("2", "SECOND_DAY_AIR");
		UPS_SERVICE_MAP.put("ST", "STANDARD");
		UPS_SERVICE_MAP.put("11", "STANDARD");
		UPS_SERVICE_MAP.put("3DS", "THREE_DAY_SELECT");
		UPS_SERVICE_MAP.put("12", "THREE_DAY_SELECT");
		UPS_SERVICE_MAP.put("ES", "WORLDWIDE_EXPR");
		UPS_SERVICE_MAP.put("07", "WORLDWIDE_EXPR");
		UPS_SERVICE_MAP.put("7", "WORLDWIDE_EXPR");
		UPS_SERVICE_MAP.put("EX", "WORLDWIDE_EXPTD");
		UPS_SERVICE_MAP.put("08", "WORLDWIDE_EXPTD");
		UPS_SERVICE_MAP.put("8", "WORLDWIDE_EXPTD");
		UPS_SERVICE_MAP.put("59", "SECOND_DAY_AIR_AM");
		UPS_SERVICE_MAP.put("13", "NEXT_DAY_SAVER");
		UPS_SERVICE_MAP.put("14", "NEXT_DAY_AIR_AM");

		UPS_SERVICE_DESC = new HashMap<String, String>();
		UPS_SERVICE_DESC.put("UPS Standard", "STANDARD");
		UPS_SERVICE_DESC.put("UPS Worldwide Express", "WORLDWIDE_EXPR");
		UPS_SERVICE_DESC.put("UPS Worldwide Expedited", "WORLDWIDE_EXPTD");
		UPS_SERVICE_DESC.put("UPS Ground", "GROUND");
		UPS_SERVICE_DESC.put("UPS 3 Day Select", "THREE_DAY_SELECT");
		UPS_SERVICE_DESC.put("UPS 2nd Day Air", "SECOND_DAY_AIR");
		UPS_SERVICE_DESC.put("UPS Next Day Air Saver", "NEXT_DAY_SAVER");
		UPS_SERVICE_DESC.put("UPS Next Day Air", "NEXT_DAY_AIR");

		UPS_SERVICE_ID = new HashMap<String, String>();
		UPS_SERVICE_ID.put("STANDARD", "UPS Standard");
		UPS_SERVICE_ID.put("WORLDWIDE_EXPR", "UPS Worldwide Express");
		UPS_SERVICE_ID.put("WORLDWIDE_EXPTD", "UPS Worldwide Expedited");
		UPS_SERVICE_ID.put("GROUND", "UPS Ground");
		UPS_SERVICE_ID.put("THREE_DAY_SELECT", "UPS 3 Day Select");
		UPS_SERVICE_ID.put("SECOND_DAY_AIR", "UPS 2nd Day Air");
		UPS_SERVICE_ID.put("NEXT_DAY_SAVER", "UPS Next Day Air Saver");
		UPS_SERVICE_ID.put("NEXT_DAY_AIR", "UPS Next Day Air");

		UPS_SERVICE_MAP_FOR_RATES = new HashMap<String, String>();
		UPS_SERVICE_MAP_FOR_RATES.put("STANDARD", "11");
		UPS_SERVICE_MAP_FOR_RATES.put("WORLDWIDE_EXPR", "07");
		UPS_SERVICE_MAP_FOR_RATES.put("WORLDWIDE_EXPTD", "08");
		UPS_SERVICE_MAP_FOR_RATES.put("GROUND", "03");
		UPS_SERVICE_MAP_FOR_RATES.put("THREE_DAY_SELECT", "12");
		UPS_SERVICE_MAP_FOR_RATES.put("SECOND_DAY_AIR", "02");
		UPS_SERVICE_MAP_FOR_RATES.put("NEXT_DAY_SAVER", "13");
		UPS_SERVICE_MAP_FOR_RATES.put("NEXT_DAY_AIR", "01");

		UPS_PACKAGE_TYPE = new HashMap<String, String>();
		UPS_PACKAGE_TYPE.put("upsLetter", "01");
		UPS_PACKAGE_TYPE.put("customerSuppliedPackage", "02");
		UPS_PACKAGE_TYPE.put("tube", "03");
		UPS_PACKAGE_TYPE.put("pak", "04");
		UPS_PACKAGE_TYPE.put("uPSExpressBox", "21");
		UPS_PACKAGE_TYPE.put("uPS25KGBox", "24");
		UPS_PACKAGE_TYPE.put("uPS10KGBox", "25");
		UPS_PACKAGE_TYPE.put("pallet", "30");
		UPS_PACKAGE_TYPE.put("smallExpressBox", "2a");
		UPS_PACKAGE_TYPE.put("mediumExpressBox", "2b");
		UPS_PACKAGE_TYPE.put("largeExpressBox", "2c");

		SHIPPER_ADDRESS = new HashMap<String, String>();
		SHIPPER_ADDRESS.put("toName", "Envelopes.com");
		SHIPPER_ADDRESS.put("attnName", "Envelopes.com");
		SHIPPER_ADDRESS.put("address1", "1200 Kinne Street");
		SHIPPER_ADDRESS.put("address2", null);
		SHIPPER_ADDRESS.put("city", "East Syracuse");
		SHIPPER_ADDRESS.put("postalCode", "13057");
		SHIPPER_ADDRESS.put("stateProvinceGeoId", "NY");
		SHIPPER_ADDRESS.put("countryGeoId", "US");
		SHIPPER_ADDRESS.put("phone", "8776835673");
	}

	/*
	 * Login to UPS
	 */
	public static String getUPSReturnShipLabel(Map<String, String> shipFromAddress, List<BigDecimal> packageWeights, String orderId, String returnId) throws Exception {
		ShipServiceStub shipServiceStub = new ShipServiceStub(SHIP_URL);
		ShipmentRequest shipRequest = new ShipmentRequest();
		ShipServiceStub.TransactionReferenceType transactionReference = new ShipServiceStub.TransactionReferenceType();
		transactionReference.setCustomerContext("Envelopes.com");

		ShipServiceStub.RequestType request = new ShipServiceStub.RequestType();
		request.setTransactionReference(transactionReference);
		String[] requestOption = { "nonvalidate" };
		request.setRequestOption(requestOption);
		shipRequest.setRequest(request);

		ShipServiceStub.ShipmentType shipment = new ShipServiceStub.ShipmentType();

		//Envelopes.com Shipper
		shipment.setShipper(createSHIPShipper(SHIPPER_ADDRESS));
		shipment.setDescription("Envelopes Shipment");

		//ship from address
		shipment.setShipFrom(createSHIPShipFrom(shipFromAddress));

		//ship to address
		shipment.setShipTo(createShipTo(SHIPPER_ADDRESS));

		ShipServiceStub.ServiceType service = new ShipServiceStub.ServiceType();
		service.setCode(UPS_SERVICE_TYPE.get("ground"));
		shipment.setService(service);

		//package details
		ShipServiceStub.PackageType[] pkgArray = new ShipServiceStub.PackageType[ packageWeights.size() ];
		int count = 0;
		for(BigDecimal packageWeight : packageWeights) {
			ShipServiceStub.PackageType pkg1 = new ShipServiceStub.PackageType();
			PackagingType pkgingType = new PackagingType();
			pkgingType.setCode(UPS_PACKAGE_TYPE.get("customerSuppliedPackage"));
			pkg1.setPackaging(pkgingType);
			ShipServiceStub.PackageWeightType weight = new ShipServiceStub.PackageWeightType();
			weight.setWeight(packageWeight.toString());
			ShipServiceStub.ShipUnitOfMeasurementType shpUnitOfMeas = new ShipServiceStub.ShipUnitOfMeasurementType();
			shpUnitOfMeas.setCode("LBS");
			shpUnitOfMeas.setDescription("Pounds");
			weight.setUnitOfMeasurement(shpUnitOfMeas);
			pkg1.setPackageWeight(weight);
			ShipServiceStub.ReferenceNumberType orderRefNumber = new ShipServiceStub.ReferenceNumberType();
			orderRefNumber.setCode("PO");
			orderRefNumber.setValue(orderId);
			ShipServiceStub.ReferenceNumberType raRefNumber = new ShipServiceStub.ReferenceNumberType();
			raRefNumber.setCode("RZ");
			raRefNumber.setValue(returnId);
			ShipServiceStub.ReferenceNumberType[] refNumbers = { orderRefNumber, raRefNumber };
			pkg1.setReferenceNumber(refNumbers);
			pkgArray[count] = pkg1;
			count++;
		}

		shipment.setPackage(pkgArray);

		ShipServiceStub.PaymentInfoType payInfo = new ShipServiceStub.PaymentInfoType();
		ShipServiceStub.ShipmentChargeType shpmntCharge = new ShipServiceStub.ShipmentChargeType();
		shpmntCharge.setType("01");
		ShipServiceStub.BillShipperType billShipper = new ShipServiceStub.BillShipperType();
		billShipper.setAccountNumber("107388");
		shpmntCharge.setBillShipper(billShipper);
		ShipServiceStub.ShipmentChargeType[] shpmntChargeArray = { shpmntCharge };
		payInfo.setShipmentCharge(shpmntChargeArray);
		shipment.setPaymentInformation(payInfo);

		ShipServiceStub.LabelSpecificationType labelSpecType = new ShipServiceStub.LabelSpecificationType();
		ShipServiceStub.LabelImageFormatType labelImageFormat = new ShipServiceStub.LabelImageFormatType();
		labelImageFormat.setCode("GIF");
		labelImageFormat.setDescription("GIF");
		labelSpecType.setLabelImageFormat(labelImageFormat);
		labelSpecType.setHTTPUserAgent("Mozilla/4.5");
		shipRequest.setLabelSpecification(labelSpecType);

		shipRequest.setShipment(shipment);

		ShipServiceStub.ShipmentResponse shipResponse = shipServiceStub.processShipment(shipRequest, createShipSecurity());

		String statusCode = shipResponse.getResponse().getResponseStatus().getCode();
		String description = shipResponse.getResponse().getResponseStatus().getDescription();
		String trackingNumber = shipResponse.getShipmentResults().getShipmentIdentificationNumber();
		String base64Label = shipResponse.getShipmentResults().getPackageResults()[0].getShippingLabel().getGraphicImage();

		Debug.logError("statusCode: " + statusCode, module);
		Debug.logError("description: " + description, module);
		Debug.logError("trackingNumber: " + trackingNumber, module);
		Debug.logError("base64Label: " + base64Label, module);

		return base64Label;
	}

	public static List<Map> getTimeInTransit(String zipTo, String cityTo, String stateTo, String countryTo) throws Exception {
		List<Map> shipMethodAndTNT = new ArrayList();

		TimeInTransitServiceStub tntServiceStub = new TimeInTransitServiceStub(TNT_URL);
		TimeInTransitRequest tntRequest = new TimeInTransitRequest();
		TimeInTransitServiceStub.RequestType request = new com.ups.www.wsdl.xoltws.tnt.v1_0.TimeInTransitServiceStub.RequestType();
		String[] requestOption = { "TNT" };
		request.setRequestOption(requestOption);
		tntRequest.setRequest(request);

		//build the shipFrom data
		RequestShipFromType shipFrom = new RequestShipFromType();
		RequestShipFromAddressType addressFrom = new RequestShipFromAddressType();
		addressFrom.setCity(SHIPPER_ADDRESS.get("city"));
		addressFrom.setCountryCode(SHIPPER_ADDRESS.get("countryGeoId"));
		addressFrom.setPostalCode(SHIPPER_ADDRESS.get("postalCode"));
		addressFrom.setStateProvinceCode(SHIPPER_ADDRESS.get("stateProvinceGeoId"));
		shipFrom.setAddress(addressFrom);
		tntRequest.setShipFrom(shipFrom);

		//build the shipTo data
		RequestShipToType shipTo = new RequestShipToType();
		RequestShipToAddressType addressTo = new RequestShipToAddressType();
		addressTo.setCity(cityTo);
		addressTo.setCountryCode(countryTo);
		addressTo.setPostalCode(zipTo);
		addressTo.setStateProvinceCode(stateTo);
		shipTo.setAddress(addressTo);
		tntRequest.setShipTo(shipTo);

		//set date for fake pickup
		Timestamp nowTime = UtilDateTime.nowTimestamp();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		PickupType pickup = new PickupType();
		pickup.setDate(dateFormat.format(nowTime));
		tntRequest.setPickup(pickup);

		//set data for a fake package
		ShipmentWeightType shipmentWeight = new ShipmentWeightType();
		shipmentWeight.setWeight("10");
		TimeInTransitServiceStub.CodeDescriptionTypeE unitOfMeasurement = new TimeInTransitServiceStub.CodeDescriptionTypeE();
		unitOfMeasurement.setCode("LBS");
		unitOfMeasurement.setDescription("Pounds");
		shipmentWeight.setUnitOfMeasurement(unitOfMeasurement);
		tntRequest.setShipmentWeight(shipmentWeight);

		//set more data for fake pickup
		tntRequest.setTotalPackagesInShipment("1");
		TimeInTransitServiceStub.InvoiceLineTotalType invoiceLineTotal = new TimeInTransitServiceStub.InvoiceLineTotalType();
		invoiceLineTotal.setCurrencyCode("USD");
		invoiceLineTotal.setMonetaryValue("10");
		tntRequest.setInvoiceLineTotal(invoiceLineTotal);
		tntRequest.setMaximumListSize("1");

		//send request and get response
		TimeInTransitResponse tntResponse = tntServiceStub.processTimeInTransit(tntRequest, createTNTSecurity());
		String statusCode = tntResponse.getResponse().getResponseStatus().getCode();
		String description = tntResponse.getResponse().getResponseStatus().getDescription();

		//get list of ServiceSummary (Array)
		TimeInTransitResponseChoice_type0 tntTransitResponseChoice = tntResponse.getTimeInTransitResponseChoice_type0();
		TransitResponseType tntTransitResponse = tntTransitResponseChoice.getTransitResponse();
		ServiceSummaryType[] tntServiceSummaryList = tntTransitResponse.getServiceSummary();
		List summaryList = Arrays.asList(tntServiceSummaryList);
		Iterator summaryListIter = summaryList.iterator();
		while(summaryListIter.hasNext()){
			Map<String, String> summaryMap = new HashMap();
			ServiceSummaryType serviceSummary = (ServiceSummaryType)summaryListIter.next();
			TimeInTransitServiceStub.CodeDescriptionTypeE service = serviceSummary.getService();
			String serviceCode = service.getCode();
			String serviceDesc = service.getDescription();
			EstimatedArrivalType etaInfo = serviceSummary.getEstimatedArrival();
			String inTransitTime = etaInfo.getBusinessDaysInTransit();
			PickupType arrivalInfo = etaInfo.getArrival();
			String etaDate = arrivalInfo.getDate();
			String etaTime = arrivalInfo.getTime();
			if(UtilValidate.isNotEmpty(serviceCode) && UtilValidate.isNotEmpty(serviceDesc) && UtilValidate.isNotEmpty(inTransitTime) && UtilValidate.isNotEmpty(etaDate) && UtilValidate.isNotEmpty(etaTime)) {
				summaryMap.put("serviceCode", serviceCode);
				summaryMap.put("serviceDesc", serviceDesc);
				summaryMap.put("inTransitTime", inTransitTime);
				summaryMap.put("etaDate", etaDate);
				summaryMap.put("etaTime", etaTime);
				shipMethodAndTNT.add(summaryMap);
				//Debug.logError("SUMMARY: TNT = " + inTransitTime + " serviceDesc = " + serviceDesc + "[" + serviceCode + "], DATE = " + etaDate + ", TIME = " + etaTime, module);
			}
		}
		//Debug.logError("statusCode: " + statusCode, module);
		//Debug.logError("description: " + description, module);
		return shipMethodAndTNT;
	}

	/*
	 * Get UPS Ship Rates
	 */
	public static Map<String, BigDecimal> getRates(Map<String, String> shipToAddress, List<BigDecimal> packageWeights, GenericValue shipmentMethod) throws Exception {
		Map<String, BigDecimal> rates = new HashMap<>();

		try {
			RateServiceStub rateServiceStub = new RateServiceStub(RATE_URL);

			RateRequest rateRequest = new RateRequest();
			RateServiceStub.RequestType request = new RateServiceStub.RequestType();
			String[] requestOption = { "shop" };
			request.setRequestOption(requestOption);
			rateRequest.setRequest(request);

			RateServiceStub.ShipmentType shpmnt = new RateServiceStub.ShipmentType();

			/** *******Shipper*********************/
			shpmnt.setShipper(createRATEShipper(SHIPPER_ADDRESS));
			/** ******Shipper**********************/

			/** ************ShipFrom*******************/
			shpmnt.setShipFrom(createRATEShipFrom(SHIPPER_ADDRESS));
			/** ***********ShipFrom**********************/

			/** ************ShipTo*******************/
			shpmnt.setShipTo(createRATEShipTo(shipToAddress));
			/** ***********ShipTo**********************/

			/**********Service********************** */
			RateServiceStub.CodeDescriptionTypeE service = new RateServiceStub.CodeDescriptionTypeE();
			//Debug.logError("METHOD: " + shipmentMethod.getString("shipmentMethodTypeId"), module);
			service.setCode(UPS_SERVICE_MAP_FOR_RATES.get(shipmentMethod.getString("shipmentMethodTypeId")));
			service.setDescription(UPS_SERVICE_ID.get(shipmentMethod.getString("shipmentMethodTypeId")));
			shpmnt.setService(service);
			/** ********Service***********************/

			/********************Package***************** */
			RateServiceStub.PackageType[] pkgArray = new RateServiceStub.PackageType[ packageWeights.size() ];
			int count = 0;
			for(BigDecimal packageWeight : packageWeights) {
				RateServiceStub.PackageType pkg1 = new RateServiceStub.PackageType();
				RateServiceStub.CodeDescriptionTypeE pkgingType = new RateServiceStub.CodeDescriptionTypeE();
				pkgingType.setCode("02");
				pkgingType.setDescription("Customer Supplied Package");
				pkg1.setPackagingType(pkgingType);
				RateServiceStub.PackageWeightType pkgWeight = new RateServiceStub.PackageWeightType();
				RateServiceStub.CodeDescriptionTypeE uomType = new RateServiceStub.CodeDescriptionTypeE();
				uomType.setCode("lbs");
				uomType.setDescription("Pounds");
				pkgWeight.setUnitOfMeasurement(uomType);
				pkgWeight.setWeight(packageWeight.toString());
				pkg1.setPackageWeight(pkgWeight);
				pkgArray[count] = pkg1;
				count++;
			}
			shpmnt.setPackage(pkgArray);
			/********************Package******************/
			rateRequest.setShipment(shpmnt);

			RateResponse rateResponse = rateServiceStub.processRate(rateRequest, createRATESecurity());

			String statusCode = rateResponse.getResponse().getResponseStatus().getCode();
			String description = rateResponse.getResponse().getResponseStatus().getDescription();
			RatedShipmentType[] shipments = rateResponse.getRatedShipment();
			for(int i =0; i < shipments.length; i++) {
				String serviceCode = shipments[i].getService().getCode();
				String totalCharges = shipments[i].getTotalCharges().getMonetaryValue();
				if(UtilValidate.isNotEmpty(UPS_SERVICE_MAP.get(serviceCode))) {
					rates.put(UPS_SERVICE_MAP.get(serviceCode), new BigDecimal(totalCharges));
				}
				//Debug.logError("Total Charge: " + totalCharges, module);
				//Debug.logError("Total + Surcharge: " + ShippingHelper.getShippingSurcharges(shipmentMethod, shipToAddress, new BigDecimal(totalCharges), packageWeights.get(0)), module);
			}

			//Debug.logError("statusCode: " + statusCode, module);
			//Debug.logError("description: " + description, module);
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error trying to get rates. " + e + " : " + e.getMessage(), module);
		}

		return rates;
	}

	public static Map<String, Object> getTrackingData(String trackingNumber) {
		Map<String, Object> trackData = new HashMap<>();

		try {
			TrackServiceStub trackServiceStub = new TrackServiceStub(TRACK_URL);

			TrackServiceStub.TrackRequest trackRequest = new TrackServiceStub.TrackRequest();
			TrackServiceStub.RequestType request = new TrackServiceStub.RequestType();
			String[] requestOption = { "15" };
			request.setRequestOption(requestOption);
			trackRequest.setRequest(request);
			trackRequest.setInquiryNumber(trackingNumber);
			trackRequest.setTrackingOption("02");

			TrackServiceStub.TrackResponse trackResponse = trackServiceStub.processTrack(trackRequest, createTrackSecurity());

			BigDecimal weight = BigDecimal.ZERO;
			String statusCode = trackResponse.getResponse().getResponseStatus().getCode();
			if(statusCode.equalsIgnoreCase("1")) {
				TrackServiceStub.ShipmentType[] shipments = trackResponse.getShipment();
				for(TrackServiceStub.ShipmentType shipment : shipments) {
					trackData.put("pickupDate", EnvConstantsUtil.MDY.format(EnvConstantsUtil.yyyyMMdd.parse(shipment.getPickupDate())));

					if(shipment.getDeliveryDetail() != null) {
						TrackServiceStub.DeliveryDetailType[] deliveries = shipment.getDeliveryDetail();
						for (TrackServiceStub.DeliveryDetailType delivery : deliveries) {
							trackData.put("deliveryDate", EnvConstantsUtil.MDY.format(EnvConstantsUtil.yyyyMMdd.parse(delivery.getDate())));
						}
					}

					if(UtilValidate.isNotEmpty(shipment.getShipmentWeight())) {
						weight = weight.add(new BigDecimal(shipment.getShipmentWeight().getWeight()));
					}
				}
				if(weight.compareTo(BigDecimal.ZERO) == 0) {
					weight = BigDecimal.ONE;
				}
				trackData.put("weight", weight);
			}
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error trying to get tracking data. " + e + " : " + e.getMessage(), module);
		}

		return trackData;
	}

	/*
	 * Create UPSSecurity object
	 */
	private static TrackServiceStub.UPSSecurity createTrackSecurity() {
		TrackServiceStub.UPSSecurity upss = new TrackServiceStub.UPSSecurity();
		TrackServiceStub.ServiceAccessToken_type0 upsSvcToken = new TrackServiceStub.ServiceAccessToken_type0();
		upsSvcToken.setAccessLicenseNumber(ACCESS_KEY);
		upss.setServiceAccessToken(upsSvcToken);
		TrackServiceStub.UsernameToken_type0 upsSecUsrnameToken = new TrackServiceStub.UsernameToken_type0();
		upsSecUsrnameToken.setUsername(USER_NAME);
		upsSecUsrnameToken.setPassword(PASS_WORD);
		upss.setUsernameToken(upsSecUsrnameToken);

		return upss;
	}

	private static ShipServiceStub.UPSSecurity createShipSecurity() {
		ShipServiceStub.UPSSecurity upss = new ShipServiceStub.UPSSecurity();
		ShipServiceStub.ServiceAccessToken_type0 upsSvcToken = new ShipServiceStub.ServiceAccessToken_type0();
		upsSvcToken.setAccessLicenseNumber(ACCESS_KEY);
		upss.setServiceAccessToken(upsSvcToken);
		ShipServiceStub.UsernameToken_type0 upsSecUsrnameToken = new ShipServiceStub.UsernameToken_type0();
		upsSecUsrnameToken.setUsername(USER_NAME);
		upsSecUsrnameToken.setPassword(PASS_WORD);
		upss.setUsernameToken(upsSecUsrnameToken);

		return upss;
	}

	private static TimeInTransitServiceStub.UPSSecurity createTNTSecurity() {
		TimeInTransitServiceStub.UPSSecurity upss = new TimeInTransitServiceStub.UPSSecurity();
		TimeInTransitServiceStub.ServiceAccessToken_type0 token = new TimeInTransitServiceStub.ServiceAccessToken_type0();
		token.setAccessLicenseNumber(ACCESS_KEY);
		upss.setServiceAccessToken(token);
		TimeInTransitServiceStub.UsernameToken_type0 upsUsrToken = new TimeInTransitServiceStub.UsernameToken_type0();
		upsUsrToken.setPassword(PASS_WORD);
		upsUsrToken.setUsername(USER_NAME);
		upss.setUsernameToken(upsUsrToken);

		return upss;
	}

	private static RateServiceStub.UPSSecurity createRATESecurity() {
		RateServiceStub.UPSSecurity upss = new RateServiceStub.UPSSecurity();
		RateServiceStub.ServiceAccessToken_type0 token = new RateServiceStub.ServiceAccessToken_type0();
		token.setAccessLicenseNumber(ACCESS_KEY);
		upss.setServiceAccessToken(token);
		RateServiceStub.UsernameToken_type0 upsUsrToken = new RateServiceStub.UsernameToken_type0();
		upsUsrToken.setPassword(PASS_WORD);
		upsUsrToken.setUsername(USER_NAME);
		upss.setUsernameToken(upsUsrToken);

		return upss;
	}

	/*
	 * Create Shipper object
	 */
	private static ShipServiceStub.ShipperType createSHIPShipper(Map<String, String> address) {
		ShipServiceStub.ShipperType shipper = new ShipServiceStub.ShipperType();
		shipper.setName(SHIPPER_ADDRESS.get("toName"));
		shipper.setAttentionName(SHIPPER_ADDRESS.get("attnName"));
		shipper.setShipperNumber(UPS_ACCOUNT);
		shipper.setPhone(createShipPhone(SHIPPER_ADDRESS.get("phone")));
		shipper.setAddress(createAddress(address));

		return shipper;
	}

	private static RateServiceStub.ShipperType createRATEShipper(Map<String, String> address) {
		RateServiceStub.ShipperType shipper = new RateServiceStub.ShipperType();
		shipper.setName(SHIPPER_ADDRESS.get("toName"));
		shipper.setShipperNumber(UPS_ACCOUNT);
		shipper.setAddress(createRATEAddress(address));

		return shipper;
	}

	/*
	 * Create ShipFrom object
	 */
	private static ShipServiceStub.ShipFromType createSHIPShipFrom(Map<String, String> address) {
		ShipServiceStub.ShipFromType shipFrom = new ShipServiceStub.ShipFromType();
		shipFrom.setName(address.get("toName"));
		shipFrom.setAttentionName(address.get("attnName"));
		shipFrom.setAddress(createAddress(address));

		return shipFrom;
	}

	private static RateServiceStub.ShipFromType createRATEShipFrom(Map<String, String> address) {
		RateServiceStub.ShipFromType shipFrom = new RateServiceStub.ShipFromType();
		shipFrom.setName(address.get("toName"));
		shipFrom.setAddress(createRATEAddress(address));

		return shipFrom;
	}

	/*
	 * Create ShipTo object
	 */
	private static ShipServiceStub.ShipToType createShipTo(Map<String, String> address) {
		ShipServiceStub.ShipToType shipTo = new ShipServiceStub.ShipToType();
		shipTo.setName(address.get("toName"));
		shipTo.setAttentionName(address.get("attnName"));
		shipTo.setAddress(createToAddress(address));

		return shipTo;
	}

	private static RateServiceStub.ShipToType createRATEShipTo(Map<String, String> address) {
		RateServiceStub.ShipToType shipTo = new RateServiceStub.ShipToType();
		shipTo.setName(address.get("firstName"));
		shipTo.setAddress(createRATEToAddress(address));

		return shipTo;
	}

	/*
	 * Create ShipAddressType object
	 */
	private static ShipAddressType createAddress(Map<String, String> address) {
		ShipAddressType shipAddressType = new ShipAddressType();
		String[] addressLines = { address.get("address1") };
		shipAddressType.setAddressLine(addressLines);
		shipAddressType.setCity(address.get("city"));
		shipAddressType.setPostalCode(address.get("postalCode"));
		shipAddressType.setStateProvinceCode(address.get("stateProvinceGeoId"));
		shipAddressType.setCountryCode(address.get("countryGeoId"));

		return shipAddressType;
	}

	private static RateServiceStub.AddressType createRATEAddress(Map<String, String> address) {
		RateServiceStub.AddressType shipAddressType = new RateServiceStub.AddressType();
		String[] addressLines = { address.get("address1") };
		shipAddressType.setAddressLine(addressLines);
		shipAddressType.setCity(address.get("city"));
		shipAddressType.setPostalCode(address.get("postalCode"));
		shipAddressType.setStateProvinceCode(address.get("stateProvinceGeoId"));
		shipAddressType.setCountryCode(address.get("countryGeoId"));

		return shipAddressType;
	}

	/*
	 * Create ShipToAddressType object
	 */
	private static ShipServiceStub.ShipToAddressType createToAddress(Map<String, String> address) {
		ShipServiceStub.ShipToAddressType shipAddressType = new ShipServiceStub.ShipToAddressType();
		String[] addressLines = { address.get("address1") };
		shipAddressType.setAddressLine(addressLines);
		shipAddressType.setCity(address.get("city"));
		shipAddressType.setPostalCode(address.get("postalCode"));
		shipAddressType.setStateProvinceCode(address.get("stateProvinceGeoId"));
		shipAddressType.setCountryCode(address.get("countryGeoId"));

		return shipAddressType;
	}

	private static RateServiceStub.ShipToAddressType createRATEToAddress(Map<String, String> address) {
		RateServiceStub.ShipToAddressType shipAddressType = new RateServiceStub.ShipToAddressType();
		String[] addressLines = { address.get("address1") };
		shipAddressType.setAddressLine(addressLines);
		shipAddressType.setCity(address.get("city"));
		shipAddressType.setPostalCode(address.get("postalCode"));
		shipAddressType.setStateProvinceCode(address.get("stateProvinceGeoId"));
		if(ShippingHelper.isUSTerritory(address.get("postalCode"))) {
			shipAddressType.setCountryCode(ShippingHelper.getUSTerritoryGeoId(address.get("postalCode")));
		} else {
			shipAddressType.setCountryCode((UtilValidate.isNotEmpty(address.get("countryGeoId")) && address.get("countryGeoId").equalsIgnoreCase("USA")) ? "US" : address.get("countryGeoId"));
		}

		return shipAddressType;
	}

	/*
	 * Create ShipPhoneType object
	 */
	private static ShipPhoneType createShipPhone(String phone) {
		ShipPhoneType shipperPhone = new ShipPhoneType();
		shipperPhone.setNumber(phone);

		return shipperPhone;
	}
}