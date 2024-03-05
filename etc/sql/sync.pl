#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use DBI;
use Ofbiz;
use Data::Dumper;

my $oldDSN = "DBI:mysql:database=ofbiz_ae;host=db2.envelopes.com;port=3306";
my $oldDBH = DBI->connect($oldDSN, "ofbiz_ae", "keyuDr7p", {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;

my $newDSN = "DBI:mysql:database=ofbiz;host=shredder.envelopes.com;port=4406";
my $newDBH = DBI->connect($newDSN, "ofbiz", "t8ragaP1", {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;

# retrieve virtual/variants products
my $products_handle = $newDBH->prepare(qq{
	SELECT * FROM PRODUCT LIMIT 1
});

die "Couldn't prepare queries; aborting" unless defined $products_handle;

# start transaction
my $success = 1;
$success &&= $products_handle->execute();

# iterate all variant products and create table rows
while(my $products = $products_handle->fetchrow_hashref()) {
	print Dumper $products;
}

$products_handle->finish();

# end transaction
my $result = ($success ? $oldDBH->commit : $oldDBH->rollback);
$result = ($success ? $newDBH->commit : $newDBH->rollback);

unless($result) {
	die "Couldn't finish transaction: " . $oldDBH->errstr;
}

$oldDBH->disconnect();
$newDBH->disconnect();