import java.sql.ResultSet;
import java.util.*;
import java.sql.Timestamp;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.apache.ofbiz.base.util.Debug;

import com.envelopes.order.*;
import com.envelopes.util.*;

import org.apache.ofbiz.entity.jdbc.SQLProcessor;
import org.apache.ofbiz.base.util.*;

String module = "orderList.groovy";

Map<String, Object> conditions = EnvUtil.getParameterMap(request);
List whereClause = new ArrayList();

if(UtilValidate.isNotEmpty(conditions)) {
    Iterator iter = conditions.entrySet().iterator();
    while(iter.hasNext()) {
        Map.Entry pairs = (Map.Entry) iter.next();

        if(((String) pairs.getKey()).equals("orderId") && ((String) pairs.getValue()).contains(",")) {
            whereClause.add("oh.order_id in (" + ((String) pairs.getValue()).replace(/\s/, "").replaceAll(/([a-zA-Z0-9]+)/, "'\$1'") + ")");
        } else if(((String) pairs.getKey()).equals("start")) {
            whereClause.add("oh.order_date > '" + EnvUtil.convertStringToTimestamp((String) pairs.getValue() + " 00:00:00.0") + "'");
        } else if(((String) pairs.getKey()).equals("end")) {
            whereClause.add("oh.order_date < '" + EnvUtil.convertStringToTimestamp((String) pairs.getValue() + " 23:59:00.0") + "'");
        } else if(((String) pairs.getKey()).equals("webSiteId")) {
            if(((String) pairs.getValue()).contains(",")) {
                String[] siteIds = ((String) pairs.getValue()).split(",");
                List channels = new ArrayList();
                for(String siteId : siteIds) {
                    channels.add("oh.sales_channel_enum_id = '" + EnvUtil.getSalesChannelEnumId(siteId) + "'");
                }
                whereClause.add("(" + channels.join(" OR ") + ")");
            } else {
                whereClause.add("oh.sales_channel_enum_id = '" + EnvUtil.getSalesChannelEnumId((String) pairs.getValue()) + "'");
            }
        } else {
            whereClause.add("oh.order_id = '" + (String) pairs.getValue() + "'");
        }
    }
}

if(UtilValidate.isEmpty(conditions)) {
    whereClause.add("oh.order_date >= '" + UtilDateTime.getDayStart(UtilDateTime.nowTimestamp()) + "'");
    whereClause.add("oh.order_date < '" + UtilDateTime.nowTimestamp() + "'");
}

SQLProcessor du = null;
String sqlCommand = null;
List orderList = new ArrayList();

