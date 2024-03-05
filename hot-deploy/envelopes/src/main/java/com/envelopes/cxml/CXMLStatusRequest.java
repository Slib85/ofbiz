/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.cxml;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.*;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.service.LocalDispatcher;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.envelopes.util.*;

public class CXMLStatusRequest {
	public static final String module = CXMLStatusRequest.class.getName();

	protected Map<String, Object> requestObj = null;
	protected HashMap<String, Object> responseObj = null;
	protected Map<String, Object> context = null;

	protected String xml = null;
	protected Document xmlDoc = null;

	protected HttpServletRequest request = null;
	protected Delegator delegator = null;
	protected LocalDispatcher dispatcher = null;

	protected Document requestXML = null;

	public String orderId = null;
	public String orderItemSeqId = null;
	public String payloadID = null;
	public String jobID = null;
	public String stockIndicator = null;
	public String stockError = null;
	public String stockErrorDesc = null;
	public String statusCode = null;
	public GenericValue vendor = null;
	public GenericValue vendorOrder = null;

	/**
	 * CXMLStatusRequest constructor
	 */
	public CXMLStatusRequest(HttpServletRequest request)  {
		this.request = request;
		this.dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		this.delegator = (Delegator) request.getAttribute("delegator");
		this.context = EnvUtil.getParameterMap(this.request);
	}

	/**
	 * Receive an cXML Status Request
	 */
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
				//dispatcher.runSync("sendEmail", UtilMisc.toMap("email", "shoab@bigname.com", "rawData", null, "data", UtilMisc.<String, String>toMap("subject", "DUPLI CXML STATUS", "request", emailBody.replace("<", "< ").replace(">", " >")), "messageType", "genericEmail", "webSiteId", "envelopes"));
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

	/**
	 * Return whether XML exists
	 * @return
	 */
	public boolean isXMLValid() {
		return (this.xmlDoc != null);
	}

	/**
	 * Return if a valid orderId and payloadId is found
	 * @return
	 */
	public boolean isXMLDataValid() {
		if(isXMLValid()) {
			Element requestElement = (Element) this.xmlDoc.getElementsByTagName("Request").item(0);
			if(UtilValidate.isNotEmpty(requestElement)) {
				Element statusUpdateRequest = (Element) requestElement.getElementsByTagName("StatusUpdateRequest").item(0);
				if(UtilValidate.isNotEmpty(statusUpdateRequest)) {
					Element documentReference = (Element) statusUpdateRequest.getElementsByTagName("DocumentReference").item(0);
					if(UtilValidate.isNotEmpty(documentReference)) {
						this.payloadID = documentReference.getAttribute("payloadID");
						if(UtilValidate.isNotEmpty(this.payloadID) && this.payloadID.indexOf("-") != -1) {
							this.orderId = this.payloadID.substring(0, this.payloadID.indexOf("-"));
							return (UtilValidate.isNotEmpty(this.orderId) && UtilValidate.isNotEmpty(this.payloadID));
						}
					}
				}
			}
		}

		return false;
	}

	/**
	 * Get the jobId from the request
	 */
	public void getJobId() {
		if(isXMLValid()) {
			Element requestElement = (Element) this.xmlDoc.getElementsByTagName("Request").item(0);
			if(UtilValidate.isNotEmpty(requestElement)) {
				Element statusUpdateRequest = (Element) requestElement.getElementsByTagName("StatusUpdateRequest").item(0);
				if(UtilValidate.isNotEmpty(statusUpdateRequest)) {
					Element documentReference = (Element) statusUpdateRequest.getElementsByTagName("DocumentReference").item(0);
					if(UtilValidate.isNotEmpty(documentReference)) {
						this.payloadID = documentReference.getAttribute("payloadID");
						if(UtilValidate.isNotEmpty(this.payloadID) && this.payloadID.indexOf("-") != -1) {
							this.orderId = this.payloadID.substring(0, this.payloadID.indexOf("-"));
						}
					}

					Element jobMapping = (Element) statusUpdateRequest.getElementsByTagName("Status").item(0);
					if(UtilValidate.isNotEmpty(jobMapping) && "Job Mapping".equalsIgnoreCase(jobMapping.getAttribute("text"))) {
						this.jobID = jobMapping.getTextContent();
					}
				}
			}
		}
	}

