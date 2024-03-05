/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.email;

import java.io.*;
import java.lang.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.bigname.core.config.SiteConfig;
import com.bigname.quote.calculator.CalculatorHelper;
import com.envelopes.bronto.BrontoTranslator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericDataSourceException;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.order.order.OrderReadHelper;
import org.apache.ofbiz.entity.jdbc.SQLProcessor;

import com.bronto.api.v4.*;

import com.envelopes.order.OrderHelper;
import com.envelopes.product.ProductHelper;
import com.envelopes.util.*;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import com.google.gson.internal.LinkedTreeMap;

import static com.envelopes.party.PartyHelper.TO_NAME;
import static com.envelopes.party.PartyHelper.splitAtFirstSpace;

public class EmailHelper {
	public static final String module = EmailHelper.class.getName();
	public static final String BRONTO_STATUS = UtilProperties.getPropertyValue("envelopes", "envelopes.bronto.status");

	/*
	 * Send email
	 */
	public static boolean sendEmail(Map<String, Object> rawData, Map<String, String> data, String email, String messageType, boolean sendCopy, String webSiteId) throws Exception {
		if(UtilValidate.isEmpty(webSiteId)) {
			webSiteId = "envelopes";
		}

		boolean success = false;
		if(rawData == null) {
			rawData = new HashMap<>();
		}
		ContactObject contact = getContact(email, webSiteId);
		contact = addOrUpdateContact(createContact(email, new BrontoTranslator().translate(rawData, contact != null, webSiteId), contact, webSiteId), webSiteId);
		DeliveryObject delivery = sendDelivery(data, contact, messageType, webSiteId);
		if(delivery != null) {
			success = true;
		}

		if(sendCopy) {
			ContactObject contactBCC = addOrUpdateContact(createContact(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_BCC_EMAIL, null, null, webSiteId), webSiteId);
			sendDelivery(data, contactBCC, messageType, webSiteId);
		}

		if(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_CC_EMAIL_ADDRESSES.containsKey(messageType)) {
			for (String ccEmailAddress : EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_CC_EMAIL_ADDRESSES.get(messageType)) {
				ContactObject contactBCC = addOrUpdateContact(createContact(ccEmailAddress, null, null, webSiteId), webSiteId);
				sendDelivery(data, contactBCC, messageType, webSiteId);
			}
		}

		return success;
	}

	/**
	 * Send SMS message
	 */
	public static boolean sendSMS(Map<String, Object> rawData, Map<String, String> data, String email, String messageType, String webSiteId) throws Exception {
		/*boolean success = false;
		if(rawData == null) {
			rawData = new HashMap<>();
		}
		ContactObject contact = getContact(email, webSiteId);
		if(hasSubscribedForSMSAlerts(contact, webSiteId)) {
			SmsDeliveryObject delivery = sendSMSDelivery(data, contact, messageType, webSiteId);
			if (delivery != null) {
				success = true;
			}
		} else {
			success = true;
		}

		return success;*/
		return true;
	}

	private static boolean hasSubscribedForSMSAlerts(ContactObject contact, String webSiteId) {
		String[] smsKeywords = contact.getSMSKeywordIDs();
		if(smsKeywords != null && smsKeywords.length > 0) {
			for (String smsKeyword : smsKeywords) {
				if(smsKeyword.equals(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_SMS_KEYWORD)) {
					return true;
				}
			}
		}
		return false;
	}

	private static SmsDeliveryObject sendSMSDelivery(Map<String, String> data, ContactObject contact, String messageType, String webSiteId) throws Exception {
		BrontoSoapApiImplServiceStub stub = createStub();
		SessionHeaderE sessionHeader = brontoLogin(stub, webSiteId);

		SmsDeliveryObject delivery = new SmsDeliveryObject();
		delivery.setDeliveryType("triggered");
		delivery.setMessageId(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_SMS_MESSAGE_TYPES.get(messageType));

		GregorianCalendar cal = new GregorianCalendar();
		XMLGregorianCalendar startTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		delivery.setStart(cal);

		SmsDeliveryContactsObject contactsObject = new SmsDeliveryContactsObject();
		contactsObject.setKeyword(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_SMS_KEYWORD);
		contactsObject.setContactIds(new String[]{contact.getId()});

		delivery.setContactRecipients(contactsObject);

		SmsMessageFieldObject msgField = null;
		for (String key : data.keySet()) {
			msgField = new SmsMessageFieldObject();
			msgField.setName(key);
			msgField.setContent(data.get(key));
			delivery.addFields(msgField);
		}

		AddSMSDeliveries req = new AddSMSDeliveries();
		req.addSmsdeliveries(delivery);

		AddSMSDeliveriesE deliveries = new AddSMSDeliveriesE();
		deliveries.setAddSMSDeliveries(req);

		AddSMSDeliveriesResponseE resp = null;

		int counter = 0;
		boolean transactionSuccess = false;

		//if it errors out retry up to max of 5 times
		while(counter < 5 && !transactionSuccess) {
			resp = stub.addSMSDeliveries(deliveries, sessionHeader);
			WriteResult result = resp.getAddSMSDeliveriesResponse().get_return();
			for(ResultItem item : result.getResults()) {
				if(item.getIsError()) {
					transactionSuccess = false;
					Debug.logError("Error: " + item.getErrorCode() + " - " + item.getErrorString(), module);
				} else {
					transactionSuccess = true;
					return delivery;
				}
			}
			counter++;
		}

		return null;
	}

	/*
	 * Create delivery object and send email
	 */
	private static DeliveryObject sendDelivery(Map<String, String> data, ContactObject contact, String messageType, String webSiteId) throws Exception {
		BrontoSoapApiImplServiceStub stub = createStub();
		SessionHeaderE sessionHeader = brontoLogin(stub, webSiteId);

		DeliveryObject delivery = new DeliveryObject();
		delivery.setMessageId((String) EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_MESSAGE_TYPES.get(messageType));
		delivery.setType("transactional");

		String fromEmail = (data.containsKey("oFromEmail")) ? data.get("oFromEmail") : EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FROM_EMAIL;
		String fromName = (data.containsKey("oFromName")) ? data.get("oFromName") : EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_FROM_NAME;
		String replyEmail = (data.containsKey("oReplyEmail")) ? data.get("oReplyEmail") : (EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_PREPRESS_MESSAGE_TYPES.contains(messageType) ? EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_PREPRESS_REPLY_EMAIL : EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_REPLY_EMAIL);

		delivery.setFromEmail(fromEmail);
		delivery.setFromName(fromName);
		delivery.setReplyEmail(replyEmail);

		GregorianCalendar cal = new GregorianCalendar();
		XMLGregorianCalendar startTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		delivery.setStart(cal);

		DeliveryRecipientObject recipient = new DeliveryRecipientObject();
		recipient.setType("contact");
		if(contact != null) {
			recipient.setId(contact.getId());
		}
		delivery.addRecipients(recipient);

		MessageFieldObject msgField = null;
		Iterator dataIter = data.entrySet().iterator();
		while(dataIter.hasNext()) {
			Map.Entry<String, String> pairs = (Map.Entry<String, String>) dataIter.next();
			msgField = new MessageFieldObject();
			msgField.setName((String) pairs.getKey());
			msgField.setType("html");
			msgField.setContent(EnvUtil.convertToString(pairs.getValue()));
			delivery.addFields(msgField);
		}

		AddDeliveries req = new AddDeliveries();
		req.addDeliveries(delivery);

		AddDeliveriesE deliveries = new AddDeliveriesE();
		deliveries.setAddDeliveries(req);

		AddDeliveriesResponseE resp = null;

		int counter = 0;
		boolean transactionSuccess = false;

		//if it errors out retry up to max of 5 times
		while(counter < 5 && !transactionSuccess) {
			resp = stub.addDeliveries(deliveries, sessionHeader);
			WriteResult result = resp.getAddDeliveriesResponse().get_return();
			for(ResultItem item : result.getResults()) {
				if(item.getIsError()) {
					transactionSuccess = false;
					Debug.logError("Error: " + item.getErrorCode() + " - " + item.getErrorString(), module);
				} else {
					transactionSuccess = true;
					return delivery;
				}
			}
			counter++;
		}

		return null;
	}

	/*
	 * Create a contact object with email only
	 */
	public static boolean addOrUpdateContact(String email, Map<String, Object> rawData, String webSiteId) throws Exception {
		return addOrUpdateContact(email, rawData, false, webSiteId);
	}

	@SuppressWarnings("unchecked")
	public static boolean addOrUpdateContact(String email, Object rawData, boolean translated, String webSiteId) throws Exception {
		ContactObject contact = getContact(email, webSiteId);
		Map<String, String> customerData = (!translated) ? new BrontoTranslator().translate((Map<String, Object>) rawData, contact != null, webSiteId) : (Map<String, String>) rawData;
		contact = createContact(email, customerData, contact, webSiteId);
		contact = addOrUpdateContact(contact, webSiteId);

		if(contact != null) {
			return true;
		}

		return false;
	}

	public static boolean isNewContact(String emailAddress, String webSiteId) {
		try {
			return getContact(emailAddress, webSiteId) == null;
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error occurred while finding the email address in Bronto " + emailAddress, module);
		}
		return false;
	}

