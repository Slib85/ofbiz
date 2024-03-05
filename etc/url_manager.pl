#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use DBI;
use Ofbiz;
use Data::Dumper;
use Digest::MD5 qw(md5 md5_hex md5_base64);

my $ofbiz = Ofbiz->new(conf => "/usr/local/ofbiz/framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;

# retrieve virtual/variants products
my $all_products_handle = $dbh->prepare(qq{
	SELECT      p.product_id as product_id,
	            case when (p.sales_discontinuation_date is null OR p.sales_discontinuation_date > NOW()) then 'Y' ELSE 'N' END as active,
	            p.is_variant as is_variant,
	            rr.rule_id as rule_id,
	            rr.web_site_id as web_site_id,
                pws.ae as ae,
                pws.envelopes as envelopes,
                pws.folders as folders,
                pws.bags as bags
	FROM
        product p
            INNER JOIN
        rewrite_rule rr on p.product_id = rr.product_id
            LEFT JOIN
    	product_web_site pws on p.product_id = pws.product_id
	WHERE       p.is_variant = 'Y'
		AND     p.primary_product_category_id is not null
});

# retrieve virtual/variants products
my $products_handle = $dbh->prepare(qq{
	SELECT      p.product_id as product_id,
	            p.is_virtual as is_virtual,
	            p.is_variant as is_variant,
                pws.ae as ae,
                pws.envelopes as envelopes,
                pws.folders as folders,
                pws.bags as bags
	FROM
        product p
            LEFT JOIN
    	product_web_site pws on p.product_id = pws.product_id
	WHERE       (p.is_variant = 'Y')
		AND     p.primary_product_category_id is not null
		AND     (p.sales_discontinuation_date is null OR p.sales_discontinuation_date > NOW())
});

my $product_handle = $dbh->prepare(qq{
	SELECT      product_id,
	            primary_product_category_id,
	            product_type_id,
	            internal_name
	FROM        product
	WHERE       product_id = ?
});

my $design_handle = $dbh->prepare(qq{
	SELECT      scene7_template_id,
	            template_description,
	            product_desc
	FROM        scene7_template
	WHERE       fxg_type = 'DOM'
		AND		thru_date is null
});

my $product_category = $dbh->prepare(qq{
	SELECT      category_name,
	            primary_parent_category_id,
	            product_category_id,
	            description
	FROM        product_category
	WHERE       product_category_id = ?
});

my $color_warehouse = $dbh->prepare(qq{
	SELECT      variant_product_id,
	            color_description
	FROM        color_warehouse
	WHERE       variant_product_id = ? LIMIT 1
});

# retrieve product features of type
my $features_type_handle = $dbh->prepare(qq{
	SELECT
	    pfa.product_feature_id as product_feature_id,
	    pf.product_feature_type_id as product_feature_type_id,
	    pf.description as description,
	    pf.default_sequence_num as default_sequence_num,
        pf.id_code as id_code
	FROM
	    product_feature_appl pfa
	        INNER JOIN
	    product_feature pf ON pfa.product_feature_id = pf.product_feature_id
	WHERE
	    pfa.product_id = ?
	        AND
	    pf.product_feature_type_id = ?
	ORDER BY default_sequence_num asc
});

my $color_shop = $dbh->prepare(qq{
	SELECT
	    pcr.product_category_id AS product_category_id,
	    pc.category_name AS category_name
	FROM
	    product_category_rollup AS pcr
	        INNER JOIN
	    product_category AS pc ON pcr.product_category_id = pc.product_category_id
	WHERE
	    pcr.parent_product_category_id = 'COLORS'
});

my $color_shop_inner = $dbh->prepare(qq{
	SELECT
	    product_category_id, primary_parent_category_id, description
	FROM
	    product_category pc
	WHERE
	    pc.primary_parent_category_id IN (SELECT
	            product_category_id
	        FROM
	            product_category
	        WHERE
	            primary_parent_category_id = 'COLORS')
	AND pc.product_category_id not in ('8614','8616','8613','8608','8126','8603','8605','8536','8615','8612','8611')
});

my $collection_shop = $dbh->prepare(qq{
	SELECT
	    pcr.product_category_id AS product_category_id,
	    pc.category_name AS category_name
	FROM
	    product_category_rollup AS pcr
	        INNER JOIN
	    product_category AS pc ON pcr.product_category_id = pc.product_category_id
	WHERE
	    pcr.parent_product_category_id = 'COLLECTIONS'
});

my $collection_shop_inner = $dbh->prepare(qq{
	SELECT
	    product_category_id, primary_parent_category_id, description
	FROM
	    product_category pc
	WHERE
	    pc.primary_parent_category_id IN (SELECT
	            product_category_id
	        FROM
	            product_category
	        WHERE
	            primary_parent_category_id = 'COLLECTIONS')
	AND pc.product_category_id not in ('8610','8575','8580','8581','8586','8587','8588','8589','8592','8595','8596','8597','8598','8599','8600','8188','8602','8604','8229','8528','8529','8530','8531')
});

# retrieve product features
my $features_handle = $dbh->prepare(qq{
	SELECT 		pfa.product_feature_id,
				pf.product_feature_type_id,
				pf.description,
				pf.default_sequence_num
	FROM       	product_feature_appl pfa
	INNER JOIN 	product_feature pf
	ON         	pfa.product_feature_id = pf.product_feature_id
	WHERE      	pfa.product_id = ?
});

my $insertURL = $dbh->prepare(qq{
	insert into rewrite_rule (rule_id, web_site_id, rewrite_type_id, from_url, to_url, product_id, scene7_template_id, response_code, time_stamp, manual) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) on duplicate key update from_url = ?, to_url = ?, response_code = ?, time_stamp = ?
});

my $getDuplicateRewrites = $dbh->prepare(qq{
	SELECT
		rule_id as rule_id,
		web_site_id AS web_site_id,
		rewrite_type_id AS rewrite_type_id,
		from_url AS from_url,
		to_url AS to_url,
		product_id AS product_id,
		response_code AS response_code,
		time_stamp AS time_stamp
	FROM
		rewrite_rule
	WHERE
		(product_id, web_site_id) IN (SELECT
				product_id, web_site_id
			FROM
				rewrite_rule
			GROUP BY product_id, web_site_id
			HAVING COUNT(product_id) > 1)
            AND time_stamp = ?
});

my $updateDuplicateRewrites = $dbh->prepare(qq{
	update rewrite_rule set rewrite_type_id = ?, to_url = ?, response_code = ?, time_stamp = ? where product_id = ? and time_stamp != ? and web_site_id = ?
});

my $getDate = $dbh->prepare(qq{
	select now()
});

my $getRules = $dbh->prepare(qq{
    SELECT
    		rule_id as rule_id,
    		web_site_id AS web_site_id,
    		rewrite_type_id AS rewrite_type_id,
    		from_url AS from_url,
    		to_url AS to_url,
    		product_id AS product_id,
    		scene7_template_id AS scene7_template_id,
    		response_code AS response_code,
    		time_stamp AS time_stamp
    	FROM
    		rewrite_rule
    	WHERE (active is null or active = 'Y')
    ORDER BY from_url
});

my $discontinueRules = $dbh->prepare(qq{
	update rewrite_rule set active = ? where rule_id = ? and web_site_id = ?
});

die "Couldn't prepare queries; aborting" unless defined $products_handle;

# start transaction
my $success = 1;

##########################
##########################
########## DATE ##########
#VERY IMPORTANT VARIABLE, USED TO UPDATE OLD RECORDS AFTER NEW ONE IS PROCESSED
$success &&= $getDate->execute();
my $dateTime = $getDate->fetchrow_array();
#########################
#########################
#########################

staticPages();

