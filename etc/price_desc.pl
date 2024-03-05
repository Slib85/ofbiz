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
	SELECT
	    p.parent_product_id AS virtual_product_id,
	    p.product_id AS variant_product_id,
	    p.is_printable AS is_printable
	FROM
	    product p
	WHERE
	    p.is_virtual = 'N'
	        AND p.is_variant = 'Y'
	        AND YEAR(p.created_stamp) >= '2007'
	        AND (p.sales_discontinuation_date > NOW()
	        OR p.sales_discontinuation_date IS NULL)
	        AND p.parent_product_id IS NOT NULL
	        AND (p.has_color_opt IS NULL
	        OR p.has_color_opt = 'Y')
	ORDER BY p.parent_product_id
});

my $plain_price_handle = $dbh->prepare(qq{
	SELECT
	    pp.price AS price,
	    pp.quantity AS quantity
	FROM
	    product_price pp
	WHERE
	    pp.colors = 0
	    	AND pp.product_id = ?
	    	AND (pp.thru_date > NOW() OR pp.thru_date IS NULL)
	ORDER BY colors , quantity ASC
	LIMIT 1
});

my $print_price_handle = $dbh->prepare(qq{
	SELECT
	    pp.price AS price,
	    pp.quantity AS quantity
	FROM
	    product_price pp
	WHERE
	    pp.colors >= 1
	    	AND pp.product_id = ?
	    	AND (pp.thru_date > NOW() OR pp.thru_date IS NULL)
	ORDER BY colors , quantity ASC
	LIMIT 1
});

my $update_plain_handle = $dbh->prepare(qq{
	update product set plain_price_description = ? where product_id = ?
});

my $update_print_handle = $dbh->prepare(qq{
	update product set print_price_description = ? where product_id = ?
});

die "Couldn't prepare queries; aborting" unless defined defined $variants_handle && defined $update_plain_handle;

# start transaction
my $success = 1;
$success &&= $variants_handle->execute();

# iterate all virtual products and create table rows
while (my $product = $variants_handle->fetchrow_hashref()) {
	my $virtual_product_id = $$product{"virtual_product_id"};
	my $variant_product_id = $$product{"variant_product_id"};
	my $is_printable = $$product{"is_printable"};

	$success &&= $plain_price_handle->execute($variant_product_id);
	my $plainPrice = $plain_price_handle->fetchrow_hashref();

	if(defined $$plainPrice{"price"} && defined $$plainPrice{"quantity"}) {
		#print "Setting " . $variant_product_id . " to " . description($$plainPrice{"price"}, $$plainPrice{"quantity"}) . "\n";
		$success &&= $update_plain_handle->execute(description(sprintf("%.2f", $$plainPrice{"price"}), $$plainPrice{"quantity"}), $variant_product_id);
	}

	if(defined $is_printable && $is_printable eq "Y") {
		$success &&= $print_price_handle->execute($variant_product_id);
		my $printPrice = $print_price_handle->fetchrow_hashref();

		if(defined $$printPrice{"price"} && defined $$printPrice{"quantity"}) {
			#print "Setting " . $variant_product_id . " to " . description($$printPrice{"price"}, $$printPrice{"quantity"}) . "\n";
			$success &&= $update_print_handle->execute(description(sprintf("%.2f", $$printPrice{"price"}), $$printPrice{"quantity"}), $variant_product_id);
		}
	}
}

sub description {
  return sprintf("\$%s / %s", shift, shift);
}

$variants_handle->finish();
$plain_price_handle->finish();
$print_price_handle->finish();
$update_plain_handle->finish();
$update_print_handle->finish();

# end transaction
my $result = ($success ? $dbh->commit : $dbh->rollback);

unless($result) {
  die "Couldn't finish transaction: " . $dbh->errstr;
}

$dbh->disconnect();
