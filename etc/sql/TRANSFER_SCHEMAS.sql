/* TRANSFER DATA TO NEW SCHEMA */

SET FOREIGN_KEY_CHECKS=0;
SET AUTOCOMMIT=0;
SET UNIQUE_CHECKS=0;

TRUNCATE TABLE `[:TARGET_DB:]`.`product`;
TRUNCATE TABLE `[:TARGET_DB:]`.`product_feature`;
TRUNCATE TABLE `[:TARGET_DB:]`.`product_feature_appl`;
TRUNCATE TABLE `[:TARGET_DB:]`.`product_feature_type`;
TRUNCATE TABLE `[:TARGET_DB:]`.`product_category`;
TRUNCATE TABLE `[:TARGET_DB:]`.`product_category_rollup`;
TRUNCATE TABLE `[:TARGET_DB:]`.`product_category_member`;
TRUNCATE TABLE `[:TARGET_DB:]`.`product_category_role`;
TRUNCATE TABLE `[:TARGET_DB:]`.`product_price`;
TRUNCATE TABLE `[:TARGET_DB:]`.`user_login`;
TRUNCATE TABLE `[:TARGET_DB:]`.`party`;
TRUNCATE TABLE `[:TARGET_DB:]`.`party_role`;
TRUNCATE TABLE `[:TARGET_DB:]`.`person`;
TRUNCATE TABLE `[:TARGET_DB:]`.`party_contact_mech`;
TRUNCATE TABLE `[:TARGET_DB:]`.`contact_mech`;
TRUNCATE TABLE `[:TARGET_DB:]`.`contact_mech_attribute`;
TRUNCATE TABLE `[:TARGET_DB:]`.`telecom_number`;
TRUNCATE TABLE `[:TARGET_DB:]`.`postal_address`;
TRUNCATE TABLE `[:TARGET_DB:]`.`party_contact_mech_purpose`;
TRUNCATE TABLE `[:TARGET_DB:]`.`order_adjustment`;
TRUNCATE TABLE `[:TARGET_DB:]`.`order_contact_mech`;
TRUNCATE TABLE `[:TARGET_DB:]`.`order_header`;
TRUNCATE TABLE `[:TARGET_DB:]`.`order_header_note`;
TRUNCATE TABLE `[:TARGET_DB:]`.`order_item`;
TRUNCATE TABLE `[:TARGET_DB:]`.`order_item_artwork`;
TRUNCATE TABLE `[:TARGET_DB:]`.`order_item_artwork_comment`;
TRUNCATE TABLE `[:TARGET_DB:]`.`order_item_attribute`;
TRUNCATE TABLE `[:TARGET_DB:]`.`order_item_content`;
TRUNCATE TABLE `[:TARGET_DB:]`.`order_item_ship_group`;
TRUNCATE TABLE `[:TARGET_DB:]`.`order_item_ship_group_assoc`;
TRUNCATE TABLE `[:TARGET_DB:]`.`order_status`;
TRUNCATE TABLE `[:TARGET_DB:]`.`order_role`;
TRUNCATE TABLE `[:TARGET_DB:]`.`order_payment_preference`;
TRUNCATE TABLE `[:TARGET_DB:]`.`payment`;
TRUNCATE TABLE `[:TARGET_DB:]`.`payment_method`;
TRUNCATE TABLE `[:TARGET_DB:]`.`payment_gateway_response`;
TRUNCATE TABLE `[:TARGET_DB:]`.`print_card`;
TRUNCATE TABLE `[:TARGET_DB:]`.`print_press`;
TRUNCATE TABLE `[:TARGET_DB:]`.`print_plate`;
TRUNCATE TABLE `[:TARGET_DB:]`.`credit_card`;
TRUNCATE TABLE `[:TARGET_DB:]`.`loyalty_points`;
/* PROMO TABLES SHOULD NOT BE TRUNCATED, THEY HAVE EXISTING PROMOS IN IT */
/*TRUNCATE TABLE `[:TARGET_DB:]`.`product_promo_code_email`;
TRUNCATE TABLE `[:TARGET_DB:]`.`product_promo_code_party`;
TRUNCATE TABLE `[:TARGET_DB:]`.`product_promo_code`;
TRUNCATE TABLE `[:TARGET_DB:]`.`product_store_promo_appl`;
TRUNCATE TABLE `[:TARGET_DB:]`.`product_promo_product`;
TRUNCATE TABLE `[:TARGET_DB:]`.`product_promo_category`;
TRUNCATE TABLE `[:TARGET_DB:]`.`product_promo_cond`;
TRUNCATE TABLE `[:TARGET_DB:]`.`product_promo_action`;
TRUNCATE TABLE `[:TARGET_DB:]`.`product_promo_rule`;*/
TRUNCATE TABLE `[:TARGET_DB:]`.`product_review`;
TRUNCATE TABLE `[:TARGET_DB:]`.`product_content`;
TRUNCATE TABLE `[:TARGET_DB:]`.`scene7_design`;
TRUNCATE TABLE `[:TARGET_DB:]`.`scene7_design_attrib`;
TRUNCATE TABLE `[:TARGET_DB:]`.`scene7_prod_assoc`;
TRUNCATE TABLE `[:TARGET_DB:]`.`scene7_template`;
TRUNCATE TABLE `[:TARGET_DB:]`.`scene7_template_attr`;
TRUNCATE TABLE `[:TARGET_DB:]`.`scene7_template_category`;
TRUNCATE TABLE `[:TARGET_DB:]`.`scene7_user_content`;
TRUNCATE TABLE `[:TARGET_DB:]`.`custom_envelope`;
TRUNCATE TABLE `[:TARGET_DB:]`.`custom_envelope_contact`;
TRUNCATE TABLE `[:TARGET_DB:]`.`custom_envelope_content`;
TRUNCATE TABLE `[:TARGET_DB:]`.`custom_envelope_detail`;
TRUNCATE TABLE `[:TARGET_DB:]`.`custom_envelope_price`;
TRUNCATE TABLE `[:TARGET_DB:]`.`custom_envelope_window`;

INSERT INTO `[:TARGET_DB:]`.`product`
(`PRODUCT_ID`,                              `PRODUCT_TYPE_ID`,                          `PRIMARY_PRODUCT_CATEGORY_ID`,
`MANUFACTURER_PARTY_ID`,                    `FACILITY_ID`,                              `INTRODUCTION_DATE`,
`RELEASE_DATE`,                             `SUPPORT_DISCONTINUATION_DATE`,             `SALES_DISCONTINUATION_DATE`,
`SALES_DISC_WHEN_NOT_AVAIL`,                `INTERNAL_NAME`,                            `BRAND_NAME`,
`COMMENTS`,                                 `PRODUCT_NAME`,                             `DESCRIPTION`,
`LONG_DESCRIPTION`,                         `PRICE_DETAIL_TEXT`,                        `SMALL_IMAGE_URL`,
`MEDIUM_IMAGE_URL`,                         `LARGE_IMAGE_URL`,                          `DETAIL_IMAGE_URL`,
`ORIGINAL_IMAGE_URL`,                       `DETAIL_SCREEN`,                            `INVENTORY_MESSAGE`,
`REQUIRE_INVENTORY`,                        `QUANTITY_UOM_ID`,                          `QUANTITY_INCLUDED`,
`PIECES_INCLUDED`,                          `REQUIRE_AMOUNT`,                           `FIXED_AMOUNT`,
`AMOUNT_UOM_TYPE_ID`,                       `WEIGHT_UOM_ID`,                            `WEIGHT`,
`PRODUCT_WEIGHT`,                           `HEIGHT_UOM_ID`,                            `PRODUCT_HEIGHT`,
`SHIPPING_HEIGHT`,                          `WIDTH_UOM_ID`,                             `PRODUCT_WIDTH`,
`SHIPPING_WIDTH`,                           `DEPTH_UOM_ID`,                             `PRODUCT_DEPTH`,
`SHIPPING_DEPTH`,                           `DIAMETER_UOM_ID`,                          `PRODUCT_DIAMETER`,
`PRODUCT_RATING`,                           `RATING_TYPE_ENUM`,                         `RETURNABLE`,
`TAXABLE`,                                  `CHARGE_SHIPPING`,                          `AUTO_CREATE_KEYWORDS`,
`INCLUDE_IN_PROMOTIONS`,                    `IS_VIRTUAL`,                               `IS_VARIANT`,
`VIRTUAL_VARIANT_METHOD_ENUM`,              `ORIGIN_GEO_ID`,                            `REQUIREMENT_METHOD_ENUM_ID`,
`BILL_OF_MATERIAL_LEVEL`,                   `RESERV_MAX_PERSONS`,                       `RESERV2ND_P_P_PERC`,
`RESERV_NTH_P_P_PERC`,                      `CONFIG_ID`,                                `CREATED_DATE`,
`CREATED_BY_USER_LOGIN`,                    `LAST_MODIFIED_DATE`,                       `LAST_MODIFIED_BY_USER_LOGIN`,
`IN_SHIPPING_BOX`,                          `DEFAULT_SHIPMENT_BOX_TYPE_ID`,             `LOT_ID_FILLED_IN`,
`ORDER_DECIMAL_QUANTITY`,                   `LAST_UPDATED_STAMP`,                       `LAST_UPDATED_TX_STAMP`,
`CREATED_STAMP`,                            `CREATED_TX_STAMP`,                         `PARENT_PRODUCT_ID`,
`AVERAGE_CUSTOMER_RATING`,                  `META_TITLE`,                               `META_DESCRIPTION`,
`META_KEYWORD`,                             `COLOR_COUNT`,                              `PLAIN_PRICE_DESCRIPTION`,
`PRINT_PRICE_DESCRIPTION`,                  `IS_PRINTABLE`,                             `HAS_RUSH_PRODUCTION`,
`CARTON_QTY`,                               `HAS_COLOR_OPT`,                            `BIN_LOCATION`,
`IS_ASSEMBLY_ITEM`,                         `TAG_LINE`,                                 `ON_SALE`,
`COLOR_DESCRIPTION`)
SELECT
`product`.`PRODUCT_ID`,                     `product`.`PRODUCT_TYPE_ID`,                `product`.`PRIMARY_PRODUCT_CATEGORY_ID`,
`product`.`MANUFACTURER_PARTY_ID`,          `product`.`FACILITY_ID`,                    `product`.`INTRODUCTION_DATE`,
null,                                       `product`.`SUPPORT_DISCONTINUATION_DATE`,   `product`.`SALES_DISCONTINUATION_DATE`,
`product`.`SALES_DISC_WHEN_NOT_AVAIL`,      `product`.`INTERNAL_NAME`,                  `product`.`BRAND_NAME`,
`product`.`COMMENTS`,                       `product`.`PRODUCT_NAME`,                   `product`.`DESCRIPTION`,
`product`.`LONG_DESCRIPTION`,               `product`.`PRICE_DETAIL_TEXT`,              `product`.`SMALL_IMAGE_URL`,
`product`.`MEDIUM_IMAGE_URL`,               `product`.`LARGE_IMAGE_URL`,                `product`.`DETAIL_IMAGE_URL`,
null,                                       `product`.`DETAIL_SCREEN`,                  `product`.`INVENTORY_MESSAGE`,
`product`.`REQUIRE_INVENTORY`,              `product`.`QUANTITY_UOM_ID`,                `product`.`QUANTITY_INCLUDED`,
`product`.`PIECES_INCLUDED`,                `product`.`REQUIRE_AMOUNT`,                 `product`.`FIXED_AMOUNT`,
`product`.`AMOUNT_UOM_TYPE_ID`,             `product`.`WEIGHT_UOM_ID`,                  `product`.`WEIGHT`,
null,                                       `product`.`HEIGHT_UOM_ID`,                  `product`.`PRODUCT_HEIGHT`,
`product`.`SHIPPING_HEIGHT`,                `product`.`WIDTH_UOM_ID`,                   `product`.`PRODUCT_WIDTH`,
`product`.`SHIPPING_WIDTH`,                 `product`.`DEPTH_UOM_ID`,                   `product`.`PRODUCT_DEPTH`,
`product`.`SHIPPING_DEPTH`,                 null,                                       null,
`product`.`PRODUCT_RATING`,                 `product`.`RATING_TYPE_ENUM`,               `product`.`RETURNABLE`,
`product`.`TAXABLE`,                        `product`.`CHARGE_SHIPPING`,                `product`.`AUTO_CREATE_KEYWORDS`,
`product`.`INCLUDE_IN_PROMOTIONS`,          `product`.`IS_VIRTUAL`,                     `product`.`IS_VARIANT`,
null,                                       `product`.`ORIGIN_GEO_ID`,                  `product`.`REQUIREMENT_METHOD_ENUM_ID`,
`product`.`BILL_OF_MATERIAL_LEVEL`,         `product`.`RESERV_MAX_PERSONS`,             `product`.`RESERV2ND_P_P_PERC`,
`product`.`RESERV_NTH_P_P_PERC`,            null,                                       `product`.`CREATED_DATE`,
`product`.`CREATED_BY_USER_LOGIN`,          `product`.`LAST_MODIFIED_DATE`,             `product`.`LAST_MODIFIED_BY_USER_LOGIN`,
`product`.`IN_SHIPPING_BOX`,                null,                                       null,
null,                                       `product`.`LAST_UPDATED_STAMP`,             `product`.`LAST_UPDATED_TX_STAMP`,
`product`.`CREATED_STAMP`,                  `product`.`CREATED_TX_STAMP`,               `product`.`PARENT_PRODUCT_ID`,
`product`.`AVERAGE_CUSTOMER_RATING`,        `product`.`META_TITLE`,                     `product`.`META_DESCRIPTION`,
`product`.`META_KEYWORD`,                   `product`.`COLOR_COUNT`,                    `product`.`PLAIN_PRICE_DESCRIPTION`,
`product`.`PRINT_PRICE_DESCRIPTION`,        `product`.`IS_PRINTABLE`,                   `product`.`HAS_RUSH_PRODUCTION`,
`product`.`CARTON_QTY`,                     `product`.`HAS_COLOR_OPT`,                  `product`.`BIN_LOCATION`,
`product`.`IS_ASSEMBLY_ITEM`,               `product`.`TAG_LINE`,                       `product`.`ON_SALE`,
`product`.`COLOR_DESCRIPTION`
FROM `[:SOURCE_DB:]`.`product`;


INSERT INTO `[:TARGET_DB:]`.`product_feature`
(`PRODUCT_FEATURE_ID`,                      `PRODUCT_FEATURE_TYPE_ID`,                  `PRODUCT_FEATURE_CATEGORY_ID`,
`DESCRIPTION`,                              `UOM_ID`,                                   `NUMBER_SPECIFIED`,
`DEFAULT_AMOUNT`,                           `DEFAULT_SEQUENCE_NUM`,                     `ABBREV`,
`ID_CODE`,                                  `LAST_UPDATED_STAMP`,                       `LAST_UPDATED_TX_STAMP`,
`CREATED_STAMP`,                            `CREATED_TX_STAMP`)

