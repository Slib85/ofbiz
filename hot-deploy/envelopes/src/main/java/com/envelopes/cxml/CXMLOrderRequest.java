/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.cxml;

import java.io.*;
import java.lang.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.envelopes.netsuite.NetsuiteHelper;
import com.envelopes.product.ProductHelper;
import com.itextpdf.text.pdf.PdfReader;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.LocalDispatcher;
import org.w3c.dom.*;

import org.apache.xerces.dom.DocumentTypeImpl;
import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.order.order.OrderReadHelper;
import com.envelopes.order.OrderHelper;


import com.envelopes.util.*;

public class CXMLOrderRequest {
	public static final String module = CXMLOrderRequest.class.getName();

	protected Map<String, Object> context = null;
	protected Document xml = null;
	protected OrderReadHelper orh = null;
	protected Delegator delegator = null;
	protected LocalDispatcher dispatcher = null;
	protected List<GenericValue> orderItems = null;
	protected GenericValue vendor = null;
	protected GenericValue vendorProduct = null;
	protected boolean orderPerLine = false;
	protected String payloadID = null;

	/**
	 * CXMLOrderRequest constructor
	 */
	public CXMLOrderRequest(Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> context, List<GenericValue> orderItems)  {
		this.delegator = delegator;
		this.dispatcher = dispatcher;
		this.orderItems = orderItems;
		this.context = context;

		if(UtilValidate.isNotEmpty(this.context.get("orderId"))) {
			this.orh = new OrderReadHelper(this.delegator, (String) this.context.get("orderId"));
			setPayloadID();
		}
	}

	public CXMLOrderRequest(Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> context)  {
		this.delegator = delegator;
		this.dispatcher = dispatcher;
		this.context = context;

		if(UtilValidate.isNotEmpty(this.context.get("orderId")) && UtilValidate.isNotEmpty(this.context.get("orderItemSeqId"))) {
			this.orh = new OrderReadHelper(this.delegator, (String) this.context.get("orderId"));
			this.orderItems = UtilMisc.<GenericValue>toList(this.orh.getOrderItem((String) this.context.get("orderItemSeqId")));
			this.orderPerLine = true;

			setPayloadID();
		}
	}

	public void createDoc() throws Exception {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		this.xml = docBuilder.newDocument();

		this.xml.appendChild(createChildren());
	}

	public Element createChildren() throws Exception {
		Element rootElement = createElement("cXML");
		rootElement.setAttribute("payloadID", getPayloadID());
		rootElement.setAttribute("timestamp", EnvConstantsUtil.UTC.format(UtilDateTime.nowTimestamp()));
		rootElement.setAttribute("version", "1.2.029");
		rootElement.setAttribute("xml:lang", "en-US");

		CXMLHelper.createHeader(this.xml, rootElement, this.vendor);
		rootElement.appendChild(createRequest());

		return rootElement;
	}

	public Element createRequest() throws Exception {
		Element request = createElement("Request");
		request.setAttribute("deploymentMode", "production");

		request.appendChild(createOrderRequest());

		return request;
	}

	public Element createOrderRequest() throws Exception {
		Element orderRequest = createElement("OrderRequest");
		orderRequest.appendChild(createOrderRequestHeader());

		List<Element> items = createItems();
		for(Element item : items) {
			orderRequest.appendChild(item);
		}

		return orderRequest;
	}

	public Element createOrderRequestHeader() throws Exception {
		Element orderRequestHeader = createElement("OrderRequestHeader");
		orderRequestHeader.setAttribute("orderDate", EnvConstantsUtil.UTC.format(this.orh.getOrderHeader().getTimestamp("entryDate")));
		orderRequestHeader.setAttribute("orderID", (this.orderPerLine) ? this.orh.getOrderId() + "_" + (String) this.context.get("orderItemSeqId") : this.orh.getOrderId());
		orderRequestHeader.setAttribute("orderType", "regular");
		orderRequestHeader.setAttribute("orderVersion", "1");
		orderRequestHeader.setAttribute("type", "new");

		BigDecimal totalCost = new BigDecimal((String) context.get("cost"));

		Element total = createElement("Total");
		Element money = createElement("Money");
		money.setAttribute("currency", "USD");
		money.appendChild(createTextNode(totalCost.toPlainString()));
		total.appendChild(money);

		orderRequestHeader.appendChild(total);
		orderRequestHeader.appendChild(createShipTo());
		orderRequestHeader.appendChild(createBillTo(true));

		return orderRequestHeader;
	}