	/*
	 * Create a contact object with email only
	 */
	private static ContactObject createContact(String email, Map<String, String> data, ContactObject contact, String webSiteId) {
		if(contact == null) {
			contact = new ContactObject();
			String[] listIds = {EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_LIST_ID};
			contact.setEmail(email);
			contact.setStatus("active");
			contact.setListIds(listIds);
		}
		/**
		 * If the contact already exists, check for the MODE parameter. This is used to unsubscribe a subscribed contact or resubscribe an unsubscribed contact.
		 */
		else if(UtilValidate.isNotEmpty(data) && data.containsKey("MODE")) {
			String mode = data.get("MODE");
			if(mode.equals("subscribe")) {
				contact.setStatus("active");
			} else if(mode.equals("unsubscribe")) {
				contact.setStatus("unsub");
			}
		}

		/**
		 * After checking the MODE parameter, remove the parameter from the bronto data map, which contains bronto field data.
		 */
		if(UtilValidate.isNotEmpty(data) && data.containsKey("MODE")) {
			data.remove("MODE");
		}

		if(UtilValidate.isNotEmpty(data)) {
			ContactField field = null;
			Iterator dataIter = data.entrySet().iterator();
			while(dataIter.hasNext()) {
				Map.Entry<String, String> pairs = (Map.Entry<String, String>) dataIter.next();
				field = new ContactField();
				field.setFieldId((String) pairs.getKey());
				field.setContent((String) pairs.getValue());
				contact.addFields(field);
			}
		}

		return contact;
	}

	/*
	 * update an existing contact
	 */
	private static ContactObject addOrUpdateContact(ContactObject contact, String webSiteId) throws Exception {
		BrontoSoapApiImplServiceStub stub = createStub();
		SessionHeaderE sessionHeader = brontoLogin(stub, webSiteId);

		String email = null;
		boolean addContact = false;
		if(contact != null) {
			email = contact.getEmail();
		}

		//check if this contact exists, if so update, if not add
		if(UtilValidate.isNotEmpty(email)) {
			ContactObject contactCheck = getContact(email, webSiteId);
			if(contactCheck == null) {
				addContact = true;
			} else {
				contact.setId(contactCheck.getId());
			}
		}

		if(addContact) {
			AddContacts req = new AddContacts();
			req.addContacts(contact);

			AddContactsE contacts = new AddContactsE();
			contacts.setAddContacts(req);

			AddContactsResponseE resp = null;
			resp = stub.addContacts(contacts, sessionHeader);

			WriteResult result = resp.getAddContactsResponse().get_return();
			for(ResultItem item : result.getResults()) {
				if(item.getIsError()) {
					Debug.logError("Error: " + item.getErrorCode() + " - " + item.getErrorString(), module);
				} else {
					contact.setId(item.getId());
					return contact;
				}
			}
		} else {
			UpdateContacts req = new UpdateContacts();
			req.addContacts(contact);

			UpdateContactsE contacts = new UpdateContactsE();
			contacts.setUpdateContacts(req);

			UpdateContactsResponseE resp = null;
			resp = stub.updateContacts(contacts, sessionHeader);

			WriteResult result = resp.getUpdateContactsResponse().get_return();

			for(ResultItem item : result.getResults()) {
				if(item.getIsError()) {
					Debug.logError("Error: " + item.getErrorCode() + " - " + item.getErrorString(), module);
				} else {
					return contact;
				}
			}
		}

		return null;
	}

	/*
	 * Search for a contact by email address and return it
	 */
	private static ContactObject getContact(String email, String webSiteId) throws Exception {
		BrontoSoapApiImplServiceStub stub = createStub();
		SessionHeaderE sessionHeader = brontoLogin(stub, webSiteId);

		StringValue sVal = new StringValue();
		sVal.setValue(email);
		ContactFilter filter = new ContactFilter();
		filter.addEmail(sVal);

		ReadContacts req = new ReadContacts();
		req.setFilter(filter);
		req.setIncludeLists(true);
		req.setIncludeSMSKeywords(true);
		req.setPageNumber(1);

		ReadContactsE contacts = new ReadContactsE();
		contacts.setReadContacts(req);

		ContactObject contact = null;
		ReadContactsResponseE resp = null;

		resp = stub.readContacts(contacts, sessionHeader);

		ContactObject [] contactList = resp.getReadContactsResponse().get_return();
		if(contactList != null && contactList.length == 1) {
			contact = contactList[0];
		}

		return contact;
	}

	/*
	 * Login and on success return a SessionHeader
	 */
	private static SessionHeaderE brontoLogin(BrontoSoapApiImplServiceStub stub, String webSiteId) throws Exception {
		SessionHeader sessionHeader = new SessionHeader();
		SessionHeaderE sessionHeaderE = new SessionHeaderE();

		Login login = new Login();
		LoginE loginE = new LoginE();

		login.setApiToken(EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_API_KEY);
		loginE.setLogin(login);

		sessionHeader.setSessionId(stub.login(loginE).getLoginResponse().get_return());
		sessionHeaderE.setSessionHeader(sessionHeader);

		return sessionHeaderE;
	}

	/*
	 * Create Order Password Email Data for Bronto
	 */
	public static Map<String, String> createPasswordEmailData(Delegator delegator, Map orderData, String orderId) throws GenericEntityException {
		Map<String, String> data = new HashMap<String, String>();
		if(orderData == null) {
			orderData = OrderHelper.getOrderData(delegator, orderId, false);
		}

		data.put("orderFirstName", ((GenericValue) orderData.get("billingAddress")).getString("toName"));
		data.put("email", (String) orderData.get("email"));
		data.put("tempPassword", (String) orderData.get("password"));

		return data;
	}

	/*
	 * Create Order Confirmation Email Data for Bronto
	 */
	public static Map<String, String> createEmailAddressBookData(Delegator delegator, LocalDispatcher dispatcher, Map orderData, String orderId, String webSiteId) throws GenericEntityException {
		Map<String, String> data = new HashMap<String, String>();

		data.put("ordernumber", orderId);
		data.put("orderFirstName", ((GenericValue) orderData.get("billingAddress")).getString("toName"));

		return data;
	}

