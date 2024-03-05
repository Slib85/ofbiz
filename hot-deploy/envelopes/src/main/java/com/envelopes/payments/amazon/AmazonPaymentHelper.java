/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.payments.amazon;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.GeneralException;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilHttp;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;

import java.util.Properties;

import com.amazonservices.mws.offamazonpayments.*;
import com.amazonservices.mws.offamazonpayments.model.*;

import com.envelopes.util.*;

public class AmazonPaymentHelper {
	public static final String module = AmazonPaymentHelper.class.getName();

	private static String LOGIN_APP_ID;
	private static String LOGIN_CLIENT_ID;
	private static String LOGIN_SECRET;
	private static String MWS_KEY;
	private static String MWS_SECRET;
	private static String MWS_SELLER_ID;
	private static String MWS_APP_NAME;
	private static String MWS_APP_VERSION;
	private static String MWS_REGION;
	private static String MWS_ENV;
	private static Integer TRAN_TIMEOUT;
	public static String MWS_AUTO_CAPTURE;

	static {
		try{
			LOGIN_APP_ID = UtilProperties.getPropertyValue("envelopes", "amazon.login.appid");
			LOGIN_CLIENT_ID = UtilProperties.getPropertyValue("envelopes", "amazon.login.clientid");
			LOGIN_SECRET = UtilProperties.getPropertyValue("envelopes", "amazon.login.secret");
			MWS_KEY = UtilProperties.getPropertyValue("envelopes", "amazon.mws.key");
			MWS_SECRET = UtilProperties.getPropertyValue("envelopes", "amazon.mws.secret");
			MWS_SELLER_ID = UtilProperties.getPropertyValue("envelopes", "amazon.mws.sellerId");
			MWS_APP_NAME = UtilProperties.getPropertyValue("envelopes", "amazon.mws.applicationName");
			MWS_APP_VERSION = UtilProperties.getPropertyValue("envelopes", "amazon.mws.applicationVersion");
			MWS_REGION = UtilProperties.getPropertyValue("envelopes", "amazon.mws.region");
			MWS_ENV = UtilProperties.getPropertyValue("envelopes", "amazon.mws.environment");
			MWS_AUTO_CAPTURE = UtilProperties.getPropertyValue("envelopes", "amazon.mws.autocapture");
			TRAN_TIMEOUT = 60;
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error, could not open: " + "envelopes.properties", module);
		}
	}


	/**
	 * Get the response object for a order refund request
	 */
	public static RefundResponse setRefundResponse(String orderPaymentPreferenceId, String amazonCaptureId, String amount, String reason) {
		OffAmazonPaymentsService service = createAmazonPaymentService();

		RefundResponse response = null;

		try {
			response = createAmazonPaymentService().refund(setRefundRequest(orderPaymentPreferenceId, amazonCaptureId, amount, reason));
		} catch (OffAmazonPaymentsServiceException ex) {
			EnvUtil.reportError(ex);
			Debug.logError(ex, "Error trying to send request", module);
		}

		return response;
	}

	/**
	 * Create the request object for an amazon order refund to Amazon
	 */
	protected static RefundRequest setRefundRequest(String orderPaymentPreferenceId, String amazonCaptureId, String amount, String reason) {
		RefundRequest request = new RefundRequest();
		request.setSellerId(MWS_SELLER_ID);
		request.setAmazonCaptureId(amazonCaptureId);
		request.setRefundReferenceId(orderPaymentPreferenceId + "-" + UtilDateTime.nowAsString() + "-REFUND");
		request.setRefundAmount(new Price(amount, "USD"));

		return request;
	}

	/**
	 * Get the response object for a order close request
	 */
	public static CloseOrderReferenceResponse setCloseOrderReferenceResponse(String amazonOrderReferenceId, String reason) {
		OffAmazonPaymentsService service = createAmazonPaymentService();

		CloseOrderReferenceResponse response = null;

		try {
			response = createAmazonPaymentService().closeOrderReference(setCloseOrderReferenceRequest(amazonOrderReferenceId, reason));
		} catch (OffAmazonPaymentsServiceException ex) {
			EnvUtil.reportError(ex);
			Debug.logError(ex, "Error trying to send request", module);
		}

		return response;
	}

	/**
	 * Create the request object for an amazon order close to Amazon
	 */
	protected static CloseOrderReferenceRequest setCloseOrderReferenceRequest(String amazonOrderReferenceId, String reason) {
		CloseOrderReferenceRequest request = new CloseOrderReferenceRequest();
		request.setSellerId(MWS_SELLER_ID);
		request.setAmazonOrderReferenceId(amazonOrderReferenceId);
		request.setClosureReason(reason);

		return request;
	}

	/**
	 * Get the response object for a order capture request
	 */
	public static CaptureResponse setCaptureResponse(String amazonAuthorizationId, String paymentMethodId, String amount) {
		OffAmazonPaymentsService service = createAmazonPaymentService();

		CaptureResponse response = null;

		try {
			response = createAmazonPaymentService().capture(setCaptureRequest(amazonAuthorizationId, paymentMethodId, amount));
		} catch (OffAmazonPaymentsServiceException ex) {
			EnvUtil.reportError(ex);
			Debug.logError(ex, "Error trying to send request", module);
		}

		return response;
	}

