#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use DBI;
use Ofbiz;
use Data::Dumper;

my $ofbiz = Ofbiz->new(conf => "/usr/local/ofbiz/framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;

$dbh->do('set names utf8');

my $plainOnly = 0;
my $printOnly = 0;
if(defined $ARGV[0] && $ARGV[0] eq "PLAIN") {
	$plainOnly = 1;
} elsif(defined $ARGV[0] && $ARGV[0] eq "PRINT") {
	$printOnly = 1;
}

# retrieve virtual/variants products
my $products_handle = $dbh->prepare(qq{
	SELECT
	    PRODUCT_ID AS product_id,
	    PRODUCT_TYPE_ID AS product_type_id,
	    PRIMARY_PRODUCT_CATEGORY_ID AS primary_product_category_id,
	    MANUFACTURER_PARTY_ID AS manufacturer_party_id,
	    FACILITY_ID AS facility_id,
	    INTRODUCTION_DATE AS introduction_date,
	    RELEASE_DATE AS release_date,
	    SUPPORT_DISCONTINUATION_DATE AS support_discontinuation_date,
	    SALES_DISCONTINUATION_DATE AS sales_discontinuation_date,
	    SALES_DISC_WHEN_NOT_AVAIL AS sales_disc_when_not_avail,
	    INTERNAL_NAME AS internal_name,
	    BRAND_NAME AS brand_name,
	    COMMENTS AS comments,
	    PRODUCT_NAME AS product_name,
	    DESCRIPTION AS description,
	    LONG_DESCRIPTION AS long_description,
	    PRICE_DETAIL_TEXT AS price_detail_text,
	    SMALL_IMAGE_URL AS small_image_url,
	    MEDIUM_IMAGE_URL AS medium_image_url,
	    LARGE_IMAGE_URL AS large_image_url,
	    DETAIL_IMAGE_URL AS detail_image_url,
	    ORIGINAL_IMAGE_URL AS original_image_url,
	    DETAIL_SCREEN AS detail_screen,
	    INVENTORY_MESSAGE AS inventory_message,
	    REQUIRE_INVENTORY AS require_inventory,
	    QUANTITY_UOM_ID AS quantity_uom_id,
	    QUANTITY_INCLUDED AS quantity_included,
	    PIECES_INCLUDED AS pieces_included,
	    REQUIRE_AMOUNT AS require_amount,
	    FIXED_AMOUNT AS fixed_amount,
	    AMOUNT_UOM_TYPE_ID AS amount_uom_type_id,
	    WEIGHT_UOM_ID AS weight_uom_id,
	    PRODUCT_WEIGHT AS product_weight,
	    HEIGHT_UOM_ID AS height_uom_id,
	    PRODUCT_HEIGHT AS product_height,
	    SHIPPING_HEIGHT AS shipping_height,
	    WIDTH_UOM_ID AS width_uom_id,
	    PRODUCT_WIDTH AS product_width,
	    SHIPPING_WIDTH AS shipping_width,
	    DEPTH_UOM_ID AS depth_uom_id,
	    PRODUCT_DEPTH AS product_depth,
	    SHIPPING_DEPTH AS shipping_depth,
	    DIAMETER_UOM_ID AS diameter_uom_id,
	    PRODUCT_DIAMETER AS product_diameter,
	    PRODUCT_RATING AS product_rating,
	    RATING_TYPE_ENUM AS rating_type_enum,
	    RETURNABLE AS returnable,
	    TAXABLE AS taxable,
	    CHARGE_SHIPPING AS charge_shipping,
	    AUTO_CREATE_KEYWORDS AS auto_create_keywords,
	    INCLUDE_IN_PROMOTIONS AS include_in_promotions,
	    IS_VIRTUAL AS is_virtual,
	    IS_VARIANT AS is_variant,
	    VIRTUAL_VARIANT_METHOD_ENUM AS virtual_variant_method_enum,
	    ORIGIN_GEO_ID AS origin_geo_id,
	    REQUIREMENT_METHOD_ENUM_ID AS requirement_method_enum_id,
	    BILL_OF_MATERIAL_LEVEL AS bill_of_material_level,
	    RESERV_MAX_PERSONS AS reserv_max_persons,
	    RESERV2ND_P_P_PERC AS reserv2nd_p_p_perc,
	    RESERV_NTH_P_P_PERC AS reserv_nth_p_p_perc,
	    CONFIG_ID AS config_id,
	    CREATED_DATE AS created_date,
	    CREATED_BY_USER_LOGIN AS created_by_user_login,
	    LAST_MODIFIED_DATE AS last_modified_date,
	    LAST_MODIFIED_BY_USER_LOGIN AS last_modified_by_user_login,
	    IN_SHIPPING_BOX AS in_shipping_box,
	    DEFAULT_SHIPMENT_BOX_TYPE_ID AS default_shipment_box_type_id,
	    LOT_ID_FILLED_IN AS lot_id_filled_in,
	    ORDER_DECIMAL_QUANTITY AS order_decimal_quantity,
	    LAST_UPDATED_STAMP AS last_updated_stamp,
	    LAST_UPDATED_TX_STAMP AS last_updated_tx_stamp,
	    CREATED_STAMP AS created_stamp,
	    CREATED_TX_STAMP AS created_tx_stamp,
	    PARENT_PRODUCT_ID AS parent_product_id,
	    AVERAGE_CUSTOMER_RATING AS average_customer_rating,
	    META_TITLE AS meta_title,
	    META_DESCRIPTION AS meta_description,
	    META_KEYWORD AS meta_keyword,
	    COLOR_DESCRIPTION AS color_description,
	    COLOR_COUNT AS color_count,
	    PLAIN_PRICE_DESCRIPTION AS plain_price_description,
	    PRINT_PRICE_DESCRIPTION AS print_price_description,
	    IS_PRINTABLE AS is_printable,
	    HAS_RUSH_PRODUCTION AS has_rush_production,
	    CARTON_QTY AS carton_qty,
	    HAS_COLOR_OPT AS has_color_opt,
	    BIN_LOCATION AS bin_location,
	    IS_ASSEMBLY_ITEM AS is_assembly_item,
	    TAG_LINE AS tag_line,
	    ON_SALE AS on_sale,
	    HAS_SAMPLE AS has_sample,
	    HAS_WHITE_INK,
		SHOW_OUT_OF_STOCK_RECOMMENDATIONS
	from product where } . ((defined $ARGV[0] && $ARGV[0] eq "ALLOW_DISCONTINUED") ? qq{} : qq{sales_discontinuation_date is null and}) . qq{ is_variant = "Y"
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

# retrieve product price
my $price_handle = $dbh->prepare(qq{
	select		quantity,
				price,
				original_price,
				colors,
				upc
	from		product_price
	where		product_id = ?
	AND			thru_date is null
	order by    quantity, colors asc
});

my $category_handle = $dbh->prepare(qq{
	select      product_category_id as product_category_id,
                primary_parent_category_id as primary_parent_category_id
	from        product_category
	where       product_category_id = ?
});

die "Couldn't prepare queries; aborting" unless defined $products_handle;

my $header2 = "Code\tParent Code\tProduct Type\tCategory\tStyle\tProduct Page Name\tNetsuite Category\tColor Name\tCollection\tColor\tSize Code\tSize\tMetric Size\tPaper Weight\tPaper Texture\tQuantity\tPricing\tOriginal Price\tPercent Savings\tUnit Pricing\tInk Colors\tEach Weight\tStyle Description\tColor Family Description\tAvailability\tRecycled Content\tCompare to Brand\tWindow Size\tWindow Position\tSealing Method\tRecycled\tLaser\tInkjet\tCarton Quantity\tRecycled Percent\tTagline\tShape\tLabels Per Sheet\tTop Margin\tBottom Margin\tLeft Margin\tRight Margin\tCorner Radius\tVertical Spacing\tHorizontal Spacing\tBackslits\tRush Production\tUPC\tImage Url\tCreated Date\tURL\tShow Out Of Stock Recommendations";

open (OD2, '>/usr/local/ofbiz/exports/product_export2.tsv');
print OD2 $header2 . "\n";

my $success = 1;

$success &&= $products_handle->execute();
while (my $product = $products_handle->fetchrow_hashref()) {
	#regular data
	my %features = ();
	$success &&= $features_handle->execute($$product{"product_id"});
	while(my $feature = $features_handle->fetchrow_hashref()) {
		$features{ lc $$feature{"product_feature_type_id"} } = $feature;
	}

	$success &&= $category_handle->execute($$product{"primary_product_category_id"});
	my $category = $category_handle->fetchrow_hashref();

	my $longDesc = $$product{"long_description"} || "";
	$longDesc =~ s/\R//g;

	my $colorDesc = $$product{"color_description"} || "";
	$colorDesc =~ s/\R//g;

	my $priceStr = "";
	$success &&= $price_handle->execute($$product{"product_id"});
	while(my $price = $price_handle->fetchrow_hashref()) {
		if($plainOnly && $$price{"colors"} > 0) {
			next;
		} elsif($printOnly && $$price{"colors"} == 0) {
			next;
		}

		$priceStr = $priceStr . $$price{"quantity"} . "," . $$price{"colors"} . "," . $$price{"price"} . ";";

		my $quantity = $$price{"quantity"} || 1;
		my $pricePQty = $$price{"price"} || 1;
		my $originalPrice = $$price{"original_price"} || $pricePQty;

		print OD2 ($$product{"product_id"} || "") . "\t";
		print OD2 ($$product{"parent_product_id"} || "") . "\t";
		print OD2 ($$product{"product_type_id"} || "") . "\t";
		print OD2 ($$category{"primary_parent_category_id"} || "") . "\t";
		print OD2 ($$product{"primary_product_category_id"} || "") . "\t";
		print OD2 ($$product{"internal_name"} || "") . "\t";
		print OD2 ($features{"netsuite_category"}->{"description"} || "") . "\t";
		print OD2 ($features{"color"}->{"description"} || "") . "\t";
		print OD2 ($features{"collection"}->{"description"} || "") . "\t";
		print OD2 ($features{"color_group"}->{"description"} || "") . "\t";
		print OD2 ($features{"size_code"}->{"description"} || "") . "\t";
		print OD2 ($features{"size"}->{"description"} || "") . "\t";
		print OD2 ($features{"metric_size"}->{"description"} || "") . "\t";
		print OD2 ($features{"paper_weight"}->{"description"} || "") . "\t";
		print OD2 ($features{"paper_texture"}->{"description"} || "") . "\t";
		print OD2 $quantity . "\t";
		print OD2 $pricePQty . "\t";
		print OD2 $originalPrice . "\t";
		print OD2 ($originalPrice == 0 ? "0" : (1 - ($pricePQty / $originalPrice)) * 100) . "%\t";
		print OD2 ($pricePQty/$quantity) . "\t";
		print OD2 ($$price{"colors"} || "") . "\t";
		print OD2 ($$product{"product_weight"} || "") . "\t";
		print OD2 ($longDesc || "") . "\t";
		print OD2 ($colorDesc || "") . "\t";
		print OD2 ($features{"availability"}->{"description"} || "") . "\t";
		print OD2 ($features{"recycled_content"}->{"description"} || "") . "\t";
		print OD2 ($features{"compare_to_brand"}->{"description"}|| "") . "\t";
		print OD2 ($features{"window_size"}->{"description"} || "") . "\t";
		print OD2 ($features{"window_position"}->{"description"} || "") . "\t";
		print OD2 ($features{"sealing_method"}->{"description"} || "") . "\t";
		print OD2 ($features{"recycled"}->{"description"} || "") . "\t";
		print OD2 ($features{"laser"}->{"description"} || "") . "\t";
		print OD2 ($features{"inkjet"}->{"description"} || "") . "\t";
		print OD2 ($$product{"carton_qty"} || "") . "\t";
		print OD2 ($features{"recycled_percent"}->{"description"} || "") . "\t";
		print OD2 ($$product{"tag_line"} || "") . "\t";
		print OD2 ($features{"shape"}->{"description"} || "") . "\t";
		print OD2 ($features{"labels_per_sheet"}->{"description"} || "") . "\t";
		print OD2 ($features{"top_margin"}->{"description"} || "") . "\t";
		print OD2 ($features{"bottom_margin"}->{"description"} || "") . "\t";
		print OD2 ($features{"left_margin"}->{"description"} || "") . "\t";
		print OD2 ($features{"right_margin"}->{"description"} || "") . "\t";
		print OD2 ($features{"corner_radius"}->{"description"} || "") . "\t";
		print OD2 ($features{"vertical_spacing"}->{"description"} || "") . "\t";
		print OD2 ($features{"horizontal_spacing"}->{"description"} || "") . "\t";
		print OD2 ($features{"backslits"}->{"description"} || "") . "\t";
		print OD2 ($$product{"has_rush_production"} || "") . "\t";
		print OD2 ($$price{"upc"} || "") . "\t";
		print OD2 sprintf("https://www.envelopes.com/images/products/detail/%s%s", lc($$product{"product_id"}), ".jpg") . "\t";
		print OD2 ($$product{"created_stamp"} || "") . "\t";
		print OD2 ("http://www.envelopes.com/envelopes/control/product/~category_id=" . $$product{"primary_product_category_id"} . "/~product_id=" . $$product{"product_id"}) . "\t";
		print OD2 ($$product{"SHOW_OUT_OF_STOCK_RECOMMENDATIONS"} || "N") . "\n";
	}
}

close (OD2);

$features_handle->finish();
$price_handle->finish();
$products_handle->finish();
$category_handle->finish();
$dbh->disconnect();
