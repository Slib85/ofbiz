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
use Net::Amazon::AWSSign;
use POSIX qw(strftime);
use Digest::MD5 qw(md5 md5_hex md5_base64);
use Digest::SHA qw(hmac_sha256_base64);
use URI::Escape;
use HTTP::Request::Common;
use MIME::Base64;
use MIME::Lite;
use Email::Send::SMTP::Gmail;
use XML::Simple;
use Text::CSV;
use Net::OAuth::Client;
use URL::Encode 'url_encode';
use Math::Random::MT 'rand';

Log ("Getting New Tracking Data.\n");
my @messageLog = ();
my $msgID = 1;
my $script = 20;
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

my $nsContent = '{"company":"amazon"}';
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
my %trackingInfo = ();

if (!$httpResponse->is_error){
	my @perl_array = @{decode_json ($httpResponse->content())};
	foreach my $key ( @perl_array ){
    $key->{'tracking'}  =~ s/(\d+\w+)(?:<BR>(?:\d+\w+))*/$1/g;
    $key->{'tracking'}  =~ s/(?:USPSPriorityMail)(?:<BR>(\d+\w+))*/$1/g;
		$trackingInfo{$key->{'orderid'}} = {'tracking' => $key->{'tracking'}, 'carrier' => $key->{'carrier'}, 'method' => $key->{'method'}, 'date' => $key->{'date'}};
	}
} else {
	Log ("Failed to get Tracking Data from Netsuite.");
    sendEmail();
	exit;
}

my $xmlBody  ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
   $xmlBody .="<AmazonEnvelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"amzn-envelope.xsd\">";
   $xmlBody .="<Header>";
   $xmlBody .="<DocumentVersion>1.01</DocumentVersion>";
   $xmlBody .="<MerchantIdentifier>A1RY28S982B9C3</MerchantIdentifier>";
   $xmlBody .="</Header>";
   $xmlBody .="<MessageType>OrderFulfillment</MessageType>";

###################################
# BEGIN CHECK ON UNSHIPPED ORDERS #
###################################

my $tsv = Text::CSV->new({ binary => 1, sep_char => "\t", escape_char => "\"" });
use constant myAWSId        => 'AKIAIWD7AYNMMN4JINLQ';
use constant myAWSSecret    => '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';
my $awsSign = new Net::Amazon::AWSSign(myAWSId, myAWSSecret);  # New object
my @unshipped_orders_list;

# Request Report to be generated
my $request = {
	Action => 'RequestReport',
	Marketplace => 'ATVPDKIKX0DER',
	SellerId => 'A1RY28S982B9C3',
	ReportType => '_GET_FLAT_FILE_ACTIONABLE_ORDER_DATA_',
	StartDate => strftime("%Y-%m-%dT%H:%M:%S", localtime((time + 5 * 60 * 60) - 10 * 24 * 60 * 60)),
	EndDate => strftime("%Y-%m-%dT%H:%M:%S", localtime((time + 5 * 60 * 60)))
};
my $awsRESTURI="https://mws.amazonservices.com/?".URI::Query->new($request)->stringify;
my $awsSignedRESTURI=$awsSign->addRESTSecret($awsRESTURI);
my $ua = new LWP::UserAgent();
print "   AMAZON: Creating new report. \n";
my $res = $ua->get($awsSignedRESTURI);
my $content = $res->content();

my $xmlParser = new XML::Simple();
my $xml = $xmlParser->XMLin($content);
my $reportID = $xml->{RequestReportResult}->{ReportRequestInfo}->{ReportRequestId};

