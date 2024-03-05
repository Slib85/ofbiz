#!/bin/bash
#
# This script generates data for new OFBIZ
#

log() {
	echo `date "+%Y-%m-%d %H:%M:%S"` $1
}

updateCollation() {
	sed -i -r -e 's/latin1_general_(cs|ci)/utf8_unicode_ci/g;s/utf8_general_(cs|ci)/utf8_unicode_ci/g;s/latin1_swedish_(cs|ci)/utf8_unicode_ci/g' $1
	sed -i -e 's/DEFAULT CHARSET=utf8;/DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;/g;s/latin1/utf8/g' $1
	sed -i -r 's/COLLATE utf8_unicode_ci//g' $1
	sed -i 's/CHARACTER SET utf8/CHARACTER SET utf8 COLLATE utf8_unicode_ci/g' $1
}

temp_db_name="ofbiztemp"
db_name="ofbizupgrade"
# Prompt user for information
echo "Temporary database used will be: $temp_db_name"
echo "Final database used will be: $db_name"

echo "Database username: "
read temp_db_username
echo "You entered: $temp_db_username"

echo "Database password: "
read -s temp_db_password

log "Creating Temp Database: $temp_db_name"
echo "drop database if exists $temp_db_name;" | mysql -u$temp_db_username -p$temp_db_password
echo "create database $temp_db_name;" | mysql -u$temp_db_username -p$temp_db_password
log "Creating product data backup file on DB2 (Takes about 45 seconds)"
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'mysqldump -h db2 --disable-keys --no-autocommit --extended-insert --user=ofbiz_ae --password=keyuDr7p ofbiz_ae product product_assoc product_feature product_feature_type product_feature_appl product_price product_category product_category_rollup product_category_member product_category_role product_review> old.product.dump.sql'
log "Compressing product data backup file on DB2 (Takes about 15 seconds)"
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'tar -pczf old.product.dump.sql.tar.gz old.product.dump.sql'
log "Downloading product data  backup file from DB2 (Takes about 30 seconds)"
sshpass -p "t8ragaP6" scp skhan@db2.envelopes.com:~/old.product.dump.sql.tar.gz .

log "Creating promo data backup file on DB2"
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'mysqldump -h db2 --disable-keys --no-autocommit --extended-insert --user=ofbiz_ae --password=keyuDr7p ofbiz_ae product_promo product_store_promo_appl product_promo_action product_promo_cond product_promo_rule product_promo_code product_promo_use product_promo_code_email product_promo_code_party product_promo_product product_promo_category > old.product.promo.dump.sql'

log "Compressing promo data backup file on DB2 (Takes about 3 minutes)"
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'tar -pczf old.product.promo.dump.sql.tar.gz old.product.promo.dump.sql'

log "Downloading promo data backup file from DB2 (Takes about 2 minutes)"
sshpass -p "t8ragaP6" scp skhan@db2.envelopes.com:~/old.product.promo.dump.sql.tar.gz .

log "Creating user data backup file on DB2 (Takes about 4 minutes)"
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'mysqldump -h db2 --disable-keys --no-autocommit --extended-insert --user=ofbiz_ae --password=keyuDr7p ofbiz_ae role_type user_login party party_role person party_contact_mech contact_mech contact_mech_attribute telecom_number postal_address party_contact_mech_purpose user_login_security_group > old.user.dump.sql'

log "Compressing user data backup file on DB2 (Takes about 3 minutes)"
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'tar -pczf old.user.dump.sql.tar.gz old.user.dump.sql'

log "Downloading user data backup file from DB2 (Takes about 2 minutes)"
sshpass -p "t8ragaP6" scp skhan@db2.envelopes.com:~/old.user.dump.sql.tar.gz .

log "Creating list of users with orders (Takes about 10 seconds)"
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'echo "CREATE TABLE ORDER_TMP ( CREATED_BY varchar(250) DEFAULT NULL ) ENGINE=InnoDB DEFAULT CHARSET=utf8;" > old.created_by.dump.sql'
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'echo "CREATE INDEX id_index_created_by ON ORDER_TMP (created_by) USING BTREE;" >> old.created_by.dump.sql'
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'mysql -h db2 -uofbiz_ae -pkeyuDr7p -N -e "SELECT distinct CONCAT(\"INSERT INTO ORDER_TMP(created_by) values(\\\"\", created_by , \"\\\");\") FROM order_header where created_by is not null;" ofbiz_ae >> old.created_by.dump.sql'

log "Compressing users data backup file on DB2 (Takes about 3 minutes)"
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'tar -pczf old.created_by.dump.sql.tar.gz old.created_by.dump.sql'