SELECT
`product_feature`.`PRODUCT_FEATURE_ID`,     `product_feature`.`PRODUCT_FEATURE_TYPE_ID`,`product_feature`.`PRODUCT_FEATURE_CATEGORY_ID`,
`product_feature`.`DESCRIPTION`,            `product_feature`.`UOM_ID`,                 `product_feature`.`NUMBER_SPECIFIED`,
`product_feature`.`DEFAULT_AMOUNT`,         `product_feature`.`DEFAULT_SEQUENCE_NUM`,   `product_feature`.`ABBREV`,
`product_feature`.`ID_CODE`,                `product_feature`.`LAST_UPDATED_STAMP`,     `product_feature`.`LAST_UPDATED_TX_STAMP`,
`product_feature`.`CREATED_STAMP`,          `product_feature`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`product_feature`;


INSERT INTO `[:TARGET_DB:]`.`product_feature_appl`
(`PRODUCT_ID`,                              `PRODUCT_FEATURE_ID`,                       `PRODUCT_FEATURE_APPL_TYPE_ID`,
`FROM_DATE`,                                `THRU_DATE`,                                `SEQUENCE_NUM`,
`AMOUNT`,                                   `RECURRING_AMOUNT`,                         `LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,                    `CREATED_STAMP`,                            `CREATED_TX_STAMP`)

SELECT
`product_feature_appl`.`PRODUCT_ID`,        `product_feature_appl`.`PRODUCT_FEATURE_ID`,`product_feature_appl`.`PRODUCT_FEATURE_APPL_TYPE_ID`,
`product_feature_appl`.`FROM_DATE`,         `product_feature_appl`.`THRU_DATE`,         `product_feature_appl`.`SEQUENCE_NUM`,
`product_feature_appl`.`AMOUNT`,            `product_feature_appl`.`RECURRING_AMOUNT`,  `product_feature_appl`.`LAST_UPDATED_STAMP`,`product_feature_appl`.`LAST_UPDATED_TX_STAMP`,
`product_feature_appl`.`CREATED_STAMP`,     `product_feature_appl`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`product_feature_appl`;


INSERT INTO `[:TARGET_DB:]`.`product_feature_type`
(`PRODUCT_FEATURE_TYPE_ID`,					`PARENT_TYPE_ID`,							`HAS_TABLE`,
`DESCRIPTION`,								`LAST_UPDATED_STAMP`,						`LAST_UPDATED_TX_STAMP`,
`CREATED_STAMP`,							`CREATED_TX_STAMP`)

SELECT `product_feature_type`.`PRODUCT_FEATURE_TYPE_ID`,
`product_feature_type`.`PARENT_TYPE_ID`,	`product_feature_type`.`HAS_TABLE`,				`product_feature_type`.`DESCRIPTION`,
`product_feature_type`.`LAST_UPDATED_STAMP`,`product_feature_type`.`LAST_UPDATED_TX_STAMP`,	`product_feature_type`.`CREATED_STAMP`,
`product_feature_type`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`product_feature_type`;


INSERT INTO `[:TARGET_DB:]`.`product_category_rollup`
(`PRODUCT_CATEGORY_ID`,						`PARENT_PRODUCT_CATEGORY_ID`,				`FROM_DATE`,
`THRU_DATE`,								`SEQUENCE_NUM`,								`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,					`CREATED_STAMP`,							`CREATED_TX_STAMP`)

SELECT
`product_category_rollup`.`PRODUCT_CATEGORY_ID`,		`product_category_rollup`.`PARENT_PRODUCT_CATEGORY_ID`,		`product_category_rollup`.`FROM_DATE`,
`product_category_rollup`.`THRU_DATE`,					`product_category_rollup`.`SEQUENCE_NUM`,					`product_category_rollup`.`LAST_UPDATED_STAMP`,
`product_category_rollup`.`LAST_UPDATED_TX_STAMP`,		`product_category_rollup`.`CREATED_STAMP`,					`product_category_rollup`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`product_category_rollup`;


INSERT INTO `[:TARGET_DB:]`.`product_category`
(`PRODUCT_CATEGORY_ID`,						`PRODUCT_CATEGORY_TYPE_ID`,						`PRIMARY_PARENT_CATEGORY_ID`,
`DESCRIPTION`,								`LONG_DESCRIPTION`,								`CATEGORY_IMAGE_URL`,
`LINK_ONE_IMAGE_URL`,						`LINK_TWO_IMAGE_URL`,							`DETAIL_TEMPLATE`,
`SHOW_IN_SELECT`,							`META_TITLE_OVERRIDE`,							`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,					`CREATED_STAMP`,								`CREATED_TX_STAMP`,
`DESCRIPTION_OVERRIDE`,						`CATEGORY_NAME`,								`DETAIL_SCREEN`,
`META_TITLE`,								`META_DESCRIPTION`,								`META_KEYWORD`)

SELECT
`product_category`.`PRODUCT_CATEGORY_ID`,	`product_category`.`PRODUCT_CATEGORY_TYPE_ID`,	`product_category`.`PRIMARY_PARENT_CATEGORY_ID`,
`product_category`.`DESCRIPTION`,			`product_category`.`LONG_DESCRIPTION`,			`product_category`.`CATEGORY_IMAGE_URL`,
`product_category`.`LINK_ONE_IMAGE_URL`,	`product_category`.`LINK_TWO_IMAGE_URL`,		`product_category`.`DETAIL_TEMPLATE`,
`product_category`.`SHOW_IN_SELECT`,		`product_category`.`META_TITLE_OVERRIDE`,		`product_category`.`LAST_UPDATED_STAMP`,
`product_category`.`LAST_UPDATED_TX_STAMP`,	`product_category`.`CREATED_STAMP`,				`product_category`.`CREATED_TX_STAMP`,
`product_category`.`DESCRIPTION_OVERRIDE`,	`product_category`.`CATEGORY_NAME`,				`product_category`.`DETAIL_SCREEN`,
`product_category`.`META_TITLE`,			`product_category`.`META_DESCRIPTION`,			`product_category`.`META_KEYWORD`
FROM `[:SOURCE_DB:]`.`product_category`;


INSERT INTO `[:TARGET_DB:]`.`product_category_member`
(`PRODUCT_CATEGORY_ID`,						`PRODUCT_ID`,									`FROM_DATE`,
`THRU_DATE`,								`COMMENTS`,										`SEQUENCE_NUM`,
`QUANTITY`,									`LAST_UPDATED_STAMP`,							`LAST_UPDATED_TX_STAMP`,
`CREATED_STAMP`,							`CREATED_TX_STAMP`)

SELECT
`product_category_member`.`PRODUCT_CATEGORY_ID`,	`product_category_member`.`PRODUCT_ID`,			`product_category_member`.`FROM_DATE`,
`product_category_member`.`THRU_DATE`,				`product_category_member`.`COMMENTS`,			`product_category_member`.`SEQUENCE_NUM`,
`product_category_member`.`QUANTITY`,				`product_category_member`.`LAST_UPDATED_STAMP`,	`product_category_member`.`LAST_UPDATED_TX_STAMP`,
`product_category_member`.`CREATED_STAMP`,			`product_category_member`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`product_category_member`;


INSERT INTO `[:TARGET_DB:]`.`product_category_role`
(`PRODUCT_CATEGORY_ID`,								`PARTY_ID`,									`ROLE_TYPE_ID`,
`FROM_DATE`,										`THRU_DATE`,								`COMMENTS`,
`LAST_UPDATED_STAMP`,								`LAST_UPDATED_TX_STAMP`,					`CREATED_STAMP`,
`CREATED_TX_STAMP`)

 SELECT
`product_category_role`.`PRODUCT_CATEGORY_ID`,		`product_category_role`.`PARTY_ID`,					`product_category_role`.`ROLE_TYPE_ID`,
`product_category_role`.`FROM_DATE`,				`product_category_role`.`THRU_DATE`,				`product_category_role`.`COMMENTS`,
`product_category_role`.`LAST_UPDATED_STAMP`,		`product_category_role`.`LAST_UPDATED_TX_STAMP`,	`product_category_role`.`CREATED_STAMP`,
`product_category_role`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`product_category_role`;


INSERT INTO `[:TARGET_DB:]`.`product_price`
(`PRODUCT_ID`,                              `PRODUCT_PRICE_TYPE_ID`,                    `PRODUCT_PRICE_PURPOSE_ID`,
`CURRENCY_UOM_ID`,                          `PRODUCT_STORE_GROUP_ID`,                   `FROM_DATE`,
`THRU_DATE`,                                `PRICE`,                                    `QUANTITY`,
`COLORS`,                                   `TERM_UOM_ID`,                              `CUSTOM_PRICE_CALC_SERVICE`,
`PRICE_WITHOUT_TAX`,                        `PRICE_WITH_TAX`,                           `TAX_AMOUNT`,
`TAX_PERCENTAGE`,                           `TAX_AUTH_PARTY_ID`,                        `TAX_AUTH_GEO_ID`,
`TAX_IN_PRICE`,                             `CREATED_DATE`,                             `CREATED_BY_USER_LOGIN`,
`LAST_MODIFIED_DATE`,                       `LAST_MODIFIED_BY_USER_LOGIN`,              `LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,                    `CREATED_STAMP`,                            `CREATED_TX_STAMP`)

SELECT
`product_price`.`PRODUCT_ID`,               `product_price`.`PRODUCT_PRICE_TYPE_ID`,    	`product_price`.`PRODUCT_PRICE_PURPOSE_ID`,
`product_price`.`CURRENCY_UOM_ID`,          `product_price`.`PRODUCT_STORE_GROUP_ID`,   	`product_price`.`FROM_DATE`,
`product_price`.`THRU_DATE`,                `product_price`.`PRICE`,                    	`product_price`.`QUANTITY`,
`product_price`.`COLOR`,                    `product_price`.`TERM_UOM_ID`,              	null,
null,                                       null,                                       	null,
null,                                       null,                                       	null,
null,                                       `product_price`.`CREATED_DATE`,             	`product_price`.`CREATED_BY_USER_LOGIN`,
`product_price`.`LAST_MODIFIED_DATE`,       `product_price`.`LAST_MODIFIED_BY_USER_LOGIN`,	`product_price`.`LAST_UPDATED_STAMP`,
`product_price`.`LAST_UPDATED_TX_STAMP`,    `product_price`.`CREATED_STAMP`,            	`product_price`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`product_price`;


INSERT INTO `[:TARGET_DB:]`.`user_login`
(`USER_LOGIN_ID`,							`CURRENT_PASSWORD`,							`PASSWORD_HINT`,
`IS_SYSTEM`,								`ENABLED`,									`HAS_LOGGED_OUT`,
`REQUIRE_PASSWORD_CHANGE`,					`LAST_CURRENCY_UOM`,						`LAST_LOCALE`,
`LAST_TIME_ZONE`,							`DISABLED_DATE_TIME`,						`SUCCESSIVE_FAILED_LOGINS`,
`EXTERNAL_AUTH_ID`,							`USER_LDAP_DN`,								`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,					`CREATED_STAMP`,							`CREATED_TX_STAMP`,
`PARTY_ID`)

SELECT
`user_login`.`USER_LOGIN_ID`,				`user_login`.`CURRENT_PASSWORD`,			`user_login`.`PASSWORD_HINT`,
`user_login`.`IS_SYSTEM`,					`user_login`.`ENABLED`,			   			`user_login`.`HAS_LOGGED_OUT`,
'N',										`user_login`.`LAST_CURRENCY_UOM`,	    	`user_login`.`LAST_LOCALE`,
null,										`user_login`.`DISABLED_DATE_TIME`,			`user_login`.`SUCCESSIVE_FAILED_LOGINS`,
null, 										null,										`user_login`.`LAST_UPDATED_STAMP`,
`user_login`.`LAST_UPDATED_TX_STAMP`,		`user_login`.`CREATED_STAMP`,		    	`user_login`.`CREATED_TX_STAMP`,
`user_login`.`PARTY_ID`
FROM `[:SOURCE_DB:]`.`user_login`;


REPLACE INTO `[:TARGET_DB:]`.`role_type`
(`ROLE_TYPE_ID`,								`PARENT_TYPE_ID`,							`HAS_TABLE`,
`DESCRIPTION`)

SELECT
`role_type`.`ROLE_TYPE_ID`,						`role_type`.`PARENT_TYPE_ID`,				`role_type`.`HAS_TABLE`,
`role_type`.`DESCRIPTION`
FROM `[:SOURCE_DB:]`.`role_type`;


INSERT INTO `[:TARGET_DB:]`.`party`
(`PARTY_ID`,								`PARTY_TYPE_ID`,							`EXTERNAL_ID`,
`PREFERRED_CURRENCY_UOM_ID`,				`DESCRIPTION`,								`STATUS_ID`,
`CREATED_DATE`,								`CREATED_BY_USER_LOGIN`,					`LAST_MODIFIED_DATE`,
`LAST_MODIFIED_BY_USER_LOGIN`,				`DATA_SOURCE_ID`,							`IS_UNREAD`,
`LAST_UPDATED_STAMP`,						`LAST_UPDATED_TX_STAMP`,					`CREATED_STAMP`,
`CREATED_TX_STAMP`)

SELECT
`party`.`PARTY_ID`,							`party`.`PARTY_TYPE_ID`,				    `party`.`EXTERNAL_ID`,
`party`.`PREFERRED_CURRENCY_UOM_ID`,	    `party`.`DESCRIPTION`,					    `party`.`STATUS_ID`,
`party`.`CREATED_DATE`,					    `party`.`CREATED_BY_USER_LOGIN`,		    `party`.`LAST_MODIFIED_DATE`,
`party`.`LAST_MODIFIED_BY_USER_LOGIN`,		null,										null,
`party`.`LAST_UPDATED_STAMP`,			    `party`.`LAST_UPDATED_TX_STAMP`,		    `party`.`CREATED_STAMP`,
`party`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`party`;


INSERT INTO `[:TARGET_DB:]`.`party_role`
(`PARTY_ID`,								`ROLE_TYPE_ID`,								`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,					`CREATED_STAMP`,							`CREATED_TX_STAMP`)

SELECT
`party_role`.`PARTY_ID`,					`party_role`.`ROLE_TYPE_ID`,				`party_role`.`LAST_UPDATED_STAMP`,
`party_role`.`LAST_UPDATED_TX_STAMP`,		`party_role`.`CREATED_STAMP`,				`party_role`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`party_role`;


