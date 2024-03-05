#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use DBI;
use Ofbiz;
use Data::Dumper;
use LWP::UserAgent;
use HTTP::Headers;
use HTTP::Request;
use HTTP::Cookies;
use HTTP::Response;
use HTML::TagParser;
use JSON;
use Net::OAuth::Client;
use URL::Encode 'url_encode';
use Math::Random::MT 'rand';

my $ofbiz = Ofbiz->new(conf => "/usr/local/ofbiz/framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;

# retrieve virtual/variants products
my $products_handle = $dbh->prepare(qq{
	SELECT      p.product_id as product_id,
	            p.product_name as product_name,
	            p.long_description as long_description,
	            p.primary_product_category_id as primary_product_category_id,
	            pp.quantity as quantity,
	            pp.price as price,
	            p.weight as weight,
	            p.product_width as width,
	            p.product_height as height,
	            p.product_depth as depth
	FROM        product p inner join product_price pp on p.product_id = pp.product_id
	WHERE
	    p.is_virtual = 'N'
	        AND p.is_variant = 'Y'
	        AND (p.sales_discontinuation_date > NOW() OR p.sales_discontinuation_date IS NULL)
	        AND pp.colors = 0
	        AND pp.quantity <= 1000
});

my $category_handle = $dbh->prepare(qq{
	SELECT      category_name
	FROM        product_category
	WHERE       product_category_id = ?
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

die "Couldn't prepare queries; aborting" unless defined $products_handle;

my %descAndProdList = ();
my %prodAndCatList = ();
my %quantities = ();
my %itemName = ();

my $header = "Category\tProduct Group (Family) Name\tMarketing Copy\tSupplier ID";
my $header2 = "itemid\tsku\tname\tquantity\tstyle desc\tsize\tcolor name\tcolor group\tstyle\tclosure\tpaper weight\tpcw recycled %\ttotal recycled\tbrand\tprice\tunit weight\twidth\theight\tdepth";
my $header3 = "sku\tqty";

open (OD, '>/tmp/officedepot.txt');
open (OD2, '>/tmp/officedepot_2.txt');
open (OD3, '>/tmp/inventory.csv');
print OD $header . "\n";
print OD2 $header2 . "\n";
print OD3 $header3 . "\n";

my $success = 1;

#######inventory
print "Getting New Inventory Data.\n";
my $script = 19;
my $oauth = Net::OAuth->request('protected resource')->new(
	consumer_key => '52f7448500c29b1c5fee67d032ef9fbe90fcc3fe1f824e8a22b43f78b8d46186',
	consumer_secret => 'd31ce77fb9ff1f079afa20e1732c86ef28b046c4c1c7c71ed9ee75a377d12f46',
	token            => '265bc141d90e3537137335374cff257ed5a83af795aad4e73419b7404c7656bc',
	token_secret     => '14fe0dadb2339781778b2fb7521dc69b8543f7fb17a750c6cddbc4c7aa97dd31',
	protocol_version => Net::OAuth::PROTOCOL_VERSION_1_0A,
	signature_method => 'HMAC-SHA256',
	timestamp        => time,
	nonce            => int( rand( 2**32 ) ),
	request_url => "https://073514.restlets.api.netsuite.com/app/site/hosting/restlet.nl?script=$script&deploy=1",
	request_method => 'POST',
	signature_method => 'HMAC-SHA256',
	oauth_version => '1.0',
);
$oauth->sign;

my $nsContent = '{}';
my $header = $oauth->to_authorization_header . ',realm="073514"';
my $httpRequest = HTTP::Request->new(
	$oauth->request_method,
	$oauth->request_url,
	[
		'Authorization' => $header,
		'Content-Type' => 'application/json'
	],
	$nsContent,
);

my $httpResponse = LWP::UserAgent->new->request($httpRequest);

if(!$httpResponse->is_error) {
	my @perl_array = @{decode_json ($httpResponse->content())};
	foreach my $key (@perl_array) {
		my $itemId = $key->{'itemid'};
		$key->{'itemid'} =~ s/.*\s+\:+\s+(.*)$/$1/;
		$itemName{$key->{'itemid'}} = $itemId;
		next if (!$key->{'itemid'} =~ m/.*\s+\:+\s+(.*)$/);
		$quantities{$key->{'itemid'}} = $key->{'qty'};

	}
} else {
	print "   Failed to get Inventory Data from Netsuite.";
	exit;
}

$success &&= $products_handle->execute();
while (my $product = $products_handle->fetchrow_hashref()) {
	my $sku = $$product{"product_id"};
	my $skuQty = skuWithQty($$product{"product_id"}, $$product{"quantity"});

	#regular data
	my %features = ();
	$success &&= $features_handle->execute($sku);
	while(my $feature = $features_handle->fetchrow_hashref()) {
		$features{ lc $$feature{"product_feature_type_id"} } = $feature;
	}

	my $key = ($$product{"long_description"} || "") . "CONCAT_DELIM" .  ($features{"size"}->{"description"} || "") . ($$product{"quantity"} || "");

	if(defined $key) {
		#print $_ . "\n";
		#print $desc . "\n";
		if(exists $descAndProdList{$key}) {
			my @array = @{$descAndProdList{$key}};
			push @array, $skuQty;
			$descAndProdList{$key} = [@array];
		} else {
			my @array = ($skuQty);
			$descAndProdList{$key} = [@array];
		}
	} elsif(defined $features{"size"}->{"description"}) {
		if(exists $descAndProdList{$features{"size"}->{"description"}}) {
			my @array = @{$descAndProdList{$features{"size"}->{"description"}}};
			push @array, $skuQty;
			$descAndProdList{$features{"size"}->{"description"}} = [@array];
		} else {
			my @array = ($skuQty);
			$descAndProdList{$features{"size"}->{"description"}} = [@array];
		}
	}

	my $catName = "";
	if(defined $$product{"primary_product_category_id"}) {
		$success &&= $category_handle->execute($$product{"primary_product_category_id"});
		my $catName = $category_handle->fetchrow_array();
		$prodAndCatList{$skuQty} = $catName;
	}

	my $prodFullName = $$product{"product_name"} . " - " . ($features{"color"}->{"description"} || "") . " (" . $$product{"quantity"} . " Qty.)";
	print OD2 ($itemName{$sku} || "") . "\t";
	print OD2 "$skuQty" . "\t";
	print OD2 "$prodFullName" . "\t";
	print OD2 ($$product{"quantity"} || "") . "\t";
	print OD2 ($$product{"long_description"} || "") . "\t";
	print OD2 ($features{"size"}->{"description"} || "") . "\t";
	print OD2 ($features{"color"}->{"description"} || "") . "\t";
	print OD2 ($features{"color_group"}->{"description"} || "") . "\t";
	print OD2 ($$product{"primary_product_category_id"} || "") . "\t";
	print OD2 ($features{"sealing_method"}->{"description"} || "") . "\t";
	print OD2 ($features{"paper_weight"}->{"description"} || "") . "\t";
	print OD2 ($features{"recycled_content"}->{"description"} || "") . "\t";
	print OD2 ($features{"recycled_percent"}->{"description"} || "") . "\t";
	print OD2 ($features{"compare_to_brand"}->{"description"} || "") . "\t";
	print OD2 ($$product{"price"} || "") . "\t";
	print OD2 ($$product{"weight"} || "") . "\t";
	print OD2 ($$product{"width"} || "") . "\t";
	print OD2 ($$product{"height"} || "") . "\t";
	print OD2 ($$product{"depth"} || "") . "\n";

	#print to inventory file
	if(defined $quantities{$sku} && $quantities{$sku} ne "") {
		#divide the quantity by break
		#print $sku . " " . $quantities{$sku} . "\n";
		my $inventoryCount = int($quantities{$sku} / $$product{"quantity"});
		if($inventoryCount > 0) {
			print OD3 "$skuQty,$inventoryCount" . "\n";
		} else {
			print OD3 "$skuQty,0" . "\n";
		}
	} else {
		print OD3 "$skuQty,0" . "\n";
	}
}

for my $desc (keys %descAndProdList) {
	my @array = @{$descAndProdList{$desc}};

	my $key = ($desc || "");
	if(index($key, 'CONCAT_DELIM') != -1) {
		$key = substr $key, 0, index($key, 'CONCAT_DELIM');
	}

	if(exists $prodAndCatList{$array[0]}) {
		print OD $prodAndCatList{$array[0]} . "\t";    #category
	} else {
		print OD "\t";                                 #category
	}

	print OD "\t";                                     #Product Group (Family) Name
	print OD $key . "\t";                  #Marketing Copy
	print OD "\t";                                     #Supplier ID
	foreach (@array) {
		print OD $_ . "\t";
	}
	print OD "\n";
}

sub skuWithoutQty {
	my $sku = shift;
	$sku = substr $sku, 0, rindex($sku, "-");

	return $sku;
}

sub skuWithQty {
	my $sku = $_[0];
	my $qty = $_[1];

	my $newSku = $sku . "-" . $qty;

	if($qty > 500) {
		my $mVal = $qty/1000;
		$newSku = $sku . "-" . $mVal . "M";
	}

	return $newSku;
}

close (OD);
close (OD2);
close (OD3);

$features_handle->finish();
$category_handle->finish();
$products_handle->finish();
$dbh->disconnect();