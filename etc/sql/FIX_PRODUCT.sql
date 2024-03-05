DROP PROCEDURE IF EXISTS convert_data;

DELIMITER //
CREATE PROCEDURE convert_data()
BEGIN
	/* add column to store new product assoc (Takes about 40 seconds) */
	ALTER TABLE `product`
	ADD COLUMN `PARENT_PRODUCT_ID` varchar(30) NULL;

	/* add columns to store new price (Takes about 75 seconds) */
	ALTER TABLE `product_price`
	ADD COLUMN `QUANTITY` DECIMAL(20,0) NOT NULL DEFAULT -1,
	ADD COLUMN `COLOR` DECIMAL(20,0) NOT NULL DEFAULT -1,
	DROP PRIMARY KEY,
	ADD PRIMARY KEY (`PRODUCT_ID`,`PRODUCT_PRICE_TYPE_ID`,`PRODUCT_PRICE_PURPOSE_ID`,`CURRENCY_UOM_ID`,`PRODUCT_STORE_GROUP_ID`,
					 `FROM_DATE`, `QUANTITY`, `COLOR`);

	/*associate products (Takes about 30 seconds) */
	UPDATE `product` p INNER JOIN `product_assoc` pa
	   ON p.PRODUCT_ID = pa.PRODUCT_ID_TO
	   SET p.PARENT_PRODUCT_ID = pa.PRODUCT_ID;

	/* CLEANUP PRODUCT PRICE (Takes about xxx seconds) **/
	DROP TABLE IF EXISTS product_price_temp;
	CREATE TABLE product_price_temp LIKE product_price;
	INSERT INTO product_price_temp
	SELECT * FROM `product_price` WHERE PRODUCT_PRICE_TYPE_ID != 'LIST_PRICE';
	DROP TABLE product_price;
	RENAME TABLE product_price_temp TO product_price;

	-- Create Backup of quantities
	DROP TABLE IF EXISTS product_qty;
	CREATE TABLE product_qty (
		PRODUCT_ID varchar(255),
		NEW_PRODUCT_ID varchar(255),
		QUANTITY DECIMAL(20,0),
		COLORS varchar(255),
		WEIGHT varchar(255)
	);

	insert into `product_qty` (`PRODUCT_ID`, `NEW_PRODUCT_ID`, `QUANTITY`, `COLORS`, `WEIGHT`)
	SELECT 		DISTINCT p.PRODUCT_ID,
				pa.PRODUCT_ID,
				CASE WHEN pf.DESCRIPTION = 'CQ' THEN 1
					 ELSE cast(REPLACE(pf.DESCRIPTION, ',', '') as DECIMAL(20,0))
				END,
				CASE WHEN p.PRODUCT_ID LIKE '%P1C' THEN 1
					 WHEN p.PRODUCT_ID LIKE '%P2C' THEN 2
					 WHEN p.PRODUCT_ID LIKE '%P4C' THEN 4
				ELSE 0 END,
				p.weight
	FROM 		`product` p
	INNER JOIN  `product_feature_appl` pfa ON (p.PRODUCT_ID = pfa.PRODUCT_ID)
	INNER JOIN	`product_feature` pf ON (pfa.PRODUCT_FEATURE_ID = pf.PRODUCT_FEATURE_ID AND pf.PRODUCT_FEATURE_TYPE_ID = 'QUANTITY')
	INNER JOIN 	`product_price` pp ON (pp.PRODUCT_ID = p.PRODUCT_ID AND pp.PRODUCT_PRICE_TYPE_ID = 'DEFAULT_PRICE' AND pp.QUANTITY = -1)
	INNER JOIN 	`product_assoc` pa ON (p.PRODUCT_ID = pa.PRODUCT_ID_TO);


	/*create new product prices (Takes about 5min)*/
	SET UNIQUE_CHECKS=0;
	SET FOREIGN_KEY_CHECKS=0;
	INSERT INTO `product_price`
		(`PRODUCT_ID`,					`PRODUCT_PRICE_TYPE_ID`,	`CURRENCY_UOM_ID`,			`PRODUCT_STORE_GROUP_ID`,	`FROM_DATE`,
		`THRU_DATE`,					`PRICE`,					`CREATED_DATE`,				`CREATED_BY_USER_LOGIN`,	`LAST_MODIFIED_DATE`,
		`LAST_MODIFIED_BY_USER_LOGIN`,	`LAST_UPDATED_STAMP`,		`LAST_UPDATED_TX_STAMP`,	`CREATED_STAMP`,			`CREATED_TX_STAMP`,
		`PRODUCT_PRICE_PURPOSE_ID`,		`TERM_UOM_ID`,				`QUANTITY`,					`COLOR`)
	SELECT 		DISTINCT pa.PRODUCT_ID, 		pp.PRODUCT_PRICE_TYPE_ID, 	'USD',						'_NA_',						pp.FROM_DATE,
				pp.THRU_DATE, 					pp.PRICE,					pp.CREATED_DATE, 			pp.CREATED_BY_USER_LOGIN, 	pp.LAST_MODIFIED_DATE,
				pp.LAST_MODIFIED_BY_USER_LOGIN, pp.LAST_UPDATED_STAMP, 		pp.LAST_UPDATED_TX_STAMP,	pp.CREATED_STAMP, 			pp.CREATED_TX_STAMP,
				pp.PRODUCT_PRICE_PURPOSE_ID, 	pp.TERM_UOM_ID,		cast(REPLACE(pf.DESCRIPTION, ',', '') as DECIMAL(20,0)),
				CASE WHEN p.PRODUCT_ID LIKE '%P1C' THEN 1
					 WHEN p.PRODUCT_ID LIKE '%P2C' THEN 2
					 WHEN p.PRODUCT_ID LIKE '%P4C' THEN 4
				ELSE 0 END
	FROM 		`product` p
	INNER JOIN  `product_feature_appl` pfa ON (p.PRODUCT_ID = pfa.PRODUCT_ID)
	INNER JOIN	`product_feature` pf ON (pfa.PRODUCT_FEATURE_ID = pf.PRODUCT_FEATURE_ID AND pf.PRODUCT_FEATURE_TYPE_ID = 'QUANTITY')
	INNER JOIN 	`product_price` pp ON (pp.PRODUCT_ID = p.PRODUCT_ID AND pp.PRODUCT_PRICE_TYPE_ID = 'DEFAULT_PRICE' AND pp.QUANTITY = -1)
	INNER JOIN 	`product_assoc` pa ON (p.PRODUCT_ID = pa.PRODUCT_ID_TO)
	WHERE p.IS_VARIANT = 'N' AND p.IS_VIRTUAL = 'N' AND p.PRODUCT_ID NOT LIKE '%-CQ%' AND pf.DESCRIPTION != 'CQ';

	/*remove old data (Takes about ??? seconds)*/
	-- Clean up product_price
	DROP TABLE IF EXISTS product_price_temp;
	CREATE TABLE product_price_temp LIKE product_price;
	INSERT INTO product_price_temp
	select * FROM `product_price` WHERE `QUANTITY` <> -1;
	DROP TABLE product_price;
	RENAME TABLE product_price_temp TO product_price;

	-- Clean up product_feature_appl
	DROP TABLE IF EXISTS product_feature_appl_temp;
	CREATE TABLE product_feature_appl_temp LIKE product_feature_appl;
	INSERT INTO product_feature_appl_temp
	SELECT * FROM `product_feature_appl` WHERE PRODUCT_ID NOT IN (SELECT PRODUCT_ID FROM `product` WHERE IS_VARIANT = 'N' AND IS_VIRTUAL = 'N');
	DROP TABLE product_feature_appl;
	RENAME TABLE product_feature_appl_temp TO product_feature_appl;

	-- Clean up product
	DROP TABLE IF EXISTS product_temp;
	CREATE TABLE product_temp LIKE product;
	INSERT INTO product_temp
	SELECT * FROM `product` WHERE NOT (IS_VARIANT = 'N' AND IS_VIRTUAL = 'N');
	DROP TABLE product;
	RENAME TABLE product_temp TO product;


	/* remove lingering bundles (Takes about ??? seconds) */
	-- Clean up product_feature_appl
	DROP TABLE IF EXISTS product_feature_appl_temp;
	CREATE TABLE product_feature_appl_temp LIKE product_feature_appl;
	INSERT INTO product_feature_appl_temp
	SELECT * FROM `product_feature_appl` WHERE NOT(PRODUCT_ID LIKE '%-P1C' OR PRODUCT_ID LIKE '%-P2C' OR PRODUCT_ID LIKE '%-P4C');
	DROP TABLE product_feature_appl;
	RENAME TABLE product_feature_appl_temp TO product_feature_appl;

	DROP TABLE IF EXISTS product_price_temp;
	CREATE TABLE product_price_temp LIKE product_price;
	INSERT INTO product_price_temp
	select * FROM `product_price` WHERE NOT(PRODUCT_ID LIKE '%-P1C' OR PRODUCT_ID LIKE '%-P2C' OR PRODUCT_ID LIKE '%-P4C');
	DROP TABLE product_price;
	RENAME TABLE product_price_temp TO product_price;

	DROP TABLE IF EXISTS product_temp;
	CREATE TABLE product_temp LIKE product;
	INSERT INTO product_temp
	SELECT * FROM `product` WHERE NOT(PRODUCT_ID LIKE '%-P1C' OR PRODUCT_ID LIKE '%-P2C' OR PRODUCT_ID LIKE '%-P4C');
	DROP TABLE product;
	RENAME TABLE product_temp TO product;

	/*move carton quantities to product table (Takes about 120 seconds) */
	UPDATE `product` p
	INNER JOIN  `product_feature_appl` pfa ON (p.PRODUCT_ID = pfa.PRODUCT_ID)
	INNER JOIN	`product_feature` pf ON (pfa.PRODUCT_FEATURE_ID = pf.PRODUCT_FEATURE_ID AND pf.PRODUCT_FEATURE_TYPE_ID = 'CARTON_QUANTITY')
	SET p.CARTON_QTY = pf.DESCRIPTION;

	DROP TABLE IF EXISTS product_feature_appl_temp;
	CREATE TABLE product_feature_appl_temp LIKE product_feature_appl;
	INSERT INTO product_feature_appl_temp
	SELECT * FROM `product_feature_appl` where PRODUCT_FEATURE_ID NOT IN (SELECT PRODUCT_FEATURE_ID FROM `product_feature` where PRODUCT_FEATURE_TYPE_ID = 'CARTON_QUANTITY');
	DROP TABLE product_feature_appl;
	RENAME TABLE product_feature_appl_temp TO product_feature_appl;

	DROP TABLE IF EXISTS product_feature_temp;
	CREATE TABLE product_feature_temp LIKE product_feature;
	INSERT INTO product_feature_temp
	SELECT * FROM `product_feature` where PRODUCT_FEATURE_TYPE_ID != 'CARTON_QUANTITY';
	DROP TABLE product_feature;
	RENAME TABLE product_feature_temp TO product_feature;

	/*Fix product weights*/
	update `product` p
		INNER JOIN
		(SELECT
			new_product_id,
			CAST(avg(weight / quantity) as decimal (10 , 3 )) AS AV,
			CAST(max(weight / quantity) as decimal (10 , 3 )) AS MA,
			min(weight / quantity) AS MI
		FROM
			product_qty
		WHERE product_id not like '%-CQ%'
		group by new_product_id) as x ON x.new_product_id = p.product_id
	SET
		P.WEIGHT = X.AV;

	-- TRIM PRODUCT IDS
	update product set product_id = TRIM(product_id), parent_product_id = TRIM(parent_product_id);
	update product_price set product_id = TRIM(product_id);
	update product_feature_appl set product_id = TRIM(product_id);

	-- SET THE STORE FOR THE PRODUCT REVIEWS
	ALTER TABLE product_review ADD SALES_CHANNEL_ENUM_ID varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci;
	update product_review set SALES_CHANNEL_ENUM_ID = 'AE_SALES_CHANNEL' where product_store_id = '10000';
	update product_review set SALES_CHANNEL_ENUM_ID = 'ENV_SALES_CHANNEL' where product_store_id = '11000';
	update product_review set product_store_id = '10000';

	-- REMOVE BAD DATA
	DELETE FROM `PRODUCT_FEATURE_APPL` WHERE PRODUCT_ID IN (SELECT PRODUCT_ID FROM `PRODUCT` WHERE PRODUCT_ID LIKE '%-CQ%' AND IS_VARIANT = 'Y');
	DELETE FROM `PRODUCT_FEATURE_APPL` WHERE PRODUCT_ID IN (SELECT PRODUCT_ID FROM `PRODUCT` WHERE PRODUCT_ID LIKE '%-P3C%');
	DELETE FROM `PRODUCT_PRICE` WHERE PRODUCT_ID IN (SELECT PRODUCT_ID FROM `PRODUCT` WHERE PRODUCT_ID LIKE '%-CQ%' AND IS_VARIANT = 'Y');
	DELETE FROM `PRODUCT_PRICE` WHERE PRODUCT_ID IN (SELECT PRODUCT_ID FROM `PRODUCT` WHERE PRODUCT_ID LIKE '%-P3C%');
	DELETE FROM `PRODUCT` WHERE PRODUCT_ID LIKE '%-CQ%' AND IS_VARIANT = 'Y';
	DELETE FROM `PRODUCT` WHERE PRODUCT_ID LIKE '%-P3C%';

	UPDATE `PRODUCT` SET SALES_DISCONTINUATION_DATE = NOW() WHERE PRODUCT_ID IN ('FE4270-17');

	-- SETTING THE PARENT CATEGORY FOR MINI_ENVELOPES
	UPDATE PRODUCT_CATEGORY SET PRIMARY_PARENT_CATEGORY_ID='INVITATION' WHERE PRODUCT_CATEGORY_ID='MINI_ENVELOPES';

    SET UNIQUE_CHECKS=1;
	SET FOREIGN_KEY_CHECKS=1;

END //
DELIMITER ;

CALL convert_data();

DROP PROCEDURE convert_data;