	/*
	 * Create Order Confirmation Email Data for Bronto
	 */
	public static Map<String, String> createOrderConfirmationEmailData(Delegator delegator, LocalDispatcher dispatcher, Map orderData, String orderId, String webSiteId) throws GenericEntityException {
		Map<String, String> data = new HashMap<String, String>();
		List<String> quoteAssignedToEmails = new ArrayList<>();

		try {
			if(orderData == null) {
				orderData = OrderHelper.getOrderData(delegator, orderId, false);
			}

			List<Map> payments = (List<Map>) orderData.get("payments");
			String paymentMethodTypeId = null;
			String cardNumber = null;
			String cardType = null;
			String expireDate = null;
			boolean isOrderPrinted = OrderHelper.isOrderPrinted(delegator, orderId);

			for(Map payment : payments) {
				paymentMethodTypeId = (String) payment.get("paymentMethodTypeId");
				cardType = (String) payment.get("cardType");
				cardNumber = (String) payment.get("cardNumber");
				expireDate = (String) payment.get("expireDate");
			}
			data.put("ordernumber", orderId);

			data.put("orderFirstName", ((GenericValue) orderData.get("billingAddress")).getString("toName"));

			//get the header message to display in the email
			SiteConfig siteConfig = new SiteConfig(webSiteId);
			siteConfig.getWebSite(delegator);

			if(isOrderPrinted) {
				if (paymentMethodTypeId.equalsIgnoreCase("EXT_NET30")) {
					data.put("orderHeaderMessage", "Your order <a href=\"https://" + siteConfig.webSiteUrl() + "/account\" style=\"color: #053959; font:bold 12px/17px Helvetica; text-decoration: underline;\">" + orderId + "</a> has been received. We will process the order right away and send you a shipping confirmation email soon. Thank you for shopping at " + siteConfig.webSiteName());
				} else if (OrderHelper.isOrderScene7(delegator, orderId)) {
					if(paymentMethodTypeId.equalsIgnoreCase("CREDIT_CARD")) {
						data.put("orderHeaderMessage", "Your order <a href=\"https://" + siteConfig.webSiteUrl() + "/account\" style=\"color: #053959; font:bold 12px/17px Helvetica; text-decoration: underline;\">" + orderId + "</a> has been received and your credit card has been authorized. We'll get started on it right away and you'll receive a shipment notification once complete.  Thank you for shopping at " + siteConfig.webSiteName());
					} else if(paymentMethodTypeId.equalsIgnoreCase("EXT_AMAZON") || paymentMethodTypeId.equalsIgnoreCase("EXT_PAYPAL_CHECKOUT")) {
						data.put("orderHeaderMessage", "Your order <a href=\"https://" + siteConfig.webSiteUrl() + "/account\" style=\"color: #053959; font:bold 12px/17px Helvetica; text-decoration: underline;\">" + orderId + "</a> has been received and your credit card has been charged. We'll get started on it right away and you'll receive a shipment notification once complete.  Thank you for shopping at " + siteConfig.webSiteName());
					} else {
						data.put("orderHeaderMessage", "Your order <a href=\"https://" + siteConfig.webSiteUrl() + "/account\" style=\"color: #053959; font:bold 12px/17px Helvetica; text-decoration: underline;\">" + orderId + "</a> has been received and is awaiting check receipt.  Once payment has been accepted, we'll get started on it right away and you'll receive a shipment notification once complete.  Thank you for shopping at " + siteConfig.webSiteName());
					}
				} else {
					if(paymentMethodTypeId.equalsIgnoreCase("CREDIT_CARD")) {
						data.put("orderHeaderMessage", "Your order <a href=\"https://" + siteConfig.webSiteUrl() + "/account\" style=\"color: #053959; font:bold 12px/17px Helvetica; text-decoration: underline;\">" + orderId + "</a> has been received and your credit card has been authorized. We'll get started on it right away and will send you your proof within 1 business day. Once approved, production time begins and you'll receive a shipment notification once complete.  Thank you for shopping at " + siteConfig.webSiteName());
					} else if(paymentMethodTypeId.equalsIgnoreCase("EXT_AMAZON") || paymentMethodTypeId.equalsIgnoreCase("EXT_PAYPAL_CHECKOUT")) {
						data.put("orderHeaderMessage", "Your order <a href=\"https://" + siteConfig.webSiteUrl() + "/account\" style=\"color: #053959; font:bold 12px/17px Helvetica; text-decoration: underline;\">" + orderId + "</a> has been received and your credit card has been charged. We'll get started on it right away and will send you your proof within 1 business day. Once approved, production time begins and you'll receive a shipment notification once complete.  Thank you for shopping at " + siteConfig.webSiteName());
					} else {
						data.put("orderHeaderMessage", "Your order <a href=\"https://" + siteConfig.webSiteUrl() + "/account\" style=\"color: #053959; font:bold 12px/17px Helvetica; text-decoration: underline;\">" + orderId + "</a> has been received and is awaiting check receipt.  Once payment has been accepted we'll process the order and send you a shipping confirmation email soon.  Thank you for shopping at " + siteConfig.webSiteName());
					}
				}
			} else {
				if(paymentMethodTypeId.equalsIgnoreCase("CREDIT_CARD")) {
					data.put("orderHeaderMessage", "Your order <a href=\"https://" + siteConfig.webSiteUrl() + "/account\" style=\"color: #053959; font:bold 12px/17px Helvetica; text-decoration: underline;\">" + orderId + "</a> has been received and your credit card has been authorized. We'll get started on it right away and will send you a shipping confirmation email soon. Thank you for shopping at " + siteConfig.webSiteName());
				} else if(paymentMethodTypeId.equalsIgnoreCase("EXT_AMAZON") || paymentMethodTypeId.equalsIgnoreCase("EXT_PAYPAL_CHECKOUT")) {
					data.put("orderHeaderMessage", "Your order <a href=\"https://" + siteConfig.webSiteUrl() + "/account\" style=\"color: #053959; font:bold 12px/17px Helvetica; text-decoration: underline;\">" + orderId + "</a> has been received and your credit card has been charged. We'll get started on it right away and will send you a shipping confirmation email soon. Thank you for shopping at " + siteConfig.webSiteName());
				} else if(paymentMethodTypeId.equalsIgnoreCase("PERSONAL_CHECK")) {
					data.put("orderHeaderMessage", "Your order <a href=\"https://" + siteConfig.webSiteUrl() + "/account\" style=\"color: #053959; font:bold 12px/17px Helvetica; text-decoration: underline;\">" + orderId + "</a> has been received and is awaiting check receipt.  Once payment has been accepted we'll process the order and send you a shipping confirmation email soon. Thank  you for shopping at " + siteConfig.webSiteName());
				} else {
					data.put("orderHeaderMessage", "Your order <a href=\"https://" + siteConfig.webSiteUrl() + "/account\" style=\"color: #053959; font:bold 12px/17px Helvetica; text-decoration: underline;\">" + orderId + "</a> has been received. We'll get started on it right away and will send you a shipping confirmation email soon. Thank you for shopping at " + siteConfig.webSiteName());
				}
			}

			//gather payment data
			if(paymentMethodTypeId.equalsIgnoreCase("CREDIT_CARD")) {
				data.put("orderPaymentMessage", "<div style=\"color: #053959; font:bold 12px/17px Helvetica;\">Payment Information</div><div style=\"font: normal 12px/17px Helvetica; color: #595959;\">Your " + cardType + " " + cardNumber + " has been authorized.");
			} else if(paymentMethodTypeId.equalsIgnoreCase("EXT_AMAZON")) {
				data.put("orderPaymentMessage", "<div style=\"color: #053959; font:bold 12px/17px Helvetica;\">Payment Information</div><div style=\"font: normal 12px/17px Helvetica; color: #595959;\">Your Amazon Order Reference " + cardType + " has been charged.");
			} else if(paymentMethodTypeId.equalsIgnoreCase("EXT_PAYPAL_CHECKOUT")) {
				data.put("orderPaymentMessage", "<div style=\"color: #053959; font:bold 12px/17px Helvetica;\">Payment Information</div><div style=\"font: normal 12px/17px Helvetica; color: #595959;\">Your PayPal Order Reference " + cardType + " has been charged.");
			} else if(paymentMethodTypeId.equalsIgnoreCase("PERSONAL_CHECK")) {
				data.put("orderPaymentMessage", "<div style=\"color: #053959; font:bold 12px/17px Helvetica;\">Payment Information</div><div style=\"font: normal 12px/17px Helvetica; color: #595959;\">Please mail your check to us as soon as possible and we will process your order. Our mailing address is:<br /><br />"  + siteConfig.webSiteName() + "<br />105 Maxess Road<br />Suite S215<br />Melville, NY 11747<br />Attn: Check Order</div>");
			} else {
				data.put("orderPaymentMessage", "");
			}

			//gather order item data
			List<Map> items = (List<Map>) orderData.get("items");

			int i = 1;
			for(Map item : items) {
				GenericValue orderItem = (GenericValue) item.get("item");
				String productName = (EnvConstantsUtil.CUSTOM_PRODUCT.equalsIgnoreCase(orderItem.getString("productId")) || EnvConstantsUtil.SAMPLE_PRODUCT.equalsIgnoreCase(orderItem.getString("productId"))) ? orderItem.getString("itemDescription") : ((GenericValue) item.get("product")).getString("internalName");
				data.put("itemName_" + i, productName + ((orderItem.getBigDecimal("quantity").compareTo(new BigDecimal("5")) <= 0) ? " (SAMPLE)" : ""));
				data.put("itemDescription_" + i, ((GenericValue) item.get("product")).getString("internalName") + ((orderItem.getBigDecimal("quantity").compareTo(new BigDecimal("5")) <= 0) ? " (SAMPLE)" : ""));

				if(UtilValidate.isNotEmpty(orderItem.getString("comments"))) {
					data.put("itemComment_" + i, orderItem.getString("comments"));
					data.put("itemCommentShow_" + i, "inline");
					data.put("itemCommentStart_" + i, "<!-- -->");
					data.put("itemCommentEnds_" + i, "<!-- -->");
				} else {
					data.put("itemComment_" + i, "&nbsp;");
					data.put("itemCommentShow_" + i, "none");
					data.put("itemCommentStart_" + i, "<!--");
					data.put("itemCommentEnds_" + i, "-->");
				}

				if (UtilValidate.isNotEmpty(orderItem.get("referenceQuoteId"))) {
					GenericValue quoteInfo = EntityQuery.use(delegator).from("CustomEnvelope").where("quoteId", orderItem.get("referenceQuoteId")).queryOne();
					if (UtilValidate.isNotEmpty(quoteInfo) && UtilValidate.isNotEmpty(quoteInfo.get("assignedTo")) && !quoteAssignedToEmails.contains((String) quoteInfo.get("assignedTo"))) {
						quoteAssignedToEmails.add((String) quoteInfo.get("assignedTo"));
					}
				}

				if (ProductHelper.getProductFeatures(delegator, (GenericValue) item.get("product"), UtilMisc.toList("COLOR")).size() != 0 && !ProductHelper.getProductFeatures(delegator, (GenericValue) item.get("product"), UtilMisc.toList("COLOR")).get("COLOR").equals("Custom Color")) {
					String itemColorHtml = "" +
						"<tr>\n" +
							"<td width=\"130\" style=\"font: normal 12px/17px Helvetica; color: #595959;\">Color:</td>\n" +
							"<td style=\"font: normal 12px/17px Helvetica; color: #595959;\">" + ProductHelper.getProductFeatures(delegator, (GenericValue) item.get("product"), UtilMisc.toList("COLOR")).get("COLOR") + "</td>\n" +
						"</tr>";

					data.put("itemColor_" + i, itemColorHtml);

					itemColorHtml = "" +
						"<tr>\n" +
							"<td style=\"mso-hide: all; width: 30%; font: normal 5px/7px Helvetica; color: #595959;\">Color:</td>\n" +
							"<td style=\"mso-hide: all; font: normal 5px/7px Helvetica; color: #595959;\">" + ProductHelper.getProductFeatures(delegator, (GenericValue) item.get("product"), UtilMisc.toList("COLOR")).get("COLOR") + "</td>\n" +
						"</tr>";

					data.put("itemColorMobile_" + i, itemColorHtml);
				}
				data.put("itemQty_" + i, EnvUtil.convertBigDecimal(orderItem.getBigDecimal("quantity"), true));
				data.put("itemLink_" + i, ProductHelper.getProductUrl(delegator, null, (String) orderItem.getString("productId"), EnvUtil.getWebsiteId(((GenericValue) orderData.get("orderHeader")).getString("salesChannelEnumId")), true));

				List attributes = (List) item.get("attribute");
				for(Object attributeObj : attributes) {
					GenericValue attribute = (GenericValue) attributeObj;
					if(attribute.getString("attrName").equals("colorsFront")) {
						data.put("itemFrontColor_" + i, (UtilValidate.isNotEmpty(!attribute.getString("attrValue").equals("0"))) ? attribute.getString("attrValue") : "&nbsp;");
					} else if(attribute.getString("attrName").equals("colorsBack")) {
						data.put("itemBackColor_" + i, (UtilValidate.isNotEmpty(!attribute.getString("attrValue").equals("0"))) ? attribute.getString("attrValue") : "&nbsp;");
					}
				}

				if(OrderHelper.isItemPrinted(delegator, null, null, orderItem)) {
					data.put("showPrint_" + i, "inline");
					data.put("commentStart_" + i, "<!-- -->");
					data.put("commentEnd_" + i, "<!-- -->");
				} else {
					data.put("showPrint_" + i, "none");
					data.put("commentStart_" + i, "<!--");
					data.put("commentEnd_" + i, "-->");
				}

				if(UtilValidate.isNotEmpty(orderItem.get("artworkSource"))) {
					data.put("itemArtSource_" + i, EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_PRINT_TYPES.get(orderItem.getString("artworkSource")));
				} else {
					data.put("itemArtSource_" + i, "&nbsp;");
				}

				BigDecimal subTotal = OrderReadHelper.getOrderItemSubTotal(orderItem, (List) orderData.get("adjustments"));
				data.put("itemPrice_" + i, EnvUtil.convertBigDecimal(subTotal, false));
				data.put("itemUnitPrice_" + i, EnvUtil.convertBigDecimal(orderItem.getBigDecimal("unitPrice"), false));

				data.put("itemArtStatus_" + i, (delegator.findOne("StatusItem", UtilMisc.toMap("statusId", orderItem.getString("statusId")), true)).getString("description"));
				i++;
			}

			data.put("quoteAssignedToEmails", String.join( ",", quoteAssignedToEmails));

			boolean hasPromo = false;

			List<Map> orderTotals = OrderHelper.getOrderTotals(delegator, orderId);
			int j = 1;
			for(Map adj : orderTotals) {
				for(Object _entry : adj.entrySet()) {
					Map.Entry entry = (Map.Entry) _entry;
					if(((String) entry.getKey()).equals("Refunds") || ((String) entry.getKey()).equals("Rate")) {
						continue; //we dont want refunds to show in order confirmation
					} else if(((String) entry.getKey()).equals("Subtotal")) {
						data.put("orderSubTotal", EnvUtil.convertBigDecimal((BigDecimal) entry.getValue(), false));
					} else if(((String) entry.getKey()).equals("Tax")) {
						data.put("orderTaxTotal", EnvUtil.convertBigDecimal((BigDecimal) entry.getValue(), false));
					} else if(((String) entry.getKey()).equals("Shipping")) {
						data.put("orderShipTotal", EnvUtil.convertBigDecimal((BigDecimal) entry.getValue(), false));
					} else {
						hasPromo = true;
						data.put("orderAdjDescription_" + j , (String) entry.getKey());
						data.put("orderAdjAmount_" + j, EnvUtil.convertBigDecimal((BigDecimal) entry.getValue(), false));
						j++;
					}
				}
			}

			if(hasPromo) {
				data.put("orderAdjCommentStart", "");
				data.put("orderAdjCommentEnd", "");
			} else {
				data.put("orderAdjCommentStart", "<!--");
				data.put("orderAdjCommentEnd", "-->");
			}

			data.put("orderTotal", ((GenericValue) orderData.get("orderHeader")).getBigDecimal("grandTotal").toString());
			data.put("shippingMethod", (String) orderData.get("shipping"));


			//gather shipping/billing data
			GenericValue shippingAddress = (GenericValue) orderData.get("shippingAddress");
			data.put("shippingAddressName", shippingAddress.getString("toName"));
			data.put("shippingCompanyName", shippingAddress.getString("companyName"));
			data.put("shippingAddress1", shippingAddress.getString("address1"));
			data.put("shippingAddress2", shippingAddress.getString("address2"));
			data.put("shippingCity", shippingAddress.getString("city"));
			data.put("shippingState", shippingAddress.getString("stateProvinceGeoId"));
			data.put("shippingZip", shippingAddress.getString("postalCode"));

			GenericValue billingAddress = (GenericValue) orderData.get("billingAddress");
			data.put("orderFirstName", billingAddress.getString("toName"));
			data.put("billingCompanyName", billingAddress.getString("companyName"));
			data.put("billingAddress1", billingAddress.getString("address1"));
			data.put("billingAddress2", billingAddress.getString("address2"));
			data.put("billingCity", billingAddress.getString("city"));
			data.put("billingState", billingAddress.getString("stateProvinceGeoId"));
			data.put("billingZip", billingAddress.getString("postalCode"));
			data.put("salesRepInfoDesktop", getSalesRepInfoHTML(((HashMap<String, String>) orderData.get("salesRep")).get("email"), ((HashMap<String, String>) orderData.get("salesRep")).get("name"), ((HashMap<String, String>) orderData.get("salesRep")).get("phone"), true));
			data.put("salesRepInfoMobile", getSalesRepInfoHTML(((HashMap<String, String>) orderData.get("salesRep")).get("email"), ((HashMap<String, String>) orderData.get("salesRep")).get("name"), ((HashMap<String, String>) orderData.get("salesRep")).get("phone"), false));
		} catch (Exception e) {
			EnvUtil.reportError(e);
		}

		return data;
	}