INSERT INTO `[:TARGET_DB:]`.`person`
(`PARTY_ID`,								`SALUTATION`,								`FIRST_NAME`,
`MIDDLE_NAME`,								`LAST_NAME`,								`PERSONAL_TITLE`,
`SUFFIX`,									`NICKNAME`,									`FIRST_NAME_LOCAL`,
`MIDDLE_NAME_LOCAL`,						`LAST_NAME_LOCAL`,							`OTHER_LOCAL`,
`MEMBER_ID`,								`GENDER`,									`BIRTH_DATE`,
`DECEASED_DATE`,							`HEIGHT`,									`WEIGHT`,
`MOTHERS_MAIDEN_NAME`,						`MARITAL_STATUS`,							`SOCIAL_SECURITY_NUMBER`,
`PASSPORT_NUMBER`,							`PASSPORT_EXPIRE_DATE`,						`TOTAL_YEARS_WORK_EXPERIENCE`,
`COMMENTS`,									`EMPLOYMENT_STATUS_ENUM_ID`,				`RESIDENCE_STATUS_ENUM_ID`,
`OCCUPATION`,								`YEARS_WITH_EMPLOYER`,						`MONTHS_WITH_EMPLOYER`,
`EXISTING_CUSTOMER`,						`CARD_ID`,									`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,					`CREATED_STAMP`,							`CREATED_TX_STAMP`)

SELECT
`person`.`PARTY_ID`,						`person`.`SALUTATION`,						`person`.`FIRST_NAME`,
`person`.`MIDDLE_NAME`,						`person`.`LAST_NAME`,						`person`.`PERSONAL_TITLE`,
`person`.`SUFFIX`,							`person`.`NICKNAME`,						`person`.`FIRST_NAME_LOCAL`,
null,										`person`.`LAST_NAME_LOCAL`,					null,
`person`.`MEMBER_ID`,						`person`.`GENDER`,							`person`.`BIRTH_DATE`,
null,										`person`.`HEIGHT`,							`person`.`WEIGHT`,
`person`.`MOTHERS_MAIDEN_NAME`,				`person`.`MARITAL_STATUS`,					`person`.`SOCIAL_SECURITY_NUMBER`,
`person`.`PASSPORT_NUMBER`,					`person`.`PASSPORT_EXPIRE_DATE`,			`person`.`TOTAL_YEARS_WORK_EXPERIENCE`,
`person`.`COMMENTS`,						`person`.`EMPLOYMENT_STATUS_ENUM_ID`,		`person`.`RESIDENCE_STATUS_ENUM_ID`,
`person`.`OCCUPATION`,						`person`.`YEARS_WITH_EMPLOYER`,				`person`.`MONTHS_WITH_EMPLOYER`,
`person`.`EXISTING_CUSTOMER`,				null,										`person`.`LAST_UPDATED_STAMP`,
`person`.`LAST_UPDATED_TX_STAMP`,			`person`.`CREATED_STAMP`,					`person`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`person`;


INSERT INTO `[:TARGET_DB:]`.`party_contact_mech`
(`PARTY_ID`,								`CONTACT_MECH_ID`,							`FROM_DATE`,
`THRU_DATE`,								`ROLE_TYPE_ID`,								`ALLOW_SOLICITATION`,
`EXTENSION`,								`VERIFIED`,									`COMMENTS`,
`LAST_UPDATED_STAMP`,						`LAST_UPDATED_TX_STAMP`,					`CREATED_STAMP`,
`CREATED_TX_STAMP`,							`YEARS_WITH_CONTACT_MECH`,					`MONTHS_WITH_CONTACT_MECH`)

SELECT
`party_contact_mech`.`PARTY_ID`,		    `party_contact_mech`.`CONTACT_MECH_ID`,	    	`party_contact_mech`.`FROM_DATE`,
`party_contact_mech`.`THRU_DATE`,		    `party_contact_mech`.`ROLE_TYPE_ID`,	    	`party_contact_mech`.`ALLOW_SOLICITATION`,
`party_contact_mech`.`EXTENSION`,		    `party_contact_mech`.`VERIFIED`,		    	`party_contact_mech`.`COMMENTS`,
`party_contact_mech`.`LAST_UPDATED_STAMP`,  `party_contact_mech`.`LAST_UPDATED_TX_STAMP`,	`party_contact_mech`.`CREATED_STAMP`,
`party_contact_mech`.`CREATED_TX_STAMP`,    `party_contact_mech`.`YEARS_WITH_CONTACT_MECH`,	`party_contact_mech`.`MONTHS_WITH_CONTACT_MECH`
FROM `[:SOURCE_DB:]`.`party_contact_mech`;


INSERT INTO `[:TARGET_DB:]`.`contact_mech`
(`CONTACT_MECH_ID`,							`CONTACT_MECH_TYPE_ID`,						`INFO_STRING`,
`LAST_UPDATED_STAMP`,						`LAST_UPDATED_TX_STAMP`,					`CREATED_STAMP`,
`CREATED_TX_STAMP`)

SELECT
`contact_mech`.`CONTACT_MECH_ID`,			`contact_mech`.`CONTACT_MECH_TYPE_ID`,		`contact_mech`.`INFO_STRING`,
`contact_mech`.`LAST_UPDATED_STAMP`,		`contact_mech`.`LAST_UPDATED_TX_STAMP`,		`contact_mech`.`CREATED_STAMP`,
`contact_mech`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`contact_mech`;


INSERT INTO `[:TARGET_DB:]`.`contact_mech_attribute`
(`CONTACT_MECH_ID`,							`ATTR_NAME`,								`ATTR_VALUE`,
`LAST_UPDATED_STAMP`,						`LAST_UPDATED_TX_STAMP`,					`CREATED_STAMP`,
`CREATED_TX_STAMP`)

SELECT
`contact_mech_attribute`.`CONTACT_MECH_ID`,			`contact_mech_attribute`.`ATTR_NAME`,					`contact_mech_attribute`.`ATTR_VALUE`,
`contact_mech_attribute`.`LAST_UPDATED_STAMP`,		`contact_mech_attribute`.`LAST_UPDATED_TX_STAMP`,		`contact_mech_attribute`.`CREATED_STAMP`,
`contact_mech_attribute`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`contact_mech_attribute`;


INSERT INTO `[:TARGET_DB:]`.`telecom_number`
(`CONTACT_MECH_ID`,							`COUNTRY_CODE`,								`AREA_CODE`,
`CONTACT_NUMBER`,							`ASK_FOR_NAME`,								`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,					`CREATED_STAMP`,							`CREATED_TX_STAMP`)

SELECT
`telecom_number`.`CONTACT_MECH_ID`,			`telecom_number`.`COUNTRY_CODE`,			`telecom_number`.`AREA_CODE`,
`telecom_number`.`CONTACT_NUMBER`,			`telecom_number`.`ASK_FOR_NAME`,			`telecom_number`.`LAST_UPDATED_STAMP`,
`telecom_number`.`LAST_UPDATED_TX_STAMP`,	`telecom_number`.`CREATED_STAMP`,			`telecom_number`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`telecom_number`;


INSERT INTO `[:TARGET_DB:]`.`postal_address`
(`CONTACT_MECH_ID`,							`TO_NAME`,									`ATTN_NAME`,
`ADDRESS1`,									`ADDRESS2`,									`DIRECTIONS`,
`CITY`,										`POSTAL_CODE`,								`POSTAL_CODE_EXT`,
`COUNTRY_GEO_ID`,							`STATE_PROVINCE_GEO_ID`,					`COUNTY_GEO_ID`,
`POSTAL_CODE_GEO_ID`,						`GEO_POINT_ID`,								`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,					`CREATED_STAMP`,							`CREATED_TX_STAMP`,
`COMPANY_NAME`)

SELECT
`postal_address`.`CONTACT_MECH_ID`,			`postal_address`.`TO_NAME`,					`postal_address`.`ATTN_NAME`,
`postal_address`.`ADDRESS1`,				`postal_address`.`ADDRESS2`,				`postal_address`.`DIRECTIONS`,
`postal_address`.`CITY`,					`postal_address`.`POSTAL_CODE`,				`postal_address`.`POSTAL_CODE_EXT`,
`postal_address`.`COUNTRY_GEO_ID`,			`postal_address`.`STATE_PROVINCE_GEO_ID`,	`postal_address`.`COUNTY_GEO_ID`,
`postal_address`.`POSTAL_CODE_GEO_ID`,		null,										`postal_address`.`LAST_UPDATED_STAMP`,
`postal_address`.`LAST_UPDATED_TX_STAMP`,	`postal_address`.`CREATED_STAMP`,			`postal_address`.`CREATED_TX_STAMP`,
`postal_address`.`COMPANY_NAME`
FROM `[:SOURCE_DB:]`.`postal_address`;


INSERT INTO `[:TARGET_DB:]`.`party_contact_mech_purpose`
(`PARTY_ID`,								`CONTACT_MECH_ID`,							`CONTACT_MECH_PURPOSE_TYPE_ID`,
`FROM_DATE`,								`THRU_DATE`,								`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,					`CREATED_STAMP`,							`CREATED_TX_STAMP`)

SELECT
`party_contact_mech_purpose`.`PARTY_ID`,	`party_contact_mech_purpose`.`CONTACT_MECH_ID`,			`party_contact_mech_purpose`.`CONTACT_MECH_PURPOSE_TYPE_ID`,
`party_contact_mech_purpose`.`FROM_DATE`,	`party_contact_mech_purpose`.`THRU_DATE`,				`party_contact_mech_purpose`.`LAST_UPDATED_STAMP`,
`party_contact_mech_purpose`.`LAST_UPDATED_TX_STAMP`,`party_contact_mech_purpose`.`CREATED_STAMP`,	`party_contact_mech_purpose`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`party_contact_mech_purpose`;

INSERT INTO `[:TARGET_DB:]`.`order_adjustment`
(`ORDER_ADJUSTMENT_ID`,						`ORDER_ADJUSTMENT_TYPE_ID`,					`ORDER_ID`,
`ORDER_ITEM_SEQ_ID`,						`SHIP_GROUP_SEQ_ID`,						`COMMENTS`,
`DESCRIPTION`,								`AMOUNT`,									`RECURRING_AMOUNT`,
`AMOUNT_ALREADY_INCLUDED`,					`PRODUCT_PROMO_ID`,							`PRODUCT_PROMO_RULE_ID`,
`PRODUCT_PROMO_ACTION_SEQ_ID`,				`PRODUCT_FEATURE_ID`,						`CORRESPONDING_PRODUCT_ID`,
`TAX_AUTHORITY_RATE_SEQ_ID`,				`SOURCE_REFERENCE_ID`,						`SOURCE_PERCENTAGE`,
`CUSTOMER_REFERENCE_ID`,					`PRIMARY_GEO_ID`,							`SECONDARY_GEO_ID`,
`EXEMPT_AMOUNT`,							`TAX_AUTH_GEO_ID`,							`TAX_AUTH_PARTY_ID`,
`OVERRIDE_GL_ACCOUNT_ID`,					`INCLUDE_IN_TAX`,							`INCLUDE_IN_SHIPPING`,
`CREATED_DATE`,								`CREATED_BY_USER_LOGIN`,					`LAST_MODIFIED_DATE`,
`LAST_MODIFIED_BY_USER_LOGIN`,				`ORIGINAL_ADJUSTMENT_ID`,					`AMOUNT_PER_QUANTITY`,
`PERCENTAGE`,								`LAST_UPDATED_STAMP`,						`LAST_UPDATED_TX_STAMP`,
`CREATED_STAMP`,							`CREATED_TX_STAMP`)

SELECT
`order_adjustment`.`ORDER_ADJUSTMENT_ID`,		`order_adjustment`.`ORDER_ADJUSTMENT_TYPE_ID`,	`order_adjustment`.`ORDER_ID`,
`order_adjustment`.`ORDER_ITEM_SEQ_ID`,			`order_adjustment`.`SHIP_GROUP_SEQ_ID`,			`order_adjustment`.`COMMENTS`,
`order_adjustment`.`DESCRIPTION`,				`order_adjustment`.`AMOUNT`,					`order_adjustment`.`RECURRING_AMOUNT`,
null,											`order_adjustment`.`PRODUCT_PROMO_ID`,			`order_adjustment`.`PRODUCT_PROMO_RULE_ID`,
`order_adjustment`.`PRODUCT_PROMO_ACTION_SEQ_ID`,`order_adjustment`.`PRODUCT_FEATURE_ID`,		`order_adjustment`.`CORRESPONDING_PRODUCT_ID`,
`order_adjustment`.`TAX_AUTHORITY_RATE_SEQ_ID`,	`order_adjustment`.`SOURCE_REFERENCE_ID`,		`order_adjustment`.`SOURCE_PERCENTAGE`,
`order_adjustment`.`CUSTOMER_REFERENCE_ID`,		`order_adjustment`.`PRIMARY_GEO_ID`,			`order_adjustment`.`SECONDARY_GEO_ID`,
`order_adjustment`.`EXEMPT_AMOUNT`,				`order_adjustment`.`TAX_AUTH_GEO_ID`,			`order_adjustment`.`TAX_AUTH_PARTY_ID`,
`order_adjustment`.`OVERRIDE_GL_ACCOUNT_ID`,	`order_adjustment`.`INCLUDE_IN_TAX`,			`order_adjustment`.`INCLUDE_IN_SHIPPING`,
`order_adjustment`.`CREATED_DATE`,				`order_adjustment`.`CREATED_BY_USER_LOGIN`,		null,
null,											`order_adjustment`.`ORIGINAL_ADJUSTMENT_ID`,	`order_adjustment`.`AMOUNT_PER_QUANTITY`,
`order_adjustment`.`PERCENTAGE`,				`order_adjustment`.`LAST_UPDATED_STAMP`,		`order_adjustment`.`LAST_UPDATED_TX_STAMP`,
`order_adjustment`.`CREATED_STAMP`,				`order_adjustment`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`order_adjustment`;


INSERT INTO `[:TARGET_DB:]`.`order_contact_mech`
(`ORDER_ID`,							`CONTACT_MECH_PURPOSE_TYPE_ID`,					`CONTACT_MECH_ID`,
`LAST_UPDATED_STAMP`,					`LAST_UPDATED_TX_STAMP`,						`CREATED_STAMP`,
`CREATED_TX_STAMP`)

SELECT
`order_contact_mech`.`ORDER_ID`,			`order_contact_mech`.`CONTACT_MECH_PURPOSE_TYPE_ID`,		`order_contact_mech`.`CONTACT_MECH_ID`,
`order_contact_mech`.`LAST_UPDATED_STAMP`,	`order_contact_mech`.`LAST_UPDATED_TX_STAMP`,				`order_contact_mech`.`CREATED_STAMP`,
`order_contact_mech`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`order_contact_mech`;


