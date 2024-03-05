package com.bigname.integration.cimpress;

import com.bigname.core.logging.BlackBox;
import com.bigname.core.util.Counter;
import com.bigname.integration.cimpress.client.request.*;
import com.envelopes.cart.CartHelper;
import com.envelopes.order.OrderHelper;
import com.envelopes.party.PartyHelper;
import com.envelopes.product.ProductHelper;
import com.envelopes.service.ServiceHelper;
import com.envelopes.shipping.ShippingHelper;
import com.envelopes.util.EnvUtil;
import com.google.gson.Gson;
import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.DelegatorFactory;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.jdbc.SQLProcessor;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.entity.util.EntityUtilProperties;
import org.apache.ofbiz.order.shoppingcart.CheckOutHelper;
import org.apache.ofbiz.order.shoppingcart.ShoppingCart;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartHelper;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartItem;
import org.apache.ofbiz.product.store.ProductStoreWorker;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ModelService;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class CimpressHelper {

    public static final String module = CimpressHelper.class.getName();

    enum OrderMode {SINGLE_ITEM, MULTI_ITEM}

    private static final OrderMode MODE = OrderMode.SINGLE_ITEM;

    private static final Map<String, String> cimpressConfig = new HashMap<>();

    private static final Map<String, String> defaultCimpressConfig = new HashMap<>();
    static {
        defaultCimpressConfig.put(ConfigKey.AUTH_ENDPOINT.key(), "https://cimpress.auth0.com");
        defaultCimpressConfig.put(ConfigKey.FULFILLMENT_MANAGER_API_ENDPOINT.key(),  "https://tst-fulfillment.at.cimpress.io/v1");
        defaultCimpressConfig.put(ConfigKey.ORDER_MANAGER_API_ENDPOINT.key(),  "https://int-order-manager.commerce.cimpress.io/v1");
        defaultCimpressConfig.put(ConfigKey.CLIENT_ID.key(), "hZh2nfhEejDMc30vwpx05IVx0Iuq7aSO");
        defaultCimpressConfig.put(ConfigKey.FULFILLER_ID.key(), "cje6pyh840");
        defaultCimpressConfig.put(ConfigKey.USER_NAME.key(), "laura@bigname.com");
        defaultCimpressConfig.put(ConfigKey.PASSWORD.key(), "ActionEnv123!");
        defaultCimpressConfig.put(ConfigKey.AUTO_ACCEPT.key(), "true");
        defaultCimpressConfig.put(ConfigKey.NOTIFICATION_FETCH_SIZE.key(), "25");
        defaultCimpressConfig.put(ConfigKey.LOGGER_LEVEL.key(), "INFO");
        defaultCimpressConfig.put(ConfigKey.ALLOW_DUPLICATE.key(), "false");
        defaultCimpressConfig.put(ConfigKey.ORDER_MODE.key(), "SINGLE_ITEM");
        defaultCimpressConfig.put(ConfigKey.PARTY_ID.key(), "2053030");
        defaultCimpressConfig.put(ConfigKey.NETSUITE_EXPORT.key(), "false");
        defaultCimpressConfig.put(ConfigKey.SHIPPING_CHANGE_REPEAT_EMAIL.key(), "false");
        defaultCimpressConfig.put(ConfigKey.ADDRESS_CHANGE_REPEAT_EMAIL.key(), "false");
        defaultCimpressConfig.put(ConfigKey.CANCELLATION_REPEAT_EMAIL.key(), "false");
        defaultCimpressConfig.put(ConfigKey.NOTIFICATION_EMAIL_ADDRESS.key(), "mike@bigname.com");
        defaultCimpressConfig.put(ConfigKey.PRICE_CHECK.key(), "true");
        defaultCimpressConfig.put(ConfigKey.ORDER_NOTE.key(), "** VISTAPRINT Order ** Ship on customers UPS ACCT#072VA4 **");
    }

    static void refreshCimpressConfig() {
        cimpressConfig.clear();
    }

    public static Map<String, String> getCimpressConfig(Delegator... delegator) {
        if(delegator == null || delegator.length == 0 || delegator[0] == null) {
            delegator = new Delegator[] {DelegatorFactory.getDelegator("default")};
        }
        if(cimpressConfig.isEmpty()) {
            try {
                List<GenericValue> configParams = EntityQuery.use(delegator[0]).from("CimpressConfig").cache(false).queryList();
                if (UtilValidate.isNotEmpty(configParams)) {
                    configParams.forEach(e -> cimpressConfig.put(e.getString("paramName"), e.getString("paramValue")));
                } else {
                    cimpressConfig.putAll(defaultCimpressConfig);
                }
            } catch (GenericEntityException e) {
                EnvUtil.reportError(e);
                Debug.logError("Error while getting Cimpress Config:  " + e, module);
            }
        }
        return UtilValidate.isEmpty(cimpressConfig) ? defaultCimpressConfig : cimpressConfig;
    }

    private static boolean isConfigValid(Map<String, String> config, BlackBox logger) {
        boolean valid = true;
        for (ConfigKey configKey : ConfigKey.values()) {
            if(!configKey.isOptional() && UtilValidate.isEmpty(config.get(configKey.key()))) {
                valid = false;
                logger.error(configKey.key() + " is required, but is EMPTY");
            }
        }
        return valid;
    }

    @SuppressWarnings("unchecked")
    static Map<String, Object> getNewOrdersFromCimpress(Delegator delegator, LocalDispatcher dispatcher){
        Map<String, Object> result = new HashMap<>();
        Counter totalOrderItems = Counter.ZERO.auto();
        Counter totalOrders = Counter.ZERO.auto();
        Counter failedOrders = Counter.ZERO.auto();
        Counter importedOrders = Counter.ZERO.auto();
        Counter importedOrdersWithWarnings = Counter.ZERO.auto();
        Counter failedOrderItems = Counter.ZERO.auto();
        Counter importedOrderItems = Counter.ZERO.auto();
        Counter acceptedOrderItems = Counter.ZERO.auto();
        Counter unacceptedOrderItems = Counter.ZERO.auto();
        BlackBox logger = new BlackBox();
        logger.info("Starting Cimpress order import process");
        boolean success = false;
        String importStatus = "";
        Map<String, String> cimpressConfig = getCimpressConfig(delegator);
        BlackBox.Level level = BlackBox.Level.INFO;
        try {
            level = BlackBox.Level.valueOf(UtilValidate.isNotEmpty(cimpressConfig.get(ConfigKey.LOGGER_LEVEL.key())) ? cimpressConfig.get(ConfigKey.LOGGER_LEVEL.key()) : "INFO");
        } catch(Exception e) {
            logger.warning("Invalid value for '" + ConfigKey.LOGGER_LEVEL.key() + "' config parameter, using default");
            EnvUtil.reportError(e);
        }
        boolean allowDuplicates = "true".equals(cimpressConfig.get(ConfigKey.ALLOW_DUPLICATE.key()));
        boolean autoAccept = !"false".equals(cimpressConfig.get(ConfigKey.AUTO_ACCEPT.key()));

        logger.debug("Auto-accept mode is " + (autoAccept ? "ON" : "OFF"))
                .debug("Order mode is " + MODE)
                .debug("Allow duplicate order item import is " + (allowDuplicates ? "ON" : "OFF"))
                .debug("Getting new order notifications from Cimpress");


        try {
            Map<String, Object> notificationResponse = CimpressUtil.getNotifications(new GetNotificationsRequest("OrderRequest"));

            try {
                Debug.log("Cimpress - Order Response Success: " + notificationResponse.toString());
            } catch (Exception e) {
                Debug.log("Cimpress - Order Response Fail: " + e.getMessage());
            }

            if(UtilValidate.isEmpty(notificationResponse) || !notificationResponse.containsKey("notifications")) {
                logger.error("Unable to get new order notification from Cimpress");
            } else if(UtilValidate.isEmpty(notificationResponse.get("notifications"))) {
                success = true;
                importStatus = "COMPLETED";
                logger.info("No new order notifications")
                        .info("Importing Cimpress orders completed");
            } else {
                String emailSubject = "Cimpress - Auto-accepting Item(s) Failed";
                StringBuilder emailMessage = new StringBuilder("Accepting below orders failed <br/>");
                boolean[] sendEmail = {false};

                List<Map<String, Object>> notifications = (List<Map<String, Object>>) notificationResponse.get("notifications");
                logger.debug("Found " + notifications.size() + " NEW notification(s) from Cimpress");
                logger.summary("New order notifications : " + notifications.size());
                notifications.forEach(notification -> {
                    boolean skipOrder = false;
                    String cimpressOrderId = (String) ((Map<String, Object>) notification.get("order")).get("orderId");
                    logger.debug("Processing Cimpress Order with ID: " + cimpressOrderId);
                    final List<Map<String, Object>> orderItems = new ArrayList<>();
                    try {
                        orderItems.addAll(((List<Map<String, Object>>) notification.get("items")).stream().filter(e -> checkCimpressOrderStatus((String) e.get("itemId"), "new")).collect(Collectors.toList()));
                        logger.debug("Found " + orderItems.size() + " order item(s) in NEW status for Cimpress order [Order ID: " + cimpressOrderId + "]");


                        List<Map<String, Object>> filteredOrderItems = new ArrayList<>();

                        if(allowDuplicates) {
                            filteredOrderItems.addAll(orderItems);
                        } else {
                            try {
                                logger.debug("Filtering already imported order items for Cimpress order [Order ID: " + cimpressOrderId + "]");

                                List<String> alreadyImportedItemIds = getAlreadyImportedItemIds(delegator, orderItems);

                                logger.info("Found " + alreadyImportedItemIds.size() + " already imported order item(s) for Cimpress order [Order ID: " + cimpressOrderId + "]");

                                if (UtilValidate.isNotEmpty(alreadyImportedItemIds)) {
                                    orderItems.forEach(e -> {
                                        if (alreadyImportedItemIds.contains(e.get("itemId"))) {
                                            logger.info("Removing order item with itemId " + e.get("itemId") + " from items to import, since a BIGNAME order with the same itemId already exists [Order ID: " + cimpressOrderId + "]");
                                        } else {
                                            filteredOrderItems.add(e);
                                        }
                                    });
                                } else {
                                    filteredOrderItems.addAll(orderItems);
                                }
                            } catch (Exception e) {
                                logger.error("An error occurred while filtering already imported order items for Cimpress order [Order ID: " + cimpressOrderId + "]");
                                totalOrders.incr();
                                failedOrders.incr();
                                skipOrder = true;
                                EnvUtil.reportError(e);
                                Debug.logError("Error while filtering already imported Cimpress order items:  " + e, module);
                            }
                        }
                        if(skipOrder) {
                            logger.warning("Skipping Cimpress order [Order ID: " + cimpressOrderId + "]");
                        } else {
                            orderItems.clear();
                            if (UtilValidate.isNotEmpty(filteredOrderItems)) {
                                orderItems.addAll(filteredOrderItems);
                            }

                            if (UtilValidate.isEmpty(orderItems)) {
                                logger.warning("No order item found for Cimpress order to import [Order ID: " + cimpressOrderId + "]");
                            } else {
                                totalOrderItems.add(orderItems.size());
                                logger.info("Cimpress order has " + orderItems.size() + " order item(s) [Order ID: " + cimpressOrderId + "]");

                                Map<String, Object> cimpressOrderMap = getOrderFromCimpress(delegator, cimpressOrderId, orderItems, logger);

                                if (UtilValidate.isEmpty(cimpressOrderMap)) {
                                    logger.warning("Empty Cimpress order, so skipping the order [Order ID: " + cimpressOrderId + "]");
                                    failedOrders.incr();
                                }
                                cimpressOrderMap.put("notificationId", notification.get("notificationId"));
                                if (MODE == OrderMode.SINGLE_ITEM) {
                                    logger.debug("Splitting Cimpress order in to " + orderItems.size() + " SINGLE_ITEM order(s)  [Order ID: " + cimpressOrderId + "]");
                                }

                                List<Map<String, Object>> orders = splitOrders(cimpressOrderMap);
                                totalOrders.add(orders.size());
                                orders.forEach(order -> {
                                    List<Map<String, Object>> cimpressItems = new ArrayList<>();
                                    try {
                                        cimpressItems = (List<Map<String, Object>>) order.get("items");
                                        Map<String, Object> callResult = createOrder(delegator, dispatcher, order);
                                        int actualNumberOfItems = MODE == OrderMode.SINGLE_ITEM ? 1 : orderItems.size();
                                        int importedNumberOfItems = cimpressItems.size();

                                        boolean orderCreated = false;
                                        if ((boolean) callResult.get("success")) {
                                            String orderId = (String) callResult.get("orderId");
                                            if (UtilValidate.isNotEmpty(orderId)) {
                                                orderCreated = true;
                                                if (actualNumberOfItems == importedNumberOfItems) {
                                                    logger.info("BIGNAME order created [Order ID: " + orderId + "]");
                                                    importedOrders.incr();
                                                } else {
                                                    logger.info("BIGNAME order created with warning(s) [Order ID: " + orderId + "]");
                                                    importedOrdersWithWarnings.incr();
                                                    failedOrderItems.add(actualNumberOfItems - importedNumberOfItems);
                                                }
                                                importedOrderItems.add(importedNumberOfItems);
                                                boolean[] orderAccepted = {false};
                                                if (autoAccept) {
                                                    cimpressItems.forEach(e -> {
                                                        orderAccepted[0] = false;
                                                        String cimpressItemId = (String) e.get("itemId");
                                                        Map<String, Object> links = (Map<String, Object>) e.get("links");
                                                        if (UtilValidate.isEmpty(links) || UtilValidate.isEmpty(links.get("accept"))) {
                                                            logger.warning("Accept link is empty, so unable to accept Cimpress item [Item ID: " + cimpressItemId + "]");
                                                            unacceptedOrderItems.incr();
                                                        } else {
                                                            Map<String, Object> acceptLink = (Map<String, Object>) links.get("accept");
                                                            Boolean accepted = CimpressUtil.acceptNotification((String) acceptLink.get("href"));
                                                            if (accepted != null && accepted) {
                                                                acceptedOrderItems.incr();
                                                                logger.info("Accepted Cimpress item [Item ID: " + cimpressItemId + "]");
                                                                orderAccepted[0] = true;
                                                            } else {
                                                                unacceptedOrderItems.incr();
                                                                logger.warning("Unable to accept Cimpress item [Item ID: " + cimpressItemId + "]");
                                                                emailMessage.append("<br/>Bigname OrderId: " + orderId + ", Cimpress OrderId: " + order.get("orderId") + ", Cimpress ItemId: " + cimpressItemId +System.lineSeparator());
                                                                sendEmail[0] = true;
                                                            }
                                                        }
                                                    });
                                                } else {
                                                    logger.debug("Auto-accept is OFF, so not accepting the order");
                                                    unacceptedOrderItems.add(cimpressItems.size());
                                                }
                                                order.put("bignameOrderId", orderId);
                                                order.put("accepted", orderAccepted[0]);
                                                saveCimpressOrderData(delegator, UtilMisc.toList(order));
                                            }
                                        }
                                        if (orderCreated) {
                                            TransactionUtil.commit();
                                        } else {
                                            if (MODE == OrderMode.SINGLE_ITEM) {
                                                String cimpressItemId = (String) ((List<Map<String, Object>>) order.get("items")).get(0).get("itemId");
                                                logger.error("BIGNAME order creation failed [Order ID: " + cimpressOrderId + ", Item ID: " + cimpressItemId);
                                            } else {
                                                logger.error("BIGNAME order creation failed [Order ID: " + cimpressOrderId);
                                            }
                                            if (UtilValidate.isNotEmpty(callResult.get("error"))) {
                                                logger.error((String) callResult.get("error"));
                                            }
                                            failedOrders.incr();

                                        }
                                    } catch (Exception e) {
                                        if (MODE == OrderMode.SINGLE_ITEM) {
                                            String itemId = UtilValidate.isNotEmpty(cimpressItems) ? (String) cimpressItems.get(0).get("itemId") : "";
                                            logger.error("An error occurred while importing Cimpress order item, skipping the item [Order ID: " + cimpressOrderId + ", Item ID: " + itemId + "]");
                                        } else {
                                            logger.error("An error occurred while importing Cimpress order, skipping the order [Order ID: " + cimpressOrderId + "]");
                                        }
                                        EnvUtil.reportError(e);
                                        Debug.logError("Error while importing Cimpress order item:  " + e, module);
                                    }
                                });
                            }
                        }
                    } catch (Exception e) {
                        logger.error("An error occurred while importing Cimpress order, skipping the order [Order ID: " + cimpressOrderId + "]");
                        failedOrders.incr();
                        failedOrderItems.add(orderItems.size());
                        EnvUtil.reportError(e);
                        Debug.logError("Error while importing Cimpress order:  " + e, module);
                    }

                });
                if(importedOrders.intValue() == 0) {
                    logger.error("Importing Cimpress orders failed");
                    importStatus = "FAILED";
                } else {
                    success = true;
                    logger.info("Importing Cimpress orders completed");
                }

                if(importStatus.equals("") && (failedOrders.intValue() > 0 || failedOrderItems.intValue() > 0 || importedOrdersWithWarnings.intValue() > 0)) {
                    importStatus = "COMPLETED_WARNING";
                } else {
                    importStatus = "COMPLETED";
                }
                logger.summary("Total Orders: " + totalOrders);
                logger.summary("Orders Imported: " + importedOrders);
                if(importedOrdersWithWarnings.intValue() > 0) {
                    logger.summary("Orders Imported with warning(s): " + importedOrdersWithWarnings);
                }
                logger.summary("Orders Failed: " + failedOrders);
                logger.summary("Total OrderItems: " + totalOrderItems);
                logger.summary("Order Items Imported: " + importedOrderItems);
                logger.summary("Order Items Failed: " + failedOrderItems);
                logger.summary("Order Items Accepted: " + acceptedOrderItems);
                logger.summary("Order Items Unaccepted: " + unacceptedOrderItems);
                if (sendEmail[0]) {
                    sendEmail(dispatcher, emailSubject, emailMessage.toString());
                }
            }
        } catch (Exception e) {
            importStatus = "FAILED";
            logger.error("An error occurred while importing NEW Cimpress notifications");
            EnvUtil.reportError(e);
            Debug.logError("Error while importing NEW Cimpress notifications:  " + e, module);
        }

        result.put("logger", logger.extract(level));
        result.put("importStatus", importStatus);
        result.put("success", success);
        logger.print(BlackBox.Level.SUMMARY);
        return result;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> getOrderFromCimpress(Delegator delegator, String orderId, List<Map<String, Object>> orderItems, BlackBox logger) {
        logger.info("Getting order from Cimpress [Order ID: " + orderId + "]");
        Map<String, Object> order        = new HashMap<>();
        List<Map<String, Object>> items  = new ArrayList<>();
        logger.debug("Getting order attributes from Cimpress [Order ID: " + orderId + "]");
        Map<String, Object> orderAttributes = CimpressUtil.getOrderDetails(new GetOrderDetailsRequest(orderId));
        Map<String, Object> transferPrice = (Map<String, Object>) orderAttributes.get("transferPrice");
        if(UtilValidate.isEmpty(orderAttributes)) {
            logger.warning("Unable to get order attributes from Cimpress [Order ID: " + orderId + "]");
            logger.warning("Skipping Cimpress Order [Order ID: " + orderId + "]");
        } else {
            logger.debug("Got order attributes from Cimpress [Order ID: " + orderId + "]");
            boolean[] surePost = {false};
            boolean[] priceMismatch = {false};
            orderItems.forEach(orderItem -> {
                String itemId = (String) orderItem.get("itemId");


                logger.debug("Getting order item details from Cimpress [Item ID: " + itemId + "]");
                Map<String, Object> itemDetails = CimpressUtil.getItemDetails(new GetItemDetailsRequest(itemId));

                if (UtilValidate.isEmpty(itemDetails)) {
                    logger.warning("Unable to get order item details from Cimpress [Item ID: " + itemId + "]");
                    logger.warning("Skipping Cimpress order item [Item ID: " + itemId + "]");
                } else {
                    Map<String, Object> item = new HashMap<>();
                    logger.debug("Got order item details from Cimpress [Item ID: " + itemId + "]");

                    logger.debug("Getting order item artwork file from Cimpress [Item ID: " + itemId + "]");
                    Map<String, String> artwork = CimpressUtil.getArtWork(new GetArtworkRequest(itemId));
                    if(UtilValidate.isEmpty(artwork)) {
                        logger.warning("Unable to get order item artwork file from Cimpress [Item ID: " + itemId + "]");
                    } else {
                        logger.debug("Got order item artwork file from Cimpress [Item ID: " + itemId + "]");
                        item.put("artworkContentName", artwork.get("fileName"));
                        item.put("artworkContentPath", artwork.get("filePath"));
                    }

                    logger.debug("Getting order item preview file from Cimpress [Item ID: " + itemId + "]");
                    Map<String, String> preview = CimpressUtil.getPreview(new GetPreviewRequest(itemId));
                    if(UtilValidate.isEmpty(preview)) {
                        logger.warning("Unable to get order item preview file from Cimpress [Item ID: " + itemId + "]");
                    } else {
                        logger.debug("Got order item preview file from Cimpress [Item ID: " + itemId + "]");
                        item.put("previewContentName", preview.get("fileName"));
                        item.put("previewContentPath", preview.get("filePath"));
                    }

                    logger.debug("Getting order item plan from Cimpress [Item ID: " + itemId + "]");
                    Map<String, Object> plan = CimpressUtil.getItemPlan(new GetItemPlanRequest(itemId));
                    if(UtilValidate.isEmpty(plan)) {
                        logger.warning("Unable to get order item plan from Cimpress [Item ID: " + itemId + "]");
                    } else {
                        logger.debug("Got order item plan from Cimpress [Item ID: " + itemId + "]");
                        Map<String, Object> production = (Map<String, Object>) plan.get("production");
                        if(UtilValidate.isEmpty(plan.get("shipping"))) {
                            logger.warning("Unable to find shipping info for Cimpress order item, skipping the item [Item ID: " + itemId + "]");
                        } else {
                            Map<String, Object> shipping = (Map<String, Object>) plan.get("shipping");
                            Map<String, Object> carrierService = (Map<String, Object>) shipping.get("expectedCarrierService");

                            if(UtilValidate.isNotEmpty(production)) {
                                item.put("expectedProductionStartTime", production.get("expectedProductionStartTime"));
                            }
                            item.put("expectedShipTime", shipping.get("expectedShipTime"));
                            item.put("expectedCarrierService", carrierService.get("name"));

                            List<Map<String, Object>> deliveryDetails = (List<Map<String, Object>>) itemDetails.get("deliveryDetails");
                            item.put("itemId", itemId);
                            item.put("links", orderItem.get("links"));

                            Map<String, Object> product = (Map<String, Object>) itemDetails.get("product");
                            List<Map<String, Object>> attributes = (List<Map<String, Object>>) product.get("attributes");
                            item.put("product", product);
                            if(transferPrice == null || !transferPrice.containsKey(itemId) || UtilValidate.isEmpty(transferPrice.get(itemId)) || UtilValidate.isEmpty(((Map<String, Object>) transferPrice.get(itemId)).get("basePrice"))) {
                                logger.warning("Transfer Price is empty for Cimpress order item [Item ID: " + itemId + "]");
                                item.put("transferBasePrice", "");
                            } else {
                                item.put("transferBasePrice", ((Map<String, Object>) transferPrice.get(itemId)).get("basePrice").toString());
                            }
                            item.put("bignameSKU", attributes.stream().filter(e -> e.get("name").equals("BIG NAME SKU")).findFirst().get().get("value"));
                            item.put("orderedQuantity", itemDetails.get("orderedQuantity").toString());
                            item.put("deliveryType", deliveryDetails.stream().filter(e -> e.containsKey("type")).findFirst().get().get("type"));
                            priceMismatch[0] = priceMismatch[0] || !priceMatch(delegator, (String) item.get("bignameSKU"), (String) item.get("orderedQuantity"), (String)item.get("transferBasePrice"));
                            surePost[0] = surePost[0] || "UPS Sure Post".equalsIgnoreCase((String)item.get("expectedCarrierService"));
                            items.add(item);
                        }
                    }
                }
            });
            if (UtilValidate.isNotEmpty(items)) {
                logger.info("Got order from Cimpress [Order ID: " + orderId + "]");
                order.put("orderId", orderId);
                order.put("createdDate", orderAttributes.get("createdDate"));
                order.put("promisedArrivalDate", orderAttributes.get("promisedArrivalDate"));
                order.put("destinationAddress", orderAttributes.get("destinationAddress"));
                boolean restrictedAddress = isRestrictedAddress((Map<String, Object>) order.get("destinationAddress"));
                order.put("priceMismatch", priceMismatch[0]);
                order.put("restrictedAddress", restrictedAddress);
                order.put("surePost", surePost[0]);
                order.put("reviewFlag", surePost[0] || priceMismatch[0] || restrictedAddress);
                order.put("items", items);
            } else {
                logger.warning("Unable to get item details for any order items from Cimpress [Order ID: " + orderId + "]");
            }
        }
        return order;
    }

    public static Map<String, Object> getOpenCimpressOrders(Delegator delegator) throws Exception {
        List<Map<String, Object>> openOrders = new ArrayList<>();
        List<Map<String, Object>> cancelledOrders = new ArrayList<>();
        List<Map<String, Object>> changedOrders = new ArrayList<>();
        List<Map<String, Object>> pendingOrders = new ArrayList<>();
        Map<String, Object> ordersMap = new HashMap<>();
        List<GenericValue> orders = getCimpressOrders(delegator, UtilMisc.toMap("cimpressStatus", "OPEN"));
        orders.forEach(o -> {
            Map<String, Object> order = new HashMap<>();
            order.put("bignameOrderId", o.get("bignameOrderId"));
            order.put("cimpressOrderId", o.get("cimpressOrderId"));
            order.put("cimpressItemIds", o.get("cimpressItemIds"));
            order.put("cimpressStatus", o.get("cimpressStatus"));
            order.put("addressChanged", o.get("addressChanged"));
            order.put("shippingMethodChanged", o.get("shippingMethodChanged"));
            order.put("surePost", o.get("surePost"));
            order.put("priceMismatch", o.get("priceMismatch"));
            order.put("restrictedAddress", o.get("restrictedAddress"));
            order.put("cimpressOrderData", new Gson().fromJson((String) o.get("cimpressOrderData"), Map.class));
            if ("CANCELLED".equals(o.get("cimpressStatus"))) {
                cancelledOrders.add(order);
            } else {
                openOrders.add(order);
                if ("Y".equals(o.get("shippingMethodChanged")) || "Y".equals(o.get("addressChanged"))) {
                    changedOrders.add(order);
                }
                if ("Y".equals(o.get("pending"))) {
                    pendingOrders.add(order);
                }
            }
        });
        ordersMap.put("openOrders", openOrders);
        ordersMap.put("cancelledOrders", cancelledOrders);
        ordersMap.put("changedOrders", changedOrders);
        ordersMap.put("pendingOrders", pendingOrders);

        return ordersMap;
    }

    static Map<String, Object> setProductionStarted(Delegator delegator, LocalDispatcher dispatcher, Map<String, ? extends Object> context) {
        boolean success = false;
        Map<String, Object> result = new HashMap<>();
        List<Map<String, String>> items = new ArrayList<>();
        try {
            GenericValue cimpressOrder = getCimpressOrderData(delegator, (String)context.get("orderId"));
            if(UtilValidate.isNotEmpty(cimpressOrder)) {
                boolean setToProduction = true;
                if("ACCEPT_FAILED".equalsIgnoreCase(cimpressOrder.getString("cimpressStatus")) && !checkCimpressOrderStatus(cimpressOrder.getString("cimpressItemIds"), "accepted")) {
                    setToProduction = false;
                    String emailMessage = "The Cimpress order is not yet accepted, so can't set as production started for the item " +  cimpressOrder.get("cimpressItemIds") + " with Bigname OrderId: " + context.get("orderId") + ", Cimpress OrderId: " + cimpressOrder.get("cimpressOrderId");
                    sendEmail(dispatcher, "Cimpress - Setting Production Started Failed", emailMessage);
                }
                if (setToProduction) {
                    items.add(UtilMisc.toMap("itemId", cimpressOrder.getString("cimpressItemIds")));
                    success = CimpressUtil.setProductionStarted(new UpdateItemStatusRequest(items));
                    if (success) {
                        cimpressOrder.put("cimpressStatus", "PRODUCTION");
                    } else {
                        cimpressOrder.put("cimpressStatus", "PRODUCTION_FAILED");
                        String emailMessage = "An error occurred while setting production started for the item " +  cimpressOrder.get("cimpressItemIds") + " with Bigname OrderId: " + context.get("orderId") + ", Cimpress OrderId: " + cimpressOrder.get("cimpressOrderId");
                        sendEmail(dispatcher, "Cimpress - Setting Production Started Failed", emailMessage);
                    }
                    delegator.createOrStore(cimpressOrder);
                }
            }
        } catch (Exception e) {
            String emailMessage = "An error occurred while setting production started for Cimpress order with Bigname OrderId: " + context.get("orderId") + "]";
            sendEmail(dispatcher, "Cimpress - Setting Production Started Failed", emailMessage);
            EnvUtil.reportError(e);
            Debug.logError("Error while setting production started for Cimpress order:  " + e, module);
        }
        result.put("success", success);
        return result;
    }

    @SuppressWarnings("unchecked")
    static Map<String, Object> createShipment(Delegator delegator, LocalDispatcher dispatcher, Map<String, ? extends Object> context) {
        boolean success = false;
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> shipmentParams = new HashMap<>();
        List<Map<String, String>> items = new ArrayList<>();
        Map<String, String> item = new HashMap<>();
        try {
            GenericValue cimpressOrder = getCimpressOrderData(delegator, (String)context.get("orderId"));
            if(UtilValidate.isNotEmpty(cimpressOrder)) {
                Map<String, Object> cimpressOrderData = new Gson().fromJson(cimpressOrder.getString("cimpressOrderData"), Map.class);
                String quantity = (String) ((List<Map<String, Object>>)cimpressOrderData.get("items")).get(0).get("orderedQuantity");
                boolean createShipment = true;
                if(("ACCEPT_FAILED".equalsIgnoreCase(cimpressOrder.getString("cimpressStatus")) || "PRODUCTION_FAILED".equalsIgnoreCase(cimpressOrder.getString("cimpressStatus"))) && !checkCimpressOrderStatus(cimpressOrder.getString("cimpressItemIds"), "production")) {
                    createShipment = false;
                    String emailMessage = "The Cimpress order is not yet set as production started, so can't create Cimpress shipment for the item " +  cimpressOrder.get("cimpressItemIds") + " with Bigname OrderId: " + context.get("orderId") + ", Cimpress OrderId: " + cimpressOrder.get("cimpressOrderId");
                    sendEmail(dispatcher, "Cimpress - Create Shipment Failed", emailMessage);
                }
                if (createShipment) {
                    items.add(UtilMisc.toMap("itemId", cimpressOrder.getString("cimpressItemIds"), "quantity", quantity));
                    shipmentParams.put("items", items);

                    String trackingId = "";
                    String trackingUrl = "";
                    String carrierService = "";
                    GenericValue oISG = EntityQuery.use(delegator).from("OrderItemShipGroup").where("orderId", (String) context.get("orderId"), "shipGroupSeqId", "00001").queryOne();
                    if(oISG != null) {
                        carrierService = getCarrierServiceId(oISG.getString("shipmentMethodTypeId"));
                        GenericValue oISGA = EntityQuery.use(delegator).from("OrderItemShipGroupAssoc").where("orderId", (String) context.get("orderId"), "orderItemSeqId", "00001", "shipGroupSeqId", "00001").queryOne();
                        if(oISGA != null && UtilValidate.isNotEmpty(oISGA.getString("trackingNumber"))) {
                            trackingId = oISGA.getString("trackingNumber");
                            trackingUrl = EnvUtil.getTrackingURL(trackingId);
                        }
                    }

                    shipmentParams.put("trackingId", trackingId);
                    shipmentParams.put("trackingUrl", trackingUrl);
                    shipmentParams.put("carrierService", carrierService);
                    success = CimpressUtil.createShipment(new CreateShipmentRequest(shipmentParams));
                    Debug.log("Cimpress - Shipment Success: " + shipmentParams.toString());
                    if (success) {
                        cimpressOrder.put("cimpressStatus", "SHIPPED");
                    } else {
                        cimpressOrder.put("cimpressStatus", "SHIPMENT_FAILED");
                        String emailMessage = "An error occurred while creating Cimpress shipment for the item " +  cimpressOrder.get("cimpressItemIds") + " with Bigname OrderId: " + context.get("orderId") + ", Cimpress OrderId: " + cimpressOrder.get("cimpressOrderId");
                        sendEmail(dispatcher, "Cimpress - Create Shipment Failed", emailMessage);
                    }
                    delegator.createOrStore(cimpressOrder);
                }
            }
        } catch (Exception e) {
            String emailMessage = "An error occurred while creating Cimpress shipment for Cimpress order with Bigname OrderId: " + context.get("orderId") + "]";
            sendEmail(dispatcher, "Cimpress - Create Shipment Failed", emailMessage);
            EnvUtil.reportError(e);
            Debug.logError("Error while creating Cimpress shipment:  " + e, module);
        }
        result.put("success", success);
        return result;
    }

    @SuppressWarnings("unchecked")
    static Map<String, Object> getCancelledOrdersFromCimpress(Delegator delegator, LocalDispatcher dispatcher) {
        Map<String, Object> result = new HashMap<>();
        String importStatus = "";
        Counter totalOrders = Counter.ZERO.auto();
        Counter totalOrderItems = Counter.ZERO.auto();
        Counter totalCancelledOrders = Counter.ZERO.auto();
        Counter totalCancelledOrderItems = Counter.ZERO.auto();
        Counter failedOrders = Counter.ZERO.auto();
        Counter failedOrderItems = Counter.ZERO.auto();
        BlackBox logger = new BlackBox();
        logger.info("Starting Cimpress cancellation import process");
        BlackBox.Level level = BlackBox.Level.INFO;
        Map<String, String> cimpressConfig = getCimpressConfig(delegator);
        try {
            level = BlackBox.Level.valueOf(UtilValidate.isNotEmpty(cimpressConfig.get(ConfigKey.LOGGER_LEVEL.key())) ? cimpressConfig.get(ConfigKey.LOGGER_LEVEL.key()) : "INFO");
        } catch(Exception e) {
            logger.warning("Invalid value for '" + ConfigKey.LOGGER_LEVEL.key() + "' config parameter, using default");
            EnvUtil.reportError(e);
        }
        boolean repeatNotification = "true".equalsIgnoreCase(cimpressConfig.get(ConfigKey.CANCELLATION_REPEAT_EMAIL.key()));

        boolean success = false;
        try {
            Map<String, Object> notificationResponse = CimpressUtil.getNotifications(new GetNotificationsRequest("CancellationRequest"));
            if(UtilValidate.isEmpty(notificationResponse) || !notificationResponse.containsKey("notifications")) {
                logger.error("Unable to get cancel order notification from Cimpress");
            } else if(UtilValidate.isEmpty(notificationResponse.get("notifications"))) {
                success = true;
                importStatus = "COMPLETED";
                logger.info("No cancel order notifications");
                logger.info("Importing Cimpress cancel orders completed");
            } else {

                String emailSubject = "Cimpress Order Cancellation";
                StringBuilder emailMessage = new StringBuilder("Cancellation Request received for following items");

                List<Map<String, Object>> notifications = (List<Map<String, Object>>) notificationResponse.get("notifications");
                logger.summary("Cancel order notifications : " + notifications.size());

                notifications.forEach(notification -> {

                    String cimpressOrderId = (String) ((Map<String, Object>) notification.get("order")).get("orderId");
                    logger.debug("Processing Cimpress cancellation for Order with ID: " + cimpressOrderId);
                    List<Map<String, Object>> orderItems = new ArrayList<>();
                    try {
                        orderItems.addAll(((List<Map<String, Object>>) notification.get("items")).stream().filter(e -> !checkCimpressOrderStatus((String) e.get("itemId"), "new")).collect(Collectors.toList()));
                        logger.debug("Found " + orderItems.size() + " cancelled order items for Cimpress order [Order ID: " + cimpressOrderId + "]");
                        totalOrderItems.add(orderItems.size());
                        if(UtilValidate.isNotEmpty(orderItems)) {
                            totalOrders.incr();
                        }
                        boolean[] orderHasCancellations = {false};
                        orderItems.forEach(item -> {
                            try {
                                GenericValue cimpressOrderDataGV = EntityQuery.use(delegator).from("CimpressOrderData").where("cimpressItemIds", item.get("itemId")).queryFirst();
                                boolean alreadyProcessed = "CANCELLED".equals(cimpressOrderDataGV.get("cimpressStatus"));
                                if (!alreadyProcessed || repeatNotification) {
                                    cimpressOrderDataGV.put("cimpressStatus", "CANCELLED");
                                    delegator.store(cimpressOrderDataGV);
                                    totalCancelledOrderItems.incr();
                                    orderHasCancellations[0] = true;
                                    logger.info("Cancelled Cimpress order data with  [BIGNAME Order ID: " + cimpressOrderDataGV.getString("bignameOrderId") + "]");
                                    emailMessage.append("<br/>Bigname OrderId: " + cimpressOrderDataGV.get("bignameOrderId") + ", Cimpress OrderId: " + cimpressOrderDataGV.get("cimpressOrderId") + ", Cimpress ItemId: " + item.get("itemId"));
                                }
                            } catch (Exception e) {
                                logger.error("Failed to Set CimpressStatus Cancelled for Cimpress order [Order ID: " + cimpressOrderId + "], [Item ID: " + item.get("itemId") + "]");
                                failedOrderItems.incr();
                                emailMessage.append("<br/>Bigname OrderId: - , Cimpress OrderId: " + cimpressOrderId + " , Cimpress ItemId: " + item.get("itemId"));
                                EnvUtil.reportError(e);
                                Debug.logError("Error while setting Cimpress order as cancelled:  " + e, module);
                            }
                        });
                        if(UtilValidate.isNotEmpty(orderItems) && !orderHasCancellations[0]) {
                            failedOrders.incr();
                        }
                        if (orderHasCancellations[0]) {
                            sendEmail(dispatcher, emailSubject, emailMessage.toString());
                        }
                    } catch (Exception e) {
                        logger.error("An error occurred while importing CANCELLED Cimpress order notification, skipping the order [Order ID: " + cimpressOrderId + "]");
                        failedOrders.incr();
                        failedOrderItems.add(orderItems.size());
                        EnvUtil.reportError(e);
                        Debug.logError("Error while importing Cimpress order cancellation:  " + e, module);
                    }

                });
                if (totalOrders.intValue() == 0) {
                    success = true;
                    importStatus = "COMPLETED";
                    logger.info("No Cimpress order cancellations");
                    logger.info("Importing Cimpress cancel orders completed");
                } else if(totalCancelledOrderItems.intValue() == 0) {
                    importStatus = "FAILED";
                    logger.info("Importing Cimpress cancel orders failed");
                } else {
                    success = true;
                    logger.info("Importing Cimpress cancel orders completed");
                }

                if(importStatus.equals("") && (failedOrders.intValue() > 0 || failedOrderItems.intValue() > 0)) {
                    importStatus = "COMPLETED_WARNING";
                } else {
                    importStatus = "COMPLETED";
                }
                logger.summary("Total Orders: " + totalOrders);
                logger.summary("Total Order Items: " + totalOrderItems);
                logger.summary("Total Cancelled Orders: " + totalCancelledOrders);
                logger.summary("Total Cancelled Order Items: " + totalCancelledOrderItems);
                logger.summary("Failed Orders: " + failedOrders);
                logger.summary("Failed Order Items: " + failedOrderItems);
            }
        } catch (Exception e) {
            importStatus = "FAILED";
            logger.error("An error occurred while importing CANCELLED Cimpress notifications");
            EnvUtil.reportError(e);
            Debug.logError("Error while importing CANCELLED Cimpress notifications:  " + e, module);
        }
        result.put("logger", logger.extract(level));
        result.put("importStatus", importStatus);
        result.put("success", success);
        logger.print(level);
        return result;
    }

    @SuppressWarnings("unchecked")
    static Map<String, Object> getAddressChangesFromCimpress(Delegator delegator, LocalDispatcher dispatcher) {
        Map<String, Object> result = new HashMap<>();
        String importStatus = "";
        Counter totalOrders = Counter.ZERO.auto();
        Counter totalOrderItems = Counter.ZERO.auto();
        Counter totalChangedOrders = Counter.ZERO.auto();
        Counter totalChangedOrderItems = Counter.ZERO.auto();
        Counter failedOrders = Counter.ZERO.auto();
        Counter failedOrderItems = Counter.ZERO.auto();
        BlackBox logger = new BlackBox();
        logger.info("Starting Cimpress address change import process");
        BlackBox.Level level = BlackBox.Level.INFO;
        Map<String, String> cimpressConfig = getCimpressConfig(delegator);
        try {
            level = BlackBox.Level.valueOf(UtilValidate.isNotEmpty(cimpressConfig.get(ConfigKey.LOGGER_LEVEL.key())) ? cimpressConfig.get(ConfigKey.LOGGER_LEVEL.key()) : "INFO");
        } catch(Exception e) {
            logger.warning("Invalid value for '" + ConfigKey.LOGGER_LEVEL.key() + "' config parameter, using default");
            EnvUtil.reportError(e);
        }
        boolean repeatNotification = "true".equalsIgnoreCase(cimpressConfig.get(ConfigKey.ADDRESS_CHANGE_REPEAT_EMAIL.key()));
        boolean success = false;
        try {
            Map<String, Object> notificationResponse = CimpressUtil.getNotifications(new GetNotificationsRequest("ChangeRequest"));
            if(UtilValidate.isEmpty(notificationResponse) || !notificationResponse.containsKey("notifications")) {
                logger.error("Unable to get any address change notification from Cimpress");
            } else if(UtilValidate.isEmpty(notificationResponse.get("notifications"))) {
                success = true;
                importStatus = "COMPLETED";
                logger.info("No  address change notifications");
                logger.info("Importing Cimpress  address changes completed");
            } else {

                String emailSubject = "Cimpress Address Change";
                StringBuilder emailMessage = new StringBuilder("Address change request received for following items");

                List<Map<String, Object>> notifications = (List<Map<String, Object>>) notificationResponse.get("notifications");
                logger.summary("Address change notifications : " + notifications.size());

                notifications.forEach(notification -> {

                    String cimpressOrderId = (String) ((Map<String, Object>) notification.get("order")).get("orderId");
                    logger.debug("Processing Cimpress address change for Order with ID: " + cimpressOrderId);
                    List<Map<String, Object>> orderItems = new ArrayList<>();
                    try {
                        orderItems.addAll(((List<Map<String, Object>>) notification.get("items")));
                        logger.debug("Found " + orderItems.size() + " address change(s) for order item(s) for Cimpress order [Order ID: " + cimpressOrderId + "]");
                        totalOrderItems.add(orderItems.size());
                        totalOrders.add(orderItems.size());
                        Map<String, Object> changeRequest = (Map<String, Object>) notification.get("changeRequest");
                        if (UtilValidate.isNotEmpty(changeRequest)) {
                            Map<String, Object> deliveryChangeDetails = (Map<String, Object>) changeRequest.get("deliveryChangeDetails");
                            if (UtilValidate.isNotEmpty(deliveryChangeDetails)) {
                                Map<String, Object> destinationAddress = (Map<String, Object>) deliveryChangeDetails.get("destinationAddress");
                                orderItems.forEach(item -> {
                                    try {
                                        GenericValue cimpressOrderDataGV = EntityQuery.use(delegator).from("CimpressOrderData").where("cimpressItemIds", item.get("itemId")).queryFirst();
                                        boolean alreadyProcessed = "Y".equals(cimpressOrderDataGV.get("addressChanged"));
                                        if (!alreadyProcessed || repeatNotification) {
                                            cimpressOrderDataGV.put("addressChanged", "Y");
                                            cimpressOrderDataGV.put("newAddress", new Gson().toJson(destinationAddress));
                                            delegator.store(cimpressOrderDataGV);
                                            totalChangedOrders.incr();
                                            totalChangedOrderItems.incr();
                                            logger.info("Address changed for Cimpress order data with  [BIGNAME Order ID: " + cimpressOrderDataGV.getString("bignameOrderId") + "]");
                                            emailMessage.append("<br/>Bigname OrderId: " + cimpressOrderDataGV.get("bignameOrderId") + ", Cimpress OrderId: " + cimpressOrderDataGV.get("cimpressOrderId") + ", Cimpress ItemId: " + item.get("itemId"));
                                        }
                                    } catch (Exception e) {
                                        logger.error("Failed to process address change for Cimpress order [Order ID: " + cimpressOrderId + "], [Item ID: " + item.get("itemId") + "]");
                                        failedOrderItems.incr();
                                        failedOrders.incr();
                                        emailMessage.append("<br/>Bigname OrderId: - , Cimpress OrderId: " + cimpressOrderId + " , Cimpress ItemId: " + item.get("itemId"));
                                        EnvUtil.reportError(e);
                                        Debug.logError("Error while processing address changes for Cimpress order item:  " + e, module);
                                    }
                                });

                            }
                        }

                    } catch (Exception e) {
                        logger.error("An error occurred while importing Cimpress order address change notification, skipping the order [Order ID: " + cimpressOrderId + "]");
                        failedOrders.incr();
                        failedOrderItems.add(orderItems.size());
                        EnvUtil.reportError(e);
                        Debug.logError("Error while processing address changes for Cimpress order:  " + e, module);
                    }

                });
                if (UtilValidate.isNotEmpty(notifications)) {
                    sendEmail(dispatcher, emailSubject, emailMessage.toString());
                }
                if(totalChangedOrderItems.intValue() == 0) {
                    importStatus = "FAILED";
                    logger.info("Importing Cimpress order address changes failed");
                } else {
                    success = true;
                    logger.info("Importing Cimpress order address changes completed");
                }

                if(importStatus.equals("") && (failedOrders.intValue() > 0 || failedOrderItems.intValue() > 0)) {
                    importStatus = "COMPLETED_WARNING";
                } else {
                    importStatus = "COMPLETED";
                }
                logger.summary("Total Orders: " + totalOrders);
                logger.summary("Total Order Items: " + totalOrderItems);
                logger.summary("Total Address Changed Orders: " + totalChangedOrders);
                logger.summary("Total Address Changed Order Items: " + totalChangedOrderItems);
                logger.summary("Failed Orders: " + failedOrders);
                logger.summary("Failed Order Items: " + failedOrderItems);
            }
        } catch (Exception e) {
            importStatus = "FAILED";
            logger.error("An error occurred while importing Cimpress order address change notifications");
            EnvUtil.reportError(e);
            Debug.logError("Error while importing Cimpress order address change notifications:  " + e, module);
        }
        result.put("logger", logger.extract(level));
        result.put("importStatus", importStatus);
        result.put("success", success);
        logger.print(level);
        return result;
    }

    @SuppressWarnings("unchecked")
    static Map<String, Object> getShippingCarrierChangesFromCimpress(Delegator delegator, LocalDispatcher dispatcher) {
        Map<String, String> cimpressConfig = getCimpressConfig(delegator);
        Map<String, Object> result = new HashMap<>();
        boolean repeatNotification = "true".equalsIgnoreCase(cimpressConfig.get(ConfigKey.SHIPPING_CHANGE_REPEAT_EMAIL.key()));
        boolean success = false;
        String importStatus = "";
        Counter totalOpenOrders = Counter.ZERO.auto();
        Counter failedOrders = Counter.ZERO.auto();
        Counter shippingMethodChanges = Counter.ZERO.auto();
        Counter unchangedOrders = Counter.ZERO.auto();
        BlackBox logger = new BlackBox();
        BlackBox.Level level = BlackBox.Level.INFO;
        try {
            level = BlackBox.Level.valueOf(UtilValidate.isNotEmpty(cimpressConfig.get(ConfigKey.LOGGER_LEVEL.key())) ? cimpressConfig.get(ConfigKey.LOGGER_LEVEL.key()) : "INFO");
        } catch(Exception e) {
            logger.warning("Invalid value for '" + ConfigKey.LOGGER_LEVEL.key() + "' config parameter, using default");
            EnvUtil.reportError(e);
        }
        logger.info("Starting to import shipping carrier changes from Cimpress");
        try {
            String emailSubject = "Cimpress Shipping Method Changes";
            StringBuilder emailMessage = new StringBuilder("Shipping method changes received for the following Items");
            boolean[] sendEmail = {false};

            Map<String, List<GenericValue>> openOrders = getOrdersGroupedByCimpressOrderId(delegator, "OPEN");
            openOrders.forEach((cimpressOrderId,cimpressOrders) -> {
                totalOpenOrders.add(cimpressOrders.size());
                cimpressOrders.forEach(e -> {
                    String bignameOrderId = e.getString("bignameOrderId");
                    try {
                        Map<String, Object> cimpressOrderData = new Gson().fromJson((String) e.get("cimpressOrderData"), Map.class);
                        List<Map<String, Object>> cimpressOrderItems = (List<Map<String, Object>>) cimpressOrderData.get("items");

                        cimpressOrderItems.forEach(item -> {
                            logger.debug("Getting order item plan from Cimpress [Item ID: " + item.get("itemId") + "]");
                            String newShippingMethod = "";
                            try {
                                Map<String, Object> plan = CimpressUtil.getItemPlan(new GetItemPlanRequest((String) item.get("itemId")));
                                if (UtilValidate.isEmpty(plan)) {
                                    logger.warning("Unable to get order item plan from Cimpress [Item ID: " + item.get("itemId") + "]");
                                } else {
                                    logger.debug("Got order item plan from Cimpress [Item ID: " + item.get("itemId") + "]");

                                    Map<String, Object> shipping = (Map<String, Object>) plan.get("shipping");
                                    if (UtilValidate.isNotEmpty(shipping)) {
                                        Map<String, Object> carrierService = (Map<String, Object>) shipping.get("expectedCarrierService");
                                        String currentShippingMethodId = getShippingMethodId((String) item.get("expectedCarrierService"));
                                        String newShippingMethodId = getShippingMethodId((String) carrierService.get("name"));
                                        boolean shippingMethodChanged = !currentShippingMethodId.equals(newShippingMethodId);
                                        if (shippingMethodChanged) {
                                            logger.debug("Shipping Method changed for Cimpress order [Cimpress Order ID: " + cimpressOrderId + ", BIGNAME Order ID: " + bignameOrderId + "]");
                                            shippingMethodChanges.incr();
                                            newShippingMethod = (String) carrierService.get("name");
                                            sendEmail[0] = true;
                                            emailMessage.append("<br/>Shipping Method Changed for Bigname OrderId: " + bignameOrderId + ", Cimpress OrderId: " + cimpressOrderId + ", Cimpress ItemId: " + item.get("itemId"));
                                            try {
                                                logger.debug("Getting Cimpress order data for Cimpress order [BIGNAME Order ID: " + bignameOrderId + "]");
                                                GenericValue cimpressOrderDataGV = getCimpressOrderData(delegator, e.getString("bignameOrderId"));
                                                boolean alreadyProcessed = "Y".equals(cimpressOrderDataGV.get("shippingMethodChanged"));
                                                if (!alreadyProcessed || repeatNotification) {
                                                    cimpressOrderDataGV.put("newShippingMethod", newShippingMethod);
                                                    cimpressOrderDataGV.put("shippingMethodChanged", "Y");
                                                    logger.info("Shipping carrier changed for [Order ID: " + bignameOrderId + "]");
                                                    delegator.store(cimpressOrderDataGV);
                                                }
                                            } catch (Exception e1) {
                                                logger.error("An error occurred while updating shipping carrier change for Cimpress order data [Item ID: " + item.get("itemId") + " Order ID: " + bignameOrderId + "]");
                                                failedOrders.incr();
                                                EnvUtil.reportError(e1);
                                                Debug.logError("Error while updating shipping carrier change for Cimpress order data:  " + e1, module);
                                            }
                                        } else {
                                            unchangedOrders.incr();
                                        }
                                    } else {
                                        unchangedOrders.incr();
                                    }
                                }

                            } catch (Exception e1) {
                                logger.error("An error occurred while checking shipping carrier changes for Bigname Order [Order ID: " + bignameOrderId + " Item ID: " + item.get("itemId") +"]");
                                failedOrders.incr();
                                EnvUtil.reportError(e1);
                                Debug.logError("Error while checking shipping carrier change for Cimpress order:  " + e1, module);
                            }
                        });
                    } catch (Exception e1) {
                        logger.error("An error occurred while checking shipping carrier changes for Bigname Order [Order ID: " + bignameOrderId + "]");
                        logger.warning("Skipping Bigname Order [Order ID: " + bignameOrderId + "]");
                        failedOrders.incr();
                        EnvUtil.reportError(e1);
                        Debug.logError("Error while checking shipping carrier change for Cimpress order:  " + e1, module);
                    }
                });

            });
            if(totalOpenOrders.intValue() > 0 && totalOpenOrders.intValue() == failedOrders.intValue()) {
                logger.error("Importing Cimpress order shipping carrier changes failed");
                importStatus = "FAILED";
            } else {
                success = true;
                logger.info("Importing Cimpress order shipping carrier changes completed");
            }

            if(importStatus.equals("") && failedOrders.intValue() > 0) {
                importStatus = "COMPLETED_WARNING";
            } else {
                importStatus = "COMPLETED";
            }
            logger.summary("Total Open Orders: " + totalOpenOrders);
            logger.summary("Unchanged Orders: " + unchangedOrders);
            logger.summary("Failed Orders: " + failedOrders);
            logger.summary("Shipping Method Changes: " + shippingMethodChanges);
            if (sendEmail[0]) {
                sendEmail(dispatcher, emailSubject, emailMessage.toString());
            }
        } catch (Exception e) {
            logger.error("An error occurred while checking order shipping carrier modifications on Cimpress side");
            importStatus = "FAILED";
            EnvUtil.reportError(e);
            Debug.logError("Error while checking shipping carrier changes for OPEN Cimpress orders:  " + e, module);
        }
        result.put("logger", logger.extract(level));
        result.put("importStatus", importStatus);
        result.put("success", success);
        logger.print(level);
        return result;
    }

    private static Map<String, List<GenericValue>> getOrdersGroupedByCimpressOrderId(Delegator delegator, String cimpressStatus) throws Exception {
        Map<String, List<GenericValue>> ordersMap = new LinkedHashMap<>();

        List<GenericValue> openOrders = getCimpressOrders(delegator, UtilMisc.toMap("cimpressStatus", cimpressStatus));
        openOrders.forEach(e -> {
            String cimpressOrderId = e.getString("cimpressOrderId");
            if(ordersMap.containsKey(cimpressOrderId)) {
                ordersMap.get(cimpressOrderId).add(e);
            } else {
                ordersMap.put(cimpressOrderId, UtilMisc.toList(e));
            }
        });

        return ordersMap;
    }

    @SuppressWarnings("unchecked")
    private static boolean priceMatch(Delegator delegator, String cimpressProductId, String quantity, String price) {
        Map<String, String> cimpressConfig = getCimpressConfig(delegator);
        boolean priceCheckEnabled = "true".equalsIgnoreCase(cimpressConfig.get(ConfigKey.PRICE_CHECK.key()));
        if (!priceCheckEnabled) {
            return true;
        }
        boolean matches = false;
        try {
            Map<String, Object> cimpressProduct = getCimpressProduct(delegator, cimpressProductId);
            if (UtilValidate.isNotEmpty(cimpressProduct) && UtilValidate.isNotEmpty(price)) {
                Map<String, Object> priceMap = (Map<String, Object>) cimpressProduct.get("price");
                if (UtilValidate.isNotEmpty(priceMap.get(quantity))) {
                    BigDecimal bignamePrice = new BigDecimal((String) priceMap.get(quantity));
                    bignamePrice = bignamePrice.setScale(2, BigDecimal.ROUND_CEILING);
                    BigDecimal cimpressPrice = new BigDecimal(price);
                    cimpressPrice = cimpressPrice.setScale(2, BigDecimal.ROUND_CEILING);
                    matches = bignamePrice.equals(cimpressPrice);
                }
            }
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError("Error while performing price check of Cimpress order item:  " + e, module);
        }
        return matches;
    }

    private static boolean isRestrictedAddress(Map<String, Object> address) {
        return ShippingHelper.isUspsAddress(UtilMisc.toMap("address1", (String)address.get("street1"), "address2", (String)address.get("street2"), "city", (String)address.get("city"),"countryGeoId", (String)address.get("country")));
    }

    private static boolean hasAddressChanged(Map<String, Object> oldAddress, Map<String, Object> newAddress) {
        return !trim(oldAddress.get("firstName")).equals(trim(newAddress.get("firstName"))) || !trim(oldAddress.get("lastName")).equals(trim(newAddress.get("lastName")))
                || !trim(oldAddress.get("company")).equals(trim(newAddress.get("company"))) || !trim(oldAddress.get("street1")).equals(trim(newAddress.get("street1")))
                || !trim(oldAddress.get("street2")).equals(trim(newAddress.get("street2"))) || !trim(oldAddress.get("city")).equals(trim(newAddress.get("city")))
                || !trim(oldAddress.get("postalCode")).equals(trim(newAddress.get("postalCode"))) || !trim(oldAddress.get("phone")).equals(trim(newAddress.get("phone")))
                || !trim(oldAddress.get("stateOrProvince")).equals(trim(newAddress.get("stateOrProvince")))
                || !trim(oldAddress.get("country")).equals(trim(newAddress.get("country")));
    }

    private static Object trim(Object string) {
        return string == null ? "" : string instanceof String ? ((String)string).trim() : string;
    }
    private static GenericValue getCimpressOrderData(Delegator delegator, String bignameOrderId) throws Exception {
        return EntityQuery.use(delegator).from("CimpressOrderData").where("bignameOrderId", bignameOrderId).queryOne();
    }

    private static  List<GenericValue> getCimpressOrders(Delegator delegator, Map<String, Object> context) throws Exception {
        List<EntityCondition> conditionList = new ArrayList<>();
        if(context.containsKey("cimpressStatus")) {
            if("OPEN".equalsIgnoreCase((String)context.get("cimpressStatus"))) {
                conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL, "ORDER_SHIPPED") );
                conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL, "ORDER_CANCELLED"));
            } else if("ACCEPTED".equalsIgnoreCase((String)context.get("cimpressStatus"))) {
                conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL, "ORDER_SHIPPED") );
                conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL, "ORDER_CANCELLED"));
                conditionList.add(EntityCondition.makeCondition("cimpressStatus", EntityOperator.EQUALS, "ACCEPTED"));
            } else if("ACCEPT_FAILED".equalsIgnoreCase((String)context.get("cimpressStatus"))) {
                conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL, "ORDER_SHIPPED") );
                conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL, "ORDER_CANCELLED"));
                conditionList.add(EntityCondition.makeCondition("cimpressStatus", EntityOperator.EQUALS, "ACCEPT_FAILED"));
            } else if("PRODUCTION".equalsIgnoreCase((String)context.get("cimpressStatus"))) {
                conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL, "ORDER_SHIPPED") );
                conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL, "ORDER_CANCELLED"));
                conditionList.add(EntityCondition.makeCondition("cimpressStatus", EntityOperator.EQUALS, "PRODUCTION"));
            } else if("PRODUCTION_FAILED".equalsIgnoreCase((String)context.get("cimpressStatus"))) {
                conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL, "ORDER_SHIPPED") );
                conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL, "ORDER_CANCELLED"));
                conditionList.add(EntityCondition.makeCondition("cimpressStatus", EntityOperator.EQUALS, "PRODUCTION_FAILED"));
            }
        }

        if(UtilValidate.isNotEmpty(context.get("cimpressItemIds"))) {
            conditionList.add(EntityCondition.makeCondition("cimpressItemIds", EntityOperator.IN, context.get("cimpressItemIds")));
        }

        if(UtilValidate.isNotEmpty(context.get("bignameOrderIds"))) {
            conditionList.add(EntityCondition.makeCondition("bignameOrderId", EntityOperator.IN, context.get("bignameOrderIds")));
        }
        return EntityQuery.use(delegator).from("CimpressOrder").where(conditionList).queryList();
    }

    private static boolean checkCimpressOrderStatus(String itemId, String status) {
        boolean cimpressStatus = false;
        Map<String, Object> statusDetails = CimpressUtil.getItemStatus(new GetItemStatusRequest(itemId));
        if (UtilValidate.isNotEmpty(statusDetails) && statusDetails.containsKey(status)) {
            cimpressStatus = true;
        }
        return cimpressStatus;
    }

    private static  List<String> getAlreadyImportedItemIds(Delegator delegator,  List<Map<String, Object>> orderItems) throws Exception {
        List<String> alreadyImportedItemIds = new ArrayList<>();
        List<String> itemIdsToImport = new ArrayList<>();
        orderItems.forEach(e -> itemIdsToImport.add((String)e.get("itemId")));
        List<GenericValue> importedOrders = getCimpressOrders(delegator, UtilMisc.toMap("cimpressItemIds", itemIdsToImport));
        importedOrders.forEach(e -> alreadyImportedItemIds.add((String)e.get("cimpressItemIds")));
        return alreadyImportedItemIds;
    }

    @SuppressWarnings("unchecked")
    private static void saveCimpressOrderData(Delegator delegator, List<Map<String, Object>> orders) throws GenericEntityException, SQLException {
        SQLProcessor sqlProcessor = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));
        Timestamp ts = UtilDateTime.nowTimestamp();
        String INSERT = "INSERT INTO cimpress_order_data (bigname_order_id, cimpress_order_id, notification_id, cimpress_item_ids, cimpress_order_data, cimpress_status, address_changed, shipping_method_changed, pending, sure_post, price_mismatch, restricted_address, last_updated_stamp, last_updated_tx_stamp, created_stamp, created_tx_stamp) VALUES (? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = sqlProcessor.getConnection().prepareStatement(INSERT)) {
            for (Map<String, Object> order : orders) {
                statement.setString(1, (String) order.get("bignameOrderId"));
                statement.setString(2, (String) order.get("orderId"));
                statement.setString(3, (String) order.get("notificationId"));
                statement.setString(4, String.valueOf(((List<Map<String, Object>>) order.get("items")).get(0).get("itemId")));
                statement.setString(5, new Gson().toJson(order));
                statement.setString(6, (boolean) order.get("accepted") ? "ACCEPTED" : "ACCEPT_FAILED");
                statement.setString(7, "N");
                statement.setString(8, "N");
                statement.setString(9, (boolean) order.get("reviewFlag") ? "Y" : "N");
                statement.setString(10, (boolean) order.get("surePost") ? "Y" : "N");
                statement.setString(11, (boolean) order.get("priceMismatch") ? "Y" : "N");
                statement.setString(12, (boolean) order.get("restrictedAddress") ? "Y" : "N");
                statement.setTimestamp(13, ts);
                statement.setTimestamp(14, ts);
                statement.setTimestamp(15, ts);
                statement.setTimestamp(16, ts);
                statement.addBatch();
            }
            statement.executeBatch();
        }finally{
            if(sqlProcessor != null) {
                sqlProcessor.close();
            }
        }

    }

    @SuppressWarnings("unchecked")
    private static List<Map<String, Object>> splitOrders(Map<String, Object> order) {
        List<Map<String, Object>> orders = new ArrayList<>();
        if(MODE == OrderMode.SINGLE_ITEM) {
            Map<String, Object> _order = new HashMap<>(order);
            _order.remove("items");

            ((List<Map<String, Object>>)order.get("items")).forEach(e -> {
                Map<String, Object> __order = new HashMap<>(_order);
                List<Map<String, Object>> __items = new ArrayList<>();
                __items.add(e);
                __order.put("items", __items);
                orders.add(__order);
            });
        } else {
            orders.add(order);
        }

        return orders;
    }

    private static GenericValue getWebSite(Delegator delegator, String webSiteId) throws GenericEntityException {
        return EntityQuery.use(delegator).from("WebSite").where("webSiteId", webSiteId).cache(true).queryOne();
    }

    private static Map<String, Object> getCimpressProduct(Delegator delegator, String virtualBignameSKU)  {
        try {
            Map<String, Object> product = new HashMap<>();
            GenericValue gv = EntityQuery.use(delegator).from("CimpressProduct").where("cimpressProductId", virtualBignameSKU).queryOne();
            product.putAll(gv);
            String priceJson = gv.getString("price");
            product.put("price", new Gson().fromJson(priceJson, Map.class));
            return product;
        } catch (GenericEntityException e) {
            EnvUtil.reportError(e);
            Debug.logError("Error while trying to find Cimpress product:" + e, module);
        }
        return null;
    }

    private static String getShippingMethodId(String carrierService) {
        switch (carrierService.toUpperCase()) {

            case "UPS NEXT DAY AIR SAVER":
                return "NEXT_DAY_SAVER";

            case "UPS NEXT DAY AIR":
                return "NEXT_DAY_AIR";

            case "UPS 2ND DAY":
                return "SECOND_DAY_AIR";

            case "UPS THREE DAY":
                return "THREE_DAY_SELECT";

            case "UPS SURE POST":
                return "SURE_POST";

            default :
                return "GROUND";
        }
    }

    private static String getCarrierServiceId(String shippingMethodId) {
        switch (shippingMethodId.toUpperCase()) {

            case "NEXT_DAY_SAVER":
                return "recs:ups-next-day-air-saver";

            case "NEXT_DAY_AIR":
                return "recs:ups-next-day-air";

            case "SECOND_DAY_AIR":
                return "recs:ups-second-day";

            case "THREE_DAY_SELECT":
                return "recs:ups-three-day-select";

            case "SURE_POST":
                return "recs:ups-sure-post";

            default :
                return "recs:ups-ground";
        }
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> createOrder(Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> order) throws Exception {
        String webSiteId = "envelopes";
        String catalogId = "AECatalog";
        Map<String, String> cimpressConfig = getCimpressConfig(delegator);
        String partyId = cimpressConfig.get(ConfigKey.PARTY_ID.key());
        String orderNote = cimpressConfig.get(ConfigKey.ORDER_NOTE.key());
        boolean netsuiteExport =  "true".equalsIgnoreCase(cimpressConfig.get(ConfigKey.NETSUITE_EXPORT.key()));
        String paymentMethodTypeId = "EXT_NET30"; //Default
        String orderType = "SALES_ORDER";

        boolean success = false;
        Map<String, Object> result = new HashMap<>();
        GenericValue webSite = getWebSite(delegator, "envelopes");
        String productStoreId = webSite.getString("productStoreId");
        GenericValue productStore = ProductStoreWorker.getProductStore(productStoreId, delegator);
        Locale locale = UtilMisc.ensureLocale(productStore.getString("defaultLocaleString"));
        String currencyUom = productStore.getString("defaultCurrencyUomId");
        boolean areOrderItemsExploded = productStore.get("explodeOrderItems") != null && productStore.getBoolean("explodeOrderItems");

        String emailAddress = PartyHelper.getPrimaryEmailAddress(delegator, partyId);
        GenericValue userLogin = PartyHelper.getUserLogin(delegator, emailAddress);

        int shipGroupIndex = 0;
        String carrierPartyId = "UPS";

        ShoppingCart cart = new ShoppingCart(delegator, productStoreId, webSiteId, locale, currencyUom, null, null);

        cart.setIgnoreProductPriceLookup(true);
        if(UtilValidate.isNotEmpty(orderNote)) {
            cart.addOrderNote(orderNote);
        }
        cart.setUserLogin(userLogin, dispatcher);
        cart.setOrderType(orderType);
        cart.setChannelType(EnvUtil.getSalesChannelEnumId(webSiteId));
        cart.setCarrierPartyId(shipGroupIndex, carrierPartyId);

        String externalId = (String) order.get("orderId");
        Timestamp orderDate = UtilDateTime.toTimestamp(toDate((String) order.get("createdDate")));
        cart.setOrderDate(orderDate);
        GenericValue salesTax = delegator.makeValue("OrderAdjustment", UtilMisc.toMap("orderAdjustmentTypeId", "FEE", "shipGroupSeqId", "00001", "description", "Tax", "amount", BigDecimal.ZERO, "createdDate", UtilDateTime.nowTimestamp()));
        cart.addAdjustment(salesTax);
        ShoppingCartHelper cartHelper = new ShoppingCartHelper(delegator, dispatcher, cart);

        List<Map<String, Object>> items = (List<Map<String, Object>>) order.get("items");

        for (Map<String, Object> item : items) {
            Map<String, Object> envPriceCalcAttributes = new HashMap<>();
            Map<String, Object> envArtworkAttributes = new HashMap<>();
            Map<String, Object> envQuoteAttributes = new HashMap<>();
            Map<String, Object> envQuantityAttributes = new LinkedHashMap<>();

            Map<String, Object> cimpressProduct = getCimpressProduct(delegator, (String) item.get("bignameSKU"));
            if (UtilValidate.isEmpty(cimpressProduct)) {
                result.put("success", false);
                result.put("error", "Unable to find Cimpress Product with ID: " + item.get("bignameSKU"));
                return result;
            }
            String shipmentMethodTypeId = getShippingMethodId((String) item.get("expectedCarrierService"));
            cart.setShipmentMethodTypeId(shipGroupIndex, shipmentMethodTypeId);
            Map<String, Object> itemContext = new HashMap<>();
            itemContext.put("partyId", partyId);
            itemContext.put("quantity", item.get("orderedQuantity"));
            if (UtilValidate.isNotEmpty(item.get("artworkContentName"))) {
                itemContext.put("artworkSource", "ART_UPLOADED");
                itemContext.put("fileName[]", item.get("artworkContentName"));
                itemContext.put("filePath[]", item.get("artworkContentPath"));
            } else {
                itemContext.put("fileName[]", "");
                itemContext.put("filePath[]", "");
                itemContext.put("artworkSource", "ART_NOT_RECEIVED");
            }
            itemContext.put("colorsFront", cimpressProduct.get("colorsFront"));
            itemContext.put("isRush", "true");
            itemContext.put("isProduct", "true");
            itemContext.put("add_product_id", cimpressProduct.get("bignameProductId"));

            itemContext.put("isFolded", "false");
            itemContext.put("addresses", "0");
            itemContext.put("templateId", "");
            itemContext.put("isFullBleed", "false");
            itemContext.put("whiteInkBack", "false");
            itemContext.put("whiteInkFront", "false");
            itemContext.put("colorsBack", cimpressProduct.get("colorsBack"));
            itemContext.put("cuts", "0");
            itemContext.put("fileOrder[]", "");
            itemContext.put("fileOrderItem[]", "");

            Map<String, Object> priceMap = (Map<String, Object>) cimpressProduct.get("price");
            if (priceMap.containsKey(item.get("orderedQuantity"))) {
                itemContext.put("price", priceMap.get(item.get("orderedQuantity")));
            }

            envPriceCalcAttributes = CartHelper.buildPriceContext(itemContext);
            envArtworkAttributes = CartHelper.buildArtworkContext(itemContext);
            envQuoteAttributes = CartHelper.buildQuoteContext(itemContext);

            Map<String, Object> priceReCalcAttributes = new HashMap<>(envPriceCalcAttributes);
            priceReCalcAttributes.put("quantity", itemContext.get("quantity"));
            priceReCalcAttributes.put("id", itemContext.get("add_product_id"));
            Map<String, Object> quantityPriceList = getProductPrice(cart, delegator, dispatcher, priceReCalcAttributes);
            Map<Integer, Map<String, Object>> priceList = (Map) quantityPriceList.get("priceList");
            for (Integer quantity : priceList.keySet()) {
                if (itemContext.containsKey("pricingRequest") && quantity.toString().equals(itemContext.get("quantity"))) {
                    itemContext.put("price", ((BigDecimal) priceList.get(quantity).get("price")).toPlainString());
                    envPriceCalcAttributes = CartHelper.buildPriceContext(itemContext);
                    priceReCalcAttributes.put("price", (BigDecimal) priceList.get(quantity).get("price"));
                }
                envQuantityAttributes.put(quantity.toString(), priceList.get(quantity).get("price"));
            }

            itemContext.put("envPriceCalcAttributes", envPriceCalcAttributes);
            itemContext.put("envArtworkAttributes", envArtworkAttributes);
            itemContext.put("envQuoteAttributes", envQuoteAttributes);
            itemContext.put("envQuantityAttributes", envQuantityAttributes);

            String productId = (String) itemContext.remove("add_product_id");
            BigDecimal quantity = new BigDecimal((String) itemContext.remove("quantity"));
            quantity = quantity.setScale(UtilNumber.getBigDecimalScale("order.decimals"), UtilNumber.getBigDecimalRoundingMode("order.rounding"));
            Map<String, Object> attributes = null;

            for (int namesIdx = 0; namesIdx < ShoppingCartItem.attributeNames.length; namesIdx++) {
                if (attributes == null)
                    attributes = new HashMap<>();
                if (itemContext.containsKey(ShoppingCartItem.attributeNames[namesIdx])) {
                    attributes.put(ShoppingCartItem.attributeNames[namesIdx], itemContext.get(ShoppingCartItem.attributeNames[namesIdx]));
                }
            }

            Map<String, String> orderItemAttributes = new HashMap<>();
            String orderItemAttributePrefix = EntityUtilProperties.getPropertyValue("order", "order.item.attr.prefix", delegator);
            for (Map.Entry<String, ? extends Object> entry : itemContext.entrySet()) {
                if (entry.getKey().contains(orderItemAttributePrefix) && UtilValidate.isNotEmpty(entry.getValue())) {
                    orderItemAttributes.put(entry.getKey().replaceAll(orderItemAttributePrefix, ""), entry.getValue().toString());
                }
            }
            cartHelper.addToCart(catalogId, null, null, productId, null,
                    null, null, (itemContext.containsKey("price") ? new BigDecimal((String)itemContext.get("price")) : BigDecimal.ZERO), BigDecimal.ZERO, quantity, null, null, null,
                    null, null,
                    null, null, null, null, itemContext, null);
        }

        GenericValue duty = delegator.makeValue("OrderAdjustment", UtilMisc.toMap("orderAdjustmentTypeId", "FEE", "shipGroupSeqId", "00001", "description", "Duty", "amount", BigDecimal.ZERO, "createdDate", UtilDateTime.nowTimestamp()));
        cart.addAdjustment(duty);

        GenericValue emailContactMech = PartyHelper.getMatchedEmailAddress(delegator, emailAddress, userLogin.getString("partyId"), "EMAIL_ADDRESS");
        if(emailContactMech == null) {
            emailContactMech = PartyHelper.createPartyContactMech(delegator, userLogin.getString("partyId"), "EMAIL_ADDRESS", emailAddress);
        }
        cart.setOrderEmailContactMechId(emailContactMech.getString("contactMechId")); //set the email address to the cart

        Map<String, Object> destination = (Map<String, Object>) order.get("destinationAddress");

        Map<String, String> shippingPhone = new HashMap<>();
        shippingPhone.put("countryCode", null);
        shippingPhone.put("areaCode", null);
        shippingPhone.put("contactNumber", (String) destination.get("phone"));


        Map<String, String> shippingAddress = new HashMap<>();

        shippingAddress.put("firstName", (String) destination.get("firstName"));
        shippingAddress.put("lastName", (String) destination.get("lastName"));
        shippingAddress.put("companyName", (String) destination.get("company"));
        shippingAddress.put("address1", (String) destination.get("street1"));
        shippingAddress.put("address2", (String) destination.get("street2"));
        shippingAddress.put("city", (String) destination.get("city"));
        shippingAddress.put("postalCode", (String) destination.get("postalCode"));
        shippingAddress.put("stateProvinceGeoId", (String) destination.get("stateOrProvince"));
        shippingAddress.put("countryGeoId", (String) destination.get("country"));
        shippingAddress.put("countryGeoId", "USA");  //TODO - Remove

        Map<String, String> billingPhone = new HashMap<>(shippingPhone);
        Map<String, String> billingAddress = new HashMap<>(shippingAddress);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 2);
        long jobStartTime = cal.getTimeInMillis();

        GenericValue shippingContactMech = PartyHelper.getMatchedPostalAddress(delegator, shippingAddress, partyId, "SHIPPING_LOCATION");
        GenericValue shippingTelecomMech = PartyHelper.getMatchedTelecomNumber(delegator, shippingPhone, partyId, "PHONE_SHIPPING");
        GenericValue billingContactMech = PartyHelper.getMatchedPostalAddress(delegator, billingAddress, partyId, "BILLING_LOCATION");
        GenericValue billingTelecomMech = PartyHelper.getMatchedTelecomNumber(delegator, billingPhone, partyId, "PHONE_BILLING");

        if(UtilValidate.isEmpty(shippingContactMech)) {
            shippingContactMech = PartyHelper.createPostalAddress(delegator, userLogin.getString("partyId"), shippingAddress, "SHIPPING_LOCATION");
        }
        if(UtilValidate.isEmpty(billingContactMech)) {
            if(!EnvUtil.areMapsEqual(shippingAddress, billingAddress)) {
                billingContactMech = PartyHelper.createPostalAddress(delegator, userLogin.getString("partyId"), billingAddress, "BILLING_LOCATION");
            } else {
                billingContactMech = shippingContactMech;
                PartyHelper.createPartyContactMechPurpose(delegator, userLogin.getString("partyId"), billingContactMech.getString("contactMechId"), "BILLING_LOCATION", false);
            }
        }

        if(UtilValidate.isEmpty(shippingTelecomMech)) {
            shippingTelecomMech = PartyHelper.createTelecomNumber(delegator, userLogin.getString("partyId"), shippingPhone, "PHONE_SHIPPING");
        }
        if(UtilValidate.isEmpty(billingTelecomMech)) {
            if(!EnvUtil.areMapsEqual(shippingPhone, billingPhone)) {
                billingTelecomMech = PartyHelper.createTelecomNumber(delegator, userLogin.getString("partyId"), billingPhone, "PHONE_BILLING");
            } else {
                billingTelecomMech = shippingTelecomMech;
                PartyHelper.createPartyContactMechPurpose(delegator, userLogin.getString("partyId"), billingTelecomMech.getString("contactMechId"), "PHONE_BILLING", false);
            }
        }

        if(UtilValidate.isNotEmpty(shippingContactMech)) {
            PartyHelper.createPartyContactMechAttribute(delegator, shippingContactMech.getString("contactMechId"), "isBlindShipment", "Y");

            if(UtilValidate.isNotEmpty(shippingTelecomMech)) {
                PartyHelper.createPartyContactMechAttribute(delegator, shippingContactMech.getString("contactMechId"), "telecomNumber", shippingTelecomMech.getString("contactMechId"));
            }
        }

        if(UtilValidate.isNotEmpty(billingContactMech) && UtilValidate.isNotEmpty(billingTelecomMech)) {
            PartyHelper.createPartyContactMechAttribute(delegator, billingContactMech.getString("contactMechId"), "telecomNumber", billingTelecomMech.getString("contactMechId"));
        }

        cart.setShippingContactMechId(0, shippingContactMech.getString("contactMechId"));

        cart.setExternalId(externalId);

        GenericValue payment = PartyHelper.createOfflinePayment(delegator, (UtilValidate.isNotEmpty(billingContactMech.get("contactMechId"))) ? (String) billingContactMech.get("contactMechId") : null, partyId, paymentMethodTypeId, null);
        cart.addPayment((UtilValidate.isEmpty(payment) && UtilValidate.isEmpty(payment.get("paymentMethodId"))) ? null : payment.getString("paymentMethodId"));


        CheckOutHelper checkOutHelper = new CheckOutHelper(dispatcher, delegator, cart);

        List<GenericValue> trackingCodeOrders = new ArrayList<>();
        String distributorId = null;
        String affiliateId = null;
        String visitId = null;

        Map<String, Object> callResult = checkOutHelper.createOrder(userLogin, distributorId, affiliateId, trackingCodeOrders, areOrderItemsExploded, visitId, webSiteId);
        if (callResult != null) {
            if (callResult.get(ModelService.RESPONSE_MESSAGE).equals(ModelService.RESPOND_SUCCESS)) {
                String orderId = cart.getOrderId();
                Map<String, Object> orderData = OrderHelper.getOrderData(delegator, orderId, false);
                List<Map> itemList = (List<Map>) orderData.get("items");
                if (UtilValidate.isNotEmpty(itemList)) {
                    for (Map item : itemList) {
                        if (ProductHelper.hasInventory(delegator, ((GenericValue) item.get("item")).getString("productId"), ((GenericValue) item.get("item")).getBigDecimal("quantity").longValue(), true)) {
                            OrderHelper.updateStatusForPrintItem(dispatcher, delegator, (GenericValue) item.get("item"));
                        } else {
                            if (!OrderHelper.isItemSample((GenericValue) item.get("item"), null)) {
                                dispatcher.runAsync("changeOrderItemStatus", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", ((GenericValue) item.get("item")).getString("orderItemSeqId"), "statusId", "ITEM_BACKORDERED", "userLogin", EnvUtil.getSystemUser(delegator)));
                            }
                        }
                        Timestamp dueDate = UtilDateTime.toTimestamp(toDate((String) items.get(Integer.parseInt(((GenericValue) item.get("item")).getString("orderItemSeqId")) - 1).get("expectedShipTime")));
                        OrderHelper.updateOrderItem(delegator, orderId, ((GenericValue) item.get("item")).getString("orderItemSeqId"), UtilMisc.<String, Object>toMap("dueDate", dueDate));
                    }
                }

                //if(UtilValidate.isNotEmpty(order.get("reviewFlag")) && (boolean) order.get("reviewFlag")) {
                //    ServiceHelper.runAsync(dispatcher, "changeOrderStatus", UtilMisc.toMap("orderId", orderId, "statusId", "ORDER_PENDING", "userLogin", EnvUtil.getSystemUser(delegator)), "", true);
                //} else if(netsuiteExport) {
                    dispatcher.schedule("exportOrder", UtilMisc.toMap("orderIds", UtilMisc.toList(orderId), "ignoreValidity", Boolean.TRUE, "userLogin", EnvUtil.getSystemUser(delegator)), jobStartTime);
                    dispatcher.schedule("exportOrderXML", UtilMisc.toMap("orderId", orderId, "userLogin", EnvUtil.getSystemUser(delegator)), jobStartTime);
                //}
                result.put("orderId", orderId);
                success = true;
            }
        }
        result.put("success", success);
        return result;
    }

    private static Map<String, Object> getProductPrice(ShoppingCart cart, Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> context) {
        Map<String, Object> jsonResponse = new HashMap<>();
        Integer quantity = (UtilValidate.isNotEmpty(context.get("quantity"))) ? context.get("quantity") instanceof String ? Integer.valueOf((String) context.get("quantity")) : ((BigDecimal) context.get("quantity")).intValue() : Integer.valueOf(0);
        Integer colorsFront = (UtilValidate.isNotEmpty(context.get("colorsFront"))) ? context.get("colorsFront") instanceof String ? Integer.valueOf((String) context.get("colorsFront")) : (Integer) context.get("colorsFront") : Integer.valueOf(0);
        Integer colorsBack = (UtilValidate.isNotEmpty(context.get("colorsBack"))) ? context.get("colorsBack") instanceof String ? Integer.valueOf((String) context.get("colorsBack")) : (Integer) context.get("colorsBack") : Integer.valueOf(0);
        Boolean isRush = (UtilValidate.isNotEmpty(context.get("isRush"))) ? context.get("isRush") instanceof String ? Boolean.valueOf((String) context.get("isRush")) : (Boolean) context.get("isRush") : false;
        Boolean whiteInkFront = (UtilValidate.isNotEmpty(context.get("whiteInkFront"))) ? context.get("whiteInkFront") instanceof String ? Boolean.valueOf((String) context.get("whiteInkFront")) : (Boolean) context.get("whiteInkFront") : false;
        Boolean whiteInkBack = (UtilValidate.isNotEmpty(context.get("whiteInkBack"))) ? context.get("whiteInkBack") instanceof String ? Boolean.valueOf((String) context.get("whiteInkBack")) : (Boolean) context.get("whiteInkBack") : false;
        Integer cuts = (UtilValidate.isNotEmpty(context.get("cuts"))) ? context.get("cuts") instanceof String ? Integer.valueOf((String) context.get("cuts")) : (Integer) context.get("cuts") : Integer.valueOf("0");
        Boolean isFolded = (UtilValidate.isNotEmpty(context.get("isFolded"))) ? context.get("isFolded") instanceof String ? Boolean.valueOf((String) context.get("isFolded")) : (Boolean) context.get("isFolded") : false;
        Boolean isFullBleed = (UtilValidate.isNotEmpty(context.get("isFullBleed"))) ? context.get("isFullBleed") instanceof String ? Boolean.valueOf((String) context.get("isFullBleed")) : (Boolean) context.get("isFullBleed") : false;
        Integer addresses = (UtilValidate.isNotEmpty(context.get("addresses"))) ? context.get("addresses") instanceof String ? Integer.valueOf((String) context.get("addresses")) : (Integer) context.get("addresses") : Integer.valueOf(0);
        BigDecimal customPrice = (UtilValidate.isNotEmpty(context.get("price"))) ? context.get("price") instanceof String ? new BigDecimal((String) context.get("price")) : (BigDecimal) context.get("price") : BigDecimal.ZERO;
        String templateId = (String) context.get("templateId");

        Map<Integer, Map> productPrice = new LinkedHashMap<Integer, Map>();
        try {
            GenericValue product = ProductHelper.getProduct(delegator, (String) context.get("id"));
            productPrice = ProductHelper.getProductPrice(cart, delegator, dispatcher, product, quantity, (String) context.get("partyId"), colorsFront, colorsBack, isRush, whiteInkFront, whiteInkBack, cuts, isFolded, isFullBleed, addresses, customPrice, templateId);
        } catch (GenericEntityException | GenericServiceException e) {
            EnvUtil.reportError(e);
            Debug.logError("Error while trying to retrieve json data: " + e, module);
        }

        if (UtilValidate.isEmpty(productPrice)) {
            jsonResponse.put("success", false);
        } else {
            jsonResponse.put("success", true);
        }
        jsonResponse.put("priceList", productPrice);
        return jsonResponse;
    }
    public static Date toDate(String utcDate) throws Exception {
        SimpleDateFormat utcFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        utcFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return utcFormatter.parse( utcDate);
    }

    public static void sendEmail(LocalDispatcher dispatcher, String subject, String message) {
        Map<String, String> cimpressConfig = getCimpressConfig();
        String emailAddress = cimpressConfig.get(ConfigKey.NOTIFICATION_EMAIL_ADDRESS.key());
        try {
            dispatcher.runSync("sendEmail", UtilMisc.toMap("email", emailAddress, "rawData", null, "data", UtilMisc.<String, String>toMap("subject", subject, "request", message), "messageType", "genericEmail", "webSiteId", "envelopes"));
        } catch (GenericServiceException e) {
            EnvUtil.reportError(e);
            Debug.logError("Error while sending Cimpress email notifications:  " + e, module);
        }

    }

    //TODO - For Demo Only
    static Map<String, Object> getOrders(Delegator delegator, LocalDispatcher dispatcher) throws Exception {
        return dispatcher.runSync("getNewOrdersFromCimpress", new HashMap<>(), 7200, true);
    }
    //TODO-06072018 #3 - testing  only
    static Map<String, Object> setProductionStarted(Delegator delegator, LocalDispatcher dispatcher) throws Exception {
        return dispatcher.runSync("setProductionStarted", UtilMisc.toMap("orderId", "QA1796351"), 7200, true);
    }

    //TODO-06072018 #3 - testing  only
    static Map<String, Object> createShipment(Delegator delegator, LocalDispatcher dispatcher) throws Exception {
        return dispatcher.runSync("createShipment", UtilMisc.toMap("orderId", "QA1796351"), 7200, true);
    }

    //TODO-06072018 #3 - testing  only
    static Map<String, Object> getShippingCarrierChangesFromCimpressTest(Delegator delegator, LocalDispatcher dispatcher) throws Exception {
        return dispatcher.runSync("getShippingCarrierChangesFromCimpress", new HashMap<>(), 7200, true);
    }

    //TODO-06072018 #3 - testing  only
    static Map<String, Object> getAddressChangesFromCimpressTest(Delegator delegator, LocalDispatcher dispatcher) throws Exception {
        return dispatcher.runSync("getAddressChangesFromCimpress", new HashMap<>(), 7200, true);
    }

    //TODO-06072018 #3 - testing  only
    static Map<String, Object> getCancelledOrdersFromCimpressTest(Delegator delegator, LocalDispatcher dispatcher) throws Exception {
        return dispatcher.runSync("getCancelledOrdersFromCimpress", new HashMap<>(), 7200, true);
    }

}