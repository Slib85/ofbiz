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

# retrieve variants products
my $variants_handle = $dbh->prepare(qq{
	select 		p.parent_product_id 			as virtual_product_id,
				p.product_name 					as product_name,
				p.primary_product_category_id	as primary_product_category_id,
				p.product_id 					as variant_product_id,
				p.on_sale 						as on_sale,
				p.product_type_id 				as product_type_id,
				p.is_printable 					as is_printable,
				p.product_height 				as product_height,
				p.product_width 				as product_width,
				p.product_depth 				as product_depth,
				p.plain_price_description		as plain_price,
				p.print_price_description		as print_price,
				p.created_stamp                 as created_stamp
	from   		product p
	where  		p.is_virtual = "N"
				and p.is_variant = "Y"
				and year(p.created_stamp) >= "2007"
				and (p.sales_discontinuation_date > now() or p.sales_discontinuation_date is null)
				and p.parent_product_id is not null
				and (p.has_color_opt is null or p.has_color_opt = 'Y')
	order by 	p.parent_product_id
});

my $category_member_handle = $dbh->prepare(qq{
	select      pc.product_category_id as product_category_id,
	            pc.category_name as category_name
	from        product_category pc inner join product_category_member pcm on pc.product_category_id = pcm.product_category_id
	where       pcm.product_id = ?
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

die "Couldn't prepare queries; aborting" unless defined $variants_handle && defined $category_member_handle && defined $features_handle;;

my $header = "Product\tProduct Name\tColor\tPrimary Category\tAdditional Categories";
open (DF, '>/tmp/product_data.txt');

print DF $header . "\n";

# start transaction
my $success = 1;
$success &&= $variants_handle->execute();
while (my $product = $variants_handle->fetchrow_hashref()) {
	#feature data
	my %features = ();
	$success &&= $features_handle->execute($$product{"variant_product_id"});
	while(my $feature = $features_handle->fetchrow_hashref()) {
		$features{ lc $$feature{"product_feature_type_id"} } = $feature;
	}
	
	print DF $$product{"variant_product_id"} . "\t";
	print DF $$product{"product_name"} . "\t";
	
	print DF ($features{"color"}->{"description"} || "") . "\t";
	print DF ($$product{"primary_product_category_id"} || "") . "\t";
	
	#category data
	$success &&= $category_member_handle->execute($$product{"variant_product_id"});
	while(my $categories = $category_member_handle->fetchrow_hashref()) {
		print DF ($$categories{"category_name"} || "") . ",";
	}

	print DF "\n";
}

close (DF);

$variants_handle->finish();
$category_member_handle->finish();
$features_handle->finish();

# end transaction
my $result = ($success ? $dbh->commit : $dbh->rollback);

unless($result) {
	die "Couldn't finish transaction: " . $dbh->errstr;
}

$dbh->disconnect();
