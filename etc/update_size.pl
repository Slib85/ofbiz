#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use DBI;
use Ofbiz;
use Text::CSV_XS;

my $ofbiz = Ofbiz->new(conf => "/usr/local/ofbiz/framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;

use constant UPDATE_FILE => "/usr/local/ofbiz/etc/product_feature.csv";
my $updateFromFile = 0;
if(defined $ARGV[0] && $ARGV[0] eq "FILE") {
	$updateFromFile = 1;
}

my $all_features = $dbh->prepare(qq{
	SELECT product_feature_id, description from product_feature
});

my $trim_feature = $dbh->prepare(qq{
	update product_feature set description = ? where product_feature_id = ?
});

my $find_feature = $dbh->prepare(qq{
	select product_feature_id from product_feature where description = ? and product_feature_type_id = ? order by created_stamp asc limit 1
});

my $remove_feature_appl = $dbh->prepare(qq{
	delete pfa from product_feature_appl pfa inner join product_feature pf on pf.product_feature_id = pfa.product_feature_id where pfa.product_id = ? and pf.product_feature_type_id = ?
});

my $add_feature_appl = $dbh->prepare(qq{
	insert into product_feature_appl (product_id, product_feature_id, product_feature_appl_type_id, from_date, last_updated_stamp, last_updated_tx_stamp, created_stamp, created_tx_stamp) values (?, ?, ?, now(), now(), now(), now(), now())
});

die "Couldn't prepare queries; aborting" unless defined $all_features && defined $trim_feature;

exit;
# start transaction
my $success = 1;

#trim description of all features
$success &&= $all_features->execute();
while (my ($featureId, $desc) = $all_features->fetchrow_array()) {
	next unless defined $featureId && $desc;
	my $newDesc = $desc;
	$newDesc =~ s/^\s+|\s+$//g;
	print "updated [" . $featureId . "]" . " from [" . $desc . "]" . " to [" . $newDesc . "]\n";
	$success &&= $trim_feature->execute($newDesc, $featureId); #trim all features
}

if($updateFromFile) {
	my $updateData = Text::CSV_XS->new({ binary => 1 });
	open(INPUTFILE, "<", UPDATE_FILE) or die "Couldn't open filehandle: $!";
	while (<INPUTFILE>) {
		if ($updateData->parse($_)) {
			my @columns = $updateData->fields();

			my $productId = $columns[0];
			my $parentId = $columns[1];
			my $newFeature = $columns[2];
			my $featureType = $columns[3];
			my $assocType = $columns[4];

			$success &&= $find_feature->execute($newFeature, $featureType); #find feature by description
			my ($product_feature_id) = $find_feature->fetchrow_array();

			if (defined $product_feature_id) {
				$success &&= $remove_feature_appl->execute($productId, $featureType); #remove old feature
				$success &&= $remove_feature_appl->execute($parentId, $featureType); #remove old feature
				$success &&= $add_feature_appl->execute($productId, $product_feature_id, $assocType); #add new feature
				$success &&= $add_feature_appl->execute($parentId, $product_feature_id, $assocType); #add new feature
			}
		}
	}
	close INPUTFILE or die "Couldn't close channel mappings file at " . UPDATE_FILE;
}

$all_features->finish();
$trim_feature->finish();
$find_feature->finish();
$remove_feature_appl->finish();
$add_feature_appl->finish();

# end transaction
my $result = ($success ? $dbh->commit : $dbh->rollback);

unless($result) {
  die "Couldn't finish transaction: " . $dbh->errstr;
}

$dbh->disconnect();