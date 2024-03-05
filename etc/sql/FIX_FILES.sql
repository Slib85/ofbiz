DROP PROCEDURE IF EXISTS convert_data;

DELIMITER //
CREATE PROCEDURE convert_data()
BEGIN
	SET AUTOCOMMIT=0;
	SET UNIQUE_CHECKS=0;
	SET FOREIGN_KEY_CHECKS=0;

	DROP TABLE IF EXISTS `order_item_content_temp`;

	CREATE TABLE `order_item_content_temp` (
	  `ORDER_ITEM_CONTENT_ID` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
	  `ORDER_ID` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
	  `ORDER_ITEM_SEQ_ID` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
	  `CONTENT_PURPOSE_ENUM_ID` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
	  `CONTENT_PATH` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
	  `CONTENT_NAME` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
	  `FROM_DATE` datetime NOT NULL,
	  `THRU_DATE` datetime DEFAULT NULL,
	  `LAST_UPDATED_STAMP` datetime DEFAULT NULL,
	  `LAST_UPDATED_TX_STAMP` datetime DEFAULT NULL,
	  `CREATED_STAMP` datetime DEFAULT NULL,
	  `CREATED_TX_STAMP` datetime DEFAULT NULL,
	  PRIMARY KEY (`ORDER_ID`,`ORDER_ITEM_SEQ_ID`,`CONTENT_PURPOSE_ENUM_ID`,`FROM_DATE`,`ORDER_ITEM_CONTENT_ID`),
	  KEY `OICNT_OI` (`ORDER_ID`,`ORDER_ITEM_SEQ_ID`),
	  KEY `OICNT_PENUM` (`CONTENT_PURPOSE_ENUM_ID`),
	  KEY `ORR_ITM_CNTT_TXSTP` (`LAST_UPDATED_TX_STAMP`),
	  KEY `ORR_ITM_CNTT_TXCRS` (`CREATED_TX_STAMP`),
	  CONSTRAINT `OICNT_OI` FOREIGN KEY (`ORDER_ID`, `ORDER_ITEM_SEQ_ID`) REFERENCES `order_item` (`ORDER_ID`, `ORDER_ITEM_SEQ_ID`),
	  CONSTRAINT `OICNT_PENUM` FOREIGN KEY (`CONTENT_PURPOSE_ENUM_ID`) REFERENCES `enumeration` (`ENUM_ID`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

	INSERT INTO order_item_content_temp
	(
		`ORDER_ID`,
		`ORDER_ITEM_SEQ_ID`,
		`ORDER_ITEM_CONTENT_ID`,
		`CONTENT_PURPOSE_ENUM_ID`,
		`FROM_DATE`,
		`THRU_DATE`,
		`CONTENT_NAME`,
		`CONTENT_PATH`,
		`LAST_UPDATED_STAMP`,
		`LAST_UPDATED_TX_STAMP`,
		`CREATED_STAMP`,
		`CREATED_TX_STAMP`
		)
	SELECT
		oic.order_id,
		oic.order_item_seq_id,
		oic.content_id,
		oic.content_purpose_enum_id,
		oic.from_date,
		oic.thru_date,
		dr.data_resource_name,
		dr.object_info,
		oic.last_updated_stamp,
		oic.last_updated_tx_stamp,
		oic.created_stamp,
		oic.created_tx_stamp
	FROM
		order_item_content AS oic
			INNER JOIN
		content AS co ON oic.content_id = co.content_id
			INNER JOIN
		data_resource AS dr ON co.data_resource_id = dr.data_resource_id;

	INSERT INTO order_item_content_temp
	(
		`ORDER_ID`,
		`ORDER_ITEM_SEQ_ID`,
		`ORDER_ITEM_CONTENT_ID`,
		`CONTENT_PURPOSE_ENUM_ID`,
		`FROM_DATE`,
		`THRU_DATE`,
		`CONTENT_NAME`,
		`CONTENT_PATH`,
		`LAST_UPDATED_STAMP`,
		`LAST_UPDATED_TX_STAMP`,
		`CREATED_STAMP`,
		`CREATED_TX_STAMP`
		)
	SELECT
	    oia.order_id,
	    oia.order_item_seq_id,
	    oiac.content_id,
	    'OIACPRP_FILE',
	    oiac.from_date,
	    oiac.thru_date,
	    dr.data_resource_name,
	    dr.object_info,
		oiac.last_updated_stamp,
		oiac.last_updated_tx_stamp,
		oiac.created_stamp,
		oiac.created_tx_stamp
	FROM
	    order_item_artwork AS oia
	        INNER JOIN
	    order_item_artwork_content AS oiac ON oia.order_item_artwork_id = oiac.order_item_artwork_id
	        INNER JOIN
	    content AS co ON oiac.content_id = co.content_id
	        INNER JOIN
	    data_resource AS dr ON co.data_resource_id = dr.data_resource_id
	WHERE oia.order_id is not null;

		SET AUTOCOMMIT=1;
		SET UNIQUE_CHECKS=1;
		SET FOREIGN_KEY_CHECKS=1;

		commit;
END //
DELIMITER ;

CALL convert_data();

DROP PROCEDURE convert_data;