INSERT INTO `[:TARGET_DB:]`.`order_header`
(`ORDER_ID`,							`ORDER_TYPE_ID`,								`ORDER_NAME`,
`EXTERNAL_ID`,							`SALES_CHANNEL_ENUM_ID`,						`ORDER_DATE`,
`PRIORITY`,								`ENTRY_DATE`,									`PICK_SHEET_PRINTED_DATE`,
`VISIT_ID`,								`STATUS_ID`,									`CREATED_BY`,
`FIRST_ATTEMPT_ORDER_ID`,				`CURRENCY_UOM`,									`SYNC_STATUS_ID`,
`BILLING_ACCOUNT_ID`,					`ORIGIN_FACILITY_ID`,							`WEB_SITE_ID`,
`PRODUCT_STORE_ID`,						`TERMINAL_ID`,									`TRANSACTION_ID`,
`AUTO_ORDER_SHOPPING_LIST_ID`,			`NEEDS_INVENTORY_ISSUANCE`,						`IS_RUSH_ORDER`,
`INTERNAL_CODE`,						`REMAINING_SUB_TOTAL`,							`GRAND_TOTAL`,
`IS_VIEWED`,							`INVOICE_PER_SHIPMENT`,							`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,				`CREATED_STAMP`,								`CREATED_TX_STAMP`,
`EXPORTED_DATE`)

SELECT
`order_header`.`ORDER_ID`,					`order_header`.`ORDER_TYPE_ID`,					`order_header`.`ORDER_NAME`,
`order_header`.`EXTERNAL_ID`,				`order_header`.`SALES_CHANNEL_ENUM_ID`,			`order_header`.`ORDER_DATE`,
null,										`order_header`.`ENTRY_DATE`,					null,
`order_header`.`VISIT_ID`,					`order_header`.`STATUS_ID`,						`order_header`.`CREATED_BY`,
`order_header`.`FIRST_ATTEMPT_ORDER_ID`,	`order_header`.`CURRENCY_UOM`,					`order_header`.`SYNC_STATUS_ID`,
`order_header`.`BILLING_ACCOUNT_ID`,		`order_header`.`ORIGIN_FACILITY_ID`,			`order_header`.`WEB_SITE_ID`,
"10000",									`order_header`.`TERMINAL_ID`,					`order_header`.`TRANSACTION_ID`,
`order_header`.`AUTO_ORDER_SHOPPING_LIST_ID`,`order_header`.`NEEDS_INVENTORY_ISSUANCE`,		`order_header`.`IS_RUSH_ORDER`,
`order_header`.`INTERNAL_CODE`,				`order_header`.`REMAINING_SUB_TOTAL`,			`order_header`.`GRAND_TOTAL`,
null,										null,											`order_header`.`LAST_UPDATED_STAMP`,
`order_header`.`LAST_UPDATED_TX_STAMP`,		`order_header`.`CREATED_STAMP`,					`order_header`.`CREATED_TX_STAMP`,
`order_header`.`EXPORTED_DATE`
FROM `[:SOURCE_DB:]`.`order_header`;


INSERT INTO `[:TARGET_DB:]`.`order_header_note`
(`ORDER_ID`,								`NOTE_ID`,										`INTERNAL_NOTE`,
`LAST_UPDATED_STAMP`,						`LAST_UPDATED_TX_STAMP`,						`CREATED_STAMP`,
`CREATED_TX_STAMP`)

SELECT
`order_header_note`.`ORDER_ID`,				`order_header_note`.`NOTE_ID`,					`order_header_note`.`INTERNAL_NOTE`,
`order_header_note`.`LAST_UPDATED_STAMP`,	`order_header_note`.`LAST_UPDATED_TX_STAMP`,	`order_header_note`.`CREATED_STAMP`,
`order_header_note`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`order_header_note`;


INSERT INTO `[:TARGET_DB:]`.`order_item`
(`ORDER_ID`,								`ORDER_ITEM_SEQ_ID`,							`EXTERNAL_ID`,
`ORDER_ITEM_TYPE_ID`,						`ORDER_ITEM_GROUP_SEQ_ID`,						`IS_ITEM_GROUP_PRIMARY`,
`FROM_INVENTORY_ITEM_ID`,					`BUDGET_ID`,									`BUDGET_ITEM_SEQ_ID`,
`PRODUCT_ID`,								`SUPPLIER_PRODUCT_ID`,							`PRODUCT_FEATURE_ID`,
`PROD_CATALOG_ID`,							`PRODUCT_CATEGORY_ID`,							`IS_PROMO`,
`QUOTE_ID`,									`QUOTE_ITEM_SEQ_ID`,							`SHOPPING_LIST_ID`,
`SHOPPING_LIST_ITEM_SEQ_ID`,				`SUBSCRIPTION_ID`,								`DEPLOYMENT_ID`,
`QUANTITY`,									`CANCEL_QUANTITY`,								`SELECTED_AMOUNT`,
`UNIT_PRICE`,								`UNIT_LIST_PRICE`,								`UNIT_AVERAGE_COST`,
`UNIT_RECURRING_PRICE`,						`IS_MODIFIED_PRICE`,							`RECURRING_FREQ_UOM_ID`,
`ITEM_DESCRIPTION`,							`COMMENTS`,										`CORRESPONDING_PO_ID`,
`STATUS_ID`,								`SYNC_STATUS_ID`,								`ESTIMATED_SHIP_DATE`,
`ESTIMATED_DELIVERY_DATE`,					`AUTO_CANCEL_DATE`,								`DONT_CANCEL_SET_DATE`,
`DONT_CANCEL_SET_USER_LOGIN`,				`SHIP_BEFORE_DATE`,								`SHIP_AFTER_DATE`,
`CANCEL_BACK_ORDER_DATE`,					`OVERRIDE_GL_ACCOUNT_ID`,						`SALES_OPPORTUNITY_ID`,
`CHANGE_BY_USER_LOGIN_ID`,					`LAST_UPDATED_STAMP`,							`LAST_UPDATED_TX_STAMP`,
`CREATED_STAMP`,							`CREATED_TX_STAMP`, 							`IS_RUSH_PRODUCTION`,
`DUE_DATE`,									`ARTWORK_SOURCE`,								`IS_REMOVE_FROM_SCHEDULE`,
`RESPONSE_STATUS_ID`)

SELECT
`order_item`.`ORDER_ID`,					`order_item`.`ORDER_ITEM_SEQ_ID`,				`order_item`.`EXTERNAL_ID`,
`order_item`.`ORDER_ITEM_TYPE_ID`,			`order_item`.`ORDER_ITEM_GROUP_SEQ_ID`,			`order_item`.`IS_ITEM_GROUP_PRIMARY`,
null,										`order_item`.`BUDGET_ID`,						`order_item`.`BUDGET_ITEM_SEQ_ID`,
`order_item`.`PRODUCT_ID`,					null,											`order_item`.`PRODUCT_FEATURE_ID`,
`order_item`.`PROD_CATALOG_ID`,				`order_item`.`PRODUCT_CATEGORY_ID`,				`order_item`.`IS_PROMO`,
`order_item`.`QUOTE_ID`,					`order_item`.`QUOTE_ITEM_SEQ_ID`,				`order_item`.`SHOPPING_LIST_ID`,
`order_item`.`SHOPPING_LIST_ITEM_SEQ_ID`,	`order_item`.`SUBSCRIPTION_ID`,					`order_item`.`DEPLOYMENT_ID`,
`order_item`.`QUANTITY`,					`order_item`.`CANCEL_QUANTITY`,					`order_item`.`SELECTED_AMOUNT`,
`order_item`.`UNIT_PRICE`,					`order_item`.`UNIT_LIST_PRICE`,					`order_item`.`UNIT_AVERAGE_COST`,
`order_item`.`UNIT_RECURRING_PRICE`,		`order_item`.`IS_MODIFIED_PRICE`,				`order_item`.`RECURRING_FREQ_UOM_ID`,
`order_item`.`ITEM_DESCRIPTION`,			`order_item`.`COMMENTS`,						`order_item`.`CORRESPONDING_PO_ID`,
`order_item`.`STATUS_ID`,					`order_item`.`SYNC_STATUS_ID`,					`order_item`.`ESTIMATED_SHIP_DATE`,
`order_item`.`ESTIMATED_DELIVERY_DATE`,		`order_item`.`AUTO_CANCEL_DATE`,				`order_item`.`DONT_CANCEL_SET_DATE`,
`order_item`.`DONT_CANCEL_SET_USER_LOGIN`,	`order_item`.`SHIP_BEFORE_DATE`,				`order_item`.`SHIP_AFTER_DATE`,
null,										`order_item`.`OVERRIDE_GL_ACCOUNT_ID`,			null,
null,										`order_item`.`LAST_UPDATED_STAMP`,				`order_item`.`LAST_UPDATED_TX_STAMP`,
`order_item`.`CREATED_STAMP`,				`order_item`.`CREATED_TX_STAMP`,				`order_item`.`IS_RUSH_PRODUCTION`,
`order_item`.`DUE_DATE`,					`order_item`.`ARTWORK_SOURCE`,					`order_item`.`IS_REMOVE_FROM_SCHEDULE`,
`order_item`.`RESPONSE_STATUS_ID`
FROM `[:SOURCE_DB:]`.`order_item`;


INSERT INTO `[:TARGET_DB:]`.`order_item_artwork`
(`ORDER_ITEM_ARTWORK_ID`,					`ORDER_ID`,										`ORDER_ITEM_SEQ_ID`,
`SCENE7_DESIGN_ID`,							`ITEM_JOB_NAME`,								`ITEM_INTERNAL_JOB_NAME`,
`ITEM_INK_COLOR`,							`FRONT_INK_COLOR1`,								`FRONT_INK_COLOR2`,
`FRONT_INK_COLOR3`,							`FRONT_INK_COLOR4`,								`BACK_INK_COLOR1`,
`BACK_INK_COLOR2`,							`BACK_INK_COLOR3`,								`BACK_INK_COLOR4`,
`CROPPED_WIDTH`,							`CROPPED_HEIGHT`,								`ITEM_PRINT_POSITION`,
`ITEM_PREPRESS_COMMENTS`,					`ITEM_CUSTOMER_COMMENTS`,						`IMAGE_AREA`,
`ITEM_HTML`,								`ITEM_HTML_BOTTOM`,								`ITEM_XML`,
`EMAIL`,									`ASSIGNED_TO`,									`PRINT_PLATE_ID`,
`IS_FOUR_COLOR_PRINT`,						`IS_SINGLE_PLATE_JOB`,							`ASSIGNED_TO_USER_LOGIN`,
`LAST_MODIFIED_DATE`,						`LAST_MODIFIED_BY_USER_LOGIN`,					`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,					`CREATED_STAMP`,								`CREATED_TX_STAMP`)

SELECT
`order_item_artwork`.`ORDER_ITEM_ARTWORK_ID`,	`order_item_artwork`.`ORDER_ID`,					`order_item_artwork`.`ORDER_ITEM_SEQ_ID`,
`order_item_artwork`.`SCENE7_DESIGN_ID`,		`order_item_artwork`.`ITEM_JOB_NAME`,				`order_item_artwork`.`ITEM_INTERNAL_JOB_NAME`,
`order_item_artwork`.`ITEM_INK_COLOR`,			`order_item_artwork`.`FRONT_INK_COLOR1`,			`order_item_artwork`.`FRONT_INK_COLOR2`,
`order_item_artwork`.`FRONT_INK_COLOR3`,		`order_item_artwork`.`FRONT_INK_COLOR4`,			`order_item_artwork`.`BACK_INK_COLOR1`,
`order_item_artwork`.`BACK_INK_COLOR2`,			`order_item_artwork`.`BACK_INK_COLOR3`,				`order_item_artwork`.`BACK_INK_COLOR4`,
`order_item_artwork`.`CROPPED_WIDTH`,			`order_item_artwork`.`CROPPED_HEIGHT`,				`order_item_artwork`.`ITEM_PRINT_POSITION`,
`order_item_artwork`.`ITEM_PREPRESS_COMMENTS`,	`order_item_artwork`.`ITEM_CUSTOMER_COMMENTS`,		`order_item_artwork`.`IMAGE_AREA`,
`order_item_artwork`.`ITEM_HTML`,				`order_item_artwork`.`ITEM_HTML_BOTTOM`,			`order_item_artwork`.`ITEM_XML`,
`order_item_artwork`.`EMAIL`,					`order_item_artwork`.`ASSIGNED_TO`,					`order_item_artwork`.`PRINT_PLATE_ID`,
`order_item_artwork`.`IS_FOUR_COLOR_PRINT`,		`order_item_artwork`.`IS_SINGLE_PLATE_JOB`,			`order_item_artwork`.`ASSIGNED_TO_USER_LOGIN`,
`order_item_artwork`.`LAST_MODIFIED_DATE`,		`order_item_artwork`.`LAST_MODIFIED_BY_USER_LOGIN`,	`order_item_artwork`.`LAST_UPDATED_STAMP`,
`order_item_artwork`.`LAST_UPDATED_TX_STAMP`,	`order_item_artwork`.`CREATED_STAMP`,				`order_item_artwork`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`order_item_artwork`;


INSERT INTO `[:TARGET_DB:]`.`order_item_artwork_comment`
(`ORDER_ITEM_ARTWORK_COMMENT_ID`,				`ORDER_ITEM_ARTWORK_ID`,							`PARTY_ID`,
`TYPE_ENUM_ID`,									`MESSAGE`,											`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,						`CREATED_STAMP`,									`CREATED_TX_STAMP`,
`INTERNAL_NOTE`)

SELECT
`order_item_artwork_comment`.`ORDER_ITEM_ARTWORK_COMMENT_ID`,		`order_item_artwork_comment`.`ORDER_ITEM_ARTWORK_ID`,		`order_item_artwork_comment`.`PARTY_ID`,
`order_item_artwork_comment`.`TYPE_ENUM_ID`,						`order_item_artwork_comment`.`MESSAGE`,						`order_item_artwork_comment`.`LAST_UPDATED_STAMP`,
`order_item_artwork_comment`.`LAST_UPDATED_TX_STAMP`,				`order_item_artwork_comment`.`CREATED_STAMP`,				`order_item_artwork_comment`.`CREATED_TX_STAMP`,
`order_item_artwork_comment`.`REASON`
FROM `[:SOURCE_DB:]`.`order_item_artwork_comment`;


INSERT INTO `[:TARGET_DB:]`.`order_item_attribute`
(`ORDER_ID`,								`ORDER_ITEM_SEQ_ID`,							`ATTR_NAME`,
`ATTR_VALUE`,								`LAST_UPDATED_STAMP`,							`LAST_UPDATED_TX_STAMP`,
`CREATED_STAMP`,							`CREATED_TX_STAMP`)

SELECT
`order_item_attribute`.`ORDER_ID`,			`order_item_attribute`.`ORDER_ITEM_SEQ_ID`,		`order_item_attribute`.`ATTR_NAME`,
`order_item_attribute`.`ATTR_VALUE`,		`order_item_attribute`.`LAST_UPDATED_STAMP`,	`order_item_attribute`.`LAST_UPDATED_TX_STAMP`,
`order_item_attribute`.`CREATED_STAMP`,		`order_item_attribute`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`order_item_attribute`;


INSERT INTO `[:TARGET_DB:]`.`order_item_content`
(`ORDER_ID`,									`ORDER_ITEM_SEQ_ID`,								`CONTENT_PURPOSE_ENUM_ID`,
`FROM_DATE`,									`THRU_DATE`,										`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,						`CREATED_STAMP`,									`CREATED_TX_STAMP`,
`CONTENT_PATH`,									`ORDER_ITEM_CONTENT_ID`)

