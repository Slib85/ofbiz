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
	    p.PRODUCT_ID AS product_id,
	    p.PRODUCT_TYPE_ID AS product_type_id,
	    p.PRIMARY_PRODUCT_CATEGORY_ID AS primary_product_category_id,
	    p.MANUFACTURER_PARTY_ID AS manufacturer_party_id,
	    p.FACILITY_ID AS facility_id,
	    p.INTRODUCTION_DATE AS introduction_date,
	    p.RELEASE_DATE AS release_date,
	    p.SUPPORT_DISCONTINUATION_DATE AS support_discontinuation_date,
	    p.SALES_DISCONTINUATION_DATE AS sales_discontinuation_date,
	    p.SALES_DISC_WHEN_NOT_AVAIL AS sales_disc_when_not_avail,
	    p.INTERNAL_NAME AS internal_name,
	    p.BRAND_NAME AS brand_name,
	    p.COMMENTS AS comments,
	    p.PRODUCT_NAME AS product_name,
	    p.DESCRIPTION AS description,
	    p.LONG_DESCRIPTION AS long_description,
	    p.PRICE_DETAIL_TEXT AS price_detail_text,
	    p.SMALL_IMAGE_URL AS small_image_url,
	    p.MEDIUM_IMAGE_URL AS medium_image_url,
	    p.LARGE_IMAGE_URL AS large_image_url,
	    p.DETAIL_IMAGE_URL AS detail_image_url,
	    p.ORIGINAL_IMAGE_URL AS original_image_url,
	    p.DETAIL_SCREEN AS detail_screen,
	    p.INVENTORY_MESSAGE AS inventory_message,
	    p.REQUIRE_INVENTORY AS require_inventory,
	    p.QUANTITY_UOM_ID AS quantity_uom_id,
	    p.QUANTITY_INCLUDED AS quantity_included,
	    p.PIECES_INCLUDED AS pieces_included,
	    p.REQUIRE_AMOUNT AS require_amount,
	    p.FIXED_AMOUNT AS fixed_amount,
	    p.AMOUNT_UOM_TYPE_ID AS amount_uom_type_id,
	    p.WEIGHT_UOM_ID AS weight_uom_id,
	    p.PRODUCT_WEIGHT AS product_weight,
	    p.HEIGHT_UOM_ID AS height_uom_id,
	    p.PRODUCT_HEIGHT AS product_height,
	    p.SHIPPING_HEIGHT AS shipping_height,
	    p.WIDTH_UOM_ID AS width_uom_id,
	    p.PRODUCT_WIDTH AS product_width,
	    p.SHIPPING_WIDTH AS shipping_width,
	    p.DEPTH_UOM_ID AS depth_uom_id,
	    p.PRODUCT_DEPTH AS product_depth,
	    p.SHIPPING_DEPTH AS shipping_depth,
	    p.DIAMETER_UOM_ID AS diameter_uom_id,
	    p.PRODUCT_DIAMETER AS product_diameter,
	    p.PRODUCT_RATING AS product_rating,
	    p.RATING_TYPE_ENUM AS rating_type_enum,
	    p.RETURNABLE AS returnable,
	    p.TAXABLE AS taxable,
	    p.CHARGE_SHIPPING AS charge_shipping,
	    p.AUTO_CREATE_KEYWORDS AS auto_create_keywords,
	    p.INCLUDE_IN_PROMOTIONS AS include_in_promotions,
	    p.IS_VIRTUAL AS is_virtual,
	    p.IS_VARIANT AS is_variant,
	    p.VIRTUAL_VARIANT_METHOD_ENUM AS virtual_variant_method_enum,
	    p.ORIGIN_GEO_ID AS origin_geo_id,
	    p.REQUIREMENT_METHOD_ENUM_ID AS requirement_method_enum_id,
	    p.BILL_OF_MATERIAL_LEVEL AS bill_of_material_level,
	    p.RESERV_MAX_PERSONS AS reserv_max_persons,
	    p.RESERV2ND_P_P_PERC AS reserv2nd_p_p_perc,
	    p.RESERV_NTH_P_P_PERC AS reserv_nth_p_p_perc,
	    p.CONFIG_ID AS config_id,
	    p.CREATED_DATE AS created_date,
	    p.CREATED_BY_USER_LOGIN AS created_by_user_login,
	    p.LAST_MODIFIED_DATE AS last_modified_date,
	    p.LAST_MODIFIED_BY_USER_LOGIN AS last_modified_by_user_login,
	    p.IN_SHIPPING_BOX AS in_shipping_box,
	    p.DEFAULT_SHIPMENT_BOX_TYPE_ID AS default_shipment_box_type_id,
	    p.LOT_ID_FILLED_IN AS lot_id_filled_in,
	    p.ORDER_DECIMAL_QUANTITY AS order_decimal_quantity,
	    p.LAST_UPDATED_STAMP AS last_updated_stamp,
	    p.LAST_UPDATED_TX_STAMP AS last_updated_tx_stamp,
	    p.CREATED_STAMP AS created_stamp,
	    p.CREATED_TX_STAMP AS created_tx_stamp,
	    p.PARENT_PRODUCT_ID AS parent_product_id,
	    p.AVERAGE_CUSTOMER_RATING AS average_customer_rating,
	    p.META_TITLE AS meta_title,
	    p.META_DESCRIPTION AS meta_description,
	    p.META_KEYWORD AS meta_keyword,
	    p.COLOR_DESCRIPTION AS color_description,
	    p.COLOR_COUNT AS color_count,
	    p.PLAIN_PRICE_DESCRIPTION AS plain_price_description,
	    p.PRINT_PRICE_DESCRIPTION AS print_price_description,
	    p.IS_PRINTABLE AS is_printable,
	    p.HAS_RUSH_PRODUCTION AS has_rush_production,
	    p.CARTON_QTY AS carton_qty,
	    p.HAS_COLOR_OPT AS has_color_opt,
	    p.BIN_LOCATION AS bin_location,
	    p.IS_ASSEMBLY_ITEM AS is_assembly_item,
	    p.TAG_LINE AS tag_line,
	    p.ON_SALE AS on_sale,
	    p.HAS_SAMPLE AS has_sample,
	    p.HAS_WHITE_INK,
		CONCAT(IF(pws.AE = "Y" OR pws.ENVELOPES = "Y", "Envelopes, ", ""), IF(pws.FOLDERS = "Y", "Folders, ", "")) as website
	FROM product p
	INNER JOIN product_web_site pws ON pws.product_id = p.product_id
	WHERE p.is_variant = "Y"
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
				colors,
				upc
	from		product_price
	where		product_id = ?
	order by    quantity, colors asc
});

