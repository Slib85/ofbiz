package com.bigname.integration.listrak;

import com.bigname.core.config.SiteConfig;
import com.bigname.quote.calculator.CalculatorHelper;
import com.envelopes.cxml.CXMLHelper;
import com.envelopes.email.EmailHelper;
import com.envelopes.order.OrderHelper;
import com.envelopes.party.PartyHelper;
import com.envelopes.product.ProductHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericDataSourceException;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.jdbc.SQLProcessor;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.entity.util.EntityUtilProperties;
import org.apache.ofbiz.order.order.OrderReadHelper;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

import static com.envelopes.cxml.CXMLHelper.getOutsourceArtworkEmailData;
import static com.envelopes.party.PartyHelper.TO_NAME;
import static com.envelopes.party.PartyHelper.splitAtFirstSpace;

public class ListrakHelper {
    public static final String module = ListrakHelper.class.getName();

    private static String getSalesRepInfoHTML(String email, String name, String phone) {
        String emailHTML = "";

        if (UtilValidate.isNotEmpty(name)) {
            emailHTML +=
                    "<tr>\n" +
                            "<td class=\"full-img pad-b-50\" style=\"font-family:verdana,geneva,sans-serif; font-weight: bold; font-size: 14px; padding-top: 5px;\">\n" +
                            name + "\n" +
                            "</td>\n" +
                            "</tr>\n";
        }

        if (UtilValidate.isNotEmpty(phone)) {
            emailHTML +=
                    "<tr>\n" +
                            "<td class=\"full-img pad-b-50\" style=\"font-family:verdana,geneva,sans-serif; font-weight: bold; font-size: 14px; padding-top: 10px;\">\n" +
                            "<span style=\"font-weight: bold;\">Call:</span> <a href=\"tel:" + phone + "\">" + phone + "</a>\n" +
                            "</td>\n" +
                            "</tr>";
        }

        if (UtilValidate.isNotEmpty(email)) {
            emailHTML +=
                    "<tr>\n" +
                            "<td class=\"full-img pad-b-50\" style=\"font-family:verdana,geneva,sans-serif; font-weight: bold; font-size: 14px; padding-top: 10px;\">\n" +
                            "<span style=\"font-weight: bold;\">Email:</span> <a href=\"mailto:" + email + "\">" + email + "</a>\n" +
                            "</td>\n" +
                            "</tr>\n";
        }

        if (UtilValidate.isNotEmpty(emailHTML)) {
            return
                    "<tr>\n" +
                            "<td class=\"full-img\" style=\"font-family:verdana,geneva,sans-serif; font-weight:bold; color:#00a3e4; padding:20px 0 10px;\">Sales Rep. Information</td>\n" +
                            "</tr>\n" + emailHTML;
        } else {
            return "";
        }
    }

    public static String paramsToUrlString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder str = new StringBuilder();

        Iterator paramsIter = params.keySet().iterator();
        while (paramsIter.hasNext()) {
            String param = (String) paramsIter.next();
            String paramValue = params.get(param);

            if (str.length() == 0) {
                str.append(param + "=" + paramValue);
            } else {
                str.append("&" + param + "=" + paramValue);
            }
        }