log "Downloading list of users with orders backup file from DB2 (Takes about 30 seconds)"
sshpass -p "t8ragaP6" scp skhan@db2.envelopes.com:~/old.created_by.dump.sql.tar.gz .

log "Creating order data backup file on DB2 (Takes about 1 minute)"
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'mysqldump -h db2 --disable-keys --no-autocommit --extended-insert --user=ofbiz_ae --password=keyuDr7p ofbiz_ae order_role order_adjustment order_contact_mech order_header order_header_note order_item order_item_artwork order_item_artwork_comment order_item_attribute order_item_reorder order_item_ship_group order_item_ship_group_assoc order_payment_preference order_status > old.order.dump.sql'
log "Compressing order data backup file on DB2 (Takes about 1 minute)"
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'tar -pczf old.order.dump.sql.tar.gz old.order.dump.sql'
log "Downloading order data backup file from DB2 (Takes about 30 seconds)"
sshpass -p "t8ragaP6" scp skhan@db2.envelopes.com:~/old.order.dump.sql.tar.gz .

log "Creating payment data backup file on DB2 (Takes about 10 seconds)"
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'mysqldump -h db2 --disable-keys --no-autocommit --extended-insert --user=ofbiz_ae --password=keyuDr7p ofbiz_ae payment payment_method payment_gateway_response credit_card > old.payment.dump.sql'

log "Compressing product data backup file on DB2 (Takes about 10 seconds)"
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'tar -pczf old.payment.dump.sql.tar.gz old.payment.dump.sql'

log "Downloading product data  backup file from DB2 (Takes about 10 seconds)"
sshpass -p "t8ragaP6" scp skhan@db2.envelopes.com:~/old.payment.dump.sql.tar.gz .

log "Creating file data backup file on DB2 (Takes about 12 seconds)"
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'mysqldump -h db2 --disable-keys --no-autocommit --extended-insert --user=ofbiz_ae --password=keyuDr7p ofbiz_ae party_content order_item_artwork_content order_item_content content data_resource > old.file.dump.sql'

log "Compressing file data backup file on DB2 (Takes about 15 seconds)"
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'tar -pczf old.file.dump.sql.tar.gz old.file.dump.sql'

log "Downloading file data  backup file from DB2 (Takes about 12 seconds)"
sshpass -p "t8ragaP6" scp skhan@db2.envelopes.com:~/old.file.dump.sql.tar.gz .

log "Creating loyalty backup file on DB2 (Takes about 1 seconds)"
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'mysqldump -h db2 --disable-keys --no-autocommit --extended-insert --user=ofbiz_ae --password=keyuDr7p ofbiz_ae loyalty_points > old.loyalty.dump.sql'

log "Compressing loyalty backup file on DB2 (Takes about 1 seconds)"
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'tar -pczf old.loyalty.dump.sql.tar.gz old.loyalty.dump.sql'

log "Downloading loyalty backup file from DB2 (Takes about 1 seconds)"
sshpass -p "t8ragaP6" scp skhan@db2.envelopes.com:~/old.loyalty.dump.sql.tar.gz .

log "Creating Scene7 backup file on DB2 (Takes about 1 seconds)"
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'mysqldump -h db2 --disable-keys --no-autocommit --extended-insert --user=ofbiz_ae --password=keyuDr7p ofbiz_ae product_content scene7_design scene7_prod_assoc scene7_template scene7_template_attr scene7_template_category scene7_user_content > old.scene7.dump.sql'

log "Compressing Scene7 backup file on DB2 (Takes about 1 seconds)"
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'tar -pczf old.scene7.dump.sql.tar.gz old.scene7.dump.sql'

log "Downloading Scene7 backup file from DB2 (Takes about 1 seconds)"
sshpass -p "t8ragaP6" scp skhan@db2.envelopes.com:~/old.scene7.dump.sql.tar.gz .

log "Creating plate backup file on DB2 (Takes about 1 seconds)"
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'mysqldump -h db2 --disable-keys --no-autocommit --extended-insert --user=ofbiz_ae --password=keyuDr7p ofbiz_ae print_card print_plate print_press > old.plate.dump.sql'

log "Compressing plate backup file on DB2 (Takes about 1 seconds)"
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'tar -pczf old.plate.dump.sql.tar.gz old.plate.dump.sql'

log "Downloading plate backup file from DB2 (Takes about 1 seconds)"
sshpass -p "t8ragaP6" scp skhan@db2.envelopes.com:~/old.plate.dump.sql.tar.gz .