$success &&= $products_handle->execute();
# iterate all variant products and create table rows
while(my $products = $products_handle->fetchrow_hashref()) {
	$success &&= $product_handle->execute($$products{"product_id"});
	my $product = $product_handle->fetchrow_hashref();
	my %features = ();

	$success &&= $features_handle->execute($$products{"product_id"});
	while(my $feature = $features_handle->fetchrow_hashref()) {
		$features{lc $$feature{"product_feature_type_id"}} = $feature;
	}

	$success &&= $product_category->execute($$product{"primary_product_category_id"});
	my $productCategory = $product_category->fetchrow_hashref();

	my $productCategoryName = $$productCategory{"category_name"} || "null";
	$productCategoryName = cleanSeoUrl($productCategoryName);

	my $primaryParentCategoryId = $$productCategory{"primary_parent_category_id"} || "null";
	my $primaryParentCategoryIdAsName = $primaryParentCategoryId;
	$primaryParentCategoryIdAsName = cleanSeoUrl($primaryParentCategoryIdAsName);

	my $internalName = $$product{"internal_name"};
	$internalName = cleanSeoUrl($internalName);

	my $productType = $$product{"product_type_id"} || "";
	$productType = cleanSeoUrl($productType);

	if(index($$products{"product_id"}, "-W2") != -1 || index($$products{"product_id"}, "W2_") != -1 || $$product{"primary_product_category_id"} eq 'LAYER_CARDS') {
		my $size = $features{"size"}->{"description"} || "";
		$size = cleanSeoUrl($size);
		if($size ne "") {
			$internalName = $internalName . "-" . $size;
		}
	}

	if($productType ne "" && $$product{"primary_product_category_id"} ne 'PETALS' && $$product{"primary_product_category_id"} ne 'POCKETS') {
		if(lc($productType) eq "envelope" && index($internalName, "envelopes") == -1) {
			$internalName = $internalName . "-" . "envelopes";
		}
	}

	$success &&= $color_warehouse->execute($$products{"product_id"});
	my $warehouse = $color_warehouse->fetchrow_hashref();

	if($$products{"is_variant"} eq "Y") {
		if(defined $warehouse) {
			my $colorName = $$warehouse{"color_description"} || "";
			$colorName = cleanSeoUrl($colorName);
			if($colorName ne "" && index($internalName, $colorName) == -1) {
				$internalName = $internalName . "-" . $colorName;
			}
		} else {
			my $colorName = $features{"color"}->{"description"} || "";
			$colorName = cleanSeoUrl($colorName);
			if($colorName ne "" && index($internalName, $colorName) == -1) {
				$internalName = $internalName . "-" . $colorName;
			}
		}
	}

    my $nonFriendly = "/envelopes/control/product/~category_id=" . $$product{"primary_product_category_id"} . "/~product_id=" . $$products{"product_id"};
	my $nonFriendlyAE = "/ae/control/product/~category_id=" . $$product{"primary_product_category_id"} . "/~product_id=" . $$products{"product_id"};
	if($productCategoryName ne "" && $productCategoryName eq "pockets") {
		my $friendly = "/" . "pocketsAndLayers" . "/" . $internalName;
		insertURLRow($friendly, "envelopes", "REWRITE", $nonFriendly, $$products{"product_id"}, undef, undef, $dateTime, undef);
		#insertURLRow($friendly, "ae", "REWRITE", $nonFriendlyAE, $$products{"product_id"}, undef, undef, $dateTime, undef);
	} elsif($productCategoryName ne "" && $productCategoryName eq "petals") {
		my $friendly = "/" . "petalsAndLayers" . "/" . $internalName;
		insertURLRow($friendly, "envelopes", "REWRITE", $nonFriendly, $$products{"product_id"}, undef, undef, $dateTime, undef);
		#insertURLRow($friendly, "ae", "REWRITE", $nonFriendlyAE, $$products{"product_id"}, undef, undef, $dateTime, undef);
	} elsif($primaryParentCategoryIdAsName ne "" && $primaryParentCategoryIdAsName eq "paperandmore") {
		my $friendly = "/" . $productCategoryName . "/" . $internalName;
		insertURLRow($friendly, "envelopes", "REWRITE", $nonFriendly, $$products{"product_id"}, undef, undef, $dateTime, undef);
		#insertURLRow($friendly, "ae", "REWRITE", $nonFriendlyAE, $$products{"product_id"}, undef, undef, $dateTime, undef);
	} else {
		my $friendly = "/" . $primaryParentCategoryIdAsName . "/" . $productCategoryName . "/" . $internalName;
		insertURLRow($friendly, "envelopes", "REWRITE", $nonFriendly, $$products{"product_id"}, undef, undef, $dateTime, undef);
		#insertURLRow($friendly, "ae", "REWRITE", $nonFriendlyAE, $$products{"product_id"}, undef, undef, $dateTime, undef);
	}

    #separate rule for folders
    if(defined $$products{"folders"} && $$products{"folders"} eq "Y") {
        #print "here";
        my @pocketTypes = get_features_of_type($$product{"product_id"}, "POCKET_STYLE");
        my @printMethods = get_features_of_type($$product{"product_id"}, "IMPRINT_METHOD");

        my $nonFriendlyFOLD = "/folders/control/product/~category_id=" . $$product{"primary_product_category_id"} . "/~product_id=" . $$products{"product_id"};
        my $friendly = "/" . $productCategoryName . "/" . $internalName;
        insertURLRow($friendly, "folders", "REWRITE", $nonFriendlyFOLD, $$products{"product_id"}, undef, undef, $dateTime, undef);

        if(@printMethods) {
            if(@pocketTypes) {
                foreach my $pocket (@pocketTypes) {
                    my $seoPocket = cleanSeoUrl($$pocket{"id_code"});
                    foreach my $printMethod (@printMethods) {
                        my $seoPrintMethod = $$printMethod{"id_code"};
                        if($seoPrintMethod eq "FOUR_COLOR") {
                            $seoPrintMethod = "four-color-printing";
                        } elsif($seoPrintMethod eq "SPOT") {
                            $seoPrintMethod = "spot-color-printing";
                        } elsif($seoPrintMethod eq "FOIL") {
                            $seoPrintMethod = "foil-stamping";
                        } elsif($seoPrintMethod eq "EMBOSS") {
                            $seoPrintMethod = "embossed";
                        }

                        my $nonFriendlyFOLD = "/folders/control/product/~category_id=" . $$product{"primary_product_category_id"} . "/~product_id=" . $$products{"product_id"} . "/~pocket_type=" . $$pocket{"id_code"} . "/~print_method=" . $$printMethod{"id_code"};
                        my $friendly = "/" . $productCategoryName . "/" . $internalName . "/" . $seoPocket . "/" . $seoPrintMethod;
                        insertURLRow($friendly, "folders", "REWRITE", $nonFriendlyFOLD, $$products{"product_id"}, undef, undef, $dateTime, undef);
                    }
                }
            } else {
                foreach my $printMethod (@printMethods) {
                    my $seoPrintMethod = $$printMethod{"id_code"};
                    if($seoPrintMethod eq "FOUR_COLOR") {
                        $seoPrintMethod = "four-color-printing";
                    } elsif($seoPrintMethod eq "SPOT") {
                        $seoPrintMethod = "spot-color-printing";
                    } elsif($seoPrintMethod eq "FOIL") {
                        $seoPrintMethod = "foil-stamping";
                    } elsif($seoPrintMethod eq "EMBOSS") {
                        $seoPrintMethod = "embossed";
                    }

                    my $nonFriendlyFOLD = "/folders/control/product/~category_id=" . $$product{"primary_product_category_id"} . "/~product_id=" . $$products{"product_id"} . "/~print_method=" . $$printMethod{"id_code"};
                    my $friendly = "/" . $productCategoryName . "/" . $internalName . "/" . $seoPrintMethod;
                    insertURLRow($friendly, "folders", "REWRITE", $nonFriendlyFOLD, $$products{"product_id"}, undef, undef, $dateTime, undef);
                }
            }
        }
    }
}

