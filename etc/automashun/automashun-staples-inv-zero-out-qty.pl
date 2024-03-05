#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
# This is compiled with threading support
use strict;
use warnings;
use threads;
use threads::shared;
use DateTime;
use IPC::System::Simple qw(system capture);
use LWP::Simple;
use LWP::UserAgent;
use HTTP::Headers;
use HTTP::Request;
use HTTP::Cookies;
use HTTP::Response;
use Data::Dumper;
use JSON;
use Email::Send;
use Email::Simple::Creator;
use Email::Send::SMTP::Gmail;
use Text::CSV;
use DBI;
use Ofbiz;

my $ofbiz = Ofbiz->new(conf => "/usr/local/ofbiz/framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;

#get parent category
my $product_data = $dbh->prepare(qq{
	select product_id as product_id, internal_name as internal_name, sales_discontinuation_date as discontinued from product where product_id = ?
});

my $curDate = DateTime->now();

use open ':std', ':encoding(UTF-8)';
use Spreadsheet::ParseExcel;
use Spreadsheet::WriteExcel;

my $inputFile = '/usr/local/ofbiz/etc/automashun/staples-inv-sample.xls';
my $outputFile = '/tmp/staples-inv.xls';

print "Opening Sample File.\n";
my $parser   = Spreadsheet::ParseExcel->new();
my $workbook = $parser->Parse($inputFile);
my @sampleRow = ();
my %skuLookup = ();
my %locations = ();
my %quantities = ();
my %overrideQuantities = ();
my %breaks = ();
my %descriptions = ();

print "Opening Output File.\n";
my $workbookOut = Spreadsheet::WriteExcel->new($outputFile);
my $worksheetOut = $workbookOut->add_worksheet();
open (SI, '>', '/tmp/staples-inventory.csv');
print SI "SKU,Quantity,Status,Discontinue Date" . "\n";
open (SIB, '>', '/tmp/staples-inventory_b.csv');
print SIB "SKU,Quantity,Status,Discontinue Date" . "\n";

print "Parsing Sample File Header and creating new Output File.\n";
for my $worksheet ( $workbook->worksheets() ) {
	my ( $row_min, $row_max ) = $worksheet->row_range();
	my ( $col_min, $col_max ) = $worksheet->col_range();
	for my $row ( $row_min .. $row_max ) {
		for my $col ( $col_min .. $col_max ) {
			my $cell = $worksheet->get_cell( $row, $col );
			$cell = ((defined $cell)?$cell->value():"");
			if ($row == 5){
				push(@sampleRow, $cell);
			} else {
				$worksheetOut->write($row, $col,  $cell);
			}
		}
		if ($row == 5){
			last;
		}
	}
}

print "Parsing Cross Reference SKU File.\n";
$workbook = $parser->Parse('/usr/local/ofbiz/etc/automashun/staples-skus.xls');
for my $worksheet ( $workbook->worksheets() ) {
	my ( $row_min, $row_max ) = $worksheet->row_range();
	for my $row ( $row_min .. $row_max ) {
		if ($row == 0){
			next;
		}
		my $sku =$worksheet->get_cell( $row, 3 )->value();
		$sku =~ s/^\s+|\s+$//g;
		$skuLookup{$worksheet->get_cell( $row, 1 )->value()} = {'sku' => $sku, 'qty' => $worksheet->get_cell( $row, 4 )->value(), 'staples_sku' => $worksheet->get_cell( $row, 2 )->value()};
	}
}

#print Dumper %skuLookup;
#exit;

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

if (!$httpResponse->is_error){
	my @perl_array = @{decode_json ($httpResponse->content())};
	foreach my $key ( @perl_array ){
		next if (!$key->{'itemid'} =~ m/.*\s+\:+\s+(.*)$/);
		$key->{'itemid'} =~ s/.*\s+\:+\s+(.*)$/$1/;
		$locations{$key->{'itemid'}} = (defined $key->{'location'}) ? $key->{'location'} : '1';
		$quantities{$key->{'itemid'}} = (defined $key->{'dropship'} && $key->{'dropship'} eq "T") ? '1000' : $key->{'qty'};
		$descriptions{$key->{'itemid'}} = $key->{'desc'};
	}
} else {
	print "   Failed to get Inventory Data from Netsuite.";
	exit;
}

print "Getting New Inventory Override Data.\n";
my $quantityOverrideUrl = 'https://docs.google.com/spreadsheets/d/1CzSzX2xzfT7WVkoBeTd2JAIcw88ZJ-h0RNej6n-t_24/export?format=csv';
my $quantityOverrideContent = get($quantityOverrideUrl);

my $overrideCSV = Text::CSV->new({ binary => 1, sep_char => "," });
my @overrideCSVLines = split /\n/, $quantityOverrideContent;
my $overrideCSVCounter = 0;

foreach my $line (@overrideCSVLines) {
	if($overrideCSVCounter > 0) {
		if($overrideCSV->parse($line."\n")) {
			my @fields = $overrideCSV->fields();
			my $sku = $fields[0];
			my $quantity = $fields[3];

			if($quantity ne '') {
				if(index($quantity, ";") != -1) {
					$quantity =~ m/^(.*?);(.*?)$/;
					my $overrideQty = $1;
					my $qtyToIgnoreStr = $2;

					$overrideQuantities{$sku} = $overrideQty;
					my @qtyToIgnore = split /,/, $qtyToIgnoreStr;
					$breaks{$sku} = \@qtyToIgnore;
				} else {
					$quantity =~ s/^\s+|\s+$//g;
					$quantities{$sku} = $quantity;
				}
			}
        }
    }
    $overrideCSVCounter++;
}

#print Dumper %breaks;
#print "$_ $inventory{$_}\n" for (keys %inventory);
#exit;

my $newRowId = 5;
foreach my $key ( keys %skuLookup ){
	$sampleRow[1] = $key;
	if(defined $ARGV[0] && $ARGV[0] eq "SA") {
		$sampleRow[8] = (defined $descriptions{$skuLookup{$key}->{sku}} && $descriptions{$skuLookup{$key}->{sku}} ne "") ? $descriptions{$skuLookup{$key}->{sku}} : "Envelopes";
	}
	$sampleRow[16] = $skuLookup{$key}->{staples_sku};

	#print $skuLookup{$key}->{sku} . "\n";

	$product_data->execute($skuLookup{$key}->{sku});
	my($productId, $productName, $disc) = $product_data->fetchrow_array();

	if (!defined $quantities{$skuLookup{$key}->{sku}}){
		$sampleRow[2] = "NO";
		$sampleRow[3] = 0;
		$sampleRow[13] = "YES";
		print SI $key . ",0,," . "\n";
		if(defined $locations{$skuLookup{$key}->{sku}} && $locations{$skuLookup{$key}->{sku}} eq "2") {
			print SIB $key . ",0,," . "\n";
		}
	} else {
		my $qty = $quantities{$skuLookup{$key}->{sku}};
		if($qty ne "" && defined $breaks{$skuLookup{$key}->{sku}} && ($skuLookup{$key}->{qty} ~~ $breaks{$skuLookup{$key}->{sku}})) {
			my @breakArray = @{ $breaks{$skuLookup{$key}->{sku}} };
			foreach my $break (@breakArray) {
				if ($skuLookup{$key}->{qty} ne "" && $skuLookup{$key}->{qty} eq $break) {
					if(defined $overrideQuantities{$skuLookup{$key}->{sku}}) {
						$qty = $overrideQuantities{$skuLookup{$key}->{sku}};
					}

					#item is discontinued if qty is set to -1
					$sampleRow[2] = int($qty) > 0 ? "YES" : "NO";
					$sampleRow[3] = int($qty) > 0 ? int($qty / $skuLookup{$key}->{qty}) : 0;
					$sampleRow[13] = int($qty) > 0 ? "" : "NO";
					print SI $key . ",0," . (int($qty) < 0 ? "Discontinued" : "") . "," . $curDate->ymd('') . "\n";
				}
			}
		} elsif($qty ne "" && int($qty) == -1) {
			#item is discontinued if qty is set to -1
			$sampleRow[2] = "NO";
			$sampleRow[3] = 0;
			$sampleRow[13] = "NO";
			print SI $key . ",0,Discontinued," . $curDate->ymd('') . "\n";
		} elsif(defined $descriptions{$skuLookup{$key}->{sku}} && index($descriptions{$skuLookup{$key}->{sku}}, "Spot Seal") != -1 && int($skuLookup{$key}->{qty}) < 500) {
			$sampleRow[2] = "NO";
			$sampleRow[3] = 0;
			$sampleRow[13] = "YES";
			print SI $key . ",0,," . "\n";
			if(defined $locations{$skuLookup{$key}->{sku}} && $locations{$skuLookup{$key}->{sku}} eq "2") {
				print SIB $key . ",0,," . "\n";
			}
		} elsif($qty ne "" && $skuLookup{$key}->{qty} ne "" && int($qty / $skuLookup{$key}->{qty}) > 0){
			$sampleRow[2] = "YES";
			$sampleRow[3] = int($qty / $skuLookup{$key}->{qty});
			$sampleRow[13] = "";
			print SI $key . ",0,," . "\n";
			if(defined $locations{$skuLookup{$key}->{sku}} && $locations{$skuLookup{$key}->{sku}} eq "2") {
				print SIB $key . ",0,," . "\n";
			}
		} else {
			$sampleRow[2] = "NO";
			$sampleRow[3] = 0;
			$sampleRow[13] = "YES";
			print SI $key . ",0,," . "\n";
			if(defined $locations{$skuLookup{$key}->{sku}} && $locations{$skuLookup{$key}->{sku}} eq "2") {
				print SIB $key . ",0,," . "\n";
			}
		}
	}

	for(my $i = 0; $i < scalar(@sampleRow); $i++){
		$worksheetOut->write($newRowId, $i,  $sampleRow[$i]);
	}
	$newRowId++;
}
$workbookOut->close();
close (SI);
sendEmail();
print "New Inventory file Generated.\n";

sub sendEmail{
	my $mail=Email::Send::SMTP::Gmail->new( -smtp=>'smtp.gmail.com', -login=>'service@envelopes.com', -pass=>'Envelopes@123');
	my @receipients = ( 'inbound@b2bgateway.net', 'ian@bigname.com', 'mike@bigname.com' );
	for (@receipients){
		$mail->send(
					-to=>$_,
					-subject=> 'EnvelopesCom Inventory Report',
					-verbose=>'1',
					-body=> '',
					-attachments=>'/tmp/staples-inventory.csv'
					);
	}
	$mail->bye;
}

# todo: CHECK STATUS\
unlink $outputFile;
print "Done!\n";

$product_data->finish();
$dbh->disconnect();