my $wait_time = 1;

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
while ($reportDone == 0){
	$wait_time++;
	$res = $ua->get($awsSignedRESTURI);
	$content = $res->content();
	$xml = $xmlParser->XMLin($content);
	if(defined $xml->{GetReportRequestListResult}->{ReportRequestInfo}->{ReportProcessingStatus} && $xml->{GetReportRequestListResult}->{ReportRequestInfo}->{ReportProcessingStatus} eq "_DONE_" ){
		$reportDone = 1;
		$reportID = $xml->{GetReportRequestListResult}->{ReportRequestInfo}->{GeneratedReportId};
	} else {
		print ">>> AMAZON: Waiting for report: $reportID. Time: $wait_time seconds...\n";
		sleep 1;
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

open(my $unshippedReport, '>', '/tmp/amazon-tracking-report-file.txt');
print $unshippedReport $res->{_content};
print $unshippedReport "\n\n";

my @lines = split /\n/, $res->{_content};
my $x = 0;
my %amazonOrder = ();
my $lastCustomerID = "0";
my $orderCount = 0;

foreach my $line (@lines) {
	if ($x > 0) {
	    $line =~ s/[^a-zA-Z\d\s-]//g;
		if ($tsv->parse($line."\n")) {
			my @fields = $tsv->fields();
			push @unshipped_orders_list, $fields[0];
			print $unshippedReport $fields[0] . "\n";
		} else {
		    warn "Line could not be parsed: $line\n";
		}
	}
	$x++;
}

close $unshippedReport;

my %unshipped_orders = map { $_ => 1 } @unshipped_orders_list;

################################
# END CHECK FOR SHIPPED ORDERS #
################################
my $responses = "";

foreach my $orderid ( keys %trackingInfo ) {
	$responses .= "Looking at: $orderid\n";
	print "Looking at: $orderid\n";
	if (exists($unshipped_orders{$orderid})) {
		$responses .= "Processing order #: " . $orderid . " \t\t ". $trackingInfo{$orderid}->{tracking} . "\n";
    	Log("Processing order #: " . $orderid . " \t\t ". $trackingInfo{$orderid}->{tracking} . "\n");
		my $carrier = ((uc($trackingInfo{$orderid}->{carrier}) eq "NONUPS")? "USPS": uc($trackingInfo{$orderid}->{carrier}));
		$xmlBody .="<Message>";
		$xmlBody .="<MessageID>".$msgID."</MessageID>";
		$xmlBody .="<OrderFulfillment>";
		$xmlBody .="<AmazonOrderID>" . $orderid . "</AmazonOrderID>";
		$trackingInfo{$orderid}->{date} =~ /^(.*?)\/(.*?)\/(.*?)$/;
		$xmlBody .="<FulfillmentDate>" . "$3-" . sprintf("%02d", $1) . "-" . sprintf("%02d", $2) . "T00:00:00" . "</FulfillmentDate>";
		$xmlBody .="<FulfillmentData>";
		$xmlBody .="<CarrierCode>" . $carrier . "</CarrierCode>";
		$xmlBody .="<ShippingMethod>" . $trackingInfo{$orderid}->{method} . "</ShippingMethod>";
		$xmlBody .="<ShipperTrackingNumber>" .  $trackingInfo{$orderid}->{tracking} . "</ShipperTrackingNumber>";
		$xmlBody .="</FulfillmentData>";
		$xmlBody .="</OrderFulfillment>";
		$xmlBody .="</Message>";
		$msgID++;
	} else {
	    print "$orderid not found in unshipped list\n";
	}
}
$xmlBody .="</AmazonEnvelope>";
print $xmlBody;

if (defined $responses) {
	sendEmail("AUTOMASHUN: Amazon Tracking.", $responses);
}

#calulate Content-MD5 Header
my $md5hash = md5_base64($xmlBody);
while (length($md5hash) % 4) {
  $md5hash .= '=';
}

#fields to post
my %post_parameters = (
    'AWSAccessKeyId' => 'AKIAIWD7AYNMMN4JINLQ',
    'Action' => 'SubmitFeed',,
    'FeedType' => '_POST_ORDER_FULFILLMENT_DATA_',
    'Marketplace' => 'ATVPDKIKX0DER',
    'Merchant' => 'A1RY28S982B9C3',
    'SignatureMethod' => 'HmacSHA256',
    'SignatureVersion' => '2',
    'Timestamp' => strftime("%Y-%m-%dT%H:%M:%SZ", localtime((time + 5 * 60 * 60))),
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
  $digest .= '=';
}

$post_parameters{'Signature'} = $digest;
my $ua_send = LWP::UserAgent->new();
my $request_send = HTTP::Request->new(POST => "https://mws.amazonservices.com/?" . URI::Query->new(%post_parameters));
$request_send->header('Content-MD5' => $md5hash);
$request_send->content($xmlBody);
$request_send->content_type("text/xml;");

my $response = $ua_send->request($request_send);
print $response->{_content};

Log("XML Sent to AMAZON. \n");
exit;

###############################################
sub Log {
    push(@messageLog, $_[0]);
    print $_[0];
    return;
}

sub sendEmail {
	my $subject = $_[0];
	my $body = $_[1];

	open(my $fh, '>', '/tmp/amazon-tracking-report.txt');
	print $fh $body;
	close $fh;

	my $mail=Email::Send::SMTP::Gmail->new( -smtp=>'smtp.gmail.com', -login=>'service@envelopes.com', -pass=>'Envelopes@123');
	my @receipients = ( 'mike@bigname.com', 'laura@bigname.com', 'ian@bigname.com' );

	for (@receipients){
		$mail->send(
					-to=>$_,
					-subject=> $subject,
					-verbose=>'1',
					-body=> $body,
					-attachments=>'/tmp/amazon-tracking-report.txt'
					);
	}

	$mail->bye;
}
