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
	    p.product_id AS product_id
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
	        AND (pws.ae = 'Y' OR PWS.envelopes = 'Y')
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
my $getRatingTypes = $dbh->prepare(qq{
	select case when describe_Yourself is null then "Other" else describe_yourself end, count(*) from product_review where product_id = ? and status_id = 'PRR_APPROVED' and sales_channel_enum_id = 'ENV_SALES_CHANNEL' group by describe_Yourself
});

# get average rating for a product
my $getTagLine = $dbh->prepare(qq{
	select prod.tag_line from product prod inner join product_assoc pa on prod.product_id = pa.product_id where pa.product_id_to = ?
});

#getting scene7 data
my $getScene7Data = $dbh->prepare(qq{
	select st.scene7_template_id, st.template_description, spa.product_id, st.base_quantity, st.base_price, st.product_desc from scene7_template st inner join scene7_prod_assoc spa on st.scene7_template_id = spa.scene7_template_id where spa.template_type_id = 'ADVANCE' and spa.template_assoc_type_id = 'TEMPLATE_FRONT' and st.print_price_description is not null and st.thru_date is null
});

my $getScene7Category = $dbh->prepare(qq{
	select scene7_template_id, category_id, description from scene7_template_category where thru_date is null and scene7_template_id = ?
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

close($fh)
  or die "Couldn't close mappings file at " . MAPPING_FILE;

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

# returns category hash
sub get_scene7_cat
{
	my $template_id = shift;
	my %cats   = ();

	$getScene7Category->execute($template_id);
	while(my $cat = $getScene7Category->fetchrow_hashref()) {
		if(defined $cats{ lc $$cat{"category_id"} } && $cats{ lc $$cat{"category_id"} } ne "") {
			$cats{ lc $$cat{"category_id"} } = $cats{ lc $$cat{"category_id"} } . "," . $$cat{"description"};
		} else {
			$cats{ lc $$cat{"category_id"} } = $$cat{"description"};
		}
	}
	return %cats;
}

# build sli xml
my $output = IO::File->new(">/usr/local/ofbiz/etc/sli_envelopes.xml");
my $doc = new XML::Writer(OUTPUT => $output, DATA_MODE => 1, DATA_INDENT => 4);

$doc->xmlDecl("UTF-8");
$doc->startTag("tns:products", "xsi:schemaLocation" => "http://www.example.org/SLISchema SLISchema.xsd");

# product information


$variants_unique_handle->execute();
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

		my $reviewers = "";
		$getRatingTypes->execute($$product{"product_id"});
		while (my($reviewerType, $totalReviewerCount) = $getRatingTypes->fetchrow_array()) {
			if($reviewerType ne "Accounting Services"
				&& $reviewerType ne "Banking"
				&& $reviewerType ne "Bride"
				&& $reviewerType ne "Ecommerce"
				&& $reviewerType ne "Education"
				&& $reviewerType ne "Event Planner"
				&& $reviewerType ne "Florist"
				&& $reviewerType ne "Government"
				&& $reviewerType ne "Graphic Designer"
				&& $reviewerType ne "Groom"
				&& $reviewerType ne "Home Improvement Services"
				&& $reviewerType ne "Homemaker"
				&& $reviewerType ne "Legal Services"
				&& $reviewerType ne "Medical Services"
				&& $reviewerType ne "Non-Profit"
				&& $reviewerType ne "Photographer"
				&& $reviewerType ne "Printer"
				&& $reviewerType ne "Realtor"
				&& $reviewerType ne "Religious Institution"
				&& $reviewerType ne "Retail Sales"
				&& $reviewerType ne "Stationery Designer"
				&& $reviewerType ne "Student"
				&& $reviewerType ne "Teacher"
				&& $reviewerType ne "Web Designer") {
				$reviewerType = "Other";
			}
			if($reviewers eq "") {
				#$reviewers = $reviewerType . " (" . $totalReviewerCount . ")";
				$reviewers = $reviewerType;
			} else {
				#$reviewers = $reviewers . "|" . $reviewerType . " (" . $totalReviewerCount . ")";
				if(index($reviewers, $reviewerType) == -1) {
					$reviewers = $reviewers . "|" . $reviewerType;
				}
			}
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
		my $url_domain_uf = "https://www.actionenvelope.com";
		my $url_path   = sprintf("/envelopes/control/product/~category_id=%s/~product_id=%s", $$product{"category_id"}, $$product{"product_id"});
		my $ae_url_path   = sprintf("/ae/control/product/~category_id=%s/~product_id=%s", $$product{"category_id"}, $$product{"product_id"});
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

		my $colorImage = "";
		if(defined $features{"color"}) {
			$colorImage = lc $features{"color"};
			$colorImage =~ s/w\///g;
			$colorImage =~ s/ /_/g;
			$colorImage =~ s/-/_/g;
			$colorImage =~ s/[^_a-z0-9]//g;
		}

		my $colorGroupImage = "";
		if(defined $features{"color_group"}) {
			if($features{"color_group"} eq "Midnight Black") {
				$features{"color_group"} = "Black";
			} elsif($features{"color_group"} eq "Ivory") {
				$features{"color_group"} = "Natural";
			} elsif($features{"color_group"} eq "Quartz") {
				$features{"color_group"} = "Natural";
			}
			$colorGroupImage = lc $features{"color_group"};
			$colorGroupImage =~ s/ /_/g;
		}

		my $category_name_list = "";
		my $category_id_list = "";
		$all_product_categories->execute($$product{"product_id"});
		while (my($catId, $parentCatId, $catName) = $all_product_categories->fetchrow_array()) {
			if(defined $catName && $parentCatId ne "ENVELOPES" && $category_name_list ne "") {
				#if(index(lc $category_name_list, lc $catName) == -1) {
					$category_name_list = $category_name_list . "," . (ucfirst lc $catName);
				#}
			} elsif(defined $catName && $parentCatId ne "ENVELOPES" && $category_name_list eq "") {
				$category_name_list = ucfirst lc $catName;
			}

			if(defined $catId && $parentCatId eq "ENVELOPES" && $category_id_list ne "") {
				if(index(lc $category_id_list, lc $catId) == -1) {
					$category_id_list = $category_id_list . "," . (ucfirst lc $catId);
				}
			} elsif(defined $catId && $parentCatId eq "ENVELOPES" && $category_id_list eq "") {
				$category_id_list = ucfirst lc $catId;
			}
		}

		my $size = $features{"size"};
		#my $sizeCode  = $features{"size_code"};
		#if(defined $size && defined $sizeCode && $size ne $sizeCode) {
		#	$size = $sizeCode . " (" . $size . ")";
		#}

		$doc->startTag("tns:product");
		$doc->dataElement("tns:productType",           "Products");
		$doc->dataElement("tns:name",                  $productName);
		$doc->dataElement("tns:color",                 $features{"color"});
		if($colorImage ne "" && !$brandItem) {
			$doc->dataElement("tns:colorImage",		   "");
		}

		$doc->dataElement("tns:parentid",              $$product{"parent_product_id"} || undef);
		$doc->dataElement("tns:productid",             $$product{"product_id"});
		$doc->dataElement("tns:url",                   sprintf("%s%s", $url_domain, $url_seo_path));
		$doc->dataElement("tns:unfriendlyUrl",         sprintf("%s%s", $url_domain_uf, $ae_url_path));
		$doc->dataElement("tns:basePrice",             sprintf("%0.2f", $$product{"price"}));
		$doc->dataElement("tns:image",                 sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/%s%s", $$product{"product_id"}, "?wid=700&hei=440&fmt=png-alpha"));
		$doc->dataElement("tns:baseQuantity",          $$product{"quantity"});
		$doc->dataElement("tns:paperWeight",           $features{"paper_weight"} || undef);
		$doc->dataElement("tns:availability",          $features{"availability"} || undef);
		$doc->dataElement("tns:collection",            $features{"collection"} || undef);
		$doc->dataElement("tns:size",                  $size);
		$doc->dataElement("tns:metricSize",            $features{"metric_size"} || undef);
		$doc->dataElement("tns:recycledContent",       $features{"recycled_content"} || undef);
		$doc->dataElement("tns:paperTexture",	       $features{"paper_texture"} || undef);
		$doc->dataElement("tns:colorGroup",            $features{"color_group"} || undef);
		if($colorGroupImage ne "") {
			$doc->dataElement("tns:colorGroupImage",   "");
		}

		$doc->dataElement("tns:sizeCode",              $features{"size_code"} || undef);

		if (!$brandItem) {
			$doc->dataElement("tns:brand",             "");
		} else {
			$doc->dataElement("tns:brand",             $features{"mill"} || undef);
			$doc->dataElement("tns:paperGrade",        $features{"grade"} || undef);
		}

		$doc->dataElement("tns:ink",                   $features{"inkjet"} || "N");
		$doc->dataElement("tns:laser",                 $features{"laser"} || "N");
		$doc->dataElement("tns:recycled",              $features{"recycled"} || "N");
		$doc->dataElement("tns:sealingMethod",         $features{"sealing_method"} || undef);

		$doc->dataElement("tns:SFI",                   $features{"sfi_certified"} || "N");
		$doc->dataElement("tns:FSC",                   $features{"fsc_certified"} || "N");
		$doc->dataElement("tns:peelAndSeal",           get_specialty_shop($$product{"product_id"}));
		$doc->dataElement("tns:type",                  ucfirst lc $$product{"product_type_id"});
		$doc->dataElement("tns:productStyle",          $category_name_list);
		$doc->dataElement("tns:productUse",            $category_id_list);

		if($prodRating > 0) {
			$doc->dataElement("tns:avgRating",         $prodRating);
			$doc->dataElement("tns:numOfReviews",      $totalReviews);
			$doc->dataElement("tns:ratingsAttribute",  $reviewers);
		}

		if($prodTagLine ne "") {
			$doc->dataElement("tns:tagLine",           $prodTagLine);
		}

		my $saleStr = "";
		if(defined $$product{"on_sale"} && $$product{"on_sale"} eq "Y") {
			$saleStr = "Sale";
		}
		if (defined $$product{"on_clearance"} && $$product{"on_clearance"} eq "Y") {
			$saleStr = "Clearance";

        }
		$doc->dataElement("tns:onSale",	           $saleStr);

		if(defined $$product{"original_price"}) {
			$doc->dataElement("tns:originalPrice",	   sprintf("%0.2f", $$product{"original_price"}));
		} else {
			$doc->dataElement("tns:originalPrice",	   "");
		}

		$doc->dataElement("tns:salesRank",             $$product{"rank"} || "0");
		$doc->dataElement("tns:manualRank",	           "0");
		$doc->dataElement("tns:printable",             $$product{"printable"} || "N");
		$doc->dataElement("tns:new",        	       $$product{"new"} || "N");
		$doc->dataElement("tns:percentSavings",        $$product{"percent_savings"} || 0);
		$doc->dataElement("tns:createdStamp",          $$product{"created_stamp"});

		$doc->endTag();
	}
}

$getScene7Data->execute();
my %designHash = ();
while (my $s7Data = $getScene7Data->fetchrow_hashref()) {
	if(!exists($designHash{$$s7Data{"scene7_template_id"}})) {
		my %s7cats = get_scene7_cat($$s7Data{"scene7_template_id"});
		my %features = get_features($$s7Data{"product_id"});

		my $templateName = "";
		if(defined $$s7Data{"template_description"}) {
			$templateName = $templateName . $$s7Data{"template_description"};
		} else {
			$templateName = $templateName . $$s7Data{"scene7_template_id"};
		}

		if(defined $$s7Data{"product_desc"}) {
			$templateName = $templateName . " - " . $$s7Data{"product_desc"};
		}

		my $originalPrice = 0;
		my $basePrice = $$s7Data{"base_price"};
		if(($$s7Data{"scene7_template_id"} =~ m/^10/ && length($$s7Data{"scene7_template_id"}) == 6) || ($$s7Data{"scene7_template_id"} =~ m/^5/ && length($$s7Data{"scene7_template_id"}) == 5)) {
			$originalPrice = $basePrice;
			$basePrice = $originalPrice - ($basePrice*.25);
		} elsif((defined $$s7Data{"template_description"} && index(lc($$s7Data{"template_description"}), "thank you") != -1) || (defined $$s7Data{"product_desc"} && index(lc($$s7Data{"product_desc"}), "thank you") != -1)) {
			$originalPrice = $basePrice;
			$basePrice = $originalPrice - ($basePrice*.25);
		}
		$doc->startTag("tns:product");
		$doc->dataElement("tns:productType",    "Designs");
		$doc->dataElement("tns:name",           $templateName);
		$doc->dataElement("tns:url",            "https://www.envelopes.com/envelopes/control/designProduct?designId=" . $$s7Data{"scene7_template_id"});
		$doc->dataElement("tns:unfriendlyUrl",  "https://www.actionenvelope.com/ae/control/designProduct?designId=" . $$s7Data{"scene7_template_id"});
		$doc->dataElement("tns:image",          "https://texel.envelopes.com/getBasicImage?id=" . $$s7Data{"scene7_template_id"} . "&fmt=png&wid=400");
		$doc->dataElement("tns:productid",      $$s7Data{"scene7_template_id"});
		$doc->dataElement("tns:size",           $features{"size"});
		$doc->dataElement("tns:metricSize",     $features{"metric_size"} || undef);
		$doc->dataElement("tns:sizeCode",       $features{"size_code"} || undef);
		$doc->dataElement("tns:productStyle",   $$s7Data{"product_desc"} || undef);
		$doc->dataElement("tns:industry",    	$s7cats{"industry"} || undef);
		$doc->dataElement("tns:theme",	        $s7cats{"theme"} || undef);
		$doc->dataElement("tns:use",	        $s7cats{"use"} || undef);
		#if($originalPrice > 0) {
		#	$doc->dataElement("tns:onSale",			"Y");
		#	$doc->dataElement("tns:originalPrice",	sprintf("%0.2f", $originalPrice));
		#	$doc->dataElement("tns:basePrice",      sprintf("%0.2f", $basePrice));
		#} else {
			$doc->dataElement("tns:basePrice",      sprintf("%0.2f", $basePrice));
		#}
		$doc->dataElement("tns:baseQuantity",   $$s7Data{"base_quantity"});
		$doc->dataElement("tns:salesRank",      "");
		$doc->dataElement("tns:manualRank",	    "");
		$doc->dataElement("tns:printable",      "");

		$doc->endTag();
		$designHash{$$s7Data{"scene7_template_id"}} = 1;
	}
}

$doc->endTag();
$doc->end();

$variants_unique_handle->finish();
$variants_handle->finish();
$features_handle->finish();
$member_handle->finish();
$getRating->finish();
$getRatingTypes->finish();
$getTagLine->finish();
$getScene7Data->finish();
$getScene7Category->finish();
$all_product_categories->finish();

$dbh->disconnect();