SELECT
`order_item_content_temp`.`ORDER_ID`,			    `order_item_content_temp`.`ORDER_ITEM_SEQ_ID`,		    `order_item_content_temp`.`CONTENT_PURPOSE_ENUM_ID`,
`order_item_content_temp`.`FROM_DATE`,			    `order_item_content_temp`.`THRU_DATE`,				    `order_item_content_temp`.`LAST_UPDATED_STAMP`,
`order_item_content_temp`.`LAST_UPDATED_TX_STAMP`,  `order_item_content_temp`.`CREATED_STAMP`,			    `order_item_content_temp`.`CREATED_TX_STAMP`,
`order_item_content_temp`.`CONTENT_PATH`,			`order_item_content_temp`.`ORDER_ITEM_CONTENT_ID`
FROM `[:SOURCE_DB:]`.`order_item_content_temp`;


INSERT INTO `[:TARGET_DB:]`.`order_item_ship_group`
(`ORDER_ID`,								`SHIP_GROUP_SEQ_ID`,							`SHIPMENT_METHOD_TYPE_ID`,
`SUPPLIER_PARTY_ID`,						`VENDOR_PARTY_ID`,								`CARRIER_PARTY_ID`,
`CARRIER_ROLE_TYPE_ID`,						`FACILITY_ID`,									`CONTACT_MECH_ID`,
`TELECOM_CONTACT_MECH_ID`,					`TRACKING_NUMBER`,								`SHIPPING_INSTRUCTIONS`,
`MAY_SPLIT`,								`GIFT_MESSAGE`,									`IS_GIFT`,
`SHIP_AFTER_DATE`,							`SHIP_BY_DATE`,									`ESTIMATED_SHIP_DATE`,
`ESTIMATED_DELIVERY_DATE`,					`LAST_UPDATED_STAMP`,							`LAST_UPDATED_TX_STAMP`,
`CREATED_STAMP`,							`CREATED_TX_STAMP`)

SELECT
`order_item_ship_group`.`ORDER_ID`,					`order_item_ship_group`.`SHIP_GROUP_SEQ_ID`,	`order_item_ship_group`.`SHIPMENT_METHOD_TYPE_ID`,
`order_item_ship_group`.`SUPPLIER_PARTY_ID`,		null,											`order_item_ship_group`.`CARRIER_PARTY_ID`,
`order_item_ship_group`.`CARRIER_ROLE_TYPE_ID`,		null,											`order_item_ship_group`.`CONTACT_MECH_ID`,
`order_item_ship_group`.`TELECOM_CONTACT_MECH_ID`,  `order_item_ship_group`.`TRACKING_NUMBER`,		`order_item_ship_group`.`SHIPPING_INSTRUCTIONS`,
`order_item_ship_group`.`MAY_SPLIT`,				`order_item_ship_group`.`GIFT_MESSAGE`,			`order_item_ship_group`.`IS_GIFT`,
`order_item_ship_group`.`SHIP_AFTER_DATE`,			`order_item_ship_group`.`SHIP_BY_DATE`,			null,
`order_item_ship_group`.`ESTIMATED_DELIVERY_DATE`,	`order_item_ship_group`.`LAST_UPDATED_STAMP`,	`order_item_ship_group`.`LAST_UPDATED_TX_STAMP`,
`order_item_ship_group`.`CREATED_STAMP`,			`order_item_ship_group`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`order_item_ship_group`;


INSERT INTO `[:TARGET_DB:]`.`order_item_ship_group_assoc`
(`ORDER_ID`,										`ORDER_ITEM_SEQ_ID`,							`SHIP_GROUP_SEQ_ID`,
`QUANTITY`,											`CANCEL_QUANTITY`,								`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,							`CREATED_STAMP`,								`CREATED_TX_STAMP`)

SELECT
`order_item_ship_group_assoc`.`ORDER_ID`,			`order_item_ship_group_assoc`.`ORDER_ITEM_SEQ_ID`,		`order_item_ship_group_assoc`.`SHIP_GROUP_SEQ_ID`,
`order_item_ship_group_assoc`.`QUANTITY`,			`order_item_ship_group_assoc`.`CANCEL_QUANTITY`,		`order_item_ship_group_assoc`.`LAST_UPDATED_STAMP`,
`order_item_ship_group_assoc`.`LAST_UPDATED_TX_STAMP`,`order_item_ship_group_assoc`.`CREATED_STAMP`,		`order_item_ship_group_assoc`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`order_item_ship_group_assoc`;


INSERT INTO `[:TARGET_DB:]`.`order_status`
(`ORDER_STATUS_ID`,											`STATUS_ID`,									`ORDER_ID`,
`ORDER_ITEM_SEQ_ID`,										`ORDER_PAYMENT_PREFERENCE_ID`,					`STATUS_DATETIME`,
`STATUS_USER_LOGIN`,										`CHANGE_REASON`,								`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,									`CREATED_STAMP`,								`CREATED_TX_STAMP`)

SELECT
`order_status`.`ORDER_STATUS_ID`,				    		`order_status`.`STATUS_ID`,					    `order_status`.`ORDER_ID`,
`order_status`.`ORDER_ITEM_SEQ_ID`,							null,									    	`order_status`.`STATUS_DATETIME`,
`order_status`.`STATUS_USER_LOGIN`,					    	`order_status`.`CHANGE_REASON`,			    	`order_status`.`LAST_UPDATED_STAMP`,
`order_status`.`LAST_UPDATED_TX_STAMP`,				    	`order_status`.`CREATED_STAMP`,		    		`order_status`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`order_status`;


INSERT INTO `[:TARGET_DB:]`.`order_role`
(`ORDER_ID`,										`PARTY_ID`,												`ROLE_TYPE_ID`,
`LAST_UPDATED_STAMP`,								`LAST_UPDATED_TX_STAMP`,								`CREATED_STAMP`,
`CREATED_TX_STAMP`)

SELECT
`order_role`.`ORDER_ID`,							`order_role`.`PARTY_ID`,								`order_role`.`ROLE_TYPE_ID`,
`order_role`.`LAST_UPDATED_STAMP`,					`order_role`.`LAST_UPDATED_TX_STAMP`,					`order_role`.`CREATED_STAMP`,
`order_role`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`order_role`;


INSERT INTO `[:TARGET_DB:]`.`order_payment_preference`
(`ORDER_PAYMENT_PREFERENCE_ID`,						`ORDER_ID`,											`ORDER_ITEM_SEQ_ID`,
`SHIP_GROUP_SEQ_ID`,								`PRODUCT_PRICE_PURPOSE_ID`,							`PAYMENT_METHOD_TYPE_ID`,
`PAYMENT_METHOD_ID`,								`FIN_ACCOUNT_ID`,									`SECURITY_CODE`,
`TRACK2`,											`PRESENT_FLAG`,										`SWIPED_FLAG`,
`OVERFLOW_FLAG`,									`MAX_AMOUNT`,										`PROCESS_ATTEMPT`,
`BILLING_POSTAL_CODE`,								`MANUAL_AUTH_CODE`,									`MANUAL_REF_NUM`,
`STATUS_ID`,										`NEEDS_NSF_RETRY`,									`CREATED_DATE`,
`CREATED_BY_USER_LOGIN`,							`LAST_MODIFIED_DATE`,								`LAST_MODIFIED_BY_USER_LOGIN`,
`LAST_UPDATED_STAMP`,								`LAST_UPDATED_TX_STAMP`,							`CREATED_STAMP`,
`CREATED_TX_STAMP`)

SELECT
`order_payment_preference`.`ORDER_PAYMENT_PREFERENCE_ID`,	`order_payment_preference`.`ORDER_ID`,					`order_payment_preference`.`ORDER_ITEM_SEQ_ID`,
null,														`order_payment_preference`.`PRODUCT_PRICE_PURPOSE_ID`,	`order_payment_preference`.`PAYMENT_METHOD_TYPE_ID`,
`order_payment_preference`.`PAYMENT_METHOD_ID`,				`order_payment_preference`.`FIN_ACCOUNT_ID`,			`order_payment_preference`.`SECURITY_CODE`,
null,														`order_payment_preference`.`PRESENT_FLAG`,				null,
`order_payment_preference`.`OVERFLOW_FLAG`,					`order_payment_preference`.`MAX_AMOUNT`,				`order_payment_preference`.`PROCESS_ATTEMPT`,
`order_payment_preference`.`BILLING_POSTAL_CODE`,			`order_payment_preference`.`MANUAL_AUTH_CODE`,			`order_payment_preference`.`MANUAL_REF_NUM`,
`order_payment_preference`.`STATUS_ID`,						`order_payment_preference`.`NEEDS_NSF_RETRY`,			`order_payment_preference`.`CREATED_DATE`,
`order_payment_preference`.`CREATED_BY_USER_LOGIN`,			null, 													null,
`order_payment_preference`.`LAST_UPDATED_STAMP`,			`order_payment_preference`.`LAST_UPDATED_TX_STAMP`,		`order_payment_preference`.`CREATED_STAMP`,
`order_payment_preference`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`order_payment_preference`;


DELETE FROM `[:SOURCE_DB:]`.`user_login_security_group` where user_login_id IN ( 'admin','demoadmin','flexadmin','ltdadmin','supplier');


INSERT INTO `[:TARGET_DB:]`.`user_login_security_group`
(`USER_LOGIN_ID`,										`GROUP_ID`,											`FROM_DATE`,
`THRU_DATE`,											`LAST_UPDATED_STAMP`,								`LAST_UPDATED_TX_STAMP`,
`CREATED_STAMP`,										`CREATED_TX_STAMP`)

SELECT
`user_login_security_group`.`USER_LOGIN_ID`,		`user_login_security_group`.`GROUP_ID`,			    `user_login_security_group`.`FROM_DATE`,
`user_login_security_group`.`THRU_DATE`,   			`user_login_security_group`.`LAST_UPDATED_STAMP`,	`user_login_security_group`.`LAST_UPDATED_TX_STAMP`,
`user_login_security_group`.`CREATED_STAMP`,	    `user_login_security_group`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`user_login_security_group`;


INSERT INTO `[:TARGET_DB:]`.`payment`
(`PAYMENT_ID`,									`PAYMENT_TYPE_ID`,										`PAYMENT_METHOD_TYPE_ID`,
`PAYMENT_METHOD_ID`,							`PAYMENT_GATEWAY_RESPONSE_ID`,							`PAYMENT_PREFERENCE_ID`,
`PARTY_ID_FROM`,								`PARTY_ID_TO`,											`ROLE_TYPE_ID_TO`,
`STATUS_ID`,									`EFFECTIVE_DATE`,										`PAYMENT_REF_NUM`,
`AMOUNT`,										`CURRENCY_UOM_ID`,										`COMMENTS`,
`FIN_ACCOUNT_TRANS_ID`,							`OVERRIDE_GL_ACCOUNT_ID`,								`ACTUAL_CURRENCY_AMOUNT`,
`ACTUAL_CURRENCY_UOM_ID`,						`LAST_UPDATED_STAMP`,									`LAST_UPDATED_TX_STAMP`,
`CREATED_STAMP`,								`CREATED_TX_STAMP`)

SELECT
`payment`.`PAYMENT_ID`,							`payment`.`PAYMENT_TYPE_ID`,							`payment`.`PAYMENT_METHOD_TYPE_ID`,
`payment`.`PAYMENT_METHOD_ID`,					`payment`.`PAYMENT_GATEWAY_RESPONSE_ID`,				`payment`.`PAYMENT_PREFERENCE_ID`,
`payment`.`PARTY_ID_FROM`,						`payment`.`PARTY_ID_TO`,								`payment`.`ROLE_TYPE_ID_TO`,
`payment`.`STATUS_ID`,							`payment`.`EFFECTIVE_DATE`,								`payment`.`PAYMENT_REF_NUM`,
`payment`.`AMOUNT`,								`payment`.`CURRENCY_UOM_ID`,							`payment`.`COMMENTS`,
`payment`.`FIN_ACCOUNT_TRANS_ID`,				`payment`.`OVERRIDE_GL_ACCOUNT_ID`,						`payment`.`ACTUAL_CURRENCY_AMOUNT`,
`payment`.`ACTUAL_CURRENCY_UOM_ID`,				`payment`.`LAST_UPDATED_STAMP`,							`payment`.`LAST_UPDATED_TX_STAMP`,
`payment`.`CREATED_STAMP`,						`payment`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`payment`;