try {
    du = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));

    sqlCommand = "SELECT " +
            "    oh.order_id, " +
            "    oh.grand_total, " +
            "    oh.order_date, " +
            "    oh.status_id, " +
            "    oi.is_rush_production, " +
            "    oi.artwork_source, " +
            "    cm.info_string, " +
            "    pa.to_name, " +
            "    smt.generic_description, " +
            "    CASE " +
            "        WHEN " +
            "            oia.attr_value = 0 " +
            "                OR oia.attr_value IS NULL " +
            "        THEN " +
            "            'N' " +
            "        ELSE 'Y' " +
            "    END AS addressing, " +
            "    oh.exported_date, " +
            "    pa.company_name, " +
            "    oi.reference_order_id, " +
            "    oi.product_id, " +
            "    oi.reference_quote_id, " +
            "    ce.assigned_to " +
            "FROM " +
            "    order_header oh " +
            "        INNER JOIN " +
            "    order_item oi ON oi.order_id = oh.order_id " +
            "        INNER JOIN " +
            "    order_contact_mech ocm ON oh.order_id = ocm.order_id " +
            "        INNER JOIN " +
            "    contact_mech cm ON ocm.contact_mech_id = cm.contact_mech_id " +
            "        INNER JOIN " +
            "    order_contact_mech ocm2 ON ocm2.order_id = oh.ORDER_ID " +
            "        INNER JOIN " +
            "    postal_address pa ON ocm2.contact_mech_id = pa.contact_mech_id " +
            "        INNER JOIN " +
            "    order_item_ship_group oisg ON oisg.order_id = oh.order_id " +
            "        INNER JOIN " +
            "    shipment_method_type smt ON smt.shipment_method_type_id = oisg.shipment_method_type_id " +
            "        LEFT JOIN " +
            "    custom_envelope ce ON oi.reference_quote_id = ce.quote_id " +
            "        LEFT JOIN " +
            "    order_item_attribute oia ON oia.order_id = oi.ORDER_ID " +
            "        AND oi.order_item_seq_id = oia.ORDER_ITEM_SEQ_ID " +
            "        AND OIA.attr_name = 'addresses' " +
            "WHERE " +
            whereClause.join(" AND ") +
            "        AND ocm.CONTACT_MECH_PURPOSE_TYPE_ID = 'ORDER_EMAIL' " +
            "        AND ocm2.CONTACT_MECH_PURPOSE_TYPE_ID = 'BILLING_LOCATION' " +
            "ORDER BY oh.order_date DESC, oh.order_id ASC, oi.order_item_seq_id ASC";

    //Debug.logInfo("Cmd:" + sqlCommand, module);
    ResultSet rs = null;
    rs = du.executeQuery(sqlCommand);
    if(rs != null) {
        String orderId = null;
        Integer numberOfItems = 0;
        Map<String, Object> data = new HashMap<String, Object>();
        while(rs.next()) {
            if (!orderId.equals(rs.getString(1))) {
                if (orderId != null) {
                    orderList.add(data);
                }
                orderId = rs.getString(1);
                data = new HashMap<String, Object>();
                numberOfItems = 0;
            }
            
            ArrayList paymentStatus = OrderHelper.getPaymentData(null, delegator, orderId);

            data.put("orderId", rs.getString(1));
            data.put("orderTotal", rs.getBigDecimal(2));
            data.put("orderDate", rs.getTimestamp(3));
            data.put("statusId", rs.getString(4));
            data.put("isRushProduction", rs.getString(5));
            data.put("email", rs.getString(7));
            data.put("billingName", rs.getString(8));
            data.put("billingCompany", rs.getString(12));
            data.put("shippingMethod", rs.getString(9));
            data.put("exportedDate", rs.getString(11));
            data.put("addressing", ((UtilValidate.isNotEmpty(data.get("addressing")) && !data.get("addressing")) || UtilValidate.isEmpty(data.get("addressing")) ? (rs.getString(10) != null && rs.getString(10).equals("Y")) : data.get("addressing")));
            data.put("isScene7", ((UtilValidate.isNotEmpty(data.get("isScene7")) && !data.get("isScene7")) || UtilValidate.isEmpty(data.get("isScene7")) ? (rs.getString(6) != null && rs.getString(6).equals("SCENE7_ART_ONLINE")) : data.get("isScene7")));
            data.put("isPrinted", ((UtilValidate.isNotEmpty(data.get("isPrinted")) && !data.get("isPrinted")) || UtilValidate.isEmpty(data.get("isPrinted")) ? (rs.getString(6) != null) : data.get("isPrinted")));
            data.put("isReorder", ((UtilValidate.isNotEmpty(data.get("isReorder")) && !data.get("isReorder")) || UtilValidate.isEmpty(data.get("isReorder")) ? (rs.getString(13) != null && rs.getString(13) != '') : data.get("isReorder")));
            data.put("numberOfItems", ++numberOfItems);
            data.put("paymentStatusDescription", (UtilValidate.isNotEmpty(paymentStatus.get(0)) && UtilValidate.isNotEmpty(paymentStatus.get(0).get("statusId")) ? paymentStatus.get(0).get("statusId") : ""));
            data.put("productId", rs.getString(14));
            data.put("quoteId", rs.getString(15));
            data.put("salesRep", rs.getString(16));
        }

        if (UtilValidate.isNotEmpty(data)) {
            orderList.add(data);
        }
    }
} finally {
    if(du != null) {
        du.close();
    }
}

request.setAttribute("saveResponse", true);
OrderEvents.getOrderQueue(request, response);
Map<String, Object> orderQueue = (Map<String, Object>) request.getAttribute("savedResponse");

context.put("orderList", orderList);
context.put("orderQueue", orderQueue.get("queues"));