	/*
	 * Create Order Confirmation Email Data for Bronto
	 */
	public static Map<String, String> createOrderConfirmationSMSData(Delegator delegator, Map orderData, String orderId) throws GenericEntityException {
		Map<String, String> data = new HashMap<String, String>();
		if (orderData == null) {
			orderData = OrderHelper.getOrderData(delegator, orderId, false);
		}
		data.put("Order_Number", orderId);
		return data;
	}

	/*
	 * Create Order Confirmation Email Data for Bronto
	 */
	public static Map<String, String> createOrderShippedSMSData(Delegator delegator, Map orderData, String orderId) throws GenericEntityException {
		Map<String, String> data = new HashMap<String, String>();
		if (orderData == null) {
			orderData = OrderHelper.getOrderData(delegator, orderId, false);
		}
		data.put("Order_Number", orderId);
		data.put("email", (String) orderData.get("email"));

		GenericValue shipGroup = (GenericValue)orderData.get("shipGroup");
		String trackingURL = "";
		String trackingNumber = (String)shipGroup.get("trackingNumber");

		if(UtilValidate.isNotEmpty(trackingNumber)) {
			trackingNumber  = "Tracking# " + trackingNumber + ".";
		} else {
			trackingNumber = " ";
		}

		if(UtilValidate.isNotEmpty(trackingNumber) && UtilValidate.isNotEmpty(trackingURL = EnvUtil.getTrackingURL(trackingNumber))) {
			data.put("trackingURL", trackingURL);
		} else {
			data.put("trackingURL", "");
		}

		data.put("trackingNumber", trackingNumber);
		return data;
	}

	public static Map<String, String> createProofReadySMSData(Delegator delegator, Map orderData, String orderId) throws GenericEntityException {
		Map<String, String> data = new HashMap<>();
		if (orderData == null) {
			orderData = OrderHelper.getOrderData(delegator, orderId, false);
		}
		data.put("Order_Number", orderId);
		data.put("email", (String)orderData.get("email"));
		return data;
	}

	/*
	 * Create Order Pending Email Data for Bronto
	 */
	public static Map<String, String> createOrderPendingEmailData(Delegator delegator, Map orderData, String orderId) throws GenericEntityException {
		Map<String, String> data = new HashMap<String, String>();
		if (orderData == null) {
			orderData = OrderHelper.getOrderData(delegator, orderId, false);
		}
		data.put("orderNumber", orderId);
		data.put("orderFirstName", splitAtFirstSpace(((GenericValue)orderData.get("shippingAddress")).getString(TO_NAME))[0]);
		return data;
	}

	/*
	 * Create Sample Email Data for Bronto
	 */
	public static Map<String, String> createSampleEmailData(Delegator delegator, Map orderData, String orderId) throws GenericEntityException, ParseException {
		Map<String, String> data = new HashMap<>();
		if (orderData == null) {
			orderData = OrderHelper.getOrderData(delegator, orderId, false);
		}

		Map<String, Object> couponData = OrderHelper.orderSampleData(delegator, delegator.findByAnd("OrderItem", UtilMisc.toMap("orderId", orderId), null, false), true);
		data.put("orderFirstName", splitAtFirstSpace(((GenericValue)orderData.get("shippingAddress")).getString(TO_NAME))[0]);
		data.put("promotionCode", (String) couponData.get("sampleCoupon"));
		data.put("promotionAmount", "$" + EnvUtil.convertBigDecimal((BigDecimal) couponData.get("promotionAmount"), true));
		data.put("sampleCouponExpireDate", EnvUtil.convertTimestampToString((Timestamp) couponData.get("expireDate"), "MM/dd/yyyy"));

		return data;
	}

	/*
	 * Create Data for order production email
	 */
	public static Map<String, String> createProductionEmailData(Delegator delegator, Map orderData, String orderId, String orderItemSeqId) throws GenericEntityException, ParseException {
		if (orderData == null) {
			orderData = OrderHelper.getOrderData(delegator, orderId, false);
		}

		Map<String, String> data = new HashMap<String, String>();
		GenericValue product = null;
		GenericValue orderItem = null;
		List<Map> items = (List<Map>) orderData.get("items");
		for(Map item : items) {
			orderItem = (GenericValue) item.get("item");
			product = (GenericValue) item.get("product");
			if(orderItemSeqId.equalsIgnoreCase(orderItem.getString("orderItemSeqId"))) {
				break;
			}
		}

		data.put("orderNumber", orderId);
		data.put("itemName", product.getString("productName"));
		data.put("orderFirstName", splitAtFirstSpace(((GenericValue) orderData.get("shippingAddress")).getString(TO_NAME))[0]);
		data.put("dueDate", EnvUtil.convertTimestampToString(orderItem.getTimestamp("dueDate"), "MM/dd/yyyy"));
		return data;
	}

