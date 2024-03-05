#!/usr/bin/env perl
use strict;
use warnings;
use IPC::System::Simple qw(system capture);
use LWP::UserAgent;
use HTTP::Headers;
use HTTP::Request;
use HTTP::Cookies;
use HTTP::Response;
use Data::Dumper;
use Mail::IMAPClient;
use JSON;
use File::Copy;
use URI::Query;
use Net::Amazon::AWSSign;
use POSIX qw(strftime);
use Digest::MD5 qw(md5 md5_hex md5_base64);
use URI::Escape;
use Spreadsheet::ParseExcel;
use Spreadsheet::WriteExcel;
use Email::Send;
use Email::Simple::Creator;
use MIME::Lite;
use Email::Send::SMTP::Gmail;
use DateTime;

my @messageLog = ();

Log("Getting New Tracking Data.\n");
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

my $nsContent = '{"company":"quill"}';
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
		$trackingInfo{$key->{'orderid'}} = {'tracking' => $key->{'tracking'}, 'date' => $key->{'date'}};
	}
} else {
	Log("  Failed to get Tracking Data from Netsuite.\n");
	exit;
}

Log("Opening old file.\n");
my $parser = Spreadsheet::ParseExcel->new();

Log("Creating Output File.\n");
my $number_of_days_back = 2;
my $filename_prefix = "quill-shipping-";
my $filename = "/tmp/$filename_prefix" . strftime("%Y%m%d-%H%M%S", localtime(time)).'.xls';
my $workbookOut = Spreadsheet::WriteExcel->new($filename);
my $worksheetOut = $workbookOut->add_worksheet();

my $heading = $workbookOut->add_format(bold => 1, size => 20);
my $title = $workbookOut->add_format(bold => 1, size => 12);
my $sub = $workbookOut->add_format(bold => 1);
my $left = $workbookOut->add_format(align => 'left');

$worksheetOut->write(0, 0, "Quill Orders", $heading);
$worksheetOut->write(1, 0, "Shipped between " . strftime("%m/%d/%Y", localtime(time)) . " and " . strftime("%m/%d/%Y", localtime(time - $number_of_days_back * 24 * 60 * 60)), $title);
$worksheetOut->write(3, 0, "PO #", $sub);
$worksheetOut->write(3, 1, "Tracking #", $sub);
$worksheetOut->write(3, 2, "Ship Date", $sub);

$worksheetOut->set_column('A:A', 10);
$worksheetOut->set_column('B:B', 21);
$worksheetOut->set_column('C:C', 17);

my $row = 4;

# Go through every file x days back.
my @files = glob("/home/ricardo/automashun/logs/quill_tracking/$filename_prefix*.xls");

my @ignore_orderids_list;
my $dt = DateTime->now();
my $two_days_ago = $dt->subtract(days => $number_of_days_back);

for (my $i = 0; $i < scalar @files - 1; $i++) {
	$files[$i] =~ /$filename_prefix(.*?)\-(.*)/gi;
	if ($1 < $two_days_ago->strftime("%Y%m%d", localtime(time))) {
		move("/home/ricardo/automashun/logs/quill_tracking/$filename_prefix$1-$2", "/home/ricardo/automashun/logs/quill_tracking/old/$filename_prefix$1-$2");
		next;
	}

	my $pastWorkBook = $parser->parse($files[$i]);
	for my $pastWorkSheet ( $pastWorkBook->worksheets() ) {
		my ( $row_min, $row_max ) = $pastWorkSheet->row_range();
		for my $row ( $row_min .. $row_max ) {
			my $cell = $pastWorkSheet->get_cell($row, 0);
			next unless $cell;
			push @ignore_orderids_list, $cell->value();
		}
	}
}

# Build hash of orders already given to quill.
my %ignore_orderids = map { $_ => 1 } @ignore_orderids_list;

# Build worksheet for quill.
foreach my $orderid ( keys %trackingInfo ) {
    if ($orderid eq "" || exists($ignore_orderids{$orderid})){
        next;
    }
    Log ("Processing Order #: $orderid\n");
    $worksheetOut->write($row, 0, $orderid, $left);
    $worksheetOut->write($row, 1, $trackingInfo{$orderid}->{tracking});
    $worksheetOut->write($row, 2, $trackingInfo{$orderid}->{date});
    $row++;
}
$workbookOut->close();

Log("Emailing File.\n");
sendEmail("Quill Tracking Information", "", $filename);

###############################################

sub sendEmail{
    my $subject = $_[0];
    my $body = $_[1];
    my $filename = $_[2];
    my $mail=Email::Send::SMTP::Gmail->new(-smtp=>'smtp.gmail.com', -login=>'service@envelopes.com', -pass=>'BNCPassword@123');
    my @receipients = ( 'whitney@envelopes.com', 'shiplists@quill.com', 'mike@bigname.com', 'ian@bigname.com');
    for (@receipients){
        $mail->send(-to=>$_,
                    -subject=>"Quill Orders Shipped between: " . strftime("%m/%d/%Y", localtime(time - 2 * 24 * 60 * 60)) . " and " . strftime("%m/%d/%Y", localtime(time)),
                    -verbose=>'0',
                    -body=>"Hello, \nAttached are the tracking numbers for any orders shipped between " . strftime("%B %d", localtime(time - 2 * 24 * 60 * 60)) ." and " . strftime("%B %d", localtime(time)) . ".\n\nThanks,\nWhitney",
                    -attachments=>$filename);
    }
    $mail->bye;
}

sub Log {
    push(@messageLog, $_[0]);
    print $_[0];
    return;
}
