#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use DBI;
use Ofbiz;
use Data::Dumper;
use File::Basename;

my $ofbiz = Ofbiz->new(conf => "/usr/local/ofbiz/framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;

my $delete_handle = $dbh->prepare(qq{
	DELETE FROM color_warehouse
});

# retrieve variants products
my $variants_handle = $dbh->prepare(qq{
	select 		p.parent_product_id 			as virtual_product_id,
				p.product_name 					as product_name,
				p.primary_product_category_id	as primary_product_category_id,
				p.product_id 					as variant_product_id,
				p.on_sale 						as on_sale,
				p.on_clearance					as on_clearance,
				p.product_type_id 				as product_type_id,
				p.is_printable 					as is_printable,
				p.product_height 				as product_height,
				p.product_width 				as product_width,
				p.product_depth 				as product_depth,
				p.plain_price_description		as plain_price,
				p.print_price_description		as print_price,
				p.created_stamp                 as created_stamp,
				p.meta_title                    as meta_title,
				p.has_sample					as has_sample,
				p.has_white_ink					as has_white_ink,
				p.has_custom_qty				as has_custom_qty,
				case when p.created_stamp >= date_sub(now(), interval 12 month) then "Y" else "N" end as new
	from   		product p
	where  		p.is_virtual = "N"
				and p.is_variant = "Y"
				and year(p.created_stamp) >= "2007"
				and (p.sales_discontinuation_date > now() or p.sales_discontinuation_date is null)
				and p.parent_product_id is not null
				and (p.has_color_opt is null or p.has_color_opt = 'Y')
	order by 	p.parent_product_id
});

# retrieve product features
my $features_handle = $dbh->prepare(qq{
	select		pfa.product_feature_id,
				pf.product_feature_type_id,
				pf.description,
				pf.default_sequence_num
	from		product_feature_appl pfa
	inner join	product_feature pf
	on			pfa.product_feature_id = pf.product_feature_id
	where		pfa.product_id = ?
});

# retrieve product features
my $savings_handle = $dbh->prepare(qq{
	SELECT
		CASE
			WHEN
				((on_sale IS NOT NULL || on_sale != 'N') || (on_clearance IS NOT NULL || on_clearance != 'N')) && original_price IS NOT NULL
			THEN
				ROUND((1 - (price / original_price)) * 100)
			ELSE 0
		END
	FROM
		product_price pp
			INNER JOIN
		product p ON pp.product_id = p.product_id
	WHERE
		p.product_id = ?
			AND thru_date IS NULL
	ORDER BY colors 
	LIMIT 1;
});

# update color_warehouse table
my $insert_handle = $dbh->prepare(qq{
	insert into color_warehouse set
		virtual_product_id          = ?,
		variant_product_id          = ?,
		product_name                = ?,
		product_type_id             = ?,
		primary_product_category_id = ?,
		on_sale                     = ?,
		on_clearance                = ?,
		is_new                      = ?,
		color_feature_id            = ?,
		color_description           = ?,
		color_group_feature_id      = ?,
		color_group_description     = ?,
		collection_feature_id       = ?,
		collection_description      = ?,
		paper_weight_feature_id     = ?,
		paper_weight_description    = ?,
		paper_texture_feature_id    = ?,
		paper_texture_description   = ?,
		coating_feature_id    		= ?,
		coating_description   		= ?,
		size_feature_id             = ?,
		size_description            = ?,
		brand_feature_id            = ?,
		brand_description           = ?,
		sealing_method              = ?,
		percent_recycled            = ?,
		is_fsc_certified            = ?,
		is_sfi_certified            = ?,
		is_printable                = ?,
		sequence_num                = ?,
		product_height              = ?,
		product_width               = ?,
		product_depth               = ?,
		plain_price_description     = ?,
		print_price_description     = ?,
		min_colors                  = ?,
		max_colors                  = ?,
		has_sample                  = ?,
		has_white_ink               = ?,
		has_custom_qty              = ?,
		created_stamp               = ?,
		percent_savings             = ?
});

my @sqlColors = (
	q{update color_warehouse set color_hex_code='EFBD66' where color_description like '%Brown%'},
	q{update color_warehouse set color_hex_code='D9C7B4' where color_description like '%Taupe%'},
	q{update color_warehouse set color_hex_code='F1E5BF' where color_description like '%Champagne%'},
	q{update color_warehouse set color_hex_code='DADBDF' where color_description like '%Silver%'},
	q{update color_warehouse set color_hex_code='EAD29F' where color_description like '%Blonde%'},
	q{update color_warehouse set color_hex_code='F5D3DE' where color_description like '%Rose Quartz%'},
	q{update color_warehouse set color_hex_code='B14E6D' where color_description like '%Burgundy Leatherette%'},
	q{update color_warehouse set color_hex_code='CBBBEC' where color_description='Amethyst Metallic'},
	q{update color_warehouse set color_hex_code='737D87' where color_description='Anthracite Metallic'},
	q{update color_warehouse set color_hex_code='DAE8EB' where color_description='Aquamarine Metallic'},
	q{update color_warehouse set color_hex_code='D14A84' where color_description='Azalea Metallic'},
	q{update color_warehouse set color_hex_code='C1D9F3' where color_description='Baby Blue'},
	q{update color_warehouse set color_hex_code='E3E3E3' where color_description='Birch Translucent'},
	q{update color_warehouse set color_hex_code='DAE0E0' where color_description='Blue Parchment'},
	q{update color_warehouse set color_hex_code='FFD3B8' where color_description='Blush'},
	q{update color_warehouse set color_hex_code='F2ABBB' where color_description='Blush Translucent'},
	q{update color_warehouse set color_hex_code='263140' where color_description='Dark Blue Linen'},
	q{update color_warehouse set color_hex_code='263140' where color_description='Blue with Gold Foil'},
	q{update color_warehouse set color_hex_code='263140' where color_description='Dark Blue Linen - Gold Foil Square Border'},
	q{update color_warehouse set color_hex_code='263140' where color_description='Dark Blue Linen - Silver Foil Square Border'},
	q{update color_warehouse set color_hex_code='263140' where color_description='Deep Black Linen - Gold Foil Floral Border'},
	q{update color_warehouse set color_hex_code='263140' where color_description='Dark Blue Linen - Gold Foil Floral Border'},
	q{update color_warehouse set color_hex_code='263140' where color_description='Dark Blue Linen - Silver Foil Floral Border'},
	q{update color_warehouse set color_hex_code='5D67B0' where color_description='Boardwalk Blue'},
	q{update color_warehouse set color_hex_code='5EB7D9' where color_description='Bright Blue'},
	q{update color_warehouse set color_hex_code='E8CD3E' where color_description='Bright Canary'},
	q{update color_warehouse set color_hex_code='F86DAE' where color_description='Bright Fuschia'},
	q{update color_warehouse set color_hex_code='F5BD28' where color_description='Bright Gold'},
	q{update color_warehouse set color_hex_code='6BC784' where color_description='Bright Green'},
	q{update color_warehouse set color_hex_code='EA653C' where color_description='Bright Orange'},
	q{update color_warehouse set color_hex_code='D480E2' where color_description='Bright Purple'},
	q{update color_warehouse set color_hex_code='86D8D2' where color_description='Bright Teal'},
	q{update color_warehouse set color_hex_code='8A7059' where color_description='Bronze Metallic'},
	q{update color_warehouse set color_hex_code='392C23' where color_description='Teak Woodgrain'},
	q{update color_warehouse set color_hex_code='F3CACE' where color_description='Candy Pink'},
	q{update color_warehouse set color_hex_code='E9F15E' where color_description='Chartreuse Translucent'},
	q{update color_warehouse set color_hex_code='655444' where color_description='Chocolate'},
	q{update color_warehouse set color_hex_code='E1A67E' where color_description='Copper Metallic'},
	q{update color_warehouse set color_hex_code='ECE6D8' where color_description='Cream Parchment'},
	q{update color_warehouse set color_hex_code='614881' where color_description='Deep Purple'},
	q{update color_warehouse set color_hex_code='D0E131' where color_description='Electric Green'},
	q{update color_warehouse set color_hex_code='EAEE42' where color_description='Electric Yellow'},
	q{update color_warehouse set color_hex_code='EA653C' where color_description='Electric Orange'},
	q{update color_warehouse set color_hex_code='DF3C8B' where color_description='Electric Pink'},
	q{update color_warehouse set color_hex_code='FB2D3F' where color_description='Electric Cherry'},
	q{update color_warehouse set color_hex_code='AB937B' where color_description='Oak Woodgrain'},
	q{update color_warehouse set color_hex_code='678D74' where color_description='Emerald Metallic'},
	q{update color_warehouse set color_hex_code='9DBF69' where color_description='Fairway Metallic'},
	q{update color_warehouse set color_hex_code='DA7436' where color_description='Flame Metallic'},
	q{update color_warehouse set color_hex_code='F5D791' where color_description='Gold Metallic'},
	q{update color_warehouse set color_hex_code='EBDEA9' where color_description='Gold Parchment'},
	q{update color_warehouse set color_hex_code='E4B95D' where color_description='Gold Translucent'},
	q{update color_warehouse set color_hex_code='EFC95A' where color_description='Goldenrod'},
	q{update color_warehouse set color_hex_code='D8D3CF' where color_description='Gray Parchment'},
	q{update color_warehouse set color_hex_code='75797C' where color_description='Sterling Gray Linen'},
	q{update color_warehouse set color_hex_code='E5F0D0' where color_description='Green Parchment'},
	q{update color_warehouse set color_hex_code='BC9F73' where color_description like '%Grocery Bag%'},
	q{update color_warehouse set color_hex_code='C3444F' where color_description like '%Holiday Red%'},
	q{update color_warehouse set color_hex_code='EBEBEB' where color_description='Iridescent Translucent'},
	q{update color_warehouse set color_hex_code='CB4252' where color_description='Jupiter Metallic'},
	q{update color_warehouse set color_hex_code='AAE4CE' where color_description='Lagoon Metallic'},
	q{update color_warehouse set color_hex_code='BCCC8E' where color_description='Leaf Translucent'},
	q{update color_warehouse set color_hex_code='FFFBA9' where color_description='Lemonade'},
	q{update color_warehouse set color_hex_code='DD56A3' where color_description='Magenta'},
	q{update color_warehouse set color_hex_code='BA006C' where color_description='Magenta Translucent'},
	q{update color_warehouse set color_hex_code='E17A38' where color_description='Mandarin'},
	q{update color_warehouse set color_hex_code='2D2D2D' where color_description like '%Midnight Black%'},
	q{update color_warehouse set color_hex_code='99916D' where color_description='Moss'},
	q{update color_warehouse set color_hex_code='F3F0DE' where color_description='Natural'},
	q{update color_warehouse set color_hex_code='F3F0DE' where color_description='Natural - 100% Recycled'},
	q{update color_warehouse set color_hex_code='F7F2DC' where color_description='Natural Linen'},
	q{update color_warehouse set color_hex_code='C9944E' where color_description='Ochre'},
	q{update color_warehouse set color_hex_code='BCAC5E' where color_description='Olive'},
	q{update color_warehouse set color_hex_code='E06730' where color_description='Orange Translucent'},
	q{update color_warehouse set color_hex_code='CED2EB' where color_description='Orchid'},
	q{update color_warehouse set color_hex_code='B3D8E1' where color_description='Pastel Blue'},
	q{update color_warehouse set color_hex_code='F7EC83' where color_description='Pastel Canary'},
	q{update color_warehouse set color_hex_code='DADAD8' where color_description='Pastel Gray'},
	q{update color_warehouse set color_hex_code='B0DBAE' where color_description='Pastel Green'},
	q{update color_warehouse set color_hex_code='ECBDC7' where color_description='Pastel Pink'},
	q{update color_warehouse set color_hex_code='EAEE42' where color_description='Pastel Yellow'},
	q{update color_warehouse set color_hex_code='EA653C' where color_description='Pastel Orange'},
	q{update color_warehouse set color_hex_code='B3D8E1' where color_description='Fluorescent Blue'},
	q{update color_warehouse set color_hex_code='F7EC83' where color_description='Fluorescent Canary'},
	q{update color_warehouse set color_hex_code='DADAD8' where color_description='Fluorescent Gray'},
	q{update color_warehouse set color_hex_code='B0DBAE' where color_description='Fluorescent Green'},
	q{update color_warehouse set color_hex_code='ECBDC7' where color_description='Fluorescent Pink'},
	q{update color_warehouse set color_hex_code='EAEE42' where color_description='Fluorescent Yellow'},
	q{update color_warehouse set color_hex_code='EA653C' where color_description='Fluorescent Orange'},
	q{update color_warehouse set color_hex_code='F1DCDB' where color_description='Pink Parchment'},
	q{update color_warehouse set color_hex_code='E4E3E1' where color_description='Platinum Translucent'},
	q{update color_warehouse set color_hex_code='A877A4' where color_description='Punch Metallic'},
	q{update color_warehouse set color_hex_code='F4F0E4' where color_description='Quartz Metallic'},
	q{update color_warehouse set color_hex_code='3E6643' where color_description='Racing Green'},
	q{update color_warehouse set color_hex_code='F1CCD4' where color_description='Rose Quartz Metallic'},
	q{update color_warehouse set color_hex_code='A72E35' where color_description='Ruby Red'},
	q{update color_warehouse set color_hex_code='747EAF' where color_description='Sapphire Metallic'},
	q{update color_warehouse set color_hex_code='B3BFB5' where color_description='Slate'},
	q{update color_warehouse set color_hex_code='72716D' where color_description='Smoke'},
	q{update color_warehouse set color_hex_code='EDE279' where color_description='Split Pea'},
	q{update color_warehouse set color_hex_code='F1DCB1' where color_description='Spring Ochre Translucent'},
	q{update color_warehouse set color_hex_code='E7DAB8' where color_description='Stone'},
	q{update color_warehouse set color_hex_code='FBBE15' where color_description='Sunflower'},
	q{update color_warehouse set color_hex_code='BACDEB' where color_description='Surf Translucent'},
	q{update color_warehouse set color_hex_code='DACAA9' where color_description='Tan'},
	q{update color_warehouse set color_hex_code='B36855' where color_description='Terracotta'},
	q{update color_warehouse set color_hex_code='9A723F' where color_description='Tobacco'},
	q{update color_warehouse set color_hex_code='9BAAD5' where color_description='Vista Metallic'},
	q{update color_warehouse set color_hex_code='834C51' where color_description='Wine'},
	q{update color_warehouse set color_hex_code='C9363E' where color_description='Red Translucent'},
	q{update color_warehouse set color_hex_code='EFBD66' where color_group_description='Brown' and  collection_description='Kraft'},
	q{update color_warehouse set color_hex_code='D3D4D6' where color_description='Silver Metallic'},
	q{update color_warehouse set color_hex_code='91A15A' where color_description='Avocado'},
	q{update color_warehouse set color_hex_code='016C74' where color_description='Teal'},
	q{update color_warehouse set color_hex_code='D57342' where color_description='Rust'},
	q{update color_warehouse set color_hex_code='A02F41' where color_description='Garnet'},
	q{update color_warehouse set color_hex_code='F0EBD5' where color_description='Ivory'},
	q{update color_warehouse set color_hex_code='484848' where color_description='Black Satin'},
	q{update color_warehouse set color_hex_code='2A8AD6' where color_description='Boutique Blue'},
	q{update color_warehouse set color_hex_code='576E8E' where color_description='Dark Wash'},
	q{update color_warehouse set color_hex_code='AFC1CF' where color_description='Light Wash'},
	q{update color_warehouse set color_hex_code='DCE87C' where color_description like '%Glowing Green%'},
	q{update color_warehouse set color_hex_code='DF3C8B' where color_description='Hottie Pink'},
	q{update color_warehouse set color_hex_code='7D487E' where color_description='Purple Power'},
	q{update color_warehouse set color_hex_code='A5A5A5' where color_description='Mirror'},
	q{update color_warehouse set color_hex_code='E0E0E0' where color_description='Private Mirror'},
	q{update color_warehouse set color_hex_code='CBC6A9' where color_description='Silversand'},
	q{update color_warehouse set color_hex_code='31C7EA' where color_description='Trendy Teal'},
	q{update color_warehouse set color_hex_code='B8B8B8' where color_description='Gray Kraft'},
	q{update color_warehouse set color_hex_code='B8B8B8' where color_group_description='Gray' and  collection_description='Kraft'},
	q{update color_warehouse set color_hex_code='D7D7D7' where color_description='Crystal Metallic'},
	q{update color_warehouse set color_hex_code='DADBDF' where color_description='Silver'},
	q{update color_warehouse set color_hex_code='2D2D2D' where color_description='Black'},
	q{update color_warehouse set color_hex_code='ECE8DF' where color_description='Natural White - 100% Cotton'},
	q{update color_warehouse set color_hex_code='8DC65D' where color_description='Limelight'},
	q{update color_warehouse set color_hex_code='1095C0' where color_description='Pool'},
	q{update color_warehouse set color_hex_code='29426B' where color_description='Navy'},
	q{update color_warehouse set color_hex_code='7C4250' where color_description='Vintage Plum'},
	q{update color_warehouse set color_hex_code='944855' where color_description='Mulberry'},
	q{update color_warehouse set color_hex_code='A392B2' where color_description='Wisteria'},
	q{update color_warehouse set color_hex_code='AD83B5' where color_description='Bright Violet'},
	q{update color_warehouse set color_hex_code='F1EDE1' where color_description='70lb. Natural'},
	q{update color_warehouse set color_hex_code='72716D' where color_description='Smoke Flap'},
	q{update color_warehouse set color_hex_code='2D2D2D' where color_description='Black Flap'},
	q{update color_warehouse set color_hex_code='F3F0DE' where color_description='Natural Flap'},
	q{update color_warehouse set color_hex_code='DADBDF' where color_description='Silver Flap'},
	q{update color_warehouse set color_hex_code='F5BD28' where color_description='Gold Flap'},
	q{update color_warehouse set color_hex_code='A72E35' where color_description='Ruby Red Flap'},
	q{update color_warehouse set color_hex_code='F1CAF3' where color_description='Candy Pink Flap'},
	q{update color_warehouse set color_hex_code='F86DAE' where color_description='Fuchsia Flap'},
	q{update color_warehouse set color_hex_code='1095C0' where color_description='Pool Flap'},
	q{update color_warehouse set color_hex_code='29426B' where color_description='Navy Flap'},
	q{update color_warehouse set color_hex_code='E17A38' where color_description='Mandarin Flap'},
	q{update color_warehouse set color_hex_code='91A15A' where color_description='Avocado Seam'},
	q{update color_warehouse set color_hex_code='72716D' where color_description='Smoke Seam'},
	q{update color_warehouse set color_hex_code='2D2D2D' where color_description='Black Seam'},
	q{update color_warehouse set color_hex_code='DADBDF' where color_description='Silver Seam'},
	q{update color_warehouse set color_hex_code='F5BD28' where color_description='Gold Seam'},
	q{update color_warehouse set color_hex_code='A72E35' where color_description='Ruby Red Seam'},
	q{update color_warehouse set color_hex_code='F1CAF3' where color_description='Candy Pink Seam'},
	q{update color_warehouse set color_hex_code='F86DAE' where color_description='Fuchsia Seam'},
	q{update color_warehouse set color_hex_code='1095C0' where color_description='Pool Seam'},
	q{update color_warehouse set color_hex_code='29426B' where color_description='Navy Seam'},
	q{update color_warehouse set color_hex_code='E17A38' where color_description='Mandarin Seam'},
	q{update color_warehouse set color_hex_code='ED5D43' where color_description='Tangerine'},
	q{update color_warehouse set color_hex_code='B2DFDA' where color_description='Seafoam'},
	q{update color_warehouse set color_hex_code='E4EA64' where color_description='Bright Lemon'},
	q{update color_warehouse set color_hex_code='A6A19B' where color_description='32lb. Gray Kraft'},
	q{update color_warehouse set color_hex_code='2D2D2D' where color_description='Black Linen'},
	q{update color_warehouse set color_hex_code='A6A19B' where color_description='Gray Kraft w/ Peel & Seel®'},
	q{update color_warehouse set color_hex_code='DADADA' where color_description='Stainless Steel'},
	q{update color_warehouse set color_hex_code='E46DA5' where color_description='Bright Fuchsia'},
	q{update color_warehouse set color_hex_code='F28EA8' where color_description='Electric Pink'},
	q{update color_warehouse set color_hex_code='F3F0DE' where color_description='Natural w/Gold LUX Lining'},
	q{update color_warehouse set color_hex_code='066FE5' where color_description='Blue'},
	q{update color_warehouse set color_hex_code='B2251C' where color_description='Red'},
	q{update color_warehouse set color_hex_code='BA242D' where color_description='Chinese New Year'},
	q{update color_warehouse set color_hex_code='EBEE45' where color_description like '%Citrus%'},
	q{update color_warehouse set color_hex_code='559465' where color_description like '%Holiday Green%'},
	q{update color_warehouse set color_hex_code='F2EFE0' where color_description='Cream'},
	q{update color_warehouse set color_hex_code='EEE6D1' where color_description='Almond'},
	q{update color_warehouse set color_hex_code='F0F0EE' where color_description='Cottonwood'},
	q{update color_warehouse set color_hex_code='62B8DB' where color_description='Caribbean'},
	q{update color_warehouse set color_hex_code='D0E22E' where color_description='Wasabi'},
	q{update color_warehouse set color_hex_code='D0D2E9' where color_description='Lilac'},
	q{update color_warehouse set color_hex_code='D6C6A5' where color_description='Nude'},
	q{update color_warehouse set color_hex_code='DADBDF' where color_description='Silver Border'},
	q{update color_warehouse set color_hex_code='F5BD28' where color_description='Gold Border'},
	q{update color_warehouse set color_hex_code='1095C0' where color_description='Pool Border'},
	q{update color_warehouse set color_hex_code='E17A38' where color_description='Mandarin Border'},
	q{update color_warehouse set color_hex_code='F1CAF3' where color_description='Candy Pink Border'},
	q{update color_warehouse set color_hex_code='A72E35' where color_description='Ruby Red Border'},
	q{update color_warehouse set color_hex_code='72716D' where color_description='Smoke Border'},
	q{update color_warehouse set color_hex_code='91A15A' where color_description='Avocado Border'},
	q{update color_warehouse set color_hex_code='2D2D2D' where color_description='Black Border'},
	q{update color_warehouse set color_hex_code='2D2D2D' where color_description='Black w/Red LUX Lining'},
	q{update color_warehouse set color_hex_code='2D2D2D' where color_description='Black w/Silver LUX Lining'},
	q{update color_warehouse set color_hex_code='2D2D2D' where color_description='Black w/Gold LUX Lining'},
	q{update color_warehouse set color_hex_code='F3F0DE' where color_description='Natural w/Red LUX Lining'},
	q{update color_warehouse set color_hex_code='F3F0DE' where color_description='Natural w/Black LUX Lining'},
	q{update color_warehouse set color_hex_code='29426B' where color_description='Navy w/Silver LUX Lining'},
	q{update color_warehouse set color_hex_code='29426B' where color_description='Navy w/Gold LUX Lining'},
	q{update color_warehouse set color_hex_code='DADBDF' where color_description='Silver w/Black LUX Lining'},
	q{update color_warehouse set color_hex_code='72716D' where color_description='Smoke w/Silver LUX Lining'},
	q{update color_warehouse set color_hex_code='F3F0DE' where color_description='Natural with Gold Foil Lining'},
	q{update color_warehouse set color_hex_code='000000' where color_description='Espresso Linen'},
	q{update color_warehouse set color_hex_code='222222' where color_description='Black Gloss'},
	q{update color_warehouse set color_hex_code='000000' where color_description='Black Marblecoat'},
	q{update color_warehouse set color_hex_code='2A2A2A' where color_description='Deep Black Linen'},
	q{update color_warehouse set color_hex_code='2A2A2A' where color_description='Black with Gold Foil'},
	q{update color_warehouse set color_hex_code='2A2A2A' where color_description='Deep Black Linen - Gold Foil Square Border'},
	q{update color_warehouse set color_hex_code='2A2A2A' where color_description='Deep Black Linen - Silver Foil Square Border'},
	q{update color_warehouse set color_hex_code='2A2A2A' where color_description='Deep Black Linen - Silver Foil Floral Border'},
	q{update color_warehouse set color_hex_code='6A313A' where color_description='Burgundy Linen'},
	q{update color_warehouse set color_hex_code='490207' where color_description='Burgundy Marblecoat'},
	q{update color_warehouse set color_hex_code='ffffff' where color_description='Eggplant Linen'},
	q{update color_warehouse set color_hex_code='293749' where color_description='Navy Gloss'},
	q{update color_warehouse set color_hex_code='000310' where color_description='Blue Marblecoat'},
	q{update color_warehouse set color_hex_code='00231b' where color_description='Green Marblecoat'},
	q{update color_warehouse set color_hex_code='2C4636' where color_description='Deep Pine Linen'},
	q{update color_warehouse set color_hex_code='21141e' where color_description='Dark Purple Linen'},
	q{update color_warehouse set color_hex_code='386151' where color_description='Green Linen'},
	q{update color_warehouse set color_hex_code='386151' where color_description='Dark Green Linen'},
	q{update color_warehouse set color_hex_code='6a6092' where color_description='Wisteria'},
	q{update color_warehouse set color_hex_code='183668' where color_description='Dark Blue'},
	q{update color_warehouse set color_hex_code='d1d1d1' where color_description='Sterling Grey'},
	q{update color_warehouse set color_hex_code='ab9367' where color_description='18pt Grocery Bag'},
	q{update color_warehouse set color_hex_code='d1e400' where color_description='Wasabi'},
	q{update color_warehouse set color_hex_code='01803d' where color_description='Green'},
	q{update color_warehouse set color_hex_code='b94873' where color_description='Magenta'},
	q{update color_warehouse set color_hex_code='17263c' where color_description='Blue Linen'},
	q{update color_warehouse set color_hex_code='fe2720' where color_description='Red Gloss'},
	q{update color_warehouse set color_hex_code='eeeeee' where color_description='Bright White Gloss w/ Gold Foil'},
	q{update color_warehouse set color_hex_code='f9f6ed' where color_description='Natural Ivory Linen w/ Gold Foil'},
	q{update color_warehouse set color_hex_code='141414' where color_description='Deep Black Linen w/ Gold Foil '},
	q{update color_warehouse set color_hex_code='141414' where color_description='Deep Black Linen w/ Silver Foil'},
	q{update color_warehouse set color_hex_code='6f232f' where color_description='Burgundy Linen - Gold Foil Flourish'},
	q{update color_warehouse set color_hex_code='6f232f' where color_description='Burgundy Linen - Silver Foil Flourish '},
	q{update color_warehouse set color_hex_code='6f232f' where color_description='Burgundy Linen w/ Gold Foil'},
	q{update color_warehouse set color_hex_code='6f232f' where color_description='Burgundy Linen w/ Silver Foil'},
	q{update color_warehouse set color_hex_code='141414' where color_description='Dark Purple Linen w/ Gold Foil '},
	q{update color_warehouse set color_hex_code='192940' where color_description='Dark Blue Linen - Gold Foil Flourish'},
	q{update color_warehouse set color_hex_code='192940' where color_description='Dark Blue Linen - Silver Foil Flourish'},
	q{update color_warehouse set color_hex_code='192940' where color_description='Dark Blue Linen w/ Gold Foil'},
	q{update color_warehouse set color_hex_code='192940' where color_description='Dark Blue Linen w/ Silver Foil'},
	q{update color_warehouse set color_hex_code='2f4a3b' where color_description='Green Linen w/ Gold Foil'},
	q{update color_warehouse set color_hex_code='fafafa' where color_description='Bright White'},
	q{update color_warehouse set color_hex_code='f2efe7' where color_description='Vanilla Bean White'},
	q{update color_warehouse set color_hex_code='fdf6ef' where color_description='Ecru Natural'},
	q{update color_warehouse set color_hex_code='ffffff' where color_description='Bright White - 100% Recycled'},
	q{update color_warehouse set color_hex_code='f0ead5' where color_description='natural'},
	q{update color_warehouse set color_hex_code='eddec0' where color_description='Antique Natural'},
	q{update color_warehouse set color_hex_code='fef9e7' where color_description='Alabaster Natural'},
	q{update color_warehouse set color_hex_code='decfbd' where color_description='Sandcastle Natural '},
	q{update color_warehouse set color_hex_code='a58a71' where color_description='Grocery Bag Brown'},
	q{update color_warehouse set color_hex_code='bca185' where color_description='Warm Oatmeal'},
	q{update color_warehouse set color_hex_code='47352c' where color_description='Dark Espresso Brown'},
	q{update color_warehouse set color_hex_code='cdc9c8' where color_description='Gray Mist'},
	q{update color_warehouse set color_hex_code='c2c1bd' where color_description='Snowstorm Gray'},
	q{update color_warehouse set color_hex_code='777b7e' where color_description='Chelsea Gray'},
	q{update color_warehouse set color_hex_code='e4e4e4' where color_description='White Marble'},
	q{update color_warehouse set color_hex_code='bfbeba' where color_description='graystone'},
	q{update color_warehouse set color_hex_code='a79a94' where color_description='Storm Gray'},
	q{update color_warehouse set color_hex_code='646569' where color_description='Iron Gray'},
	q{update color_warehouse set color_hex_code='141213' where color_description='Black Marble'},
	q{update color_warehouse set color_hex_code='e4c9d0' where color_description='Ballet Pink'},
	q{update color_warehouse set color_hex_code='210308' where color_description='Rosewood Marble'},
	q{update color_warehouse set color_hex_code='743142' where color_description='rosewood'},
	q{update color_warehouse set color_hex_code='491d37' where color_description='Deep Maroon'},
	q{update color_warehouse set color_hex_code='97253e' where color_description='Chili Red'},
	q{update color_warehouse set color_hex_code='e4171a' where color_description='Cherry Red'},
	q{update color_warehouse set color_hex_code='291b29' where color_description='Fig Purple'},
	q{update color_warehouse set color_hex_code='a9c8db' where color_description='Artic Blue'},
	q{update color_warehouse set color_hex_code='b0b8cc' where color_description='Cornflower Blue'},
	q{update color_warehouse set color_hex_code='07243a' where color_description='Dark Blue Marble'},
	q{update color_warehouse set color_hex_code='30c7dc' where color_description='Turquoise Blue'},
	q{update color_warehouse set color_hex_code='09314e' where color_description='Nautical Blue'},
	q{update color_warehouse set color_hex_code='082948' where color_description='Inkwell Blue'},
	q{update color_warehouse set color_hex_code='044161' where color_description='Cobalt Blue'},
	q{update color_warehouse set color_hex_code='0e2d57' where color_description='Dark Navy'},
	q{update color_warehouse set color_hex_code='0e2d57' where color_description='Dark Navy Blue'},
	q{update color_warehouse set color_hex_code='0b4061' where color_description='Oxford Blue'},
	q{update color_warehouse set color_hex_code='c7c19f' where color_description='Sage Green'},
	q{update color_warehouse set color_hex_code='1d432f' where color_description='Dark Pine Green'},
	q{update color_warehouse set color_hex_code='6a9961' where color_description='Grashopper Green'},
	q{update color_warehouse set color_hex_code='fcd445' where color_description='Sunshine Yellow'},
	q{update color_warehouse set color_hex_code='292929' where color_description='Black Linen w/ Gold Foil'},
	q{update color_warehouse set color_hex_code='292929' where color_description='Black Linen - Gold Foil Square Border'},
	q{update color_warehouse set color_hex_code='292929' where color_description='Black Linen - Gold Foil Square Border'},
	q{update color_warehouse set color_hex_code='292929' where color_description='Black Linen - Silver Foil Floral Border'},
	q{update color_warehouse set color_hex_code='17273e' where color_description='Nautical Blue Linen'},
	q{update color_warehouse set color_hex_code='17273e' where color_description='Nautical Blue Linen w/ Gold Foil'},
	q{update color_warehouse set color_hex_code='292929' where color_description='Black Linen - Gold Foil Floral Border'},
	q{update color_warehouse set color_hex_code='17273e' where color_description='Nautical Blue Linen - Gold Foil Floral Border'},
	q{update color_warehouse set color_hex_code='17273e' where color_description='Nautical Blue Linen - Silver Foil Floral Border'},
	q{update color_warehouse set color_hex_code='292929' where color_description='Black Linen - Silver Foil Square Border'},
	q{update color_warehouse set color_hex_code='2a2829' where color_description='Black Linen w/ Silver Foil'},
	q{update color_warehouse set color_hex_code='17273e' where color_description='Nautical Blue Linen w/ Silver Foil'},
	q{update color_warehouse set color_hex_code='2D2D2D' where color_description='Black - Panel & Border Front'},
	q{update color_warehouse set color_hex_code='2D2D2D' where color_description='Black - Clear Front'},
	q{update color_warehouse set color_hex_code='2D2D2D' where color_description='Black - Ready Clip'},
	q{update color_warehouse set color_hex_code='2D2D2D' where color_description='Black - Slide Grip'},
	q{update color_warehouse set color_hex_code='df666d' where color_description='Red - Panel & Border Front'},
	q{update color_warehouse set color_hex_code='c54045' where color_description='Red - Clear Front'},
	q{update color_warehouse set color_hex_code='992120' where color_description='Red - 100% Recycled'},
	q{update color_warehouse set color_hex_code='1a3d5d' where color_description='Dark Blue - Panel & Border Front'},
	q{update color_warehouse set color_hex_code='0269a3' where color_description='Light Blue - Panel & Border Front'},
	q{update color_warehouse set color_hex_code='0269a3' where color_description='Light Blue - Clear Front'},
	q{update color_warehouse set color_hex_code='025a88' where color_description='Blue - Panel & Border Front'},
	q{update color_warehouse set color_hex_code='333d49' where color_description='Dark Blue - Clear Front'},
	q{update color_warehouse set color_hex_code='025867' where color_description='Blue - 100% Recycled'},
	q{update color_warehouse set color_hex_code='398D5B' where color_description='Green - Panel & Border Front'},
	q{update color_warehouse set color_hex_code='5CAA56' where color_description='Green - Clear Front'},
	q{update color_warehouse set color_hex_code='1f1f1d' where color_description='Damask Border'},
	q{update color_warehouse set color_hex_code='203451' where color_description LIKE '%Lapis Metallic%'},
	q{update color_warehouse set color_hex_code='153365' where color_description='Navy/Silver Metallic'},
	q{update color_warehouse set color_hex_code='000000' where color_description='Brasilia Black Woodgrain'},
	q{update color_warehouse set color_hex_code='131313' where color_description='Black Texture'},
	q{update color_warehouse set color_hex_code='551a20' where color_description='Maroon Texture'},
	q{update color_warehouse set color_hex_code='650e1f' where color_description='Burgundy Print'},
	q{update color_warehouse set color_hex_code='08325a' where color_description='Blue Print'},
	q{update color_warehouse set color_hex_code='000000' where color_description='Maroon'},
	q{update color_warehouse set color_hex_code='000000' where color_description='Royal Blue'},
	q{update color_warehouse set color_hex_code='000000' where color_description='Dark Green '},
	q{update color_warehouse set color_hex_code='143522' where color_description='Green Print'},
	q{update color_warehouse set color_hex_code='450e14' where color_description='Maroon Print'},
	q{update color_warehouse set color_hex_code='800210' where color_description='Red Print'},
	q{update color_warehouse set color_hex_code='1d2b48' where color_description='Navy Print'},
	q{update color_warehouse set color_hex_code='0a315c' where color_description='Navy Texture'}
);

# set product color count
my $color_handle = $dbh->prepare(qq{
	update 	product
	set 	color_count = ?
	where 	product_id  = ?
});

# sort ai color warehouse table
my $sort_color = $dbh->prepare(qq{
	select virtual_product_id, color_group_description, color_description from color_warehouse group by virtual_product_id, color_group_description, color_description asc
});

# updated ai color warehouse
my $updated_sort = $dbh->prepare(qq{
	update color_warehouse set sequence_num = ? where virtual_product_id = ? and color_group_description = ? and color_description = ?
});

# check if CUR & TIX is active if so, add TIX to currency
my $curTix = $dbh->prepare(qq{
	select virtual_product_id, variant_product_id, product_type_id, color_feature_id, color_description, color_group_feature_id, color_group_description, collection_feature_id, collection_description, paper_weight_feature_id, paper_weight_description, size_feature_id, size_description, percent_recycled, is_printable, is_fsc_certified, on_sale, is_new, sequence_num, min_colors, max_colors, primary_product_category_id, product_name, color_hex_code, plain_price_description, print_price_description, product_height, product_width from color_warehouse where virtual_product_id in ('TIX','CUR')
});

my $curTixInsert = $dbh->prepare(qq{
	insert into color_warehouse set virtual_product_id = ?, variant_product_id = ?, product_type_id = ?, color_feature_id = ?, color_description = ?, color_group_feature_id = ?, color_group_description = ?, collection_feature_id = ?, collection_description = ?, paper_weight_feature_id = ?, paper_weight_description = ?, size_feature_id = ?, size_description = ?, percent_recycled = ?, is_printable = ?, is_fsc_certified = ?, on_sale = ?, is_new = ?, sequence_num = ?, min_colors = ?, max_colors = ?, primary_product_category_id = ?, product_name = ?, color_hex_code = ?, plain_price_description = ?, print_price_description = ?, product_height = ?, product_width = ?
});

my $minColors = $dbh->prepare(qq{
	select colors from product_price where colors >= 1 and product_id = ? and thru_date is null order by colors asc limit 1
});

my $maxColors = $dbh->prepare(qq{
	select colors from product_price where colors >= 1 and product_id = ? and thru_date is null order by colors desc limit 1
});

my $s7Templates = $dbh->prepare(qq{
	select scene7_template_id from scene7_prod_assoc where product_id = ? limit 1
});

my $productCategory = $dbh->prepare(qq{
	select description from product_category where product_category_id = ?
});

my $getFirstProduct = $dbh->prepare(qq{
	select cw.variant_product_id from color_warehouse cw inner join product p on p.parent_product_id = cw.virtual_product_id where p.product_id = ? order by cw.sequence_num asc limit 1
});
my $updateMetaTitle = $dbh->prepare(qq{
	update product set meta_title = ? where product_id = ?
});

die "Couldn't prepare queries; aborting" unless defined $delete_handle  && defined $variants_handle && defined $insert_handle;

# start transaction
my $success = 1;
$success &&= $delete_handle->execute();
$success &&= $variants_handle->execute();
my %titles = ();

# iterate all virtual products and create table rows
while (my $product = $variants_handle->fetchrow_hashref()) {
	my $sequence_num = 0;
	my $virtual_product_id = $$product{"virtual_product_id"};
	my $variant_product_id = $$product{"variant_product_id"};
	my %features = ();

	#print "${variant_product_id}\n";
	$success &&= $features_handle->execute($variant_product_id);
	while(my $feature = $features_handle->fetchrow_hashref()) {
		$features{ lc $$feature{"product_feature_type_id"} } = $feature;
	}

	$success &&= $minColors->execute($variant_product_id);
	my $minColor = $minColors->fetchrow_array();

	$success &&= $maxColors->execute($variant_product_id);
	my $maxColor = $maxColors->fetchrow_array();

	#product is considered printable if it can print and has a s7 template
	my $isPrintable = "N";
	if(defined $$product{"is_printable"} && $$product{"is_printable"} eq "Y") {
		$success &&= $s7Templates->execute($variant_product_id);
		my $s7Temp = $s7Templates->fetchrow_hashref();

		if(!defined $s7Temp) {
			$success &&= $s7Templates->execute($virtual_product_id);
			$s7Temp = $s7Templates->fetchrow_hashref();
		}

		if(defined $s7Temp) {
			$isPrintable = "Y";
		}
	}

	#get percent savings
	$success &&= $savings_handle->execute($variant_product_id);
	my $percentSavings = $savings_handle->fetchrow_array();


	$success &&= $insert_handle->execute(
		$$product{"virtual_product_id"},
		$$product{"variant_product_id"},
		$$product{"product_name"} || undef,
		$$product{"product_type_id"} || undef,
		$$product{"primary_product_category_id"} || undef,
		$$product{"on_sale"} || undef,
		$$product{"on_clearance"} || undef,
		$$product{"new"} || "N",
		$features{"color"}->{"product_feature_id"},
		$features{"color"}->{"description"},
		$features{"color_group"}->{"product_feature_id"},
		$features{"color_group"}->{"description"},
		$features{"collection"}->{"product_feature_id"},
		$features{"collection"}->{"description"},
		$features{"paper_weight"}->{"product_feature_id"},
		$features{"paper_weight"}->{"description"},
		$features{"paper_texture"}->{"product_feature_id"},
		$features{"paper_texture"}->{"description"},
		$features{"coating"}->{"product_feature_id"},
		$features{"coating"}->{"description"},
		$features{"size"}->{"product_feature_id"},
		$features{"size"}->{"description"},
		$features{"brand"}->{"product_feature_id"},
		$features{"brand"}->{"description"},
		$features{"sealing_method"}->{"description"},
		$features{"recycled_percent"}->{"description"},
		$features{"fsc_certified"}->{"description"},
		$features{"sfi_certified"}->{"description"},
		$isPrintable,
		$features{"color"}->{"default_sequence_num"} || $sequence_num++,
		$$product{"product_height"} || undef,
		$$product{"product_width"} || undef,
		$$product{"product_depth"} || undef,
		$$product{"plain_price"} || undef,
		$$product{"print_price"} || undef,
		$minColor || 0,
		$maxColor || 0,
		$$product{"has_sample"} || undef,
		$$product{"has_white_ink"} || undef,
		$$product{"has_custom_qty"} || undef,
		$$product{"created_stamp"} || undef,
		$percentSavings || 0,
	) or die "Couldn't save data: " . $dbh->errstr;

	# Style asked for by Ian
	# Logic: Color. Product Name | Dimensions | Product Type | Envelopes.com

	my @skuListForMailing = ("10968", "13767", "1410", "14190", "1795", "27297", "37090", "41319", "41520", "41823", "45622", "45626", "45641", "45643", "45664", "5070", "73228", "73286", "74019", "75795", "75829", "75852", "75894", "75902", "75928", "75936", "75944", "76297", "76699", "77771", "79249", "82520", "84477", "84477W", "84485", "86339", "90014", "92663", "95538", "99966", "BP-CPM1419BL", "BP-CPM1419R", "BP-CPM1419Y", "BP-P2012B", "BP-P2012BL", "BP-P2012G", "BP-P2012GO", "BP-P2012K", "BP-P2012R", "BP-P2012W", "BP-P2012Y", "BP-P2024B", "BP-P2024BL", "BP-P2024G", "BP-P2024GO", "BP-P2024K", "BP-P2024R", "BP-P2024W", "BP-P2024Y", "BP-P3024B", "BP-P3024BL", "BP-P3024G", "BP-P3024GO", "BP-P3024K", "BP-P3024R", "BP-P3024W", "BP-P3024Y", "BP-TYC1013B", "BP-TYC1013G", "BP-TYC1013R", "BP-TYC1013S", "BP-TYC1013Y", "BP-TYC912B", "BP-TYC912G", "BP-TYC912R", "BP-TYC912S", "BP-TYC912Y", "CON-CMP", "CON-CMT", "CON-HDC1", "CON-HDC2", "CON-HDC3", "CON-PM1", "CON-PM2", "EXP-0205PL", "EXP-0214PL", "EXP-0215PL", "EXP-0220PL", "EXP-0221PL", "EXP-0222PL", "EXP-0251PL", "EXP-0257PL", "EXP-0258PL", "EXP-0265PL", "EXP-0275PL", "EXP-0282PL", "EXP-0288PL", "EXP-0321PL", "EXP-0322PL", "EXP-1602PL", "EXP-1607PL", "EXP-1608PL", "EXP-1615PL", "EXP-1639PL", "EXP-1651PL", "EXP-1677PL", "HD-E054", "HD-E055", "HD-E056", "LUX-KWBM-0", "LUX-KWBM-00", "LUX-KWBM-000", "LUX-KWBM-1", "LUX-KWBM-2", "LUX-KWBM-3", "LUX-KWBM-4", "LUX-KWBM-5", "LUX-KWBM-6", "LUX-KWBM-CD", "LUX-MMBM-BLK1", "LUX-MMBM-BLK3", "LUX-MMBM-BLK5", "LUX-MMBM-BLK7", "LUX-MMBM-SIL1", "LUX-MMBM-SIL3", "LUX-MMBM-SIL5", "LUX-MMBM-SIL7", "PC1101PL", "PC1102PL", "PC1123PL", "PC1127PL", "PC1129PL", "PC1131PL", "PC1176FC", "PC1180FC", "PC1192PL", "PC1207FC", "PC1211FC", "PC1250PL", "PC1251PL");

	if(exists(${{map {$_ => 1} @skuListForMailing}}{$variant_product_id}) && defined $$product{"product_name"}) {
		my $genColor = getGencolor($features{"color"}->{"description"});

		if(defined $features{"size_code"}->{"description"} && ($genColor ne "" || defined $features{"color_group"}->{"description"} || defined $features{"color"}->{"description"})) {
			my $prodName = $$product{"product_name"};
			$prodName =~ s/$features{"size"}->{"description"}\s//gi;
			$titles{$variant_product_id} = $features{"color"}->{"description"} . " " .  ($genColor eq "" ? $features{"color_group"}->{"description"} . " " : "") . $prodName . " | " . $features{"size"}->{"description"} . " | Shipping & Packaging";
			#print $titles{$variant_product_id} . "\n";
		}
	} elsif(($$product{"primary_product_category_id"} eq "PETALS" || $$product{"primary_product_category_id"} eq "POCKETS") && defined $$product{"product_name"} && defined $features{"size_code"}->{"description"}) {
		my $genColor = getGencolor($features{"color"}->{"description"});

		my $prodName = $$product{"product_name"};
		$prodName =~ s/$features{"size"}->{"description"}\s//gi;
		$prodName =~ s/Invitations//gi;
		$prodName =~ s/Envelopes//gi;
		$prodName =~ s/(?: \(.*?\)|\(.*?\) )//gi;
		$prodName = $prodName . ' Invitations';
		$prodName =~ s/ +/ /gi;
		if(defined $features{"size"}->{"description"} && ($genColor ne "" || defined $features{"color_group"}->{"description"})) {
			$titles{$variant_product_id} = $features{"color"}->{"description"} . " " .  ($genColor eq "" ? $features{"color_group"}->{"description"} . " " : "") . $prodName . " | " . $features{"size"}->{"description"} . " | Invitations/Announcements";
			#print $titles{$variant_product_id} . "\n";
		}
	} elsif($$product{"product_type_id"} eq "BOX" && defined $$product{"product_name"} && defined $features{"size_code"}->{"description"}) {
		my $genColor = getGencolor($features{"color"}->{"description"});

		my $prodName = $$product{"product_name"};
		$prodName =~ s/\s\($features{"size_code"}->{"description"}\)//gi;
		if(defined $features{"size_code"}->{"description"} && ($genColor ne "" || defined $features{"color_group"}->{"description"})) {
			$titles{$variant_product_id} = $features{"color"}->{"description"} . " " . (($genColor eq "") ? $features{"color_group"}->{"description"} : "") . " " . $prodName . " | " . $features{"size_code"}->{"description"} . " | Packaging";
			#print $titles{$variant_product_id} . "\n";
		}
	} elsif($$product{"product_type_id"} eq "ENVELOPE" && defined $$product{"primary_product_category_id"}) {
		my $genColor = getGencolor($features{"color"}->{"description"});

		$success &&= $productCategory->execute($$product{"primary_product_category_id"});
		my $catDesc = $productCategory->fetchrow_array();
		$catDesc =~ s/ Envelopes//g;
		if(defined $features{"color"}->{"description"} && defined $features{"color_group"}->{"description"} && defined $features{"size_code"}->{"description"} && defined $features{"size"}->{"description"}) {
			$titles{$variant_product_id} = $features{"color"}->{"description"} . " " . (($genColor eq "") ? $features{"color_group"}->{"description"} . " " : "") . $features{"size_code"}->{"description"} . " Envelopes | " . $catDesc . " | (" . $features{"size"}->{"description"} . ")";
			#print $titles{$variant_product_id} . "\n";
		}
	} elsif ($$product{"product_type_id"} eq "PAPER" && defined $$product{"product_name"} && $$product{"product_name"} =~ /^[aA]/ && (index($$product{"product_name"}, ' Flat Card') != -1 || index($$product{"product_name"}, ' Folded Card') != -1)) {
		my $genColor = getGencolor($features{"color"}->{"description"});

		if(defined $features{"color"}->{"description"} && defined $features{"color_group"}->{"description"} && defined $features{"size_code"}->{"description"} && defined $features{"size"}->{"description"}) {
			my $flatOrFolded = (index($$product{"product_name"}, ' Flat Card') != -1) ? 'Flat Cards' : 'Folded Cards';
			$titles{$variant_product_id} = $features{"color"}->{"description"} . (($genColor eq "") ? " " . $features{"color_group"}->{"description"} . " " : " ") . $features{"size_code"}->{"description"} . " " . $flatOrFolded . " | (" . $features{"size"}->{"description"} . ") | Notecards";
			#print $titles{$variant_product_id} . "\n";
		}
	} elsif ($$product{"product_type_id"} eq "PAPER" && defined $$product{"product_name"} && index($$product{"product_name"}, ' Cardstock') != -1) {
		my $genColor = getGencolor($features{"color"}->{"description"});

		if(defined $features{"paper_weight"}->{"description"} && defined $features{"color"}->{"description"} && defined $features{"color_group"}->{"description"} && defined $features{"size_code"}->{"description"} && defined $features{"size"}->{"description"}) {
			$titles{$variant_product_id} = $features{"size_code"}->{"description"} . " " . $features{"color"}->{"description"} . (($genColor eq "") ? " " . $features{"color_group"}->{"description"} . " " : " ") . "Cardstock | " . $features{"paper_weight"}->{"description"} . " | Stationery";
			#print $titles{$variant_product_id} . "\n";
		}
	} elsif ($$product{"product_type_id"} eq "PAPER" && defined $$product{"product_name"} && index($$product{"product_name"}, ' Paper') != -1) {
		my $genColor = getGencolor($features{"color"}->{"description"});

		if(defined $features{"paper_weight"}->{"description"} && defined $features{"color"}->{"description"} && defined $features{"color_group"}->{"description"} && defined $features{"size_code"}->{"description"} && defined $features{"size"}->{"description"}) {
			$titles{$variant_product_id} = $features{"size_code"}->{"description"} . " " . $features{"color"}->{"description"} . (($genColor eq "") ? " " . $features{"color_group"}->{"description"} . " " : " ") . "Paper | " . $features{"paper_weight"}->{"description"} . " | Stationery";
			#print $titles{$variant_product_id} . "\n";
		}
	}

	$success &&= $color_handle->execute($variants_handle->rows, $virtual_product_id) or die "Couldn't save data: " . $dbh->errstr;
}

my $sequenceNum = 40; #this is the number to start at after all hardcoded numbers have been used
my $oldVirtualId = "";
#select all the inserted data in color warehouse and then sort them by color group
$success &&= $sort_color->execute();
while (my($virtualProd, $colorGroup, $color) = $sort_color->fetchrow_array()) {
	if($oldVirtualId ne $virtualProd) {
		$sequenceNum = 30;
	}

	if(!defined $colorGroup) {
		$colorGroup = "";
	}
	if($virtualProd eq "CUR") { #for currency , we dont want the white to be first
		if($color eq "Gold Bow") {
			$success &&= $updated_sort->execute("1", $virtualProd, $colorGroup, $color);
		} else {
			$sequenceNum++;
			$success &&= $updated_sort->execute($sequenceNum, $virtualProd, $colorGroup, $color);
		}
		#} elsif($color eq "Holiday Red") {
		#	$success &&= $updated_sort->execute("0", $virtualProd, $colorGroup, $color);
	} elsif($colorGroup eq "White") {
		if($color eq "24lb. Bright White") {
			$success &&= $updated_sort->execute("1", $virtualProd, $colorGroup, $color);
		} elsif($color eq "28lb. Bright White") {
			$success &&= $updated_sort->execute("2", $virtualProd, $colorGroup, $color);
		} elsif($color eq "White w/ Peel & Seel®") {
			$success &&= $updated_sort->execute("3", $virtualProd, $colorGroup, $color);
		} elsif($color eq "60lb. White w/Peel & Press™") {
			$success &&= $updated_sort->execute("4", $virtualProd, $colorGroup, $color);
		} elsif($color eq "70lb. Bright White") {
			$success &&= $updated_sort->execute("5", $virtualProd, $colorGroup, $color);
		} elsif($color eq "80lb. Bright White") {
			$success &&= $updated_sort->execute("6", $virtualProd, $colorGroup, $color);
		} elsif($color eq "80lb. White w/Peel & Seel®") {
			$success &&= $updated_sort->execute("6", $virtualProd, $colorGroup, $color);
		} elsif($color eq "80lb. White w/Peel & Press™") {
			$success &&= $updated_sort->execute("7", $virtualProd, $colorGroup, $color);
		} elsif($color eq "White - 100% Recycled") {
			$success &&= $updated_sort->execute("8", $virtualProd, $colorGroup, $color);
		} elsif($color eq "Bright White - 100% Cotton") {
			$success &&= $updated_sort->execute("9", $virtualProd, $colorGroup, $color);
		} elsif($color eq "Bright White") {
			$success &&= $updated_sort->execute("10", $virtualProd, $colorGroup, $color);
		} elsif($color eq "White - 100% Recycled") {
			$success &&= $updated_sort->execute("11", $virtualProd, $colorGroup, $color);
		} elsif($color eq "11lb. Tyvek") {
			$success &&= $updated_sort->execute("12", $virtualProd, $colorGroup, $color);
		} elsif($color eq "14lb. Tyvek") {
			$success &&= $updated_sort->execute("13", $virtualProd, $colorGroup, $color);
		} elsif($color eq "Glossy White") {
			$success &&= $updated_sort->execute("14", $virtualProd, $colorGroup, $color);
		} elsif($color eq "White Linen") {
			$success &&= $updated_sort->execute("15", $virtualProd, $colorGroup, $color);
		} elsif($color eq "Birch Translucent") {
			$success &&= $updated_sort->execute("16", $virtualProd, $colorGroup, $color);
		} elsif($color =~ m/Foil/ || $color =~ m/LUX Lining/) {
			$success &&= $updated_sort->execute("17", $virtualProd, $colorGroup, $color);
		} else {
			$success &&= $updated_sort->execute("18", $virtualProd, $colorGroup, $color);
		}
	} elsif($colorGroup eq "Crystal") {
		$success &&= $updated_sort->execute("11", $virtualProd, $colorGroup, $color);
	} elsif($colorGroup eq "Natural") {
		if($color eq "Natural") {
			$success &&= $updated_sort->execute("19", $virtualProd, $colorGroup, $color);
		} elsif($color eq "Natural - 100% Recycled") {
			$success &&= $updated_sort->execute("20", $virtualProd, $colorGroup, $color);
		} elsif($color eq "Natural Linen") {
			$success &&= $updated_sort->execute("21", $virtualProd, $colorGroup, $color);
		} elsif($color eq "Natural White - 100% Cotton") {
			$success &&= $updated_sort->execute("22", $virtualProd, $colorGroup, $color);
		} else {
			$success &&= $updated_sort->execute("23", $virtualProd, $colorGroup, $color);
		}
	} elsif($colorGroup eq "Ivory") {
		$success &&= $updated_sort->execute("24", $virtualProd, $colorGroup, $color);
	} elsif($colorGroup eq "Brown") {
		if($color eq "24lb. Brown Kraft") {
			$success &&= $updated_sort->execute("25", $virtualProd, $colorGroup, $color);
		} elsif($color eq "28lb. Brown Kraft") {
			$success &&= $updated_sort->execute("26", $virtualProd, $colorGroup, $color);
		} else {
			$success &&= $updated_sort->execute("27", $virtualProd, $colorGroup, $color);
		}
	} elsif($colorGroup eq "Gray") {
		$success &&= $updated_sort->execute("28", $virtualProd, $colorGroup, $color);
	} elsif($colorGroup eq "Black") {
		$success &&= $updated_sort->execute("29", $virtualProd, $colorGroup, $color);
	} elsif($colorGroup eq "Clear") {
		$success &&= $updated_sort->execute("30", $virtualProd, $colorGroup, $color);
	} elsif($colorGroup eq "Silver") {
		$success &&= $updated_sort->execute("31", $virtualProd, $colorGroup, $color);
	} elsif($colorGroup eq "Gold") {
		$success &&= $updated_sort->execute("32", $virtualProd, $colorGroup, $color);
	} elsif($colorGroup eq "Red") {
		# } elsif($colorGroup eq "Red" && $color ne "Holiday Red") {
		$success &&= $updated_sort->execute("33", $virtualProd, $colorGroup, $color);
	} elsif($colorGroup eq "Pink") {
		$success &&= $updated_sort->execute("34", $virtualProd, $colorGroup, $color);
	} elsif($colorGroup eq "Purple") {
		$success &&= $updated_sort->execute("35", $virtualProd, $colorGroup, $color);
	} elsif($colorGroup eq "Teal") {
		$success &&= $updated_sort->execute("36", $virtualProd, $colorGroup, $color);
	} elsif($colorGroup eq "Blue") {
		$success &&= $updated_sort->execute("37", $virtualProd, $colorGroup, $color);
	} elsif($colorGroup eq "Green") {
		$success &&= $updated_sort->execute("38", $virtualProd, $colorGroup, $color);
	} elsif($colorGroup eq "Yellow") {
		$success &&= $updated_sort->execute("39", $virtualProd, $colorGroup, $color);
	} elsif($colorGroup eq "Orange") {
		$success &&= $updated_sort->execute("40", $virtualProd, $colorGroup, $color);
	} elsif($colorGroup eq "Airmail") {
		$success &&= $updated_sort->execute("41", $virtualProd, $colorGroup, $color);
	} elsif($colorGroup eq "Prints") {
		$success &&= $updated_sort->execute("42", $virtualProd, $colorGroup, $color);
	} else {
		$sequenceNum++;
		$success &&= $updated_sort->execute($sequenceNum, $virtualProd, $colorGroup, $color);
	}
	if($color eq "Snowflakes" || $color eq "Christmas Lights" || $color eq "Poinsettia" || $color eq "Ornaments") {
		$success &&= $updated_sort->execute("100", $virtualProd, $colorGroup, $color);
	}
	$oldVirtualId = $virtualProd;
	#print "$virtualProd, $colorGroup, $sequenceNum\n";
}

$success &&= $curTix->execute();
while (my($tix_virtual_product_id, $tix_variant_product_id, $tix_product_type_id, $tix_color_feature_id, $tix_color_description, $tix_color_group_feature_id, $tix_color_group_description, $tix_collection_feature_id, $tix_collection_description, $tix_paper_weight_feature_id, $tix_paper_weight_description, $tix_size_feature_id, $tix_size_description, $tix_percent_recycled, $tix_is_printable, $tix_is_fsc_certified, $tix_on_sale, $tix_is_new, $tix_sequence_num, $tix_mincolor, $tix_maxcolor, $tix_category, $tix_name, $tix_hex, $tix_plaindesc, $tix_printdesc, $tix_height, $tix_width) = $curTix->fetchrow_array()) {
	if(defined $tix_virtual_product_id && $tix_virtual_product_id eq "TIX") {
		$success &&= $curTixInsert->execute("CUR", $tix_variant_product_id, $tix_product_type_id, $tix_color_feature_id, $tix_color_description, $tix_color_group_feature_id, $tix_color_group_description, $tix_collection_feature_id, $tix_collection_description, $tix_paper_weight_feature_id, $tix_paper_weight_description, $tix_size_feature_id, $tix_size_description, $tix_percent_recycled, $tix_is_printable, $tix_is_fsc_certified, $tix_on_sale, $tix_is_new, $tix_sequence_num, $tix_mincolor, $tix_maxcolor, $tix_category, $tix_name, $tix_hex, $tix_plaindesc, $tix_printdesc, $tix_height, $tix_width);
	} elsif(defined $tix_virtual_product_id && $tix_virtual_product_id eq "CUR") {
		$success &&= $curTixInsert->execute("TIX", $tix_variant_product_id, $tix_product_type_id, $tix_color_feature_id, $tix_color_description, $tix_color_group_feature_id, $tix_color_group_description, $tix_collection_feature_id, $tix_collection_description, $tix_paper_weight_feature_id, $tix_paper_weight_description, $tix_size_feature_id, $tix_size_description, $tix_percent_recycled, $tix_is_printable, $tix_is_fsc_certified, $tix_on_sale, $tix_is_new, "20", $tix_mincolor, $tix_maxcolor, $tix_category, $tix_name, $tix_hex, $tix_plaindesc, $tix_printdesc, $tix_height, $tix_width);
	}
}

for(@sqlColors) {
	my $sth = $dbh->prepare($_);
	$sth->execute or die $dbh->errstr;
	$sth->finish;
}

foreach my $key(keys %titles) {
	$success &&= $getFirstProduct->execute($key);
	my $firstSKU = $getFirstProduct->fetchrow_array();
	if($firstSKU ne $key) {
		$success &&= $updateMetaTitle->execute($titles{$key}, $key);
		#print "$key : $titles{$key}\n";
	}
}

$variants_handle->finish();
$features_handle->finish();
$insert_handle->finish();
$color_handle->finish();
$sort_color->finish();
$updated_sort->finish();
$curTix->finish();
$curTixInsert->finish();
$s7Templates->finish();
$productCategory->finish();
$getFirstProduct->finish();
$updateMetaTitle->finish();
$savings_handle->finish();

# end transaction
my $result = ($success ? $dbh->commit : $dbh->rollback);

unless($result) {
	die "Couldn't finish transaction: " . $dbh->errstr;
}

$dbh->disconnect();

sub getGencolor {
	my $gColor = "";
	my @colorStr = ("Blue","Green","Red","Orange","Yellow","Purple","Pink","Brown","White","Natural","Gray","Black","Clear","Silver","Gold","Teal","Quartz","Crystal");
	my $colorDesc = $_[0];

	foreach(@colorStr) {
		if(index($colorDesc, $_) != -1) {
			$gColor = $_;
			last;
		}
	}

	return $gColor;
}