log "Creating custom envelope backup file on DB2 (Takes about 1 min)"
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'mysqldump -h db2 --disable-keys --no-autocommit --extended-insert --user=ofbiz_ae --password=keyuDr7p ofbiz_ae custom_envelope custom_envelope_contact custom_envelope_content custom_envelope_detail custom_envelope_price custom_envelope_window > old.custom.envelope.dump.sql'

log "Compressing plate backup file on DB2 (Takes about 1 seconds)"
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'tar -pczf old.custom.envelope.dump.sql.tar.gz old.custom.envelope.dump.sql'

log "Downloading plate backup file from DB2 (Takes about 1 seconds)"
sshpass -p "t8ragaP6" scp skhan@db2.envelopes.com:~/old.custom.envelope.dump.sql.tar.gz .

log "Creating seq backup file on DB2 (Takes about 1 seconds)"
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'mysqldump -h db2 --disable-keys --no-autocommit --extended-insert --user=ofbiz_ae --password=keyuDr7p ofbiz_ae sequence_value_item > old.seq.dump.sql'

log "Compressing seq backup file on DB2 (Takes about 1 seconds)"
sshpass -p "t8ragaP6" ssh skhan@db2.envelopes.com 'tar -pczf old.seq.dump.sql.tar.gz old.seq.dump.sql'

log "Downloading seq backup file from DB2 (Takes about 1 seconds)"
sshpass -p "t8ragaP6" scp skhan@db2.envelopes.com:~/old.seq.dump.sql.tar.gz .

log "Extracting product data backup file (Takes about 30 seconds)"
tar -xf old.product.dump.sql.tar.gz

log "Updating collation"
updateCollation "old.product.dump.sql"

log "Restoring product data backup into $temp_db_name (Takes about 9 minutes)"
(
	echo "SET AUTOCOMMIT=0;"
	echo "SET UNIQUE_CHECKS=0;"
	echo "SET FOREIGN_KEY_CHECKS=0;"
	cat old.product.dump.sql
	echo "SET FOREIGN_KEY_CHECKS=1;"
	echo "SET UNIQUE_CHECKS=1;"
	echo "SET AUTOCOMMIT=1;"
	echo "COMMIT;"
) | mysql -u$temp_db_username -p$temp_db_password $temp_db_name

log "Removing backup files"
rm old.product.dump.sql -rf
rm old.product.dump.sql.tar.gz -rf

log "Extracting promo data backup file (Takes about 1 minute)"
tar -xf old.product.promo.dump.sql.tar.gz

log "Updating collation (Takes about 1 min)"
updateCollation "old.product.promo.dump.sql"

log "Restoring promo data backup into $temp_db_name (Takes about 2 minutes)"
(
	echo "SET AUTOCOMMIT=0;"
	echo "SET UNIQUE_CHECKS=0;"
	echo "SET FOREIGN_KEY_CHECKS=0;"
	cat old.product.promo.dump.sql
	echo "SET FOREIGN_KEY_CHECKS=1;"
	echo "SET UNIQUE_CHECKS=1;"
	echo "SET AUTOCOMMIT=1;"
	echo "COMMIT;"
) | mysql -u$temp_db_username -p$temp_db_password $temp_db_name

log "Removing backup files"
rm old.product.promo.dump.sql -rf
rm old.product.promo.dump.sql.tar.gz -rf

log "Extracting user data backup file (Takes about 1 minute)"
tar -xf old.user.dump.sql.tar.gz

log "Updating collation (Takes about 10 min)"
updateCollation "old.user.dump.sql"

log "Restoring user data backup into $temp_db_name (Takes about 70 minutes)"
(
	echo "SET AUTOCOMMIT=0;"
	echo "SET UNIQUE_CHECKS=0;"
	echo "SET FOREIGN_KEY_CHECKS=0;"
	cat old.user.dump.sql
	echo "SET FOREIGN_KEY_CHECKS=1;"
	echo "SET UNIQUE_CHECKS=1;"
	echo "SET AUTOCOMMIT=1;"
	echo "COMMIT;"
) | mysql -u$temp_db_username -p$temp_db_password $temp_db_name

log "Removing backup files"
rm old.user.dump.sql -rf
rm old.user.dump.sql.tar.gz -rf

log "Extracting product data backup file (Takes about 30 seconds)"
tar -xf old.created_by.dump.sql.tar.gz

log "Updating collation (Takes about 1 min)"
updateCollation "old.created_by.dump.sql"

