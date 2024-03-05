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

use open ':std', ':encoding(UTF-8)';
use Spreadsheet::ParseExcel;
use Spreadsheet::WriteExcel;
use Net::OAuth::Client;
use URL::Encode 'url_encode';
use Math::Random::MT 'rand';

my $parser   = Spreadsheet::ParseExcel->new();
my %skuLookup = ();
my %quantities = ();

print "Parsing Cross Reference SKU File.\n";
my $workbook = $parser->Parse('ebay-skus.xls');
for my $worksheet ( $workbook->worksheets() ) {
    my ( $row_min, $row_max ) = $worksheet->row_range();
    for my $row ( $row_min .. $row_max ) {
		if ($row == 0){
			next;
		}
		my $sku = $worksheet->get_cell( $row, 3 )->value();
		$sku =~ s/^\s+|\s+$//g;
        $skuLookup{$worksheet->get_cell( $row, 1 )->value()} = {'sku' => $sku, 'qty' => $worksheet->get_cell( $row, 4 )->value()};
    }
}

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
		$quantities{$key->{'itemid'}} = $key->{'qty'};
	}
} else {
	print "   Failed to get Inventory Data from Netsuite.";
	exit;
}
print  "Building XML Request\n";

#get the HTTP::Headers object needed for calls to the eBay API
$httpHeader = HTTP::Headers->new;
#add all the headers with their values
$httpHeader->push_header('X-EBAY-API-COMPATIBILITY-LEVEL' => 851);
$httpHeader->push_header('X-EBAY-API-DEV-NAME' => "82fa78d7-0190-4ab5-a7b4-4ecd477fd599");
$httpHeader->push_header('X-EBAY-API-APP-NAME' => "Envelope-e689-4077-9f4d-9cdd5f297300");
$httpHeader->push_header('X-EBAY-API-CERT-NAME' => "ea96e5f6-9b5e-4605-bbe2-800cf545751e");
$httpHeader->push_header('X-EBAY-API-CALL-NAME' => "GetOrders");
$httpHeader->push_header('X-EBAY-API-SITEID' => "0");
# NOTE: TOKEN WILL EXPIRE ON 6/30/2015
my $xmlBody = '<?xml version="1.0" encoding="utf-8" ?>';
$xmlBody .= '<ReviseInventoryStatusRequest xmlns="urn:ebay:apis:eBLBaseComponents">';
$xmlBody .= '<RequesterCredentials>';
$xmlBody .= '<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**UhDLUg**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wJkYGnAJmDqQudj6x9nY+seQ**cRQCAA**AAMAAA**UyWPbKbznEmpockl1facoccJ6S+s6cKmmNQUa8iT6YpjiPoJWvvCMXNFnQkVU3rx7CZ4pU/2BCNFJgHfWZfd6oe6SfjMuwttjEL7vvyjeUIUc22EKCsP/oEMqAe+0WfdVetFffo0Ifkmrl6s5I6v0+TKgNuNBcSST97KKMF1Nzdlv+dIf/M7m1/MZJKsEZ/EXDHxKJZPqw1HhDcHK/6oNOvMslr8Gu4OI6XkHf0LGJH83PGHjKBgdwwUSSOVLl4xJCvAtpMzqcWo0/lfFre1lynzcSDucrIi5NNyMlWZgtJnnzx5v4T0MeC777xcKZ0OxdiXaGhgNn1NZgJ4kLHSX/ksIlTSGjs8PXswtQCwgOOw9EvvT5evufadoui6cgbN2PgwQLsthvEMk7JfzAt6mJhItKEiaqDKnNv1ZrlUxxYbtrGvuJca/mvabQHlkPVHdW3l6eEX4A9Rb7en3i+LJBmkqDey7Skp7K+ALLJxUysT1K+HAmgKnVgRUi+Ndw+zOtYcP3pVICvbaB9Umhz+LFJA3asN1ESKn7hWr2QCWGfCrVFk3lGRZ3BfW69HbJfRpee5HDXyKCwjOj1v6M4zMIbRoqttJ6azUX1VUuLRvf53TCzcDw/pbsYWKLtA2dVbB8MV+rHmJeXL9gn9Lyn7jKH7eD01Qg297f53Sf6Q7L5Z5pR1o6nRpe41jwKgg+ABmMG7gjFg79z2FlG+E4wKAOM5OXkQDOelx3F+zS52e2Ps0udkibhrR9aC2qKHMUls</eBayAuthToken>';
$xmlBody .= '</RequesterCredentials>';

foreach my $key ( keys %skuLookup ){
	$xmlBody .= '<InventoryStatus>';
	$xmlBody .= '<ItemID>' .$key . '</ItemID>';
	$xmlBody .= '<SKU>' .$skuLookup{$key}->{sku} . '</SKU>';
	if (!defined  $quantities{$skuLookup{$key}->{sku}}){
			$xmlBody .= '<Quantity>0</Quantity>';
	} else {
		my $qty = $quantities{$skuLookup{$key}->{sku}};
		if ($qty ne "" && int($quantities{$skuLookup{$key}->{sku}} / $skuLookup{$key}->{qty}) > 0){
			$xmlBody .= '<Quantity>' . int($quantities{$skuLookup{$key}->{sku}} / $skuLookup{$key}->{qty}) . '</Quantity>';
		}else {
			$xmlBody .= '<Quantity>0</Quantity>';
		}
	}
	$xmlBody .= '</InventoryStatus>';
}

$xmlBody .= '<Version>853</Version>';
$xmlBody .= '</ReviseInventoryStatusRequest>';



print "Sending Data to E-Bay.\n";

#print Dumper $xmlBody;
exit;

$httpRequest = HTTP::Request->new("POST", 'https://api.ebay.com/ws/api.dll', $httpHeader, $xmlBody);
$httpResponse = $ua->request($httpRequest);

#See if there were any errors getting the response
if (!$httpResponse->is_error){

}

# todo: CHECK STATUS\
#unlink 'newegg-inv.xls';
print "Done!\n";
exit;

