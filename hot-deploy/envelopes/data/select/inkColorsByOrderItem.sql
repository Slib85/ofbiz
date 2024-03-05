SELECT 
	oia.order_id AS "Order ID", 
    oia.order_item_seq_id AS "Order Item ID",
    (
		SELECT 
			oiattr.attr_value
		FROM ordeR_item_attribute oiattr 
        WHERE oiattr.order_id = oia.order_id 
			AND oiattr.order_item_seq_id = oia.order_item_seq_id 
			AND oiattr.attr_name = "colorsFront"
	) AS "Colors Front",
    CASE WHEN oia.front_ink_color1 IS NULL THEN "" ELSE oia.front_ink_color1 END AS "Front Ink 1", 
    CASE WHEN oia.front_ink_color2 IS NULL THEN "" ELSE oia.front_ink_color2 END AS "Front Ink 2", 
    CASE WHEN oia.front_ink_color3 IS NULL THEN "" ELSE oia.front_ink_color3 END AS "Front Ink 3", 
    CASE WHEN oia.front_ink_color4 IS NULL THEN "" ELSE oia.front_ink_color4 END AS "Front Ink 4",
    (
		SELECT 
			oiattr.attr_value 
		FROM ordeR_item_attribute oiattr 
        WHERE oiattr.order_id = oia.order_id 
			AND oiattr.order_item_seq_id = oia.order_item_seq_id 
			AND oiattr.attr_name = "colorsBack"
	) AS "Colors Back", 
    CASE WHEN oia.back_ink_color1 IS NULL THEN "" ELSE oia.back_ink_color1 END AS "Back Ink 1",
    CASE WHEN oia.back_ink_color2 IS NULL THEN "" ELSE oia.back_ink_color2 END AS "Back Ink 2",
    CASE WHEN oia.back_ink_color3 IS NULL THEN "" ELSE oia.back_ink_color3 END AS "Back Ink 3",
    CASE WHEN oia.back_ink_color4 IS NULL THEN "" ELSE oia.back_ink_color4 END AS "Back Ink 4",
    oh.order_date AS "Order Date"
FROM order_item_artwork oia
INNER JOIN order_header oh ON oh.order_id = oia.order_id
WHERE oh.order_date BETWEEN "2018-10-02 00:00:00" AND "2019-10-02 23:59:59" LIMIT 99999