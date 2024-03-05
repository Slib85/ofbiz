/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.cxml;

import java.io.StringWriter;
import java.lang.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bigname.integration.listrak.ListrakHelper;
import com.envelopes.http.HTTPHelper;
import com.envelopes.netsuite.NetsuiteHelper;
import com.envelopes.order.OrderHelper;

import com.envelopes.party.PartyHelper;
import com.envelopes.product.ProductHelper;
import com.google.gson.Gson;
import com.itextpdf.text.pdf.PdfReader;
import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.model.DynamicViewEntity;
import org.apache.ofbiz.entity.model.ModelKeyMap;
import org.apache.ofbiz.entity.transaction.GenericTransactionException;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.entity.util.EntityListIterator;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.order.order.OrderReadHelper;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;
import org.apache.xerces.dom.DocumentTypeImpl;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.envelopes.util.*;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class CXMLHelper {
	public static final String module = CXMLHelper.class.getName();

	public static String CUSTOMER_ID;
	public static String DUNS;
	public static String SERVICE_ITEM_SKU = "Print Service";

	static {
		try{
			CUSTOMER_ID = UtilProperties.getPropertyValue("envelopes", "iparcel.id");
			DUNS = UtilProperties.getPropertyValue("envelopes", "duns.number");
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error, could not open: " + "envelopes.properties", module);
		}
	}

	public static void createHeader(Document xml, Element rootElement, GenericValue vendor) {
		Element header = createElement(xml, "Header");

		Element from = createElement(xml, "From");
		Element fromCredential = createElement(xml, "Credential");
		fromCredential.setAttribute("domain", "DUNS");
		Element fromIdentity = createElement(xml, "Identity");
		fromIdentity.appendChild(createTextNode(xml, DUNS));
		fromCredential.appendChild(fromIdentity);
		from.appendChild(fromCredential);

		Element to = createElement(xml, "To");
		Element toCredential = createElement(xml, "Credential");
		toCredential.setAttribute("domain", "DUNS");
		Element toIdentity = createElement(xml, "Identity");
		toIdentity.appendChild(createTextNode(xml, vendor.getString("duns")));
		toCredential.appendChild(toIdentity);
		to.appendChild(toCredential);

		Element sender = createElement(xml, "Sender");
		Element senderCredential = createElement(xml, "Credential");
		senderCredential.setAttribute("domain", "DUNS");
		Element senderIdentity = createElement(xml, "Identity");
		senderIdentity.appendChild(createTextNode(xml, DUNS));
		senderCredential.appendChild(senderIdentity);
		Element senderSharedSecret = createElement(xml, "SharedSecret");
		senderSharedSecret.appendChild(createTextNode(xml, vendor.getString("sharedSecret")));
		senderCredential.appendChild(senderSharedSecret);
		Element userAgent = createElement(xml, "UserAgent");
		userAgent.appendChild(createTextNode(xml, "Buyer 8.2"));
		sender.appendChild(senderCredential);
		sender.appendChild(userAgent);

		header.appendChild(from);
		header.appendChild(to);
		header.appendChild(sender);

		rootElement.appendChild(header);
	}

	private static Element createElement(Document xml, String str) {
		return xml.createElement(str);
	}
	private static Text createTextNode(Document xml, String str) {
		return xml.createTextNode(str);
	}

	public static String getGeoCodeFromGeoId(Delegator delegator, String geoId) {
		if(UtilValidate.isNotEmpty(geoId)) {
			GenericValue geo = null;
			try {
				geo = delegator.findOne("Geo", UtilMisc.toMap("geoId", geoId), true);
			} catch (GenericEntityException e) {
				EnvUtil.reportError(e);
			}
			if(UtilValidate.isNotEmpty(geo)) {
				return geo.getString("geoCode");
			}
		}

		return null;
	}

	public static String getGeoNameFromGeoId(Delegator delegator, String geoId) {
		if(UtilValidate.isNotEmpty(geoId)) {
			GenericValue geo = null;
			try {
				geo = delegator.findOne("Geo", UtilMisc.toMap("geoId", geoId), true);
			} catch (GenericEntityException e) {
				EnvUtil.reportError(e);
			}
			if(UtilValidate.isNotEmpty(geo)) {
				return geo.getString("geoName");
			}
		}

		return null;
	}

	public static List<GenericValue> getAllVendor(Delegator delegator) throws GenericEntityException {
		return delegator.findAll("Vendor", true);
	}

	public static GenericValue getVendor(Delegator delegator, String partyId) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(partyId)) {
			return delegator.findOne("Vendor", UtilMisc.toMap("partyId", partyId), true);
		}

		return null;
	}

	public static Map<String, Object> getVendorRules(GenericValue vendor) {
		HashMap<String, Object> data = new HashMap<>();

		if(UtilValidate.isNotEmpty(vendor) && UtilValidate.isNotEmpty(vendor.getString("rules"))) {
			HashMap<String, Object> rules = new Gson().fromJson(vendor.getString("rules"), HashMap.class);

			Iterator it = rules.entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				String key = (String) pair.getKey();
				if(pair.getValue() instanceof String) {
				//	if(key.equalsIgnoreCase("singleItemOrder") || key.equalsIgnoreCase("profitMargin")) {
				//		data.put(key, pair.getValue());
				//	} else {
						data.put(key, UtilMisc.toList(pair.getValue()));
				//	}
				} else {
					data.put(key, pair.getValue());
				}
			}
		}

		return data;
	}

	public static List<GenericValue> getAllVendorProducts(Delegator delegator, int numOfMonths) throws GenericEntityException {
		List<EntityCondition> conditions = new ArrayList<EntityCondition>();
		conditions.add(EntityCondition.makeCondition("createdStamp", EntityOperator.GREATER_THAN_EQUAL_TO, EnvUtil.getNMonthsBeforeOrAfterNow(numOfMonths, true)));
		EntityCondition condition = EntityCondition.makeCondition(conditions, EntityOperator.AND);

		return delegator.findList("VendorProduct", condition, null, UtilMisc.toList("createdStamp DESC"), null, false);
	}

	public static GenericValue getProduct(Delegator delegator, String productId) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(productId)) {
			return delegator.findOne("ProductOutsource", UtilMisc.toMap("productId", productId), false);
		}

		return null;
	}

	/**
	 * shipping - List
	 * inkColors - List
	 * productionTime - List
	 * singleItemOrder - String
	 * stockSupplied - String
	 * profitMargin - String
	 * @param product
	 * @return
	 */
	public static Map<String, Object> getProductRules(GenericValue product) {
		HashMap<String, Object> data = new HashMap<>();

		if(UtilValidate.isNotEmpty(product) && UtilValidate.isNotEmpty(product.getString("rules"))) {
			HashMap<String, Object> rules = new Gson().fromJson(product.getString("rules"), HashMap.class);

			Iterator it = rules.entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				String key = (String) pair.getKey();
				if(pair.getValue() instanceof String) {
					if(key.equalsIgnoreCase("singleItemOrder") || key.equalsIgnoreCase("profitMargin") || key.equalsIgnoreCase("stockSupplied")) {
						data.put(key, pair.getValue());
					} else {
						data.put(key, UtilMisc.toList(pair.getValue()));
					}
				} else {
					data.put(key, pair.getValue());
				}
			}
		}

		return data;
	}

	public static GenericValue getVendorProduct(Delegator delegator, String productId) throws GenericEntityException {
		return getVendorProduct(delegator, productId, null);
	}
	public static GenericValue getVendorProduct(Delegator delegator, String productId, String vendorPartyId) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(productId)) {
			Map<String, Object> data = UtilMisc.toMap("productId", productId);
			if(vendorPartyId != null) {
				data.put("vendorPartyId", vendorPartyId);
			}
			return EntityUtil.getFirst(EntityUtil.filterByDate(delegator.findByAnd("VendorProduct", data, null, false)));
		}

		return null;
	}

	public static List<GenericValue> getOutsourceableProducts(Delegator delegator) throws GenericEntityException {
		return delegator.findAll("ProductOutsource", false);
	}

	public static List<GenericValue> getVendors(Delegator delegator, String productId) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(productId)) {
			return EntityUtil.filterByDate(delegator.findByAnd("VendorProduct", UtilMisc.toMap("productId", productId), null, true));
		}

		return null;
	}

	public static String getPDFPath(Delegator delegator, String orderId, String orderItemSeqId, String type) throws GenericEntityException {
		List<GenericValue> pdfContent = OrderHelper.getOrderItemContent(delegator, orderId, orderItemSeqId);
		GenericValue content = null;
		for(GenericValue pdf : pdfContent) {
			if(pdf.getString("contentPurposeEnumId").contains(type) && !pdf.getString("contentPurposeEnumId").contains("_NOKEY") && !pdf.getString("contentPurposeEnumId").contains("_WHITE")) {
				content = pdf;
				break;
			}
		}

		return (UtilValidate.isNotEmpty(content)) ? EnvConstantsUtil.OFBIZ_HOME + content.getString("contentPath") : null;
	}

	public static String getPDFUrl(Delegator delegator, String orderId, String orderItemSeqId, String type) throws GenericEntityException {
		List<GenericValue> pdfContent = OrderHelper.getOrderItemContent(delegator, orderId, orderItemSeqId);
		GenericValue content = null;
		for(GenericValue pdf : pdfContent) {
			if(pdf.getString("contentPurposeEnumId").contains(type) && !pdf.getString("contentPurposeEnumId").contains("_NOKEY") && !pdf.getString("contentPurposeEnumId").contains("_WHITE")) {
				content = pdf;
				break;
			}
		}

		return (UtilValidate.isNotEmpty(content)) ? "https://www.envelopes.com/envelopes/control/downloadFile?filePath=" + content.getString("contentPath") + "&fileName=" + content.getString("contentName") + "&downLoad=Y" : null;
	}

	public static String getPOPDFUrl(Delegator delegator, String path, String fileName) throws GenericEntityException {
		return "https://www.envelopes.com/envelopes/control/downloadFile?filePath=" + path + "&fileName=" + fileName + "&downLoad=Y";
	}

	protected static boolean outsourceable(GenericValue orderItem) {
		if("ART_OUTSOURCED".equalsIgnoreCase(orderItem.getString("statusId"))) {
			return true;
		}

		return false;
	}

	public static boolean autoOutsourceable(Delegator delegator, String orderId, String orderItemSeqId, GenericValue orderItem) throws GenericEntityException {
		if(UtilValidate.isEmpty(orderItem)) {
			orderItem = delegator.findOne("OrderItem", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), false);
		}

		if(UtilValidate.isNotEmpty(orderItem)) {
			GenericValue vendorProduct = getVendorProduct(delegator, orderItem.getString("productId"));
			if(vendorProduct != null && !wasSent(delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId"))) {
				return true;
			}
		}

		return false;
	}

	public static String getPDFDimension(String pdfPath, String defaultDimension) {
        String dimension = defaultDimension;
	    try {
            PdfReader reader = new PdfReader(pdfPath);
            float width = reader.getPageSize(1).getWidth()/72;
            float height = reader.getPageSize(1).getHeight()/72;
            dimension = width + " x " + height;
        } catch (Exception pdfE) {
            EnvUtil.reportError(pdfE);
        }
	    return dimension;
    }

	public static List<Map<String, Object>> vendorOrders(Delegator delegator, String partyId) throws GenericEntityException {
		List<Map<String, Object>> orderList = new ArrayList<>();

		DynamicViewEntity dve = new DynamicViewEntity();
		dve.addMemberEntity("OH", "OrderHeader");
		dve.addMemberEntity("OI", "OrderItem");
		dve.addViewLink("OH", "OI", false, ModelKeyMap.makeKeyMapList("orderId"));
		dve.addMemberEntity("P", "Product");
		dve.addViewLink("OI", "P", false, ModelKeyMap.makeKeyMapList("productId"));
		dve.addMemberEntity("VP", "VendorProduct");
		dve.addViewLink("P", "VP", false, ModelKeyMap.makeKeyMapList("productId"));
		dve.addMemberEntity("VO", "VendorOrder");
		dve.addViewLink("OI", "VO", false, ModelKeyMap.makeKeyMapList("orderId", "orderId", "orderItemSeqId", "orderItemSeqId"));
		dve.addAlias("OH", "orderId");
		dve.addAlias("OH", "orderDate");
		dve.addAlias("OH", "entryDate");
		dve.addAlias("OH", "createdBy");
		dve.addAlias("OH", "orderStatusId", "statusId", null, null, null, null);
		dve.addAlias("OI", "orderItemSeqId");
		dve.addAlias("OI", "productId");
		dve.addAlias("OI", "statusId");
//		dve.addAlias("OI", "quantity");
//		dve.addAlias("OI", "unitPrice");
//		dve.addAlias("OI", "itemDescription");
//		dve.addAlias("OI", "isRushProduction");
//		dve.addAlias("OI", "artworkSource");
//		dve.addAlias("OI", "dueDate");
//		dve.addAlias("OI", "outsourceable");
//		dve.addAlias("OI", "outsource");
//		dve.addAlias("OI", "referenceQuoteId");
//		dve.addAlias("OI", "referenceQuoteItemSeqId");
		dve.addAlias("P", "productName");
		dve.addAlias("VP", "vendorProductId");
		dve.addAlias("VP", "vendorPartyId");
		dve.addAlias("VO", "partyId");
		dve.addAlias("VO", "comments");
		dve.addAlias("VO", "purchaseOrderId");
		dve.addAlias("VO", "deliveryDate");
		dve.addAlias("VO", "trackingNumber");
		dve.addAlias("VO", "vendorInvoiceId");
		dve.addAlias("VO", "shipDate");
		dve.addAlias("VO", "packageWeight");
		dve.addAlias("VO", "createdStamp");

		List<EntityCondition> conditionList = new ArrayList<>();
		conditionList.add(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId));
		conditionList.add(EntityCondition.makeCondition("vendorPartyId", EntityOperator.EQUALS, partyId));
		conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL, "ITEM_SHIPPED"));
		conditionList.add(EntityCondition.makeCondition("orderStatusId", EntityOperator.NOT_IN, UtilMisc.toList("ORDER_CANCELLED","ORDER_REJECTED")));

		List<String> orderBy = new ArrayList<>();
		orderBy.add("createdStamp ASC");

		List<String> groupBy = new ArrayList<>();
		groupBy.add("orderId");
		groupBy.add("orderItemSeqId");

		dve.setGroupBy(groupBy);

		EntityListIterator eli = null;
		boolean beganTransaction = false;
		try {
			beganTransaction = TransactionUtil.begin();
			try {
				eli = delegator.findListIteratorByCondition(dve, EntityCondition.makeCondition(conditionList), null, null, orderBy, null);
				GenericValue orderGV = null;
				while((orderGV = eli.next()) != null) {
					orderList.add(OrderHelper.getOrderData(delegator, orderGV.getString("orderId"), orderGV.getString("orderItemSeqId"), true));
				}
			} catch (GenericEntityException e1) {
				TransactionUtil.rollback(beganTransaction, "Error looking up vendor orders.", e1);
				EnvUtil.reportError(e1);
			} finally {
				if(eli != null) {
					try {
						eli.close();
						TransactionUtil.commit(beganTransaction);
					} catch (GenericEntityException e2) {
						EnvUtil.reportError(e2);
					}
				}
			}
		} catch (GenericTransactionException e3) {
			EnvUtil.reportError(e3);
		}

		return orderList;
	}

	protected static Map<String, List<GenericValue>> getOutsourceableGroups(Delegator delegator, List<GenericValue> orderItems)  throws GenericEntityException {
		return getOutsourceableGroups(delegator, orderItems, null);
	}
	protected static Map<String, List<GenericValue>> getOutsourceableGroups(Delegator delegator, List<GenericValue> orderItems, String vendorPartyId)  throws GenericEntityException {
		//group items by vendor party id
		Map<String, List<GenericValue>> orderItemGroups = new HashMap<>();

		for(GenericValue orderItem : orderItems) {
			if(wasSent(delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId"))) {
				continue;
			}

			GenericValue vendorProduct = (vendorPartyId == null) ? getVendorProduct(delegator, orderItem.getString("productId")) : getVendorProduct(delegator, orderItem.getString("productId"), vendorPartyId);
			if(!orderItemGroups.containsKey(vendorProduct.getString("vendorPartyId"))) {
				orderItemGroups.put(vendorProduct.getString("vendorPartyId"), UtilMisc.<GenericValue>toList(orderItem));
			} else {
				List<GenericValue> temp = orderItemGroups.get(vendorProduct.getString("vendorPartyId"));
				temp.add(orderItem);
				orderItemGroups.put(vendorProduct.getString("vendorPartyId"), temp);
			}
		}

		return orderItemGroups;
	}

	protected static boolean wasSent(Delegator delegator, String orderId, String orderItemSeqId) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(orderId) && UtilValidate.isNotEmpty(orderItemSeqId)) {
			return UtilValidate.isNotEmpty(delegator.findOne("VendorOrder", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), false));
		}

		return false;
	}

	public static void setOutsourceComment(Delegator delegator, GenericValue orderItemArtwork) throws GenericEntityException {
		OrderHelper.appendToOrderComments(delegator, "pressman", " - [OUTSOURCED]", orderItemArtwork);
	}

	public static String getOutsourceArtworkEmailData(Delegator delegator, OrderReadHelper orh, String orderId, String orderItemSeqId) throws GenericEntityException {
		String emailHTML = "";

		String shipping = orh.getShippingMethod("00001", false);
		if (UtilValidate.isNotEmpty(shipping)) {
			emailHTML += "<tr>\n" +
					"<td width=\"130\" style=\"font: normal 12px/17px Helvetica; color: #595959;\">Blindship:</td>\n" +
					"<td style=\"font: normal 12px/17px Helvetica; color: #595959;\">" + (OrderHelper.isBlindShipment(delegator, (EntityQuery.use(delegator).from("OrderContactMech").where("orderId", orderId, "contactMechPurposeTypeId", "SHIPPING_LOCATION").queryFirst()).getString("contactMechId")) ? "Yes" : "No") + "</td>\n" +
				"</tr>\n" +
				"<tr>\n" +
					"<td width=\"130\" style=\"font: normal 12px/17px Helvetica; color: #595959;\">Ship Via:</td>\n" +
					"<td style=\"font: normal 12px/17px Helvetica; color: #595959;\">" + shipping + "</td>\n" +
				"</tr>";
		}

		String additionalText, side1Text, side2Text, foilText, embossText, rightPocketText, centerPocketText, leftPocketText, reinforcedEdgeText, backboneText, closureText, gussetText, fileTabText, spineText;
		additionalText = side1Text = side2Text = foilText = embossText = rightPocketText = centerPocketText = leftPocketText = reinforcedEdgeText = backboneText = closureText = gussetText = fileTabText = spineText = "";
		Map<String, String> instructionDataList = new HashMap<>();
		Map<String, Object> orderItemAttributes = OrderHelper.getOrderItemAttributeMap(delegator, orderId, orderItemSeqId, true);
		Iterator it = orderItemAttributes.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
            String description = (String) ((Map<String, String>) (orderItemAttributes.get(pair.getKey()))).get("description");
			String value = (String) ((Map<String, String>) (orderItemAttributes.get(pair.getKey()))).get("value");
			Pattern p = Pattern.compile("^((?:side1|side2|foil|emboss|rightPocket|centerPocket|leftPocket|reinforcedEdge|backbone|closure|gusset|fileTab|spine)).*?((?:\\d+.*?|))$");
			Matcher m = p.matcher((String) pair.getKey());
			
			while (m.find()) {
                switch (m.group(1)) {
                    case "side1":
                        instructionDataList.put("Side 1:", (UtilValidate.isNotEmpty(instructionDataList.get("Side 1:")) ? instructionDataList.get("Side 1:") : "") + description + ": " + value + "<br />");
                        break;
                    case "side2":
                        instructionDataList.put("Side 2:", (UtilValidate.isNotEmpty(instructionDataList.get("Side 2:")) ? instructionDataList.get("Side 2:") : "") + description + ": " + value + "<br />");
                        break;
                    case "foil":
                        instructionDataList.put("Foil" + (UtilValidate.isNotEmpty(m.group(2)) ? " " + m.group(2) : "") + ":", (UtilValidate.isNotEmpty(instructionDataList.get("Foil" + (UtilValidate.isNotEmpty(m.group(2)) ? " " + m.group(2) : "") + ":")) ? instructionDataList.get("Foil" + (UtilValidate.isNotEmpty(m.group(2)) ? " " + m.group(2) : "") + ":") : "") + description + ": " + value + "<br />");
                        break;
                    case "emboss":
                        instructionDataList.put("Embossing" + (UtilValidate.isNotEmpty(m.group(2)) ? " " + m.group(2) : "") + ":", (UtilValidate.isNotEmpty(instructionDataList.get("Embossing" + (UtilValidate.isNotEmpty(m.group(2)) ? " " + m.group(2) : "") + ":")) ? instructionDataList.get("Embossing" + (UtilValidate.isNotEmpty(m.group(2)) ? " " + m.group(2) : "") + ":") : "") + description + ": " + value + "<br />");
                        break;
                    case "rightPocket":
                        instructionDataList.put("Right Pocket:", (UtilValidate.isNotEmpty(instructionDataList.get("Right Pocket:")) ? instructionDataList.get("Right Pocket:") : "") + description + ": " + value + "<br />");
                        break;
                    case "centerPocket":
                        instructionDataList.put("Center Pocket:", (UtilValidate.isNotEmpty(instructionDataList.get("Center Pocket:")) ? instructionDataList.get("Center Pocket:") : "") + description + ": " + value + "<br />");
                        break;
                    case "leftPocket":
                        instructionDataList.put("Left Pocket:", (UtilValidate.isNotEmpty(instructionDataList.get("Left Pocket:")) ? instructionDataList.get("Left Pocket:") : "") + description + ": " + value + "<br />");
                        break;
                    case "reinforcedEdge":
                        instructionDataList.put("Reinforced Edge:", (UtilValidate.isNotEmpty(instructionDataList.get("Reinforced Edge:")) ? instructionDataList.get("Reinforced Edge:") : "") + description + ": " + value + "<br />");
                        break;
                    case "backbone":
                        instructionDataList.put("Backbone:", (UtilValidate.isNotEmpty(instructionDataList.get("Backbone:")) ? instructionDataList.get("Backbone:") : "") + description + ": " + value + "<br />");
                        break;
                    case "closure":
                        instructionDataList.put("Closure:", (UtilValidate.isNotEmpty(instructionDataList.get("Closure:")) ? instructionDataList.get("Closure:") : "") + description + ": " + value + "<br />");
                        break;
                    case "gusset":
                        instructionDataList.put("Gusset:", (UtilValidate.isNotEmpty(instructionDataList.get("Gusset:")) ? instructionDataList.get("Gusset:") : "") + description + ": " + value + "<br />");
                        break;
                    case "fileTab":
                        instructionDataList.put("File Tab:", (UtilValidate.isNotEmpty(instructionDataList.get("File Tab:")) ? instructionDataList.get("File Tab:") : "") + description + ": " + value + "<br />");
                        break;
                    case "spine":
                        instructionDataList.put("Spine:", (UtilValidate.isNotEmpty(instructionDataList.get("Spine:")) ? instructionDataList.get("Spine:") : "") + description + ": " + value + "<br />");
                        break;
                    default:
                        instructionDataList.put("Additional Instructions:", (UtilValidate.isNotEmpty(instructionDataList.get("Additional Instructions:")) ? instructionDataList.get("Additional Instructions:") : "") + description + ": " + value + "<br />");
                        break;
                }
            }
		}

		for (Map.Entry<String, String> instructionData : instructionDataList.entrySet()) {
			emailHTML += "<tr>\n" +
				"<tr>" +
					"<td colspan=\"2\" style=\"text-decoration: underline; color: #000000; font-weight: bold;font-size: 15px;\">" + instructionData.getKey() + "</td>" +
				"</tr>" +
				"<tr>" +
					"<td colspan=\"2\" style=\"font: normal 12px/17px Helvetica; color: #595959;\">" + instructionDataList.get(instructionData.getKey()) + "</td>" +
				"</tr>";
        }

		return emailHTML;
	}

	public static String outsourceOrderRequest(Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> context) {
		boolean success = false;
		boolean changeItemStatus = true;

		String xml = null;
		String error = null;

		try {
			if(UtilValidate.isNotEmpty(context.get("orderId")) && UtilValidate.isNotEmpty(context.get("orderItemSeqId")) && UtilValidate.isNotEmpty((String) context.get("vendorPartyId"))) {
				OrderReadHelper orh = new OrderReadHelper(delegator, (String) context.get("orderId"));

				Map<String, List<GenericValue>> orderGroups = CXMLHelper.getOutsourceableGroups(delegator, UtilMisc.<GenericValue>toList(orh.getOrderItem((String) context.get("orderItemSeqId"))), (String) context.get("vendorPartyId"));

				if(UtilValidate.isNotEmpty(orderGroups)) {
					Iterator orderGroupIter = orderGroups.entrySet().iterator();
					while (orderGroupIter.hasNext()) {
						Map.Entry pairs = (Map.Entry) orderGroupIter.next();

						GenericValue vendor = CXMLHelper.getVendor(delegator, (String) pairs.getKey());
						GenericValue orderItemArtwork = OrderHelper.getOrderItemArtwork(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"));
						GenericValue orderItem = orh.getOrderItem((String) context.get("orderItemSeqId"));
						GenericValue vendorItem = CXMLHelper.getVendorProduct(delegator, orderItem.getString("productId"), vendor.getString("partyId"));

						GenericValue shippingAddress = ("SPECIAL_ORDER".equalsIgnoreCase(vendor.getString("poType"))) ? delegator.makeValue("PostalAddress", UtilMisc.toMap("contactMechId", "1", "toName", "Envelopes.com", "address1", "5300 New Horizons Blvd", "city", "Amityville", "stateProvinceGeoId", "NY", "postalCode", "11701", "countryGeoId", "USA")) : OrderHelper.getShippingAddress(orh, delegator, null);
						Map<String, Object> data = getPOData(delegator, shippingAddress, vendor, orderItem, context);

						//if internalProcessing is true then this is for vendors to pick up orders in BOS
						if("Y".equalsIgnoreCase(vendor.getString("internalProcessing"))) {
							changeItemStatus = false;
							EnvUtil.convertMapValuesToString(data);
							Debug.logWarning("OUTSOURCE email(Internal Processing):\n" + new Gson().toJson(data), module);
							try {
								Map<String, Object> result = dispatcher.runSync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "OutsourceArtwork", "data", ListrakHelper.createOutsourceArtworkData(data), "email", vendor.getString("email"), "webSiteId", EnvUtil.getWebsiteId(orh.getOrderHeader().getString("salesChannelEnumId"))));

								if (ServiceUtil.isSuccess(result)) {
									success = true;
									CXMLOrderResponse.saveVendorOrder(delegator, vendor.getString("partyId"), UtilMisc.<GenericValue>toList(orderItem), null, (String) context.get("comments"), CXMLOrderRequest.getPayloadID((String) context.get("orderId")), Long.valueOf("1"), (String) context.get("data"), (String) context.get("cost"));
									CXMLHelper.setOutsourceComment(delegator, orderItemArtwork);
								} else {
									success = false;
									error = "Error sending outsource email.";
								}
							} catch (Exception e) {
								Debug.logError("Error sending email.", module);
							}
						} else {
							//send orders out to vendors
							String workerUrl = CXMLHelper.getPDFUrl(delegator, (String) context.get("orderId"), (String) context.get("orderItemSeqId"), "_WORKER");
							if (workerUrl != null) {
								Debug.logWarning("OUTSOURCE PO Data:\n" + new Gson().toJson(data), module);
								//create PO
								Map<String, Object> purchaseOrder = NetsuiteHelper.createPurchaseOrder(delegator, dispatcher, data);
								//if po# was received, check one more time if it was created, sometimes netsuite sends wrong response back
								if (UtilValidate.isNotEmpty(purchaseOrder) && UtilValidate.isEmpty(purchaseOrder.get("purchaseOrderId"))) {
									purchaseOrder = NetsuiteHelper.getOrderItemPO(delegator, dispatcher, data);
								}
								if (UtilValidate.isNotEmpty(purchaseOrder) && UtilValidate.isNotEmpty((String) purchaseOrder.get("purchaseOrderId"))) {
									if (UtilValidate.isNotEmpty(vendor.getString("cxmlEndpoint"))) {
										/*CXMLOrderRequest cxmlRequest = new CXMLOrderRequest(delegator, dispatcher, context);
										cxmlRequest.setVendor(vendor);
										cxmlRequest.createDoc();
										cxmlRequest.createChildren();
										xml = cxmlRequest.getXMLString();*/
										context.put("purchaseOrderId", purchaseOrder.get("purchaseOrderId"));
										BaseCXMLOrderRequest cxmlRequest = "V_NAVITOR".equals(vendor.getString("partyId")) ? new NavitorCXMLOrderRequest(delegator, dispatcher, context, vendor) : new BaseCXMLOrderRequest(delegator, dispatcher, context, vendor);
										xml = cxmlRequest.toString();
										Debug.logWarning("OUTSOURCE cXML:\n" + xml, module);
										String result = HTTPHelper.getURL(vendor.getString("cxmlEndpoint"), "POST", null, null, UtilMisc.toMap("key", xml), null, false, EnvConstantsUtil.RESPONSE_XML, EnvConstantsUtil.RESPONSE_XML);
										Debug.logWarning("OUTSOURCE cXML Response:\n" + result, module);
										CXMLOrderResponse cxmlResponse = new CXMLOrderResponse(result);
										if (cxmlResponse.isSuccess()) {
											success = true;
											CXMLOrderResponse.saveVendorOrder(delegator, (String) context.get("vendorPartyId"), cxmlRequest.getOrderItems(), (String) purchaseOrder.get("purchaseOrderId"), (String) context.get("comments"), cxmlRequest.getPayloadID(), Long.valueOf("1"), (String) context.get("data"), (String) context.get("cost"));
											CXMLHelper.setOutsourceComment(delegator, orderItemArtwork);
										} else {
											success = false;
											error = result;
										}
									} else {
										Map<String, Object> file = NetsuiteHelper.getPOPDF(delegator, dispatcher, UtilMisc.toMap("purchaseOrderId", (String) purchaseOrder.get("purchaseOrderId")));
										if (UtilValidate.isNotEmpty((String) file.get("path"))) {
											Map shipData = NetsuiteHelper.getShipMethod(delegator, dispatcher, orh, shippingAddress, orh.getShippingTotal(), orh.getAdjustments());

											Map<String, String> orderItemAttribute = OrderHelper.getOrderItemAttributeMap(delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId"));
											Integer colorsFront = Integer.valueOf(UtilValidate.isNotEmpty(orderItemAttribute.get("colorsFront")) ? orderItemAttribute.get("colorsFront") : "0");
											Integer colorsBack = Integer.valueOf(UtilValidate.isNotEmpty(orderItemAttribute.get("colorsBack")) ? orderItemAttribute.get("colorsBack") : "0");

											GenericValue product = orderItem.getRelatedOne("Product", true);

											Map<String, String> prodFeatures = ProductHelper.getProductFeatures(delegator, product, UtilMisc.toList("COLOR", "SEALING_METHOD"), true);

											data.put("orderId", (String) context.get("orderId"));
											data.put("color", (UtilValidate.isNotEmpty(prodFeatures) && UtilValidate.isNotEmpty(prodFeatures.get("Color"))) ? (String) prodFeatures.get("Color") : "Not Available");
											data.put("sealingMethod", (UtilValidate.isNotEmpty(prodFeatures) && UtilValidate.isNotEmpty(prodFeatures.get("Sealing Method"))) ? (String) prodFeatures.get("Sealing Method") : "Not Available");
											data.put("colorsFront", colorsFront);
											data.put("frontInk", OrderHelper.getOrderItemInkList(orderItemArtwork, true));
											data.put("colorsBack", colorsBack);
											data.put("backInk", OrderHelper.getOrderItemInkList(orderItemArtwork, false));
											data.put("shippingMethod", (String) shipData.get("shipMethodDesc"));
											data.put("upsAccount", vendor.getString("upsAccount"));
											data.put("vendorSku", vendorItem.getString("vendorProductId"));
											data.put("poFile", CXMLHelper.getPOPDFUrl(delegator, (String) file.get("path"), (String) file.get("name")));
											data.put("isBlindShip", OrderHelper.isBlindShipment(delegator, shippingAddress.getString("contactMechId")) ? "Yes" : "No");
											data.put("workerFile", workerUrl);
											data.put("orderNumber", (String) purchaseOrder.get("purchaseOrderId"));

											EnvUtil.convertMapValuesToString(data);
											Debug.logWarning("OUTSOURCE email:\n" + new Gson().toJson(data), module);
											try {
												Map<String, Object> result = dispatcher.runSync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "OrderOutsource", "data", ListrakHelper.createOrderOutsourceData(data), "email", vendor.getString("email"), "webSiteId", EnvUtil.getWebsiteId(orh.getOrderHeader().getString("salesChannelEnumId"))));
												dispatcher.runSync("sendListrakEmail", UtilMisc.toMap("listName", "Transactional", "messageName", "OrderOutsource", "data", ListrakHelper.createOrderOutsourceData(data), "email", "outsource@" + EnvUtil.getWebsiteId(orh.getOrderHeader().getString("salesChannelEnumId")) + ".com", "webSiteId", EnvUtil.getWebsiteId(orh.getOrderHeader().getString("salesChannelEnumId"))));

												if (ServiceUtil.isSuccess(result)) {
													success = true;
													CXMLOrderResponse.saveVendorOrder(delegator, vendor.getString("partyId"), UtilMisc.<GenericValue>toList(orderItem), (String) purchaseOrder.get("purchaseOrderId"), (String) context.get("comments"), CXMLOrderRequest.getPayloadID((String) context.get("orderId")), Long.valueOf("1"), (String) context.get("data"), (String) context.get("cost"));
													CXMLHelper.setOutsourceComment(delegator, orderItemArtwork);
												} else {
													success = false;
													error = "Error sending outsource email.";
												}
											} catch (Exception e) {
												Debug.logError("Error sending email.", module);
											}
										} else {
											success = false;
											error = "No PO file found.";
										}
									}
								} else {
									success = false;
									error = "Error creating PO in Netsuite.";
								}
							} else {
								success = false;
								error = "No worker file found.";
							}
						}
					}
				} else {
					success = false;
					error = "Error finding outsourceable groups.";
				}
			}

			//change status if sent successfully
			if(success && changeItemStatus) {
				dispatcher.runSync("changeOrderItemStatus", UtilMisc.toMap("orderId", context.get("orderId"), "orderItemSeqId", context.get("orderItemSeqId"), "statusId", "ART_OUTSOURCED", "userLogin", context.get("userLogin")));
			}
		} catch(Exception e) {
			success = false;
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to send cxml: " + xml, module);
		}

		//if success is false throw email error
		if(!success) {
			try {
				String message = "Outsource for " + context.get("orderId") + " - " + context.get("orderItemSeqId") + " was unsuccessful.";
				//dispatcher.runAsync("sendEmail", UtilMisc.toMap("email", "outsourcing@envelopes.com", "rawData", null, "data", UtilMisc.<String, String>toMap("subject", "Vendor Outsource", "request", message), "messageType", "genericEmail", "webSiteId", "envelopes"));
			} catch(Exception e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "Error trying to send email: " + xml, module);
			}
		}

		return error;
	}

	public static Map<String, Object> getPOData(Delegator delegator, GenericValue shippingAddress, GenericValue vendor, GenericValue orderItem, Map<String, Object> context) throws Exception {
		boolean isRush = OrderHelper.isRush(delegator, null, null, orderItem);
		GenericValue vendorProduct = CXMLHelper.getVendorProduct(delegator, orderItem.getString("productId"), vendor.getString("partyId"));

		boolean stockSupplied = (vendorProduct != null && "Y".equalsIgnoreCase(vendorProduct.getString("stockSupplied"))) ? true : false;
		Map<String, Object> data = new HashMap<>();
		data.put("orderId", (String) context.get("orderId"));
		data.put("lineItem", EnvUtil.removeChar("0", (String) context.get("orderItemSeqId"), true, false, false));
		data.put("sku", orderItem.getString("productId"));
		data.put("name", orderItem.getString("itemDescription"));
		data.put("quantity", orderItem.getBigDecimal("quantity"));
		data.put("vendorId", vendor.getString("netsuiteId"));
		data.put("cost", new BigDecimal((String) context.get("cost")));
		data.put("comments", (String) context.get("comments"));
		data.put("emailPO", false);
		data.put("shipDate", (isRush) ? EnvConstantsUtil.MDY.format(UtilValidate.isEmpty(orderItem.get("dueDate")) ? UtilDateTime.nowTimestamp() : orderItem.getTimestamp("dueDate")) : EnvConstantsUtil.MDY.format(OrderHelper.getDueDate(3, null, false)));
		data.put("dueDate", (isRush) ? EnvConstantsUtil.MDYTA.format(UtilValidate.isEmpty(orderItem.get("dueDate")) ? UtilDateTime.nowTimestamp() : orderItem.getTimestamp("dueDate")) : EnvConstantsUtil.MDYTA.format(OrderHelper.getDueDate(3, null, false)));
		data.put("poType", vendor.getString("poType"));
		data.put("workerFile", CXMLHelper.getPDFUrl(delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId"), "_PDF"));
		data.put("stockSupplied", stockSupplied);
		if(stockSupplied) {
			data.put("stockSuppliedSku", SERVICE_ITEM_SKU);
		}

		GenericValue product = orderItem.getRelatedOne("Product", true);
		Map<String, String> productFeatures = ProductHelper.getProductFeatures(delegator, product, UtilMisc.toList("COLOR", "PAPER_TEXTURE", "COLOR_GROUP", "PAPER_WEIGHT", "COATING"), true);
		data.put("vendorColor", ProductHelper.getVendorColor(delegator, productFeatures));

		if (UtilValidate.isNotEmpty(orderItem.getString("referenceQuoteId"))) {
			GenericValue customEnvelope = EntityQuery.use(delegator).from("CustomEnvelope").where("quoteId", orderItem.getString("referenceQuoteId")).queryOne();
			if (customEnvelope != null && UtilValidate.isNotEmpty(customEnvelope.getString("assignedTo"))) {
				data.put("assignedTo", customEnvelope.getString("assignedTo"));
			}

			if (UtilValidate.isNotEmpty(orderItem.getString("referenceQuoteItemSeqId"))) {
				GenericValue qcQuote = EntityQuery.use(delegator).from("QcQuote").where("quoteId", orderItem.getString("referenceQuoteItemSeqId"), "quoteRequestId", orderItem.getString("referenceQuoteId")).queryOne();
				if (qcQuote != null && UtilValidate.isNotEmpty(qcQuote.getString("pricingRequest"))) {
					HashMap<String, Object> pricingRequest = new Gson().fromJson(qcQuote.getString("pricingRequest"), HashMap.class);
					String coatingText = "";
					String coatingSide1 = "None";
					String coatingSide2 = "None";
					if(UtilValidate.isNotEmpty(pricingRequest.get("ADDONS"))) {
						Map sides = (Map) ((List) pricingRequest.get("ADDONS")).get(0);
						if(UtilValidate.isNotEmpty(sides)) {
							coatingText = UtilValidate.isNotEmpty(sides.get("SIDES")) ? "C" + (((List) sides.get("SIDES"))).size() + "S" : "";
							coatingSide1 = UtilValidate.isNotEmpty(sides.get("SIDES")) && ((List) sides.get("SIDES")).size() > 0 && UtilValidate.isNotEmpty(((List) sides.get("SIDES")).get(0)) ? (String) ((List) sides.get("SIDES")).get(0) : "None";
							coatingSide2 = UtilValidate.isNotEmpty(sides.get("SIDES")) && ((List) sides.get("SIDES")).size() > 1 && UtilValidate.isNotEmpty(((List) sides.get("SIDES")).get(1)) ? (String) ((List) sides.get("SIDES")).get(1) : "None";
						}
					}
					data.put("coatingText", coatingText);
					data.put("coatingSide1", coatingSide1);
					data.put("coatingSide2", coatingSide2);
				}
			}
		}

		GenericValue vendorOrder = EntityQuery.use(delegator).from("VendorOrder").where("orderId", orderItem.getString("orderId"), "orderItemSeqId", orderItem.getString("orderItemSeqId")).queryOne();
		if(vendorOrder != null && UtilValidate.isNotEmpty(vendorOrder.getString("purchaseOrderId"))) {
			String fileName = (String) context.get("orderId") + "-" + vendorOrder.getString("purchaseOrderId") + ".pdf";
			data.put("poFile", CXMLHelper.getPOPDFUrl(delegator, "uploads/purchaseorders/" + fileName, fileName));
		}

		data.put("shippingAddressName", shippingAddress.getString("toName"));
		data.put("shippingCompanyName", shippingAddress.getString("companyName"));
		data.put("shippingAddress1", shippingAddress.getString("address1"));
		data.put("shippingAddress2", shippingAddress.getString("address2"));
		data.put("shippingCity", shippingAddress.getString("city"));
		data.put("shippingState", shippingAddress.getString("stateProvinceGeoId"));
		data.put("shippingZip", shippingAddress.getString("postalCode"));
		data.put("orderFirstName", PartyHelper.splitAtFirstSpace(shippingAddress.getString(PartyHelper.TO_NAME))[0]);
		data.put("quantity", EnvUtil.convertBigDecimal(orderItem.getBigDecimal("quantity"), true));
		data.put("POInstructionsHTML", getOutsourceArtworkEmailData(delegator, new OrderReadHelper(delegator, (String) context.get("orderId")), (String) context.get("orderId"), (String) context.get("orderItemSeqId")));

		return data;
	}

	public static String getXMLString(Document xml) throws TransformerException {
		StringWriter writer = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

		DocumentType doctype = new DocumentTypeImpl(null, "cXML SYSTEM", "", "http://xml.cxml.org/schemas/cXML/1.2.029/cXML.dtd");
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
		transformer.transform(new DOMSource(xml), new StreamResult(writer));
		return writer.toString();
	}
}
