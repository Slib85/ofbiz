#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;

use utf8;
use Encode;
use Data::Dumper;
use DBI;
use LWP::UserAgent;
use Ofbiz;
use POSIX;
use Search::Elasticsearch;
use JSON;
use Math::Combinatorics;
use DateTime;
use constant MAPPING_FILE_ENV => "/usr/local/ofbiz/etc/proxy_rewrite.txt";
use constant MAPPING_FILE_FOLD => "/usr/local/ofbiz/etc/folders-proxy_rewrite.txt";
use List::Util qw(min);
use List::Util qw(max);

my $ofbiz = Ofbiz->new(conf => "/usr/local/ofbiz/framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;

$dbh->do('set names utf8');

my $productNewTotalDays = 365;
my $productNewTotalMonths = 12;
my $baseRankAdjustment = .1;

my $parentColorCountHandle = $dbh->prepare(qq{
	SELECT count(*)
	FROM product
	WHERE parent_product_id = ?
		AND (
			SALES_DISCONTINUATION_DATE IS NULL 
			OR (SHOW_OUT_OF_STOCK_RECOMMENDATIONS = "Y" AND SALES_DISCONTINUATION_DATE IS NOT NULL)
		)
});

# retrieve details for fifty envelope bundles
my $variants_unique_handle = $dbh->prepare(qq{
	SELECT
	    p.product_id AS product_id
	FROM
	    product p
	WHERE
	    p.is_virtual = 'N'
	        AND p.is_variant = 'Y'
	        AND (
					(
						p.sales_discontinuation_date > NOW()
						OR p.sales_discontinuation_date IS NULL
					)
					OR 
					(
						SHOW_OUT_OF_STOCK_RECOMMENDATIONS = "Y" 
						AND SALES_DISCONTINUATION_DATE IS NOT NULL
					)
				)
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
	    p.product_depth AS depth,
	    p.product_height AS height,
	    p.product_width AS width,
	    p.product_weight AS weight,
	    p.primary_product_category_id AS category_id,
	    pc.category_name AS category_name,
	    p.has_color_opt AS has_color_opt,
	    p.on_sale AS on_sale,
	    p.on_clearance AS on_clearance,
		p.show_out_of_stock_recommendations,
	    pp.price AS price,
	    pp.original_price as original_price,
	    pc.primary_parent_category_id AS parent_category_id,
	    pp.quantity AS quantity,
	    pp.colors AS colors,
	    rc.rank AS sales_rank,
        rc.normalized_rank AS rank,
        p.is_printable as printable,
        case when p.created_stamp >= date_sub(now(), interval $productNewTotalMonths month) then "Y" else "N" end as new,
        case when pp.original_price is not null then ROUND((1-(pp.price/pp.original_price))*100) else 0 end as percent_savings,
		pa.asset_name as asset_name,
		pa.asset_default as asset_default,
		p.created_stamp as created_stamp,
		ps.views as views,
		ps.added_to_cart as added_to_cart,
		ps.purchased as purchased,
		ps.revenue as revenue,
		ps.conversion_rate as conversion_rate,
		ps.quantity_purchased as quantity_purchased,
		pws.ae as ae,
		pws.envelopes as envelopes,
		pws.folders as folders,
		pws.bags as bags
	FROM
	    product p
	        INNER JOIN
	    product_category pc ON p.primary_product_category_id = pc.product_category_id
	        LEFT JOIN
        product_price pp ON p.product_id = pp.product_id
			LEFT JOIN
		product_recommendation rc on p.product_id = rc.product_id
			LEFT JOIN
		product_assets pa on pa.product_id = p.product_id
			LEFT JOIN
		product_web_site pws on p.product_id = pws.product_id
		    LEFT JOIN
		product_statistic ps on p.product_id = ps.product_id
	WHERE
	    p.product_id = ?
	        AND p.product_type_id IS NOT NULL
	        AND (pp.product_price_type_id = 'DEFAULT_PRICE' OR pp.product_price_type_id IS NULL)
	        AND (pp.thru_date > NOW() OR pp.thru_date IS NULL)
	        AND (
					(
						p.sales_discontinuation_date > NOW() 
						OR p.sales_discontinuation_date IS NULL
					)
					OR 
					(
						SHOW_OUT_OF_STOCK_RECOMMENDATIONS = "Y" 
						AND SALES_DISCONTINUATION_DATE IS NOT NULL
					)
				)
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

# retrieve product features of type
my $features_type_handle = $dbh->prepare(qq{
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
	        AND
	    pf.product_feature_type_id = ?
});

# retrieve product sub features
my $sub_features_handle = $dbh->prepare(qq{
	SELECT
        pfa.product_id AS product_id,
        pf.product_feature_type_id AS product_feature_type_id,
        pf.product_feature_id AS product_feature_id,
        pf.description AS description,
        pf2.product_feature_type_id AS product_feature_type_id_to,
        pf2.product_feature_id AS product_feature_id_to,
        pf2.description AS description_to
    FROM
        product_feature_assoc pfa
            INNER JOIN
        product_feature pf ON pf.product_feature_id = pfa.product_feature_id
            INNER JOIN
        product_feature pf2 ON pf2.product_feature_id = pfa.product_feature_id_to
    WHERE
	    pfa.product_id = ? and pf2.product_feature_type_id = ?
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
	select count(*), (ROUND(avg(product_rating)*2)/2) from product_review where product_id = ? and status_id = 'PRR_APPROVED' and sales_channel_enum_id is not null
});

# get average rating for a product
my $getRatingTypes = $dbh->prepare(qq{
	select case when describe_Yourself is null then "Other" else describe_yourself end, count(*) from product_review where product_id = ? and status_id = 'PRR_APPROVED' and sales_channel_enum_id is not null group by describe_Yourself
});

# get average rating for a product
my $getTagLine = $dbh->prepare(qq{
	select prod.tag_line from product prod inner join product_assoc pa on prod.product_id = pa.product_id where pa.product_id_to = ?
});

#getting scene7 data
my $getScene7Data = $dbh->prepare(qq{
	select st.scene7_template_id, st.template_description, spa.product_id, st.base_quantity, st.base_price, st.product_desc from scene7_template st inner join scene7_prod_assoc spa on st.scene7_template_id = spa.scene7_template_id where spa.template_type_id = 'ADVANCE' and spa.template_assoc_type_id = 'TEMPLATE_FRONT' and st.print_price_description is not null
});

my $getScene7Category = $dbh->prepare(qq{
	select scene7_template_id, category_id, description from scene7_template_category where thru_date is null and scene7_template_id = ?
});

#get all other categories
my $all_product_categories = $dbh->prepare(qq{
	SELECT
		pc.primary_parent_category_id,
		pc.category_name,
		pc.product_category_id
	FROM
		product_category_member pcm
			INNER JOIN
		product_category pc ON pcm.product_category_id = pc.product_category_id
			INNER JOIN
		product_category pc2 ON pc.primary_parent_category_id = pc2.product_category_id
	WHERE
		pcm.product_id in (?,?)
			AND pcm.thru_date IS NULL
			AND pc2.primary_parent_category_id = 'ENVELOPES'
});

my $updateFacet = $dbh->prepare(qq{
    REPLACE INTO search_facet (facet_id, facet_type_id, facet_name, reference_id) VALUES (?, ?, ?, ?)
});

my $stopWords = $dbh->prepare(qq{
	select word as word from search_stop_word where enabled = 'Y'
});

die "Couldn't prepare queries; aborting" unless defined $variants_handle && defined $features_handle && defined $member_handle;

my $success = 1;

my %url_mapping_env;
open(my $fh_env, "<", MAPPING_FILE_ENV) or die "Couldn't open mappings file at " . MAPPING_FILE_ENV;
while (<$fh_env>) {
	chomp;
	chop;
	my ($key, $val)    = split(/\t/);
	$url_mapping_env{$val} = $key
		if (defined $key && defined $val);
}
close($fh_env) or die "Couldn't close mappings file at " . MAPPING_FILE_ENV;

my %url_mapping_fold;
open(my $fh_fold, "<", MAPPING_FILE_FOLD) or die "Couldn't open mappings file at " . MAPPING_FILE_FOLD;
while (<$fh_fold>) {
	chomp;
	chop;
	my ($key, $val)    = split(/\t/);
	$url_mapping_fold{$val} = $key
		if (defined $key && defined $val);
}
close($fh_fold) or die "Couldn't close mappings file at " . MAPPING_FILE_FOLD;

my $totalDimensions = 3;
my $totalLengthRequired = 0;
for (my $x = 0; $x < $totalDimensions; $x++) {
	$totalLengthRequired += getLengthRequired($x);
}

# returns feature hash
sub get_features {
	my $product_id = shift;
	my %features = ();

	$features_handle->execute($product_id);
	while(my $feature = $features_handle->fetchrow_hashref()) {
		$features{ lc $$feature{"product_feature_type_id"} } = $$feature{"description"};
	}
	return %features;
}

# returns features type array
sub get_features_of_type {
	my $product_id = $_[0];
	my $feature_type_id = $_[1];

	my @features = ();

	$features_type_handle->execute($product_id, $feature_type_id);
	while(my $feature = $features_type_handle->fetchrow_hashref()) {
		push @features, cleanFacetName($$feature{"description"});
	}
	return @features;
}

# returns sub features array
sub get_sub_features {
	my $product_id = $_[0];
	my $feature_type_id = $_[1];

	my @features = ();

	$sub_features_handle->execute($product_id, $feature_type_id);
	while(my $feature = $sub_features_handle->fetchrow_hashref()) {
		push @features, cleanFacetName($$feature{"description_to"});
	}
	return @features;
}

# returns name of the specialy shop this envelope belongs to
sub get_specialty_shop {
	my $product_id = shift;

	$member_handle->execute("PEEL_N_SEAL", $product_id);
	if ($member_handle->rows()) { return "Y"; }

	return "N";
}

# returns the tagline
sub getTagLine {
	my $product_id = shift;
	my $tagLine = "";
	$getTagLine->execute($product_id);
	my($tag) = $getTagLine->fetchrow_array();
	if(defined $tag) {
		$tagLine = $tag;
	}
	return $tagLine;
}

# returns category hash
sub get_scene7_cat {
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

sub getAutoComplete {
	my $name = shift;

	# Let's get the size code first, if it exists...
	my $sizeCode = $name;
	$sizeCode =~ s/^.*?([\d\s\/\.]+?x[\d\s\/\.]+).*?$/$1/;
	$sizeCode =~ s/^(?:\s+|)(.*?)(?:\s+|)$/$1/g;

	#Let's now remove the size code from name and parenthesis...
	$name =~ s/$sizeCode//g;
	$name =~ s/\(\)//g;

	#Let's clean up name having extra spaces for the split.
	$name =~ s/ +/ /g;

	#Now we can split name
	my @splitName = split(' ', $name);
	push @splitName, $sizeCode;

	#Remove hyphens
	for my $index (reverse 0..$#splitName) {
		if ( $splitName[$index] =~ /\-/ ) {
			splice(@splitName, $index, 1, ());
		}
	}

	my @combinations = combine(3, @splitName);
	for my $x (@combinations) {
		my $str = '';
		for my $i (@$x) {
			$str = $str eq '' ? $i : $str . ' ' . $i;
		}
		push @splitName, $str;
	}

	#print Dumper @splitName;
	#exit;
	return @splitName;
}

sub removeFractions {
	my $size = shift;
	if(defined $size) {
		$size =~ s/(?:\.\d+|\d+\/\d+)//g;
		$size =~ s/\s+/ /g;
		$size =~ s/^\s+|\s+$//g;

		if($size =~ /^x/ || $size =~ /x$/) {
			$size = undef;
		}
		if(defined $size && $size eq "") {
			$size = undef;
		}
	}
	return $size;
}

sub removeWhiteSpace {
	my $str = shift;
	if(defined $str) {
		$str =~ s/\s+//g;
	}

	return $str;
}

sub cleanFacetName {
	my $facetName = shift;
	if(defined $facetName) {
		$facetName =~ s/_/ /g;
		$facetName =~ s/[^a-zA-Z0-9#%\_\-\.\/"',\? ]//g;
	}
	return $facetName;
}

sub saveFacet {
	my ($facetId, $facetType, $relatedFacet, $referenceIds) = @_;

	#if no facetid was passed in or its empty, ignore it
	if(!defined $facetId || (defined $facetId && $facetId eq "")) {
		return;
	}

	if(ref($facetId) eq 'ARRAY') {
		my @facetArray = @{$facetId};
		my $idCounter = 0;
		foreach my $item (@facetArray) {
			my $facetName = cleanFacetName($item);
			$facetId = $item;
			$facetId =~ s/[^a-zA-Z0-9]//g;

			my $facetReferenceId = undef;
			if(defined $referenceIds) {
				my @facetReferenceArray = @{$referenceIds};
				$facetReferenceId = $facetReferenceArray[$idCounter];
			}
			$updateFacet->execute(lc $facetId, $facetType, $facetName, $facetReferenceId) or die "Couldn't save data: " . $dbh->errstr;

			$idCounter++;
		}
	} else {
		my $facetName = cleanFacetName($facetId);
		$facetId =~ s/[^a-zA-Z0-9]//g;
		if($facetType eq "cog2" && defined $relatedFacet) {
			$relatedFacet =~ s/[^a-zA-Z0-9]//g;
			$facetId = $relatedFacet . "_" . $facetId;
		}
		$updateFacet->execute(lc $facetId, $facetType, $facetName, undef) or die "Couldn't save data: " . $dbh->errstr;
	}
}

sub getWebSiteIds {
	my $product = shift;

	my @webSiteIds = ();
	if(defined $$product{"ae"} && $$product{"ae"} eq "Y") {
		push @webSiteIds, "ae";
	}
	if(defined $$product{"envelopes"} && $$product{"envelopes"} eq "Y") {
		push @webSiteIds, "envelopes";
	}
	if(defined $$product{"folders"} && $$product{"folders"} eq "Y") {
		push @webSiteIds, "folders";
	}
	if(defined $$product{"bags"} && $$product{"bags"} eq "Y") {
		push @webSiteIds, "bags";
	}

	return @webSiteIds;
}

sub getSizeRanking {
	my $sizeString = $_[0];
	chomp $sizeString;

	my @splitDimensions = split(/x/, $sizeString);
	my @wholeNumberList = ();

	foreach my $dimension (@splitDimensions) {
		$dimension =~ s/[^0-9\.\/\s]//g;
		my $decimal = 0;
		$dimension =~ /^(?:\s+|)(\d+(?:\.\d+|))/;
		my $wholeNumber = $1;
		if ($dimension =~ /(\d+)(?:|\s+)\/(?:|\s+)(\d+)/) {
			$decimal = $1 / $2;
		}

		push(@wholeNumberList, $wholeNumber + $decimal);
	}

	my $ranking = "";

	for (my $x = scalar @wholeNumberList - 1; $x >= 0; $x--) {
		my $multipliedValue = 1;

		for (my $y = 0; $y <= $x; $y++) {
			$multipliedValue *= $wholeNumberList[$y];
		}

		$multipliedValue = sprintf("%.2f", $multipliedValue);
		$multipliedValue =~ s/\.//g;
		$multipliedValue = formatRankingValue($multipliedValue, getLengthRequired($x));
		$ranking = $multipliedValue . $ranking;
	}

	if (length $ranking < $totalLengthRequired) {
		$ranking = formatRankingValue($ranking, $totalLengthRequired, 1);
	}

	return $ranking;
}

sub getLengthRequired {
	return (3 * ($_[0] + 1)) + 2;
}

sub formatRankingValue {
	my $formattedValue;

	if (defined $_[2] && $_[2]) {
		$formattedValue = sprintf ("%-$_[1]s", $_[0]);
	} else {
		$formattedValue = sprintf("%$_[1]d", $_[0]);
	}
	$formattedValue =~ tr/ /0/;
	return $formattedValue;
}

#es endpoint
my $url = 'https://elastic:jdJk6CImCSHPfFEkd7aNyRCt@ebb060d251ef4837b4a2fd15fb15192e.us-east-1.aws.found.io:9243/';

# elastic search init
my $datepostfix = strftime "%Y%m%d_%H%M%S", localtime;

my $testmode = 0;
my $alias = "bigname_search_alias";
my $indexName = "bigname_search_" . $datepostfix;
if(defined $ARGV[0] && $ARGV[0] eq "QA") {
	$alias = "bigname_search_qa_alias";
	$indexName = "bigname_search_qa_" . $datepostfix;
	print "QA MODE\n\n";
} elsif(defined $ARGV[0] && $ARGV[0] eq "TEST") {
	$testmode = 1;
	print "TEST MODE\n\n";
}

#####
#LOAD THE JSON MAPPING FILE
#####
my $mappingJSON = "";
my $mappingFile = '/usr/local/ofbiz/etc/mapping.json';
open(my $mappingHandle, '<:encoding(UTF-8)', $mappingFile) or die "Could not open file '$mappingFile' $!";
while (my $jsonRow = <$mappingHandle>) {
	chomp $jsonRow;
	$mappingJSON .= $jsonRow;
}
close $mappingHandle;

#####
#CREATE THE NEW INDEX
#####
my $ua;
my $uaResponse;
my $uaContent;
my $uaJSON;
my @indexesToRemove = ();

if($testmode == 0) {
	print "Creating index: " . $indexName . ".\n";
	$ua = LWP::UserAgent->new(
		ssl_opts => { SSL_ca_file => '/usr/local/ofbiz/etc/isrgrootx1.pem' },
	);
	$uaResponse = $ua->put($url . $indexName, 'Content-type' => 'application/json', Content => $mappingJSON);
	$uaContent = $uaResponse->content();
	print $uaContent;
	$uaJSON = decode_json($uaContent);

	#####
	#GET OLD INDEX ASSOCIATED TO ALIAS TO DELETE
	#####
	if ($uaJSON->{acknowledged}) {
		print "Getting list of old indexes to remove.\n";
		$uaResponse = $ua->get($url . "*/_alias/" . $alias);
		$uaContent = $uaResponse->content();
		$uaJSON = decode_json($uaContent);
		foreach my $indexNameToRemove (keys %$uaJSON) {
			push @indexesToRemove, $indexNameToRemove;
		}
	} else {
		print $uaContent;
		exit;
	}
}

my @documents = ();

#get stop words to ignore
my @stopWords = ();
$stopWords->execute();
while (my $word = $stopWords->fetchrow_hashref()) {
	push @stopWords, $$word{"word"};
}

sub removeStopWords {
	my $description = $_[0];

	if(defined $description) {
		foreach (@stopWords) {
			$description =~ s/$_//gi;
		}
	}

	return $description;
}

print "Preparing data.\n";
$variants_unique_handle->execute();
while (my $uniqueProd = $variants_unique_handle->fetchrow_hashref()) {
	$variants_handle->execute($$uniqueProd{"product_id"});
	while (my $product = $variants_handle->fetchrow_hashref()) {
		#print $$product{"product_id"} . "\n";
		my $qty = $$product{"quantity"};

		my $prodRating = 0;
		my $totalReviews = 0;

		$parentColorCountHandle->execute($$product{"parent_product_id"});

		my $parentColorCount = $parentColorCountHandle->fetchrow_array();

		$getRating->execute($$product{"product_id"});
		my($total, $rating) = $getRating->fetchrow_array();
		if(defined $rating && $rating > 0) {
			$prodRating = $rating;
			$totalReviews = $total;
		}

		my @reviewers = ();
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

			my %reviewersMap = map { $_ => 1 } @reviewers;
			if(!exists($reviewersMap{$reviewerType})) {
				push @reviewers, $reviewerType;
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
		$$product{"name"}            =~ s/[^!-~\s]//g if defined $$product{"name"};
		$$product{"description"}     =~ s/[^!-~\s]//g if defined $$product{"description"};
		$$product{"alt_description"} =~ s/[^!-~\s]//g if defined $$product{"alt_description"};

		# if needed, modify the product name
		if($$product{"product_type_id"} eq "ENVELOPE" && $$product{"category_id"} ne "PETALS" && $$product{"category_id"} ne "POCKETS") {
			if ($$product{"name"} !~ m/envelope/i) {
				# add envelopes to product name
				$$product{"name"} .= " Envelopes";
			}
		}

		# generate the url parameters
		my $url_path = sprintf("product/~category_id=%s/~product_id=%s", $$product{"category_id"}, $$product{"product_id"});
		#if(defined $url_mapping_env{'/envelopes/control' . $url_path}) {
		#	$url_path = $url_mapping_env{'/envelopes/control' . $url_path};
		#} elsif(defined $url_mapping_fold{'/folders/control' . $url_path}) {
		#	$url_path = $url_mapping_fold{'/folders/control' . $url_path};
		#}

		my $productName = $$product{"name"};

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

		my @productStyles = ();
		my @productStyleIds = ();
		my @productUses = ();
		my @productUseIds = ();

		push @productStyles, cleanFacetName(ucfirst lc $$product{"category_name"} || ucfirst lc $$product{"category_id"});
		push @productStyleIds, $$product{"category_id"};
		if(defined $$product{"parent_category_id"}) {
			push @productUses, cleanFacetName(ucfirst lc $$product{"parent_category_id"});
			push @productUseIds, $$product{"parent_category_id"};
		}

		$all_product_categories->execute($$product{"product_id"}, $$product{"parent_product_id"});
		while (my($catId, $catName, $idForCatName) = $all_product_categories->fetchrow_array()) {
			if(defined $catName) {
				my %productStylesMap = map { $_ => 1 } @productStyles;
				if(!exists($productStylesMap{(ucfirst lc cleanFacetName(ucfirst lc $catName))})) {
					push @productStyles, cleanFacetName(ucfirst lc $catName);
					push @productStyleIds, $idForCatName;
				}
			}

			if(defined $catId) {
				my %productUsesMap = map { $_ => 1 } @productUses;
				if(!exists($productUsesMap{(ucfirst lc cleanFacetName(ucfirst lc $catId))})) {
					push @productUses, cleanFacetName(ucfirst lc $catId);
					push @productUseIds, $catId;
				}
			}
		}

		# retrieve pocket type if folders
		my @pocketTypes = get_features_of_type($$product{"product_id"}, "POCKET_STYLE");

		# retrieve pocket type if folders
		my @printMethods = get_features_of_type($$product{"product_id"}, "IMPRINT_METHOD");

		# retrieve pocket type if folders
		my @numberOfPockets = get_sub_features($$product{"product_id"}, "NUMBER_OF_POCKETS");

		my $saleStr = undef;
		if(defined $$product{"on_sale"} && $$product{"on_sale"} eq "Y") {
			$saleStr = "Sale";
		}
		if (defined $$product{"on_clearance"} && $$product{"on_clearance"} eq "Y") {
			$saleStr = "Clearance";
		}

		my $depth = defined $$product{"depth"} ? sprintf("%g", $$product{"depth"}) : undef;
		my $height = defined $$product{"height"} ? sprintf("%g", $$product{"height"}) : undef;
		my $width = defined $$product{"width"} ? sprintf("%g", $$product{"width"}) : undef;

		my @sizeArray = ();
		if(defined $width && defined $height) {
			my $roundedWidth = int($width + 0.5);
			my $roundedHeight = int($height + 0.5);

			my $simpleSizeStr =  $roundedWidth . " x " . $roundedHeight . ((defined $depth) ? " x " . int($depth + 0.5) : "");
			my $simpleSizeStrR =  $roundedHeight . " x " . $roundedWidth . ((defined $depth) ? " x " . int($depth + 0.5) : "");
			if($simpleSizeStr eq "4 x 6") { push @sizeArray, $simpleSizeStr; }
			if($simpleSizeStrR eq "4 x 6") { push @sizeArray, $simpleSizeStrR; }
		}

		#create facet list
		#prodtype           productType
		#st                 productStyle
		#si                 size
		#cog1               colorGroup
		#cog2               color                   ################ THIS VALUE HAS TO BE PREFIXED WITH THE COG1 ID AND UNDERSCORE
		#sm                 sealingMethod
		#pw                 paperWeight
		#finish             paperTexture
		#col                collection
		#use                productUse
		#ra                 avgRating
		#printable          printable
		#pt                 pockettype
		#po                 printoption
		#nop                numofpockets
		#customizable       customizable

		saveFacet("Products", "prodtype", undef, undef);
		saveFacet(\@productStyles, "st", undef, \@productStyleIds);
		saveFacet(\@productUses, "use", undef, \@productUseIds);
		saveFacet($features{"size"} || undef, "si", undef, undef);
		saveFacet($features{"size_code"} || undef, "s", undef, undef);
		saveFacet($features{"color_group"} || undef, "cog1", undef);
		saveFacet($features{"color"} || undef, "cog2", $features{"color_group"}, undef);
		saveFacet($features{"sealing_method"} || undef, "sm", undef, undef);
		saveFacet($features{"paper_weight"} || undef, "pw", undef, undef);
		saveFacet($features{"paper_texture"} || undef, "finish", undef, undef);
		saveFacet($features{"collection"} || undef, "col", undef, undef);
		saveFacet($$product{"printable"} || undef, "customizable", undef, undef);
		saveFacet($$product{"printable"} || undef, "printable", undef, undef);
		saveFacet(\@pocketTypes, "pt", undef, undef);
		saveFacet(\@printMethods, "po", undef, undef);
		saveFacet(\@numberOfPockets, "nop", undef, undef);

		my @webSiteIds = getWebSiteIds($product);
		my @suggestions = getAutoComplete(Encode::decode('utf8', $productName . ' ' . ($features{"color"} || '')));
		#print Dumper \@suggestions;

		my $salesrank = $$product{"rank"} || 0.000001;
		
		#$productNewTotalDays
		if ($$product{"new"} eq "Y") {
			my $date1 = DateTime->now;
			my $date2;

			if ($$product{"created_stamp"} =~ m/^(\d{4})\-(\d{2})\-(\d{2})(?:\s|T)(\d{2}):(\d{2}):(\d{2})/i) {
				$date2 = DateTime->new(year => $1, month => $2, day => $3, hour => $4, minute => $5, second => $6, time_zone  => 'UTC');
			}

			my $rankAdjustment = $baseRankAdjustment * (($productNewTotalDays - ($date2->delta_days($date1)->delta_days() - 1)) / $productNewTotalDays);

			$salesrank = min(1, max(0.000001, $rankAdjustment + $salesrank));
		}

		push @documents, {index => {_index => $indexName, _type => 'product', _id => $$product{"product_id"}}};
		push @documents, {
			#"autocomplete"					=> { input  => \@suggestions, weight  => ($$product{"sales_rank"} || 1) },
			"autocomplete"					=> Encode::decode('utf8', $productName . ' ' . ($features{"color"} || '') . ' ' . (defined $features{"size"} ? removeWhiteSpace($features{"size"}) : '') . ' ' . (defined $features{"color_group"} ? removeWhiteSpace($features{"color_group"}) : '')) || undef,
			"availability"					=> $features{"availability"} || undef,
			"avgrating"						=> $prodRating,
			"baseprice"						=> formatCurrency($$product{"price"}),
			"basequantity"					=> $$product{"quantity"},
			"brand"							=> Encode::decode('utf8', $features{"brand"}) || undef,
			"collection"					=> cleanFacetName($features{"collection"}) || undef,
			"color"							=> cleanFacetName($features{"color"}),
			"colordescription"				=> removeStopWords($$product{"alt_description"}) || undef,
			"colorgroup"					=> cleanFacetName($features{"color_group"}) || undef,
			"conversionrate"				=> $$product{"conversion_rate"} || 0,
			"createdstamp"					=> $$product{"created_stamp"},
			"customizable"					=> cleanFacetName($$product{"printable"} || "N"),
			"description"					=> removeStopWords($$product{"description"}) || undef,
			"depth"							=> $depth || undef,
			"fsc"							=> $features{"fsc_certified"} || "N",
			"height"						=> $height || undef,
			"image"							=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/%s", (defined $$product{"asset_default"} && $$product{"asset_default"} eq "Y" ? $$product{"asset_name"} : $$product{"product_id"})),
			"ink"							=> $features{"inkjet"} || "N",
			"laser"							=> $features{"laser"} || "N",
			"manualrank"					=> 0,
			"measurement"       			=> getSizeRanking($features{"size"} || ""),
			"metricsize"					=> $features{"metric_size"} || undef,
			"name"							=> Encode::decode('utf8', $productName),
			"new"							=> $$product{"new"} || "N",
			"numofpockets"					=> \@numberOfPockets,
			"numofreviews"					=> $totalReviews,
			"onsale"						=> $saleStr,
			"originalprice"					=> (defined $$product{"original_price"}) ? formatCurrency($$product{"original_price"}) : "",
			"papergrade"					=> undef,
			"papertexture"					=> cleanFacetName($features{"paper_texture"}) || undef,
			"paperweight"					=> cleanFacetName($features{"paper_weight"}) || undef,
			"parentid"						=> $$product{"parent_product_id"} || undef,
			"peelandseal"					=> get_specialty_shop($$product{"product_id"}),
			"percentsavings"				=> $$product{"percent_savings"} || 0,
			"pocketspec"					=> $features{"pocket_spec"} || undef,
			"pockettype"					=> \@pocketTypes,
			"printable"						=> cleanFacetName($$product{"printable"} || "N"),
			"printoption"					=> \@printMethods,
			"productid"						=> $$product{"product_id"},
			"productstyle"					=> \@productStyles,
			"producttype"					=> cleanFacetName("Products"),
			"productuse"					=> \@productUses,
			"quantitypurchased"				=> $$product{"quantity_purchased"} || 0,
			"ratingsattribute"				=> \@reviewers,
			"recycled"						=> $features{"recycled"} || "N",
			"recycledcontent"				=> $features{"recycled_content"} || undef,
			"revenue"						=> $$product{"revenue"} || 0,
			"salesrank"						=> $salesrank,
			"sealingmethod"					=> cleanFacetName($features{"sealing_method"}) || undef,
			"sfi"							=> $features{"sfi_certified"} || "N",
			#"simplesize"					=> \@sizeArray,
			"size"							=> cleanFacetName($features{"size"}),
			"sizecode"						=> cleanFacetName($features{"size_code"}) || undef,
			"spinesize"						=> $features{"spine_size"} || undef,
			"tabsize"						=> $features{"tab_size"} || undef,
			"tagline"						=> $prodTagLine,
			"totalcolors"					=> $parentColorCount || undef,
			"type"							=> ucfirst lc $$product{"product_type_id"},
			"url"							=> $url_path,
			"views"							=> $$product{"views"} || 0,
			"websiteid"						=> \@webSiteIds,
			"width"							=> $width || undef,
			"showOutOfStockRecommendations" => $$product{"show_out_of_stock_recommendations"}
		};
	}
}

$getScene7Data->execute();
my %designHash = ();
while (my $s7Data = $getScene7Data->fetchrow_hashref()) {
	if(!exists($designHash{$$s7Data{"scene7_template_id"}})) {
		my %s7cats = get_scene7_cat($$s7Data{"scene7_template_id"});
		my %features = get_features($$s7Data{"product_id"});

		$variants_handle->execute($$s7Data{"product_id"});
		my $product = $variants_handle->fetchrow_hashref();

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

		# generate the url parameters
		my $url_path = sprintf("designProduct?designId=%s", $$s7Data{"scene7_template_id"});
		my @suggestions = getAutoComplete(Encode::decode('utf8', $templateName));

		push @documents, {index => {_index => $indexName, _type => 'product', _id => $$s7Data{"scene7_template_id"}}};
		push @documents, {
			"autocomplete"		=> \@suggestions,
			"availability"		=> undef,
			"avgrating"			=> 0,
			"baseprice"			=> formatCurrency($basePrice),
			"basequantity"		=> $$s7Data{"base_quantity"},
			"brand"				=> undef,
			"collection"		=> undef,
			"color"				=> undef,
			"colordescription"	=> undef,
			"colorgroup"		=> undef,
			"conversionrate"	=> 0,
			"createdstamp"		=> undef,
			"customizable"		=> cleanFacetName("Y"),
			"description"		=> undef,
			"depth"				=> undef,
			"fsc"				=> undef,
			"height"			=> undef,
			"image"				=> sprintf("https://texel.envelopes.com/getBasicImage?id=%s%s", $$s7Data{"scene7_template_id"}, "&fmt=png&wid=400"),
			"ink"				=> undef,
			"laser"				=> undef,
			"manualrank"		=> 0,
			"measurement"       => getSizeRanking($features{"size"} || ""),
			"metricsize"		=> $features{"metric_size"} || undef,
			"name"				=> Encode::decode('utf8', $templateName),
			"new"				=> undef,
			"numofpockets"		=> undef,
			"numofreviews"		=> undef,
			"onsale"			=> undef,
			"originalprice"		=> (defined $originalPrice) ? formatCurrency($originalPrice) : "",
			"papergrade"		=> undef,
			"papertexture"		=> undef,
			"paperweight"		=> undef,
			"parentid"			=> undef,
			"peelandseal"		=> undef,
			"percentsavings"	=> 25,
			"pocketspec"		=> undef,
			"pockettype"		=> undef,
			"printable"			=> cleanFacetName("Y"),
			"printoption"		=> undef,
			"productid"			=> $$s7Data{"scene7_template_id"},
			"productstyle"		=> undef,
			"producttype"		=> cleanFacetName("Designs"),
			"productuse"		=> undef,
			"quantitypurchased"	=> 0,
			"ratingsattribute"	=> undef,
			"recycled"			=> undef,
			"recycledcontent"	=> undef,
			"revenue"			=> 0,
			"salesrank"			=> 0.000001,
			"sealingmethod"		=> undef,
			"sfi"				=> undef,
			"size"				=> cleanFacetName($features{"size"}),
			"sizecode"			=> cleanFacetName($features{"size_code"}) || undef,
			"spinesize"			=> undef,
			"tabsize"			=> undef,
			"tagline"			=> undef,
			"totalcolors"		=> undef,
			"type"				=> ucfirst lc $$product{"product_type_id"},
			"url"				=> $url_path,
			"views"				=> 0,
			"websiteid"			=> "envelopes",
			"width"				=> undef
		};

		$designHash{$$s7Data{"scene7_template_id"}} = 1;
	}
}

#####################
########MANUAL#######

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'SF-101'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("531.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/SF-101?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folders - Standard Two Pocket",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "SF-101",
	"productstyle"			=> ["Standard Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=ST_PR_FOLDERS/~product_id=SF-101",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4\" Standard Pockets"
};
saveFacet("Standard Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'SF-101'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("531.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/SF-101?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folders - Standard Two Pocket",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "SF-101",
	"productstyle"			=> ["Standard Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=ST_PR_FOLDERS/~product_id=SF-101",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4\" Standard Pockets"
};
saveFacet("Standard Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'VS-104'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("531.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/VS-104?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folders - Curved Pockets",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "VS-104",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=VS-104",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4\" Curved Pockets"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'SF-102'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("531.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/SF-102?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folders - Standard Two Pocket w/ Front Cover Window",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "SF-102",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=SF-102",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4\" Standard Pockets",
	"spinesize"				=> "2\" x 4\" Front Cover Window"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'SF-111'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("531.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/SF-111?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folders - Standard One Pocket (Right)",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "SF-111",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=SF-111",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4\" Standard Right Pocket"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'WP-0880'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("610.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/WP-0880?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folders - Wavy Pockets",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "WP-0880",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=WP-0880",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 5/16\" Wavy Left Pocket",
	"spinesize"				=> "5\" Wavy Right Pocket"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'OR-144'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("531.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/OR-144?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folders - Standard Two Pocket w/ Front Cover Center Card Slits",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "OR-144",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=OR-144",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4\" Standard Pockets",
	"spinesize"				=> "Front Cover Center Card Slits"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'OR-145'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("531.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/OR-145?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folders - Standard Two Pocket w/ Front Cover Lower Right Card Slits",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "OR-145",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=OR-145",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4\" Standard Pockets",
	"spinesize"				=> "Front Cover Lower Right Card Slits"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => '3DF-113'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("531.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/3DF-113?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 1/2 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 1/2 x 12 Presentation Folders - 3/8\" Double Score Spine",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "3DF-113",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 1/2 x 12",
	"sizecode"				=> "9 1/2 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=3DF-113",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4\" Pockets",
	"spinesize"				=> "Double Score Spine"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => '3DF-152'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("673.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/3DF-152?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("10 x 11 3/4"),
	"metricsize"			=> undef,
	"name"					=> "File Tab Presentation Folder",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "3DF-152",
	"productstyle"			=> ["Oversized Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "10 x 11 3/4",
	"sizecode"				=> "10 x 11 3/4",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=OS_FOLDERS/~product_id=3DF-152",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/2\" Standard Pockets w/ 1/4\" Zippered Expansion",
	'tabsize'				=> "1\" Wide Full Length File Tab"
};
saveFacet("Oversized Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => '3DF-139'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("673.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/3DF-139?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("10 x 11 3/4"),
	"metricsize"			=> undef,
	"name"					=> "10 x 11 3/4 Presentation Folders - 1/4\" Expansion Pockets and 1 Reinforced Edges",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "3DF-139",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "10 x 11 3/4",
	"sizecode"				=> "10 x 11 3/4",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=3DF-139",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/2\" Pockets w/ 1/4 Zippered Expansion",
	"spinesize"				=> "Double Score Spine",
	"tabsize"				=> "1\" File Tab w/ Reinforced Edges"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => '3DF-149'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/3DF-149?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 1/2 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 1/2 x 12 Presentation Folders - 1/4\" Capacity Box Pockets and Double Score Spine",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "3DF-149",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 1/2 x 12",
	"sizecode"				=> "9 1/2 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=3DF-149",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4\" Pockets w/ 1/4\" Capacity",
	"spinesize"				=> "Double Score Spine"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'RE-2971'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("673.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/RE-2971?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 1/2 x 11 3/4"),
	"metricsize"			=> undef,
	"name"					=> "9 1/2 x 11 3/4 Presentation Folders - Reinforced Edges",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "RE-2971",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 1/2 x 11 3/4",
	"sizecode"				=> "9 1/2 x 11 3/4",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=RE-2971",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/2\" Standard Pockets",
	"tabsize"				=> "1\" File Tab w/ Reinforced Edges"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'MF-4801'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("359.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/MF-4801?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("4 x 9"),
	"metricsize"			=> undef,
	"name"					=> "Mini Folders - Standard Two Pocket",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "MF-4801",
	"productstyle"			=> ["Small Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "4 x 9",
	"sizecode"				=> "4 x 9",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SM_FOLDERS/~product_id=MF-4801",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "3\" Pockets"
};
saveFacet("Small Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'MF-135'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("359.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/MF-135?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("4 x 9"),
	"metricsize"			=> undef,
	"name"					=> "Mini Folders - Standard Two Pocket",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "MF-135",
	"productstyle"			=> ["Small Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "4 x 9",
	"sizecode"				=> "4 x 9",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SM_FOLDERS/~product_id=MF-135",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "3\" Pockets"
};
saveFacet("Small Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'CKH-2709'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/CKH-2709?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("3 3/8 x 6"),
	"metricsize"			=> undef,
	"name"					=> "Card Holder - Two Pocket (3 3/8 x 6)",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "CKH-2709",
	"productstyle"			=> ["Small Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "3 3/8 x 6",
	"sizecode"				=> "3 3/8 x 6",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SM_FOLDERS/~product_id=CKH-2709",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "2 1/2\" Pockets"
};
saveFacet("Small Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'CKH-2703'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/CKH-2703?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("3"),
	"metricsize"			=> undef,
	"name"					=> "Card Holder - Vertical Orientation w/ Bottom Pocket (5 1/8 x 3 3/8)",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "CKH-2703",
	"productstyle"			=> ["Small Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "3",
	"sizecode"				=> "3",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SM_FOLDERS/~product_id=CKH-2703",
	"websiteid"				=> ["folders"]
};
saveFacet("Small Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'HF-6801'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("423.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/HF-6801?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("6 x 9"),
	"metricsize"			=> undef,
	"name"					=> "Small Presentation Folders - Standard Two Pocket",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "HF-6801",
	"productstyle"			=> ["Small Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "6 x 9",
	"sizecode"				=> "6 x 9",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SM_FOLDERS/~product_id=HF-6801",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "3\" Standard Pockets"
};
saveFacet("Small Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'LF-118'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("706.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/LF-118?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 14 1/2"),
	"metricsize"			=> undef,
	"name"					=> "Legal Size Folder",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "LF-118",
	"productstyle"			=> ["Legal Size Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 14 1/2",
	"sizecode"				=> "9 x 14 1/2",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=LS_FOLDERS/~product_id=LF-118",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4\" Standard Pockets"
};
saveFacet("Legal Size Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'CHEP-185'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("395.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/CHEP-185?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 1/2 x 12"),
	"metricsize"			=> undef,
	"name"					=> "Economy Certificate Holder - Portrait Orientation (Vertical)",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "CHEP-185",
	"productstyle"			=> [""],
	"producttype"			=> "Products",
	"productuse"			=> [""],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 1/2 x 12",
	"sizecode"				=> "9 1/2 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "CERTIFICATE_HOLDER",
	"url"					=> "product/~category_id=CERT_HOLDERS/~product_id=CHEP-185",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "Portrait Orientation",
	"spinesize"				=> "Holds 8 1/2 x 11 Certificates"
};
saveFacet("", "st", undef, undef);
saveFacet("", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'PDCL-8.5x11'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/PDCL-8.5x11?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 11 1/2"),
	"metricsize"			=> undef,
	"name"					=> "Padded Diploma Cover - 8 1/2 x 11 Size w/ Landscape Orientation ",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "PDCL-8.5x11",
	"productstyle"			=> ["Padded Diploma Covers"],
	"producttype"			=> "Products",
	"productuse"			=> ["Certificate Holders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 11 1/2",
	"sizecode"				=> "9 x 11 1/2",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "CERTIFICATE_HOLDER",
	"url"					=> "product/~category_id=PAD_DIP_COVERS/~product_id=PDCL-8.5x11",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "Landscape Orientation",
	"spinesize"				=> "Holds 8 19/32 x 11 3/32\" Certificates"
};
saveFacet("Padded Diploma Covers", "st", undef, undef);
saveFacet("Certificate Holders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'VS-105'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("531.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/VS-105?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folders - Angled Pockets",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "VS-105",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9\" x 12\"",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=VS-105",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4\" Angled Pockets"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'SF-0882'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("610.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/SF-0882?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folders - 6 Tall Pockets",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "SF-0882",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=SF-0882",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "6\" Tall Pockets"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => '3DF-0802'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("531.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/3DF-0802?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 1/2 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 1/2 x 12 Presentation Folders - 1/2\" Double Score Spine",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "3DF-0802",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 1/2 x 12",
	"sizecode"				=> "9 1/2 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=3DF-0802",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/4\" Standard Pockets",
	"spinesize"				=> "Double Score Spine"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => '3DF-0803'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("531.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/3DF-0803?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 1/2 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 1/2 x 12 Presentation Folders - 1/2\" Triple Score Spine",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "3DF-0803",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 1/2 x 12",
	"sizecode"				=> "9 1/2 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=3DF-0803",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/4\" Standard Pockets",
	"spinesize"				=> "Triple Score Spine"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => '3DF-0811'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("531.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/3DF-0811?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 1/2 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 1/2 x 12 Presentation Folders - 1/4\" Double Score Spine",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "3DF-0811",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 1/2 x 12",
	"sizecode"				=> "9 1/2 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=3DF-0811",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/4\" Standard Pockets w/ 1/4\" Capacity",
	"spinesize"				=> "Double Score Spine"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => '3DF-141'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("673.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/3DF-141?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 1/2 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 1/2 x 12 Presentation Folders - 1/8\" Capacity Box Pockets and Double Score Spine ",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "3DF-141",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 1/2 x 12",
	"sizecode"				=> "9 1/2 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=3DF-141",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4\" Pockets w/ 1/8\" Capacity",
	"spinesize"				=> "Double Score Spine"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => '3DF-0853'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/3DF-0853?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folders - 1/4\" Capacity Box Right Pocket and Double Score Spine",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "3DF-0853",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=3DF-0853",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/4\" Flat Left Pocket & 4 1/4\" Right Pocket w/ 1/4\" Capacity",
	"spinesize"				=> "Double Score Spine"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'FT-130'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("673.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/FT-130?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 3/4 x 12"),
	"metricsize"			=> undef,
	"name"					=> "File Tab Presentation Folder",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "FT-130",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 3/4 x 12",
	"sizecode"				=> "9 3/4 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=FT-130",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4\" Standard Pockets",
	"tabsize"				=> "15/16\" Wide Full Length File Tab"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'RE-2972'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("673.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/RE-2972?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 1/2 x 11 3/4"),
	"metricsize"			=> undef,
	"name"					=> "9 1/2 x 11 3/4 Presentation Folders - Reinforced Edges w/ 1/2\" Spine",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "RE-2972",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 1/2 x 11 3/4",
	"sizecode"				=> "9 1/2 x 11 3/4",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=RE-2972",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/2\" Standard Pockets",
	"spinesize"				=> "1/2\" Double Score Spine",
	"tabsize"				=> "1\" File Tab w/ Reinforced Edges"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'RE-2911'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("673.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/RE-2911?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 1/2 x 11 3/4"),
	"metricsize"			=> undef,
	"name"					=> "9 1/2 x 11 3/4 Presentation Folders - Reinforced Edges w/ 1/4\" Scored Spine",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "RE-2911",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 1/2 x 11 3/4",
	"sizecode"				=> "9 1/2 x 11 3/4",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=RE-2911",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/2\" Standard Pockets",
	"spinesize"				=> "1/4\" Double Score Spine",
	"tabsize"				=> "1\" File Tab w/ Reinforced Edges"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'FT-0822'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("531.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/FT-0822?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 11 3/4"),
	"metricsize"			=> undef,
	"name"					=> "File Tab Presentation Folder",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "FT-0822",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 11 3/4",
	"sizecode"				=> "9 x 11 3/4",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=FT-0822",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/4\" Standard Pockets",
	"tabsize"				=> "5 1/2\" Length File Tab"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'VS-112'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("610.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/VS-112?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folder - One Small Pocket and One Vertical Pocket",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "VS-112",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=VS-112",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4\" Small Pocket",
	"spinesize"				=> "5 3/4\" Wide Vertical Pocket"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'SFTF-0855'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("610.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/SFTF-0855?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folders - Two Pocket w/ Tuck Tab Flap (Right)",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "SFTF-0855",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=SFTF-0855",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/4\"  Standard Pockets",
	"tabsize"				=> "3 1/2\" Right Tuck Tab Flap"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'SFTF-0856'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("610.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/SFTF-0856?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folders - Two Pocket w/ Tuck Tab Flap (Left)",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "SFTF-0856",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=SFTF-0856",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/4\"  Standard Pockets",
	"tabsize"				=> "3 1/2\" Left Tuck Tab Flap"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => '3DF-143'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("531.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/3DF-143?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folders - Three Pocket Tripanel w/ Capacity Pockets and Double Scored Spine",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "3DF-143",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=3DF-143",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4\" Capacity Pockets",
	"spinesize"				=> "Double Score Spine"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'TPF-3808'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("885.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/TPF-3808?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 1/2 x 12 1/2"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folders - Three Pocket Tripanel w/ Curved Pockets",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "TPF-3808",
	"productstyle"			=> ["Tri-Panel Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 1/2 x 12 1/2",
	"sizecode"				=> "9 1/2 x 12 1/2",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=TRI_PANEL_FOLDERS/~product_id=TPF-3808",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/2\" Curved Pockets"
};
saveFacet("Tri-Panel Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'TPF-3832'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("610.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/TPF-3832?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 1/2 x 12 1/2"),
	"metricsize"			=> undef,
	"name"					=> "9 1/2 x 12 1/2 Presentation Folders - Three Pocket Tripanel w/ Small Left Panel",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "TPF-3832",
	"productstyle"			=> ["Tri-Panel Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 1/2 x 12 1/2",
	"sizecode"				=> "9 1/2 x 12 1/2",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=TRI_PANEL_FOLDERS/~product_id=TPF-3832",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/4\" Pockets"
};
saveFacet("Tri-Panel Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'SF-0804'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("885.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/SF-0804?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folders - Standard One Pocket (Right) w/ Rounded Corner",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "SF-0804",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=SF-0804",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/4\" Standard Right Pocket"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'VSRP-0830'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("531.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/VSRP-0830?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folders - Serpentine Cut w/ One Vertical Pocket (Right)",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "VSRP-0830",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=VSRP-0830",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "7\" Vertical Right Pocket"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'SF-08-Flash'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("531.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/SF-08-Flash?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folders - One Pocket (Right) w/ Flash Drive Holder",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "SF-08-Flash",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=SF-08-Flash",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4\" Standard Right Pocket w/ Flash Drive Holder"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'STF-0816'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("531.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/STF-0816?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folders - One Pocket (Left) w/ Staple Tab Optional Window",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "STF-0816",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=STF-0816",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "3 1/2\" Standard Left Pocket",
	"spinesize"				=> "Optional Die Cut Window",
	"tabsize"				=> "Staple Tab"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'SPDAT-FD-04515'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/SPDAT-FD-04515?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 11 9/16"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folders - One Pocket (Left) w/ Document Attachment Tab & 1/4\" Double Scored Spine",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "SPDAT-FD-04515",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 11 9/16",
	"sizecode"				=> "9 x 11 9/16",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=SPDAT-FD-04515",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "3 15/16\" Standard Left Pocket",
	"spinesize"				=> "1/4\" Double Score Spine",
	"tabsize"				=> "Attachment Tab"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'SPDAT-FD-06521'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/SPDAT-FD-06521?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 11 9/16"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folders - One Pocket (Left) w/ Document Attachment Tab & 1\" Double Scored Spine",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "SPDAT-FD-06521",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 11 9/16",
	"sizecode"				=> "9 x 11 9/16",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=SPDAT-FD-06521",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "3 15/16\" Standard Left Pocket",
	"spinesize"				=> "1\" Double Score Spine",
	"tabsize"				=> "Attachment Tab"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'SPDAT-FD-06684'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/SPDAT-FD-06684?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 11 9/16"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folders - One Pocket (Left) w/ Document Attachment Tab & 1/2\" Double Scored Spine",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "SPDAT-FD-06684",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 11 9/16",
	"sizecode"				=> "9 x 11 9/16",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=SPDAT-FD-06684",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "3 15/16\" Standard Left Pocket",
	"spinesize"				=> "1/2\" Double Score Spine",
	"tabsize"				=> "Attachment Tab"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'V1-0852'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("610.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/V1-0852?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folders - Vertical Orientation w/ One Pocket (Bottom) & Tuck Tab",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "V1-0852",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=V1-0852",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "5 1/4\" Bottom Pocket",
	"tabsize"				=> "7 1/2\" Vertical Tuck Tab Flap"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'TPF-3803'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("885.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/TPF-3803?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Presentation Folders - Tripanel w/ One Pocket (Center)",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "TPF-3803",
	"productstyle"			=> ["Tri-Panel Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=TRI_PANEL_FOLDERS/~product_id=TPF-3803",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/4\" Unglued Center Rectangle Pocket"
};
saveFacet("Tri-Panel Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'CL-1465'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("885.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/CL-1465?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 1/2 x 14 1/2"),
	"metricsize"			=> undef,
	"name"					=> "Legal Size Conformer® Folder",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "CL-1465",
	"productstyle"			=> ["Legal Size Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 1/2 x 14 1/2",
	"sizecode"				=> "9 1/2 x 14 1/2",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=LS_FOLDERS/~product_id=CL-1465",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/2\" Standard Pockets"
};
saveFacet("Legal Size Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => '3DLF-168'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/3DLF-168?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("10 x 14 3/8"),
	"metricsize"			=> undef,
	"name"					=> "Legal Size Expansion Folder",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "3DLF-168",
	"productstyle"			=> ["Legal Size Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "10 x 14 3/8",
	"sizecode"				=> "10 x 14 3/8",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=LS_FOLDERS/~product_id=3DLF-168",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/2\" Standard Pockets w/ 1/4\" Zippered Expansion",
	"tabsize"				=> "1\" File Tab w/ Reinforced Edges",
	"spinesize"				=> "1/4\" Double Score Spine"
};
saveFacet("Legal Size Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'MFC-4865'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("423.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/MFC-4865?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("4 1/2 x 9 1/4"),
	"metricsize"			=> undef,
	"name"					=> "Mini CONFORMER® Folders - Standard Two Pocket",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "MFC-4865",
	"productstyle"			=> ["Small Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "4 1/2 x 9 1/4",
	"sizecode"				=> "4 1/2 x 9 1/4",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SM_FOLDERS/~product_id=MFC-4865",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "3 1/2 Standard Pockets"
};
saveFacet("Small Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'MF-4804'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("423.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/MF-4804?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("4 x 9"),
	"metricsize"			=> undef,
	"name"					=> "Mini Folders - One Pocket (Right)",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "MF-4804",
	"productstyle"			=> ["Small Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "4 x 9",
	"sizecode"				=> "4 x 9",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SM_FOLDERS/~product_id=MF-4804",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "3\" Right Pocket"
};
saveFacet("Small Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'HF-6851'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("423.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/HF-6851?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("6 x 9"),
	"metricsize"			=> undef,
	"name"					=> "Small Presentation Folders - Landscape Orientation & One Pocket (Bottom)",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "HF-6851",
	"productstyle"			=> ["Small Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "6 x 9",
	"sizecode"				=> "9 x 6",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SM_FOLDERS/~product_id=HF-6851",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "2 3/4\" Bottom Pocket"
};
saveFacet("Small Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'CHEL-186'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/CHEL-186?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 1/2 x 12 1/4"),
	"metricsize"			=> undef,
	"name"					=> "Certificate Holder  - Landscape Orientation w/ Mounting Board (Horizontal)",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "CHEL-186",
	"productstyle"			=> [""],
	"producttype"			=> "Products",
	"productuse"			=> [""],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 1/2 x 12 1/4",
	"sizecode"				=> "9 1/2 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "CERTIFICATE_HOLDER",
	"url"					=> "product/~category_id=CERT_HOLDERS/~product_id=CHEL-186",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "Landscape Orientation",
	"spinesize"				=> "Holds 8 1/2 x 11 Certificates"
};
saveFacet("", "st", undef, undef);
saveFacet("", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'CHEP-186'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/CHEP-186?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 1/2 x 12 1/4"),
	"metricsize"			=> undef,
	"name"					=> "Certificate Holder  - Portrait Orientation w/ Mounting Board (Vertical)",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "CHEP-186",
	"productstyle"			=> [""],
	"producttype"			=> "Products",
	"productuse"			=> [""],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 1/2 x 12 1/4",
	"sizecode"				=> "9 1/2 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "CERTIFICATE_HOLDER",
	"url"					=> "product/~category_id=CERT_HOLDERS/~product_id=CHEP-186",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "Portrait Orientation",
	"spinesize"				=> "Holds 8 1/2 x 11 Certificates"
};
saveFacet("", "st", undef, undef);
saveFacet("", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'CHEP-183'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/CHEP-183?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 3/8 x 12"),
	"metricsize"			=> undef,
	"name"					=> "Economy Certificate Holder  - Portrait Orientation (Vertical)",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "CHEP-183",
	"productstyle"			=> [""],
	"producttype"			=> "Products",
	"productuse"			=> [""],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 3/8 x 12",
	"sizecode"				=> "12 x 9",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "CERTIFICATE_HOLDER",
	"url"					=> "product/~category_id=CERT_HOLDERS/~product_id=CHEP-183",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "Portrait Orientation",
	"spinesize"				=> "Holds 8 1/2 x 11 Certificates"
};
saveFacet("", "st", undef, undef);
saveFacet("", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'CHEP-188'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("531.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/CHEP-188?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 1/2 x 12"),
	"metricsize"			=> undef,
	"name"					=> "Certificate Holder  - Portrait Orientation w/ One Pocket",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "CHEP-188",
	"productstyle"			=> [""],
	"producttype"			=> "Products",
	"productuse"			=> [""],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 1/2 x 12",
	"sizecode"				=> "9 1/2 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "CERTIFICATE_HOLDER",
	"url"					=> "product/~category_id=CERT_HOLDERS/~product_id=CHEP-188",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4\" Pocket",
	"spinesize"				=> "Portrait Orientation",
	"tabsize"				=> "Holds 8 1/2 x 11 Certificates"
};
saveFacet("", "st", undef, undef);
saveFacet("", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'CHEL-189'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("610.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/CHEL-189?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 3/8 x 12"),
	"metricsize"			=> undef,
	"name"					=> "Certificate Holder  - Landscape Orientation w/ One Pocket",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "CHEL-189",
	"productstyle"			=> [""],
	"producttype"			=> "Products",
	"productuse"			=> [""],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 3/8 x 12",
	"sizecode"				=> "12 x 9",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "CERTIFICATE_HOLDER",
	"url"					=> "product/~category_id=CERT_HOLDERS/~product_id=CHEL-189",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4\" Pocket",
	"spinesize"				=> "Holds 8 1/2 x 11 Certificates",
	"tabsize"				=> "Landscape Orientation"
};
saveFacet("", "st", undef, undef);
saveFacet("", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'CHERP-0911'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("395.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/CHERP-0911?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "Economy Certificate Holder  - Portrait Orientation w/ Rounded Corners",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "CHERP-0911",
	"productstyle"			=> [""],
	"producttype"			=> "Products",
	"productuse"			=> [""],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> undef,
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "CERTIFICATE_HOLDER",
	"url"					=> "product/~category_id=CERT_HOLDERS/~product_id=CHERP-0911",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "Portrait Orientation",
	"spinesize"				=> "Holds 8 1/2 x 11 Certificates"
};
saveFacet("", "st", undef, undef);
saveFacet("", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'CHERL-0911'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("395.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/CHERL-0911?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "Economy Certificate Holder  - Landscape Orientation w/ Rounded Corners",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "CHERL-0911",
	"productstyle"			=> [""],
	"producttype"			=> "Products",
	"productuse"			=> [""],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> undef,
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "CERTIFICATE_HOLDER",
	"url"					=> "product/~category_id=CERT_HOLDERS/~product_id=CHERL-0911",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "Landscape Orientation",
	"spinesize"				=> "Holds 8 1/2 x 11 Certificates"
};
saveFacet("", "st", undef, undef);
saveFacet("", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'CHCEP-0910'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/CHCEP-0910?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 1/2 x 12"),
	"metricsize"			=> undef,
	"name"					=> "Certificate Holder  - Portfolio Style w/ Portrait Orientation ",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "CHCEP-0910",
	"productstyle"			=> [""],
	"producttype"			=> "Products",
	"productuse"			=> [""],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 1/2 x 12",
	"sizecode"				=> undef,
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "CERTIFICATE_HOLDER",
	"url"					=> "product/~category_id=CERT_HOLDERS/~product_id=CHCEP-0910",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "Portrait Orientation",
	"spinesize"				=> "Holds 8 1/2 x 11 Certificates",
	"tabsize"				=> "Flap w/Notch Closure"
};
saveFacet("", "st", undef, undef);
saveFacet("", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'CHCEL-0910'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/CHCEL-0910?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 1/2 x 12"),
	"metricsize"			=> undef,
	"name"					=> "Certificate Holder  - Portfolio Style w/ Landscape Orientation ",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "CHCEL-0910",
	"productstyle"			=> [""],
	"producttype"			=> "Products",
	"productuse"			=> [""],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 1/2 x 12",
	"sizecode"				=> undef,
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "CERTIFICATE_HOLDER",
	"url"					=> "product/~category_id=CERT_HOLDERS/~product_id=CHCEL-0910",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "Landscape Orientation",
	"spinesize"				=> "Holds 8 1/2 x 11 Certificates",
	"tabsize"				=> "Flap w/Notch Closure"
};
saveFacet("", "st", undef, undef);
saveFacet("", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'PDCP-8.5x14'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/PDCP-8.5x14?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 14 1/2"),
	"metricsize"			=> undef,
	"name"					=> "Padded Diploma Cover - 8 1/2 x 14 Size w/ Portrait Orientation ",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "PDCP-8.5x14",
	"productstyle"			=> ["Padded Diploma Covers"],
	"producttype"			=> "Products",
	"productuse"			=> ["Certificate Holders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 14 1/2",
	"sizecode"				=> undef,
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "CERTIFICATE_HOLDER",
	"url"					=> "product/~category_id=PAD_DIP_COVERS/~product_id=PDCP-8.5x14",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "Portrait Orientation",
	"spinesize"				=> "Holds 8 19/32 x 14 3/32 Certificates"
};
saveFacet("Padded Diploma Covers", "st", undef, undef);
saveFacet("Certificate Holders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'PDCL-8.5x14'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/PDCL-8.5x14?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 14 1/2"),
	"metricsize"			=> undef,
	"name"					=> "Padded Diploma Cover - 8 1/2 x 14 Size w/ Landscape Orientation ",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "PDCL-8.5x14",
	"productstyle"			=> ["Padded Diploma Covers"],
	"producttype"			=> "Products",
	"productuse"			=> ["Certificate Holders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 14 1/2",
	"sizecode"				=> undef,
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "CERTIFICATE_HOLDER",
	"url"					=> "product/~category_id=PAD_DIP_COVERS/~product_id=PDCL-8.5x14",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "Landscape Orientation",
	"spinesize"				=> "Holds 8 19/32 x 14 3/32 Certificates"
};
saveFacet("Padded Diploma Covers", "st", undef, undef);
saveFacet("Certificate Holders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'PDCL-8x10'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/PDCL-8x10?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("8 1/2 x 10 1/2"),
	"metricsize"			=> undef,
	"name"					=> "Padded Diploma Cover - 8 x 10 Size w/ Landscape Orientation ",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "PDCL-8x10",
	"productstyle"			=> ["Padded Diploma Covers"],
	"producttype"			=> "Products",
	"productuse"			=> ["Certificate Holders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "8 1/2 x 10 1/2",
	"sizecode"				=> undef,
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "CERTIFICATE_HOLDER",
	"url"					=> "product/~category_id=PAD_DIP_COVERS/~product_id=PDCL-8x10",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "Landscape Orientation",
	"spinesize"				=> "Holds 8 3/32 x 10 3/32 Certificates"
};
saveFacet("Padded Diploma Covers", "st", undef, undef);
saveFacet("Certificate Holders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'RC-190'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("395.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/RC-190?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("8 9/16 x 11 1/16"),
	"metricsize"			=> undef,
	"name"					=> "Report Covers - Two Piece (8 9/16 x 11 1/16)",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "RC-190",
	"productstyle"			=> ["Report Covers"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "8 9/16 x 11 1/16",
	"sizecode"				=> "8",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=REPORT_COVERS/~product_id=RC-190",
	"websiteid"				=> ["folders"]
};
saveFacet("Report Covers", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'RCW-192'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("395.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/RCW-192?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("8 9/16 x 11 1/16"),
	"metricsize"			=> undef,
	"name"					=> "Report Covers - Two Piece w/ Window (8 9/16 x 11 1/16)",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "RCW-192",
	"productstyle"			=> ["Report Covers"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "8 9/16 x 11 1/16",
	"sizecode"				=> "8",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=REPORT_COVERS/~product_id=RCW-192",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "2\" x 4\" Die Cut Window"
};
saveFacet("Report Covers", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'RCW-7509'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("395.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/RCW-7509?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("8 3/4 x 11 1/4"),
	"metricsize"			=> undef,
	"name"					=> "Report Covers - Two Piece w/ Window (8 3/4 x 11 1/4)",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "RCW-7509",
	"productstyle"			=> ["Report Covers"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "8 3/4 x 11 1/4",
	"sizecode"				=> "8",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=REPORT_COVERS/~product_id=RCW-7509",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "2 x 4 Die Cut Window"
};
saveFacet("Report Covers", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'RC-7501'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("395.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/RC-7501?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("8 3/4 x 11 1/4"),
	"metricsize"			=> undef,
	"name"					=> "Report Covers - Two Piece (8 3/4x 11 1/4)",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "RC-7501",
	"productstyle"			=> ["Report Covers"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "8 3/4 x 11 1/4",
	"sizecode"				=> "8",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=REPORT_COVERS/~product_id=RC-7501",
	"websiteid"				=> ["folders"]
};
saveFacet("Report Covers", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'RC-0901'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("395.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/RC-0901?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "Report Covers - One Piece w/ Right Rounded Corners (9 x 12)",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "RC-0901",
	"productstyle"			=> ["Report Covers"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "8",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=REPORT_COVERS/~product_id=RC-0901",
	"websiteid"				=> ["folders"]
};
saveFacet("Report Covers", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'RCFT-0922'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("395.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/RCFT-0922?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 11 3/4"),
	"metricsize"			=> undef,
	"name"					=> "Report Covers - One Piece w/ Top Tab (9 x 11 3/4)",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "RCFT-0922",
	"productstyle"			=> ["Report Covers"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 11 3/4",
	"sizecode"				=> undef,
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=REPORT_COVERS/~product_id=RCFT-0922",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "5\" Die Cut File Tab"
};
saveFacet("Report Covers", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'EF-155'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("395.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/EF-155?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("8 9/16 x 12 1/4"),
	"metricsize"			=> undef,
	"name"					=> "Expansion Portfolio",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "EF-155",
	"productstyle"			=> ["Portfolios"],
	"producttype"			=> "Products",
	"productuse"			=> ["Paper and more"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "8 9/16 x 12 1/4",
	"sizecode"				=> "8 9/16 x 12 1/4",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "PORTFOLIO",
	"url"					=> "product/~category_id=PORTFOLIOS/~product_id=EF-155",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "1\" Expansion",
	"tabsize"				=> "6 1/16 Pocket",
	"spinesize"				=> "Open Bottom Corners"
};
saveFacet("Portfolios", "st", undef, undef);
saveFacet("Paper and more", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'EF-FD-07564'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/EF-FD-07564?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "Expansion Portfolio",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "EF-FD-07564",
	"productstyle"			=> ["Portfolios"],
	"producttype"			=> "Products",
	"productuse"			=> ["Paper and more"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "PORTFOLIO",
	"url"					=> "product/~category_id=PORTFOLIOS/~product_id=EF-FD-07564",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "1 1/2\" Expansion",
	"tabsize"				=> "8 3/4\" Pocket",
	"spinesize"				=> "Closed Bottom Corners"
};
saveFacet("Portfolios", "st", undef, undef);
saveFacet("Paper and more", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'F-1602'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/F-1602?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("10 x 13"),
	"metricsize"			=> undef,
	"name"					=> "Deluxe Portfolio",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "F-1602",
	"productstyle"			=> ["Portfolios"],
	"producttype"			=> "Products",
	"productuse"			=> ["Paper and more"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "10 x 13",
	"sizecode"				=> "10 x 13",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "PORTFOLIO",
	"url"					=> "product/~category_id=PORTFOLIOS/~product_id=F-1602",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "8 5/8\" Standard Pocket"
};
saveFacet("Portfolios", "st", undef, undef);
saveFacet("Paper and more", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'EF-3512'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("876.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/EF-3512?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "Expansion Portfolio",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "EF-3512",
	"productstyle"			=> ["Portfolios"],
	"producttype"			=> "Products",
	"productuse"			=> ["Paper and more"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "PORTFOLIO",
	"url"					=> "product/~category_id=PORTFOLIOS/~product_id=EF-3512",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "1 1/2\" Expansion",
	"tabsize"				=> "8 3/4\" Pocket",
	"spinesize"				=> "Open Bottom Corners"
};
saveFacet("Portfolios", "st", undef, undef);
saveFacet("Paper and more", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'EFL-3511'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("876.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/EFL-3511?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("10 x 15"),
	"metricsize"			=> undef,
	"name"					=> "Legal Size Expansion Portfolio",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "EFL-3511",
	"productstyle"			=> ["Portfolios"],
	"producttype"			=> "Products",
	"productuse"			=> ["Paper and more"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "10 x 15",
	"sizecode"				=> "10 x 15",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "PORTFOLIO",
	"url"					=> "product/~category_id=PORTFOLIOS/~product_id=EFL-3511",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "1 1/2\" Expansion",
	"tabsize"				=> "9 3/8\" Pocket",
	"spinesize"				=> "Open Bottom Corners"
};
saveFacet("Portfolios", "st", undef, undef);
saveFacet("Paper and more", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'EFL-3501'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/EFL-3501?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("10 x 15"),
	"metricsize"			=> undef,
	"name"					=> "Legal Size Expansion Portfolio",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "EFL-3501",
	"productstyle"			=> ["Portfolios"],
	"producttype"			=> "Products",
	"productuse"			=> ["Paper and more"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "10 x 15",
	"sizecode"				=> "10 x 15",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "PORTFOLIO",
	"url"					=> "product/~category_id=PORTFOLIOS/~product_id=EFL-3501",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "1 1/2\" Expansion",
	"tabsize"				=> "9 3/8\" Pocket",
	"spinesize"				=> "Closed Bottom Corners"
};
saveFacet("Portfolios", "st", undef, undef);
saveFacet("Paper and more", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'EFL-165'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("876.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/EFL-165?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("10 x 15"),
	"metricsize"			=> undef,
	"name"					=> "Legal Size Expansion Portfolio",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "EFL-165",
	"productstyle"			=> ["Portfolios"],
	"producttype"			=> "Products",
	"productuse"			=> ["Paper and more"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "10 x 15",
	"sizecode"				=> "10 x 15",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "PORTFOLIO",
	"url"					=> "product/~category_id=PORTFOLIOS/~product_id=EFL-165",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "1\" Expansion",
	"tabsize"				=> "6 1/16\" Pocket",
	"spinesize"				=> "Open Bottom Corners"
};
saveFacet("Portfolios", "st", undef, undef);
saveFacet("Paper and more", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'AFF-0924-002'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/AFF-0924-002?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 1/2 x 11 3/4"),
	"metricsize"			=> undef,
	"name"					=> "File Tab Standard Folder",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "AFF-0924-002",
	"productstyle"			=> ["File Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 1/2 x 11 3/4",
	"sizecode"				=> undef,
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=FILE_FOLDERS/~product_id=AFF-0924-002",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "Two 1/4\" Bottom Front Expansion Scores",
	"tabsize"				=> "3 3/4\" Wide Center Tab"
};
saveFacet("File Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'CKH-2711'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("934.95"),
	"basequantity"			=> "1000",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/CKH-2711?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("3 3/8 x 6"),
	"metricsize"			=> undef,
	"name"					=> "Card Holder - Right Pocket (3 3/8 x 6)",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "CKH-2711",
	"productstyle"			=> ["Small Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "3 3/8 x 6",
	"sizecode"				=> "3",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SM_FOLDERS/~product_id=CKH-2711",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "2 1/2\" Right Pocket"
};
saveFacet("Small Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'CKH-2710'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/CKH-2710?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("3 3/8 x 6"),
	"metricsize"			=> undef,
	"name"					=> "Card Holder - Left Pocket(3 3/8 x 6)",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "CKH-2710",
	"productstyle"			=> ["Small Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "3 3/8 x 6",
	"sizecode"				=> "3",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SM_FOLDERS/~product_id=CKH-2710",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "2 1/2\" Left Pocket"
};
saveFacet("Small Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'CKH-2704'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/CKH-2704?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("2 3/4 x 3 3/4"),
	"metricsize"			=> undef,
	"name"					=> "Card Holder - Horizontal Orientation w/ Side Pocket (2 3/4 x 3 3/4)",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "CKH-2704",
	"productstyle"			=> ["Small Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "2 3/4 x 3 3/4",
	"sizecode"				=> "2 3/4 x 3 3/4",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SM_FOLDERS/~product_id=CKH-2704",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "2 1/4\" Side Pocket"
};
saveFacet("Small Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'PHP-0303'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/PHP-0303?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("4 3/8 x 6 1/4"),
	"metricsize"			=> undef,
	"name"					=> "Photo Holder - 4 x 6 Portrait Orientation",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "PHP-0303",
	"productstyle"			=> ["Small Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "4 3/8 x 6 1/4",
	"sizecode"				=> "4 3/8 x 6 1/4",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SM_FOLDERS/~product_id=PHP-0303",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "3 1/4 x 5 1/4 Photo Window"
};
saveFacet("Small Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'SF-0810'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> formatCurrency("706.95"),
	"basequantity"			=> "250",
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/SF-0810?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "Standard 2 Pocket with 3\" Pockets",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "SF-0810",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=SF-0810",
	"websiteid"				=> ["folders"]
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'OF-0824'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/OF-0824?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 1/2 x 12 1/2"),
	"metricsize"			=> undef,
	"name"					=> "Oversized 2 Pocket",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "OF-0824",
	"productstyle"			=> ["Oversized Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 1/2 x 12 1/2",
	"sizecode"				=> "9 1/2 x 12 1/2",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=OS_FOLDERS/~product_id=OF-0824",
	"websiteid"				=> ["folders"]
};
saveFacet("Oversized Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'RP-0863'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/RP-0863?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "Curved 2 Pockets",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "RP-0863",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=RP-0863",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/4\" Curved Pockets"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => '3DFE-2886'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/3DFE-2886?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9.875 x 11 3/4"),
	"metricsize"			=> undef,
	"name"					=> "Extra Capacity Folder - Flat Expansion Pocket",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "3DFE-2886",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9.875 x 11 3/4",
	"sizecode"				=> "9.875 x 11 3/4",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=3DFE-2886",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/2\" Flat Left Pocket & 4/12\" Right Pocket with 1/4\" Zippered Expansion ",
	"tabsize"				=> "1\" File Tab w/ Reinforced Edges",
	"spinesize"				=> "Double Score Spine"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'RE-2971-TOP'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/RE-2971-TOP?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 1/2 x 11 3/4"),
	"metricsize"			=> undef,
	"name"					=> "2 Pockets Reinforced Side and Top Edges",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "RE-2971-TOP",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 1/2 x 11 3/4",
	"sizecode"				=> "9 1/2 x 11 3/4",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=RE-2971-TOP",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/2\" Standard Pockets",
	"spinesize"				=> "1\" Top and Side File Tab w/ Reinforced Edges"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'FTRE-2976'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/FTRE-2976?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 3/4 x 11 3/4"),
	"metricsize"			=> undef,
	"name"					=> "Reinforced Folder - Two Pocket",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "FTRE-2976",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 3/4 x 11 3/4",
	"sizecode"				=> "9 3/4 x 11 3/4",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=FTRE-2976",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/2\" Standard Pockets",
	"tabsize"				=> "1\" File Tab w/ Reinforced Edges",
	"spinesize"				=> "1/2\" Double Score Spine"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'FTRE-2977'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/FTRE-2977?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 3/4 x 11 3/4"),
	"metricsize"			=> undef,
	"name"					=> "Reinforced Folder - One Pocket",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "FTRE-2977",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 3/4 x 11 3/4",
	"sizecode"				=> "9 3/4 x 11 3/4",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=FTRE-2977",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/2\" Standard Left Pocket",
	"tabsize"				=> "1\" File Tab w/ Reinforced Edges"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => '3DF-0812'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/3DF-0812?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "Standard 2 Pocket with 1/4\" Capacity Box Pockets & 1/2\" Double Score Spine",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "3DF-0812",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=3DF-0812",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/4\" Standard Pockets w/ 1/4\" Capacity ",
	"spinesize"				=> "Double Score Spine"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => '3DE-2872'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/3DE-2872?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9.875 x 11 3/4"),
	"metricsize"			=> undef,
	"name"					=> "2 Pocket with 1/4\" Expansion Pockets & 1\" Reinforced Edges",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "3DE-2872",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9.875 x 11 3/4",
	"sizecode"				=> "9.875 x 11 3/4",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=3DE-2872",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4 1/4\" Pockets w/ 1/4\" Zippered Expansion",
	"spinesize"				=> "Double Score Spine",
	"tabsize"				=> "1\" File Tab w/ Reinforced Edges"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'TPF-3851'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/TPF-3851?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("12 x 9"),
	"metricsize"			=> undef,
	"name"					=> "1 Pocket Landscape tripanel",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "TPF-3851",
	"productstyle"			=> ["Tri-Panel Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "12 x 9",
	"sizecode"				=> "12 x 9",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=TRI_PANEL_FOLDERS/~product_id=TPF-3851",
	"websiteid"				=> ["folders"]
};
saveFacet("Tri-Panel Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'TP-109'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/TP-109?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "3 Pockets Tripanel",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "TP-109",
	"productstyle"			=> ["Tri-Panel Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=TRI_PANEL_FOLDERS/~product_id=TP-109",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4\" Pockets"
};
saveFacet("Tri-Panel Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'HF-6802'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/HF-6802?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("5 3/4 x 8 3/4"),
	"metricsize"			=> undef,
	"name"					=> "2 Pockets Half Size",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "HF-6802",
	"productstyle"			=> ["Small Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "5 3/4 x 8 3/4",
	"sizecode"				=> "5 3/4 x 8 3/4",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SM_FOLDERS/~product_id=HF-6802",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "3\" Standard Pockets"
};
saveFacet("Small Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => 'EF-3502'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/EF-3502?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "Expansion Portfolio",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "EF-3502",
	"productstyle"			=> ["Portfolios"],
	"producttype"			=> "Products",
	"productuse"			=> ["Paper and more"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> "9 x 12",
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "PORTFOLIO",
	"url"					=> "product/~category_id=PORTFOLIOS/~product_id=EF-3502",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "1 1/2\" Expansion",
	"tabsize"				=> "8 3/4\" Pocket",
	"spinesize"				=> "Closed Bottom Corners"
};
saveFacet("Portfolios", "st", undef, undef);
saveFacet("Paper and more", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => '08-96'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/08-96?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Quick Ship - Presentation Folder - Full Color Printing 24 Hr",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "08-96",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> undef,
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=08-96",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4\" Standard Pockets",
	"spinesize"				=> "24 Hour Turnaround"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

push @documents, {index => {_index => $indexName, _type => 'product', _id => '08-96-FOIL'}};
push @documents, {
	"availability"			=> undef,
	"avgrating"				=> 0,
	"baseprice"				=> undef,
	"basequantity"			=> undef,
	"brand"					=> undef,
	"collection"			=> undef,
	"color"					=> undef,
	"colorgroup"			=> undef,
	"createdstamp"			=> undef,
	"customizable"			=> "Y",
	"fsc"					=> undef,
	"image"					=> sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/08-96-FOIL?wid=700&hei=440&fmt=png-alpha"),
	"ink"					=> undef,
	"laser"					=> undef,
	"manualrank"			=> 0,
	"measurement"			=> getSizeRanking("9 x 12"),
	"metricsize"			=> undef,
	"name"					=> "9 x 12 Quick Ship - Presentation Folder - Foil Stamping 3 Day",
	"new"					=> "N",
	"numofpockets"			=> [],
	"numofreviews"			=> 0,
	"onsale"				=> "",
	"originalprice"			=> undef,
	"papergrade"			=> undef,
	"papertexture"			=> undef,
	"paperweight"			=> undef,
	"parentid"				=> undef,
	"peelandseal"			=> "N",
	"percentsavings"		=> 0,
	"pockettype"			=> [],
	"printable"				=> "Y",
	"printoption"			=> [],
	"productid"				=> "08-96-FOIL",
	"productstyle"			=> ["Specialty 9 x 12 Presentation Folders"],
	"producttype"			=> "Products",
	"productuse"			=> ["Folders"],
	"ratingsattribute"		=> [],
	"recycled"				=> undef,
	"recycledcontent"		=> undef,
	"salesrank"				=> 0,
	"sealingmethod"			=> undef,
	"sfi"					=> undef,
	"size"					=> "9 x 12",
	"sizecode"				=> undef,
	"tagline"				=> undef,
	"totalcolors"			=> 64,
	"type"					=> "FOLDER",
	"url"					=> "product/~category_id=SP_912_PR_FOLDERS/~product_id=08-96-FOIL",
	"websiteid"				=> ["folders"],
	"pocketspec"			=> "4\" Standard Pockets",
	"spinesize"				=> "3 Day Turnaround"
};
saveFacet("Specialty 9 x 12 Presentation Folders", "st", undef, undef);
saveFacet("Folders", "use", undef, undef);

#my $searchIndexFile = '/tmp/elastic_search_index.txt';
#open(my $searchIndexHandle, '>', $searchIndexFile) or die "Could not open file '$searchIndexFile' $!";
#print $searchIndexHandle Dumper @documents;
#close $searchIndexHandle;
open FILE, ">", "/tmp/elasticdata.json" or die "Couldn't open file: $!";
print FILE encode_json(\@documents) . "\n";
close FILE;

if($testmode == 0) {
	#bulk import to that index
	my $es = Search::Elasticsearch->new(nodes => $url);
	my $esResponse = $es->bulk(
		index => $indexName, # required
		type  => 'product',  # required
		body  => \@documents
	);

	if ($esResponse->{errors}) {
		print "Error trying to add data to index.\n";
		#delete the new index that was just made because the script failed
		$uaResponse = $ua->delete($url . $indexName);
		$uaContent = $uaResponse->content();

		$uaJSON = decode_json($uaContent);
		if ($uaJSON->{acknowledged}) {
			print "Inserting to index failed. Deleted " . $indexName . "\n";
		} else {
			#print $uaContent;
			exit;
		}
		die "Bulk index had issues.";
	} else {
		print "Successfully created the new index.\n";
	}

	####
	#REMOVE OLD INDEXES FROM ALIAS AND ADD NEW ONE
	####
	print "Removing old indexes and adding new index to alias: " . $alias . ".\n";
	my %removeAndAddAlias = ();
	$removeAndAddAlias{"actions"} = [];
	push( @{$removeAndAddAlias{"actions"}}, { "add", { "index", $indexName, "alias", $alias } } );
	foreach (@indexesToRemove) {
		push( @{$removeAndAddAlias{"actions"}}, { "remove", { "index", $_, "alias", $alias } } );
	}

	my $aliasJSON = to_json(\%removeAndAddAlias);
	#print $aliasJSON . "\n";
	$uaResponse = $ua->post($url . "_aliases", 'Content-type' => 'application/json', Content => $aliasJSON);
	$uaContent = $uaResponse->content();

	####
	#DELETE OLD INDEXES
	####
	print "Preparing to remove old indexes.\n";
	foreach my $indexNameToRemove (keys %$uaJSON) {
		$uaResponse = $ua->delete($url . $indexNameToRemove);
		$uaContent = $uaResponse->content();

		$uaJSON = decode_json($uaContent);
		if ($uaJSON->{acknowledged}) {
			print "Deleted " . $indexNameToRemove . "\n";
		}
		else {
			print $uaContent;
			exit;
		}
	}
}

$parentColorCountHandle->finish();
$variants_unique_handle->finish();
$variants_handle->finish();
$sub_features_handle->finish();
$features_type_handle->finish();
$features_handle->finish();
$member_handle->finish();
$getRating->finish();
$getRatingTypes->finish();
$getTagLine->finish();
$getScene7Data->finish();
$getScene7Category->finish();
$all_product_categories->finish();
$updateFacet->finish();
$stopWords->finish();

# end transaction
my $result = ($success ? $dbh->commit : $dbh->rollback);

unless($result) {
	die "Couldn't finish transaction: " . $dbh->errstr;
}

$dbh->disconnect();

sub formatCurrency {
	my $price = $_[0];

	if (defined $price) {
		return sprintf("%0.2f", floor($price * 100 + 0.5) / 100);
	} else {
		return "0.00";
	}
}