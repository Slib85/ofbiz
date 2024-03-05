import java.sql.ResultSet

import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.entity.jdbc.SQLProcessor;

String module = "dashboard.groovy";

SQLProcessor du = null;
String sqlCommand = null;
List orderList = new ArrayList();

Map<String, Object> dashData = new HashMap<>();

try {
    du = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));

    //GET THE LAST 30 DAYS WORTH OF ORDER DATA
    sqlCommand = "select date_format(entry_date,'%b %d'), count(order_id), sum(grand_total) as total from order_header where date(entry_date) >= date(date_sub(now(), interval 2 week)) group by date(entry_date) order by entry_date asc";
    Map<String, Object> data = new LinkedHashMap<>();

    ResultSet rs = null;
    rs = du.executeQuery(sqlCommand);
    if(rs != null) {
        while(rs.next()) {
            data.put(rs.getString(1), UtilMisc.toList(rs.getString(2), rs.getString(3)));
            if(rs.isLast()) {
                dashData.put("todayCurrent", UtilMisc.toList(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        }
    }

    dashData.put("currentPeriod", data);

    //GET THE LAST 30 DAYS WORTH OF ORDER DATA FROM PREVIOUS PERIOD
    sqlCommand = "select date_format(entry_date,'%b %d'), count(order_id), sum(grand_total) as total from order_header where date(entry_date) >= date(date_sub(date_sub(now(), interval 1 year), interval 2 week)) and date(entry_date) <= date(date_sub(now(), interval 1 year)) group by date(entry_date) order by entry_date asc";
    data = new LinkedHashMap<>();

    rs = null;
    rs = du.executeQuery(sqlCommand);
    if(rs != null) {
        while(rs.next()) {
            data.put(rs.getString(1), UtilMisc.toList(rs.getString(2), rs.getString(3)));
            if(rs.isLast()) {
                dashData.put("todayPrevious", UtilMisc.toList(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        }
    }

    dashData.put("previousPeriod", data);

    //GET TODAYS SALES AND STATES
    /*sqlCommand = "SELECT " +
            "    count(*), " +
            "    sum(oh.grand_total), " +
            "    pa.state_province_geo_id " +
            "FROM " +
            "    order_header oh " +
            "        INNER JOIN " +
            "    order_contact_mech ocm ON oh.order_id = ocm.order_id " +
            "        INNER JOIN " +
            "    contact_mech cm ON ocm.contact_mech_id = cm.contact_mech_id " +
            "        INNER JOIN " +
            "    postal_address pa ON ocm.contact_mech_id = pa.contact_mech_id " +
            "WHERE " +
            "    date(oh.entry_date) = date('" + UtilDateTime.nowTimestamp() + "') " +
            "    AND ocm.CONTACT_MECH_PURPOSE_TYPE_ID = 'BILLING_LOCATION' GROUP BY pa.state_province_geo_id";
    data = new LinkedHashMap<>();

    rs = null;
    rs = du.executeQuery(sqlCommand);
    if(rs != null) {
        while(rs.next()) {
            data.put("US-" + rs.getString(3), UtilMisc.toList(rs.getString(1), rs.getString(2)));
        }
    }

    dashData.put("stateData", data);

    //GET TODAYS PRODUCTS
    sqlCommand = "select product_id, item_description, sum(quantity) as total_quantity, sum(quantity) * 100 / (select sum(quantity) from order_item where date(created_stamp) = date(now())) as percent from order_item where date(created_stamp) = date(now()) group by product_id order by total_quantity desc limit 10";
    data = new LinkedHashMap<>();

    rs = null;
    rs = du.executeQuery(sqlCommand);
    if(rs != null) {
        while(rs.next()) {
            data.put(rs.getString(1), UtilMisc.toList(rs.getString(2), rs.getString(3), rs.getString(4)));
        }
    }

    dashData.put("productData", data);*/

} finally {
    du.close();
}

context.dashData = dashData;