#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use DBI;
use Ofbiz;
use XML::Writer;

use constant MAPPING_FILE => "/usr/local/ofbiz/etc/proxy_rewrite.txt";

my $ofbiz = Ofbiz->new(conf => "/usr/local/ofbiz/framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0})
	or die "Couldn't connect to database: " . DBI->errstr;

$dbh->do('set names utf8');

# retrieve details for fifty envelope bundles
my $variants_unique_handle = $dbh->prepare(qq{
	SELECT
	    p.product_id AS product_id,
	    p.parent_product_id as parent_product_id
	FROM
	    product p
	INNER JOIN
		product_web_site pws
			ON p.product_id = pws.product_id
	WHERE
	    p.is_virtual = 'N'
	        AND p.is_variant = 'Y'
	        AND (p.sales_discontinuation_date > NOW()
	        OR p.sales_discontinuation_date IS NULL)
	        AND (pws.ae = 'Y' OR pws.envelopes = 'Y')
	ORDER BY p.parent_product_id ASC
});

# retrieve details for fifty envelope bundles
my $variants_handle = $dbh->prepare(qq{
	SELECT
	    p.product_id AS product_id,
	    p.parent_product_id as parent_product_id,
	    p.tag_line AS tag_line,
	    p.internal_name AS name,
	    p.product_type_id AS product_type_id,
	    p.small_image_url AS image_url,
	    p.long_description AS description,
	    p.color_description AS alt_description,
	    p.product_height AS height,
	    p.product_width AS width,
	    p.product_weight AS weight,
	    p.primary_product_category_id AS category_id,
	    pc.category_name AS category_name,
	    p.has_color_opt AS has_color_opt,
	    p.on_sale AS on_sale,
	    p.on_clearance AS on_clearance,
	    pp.price AS price,
	    pp.original_price as original_price,
	    pc.primary_parent_category_id AS parent_category_id,
	    pp.quantity AS quantity,
	    pp.colors AS colors,
        rc.rank AS rank,
        p.is_printable as printable,
        case when p.created_stamp >= date_sub(now(), interval 12 month) then "Y" else "N" end as new,
        case when pp.original_price is not null then ROUND((1-(pp.price/pp.original_price))*100) else 0 end as percent_savings,
		pa.asset_name as asset_name,
		pa.asset_default as asset_default,
		p.created_stamp as created_stamp
	FROM
	    product p
	        INNER JOIN
	    product_price pp ON p.product_id = pp.product_id
	        INNER JOIN
	    product_category pc ON p.primary_product_category_id = pc.product_category_id
			LEFT JOIN
		product_recommendation rc on p.product_id = rc.product_id
			LEFT JOIN
		product_assets pa on pa.product_id = p.product_id
	WHERE
	    p.product_id = ?
	        AND pp.product_price_type_id = 'DEFAULT_PRICE'
	        AND (pp.thru_date > NOW() OR pp.thru_date IS NULL)
	        AND (p.sales_discontinuation_date > NOW() OR p.sales_discontinuation_date IS NULL)
	ORDER BY quantity ASC, colors ASC, asset_default DESC LIMIT 1
});

# retrieve product features
my $features_handle = $dbh->prepare(qq{
	SELECT
	    pfa.product_feature_id,
	    pf.product_feature_type_id,
	    pf.description,
	    pf.default_sequence_num
	FROM
	    product_feature_appl pfa
	        INNER JOIN
	    product_feature pf ON pfa.product_feature_id = pf.product_feature_id
	WHERE
	    pfa.product_id = ?
});


# check whether envelope is a member of specified category
my $member_handle = $dbh->prepare(qq{
	SELECT
	    pc.product_category_id
	FROM
	    product_category_member pcm
	        INNER JOIN
	    product_category pc ON pcm.product_category_id = pc.product_category_id
	WHERE
	    pc.product_category_id = ?
	        AND pcm.product_id = ?
});

# get average rating for a product
my $getRating = $dbh->prepare(qq{
	select count(*), (ROUND(avg(product_rating)*2)/2) from product_review where product_id = ? and status_id = 'PRR_APPROVED' and sales_channel_enum_id = 'ENV_SALES_CHANNEL'
});

# get average rating for a product
my $getTagLine = $dbh->prepare(qq{
	select prod.tag_line from product prod inner join product_assoc pa on prod.product_id = pa.product_id where pa.product_id_to = ?
});

#get all other categories
my $all_product_categories = $dbh->prepare(qq{
	SELECT
        pc.product_category_id AS product_category_id,
        pc.primary_parent_category_id AS primary_parent_category_id,
        pc.category_name AS category_name
    FROM
        product_category pc
            INNER JOIN
        product_category_member pcm ON pc.product_category_id = pcm.product_category_id
    WHERE
    	pcm.thru_date IS NULL
        AND pcm.product_id  in (?)
});

die "Couldn't prepare queries; aborting" unless defined $variants_handle && defined $features_handle && defined $member_handle;


# retrieve envelope redirects
die "URL mappings file does not exist; aborting"
	unless -f MAPPING_FILE;

my %url_mapping;
open(my $fh, "<", MAPPING_FILE)
	or die "Couldn't open mappings file at " . MAPPING_FILE;

while (<$fh>) {
	chomp;
	chop;
	my ($key, $val)    = split(/\t/);
	$url_mapping{$val} = $key
		if (defined $key && defined $val);
}

close($fh) or die "Couldn't close mappings file at " . MAPPING_FILE;

# returns feature hash
sub get_features
{
	my $product_id = shift;
	my %features   = ();

	$features_handle->execute($product_id);
	while(my $feature = $features_handle->fetchrow_hashref()) {
		$features{ lc $$feature{"product_feature_type_id"} } = $$feature{"description"}; }
	return %features;
}

# returns name of the specialy shop this envelope belongs to
sub get_specialty_shop
{
	my $product_id = shift;

	$member_handle->execute("PEEL_N_SEAL", $product_id);
	if ($member_handle->rows()) { return "Y"; }

	return "N";
}

# returns the tagline
sub getTagLine
{
	my $product_id = shift;
	my $tagLine = "";
	$getTagLine->execute($product_id);
	my($tag) = $getTagLine->fetchrow_array();
	if(defined $tag)
	{
		$tagLine = $tag;
	}
	return $tagLine;
}

sub cleanFacetName {
	my $facetName = shift;
	if(defined $facetName) {
		$facetName =~ s/[^a-zA-Z0-9]//g;
	}
	return $facetName;
}

# build sli xml
my %category_data = ();

my $output = IO::File->new(">/tmp/certona_envelopes.xml");
my $doc = new XML::Writer(OUTPUT => $output, DATA_MODE => 1, DATA_INDENT => 4);

$doc->xmlDecl("UTF-8");
$doc->startTag("certonaFeed");
$doc->startTag("items");

# product information
my $parentProductId = "";
$variants_unique_handle->execute();
my $isFirstPassThrough = 1;
while (my $uniqueProd = $variants_unique_handle->fetchrow_hashref()) {
	$variants_handle->execute($$uniqueProd{"product_id"});
	while (my $product = $variants_handle->fetchrow_hashref()) {
		#print $$product{"product_id"} . "\n";
		my $qty = $$product{"quantity"};
		my $brandItem = $$product{"has_color_opt"};

		my $prodRating = 0;
		my $totalReviews = 0;
		$getRating->execute($$product{"product_id"});
		my($total, $rating) = $getRating->fetchrow_array();
		if(defined $rating && $rating > 0) {
			$prodRating = $rating;
			$totalReviews = $total;
		}

		my $prodTagLine = "";
		$prodTagLine = $$product{"tag_line"};
		#if the variant doesnt have a tagline, lets get it from virtuals
		if(!defined $prodTagLine || $prodTagLine eq "") {
			$prodTagLine = getTagLine($$product{"product_id"});
		}

		# retrieve product features
		my %features = get_features($$product{"product_id"});

		# scrub invalid characters
		$$product{"name"}        =~ s/[^!-~\s]//g if defined $$product{"name"};
		$$product{"description"} =~ s/[^!-~\s]//g if defined $$product{"description"};

		# if needed, modify the product name
		if($$product{"product_type_id"} eq "ENVELOPE" && $$product{"category_id"} ne "PETALS" && $$product{"category_id"} ne "POCKETS") {
			if ($$product{"name"} !~ m/envelope/i) {
				# add envelopes to product name
				$$product{"name"} .= " Envelopes";
			}
		}

		# make sure description is not blank
		if ((!defined $$product{"description"}) || ($$product{"description"} eq "")) {
			$$product{"description"} = $$product{"alt_description"};
		}

		# generate the url parameters
		my $url_domain = "https://www.envelopes.com";
		my $url_path   = sprintf("/envelopes/control/product/~category_id=%s/~product_id=%s", $$product{"category_id"}, $$product{"product_id"});
		my $url_seo_path = $url_mapping{$url_path} || $url_path;

		my $productName = $$product{"name"};
		# make brand shop name
		if($brandItem)	{
			if(defined $features{"mill"}) {
				$productName = $productName . " - " . $features{"mill"};
			}
			if(defined $features{"paper_weight"}) {
				$productName = $productName . " " . $features{"paper_weight"};
			}
			if(defined $features{"grade"}) {
				$productName = $productName . " " . $features{"grade"};
			}
		}

		my $category_id_list = "";
		$all_product_categories->execute($$product{"product_id"});
		while (my($catId, $parentCatId, $catName) = $all_product_categories->fetchrow_array()) {
			if(defined $catName) {
				if($category_id_list eq "") {
					$category_id_list = cleanFacetName(lc $catId);
				} else {
					$category_id_list = $category_id_list . "," . cleanFacetName(lc $catId);
				}
			}
			$category_data{cleanFacetName(lc $catId)} = {"name" => $catName, "parentid" => cleanFacetName(lc $parentCatId)};
		}

		if($isFirstPassThrough) {
			$doc->startTag("item", "name" => "id", "value" => $$uniqueProd{"parent_product_id"});
			$doc->dataElement("attribute",        ucfirst lc $$product{"product_type_id"}, "name" => "type");
			$doc->cdataElement("attribute",       $features{"size"}, "name" => "size");
			$doc->dataElement("attribute",        $features{"metric_size"} || undef, "name" => "metricsize");
			$doc->cdataElement("attribute",       $features{"size_code"} || undef, "name" => "sizecode");
			$doc->dataElement("attribute",        $category_id_list, "name" => "categories");
			$isFirstPassThrough = 0;
		} elsif(!$isFirstPassThrough && $parentProductId ne $$uniqueProd{"parent_product_id"}) {
			$doc->endTag("item");
			$doc->startTag("item", "name" => "id", "value" => $$uniqueProd{"parent_product_id"});
			$doc->dataElement("attribute",        ucfirst lc $$product{"product_type_id"}, "name" => "type");
			$doc->cdataElement("attribute",       $features{"size"}, "name" => "size");
			$doc->dataElement("attribute",        $features{"metric_size"} || undef, "name" => "metricsize");
			$doc->cdataElement("attribute",       $features{"size_code"} || undef, "name" => "sizecode");
			$doc->dataElement("attribute",        $category_id_list, "name" => "categories");
		}

		$parentProductId = $$uniqueProd{"parent_product_id"};

		$doc->startTag("child", "name" => "id", "value" => $$product{"product_id"});
		#$doc->dataElement("tns:parentid",     $$product{"parent_product_id"} || undef);
		$doc->cdataElement("attribute",       $productName, "name" => "itemname");
		$doc->cdataElement("attribute",       $features{"color"}, "name" => "colorname");
		$doc->cdataElement("attribute",       $features{"brand"} || undef, "name" => "brand");
		$doc->cdataElement("attribute",       sprintf("%s%s", $url_domain, $url_seo_path), "name" => "detailurl");
		$doc->cdataElement("attribute",       sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/%s%s", $$product{"product_id"}, "?wid=700&hei=440&fmt=png-alpha"), "name" => "image-main");
		$doc->dataElement("attribute",        sprintf("%0.2f", $$product{"price"}), "name" => "currentprice");
		$doc->dataElement("attribute",	      (defined $$product{"original_price"}) ? sprintf("%0.2f", $$product{"original_price"}) : "", "name" => "originalprice");
		$doc->dataElement("attribute",        $$product{"quantity"}, "name" => "quantity");
		$doc->dataElement("attribute",        $features{"paper_weight"} || undef, "name" => "paperweight");
		$doc->cdataElement("attribute",       $features{"availability"} || undef, "name" => "availability");
		$doc->dataElement("attribute",        $features{"collection"} || undef, "name" => "collection");
		$doc->cdataElement("attribute",       $features{"recycled_content"} || undef, "name" => "recycledcontent");
		$doc->cdataElement("attribute",	      $features{"paper_texture"} || undef, "name" => "papertexture");
		$doc->dataElement("attribute",        $features{"color_group"} || undef, "name" => "colorgroup");
		$doc->dataElement("attribute",        $features{"inkjet"} || "N", "name" => "ink");
		$doc->dataElement("attribute",        $features{"laser"} || "N", "name" => "laser");
		$doc->cdataElement("attribute",       $features{"recycled"} || "N", "name" => "recycled");
		$doc->cdataElement("attribute",       $features{"sealing_method"} || undef, "name" => "sealingmethod");
		$doc->dataElement("attribute",        $features{"sfi_certified"} || "N", "name" => "sfi");
		$doc->dataElement("attribute",        $features{"fsc_certified"} || "N", "name" => "fsc");
		$doc->dataElement("attribute",        get_specialty_shop($$product{"product_id"}), "name" => "peelandseal");
		$doc->dataElement("attribute",        ($prodRating > 0) ? $prodRating : 0, "name" => "rating");
		$doc->dataElement("attribute",        ($prodRating > 0) ? $totalReviews : 0, "name" => "reviewcount");
		$doc->dataElement("attribute",        (defined $$product{"on_clearance"} && $$product{"on_clearance"} eq "Y") ? "Y" : "N", "name" => "clearanceflag");
		$doc->dataElement("attribute",        (defined $$product{"on_sale"} && $$product{"on_sale"} eq "Y") ? "Y" : "N", "name" => "saleflag");
		$doc->dataElement("attribute",        $$product{"new"} || "N", "name" => "newflag");
		$doc->endTag("child");
	}
}
$doc->endTag("item");
$doc->endTag("items");

#globallist
$doc->startTag("globallists");
$doc->startTag("categories");
$doc->startTag("category");
$doc->dataElement("id",         "ENVELOPES");
$doc->dataElement("type",       "CATALOG");
$doc->cdataElement("name",      "Envelopes");
$doc->cdataElement("url",       "");
$doc->dataElement("parentid",   "");
$doc->endTag("category");
foreach my $k (keys %category_data) {
	# generate the url parameters
	my $url_domain = "https://www.envelopes.com";
	my $url_path   = sprintf("/envelopes/control/category/~category_id=%s", $k);
	my $url_seo_path = $url_mapping{$url_path} || $url_path;

	$doc->startTag("category");
	$doc->dataElement("id",         $k);
	$doc->dataElement("type",       (defined $category_data{$k}{"parentid"} && $category_data{$k}{"parentid"} eq "ENVELOPES") ? "CATEGORY" : "STYLE");
	$doc->cdataElement("name",      $category_data{$k}{"name"} || undef);
	$doc->cdataElement("url",       $url_domain . $url_seo_path);
	$doc->dataElement("parentid",   $category_data{$k}{"parentid"} || undef);
	$doc->endTag("category");
}
$doc->endTag("categories");
$doc->endTag("globallists");

$doc->endTag("certonaFeed");
$doc->end();

$variants_unique_handle->finish();
$variants_handle->finish();
$features_handle->finish();
$member_handle->finish();
$getRating->finish();
$getTagLine->finish();
$all_product_categories->finish();

$dbh->disconnect();