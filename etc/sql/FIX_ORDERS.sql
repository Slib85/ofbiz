DROP PROCEDURE IF EXISTS convert_data;

DELIMITER //
CREATE PROCEDURE convert_data()
BEGIN
	DECLARE bDone INT;
	DECLARE OID VARCHAR(20);
	DECLARE OISID VARCHAR(20);
	DECLARE IDESC VARCHAR(255);
	DECLARE i INT DEFAULT 0;
	DECLARE ctr INT DEFAULT 0;
	DECLARE str_len INT;
	DECLARE temp_str VARCHAR(200) DEFAULT '';
	DECLARE qty_str VARCHAR(200) DEFAULT '';
	DECLARE curs CURSOR FOR SELECT order_id, order_item_seq_id, ITEM_DESCRIPTION FROM order_item WHERE product_id = 'SAMPLE';
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET bDone = 1;

	SET AUTOCOMMIT=0;
	SET UNIQUE_CHECKS=0;
	SET FOREIGN_KEY_CHECKS=0;

	ALTER TABLE order_item MODIFY UNIT_PRICE DECIMAL(18,6), MODIFY UNIT_LIST_PRICE DECIMAL(18,6);

	DROP TABLE IF EXISTS order_item_samples;
	CREATE TABLE order_item_samples LIKE order_item;

	UPDATE `order_item` oi
	inner join `product_qty` pq on (oi.product_id = pq.product_id)
	SET oi.product_id = pq.new_product_id,
		oi.quantity = oi.quantity * pq.quantity,
		oi.unit_price = oi.unit_price / pq.quantity,
		oi.unit_list_price = oi.unit_price / pq.quantity
	where oi.product_id != 'SAMPLE' AND (oi.custom_quantity is null OR custom_quantity = 0);

	UPDATE `order_item` oi
	inner join `product_qty` pq on (oi.product_id = pq.product_id)
	SET oi.product_id = pq.new_product_id,
		oi.quantity = oi.custom_quantity,
		oi.unit_price = oi.unit_price / oi.custom_quantity,
		oi.unit_list_price = oi.unit_price / oi.custom_quantity
	where oi.product_id != 'SAMPLE' AND oi.custom_quantity != 0;

	OPEN curs;

	SET bDone = 0;
	REPEAT
	FETCH curs INTO OID, OISID, IDESC;
		IF bDone = 0 THEN
			SET ctr = 0;
			SET str_len=LENGTH(IDESC);
			SET i = (LENGTH(IDESC)-LENGTH(REPLACE(IDESC, ';', '')))/LENGTH(';');
			WHILE(ctr<i) DO
				SET ctr=ctr+1;
				SET temp_str = REPLACE(SUBSTRING(SUBSTRING_INDEX(IDESC, ';', ctr), LENGTH(SUBSTRING_INDEX(IDESC, ';',ctr - 1)) + 1), ';', '');
				SET qty_str = SUBSTRING(temp_str,instr(temp_str,"(") + 1, instr(temp_str,")")-instr(temp_str,"(")-1);
				SET temp_str = REPLACE(temp_str, concat('(',qty_str,')'),'');

				-- SELECT OID, OISID, temp_str, ctr, qty_str;
				SET qty_str = REPLACE(qty_str, "\\", "");
				IF qty_str = "" THEN
					SET qty_str = "1";
				ELSEIF qty_str = null THEN
					SET qty_str = "1";
				ELSEIF qty_str = "null" THEN
					SET qty_str = "1";
				END IF;

				INSERT INTO `order_item_samples`
				(`ORDER_ID`,					`ORDER_ITEM_SEQ_ID`,		`ORDER_ITEM_TYPE_ID`,		`BUDGET_ID`,
				`BUDGET_ITEM_SEQ_ID`,			`PRODUCT_ID`,				`PRODUCT_FEATURE_ID`,		`PROD_CATALOG_ID`,
				`PRODUCT_CATEGORY_ID`,			`QUOTE_ID`,					`QUOTE_ITEM_SEQ_ID`,		`SHOPPING_LIST_ID`,
				`SHOPPING_LIST_ITEM_SEQ_ID`,	`SUBSCRIPTION_ID`,			`DEPLOYMENT_ID`,			`QUANTITY`,
				`CUSTOM_QUANTITY`,				`CANCEL_QUANTITY`,			`SELECTED_AMOUNT`,			`UNIT_PRICE`,
				`UNIT_LIST_PRICE`,				`UNIT_AVERAGE_COST`,		`ITEM_DESCRIPTION`,			`COMMENTS`,
				`CORRESPONDING_PO_ID`,			`STATUS_ID`,				`SYNC_STATUS_ID`,			`ESTIMATED_SHIP_DATE`,
				`ESTIMATED_DELIVERY_DATE`,		`AUTO_CANCEL_DATE`,			`DONT_CANCEL_SET_DATE`,		`DONT_CANCEL_SET_USER_LOGIN`,
				`LAST_UPDATED_STAMP`,			`LAST_UPDATED_TX_STAMP`,	`CREATED_STAMP`,			`CREATED_TX_STAMP`,
				`EXTERNAL_ID`,					`ORDER_ITEM_GROUP_SEQ_ID`,	`IS_ITEM_GROUP_PRIMARY`,	`IS_PROMO`,
				`UNIT_RECURRING_PRICE`,			`IS_MODIFIED_PRICE`,		`RECURRING_FREQ_UOM_ID`,	`SHIP_BEFORE_DATE`,
				`SHIP_AFTER_DATE`,				`OVERRIDE_GL_ACCOUNT_ID`,	`IS_RUSH_PRODUCTION`,		`DUE_DATE`,
				`ARTWORK_SOURCE`,				`IS_REMOVE_FROM_SCHEDULE`,	`RESPONSE_STATUS_ID`)
				SELECT
				`ORDER_ID`,						CONCAT(`ORDER_ITEM_SEQ_ID`, ctr),		`ORDER_ITEM_TYPE_ID`,		`BUDGET_ID`,
				`BUDGET_ITEM_SEQ_ID`,			temp_str,					`PRODUCT_FEATURE_ID`,		`PROD_CATALOG_ID`,
				`PRODUCT_CATEGORY_ID`,			`QUOTE_ID`,					`QUOTE_ITEM_SEQ_ID`,		`SHOPPING_LIST_ID`,
				`SHOPPING_LIST_ITEM_SEQ_ID`,	`SUBSCRIPTION_ID`,			`DEPLOYMENT_ID`,			qty_str,
				`CUSTOM_QUANTITY`,				`CANCEL_QUANTITY`,			`SELECTED_AMOUNT`,			`UNIT_PRICE`,
				`UNIT_LIST_PRICE`,				`UNIT_AVERAGE_COST`,		`ITEM_DESCRIPTION`,			`COMMENTS`,
				`CORRESPONDING_PO_ID`,			`STATUS_ID`,				`SYNC_STATUS_ID`,			`ESTIMATED_SHIP_DATE`,
				`ESTIMATED_DELIVERY_DATE`,		`AUTO_CANCEL_DATE`,			`DONT_CANCEL_SET_DATE`,		`DONT_CANCEL_SET_USER_LOGIN`,
				`LAST_UPDATED_STAMP`,			`LAST_UPDATED_TX_STAMP`,	`CREATED_STAMP`,			`CREATED_TX_STAMP`,
				`EXTERNAL_ID`,					`ORDER_ITEM_GROUP_SEQ_ID`,	`IS_ITEM_GROUP_PRIMARY`,	`IS_PROMO`,
				`UNIT_RECURRING_PRICE`,			`IS_MODIFIED_PRICE`,		`RECURRING_FREQ_UOM_ID`,	`SHIP_BEFORE_DATE`,
				`SHIP_AFTER_DATE`,				`OVERRIDE_GL_ACCOUNT_ID`,	`IS_RUSH_PRODUCTION`,		`DUE_DATE`,
				`ARTWORK_SOURCE`,				`IS_REMOVE_FROM_SCHEDULE`,	`RESPONSE_STATUS_ID`
				FROM `order_item` WHERE  order_id = OID AND order_item_seq_id = OISID;

			END WHILE;
		END IF;
	UNTIL bDone END REPEAT;

	CLOSE curs;

	DROP TABLE IF EXISTS order_item_temp;
	CREATE TABLE order_item_temp LIKE order_item;
	INSERT INTO order_item_temp
	SELECT * FROM `order_item` where product_id != 'SAMPLE' ;
	INSERT INTO order_item_temp
	select * from `order_item_samples`;

	DROP TABLE IF EXISTS order_item;
	RENAME TABLE order_item_temp TO order_item;

	-- TRIM FIELDS
	update order_item set product_id = TRIM(product_id);

	-- UPDATE THE ITEM_JOB_NAME to be 200 chars or less
	DROP TABLE IF EXISTS artwork_job_name;
	CREATE TABLE `artwork_job_name` (
		`ORDER_ITEM_ARTWORK_ID` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
		`ITEM_JOB_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
	PRIMARY KEY (`order_item_artwork_id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

	INSERT INTO artwork_job_name (order_item_artwork_id, item_job_name)
	SELECT oia.order_item_artwork_id, SUBSTRING(oia.item_job_name, 1, 200) from order_item_artwork oia;

	UPDATE order_item_artwork set item_job_name = null;
	ALTER TABLE order_item_artwork MODIFY ITEM_JOB_NAME varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci;

	UPDATE order_item_artwork as oia inner join artwork_job_name ajn on oia.order_item_artwork_id = ajn.order_item_artwork_id set oia.item_job_name = ajn.item_job_name;
	DROP TABLE artwork_job_name;

	DROP TABLE IF EXISTS artwork_job_position;
	CREATE TABLE `artwork_job_position` (
		`ORDER_ITEM_ARTWORK_ID` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
		`ITEM_PRINT_POSITION` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
	PRIMARY KEY (`order_item_artwork_id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

	INSERT INTO artwork_job_position (order_item_artwork_id, item_print_position)
	SELECT oia.order_item_artwork_id, SUBSTRING(oia.item_print_position, 1, 200) from order_item_artwork oia;

	UPDATE order_item_artwork set item_print_position = null;
	ALTER TABLE order_item_artwork MODIFY ITEM_JOB_NAME varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci;

	UPDATE order_item_artwork as oia inner join artwork_job_position ajn on oia.order_item_artwork_id = ajn.order_item_artwork_id set oia.item_print_position = ajn.item_print_position;
	DROP TABLE artwork_job_position;

	-- NEW SYSTEM CARD TYPES ARE DIFF
	UPDATE `CREDIT_CARD` SET `CARD_TYPE` = 'CCT_VISA' WHERE `CARD_TYPE` IN ('Visa');
	UPDATE `CREDIT_CARD` SET `CARD_TYPE` = 'CCT_AMERICANEXPRESS' WHERE `CARD_TYPE` IN ('AmericanExpress','American Express','amex');
	UPDATE `CREDIT_CARD` SET `CARD_TYPE` = 'CCT_MASTERCARD' WHERE `CARD_TYPE` IN ('MasterCard','Master Card');
	UPDATE `CREDIT_CARD` SET `CARD_TYPE` = 'CCT_DISCOVER' WHERE `CARD_TYPE` IN ('Discover');


	-- UPDATING PRINT SETTINGS ATTRIBUTES TO USE NUMERIC AND BOOLEAN VALUES
	UPDATE ORDER_ITEM_ATTRIBUTE SET ATTR_NAME='isFullBleed', ATTR_VALUE='true' WHERE ATTR_VALUE='PFB';
	UPDATE ORDER_ITEM_ATTRIBUTE SET ATTR_VALUE=0 WHERE ATTR_NAME IN ('colorsBack', 'colorsFront') AND ATTR_VALUE = 'NONE' OR ATTR_VALUE IS NULL OR ATTR_VALUE = '';
	UPDATE ORDER_ITEM_ATTRIBUTE SET ATTR_VALUE=1 WHERE ATTR_NAME IN ('colorsBack', 'colorsFront') AND ATTR_VALUE = 'P1C';
	UPDATE ORDER_ITEM_ATTRIBUTE SET ATTR_VALUE=2 WHERE ATTR_NAME IN ('colorsBack', 'colorsFront') AND ATTR_VALUE = 'P2C';
	UPDATE ORDER_ITEM_ATTRIBUTE SET ATTR_VALUE=4 WHERE ATTR_NAME IN ('colorsBack', 'colorsFront') AND ATTR_VALUE = 'P4C';

	UPDATE ORDER_ITEM_ATTRIBUTE SET ATTR_VALUE='true' WHERE ATTR_VALUE IN ('Y','Yes') AND ATTR_NAME IN ('whiteInkBack','whiteInkFront','Folds');
	UPDATE ORDER_ITEM_ATTRIBUTE SET ATTR_VALUE='false' WHERE ATTR_VALUE IN ('N','No') AND ATTR_NAME IN ('whiteInkBack','whiteInkFront','Folds');

	UPDATE ORDER_ITEM_ATTRIBUTE SET ATTR_VALUE=0 WHERE ATTR_VALUE IN ('NONE') AND ATTR_NAME = 'Cuts';
	UPDATE ORDER_ITEM_ATTRIBUTE SET ATTR_VALUE=1 WHERE ATTR_VALUE IN ('One') AND ATTR_NAME = 'Cuts';
	UPDATE ORDER_ITEM_ATTRIBUTE SET ATTR_VALUE=2 WHERE ATTR_VALUE IN ('Two') AND ATTR_NAME = 'Cuts';

	UPDATE ORDER_ITEM_ATTRIBUTE SET ATTR_NAME = 'addresses' WHERE ATTR_NAME = 'addressing';
	UPDATE ORDER_ITEM_ATTRIBUTE SET ATTR_NAME = 'cuts' WHERE ATTR_NAME = 'Cuts';
	UPDATE ORDER_ITEM_ATTRIBUTE SET ATTR_NAME = 'isFolded' WHERE ATTR_NAME = 'Folds';

	-- UPDATE ORDER PAYMENT PREFERENCE FOR CHECK --
	UPDATE ORDER_PAYMENT_PREFERENCE SET PAYMENT_METHOD_TYPE_ID = 'PERSONAL_CHECK' WHERE PAYMENT_METHOD_TYPE_ID = 'EXT_OFFLINE' AND STATUS_ID = 'PAYMENT_NOT_RECEIVED';

	SET AUTOCOMMIT=1;
	SET UNIQUE_CHECKS=1;
	SET FOREIGN_KEY_CHECKS=1;
	commit;
END //
DELIMITER ;

CALL convert_data();

DROP PROCEDURE convert_data;