	public Element createShipTo() throws GenericEntityException {
		Element shipTo = createElement("ShipTo");

		Element address;
		if("SPECIAL_ORDER".equalsIgnoreCase(vendor.getString("poType"))) {
			address = createAddress(this.delegator.makeValue("PostalAddress", UtilMisc.toMap("contactMechId", "1", "toName", "Envelopes.com", "address1", "5300 New Horizons Blvd", "city", "Amityville", "stateProvinceGeoId", "NY", "postalCode", "11701", "countryGeoId", "USA")));
		} else {
			address = createAddress(OrderHelper.getShippingAddress(this.orh, this.delegator, null));
		}

		Element email = createEmail();
		Element phone = createPhone();
		address.appendChild(email);
		address.appendChild(phone);

		shipTo.appendChild(address);
		return shipTo;
	}

	public Element createBillTo(boolean useEnvAddress) throws GenericEntityException {
		Element shipTo = createElement("BillTo");

		Element address;
		if(useEnvAddress) {
			address = createAddress(this.delegator.makeValue("PostalAddress", UtilMisc.toMap("contactMechId", "1", "toName", "Envelopes.com", "address1", "5300 New Horizons Blvd", "city", "Amityville", "stateProvinceGeoId", "NY", "postalCode", "11701", "countryGeoId", "USA")));
		} else {
			address = createAddress(OrderHelper.getBillingAddress(this.orh, this.delegator, null));
		}
		Element email = createEmail();
		Element phone = createPhone();
		address.appendChild(email);
		address.appendChild(phone);

		shipTo.appendChild(address);
		return shipTo;
	}

	public Element createAddress(GenericValue addressGV) throws GenericEntityException {
		Element address = createElement("Address");
		address.setAttribute("addressID", addressGV.getString("contactMechId"));
		address.setAttribute("isoCountryCode", CXMLHelper.getGeoCodeFromGeoId(this.delegator, addressGV.getString("countryGeoId")));

		Element name = createElement("Name");
		name.setAttribute("xml:lang", "en");
		name.appendChild(createTextNode("Address"));
		address.appendChild(name);

		Element postalAddress = createElement("PostalAddress");
		postalAddress.setAttribute("name", "default");

		Element deliverTo = createElement("DeliverTo");
		deliverTo.appendChild(createTextNode(addressGV.getString("toName")));
		postalAddress.appendChild(deliverTo);

		if(UtilValidate.isNotEmpty(addressGV.getString("companyName"))) {
			Element companyName = createElement("DeliverTo");
			companyName.appendChild(createTextNode(addressGV.getString("companyName")));
			postalAddress.appendChild(companyName);
		}

		Element street = createElement("Street");
		street.appendChild(createTextNode(addressGV.getString("address1")));
		postalAddress.appendChild(street);

		if(UtilValidate.isNotEmpty(addressGV.getString("address2"))) {
			Element address2 = createElement("Street");
			address2.appendChild(createTextNode(addressGV.getString("address2")));
			postalAddress.appendChild(address2);
		}

		Element city = createElement("City");
		city.appendChild(createTextNode(addressGV.getString("city")));
		postalAddress.appendChild(city);

		Element state = createElement("State");
		state.appendChild(createTextNode(addressGV.getString("stateProvinceGeoId")));
		postalAddress.appendChild(state);

		Element postalCode = createElement("PostalCode");
		postalCode.appendChild(createTextNode(addressGV.getString("postalCode")));
		postalAddress.appendChild(postalCode);

		Element country = createElement("Country");
		country.setAttribute("isoCountryCode", CXMLHelper.getGeoCodeFromGeoId(this.delegator, OrderHelper.isUSTerritory(addressGV) ? addressGV.getString("stateProvinceGeoId") : addressGV.getString("countryGeoId")));
		country.appendChild(createTextNode(CXMLHelper.getGeoNameFromGeoId(this.delegator, OrderHelper.isUSTerritory(addressGV) ? addressGV.getString("stateProvinceGeoId") : addressGV.getString("countryGeoId"))));
		postalAddress.appendChild(country);

		address.appendChild(postalAddress);

		return address;
	}