my $category_handle = $dbh->prepare(qq{
	select      product_category_id as product_category_id,
                primary_parent_category_id as primary_parent_category_id
	from        product_category
	where       product_category_id = ?
});

my $parentChildProductFeaturesAssoc_handle = $dbh->prepare(qq{
	SELECT 
		pf2.product_feature_type_id as parentFeatureName,
		pf2.description as parentFeatureDescription,
		pf.product_feature_type_id AS childFeatureName, 
		pf.description AS childFeatureDescription, 
		pfa.product_feature_id AS parentFeatureID, 
		pfa.product_feature_id_to AS childFeatureID 
	FROM product_feature pf
	INNER JOIN product_feature_assoc pfa 
		ON pf.product_feature_id = pfa.product_feature_id_to 
	INNER JOIN product_feature pf2 on pf2.product_feature_id = pfa.product_feature_id
	WHERE pfa.product_id = ?
	ORDER BY pfa.product_feature_id;
});

my $vendorProductData_handle = $dbh->prepare(qq{
    SELECT
        vp.vendor_party_id as vendorPartyID,
        GROUP_CONCAT(vp.vendor_product_id SEPARATOR ";") as vendorProductID
    FROM vendor_product vp
    INNER JOIN product p
        ON p.product_id = vp.product_id
    WHERE vp.product_id = ?
    ORDER BY vp.vendor_party_id
});