	/**
	 * Update the jobId to database
	 * @return
	 */
	public boolean updateJobId() {
		boolean success = false;

		if(UtilValidate.isNotEmpty(this.payloadID) && UtilValidate.isNotEmpty(this.jobID)) {
			try {
				this.vendorOrder = EntityQuery.use(this.delegator).from("VendorOrder").where("orderId", this.orderId, "payloadId", this.payloadID).queryFirst();
				if(this.vendorOrder != null) {
					this.orderItemSeqId = this.vendorOrder.getString("orderItemSeqId");
					this.vendorOrder.set("jobId", this.jobID);
					this.vendorOrder.store();
					success = true;
				}
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "Error trying to store job id data", module);
			}
		}

		return success;
	}

	/**
	 * Get the stock indicator from the request
	 */
	public void getStockIndicator() {
		if(isXMLValid()) {
			Element requestElement = (Element) this.xmlDoc.getElementsByTagName("Request").item(0);
			if(UtilValidate.isNotEmpty(requestElement)) {
				Element statusUpdateRequest = (Element) requestElement.getElementsByTagName("StatusUpdateRequest").item(0);
				if(UtilValidate.isNotEmpty(statusUpdateRequest)) {
					Element documentReference = (Element) statusUpdateRequest.getElementsByTagName("DocumentReference").item(0);
					if(UtilValidate.isNotEmpty(documentReference)) {
						this.payloadID = documentReference.getAttribute("payloadID");
						if(UtilValidate.isNotEmpty(this.payloadID) && this.payloadID.indexOf("-") != -1) {
							this.orderId = this.payloadID.substring(0, this.payloadID.indexOf("-"));
						}
					}

					Element stockIndicator = (Element) statusUpdateRequest.getElementsByTagName("Status").item(0);
					if(UtilValidate.isNotEmpty(stockIndicator) && "Stock Received".equalsIgnoreCase(stockIndicator.getAttribute("text"))) {
						this.stockIndicator = stockIndicator.getTextContent();
					}
				}
			}
		}
	}

	/**
	 * Update the stock indicator to database
	 * @return
	 */
	public boolean updateStockIndicator() {
		boolean success = false;

		if(UtilValidate.isNotEmpty(this.payloadID) && UtilValidate.isNotEmpty(this.stockIndicator)) {
			try {
				this.vendorOrder = EntityQuery.use(this.delegator).from("VendorOrder").where("orderId", this.orderId, "payloadId", this.payloadID).queryFirst();
				if(this.vendorOrder != null) {
					this.orderItemSeqId = this.vendorOrder.getString("orderItemSeqId");
					this.vendorOrder.set("stockIndicator", this.stockIndicator);
					this.vendorOrder.store();
					success = true;
				}
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "Error trying to store stock indicator data", module);
			}
		}

		return success;
	}

	/**
	 * Get the stock error from the request
	 */
	public void getStockError() {
		if(isXMLValid()) {
			Element requestElement = (Element) this.xmlDoc.getElementsByTagName("Request").item(0);
			if(UtilValidate.isNotEmpty(requestElement)) {
				Element statusUpdateRequest = (Element) requestElement.getElementsByTagName("StatusUpdateRequest").item(0);
				if(UtilValidate.isNotEmpty(statusUpdateRequest)) {
					Element documentReference = (Element) statusUpdateRequest.getElementsByTagName("DocumentReference").item(0);
					if(UtilValidate.isNotEmpty(documentReference)) {
						this.payloadID = documentReference.getAttribute("payloadID");
						if(UtilValidate.isNotEmpty(this.payloadID) && this.payloadID.indexOf("-") != -1) {
							this.orderId = this.payloadID.substring(0, this.payloadID.indexOf("-"));
						}
					}

					Element stockErrorIndicator = (Element) statusUpdateRequest.getElementsByTagName("Status").item(0);
					if(UtilValidate.isNotEmpty(stockErrorIndicator) && "Stock Issue".equalsIgnoreCase(stockErrorIndicator.getAttribute("text"))) {
						this.stockError = stockErrorIndicator.getAttribute("code");
						if(NumberUtils.isNumber(this.stockError)) {
							String stockErr = Integer.toString((Integer.valueOf(this.stockError) - 600));
							if(OutsourceRule.osIssue.containsKey(stockErr)) {
								this.stockError = stockErr;
							}
						}
					}

					Element stockErrorDescription = (Element) statusUpdateRequest.getElementsByTagName("Comments").item(0);
					if(UtilValidate.isNotEmpty(stockErrorDescription)) {
						this.stockErrorDesc = stockErrorDescription.getTextContent();
					}
				}
			}
		}
	}

	/**
	 * Update the stock error to database
	 * @return
	 */
	public boolean updateStockError() {
		boolean success = false;

		if(UtilValidate.isNotEmpty(this.payloadID) && UtilValidate.isNotEmpty(this.stockError)) {
			try {
				this.vendorOrder = EntityQuery.use(this.delegator).from("VendorOrder").where("orderId", this.orderId, "payloadId", this.payloadID).queryFirst();
				if(this.vendorOrder != null) {
					this.orderItemSeqId = this.vendorOrder.getString("orderItemSeqId");
					this.vendorOrder.set("errorCode", this.stockError);
					this.vendorOrder.set("errorDescription", this.stockErrorDesc);
					this.vendorOrder.store();
					success = true;
				}
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "Error trying to store stock error data", module);
			}
		}

		return success;
	}

	/**
	 * Get the stock error from the request
	 */
	public void getStatusCode() {
		if(isXMLValid()) {
			Element requestElement = (Element) this.xmlDoc.getElementsByTagName("Request").item(0);
			if(UtilValidate.isNotEmpty(requestElement)) {
				Element statusUpdateRequest = (Element) requestElement.getElementsByTagName("StatusUpdateRequest").item(0);
				if(UtilValidate.isNotEmpty(statusUpdateRequest)) {
					Element documentReference = (Element) statusUpdateRequest.getElementsByTagName("DocumentReference").item(0);
					if(UtilValidate.isNotEmpty(documentReference)) {
						this.payloadID = documentReference.getAttribute("payloadID");
						if(UtilValidate.isNotEmpty(this.payloadID) && this.payloadID.indexOf("-") != -1) {
							this.orderId = this.payloadID.substring(0, this.payloadID.indexOf("-"));
						}
					}

					Element statusCodeIndicator = (Element) statusUpdateRequest.getElementsByTagName("Status").item(0);
					if(UtilValidate.isNotEmpty(statusCodeIndicator) && "Order Update".equalsIgnoreCase(statusCodeIndicator.getAttribute("text"))) {
						this.statusCode = statusCodeIndicator.getAttribute("code");
						if(NumberUtils.isNumber(this.statusCode)) {
							String statusId = Integer.toString((Integer.valueOf(this.statusCode) - 700));
							if(OutsourceRule.osStatus.containsKey(statusId)) {
								this.statusCode = statusId;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Update the stock status to database
	 * @return
	 */
	public boolean updateStatusCode() {
		boolean success = false;

		if(UtilValidate.isNotEmpty(this.payloadID) && UtilValidate.isNotEmpty(this.statusCode)) {
			try {
				this.vendorOrder = EntityQuery.use(this.delegator).from("VendorOrder").where("orderId", this.orderId, "payloadId", this.payloadID).queryFirst();
				if(this.vendorOrder != null) {
					this.orderItemSeqId = this.vendorOrder.getString("orderItemSeqId");
					this.vendorOrder.set("statusCode", this.statusCode);
					this.vendorOrder.store();
					success = true;
				}
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "Error trying to store stock status data", module);
			}
		}

		return success;
	}

	/**
	 * Return the orderId
	 * @return
	 */
	public String getOrderId() {
		return this.orderId;
	}

	/**
	 * Return the orderItemSeqId
	 * @return
	 */
	public String getOrderItemSeqId() {
		return this.orderItemSeqId;
	}

	/**
	 * Set the orderId
	 * @param orderId
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * Set the orderItemSeqId
	 * @param orderItemSeqId
	 */
	public void setOrderItemSeqId(String orderItemSeqId) {
		this.orderItemSeqId = orderItemSeqId;
	}

	/**
	 * Return the payloadId
	 * @return
	 */
	public String getPayloadID() {
		return this.payloadID;
	}

	public void setVendorOrder() throws GenericEntityException {
		this.orderId = (String) this.context.get("orderId");
		this.orderItemSeqId = (UtilValidate.isNotEmpty((String) this.context.get("orderItemSeqId"))) ? (String) this.context.get("orderItemSeqId") : (String) this.context.get("lineItem");

		//check if seq id is formatted properly
		if(UtilValidate.isNotEmpty(this.orderItemSeqId)) {
			this.orderItemSeqId = EnvUtil.formatOrderItemSeqNumber(this.orderItemSeqId);
		}

		if(UtilValidate.isNotEmpty(this.orderId) && UtilValidate.isNotEmpty(this.orderItemSeqId)) {
			this.vendorOrder = EntityQuery.use(this.delegator).from("VendorOrder").where("orderId", this.orderId, "orderItemSeqId", this.orderItemSeqId).queryOne();
			if(this.vendorOrder != null) {
				this.vendor = CXMLHelper.getVendor(this.delegator, this.vendorOrder.getString("partyId"));
			}
		}
	}

	public GenericValue getVendor() {
		return this.vendor;
	}

	public void createRequestDoc() throws Exception {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		this.requestXML = docBuilder.newDocument();

		this.requestXML.appendChild(createRequest());
	}

	public Element createRequest() {
		Element rootElement = createElement(this.requestXML, "cXML");
		rootElement.setAttribute("payloadID", this.orderId + "_" + this.orderItemSeqId + "-LOCATION-" + UtilDateTime.nowAsString());
		rootElement.setAttribute("timestamp", EnvConstantsUtil.UTC.format(UtilDateTime.nowTimestamp()));
		rootElement.setAttribute("version", "1.2.029");
		rootElement.setAttribute("xml:lang", "en-US");

		CXMLHelper.createHeader(this.requestXML, rootElement, this.vendor);

		Element statusRequest = createElement(this.requestXML, "Request");
		statusRequest.setAttribute("deploymentMode", "production");

		Element statusUpdateRequest = createElement(this.requestXML, "StatusUpdateRequest");
		Element documentReference = createElement(this.requestXML, "DocumentReference");
		documentReference.setAttribute("payloadID", this.vendorOrder.getString("payloadId"));
		statusUpdateRequest.appendChild(documentReference);

		Element status = createElement(this.requestXML, "Status");
		status.setAttribute("code", "201");
		status.setAttribute("text", "Stock Pulled");
		status.setAttribute("xml:lang", "en-US");
		status.appendChild(createTextNode(this.requestXML, "Cubby " + this.vendorOrder.getString("stockLocation")));
		statusUpdateRequest.appendChild(status);

		statusRequest.appendChild(statusUpdateRequest);
		rootElement.appendChild(statusRequest);
		return rootElement;
	}

	public String getXMLRequestString() throws TransformerException {
		return CXMLHelper.getXMLString(this.requestXML);
	}

	private Element createElement(Document xml, String str) {
		return xml.createElement(str);
	}

	private static Text createTextNode(Document xml, String str) {
		return xml.createTextNode(str);
	}
}