	/**
	 * Create the request object for an amazon order capture to Amazon
	 */
	protected static CaptureRequest setCaptureRequest(String amazonAuthorizationId, String paymentMethodId, String amount) {
		CaptureRequest request = new CaptureRequest();
		request.setSellerId(MWS_SELLER_ID);
		request.setAmazonAuthorizationId(amazonAuthorizationId);
		request.setCaptureReferenceId(paymentMethodId + "-CAPTURE");
		request.setCaptureAmount(new Price(amount, "USD"));
		//request.setSellerCaptureNote('');
		//request.setTransactionTimeout(TRAN_TIMEOUT);

		return request;
	}

	/**
	 * Get the response object for a order detail request
	 */
	public static AuthorizeResponse setAuthorizeResponse(String amazonOrderReferenceId, String paymentMethodId, String amount) {
		OffAmazonPaymentsService service = createAmazonPaymentService();

		AuthorizeResponse response = null;

		try {
			response = createAmazonPaymentService().authorize(setAuthorizeRequest(amazonOrderReferenceId, paymentMethodId, amount));
		} catch (OffAmazonPaymentsServiceException ex) {
			EnvUtil.reportError(ex);
			Debug.logError(ex, "Error trying to send request", module);
		}

		return response;
	}

	/**
	 * Create the request object for an amazon order detail to Amazon
	 */
	protected static AuthorizeRequest setAuthorizeRequest(String amazonOrderReferenceId, String paymentMethodId, String amount) {
		AuthorizeRequest request = new AuthorizeRequest();
		request.setSellerId(MWS_SELLER_ID);
		request.setAmazonOrderReferenceId(amazonOrderReferenceId);
		request.setAuthorizationReferenceId(paymentMethodId + "-AUTH");
		request.setAuthorizationAmount(new Price(amount, "USD"));
		//request.setSellerAuthorizationNote('');
		//request.setTransactionTimeout(TRAN_TIMEOUT);
		request.setCaptureNow(false);

		return request;
	}

	/**
	 * Get the response object for a order confirm request
	 */
	public static ConfirmOrderReferenceResponse setConfirmOrderReferenceResponse(String amazonOrderReferenceId) {
		OffAmazonPaymentsService service = createAmazonPaymentService();

		ConfirmOrderReferenceResponse response = null;

		try {
			response = createAmazonPaymentService().confirmOrderReference(setConfirmOrderReferenceRequest(amazonOrderReferenceId));
		} catch (OffAmazonPaymentsServiceException ex) {
			EnvUtil.reportError(ex);
			Debug.logError(ex, "Error trying to send request", module);
		}

		return response;
	}

	/**
	 * Create the request object for an amazon order confirm to Amazon
	 */
	protected static ConfirmOrderReferenceRequest setConfirmOrderReferenceRequest(String amazonOrderReferenceId) {
		ConfirmOrderReferenceRequest request = new ConfirmOrderReferenceRequest();
		request.setSellerId(MWS_SELLER_ID);
		request.setAmazonOrderReferenceId(amazonOrderReferenceId);

		return request;
	}

	/**
	 * Get the response object for a order detail request
	 */
	public static SetOrderReferenceDetailsResponse setOrderReferenceDetailsResponse(String amazonOrderReferenceId, String grandTotal, String orderId, String orderNotes) {
		OffAmazonPaymentsService service = createAmazonPaymentService();

		SetOrderReferenceDetailsResponse response = null;

		try {
			response = createAmazonPaymentService().setOrderReferenceDetails(setOrderReferenceDetailsRequest(amazonOrderReferenceId, grandTotal, orderId, orderNotes));
		} catch (OffAmazonPaymentsServiceException ex) {
			EnvUtil.reportError(ex);
			Debug.logError(ex, "Error trying to send request", module);
		}

		return response;
	}

	/**
	 * Create the request object for an amazon order detail to Amazon
	 */
	protected static SetOrderReferenceDetailsRequest setOrderReferenceDetailsRequest(String amazonOrderReferenceId, String grandTotal, String orderId, String orderNotes) {
		SetOrderReferenceDetailsRequest request = new SetOrderReferenceDetailsRequest();
		request.setSellerId(MWS_SELLER_ID);
		request.setAmazonOrderReferenceId(amazonOrderReferenceId);

		if(UtilValidate.isNotEmpty(grandTotal)) {
			OrderTotal orderTotal = new OrderTotal("USD", grandTotal);

			SellerOrderAttributes sellerAttr = null;
			if(UtilValidate.isNotEmpty(orderId)) {
				sellerAttr = new SellerOrderAttributes(orderId, null, null, null);
			}

			OrderReferenceAttributes orderAttr = new OrderReferenceAttributes(orderTotal, null, orderNotes, sellerAttr);
			request.setOrderReferenceAttributes(orderAttr);
		}

		return request;
	}

	/**
	 * Create the service stub object that is the basis of all requests/responses
	 */
	protected static OffAmazonPaymentsService createAmazonPaymentService() {
		Properties properties = new Properties();
		properties.setProperty("accessKeyId", MWS_KEY);
		properties.setProperty("secretAccessKey", MWS_SECRET);
		properties.setProperty("applicationName", MWS_APP_NAME);
		properties.setProperty("applicationVersion", MWS_APP_VERSION);
		properties.setProperty("sellerId", MWS_SELLER_ID);
		properties.setProperty("environment", MWS_ENV);
		properties.setProperty("region", MWS_REGION);
		properties.setProperty("clientId", LOGIN_CLIENT_ID);
		OffAmazonPaymentsServiceConfig config = new OffAmazonPaymentsServiceConfig(properties);
		OffAmazonPaymentsService service = new OffAmazonPaymentsServiceClient(config);
		return service;
	}
}