log "Restoring list of users with orders data backup into $temp_db_name (Takes about 2 minutes)"
(
	echo "SET AUTOCOMMIT=0;"
	echo "SET UNIQUE_CHECKS=0;"
	echo "SET FOREIGN_KEY_CHECKS=0;"
	cat old.created_by.dump.sql
	echo "SET FOREIGN_KEY_CHECKS=1;"
	echo "SET UNIQUE_CHECKS=1;"
	echo "SET AUTOCOMMIT=1;"
	echo "COMMIT;"
) | mysql -u$temp_db_username -p$temp_db_password $temp_db_name

log "Removing backup files"
rm old.created_by.dump.sql -rf
rm old.created_by.dump.sql.tar.gz -rf

log "Extracting order data backup file (Takes about 15 seconds)"
tar -xf old.order.dump.sql.tar.gz

log "Updating collation (Takes about 1 min)"
updateCollation "old.order.dump.sql"

log "Restoring order data backup into $temp_db_name (Takes about 32 minutes)"
(
	echo "SET AUTOCOMMIT=0;"
	echo "SET UNIQUE_CHECKS=0;"
	echo "SET FOREIGN_KEY_CHECKS=0;"
	cat old.order.dump.sql
	echo "SET FOREIGN_KEY_CHECKS=1;"
	echo "SET UNIQUE_CHECKS=1;"
	echo "SET AUTOCOMMIT=1;"
	echo "COMMIT;"
) | mysql -u$temp_db_username -p$temp_db_password $temp_db_name

log "Removing backup files"
rm old.order.dump.sql -rf
rm old.order.dump.sql.tar.gz -rf

log "Extracting product data backup file (Takes about 5 seconds)"
tar -xf old.payment.dump.sql.tar.gz

log "Updating collation (Takes about 1 min)"
updateCollation "old.payment.dump.sql"

log "Restoring product data backup into $temp_db_name (Takes about 2 minutes)"
(
	echo "SET AUTOCOMMIT=0;"
	echo "SET UNIQUE_CHECKS=0;"
	echo "SET FOREIGN_KEY_CHECKS=0;"
	cat old.payment.dump.sql
	echo "SET FOREIGN_KEY_CHECKS=1;"
	echo "SET UNIQUE_CHECKS=1;"
	echo "SET AUTOCOMMIT=1;"
	echo "COMMIT;"
) | mysql -u$temp_db_username -p$temp_db_password $temp_db_name

log "Removing backup files"
rm old.payment.dump.sql -rf
rm old.payment.dump.sql.tar.gz -rf

log "Extracting file data backup file (Takes about 2 seconds)"
tar -xf old.file.dump.sql.tar.gz

log "Updating collation (Takes about 2 min)"
updateCollation "old.file.dump.sql"

log "Restoring file data backup into $temp_db_name (Takes about 5 minutes)"
(
	echo "SET AUTOCOMMIT=0;"
	echo "SET UNIQUE_CHECKS=0;"
	echo "SET FOREIGN_KEY_CHECKS=0;"
	cat old.file.dump.sql
	echo "SET FOREIGN_KEY_CHECKS=1;"
	echo "SET UNIQUE_CHECKS=1;"
	echo "SET AUTOCOMMIT=1;"
	echo "COMMIT;"
) | mysql -u$temp_db_username -p$temp_db_password $temp_db_name

log "Removing backup files"
rm old.file.dump.sql -rf
rm old.file.dump.sql.tar.gz -rf

log "Extracting loyalty backup file (Takes about 1 seconds)"
tar -xf old.loyalty.dump.sql.tar.gz

log "Updating collation (Takes about 1 min)"
updateCollation "old.loyalty.dump.sql"

log "Restoring loyalty backup into $temp_db_name (Takes about 1 minutes)"
(
	echo "SET AUTOCOMMIT=0;"
	echo "SET UNIQUE_CHECKS=0;"
	echo "SET FOREIGN_KEY_CHECKS=0;"
	cat old.loyalty.dump.sql
	echo "SET FOREIGN_KEY_CHECKS=1;"
	echo "SET UNIQUE_CHECKS=1;"
	echo "SET AUTOCOMMIT=1;"
	echo "COMMIT;"
) | mysql -u$temp_db_username -p$temp_db_password $temp_db_name

log "Removing backup files"
rm old.loyalty.dump.sql -rf
rm old.loyalty.dump.sql.tar.gz -rf

log "Extracting Scene7 backup file (Takes about 1 seconds)"
tar -xf old.scene7.dump.sql.tar.gz

log "Updating collation (Takes about 1 min)"
updateCollation "old.scene7.dump.sql"

