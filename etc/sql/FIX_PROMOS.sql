DROP PROCEDURE IF EXISTS convert_data;

DELIMITER //
CREATE PROCEDURE convert_data()
BEGIN
	SET AUTOCOMMIT=0;
	SET UNIQUE_CHECKS=0;
	SET FOREIGN_KEY_CHECKS=0;

	-- Get rid of any actionenv site applications (wont exist in the new website)
	DELETE FROM product_store_promo_appl where product_store_id = '10000';
	UPDATE product_store_promo_appl set product_store_id = '10000';

	/*
		PROMO MAPPING
		NAME		NEW ID			OLD ID
		LOYALTY		10000			10000
		NONTAXABLE	10001			10011
		NONPROFIT	10002			10009
		TRADE 		10003			10010
		POSTNET		10004			10017
		ALLEGRA		10005			10019
	*/

	-- Update the promo use and applications to reflect the existing new replacement promos in the new system
	UPDATE order_adjustment set product_promo_id = '10001' where product_promo_id = '10011';
	UPDATE order_adjustment set product_promo_id = '10002' where product_promo_id = '10009';
	UPDATE order_adjustment set product_promo_id = '10003' where product_promo_id = '10010';
	UPDATE order_adjustment set product_promo_id = '10004' where product_promo_id = '10017';
	UPDATE order_adjustment set product_promo_id = '10005' where product_promo_id = '10019';

	UPDATE product_promo_use set product_promo_id = '10001' where product_promo_id = '10011';
	UPDATE product_promo_use set product_promo_id = '10002' where product_promo_id = '10009';
	UPDATE product_promo_use set product_promo_id = '10003' where product_promo_id = '10010';
	UPDATE product_promo_use set product_promo_id = '10004' where product_promo_id = '10017';
	UPDATE product_promo_use set product_promo_id = '10005' where product_promo_id = '10019';

	-- Delete the promos that already exist in the new system that are duplicates of the old
	DELETE FROM product_promo_code WHERE product_promo_id in ('10001','10011','10009','10010','10017','10019','10000');
	DELETE FROM product_store_promo_appl WHERE product_promo_id in ('10001','10011','10009','10010','10017','10019','10000');
	DELETE FROM product_promo_product WHERE product_promo_id in ('10001','10011','10009','10010','10017','10019','10000');
	DELETE FROM product_promo_category WHERE product_promo_id in ('10001','10011','10009','10010','10017','10019','10000');
	DELETE FROM product_promo_cond WHERE product_promo_id in ('10001','10011','10009','10010','10017','10019','10000');
	DELETE FROM product_promo_action WHERE product_promo_id in ('10001','10011','10009','10010','10017','10019','10000');
	DELETE FROM product_promo_rule WHERE product_promo_id in ('10001','10011','10009','10010','10017','10019','10000');
	DELETE FROM product_promo WHERE product_promo_id in ('10001','10011','10009','10010','10017','10019','10000');

	/*
		###########################################
		###########################################
		Create Backup of samples
	*/
	DROP TABLE IF EXISTS promos_temp;
	CREATE TABLE promos_temp (
		PRODUCT_PROMO_ID varchar(30),
		NEW_PRODUCT_PROMO_ID varchar(30),
		PRODUCT_PROMO_CODE_ID varchar(30),
		FROM_DATE datetime,
		THRU_DATE datetime,
		AMOUNT DECIMAL(18,6)
	);

	INSERT INTO promos_temp (`PRODUCT_PROMO_ID`,`PRODUCT_PROMO_CODE_ID`,`FROM_DATE`,`THRU_DATE`,`AMOUNT`, `NEW_PRODUCT_PROMO_ID`)
	SELECT pp.product_promo_id, ppc.product_promo_code_id, pspa.from_date, pspa.thru_date, ppa.amount,
			CASE WHEN ppa.amount = 1 THEN '9500'
				 WHEN ppa.amount = 2 THEN '9501'
				 WHEN ppa.amount = 3 THEN '9502'
				 WHEN ppa.amount = 4 THEN '9503'
				 WHEN ppa.amount = 5 THEN '9504'
				 ELSE '9500'
			END
	FROM product_promo pp
	INNER JOIN product_promo_code ppc on pp.product_promo_id = ppc.product_promo_id
	INNER JOIN product_store_promo_appl pspa on pp.product_promo_id = pspa.product_promo_id
	INNER JOIN product_promo_action ppa on pp.product_promo_id = ppa.product_promo_id
	WHERE pp.promo_name = 'Samples Coupon'
	GROUP BY ppc.product_promo_code_id
	ORDER BY ppc.created_stamp asc;

	/*
		###########################################
		###########################################
		Create Backup of black friday
	*/

	INSERT INTO promos_temp (`PRODUCT_PROMO_ID`,`PRODUCT_PROMO_CODE_ID`,`FROM_DATE`,`THRU_DATE`, `NEW_PRODUCT_PROMO_ID`)
	SELECT pp.product_promo_id, ppc.product_promo_code_id, pspa.from_date, pspa.thru_date, '9505'
	FROM product_promo pp
	INNER JOIN product_promo_code ppc on pp.product_promo_id = ppc.product_promo_id
	INNER JOIN product_store_promo_appl pspa on pp.product_promo_id = pspa.product_promo_id
	INNER JOIN product_promo_action ppa on pp.product_promo_id = ppa.product_promo_id
	WHERE pp.promo_name = 'Trade Black Friday Discount'
	GROUP BY ppc.product_promo_code_id
	ORDER BY ppc.created_stamp asc;

	-- delete all rows of sample coupons
	DROP TABLE IF EXISTS product_promo_code_email_temp;
	DROP TABLE IF EXISTS product_promo_code_party_temp;
	DROP TABLE IF EXISTS product_promo_code_temp;
	DROP TABLE IF EXISTS product_store_promo_appl_temp;
	DROP TABLE IF EXISTS product_promo_product_temp;
	DROP TABLE IF EXISTS product_promo_category_temp;
	DROP TABLE IF EXISTS product_promo_cond_temp;
	DROP TABLE IF EXISTS product_promo_action_temp;
	DROP TABLE IF EXISTS product_promo_rule_temp;

	CREATE TABLE product_promo_code_email_temp LIKE product_promo_code_email;
	INSERT INTO product_promo_code_email_temp
	SELECT * FROM product_promo_code_email where product_promo_code_id not in (select distinct ps.product_promo_code_id from promos_temp ps);
	DROP TABLE product_promo_code_email;
	RENAME TABLE product_promo_code_email_temp TO product_promo_code_email;

	CREATE TABLE product_promo_code_party_temp LIKE product_promo_code_party;
	INSERT INTO product_promo_code_party_temp
	SELECT * FROM product_promo_code_party where product_promo_code_id not in (select distinct ps.product_promo_code_id from promos_temp ps);
	DROP TABLE product_promo_code_party;
	RENAME TABLE product_promo_code_party_temp TO product_promo_code_party;

	CREATE TABLE product_promo_code_temp LIKE product_promo_code;
	INSERT INTO product_promo_code_temp
	SELECT * FROM product_promo_code where product_promo_id not in (select distinct ps.product_promo_id from promos_temp ps);
	DROP TABLE product_promo_code;
	RENAME TABLE product_promo_code_temp TO product_promo_code;

	CREATE TABLE product_store_promo_appl_temp LIKE product_store_promo_appl;
	INSERT INTO product_store_promo_appl_temp
	SELECT * FROM product_store_promo_appl where product_promo_id not in (select distinct ps.product_promo_id from promos_temp ps);
	DROP TABLE product_store_promo_appl;
	RENAME TABLE product_store_promo_appl_temp TO product_store_promo_appl;

	CREATE TABLE product_promo_product_temp LIKE product_promo_product;
	INSERT INTO product_promo_product_temp
	SELECT * FROM product_promo_product where product_promo_id not in (select distinct ps.product_promo_id from promos_temp ps);
	DROP TABLE product_promo_product;
	RENAME TABLE product_promo_product_temp TO product_promo_product;

	CREATE TABLE product_promo_category_temp LIKE product_promo_category;
	INSERT INTO product_promo_category_temp
	SELECT * FROM product_promo_category where product_promo_id not in (select distinct ps.product_promo_id from promos_temp ps);
	DROP TABLE product_promo_category;
	RENAME TABLE product_promo_category_temp TO product_promo_category;

	CREATE TABLE product_promo_cond_temp LIKE product_promo_cond;
	INSERT INTO product_promo_cond_temp
	SELECT * FROM product_promo_cond where product_promo_id not in (select distinct ps.product_promo_id from promos_temp ps);
	DROP TABLE product_promo_cond;
	RENAME TABLE product_promo_cond_temp TO product_promo_cond;

	CREATE TABLE product_promo_action_temp LIKE product_promo_action;
	INSERT INTO product_promo_action_temp
	SELECT * FROM product_promo_action where product_promo_id not in (select distinct ps.product_promo_id from promos_temp ps);
	DROP TABLE product_promo_action;
	RENAME TABLE product_promo_action_temp TO product_promo_action;

	CREATE TABLE product_promo_rule_temp LIKE product_promo_rule;
	INSERT INTO product_promo_rule_temp
	SELECT * FROM product_promo_rule where product_promo_id not in (select distinct ps.product_promo_id from promos_temp ps);
	DROP TABLE product_promo_rule;
	RENAME TABLE product_promo_rule_temp TO product_promo_rule;

	CREATE TABLE product_promo_temp LIKE product_promo;
	INSERT INTO product_promo_temp
	SELECT * FROM product_promo where product_promo_id not in (select distinct ps.product_promo_id from promos_temp ps);
	DROP TABLE product_promo;
	RENAME TABLE product_promo_temp TO product_promo;

	-- Lingering black friday promos with no ids
	DELETE FROM product_promo where promo_name = 'Trade Black Friday Discount' and product_promo_id != 'Trade Black Friday Discount';

	-- UPDATE ALL FREE SHIPPING COUPONS TO NEW METHOD --
	UPDATE product_promo_action set product_promo_action_enum_id = 'PROMO_SHIP_CHARGE', amount = 100 where product_promo_action_enum_id = 'PROMO_FREE_SHIPPING';

	-- REMOVE IS SAMPLE FROM ALL PROMO UNLESS IT IS A SAMPLE
	UPDATE product_promo set is_sample = null, is_stackable = 'Y' where is_sample = 'Y' and product_promo_id not in ('9500','9501','9502','9503','9504');

	SET AUTOCOMMIT=1;
	SET UNIQUE_CHECKS=1;
	SET FOREIGN_KEY_CHECKS=1;
END //
DELIMITER ;

CALL convert_data();

DROP PROCEDURE convert_data;