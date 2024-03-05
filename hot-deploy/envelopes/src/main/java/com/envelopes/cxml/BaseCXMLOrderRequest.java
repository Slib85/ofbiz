package com.envelopes.cxml;

import com.bigname.cxml.cxml.*;
import com.bigname.cxml.cxml.Number;
import com.envelopes.netsuite.NetsuiteHelper;
import com.envelopes.order.OrderHelper;
import com.envelopes.product.ProductHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.order.order.OrderReadHelper;
import org.apache.ofbiz.service.LocalDispatcher;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseCXMLOrderRequest {
    public static final String module = BaseCXMLOrderRequest.class.getName();

    protected Map<String, Object> context;
    protected CXML cxml;
    protected OrderReadHelper orh;
    protected Delegator delegator;
    protected LocalDispatcher dispatcher;
    protected List<GenericValue> orderItems;
    protected GenericValue vendor;
    protected GenericValue shippingAddress;
    protected GenericValue billingAddress;
    protected String shippingMethod;
    protected boolean orderPerLine;
    protected String payloadID;
    protected boolean isBlindShip;
    protected String placingPartyId;
    protected boolean billToEnvelopes = true;
    protected String rootURL = EnvConstantsUtil.IS_DEV ? "https://localhost" : EnvConstantsUtil.IS_QA ? "https://qa.envelopes.com" : "https://www.envelopes.com";
    protected enum AddressType { SHIPPING, BILLING }

    protected BaseCXMLOrderRequest(Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> context, List<GenericValue> orderItems, GenericValue vendor) throws Exception {
        this.delegator = delegator;
        this.dispatcher = dispatcher;
        this.context = context;
        this.vendor = vendor;



        if(UtilValidate.isNotEmpty(orderItems)) {
            this.orderItems = orderItems;
            if(UtilValidate.isNotEmpty(this.context.get("orderId"))) {
                this.orh = new OrderReadHelper(this.delegator, (String) this.context.get("orderId"));
            }
        } else {
            if(UtilValidate.isNotEmpty(this.context.get("orderId")) && UtilValidate.isNotEmpty(this.context.get("orderItemSeqId"))) {
                this.orh = new OrderReadHelper(this.delegator, (String) this.context.get("orderId"));
                this.orderItems = UtilMisc.<GenericValue>toList(this.orh.getOrderItem((String) this.context.get("orderItemSeqId")));
                this.orderPerLine = true;
            }
        }
        placingPartyId = this.orh.getPlacingParty().getString("partyId");
        shippingAddress = OrderHelper.getShippingAddress(this.orh, this.delegator, null);
        if("SPECIAL_ORDER".equalsIgnoreCase(vendor.getString("poType"))) {
            shippingAddress = this.delegator.makeValue("PostalAddress", UtilMisc.toMap("contactMechId", "1", "toName", "Envelopes.com", "address1", "105 Maxess Rd, Rm S215", "city", "Melville", "stateProvinceGeoId", "NY", "postalCode", "11747", "countryGeoId", "USA"));
        }

        billingAddress = this.delegator.makeValue("PostalAddress", UtilMisc.toMap("contactMechId", "1", "toName", "Envelopes.com", "address1", "105 Maxess Rd, Rm S215", "city", "Melville", "stateProvinceGeoId", "NY", "postalCode", "11747", "countryGeoId", "USA"));
        if(!billToEnvelopes) {
            OrderHelper.getBillingAddress(this.orh, this.delegator, null);
        }

        try {
            Map shippingData = NetsuiteHelper.getShipMethod(this.delegator, this.dispatcher, this.orh, shippingAddress, orh.getShippingTotal(), orh.getAdjustments());
            shippingMethod = (String) shippingData.get("shipMethodDesc");
            isBlindShip = OrderHelper.isBlindShipment(this.delegator, shippingAddress.getString("contactMechId"));
        } catch(Exception e) {
            EnvUtil.reportError(e);
        }
        init();
    }

    protected BaseCXMLOrderRequest(Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> context, GenericValue vendor) throws Exception {
        this(delegator, dispatcher, context, null, vendor);
    }

    protected void init() {
        setPayloadID();
        createRootNode().createHeaderNode().createRequestNode();
    }

    protected BaseCXMLOrderRequest createRootNode() {
        this.cxml = new CXML();
        cxml.setLang("en-US");
        cxml.setVersion("1.2.029");
        cxml.setTimestamp(EnvConstantsUtil.UTC.format(UtilDateTime.nowTimestamp()) );
        cxml.setPayloadID(getPayloadID());
        return this;
    }

    protected BaseCXMLOrderRequest createHeaderNode() {
        Header header = new Header();
        this.createFromNode(header)
            .createToNode(header)
            .createSenderNode(header);
        cxml.setHeader(header);
        return this;
    }

    protected BaseCXMLOrderRequest createFromNode(Header header) {
        String domain = vendor.getString("fromDomain");
        From from = new From();
        from.getCredential().add(createCredentialNode(domain, "DUNS".equalsIgnoreCase(domain) ? EnvConstantsUtil.BIGNAME_DUNS : vendor.getString("fromIdentity")));
        header.setFrom(from);
        return this;
    }

    protected BaseCXMLOrderRequest createToNode(Header header) {
        String domain = vendor.getString("toDomain");
        To to = new To();
        to.getCredential().add(createCredentialNode(domain, vendor.getString("toIdentity")));
        header.setTo(to);
        return this;
    }

    protected BaseCXMLOrderRequest createSenderNode(Header header) {
        String domain = vendor.getString("fromDomain");
        Sender sender = new Sender();
        sender.getCredential().add(createCredentialNode(domain, "DUNS".equalsIgnoreCase(domain) ? EnvConstantsUtil.BIGNAME_DUNS : vendor.getString("fromIdentity"), vendor.getString("sharedSecret")));
        UserAgent userAgent = new UserAgent();
        userAgent.setContent("BOS_1.0");
        sender.setUserAgent(userAgent);
        header.setSender(sender);
        return this;
    }

    protected Credential createCredentialNode(String domain, String id, String... secret) {
        Credential credential = new Credential();
        Identity identity = new Identity();
        identity.getContent().add(id);
        credential.setDomain(domain);
        credential.setIdentity(identity);
        if(secret != null && secret.length > 0 && UtilValidate.isNotEmpty(secret[0])) {
            SharedSecret sharedSecret = new SharedSecret();
            sharedSecret.getContent().add(secret[0]);
            credential.setSharedSecret(sharedSecret);
        }
        return credential;
    }

    protected BaseCXMLOrderRequest createRequestNode() {
        Request request = new Request();
        request.setDeploymentMode(EnvConstantsUtil.IS_PRODUCTION ? "production" : "test");
        OrderRequest orderRequest = new OrderRequest();
        this.createOrderRequestHeaderNode(orderRequest)
            .createItemOutNodes(orderRequest);
        request.setOrderRequest(orderRequest);
        cxml.setRequest(request);
        return this;
    }

    protected BaseCXMLOrderRequest createOrderRequestHeaderNode(OrderRequest orderRequest) {
        OrderRequestHeader orderRequestHeader = new OrderRequestHeader();
        orderRequestHeader.setOrderDate(EnvConstantsUtil.UTC.format(this.orh.getOrderHeader().getTimestamp("entryDate")));
        orderRequestHeader.setOrderID((this.orderPerLine) ? this.orh.getOrderId() + "_" + this.context.get("orderItemSeqId") : this.orh.getOrderId());
        orderRequestHeader.setType("new");
        orderRequestHeader.setOrderType("regular");
        orderRequestHeader.setOrderVersion("1");
        this.createTotalNode(orderRequestHeader)
            .createShipToNode(orderRequestHeader)
            .createBillToNode(orderRequestHeader);
        orderRequest.setOrderRequestHeader(orderRequestHeader);
        return this;
    }

    protected BaseCXMLOrderRequest createTotalNode(OrderRequestHeader orderRequestHeader) {
        Total total = new Total();
        total.setMoney(createMoneyNode("USD", new BigDecimal((String) context.get("cost")).toPlainString()));
        orderRequestHeader.setTotal(total);
        return this;
    }

    protected BaseCXMLOrderRequest createShipToNode(OrderRequestHeader orderRequestHeader) {
        ShipTo shipTo = new ShipTo();
        shipTo.setAddress(createAddressNode(AddressType.SHIPPING));
        orderRequestHeader.setShipTo(shipTo);
        return this;
    }

    protected BaseCXMLOrderRequest createBillToNode(OrderRequestHeader orderRequestHeader) {
        BillTo billTo = new BillTo();
        billTo.setAddress(createAddressNode(AddressType.BILLING));
        orderRequestHeader.setBillTo(billTo);
        return this;
    }

    protected Address createAddressNode(AddressType addressType) {
        GenericValue addressGV = addressType == AddressType.BILLING ? this.billingAddress : this.shippingAddress;
        Address address = new Address();
        address.setAddressID(addressGV.getString("contactMechId"));
        address.setIsoCountryCode(CXMLHelper.getGeoCodeFromGeoId(this.delegator, addressGV.getString("countryGeoId")));
        this.createNameNode(address, addressType)
            .createPostalAddressNode(address, addressType)
            .createEmailNode(address, addressType)
            .createPhoneNode(address, addressType);
        return address;
    }

    protected BaseCXMLOrderRequest createNameNode(Address address, AddressType addressType) {
        Name name = new Name();
        name.setLang("en");
        name.setContent("Address");
        address.setName(name);
        return this;
    }

    protected BaseCXMLOrderRequest createPostalAddressNode(Address address, AddressType addressType) {
        GenericValue addressGV = addressType == AddressType.BILLING ? this.billingAddress : this.shippingAddress;
        PostalAddress postalAddress = new PostalAddress();
        postalAddress.setName("default");
        DeliverTo deliverTo = new DeliverTo();
        deliverTo.setContent(addressGV.getString("toName"));
        postalAddress.getDeliverTo().add(deliverTo);
        if(UtilValidate.isNotEmpty(addressGV.getString("companyName"))) {
            deliverTo = new DeliverTo();
            deliverTo.setContent(addressGV.getString("companyName"));
            postalAddress.getDeliverTo().add(deliverTo);
        }
        Street street = new Street();
        street.setContent(addressGV.getString("address1"));
        postalAddress.getStreet().add(street);
        if(UtilValidate.isNotEmpty(addressGV.getString("address2"))) {
            street = new Street();
            street.setContent(addressGV.getString("address2"));
            postalAddress.getStreet().add(street);
        }
        City city = new City();
        city.setContent(addressGV.getString("city"));
        postalAddress.setCity(city);
        State state = new State();
        state.setContent(addressGV.getString("stateProvinceGeoId"));
        postalAddress.setState(state);
        PostalCode postalCode = new PostalCode();
        postalCode.setContent(addressGV.getString("postalCode"));
        postalAddress.setPostalCode(postalCode);
        Country country = new Country();
        country.setIsoCountryCode(CXMLHelper.getGeoCodeFromGeoId(this.delegator, OrderHelper.isUSTerritory(addressGV) ? addressGV.getString("stateProvinceGeoId") : addressGV.getString("countryGeoId")));
        country.setContent(CXMLHelper.getGeoNameFromGeoId(this.delegator, OrderHelper.isUSTerritory(addressGV) ? addressGV.getString("stateProvinceGeoId") : addressGV.getString("countryGeoId")));
        postalAddress.setCountry(country);
        address.setPostalAddress(postalAddress);
        return this;
    }

    protected BaseCXMLOrderRequest createEmailNode(Address address, AddressType addressType) {
        Email email = new Email();
        email.setName("default");
        email.setPreferredLang("en-US");
        email.setContent("outsource@envelopes.com");
        address.setEmail(email);
        return this;
    }

    protected BaseCXMLOrderRequest createPhoneNode(Address address, AddressType addressType) {
        Phone phone = new Phone();
        phone.setName("work");
        this.createTelephoneNode(phone, addressType);
        address.setPhone(phone);
        return this;
    }
    protected BaseCXMLOrderRequest createTelephoneNode(Phone phone, AddressType addressType) {
        TelephoneNumber telephoneNumber = new TelephoneNumber();
        this.createCountryCodeNode(telephoneNumber, addressType)
            .createAreaCodeNode(telephoneNumber, addressType)
            .createNumberNode(telephoneNumber, addressType);

        return this;
    }

    protected BaseCXMLOrderRequest createCountryCodeNode(TelephoneNumber telephoneNumber, AddressType addressType) {
        CountryCode countryCode = new CountryCode();
        countryCode.setIsoCountryCode("US");
        countryCode.setContent("1");
        telephoneNumber.setCountryCode(countryCode);
        return this;
    }

    protected BaseCXMLOrderRequest createAreaCodeNode(TelephoneNumber telephoneNumber, AddressType addressType) {
        AreaOrCityCode areaOrCityCode = new AreaOrCityCode();
        areaOrCityCode.setContent("877");
        telephoneNumber.setAreaOrCityCode(areaOrCityCode);
        return this;
    }

    protected BaseCXMLOrderRequest createNumberNode(TelephoneNumber telephoneNumber, AddressType addressType) {
        Number number = new Number();
        number.setContent("683-5673");
        telephoneNumber.setNumber(number);
        return this;
    }

    protected BaseCXMLOrderRequest createItemOutNodes(OrderRequest orderRequest) {
        for(GenericValue orderItem : orderItems) {
            Map<String, Object> lineItemData = getLineItemData(orderItem);
            ItemOut itemOut = new ItemOut();
            itemOut.setLineNumber((String)lineItemData.get("lineNumber"));
            itemOut.setQuantity((String)lineItemData.get("quantity"));
            this.createCommentsNode(itemOut, lineItemData)
                .createItemIdNode(itemOut, lineItemData)
                .createItemDetailNode(itemOut, lineItemData);
            orderRequest.getItemOut().add(itemOut);
        }
        return this;
    }

    protected BaseCXMLOrderRequest createCommentsNode(ItemOut itemOut, Map<String, Object> lineItemData) {
        Comments comments = new Comments();
        comments.getContent().add((String) lineItemData.get("comments"));
        itemOut.setComments(comments);
        return this;
    }

    protected BaseCXMLOrderRequest createItemIdNode(ItemOut itemOut, Map<String, Object> lineItemData) {
        ItemID itemID = new ItemID();
        this.createSupplierPartIdNode(itemID, lineItemData);
        itemOut.setItemID(itemID);
        return this;
    }

    protected BaseCXMLOrderRequest createSupplierPartIdNode(ItemID itemID, Map<String, Object> lineItemData) {
        SupplierPartID supplierPartID = new SupplierPartID();
        supplierPartID.setContent((String)lineItemData.get("supplierPartId"));
        itemID.setSupplierPartID(supplierPartID);
        return this;
    }

    protected BaseCXMLOrderRequest createSupplierPartAuxiliaryIdNode(ItemID itemID, Map<String, Object> lineItemData) {
        SupplierPartAuxiliaryID supplierPartAuxiliaryID = new SupplierPartAuxiliaryID();
        //TODO
        itemID.setSupplierPartAuxiliaryID(supplierPartAuxiliaryID);
        return this;
    }

    protected BaseCXMLOrderRequest createItemDetailNode(ItemOut itemOut, Map<String, Object> lineItemData) {
        ItemDetail itemDetail = new ItemDetail();
        this.createUnitPriceNode(itemDetail, lineItemData)
            .createDescriptionNode(itemDetail, lineItemData)
            .createUnitOfMeasureNode(itemDetail, lineItemData)
            .createClassificationNode(itemDetail, lineItemData)
            .createURLNode(itemDetail, lineItemData)
            .createExtrinsicNode(itemDetail, "MaterialCode", (String)lineItemData.get("materialCode"))
            .createExtrinsicNode(itemDetail, "Dimensions", (String)lineItemData.get("dimensions"))
            .createExtrinsicNode(itemDetail, "PDFDimensions", (String)lineItemData.get("pdfDimensions"))
            .createExtrinsicNode(itemDetail, "Color", (String)lineItemData.get("color"))
            .createExtrinsicNode(itemDetail, "Ship In Name Of", isBlindShip ? "None" : "Customer")
            .createExtrinsicNode(itemDetail, "rush", (String)lineItemData.get("rush"))
            .createExtrinsicNode(itemDetail, "requestedShipDate", (String)lineItemData.get("requestedShipDate"))
            .createExtrinsicNode(itemDetail, "stockLocation", (String)lineItemData.get("stockLocation"))
            .createExtrinsicNode(itemDetail, "specialCustomerRule", (String)lineItemData.get("specialCustomerRule"))
            .createExtrinsicNode(itemDetail, "customerDueDate", (String)lineItemData.get("customerDueDate"))
            .createExtrinsicNode(itemDetail, "variableDataUnits", (String)lineItemData.get("variableDataUnits"))
            .createExtrinsicNode(itemDetail, "requestedShipper", shippingMethod)
            .createExtrinsicNode(itemDetail, "requestedShippingAccount", vendor.getString("upsAccount"))
            .createColorExtrinsicNodes(itemDetail, lineItemData);
        itemOut.setItemDetail(itemDetail);
        return this;
    }

    protected BaseCXMLOrderRequest createColorExtrinsicNodes(ItemDetail itemDetail, Map<String, Object> lineItemData) {
        this.createExtrinsicNode(itemDetail, "Side 1 Inks", ((Integer) lineItemData.get("colorsFront")).toString());
        this.createExtrinsicNode(itemDetail, "Side 2 Inks", ((Integer) lineItemData.get("colorsBack")).toString());

        List<String> inkColorsFront = (List<String>)lineItemData.get("inkColorsFront");
        for(int i = 1; i <= inkColorsFront.size(); i ++) {
            this.createExtrinsicNode(itemDetail, "Side 1 Ink " + i, inkColorsFront.get(i - 1));
        }

        List<String> inkColorsBack = (List<String>)lineItemData.get("inkColorsBack");
        for(int i = 1; i <= inkColorsBack.size(); i ++) {
            this.createExtrinsicNode(itemDetail, "Side 2 Ink " + i, inkColorsBack.get(i - 1));
        }
        return this;
    }


    protected BaseCXMLOrderRequest createUnitPriceNode(ItemDetail itemDetail, Map<String, Object> lineItemData) {
        UnitPrice unitPrice = new UnitPrice();
        unitPrice.setMoney(createMoneyNode("USD", ((BigDecimal) lineItemData.get("cost")).toPlainString()));
        itemDetail.setUnitPrice(unitPrice);
        return this;
    }

    protected Money createMoneyNode(String currency, String content) {
        Money money = new Money();
        money.setCurrency(currency);
        money.setContent(content);
        return money;
    }

    protected BaseCXMLOrderRequest createURLNode(ItemDetail itemDetail, Map<String, Object> lineItemData) {
        itemDetail.setURL(createURLNode((String)lineItemData.get("url")));
        return this;
    }

    protected URL createURLNode(String content) {
        URL url = new URL();
        url.setContent(content);
        return url;
    }

    protected BaseCXMLOrderRequest createDescriptionNode(ItemDetail itemDetail, Map<String, Object> lineItemData) {
        Description description = new Description();
        description.setLang("en");
        description.getContent().add((String) lineItemData.get("description"));
        itemDetail.getDescription().add(description);
        return this;
    }

    protected BaseCXMLOrderRequest createUnitOfMeasureNode(ItemDetail itemDetail, Map<String, Object> lineItemData) {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setContent("LO");
        itemDetail.setUnitOfMeasure(unitOfMeasure);
        return this;
    }

    protected BaseCXMLOrderRequest createClassificationNode(ItemDetail itemDetail, Map<String, Object> lineItemData) {
        Classification classification = new Classification();
        classification.setDomain("Not Available");
        classification.setContent("Not Available");
        itemDetail.getClassification().add(classification);
        return this;
    }

    protected BaseCXMLOrderRequest createExtrinsicNode(ItemDetail itemDetail, String name, String content) {
        itemDetail.getExtrinsic().add(createExtrinsicNode(name, content));
        return this;
    }

    protected BaseCXMLOrderRequest createExtrinsicNode(OrderRequestHeader orderRequestHeader, String name, String content) {
        orderRequestHeader.getExtrinsic().add(createExtrinsicNode(name, content));
        return this;
    }

    protected Extrinsic createExtrinsicNode(String name, String content) {
        Extrinsic extrinsic = new Extrinsic();
        extrinsic.setName(name);
        extrinsic.getContent().add(content);
        return extrinsic;
    }


    private Map<String, Object> getLineItemData(GenericValue orderItem) {
        Map<String, Object> lineItemData = new HashMap<>();
        try {
            GenericValue product = EntityQuery.use(this.delegator).from("Product").where("productId", orderItem.getString("productId")).queryOne();
            GenericValue productStock = EntityQuery.use(this.delegator).from("ProductStock").where("productId", orderItem.getString("productId")).cache(false).queryOne();
            GenericValue vendorProduct = CXMLHelper.getVendorProduct(this.delegator, orderItem.getString("productId"), this.vendor.getString("partyId"));
            GenericValue orderItemArtwork = OrderHelper.getOrderItemArtwork(this.delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId"));
            Map<String, String> orderItemAttribute = OrderHelper.getOrderItemAttributeMap(this.delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId")) ;
            Map<String, String> prodFeatures = ProductHelper.getProductFeatures(delegator, product, UtilMisc.toList("COLOR"), true);

            String supplierPartId = (UtilValidate.isNotEmpty(vendorProduct.getString("vendorParentProductId"))) ? vendorProduct.getString("vendorParentProductId") : vendorProduct.getString("vendorProductId");
            String vendorProductId = (UtilValidate.isNotEmpty(vendorProduct.getString("vendorParentProductId"))) ? vendorProduct.getString("vendorProductId") : null;
            BigDecimal cost = new BigDecimal((String) context.get("cost"));
            String pdfURL = CXMLHelper.getPDFUrl(this.delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId"), "_WORKER");
            String pdfPath = CXMLHelper.getPDFPath(this.delegator, orderItem.getString("orderId"), orderItem.getString("orderItemSeqId"), "_WORKER");
            String dimensions = ProductHelper.getSize(this.delegator, orderItem.getString("productId"));
            String pdfDimensions = CXMLHelper.getPDFDimension(pdfPath, dimensions);
            boolean isRush = OrderHelper.isRush(this.delegator, this.orh.getOrderId(), orderItem.getString("orderItemSeqId"), orderItem);
            String requestedShipDate = isRush ? EnvConstantsUtil.yyyyMMddDash.format(EnvUtil.getNDaysBeforeOrAfterDate(orderItem.getTimestamp("dueDate"), -1, false, true)) : "";
            String customerDueDate = UtilValidate.isNotEmpty(orderItem.get("dueDate")) ? EnvConstantsUtil.yyyyMMddDash.format(orderItem.getTimestamp("dueDate")) : "";
            boolean stockSupplied = vendorProduct != null && "Y".equalsIgnoreCase(vendorProduct.getString("stockSupplied"));
            String variableDataUnits = orderItemAttribute.getOrDefault("addresses", "0");
            String stockLocation = stockSupplied && productStock != null && "B".equalsIgnoreCase(productStock.getString("location")) ? productStock.getString("location") : "";
            String color = UtilValidate.isNotEmpty(prodFeatures) && UtilValidate.isNotEmpty(prodFeatures.get("Color")) ? (String) prodFeatures.get("Color") : "Not Available";
            String lineNumber = EnvUtil.removeChar("0", orderItem.getString("orderItemSeqId"), true, false, false);
            String comments = (String) this.context.get("comments");
            comments = UtilValidate.isNotEmpty(comments) ? comments.replaceAll("\\r\\n|\\r|\\n", " ") : "";

            Integer colorsFront = Integer.valueOf(UtilValidate.isNotEmpty(orderItemAttribute.get("colorsFront")) ? orderItemAttribute.get("colorsFront") : "0");
            Integer colorsBack = Integer.valueOf(UtilValidate.isNotEmpty(orderItemAttribute.get("colorsBack")) ? orderItemAttribute.get("colorsBack") : "0");

            List<String> inkColorsFront = new ArrayList<>();
            List<String> inkColorsBack = new ArrayList<>();

            for(int i = 1; i <= 4; i ++) {
                String inkColorFront = orderItemArtwork.getString("frontInkColor" + i);
                String inkColorBack = orderItemArtwork.getString("backInkColor" + i);
                if(UtilValidate.isNotEmpty(inkColorFront)) {
                    inkColorsFront.add(inkColorFront);
                }

                if(UtilValidate.isNotEmpty(inkColorBack)) {
                    inkColorsBack.add(inkColorBack);
                }
            }


            lineItemData.put("comments", comments);
            lineItemData.put("lineNumber", lineNumber);
            lineItemData.put("quantity", orderItem.getBigDecimal("quantity").toString());
            lineItemData.put("supplierPartId", supplierPartId);
            lineItemData.put("cost", cost);
            lineItemData.put("description", orderItem.getString("itemDescription"));
            lineItemData.put("url", pdfURL.replaceAll("https://www.envelopes.com", rootURL));
            lineItemData.put("vendorProductId", vendorProductId);
            lineItemData.put("dimensions", dimensions);
            lineItemData.put("pdfDimensions", pdfDimensions);
            lineItemData.put("color", color);
            lineItemData.put("rush", isRush ? "true" : "");
            lineItemData.put("stockLocation", stockLocation);
            lineItemData.put("requestedShipDate", requestedShipDate);
            lineItemData.put("customerDueDate", customerDueDate);
            lineItemData.put("variableDataUnits", variableDataUnits);
            lineItemData.put("colorsFront", colorsFront);
            lineItemData.put("inkColorsFront", inkColorsFront);
            lineItemData.put("colorsBack", colorsBack);
            lineItemData.put("inkColorsBack", inkColorsBack);
            lineItemData.put("shippingMethod", inkColorsBack);


            applyCustomRules(lineItemData);


        } catch(Exception e) {
            EnvUtil.reportError(e);
        }
        return lineItemData;
    }

    protected void applyCustomRules(Map<String, Object> lineItemData) {
        String specialCustomerRule = "2053030".equalsIgnoreCase(this.orh.getPlacingParty().getString("partyId")) || "2162652".equalsIgnoreCase(this.orh.getPlacingParty().getString("partyId")) ? "CIMPRESS" : "";
        lineItemData.put("specialCustomerRule", specialCustomerRule);

        String materialCode = UtilValidate.isNotEmpty(lineItemData.get("vendorProductId")) ? (String)lineItemData.get("vendorProductId") : "";
        lineItemData.put("materialCode", materialCode);
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

    public List<GenericValue> getOrderItems() {
        return orderItems;
    }

    public String toString() {
        try
        {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(CXML.class);

            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty("jaxb.encoding", "ISO-8859-1");
//            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

            // output pretty printed
            try {
                jaxbMarshaller.setProperty("com.sun.xml.bind.xmlHeaders", "<!DOCTYPE cXML SYSTEM  \"http://xml.cxml.org/schemas/cXML/1.2.029/cXML.dtd\">\n");
            } catch (PropertyException ignore) {
               // Ignore
            }

            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //Print XML String to Console
            StringWriter sw = new StringWriter();

            //Write XML to StringWriter
            jaxbMarshaller.marshal(cxml, sw);

            //Verify XML Content
            return sw.toString().replaceAll("<cxml", "<cXML").replaceAll("</cxml", "</cXML"); //TODO - fix the issue with the lowercase root node

        } catch (JAXBException e) {
            EnvUtil.reportError(e);
        }
        return "";
    }
}
