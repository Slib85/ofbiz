#!/bin/sh

perl /usr/local/ofbiz/etc/price_desc.pl
perl /usr/local/ofbiz/etc/color_warehouse.pl
perl /usr/local/ofbiz/etc/elasticsearch_data.pl

/usr/local/ofbiz/etc/deploy_sli_feed.sh
/usr/local/ofbiz/etc/clearCache.sh