#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use DBI;
use Ofbiz;
use XML::Writer;
use Data::Dumper;
use Time::Local;

use constant MAPPING_FILE => "/usr/local/ofbiz/etc/proxy_rewrite.txt";

my $ofbiz = Ofbiz->new(conf => "/usr/local/ofbiz/framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;

# retrieve variant list
my $getVariants = $dbh->prepare(qq{
	select * from product where is_virtual = 'N' and is_variant = 'Y'
});

#check if the product is the smallest quantity
my $smallestQuantity = $dbh->prepare(qq{
	SELECT
		p.product_id AS product_id,
		pp.quantity AS quantity
	FROM
		product p
			INNER JOIN
		product_price pp ON p.product_id = pp.product_id
	WHERE
        p.product_id = ?
		    AND pp.product_price_type_id = 'DEFAULT_PRICE'
			AND pp.colors = 0
	ORDER BY product_id ASC, quantity ASC
});

# retrieve ratings
my $getRating = $dbh->prepare(qq{
	select * from product_review where product_id = ? and status_id = 'PRR_APPROVED' and sales_channel_enum_id = 'ENV_SALES_CHANNEL'
});

# get average rating for a product
my $getAVGRating = $dbh->prepare(qq{
	select count(*), (ROUND(avg(product_rating)*2)/2), min(product_rating), max(product_rating) from product_review where product_id = ? and status_id = 'PRR_APPROVED' and sales_channel_enum_id = 'ENV_SALES_CHANNEL' group by product_id
});

die "Couldn't prepare queries; aborting" unless defined $getVariants && defined $getRating && defined $getAVGRating;

# retrieve envelope redirects
die "URL mappings file does not exist; aborting" unless -f MAPPING_FILE;

my %url_mapping;
open(my $fh, "<", MAPPING_FILE) or die "Couldn't open mappings file at " . MAPPING_FILE;

while (<$fh>) {
	chomp;
	chop;
	my ($key, $val)    = split(/\t/);
	$url_mapping{$val} = $key
	if (defined $key && defined $val);
}

close($fh) or die "Couldn't close mappings file at " . MAPPING_FILE;

# build google base xml
my $doc = new XML::Writer(DATA_MODE => 1, DATA_INDENT => 4);

$doc->xmlDecl("UTF-8");
$doc->startTag("feed", "xmlns:vc" => "http://www.w3.org/2007/XMLSchema-versioning", "xmlns:xsi" => "http://www.w3.org/2001/XMLSchema-instance", "xsi:noNamespaceSchemaLocation" => "http://www.google.com/shopping/reviews/schema/product/2.0/product_reviews.xsd");
	$doc->startTag("aggregator");
		$doc->dataElement("name", "Envelopes.com Reviews");
	$doc->endTag();
	$doc->startTag("publisher");
		$doc->dataElement("name", "Envelopes.com");
		$doc->dataElement("favicon", "http://www.envelopes.com/html/img/icon/favicon-envelopes.png");
	$doc->endTag();
	$doc->startTag("reviews");
	# product information

	$getVariants->execute();
	while(my $product = $getVariants->fetchrow_hashref()) {
		my $prodRating = 0;
		my $totalReviews = 0;
		my $minRating = 1;
		my $maxRating = 5;
		$getAVGRating->execute($$product{"PRODUCT_ID"});
		my($total, $rating, $min, $max) = $getAVGRating->fetchrow_array();
		if(defined $rating && $rating > 0) {
			$prodRating = $rating;
			$totalReviews = $total;
		}

		if($totalReviews > 0) {
			# generate the url parameters
			my $url_domain   = "http://www.envelopes.com";
			my $url_path     = sprintf("/envelopes/control/product/~category_id=%s/~product_id=%s", $$product{"PRIMARY_PRODUCT_CATEGORY_ID"}, $$product{"PRODUCT_ID"});
			my $url_seo_path = $url_mapping{$url_path} || $url_path;

			$smallestQuantity->execute($$product{"PRODUCT_ID"});
			my ($smallestBundleId, $smallestBundleQty) = $smallestQuantity->fetchrow_array();

			my $prodSKU = $smallestBundleId . "-" . $smallestBundleQty;

			if(defined $smallestBundleId && $smallestBundleId ne "") {
				$getRating->execute($$product{"PRODUCT_ID"});
				while(my $review = $getRating->fetchrow_hashref()) {
					my $createdStamp = $$review{"CREATED_STAMP"};
					my ($ayear,$amon,$amday,$ahour,$amin,$asec) = split(/[\s\-\:]+/, $createdStamp);
					$createdStamp = timelocal($asec,$amin,$ahour,$amday,$amon-1,$ayear);

					my $reviewerName = (defined $$review{"NICK_NAME"} && $$review{"NICK_NAME"} ne "") ? $$review{"NICK_NAME"} : "noname";
					$reviewerName =~ s/[^a-zA-Z ]//g;

					$doc->startTag("review");
						$doc->dataElement("review_id", $$review{"PRODUCT_REVIEW_ID"});
						$doc->startTag("reviewer");
							$doc->dataElement("name", (($reviewerName ne "" && $reviewerName ne " ") ? $reviewerName : "noname"), "is_anonymous" => (defined $$review{"POSTED_ANONYMOUS"} && $$review{"POSTED_ANONYMOUS"} eq "Y") ? "true" : "false");
							#$doc->dataElement("reviewer_id", (defined $$review{"EMAIL_ADDRESS"} && $$review{"EMAIL_ADDRESS"} ne "") ? $$review{"EMAIL_ADDRESS"} : "");
						$doc->endTag();
						$doc->dataElement("review_timestamp", $ayear . "-" . $amon . "-" . $amday . "T" . $ahour . ":" . $amin . ":" . $asec . "Z");
						#$doc->cdataElement("title", $$review{"PRODUCT_USE"});
						$doc->cdataElement("content", removeChars($$review{"PRODUCT_REVIEW"}));
						#$doc->startTag("pros"); $doc->endTag();
						#$doc->startTag("cons"); $doc->endTag();
						$doc->dataElement("review_url", sprintf("%s%s", $url_domain, $url_seo_path), "type" => "group");
						$doc->startTag("ratings");
							$doc->dataElement("overall", $$review{"PRODUCT_RATING"}, "min" => $minRating, "max" => $maxRating);
						$doc->endTag();
						$doc->startTag("products");
							$doc->startTag("product");
								$doc->startTag("product_ids");
									#$doc->startTag("gtins"); $doc->endTag();
									$doc->startTag("mpns");
										$doc->dataElement("mpn", $prodSKU);
									$doc->endTag();
									$doc->startTag("skus");
										$doc->dataElement("sku", $prodSKU);
									$doc->endTag();
									$doc->startTag("brands");
										$doc->dataElement("brand", "Envelopes.com");
									$doc->endTag();
								$doc->endTag();
								$doc->cdataElement("product_name", removeChars($$product{"INTERNAL_NAME"}));
								$doc->dataElement("product_url", sprintf("%s%s", $url_domain, $url_seo_path));
							$doc->endTag();
						$doc->endTag();
						$doc->dataElement("is_spam", "false");
						$doc->dataElement("collection_method", (defined $$review{"ORDER_ID"} && $$review{"ORDER_ID"} ne "") ? "post_fulfillment" : "unsolicited");
						if(defined $$review{"ORDER_ID"} && $$review{"ORDER_ID"} ne "") {
							$doc->dataElement("transaction_id", $$review{"ORDER_ID"})
						}
					$doc->endTag();
				}
			}
		}
	}
	$doc->endTag();
$doc->endTag();
$doc->end();

sub removeChars {
	my $str = $_[0];
	$str =~ s/[^[:ascii:]]//g; #remove trailing and leading spaces
	return $str;
}

$getVariants->finish();
$smallestQuantity->finish();
$getRating->finish();
$getAVGRating->finish();

$dbh->disconnect();
