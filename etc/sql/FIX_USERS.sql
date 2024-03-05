DROP PROCEDURE IF EXISTS convert_data;

DELIMITER //
CREATE PROCEDURE convert_data()
BEGIN
	SET AUTOCOMMIT=0;
	SET UNIQUE_CHECKS=0;
	SET FOREIGN_KEY_CHECKS=0;

	-- Create a temp table of all party ids that have an order or have a non anonymous user login
	DROP TABLE IF EXISTS valid_party;
	CREATE TABLE valid_party (
		PARTY_ID varchar(30)
	);

	-- all order party ids
	REPLACE INTO valid_party
	SELECT distinct ul.party_id FROM user_login ul INNER JOIN order_header oh ON oh.created_by = ul.user_login_id;

	REPLACE INTO valid_party
	SELECT distinct party_id FROM order_role;

	-- all order partyids based on order contact mechs (due to bad data sometimes party associations are incorrect)
	REPLACE INTO valid_party
	SELECT distinct pcm.party_id from order_contact_mech ocm inner join party_contact_mech pcm on ocm.contact_mech_id = pcm.contact_mech_id;

	-- all non anonymous party ids, these people may be trade customers who never placed an order yet
	REPLACE INTO valid_party
	SELECT party_id from user_login where user_login_id not like '%\_ANONYMOUS\_%';

	-- Clean up user login
	DROP TABLE IF EXISTS user_login_temp;
	CREATE TABLE user_login_temp LIKE user_login;
	INSERT INTO user_login_temp
	select * from user_login where party_id in (select party_id from valid_party);
	DROP TABLE user_login;
	RENAME TABLE user_login_temp TO user_login;

	-- Clean up party
	DROP TABLE IF EXISTS party_temp;
	CREATE TABLE party_temp LIKE party;
	INSERT INTO party_temp
	select * from party where party_id in (select party_id from valid_party);
	DROP TABLE party;
	RENAME TABLE party_temp TO party;

	-- Clean up person
	DROP TABLE IF EXISTS person_temp;
	CREATE TABLE person_temp LIKE person;
	INSERT INTO person_temp
	select * from person where party_id in (select party_id from valid_party);
	DROP TABLE person;
	RENAME TABLE person_temp TO person;

	DROP TABLE IF EXISTS party_contact_mech_temp;
	CREATE TABLE party_contact_mech_temp LIKE party_contact_mech;
	INSERT INTO party_contact_mech_temp
	select * from party_contact_mech where party_id in (select party_id from valid_party);
	DROP TABLE party_contact_mech;
	RENAME TABLE party_contact_mech_temp TO party_contact_mech;

	-- Clean up party contact mech purpose
	DROP TABLE IF EXISTS party_contact_mech_purpose_temp;
	CREATE TABLE party_contact_mech_purpose_temp LIKE party_contact_mech_purpose;
	INSERT INTO party_contact_mech_purpose_temp
	select * from party_contact_mech_purpose  where party_id in (select party_id from valid_party);
	DROP TABLE party_contact_mech_purpose;
	RENAME TABLE party_contact_mech_purpose_temp TO party_contact_mech_purpose;

	-- Clean up party role
	DROP TABLE IF EXISTS party_role_temp;
	CREATE TABLE party_role_temp LIKE party_role;
	INSERT INTO party_role_temp
	select * from party_role where party_id in (select party_id from valid_party);
	DROP TABLE party_role;
	RENAME TABLE party_role_temp TO party_role;

	DROP TABLE IF EXISTS contact_mech_temp;
	CREATE TABLE contact_mech_temp LIKE contact_mech;
	INSERT INTO contact_mech_temp
	select * from contact_mech where contact_mech_id in (select contact_mech_id from party_contact_mech);
	DROP TABLE contact_mech;
	RENAME TABLE contact_mech_temp TO contact_mech;

	-- Clean up cantact mech attribute
	DROP TABLE IF EXISTS contact_mech_attribute_temp;
	CREATE TABLE contact_mech_attribute_temp LIKE contact_mech_attribute;
	INSERT INTO contact_mech_attribute_temp
	select * from contact_mech_attribute where contact_mech_id in (select contact_mech_id from contact_mech);
	DROP TABLE contact_mech_attribute;
	RENAME TABLE contact_mech_attribute_temp TO contact_mech_attribute;

	-- Clean up postal address
	DROP TABLE IF EXISTS postal_address_temp;
	CREATE TABLE postal_address_temp LIKE postal_address;
	INSERT INTO postal_address_temp
	select * from postal_address where contact_mech_id in (select contact_mech_id from contact_mech);
	DROP TABLE postal_address;
	RENAME TABLE postal_address_temp TO postal_address;

	-- Clean up telecom number
	DROP TABLE IF EXISTS telecom_number_temp;
	CREATE TABLE telecom_number_temp LIKE telecom_number;
	INSERT INTO telecom_number_temp
	select * from telecom_number where contact_mech_id in (select contact_mech_id from contact_mech);
	DROP TABLE telecom_number;
	RENAME TABLE telecom_number_temp TO telecom_number;

	SET AUTOCOMMIT=1;
	SET UNIQUE_CHECKS=1;
	SET FOREIGN_KEY_CHECKS=1;
	commit;