	/**
	 * Build data for quote completion email
	 * @param delegator
	 * @param quoteIds
	 * @return
	 * @throws GenericEntityException
	 * @throws ParseException
	 */
	public static Map<String, String> createQuoteCompletedEmailData(Delegator delegator, String[] quoteIds) throws GenericEntityException, ParseException {
		Map<String, String> data = new HashMap<>();

		int i = 1;
		for(String quoteId : quoteIds) {
			Map<String, Object> quoteData = CalculatorHelper.deserializedQuoteSummary(delegator, null, quoteId);
			GenericValue product = (GenericValue) quoteData.get("product");

			Map<String, String> productFeatures = ProductHelper.getProductFeatures(delegator, product, UtilMisc.toList("COATING"), true);

			data.put("oFromName", (String) quoteData.get("salesPerson"));
			data.put("oFromEmail", (String) quoteData.get("salesEmail"));
			data.put("oReplyEmail", (String) quoteData.get("salesEmail"));

			data.put("email", ((GenericValue) quoteData.get("customer")).getString("userEmail"));
			data.put("quoteId", ((GenericValue) quoteData.get("customer")).getString("quoteId"));
			data.put("salesPerson", (String) quoteData.get("salesPerson"));
			data.put("salesEmail", (String) quoteData.get("salesEmail"));
			data.put("salesNumber", (String) quoteData.get("salesNumber"));
			data.put("productId_" + i, (product != null) ? product.getString("productId") : "CUSTOM-P");
			data.put("product_" + i, ((product != null) ? product.getString("productName") : (String) quoteData.get("productName")) + " " + quoteData.get("material") + (UtilValidate.isNotEmpty(quoteData.get("COATING")) ? " - C" + ((List) quoteData.get("COATING")).size() + "S" : ""));
			data.put("productionTime_" + i, UtilValidate.isNotEmpty(quoteData.get("production")) ? (String) quoteData.get("production") : "Not Specified.");
			data.put("comment_" + i, UtilValidate.isNotEmpty(quoteData.get("comment")) ? (String) quoteData.get("comment") : "No comments.");
			data.put("printSummary_" + i, (String) quoteData.get("printSummary"));

			StringBuilder desktopQty = new StringBuilder();
			StringBuilder mobileQty = new StringBuilder();
			StringBuilder printOptionsDesktop = new StringBuilder();
			StringBuilder printOptionsMobile = new StringBuilder();

			printOptionsDesktop.append("<tr><td style=\"width: 425px; height: 5px;\"></td></tr><tr><td style=\"width: 425px; vertical-align: top;\"><div style=\"font-size: 14px; color: #818181;\">");
			if(UtilValidate.isNotEmpty(quoteData.get("offset")) && (Boolean) quoteData.get("offset")) {
				printOptionsDesktop.append("<div style=\"font-size: 14px; color: #818181;\"><div style=\"font-size: 14px; color: #232323; font-weight: bold;\">Offset Printing: </div>" + ((String) quoteData.get("offsetNote")).replace("\n", "<br />") + "</div>");
				printOptionsMobile.append("<div style=\"mso-hide: all; margin-top: 5px; background-color: #ffffff; font-size: 9px; font-weight: bold; color: #232323;\">Offset Printing: <div style=\"mso-hide: all; color: #818181;\">" + ((String) quoteData.get("offsetNote")).replace("\n", "<br />") + "</div></div>");
			}
			if(UtilValidate.isNotEmpty(quoteData.get("foil")) && (Boolean) quoteData.get("foil")) {
				printOptionsDesktop.append("<div style=\"font-size: 14px; color: #818181;\"><span style=\"font-size: 14px; color: #232323; font-weight: bold;\">Number of Foil Images: </span> " + quoteData.get("totalFoilImages") + "</div>");
				printOptionsMobile.append("<div style=\"mso-hide: all; margin-top: 5px; background-color: #ffffff; font-size: 9px; font-weight: bold; color: #232323;\">Number of Foil Images: <span style=\"mso-hide: all; color: #818181;\">" + quoteData.get("totalFoilImages") + "</span></div>");
			}
			if(UtilValidate.isNotEmpty(quoteData.get("embossing")) && (Boolean) quoteData.get("embossing")) {
				printOptionsDesktop.append("<div style=\"font-size: 14px; color: #818181;\"><span style=\"font-size: 14px; color: #232323; font-weight: bold;\">Number of Embossed Images: </span> " + quoteData.get("totalEmbossingImages") + "</div>");
				printOptionsMobile.append("<div style=\"mso-hide: all; margin-top: 5px; background-color: #ffffff; font-size: 9px; font-weight: bold; color: #232323;\">Number of Embossed Images: <span style=\"mso-hide: all; color: #818181;\">" + quoteData.get("totalEmbossingImages") + "</span></div>");
			}
			printOptionsDesktop.append("</td></tr>");

			data.put("desktopOptions_" + i, printOptionsDesktop.toString());
			data.put("mobileOptions_" + i, printOptionsMobile.toString());

			String fileName = quoteData.get("quoteRequestId") + "-" + quoteData.get("quoteId") + ".pdf";
			data.put("downloadUrl_" + i, "https://www.folders.com/folders/control/serveFileForStream?filePath=uploads/quotes/" + fileName + "&fileName=" + fileName + "&downLoad=Y");
			data.put("addToCart_" + i, "https://www.folders.com/folders/control/quoteOrder?quoteRequestId=" + quoteData.get("quoteRequestId") + "&quoteIds=" + String.join(",", quoteIds));

			Iterator iter = ((Map) quoteData.get("prices")).entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry pair = (Map.Entry) iter.next();
				Integer key = (Integer) pair.getKey();
				Map<String, BigDecimal> prices = (Map<String, BigDecimal>) pair.getValue();

				desktopQty.append("<tr><td style=\"width: 425px; height: 5px;\"></td></tr><tr><td style=\"width: 425px; vertical-align: top;\"><div style=\"font-size: 14px; color: #818181;\"><span style=\"font-size: 14px; color: #232323; font-weight: bold;\">Quantity:</span> ")
					.append(key.toString());

				mobileQty.append("<div style=\"mso-hide: all; margin-top: 5px; background-color: #ffffff; font-size: 9px; font-weight: bold; color: #232323;\">Quantity: <span style=\"mso-hide: all; color: #818181;\">")
					.append(key.toString());

				if (CalculatorHelper.discountAmount.compareTo(new BigDecimal(0)) != 0) {
					desktopQty.append(" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"font-size: 14px; color: #232323; font-weight: bold;\">Price:</span> <strike>$")
						.append((prices.get("total").divide(new BigDecimal(1).subtract(CalculatorHelper.discountAmount), 2, RoundingMode.HALF_UP)).toPlainString())
						.append("</strike>&nbsp;&nbsp;<span style=\"color: #e16e12;\">$");

					mobileQty.append("</span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Price: <span style=\"mso-hide: all; color: #818181;\"><strike>$")
						.append((prices.get("total").divide(new BigDecimal(1).subtract(CalculatorHelper.discountAmount), 2, RoundingMode.HALF_UP)).toPlainString())
						.append("</strike>&nbsp;&nbsp;<span style=\"color: #e16e12;\">$");
				} else {
					desktopQty.append(" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"font-size: 14px; color: #232323; font-weight: bold;\">Price:</span> <span style=\"color: #e16e12;\">$");

					mobileQty.append("</span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Price: <span style=\"mso-hide: all; color: #818181;\"> <span style=\"color: #e16e12;\">$");
				}

				desktopQty.append(prices.get("total").toPlainString())
					.append("</span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"font-size: 14px; color: #232323; font-weight: bold;\">Each:</span> $")
					.append(prices.get("each").toPlainString())
					.append("</div></td></tr>");

				mobileQty.append(prices.get("total").toPlainString())
					.append("</span></span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Each: <span style=\"mso-hide: all; color: #818181;\">$")
					.append(prices.get("each").toPlainString())
					.append("</span></div>");
			}

			data.put("desktopQty_" + i, desktopQty.toString());
			data.put("mobileQty_" + i, mobileQty.toString());
			i++;
		}

		return data;
	}

	/*
	 * Create Proof ready Email Data for Bronto
	 */
	public static Map<String, String> createProofReadyEmailData(Delegator delegator, Map orderData, String orderId) throws GenericEntityException {
		Map<String, String> data = new HashMap<String, String>();
		if (orderData == null) {
			orderData = OrderHelper.getOrderData(delegator, orderId, false);
		}
		GenericValue shippingAddressData = (GenericValue)orderData.get("shippingAddress");
		data.put("orderNumber", orderId);
		data.put("orderFirstName", splitAtFirstSpace(shippingAddressData.getString(TO_NAME))[0]);
		data.put("email", (String)orderData.get("email"));
		return data;
	}

	/*
	 * Create Need new Artwork Email Data for Bronto
	 */
	public static Map<String, String> createNeedNewArtworkEmailData(Delegator delegator, Map orderData, String orderId, String orderItemSeqId) throws GenericEntityException {
		Map<String, String> data = new HashMap<String, String>();
		if (orderData == null) {
			orderData = OrderHelper.getOrderData(delegator, orderId, false);
		}
		data.put("orderNumber", orderId);
		data.put("orderItemSeqId", orderItemSeqId);
		data.put("email", (String)orderData.get("email"));
		return data;
	}

	public static Map<String, String> createDoContactEmailData(Map requestData) {
		Map<String, String> data = new HashMap<>();
		data.put("name", (String)requestData.get("name"));
		data.put("email", (String)requestData.get("email"));
		data.put("orderId", (String)requestData.get("orderId"));
		data.put("inquiry", (String)requestData.get("inquiry"));
		data.put("subject", (String)requestData.get("subject"));
		data.put("message", (String)requestData.get("message"));

		return data;
	}

	/*
	 * Create Trade Request Email Data for Bronto
	 */
	public static Map<String, String> createTradeRequestEmailData(Map requestData) throws GenericEntityException {
		Map<String, String> data = new HashMap<String, String>();
		data.put("email", (String)requestData.get("email"));
		data.put("requestFirstName", (String)requestData.get("billing_firstName"));
		data.put("requestName", requestData.get("billing_firstName") + " " + requestData.get("billing_lastName"));
		String address1 = "",
			address2 = "",
			companyName = "",
			city = "",
			state = "",
			zip = "",
			contactNumber = "",
			industry = "",
			interestedIn = "",
			companyWebsite = "";
		StringBuilder address = new StringBuilder()
				.append(UtilValidate.isNotEmpty(companyName = (String) requestData.get("billing_companyName")) ? companyName + "<br/>" : "")
				.append(UtilValidate.isNotEmpty(address1 = (String) requestData.get("billing_address1")) ? address1 + "<br/>" : "")
				.append(UtilValidate.isNotEmpty(address2 = (String) requestData.get("billing_address2")) ? address2 + "<br/>" : "")
				.append(UtilValidate.isNotEmpty(city = (String) requestData.get("billing_city")) ? city + "<br/>" : "")
				.append(UtilValidate.isNotEmpty(state = (String) requestData.get("billing_stateProvinceGeoId")) ? state + "-" : "")
				.append(UtilValidate.isNotEmpty(zip = (String) requestData.get("billing_postalCode")) ? zip : "")
				.append(UtilValidate.isNotEmpty(contactNumber = (String) requestData.get("billing_contactNumber")) ? contactNumber : "");

		data.put("requestAddress", address.toString());

		data.put("requestIndustry", UtilValidate.isNotEmpty(industry = (String) requestData.get("industry")) ? industry : "Did not answer.");
		data.put("requestInterestedIn", UtilValidate.isNotEmpty(interestedIn = (String) requestData.get("interest")) ? interestedIn : "Did not answer.");
		data.put("requestCompanyWebsite", UtilValidate.isNotEmpty(companyWebsite = (String) requestData.get("company_website")) ? companyWebsite : "Did not answer.");
		data.put("requestTaxId", (String)requestData.get("federal_tax_id"));
		data.put("requestUserName", (String)requestData.get("emailAddress"));
		data.put("requestPassword", (String)requestData.get("password"));
		data.put("requestComment", (String)requestData.get("comments"));

		return data;
	}

	/*
	 * Create Review Email Data for Bronto
	 */
	public static Map<String, String> createReviewEmailData(Delegator delegator, String orderId) throws GenericEntityException, UnsupportedEncodingException {
		Map<String, String> data = new HashMap<String, String>();

		Map<String, Object> orderData = OrderHelper.getOrderData(delegator, orderId, false);
		if (UtilValidate.isNotEmpty(orderData)) {
			Map<String, Object> orderInfo = OrderHelper.buildOrderDetails(delegator, orderData);

			if (UtilValidate.isNotEmpty(orderInfo)) {
				Map<String, String> shippingAddresses = (Map<String, String>)orderInfo.get("shippingAddress");
				String[] split_name = ((String)shippingAddresses.get("name")).split(" ");

				data.put("orderNumber", orderId);
				data.put("firstname", split_name[0]);

				int i = 1;
				for (Map<String, Object> lineItem : (List<Map<String, Object>>)orderInfo.get("lineItems")) {
					if (!"CUSTOM-P".equalsIgnoreCase((String)lineItem.get("productId")) && !OrderHelper.isItemSample(delegator, (String) lineItem.get("productId"), new BigDecimal(((Integer) lineItem.get("quantity")).toString()), null)) {
						data.put("itemName_" + i, (String) lineItem.get("productName"));
						data.put("itemSize_" + i, (String) lineItem.get("productSize"));
						data.put("itemColor_" + i, (String) lineItem.get("productColor"));
						data.put("itemId_" + i, (String) lineItem.get("productId"));
						data.put("itemLink_" + i, ProductHelper.getProductUrl(delegator, null, (String) lineItem.get("productId"), EnvUtil.getWebsiteId(((GenericValue) orderData.get("orderHeader")).getString("salesChannelEnumId")), true));
						i++;
					}
				}
			}
		}

		return data;
	}

	/*
	 * Create Non-Profit Request Email Data for Bronto
	 */
	public static Map<String, String> createNonProfitRequestEmailData(Map requestData) throws GenericEntityException {
		Map<String, String> data = new HashMap<String, String>();
		data.put("email", (String)requestData.get("email"));
		data.put("requestFirstName", (String)requestData.get("billing_firstName"));
		data.put("requestName", requestData.get("billing_firstName") + " " + requestData.get("billing_lastName"));
		String address1 = "",
			address2 = "",
			companyName = "",
			city = "",
			state = "",
			zip = "",
			contactNumber = "",
			organizationType = "";
		StringBuilder address = new StringBuilder()
				.append(UtilValidate.isNotEmpty(companyName = (String) requestData.get("billing_companyName")) ? companyName + "<br/>" : "")
				.append(UtilValidate.isNotEmpty(address1 = (String) requestData.get("billing_address1")) ? address1 + "<br/>" : "")
				.append(UtilValidate.isNotEmpty(address2 = (String) requestData.get("billing_address2")) ? address2 + "<br/>" : "")
				.append(UtilValidate.isNotEmpty(city = (String) requestData.get("billing_city")) ? city + "<br/>" : "")
				.append(UtilValidate.isNotEmpty(state = (String) requestData.get("billing_stateProvinceGeoId")) ? state + "-" : "")
				.append(UtilValidate.isNotEmpty(zip = (String) requestData.get("billing_postalCode")) ? zip : "")
				.append(UtilValidate.isNotEmpty(contactNumber = (String) requestData.get("billing_contactNumber")) ? contactNumber : "");

		data.put("requestAddress", address.toString());

		String donations = "",
			invitations = "",
			invoices = "",
			checks = "",
			other = "",
			companyWebsite = "";
		StringBuilder envelopesUse = new StringBuilder()
				.append(UtilValidate.isNotEmpty(donations = (String) requestData.get("use-donations")) ? donations + "<br/>" : "")
				.append(UtilValidate.isNotEmpty(invitations = (String) requestData.get("use-invitations")) ? invitations + "<br/>" : "")
				.append(UtilValidate.isNotEmpty(invoices = (String) requestData.get("use-invoices")) ? invoices + "<br/>" : "")
				.append(UtilValidate.isNotEmpty(checks = (String) requestData.get("use-checks")) ? checks + "<br/>" : "")
				.append(UtilValidate.isNotEmpty(other = (String) requestData.get("use-other")) ? other + "<br/>" : "");

		data.put("requestEnvelopesUse", envelopesUse.toString());
		data.put("requestOrganizationType", UtilValidate.isNotEmpty(organizationType = (String) requestData.get("industry")) ? organizationType : "Did not answer.");
		data.put("requestCompanyWebsite", UtilValidate.isNotEmpty(companyWebsite = (String) requestData.get("company_website")) ? companyWebsite : "Did not answer.");
		data.put("requestTaxId", (String)requestData.get("federal_tax_id"));
		data.put("requestUserName", (String)requestData.get("emailAddress"));
		data.put("requestPassword", (String)requestData.get("password"));
		data.put("requestComment", (String)requestData.get("comments"));

		return data;
	}

	/*
	 * Create Item Shipped Email Data for Bronto
	 */
	public static Map<String, String> createItemShippedEmailData(Delegator delegator, Map orderData, String orderId, String orderItemSeqId, String webSiteId) throws GenericEntityException {
		Map<String, String> data = new HashMap<>();
		if (orderData == null) {
			orderData = OrderHelper.getOrderData(delegator, orderId, false);
		}
		GenericValue shippingAddressData = (GenericValue)orderData.get("shippingAddress");
		GenericValue billingAddressData = (GenericValue)orderData.get("billingAddress");
		List<Map> items = (List<Map>) orderData.get("items");
		List<String> quoteAssignedToEmails = new ArrayList<>();

		data.put("orderNumber", orderId);
		data.put("orderFirstName", splitAtFirstSpace(billingAddressData.getString(TO_NAME))[0]);
		data.put("orderLastName", splitAtFirstSpace(billingAddressData.getString(TO_NAME))[1]);
		data.put("email", (String) orderData.get("email"));

		boolean foundTrackingNumber = false; //if something ships and no tracking is provided, dont send email

		int i = 1;
		for(Map item : items) {
			GenericValue orderItem = (GenericValue) item.get("item");
			if(!orderItemSeqId.equalsIgnoreCase(orderItem.getString("orderItemSeqId"))) {
				continue;
			}

			if("ITEM_SHIPPED".equalsIgnoreCase(orderItem.getString("statusId"))) {
				String productName = (EnvConstantsUtil.CUSTOM_PRODUCT.equalsIgnoreCase(orderItem.getString("productId")) || EnvConstantsUtil.SAMPLE_PRODUCT.equalsIgnoreCase(orderItem.getString("productId"))) ? orderItem.getString("itemDescription") : ((GenericValue) item.get("product")).getString("internalName");
				data.put("itemName_" + i, productName + ((orderItem.getBigDecimal("quantity").compareTo(new BigDecimal("5")) <= 0) ? " (SAMPLE)" : ""));
				data.put("itemDescription_" + i, ((GenericValue) item.get("product")).getString("internalName") + ((orderItem.getBigDecimal("quantity").compareTo(new BigDecimal("5")) <= 0) ? " (SAMPLE)" : ""));

				if(UtilValidate.isNotEmpty(orderItem.getString("comments"))) {
					data.put("itemComment_" + i, orderItem.getString("comments"));
					data.put("itemCommentShow_" + i, "inline");
					data.put("itemCommentStart_" + i, "<!-- -->");
					data.put("itemCommentEnds_" + i, "<!-- -->");
				} else {
					data.put("itemComment_" + i, "&nbsp;");
					data.put("itemCommentShow_" + i, "none");
					data.put("itemCommentStart_" + i, "<!--");
					data.put("itemCommentEnds_" + i, "-->");
				}

				if (UtilValidate.isNotEmpty(orderItem.get("referenceQuoteId"))) {
					GenericValue quoteInfo = EntityQuery.use(delegator).from("CustomEnvelope").where("quoteId", orderItem.get("referenceQuoteId")).queryOne();
					if (UtilValidate.isNotEmpty(quoteInfo) && UtilValidate.isNotEmpty(quoteInfo.get("assignedTo")) && !quoteAssignedToEmails.contains((String) quoteInfo.get("assignedTo"))) {
						quoteAssignedToEmails.add((String) quoteInfo.get("assignedTo"));
					}
				}

				if (ProductHelper.getProductFeatures(delegator, (GenericValue) item.get("product"), UtilMisc.toList("COLOR")).size() != 0 && !ProductHelper.getProductFeatures(delegator, (GenericValue) item.get("product"), UtilMisc.toList("COLOR")).get("COLOR").equals("Custom Color")) {
					String itemColorHtml = "" +
							"<tr>\n" +
							"<td width=\"130\" style=\"font: normal 12px/17px Helvetica; color: #595959;\">Color:</td>\n" +
							"<td style=\"font: normal 12px/17px Helvetica; color: #595959;\">" + ProductHelper.getProductFeatures(delegator, (GenericValue) item.get("product"), UtilMisc.toList("COLOR")).get("COLOR") + "</td>\n" +
							"</tr>";

					data.put("itemColor_" + i, itemColorHtml);

					itemColorHtml = "" +
							"<tr>\n" +
							"<td style=\"mso-hide: all; width: 30%; font: normal 5px/7px Helvetica; color: #595959;\">Color:</td>\n" +
							"<td style=\"mso-hide: all; font: normal 5px/7px Helvetica; color: #595959;\">" + ProductHelper.getProductFeatures(delegator, (GenericValue) item.get("product"), UtilMisc.toList("COLOR")).get("COLOR") + "</td>\n" +
							"</tr>";

					data.put("itemColorMobile_" + i, itemColorHtml);
				}
				data.put("itemQty_" + i, EnvUtil.convertBigDecimal(orderItem.getBigDecimal("quantity"), true));

				List attributes = (List) item.get("attribute");
				for(Object attributeObj : attributes) {
					GenericValue attribute = (GenericValue) attributeObj;
					if(attribute.getString("attrName").equals("colorsFront")) {
						data.put("itemFrontColor_" + i, (UtilValidate.isNotEmpty(!attribute.getString("attrValue").equals("0"))) ? attribute.getString("attrValue") : "&nbsp;");
					} else if(attribute.getString("attrName").equals("colorsBack")) {
						data.put("itemBackColor_" + i, (UtilValidate.isNotEmpty(!attribute.getString("attrValue").equals("0"))) ? attribute.getString("attrValue") : "&nbsp;");
					}
				}

				if(OrderHelper.isItemPrinted(delegator, null, null, orderItem)) {
					data.put("showPrint_" + i, "inline");
					data.put("commentStart_" + i, "<!-- -->");
					data.put("commentEnd_" + i, "<!-- -->");
				} else {
					data.put("showPrint_" + i, "none");
					data.put("commentStart_" + i, "<!--");
					data.put("commentEnd_" + i, "-->");
				}

				GenericValue shipGroupAssoc = (GenericValue) item.get("shipGroupAssoc");
				String trackingNumber = "";
				String trackingURL = "";
				if (UtilValidate.isNotEmpty(trackingNumber = (String) shipGroupAssoc.get("trackingNumber")) && UtilValidate.isNotEmpty(trackingURL = EnvUtil.getTrackingURL(trackingNumber))) {
					data.put("itemTrackingURL_" + i, "<a href='" + trackingURL + "'>" + trackingNumber + "</a>");
					foundTrackingNumber = true;
				} else {
					data.put("itemTrackingURL_" + i, "");
				}
				i++;
			}
		}

		data.put("foundTrackingNumber", Boolean.toString(foundTrackingNumber));

		String value = "";
		data.put("shippingAddressName", splitAtFirstSpace(shippingAddressData.getString(TO_NAME))[0] + " " + splitAtFirstSpace(shippingAddressData.getString(TO_NAME))[1]);
		data.put("shippingCompanyName", UtilValidate.isNotEmpty(value = (String)shippingAddressData.get("companyName")) ? value : "");
		data.put("shippingAddress1", UtilValidate.isNotEmpty(value = (String)shippingAddressData.get("address1")) ? value : "");
		data.put("shippingAddress2", UtilValidate.isNotEmpty(value = (String)shippingAddressData.get("address2")) ? value : "");
		data.put("shippingCity", UtilValidate.isNotEmpty(value = (String)shippingAddressData.get("city")) ? value : "");
		data.put("shippingState", UtilValidate.isNotEmpty(value = (String)shippingAddressData.get("stateProvinceGeoId")) ? value : "");
		data.put("shippingZip", UtilValidate.isNotEmpty(value = (String)shippingAddressData.get("postalCode")) ? value : "");
		data.put("billingCompanyName", UtilValidate.isNotEmpty(value = (String)billingAddressData.get("companyName")) ? value : "");
		data.put("billingAddress1", UtilValidate.isNotEmpty(value = (String)billingAddressData.get("address1")) ? value : "");
		data.put("billingAddress2", UtilValidate.isNotEmpty(value = (String)billingAddressData.get("address2")) ? value : "");
		data.put("billingCity", UtilValidate.isNotEmpty(value = (String)billingAddressData.get("city")) ? value : "");
		data.put("billingState", UtilValidate.isNotEmpty(value = (String)billingAddressData.get("stateProvinceGeoId")) ? value : "");
		data.put("billingZip", UtilValidate.isNotEmpty(value = (String)billingAddressData.get("postalCode")) ? value : "");
		data.put("salesRepInfoDesktop", getSalesRepInfoHTML(((HashMap<String, String>) orderData.get("salesRep")).get("email"), ((HashMap<String, String>) orderData.get("salesRep")).get("name"), ((HashMap<String, String>) orderData.get("salesRep")).get("phone"), true));
		data.put("salesRepInfoMobile", getSalesRepInfoHTML(((HashMap<String, String>) orderData.get("salesRep")).get("email"), ((HashMap<String, String>) orderData.get("salesRep")).get("name"), ((HashMap<String, String>) orderData.get("salesRep")).get("phone"), false));

		return data;
	}

	/*
	 * Create Order Shipped Email Data for Bronto
	 */
	public static Map<String, String> createOrderShippedEmailData(Delegator delegator, Map orderData, String orderId) throws GenericEntityException {
		Map<String, String> data = new HashMap<String, String>();
		if (orderData == null) {
			orderData = OrderHelper.getOrderData(delegator, orderId, false);
		}
		GenericValue shippingAddressData = (GenericValue)orderData.get("shippingAddress");
		GenericValue billingAddressData = (GenericValue)orderData.get("billingAddress");
		GenericValue shipGroup = (GenericValue)orderData.get("shipGroup");
		String value = "";
		data.put("orderNumber", orderId);
		data.put("orderFirstName", splitAtFirstSpace(billingAddressData.getString(TO_NAME))[0]);
		data.put("orderLastName", splitAtFirstSpace(billingAddressData.getString(TO_NAME))[1]);
		data.put("email", (String) orderData.get("email"));
		String trackingNumber = "";
		String trackingURL = "";
		if(UtilValidate.isNotEmpty(trackingNumber = (String)shipGroup.get("trackingNumber")) && UtilValidate.isNotEmpty(trackingURL = EnvUtil.getTrackingURL(trackingNumber))) {
			data.put("trackingURL", "Here is your tracking number: <a href='" + trackingURL + "'>" + trackingNumber + "</a>");
		} else {
			data.put("trackingURL", "");
		}
		data.put("shippingAddressName", splitAtFirstSpace(shippingAddressData.getString(TO_NAME))[0] + " " + splitAtFirstSpace(shippingAddressData.getString(TO_NAME))[1]);
		data.put("shippingCompanyName", UtilValidate.isNotEmpty(value = (String)shippingAddressData.get("companyName")) ? value : "");
		data.put("shippingAddress1", UtilValidate.isNotEmpty(value = (String)shippingAddressData.get("address1")) ? value : "");
		data.put("shippingAddress2", UtilValidate.isNotEmpty(value = (String)shippingAddressData.get("address2")) ? value : "");
		data.put("shippingCity", UtilValidate.isNotEmpty(value = (String)shippingAddressData.get("city")) ? value : "");
		data.put("shippingState", UtilValidate.isNotEmpty(value = (String)shippingAddressData.get("stateProvinceGeoId")) ? value : "");
		data.put("shippingZip", UtilValidate.isNotEmpty(value = (String)shippingAddressData.get("postalCode")) ? value : "");
		data.put("billingCompanyName", UtilValidate.isNotEmpty(value = (String)billingAddressData.get("companyName")) ? value : "");
		data.put("billingAddress1", UtilValidate.isNotEmpty(value = (String)billingAddressData.get("address1")) ? value : "");
		data.put("billingAddress2", UtilValidate.isNotEmpty(value = (String)billingAddressData.get("address2")) ? value : "");
		data.put("billingCity", UtilValidate.isNotEmpty(value = (String)billingAddressData.get("city")) ? value : "");
		data.put("billingState", UtilValidate.isNotEmpty(value = (String)billingAddressData.get("stateProvinceGeoId")) ? value : "");
		data.put("billingZip", UtilValidate.isNotEmpty(value = (String)billingAddressData.get("postalCode")) ? value : "");
		data.put("salesRepInfoDesktop", getSalesRepInfoHTML(((HashMap<String, String>) orderData.get("salesRep")).get("email"), ((HashMap<String, String>) orderData.get("salesRep")).get("name"), ((HashMap<String, String>) orderData.get("salesRep")).get("phone"), true));
		data.put("salesRepInfoMobile", getSalesRepInfoHTML(((HashMap<String, String>) orderData.get("salesRep")).get("email"), ((HashMap<String, String>) orderData.get("salesRep")).get("name"), ((HashMap<String, String>) orderData.get("salesRep")).get("phone"), false));

		return data;
	}

	/*
	 * Create Custom Quote Email Data for Bronto
	 */
	public static Map<String, String> createCustomQuoteEmailData(Map orderData, String quoteId) throws GenericEntityException {
		Map<String, String> data = new HashMap<String, String>();
		if (UtilValidate.isEmpty(orderData)) {
			return data;
		}
		data.put("quoteId", quoteId);
		data.put("quoteFirstName", (String)orderData.get("firstName"));
		data.put("quoteName", orderData.get("firstName") + " " + orderData.get("lastName"));
		String address1 = "", address2 = "", companyName = "", city = "", state = "", zip = "";
		StringBuilder address = new StringBuilder()
							 .append(UtilValidate.isNotEmpty(companyName = (String) orderData.get("company")) ? companyName + "<br/>" : "")
							 .append(UtilValidate.isNotEmpty(address1 = (String) orderData.get("address1")) ? address1 + "<br/>" : "")
							 .append(UtilValidate.isNotEmpty(address2 = (String) orderData.get("address2")) ? address2 + "<br/>" : "")
							 .append(UtilValidate.isNotEmpty(city = (String) orderData.get("city")) ? city + "<br/>" : "")
							 .append(UtilValidate.isNotEmpty(state = (String) orderData.get("state")) ? state + "-" : "")
							 .append(UtilValidate.isNotEmpty(zip = (String) orderData.get("zip")) ? zip : "");
		data.put("quoteAddress", address.toString());
		data.put("quoteComment", (String) orderData.get("comment"));
		data.put("quoteSize", (String) orderData.get("standardSize"));
		data.put("quoteQuantity", (String) orderData.get("quantity"));
		data.put("quoteSeal", (String) orderData.get("sealingMethod"));
		data.put("quotePaperType", (String) orderData.get("paperType"));
		//data.put("quotePrint", ((String) orderData.get("printingRequired")).equals("Y") ? "Yes" : "No");
		return data;
	}

	public static Map<String, String> createReorderEmailData(Delegator delegator, Map orderData, String orderId) throws GenericEntityException {
		Map<String, String> data = new HashMap<String, String>();
		if(orderData == null) {
			orderData = OrderHelper.getOrderData(delegator, orderId, false);
		}

		GenericValue orderHeader = (GenericValue) orderData.get("orderHeader");
		SiteConfig siteConfig = new SiteConfig(EnvUtil.getWebsiteId(orderHeader.getString("salesChannelEnumId")));
		siteConfig.getWebSite(delegator);

		data.put("orderNumber", orderId);
		data.put("email", (String) orderData.get("email"));
		data.put("itemReminderOrderDate", EnvConstantsUtil.WMDSHORT.format(((GenericValue) orderData.get("orderHeader")).getTimestamp("orderDate")));

		List<Map> items = (List<Map>) orderData.get("items");
		for(Map item : items) {
			GenericValue orderItem = (GenericValue) item.get("item");

			if(orderItem.getBigDecimal("quantity").compareTo(new BigDecimal("5")) < 0) {
				continue;
			}

			data.put("itemReminderItem", orderItem.getString("productId"));
			data.put("itemReminderQty", orderItem.getBigDecimal("quantity").toBigInteger().toString());
			data.put("itemReminderDescription", orderItem.getString("itemDescription"));
			data.put("messageType", "plainReorder");

			if(OrderHelper.isItemPrinted(delegator, null, null, orderItem)) {
				data.put("messageType", "printReorder");
				List<GenericValue> contents = (List<GenericValue>) item.get("content");
				for(GenericValue content: contents) {
					if("OIACPRP_FRONT".equalsIgnoreCase(content.getString("contentPurposeEnumId"))) {
						data.put("itemReminderItemImg", "https://" + siteConfig.webSiteUrl() + "/" + siteConfig.getWebSiteId() + "/control/serveFileForStream?filePath=" + content.getString("contentPath"));
					} else if("OIACPRP_BACK".equalsIgnoreCase(content.getString("contentPurposeEnumId"))) {
						data.put("itemReminderItemImgBack", "https://" + siteConfig.webSiteUrl() + "/" + siteConfig.getWebSiteId() + "/control/serveFileForStream?filePath=" + content.getString("contentPath"));
					}
				}

				if(UtilValidate.isEmpty("itemReminderItemImg")) {
					data.put("itemReminderItemImg", "https://" + siteConfig.webSiteUrl() + "/html/img/icon/1x1trans.gif");
				}
				if(UtilValidate.isEmpty("itemReminderItemImgBack")) {
					data.put("itemReminderItemImgBack", "https://" + siteConfig.webSiteUrl() + "/html/img/icon/1x1trans.gif");
				}
			}

			break;
		}

		return data;
	}

	protected static BrontoSoapApiImplServiceStub createStub() throws Exception {
		BrontoSoapApiImplServiceStub stub = new BrontoSoapApiImplServiceStub();
		stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(60000l);
		stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, 60000);
		stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, 60000);

		return stub;
	}

