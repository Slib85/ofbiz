#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use DBI;
use Ofbiz;
use Data::Dumper;
use JSON;

my $ofbiz = Ofbiz->new(conf => "/usr/local/ofbiz/framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;

# retrieve data
my $variableSelect = $dbh->prepare(qq{
	SELECT
		cag.customer_address_group_id as customer_address_group_id,
		cag.name                      as list_name,
		cag.party_id                  as party_id,
		cag.session_id                as session_id,
		cag.from_date                 as from_date,
		cag.thru_date                 as thru_date,
		ca.customer_address_id        as address_id,
		ca.name                       as name1,
		ca.name2                      as name2,
		ca.address1                   as address1,
		ca.address2                   as address2,
		ca.city                       as city,
		ca.state                      as state,
		ca.zip                        as zip,
		ca.country                    as country
	FROM
		customer_address_group cag
			INNER JOIN
		customer_address ca ON cag.customer_address_group_id = ca.customer_address_group_id
	ORDER BY ca.customer_address_group_id;
});
# insert data group
my $variableGroupInsert = $dbh->prepare(qq{
	INSERT INTO variable_data_group SET
		variable_data_group_id = ?,
		name                   = ?,
		attribute_set          = 'address',
		attributes             = '["Name Line 1","Name Line 2","Address Line 1","Address Line 2","City","State","Zip","Country"]',
		session_id             = ?,
		from_date              = ?,
		thru_date              = ?
});
# insert data
my $variableInsert = $dbh->prepare(qq{
	INSERT INTO variable_data SET
		variable_data_id        = ?,
		variable_data_group_id  = ?,
		sequence_num            = ?,
		data                    = ?
});
# env session
my $sessionInsert = $dbh->prepare(qq{
	INSERT INTO env_session SET
		client_id               = 'envelopes',
		party_id                = ?,
		session_id              = ?
});

die "Couldn't prepare queries; aborting" unless defined $variableSelect;

my $success = 1;

$success &&= $variableSelect->execute();
my $counter = 0;
my $sessionId = '00000000-0000-0000-0000-000000000000-00000000-0000-0000-0000-0000000';
my $groupId = "";
while(my $data = $variableSelect->fetchrow_hashref()) {
	# we cant import any of these
	if($$data{"customer_address_group_id"} <= 10099) {
		next;
	}

	if($groupId eq $$data{"customer_address_group_id"}) {
		$counter++;
	} else {
		$counter = 0;
		$groupId = $$data{"customer_address_group_id"};

		#insert data group
		my $envSessionId = $sessionId . $$data{"customer_address_group_id"};
		print $$data{"customer_address_group_id"} . " " . ($$data{"list_name"} || "") . " " . ($$data{"session_id"} || "") . " " . ($$data{"from_date"} || "") . " " . ($$data{"thru_date"} || "") . "\n";
		$success &&= $variableGroupInsert->execute(
			$$data{"customer_address_group_id"},
			$$data{"list_name"} || undef,
			$envSessionId,
			$$data{"from_date"} || undef,
			$$data{"thru_date"} || undef
		) or print "Couldn't save data: " . $dbh->errstr;

		if(defined $$data{"party_id"}) {
			$success &&= $sessionInsert->execute(
				$$data{"party_id"},
				$envSessionId
			) or print "Couldn't save data: " . $dbh->errstr;
		}
	}

	my $name1    = $$data{"name1"} || "";
	my $name2    = $$data{"name2"} || "";
	my $address1 = $$data{"address1"} || "";
	my $address2 = $$data{"address2"} || "";
	my $city     = $$data{"city"} || "";
	my $state    = $$data{"state"} || "";
	my $zip      = $$data{"zip"} || "";
	my $country  = $$data{"country"} || "";
	my @addressArray = ($name1, $name2, $address1, $address2, $city, $state, $zip, $country);

	my $stringifiedArray = JSON->new->utf8->encode(\@addressArray);
	#print $stringifiedArray . "\n";

	#insert data
	#print $$data{"address_id"} . " " . $$data{"customer_address_group_id"} . " " . $counter . " " . $stringifiedArray . "\n";
	$success &&= $variableInsert->execute(
		$$data{"address_id"},
		$$data{"customer_address_group_id"},
		$counter,
		$stringifiedArray
	) or print "Couldn't save data: " . $dbh->errstr;
}

# end transaction
my $result = ($success ? $dbh->commit : $dbh->rollback);

unless($result) {
  die "Couldn't finish transaction: " . $dbh->errstr;
}

$variableSelect->finish();
$variableGroupInsert->finish();
$variableInsert->finish();
$sessionInsert->finish();
$dbh->disconnect();