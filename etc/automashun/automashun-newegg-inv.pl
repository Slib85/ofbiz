#!/usr/bin/env perl
use lib "../lib/";
# This is compiled with threading support
use strict;
use warnings;
use threads;
use threads::shared;
use IPC::System::Simple qw(system capture);
use LWP::UserAgent;
use HTTP::Headers;
use HTTP::Request;
use HTTP::Cookies;
use HTTP::Response;
use Data::Dumper;
use JSON;
use URI::Query;
use Net::FTP;
use open ':std', ':encoding(UTF-8)';
use Spreadsheet::ParseExcel;
use Spreadsheet::WriteExcel;
use Email::Send::SMTP::Gmail;
use Net::OAuth::Client;
use URL::Encode 'url_encode';
use Math::Random::MT 'rand';

my @messageLog = ();

Log("Opening Sample File.\n");
my $parser   = Spreadsheet::ParseExcel->new();
my $workbook = $parser->Parse('newegg-inv-sample.xls');
my @sampleRow = ();
my %skuLookup = ();
my %quantities = ();
Log("Opening Output File.\n");
my $workbookOut = Spreadsheet::WriteExcel->new('newegg-inv.xls');
my $worksheetOut = $workbookOut->add_worksheet();

Log("Parsing Sample File Header and creating new Output File.\n");;
for my $worksheet ( $workbook->worksheets() ) {
    my ( $row_min, $row_max ) = $worksheet->row_range();
    my ( $col_min, $col_max ) = $worksheet->col_range();
    for my $row ( $row_min .. $row_max ) {
        for my $col ( $col_min .. $col_max ) {
            my $cell = $worksheet->get_cell( $row, $col );
            $cell = ((defined $cell)?$cell->value():"");
        	$worksheetOut->write($row, $col,  $cell);
        }
    	if ($row == 1){
    		last;
		}
    }
}

Log("Parsing Cross Reference SKU File.\n");
$workbook = $parser->Parse('newegg-skus.xls');
for my $worksheet ( $workbook->worksheets() ) {
    my ( $row_min, $row_max ) = $worksheet->row_range();
    for my $row ( $row_min .. $row_max ) {
		if ($row == 0){
			next;
		}
		my $sku =$worksheet->get_cell( $row, 3 )->value();
		$sku =~ s/^\s+|\s+$//g;
        $skuLookup{$worksheet->get_cell( $row, 1 )->value()} = {'sku' => $sku, 'qty' => $worksheet->get_cell( $row, 4 )->value(), 'price' => $worksheet->get_cell( $row, 5 )->value(), 'active' => $worksheet->get_cell( $row, 6 )->value(), 'to-order' => $worksheet->get_cell( $row, 7 )->value(), 'newegg_sku' => $worksheet->get_cell( $row, 2 )->value()};
    }
}

Log("Getting New Inventory Data.\n");
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
		$quantities{$key->{'itemid'}} = $key->{'qty'};
	}
} else {
	Log("   Failed to get Inventory Data from Netsuite.");
	exit;
}

my $newRowId = 2;
foreach my $key ( keys %skuLookup ){
	$sampleRow[0] = $key;
	$sampleRow[1] = $skuLookup{$key}->{newegg_sku};
	$sampleRow[2] = "USD";
	$sampleRow[3] = "";
	$sampleRow[4] = "0.00";
	$sampleRow[5] = "False";
	$sampleRow[6] = $skuLookup{$key}->{price};
	$sampleRow[8] = "Seller";
	$sampleRow[9] = "default";

	if ($skuLookup{$key}->{'to-order'} eq "True"){
		$sampleRow[7] = 500;
		$sampleRow[10] = "True";
	} elsif (!defined $quantities{$skuLookup{$key}->{sku}}){
		$sampleRow[7] = 0;
		$sampleRow[10] = "False";
	} else {
		my $qty = $quantities{$skuLookup{$key}->{sku}};
		if ($qty ne "" && int($quantities{$skuLookup{$key}->{sku}} / $skuLookup{$key}->{qty}) > 0){
			$sampleRow[7] = int($quantities{$skuLookup{$key}->{sku}} / $skuLookup{$key}->{qty});
			$sampleRow[10] = "True";
		}else {
			$sampleRow[7] = 0;
			$sampleRow[10] = "False";
		}
	}

	for(my $i = 0; $i < scalar(@sampleRow); $i++){
		$worksheetOut->write($newRowId, $i,  $sampleRow[$i]);
	}
	$newRowId++;
}
$workbookOut->close();

Log("New Inventory file Generated.\n");

my $host = "ftp03.newegg.com";
my $username = "A2H6";
my $password = "envelopes5300";
my $ftpdir = "/Inbound/Inventory";
my $file = "newegg-inv.xls";

#-- connect to ftp server
Log("Uploading file to newegg ftp...\n");
my $ftp = Net::FTP->new($host, Debug => 0, Timeout=>60, Passive => 1) or die "Error connecting to $host: $!";
$ftp->login($username,$password) or die "Login failed: $!";
$ftp->cwd($ftpdir) or die "Can't go to $ftpdir: $!";
$ftp->put($file, "inventory.xls") or die "Can't put $file: $!";
$ftp->quit or die "Error closing ftp connection: $!";

unlink 'newegg-inv.xls';
Log("Done!\n");

sub Log {
    push(@messageLog, $_[0]);
    print $_[0];
    return;
}

