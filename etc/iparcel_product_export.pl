#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use DBI;
use Ofbiz;
use Net::FTP;
use Data::Dumper;
use Text::CSV_XS;

my $ofbiz = Ofbiz->new(conf => "/usr/local/ofbiz/framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;

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
		PRODUCT_WEIGHT AS weight,
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
		HAS_WHITE_INK
	from product where sales_discontinuation_date is null and is_variant = "Y"
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
				colors
	from		product_price
	where		product_id = ?
	and colors = 0
	order by    quantity, colors asc
});

my $category_handle = $dbh->prepare(qq{
	select      product_category_id
	from        product_category
	where       product_category_id = ?
});

die "Couldn't prepare queries; aborting" unless defined $products_handle;

my $success = 1;

my $header = [
	'SKU',
	'Name',
	'HSCodeUS',
	'HSCodeCA',
	'Country',
	'Price',
	'URL',
	'Weight',
	'Length',
	'Width',
	'Height',
	'ShipAlone',
	'Delete',
	'WeightInKilos',
	'Attribute1',
	'Attribute2',
	'Attribute3',
	'Attribute4',
	'Attribute5',
	'Attribute6'
];

my $outputCSV = Text::CSV_XS->new ({ binary => 1, eol => $/ });
open(ODCSV, '>/tmp/iparcel_product_export.csv') or die "Couldn't open filehandle: $!";
$outputCSV->print(\*ODCSV, $header);

$success &&= $products_handle->execute();
while (my $product = $products_handle->fetchrow_hashref()) {
	#regular data
	my %features = ();
	$success &&= $features_handle->execute($$product{"product_id"});
	while(my $feature = $features_handle->fetchrow_hashref()) {
		$features{ lc $$feature{"product_feature_type_id"} } = $feature;
	}

	$success &&= $category_handle->execute($$product{"primary_product_category_id"});
	my $category = $category_handle->fetchrow_array();

	my $longDesc = $$product{"long_description"} || "";
	$longDesc =~ s/\R//g;

	my $colorDesc = $$product{"color_description"} || "";
	$colorDesc =~ s/\R//g;

	$success &&= $price_handle->execute($$product{"product_id"});

	#for(my $i = 1; $i <= 5; $i++) {
	#	my $row = doRow(($$product{"product_id"} || "") . "-" . $i, ($$product{"internal_name"} || "") . " - Sample of " . $i, $i, ("http://www.envelopes.com/envelopes/control/product/~category_id=" . $$product{"primary_product_category_id"} . "/~product_id=" . $$product{"product_id"}), ($$product{"weight"}*$i), ($$product{"product_height"} || ""), ($$product{"product_width"} || ""), ($$product{"product_depth"} || ""));
	#	$outputCSV->print(\*ODCSV, $row);
	#}

	my $lowestQty = 0;
	my $pricePerUnit = 0;
	my @qtyArray = (1,2,3,4,5);

	while(my $price = $price_handle->fetchrow_hashref()) {
		if($lowestQty == 0) {
			$lowestQty = $$price{"quantity"};
			$pricePerUnit = $$price{"price"} / $$price{"quantity"};
		}
		#my $row = doRow(($$product{"product_id"} || "") . "-" . ($$price{"quantity"} || ""), ($$product{"internal_name"} || "") . " - Pack of " . ($$price{"quantity"} || ""), ($$price{"price"} || ""), ("http://www.envelopes.com/envelopes/control/product/~category_id=" . $$product{"primary_product_category_id"} . "/~product_id=" . $$product{"product_id"}), ($$product{"weight"}*$$price{"quantity"}), ($$product{"product_height"} || ""), ($$product{"product_width"} || ""), ($$product{"product_depth"} || ""));
		#$outputCSV->print(\*ODCSV, $row);

		push(@qtyArray, $$price{"quantity"});
	}

	my $row = doRow(($$product{"product_id"} || ""), ($$product{"internal_name"} || ""), $pricePerUnit, ("http://www.envelopes.com/envelopes/control/product/~category_id=" . $$product{"primary_product_category_id"} . "/~product_id=" . $$product{"product_id"}), ($$product{"weight"} || ""), ($$product{"product_height"} || ""), ($$product{"product_width"} || ""), ($$product{"product_depth"} || ""));
	$outputCSV->print(\*ODCSV, $row);

	#custom quantity now
	#if($lowestQty > 0) {
	#	for(my $i = $lowestQty; $i <= 50000; $i+=$lowestQty) {
	#		if( grep { $_ eq $i} @qtyArray ) {
	#          next;
	#        }
	#		my $row = doRow(($$product{"product_id"} || "") . "-" . $i, ($$product{"internal_name"} || "") . " - Pack of " . $i, $i*$pricePerUnit, ("http://www.envelopes.com/envelopes/control/product/~category_id=" . $$product{"primary_product_category_id"} . "/~product_id=" . $$product{"product_id"}), ($$product{"weight"}*$i), ($$product{"product_height"} || ""), ($$product{"product_width"} || ""), ($$product{"product_depth"} || ""));
	#		$outputCSV->print(\*ODCSV, $row);
	#	}
	#}
}

# returns name of the specialy shop this envelope belongs to
sub doRow {
	my $sku = $_[0];
	my $internalName = $_[1];
	my $price = $_[2];
	my $url = $_[3];
	my $weight = $_[4];
	my $height = $_[5];
	my $width = $_[6];
	my $depth = $_[7];

	return [
		$sku,
		$internalName,
		"",
		"",
		"United States",
		$price,
		$url,
		$weight,
		$height,
		$width,
		$depth,
		"false",
		"false",
		"false",
		"",
		"",
		"",
		"",
		"",
		""
	];
}

close ODCSV or die "Couldn't close filehandle: $!";

$features_handle->finish();
$price_handle->finish();
$products_handle->finish();
$category_handle->finish();
$dbh->disconnect();

my $ftp = Net::FTP->new('files.i-parcel.com', Debug => 0, Passive => 1) or die 'Cannot connect to some.host.name: $@';
$ftp->login('lsantos','n5nfSA5gRu') or die 'Cannot login ', $ftp->message;
$ftp->cwd('/incoming/catalog/new') or die 'Cannot change working directory', $ftp->message;
$ftp->put('/tmp/iparcel_product_export.csv') or die 'Cannot login ', $ftp->message;
$ftp->quit or die 'Error closing ftp connection: $!';