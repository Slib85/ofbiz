/*******************************************************************************
 * BigName Commerce LLC
 *******************************************************************************/
package com.bigname.tax;

import net.avalara.avatax.rest.client.AvaTaxClient;
import net.avalara.avatax.rest.client.enums.AvaTaxEnvironment;
import net.avalara.avatax.rest.client.enums.DocumentType;
import net.avalara.avatax.rest.client.models.*;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartItem;
import org.apache.ofbiz.service.LocalDispatcher;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AvalaraTax {
    public static final String module = AvalaraTax.class.getName();

    private LocalDispatcher dispatcher = null;
    private Delegator delegator = null;
    private String customer = null;
    private String address1 = null;
    private String address2 = null;
    private String city = null;
    private String stateProvinceGeoId = null;
    private String postalCode = null;
    private String countryGeoId = null;
    private List<Map<String, Object>> itemList = new ArrayList<>();
    private BigDecimal discount = BigDecimal.ZERO;
    private AvaTaxClient client = null;

    private String DEFAULT_PRODUCT_TAX_CODE = "P0000000";
    private String DEFAULT_SHIPPING_TAX_CODE = "FR020100";

    private BigDecimal taxTotal = BigDecimal.ZERO; //tax total

    public AvalaraTax(LocalDispatcher dispatcher, Delegator delegator) {
        this.dispatcher = dispatcher;
        this.delegator = delegator;
    }

    public void getClient() {
        this.createClient();
    }

    public void setItems(List<Map<String, Object>> items) {
        this.itemList = items;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setAddress(Map<String, Object> addr) {
        this.address1 = (String) addr.get("address1");
        this.address2 = (String) addr.get("address2");
        this.city = (String) addr.get("city");
        this.stateProvinceGeoId = (String) addr.get("stateProvinceGeoId");
        this.postalCode = (String) addr.get("postalCode");
        this.countryGeoId = (String) addr.get("countryGeoId");
    }

    public void setAddress1(String str) {
        this.address1 = str;
    }

    public void setAddress2(String str) {
        this.address2 = str;
    }

    public void setCity(String str) {
        this.city = str;
    }

    public void setStateProvinceGeoId(String str) {
        this.stateProvinceGeoId = str;
    }

    public void setPostalCode(String str) {
        this.postalCode = str;
    }

    public void setCountryGeoId(String str) {
        this.countryGeoId = str;
    }

    public void setDiscount(BigDecimal amount) {
        this.discount = amount;
    }

    public BigDecimal getTotalTax() {
        return this.taxTotal;
    }

    /**
     * Create teh AvaTaxClient
     */
    private void createClient() {
        // create the client
        this.client = new AvaTaxClient(AvalaraTaxHelper.APP_NAME, AvalaraTaxHelper.APP_VERSION, AvalaraTaxHelper.MACHINE_NAME, (AvalaraTaxHelper.SANDBOX) ? AvaTaxEnvironment.Sandbox : AvaTaxEnvironment.Production);
        this.client.withSecurity(AvalaraTaxHelper.ACCOUNT_ID, AvalaraTaxHelper.LICENSE_KEY);
    }

    /**
     * Retrieve geolocation information for a specified address
     *
     * Resolve an address against Avalara's address-validation system.  If the address can be resolved, this API
     * provides the latitude and longitude of the resolved location.  The value 'resolutionQuality' can be used
     * to identify how closely this address can be located.  If the address cannot be clearly located, use the
     * 'messages' structure to learn more about problems with this address.
     * This is the same API as the POST /api/v2/addresses/resolve endpoint.
     * @return
     * @throws Exception
     */
    public AddressResolutionModel resolveAddres() throws Exception {
        AddressResolutionModel addressResolution = this.client.resolveAddress(this.address1, this.address2, null, this.city, this.stateProvinceGeoId, this.postalCode, this.countryGeoId, null, null, null);

        return addressResolution;
    }

    /**
     * Send the request to get the tax rate/amount details
     * @throws Exception
     */
    public void sendTaxTransaction() throws Exception {
        //create the client
        createClient();

        //get the result
        TransactionModel model = this.client.createTransaction(null, createTransaction());
        this.taxTotal = model.getTotalTax();
    }

    /**
     * Create the transaction to get tax rate against
     * @return CreateTransactionModel
     * @throws GenericEntityException
     */
    public CreateTransactionModel createTransaction() throws GenericEntityException {
        CreateTransactionModel transaction = new CreateTransactionModel();
        transaction.setType(DocumentType.SalesOrder);
        transaction.setCode("Get");
        transaction.setDate(UtilDateTime.nowDate());
        transaction.setCustomerCode((this.customer != null) ? this.customer : "0");

        //add the line items (items and shipping)
        ArrayList<LineItemModel> items = createLineItemModel();
        transaction.setLines(items);
        transaction.setDiscount(this.discount);

        //add addresses
        transaction.setAddresses(createAddressModel());

        return transaction;
    }

    /**
     * Add items from cart/order to request
     * @return ArrayList<LineItemModel>
     */
    private ArrayList<LineItemModel> createLineItemModel() {
        ArrayList<LineItemModel> itemModels = new ArrayList<>();

        for(Map<String, Object> item : this.itemList) {
            if((Boolean) item.get("isProduct")) {
                LineItemModel itemModel = new LineItemModel();
                itemModel.setItemCode((String) item.get("productId"));
                itemModel.setDescription((String) item.get("description"));
                itemModel.setAmount((BigDecimal) item.get("price"));
                itemModel.setQuantity((BigDecimal) item.get("quantity"));
                itemModel.setDiscounted(true); // all items are discountable
                itemModel.setTaxCode(UtilValidate.isNotEmpty((String) item.get("taxCode")) ? (String) item.get("taxCode") : DEFAULT_PRODUCT_TAX_CODE);
                itemModels.add(itemModel);
            } else if((Boolean) item.get("isShipping")) {
                itemModels.add(this.createShipItem(item));
            }
        }

        return itemModels;
    }

    /**
     * Add shipping as an item to request
     * @param item
     * @return LineItemModel
     */
    private LineItemModel createShipItem(Map<String, Object> item) {
        LineItemModel itemModel = new LineItemModel();
        itemModel.setItemCode((String) item.get("productId"));
        itemModel.setDescription((String) item.get("description"));
        itemModel.setAmount((BigDecimal) item.get("price"));
        itemModel.setQuantity((BigDecimal) item.get("quantity"));
        itemModel.setDiscounted(false); // if shipping discount is applied, set to true
        itemModel.setTaxCode(UtilValidate.isNotEmpty((String) item.get("taxCode")) ? (String) item.get("taxCode") : DEFAULT_SHIPPING_TAX_CODE);

        return itemModel;
    }

    /**
     * Create the address model that holds shipFrom and shipTo
     * @return AddressesModel
     * @throws GenericEntityException
     */
    private AddressesModel createAddressModel() throws GenericEntityException {
        AddressesModel addresses = new AddressesModel();
        addresses.setShipFrom(createShipFrom());
        addresses.setShipTo(createShipTo());

        return addresses;
    }

    /**
     * Create the ship from address for calculating the tax
     * @return AddressLocationInfo
     */
    private AddressLocationInfo createShipFrom() {
        AddressLocationInfo address = new AddressLocationInfo();
        address.setLine1("5300 New Horizons Blvd");
        address.setCity("Amityville");
        address.setRegion("NY");
        address.setPostalCode("11701");
        address.setCountry("US");

        return address;
    }

    /**
     * Create the ship to address for calculating the tax
     * @return AddressLocationInfo
     * @throws GenericEntityException
     */
    private AddressLocationInfo createShipTo() throws GenericEntityException {
        AddressLocationInfo address = new AddressLocationInfo();
        address.setLine1(this.address1);
        address.setLine2(this.address2);
        address.setCity(this.city);
        address.setRegion(this.stateProvinceGeoId);
        address.setPostalCode(this.postalCode);

        //country needs 2char ISO code
        GenericValue country = EntityQuery.use(this.delegator).from("Geo").where("geoId", this.countryGeoId).cache().queryOne();
        if(country != null) {
            address.setCountry(country.getString("geoCode"));
        }

        return address;
    }
}