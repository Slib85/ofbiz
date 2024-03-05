DROP VIEW `nused_promo`;
DROP VIEW `unused_promo`;
DROP VIEW `unused_promos`;
DROP VIEW `used_promo`;
DROP VIEW `used_promos`;

CREATE VIEW `used_promo` AS
    select 
        sum(`product_promo_use`.`TOTAL_DISCOUNT_AMOUNT`) AS `sum(TOTAL_DISCOUNT_AMOUNT)`,
        `product_promo_use`.`PRODUCT_PROMO_ID` AS `used_promo_id`,
        `product_promo_use`.`ORDER_ID` AS `order_id`
    from
        `product_promo_use`
    group by `product_promo_use`.`PRODUCT_PROMO_ID`
    order by -(`product_promo_use`.`CREATED_STAMP`);


CREATE VIEW `nused_promo` AS
    select 
        `product_promo`.`PRODUCT_PROMO_ID` AS `nused_promo_id`
    from
        `product_promo`
    where
        ((`product_promo`.`PROMO_NAME` = _utf8'Samples Coupon')
            and (not (`product_promo`.`PRODUCT_PROMO_ID` in (select 
                `used_promo`.`used_promo_id` AS `used_promo_id`
            from
                `used_promo`)))
            and (`product_promo`.`CREATED_STAMP` < _utf8'2009-04-01 00:00:00.0'));


CREATE VIEW `unused_promo` AS
    select 
        `product_promo`.`PRODUCT_PROMO_ID` AS `product_promo_id`
    from
        `product_promo`
    where
        ((`product_promo`.`PROMO_NAME` = _utf8'Samples Coupon')
            and (not (`product_promo`.`PRODUCT_PROMO_ID` in (select 
                `used_promo`.`used_promo_id` AS `used_promo_id`
            from
                `used_promo`)))
            and (`product_promo`.`CREATED_STAMP` < _utf8'2009-04-01 00:00:00.0'));

CREATE VIEW `used_promos` AS
    select 
        sum(`product_promo_use`.`TOTAL_DISCOUNT_AMOUNT`) AS `sum(TOTAL_DISCOUNT_AMOUNT)`,
        `product_promo_use`.`PRODUCT_PROMO_ID` AS `used_promo_id`,
        `product_promo_use`.`ORDER_ID` AS `order_id`
    from
        `product_promo_use`
    group by `product_promo_use`.`PRODUCT_PROMO_ID`;


CREATE VIEW `unused_promos` AS
    select 
        `product_promo`.`PRODUCT_PROMO_ID` AS `product_promo_id`
    from
        `product_promo`
    where
        ((`product_promo`.`PROMO_NAME` = _utf8'Samples Coupon')
            and (not (`product_promo`.`PRODUCT_PROMO_ID` in (select 
                `used_promo`.`used_promo_id` AS `used_promo_id`
            from
                `used_promo`)))
            and (`product_promo`.`CREATED_STAMP` < _utf8'2009-04-01 00:00:00.0'));
