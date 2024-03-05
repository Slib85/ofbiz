/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.cxml;

import java.io.StringReader;
import java.lang.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.envelopes.util.*;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class CXMLOrderResponse {
	public static final String module = CXMLOrderResponse.class.getName();

	protected Map<String, Object> requestObj = null;
	protected HashMap<String, Object> responseObj = null;
	protected Map<String, Object> context = null;
	protected Document document = null;

	/**
	 * CXMLOrderResponse constructor
	 */
	public CXMLOrderResponse(HttpServletRequest request)  {
		this.context = EnvUtil.getParameterMap(request);
	}

	/**
	 * CXMLOrderResponse constructor
	 */
	public CXMLOrderResponse(String response)  {
		if(UtilValidate.isNotEmpty(response)) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			try {
				builder = factory.newDocumentBuilder();
				this.document = builder.parse(new InputSource(new StringReader(response)));
				this.document.getDocumentElement().normalize();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isSuccess() {
		Node responseNode = this.document.getElementsByTagName("Response").item(0);
		if(responseNode.getNodeType() == Node.ELEMENT_NODE) {
			Element responseElement = (Element) responseNode;
			Node statusNode = responseElement.getElementsByTagName("Status").item(0);
			if(statusNode.getNodeType() == Node.ELEMENT_NODE) {
				Element statusElement = (Element) statusNode;
				if(UtilValidate.isNotEmpty(statusElement.getAttribute("text")) && "OK".equalsIgnoreCase(statusElement.getAttribute("text"))) {
					return true;
				} else if(UtilValidate.isNotEmpty(statusElement.getAttribute("text")) && statusElement.getAttribute("text").contains("Order Already Exists")) {
					return true;
				}
			}
		}

		return false;
	}

	public static void saveVendorOrder(Delegator delegator, String partyId, List<GenericValue> orderItems, String purchaseOrderId, String comments, String payloadId, Long orderVersion, String data, String cost) throws GenericEntityException {
		List<GenericValue> toSaveOrders = new ArrayList<>();
		for(GenericValue orderItem : orderItems) {
			toSaveOrders.add(delegator.makeValue("VendorOrder",
					UtilMisc.toMap(
						"orderId", orderItem.getString("orderId"),
						"orderItemSeqId", orderItem.getString("orderItemSeqId"),
						"payloadId", payloadId,
						"orderVersion", orderVersion,
						"purchaseOrderId", purchaseOrderId,
						"cost", new BigDecimal(cost),
						"comments", comments,
						"partyId", partyId,
						"priceData", data
					)
				)
			);
		}

		delegator.storeAll(toSaveOrders);
	}
}