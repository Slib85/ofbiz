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
use HTML::TagParser;
use Data::Dumper;
use JSON;
use URI::Query;
use Net::Amazon::AWSSign;
use POSIX qw(strftime);
use POSIX qw(ceil);
use Digest::MD5 qw(md5 md5_hex md5_base64);
use URI::Escape;
use Email::Send;
use Email::Send::Gmail;
use Email::Simple::Creator;
use MIME::Lite;
use Email::Send::SMTP::Gmail;
use URI::Escape;

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

my $nsContent = '{"company":"staples"}';
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
		my $trackingNum = '';
		if(defined $key->{'tracking'}) {
			$trackingNum = $key->{'tracking'};
			if(index($trackingNum, "<BR>") > 0) {
				$trackingNum = (split(/<BR>/, $trackingNum))[0];
			}
		}
		$trackingInfo{$key->{'orderid'}} = {'tracking' => $trackingNum, 'date' => $key->{'date'}, 'status' => $key->{'status'}};
	}
} else {
	Log("  Failed to get Tracking Data from Netsuite.\n");
	exit;
}

Log("Logging in to Commerce Hub.\n");
my $ua = LWP::UserAgent->new;
$ua->timeout(30);
$ua->agent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.109 Safari/537.36");
my $cookie_jar = HTTP::Cookies->new;
$ua->cookie_jar($cookie_jar);

my $req = HTTP::Request->new( GET => "https://dsm.commercehub.com/dsm/gotoLogin.do" );
my $res = $ua->request($req);
$cookie_jar->extract_cookies( $res );
$ua->cookie_jar($cookie_jar);

my $loginHTML = HTML::TagParser->new($res->{_content});

my @formInput = $loginHTML->getElementsByTagName("input");
my $lt = "";
my $execution = "";
my $csrfToken = "";
foreach (@formInput) {
	if($_->getAttribute("name") eq "lt") {
		$lt = $_->getAttribute("value");
	} elsif($_->getAttribute("name") eq "execution") {
		$execution = $_->getAttribute("value");
	} elsif($_->getAttribute("name") eq "csrfToken") {
		$csrfToken = $_->getAttribute("value");
	}
}

sleep(1);

my $formdata = ['username' => 'envelopescom', 'password' => 'env5300$', 'lt' => $lt, 'execution' => $execution, '_eventId' => 'submit', 'csrfToken' => $csrfToken, 'submit' => 'Sign in'];
$res = $ua->post("https://apps.commercehub.com/account/login?service=https://dsm.commercehub.com/dsm/shiro-cas", $formdata);
$res = $ua->request($req);

Log("Getting Staples Form Fields to post back to Commerce Hub.\n");
my $page = 0;
my $found = 0;