	private static String getSalesRepInfoHTML(String email, String name, String phone, Boolean isDesktop) {
		String emailHTML = "";

		if (UtilValidate.isNotEmpty(name)) {
			emailHTML += "" +
				"<tr>\n" +
					"<td colspan=\"2\" style=\"font-weight: bold; font-size: " + (isDesktop ? "18" : "12") + "px; padding-top: " + (isDesktop ? "15" : "5") + "px;\">\n" +
						name + "\n" +
					"</td>\n" +
				"</tr>\n";
		}

		if (UtilValidate.isNotEmpty(phone)) {
			emailHTML += "" +
				"<tr>\n" +
					"<td colspan=\"2\" style=\"font-size: " + (isDesktop ? "18" : "12") + "px; padding-top: " + (isDesktop ? "5" : "2") + "px;\">\n" +
						"<span style=\"font-weight: bold;\">Call:</span> <a href=\"tel:" + phone + "\">" + phone + "</a>\n" +
					"</td>\n" +
				"</tr>";
		}

		if (UtilValidate.isNotEmpty(email)) {
			emailHTML += "" +
				"<tr>\n" +
					"<td colspan=\"2\" style=\"font-size: " + (isDesktop ? "18" : "12") + "px; padding-top: " + (isDesktop ? "5" : "2") + "px;\">\n" +
						"<span style=\"font-weight: bold;\">Email:</span> <a href=\"mailto:" + email + "\">" + email + "</a>\n" +
					"</td>\n" +
				"</tr>\n";
		}

		return emailHTML;
	}

