#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use DBI;
use Ofbiz;
use Data::Dumper;

my $ofbiz = Ofbiz->new(conf => "/usr/local/ofbiz/framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;

my $select_handle = $dbh->prepare(qq{
SELECT
	order_item_artwork_id AS order_item_artwork_id,
	oiart.order_id AS order_id,
	oiart.order_item_seq_id AS order_item_seq_id,
	oiattr1.attr_value AS colors_front,
	oiattr2.attr_value AS colors_back,
	item_ink_color AS item_ink_color,
	front_ink_color1 AS front1,
	front_ink_color2 AS front2,
	front_ink_color3 AS front3,
	front_ink_color4 AS front4,
	back_ink_color1 AS back1,
	back_ink_color2 AS back2,
	back_ink_color3 AS back3,
	back_ink_color4 AS back4
FROM
	order_item_artwork oiart
		LEFT OUTER JOIN
	order_item_attribute oiattr1 ON oiart.order_id = oiattr1.order_id
		LEFT OUTER JOIN
	order_item_attribute oiattr2 ON oiart.order_id = oiattr2.order_id
WHERE
	oiart.order_item_seq_id = oiattr1.order_item_seq_id
		AND oiart.order_item_seq_id = oiattr2.order_item_seq_id
		AND ((oiattr1.attr_value = 2 AND oiattr2.attr_value = 0) OR (oiattr1.attr_value = 0 AND oiattr2.attr_value = 2))
		AND oiattr1.attr_name = 'colorsFront'
		AND oiattr2.attr_name = 'colorsBack'
		AND oiart.item_ink_color IS NOT NULL
		AND oiart.item_ink_color LIKE '% and %'
});

my $update_front_handle = $dbh->prepare(qq{
	update order_item_artwork set front_ink_color1 = ?, front_ink_color2 = ? where order_item_artwork_id = ?
});

my $update_back_handle = $dbh->prepare(qq{
	update order_item_artwork set back_ink_color1 = ?, back_ink_color2 = ? where order_item_artwork_id = ?
});

die "Couldn't prepare queries; aborting" unless defined $select_handle;

# start transaction
my $success = 1;
$success &&= $select_handle->execute();

# iterate all virtual products and create table rows
while (my $data = $select_handle->fetchrow_hashref()) {
	if($$data{"colors_front"} eq "2" && !defined $$data{"front1"} && !defined $$data{"front2"} && !defined $$data{"front3"} && !defined $$data{"front4"}) {
		my @colors = split / and /, $$data{"item_ink_color"};
		if(scalar @colors > 2) {
			print $$data{"order_id"} . " - " . $$data{"order_item_seq_id"} . " - " . $$data{"item_ink_color"} . " Bad Data\n";
		} else {
			my $front1 = $colors[0];
			$front1 =~ s/^\s+|\s+$//g;
			my $front2 = $colors[1];
			$front2 =~ s/^\s+|\s+$//g;
			print $front1 . "-" . $front2;
			print "Updating " . $$data{"order_id"} . " - " . $$data{"order_item_seq_id"} . "\n";
			$success &&= $update_front_handle->execute($front1, $front2, $$data{"order_item_artwork_id"});
		}
	} elsif($$data{"colors_back"} eq "2" && !defined $$data{"back1"} && !defined $$data{"back2"} && !defined $$data{"back3"} && !defined $$data{"back4"}) {
		my @colors = split / and /, $$data{"item_ink_color"};
		if(scalar @colors > 2) {
			print $$data{"order_id"} . " - " . $$data{"order_item_seq_id"} . " - " . $$data{"item_ink_color"} . " Bad Data\n";
		} else {
			my $front1 = $colors[0];
			$front1 =~ s/^\s+|\s+$//g;
			my $front2 = $colors[1];
			$front2 =~ s/^\s+|\s+$//g;
			print $front1 . "-" . $front2;
			print "Updating " . $$data{"order_id"} . " - " . $$data{"order_item_seq_id"} . "\n";
			$success &&= $update_back_handle->execute($front1, $front2, $$data{"order_item_artwork_id"});
		}
	}
}

$select_handle->finish();
$update_front_handle->finish();
$update_back_handle->finish();

# end transaction
my $result = ($success ? $dbh->commit : $dbh->rollback);

unless($result) {
  die "Couldn't finish transaction: " . $dbh->errstr;
}

$dbh->disconnect();