INSERT INTO `[:TARGET_DB:]`.`payment_method`
(`PAYMENT_METHOD_ID`,							`PAYMENT_METHOD_TYPE_ID`,								`PARTY_ID`,
`GL_ACCOUNT_ID`,								`FIN_ACCOUNT_ID`,										`DESCRIPTION`,
`FROM_DATE`,									`THRU_DATE`,											`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,						`CREATED_STAMP`,										`CREATED_TX_STAMP`)

SELECT
`payment_method`.`PAYMENT_METHOD_ID`,			`payment_method`.`PAYMENT_METHOD_TYPE_ID`,				`payment_method`.`PARTY_ID`,
`payment_method`.`GL_ACCOUNT_ID`,				null,													`payment_method`.`DESCRIPTION`,
`payment_method`.`FROM_DATE`,					`payment_method`.`THRU_DATE`,							`payment_method`.`LAST_UPDATED_STAMP`,
`payment_method`.`LAST_UPDATED_TX_STAMP`,		`payment_method`.`CREATED_STAMP`,						`payment_method`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`payment_method`;


INSERT INTO `[:TARGET_DB:]`.`print_card`
(`PRINT_CARD_ID`,								`ORDER_ITEM_ARTWORK_ID`,								`STATUS_ID`,
`PRODUCT_ID`,									`ITEM_DESCRIPTION`,										`ORDER_ID`,
`QUANTITY`,										`IS_RUSH_PRODUCTION`,									`DUE_DATE`,
`IS_REMOVE_FROM_SCHEDULE`,						`LAST_UPDATED_STAMP`,									`LAST_UPDATED_TX_STAMP`,
`CREATED_STAMP`,								`CREATED_TX_STAMP`)

SELECT
`print_card`.`PRINT_CARD_ID`,					`print_card`.`ORDER_ITEM_ARTWORK_ID`,					`print_card`.`STATUS_ID`,
`print_card`.`PRODUCT_ID`,						`print_card`.`ITEM_DESCRIPTION`,						`print_card`.`ORDER_ID`,
`print_card`.`CUSTOM_QUANTITY`,					`print_card`.`IS_RUSH_PRODUCTION`,						`print_card`.`DUE_DATE`,
`print_card`.`IS_REMOVE_FROM_SCHEDULE`,			`print_card`.`LAST_UPDATED_STAMP`,						`print_card`.`LAST_UPDATED_TX_STAMP`,
`print_card`.`CREATED_STAMP`,					`print_card`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`print_card`;


INSERT INTO `[:TARGET_DB:]`.`print_plate`
(`PRINT_PLATE_ID`,								`PRINT_PRESS_ID`,										`SCHEDULE_PRINT_PRESS_ID`,
`STATUS_ID`,									`COMMENT`,												`DUE_DATE`,
`SEQUENCE_NUM`,									`LAST_UPDATED_STAMP`,									`LAST_UPDATED_TX_STAMP`,
`CREATED_STAMP`,								`CREATED_TX_STAMP`)

SELECT
`print_plate`.`PRINT_PLATE_ID`,					`print_plate`.`PRINT_PRESS_ID`,							`print_plate`.`SCHEDULE_PRINT_PRESS_ID`,
`print_plate`.`STATUS_ID`,						`print_plate`.`COMMENT`,								`print_plate`.`DUE_DATE`,
`print_plate`.`SEQUENCE_NUM`,					`print_plate`.`LAST_UPDATED_STAMP`,						`print_plate`.`LAST_UPDATED_TX_STAMP`,
`print_plate`.`CREATED_STAMP`,					`print_plate`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`print_plate`;


INSERT INTO `[:TARGET_DB:]`.`print_press`
(`PRINT_PRESS_ID`,								`PRESS_NAME`,											`DESCRIPTION`,
`SEQUENCE_NUM`,									`PLATE_SEQUENCE`,										`SAVED_STATE`,
`PLATE_WIDTH`,									`PLATE_HEIGHT`,											`METRIX_ID`,
`LAST_UPDATED_STAMP`,							`LAST_UPDATED_TX_STAMP`,								`CREATED_STAMP`,
`CREATED_TX_STAMP`)

SELECT
`print_press`.`PRINT_PRESS_ID`, 				`print_press`.`PRESS_NAME`,								`print_press`.`DESCRIPTION`,
`print_press`.`SEQUENCE_NUM`,					`print_press`.`PLATE_SEQUENCE`,							`print_press`.`SAVED_STATE`,
`print_press`.`PLATE_WIDTH`,					`print_press`.`PLATE_HEIGHT`,							`print_press`.`METRIX_ID`,
`print_press`.`LAST_UPDATED_STAMP`,				`print_press`.`LAST_UPDATED_TX_STAMP`,					`print_press`.`CREATED_STAMP`,
`print_press`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`print_press`;


INSERT INTO `[:TARGET_DB:]`.`payment_gateway_response`
(`PAYMENT_GATEWAY_RESPONSE_ID`,					`PAYMENT_SERVICE_TYPE_ENUM_ID`,							`ORDER_PAYMENT_PREFERENCE_ID`,
`PAYMENT_METHOD_TYPE_ID`,						`PAYMENT_METHOD_ID`,									`TRANS_CODE_ENUM_ID`,
`AMOUNT`,										`CURRENCY_UOM_ID`,										`REFERENCE_NUM`,
`ALT_REFERENCE`,								`SUB_REFERENCE`,										`GATEWAY_CODE`,
`GATEWAY_FLAG`,									`GATEWAY_AVS_RESULT`,									`GATEWAY_CV_RESULT`,
`GATEWAY_SCORE_RESULT`,							`GATEWAY_MESSAGE`,										`TRANSACTION_DATE`,
`RESULT_DECLINED`,								`RESULT_NSF`,											`RESULT_BAD_EXPIRE`,
`RESULT_BAD_CARD_NUMBER`,						`LAST_UPDATED_STAMP`,									`LAST_UPDATED_TX_STAMP`,
`CREATED_STAMP`,								`CREATED_TX_STAMP`)

SELECT
`payment_gateway_response`.`PAYMENT_GATEWAY_RESPONSE_ID`,		`payment_gateway_response`.`PAYMENT_SERVICE_TYPE_ENUM_ID`,		`payment_gateway_response`.`ORDER_PAYMENT_PREFERENCE_ID`,
`payment_gateway_response`.`PAYMENT_METHOD_TYPE_ID`,			`payment_gateway_response`.`PAYMENT_METHOD_ID`,					`payment_gateway_response`.`TRANS_CODE_ENUM_ID`,
`payment_gateway_response`.`AMOUNT`,							`payment_gateway_response`.`CURRENCY_UOM_ID`,					`payment_gateway_response`.`REFERENCE_NUM`,
`payment_gateway_response`.`ALT_REFERENCE`,						`payment_gateway_response`.`SUB_REFERENCE`,						`payment_gateway_response`.`GATEWAY_CODE`,
`payment_gateway_response`.`GATEWAY_FLAG`,						`payment_gateway_response`.`GATEWAY_AVS_RESULT`,				null,
`payment_gateway_response`.`GATEWAY_SCORE_RESULT`,				`payment_gateway_response`.`GATEWAY_MESSAGE`,					`payment_gateway_response`.`TRANSACTION_DATE`,
`payment_gateway_response`.`RESULT_DECLINED`,					`payment_gateway_response`.`RESULT_NSF`,						`payment_gateway_response`.`RESULT_BAD_EXPIRE`,
`payment_gateway_response`.`RESULT_BAD_CARD_NUMBER`,			`payment_gateway_response`.`LAST_UPDATED_STAMP`,				`payment_gateway_response`.`LAST_UPDATED_TX_STAMP`,
`payment_gateway_response`.`CREATED_STAMP`,						`payment_gateway_response`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`payment_gateway_response`;


INSERT INTO `[:TARGET_DB:]`.`credit_card`
(`PAYMENT_METHOD_ID`,										`CARD_TYPE`,											`CARD_NUMBER`,
`VALID_FROM_DATE`,											`EXPIRE_DATE`,											`ISSUE_NUMBER`,
`COMPANY_NAME_ON_CARD`,										`TITLE_ON_CARD`,										`FIRST_NAME_ON_CARD`,
`MIDDLE_NAME_ON_CARD`,										`LAST_NAME_ON_CARD`,									`SUFFIX_ON_CARD`,
`CONTACT_MECH_ID`,											`CONSECUTIVE_FAILED_AUTHS`,								`LAST_FAILED_AUTH_DATE`,
`CONSECUTIVE_FAILED_NSF`,									`LAST_FAILED_NSF_DATE`,									`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,									`CREATED_STAMP`,										`CREATED_TX_STAMP`)

SELECT
`credit_card`.`PAYMENT_METHOD_ID`,							`credit_card`.`CARD_TYPE`,								`credit_card`.`CARD_NUMBER`,
`credit_card`.`VALID_FROM_DATE`,							`credit_card`.`EXPIRE_DATE`,							`credit_card`.`ISSUE_NUMBER`,
`credit_card`.`COMPANY_NAME_ON_CARD`,						`credit_card`.`TITLE_ON_CARD`,							`credit_card`.`FIRST_NAME_ON_CARD`,
`credit_card`.`MIDDLE_NAME_ON_CARD`,						`credit_card`.`LAST_NAME_ON_CARD`,						`credit_card`.`SUFFIX_ON_CARD`,
`credit_card`.`CONTACT_MECH_ID`,							`credit_card`.`CONSECUTIVE_FAILED_AUTHS`,				`credit_card`.`LAST_FAILED_AUTH_DATE`,
`credit_card`.`CONSECUTIVE_FAILED_NSF`,						`credit_card`.`LAST_FAILED_NSF_DATE`,					`credit_card`.`LAST_UPDATED_STAMP`,
`credit_card`.`LAST_UPDATED_TX_STAMP`,						`credit_card`.`CREATED_STAMP`,							`credit_card`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`credit_card`;


update `[:TARGET_DB:]`.`credit_card` set card_number = '9VUxT4m/vK+6sydanbWbAnj5P5NjDFB7pLZG8JDS8AM=';


INSERT INTO `[:TARGET_DB:]`.`loyalty_points`
(`LOYALTY_POINTS_ID`,								`PARTY_ID`,												`CREATED_BY_USER_LOGIN`,
`CREATED_DATE`,										`POINTS`,												`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,							`CREATED_STAMP`,										`CREATED_TX_STAMP`)

SELECT
`loyalty_points`.`LOYALTY_POINTS_ID`,				`loyalty_points`.`PARTY_ID`,							`loyalty_points`.`CREATED_BY_USER_LOGIN`,
`loyalty_points`.`CREATED_DATE`,					`loyalty_points`.`POINTS`,								`loyalty_points`.`LAST_UPDATED_STAMP`,
`loyalty_points`.`LAST_UPDATED_TX_STAMP`,			`loyalty_points`.`CREATED_STAMP`,						`loyalty_points`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`loyalty_points`;


REPLACE INTO `[:TARGET_DB:]`.`sequence_value_item` (
`SEQ_NAME`, 										`SEQ_ID`, 												`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`, 							`CREATED_STAMP`, 										`CREATED_TX_STAMP`
)

SELECT
`sequence_value_item`.`SEQ_NAME`,					`sequence_value_item`.`SEQ_ID`,							`sequence_value_item`.`LAST_UPDATED_STAMP`,
`sequence_value_item`.`LAST_UPDATED_TX_STAMP`,		`sequence_value_item`.`CREATED_STAMP`,					`sequence_value_item`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`sequence_value_item`;

REPLACE INTO `[:TARGET_DB:]`.`sequence_value_item` (
`SEQ_NAME`, 								`SEQ_ID`, 												`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`, 					`CREATED_STAMP`, 										`CREATED_TX_STAMP`
)

SELECT
"OrderItemContent",									`sequence_value_item`.`SEQ_ID`,							`sequence_value_item`.`LAST_UPDATED_STAMP`,
`sequence_value_item`.`LAST_UPDATED_TX_STAMP`,		`sequence_value_item`.`CREATED_STAMP`,					`sequence_value_item`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`sequence_value_item` WHERE `sequence_value_item`.`SEQ_NAME` = "Content";

UPDATE

INSERT INTO `[:TARGET_DB:]`.`product_promo_code_email`
(`PRODUCT_PROMO_CODE_ID`,								`EMAIL_ADDRESS`,												`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,								`CREATED_STAMP`,												`CREATED_TX_STAMP`)

SELECT
`product_promo_code_email`.`PRODUCT_PROMO_CODE_ID`,				`product_promo_code_email`.`EMAIL_ADDRESS`,					`product_promo_code_email`.`LAST_UPDATED_STAMP`,
`product_promo_code_email`.`LAST_UPDATED_TX_STAMP`,				`product_promo_code_email`.`CREATED_STAMP`,					`product_promo_code_email`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`product_promo_code_email`;


INSERT INTO `[:TARGET_DB:]`.`product_promo_code_party`
(`PRODUCT_PROMO_CODE_ID`,								`PARTY_ID`,														`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,								`CREATED_STAMP`,												`CREATED_TX_STAMP`)

SELECT
`product_promo_code_party`.`PRODUCT_PROMO_CODE_ID`,				`product_promo_code_party`.`PARTY_ID`,						`product_promo_code_party`.`LAST_UPDATED_STAMP`,
`product_promo_code_party`.`LAST_UPDATED_TX_STAMP`,				`product_promo_code_party`.`CREATED_STAMP`,					`product_promo_code_party`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`product_promo_code_party`;


INSERT INTO `[:TARGET_DB:]`.`product_promo_code`
(`PRODUCT_PROMO_CODE_ID`,				`PRODUCT_PROMO_ID`,							`USER_ENTERED`,
`REQUIRE_EMAIL_OR_PARTY`,				`USE_LIMIT_PER_CODE`,						`USE_LIMIT_PER_CUSTOMER`,
`FROM_DATE`,							`THRU_DATE`,								`CREATED_DATE`,
`CREATED_BY_USER_LOGIN`,				`LAST_MODIFIED_DATE`,						`LAST_MODIFIED_BY_USER_LOGIN`,
`LAST_UPDATED_STAMP`,					`LAST_UPDATED_TX_STAMP`,					`CREATED_STAMP`,
`CREATED_TX_STAMP`)

SELECT
`product_promo_code`.`PRODUCT_PROMO_CODE_ID`,				`product_promo_code`.`PRODUCT_PROMO_ID`,						`product_promo_code`.`USER_ENTERED`,
`product_promo_code`.`REQUIRE_EMAIL_OR_PARTY`,				`product_promo_code`.`USE_LIMIT_PER_CODE`,						`product_promo_code`.`USE_LIMIT_PER_CUSTOMER`,
null,														null,															`product_promo_code`.`CREATED_DATE`,
`product_promo_code`.`CREATED_BY_USER_LOGIN`,				`product_promo_code`.`LAST_MODIFIED_DATE`,						`product_promo_code`.`LAST_MODIFIED_BY_USER_LOGIN`,
`product_promo_code`.`LAST_UPDATED_STAMP`,					`product_promo_code`.`LAST_UPDATED_TX_STAMP`,					`product_promo_code`.`CREATED_STAMP`,
`product_promo_code`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`product_promo_code`;


INSERT INTO `[:TARGET_DB:]`.`product_store_promo_appl`
(`PRODUCT_STORE_ID`,				`PRODUCT_PROMO_ID`,							`FROM_DATE`,
`THRU_DATE`,						`SEQUENCE_NUM`,								`MANUAL_ONLY`,
`LAST_UPDATED_STAMP`,				`LAST_UPDATED_TX_STAMP`,					`CREATED_STAMP`,
`CREATED_TX_STAMP`)

SELECT
`product_store_promo_appl`.`PRODUCT_STORE_ID`,				`product_store_promo_appl`.`PRODUCT_PROMO_ID`,						`product_store_promo_appl`.`FROM_DATE`,
`product_store_promo_appl`.`THRU_DATE`,						`product_store_promo_appl`.`SEQUENCE_NUM`,							null,
`product_store_promo_appl`.`LAST_UPDATED_STAMP`,			`product_store_promo_appl`.`LAST_UPDATED_TX_STAMP`,					`product_store_promo_appl`.`CREATED_STAMP`,
`product_store_promo_appl`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`product_store_promo_appl`;


INSERT INTO `[:TARGET_DB:]`.`product_promo_product`
(`PRODUCT_PROMO_ID`,				`PRODUCT_PROMO_RULE_ID`,					`PRODUCT_PROMO_ACTION_SEQ_ID`,
`PRODUCT_PROMO_COND_SEQ_ID`,		`PRODUCT_ID`,								`PRODUCT_PROMO_APPL_ENUM_ID`,
`LAST_UPDATED_STAMP`,				`LAST_UPDATED_TX_STAMP`,					`CREATED_STAMP`,
`CREATED_TX_STAMP`)

SELECT
`product_promo_product`.`PRODUCT_PROMO_ID`,				`product_promo_product`.`PRODUCT_PROMO_RULE_ID`,			`product_promo_product`.`PRODUCT_PROMO_ACTION_SEQ_ID`,
`product_promo_product`.`PRODUCT_PROMO_COND_SEQ_ID`,	`product_promo_product`.`PRODUCT_ID`,						`product_promo_product`.`PRODUCT_PROMO_APPL_ENUM_ID`,
`product_promo_product`.`LAST_UPDATED_STAMP`,			`product_promo_product`.`LAST_UPDATED_TX_STAMP`,			`product_promo_product`.`CREATED_STAMP`,
`product_promo_product`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`product_promo_product`;


INSERT INTO `[:TARGET_DB:]`.`product_promo_category`
(`PRODUCT_PROMO_ID`,				`PRODUCT_PROMO_RULE_ID`,					`PRODUCT_PROMO_ACTION_SEQ_ID`,
`PRODUCT_PROMO_COND_SEQ_ID`,		`PRODUCT_CATEGORY_ID`,						`AND_GROUP_ID`,
`PRODUCT_PROMO_APPL_ENUM_ID`,		`INCLUDE_SUB_CATEGORIES`,					`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,			`CREATED_STAMP`,							`CREATED_TX_STAMP`)