die "Couldn't prepare queries; aborting" unless defined $products_handle;


my $header2 = "Website\tCode\tParent Code\tProduct Type\tDiscontinuation Date\tCategory\tStyle\tProduct Page Name\tNetsuite Category\tColor Name\tCoating\tCollection\tColor\tSize Code\tSize\tMetric Size\tPaper Weight\tPaper Texture\tPricing\tQuantity\tEach Weight\tStyle Description\tColor Family Description\tAvailability\tRecycled Content\tCompare to Brand\tWindow Size\tWindow Position\tSealing Method\tRecycled\tLaser\tInkjet\tCarton Quantity\tRecycled Percent\tTagline\tShape\tLabels Per Sheet\tTop Margin\tBottom Margin\tLeft Margin\tRight Margin\tCorner Radius\tVertical Spacing\tHorizontal Spacing\tBackslits\tRush Production\tImage Url\tCreated Date\tURL\tPrintable\tReinforced Edge\tDimension Closed\tDimension Open\tIndividual Availability\tImprint Method\tPocket Style\tVendor\tVendor Sku\tCard Slits\tNumber Of Pockets\tNumber Of Panels\tPocket Spec\tImage Size";

#my $header2 = "Website\tCoating\tPrintable\tReinforced Edge\tDimension Closed\tDimension Open\tIndividual Availability\tImprint Method\tPocket Style\tVendor\tVendor Sku\tCard Slits\tNumber Of Pockets\tPocket Spec\tImage Size";

