#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use DBI;
use Ofbiz;
use Text::CSV_XS;

my $ofbiz = Ofbiz->new(conf => "/usr/local/ofbiz/framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;

use constant PRODUCT_DATA => "/usr/local/ofbiz/etc/product_category.csv";

# delete old data
my $delete_handle = $dbh->prepare(qq{
	DELETE FROM product_category_member
});

my $product_handle = $dbh->prepare(qq{
	select product_id as product_id, primary_product_category_id as category_id from product where is_variant = 'Y'
});

my $create_member = $dbh->prepare(qq{
	REPLACE INTO product_category_member (product_category_id, product_id, from_date) VALUES (?, ?, '2017-11-14 00:00:00.0')
});

die "Couldn't prepare queries; aborting" unless defined $delete_handle && $product_handle;

my $success = 1;
$success &&= $delete_handle->execute();
$success &&= $product_handle->execute();

#do data from product table
while (my($productId, $category_id) = $product_handle->fetchrow_array()) {
    if(defined $category_id) {
	    $success &&= $create_member->execute($category_id, $productId);
	}
}

#do data from file
my $categoryData = Text::CSV_XS->new ({ binary => 1 });
open (INPUTFILE, "<", PRODUCT_DATA) or die "Couldn't open filehandle: $!";
while(<INPUTFILE>) {
    next if ($. == 1);
    if($categoryData->parse($_)) {
        my @columns = $categoryData->fields();
        my $productId = $columns[1];
        my $style = $columns[2];
        my $use = $columns[3];

        my @styles = split(',', $style);
        foreach my $val (@styles) {
            $success &&= $create_member->execute(trim($val), trim($productId));
        }

        my @uses = split(',', $use);
        foreach my $val (@uses) {
            $success &&= $create_member->execute(trim($val), trim($productId));
        }
    }
}

sub trim {
    my $s = shift;
    $s =~ s/^\s+|\s+$//g;
    return $s;
};

$delete_handle->finish();
$product_handle->finish();
$create_member->finish();

# end transaction
my $result = ($success ? $dbh->commit : $dbh->rollback);

unless($result) {
  die "Couldn't finish transaction: " . $dbh->errstr;
}

$dbh->disconnect();