my @stringsToPost = ();
do {
	my $uri = "https://dsm.commercehub.com/dsm/gotoOrdersWithoutAction.do?isPaging=true&vendor=envelopescom&pager.offset=".($page*50)."&pageSize=50&merchant=staples&goTo=1&new=0";
	Log("Processing Page: ". $uri ."\n" );
	$res = $ua->post($uri);
	my @formdata=();
	my @fields = $res->content =~ /<form name=\"staplesWithoutActionForm\"(?:\s|\w|\=|\"|\/|\.)*>((?:(?:\s*|(?:<!---(?:\s|\w|\*)*--->))*(?:<input(?:\s\w+=\"(?:\w|\d|\(|\)|\=|\?|\.)*\")*>))+)/gsi;
	while( $fields[0] =~ /(<input(?:\s\w+=\"(?:\w|\d|\(|\)|\=|\?|\.)*\")*>)/gsi){
		my @keyval = $1 =~ /<input\stype=\"hidden\"\sname=\"(.*)\"\svalue=\"(.*)\">/gsi;
		if(scalar(@keyval) > 0){
			push( @formdata, uri_escape($keyval[0]));
			push( @formdata, uri_escape($keyval[1]));
		}
	}

	@fields = $res->content =~ /<\/tr>\s*(?:\s*<input\stype=\"hidden\"\sname=\"value\((\w+|\d+)\)\"\svalue=\"(\d+|\w+)\">)+(?:\s*<input\stype=\"hidden\"\sname=\"value\((\w+|\d+)\)\"\svalue=\"(\d+|\w+)\">)+(?:\s*<input\stype=\"hidden\"\sname=\"value\((\w+|\d+)\)\"\svalue=\"(\d+|\w+)\">)+/gsi;
	my @orderId = $res->content =~ /<a\shref=\"\/dsm\/gotoOrderDetail.do\?Hub_PO=\d+\">\s*(\w*|\d*)\s*<\/a>/gsi;

	if (scalar(@fields)/6 != scalar(@orderId)){
		Log('REGEX ERROR!');
		exit;
	}

	for(my $i = 0; $i < scalar(@fields); $i+=6){
		my @id = $fields[$i] =~ /^(O\d+)/gsi;

		if(defined $trackingInfo{$orderId[$i/6]} && index($trackingInfo{$orderId[$i/6]}->{'tracking'}, '<BR>')  == -1){
			push( @formdata, uri_escape('value('.$id[0].'trackingNumber)'));
			push( @formdata, uri_escape($trackingInfo{$orderId[$i/6]}->{'tracking'}));
			push( @formdata, uri_escape('value('.$id[0].'shippingCode)'));
			push( @formdata, uri_escape('UG'));
			$found++;
			Log("Order #: " . $orderId[$i/6] . "  \t\t Tracking #" . $trackingInfo{$orderId[$i/6]}->{'tracking'}."\n");
		} else {
			push( @formdata, uri_escape('value('.$id[0].'trackingNumber)'));
			push( @formdata, uri_escape(''));
			push( @formdata, uri_escape('value('.$id[0].'shippingCode)'));
			push( @formdata, uri_escape(''));
		}

		#Unable to do multiple numbers per order.
		if(defined $trackingInfo{$orderId[$i/6]} && index($trackingInfo{$orderId[$i/6]}->{'tracking'}, '<BR>')  != -1){
			Log("Order #: " . $orderId[$i/6] . "  \t\t Tracking #" . $trackingInfo{$orderId[$i/6]}->{'tracking'}." *** Requires manual action.\n");
			sendEmail('Automashun: Staples - Tracking ERROR: Order #: ' . $orderId[$i/6] .' requires manual action!', join("", @messageLog));
		}

		push( @formdata, uri_escape('value('.$fields[$i].')'));
		push( @formdata, uri_escape($fields[$i+1]));
		push( @formdata, uri_escape('value('.$fields[$i+2].')'));
		push( @formdata, uri_escape($fields[$i+3]));
		push( @formdata, uri_escape('value('.$fields[$i+4].')'));
		push( @formdata, uri_escape($fields[$i+5]));
	}
	push(@formdata, uri_escape('pageSize'));
	push(@formdata, uri_escape('50'));

	my $postString ="";
	for(my $i = 0; $i < scalar(@formdata); $i+=2){
		$postString .= $formdata[$i] . "=" . $formdata[$i+1] . "&" ;
	}
	$postString =~s/\&$//gsi;

	push(@stringsToPost, $postString);

	my @page = $res->content =~/pager\.offset\=(\d+)\'\)\">Next/gsi;
	if (scalar(@page) == 1){
		$page++;
	} else {
		$page = 0;
	}
} while($page != 0);

for(my $i = scalar(@stringsToPost)-1; $i >= 0 ; $i--){
	$res = $ua->post("https://dsm.commercehub.com/dsm/gotoStaplesWithoutActionPaging.do", Content => $stringsToPost[$i], Referer => 'https://dsm.commercehub.com/dsm/gotoOrdersWithoutAction.do?merchant=staples');
}

Log('Done: ' . $found . " Updated.\n");

#now process staples advantage tracking
Log("Getting Staples Advantage Form Fields to post back to Commerce Hub.\n");
$page = 1;
my $uri = 'https://dsm.commercehub.com/dsm/gotoOrderRealmForm.do?action=web_quickship&status=open&substatus=accepted&merchant=staplesaq';
my $totalPages = 0;
my $actionURL = "";

$res = $ua->post($uri);
my $html = HTML::TagParser->new($res->{_content});

my @pagination = $html->getElementsByAttribute("class", "fw_widget_pageselector_text");
my @totalRecords = $pagination[0]->innerText() =~ /displaying.*?of.*?(\d+)/i;
$totalPages = ceil(int($totalRecords[0]) / 10);
print "     Total Pages: " . $totalPages . "\n";
my $counter = 0;

{ do {
	$counter = 0;
	my @formdata = ();
	print "     Processing Page: " . $page . " of " . $totalPages . "\n";
	my @form = $html->getElementsByAttribute("id", "primaryForm");
	foreach my $elem(@form) {
		$actionURL = "https://dsm.commercehub.com" . $elem->getAttribute("action");
		my @orderTables = $elem->subTree()->getElementsByClassName("fw_widget_windowtag_body");
		foreach my $orderTable(@orderTables) {
			my @orderId = $orderTable->subTree()->getElementsByClassName("simple_link");
			if(scalar(@orderId) == 0) {
				last;
			}
			print "          Processing Order: " . $orderId[0]->innerText();
			print $trackingInfo{$orderId[0]->innerText()} . "\n" . $trackingInfo{$orderId[0]->innerText()}->{'status'} . " \n " . $trackingInfo{$orderId[0]->innerText()}->{'status'} . " \n\n\n\n\n\n";
			if(defined $trackingInfo{$orderId[0]->innerText()} && ($trackingInfo{$orderId[0]->innerText()}->{'status'} eq "fullyBilled" || $trackingInfo{$orderId[0]->innerText()}->{'status'} eq "pendingBilling")) {
				my %itemInfo = ();
				my @itemTables = $orderTable->subTree()->getElementsByClassName("fw_widget_tablerow-odd");
				foreach my $item(@itemTables) {
					my @columns = $item->subTree()->getElementsByTagName("td");
					my @itemId = $columns[4]->getAttribute("id") =~ /item\((.*?)\)/gsi;
					#print " ID: " . $itemId[0] . "\n";
					#print " QUANTITY TO SHIP: " . $columns[4]->innerText() . "\n";
					$itemInfo{$itemId[0]} = $columns[4]->innerText();
				}
				@itemTables = $orderTable->subTree()->getElementsByClassName("fw_widget_tablerow-even");
				foreach my $item(@itemTables) {
					my @columns = $item->subTree()->getElementsByTagName("td");
					my @itemId = $columns[4]->getAttribute("id") =~ /item\((.*?)\)/gsi;
					#print " ID: " . $itemId[0] . "\n";
					#print " QUANTITY TO SHIP: " . $columns[4]->innerText() . "\n";
					$itemInfo{$itemId[0]} = $columns[4]->innerText();
				}

				my @inputList = $orderTable->subTree()->getElementsByTagName("input");
				my @commerceOrderId = $inputList[0]->getAttribute("name") =~ /\((.*?)\)/gsi;
				#print "ORDER ID: " . $commerceOrderId[0] . "\n";
				push( @formdata, uri_escape("order(" . $commerceOrderId[0] .").id") );
				push( @formdata, uri_escape($commerceOrderId[0]) );
				#enter the shipping method manually
				push( @formdata, uri_escape( "order(" . $commerceOrderId[0] . ").box(1).shippingmethod" ) );
				push( @formdata, uri_escape( "UG" ) );
				foreach my $inputElement(@inputList) {
					#print "NAME=" . $inputElement->getAttribute("name") . "=" . ((defined $inputElement->getAttribute("value")) ? $inputElement->getAttribute("value") : "REMOVE_ME") . "\n";
					push( @formdata, uri_escape( $inputElement->getAttribute("name") ) );
					if(index($inputElement->getAttribute("name"), "trackingnumber") != -1) {
						push( @formdata, uri_escape( $trackingInfo{$orderId[0]->innerText()}->{'tracking'} ) );
					} elsif(index($inputElement->getAttribute("name"), "shipped") != -1) {
						my @itemId = $inputElement->getAttribute("name") =~ /item\((.*?)\)/gsi;
						push( @formdata, uri_escape( $itemInfo{$itemId[0]} ) );
					} else {
						push( @formdata, uri_escape( ((defined $inputElement->getAttribute("value")) ? $inputElement->getAttribute("value") : "REMOVE_ME") ) );
					}
				}
				print " : Tracking " . $trackingInfo{$orderId[0]->innerText()}->{'tracking'} . "\n";
				$counter++;
			} else {
				print " : This order was not fully shipped and billed.\n";
			}
		}
	}

	push(@formdata, uri_escape('sortbyattribute'));
	push(@formdata, uri_escape('orderDate'));
	push(@formdata, uri_escape('sortorder'));
	push(@formdata, uri_escape('ascending'));
	push(@formdata, uri_escape('request-page-size'));
	push(@formdata, uri_escape('10'));
	if($counter > 0) {
		push(@formdata, uri_escape('page'));
		push(@formdata, uri_escape('REMOVE_ME'));
		push(@formdata, uri_escape('confirmbtn'));
		push(@formdata, uri_escape('Submit'));
		push(@formdata, uri_escape('pageSelector'));
		push(@formdata, uri_escape( $page ));
	} else {
		push(@formdata, uri_escape('page'));
		push(@formdata, uri_escape( $page+1 ));
		push(@formdata, uri_escape('performNewPaging'));
		push(@formdata, uri_escape('true'));
		push(@formdata, uri_escape('pageSelector'));
		push(@formdata, uri_escape( $page ));
	}


	#print Dumper \@formdata;
	#only submit if we have tracking to submit else move to next page
	my $postString = "";
	for(my $i = 0; $i < scalar(@formdata); $i+=2) {
		if(index($formdata[$i], 'expectedDate') == -1 && index($formdata[$i], 'unitcost') == -1) {
			#print $formdata[$i] . "=" . $formdata[$i+1] . "\n";
			$postString .= $formdata[$i] . "=" . (($formdata[$i+1] ne "REMOVE_ME") ? $formdata[$i+1] : "") . "&" ;
		}
	}

	$postString =~s/\&$//gsi;
	#print $postString . "\n";

	print "     Posting Acknowledgements to " . $actionURL . "\n";
	$res = $ua->post($actionURL, Content => $postString, Referer => 'https://dsm.commercehub.com/dsm/gotoOrderRealmForm.do?action=web_quickship&status=open&substatus=accepted&merchant=staplesaq');

	if(!$res->is_error) {
		#print "Error. Please check results.\n";
	} else {
		#print $res->{_content};
		#print "Success.\n";
	}

	#assign the new html for next page
	$html = HTML::TagParser->new($res->{_content});
	$page++;
} while($page <= $totalPages); }

sendEmail('Automashun: Staples - Tracking: ' . $found . " Updated.", join("", @messageLog));

sub sendEmail{
	my $subject = $_[0];
	my $body = $_[1];
	my $filename = $_[2];
	my $mail=Email::Send::SMTP::Gmail->new( -smtp=>'smtp.gmail.com', -login=>'service@envelopes.com', -pass=>'BNCPassword@123');
	my @receipients = ( 'whitney@envelopes.com', 'amy@envelopes.com', 'ian@bigname.com');
	for (@receipients){
		$mail->send(-to=>$_,
					-subject=> $subject,
					-verbose=>'1',
					-body=>$body);
	}
	$mail->bye;
}

sub Log {
	push(@messageLog, $_[0]);
	print $_[0];
	return;
}

