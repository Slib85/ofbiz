/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.cxml;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.lang.*;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.envelopes.order.OrderHelper;
import com.envelopes.util.*;
import org.apache.xerces.dom.DocumentTypeImpl;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.order.order.OrderReadHelper;
import org.apache.ofbiz.service.LocalDispatcher;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

public class CXMLShipNoticeRequest {
	public static final String module = CXMLShipNoticeRequest.class.getName();

	protected Map<String, Object> requestObj = null;
	protected HashMap<String, Object> responseObj = null;
	protected Map<String, Object> context = null;
	protected String xml = null;
	protected Document xmlDoc = null;
	protected HttpServletRequest request = null;
	protected String orderId = null;
	protected String orderItemSeqId = null;
	protected String trackingNumber = null;
	protected OrderReadHelper orh = null;
	protected Delegator delegator = null;
	protected LocalDispatcher dispatcher = null;
	protected Document responseXML = null;

	/**
	 * CXMLShipNoticeRequest constructor
	 */
	public CXMLShipNoticeRequest(HttpServletRequest request)  {
		this.request = request;
		this.dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		this.delegator = (Delegator) request.getAttribute("delegator");
	}

	public void getXML() {
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			InputStream inputStream = this.request.getInputStream();
			if(inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}

			try {
				String emailBody = stringBuilder.toString();
				//dispatcher.runSync("sendEmail", UtilMisc.toMap("email", "shoab@bigname.com", "rawData", null, "data", UtilMisc.<String, String>toMap("subject", "DUPLI CXML SHIP", "request", emailBody.replace("<", "< ").replace(">", " >")), "messageType", "genericEmail", "webSiteId", "envelopes"));
			} catch(Exception e2) {
				EnvUtil.reportError(e2);
				Debug.logError(e2, "Error trying to send dupli xml email for testing purposes", module);
			}

			this.xmlDoc = EnvUtil.stringToXML(stringBuilder.toString());
			this.xmlDoc.getDocumentElement().normalize();
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to read cxml data", module);
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception ex) {
					EnvUtil.reportError(ex);
					Debug.logError(ex, "Error trying to read cxml data", module);
				}
			}
		}
	}

	public boolean isXMLValid() {
		return (this.xmlDoc != null);
	}

	public boolean isXMLDataValid() {
		return (UtilValidate.isNotEmpty(this.orderId) && UtilValidate.isNotEmpty(this.orderItemSeqId) && UtilValidate.isNotEmpty(this.trackingNumber));
	}

	public void getOrderItemAndTracking() {
		if(isXMLValid()) {
			Element requestElement = (Element) this.xmlDoc.getElementsByTagName("Request").item(0);
			if(UtilValidate.isNotEmpty(requestElement)) {
				Element shipNoticeRequest = (Element) requestElement.getElementsByTagName("ShipNoticeRequest").item(0);
				if(UtilValidate.isNotEmpty(shipNoticeRequest)) {
					Element shipControl = (Element) shipNoticeRequest.getElementsByTagName("ShipControl").item(0);
					if(UtilValidate.isNotEmpty(shipControl)) {
						Element tracking = (Element) shipControl.getElementsByTagName("ShipmentIdentifier").item(0);
						if(UtilValidate.isNotEmpty(tracking)) {
							this.trackingNumber = tracking.getTextContent();
						}
					}

					Element shipNoticePortion = (Element) shipNoticeRequest.getElementsByTagName("ShipNoticePortion").item(0);
					if(UtilValidate.isNotEmpty(shipNoticePortion)) {
						Element orderReference = (Element) shipNoticeRequest.getElementsByTagName("OrderReference").item(0);
						if(UtilValidate.isNotEmpty(orderReference)) {
							String orderAndItem = orderReference.getAttribute("orderID");
							if(UtilValidate.isNotEmpty(orderAndItem)) {
								String orderId = orderAndItem.substring(0, orderAndItem.indexOf("_"));
								String orderItemSeqId = orderAndItem.substring(orderAndItem.indexOf("_")+1);

								this.orderId = orderId;
								this.orderItemSeqId = orderItemSeqId;
							}
						}
					}
				}
			}
		}
	}

	public boolean updateTracking() {
		boolean success = false;

		if(UtilValidate.isNotEmpty(this.orderId) && UtilValidate.isNotEmpty(this.orderItemSeqId) && UtilValidate.isNotEmpty(this.trackingNumber)) {
			try {
				this.orh = new OrderReadHelper(this.delegator, this.orderId);

				if(orh.getOrderHeader() != null) {
					GenericValue orderItem = orh.getOrderItem(this.orderItemSeqId);
					Map<String, Map<String, String>> orderAndTracking = new HashMap<>();
					orderAndTracking.put(this.orderId, UtilMisc.<String, String>toMap(this.orderItemSeqId + "|" + orderItem.getString("productId"), this.trackingNumber));
					Map<String, List<String>> updatedTracking = OrderHelper.updateOrderTracking(delegator, orderAndTracking);

					//change item statuses to shipped
					Iterator updatedTrackingIter = updatedTracking.entrySet().iterator();
					while(updatedTrackingIter.hasNext()) {
						Map.Entry pairs = (Map.Entry) updatedTrackingIter.next();
						String orderId = (String) pairs.getKey();
						List<String> orderItemSeqIds = (List<String>) pairs.getValue();

						for(String orderItemSeqId : orderItemSeqIds) {
							OrderHelper.processItemShipStatusChange(delegator, dispatcher, orderId, orderItemSeqId, true, null);
						}
					}

					success = true;
				}
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "Error trying to store tracking data", module);
			}
		}

		return success;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getOrderItemSeqId() {
		return this.orderItemSeqId;
	}

	public String getTrackingNumber() {
		return this.trackingNumber;
	}

	/**
	 * This will return just a basic calculation of quantity * weight
	 * @return
	 * @throws GenericEntityException
	 */
	public BigDecimal getPackageWeight() throws GenericEntityException {
		if(this.orh != null) {
			GenericValue orderItem = this.orh.getOrderItem(this.orderItemSeqId);
			GenericValue product = orderItem.getRelatedOne("Product", true);

			if(UtilValidate.isNotEmpty(product.getBigDecimal("productWeight"))) {
				return product.getBigDecimal("productWeight").multiply(orderItem.getBigDecimal("quantity")).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);
			}
		}

		return BigDecimal.ZERO;
	}

	public void createResponseDoc(boolean success) throws Exception {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		this.responseXML = docBuilder.newDocument();

		this.responseXML.appendChild(createResponse(success));
	}

	public Element createResponse(boolean success) throws Exception {
		Element rootElement = createElement("cXML");
		rootElement.setAttribute("payloadID", this.orderId + "_" + this.orderItemSeqId + "-" + UtilDateTime.nowAsString());
		rootElement.setAttribute("timestamp", EnvConstantsUtil.UTC.format(UtilDateTime.nowTimestamp()));
		rootElement.setAttribute("version", "1.2.029");
		rootElement.setAttribute("xml:lang", "en-US");

		Element response = createElement("Response");
		Element status = createElement("Status");
		if(success) {
			status.setAttribute("code", "200");
			status.setAttribute("text", "OK");
		} else {
			status.setAttribute("code", "400");
			status.setAttribute("text", "BAD");
		}

		response.appendChild(status);
		rootElement.appendChild(response);

		return rootElement;
	}

	public String getXMLString() throws TransformerException {
		return CXMLHelper.getXMLString(this.responseXML);
	}

	private Element createElement(String str) {
		return this.responseXML.createElement(str);
	}
}