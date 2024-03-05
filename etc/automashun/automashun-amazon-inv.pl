#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
# This is compiled with threading support
use strict;
use warnings;
use threads;
use threads::shared;
use IPC::System::Simple qw(system capture);
use LWP::Simple;
use LWP::UserAgent;
use HTTP::Headers;
use HTTP::Request;
use HTTP::Cookies;
use HTTP::Response;
use Data::Dumper;
use JSON;
use URI::Query;
use Net::Amazon::AWSSign;
use POSIX qw(strftime);
use POSIX qw(floor);
use Digest::MD5 qw(md5 md5_hex md5_base64);
use Digest::SHA qw(hmac_sha256_base64);
use URI::Escape;
use HTTP::Request::Common;
use MIME::Base64;
use Email::Send::SMTP::Gmail;
use XML::Simple;
use Text::CSV;
use Scalar::Util qw(looks_like_number);
use DBI;
use Ofbiz;
use Net::OAuth::Client;
use URL::Encode 'url_encode';
use Math::Random::MT 'rand';

my $ofbiz = Ofbiz->new(conf => "/usr/local/ofbiz/framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;

#get parent category
my $product_data = $dbh->prepare(qq{
	select product_id as product_id, internal_name as internal_name, sales_discontinuation_date as discontinued from product where product_id = ?
});

print "Getting New Inventory Data.\n";
my %inventory = ();
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

if(!$httpResponse->is_error) {
	my @perl_array = @{decode_json ($httpResponse->content())};
	foreach my $key ( @perl_array ){
		next if (!$key->{'itemid'} =~ m/.*\s+\:+\s+(.*)$/);
		$key->{'itemid'} =~ s/.*\s+\:+\s+(.*)$/$1/;

		$inventory{$key->{'itemid'}} = (defined $key->{'dropship'} && $key->{'dropship'} eq "T") ? '1000' : $key->{'locationqty'};
	}
} else {
	print "   Failed to get Inventory Data from Netsuite.";
	exit;
}

print "Getting New Inventory Override Data.\n";
my $inventory_override_data = $dbh->prepare(qq{
	SELECT 
		product_id,
		amazon
	FROM
		channels_quantity_override
	WHERE 
		amazon IS NOT NULL
});

$inventory_override_data->execute();

while (my $inventory_override_row = $inventory_override_data->fetchrow_hashref()) {
	$inventory{$$inventory_override_row{"product_id"}} = $$inventory_override_row{"amazon"}; 
}

$inventory_override_data->finish();


#my $quantityOverrideUrl = 'https://docs.google.com/spreadsheets/d/1CzSzX2xzfT7WVkoBeTd2JAIcw88ZJ-h0RNej6n-t_24/export?format=csv';
#my $quantityOverrideContent = get($quantityOverrideUrl);

#my $overrideCSV = Text::CSV->new({ binary => 1, sep_char => "," });
#my @overrideCSVLines = split /\n/, $quantityOverrideContent;
#my $overrideCSVCounter = 0;

#foreach my $line (@overrideCSVLines) {
#	if($overrideCSVCounter > 0) {
#		if($overrideCSV->parse($line."\n")) {
#			my @fields = $overrideCSV->fields();
#			my $sku = $fields[0];
#			my $quantity = $fields[1];
#
#			if($quantity ne '') {
#			    $inventory{$sku} = $quantity;
#			}
#        }
#    }
#    $overrideCSVCounter++;
#}

#print "$_ $inventory{$_}\n" for (keys %inventory);
#print Dumper %inventory;

#get all amazon skus
use constant myAWSId        => 'AKIAIWD7AYNMMN4JINLQ';
use constant myAWSSecret    => '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';
my $awsSign = new Net::Amazon::AWSSign(myAWSId, myAWSSecret);  # New object

# Request Report to be generated
print "   AMAZON: Creating new report for FBA. \n";
my $request = {
	Action => 'RequestReport',
	Marketplace => 'ATVPDKIKX0DER',
	SellerId => 'A1RY28S982B9C3',
	ReportType => '_GET_AFN_INVENTORY_DATA_'
};
my $awsRESTURI="https://mws.amazonservices.com/?".URI::Query->new($request)->stringify;
my $awsSignedRESTURI=$awsSign->addRESTSecret($awsRESTURI);

my $ua = LWP::UserAgent->new;
$ua = new LWP::UserAgent();
my $res = $ua->get($awsSignedRESTURI);
my $content = $res->content();

my $xmlParser = new XML::Simple();
my $xml = $xmlParser->XMLin($content);
my $reportID = $xml->{RequestReportResult}->{ReportRequestInfo}->{ReportRequestId};

my $wait_time = 0;

print ">>> AMAZON: Waiting for report: $reportID. Time: $wait_time seconds...\n";
sleep 1;

# Check if report has been generated.
$request = {
	Action => 'GetReportRequestList',
	"ReportRequestIdList.Id.1" => $reportID,
	Marketplace => 'ATVPDKIKX0DER',
	SellerId => 'A1RY28S982B9C3'
};

$awsRESTURI="https://mws.amazonservices.com/?".URI::Query->new($request)->stringify;
$awsSignedRESTURI=$awsSign->addRESTSecret($awsRESTURI);

my $reportDone = 0;
while ($reportDone == 0) {
	$wait_time += 10;
	$res = $ua->get($awsSignedRESTURI);
	$content = $res->content();
	$xml = $xmlParser->XMLin($content);
	if(defined $xml->{GetReportRequestListResult}->{ReportRequestInfo}->{ReportProcessingStatus} && $xml->{GetReportRequestListResult}->{ReportRequestInfo}->{ReportProcessingStatus} eq "_DONE_" ){
		$reportDone = 1;
		$reportID = $xml->{GetReportRequestListResult}->{ReportRequestInfo}->{GeneratedReportId};
	} else {
		print ">>> AMAZON: Waiting for report: $reportID. Time: $wait_time seconds...\n";

		if($wait_time > 600) {
            exit;
        }
		sleep 10;
	}
}

# Download Report
$request = {
	Action => 'GetReport',
	ReportId => $reportID,
	Marketplace => 'ATVPDKIKX0DER',
	SellerId => 'A1RY28S982B9C3'
};

$awsRESTURI="https://mws.amazonservices.com/?".URI::Query->new($request)->stringify;
$awsSignedRESTURI=$awsSign->addRESTSecret($awsRESTURI);
$res = $ua->get($awsSignedRESTURI);

my $tsv = Text::CSV->new({ binary => 1, sep_char => "\t" });
my @lines = split /\n/, $res->{_content};
my $x = 0;
my %fbaProducts = ();

foreach my $line (@lines) {
	if($x > 0) {
		if($tsv->parse($line."\n")) {
			my @fields = $tsv->fields();
			$fbaProducts{$fields[0]} = {'asin' => $fields[2]};
        }
    }
    $x++;
}

# Request Report to be generated
print "   AMAZON: Creating new report for all products. \n";
$reportID = "";
$request = {
	Action => 'RequestReport',
	Marketplace => 'ATVPDKIKX0DER',
	SellerId => 'A1RY28S982B9C3',
	ReportType => '_GET_FLAT_FILE_OPEN_LISTINGS_DATA_'
};
$awsRESTURI="https://mws.amazonservices.com/?".URI::Query->new($request)->stringify;
$awsSignedRESTURI=$awsSign->addRESTSecret($awsRESTURI);

$ua = new LWP::UserAgent();
$res = $ua->get($awsSignedRESTURI);
$content = $res->content();

$xmlParser = new XML::Simple();
$xml = $xmlParser->XMLin($content);
$reportID = $xml->{RequestReportResult}->{ReportRequestInfo}->{ReportRequestId};

$wait_time = 0;

print ">>> AMAZON: Waiting for report: $reportID. Time: $wait_time seconds...\n";
sleep 1;

# Check if report has been generated.
$request = {
	Action => 'GetReportRequestList',
	"ReportRequestIdList.Id.1" => $reportID,
	Marketplace => 'ATVPDKIKX0DER',
	SellerId => 'A1RY28S982B9C3'
};

$awsRESTURI="https://mws.amazonservices.com/?".URI::Query->new($request)->stringify;
$awsSignedRESTURI=$awsSign->addRESTSecret($awsRESTURI);

$reportDone = 0;
while ($reportDone == 0) {
	$wait_time += 10;
	$res = $ua->get($awsSignedRESTURI);
	$content = $res->content();
	$xml = $xmlParser->XMLin($content);
	if(defined $xml->{GetReportRequestListResult}->{ReportRequestInfo}->{ReportProcessingStatus} && $xml->{GetReportRequestListResult}->{ReportRequestInfo}->{ReportProcessingStatus} eq "_DONE_" ){
		$reportDone = 1;
		$reportID = $xml->{GetReportRequestListResult}->{ReportRequestInfo}->{GeneratedReportId};
	} else {
		print ">>> AMAZON: Waiting for report: $reportID. Time: $wait_time seconds...\n";

		if($wait_time > 600) {
		    exit;
		}
		sleep 10;
	}
}

# Download Report
$request = {
	Action => 'GetReport',
	ReportId => $reportID,
	Marketplace => 'ATVPDKIKX0DER',
	SellerId => 'A1RY28S982B9C3'
};

$awsRESTURI="https://mws.amazonservices.com/?".URI::Query->new($request)->stringify;
$awsSignedRESTURI=$awsSign->addRESTSecret($awsRESTURI);
$res = $ua->get($awsSignedRESTURI);

$tsv = Text::CSV->new({ binary => 1, sep_char => "\t" });
@lines = split /\n/, $res->{_content};
$x = 0;

# Post the new inventory
my $xmlBody = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>";
$xmlBody .= "<AmazonEnvelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"amzn-envelope.xsd\">";
$xmlBody .= "<Header>";
$xmlBody .= "<DocumentVersion>1.01</DocumentVersion>";
$xmlBody .= "<MerchantIdentifier>A1RY28S982B9C3</MerchantIdentifier>";
$xmlBody .= "</Header>";
$xmlBody .= "<MessageType>Inventory</MessageType>";

open (SI, '>', '/tmp/amazon-inventory.csv');
print SI "SKU,Quantity\n";

foreach my $line (@lines) {
	if($x == 0) {
	    $x++;
	    next;
	}
    if($tsv->parse($line."\n")) {
        my @fields = $tsv->fields();

        if(exists $fbaProducts{$fields[0]}) {
        	print SI $fields[0] ."," . $fields[1] . " is FBA, skipping.\n";
            next;
        } #elseif(index($fields[0], "45315-500A") != -1) {
        #    print $fields[0] . "\t" . $fields[1] . " is set to ignore, skipping.\n";
        #    next;
        #}
        $xmlBody .= "<Message>";
        $xmlBody .= "<MessageID>" . $x . "</MessageID>";
        $xmlBody .= "<OperationType>Update</OperationType>";
        $xmlBody .= "<Inventory>";
        $xmlBody .= "<SKU>" . $fields[0] . "</SKU>";
        $xmlBody .= "<Quantity>" . getQty($fields[0]) . "</Quantity>";
        $xmlBody .= "<FulfillmentLatency>1</FulfillmentLatency>";
        $xmlBody .= "</Inventory>";
        $xmlBody .= "</Message>";
        $x++;
        print SI $fields[0] ."," . getQty($fields[0]) . "\n";
    }
}

$xmlBody .= "</AmazonEnvelope>";
close (SI);

#calulate Content-MD5 Header
my $md5hash = md5_base64($xmlBody);
while (length($md5hash) % 4) {
    $md5hash .= '=';
}

#fields to post
my %post_parameters = (
    'AWSAccessKeyId' => 'AKIAIWD7AYNMMN4JINLQ',
    'Action' => 'SubmitFeed',,
    'FeedType' => '_POST_INVENTORY_AVAILABILITY_DATA_',
    'Marketplace' => 'ATVPDKIKX0DER',
    'Merchant' => 'A1RY28S982B9C3',
    'SignatureMethod' => 'HmacSHA256',
    'SignatureVersion' => '2',
    'Timestamp' => strftime("%Y-%m-%dT%H:%M:%SZ", localtime((time + 4 * 60 * 60))),
    'Version' => '2009-01-01'
);
my $myAWSSecret = 'LgTzqXQXMhzHZ9ACao5OCM2T9mB3yFc3iqz/YgB+';

my $stringToSign = "POST\n" .
                   "mws.amazonservices.com\n" .
                   "/\n" .
                   URI::Query->new(%post_parameters);

my $digest = hmac_sha256_base64($stringToSign, $myAWSSecret);
# Fix padding of Base64 digests
while (length($digest) % 4) {
    $digest .= '='
}

$post_parameters{'Signature'} = $digest;
my $ua_send = LWP::UserAgent->new();
my $request_send = HTTP::Request->new(POST => "https://mws.amazonservices.com/?" . URI::Query->new(%post_parameters));
$request_send->header('Content-MD5' => $md5hash);
$request_send->content($xmlBody);
$request_send->content_type("text/xml;");

my $response = $ua_send->request($request_send);
$xml = $xmlParser->XMLin($response->{_content});
if(defined $xml->{SubmitFeedResult}->{FeedSubmissionInfo}->{FeedSubmissionId}) {
    $reportID = $xml->{GetReportRequestListResult}->{ReportRequestInfo}->{GeneratedReportId};
    print ">>> Feed Id: $reportID...\n";
} else {
    print ">>> Feed submission error..\n";
    print $response->{_content} . ","  . "\n";
}

#print $response->{_content};
print "XML Sent to AMAZON. \n";

sub getQty {
	my $sku = $_[0];
	my $qty = 0;

	my @response = $sku =~ m/(.*)-(\d|\w*)$/gis;

	#print $sku . "\n";
    #print @response . "\n";

	$qty = qtyTranslator($response[1]);
    if((defined $qty && !looks_like_number($qty)) || !defined $qty) {
        $qty = 0;
    }

    $product_data->execute($response[0]);
	my($productId, $productName, $disc) = $product_data->fetchrow_array();

    #note: $qty represents the quantity pack for the sku, we then override this below with the qty on hand we are passing to amazon

    #if we found inventory from netsuite
    if(defined $disc) {
        #disc, set inventory to 0
        $qty = 0;
    } elsif(defined $inventory{$response[0]} && ($qty*1) < 2000) {
        #we will only send quantities for skus that are smaller than 2000 packs
        if($inventory{$response[0]} eq "") {
            $inventory{$response[0]} = 0;
        }

        if((($inventory{$response[0]}*1) > ($qty*1)) && ($qty*1) > 0) {
            $qty = ($inventory{$response[0]}*1) / ($qty*1);
        } else {
            $qty = 0;
        }
    } else {
        $qty = 0;
    }

    return floor($qty);
}

sub qtyTranslator {
	my $val = $_[0];
	my %crossRef = (
		"1M", "1000",
		"1.5M", "1500",
		"2M", "2000",
		"2.5M", "2500",
		"3M", "3000",
		"5M", "5000",
		"10M", "10000",
		"20M", "20000",
		"25M", "25000",
		"30M", "30000",
		"35M", "35000",
		"40M", "40000",
		"45M", "45000",
        "50M", "50000"
	);
	return (defined $val && defined $crossRef{$val}) ? $crossRef{$val} : $val;
}

$product_data->finish();
$dbh->disconnect();