open (OD2, '>/tmp/product_export2.tsv');
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

	my %parentChildFeatures = ();
	my $currentParentFeatureId = "";
	my $currentChildFeatureName = "";
	$success &&= $parentChildProductFeaturesAssoc_handle->execute($$product{"product_id"});
	
	while (my $featureRow = $parentChildProductFeaturesAssoc_handle->fetchrow_hashref()) {
		if ($currentParentFeatureId ne $$featureRow{"parentFeatureID"}) {
			$parentChildFeatures{lc $$featureRow{"parentFeatureName"}} .= ($currentParentFeatureId ne "" ? "|" : "") . $$featureRow{"parentFeatureDescription"};
		}

		$parentChildFeatures{lc $$featureRow{"childFeatureName"}} .= ($currentChildFeatureName ne "" && defined $parentChildFeatures{lc $$featureRow{"childFeatureName"}} ? ($currentParentFeatureId eq $$featureRow{"parentFeatureID"} && $currentChildFeatureName eq $$featureRow{"childFeatureName"} ? ";" : "|") : "") . $$featureRow{"childFeatureDescription"};

        $currentParentFeatureId = $$featureRow{"parentFeatureID"};
        $currentChildFeatureName = $$featureRow{"childFeatureName"};
	}

    my %vendorData = ();
	$success &&= $vendorProductData_handle->execute($$product{"product_id"});

    while (my $vendorRow = $vendorProductData_handle->fetchrow_hashref()) {
        $vendorData{"vendorPartyID"} .= (defined $vendorData{"vendorPartyID"} ? "|" : "") . $$vendorRow{"vendorPartyID"};
        $vendorData{"vendorProductID"} .= (defined $vendorData{"vendorProductID"} ? "|" : "") . $$vendorRow{"vendorProductID"};
    }

	$success &&= $category_handle->execute($$product{"primary_product_category_id"});
	my $category = $category_handle->fetchrow_hashref();

	my $longDesc = $$product{"long_description"} || "";
	$longDesc =~ s/\R//g;

	my $colorDesc = $$product{"color_description"} || "";
	$colorDesc =~ s/\R//g;

	my $website = $$product{"website"} || "";
	$website =~ s/, $//;

	my $imageSize = "36";
	if (defined $features{"image_size"}) {
		$imageSize = $features{"image_size"};
	}

	my $priceStr = "";
	my $baseQuantityPrice = "";
	$success &&= $price_handle->execute($$product{"product_id"});

	while(my $price = $price_handle->fetchrow_hashref()) {
		if($plainOnly && $$price{"colors"} > 0) {
			next;
		} elsif($printOnly && $$price{"colors"} == 0) {
			next;
		}

		if ($baseQuantityPrice eq "") {
			$baseQuantityPrice = $$price{"price"};
		}

		$priceStr = $priceStr . $$price{"quantity"} . "," . $$price{"colors"} . "," . $$price{"price"} . ";";
	}

	print OD2 "$website\t";
	print OD2 ($$product{"product_id"} || "") . "\t";
	print OD2 ($$product{"parent_product_id"} || "") . "\t";
	print OD2 ($$product{"product_type_id"} || "") . "\t";
	print OD2 ($$product{"sales_discontinuation_date"} || "") . "\t";
	print OD2 ($$category{"primary_parent_category_id"} || "") . "\t";
	print OD2 ($$product{"primary_product_category_id"} || "") . "\t";
	print OD2 ($$product{"internal_name"} || "") . "\t";
	print OD2 ($features{"netsuite_category"}->{"description"} || "") . "\t";
	print OD2 ($features{"color"}->{"description"} || "") . "\t";
	print OD2 ($features{"coating"}->{"description"} || "") . "\t";
	print OD2 ($features{"collection"}->{"description"} || "") . "\t";
	print OD2 ($features{"color_group"}->{"description"} || "") . "\t";
	print OD2 ($features{"size_code"}->{"description"} || "") . "\t";
	print OD2 ($features{"size"}->{"description"} || "") . "\t";
	print OD2 ($features{"metric_size"}->{"description"} || "") . "\t";
	print OD2 ($features{"paper_weight"}->{"description"} || "") . "\t";
	print OD2 ($features{"paper_texture"}->{"description"} || "") . "\t";
    print OD2 $priceStr . "\t";
	print OD2 $baseQuantityPrice . "\t";
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
	print OD2 sprintf("https://www.envelopes.com/images/products/detail/%s%s", lc($$product{"product_id"}), ".jpg") . "\t";
	print OD2 ($$product{"created_stamp"} || "") . "\t";
	print OD2 ("http://www.envelopes.com/envelopes/control/product/~category_id=" . $$product{"primary_product_category_id"} . "/~product_id=" . $$product{"product_id"}) . "\t";
	print OD2 ($$product{"is_printable"} || "") . "\t";
	print OD2 ($features{"reinforced_edge"}->{"description"} || "") . "\t";
	print OD2 ($parentChildFeatures{"dimension_closed"} || "") . "\t";
	print OD2 ($parentChildFeatures{"dimension_open"} || "") . "\t";
	print OD2 ($parentChildFeatures{"availability"} || "") . "\t";
	print OD2 ($features{"imprint_method"}->{"description"} || "") . "\t";
	print OD2 ($parentChildFeatures{"pocket_style"} || "") . "\t";
	print OD2 ($vendorData{"vendorPartyID"} || "") . "\t";
	print OD2 ($vendorData{"vendorProductID"} || "") . "\t";
	print OD2 ($parentChildFeatures{"card_slits"} || "") . "\t";
	print OD2 ($parentChildFeatures{"number_of_pockets"} || "") . "\t";
	print OD2 ($features{"number_of_panels"}->{"description"} || "") . "\t";
	print OD2 ($parentChildFeatures{"pocket_spec"} || "") . "\t";
	print OD2 $imageSize . "\n";
}

close (OD2);

$features_handle->finish();
$price_handle->finish();
$products_handle->finish();
$category_handle->finish();
$dbh->disconnect();