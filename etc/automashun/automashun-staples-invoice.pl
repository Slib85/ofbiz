#!/usr/bin/env perl
use strict;
use IPC::System::Simple qw(system capture);
use LWP::UserAgent;
use HTTP::Headers;
use HTTP::Request;
use HTTP::Cookies;
use HTTP::Response;
use HTML::TagParser;
use Data::Dumper;
use Mail::IMAPClient;
use Courriel qw();
use JSON;
use Text::CSV;
use Email::Send;
use Email::Simple::Creator;
use Try::Tiny;
use Net::Amazon::AWSSign;
use URI::Query;
use XML::Simple;
use POSIX qw(strftime);
use POSIX qw(ceil);
use XML::DOM;
use Spreadsheet::ParseExcel;
use MIME::Parser;
use IO::Socket::SSL;
use MIME::Lite;
use Email::Send::SMTP::Gmail;
use URI::Escape;

my @messageLog = ();

Log("Getting New Invoice Data.\n");
my $script = 25;
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

my %invoiceInfo = ();

if (!$httpResponse->is_error){
	my @perl_array = @{decode_json ($httpResponse->content())};
	foreach my $key ( @perl_array ){
		$invoiceInfo{$key->{'orderid'}} = $key->{'invoicenumber'};
	}
} else {
	Log("  Failed to get Invoice Data from Netsuite.\n");
	exit;
}


#print Dumper %invoiceInfo;
my $ua = LWP::UserAgent->new;
$ua->timeout(10);
$ua->agent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.109 Safari/537.36");
my $cookie_jar = HTTP::Cookies->new;
$ua->cookie_jar($cookie_jar);

# Check for new files
my $req = HTTP::Request->new( GET => "https://dsm.commercehub.com/dsm/gotoLogin.do" );
my $res = $ua->request($req);
$cookie_jar->extract_cookies( $res );
$ua->cookie_jar($cookie_jar);

my $html = HTML::TagParser->new($res->{_content});

my @formInput = $html->getElementsByTagName("input");
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

sleep(1);

staplesAdvInvoice($ua, $res, $req);
#staplesInvoice($ua, $res, $req);