END //
DELIMITER ;

CALL convert_data();

DROP PROCEDURE convert_data;


-- GET RID OF UNUSED CONTACT MECHS
DROP PROCEDURE IF EXISTS delete_data;

DELIMITER //
CREATE PROCEDURE delete_data()
BEGIN
	DECLARE done INT DEFAULT FALSE;
	DECLARE cmid VARCHAR(30);
    DECLARE deletes INT;
    DECLARE curs CURSOR FOR SELECT DISTINCT cm.contact_mech_id FROM contact_mech cm WHERE cm.contact_mech_type_id = 'POSTAL_ADDRESS' AND cm.contact_mech_id NOT IN (SELECT DISTINCT cmu.contact_mech_id FROM contact_mech_used cmu);
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

	SET AUTOCOMMIT=0;
	SET UNIQUE_CHECKS=0;
	SET FOREIGN_KEY_CHECKS=0;

    DROP TABLE IF EXISTS contact_mech_used;
	CREATE TABLE contact_mech_used (`CONTACT_MECH_ID` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '', PRIMARY KEY (`CONTACT_MECH_ID`));
    REPLACE INTO contact_mech_used (contact_mech_id)
    SELECT DISTINCT ocm.contact_mech_id FROM order_contact_mech ocm inner join contact_mech cm on ocm.contact_mech_id = cm.contact_mech_id where cm.contact_mech_type_id = 'POSTAL_ADDRESS';
    REPLACE INTO contact_mech_used (contact_mech_id)
    SELECT DISTINCT cc.contact_mech_id FROM credit_card cc inner join contact_mech cm on cc.contact_mech_id = cm.contact_mech_id where cm.contact_mech_type_id = 'POSTAL_ADDRESS' and cc.contact_mech_id is not null;
    REPLACE INTO contact_mech_used (contact_mech_id)
    SELECT DISTINCT oisg.contact_mech_id FROM order_item_ship_group oisg inner join contact_mech cm on oisg.contact_mech_id = cm.contact_mech_id where cm.contact_mech_type_id = 'POSTAL_ADDRESS' and oisg.contact_mech_id is not null;
    -- SELECT count(*) FROM contact_mech_used;

	OPEN curs;
    SET deletes = 0;
	read_loop: LOOP
		FETCH curs INTO cmid;
		IF done THEN
		  LEAVE read_loop;
		END IF;
        SET deletes = deletes + 1;
		-- DELETE FROM PARTY_CONTACT_MECH
		DELETE FROM party_contact_mech WHERE contact_mech_id = cmid;

		-- DELETE FROM PARTY_CONTACT_MECH_PURPOSE
		DELETE FROM party_contact_mech_purpose WHERE contact_mech_id = cmid;

		-- DELETE FROM POSTAL_ADDRESS
		DELETE FROM postal_address WHERE contact_mech_id = cmid;

		-- DELETE FROM CONTACT_MECH_ATTRIBUTE
		DELETE FROM contact_mech_attribute WHERE contact_mech_id = cmid;

		-- DELETE FROM CONTACT_MECH
		DELETE FROM contact_mech WHERE contact_mech_id = cmid;
	END LOOP;
	CLOSE curs;

    DROP TABLE IF EXISTS contact_mech_used;

    SET AUTOCOMMIT=1;
	SET UNIQUE_CHECKS=1;
	SET FOREIGN_KEY_CHECKS=1;
	COMMIT;
END //
DELIMITER ;

CALL delete_data();

DROP PROCEDURE delete_data;