        return str.toString();
    }

    public static String paramsToJsonString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder str = new StringBuilder();

        Iterator paramsIter = params.keySet().iterator();
        while (paramsIter.hasNext()) {
            String param = (String) paramsIter.next();
            String paramValue = params.get(param).replaceAll("[™®©]", "");

            if (str.length() == 0) {
                str.append(param + ":" + paramValue);
            } else {
                str.append("," + param + ":" + paramValue);
            }
        }

        return "{" + str.toString() + "}";
    }

    public static String getToken() {
        try {
            URL url = new URL("https://auth.listrak.com/OAuth2/Token");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestMethod("POST");

            Map<String, String> urlParams = new HashMap<>();
            urlParams.put("grant_type", "client_credentials");
            urlParams.put("client_id", "tex60sgne8ggr1kshajf");
            urlParams.put("client_secret", "NtFQYF6Rse3CdDpkv8H2caP6YErvuo8qzSC0FJa2IUA");

            connection.setRequestProperty("Content-Length", Integer.toString(paramsToUrlString(urlParams).length()));
            connection.setDoOutput(true);
            connection.setDoInput(true);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(paramsToUrlString(urlParams));
            wr.flush();
            wr.close();

            InputStream input = connection.getInputStream();

            StringBuffer response = new StringBuffer();
            BufferedReader in = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            return (String) (new Gson().fromJson(response.toString(), HashMap.class)).get("access_token");
        } catch (Exception e) {
            Debug.logError("Error: " + e.getMessage(), module);
            return null;
        }
    }

    public static Map<String, Object> sendEmail(DispatchContext dctx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();

        try {
            sendEmail((String) context.get("listName"), (String) context.get("messageName"), (Map) context.get("data"), (String) context.get("email"), (String) context.get("webSiteId"));
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "There was an error while trying to send listrak email to: " + (String) context.get("email"), module);
        }

        return result;
    }

    public static boolean sendEmail(String listName, String messageName, Map<String, String> data, String email, String webSiteId) throws Exception {
        boolean success = false;

        String myToken = ListrakHelper.getToken();
        HttpURLConnection connection;

        try {
            if (UtilValidate.isNotEmpty(myToken)) {
                Map<String, String> urlParams = new HashMap<>();
                urlParams.put("\"emailAddress\"", "\"" + email + "\"");

                ArrayList segmentationFieldValueList = new ArrayList<>();
                HashMap<String, Object> segmentationFieldValueMap = new HashMap<>();

                for (Map.Entry<String, String> entry : data.entrySet()) {
                    if (UtilValidate.isNotEmpty(UtilProperties.getPropertyValue(webSiteId, webSiteId + ".listrak.fieldID." + entry.getKey())) && UtilValidate.isNotEmpty(entry.getValue())) {
                        segmentationFieldValueMap = new HashMap<>();
                        segmentationFieldValueMap.put("segmentationFieldId", Integer.parseInt(UtilProperties.getPropertyValue(webSiteId, webSiteId + ".listrak.fieldID." + entry.getKey())));
                        segmentationFieldValueMap.put("value", entry.getValue());
                        segmentationFieldValueList.add(segmentationFieldValueMap);
                    }
                }

                urlParams.put("\"segmentationFieldValues\"", new Gson().toJson(segmentationFieldValueList));

                URL url = new URL("https://api.listrak.com/email/v1/List/" + UtilProperties.getPropertyValue(webSiteId, webSiteId + ".listrak.listID." + listName) + "/TransactionalMessage/" + UtilProperties.getPropertyValue(webSiteId, webSiteId + ".listrak.messageID." + messageName) + "/Message");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Authorization", "Bearer " + myToken);
                connection.setRequestProperty("Content-Type", "application/json; charset=utf8");
                connection.setRequestProperty("Content-Length", Integer.toString(paramsToJsonString(urlParams).length()));
                connection.setDoOutput(true);
                connection.setDoInput(true);

                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(paramsToJsonString(urlParams));
                wr.flush();
                wr.close();

                InputStream input = connection.getInputStream();

                StringBuffer response = new StringBuffer();
                BufferedReader myIn = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
                String inputLine;

                while ((inputLine = myIn.readLine()) != null) {
                    response.append(inputLine);
                }

                myIn.close();
            }
        } catch (Exception e) {
            Debug.log(e.getMessage(), module);
        }

        return success;
    }

    /*
     * Create Order Confirmation Email Data for Listrak
     */
    public static Map<String, String> createOrderConfirmationEmailData(Delegator delegator, LocalDispatcher dispatcher, Map orderData, String orderId, String webSiteId) throws GenericEntityException {
        Map<String, String> data = new HashMap<String, String>();
        List<String> quoteAssignedToEmails = new ArrayList<>();

        try {
            data.put("OrderNumber", orderId);

            if (orderData == null) {
                orderData = OrderHelper.getOrderData(delegator, orderId, false);
            }

            List<Map> payments = (List<Map>) orderData.get("payments");
            String paymentMethodTypeId = null;
            String cardNumber = null;
            String cardType = null;
            boolean isOrderPrinted = OrderHelper.isOrderPrinted(delegator, orderId);

            for (Map payment : payments) {
                paymentMethodTypeId = (String) payment.get("paymentMethodTypeId");
                cardType = (String) payment.get("cardType");
                cardNumber = (String) payment.get("cardNumber");
            }

            //get the header message to display in the email
            SiteConfig siteConfig = new SiteConfig(webSiteId);
            siteConfig.getWebSite(delegator);

            if (isOrderPrinted) {
                if (paymentMethodTypeId.equalsIgnoreCase("EXT_NET30")) {
                    data.put("OrderHeaderMessage", "Your order <a href=\"https://" + siteConfig.webSiteUrl() + "/account\" style=\"color: #053959; font:bold 12px/17px Helvetica; text-decoration: underline;\" title=\"Order Number\">" + orderId + "</a> has been received. We will process the order right away and send you a shipping confirmation email soon. Thank you for shopping at " + siteConfig.webSiteName());
                } else if (OrderHelper.isOrderScene7(delegator, orderId)) {
                    if (paymentMethodTypeId.equalsIgnoreCase("CREDIT_CARD")) {
                        data.put("OrderHeaderMessage", "Your order <a href=\"https://" + siteConfig.webSiteUrl() + "/account\" style=\"color: #053959; font:bold 12px/17px Helvetica; text-decoration: underline;\" title=\"Order Number\">" + orderId + "</a> has been received and your credit card has been authorized. We'll get started on it right away and you'll receive a shipment notification once complete.  Thank you for shopping at " + siteConfig.webSiteName());
                    } else if (paymentMethodTypeId.equalsIgnoreCase("EXT_AMAZON") || paymentMethodTypeId.equalsIgnoreCase("EXT_PAYPAL_CHECKOUT")) {
                        data.put("OrderHeaderMessage", "Your order <a href=\"https://" + siteConfig.webSiteUrl() + "/account\" style=\"color: #053959; font:bold 12px/17px Helvetica; text-decoration: underline;\" title=\"Order Number\">" + orderId + "</a> has been received and your credit card has been charged. We'll get started on it right away and you'll receive a shipment notification once complete.  Thank you for shopping at " + siteConfig.webSiteName());
                    } else {
                        data.put("OrderHeaderMessage", "Your order <a href=\"https://" + siteConfig.webSiteUrl() + "/account\" style=\"color: #053959; font:bold 12px/17px Helvetica; text-decoration: underline;\" title=\"Order Number\">" + orderId + "</a> has been received and is awaiting check receipt.  Once payment has been accepted, we'll get started on it right away and you'll receive a shipment notification once complete.  Thank you for shopping at " + siteConfig.webSiteName());
                    }
                } else {
                    if (paymentMethodTypeId.equalsIgnoreCase("CREDIT_CARD")) {
                        data.put("OrderHeaderMessage", "Your order <a href=\"https://" + siteConfig.webSiteUrl() + "/account\" style=\"color: #053959; font:bold 12px/17px Helvetica; text-decoration: underline;\" title=\"Order Number\">" + orderId + "</a> has been received and your credit card has been authorized. We'll get started on it right away and will send you your proof within 1 business day. Once approved, production time begins and you'll receive a shipment notification once complete.  Thank you for shopping at " + siteConfig.webSiteName());
                    } else if (paymentMethodTypeId.equalsIgnoreCase("EXT_AMAZON") || paymentMethodTypeId.equalsIgnoreCase("EXT_PAYPAL_CHECKOUT")) {
                        data.put("OrderHeaderMessage", "Your order <a href=\"https://" + siteConfig.webSiteUrl() + "/account\" style=\"color: #053959; font:bold 12px/17px Helvetica; text-decoration: underline;\" title=\"Order Number\">" + orderId + "</a> has been received and your credit card has been charged. We'll get started on it right away and will send you your proof within 1 business day. Once approved, production time begins and you'll receive a shipment notification once complete.  Thank you for shopping at " + siteConfig.webSiteName());
                    } else {
                        data.put("OrderHeaderMessage", "Your order <a href=\"https://" + siteConfig.webSiteUrl() + "/account\" style=\"color: #053959; font:bold 12px/17px Helvetica; text-decoration: underline;\" title=\"Order Number\">" + orderId + "</a> has been received and is awaiting check receipt.  Once payment has been accepted we'll process the order and send you a shipping confirmation email soon.  Thank you for shopping at " + siteConfig.webSiteName());
                    }
                }
            } else {
                if (paymentMethodTypeId.equalsIgnoreCase("CREDIT_CARD")) {
                    data.put("OrderHeaderMessage", "Your order <a href=\"https://" + siteConfig.webSiteUrl() + "/account\" style=\"color: #053959; font:bold 12px/17px Helvetica; text-decoration: underline;\" title=\"Order Number\">" + orderId + "</a> has been received and your credit card has been authorized. We'll get started on it right away and will send you a shipping confirmation email soon. Thank you for shopping at " + siteConfig.webSiteName());
                } else if (paymentMethodTypeId.equalsIgnoreCase("EXT_AMAZON") || paymentMethodTypeId.equalsIgnoreCase("EXT_PAYPAL_CHECKOUT")) {
                    data.put("OrderHeaderMessage", "Your order <a href=\"https://" + siteConfig.webSiteUrl() + "/account\" style=\"color: #053959; font:bold 12px/17px Helvetica; text-decoration: underline;\" title=\"Order Number\">" + orderId + "</a> has been received and your credit card has been charged. We'll get started on it right away and will send you a shipping confirmation email soon. Thank you for shopping at " + siteConfig.webSiteName());
                } else if (paymentMethodTypeId.equalsIgnoreCase("PERSONAL_CHECK")) {
                    data.put("OrderHeaderMessage", "Your order <a href=\"https://" + siteConfig.webSiteUrl() + "/account\" style=\"color: #053959; font:bold 12px/17px Helvetica; text-decoration: underline;\" title=\"Order Number\">" + orderId + "</a> has been received and is awaiting check receipt.  Once payment has been accepted we'll process the order and send you a shipping confirmation email soon. Thank  you for shopping at " + siteConfig.webSiteName());
                } else {
                    data.put("OrderHeaderMessage", "Your order <a href=\"https://" + siteConfig.webSiteUrl() + "/account\" style=\"color: #053959; font:bold 12px/17px Helvetica; text-decoration: underline;\" title=\"Order Number\">" + orderId + "</a> has been received. We'll get started on it right away and will send you a shipping confirmation email soon. Thank you for shopping at " + siteConfig.webSiteName());
                }
            }

            //gather payment data ... this might not always be there.
            String paymentInformationHTML = "" +
                    "<tr>\n" +
                    "<td class=\"full-img\" style=\"font-family:verdana,geneva,sans-serif; font-weight:bold; color:#00a3e4; padding:20px 0 10px;\">Payment Information</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td class=\"full-img pad-b-50\" style=\"font-family:verdana,geneva,sans-serif; font-weight:normal; color:#7d7d7d;\">\n";

            if (paymentMethodTypeId.equalsIgnoreCase("CREDIT_CARD")) {
                paymentInformationHTML = paymentInformationHTML + "Your " + cardType + " " + cardNumber + " has been authorized.\n";
            } else if (paymentMethodTypeId.equalsIgnoreCase("EXT_AMAZON")) {
                paymentInformationHTML = paymentInformationHTML + "Your Amazon Order Reference " + cardType + " has been charged.\n";
            } else if (paymentMethodTypeId.equalsIgnoreCase("EXT_PAYPAL_CHECKOUT")) {
                paymentInformationHTML = paymentInformationHTML + "Your PayPal Order Reference " + cardType + " has been charged.\n";
            } else if (paymentMethodTypeId.equalsIgnoreCase("PERSONAL_CHECK")) {
                paymentInformationHTML = paymentInformationHTML + "Please mail your check to us as soon as possible and we will process your order. Our mailing address is:<br /><br /><a style=\"color:#00a3e4; text-decoration:none;\" title=\"Website\" href=\"https://www." + siteConfig.webSiteName() + "\">" + siteConfig.webSiteName() + "</a><br /><a style=\"color:#00a3e4; text-decoration:none;\" title=\"Map\" href=\"https://goo.gl/maps/cxsL8oQsZrKvHrfB6\">105 Maxess Road<br />Suite S215<br />Melville, NY 11747</a><br />Attn: Check Order\n";
            } else {
                paymentInformationHTML = "";
            }

            if (UtilValidate.isNotEmpty(paymentInformationHTML)) {
                data.put("PaymentInformationHTML", paymentInformationHTML +
                        "</td>\n" +
                        "</tr>\n"
                );
            }

            //gather order item data
            List<Map> items = (List<Map>) orderData.get("items");
            String cartHTML = "<!-- Cart -->\n" +
                    "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n" +
                    "<tr>\n" +
                    "<td align=\"center\" style=\"padding:20px 20px 0\">\n" +
                    "<!-- Cart Content -->\n" +
                    "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n";

            for (Map item : items) {
                GenericValue orderItem = (GenericValue) item.get("item");

                String productName = (EnvConstantsUtil.CUSTOM_PRODUCT.equalsIgnoreCase(orderItem.getString("productId")) || EnvConstantsUtil.SAMPLE_PRODUCT.equalsIgnoreCase(orderItem.getString("productId"))) ? orderItem.getString("itemDescription") : ((GenericValue) item.get("product")).getString("internalName");
                String productUrl = ProductHelper.getProductUrl(delegator, null, orderItem.getString("productId"), EnvUtil.getWebsiteId(((GenericValue) orderData.get("orderHeader")).getString("salesChannelEnumId")), true);
                BigDecimal subTotal = OrderReadHelper.getOrderItemSubTotal(orderItem, (List) orderData.get("adjustments"));

                cartHTML = cartHTML +
                        "<tr>\n" +
                        "<td style=\"border-bottom:1px solid #d9d9d9; padding:8px 0;\">\n" +
                        "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n" +
                        "<tr>\n" +
                        "<td class=\"pad-lr-10\" width=\"25%\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#7d7d7d; text-align:left; padding:0 20px;\">\n" +
                        "<a href=\"" + productUrl + "\" title=\"Product\" target=\"_blank\" style=\"color:#00a3e4; text-decoration:none;\"><img width=\"100%\" src=\"https://actionenvelope3.scene7.com/is/image/ActionEnvelope/" + orderItem.getString("productId") + "?wid=120&fmt=jpeg&qlt=75&bgc=ffffff\" alt=\"Product\" /></a>\n" +
                        "</td>\n" +
                        "<td width=\"75%\">\n" +
                        "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n" +
                        "<tr>\n" +
                        "<td width=\"130\" valign=\"middle\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#7d7d7d; text-align:left; padding:5px 0px 5px 0px;\">Product Name:</td>\n" +
                        "<td valign=\"middle\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#00a3e4; text-align:left; padding:5px 0px 5px 0px;\">" + productName + ((orderItem.getBigDecimal("quantity").compareTo(new BigDecimal("5")) <= 0) ? " (SAMPLE)" : "") + "</td>\n" +
                        "</tr>\n" +
                        "</table>\n";

                if (ProductHelper.getProductFeatures(delegator, (GenericValue) item.get("product"), UtilMisc.toList("COLOR")).size() != 0 && !ProductHelper.getProductFeatures(delegator, (GenericValue) item.get("product"), UtilMisc.toList("COLOR")).get("COLOR").equals("Custom Color")) {
                    cartHTML = cartHTML +
                            "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n" +
                            "<tr>\n" +
                            "<td width=\"130\" valign=\"middle\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#7d7d7d; text-align:left; padding:5px 0px 5px 0px;\">Color:</td>\n" +
                            "<td valign=\"middle\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#00a3e4; text-align:left; padding:5px 0px 5px 0px;\">" + ProductHelper.getProductFeatures(delegator, (GenericValue) item.get("product"), UtilMisc.toList("COLOR")).get("COLOR") + "</td>\n" +
                            "</tr>\n" +
                            "</table>\n";
                }

                cartHTML = cartHTML +
                        "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n" +
                        "<tr>\n" +
                        "<td width=\"130\" valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#7d7d7d; text-align:left; padding:5px 0px 5px 0px;\">Quantity:</td>\n" +
                        "<td valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#00a3e4; text-align:left; padding:5px 0px 5px 0px;\">" + EnvUtil.convertBigDecimal(orderItem.getBigDecimal("quantity"), true) + "</td>\n" +
                        "</tr>\n" +
                        "</table>\n" +
                        "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n" +
                        "<tr>\n" +
                        "<td width=\"130\" valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#7d7d7d; text-align:left; padding:5px 0px 5px 0px;\">Price:</td>\n" +
                        "<td valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#00a3e4; text-align:left; padding:5px 0px 5px 0px;\">$" + EnvUtil.convertBigDecimal(subTotal, false) + "</td>\n" +
                        "</tr>\n" +
                        "</table>\n";

                if (UtilValidate.isNotEmpty(orderItem.get("artworkSource"))) {
                    List attributes = (List) item.get("attribute");

                    String colorsFrontHTML = "";
                    String colorsBackHTML = "";

                    for (Object attributeObj : attributes) {
                        GenericValue attribute = (GenericValue) attributeObj;
                        if (attribute.getString("attrName").equals("colorsFront")) {
                            colorsFrontHTML = "" +
                                    "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n" +
                                    "<tr>\n" +
                                    "<td width=\"130\" valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#7d7d7d; text-align:left; padding:5px 0px 5px 0px;\">Ink Colors (front):</td>\n" +
                                    "<td valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#00a3e4; text-align:left; padding:5px 0px 5px 0px;\">" + ((UtilValidate.isNotEmpty(!attribute.getString("attrValue").equals("0"))) ? attribute.getString("attrValue") : "&nbsp;") + "</td>\n" +
                                    "</tr>\n" +
                                    "</table>\n";
                        } else if (attribute.getString("attrName").equals("colorsBack")) {
                            colorsBackHTML = "" +
                                    "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n" +
                                    "<tr>\n" +
                                    "<td width=\"130\" valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#7d7d7d; text-align:left; padding:5px 0px 5px 0px;\">Ink Colors (back):</td>\n" +
                                    "<td valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#00a3e4; text-align:left; padding:5px 0px 5px 0px;\">" + ((UtilValidate.isNotEmpty(!attribute.getString("attrValue").equals("0"))) ? attribute.getString("attrValue") : "&nbsp;") + "</td>\n" +
                                    "</tr>\n" +
                                    "</table>\n";
                        }
                    }

                    cartHTML = cartHTML + colorsFrontHTML + colorsBackHTML +
                            "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n" +
                            "<tr>\n" +
                            "<td width=\"130\" valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#7d7d7d; text-align:left; padding:5px 0px 5px 0px;\">Artwork:</td>\n" +
                            "<td valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#00a3e4; text-align:left; padding:5px 0px 5px 0px;\">" + EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_PRINT_TYPES.get(orderItem.getString("artworkSource")) + "</td>\n" +
                            "</tr>\n" +
                            "</table>\n";
                }

                if (UtilValidate.isNotEmpty(orderItem.getString("comments"))) {
                    cartHTML = cartHTML +
                            "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n" +
                            "<tr>\n" +
                            "<td width=\"130\" valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#7d7d7d; text-align:left; padding:5px 0px 5px 0px;\">Artwork:</td>\n" +
                            "<td valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#00a3e4; text-align:left; padding:5px 0px 5px 0px;\">" + orderItem.getString("comments") + "</td>\n" +
                            "</tr>\n" +
                            "</table>\n";
                }

                if (UtilValidate.isNotEmpty(orderItem.get("referenceQuoteId"))) {
                    GenericValue quoteInfo = EntityQuery.use(delegator).from("CustomEnvelope").where("quoteId", orderItem.get("referenceQuoteId")).queryOne();
                    if (UtilValidate.isNotEmpty(quoteInfo) && UtilValidate.isNotEmpty(quoteInfo.get("assignedTo")) && !quoteAssignedToEmails.contains((String) quoteInfo.get("assignedTo"))) {
                        quoteAssignedToEmails.add((String) quoteInfo.get("assignedTo"));
                    }
                }

                //data.put("itemArtStatus_" + i, (delegator.findOne("StatusItem", UtilMisc.toMap("statusId", orderItem.getString("statusId")), true)).getString("description"));

                cartHTML = cartHTML +
                        "</td>\n" +
                        "</tr>\n" +
                        "</table>\n" +
                        "</td>\n" +
                        "</tr>\n";
            }

            cartHTML = cartHTML +
                    "</table>\n" +
                    "<!-- Cart Content END -->\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "<!-- Cart END-->";

            data.put("OrderCartHTML", cartHTML);

            // not used for envelopes?
            data.put("quoteAssignedToEmails", String.join(",", quoteAssignedToEmails));

            List<Map> orderTotals = OrderHelper.getOrderTotals(delegator, orderId);
            String adjustmentHTML = "";

            for (Map adj : orderTotals) {
                for (Object _entry : adj.entrySet()) {
                    Map.Entry entry = (Map.Entry) _entry;
                    if (((String) entry.getKey()).equals("Refunds") || ((String) entry.getKey()).equals("Rate")) {
                        continue; //we dont want refunds to show in order confirmation
                    } else if (((String) entry.getKey()).equals("Subtotal")) {
                        data.put("SubTotal", EnvUtil.convertBigDecimal((BigDecimal) entry.getValue(), false));
                    } else if (((String) entry.getKey()).equals("Tax")) {
                        data.put("Tax", EnvUtil.convertBigDecimal((BigDecimal) entry.getValue(), false));
                    } else if (((String) entry.getKey()).equals("Shipping")) {
                        data.put("ShippingCost", EnvUtil.convertBigDecimal((BigDecimal) entry.getValue(), false));
                    } else {
                        adjustmentHTML = adjustmentHTML +
                                "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n" +
                                "<tr>\n" +
                                "<td class=\"pad-lr-20\" align=\"center\" style=\"padding:5px 40px;\">\n" +
                                "<!-- Shipping -->\n" +
                                "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n" +
                                "<tbody>\n" +
                                "<tr>\n" +
                                "<td class=\"copy-18\" style=\"text-align:right; font-family:verdana,geneva,sans-serif; font-size:13px; \">\n" +
                                "<table class=\"half-width left\" width=\"380\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; width:380px\">\n" +
                                "<tbody>\n" +
                                "<tr>\n" +
                                "<td class=\"full-img left\" style=\"font-family:verdana,geneva,sans-serif; text-align:right;\">" + (String) entry.getKey() + ":</td>\n" +
                                "</tr>\n" +
                                "</tbody>\n" +
                                "</table>\n" +
                                "<table class=\"half-width right\" width=\"120\" align=\"right\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; width:120px;\">\n" +
                                "<tbody>\n" +
                                "<tr>\n" +
                                "<td class=\"full-img\" style=\"font-family:verdana,geneva,sans-serif; color:#7d7d7d; text-align:right;\">($" + EnvUtil.convertBigDecimal((BigDecimal) entry.getValue(), false) + ")</td>\n" +
                                "</tr>\n" +
                                "</tbody>\n" +
                                "</table>\n" +
                                "</td>\n" +
                                "</tr>\n" +
                                "</tbody>\n" +
                                "</table>\n" +
                                "<!-- Shipping END -->\n" +
                                "</td>\n" +
                                "</tr>" +
                                "</table>";
                    }
                }
            }

            data.put("AdjustmentHTML", adjustmentHTML);
            data.put("GrandTotal", EnvUtil.convertBigDecimal(((GenericValue) orderData.get("orderHeader")).getBigDecimal("grandTotal"), false));
            data.put("ShippingMethod", (String) orderData.get("shipping"));

            GenericValue shippingAddress = (GenericValue) orderData.get("shippingAddress");
            data.put("ShippingFullName", shippingAddress.getString("toName"));
            data.put("ShippingCompanyName", shippingAddress.getString("companyName"));
            data.put("ShippingStreetAddress1", shippingAddress.getString("address1"));
            data.put("ShippingStreetAddress2", shippingAddress.getString("address2"));
            data.put("ShippingCity", shippingAddress.getString("city"));
            data.put("ShippingState", shippingAddress.getString("stateProvinceGeoId"));
            data.put("ShippingZip", shippingAddress.getString("postalCode"));

            GenericValue billingAddress = (GenericValue) orderData.get("billingAddress");
            data.put("BillingFullName", billingAddress.getString("toName"));
            data.put("BillingCompanyName", billingAddress.getString("companyName"));
            data.put("BillingStreetAddress1", billingAddress.getString("address1"));
            data.put("BillingStreetAddress2", billingAddress.getString("address2"));
            data.put("BillingCity", billingAddress.getString("city"));
            data.put("BillingState", billingAddress.getString("stateProvinceGeoId"));
            data.put("BillingZip", billingAddress.getString("postalCode"));

            data.put("ContactSalesPersonHTML", getSalesRepInfoHTML(((HashMap<String, String>) orderData.get("salesRep")).get("email"), ((HashMap<String, String>) orderData.get("salesRep")).get("name"), ((HashMap<String, String>) orderData.get("salesRep")).get("phone")));
        } catch (Exception e) {
            EnvUtil.reportError(e);
        }

        return data;
    }

    public static Map<String, String> createItemShippedEmailData(Delegator delegator, Map orderData, String orderId, String orderItemSeqId, String webSiteId) throws GenericEntityException {
        Map<String, String> data = new HashMap<>();

        try {
            if (orderData == null) {
                orderData = OrderHelper.getOrderData(delegator, orderId, false);
            }

            GenericValue shippingAddressData = (GenericValue) orderData.get("shippingAddress");
            GenericValue billingAddressData = (GenericValue) orderData.get("billingAddress");

            data.put("OrderNumber", orderId);
            data.put("BillingFullName", splitAtFirstSpace(billingAddressData.getString(TO_NAME))[0] + " " + splitAtFirstSpace(billingAddressData.getString(TO_NAME))[1]);
            data.put("EmailAddress", (String) orderData.get("email"));
            data.put("foundTrackingNumber", Boolean.toString(false));

            List<Map> items = (List<Map>) orderData.get("items");

            //gather order item data
            String cartHTML =
                    "<!-- Cart -->\n" +
                            "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n" +
                            "<tr>\n" +
                            "<td align=\"center\" style=\"padding:20px 20px 0\">\n" +
                            "<!-- Cart Content -->\n" +
                            "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n";


            for (Map item : items) {
                GenericValue orderItem = (GenericValue) item.get("item");

                if (UtilValidate.isNotEmpty(orderItemSeqId) && !orderItemSeqId.equalsIgnoreCase(orderItem.getString("orderItemSeqId"))) {
                    continue;
                }

                if ("ITEM_SHIPPED".equalsIgnoreCase(orderItem.getString("statusId"))) {
                    String productName = (EnvConstantsUtil.CUSTOM_PRODUCT.equalsIgnoreCase(orderItem.getString("productId")) || EnvConstantsUtil.SAMPLE_PRODUCT.equalsIgnoreCase(orderItem.getString("productId"))) ? orderItem.getString("itemDescription") : ((GenericValue) item.get("product")).getString("internalName");
                    String productUrl = ProductHelper.getProductUrl(delegator, null, orderItem.getString("productId"), EnvUtil.getWebsiteId(((GenericValue) orderData.get("orderHeader")).getString("salesChannelEnumId")), true);
                    BigDecimal subTotal = OrderReadHelper.getOrderItemSubTotal(orderItem, (List) orderData.get("adjustments"));

                    cartHTML = cartHTML +
                            "<tr>\n" +
                            "<td style=\"border-bottom:1px solid #d9d9d9; padding:8px 0;\">\n" +
                            "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n" +
                            "<tr>\n" +
                            "<td class=\"pad-lr-10\" width=\"25%\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#7d7d7d; text-align:left; padding:0 20px;\">\n" +
                            "<a href=\"" + productUrl + "\" title=\"Product\" target=\"_blank\" style=\"color:#00a3e4; text-decoration:none;\"><img width=\"100%\" src=\"https://actionenvelope3.scene7.com/is/image/ActionEnvelope/" + orderItem.getString("productId") + "?wid=120&fmt=jpeg&qlt=75&bgc=ffffff\" alt=\"Product\" /></a>\n" +
                            "</td>\n" +
                            "<td width=\"75%\">\n" +
                            "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n" +
                            "<tr>\n" +
                            "<td width=\"130\" valign=\"middle\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#7d7d7d; text-align:left; padding:5px 0px 5px 0px;\">Product Name:</td>\n" +
                            "<td valign=\"middle\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#00a3e4; text-align:left; padding:5px 0px 5px 0px;\">" + productName + ((orderItem.getBigDecimal("quantity").compareTo(new BigDecimal("5")) <= 0) ? " (SAMPLE)" : "") + "</td>\n" +
                            "</tr>\n" +
                            "</table>\n";

                    if (ProductHelper.getProductFeatures(delegator, (GenericValue) item.get("product"), UtilMisc.toList("COLOR")).size() != 0 && !ProductHelper.getProductFeatures(delegator, (GenericValue) item.get("product"), UtilMisc.toList("COLOR")).get("COLOR").equals("Custom Color")) {
                        cartHTML = cartHTML +
                                "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n" +
                                "<tr>\n" +
                                "<td width=\"130\" valign=\"middle\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#7d7d7d; text-align:left; padding:5px 0px 5px 0px;\">Color:</td>\n" +
                                "<td valign=\"middle\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#00a3e4; text-align:left; padding:5px 0px 5px 0px;\">" + ProductHelper.getProductFeatures(delegator, (GenericValue) item.get("product"), UtilMisc.toList("COLOR")).get("COLOR") + "</td>\n" +
                                "</tr>\n" +
                                "</table>\n";
                    }

                    cartHTML = cartHTML +
                            "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n" +
                            "<tr>\n" +
                            "<td width=\"130\" valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#7d7d7d; text-align:left; padding:5px 0px 5px 0px;\">Quantity:</td>\n" +
                            "<td valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#00a3e4; text-align:left; padding:5px 0px 5px 0px;\">" + EnvUtil.convertBigDecimal(orderItem.getBigDecimal("quantity"), true) + "</td>\n" +
                            "</tr>\n" +
                            "</table>\n" +
                            "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n" +
                            "<tr>\n" +
                            "<td width=\"130\" valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#7d7d7d; text-align:left; padding:5px 0px 5px 0px;\">Price:</td>\n" +
                            "<td valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#00a3e4; text-align:left; padding:5px 0px 5px 0px;\">$" + EnvUtil.convertBigDecimal(subTotal, false) + "</td>\n" +
                            "</tr>\n" +
                            "</table>\n";

                    if (UtilValidate.isNotEmpty(orderItem.get("artworkSource"))) {
                        List attributes = (List) item.get("attribute");

                        String colorsFrontHTML = "";
                        String colorsBackHTML = "";

                        for (Object attributeObj : attributes) {
                            GenericValue attribute = (GenericValue) attributeObj;
                            if (attribute.getString("attrName").equals("colorsFront")) {
                                colorsFrontHTML = "" +
                                        "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n" +
                                        "<tr>\n" +
                                        "<td width=\"130\" valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#7d7d7d; text-align:left; padding:5px 0px 5px 0px;\">Ink Colors (front):</td>\n" +
                                        "<td valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#00a3e4; text-align:left; padding:5px 0px 5px 0px;\">" + ((UtilValidate.isNotEmpty(!attribute.getString("attrValue").equals("0"))) ? attribute.getString("attrValue") : "&nbsp;") + "</td>\n" +
                                        "</tr>\n" +
                                        "</table>\n";
                            } else if (attribute.getString("attrName").equals("colorsBack")) {
                                colorsBackHTML = "" +
                                        "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n" +
                                        "<tr>\n" +
                                        "<td width=\"130\" valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#7d7d7d; text-align:left; padding:5px 0px 5px 0px;\">Ink Colors (back):</td>\n" +
                                        "<td valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#00a3e4; text-align:left; padding:5px 0px 5px 0px;\">" + ((UtilValidate.isNotEmpty(!attribute.getString("attrValue").equals("0"))) ? attribute.getString("attrValue") : "&nbsp;") + "</td>\n" +
                                        "</tr>\n" +
                                        "</table>\n";
                            }
                        }

                        cartHTML = cartHTML + colorsFrontHTML + colorsBackHTML +
                                "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n" +
                                "<tr>\n" +
                                "<td width=\"130\" valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#7d7d7d; text-align:left; padding:5px 0px 5px 0px;\">Artwork:</td>\n" +
                                "<td valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#00a3e4; text-align:left; padding:5px 0px 5px 0px;\">" + EnvConstantsUtil.SITE_CONFIGS.get(webSiteId).BRONTO_PRINT_TYPES.get(orderItem.getString("artworkSource")) + "</td>\n" +
                                "</tr>\n" +
                                "</table>\n";
                    }

                    if (UtilValidate.isNotEmpty(orderItem.getString("comments"))) {
                        cartHTML = cartHTML +
                                "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n" +
                                "<tr>\n" +
                                "<td width=\"130\" valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#7d7d7d; text-align:left; padding:5px 0px 5px 0px;\">Artwork:</td>\n" +
                                "<td valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#00a3e4; text-align:left; padding:5px 0px 5px 0px;\">" + orderItem.getString("comments") + "</td>\n" +
                                "</tr>\n" +
                                "</table>\n";
                    }

                    //data.put("itemArtStatus_" + i, (delegator.findOne("StatusItem", UtilMisc.toMap("statusId", orderItem.getString("statusId")), true)).getString("description"));

                    GenericValue shipGroupAssoc = (GenericValue) item.get("shipGroupAssoc");
                    String trackingNumber = "";
                    String trackingURL = "";

                    if (UtilValidate.isNotEmpty(trackingNumber = (String) shipGroupAssoc.get("trackingNumber")) && UtilValidate.isNotEmpty(trackingURL = EnvUtil.getTrackingURL(trackingNumber))) {
                        cartHTML = cartHTML +
                                "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" role=\"presentation\">\n" +
                                "<tr>\n" +
                                "<td width=\"130\" valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#7d7d7d; text-align:left; padding:5px 0px 5px 0px;\">Tracking Number:</td>\n" +
                                "<td valign=\"top\" style=\"font-family:verdana,geneva,sans-serif; font-size:13px; color:#00a3e4; text-align:left; padding:5px 0px 5px 0px;\"><a style=\"text-decoration: none;\" title=\"Tracking Number\" href=\"" + trackingURL + "\">" + trackingNumber + "</a></td>\n" +
                                "</tr>\n" +
                                "</table>\n";
                        data.put("foundTrackingNumber", Boolean.toString(true));
                    }

                    cartHTML = cartHTML +
                            "</td>\n" +
                            "</tr>\n" +
                            "</table>\n" +
                            "</td>\n" +
                            "</tr>\n";
                }
            }

            cartHTML = cartHTML +
                    "</table>\n" +
                    "<!-- Cart Content END -->\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "<!-- Cart END-->";

            data.put("ShippingCartHTML1", cartHTML);

            String value = "";
            data.put("ShippingFullName", splitAtFirstSpace(shippingAddressData.getString(TO_NAME))[0] + " " + splitAtFirstSpace(shippingAddressData.getString(TO_NAME))[1]);
            data.put("ShippingCompanyName", UtilValidate.isNotEmpty(value = (String) shippingAddressData.get("companyName")) ? value : "");
            data.put("ShippingStreetAddress1", UtilValidate.isNotEmpty(value = (String) shippingAddressData.get("address1")) ? value : "");
            data.put("ShippingStreetAddress2", UtilValidate.isNotEmpty(value = (String) shippingAddressData.get("address2")) ? value : "");
            data.put("ShippingCity", UtilValidate.isNotEmpty(value = (String) shippingAddressData.get("city")) ? value : "");
            data.put("ShippingState", UtilValidate.isNotEmpty(value = (String) shippingAddressData.get("stateProvinceGeoId")) ? value : "");
            data.put("ShippingZip", UtilValidate.isNotEmpty(value = (String) shippingAddressData.get("postalCode")) ? value : "");
            data.put("BillingCompanyName", UtilValidate.isNotEmpty(value = (String) billingAddressData.get("companyName")) ? value : "");
            data.put("BillingStreetAddress1", UtilValidate.isNotEmpty(value = (String) billingAddressData.get("address1")) ? value : "");
            data.put("BillingStreetAddress2", UtilValidate.isNotEmpty(value = (String) billingAddressData.get("address2")) ? value : "");
            data.put("BillingCity", UtilValidate.isNotEmpty(value = (String) billingAddressData.get("city")) ? value : "");
            data.put("BillingState", UtilValidate.isNotEmpty(value = (String) billingAddressData.get("stateProvinceGeoId")) ? value : "");
            data.put("BillingZip", UtilValidate.isNotEmpty(value = (String) billingAddressData.get("postalCode")) ? value : "");
            data.put("ContactSalesPersonHTML", getSalesRepInfoHTML(((HashMap<String, String>) orderData.get("salesRep")).get("email"), ((HashMap<String, String>) orderData.get("salesRep")).get("name"), ((HashMap<String, String>) orderData.get("salesRep")).get("phone")));
        } catch (Exception e) {
            EnvUtil.reportError(e);
        }

        return data;
    }

    public static Map<String, String> createCustomQuoteEmailData(Map orderData, String quoteId) throws GenericEntityException {
        Map<String, String> data = new HashMap<String, String>();

        if (UtilValidate.isEmpty(orderData)) {
            return data;
        }

        data.put("QuoteId", quoteId);
        data.put("QuoteFirstName", (String) orderData.get("firstName"));
        data.put("QuoteName", orderData.get("firstName") + " " + orderData.get("lastName"));
        String address1 = "", address2 = "", companyName = "", city = "", state = "", zip = "";
        StringBuilder address = new StringBuilder()
                .append(UtilValidate.isNotEmpty(companyName = (String) orderData.get("companyName")) ? companyName + "<br/>" : "")
                .append(UtilValidate.isNotEmpty(address1 = (String) orderData.get("address1")) ? address1 + "<br/>" : "")
                .append(UtilValidate.isNotEmpty(address2 = (String) orderData.get("address2")) ? address2 + "<br/>" : "")
                .append(UtilValidate.isNotEmpty(city = (String) orderData.get("city")) ? city + ", " : "")
                .append(UtilValidate.isNotEmpty(state = (String) orderData.get("stateProvinceGeoId")) ? state + "-" : "")
                .append(UtilValidate.isNotEmpty(zip = (String) orderData.get("postalCode")) ? zip : "");
        data.put("QuoteAddress", address.toString());
        data.put("QuoteComments", (String) orderData.get("comment"));
        data.put("QuoteSize", (String) orderData.get("standardSize"));
        data.put("QuoteQuantity", (String) orderData.get("quantity"));
        data.put("QuoteSealingMethod", (String) orderData.get("sealingMethod"));
        data.put("QuotePaperType", (String) orderData.get("paperType"));
        data.put("QuotePrinting", (UtilValidate.isNotEmpty(orderData.get("printingRequired")) && ((String) orderData.get("printingRequired")).equals("Y")) ? "Yes" : "No");
        return data;
    }

    public static Map<String, String> createNonProfitRequestEmailData(Map requestData) throws GenericEntityException {
        Map<String, String> data = new HashMap<String, String>();
        data.put("EmailAddress", (String) requestData.get("email"));
        data.put("RequestFirstName", (String) requestData.get("billing_firstName"));
        data.put("RequestName", requestData.get("billing_firstName") + " " + requestData.get("billing_lastName"));
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

        data.put("RequestAddress", address.toString());

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

        data.put("RequestEnvelopesUse", envelopesUse.toString());
        data.put("RequestOrganizationType", UtilValidate.isNotEmpty(organizationType = (String) requestData.get("industry")) ? organizationType : "Did not answer.");
        data.put("RequestCompanyWebsite", UtilValidate.isNotEmpty(companyWebsite = (String) requestData.get("company_website")) ? companyWebsite : "Did not answer.");
        data.put("RequestTaxId", (String) requestData.get("federal_tax_id"));
        data.put("RequestUserName", (String) requestData.get("emailAddress"));
        data.put("RequestPassword", (String) requestData.get("password"));
        data.put("RequestComment", (String) requestData.get("comments"));

        return data;
    }

    public static Map<String, String> createOrderOutsourceData(Map<String, Object> incomingData) {
        Map<String, String> outgoingData = new HashMap<>();

        outgoingData.put("OrderNumber", UtilValidate.isNotEmpty(incomingData.get("orderId")) ? incomingData.get("orderId").toString() : null);
        outgoingData.put("QuoteName", UtilValidate.isNotEmpty(incomingData.get("name")) ? incomingData.get("name").toString() : null);
        outgoingData.put("Color", UtilValidate.isNotEmpty(incomingData.get("color")) ? incomingData.get("color").toString() : null);
        outgoingData.put("QuoteSealingMethod", UtilValidate.isNotEmpty(incomingData.get("sealingMethod")) ? incomingData.get("sealingMethod").toString() : null);
        outgoingData.put("Sku", UtilValidate.isNotEmpty(incomingData.get("sku")) ? incomingData.get("sku").toString() : null);
        outgoingData.put("VendorSku", UtilValidate.isNotEmpty(incomingData.get("vendorSku")) ? incomingData.get("vendorSku").toString() : null);
        outgoingData.put("Quantity", UtilValidate.isNotEmpty(incomingData.get("quantity")) ? incomingData.get("quantity").toString() : null);
        outgoingData.put("ColorsFront", UtilValidate.isNotEmpty(incomingData.get("colorsFront")) ? incomingData.get("colorsFront").toString() : null);
        outgoingData.put("FrontInk", UtilValidate.isNotEmpty(incomingData.get("frontInk")) ? incomingData.get("frontInk").toString() : null);
        outgoingData.put("ColorsBack", UtilValidate.isNotEmpty(incomingData.get("colorsBack")) ? incomingData.get("colorsBack").toString() : null);
        outgoingData.put("BackInk", UtilValidate.isNotEmpty(incomingData.get("backInk")) ? incomingData.get("backInk").toString() : null);
        outgoingData.put("Cost", UtilValidate.isNotEmpty(incomingData.get("cost")) ? incomingData.get("cost").toString() : null);
        outgoingData.put("POFile", UtilValidate.isNotEmpty(incomingData.get("poFile")) ? incomingData.get("poFile").toString() : null);
        outgoingData.put("WorkerFile", UtilValidate.isNotEmpty(incomingData.get("workerFile")) ? incomingData.get("workerFile").toString() : null);
        outgoingData.put("IsBlindShip", UtilValidate.isNotEmpty(incomingData.get("isBlindShip")) ? incomingData.get("isBlindShip").toString() : null);
        outgoingData.put("Comments", UtilValidate.isNotEmpty(incomingData.get("comments")) ? incomingData.get("comments").toString() : null);
        outgoingData.put("ShippingMethod", UtilValidate.isNotEmpty(incomingData.get("shippingMethod")) ? incomingData.get("shippingMethod").toString() : null);
        outgoingData.put("UpsAccount", UtilValidate.isNotEmpty(incomingData.get("upsAccount")) ? incomingData.get("upsAccount").toString() : null);
        outgoingData.put("DueDate", UtilValidate.isNotEmpty(incomingData.get("dueDate")) ? incomingData.get("dueDate").toString() : null);
        outgoingData.put("ShipDate", UtilValidate.isNotEmpty(incomingData.get("shipDate")) ? incomingData.get("shipDate").toString() : null);
        outgoingData.put("ShippingAddressName", UtilValidate.isNotEmpty(incomingData.get("shippingAddressName")) ? incomingData.get("shippingAddressName").toString() : null);
        outgoingData.put("ShippingCompanyName", UtilValidate.isNotEmpty(incomingData.get("shippingCompanyName")) ? incomingData.get("shippingCompanyName").toString() : null);
        outgoingData.put("ShippingAddress1", UtilValidate.isNotEmpty(incomingData.get("shippingAddress1")) ? incomingData.get("shippingAddress1").toString() : null);
        outgoingData.put("ShippingAddress2", UtilValidate.isNotEmpty(incomingData.get("shippingAddress2")) ? incomingData.get("shippingAddress2").toString() : null);
        outgoingData.put("ShippingCity", UtilValidate.isNotEmpty(incomingData.get("shippingCity")) ? incomingData.get("shippingCity").toString() : null);
        outgoingData.put("ShippingState", UtilValidate.isNotEmpty(incomingData.get("shippingState")) ? incomingData.get("shippingState").toString() : null);
        outgoingData.put("ShippingZip", UtilValidate.isNotEmpty(incomingData.get("shippingZip")) ? incomingData.get("shippingZip").toString() : null);

        return outgoingData;
    }

    public static Map<String, String> createOrderPendingEmailData(Delegator delegator, Map orderData, String orderId) throws GenericEntityException {
        Map<String, String> data = new HashMap<>();

        if (orderData == null) {
            orderData = OrderHelper.getOrderData(delegator, orderId, false);
        }

        data.put("OrderNumber", orderId);
        data.put("ShippingFirstName", splitAtFirstSpace(((GenericValue) orderData.get("shippingAddress")).getString(TO_NAME))[0]);

        return data;
    }

    public static Map<String, String> createPasswordEmailData(Delegator delegator, Map orderData, String orderId) throws GenericEntityException {
        Map<String, String> data = new HashMap<>();

        if (orderData == null) {
            orderData = OrderHelper.getOrderData(delegator, orderId, false);
        }

        data.put("BillingFirstName", ((GenericValue) orderData.get("billingAddress")).getString("toName"));
        data.put("EmailAddress", (String) orderData.get("email"));
        data.put("TempPassword", (String) orderData.get("password"));

        return data;
    }

    public static Map<String, String> createProductionEmailData(Delegator delegator, Map orderData, String orderId, String orderItemSeqId) throws GenericEntityException, ParseException {
        if (orderData == null) {
            orderData = OrderHelper.getOrderData(delegator, orderId, false);
        }

        Map<String, String> data = new HashMap<String, String>();
        GenericValue product = null;
        GenericValue orderItem = null;
        List<Map> items = (List<Map>) orderData.get("items");
        for (Map item : items) {
            orderItem = (GenericValue) item.get("item");
            product = (GenericValue) item.get("product");
            if (orderItemSeqId.equalsIgnoreCase(orderItem.getString("orderItemSeqId"))) {
                break;
            }
        }

        data.put("OrderNumber", orderId);
        data.put("ProductName", product.getString("productName"));
        data.put("ShippingFirstName", splitAtFirstSpace(((GenericValue) orderData.get("shippingAddress")).getString(TO_NAME))[0]);
        data.put("DueDate", EnvUtil.convertTimestampToString(orderItem.getTimestamp("dueDate"), "MM/dd/yyyy"));
        return data;
    }

    public static Map<String, String> createProofReadyEmailData(Delegator delegator, Map orderData, String orderId) throws GenericEntityException {
        Map<String, String> data = new HashMap<>();

        if (orderData == null) {
            orderData = OrderHelper.getOrderData(delegator, orderId, false);
        }

        GenericValue shippingAddressData = (GenericValue) orderData.get("shippingAddress");
        data.put("OrderNumber", orderId);
        data.put("ShippingFirstName", splitAtFirstSpace(shippingAddressData.getString(TO_NAME))[0]);
        data.put("EmailAddress", (String) orderData.get("email"));
        return data;
    }

    public static Map<String, String> createSampleEmailData(Delegator delegator, Map orderData, String orderId) throws GenericEntityException, ParseException {
        Map<String, String> data = new HashMap<>();
        if (orderData == null) {
            orderData = OrderHelper.getOrderData(delegator, orderId, false);
        }

        Map<String, Object> couponData = OrderHelper.orderSampleData(delegator, delegator.findByAnd("OrderItem", UtilMisc.toMap("orderId", orderId), null, false), true);
        data.put("ShippingFirstName", splitAtFirstSpace(((GenericValue) orderData.get("shippingAddress")).getString(TO_NAME))[0]);
        data.put("PromotionCode", (String) couponData.get("sampleCoupon"));
        data.put("PromotionAmount", "$" + EnvUtil.convertBigDecimal((BigDecimal) couponData.get("promotionAmount"), true));
        data.put("SampleCouponExpireDate", EnvUtil.convertTimestampToString((Timestamp) couponData.get("expireDate"), "MM/dd/yyyy"));

        return data;
    }

    public static Map<String, String> createTradeRequestEmailData(Map requestData) throws GenericEntityException {
        Map<String, String> data = new HashMap<String, String>();
        data.put("EmailAddress", (String) requestData.get("email"));
        data.put("RequestFirstName", (String) requestData.get("billing_firstName"));
        data.put("RequestName", requestData.get("billing_firstName") + " " + requestData.get("billing_lastName"));
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

        data.put("RequestAddress", address.toString());

        data.put("RequestIndustry", UtilValidate.isNotEmpty(industry = (String) requestData.get("industry")) ? industry : "Did not answer.");
        data.put("RequestInterestedIn", UtilValidate.isNotEmpty(interestedIn = (String) requestData.get("interest")) ? interestedIn : "Did not answer.");
        data.put("RequestCompanyWebsite", UtilValidate.isNotEmpty(companyWebsite = (String) requestData.get("company_website")) ? companyWebsite : "Did not answer.");
        data.put("RequestTaxId", (String) requestData.get("federal_tax_id"));
        data.put("RequestUserName", (String) requestData.get("emailAddress"));
        data.put("RequestPassword", (String) requestData.get("password"));
        data.put("RequestComment", (String) requestData.get("comments"));

        return data;
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
            if (rs != null) {
                while (rs.next()) {
                    Map<String, String> anniversaryData = new HashMap<String, String>();
                    anniversaryData.put("EmailAddress", rs.getString(1));
                    anniversaryData.put("AnniversaryCount", rs.getString(2));
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

    public static String buildEmailThisQuoteHTML(Map<String, Object> context, Boolean isDesktop) {
        String emailHTML = "";

        if (UtilValidate.isNotEmpty(context.get("quoteData"))) {
            Gson gson = new GsonBuilder().serializeNulls().create();
            List<Object> quoteDataList = gson.fromJson((String) context.get("quoteData"), ArrayList.class);

            for (Integer x = 0; x < quoteDataList.size(); x++) {
                for (Map.Entry<String, Object> entry : ((LinkedTreeMap<String, Object>) quoteDataList.get(x)).entrySet()) {
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
                    }
                }
            }
        }

        return emailHTML;
    }

    public static Map<String, String> createOutsourceArtworkData(Map<String, Object> data) throws Exception {
        Map<String, String> outgoingData = new HashMap<>();

        outgoingData.put("OrderNumber", UtilValidate.isNotEmpty(data.get("orderId")) ? String.valueOf(data.get("orderId")) : null);
        outgoingData.put("LineItem", UtilValidate.isNotEmpty(data.get("lineItem")) ? String.valueOf(data.get("lineItem")) : null);
        outgoingData.put("Sku", UtilValidate.isNotEmpty(data.get("sku")) ? String.valueOf(data.get("sku")) : null);
        outgoingData.put("Name", UtilValidate.isNotEmpty(data.get("name")) ? String.valueOf(data.get("name")) : null);
        outgoingData.put("Quantity", UtilValidate.isNotEmpty(data.get("quantity")) ? String.valueOf(data.get("quantity")) : null);
        outgoingData.put("VendorId", UtilValidate.isNotEmpty(data.get("vendorId")) ? String.valueOf(data.get("vendorId")) : null);
        outgoingData.put("Cost", UtilValidate.isNotEmpty(data.get("cost")) ? String.valueOf(data.get("cost")) : null);
        outgoingData.put("Comments", UtilValidate.isNotEmpty(data.get("comments")) ? String.valueOf(data.get("comments")) : null);
        outgoingData.put("EmailPO", UtilValidate.isNotEmpty(data.get("emailPO")) ? String.valueOf(data.get("emailPO")) : null);
        outgoingData.put("ShipDate", UtilValidate.isNotEmpty(data.get("shipDate")) ? String.valueOf(data.get("shipDate")) : null);
        outgoingData.put("DueDate", UtilValidate.isNotEmpty(data.get("dueDate")) ? String.valueOf(data.get("dueDate")) : null);
        outgoingData.put("POType", UtilValidate.isNotEmpty(data.get("poType")) ? String.valueOf(data.get("poType")) : null);
        outgoingData.put("WorkerFile", UtilValidate.isNotEmpty(data.get("workerFile")) ? String.valueOf(data.get("workerFile")) : null);
        outgoingData.put("StockSupplied", UtilValidate.isNotEmpty(data.get("stockSupplied")) ? String.valueOf(data.get("stockSupplied")) : null);
        outgoingData.put("StockSuppliedSku", UtilValidate.isNotEmpty(data.get("stockSuppliedSku")) ? String.valueOf(data.get("stockSuppliedSku")) : null);
        outgoingData.put("VendorColor", UtilValidate.isNotEmpty(data.get("vendorColor")) ? String.valueOf(data.get("vendorColor")) : null);
        outgoingData.put("AssignedTo", UtilValidate.isNotEmpty(data.get("assignedTo")) ? String.valueOf(data.get("assignedTo")) : null);
        outgoingData.put("CoatingText", UtilValidate.isNotEmpty(data.get("coatingText")) ? String.valueOf(data.get("coatingText")) : null);
        outgoingData.put("CoatingSide1", UtilValidate.isNotEmpty(data.get("coatingSide1")) ? String.valueOf(data.get("coatingSide1")) : null);
        outgoingData.put("CoatingSide2", UtilValidate.isNotEmpty(data.get("coatingSide2")) ? String.valueOf(data.get("coatingSide2")) : null);
        outgoingData.put("POFile", UtilValidate.isNotEmpty(data.get("poFile")) ? String.valueOf(data.get("poFile")) : null);
        outgoingData.put("ShippingAddressName", UtilValidate.isNotEmpty(data.get("shippingAddressName")) ? String.valueOf(data.get("shippingAddressName")) : null);
        outgoingData.put("ShippingCompanyName", UtilValidate.isNotEmpty(data.get("shippingCompanyName")) ? String.valueOf(data.get("shippingCompanyName")) : null);
        outgoingData.put("ShippingAddress1", UtilValidate.isNotEmpty(data.get("shippingAddress1")) ? String.valueOf(data.get("shippingAddress1")) : null);
        outgoingData.put("ShippingAddress2", UtilValidate.isNotEmpty(data.get("shippingAddress2")) ? String.valueOf(data.get("shippingAddress2")) : null);
        outgoingData.put("ShippingCity", UtilValidate.isNotEmpty(data.get("shippingCity")) ? String.valueOf(data.get("shippingCity")) : null);
        outgoingData.put("ShippingState", UtilValidate.isNotEmpty(data.get("shippingState")) ? String.valueOf(data.get("shippingState")) : null);
        outgoingData.put("ShippingZip", UtilValidate.isNotEmpty(data.get("shippingZip")) ? String.valueOf(data.get("shippingZip")) : null);
        outgoingData.put("ShippingFirstName", UtilValidate.isNotEmpty(data.get("orderFirstName")) ? String.valueOf(data.get("orderFirstName")) : null);
        outgoingData.put("POInstructionsHTML", UtilValidate.isNotEmpty(data.get("POInstructionsHTML")) ? String.valueOf(data.get("POInstructionsHTML")) : null);

        return outgoingData;
    }

    public static Map<String, String> convertQuoteAssignmentData(Map<String, Object> data) {
        Map<String, String> outgoingData = new HashMap<>();

        outgoingData.put("QuoteId", UtilValidate.isNotEmpty(data.get("quoteId")) ? String.valueOf(data.get("quoteId")) : null);
        outgoingData.put("StatusId", UtilValidate.isNotEmpty(data.get("statusId")) ? String.valueOf(data.get("statusId")) : null);
        outgoingData.put("QuoteType", UtilValidate.isNotEmpty(data.get("quoteType")) ? String.valueOf(data.get("quoteType")) : null);
        outgoingData.put("ProductId", UtilValidate.isNotEmpty(data.get("productId")) ? String.valueOf(data.get("productId")) : null);
        outgoingData.put("Gclid", UtilValidate.isNotEmpty(data.get("gclid")) ? String.valueOf(data.get("gclid")) : null);
        outgoingData.put("Source", UtilValidate.isNotEmpty(data.get("source")) ? String.valueOf(data.get("source")) : null);
        outgoingData.put("StandardSize", UtilValidate.isNotEmpty(data.get("standardSize")) ? String.valueOf(data.get("standardSize")) : null);
        outgoingData.put("SizeOrientation", UtilValidate.isNotEmpty(data.get("sizeOrientation")) ? String.valueOf(data.get("sizeOrientation")) : null);
        outgoingData.put("WindowCount", UtilValidate.isNotEmpty(data.get("windowCount")) ? String.valueOf(data.get("windowCount")) : null);
        outgoingData.put("QuotePaperType", UtilValidate.isNotEmpty(data.get("paperType")) ? String.valueOf(data.get("paperType")) : null);
        outgoingData.put("QuoteSealingMethod", UtilValidate.isNotEmpty(data.get("sealingMethod")) ? String.valueOf(data.get("sealingMethod")) : null);
        outgoingData.put("PrintingRequired", UtilValidate.isNotEmpty(data.get("printingRequired")) ? String.valueOf(data.get("printingRequired")) : null);
        outgoingData.put("InkFront", UtilValidate.isNotEmpty(data.get("inkFront")) ? String.valueOf(data.get("inkFront")) : null);
        outgoingData.put("InkBack", UtilValidate.isNotEmpty(data.get("inkBack")) ? String.valueOf(data.get("inkBack")) : null);
        outgoingData.put("Rush", UtilValidate.isNotEmpty(data.get("rushService")) ? String.valueOf(data.get("rushService")) : null);
        outgoingData.put("Quantity", UtilValidate.isNotEmpty(data.get("quantity")) ? String.valueOf(data.get("quantity")) : null);
        outgoingData.put("QuoteComments", UtilValidate.isNotEmpty(data.get("comment")) ? String.valueOf(data.get("comment")) : null);
        outgoingData.put("OrderedFrom", UtilValidate.isNotEmpty(data.get("orderedFrom")) ? String.valueOf(data.get("orderedFrom")) : null);
        outgoingData.put("CreatedDate", UtilValidate.isNotEmpty(data.get("createdDate")) ? String.valueOf(data.get("createdDate")) : null);
        outgoingData.put("AssignedTo", UtilValidate.isNotEmpty(data.get("assignedTo")) ? String.valueOf(data.get("assignedTo")) : null);
        outgoingData.put("WebSiteId", UtilValidate.isNotEmpty(data.get("webSiteId")) ? String.valueOf(data.get("webSiteId")) : null);
        outgoingData.put("ExportedDate", UtilValidate.isNotEmpty(data.get("exportedDate")) ? String.valueOf(data.get("exportedDate")) : null);
        outgoingData.put("InternalComments", UtilValidate.isNotEmpty(data.get("internalComments")) ? String.valueOf(data.get("internalComments")) : null);
        outgoingData.put("IsDrift", UtilValidate.isNotEmpty(data.get("isDrift")) ? String.valueOf(data.get("isDrift")) : null);
        outgoingData.put("PartyID", UtilValidate.isNotEmpty(data.get("partyId")) ? String.valueOf(data.get("partyId")) : null);
        outgoingData.put("QuoteFirstName", UtilValidate.isNotEmpty(data.get("firstName")) ? String.valueOf(data.get("firstName")) : null);
        outgoingData.put("QuoteLastName", UtilValidate.isNotEmpty(data.get("lastName")) ? String.valueOf(data.get("lastName")) : null);
        outgoingData.put("Address1", UtilValidate.isNotEmpty(data.get("address1")) ? String.valueOf(data.get("address1")) : null);
        outgoingData.put("Address2", UtilValidate.isNotEmpty(data.get("address2")) ? String.valueOf(data.get("address2")) : null);
        outgoingData.put("City", UtilValidate.isNotEmpty(data.get("city")) ? String.valueOf(data.get("city")) : null);
        outgoingData.put("StateProvinceGeoId", UtilValidate.isNotEmpty(data.get("stateProvinceGeoId")) ? String.valueOf(data.get("stateProvinceGeoId")) : null);
        outgoingData.put("PostalCode", UtilValidate.isNotEmpty(data.get("postalCode")) ? String.valueOf(data.get("postalCode")) : null);
        outgoingData.put("Phone", UtilValidate.isNotEmpty(data.get("phone")) ? String.valueOf(data.get("phone")) : null);
        outgoingData.put("AdditionalPhone", UtilValidate.isNotEmpty(data.get("additionalPhone")) ? String.valueOf(data.get("additionalPhone")) : null);
        outgoingData.put("EmailAddress", UtilValidate.isNotEmpty(data.get("userEmail")) ? String.valueOf(data.get("userEmail")) : null);
        outgoingData.put("WindowComment", UtilValidate.isNotEmpty(data.get("windowComment")) ? String.valueOf(data.get("windowComment")) : null);
        outgoingData.put("CompanyName", UtilValidate.isNotEmpty(data.get("companyName")) ? String.valueOf(data.get("companyName")) : null);
        outgoingData.put("Industry", UtilValidate.isNotEmpty(data.get("industry")) ? String.valueOf(data.get("industry")) : null);
        outgoingData.put("CountryGeoId", UtilValidate.isNotEmpty(data.get("countryGeoId")) ? String.valueOf(data.get("countryGeoId")) : null);
        outgoingData.put("AdditionalInfo", UtilValidate.isNotEmpty(data.get("additonalInfo")) ? String.valueOf(data.get("additonalInfo")) : null);
        outgoingData.put("ProductStyle", UtilValidate.isNotEmpty(data.get("productStyle")) ? String.valueOf(data.get("productStyle")) : null);

        return outgoingData;
    }

    public static Map<String, String> createQuoteCompletedEmailData(Delegator delegator, String[] quoteIds) throws GenericEntityException, ParseException {
        Map<String, String> data = new HashMap<>();

        String quoteHTML = "";

        for(String quoteId : quoteIds) {
            Map<String, Object> quoteData = CalculatorHelper.deserializedQuoteSummary(delegator, null, quoteId);
            GenericValue product = (GenericValue) quoteData.get("product");

            data.put("OFromName", (String) quoteData.get("salesPerson"));
            data.put("OFromEmail", (String) quoteData.get("salesEmail"));
            data.put("OReplyEmail", (String) quoteData.get("salesEmail"));

            data.put("EmailAddress", ((GenericValue) quoteData.get("customer")).getString("userEmail"));
            data.put("QuoteId", ((GenericValue) quoteData.get("customer")).getString("quoteId"));
            data.put("SalesPerson", (String) quoteData.get("salesPerson"));
            data.put("SalesEmail", (String) quoteData.get("salesEmail"));
            data.put("SalesNumber", (String) quoteData.get("salesNumber"));

            StringBuilder desktopQty = new StringBuilder();
            StringBuilder printOptionsDesktop = new StringBuilder();

            printOptionsDesktop.append("<tr><td style=\"width: 425px; height: 5px;\"></td></tr><tr><td style=\"width: 425px; vertical-align: top;\"><div style=\"font-size: 14px; color: #818181;\">");
            if(UtilValidate.isNotEmpty(quoteData.get("offset")) && (Boolean) quoteData.get("offset")) {
                printOptionsDesktop.append("<div style=\"font-size: 14px; color: #818181;\"><div style=\"font-size: 14px; color: #232323; font-weight: bold;\">Offset Printing: </div>" + ((String) quoteData.get("offsetNote")).replace("\n", "<br />") + "</div>");
            }
            if(UtilValidate.isNotEmpty(quoteData.get("foil")) && (Boolean) quoteData.get("foil")) {
                printOptionsDesktop.append("<div style=\"font-size: 14px; color: #818181;\"><span style=\"font-size: 14px; color: #232323; font-weight: bold;\">Number of Foil Images: </span> " + quoteData.get("totalFoilImages") + "</div>");
            }
            if(UtilValidate.isNotEmpty(quoteData.get("embossing")) && (Boolean) quoteData.get("embossing")) {
                printOptionsDesktop.append("<div style=\"font-size: 14px; color: #818181;\"><span style=\"font-size: 14px; color: #232323; font-weight: bold;\">Number of Embossed Images: </span> " + quoteData.get("totalEmbossingImages") + "</div>");
            }
            printOptionsDesktop.append("</td></tr>");

            String fileName = quoteData.get("quoteRequestId") + "-" + quoteData.get("quoteId") + ".pdf";

            Iterator iter = ((Map) quoteData.get("prices")).entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry pair = (Map.Entry) iter.next();
                Integer key = (Integer) pair.getKey();
                Map<String, BigDecimal> prices = (Map<String, BigDecimal>) pair.getValue();

                desktopQty.append("<tr><td style=\"width: 425px; height: 5px;\"></td></tr><tr><td style=\"width: 425px; vertical-align: top;\"><div style=\"font-size: 14px; color: #818181;\"><span style=\"font-size: 14px; color: #232323; font-weight: bold;\">Quantity:</span> ")
                        .append(key.toString());

                if (CalculatorHelper.discountAmount.compareTo(new BigDecimal(0)) != 0) {
                    desktopQty.append(" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"font-size: 14px; color: #232323; font-weight: bold;\">Price:</span> <strike>$")
                            .append((prices.get("total").divide(new BigDecimal(1).subtract(CalculatorHelper.discountAmount), 2, RoundingMode.HALF_UP)).toPlainString())
                            .append("</strike>&nbsp;&nbsp;<span style=\"color: #e16e12;\">$");
                } else {
                    desktopQty.append(" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"font-size: 14px; color: #232323; font-weight: bold;\">Price:</span> <span style=\"color: #e16e12;\">$");
                }

                desktopQty.append(prices.get("total").toPlainString())
                        .append("</span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"font-size: 14px; color: #232323; font-weight: bold;\">Each:</span> $")
                        .append(prices.get("each").toPlainString())
                        .append("</div></td></tr>");
            }

            quoteHTML = quoteHTML +
                "<table style=\"width: 425px; background-color: #ffffff;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                    "<tr>\n" +
                        "<td style=\"width: 425px; vertical-align: top;\">\n" +
                            "<img src=\"https://actionenvelope.scene7.com/is/image/ActionEnvelope/" + ((product != null) ? product.getString("productId") : "CUSTOM-P") + "?wid=150&fmt=jpeg&qlt=75\" style=\"display: block; border: 0px;\" alt=\"Product Image\" />\n" +
                        "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                        "<td style=\"width: 425px; height: 20px;\"></td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                        "<td style=\"width: 425px; vertical-align: top;\">\n" +
                            "<div style=\"font-size: 18px; color: #063b63; font-weight: bold;\">Quote Summary:</div>\n" +
                        "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                        "<td style=\"width: 425px; height: 10px;\"></td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                        "<td style=\"width: 425px; vertical-align: top;\">\n" +
                            "<div style=\"font-size: 14px; color: #818181;\"><span style=\"font-size: 14px; color: #232323; font-weight: bold;\">Style:</span> " + ((product != null) ? product.getString("productId") : "CUSTOM-P") + "</div>\n" +
                        "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                        "<td style=\"width: 425px; height: 10px;\"></td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                        "<td style=\"width: 425px; vertical-align: top;\">\n" +
                            "<div style=\"font-size: 14px; color: #818181;\"><span style=\"font-size: 14px; color: #232323; font-weight: bold;\">Product:</span> " + ((product != null) ? product.getString("productName") : (String) quoteData.get("productName")) + " " + quoteData.get("material") + (UtilValidate.isNotEmpty(quoteData.get("COATING")) ? " - C" + ((List) quoteData.get("COATING")).size() + "S" : "") + "</div>\n" +
                        "</td>\n" +
                    "</tr>\n" +
                    desktopQty.toString() +
                    "<tr>\n" +
                        "<td style=\"width: 425px; height: 5px;\"></td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                        "<td style=\"width: 425px; vertical-align: top;\">\n" +
                            "<div style=\"font-size: 14px; color: #818181;\"><span style=\"font-size: 14px; color: #232323; font-weight: bold;\">Printing Method:</span> " + quoteData.get("printSummary") + "</div>\n" +
                        "</td>\n" +
                    "</tr>\n" +
                    printOptionsDesktop.toString() +
                    "<tr>\n" +
                        "<td style=\"width: 425px; height: 5px;\"></td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                        "<td style=\"width: 425px; vertical-align: top;\">\n" +
                            "<div style=\"font-size: 14px; color: #818181;\"><span style=\"font-size: 14px; color: #232323; font-weight: bold;\">Production Time:</span> " + (UtilValidate.isNotEmpty(quoteData.get("production")) ? (String) quoteData.get("production") : "Not Specified.") + "</div>\n" +
                        "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                        "<td style=\"width: 425px; height: 5px;\"></td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                        "<td style=\"width: 425px; vertical-align: top;\">\n" +
                            "<div style=\"font-size: 14px; color: #818181;\"><span style=\"font-size: 14px; color: #232323; font-weight: bold;\">Comments:</span> " + (UtilValidate.isNotEmpty(quoteData.get("comment")) ? (String) quoteData.get("comment") : "No comments.") + "</div>\n" +
                        "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                        "<td style=\"width: 425px; height: 20px;\"></td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                        "<td style=\"width: 425px; vertical-align: top;\">\n" +
                            "<table style=\"width: 350px; border: 0px\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                                "<tr>\n" +
                                    "<td style=\"width: 169px; height: 42px; background-color: #FFFFF; text-transform: uppercase; padding: 0px; font-size: 14px; text-align: center; font-weight: bold; color: #ffffff;\">\n" +
                                        "<a target=\"_blank\" href=\"" + "https://www.folders.com/folders/control/quoteOrder?quoteRequestId=" + quoteData.get("quoteRequestId") + "&quoteIds=" + String.join(",", quoteIds) + "\" style=\"text-decoration: none;\">\n" +
                                            "<img src=\"http://hosting-source.bm23.com/38021/public/Transactional-Quote_Completed/015_2019_FOL_Quote-Completed-Update-Email_Add_to_Cart_Button.jpg\" alt=\"Add To Cart\" width=\"169px\" style=\"mso-hide: all; max-width: 300px; display: block; border: 0px;\" />\n" +
                                        "</a>\n" +
                                    "</td>\n" +
                                    "<td style=\"width: 10px; height: 42px;\"></td>\n" +
                                        "<td style=\"width: 169px; height: 42px; background-color: #FFFFFF; text-transform: uppercase; font-size: 14px; text-align: center; font-weight: bold; padding: 0px; color: #FFFFFF; border\">\n" +
                                            "<a target=\"_blank\" href=\"" + "https://www.folders.com/folders/control/serveFileForStream?filePath=uploads/quotes/" + fileName + "&fileName=" + fileName + "&downLoad=Y" + "\" style=\"font-size: 14px; font-weight: bold;\">\n" +
                                            "<img src=\"http://hosting-source.bm23.com/38021/public/Transactional-Quote_Completed/015_2019_FOL_Quote-Completed-Update-Email_PDF_Button.jpg\" alt=\"Download PDF\" width=\"169px\" style=\"mso-hide: all; max-width: 300px; display: block; border: 0px;\" />\n" +
                                        "</a>\n" +
                                    "</td>\n" +
                                "</tr>\n" +
                            "</table>\n" +
                        "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                        "<td style=\"width: 425px; height: 10px;\"></td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                        "<td style=\"width: 425px; height: 35px;\"></td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                        "<td style=\"width: 425px; height: 5px; background-color: #d4d4d4;\"></td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                        "<td style=\"width: 425px; height: 35px;\"></td>\n" +
                    "</tr>\n" +
                "</table>";
        }

        data.put("QuoteHTML", quoteHTML);

        return data;
    }

    public static Map<String, String> createQuoteRequestData(Map<String, Object> data) {
        Map<String, String> outgoingData = new HashMap<>();

        outgoingData.put("EmailAddress", UtilValidate.isNotEmpty(data.get("email")) ? String.valueOf(data.get("email")) : null);
        outgoingData.put("UserEmail", UtilValidate.isNotEmpty(data.get("userEmail")) ? String.valueOf(data.get("userEmail")) : null);
        outgoingData.put("ProductId", UtilValidate.isNotEmpty(data.get("productId")) ? String.valueOf(data.get("productId")) : null);
        outgoingData.put("Quantity", UtilValidate.isNotEmpty(data.get("productQuantity")) ? String.valueOf(data.get("productQuantity")) : null);
        outgoingData.put("PartyId", UtilValidate.isNotEmpty(data.get("partyId")) ? String.valueOf(data.get("partyId")) : null);
        outgoingData.put("StatusId", UtilValidate.isNotEmpty(data.get("statusId")) ? String.valueOf(data.get("statusId")) : null);
        outgoingData.put("CreatedDate", UtilValidate.isNotEmpty(data.get("createdDate")) ? String.valueOf(data.get("createdDate")) : null);
        outgoingData.put("QuoteId", UtilValidate.isNotEmpty(data.get("quoteId")) ? String.valueOf(data.get("quoteId")) : null);
        outgoingData.put("QuoteLastName", UtilValidate.isNotEmpty(data.get("lastName")) ? String.valueOf(data.get("lastName")) : null);
        outgoingData.put("QuoteAddress", UtilValidate.isNotEmpty(data.get("address1")) ? String.valueOf(data.get("address1")) : null);
        outgoingData.put("CompanyName", UtilValidate.isNotEmpty(data.get("companyName")) ? String.valueOf(data.get("companyName")) : null);
        outgoingData.put("PostalCode", UtilValidate.isNotEmpty(data.get("postalCode")) ? String.valueOf(data.get("postalCode")) : null);
        outgoingData.put("Comments", UtilValidate.isNotEmpty(data.get("additonalInfo")) ? String.valueOf(data.get("additonalInfo")) : null);
        outgoingData.put("QuoteFirstName", UtilValidate.isNotEmpty(data.get("firstName")) ? String.valueOf(data.get("firstName")) : null);
        outgoingData.put("QuoteReferenceNumber", UtilValidate.isNotEmpty(data.get("referenceNumber")) ? String.valueOf(data.get("referenceNumber")) : null);
        outgoingData.put("StateProvinceGeoId", UtilValidate.isNotEmpty(data.get("stateProvinceGeoId")) ? String.valueOf(data.get("stateProvinceGeoId")) : null);
        outgoingData.put("QuoteFoilColor", UtilValidate.isNotEmpty(data.get("foilColor")) ? String.valueOf(data.get("foilColor")) : "None");
        outgoingData.put("Phone", UtilValidate.isNotEmpty(data.get("phone")) ? String.valueOf(data.get("phone")) : null);
        outgoingData.put("QuoteFolderStyle", UtilValidate.isNotEmpty(data.get("productStyle")) ? String.valueOf(data.get("productStyle")) : null);
        outgoingData.put("QuoteColorSide1", UtilValidate.isNotEmpty(data.get("outsideColor")) ? String.valueOf(data.get("outsideColor")) : null);
        outgoingData.put("QuoteColorSide2", UtilValidate.isNotEmpty(data.get("insideColor")) ? String.valueOf(data.get("insideColor")) : null);

        return outgoingData;
    }

    public static Map<String, String> createSamplePackRequestData(Map<String, Object> data) {
        Map<String, String> outgoingData = new HashMap<>();

        outgoingData.put("ProductId", UtilValidate.isNotEmpty(data.get("sku")) ? String.valueOf(data.get("sku")) : null);
        outgoingData.put("FirstName", UtilValidate.isNotEmpty(data.get("firstName")) ? String.valueOf(data.get("firstName")) : null);
        outgoingData.put("LastName", UtilValidate.isNotEmpty(data.get("lastName")) ? String.valueOf(data.get("lastName")) : null);
        outgoingData.put("EmailAddress", UtilValidate.isNotEmpty(data.get("email")) ? String.valueOf(data.get("email")) : null);
        outgoingData.put("Phone", UtilValidate.isNotEmpty(data.get("phoneNumber")) ? String.valueOf(data.get("phoneNumber")) : null);
        outgoingData.put("CompanyName", UtilValidate.isNotEmpty(data.get("companyName")) ? String.valueOf(data.get("companyName")) : null);
        outgoingData.put("Address1", UtilValidate.isNotEmpty(data.get("address")) ? String.valueOf(data.get("address")) : null);
        outgoingData.put("City", UtilValidate.isNotEmpty(data.get("city")) ? String.valueOf(data.get("city")) : null);
        outgoingData.put("StateProvinceGeoId", UtilValidate.isNotEmpty(data.get("state")) ? String.valueOf(data.get("state")) : null);
        outgoingData.put("PostalCode", UtilValidate.isNotEmpty(data.get("zipCode")) ? String.valueOf(data.get("zipCode")) : null);

        return outgoingData;
    }

    public static Map<String, String> createSampleRequestData(Map<String, Object> data) {
        Map<String, String> outgoingData = new HashMap<>();

        outgoingData.put("ProductId", UtilValidate.isNotEmpty(data.get("sku")) ? String.valueOf(data.get("sku")) : null);
        outgoingData.put("PrintingMethod1", UtilValidate.isNotEmpty(data.get("printingMethod1")) ? String.valueOf(data.get("printingMethod1")) : null);
        outgoingData.put("PrintingMethod2", UtilValidate.isNotEmpty(data.get("printingMethod2")) ? String.valueOf(data.get("printingMethod2")) : null);
        outgoingData.put("PrintingMethod3", UtilValidate.isNotEmpty(data.get("printingMethod3")) ? String.valueOf(data.get("printingMethod3")) : null);
        outgoingData.put("ColorName1", UtilValidate.isNotEmpty(data.get("colorName1")) ? String.valueOf(data.get("colorName1")) : null);
        outgoingData.put("ColorName2", UtilValidate.isNotEmpty(data.get("colorName2")) ? String.valueOf(data.get("colorName2")) : null);
        outgoingData.put("ColorName3", UtilValidate.isNotEmpty(data.get("colorName3")) ? String.valueOf(data.get("colorName3")) : null);
        outgoingData.put("FoilColor", UtilValidate.isNotEmpty(data.get("foilColor")) ? String.valueOf(data.get("foilColor")) : null);
        outgoingData.put("FirstName", UtilValidate.isNotEmpty(data.get("firstName")) ? String.valueOf(data.get("firstName")) : null);
        outgoingData.put("LastName", UtilValidate.isNotEmpty(data.get("lastName")) ? String.valueOf(data.get("lastName")) : null);
        outgoingData.put("EmailAddress", UtilValidate.isNotEmpty(data.get("email")) ? String.valueOf(data.get("email")) : null);
        outgoingData.put("Phone", UtilValidate.isNotEmpty(data.get("phoneNumber")) ? String.valueOf(data.get("phoneNumber")) : null);
        outgoingData.put("CompanyName", UtilValidate.isNotEmpty(data.get("companyName")) ? String.valueOf(data.get("companyName")) : null);
        outgoingData.put("Address1", UtilValidate.isNotEmpty(data.get("address")) ? String.valueOf(data.get("address")) : null);
        outgoingData.put("City", UtilValidate.isNotEmpty(data.get("city")) ? String.valueOf(data.get("city")) : null);
        outgoingData.put("StateProvinceGeoId", UtilValidate.isNotEmpty(data.get("state")) ? String.valueOf(data.get("state")) : null);
        outgoingData.put("PostalCode", UtilValidate.isNotEmpty(data.get("zipCode")) ? String.valueOf(data.get("zipCode")) : null);

        return outgoingData;
    }

    public static Map<String, String> createBackorderEmailData(Map orderData, GenericValue orderItem) throws Exception {
        Map<String, String> outgoingData = new HashMap<>();

        if (UtilValidate.isEmpty(orderData) || UtilValidate.isEmpty(orderItem)) {
            return outgoingData;
        }

        outgoingData.put("OrderNumber", (String) ((GenericValue) orderData.get("orderHeader")).get("orderId"));
        outgoingData.put("ProductSku1", orderItem.getString("productId"));
        outgoingData.put("ProductTitle1", orderItem.getString("itemDescription"));
        outgoingData.put("ProductQty1", orderItem.getBigDecimal("quantity").setScale(0,BigDecimal.ROUND_DOWN).toString());

        return outgoingData;
    }
}