SELECT
`product_promo_category`.`PRODUCT_PROMO_ID`,				`product_promo_category`.`PRODUCT_PROMO_RULE_ID`,					`product_promo_category`.`PRODUCT_PROMO_ACTION_SEQ_ID`,
`product_promo_category`.`PRODUCT_PROMO_COND_SEQ_ID`,		`product_promo_category`.`PRODUCT_CATEGORY_ID`,						`product_promo_category`.`AND_GROUP_ID`,
`product_promo_category`.`PRODUCT_PROMO_APPL_ENUM_ID`,		`product_promo_category`.`INCLUDE_SUB_CATEGORIES`,					`product_promo_category`.`LAST_UPDATED_STAMP`,
`product_promo_category`.`LAST_UPDATED_TX_STAMP`,			`product_promo_category`.`CREATED_STAMP`,							`product_promo_category`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`product_promo_category`;


INSERT INTO `[:TARGET_DB:]`.`product_promo_cond`
(`PRODUCT_PROMO_ID`,			`PRODUCT_PROMO_RULE_ID`,					`PRODUCT_PROMO_COND_SEQ_ID`,
`INPUT_PARAM_ENUM_ID`,			`OPERATOR_ENUM_ID`,							`COND_VALUE`,
`OTHER_VALUE`,					`LAST_UPDATED_STAMP`,						`LAST_UPDATED_TX_STAMP`,
`CREATED_STAMP`,				`CREATED_TX_STAMP`)

SELECT
`product_promo_cond`.`PRODUCT_PROMO_ID`,			`product_promo_cond`.`PRODUCT_PROMO_RULE_ID`,					`product_promo_cond`.`PRODUCT_PROMO_COND_SEQ_ID`,
`product_promo_cond`.`INPUT_PARAM_ENUM_ID`,			`product_promo_cond`.`OPERATOR_ENUM_ID`,						`product_promo_cond`.`COND_VALUE`,
`product_promo_cond`.`OTHER_VALUE`,					`product_promo_cond`.`LAST_UPDATED_STAMP`,						`product_promo_cond`.`LAST_UPDATED_TX_STAMP`,
`product_promo_cond`.`CREATED_STAMP`,				`product_promo_cond`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`product_promo_cond`;


INSERT INTO `[:TARGET_DB:]`.`product_promo_action`
(`PRODUCT_PROMO_ID`,					`PRODUCT_PROMO_RULE_ID`,					`PRODUCT_PROMO_ACTION_SEQ_ID`,
`PRODUCT_PROMO_ACTION_ENUM_ID`,			`ORDER_ADJUSTMENT_TYPE_ID`,					`SERVICE_NAME`,
`QUANTITY`,								`AMOUNT`,									`PRODUCT_ID`,
`PARTY_ID`,								`USE_CART_QUANTITY`,						`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,				`CREATED_STAMP`,							`CREATED_TX_STAMP`)

SELECT
`product_promo_action`.`PRODUCT_PROMO_ID`,					`product_promo_action`.`PRODUCT_PROMO_RULE_ID`,					`product_promo_action`.`PRODUCT_PROMO_ACTION_SEQ_ID`,
`product_promo_action`.`PRODUCT_PROMO_ACTION_ENUM_ID`,		`product_promo_action`.`ORDER_ADJUSTMENT_TYPE_ID`,				null,
`product_promo_action`.`QUANTITY`,							`product_promo_action`.`AMOUNT`,								`product_promo_action`.`PRODUCT_ID`,
`product_promo_action`.`PARTY_ID`,							null,															`product_promo_action`.`LAST_UPDATED_STAMP`,
`product_promo_action`.`LAST_UPDATED_TX_STAMP`,				`product_promo_action`.`CREATED_STAMP`,							`product_promo_action`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`product_promo_action`;


INSERT INTO `[:TARGET_DB:]`.`product_promo_rule`
(`PRODUCT_PROMO_ID`,			`PRODUCT_PROMO_RULE_ID`,					`RULE_NAME`,
`LAST_UPDATED_STAMP`,			`LAST_UPDATED_TX_STAMP`,					`CREATED_STAMP`,
`CREATED_TX_STAMP`)

SELECT
`product_promo_rule`.`PRODUCT_PROMO_ID`,			`product_promo_rule`.`PRODUCT_PROMO_RULE_ID`,					`product_promo_rule`.`RULE_NAME`,
`product_promo_rule`.`LAST_UPDATED_STAMP`,			`product_promo_rule`.`LAST_UPDATED_TX_STAMP`,					`product_promo_rule`.`CREATED_STAMP`,
`product_promo_rule`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`product_promo_rule`;



INSERT INTO `[:TARGET_DB:]`.`product_promo`
(`PRODUCT_PROMO_ID`,			`PROMO_NAME`,						`PROMO_TEXT`,
`USER_ENTERED`,					`SHOW_TO_CUSTOMER`,					`REQUIRE_CODE`,
`USE_LIMIT_PER_ORDER`,			`USE_LIMIT_PER_CUSTOMER`,			`USE_LIMIT_PER_PROMOTION`,
`BILLBACK_FACTOR`,				`OVERRIDE_ORG_PARTY_ID`,			`CREATED_DATE`,
`CREATED_BY_USER_LOGIN`,		`LAST_MODIFIED_DATE`,				`LAST_MODIFIED_BY_USER_LOGIN`,
`LAST_UPDATED_STAMP`,			`LAST_UPDATED_TX_STAMP`,			`CREATED_STAMP`,
`CREATED_TX_STAMP`,				`IS_SAMPLE`,						`IS_STACKABLE`,
`SHOW_ON_SITE`,					`NETSUITE_ID`)

SELECT
`product_promo`.`PRODUCT_PROMO_ID`,			`product_promo`.`PROMO_NAME`,						`product_promo`.`PROMO_TEXT`,
`product_promo`.`USER_ENTERED`,				`product_promo`.`SHOW_TO_CUSTOMER`,					`product_promo`.`REQUIRE_CODE`,
`product_promo`.`USE_LIMIT_PER_ORDER`,		`product_promo`.`USE_LIMIT_PER_CUSTOMER`,			`product_promo`.`USE_LIMIT_PER_PROMOTION`,
`product_promo`.`BILLBACK_FACTOR`,			null,												`product_promo`.`CREATED_DATE`,
`product_promo`.`CREATED_BY_USER_LOGIN`,	`product_promo`.`LAST_MODIFIED_DATE`,				`product_promo`.`LAST_MODIFIED_BY_USER_LOGIN`,
`product_promo`.`LAST_UPDATED_STAMP`,		`product_promo`.`LAST_UPDATED_TX_STAMP`,			`product_promo`.`CREATED_STAMP`,
`product_promo`.`CREATED_TX_STAMP`,			`product_promo`.`SAMPLE_COUPON`,					null,
`product_promo`.`SHOW_ON_SITE`,				`product_promo`.`NETSUITE_ID`
FROM `[:SOURCE_DB:]`.`product_promo`;


-- FINALLY MIGRATE THE SAMPLE AND BLACK FRIDAY COUPON
REPLACE INTO `[:TARGET_DB:]`.`product_promo_code`
(`PRODUCT_PROMO_CODE_ID`,
`PRODUCT_PROMO_ID`,
`USER_ENTERED`,
`REQUIRE_EMAIL_OR_PARTY`,				`USE_LIMIT_PER_CODE`,						`USE_LIMIT_PER_CUSTOMER`,
`FROM_DATE`,							`THRU_DATE`,								`CREATED_DATE`,
`CREATED_BY_USER_LOGIN`,				`LAST_MODIFIED_DATE`,						`LAST_MODIFIED_BY_USER_LOGIN`,
`LAST_UPDATED_STAMP`,					`LAST_UPDATED_TX_STAMP`,					`CREATED_STAMP`,
`CREATED_TX_STAMP`)

SELECT
`promos_temp`.`PRODUCT_PROMO_CODE_ID`,
CASE WHEN `promos_temp`.`amount` = 1 THEN '9500'
	 WHEN `promos_temp`.`amount` = 2 THEN '9501'
	 WHEN `promos_temp`.`amount` = 3 THEN '9502'
	 WHEN `promos_temp`.`amount` = 4 THEN '9503'
	 WHEN `promos_temp`.`amount` = 5 THEN '9504'
	 ELSE '9505'
END,
'Y',
'N',												'1',													null,
`promos_temp`.`FROM_DATE`,							`promos_temp`.`THRU_DATE`,								'2001-01-01 00:00:00.0',
'Shoab',											'2001-01-01 00:00:00.0',								'Shoab',
'2001-01-01 00:00:00.0',							'2001-01-01 00:00:00.0',								'2001-01-01 00:00:00.0',
'2001-01-01 00:00:00.0'
FROM `[:SOURCE_DB:]`.`promos_temp`;


INSERT INTO `[:TARGET_DB:]`.`product_review`
(`PRODUCT_REVIEW_ID`,			`PRODUCT_STORE_ID`,						`PRODUCT_ID`,
`USER_LOGIN_ID`,				`STATUS_ID`,							`POSTED_ANONYMOUS`,
`POSTED_DATE_TIME`,				`PRODUCT_RATING`,						`PRODUCT_REVIEW`,
`LAST_UPDATED_STAMP`,			`LAST_UPDATED_TX_STAMP`,				`CREATED_STAMP`,
`CREATED_TX_STAMP`,				`NICK_NAME`,							`PRODUCT_QUALITY`,
`PRODUCT_QUALITY_REASON`,		`PRODUCT_USE`,							`DESCRIBE_YOURSELF`,
`RECOMMEND`,					`EMAIL_ADDRESS`,						`ORDER_ID`,
`REVIEW_RESPONSE`,				`CONTENT_ID`,							`SHOW_CONTENT`,
`SALES_CHANNEL_ENUM_ID`)

SELECT
`product_review`.`PRODUCT_REVIEW_ID`,			`product_review`.`PRODUCT_STORE_ID`,					`product_review`.`PRODUCT_ID`,
`product_review`.`USER_LOGIN_ID`,				`product_review`.`STATUS_ID`,							`product_review`.`POSTED_ANONYMOUS`,
`product_review`.`POSTED_DATE_TIME`,			`product_review`.`PRODUCT_RATING`,						`product_review`.`PRODUCT_REVIEW`,
`product_review`.`LAST_UPDATED_STAMP`,			`product_review`.`LAST_UPDATED_TX_STAMP`,				`product_review`.`CREATED_STAMP`,
`product_review`.`CREATED_TX_STAMP`,			`product_review`.`NICK_NAME`,							`product_review`.`PRODUCT_QUALITY`,
`product_review`.`PRODUCT_QUALITY_REASON`,		`product_review`.`PRODUCT_USE`,							`product_review`.`DESCRIBE_YOURSELF`,
`product_review`.`RECOMMEND`,					`product_review`.`EMAIL_ADDRESS`,						`product_review`.`ORDER_ID`,
`product_review`.`REVIEW_RESPONSE`,				`product_review`.`CONTENT_ID`,							`product_review`.`SHOW_CONTENT`,
`product_review`.`SALES_CHANNEL_ENUM_ID`
FROM `[:SOURCE_DB:]`.`product_review`;


INSERT INTO `[:TARGET_DB:]`.`product_content`
(`PRODUCT_ID`,				`CONTENT_ID`,					`PRODUCT_CONTENT_TYPE_ID`,
`FROM_DATE`,				`THRU_DATE`,					`PURCHASE_FROM_DATE`,
`PURCHASE_THRU_DATE`,		`USE_COUNT_LIMIT`,				`USE_TIME`,
`USE_TIME_UOM_ID`,			`USE_ROLE_TYPE_ID`,				`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,	`CREATED_STAMP`,				`CREATED_TX_STAMP`)

SELECT
`product_content`.`PRODUCT_ID`,					`product_content`.`CONTENT_ID`,					`product_content`.`PRODUCT_CONTENT_TYPE_ID`,
`product_content`.`FROM_DATE`,					`product_content`.`THRU_DATE`,					`product_content`.`PURCHASE_FROM_DATE`,
`product_content`.`PURCHASE_THRU_DATE`,			`product_content`.`USE_COUNT_LIMIT`,			`product_content`.`USE_TIME`,
`product_content`.`USE_TIME_UOM_ID`,			`product_content`.`USE_ROLE_TYPE_ID`,			`product_content`.`LAST_UPDATED_STAMP`,
`product_content`.`LAST_UPDATED_TX_STAMP`,		`product_content`.`CREATED_STAMP`,				`product_content`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`product_content`;


INSERT INTO `[:TARGET_DB:]`.`scene7_design`
(`SCENE7_DESIGN_ID`,		`SCENE7_TEMPLATE_ID`,		`PARENT_ID`,
`CONTENT_ID`,				`PARTY_ID`,					`TYPE`,
`URL`,						`DATA`,						`SESSION_ID`,
`INACTIVE`,					`LAST_UPDATED_STAMP`,		`LAST_UPDATED_TX_STAMP`,
`CREATED_STAMP`,			`CREATED_TX_STAMP`)

SELECT
`scene7_design`.`SCENE7_DESIGN_ID`,		`scene7_design`.`SCENE7_TEMPLATE_ID`,		`scene7_design`.`PARENT_ID`,
`scene7_design`.`CONTENT_ID`,			`scene7_design`.`PARTY_ID`,					`scene7_design`.`TYPE`,
`scene7_design`.`URL`,					`scene7_design`.`DATA`,						`scene7_design`.`SESSION_ID`,
`scene7_design`.`INACTIVE`,				`scene7_design`.`LAST_UPDATED_STAMP`,		`scene7_design`.`LAST_UPDATED_TX_STAMP`,
`scene7_design`.`CREATED_STAMP`,		`scene7_design`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`scene7_design`;

