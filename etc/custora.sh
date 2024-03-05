#!/bin/bash
rm -f /tmp/orders.csv
rm -f /tmp/order-items.csv
rm -f /tmp/users.csv;

(echo "select 'order_id', 'user_id', 'email_address', 'transaction_date', 'revenue', 'profit', 'company', 'name', 'address1', 'city', 'shipping_state', 'shipping_country', 'shipping_province', 'billing_state', 'billing_country', 'billing_province', 'channel', 'shipping_cost', 'ship_method', 'tax', 'payment_method' union all select oh.order_id as order_id,
        ul.party_id as user_id,
        cm.info_string as email_address,
        oh.created_stamp as transaction_date,
        oh.grand_total as revenue,
        '' as profit,
        pa.company_name as company,
        pa.to_name as name,
        pa.address1 as address1,
        pa.city as city,
        pa.state_province_geo_id as shipping_state,
        pa.country_geo_id as shipping_country,
        pa.state_province_geo_id as shipping_province,
        pa2.state_province_geo_id as billing_state,
        pa2.country_geo_id as billing_country,
        pa2.state_province_geo_id as billing_province,
        CASE
            WHEN oh.sales_channel_enum_id = 'ENV_SALES_CHANNEL' THEN 'Envelopes.com'
            ELSE 'ActionEnvelope.com'
        END as channel,
        (select oa.amount from order_adjustment oa where oa.order_id = oh.order_id and oa.order_adjustment_type_id = 'SHIPPING_CHARGES' LIMIT 1) as shipping_cost,
        (select oisg.shipment_method_type_id from order_item_ship_group oisg where oisg.order_id = oh.order_id LIMIT 1) as ship_method,
        (select oa.amount from order_adjustment oa where oa.order_id = oh.order_id and oa.order_adjustment_type_id = 'SALES_TAX' LIMIT 1) as tax,
        CASE
            WHEN (select opp.payment_method_type_id from order_payment_preference opp where opp.order_id = oh.order_id LIMIT 1) = 'CREDIT_CARD' THEN 'Credit Card'
            ELSE 'Check'
        END as payment_method
from order_header oh
inner join order_contact_mech ocm on oh.order_id = ocm.order_id
inner join order_contact_mech ocm2 on oh.order_id = ocm2.order_id
inner join order_contact_mech ocm3 on oh.order_id = ocm3.order_id
inner join user_login ul on oh.created_by = ul.user_login_id
inner join postal_address pa on ocm.contact_mech_id = pa.contact_mech_id
inner join contact_mech cm on ocm2.contact_mech_id = cm.contact_mech_id
inner join postal_address pa2 on ocm3.contact_mech_id = pa2.contact_mech_id
where   date(oh.created_stamp) >= '2005-01-01'
        and ocm.contact_mech_purpose_type_id = 'SHIPPING_LOCATION'
        and ocm2.contact_mech_purpose_type_id = 'ORDER_EMAIL'
        and ocm3.contact_mech_purpose_type_id = 'BILLING_LOCATION'
group by oh.order_id
INTO OUTFILE '/tmp/orders.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '\"'
LINES TERMINATED BY '\n';") | mysql -uofbiz -psuyeqUz7 ofbiz;

(echo "select 'order_id', 'user_id', 'email_address', 'transaction_date', 'product_id', 'price', 'category', 'product_name', 'brand', 'color', 'collection', 'paper_weight', 'texture', 'dimensions', 'quantity', 'rush', 'scene7', 'plain_or_printed' union all select oh.order_id as order_id,
        ul.party_id as user_id,
        cm.info_string as email_address,
        oh.created_stamp as transaction_date,
        oi.product_id as product_id,
        oi.unit_price as price,
        pc.description as category,
        p.product_name as product_name,
        (select pf.description from product_feature pf inner join product_feature_appl pfa on pf.product_feature_id = pfa.product_feature_id where pfa.product_id = p.product_id and pf.product_feature_type_id = 'COMPARE_TO_BRAND' LIMIT 1) as brand,
        (select pf.description from product_feature pf inner join product_feature_appl pfa on pf.product_feature_id = pfa.product_feature_id where pfa.product_id = p.product_id and pf.product_feature_type_id = 'COLOR' LIMIT 1) as color,
        (select pf.description from product_feature pf inner join product_feature_appl pfa on pf.product_feature_id = pfa.product_feature_id where pfa.product_id = p.product_id and pf.product_feature_type_id = 'COLLECTION' LIMIT 1) as collection,
        (select pf.description from product_feature pf inner join product_feature_appl pfa on pf.product_feature_id = pfa.product_feature_id where pfa.product_id = p.product_id and pf.product_feature_type_id = 'PAPER_WEIGHT' LIMIT 1) as paper_weight,
        (select pf.description from product_feature pf inner join product_feature_appl pfa on pf.product_feature_id = pfa.product_feature_id where pfa.product_id = p.product_id and pf.product_feature_type_id = 'PAPER_TEXTURE' LIMIT 1) as texture,
        (select pf.description from product_feature pf inner join product_feature_appl pfa on pf.product_feature_id = pfa.product_feature_id where pfa.product_id = p.product_id and pf.product_feature_type_id = 'SIZE' LIMIT 1) as dimensions,
        oi.quantity as quantity,
        CASE
            WHEN oi.is_rush_production = 'Y' THEN oi.is_rush_production
            ELSE 'N'
        END as rush,
        CASE
            WHEN oi.artwork_source = 'SCENE7_ART_ONLINE' or oi.artwork_source = 'ART_DESIGNED_ONLINE' THEN 'Y'
            ELSE 'N'
        END as scene7,
        CASE
            WHEN oi.artwork_source is null THEN 'Plain'
            ELSE 'Printed'
        END as plain_or_printed
from order_header oh
inner join order_item oi on oh.order_id = oi.order_id
inner join product p on oi.product_id = p.product_id
inner join product_category as pc on p.primary_product_category_id = pc.product_category_id
inner join order_contact_mech ocm on oh.order_id = ocm.order_id
inner join user_login ul on oh.created_by = ul.user_login_id
inner join contact_mech cm on ocm.contact_mech_id = cm.contact_mech_id
where   date(oh.created_stamp) >= '2005-01-01'
        and ocm.contact_mech_purpose_type_id = 'ORDER_EMAIL'
INTO OUTFILE '/tmp/order-items.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '\"'
LINES TERMINATED BY '\n';") | mysql -uofbiz -psuyeqUz7 ofbiz;

(echo "select 'user_id', 'email_address', 'is_email_opt_in', 'created_date', 'trade_type', 'non_taxable', 'loyalty_points' union all select distinct ul.party_id as user_id,
        cm.info_string as email_address,
        '' as is_email_opt_in,
        ul.created_stamp as created_date,
        CASE
            WHEN (select count(*) from party_role pr where pr.party_id = ul.party_id and pr.role_type_id IN ('WHOLESALER','WHOLESALER_ALGRA','WHOLESALER_PN')) > 0 THEN 'Trade'
            WHEN (select count(*) from party_role pr where pr.party_id = ul.party_id and pr.role_type_id = 'NON_PROFIT') > 0 THEN 'NonProfit'
            ELSE 'None'
        END as trade_type,
        CASE WHEN (select count(*) from party_role pr where pr.party_id = ul.party_id and pr.role_type_id = 'NON_TAXABLE') > 0 THEN 'Y' ELSE 'N' END as non_taxable,
        (select sum(oh2.grand_total) from order_header oh2 inner join user_login ul2 on oh2.created_by = ul2.user_login_id where ul2.party_id = ul.party_id and oh2.status_id = 'ORDER_SHIPPED' and oh2.entry_date > date_sub(now(), interval 12 month)) as loyalty_points
from order_header oh
inner join order_contact_mech ocm on oh.order_id = ocm.order_id
inner join user_login ul on oh.created_by = ul.user_login_id
inner join contact_mech cm on ocm.contact_mech_id = cm.contact_mech_id
where   date(oh.created_stamp) >= '2005-01-01'
        and ocm.contact_mech_purpose_type_id = 'ORDER_EMAIL'
group by ul.party_id
INTO OUTFILE '/tmp/users.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '\"'
LINES TERMINATED BY '\n';") | mysql -uofbiz -psuyeqUz7 ofbiz;

puturl=https://www.custora.com/import.xml
apikey=eyk2tWoboUM8wedudIWT
transaction_file=/tmp/orders.csv
user_file=/tmp/users.csv
order_items_file=/tmp/order-items.csv
curl "$puturl" -X PUT  \
  --form-string "api_key=$apikey" \
  -F "raw_transaction_file=@$transaction_file" \
  -F "raw_user_file=@$user_file" \
  -F "raw_order_items_file=@$order_items_file"