	public Element createEmail() {
		Element email = createElement("Email");
		email.setAttribute("name", "default");
		email.setAttribute("preferredLang", "en-US");
		email.appendChild(createTextNode("outsource@envelopes.com"));

		return email;
	}

	public Element createPhone() {
		Element phone = createElement("Phone");
		phone.setAttribute("name", "work");

		Element telephoneNumber = createElement("TelephoneNumber");

		Element countryCode = createElement("CountryCode");
		countryCode.setAttribute("isoCountryCode", "US");
		countryCode.appendChild(createTextNode("1"));

		Element areaCode = createElement("AreaOrCityCode");
		areaCode.appendChild(createTextNode("877"));

		Element number = createElement("Number");
		number.appendChild(createTextNode("683-5673"));

		telephoneNumber.appendChild(countryCode);
		telephoneNumber.appendChild(areaCode);
		telephoneNumber.appendChild(number);

		phone.appendChild(telephoneNumber);

		return phone;
	}

	public List<Element> createItems() throws Exception {
		List<Element> elements = new ArrayList<>();
		boolean isBlindShipment = OrderHelper.isBlindShipment(this.delegator, (OrderHelper.getShippingAddress(this.orh, this.delegator, null)).getString("contactMechId"));

		for(GenericValue orderItem : this.orderItems) {
			GenericValue product = EntityQuery.use(this.delegator).from("Product").where("productId", orderItem.getString("productId")).queryOne();
			GenericValue productStock = EntityQuery.use(this.delegator).from("ProductStock").where("productId", orderItem.getString("productId")).cache(false).queryOne();
			GenericValue vendorProduct = getVendorProduct(orderItem.getString("productId"));

			GenericValue orderItemArtwork = OrderHelper.getOrderItemArtwork(this.delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId"));
			Map<String, String> orderItemAttribute = OrderHelper.getOrderItemAttributeMap(this.delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId")) ;

			Map<String, String> prodFeatures = ProductHelper.getProductFeatures(delegator, product, UtilMisc.toList("COLOR"), true);
			String supplierPartId = (UtilValidate.isNotEmpty(vendorProduct.getString("vendorParentProductId"))) ? vendorProduct.getString("vendorParentProductId") : vendorProduct.getString("vendorProductId");
			String vendorProductId = (UtilValidate.isNotEmpty(vendorProduct.getString("vendorParentProductId"))) ? vendorProduct.getString("vendorProductId") : null;
			boolean isRush = OrderHelper.isRush(this.delegator, this.orh.getOrderId(), orderItem.getString("orderItemSeqId"), orderItem);
			boolean stockSupplied = (vendorProduct != null && "Y".equalsIgnoreCase(vendorProduct.getString("stockSupplied"))) ? true : false;
			BigDecimal cost = new BigDecimal((String) context.get("cost"));
			//BigDecimal unitCost = cost.divide(orderItem.getBigDecimal("quantity"), 5, BigDecimal.ROUND_HALF_UP);

			Element item = createElement("ItemOut");
			item.setAttribute("lineNumber", EnvUtil.removeChar("0", orderItem.getString("orderItemSeqId"), true, false, false));
			item.setAttribute("quantity", orderItem.getBigDecimal("quantity").toString());

			//comments
			String commentStr = (String) this.context.get("comments");
			commentStr = (UtilValidate.isNotEmpty(commentStr)) ? commentStr.replaceAll("\\r\\n|\\r|\\n", " ") : "";

			Element comments = createElement("Comments");
			comments.setAttribute("xml:lang", "en-US");
			comments.appendChild(createTextNode((UtilValidate.isNotEmpty(commentStr)) ? commentStr : ""));
			item.appendChild(comments);

			Element itemID = createElement("ItemID");
			Element supplierPartID = createElement("SupplierPartID");
			supplierPartID.appendChild(createTextNode(supplierPartId));
			itemID.appendChild(supplierPartID);

			Element itemDetail = createElement("ItemDetail");
			Element unitPrice = createElement("UnitPrice");
			Element money = createElement("Money");
			money.setAttribute("currency", "USD");
			money.appendChild(createTextNode(cost.toPlainString()));
			unitPrice.appendChild(money);

			Element description = createElement("Description");
			description.setAttribute("xml:lang", "en");
			description.appendChild(createTextNode(orderItem.getString("itemDescription")));

			Element unitOfMeasure = createElement("UnitOfMeasure");
			unitOfMeasure.appendChild(createTextNode("LO"));

			Element classification = createElement("Classification");
			classification.setAttribute("domain", "Not Available");
			classification.appendChild(createTextNode("Not Available"));

			Element url = createElement("URL");
			url.appendChild(createTextNode(CXMLHelper.getPDFUrl(this.delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId"), "_WORKER")));

			if(UtilValidate.isNotEmpty(vendorProductId)) {
				Element materialCode = createElement("Extrinsic");
				materialCode.setAttribute("name", "MaterialCode");
				materialCode.appendChild(createTextNode(vendorProductId));
				itemDetail.appendChild(materialCode);
			}

			Element dimensions = createElement("Extrinsic");
			dimensions.setAttribute("name", "Dimensions");
			dimensions.appendChild(createTextNode(ProductHelper.getSize(this.delegator, orderItem.getString("productId"))));

			Element pdfDimensions = createElement("Extrinsic");
			pdfDimensions.setAttribute("name", "PDFDimensions");

			Text documentSize = createTextNode(ProductHelper.getSize(this.delegator, orderItem.getString("productId")));

			try {
                PdfReader reader = new PdfReader(CXMLHelper.getPDFPath(this.delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId"), "_WORKER"));
				float width = reader.getPageSize(1).getWidth()/72;
				float height = reader.getPageSize(1).getHeight()/72;
				documentSize = createTextNode(width + " x " + height);
			} catch (Exception pdfE) {
				EnvUtil.reportError(pdfE);
			} finally {
				pdfDimensions.appendChild(documentSize);
			}

			Element color = createElement("Extrinsic");
			color.setAttribute("name", "Color");
			color.appendChild(createTextNode((UtilValidate.isNotEmpty(prodFeatures) && UtilValidate.isNotEmpty(prodFeatures.get("Color"))) ? (String) prodFeatures.get("Color") : "Not Available"));

			Element blindShipment = createElement("Extrinsic");
			blindShipment.setAttribute("name", "Ship In Name Of");
			blindShipment.appendChild(createTextNode((isBlindShipment) ? "None" : "Customer"));

			Element rushDetails = createElement("Extrinsic");
			rushDetails.setAttribute("name", "rush");
			rushDetails.appendChild(createTextNode((isRush) ? "true" : ""));

			Element dueDate = createElement("Extrinsic");
			dueDate.setAttribute("name", "requestedShipDate");
			dueDate.appendChild(createTextNode((isRush) ? EnvConstantsUtil.yyyyMMddDash.format(EnvUtil.getNDaysBeforeOrAfterDate(orderItem.getTimestamp("dueDate"), -1, false, true)) : ""));

			Element stockLocation = createElement("Extrinsic");
			stockLocation.setAttribute("name", "stockLocation");
			stockLocation.appendChild(createTextNode((stockSupplied && productStock != null && "B".equalsIgnoreCase(productStock.getString("location"))) ? productStock.getString("location") : ""));

			//HARDCODED RULE FOR SPECIFIC CIMPRESS CUSTOMER
			Element customerName = createElement("Extrinsic");
			customerName.setAttribute("name", "specialCustomerRule");
			customerName.appendChild(createTextNode(("2053030".equalsIgnoreCase(this.orh.getPlacingParty().getString("partyId")) || "2162652".equalsIgnoreCase(this.orh.getPlacingParty().getString("partyId"))) ? "CIMPRESS" : ""));

			Element customerDueDate = createElement("Extrinsic");
			customerDueDate.setAttribute("name", "customerDueDate");
			customerDueDate.appendChild(createTextNode((UtilValidate.isNotEmpty(orderItem.get("dueDate"))) ? EnvConstantsUtil.yyyyMMddDash.format(orderItem.getTimestamp("dueDate")) : ""));

			Element numberOfVDA = createElement("Extrinsic");
			numberOfVDA.setAttribute("name", "variableDataUnits");
			numberOfVDA.appendChild(createTextNode(orderItemAttribute.containsKey("addresses") ? orderItemAttribute.get("addresses") : "0"));

			itemDetail.appendChild(unitPrice);
			itemDetail.appendChild(description);
			itemDetail.appendChild(unitOfMeasure);
			itemDetail.appendChild(classification);
			itemDetail.appendChild(url);
			itemDetail.appendChild(dimensions);
			itemDetail.appendChild(pdfDimensions);
			itemDetail.appendChild(color);
			itemDetail.appendChild(blindShipment);
			itemDetail.appendChild(rushDetails);
			itemDetail.appendChild(dueDate);
			itemDetail.appendChild(stockLocation);
			itemDetail.appendChild(customerName);
			itemDetail.appendChild(customerDueDate);
			itemDetail.appendChild(numberOfVDA);

			//SIDES
			Integer colorsFront = Integer.valueOf(UtilValidate.isNotEmpty(orderItemAttribute.get("colorsFront")) ? orderItemAttribute.get("colorsFront") : "0");
			Integer colorsBack = Integer.valueOf(UtilValidate.isNotEmpty(orderItemAttribute.get("colorsBack")) ? orderItemAttribute.get("colorsBack") : "0");

			Element totalInkFront = createElement("Extrinsic");
			totalInkFront.setAttribute("name", "Side 1 Inks");
			totalInkFront.appendChild(createTextNode(colorsFront.toString()));

			Element totalInkBack = createElement("Extrinsic");
			totalInkBack.setAttribute("name", "Side 2 Inks");
			totalInkBack.appendChild(createTextNode(colorsBack.toString()));

			itemDetail.appendChild(totalInkFront);
			itemDetail.appendChild(totalInkBack);

			if(UtilValidate.isNotEmpty(orderItemArtwork.getString("frontInkColor1"))) {
				Element ink = createElement("Extrinsic");
				ink.setAttribute("name", "Side 1 Ink 1");
				ink.appendChild(createTextNode(orderItemArtwork.getString("frontInkColor1")));
				itemDetail.appendChild(ink);
			}
			if(UtilValidate.isNotEmpty(orderItemArtwork.getString("frontInkColor2"))) {
				Element ink = createElement("Extrinsic");
				ink.setAttribute("name", "Side 1 Ink 2");
				ink.appendChild(createTextNode(orderItemArtwork.getString("frontInkColor2")));
				itemDetail.appendChild(ink);
			}
			if(UtilValidate.isNotEmpty(orderItemArtwork.getString("frontInkColor3"))) {
				Element ink = createElement("Extrinsic");
				ink.setAttribute("name", "Side 1 Ink 3");
				ink.appendChild(createTextNode(orderItemArtwork.getString("frontInkColor3")));
				itemDetail.appendChild(ink);
			}
			if(UtilValidate.isNotEmpty(orderItemArtwork.getString("frontInkColor4"))) {
				Element ink = createElement("Extrinsic");
				ink.setAttribute("name", "Side 1 Ink 4");
				ink.appendChild(createTextNode(orderItemArtwork.getString("frontInkColor4")));
				itemDetail.appendChild(ink);
			}
			if(UtilValidate.isNotEmpty(orderItemArtwork.getString("backInkColor1"))) {
				Element ink = createElement("Extrinsic");
				ink.setAttribute("name", "Side 2 Ink 1");
				ink.appendChild(createTextNode(orderItemArtwork.getString("backInkColor1")));
				itemDetail.appendChild(ink);
			}
			if(UtilValidate.isNotEmpty(orderItemArtwork.getString("backInkColor2"))) {
				Element ink = createElement("Extrinsic");
				ink.setAttribute("name", "Side 2 Ink 2");
				ink.appendChild(createTextNode(orderItemArtwork.getString("backInkColor2")));
				itemDetail.appendChild(ink);
			}
			if(UtilValidate.isNotEmpty(orderItemArtwork.getString("backInkColor3"))) {
				Element ink = createElement("Extrinsic");
				ink.setAttribute("name", "Side 2 Ink 3");
				ink.appendChild(createTextNode(orderItemArtwork.getString("backInkColor3")));
				itemDetail.appendChild(ink);
			}
			if(UtilValidate.isNotEmpty(orderItemArtwork.getString("backInkColor4"))) {
				Element ink = createElement("Extrinsic");
				ink.setAttribute("name", "Side 2 Ink 4");
				ink.appendChild(createTextNode(orderItemArtwork.getString("backInkColor4")));
				itemDetail.appendChild(ink);
			}
			//END SIDE

			Map shipData = NetsuiteHelper.getShipMethod(this.delegator, this.dispatcher, this.orh, OrderHelper.getShippingAddress(this.orh, this.delegator, null), orh.getShippingTotal(), orh.getAdjustments());

			Element requestedShipper = createElement("Extrinsic");
			requestedShipper.setAttribute("name", "requestedShipper");
			requestedShipper.appendChild(createTextNode((String) shipData.get("shipMethodDesc")));

			Element requestedShippingAccount = createElement("Extrinsic");
			requestedShippingAccount.setAttribute("name", "requestedShippingAccount");
			requestedShippingAccount.appendChild(createTextNode(vendor.getString("upsAccount")));

			itemDetail.appendChild(requestedShipper);
			itemDetail.appendChild(requestedShippingAccount);

//			Element distribution = createElement("Distribution");
//			Element accounting = createElement("Accounting");
//			accounting.setAttribute("name", "DistributionCharge");
//
//			Element segment = createElement("Segment");
//			segment.setAttribute("description", "XXX");
//			segment.setAttribute("id", "");
//			segment.setAttribute("type", "CostCenter");
//			accounting.appendChild(segment);
//
//			Element charge = createElement("Charge");
//			Element moneyCharge = createElement("Money");
//			moneyCharge.setAttribute("currency", "USD");
//			moneyCharge.appendChild(createTextNode("0.00"));
//			charge.appendChild(moneyCharge);
//
//			distribution.appendChild(charge);
//			distribution.appendChild(accounting);

			item.appendChild(itemID);
			item.appendChild(itemDetail);
//			item.appendChild(distribution);

			elements.add(item);
		}

		return elements;
	}

	public String getXMLString() throws TransformerException {
		return CXMLHelper.getXMLString(this.xml);
	}

	public void setVendor(GenericValue vendor) {
		this.vendor = vendor;
	}

	public GenericValue getVendor() {
		return this.vendor;
	}

	public GenericValue getVendorProduct(String productId) throws GenericEntityException {
		return CXMLHelper.getVendorProduct(this.delegator, productId, this.vendor.getString("partyId"));
	}

	public List<GenericValue> getOrderItems() {
		return this.orderItems;
	}

	public String getVendorPartyId() {
		if(this.vendor != null) {
			return this.vendor.getString("partyId");
		}

		return null;
	}

	public String getVendorNetsuiteId() {
		if(this.vendor != null) {
			return this.vendor.getString("netsuiteId");
		}

		return null;
	}

	public boolean isCXMLReady() {
		if(this.vendor != null && UtilValidate.isNotEmpty(this.vendor.getString("cxmlEndpoint"))) {
			return true;
		}

		return false;
	}

	public String getPayloadID() {
		return this.payloadID;
	}

	private void setPayloadID() {
		if(this.payloadID == null) {
			this.payloadID = this.orh.getOrderId() + "-" + UtilDateTime.nowAsString();
		}
	}

	public static String getPayloadID(String orderId) {
		return orderId + "-" + UtilDateTime.nowAsString();
	}

	private Element createElement(String str) {
		return this.xml.createElement(str);
	}

	private Text createTextNode(String str) {
		return this.xml.createTextNode(str);
	}
}
