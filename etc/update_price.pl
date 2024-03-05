#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use DBI;
use Ofbiz;
use Text::CSV_XS;

my $ofbiz = Ofbiz->new(conf => "/usr/local/ofbiz/framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;

use constant UPDATE_FILE => "/usr/local/ofbiz/etc/priceupdate.csv";
my $updateFromFile = 0;
if(defined $ARGV[0] && $ARGV[0] eq "FILE") {
	$updateFromFile = 1;
}

my $product_price = $dbh->prepare(qq{
	SELECT
        pp.product_id, pp.quantity, pp.colors, pp.price
    FROM
        product_price pp
            LEFT JOIN
        product_web_site pws on pp.product_id = pws.product_id
    WHERE
        pp.price > 0
            AND pws.envelopes IS NOT NULL
});

my $product_price_by_sku = $dbh->prepare(qq{
	SELECT
        pp.product_id, pp.quantity, pp.colors, pp.price
    FROM
        product_price pp
            LEFT JOIN
        product_web_site pws on pp.product_id = pws.product_id
    WHERE
        pp.price > 0
            AND pws.envelopes IS NOT NULL
            AND pp.product_id = ?
            AND pp.colors = ?
});

my $update_price = $dbh->prepare(qq{
	update product_price set price = ? where product_id = ? and quantity = ? and colors = ?
});

die "Couldn't prepare queries; aborting" unless defined $product_price && defined $update_price;

exit;
# start transaction
my $success = 1;
if($updateFromFile) {
	my $updateData = Text::CSV_XS->new ({ binary => 1 });
	open (INPUTFILE, "<", UPDATE_FILE) or die "Couldn't open filehandle: $!";
	while(<INPUTFILE>) {
		if($updateData->parse($_)) {
			my @columns = $updateData->fields();

			$success &&= $product_price_by_sku->execute($columns[0], $columns[1]);
			# iterate all virtual products and create table rows
			while (my ($productid, $quantity, $colors, $price) = $product_price_by_sku->fetchrow_array()) {
				next unless defined $productid && $quantity && $price;

				my $percentage = 1 + ($columns[2] / 100);
				my $newPrice = sprintf('%0.2f', $price * $percentage);

				# split dollars, cents
				my ($dollars, $cents) = split(/\./, $newPrice);

				#if the dollars end in a multiple of 10, then lower its price by $1 so that it ends in a 9 and looks more attractive
				if (defined $dollars && $dollars > 20) {
					my $lastDigit = substr $dollars, -1, 1;
					if (defined $lastDigit && $lastDigit eq "0") {
						$dollars = $dollars - 1;
					}
				}

				#make it end in 95
				$newPrice = $dollars . '.95';

				print "updating $productid - $quantity - $colors - $price : $newPrice\n";

				$success &&= $update_price->execute($newPrice, $productid, $quantity, $colors);
			}
		}
	}
	close INPUTFILE or die "Couldn't close channel mappings file at " . UPDATE_FILE;
} else {
	$success &&= $product_price->execute();
	# iterate all virtual products and create table rows
	while (my ($productid, $quantity, $colors, $price) = $product_price->fetchrow_array()) {
		next unless defined $productid && $quantity && $price;

		my $newPrice = sprintf('%0.2f', $price * 1.05);

		# split dollars, cents
		my ($dollars, $cents) = split(/\./, $newPrice);

		#if the dollars end in a multiple of 10, then lower its price by $1 so that it ends in a 9 and looks more attractive
		if (defined $dollars && $dollars > 20) {
			my $lastDigit = substr $dollars, -1, 1;
			if (defined $lastDigit && $lastDigit eq "0") {
				$dollars = $dollars - 1;
			}
		}

		#make it end in 95
		$newPrice = $dollars . '.95';

		print "updating $productid - $quantity - $colors - $price : $newPrice\n";

		$success &&= $update_price->execute($newPrice, $productid, $quantity, $colors);
	}
}

$product_price_by_sku->finish();
$product_price->finish();
$update_price->finish();

# end transaction
my $result = ($success ? $dbh->commit : $dbh->rollback);

unless($result) {
  die "Couldn't finish transaction: " . $dbh->errstr;
}

$dbh->disconnect();