sub staplesInvoice {
	my $ua = $_[0];
	my $res = $_[1];
	my $req = $_[2];

	push @{ $ua->requests_redirectable }, 'POST';

	$req->uri('https://dsm.commercehub.com/dsm/gotoOpenOrders.do?PID=staples');
	$res = $ua->request($req);

	#search for the order
	keys %invoiceInfo; # reset the internal iterator so a prior each() doesn't affect the loop
	while(my($k, $v) = each %invoiceInfo) {
		#if($k ne "DCA5Z4") {
		#	next;
		#}

		my @formdata = ();
		my $actionURL = "https://dsm.commercehub.com/dsm/handleQuickSearchAction.do";

		push( @formdata, uri_escape( "quickSearchMode") );
		push( @formdata, uri_escape( "oneLineSearch" ) );

		push( @formdata, uri_escape( "quicksearchOneLineSearchName") );
		push( @formdata, uri_escape( "orderPoNumber" ) );

		push( @formdata, uri_escape( "quicksearchCriteria") );
		push( @formdata, uri_escape( $k ) );

		push( @formdata, uri_escape( "quicksearchTypeRealm") );
		push( @formdata, uri_escape( "orders" ) );

		my $postString = "";
		for(my $i = 0; $i < scalar(@formdata); $i+=2) {
			#print $formdata[$i] . "=" . $formdata[$i+1] . "\n";
			$postString .= $formdata[$i] . "=" . $formdata[$i+1] . "&" ;
		}

		$postString =~s/\&$//gsi;
		#print $postString . "\n";

		print "     Searching For Order " . $k . " : ";
		$res = $ua->post($actionURL, Content => $postString, Referer => 'https://dsm.commercehub.com/dsm/gotoHome.do');

		my $html = $res->{_content};

		my $orderView = $res->request()->uri();
		if(index($orderView, "gotoDefaultOrderDetail") != -1) {
			print "Found Order " . $k . " : ";

			if($html =~ /Vendor Invoice/) {
				print "Order " . $k . " Already Invoiced.\n";
				next;
			}

			$orderView =~ /Hub_PO\=(.*?)(?:$|\&)/gi;
			my $hubPO = $1;

			$html =~ /name\=\"shipToId\"(?:|.*?)value\=\"(.*)\"/gi;
			my $shipToId = $1;

			@formdata = ();
			$actionURL = "https://dsm.commercehub.com/dsm/handleOrderAction.do";

			push( @formdata, uri_escape( "partnerId") );
			push( @formdata, uri_escape( "envelopescom" ) );

			push( @formdata, uri_escape( "Hub_PO") );
			push( @formdata, uri_escape( $hubPO ) );

			push( @formdata, uri_escape( "PO" ) );
			push( @formdata, uri_escape( $k ) );

			push( @formdata, uri_escape( "orderId") );
			push( @formdata, uri_escape( $hubPO ) );

			push( @formdata, uri_escape( "orderId") );
			push( @formdata, uri_escape( $hubPO ) );

			push( @formdata, uri_escape( "ponumber") );
			push( @formdata, uri_escape( $k ) );

			push( @formdata, uri_escape( "orderMerchant") );
			push( @formdata, uri_escape( "staples" ) );

			push( @formdata, uri_escape( "shipToId") );
			push( @formdata, uri_escape( $shipToId ) );

			push( @formdata, uri_escape( "merchantCompanyName") );
			push( @formdata, "Staples%20Inc." );

			push( @formdata, uri_escape( "action") );
			push( @formdata, uri_escape( "staples.v_invoice" ) );

			$postString = "";
			for(my $i = 0; $i < scalar(@formdata); $i+=2) {
				#print $formdata[$i] . "=" . $formdata[$i+1] . "\n";
				$postString .= $formdata[$i] . "=" . $formdata[$i+1] . "&" ;
			}

			$postString =~s/\&$//gsi;
			#print $postString . "\n";

			print "Fetching Invoice for Order " . $k . " : ";
			$req->uri($actionURL . "?" . $postString);
			$res = $ua->request($req);

			#print $res->{_content};

			$html = HTML::TagParser->new($res->{_content});

			my($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst) = localtime();

			@formdata = ();
			$actionURL = "https://dsm.commercehub.com/dsm/gotoStaplesInvoiceAction.do";

			my @form = $html->getElementsByAttribute("name", "invoiceForm");
			foreach my $elem(@form) {
				my @inputs = $elem->subTree()->getElementsByTagName("input");
				foreach my $input(@inputs) {
					if(defined $input->getAttribute("name") && defined $input->getAttribute("value")) {
						if($input->getAttribute("name") =~ /lineItemId/) {
							my $lineItem = $input->getAttribute("name");
							$lineItem =~ s/lineItemId//g;
							push( @formdata, $lineItem . "processInv" );
							push( @formdata, "on" );
						}

						if($input->getAttribute("name") ne "confirmbtn" && $input->getAttribute("name") ne "O001totalAmountDisplay") {
							push( @formdata, $input->getAttribute("name") );
							push( @formdata, $input->getAttribute("value") );
						}
					}
				}
			}

			my $curMonth = $mon+1;
			my $curDay = $mday+1;
			my $curYear = $year+1900;
			my $invoiceDate = $curMonth . "/" . $curDay . "/" . $curYear;

			#push( @formdata, uri_escape( "O001invoiceDate") );
			#push( @formdata, uri_escape( $invoiceDate ) );

			push( @formdata, uri_escape( "O001netDue") );
			push( @formdata, uri_escape( "30" ) );

			push( @formdata, uri_escape( "O001discount") );
			push( @formdata, uri_escape( "1" ) );

			push( @formdata, uri_escape( "O001discDue") );
			push( @formdata, uri_escape( "10" ) );

			push( @formdata, uri_escape( "O001invoiceNumber") );
			push( @formdata, uri_escape( $v ) );

			$postString = "";
			for(my $i = 0; $i < scalar(@formdata); $i+=2) {
				#print $formdata[$i] . "=" . $formdata[$i+1] . "\n";
				$postString .= $formdata[$i] . "=" . $formdata[$i+1] . "&" ;
			}

			$postString =~s/\&$//gsi;
			#print $postString . "\n";

			$res = $ua->post($actionURL, Content => $postString, Referer => $res->request()->uri());
			#print $res->{_content};

			print "Done.\n";
		} else {
			print "Skipping Order " . $k . ".\n";
		}
	}
}