	public static String buildEmailThisQuoteHTML(Map<String, Object> context, Boolean isDesktop) {
		String emailHTML = "";

		if (UtilValidate.isNotEmpty(context.get("quoteData"))) {
			Gson gson = new GsonBuilder().serializeNulls().create();
			List<Object> quoteDataList = new ArrayList<>();
			quoteDataList = gson.fromJson((String) context.get("quoteData"), ArrayList.class);

			for (Integer x = 0; x < quoteDataList.size(); x++) {
				for(Map.Entry<String, Object> entry : ((LinkedTreeMap<String, Object>) quoteDataList.get(x)).entrySet()) {
					if (isDesktop) {
						emailHTML += "" +
							"<table style=\"width: 600px;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
								"<tr>\n" +
									"<td style=\"width: 600px;\">\n" +
										"<table style=\"width: 600px;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
											"<tr>\n" +
												"<td valign=\"top\" style=\"width: 150px;\">\n" +
													"<div style=\"color: #000000; font-size: 12px; font-weight: bold; font-family: Arial, Helvetica, sans-serif;\">\n" +
														entry.getKey() + ":\n" +
													"</div>\n" +
												"</td>\n" +
												"<td valign=\"top\" style=\"width: 450px;\">\n" +
													"<div style=\"color: #000000; font-size: 12px; font-weight: bold; font-family: Arial, Helvetica, sans-serif;\">\n" +
														entry.getValue() + "\n" +
													"</div>\n" +
												"</td>\n" +
											"</tr>\n" +
										"</table>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td colspan=\"3\" style=\"height: 10px;\"></td>\n" +
								"</tr>\n" +
							"</table>";
					} else {
						emailHTML += "" +
							"<table style=\"mso-hide: all; width: 100%; padding-bottom: 3px;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
								"<tr>\n" +
									"<td valign=\"top\" style=\"mso-hide: all; width: 40%;\">\n" +
										"<div style=\"mso-hide: all; color: #000000; font-size: 8px; font-weight: bold; font-family: Arial, Helvetica, sans-serif;\">\n" +
											entry.getKey() + ":\n" +
										"</div>\n" +
									"</td>\n" +
									"<td valign=\"top\" style=\"mso-hide: all; width: 60%;\">\n" +
										"<div style=\"mso-hide: all; color: #000000; font-size: 8px; font-weight: bold; font-family: Arial, Helvetica, sans-serif;\">\n" +
											entry.getValue() + "\n" +
										"</div>\n" +
									"</td>\n" +
								"</tr>\n" +
							"</table>";
					}
				}
			}
		}

		return emailHTML;
	}