log "Restoring Scene7 backup into $temp_db_name (Takes about 1 minutes)"
(
	echo "SET AUTOCOMMIT=0;"
	echo "SET UNIQUE_CHECKS=0;"
	echo "SET FOREIGN_KEY_CHECKS=0;"
	cat old.scene7.dump.sql
	echo "SET FOREIGN_KEY_CHECKS=1;"
	echo "SET UNIQUE_CHECKS=1;"
	echo "SET AUTOCOMMIT=1;"
	echo "COMMIT;"
) | mysql -u$temp_db_username -p$temp_db_password $temp_db_name

log "Removing backup files"
rm old.scene7.dump.sql -rf
rm old.scene7.dump.sql.tar.gz -rf

log "Extracting plate backup file (Takes about 1 seconds)"
tar -xf old.plate.dump.sql.tar.gz

log "Updating collation (Takes about 1 min)"
updateCollation "old.plate.dump.sql"

log "Restoring plate backup into $temp_db_name (Takes about 1 seconds)"
(
	echo "SET AUTOCOMMIT=0;"
	echo "SET UNIQUE_CHECKS=0;"
	echo "SET FOREIGN_KEY_CHECKS=0;"
	cat old.plate.dump.sql
	echo "SET FOREIGN_KEY_CHECKS=1;"
	echo "SET UNIQUE_CHECKS=1;"
	echo "SET AUTOCOMMIT=1;"
	echo "COMMIT;"
) | mysql -u$temp_db_username -p$temp_db_password $temp_db_name

log "Removing backup files"
rm old.plate.dump.sql -rf
rm old.plate.dump.sql.tar.gz -rf

log "Extracting plate backup file (Takes about 1 seconds)"
tar -xf old.custom.envelope.dump.sql.tar.gz

log "Updating collation (Takes about 1 min)"
updateCollation "old.custom.envelope.dump.sql"

log "Restoring custom envelope backup into $temp_db_name (Takes about 1 seconds)"
(
	echo "SET AUTOCOMMIT=0;"
	echo "SET UNIQUE_CHECKS=0;"
	echo "SET FOREIGN_KEY_CHECKS=0;"
	cat old.custom.envelope.dump.sql
	echo "SET FOREIGN_KEY_CHECKS=1;"
	echo "SET UNIQUE_CHECKS=1;"
	echo "SET AUTOCOMMIT=1;"
	echo "COMMIT;"
) | mysql -u$temp_db_username -p$temp_db_password $temp_db_name

log "Removing backup files"
rm old.custom.envelope.dump.sql -rf
rm old.custom.envelope.dump.sql.tar.gz -rf

log "Extracting seq backup file (Takes about 1 seconds)"
tar -xf old.seq.dump.sql.tar.gz

log "Updating collation (Takes about 1 min)"
updateCollation "old.seq.dump.sql"

log "Restoring seq backup into $temp_db_name (Takes about 1 seconds)"
(
	echo "SET AUTOCOMMIT=0;"
	echo "SET UNIQUE_CHECKS=0;"
	echo "SET FOREIGN_KEY_CHECKS=0;"
	cat old.seq.dump.sql
	echo "SET FOREIGN_KEY_CHECKS=1;"
	echo "SET UNIQUE_CHECKS=1;"
	echo "SET AUTOCOMMIT=1;"
	echo "COMMIT;"
) | mysql -u$temp_db_username -p$temp_db_password $temp_db_name

log "Removing backup files"
rm old.seq.dump.sql -rf
rm old.seq.dump.sql.tar.gz -rf

log "Giving the product data a body massage (Takes about 10 minutes)"
cat FIX_PRODUCT.sql | mysql -u$temp_db_username -p$temp_db_password $temp_db_name

log "Giving the user data a body massage (Takes about 120 minutes)"
cat FIX_USERS.sql | mysql -u$temp_db_username -p$temp_db_password $temp_db_name

log "Giving the order data a body massage (Takes about 21 minutes)"
cat FIX_ORDERS.sql | mysql -u$temp_db_username -p$temp_db_password $temp_db_name

log "Giving the file data a body massage (Takes about 3 minutes)"
cat FIX_FILES.sql | mysql -u$temp_db_username -p$temp_db_password $temp_db_name

log "Giving the promo data a body massage (Takes about 1 minutes)"
cat FIX_PROMOS.sql | mysql -u$temp_db_username -p$temp_db_password $temp_db_name

log "Transfering data to new DB";
sed -e "s/\[:TARGET_DB:\]/$db_name/g;s/\[:SOURCE_DB:\]/$temp_db_name/g" TRANSFER_SCHEMAS.sql | mysql -u$temp_db_username -p$temp_db_password $temp_db_name

log "Done :)"
