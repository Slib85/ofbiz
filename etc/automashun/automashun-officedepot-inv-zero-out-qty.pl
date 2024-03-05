#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use IPC::System::Simple qw(system capture);
use LWP::Simple;
use LWP::UserAgent;
use HTTP::Headers;
use HTTP::Request;
use HTTP::Cookies;
use HTTP::Response;
use HTML::TagParser;
use Data::Dumper;
use JSON;
use URI::Query;
use Email::Send;
use Email::Simple::Creator;
use Email::Send::SMTP::Gmail;
use Text::CSV;
use Text::CSV_XS;
use Net::OAuth::Client;
use URL::Encode 'url_encode';
use Math::Random::MT 'rand';

my %quantities = ();
my %descriptions = ();

my $getNetsuiteInv = 1;

if($getNetsuiteInv) {
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

			$quantities{$key->{'itemid'}} = (defined $key->{'dropship'} && $key->{'dropship'} eq "T") ? '1000' : $key->{'qty'};
			$descriptions{$key->{'itemid'}} = $key->{'desc'};
		}
	} else {
		print "   Failed to get Inventory Data from Netsuite.";
		exit;
	}
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
			my $quantity = $fields[2];

			if($quantity ne '') {
			    $quantities{$sku} = $quantity;
			}
        }
    }
    $overrideCSVCounter++;
}

#print "$_ $inventory{$_}\n" for (keys %inventory);

my $HEADER = [
	'Sku',
	'OD Item Number',
	'Quantity Available'
];

my $outputCSV = Text::CSV_XS->new ({ binary => 1, eol => $/ });
my $CSV = Text::CSV_XS->new ({ binary => 1 });
open(ODCSV, '>/tmp/od-inventory.csv') or die "Couldn't open filehandle: $!";
$outputCSV->print(\*ODCSV, $HEADER);

my $file = '/usr/local/ofbiz/etc/automashun/OfficeDepotINV.csv';
open (INPUTFILE, "<", $file) or die "Couldn't open filehandle: $!";
while (<INPUTFILE>) {
	next if ($. == 1);
	if($CSV->parse($_)) {
		my @columns = $CSV->fields();
		my $sku = removeQuantity($columns[0]);
		my $qty = getQuantity($columns[0]);
		if(!defined $quantities{$sku}) {
			my $row = [ $columns[0], $columns[1], '0' ];
			$outputCSV->print(\*ODCSV, $row);
		} else {
			my $netsuiteQty = $quantities{$sku};
			if($netsuiteQty ne "" && int($netsuiteQty / $qty) > 0) {
				my $row = [ $columns[0], $columns[1], '0' ];
				$outputCSV->print(\*ODCSV, $row);
			} else {
				my $row = [ $columns[0], $columns[1], '0' ];
				$outputCSV->print(\*ODCSV, $row);
			}
		}
		#print $sku . "\n";
	} else {
		my $err = $CSV->error_input;
		print "Failed to parse line: $err";
	}
}

close ODCSV or die "Couldn't close filehandle: $!";
sendEmail();

sub sendEmail{
	my $mail=Email::Send::SMTP::Gmail->new( -smtp=>'smtp.gmail.com', -login=>'service@envelopes.com', -pass=>'Envelopes@123');
	my @receipients = ( 'inbound@b2bgateway.net', 'whitney@bigname.com', 'ian@bigname.com' );
	for (@receipients){
		$mail->send(
					-to=>$_,
					-subject=> 'EnvelopesCom_OfficeDepotCom 846',
					-verbose=>'1',
					-body=> '',
					-attachments=>'/tmp/od-inventory.csv'
					);
	}
	$mail->bye;
}

sub removeQuantity {
	my $sku = shift;
	my $hyphenIndex = rindex($sku, "-");
	if($hyphenIndex != -1) {
		return substr $sku, 0, $hyphenIndex;
	}
	return $sku;
}

sub getQuantity {
	my $sku = shift;
	my $qty = 0;
	my $hyphenIndex = my $result = rindex($sku, "-");
	if($hyphenIndex != -1) {
		$qty = substr $sku, $hyphenIndex+1;
	}

	my $mIndex = index($qty, "M");
	if($mIndex != -1) {
		$qty = (substr $qty, 0, $mIndex)*1000;
	}

	return int($qty);
}