	public static List<Map<String, String>> getTodaysTradeAnniversaryEmails(Delegator delegator) {
		List<Map<String, String>> anniversaryList = new ArrayList<>();
		SQLProcessor du = null;
		String sqlCommand = null;

		try {
			du = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));

			sqlCommand = "" +
					"SELECT " +
					"    ul.user_login_id as email, " +
					"    YEAR(NOW()) - YEAR(pr.created_stamp) as anniversaryCount " +
					"FROM " +
					"    party_role pr " +
					"        INNER JOIN " +
					"    user_login ul ON ul.party_id = pr.party_id " +
					"WHERE " +
					"    pr.role_type_id = 'WHOLESALER' " +
					"        AND MONTH(pr.CREATED_STAMP) = MONTH(NOW()) AND DAY(pr.CREATED_STAMP) = DAY(NOW()) AND YEAR(pr.CREATED_STAMP) != YEAR(NOW()) " +
					"        AND ul.user_login_id not like \"%ANONYMOUS%\"";

			ResultSet rs = null;
			rs = du.executeQuery(sqlCommand);
			if(rs != null) {
				while(rs.next()) {
					Map<String, String> anniversaryData = new HashMap<String, String>();
					anniversaryData.put("email", rs.getString(1));
					anniversaryData.put("anniversaryCount", rs.getString(2));
					if (UtilValidate.isNotEmpty(anniversaryData)) {
						anniversaryList.add(anniversaryData);
					}
				}
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "There was an issue trying to obtain today's Trade Anniversary Emails.", module);
		} finally {
			try {
				if (du != null) {
					du.close();
				}
			} catch (GenericDataSourceException gdse) {
				EnvUtil.reportError(gdse);
				Debug.logError(gdse, "There was an issue trying to close SQLProcessor", module);
			}
		}

		return anniversaryList;
	}
}
