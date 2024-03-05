ALTER TABLE `qc_style_group` DROP COLUMN `THRU_DATE`, DROP COLUMN `FROM_DATE`;
ALTER TABLE `qc_vendor` DROP COLUMN `THRU_DATE`, DROP COLUMN `FROM_DATE`;
ALTER TABLE `qc_style` DROP COLUMN `THRU_DATE`, DROP COLUMN `FROM_DATE`;
ALTER TABLE `qc_material_type` DROP COLUMN `THRU_DATE`, DROP COLUMN `FROM_DATE`;
ALTER TABLE `qc_stock_type` DROP COLUMN `THRU_DATE`, DROP COLUMN `FROM_DATE`;
ALTER TABLE `qc_stock` DROP COLUMN `THRU_DATE`, DROP COLUMN `FROM_DATE`;
ALTER TABLE `qc_attribute` DROP COLUMN `THRU_DATE`, DROP COLUMN `FROM_DATE`;


update qc_style set style_name=style_description;

insert into qc_style_group_attribute_assoc (style_group_id, attribute_id, sequence_num) select style_group_id, attribute_id, '0' from qc_attribute_value where attribute_id != 'ITEM_UPCHARGE' and style_group_id is not null;