/*
INSERT INTO `[:TARGET_DB:]`.`scene7_design_attrib`
(`SCENE7_DESIGN_ID`,		`TYPE`,						`SCENE7_DESIGN_KEY_ID`,
`SCENE7_DESIGN_VALUE`,		`LAST_UPDATED_STAMP`,		`LAST_UPDATED_TX_STAMP`,
`CREATED_STAMP`,			`CREATED_TX_STAMP`)

SELECT
`scene7_design_attrib`.`SCENE7_DESIGN_ID`,			`scene7_design_attrib`.`TYPE`,						`scene7_design_attrib`.`SCENE7_DESIGN_KEY_ID`,
`scene7_design_attrib`.`SCENE7_DESIGN_VALUE`,		`scene7_design_attrib`.`LAST_UPDATED_STAMP`,		`scene7_design_attrib`.`LAST_UPDATED_TX_STAMP`,
`scene7_design_attrib`.`CREATED_STAMP`,				`scene7_design_attrib`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`scene7_design_attrib`;
*/

INSERT INTO `[:TARGET_DB:]`.`scene7_prod_assoc`
(`PRODUCT_ID`,			`SCENE7_TEMPLATE_ID`,		`TEMPLATE_ASSOC_TYPE_ID`,
`TEMPLATE_TYPE_ID`,		`LAST_UPDATED_STAMP`,		`LAST_UPDATED_TX_STAMP`,
`CREATED_STAMP`,		`CREATED_TX_STAMP`)

SELECT
`scene7_prod_assoc`.`PRODUCT_ID`,			`scene7_prod_assoc`.`SCENE7_TEMPLATE_ID`,		`scene7_prod_assoc`.`TEMPLATE_ASSOC_TYPE_ID`,
`scene7_prod_assoc`.`TEMPLATE_TYPE_ID`,		`scene7_prod_assoc`.`LAST_UPDATED_STAMP`,		`scene7_prod_assoc`.`LAST_UPDATED_TX_STAMP`,
`scene7_prod_assoc`.`CREATED_STAMP`,		`scene7_prod_assoc`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`scene7_prod_assoc`;


INSERT INTO `[:TARGET_DB:]`.`scene7_template`
(`SCENE7_TEMPLATE_ID`,			`FXG_TYPE`,					`THUMBNAIL_ID`,
`TEMPLATE_NAME`,				`TEMPLATE_URL`,				`THUMBNAIL_PATH`,
`TEMPLATE_DESCRIPTION`,			`PRODUCT_TYPE_ID`,			`PRODUCT_DESC`,
`WIDTH`,						`HEIGHT`,					`COLORS`,
`PRINT_PRICE_DESCRIPTION`,		`BASE_QUANTITY`,			`BASE_PRICE`,
`HAS_ADDRESSING`,				`HAS_VARIABLE_DATA`,		`TEMPLATE_ASSOC_TYPE_ID`,
`LAST_UPDATED_STAMP`,			`LAST_UPDATED_TX_STAMP`,	`CREATED_STAMP`,
`CREATED_TX_STAMP`)

SELECT
`scene7_template`.`SCENE7_TEMPLATE_ID`,			`scene7_template`.`FXG_TYPE`,					`scene7_template`.`THUMBNAIL_ID`,
`scene7_template`.`TEMPLATE_NAME`,				`scene7_template`.`TEMPLATE_URL`,				`scene7_template`.`THUMBNAIL_PATH`,
`scene7_template`.`TEMPLATE_DESCRIPTION`,		`scene7_template`.`PRODUCT_TYPE_ID`,			`scene7_template`.`PRODUCT_DESC`,
`scene7_template`.`WIDTH`,						`scene7_template`.`HEIGHT`,						`scene7_template`.`COLORS`,
`scene7_template`.`PRINT_PRICE_DESCRIPTION`,	`scene7_template`.`BASE_QUANTITY`,				`scene7_template`.`BASE_PRICE`,
`scene7_template`.`HAS_ADDRESSING`,				`scene7_template`.`HAS_VARIABLE_DATA`,			`scene7_template`.`TEMPLATE_ASSOC_TYPE_ID`,
`scene7_template`.`LAST_UPDATED_STAMP`,			`scene7_template`.`LAST_UPDATED_TX_STAMP`,		`scene7_template`.`CREATED_STAMP`,
`scene7_template`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`scene7_template`;


INSERT INTO `[:TARGET_DB:]`.`scene7_template_attr`
(`SCENE7_TEMPLATE_ID`,			`ATTRIBUTE_ID`,				`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,		`CREATED_STAMP`,			`CREATED_TX_STAMP`)

SELECT
`scene7_template_attr`.`SCENE7_TEMPLATE_ID`,			`scene7_template_attr`.`ATTRIBUTE_ID`,				`scene7_template_attr`.`LAST_UPDATED_STAMP`,
`scene7_template_attr`.`LAST_UPDATED_TX_STAMP`,			`scene7_template_attr`.`CREATED_STAMP`,				`scene7_template_attr`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`scene7_template_attr`;


INSERT INTO `[:TARGET_DB:]`.`scene7_template_category`
(`SCENE7_TEMPLATE_ID`,			`CATEGORY_ID`,				`FROM_DATE`,
`THRU_DATE`,					`DESCRIPTION`,				`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,		`CREATED_STAMP`,			`CREATED_TX_STAMP`)

SELECT
`scene7_template_category`.`SCENE7_TEMPLATE_ID`,		`scene7_template_category`.`CATEGORY_ID`,			`scene7_template_category`.`FROM_DATE`,
`scene7_template_category`.`THRU_DATE`,					`scene7_template_category`.`DESCRIPTION`,			`scene7_template_category`.`LAST_UPDATED_STAMP`,
`scene7_template_category`.`LAST_UPDATED_TX_STAMP`,		`scene7_template_category`.`CREATED_STAMP`,			`scene7_template_category`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`scene7_template_category`;


INSERT INTO `[:TARGET_DB:]`.`scene7_user_content`
(`ID`,					`PARTY_ID`,					`CONTENT_ID`,
`GALLERY_ID`,			`SESSION_ID`,				`ASSET_URL`,
`FOLDER`,				`LAST_UPDATED_STAMP`,		`LAST_UPDATED_TX_STAMP`,
`CREATED_STAMP`,		`CREATED_TX_STAMP`)

SELECT
`scene7_user_content`.`ID`,					`scene7_user_content`.`PARTY_ID`,				`scene7_user_content`.`CONTENT_ID`,
`scene7_user_content`.`GALLERY_ID`,			`scene7_user_content`.`SESSION_ID`,				`scene7_user_content`.`ASSET_URL`,
`scene7_user_content`.`FOLDER`,				`scene7_user_content`.`LAST_UPDATED_STAMP`,		`scene7_user_content`.`LAST_UPDATED_TX_STAMP`,
`scene7_user_content`.`CREATED_STAMP`,		`scene7_user_content`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`scene7_user_content`;


INSERT INTO `[:TARGET_DB:]`.`custom_envelope`
(`QUOTE_ID`,				`STATUS_ID`,			`USER_EMAIL`,				`STANDARD_SIZE`,			`WIDTH`,					`HEIGHT`,
`CREATED_DATE`,				`SEALING_METHOD`,		`OTHER_SEALING_METHOD`,		`PAPER_TYPE`,				`PRINTING_REQUIRED`,		`WINDOW_COUNT`,
`COLOR_NUM`,				`INK_FRONT`,			`INK_BACK`,					`SIDE_NUM`,					`RUSH_SERVICE`,				`QUANTITY`,
`DEADLINE`,					`COMMENT`,				`CONTACT_METHOD`,			`LAST_UPDATED_STAMP`,		`LAST_UPDATED_TX_STAMP`,	`CREATED_STAMP`,
`CREATED_TX_STAMP`)

SELECT
`custom_envelope`.`QUOTE_ID`,				`custom_envelope`.`STATUS_ID`,			`custom_envelope`.`USER_EMAIL`,				`custom_envelope`.`STANDARD_SIZE`,		`custom_envelope`.`WIDTH`,					`custom_envelope`.`HEIGHT`,
`custom_envelope`.`CREATED_DATE`,			`custom_envelope`.`SEALING_METHOD`,		`custom_envelope`.`OTHER_SEALING_METHOD`,	`custom_envelope`.`PAPER_TYPE`,			`custom_envelope`.`PRINTING_REQUIRED`,		`custom_envelope`.`WINDOW_COUNT`,
`custom_envelope`.`COLOR_NUM`,				`custom_envelope`.`INK_FRONT`,			`custom_envelope`.`INK_BACK`,				`custom_envelope`.`SIDE_NUM`,			`custom_envelope`.`RUSH_SERVICE`,			`custom_envelope`.`QUANTITY`,
`custom_envelope`.`DEADLINE`,				`custom_envelope`.`COMMENT`,			`custom_envelope`.`CONTACT_METHOD`,			`custom_envelope`.`LAST_UPDATED_STAMP`,	`custom_envelope`.`LAST_UPDATED_TX_STAMP`,	`custom_envelope`.`CREATED_STAMP`,
`custom_envelope`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`custom_envelope`;

INSERT INTO `[:TARGET_DB:]`.`custom_envelope_contact`
(`USER_EMAIL`,				`PARTY_ID`,				`FIRST_NAME`,			`LAST_NAME`,				`COMPANY`,
`ADDRESS1`,					`ADDRESS2`,				`CITY`,					`STATE`,					`ZIP`,
`PHONE`,					`COMMENT`,				`LAST_UPDATED_STAMP`,	`LAST_UPDATED_TX_STAMP`,	`CREATED_STAMP`,
`CREATED_TX_STAMP`)

SELECT
`custom_envelope_contact`.`USER_EMAIL`,			`custom_envelope_contact`.`PARTY_ID`,		`custom_envelope_contact`.`FIRST_NAME`,				`custom_envelope_contact`.`LAST_NAME`,				`custom_envelope_contact`.`COMPANY`,
`custom_envelope_contact`.`ADDRESS1`,			`custom_envelope_contact`.`ADDRESS2`,		`custom_envelope_contact`.`CITY`,					`custom_envelope_contact`.`STATE`,					`custom_envelope_contact`.`ZIP`,
`custom_envelope_contact`.`PHONE`,				`custom_envelope_contact`.`COMMENT`,		`custom_envelope_contact`.`LAST_UPDATED_STAMP`,		`custom_envelope_contact`.`LAST_UPDATED_TX_STAMP`,	`custom_envelope_contact`.`CREATED_STAMP`,
`custom_envelope_contact`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`custom_envelope_contact`;

INSERT INTO `[:TARGET_DB:]`.`custom_envelope_content`
(`QUOTE_ID`,				`CONTENT_ID`,				`LAST_UPDATED_STAMP`,		`LAST_UPDATED_TX_STAMP`,
`CREATED_STAMP`,			`CREATED_TX_STAMP`)

SELECT
`custom_envelope_content`.`QUOTE_ID`,					`custom_envelope_content`.`CONTENT_ID`,					`custom_envelope_content`.`LAST_UPDATED_STAMP`,			`custom_envelope_content`.`LAST_UPDATED_TX_STAMP`,
`custom_envelope_content`.`CREATED_STAMP`,				`custom_envelope_content`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`custom_envelope_content`;

INSERT INTO `[:TARGET_DB:]`.`custom_envelope_detail`
(`QUOTE_ID`,					`PRODUCTION_TIME`,				`APPROX_SHIP_DATE`,					`WEIGHT`,
`CARTON_QUANTITY`,				`COMMENT`,						`INTERNAL_NOTES`,					`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,		`CREATED_STAMP`,				`CREATED_TX_STAMP`)

SELECT
`custom_envelope_detail`.`QUOTE_ID`,					`custom_envelope_detail`.`PRODUCTION_TIME`,					`custom_envelope_detail`.`APPROX_SHIP_DATE`,			`custom_envelope_detail`.`WEIGHT`,
`custom_envelope_detail`.`CARTON_QUANTITY`,				`custom_envelope_detail`.`COMMENT`,							`custom_envelope_detail`.`INTERNAL_NOTES`,				`custom_envelope_detail`.`LAST_UPDATED_STAMP`,
`custom_envelope_detail`.`LAST_UPDATED_TX_STAMP`,		`custom_envelope_detail`.`CREATED_STAMP`,					`custom_envelope_detail`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`custom_envelope_detail`;

INSERT INTO `[:TARGET_DB:]`.`custom_envelope_price`
(`QUOTE_PRICE_ID`,				`QUOTE_ID`,				`QUANTITY`,				`PRICE`,
`PRICE_TYPE`,					`SEQUENCE_NUM`,			`EXPIRE_DATE`,			`LAST_UPDATED_STAMP`,
`LAST_UPDATED_TX_STAMP`,		`CREATED_STAMP`,		`CREATED_TX_STAMP`)

SELECT
`custom_envelope_price`.`QUOTE_PRICE_ID`,				`custom_envelope_price`.`QUOTE_ID`,			`custom_envelope_price`.`QUANTITY`,				`custom_envelope_price`.`PRICE`,
`custom_envelope_price`.`PRICE_TYPE`,					`custom_envelope_price`.`SEQUENCE_NUM`,		`custom_envelope_price`.`EXPIRE_DATE`,			`custom_envelope_price`.`LAST_UPDATED_STAMP`,
`custom_envelope_price`.`LAST_UPDATED_TX_STAMP`,		`custom_envelope_price`.`CREATED_STAMP`,	`custom_envelope_price`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`custom_envelope_price`;

INSERT INTO `[:TARGET_DB:]`.`custom_envelope_window`
(`ENVELOPE_WINDOW_ID`,			`QUOTE_ID`,				`STANDARD_POSITION`,			`WIDTH`,					`HEIGHT`,
`LEFT_POSITION`,				`BOTTOM_POSITION`,		`SEQUENCE_NUM`,					`LAST_UPDATED_STAMP`,		`LAST_UPDATED_TX_STAMP`,
`CREATED_STAMP`,				`CREATED_TX_STAMP`)

SELECT
`custom_envelope_window`.`ENVELOPE_WINDOW_ID`,			`custom_envelope_window`.`QUOTE_ID`,			`custom_envelope_window`.`STANDARD_POSITION`,			`custom_envelope_window`.`WIDTH`,				`custom_envelope_window`.`HEIGHT`,
`custom_envelope_window`.`LEFT_POSITION`,				`custom_envelope_window`.`BOTTOM_POSITION`,		`custom_envelope_window`.`SEQUENCE_NUM`,				`custom_envelope_window`.`LAST_UPDATED_STAMP`,	`custom_envelope_window`.`LAST_UPDATED_TX_STAMP`,
`custom_envelope_window`.`CREATED_STAMP`,				`custom_envelope_window`.`CREATED_TX_STAMP`
FROM `[:SOURCE_DB:]`.`custom_envelope_window`;


SET FOREIGN_KEY_CHECKS=1;
SET UNIQUE_CHECKS=1;
SET AUTOCOMMIT=1;
COMMIT;