sub staplesAdvInvoice {
	my $ua = $_[0];
	my $res = $_[1];
	my $req = $_[2];

	#process staples advantage invoices
	Log("Getting Staples Form Fields to post back to Commerce Hub for Advantage.\n");

	my $page = 1;
	my $totalPages = 0;
	my $actionURL = "";

	$req->uri('https://dsm.commercehub.com/dsm/gotoOrderRealmForm.do?action=web_quickinvoice&merchant=staplesaq');
	$res = $ua->request($req);

	$html = HTML::TagParser->new($res->{_content});

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
			my @orderTables = $elem->subTree()->getElementsByClassName("fw_widget_windowtag");
			foreach my $orderTable(@orderTables) {
				my @orderId = $orderTable->subTree()->getElementsByClassName("simple_link");
				if(scalar(@orderId) == 0) {
					last;
				}
				print "          Processing Order: " . $orderId[0]->innerText();
				if(defined $invoiceInfo{$orderId[0]->innerText()}) {
					my %itemInfo = ();
					my $invoiceTotal = 0;

					my @itemTables = $orderTable->subTree()->getElementsByClassName("fw_widget_tablerow-odd");
					foreach my $item(@itemTables) {
						my @columns = $item->subTree()->getElementsByTagName("td");
						my @itemId = $columns[4]->getAttribute("id") =~ /item\((.*?)\)/gsi;
						my $qtyToInvoice = $columns[4]->innerText();
						my @unitCostInput = $columns[7]->subTree()->getElementsByTagName("input");
						my $unitCost = $unitCostInput[0]->getAttribute("value");
						#print " ID: " . $itemId[0];
						print " QTY: " . $qtyToInvoice;
						print " COST: " . $unitCost;
						$itemInfo{$itemId[0]} = { 'qty' => $columns[4]->innerText(), 'cost' => $unitCost };
					}
					@itemTables = $orderTable->subTree()->getElementsByClassName("fw_widget_tablerow-even");
					foreach my $item(@itemTables) {
						my @columns = $item->subTree()->getElementsByTagName("td");
						my @itemId = $columns[4]->getAttribute("id") =~ /item\((.*?)\)/gsi;
						my $qtyToInvoice = $columns[4]->innerText();
						my @unitCostInput = $columns[7]->subTree()->getElementsByTagName("input");
						my $unitCost = $unitCostInput[0]->getAttribute("value");
						#print " ID: " . $itemId[0];
						print " QTY: " . $qtyToInvoice;
						print " COST: " . $unitCost;
						$itemInfo{$itemId[0]} = { 'qty' => $columns[4]->innerText(), 'cost' => $unitCost };
					}
					foreach my $key (keys %itemInfo) {
						$invoiceTotal += ($itemInfo{$key}->{'cost'}*$itemInfo{$key}->{'qty'});
						$invoiceTotal = sprintf("%.2f", $invoiceTotal);
					}
					print " TOTAL: " . $invoiceTotal;
					print " : INVOICE " . $invoiceInfo{$orderId[0]->innerText()} . "\n";

					my @inputList = $orderTable->subTree()->getElementsByTagName("input");
					my @commerceOrderId = $inputList[0]->getAttribute("name") =~ /\((.*?)\)/gsi;
					#print "ORDER ID: " . $commerceOrderId[0] . "\n";
					push( @formdata, uri_escape("order(" . $commerceOrderId[0] .").id") );
					push( @formdata, uri_escape($commerceOrderId[0]) );
					#enter the shipping method manually
					push( @formdata, uri_escape( "order(" . $commerceOrderId[0] . ").currencycode" ) );
					push( @formdata, uri_escape( "USD" ) );
					foreach my $inputElement(@inputList) {
						#print "NAME=" . $inputElement->getAttribute("name") . "=" . ((defined $inputElement->getAttribute("value")) ? $inputElement->getAttribute("value") : "REMOVE_ME") . "\n";
						if(index($inputElement->getAttribute("name"), "invoicenumber.autofill") == -1 && index($inputElement->getAttribute("name"), "invoiced") == -1) {
							push( @formdata, uri_escape( $inputElement->getAttribute("name") ) );
							if(index($inputElement->getAttribute("name"), "termsdiscountpercent") != -1) {
								push( @formdata, uri_escape( '1' ) );
							} elsif(index($inputElement->getAttribute("name"), "termsdiscountdaysdue") != -1) {
								push( @formdata, uri_escape( '10' ) );
							} elsif(index($inputElement->getAttribute("name"), "invoicenumber") != -1) {
								push( @formdata, uri_escape( $invoiceInfo{$orderId[0]->innerText()} ) );
							} elsif(index($inputElement->getAttribute("name"), "termsnetdaysdue") != -1) {
								push( @formdata, uri_escape( '30' ) );
							} elsif(index($inputElement->getAttribute("name"), "invoicetotal") != -1) {
								push( @formdata, uri_escape( $invoiceTotal ) );
							} else {
								push( @formdata, uri_escape( ((defined $inputElement->getAttribute("value")) ? $inputElement->getAttribute("value") : "REMOVE_ME") ) );
							}
						}
					}
					#we need to add the unit cost for each item as well
					foreach my $key (keys %itemInfo) {
						push( @formdata, uri_escape("order(" . $commerceOrderId[0] . ").item(" . $key  . ").invoiced") );
						push( @formdata, uri_escape( $itemInfo{$key}->{'qty'} ) );
					}

					#delete $invoiceInfo{$orderId[0]->innerText()}
					$counter++;
				} else {
					print " : This order has not been invoiced yet.\n";
				}
				#last;
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
		#only submit if we have invoice to submit else move to next page
		my $postString = "";
		for(my $i = 0; $i < scalar(@formdata); $i+=2) {
			#print $formdata[$i] . "=" . $formdata[$i+1] . "\n";
			$postString .= $formdata[$i] . "=" . (($formdata[$i+1] ne "REMOVE_ME") ? $formdata[$i+1] : "") . "&" ;
		}

		$postString =~s/\&$//gsi;
		print "     Posting Invoices to " . $actionURL . "\n";
		$res = $ua->post($actionURL, Content => $postString, Referer => 'https://dsm.commercehub.com/dsm/gotoOrderRealmForm.do?action=web_quickinvoice&merchant=staplesaq');
		#print "$postString\n";
		#print $res->{_content};
		if(!$res->is_error) {
			print STDERR $res->status_line, "\n";
			print "Error. Please check results.\n";
		} else {
			#print $res->{_content};
			print "Success.\n";
		}
		#assign the new html for next page
		$html = HTML::TagParser->new($res->{_content});
		$page++;
	} while($page <= $totalPages); }
}

sub Log {
	push(@messageLog, $_[0]);
	print $_[0];
	return;
}