sub staticPages {
    # Envelopes
	insertURLRow("/", "envelopes", "REWRITE", "/envelopes/control/main", undef, undef, undef, $dateTime, undef);
	insertURLRow("/business-envelopes", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=BUSINESS", undef, undef, undef, $dateTime, undef);
    insertURLRow("/business/regular-envelopes", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=REGULAR", undef, undef, undef, $dateTime, undef);
    insertURLRow("/business/window-envelopes", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=WINDOW", undef, undef, undef, $dateTime, undef);
    insertURLRow("/business/open-end-envelopes", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=OPEN_END", undef, undef, undef, $dateTime, undef);
    insertURLRow("/business/booklet-envelopes", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=BOOKLET", undef, undef, undef, $dateTime, undef);
    insertURLRow("/business/clasp-envelopes", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=CLASP", undef, undef, undef, $dateTime, undef);
    insertURLRow("/business/remittance-envelopes", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=REMITTANCE", undef, undef, undef, $dateTime, undef);
    insertURLRow("/business/express-envelopes", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=EXPRESS", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation-envelopes", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=INVITATION", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitations/square-flap-envelopes", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=SQUARE_FLAP", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitations/open-end-envelopes", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=INV_OPEN_END", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitations/pointed-flap-envelopes", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=POINTED_FLAP", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitations/inner-outer-envelopes", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=INNER_OUTER", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitations/lined-envelopes", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=LINED?af=st:lined%20col:linedenvelopes", undef, undef, undef, $dateTime, undef);
    insertURLRow("/shipping-envelopes", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=SHIPPING", undef, undef, undef, $dateTime, undef);
    insertURLRow("/shipping/paperboard-mailers-envelopes", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=PAPERBOARD_MAILERS", undef, undef, undef, $dateTime, undef);
    insertURLRow("/shipping/bubble-lined-envelopes", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=BUBBLE_LINED", undef, undef, undef, $dateTime, undef);
    insertURLRow("/shipping/plastic-mailers-envelopes", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=PLASTIC_MAILERS", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitations/mini-envelopes", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=MINI_ENVELOPES", undef, undef, undef, $dateTime, undef);
    insertURLRow("/shipping/padded-mailers", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=PADDED_MAILERS", undef, undef, undef, $dateTime, undef);
    insertURLRow("/shipping/rigi-bag-mailers", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=RIGI_BAG_MAILERS", undef, undef, undef, $dateTime, undef);
    insertURLRow("/shipping/tuffguard-mailers", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=TUFFGUARD_MAILERS", undef, undef, undef, $dateTime, undef);
    insertURLRow("/shipping/jiffylite-bubble", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=JIFFYLITE_BUBBLE", undef, undef, undef, $dateTime, undef);
    insertURLRow("/shipping/boxes", "envelopes", "REWRITE", "/envelopes/control/category?af=use:shipping%20st:boxes", undef, undef, undef, $dateTime, undef);
    insertURLRow("/shipping/tyvek", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=TYVEK", undef, undef, undef, $dateTime, undef);
    insertURLRow("/paper", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=PAPER", undef, undef, undef, $dateTime, undef);
    insertURLRow("/cardstock", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=CARDSTOCK", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards", "envelopes", "REWRITE", "/envelopes/control/category?af=st:notecards", undef, undef, undef, $dateTime, undef);
    insertURLRow("/bordercards", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=BORDERCARDS", undef, undef, undef, $dateTime, undef);
    insertURLRow("/folders", "envelopes", "REWRITE", "/envelopes/control/category?af=st:folders", undef, undef, undef, $dateTime, undef);
    insertURLRow("/popular", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=MOST_POPULAR", undef, undef, undef, $dateTime, undef);
    insertURLRow("/new-arrivals", "envelopes", "REWRITE", "/envelopes/control/new-arrivals", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitations/contour-flap-envelopes", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=CONTOUR_FLAP", undef, undef, undef, $dateTime, undef);
    insertURLRow("/petalsAndLayers", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=PETALS", undef, undef, undef, $dateTime, undef);
    insertURLRow("/pocketsAndLayers", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=POCKETS", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitations/printeriors", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=PRINT_ERIORS", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitations/colorflaps", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=COLOR_FLAPS", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitations/colorseams", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=COLOR_SEAMS", undef, undef, undef, $dateTime, undef);
    insertURLRow("/shipping/expansion-envelopes", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=EXPANSION", undef, undef, undef, $dateTime, undef);
    insertURLRow("/business/document-envelopes", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=DOCUMENT", undef, undef, undef, $dateTime, undef);
    insertURLRow("/labels", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=LABELS", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitations/tags", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=TAGS", undef, undef, undef, $dateTime, undef);
    insertURLRow("/folded-tags/a7-folded-tags-envelopes", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=FOLDED_TAGS/~product_id=A7_FOLDED", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitations/zfold-invitations", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=Z-FOLD", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitations/gatefold-invitations", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=GATEFOLD", undef, undef, undef, $dateTime, undef);
    insertURLRow("/made-to-order", "envelopes", "REWRITE", "/envelopes/control/customEnvelopes", undef, undef, undef, $dateTime, undef);
    insertURLRow("/Large-Quantity-Orders", "envelopes", "REWRITE", "/envelopes/control/largeQuantityOrders", undef, undef, undef, $dateTime, undef);
    insertURLRow("/envelope-sizes", "envelopes", "REWRITE", "/envelopes/control/envelope-sizes", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors", "envelopes", "REWRITE", "/envelopes/control/shopByColor", undef, undef, undef, $dateTime, undef);
    #Begin Colors
    #New Rewrites.
    insertURLRow("/colors/white-envelopes", "envelopes", "REWRITE", "/envelopes/control/shopByColor/~category_id=WHITE", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/natural-envelopes", "envelopes", "REWRITE", "/envelopes/control/shopByColor/~category_id=NATURAL", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/yellow-envelopes", "envelopes", "REWRITE", "/envelopes/control/shopByColor/~category_id=YELLOW", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/orange-envelopes", "envelopes", "REWRITE", "/envelopes/control/shopByColor/~category_id=ORANGE", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/red-envelopes", "envelopes", "REWRITE", "/envelopes/control/shopByColor/~category_id=RED", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/pink-envelopes", "envelopes", "REWRITE", "/envelopes/control/shopByColor/~category_id=PINK", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/purple-envelopes", "envelopes", "REWRITE", "/envelopes/control/shopByColor/~category_id=PURPLE", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/brown-envelopes", "envelopes", "REWRITE", "/envelopes/control/shopByColor/~category_id=BROWN", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/blue-envelopes", "envelopes", "REWRITE", "/envelopes/control/shopByColor/~category_id=BLUE", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/green-envelopes", "envelopes", "REWRITE", "/envelopes/control/shopByColor/~category_id=GREEN", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/teal-envelopes", "envelopes", "REWRITE", "/envelopes/control/shopByColor/~category_id=TEAL", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/clear-envelopes", "envelopes", "REWRITE", "/envelopes/control/shopByColor/~category_id=CLEAR", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/gold-envelopes", "envelopes", "REWRITE", "/envelopes/control/shopByColor/~category_id=GOLD", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/silver-envelopes", "envelopes", "REWRITE", "/envelopes/control/shopByColor/~category_id=SILVER", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/black-envelopes", "envelopes", "REWRITE", "/envelopes/control/shopByColor/~category_id=BLACK", undef, undef, undef, $dateTime, undef);
    # Redirects for from the old to the new version.  This is needed to keep any old calls to this page active still.
    insertURLRow("/colors/white-colors", "envelopes", "REDIRECT", "/colors/white-envelopes", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/natural-colors", "envelopes", "REDIRECT", "/colors/natural-envelopes", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/yellow-colors", "envelopes", "REDIRECT", "/colors/yellow-envelopes", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/orange-colors", "envelopes", "REDIRECT", "/colors/orange-envelopes", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/red-colors", "envelopes", "REDIRECT", "/colors/red-envelopes", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/pink-colors", "envelopes", "REDIRECT", "/colors/pink-envelopes", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/purple-colors", "envelopes", "REDIRECT", "/colors/purple-envelopes", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/brown-colors", "envelopes", "REDIRECT", "/colors/brown-envelopes", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/blue-colors", "envelopes", "REDIRECT", "/colors/blue-envelopes", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/green-colors", "envelopes", "REDIRECT", "/colors/green-envelopes", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/teal-colors", "envelopes", "REDIRECT", "/colors/teal-envelopes", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/clear-colors", "envelopes", "REDIRECT", "/colors/clear-envelopes", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/gold-colors", "envelopes", "REDIRECT", "/colors/gold-envelopes", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/silver-colors", "envelopes", "REDIRECT", "/colors/silver-envelopes", undef, undef, undef, $dateTime, undef);
    insertURLRow("/colors/black-colors", "envelopes", "REDIRECT", "/colors/black-envelopes", undef, undef, undef, $dateTime, undef);
	#End Colors
    insertURLRow("/sizes", "envelopes", "REWRITE", "/envelopes/control/shopBySize", undef, undef, undef, $dateTime, undef);
    insertURLRow("/uses", "envelopes", "REWRITE", "/envelopes/control/shopByUse", undef, undef, undef, $dateTime, undef);
    insertURLRow("/cotton", "envelopes", "REWRITE", "/envelopes/control/search?w=cotton", undef, undef, undef, $dateTime, undef);
    insertURLRow("/gogreen", "envelopes", "REWRITE", "/envelopes/control/search/~w=recycled", undef, undef, undef, $dateTime, undef);
    insertURLRow("/gogreen/shop", "envelopes", "REWRITE", "/envelopes/control/search/~w=recycled", undef, undef, undef, $dateTime, undef);
    insertURLRow("/gogreen/recycled-envelopes", "envelopes", "REWRITE", "/envelopes/control/search/~w=recycled", undef, undef, undef, $dateTime, undef);
    insertURLRow("/direct-mail", "envelopes", "REWRITE", "/envelopes/control/search?w=direct%20mail&af=prodtype%3adesigns", undef, undef, undef, $dateTime, undef);
    insertURLRow("/shops/holiday", "envelopes", "REWRITE", "/envelopes/control/holidayShop", undef, undef, undef, $dateTime, undef);
    insertURLRow("/holiday", "envelopes", "REWRITE", "/envelopes/control/holidayShop", undef, undef, undef, $dateTime, undef);
    insertURLRow("/tax-envelopes", "envelopes", "REWRITE", "/envelopes/control/taxEnvelopesShop", undef, undef, undef, $dateTime, undef);
    insertURLRow("/print-services", "envelopes", "REWRITE", "/envelopes/control/designoptions", undef, undef, undef, $dateTime, undef);
    insertURLRow("/account", "envelopes", "REWRITE", "/envelopes/control/account", undef, undef, undef, $dateTime, undef);
    insertURLRow("/account/approvals", "envelopes", "REWRITE", "/envelopes/control/proofApproval", undef, undef, undef, $dateTime, undef);
    insertURLRow("/account/login", "envelopes", "REWRITE", "/envelopes/control/login", undef, undef, undef, $dateTime, undef);
    insertURLRow("/privacy", "envelopes", "REWRITE", "/envelopes/control/privacy", undef, undef, undef, $dateTime, undef);
    insertURLRow("/help/safe-shopping-policy", "envelopes", "REWRITE", "/envelopes/control/safeshopping", undef, undef, undef, $dateTime, undef);
    insertURLRow("/terms", "envelopes", "REWRITE", "/envelopes/control/terms", undef, undef, undef, $dateTime, undef);
    insertURLRow("/help/design-options", "envelopes", "REWRITE", "/envelopes/control/designoptions", undef, undef, undef, $dateTime, undef);
    insertURLRow("/service/print-proofs", "envelopes", "REWRITE", "/envelopes/control/reviewingproof", undef, undef, undef, $dateTime, undef);
    insertURLRow("/help/reorders", "envelopes", "REWRITE", "/envelopes/control/reordering", undef, undef, undef, $dateTime, undef);
    insertURLRow("/cross-sell", "envelopes", "REWRITE", "/envelopes/control/cross-sell", undef, undef, undef, $dateTime, undef);
    insertURLRow("/stamps", "envelopes", "REWRITE", "/envelopes/control/category?af=si:1625%20si:225x8125%20st:stamps%20prodtype:products&sort=SIZE_SMALL", undef, undef, undef, $dateTime, undef);
    insertURLRow("/peelandpress", "envelopes", "REWRITE", "/envelopes/control/peelAndPress", undef, undef, undef, $dateTime, undef);
    insertURLRow("/addToCart", "envelopes", "REWRITE", "/envelopes/control/addToCart", undef, undef, undef, $dateTime, undef);
    insertURLRow("/cart", "envelopes", "REWRITE", "/envelopes/control/cart", undef, undef, undef, $dateTime, undef);
    insertURLRow("/checkout", "envelopes", "REWRITE", "/envelopes/control/checkout", undef, undef, undef, $dateTime, undef);
    insertURLRow("/receipt", "envelopes", "REWRITE", "/envelopes/control/receipt", undef, undef, undef, $dateTime, undef);
    insertURLRow("/search", "envelopes", "REWRITE", "/envelopes/control/search", undef, undef, undef, $dateTime, undef);
    insertURLRow("/about", "envelopes", "REWRITE", "/envelopes/control/aboutus", undef, undef, undef, $dateTime, undef);
    insertURLRow("/about/testimonials", "envelopes", "REWRITE", "/envelopes/control/testimonials", undef, undef, undef, $dateTime, undef);
    insertURLRow("/coupons", "envelopes", "REWRITE", "/envelopes/control/coupons", undef, undef, undef, $dateTime, undef);
    insertURLRow("/print-services#overview", "envelopes", "REWRITE", "/envelopes/control/print-services", undef, undef, undef, $dateTime, undef);
    insertURLRow("/print-services#options", "envelopes", "REWRITE", "/envelopes/control/print-services", undef, undef, undef, $dateTime, undef);
    insertURLRow("/print-services#production", "envelopes", "REWRITE", "/envelopes/control/print-services", undef, undef, undef, $dateTime, undef);
    insertURLRow("/print-services#start", "envelopes", "REWRITE", "/envelopes/control/print-services", undef, undef, undef, $dateTime, undef);
    insertURLRow("/recipient-addressing", "envelopes", "REWRITE", "/envelopes/control/addressing", undef, undef, undef, $dateTime, undef);
    insertURLRow("/social", "envelopes", "REWRITE", "/envelopes/control/social", undef, undef, undef, $dateTime, undef);
    insertURLRow("/fsc-certified", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=FSC_CERTIFIED", undef, undef, undef, $dateTime, undef);
    insertURLRow("/sfi-certified", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=SFI_CERTIFIED", undef, undef, undef, $dateTime, undef);
    insertURLRow("/collections", "envelopes", "REWRITE", "/envelopes/control/shopByCollection", undef, undef, undef, $dateTime, undef);
    insertURLRow("/collections#black", "envelopes", "REWRITE", "/envelopes/control/shopByCollection#black", undef, undef, undef, $dateTime, undef);
    insertURLRow("/collections#brights", "envelopes", "REWRITE", "/envelopes/control/shopByCollection#brights", undef, undef, undef, $dateTime, undef);
    insertURLRow("/collections#clear", "envelopes", "REWRITE", "/envelopes/control/shopByCollection#clear", undef, undef, undef, $dateTime, undef);
    insertURLRow("/collections#color-linings", "envelopes", "REWRITE", "/envelopes/control/shopByCollection#color_linings", undef, undef, undef, $dateTime, undef);
    insertURLRow("/collections#denim", "envelopes", "REWRITE", "/envelopes/control/shopByCollection#denim", undef, undef, undef, $dateTime, undef);
    insertURLRow("/collections#earthtones", "envelopes", "REWRITE", "/envelopes/control/shopByCollection#earthtones", undef, undef, undef, $dateTime, undef);
    insertURLRow("/collections#exclusive", "envelopes", "REWRITE", "/envelopes/control/shopByCollection#exclusive", undef, undef, undef, $dateTime, undef);
    insertURLRow("/collections#fashion", "envelopes", "REWRITE", "/envelopes/control/shopByCollection#fashion", undef, undef, undef, $dateTime, undef);
    insertURLRow("/collections#express", "envelopes", "REWRITE", "/envelopes/control/shopByCollection#express", undef, undef, undef, $dateTime, undef);
    insertURLRow("/collections#grocery-bag", "envelopes", "REWRITE", "/envelopes/control/shopByCollection#grocery_bag", undef, undef, undef, $dateTime, undef);
    insertURLRow("/collections#ivory", "envelopes", "REWRITE", "/envelopes/control/shopByCollection#ivory", undef, undef, undef, $dateTime, undef);
    insertURLRow("/collections#kraft", "envelopes", "REWRITE", "/envelopes/control/shopByCollection#kraft", undef, undef, undef, $dateTime, undef);
    insertURLRow("/collections#linen", "envelopes", "REWRITE", "/envelopes/control/shopByCollection#linen", undef, undef, undef, $dateTime, undef);
    insertURLRow("/collections#metallics", "envelopes", "REWRITE", "/envelopes/control/shopByCollection#metallics", undef, undef, undef, $dateTime, undef);
    insertURLRow("/collections#mirror", "envelopes", "REWRITE", "/envelopes/control/shopByCollection#mirror", undef, undef, undef, $dateTime, undef);
    insertURLRow("/collections#parchments", "envelopes", "REWRITE", "/envelopes/control/shopByCollection#parchments", undef, undef, undef, $dateTime, undef);
    insertURLRow("/collections#pastels", "envelopes", "REWRITE", "/envelopes/control/shopByCollection#pastels", undef, undef, undef, $dateTime, undef);
    insertURLRow("/collections#translucents", "envelopes", "REWRITE", "/envelopes/control/shopByCollection#translucents", undef, undef, undef, $dateTime, undef);
    insertURLRow("/collections#white", "envelopes", "REWRITE", "/envelopes/control/shopByCollection#white", undef, undef, undef, $dateTime, undef);
    insertURLRow("/recycled-envelopes", "envelopes", "REWRITE", "/envelopes/control/search?w=recycled", undef, undef, undef, $dateTime, undef);
    insertURLRow("/recycled-envelopes/#10-percent", "envelopes", "REWRITE", "/envelopes/control/recycledEnvelopes#10_percent", undef, undef, undef, $dateTime, undef);
    insertURLRow("/recycled-envelopes/#20-percent", "envelopes", "REWRITE", "/envelopes/control/recycledEnvelopes#20_percent", undef, undef, undef, $dateTime, undef);
    insertURLRow("/recycled-envelopes/#30-percent", "envelopes", "REWRITE", "/envelopes/control/recycledEnvelopes#30_percent", undef, undef, undef, $dateTime, undef);
    insertURLRow("/recycled-envelopes/#100-percent", "envelopes", "REWRITE", "/envelopes/control/recycledEnvelopes#100_percent", undef, undef, undef, $dateTime, undef);
    insertURLRow("/white-ink", "envelopes", "REWRITE", "/envelopes/control/white-ink", undef, undef, undef, $dateTime, undef);
    insertURLRow("/luxpaper", "envelopes", "REWRITE", "/envelopes/control/luxpaper", undef, undef, undef, $dateTime, undef);
    insertURLRow("/eventplanning", "envelopes", "REWRITE", "/envelopes/control/eventplanning", undef, undef, undef, $dateTime, undef);
    insertURLRow("/clearance", "envelopes", "REWRITE", "/envelopes/control/search?w=*&af=onsale:sale%20onsale:clearance", undef, undef, undef, $dateTime, undef);
    insertURLRow("/swatch-book-giveaway", "envelopes", "REWRITE", "/envelopes/control/swatchbook", undef, undef, undef, $dateTime, undef);
    insertURLRow("/year-end-swatchbook", "envelopes", "REWRITE", "/envelopes/control/year-end-swatchbook", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invoice-double-window", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=WINDOW/product/~product_id=INVDW-GB", undef, undef, undef, $dateTime, undef);
    insertURLRow("/shipping-and-mailing-supplies", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=SHIPPING?af=prodtype:products%20use:shipping%20use:shipping%20prodtype:products&sort=SIZE_LARGE", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/rolland", "envelopes", "REWRITE", "/envelopes/control/search?w=rolland&c=0", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/boxes-and-packaging", "envelopes", "REWRITE", "/envelopes/control/category?af=st:boxes%20st:pillowbox%20prodtype:products&sort=SIZE_SMALL", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/mini-envelopes", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=MINI_ENVELOPES", undef, undef, undef, $dateTime, undef);
    insertURLRow("/Petals", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=PETALS/~product_id=LUX-614PTL113", undef, undef, undef, $dateTime, undef);
    insertURLRow("/Pockets", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=POCKETS/~product_id=A7PKTGB", undef, undef, undef, $dateTime, undef);
    insertURLRow("/10envelopes", "envelopes", "REWRITE", "/envelopes/control/search?w=%2310&c=0", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/1-coin-envelopes", "envelopes", "REWRITE", "/envelopes/control/category?af=si:214x312", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/business", "envelopes", "REWRITE", "/envelopes/control/search/~af=use%3Abusinessstationery/~w=*", undef, undef, undef, $dateTime, undef);
    insertURLRow("/tradepro", "envelopes", "REWRITE", "/envelopes/control/tradePro", undef, undef, undef, $dateTime, undef);
    insertURLRow("/nonprofit", "envelopes", "REWRITE", "/envelopes/control/nonprofitdiscount", undef, undef, undef, $dateTime, undef);
    insertURLRow("/postNet", "envelopes", "REWRITE", "/envelopes/control/postNet", undef, undef, undef, $dateTime, undef);
    insertURLRow("/alliance", "envelopes", "REWRITE", "/envelopes/control/alliance", undef, undef, undef, $dateTime, undef);
    insertURLRow("/wedding-shop", "envelopes", "REWRITE", "/envelopes/control/weddingShop", undef, undef, undef, $dateTime, undef);
    insertURLRow("/textures", "envelopes", "REWRITE", "/envelopes/control/search/~w=textures", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/photo-cards", "envelopes", "REWRITE", "/envelopes/control/search/~w=photo%20cards", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/extracts", "envelopes", "REWRITE", "/envelopes/control/extracts", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/restaurants", "envelopes", "REWRITE", "/envelopes/control/restaurants", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/business/window/w-2-form-envelopes-5-5-8-x-9-24lb-white-w-wesco-security-tint", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=WINDOW/~product_id=7487-W2", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-silver-metallic", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=5080-06", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-crystal-metallic", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=5080-30", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-gold-metallic", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=5080-07", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-quartz-metallic", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=5080-08", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-ruby-red", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-18", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-garnet", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-26", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-magenta", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-10", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-candy-pink", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-14", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-vintage-plum", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-104", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-wisteria", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-106", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-teal", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-25", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-ruby-seafoam", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-113", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-baby-blue", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-13", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-boardwalk-blue", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-23", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-pool", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-102", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-navy", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-103", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-limelight", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-101", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-sunflower", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-12", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-lemonade", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-15", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-mandarin", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-11", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-tangerine", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-112", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-80lb-bright-white", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=17MFW", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-white-100-recycled", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=5080-WPC", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-bright-white-100-cotton", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=5080-SW", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-white-linen", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=5080-WLI", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-natural", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=17MFN", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-natural-100-recycled", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=5080-NPC", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-natural-linen", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=5080-NLI", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-natural-white-100-cotton", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=5080-SN", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-chocolate", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-17", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-smoke", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-22", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-black-linen", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=5080-BLI", undef, undef, undef, $dateTime, undef);
    insertURLRow("/notecards/17-mini-folded-card-envelopes-midnight-black", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-B", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelopes-silver-metallic", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=MINSDS", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelopes-silver-damask", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=LEVC-97", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelopes-red-bow", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=LEVC-99", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelopes-limelight", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=LUXLEVC-101", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelope-envelopes-grocery-bag", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=LEVC-GB", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelope-envelopes-chocolate", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=EXLEVC-17", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelope-envelopes-smoke", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=EXLEVC-22", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelopes-midnight-black", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=MINBLK", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelopes-crystal-metallic", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=MINSDC", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelopes-gold-metallic", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=MINSDG", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelopes-chinese-new-year", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=LEVC-96", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelope-envelopes-ruby-red", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=EXLEVC-18", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelope-envelopes-magenta", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=EXLEVC-10", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelopes-wisteria", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=LUXLEVC-106", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelopes-seafoam", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=LUXLEVC-113", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelope-envelopes-baby-blue", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=EXLEVC-13", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelope-envelopes-boardwalk-blue", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=EXLEVC-23", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelope-envelopes-teal", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=EXLEVC-25", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelopes-pool", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=LUXLEVC-102", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelopes-navy", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=LUXLEVC-103", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelope-envelopes-avocado", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=EXLEVC-27", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelope-envelopes-sunflower", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=EXLEVC-12", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelope-envelopes-lemonade", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=EXLEVC-15", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelope-envelopes-mandarin", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=EXLEVC-11", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelopes-tangerine", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=LUXLEVC-112", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelopes-flourish", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=LEVC-98", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitations/pointed-flap/17-mini-envelopes-natural", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=LEVC903", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelopes-quartz-metallic", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=MINSDQ", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelope-envelopes-garnet", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=EXLEVC-26", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelope-envelopes-candy-pink", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=EXLEVC-14", undef, undef, undef, $dateTime, undef);
    insertURLRow("/invitation/pointed-flap/17-mini-envelopes-vintage-plum", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=MINI_ENVELOPES/~product_id=LUXLEVC-104", undef, undef, undef, $dateTime, undef);
    insertURLRow("/flat-cards/17-mini-folded-card-white-100-recycled", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=5080-WPC", undef, undef, undef, $dateTime, undef);
    insertURLRow("/flat-cards/17-mini-folded-card-vintage-plum", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-104", undef, undef, undef, $dateTime, undef);
    insertURLRow("/flat-cards/17-mini-folded-card-ruby-red", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-18", undef, undef, undef, $dateTime, undef);
    insertURLRow("/flat-cards/17-mini-folded-card-navy", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-103", undef, undef, undef, $dateTime, undef);
    insertURLRow("/flat-cards/17-mini-folded-card-garnet", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-26", undef, undef, undef, $dateTime, undef);
    insertURLRow("/liners", "envelopes", "REWRITE", "/envelopes/control/liners", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/flat-cards/17-mini-folded-card-envelopes", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=17MFN", undef, undef, undef, $dateTime, undef);
    insertURLRow("/flat-cards/17-mini-folded-card-chocolate", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=LUX-5080-17", undef, undef, undef, $dateTime, undef);
    insertURLRow("/flat-cards/17-mini-folded-card-bright-white-100-cotton", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=5080-SW", undef, undef, undef, $dateTime, undef);
    insertURLRow("/flat-cards/swatchbook-premium-envelopes-paper", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=SWATCHBOOK", undef, undef, undef, $dateTime, undef);
    insertURLRow("/5-ways-to-grow-your-photography-business", "envelopes", "REWRITE", "/envelopes/control/photoBusiness", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/increase-direct-mail-conversions", "envelopes", "REWRITE", "/envelopes/control/whitePaper", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/direct-mail-thank-you", "envelopes", "REWRITE", "/envelopes/control/whitePaperThankYou", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/bulk-envelopes", "envelopes", "REWRITE", "/envelopes/control/bulkEnvelopes", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/accounting-envelopes", "envelopes", "REWRITE", "/envelopes/control/accountingEnvelopes", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/graphic-designers", "envelopes", "REWRITE", "/envelopes/control/graphicDesigners", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/corporate-printing", "envelopes", "REWRITE", "/envelopes/control/corporatePrinters", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/envelopes-for-photos", "envelopes", "REWRITE", "/envelopes/control/photographerWorkflow", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/boxes-packaging", "envelopes", "REWRITE", "/envelopes/control/category?af=use:shipping%20si:2x34x3%20si:2532x218x2532%20si:212x78x4%20si:31732x3916x31732%20si:4x4x2%20si:4x4x4%20si:558x71116x212%20si:6x4x3%20si:6x6x6%20si:7x4x2%20si:8x4x3%20si:24x24x24%20si:20x20x20%20si:18x18x18%20si:16x16x16%20si:13x10x4%20si:1218x918x2%20si:1218x918x1%20si:12x12x12%20si:12x9x3%20si:14x14x14%20si:1118x834x4%20si:1014x814x114%20si:8x8x8%20si:412x6x1%20si:10x10x10%20si:1118x834x2%20prodtype:products&sort=SIZE_SMALL", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/coin-envelopes", "envelopes", "REWRITE", "/envelopes/control/category?af=si:11116x234%20si:214x312%20si:212x414%20si:278x514%20si:3x412%20si:3x478%20si:318x512%20si:338x6%20si:312x612%20si:2x2%20st:openend&sort=MOST_POPULAR", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/specialty-envelopes", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=SPECIALTY_USE", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/shipping-envelopes-plastic-mailers", "envelopes", "REWRITE", "/envelopes/control/category?af=use:shipping%20prodtype:products&sort=SIZE_SMALL", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/9-envelopes", "envelopes", "REWRITE", "/envelopes/control/category?af=si:378x878", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/10-envelopes", "envelopes", "REWRITE", "/envelopes/control/category?af=si:418x912", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/6-x-9-envelopes", "envelopes", "REWRITE", "/envelopes/control/category?af=si:6x9", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/9-x-12-envelopes", "envelopes", "REWRITE", "/envelopes/control/category?af=si:9x12", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/10-x-13-envelopes", "envelopes", "REWRITE", "/envelopes/control/category?af=si:10x13", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/peel-and-press-self-adhesive-envelopes", "envelopes", "REWRITE", "/envelopes/control/search?af=sm:peelpress&w=peel%20&%20press&c=0", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/a1-envelopes", "envelopes", "REWRITE", "/envelopes/control/category?af=si:358x518%20st:squareflap%20st:contourflap", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/a2-envelopes", "envelopes", "REWRITE", "/envelopes/control/category?af=si:438x534%20st:squareflap%20st:contourflap", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/a4-envelopes", "envelopes", "REWRITE", "/envelopes/control/category?af=si:414x614%20st:squareflap%20st:contourflap", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/a6-envelopes", "envelopes", "REWRITE", "/envelopes/control/category?af=si:434x612%20st:squareflap%20st:contourflap", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/a7-envelopes", "envelopes", "REWRITE", "/envelopes/control/category?af=si:514x714", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/a8-envelopes", "envelopes", "REWRITE", "/envelopes/control/category?af=si:512x818&sort=PRICE_HIGH", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/a9-envelopes", "envelopes", "REWRITE", "/envelopes/control/category?af=si:534x834", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/a10-envelopes", "envelopes", "REWRITE", "/envelopes/control/category?af=si:6x912&sort=MOST_POPULAR", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/mailing-tubes", "envelopes", "REWRITE", "/envelopes/control/search?w=mailing+tubes", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/glassine-envelopes", "envelopes", "REWRITE", "/envelopes/control/search?af=cog1:clear%20cog2:clear_30lbglassine&w=glassine&c=0", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/cello-envelopes", "envelopes", "REWRITE", "/envelopes/control/search?w=Cello&c=0", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/10-button-and-string-envelopes", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=ENVELOPE/~product_id=10BS-28W",  undef, undef, undef, $dateTime, "Y");
    insertURLRow("/pillow-boxes", "envelopes", "REWRITE", "/envelopes/control/category?af=si:2x34x3%20si:412x6x1%20si:212x78x4%20st:boxes%20st:pillowbox&sort=SIZE_SMALL", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/paper-cardstock", "envelopes", "REWRITE", "/envelopes/control/category?af=si:12x18%20si:13x19%20si:812x11%20si:11x17%20si:12x12%20si:812x14%20st:paper%20st:cardstock&sort=SIZE_SMALL", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/gift-boxes", "envelopes", "REWRITE", "/envelopes/control/search?w=gift+boxes&c=0", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/Boxes", "envelopes", "REWRITE", "/envelopes/control/search?w=boxes&c=0", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/baronials", "envelopes", "REWRITE", "/envelopes/control/category/~category_id=POINTED_FLAP", undef, undef, undef, $dateTime, "Y");
    insertURLRow("/swatchbook", "envelopes", "REWRITE", "/envelopes/control/product/~category_id=NOTECARDS/~product_id=SWATCHBOOK", undef, undef, undef, $dateTime, "Y");

    # AE / Action Envelope
    insertURLRow("/", "ae", "REWRITE", "/ae/control/main", undef, undef, undef, $dateTime, undef);
    # insertURLRow("/directMail", "ae", "REWRITE", "/ae/control/search?w=direct%20mail&af=prodtype%3adesigns", undef, undef, undef, $dateTime, undef);
    # insertURLRow("/cross-sell", "ae", "REWRITE", "/ae/control/cross-sell", undef, undef, undef, $dateTime, undef);
    # insertURLRow("/addToCart", "ae", "REWRITE", "/ae/control/addToCart", undef, undef, undef, $dateTime, undef);
    # insertURLRow("/cart", "ae", "REWRITE", "/ae/control/cart", undef, undef, undef, $dateTime, undef);
    # insertURLRow("/checkout", "ae", "REWRITE", "/ae/control/checkout", undef, undef, undef, $dateTime, undef);
    # insertURLRow("/receipt", "ae", "REWRITE", "/ae/control/receipt", undef, undef, undef, $dateTime, undef);

    # Bags
    insertURLRow("/", "bags", "REWRITE", "/bags/control/main", undef, undef, undef, $dateTime, undef);

    # Folders
    insertURLRow("/", "folders", "REWRITE", "/folders/control/main", undef, undef, undef, $dateTime, undef);
    insertURLRow("/search", "folders", "REWRITE", "/folders/control/search", undef, undef, undef, $dateTime, undef);
    
    insertURLRow("/quickShipFolders", "folders", "REWRITE", "/folders/control/customFolders?af=st:quickshipfoilstampedfolders%20st:quickshipfourcolorfolders", undef, undef, undef, $dateTime, undef);
    insertURLRow("/extraCapacityFolders", "folders", "REWRITE", "/folders/control/customFolders?categoryId=Specialty%209%20x%2012%20Presentation%20Folder", undef, undef, undef, $dateTime, undef);
    insertURLRow("/smallFolders", "folders", "REWRITE", "/folders/control/customFolders?categoryId=Small%20Folders", undef, undef, undef, $dateTime, undef);
    insertURLRow("/standardPresentationFolders", "folders", "REWRITE", "/folders/control/customFolders?categoryId=Specialty%209%20x%2012%20Presentation%20Folder", undef, undef, undef, $dateTime, undef);
    insertURLRow("/reinforcedFolders", "folders", "REWRITE", "/folders/control/customFolders?categoryId=Specialty%209%20x%2012%20Presentation%20Folder", undef, undef, undef, $dateTime, undef);
    insertURLRow("/reportCovers", "folders", "REWRITE", "/folders/control/customFolders?categoryId=Report%20Cover", undef, undef, undef, $dateTime, undef);
    insertURLRow("/certificateHolders", "folders", "REWRITE", "/folders/control/customFolders?categoryId=Certificate%20Holder", undef, undef, undef, $dateTime, undef);

    insertURLRow("/cross-sell", "folders", "REWRITE", "/folders/control/cross-sell", undef, undef, undef, $dateTime, undef);
    insertURLRow("/addToCart", "folders", "REWRITE", "/folders/control/addToCart", undef, undef, undef, $dateTime, undef);
    insertURLRow("/cart", "folders", "REWRITE", "/folders/control/cart", undef, undef, undef, $dateTime, undef);
    insertURLRow("/checkout", "folders", "REWRITE", "/folders/control/checkout", undef, undef, undef, $dateTime, undef);
    insertURLRow("/receipt", "folders", "REWRITE", "/folders/control/receipt", undef, undef, undef, $dateTime, undef);

    insertURLRow("/", "bigname", "REWRITE", "/bigname/control/main", undef, undef, undef, $dateTime, undef);
    insertURLRow("/contact", "bags", "REWRITE", "/bags/control/contact", undef, undef, undef, $dateTime, undef);
}

$success &&= $design_handle->execute();
while(my $designs = $design_handle->fetchrow_hashref()) {
	my $designName = $$designs{"scene7_template_id"};
	$designName = cleanSeoUrl($designName);

	if(index($designName, "back") < 0 && substr($$designs{"scene7_template_id"}, 0, 1) ne "8") {
		$designName = "design/" . $designName;
		my $productDesc = $$designs{"product_desc"} || "";
		$productDesc = cleanSeoUrl($productDesc);

		if($productDesc ne "") {
			$designName = $designName . "-" . $productDesc;
		}

		my $templateDesc = $$designs{"template_description"} || "";
		$templateDesc = cleanSeoUrl($templateDesc);

		if($templateDesc ne "") {
			$designName = $designName . "-" . $templateDesc;
		}

        insertURLRow("/" . $designName, "envelopes", "REWRITE", "/envelopes/control/product/~designId=" . $$designs{"scene7_template_id"}, undef, $$designs{"scene7_template_id"}, undef, $dateTime);
        #insertURLRow("/" . $designName, "ae", "REWRITE", "/ae/control/product/~designId=" . $$designs{"scene7_template_id"}, undef, $$designs{"scene7_template_id"}, undef, $dateTime);
	}
}

#if a REWRITE rule exist that has the same product ID as an old one, the old one needs to turn to a REDIRECT and the toUrl field needs to become the fromUrl of the latest entry
$success &&= $getDuplicateRewrites->execute($dateTime);
while(my $products = $getDuplicateRewrites->fetchrow_hashref()) {
	$success &&= $updateDuplicateRewrites->execute(
		"REDIRECT",
		$$products{"from_url"},
		"301",
		$dateTime,
		$$products{"product_id"},
		$dateTime,
		$$products{"web_site_id"}
	) or die "Couldn't save data: " . $dbh->errstr;
}

#go through all existing data and discontinue stuff
$success &&= $all_products_handle->execute();
while(my $ruleStatus = $all_products_handle->fetchrow_hashref()) {
    #if product is inactive set the rule to inactive
    if($$ruleStatus{"active"} eq "N") {
        $success &&= $discontinueRules->execute(
            "N",
            $$ruleStatus{"rule_id"},
            $$ruleStatus{"web_site_id"}
        ) or die "Couldn't save data: " . $dbh->errstr;
    } else {
        #if available on envelope site
        if($$ruleStatus{"web_site_id"} eq "envelopes") {
            if(!defined $$ruleStatus{"envelopes"} || (defined $$ruleStatus{"envelopes"} && $$ruleStatus{"envelopes"} eq "N")) {
                $success &&= $discontinueRules->execute(
                    "N",
                    $$ruleStatus{"rule_id"},
                    $$ruleStatus{"web_site_id"}
                ) or die "Couldn't save data: " . $dbh->errstr;
            } else {
                $success &&= $discontinueRules->execute(
                    "Y",
                    $$ruleStatus{"rule_id"},
                    $$ruleStatus{"web_site_id"}
                ) or die "Couldn't save data: " . $dbh->errstr;
            }
        }

        #if available on ae site
        # if($$ruleStatus{"web_site_id"} eq "ae") {
        #     if(!defined $$ruleStatus{"ae"} || (defined $$ruleStatus{"ae"} && $$ruleStatus{"ae"} eq "N")) {
        #         $success &&= $discontinueRules->execute(
        #             "N",
        #             $$ruleStatus{"rule_id"},
        #             $$ruleStatus{"web_site_id"}
        #         ) or die "Couldn't save data: " . $dbh->errstr;
        #     } else {
        #         $success &&= $discontinueRules->execute(
        #             "Y",
        #             $$ruleStatus{"rule_id"},
        #             $$ruleStatus{"web_site_id"}
        #         ) or die "Couldn't save data: " . $dbh->errstr;
        #     }
        # }

        #if available on folders site
        if($$ruleStatus{"web_site_id"} eq "folders") {
            if(!defined $$ruleStatus{"folders"} || (defined $$ruleStatus{"folders"} && $$ruleStatus{"folders"} eq "N")) {
                $success &&= $discontinueRules->execute(
                    "N",
                    $$ruleStatus{"rule_id"},
                    $$ruleStatus{"web_site_id"}
                ) or die "Couldn't save data: " . $dbh->errstr;
            } else {
                $success &&= $discontinueRules->execute(
                    "Y",
                    $$ruleStatus{"rule_id"},
                    $$ruleStatus{"web_site_id"}
                ) or die "Couldn't save data: " . $dbh->errstr;
            }
        }

        #if available on bags site
        if($$ruleStatus{"web_site_id"} eq "bags") {
            if(!defined $$ruleStatus{"bags"} || (defined $$ruleStatus{"bags"} && $$ruleStatus{"bags"} eq "N")) {
                $success &&= $discontinueRules->execute(
                    "N",
                    $$ruleStatus{"rule_id"},
                    $$ruleStatus{"web_site_id"}
                ) or die "Couldn't save data: " . $dbh->errstr;
            } else {
                $success &&= $discontinueRules->execute(
                    "Y",
                    $$ruleStatus{"rule_id"},
                    $$ruleStatus{"web_site_id"}
                ) or die "Couldn't save data: " . $dbh->errstr;
            }
        }
    }
}

open (PROXY, '>/usr/local/ofbiz/hot-deploy/envelopes/config/proxy.properties');
open (REWRITE, '>/usr/local/ofbiz/etc/proxy_rewrite.txt');
open (REDIRECT, '>/usr/local/ofbiz/etc/proxy_redirect.txt');

open (AEPROXY, '>/usr/local/ofbiz/hot-deploy/envelopes/config/ae-proxy.properties');
open (AEREWRITE, '>/usr/local/ofbiz/etc/ae-proxy_rewrite.txt');
open (AEREDIRECT, '>/usr/local/ofbiz/etc/ae-proxy_redirect.txt');

open (BAGSPROXY, '>/usr/local/ofbiz/hot-deploy/bags/config/bags-proxy.properties');
open (BAGSREWRITE, '>/usr/local/ofbiz/etc/bags-proxy_rewrite.txt');
open (BAGSREDIRECT, '>/usr/local/ofbiz/etc/bags-proxy_redirect.txt');

open (FOLDERSPROXY, '>/usr/local/ofbiz/hot-deploy/folders/config/folders-proxy.properties');
open (FOLDERSREWRITE, '>/usr/local/ofbiz/etc/folders-proxy_rewrite.txt');
open (FOLDERSREDIRECT, '>/usr/local/ofbiz/etc/folders-proxy_redirect.txt');

open (BIGNAMEPROXY, '>/usr/local/ofbiz/hot-deploy/bigname/config/bigname-proxy.properties');
open (BIGNAMEREWRITE, '>/usr/local/ofbiz/etc/bigname-proxy_rewrite.txt');
open (BIGNAMEREDIRECT, '>/usr/local/ofbiz/etc/bigname-proxy_redirect.txt');

$success &&= $getRules->execute();
while(my $rules = $getRules->fetchrow_hashref()) {
    if($$rules{"web_site_id"} eq "envelopes") {
         if($$rules{"rewrite_type_id"} eq "REWRITE") {
            print REWRITE $$rules{"from_url"} . "\t" . $$rules{"to_url"} . ";\n";
            print PROXY makePropertyFriendly($$rules{"to_url"}) . "=" . makePropertyFriendly($$rules{"from_url"}) . "\n";
         } elsif($$rules{"rewrite_type_id"} eq "REDIRECT") {
            print REDIRECT $$rules{"from_url"} . "\t" . $$rules{"to_url"} . ";\n";
         }
    # } elsif($$rules{"web_site_id"} eq "ae") {
    #     if($$rules{"rewrite_type_id"} eq "REWRITE") {
    #         print AEREWRITE $$rules{"from_url"} . "\t" . $$rules{"to_url"} . ";\n";
    #         if(!defined $$rules{"product_id"} && !defined $$rules{"scene7_template_id"}) {
    #             print AEPROXY makePropertyFriendly($$rules{"to_url"}) . "=" . makePropertyFriendly($$rules{"from_url"}) . "\n";
    #         }
    #     } elsif($$rules{"rewrite_type_id"} eq "REDIRECT") {
    #         print AEREDIRECT $$rules{"from_url"} . "\t" . $$rules{"to_url"} . ";\n";
    #     }
    } elsif($$rules{"web_site_id"} eq "bags") {
		if($$rules{"rewrite_type_id"} eq "REWRITE") {
			print BAGSREWRITE $$rules{"from_url"} . "\t" . $$rules{"to_url"} . ";\n";
			if(!defined $$rules{"product_id"} && !defined $$rules{"scene7_template_id"}) {
				print BAGSPROXY makePropertyFriendly($$rules{"to_url"}) . "=" . makePropertyFriendly($$rules{"from_url"}) . "\n";
			}
		} elsif($$rules{"rewrite_type_id"} eq "REDIRECT") {
			print BAGSREDIRECT $$rules{"from_url"} . "\t" . $$rules{"to_url"} . ";\n";
		}
    } elsif($$rules{"web_site_id"} eq "folders") {
		if($$rules{"rewrite_type_id"} eq "REWRITE") {
			print FOLDERSREWRITE $$rules{"from_url"} . "\t" . $$rules{"to_url"} . ";\n";
			print FOLDERSPROXY makePropertyFriendly($$rules{"to_url"}) . "=" . makePropertyFriendly($$rules{"from_url"}) . "\n";
		} elsif($$rules{"rewrite_type_id"} eq "REDIRECT") {
			print FOLDERSREDIRECT $$rules{"from_url"} . "\t" . $$rules{"to_url"} . ";\n";
		}
	} elsif($$rules{"web_site_id"} eq "bigname") {
		if($$rules{"rewrite_type_id"} eq "REWRITE") {
			print BIGNAMEREWRITE $$rules{"from_url"} . "\t" . $$rules{"to_url"} . ";\n";
			if(!defined $$rules{"product_id"} && !defined $$rules{"scene7_template_id"}) {
				print BIGNAMEPROXY makePropertyFriendly($$rules{"to_url"}) . "=" . makePropertyFriendly($$rules{"from_url"}) . "\n";
			}
		} elsif($$rules{"rewrite_type_id"} eq "REDIRECT") {
			print BIGNAMEREDIRECT $$rules{"from_url"} . "\t" . $$rules{"to_url"} . ";\n";
		}
	}
}

close (PROXY);
close (REWRITE);
close (REDIRECT);
close (AEPROXY);
close (AEREWRITE);
close (AEREDIRECT);
close (BAGSPROXY);
close (BAGSREWRITE);
close (BAGSREDIRECT);
close (FOLDERSPROXY);
close (FOLDERSREWRITE);
close (FOLDERSREDIRECT);
close (BIGNAMEPROXY);
close (BIGNAMEREWRITE);
close (BIGNAMEREDIRECT);

$all_products_handle->finish();
$products_handle->finish();
$product_handle->finish();
$design_handle->finish();
$product_category->finish();
$color_warehouse->finish();
$features_handle->finish();
$features_type_handle->finish();
$color_shop->finish();
$color_shop_inner->finish();
$collection_shop->finish();
$collection_shop_inner->finish();
$insertURL->finish();
$getDate->finish();
$getDuplicateRewrites->finish();
$updateDuplicateRewrites->finish();
$getRules->finish();
$discontinueRules->finish();

#these escapes are done for delimiter reasons when processing a java properties file
sub makePropertyFriendly {
    my $str = $_[0];
    $str =~ s/\=/!/g; #escape = with !
    $str =~ s/\:/#/g; #escape : with #
    return $str;
}

sub cleanSeoUrl {
	my $str = $_[0];
	$str =~ s/^\s+|\s+$//g; #remove trailing and leading spaces
	if($str eq "") {
		return $str;
	}

	$str = lc($str); #lower case all letters
	$str =~ s/\//-/g; #remove slashes
	$str =~ s/\<.*?\>//g; #remove everything in tags
	$str =~ s/\[([^\[\]]|(?0))*]//g; #remove everything in brackets
	$str =~ s/\([^)]*\)//g; #remove everything in parenthesis
	$str =~ s/[^a-zA-Z0-9 \/-]//g; #remove all unless letter, number, space, slash, hyphen
	$str =~ s/^\s+|\s+$//g; #remove trailing and leading spaces
	$str =~ s/-/ /g; #replace hyphens with spaces
	$str =~ s/\s+/ /g; #replace multiple spaces with a single space
	$str =~ s/^\s+|\s+$//g; #remove trailing and leading spaces
	$str =~ s/ /-/g; #replace spaces with hyphens
	return $str;
}

sub toMD5 {
	return md5_hex($_[0]);
}

sub insertURLRow {
	my ($friendly, $websiteId, $rewriteTypeId, $nonFriendly, $productId, $scene7TemplateId, $responseCode, $dateTime, $manual) = @_;
	$success &&= $insertURL->execute(
		toMD5($friendly),
		$websiteId,
		$rewriteTypeId,
		$friendly,
		$nonFriendly,
		$productId,
		$scene7TemplateId,
		$responseCode,
		$dateTime,
		$manual,
		$friendly,
		$nonFriendly,
		$responseCode,
		$dateTime,
	) or die "Couldn't save data: " . $dbh->errstr;
}

# returns features type array
sub get_features_of_type {
	my $product_id = $_[0];
    my $feature_type_id = $_[1];

	my @features = ();

	$features_type_handle->execute($product_id, $feature_type_id);
	while(my $feature = $features_type_handle->fetchrow_hashref()) {
	    push @features, $feature;
	}
	return @features;
}

# end transaction
my $result = ($success ? $dbh->commit : $dbh->rollback);

unless($result) {
	die "Couldn't finish transaction: " . $dbh->errstr;
}

$dbh->disconnect();